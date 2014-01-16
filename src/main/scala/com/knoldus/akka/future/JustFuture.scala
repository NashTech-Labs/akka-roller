package com.knoldus.akka.future

object JustFuture extends App {

  import scala.concurrent.Promise

  // Create a Promise
  val promise = Promise[String]()

  // Get the associated Future from that Promise
  val future = promise.future

  // Successfully fulfill the Promise
  promise.success("I always keep my promises!")

  // Extract the value from the Future
  println(future.value)

}