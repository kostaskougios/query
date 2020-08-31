import Dependencies._
import coursier.maven.MavenRepository
import mill._
import mill.modules.Assembly.Rule
import mill.scalalib._
import mill.scalalib.scalafmt._

object query extends Common {
  override def ivyDeps = Agg(Spark.Core, Spark.Sql, Utils.Scopt, Utils.JLine, Utils.Logback)

  object test extends CommonTest

  override def assemblyRules =
    Seq(
      Rule.Append("META-INF/services/org.apache.hadoop.fs.FileSystem", separator = "\n") // all FileSystem files will be concatenated into single file
    ) ++ super.assemblyRules

  def dataGenerator() = runMain("com.aktit.query.DataGenerator")
}

trait Common extends SbtModule with ScalafmtModule {
  override def scalaVersion = Scala.Version

  override def scalacOptions = Seq("-deprecation", "-feature", "-unchecked")

  override def repositories = super.repositories ++ Seq(
    MavenRepository("https://oss.sonatype.org/content/repositories/releases/")
  )

  trait CommonTest extends Tests {
    override def ivyDeps = Agg(Test.ScalaTest, Test.ScalaMock, Utils.Diffx) ++ extraIvyDeps

    def extraIvyDeps: Seq[Dep] = Nil

    override def testFrameworks = Seq("org.scalatest.tools.Framework")
  }

}

object Dependencies {

  object Scala {
    val Version = "2.12.12"
  }

  object Test {
    val ScalaTest = ivy"org.scalatest::scalatest:3.2.2"
    val ScalaMock = ivy"org.scalamock::scalamock:4.4.0"
  }

  object Apache {
    val CommonIO = ivy"commons-io:commons-io:2.7"
    val CommonText = ivy"org.apache.commons:commons-text:1.7"
  }

  object Spark {
    val Version = "3.0.0"
    val Core = ivy"org.apache.spark::spark-core:$Version" excludeName "slf4j-log4j12"
    val Sql = ivy"org.apache.spark::spark-sql:$Version" excludeName "slf4j-log4j12"
  }

  object Utils {
    val Enum = ivy"com.beachape::enumeratum:1.5.13"
    val Scopt = ivy"com.github.scopt::scopt:4.0.0-RC2"
    val JLine = ivy"org.jline:jline:3.16.0"
    val Logback = ivy"ch.qos.logback:logback-classic:1.2.3"
    val Diffx = ivy"com.softwaremill.diffx::diffx-scalatest:0.3.29"
  }

}
