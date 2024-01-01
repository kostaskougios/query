val Scala213Version = "2.13.12"

ThisBuild / version := "0.2"
ThisBuild / organization := "io.github.kostaskougios"
name := "query"
ThisBuild / scalaVersion := Scala213Version
ThisBuild / scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation")

// -----------------------------------------------------------------------------------------------
// Dependencies
// -----------------------------------------------------------------------------------------------

val ScalaTest = "org.scalatest" %% "scalatest" % "3.2.2"
val ScalaMock = "org.scalamock" %% "scalamock" % "4.4.0"

val SparkVersion = "3.2.1"
val SparkCore = ("org.apache.spark" %% "spark-core" % SparkVersion) // .exclude ("slf4j-log4j12")
val SparkSql = "org.apache.spark" %% "spark-sql" % SparkVersion // excludeName "slf4j-log4j12"
val SparkAvro = "org.apache.spark" %% "spark-avro" % SparkVersion

val Enum = "com.beachape" %% "enumeratum" % "1.5.13"
val Scopt = "com.github.scopt" %% "scopt" % "4.0.0-RC2"
val JLine = "org.jline" % "jline" % "3.16.0"
val Logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
val Diffx = "com.softwaremill.diffx" %% "diffx-scalatest" % "0.3.29"

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
      Logback,
      ScalaTest,
      ScalaMock,
      Diffx
    )
  )
