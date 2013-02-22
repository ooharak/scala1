package io

import scala.xml.XML

/**
 * @author Administrator
 *
 */
object IOExample {

  /**
   * @param args
   */
  def main(args: Array[String]) {
    val xml = <html>
                <head>
                  <meta http-equiv="Content-Type" content="text/html"/>
                </head>
                <body>
                  <h1>midashi</h1>
                  aaa
                </body>
              </html>
    println(xml)
  }
}