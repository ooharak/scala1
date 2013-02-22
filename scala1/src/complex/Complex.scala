package complex

class Complex(real: Double, imaginary: Double) {
	def ref() = real
	def imf() = imaginary
	def re = real
	def im = imaginary
	
	override def toString() =
	  "" + re + (if (im < 0) "" else "+") + im + "i"
}
