import sbt._

object Dependencies {

  val robertoJgit = "com.madgag" % "org.eclipse.jgit" % "3.4.0.1.1-UNOFFICIAL-ROBERTO-RELEASE"
  val eclipseJgit = "org.eclipse.jgit" % "org.eclipse.jgit" % "3.4.1.201406201815-r"
  val jgit = eclipseJgit

  val specs2 = "org.specs2" %% "specs2" % "2.3.12"

  val madgagCompress = "com.madgag" % "util-compress" % "1.33"

  val guava = Seq("com.google.guava" % "guava" % "17.0", "com.google.code.findbugs" % "jsr305" % "2.0.1")

}
