package com.knoldus.akka.remoting

import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props
import java.util.Scanner

object LocalApplication extends App {
  val hostAddress: String = java.net.InetAddress.getLocalHost.getHostAddress()

  /** These settings can be externalized  */
  val configString = """akka {
  actor {
    provider = "akka.remote.RemoteActorRefProvider"
  }
  remote {
    enabled-transports = ["akka.remote.netty.tcp"]
    netty.tcp {
      hostname = "127.0.0.1"
      port = 2552
    }
 }
}"""
  val configuration = ConfigFactory.parseString(configString)
  
  val system = ActorSystem("Node1", ConfigFactory.load(configuration))
  val remoteActorReference = system.actorFor("akka.tcp://Node3@" + "127.0.0.1" + ":" + 2553 + "/user/Discovery")
  val local = system.actorOf(Props(new LocalActor(remoteActorReference)))
  val scanner = new Scanner(System.in)

  println("Send message to Remote")

  while (true) {

    val input = scanner.nextLine
    local ! Send(input)

  }
}

class LocalActor(remote: ActorRef) extends Actor {
  def receive = {
    case msg: Send =>
      //      println("Sending message to " + remote)
      remote ! msg
    case msg: Get =>
      println("----------------------------------------------------------")
      println("Message Received from Remote :" + msg.message)
      println("----------------------------------------------------------")
  }

}

case class Send(message: String)
case class Get(message: String)