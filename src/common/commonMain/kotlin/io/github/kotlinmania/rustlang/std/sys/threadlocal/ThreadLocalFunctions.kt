package io.github.kotlinmania.rustlang.std.sys.threadlocal

import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.threadlocal.list.run

/**
 * Run a callback in a scenario which must not unwind (such as a `extern "C"
 * fn` declared in a user crate). If the callback unwinds anyway, then
 * `rtabort` with a message about thread local panicking on drop.
 */
public fun abortOnDtorUnwind(f: () -> Unit) {
    open class DtorUnwindGuard {
        public open fun drop() {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("thread local panicked on drop").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("thread local panicked on drop").toString() + ", aborting\n"
                }
                abort()
            }
        }
    }
    // Using a guard like this is lower cost.
    val guard = DtorUnwindGuard()
    f()
    forget(guard)
}
