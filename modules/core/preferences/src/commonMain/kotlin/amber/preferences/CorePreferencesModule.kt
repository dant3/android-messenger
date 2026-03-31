package amber.preferences

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal expect val PreferencesSettingsStorageModule: Module
val CorePreferencesModule: Module = module {
    includes(PreferencesSettingsStorageModule)

    singleOf(::AppPreferencesImpl).bind<AppPreferences>()
}
