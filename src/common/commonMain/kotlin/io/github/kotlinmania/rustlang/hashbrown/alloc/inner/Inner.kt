// port-lint: source alloc.rs
package io.github.kotlinmania.rustlang.hashbrown.alloc.inner

import io.github.kotlinmania.rustlang.alloc.alloc.alloc
import io.github.kotlinmania.rustlang.alloc.alloc.dealloc
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.ptr.sliceFromRawPartsMut
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

// not exposed outside of this crate
public interface Allocator {
    public fun allocate(layout: Layout): Result<NonNull, Unit>

    public fun deallocate(ptr: NonNull, layout: Layout)
}

public open class Global : Allocator {
    public open fun clone(): Global = Global()

    public override fun allocate(layout: Layout): Result<NonNull, Unit> =
        when (val matchVal = NonNull.new(alloc(layout))) {
            null -> Result.Err<NonNull, Unit>(Unit)
            else -> {
                val data = matchVal
                // SAFETY: this is NonNull.sliceFromRawParts.
                Result.Ok<NonNull, Unit>(NonNull.newUnchecked(sliceFromRawPartsMut(data.asPtr(), layout.size())))
            }
        }

    public override fun deallocate(ptr: NonNull, layout: Layout) {
        run {
            dealloc(ptr.asPtr(), layout)
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Global> =
            object : TypeLayout<Global> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Global =
                    Global()
                override fun writeNative(addr: Long, value: Global) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Global =
                    Global()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Global) {
                }
            }

        public fun default(): Global = Global()
    }
}

public fun <A : Allocator> doAlloc(alloc: A, layout: Layout): Result<NonNull, Unit> = alloc.allocate(layout)
