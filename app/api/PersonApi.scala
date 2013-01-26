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
import play.api.libs.json._

import domain._
import Person._

final class PersonApi(coll: CollReadOnly[Person], processor: ActorRef)(implicit system: ActorSystem) {

  def createForm = Person.Form create { doc ⇒ byDocument(doc).isEmpty }

  def updateForm(person: Person) = Person.Form.update(person, doc ⇒
    byDocument(doc).fold(true)(_.id == person.id)
  )

  // Consistent reads

  def all = coll.all

  val byId = coll.byId _

  private def byDocument(document: String) = coll find (_.document == document)

  // Updates

  private implicit val timeout = Timeout(5 seconds)

  def create(data: Person.Data) =
    (processor ? Message(PersonApi.Create(data))).mapTo[Valid[Person]]

  def update(person: Person, data: Person.Data) =
    (processor ? Message(PersonApi.Update(person.id, data))).mapTo[Valid[Person]]

}

private[api] object PersonApi {

  sealed trait WithJs {
    def js: String
    def data = read(js)
  }

  case class Create(js: String) extends WithJs
  def Create(data: Data) = new Create(write(data))

  case class Update(id: Int, js: String) extends WithJs
  def Update(id: Int, data: Data) = new Update(id, write(data))

  private def write(data: Data): String = Json stringify (Json.writes[Data] writes data)
  private def read(js: String): Valid[Data] = jsValid(Json.reads[Data] reads (Json parse js))
}

// -------------------------------------------------------------------------------------------------------------
//  PersonProcessor is single writer to coll, so we can have reads and writes in separate transactions
// -------------------------------------------------------------------------------------------------------------

class PersonProcessor(coll: Coll[Person]) extends Actor { this: Emitter ⇒

  def receive = {

    case create: PersonApi.Create ⇒ sender ! create.data.flatMap(_.apply map coll.insert)

    case update: PersonApi.Update ⇒ sender ! update.data.flatMap(_.apply map (f ⇒ coll.update(f(update.id))))
  }
}
