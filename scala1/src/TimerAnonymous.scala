object TimerAnonymous {
  def oncePerSecond(cb: ()=>Unit) {
    while (true) { cb(); Thread sleep 1000}
  }

  
  def main(args: Array[String]) {
    oncePerSecond(()=> println("time flies"))
  }
}