package marsrover.domain

import marsrover.domain.model.{Bearing, Coordinate, East, Grid, LeftRotate, MarsRover, North, RightRotate, RotateDirection, South, West, origin}

import scala.io.Source

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

  def moveRover(roverWithPendingInstructions: MarsRover, instruction: String) = {
    instruction match {
      case "L" => rotate(roverWithPendingInstructions, LeftRotate)
      case "R" => rotate(roverWithPendingInstructions, RightRotate)
      case "M" => move(roverWithPendingInstructions)
    }
  }
}

object GridState {

  def readInstructionsFile(instructionFileLocation: String): Seq[String] = {
    Source.fromResource(instructionFileLocation).getLines().toSeq
  }

  def constructGrid(gridConstructionInstruction: String): Grid = {
    val maxGridCoordinates = gridConstructionInstruction.split(" ").map(_.toInt)
    val maxCoordinate = Coordinate(maxGridCoordinates(0), maxGridCoordinates(1))
    Grid(origin, maxCoordinate)
  }

  def parseRoverBearingInstruction(roverLocationInstruction: String): Bearing = {
    roverLocationInstruction.split(" ").last match {
      case "N" => North
      case "E" => East
      case "S" => South
      case "W" => West
      case _ => North
    }
  }

  def parseRoverLocationInstruction(roverLocationInstruction: String): Coordinate = {
    val roverGridLocation = roverLocationInstruction.split(" ").init.map(_.toInt)
    Coordinate(roverGridLocation.head, roverGridLocation.last)
  }

  def constructRoverWithInitialCoordinateAndInstructions(roverLocationInstruction: String, roverInstructions: String): MarsRover = {
    val roverGridLocation = parseRoverLocationInstruction(roverLocationInstruction)
    val roverGridBearing = parseRoverBearingInstruction(roverLocationInstruction)
    MarsRover(roverGridLocation, roverGridBearing, roverInstructions.map(_.toString), Seq())
  }

  def getInitialGrid(instructionFileLocation: String): GridState = {
    val instructionsAsStrings = readInstructionsFile(instructionFileLocation)

    val gridConstructionInstruction = instructionsAsStrings.head
    val grid = constructGrid(gridConstructionInstruction)

    val roverConstructionInstructions = instructionsAsStrings.tail
    val rovers = roverConstructionInstructions.grouped(2).map { x =>
      constructRoverWithInitialCoordinateAndInstructions(x.head, x.last)
    }.toSeq
    GridState(rovers, grid)
  }
}
