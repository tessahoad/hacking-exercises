package dawkinsweasel

import dawkinsweasel.domain.Individual
import dawkinsweasel.domain.stringindividuals.StringIndividual

fun main() {
    mutateUntilBest(StringIndividual.randomIndividual())
}

fun mutateUntilBest(individual: Individual): Individual {
    return if (individual.score() == individual.bestScore) {
        individual
    } else {
        val individualCopies = (1..100).map{ individual }

        val mutatedIndividuals = individualCopies.map{ it.mutate() }

        val bestIndividual = mutatedIndividuals.maxBy { it.score() }
        println(bestIndividual?.niceToString())
        bestIndividual?.let { mutateUntilBest(it) }!!
    }
}