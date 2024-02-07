package texasholdem.userinterfaces

import texasholdem.Hand
import texasholdem.domain.GameState
import texasholdem.domain.Round

interface UserInterface {
    fun display(gameState: GameState): GameState
}

object ConsoleUI : UserInterface {
    override fun display(gameState: GameState): GameState {
        if (gameState.round != Round.REVEAL) {
            println(gameState.round)

            gameState.players.map {
                val playersView = it.viewCards(gameState)
                println("${it.name}'s hole cards: ${playersView.hole}")
                it.viewCards(gameState)
            }

            println("Community Cards: ${gameState.communityCards}")
        }
        return gameState
    }

    fun displayReveal(winningHand: Hand, allHands: List<Hand>): Unit {
        println("REVEAL")

        println("Winning Hand: $winningHand")
        println("All the hands:")
        allHands.forEach{println(it)}
    }
}