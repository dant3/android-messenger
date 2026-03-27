package amber.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalSettingsApi::class)
internal class AppPreferencesImpl(
    settings: ObservableSettings,
) : AppPreferences {
    private val flowSettings = settings.toFlowSettings(Dispatchers.IO)

    override val theme: Preference<AppPreferences.Theme> = MappingPreference(
        settings = settings,
        key = "theme",
        defaultValue = AppPreferences.Theme.SYSTEM,
        serialize = { it.name },
        deserialize = { name ->
            AppPreferences.Theme.entries.firstOrNull { it.name == name } ?: AppPreferences.Theme.SYSTEM
        },
    )

    override val useDynamicColors: Preference<Boolean> = BooleanPreference(
        settings = settings,
        key = "use_dynamic_colors",
        defaultValue = true,
    )

    private inner class BooleanPreference(
        private val settings: ObservableSettings,
        private val key: String,
        override val defaultValue: Boolean,
    ) : Preference<Boolean> {
        override fun get(): Boolean = settings.getBoolean(key, defaultValue)
        override fun set(value: Boolean) = settings.putBoolean(key, value)
        override fun observe(): Flow<Boolean> = flowSettings.getBooleanFlow(key, defaultValue)
    }

    private inner class MappingPreference<T>(
        private val settings: ObservableSettings,
        private val key: String,
        override val defaultValue: T,
        private val serialize: (T) -> String,
        private val deserialize: (String) -> T,
    ) : Preference<T> {
        override fun get(): T {
            val raw = settings.getStringOrNull(key) ?: return defaultValue
            return deserialize(raw)
        }

        override fun set(value: T) = settings.putString(key, serialize(value))

        override fun observe(): Flow<T> = flowSettings.getStringOrNullFlow(key).map { raw ->
            if (raw != null) deserialize(raw) else defaultValue
        }
    }
}
