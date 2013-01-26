package nestor.helper

import views.html.helper._

object FormHelper {
    
  implicit val formFields = FieldConstructor(views.html.snip.formFieldConstructor.f)    
    
}
