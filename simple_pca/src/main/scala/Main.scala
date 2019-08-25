import tools._

object Main extends App {

    val file = new DataInput
    val reduction = new DimensionReduction

    val matrix = file.csv_to_matrix("data/trimmed_creditcard.csv")
    println("Total features before PCA: " + matrix.cols)

    val pca = reduction.pca(matrix, 0.9)
    println("Total features after PCA: " + pca.cols)
}

