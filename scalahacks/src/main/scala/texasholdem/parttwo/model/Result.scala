package texasholdem.parttwo.model

sealed trait Result

object Wins extends Result
object Draws extends Result
object Loses extends Result
