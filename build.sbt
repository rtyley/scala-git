import ReleaseTransformations._

lazy val baseSettings = Seq(
  scalaVersion := "2.13.10",
  crossScalaVersions := Seq(scalaVersion.value),
  organization := "com.madgag.scala-git",
  scmInfo := Some(ScmInfo(
    url("https://github.com/rtyley/scala-git"),
    "scm:git:git@github.com:rtyley/scala-git.git"
  )),
  licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  scalacOptions ++= Seq("-deprecation", "-unchecked")
)

lazy val `scala-git` = project.settings(baseSettings: _*).dependsOn(`scala-git-test` % Test)

lazy val `scala-git-test` = project.in(file("scala-git-test")).settings(baseSettings: _*)

ThisBuild / Test / testOptions +=
  Tests.Argument(TestFrameworks.ScalaTest, "-u", s"test-results/scala-${scalaVersion.value}")


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


