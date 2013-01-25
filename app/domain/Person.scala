package nestor
package domain

case class Person(
    id: Int,
    firstName: String,
    lastName: String) {

}

object Person {

  object Command {

    case class Create(firstName: String, lastName: String) {
      def apply(id: Int) = Person(id, firstName, lastName)
    }
  }

  object Form {

    import play.api.data.{ Form ⇒ F }
    import play.api.data.Forms._
    import Command._

    lazy val create = F(mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText
    )(Create.apply)(Create.unapply))
  }
}
