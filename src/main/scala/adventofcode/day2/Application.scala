package adventofcode.day2

import scala.io.Source

object Application extends App {
  private val input: Array[String] = Source.fromResource("adventofcode/day2/input.txt").getLines().toArray


  def partOne(input: Array[String]) = {
    val tuple = input.map(s => s.split(" ")).foldLeft(0, 0)((x, y) => {
      val instruction = y(0)
      instruction match {
        case "up" => (x._1 - y(1).toInt, x._2)
        case "down" => (x._1 + y(1).toInt, x._2)
        case "forward" => (x._1, x._2 + y(1).toInt)
      }
    })
    calculateFinalScore(tuple._1, tuple._2)
  }

  private def calculateFinalScore(depth: Int, horizontalPosition: Int): Int = {
    depth * horizontalPosition
  }


  def partTwo(input: Array[String]) = {
    val tuple = input.map(s => s.split(" ")).foldLeft(0, 0, 0)((x, y) => {
      val instruction = y(0)
      instruction match {
        case "up" => (x._1, x._2, x._3 - y(1).toInt)
        case "down" => (x._1, x._2, x._3 + y(1).toInt)
        case "forward" => (x._1 + x._3 * y(1).toInt, x._2 + y(1).toInt, x._3)
      }
    })
    calculateFinalScore(tuple._1, tuple._2)
  }

  println(s"Part one answer: ${partOne(input)}")
  println(s"Part two answer: ${partTwo(input)}")

}
