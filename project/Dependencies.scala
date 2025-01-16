import sbt._

object Dependencies {

  val jgit = "org.eclipse.jgit" % "org.eclipse.jgit" % "6.10.0.202406032230-r"

  val scalatest = "org.scalatest" %% "scalatest" % "3.2.19"

  val madgagCompress = "com.madgag" % "util-compress" % "1.35"

  val guava = Seq("com.google.guava" % "guava" % "33.4.0-jre", "com.google.code.findbugs" % "jsr305" % "3.0.2")

}
