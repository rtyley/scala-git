sonatypeProfileName := "com.madgag"

publishTo in ThisBuild :=
  Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)

scmInfo in ThisBuild := Some(ScmInfo(
  url("https://github.com/rtyley/scala-git"),
  "scm:git:git@github.com:rtyley/scala-git.git"
))

pomExtra in ThisBuild := (
  <url>https://github.com/rtyley/scala-git</url>
    <developers>
      <developer>
        <id>rtyley</id>
        <name>Roberto Tyley</name>
        <url>https://github.com/rtyley</url>
      </developer>
    </developers>
  )