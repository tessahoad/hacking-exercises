package marsrover

import marsrover.domain.GridState
import marsrover.domain.model._

import scala.io.Source

object scratchpad {

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
