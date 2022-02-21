package cheatinghangman

import cheatinghangman.display.ConsoleUI
import cheatinghangman.model._

object Application {
  def main(args: Array[String]): Unit = {
    val player = new HumanPlayer
    val executioner = new CheatingExecutioner
    val wordSet = new OnlineDictionary().getWords(5)
    val initialWord = executioner.pickWord(wordSet)
    val userInterface = new ConsoleUI
    val initialState = new GameState(initialWord, Set(), Set(), InProgress)

    val game = new HangmanGame(player, executioner, wordSet, Seq(initialState))
    val states = Iterator.iterate(game)(_.playRound()).takeWhile(game => game.turns.last.status != GameFinished)
    states.foreach(s => userInterface.display(s.turns.last))
  }
}
