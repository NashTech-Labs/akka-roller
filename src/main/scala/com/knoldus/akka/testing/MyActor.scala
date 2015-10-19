package com.knoldus.akka.testing

import akka.actor.{Actor, ActorLogging}

class MyActor extends Actor with ActorLogging{

  def receive ={
    case s:String => sender ! "Hello"
    case x => sender ! doSomeFunnyCalculations(x)
  }

  def doSomeFunnyCalculations(x:Any) = x
}

