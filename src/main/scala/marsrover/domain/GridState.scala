package marsrover.domain

import marsrover.domain.model.{Bearing, East, Grid, LeftRotate, MarsRover, North, RightRotate, RotateDirection, South, West}

case class GridState(rovers: Seq[MarsRover], grid: Grid) {

  def rotate(marsRover: MarsRover, rotateDirection: RotateDirection): MarsRover = {
    val roverBearing = marsRover.bearing
    val newBearing = rotateDirection match {
      case LeftRotate => rotate90ClockwiseNTimes(roverBearing, 3)
      case RightRotate => rotate90ClockwiseNTimes(roverBearing, 1)
    }
    marsRover.copy(bearing = newBearing)
  }

  def rotate90ClockwiseNTimes(roverBearing: Bearing, n: Int): Bearing = {
    (1 to n).foldLeft(roverBearing)((x, _) => rotate90Clockwise(x))
  }

  def rotate90Clockwise(bearing: Bearing): Bearing = {
    bearing match {
      case North => East
      case East => South
      case South => West
      case West => North
    }
  }

  def moveY(marsRover: MarsRover, n: Int) = {
    marsRover.copy(coordinate = marsRover.coordinate.copy(y = marsRover.coordinate.y + n))
  }

  def moveX(marsRover: MarsRover, n: Int) = {
    marsRover.copy(coordinate = marsRover.coordinate.copy(x = marsRover.coordinate.x + n))
  }

  def move(marsRover: MarsRover): MarsRover = {
    marsRover.bearing match {
      case North => moveY(marsRover, 1)
      case East => moveX(marsRover, 1)
      case South => moveY(marsRover, -1)
      case West => moveX(marsRover, -1)
    }
  }

  def roversHavePendingInstructions(): Boolean = {
    rovers.exists(rover => rover.pendingInstructions.nonEmpty)
  }

  def moveRovers(): GridState = {
    if (!roversHavePendingInstructions()) {
      return null
    }
    val roverWithPendingInstructions = rovers.find(rover => rover.pendingInstructions.nonEmpty).get
    val instruction = roverWithPendingInstructions.pendingInstructions.head
    val movedRover: MarsRover = moveRover(roverWithPendingInstructions, instruction)
    val movedRoverWithUpdatedInstructions = movedRover.copy(
      pendingInstructions = movedRover.pendingInstructions.tail,
      completedInstructions = movedRover.completedInstructions ++ Seq(instruction)
    )

    this.copy(
      rovers = rovers.updated(rovers.indexOf(roverWithPendingInstructions), movedRoverWithUpdatedInstructions)
    )
  }

  private def moveRover(roverWithPendingInstructions: MarsRover, instruction: String) = {
    instruction match {
      case "L" => rotate(roverWithPendingInstructions, LeftRotate)
      case "R" => rotate(roverWithPendingInstructions, RightRotate)
      case "M" => move(roverWithPendingInstructions)
    }
  }
}
