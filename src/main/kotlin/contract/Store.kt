package contract

import stores.AmazonIndia

abstract class Store () {

	companion object {
		val availableStores: Map<String, Class<*>> = hashMapOf(
			"Amazon India" to AmazonIndia::class.java
		)

		/*
		 * Used to initialize the concrete class based on the type string
		 */
		fun create(
			aClass: Class<*>
		): Store? {
			val field: Store
			return try {
				field = aClass.newInstance() as Store
				field
			} catch (e: InstantiationException) {
				null
			} catch (e: IllegalAccessException) {
				null
			}
		}
	}

	abstract suspend fun check() : Boolean
}
