import sbt._

object Dependencies {

  val jgit = "org.eclipse.jgit" % "org.eclipse.jgit" % "6.7.0.202309050840-r"

  val scalatest = "org.scalatest" %% "scalatest" % "3.2.15"

  val madgagCompress = "com.madgag" % "util-compress" % "1.35"

  val guava = Seq("com.google.guava" % "guava" % "32.1.3-jre", "com.google.code.findbugs" % "jsr305" % "3.0.2")

}
