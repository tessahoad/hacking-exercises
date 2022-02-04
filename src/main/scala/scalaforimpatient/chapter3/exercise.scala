package scalaforimpatient.chapter3

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import java.awt.datatransfer._
import scala.jdk.CollectionConverters._

object exercise extends App {
  /*
    1. Write a code snippet that sets a to an array of n
       random integers between 0 (inclusive) and n (exclusive).
  */
  def partOne(n: Int) {
    val a = ArrayBuffer[Int](n)
    for (_ <- 0 until n) {
      a.append(Random.nextInt(n))
    }
    println(a.mkString("[", ",", "]"))
  }

  /*
     2. Write a loop that swaps adjacent elements of an array of integers.
        For example, Array(1, 2, 3, 4, 5) becomes Array(2, 1, 4, 3, 5).
  */
  def partTwo(n: Int) {
    val array = Range.inclusive(1, n).toArray
    for (i <- 0 until(array.length - 1, 2)) {
      val i1 = array(i)
      val i2 = array(i + 1)
      array(i) = i2;
      array(i + 1) = i1
    }
    println(array.mkString("[", ",", "]"))
  }

  /*
     3. “Repeat the preceding assignment, but produce a new array with the swapped values. Use for/yield.”
  */
  def partThree(n: Int) {
    val array = Range.inclusive(1, n).toArray

    val result = for (i <- 0 until array.length) yield {
      if (i % 2 == 0 && i + 1 == n) array(i)
      else if (i % 2 == 0 && i + 1 < n) array(i + 1)
      else array(i - 1)
    }
    println(result.mkString("[", ",", "]"))
  }

  /*
     4. Given an array of integers, produce a new array that contains all positive values of the original array,
     in their original order, followed by all values that are zero or negative, in their original order.
  */
  def partFour(arr: Array[Int]) {

    val positives = arr.filter(a => a > 0)
    val nonPositives = arr.filter(a => a <= 0)

    val result = positives.appendedAll(nonPositives);

    println(result.mkString("[", ",", "]"))
  }

  /*
     5. How do you compute the average of an Array[Double]?
  */
  def partFive(arr: Array[Double]) {

    val result = arr.sum / arr.length

    println(result)
  }

  /*
     6. How do you rearrange the elements of an Array[Int] so that they appear in reverse sorted order?
     How do you do the same with an ArrayBuffer[Int]?
  */
  def partSix(arr: Array[Int]) {

    val result = arr.sorted.reverse

    println(result.mkString("[", ",", "]"))
  }

  /*
     7. Write a code snippet that produces all values from an array with duplicates removed. (Hint: Look at Scaladoc.)
  */
  def partSeven(arr: Array[Int]) {

    val result = arr.distinct

    println(result.mkString("[", ",", "]"))
  }

  /*
     8. Collect indexes of the negative elements, reverse the sequence, drop the last index, and call a.remove(i) for each index.
  */
  def partEight(arr: ArrayBuffer[Int]) {

    val indices = arr.indices.filter(i => arr(i) < 0).reverse.dropRight(1)

    indices.foreach(i => arr.remove(i))

    println(arr.toString)
  }

  /*
     9. Make a collection of all time zones returned by java.util.TimeZone.getAvailableIDs that are in America.
     Strip off the "America/" prefix and sort the result.
  */
  def partNine() {
    val validStart = "America/"
    val availableIds = java.util.TimeZone.getAvailableIDs.filter(id => id.startsWith(validStart)).map(s => s.stripPrefix(validStart)).sorted
    println(availableIds.mkString("[", ",", "]"))
  }

  /*
     10. Import java.awt.datatransfer._ and make an object of type SystemFlavorMap with the call
      val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
      Then call the getNativesForFlavor method with parameter DataFlavor.imageFlavor and get the return value as a Scala buffer.
      (Why this obscure class? It’s hard to find uses of java.util.List in the standard Java library.)
  */
  def partTen(): Unit = {
    val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
    val strings = flavors.getNativesForFlavor(DataFlavor.imageFlavor).asScala
    println(strings)
  }


  partOne(10)
  partTwo(6)
  partThree(5)
  partFour(Array(2, -3, 9, 0, 42, -1, -1, 4))
  partFive(Array(2.4, 2.5, 2.6, 2.7, 2.8))
  partSix(Array(2, -3, 9, 0, 42, -1, -1, 4))
  partSeven(Array(0, 2, -3, 9, 4, 0, 42, -1, -1, 4, -1, 7, 32))
  partEight(ArrayBuffer(0, 2, -3, 9, 4, 0, 42, -1, -1, 4, -1, -7, 32, -9))
  partNine()
  partTen()
}
