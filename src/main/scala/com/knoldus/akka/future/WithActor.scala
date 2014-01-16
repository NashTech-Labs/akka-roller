package com.knoldus.akka.future

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await

object WithActor extends App {
  implicit val timeout = Timeout(5 seconds)
  val system = ActorSystem("FaultTestingSystem")
  val a = system.actorOf(Props[A])
  val f = a ? ""
  val result = Await.result(f, timeout.duration).asInstanceOf[String]
  println(result)

}

class A extends Actor {
  def receive = {
    case _ => sender ! "Hello"
  }
}