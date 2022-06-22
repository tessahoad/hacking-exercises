package dawkinsweasel.geneticprogrammingframework.model

import dawkinsweasel.geneticprogrammingframework.model.Individual

case class Population(individuals: Seq[Individual]) {
  def findIndividualWithFitness(fitness: Int): Individual = {
    individuals.find(i => i.getFitness() == fitness).orNull
  }
}

object Population {
  def getNextGeneration(generation: Population): Population = {
    val newIndividuals = generation.individuals
      .map(i => Array.fill(100)(i).map(i => i.mutate()))
      .map(array => array.maxBy(_.getFitness()))
    
    Population(newIndividuals)
  } 
}
