// port-lint: tests x86/fxsr.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.asMutPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// FXSR floating-point context fast save and restore.

public open class FxsaveArea internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UByteArray>,
) {
    public var data: UByteArray
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) {
            fieldLayout.writeNative(fieldStorage.rawAddress, value)
        }

    internal constructor(data: UByteArray, fieldLayout: TypeLayout<UByteArray>) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 16L)), AlignedTypeLayout(fieldLayout, 16L))

    public fun pointerLayout(): TypeLayout<FxsaveArea> = TransparentTypeLayout(fieldLayout, { ptr, layout -> FxsaveArea(ptr, layout) }, { it.data })

    public open fun ptr(): MutPtr = this.data.asMutPtr()

    public companion object {
        public operator fun invoke(data: UByteArray, fieldLayout: TypeLayout<UByteArray>): FxsaveArea = FxsaveArea(data, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UByteArray>): FxsaveArea = FxsaveArea(fieldStorage, fieldLayout)

        public fun new(fieldLayout: TypeLayout<UByteArray>): FxsaveArea = FxsaveArea(data = UByteArray(512) { 0u.toUByte() }, fieldLayout = PrimitiveArrayLayouts.uByteArray((512).toInt()))
    }
}

// Register saving/restoring is not supported in Miri
public fun testFxsave() {
    val a = FxsaveArea.new()
    val b = FxsaveArea.new()
    run {
        fxsave(a.ptr())
        fxrstor(a.ptr())
        fxsave(b.ptr())
    }
}
