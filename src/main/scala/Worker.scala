import akka.actor.Actor

class Worker extends Actor {
  override def receive: Receive = {
    case Work(start, nrOfElements) =>
      sender ! Result(calculatePiFor(start, nrOfElements))
  }

  def calculatePiFor(start: Int, nrOfElements: Int): Double = {
//    start.until(start + nrOfElements).map(i => 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)).sum
    var acc = 0.0
    for (i <- start until (start + nrOfElements))
      acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
    acc
  }
}

