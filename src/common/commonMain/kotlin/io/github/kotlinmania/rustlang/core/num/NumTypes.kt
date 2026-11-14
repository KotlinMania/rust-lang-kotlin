package io.github.kotlinmania.rustlang.core.num

/**
 * A classification of floating point numbers.
 *
 * This `sealed class` is used as the return type for [f32.classify] and [f64.classify]. See
 * their documentation for more.
 *
 * # Examples
 *
 * ```kotlin
 * val num = 12.4f
 * val inf = Float.POSITIVE_INFINITY
 * val zero = 0.0f
 * val sub: Float = 1.1754942e-38f
 * val nan = Float.NaN
 * check(num.classify() == FpCategory.Normal)
 * check(inf.classify() == FpCategory.Infinite)
 * check(zero.classify() == FpCategory.Zero)
 * check(sub.classify() == FpCategory.Subnormal)
 * check(nan.classify() == FpCategory.Nan)
 * ```
 */
public sealed class FpCategory {
    /**
     * NaN (not a number): this value results from calculations like `(- 1.0).sqrt()`.
     *
     * See [the documentation for `Float`](f32) for more information on the unusual properties
     * of NaN.
     */
    public data object Nan : FpCategory()
    /**
     * Positive or negative infinity, which often results from dividing a nonzero number
     * by zero.
     */
    public data object Infinite : FpCategory()
    /**
     * Positive or negative zero.
     *
     * See [the documentation for `Float`](f32) for more information on the signedness of zeroes.
     */
    public data object Zero : FpCategory()
    /**
     * “Subnormal” or “denormal” floating point representation (less precise, relative to
     * their magnitude, than [Normal]).
     *
     * Subnormal numbers are larger in magnitude than [Zero] but smaller in magnitude than all
     * [Normal] numbers.
     *
     * [Normal]: Self.Normal
     * [Zero]: Self.Zero
     */
    public data object Subnormal : FpCategory()
    /**
     * A regular floating point number, not any of the exceptional categories.
     *
     * The smallest positive normal numbers are [f32.MinPositive] and [f64.MinPositive],
     * and the largest positive normal numbers are [f32.Max] and [f64.Max]. (Unlike signed
     * integers, floating point numbers are symmetric in their range, so negating any of these
     * constants will produce their negative counterpart.)
     */
    public data object Normal : FpCategory()

    public fun rustEquals(other: FpCategory): Boolean = this == other
}
