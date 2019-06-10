# Scala Async examples (Futures (Promises), Actors, Thread Pools)

Examples on async programming with futures (called promises in Javascript), actors and thread pools

## Getting Started

### Prerequisites

- Java
- SBT
- Wiremock

### Installing

Download and install SBT (follow the instructions)

```
https://www.scala-sbt.org/download.html
```

Run in the root of the project (will take a while)

```
sbt compile
```

Download Wiremock (http://wiremock.org/docs/running-standalone/) to run certain examples and place the jar in `wiremock` folder

## Run

Some examples require a server that will respond to your requests, run wiremock in order to do that run the following command inside the wiremock folder

```
java -jar wiremock-standalone-2.23.2.jar
```

Then run an example you should do

```
sbt run
```

Then select the example you want to run by typing its number

## Built With

- [Sbt](https://www.scala-sbt.org/index.html) - Build tool
- [Scala](https://www.scala-lang.org/) - Programming Language
- [Akka](https://akka.io/) - Toolkit for building message-driven applications
- [JVM](https://www.java.com/en/) - Java virtual machine
- [Wiremock](http://wiremock.org/) - Simulator for HTTP-based APIs

## Authors

- **Martin Silberkasten**
