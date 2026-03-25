package amber.core.utils.logging

import kotlin.reflect.KClass

@Suppress("NOTHING_TO_INLINE")
internal object LoggerNameResolver {
  /** get class name for function by the package of the function */
  internal inline fun name(noinline func: () -> Unit): String {
    val name = requireNotNull(func::class.simpleName)
    val slicedName =
        when {
          name.contains("Kt$") -> name.substringBefore("Kt$")
          name.contains("$") -> name.substringBefore("$")
          else -> name
        }
    return slicedName
  }

  /** get class name for java class (that usually represents kotlin class) */
  internal inline fun <T : Any> name(forClass: KClass<T>): String =
      requireNotNull(forClass.simpleName)
}
