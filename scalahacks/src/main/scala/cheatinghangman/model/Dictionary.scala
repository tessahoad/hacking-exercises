package cheatinghangman.model

import scala.util.{Random, Using}

trait Dictionary {
  def getWords(): Set[String]
  def getWords(length: Int): Set[String]
  def getWords(length: Int, number: Int): Set[String]
}

class SimpleDictionary extends Dictionary {
  override def getWords(): Set[String] = {
    Seq("cat", "car", "cow", "dog", "pig").map(_.toLowerCase()).toSet
  }

  override def getWords(length: Int): Set[String] = {
    if (length == 3) getWords() else throw new UnsupportedOperationException
  }

  override def getWords(length: Int, number: Int): Set[String] = {
    if (length == 3) getWords() else throw new UnsupportedOperationException
  }
}

// todo: cache
class OnlineDictionary extends Dictionary {
  override def getWords(): Set[String] = {
    Using(scala.io.Source.fromURL("http://www.mieliestronk.com/corncob_caps.txt")) { source => source.getLines().toSeq }.get.map(_.toLowerCase).toSet
  }

  override def getWords(length: Int): Set[String] = {
    Using(scala.io.Source.fromURL("http://www.mieliestronk.com/corncob_caps.txt")) { source => source.getLines().toSeq }.get.map(_.toLowerCase).filter(_.length == length).toSet
  }

  override def getWords(length: Int, number: Int): Set[String] = {
    val allWords = Using(scala.io.Source.fromURL("http://www.mieliestronk.com/corncob_caps.txt")) { source => source.getLines().toSeq }.get.map(_.toLowerCase).filter(_.length == length)
    Random.shuffle(allWords).take(100).toSet
  }
}
