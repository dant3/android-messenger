package amber.preferences

interface AppPreferences {
    val theme: Preference<Theme>
    val useDynamicColors: Preference<Boolean>

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM,
    }
}
