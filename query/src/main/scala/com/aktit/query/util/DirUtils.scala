package com.aktit.query.util

import java.util.UUID

import org.apache.commons.io.FileUtils

/**
  * @author kostas.kougios
  *         02/09/2020 - 23:41
  */
object DirUtils {
  def randomFolder: String = FileUtils.getTempDirectoryPath + "/AbstractSparkSuite/" + UUID.randomUUID

}
