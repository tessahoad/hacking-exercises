package dawkinsweasel._2026

import com.github.vickumar1981.stringdistance.StringDistance._
import com.github.vickumar1981.stringdistance.impl.ConstantGap
import dawkinsweasel._2026.domain.Mutator

import scala.util.Random

object domain {

  /* TODO: Requirements
    1. System must be able to take a candidate object, and simulate mutations toward a target object
    2. System should be able to either run for a set number of rounds, or be allowed to go until target object is reached
    3. System should be able to run multiple simululations of evolution, and display information on graphs about it
      a. how many rounds does it take to reach target state?
      b. how many rounds if you change the numbers of copies?
      c. how many rounds if you change the volatility of the mutation?
      d. what's the highest scoring individual after n rounds across multiple runs?
  */

  trait Individual

  trait Fitness[T <: Individual, U] {
    def calculateFitness(individual: T): U
  }

  trait Round[T <: Individual, U, V <: Fitness[T, U]] {
    val startingPopulation: Seq[T]
    val number: Int
    val winningIndividual: T
    val fitness: V
  }

  trait Simulation[T <: Individual, U,  V <: Fitness[T, U]]

  trait Runner[T <: Individual, U,  V <: Fitness[T, U]] {
    def run(simulation: Simulation[T, U, V]): Seq[Round[T, U, V]]
  }


  trait Mutator[T <: Individual] {
    def mutate(individual: T): T
  }

  trait UserInterface[T <: Individual, U,  V <: Fitness[T, U]] {
    def display(round: Round[T, U, V]): Unit
  }

  class GraphBasedInterface[T <: Individual, U,  V <: Fitness[T, U]] extends UserInterface[T, U, V] {

    override def display(round: Round[T, U, V]): Unit = ???
  }

  class TextBasedInterface[T <: Individual, U,  V <: Fitness[T, U]] extends UserInterface[T, U, V] {

    override def display(round: Round[T, U, V]): Unit = {
      println(s"Round: ${round.number}. Winning individual ${round.winningIndividual}")
    }
  }

  case class WeaselIndividual(value: String) extends Individual

  object WeaselIndividual {
    val CANDIDATE_COMPONENTS = ('A' to 'Z').appended(' ')
    def apply(random: Random): WeaselIndividual = {
      val baseValue = (1 to 28)
        .map(_ => CANDIDATE_COMPONENTS(random.nextInt(CANDIDATE_COMPONENTS.length)))
        .mkString
      WeaselIndividual(baseValue)
    }
  }

  object SimilarityFitness extends Fitness[WeaselIndividual, Int] {
    val TARGET = "METHINKS IT IS LIKE A WEASEL"

    override def calculateFitness(individual: WeaselIndividual): Int = {
      individual.value.zip(TARGET).map{ case (candidateChar, targetChar) =>
        if (candidateChar.equals(targetChar)) 1 else 0
      }.sum
    }
  }

  object LevenshteinFitness extends Fitness[WeaselIndividual, Double] {
    val TARGET = "METHINKS IT IS LIKE A WEASEL"

    override def calculateFitness(individual: WeaselIndividual): Double = {
      Levenshtein.score(individual.value, TARGET)
    }
  }

  class InPlaceMutator(mutationChance: Int, random: Random) extends Mutator[WeaselIndividual] {
    override def mutate(individual: WeaselIndividual): WeaselIndividual = {
      val newValue = individual.value.map { char =>
        if (random.nextInt(100) < mutationChance) {
          WeaselIndividual.CANDIDATE_COMPONENTS(random.nextInt(WeaselIndividual.CANDIDATE_COMPONENTS.length))
        }
        else char
      }
      individual.copy(value = newValue)
    }
  }

  class SingleEditMutation(mutationChance: Int, random: Random) extends Mutator[WeaselIndividual] {
    override def mutate(individual: WeaselIndividual): WeaselIndividual = {
      val newValue = individual.value.flatMap { char =>
        if (random.nextInt(100) < mutationChance) {
          random.nextInt(3) match {
            case 0 => ""
            case 1 => WeaselIndividual.CANDIDATE_COMPONENTS(random.nextInt(WeaselIndividual.CANDIDATE_COMPONENTS.length)).toString
            case 2 => s"$char${WeaselIndividual.CANDIDATE_COMPONENTS(random.nextInt(WeaselIndividual.CANDIDATE_COMPONENTS.length))}"
          }
        }
        else char.toString
      }
      individual.copy(value = newValue)
    }
  }

  def main(args: Array[String]): Unit = {

  }

}
