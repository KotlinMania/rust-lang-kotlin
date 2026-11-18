// port-lint: source backtrace/libunwind.rs
package io.github.kotlinmania.rustlang.backtrace.backtrace.libunwind

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.addrOfMut
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.unix.UintptrT

// Backtrace support using libunwind/gccS/etc APIs.
//
// This module contains the ability to unwind the stack using libunwind-style
// APIs. Note that there's a whole bunch of implementations of the
// libunwind-like API, and this is just trying to be compatible with most of
// them all at once instead of being picky.
//
// The libunwind API is powered by `_Unwind_Backtrace` and is in practice very
// reliable at generating a backtrace. It's not entirely clear how it does it
// (frame pointers? ehFrame info? both?) but it seems to work!
//
// Most of the complexity of this module is handling the various platform
// differences across libunwind implementations. Otherwise this is a pretty
// straightforward Rust binding to the libunwind APIs.
//
// This is the default unwinding API for all non-Windows platforms currently.

public sealed class Frame {
    public class Raw internal constructor(valueCtorArg: MutPtr) : Frame() {
        public var value: MutPtr = valueCtorArg
            internal set

        public operator fun component1(): MutPtr = this.value

        public fun copy(value: MutPtr = this.value): Raw = Raw(valueCtorArg = value)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Raw) return false
            if (this.value != other.value) return false
            return true
        }

        override fun hashCode(): Int = this.value.hashCode()

        public companion object {
            public operator fun invoke(
                value: MutPtr,
            ): Raw = Raw(
                valueCtorArg = value,
            )
        }
    }
    public class Cloned internal constructor(ipCtorArg: MutPtr, spCtorArg: MutPtr, symbolAddressCtorArg: MutPtr) : Frame() {
        public var ip: MutPtr = ipCtorArg
            internal set
        public var sp: MutPtr = spCtorArg
            internal set
        public var symbolAddress: MutPtr = symbolAddressCtorArg
            internal set

        public operator fun component1(): MutPtr = this.ip

        public operator fun component2(): MutPtr = this.sp

        public operator fun component3(): MutPtr = this.symbolAddress

        public fun copy(ip: MutPtr = this.ip, sp: MutPtr = this.sp, symbolAddress: MutPtr = this.symbolAddress): Cloned = Cloned(ipCtorArg = ip, spCtorArg = sp, symbolAddressCtorArg = symbolAddress)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Cloned) return false
            if (this.ip != other.ip) return false
            if (this.sp != other.sp) return false
            if (this.symbolAddress != other.symbolAddress) return false
            return true
        }

        override fun hashCode(): Int {
            var result = this.ip.hashCode()
            result = 31 * result + this.sp.hashCode()
            result = 31 * result + this.symbolAddress.hashCode()
            return result
        }

        public companion object {
            public operator fun invoke(
                ip: MutPtr,
                sp: MutPtr,
                symbolAddress: MutPtr,
            ): Cloned = Cloned(
                ipCtorArg = ip,
                spCtorArg = sp,
                symbolAddressCtorArg = symbolAddress,
            )
        }
    }

    // With a raw libunwind pointer it should only ever be access in a readonly
    // threadsafe fashion, so it's `Sync`. When sending to other threads via `Clone`
    // we always switch to a version which doesn't retain interior pointers, so we
    // should be `Send` as well.

    public open fun ip(): MutPtr {
        val ctx1 =
            when (this) {
                is Frame.Raw -> {
                    val (ctx) = this as Frame.Raw
                    ctx
                }
                is Frame.Cloned -> {
                    val ip = (this as Frame.Cloned).ip
                    return ip
                }
            }
        val ip1 = MutPtr(unwindGetIp(ctx1).toLong())
        return ip1
    }

    public open fun sp(): MutPtr =
        when (this) {
            is Frame.Raw -> {
                val (ctx) = this as Frame.Raw
                MutPtr(getSp(ctx).toLong())
            }
            is Frame.Cloned -> {
                val sp = (this as Frame.Cloned).sp
                sp
            }
        }

    public open fun symbolAddress(): MutPtr {
        when (val matchVal = this) {
            is Frame.Cloned -> {
                val symbolAddress = matchVal.symbolAddress
                return symbolAddress
            }
            else -> {}
        }
        // The macOS linker emits a "compact" unwind table that only includes an
        // entry for a function if that function either has an LSDA or its
        // encoding differs from that of the previous entry.  Consequently, on
        // macOS, `_Unwind_FindEnclosingFunction` is unreliable (it can return a
        // pointer to some totally unrelated function).  Instead, we just always
        // return the ip.
        //
        // https://github.com/rust-lang/rust/issues/74771#issuecomment-664056788
        //
        // Note the `skipInnerFrames.rs` test is skipped on macOS due to this
        // clause, and if this is fixed that test in theory can be run on macOS!
        return if (false) {
            this.ip()
        } else {
            unwindFindEnclosingFunction(this.ip())
        }
    }

    public open fun moduleBaseAddress(): MutPtr? = null

    public open fun clone(): Frame = Frame.Cloned(ip = this.ip(), sp = this.sp(), symbolAddress = this.symbolAddress())
}

