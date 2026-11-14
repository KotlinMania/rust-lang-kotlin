@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CShort
import io.github.kotlinmania.rustlang.core.ffi.CUchar
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ffi.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.CUshort
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.CcT
import io.github.kotlinmania.rustlang.libc.In6Addr
import io.github.kotlinmania.rustlang.libc.Msghdr
import io.github.kotlinmania.rustlang.libc.PidT
import io.github.kotlinmania.rustlang.libc.SaFamilyT
import io.github.kotlinmania.rustlang.libc.Sigval
import io.github.kotlinmania.rustlang.libc.Sockaddr
import io.github.kotlinmania.rustlang.libc.SpeedT
import io.github.kotlinmania.rustlang.libc.TcflagT
import io.github.kotlinmania.rustlang.libc.Timespec
import io.github.kotlinmania.rustlang.libc.Timeval
import io.github.kotlinmania.rustlang.libc.UidT
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U8
import io.github.kotlinmania.rustlang.libc.unix.IFNAMSIZ
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.UT_HOSTSIZE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.UT_LINESIZE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.UT_NAMESIZE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Be16

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

// Most `* _SUPER_MAGIC` constants are defined at the `linuxLike` level; the
// following are only available on newer Linux versions than the versions
// currently used in CI in some configurations, so we define them here.

/**
 * This is an extern type ("opaque" or "incomplete" type in C).
 *
 * <div class="warning">
 * This type's current representation allows inspecting some properties, such as via
 * <code>sizeOf</code>, and it is technically possible to construct the type within
 * <code>MaybeUninit</code>, However, this <strong>MUST NOT</strong> be relied upon
 * because a future version of <code>libc</code> may switch to a proper
 * <a href="https://rust-lang.github.io/rfcs/1861-extern-types.html">extern type</a>
 * representation when available.
 * </div>
 */
public data class PropInfo(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

public class StackT internal constructor(
    ssSpCtorArg: MutPtr,
    public var ssFlags: CInt,
    public var ssSize: SizeT,
) {
    public var ssSp: MutPtr = ssSpCtorArg
        internal set

    public constructor() : this(
        ssSpCtorArg = MutPtr(0L),
        ssFlags = 0,
        ssSize = 0uL,
    )

    public operator fun component1(): MutPtr = this.ssSp

    public operator fun component2(): CInt = this.ssFlags

    public operator fun component3(): SizeT = this.ssSize

    public fun copy(ssSp: MutPtr = this.ssSp, ssFlags: CInt = this.ssFlags, ssSize: SizeT = this.ssSize): StackT = StackT(ssSpCtorArg = ssSp, ssFlags = ssFlags, ssSize = ssSize)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StackT) return false
        if (this.ssSp != other.ssSp) return false
        if (this.ssFlags != other.ssFlags) return false
        if (this.ssSize != other.ssSize) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ssSp.hashCode()
        result = 31 * result + this.ssFlags.hashCode()
        result = 31 * result + this.ssSize.hashCode()
        return result
    }

    public fun clone(): StackT = StackT(ssSp, ssFlags, ssSize)

    public companion object {
        public fun typeLayout(): TypeLayout<StackT> =
            object : TypeLayout<StackT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): StackT =
                    StackT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: StackT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.ssSp)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.ssFlags)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.ssSize)
                }
                override fun readBytes(buf: ByteArray, offset: Int): StackT =
                    StackT(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: StackT) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.ssSp)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.ssFlags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.ssSize)
                }
            }

        public operator fun invoke(
            ssSp: MutPtr = MutPtr(0L),
            ssFlags: CInt = 0,
            ssSize: SizeT = 0uL,
        ): StackT = StackT(
            ssSpCtorArg = ssSp,
            ssFlags = ssFlags,
            ssSize = ssSize,
        )
    }
}

public data class FsidT(
    public var value: IntArray = IntArray(2),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FsidT) return false
        if (!this.value.contentEquals(other.value)) return false
        return true
    }

    override fun hashCode(): Int = this.value.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<FsidT> =
            object : TypeLayout<FsidT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FsidT =
                    FsidT(
                        (PrimitiveArrayLayouts.intArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FsidT) {
                    (PrimitiveArrayLayouts.intArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FsidT =
                    FsidT(
                        (PrimitiveArrayLayouts.intArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FsidT) {
                    (PrimitiveArrayLayouts.intArray(2)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public data class Termios(
    public var cIflag: TcflagT = 0uL,
    public var cOflag: TcflagT = 0uL,
    public var cCflag: TcflagT = 0uL,
    public var cLflag: TcflagT = 0uL,
    public var cLine: CcT = 0u,
    public var cCc: UByteArray = UByteArray(NCCS),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Termios) return false
        if (this.cIflag != other.cIflag) return false
        if (this.cOflag != other.cOflag) return false
        if (this.cCflag != other.cCflag) return false
        if (this.cLflag != other.cLflag) return false
        if (this.cLine != other.cLine) return false
        if (!this.cCc.contentEquals(other.cCc)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cIflag.hashCode()
        result = 31 * result + this.cOflag.hashCode()
        result = 31 * result + this.cCflag.hashCode()
        result = 31 * result + this.cLflag.hashCode()
        result = 31 * result + this.cLine.hashCode()
        result = 31 * result + this.cCc.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Termios> =
            object : TypeLayout<Termios> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((33L + ((NCCS).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): Termios =
                    Termios(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U8).readNative(addr + 32L),
                        (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).readNative(addr + 33L),
                    )
                override fun writeNative(addr: Long, value: Termios) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.cIflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.cOflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.cCflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.cLflag)
                    (PrimitiveLayouts.U8).writeNative(addr + 32L, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).writeNative(addr + 33L, value.cCc)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Termios =
                    Termios(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 32),
                        (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).readBytes(buf, offset + 33),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Termios) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.cIflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.cOflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.cCflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.cLflag)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 32, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).writeBytes(buf, offset + 33, value.cCc)
                }
            }
    }
}

public data class Termios2(
    public var cIflag: TcflagT = 0uL,
    public var cOflag: TcflagT = 0uL,
    public var cCflag: TcflagT = 0uL,
    public var cLflag: TcflagT = 0uL,
    public var cLine: CcT = 0u,
    public var cCc: UByteArray = UByteArray(19),
    public var cIspeed: SpeedT = 0uL,
    public var cOspeed: SpeedT = 0uL,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Termios2) return false
        if (this.cIflag != other.cIflag) return false
        if (this.cOflag != other.cOflag) return false
        if (this.cCflag != other.cCflag) return false
        if (this.cLflag != other.cLflag) return false
        if (this.cLine != other.cLine) return false
        if (!this.cCc.contentEquals(other.cCc)) return false
        if (this.cIspeed != other.cIspeed) return false
        if (this.cOspeed != other.cOspeed) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.cIflag.hashCode()
        result = 31 * result + this.cOflag.hashCode()
        result = 31 * result + this.cCflag.hashCode()
        result = 31 * result + this.cLflag.hashCode()
        result = 31 * result + this.cLine.hashCode()
        result = 31 * result + this.cCc.contentHashCode()
        result = 31 * result + this.cIspeed.hashCode()
        result = 31 * result + this.cOspeed.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Termios2> =
            object : TypeLayout<Termios2> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 72L
                override fun readNative(addr: Long): Termios2 =
                    Termios2(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U8).readNative(addr + 32L),
                        (PrimitiveArrayLayouts.uByteArray(19)).readNative(addr + 33L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                    )
                override fun writeNative(addr: Long, value: Termios2) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.cIflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.cOflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.cCflag)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.cLflag)
                    (PrimitiveLayouts.U8).writeNative(addr + 32L, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray(19)).writeNative(addr + 33L, value.cCc)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.cIspeed)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.cOspeed)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Termios2 =
                    Termios2(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 32),
                        (PrimitiveArrayLayouts.uByteArray(19)).readBytes(buf, offset + 33),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Termios2) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.cIflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.cOflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.cCflag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.cLflag)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 32, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray(19)).writeBytes(buf, offset + 33, value.cCc)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.cIspeed)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.cOspeed)
                }
            }
    }
}

public data class Mallinfo(
    public var arena: SizeT = 0uL,
    public var ordblks: SizeT = 0uL,
    public var smblks: SizeT = 0uL,
    public var hblks: SizeT = 0uL,
    public var hblkhd: SizeT = 0uL,
    public var usmblks: SizeT = 0uL,
    public var fsmblks: SizeT = 0uL,
    public var uordblks: SizeT = 0uL,
    public var fordblks: SizeT = 0uL,
    public var keepcost: SizeT = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Mallinfo> =
            object : TypeLayout<Mallinfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 80L
                override fun readNative(addr: Long): Mallinfo =
                    Mallinfo(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                    )
                override fun writeNative(addr: Long, value: Mallinfo) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.arena)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.ordblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.smblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.hblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.hblkhd)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.usmblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.fsmblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.uordblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.fordblks)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.keepcost)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Mallinfo =
                    Mallinfo(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Mallinfo) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.arena)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.ordblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.smblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.hblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.hblkhd)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.usmblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.fsmblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.uordblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.fordblks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.keepcost)
                }
            }
    }
}

public data class Flock(
    public var lType: CShort = 0,
    public var lWhence: CShort = 0,
    public var lStart: OffT = 0L,
    public var lLen: OffT = 0L,
    public var lPid: PidT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Flock> =
            object : TypeLayout<Flock> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Flock =
                    Flock(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Flock) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.lType)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.lWhence)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.lStart)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.lLen)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.lPid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Flock =
                    Flock(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Flock) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.lType)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.lWhence)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.lStart)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.lLen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.lPid)
                }
            }
    }
}

// Note(1.0,deprecate): lfs binding to be removed
public data class Flock64(
    public var lType: CShort = 0,
    public var lWhence: CShort = 0,
    public var lStart: KernelLoffT = 0L,
    public var lLen: KernelLoffT = 0L,
    public var lPid: KernelPidT = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Flock64> =
            object : TypeLayout<Flock64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Flock64 =
                    Flock64(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Flock64) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.lType)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.lWhence)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.lStart)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.lLen)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.lPid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Flock64 =
                    Flock64(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Flock64) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.lType)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.lWhence)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.lStart)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.lLen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.lPid)
                }
            }
    }
}

public data class CpuSetT(
    public var bits: ULongArray = ULongArray(16),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CpuSetT) return false
        if (!this.bits.contentEquals(other.bits)) return false
        return true
    }

    override fun hashCode(): Int = this.bits.contentHashCode()

    public companion object {
        public fun typeLayout(): TypeLayout<CpuSetT> =
            object : TypeLayout<CpuSetT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): CpuSetT =
                    CpuSetT(
                        (PrimitiveArrayLayouts.uLongArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: CpuSetT) {
                    (PrimitiveArrayLayouts.uLongArray(16)).writeNative(addr, value.bits)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CpuSetT =
                    CpuSetT(
                        (PrimitiveArrayLayouts.uLongArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CpuSetT) {
                    (PrimitiveArrayLayouts.uLongArray(16)).writeBytes(buf, offset, value.bits)
                }
            }
    }
}

public data class SemT(
    public var count: CUint = 0u,
    public var reserved: IntArray = IntArray(3),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SemT) return false
        if (this.count != other.count) return false
        if (!this.reserved.contentEquals(other.reserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.count.hashCode()
        result = 31 * result + this.reserved.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SemT> =
            object : TypeLayout<SemT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SemT =
                    SemT(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveArrayLayouts.intArray(3)).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: SemT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.count)
                    (PrimitiveArrayLayouts.intArray(3)).writeNative(addr + 4L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SemT =
                    SemT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.intArray(3)).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SemT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.count)
                    (PrimitiveArrayLayouts.intArray(3)).writeBytes(buf, offset + 4, value.reserved)
                }
            }
    }
}

