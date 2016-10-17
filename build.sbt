name := "akka-roller"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.11",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.11",
  "com.typesafe.akka" %% "akka-remote" % "2.4.11",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.11",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
