import sbt._

object Dependencies {

  val robertoJgit = "com.madgag" % "org.eclipse.jgit" % "6.4.0.202211300538-r"
  val eclipseJgit = "org.eclipse.jgit" % "org.eclipse.jgit" % "4.0.1.201506240215-r"
  val jgit = eclipseJgit

  val scalatest = "org.scalatest" %% "scalatest" % "3.2.14"

  val madgagCompress = "com.madgag" % "util-compress" % "1.35"

  val guava = Seq("com.google.guava" % "guava" % "31.1-jre", "com.google.code.findbugs" % "jsr305" % "2.0.1")

}
