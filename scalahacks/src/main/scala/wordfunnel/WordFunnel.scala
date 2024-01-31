package wordfunnel

object WordFunnel {

  private val enable1Words = scala.io.Source.fromURL("https://raw.githubusercontent.com/dolph/dictionary/master/enable1.txt").mkString.split("\n").toSet

  def main(args: Array[String]): Unit = {
    println(funnel("leave", "eave"))
    println(funnel("reset", "rest"))
    println(funnel("dragoon", "dragon"))
    println(funnel("eave", "leave"))
    println(funnel("sleet", "lets"))
    println(funnel("skiff", "ski"))

    println(bonus("dragoon"))
    println(bonus("boats"))
    println(bonus("affidavit"))

    println(bonus2().mkString(","))

    println(funnel2("gnash"))
    println(funnel2("princesses"))
    println(funnel2("turntables"))
    println(funnel2("implosive"))
    println(funnel2("programmer"))
  }

  def funnel(word: String, target: String): Boolean = {
    funneledWords(word).contains(target)
  }

  def funnel2(word: String): Seq[String] = {
    val trie = enable1Words.foldLeft(Trie(Map())) { case (trie, word) =>
      trie.add(word)
    }
    Seq()
  }

  case class Trie(children: Map[String, Trie]) {
    def add(word: String): Trie = {
      if (word.isEmpty)
        this
      else {
        val trie = children.getOrElse(word, Trie(Map()))
        val updatedChild = trie.add(intersectEnable1Words(funneledWords(word)))
        trie.copy(children = trie.children + updatedChild)
      }

    }
  }

  def funneledWords(word: String): Set[String] = {
    (0 until word.length).map(index => removeCharAt(word, index)).toSet
  }

  def intersectEnable1Words(words: Set[String]) = {
    words.intersect(enable1Words)
  }

  def bonus(word: String): Set[String] = {
    intersectEnable1Words(funneledWords(word))
  }

  def bonus2(): Set[String] = {
    enable1Words
      .filter(_.length > 4)
      .map(word => word -> bonus(word))
      .filter{ case (_, funneledWords) => funneledWords.size == 5 }
      .map(_._1)
  }

  def removeCharAt(str: String, index: Int): String = {
    str.take(index) + str.drop(index + 1)
  }
}
