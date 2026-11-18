@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U8
import io.github.kotlinmania.rustlang.libc.unix.CcT
import io.github.kotlinmania.rustlang.libc.unix.In6Addr
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.Sigval
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.Timespec
import io.github.kotlinmania.rustlang.libc.unix.Timeval
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SaFamilyT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Sockaddr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SpeedT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.TcflagT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.UT_HOSTSIZE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.UT_LINESIZE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Be16
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Msghdr

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
public open class PropInfo(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): PropInfo = PropInfo(data, marker)
}

public open class StackT internal constructor(
    ssSpCtorArg: MutPtr,
    public var ssFlags: CInt,
    public var ssSize: SizeT,
) {
    public var ssSp: MutPtr = ssSpCtorArg
        internal set

    public open fun clone(): StackT = StackT(ssSp, ssFlags, ssSize)

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
            ssSp: MutPtr,
            ssFlags: CInt,
            ssSize: SizeT,
        ): StackT = StackT(
            ssSpCtorArg = ssSp,
            ssFlags = ssFlags,
            ssSize = ssSize,
        )
    }
}

public open class FsidT(
    public var value: IntArray,
) {
    public open fun clone(): FsidT = FsidT(value)

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

public open class Termios(
    public var cIflag: TcflagT,
    public var cOflag: TcflagT,
    public var cCflag: TcflagT,
    public var cLflag: TcflagT,
    public var cLine: CcT,
    public var cCc: UByteArray,
) {
    public open fun clone(): Termios = Termios(cIflag, cOflag, cCflag, cLflag, cLine, cCc)

    public companion object {
        public fun typeLayout(): TypeLayout<Termios> =
            object : TypeLayout<Termios> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = alignUpBytes((17L + ((NCCS).toLong() * (PrimitiveLayouts.U8).strideBytes)), 4L)
                override fun readNative(addr: Long): Termios =
                    Termios(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U8).readNative(addr + 16L),
                        (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).readNative(addr + 17L),
                    )
                override fun writeNative(addr: Long, value: Termios) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cIflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.cOflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.cCflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.cLflag)
                    (PrimitiveLayouts.U8).writeNative(addr + 16L, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).writeNative(addr + 17L, value.cCc)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Termios =
                    Termios(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 16),
                        (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).readBytes(buf, offset + 17),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Termios) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cIflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.cOflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.cCflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.cLflag)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 16, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray((NCCS).toInt())).writeBytes(buf, offset + 17, value.cCc)
                }
            }
    }
}

public open class Termios2(
    public var cIflag: TcflagT,
    public var cOflag: TcflagT,
    public var cCflag: TcflagT,
    public var cLflag: TcflagT,
    public var cLine: CcT,
    public var cCc: UByteArray,
    public var cIspeed: SpeedT,
    public var cOspeed: SpeedT,
) {
    public open fun clone(): Termios2 = Termios2(cIflag, cOflag, cCflag, cLflag, cLine, cCc, cIspeed, cOspeed)

    public companion object {
        public fun typeLayout(): TypeLayout<Termios2> =
            object : TypeLayout<Termios2> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 44L
                override fun readNative(addr: Long): Termios2 =
                    Termios2(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U8).readNative(addr + 16L),
                        (PrimitiveArrayLayouts.uByteArray(19)).readNative(addr + 17L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: Termios2) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cIflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.cOflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.cCflag)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.cLflag)
                    (PrimitiveLayouts.U8).writeNative(addr + 16L, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray(19)).writeNative(addr + 17L, value.cCc)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.cIspeed)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.cOspeed)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Termios2 =
                    Termios2(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 16),
                        (PrimitiveArrayLayouts.uByteArray(19)).readBytes(buf, offset + 17),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Termios2) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cIflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.cOflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.cCflag)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.cLflag)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 16, value.cLine)
                    (PrimitiveArrayLayouts.uByteArray(19)).writeBytes(buf, offset + 17, value.cCc)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.cIspeed)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.cOspeed)
                }
            }
    }
}

