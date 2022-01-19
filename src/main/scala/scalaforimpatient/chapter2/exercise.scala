package scalaforimpatient.chapter2

object exercise {
  def getSignum(number : Int) :Int = {
    val result =  if (number == 0) 0 else if (number > 0) 1 else -1
    result
  }

  def countdown(n: Int): Unit = {
    for (i <- Range.inclusive(n, 0, -1)) println(i)
  }

  def unicodeProduct(str: String): Long = {
    str.foldLeft(1L)(_ * _)
  }

  def raiseToPower(base: Double, exponent: Int): Double = {
    val sign = getSignum(exponent)
    if (sign == 0) 1
    else if (sign == -1) 1 / raiseToPower(base, exponent * -1)
    else if (exponent % 2 == 0) {
      val d = raiseToPower(base, exponent / 2)
      d * d
    }
    else base * raiseToPower(base, exponent - 1)
  }

  def main(args: Array[String]): Unit = {
    print(raiseToPower(2, 4))
  }
}
