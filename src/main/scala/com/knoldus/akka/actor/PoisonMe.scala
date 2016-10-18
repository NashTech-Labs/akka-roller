package com.knoldus.akka.actor

import akka.actor.{Actor, ActorSystem, Kill, PoisonPill, Props}

object PoisonPillMe extends App {
  val system = ActorSystem("Now")
  val show = system.actorOf(Props(classOf[PoisonMe]))
  show ! "hey1"
  show ! "hey2"
  show ! "hey3"
  show ! "hey4"
  show ! "hey5"
  show ! "hey6"
  show ! Kill // Try Kill
  show ! "hey again"
  system terminate()
}

class PoisonMe extends Actor {
  def receive = {
    case x => println(s" I process almost anything! like - ${x} ")
  }

  override def postStop() = {
    println(" Aah! , I would seek revenge!")
  }
}