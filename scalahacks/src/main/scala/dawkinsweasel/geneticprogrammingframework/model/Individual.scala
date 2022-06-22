package dawkinsweasel.geneticprogrammingframework.model

import dawkinsweasel.geneticprogrammingframework.model.DawkinsWeaselIndividual.{randomValidCharacter, targetSentence}

import scala.util.Random

trait Individual {
  val parent: Individual

//  chance of mutability belongs to the individual?
  def mutate(): Individual

  def getFitness(): Int
}

case class DawkinsWeaselIndividual(sentence: String, parent: Individual) extends Individual {

  override def mutate(): Individual = {
    val newSentence = sentence.map(c => if (Random.nextInt(100) < 5) randomValidCharacter() else c)

    DawkinsWeaselIndividual(newSentence, this)
  }

  override def getFitness(): Int = {
    sentence.zip(targetSentence).foldLeft(0)((x, y) => if (y._1 == y._2) x + 1 else x)
  }
}

object DawkinsWeaselIndividual {
  private val targetSentence = "METHINKS IT IS LIKE A WEASEL"

  private val validCharacters = " ABCDEFGHIJKLMNOPQRSTUVWXYZ"

  private def randomValidCharacter(): Char = {
    Random.shuffle(validCharacters).head
  }
  
  def emptyWeasel(): DawkinsWeaselIndividual = {
    DawkinsWeaselIndividual(sentence = "_" * targetSentence.length, null)
  }

  def randomWeasel(): DawkinsWeaselIndividual = {
    DawkinsWeaselIndividual(sentence = ("_" * targetSentence.length).map(_ => randomValidCharacter()), emptyWeasel())
  }

  def fittestWeasel(): DawkinsWeaselIndividual = {
    DawkinsWeaselIndividual(sentence = targetSentence, emptyWeasel())
  }
}

