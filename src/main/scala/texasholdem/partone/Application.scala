package texasholdem.partone

import texasholdem.model.{ComputerPlayer, Deck, Game}
import texasholdem.model.Game.dealToPlayers
import texasholdem.userinterface.ConsoleUI

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
