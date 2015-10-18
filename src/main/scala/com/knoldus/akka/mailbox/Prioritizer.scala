package com.knoldus.akka.mailbox

import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import akka.dispatch.{PriorityGenerator, UnboundedStablePriorityMailbox}
import com.typesafe.config.Config

object Prioritizer extends App {
  val system = ActorSystem("Now")
  val show = system.actorOf(Props(classOf[ShowStopper]).withMailbox("prio-mailbox"))
  show ! 'lowpriority
  show ! 'lowpriority
  show ! PoisonPill
  show ! 'lowpriority
  show ! 'lowpriority
  show ! 'something
  show ! 'something
  show ! 'lowpriority
  show ! 'highpriority

  system terminate()
}


class MyPrioMailbox(settings: ActorSystem.Settings, config: Config)
  extends UnboundedStablePriorityMailbox(
    // Create a new PriorityGenerator, lower prio means more important
    PriorityGenerator {
      // 'highpriority messages should be treated first if possible
      case 'highpriority => 0

      // 'lowpriority messages should be treated last if possible
      case 'lowpriority => 2

      // PoisonPill when no other left
      case PoisonPill => 3

      // We default to 1, which is in between high and low
      case otherwise => 1
    })


class ShowStopper extends Actor {
  def receive = {
    case x => println(s" I process almost anything! like - ${x} ")
  }
}