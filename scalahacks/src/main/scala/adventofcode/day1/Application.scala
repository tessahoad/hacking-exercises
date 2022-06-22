package adventofcode.day1

import scala.io.Source

object Application extends App {
  private val input: Array[Int] = Source.fromResource("adventofcode/day1/input.txt").getLines().toArray.map(_.toInt)

  def partOne(numbers: Array[Int]) : Int = {
    numbers.init.zip(numbers.tail).count(x => x._1 < x._2)
  }

  def partTwo(numbers: Array[Int]) : Int = {
    partOne(numbers.sliding(3).map(_.sum).toArray)
  }

  println(s"Part one answer: ${partOne(input)}")
  println(s"Part two answer: ${partTwo(input)}")

}
