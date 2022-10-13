package talkingclock

import java.text.SimpleDateFormat
import java.util.Calendar

object TakingClockApp {
  val now = Calendar.getInstance().getTime
  val inputFormat = new SimpleDateFormat("HH:mm")

  val inputData = Seq(
    "00:00",
    "01:30",
    "12:05",
    "14:01",
    "20:29",
    "21:00",
    "23:12",
    inputFormat.format(now)
  )

  def main(args: Array[String]): Unit = {
    val myUI = SpeechUI
    val clocks = inputData.map(timeString => Clock.fromString(timeString))
    clocks.foreach(clock => myUI.tellTime(clock.myTimeAsString()))
  }
}
