// port-lint: source index.rs
package io.github.kotlinmania.rustlang.core.index

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.intrinsics.sliceGetUnchecked
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.range.Range

// Helper types for indexing slices.

/**
 * Clamps an index, guaranteeing that it will only access valid elements of the slice.
 *
 * # Examples
 *
 * ```kotlin
 * import core.index.Clamp
 *
 * @feature(sliceindexWrappers)
 * public fun doctestEntry() {
 *     val s: List<Int> = listOf(0, 1, 2, 3)
 *     check(3 == s[Clamp(6)])
 *     check(listOf(1, 2, 3) == s[Clamp(1 until 6)])
 *     check(((listOf() as Any?) as List<Int>) == s[Clamp(5 until 6)])
 *     check(listOf(0, 1, 2, 3) == s[Clamp(0 until 6)])
 *     check(listOf(0, 1, 2, 3) == s[Clamp(0..6)])
 *     check(((listOf() as Any?) as List<Int>) == s[Clamp(6 until Int.MAX_VALUE)])
 * }
 * ```
 */
public open class Clamp<Idx>(
    public var value: Idx,
) {
    public var field0: Idx
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun <Idx> typeLayout(typeLayoutIdx: TypeLayout<Idx>): TypeLayout<Clamp<Idx>> =
            object : TypeLayout<Clamp<Idx>> {
                override val alignBytes: Long = listOf(1L, typeLayoutIdx.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutIdx.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Clamp<Idx> =
                    Clamp(
                        (typeLayoutIdx).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Clamp<Idx>) {
                    (typeLayoutIdx).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Clamp<Idx> =
                    Clamp(
                        (typeLayoutIdx).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Clamp<Idx>) {
                    (typeLayoutIdx).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * Always accesses the last element of the slice.
 *
 * # Examples
 *
 * ```kotlin
 * import core.index.Last
 * import core.slice.SliceIndex
 *
 * @feature(sliceindexWrappers)
 * @feature(sliceIndexMethods)
 * public fun doctestEntry() {
 *     val s = listOf(0, 1, 2, 3)
 *     check(3 == s[Last])
 *     check(null == Last.get(((listOf() as Any?) as List<Int>)))
 * }
 * ```
 */
public open class Last {
    public companion object {
        public fun typeLayout(): TypeLayout<Last> =
            object : TypeLayout<Last> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Last =
                    Last()
                override fun writeNative(addr: Long, value: Last) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Last =
                    Last()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Last) {
                }
            }
    }
}

public fun <T> Clamp<Int>.get(slice: List<T>): T? = slice.get(minOf(this.value, slice.size - 1))

public fun <T> Clamp<Int>.getMut(slice: MutableList<T>): T? = slice.getOrNull(minOf(this.value, slice.size - 1))

public fun Clamp<Int>.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller ensures that the slice isn't empty
    return sliceGetUnchecked(slice, minOf(this.value, slice.len() - 1))
}

public fun Clamp<Int>.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller ensures that the slice isn't empty
    return sliceGetUnchecked(slice, minOf(this.value, slice.len() - 1))
}

public fun <T> Clamp<Int>.index(slice: List<T>): T = slice[minOf(this.value, slice.size - 1)]

public fun <T> Clamp<Int>.indexMut(slice: MutableList<T>): T = slice[minOf(this.value, slice.size - 1)]

public fun <T> Clamp<range.Range<Int>>.getTSlice(slice: List<T>): List<T>? {
    val start = minOf(this.value.start, slice.size)
    val end = minOf(this.value.end, slice.size)
    return (start until end).get(slice)
}

public fun <T> Clamp<range.Range<Int>>.getMutTSlice(slice: MutableList<T>): List<T>? {
    val start = minOf(this.value.start, slice.size)
    val end = minOf(this.value.end, slice.size)
    return (start until end).getMut(slice)
}

public fun Clamp<range.Range<Int>>.getUncheckedTSlice(slice: ConstPtr): ConstPtr {
    val start = minOf(this.value.start, slice.len())
    val end = minOf(this.value.end, slice.len())
    // SAFETY: a range ending before len is always valid
    return (start until end).getUnchecked(slice)
}

public fun Clamp<range.Range<Int>>.getUncheckedMutTSlice(slice: MutPtr): MutPtr {
    val start = minOf(this.value.start, slice.len())
    val end = minOf(this.value.end, slice.len())
    // SAFETY: a range ending before len is always valid
    return (start until end).getUncheckedMut(slice)
}

public fun <T> Clamp<range.Range<Int>>.indexTSlice(slice: List<T>): List<T> {
    val start = minOf(this.value.start, slice.size)
    val end = minOf(this.value.end, slice.size)
    return (start until end).index(slice)
}

public fun <T> Clamp<range.Range<Int>>.indexMutTSlice(slice: MutableList<T>): List<T> {
    val start = minOf(this.value.start, slice.size)
    val end = minOf(this.value.end, slice.size)
    return (start until end).indexMut(slice)
}

public fun <T> Last.get(slice: List<T>): T? = slice.last()

public fun <T> Last.getMut(slice: MutableList<T>): T? = slice.lastOrNull()

public fun Last.getUnchecked(slice: ConstPtr): ConstPtr {
    // SAFETY: the caller ensures that the slice isn't empty
    return sliceGetUnchecked(slice, slice.len() - 1)
}

public fun Last.getUncheckedMut(slice: MutPtr): MutPtr {
    // SAFETY: the caller ensures that the slice isn't empty
    return sliceGetUnchecked(slice, slice.len() - 1)
}

public fun <T> Last.index(slice: List<T>): T {
    // N.B., use intrinsic indexing
    return slice[slice.size - 1]
}

public fun <T> Last.indexMut(slice: MutableList<T>): T {
    // N.B., use intrinsic indexing
    return slice[slice.size - 1]
}
