import scalaz._, std.AllInstances._
import play.api.libs.json.JsResult

package object nestor {

  type Error = List[String]
  type Valid[A] = Validation[Error, A]

  object Error {
    def apply(msg: String): Error = List(msg)
  }

  def jsValid[A](jsResult: JsResult[A]): Valid[A] =
    Validation fromEither jsResult.asEither.left.map(_.toList.map(_.toString): Error)

  /**
   * K combinator implementation
   * Provides oneliner side effects
   * See http://hacking-scala.posterous.com/side-effecting-without-braces
   */
  implicit def ornicarAddKcombinator[A](any: A) = new {
    def kCombinator(sideEffect: A ⇒ Unit): A = {
      sideEffect(any)
      any
    }
    def ~(sideEffect: A ⇒ Unit): A = kCombinator(sideEffect)
    def pp: A = kCombinator(println)
  }
}
