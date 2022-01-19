import wordle.WordleExceptions.{InvalidWordException, WordLengthException}

import scala.util.Random

package object wordle {
  private val listOfWords = scala.io.Source.fromURL("http://www.mieliestronk.com/corncob_caps.txt").mkString

  private def getWords(length: Int) = {
    listOfWords.split("\r\n").filter(s => s.length == 5)
  }

  def pickWord(length: Int): String = {
    val words = getWords(5)
    words(Random.nextInt(words.length))
  }

  private def validateKnownWord(word: String) = {
    if (!listOfWords.contains(word)) throw new InvalidWordException
  }

  private def validateLength(guess: String, length: Int): Unit = {
    if (guess.length != length) throw new WordLengthException
  }

  def validateGuess(guess: String, length: Int): Boolean = {
    try {
      validateLength(guess, length)
      validateKnownWord(guess)
      true
    } catch {
      case _: WordLengthException => println(guess + " is invalid, must be 5 letters long"); false
      case _: InvalidWordException => println(guess + " is invalid, must be a known word"); false
    }
  }
}
