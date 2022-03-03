package cheatinghangman

import cheatinghangman.model._

class HangmanGame(val player: Player,
                  val executioner: Executioner,
                  val wordSet: Set[String],
                  val turns: Seq[GameState]) {

  val maximumIncorrectGuesses = 11

  def playRound(): HangmanGame = {
    val previousRound = turns.last
    val guessWord = previousRound.guessWord

    if (previousRound.status == Won || previousRound.status == Lost) {
      val latestState = new GameState(guessWord, previousRound.incorrectGuesses, previousRound.correctGuesses, GameFinished)
      return new HangmanGame(player, executioner, wordSet, turns.appended(latestState))
    }

    val playerGuess = getValidPlayerGuess(this)
    val newWord = executioner.pickWord(this, playerGuess)

    if (!guessWord.contains(playerGuess)) {
      val status = if (previousRound.incorrectGuesses.size + 1 == maximumIncorrectGuesses) Lost else InProgress
      val latestState = new GameState(guessWord, previousRound.incorrectGuesses + playerGuess, previousRound.correctGuesses, status)
      new HangmanGame(player, executioner, wordSet, turns.appended(latestState))
    } else if (!newWord.contains(playerGuess)) {
      val status = if (previousRound.incorrectGuesses.size + 1 == maximumIncorrectGuesses) Lost else InProgress
      val latestState = new GameState(newWord, previousRound.incorrectGuesses + playerGuess, previousRound.correctGuesses, status)
      new HangmanGame(player, executioner, wordSet, turns.appended(latestState))
    } else {
      val status = if (newWord.forall(c => (previousRound.correctGuesses + playerGuess).contains(c))) Won else InProgress
      val latestState = new GameState(newWord, previousRound.incorrectGuesses, previousRound.correctGuesses + playerGuess, status)
      new HangmanGame(player, executioner, wordSet, turns.appended(latestState))
    }
  }

  def getValidPlayerGuess(game: HangmanGame): Char = {
    val guess = game.player.guessLetter(game.turns.last.correctGuesses, game.turns.last.incorrectGuesses, game.turns.last.guessWord.length)
    val isValid = validatePlayerGuess(guess, game.turns.last.getAllGuesses())
    if (isValid) {
      guess(0)
    }
    else getValidPlayerGuess(game)
  }

  private def validatePlayerGuess(guess: String, guesses: Set[Char]): Boolean = {
    try {
      new LengthValidator(1).validate(guess)
      new CharacterValidator(('a' to 'z').toArray).validate(guess(0))
      new RepeatedCharacterValidator(guesses.toArray).validate(guess(0))
      true
    } catch {
      //    TODO: change so Display is responsible for display?
      case lengthException: GuessTooLongException => println(lengthException.getUserFeedback); false
      case invalidCharException: InvalidCharacterException => println(invalidCharException.getUserFeedback); false
      case characterAlreadyGuessedException: RepeatedCharacterException => println(characterAlreadyGuessedException.getUserFeedback); false
    }
  }
}
