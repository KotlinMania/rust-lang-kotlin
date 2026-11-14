// port-lint: source io/io_slice/repr_uefi.rs
package io.github.kotlinmania.rustlang.core.io.ioslice.repruefi

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawMutSliceList
import io.github.kotlinmania.rustlang.core.slice.rawSliceList

// A buffer type used with `Write.writeVectored` for UEFI Networking APIs. Vectored writing to
// File is not supported as of UEFI Spec 2.11.

internal open class IoSlice(
    public var len: UInt,
    public var data: ConstPtr,
    public var p: Unit,
) {
    internal open fun advance(n: Int) {
        check(n <= this.len.toInt()) { "advancing IoSlice beyond its length" }
        // SAFETY:
        // * `n <= len` as asserted above.
        // * The allocation pointed to by `data` is valid up to `data + len`.
        run {
            this.len -= n.toUInt()
            this.data = ConstPtr((this.data).rawAddress + ((n).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
    }

    internal open fun asSlice(): List<UByte> {
        // SAFETY:
        // * `data` and `len` come from a prior decomposition of a valid slice.
        return rawSliceList(this.data, this.len.toInt(), PrimitiveLayouts.U8)
    }

    public companion object {
        internal fun new(buf: List<UByte>): IoSlice {
            check(buf.size <= UInt.MAX_VALUE.toInt()) { "slice too large for IoSlice" }
            return IoSlice(len = buf.size.toUInt(), data = buf.asPtr(), p = Unit)
        }
    }
}

internal open class IoSliceMut(
    public var len: UInt,
    public var data: MutPtr,
    public var p: Unit,
) {
    internal open fun advance(n: Int) {
        check(n <= this.len.toInt()) { "advancing IoSliceMut beyond its length" }
        // SAFETY:
        // * `n <= len` as asserted above.
        // * The allocation pointed to by `data` is valid up to `data + len`.
        run {
            this.len -= n.toUInt()
            this.data = MutPtr((this.data).rawAddress + ((n).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
    }

    internal open fun asSlice(): List<UByte> {
        // SAFETY:
        // * `data` and `len` come from a prior decomposition of a valid slice.
        return rawSliceList(this.data, this.len.toInt(), PrimitiveLayouts.U8)
    }

    internal open fun intoSlice(): MutableList<UByte> {
        // SAFETY:
        // * `data` and `len` come from a prior decomposition of a valid slice.
        return rawMutSliceList(this.data, this.len.toInt(), PrimitiveLayouts.U8)
    }

    internal open fun asMutSlice(): MutableList<UByte> {
        // SAFETY:
        // * `data` and `len` come from a prior decomposition of a valid slice.
        return rawMutSliceList(this.data, this.len.toInt(), PrimitiveLayouts.U8)
    }

    public companion object {
        internal fun new(buf: MutableList<UByte>): IoSliceMut {
            check(buf.size <= UInt.MAX_VALUE.toInt()) { "slice too large for IoSliceMut" }
            return IoSliceMut(len = buf.size.toUInt(), data = buf.asMutPtr(), p = Unit)
        }
    }
}
