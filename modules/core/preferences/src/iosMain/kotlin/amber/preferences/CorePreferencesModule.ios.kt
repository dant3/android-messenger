package amber.preferences

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual val CorePreferencesModule: Module = module {
    single<ObservableSettings> {
        NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }
    single<AppPreferences> { AppPreferencesImpl(get()) }
}
