package texasholdem

import texasholdem.domain.GameState
import texasholdem.domain.Player
import texasholdem.domain.Round

object TexasHoldEmGame {

    fun play(state: GameState): GameState {
        return when (state.round) {
            Round.HOLE -> stateAfterHole(state)
            Round.FLOP -> stateAfterFlop(state)
            Round.TURN -> stateAfterTurn(state)
            Round.RIVER -> stateAfterRiver(state)
            Round.REVEAL -> state
            else -> {
                throw RuntimeException("bork")
            }
        }
    }

    private fun stateAfterHole(state: GameState): GameState {
        return dealHoleCards(state).copy(round = Round.FLOP)
    }

    private fun dealHoleCards(state: GameState): GameState {
        return state.players.fold(state) { gameState: GameState, player: Player ->
            val (newDeck, holeCards) = gameState.deck.dealFrom(2)
            val hole = Pair(holeCards.first(), holeCards.last())
            state.copy(deck = newDeck, holeCards = gameState.holeCards.plus(Pair(player,hole)))
        }
    }

    private fun stateAfterFlop(state: GameState): GameState {
        return dealCommunityCards(state).copy(round = Round.TURN)
    }

    private fun stateAfterTurn(state: GameState): GameState {
        return dealCommunityCards(state).copy(round = Round.RIVER)
    }

    private fun stateAfterRiver(state: GameState): GameState {
        return dealCommunityCards(state).copy(round = Round.REVEAL)
    }

    private fun dealCommunityCards(gameState: GameState): GameState {
        val (newDeck, communityCard) = gameState.deck.dealFrom(1)
        return gameState.copy(deck = newDeck, communityCards = gameState.communityCards + communityCard)
    }
}