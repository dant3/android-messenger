package amber.arch.lifecycle

import kotlin.jvm.JvmInline

@JvmInline
value class InitPriority(val value: Int) : Comparable<InitPriority> {
    override fun compareTo(other: InitPriority): Int = value.compareTo(other.value)

    operator fun inc(): InitPriority = InitPriority(value + 1)
    operator fun dec(): InitPriority = InitPriority(value - 1)

    operator fun plus(other: Int): InitPriority = InitPriority(value + other)
    operator fun minus(other: Int): InitPriority = InitPriority(value - other)

    companion object {
        val FIRST = InitPriority(Int.MAX_VALUE)
        val DEFAULT = InitPriority(0)
        val LAST = InitPriority(Int.MIN_VALUE)
    }
}
