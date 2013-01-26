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

final class PersonApi(coll: Coll[Person], processor: ActorRef)(implicit system: ActorSystem) {

  //
  // Consistent reads
  //

  def all = coll.all

  val byId = coll.byId _

  //
  // Updates
  //

  private implicit val timeout = Timeout(5 seconds)

  def create(command: Command.Create): Future[Valid[Person]] = {
    processor ? Message(command)
  }.mapTo[Valid[Person]]
}

// -------------------------------------------------------------------------------------------------------------
//  PersonProcessor is single writer to coll, so we can have reads and writes in separate transactions
// -------------------------------------------------------------------------------------------------------------

class PersonProcessor(coll: Coll[Person]) extends Actor { this: Emitter ⇒

  def receive = {

    case create: Command.Create ⇒ sender ! (create.apply map (coll.+=))

  }
}
