package nestor
package api

import akka.actor._
import akka.pattern.ask
import akka.dispatch._
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.stm.Ref

import org.eligosource.eventsourced.core._
import scalaz._, std.AllInstances._

import domain._
import Person.Command

final class PersonApi(
    personColl: Coll[Person],
    personProcessor: ActorRef)(implicit system: ActorSystem) {

  //
  // Consistent reads
  //

  def all = personColl.all

  val byId = personColl.byId _

  //
  // Updates
  //

  def create(firstName: String, lastName: String): Future[Valid[Person]] = {
    personProcessor ? Message(Command.Create(firstName, lastName))
  }.mapTo[Valid[Person]]

  implicit val timeout = Timeout(5 seconds)
}

// -------------------------------------------------------------------------------------------------------------
//  PersonProcessor is single writer to personColl, so we can have reads and writes in separate transactions
// -------------------------------------------------------------------------------------------------------------

class PersonProcessor(personColl: Coll[Person]) extends Actor { this: Emitter ⇒

  def receive = {

    case create: Command.Create ⇒ sender ! Success(personColl += create.apply)

  }
}
