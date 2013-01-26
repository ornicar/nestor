package nestor
package domain

import scalaz._, std.AllInstances._, std.option._, Scalaz._

case class Person(
    id: Int,
    firstName: String,
    lastName: String,
    country: Country) {
}

object Person {

  object Command {

    case class Create(firstName: String, lastName: String, countryCode: String) {
      def apply: Valid[Int ⇒ Person] = Country(countryCode) map { country ⇒
        (id: Int) ⇒ Person(id, firstName, lastName, country)
      } toSuccess { Error("Invalid country code: " + countryCode) }
    }
  }

  object Form {

    import play.api.data.{ Form ⇒ F }
    import play.api.data.Forms._
    import Command._

    lazy val create = F(mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "country" -> nonEmptyText.verifying(Country.all contains _)
    )(Create.apply)(Create.unapply))
  }
}
