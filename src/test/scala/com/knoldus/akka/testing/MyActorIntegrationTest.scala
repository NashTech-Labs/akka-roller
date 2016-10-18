package com.knoldus.akka.testing

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestActors, TestKit}
import akka.util.Timeout
import org.scalatest.{BeforeAndAfterAll, FunSuiteLike}

import scala.concurrent.duration._

class MyActorIntegrationTest extends TestKit(ActorSystem("Simple")) with FunSuiteLike with BeforeAndAfterAll with ImplicitSender {

//  val echoRef = system.actorOf(TestActors.echoActorProps)
  implicit val timeout = Timeout(3.seconds)

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }


//  test("should return back the value that we pass") {
//    echoRef ! "test"
//    expectMsg("test")
//  }

  val myActorRef = TestActorRef[MyActor]

  test("should return back the value that we pass for MyActor") {
    myActorRef ! 52
    expectMsg(52)
    myActorRef ! "52"
    expectMsg("Hello")
  }

}
