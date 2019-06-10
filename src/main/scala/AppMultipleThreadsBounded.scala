import java.util.concurrent.Executors

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object AppMultipleThreadsBounded {
  def main(args: Array[String]): Unit = {
    implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(8))

    println("Start")

    val start = System.currentTimeMillis

    val seq = (0 to 8).map(x => Future{
      Thread.sleep(500)
    })
    val f = Future.sequence(seq)
    Await.result(f, 30.seconds)


    println(s"Finish: ${System.currentTimeMillis - start}ms")
  }
}
