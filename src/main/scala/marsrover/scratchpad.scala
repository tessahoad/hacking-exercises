package marsrover

import scala.io.Source

object scratchpad {

  sealed trait Bearing

  object North extends Bearing

  object East extends Bearing

  object South extends Bearing

  object West extends Bearing

  sealed trait RotateDirection

  object LeftRotate extends RotateDirection

  object RightRotate extends RotateDirection

  case class MarsRover(coordinate: Coordinate,
                       bearing: Bearing,
                       pendingInstructions: Seq[String],
                       completedInstructions: Seq[String])

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

  def move(marsRover: MarsRover): MarsRover = {
    def moveY(n: Int) = {
      marsRover.copy(coordinate = marsRover.coordinate.copy(y = marsRover.coordinate.y + n))
    }

    def moveX(n: Int) = {
      marsRover.copy(coordinate = marsRover.coordinate.copy(x = marsRover.coordinate.x + n))
    }

    marsRover.bearing match {
      case North => moveY(1)
      case East => moveX(1)
      case South => moveY(-1)
      case West => moveX(-1)
    }
  }

  def getInitialGrid(instructionFileLocation: String): GridState = {
    val instructionsAsStrings = Source.fromResource(instructionFileLocation).getLines().toSeq
    val gridConstructionInstruction = instructionsAsStrings.head
    val maxGridCoordinates = gridConstructionInstruction.split(" ").map(_.toInt)
    val maxCoordinate = Coordinate(maxGridCoordinates(0), maxGridCoordinates(1))
    val roverConstructionInstructions = instructionsAsStrings.tail
    val rovers = roverConstructionInstructions.grouped(2).map { x =>
      val roverGridLocation = x.head.split(" ").init.map(_.toInt)
      val roverGridBearing = x.head.split(" ").last.map {
        case 'N' => North
        case 'E' => East
        case 'S' => South
        case 'W' => West
        case _ => North
      }.head
      val roverMovementInstructions = x.last
      MarsRover(Coordinate(roverGridLocation.head, roverGridLocation.last), roverGridBearing, roverMovementInstructions.map(_.toString), Seq())
    }.toSeq
    GridState(rovers, Grid(origin, maxCoordinate))
  }

  final val origin = Coordinate(0, 0)

  case class Coordinate(x: Int, y: Int)

  case class Grid(minXY: Coordinate, maxXY: Coordinate)

  case class GridState(rovers: Seq[MarsRover], grid: Grid) {

    def roversHavePendingInstructions(): Boolean = {
      rovers.exists(rover => rover.pendingInstructions.nonEmpty)
    }

    def moveRovers(): GridState = {
      if (!roversHavePendingInstructions()) {
        return null
      }
      val roverWithPendingInstructions = rovers.find(rover => rover.pendingInstructions.nonEmpty).get
      val instruction = roverWithPendingInstructions.pendingInstructions.head
      val movedRover = instruction match {
        case "L" => rotate(roverWithPendingInstructions, LeftRotate)
        case "R" => rotate(roverWithPendingInstructions, RightRotate)
        case "M" => move(roverWithPendingInstructions)
      }
      val movedRoverWithUpdatedInstructions = movedRover.copy(
        pendingInstructions = movedRover.pendingInstructions.tail,
        completedInstructions = movedRover.completedInstructions ++ Seq(instruction)
      )

      this.copy(
        rovers = rovers.updated(rovers.indexOf(roverWithPendingInstructions), movedRoverWithUpdatedInstructions)
      )
    }
  }

  trait UserInterface {
    def display(gridState: GridState): String
  }

  object ConsoleUI extends UserInterface {
    override def display(gridState: GridState): String = {

      gridState.rovers.zipWithIndex.foreach { case (rover, index) =>
        println("rover-" + index + " location: x: " + rover.coordinate.x + ", y: " + rover.coordinate.y + ", bearing: " + rover.bearing.getClass.getSimpleName)
      }
      "string"
    }
  }


}