public data class ExitStatus(
    public var eTermination: CShort = 0,
    public var eExit: CShort = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<ExitStatus> =
            object : TypeLayout<ExitStatus> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ExitStatus =
                    ExitStatus(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: ExitStatus) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.eTermination)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.eExit)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExitStatus =
                    ExitStatus(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExitStatus) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.eTermination)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.eExit)
                }
            }
    }
}

public data class Statvfs(
    public var fBsize: CUlong = 0uL,
    public var fFrsize: CUlong = 0uL,
    public var fBlocks: io.github.kotlinmania.rustlang.libc.FsblkcntT = 0u,
    public var fBfree: io.github.kotlinmania.rustlang.libc.FsblkcntT = 0u,
    public var fBavail: io.github.kotlinmania.rustlang.libc.FsblkcntT = 0u,
    public var fFiles: io.github.kotlinmania.rustlang.libc.FsfilcntT = 0u,
    public var fFfree: io.github.kotlinmania.rustlang.libc.FsfilcntT = 0u,
    public var fFavail: io.github.kotlinmania.rustlang.libc.FsfilcntT = 0u,
    public var fFsid: CUlong = 0uL,
    public var fFlag: CUlong = 0uL,
    public var fNamemax: CUlong = 0uL,
    public var fReserved: UIntArray = UIntArray(6),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statvfs) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fFavail != other.fFavail) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fFlag != other.fFlag) return false
        if (this.fNamemax != other.fNamemax) return false
        if (!this.fReserved.contentEquals(other.fReserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fBsize.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fFavail.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fFlag.hashCode()
        result = 31 * result + this.fNamemax.hashCode()
        result = 31 * result + this.fReserved.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Statvfs> =
            object : TypeLayout<Statvfs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Statvfs =
                    Statvfs(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U64).readNative(addr + 80L),
                        (PrimitiveArrayLayouts.uIntArray(6)).readNative(addr + 88L),
                    )
                override fun writeNative(addr: Long, value: Statvfs) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.fBsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.fFrsize)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.fBlocks)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.fBfree)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.fBavail)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.fFiles)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.fFfree)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.fFavail)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.fFsid)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.fFlag)
                    (PrimitiveLayouts.U64).writeNative(addr + 80L, value.fNamemax)
                    (PrimitiveArrayLayouts.uIntArray(6)).writeNative(addr + 88L, value.fReserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Statvfs =
                    Statvfs(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 80),
                        (PrimitiveArrayLayouts.uIntArray(6)).readBytes(buf, offset + 88),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Statvfs) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.fBsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.fFrsize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.fBlocks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.fBfree)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.fBavail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.fFiles)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.fFfree)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.fFavail)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.fFsid)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.fFlag)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 80, value.fNamemax)
                    (PrimitiveArrayLayouts.uIntArray(6)).writeBytes(buf, offset + 88, value.fReserved)
                }
            }
    }
}

public class SignalfdSiginfo(
    public var ssiSigno: UInt = 0u,
    public var ssiErrno: Int = 0,
    public var ssiCode: Int = 0,
    public var ssiPid: UInt = 0u,
    public var ssiUid: UInt = 0u,
    public var ssiFd: Int = 0,
    public var ssiTid: UInt = 0u,
    public var ssiBand: UInt = 0u,
    public var ssiOverrun: UInt = 0u,
    public var ssiTrapno: UInt = 0u,
    public var ssiStatus: Int = 0,
    public var ssiInt: Int = 0,
    public var ssiPtr: CUlonglong = 0uL,
    public var ssiUtime: CUlonglong = 0uL,
    public var ssiStime: CUlonglong = 0uL,
    public var ssiAddr: CUlonglong = 0uL,
    public var ssiAddrLsb: UShort = 0u,
    public var pad2: UShort = 0u,
    public var ssiSyscall: Int = 0,
    public var ssiCallAddr: ULong = 0uL,
    public var ssiArch: UInt = 0u,
    public var pad: UByteArray = UByteArray(28),
) {
    public fun clone(): SignalfdSiginfo = SignalfdSiginfo(ssiSigno, ssiErrno, ssiCode, ssiPid, ssiUid, ssiFd, ssiTid, ssiBand, ssiOverrun, ssiTrapno, ssiStatus, ssiInt, ssiPtr, ssiUtime, ssiStime, ssiAddr, ssiAddrLsb, pad2, ssiSyscall, ssiCallAddr, ssiArch, pad)

    public companion object {
        public fun typeLayout(): TypeLayout<SignalfdSiginfo> =
            object : TypeLayout<SignalfdSiginfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): SignalfdSiginfo =
                    SignalfdSiginfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.I32).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 44L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U16).readNative(addr + 80L),
                        (PrimitiveLayouts.U16).readNative(addr + 82L),
                        (PrimitiveLayouts.I32).readNative(addr + 84L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U32).readNative(addr + 96L),
                        (PrimitiveArrayLayouts.uByteArray(28)).readNative(addr + 100L),
                    )
                override fun writeNative(addr: Long, value: SignalfdSiginfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.ssiSigno)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.ssiErrno)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.ssiCode)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.ssiPid)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.ssiUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.ssiFd)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.ssiTid)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.ssiBand)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.ssiOverrun)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.ssiTrapno)
                    (PrimitiveLayouts.I32).writeNative(addr + 40L, value.ssiStatus)
                    (PrimitiveLayouts.I32).writeNative(addr + 44L, value.ssiInt)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.ssiPtr)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.ssiUtime)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.ssiStime)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.ssiAddr)
                    (PrimitiveLayouts.U16).writeNative(addr + 80L, value.ssiAddrLsb)
                    (PrimitiveLayouts.U16).writeNative(addr + 82L, value.pad2)
                    (PrimitiveLayouts.I32).writeNative(addr + 84L, value.ssiSyscall)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.ssiCallAddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 96L, value.ssiArch)
                    (PrimitiveArrayLayouts.uByteArray(28)).writeNative(addr + 100L, value.pad)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SignalfdSiginfo =
                    SignalfdSiginfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 82),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 96),
                        (PrimitiveArrayLayouts.uByteArray(28)).readBytes(buf, offset + 100),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SignalfdSiginfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.ssiSigno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.ssiErrno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.ssiCode)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.ssiPid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.ssiUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.ssiFd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.ssiTid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.ssiBand)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.ssiOverrun)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.ssiTrapno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 40, value.ssiStatus)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 44, value.ssiInt)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.ssiPtr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.ssiUtime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.ssiStime)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.ssiAddr)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 80, value.ssiAddrLsb)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 82, value.pad2)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 84, value.ssiSyscall)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.ssiCallAddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 96, value.ssiArch)
                    (PrimitiveArrayLayouts.uByteArray(28)).writeBytes(buf, offset + 100, value.pad)
                }
            }
    }
}

public data class Itimerspec(
    public var itInterval: Timespec = Timespec(),
    public var itValue: Timespec = Timespec(),
)

public data class Genlmsghdr(
    public var cmd: UByte = 0u,
    public var version: UByte = 0u,
    public var reserved: UShort = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Genlmsghdr> =
            object : TypeLayout<Genlmsghdr> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Genlmsghdr =
                    Genlmsghdr(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Genlmsghdr) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.cmd)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.version)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Genlmsghdr =
                    Genlmsghdr(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Genlmsghdr) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.cmd)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.version)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.reserved)
                }
            }
    }
}

public data class Nlmsghdr(
    public var nlmsgLen: UInt = 0u,
    public var nlmsgType: UShort = 0u,
    public var nlmsgFlags: UShort = 0u,
    public var nlmsgSeq: UInt = 0u,
    public var nlmsgPid: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Nlmsghdr> =
            object : TypeLayout<Nlmsghdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Nlmsghdr =
                    Nlmsghdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Nlmsghdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nlmsgLen)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.nlmsgType)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.nlmsgFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nlmsgSeq)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.nlmsgPid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nlmsghdr =
                    Nlmsghdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nlmsghdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nlmsgLen)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.nlmsgType)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.nlmsgFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nlmsgSeq)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.nlmsgPid)
                }
            }
    }
}

public data class Nlmsgerr(
    public var error: CInt = 0,
    public var msg: Nlmsghdr = Nlmsghdr(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Nlmsgerr> =
            object : TypeLayout<Nlmsgerr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): Nlmsgerr =
                    Nlmsgerr(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (Nlmsghdr.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Nlmsgerr) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.error)
                    (Nlmsghdr.typeLayout()).writeNative(addr + 4L, value.msg)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nlmsgerr =
                    Nlmsgerr(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (Nlmsghdr.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nlmsgerr) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.error)
                    (Nlmsghdr.typeLayout()).writeBytes(buf, offset + 4, value.msg)
                }
            }
    }
}

public data class NlPktinfo(
    public var group: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<NlPktinfo> =
            object : TypeLayout<NlPktinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): NlPktinfo =
                    NlPktinfo(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: NlPktinfo) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.group)
                }
                override fun readBytes(buf: ByteArray, offset: Int): NlPktinfo =
                    NlPktinfo(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: NlPktinfo) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.group)
                }
            }
    }
}

public data class NlMmapReq(
    public var nmBlockSize: CUint = 0u,
    public var nmBlockNr: CUint = 0u,
    public var nmFrameSize: CUint = 0u,
    public var nmFrameNr: CUint = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<NlMmapReq> =
            object : TypeLayout<NlMmapReq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): NlMmapReq =
                    NlMmapReq(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: NlMmapReq) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nmBlockSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.nmBlockNr)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nmFrameSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.nmFrameNr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): NlMmapReq =
                    NlMmapReq(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: NlMmapReq) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nmBlockSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.nmBlockNr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nmFrameSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.nmFrameNr)
                }
            }
    }
}

public data class NlMmapHdr(
    public var nmStatus: CUint = 0u,
    public var nmLen: CUint = 0u,
    public var nmGroup: UInt = 0u,
    public var nmPid: UInt = 0u,
    public var nmUid: UInt = 0u,
    public var nmGid: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<NlMmapHdr> =
            object : TypeLayout<NlMmapHdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): NlMmapHdr =
                    NlMmapHdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: NlMmapHdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.nmStatus)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.nmLen)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nmGroup)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.nmPid)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.nmUid)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.nmGid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): NlMmapHdr =
                    NlMmapHdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: NlMmapHdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.nmStatus)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.nmLen)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nmGroup)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.nmPid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.nmUid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.nmGid)
                }
            }
    }
}

public data class Nlattr(
    public var nlaLen: UShort = 0u,
    public var nlaType: UShort = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Nlattr> =
            object : TypeLayout<Nlattr> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Nlattr =
                    Nlattr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Nlattr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.nlaLen)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.nlaType)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Nlattr =
                    Nlattr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Nlattr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.nlaLen)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.nlaType)
                }
            }
    }
}

public data class In6Pktinfo(
    public var ipi6Addr: In6Addr = In6Addr(),
    public var ipi6Ifindex: CInt = 0,
)

public data class InotifyEvent(
    public var wd: CInt = 0,
    public var mask: UInt = 0u,
    public var cookie: UInt = 0u,
    public var len: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<InotifyEvent> =
            object : TypeLayout<InotifyEvent> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): InotifyEvent =
                    InotifyEvent(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: InotifyEvent) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.wd)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.mask)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.cookie)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.len)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InotifyEvent =
                    InotifyEvent(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InotifyEvent) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.wd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.mask)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.cookie)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.len)
                }
            }
    }
}

