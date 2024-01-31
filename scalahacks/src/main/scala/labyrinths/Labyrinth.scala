package labyrinths

import labyrinths.Labyrinth.Traversed

import scala.util.Random

case class Labyrinth(cells: Seq[(Cell, Traversed)],
                     currentCell: Cell) {

  def getHeight(): Int = {
    cells.maxBy(_._1.yCoord)._1.yCoord + 1
  }

  def getWidth(): Int = {
    cells.maxBy(_._1.xCoord)._1.xCoord + 1
  }

  def isFullyTraversed: Boolean = {
    !cells.exists { case (_, traversed) => !traversed }
  }

  def markCellAsTraversed(cellToMark: Cell): Labyrinth = {
    val cellsToKeepTheSame = cells.filterNot { case (cell, traversed) => cell.xCoord == cellToMark.xCoord && cell.yCoord == cellToMark.yCoord && !traversed }
    val cellWithUpdatedWalls = if (cellToMark.isVerticallyAdjacent(currentCell)) {
      if (currentCell.yCoord - 1 == cellToMark.yCoord) cellToMark.copy(firstVisitedFrom = Some(BELOW)) else cellToMark.copy(firstVisitedFrom = Some(ABOVE))
    } else if (cellToMark.isHorizontallyAdjacent(currentCell)) {
      if (currentCell.xCoord - 1 == cellToMark.xCoord) cellToMark.copy(firstVisitedFrom = Some(LEFT)) else cellToMark.copy(firstVisitedFrom = Some(RIGHT))
    } else {
      currentCell
    }
    Labyrinth(cellsToKeepTheSame ++ Seq((cellWithUpdatedWalls, true)), cellWithUpdatedWalls)
  }

  def backtrackToTraversableCell(): Labyrinth = {
    val traversableCells = cells.filter { case (cell, traversed) => traversed && canTraverseFromCell(cell) }
    this.copy(currentCell = traversableCells.last._1)
  }

  def canTraverseFromCell(cellToTraverseFrom: Cell): Boolean = {
    cells.exists { case (cell, traversed) =>
      !traversed && cell.isAdjacent(cellToTraverseFrom)
    }
  }

  def traverseFromCurrentCell(random: Random): Labyrinth = {
    val start = cells.find { case (cell, _) => cell.xCoord == currentCell.xCoord && cell.yCoord == currentCell.yCoord }
      .getOrElse(throw new RuntimeException())._1
    if (canTraverseFromCell(currentCell)) {
      val traversableAdjacentCells = cells.filter { case (cell, traversed) =>
        !traversed && cell.isAdjacent(start)
      }
      val randomTraversableAdjacentCell = traversableAdjacentCells(random.nextInt(traversableAdjacentCells.length))
      this.markCellAsTraversed(randomTraversableAdjacentCell._1)
    } else {
      backtrackToTraversableCell().traverseFromCurrentCell(random)
    }
  }
}

object Labyrinth {

  type Traversed = Boolean

  def untraversedLabyrinth(width: Int, height: Int, random: Random): Labyrinth = {
    val randomStart = (random.nextInt(width), random.nextInt(height))
    val startingCell = Cell(randomStart._1, randomStart._2)
    val cells = for (i <- 0 until width;
                     j <- 0 until height)
    yield Cell(i, j)
    Labyrinth(cells.map(cell => (cell, false)), startingCell).markCellAsTraversed(startingCell)
  }
}
