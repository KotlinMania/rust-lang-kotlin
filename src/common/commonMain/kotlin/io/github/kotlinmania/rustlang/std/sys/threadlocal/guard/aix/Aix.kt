// port-lint: source sys/thread_local/guard/aix.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.aix

import io.github.kotlinmania.rustlang.core.cell.Cell
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.rt.threadCleanup
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.threadlocal.key.tests.destructors

// AIX registers thread destructors using `__pt_atexit_np`.
// Calling it registers a callback that runs on thread termination
// and upon process exit (for the main thread).

public fun interface PtAtexitNpDtorCallback {
    public operator fun invoke(arg0: Int): Int
}

public fun enable() {
    val REGISTERED: Cell<Boolean>  = Cell.new(false)
    fun ptAtexitNp(flags: Int, dtor: PtAtexitNpDtorCallback): Int = 0
    fun runDtors(arg0: Int): Int {
        run {
            destructors.run()
            threadCleanup()
        }
        return 0
    }
    if (!REGISTERED.replace(true)) {
        val ret = ptAtexitNp(0, ::runDtors)
        if (ret != 0) {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("failed to register thread destructor").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("failed to register thread destructor").toString() + ", aborting\n"
                }
                abort()
            }
        }
    }
}
