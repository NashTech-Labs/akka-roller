package com.knoldus.akka.actor

import akka.actor.{Actor, ActorSystem, Props}

object SplitPersonalityRunner extends App {

  val system = ActorSystem("Now")
  val show = system.actorOf(Props(classOf[SplitPersonality]))
  show ! "foo"
  show ! "foo"
  show ! "bar"
  show ! "bar"
  show ! "foo"
  show ! "foo"

  system terminate() // Comment out this if it is indeterministic behavior
}

class SplitPersonality extends Actor {

  import context._

  def angry: Receive = {
    case "foo" => println("I am already angry?")
    case "bar" => println("Becoming happy now"); become(happy)
  }

  def happy: Receive = {
    case "bar" => println("I am already happy :-)")
    case "foo" => println("Becoming angry now"); become(angry)
  }

  def receive = {
    case "foo" => println("Becoming angry now"); become(angry)
    case "bar" => println("Becoming happy now"); become(happy)
  }
}
