package wordle

import wordle.userinterfaces.UserInterface
import wordle.model.*

typealias Word = String
typealias GameDictionary = List<Word>

object WordleGame {

    enum class GameState {
        IN_PROGRESS, WON, LOST
    }

    enum class GameMode {
        NORMAL, DEBUG
    }

    data class WordleState(
        val gameDictionary: GameDictionary,
        val userGuesses: List<Guess>,
        val maxTurns: Int,
        val gameState: GameState,
        val player: Player,
        val wordLength: Int,
        val ui: UserInterface,
        val validators: List<WordValidator>,
        val correctWord: Word,
        val gameMode: GameMode
    ) {
        fun playRound(): WordleState {
            if (gameMode == GameMode.DEBUG) {
                println("Winning word: $correctWord")
            }

            return if (userGuesses.size == maxTurns) {
                val finalState = this.copy(gameState = GameState.LOST)
                ui.displayResultMessage(finalState)
                finalState
            } else {
                val playerGuess = getValidWord(player, gameDictionary, userGuesses, validators)
                val guesses = userGuesses + Guess.fromPlayerInput(playerGuess, correctWord)
                if (playerGuess == correctWord) {
                    val finalState = this.copy(userGuesses = guesses, gameState = GameState.WON)
                    ui.displayResultMessage(finalState)
                    finalState
                } else this.copy(userGuesses = guesses)
            }
        }

        private fun getValidWord(
            player: Player,
            gameDictionary: GameDictionary,
            userGuesses: List<Guess>,
            validators: List<WordValidator>
        ): Word {
            ui.askForUserInput()
            val playerGuess = player.pickWord(PlayerView(userGuesses)).toUpperCase()
            val failedValidations =
                (validators + AlreadyGuessedWordsValidator(userGuesses.map { it.toWord() })).mapNotNull {
                    it.validate(playerGuess)
                }
            return if (failedValidations.isEmpty()) playerGuess else {
                ui.displayError(failedValidations)
                getValidWord(player, gameDictionary, userGuesses, validators)
            }
        }
    }
}

