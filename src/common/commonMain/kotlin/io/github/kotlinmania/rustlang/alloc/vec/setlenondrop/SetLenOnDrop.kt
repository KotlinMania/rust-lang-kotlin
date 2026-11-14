// port-lint: source vec/set_len_on_drop.rs
package io.github.kotlinmania.rustlang.alloc.vec.setlenondrop

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// Set the length of the vec when the `SetLenOnDrop` value goes out of scope.
//
// The idea is: The length field in SetLenOnDrop is a local variable
// that the optimizer will see does not alias with any stores through the Vec's data
// pointer. This is a workaround for alias analysis issue #32155
internal open class SetLenOnDrop(
    public var len: RustMutableReference<Int>,
    public var localLen: Int,
) {
    internal open fun incrementLen(increment: Int) {
        this.localLen += increment
    }

    internal open fun currentLen(): Int = this.localLen

    public open fun drop() {
        (this.len).write(this.localLen)
    }

    public companion object {
        internal fun new(len: RustMutableReference<Int>): SetLenOnDrop = SetLenOnDrop(localLen = (len).read(), len = len)
    }
}
