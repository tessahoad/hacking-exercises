package texasholdem.parttwo.userinterface

import texasholdem.parttwo.model._

trait UserInterface {
  def showPlayerHand(playerHand: Seq[Card], holeCards: Seq[Card]): String
  def showCommunityCards(cards: Seq[Card]): String
}

class ConsoleUI extends UserInterface {

  override def showCommunityCards(cards: Seq[Card]): String = {
    println("Community Cards: ")
    cards.foreach(card => {
      print(s"${displayCard(card)}    ")
    })
    println()

    "to be implemented"
  }

  override def showPlayerHand(playerHand: Seq[Card], holeCards: Seq[Card]): String = {
    println("Player Hand: ")
    print("Community: ")
    playerHand.filter(card => !holeCards.contains(card)).foreach(card => {
      print(s"${displayCard(card)}    ")
    })
    println()
    print("Hole: ")
    holeCards.foreach(card => {
      print(s"       ${displayCard(card)}")
    })
    println()

    "to be implemented"
  }

  private def displayCard(card: Card) = {
    s"${rankToDisplayMapping(card.rank)} ${suitToDisplayMapping(card.suit)}"
  }

  private val suitToDisplayMapping = Map(
    (Clubs, "\u2663"),
    (Diamonds, "\u2666"),
    (Hearts, "\u2665"),
    (Spades, "\u2664")
  )

  private val rankToDisplayMapping = Map(
    (Two, "2"),
    (Three, "3"),
    (Four, "4"),
    (Five, "5"),
    (Six, "6"),
    (Seven, "7"),
    (Eight, "8"),
    (Nine, "9"),
    (Ten, "10"),
    (Jack, "J"),
    (Queen, "Q"),
    (King, "K"),
    (Ace, "A")
  )
}
