package com.aktit.query

import com.aktit.query.util.DirUtils.randomFolder
import org.apache.spark.sql.{Dataset, SparkSession}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.slf4j.LoggerFactory

/**
  * The base class for all spark tests.
  *
  * @author kostas.kougios
  *         20/06/2020 - 10:46
  */
abstract class AbstractSparkSuite extends AnyFunSuite with Matchers {

  protected val spark = AbstractSparkSuite.spark

  implicit class DatasetImplicits[A](ds: Dataset[A]) {
    def toSeq = ds.collect.toSeq

    def toSet = ds.collect.toSet
  }

}

object AbstractSparkSuite {
  // there is 1 SparkSession instance per jvm, this is the one for tests
  val spark = {
    val tmpDir = randomFolder
    LoggerFactory.getLogger(getClass).info(s"Testing tmp dir is $tmpDir")
    SparkSession.builder
      .master("local[*]")
      .appName("testing")
      .config("spark.ui.enabled", false)
      .config("spark.sql.shuffle.partitions", 4)
      .config("spark.sql.warehouse.dir", tmpDir)
      .getOrCreate
  }
}
