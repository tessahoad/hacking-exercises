package wordle

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

object Application {

  object GameParameters {
    var wordLength = 5
    var guesses = ArrayBuffer[String]()
  }

  def getUserGuess(): String = {
    var isValid = false
    var guess = ""
    while (!isValid) {
      guess = StdIn.readLine(Console.WHITE + "Guess :").toUpperCase
      isValid = validateGuess(guess, GameParameters.wordLength)
    }
    guess
  }

  def printMatch(word: String, matchWord: String) = {
    for (i <- 0 until GameParameters.wordLength) {
      if (matchWord(i).equals(word(i))) print(Console.GREEN + word(i))
      else if (matchWord.contains(word(i))) print(Console.YELLOW + word(i))
      else print(Console.WHITE + word(i))
      Thread.sleep(200)
    }
    println()
  }

  def main(args: Array[String]): Unit = {
    val wordle = pickWord(GameParameters.wordLength)

    var turns = 0

    while (turns < 6) {
      val guess = getUserGuess()
      GameParameters.guesses += guess
      printMatch(guess, wordle)

      if (guess.contentEquals(wordle)) {
        println(Console.GREEN + "VICTORY")
        GameParameters.guesses.foreach(guess => printMatch(guess, wordle))
        return
      }

      turns += 1
    }


    println(Console.RED + "BAD LUCK. WORD WAS: " + Console.WHITE_B + wordle)
  }
}