public data class SockExtendedErr(
    public var eeErrno: UInt = 0u,
    public var eeOrigin: UByte = 0u,
    public var eeType: UByte = 0u,
    public var eeCode: UByte = 0u,
    public var eePad: UByte = 0u,
    public var eeInfo: UInt = 0u,
    public var eeData: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SockExtendedErr> =
            object : TypeLayout<SockExtendedErr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockExtendedErr =
                    SockExtendedErr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U8).readNative(addr + 6L),
                        (PrimitiveLayouts.U8).readNative(addr + 7L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SockExtendedErr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.eeErrno)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.eeOrigin)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.eeType)
                    (PrimitiveLayouts.U8).writeNative(addr + 6L, value.eeCode)
                    (PrimitiveLayouts.U8).writeNative(addr + 7L, value.eePad)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.eeInfo)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.eeData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockExtendedErr =
                    SockExtendedErr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 7),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockExtendedErr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.eeErrno)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.eeOrigin)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.eeType)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 6, value.eeCode)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 7, value.eePad)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.eeInfo)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.eeData)
                }
            }
    }
}

public class RegexT internal constructor(
    public var reMagic: CInt,
    public var reNsub: SizeT,
    reEndpCtorArg: ConstPtr,
    reGutsCtorArg: MutPtr,
) {
    public var reEndp: ConstPtr = reEndpCtorArg
        internal set

    public var reGuts: MutPtr = reGutsCtorArg
        internal set

    public constructor() : this(
        reMagic = 0,
        reNsub = 0uL,
        reEndpCtorArg = ConstPtr(0L),
        reGutsCtorArg = MutPtr(0L),
    )

    public operator fun component1(): CInt = this.reMagic

    public operator fun component2(): SizeT = this.reNsub

    public operator fun component3(): ConstPtr = this.reEndp

    public operator fun component4(): MutPtr = this.reGuts

    public fun copy(reMagic: CInt = this.reMagic, reNsub: SizeT = this.reNsub, reEndp: ConstPtr = this.reEndp, reGuts: MutPtr = this.reGuts): RegexT = RegexT(reMagic = reMagic, reNsub = reNsub, reEndpCtorArg = reEndp, reGutsCtorArg = reGuts)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RegexT) return false
        if (this.reMagic != other.reMagic) return false
        if (this.reNsub != other.reNsub) return false
        if (this.reEndp != other.reEndp) return false
        if (this.reGuts != other.reGuts) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.reMagic.hashCode()
        result = 31 * result + this.reNsub.hashCode()
        result = 31 * result + this.reEndp.hashCode()
        result = 31 * result + this.reGuts.hashCode()
        return result
    }

    public fun clone(): RegexT = RegexT(reMagic, reNsub, reEndp, reGuts)

    public companion object {
        public fun typeLayout(): TypeLayout<RegexT> =
            object : TypeLayout<RegexT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): RegexT =
                    RegexT(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: RegexT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.reMagic)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.reNsub)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 16L, value.reEndp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.reGuts)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RegexT =
                    RegexT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RegexT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.reMagic)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.reNsub)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 16, value.reEndp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.reGuts)
                }
            }

        public operator fun invoke(
            reMagic: CInt = 0,
            reNsub: SizeT = 0uL,
            reEndp: ConstPtr = ConstPtr(0L),
            reGuts: MutPtr = MutPtr(0L),
        ): RegexT = RegexT(
            reMagic = reMagic,
            reNsub = reNsub,
            reEndpCtorArg = reEndp,
            reGutsCtorArg = reGuts,
        )
    }
}

public data class RegmatchT(
    public var rmSo: SsizeT = 0L,
    public var rmEo: SsizeT = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<RegmatchT> =
            object : TypeLayout<RegmatchT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): RegmatchT =
                    RegmatchT(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: RegmatchT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.rmSo)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.rmEo)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RegmatchT =
                    RegmatchT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RegmatchT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.rmSo)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.rmEo)
                }
            }
    }
}

public data class SockaddrVm(
    public var svmFamily: SaFamilyT = 0u,
    public var svmReserved1: CUshort = 0u,
    public var svmPort: CUint = 0u,
    public var svmCid: CUint = 0u,
    public var svmZero: UByteArray = UByteArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrVm) return false
        if (this.svmFamily != other.svmFamily) return false
        if (this.svmReserved1 != other.svmReserved1) return false
        if (this.svmPort != other.svmPort) return false
        if (this.svmCid != other.svmCid) return false
        if (!this.svmZero.contentEquals(other.svmZero)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.svmFamily.hashCode()
        result = 31 * result + this.svmReserved1.hashCode()
        result = 31 * result + this.svmPort.hashCode()
        result = 31 * result + this.svmCid.hashCode()
        result = 31 * result + this.svmZero.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrVm> =
            object : TypeLayout<SockaddrVm> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockaddrVm =
                    SockaddrVm(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray(4)).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SockaddrVm) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.svmFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.svmReserved1)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.svmPort)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.svmCid)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeNative(addr + 12L, value.svmZero)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrVm =
                    SockaddrVm(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray(4)).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrVm) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.svmFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.svmReserved1)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.svmPort)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.svmCid)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeBytes(buf, offset + 12, value.svmZero)
                }
            }
    }
}

// linux/elf.h
public data class Elf32Phdr(
    public var pType: Elf32Word = 0u,
    public var pOffset: Elf32Off = 0u,
    public var pVaddr: Elf32Addr = 0u,
    public var pPaddr: Elf32Addr = 0u,
    public var pFilesz: Elf32Word = 0u,
    public var pMemsz: Elf32Word = 0u,
    public var pFlags: Elf32Word = 0u,
    public var pAlign: Elf32Word = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Elf32Phdr> =
            object : TypeLayout<Elf32Phdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Elf32Phdr =
                    Elf32Phdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                    )
                override fun writeNative(addr: Long, value: Elf32Phdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.pType)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.pOffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.pVaddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.pPaddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.pFilesz)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.pMemsz)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.pFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.pAlign)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Elf32Phdr =
                    Elf32Phdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Elf32Phdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.pType)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.pOffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.pVaddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.pPaddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.pFilesz)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.pMemsz)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.pFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.pAlign)
                }
            }
    }
}

public data class Elf64Phdr(
    public var pType: Elf64Word = 0u,
    public var pFlags: Elf64Word = 0u,
    public var pOffset: Elf64Off = 0uL,
    public var pVaddr: Elf64Addr = 0uL,
    public var pPaddr: Elf64Addr = 0uL,
    public var pFilesz: Elf64Xword = 0uL,
    public var pMemsz: Elf64Xword = 0uL,
    public var pAlign: Elf64Xword = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Elf64Phdr> =
            object : TypeLayout<Elf64Phdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): Elf64Phdr =
                    Elf64Phdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Elf64Phdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.pType)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.pFlags)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.pOffset)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.pVaddr)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.pPaddr)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.pFilesz)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.pMemsz)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.pAlign)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Elf64Phdr =
                    Elf64Phdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Elf64Phdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.pType)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.pFlags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.pOffset)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.pVaddr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.pPaddr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.pFilesz)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.pMemsz)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.pAlign)
                }
            }
    }
}

// link.h
public class DlPhdrInfo internal constructor(
    public var dlpiAddr: Elf64Addr,
    dlpiNameCtorArg: ConstPtr,
    dlpiPhdrCtorArg: ConstPtr,
    public var dlpiPhnum: Elf64Half,
    // These fields were added in Android R
    public var dlpiAdds: CUlonglong,
    public var dlpiSubs: CUlonglong,
    public var dlpiTlsModid: SizeT,
    dlpiTlsDataCtorArg: MutPtr,
) {
    public var dlpiName: ConstPtr = dlpiNameCtorArg
        internal set

    public var dlpiPhdr: ConstPtr = dlpiPhdrCtorArg
        internal set

    public var dlpiTlsData: MutPtr = dlpiTlsDataCtorArg
        internal set

    public constructor() : this(
        dlpiAddr = 0uL,
        dlpiNameCtorArg = ConstPtr(0L),
        dlpiPhdrCtorArg = ConstPtr(0L),
        dlpiPhnum = 0u,
        dlpiAdds = 0uL,
        dlpiSubs = 0uL,
        dlpiTlsModid = 0uL,
        dlpiTlsDataCtorArg = MutPtr(0L),
    )

    public operator fun component1(): Elf64Addr = this.dlpiAddr

    public operator fun component2(): ConstPtr = this.dlpiName

    public operator fun component3(): ConstPtr = this.dlpiPhdr

    public operator fun component4(): Elf64Half = this.dlpiPhnum

    public operator fun component5(): CUlonglong = this.dlpiAdds

    public operator fun component6(): CUlonglong = this.dlpiSubs

    public operator fun component7(): SizeT = this.dlpiTlsModid

    public operator fun component8(): MutPtr = this.dlpiTlsData

    public fun copy(dlpiAddr: Elf64Addr = this.dlpiAddr, dlpiName: ConstPtr = this.dlpiName, dlpiPhdr: ConstPtr = this.dlpiPhdr, dlpiPhnum: Elf64Half = this.dlpiPhnum, dlpiAdds: CUlonglong = this.dlpiAdds, dlpiSubs: CUlonglong = this.dlpiSubs, dlpiTlsModid: SizeT = this.dlpiTlsModid, dlpiTlsData: MutPtr = this.dlpiTlsData): DlPhdrInfo = DlPhdrInfo(dlpiAddr = dlpiAddr, dlpiNameCtorArg = dlpiName, dlpiPhdrCtorArg = dlpiPhdr, dlpiPhnum = dlpiPhnum, dlpiAdds = dlpiAdds, dlpiSubs = dlpiSubs, dlpiTlsModid = dlpiTlsModid, dlpiTlsDataCtorArg = dlpiTlsData)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DlPhdrInfo) return false
        if (this.dlpiAddr != other.dlpiAddr) return false
        if (this.dlpiName != other.dlpiName) return false
        if (this.dlpiPhdr != other.dlpiPhdr) return false
        if (this.dlpiPhnum != other.dlpiPhnum) return false
        if (this.dlpiAdds != other.dlpiAdds) return false
        if (this.dlpiSubs != other.dlpiSubs) return false
        if (this.dlpiTlsModid != other.dlpiTlsModid) return false
        if (this.dlpiTlsData != other.dlpiTlsData) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.dlpiAddr.hashCode()
        result = 31 * result + this.dlpiName.hashCode()
        result = 31 * result + this.dlpiPhdr.hashCode()
        result = 31 * result + this.dlpiPhnum.hashCode()
        result = 31 * result + this.dlpiAdds.hashCode()
        result = 31 * result + this.dlpiSubs.hashCode()
        result = 31 * result + this.dlpiTlsModid.hashCode()
        result = 31 * result + this.dlpiTlsData.hashCode()
        return result
    }

    public fun clone(): DlPhdrInfo = DlPhdrInfo(dlpiAddr, dlpiName, dlpiPhdr, dlpiPhnum, dlpiAdds, dlpiSubs, dlpiTlsModid, dlpiTlsData)

    public companion object {
        public fun typeLayout(): TypeLayout<DlPhdrInfo> =
            object : TypeLayout<DlPhdrInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): DlPhdrInfo =
                    DlPhdrInfo(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: DlPhdrInfo) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.dlpiAddr)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 8L, value.dlpiName)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 16L, value.dlpiPhdr)
                    (PrimitiveLayouts.U16).writeNative(addr + 24L, value.dlpiPhnum)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.dlpiAdds)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.dlpiSubs)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.dlpiTlsModid)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.dlpiTlsData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DlPhdrInfo =
                    DlPhdrInfo(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DlPhdrInfo) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.dlpiAddr)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 8, value.dlpiName)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 16, value.dlpiPhdr)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 24, value.dlpiPhnum)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.dlpiAdds)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.dlpiSubs)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.dlpiTlsModid)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.dlpiTlsData)
                }
            }

        public operator fun invoke(
            dlpiAddr: Elf64Addr = 0uL,
            dlpiName: ConstPtr = ConstPtr(0L),
            dlpiPhdr: ConstPtr = ConstPtr(0L),
            dlpiPhnum: Elf64Half = 0u,
            dlpiAdds: CUlonglong = 0uL,
            dlpiSubs: CUlonglong = 0uL,
            dlpiTlsModid: SizeT = 0uL,
            dlpiTlsData: MutPtr = MutPtr(0L),
        ): DlPhdrInfo = DlPhdrInfo(
            dlpiAddr = dlpiAddr,
            dlpiNameCtorArg = dlpiName,
            dlpiPhdrCtorArg = dlpiPhdr,
            dlpiPhnum = dlpiPhnum,
            dlpiAdds = dlpiAdds,
            dlpiSubs = dlpiSubs,
            dlpiTlsModid = dlpiTlsModid,
            dlpiTlsDataCtorArg = dlpiTlsData,
        )
    }
}

