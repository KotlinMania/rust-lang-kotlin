// rust-kotlin: manual
// port-lint: source vec/set_len_on_drop.rs
package io.github.kotlinmania.rustlang.alloc.vec.setlenondrop

import io.github.kotlinmania.rustlang.core.mem.RustMutableReference

// Set the length of the vec when the `SetLenOnDrop` value goes out of scope.
//
// The idea is: The length field in SetLenOnDrop is a local variable
// that the optimizer will see does not alias with any stores through the Vec's data
// pointer. This is a workaround for alias analysis issue #32155
public open class SetLenOnDrop internal constructor(
    lenCtorArg: RustMutableReference<Int>,
    public var localLen: Int,
) : AutoCloseable {
    public var len: RustMutableReference<Int> = lenCtorArg
        internal set

    override fun close() {
        this.drop()
    }

    public open fun incrementLen(increment: Int) {
        this.localLen += increment
    }

    public open fun currentLen(): Int = this.localLen

    public open fun drop() {
        (this.len).write(this.localLen)
    }

    public companion object {
        public operator fun invoke(
            len: RustMutableReference<Int>,
            localLen: Int,
        ): SetLenOnDrop =
            SetLenOnDrop(
                lenCtorArg = len,
                localLen = localLen,
            )

        public fun new(len: RustMutableReference<Int>): SetLenOnDrop = SetLenOnDrop(localLen = (len).read(), len = len)
    }
}
