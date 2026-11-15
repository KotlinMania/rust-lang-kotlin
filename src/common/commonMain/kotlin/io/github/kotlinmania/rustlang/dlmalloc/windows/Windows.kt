// port-lint: source windows.rs
package io.github.kotlinmania.rustlang.dlmalloc.windows

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.dlmalloc.Allocator

public open class System(
    public var priv: Unit,
) : Allocator {
    public override fun alloc(size: Int): Triple<MutPtr, Int, UInt> {
        val addr = VirtualAlloc(MutPtr(0L), size, MEM_RESERVE or MEM_COMMIT, PAGE_READWRITE)
        return if (addr.isNull()) {
            Triple(MutPtr(0L), 0, 0)
        } else {
            Triple(addr.cast(), size, 0)
        }
    }

    public override fun remap(ptr: MutPtr, oldsize: Int, newsize: Int, canMove: Boolean): MutPtr = MutPtr(0L)

    public override fun freePart(ptr: MutPtr, oldsize: Int, newsize: Int): Boolean = VirtualFree(MutPtr((ptr).rawAddress + ((newsize).toLong() * (PrimitiveLayouts.U8).strideBytes)), oldsize - newsize, MEM_DECOMMIT) != 0

    public override fun free(ptr: MutPtr, size: Int): Boolean = VirtualFree(ptr, 0, MEM_DECOMMIT) != 0

    public override fun canReleasePart(flags: UInt): Boolean = true

    public override fun allocatesZeros(): Boolean = true

    public override fun pageSize(): Int =
        run {
            val info = MaybeUninit.uninit(PrimitiveLayouts.I32)
            GetSystemInfo(info.asMutPtr())
            (info.assumeInitRef().dwPageSize).toInt()
        }

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

// NB: `SRWLOCK_INIT` doesn't appear to be in `windows - sys`
public var LOCK: Srwlock = Srwlock(ptr = MutPtr(0L))

public fun acquireGlobalLock() {
    run {
        AcquireSRWLockExclusive(addrOfMut(LOCK))
    }
}

public fun releaseGlobalLock() {
    run {
        ReleaseSRWLockExclusive(addrOfMut(LOCK))
    }
}

/**
 * Not needed on Windows
 */
public fun enableAllocAfterFork() {}
