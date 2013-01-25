package nestor
package domain

case class Person(
    id: String,
    firstName: String,
    lastName: String) {

}

object Person {

  object Command {

    case class Create(personId: String)
  }
}
