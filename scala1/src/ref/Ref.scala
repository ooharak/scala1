package ref


class Reference[T] {
  private var contents: T = _
  def set(value: T) { contents = value }
  def get: T = contents
}

object Ref {
  def main(args: Array[String]) {
    val cell = new Reference[Int]
    cell.set(13)
  }
}