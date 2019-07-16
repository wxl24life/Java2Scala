package com.ddu.demo.scala

/**
  * https://docs.scala-lang.org/overviews/core/implicit-classes.html
  * @author wxl24life
  */
object ImplicitHelpers {
  implicit class IntWithTimes(x: Int) {
    def times[A](f: => A): Unit = {
      def loop(current: Int): Unit =
        if (current > 0) {
          f
          loop(current - 1)
        }

      loop(x)
    }
  }

  def main(args: Array[String]): Unit = {
    5 times println("hello")
  }
}
