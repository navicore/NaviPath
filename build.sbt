name := "NaviPath"

organization := "tech.navicore"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8") 
scalacOptions ++= Seq(
  "-target:jvm-1.8"
)
fork := true
javaOptions in test ++= Seq(
  "-Xms512M", "-Xmx2048M",
  "-XX:MaxPermSize=2048M",
  "-XX:+CMSClassUnloadingEnabled"
)

parallelExecution in test := false

val scala211 = "2.11.12"
val scala212 = "2.12.7"
crossScalaVersions := Seq(scala211, scala212)

inThisBuild(List(
  organization := "tech.navicore",
  homepage := Some(url("https://github.com/navicore/navipath")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "navicore",
      "Ed Sweeney",
      "ed@onextent.com",
      url("https://navicore.tech")
    )
  )
))

libraryDependencies ++=
  Seq(

    "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.11.1",
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.8.11",
    
    "org.scala-lang.modules" %% "scala-parser-combinators"  % "1.1.0",

    "org.scalatest" %% "scalatest" % "3.0.5" % "test"

  )

assemblyJarName in assembly := "NaviPath.jar"

assemblyMergeStrategy in assembly := {
  case PathList("reference.conf") => MergeStrategy.concat
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case PathList("META-INF", _ @ _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

