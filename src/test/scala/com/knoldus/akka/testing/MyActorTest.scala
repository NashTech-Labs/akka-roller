package com.knoldus.akka.testing

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.{BeforeAndAfterAll, FunSuiteLike}

class MyActorTest extends TestKit(ActorSystem("Simple")) with FunSuiteLike with BeforeAndAfterAll {

  val actorRef = TestActorRef(new MyActor)
  val actor = actorRef.underlyingActor

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  test("should return back the value that we pass") {
    assertResult(actor.doSomeFunnyCalculations(5))(actual = 5)
  }

}
