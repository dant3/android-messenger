package amber.preferences

import kotlinx.coroutines.flow.Flow

interface Preference<T> {
    val defaultValue: T

    fun get(): T
    fun set(value: T)
    fun observe(): Flow<T>
}

fun Preference<Boolean>.toggle() {
    set(!get())
}
