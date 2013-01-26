package nestor
package domain

import scalaz._, std.AllInstances._, std.option._, Scalaz._

case class Person(
    id: Int,
    firstName: String,
    lastName: String,
    country: Country,
    document: String,
    email: Option[String] = None,
    phone: Option[String] = None,
    notes: Option[String] = None) {
}

object Person {

  object Command {

    case class Create(
        firstName: String,
        lastName: String,
        countryCode: String,
        document: String,
        email: Option[String] = None,
        phone: Option[String] = None,
        notes: Option[String] = None) {
      def apply: Valid[Int ⇒ Person] = Country(countryCode) map { country ⇒
        (id: Int) ⇒ Person(
          id = id,
          firstName = firstName,
          lastName = lastName,
          country = country,
          document = document,
          email = email,
          phone = phone,
          notes = notes)
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
      "countryCode" -> nonEmptyText.verifying(Country.all contains _),
      "document" -> nonEmptyText,
      "email" -> optional(email),
      "phone" -> optional(nonEmptyText),
      "notes" -> optional(nonEmptyText)
    )(Create.apply)(Create.unapply))
  }
}
