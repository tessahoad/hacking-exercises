package texasholdem.parttwo

import texasholdem.parttwo.model.Card.standardDeck
import texasholdem.parttwo.model._
import texasholdem.parttwo.userinterface.ConsoleUI

import scala.util.Random

object Application {

  def main(args: Array[String]) = {
    val ui = new ConsoleUI
    val cards = standardDeck
    val shuffledCards = Random.shuffle(cards)

    val firstCut = shuffledCards.splitAt(2)
    val firstHandHoleCards = firstCut._1
    val deckRemainderAfterFirstCut = firstCut._2

    val secondCut = deckRemainderAfterFirstCut.splitAt(2)
    val secondHandHoleCards = secondCut._1
    val deckRemainderAfterSecondCut = secondCut._2

    val communityCards = deckRemainderAfterSecondCut.take(5)

    val firstHandCards = BestHandSelector.selectBestHand(communityCards, firstHandHoleCards)
    val secondHandCards = BestHandSelector.selectBestHand(communityCards, secondHandHoleCards)

    ui.showCommunityCards(communityCards)

    ui.showPlayerHand(firstHandCards, firstHandHoleCards)
    ui.showPlayerHand(secondHandCards, secondHandHoleCards)

    val result = BestHandSelector.selectBestOfTwoHands(firstHandCards, secondHandCards)

    println(s"Result comparing first hand to second: $result")
  }
}
