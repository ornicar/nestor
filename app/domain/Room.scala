package nestor
package domain

import scalaz._, Scalaz._
import org.joda.time.DateTime

case class Room(
    id: Int,
    name: Option[String],
    nbBeds: Int,
    createdAt: DateTime) {

  def nonEmptyName = name | "Room %d".format(id)

  override def toString = nonEmptyName

  def data = Room.Data(
    name = name,
    nbBeds = nbBeds)
}

object Room {

  case class Data(name: Option[String], nbBeds: Int) {

    def apply: Valid[Int ⇒ Room] = Success { (id: Int) ⇒
      Room(id, name, nbBeds, DateTime.now)
    }
  }

  object Form {

    import play.api.data.{ Form ⇒ F }
    import play.api.data.Forms._

    val create = F(mapping(
      "name" -> optional(text),
      "nbBeds" -> number
    )(Data.apply)(Data.unapply))

    def update(room: Room) = create fill room.data
  }
}
