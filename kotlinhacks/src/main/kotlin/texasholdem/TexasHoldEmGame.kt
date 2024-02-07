package texasholdem

import texasholdem.domain.GameState
import texasholdem.domain.Player
import texasholdem.domain.Round
import texasholdem.userinterfaces.ConsoleUI

object TexasHoldEmGame {

    fun play(state: GameState): GameState {
        return when (state.round) {
            Round.HOLE -> stateAfterHole(state)
            Round.FLOP -> stateAfterFlop(state)
            Round.TURN -> stateAfterTurn(state)
            Round.RIVER -> stateAfterRiver(state)
            Round.REVEAL -> reveal(state)
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
            val (newDeck, holeCards) = gameState.deck.burnAndDealFrom(2, 0)
            val hole = Pair(holeCards.first(), holeCards.last())
            state.copy(deck = newDeck, holeCards = gameState.holeCards.plus(Pair(player,hole)))
        }
    }

    private fun stateAfterFlop(state: GameState): GameState {
        return dealCommunityCards(state, 3).copy(round = Round.TURN)
    }

    private fun stateAfterTurn(state: GameState): GameState {
        return dealCommunityCards(state).copy(round = Round.RIVER)
    }

    private fun stateAfterRiver(state: GameState): GameState {
        return dealCommunityCards(state).copy(round = Round.REVEAL)
    }

    private fun reveal(state: GameState): GameState {
        val classifiedHands = state.holeCards.map { (player, hole) -> Pair(player, HandClassifier.classify(hole, state.communityCards))}.toMap()
        val winningHand = classifiedHands.maxBy { it.value.handType.rank }.value
        ConsoleUI.displayReveal(winningHand, classifiedHands.values.toList())
        return state
    }

    private fun dealCommunityCards(gameState: GameState, number: Int = 1): GameState {
        val (newDeck, communityCard) = gameState.deck.burnAndDealFrom(number, 1)
        return gameState.copy(deck = newDeck, communityCards = gameState.communityCards + communityCard)
    }
}