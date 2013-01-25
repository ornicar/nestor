package controllers

import play.api._
import play.api.mvc._

object Main extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
}
