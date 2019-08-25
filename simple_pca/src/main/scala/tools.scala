package tools

import breeze.stats.mean
import breeze.linalg._

class DimensionReduction {
    private def covariance(data:DenseMatrix[Double]) : DenseMatrix[Double] = {
        /* 
        Calculate matrix covariance.


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


        // get eigenvectors and eigenvalues.
        val es = eig(cov)
        val evectors = es.eigenvectors
        val evalues = es.eigenvalues
                        .toArray

        // pair each eigenvalues to its eigenvectors.
        var pairs : Array[(Double, DenseVector[Double])] = Array()
        for (idx <- 0 to (evectors.cols - 1)){
            pairs = pairs :+ ((evalues(idx), evectors(::, idx)))
        }
 
        // reorder eigenvalues by variance
        // and calculate cumulative sum.
        val sorted = evalues.sorted(Ordering[Double].reverse).map{
            v => v / evalues.reduceLeft{_ + _}
        }
        val cumsum = sorted.scanLeft(0.0)(_ + _)

        (cumsum, pairs)
    }

    private def projection_matrix(pairs:Array[(Double, DenseVector[Double])], vecs:Int, cols:Int) = {
        
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

    def pca(x:DenseMatrix[Double], threshold:Double): DenseMatrix[Double] = {


        // step 1: find the covariance matrix.
        val cov = covariance(x)

        // step 2: get eigenpairs: an array of eigenvalues and
        // eigenvectors tuple.
        val (sum_values, pairs) = eigen_magic(cov)

        // step 3: build projection matrix.
        val vectors_to_keep = sum_values.indexWhere(_ >= threshold)
        val projection = projection_matrix(pairs, vectors_to_keep, x.cols)
        
        // step 4: dot product raw data with projection matrix.
        val output = x * projection

        output
    }
}

class DataInput {
    def csv_to_matrix(filename:String) : DenseMatrix[Double] = {
        // convert data from csv to 2d array.
        val data = io.Source.fromFile(filename)
                            .getLines()
                            .map(_.split(",").map(_.trim.toDouble))
                            .toArray

        val matrix = DenseMatrix(data:_*)

        matrix
    }
}