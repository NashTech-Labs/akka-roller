package com.knoldus.akka.messaging

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import com.knoldus.akka.health.OperationTheater
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await
import akka.actor.ActorRef

class MessageActor extends Actor with ActorLogging {
  
  def receive = {
    case _=> log info (s"Ok, i got it from $sender")
  }

}

class MessageSender(messageActor:ActorRef) extends Actor with ActorLogging {
  
  def receive = {
    case SendMessage => {
      log info (s"The sender to this actor is $sender")
      messageActor ! "Hi"
      messageActor.forward("Hi")
    }
  }
  
  
}

case class SendMessage

object Tester extends App {
  
  implicit val timeout = Timeout(5.seconds)
  val system = ActorSystem("MessageSimulation")
  val messageActor = system.actorOf(Props[MessageActor])
  val messageSender = system.actorOf(Props(new MessageSender(messageActor)), "Sender")
  messageSender ! SendMessage
}