// linux/seccomp.h
public data class SeccompData(
    public var nr: CInt = 0,
    public var arch: U32 = 0u,
    public var instructionPointer: U64 = 0uL,
    public var args: ULongArray = ULongArray(6),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SeccompData) return false
        if (this.nr != other.nr) return false
        if (this.arch != other.arch) return false
        if (this.instructionPointer != other.instructionPointer) return false
        if (!this.args.contentEquals(other.args)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.nr.hashCode()
        result = 31 * result + this.arch.hashCode()
        result = 31 * result + this.instructionPointer.hashCode()
        result = 31 * result + this.args.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SeccompData> =
            object : TypeLayout<SeccompData> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): SeccompData =
                    SeccompData(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uLongArray(6)).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: SeccompData) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.nr)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.arch)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.instructionPointer)
                    (PrimitiveArrayLayouts.uLongArray(6)).writeNative(addr + 16L, value.args)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompData =
                    SeccompData(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uLongArray(6)).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompData) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.nr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.arch)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.instructionPointer)
                    (PrimitiveArrayLayouts.uLongArray(6)).writeBytes(buf, offset + 16, value.args)
                }
            }
    }
}

public data class SeccompMetadata(
    public var filterOff: U64 = 0uL,
    public var flags: U64 = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SeccompMetadata> =
            object : TypeLayout<SeccompMetadata> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SeccompMetadata =
                    SeccompMetadata(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: SeccompMetadata) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.filterOff)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompMetadata =
                    SeccompMetadata(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompMetadata) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.filterOff)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.flags)
                }
            }
    }
}

public data class SeccompNotifSizes(
    public var seccompNotif: U16 = 0u,
    public var seccompNotifResp: U16 = 0u,
    public var seccompData: U16 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SeccompNotifSizes> =
            object : TypeLayout<SeccompNotifSizes> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 6L
                override fun readNative(addr: Long): SeccompNotifSizes =
                    SeccompNotifSizes(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: SeccompNotifSizes) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.seccompNotif)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.seccompNotifResp)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.seccompData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompNotifSizes =
                    SeccompNotifSizes(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompNotifSizes) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.seccompNotif)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.seccompNotifResp)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.seccompData)
                }
            }
    }
}

public data class SeccompNotif(
    public var id: U64 = 0uL,
    public var pid: U32 = 0u,
    public var flags: U32 = 0u,
    public var data: SeccompData = SeccompData(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SeccompNotif> =
            object : TypeLayout<SeccompNotif> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 80L
                override fun readNative(addr: Long): SeccompNotif =
                    SeccompNotif(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (SeccompData.typeLayout()).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: SeccompNotif) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.id)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.pid)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.flags)
                    (SeccompData.typeLayout()).writeNative(addr + 16L, value.data)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompNotif =
                    SeccompNotif(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (SeccompData.typeLayout()).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompNotif) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.id)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.pid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.flags)
                    (SeccompData.typeLayout()).writeBytes(buf, offset + 16, value.data)
                }
            }
    }
}

public data class SeccompNotifResp(
    public var id: U64 = 0uL,
    public var value: S64 = 0L,
    public var error: S32 = 0,
    public var flags: U32 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SeccompNotifResp> =
            object : TypeLayout<SeccompNotifResp> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): SeccompNotifResp =
                    SeccompNotifResp(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: SeccompNotifResp) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.id)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.value)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.error)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.flags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompNotifResp =
                    SeccompNotifResp(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompNotifResp) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.id)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.value)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.error)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.flags)
                }
            }
    }
}

public data class SeccompNotifAddfd(
    public var id: U64 = 0uL,
    public var flags: U32 = 0u,
    public var srcfd: U32 = 0u,
    public var newfd: U32 = 0u,
    public var newfdFlags: U32 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SeccompNotifAddfd> =
            object : TypeLayout<SeccompNotifAddfd> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): SeccompNotifAddfd =
                    SeccompNotifAddfd(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: SeccompNotifAddfd) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.id)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.flags)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.srcfd)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.newfd)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.newfdFlags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SeccompNotifAddfd =
                    SeccompNotifAddfd(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SeccompNotifAddfd) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.id)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.flags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.srcfd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.newfd)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.newfdFlags)
                }
            }
    }
}

public data class PtracePeeksiginfoArgs(
    public var off: U64 = 0uL,
    public var flags: U32 = 0u,
    public var nr: S32 = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<PtracePeeksiginfoArgs> =
            object : TypeLayout<PtracePeeksiginfoArgs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): PtracePeeksiginfoArgs =
                    PtracePeeksiginfoArgs(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: PtracePeeksiginfoArgs) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.off)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.flags)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.nr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PtracePeeksiginfoArgs =
                    PtracePeeksiginfoArgs(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PtracePeeksiginfoArgs) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.off)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.flags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.nr)
                }
            }
    }
}

// linux/input.h
public data class InputEvent(
    public var time: Timeval = Timeval(),
    public var type: U16 = 0u,
    public var code: U16 = 0u,
    public var value: S32 = 0,
)

public data class InputId(
    public var bustype: U16 = 0u,
    public var vendor: U16 = 0u,
    public var product: U16 = 0u,
    public var version: U16 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<InputId> =
            object : TypeLayout<InputId> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): InputId =
                    InputId(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: InputId) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.bustype)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.vendor)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.product)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.version)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InputId =
                    InputId(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InputId) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.bustype)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.vendor)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.product)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.version)
                }
            }
    }
}

public data class InputAbsinfo(
    public var value: S32 = 0,
    public var minimum: S32 = 0,
    public var maximum: S32 = 0,
    public var fuzz: S32 = 0,
    public var flat: S32 = 0,
    public var resolution: S32 = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<InputAbsinfo> =
            object : TypeLayout<InputAbsinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): InputAbsinfo =
                    InputAbsinfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: InputAbsinfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.value)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.minimum)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.maximum)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.fuzz)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.flat)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.resolution)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InputAbsinfo =
                    InputAbsinfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InputAbsinfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.value)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.minimum)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.maximum)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.fuzz)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.flat)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.resolution)
                }
            }
    }
}

public data class InputKeymapEntry(
    public var flags: U8 = 0u,
    public var len: U8 = 0u,
    public var index: U16 = 0u,
    public var keycode: U32 = 0u,
    public var scancode: UByteArray = UByteArray(32),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InputKeymapEntry) return false
        if (this.flags != other.flags) return false
        if (this.len != other.len) return false
        if (this.index != other.index) return false
        if (this.keycode != other.keycode) return false
        if (!this.scancode.contentEquals(other.scancode)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.flags.hashCode()
        result = 31 * result + this.len.hashCode()
        result = 31 * result + this.index.hashCode()
        result = 31 * result + this.keycode.hashCode()
        result = 31 * result + this.scancode.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<InputKeymapEntry> =
            object : TypeLayout<InputKeymapEntry> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): InputKeymapEntry =
                    InputKeymapEntry(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(32)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: InputKeymapEntry) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.flags)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.len)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.index)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.keycode)
                    (PrimitiveArrayLayouts.uByteArray(32)).writeNative(addr + 8L, value.scancode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InputKeymapEntry =
                    InputKeymapEntry(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(32)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InputKeymapEntry) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.flags)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.len)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.index)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.keycode)
                    (PrimitiveArrayLayouts.uByteArray(32)).writeBytes(buf, offset + 8, value.scancode)
                }
            }
    }
}

public data class InputMask(
    public var type: U32 = 0u,
    public var codesSize: U32 = 0u,
    public var codesPtr: U64 = 0uL,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<InputMask> =
            object : TypeLayout<InputMask> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): InputMask =
                    InputMask(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: InputMask) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.type)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.codesSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.codesPtr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InputMask =
                    InputMask(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InputMask) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.type)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.codesSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.codesPtr)
                }
            }
    }
}

public data class FfReplay(
    public var length: U16 = 0u,
    public var delay: U16 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FfReplay> =
            object : TypeLayout<FfReplay> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FfReplay =
                    FfReplay(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: FfReplay) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.length)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.delay)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfReplay =
                    FfReplay(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfReplay) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.length)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.delay)
                }
            }
    }
}

public data class FfTrigger(
    public var button: U16 = 0u,
    public var interval: U16 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FfTrigger> =
            object : TypeLayout<FfTrigger> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FfTrigger =
                    FfTrigger(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: FfTrigger) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.button)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.interval)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfTrigger =
                    FfTrigger(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfTrigger) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.button)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.interval)
                }
            }
    }
}

public data class FfEnvelope(
    public var attackLength: U16 = 0u,
    public var attackLevel: U16 = 0u,
    public var fadeLength: U16 = 0u,
    public var fadeLevel: U16 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FfEnvelope> =
            object : TypeLayout<FfEnvelope> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FfEnvelope =
                    FfEnvelope(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: FfEnvelope) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.attackLength)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.attackLevel)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.fadeLength)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.fadeLevel)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfEnvelope =
                    FfEnvelope(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfEnvelope) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.attackLength)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.attackLevel)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.fadeLength)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.fadeLevel)
                }
            }
    }
}

public data class FfConstantEffect(
    public var level: S16 = 0,
    public var envelope: FfEnvelope = FfEnvelope(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FfConstantEffect> =
            object : TypeLayout<FfConstantEffect> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 10L
                override fun readNative(addr: Long): FfConstantEffect =
                    FfConstantEffect(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (FfEnvelope.typeLayout()).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: FfConstantEffect) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.level)
                    (FfEnvelope.typeLayout()).writeNative(addr + 2L, value.envelope)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfConstantEffect =
                    FfConstantEffect(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (FfEnvelope.typeLayout()).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfConstantEffect) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.level)
                    (FfEnvelope.typeLayout()).writeBytes(buf, offset + 2, value.envelope)
                }
            }
    }
}

