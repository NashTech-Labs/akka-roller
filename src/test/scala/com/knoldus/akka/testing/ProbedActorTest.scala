package com.knoldus.akka.testing

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

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

