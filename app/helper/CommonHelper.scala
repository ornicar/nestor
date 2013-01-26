package nestor.helper

import org.joda.time.DateTime
import org.joda.time.format.{ DateTimeFormat, DateTimeFormatter }
import java.util.Locale

object CommonHelper { 

  private val formatter: DateTimeFormatter = DateTimeFormat forStyle "MS" withLocale new Locale("en")

  def showDate(date: DateTime): String = formatter print date

  implicit def richString(str: String) = new {
    def active(other: String, show: String = "active") = if (str == other) show else ""
  }
}
