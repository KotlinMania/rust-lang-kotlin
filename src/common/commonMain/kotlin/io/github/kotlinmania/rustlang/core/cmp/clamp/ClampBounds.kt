// rust-kotlin: manual
package io.github.kotlinmania.rustlang.core.cmp.clamp

/**
 * Trait implemented by range bounds that can clamp values via [clamp].
 */
public interface ClampBounds<T> {
    public fun clamp(value: T): T
}

/**
 * Clamp bounds specialization for [Float] values.
 */
public fun interface FloatClampBounds {
    public fun clamp(value: Float): Float
}

/**
 * Clamp bounds specialization for [Double] values.
 */
public fun interface DoubleClampBounds {
    public fun clamp(value: Double): Double
}
