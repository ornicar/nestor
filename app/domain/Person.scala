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
}
