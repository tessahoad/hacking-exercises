package labyrinths

sealed trait VistorDirection

case object LEFT extends VistorDirection

case object RIGHT extends VistorDirection

case object ABOVE extends VistorDirection

case object BELOW extends VistorDirection

case class Cell(xCoord: Int,
                yCoord: Int,
                firstVisitedFrom: Option[VistorDirection] = None) {
  def isHorizontallyAdjacent(cell: Cell): Boolean = {
    (cell.xCoord == xCoord + 1 || cell.xCoord == xCoord - 1) && cell.yCoord == yCoord
  }

  def isVerticallyAdjacent(cell: Cell): Boolean = {
    (cell.yCoord == yCoord + 1 || cell.yCoord == yCoord - 1) && cell.xCoord == xCoord
  }

  def isAdjacent(cell: Cell): Boolean = {
    isHorizontallyAdjacent(cell) || isVerticallyAdjacent(cell)
  }
}
