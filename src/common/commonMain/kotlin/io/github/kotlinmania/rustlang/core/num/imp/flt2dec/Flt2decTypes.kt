package io.github.kotlinmania.rustlang.core.num.imp.flt2dec

/**
 * Sign formatting options.
 */
public sealed class Sign {
    /**
     * Prints `-` for any negative value.
     */
    public data object Minus : Sign()
    // -inf -1 -0  0  1  inf nan

    /**
     * Prints `-` for any negative value, or `+` otherwise.
     */
    public data object MinusPlus : Sign()

    public fun rustEquals(other: Sign): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Minus -> 0
        MinusPlus -> 1
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}
