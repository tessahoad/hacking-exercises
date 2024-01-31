package texasholdem.userinterfaces

import texasholdem.domain.GameState

interface UserInterface {
    fun display(gameState: GameState): GameState
}

object ConsoleUI : UserInterface {
    override fun display(gameState: GameState): GameState {
        println(gameState.round)

        gameState.players.map {
            val playersView = it.viewCards(gameState)
            println("${it.name}'s hole cards: ${playersView.hole}")
            it.viewCards(gameState)
        }

        println("Community Cards: ${gameState.communityCards}")
        return gameState
    }
}