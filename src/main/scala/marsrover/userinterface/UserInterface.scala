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

    (gridState.grid.minXY.y to gridState.grid.maxXY.y).foreach( xCoord => {
      (gridState.grid.minXY.x to gridState.grid.maxXY.x).foreach{ yCoord =>
        if (gridState.rovers.exists(rover => rover.coordinate.x == xCoord && rover.coordinate.y == yCoord)) {
          val rover = gridState.rovers.find(rover => rover.coordinate.x == xCoord && rover.coordinate.y == yCoord).get
          rover.bearing match {
            case North => print("⇧   ")
            case East => print("⇨   ")
            case South => print("⇩   ")
            case West => print("⇦   ")
          }
        } else print("X   ")
      }
      println()
    })
    println()

    "string"
  }
}
