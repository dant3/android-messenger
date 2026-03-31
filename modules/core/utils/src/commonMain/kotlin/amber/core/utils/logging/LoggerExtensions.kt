package amber.core.utils.logging

import dev.shivathapaa.logger.api.LoggerFactory
import kotlin.reflect.KClass

inline fun <reified T : Any> LoggerFactory.get() = get(T::class)

fun <T : Any> LoggerFactory.get(kClass: KClass<T>) = get(LoggerNameResolver.name(kClass))

fun LoggerFactory.get(lambda: () -> Unit) = get(LoggerNameResolver.name(lambda))

inline fun <reified T : Any> logger() = LoggerFactory.get(T::class)

fun logger(lambda: () -> Unit) = LoggerFactory.get(lambda)
