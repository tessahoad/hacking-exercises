package adventofcode.day5

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Application extends App {
  private val input: Array[String] = Source.fromResource("adventofcode/day4/input.txt").getLines().toArray

  class Entry(val value: Int, var matched: Boolean = false) {
    def markMatched(): Unit = {
      matched = true;
    }
  }

  class BingoCard(val rows: ArrayBuffer[Array[Entry]]) {
    def hasBingo(): Boolean = {
      val columns = rows.transpose(x => x)
      for (i <- 0 until 5) {
        if (rows(i).count(e => e.matched) == 5 || columns(i).count(e => e.matched) == 5) return true
      }
      false
    }

    def markMatch(value: Int) = {
      rows.flatten.filter(e => e.value == value).foreach(e => e.markMatched())
    }
  }

  def getBingoCalls() = {
    input.head.split(",").map(_.toInt)
  }

  def parseInput() = {
    val unparsedRows: ArrayBuffer[String] = ArrayBuffer.from(input).filter(c => c != "")
    unparsedRows.remove(0)

    val parsedRows = ArrayBuffer[Array[Entry]]()

    for (i <- 0 until unparsedRows.length) {
      val row = unparsedRows(i).split(" ").filter(c => c != "").map(r => new Entry(r.toInt))
      parsedRows.append(row)
    }

    val cards = ArrayBuffer[BingoCard]()

    while (parsedRows.nonEmpty) {
      cards.append(new BingoCard(parsedRows.take(5)))
      parsedRows.remove(0, 5)
    }

    cards
  }

  def getWinner(calls: Array[Int], cards: Array[BingoCard]): (BingoCard, Int) = {
    for (i <- calls.indices) {
      cards.foreach(card => {
        val call = calls(i)
        card.markMatch(call)
        if (card.hasBingo()) return (card, call)
      })
    }
    throw new RuntimeException("Should have been a winner")
  }

  def getLastPlace(calls: Array[Int], cards: ArrayBuffer[BingoCard]): BingoCard = {
    var bingoCards = cards

    for (i <- calls.indices) {
      val call = calls(i)
      bingoCards.foreach(card => {
        card.markMatch(call)
      })
      bingoCards = bingoCards.filter(card => !card.hasBingo())
      if (bingoCards.length == 1) {
        return bingoCards(0)
      }
    }
    throw new RuntimeException("Should have been a loser")
  }

  def calculateScore(card: BingoCard, call: Int) = {
    val sumOfUnmatched = card.rows.flatten(r => r).filter(e => !e.matched).map(_.value).sum
    sumOfUnmatched.*(call)
  }

  private val winningCardAndCall: (BingoCard, Int) = getWinner(getBingoCalls(), parseInput().toArray)
  println("Score was :" + calculateScore(winningCardAndCall._1, winningCardAndCall._2))

  private val losingCardAndCall: (BingoCard, Int) = getWinner(getBingoCalls(), Array(getLastPlace(getBingoCalls(), parseInput())))
  println("Score was :" + calculateScore(losingCardAndCall._1, losingCardAndCall._2))
}
