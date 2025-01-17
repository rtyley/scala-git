import ReleaseTransformations.*
import sbtversionpolicy.withsbtrelease.ReleaseVersion
import Dependencies.*

lazy val artifactProducingProjectSettings = Seq(
  scalaVersion := "2.13.16",
  organization := "com.madgag.scala-git",
  licenses := Seq(License.Apache2),
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-release:11"),
  libraryDependencies ++= Seq(madgagCompress % Test, scalatest % Test)
)

lazy val `scala-git` = project.settings(artifactProducingProjectSettings *).dependsOn(`scala-git-test` % Test).settings(
  libraryDependencies ++= Seq(
    jgit,
    "com.madgag" %% "scala-collection-plus" % "0.11",
    scalatest % Test
  )
)

lazy val `scala-git-test` = project.in(file("scala-git-test")).settings(artifactProducingProjectSettings *).settings(
  libraryDependencies ++= guava :+ madgagCompress :+ jgit
)

ThisBuild / Test / testOptions +=
  Tests.Argument(TestFrameworks.ScalaTest, "-u", s"test-results/scala-${scalaVersion.value}", "-o")

lazy val root = (project in file(".")).aggregate(`scala-git`, `scala-git-test`).settings(
  publish / skip := true,
  // releaseVersion := ReleaseVersion.fromAggregatedAssessedCompatibilityWithLatestRelease().value,
  // releaseCrossBuild := true, // true if you cross-build the project for multiple Scala versions
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


