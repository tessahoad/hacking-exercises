package dawkinsweasel._2026

import scala.util.Random

object FunctionalExample {
  val CANDIDATE_COMPONENTS = ('A' to 'Z').appended(' ')
  val TARGET = "METHINKS IT IS LIKE A WEASEL"

  def initialiseRandomString(): String = {
    (1 to 28)
      .map(_ => CANDIDATE_COMPONENTS(Random.nextInt(CANDIDATE_COMPONENTS.length)))
      .mkString
  }

  def reproduce(initial: String, numCopies: Int): Array[String] = {
    Array.fill(numCopies)(initial)
  }

  def mutate(initial: String): String = {
    initial.map { char =>
      if (Random.nextInt(100) < 5) {
        CANDIDATE_COMPONENTS(Random.nextInt(CANDIDATE_COMPONENTS.length))
      }
      else char
    }
  }

  def scoreCandidate(candidate: String, target: String): Int = {
    candidate.zip(target).map{ case (candidateChar, targetChar) =>
      if (candidateChar.equals(targetChar)) 1 else 0
    }.sum
  }

  def main(args: Array[String]): Unit = {
    val initial = initialiseRandomString()
    val initialScore = scoreCandidate(initial, TARGET)
    val rounds = Iterator.iterate((initial, initialScore)){ case (candidate, candidateScore) =>
      val copies = reproduce(candidate, 100)
      val mutations = copies.map(mutate)
      val scoredCandidates = mutations.map(m => (m, scoreCandidate(m, TARGET)))
      val winner = scoredCandidates.maxBy{ case (candidate, score) => score }
      Option.when(winner._1 != candidate) {
        println(s"Winner: $winner")
      }
      winner
    }.zipWithIndex.takeWhile{ case ((_, score), _) => score < 28 }.toList

    println(s"We got $TARGET after ${rounds.length} rounds")
  }
}
