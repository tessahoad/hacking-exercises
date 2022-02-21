package cheatinghangman.model

sealed trait Status

case object InProgress extends Status
case object Lost extends Status
case object Won extends Status
case object GameFinished extends Status

class GameState(val guessWord: String,
                val incorrectGuesses: Set[Char],
                val correctGuesses: Set[Char],
                val status: Status) {
  def getAllGuesses() = {
    incorrectGuesses ++ correctGuesses
  }
}