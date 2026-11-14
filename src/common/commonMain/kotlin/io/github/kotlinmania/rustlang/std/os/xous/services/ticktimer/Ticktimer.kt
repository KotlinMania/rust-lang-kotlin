// port-lint: source os/xous/services/ticktimer.rs
package io.github.kotlinmania.rustlang.std.os.xous.services.ticktimer

import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.os.xous.ffi.connect
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Connection

internal sealed class TicktimerScalar {
    public data object ElapsedMs : TicktimerScalar()
    public data class SleepMs(public var value: Int) : TicktimerScalar()
    public data class LockMutex(public var value: Int) : TicktimerScalar()
    public data class UnlockMutex(public var value: Int) : TicktimerScalar()
    public data class WaitForCondition(public var field0: Int, public var field1: Int) : TicktimerScalar()
    public data class NotifyCondition(public var field0: Int, public var field1: Int) : TicktimerScalar()
    public data class FreeMutex(public var value: Int) : TicktimerScalar()
    public data class FreeCondition(public var value: Int) : TicktimerScalar()

    public open fun into(): IntArray =
        when (this) {
            TicktimerScalar.ElapsedMs -> listOf(0, 0, 0, 0, 0)
            is TicktimerScalar.SleepMs -> {
                val (msecs) = this
                intArrayOf(1, msecs, 0, 0, 0)
            }
            is TicktimerScalar.LockMutex -> {
                val (cookie) = this
                intArrayOf(6, cookie, 0, 0, 0)
            }
            is TicktimerScalar.UnlockMutex -> {
                val (cookie1) = this
                intArrayOf(7, cookie1, 0, 0, 0)
            }
            is TicktimerScalar.WaitForCondition -> {
                val (cookie2, timeoutMs) = this
                intArrayOf(8, cookie2, timeoutMs, 0, 0)
            }
            is TicktimerScalar.NotifyCondition -> {
                val (cookie3, count) = this
                intArrayOf(9, cookie3, count, 0, 0)
            }
            is TicktimerScalar.FreeMutex -> {
                val (cookie4) = this
                intArrayOf(10, cookie4, 0, 0, 0)
            }
            is TicktimerScalar.FreeCondition -> {
                val (cookie5) = this
                intArrayOf(11, cookie5, 0, 0, 0)
            }
        }
}

/**
 * Returns a `Connection` to the ticktimer server. This server is used for synchronization
 * primitives such as sleep, Mutex, and Condvar.
 */
internal fun ticktimerServer(): Connection {
    val TICKTIMER_SERVER_CONNECTION: Atomic<UInt>  = AtomicU32.new(0)
    val cid = TICKTIMER_SERVER_CONNECTION.load(Ordering.Relaxed)
    if (cid != 0) {
        return cid.into()
    }
    val cid1 = connect("ticktimer-server".tryInto().unwrap()).unwrap()
    TICKTIMER_SERVER_CONNECTION.store(cid1.into(), Ordering.Relaxed)
    return cid1
}
