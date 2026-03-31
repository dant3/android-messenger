package amber.auth

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFRetain
import platform.CoreFoundation.CFTypeRef
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSCopyingProtocol
import platform.Foundation.NSMutableDictionary
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Security.SecItemAdd
import platform.Security.kSecAttrAccount
import platform.Security.kSecAttrService
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecValueData

/**
 * Verifies that [IosSecureStorage]'s dictionary bridging produces valid Security framework queries.
 *
 * Keychain operations return `-25291` (`errSecNoAccessForItem`) in K/N test runners because
 * the binary lacks entitlements. That's expected — the important thing is that it does NOT
 * return `-50` (`errSecParam`), which would mean the dictionary itself is malformed.
 */
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class IosSecureStorageTest : FunSpec() {
    init {
        test("keychain query produces valid dictionary") {
            val data = NSString.create("value").dataUsingEncoding(NSUTF8StringEncoding)!!
            val dict = NSMutableDictionary().apply {
                setObject(cfBridge(kSecClassGenericPassword), cfKey(kSecClass))
                setObject("test_svc", cfKey(kSecAttrService))
                setObject("test_key", cfKey(kSecAttrAccount))
                setObject(data, cfKey(kSecValueData))
            }
            @Suppress("UNCHECKED_CAST")
            val cfDict = CFBridgingRetain(dict) as CFDictionaryRef
            val status = SecItemAdd(cfDict, null)
            // -25291 = errSecNoAccessForItem (valid dict, no entitlement in test runner)
            // -50    = errSecParam (malformed dict — would indicate broken bridging)
            status shouldBe ERR_SEC_NO_ACCESS_FOR_ITEM
        }

        test("old map bridging produces broken dictionary") {
            val data = NSString.create("value").dataUsingEncoding(NSUTF8StringEncoding)!!
            val query = mapOf<Any?, Any?>(
                kSecClass to kSecClassGenericPassword,
                kSecAttrService to "test_svc",
                kSecAttrAccount to "test_key",
                kSecValueData to data,
            )
            @Suppress("UNCHECKED_CAST")
            val cfDict = CFBridgingRetain(query) as? CFDictionaryRef
            val status = SecItemAdd(cfDict, null)
            // Proves the old approach was broken: CFStringRef pointers in Kotlin Map
            // don't bridge to NSDictionary correctly
            status shouldNotBe ERR_SEC_NO_ACCESS_FOR_ITEM
            status shouldBe ERR_SEC_PARAM
        }
    }

    private companion object {
        const val ERR_SEC_PARAM = -50
        const val ERR_SEC_NO_ACCESS_FOR_ITEM = -25291

        fun cfBridge(ref: CFTypeRef?): Any = CFBridgingRelease(CFRetain(ref))!!

        @Suppress("UNCHECKED_CAST")
        fun cfKey(ref: CFTypeRef?): NSCopyingProtocol = CFBridgingRelease(CFRetain(ref)) as NSCopyingProtocol
    }
}
