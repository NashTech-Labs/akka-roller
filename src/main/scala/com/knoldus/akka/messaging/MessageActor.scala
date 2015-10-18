package com.knoldus.akka.messaging

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.duration._

class MessageActor extends Actor with ActorLogging {

  def receive = {
    case _ => log info (s"Ok, i got it from $sender")
  }

}

class MessageSender(messageActor: ActorRef) extends Actor with ActorLogging {

  def receive = {
    case SendMessage => {
      log info (s"The sender to this actor is $sender")
      messageActor ! "Hi"
      messageActor.forward("Hi")
    }
  }

}

case object SendMessage

object TestMessages extends App {

  implicit val timeout = Timeout(5.seconds)
  val system = ActorSystem("MessageSimulation")
  val messageActor = system.actorOf(Props[MessageActor])
  val messageSender = system.actorOf(Props(new MessageSender(messageActor)), "Sender")
  messageSender ! SendMessage
}