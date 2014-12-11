package com.knoldus.akka.faulttolerance

import scala.concurrent.duration.DurationInt
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.OneForOneStrategy
import akka.actor.Props
import akka.actor.SupervisorStrategy.Escalate
import akka.actor.SupervisorStrategy.Restart
import akka.actor.SupervisorStrategy.Resume
import akka.actor.SupervisorStrategy.Stop
import akka.actor.actorRef2Scala
import akka.actor.ActorSystem
import akka.actor.ActorRef

class Supervisor extends Actor with ActorLogging {
  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException =>
        println("Resuming the child"); Resume // try Restart here and note the difference in the state
      case _: NullPointerException     => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception                => Escalate
    }

  def receive: PartialFunction[Any, Unit] = {
    case p: Props => sender ! context.actorOf(p)
  }
}

class Child extends Actor {
  var state = 0

  override def preRestart(reason: Throwable,
                          message: Option[Any]) {
    println(s"This is the ugly message that killed me = $message")
  }

  def receive = {
    case ex: Exception => throw ex
    case x: Int        => state = x
    case "get"         => sender ! state
  }
}

class FaultTestStarterActor extends Actor {
  val mySupervisor = context.actorOf(Props[Supervisor], "supervisor")
  def receive = {
    case child: ActorRef =>
      child ! 10
      child ! new ArithmeticException
      child ! "get"
    case x: String => {
      mySupervisor ! Props[Child]
    }
    case y: Int => println(s"State is $y")
  }
}

object Starter extends App {
  val system = ActorSystem("FaultTestingSystem")
  system.actorOf(Props[FaultTestStarterActor]) ! "start"
}