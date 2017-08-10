package com.knoldus.akka.actor

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

object Init {
  val system = ActorSystem("PL")

  val driver = system.actorOf(Driver.props(), "Driver")
  val attendant = system.actorOf(Attendant.props(), "Attendant")
  val slotMonitor = system.actorOf(SlotMonitor.props(), "SlotMonitor")
}

object StartParking extends App {
  Init.driver ! "Request Parking"
}

object Driver {
  def props(): Props = Props[Driver]
}

class Driver extends Actor {
  def receive = {
    case "Request Parking" => Init.attendant ! "LetMePark"
    case x                 => println("Driver")
  }
}

object SlotMonitor {
  def props(): Props = Props[SlotMonitor]
}

class SlotMonitor extends Actor {
  def receive = {
    case "GiveMeEmptySlot" => sender ! 5
    case msg               => println("Slot Monitor")
  }
}

object Attendant {
  def props(): Props = Props[Attendant]
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
