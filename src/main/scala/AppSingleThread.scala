import java.util.concurrent.Executors
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.concurrent.duration._

object AppSingleThread {
  def main(args: Array[String]): Unit = {
    implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))

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
