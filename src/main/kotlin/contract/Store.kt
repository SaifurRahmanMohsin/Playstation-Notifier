package contract

import stores.AmazonIndia
import stores.Flipkart
import stores.PrepaidGamerCard
import stores.RelianceDigital

abstract class Store {
	companion object {
		val availableStores: LinkedHashMap<String, Class<*>> = linkedMapOf(
//			"Amazon India" to AmazonIndia::class.java,
			"Prepaidgamercard" to PrepaidGamerCard::class.java,
			"Reliance Digital" to RelianceDigital::class.java,
			"Flipkart" to Flipkart::class.java
		)

		/*
		 * Used to initialize the concrete class based on the type string
		 */
		fun create(
			aClass: Class<*>
		): Store? {
			return try {
				aClass.getDeclaredConstructor().newInstance() as Store
			} catch (e: InstantiationException) {
				null
			} catch (e: IllegalAccessException) {
				null
			}
		}
	}

	abstract suspend fun check() : Boolean
}
