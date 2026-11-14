// port-lint: source sys/sync/futex/wasm.rs
package io.github.kotlinmania.rustlang.std.sys.sync.futex.wasm

import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.time.Duration

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
 * Wait for a futexWake operation to wake us.
 *
 * Returns directly if the futex doesn't hold the expected value.
 *
 * Returns false on timeout, and true in all other cases.
 */
public fun futexWait(futex: Atomic<UInt>, expected: UInt, timeout: Duration?): Boolean {
    val timeout1 = (timeout?.let({ t -> t.asNanos().tryInto().ok() }) ?: -1)
    return wasm.memoryAtomicWait32((constPtrFromRustCast(futex)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, expected.toInt(), timeout1) < 2
}

/**
 * Wakes up one thread that's blocked on `futexWait` on this futex.
 *
 * Returns true if this actually woke up such a thread,
 * or false if no thread was waiting on this futex.
 */
public fun futexWake(futex: Atomic<UInt>): Boolean = wasm.memoryAtomicNotify((constPtrFromRustCast(futex)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, 1) > 0

/**
 * Wakes up all threads that are waiting on `futexWait` on this futex.
 */
public fun futexWakeAll(futex: Atomic<UInt>) {
    run {
        wasm.memoryAtomicNotify((constPtrFromRustCast(futex)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, Int.MAX_VALUE.toUInt())
    }
}
