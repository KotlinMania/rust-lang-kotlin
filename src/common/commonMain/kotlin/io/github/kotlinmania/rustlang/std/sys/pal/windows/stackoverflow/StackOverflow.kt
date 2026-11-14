// port-lint: source sys/pal/windows/stack_overflow.rs
package io.github.kotlinmania.rustlang.std.sys.pal.windows.stackoverflow

import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.std.sys.c
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.thread.current.currentOsId
import io.github.kotlinmania.rustlang.std.thread.current.withCurrentName

/**
 * Reserve stack space for use in stack overflow exceptions.
 */
public fun reserveStack() {
    val result = c.SetThreadStackGuarantee(RustMutableReference(allocateNativeCell(0x5000, PrimitiveLayouts.I32), PrimitiveLayouts.I32))
    // Reserving stack space is not critical so we allow it to fail in the released build of libstd.
    // We still use debug assert here so that CI will test that we haven't made a mistake calling the function.
    check(result != 0)
}

public fun vectoredHandler(exceptionInfo: MutPtr): Int {
    // SAFETY: It's up to the caller (which in this case is the OS) to ensure that `ExceptionInfo` is valid.
    return run {
        val rec = (ObjectSlotLayout<c.ExceptionPointers>()).readNative((exceptionInfo).rawAddress).exceptionRecord
        val code = rec.exceptionCode
        if (code == c.EXCEPTION_STACK_OVERFLOW.castUnsigned()) {
            withCurrentName({ name ->
                val name1 = name.unwrapOr("<unknown>")
                val tid = currentOsId()
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "\nthread '$name' ($tid) has overflowed its stack\n")
                    }
                }
                run {
                    "\nthread '$name' ($tid) has overflowed its stack\n"
                }
            })
        }
        c.EXCEPTION_CONTINUE_SEARCH
    }
}

public fun init() {
    // SAFETY: `vectoredHandler` has the correct ABI and is safe to call during exception handling.
    run {
        val result = c.AddVectoredExceptionHandler(0, ::vectoredHandler)
        // Similar to the above, adding the stack overflow handler is allowed to fail
        // but a debug assert is used so CI will still test that it normally works.
        check(!((result).rawAddress == 0L)) { "failed to install exception handler" }
    }
    // Set the thread stack guarantee for the main thread.
    reserveStack()
}
