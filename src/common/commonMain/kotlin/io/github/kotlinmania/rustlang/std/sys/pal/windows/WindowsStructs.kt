package io.github.kotlinmania.rustlang.std.sys.pal.windows

import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

/**
 * Align the inner value to 8 bytes.
 *
 * This is enough for almost all of the buffers we're likely to work with in
 * the Windows APIs we use.
 */
public open class Align8<T>(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<T>,
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public var value: T
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    public constructor(value: T, fieldLayout: TypeLayout<T>) : this(allocateNativeCell(value, AlignedTypeLayout(fieldLayout, 8L)), AlignedTypeLayout(fieldLayout, 8L))

    public fun pointerLayout(): TypeLayout<Align8<T>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Align8<T>(ptr, layout) }, { it.value })

    public open fun clone(): Align8<T> = Align8<T>(value, fieldLayout)
}
