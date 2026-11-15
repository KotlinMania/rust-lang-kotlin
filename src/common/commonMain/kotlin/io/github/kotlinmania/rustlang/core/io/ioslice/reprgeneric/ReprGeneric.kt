// port-lint: source io/io_slice/repr_generic.rs
package io.github.kotlinmania.rustlang.core.io.ioslice.reprgeneric

import io.github.kotlinmania.rustlang.mem.replace

public open class IoSlice(
    public var value: List<UByte>,
) {
    public var field0: List<UByte>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): IoSlice = IoSlice(value)

    public open fun advance(n: Int) {
        // Note(const-Note): Use `self.0[n ..]` once const.
        val (_, remaining) = this.value.splitAt(n)
        this.value = remaining
    }

    public open fun asSlice(): List<UByte> = this.value

    public companion object {
        public fun new(buf: List<UByte>): IoSlice = IoSlice(buf)
    }
}

public open class IoSliceMut(
    public var value: MutableList<UByte>,
) {
    public var field0: MutableList<UByte>
        get() = value
        set(v) {
            value = v
        }

    public open fun advance(n: Int) {
        // Note(const-Note): Use `mem.take` once const.
        val slice = replace(this.value, listOf())
        val (_, remaining) = slice.splitAtMut(n)
        this.value = remaining
    }

    public open fun asSlice(): List<UByte> = this.value

    public open fun intoSlice(): MutableList<UByte> = this.value

    public open fun asMutSlice(): MutableList<UByte> = this.value

    public companion object {
        public fun new(buf: MutableList<UByte>): IoSliceMut = IoSliceMut(buf)
    }
}
