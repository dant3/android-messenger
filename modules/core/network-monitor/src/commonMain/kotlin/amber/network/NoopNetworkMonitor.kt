package amber.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoopNetworkMonitor : NetworkMonitor {
    override val isNetworkAvailable: StateFlow<Boolean> = MutableStateFlow(true).asStateFlow()
}