package controllers

import play.api._
import play.api.mvc._

import nestor._

object Person extends Controller with CRUD[domain.Person, domain.Person.Data] {

  def api = Global.env.person.api
  def indexRoute = routes.Person.index
  def createT = form ⇒ views.html.person.create(form)
  def updateT = (person, form) ⇒ views.html.person.update(person, form)

  def index = Action { Ok(views.html.person.index(api.all)) }
}
