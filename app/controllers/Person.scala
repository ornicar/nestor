package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

import nestor._

object Person extends Controller {

  private def api = Global.env.person.api

  def index = Action {
    Ok(api.all mkString "\n")
  }

  def createForm = Action {
    Ok(views.html.person.create(api.createForm))
  }

  def create = Action { implicit request ⇒
    Async {
      api.createForm.bindFromRequest.fold(
        errors ⇒ Future successful BadRequest(views.html.person.create(errors)),
        command ⇒ api create command map {
          _ fold (
            errors ⇒ BadRequest(errors.toString),
            person ⇒ Ok(person.toString)
          )
        }
      )
    }
  }

}
