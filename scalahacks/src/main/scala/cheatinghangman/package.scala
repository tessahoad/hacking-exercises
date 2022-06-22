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
}
