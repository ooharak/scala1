import java.util.Date
import java.text.DateFormat._
import java.util.Locale

object FrenchDate {
	def main(args: Array[String]) {
		val now = new Date
		val df = getDateInstance(LONG, Locale.FRANCE)
		println(df format now)
	}
}