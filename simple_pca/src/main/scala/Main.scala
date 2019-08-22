import tools._

object Main extends App {

    val filename = "data/test.csv"
    val x = new CSVReader

    val data = x.read_csv(filename)
    val matrix = x.to_matrix(data)

    println(data)
}

