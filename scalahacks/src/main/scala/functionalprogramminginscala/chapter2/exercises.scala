package functionalprogramminginscala.chapter2

import scala.annotation.tailrec

object exercises {
  def fib(n: Int): Int = {
    @tailrec
    def go(prev: Int, current: Int, acc: Int, total: Int): Int = {
      if (acc == total) current
      else go(current, prev + current, acc + 1, total)
    }

    go(0, 1, 1, n)
  }

  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10) {
      println(fib(i))
    }
  }
}