public data class FfRampEffect(
    public var startLevel: S16 = 0,
    public var endLevel: S16 = 0,
    public var envelope: FfEnvelope = FfEnvelope(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FfRampEffect> =
            object : TypeLayout<FfRampEffect> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): FfRampEffect =
                    FfRampEffect(
                        (PrimitiveLayouts.I16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (FfEnvelope.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: FfRampEffect) {
                    (PrimitiveLayouts.I16).writeNative(addr, value.startLevel)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.endLevel)
                    (FfEnvelope.typeLayout()).writeNative(addr + 4L, value.envelope)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfRampEffect =
                    FfRampEffect(
                        (PrimitiveLayouts.I16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (FfEnvelope.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfRampEffect) {
                    (PrimitiveLayouts.I16).writeBytes(buf, offset, value.startLevel)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.endLevel)
                    (FfEnvelope.typeLayout()).writeBytes(buf, offset + 4, value.envelope)
                }
            }
    }
}

public data class FfConditionEffect(
    public var rightSaturation: U16 = 0u,
    public var leftSaturation: U16 = 0u,
    public var rightCoeff: S16 = 0,
    public var leftCoeff: S16 = 0,
    public var deadband: U16 = 0u,
    public var center: S16 = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FfConditionEffect> =
            object : TypeLayout<FfConditionEffect> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): FfConditionEffect =
                    FfConditionEffect(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.I16).readNative(addr + 4L),
                        (PrimitiveLayouts.I16).readNative(addr + 6L),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (PrimitiveLayouts.I16).readNative(addr + 10L),
                    )
                override fun writeNative(addr: Long, value: FfConditionEffect) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.rightSaturation)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.leftSaturation)
                    (PrimitiveLayouts.I16).writeNative(addr + 4L, value.rightCoeff)
                    (PrimitiveLayouts.I16).writeNative(addr + 6L, value.leftCoeff)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.deadband)
                    (PrimitiveLayouts.I16).writeNative(addr + 10L, value.center)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfConditionEffect =
                    FfConditionEffect(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 10),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfConditionEffect) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.rightSaturation)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.leftSaturation)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 4, value.rightCoeff)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 6, value.leftCoeff)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.deadband)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 10, value.center)
                }
            }
    }
}

public class FfPeriodicEffect internal constructor(
    public var waveform: U16,
    public var period: U16,
    public var magnitude: S16,
    public var offset: S16,
    public var phase: U16,
    public var envelope: FfEnvelope,
    public var customLen: U32,
    customDataCtorArg: MutPtr,
) {
    public var customData: MutPtr = customDataCtorArg
        internal set

    public constructor() : this(
        waveform = 0u,
        period = 0u,
        magnitude = 0,
        offset = 0,
        phase = 0u,
        envelope = FfEnvelope(),
        customLen = 0u,
        customDataCtorArg = MutPtr(0L),
    )

    public operator fun component1(): U16 = this.waveform

    public operator fun component2(): U16 = this.period

    public operator fun component3(): S16 = this.magnitude

    public operator fun component4(): S16 = this.offset

    public operator fun component5(): U16 = this.phase

    public operator fun component6(): FfEnvelope = this.envelope

    public operator fun component7(): U32 = this.customLen

    public operator fun component8(): MutPtr = this.customData

    public fun copy(waveform: U16 = this.waveform, period: U16 = this.period, magnitude: S16 = this.magnitude, offset: S16 = this.offset, phase: U16 = this.phase, envelope: FfEnvelope = this.envelope, customLen: U32 = this.customLen, customData: MutPtr = this.customData): FfPeriodicEffect = FfPeriodicEffect(waveform = waveform, period = period, magnitude = magnitude, offset = offset, phase = phase, envelope = envelope, customLen = customLen, customDataCtorArg = customData)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FfPeriodicEffect) return false
        if (this.waveform != other.waveform) return false
        if (this.period != other.period) return false
        if (this.magnitude != other.magnitude) return false
        if (this.offset != other.offset) return false
        if (this.phase != other.phase) return false
        if (this.envelope != other.envelope) return false
        if (this.customLen != other.customLen) return false
        if (this.customData != other.customData) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.waveform.hashCode()
        result = 31 * result + this.period.hashCode()
        result = 31 * result + this.magnitude.hashCode()
        result = 31 * result + this.offset.hashCode()
        result = 31 * result + this.phase.hashCode()
        result = 31 * result + this.envelope.hashCode()
        result = 31 * result + this.customLen.hashCode()
        result = 31 * result + this.customData.hashCode()
        return result
    }

    public fun clone(): FfPeriodicEffect = FfPeriodicEffect(waveform, period, magnitude, offset, phase, envelope, customLen, customData)

    public companion object {
        public fun typeLayout(): TypeLayout<FfPeriodicEffect> =
            object : TypeLayout<FfPeriodicEffect> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): FfPeriodicEffect =
                    FfPeriodicEffect(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.I16).readNative(addr + 4L),
                        (PrimitiveLayouts.I16).readNative(addr + 6L),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (FfEnvelope.typeLayout()).readNative(addr + 10L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: FfPeriodicEffect) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.waveform)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.period)
                    (PrimitiveLayouts.I16).writeNative(addr + 4L, value.magnitude)
                    (PrimitiveLayouts.I16).writeNative(addr + 6L, value.offset)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.phase)
                    (FfEnvelope.typeLayout()).writeNative(addr + 10L, value.envelope)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.customLen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.customData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfPeriodicEffect =
                    FfPeriodicEffect(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (FfEnvelope.typeLayout()).readBytes(buf, offset + 10),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfPeriodicEffect) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.waveform)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.period)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 4, value.magnitude)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 6, value.offset)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.phase)
                    (FfEnvelope.typeLayout()).writeBytes(buf, offset + 10, value.envelope)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.customLen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.customData)
                }
            }

        public operator fun invoke(
            waveform: U16 = 0u,
            period: U16 = 0u,
            magnitude: S16 = 0,
            offset: S16 = 0,
            phase: U16 = 0u,
            envelope: FfEnvelope = FfEnvelope(),
            customLen: U32 = 0u,
            customData: MutPtr = MutPtr(0L),
        ): FfPeriodicEffect = FfPeriodicEffect(
            waveform = waveform,
            period = period,
            magnitude = magnitude,
            offset = offset,
            phase = phase,
            envelope = envelope,
            customLen = customLen,
            customDataCtorArg = customData,
        )
    }
}

public data class FfRumbleEffect(
    public var strongMagnitude: U16 = 0u,
    public var weakMagnitude: U16 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FfRumbleEffect> =
            object : TypeLayout<FfRumbleEffect> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FfRumbleEffect =
                    FfRumbleEffect(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: FfRumbleEffect) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.strongMagnitude)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.weakMagnitude)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfRumbleEffect =
                    FfRumbleEffect(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfRumbleEffect) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.strongMagnitude)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.weakMagnitude)
                }
            }
    }
}

public data class FfEffect(
    public var type: U16 = 0u,
    public var id: S16 = 0,
    public var direction: U16 = 0u,
    public var trigger: FfTrigger = FfTrigger(),
    public var replay: FfReplay = FfReplay(),
    // Note(1.0): this is actually a union
    public var u: ULongArray = ULongArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FfEffect) return false
        if (this.type != other.type) return false
        if (this.id != other.id) return false
        if (this.direction != other.direction) return false
        if (this.trigger != other.trigger) return false
        if (this.replay != other.replay) return false
        if (!this.u.contentEquals(other.u)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.type.hashCode()
        result = 31 * result + this.id.hashCode()
        result = 31 * result + this.direction.hashCode()
        result = 31 * result + this.trigger.hashCode()
        result = 31 * result + this.replay.hashCode()
        result = 31 * result + this.u.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<FfEffect> =
            object : TypeLayout<FfEffect> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): FfEffect =
                    FfEffect(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (PrimitiveLayouts.U16).readNative(addr + 4L),
                        (FfTrigger.typeLayout()).readNative(addr + 6L),
                        (FfReplay.typeLayout()).readNative(addr + 10L),
                        (PrimitiveArrayLayouts.uLongArray(4)).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: FfEffect) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.type)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.id)
                    (PrimitiveLayouts.U16).writeNative(addr + 4L, value.direction)
                    (FfTrigger.typeLayout()).writeNative(addr + 6L, value.trigger)
                    (FfReplay.typeLayout()).writeNative(addr + 10L, value.replay)
                    (PrimitiveArrayLayouts.uLongArray(4)).writeNative(addr + 16L, value.u)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FfEffect =
                    FfEffect(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 4),
                        (FfTrigger.typeLayout()).readBytes(buf, offset + 6),
                        (FfReplay.typeLayout()).readBytes(buf, offset + 10),
                        (PrimitiveArrayLayouts.uLongArray(4)).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FfEffect) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.type)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.id)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 4, value.direction)
                    (FfTrigger.typeLayout()).writeBytes(buf, offset + 6, value.trigger)
                    (FfReplay.typeLayout()).writeBytes(buf, offset + 10, value.replay)
                    (PrimitiveArrayLayouts.uLongArray(4)).writeBytes(buf, offset + 16, value.u)
                }
            }
    }
}

// linux/uinput.h
public data class UinputFfUpload(
    public var requestId: U32 = 0u,
    public var retval: S32 = 0,
    public var effect: FfEffect = FfEffect(),
    public var old: FfEffect = FfEffect(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<UinputFfUpload> =
            object : TypeLayout<UinputFfUpload> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 104L
                override fun readNative(addr: Long): UinputFfUpload =
                    UinputFfUpload(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (FfEffect.typeLayout()).readNative(addr + 8L),
                        (FfEffect.typeLayout()).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: UinputFfUpload) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.requestId)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.retval)
                    (FfEffect.typeLayout()).writeNative(addr + 8L, value.effect)
                    (FfEffect.typeLayout()).writeNative(addr + 56L, value.old)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UinputFfUpload =
                    UinputFfUpload(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (FfEffect.typeLayout()).readBytes(buf, offset + 8),
                        (FfEffect.typeLayout()).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UinputFfUpload) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.requestId)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.retval)
                    (FfEffect.typeLayout()).writeBytes(buf, offset + 8, value.effect)
                    (FfEffect.typeLayout()).writeBytes(buf, offset + 56, value.old)
                }
            }
    }
}

public data class UinputFfErase(
    public var requestId: U32 = 0u,
    public var retval: S32 = 0,
    public var effectId: U32 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<UinputFfErase> =
            object : TypeLayout<UinputFfErase> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): UinputFfErase =
                    UinputFfErase(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: UinputFfErase) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.requestId)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.retval)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.effectId)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UinputFfErase =
                    UinputFfErase(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UinputFfErase) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.requestId)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.retval)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.effectId)
                }
            }
    }
}

public data class UinputAbsSetup(
    public var code: U16 = 0u,
    public var absinfo: InputAbsinfo = InputAbsinfo(),
) {
    public companion object {
        public fun typeLayout(): TypeLayout<UinputAbsSetup> =
            object : TypeLayout<UinputAbsSetup> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 28L
                override fun readNative(addr: Long): UinputAbsSetup =
                    UinputAbsSetup(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (InputAbsinfo.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: UinputAbsSetup) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.code)
                    (InputAbsinfo.typeLayout()).writeNative(addr + 4L, value.absinfo)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UinputAbsSetup =
                    UinputAbsSetup(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (InputAbsinfo.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UinputAbsSetup) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.code)
                    (InputAbsinfo.typeLayout()).writeBytes(buf, offset + 4, value.absinfo)
                }
            }
    }
}

