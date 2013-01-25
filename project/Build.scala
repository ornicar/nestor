import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "nestor"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq()

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers ++= Seq(
      "Eligosource Releases" at "http://repo.eligotech.com/nexus/content/repositories/eligosource-releases"
    ),
    libraryDependencies ++= Seq(
      "org.eligosource" %% "eventsourced" % "0.5-M1"
    ),
    scalacOptions := Seq(
      "-deprecation",
      "-unchecked",
      "-feature",
      "-language:postfixOps"
    )
  )

}
