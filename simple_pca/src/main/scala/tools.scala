package tools

import breeze.stats.mean
import breeze.linalg._

class DimensionReduction {
    def covariance(data:DenseMatrix[Double]) : DenseMatrix[Double] = {
        /* 
        Calculate matrix covariance.

        reference: https://towardsdatascience.com/principal-component-analysis-your-tutorial-and-code-9719d3d3f376

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

    def pca(x:DenseMatrix[Double]) = {


        // step 1: find the covariance matrix.
        val cov = covariance(x)
        print(cov.cols)
        // step 2: calculate eigenvalues and eigenvectors.
        val es = eig(cov)

        val eigenvalues = es.eigenvalues
                            .toArray
        
        val eigenvectors = es.eigenvectors

        val eigenpairs = eigenvalues.foreach(values => (1, values))
        println(eigenpairs)
        println(eigenvectors)
        println(eigenvectors(0, ::))

        // eigenvalues.foreach(v => println(v))
        // println(es.eigenvectors)
        // eigenvectors.foreach(v => println(v))

        // step 3: reorder eigenvalues by variance.

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