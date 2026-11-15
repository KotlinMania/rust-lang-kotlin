// port-lint: source libunwind.rs
package io.github.kotlinmania.rustlang.unwind.libunwind

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.types.UnwindExceptionClass
import io.github.kotlinmania.rustlang.types.UnwindReasonCode
import io.github.kotlinmania.rustlang.types.UnwindWord

public fun interface FnCallback {
    public operator fun invoke(version: CInt, actions: UnwindAction, exceptionClass: UnwindExceptionClass, exceptionObject: MutPtr, context: MutPtr): UnwindReasonCode
}

public typealias UnwindPtr = ConstPtr

public open class UnwindContext(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

// Note: The `@link` attributes on `extern "C"` block marks those symbols declared in
// the block are reexported in dylib build of std. This is needed when build rustc with
// feature `llvm - libunwind`, as no other cdylib will provided those _Unwind_* symbols.
// However the `link` attribute is duplicated multiple times and does not just export symbol,
// a better way to manually export symbol would be another attribute like `@export`.
// See the logic in function rustcCodegenSsa.src.back.exportedSymbols, module
// rustcCodegenSsa.src.back.symbolExport, rustcMiddle.middle.exportedSymbols
// and RFC 2841
public expect fun unwindResume(exception: MutPtr): Nothing

public expect fun unwindDeleteException(exception: MutPtr)

public expect fun unwindGetLanguageSpecificData(ctx: MutPtr): MutPtr

public expect fun unwindGetRegionStart(ctx: MutPtr): UnwindPtr

public expect fun unwindGetTextRelBase(ctx: MutPtr): UnwindPtr

public expect fun unwindGetDataRelBase(ctx: MutPtr): UnwindPtr

// Not ARM EHABI
//
// 32-bit ARM on iOS/tvOS/watchOS use either DWARF/Compact unwinding or
// "setjmp-longjmp" / SjLj unwinding.
public typealias UnwindAction = CInt

public val UA_SEARCH_PHASE: CInt = 1

// pub const _UA_CLEANUP_PHASE: cInt = 2;
// pub const _UA_HANDLER_FRAME: cInt = 4;
public val UA_FORCE_UNWIND: CInt = 8

// pub const _UA_END_OF_STACK: cInt = 16;
public expect fun unwindGetGr(ctx: MutPtr, regIndex: CInt): UnwindWord

public expect fun unwindSetGr(ctx: MutPtr, regIndex: CInt, value: UnwindWord)

public expect fun unwindSetIp(ctx: MutPtr, value: UnwindWord)

public expect fun unwindGetIpinfo(ctx: MutPtr, ipBeforeInsn: MutPtr): UnwindWord

// 32-bit ARM Apple (except for watchOS armv7k specifically) uses SjLj
public expect fun unwindRaiseException(exception: MutPtr): UnwindReasonCode

// We declare these as opaque types. This is fine since you just need to
// pass them to _GCC_specific_handler and forget about them.
public open class ExceptionRecord(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

public typealias Lpvoid = MutPtr

public open class Context(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

public open class DispatcherContext(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

public typealias ExceptionDisposition = CInt

public fun interface PersonalityFn {
    public operator fun invoke(version: CInt, actions: UnwindAction, exceptionClass: UnwindExceptionClass, exceptionObject: MutPtr, context: MutPtr): UnwindReasonCode
}

public expect fun gccSpecificHandler(exceptionRecord: MutPtr, establisherFrame: Lpvoid, contextRecord: MutPtr, dispatcherContext: MutPtr, personality: PersonalityFn): ExceptionDisposition
