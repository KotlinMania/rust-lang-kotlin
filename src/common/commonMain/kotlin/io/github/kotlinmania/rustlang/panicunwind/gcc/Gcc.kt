// port-lint: source gcc.rs
package io.github.kotlinmania.rustlang.panicunwind.gcc

import io.github.kotlinmania.rustlang.alloc.panicking.PanicPayload
import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.eq
import io.github.kotlinmania.rustlang.panicunwind.rustDropPanic
import io.github.kotlinmania.rustlang.panicunwind.rustForeignException
import io.github.kotlinmania.rustlang.unwind.types.UnwindException
import io.github.kotlinmania.rustlang.unwind.types.UnwindExceptionClass
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode
import io.github.kotlinmania.rustlang.unwind.wasm.unwindDeleteException
import io.github.kotlinmania.rustlang.unwind.wasm.unwindRaiseException

// Implementation of panics backed by libgcc/libunwind (in some form).
//
// For background on exception handling and stack unwinding please see
// "Exception Handling in LLVM" (llvm.org/docs/ExceptionHandling.html) and
// documents linked from it.
// These are also good reads:
//  * <https://itanium-cxx-abi.github.io/cxx-abi/abi-eh.html>
//  * <https://nicolasbrailo.github.io/blog/projectsTexts/13exceptionsunderthehood.html>
//  * <https://www.airs.com/blog/index.php?s=exception+frames>
//
// ## A brief summary
//
// Exception handling happens in two phases: a search phase and a cleanup
// phase.
//
// In both phases the unwinder walks stack frames from top to bottom using
// information from the stack frame unwind sections of the current process's
// modules ("module" here refers to an OS module, i.e., an executable or a
// dynamic library).
//
// For each stack frame, it invokes the associated "personality routine", whose
// address is also stored in the unwind info section.
//
// In the search phase, the job of a personality routine is to examine
// exception object being thrown, and to decide whether it should be caught at
// that stack frame. Once the handler frame has been identified, cleanup phase
// begins.
//
// In the cleanup phase, the unwinder invokes each personality routine again.
// This time it decides which (if any) cleanup code needs to be run for
// the current stack frame. If so, the control is transferred to a special
// branch in the function body, the "landing pad", which invokes destructors,
// frees memory, etc. At the end of the landing pad, control is transferred
// back to the unwinder and unwinding resumes.
//
// Once stack has been unwound down to the handler frame level, unwinding stops
// and the last personality routine transfers control to the catch block.

// In case where multiple copies of std exist in a single process,
// we use address of this static variable to distinguish an exception raised by
// this copy and some other copy (which needs to be treated as foreign exception).
public var CANARY: UByte = 0u

// NOTE(nbdd0121)
// There is an ABI stability requirement on this struct.
// The first two field must be `_Unwind_Exception` and `canary`,
// as it may be accessed by a different version of the std with a different compiler.
public open class Exception internal constructor(
    public var uwe: UnwindException,
    canaryCtorArg: ConstPtr,
    public var cause: RustAny,
) {
    public var canary: ConstPtr = canaryCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            uwe: UnwindException,
            canary: ConstPtr,
            cause: RustAny,
        ): Exception = Exception(
            uwe = uwe,
            canaryCtorArg = canary,
            cause = cause,
        )
    }
}

public fun panic(data: PanicPayload): UInt {
    fun exceptionCleanup(unwindCode: UnwindReasonCode, exception: MutPtr) {
        rustDropPanic()
    }
    val exception = Exception(uwe = UnwindException(exceptionClass = RUST_EXCEPTION_CLASS, exceptionCleanup = ::exceptionCleanup, private = List(_) { ConstPtr(0L) }), canary = CANARY, cause = data.takeBox())
    val exceptionParam = mutPtrFromRustCast(Box.intoRaw(exception))
    return unwindRaiseException(exceptionParam).toUInt()
}

public fun cleanup(ptr: MutPtr): RustAny =
    run {
        val exception = MutPtr(ptr.rawAddress, ptr.rawMeta)
        if ((UnresolvedTypeLayout<UnwindException>()).readNative((exception).rawAddress).exceptionClass != RUST_EXCEPTION_CLASS) {
            unwindDeleteException(exception)
            rustForeignException()
        }
        val exception1 = MutPtr((exception).rawAddress)
        // Just access the canary field, avoid accessing the entire `Exception` as
        // it can be a foreign Rust exception.
        val canary = (addrOf(exception1.canary)).read()
        if (!eq(canary, CANARY)) {
            // A foreign Rust exception, treat it slightly differently from other
            // foreign exceptions, because call into `_Unwind_DeleteException` will
            // call into `__rust_drop_panic` which produces a confusing
            // "Rust panic must be rethrown" message.
            rustForeignException()
        }
        val exception2 = Box.fromRaw(mutPtrFromRustCast(exception1))
        exception2.cause
    }

// Rust's exception class identifier.  This is used by personality routines to
// determine whether the exception was thrown by their own runtime.
public val RUST_EXCEPTION_CLASS: UnwindExceptionClass = run { val bytesTmp0 = "MOZ\u0000RUST".encodeToByteArray().toUByteArray(); ((bytesTmp0[7].toULong() shl 56) or (bytesTmp0[6].toULong() shl 48) or (bytesTmp0[5].toULong() shl 40) or (bytesTmp0[4].toULong() shl 32) or (bytesTmp0[3].toULong() shl 24) or (bytesTmp0[2].toULong() shl 16) or (bytesTmp0[1].toULong() shl 8) or bytesTmp0[0].toULong()) }
