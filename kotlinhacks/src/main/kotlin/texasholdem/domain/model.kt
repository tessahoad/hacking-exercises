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

    fun dealFrom(numCards: Int): Pair<Deck, List<Card>>
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

    override fun dealFrom(numCards: Int): Pair<Deck, List<Card>> {
        val dealtCards = cards.take(numCards)
        val newDeck = cards.takeLast(cards.size - 2)
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

enum class Hand(val rank: Int) {
    ROYAL_FLUSH(10), STRAIGHT_FLUSH(9), FOUR_OF_A_KIND(8), FULL_HOUSE(7), FLUSH(6),
    STRAIGHT(5), THREE_OF_A_KIND(4), TWO_PAIR(3), PAIR(2), HIGH_CARD(1)
}