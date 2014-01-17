name := "akka-roller"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka"	%%	"akka-actor"		%	"2.2.3",
  "com.typesafe.akka" 	%% 	"akka-testkit" 		% 	"2.2.3",
  "com.typesafe.akka" 	%%	"akka-remote" 		% 	"2.2.3",
  "com.typesafe.akka" 	%% 	"akka-cluster" 		% 	"2.2.3",
  "org.scalatest" 		%% 	"scalatest" 		% 	"2.0" 	% 	"test",
  "junit" 				% 	"junit" 			% 	"4.11" 	% 	"test",
  "com.novocode" 		% 	"junit-interface"	% 	"0.10" 	% 	"test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
