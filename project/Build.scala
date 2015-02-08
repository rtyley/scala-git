import sbt._, Keys._
import sbtrelease._
import ReleaseStateTransformations._
import com.typesafe.sbt.pgp.PgpKeys
import xerial.sbt.Sonatype._
import Dependencies._

object build extends Build {

  lazy val baseSettings = ReleasePlugin.releaseSettings ++ sonatypeSettings ++ Seq(
    scalaVersion := "2.11.5",
    crossScalaVersions := Seq("2.10.4", "2.11.5"),
    organization := "com.madgag.scala-git",
    scmInfo := Some(ScmInfo(
      url("https://github.com/rtyley/scala-git"),
      "scm:git:git@github.com:rtyley/scala-git.git"
    )),
    description := "Scala veneer for JGit",
    pomExtra := (
      <url>https://github.com/rtyley/scala-git</url>
      <developers>
        <developer>
          <id>rtyley</id>
          <name>Roberto Tyley</name>
          <url>https://github.com/rtyley</url>
        </developer>
      </developers>
    ),
    licenses := Seq("GPLv3" -> url("http://www.gnu.org/licenses/gpl-3.0.html")),
    scalacOptions ++= Seq("-deprecation", "-Xlint", "-unchecked"),
    ReleasePlugin.ReleaseKeys.releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      ReleaseStep(
        action = { state =>
          val extracted = Project extract state
          extracted.runAggregated(PgpKeys.publishSigned in Global in extracted.get(thisProjectRef), state)
        },
        enableCrossBuild = true
      ),
      setNextVersion,
      commitNextVersion,
      ReleaseStep{ state =>
        val extracted = Project extract state
        extracted.runAggregated(SonatypeKeys.sonatypeReleaseAll in Global in extracted.get(thisProjectRef), state)
      },
      pushChanges
    )
  )

  def sgProject(name: String) = Project(name, file(name))

  lazy val scalaGit = sgProject("scala-git").settings(baseSettings: _*).dependsOn(scalaGitTest % "test")

  lazy val scalaGitTest = sgProject("scala-git-test").settings(baseSettings: _*)

  lazy val root = Project(
    "root", file(".")
  ).settings(baseSettings: _*).settings(
    publishArtifact := false,
    publish := {},
    publishLocal := {}
  ).aggregate(scalaGit, scalaGitTest)

}

