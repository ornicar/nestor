package nestor
package domain

import scalaz._, std.AllInstances._, std.option._, Scalaz._
import org.joda.time.DateTime

case class Person(
    id: Int,
    firstName: String,
    lastName: String,
    country: Country,
    document: String,
    email: Option[String] = None,
    phone: Option[String] = None,
    notes: Option[String] = None,
    createdAt: DateTime) {

  def fullName = firstName + " " + lastName
}

object Person {

  case class Data(
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
        notes = notes,
        createdAt = DateTime.now)
    } toSuccess { Error("Invalid country code: " + countryCode) }
  }

  object Data {
    def apply(person: Person): Data = Data(
      firstName = person.firstName,
      lastName = person.lastName,
      countryCode = person.country.code,
      document = person.document,
      email = person.email,
      phone = person.phone,
      notes = person.notes)
  }

  object Command {
    case class Create(data: Data)
    case class Update(id: Int, data: Data)
  }

  object Form {

    import play.api.data.{ Form ⇒ F }
    import play.api.data.Forms._
    import Command._

    def create(documentUnique: String ⇒ Boolean) = F(mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "countryCode" -> nonEmptyText.verifying(Country.all contains _),
      "document" -> nonEmptyText
        .verifying("Already exists", documentUnique),
      "email" -> optional(email),
      "phone" -> optional(nonEmptyText),
      "notes" -> optional(nonEmptyText)
    )(Data.apply)(Data.unapply))

    def update(person: Person, documentUnique: String ⇒ Boolean) =
      create(documentUnique) fill Data(person)
  }
}
