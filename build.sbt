val ScalatraVersion = "2.7.1"

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / organization := "com.carlosedp"

lazy val hello = (project in file("."))
  .settings(
    name := "Scalatra Demo App",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % ScalatraVersion,
      "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
      "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
      "org.eclipse.jetty" % "jetty-webapp" % "9.4.35.v20201120" % "container",
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
      "org.scalatra" %% "scalatra-metrics" % ScalatraVersion,
      "io.prometheus" % "simpleclient" % "0.10.0",
      "io.prometheus" % "simpleclient_hotspot" % "0.10.0",
      "io.prometheus" % "simpleclient_servlet" % "0.10.0",
      "io.prometheus" % "simpleclient_dropwizard" % "0.10.0",
      "io.prometheus" % "simpleclient_logback" % "0.10.0",
    ),
  )

enablePlugins(SbtTwirl)
enablePlugins(JettyPlugin)
