package com.knoldus.akka.faulttolerance

import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import akka.actor.Terminated
import akka.actor.Actor
import akka.actor.Props
import akka.actor.PoisonPill
import scala.concurrent.duration.DurationInt
import akka.actor.ActorSystem
import akka.actor.ActorRef

class MyActor(watchMe:ActorRef) extends Actor {
  override val supervisorStrategy =
    OneForOneStrategy(5, 1 minute) {
      case _ => Restart
    }

  override def preStart() {
    context.watch(watchMe)
  }

  def receive = {
    case Terminated(deadActor) =>
      println(deadActor.path.name + " has died")
  }
}

class SomeOtherActor extends Actor {
  def receive = {
    case _ => self ! PoisonPill
  }
}

object DeathGame extends App {
  val system = ActorSystem("DeathGame")
  val soa = system.actorOf(Props[SomeOtherActor])
  system.actorOf(Props(new MyActor(soa)))
  soa ! "die"
}
