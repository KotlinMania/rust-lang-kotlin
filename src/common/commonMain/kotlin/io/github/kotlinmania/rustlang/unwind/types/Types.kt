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

    public fun toByte(): Byte = value.toByte()

    public fun toUByte(): UByte = value.toUByte()

    public fun toShort(): Short = value.toShort()

    public fun toUShort(): UShort = value.toUShort()

    public fun toInt(): Int = value

    public fun toUInt(): UInt = value.toUInt()

    public fun toLong(): Long = value.toLong()

    public fun toULong(): ULong = value.toULong()
}

public typealias UnwindExceptionClass = ULong

public typealias UnwindWord = ConstPtr

public val UNWINDER_PRIVATE_DATA_SIZE: Int = 2

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
