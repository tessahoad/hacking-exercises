package texasholdem

import texasholdem.domain.GameState
import texasholdem.domain.Round
import texasholdem.userinterfaces.ConsoleUI

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val newGame = GameState.newStandardGame()

        val gameStates = generateSequence(newGame){ TexasHoldEmGame.play(it) }
            .take(Round.entries.size)
            .forEach { ConsoleUI.display(it) }
    }
}