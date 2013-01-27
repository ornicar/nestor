package nestor
package domain

import scalaz._, Scalaz._
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

  override def toString = fullName

  def data = Person.Data(
    firstName = firstName,
    lastName = lastName,
    countryCode = country.code,
    document = document,
    email = email,
    phone = phone,
    notes = notes)
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

  object Form {

    import play.api.data.{ Form ⇒ F }
    import play.api.data.Forms._

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
      create(documentUnique) fill person.data
  }
}
