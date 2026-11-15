// port-lint: source dummy.rs
package io.github.kotlinmania.rustlang.dlmalloc.dummy

import io.github.kotlinmania.rustlang.Allocator
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public open class System(
    public var priv: Unit,
) : Allocator {
    public override fun alloc(size: Int): Triple<MutPtr, Int, UInt> = Triple(MutPtr(0L), 0, 0)

    public override fun remap(ptr: MutPtr, oldsize: Int, newsize: Int, canMove: Boolean): MutPtr = MutPtr(0L)

    public override fun freePart(ptr: MutPtr, oldsize: Int, newsize: Int): Boolean = false

    public override fun free(ptr: MutPtr, size: Int): Boolean = false

    public override fun canReleasePart(flags: UInt): Boolean = false

    public override fun allocatesZeros(): Boolean = false

    public override fun pageSize(): Int = 1

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
