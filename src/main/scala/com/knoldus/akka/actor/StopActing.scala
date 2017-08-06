package com.knoldus.akka.actor

import akka.actor.{Actor, ActorSystem, Props}

object StopActing extends App {
  val system = ActorSystem("Now")

  val show = system.actorOf(Props(classOf[ShowStopper]))

  show ! "hey"
  show ! "hey again"

  system stop show

  show ! "hey yet again"

  system terminate()
}

class ShowStopper extends Actor {
  override def postStop() = {
    println(" Aah! , I would seek revenge!")
  }

  def receive = {
    case msg => println(s" I process almost anything! like - $msg")
  }
}
