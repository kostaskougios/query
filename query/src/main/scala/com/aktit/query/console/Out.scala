package com.aktit.query.console

/**
  * @author kostas.kougios
  *         31/08/2020 - 15:39
  */
trait Out {
  def println(s: String): Unit
}

trait OutBeans {
  val out: Out = (s: String) => System.out.println(s)
}
