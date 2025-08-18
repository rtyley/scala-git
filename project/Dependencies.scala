import sbt._

object Dependencies {

  val jgit = "org.eclipse.jgit" % "org.eclipse.jgit" % "7.3.0.202506031305-r"

  val scalatest = "org.scalatest" %% "scalatest" % "3.2.19"

  val madgagCompress = "com.madgag" % "util-compress" % "1.35"

  val guava = Seq("com.google.guava" % "guava" % "33.4.8-jre", "com.google.code.findbugs" % "jsr305" % "3.0.2")

}
