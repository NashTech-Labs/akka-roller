package com.knoldus.akka.health

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.Props
import com.knoldus.akka.util.EventSource

class OperationTheater extends Actor with ActorLogging {

  import OperationTheater._
  import EventSource._

  val monitor = context.actorOf(Props[Monitor], "Monitor")
  val heartLungMachine = context.actorOf(Props(new HeartLungMachine(monitor)), "HeartLungMachine")
  def receive = {

    case PassMeTheMachine => sender ! heartLungMachine
    case MonitorUpdate(heartRate) =>
      log info (s"HeartRate is now: $heartRate")
  }

  override def preStart() {
    monitor ! RegisterListener(self)
  }

}

object OperationTheater {
  case object PassMeTheMachine
}
