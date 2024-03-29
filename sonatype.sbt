sonatypeProfileName := "com.madgag"

ThisBuild/sonatypeCredentialHost := "s01.oss.sonatype.org"

ThisBuild / publishTo := sonatypePublishToBundle.value

ThisBuild / scmInfo := Some(ScmInfo(
  url("https://github.com/rtyley/scala-git"),
  "scm:git:git@github.com:rtyley/scala-git.git"
))

ThisBuild / pomExtra := (
  <url>https://github.com/rtyley/scala-git</url>
    <developers>
      <developer>
        <id>rtyley</id>
        <name>Roberto Tyley</name>
        <url>https://github.com/rtyley</url>
      </developer>
    </developers>
  )