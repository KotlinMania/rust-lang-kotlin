package io.github.kotlinmania.rustlang.std.sys.platformversion.darwin

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes

public open class Deferred<F>(
    public var value: F,
) {
    public var field0: F
        get() = value
        set(v) {
            value = v
        }

    public open fun drop() {
        (this.value)()
    }

    public companion object {
        public fun <F> typeLayout(typeLayoutF: TypeLayout<F>): TypeLayout<Deferred<F>> =
            object : TypeLayout<Deferred<F>> {
                override val alignBytes: Long = listOf(1L, typeLayoutF.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutF.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Deferred<F> =
                    Deferred(
                        (typeLayoutF).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Deferred<F>) {
                    (typeLayoutF).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Deferred<F> =
                    Deferred(
                        (typeLayoutF).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Deferred<F>) {
                    (typeLayoutF).writeBytes(buf, offset, value.value)
                }
            }
    }
}
