package io.github.kotlinmania.rustlang.std.sys.threadlocal.native

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

internal open class LocalPointer(
    public var p: Cell<MutPtr>,
) {
    public open fun get(): MutPtr = this.p.get()

    public open fun set(p: MutPtr) {
        this.p.set(p)
    }

    public companion object {
        public fun new(): LocalPointer = LocalPointer(p = Cell.new(MutPtr(0L)))
    }
}
