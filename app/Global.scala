package nestor

import play.api._

object Global extends GlobalSettings {

  lazy val env: Env = Env.boot()
}
