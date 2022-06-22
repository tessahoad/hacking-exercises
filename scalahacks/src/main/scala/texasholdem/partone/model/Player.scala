package texasholdem.partone.model

import texasholdem.partone.model.Game

trait Player {
  val hand: Seq[Card]

  val money: Int

  def bet(table: Game): Int

  def dealHand(hand: Seq[Card]): Player
}

case class ComputerPlayer(hand: Seq[Card], money: Int) extends Player {
  override def bet(table: Game): Int = {
    throw new UnsupportedOperationException
  }

  override def dealHand(hand: Seq[Card]): Player = {
    copy(hand = hand)
  }
}