package com.knoldus.akka.fsm

import akka.actor.FSM.{CurrentState, SubscribeTransitionCallBack, Transition, UnsubscribeTransitionCallBack}
import akka.actor.{Actor, ActorLogging, ActorRef, Props}

object MonitoringActor {

  def props(buncherActor: ActorRef): Props = Props(classOf[MonitoringActor], buncherActor)

}

class MonitoringActor(buncherActor: ActorRef) extends Actor with ActorLogging {

  override def preStart(): Unit = {
    log.info(s"Monitoring Actor started for monitoring Buncher Actor $buncherActor")

    buncherActor ! SubscribeTransitionCallBack(self)
  }

  override def postStop(): Unit = {
    log.info(s"Monitoring Actor stopped, now stopping monitoring for Buncher Actor $buncherActor")

    buncherActor ! UnsubscribeTransitionCallBack(self)
  }

  override def receive: Receive = {
    case CurrentState(ref, stateName)        => log.info(s"Current state of Buncher Actor $stateName")
    case Transition(ref, oldState, newState) => log.info(s"Buncher Actor transitioned from state $oldState to $newState")
  }

}
