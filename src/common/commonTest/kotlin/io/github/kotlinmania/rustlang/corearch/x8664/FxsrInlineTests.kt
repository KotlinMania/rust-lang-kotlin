// port-lint: tests x86_64/fxsr.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.asMutPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes

// FXSR floating-point context fast save and restore.

public open class FxsaveArea(
    public var data: UByteArray,
) {
    public open fun ptr(): MutPtr = this.data.asMutPtr()

    public companion object {
        public fun typeLayout(): TypeLayout<FxsaveArea> =
            object : TypeLayout<FxsaveArea> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 512L
                override fun readNative(addr: Long): FxsaveArea =
                    FxsaveArea(
                        (PrimitiveArrayLayouts.uByteArray(512)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FxsaveArea) {
                    (PrimitiveArrayLayouts.uByteArray(512)).writeNative(addr, value.data)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FxsaveArea =
                    FxsaveArea(
                        (PrimitiveArrayLayouts.uByteArray(512)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FxsaveArea) {
                    (PrimitiveArrayLayouts.uByteArray(512)).writeBytes(buf, offset, value.data)
                }
            }

        public fun new(): FxsaveArea = FxsaveArea(data = UByteArray(512) { 0u.toUByte() })
    }
}

// Register saving/restoring is not supported in Miri
public fun testFxsave64() {
    val a = FxsaveArea.new()
    val b = FxsaveArea.new()
    run {
        fxsave64(a.ptr())
        fxrstor64(a.ptr())
        fxsave64(b.ptr())
    }
}
