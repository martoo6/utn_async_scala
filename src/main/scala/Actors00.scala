import akka.actor.{Actor, ActorSystem, Props}
import scala.language.postfixOps

class HelloActor extends Actor {
  def receive = {
    case "hello" => println("hello back at you")
    case _       => println("wtf?")
  }
}

object Actors00 extends App {
    val system = ActorSystem("HelloSystem")

    val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
    helloActor ! "hello"
    helloActor ! "おはようございます"
}