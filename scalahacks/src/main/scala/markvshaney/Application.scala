package markvshaney

import markvshaney.scratchpad._

import scala.util.Random

object Application {
  private val limit = 50

  def main(args: Array[String]) = {
    val textSource = TrainingTextSource("markvshaney/dickinson-poems.txt")
    val text = ResourceTrainingTextReader.read(textSource)
    val ngrams = TrainingTextParser.parse(text, 3)
    val store = new ListNGramStore(ngrams)
    val startingNgrams = store.read().filter(ngram => ngram.words.head.nonEmpty && ngram.words.head.head.isUpper)
    val randomInt = Random.nextInt(startingNgrams.length)
    val startingNgram = startingNgrams(randomInt)
    val nextWordPredictor = new NextWordPredictor(store.read())

    val predictedNGrams = generateNGrams(startingNgram, nextWordPredictor, startingNgram.words.length - 1)
    displayPoem(Seq(startingNgram) ++ predictedNGrams)
  }

  def generateNGrams(startingNgram: NGram, predictor: NextWordPredictor, length: Int): Seq[NGram] = {
    val nextNGram = predictor.predict(startingNgram)

    if (length >= limit && nextNGram.words.last.matches("[A-z]+[!?.-]")) {
      Seq(nextNGram)
    } else {
      Seq(nextNGram) ++ generateNGrams(nextNGram, predictor, length + 1)
    }
  }

  private def displayPoem(poem: Seq[NGram]) = {
    poem.head.words.init.foreach(word => print(s"""$word """))

    poem.foreach(ngram => {
      if (ngram.words.last.head.isUpper && ngram.words.last.length != 1) {
        println()
      }
      print(s"""${ngram.words.last} """)
    })
    println()
  }

}
