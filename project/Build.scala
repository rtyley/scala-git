import sbt.Keys._
import sbt._
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleaseStateTransformations._

object build extends Build {

  lazy val baseSettings = Seq(
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.10.5", scalaVersion.value),
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
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      ReleaseStep(action = Command.process("publishSigned", _)),
      setNextVersion,
      commitNextVersion,
      ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
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

