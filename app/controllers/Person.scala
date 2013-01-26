package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

import nestor._

object Person extends Controller {

  private def api = Global.env.person.api

  def index = Action {
    Ok(views.html.person.index(api.all))
  }

  def createForm = Action {
    Ok(views.html.person.create(api.createForm))
  }

  def create = Action { implicit request ⇒
    Async {
      api.createForm.bindFromRequest.fold(
        errors ⇒ Future successful BadRequest(views.html.person.create(errors)),
        data ⇒ api create data map {
          _ fold (
            errors ⇒ BadRequest(errors.toString): Result,
            _ ⇒ Redirect(routes.Person.index)
          )
        }
      )
    }
  }

  def updateForm(id: Int) = Action {
    (api byId id) map { person ⇒
      Ok(views.html.person.update(person, api updateForm person))
    } getOrElse NotFound("No such person")
  }

  def update(id: Int) = Action { implicit request ⇒
    (api byId id) map { person ⇒
      Async {
        api.updateForm(person).bindFromRequest.fold(
          errors ⇒ Future successful BadRequest(views.html.person.update(person, errors)),
          data ⇒ api.update(person, data) map {
            _ fold (
              errors ⇒ BadRequest(errors.toString): Result,
              _ ⇒ Redirect(routes.Person.index)
            )
          }
        )
      }
    } getOrElse NotFound("No such person")
  }
}
