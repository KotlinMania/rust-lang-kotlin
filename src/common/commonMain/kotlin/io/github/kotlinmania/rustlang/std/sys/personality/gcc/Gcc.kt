// port-lint: source sys/personality/gcc.rs
package io.github.kotlinmania.rustlang.std.sys.personality.gcc

import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.wrappingSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.sys.personality.dwarf.eh.EHAction
import io.github.kotlinmania.rustlang.std.sys.personality.dwarf.eh.EHContext
import io.github.kotlinmania.rustlang.unwind.URC_CONTINUE_UNWIND
import io.github.kotlinmania.rustlang.unwind.URC_FATAL_PHASE1_ERROR
import io.github.kotlinmania.rustlang.unwind.URC_FATAL_PHASE2_ERROR
import io.github.kotlinmania.rustlang.unwind.URC_HANDLER_FOUND
import io.github.kotlinmania.rustlang.unwind.URC_INSTALL_CONTEXT
import io.github.kotlinmania.rustlang.unwind.gccSpecificHandler
import io.github.kotlinmania.rustlang.unwind.libunwind.ExceptionDisposition
import io.github.kotlinmania.rustlang.unwind.libunwind.Lpvoid
import io.github.kotlinmania.rustlang.unwind.libunwind.UA_FORCE_UNWIND
import io.github.kotlinmania.rustlang.unwind.libunwind.UA_SEARCH_PHASE
import io.github.kotlinmania.rustlang.unwind.libunwind.UnwindAction
import io.github.kotlinmania.rustlang.unwind.types.UnwindExceptionClass
import io.github.kotlinmania.rustlang.unwind.types.UnwindReasonCode
import io.github.kotlinmania.rustlang.unwind.unwindGetDataRelBase
import io.github.kotlinmania.rustlang.unwind.unwindGetIpinfo
import io.github.kotlinmania.rustlang.unwind.unwindGetLanguageSpecificData
import io.github.kotlinmania.rustlang.unwind.unwindGetRegionStart
import io.github.kotlinmania.rustlang.unwind.unwindGetTextRelBase
import io.github.kotlinmania.rustlang.unwind.unwindSetGr
import io.github.kotlinmania.rustlang.unwind.unwindSetIp

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

// EAX, EDX
public val UNWIND_DATA_REG: Pair<Int, Int> = Pair(0, 1)

// RAX, RDX
public val UNWIND_DATA_REG1: Pair<Int, Int> = Pair(0, 1)

// D0, D1
public val UNWIND_DATA_REG2: Pair<Int, Int> = Pair(4, 5)

// R0, R1
public val UNWIND_DATA_REG3: Pair<Int, Int> = Pair(3, 4)

// R3, R4 / X3, X4
public val UNWIND_DATA_REG4: Pair<Int, Int> = Pair(6, 7)

// R6, R7
public val UNWIND_DATA_REG5: Pair<Int, Int> = Pair(24, 25)

// R0, R1
public val UNWIND_DATA_REG6: Pair<Int, Int> = Pair(10, 11)

// x10, x11
public val UNWIND_DATA_REG7: Pair<Int, Int> = Pair(4, 5)

// a0, a1
// The following code is based on GCC's C and C++ personality routines.  For reference, see:
// https://github.com/gcc-mirror/gcc/blob/master/libstdc++-v3/libsupc++/ehPersonality.cc
// https://github.com/gcc-mirror/gcc/blob/trunk/libgcc/unwind-c.c
public fun signLpad(context: MutPtr, lpad: ConstPtr): ConstPtr {
    // DWARF register number for SP on AArch64.
    return run {
        val addr = (lpad).rawAddress.toInt()
        // `pacib` corresponds to `ptrauthKeyProcessDependentCode` in <ptrauth.h>.
        Unit
        ConstPtr((addr).toLong())
    }
}

/**
 * Default personality routine, which is used directly on most targets
 * and indirectly on Windows x8664 and AArch64 via SEH.
 */
