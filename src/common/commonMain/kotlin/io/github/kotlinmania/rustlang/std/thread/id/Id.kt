// port-lint: source thread/id.rs
package io.github.kotlinmania.rustlang.std.thread.id

import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU64
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

/**
 * A unique identifier for a running thread.
 *
 * A `ThreadId` is an opaque object that uniquely identifies each thread
 * created during the scope of a process. `ThreadId`s are guaranteed not to
 * be reused, even when a thread terminates. `ThreadId`s are under the control
 * of Rust's standard library and there may not be any relationship between
 * `ThreadId` and the underlying platform's notion of a thread identifier --
 * the two concepts cannot, therefore, be used interchangeably. A `ThreadId`
 * can be retrieved from the [id] method on a [Thread].
 *
 * # Examples
 *
 * ```kotlin
 * import std.thread
 *
 * internal expect fun currentThreadBoundToken(): Any
 *
 * public fun doctestEntry() {
 *     val otherThread = thread.spawn({
 *         currentThreadBoundToken()
 *     })
 *     val otherThreadId = otherThread.join().unwrap()
 *     check(currentThreadBoundToken() != otherThreadId)
 * }
 * ```
 */
public open class ThreadId internal constructor(
    valueCtorArg: NonZero<ULong>,
) {
    public var value: NonZero<ULong> = valueCtorArg
        internal set

    public var field0: NonZero<ULong>
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ThreadId = ThreadId(value)

    /**
     * This returns a numeric identifier for the thread identified by this
     * `ThreadId`.
     *
     * As noted in the documentation for the type itself, it is essentially an
     * opaque ID, but is guaranteed to be unique for each thread. The returned
     * value is entirely opaque -- only equality testing is stable. Note that
     * it is not guaranteed which values new threads will return, and this may
     * change across Rust versions.
     */
    public open fun asU64(): NonZero<ULong> = this.value

    public companion object {
        public operator fun invoke(
            value: NonZero<ULong>,
        ): ThreadId = ThreadId(
            valueCtorArg = value,
        )

        // Generate a new unique thread ID.
        public fun new(): ThreadId {
            fun exhausted(): Nothing {
                error("failed to generate unique thread ID: bitspace exhausted")
            }
            val COUNTER: Atomic<ULong>  = AtomicU64.new(0)
            var last = COUNTER.load(Ordering.Relaxed)
            while (true) {
                val id = last.checkedAdd(1) ?: exhausted()
                run {
                    val matchVal = COUNTER.compareExchangeWeak(last, id, Ordering.Relaxed, Ordering.Relaxed)
                    when (matchVal) { is Result.Ok<*> -> {
                        return ThreadId(NonZero.new(id).unwrap())
                    }; is Result.Err<*> -> {
                        val id1 = matchVal.error
                        last = id1
                    } }
                }
            }
        }

        public fun fromU64(v: ULong): ThreadId? = NonZero.new(v).map({ ThreadId(it) })
    }
}
