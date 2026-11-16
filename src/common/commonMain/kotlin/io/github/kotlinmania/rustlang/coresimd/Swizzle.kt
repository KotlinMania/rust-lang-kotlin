// port-lint: source swizzle.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.coresimd

/**
 * Creates a vector from the elements of another vector.
 */
public interface Swizzle {
    /**
     * Map from the elements of the input vector to the output vector.
     */
    public val INDEX: IntArray

    /**
     * Creates a new vector from the elements of `vector`.
     *
     * Lane `i` of the output is `vector[Self.INDEX[i]]`.
     */
    public fun <Mask, T> swizzle(vector: Simd<T>, m: Int): Simd<T> {
        // Safety: `vector` is a vector, and the index is a const vector of u32.
        return run { val _simdArg0 = vector; val _simdArg1 = vector; val _simdArg2 = run {
            // The index list needs to be returned as a vector.
            open class SimdShuffleIdx(
                public var value: UIntArray,
            ) {
                public var field0: UIntArray
                    get() = value
                    set(v) {
                        value = v
                    }
            }
            val output = MutableList((N).toInt()) { 0 }
            var i = 0
            while (i < N) {
                val index = Swizzle.INDEX[i]
                check((index.toUInt()).toInt() == index)
                check(index < m) { "source element index exceeds input vector length" }
                output[i] = index.toUInt()
                i += 1
            }
            SimdShuffleIdx(output)
        }; _simdArg0 }
    }

    /**
     * Creates a new vector from the elements of `first` and `second`.
     *
     * Lane `i` of the output is `concat[Self.INDEX[i]]`, where `concat` is the concatenation of
     * `first` and `second`.
     */
    public fun <Mask, T> concatSwizzle(first: Simd<T>, second: Simd<T>, m: Int): Simd<T> {
        // Safety: `first` and `second` are vectors, and the index is a const vector of u32.
        return run { val _simdArg0 = first; val _simdArg1 = second; val _simdArg2 = run {
            // The index list needs to be returned as a vector.
            open class SimdShuffleIdx(
                public var value: UIntArray,
            ) {
                public var field0: UIntArray
                    get() = value
                    set(v) {
                        value = v
                    }
            }
            val output = MutableList((N).toInt()) { 0 }
            var i = 0
            while (i < N) {
                val index = Swizzle.INDEX[i]
                check((index.toUInt()).toInt() == index)
                check(index < 2 * m) { "source element index exceeds input vector length" }
                output[i] = index.toUInt()
                i += 1
            }
            SimdShuffleIdx(output)
        }; _simdArg0 }
    }

    /**
     * Creates a new mask from the elements of `mask`.
     *
     * Element `i` of the output is `mask[Self.INDEX[i]]`.
     */
    public fun <T> swizzleMask(mask: Mask<T>, m: Int): Mask<T> where T : MaskElement<T> {
        // SAFETY: all elements of this mask come from another mask
        return Mask.fromSimdUnchecked(this.swizzle(mask.toSimd()))
    }

    /**
     * Creates a new mask from the elements of `first` and `second`.
     *
     * Element `i` of the output is `concat[Self.INDEX[i]]`, where `concat` is the concatenation of
     * `first` and `second`.
     */
    public fun <T> concatSwizzleMask(first: Mask<T>, second: Mask<T>, m: Int): Mask<T> where T : MaskElement<T> {
        // SAFETY: all elements of this mask come from another mask
        return Mask.fromSimdUnchecked(this.concatSwizzle(first.toSimd(), second.toSimd()))
    }
}

/**
 * Reverse the order of the elements in the vector.
 */
public fun <Mask, T> Simd<T>.reverse(n: Int): Simd<T> {
    open class Reverse : Swizzle {
        public val INDEX: IntArray = run {
            val index = MutableList(n) { 0 }
            var i = 0
            while (i < n) {
                index[i] = n - i - 1
                i += 1
            }
            index
        }
    }
    return Reverse.Swizzle(this)
}

/**
 * Rotates the vector such that the first `OFFSET` elements of the slice move to the end
 * while the last `self.len() - OFFSET` elements move to the front. After calling `rotateElementsLeft`,
 * the element previously at index `OFFSET` will become the first element in the slice.
 * ```kotlin
 * val a = Simd.fromArray(listOf(0, 1, 2, 3))
 * val x = a.rotateElementsLeft()
 * check(x.toArray() == listOf(3, 0, 1, 2))
 * val y = a.rotateElementsLeft()
 * check(y.toArray() == listOf(3, 0, 1, 2))
 * ```
 */