public open class Mallinfo(
    public var arena: SizeT,
    public var ordblks: SizeT,
    public var smblks: SizeT,
    public var hblks: SizeT,
    public var hblkhd: SizeT,
    public var usmblks: SizeT,
    public var fsmblks: SizeT,
    public var uordblks: SizeT,
    public var fordblks: SizeT,
    public var keepcost: SizeT,
) {
    public open fun clone(): Mallinfo = Mallinfo(arena, ordblks, smblks, hblks, hblkhd, usmblks, fsmblks, uordblks, fordblks, keepcost)

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

public open class Flock(
    public var lType: CShort,
    public var lWhence: CShort,
    public var lStart: OffT,
    public var lLen: OffT,
    public var lPid: PidT,
) {
    public open fun clone(): Flock = Flock(lType, lWhence, lStart, lLen, lPid)

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
public open class Flock64(
    public var lType: CShort,
    public var lWhence: CShort,
    public var lStart: KernelLoffT,
    public var lLen: KernelLoffT,
    public var lPid: KernelPidT,
) {
    public open fun clone(): Flock64 = Flock64(lType, lWhence, lStart, lLen, lPid)

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

public open class CpuSetT(
    public var bits: ULongArray,
) {
    public open fun clone(): CpuSetT = CpuSetT(bits)

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

public open class SemT(
    public var count: CUint,
    public var reserved: IntArray,
) {
    public open fun clone(): SemT = SemT(count, reserved)

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

public open class ExitStatus(
    public var eTermination: CShort,
    public var eExit: CShort,
) {
    public open fun clone(): ExitStatus = ExitStatus(eTermination, eExit)

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

public open class Statvfs(
    public var fBsize: CUlong,
    public var fFrsize: CUlong,
    public var fBlocks: FsblkcntT,
    public var fBfree: FsblkcntT,
    public var fBavail: FsblkcntT,
    public var fFiles: FsfilcntT,
    public var fFfree: FsfilcntT,
    public var fFavail: FsfilcntT,
    public var fFsid: CUlong,
    public var fFlag: CUlong,
    public var fNamemax: CUlong,
    public var fReserved: UIntArray,
) {
    public open fun clone(): Statvfs = Statvfs(fBsize, fFrsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFavail, fFsid, fFlag, fNamemax, fReserved)

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

public open class SignalfdSiginfo(
    public var ssiSigno: UInt,
    public var ssiErrno: Int,
    public var ssiCode: Int,
    public var ssiPid: UInt,
    public var ssiUid: UInt,
    public var ssiFd: Int,
    public var ssiTid: UInt,
    public var ssiBand: UInt,
    public var ssiOverrun: UInt,
    public var ssiTrapno: UInt,
    public var ssiStatus: Int,
    public var ssiInt: Int,
    public var ssiPtr: CUlonglong,
    public var ssiUtime: CUlonglong,
    public var ssiStime: CUlonglong,
    public var ssiAddr: CUlonglong,
    public var ssiAddrLsb: UShort,
    public var pad2: UShort,
    public var ssiSyscall: Int,
    public var ssiCallAddr: ULong,
    public var ssiArch: UInt,
    public var pad: UByteArray,
) {
    public open fun clone(): SignalfdSiginfo = SignalfdSiginfo(ssiSigno, ssiErrno, ssiCode, ssiPid, ssiUid, ssiFd, ssiTid, ssiBand, ssiOverrun, ssiTrapno, ssiStatus, ssiInt, ssiPtr, ssiUtime, ssiStime, ssiAddr, ssiAddrLsb, pad2, ssiSyscall, ssiCallAddr, ssiArch, pad)

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

public open class Itimerspec(
    public var itInterval: Timespec,
    public var itValue: Timespec,
) {
    public open fun clone(): Itimerspec = Itimerspec(itInterval, itValue)
}

public open class Genlmsghdr(
    public var cmd: UByte,
    public var version: UByte,
    public var reserved: UShort,
) {
    public open fun clone(): Genlmsghdr = Genlmsghdr(cmd, version, reserved)

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

public open class Nlmsghdr(
    public var nlmsgLen: UInt,
    public var nlmsgType: UShort,
    public var nlmsgFlags: UShort,
    public var nlmsgSeq: UInt,
    public var nlmsgPid: UInt,
) {
    public open fun clone(): Nlmsghdr = Nlmsghdr(nlmsgLen, nlmsgType, nlmsgFlags, nlmsgSeq, nlmsgPid)

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

public open class Nlmsgerr(
    public var error: CInt,
    public var msg: Nlmsghdr,
) {
    public open fun clone(): Nlmsgerr = Nlmsgerr(error, msg)

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

public open class NlPktinfo(
    public var group: UInt,
) {
    public open fun clone(): NlPktinfo = NlPktinfo(group)

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

public open class NlMmapReq(
    public var nmBlockSize: CUint,
    public var nmBlockNr: CUint,
    public var nmFrameSize: CUint,
    public var nmFrameNr: CUint,
) {
    public open fun clone(): NlMmapReq = NlMmapReq(nmBlockSize, nmBlockNr, nmFrameSize, nmFrameNr)

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

public open class NlMmapHdr(
    public var nmStatus: CUint,
    public var nmLen: CUint,
    public var nmGroup: UInt,
    public var nmPid: UInt,
    public var nmUid: UInt,
    public var nmGid: UInt,
) {
    public open fun clone(): NlMmapHdr = NlMmapHdr(nmStatus, nmLen, nmGroup, nmPid, nmUid, nmGid)

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

public open class Nlattr(
    public var nlaLen: UShort,
    public var nlaType: UShort,
) {
    public open fun clone(): Nlattr = Nlattr(nlaLen, nlaType)

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

public open class In6Pktinfo(
    public var ipi6Addr: In6Addr,
    public var ipi6Ifindex: CInt,
) {
    public open fun clone(): In6Pktinfo = In6Pktinfo(ipi6Addr, ipi6Ifindex)
}

public open class InotifyEvent(
    public var wd: CInt,
    public var mask: UInt,
    public var cookie: UInt,
    public var len: UInt,
) {
    public open fun clone(): InotifyEvent = InotifyEvent(wd, mask, cookie, len)

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

public open class SockExtendedErr(
    public var eeErrno: UInt,
    public var eeOrigin: UByte,
    public var eeType: UByte,
    public var eeCode: UByte,
    public var eePad: UByte,
    public var eeInfo: UInt,
    public var eeData: UInt,
) {
    public open fun clone(): SockExtendedErr = SockExtendedErr(eeErrno, eeOrigin, eeType, eeCode, eePad, eeInfo, eeData)

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

public open class RegexT internal constructor(
    public var reMagic: CInt,
    public var reNsub: SizeT,
    reEndpCtorArg: ConstPtr,
    reGutsCtorArg: MutPtr,
) {
    public var reEndp: ConstPtr = reEndpCtorArg
        internal set

    public var reGuts: MutPtr = reGutsCtorArg
        internal set

    public open fun clone(): RegexT = RegexT(reMagic, reNsub, reEndp, reGuts)

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
            reMagic: CInt,
            reNsub: SizeT,
            reEndp: ConstPtr,
            reGuts: MutPtr,
        ): RegexT = RegexT(
            reMagic = reMagic,
            reNsub = reNsub,
            reEndpCtorArg = reEndp,
            reGutsCtorArg = reGuts,
        )
    }
}

public open class RegmatchT(
    public var rmSo: SsizeT,
    public var rmEo: SsizeT,
) {
    public open fun clone(): RegmatchT = RegmatchT(rmSo, rmEo)

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

public open class SockaddrVm(
    public var svmFamily: SaFamilyT,
    public var svmReserved1: CUshort,
    public var svmPort: CUint,
    public var svmCid: CUint,
    public var svmZero: UByteArray,
) {
    public open fun clone(): SockaddrVm = SockaddrVm(svmFamily, svmReserved1, svmPort, svmCid, svmZero)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrVm> =
            object : TypeLayout<SockaddrVm> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockaddrVm =
                    SockaddrVm(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.uByteArray(4)).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SockaddrVm) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.svmFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.svmReserved1)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.svmPort)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.svmCid)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeNative(addr + 12L, value.svmZero)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrVm =
                    SockaddrVm(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.uByteArray(4)).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrVm) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.svmFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.svmReserved1)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.svmPort)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.svmCid)
                    (PrimitiveArrayLayouts.uByteArray(4)).writeBytes(buf, offset + 12, value.svmZero)
                }
            }
    }
}

// linux/elf.h
public open class Elf32Phdr(
    public var pType: Elf32Word,
    public var pOffset: Elf32Off,
    public var pVaddr: Elf32Addr,
    public var pPaddr: Elf32Addr,
    public var pFilesz: Elf32Word,
    public var pMemsz: Elf32Word,
    public var pFlags: Elf32Word,
    public var pAlign: Elf32Word,
) {
    public open fun clone(): Elf32Phdr = Elf32Phdr(pType, pOffset, pVaddr, pPaddr, pFilesz, pMemsz, pFlags, pAlign)

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

public open class Elf64Phdr(
    public var pType: Elf64Word,
    public var pFlags: Elf64Word,
    public var pOffset: Elf64Off,
    public var pVaddr: Elf64Addr,
    public var pPaddr: Elf64Addr,
    public var pFilesz: Elf64Xword,
    public var pMemsz: Elf64Xword,
    public var pAlign: Elf64Xword,
) {
    public open fun clone(): Elf64Phdr = Elf64Phdr(pType, pFlags, pOffset, pVaddr, pPaddr, pFilesz, pMemsz, pAlign)

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
public open class DlPhdrInfo internal constructor(
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

    public open fun clone(): DlPhdrInfo = DlPhdrInfo(dlpiAddr, dlpiName, dlpiPhdr, dlpiPhnum, dlpiAdds, dlpiSubs, dlpiTlsModid, dlpiTlsData)

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
            dlpiAddr: Elf64Addr,
            dlpiName: ConstPtr,
            dlpiPhdr: ConstPtr,
            dlpiPhnum: Elf64Half,
            dlpiAdds: CUlonglong,
            dlpiSubs: CUlonglong,
            dlpiTlsModid: SizeT,
            dlpiTlsData: MutPtr,
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
public open class SeccompData(
    public var nr: CInt,
    public var arch: U32,
    public var instructionPointer: U64,
    public var args: ULongArray,
) {
    public open fun clone(): SeccompData = SeccompData(nr, arch, instructionPointer, args)

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

public open class SeccompMetadata(
    public var filterOff: U64,
    public var flags: U64,
) {
    public open fun clone(): SeccompMetadata = SeccompMetadata(filterOff, flags)

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

public open class SeccompNotifSizes(
    public var seccompNotif: U16,
    public var seccompNotifResp: U16,
    public var seccompData: U16,
) {
    public open fun clone(): SeccompNotifSizes = SeccompNotifSizes(seccompNotif, seccompNotifResp, seccompData)

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

public open class SeccompNotif(
    public var id: U64,
    public var pid: U32,
    public var flags: U32,
    public var data: SeccompData,
) {
    public open fun clone(): SeccompNotif = SeccompNotif(id, pid, flags, data)

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

public open class SeccompNotifResp(
    public var id: U64,
    public var value: S64,
    public var error: S32,
    public var flags: U32,
) {
    public open fun clone(): SeccompNotifResp = SeccompNotifResp(id, value, error, flags)

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

public open class SeccompNotifAddfd(
    public var id: U64,
    public var flags: U32,
    public var srcfd: U32,
    public var newfd: U32,
    public var newfdFlags: U32,
) {
    public open fun clone(): SeccompNotifAddfd = SeccompNotifAddfd(id, flags, srcfd, newfd, newfdFlags)

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

public open class PtracePeeksiginfoArgs(
    public var off: U64,
    public var flags: U32,
    public var nr: S32,
) {
    public open fun clone(): PtracePeeksiginfoArgs = PtracePeeksiginfoArgs(off, flags, nr)

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
public open class InputEvent(
    public var time: Timeval,
    public var type: U16,
    public var code: U16,
    public var value: S32,
) {
    public open fun clone(): InputEvent = InputEvent(time, type, code, value)
}

public open class InputId(
    public var bustype: U16,
    public var vendor: U16,
    public var product: U16,
    public var version: U16,
) {
    public open fun clone(): InputId = InputId(bustype, vendor, product, version)

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

public open class InputAbsinfo(
    public var value: S32,
    public var minimum: S32,
    public var maximum: S32,
    public var fuzz: S32,
    public var flat: S32,
    public var resolution: S32,
) {
    public open fun clone(): InputAbsinfo = InputAbsinfo(value, minimum, maximum, fuzz, flat, resolution)

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

public open class InputKeymapEntry(
    public var flags: U8,
    public var len: U8,
    public var index: U16,
    public var keycode: U32,
    public var scancode: UByteArray,
) {
    public open fun clone(): InputKeymapEntry = InputKeymapEntry(flags, len, index, keycode, scancode)

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

public open class InputMask(
    public var type: U32,
    public var codesSize: U32,
    public var codesPtr: U64,
) {
    public open fun clone(): InputMask = InputMask(type, codesSize, codesPtr)

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

public open class FfReplay(
    public var length: U16,
    public var delay: U16,
) {
    public open fun clone(): FfReplay = FfReplay(length, delay)

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

public open class FfTrigger(
    public var button: U16,
    public var interval: U16,
) {
    public open fun clone(): FfTrigger = FfTrigger(button, interval)

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

public open class FfEnvelope(
    public var attackLength: U16,
    public var attackLevel: U16,
    public var fadeLength: U16,
    public var fadeLevel: U16,
) {
    public open fun clone(): FfEnvelope = FfEnvelope(attackLength, attackLevel, fadeLength, fadeLevel)

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

public open class FfConstantEffect(
    public var level: S16,
    public var envelope: FfEnvelope,
) {
    public open fun clone(): FfConstantEffect = FfConstantEffect(level, envelope)

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

public open class FfRampEffect(
    public var startLevel: S16,
    public var endLevel: S16,
    public var envelope: FfEnvelope,
) {
    public open fun clone(): FfRampEffect = FfRampEffect(startLevel, endLevel, envelope)

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

public open class FfConditionEffect(
    public var rightSaturation: U16,
    public var leftSaturation: U16,
    public var rightCoeff: S16,
    public var leftCoeff: S16,
    public var deadband: U16,
    public var center: S16,
) {
    public open fun clone(): FfConditionEffect = FfConditionEffect(rightSaturation, leftSaturation, rightCoeff, leftCoeff, deadband, center)

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

public open class FfPeriodicEffect internal constructor(
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

    public open fun clone(): FfPeriodicEffect = FfPeriodicEffect(waveform, period, magnitude, offset, phase, envelope, customLen, customData)

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
            waveform: U16,
            period: U16,
            magnitude: S16,
            offset: S16,
            phase: U16,
            envelope: FfEnvelope,
            customLen: U32,
            customData: MutPtr,
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

public open class FfRumbleEffect(
    public var strongMagnitude: U16,
    public var weakMagnitude: U16,
) {
    public open fun clone(): FfRumbleEffect = FfRumbleEffect(strongMagnitude, weakMagnitude)

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

public open class FfEffect(
    public var type: U16,
    public var id: S16,
    public var direction: U16,
    public var trigger: FfTrigger,
    public var replay: FfReplay,
    // Note(1.0): this is actually a union
    public var u: ULongArray,
) {
    public open fun clone(): FfEffect = FfEffect(type, id, direction, trigger, replay, u)

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
public open class UinputFfUpload(
    public var requestId: U32,
    public var retval: S32,
    public var effect: FfEffect,
    public var old: FfEffect,
) {
    public open fun clone(): UinputFfUpload = UinputFfUpload(requestId, retval, effect, old)

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

public open class UinputFfErase(
    public var requestId: U32,
    public var retval: S32,
    public var effectId: U32,
) {
    public open fun clone(): UinputFfErase = UinputFfErase(requestId, retval, effectId)

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

public open class UinputAbsSetup(
    public var code: U16,
    public var absinfo: InputAbsinfo,
) {
    public open fun clone(): UinputAbsSetup = UinputAbsSetup(code, absinfo)

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

public open class Option internal constructor(
    nameCtorArg: ConstPtr,
    public var hasArg: CInt,
    flagCtorArg: MutPtr,
    public var value: CInt,
) {
    public var name: ConstPtr = nameCtorArg
        internal set

    public var flag: MutPtr = flagCtorArg
        internal set

    public open fun clone(): Option = Option(name, hasArg, flag, value)

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
            name: ConstPtr,
            hasArg: CInt,
            flag: MutPtr,
            value: CInt,
        ): Option = Option(
            nameCtorArg = name,
            hasArg = hasArg,
            flagCtorArg = flag,
            value = value,
        )
    }
}

public open class CAnonymousIfruMap(
    public var memStart: CUlong,
    public var memEnd: CUlong,
    public var baseAddr: CUshort,
    public var irq: CUchar,
    public var dma: CUchar,
    public var port: CUchar,
) {
    public open fun clone(): CAnonymousIfruMap = CAnonymousIfruMap(memStart, memEnd, baseAddr, irq, dma, port)

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

public open class In6Ifreq(
    public var ifr6Addr: In6Addr,
    public var ifr6Prefixlen: UInt,
    public var ifr6Ifindex: CInt,
) {
    public open fun clone(): In6Ifreq = In6Ifreq(ifr6Addr, ifr6Prefixlen, ifr6Ifindex)
}

public open class SockaddrNl(
    public var nlFamily: SaFamilyT,
    public var nlPad: CUshort,
    public var nlPid: UInt,
    public var nlGroups: UInt,
) {
    public open fun clone(): SockaddrNl = SockaddrNl(nlFamily, nlPad, nlPid, nlGroups)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrNl> =
            object : TypeLayout<SockaddrNl> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): SockaddrNl =
                    SockaddrNl(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: SockaddrNl) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.nlFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.nlPad)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.nlPid)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.nlGroups)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrNl =
                    SockaddrNl(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrNl) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.nlFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.nlPad)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.nlPid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.nlGroups)
                }
            }
    }
}

public open class Dirent(
    public var dIno: ULong,
    public var dOff: Long,
    public var dReclen: CUshort,
    public var dType: CUchar,
    public var dName: UByteArray,
) {
    public open fun clone(): Dirent = Dirent(dIno, dOff, dReclen, dType, dName)

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
public open class Dirent64(
    public var dIno: ULong,
    public var dOff: Long,
    public var dReclen: CUshort,
    public var dType: CUchar,
    public var dName: UByteArray,
) {
    public open fun clone(): Dirent64 = Dirent64(dIno, dOff, dReclen, dType, dName)

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

public open class SiginfoT(
    public var siSigno: CInt,
    public var siErrno: CInt,
    public var siCode: CInt,
    public var pad: IntArray,
    public var align: IntArray,
) {
    public open fun clone(): SiginfoT = SiginfoT(siSigno, siErrno, siCode, pad, align)

    public open fun siAddr(): MutPtr {
        open class SiginfoSigfault(
            public var siSigno: CInt,
            public var siErrno: CInt,
            public var siCode: CInt,
            public var siAddr: MutPtr,
        )
        return (SiginfoSigfault.typeLayout()).readNative((ConstPtr(((constPtrFromRustCast(this))).rawAddress)).rawAddress).siAddr
    }

    public open fun siValue(): Sigval {
        open class SiginfoTimer(
            public var siSigno: CInt,
            public var siErrno: CInt,
            public var siCode: CInt,
            public var siTid: CInt,
            public var siOverrun: CInt,
            public var siSigval: Sigval,
        )
        return (UnresolvedTypeLayout<SiginfoTimer>()).readNative((ConstPtr(((constPtrFromRustCast(this))).rawAddress)).rawAddress).siSigval
    }

    public open fun sifields(): Sifields = (UnresolvedTypeLayout<SiginfoF>()).readNative((ConstPtr(((constPtrFromRustCast(this))).rawAddress)).rawAddress).sifields

    public open fun siPid(): PidT = this.sifields().sigchld.siPid

    public open fun siUid(): UidT = this.sifields().sigchld.siUid

    public open fun siStatus(): CInt = this.sifields().sigchld.siStatus

    public open fun siUtime(): CLong = this.sifields().sigchld.siUtime

    public open fun siStime(): CLong = this.sifields().sigchld.siStime

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

public open class Lastlog(
    public var llTime: TimeT,
    public var llLine: UByteArray,
    public var llHost: UByteArray,
) {
    public open fun clone(): Lastlog = Lastlog(llTime, llLine, llHost)

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

public open class Utmp(
    public var utType: CShort,
    public var utPid: PidT,
    public var utLine: UByteArray,
    public var utId: UByteArray,
    public var utUser: UByteArray,
    public var utHost: UByteArray,
    public var utExit: ExitStatus,
    public var utSession: CLong,
    public var utTv: Timeval,
    public var utAddrV6: IntArray,
    public var unused: UByteArray,
) {
    public open fun clone(): Utmp = Utmp(utType, utPid, utLine, utId, utUser, utHost, utExit, utSession, utTv, utAddrV6, unused)
}

public open class SockaddrAlg(
    public var salgFamily: SaFamilyT,
    public var salgType: UByteArray,
    public var salgFeat: UInt,
    public var salgMask: UInt,
    public var salgName: UByteArray,
) {
    public open fun clone(): SockaddrAlg = SockaddrAlg(salgFamily, salgType, salgFeat, salgMask, salgName)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrAlg> =
            object : TypeLayout<SockaddrAlg> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 88L
                override fun readNative(addr: Long): SockaddrAlg =
                    SockaddrAlg(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(14)).readNative(addr + 2L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveArrayLayouts.uByteArray(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: SockaddrAlg) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.salgFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeNative(addr + 2L, value.salgType)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.salgFeat)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.salgMask)
                    (PrimitiveArrayLayouts.uByteArray(64)).writeNative(addr + 24L, value.salgName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrAlg =
                    SockaddrAlg(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(14)).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveArrayLayouts.uByteArray(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrAlg) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.salgFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeBytes(buf, offset + 2, value.salgType)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.salgFeat)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.salgMask)
                    (PrimitiveArrayLayouts.uByteArray(64)).writeBytes(buf, offset + 24, value.salgName)
                }
            }
    }
}

public open class UinputSetup(
    public var id: InputId,
    public var name: UByteArray,
    public var ffEffectsMax: U32,
) {
    public open fun clone(): UinputSetup = UinputSetup(id, name, ffEffectsMax)

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

public open class UinputUserDev(
    public var name: UByteArray,
    public var id: InputId,
    public var ffEffectsMax: U32,
    public var absmax: IntArray,
    public var absmin: IntArray,
    public var absfuzz: IntArray,
    public var absflat: IntArray,
) {
    public open fun clone(): UinputUserDev = UinputUserDev(name, id, ffEffectsMax, absmax, absmin, absfuzz, absflat)

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
public open class FutexWaitv(
    public var value: U64,
    public var uaddr: U64,
    public var flags: U32,
    public var reserved: U32,
) {
    public open fun clone(): FutexWaitv = FutexWaitv(value, uaddr, flags, reserved)

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

public open class Mmsghdr(
    public var msgHdr: Msghdr,
    public var msgLen: CUint,
) {
    public open fun clone(): Mmsghdr = Mmsghdr(msgHdr, msgLen)
}

public open class AfAlgIv(
    public var ivlen: UInt,
    public var iv: UByteArray,
) {
    public open fun clone(): AfAlgIv = AfAlgIv(ivlen, iv)

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
    public var ifruAddr: Sockaddr?,
    public var ifruDstaddr: Sockaddr?,
    public var ifruBroadaddr: Sockaddr?,
    public var ifruNetmask: Sockaddr?,
    public var ifruHwaddr: Sockaddr?,
    public var ifruFlags: CShort?,
    public var ifruIfindex: CInt?,
    public var ifruMetric: CInt?,
    public var ifruMtu: CInt?,
    public var ifruMap: CAnonymousIfruMap?,
    public var ifruSlave: UByteArray?,
    public var ifruNewname: UByteArray?,
    ifruDataCtorArg: MutPtr?,
) {
    public var ifruData: MutPtr? = ifruDataCtorArg
        internal set

    public constructor() : this(
        ifruAddr = null,
        ifruDstaddr = null,
        ifruBroadaddr = null,
        ifruNetmask = null,
        ifruHwaddr = null,
        ifruFlags = null,
        ifruIfindex = null,
        ifruMetric = null,
        ifruMtu = null,
        ifruMap = null,
        ifruSlave = null,
        ifruNewname = null,
        ifruDataCtorArg = null,
    )

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_ifr_ifru").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun invoke(
            ifruAddr: Sockaddr? = null,
            ifruDstaddr: Sockaddr? = null,
            ifruBroadaddr: Sockaddr? = null,
            ifruNetmask: Sockaddr? = null,
            ifruHwaddr: Sockaddr? = null,
            ifruFlags: CShort? = null,
            ifruIfindex: CInt? = null,
            ifruMetric: CInt? = null,
            ifruMtu: CInt? = null,
            ifruMap: CAnonymousIfruMap? = null,
            ifruSlave: UByteArray? = null,
            ifruNewname: UByteArray? = null,
            ifruData: MutPtr? = null,
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

public open class Ifreq(
    /**
     * interface name, e.g. "en0"
     */
    public var ifrName: UByteArray,
    public var ifrIfru: CAnonymousIfrIfru,
) {
    public open fun clone(): Ifreq = Ifreq(ifrName, ifrIfru)
}

public class CAnonymousIfcIfcu internal constructor(
    ifcuBufCtorArg: MutPtr?,
    ifcuReqCtorArg: MutPtr?,
) {
    public var ifcuBuf: MutPtr? = ifcuBufCtorArg
        internal set

    public var ifcuReq: MutPtr? = ifcuReqCtorArg
        internal set

    public constructor() : this(
        ifcuBufCtorArg = null,
        ifcuReqCtorArg = null,
    )

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "__c_anonymous_ifc_ifcu").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun invoke(
            ifcuBuf: MutPtr? = null,
            ifcuReq: MutPtr? = null,
        ): CAnonymousIfcIfcu = CAnonymousIfcIfcu(
            ifcuBufCtorArg = ifcuBuf,
            ifcuReqCtorArg = ifcuReq,
        )
    }
}

// Structure used in SIOCGIFCONF request.  Used to retrieve interface
// configuration for machine (useful for programs which must know all
// networks accessible).
public open class Ifconf(
    public var ifcLen: CInt,
    // Size of buffer.
    public var ifcIfcu: CAnonymousIfcIfcu,
) {
    public open fun clone(): Ifconf = Ifconf(ifcLen, ifcIfcu)
}

public open class Ethhdr(
    public var hDest: UByteArray,
    public var hSource: UByteArray,
    public var hProto: Be16,
) {
    public open fun clone(): Ethhdr = Ethhdr(hDest, hSource, hProto)

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

public open class SifieldsSigchld(
    public var siPid: PidT,
    public var siUid: UidT,
    public var siStatus: CInt,
    public var siUtime: CLong,
    public var siStime: CLong,
) {
    public open fun clone(): SifieldsSigchld = SifieldsSigchld(siPid, siUid, siStatus, siUtime, siStime)

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
    alignPointerCtorArg: MutPtr?,
    public var sigchld: SifieldsSigchld?,
) {
    public var alignPointer: MutPtr? = alignPointerCtorArg
        internal set

    public constructor() : this(
        alignPointerCtorArg = null,
        sigchld = null,
    )

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "sifields").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun invoke(
            alignPointer: MutPtr? = null,
            sigchld: SifieldsSigchld? = null,
        ): Sifields = Sifields(
            alignPointerCtorArg = alignPointer,
            sigchld = sigchld,
        )
    }
}

public open class SiginfoF(
    public var siginfoBase: IntArray,
    public var sifields: Sifields,
) {
    public open fun clone(): SiginfoF = SiginfoF(siginfoBase, sifields)
}
