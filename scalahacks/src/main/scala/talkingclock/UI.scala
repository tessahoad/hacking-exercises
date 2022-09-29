package talkingclock

trait UI {
  def tellTime(time: String): String
}

object ConsoleUI extends UI {
  override def tellTime(time: String): String = {
    println(time)
    time
  }
}

object SpeechUI extends UI {
  override def tellTime(time: String): String = {
    Runtime.getRuntime.exec(s"say --voice=Tessa $time")
    time
  }
}
