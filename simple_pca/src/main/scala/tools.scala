package tools

import breeze.stats.mean
import breeze.linalg._

class DimensionReduction {
    def pca(x:DenseMatrix[Double], threshold:Double): DenseMatrix[Double] = {
        /** Process Principle Component Analysis.
          * 
          * This function automatically counts number of vectors
          * to keep once the sum of eigenvalues (perc) reaches the
          * threshold.
          */

        // step 1: find the covariance matrix.
        val cov = covariance(x)

        // step 2: get eigenpairs: an array of eigenvalues and
        // eigenvectors tuple.
        val (sum_values, pairs) = eigen_magic(cov)

        // step 3: build projection matrix.
        val vectors_to_keep = sum_values.indexWhere(_ >= threshold)
        val projection = projection_matrix(pairs, vectors_to_keep, x.cols)
        
        // step 4: combine raw data with projection matrix.
        val output = x * projection

        output
    }

    private def covariance(data:DenseMatrix[Double]) : DenseMatrix[Double] = {
        /** Build matrix covariance.
          * 
          * Calculate the covariance between variance in a matrix using
          * dot product. Formula reference: https://bit.ly/2z2DQpd
          * 
          * TODO: Add MinMax Scalar preprocess before  
          */

        // calculate mean of data.
        val x_mean = mean(data.t(*, ::))

        // calculate dot product covariance.
        val covariance = (data.t(::, *) - x_mean) * (data.t(::, *) - x_mean).t
        
        // divide all elements in matrix.
        covariance :/= (data.rows.toDouble - 1.0)

        // final result.
        covariance
    }

    private def eigen_magic(cov:DenseMatrix[Double]) = {
        /** Get array of eigenvalues and eigenvectors.
          *
          * This function receives covariance matrix and builds an array of
          * eigenvalues and eigenvector tuples. It also sort the eigenvalues
          * and project in a cumulative sum.
          */

        // calculate eigenvectors and eigenvalues.
        val es = eig(cov)
        val evectors = es.eigenvectors
        val evalues = es.eigenvalues
                        .toArray

        // pair each eigenvalues to its eigenvectors.
        var pairs : Array[(Double, DenseVector[Double])] = Array()
        for (idx <- 0 to (evectors.cols - 1)){
            pairs = pairs :+ ((evalues(idx), evectors(::, idx)))
        }
 
        // sort eigenvalues by variance in descending order
        // and calculate cumulative sum.
        val sorted = evalues.sorted(Ordering[Double].reverse).map{
            v => v / evalues.reduceLeft{_ + _}
        }
        val cumsum = sorted.scanLeft(0.0)(_ + _)

        (cumsum, pairs)
    }

    private def projection_matrix(pairs:Array[(Double, DenseVector[Double])], vecs:Int, cols:Int) = {
        /** Build projection matrix.
          * 
          * It initalise the matrix with first vector, then adds the
          * remaining vectors according to eigenvalues to keep.
          */

        var projection = pairs(0)._2
                                 .asDenseMatrix
                                 .reshape(cols, 1)

        for (idx <- 1 to (vecs - 1)){
            val vector = pairs(idx)._2
                                   .asDenseMatrix
                                   .reshape(cols, 1)

            projection = DenseMatrix.horzcat(projection, vector)
        }

        projection
    }

    
}

class DataInput {
    def csv_to_matrix(filename:String) : DenseMatrix[Double] = {
        /** Convert CSV file into DenseMatrix.
         *  
         * This function assumes the CSV only contains numbers.
         * 
         * TODO: add number validation and exclude non-number columns.
         */

        val data = io.Source.fromFile(filename)
                            .getLines()
                            .drop(1)  // remove headers.
                            .map(_.split(",").map(_.trim.toDouble))
                            .toArray

        val matrix = DenseMatrix(data:_*)

        matrix
    }
}