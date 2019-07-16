
name := "Java2Scala"

version := "1.0"

scalaVersion := "2.11.5"

resolvers ++= Seq("Local Maven Repository" at "file:///"+Path.userHome+"/.m2/repository",
  "Signpost releases" at "https://oss.sonatype.org/content/repositories/signpost-releases/")