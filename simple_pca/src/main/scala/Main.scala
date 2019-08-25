import tools._

object Main extends App {

    val file = new DataInput
    val reduction = new DimensionReduction

    val matrix = file.csv_to_matrix("data/matrix.csv")

    val red = reduction.pca(matrix, 0.97)

    println(red)
}

