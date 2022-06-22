package adventofcode.day7

import scala.io.Source

object Application extends App {
  private val input: Array[Int] = Source.fromResource("adventofcode/day7/input.txt").getLines().mkString.split(",").map(_.toInt)

  private val testInput: Array[Int] = Array(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)

  def getNthTriangleNumber(n: Int): Int = {
    (n * (n + 1)) / 2
  }

  def getNaiveFuelConsumption(finalPosition: Int, crabPositions: Array[Int]) = {
    crabPositions.map(_.-(finalPosition).abs).sum
  }

  def getCrabEngineeredFuelConsumption(finalPosition: Int, crabPositions: Array[Int]) = {
    crabPositions.map(p => getNthTriangleNumber((p - finalPosition).abs)).sum
  }

  val minFuelConsumption = input.distinct.map(getCrabEngineeredFuelConsumption(_, input)).min

  println(s"Fuel consumption: $minFuelConsumption")
}
