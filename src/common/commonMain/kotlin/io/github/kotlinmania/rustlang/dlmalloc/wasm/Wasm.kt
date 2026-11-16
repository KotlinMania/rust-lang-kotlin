// port-lint: source wasm.rs
package io.github.kotlinmania.rustlang.dlmalloc.wasm

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.dlmalloc.Allocator

/**
 * System setting for Wasm
 */
public open class System(
    public var priv: Unit,
) : Allocator {
    public override fun alloc(size: Int): Triple<MutPtr, Int, UInt> {
        val pages = size.divCeil(this.pageSize())
        val prev = wasm.memoryGrow(0, pages)
        // If the allocation failed, meaning `prev` is -1 or
        // `Int.maxValue()`, then return null.
        if (prev == Int.maxValue()) {
            return Triple(MutPtr(0L), 0, 0)
        }
        val prevPage = prev * this.pageSize()
        val basePtr = MutPtr(prevPage.toLong())
        val size1 = pages * this.pageSize()
        // Additionally check to see if we just allocated the final bit of the
        // address space. In such a situation it's not valid in Rust for a
        // pointer to actually wrap around to from the top of the address space
        // to 0, so it's not valid to allocate the entire region. Fake the last
        // few bytes as being un-allocated meaning that the actual size of this
        // allocation won't be page aligned, which should be handled by
        // dlmalloc.
        if ((prevPage + (size1.toInt())) == 0) {
            return Triple(basePtr, size1 - 16, 0)
        }
        return Triple(basePtr, size1, 0)
    }

    public override fun remap(ptr: MutPtr, oldsize: Int, newsize: Int, canMove: Boolean): MutPtr {
        // Note: I think this can be implemented near the end?
        return MutPtr(0L)
    }

    public override fun freePart(ptr: MutPtr, oldsize: Int, newsize: Int): Boolean = false

    public override fun free(ptr: MutPtr, size: Int): Boolean = false

    public override fun canReleasePart(flags: UInt): Boolean = false

    public override fun allocatesZeros(): Boolean = true

    public override fun pageSize(): Int = 64 * 1024

    public companion object {
        public fun typeLayout(): TypeLayout<System> =
            object : TypeLayout<System> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): System =
                    System(
                        (PrimitiveLayouts.UNIT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: System) {
                    (PrimitiveLayouts.UNIT).writeNative(addr, value.priv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): System =
                    System(
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: System) {
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset, value.priv)
                }
            }

        public fun new(): System = System(priv = Unit)
    }
}

public fun acquireGlobalLock() {
    // single threaded, no need!
    check(!false)
}

public fun releaseGlobalLock() {
    // single threaded, no need!
    check(!false)
}

public fun enableAllocAfterFork() {
    // single threaded, no need!
    check(!false)
}
