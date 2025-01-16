import ReleaseTransformations._
import Dependencies._

lazy val baseSettings = Seq(
  scalaVersion := "2.13.16",
  organization := "com.madgag.scala-git",
  scmInfo := Some(ScmInfo(
    url("https://github.com/rtyley/scala-git"),
    "scm:git:git@github.com:rtyley/scala-git.git"
  )),
  licenses := Seq(License.Apache2),
  scalacOptions ++= Seq("-deprecation", "-unchecked"),
  libraryDependencies ++= Seq(madgagCompress % Test, scalatest % Test)
)

lazy val `scala-git` = project.settings(baseSettings: _*).dependsOn(`scala-git-test` % Test)

lazy val `scala-git-test` = project.in(file("scala-git-test")).settings(baseSettings: _*)

ThisBuild / Test / testOptions +=
  Tests.Argument(TestFrameworks.ScalaTest, "-u", s"test-results/scala-${scalaVersion.value}", "-o")


lazy val root = (project in file(".")).aggregate(`scala-git`, `scala-git-test`).
  settings(baseSettings: _*).settings(
  publishArtifact := false,
  publish := {},
  publishLocal := {},
  releaseCrossBuild := true, // true if you cross-build the project for multiple Scala versions
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    // For non cross-build projects, use releaseStepCommand("publishSigned")
    releaseStepCommandAndRemaining("+publishSigned"),
    releaseStepCommand("sonatypeBundleRelease"),
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
)


