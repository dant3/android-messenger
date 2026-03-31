package amber.power

import amber.arch.lifecycle.LifecycleComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSProcessInfo
import platform.Foundation.NSProcessInfoPowerStateDidChangeNotification
import platform.Foundation.lowPowerModeEnabled

class IosPowerMonitor : PowerMonitor, LifecycleComponent {
    private val _powerMode = MutableStateFlow(currentPowerMode())
    override val powerMode: StateFlow<PowerMode> = _powerMode.asStateFlow()

    override fun init() {
        NSNotificationCenter.defaultCenter.addObserverForName(
            name = NSProcessInfoPowerStateDidChangeNotification,
            `object` = null,
            queue = NSOperationQueue.mainQueue,
        ) {
            _powerMode.value = currentPowerMode()
        }
    }

    private fun currentPowerMode(): PowerMode =
        if (NSProcessInfo.processInfo.lowPowerModeEnabled) PowerMode.LightIdle else PowerMode.Unrestricted
}
