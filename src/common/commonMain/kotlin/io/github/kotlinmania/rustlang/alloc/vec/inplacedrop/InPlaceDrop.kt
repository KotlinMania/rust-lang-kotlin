// port-lint: source vec/in_place_drop.rs
package io.github.kotlinmania.rustlang.alloc.vec.inplacedrop

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull

// A helper struct for in-place iteration that drops the destination slice of iteration,
// i.e. the head. The source slice (the tail) is dropped by IntoIter.
public open class InPlaceDrop<T>(
    public var inner: MutPtr,
    public var dst: MutPtr,
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
        public fun <T> typeLayout(): TypeLayout<InPlaceDrop<T>> =
            object : TypeLayout<InPlaceDrop<T>> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): InPlaceDrop<T> =
                    InPlaceDrop(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: InPlaceDrop<T>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.inner)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.dst)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InPlaceDrop<T> =
                    InPlaceDrop(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InPlaceDrop<T>) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.inner)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.dst)
                }
            }
    }
}

// A helper struct for in-place collection that drops the destination items together with
// the source allocation - i.e. before the reallocation happened - to avoid leaking them
// if some other destructor panics.
public open class InPlaceDstDataSrcBufDrop<Src, Dest>(
    public var ptr: NonNull,
    public var len: Int,
    public var srcCap: Int,
    public var src: Unit,
) {
    public open fun drop() {
        // ignore-tidy-undocumented-unchecked
        run {
            this.ptr
                .asPtr()
                .castSlice(this.len)
                .dropInPlace()
        }
    }
}
