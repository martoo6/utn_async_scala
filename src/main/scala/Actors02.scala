import akka.actor.{Actor, ActorSystem, Props}

import scala.language.postfixOps
import akka.pattern.{ask, pipe}
import akka.util.Timeout

import scala.concurrent.duration._

class HelloActor3 extends Actor {
  def receive = {
    case "hello" => sender() ! "hello back at you"
    case _       => sender() ! "wtf?"
  }
}

object Actors02 extends App {
    val system = ActorSystem("HelloSystem")
    implicit val dispatcher = system.dispatcher
    implicit val timeout = Timeout(5 seconds)

    val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
    helloActor ? "hello" andThen {case x => println(s"When asking in English returned: $x")}
    helloActor ? "おはようございます" andThen {case x => println(s"When asking in Japanese returned: $x")}
}