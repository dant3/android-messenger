package amber.auth

internal class FakeSecureStorage : SecureStorage {

    private val store = mutableMapOf<String, String>()

    override fun getString(key: String): String? = store[key]

    override fun putString(key: String, value: String) {
        store[key] = value
    }

    override fun remove(key: String) {
        store.remove(key)
    }

    override fun clear() {
        store.clear()
    }
}
