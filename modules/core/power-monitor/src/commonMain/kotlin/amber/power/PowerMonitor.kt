package amber.power

import kotlinx.coroutines.flow.StateFlow

interface PowerMonitor {
    val powerMode: StateFlow<PowerMode>
}
