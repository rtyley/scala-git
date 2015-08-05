import ReleaseTransformations._

lazy val baseSettings = Seq(
  scalaVersion := "2.11.7",
  crossScalaVersions := Seq("2.10.5", scalaVersion.value),
  organization := "com.madgag.scala-git",
  scmInfo := Some(ScmInfo(
    url("https://github.com/rtyley/scala-git"),
    "scm:git:git@github.com:rtyley/scala-git.git"
  )),
  licenses := Seq("GPLv3" -> url("http://www.gnu.org/licenses/gpl-3.0.html")),
  scalacOptions ++= Seq("-deprecation", "-Xlint", "-unchecked")
)

lazy val `scala-git` = project.settings(baseSettings: _*).dependsOn(`scala-git-test` % "test")

lazy val `scala-git-test` = project.in(file("scala-git-test")).settings(baseSettings: _*)

lazy val root = (project in file(".")).aggregate(`scala-git`, `scala-git-test`).
  settings(baseSettings: _*).settings(
    publishArtifact := false,
    publish := {},
    publishLocal := {},
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
