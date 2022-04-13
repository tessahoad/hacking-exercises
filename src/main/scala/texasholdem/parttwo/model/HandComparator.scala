package texasholdem.parttwo.model

import texasholdem.parttwo.model.Card.rankToValue
import texasholdem.parttwo.model.HandType.groupCardsByRank

trait HandComparator {
  def compareHands(firstHand: Hand, secondHand: Hand): Result
}

object HandStrengthComparator extends HandComparator {
  override def compareHands(firstHand: Hand, secondHand: Hand): Result = {
    firstHand.handType.strength.compare(secondHand.handType.strength) match {
      case 1 => Wins
      case 0 => Draws
      case -1 => Loses
    }
  }
}

object HighCardComparator extends HandComparator {

  override def compareHands(firstHand: Hand, secondHand: Hand): Result = {
    val firstHandHighCard = firstHand.cards.maxBy(card => rankToValue.get(card.rank))
    val secondHandHighCard = secondHand.cards.maxBy(card => rankToValue.get(card.rank))
    rankToValue(firstHandHighCard.rank).compare(rankToValue(secondHandHighCard.rank)) match {
      case 1 => Wins
      case 0 => Draws
      case -1 => Loses
    }
  }
}

object PairComparator extends HandComparator {
  override def compareHands(firstHand: Hand, secondHand: Hand): Result = {
    val firstHandPair = groupCardsByRank(firstHand.cards).find{ case (_, cards) =>
      cards.size == 2
    }.get._2
    val secondHandPair = groupCardsByRank(secondHand.cards).find{ case (_, cards) =>
      cards.size == 2
    }.get._2

    HighCardComparator.compareHands(
      firstHand.copy(firstHandPair),
      secondHand.copy(secondHandPair)
    )
  }
}

object ThreeOfAKindComparator extends HandComparator{
  override def compareHands(firstHand: Hand, secondHand: Hand): Result = {
    val firstHandPair = groupCardsByRank(firstHand.cards).find{ case (_, cards) =>
      cards.size == 3
    }.get._2
    val secondHandPair = groupCardsByRank(secondHand.cards).find{ case (_, cards) =>
      cards.size == 3
    }.get._2

    HighCardComparator.compareHands(
      firstHand.copy(firstHandPair),
      secondHand.copy(secondHandPair)
    )
  }
}

object FourOfAKindComparator extends HandComparator {
  override def compareHands(firstHand: Hand, secondHand: Hand): Result = {
    val firstHandPair = groupCardsByRank(firstHand.cards).find{ case (_, cards) =>
      cards.size == 4
    }.get._2
    val secondHandPair = groupCardsByRank(secondHand.cards).find{ case (_, cards) =>
      cards.size == 4
    }.get._2

    HighCardComparator.compareHands(
      firstHand.copy(firstHandPair),
      secondHand.copy(secondHandPair)
    )
  }
}

object KickerComparator extends HandComparator {
  override def compareHands(firstHand: Hand, secondHand: Hand): Result = ???
}

object HandComparator {

  def getComparatorForHandType(handType: HandType): HandComparator = {
     handType match {
       case HighCard => HighCardComparator
       case Pair => PairComparator
       case TwoPair => HighCardComparator
       case ThreeOfAKind => ThreeOfAKindComparator
       case Straight => HighCardComparator
       case Flush => HighCardComparator
       case FullHouse => HighCardComparator
       case FourOfAKind => FourOfAKindComparator
       case StraightFlush => HighCardComparator
       case RoyalFlush => HighCardComparator
     }
  }
}
