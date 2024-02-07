package texasholdem.domain

enum class Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES
}

enum class Rank(val value: Int) {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
    EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14)
}

enum class Round {
    HOLE, FLOP, TURN, RIVER, REVEAL
}

data class Card(val rank: Rank, val suit: Suit)

interface Deck {
    val cards: List<Card>
    fun shuffle(): Deck

    fun burnAndDealFrom(numCards: Int, cardsToBurn: Int): Pair<Deck, List<Card>>
}

data class StandardDeck(override val cards: List<Card>) : Deck {
    companion object {
        fun fullDeck(): StandardDeck {
            val cards = Rank.entries.flatMap { rank -> Suit.entries.map { suit: Suit -> Card(rank, suit) } }
            return StandardDeck(cards)
        }
    }

    override fun shuffle(): Deck {
        return this.copy(cards = cards.shuffled())
    }

    override fun burnAndDealFrom(numCards: Int, cardsToBurn: Int): Pair<Deck, List<Card>> {
        val deckAfterBurn = cards.takeLast(cards.size - cardsToBurn)
        val dealtCards = deckAfterBurn.take(numCards)
        val newDeck = deckAfterBurn.takeLast(deckAfterBurn.size - numCards)
        return Pair(this.copy(cards = newDeck), dealtCards)
    }
}


data class PlayerView(val hole: Pair<Card, Card>?, val communityCards: List<Card>)

interface Player {
    val name: String
    fun viewCards(gameState: GameState): PlayerView {
        val myHole = gameState.holeCards[this]
        return PlayerView(myHole, gameState.communityCards)
    }
}

data class ComputerPlayer(override val name: String) : Player

data class GameState(
    val round: Round?,
    val deck: Deck,
    val players: List<Player>,
    val holeCards: Map<Player, Pair<Card, Card>>,
    val communityCards: List<Card>
) {

    companion object {
        fun newStandardGame(): GameState {
            val computerPlayers = listOf(
                ComputerPlayer("player1"),
                ComputerPlayer("player2"),
                ComputerPlayer("player3")
            )

            return GameState(
                Round.HOLE,
                StandardDeck.fullDeck().shuffle(),
                computerPlayers,
                emptyMap(),
                emptyList()
            )
        }
    }
}