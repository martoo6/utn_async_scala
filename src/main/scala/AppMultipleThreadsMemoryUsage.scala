import java.util.concurrent.Executors

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object AppMultipleThreadsMemoryUsage {
  def main(args: Array[String]): Unit = {
    implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())

    println("Start")

    val start = System.currentTimeMillis

    val seq = (0 to 10000).map(x => Future{
      Thread.sleep(5000)
    })
    val f = Future.sequence(seq)
    Await.result(f, 120.seconds)


    println(s"Finish: ${System.currentTimeMillis - start}ms")
  }
}