public fun <Mask, T> Simd<T>.rotateElementsLeft(n: Int, offset: Int): Simd<T> {
    open class Rotate : Swizzle {
        public val INDEX: IntArray = run {
            val offset = offset % n
            val index = MutableList(n) { 0 }
            var i = 0
            while (i < n) {
                index[i] = (i + offset) % n
                i += 1
            }
            index
        }
    }
    return Rotate.Swizzle(this)
}

/**
 * Rotates the vector such that the first `self.len() - OFFSET` elements of the vector move to
 * the end while the last `OFFSET` elements move to the front. After calling `rotateElementsRight`,
 * the element previously at index `self.len() - OFFSET` will become the first element in the slice.
 * ```kotlin
 * val a = Simd.fromArray(listOf(0, 1, 2, 3))
 * val x = a.rotateElementsRight()
 * check(x.toArray() == listOf(1, 2, 3, 0))
 * val y = a.rotateElementsRight()
 * check(y.toArray() == listOf(1, 2, 3, 0))
 * ```
 */
public fun <Mask, T> Simd<T>.rotateElementsRight(n: Int, offset: Int): Simd<T> {
    open class Rotate : Swizzle {
        public val INDEX: IntArray = run {
            val offset = n - offset % n
            val index = MutableList(n) { 0 }
            var i = 0
            while (i < n) {
                index[i] = (i + offset) % n
                i += 1
            }
            index
        }
    }
    return Rotate.Swizzle(this)
}

/**
 * Shifts the vector elements to the left by `OFFSET`, filling in with
 * `padding` from the right.
 * ```kotlin
 * val a = Simd.fromArray(listOf(0, 1, 2, 3))
 * val x = a.shiftElementsLeft(255)
 * check(x.toArray() == listOf(3, 255, 255, 255))
 * val y = a.shiftElementsLeft(255)
 * check(y.toArray() == listOf(255, 255, 255, 255))
 * ```
 */
public fun <Mask, T> Simd<T>.shiftElementsLeft(padding: T, n: Int, offset: Int): Simd<T> {
    open class Shift : Swizzle {
        public val INDEX: IntArray = run {
            val index = IntArray(n) { n }
            var i = 0
            while (i + offset < n) {
                index[i] = i + offset
                i += 1
            }
            index
        }
    }
    return Shift.concatSwizzle(this, Simd.splat(padding))
}

/**
 * Shifts the vector elements to the right by `OFFSET`, filling in with
 * `padding` from the left.
 * ```kotlin
 * val a = Simd.fromArray(listOf(0, 1, 2, 3))
 * val x = a.shiftElementsRight(255)
 * check(x.toArray() == listOf(255, 255, 255, 0))
 * val y = a.shiftElementsRight(255)
 * check(y.toArray() == listOf(255, 255, 255, 255))
 * ```
 */
public fun <Mask, T> Simd<T>.shiftElementsRight(padding: T, n: Int, offset: Int): Simd<T> {
    open class Shift : Swizzle {
        public val INDEX: IntArray = run {
            val index = IntArray(n) { n }
            var i = offset
            while (i < n) {
                index[i] = i - offset
                i += 1
            }
            index
        }
    }
    return Shift.concatSwizzle(this, Simd.splat(padding))
}

/**
 * Interleave two vectors.
 *
 * The resulting vectors contain elements taken alternatively from `self` and `other`, first
 * filling the first result, and then the second.
 *
 * The reverse of this operation is [Simd.deinterleave].
 *
 * ```kotlin
 * val a = Simd.fromArray(listOf(0, 1, 2, 3))
 * val b = Simd.fromArray(listOf(4, 5, 6, 7))
 * val (x, y) = a.interleave(b)
 * check(x.toArray() == listOf(0, 4, 1, 5))
 * check(y.toArray() == listOf(2, 6, 3, 7))
 * ```
 */
public fun <Mask, T> Simd<T>.interleave(other: Simd<T>, n: Int): Pair<Simd<T>, Simd<T>> {
    fun interleave(high: Boolean, n: Int): IntArray {
        val idx = MutableList((n1).toInt()) { 0 }
        var i = 0
        while (i < n1) {
            val dstIndex =
                if (high) {
                    i + n1
                } else {
                    i
                }
            val srcIndex = dstIndex / 2 + (dstIndex % 2) * n1
            idx[i] = srcIndex
            i += 1
        }
        return idx
    }
    open class Lo : Swizzle {
        public val INDEX: IntArray = interleave(false, n)
    }
    open class Hi : Swizzle {
        public val INDEX: IntArray = interleave(true, n)
    }
    return Pair(Lo.concatSwizzle(this, other), Hi.concatSwizzle(this, other))
}

