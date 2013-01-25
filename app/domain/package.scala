package nestor

import scalaz._, std.AllInstances._

package object domain {

  type Error = List[String]
  type Valid[A] = Validation[Error, A]

  object Error {
    def apply(msg: String): Error = List(msg)
  }
}
