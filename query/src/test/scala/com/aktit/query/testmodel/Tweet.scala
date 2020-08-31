package com.aktit.query.testmodel

import java.time.LocalDate

/**
  * @author kostas.kougios
  *         31/08/2020 - 15:58
  */
case class Tweet(id: Long, by: String, text: String, dateTime: LocalDate)
