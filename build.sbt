name := "akka-roller"

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  "com.typesafe.akka"	%%	"akka-actor"		%	"2.4.0",
  "com.typesafe.akka" 	%% 	"akka-testkit" 		% 	"2.4.0",
  "com.typesafe.akka" 	%%	"akka-remote" 		% 	"2.4.0",
  "com.typesafe.akka" 	%% 	"akka-cluster" 		% 	"2.4.0",
  "org.scalatest" 		%% 	"scalatest" 		% 	"2.2.4" 	% 	"test",
  "junit" 				% 	"junit" 			% 	"4.11" 	% 	"test",
  "com.novocode" 		% 	"junit-interface"	% 	"0.10" 	% 	"test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
