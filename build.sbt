val Scala213Version = "2.13.12"

ThisBuild / version := "0.2"
ThisBuild / organization := "io.github.kostaskougios"
name := "query"
ThisBuild / scalaVersion := Scala213Version
ThisBuild / scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation")

// -----------------------------------------------------------------------------------------------
// Dependencies
// -----------------------------------------------------------------------------------------------

val ScalaTest = "org.scalatest" %% "scalatest" % "3.2.17" % Test
val ScalaMock = "org.scalamock" %% "scalamock" % "5.2.0" % Test
val Diffx = "com.softwaremill.diffx" %% "diffx-scalatest-should" % "0.9.0" % Test

val SparkVersion = "3.5.0"
val SparkCore = ("org.apache.spark" %% "spark-core" % SparkVersion).exclude("org.slf4j", "slf4j-log4j12")
val SparkSql = ("org.apache.spark" %% "spark-sql" % SparkVersion).exclude("org.slf4j", "slf4j-log4j12")
val SparkAvro = "org.apache.spark" %% "spark-avro" % SparkVersion

val Enum = "com.beachape" %% "enumeratum" % "1.7.2"
val Scopt = "com.github.scopt" %% "scopt" % "4.1.0"
val JLine = "org.jline" % "jline" % "3.16.0"
val LogBack = "ch.qos.logback" % "logback-classic" % "1.4.14"

// -----------------------------------------------------------------------------------------------
// Modules
// -----------------------------------------------------------------------------------------------
val commonSettings = Seq(
  Test / fork := true
)

lazy val query = project
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      SparkCore,
      SparkSql,
      SparkAvro,
      Scopt,
      JLine,
      LogBack,
      ScalaTest,
      ScalaMock,
      Diffx
    )
  )
