package com.knoldus.akka.health

import akka.actor.{Actor, ActorLogging, Props}
import com.knoldus.akka.health.Monitor.MonitorUpdate
import com.knoldus.akka.health.OperationTheater._
import com.knoldus.akka.util.EventSource._

object OperationTheater {

  case object PassMeTheMachine

  def props(): Props = Props[OperationTheater]

}

class OperationTheater extends Actor with ActorLogging {

  val monitor = context.actorOf(Monitor.props(), "Monitor")
  val heartLungMachine = context.actorOf(HeartLungMachine.props(monitor), "HeartLungMachine")

  override def preStart() {
    monitor ! RegisterListener(self)
  }

  def receive = {
    case PassMeTheMachine         => sender ! heartLungMachine
    case MonitorUpdate(heartRate) => log info s"HeartRate is now: $heartRate"
  }

}
