package com.aktit.query

import com.aktit.query.console.OutBeans
import com.aktit.query.service.{ConsoleServiceBeans, SparkBeans, TableServiceBeans}

/**
  * @author kostas.kougios
  *         30/08/2020 - 21:32
  */
object DI extends SparkBeans with OutBeans with TableServiceBeans with ConsoleServiceBeans
