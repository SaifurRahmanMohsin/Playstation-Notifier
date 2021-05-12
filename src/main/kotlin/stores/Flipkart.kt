package stores

import contract.Store
import org.jsoup.Jsoup
import java.io.IOException
import java.net.UnknownHostException

class Flipkart: Store() {
	private var url = "https://www.flipkart.com/sony-playstation-5-cfi-1008a01r-825-gb-astro-s-playroom/p/itma0201bdea62fa"

	override suspend fun check(): Boolean {
		return try {
			val doc = Jsoup.connect(url).get()
			val isSoldOut = doc.select("#container > div > div._2c7YLP.UtUXW0._6t1WkM._3HqJxg > div._1YokD2._2GoDe3 > div._1YokD2._3Mn1Gg.col-8-12 > div > div._16FRp0").text()
			return !isSoldOut.contentEquals("Coming Soon")
		} catch (ex: UnknownHostException) {
			print("Unknown host exception")
			false
		} catch (ex: IOException) {
			false
		}
	}
}
