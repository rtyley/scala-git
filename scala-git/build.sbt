import Dependencies._

description := "Scala veneer for JGit"

libraryDependencies ++= Seq(
  jgit,
  "com.madgag" %% "scala-collection-plus" % "0.11",
  scalatest % Test
)
