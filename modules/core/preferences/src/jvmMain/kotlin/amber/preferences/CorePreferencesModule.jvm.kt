package amber.preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import java.util.prefs.Preferences
import org.koin.core.module.Module
import org.koin.dsl.module

actual val CorePreferencesModule: Module = module {
    single<ObservableSettings> {
        PreferencesSettings(Preferences.userRoot().node("amber-messenger"))
    }
    single<AppPreferences> { AppPreferencesImpl(get()) }
}
