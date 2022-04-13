package texasholdem.model

import texasholdem.model.{Card, Player}

case class Game(deck: Deck,
                players: Seq[Player],
                tableCards: Seq[Card]) {

  def playRound(): Game = {

    if (getFlop().isEmpty) {
      val tuple = deck.cards.splitAt(3)
      val newTableCards = tableCards ++ tuple._1
      val newDeck = deck.discardFromTop()
      return Game(newDeck, players, newTableCards)
    } else if (getTurn().isEmpty) {
      val tuple = deck.cards.splitAt(1)
      val newTableCards = tableCards ++ tuple._1
      val newDeck = deck.discardFromTop()
      return Game(newDeck, players, newTableCards)
    } else if (getRiver().isEmpty) {
      val tuple = deck.cards.splitAt(1)
      val newTableCards = tableCards ++ tuple._1
      val newDeck = deck.discardFromTop()
      return Game(newDeck, players, newTableCards)
    }

    Game(deck, players, tableCards)
  }

  def getFlop(): Option[Seq[Card]] = {
    if (tableCards.length >= 3) Some(tableCards.take(3)) else None
  }

  def getTurn(): Option[Card] = {
    if (tableCards.length >= 4) Some(tableCards(3)) else None
  }

  def getRiver(): Option[Card] = {
    if (tableCards.length >= 5) Some(tableCards(4)) else None
  }
}

object Game {
  def dealToPlayers(game: Game): Game = {



    // look at foldLeft
    val playerWithNoCards = game.players.find(p => p.hand.isEmpty).getOrElse(return game)

    val tuple = game.deck.cards.splitAt(2)
    val playerWithHand = playerWithNoCards.dealHand(tuple._1)
    val newTable = game.copy(
      deck = game.deck.discardFromTop(),
      players = game.players.map(p => if (p.equals(playerWithNoCards)) playerWithHand else p)
    )
    dealToPlayers(newTable)
  }
}
