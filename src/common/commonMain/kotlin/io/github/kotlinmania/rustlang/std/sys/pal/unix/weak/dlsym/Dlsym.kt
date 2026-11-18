// port-lint: source sys/pal/unix/weak/dlsym.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unix.weak.dlsym

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.transmuteCopy
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.addr
import io.github.kotlinmania.rustlang.core.ptr.constptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.addr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.load
import io.github.kotlinmania.rustlang.core.sync.atomic.store
import io.github.kotlinmania.rustlang.libc.dlsym
import io.github.kotlinmania.rustlang.libc.unix.bsd.RTLD_DEFAULT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.setthreaddescription.withoutProvenanceMut

public open class DlsymWeak<F> internal constructor(
    nameCtorArg: ConstPtr,
    public var func: AtomicPtr,
    public var marker: Unit,
) {
    // Use a pointer instead of `CStr` to save space.

    /**
     * A pointer to the nul-terminated name of the symbol.
     */
    public var name: ConstPtr = nameCtorArg
        internal set

    public open fun get(): F? {
        // The caller is presumably going to read through this value
        // (by calling the function we've dlsymed). This means we'd
        // need to have loaded it with at least C11's consume
        // ordering in order to be guaranteed that the data we read
        // from the pointer isn't from before the pointer was
        // stored. Rust has no equivalent to memoryOrderConsume,
        // so we use an acquire load (sorry, ARM).
        //
        // Now, in practice this likely isn't needed even on CPUs
        // where relaxed and consume mean different things. The
        // symbols we're loading are probably present (or not) at
        // init, and even if they aren't the runtime dynamic loader
        // is extremely likely have sufficient barriers internally
        // (possibly implicitly, for example the ones provided by
        // invoking `mprotect`).
        //
        // That said, none of that's *guaranteed*, so we use acquire.
        return run {
            val matchVal = this.func.load(Ordering.Acquire)
            when {
                matchVal.addr() == 1 -> {
                    val func = matchVal
                    this.initialize()
                }
                matchVal.isNull() -> {
                    val func1 = matchVal
                    null
                }
                // SAFETY:
                // `func` is not null and `F` implements `FnPtr`, thus this
                // transmutation is well-defined. It is the responsibility of the
                // creator of this `DlsymWeak` to ensure that calling the resulting
                // function pointer does not result in undefined behaviour (though
                // the `weak` macro delegates this responsibility to the caller
                // of the function by using `unchecked` function pointers).
                // Note: use `reinterpret` once it stops complaining about generics.
                else -> {
                    val func2 = matchVal
                    transmuteCopy<MutPtr, F>(func2)
                }
            }
        }
    }

    // Cold because it should only happen during first-time initialization.
    public open fun initialize(): F? {
        // SAFETY: `self.name` was created from a `CStr` and is
        // therefore a valid C string pointer.
        val value = dlsym(RTLD_DEFAULT, this.name)
        // This synchronizes with the acquire load in `get`.
        this.func.store(value, Ordering.Release)
        return if (value.isNull()) {
            null
        } else {
            // SAFETY: see the comment in `get`.
            // Note: use `reinterpret` once it stops complaining about generics.
            transmuteCopy<MutPtr, F>(value)
        }
    }

    public companion object {
        public operator fun <F> invoke(
            name: ConstPtr,
            func: AtomicPtr,
            marker: Unit,
        ): DlsymWeak<F> = DlsymWeak<F>(
            nameCtorArg = name,
            func = func,
            marker = marker,
        )

        /**
         * # Safety
         *
         * If the signature of `F` does not match the signature of the symbol (if
         * it exists), calling the function pointer returned by `get()` is
         * undefined behaviour.
         */
        public fun <F> new(name: CStr): DlsymWeak<F> = DlsymWeak(name = name.asPtr(), func = AtomicPtr.new(withoutProvenanceMut(1)), marker = Unit)
    }
}
