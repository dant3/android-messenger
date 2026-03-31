package amber.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitor {
    val isNetworkAvailable: StateFlow<Boolean>
}
