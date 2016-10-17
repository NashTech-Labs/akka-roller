package com.knoldus.akka.remoting

import akka.actor._
import com.typesafe.config.ConfigFactory

object DiscoveryService extends App {

  /* A directory to hold all remote references */
  val hostAddress: String = java.net.InetAddress.getLocalHost.getHostAddress()

  /** These settings can be externalized  */
  val configString =
  """akka {
  actor {
    provider = "akka.remote.RemoteActorRefProvider"
  }
  remote {
    enabled-transports = ["akka.remote.netty.tcp"]
    netty.tcp {
      hostname = "127.0.0.1"
      port = 2553
    }
 }
}"""
  val configuration = ConfigFactory.parseString(configString)

  val remoteSystem = ActorSystem("Node3", ConfigFactory.load(configuration))

  val discoveryActor = remoteSystem.actorOf(Props[DiscoveryServiceActor], "Discovery")
  println(discoveryActor.path)

  /**
    * Discovery actor which would receive registrations and hand out references
    */
  class DiscoveryServiceActor extends Actor {
    def receive = {
      case x => println(s"got the message $x")
    }
  }

}