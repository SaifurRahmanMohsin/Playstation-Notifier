package stores

import contract.Store
import org.jsoup.Jsoup

class AmazonIndia : Store() {
	private var url = "https://amazon.in/b/ref=sxts_spks_0_0_448a69b1-af00-4c51-aafd-2122edb867bd?_encoding=UTF8&node=21725163031&qid=1617888417&pd_rd_w=jzXQP&pf_rd_p=448a69b1-af00-4c51-aafd-2122edb867bd&pf_rd_r=ZAMSZEFE2JF4VQ6CS3VA&pd_rd_r=4a5832ea-8d59-4120-a614-36a1db4560e4&pd_rd_wg=esvTK"

	override suspend fun check(): Boolean {
		val doc = Jsoup.connect(url).get()
		val items = doc.select(".acsUxWidget > div.acswidget-content-grid > div.bxc-grid__container > div")
		return if (items.size > 2)
			!items[2].select("div > div > div > div > h2").text().contentEquals("We’re temporarily out of stock on PS5. Subscribe to this page and stay tuned for updates.")
		else
			false
	}
}
