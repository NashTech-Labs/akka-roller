package com.knoldus.akka.faulttolerance

// All that's needed for now are three components from Akka
import akka.actor.{ Actor, Props, ActorSystem }
// Our Actor
class FirstActorFault extends Actor {
  // The 'Business Logic'
  def receive = {
    case "Good Morning" =>
      println("Actor: A very good morning to you"); throw new Exception
    case "You're terrible" => println("Actor: Seriously ?")
  }
}

object FaultTest extends App {
  val system = ActorSystem("BadShakespearean")
  val actor = system.actorOf(Props[FirstActorFault], "Greetings")
  // We'll use this utility method to talk with our Actor
  def send(msg: String) {
    println(s"Me:  $msg")
    actor ! msg
    Thread.sleep(100)
  }
  // And our driver
  send("Good Morning")
  send("You're terrible")
  //system.shutdown()
}