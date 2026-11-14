package io.github.kotlinmania.rustlang.std.sync

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout

/**
 * A type indicating whether a timed wait on a condition variable returned
 * due to a time out or not.
 *
 * It is returned by the [waitTimeout] method.
 *
 * [waitTimeout]: Condvar.waitTimeout
 */
public open class WaitTimeoutResult(
    public var value: Boolean,
) {
    public var field0: Boolean
        get() = value
        set(v) {
            value = v
        }

    /**
     * Returns `true` if the wait was known to have timed out.
     *
     * # Examples
     *
     * This example spawns a thread which will sleep 20 milliseconds before
     * updating a boolean value and then notifying the condvar.
     *
     * The main thread will wait with a 10 millisecond timeout on the condvar
     * and will leave the loop upon timeout.
     *
     * ```kotlin
     * import std.sync.Condvar
     * import std.sync.Mutex
     * import std.time.Duration
     *
     * val pair = Pair(Mutex.new(false), Condvar.new())
     * val pair2 = pair
     * thread.spawn({
     *     val (lock, cvar) = pair2
     *     // Let's wait 20 milliseconds before notifying the condvar.
     *     thread.sleep(Duration.fromMillis(20))
     *     var started = lock.lock().unwrap()
     *     // We update the boolean value.
     *     started = true
     *     cvar.notifyOne()
     * })
     * // Wait for the thread to start up.
     * val (lock, cvar) = pair
     * while (true) {
     *     // Let's put a timeout on the condvar's wait.
     *     val result = cvar.waitTimeout(lock.lock().unwrap(), Duration.fromMillis(10)).unwrap()
     *     // 10 milliseconds have passed.
     *     if (result.second.timedOut()) {
     *         // timed out now and we can leave.
     *         break
     *     }
     * }
     * ```
     */
    public open fun timedOut(): Boolean = this.value

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<WaitTimeoutResult> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<WaitTimeoutResult> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): WaitTimeoutResult =
                    WaitTimeoutResult(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: WaitTimeoutResult) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): WaitTimeoutResult =
                    WaitTimeoutResult(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: WaitTimeoutResult) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.value)
                }
            }
    }
}
