name := "scalahacks"

version := "1.0"

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.2.11",
  "org.scalatest" %% "scalatest" % "3.2.11" % Test,
  "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % Test
)

