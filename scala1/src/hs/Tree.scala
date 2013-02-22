package hs

import scala.collection.immutable.Nil

trait MyTrait {
  def methodOfTrait: String
}

abstract class Tree[T] extends MyTrait
case class Branch[T](left: Tree[T],
  value: T, right: Tree[T]) extends Tree[T] {
  def methodOfTrait(): String =
    "{l:" + left.methodOfTrait +
      ",v:" + value +
      ",r:" + right.methodOfTrait + "}"
}
case class Leaf[T](value: T) extends Tree[T] {
  def methodOfTrait(): String = "V:" + value
}

object Main {
  def main(args: Array[String]) {
    val nakahara = new Leaf("中原")
    val takatsu = new Leaf("高津")
    val kawasaki = new Branch(nakahara, "川崎", takatsu)
    println(kawasaki)
    println(kawasaki.methodOfTrait)
  }
}