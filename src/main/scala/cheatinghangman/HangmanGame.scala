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
      val status = if (guessWord.forall(c => (previousRound.correctGuesses + playerGuess).contains(c))) Won else InProgress
      val latestState = new GameState(newWord, previousRound.incorrectGuesses, previousRound.correctGuesses + playerGuess, status)
      new HangmanGame(player, executioner, wordSet, turns.appended(latestState))
    }
  }
}
