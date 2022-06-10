package marsrover

import marsrover.domain.GridState.getInitialGrid
import marsrover.userinterface.ConsoleUI

object Application {
  def main(args: Array[String]): Unit = {

    val initialState = getInitialGrid("marsrover/instructions.txt")

    val movements = Iterator.iterate(initialState)(_.moveRovers()).takeWhile(_ != null)
    movements.foreach(ConsoleUI.display)
  }
}
