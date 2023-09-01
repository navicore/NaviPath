name := "NaviPath"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
scalacOptions ++= Seq(
  "-target:jvm-1.8"
)
fork := true
javaOptions in test ++= Seq(
  "-Xms512M",
  "-Xmx2048M",
  "-XX:MaxPermSize=2048M",
  "-XX:+CMSClassUnloadingEnabled"
)

parallelExecution in test := false

val scala213 = "2.13.10"
val scala212 = "2.12.17"
crossScalaVersions := Seq(scala213, scala212)

ThisBuild / publishTo := sonatypePublishToBundle.value

ThisBuild / organization := "tech.navicore"
ThisBuild / homepage := Some(url("https://github.com/navicore/navipath"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/navicore/navipath"),
    "scm:git@github.com:navicore/navipath.git"
  )
)
ThisBuild / licenses := List(
  "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
)
ThisBuild / developers := List(
  Developer(
    "navicore",
    "Ed Sweeney",
    "ed@onextent.com",
    url("https://navicore.tech")
  )
)

libraryDependencies ++=
  Seq(
    "org.rogach" %% "scallop" % "4.1.0",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.15.2",
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.15.2",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0",
    "org.scalatest" %% "scalatest" % "3.2.15" % "test"
  )

mainClass in assembly := Some("navicore.data.navipath.cli.Main")
assemblyJarName in assembly := "NaviPath.jar"

assemblyMergeStrategy in assembly := {
  case PathList("reference.conf")                      => MergeStrategy.concat
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case PathList("META-INF", _ @_*)                     => MergeStrategy.discard
  case _                                               => MergeStrategy.first
}
