package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

import nestor._
import views._

object Person extends Controller with CRUD[domain.Person, domain.Person.Data] {

  def api = Global.env.person.api
  def indexRoute = routes.Person.index
  def createT = form ⇒ html.person.create(form)
  def updateT = (person, form) ⇒ html.person.update(person, form)

  def index = Action {
    Ok(html.person.index(api.all))
  }
}
