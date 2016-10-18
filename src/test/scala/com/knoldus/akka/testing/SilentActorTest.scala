package com.knoldus.akka.testing

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.{FunSuite, FunSuiteLike, MustMatchers, WordSpecLike}
import SilentActor._

class SilentActorTest extends TestKit(ActorSystem("testsystem")) with FunSuiteLike  with StopSystemAfterAll {

  test("A Silent Actor must change internal state when it receives a message, single")  {
      val silentActor = TestActorRef[SilentActor]
      silentActor ! SilentMessage("whisper")
      assert(silentActor.underlyingActor.internalState.contains("whisper"))
    }


  test("A Silent Actor must change internal state when it receives a message, multiple threads")  {

      val silentActor = system.actorOf(Props[SilentActor], "s3")
      silentActor ! SilentMessage("whisper1")
      silentActor ! SilentMessage("whisper2")
      silentActor ! GetState(testActor)
      expectMsg(Vector("whisper1", "whisper2"))
    }

}
