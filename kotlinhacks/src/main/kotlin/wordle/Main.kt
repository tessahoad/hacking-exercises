package wordle

import wordle.WordleGame.GameState.IN_PROGRESS
import wordle.userinterfaces.ConsoleUserInterface
import wordle.WordleGame.GameMode.DEBUG
import wordle.WordleGame.GameMode.NORMAL
import wordle.model.*

fun main(args: Array<String>) {
    val wordLength = 5
    val dictionary: GameDictionary = InternetWordSource().listWords(wordLength)
    val ui = ConsoleUserInterface()
    val player = HumanPlayer()
    val computerPlayer = ComputerPlayer(dictionary)
    val validators = listOf(LengthValidator(5), KnownWordsValidator(dictionary))
    val correctWord = dictionary.random()

    val initState = WordleGame.WordleState(
        dictionary, emptyList(), 6, IN_PROGRESS,
        computerPlayer, wordLength, ui, validators, correctWord, DEBUG
    )

    val gameStates = generateSequence(initState) { it.playRound() }.takeWhile { it.gameState == IN_PROGRESS }
    gameStates.forEach { ui.displayState(it) }
}

fun String.tail() = takeLast(length - 1)