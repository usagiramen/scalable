import tools.DimensionReduction

object Main extends App {

    val filename = "data/test.csv"
    val x = new DimensionReduction

    var data = x.read_csv(filename)

    println(data)
}

