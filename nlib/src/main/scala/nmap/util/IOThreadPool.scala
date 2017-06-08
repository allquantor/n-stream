package nmap.util

import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.{ExecutorService, Executors, ThreadFactory}

import scala.concurrent.ExecutionContext


trait IOThreadPool {

  private final val ThreadAmount = 100

  @deprecated("Should not be used for scanning.")
  private final val shortLivingTasksPool = Executors.newCachedThreadPool(
    new ThreadFactory {
      private val counter = new AtomicLong(0L)
      def newThread(r: Runnable): Thread = {
        val th = new Thread(r)
        th.setName("eon-io-thread-" +
          counter.getAndIncrement.toString)
        th.setDaemon(true)
        th
      }
    })

  private final val longBlockingTaskPool = Executors.newFixedThreadPool(ThreadAmount)

  implicit val ec = new ExecutionContext {
    val threadPool: ExecutorService = longBlockingTaskPool


    def execute(runnable: Runnable) {
      threadPool.submit(runnable)
    }

    def reportFailure(t: Throwable) {throw t}
  }

}
