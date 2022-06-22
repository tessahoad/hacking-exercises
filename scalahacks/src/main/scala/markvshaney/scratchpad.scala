package markvshaney

import scala.io.Source
import scala.util.Random

object scratchpad {

  case class TrainingTextSource(location: String)

  case class TrainingText(text: String)

  trait TrainingTextReader {
    def read(textSource: TrainingTextSource): TrainingText
  }

  object ResourceTrainingTextReader extends TrainingTextReader {
    override def read(textSource: TrainingTextSource): TrainingText = {
      val inputLines = Source.fromResource(textSource.location).getLines().toArray
      TrainingText(inputLines.mkString(" "))
    }
  }

  object TrainingTextParser {
    def parse(trainingText: TrainingText, n: Int): Seq[NGram] = {
      trainingText.text.split("\\s+").sliding(n).map(window => NGram(window.toSeq)).toSeq
    }
  }

  case class NGram(words: Seq[String])

  trait NGramStore {
    def write(ngrams: Seq[NGram]): Unit

    def read(): Seq[NGram]
  }

  class ListNGramStore(var store: Seq[NGram]) extends NGramStore {

    def write(ngrams: Seq[NGram]): Unit = {
      store = ngrams
    }

    def read(): Seq[NGram] = {
      store
    }
  }

  class NextWordPredictor(allNgrams: Seq[NGram]) {
    def predict(previousNGram: NGram): NGram = {

      val possibleNgrams = allNgrams.filter(ngram => !ngram.words.init.zip(previousNGram.words.tail).exists(x => standardizeWord(x._1) != standardizeWord(x._2)))

      if (possibleNgrams.isEmpty) {
        previousNGram
      } else {
        val randomInt = Random.nextInt(possibleNgrams.length)
        possibleNgrams(randomInt)
      }
    }

    private def standardizeWord(word: String): String = {
      word.replaceAll("[^A-Za-z]+", "")
    }
  }

}
