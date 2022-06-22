package texasholdem.parttwo.model

import texasholdem.parttwo.model.Card.rankToValue
import texasholdem.parttwo.model.HandType.{groupCardsByRank, groupCardsBySuit}

sealed trait HandType {
  val strength: Int

  def cardsFormType(cards: Seq[Card]): Boolean
}

object HighCard extends HandType {
  override val strength: Int = 1

  override def cardsFormType(cards: Seq[Card]): Boolean = {
    true
  }
}

object Pair extends HandType {
  override val strength: Int = 2

  override def cardsFormType(cards: Seq[Card]): Boolean = {
    val mostFrequentCard = groupCardsByRank(cards).maxBy { case (_, cards) =>
      cards.size
    }
    mostFrequentCard._2.size == 2
  }
}

object TwoPair extends HandType {
  override val strength: Int = 3

  override def cardsFormType(cards: Seq[Card]): Boolean = {
    val pairsInHand = groupCardsByRank(cards).filter { case (_, cards) =>
      cards.size == 2
    }
    pairsInHand.size == 2
  }
}

object ThreeOfAKind extends HandType {
  override val strength: Int = 4

  override def cardsFormType(cards: Seq[Card]): Boolean = {
    val mostFrequentCard = groupCardsByRank(cards).maxBy { case (_, cards) =>
      cards.size
    }
    mostFrequentCard._2.size == 3
  }
}

object Straight extends HandType {
  override val strength: Int = 5

  override def cardsFormType(cards: Seq[Card]): Boolean = {
    !cards.sortBy(card => rankToValue(card.rank))
      .sliding(2, 1).map {
      case Seq(x, y) => rankToValue(y.rank) - rankToValue(x.rank)
    }.exists(_ != 1)
  }
}

object Flush extends HandType {
  override val strength: Int = 6

  override def cardsFormType(cards: Seq[Card]): Boolean = {
    val mostFrequentSuit = groupCardsBySuit(cards).maxBy { case (_, cards) =>
      cards.size
    }
    mostFrequentSuit._2.size == cards.size
  }
}

object FullHouse extends HandType {
  override val strength: Int = 7

  override def cardsFormType(cards: Seq[Card]): Boolean = {
    val cardsByRank = groupCardsByRank(cards)
    val optionalGroupOfThree = cardsByRank.find { case (_, cards) =>
      cards.size == 3
    }
    val optionalGroupOfTwo = cardsByRank.find { case (_, cards) =>
      cards.size == 2
    }
    optionalGroupOfThree.isDefined && optionalGroupOfTwo.isDefined
  }
}

object FourOfAKind extends HandType {
  override val strength: Int = 8

  override def cardsFormType(cards: Seq[Card]): Boolean = {
    val mostFrequentCard = groupCardsByRank(cards).maxBy(firstHand => firstHand
      ._2.size)
    mostFrequentCard._2.size == 4
  }
}

object StraightFlush extends HandType {
  override val strength: Int = 9

  override def cardsFormType(cards: Seq[Card]): Boolean = {
    Flush.cardsFormType(cards) && Straight.cardsFormType(cards)
  }
}

object RoyalFlush extends HandType {
  override val strength: Int = 10

  override def cardsFormType(cards: Seq[Card]): Boolean = {
    StraightFlush.cardsFormType(cards) &&
      groupCardsByRank(cards).exists { case (rank, _) => rank.equals(Ace) } &&
      groupCardsByRank(cards).exists { case (rank, _) => rank.equals(Ten) }
  }
}

object HandType {
  def groupCardsByRank(cards: Seq[Card]): Map[Rank, Seq[Card]] = {
    cards.groupBy(card => card.rank)
  }

  def groupCardsBySuit(cards: Seq[Card]): Map[Suit, Seq[Card]] = {
    cards.groupBy(card => card.suit)
  }

  val handTypes = Set(HighCard, Pair, TwoPair, ThreeOfAKind, Straight, Flush, FullHouse, FourOfAKind, StraightFlush, RoyalFlush)
}

