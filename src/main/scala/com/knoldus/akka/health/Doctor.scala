package com.knoldus.akka.health

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.actor.Props
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await
import akka.actor.ActorRef
import akka.pattern.ask

// The futures created by the ask syntax need an
// execution context on which to run, and we will use the
// default global instance for that context
import scala.concurrent.ExecutionContext.Implicits.global

object Doctor extends App {

  implicit val timeout = Timeout(5.seconds)
  val system = ActorSystem("OperationSimulation")
  val theatre = system.actorOf(Props[OperationTheater], "OperationTheatre")

  // Grab the controls
  val control = Await.result((theatre ? OperationTheater.PassMeTheMachine).mapTo[ActorRef], 5.seconds)
  system.scheduler.scheduleOnce(200.millis) { control ! HeartLungMachine.PullBack(1f) }
  system.scheduler.scheduleOnce(1.seconds) { control ! HeartLungMachine.PullBack(0f) }
  system.scheduler.scheduleOnce(3.seconds) { control ! HeartLungMachine.PullBack(0.5f) }
  system.scheduler.scheduleOnce(4.seconds) { control ! HeartLungMachine.PullBack(0f) }

  // Shut down
  system.scheduler.scheduleOnce(5.seconds) { system.shutdown() }

}