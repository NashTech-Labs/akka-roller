package com.knoldus.akka.health

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

// The futures created by the ask syntax need an
// execution context on which to run, and we will use the
// default global instance for that context
import scala.concurrent.ExecutionContext.Implicits.global

object Doctor extends App {

  implicit val timeout = Timeout(5.seconds)

  val system = ActorSystem("OperationSimulation")

  val theatre = system.actorOf(OperationTheater.props(), "OperationTheatre")
  // Grab the controls
  val heartLungMachine = Await.result((theatre ? OperationTheater.PassMeTheMachine).mapTo[ActorRef], timeout.duration)

  system.scheduler.scheduleOnce(200.millis) {
    heartLungMachine ! HeartLungMachine.PullBack(1f)
  }
  system.scheduler.scheduleOnce(1.seconds) {
    heartLungMachine ! HeartLungMachine.PullBack(0f)
  }
  system.scheduler.scheduleOnce(3.seconds) {
    heartLungMachine ! HeartLungMachine.PullBack(0.5f)
  }
  system.scheduler.scheduleOnce(4.seconds) {
    heartLungMachine ! HeartLungMachine.PullBack(0f)
  }

  // Shut down
  system.scheduler.scheduleOnce(5.seconds) {
    system.terminate()
  }

}