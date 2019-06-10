import java.util.concurrent.Executors

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object AppMultipleThreadsHiBoundedLoad {
  def main(args: Array[String]): Unit = {
    implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(80))

    println("Start")

    val start = System.currentTimeMillis

    val seq = (0 to 80).map(x => Future{
      (0 to 1000000).map(Math.sin(_)).sum
    })
    val f = Future.sequence(seq)
    Await.result(f, 120.seconds)


    println(s"Finish: ${System.currentTimeMillis - start}ms")
  }
}
