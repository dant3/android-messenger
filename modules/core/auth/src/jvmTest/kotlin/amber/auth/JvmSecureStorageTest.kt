package amber.auth

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createTempDirectory
import kotlin.io.path.deleteRecursively

@OptIn(ExperimentalPathApi::class)
class JvmSecureStorageTest : FunSpec({
    lateinit var tempDir: Path
    lateinit var storage: JvmSecureStorage

    beforeEach {
        tempDir = createTempDirectory("amber-auth-test")
        storage = JvmSecureStorage(storageDir = tempDir)
    }

    afterEach {
        tempDir.deleteRecursively()
    }

    test("getString returns null for missing key") {
        storage.getString("missing").shouldBeNull()
    }

    test("putString and getString round-trip") {
        storage.putString("key", "value")
        storage.getString("key") shouldBe "value"
    }

    test("remove deletes a key") {
        storage.putString("key", "value")
        storage.remove("key")
        storage.getString("key").shouldBeNull()
    }

    test("clear removes all keys") {
        storage.putString("a", "1")
        storage.putString("b", "2")
        storage.clear()
        storage.getString("a").shouldBeNull()
        storage.getString("b").shouldBeNull()
    }

    test("data persists across instances") {
        storage.putString("key", "persistent")
        val second = JvmSecureStorage(storageDir = tempDir)
        second.getString("key") shouldBe "persistent"
    }

    test("values with equals signs are stored correctly") {
        storage.putString("key", "a=b=c")
        storage.getString("key") shouldBe "a=b=c"
    }
})
