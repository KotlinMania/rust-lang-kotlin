// port-lint: source sys/sync/once_box.rs
package io.github.kotlinmania.rustlang.std.sys.sync.oncebox

import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Align8
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Acquire
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Relaxed
import io.github.kotlinmania.rustlang.std.sync.atomic.ordering.Release

// A racily-initialized alternative to `OnceLock<Holder<T>>`.
//
// This is used to implement synchronization primitives that need allocation,
// like the pthread versions.

public open class OnceBox<T> internal constructor(
    ptrCtorArg: Atomic<MutPtr, Align8<MutPtr>>,
) {
    public var ptr: Atomic<MutPtr, Align8<MutPtr>> = ptrCtorArg
        internal set

    /**
     * Gets access to the value, assuming it is already initialized and this
     * initialization has been observed by the current thread.
     *
     * Since all modifications to the pointer have already been observed, the
     * pointer load in this function can be performed with relaxed ordering,
     * potentially allowing the optimizer to turn code like this:
     * ```rust, ignore
     * onceBox.getOrInit(|| Box.pin(42));
     * unchecked { onceBox.getUnchecked() }
     * ```
     * into
     * ```rust, ignore
     * onceBox.getOrInit(|| Box.pin(42))
     * ```
     *
     * # Safety
     * This causes undefined behavior if the assumption above is violated.
     */
    public open fun getUnchecked(): Pin<T> = Pin.newUnchecked(this.ptr.load(Relaxed))

    public open fun getOrInit(f: () -> Pin<T>): Pin<T> {
        val ptr = this.ptr.load(Acquire)
        return when (val matchVal = ptr.asRef()) {
            null -> this.initialize(f)
            else -> {
                val value = matchVal
                Pin.newUnchecked(value)
            }
        }
    }

    public open fun take(): Pin<T>? {
        val ptr = replace(this.ptr.getMut(), MutPtr(0L))
        return if (!ptr.isNull()) {
            Pin.newUnchecked(Box.fromRaw(ptr))
        } else {
            null
        }
    }

    public open fun initialize(f: () -> Pin<T>): Pin<T> {
        val newPtr = Box.intoRaw(Pin.intoInnerUnchecked(f()))
        return run {
            val matchVal = this.ptr.compareExchange(MutPtr(0L), newPtr, Release, Acquire)
            when (matchVal) { is Result.Ok -> {
                Pin.newUnchecked(newPtr)
            }; is Result.Err -> {
                val ptr = matchVal.error
                // Lost the race to another thread.
                // Drop the value we created, and use the one from the other thread instead.
                (Box.fromRaw(newPtr)).let { }
                Pin.newUnchecked(ptr)
            } }
        }
    }

    public open fun drop() {
        this.also { this = null }
    }

    public companion object {
        public operator fun <T> invoke(
            ptr: Atomic<MutPtr, Align8<MutPtr>>,
        ): OnceBox<T> = OnceBox<T>(
            ptrCtorArg = ptr,
        )

        public fun <T> new(): OnceBox<T> = OnceBox(ptr = AtomicPtr.new(MutPtr(0L)))
    }
}
