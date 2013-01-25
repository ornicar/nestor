package nestor

import domain._
import api._

import java.io.File
import akka.actor._
import scala.concurrent.duration._
import akka.util.Timeout
import org.eligosource.eventsourced.core._
import org.eligosource.eventsourced.journal.JournalioJournalProps
import play.api.Logger

trait Env {

  def logger: Logger

  def personProcessor: ActorRef
}

object Env {

  private val personProcessorId = 1

  def boot(): Env = new Env {

    implicit val system = ActorSystem("eventsourced")
    implicit val timeout = Timeout(5 seconds)

    val logger = Logger("nestor")

    val journal = Journal(JournalioJournalProps(new File("target/journal")))
    val extension = EventsourcingExtension(system, journal)

    val personsColl = Coll.empty[Person]

    val personProcessor: ActorRef = extension.processorOf(Props(
      new PersonProcessor(personsColl) with Emitter with Eventsourced {
        val id = personProcessorId
      }
    ))

    logger.info("Start recovery of event sourced messages")
    extension.recover()
    logger.info("Await processing of event sourced messages")
    extension.awaitProcessing()
    logger.info("Recovery of event sourced messages complete")
  }
}
