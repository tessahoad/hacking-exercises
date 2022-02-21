package cheatinghangman.display

import cheatinghangman.model.{GameState, InProgress, Lost, Won}

class ConsoleUI extends UserInterface {

  val state_0 =
    """
      |
      |
      |
      |
      |
      |
      |
      |""".stripMargin

  val state_1 =
    """
      |
      |
      |
      |
      |
      |
      |================
      |""".stripMargin

  val state_2 =
    """
      |
      |            |
      |            |
      |            |
      |            |
      |            |
      |================
      |""".stripMargin

  val state_3 =
    """
      |    +_______+
      |            |
      |            |
      |            |
      |            |
      |            |
      |================
      |""".stripMargin

  val state_4 =
    """
      |    +_______+
      |           \|
      |            |
      |            |
      |            |
      |            |
      |================
      |""".stripMargin

  val state_5 =
    """
      |    +_______+
      |    |      \|
      |            |
      |            |
      |            |
      |            |
      |================
      |""".stripMargin

  val state_6 =
    """
      |    +_______+
      |    |      \|
      |    0       |
      |            |
      |            |
      |            |
      |================
      |""".stripMargin

  val state_7 =
    """
      |    +_______+
      |    |      \|
      |    0       |
      |    |       |
      |            |
      |            |
      |================
      |""".stripMargin

  val state_8 =
    """
      |    +_______+
      |    |      \|
      |    0       |
      |    |\      |
      |            |
      |            |
      |================
      |""".stripMargin

  val state_9 =
    """
      |    +_______+
      |    |      \|
      |    0       |
      |   /|\      |
      |            |
      |            |
      |================
      |""".stripMargin

  val state_10 =
    """
      |    +_______+
      |    |      \|
      |    0       |
      |   /|\      |
      |     \      |
      |            |
      |================
      |""".stripMargin

  val state_11 =
    """
      |    +_______+
      |    |      \|
      |    0       |
      |   /|\      |
      |   / \      |
      |            |
      |================
      |""".stripMargin

  val stateDisplays = Seq(state_0, state_1, state_2, state_3, state_4, state_5, state_6,
    state_7, state_8, state_9, state_10, state_11)

  override def display(state: GameState): Unit = {
    state.status match {
      case InProgress =>
        println
        println(s"Wrong letters: ${state.incorrectGuesses.mkString(",")}")
        println
        println(s"Word: ${getTransformedWord(state.guessWord, state.correctGuesses)}")
        println
        println(stateDisplays(state.incorrectGuesses.size))
      case Won =>
        println
        println(s"Winner winner chicken dinner!!! Word was ${state.guessWord}")
        println
        println(stateDisplays(state.incorrectGuesses.size))
      case Lost =>
        println
        println(s"You lose!!! Word was ${state.guessWord}")
        println
        println(state_11)
      case _ =>
        println()
    }
  }

  def getTransformedWord(word: String, revealedLetters: Set[Char]): String = {
    word.map(x => if (revealedLetters.contains(x)) x else "-").mkString
  }
}
