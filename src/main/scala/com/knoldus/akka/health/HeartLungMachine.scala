package com.knoldus.akka.health

import akka.actor.Actor
import akka.actor.ActorRef

// The HeartLungMachine object carries messages for
// controlling the heart
object HeartLungMachine {
  case class PullBack(amount: Float)
  case class PushForward(amount: Float)
}

class HeartLungMachine(monitorRef: ActorRef) extends Actor {
  import HeartLungMachine._
  import Monitor._
  def receive = {
    // Doctor pulled the stick back by a certain amount,
    // and we inform the monitor that we're climbing
    case PullBack(amount) =>
      monitorRef ! RateChange(amount)

    // Doctor pushes the stick forward and we inform the
    // monitor that we're descending
    case PushForward(amount) =>
      monitorRef ! RateChange(-1 * amount)
  }

}