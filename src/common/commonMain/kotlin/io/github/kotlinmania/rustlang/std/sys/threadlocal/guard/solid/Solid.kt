// port-lint: source sys/thread_local/guard/solid.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.solid

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.abi.SolidTlsAddDestructor
import io.github.kotlinmania.rustlang.std.rt.threadCleanup
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.Task
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.tests.destructors

// SOLID, just like macOS, has an API to register TLS destructors. But since
// it does not allow specifying an argument to that function, and will not run
// destructors for terminated tasks, we still keep our own list.

public fun enable() {
    val REGISTERED: Cell<Boolean>  = Cell.new(false)
    fun tlsDtor(unused: MutPtr) {
        run {
            destructors.run()
            threadCleanup()
        }
    }
    if (!REGISTERED.replace(true)) {
        val tid = Task.currentTaskIdAborting()
        // Register `tlsDtor` to make sure the TLS destructors are called
        // for tasks created by other means than `std.thread`
        SolidTlsAddDestructor(tid, ::tlsDtor)
    }
}
