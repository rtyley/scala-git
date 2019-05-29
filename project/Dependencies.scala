import sbt._

object Dependencies {

  val robertoJgit = "com.madgag" % "org.eclipse.jgit" % "3.4.0.1.1-UNOFFICIAL-ROBERTO-RELEASE"
  val eclipseJgit = "org.eclipse.jgit" % "org.eclipse.jgit" % "5.3.1.201904271842-r"
  val jgit = eclipseJgit

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.4"

  val madgagCompress = "com.madgag" % "util-compress" % "1.33"

  val guava = Seq("com.google.guava" % "guava" % "18.0", "com.google.code.findbugs" % "jsr305" % "2.0.1")

}
