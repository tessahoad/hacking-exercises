package wordle

object WordleExceptions {
  class WordleException extends RuntimeException
  class InvalidWordException extends WordleException
  class WordLengthException extends WordleException
}
