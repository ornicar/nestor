package nestor

import scala.concurrent.stm.Ref

sealed trait CollReadOnly[A <: Coll.Record] {

  def map: Map[Int, A] 

  def all: Iterable[A] 

  def byId(id: Int): Option[A] 

  def find(selector: A ⇒ Boolean): Option[A] 
}

case class Coll[A <: Coll.Record](ref: Ref[Map[Int, A]]) extends CollReadOnly[A] {

  def map: Map[Int, A] = ref.single.get

  def all: Iterable[A] = map.values

  def byId(id: Int): Option[A] = map get id

  def find(selector: A ⇒ Boolean): Option[A] = all find selector

  def insert(builder: Int ⇒ A): A = {
    update(builder(nextId))
  }

  def update(record: A): A = {
    ref.single.transform(_ + (record.id -> record))
    record
  }

  private def nextId: Int = map.size + 1
}

object Coll {

  type Record = { def id: Int }

  def empty[A <: Record]: Coll[A] = Coll(Ref(Map.empty[Int, A]))
}
