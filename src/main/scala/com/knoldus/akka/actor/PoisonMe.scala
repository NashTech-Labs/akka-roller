package com.knoldus.akka.actor

import akka.actor.{PoisonPill, Actor, ActorSystem, Props}

object PoisonPillMe extends App{
  val system = ActorSystem("Now")
  val show = system.actorOf(Props(classOf[PoisonMe]))
  show ! "hey"
  show ! PoisonPill // Try Kill
  show ! "hey again"
  system terminate()
}

class PoisonMe extends Actor {
  def receive = {
    case x => println(s" I process almost anything! like - ${x} ")
  }

  override def postStop() = {
    println (" Aah! , I would seek revenge!")
  }
}