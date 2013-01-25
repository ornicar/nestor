package nestor
package service

import scala.concurrent.stm.Ref

import akka.actor._
import akka.pattern.ask
import akka.dispatch._
import scala.concurrent.duration._
import akka.util.Timeout

import org.eligosource.eventsourced.core._
import domain._

class PersonService(personsRef: Ref[Map[String, Person]], personProcessor: ActorRef)(implicit system: ActorSystem) {

  //
  // Consistent reads
  //

  def getPersonsMap = personsRef.single.get
  def getPerson(personId: String): Option[Person] = getPersonsMap.get(personId)
  def getPersons: Iterable[Person] = getPersonsMap.values

  //
  // Updates
  //

  implicit val timeout = Timeout(5 seconds)
}

// -------------------------------------------------------------------------------------------------------------
//  PersonProcessor is single writer to personsRef, so we can have reads and writes in separate transactions
// -------------------------------------------------------------------------------------------------------------

class PersonProcessor(personsRef: Ref[Map[String, Person]]) extends Actor { this: Emitter ⇒

  def receive = {
    case Person.Command.Create(personId) ⇒
  }
}
