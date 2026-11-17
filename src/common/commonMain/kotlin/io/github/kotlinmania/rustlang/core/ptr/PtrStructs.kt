package io.github.kotlinmania.rustlang.core.ptr

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes

/**
 * Used in [readUnaligned] and [writeUnaligned] to load and store `T`
 * with alignment 1 rather than its usual `alignOf<T>()` alignment.
 */
public open class Unaligned<T>(
    public var value: T,
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<Unaligned<T>> =
            object : TypeLayout<Unaligned<T>> {
                override val alignBytes: Long = listOf(1L, minOf(typeLayoutT.alignBytes, 1L)).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutT.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Unaligned<T> =
                    Unaligned(
                        (typeLayoutT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Unaligned<T>) {
                    (typeLayoutT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Unaligned<T> =
                    Unaligned(
                        (typeLayoutT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Unaligned<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.value)
                }
            }
    }
}
