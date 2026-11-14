// port-lint: source types.rs
package io.github.kotlinmania.rustlang.unwind.types

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

public fun interface FnCallback {
    public operator fun invoke(unwindCode: UnwindReasonCode, exception: MutPtr)
}

public sealed class UnwindReasonCode(
    public val value: Int,
) {
    public data object UrcNoReason : UnwindReasonCode(0)
    public data object UrcForeignExceptionCaught : UnwindReasonCode(1)
    public data object UrcFatalPhase2Error : UnwindReasonCode(2)
    public data object UrcFatalPhase1Error : UnwindReasonCode(3)
    public data object UrcNormalStop : UnwindReasonCode(4)
    public data object UrcEndOfStack : UnwindReasonCode(5)
    public data object UrcHandlerFound : UnwindReasonCode(6)
    public data object UrcInstallContext : UnwindReasonCode(7)
    public data object UrcContinueUnwind : UnwindReasonCode(8)
    public data object UrcFailure : UnwindReasonCode(9)

    public fun rustEquals(other: UnwindReasonCode): Boolean = this == other
}

public typealias UnwindExceptionClass = ULong

public typealias UnwindWord = ConstPtr

public val UNWINDER_PRIVATE_DATA_SIZE: Int = cfgSelect(targetArch, "x86", 5, all, targetArch, "x86_64", not, any, targetOs, "windows", targetOs, "cygwin", 2, all, targetArch, "x86_64", any, targetOs, "windows", targetOs, "cygwin", 6, all, targetArch, "arm", not, targetVendor, "apple", 20, all, targetArch, "arm", targetVendor, "apple", 5, all, targetArch, "aarch64", targetPointerWidth, "64", not, targetOs, "windows", 2, all, targetArch, "aarch64", targetPointerWidth, "64", targetOs, "windows", 6, all, targetArch, "aarch64", targetPointerWidth, "32", 5, targetArch, "m68k", 2, any, targetArch, "mips", targetArch, "mips32r6", 2, targetArch, "csky", 2, any, targetArch, "mips64", targetArch, "mips64r6", 2, any, targetArch, "powerpc", targetArch, "powerpc64", 2, targetArch, "s390x", 2, any, targetArch, "sparc", targetArch, "sparc64", 2, any, targetArch, "riscv64", targetArch, "riscv32", 2, targetFamily, "wasm", 2, targetArch, "hexagon", 5, any, targetArch, "loongarch32", targetArch, "loongarch64", 2)

// The Itanium C++ ABI requires this type to have "double-word" alignment,
// which libunwind and libgcc interpret as the maximum alignment of any
// scalar type on the current target.
public open class UnwindException internal constructor(
    public var exceptionClass: UnwindExceptionClass,
    public var exceptionCleanup: UnwindExceptionCleanupFn,
    privateCtorArg: List<UnwindWord>,
) {
    public var private: List<UnwindWord> = privateCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            exceptionClass: UnwindExceptionClass,
            exceptionCleanup: UnwindExceptionCleanupFn,
            private: List<UnwindWord>,
        ): UnwindException = UnwindException(
            exceptionClass = exceptionClass,
            exceptionCleanup = exceptionCleanup,
            privateCtorArg = private,
        )
    }
}

public fun interface UnwindExceptionCleanupFnFn {
    public operator fun invoke(unwindCode: UnwindReasonCode, exception: MutPtr)
}

public typealias UnwindExceptionCleanupFn = UnwindExceptionCleanupFnFn?
