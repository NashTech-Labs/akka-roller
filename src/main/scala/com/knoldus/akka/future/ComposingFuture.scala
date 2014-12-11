package com.knoldus.akka.future

import akka.actor.ActorSystem
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

object ComposingFuture {

}

object SumApplication extends App {
  val startTime = System.currentTimeMillis
  val number1 = timeTakingIdentityFunction(1)
  val number2 = timeTakingIdentityFunction(2)
  val number3 = timeTakingIdentityFunction(3)
  val sum = number1 + number2 + number3
  val elapsedTime = ((System.currentTimeMillis - startTime) / 1000.0)
  println("Sum of 1, 2 and 3 is " + sum + " calculated in " + elapsedTime + " seconds")

  def timeTakingIdentityFunction(number: Int) = {
    // we sleep for 3 seconds and return number
    Thread.sleep(1000)
    number
  }

  implicit val system = ActorSystem("future")
  val startTimeFuture = System.currentTimeMillis
  val future1 = Future(timeTakingIdentityFunction(1))
  val future2 = Future(timeTakingIdentityFunction(2))
  val future3 = Future(timeTakingIdentityFunction(3))

  val future = for {
    x <- future1
    y <- future2
    z <- future3
  } yield (x + y + z)

  future onSuccess {
    case sum =>
      val elapsedTime = ((System.currentTimeMillis - startTimeFuture) / 1000.0)
      println("F Sum of 1, 2 and 3 is " + sum + " calculated in " + elapsedTime + " seconds")
  }

  val startTimeFutureAgain = System.currentTimeMillis
  val futureAgain = for {
    x <- Future(timeTakingIdentityFunction(1))
    y <- Future(timeTakingIdentityFunction(2))
    z <- Future(timeTakingIdentityFunction(3))
  } yield (x + y + z)

  futureAgain onSuccess {
    case sum =>
      val elapsedTime = ((System.currentTimeMillis - startTimeFutureAgain) / 1000.0)
      println("F Sum of 1, 2 and 3 is " + sum + " calculated in " + elapsedTime + " seconds")
  }

}