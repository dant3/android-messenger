package amber.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AndroidNetworkMonitor(
    context: Context,
    scope: CoroutineScope,
) : NetworkMonitor {
    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
    private val _isNetworkAvailable = MutableStateFlow(false)
    override val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable.asStateFlow()

    init {
        val currentNetwork = connectivityManager.activeNetwork
        val capabilities = currentNetwork?.let { connectivityManager.getNetworkCapabilities(it) }
        _isNetworkAvailable.value = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                scope.launch { _isNetworkAvailable.value = true }
            }

            override fun onLost(network: Network) {
                scope.launch { _isNetworkAvailable.value = false }
            }
        }

        connectivityManager.registerNetworkCallback(request, callback)
    }
}
