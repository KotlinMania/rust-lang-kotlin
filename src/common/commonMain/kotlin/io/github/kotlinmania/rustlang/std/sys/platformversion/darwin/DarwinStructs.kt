package io.github.kotlinmania.rustlang.std.sys.platformversion.darwin

import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public open class Deferred<F, Output> internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<F> = UnresolvedTypeLayout(),
) {
    public var field0: F
        get() = value
        set(v) {
            value = v
        }

    public var value: F
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: F, fieldLayout: TypeLayout<F> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Deferred<F, Output>> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Deferred<F, Output>(ptr, layout) }, { it.value })

    public open fun drop() {
        (this.value)()
    }

    public companion object {
        public operator fun <F, Output> invoke(value: F, fieldLayout: TypeLayout<F> = UnresolvedTypeLayout()): Deferred<F, Output> = Deferred<F, Output>(value, fieldLayout)

        public fun <F, Output> fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<F> = UnresolvedTypeLayout()): Deferred<F, Output> = Deferred<F, Output>(fieldStorage, fieldLayout)
    }
}
