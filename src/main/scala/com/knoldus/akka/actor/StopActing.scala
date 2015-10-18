package com.knoldus.akka.actor

import akka.actor.{Props, ActorSystem, Actor}

object StopActing extends App{
 val system = ActorSystem("Now")
  val show = system.actorOf(Props(classOf[PoisonMe]))
  show ! "hey"
  system stop show
  show ! "hey again"
  system terminate()


}

class ShowStopper extends Actor {
  def receive = {
    case x => println(s" I process almost anything! like - ${x} ")
  }

  override def postStop() = {
    println (" Aah! , I would seek revenge!")
  }
}