/**
 * Deinterleave two vectors.
 *
 * The first result takes every other element of `self` and then `other`, starting with
 * the first element.
 *
 * The second result takes every other element of `self` and then `other`, starting with
 * the second element.
 *
 * The reverse of this operation is [Simd.interleave].
 *
 * ```kotlin
 * val a = Simd.fromArray(listOf(0, 4, 1, 5))
 * val b = Simd.fromArray(listOf(2, 6, 3, 7))
 * val (x, y) = a.deinterleave(b)
 * check(x.toArray() == listOf(0, 1, 2, 3))
 * check(y.toArray() == listOf(4, 5, 6, 7))
 * ```
 */
public fun <Mask, T> Simd<T>.deinterleave(other: Simd<T>, n: Int): Pair<Simd<T>, Simd<T>> {
    fun deinterleave(second: Boolean, n: Int): IntArray {
        val idx = MutableList((n1).toInt()) { 0 }
        var i = 0
        while (i < n1) {
            idx[i] = i * 2 + second.toInt()
            i += 1
        }
        return idx
    }
    open class Even : Swizzle {
        public val INDEX: IntArray = deinterleave(false, n)
    }
    open class Odd : Swizzle {
        public val INDEX: IntArray = deinterleave(true, n)
    }
    return Pair(Even.concatSwizzle(this, other), Odd.concatSwizzle(this, other))
}

/**
 * Resize a vector.
 *
 * If `M` > `N`, extends the length of a vector, setting the new elements to `value`.
 * If `M` < `N`, truncates the vector to the first `M` elements.
 *
 * ```kotlin
 * val x = fromArray(listOf(0, 1, 2, 3))
 * check(x.resize(9).toArray() == listOf(0, 1, 2, 3, 9, 9, 9, 9))
 * check(x.resize(9).toArray() == listOf(0, 1))
 * ```
 */
public fun <Mask, T> Simd<T>.resize(value: T, n: Int, m: Int): Simd<T> {
    open class Resize : Swizzle {
        public val INDEX: IntArray = run {
            val index = MutableList(m) { 0 }
            var i = 0
            while (i < m) {
                index[i] = if (i < n) {
                    i
                } else {
                    n
                }
                i += 1
            }
            index
        }
    }
    return Resize.concatSwizzle(this, Simd.splat(value))
}

/**
 * Extract a vector from another vector.
 *
 * ```kotlin
 * val x = fromArray(listOf(0, 1, 2, 3))
 * check((x, extract, 1) == (2 > Unit.toArray()))
 * ```
 */
public fun <Mask, T> Simd<T>.extract(n: Int, start: Int, len: Int): Simd<T> {
    open class Extract : Swizzle {
        public val INDEX: IntArray = run {
            check(start + len <= n) { "index out of bounds" }
            val index = MutableList(len) { 0 }
            var i = 0
            while (i < len) {
                index[i] = start + i
                i += 1
            }
            index
        }
    }
    return Extract.Swizzle(this)
}

/**
 * Reverse the order of the elements in the mask.
 */
public fun <T> Mask<T>.reverse(n: Int): Mask<T> where T : MaskElement<T> {
    // Safety: swizzles are safe for masks
    return Mask.fromSimdUnchecked(this.toSimd().reverse())
}

/**
 * Rotates the mask such that the first `OFFSET` elements of the slice move to the end
 * while the last `self.len() - OFFSET` elements move to the front. After calling `rotateElementsLeft`,
 * the element previously at index `OFFSET` will become the first element in the slice.
 */
public fun <T> Mask<T>.rotateElementsLeft(n: Int, offset: Int): Mask<T> where T : MaskElement<T> {
    // Safety: swizzles are safe for masks
    return Mask.fromSimdUnchecked(this.toSimd().rotateElementsLeft())
}

/**
 * Rotates the mask such that the first `self.len() - OFFSET` elements of the mask move to
 * the end while the last `OFFSET` elements move to the front. After calling `rotateElementsRight`,
 * the element previously at index `self.len() - OFFSET` will become the first element in the slice.
 */