public class Option internal constructor(
    nameCtorArg: ConstPtr,
    public var hasArg: CInt,
    flagCtorArg: MutPtr,
    public var value: CInt,
) {
    public var name: ConstPtr = nameCtorArg
        internal set

    public var flag: MutPtr = flagCtorArg
        internal set

    public constructor() : this(
        nameCtorArg = ConstPtr(0L),
        hasArg = 0,
        flagCtorArg = MutPtr(0L),
        value = 0,
    )

    public operator fun component1(): ConstPtr = this.name

    public operator fun component2(): CInt = this.hasArg

    public operator fun component3(): MutPtr = this.flag

    public operator fun component4(): CInt = this.value

    public fun copy(name: ConstPtr = this.name, hasArg: CInt = this.hasArg, flag: MutPtr = this.flag, value: CInt = this.value): Option = Option(nameCtorArg = name, hasArg = hasArg, flagCtorArg = flag, value = value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Option) return false
        if (this.name != other.name) return false
        if (this.hasArg != other.hasArg) return false
        if (this.flag != other.flag) return false
        if (this.value != other.value) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.name.hashCode()
        result = 31 * result + this.hasArg.hashCode()
        result = 31 * result + this.flag.hashCode()
        result = 31 * result + this.value.hashCode()
        return result
    }

    public fun clone(): Option = Option(name, hasArg, flag, value)

    public companion object {
        public fun typeLayout(): TypeLayout<Option> =
            object : TypeLayout<Option> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Option =
                    Option(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: Option) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.name)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.hasArg)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.flag)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Option =
                    Option(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Option) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.name)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.hasArg)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.flag)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.value)
                }
            }

        public operator fun invoke(
            name: ConstPtr = ConstPtr(0L),
            hasArg: CInt = 0,
            flag: MutPtr = MutPtr(0L),
            value: CInt = 0,
        ): Option = Option(
            nameCtorArg = name,
            hasArg = hasArg,
            flagCtorArg = flag,
            value = value,
        )
    }
}

public data class CAnonymousIfruMap(
    public var memStart: CUlong = 0uL,
    public var memEnd: CUlong = 0uL,
    public var baseAddr: CUshort = 0u,
    public var irq: CUchar = 0u,
    public var dma: CUchar = 0u,
    public var port: CUchar = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousIfruMap> =
            object : TypeLayout<CAnonymousIfruMap> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): CAnonymousIfruMap =
                    CAnonymousIfruMap(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.U8).readNative(addr + 18L),
                        (PrimitiveLayouts.U8).readNative(addr + 19L),
                        (PrimitiveLayouts.U8).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousIfruMap) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.memStart)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.memEnd)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.baseAddr)
                    (PrimitiveLayouts.U8).writeNative(addr + 18L, value.irq)
                    (PrimitiveLayouts.U8).writeNative(addr + 19L, value.dma)
                    (PrimitiveLayouts.U8).writeNative(addr + 20L, value.port)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousIfruMap =
                    CAnonymousIfruMap(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 18),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 19),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousIfruMap) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.memStart)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.memEnd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.baseAddr)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 18, value.irq)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 19, value.dma)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 20, value.port)
                }
            }
    }
}

public data class In6Ifreq(
    public var ifr6Addr: In6Addr = In6Addr(),
    public var ifr6Prefixlen: UInt = 0u,
    public var ifr6Ifindex: CInt = 0,
)

public data class SockaddrNl(
    public var nlFamily: SaFamilyT = 0u,
    public var nlPad: CUshort = 0u,
    public var nlPid: UInt = 0u,
    public var nlGroups: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrNl> =
            object : TypeLayout<SockaddrNl> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): SockaddrNl =
                    SockaddrNl(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: SockaddrNl) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.nlFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.nlPad)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.nlPid)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nlGroups)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrNl =
                    SockaddrNl(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrNl) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.nlFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.nlPad)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.nlPid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nlGroups)
                }
            }
    }
}

public data class Dirent(
    public var dIno: ULong = 0uL,
    public var dOff: Long = 0L,
    public var dReclen: CUshort = 0u,
    public var dType: CUchar = 0u,
    public var dName: UByteArray = UByteArray(256),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dirent) return false
        if (this.dIno != other.dIno) return false
        if (this.dOff != other.dOff) return false
        if (this.dReclen != other.dReclen) return false
        if (this.dType != other.dType) return false
        if (!this.dName.contentEquals(other.dName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.dIno.hashCode()
        result = 31 * result + this.dOff.hashCode()
        result = 31 * result + this.dReclen.hashCode()
        result = 31 * result + this.dType.hashCode()
        result = 31 * result + this.dName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Dirent> =
            object : TypeLayout<Dirent> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 280L
                override fun readNative(addr: Long): Dirent =
                    Dirent(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.U8).readNative(addr + 18L),
                        (PrimitiveArrayLayouts.uByteArray(256)).readNative(addr + 19L),
                    )
                override fun writeNative(addr: Long, value: Dirent) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.dIno)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.dOff)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.dReclen)
                    (PrimitiveLayouts.U8).writeNative(addr + 18L, value.dType)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeNative(addr + 19L, value.dName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Dirent =
                    Dirent(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 18),
                        (PrimitiveArrayLayouts.uByteArray(256)).readBytes(buf, offset + 19),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Dirent) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.dIno)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.dOff)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.dReclen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 18, value.dType)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeBytes(buf, offset + 19, value.dName)
                }
            }
    }
}

// Note(1.0,deprecate): lfs binding to be removed
public data class Dirent64(
    public var dIno: ULong = 0uL,
    public var dOff: Long = 0L,
    public var dReclen: CUshort = 0u,
    public var dType: CUchar = 0u,
    public var dName: UByteArray = UByteArray(256),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dirent64) return false
        if (this.dIno != other.dIno) return false
        if (this.dOff != other.dOff) return false
        if (this.dReclen != other.dReclen) return false
        if (this.dType != other.dType) return false
        if (!this.dName.contentEquals(other.dName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.dIno.hashCode()
        result = 31 * result + this.dOff.hashCode()
        result = 31 * result + this.dReclen.hashCode()
        result = 31 * result + this.dType.hashCode()
        result = 31 * result + this.dName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Dirent64> =
            object : TypeLayout<Dirent64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 280L
                override fun readNative(addr: Long): Dirent64 =
                    Dirent64(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I64).readNative(addr + 8L),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.U8).readNative(addr + 18L),
                        (PrimitiveArrayLayouts.uByteArray(256)).readNative(addr + 19L),
                    )
                override fun writeNative(addr: Long, value: Dirent64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.dIno)
                    (PrimitiveLayouts.I64).writeNative(addr + 8L, value.dOff)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.dReclen)
                    (PrimitiveLayouts.U8).writeNative(addr + 18L, value.dType)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeNative(addr + 19L, value.dName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Dirent64 =
                    Dirent64(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 18),
                        (PrimitiveArrayLayouts.uByteArray(256)).readBytes(buf, offset + 19),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Dirent64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.dIno)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 8, value.dOff)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.dReclen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 18, value.dType)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeBytes(buf, offset + 19, value.dName)
                }
            }
    }
}

public data class SiginfoT(
    public var siSigno: CInt = 0,
    public var siErrno: CInt = 0,
    public var siCode: CInt = 0,
    public var pad: IntArray = IntArray(29),
    public var align: IntArray = IntArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SiginfoT) return false
        if (this.siSigno != other.siSigno) return false
        if (this.siErrno != other.siErrno) return false
        if (this.siCode != other.siCode) return false
        if (!this.pad.contentEquals(other.pad)) return false
        if (!this.align.contentEquals(other.align)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.siSigno.hashCode()
        result = 31 * result + this.siErrno.hashCode()
        result = 31 * result + this.siCode.hashCode()
        result = 31 * result + this.pad.contentHashCode()
        result = 31 * result + this.align.contentHashCode()
        return result
    }

    public fun siAddr(): MutPtr {
        data class SiginfoSigfault(
            public var siSigno: CInt = 0,
            public var siErrno: CInt = 0,
            public var siCode: CInt = 0,
            public var siAddr: MutPtr = MutPtr(0L),
        )
        return (SiginfoSigfault.typeLayout()).readNative((ConstPtr(((constPtrFromRustCast(this))).rawAddress)).rawAddress).siAddr
    }

    public fun siValue(): Sigval {
        data class SiginfoTimer(
            public var siSigno: CInt = 0,
            public var siErrno: CInt = 0,
            public var siCode: CInt = 0,
            public var siTid: CInt = 0,
            public var siOverrun: CInt = 0,
            public var siSigval: Sigval = Sigval(),
        )
        return (ObjectSlotLayout<SiginfoTimer>()).readNative((ConstPtr(((constPtrFromRustCast(this))).rawAddress)).rawAddress).siSigval
    }

    public fun sifields(): Sifields = (ObjectSlotLayout<SiginfoF>()).readNative((ConstPtr(((constPtrFromRustCast(this))).rawAddress)).rawAddress).sifields

    public fun siPid(): PidT = this.sifields().sigchld.siPid

    public fun siUid(): UidT = this.sifields().sigchld.siUid

    public fun siStatus(): CInt = this.sifields().sigchld.siStatus

    public fun siUtime(): CLong = this.sifields().sigchld.siUtime

    public fun siStime(): CLong = this.sifields().sigchld.siStime

    public companion object {
        public fun typeLayout(): TypeLayout<SiginfoT> =
            object : TypeLayout<SiginfoT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): SiginfoT =
                    SiginfoT(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.intArray(29)).readNative(addr + 12L),
                        (PrimitiveArrayLayouts.usize64Array(0)).readNative(addr + 128L),
                    )
                override fun writeNative(addr: Long, value: SiginfoT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.siSigno)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.siErrno)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.siCode)
                    (PrimitiveArrayLayouts.intArray(29)).writeNative(addr + 12L, value.pad)
                    (PrimitiveArrayLayouts.usize64Array(0)).writeNative(addr + 128L, value.align)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SiginfoT =
                    SiginfoT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.intArray(29)).readBytes(buf, offset + 12),
                        (PrimitiveArrayLayouts.usize64Array(0)).readBytes(buf, offset + 128),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SiginfoT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.siSigno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.siErrno)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.siCode)
                    (PrimitiveArrayLayouts.intArray(29)).writeBytes(buf, offset + 12, value.pad)
                    (PrimitiveArrayLayouts.usize64Array(0)).writeBytes(buf, offset + 128, value.align)
                }
            }
    }
}

public data class Lastlog(
    public var llTime: io.github.kotlinmania.rustlang.libc.TimeT = 0L,
    public var llLine: UByteArray = UByteArray(UT_LINESIZE),
    public var llHost: UByteArray = UByteArray(UT_HOSTSIZE),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Lastlog) return false
        if (this.llTime != other.llTime) return false
        if (!this.llLine.contentEquals(other.llLine)) return false
        if (!this.llHost.contentEquals(other.llHost)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.llTime.hashCode()
        result = 31 * result + this.llLine.contentHashCode()
        result = 31 * result + this.llHost.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Lastlog> =
            object : TypeLayout<Lastlog> {
                override val alignBytes: Long = 8L
                private val off2: Long = alignUpBytes((8L + ((UT_LINESIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off2 + ((UT_HOSTSIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): Lastlog =
                    Lastlog(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((UT_LINESIZE).toInt())).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray((UT_HOSTSIZE).toInt())).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: Lastlog) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.llTime)
                    (PrimitiveArrayLayouts.uByteArray((UT_LINESIZE).toInt())).writeNative(addr + 8L, value.llLine)
                    (PrimitiveArrayLayouts.uByteArray((UT_HOSTSIZE).toInt())).writeNative(addr + off2, value.llHost)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Lastlog =
                    Lastlog(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((UT_LINESIZE).toInt())).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray((UT_HOSTSIZE).toInt())).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Lastlog) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.llTime)
                    (PrimitiveArrayLayouts.uByteArray((UT_LINESIZE).toInt())).writeBytes(buf, offset + 8, value.llLine)
                    (PrimitiveArrayLayouts.uByteArray((UT_HOSTSIZE).toInt())).writeBytes(buf, offset + off2.toInt(), value.llHost)
                }
            }
    }
}

