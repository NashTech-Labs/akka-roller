package com.knoldus.akka.health

import akka.actor.ActorLogging
import akka.actor.Actor
// Imports to help us create Actors, plus logging
import akka.actor.{ Props, Actor, ActorSystem, ActorLogging }
// The duration package object extends Ints with some
// timing functionality
import scala.concurrent.duration._

object Monitor {
  case class RateChange(amount: Float)
}

class Monitor extends Actor with ActorLogging {
  import Monitor._
  // We need an "ExecutionContext" for the scheduler.  This
  // Actor's dispatcher can serve that purpose.  The
  // scheduler's work will be dispatched on this Actor's own
  // dispatcher
  implicit val ec = context.dispatcher

  // The maximum ceiling of our heart in 'feet'
  val maxHeartRate = 200

  // The maximum rate of climb for our heart in
  // 'feet per minute'
  val maxRateOfClimb = 10

  // The varying rate of climb depending on the lung capacity
  var rateOfClimb = 0f

  // Our current heartRate
  var heartRate = 0d

  var lastTick = System.currentTimeMillis

  // We need to periodically update our heart rate.  This
  // scheduled message send will tell us when to do that
  val ticker = context.system.scheduler.schedule(100.millis, 100.millis, self, Tick)

  // An internal message we send to ourselves to tell us
  // to update our heartRate
  case object Tick

  def receive = {
    // Our rate of climb has changed
    case RateChange(amount) =>
      // Truncate the range of 'amount' to [-1, 1]
      // before multiplying
      rateOfClimb = amount.min(1.0f).max(-1.0f) *
        maxRateOfClimb
      log info (s"monitor changed rate of climb to $rateOfClimb.")

    // Calculate a new heartRate
    case Tick =>
      val tick = System.currentTimeMillis
      heartRate = heartRate + ((tick - lastTick) / 60000.0) * rateOfClimb
      lastTick = tick
  }

  // Kill our ticker when we stop
  override def postStop(): Unit = ticker.cancel
}