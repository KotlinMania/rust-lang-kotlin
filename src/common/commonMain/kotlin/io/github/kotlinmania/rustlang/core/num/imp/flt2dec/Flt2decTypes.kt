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
}
