package com.knoldus.akka.health

import akka.actor.{Actor, ActorRef, Props}

/**
  * The HeartLungMachine object carries messages for
  * controlling the heart
  */
object HeartLungMachine {

  case class PullBack(amount: Float)

  case class PushForward(amount: Float)

  def props(monitor: ActorRef): Props = Props(new HeartLungMachine(monitor))

}

class HeartLungMachine(monitorRef: ActorRef) extends Actor {

  import HeartLungMachine._
  import Monitor._

  def receive = {
    case PullBack(amount)    =>
      // Doctor pulled the stick back by a certain amount, and we inform the monitor that we're climbing
      monitorRef ! RateChange(amount)
    case PushForward(amount) =>
      // Doctor pushes the stick forward and we inform the monitor that we're descending
      monitorRef ! RateChange(-1 * amount)
  }

}
