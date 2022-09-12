package dawkinsweasel.domain.stringindividuals

import dawkinsweasel.domain.Individual
import dawkinsweasel.domain.IndividualFactory
import kotlin.random.Random

class StringIndividual(private val value: String) : Individual {

    companion object : IndividualFactory {
        private const val bestValue = "METHINKS IT IS LIKE A WEASEL."
        private val validCharacters =
            arrayOf(
                'A',
                'B',
                'C',
                'D',
                'E',
                'F',
                'G',
                'H',
                'I',
                'J',
                'K',
                'L',
                'M',
                'N',
                'O',
                'P',
                'Q',
                'R',
                'S',
                'T',
                'U',
                'V',
                'W',
                'X',
                'Y',
                'Z',
                '.',
                " "
            )

        override fun idealIndividual(): Individual {
            return StringIndividual(bestValue)
        }

        override fun randomIndividual(): Individual {
            val random = Random
            val value = bestValue.map { validCharacters[random.nextInt(0, validCharacters.size)] }.joinToString("")

            return StringIndividual(value)
        }
    }

    override val bestScore: Int
        get() = bestValue.length

    override fun score(): Int {
        return value.zip(bestValue).fold(0) { acc: Int, pair: Pair<Char, Char> -> if (pair.first == pair.second) acc + 1 else acc }
    }

    override fun mutate(): Individual {
        val random = Random
        val newValue = value.map {
            if (random.nextInt(0, 100) < 5)
                validCharacters[random.nextInt(0, validCharacters.size)] else it
        }.joinToString("")
        return StringIndividual(newValue)
    }

    override fun niceToString(): String {
        return value
    }
}