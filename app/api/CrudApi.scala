package nestor
package api

import play.api.data.Form

import scala.concurrent.Future

trait CrudApi[A <: Coll.Record, DataType] {

  def createForm: Form[DataType]
  def updateForm(record: A): Form[DataType]

  def all: Iterable[A]
  def byId: Int ⇒ Option[A]

  def create(data: DataType): Future[Valid[A]]
  def update(record: A, data: DataType): Future[Valid[A]]
}
