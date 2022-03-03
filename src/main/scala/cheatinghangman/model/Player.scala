package cheatinghangman.model

import cheatinghangman.{GuessTooLongException, HangmanGame, InvalidCharacterException, RepeatedCharacterException}

import scala.collection.mutable
import scala.io.StdIn
import scala.util.Random

trait Player {
  def guessLetter(correctGuesses: Set[Char], incorrectGuesses: Set[Char], wordLength: Int): String
}

class HumanPlayer extends Player {
  override def guessLetter(correctGuesses: Set[Char], incorrectGuesses: Set[Char], wordLength: Int): String = {
    StdIn.readLine("Guess :").toLowerCase
  }
}

class ComputerPlayer(dictionary: Dictionary = new OnlineDictionary) extends Player {
  
  val guessableCharacters = "abcdefghijklmnopqrstuvwxyz"

//  basic impl
// ideas: abstract out a way of scoring a character based on list remaining valid guesses
  override def guessLetter(correctGuesses: Set[Char], incorrectGuesses: Set[Char], wordLength: Int): String = {
    val validWords = dictionary
      .getWords(wordLength)
      .map(word => word.toCharArray)
      .filter(word => word.forall(c => !incorrectGuesses.contains(c)))
      .filter(word => correctGuesses.subsetOf(word.toSet))
      .map(word => word.mkString)

    guessableCharacters
      .filter(c => !correctGuesses.contains(c))
      .map(char => (char, validWords.count(word => word.contains(char))))
      .maxBy(_._2)
      ._1
      .toString
  }
}

