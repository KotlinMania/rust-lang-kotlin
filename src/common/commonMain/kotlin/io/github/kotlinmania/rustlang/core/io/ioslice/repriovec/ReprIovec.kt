// port-lint: source io/io_slice/repr_iovec.rs
package io.github.kotlinmania.rustlang.core.io.ioslice.repriovec

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr

public open class Iovec internal constructor(
    iovBaseCtorArg: MutPtr,
    public var iovLen: Int,
) {
    public var iovBase: MutPtr = iovBaseCtorArg
        internal set

    public open fun clone(): Iovec = Iovec(iovBase, iovLen)

    public companion object {
        public fun typeLayout(): TypeLayout<Iovec> =
            object : TypeLayout<Iovec> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Iovec =
                    Iovec(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Iovec) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.iovBase)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.iovLen)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Iovec =
                    Iovec(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Iovec) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.iovBase)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.iovLen)
                }
            }

        public operator fun invoke(
            iovBase: MutPtr,
            iovLen: Int,
        ): Iovec = Iovec(
            iovBaseCtorArg = iovBase,
            iovLen = iovLen,
        )
    }
}

public open class IoSlice(
    public var vec: Iovec,
    public var p: Unit,
) {
    public open fun clone(): IoSlice = IoSlice(vec, p)

    public open fun advance(n: Int) {
        check(n <= this.vec.iovLen) { "advancing IoSlice beyond its length" }
        // SAFETY:
        // * `n <= iovLen` as asserted above.
        // * The allocation pointed to by `iovBase` is valid up to `iovBase + iovLen`.
        run {
            this.vec.iovLen -= n
            this.vec.iovBase = MutPtr((this.vec.iovBase).rawAddress + ((n).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
    }

    public open fun asSlice(): List<UByte> {
        // SAFETY:
        // * `iovBase` and `iovLen` come from a prior decomposition of a valid slice.
        return linearSliceView((this.vec.iovBase).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, this.vec.iovLen.toInt(), PrimitiveLayouts.U8)
    }

    public companion object {
        public fun new(buf: List<UByte>): IoSlice = IoSlice(vec = Iovec(iovBase = ((buf.asPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, iovLen = buf.size), p = Unit)
    }
}

public open class IoSliceMut(
    public var vec: Iovec,
    public var p: Unit,
) {
    public open fun advance(n: Int) {
        check(n <= this.vec.iovLen) { "advancing IoSliceMut beyond its length" }
        // SAFETY:
        // * `n <= iovLen` as asserted above.
        // * The allocation pointed to by `iovBase` is valid up to `iovBase + iovLen`.
        run {
            this.vec.iovLen -= n
            this.vec.iovBase = MutPtr((this.vec.iovBase).rawAddress + ((n).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
    }

    public open fun asSlice(): List<UByte> {
        // SAFETY:
        // * `iovBase` and `iovLen` come from a prior decomposition of a valid slice.
        return linearSliceView((this.vec.iovBase).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, this.vec.iovLen.toInt(), PrimitiveLayouts.U8)
    }

    public open fun intoSlice(): MutableList<UByte> {
        // SAFETY:
        // * `iovBase` and `iovLen` come from a prior decomposition of a valid slice.
        return linearMutSliceView((this.vec.iovBase).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, this.vec.iovLen.toInt(), PrimitiveLayouts.U8)
    }

    public open fun asMutSlice(): MutableList<UByte> {
        // SAFETY:
        // * `iovBase` and `iovLen` come from a prior decomposition of a valid slice.
        return linearMutSliceView((this.vec.iovBase).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, this.vec.iovLen.toInt(), PrimitiveLayouts.U8)
    }

    public companion object {
        public fun new(buf: MutableList<UByte>): IoSliceMut = IoSliceMut(vec = Iovec(iovBase = (buf.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, iovLen = buf.size), p = Unit)
    }
}
