import ReleaseTransformations.*
import sbtversionpolicy.withsbtrelease.ReleaseVersion
import Dependencies.*

ThisBuild / scalaVersion := "3.3.6"
ThisBuild / crossScalaVersions := Seq(
  scalaVersion.value,
  "2.13.16"
)
ThisBuild / scalacOptions := Seq("-deprecation", "-release:11")

lazy val artifactProducingProjectSettings = Seq(
  organization := "com.madgag.scala-git",
  licenses := Seq(License.Apache2),
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-release:11"),
  libraryDependencies ++= Seq(scalatest % Test)
)

lazy val `scala-git` = project.settings(artifactProducingProjectSettings *).dependsOn(`scala-git-test` % Test).settings(
  libraryDependencies ++= Seq(
    jgit,
    "com.madgag" %% "scala-collection-plus" % "1.0.0",
    scalatest % Test
  ),
  Test / fork := true
)

lazy val `scala-git-test` = project.in(file("scala-git-test")).settings(artifactProducingProjectSettings *).settings(
  libraryDependencies ++= guava :+ zip4j :+ jgit
)

ThisBuild / Test / testOptions +=
  Tests.Argument(TestFrameworks.ScalaTest, "-u", s"test-results/scala-${scalaVersion.value}", "-o")

lazy val root = (project in file(".")).aggregate(`scala-git`, `scala-git-test`).settings(
  publish / skip := true,
  // releaseVersion := ReleaseVersion.fromAggregatedAssessedCompatibilityWithLatestRelease().value,
  releaseCrossBuild := true, // true if you cross-build the project for multiple Scala versions
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    setNextVersion,
    commitNextVersion
  )
)


