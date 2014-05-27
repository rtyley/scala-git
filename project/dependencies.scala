import sbt._

object Dependencies {

  val jgit = "org.eclipse.jgit" % "org.eclipse.jgit" % "3.3.2.201404171909-r"

  val specs2 = "org.specs2" %% "specs2" % "2.3.12"

  val madgagCompress = "com.madgag" % "util-compress" % "1.33"

  val guava = Seq("com.google.guava" % "guava" % "17.0", "com.google.code.findbugs" % "jsr305" % "2.0.1")

}
