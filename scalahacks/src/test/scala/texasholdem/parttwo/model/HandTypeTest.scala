package texasholdem.parttwo.model

import org.scalatest.flatspec.AnyFlatSpec

class HandTypeTest extends AnyFlatSpec {
  behavior of "HighCard"

  it should "always return true for handFormsType" in {
    val hand = Seq(
      Card(Two, Diamonds),
      Card(Two, Clubs),
      Card(Two, Hearts),
      Card(Three, Diamonds),
      Card(Two, Spades)
    )
    assert(HighCard.cardsFormType(hand))
  }

  it should "return Wins when higher ranked card compared" in {
    val expectedWinningHand = Hand(
      Seq(
        Card(Ace, Diamonds),
        Card(Ten, Clubs),
        Card(Three, Hearts),
        Card(Four, Diamonds),
        Card(Five, Spades)
      ), HighCard
    )

    val expectedLosingHand = Hand(
      Seq(
        Card(Jack, Diamonds),
        Card(Nine, Clubs),
        Card(Three, Hearts),
        Card(Four, Diamonds),
        Card(Five, Spades)
      ), HighCard
    )

    assert(HighCardComparator.compareHands(expectedWinningHand, expectedLosingHand).equals(Wins))
  }

  it should "return Draws when same ranked card compared" in {
    val firstHand = Hand(Seq(
      Card(Ace, Diamonds),
      Card(Ten, Clubs),
      Card(Three, Hearts),
      Card(Four, Diamonds),
      Card(Five, Spades)
    ), HighCard)

    val secondHand = Hand(Seq(
      Card(Ace, Diamonds),
      Card(Nine, Clubs),
      Card(Three, Hearts),
      Card(Four, Diamonds),
      Card(Five, Spades)
    ), HighCard)
    assert(HighCardComparator.compareHands(firstHand, secondHand).equals(Draws))
  }

