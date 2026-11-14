package io.github.kotlinmania.rustlang.std.sys.threadlocal.native

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public open class LocalPointer internal constructor(
    pCtorArg: Cell<MutPtr>,
) {
    public var p: Cell<MutPtr> = pCtorArg
        internal set

    public open fun get(): MutPtr = this.p.get()

    public open fun set(p: MutPtr) {
        this.p.set(p)
    }

    public companion object {
        public operator fun invoke(
            p: Cell<MutPtr>,
        ): LocalPointer = LocalPointer(
            pCtorArg = p,
        )

        public fun new(): LocalPointer = LocalPointer(p = Cell.new(MutPtr(0L)))
    }
}