public fun <T> Mask<T>.rotateElementsRight(n: Int, offset: Int): Mask<T> where T : MaskElement<T> {
    // Safety: swizzles are safe for masks
    return Mask.fromSimdUnchecked(this.toSimd().rotateElementsRight())
}

/**
 * Shifts the mask elements to the left by `OFFSET`, filling in with
 * `padding` from the right.
 */
public fun <T> Mask<T>.shiftElementsLeft(padding: Boolean, n: Int, offset: Int): Mask<T> where T : MaskElement<T> {
    // Safety: swizzles are safe for masks
    return Mask.fromSimdUnchecked(this.toSimd().shiftElementsLeft(if (padding) {
        T.TRUE
    } else {
        T.FALSE
    }))
}

/**
 * Shifts the mask elements to the right by `OFFSET`, filling in with
 * `padding` from the left.
 */
public fun <T> Mask<T>.shiftElementsRight(padding: Boolean, n: Int, offset: Int): Mask<T> where T : MaskElement<T> {
    // Safety: swizzles are safe for masks
    return Mask.fromSimdUnchecked(this.toSimd().shiftElementsRight(if (padding) {
        T.TRUE
    } else {
        T.FALSE
    }))
}

/**
 * Interleave two masks.
 *
 * The resulting masks contain elements taken alternatively from `self` and `other`, first
 * filling the first result, and then the second.
 *
 * The reverse of this operation is [Mask.deinterleave].
 *
 * ```kotlin
 * val a = fromArray(booleanArrayOf(false, true, false, true))
 * val b = fromArray(booleanArrayOf(false, false, true, true))
 * val (x, y) = a.interleave(b)
 * check(x.toArray() == booleanArrayOf(false, false, true, false))
 * check(y.toArray() == booleanArrayOf(false, true, true, true))
 * ```
 */
public fun <T> Mask<T>.interleave(other: Mask<T>, n: Int): Pair<Mask<T>, Mask<T>> where T : MaskElement<T> {
    val (lo, hi) = this.toSimd().interleave(other.toSimd())
    // Safety: swizzles are safe for masks
    return Pair(Mask.fromSimdUnchecked(lo), Mask.fromSimdUnchecked(hi))
}

/**
 * Deinterleave two masks.
 *
 * The first result takes every other element of `self` and then `other`, starting with
 * the first element.
 *
 * The second result takes every other element of `self` and then `other`, starting with
 * the second element.
 *
 * The reverse of this operation is [Mask.interleave].
 *
 * ```kotlin
 * val a = fromArray(booleanArrayOf(false, true, false, true))
 * val b = fromArray(booleanArrayOf(false, false, true, true))
 * val (x, y) = a.deinterleave(b)
 * check(x.toArray() == booleanArrayOf(false, false, false, true))
 * check(y.toArray() == booleanArrayOf(true, true, false, true))
 * ```
 */
public fun <T> Mask<T>.deinterleave(other: Mask<T>, n: Int): Pair<Mask<T>, Mask<T>> where T : MaskElement<T> {
    val (even, odd) = this.toSimd().deinterleave(other.toSimd())
    // Safety: swizzles are safe for masks
    return Pair(Mask.fromSimdUnchecked(even), Mask.fromSimdUnchecked(odd))
}

/**
 * Resize a mask.
 *
 * If `M` > `N`, extends the length of a mask, setting the new elements to `value`.
 * If `M` < `N`, truncates the mask to the first `M` elements.
 *
 * ```kotlin
 * val x = fromArray(booleanArrayOf(false, true, true, false))
 * check(x.resize(true).toArray() == booleanArrayOf(false, true, true, false, true, true, true, true))
 * check(x.resize(true).toArray() == booleanArrayOf(false, true))
 * ```
 */
public fun <T> Mask<T>.resize(value: Boolean, n: Int, m: Int): Mask<T> where T : MaskElement<T> {
    // Safety: swizzles are safe for masks
    return Mask.fromSimdUnchecked(this.toSimd().resize(if (value) {
        T.TRUE
    } else {
        T.FALSE
    }))
}

/**
 * Extract a vector from another vector.
 *
 * ```kotlin
 * val x = fromArray(booleanArrayOf(false, true, true, false))
 * check((x, extract, 1) == (2 > Unit.toArray()))
 * ```
 */
public fun <T> Mask<T>.extract(n: Int, start: Int, len: Int): Mask<T> where T : MaskElement<T> {
    // Safety: swizzles are safe for masks
    return Mask.fromSimdUnchecked(this.toSimd().extract())
}
