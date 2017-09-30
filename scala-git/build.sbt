import Dependencies._

description := "Scala veneer for JGit"

libraryDependencies ++= Seq(
  jgit,
  scalatest % "test"
)
