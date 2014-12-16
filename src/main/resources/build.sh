#!/bin/bash
function run() {
  echo -e "\e[05;34m$1\e[0m" # blue text
  time $1
}
# Maven cleans better than SBT
# run "mvn clean"
run "sbt clean"
run "sbt compile"
run "sbt universal:packageBin"
run "sbt universal:dist"
TODAY=`date +"%F-%H-%M-%S"`
git log -n 1 --pretty="[%an] %cD %s" > akka-roller-build.txt
