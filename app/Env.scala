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

final class Env {

  private val logger = Logger("nestor")

  private implicit val system = ActorSystem("eventsourced")
  private implicit val timeout = Timeout(5 seconds)

  private lazy val journal = Journal(JournalioJournalProps(new File("target/journal")))
  private lazy val extension = EventsourcingExtension(system, journal)

  object person {

    private val coll = Coll.empty[Person]

    val processor: ActorRef = extension.processorOf(Props(
      new PersonProcessor(coll) with Emitter with Eventsourced { val id = 1 }
    ))

    val api = new PersonApi(coll, processor)
  }

  // force loading before the event recovery
  person.processor

  logger.info("Start recovery of event sourced messages")
  extension.recover()
  logger.info("Await processing of event sourced messages")
  extension.awaitProcessing()
  logger.info("Recovery of event sourced messages complete")
}
