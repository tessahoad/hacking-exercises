package marsrover.userinterface

import marsrover.domain.GridState
import marsrover.domain.model._

trait UserInterface {
  def display(gridState: GridState): String
}

object ConsoleUI extends UserInterface {
  override def display(gridState: GridState): String = {

    println("CURRENT GRID STATE: ")
    println()
    gridState.rovers.zipWithIndex.foreach { case (rover, index) =>
      println("rover-" + index + " location: x: " + rover.coordinate.x + ", y: " + rover.coordinate.y + ", bearing: " + rover.bearing.getClass.getSimpleName)
    }
    println()

    "string"
  }
}
