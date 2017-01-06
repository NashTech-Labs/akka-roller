package com.knoldus.akka.faulttolerance

import akka.actor.{Actor, ActorSystem, Props}

class LifeCycleHooks extends Actor {

  override def preStart(): Unit = {
    println(s"Pre Start ${this}")
    super.preStart()
  }

  override def postStop(): Unit = {
    println(s"Post Stop ${this}")
    super.postStop()
  }

  //override def preRestart(reason: Throwable, message: Option[Any]): Unit = println(s"Pre RE Start ${this}");super.preRestart(reason, message)

  @scala.throws[Exception](classOf[Exception])
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println(s"Pre RE Start ${this} with message $message")
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable): Unit = {
    println(s"Post RE Start ${this}")
    super.postRestart(reason)
  }

  def receive: Receive = {
    case _ => throw new Exception
  }
}

object LifeCycleHooks extends App {
  val system = ActorSystem("Now")
  val lifecyclehooks = system.actorOf(Props(classOf[LifeCycleHooks]))
  lifecyclehooks ! "yikes"
}
