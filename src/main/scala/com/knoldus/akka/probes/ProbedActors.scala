package com.knoldus.akka.probes

import akka.actor.Actor
import akka.actor.ActorRef

// An annoying Actor that just keeps screaming at us
  class AnnoyingActor(snooper: ActorRef) extends Actor {
    override def preStart() {
      self ! 'send
    }
    def receive = {
      case 'send =>
        snooper ! "Hello!!!"
        self ! 'send
    }
  }
  
  // A nice Actor that just says Hi once
  class NiceActor(snooper: ActorRef) extends Actor {
    override def preStart() {
      snooper ! "Hi"
    }
    def receive = {
      case _ =>
    }
  }