package talkingclock

object TakingClockApp {
  val inputData = Seq(
    "00:00",
    "01:30",
    "12:05",
    "14:01",
    "20:29",
    "21:00",
    "23:12"
  )

  def main(args: Array[String]): Unit = {
    val myUI = ConsoleUI
    val clocks = inputData.map(timeString => Clock.fromString(timeString))
    clocks.foreach(clock => myUI.tellTime(clock.myTimeAsString()))
  }
}
