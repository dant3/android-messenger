package amber.analytics

class NoOpAnalyticsTracker : AnalyticsTracker {
    override fun trackEvent(name: String, params: Map<String, Any?>) = Unit
    override fun setUserProperty(name: String, value: String?) = Unit
}
