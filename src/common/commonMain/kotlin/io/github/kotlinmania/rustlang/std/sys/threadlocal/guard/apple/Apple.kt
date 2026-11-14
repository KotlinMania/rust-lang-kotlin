// port-lint: source sys/thread_local/guard/apple.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.apple

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.rt.threadCleanup
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.tests.destructors

// macOS allows registering destructors through _tlv_atexit. But since calling
// it while TLS destructors are running is UB, we still need to keep our own
// list of destructors.

public fun interface TlvAtexitDtorCallback {
    public operator fun invoke(arg0: MutPtr)
}

public fun enable() {
    val REGISTERED: Cell<Boolean>  = Cell.new(false)
    fun tlvAtexit(dtor: TlvAtexitDtorCallback, arg: MutPtr) {}
    fun runDtors(arg0: MutPtr) {
        run {
            destructors.run()
            threadCleanup()
        }
    }
    if (!REGISTERED.replace(true)) {
        // SAFETY: Calling _tlv_atexit while TLS destructors are running is UB.
        // But as runDtors is only called after being registered, this point
        // cannot be reached from it.
        run {
            tlvAtexit(::runDtors, MutPtr(0L))
        }
    }
}
