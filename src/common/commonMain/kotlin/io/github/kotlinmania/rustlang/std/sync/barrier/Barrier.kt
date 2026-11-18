// port-lint: source sync/barrier.rs
package io.github.kotlinmania.rustlang.std.sync.barrier

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sync.nonpoison.condvar.Condvar
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex

private class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * A barrier enables multiple threads to synchronize the beginning
 * of some computation.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.Barrier
 * import std.thread
 *
 * val n = 10
 * val barrier = Barrier.new(n)
 * thread.scope({ s ->
 *     for (unused in 0 until n) {
 *         // The same messages will be printed together.
 *         // You will NOT see any interleaving.
 *         s.spawn({
 *             println("before wait")
 *             barrier.wait()
 *             println("after wait")
 *         })
 *     }
 * })
 * ```
 */
public open class Barrier internal constructor(
    lockCtorArg: Mutex<BarrierState>,
    public var cvar: Condvar,
    public var numThreads: Int,
) {
    public var lock: Mutex<BarrierState> = lockCtorArg
        internal set

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "Barrier").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    /**
     * Blocks the current thread until all threads have rendezvoused here.
     *
     * Barriers are re-usable after all threads have rendezvoused once, and can
     * be used continuously.
     *
     * A single (arbitrary) thread will receive a [BarrierWaitResult] that
     * returns `true` from [BarrierWaitResult.isLeader] when returning
     * from this function, and all other threads will receive a result that
     * will return `false` from [BarrierWaitResult.isLeader].
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Barrier
     * import std.thread
     *
     * val n = 10
     * val barrier = Barrier.new(n)
     * thread.scope({ s ->
     *     for (unused in 0 until n) {
     *         // The same messages will be printed together.
     *         // You will NOT see any interleaving.
     *         s.spawn({
     *             println("before wait")
     *             barrier.wait()
     *             println("after wait")
     *         })
     *     }
     * })
     * ```
     */
    public open fun wait(): BarrierWaitResult {
        val lock = this.lock.lock()
        val localGen = lock.generationId
        lock.count += 1
        return if (lock.count < this.numThreads) {
            this.cvar.waitWhile(lock, { state -> localGen == state.generationId })
            BarrierWaitResult(false)
        } else {
            lock.count = 0
            lock.generationId = lock.generationId.wrappingAdd(1)
            this.cvar.notifyAll()
            BarrierWaitResult(true)
        }
    }

    public companion object {
        public operator fun invoke(
            lock: Mutex<BarrierState>,
            cvar: Condvar,
            numThreads: Int,
        ): Barrier = Barrier(
            lockCtorArg = lock,
            cvar = cvar,
            numThreads = numThreads,
        )

        /**
         * Creates a new barrier that can block a given number of threads.
         *
         * A barrier will block all threads which call [wait] until the `n`th thread calls [wait],
         * and then wake up all threads at once.
         *
         * [wait]: Barrier.wait
         *
         * # Examples
         *
         * ```kotlin
         * import std.sync.Barrier
         *
         * val barrier = Barrier.new(10)
         * ```
         */
        public fun new(n: Int): Barrier = Barrier(lock = Mutex.new(BarrierState(count = 0, generationId = 0)), cvar = Condvar.new(), numThreads = n)
    }
}

// The inner state of a double barrier
public open class BarrierState(
    public var count: Int,
    public var generationId: Int,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<BarrierState> =
            object : TypeLayout<BarrierState> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): BarrierState =
                    BarrierState(
                        (PrimitiveLayouts.USIZE64).readNative(addr),
                        (PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: BarrierState) {
                    (PrimitiveLayouts.USIZE64).writeNative(addr, value.count)
                    (PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.generationId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): BarrierState =
                    BarrierState(
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: BarrierState) {
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.count)
                    (PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.generationId)
                }
            }
    }
}

/**
 * A `BarrierWaitResult` is returned by [Barrier.wait] when all threads
 * in the [Barrier] have rendezvoused.
 *
 * # Examples
 *
 * ```kotlin
 * import std.sync.Barrier
 *
 * val barrier = Barrier.new(1)
 * val barrierWaitResult = barrier.wait()
 * ```
 */
public open class BarrierWaitResult(
    public var value: Boolean,
) {
    public var field0: Boolean
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "BarrierWaitResult").field("is_leader", this.isLeader()).finish(); Result.Ok<Unit, Error>(Unit) }

    /**
     * Returns `true` if this thread is the "leader thread" for the call to
     * [Barrier.wait].
     *
     * Only one thread will have `true` returned from their result, all other
     * threads will have `false` returned.
     *
     * # Examples
     *
     * ```kotlin
     * import std.sync.Barrier
     *
     * val barrier = Barrier.new(1)
     * val barrierWaitResult = barrier.wait()
     * println("${barrierWaitResult.isLeader()}")
     * ```
     */
    public open fun isLeader(): Boolean = this.value

    public companion object {
        public fun typeLayout(): TypeLayout<BarrierWaitResult> =
            object : TypeLayout<BarrierWaitResult> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): BarrierWaitResult =
                    BarrierWaitResult(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: BarrierWaitResult) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): BarrierWaitResult =
                    BarrierWaitResult(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: BarrierWaitResult) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.value)
                }
            }
    }
}
