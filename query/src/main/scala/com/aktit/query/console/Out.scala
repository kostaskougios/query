package com.aktit.query.console

/**
  * @author kostas.kougios
  *         31/08/2020 - 15:39
  */
trait Out {
  def print(s: String): Unit

  def println(s: String): Unit = print(s + "\n")

  def error(s: String): Unit

  def yellowColour(): Unit = print(Out.Yellow)

  def cyanColour(): Unit = print(Out.Cyan)

  def redColour(): Unit = print(Out.Red)

  def greenColour(): Unit = print(Out.Green)

  def normalColour(): Unit = print(Out.Normal)
}

object Out {
  // colours
  val Red = "\u001b[31;1m"
  val Green = "\u001b[32;1m"
  val Yellow = "\u001b[33;1m"
  val Normal = "\u001b[0m";
  val Cyan = "\u001b[36;1m";
}

trait OutBeans {
  val out: Out = new Out {
    override def print(s: String): Unit = System.out.print(s)

    override def error(s: String): Unit = System.err.println(s)
  }
}
