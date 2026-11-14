// port-lint: source vec/in_place_drop.rs
package io.github.kotlinmania.rustlang.alloc.vec.inplacedrop

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull

// A helper struct for in-place iteration that drops the destination slice of iteration,
// i.e. the head. The source slice (the tail) is dropped by IntoIter.
internal open class InPlaceDrop<T>(
    internal var inner: MutPtr,
    internal var dst: MutPtr,
) {
    public open fun len(): Int {
        // ignore-tidy-undocumented-unchecked
        return this.dst.offsetFromUnsigned(this.inner)
    }

    public open fun drop() {
        // ignore-tidy-undocumented-unchecked
        this.inner.castSlice(this.len()).dropInPlace()
    }

    public companion object {
        public fun <T> typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<InPlaceDrop<T>> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<InPlaceDrop<T>> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): InPlaceDrop<T> =
                    InPlaceDrop(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: InPlaceDrop<T>) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.inner)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.dst)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InPlaceDrop<T> =
                    InPlaceDrop(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InPlaceDrop<T>) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.inner)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.dst)
                }
            }
    }
}

// A helper struct for in-place collection that drops the destination items together with
// the source allocation - i.e. before the reallocation happened - to avoid leaking them
// if some other destructor panics.
internal open class InPlaceDstDataSrcBufDrop<Src, Dest>(
    internal var ptr: NonNull,
    internal var len: Int,
    internal var srcCap: Int,
    internal var src: Unit,
) {
    public open fun drop() {
        // ignore-tidy-undocumented-unchecked
        run {
            this.ptr.asPtr().castSlice(this.len).dropInPlace()
        }
    }
}
