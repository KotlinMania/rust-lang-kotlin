// port-lint: source sys/sync/futex/unix.rs
package io.github.kotlinmania.rustlang.std.sys.sync.futex.unix

import io.github.kotlinmania.libc.fuchsia.CLOCK_MONOTONIC
import io.github.kotlinmania.libc.fuchsia.EINTR
import io.github.kotlinmania.libc.fuchsia.ETIMEDOUT
import io.github.kotlinmania.libc.unix.linuxlike.android.FUTEX_PRIVATE_FLAG
import io.github.kotlinmania.libc.unix.linuxlike.android.FUTEX_WAIT_BITSET
import io.github.kotlinmania.libc.unix.linuxlike.android.FUTEX_WAKE
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Align4
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.libc.SysFutex
import io.github.kotlinmania.rustlang.libc.syscall
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sys.io.Errno
import io.github.kotlinmania.rustlang.std.sys.pal.unix.time.Timespec

/**
 * An atomic for use as a futex that is at least 32-bits but may be larger
 */
public typealias Futex = Atomic<Primitive>

/**
 * Must be the underlying type of Futex
 */
public typealias Primitive = UInt

/**
 * An atomic for use as a futex that is at least 8-bits but may be larger.
 */
public typealias SmallFutex = Atomic<SmallPrimitive>

/**
 * Must be the underlying type of SmallFutex
 */
public typealias SmallPrimitive = UInt

/**
 * Waits for a `futexWake` operation to wake us.
 *
 * Returns directly if the futex doesn't hold the expected value.
 *
 * Returns false on timeout, and true in all other cases.
 */
public fun futexWait(futex: Atomic<UInt, Align4<UInt>>, expected: UInt, timeout: Duration?): Boolean {
    // Calculate the timeout as an absolute timespec.
    //
    // Overflows are rounded up to an infinite timeout (None).
    val timespec = timeout?.let({ d -> Timespec.now(CLOCK_MONOTONIC).checkedAddDuration(d) })?.let({ t -> t.toTimespec() })
    while (true) {
        // No need to wait if the value already changed.
        if (futex.load(Relaxed) != expected) {
            return true
        }
        val r = syscall(SysFutex, ((constPtrFromRustCast(futex)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).rawAddress, (FUTEX_WAIT_BITSET or FUTEX_PRIVATE_FLAG).toLong(), expected.toLong(), (timespec?.let({ t -> constPtrFromRustCast(t) }) ?: ConstPtr(0L)).toLong(), ConstPtr(0L).toLong(), 0u.inv())
        when (val matchVal = (r < 0).then(Errno)) {
            ETIMEDOUT -> { return false }
            EINTR -> { continue }
            else -> { return true }
        }
    }
}

/**
 * Wakes up one thread that's blocked on `futexWait` on this futex.
 *
 * Returns true if this actually woke up such a thread,
 * or false if no thread was waiting on this futex.
 *
 * On some platforms, this always returns false.
 */
public fun futexWake(futex: Atomic<UInt, Align4<UInt>>): Boolean {
    val ptr = (constPtrFromRustCast(futex)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val op = FUTEX_WAKE or FUTEX_PRIVATE_FLAG
    return syscall(SysFutex, ptr.rawAddress, op.toLong(), 1L) > 0
}

/**
 * Wakes up all threads that are waiting on `futexWait` on this futex.
 */
public fun futexWakeAll(futex: Atomic<UInt, Align4<UInt>>) {
    val ptr = (constPtrFromRustCast(futex)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
    val op = FUTEX_WAKE or FUTEX_PRIVATE_FLAG
    run {
        syscall(SysFutex, ptr.rawAddress, op.toLong(), Int.MAX_VALUE.toLong())
    }
}
