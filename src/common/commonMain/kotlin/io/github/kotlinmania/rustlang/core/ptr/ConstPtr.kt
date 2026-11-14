package io.github.kotlinmania.rustlang.core.ptr

public class ConstPtr(
    public val rawAddress: Long,
    public val rawMeta: Long = 0L,
) {
    override fun equals(other: Any?): Boolean =
        other is ConstPtr && rawAddress == other.rawAddress && rawMeta == other.rawMeta

    override fun hashCode(): Int = 31 * rawAddress.hashCode() + rawMeta.hashCode()

    override fun toString(): String = "0x" + rawAddress.toULong().toString(16)
}
