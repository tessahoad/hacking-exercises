name := "scala-hacking"

version := "0.1"

scalaVersion := "3.1.1"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.2.11",
  "org.scalatest" %% "scalatest" % "3.2.11" % Test,
  "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test,
  "org.scalamock" %% "scalamock" % "4.0.0" % Test
)

