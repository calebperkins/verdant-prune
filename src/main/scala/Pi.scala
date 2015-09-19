import akka.actor.{Props, ActorSystem}

object Pi extends App {
  calculate(nrOfWorkers = 4, nrOfElements = 10000, nrOfMessages = 10000)

  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int): Unit = {
    val system = ActorSystem("PiSystem")
    val listener = system.actorOf(Props[Listener], name = "listener")
    val master = system.actorOf(Props(new Master(nrOfWorkers, nrOfMessages, nrOfElements, listener)), name = "master")
    master ! Calculate
  }
}
