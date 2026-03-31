package amber.auth

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFRetain
import platform.CoreFoundation.CFTypeRef
import platform.CoreFoundation.CFTypeRefVar
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSCopyingProtocol
import platform.Foundation.NSData
import platform.Foundation.NSMutableDictionary
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.errSecSuccess
import platform.Security.kSecAttrAccount
import platform.Security.kSecAttrService
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnData
import platform.Security.kSecValueData

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
internal class IosSecureStorage : SecureStorage {
    override fun getString(key: String): String? = memScoped {
        val query = keychainQuery(key)
        query.setObject(true, cfKey(kSecReturnData))
        query.setObject(cfBridge(kSecMatchLimitOne), cfKey(kSecMatchLimit))
        val result = alloc<CFTypeRefVar>()
        val status = SecItemCopyMatching(query.cfDictionary(), result.ptr)
        if (status != errSecSuccess) return null
        val data = CFBridgingRelease(result.value) as? NSData ?: return null
        NSString.create(data = data, encoding = NSUTF8StringEncoding).toString()
    }

    override fun putString(key: String, value: String) {
        remove(key)
        val data = NSString.create(value).dataUsingEncoding(NSUTF8StringEncoding) ?: return
        val query = keychainQuery(key)
        query.setObject(data, cfKey(kSecValueData))
        SecItemAdd(query.cfDictionary(), null)
    }

    override fun remove(key: String) {
        SecItemDelete(keychainQuery(key).cfDictionary())
    }

    override fun clear() {
        SecItemDelete(serviceQuery().cfDictionary())
    }

    private fun keychainQuery(key: String): NSMutableDictionary = serviceQuery().apply {
        setObject(key, cfKey(kSecAttrAccount))
    }

    private fun serviceQuery(): NSMutableDictionary = NSMutableDictionary().apply {
        setObject(cfBridge(kSecClassGenericPassword), cfKey(kSecClass))
        setObject(SERVICE_NAME, cfKey(kSecAttrService))
    }

    private companion object {
        const val SERVICE_NAME = "amber_messenger_auth"

        /** Bridge a CF constant to a Foundation object without ownership transfer. */
        fun cfBridge(ref: CFTypeRef?): Any = CFBridgingRelease(CFRetain(ref))!!

        /** Bridge a CFStringRef constant to an NSCopyingProtocol key for NSDictionary. */
        @Suppress("UNCHECKED_CAST")
        fun cfKey(ref: CFTypeRef?): NSCopyingProtocol = CFBridgingRelease(CFRetain(ref)) as NSCopyingProtocol

        @Suppress("UNCHECKED_CAST")
        fun NSMutableDictionary.cfDictionary(): CFDictionaryRef =
            CFBridgingRetain(this) as CFDictionaryRef
    }
}
