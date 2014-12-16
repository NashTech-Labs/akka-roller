#!/bin/bash
function run() {
  echo -e "\e[05;34m$1\e[0m" # blue text
  time $1
}

run "sbt clean"
run "sbt compile"
run "sbt packageBin"

TODAY=`date +"%F-%H-%M-%S"`
git log -n 1 --pretty="[%an] %cD %s" > akka-roller-build.txt
