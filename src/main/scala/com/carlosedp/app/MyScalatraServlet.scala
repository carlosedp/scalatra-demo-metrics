package com.carlosedp.app

import org.scalatra._
import org.scalatra.metrics.{MetricsSupport, HealthChecksSupport}
import java.util.concurrent.TimeUnit

import org.slf4j.{Logger, LoggerFactory}

class MyScalatraServlet
    extends ScalatraServlet
    with MetricsSupport
    with HealthChecksSupport {
  val logger = LoggerFactory.getLogger(getClass)
  healthCheck("response", unhealthyMessage = "Ouch!") { true }

  get("/") {
    val routeName = "root"
    val startTime = System.nanoTime

    // Call View
    val ret = views.html.hello()
    // Generate counter metric
    val c = Array(routeName,"get", "200")
    counter(c.mkString(".")) += 1

    // Log and return
    val elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime - startTime)
    logger.info("It took [" + routeName + "] " + elapsedTime + " " + TimeUnit.MILLISECONDS)
    ret
  }

  get("/user/:user") {
    val routeName = "user"
    val startTime = System.nanoTime
    val name = params("user")

    // Call View
    logger.info("Got user " + name)
    val ret = views.html.hello()

    // Generate counter metric
    val c = Array(routeName,"get", "200")
    counter(c.mkString(".")) += 1

    // Log and return
    val elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime - startTime)
    logger.info("It took [" + routeName + "] " + elapsedTime + " " + TimeUnit.MILLISECONDS)
    ret
  }

}
