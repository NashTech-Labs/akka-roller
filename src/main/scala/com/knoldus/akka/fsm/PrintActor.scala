package com.knoldus.akka.fsm

import akka.actor.{Props, ActorLogging, Actor}

object PrintActor {

  def props(): Props = Props[PrintActor]

}

class PrintActor extends Actor with ActorLogging {

  override def preStart(): Unit = {
    log info s"Print actor started"
  }

  def receive: Receive = {
    case msg => log info s"Got a message $msg"
  }

}
