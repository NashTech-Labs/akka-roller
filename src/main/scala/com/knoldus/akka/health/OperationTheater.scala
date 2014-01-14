package com.knoldus.akka.health

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.Props

class OperationTheater extends Actor with ActorLogging {

  import OperationTheater._
  val monitor = context.actorOf(Props[Monitor], "Monitor")
  val heartLungMachine = context.actorOf(Props(new HeartLungMachine(monitor)), "HeartLungMachine")
  def receive = {

    case PassMeTheMachine => sender ! heartLungMachine
  }

}

object OperationTheater {
  case object PassMeTheMachine
}
