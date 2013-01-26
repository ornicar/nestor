import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "nestor"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq()

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers ++= Seq(
      "Eligosource Releases" at "http://repo.eligotech.com/nexus/content/repositories/eligosource-releases",
      "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
    ),
    libraryDependencies ++= Seq(
      "org.eligosource" %% "eventsourced" % "0.5-M1",
      "org.scalaz" %% "scalaz-core" % "7.0.0-M7" //% "compile"
    ),
    templatesImport ++= Seq(
      "nestor.domain._"),
    scalacOptions := Seq(
      "-deprecation",
      "-unchecked",
      "-feature",
      "-language:postfixOps",
      "-language:reflectiveCalls",
      "-language:implicitConversions"
    )
  )

}
