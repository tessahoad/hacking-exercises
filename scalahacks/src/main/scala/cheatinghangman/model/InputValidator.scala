package cheatinghangman.model

import cheatinghangman.{GuessTooLongException, InvalidCharacterException, RepeatedCharacterException}

trait InputValidator[T] {
  def validate(obj: T): Unit
}

class LengthValidator(length: Int) extends InputValidator[String] {
  override def validate(str: String): Unit = {
    if (str == null || str.isEmpty ||  str.length > length) throw new GuessTooLongException(s"Guess was ${str.length} in length. Can only be $length character(s)")
  }
}
class CharacterValidator(validCharacters: Array[Char]) extends InputValidator[Char] {
  override def validate(char: Char): Unit = {
    if (!validCharacters.contains(char)) throw new InvalidCharacterException(s"Guess $char was invalid. Must be in ${validCharacters.mkString(",")}")
  }
}
class RepeatedCharacterValidator(guessedCharacters: Array[Char]) extends InputValidator[Char] {
  override def validate(char: Char): Unit = {
    if (guessedCharacters.contains(char)) throw new RepeatedCharacterException(s"You already guessed $char. Please choose something other than ${guessedCharacters.mkString(",")}")
  }
}