public open class Bomb(
    public var enabled: Boolean,
) {
    public open fun drop() {
        if (this.enabled) {
            error("cannot panic during the backtrace function")
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Bomb> =
            object : TypeLayout<Bomb> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): Bomb =
                    Bomb(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Bomb) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.enabled)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Bomb =
                    Bomb(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Bomb) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.enabled)
                }
            }
    }
}

public fun trace(cb: (io.github.kotlinmania.rustlang.backtrace.backtrace.Frame) -> Boolean) {
    fun traceFn(ctx: MutPtr, arg: MutPtr): UnwindReasonCode {
        val cb1 = RustMutableReference(MutPtr((MutPtr((arg).rawAddress)).rawAddress), UnresolvedTypeLayout<(io.github.kotlinmania.rustlang.backtrace.backtrace.Frame) -> Boolean>())
        val cx = io.github.kotlinmania.rustlang.backtrace.backtrace.Frame(inner = Frame.Raw(ctx))
        val bomb = Bomb(enabled = true)
        val keepGoing = cb1(cx)
        bomb.enabled = false
        return if (keepGoing) {
            URC_NO_REASON
        } else {
            URC_FAILURE
        }
    }
    run {
        unwindBacktrace(::traceFn, addrOfMut(cb).cast())
    }
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

    public fun toByte(): Byte = value.toByte()

    public fun toUByte(): UByte = value.toUByte()

    public fun toShort(): Short = value.toShort()

    public fun toUShort(): UShort = value.toUShort()

    public fun toInt(): Int = value

    public fun toUInt(): UInt = value.toUInt()

    public fun toLong(): Long = value.toLong()

    public fun toULong(): ULong = value.toULong()
}

public open class UnwindContext(
    public val handle: Long = -1L,
) {
    public val ptr: Long get() = handle
}

public fun interface UnwindTraceFn {
    public operator fun invoke(ctx: MutPtr, arg: MutPtr): UnwindReasonCode
}

public expect fun unwindBacktrace(trace: UnwindTraceFn, traceArgument: MutPtr): UnwindReasonCode

public expect fun unwindGetIp(ctx: MutPtr): UintptrT

public expect fun unwindFindEnclosingFunction(pc: MutPtr): MutPtr

// This function is a misnomer: rather than getting this frame's
// Canonical Frame Address (aka the caller frame's SP) it
// returns this frame's SP.
//
// https://github.com/libunwind/libunwind/blob/d32956507cf29d9b1a98a8bce53c78623908f4fe/src/unwind/GetCFA.c#L28-L35
public expect fun getSp(ctx: MutPtr): UintptrT

// s390x uses a biased CFA value, therefore we need to use
// _Unwind_GetGR to get the stack pointer register (%r15)
// instead of relying on _Unwind_GetCFA.
public fun getSp(ctx: MutPtr): UintptrT {
    fun unwindGetGr(ctx1: MutPtr, index: CInt): UintptrT = 0uL
    return unwindGetGr(ctx, 15)
}
