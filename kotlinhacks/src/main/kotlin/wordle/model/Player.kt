package wordle.model

import wordle.GameDictionary
import wordle.Word
import wordle.model.LetterState.*
import wordle.tail


interface Player {
    fun pickWord(playerView: PlayerView): Word
}

class HumanPlayer : Player {
    override fun pickWord(playerView: PlayerView): Word {
        return readLine()!!
    }
}

class ComputerPlayer(private val dictionary: GameDictionary) : Player {
    override fun pickWord(playerView: PlayerView): Word {
        val maybeWrongLetters =
            playerView.userGuesses.flatMap { guess ->
                guess.wordState.filter { it.second == WRONG_GUESS }.map { it.first }
            }.distinct()

        val correctLetters =
            playerView.userGuesses.flatMap { guess ->
                guess.wordState.filter {
                    it.second in setOf(
                        CORRECT_PLACE,
                        INCORRECT_PLACE
                    )
                }.map { it.first }
            }.distinct()

        val wrongLetters = maybeWrongLetters - correctLetters.intersect(maybeWrongLetters)

        val availableWords = dictionary
            .filter { it.toCharArray().intersect(wrongLetters.toSet()).isEmpty() }
            .filter { it.toCharArray().toList().containsAll(correctLetters) }
            .filterNot { word -> playerView.userGuesses.map { it.toWord() }.contains(word) }

        val workingTrie = Trie.from(availableWords)
        return getWordFromTrie(workingTrie)
    }

    private fun getTrieSize(trie: Trie): Int {
        return trie.children.size + trie.children.map { getTrieSize(it.value) }.sum()
    }

    private fun getWordFromTrie(trie: Trie): Word {
        val biggestTrie = trie.children.maxBy { getTrieSize(it.value) }
        return if (biggestTrie == null) ""
        else biggestTrie.key + getWordFromTrie(biggestTrie.value)
    }

    data class Trie(val children: Map<Char, Trie>) {
        fun add(word: Word): Trie {
            if (word.isEmpty()) return this
            var trie = children.getOrDefault(word.first(), Trie(emptyMap()))
            val updatedChild = trie.add(word.tail())
            return trie.copy(children = children + mapOf(Pair(word.first(), updatedChild)))
        }

        companion object {
            fun from(words: List<Word>): Trie {
                return words.fold(Trie(emptyMap())) { trie, word -> trie.add(word) }
            }
        }
    }


}


class PlayerView(val userGuesses: List<Guess>)