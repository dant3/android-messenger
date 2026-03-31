package amber.preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import java.util.prefs.Preferences
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val PreferencesSettingsStorageModule: Module = module {
    single<ObservableSettings> {
        PreferencesSettings(Preferences.userRoot().node("amber-messenger"))
    }
}
