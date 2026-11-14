// port-lint: source io/io_slice/repr_windows.rs
package io.github.kotlinmania.rustlang.core.io.ioslice.reprwindows

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawMutSliceList
import io.github.kotlinmania.rustlang.core.slice.rawSliceList

public open class Wsabuf internal constructor(
    public var len: UInt,
    bufCtorArg: MutPtr,
) {
    public var buf: MutPtr = bufCtorArg
        internal set

    public open fun clone(): Wsabuf = Wsabuf(len, buf)

    public companion object {
        public fun typeLayout(): TypeLayout<Wsabuf> =
            object : TypeLayout<Wsabuf> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Wsabuf =
                    Wsabuf(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Wsabuf) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.len)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.buf)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Wsabuf =
                    Wsabuf(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Wsabuf) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.len)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.buf)
                }
            }

        public operator fun invoke(
            len: UInt,
            buf: MutPtr,
        ): Wsabuf = Wsabuf(
            len = len,
            bufCtorArg = buf,
        )
    }
}

internal open class IoSlice(
    public var vec: Wsabuf,
    public var p: Unit,
) {
    public open fun clone(): IoSlice = IoSlice(vec, p)

    internal open fun advance(n: Int) {
        check(n <= this.vec.len.toInt()) { "advancing IoSlice beyond its length" }
        // SAFETY:
        // * `n <= len` as asserted above.
        // * The allocation pointed to by `buf` is valid up to `buf + len`.
        run {
            this.vec.len -= n.toUInt()
            this.vec.buf = MutPtr((this.vec.buf).rawAddress + ((n).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
    }

    internal open fun asSlice(): List<UByte> {
        // SAFETY:
        // * `buf` and `len` come from a prior decomposition of a valid slice.
        return rawSliceList(this.vec.buf, this.vec.len.toInt(), PrimitiveLayouts.U8)
    }

    public companion object {
        internal fun new(buf: List<UByte>): IoSlice {
            check(buf.size <= UInt.MAX_VALUE.toInt())
            return IoSlice(vec = Wsabuf(len = buf.size.toUInt(), buf = (buf.asPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }), p = Unit)
        }
    }
}

internal open class IoSliceMut(
    public var vec: Wsabuf,
    public var p: Unit,
) {
    internal open fun advance(n: Int) {
        check(n <= this.vec.len.toInt()) { "advancing IoSliceMut beyond its length" }
        // SAFETY:
        // * `n <= len` as asserted above.
        // * The allocation pointed to by `buf` is valid up to `buf + len`.
        run {
            this.vec.len -= n.toUInt()
            this.vec.buf = MutPtr((this.vec.buf).rawAddress + ((n).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
    }

    internal open fun asSlice(): List<UByte> {
        // SAFETY:
        // * `buf` and `len` come from a prior decomposition of a valid slice.
        return rawSliceList(this.vec.buf, this.vec.len.toInt(), PrimitiveLayouts.U8)
    }

    internal open fun intoSlice(): MutableList<UByte> {
        // SAFETY:
        // * `buf` and `len` come from a prior decomposition of a valid slice.
        return rawMutSliceList(this.vec.buf, this.vec.len.toInt(), PrimitiveLayouts.U8)
    }

    internal open fun asMutSlice(): MutableList<UByte> {
        // SAFETY:
        // * `buf` and `len` come from a prior decomposition of a valid slice.
        return rawMutSliceList(this.vec.buf, this.vec.len.toInt(), PrimitiveLayouts.U8)
    }

    public companion object {
        internal fun new(buf: MutableList<UByte>): IoSliceMut {
            check(buf.size <= UInt.MAX_VALUE.toInt())
            return IoSliceMut(vec = Wsabuf(len = buf.size.toUInt(), buf = buf.asMutPtr()), p = Unit)
        }
    }
}
