// port-lint: source sys/pal/unix/sync/mutex.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unix.sync.mutex

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.pin.Pin
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.pthreadMutexDestroy
import io.github.kotlinmania.rustlang.libc.pthreadMutexInit
import io.github.kotlinmania.rustlang.libc.pthreadMutexLock
import io.github.kotlinmania.rustlang.libc.pthreadMutexTrylock
import io.github.kotlinmania.rustlang.libc.pthreadMutexUnlock
import io.github.kotlinmania.rustlang.libc.pthreadMutexattrDestroy
import io.github.kotlinmania.rustlang.libc.pthreadMutexattrInit
import io.github.kotlinmania.rustlang.libc.pthreadMutexattrSettype
import io.github.kotlinmania.rustlang.libc.wasi.EINVAL
import io.github.kotlinmania.rustlang.libc.wasi.PTHREAD_MUTEX_INITIALIZER
import io.github.kotlinmania.rustlang.libc.wasi.PTHREAD_MUTEX_NORMAL
import io.github.kotlinmania.rustlang.libc.wasi.PthreadMutexT
import io.github.kotlinmania.rustlang.libc.wasi.PthreadMutexattrT
import io.github.kotlinmania.rustlang.std.sys.helpers.copaque.COpaque
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvtNz

public open class Mutex internal constructor(
    innerCtorArg: COpaque<PthreadMutexT>,
) {
    public var inner: COpaque<PthreadMutexT> = innerCtorArg
        internal set

    public open fun raw(): MutPtr {
        val inner = this.mapUnchecked({ m -> m.inner })
        return inner.get()
    }

    /**
     * # Safety
     * May only be called once per instance of `Self`.
     */
    public open fun init() {
        // Issue #33770
        //
        // A pthread mutex initialized with PTHREAD_MUTEX_INITIALIZER will have
        // a type of PTHREAD_MUTEX_DEFAULT, which has undefined behavior if you
        // try to re-lock it from the same thread when you already hold a lock
        // (https://pubs.opengroup.org/onlinepubs/9799919799/functions/pthreadMutexInit.html).
        // This is the case even if PTHREAD_MUTEX_DEFAULT == PTHREAD_MUTEX_NORMAL
        // (https://github.com/rust-lang/rust/issues/33770#issuecomment-220847521) -- in that
        // case, `pthreadMutexattrSettype(PTHREAD_MUTEX_DEFAULT)` will of course be the same
        // as setting it to `PTHREAD_MUTEX_NORMAL`, but not setting any mode will result in
        // a Mutex where re-locking is UB.
        //
        // In practice, glibc takes advantage of this undefined behavior to
        // implement hardware lock elision, which uses hardware transactional
        // memory to avoid acquiring the lock. While a transaction is in
        // progress, the lock appears to be unlocked. This isn't a problem for
        // other threads since the transactional memory will abort if a conflict
        // is detected, however no abort is generated when re-locking from the
        // same thread.
        //
        // Since locking the same mutex twice will result in two aliasing &mut
        // references, we instead create the mutex with type
        // PTHREAD_MUTEX_NORMAL which is guaranteed to deadlock if we try to
        // re-lock it from the same thread, thus avoiding undefined behavior.
        run {
            val attr = pin(COpaque.uninit())
            // Note(pin-ergonomics): remove the next line.
            val attr1 = attr.intoRef()
            cvtNz(pthreadMutexattrInit(attr1.get())).unwrap()
            val attr2 = AttrGuard(attr1)
            cvtNz(pthreadMutexattrSettype(attr2.value.get(), PTHREAD_MUTEX_NORMAL)).unwrap()
            cvtNz(pthreadMutexInit(this.asRef().raw(), attr2.value.get())).unwrap()
        }
    }

    /**
     * # Safety
     * * If `init` was not called on this instance, reentrant locking causes
     *   undefined behaviour.
     * * Destroying a locked mutex causes undefined behaviour.
     */
    public open fun lock() {
        fun fail(r: Int): Nothing {
            val error = Error.fromRawOsError(r)
            error("failed to lock mutex: $error")
        }
        val r = pthreadMutexLock(this.raw())
        // As we set the mutex type to `PTHREAD_MUTEX_NORMAL` above, we expect
        // the lock call to never fail. Unfortunately however, some platforms
        // (Solaris) do not conform to the standard, and instead always provide
        // deadlock detection. How kind of them! Unfortunately that means that
        // we need to check the error code here. To save us from UB on other
        // less well-behaved platforms in the future, we do it even on "good"
        // platforms like macOS. See #120147 for more context.
        if (r != 0) {
            fail(r)
        }
    }

    /**
     * # Safety
     * * If `init` was not called on this instance, reentrant locking causes
     *   undefined behaviour.
     * * Destroying a locked mutex causes undefined behaviour.
     */
    public open fun tryLock(): Boolean = pthreadMutexTrylock(this.raw()) == 0

    /**
     * # Safety
     * The mutex must be locked by the current thread.
     */
    public open fun unlock() {
        val r = pthreadMutexUnlock(this.raw())
        check(r == 0)
    }

    public open fun drop() {
        val inner = Pin.newUnchecked(this.inner)
        // SAFETY:
        // If `lock` or `init` was called, the mutex must have been pinned, so
        // it is still at the same location. Otherwise, `inner` must contain
        // `PTHREAD_MUTEX_INITIALIZER`, which is valid at all locations. Thus,
        // this call always destroys a valid mutex.
        val r = pthreadMutexDestroy(inner.get())
        if (false) {
            // On AIX and DragonFly pthreadMutexDestroy() returns EINVAL if called
            // on a mutex that was just initialized with libc.PthreadMutexInitializer.
            // Once it is used (locked/unlocked) or pthreadMutexInit() is called,
            // this behaviour no longer occurs.
            check(r == 0 || r == EINVAL)
        } else {
            check(r == 0)
        }
    }

    public companion object {
        public operator fun invoke(
            inner: COpaque<PthreadMutexT>,
        ): Mutex = Mutex(
            innerCtorArg = inner,
        )

        public fun new(): Mutex = Mutex(inner = COpaque.new(PTHREAD_MUTEX_INITIALIZER))
    }
}

public open class AttrGuard internal constructor(
    valueCtorArg: Pin<COpaque<PthreadMutexattrT>>,
) {
    public var value: Pin<COpaque<PthreadMutexattrT>> = valueCtorArg
        internal set

    public var field0: Pin<COpaque<PthreadMutexattrT>>
        get() = value
        set(v) {
            value = v
        }

    public open fun drop() {
        run {
            val result = pthreadMutexattrDestroy(this.value.get())
            check(result == 0)
        }
    }

    public companion object {
        public operator fun invoke(
            value: Pin<COpaque<PthreadMutexattrT>>,
        ): AttrGuard = AttrGuard(
            valueCtorArg = value,
        )
    }
}