  it should "return Loses when lower ranked card compared" in {
    val expectedWinningHand = Hand(Seq(
      Card(Ace, Diamonds),
      Card(Ten, Clubs),
      Card(Three, Hearts),
      Card(Four, Diamonds),
      Card(Five, Spades)
    ), HighCard)
    val expectedLosingHand = Hand(Seq(
      Card(Jack, Diamonds),
      Card(Nine, Clubs),
      Card(Three, Hearts),
      Card(Four, Diamonds),
      Card(Five, Spades)
    ), HighCard)
    assert(HighCardComparator.compareHands(expectedLosingHand, expectedWinningHand).equals(Loses))
  }

//  behavior of "Pair"
//
//  it should "return true for handFormsType when two cards of same rank in hand" in {
//    val hand = Seq(
//      Card(Two, Diamonds),
//      Card(Two, Clubs),
//      Card(Three, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//    assert(Pair.handFormsType(hand))
//  }
//
//  it should "return false for handFormsType when not two cards of same rank in hand" in {
//    val hand = Seq(
//      Card(Ace, Diamonds),
//      Card(Two, Clubs),
//      Card(Three, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//    assert(!Pair.handFormsType(hand))
//  }
//
//  it should "return Wins when higher ranked pair compared" in {
//    val expectedWinningHand = Seq(
//      Card(Ace, Diamonds),
//      Card(Ace, Clubs),
//      Card(Three, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    val expectedLosingHand = Seq(
//      Card(Jack, Diamonds),
//      Card(Jack, Clubs),
//      Card(Three, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    assert(Pair.comparedWithSameType(expectedWinningHand, expectedLosingHand).equals(Wins))
//  }
//
//  it should "return Draws when same ranked pair compared" in {
//    val expectedWinningHand = Seq(
//      Card(Ace, Diamonds),
//      Card(Ace, Clubs),
//      Card(Three, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    val expectedLosingHand = Seq(
//      Card(Ace, Hearts),
//      Card(Ace, Spades),
//      Card(Three, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    assert(Pair.comparedWithSameType(expectedWinningHand, expectedLosingHand).equals(Draws))
//  }
//
//  it should "return Loses when lower ranked pair compared" in {
//    val expectedWinningHand = Seq(
//      Card(Ace, Diamonds),
//      Card(Ace, Clubs),
//      Card(Three, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    val expectedLosingHand = Seq(
//      Card(Jack, Diamonds),
//      Card(Jack, Clubs),
//      Card(Three, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    assert(Pair.comparedWithSameType(expectedLosingHand, expectedWinningHand).equals(Loses))
//  }
//
//  behavior of "Three of a Kind"
//
//  it should "return true for handFormsType when three cards of same rank in hand" in {
//    val hand = Seq(
//      Card(Two, Diamonds),
//      Card(Two, Clubs),
//      Card(Two, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//    assert(ThreeOfAKind.handFormsType(hand))
//  }
//
//  it should "return false for handFormsType when not three cards of same rank in hand" in {
//    val hand = Seq(
//      Card(Ace, Diamonds),
//      Card(Two, Clubs),
//      Card(Three, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//    assert(!ThreeOfAKind.handFormsType(hand))
//  }
//
//  it should "return Wins when higher ranked three of a kind compared" in {
//    val expectedWinningHand = Seq(
//      Card(Ace, Diamonds),
//      Card(Ace, Clubs),
//      Card(Ace, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    val expectedLosingHand = Seq(
//      Card(Jack, Diamonds),
//      Card(Jack, Clubs),
//      Card(Jack, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    assert(ThreeOfAKind.comparedWithSameType(expectedWinningHand, expectedLosingHand).equals(Wins))
//  }
//
//  it should "return Draws when same ranked three compared" in {
//    val expectedWinningHand = Seq(
//      Card(Ace, Diamonds),
//      Card(Ace, Clubs),
//      Card(Ace, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    val expectedLosingHand = Seq(
//      Card(Ace, Hearts),
//      Card(Ace, Spades),
//      Card(Ace, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    assert(ThreeOfAKind.comparedWithSameType(expectedWinningHand, expectedLosingHand).equals(Draws))
//  }
//
//  it should "return Loses when lower ranked three compared" in {
//    val expectedWinningHand = Seq(
//      Card(Ace, Diamonds),
//      Card(Ace, Clubs),
//      Card(Ace, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    val expectedLosingHand = Seq(
//      Card(Jack, Diamonds),
//      Card(Jack, Clubs),
//      Card(Jack, Hearts),
//      Card(Four, Diamonds),
//      Card(Five, Spades)
//    )
//
//    assert(ThreeOfAKind.comparedWithSameType(expectedLosingHand, expectedWinningHand).equals(Loses))
//  }
//
//  behavior of "Four of a Kind"
//
//  it should "return true for handFormsType when four cards of same rank in hand" in {
//    val hand = Seq(
//      Card(Two, Diamonds),
//      Card(Two, Clubs),
//      Card(Two, Hearts),
//      Card(Four, Diamonds),
//      Card(Two, Spades)
//    )
//    assert(FourOfAKind.handFormsType(hand))
//  }
//
//  it should "return false for handFormsType when not four cards of same rank in hand" in {
//    val hand = Seq(
//      Card(Ace, Diamonds),
//      Card(Two, Clubs),
//      Card(Two, Hearts),
//      Card(Two, Diamonds),
//      Card(Ace, Spades)
//    )
//    assert(!FourOfAKind.handFormsType(hand))
//  }
//
//  it should "return Wins when higher ranked four compared" in {
//    val expectedWinningHand = Seq(
//      Card(Ace, Diamonds),
//      Card(Ace, Clubs),
//      Card(Ace, Hearts),
//      Card(Four, Diamonds),
//      Card(Ace, Spades)
//    )
//
//    val expectedLosingHand = Seq(
//      Card(Jack, Diamonds),
//      Card(Jack, Clubs),
//      Card(Jack, Hearts),
//      Card(Four, Diamonds),
//      Card(Jack, Spades)
//    )
//
//    assert(FourOfAKind.comparedWithSameType(expectedWinningHand, expectedLosingHand).equals(Wins))
//  }
//
//  it should "return Draws when same ranked four compared" in {
//    val expectedWinningHand = Seq(
//      Card(Ace, Diamonds),
//      Card(Ace, Clubs),
//      Card(Ace, Hearts),
//      Card(Four, Diamonds),
//      Card(Ace, Spades)
//    )
//
//    val expectedLosingHand = Seq(
//      Card(Ace, Hearts),
//      Card(Ace, Spades),
//      Card(Three, Hearts),
//      Card(Ace, Diamonds),
//      Card(Ace, Spades)
//    )
//
//    assert(FourOfAKind.comparedWithSameType(expectedWinningHand, expectedLosingHand).equals(Draws))
//  }
//
//  it should "return Loses when lower ranked four compared" in {
//    val expectedWinningHand = Seq(
//      Card(Ace, Diamonds),
//      Card(Ace, Clubs),
//      Card(Three, Hearts),
//      Card(Ace, Diamonds),
//      Card(Ace, Spades)
//    )
//
//    val expectedLosingHand = Seq(
//      Card(Jack, Diamonds),
//      Card(Jack, Clubs),
//      Card(Jack, Hearts),
//      Card(Jack, Diamonds),
//      Card(Five, Spades)
//    )
//
//    assert(FourOfAKind.comparedWithSameType(expectedLosingHand, expectedWinningHand).equals(Loses))
//  }
}
