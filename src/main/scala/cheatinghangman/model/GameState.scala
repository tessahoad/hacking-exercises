package cheatinghangman.model

sealed trait Status

case object InProgress extends Status

case object Lost extends Status

case object Won extends Status

case object GameFinished extends Status

case class GameState(guessWord: String,
                     incorrectGuesses: Set[Char],
                     correctGuesses: Set[Char],
                     status: Status) {
  def getAllGuesses() = {
    incorrectGuesses ++ correctGuesses
  }
}