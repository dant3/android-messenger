package amber.preferences

import android.content.Context
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.dsl.module

actual val CorePreferencesModule: Module = module {
    single<ObservableSettings> {
        val context: Context = get()
        SharedPreferencesSettings(context.getSharedPreferences("amber_messenger_prefs", Context.MODE_PRIVATE))
    }
    single<AppPreferences> { AppPreferencesImpl(get()) }
}