public data class Utmp(
    public var utType: CShort = 0,
    public var utPid: PidT = 0,
    public var utLine: UByteArray = UByteArray(UT_LINESIZE),
    public var utId: UByteArray = UByteArray(4),
    public var utUser: UByteArray = UByteArray(UT_NAMESIZE),
    public var utHost: UByteArray = UByteArray(UT_HOSTSIZE),
    public var utExit: ExitStatus = ExitStatus(),
    public var utSession: CLong = 0L,
    public var utTv: Timeval = Timeval(),
    public var utAddrV6: IntArray = IntArray(4),
    public var unused: UByteArray = UByteArray(20),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Utmp) return false
        if (this.utType != other.utType) return false
        if (this.utPid != other.utPid) return false
        if (!this.utLine.contentEquals(other.utLine)) return false
        if (!this.utId.contentEquals(other.utId)) return false
        if (!this.utUser.contentEquals(other.utUser)) return false
        if (!this.utHost.contentEquals(other.utHost)) return false
        if (this.utExit != other.utExit) return false
        if (this.utSession != other.utSession) return false
        if (this.utTv != other.utTv) return false
        if (!this.utAddrV6.contentEquals(other.utAddrV6)) return false
        if (!this.unused.contentEquals(other.unused)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.utType.hashCode()
        result = 31 * result + this.utPid.hashCode()
        result = 31 * result + this.utLine.contentHashCode()
        result = 31 * result + this.utId.contentHashCode()
        result = 31 * result + this.utUser.contentHashCode()
        result = 31 * result + this.utHost.contentHashCode()
        result = 31 * result + this.utExit.hashCode()
        result = 31 * result + this.utSession.hashCode()
        result = 31 * result + this.utTv.hashCode()
        result = 31 * result + this.utAddrV6.contentHashCode()
        result = 31 * result + this.unused.contentHashCode()
        return result
    }
}

public data class SockaddrAlg(
    public var salgFamily: SaFamilyT = 0u,
    public var salgType: UByteArray = UByteArray(14),
    public var salgFeat: UInt = 0u,
    public var salgMask: UInt = 0u,
    public var salgName: UByteArray = UByteArray(64),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrAlg) return false
        if (this.salgFamily != other.salgFamily) return false
        if (!this.salgType.contentEquals(other.salgType)) return false
        if (this.salgFeat != other.salgFeat) return false
        if (this.salgMask != other.salgMask) return false
        if (!this.salgName.contentEquals(other.salgName)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.salgFamily.hashCode()
        result = 31 * result + this.salgType.contentHashCode()
        result = 31 * result + this.salgFeat.hashCode()
        result = 31 * result + this.salgMask.hashCode()
        result = 31 * result + this.salgName.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrAlg> =
            object : TypeLayout<SockaddrAlg> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 88L
                override fun readNative(addr: Long): SockaddrAlg =
                    SockaddrAlg(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(14)).readNative(addr + 1L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveArrayLayouts.uByteArray(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: SockaddrAlg) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.salgFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeNative(addr + 1L, value.salgType)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.salgFeat)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.salgMask)
                    (PrimitiveArrayLayouts.uByteArray(64)).writeNative(addr + 24L, value.salgName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrAlg =
                    SockaddrAlg(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(14)).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveArrayLayouts.uByteArray(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrAlg) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.salgFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeBytes(buf, offset + 1, value.salgType)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.salgFeat)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.salgMask)
                    (PrimitiveArrayLayouts.uByteArray(64)).writeBytes(buf, offset + 24, value.salgName)
                }
            }
    }
}

public data class UinputSetup(
    public var id: InputId = InputId(),
    public var name: UByteArray = UByteArray(UINPUT_MAX_NAME_SIZE),
    public var ffEffectsMax: U32 = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UinputSetup) return false
        if (this.id != other.id) return false
        if (!this.name.contentEquals(other.name)) return false
        if (this.ffEffectsMax != other.ffEffectsMax) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.id.hashCode()
        result = 31 * result + this.name.contentHashCode()
        result = 31 * result + this.ffEffectsMax.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<UinputSetup> =
            object : TypeLayout<UinputSetup> {
                override val alignBytes: Long = 4L
                private val off2: Long = alignUpBytes((8L + ((UINPUT_MAX_NAME_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes)), 4L)
                override val sizeBytes: Long = alignUpBytes((off2 + 4L), 4L)
                override fun readNative(addr: Long): UinputSetup =
                    UinputSetup(
                        (InputId.typeLayout()).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: UinputSetup) {
                    (InputId.typeLayout()).writeNative(addr, value.id)
                    (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).writeNative(addr + 8L, value.name)
                    (PrimitiveLayouts.U32).writeNative(addr + off2, value.ffEffectsMax)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UinputSetup =
                    UinputSetup(
                        (InputId.typeLayout()).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UinputSetup) {
                    (InputId.typeLayout()).writeBytes(buf, offset, value.id)
                    (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).writeBytes(buf, offset + 8, value.name)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off2.toInt(), value.ffEffectsMax)
                }
            }
    }
}

public data class UinputUserDev(
    public var name: UByteArray = UByteArray(UINPUT_MAX_NAME_SIZE),
    public var id: InputId = InputId(),
    public var ffEffectsMax: U32 = 0u,
    public var absmax: IntArray = IntArray(ABS_CNT),
    public var absmin: IntArray = IntArray(ABS_CNT),
    public var absfuzz: IntArray = IntArray(ABS_CNT),
    public var absflat: IntArray = IntArray(ABS_CNT),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UinputUserDev) return false
        if (!this.name.contentEquals(other.name)) return false
        if (this.id != other.id) return false
        if (this.ffEffectsMax != other.ffEffectsMax) return false
        if (!this.absmax.contentEquals(other.absmax)) return false
        if (!this.absmin.contentEquals(other.absmin)) return false
        if (!this.absfuzz.contentEquals(other.absfuzz)) return false
        if (!this.absflat.contentEquals(other.absflat)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.name.contentHashCode()
        result = 31 * result + this.id.hashCode()
        result = 31 * result + this.ffEffectsMax.hashCode()
        result = 31 * result + this.absmax.contentHashCode()
        result = 31 * result + this.absmin.contentHashCode()
        result = 31 * result + this.absfuzz.contentHashCode()
        result = 31 * result + this.absflat.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<UinputUserDev> =
            object : TypeLayout<UinputUserDev> {
                override val alignBytes: Long = 4L
                private val off1: Long = alignUpBytes(((UINPUT_MAX_NAME_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes), 2L)
                private val off2: Long = alignUpBytes((off1 + 8L), 4L)
                private val off3: Long = alignUpBytes((off2 + 4L), 4L)
                private val off4: Long = alignUpBytes((off3 + ((ABS_CNT).toLong() * (PrimitiveLayouts.I32).strideBytes)), 4L)
                private val off5: Long = alignUpBytes((off4 + ((ABS_CNT).toLong() * (PrimitiveLayouts.I32).strideBytes)), 4L)
                private val off6: Long = alignUpBytes((off5 + ((ABS_CNT).toLong() * (PrimitiveLayouts.I32).strideBytes)), 4L)
                override val sizeBytes: Long = alignUpBytes((off6 + ((ABS_CNT).toLong() * (PrimitiveLayouts.I32).strideBytes)), 4L)
                override fun readNative(addr: Long): UinputUserDev =
                    UinputUserDev(
                        (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).readNative(addr),
                        (InputId.typeLayout()).readNative(addr + off1),
                        (PrimitiveLayouts.U32).readNative(addr + off2),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readNative(addr + off3),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readNative(addr + off4),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readNative(addr + off5),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readNative(addr + off6),
                    )
                override fun writeNative(addr: Long, value: UinputUserDev) {
                    (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).writeNative(addr, value.name)
                    (InputId.typeLayout()).writeNative(addr + off1, value.id)
                    (PrimitiveLayouts.U32).writeNative(addr + off2, value.ffEffectsMax)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeNative(addr + off3, value.absmax)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeNative(addr + off4, value.absmin)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeNative(addr + off5, value.absfuzz)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeNative(addr + off6, value.absflat)
                }
                override fun readBytes(buf: ByteArray, offset: Int): UinputUserDev =
                    UinputUserDev(
                        (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).readBytes(buf, offset),
                        (InputId.typeLayout()).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + off2.toInt()),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readBytes(buf, offset + off3.toInt()),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readBytes(buf, offset + off4.toInt()),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readBytes(buf, offset + off5.toInt()),
                        (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).readBytes(buf, offset + off6.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: UinputUserDev) {
                    (PrimitiveArrayLayouts.uByteArray((UINPUT_MAX_NAME_SIZE).toInt())).writeBytes(buf, offset, value.name)
                    (InputId.typeLayout()).writeBytes(buf, offset + off1.toInt(), value.id)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + off2.toInt(), value.ffEffectsMax)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeBytes(buf, offset + off3.toInt(), value.absmax)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeBytes(buf, offset + off4.toInt(), value.absmin)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeBytes(buf, offset + off5.toInt(), value.absfuzz)
                    (PrimitiveArrayLayouts.intArray((ABS_CNT).toInt())).writeBytes(buf, offset + off6.toInt(), value.absflat)
                }
            }
    }
}

// linux/futex.h
public data class FutexWaitv(
    public var value: U64 = 0uL,
    public var uaddr: U64 = 0uL,
    public var flags: U32 = 0u,
    public var reserved: U32 = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<FutexWaitv> =
            object : TypeLayout<FutexWaitv> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): FutexWaitv =
                    FutexWaitv(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: FutexWaitv) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.value)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.uaddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.flags)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FutexWaitv =
                    FutexWaitv(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FutexWaitv) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.value)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.uaddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.flags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.reserved)
                }
            }
    }
}

public data class Mmsghdr(
    public var msgHdr: Msghdr = Msghdr(),
    public var msgLen: CUint = 0u,
)

public data class AfAlgIv(
    public var ivlen: UInt = 0u,
    public var iv: UByteArray = UByteArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AfAlgIv) return false
        if (this.ivlen != other.ivlen) return false
        if (!this.iv.contentEquals(other.iv)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ivlen.hashCode()
        result = 31 * result + this.iv.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<AfAlgIv> =
            object : TypeLayout<AfAlgIv> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): AfAlgIv =
                    AfAlgIv(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(0)).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: AfAlgIv) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.ivlen)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeNative(addr + 4L, value.iv)
                }
                override fun readBytes(buf: ByteArray, offset: Int): AfAlgIv =
                    AfAlgIv(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(0)).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: AfAlgIv) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.ivlen)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeBytes(buf, offset + 4, value.iv)
                }
            }
    }
}

