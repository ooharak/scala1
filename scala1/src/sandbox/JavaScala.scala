package sandbox

import legacy.LegacyHello

object JavaScala {
  def main(args: Array[String]) {
    LegacyHello.staticHello("John")
    val obj = new LegacyHello
    obj.disBonjour("Dominique")
    obj.sayHello()
    val scalaobj = new Hello
    scalaobj.hello("folks")
  }
}