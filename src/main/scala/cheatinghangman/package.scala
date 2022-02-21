import cheatinghangman.model.{CharacterValidator, LengthValidator, RepeatedCharacterValidator}

package object cheatinghangman {

  trait HangmanException extends RuntimeException {
    def getUserFeedback: String
  }
  class GuessTooLongException(userFeedback: String) extends HangmanException {
    override def getUserFeedback: String = userFeedback
  }
  class InvalidCharacterException(userFeedback: String) extends HangmanException {
    override def getUserFeedback: String = userFeedback
  }
  class RepeatedCharacterException(userFeedback: String) extends HangmanException {
    override def getUserFeedback: String = userFeedback
  }


  def getValidPlayerGuess(game: HangmanGame): Char = {
    val guess = game.player.guessLetter()
    val isValid = validateUserGuess(guess, game.turns.last.getAllGuesses())
    if (isValid) {
      guess(0)
    }
    else getValidPlayerGuess(game)
  }

  private def validateUserGuess(guess: String, guesses: Set[Char]): Boolean = {
    try {
      new LengthValidator(1).validate(guess)
      new CharacterValidator(('a' to 'z').toArray).validate(guess(0))
      new RepeatedCharacterValidator(guesses.toArray).validate(guess(0))
      true
    } catch {
//    TODO: bubble exceptions so visual display is
      case lengthException: GuessTooLongException => println(lengthException.getUserFeedback); false
      case invalidCharException: InvalidCharacterException => println(invalidCharException.getUserFeedback); false
      case characterAlreadyGuessedException: RepeatedCharacterException => println(characterAlreadyGuessedException.getUserFeedback); false
    }
  }
}
