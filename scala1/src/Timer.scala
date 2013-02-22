object Timer {
  def oncePerSecond(cb: ()=>Unit) {
    while (true) { cb(); Thread sleep 1000}
  }
  
  def timeFiles() {
    println("time flies like an arrow")
  }
  
  def main(args: Array[String]) {
    oncePerSecond(timeFiles)
  }
}