package com.aktit.query.console

/**
  * @author kostas.kougios
  *         31/08/2020 - 15:39
  */
trait Out {
  def println(s: String): Unit

  def error(s: String): Unit
}

trait OutBeans {
  val out: Out = new Out {
    override def println(s: String): Unit = System.out.println(s)

    override def error(s: String): Unit = System.err.println(s)
  }
}
