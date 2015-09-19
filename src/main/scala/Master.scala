import scala.concurrent.duration._
import akka.routing.RoundRobinPool
import akka.actor.{Props, Actor, ActorRef}

class Master(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, listener: ActorRef) extends Actor {
  var pi: Double = _
  var nrOfResults: Int = _
  val start: Long = System.currentTimeMillis()

  val workerRouter = context.actorOf(Props[Worker].withRouter(RoundRobinPool(nrOfWorkers)), name = "workerRouter")

  override def receive: Receive = {
    case Calculate =>
      for (i <- 0 until nrOfMessages) workerRouter ! Work(i * nrOfElements, nrOfElements)
    case Result(value) =>
      pi += value
      nrOfResults += 1
      if (nrOfResults == nrOfMessages) {
        listener ! PiApproximation(pi, duration = (System.currentTimeMillis() - start).millis)
        context.stop(self)
      }
  }
}
