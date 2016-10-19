package com.knoldus.akka.actor

import akka.actor.{Props, ActorSystem, Actor}

object StopActing extends App{
 val system = ActorSystem("Now")
  val show = system.actorOf(Props(classOf[ShowStopper]))
  show ! "hey"
  show ! "hey again"
  system stop show
  show ! "hey yet again"
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