public fun rustEhPersonalityImpl(version: CInt, actions: UnwindAction, exceptionClass: UnwindExceptionClass, exceptionObject: MutPtr, context: MutPtr): UnwindReasonCode {
    return run {
        if (version != 1) {
            return URC_FATAL_PHASE1_ERROR
        }
        val ehAction =
            run {
                val matchVal = findEhAction(context)
                when (matchVal) { is Result.Ok<*, *> -> {
                    val action = matchVal.okValue()
                    action
                }; is Result.Err<*, *> -> {
                    return URC_FATAL_PHASE1_ERROR
                } }
            }
        if (actions and UA_SEARCH_PHASE != 0) {
            when (ehAction) {
                EHAction.None -> URC_CONTINUE_UNWIND
                is EHAction.Cleanup -> URC_CONTINUE_UNWIND
                is EHAction.Catch -> URC_HANDLER_FOUND
                is EHAction.Filter -> URC_HANDLER_FOUND
                EHAction.Terminate -> URC_FATAL_PHASE1_ERROR
            }
        } else {
            when {
                ehAction == EHAction.None -> URC_CONTINUE_UNWIND
                // Forced unwinding hits a terminate action.
                ehAction is EHAction.Filter && actions and UA_FORCE_UNWIND != 0 -> {
                    URC_CONTINUE_UNWIND
                }
                ehAction is EHAction.Cleanup -> {
                    val (lpad) = ehAction
                    unwindSetGr(context, UNWIND_DATA_REG.first, exceptionObject)
                    unwindSetGr(context, UNWIND_DATA_REG.second, ConstPtr(0L))
                    val maybeSignedLpad = signLpad(context, lpad)
                    unwindSetIp(context, maybeSignedLpad)
                    URC_INSTALL_CONTEXT
                }
                ehAction is EHAction.Catch -> {
                    val (lpad1) = ehAction
                    unwindSetGr(context, UNWIND_DATA_REG.first, exceptionObject)
                    unwindSetGr(context, UNWIND_DATA_REG.second, ConstPtr(0L))
                    val maybeSignedLpad1 = signLpad(context, lpad1)
                    unwindSetIp(context, maybeSignedLpad1)
                    URC_INSTALL_CONTEXT
                }
                ehAction is EHAction.Filter -> {
                    val (lpad2) = ehAction
                    unwindSetGr(context, UNWIND_DATA_REG.first, exceptionObject)
                    unwindSetGr(context, UNWIND_DATA_REG.second, ConstPtr(0L))
                    val maybeSignedLpad2 = signLpad(context, lpad2)
                    unwindSetIp(context, maybeSignedLpad2)
                    URC_INSTALL_CONTEXT
                }
                else -> URC_FATAL_PHASE2_ERROR
            }
        }
    }
}

/**
 * personality fn called by [Windows Structured Exception Handling][windows-eh]
 *
 * On x8664 and AArch64 MinGW targets, the unwinding mechanism is SEH,
 * however the unwind handler data (aka LSDA) uses GCC-compatible encoding
 *
 * [windows-eh]: https://learn.microsoft.com/en-us/cpp/cpp/structured-exception-handling-c-cpp?view=msvc-170
 */
public fun rustEhPersonality(exceptionRecord: MutPtr, establisherFrame: Lpvoid, contextRecord: MutPtr, dispatcherContext: MutPtr): ExceptionDisposition {
    // SAFETY: the cfg is still targetOs = "windows" and targetEnv = "gnu",
    // which means that this is the correct function to call, passing our impl fn
    // as the callback which gets actually used
    return gccSpecificHandler(exceptionRecord, establisherFrame, contextRecord, dispatcherContext, ::rustEhPersonalityImpl)
}

public fun findEhAction(context: MutPtr): Result<EHAction, Unit> =
    run {
        val lsda = constPtrFromRustCast(unwindGetLanguageSpecificData(context))
        val ipBeforeInstr: CInt  = 0
        val ip = unwindGetIpinfo(context, ipBeforeInstr)
        val ehContext =
            EHContext(ip = if (ipBeforeInstr != 0) {
                ip
            } else {
                ip.wrappingSub(1)
            }, funcStart = unwindGetRegionStart(context), getTextStart = { unwindGetTextRelBase(context) }, getDataStart = { unwindGetDataRelBase(context) })
        io.github.kotlinmania.rustlang.std.sys.personality.dwarf.eh.findEhAction(lsda, ehContext)
    }
