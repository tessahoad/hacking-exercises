package texasholdem.partone.model

import texasholdem.partone.model.Deck.standardCards

import scala.util.Random

sealed trait Suit

object Clubs extends Suit
object Diamonds extends Suit
object Hearts extends Suit
object Spades extends Suit

sealed trait Rank

object Two extends Rank
object Three extends Rank
object Four extends Rank
object Five extends Rank
object Six extends Rank
object Seven extends Rank
object Eight extends Rank
object Nine extends Rank
object Ten extends Rank
object Jack extends Rank
object Queen extends Rank
object King extends Rank
object Ace extends Rank

case class Card(suit: Suit, rank: Rank)

case class Deck(cards: Seq[Card] = standardCards) {
  def shuffle(): Deck = {
    Deck(Random.shuffle(cards))
  }

  def discardFromTop(): Deck = {
    Deck(cards.tail)
  }
}

object Deck {

  val standardCards = for (
    suit <- List(Clubs, Diamonds, Hearts, Spades);
    rank <- List(Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King)
  ) yield Card(suit, rank)

}