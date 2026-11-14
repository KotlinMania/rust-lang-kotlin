package io.github.kotlinmania.rustlang.corearch.aarch64.sve

internal interface AsUnsigned<out Unsigned> {
    public fun asUnsigned(): Unsigned
}

internal interface AsSigned<out Signed> {
    public fun asSigned(): Signed
}

/**
 * Same as `Into` but with into being unchecked so that it can have the required `targetFeature`
 */
internal interface SveInto<T> {
    public fun sveInto(): T
}

/**
 * Patterns returned by a `PTRUE`
 */
public sealed class Svpattern(
    public val value: Int,
) {
    /**
     * Activate the largest power-of-two number of elements that is less than the vector length
     */
    public data object SvPow2 : Svpattern(0)
    /**
     * Activate the first element
     */
    public data object SvVl1 : Svpattern(1)
    /**
     * Activate the first two elements
     */
    public data object SvVl2 : Svpattern(2)
    /**
     * Activate the first three elements
     */
    public data object SvVl3 : Svpattern(3)
    /**
     * Activate the first four elements
     */
    public data object SvVl4 : Svpattern(4)
    /**
     * Activate the first five elements
     */
    public data object SvVl5 : Svpattern(5)
    /**
     * Activate the first six elements
     */
    public data object SvVl6 : Svpattern(6)
    /**
     * Activate the first seven elements
     */
    public data object SvVl7 : Svpattern(7)
    /**
     * Activate the first eight elements
     */
    public data object SvVl8 : Svpattern(8)
    /**
     * Activate the first sixteen elements
     */
    public data object SvVl16 : Svpattern(9)
    /**
     * Activate the first thirty-two elements
     */
    public data object SvVl32 : Svpattern(10)
    /**
     * Activate the first sixty-four elements
     */
    public data object SvVl64 : Svpattern(11)
    /**
     * Activate the first one-hundred-and-twenty-eight elements
     */
    public data object SvVl128 : Svpattern(12)
    /**
     * Activate the first two-hundred-and-fifty-six elements
     */
    public data object SvVl256 : Svpattern(13)
    /**
     * Activate the largest multiple-of-four number of elements that is less than the vector length
     */
    public data object SvMul4 : Svpattern(29)
    /**
     * Activate the largest multiple-of-three number of elements that is less than the vector
     * length
     */
    public data object SvMul3 : Svpattern(30)
    /**
     * Activate all elements
     */
    public data object SvAll : Svpattern(31)

    public fun rustEquals(other: Svpattern): Boolean = this == other
}

/**
 * Addressing mode for prefetch intrinsics - allows the specification of the expected access
 * kind (read or write), the cache level to load the data, the data retention policy
 * (temporal or streaming)
 */
public sealed class Svprfop(
    public val value: Int,
) {
    /**
     * Temporal fetch of the addressed location for reading to the L1 cache (i.e. allocate in
     * cache normally)
     */
    public data object SvPldl1keep : Svprfop(0)
    /**
     * Streaming fetch of the addressed location for reading to the L1 cache (i.e. memory only
     * used once)
     */
    public data object SvPldl1strm : Svprfop(1)
    /**
     * Temporal fetch of the addressed location for reading to the L2 cache (i.e. allocate in
     * cache normally)
     */
    public data object SvPldl2keep : Svprfop(2)
    /**
     * Streaming fetch of the addressed location for reading to the L2 cache (i.e. memory only
     * used once)
     */
    public data object SvPldl2strm : Svprfop(3)
    /**
     * Temporal fetch of the addressed location for reading to the L3 cache (i.e. allocate in
     * cache normally)
     */
    public data object SvPldl3keep : Svprfop(4)
    /**
     * Streaming fetch of the addressed location for reading to the L3 cache (i.e. memory only
     * used once)
     */
    public data object SvPldl3strm : Svprfop(5)
    /**
     * Temporal fetch of the addressed location for writing to the L1 cache (i.e. allocate in
     * cache normally)
     */
    public data object SvPstl1keep : Svprfop(8)
    /**
     * Temporal fetch of the addressed location for writing to the L1 cache (i.e. memory only
     * used once)
     */
    public data object SvPstl1strm : Svprfop(9)
    /**
     * Temporal fetch of the addressed location for writing to the L2 cache (i.e. allocate in
     * cache normally)
     */
    public data object SvPstl2keep : Svprfop(10)
    /**
     * Temporal fetch of the addressed location for writing to the L2 cache (i.e. memory only
     * used once)
     */
    public data object SvPstl2strm : Svprfop(11)
    /**
     * Temporal fetch of the addressed location for writing to the L3 cache (i.e. allocate in
     * cache normally)
     */
    public data object SvPstl3keep : Svprfop(12)
    /**
     * Temporal fetch of the addressed location for writing to the L3 cache (i.e. memory only
     * used once)
     */
    public data object SvPstl3strm : Svprfop(13)

    public fun rustEquals(other: Svprfop): Boolean = this == other
}
