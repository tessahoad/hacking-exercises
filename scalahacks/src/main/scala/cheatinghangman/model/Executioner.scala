package cheatinghangman.model

import cheatinghangman.HangmanGame

import scala.util.Random

trait Executioner {
  def pickWord(words: Set[String]): String = {
    Random.shuffle(words).head
  }

  def pickWord(hangmanGame: HangmanGame, newUserGuess: Char): String
}

class HonestExecutioner extends Executioner {
  override def pickWord(hangmanGame: HangmanGame, newUserGuess: Char): String = {
    hangmanGame.turns.last.guessWord
  }
}

class CheatingExecutioner extends Executioner {
  override def pickWord(hangmanGame: HangmanGame, newUserGuess: Char): String = {
      val wordSet = hangmanGame.wordSet
      val incorrectGuesses = hangmanGame.turns.last.incorrectGuesses
      val filteredWords = wordSet.filter(word => !word.exists(incorrectGuesses)).filter(word => !word.contains(newUserGuess))

      if (filteredWords.isEmpty) hangmanGame.turns.last.guessWord else Random.shuffle(filteredWords).head
  }
}
