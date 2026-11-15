package io.github.kotlinmania.rustlang.core.num.imp.dec2flt

import io.github.kotlinmania.rustlang.core.num.imp.traits.FloatExt

/**
 * Extension to `Float` that are necessary for parsing using the Lemire method.
 *
 * See the parent module's doc comment for why this is necessary.
 *
 * Not intended for use outside of the `dec2flt` module.
 */
public interface Lemire<T> : FloatExt<T> {
    /**
     * Maximum exponent for a fast path case, or `⌊(SIG_BITS+1)/log2(5)⌋`
     */
    public val MAX_EXPONENT_FAST_PATH: Long
        get() =
            run {
                val log25 = 3.321928094887362 - 1.0
                (T.SIG_TOTAL_BITS.toDouble() / log25).toLong()
            }

    /**
     * Minimum exponent for a fast path case, or `-⌊(SIG_BITS+1)/log2(5)⌋`
     */
    public val MIN_EXPONENT_FAST_PATH: Long
        get() = -T.MAX_EXPONENT_FAST_PATH

    /**
     * Maximum exponent that can be represented for a disguised-fast path case.
     * This is `MAX_EXPONENT_FAST_PATH + ⌊(SIG_BITS+1)/log2(10)⌋`
     */
    public val MAX_EXPONENT_DISGUISED_FAST_PATH: Long
        get() = T.MAX_EXPONENT_FAST_PATH + (T.SIG_TOTAL_BITS.toDouble() / 3.321928094887362).toLong()

    /**
     * Maximum mantissa for the fast-path (`1<<53` for f64).
     */
    public val MAX_MANTISSA_FAST_PATH: ULong
        get() = 1uL shl T.SIG_TOTAL_BITS.toInt()

    /**
     * Gets a small power-of-ten for fast-path multiplication.
     */
    public fun pow10FastPath(exponent: Int): T

    /**
     * Converts integer into float through an as cast.
     * This is only called in the fast-path algorithm, and therefore
     * will not lose precision, since the value will always have
     * only if the value is <= Self.MaxMantissaFastPath.
     */
    public fun fromU64(v: ULong): T
}
