package com.knoldus.akka.future

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global
import akka.dispatch.OnFailure

object WithActorNonBlocking extends App {
  implicit val timeout = Timeout(5 seconds)
  val system = ActorSystem("FaultTestingSystem")
  val a1 = system.actorOf(Props[A1])
  val a2 = system.actorOf(Props[A2])
  val f = a1 ? ""

  f onSuccess {
    case m => println("success"); a2 ! m
  }
  f onFailure {
    case m => println("failed"); a2 ! m
  }

}

class A1 extends Actor {
  def receive = {
    case _ => sender ! "Hello"
  }
}

class A2 extends Actor {
  def receive = {
    case s: String => println("got this")
    case _         => println("got something else")
  }
}
