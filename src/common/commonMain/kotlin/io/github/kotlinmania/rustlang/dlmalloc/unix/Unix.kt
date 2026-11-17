// port-lint: source unix.rs
package io.github.kotlinmania.rustlang.dlmalloc.unix

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.dlmalloc.Allocator
import io.github.kotlinmania.rustlang.libc.mmap
import io.github.kotlinmania.rustlang.libc.mremap
import io.github.kotlinmania.rustlang.libc.munmap
import io.github.kotlinmania.rustlang.libc.pthreadAtfork
import io.github.kotlinmania.rustlang.libc.pthreadMutexLock
import io.github.kotlinmania.rustlang.libc.pthreadMutexUnlock
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.MAP_ANON
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MAP_FAILED
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.MAP_PRIVATE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.PROT_READ
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.PROT_WRITE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.MREMAP_MAYMOVE
import io.github.kotlinmania.rustlang.libc.wasi.PTHREAD_MUTEX_INITIALIZER
import io.github.kotlinmania.rustlang.libc.wasi.PthreadMutexT

/**
 * System setting for Linux
 */
public open class System(
    public var priv: Unit,
) : Allocator {
    public override fun alloc(size: Int): Triple<MutPtr, Int, UInt> {
        val addr = mmap(MutPtr(0L), size, PROT_WRITE or PROT_READ, MAP_ANON or MAP_PRIVATE, -1, 0)
        return if (addr == MAP_FAILED) {
            Triple(MutPtr(0L), 0, 0)
        } else {
            Triple(addr.cast(), size, 0)
        }
    }

    public override fun remap(ptr: MutPtr, oldsize: Int, newsize: Int, canMove: Boolean): MutPtr {
        val flags =
            if (canMove) {
                MREMAP_MAYMOVE
            } else {
                0
            }
        val ptr1 = mremap(ptr, oldsize, newsize, flags)
        return if (ptr1 == MAP_FAILED) {
            MutPtr(0L)
        } else {
            ptr1.cast()
        }
    }

    public open fun remapAllocator(ptr: MutPtr, oldsize: Int, newsize: Int, canMove: Boolean): MutPtr = MutPtr(0L)

    public override fun freePart(ptr: MutPtr, oldsize: Int, newsize: Int): Boolean {
        return run {
            val rc = mremap(ptr, oldsize, newsize, 0)
            if (rc != MAP_FAILED) {
                return true
            }
            munmap(MutPtr((ptr).rawAddress + ((newsize).toLong() * (PrimitiveLayouts.U8).strideBytes)), oldsize - newsize) == 0
        }
    }

    public open fun freePartAllocator(ptr: MutPtr, oldsize: Int, newsize: Int): Boolean = munmap(MutPtr((ptr).rawAddress + ((newsize).toLong() * (PrimitiveLayouts.U8).strideBytes)), oldsize - newsize) == 0

    public override fun free(ptr: MutPtr, size: Int): Boolean = munmap(ptr, size) == 0

    public override fun canReleasePart(flags: UInt): Boolean = true

    public override fun allocatesZeros(): Boolean = true

    public override fun pageSize(): Int = 4096

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

public var LOCK: PthreadMutexT = PTHREAD_MUTEX_INITIALIZER

public fun acquireGlobalLock() {
    check(pthreadMutexLock(addrOfMut(LOCK)) == 0)
}

public fun releaseGlobalLock() {
    check(pthreadMutexUnlock(addrOfMut(LOCK)) == 0)
}

/**
 * allows the allocator to remain unsable in the child process,
 * after a call to `fork(2)`
 *
 * #Safety
 *
 * if used, this function must be called,
 * before any allocations are made with the global allocator.
 */
public fun enableAllocAfterFork() {
    // atfork must only be called once, to avoid a deadlock,
    // where the handler attempts to acquire the global lock twice
    var FORK_PROTECTED: Boolean = false
    fun acquireGlobalLock() {
        acquireGlobalLock()
    }
    fun releaseGlobalLock() {
        releaseGlobalLock()
    }
    acquireGlobalLock()
    // if a process forks,
    // it will acquire the lock before any other thread,
    // protecting it from deadlock,
    // due to the child being created with only the calling thread.
    if (FORK_PROTECTED.inv()) {
        pthreadAtfork(acquireGlobalLock, releaseGlobalLock, releaseGlobalLock)
        FORK_PROTECTED = true
    }
    releaseGlobalLock()
}
