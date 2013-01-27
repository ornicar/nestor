package nestor
package api

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future

import org.eligosource.eventsourced.core._

import domain.Room, Room._

final class RoomApi(
  coll: CollReadOnly[Room], 
  processor: ActorRef
)(implicit system: ActorSystem) extends CrudApi[Room, Room.Data] {

  def createForm = Room.Form.create 

  def updateForm(room: Room) = Room.Form update room

  // Consistent reads

  def all = coll.all

  val byId = coll.byId _

  // Updates

  private implicit val timeout = Timeout(5 seconds)

  def create(data: Data) =
    (processor ? Message(RoomApi.Create(data))).mapTo[Valid[Room]]

  def update(room: Room, data: Data) =
    (processor ? Message(RoomApi.Update(room.id, data))).mapTo[Valid[Room]]

}

private[api] object RoomApi {

  import play.api.libs.json._

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
//  RoomProcessor is single writer to coll, so we can have reads and writes in separate transactions
// -------------------------------------------------------------------------------------------------------------

class RoomProcessor(coll: Coll[Room]) extends Actor { this: Emitter ⇒

  def receive = {

    case create: RoomApi.Create ⇒ sender ! create.data.flatMap(_.apply map coll.insert)

    case update: RoomApi.Update ⇒ sender ! update.data.flatMap(_.apply map (f ⇒ coll.update(f(update.id))))
  }
}
