package com.aktit.query

import com.aktit.query.console.Out
import com.aktit.query.model.Table
import com.aktit.query.service.TableServiceBeans
import com.aktit.query.testmodel.ModelBuilders.table
import com.aktit.query.util.DirUtils.randomFolder

import scala.reflect.runtime.universe.TypeTag

/**
  * @author kostas.kougios
  *         31/08/2020 - 16:06
  */
trait TestApp extends TableServiceBeans {
  private val o = new StringBuilder
  lazy val out = new Out {
    override def println(s: String): Unit = o.append(s).append('\n')

    override def error(s: String): Unit = o.append(s).append('\n')
  }
  lazy val spark = AbstractSparkSuite.spark

  def printed = o.result().trim

  def createTable[A <: Product: TypeTag](
      name: String,
      data: Seq[A],
      format: String = "parquet"
  ): Table = {
    val targetDir = randomFolder
    tableService.create(table(name, path = targetDir, format = format), data)
  }

}
