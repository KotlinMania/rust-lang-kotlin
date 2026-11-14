// port-lint: source io/io_slice/repr_generic.rs
package io.github.kotlinmania.rustlang.core.io.ioslice.reprgeneric

internal open class IoSlice(
    public var value: List<UByte>,
) {
    public var field0: List<UByte>
        get() = value
        set(v) {
            value = v
        }

    internal open fun advance(n: Int) {
        // Note(const-Note): Use `self.0[n ..]` once const.
        val (_, remaining) = this.value.splitAt(n)
        this.value = remaining
    }

    internal open fun asSlice(): List<UByte> = this.value

    public companion object {
        internal fun new(buf: List<UByte>): IoSlice = IoSlice(buf)
    }
}

internal open class IoSliceMut(
    public var value: MutableList<UByte>,
) {
    public var field0: MutableList<UByte>
        get() = value
        set(v) {
            value = v
        }

    internal open fun advance(n: Int) {
        // Note(const-Note): Use `mem.take` once const.
        val slice = mem.replace(this.value, listOf())
        val (_, remaining) = slice.splitAtMut(n)
        this.value = remaining
    }

    internal open fun asSlice(): List<UByte> = this.value

    internal open fun intoSlice(): MutableList<UByte> = this.value

    internal open fun asMutSlice(): MutableList<UByte> = this.value

    public companion object {
        internal fun new(buf: MutableList<UByte>): IoSliceMut = IoSliceMut(buf)
    }
}
