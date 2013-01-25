package nestor

import domain._
import service._

import scala.concurrent.stm.Ref
import java.io.File
import akka.actor._
import scala.concurrent.duration._
import akka.util.Timeout
import org.eligosource.eventsourced.core._
import org.eligosource.eventsourced.journal.JournalioJournalProps
import play.api.Logger

trait Env {

  def logger: Logger
}

object Env {

  private val personProcessorId = 1

  def boot(): Env = new Env {

    implicit val system = ActorSystem("eventsourced")
    implicit val timeout = Timeout(10 seconds)

    val logger = Logger("nestor")

    val journal = Journal(JournalioJournalProps(new File("target/journal")))
    val extension = EventsourcingExtension(system, journal)

    val personsRef = Ref(Map.empty[String, Person])

    val personProcessor: ActorRef = extension.processorOf(Props(
      new PersonProcessor(personsRef) with Emitter with Eventsourced {
        val id = personProcessorId
      }
    ))

    logger.info("Start recovery of event sourced messages")
    extension.recover()
    // wait for processor 1 to complete processing of replayed event messages
    // (ensures that recovery of externally visible state maintained by
    //  invoicesRef is completed when awaitProcessorCompletion returns)
    logger.info("Await processing of event sourced messages")
    extension.awaitProcessing()
    logger.info("Recovery of event sourced messages complete")

    // val invoiceService = new InvoiceService(invoicesRef, invoiceProcessor)
    // val statisticsService = new StatisticsService(statisticsRef)
  }
}
