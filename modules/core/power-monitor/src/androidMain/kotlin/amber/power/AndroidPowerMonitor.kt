package amber.power

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.PowerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AndroidPowerMonitor(
    private val context: Context,
    private val scope: CoroutineScope,
) : PowerMonitor {
    private val powerManager = context.getSystemService(PowerManager::class.java)
    private val _powerMode = MutableStateFlow(currentPowerMode())
    override val powerMode: StateFlow<PowerMode> = _powerMode.asStateFlow()

    init {
        val filter = IntentFilter(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            filter.addAction(PowerManager.ACTION_DEVICE_LIGHT_IDLE_MODE_CHANGED)
        }

        context.registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    scope.launch { _powerMode.value = currentPowerMode() }
                }
            },
            filter,
        )
    }

    private fun currentPowerMode(): PowerMode = when {
        powerManager.isDeviceIdleMode -> PowerMode.Idle
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && powerManager.isDeviceLightIdleMode -> PowerMode.LightIdle
        else -> PowerMode.Unrestricted
    }
}
