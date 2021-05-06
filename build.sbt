import ReleaseTransformations._

val robertoJgit = "com.madgag" % "org.eclipse.jgit" % "3.4.0.1.1-UNOFFICIAL-ROBERTO-RELEASE"
val eclipseJgit = "org.eclipse.jgit" % "org.eclipse.jgit" % "4.0.1.201506240215-r"
val jgit = eclipseJgit

val scalatest = "org.scalatest" %% "scalatest" % "3.2.8"

val madgagCompress = "com.madgag" % "util-compress" % "1.33"

val guava = Seq("com.google.guava" % "guava" % "30.1.1-jre", "com.google.code.findbugs" % "jsr305" % "2.0.1")

lazy val baseSettings = Seq(
  scalaVersion := "2.13.5",
  crossScalaVersions := Seq(scalaVersion.value, "3.0.0-RC3"),
  organization := "com.madgag.scala-git",
  scmInfo := Some(ScmInfo(
    url("https://github.com/rtyley/scala-git"),
    "scm:git:git@github.com:rtyley/scala-git.git"
  )),
  licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  scalacOptions ++= Seq("-deprecation", "-unchecked")
)

lazy val `scala-git` = project.settings(baseSettings: _*).dependsOn(`scala-git-test` % Test).settings {
  description := "Scala veneer for JGit"

  libraryDependencies ++= Seq(
    jgit,
    scalatest % Test,
    madgagCompress % Test,
    "com.madgag" %% "scala-collection-plus" % "0.8"
  )
}

lazy val `scala-git-test` = project.in(file("scala-git-test")).settings(baseSettings: _*).settings {
  description := "Utils to make testing Git-related Scala projects easier"

  libraryDependencies ++= guava :+ madgagCompress :+ jgit
}

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
    releaseStepCommand("publishSigned"),
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeReleaseAll"),
    pushChanges
  )
)


