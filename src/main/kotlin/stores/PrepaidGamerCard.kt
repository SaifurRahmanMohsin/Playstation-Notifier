package stores

import contract.Store
import org.jsoup.Jsoup
import java.net.UnknownHostException

class PrepaidGamerCard: Store() {
	private var url = "https://prepaidgamercard.com/product/playstation-5-console-ps5/"

	override suspend fun check(): Boolean {
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2")
		return try {
			val doc = Jsoup.connect(url).get()
			val items = doc.select("#product-285346 > div > div.product-main > div > div.product-info.summary.col.col-fit.entry-summary.product-summary > form")
			items.size == 1
		} catch (ex: UnknownHostException) {
			false
		}
	}
}
