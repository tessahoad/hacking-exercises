package cheatinghangman.model

import scala.io.StdIn
import scala.util.Random

trait Player {
  def guessLetter(): String
}

class HumanPlayer extends Player {
  override def guessLetter(): String = {
    StdIn.readLine("Guess :").toLowerCase
  }
}

class ComputerPlayer extends Player {
  override def guessLetter(): String = {
    Random.alphanumeric.dropWhile(!_.isLower).head.toString
  }
}

