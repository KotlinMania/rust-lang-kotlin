// port-lint: source sys/thread_local/destructors/list.rs
package io.github.kotlinmania.rustlang.std.sys.threadlocal.destructors.list

import io.github.kotlinmania.rustlang.core.cell.RefCell
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.std.alloc.System
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.threadlocal.guard.enable

public fun interface FnCallback {
    public operator fun invoke(arg0: MutPtr)
}

public var DTORS: RefCell<List<Pair<MutPtr, FnCallback>>> = RefCell.new(Vec.newIn(System()))

public fun register(t: MutPtr, dtor: FnCallback) {
    var dtors = when (val tryResult = DTORS.tryBorrowMut()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> run {
    when (val matchVal = panicOutput()) {
        null -> {}
        else -> {
            val out = matchVal
            Write.writeFmt(out, "fatal runtime error: " + ("the System allocator may not use TLS with destructors").toString() + ", aborting\n")
        }
    }
    run {
        "fatal runtime error: " + ("the System allocator may not use TLS with destructors").toString() + ", aborting\n"
    }
    abort()
} }
    enable()
    // Avoid calling the alloc error hook
    if (dtors.capacity() == dtors.len()) {
        dtors.tryReserve(1).unwrapOrElse({
            when (val matchVal1 = panicOutput()) {
                null -> {}
                else -> {
                    val out1 = matchVal1
                    Write.writeFmt(out1, "fatal runtime error: " + ("Failed to grow TLS destructor list").toString() + ", aborting\n")
                }
            }
            run {
                "fatal runtime error: " + ("Failed to grow TLS destructor list").toString() + ", aborting\n"
            }
            abort()
        })
    }
    dtors.push(Pair(t, dtor))
}

/**
 * The [guard] module contains platform-specific functions which will run this
 * function on thread exit if [guard.enable] has been called.
 *
 * # Safety
 *
 * May only be run on thread exit to guarantee that there are no live references
 * to TLS variables while they are destroyed.
 */
public fun run() {
    while (true) {
        DTORS.borrowMut().use { dtors ->
            when (val matchVal = dtors.pop()) {
                null -> {
                    // Free the list memory.
                    dtors = Vec.newIn(System())
                    break
                }
                else -> {
                    val t = matchVal.first
                    val dtor = matchVal.second
                    (dtors).let { }
                    run {
                        dtor(t)
                    }
                }
            }
        }
    }
}
