package amber.power

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoopPowerMonitor : PowerMonitor {
    override val powerMode: StateFlow<PowerMode> = MutableStateFlow(PowerMode.Unrestricted).asStateFlow()
}
