package wordle.model

import wordle.GameDictionary
import wordle.Word


interface WordValidator {
    fun validate(word: Word): ValidationError?
}

class LengthValidator(private val length: Int) : WordValidator {
    override fun validate(word: Word): ValidationError? {
        return if (word.length == length) null else ValidationError("$word is thw wrong length, should be $length letters long")
    }
}

class KnownWordsValidator(private val dictionary: GameDictionary) : WordValidator {
    override fun validate(word: Word): ValidationError? {
        return if (dictionary.contains(word)) null else ValidationError("$word is unknown")
    }

}

class AlreadyGuessedWordsValidator(private val guessedWords: List<Word>) : WordValidator {
    override fun validate(word: Word): ValidationError? {
        return if (!guessedWords.contains(word)) null else ValidationError("$word has already been guessed")
    }

}


data class ValidationError(val errorMessage: String)