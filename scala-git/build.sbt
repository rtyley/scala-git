import Dependencies._

description := "Scala veneer for JGit"

libraryDependencies ++= Seq(
  jgit,
  specs2 % "test"
)
