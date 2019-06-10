name := "UTN_Async"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.22"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8"
libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.8"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22"

libraryDependencies += "com.lihaoyi" %% "requests" % "0.1.7"
libraryDependencies += "com.lihaoyi" %% "upickle" % "0.7.1"