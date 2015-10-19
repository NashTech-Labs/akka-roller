package com.knoldus.akka.routing

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.routing.BroadcastPool

class PrintlnActor extends Actor {
  def receive = {
    case msg =>
      Thread.sleep(500)
      println("Received message '%s' in actor %s".format(msg, self.path.name))
  }
}

object RoundRobinRouterExample extends App {
  val system = ActorSystem("FaultTestingSystem")
  val roundRobinRouter =
    system.actorOf(Props[PrintlnActor].withRouter(BroadcastPool(5)), "router") // Try with RoundRobinPool, RandomPool, SmallestMailboxPool etc
  1 to 10 foreach {
    i => roundRobinRouter ! i
  }

}