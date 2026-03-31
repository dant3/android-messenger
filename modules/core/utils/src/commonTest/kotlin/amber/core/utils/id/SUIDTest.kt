@file:OptIn(ExperimentalUuidApi::class)

package amber.core.utils.id

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SUIDTest: FunSpec({
    test("randomSuidCorrectness") {
        val suid = SUID.randomSUID()
        suid.toString().length shouldBe 22
    }

    test("suidUuidAreInterchangable") {
        val randomUUID = Uuid.random()
        val suid = SUID.fromUUID(randomUUID)

        val uuidFromSuid = SUID.toUUID(suid)
        uuidFromSuid shouldBe randomUUID
    }

    test("suidInvariantIsStable") {
        val someUUID = Uuid.parse("d9cc8343-4e97-4db4-96e1-7be5ac566974")
        val suid = SUID.fromString("6cynRkckvJmnqbZLVnFz4G")

        SUID.fromUUID(someUUID) shouldBe suid
    }
})
