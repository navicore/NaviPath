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

version := "2.0.0"

val scala211 = "2.11.12"
val scala212 = "2.12.7"

crossScalaVersions := Seq(scala211, scala212)

publishMavenStyle := true

homepage := Some(url("https://github.com/navicore/NaviPath"))

scmInfo := Some(ScmInfo(url("https://github.com/navicore/NaviPath"),
                            "git@github.com:navicore/navipath.git"))

developers := List(Developer("navicore",
                             "Ed Sweeney",
                             "ed@onextent.com",
                             url("https://github.com/navicore")))
licenses += ("MIT", url("https://opensource.org/licenses/MIT"))

import ReleaseTransformations._

releaseCrossBuild := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value // Use publishSigned in publishArtifacts step

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)


sonatypeProfileName := "tech.navicore"
useGpg := true
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

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

