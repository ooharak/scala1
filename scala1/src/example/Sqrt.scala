package example

object Sqrt {
  def square(a: Double) = a * a
  def abs(a: Double) = if (a < 0) -a else a

  def sqrtitr(guess: Double, x: Double): Double =
    if (isGoodEnough(guess, x)) guess
    else sqrtitr(improve(guess, x), x)

  def isGoodEnough(guess: Double, x: Double) =
    abs(square(guess) - x) < 0.0000001

  def improve(guess: Double, x: Double): Double =
    (guess + x / guess) / 2

  def sqrt(x: Double) = sqrtitr(1.0, x)

  def main(args: Array[String]) {
    println(sqrt(3))
  }
}