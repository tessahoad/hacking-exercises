package talkingclock

import talkingclock.Clock._

case class Clock(hours: Int, minutes: Int) {
  def myTimeAsString(): String = {
    val amPm = if (isAm()) am else pm
    s"It's ${myHoursAsString()} ${myMinutesAsString()} $amPm"
  }

  def myHoursAsString(): String = {
    translateMinutes(hours % 12)
  }

  def myMinutesAsString(): String = {
    if (minutes == 0) {
      ""
    } else if (minutes < 10) {
      val minutesString = translateMinutes(minutes)
      s"oh $minutesString"
    } else if (minutes < 20) {
      val minutesString = translateMinutes(minutes)
      s"$minutesString"
    } else {
      val littleMinutes = minutes % 10
      val bigMinutes = minutes - littleMinutes
      val littleMinutesString = littleMinutes match {
        case 0 => ""
        case _ => translateMinutes(littleMinutes)
      }
      val bigMinutesString = translateMinutes(bigMinutes)
      s"$bigMinutesString $littleMinutesString"
    }
  }

  def isAm(): Boolean = {
    hours < 12
  }
}

object Clock {
  val pm = "pm"
  val am = "am"

  val numbersAsWords = Map(
    0 -> "twelve",
    1 -> "one",
    2 -> "two",
    3 -> "three",
    4 -> "four",
    5 -> "five",
    6 -> "six",
    7 -> "seven",
    8 -> "eight",
    9 -> "nine",
    10 -> "ten",
    11 -> "eleven",
    12 -> "twelve",
    13 -> "thirteen",
    14 -> "fourteen",
    15 -> "fifteen",
    16 -> "sixteen",
    17 -> "seventeen",
    18 -> "eighteen",
    19 -> "nineteen",
    20 -> "twenty",
    30 -> "thirty",
    40 -> "forty",
    50 -> "fifty"
  )

  def fromString(time: String): Clock = {
    val times = time.split(":")
    val hourString = times(0).toInt
    val minuteString = times(1).toInt

    Clock(hourString, minuteString)
  }


  def translateMinutes(minutes: Int) = {
    numbersAsWords.get(minutes) match {
      case Some(value) => value
      case None => throw new RuntimeException("🤠")
    }
  }
}
