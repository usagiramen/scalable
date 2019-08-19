import breeze.stats.mean
import breeze.linalg._

object Main extends App {

}

object DimensionReduction {
    def main(args: Array[String]) = {
        // val data = read_csv("data/test.csv")

        val x = DenseMatrix((-1.0, -1.0, 0.0),
                            (-2.0, -1.0, 2.0),
                            (-3.0, -2.0, 0.0),
                            (1.0, 1.0, 1.0),
                            (2.0, 1.0, 1.0),
                            (3.0, 2.0, 2.0),
                            (1.0, 2.0, 1.0))
        println(pca(x))
    }

    def read_csv(filename:String) : Array[Array[Double]] = {
        io.Source.fromFile(filename)
          .getLines()
          .map(_.split(",").map(_.trim.toDouble))
          .toArray
    }

    def covariance(data:DenseMatrix[Double]) : DenseMatrix[Double] = {
        // reference: https://towardsdatascience.com/principal-component-analysis-your-tutorial-and-code-9719d3d3f376

        // calculate mean of data.
        val x_mean = mean(data.t(*, ::))
        // calculate dot product covariance.
        val covariance = (data.t(::, *) - x_mean) * (data.t(::, *) - x_mean).t
        
        covariance :/= (data.rows.toDouble - 1.0)

        // final result.
        covariance
    }

    def eigenvectors(cov:DenseMatrix[Double]) = {
        val es = eig(cov)

        (es.eigenvalues, es.eigenvectors)
    }

    def pca(x:DenseMatrix[Double]) = {
        // step 1: find the covariance matrix.
        val cov = covariance(x)

        // step 2: calculate eigenvalues and eigenvectors.
        val eigen = eigenvectors(cov)
        
        // step 3: reorder eigenvalues by variance.

        eigen
    }
}
