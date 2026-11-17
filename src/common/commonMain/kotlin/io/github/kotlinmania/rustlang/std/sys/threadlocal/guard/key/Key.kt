// port-lint: source sys/thread_local/guard/key.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.key

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.withoutProvenanceMut
import io.github.kotlinmania.rustlang.std.rt.threadCleanup
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.racy.LazyKey
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.tests.destructors
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.unix.set
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.windows.DTORS

// A lot of UNIX platforms don't have a specialized way to register TLS destructors for native TLS
// (i.e., `@threadLocal static`). Instead, we use one TLS key with a destructor that will run
// all native TLS destructors in the destructor list. It also runs `rt.threadCleanup`.

public fun enable() {
    fun run(arg0: MutPtr) {
        run {
            // Rust thread-local destructors should run *before* `rt.threadCleanup`.
            destructors.run()
            // On platforms with `__cxa_thread_atexit_impl`, `destructors.run` does nothing as the
            // TLS destructors are registered with the system. But because all of those platforms
            // call the destructors of TLS keys *after* the registered "atexit" ones, this function
            // will still be run last (at the time of writing). The `threadCurrentInDtor` test
            // confirms this.
            threadCleanup()
        }
    }
    val DTORS: LazyKey  = LazyKey.new(::run)
    // Setting the key value to something other than NULL will result in the
    // destructor being run at thread exit.
    run {
        set(DTORS.force(), ptr.withoutProvenanceMut(1))
    }
}
