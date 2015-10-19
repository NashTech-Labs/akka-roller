package com.knoldus.akka.testing

import org.scalatest.Matchers
import akka.testkit.TestKit
import org.scalatest.BeforeAndAfterAll
import org.scalatest.WordSpecLike
import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.TestProbe

class ProbedActorTest extends TestKit(ActorSystem("ProbedActorsSpec"))
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  "The AnnoyingActor" should {
    "say Hello!!!" in {
      val p = TestProbe()
      val a = system.actorOf(Props(new AnnoyingActor(p.ref)))
      p.expectMsg("Hello!!!")
      system.stop(a)
    }
  }
  "The NiceActor" should {
    "say Hi" in {
      val p = TestProbe()
      val a = system.actorOf(Props(new NiceActor(p.ref)))
      p.expectMsg("Hi")
      system.stop(a)
    }
  }
}

