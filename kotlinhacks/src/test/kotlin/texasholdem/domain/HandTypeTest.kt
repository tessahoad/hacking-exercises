package texasholdem.domain

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import texasholdem.HandType

class HandTypeTest {

    @Test
    fun royalFlushFormedFromShouldReturnTrue() {
        // Given
        val royalFlush = listOf(
            Card(Rank.ACE, Suit.HEARTS),
            Card(Rank.KING, Suit.HEARTS),
            Card(Rank.QUEEN, Suit.HEARTS),
            Card(Rank.JACK, Suit.HEARTS),
            Card(Rank.TEN, Suit.HEARTS)
        )

        // When
        val actual = HandType.ROYAL_FLUSH.formedFrom(royalFlush)

        // Then
        assertTrue(actual)
    }

    @Test
    fun straightFlushFormedFromShouldReturnTrue() {
        // Given
        val royalFlush = listOf(
            Card(Rank.KING, Suit.HEARTS),
            Card(Rank.QUEEN, Suit.HEARTS),
            Card(Rank.JACK, Suit.HEARTS),
            Card(Rank.TEN, Suit.HEARTS),
            Card(Rank.NINE, Suit.HEARTS)
        )

        // When
        val actual = HandType.STRAIGHT_FLUSH.formedFrom(royalFlush)

        // Then
        assertTrue(actual)
    }

    @Test
    fun fourOfAKindFormedFromShouldReturnTrue() {
        // Given
        val royalFlush = listOf(
            Card(Rank.KING, Suit.HEARTS),
            Card(Rank.KING, Suit.CLUBS),
            Card(Rank.KING, Suit.DIAMONDS),
            Card(Rank.KING, Suit.SPADES),
            Card(Rank.NINE, Suit.HEARTS)
        )

        // When
        val actual = HandType.FOUR_OF_A_KIND.formedFrom(royalFlush)

        // Then
        assertTrue(actual)
    }

    @Test
    fun threeOfAKindFormedFromShouldReturnTrue() {
        // Given
        val royalFlush = listOf(
            Card(Rank.KING, Suit.HEARTS),
            Card(Rank.KING, Suit.CLUBS),
            Card(Rank.KING, Suit.DIAMONDS),
            Card(Rank.QUEEN, Suit.SPADES),
            Card(Rank.NINE, Suit.HEARTS)
        )

        // When
        val actual = HandType.THREE_OF_A_KIND.formedFrom(royalFlush)

        // Then
        assertTrue(actual)
    }

    @Test
    fun twoOfAKindFormedFromShouldReturnTrue() {
        // Given
        val royalFlush = listOf(
            Card(Rank.KING, Suit.HEARTS),
            Card(Rank.KING, Suit.CLUBS),
            Card(Rank.TEN, Suit.DIAMONDS),
            Card(Rank.QUEEN, Suit.SPADES),
            Card(Rank.NINE, Suit.HEARTS)
        )

        // When
        val actual = HandType.PAIR.formedFrom(royalFlush)

        // Then
        assertTrue(actual)
    }

    @Test
    fun flushFormedFromShouldReturnTrue() {
        // Given
        val royalFlush = listOf(
            Card(Rank.KING, Suit.CLUBS),
            Card(Rank.THREE, Suit.CLUBS),
            Card(Rank.TEN, Suit.CLUBS),
            Card(Rank.QUEEN, Suit.CLUBS),
            Card(Rank.NINE, Suit.CLUBS)
        )

        // When
        val actual = HandType.FLUSH.formedFrom(royalFlush)

        // Then
        assertTrue(actual)
    }

    @Test
    fun straightFormedFromShouldReturnTrue() {
        // Given
        val royalFlush = listOf(
            Card(Rank.TEN, Suit.CLUBS),
            Card(Rank.EIGHT, Suit.SPADES),
            Card(Rank.SIX, Suit.DIAMONDS),
            Card(Rank.SEVEN, Suit.SPADES),
            Card(Rank.NINE, Suit.CLUBS)
        )

        // When
        val actual = HandType.STRAIGHT.formedFrom(royalFlush)

        // Then
        assertTrue(actual)
    }

    @Test
    fun twoPairFormedFromShouldReturnTrue() {
        // Given
        val royalFlush = listOf(
            Card(Rank.TEN, Suit.CLUBS),
            Card(Rank.TEN, Suit.SPADES),
            Card(Rank.SIX, Suit.DIAMONDS),
            Card(Rank.SEVEN, Suit.SPADES),
            Card(Rank.SEVEN, Suit.CLUBS)
        )

        // When
        val actual = HandType.TWO_PAIR.formedFrom(royalFlush)

        // Then
        assertTrue(actual)
    }

    @Test
    fun fullHouseFormedFromShouldReturnTrue() {
        // Given
        val royalFlush = listOf(
            Card(Rank.TEN, Suit.CLUBS),
            Card(Rank.TEN, Suit.SPADES),
            Card(Rank.TEN, Suit.DIAMONDS),
            Card(Rank.SEVEN, Suit.SPADES),
            Card(Rank.SEVEN, Suit.CLUBS)
        )

        // When
        val actual = HandType.FULL_HOUSE.formedFrom(royalFlush)

        // Then
        assertTrue(actual)
    }
}