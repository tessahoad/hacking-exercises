package marsrover.domain

object model {

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

  final val origin = Coordinate(0, 0)

  case class Coordinate(x: Int, y: Int)

  case class Grid(minXY: Coordinate, maxXY: Coordinate)
}
