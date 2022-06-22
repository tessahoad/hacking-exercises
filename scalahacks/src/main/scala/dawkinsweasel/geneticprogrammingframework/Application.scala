package dawkinsweasel.geneticprogrammingframework

import dawkinsweasel.geneticprogrammingframework.model.{DawkinsWeaselIndividual, Population, Individual}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object Application {

  def main(args: Array[String]) = {
    val firstDawkinsWeasel = DawkinsWeaselIndividual.randomWeasel()

    val goalWeasel = DawkinsWeaselIndividual.fittestWeasel()

    val history = ArrayBuffer[Population]()
    history.append(Population(Seq(firstDawkinsWeasel)))

    while (history.last.findIndividualWithFitness(goalWeasel.getFitness()) == null) {
      val nextGeneration = Population.getNextGeneration(history.last)
      history.append(nextGeneration)
      println(nextGeneration.individuals.head.asInstanceOf[DawkinsWeaselIndividual].sentence)
    }
  }
}