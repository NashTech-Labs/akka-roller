package com.knoldus.akka.actor

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

object Init {
  val system = ActorSystem("PL")

  val driver = system.actorOf(Props[Driver], "Driver")
  val slotMonitor = system.actorOf(Props[SlotMonitor], "SlotMonitor")
  val attendant = system.actorOf(Props[Attendant], "Attendant")
}

object StartParking extends App {
  Init.driver ! "Request Parking"
}

class Driver extends Actor {
  def receive = {
    case "Request Parking" => Init.attendant ! "LetMePark"
    case x                 => println("Driver")
  }
}

class SlotMonitor extends Actor {
  def receive = {
    case "GiveMeEmptySlot" => sender ! 5
    case msg               => println("Slot Monitor")
  }
}

class Attendant extends Actor {
  implicit val timeout = Timeout(5.seconds)

  def receive = {
    case "LetMePark" =>
      println("Got the message to park")
      val parkingSlot = Await.result((Init.slotMonitor ? "GiveMeEmptySlot").mapTo[Int], timeout.duration)
      println(s"parking at $parkingSlot")
    case msg         => println("Attendant")
  }
}
