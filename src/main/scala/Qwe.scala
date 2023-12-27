

object Qwe extends App {


  val myMap = Map("key1" -> "value1", "key2" -> "value2", "key3" -> "value3")

  println(myMap.toList.flatMap(tuple => List(tuple._1, tuple._2)))
  println(myMap.flatMap { case (key, value) => List(key, value) })

}
