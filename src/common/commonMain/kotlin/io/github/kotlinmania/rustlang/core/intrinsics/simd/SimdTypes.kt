package io.github.kotlinmania.rustlang.core.intrinsics.simd

/**
 * A type for alignment options for SIMD masked load/store intrinsics.
 */
public sealed class SimdAlign(
    public val value: Int,
) {
    // These values must match the compiler's `SimdAlign` defined in
    // `rustcMiddle / src / ty / consts / int.rs`!

    /**
     * No alignment requirements on the pointer
     */
    public data object Unaligned : SimdAlign(0)

    /**
     * The pointer must be aligned to the element type of the SIMD vector
     */
    public data object Element : SimdAlign(1)

    /**
     * The pointer must be aligned to the SIMD vector type
     */
    public data object Vector : SimdAlign(2)

    public fun rustEquals(other: SimdAlign): Boolean = this == other
}
