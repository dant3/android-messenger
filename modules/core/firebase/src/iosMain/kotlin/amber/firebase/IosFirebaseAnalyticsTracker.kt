package amber.firebase

import amber.analytics.AnalyticsTracker
import cocoapods.FirebaseAnalytics.FIRAnalytics
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
class IosFirebaseAnalyticsTracker : AnalyticsTracker {
    override fun trackEvent(name: String, params: Map<String, Any?>) {
        @Suppress("UNCHECKED_CAST") // workaround for a lack of covariance in foreign api
        val castedParams = params as? Map<Any?, *>
        FIRAnalytics.logEventWithName(name, parameters = castedParams)
    }

    override fun setUserProperty(name: String, value: String?) {
        FIRAnalytics.setUserPropertyString(value, forName = name)
    }
}
