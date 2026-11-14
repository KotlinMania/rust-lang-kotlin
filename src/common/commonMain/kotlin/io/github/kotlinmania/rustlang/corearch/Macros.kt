// port-lint: source macros.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes

// Utility macros.

public open class SimdShuffleIdx(
    public var value: UIntArray,
) {
    public var field0: UIntArray
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun typeLayout(): TypeLayout<SimdShuffleIdx> =
            object : TypeLayout<SimdShuffleIdx> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes(((LEN).toLong() * (PrimitiveLayouts.U32).strideBytes), 4L)
                override fun readNative(addr: Long): SimdShuffleIdx =
                    SimdShuffleIdx(
                        (PrimitiveArrayLayouts.uIntArray((LEN).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SimdShuffleIdx) {
                    (PrimitiveArrayLayouts.uIntArray((LEN).toInt())).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SimdShuffleIdx =
                    SimdShuffleIdx(
                        (PrimitiveArrayLayouts.uIntArray((LEN).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SimdShuffleIdx) {
                    (PrimitiveArrayLayouts.uIntArray((LEN).toInt())).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * The first N indices `[0, 1, 2, ...]`.
 */
public fun identity(n: Int): UIntArray {
    val out = UIntArray(n) { 0u }
    var i = 0
    while (i < n) {
        out[i] = i.toUInt()
        i += 1
    }
    return out
}

/**
 * The first N even indices `[0, 2, 4, ...]`.
 */
public fun even(n: Int): UIntArray {
    val out = UIntArray(n) { 0u }
    var i = 0
    while (i < n) {
        out[i] = (2 * i).toUInt()
        i += 1
    }
    return out
}

/**
 * The first N odd indices `[1, 3, 5, ...]`.
 */
public fun odd(n: Int): UIntArray {
    val out = UIntArray(n) { 0u }
    var i = 0
    while (i < n) {
        out[i] = (2 * i + 1).toUInt()
        i += 1
    }
    return out
}

/**
 * Multiples of N offset by K `[K, K + N, K + 2N, ...]`.
 */
public fun deinterleaveMask(lanes: Int, n: Int, k: Int): UIntArray {
    val out = UIntArray(lanes) { 0u }
    var i = 0
    while (i < lanes) {
        out[i] = (i * n + k).toUInt()
        i += 1
    }
    return out
}

public fun interleaveMask(lanes: Int, n: Int, k: Int): UIntArray {
    val out = UIntArray(lanes) { 0u }
    var j = 0
    while (j < lanes) {
        out[j] = ((j % k) * n + j / k).toUInt()
        j += 1
    }
    return out
}
