package com.aktit.query.console

/**
  * @author kostas.kougios
  *         31/08/2020 - 15:39
  */
trait Out {
  def print(s: String): Unit

  def println(s: String): Unit = print(s + "\n")

  def error(s: String): Unit

  def yellowColour(): Unit = print(Console.YELLOW)

  def cyanColour(): Unit = print(Console.CYAN)

  def redColour(): Unit = print(Console.RED)

  def greenColour(): Unit = print(Console.GREEN)

  def normalColour(): Unit = print(Console.RESET)
}

trait OutBeans {
  val out: Out = new Out {
    override def print(s: String): Unit = System.out.print(s)

    override def error(s: String): Unit = System.err.println(s)
  }
}
