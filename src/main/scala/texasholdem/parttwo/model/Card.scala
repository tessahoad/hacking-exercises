package texasholdem.parttwo.model

case class Card(rank: Rank, suit: Suit)

object Card {
  val standardDeck: Seq[Card] = for {
    suit <- Seq(Clubs, Diamonds, Hearts, Spades)
    rank <- Seq(Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace)
  } yield Card(rank, suit)

  val rankToValue = Map(
    Two -> 2,
    Three -> 3,
    Four -> 4,
    Five -> 5,
    Six -> 6,
    Seven -> 7,
    Eight -> 8,
    Nine -> 9,
    Ten -> 10,
    Jack -> 11,
    Queen -> 12,
    King -> 13,
    Ace -> 14
  )
}

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

