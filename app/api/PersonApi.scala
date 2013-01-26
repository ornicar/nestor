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

  def createForm = Person.Form create { doc ⇒ byDocument(doc).isEmpty }

  def updateForm(person: Person) = Person.Form.update(person, doc ⇒
    byDocument(doc).fold(true)(_.id != person.id)
  )

  // Consistent reads

  def all = coll.all

  val byId = coll.byId _

  private def byDocument(document: String) = coll find (_.document == document)

  // Updates

  private implicit val timeout = Timeout(5 seconds)

  def create(data: Person.Data) =
    (processor ? Message(Command.Create(data))).mapTo[Valid[Person]]

  def update(person: Person, data: Person.Data) =
    (processor ? Message(Command.Update(person.id, data))).mapTo[Valid[Person]]
}

// -------------------------------------------------------------------------------------------------------------
//  PersonProcessor is single writer to coll, so we can have reads and writes in separate transactions
// -------------------------------------------------------------------------------------------------------------

class PersonProcessor(coll: Coll[Person]) extends Actor { this: Emitter ⇒

  def receive = {

    case Command.Create(data)     ⇒ sender ! (data.apply map (coll.insert))

    case Command.Update(id, data) ⇒ sender ! (data.apply map (f ⇒ coll.update(f(id))))

  }
}
