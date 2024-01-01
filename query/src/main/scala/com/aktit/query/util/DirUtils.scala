package com.aktit.query.util

import java.util.UUID

import org.apache.commons.io.FileUtils

/**
  * @author kostas.kougios
  *         02/09/2020 - 23:41
  */
object DirUtils {
  def randomFolder: String = {
    val t = FileUtils.getTempDirectoryPath
    val slash = if (t.endsWith("/")) "" else "/"
    t + slash + "AbstractSparkSuite/" + UUID.randomUUID
  }

}
