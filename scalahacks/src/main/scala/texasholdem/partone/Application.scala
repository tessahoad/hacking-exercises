package texasholdem.partone

import texasholdem.partone.model.Game.dealToPlayers
import texasholdem.partone.model._
import texasholdem.partone.userinterface.ConsoleUI

object Application {

  def main(args: Array[String]) = {

    val ui = new ConsoleUI

    val deck = Deck()
    val shuffledDeck = deck.shuffle()
    val numberOfPlayers = 4
    val startingMoney = 100
    val players = Array.fill(numberOfPlayers)(ComputerPlayer(Seq(), startingMoney)).toSeq

    val startingTable = dealToPlayers(Game(shuffledDeck, players, Seq()))

    val turns = Iterator.iterate(startingTable)(_.playRound()).take(4)
    turns.foreach(t => ui.display(t))
  }
}
