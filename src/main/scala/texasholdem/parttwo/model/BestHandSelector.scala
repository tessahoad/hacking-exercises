package texasholdem.parttwo.model

import texasholdem.parttwo.model.HandType.handTypes


object BestHandSelector {
  def selectBestHand(communityCards: Seq[Card], holeCards: Seq[Card]): Seq[Card] = {
    val bestHand = communityCards.combinations(3).map(combination => combination ++ holeCards).reduce((firstHandCards, secondHandCards) => {
      selectBestOfTwoHands(firstHandCards, secondHandCards)
    })
    bestHand
  }

  def selectBestOfTwoHands(firstHandCards: Seq[Card], secondHandCards: Seq[Card]): Seq[Card] = {
    val firstHandType = handTypes.filter(handType => handType.cardsFormType(firstHandCards)).maxBy(handType => handType.strength)
    val secondHandType = handTypes.filter(handType => handType.cardsFormType(secondHandCards)).maxBy(handType => handType.strength)
    val firstHand = Hand(firstHandCards, firstHandType)
    val secondHand = Hand(secondHandCards, secondHandType)

    val result = HandStrengthComparator.compareHands(firstHand, secondHand)

    if (result == Draws) {
      val comparator = HandComparator.getComparatorForHandType(firstHandType)
      val innerResult = comparator.compareHands(firstHand, secondHand)
      if (innerResult == Wins) firstHandCards else secondHandCards
    } else if (result == Wins) firstHandCards else secondHandCards
  }
}

