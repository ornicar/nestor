package controllers

import play.api._
import play.api.mvc._

import nestor._

object Room extends Controller with CRUD[domain.Room, domain.Room.Data] {

  def api = Global.env.room.api
  def indexRoute = routes.Room.index
  def createT = form ⇒ views.html.room.create(form)
  def updateT = (room, form) ⇒ views.html.room.update(room, form)

  def index = Action { Ok(views.html.room.index(api.all)) }
}
