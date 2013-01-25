package nestor

import scala.concurrent.stm.Ref

case class Coll[A <: Coll.Record](ref: Ref[Map[Int, A]]) {

  def all: Map[Int, A] = ref.single.get

  def byId(id: Int): Option[A] = all get id

  def list: Iterable[A] = all.values

  def +=(builder: Int => A): A = { 
    val record = builder(nextId)
    ref.single.transform(_ + (record.id -> record)) 
    record
  }

  private def nextId: Int = all.size + 1
}

object Coll {

  type Record = { def id: Int }

  def empty[A <: Record]: Coll[A] = Coll(Ref(Map.empty[Int, A]))
}
