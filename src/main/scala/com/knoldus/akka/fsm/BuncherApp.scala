package com.knoldus.akka.fsm

import akka.actor.{PoisonPill, ActorSystem}

object BuncherApp extends App {

  val system = ActorSystem("BuncherActorSystem")

  val printActor = system.actorOf(PrintActor.props(), "PrintActor")
  val buncherActor = system.actorOf(Buncher.props(), "BuncherActor")
  val monitoringActor = system.actorOf(MonitoringActor.props(buncherActor), "MonitoringActor")

  buncherActor ! "hello"

  buncherActor ! Buncher.SetTarget(printActor)

  buncherActor ! Buncher.Queue(1)
  buncherActor ! Buncher.Queue(2)
  buncherActor ! Buncher.Queue(3)

  // buncherActor ! Buncher.Flush

}
