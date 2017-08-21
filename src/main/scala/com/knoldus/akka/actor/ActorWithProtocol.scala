package com.knoldus.akka.actor

import akka.actor.{Props, ActorSystem, Actor, Stash}

object ActorWithProtocolApp extends App {

  val system = ActorSystem("ActorWithProtocolSystem")
  val show = system.actorOf(ActorWithProtocol.props())

  show ! "write"
  show ! "write"
  show ! "write"
  show ! "open"
  show ! "open"
  show ! "close"
  show ! "write"

}

object ActorWithProtocol {

  def props(): Props = Props(classOf[ActorWithProtocol])

}

class ActorWithProtocol extends Actor with Stash {

  def receive: Receive = {
    case "open" =>
      println("Transitioned to open state!")
      unstashAll()
      context.become(
        {
          case "write" => println("Got a request for writing!")
          case "close" =>
            unstashAll()
            context.unbecome()
          case msg     =>
            println(s"Stashing message $msg from open state!")
            stash()
        },
        discardOld = false) // stack on top instead of replacing
    case msg    =>
      println(s"Stashing message $msg from initial state!")
      stash()
  }

}