public class CAnonymousIfrIfru internal constructor(
    public var ifruAddr: Sockaddr,
    public var ifruDstaddr: Sockaddr,
    public var ifruBroadaddr: Sockaddr,
    public var ifruNetmask: Sockaddr,
    public var ifruHwaddr: Sockaddr,
    public var ifruFlags: CShort,
    public var ifruIfindex: CInt,
    public var ifruMetric: CInt,
    public var ifruMtu: CInt,
    public var ifruMap: CAnonymousIfruMap,
    public var ifruSlave: UByteArray,
    public var ifruNewname: UByteArray,
    ifruDataCtorArg: MutPtr,
) {
    public var ifruData: MutPtr = ifruDataCtorArg
        internal set

    public constructor() : this(
        ifruAddr = Sockaddr(),
        ifruDstaddr = Sockaddr(),
        ifruBroadaddr = Sockaddr(),
        ifruNetmask = Sockaddr(),
        ifruHwaddr = Sockaddr(),
        ifruFlags = 0,
        ifruIfindex = 0,
        ifruMetric = 0,
        ifruMtu = 0,
        ifruMap = CAnonymousIfruMap(),
        ifruSlave = UByteArray(IFNAMSIZ.toInt()),
        ifruNewname = UByteArray(IFNAMSIZ.toInt()),
        ifruDataCtorArg = MutPtr(0L),
    )

    public operator fun component1(): Sockaddr = this.ifruAddr

    public operator fun component2(): Sockaddr = this.ifruDstaddr

    public operator fun component3(): Sockaddr = this.ifruBroadaddr

    public operator fun component4(): Sockaddr = this.ifruNetmask

    public operator fun component5(): Sockaddr = this.ifruHwaddr

    public operator fun component6(): CShort = this.ifruFlags

    public operator fun component7(): CInt = this.ifruIfindex

    public operator fun component8(): CInt = this.ifruMetric

    public operator fun component9(): CInt = this.ifruMtu

    public operator fun component10(): CAnonymousIfruMap = this.ifruMap

    public operator fun component11(): UByteArray = this.ifruSlave

    public operator fun component12(): UByteArray = this.ifruNewname

    public operator fun component13(): MutPtr = this.ifruData

    public fun copy(ifruAddr: Sockaddr = this.ifruAddr, ifruDstaddr: Sockaddr = this.ifruDstaddr, ifruBroadaddr: Sockaddr = this.ifruBroadaddr, ifruNetmask: Sockaddr = this.ifruNetmask, ifruHwaddr: Sockaddr = this.ifruHwaddr, ifruFlags: CShort = this.ifruFlags, ifruIfindex: CInt = this.ifruIfindex, ifruMetric: CInt = this.ifruMetric, ifruMtu: CInt = this.ifruMtu, ifruMap: CAnonymousIfruMap = this.ifruMap, ifruSlave: UByteArray = this.ifruSlave, ifruNewname: UByteArray = this.ifruNewname, ifruData: MutPtr = this.ifruData): CAnonymousIfrIfru = CAnonymousIfrIfru(ifruAddr = ifruAddr, ifruDstaddr = ifruDstaddr, ifruBroadaddr = ifruBroadaddr, ifruNetmask = ifruNetmask, ifruHwaddr = ifruHwaddr, ifruFlags = ifruFlags, ifruIfindex = ifruIfindex, ifruMetric = ifruMetric, ifruMtu = ifruMtu, ifruMap = ifruMap, ifruSlave = ifruSlave, ifruNewname = ifruNewname, ifruDataCtorArg = ifruData)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousIfrIfru) return false
        if (this.ifruAddr != other.ifruAddr) return false
        if (this.ifruDstaddr != other.ifruDstaddr) return false
        if (this.ifruBroadaddr != other.ifruBroadaddr) return false
        if (this.ifruNetmask != other.ifruNetmask) return false
        if (this.ifruHwaddr != other.ifruHwaddr) return false
        if (this.ifruFlags != other.ifruFlags) return false
        if (this.ifruIfindex != other.ifruIfindex) return false
        if (this.ifruMetric != other.ifruMetric) return false
        if (this.ifruMtu != other.ifruMtu) return false
        if (this.ifruMap != other.ifruMap) return false
        if (!this.ifruSlave.contentEquals(other.ifruSlave)) return false
        if (!this.ifruNewname.contentEquals(other.ifruNewname)) return false
        if (this.ifruData != other.ifruData) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifruAddr.hashCode()
        result = 31 * result + this.ifruDstaddr.hashCode()
        result = 31 * result + this.ifruBroadaddr.hashCode()
        result = 31 * result + this.ifruNetmask.hashCode()
        result = 31 * result + this.ifruHwaddr.hashCode()
        result = 31 * result + this.ifruFlags.hashCode()
        result = 31 * result + this.ifruIfindex.hashCode()
        result = 31 * result + this.ifruMetric.hashCode()
        result = 31 * result + this.ifruMtu.hashCode()
        result = 31 * result + this.ifruMap.hashCode()
        result = 31 * result + this.ifruSlave.contentHashCode()
        result = 31 * result + this.ifruNewname.contentHashCode()
        result = 31 * result + this.ifruData.hashCode()
        return result
    }

    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_ifr_ifru").finishNonExhaustive()
    }

    public companion object {
        public operator fun invoke(
            ifruAddr: Sockaddr = Sockaddr(),
            ifruDstaddr: Sockaddr = Sockaddr(),
            ifruBroadaddr: Sockaddr = Sockaddr(),
            ifruNetmask: Sockaddr = Sockaddr(),
            ifruHwaddr: Sockaddr = Sockaddr(),
            ifruFlags: CShort = 0,
            ifruIfindex: CInt = 0,
            ifruMetric: CInt = 0,
            ifruMtu: CInt = 0,
            ifruMap: CAnonymousIfruMap = CAnonymousIfruMap(),
            ifruSlave: UByteArray = UByteArray(IFNAMSIZ.toInt()),
            ifruNewname: UByteArray = UByteArray(IFNAMSIZ.toInt()),
            ifruData: MutPtr = MutPtr(0L),
        ): CAnonymousIfrIfru = CAnonymousIfrIfru(
            ifruAddr = ifruAddr,
            ifruDstaddr = ifruDstaddr,
            ifruBroadaddr = ifruBroadaddr,
            ifruNetmask = ifruNetmask,
            ifruHwaddr = ifruHwaddr,
            ifruFlags = ifruFlags,
            ifruIfindex = ifruIfindex,
            ifruMetric = ifruMetric,
            ifruMtu = ifruMtu,
            ifruMap = ifruMap,
            ifruSlave = ifruSlave,
            ifruNewname = ifruNewname,
            ifruDataCtorArg = ifruData,
        )
    }
}

public data class Ifreq(
    /**
     * interface name, e.g. "en0"
     */
    public var ifrName: UByteArray = UByteArray(IFNAMSIZ.toInt()),
    public var ifrIfru: CAnonymousIfrIfru = CAnonymousIfrIfru(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ifreq) return false
        if (!this.ifrName.contentEquals(other.ifrName)) return false
        if (this.ifrIfru != other.ifrIfru) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifrName.contentHashCode()
        result = 31 * result + this.ifrIfru.hashCode()
        return result
    }
}

public class CAnonymousIfcIfcu internal constructor(
    ifcuBufCtorArg: MutPtr,
    ifcuReqCtorArg: MutPtr,
) {
    public var ifcuBuf: MutPtr = ifcuBufCtorArg
        internal set

    public var ifcuReq: MutPtr = ifcuReqCtorArg
        internal set

    public constructor() : this(
        ifcuBufCtorArg = MutPtr(0L),
        ifcuReqCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.ifcuBuf

    public operator fun component2(): MutPtr = this.ifcuReq

    public fun copy(ifcuBuf: MutPtr = this.ifcuBuf, ifcuReq: MutPtr = this.ifcuReq): CAnonymousIfcIfcu = CAnonymousIfcIfcu(ifcuBufCtorArg = ifcuBuf, ifcuReqCtorArg = ifcuReq)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousIfcIfcu) return false
        if (this.ifcuBuf != other.ifcuBuf) return false
        if (this.ifcuReq != other.ifcuReq) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifcuBuf.hashCode()
        result = 31 * result + this.ifcuReq.hashCode()
        return result
    }

    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "__c_anonymous_ifc_ifcu").finishNonExhaustive()
    }

    public companion object {
        public operator fun invoke(
            ifcuBuf: MutPtr = MutPtr(0L),
            ifcuReq: MutPtr = MutPtr(0L),
        ): CAnonymousIfcIfcu = CAnonymousIfcIfcu(
            ifcuBufCtorArg = ifcuBuf,
            ifcuReqCtorArg = ifcuReq,
        )
    }
}

// Structure used in SIOCGIFCONF request.  Used to retrieve interface
// configuration for machine (useful for programs which must know all
// networks accessible).
public data class Ifconf(
    public var ifcLen: CInt = 0,
    // Size of buffer.
    public var ifcIfcu: CAnonymousIfcIfcu = CAnonymousIfcIfcu(),
)

public data class Ethhdr(
    public var hDest: UByteArray = UByteArray(ETH_ALEN),
    public var hSource: UByteArray = UByteArray(ETH_ALEN),
    public var hProto: Be16 = 0u,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ethhdr) return false
        if (!this.hDest.contentEquals(other.hDest)) return false
        if (!this.hSource.contentEquals(other.hSource)) return false
        if (this.hProto != other.hProto) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.hDest.contentHashCode()
        result = 31 * result + this.hSource.contentHashCode()
        result = 31 * result + this.hProto.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Ethhdr> =
            object : TypeLayout<Ethhdr> {
                override val alignBytes: Long = 1L
                private val off1: Long = alignUpBytes(((ETH_ALEN).toLong() * (PrimitiveLayouts.U8).strideBytes), 1L)
                private val off2: Long = alignUpBytes((off1 + ((ETH_ALEN).toLong() * (PrimitiveLayouts.U8).strideBytes)), 1L)
                override val sizeBytes: Long = alignUpBytes((off2 + 2L), 1L)
                override fun readNative(addr: Long): Ethhdr =
                    Ethhdr(
                        (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).readNative(addr + off1),
                        (PrimitiveLayouts.U16).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: Ethhdr) {
                    (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).writeNative(addr, value.hDest)
                    (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).writeNative(addr + off1, value.hSource)
                    (PrimitiveLayouts.U16).writeNative(addr + off2, value.hProto)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ethhdr =
                    Ethhdr(
                        (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ethhdr) {
                    (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).writeBytes(buf, offset, value.hDest)
                    (PrimitiveArrayLayouts.uByteArray((ETH_ALEN).toInt())).writeBytes(buf, offset + off1.toInt(), value.hSource)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + off2.toInt(), value.hProto)
                }
            }
    }
}

public data class SifieldsSigchld(
    public var siPid: PidT = 0,
    public var siUid: UidT = 0,
    public var siStatus: CInt = 0,
    public var siUtime: CLong = 0L,
    public var siStime: CLong = 0L,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<SifieldsSigchld> =
            object : TypeLayout<SifieldsSigchld> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): SifieldsSigchld =
                    SifieldsSigchld(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: SifieldsSigchld) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.siPid)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.siUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.siStatus)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.siUtime)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.siStime)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SifieldsSigchld =
                    SifieldsSigchld(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SifieldsSigchld) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.siPid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.siUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.siStatus)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.siUtime)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.siStime)
                }
            }
    }
}

public class Sifields internal constructor(
    alignPointerCtorArg: MutPtr,
    public var sigchld: SifieldsSigchld,
) {
    public var alignPointer: MutPtr = alignPointerCtorArg
        internal set

    public constructor() : this(
        alignPointerCtorArg = MutPtr(0L),
        sigchld = SifieldsSigchld(),
    )

    public operator fun component1(): MutPtr = this.alignPointer

    public operator fun component2(): SifieldsSigchld = this.sigchld

    public fun copy(alignPointer: MutPtr = this.alignPointer, sigchld: SifieldsSigchld = this.sigchld): Sifields = Sifields(alignPointerCtorArg = alignPointer, sigchld = sigchld)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sifields) return false
        if (this.alignPointer != other.alignPointer) return false
        if (this.sigchld != other.sigchld) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.alignPointer.hashCode()
        result = 31 * result + this.sigchld.hashCode()
        return result
    }

    public fun fmt(f: StringBuilder) {
        DebugStruct(f, "sifields").finishNonExhaustive()
    }

    public companion object {
        public operator fun invoke(
            alignPointer: MutPtr = MutPtr(0L),
            sigchld: SifieldsSigchld = SifieldsSigchld(),
        ): Sifields = Sifields(
            alignPointerCtorArg = alignPointer,
            sigchld = sigchld,
        )
    }
}

public data class SiginfoF(
    public var siginfoBase: IntArray = IntArray(3),
    public var sifields: Sifields = Sifields(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SiginfoF) return false
        if (!this.siginfoBase.contentEquals(other.siginfoBase)) return false
        if (this.sifields != other.sifields) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.siginfoBase.contentHashCode()
        result = 31 * result + this.sifields.hashCode()
        return result
    }
}
