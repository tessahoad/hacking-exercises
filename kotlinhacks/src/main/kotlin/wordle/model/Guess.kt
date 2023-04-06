package wordle.model

import wordle.Word
import wordle.WordleGame
import wordle.model.LetterState.*


enum class LetterState {
    CORRECT_PLACE, INCORRECT_PLACE, WRONG_GUESS, UNKNOWN
}
data class Guess(val wordState: List<Pair<Char, LetterState>>) {

    fun toWord(): Word {
        return wordState.map { it.first }.joinToString("")
    }

    companion object {
        fun fromPlayerInput(playerGuess: Word, correctWord: Word): Guess {

            val correctAndIncorrectLetters = playerGuess.zip(correctWord).map {
                if (it.first == it.second) Pair(it.first, CORRECT_PLACE)
                else if (!correctWord.contains(it.first)) Pair(it.first, WRONG_GUESS)
                else Pair(it.first, UNKNOWN)
            }

            val letterGuesses = correctAndIncorrectLetters.withIndex().map {
                if (it.value.second != UNKNOWN) it.value
                else {
                    val correctWordCharCount = correctWord.count { char -> char == it.value.first } - correctAndIncorrectLetters.count { pair -> pair.first == it.value.first && pair.second == CORRECT_PLACE }
                    val guessCharCount = playerGuess.substring(0, it.index).count { char -> char == it.value.first } - correctAndIncorrectLetters.count { pair -> pair.first == it.value.first && pair.second == CORRECT_PLACE }
                    if (guessCharCount < correctWordCharCount) Pair(it.value.first,
                        INCORRECT_PLACE
                    )
                    else Pair(it.value.first, WRONG_GUESS)
                }
            }

            return Guess(letterGuesses)
        }
    }

}