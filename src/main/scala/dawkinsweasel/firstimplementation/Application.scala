package dawkinsweasel.firstimplementation

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object Application {

  private object RunParameters {
    val targetString = "METHINKS IT IS LIKE A WEASEL."
    val inputCharacters = ". ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val copies = 100
    val mutabilityChance = 5
  }

  def generateStartingSentence(): String = {
    ("_" * RunParameters.targetString.length).map(_ => randomValidCharacter())
  }

  def randomValidCharacter(): Char = {
    Random.shuffle(RunParameters.inputCharacters).head
  }

  def reproduceString(string: String): Array[String] = {
    Array.fill(RunParameters.copies)(string)
  }

  def shuffleStrings(strings: Array[String]): Array[String] = {
    strings.map(s => shuffleString(s))
  }

  def shuffleString(string: String): String = {
    string.map(c => if (Random.nextInt(100) < RunParameters.mutabilityChance) randomValidCharacter() else c)
  }

  def scoreString(string: String): Int = {
    string.zip(RunParameters.targetString).foldLeft(0)((x, y) => if (y._1 == y._2) x + 1 else x)
  }

  def getNextSentence(startingSentence: String): String = {
    println(s"Starting sentence: $startingSentence")
    val rawCopies = reproduceString(startingSentence)
    val shuffledCopies = shuffleStrings(rawCopies)
    shuffledCopies.maxBy(s => scoreString(s))
  }

  def main(args: Array[String]): Unit = {
    val history = ArrayBuffer[String]()
    val startingSentence = generateStartingSentence()
    history.append(startingSentence)
    while (scoreString(history.last) != RunParameters.targetString.length) {
      history.append(getNextSentence(history.last))
    }
    println(s"Sentence matches = ${history.last}")
  }
}
