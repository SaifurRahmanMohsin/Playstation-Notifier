package stores

import contract.Store
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.UnknownHostException

class RelianceDigital: Store() {
	private var url = "https://www.reliancedigital.in/search?q=playstation%205:relevance"

	override suspend fun check(): Boolean {
		return try {
			val doc = Jsoup.connect(url).get()
			val items = doc.select("#pl > div.pl__container > ul > li")
			for (item in items) {
				val itemName = item.select(".sp__name").text()
				if(itemName.contains("Sony PlayStation 5"))
					return true
			}
			return false
		} catch (ex: UnknownHostException) {
			print("Unknown host exception")
			false
		}
	}
}
