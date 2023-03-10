package labyrinths

trait UI {
  def display(labyrinth: Labyrinth): String
}

object ConsoleUI extends UI {

  private val verticalSeparator = "|"
  private val horizontalSeparator = "+---"
  private val columnLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

  override def display(labyrinth: Labyrinth): String = {
    val topLine = s"     ${Array.fill(labyrinth.getWidth())("*   ").zip(columnLetters.toCharArray).map { case (displayItem, letter) => letter + displayItem.tail }.mkString}\r\n"
    val topEdge = s"   ${Array.fill(labyrinth.getWidth())("+---").mkString}+\r\n"

    val rows = labyrinth.cells.groupBy { case (cell, traversed) => cell.yCoord }

    val rowLines = rows.map { case (rowNum, cells) =>
      val cellsDisplay = cells.sortBy(_._1.xCoord).map { case (cell, _) =>
        val cellCenterChar = if (cell == labyrinth.currentCell) "o" else " "
        if (cell.firstVisitedFrom.contains(LEFT)) s" $cellCenterChar  "
        else s" $cellCenterChar |"
      }.mkString
      val verticalBoundsDisplay = cells.sortBy(_._1.xCoord).map { case (cell, _) =>
        if (cell.firstVisitedFrom.contains(BELOW)) s"+   "
        else s"+---"
      }.mkString
      s" ${rowNum + 1} |$cellsDisplay\r\n   $verticalBoundsDisplay+\r\n"
    }.mkString

    val display = topLine + topEdge + rowLines
    println(display)
    Thread.sleep(1000)
    display
  }
}
