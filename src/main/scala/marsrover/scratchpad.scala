package marsrover

object scratchpad {

  sealed trait Bearing
  object North extends Bearing
  object East extends Bearing
  object South extends Bearing
  object West extends Bearing

  case class MarsRover(coordinate: Coordinate, bearing: Bearing)

  def parseInstructions(instructionFileLocation: String): Seq[Instruction] = {
    Seq()
  }

  case class Instruction()

  case class Coordinate(x: Int, y: Int)

  case class Grid(minX: Int, minY: Int, maxX: Int, maxY: Int)

  case class GridState(rovers: Seq[MarsRover], grid: Grid)

  trait UserInterface {
    def display(gridState: GridState): String
  }


}
