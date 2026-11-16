// port-lint: source sys/pal/unix/sync/condvar.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unix.sync.condvar

import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.libc.pthreadCondBroadcast
import io.github.kotlinmania.rustlang.libc.pthreadCondDestroy
import io.github.kotlinmania.rustlang.libc.pthreadCondInit
import io.github.kotlinmania.rustlang.libc.pthreadCondSignal
import io.github.kotlinmania.rustlang.libc.pthreadCondTimedwait
import io.github.kotlinmania.rustlang.libc.pthreadCondWait
import io.github.kotlinmania.rustlang.libc.pthreadCondattrDestroy
import io.github.kotlinmania.rustlang.libc.pthreadCondattrInit
import io.github.kotlinmania.rustlang.libc.pthreadCondattrSetclock
import io.github.kotlinmania.rustlang.libc.wasi.CLOCK_MONOTONIC
import io.github.kotlinmania.rustlang.libc.wasi.CLOCK_REALTIME
import io.github.kotlinmania.rustlang.libc.wasi.ClockidT
import io.github.kotlinmania.rustlang.libc.wasi.EINVAL
import io.github.kotlinmania.rustlang.libc.wasi.ETIMEDOUT
import io.github.kotlinmania.rustlang.libc.wasi.PTHREAD_COND_INITIALIZER
import io.github.kotlinmania.rustlang.libc.wasi.PthreadCondT
import io.github.kotlinmania.rustlang.libc.wasi.PthreadCondattrT
import io.github.kotlinmania.rustlang.std.sys.helpers.copaque.COpaque
import io.github.kotlinmania.rustlang.std.sys.pal.unix.sync.mutex.AttrGuard
import io.github.kotlinmania.rustlang.std.sys.pal.unix.sync.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sys.pal.unix.time.TIMESPEC_MAX_CAPPED
import io.github.kotlinmania.rustlang.std.sys.pal.unix.time.Timespec

public open class Condvar internal constructor(
    innerCtorArg: COpaque<PthreadCondT>,
) {
    public var inner: COpaque<PthreadCondT> = innerCtorArg
        internal set

    public open fun raw(): MutPtr {
        val inner = this.mapUnchecked({ c -> c.inner })
        return inner.get()
    }

    /**
     * # Safety
     * `init` must have been called on this instance.
     */
    public open fun notifyOne() {
        val r = pthreadCondSignal(this.raw())
        check(r == 0)
    }

    /**
     * # Safety
     * `init` must have been called on this instance.
     */
    public open fun notifyAll() {
        val r = pthreadCondBroadcast(this.raw())
        check(r == 0)
    }

    /**
     * # Safety
     * * `init` must have been called on this instance.
     * * `mutex` must be locked by the current thread.
     * * This condition variable may only be used with the same mutex.
     */
    public open fun wait(mutex: Pin<Mutex>) {
        val r = pthreadCondWait(this.raw(), mutex.raw())
        check(r == 0)
    }

    /**
     * # Safety
     * * `init` must have been called on this instance.
     * * `mutex` must be locked by the current thread.
     * * This condition variable may only be used with the same mutex.
     */
    public open fun waitTimeout(mutex: Pin<Mutex>, dur: Duration): Boolean {
        val mutex1 = mutex.raw()
        val timeout = Timespec.now(Condvar.CLOCK).checkedAddDuration(dur)
        val timeout1 = timeout.andThen({ t -> t.toTimespecCapped() }).unwrapOr(TIMESPEC_MAX_CAPPED)
        val r = pthreadCondTimedwait(this.raw(), mutex1, timeout1)
        check(r == ETIMEDOUT || r == 0)
        return (r == 0).toUByte()
    }

    // Apple platforms (since macOS version 10.4 and iOS version 2.0) have
    // `pthreadCondTimedwaitRelativeNp`, a non-standard extension that
    // measures timeouts based on the monotonic clock and is thus resilient
    // against wall-clock changes.

    /**
     * # Safety
     * May only be called once per instance of `Self`.
     */
    public open fun init() {
        open class AttrGuard(
            public var value: Pin<COpaque<PthreadCondattrT>>,
        ) {
            public var field0: Pin<COpaque<PthreadCondattrT>>
                get() = value
                set(v) {
                    value = v
                }

            public open fun drop() {
                run {
                    val result = pthreadCondattrDestroy(this.value.get())
                    check(result == 0)
                }
            }
        }
        run {
            val attr = pin(COpaque.uninit())
            // Note(pin-ergonomics): remove the next line.
            val attr1 = attr.intoRef()
            val r = pthreadCondattrInit(attr1.get())
            check(r == 0)
            val attr2 = AttrGuard(attr1)
            val r1 = pthreadCondattrSetclock(attr2.value.get(), Condvar.CLOCK)
            check(r1 == 0)
            val r2 = pthreadCondInit(this.asRef().raw(), attr2.value.get())
            check(r2 == 0)
        }
    }

    public open fun drop() {
        val inner = Pin.newUnchecked(this.inner)
        val r = pthreadCondDestroy(inner.get())
        if (false) {
            // On DragonFly pthreadCondDestroy() returns EINVAL if called on
            // a condvar that was just initialized with
            // libc.PthreadCondInitializer. Once it is used or
            // pthreadCondInit() is called, this behaviour no longer occurs.
            check(r == 0 || r == EINVAL)
        } else {
            check(r == 0)
        }
    }

    public companion object {
        public operator fun invoke(
            inner: COpaque<PthreadCondT>,
        ): Condvar = Condvar(
            innerCtorArg = inner,
        )

        public fun new(): Condvar = Condvar(inner = COpaque.new(PTHREAD_COND_INITIALIZER))

        public val PRECISE_TIMEOUT: Boolean = true

        public val CLOCK: ClockidT = CLOCK_MONOTONIC

        // `pthreadCondTimedwaitRelativeNp` measures the timeout
        // based on the monotonic clock.
        public val PRECISE_TIMEOUT1: Boolean = true

        // `pthreadCondattrSetclock` is unfortunately not supported on these platforms.
        public val PRECISE_TIMEOUT2: Boolean = false

        public val CLOCK1: ClockidT = CLOCK_REALTIME
    }
}
