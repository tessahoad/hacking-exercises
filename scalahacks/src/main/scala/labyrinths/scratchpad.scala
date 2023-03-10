package labyrinths

import scala.util.Random

// todo: make it testable by being able to force randomness
object scratchpad {
  val random = Random

  def main(args: Array[String]): Unit = {
    val width = 3
    val height = 3
    val startingLabyrinth = Labyrinth.untraversedLabyrinth(width, height, random)
    val moves = Iterator.iterate(startingLabyrinth)(_.traverseFromCurrentCell(random)).take(width * height)
    moves.foreach(ConsoleUI.display)
  }
}

