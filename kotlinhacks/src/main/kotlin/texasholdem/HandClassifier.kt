package texasholdem

import texasholdem.domain.Card
import texasholdem.domain.Rank

object HandClassifier {
    fun classify(hole: Pair<Card, Card>, communityCards: List<Card>): Hand {
        val possibleHands = communityCards.combinations(3).map { it + hole.first + hole.second }
        val classifiedHands = possibleHands.map { classify(it) }
        val bestHand = classifiedHands.maxBy { it.handType.rank }
        return bestHand
    }

    private fun classify(cards: List<Card>): Hand {
        val handType = HandType.entries.filter { it.formedFrom(cards) }.maxBy { it.rank }
        return Hand(handType, cards)
    }

    fun List<Card>.powerset(): List<List<Card>> {
        return powerset(this, listOf(listOf()))
    }

    fun List<Card>.combinations(n: Int): List<List<Card>> {
        return powerset().filter { it.size == n }
    }

    private tailrec fun powerset(left: List<Card>, acc: List<List<Card>>): List<List<Card>> = when {
        left.isEmpty() -> acc
        else -> powerset(left.drop(1), acc + acc.map { it + left.first() })
    }
}

data class Hand(val handType: HandType, val cards: List<Card>)

enum class HandType(val rank: Int) {
    ROYAL_FLUSH(10) {
        override fun formedFrom(cards: List<Card>): Boolean {
            return isFlush(cards) && isStraight(cards) && containsRank(cards, Rank.ACE)
        }

        override fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand {
            TODO("Not yet implemented")
        }
    }, STRAIGHT_FLUSH(9) {
        override fun formedFrom(cards: List<Card>): Boolean {
            return isFlush(cards) && isStraight(cards)
        }

        override fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand {
            TODO("Not yet implemented")
        }
    }, FOUR_OF_A_KIND(8) {
        override fun formedFrom(cards: List<Card>): Boolean {
            return groupOfRankN(cards, 4)
        }

        override fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand {
            TODO("Not yet implemented")
        }
    }, FULL_HOUSE(7) {
        override fun formedFrom(cards: List<Card>): Boolean {
            return cards.groupBy { it.rank }.any { it.value.size == 3 }
                    && cards.groupBy { it.rank }.any { it.value.size == 2 }
        }

        override fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand {
            TODO("Not yet implemented")
        }
    }, FLUSH(6) {
        override fun formedFrom(cards: List<Card>): Boolean {
            return isFlush(cards)
        }

        override fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand {
            TODO("Not yet implemented")
        }
    },
    STRAIGHT(5) {
        override fun formedFrom(cards: List<Card>): Boolean {
            return isStraight(cards)
        }

        override fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand {
            TODO("Not yet implemented")
        }
    }, THREE_OF_A_KIND(4) {
        override fun formedFrom(cards: List<Card>): Boolean {
            return groupOfRankN(cards, 3)
        }

        override fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand {
            TODO("Not yet implemented")
        }
    }, TWO_PAIR(3) {
        override fun formedFrom(cards: List<Card>): Boolean {
            return cards.groupBy { it.rank }.filter{ it.value.size == 2 }.size == 2
        }

        override fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand {
            TODO("Not yet implemented")
        }
    }, PAIR(2) {
        override fun formedFrom(cards: List<Card>): Boolean {
            return groupOfRankN(cards, 2)
        }

        override fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand {
            TODO("Not yet implemented")
        }
    }, HIGH_CARD(1) {
        override fun formedFrom(cards: List<Card>): Boolean {
            return true
        }

        override fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand {
            TODO("Not yet implemented")
        }
    };

    fun groupOfRankN(cards: List<Card>, n :Int): Boolean {
        return cards.groupBy { it.rank }.any{ it.value.size == n }
    }

    fun isFlush(cards: List<Card>): Boolean {
        return cards.groupBy { it.suit }.any{ it.value.size == 5 }
    }

    fun isStraight(cards: List<Card>): Boolean {
        val sortedCards = cards.sortedBy { -it.rank.value }
        return sortedCards.first().rank.value - sortedCards.last().rank.value == cards.size - 1
                && cards.groupBy { it.rank }.size == cards.size
    }

    fun containsRank(cards: List<Card>, rank: Rank): Boolean {
        return cards.any { it.rank == rank }
    }

    abstract fun formedFrom(cards: List<Card>): Boolean

    abstract fun winningOfType(pairOfHands: Pair<Hand, Hand>): Hand
}