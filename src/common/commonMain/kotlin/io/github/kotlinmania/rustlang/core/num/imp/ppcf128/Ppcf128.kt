// port-lint: source num/imp/ppcf128.rs
package io.github.kotlinmania.rustlang.core.num.imp.ppcf128

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

/**
 * The IBM extended-precision (double-double) floating-point lang item.
 *
 * Here we define just the lang item, the public-facing type is defined
 * in coreArch. It transparently wraps this definition.
 *
 * The ppcf128 type should only be used on PowerPC targets, but is always defined
 * for the purpose of doc builds, specifically doc builds in the stdarch repo.
 */
public open class Ppcf128(
    public var value: U128,
) {
    public var field0: U128
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Ppcf128 = Ppcf128(value)

    public companion object {
        public fun typeLayout(): TypeLayout<Ppcf128> =
            object : TypeLayout<Ppcf128> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Ppcf128 =
                    Ppcf128(
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Ppcf128) {
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ppcf128 =
                    Ppcf128(
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ppcf128) {
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeBytes(buf, offset, value.value)
                }
            }
    }
}
