package com.ddu.demo.scala

/**
  * @author wxl24life
  */
object HelloWorld {

  val text: String = "Hello World"

  def echo(text: String): Unit = {
    println(text)
  }

  def echo() = {
    println(this.text)
  }

  def main(args: Array[String]): Unit = {
    echo()
    echo("Hello Scala")
  }
}
