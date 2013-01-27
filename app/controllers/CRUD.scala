package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.data.Form
import play.api.templates.Html

import scala.concurrent.Future

import nestor._
import api.CrudApi

trait CRUD[A <: Coll.Record, DataType] { self: Controller ⇒

  def api: CrudApi[A, DataType]
  def indexRoute: Call
  def createT: Form[DataType] ⇒ Html
  def updateT: (A, Form[DataType]) ⇒ Html

  def createForm = Action {
    Ok(createT(api.createForm))
  }

  def create = Action { implicit request ⇒
    Async {
      api.createForm.bindFromRequest.fold(
        errors ⇒ Future successful BadRequest(createT(errors)),
        data ⇒ api create data map {
          _ fold (
            errors ⇒ throw new RuntimeException(errors.toString),
            _ ⇒ Redirect(indexRoute)
          )
        }
      )
    }
  }

  def updateForm(id: Int) = Action {
    (api byId id) map { record ⇒
      Ok(updateT(record, api updateForm record))
    } getOrElse NotFound("No such record")
  }

  def update(id: Int) = Action { implicit request ⇒
    (api byId id) map { record ⇒
      Async {
        api.updateForm(record).bindFromRequest.fold(
          errors ⇒ Future successful BadRequest(updateT(record, errors)),
          data ⇒ api.update(record, data) map {
            _ fold (
              errors ⇒ BadRequest(errors.toString): Result,
              _ ⇒ Redirect(indexRoute)
            )
          }
        )
      }
    } getOrElse NotFound("No such record")
  }
}
