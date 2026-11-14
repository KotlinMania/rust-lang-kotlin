// port-lint: source sys/sync/futex/wasilibc.rs
package io.github.kotlinmania.rustlang.std.sys.sync.futex.wasilibc

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.CUint
import io.github.kotlinmania.libc.fuchsia.CLOCK_REALTIME
import io.github.kotlinmania.libc.fuchsia.ClockidT
import io.github.kotlinmania.libc.fuchsia.Timespec
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.time.Duration

// A futex implementation based on the primitives provided by `wasi - libc`.
//
// This is currently only used on wasip3 targets, but in the future once
// `wasi - libc`s implementation of these symbols have percolated further it'll
// be possible to use this on all WASI targets. The `wasi - libc` implementation
// of these symbols differs depending on the target and configuration:
//
// * `wasm32 - wasip { 1, 2 }` - this will abort if blocking actually happens
// * `wasm32 - wasip1 - threads` - this uses wasm `memory.atomic.*` instructions
// * `wasm32 - wasip3` - depending on libc configuration (`ENABLE_COOP_THREADS`)
//   this either aborts (coop threads disables) on blocking or does the
//   coop-thread-thing to manage threads.
//
// Regardless this module is effectively delegating to `wasi - libc` to determine
// how to do thread management.

public val WASILIBC_FUTEX_WAKE_ALL: CInt = -1

public expect fun wasilibcFutexWait(addr: MutPtr, value: CInt, clock: ClockidT, at: ConstPtr, flags: CUint): CInt

public expect fun wasilibcFutexWake(addr: ConstPtr, count: CInt, flags: CUint): CInt

public typealias Futex = Atomic<Primitive>

public typealias Primitive = UInt

public typealias SmallFutex = Atomic<SmallPrimitive>

public typealias SmallPrimitive = UInt

public fun futexWait(futex: Atomic<UInt>, expected: UInt, timeout: Duration?): Boolean {
    val timespec =
        timeout?.let(closure@{ t ->
            Timespec(tvSec = when (val tryResult = t.asSecs().tryInto().ok()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }, tvNsec = when (val tryResult = t.subsecNanos().tryInto().ok()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) })
        })
    return wasilibcFutexWait(futex.asPtr().cast(), expected.castSigned(), CLOCK_REALTIME, (timespec?.let(fromRef) ?: ConstPtr(0L)), 0u) == 0
}

public fun futexWake(futex: Atomic<UInt>): Boolean = wasilibcFutexWake(futex.asPtr().cast(), 1, 0u) == 1

public fun futexWakeAll(futex: Atomic<UInt>) {
    run {
        wasilibcFutexWake(futex.asPtr().cast(), WASILIBC_FUTEX_WAKE_ALL, 0u)
    }
}
