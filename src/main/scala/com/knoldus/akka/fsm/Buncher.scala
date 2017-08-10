package com.knoldus.akka.fsm

import akka.actor.{Props, ActorRef, FSM}
import Buncher._
import scala.concurrent.duration._

object Buncher {

  final case class SetTarget(ref: ActorRef)
  final case class Queue(obj: Any)
  case object Flush

  final case class Batch(obj: Seq[Any])

  sealed trait State
  case object Idle extends State
  case object Active extends State

  sealed trait Data
  case object Uninitialized extends Data
  final case class Todo(target: ActorRef, queue: Seq[Any]) extends Data

  def props(): Props = Props[Buncher]

}

class Buncher extends FSM[State, Data] {

  startWith(Idle, Uninitialized)

  when(Idle) {
    case Event(SetTarget(ref), Uninitialized) => stay using Todo(ref, Vector.empty)
  }

  onTransition {
    case Active -> Idle =>
      stateData match {
        case Todo(ref, queue) => ref ! Batch(queue)
        case _                => // do nothing
      }
  }

  when(Active, stateTimeout = 5.second) {
    case Event(Flush | StateTimeout, t: Todo) => goto(Idle) using t.copy(queue = Vector.empty)
  }

  whenUnhandled {
    case Event(Queue(obj), t@Todo(_, v)) => goto(Active) using t.copy(queue = v :+ obj)
    case Event(e, s)                     =>
      log.warning("Received unhandled request {} in state {}/{}", e, stateName, s)
      stay
  }

  initialize()

}
