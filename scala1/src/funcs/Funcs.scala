package funcs

object Funcs {
  def sum(f: Int => Int, a: Int, b: Int): Int =
    if (a > b) 0 else f(a) + sum(f, a + 1, b)

  def makesum(f:Int=>Int):(Int,Int)=>Int = {
    def sumF(a:Int, b:Int):Int = 
      if (a>b) 0 else f(a) + sumF(a+1,b)
    sumF
  }
  def main(args: Array[String]) {
    println("value:" + sum(x => x * 2, 1, 10))
    println("value:" + makesum(x => x * 2)(1, 10))
  }
}