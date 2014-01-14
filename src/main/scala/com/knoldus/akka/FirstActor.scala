package com.knoldus.akka

// All that's needed for now are three components from Akka
import akka.actor.{ Actor, Props, ActorSystem }
// Our Actor
class FirstActor extends Actor {
  // The 'Business Logic'
  def receive = {
    case "Good Morning" => println("Actor: A very good morning to you")
    case "You're terrible" => println("Actor: Seriously ?")
  }
}

object BadShakespeareanMain {
  val system = ActorSystem("BadShakespearean")
  val actor = system.actorOf(Props[FirstActor], "Greetings")
  // We'll use this utility method to talk with our Actor
  def send(msg: String) {
    println(s"Me:  $msg")
    actor ! msg
    Thread.sleep(100)
  }
  // And our driver
  def main(args: Array[String]) {
    send("Good Morning")
    send("You're terrible")
    system.shutdown()
  }
}