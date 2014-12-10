name := "akka-roller"

version := "1.0"

scalaVersion := "2.11.4"


libraryDependencies ++= Seq(
  "com.typesafe.akka"	%%	"akka-actor"		%	"2.3.7",
  "com.typesafe.akka" 	%% 	"akka-testkit" 		% 	"2.3.7",
  "com.typesafe.akka" 	%%	"akka-remote" 		% 	"2.3.7",
  "com.typesafe.akka" 	%% 	"akka-cluster" 		% 	"2.3.7",
  "org.scalatest" 		%% 	"scalatest" 		% 	"2.2.1" 	% 	"test",
  "junit" 				% 	"junit" 			% 	"4.11" 	% 	"test",
  "com.novocode" 		% 	"junit-interface"	% 	"0.10" 	% 	"test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
