// port-lint: source unix/linux_like/linux_l4re_shared.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike

import io.github.kotlinmania.rustlang.core.ffi.primitives.CDouble
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CShort
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.CMSG_ALIGN
import io.github.kotlinmania.rustlang.libc.unix.CcT
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.In6Addr
import io.github.kotlinmania.rustlang.libc.unix.LocaleT
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.Timespec
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.RLIM_INFINITY
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.ModeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.Off64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.SocklenT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.DevT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Ino64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.NfdsT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.NlItem
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.PthreadOnceT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Rlim64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Cmsghdr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.Msghdr
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.InoT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.OffT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.gnu.b64.RlimT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.b64.RegoffT

public fun interface GlobErrfuncCallback {
    public operator fun invoke(epath: ConstPtr, errnum: CInt): CInt
}

public fun interface DlIteratePhdrCallbackCallback {
    public operator fun invoke(info: MutPtr, size: SizeT, data: MutPtr): CInt
}

public typealias Elf32Half = UShort

public typealias Elf32Word = UInt

public typealias Elf32Off = UInt

public typealias Elf32Addr = UInt

public typealias Elf32Xword = ULong

public typealias Elf32Sword = Int

public typealias Elf64Half = UShort

public typealias Elf64Word = UInt

public typealias Elf64Off = ULong

public typealias Elf64Addr = ULong

public typealias Elf64Xword = ULong

public typealias Elf64Sxword = Long

public typealias Elf64Sword = Int

public typealias Elf32Section = UShort

public typealias Elf64Section = UShort

public typealias Elf32Relr = Elf32Word

public typealias Elf64Relr = Elf32Xword

public typealias Elf32Rel = CAnonymousElf32Rel

public typealias Elf64Rel = CAnonymousElf64Rel

public typealias Elf32Rela = CAnonymousElf32Rela

public typealias Elf64Rela = CAnonymousElf64Rela

public typealias IconvT = MutPtr

public open class GlobT internal constructor(
    public var glPathc: SizeT,
    glPathvCtorArg: MutPtr,
    public var glOffs: SizeT,
    public var glFlags: CInt,
    unused1CtorArg: MutPtr,
    unused2CtorArg: MutPtr,
    unused3CtorArg: MutPtr,
    unused4CtorArg: MutPtr,
    unused5CtorArg: MutPtr,
) {
    public var glPathv: MutPtr = glPathvCtorArg
        internal set

    public var unused1: MutPtr = unused1CtorArg
        internal set

    public var unused2: MutPtr = unused2CtorArg
        internal set

    public var unused3: MutPtr = unused3CtorArg
        internal set

    public var unused4: MutPtr = unused4CtorArg
        internal set

    public var unused5: MutPtr = unused5CtorArg
        internal set

    public open fun clone(): GlobT = GlobT(glPathc, glPathv, glOffs, glFlags, unused1, unused2, unused3, unused4, unused5)

    public companion object {
        public fun typeLayout(): TypeLayout<GlobT> =
            object : TypeLayout<GlobT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 72L
                override fun readNative(addr: Long): GlobT =
                    GlobT(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                    )
                override fun writeNative(addr: Long, value: GlobT) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.glPathc)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.glPathv)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.glOffs)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.glFlags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.unused1)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.unused2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.unused3)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.unused4)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.unused5)
                }
                override fun readBytes(buf: ByteArray, offset: Int): GlobT =
                    GlobT(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: GlobT) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.glPathc)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.glPathv)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.glOffs)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.glFlags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.unused1)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.unused2)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.unused3)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.unused4)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.unused5)
                }
            }

        public operator fun invoke(
            glPathc: SizeT,
            glPathv: MutPtr,
            glOffs: SizeT,
            glFlags: CInt,
            unused1: MutPtr,
            unused2: MutPtr,
            unused3: MutPtr,
            unused4: MutPtr,
            unused5: MutPtr,
        ): GlobT = GlobT(
            glPathc = glPathc,
            glPathvCtorArg = glPathv,
            glOffs = glOffs,
            glFlags = glFlags,
            unused1CtorArg = unused1,
            unused2CtorArg = unused2,
            unused3CtorArg = unused3,
            unused4CtorArg = unused4,
            unused5CtorArg = unused5,
        )
    }
}

public open class Passwd internal constructor(
    pwNameCtorArg: MutPtr,
    pwPasswdCtorArg: MutPtr,
    public var pwUid: UidT,
    public var pwGid: GidT,
    pwGecosCtorArg: MutPtr,
    pwDirCtorArg: MutPtr,
    pwShellCtorArg: MutPtr,
) {
    public var pwName: MutPtr = pwNameCtorArg
        internal set

    public var pwPasswd: MutPtr = pwPasswdCtorArg
        internal set

    public var pwGecos: MutPtr = pwGecosCtorArg
        internal set

    public var pwDir: MutPtr = pwDirCtorArg
        internal set

    public var pwShell: MutPtr = pwShellCtorArg
        internal set

    public open fun clone(): Passwd = Passwd(pwName, pwPasswd, pwUid, pwGid, pwGecos, pwDir, pwShell)

    public companion object {
        public fun typeLayout(): TypeLayout<Passwd> =
            object : TypeLayout<Passwd> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Passwd =
                    Passwd(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: Passwd) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.pwName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.pwPasswd)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.pwUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.pwGid)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.pwGecos)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.pwDir)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.pwShell)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Passwd =
                    Passwd(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Passwd) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.pwName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.pwPasswd)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.pwUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.pwGid)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.pwGecos)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.pwDir)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.pwShell)
                }
            }

        public operator fun invoke(
            pwName: MutPtr,
            pwPasswd: MutPtr,
            pwUid: UidT,
            pwGid: GidT,
            pwGecos: MutPtr,
            pwDir: MutPtr,
            pwShell: MutPtr,
        ): Passwd = Passwd(
            pwNameCtorArg = pwName,
            pwPasswdCtorArg = pwPasswd,
            pwUid = pwUid,
            pwGid = pwGid,
            pwGecosCtorArg = pwGecos,
            pwDirCtorArg = pwDir,
            pwShellCtorArg = pwShell,
        )
    }
}

public open class Spwd internal constructor(
    spNampCtorArg: MutPtr,
    spPwdpCtorArg: MutPtr,
    public var spLstchg: CLong,
    public var spMin: CLong,
    public var spMax: CLong,
    public var spWarn: CLong,
    public var spInact: CLong,
    public var spExpire: CLong,
    public var spFlag: CUlong,
) {
    public var spNamp: MutPtr = spNampCtorArg
        internal set

    public var spPwdp: MutPtr = spPwdpCtorArg
        internal set

    public open fun clone(): Spwd = Spwd(spNamp, spPwdp, spLstchg, spMin, spMax, spWarn, spInact, spExpire, spFlag)

    public companion object {
        public fun typeLayout(): TypeLayout<Spwd> =
            object : TypeLayout<Spwd> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 72L
                override fun readNative(addr: Long): Spwd =
                    Spwd(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                        (PrimitiveLayouts.I64).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.I64).readNative(addr + 48L),
                        (PrimitiveLayouts.I64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                    )
                override fun writeNative(addr: Long, value: Spwd) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.spNamp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.spPwdp)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.spLstchg)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.spMin)
                    (PrimitiveLayouts.I64).writeNative(addr + 32L, value.spMax)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.spWarn)
                    (PrimitiveLayouts.I64).writeNative(addr + 48L, value.spInact)
                    (PrimitiveLayouts.I64).writeNative(addr + 56L, value.spExpire)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.spFlag)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Spwd =
                    Spwd(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Spwd) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.spNamp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.spPwdp)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.spLstchg)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.spMin)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 32, value.spMax)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.spWarn)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 48, value.spInact)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 56, value.spExpire)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.spFlag)
                }
            }

        public operator fun invoke(
            spNamp: MutPtr,
            spPwdp: MutPtr,
            spLstchg: CLong,
            spMin: CLong,
            spMax: CLong,
            spWarn: CLong,
            spInact: CLong,
            spExpire: CLong,
            spFlag: CUlong,
        ): Spwd = Spwd(
            spNampCtorArg = spNamp,
            spPwdpCtorArg = spPwdp,
            spLstchg = spLstchg,
            spMin = spMin,
            spMax = spMax,
            spWarn = spWarn,
            spInact = spInact,
            spExpire = spExpire,
            spFlag = spFlag,
        )
    }
}

public open class Itimerspec(
    public var itInterval: Timespec,
    public var itValue: Timespec,
) {
    public open fun clone(): Itimerspec = Itimerspec(itInterval, itValue)
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

public open class Sembuf(
    public var semNum: CUshort,
    public var semOp: CShort,
    public var semFlg: CShort,
) {
    public open fun clone(): Sembuf = Sembuf(semNum, semOp, semFlg)

    public companion object {
        public fun typeLayout(): TypeLayout<Sembuf> =
            object : TypeLayout<Sembuf> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 6L
                override fun readNative(addr: Long): Sembuf =
                    Sembuf(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.I16).readNative(addr + 2L),
                        (PrimitiveLayouts.I16).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: Sembuf) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.semNum)
                    (PrimitiveLayouts.I16).writeNative(addr + 2L, value.semOp)
                    (PrimitiveLayouts.I16).writeNative(addr + 4L, value.semFlg)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sembuf =
                    Sembuf(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I16).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sembuf) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.semNum)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 2, value.semOp)
                    (PrimitiveLayouts.I16).writeBytes(buf, offset + 4, value.semFlg)
                }
            }
    }
}

public open class DlPhdrInfo internal constructor(
    public var dlpiAddr: Elf64Addr,
    dlpiNameCtorArg: ConstPtr,
    dlpiPhdrCtorArg: ConstPtr,
    public var dlpiPhnum: Elf64Half,
    // As of uClibc 1.0.36, the following fields are
    // gated behind a "#if 0" block which always evaluates
    // to false. So I'm just removing these, and if uClibc changes
    // the #if block in the future to include the following fields, these
    // will probably need including here. tsidea, skrap
    // QNX (NTO) platform does not define these fields
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

public open class Elf32Ehdr(
    public var eIdent: UByteArray,
    public var eType: Elf32Half,
    public var eMachine: Elf32Half,
    public var eVersion: Elf32Word,
    public var eEntry: Elf32Addr,
    public var ePhoff: Elf32Off,
    public var eShoff: Elf32Off,
    public var eFlags: Elf32Word,
    public var eEhsize: Elf32Half,
    public var ePhentsize: Elf32Half,
    public var ePhnum: Elf32Half,
    public var eShentsize: Elf32Half,
    public var eShnum: Elf32Half,
    public var eShstrndx: Elf32Half,
) {
    public open fun clone(): Elf32Ehdr = Elf32Ehdr(eIdent, eType, eMachine, eVersion, eEntry, ePhoff, eShoff, eFlags, eEhsize, ePhentsize, ePhnum, eShentsize, eShnum, eShstrndx)

    public companion object {
        public fun typeLayout(): TypeLayout<Elf32Ehdr> =
            object : TypeLayout<Elf32Ehdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 52L
                override fun readNative(addr: Long): Elf32Ehdr =
                    Elf32Ehdr(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 18L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                        (PrimitiveLayouts.U16).readNative(addr + 40L),
                        (PrimitiveLayouts.U16).readNative(addr + 42L),
                        (PrimitiveLayouts.U16).readNative(addr + 44L),
                        (PrimitiveLayouts.U16).readNative(addr + 46L),
                        (PrimitiveLayouts.U16).readNative(addr + 48L),
                        (PrimitiveLayouts.U16).readNative(addr + 50L),
                    )
                override fun writeNative(addr: Long, value: Elf32Ehdr) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.eIdent)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.eType)
                    (PrimitiveLayouts.U16).writeNative(addr + 18L, value.eMachine)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.eVersion)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.eEntry)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.ePhoff)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.eShoff)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.eFlags)
                    (PrimitiveLayouts.U16).writeNative(addr + 40L, value.eEhsize)
                    (PrimitiveLayouts.U16).writeNative(addr + 42L, value.ePhentsize)
                    (PrimitiveLayouts.U16).writeNative(addr + 44L, value.ePhnum)
                    (PrimitiveLayouts.U16).writeNative(addr + 46L, value.eShentsize)
                    (PrimitiveLayouts.U16).writeNative(addr + 48L, value.eShnum)
                    (PrimitiveLayouts.U16).writeNative(addr + 50L, value.eShstrndx)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Elf32Ehdr =
                    Elf32Ehdr(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 18),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 42),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 46),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 50),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Elf32Ehdr) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.eIdent)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.eType)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 18, value.eMachine)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.eVersion)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.eEntry)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.ePhoff)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.eShoff)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.eFlags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 40, value.eEhsize)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 42, value.ePhentsize)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 44, value.ePhnum)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 46, value.eShentsize)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 48, value.eShnum)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 50, value.eShstrndx)
                }
            }
    }
}

public open class Elf64Ehdr(
    public var eIdent: UByteArray,
    public var eType: Elf64Half,
    public var eMachine: Elf64Half,
    public var eVersion: Elf64Word,
    public var eEntry: Elf64Addr,
    public var ePhoff: Elf64Off,
    public var eShoff: Elf64Off,
    public var eFlags: Elf64Word,
    public var eEhsize: Elf64Half,
    public var ePhentsize: Elf64Half,
    public var ePhnum: Elf64Half,
    public var eShentsize: Elf64Half,
    public var eShnum: Elf64Half,
    public var eShstrndx: Elf64Half,
) {
    public open fun clone(): Elf64Ehdr = Elf64Ehdr(eIdent, eType, eMachine, eVersion, eEntry, ePhoff, eShoff, eFlags, eEhsize, ePhentsize, ePhnum, eShentsize, eShnum, eShstrndx)

    public companion object {
        public fun typeLayout(): TypeLayout<Elf64Ehdr> =
            object : TypeLayout<Elf64Ehdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Elf64Ehdr =
                    Elf64Ehdr(
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 16L),
                        (PrimitiveLayouts.U16).readNative(addr + 18L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U32).readNative(addr + 48L),
                        (PrimitiveLayouts.U16).readNative(addr + 52L),
                        (PrimitiveLayouts.U16).readNative(addr + 54L),
                        (PrimitiveLayouts.U16).readNative(addr + 56L),
                        (PrimitiveLayouts.U16).readNative(addr + 58L),
                        (PrimitiveLayouts.U16).readNative(addr + 60L),
                        (PrimitiveLayouts.U16).readNative(addr + 62L),
                    )
                override fun writeNative(addr: Long, value: Elf64Ehdr) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr, value.eIdent)
                    (PrimitiveLayouts.U16).writeNative(addr + 16L, value.eType)
                    (PrimitiveLayouts.U16).writeNative(addr + 18L, value.eMachine)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.eVersion)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.eEntry)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.ePhoff)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.eShoff)
                    (PrimitiveLayouts.U32).writeNative(addr + 48L, value.eFlags)
                    (PrimitiveLayouts.U16).writeNative(addr + 52L, value.eEhsize)
                    (PrimitiveLayouts.U16).writeNative(addr + 54L, value.ePhentsize)
                    (PrimitiveLayouts.U16).writeNative(addr + 56L, value.ePhnum)
                    (PrimitiveLayouts.U16).writeNative(addr + 58L, value.eShentsize)
                    (PrimitiveLayouts.U16).writeNative(addr + 60L, value.eShnum)
                    (PrimitiveLayouts.U16).writeNative(addr + 62L, value.eShstrndx)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Elf64Ehdr =
                    Elf64Ehdr(
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 18),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 52),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 54),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 58),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 60),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 62),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Elf64Ehdr) {
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset, value.eIdent)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 16, value.eType)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 18, value.eMachine)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.eVersion)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.eEntry)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.ePhoff)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.eShoff)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 48, value.eFlags)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 52, value.eEhsize)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 54, value.ePhentsize)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 56, value.ePhnum)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 58, value.eShentsize)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 60, value.eShnum)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 62, value.eShstrndx)
                }
            }
    }
}

public open class Elf32Sym(
    public var stName: Elf32Word,
    public var stValue: Elf32Addr,
    public var stSize: Elf32Word,
    public var stInfo: CUchar,
    public var stOther: CUchar,
    public var stShndx: Elf32Section,
) {
    public open fun clone(): Elf32Sym = Elf32Sym(stName, stValue, stSize, stInfo, stOther, stShndx)

    public companion object {
        public fun typeLayout(): TypeLayout<Elf32Sym> =
            object : TypeLayout<Elf32Sym> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Elf32Sym =
                    Elf32Sym(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U8).readNative(addr + 12L),
                        (PrimitiveLayouts.U8).readNative(addr + 13L),
                        (PrimitiveLayouts.U16).readNative(addr + 14L),
                    )
                override fun writeNative(addr: Long, value: Elf32Sym) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.stName)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.stValue)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.stSize)
                    (PrimitiveLayouts.U8).writeNative(addr + 12L, value.stInfo)
                    (PrimitiveLayouts.U8).writeNative(addr + 13L, value.stOther)
                    (PrimitiveLayouts.U16).writeNative(addr + 14L, value.stShndx)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Elf32Sym =
                    Elf32Sym(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 13),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 14),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Elf32Sym) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.stName)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.stValue)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.stSize)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 12, value.stInfo)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 13, value.stOther)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 14, value.stShndx)
                }
            }
    }
}

public open class Elf64Sym(
    public var stName: Elf64Word,
    public var stInfo: CUchar,
    public var stOther: CUchar,
    public var stShndx: Elf64Section,
    public var stValue: Elf64Addr,
    public var stSize: Elf64Xword,
) {
    public open fun clone(): Elf64Sym = Elf64Sym(stName, stInfo, stOther, stShndx, stValue, stSize)

    public companion object {
        public fun typeLayout(): TypeLayout<Elf64Sym> =
            object : TypeLayout<Elf64Sym> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): Elf64Sym =
                    Elf64Sym(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Elf64Sym) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.stName)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.stInfo)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.stOther)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.stShndx)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stValue)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stSize)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Elf64Sym =
                    Elf64Sym(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Elf64Sym) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.stName)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.stInfo)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.stOther)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.stShndx)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stValue)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stSize)
                }
            }
    }
}

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

public open class Elf32Shdr(
    public var shName: Elf32Word,
    public var shType: Elf32Word,
    public var shFlags: Elf32Word,
    public var shAddr: Elf32Addr,
    public var shOffset: Elf32Off,
    public var shSize: Elf32Word,
    public var shLink: Elf32Word,
    public var shInfo: Elf32Word,
    public var shAddralign: Elf32Word,
    public var shEntsize: Elf32Word,
) {
    public open fun clone(): Elf32Shdr = Elf32Shdr(shName, shType, shFlags, shAddr, shOffset, shSize, shLink, shInfo, shAddralign, shEntsize)

    public companion object {
        public fun typeLayout(): TypeLayout<Elf32Shdr> =
            object : TypeLayout<Elf32Shdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): Elf32Shdr =
                    Elf32Shdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U32).readNative(addr + 28L),
                        (PrimitiveLayouts.U32).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 36L),
                    )
                override fun writeNative(addr: Long, value: Elf32Shdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.shName)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.shType)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.shFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.shAddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.shOffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.shSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.shLink)
                    (PrimitiveLayouts.U32).writeNative(addr + 28L, value.shInfo)
                    (PrimitiveLayouts.U32).writeNative(addr + 32L, value.shAddralign)
                    (PrimitiveLayouts.U32).writeNative(addr + 36L, value.shEntsize)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Elf32Shdr =
                    Elf32Shdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 36),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Elf32Shdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.shName)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.shType)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.shFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.shAddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.shOffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.shSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.shLink)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 28, value.shInfo)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 32, value.shAddralign)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 36, value.shEntsize)
                }
            }
    }
}

public open class Elf64Shdr(
    public var shName: Elf64Word,
    public var shType: Elf64Word,
    public var shFlags: Elf64Xword,
    public var shAddr: Elf64Addr,
    public var shOffset: Elf64Off,
    public var shSize: Elf64Xword,
    public var shLink: Elf64Word,
    public var shInfo: Elf64Word,
    public var shAddralign: Elf64Xword,
    public var shEntsize: Elf64Xword,
) {
    public open fun clone(): Elf64Shdr = Elf64Shdr(shName, shType, shFlags, shAddr, shOffset, shSize, shLink, shInfo, shAddralign, shEntsize)

    public companion object {
        public fun typeLayout(): TypeLayout<Elf64Shdr> =
            object : TypeLayout<Elf64Shdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Elf64Shdr =
                    Elf64Shdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveLayouts.U32).readNative(addr + 44L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: Elf64Shdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.shName)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.shType)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.shFlags)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.shAddr)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.shOffset)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.shSize)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.shLink)
                    (PrimitiveLayouts.U32).writeNative(addr + 44L, value.shInfo)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.shAddralign)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.shEntsize)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Elf64Shdr =
                    Elf64Shdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Elf64Shdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.shName)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.shType)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.shFlags)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.shAddr)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.shOffset)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.shSize)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.shLink)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 44, value.shInfo)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.shAddralign)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.shEntsize)
                }
            }
    }
}

public open class CAnonymousElf32Rel(
    public var rOffset: Elf32Addr,
    public var rInfo: Elf32Word,
) {
    public open fun clone(): CAnonymousElf32Rel = CAnonymousElf32Rel(rOffset, rInfo)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousElf32Rel> =
            object : TypeLayout<CAnonymousElf32Rel> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): CAnonymousElf32Rel =
                    CAnonymousElf32Rel(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousElf32Rel) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.rOffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.rInfo)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousElf32Rel =
                    CAnonymousElf32Rel(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousElf32Rel) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.rOffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.rInfo)
                }
            }
    }
}

public open class CAnonymousElf64Rel(
    public var rOffset: Elf64Addr,
    public var rInfo: Elf64Xword,
) {
    public open fun clone(): CAnonymousElf64Rel = CAnonymousElf64Rel(rOffset, rInfo)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousElf64Rel> =
            object : TypeLayout<CAnonymousElf64Rel> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): CAnonymousElf64Rel =
                    CAnonymousElf64Rel(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousElf64Rel) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.rOffset)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rInfo)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousElf64Rel =
                    CAnonymousElf64Rel(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousElf64Rel) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.rOffset)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rInfo)
                }
            }
    }
}

public open class Ucred(
    public var pid: PidT,
    public var uid: UidT,
    public var gid: GidT,
) {
    public open fun clone(): Ucred = Ucred(pid, uid, gid)

    public companion object {
        public fun typeLayout(): TypeLayout<Ucred> =
            object : TypeLayout<Ucred> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): Ucred =
                    Ucred(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Ucred) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.pid)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.uid)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.gid)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ucred =
                    Ucred(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ucred) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.pid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.uid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.gid)
                }
            }
    }
}

public open class Mntent internal constructor(
    mntFsnameCtorArg: MutPtr,
    mntDirCtorArg: MutPtr,
    mntTypeCtorArg: MutPtr,
    mntOptsCtorArg: MutPtr,
    public var mntFreq: CInt,
    public var mntPassno: CInt,
) {
    public var mntFsname: MutPtr = mntFsnameCtorArg
        internal set

    public var mntDir: MutPtr = mntDirCtorArg
        internal set

    public var mntType: MutPtr = mntTypeCtorArg
        internal set

    public var mntOpts: MutPtr = mntOptsCtorArg
        internal set

    public open fun clone(): Mntent = Mntent(mntFsname, mntDir, mntType, mntOpts, mntFreq, mntPassno)

    public companion object {
        public fun typeLayout(): TypeLayout<Mntent> =
            object : TypeLayout<Mntent> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): Mntent =
                    Mntent(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                    )
                override fun writeNative(addr: Long, value: Mntent) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.mntFsname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.mntDir)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.mntType)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.mntOpts)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.mntFreq)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.mntPassno)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Mntent =
                    Mntent(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Mntent) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.mntFsname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.mntDir)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.mntType)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.mntOpts)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.mntFreq)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.mntPassno)
                }
            }

        public operator fun invoke(
            mntFsname: MutPtr,
            mntDir: MutPtr,
            mntType: MutPtr,
            mntOpts: MutPtr,
            mntFreq: CInt,
            mntPassno: CInt,
        ): Mntent = Mntent(
            mntFsnameCtorArg = mntFsname,
            mntDirCtorArg = mntDir,
            mntTypeCtorArg = mntType,
            mntOptsCtorArg = mntOpts,
            mntFreq = mntFreq,
            mntPassno = mntPassno,
        )
    }
}

public open class In6Pktinfo(
    public var ipi6Addr: In6Addr,
    public var ipi6Ifindex: CUint,
) {
    public open fun clone(): In6Pktinfo = In6Pktinfo(ipi6Addr, ipi6Ifindex)
}

public open class ArpdRequest(
    public var req: CUshort,
    public var ip: UInt,
    public var dev: CUlong,
    public var stamp: CUlong,
    public var updated: CUlong,
    public var ha: UByteArray,
) {
    public open fun clone(): ArpdRequest = ArpdRequest(req, ip, dev, stamp, updated, ha)

    public companion object {
        public fun typeLayout(): TypeLayout<ArpdRequest> =
            object : TypeLayout<ArpdRequest> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((32L + ((MAX_ADDR_LEN).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override fun readNative(addr: Long): ArpdRequest =
                    ArpdRequest(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveArrayLayouts.uByteArray((MAX_ADDR_LEN).toInt())).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: ArpdRequest) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.req)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.ip)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.dev)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stamp)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.updated)
                    (PrimitiveArrayLayouts.uByteArray((MAX_ADDR_LEN).toInt())).writeNative(addr + 32L, value.ha)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ArpdRequest =
                    ArpdRequest(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveArrayLayouts.uByteArray((MAX_ADDR_LEN).toInt())).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ArpdRequest) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.req)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.ip)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.dev)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stamp)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.updated)
                    (PrimitiveArrayLayouts.uByteArray((MAX_ADDR_LEN).toInt())).writeBytes(buf, offset + 32, value.ha)
                }
            }
    }
}

public open class RegmatchT(
    public var rmSo: RegoffT,
    public var rmEo: RegoffT,
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

// Note(1.0,deprecate): lfs binding to be removed
public open class Rlimit64(
    public var rlimCur: Rlim64T,
    public var rlimMax: Rlim64T,
) {
    public open fun clone(): Rlimit64 = Rlimit64(rlimCur, rlimMax)

    public companion object {
        public fun typeLayout(): TypeLayout<Rlimit64> =
            object : TypeLayout<Rlimit64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Rlimit64 =
                    Rlimit64(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Rlimit64) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.rlimCur)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rlimMax)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Rlimit64 =
                    Rlimit64(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Rlimit64) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.rlimCur)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rlimMax)
                }
            }
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

public open class Dirent(
    public var dIno: InoT,
    public var dOff: OffT,
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
    public var dIno: Ino64T,
    public var dOff: Off64T,
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

public open class CAnonymousElf32Rela(
    public var rOffset: Elf32Addr,
    public var rInfo: Elf32Word,
    public var rAddend: Elf32Sword,
) {
    public open fun clone(): CAnonymousElf32Rela = CAnonymousElf32Rela(rOffset, rInfo, rAddend)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousElf32Rela> =
            object : TypeLayout<CAnonymousElf32Rela> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): CAnonymousElf32Rela =
                    CAnonymousElf32Rela(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousElf32Rela) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.rOffset)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.rInfo)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.rAddend)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousElf32Rela =
                    CAnonymousElf32Rela(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousElf32Rela) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.rOffset)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.rInfo)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.rAddend)
                }
            }
    }
}

public open class CAnonymousElf64Rela(
    public var rOffset: Elf64Addr,
    public var rInfo: Elf64Xword,
    public var rAddend: Elf64Sxword,
) {
    public open fun clone(): CAnonymousElf64Rela = CAnonymousElf64Rela(rOffset, rInfo, rAddend)

    public companion object {
        public fun typeLayout(): TypeLayout<CAnonymousElf64Rela> =
            object : TypeLayout<CAnonymousElf64Rela> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): CAnonymousElf64Rela =
                    CAnonymousElf64Rela(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.I64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: CAnonymousElf64Rela) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.rOffset)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.rInfo)
                    (PrimitiveLayouts.I64).writeNative(addr + 16L, value.rAddend)
                }
                override fun readBytes(buf: ByteArray, offset: Int): CAnonymousElf64Rela =
                    CAnonymousElf64Rela(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: CAnonymousElf64Rela) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.rOffset)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.rInfo)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 16, value.rAddend)
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

/**
 * Structure used in SIOCGIFCONF request.  Used to retrieve interface configuration for
 * machine (useful for programs which must know all networks accessible).
 */
public open class Ifconf(
    /**
     * Size of buffer
     */
    public var ifcLen: CInt,
    public var ifcIfcu: CAnonymousIfcIfcu,
) {
    public open fun clone(): Ifconf = Ifconf(ifcLen, ifcIfcu)
}

public val DATE_BASE: NlItem = 0x20000

public val ABDAY_1: NlItem = DATE_BASE

public val ABDAY_2: NlItem = DATE_BASE + 0x1

public val ABDAY_3: NlItem = DATE_BASE + 0x2

public val ABDAY_4: NlItem = DATE_BASE + 0x3

public val ABDAY_5: NlItem = DATE_BASE + 0x4

public val ABDAY_6: NlItem = DATE_BASE + 0x5

public val ABDAY_7: NlItem = DATE_BASE + 0x6

public val DAY_1: NlItem = DATE_BASE + 0x7

public val DAY_2: NlItem = DATE_BASE + 0x8

public val DAY_3: NlItem = DATE_BASE + 0x9

public val DAY_4: NlItem = DATE_BASE + 0xA

public val DAY_5: NlItem = DATE_BASE + 0xB

public val DAY_6: NlItem = DATE_BASE + 0xC

public val DAY_7: NlItem = DATE_BASE + 0xD

public val ABMON_1: NlItem = DATE_BASE + 0xE

public val ABMON_2: NlItem = DATE_BASE + 0xF

public val ABMON_3: NlItem = DATE_BASE + 0x10

public val ABMON_4: NlItem = DATE_BASE + 0x11

public val ABMON_5: NlItem = DATE_BASE + 0x12

public val ABMON_6: NlItem = DATE_BASE + 0x13

public val ABMON_7: NlItem = DATE_BASE + 0x14

public val ABMON_8: NlItem = DATE_BASE + 0x15

public val ABMON_9: NlItem = DATE_BASE + 0x16

public val ABMON_10: NlItem = DATE_BASE + 0x17

public val ABMON_11: NlItem = DATE_BASE + 0x18

public val ABMON_12: NlItem = DATE_BASE + 0x19

public val MON_1: NlItem = DATE_BASE + 0x1A

public val MON_2: NlItem = DATE_BASE + 0x1B

public val MON_3: NlItem = DATE_BASE + 0x1C

public val MON_4: NlItem = DATE_BASE + 0x1D

public val MON_5: NlItem = DATE_BASE + 0x1E

public val MON_6: NlItem = DATE_BASE + 0x1F

public val MON_7: NlItem = DATE_BASE + 0x20

public val MON_8: NlItem = DATE_BASE + 0x21

public val MON_9: NlItem = DATE_BASE + 0x22

public val MON_10: NlItem = DATE_BASE + 0x23

public val MON_11: NlItem = DATE_BASE + 0x24

public val MON_12: NlItem = DATE_BASE + 0x25

public val AM_STR: NlItem = DATE_BASE + 0x26

public val PM_STR: NlItem = DATE_BASE + 0x27

public val D_T_FMT: NlItem = DATE_BASE + 0x28

public val D_FMT: NlItem = DATE_BASE + 0x29

public val T_FMT: NlItem = DATE_BASE + 0x2A

public val T_FMT_AMPM: NlItem = DATE_BASE + 0x2B

public val ERA: NlItem = DATE_BASE + 0x2C

public val ERA_D_FMT: NlItem = DATE_BASE + 0x2E

public val ALT_DIGITS: NlItem = DATE_BASE + 0x2F

public val ERA_D_T_FMT: NlItem = DATE_BASE + 0x30

public val ERA_T_FMT: NlItem = DATE_BASE + 0x31

public val CODESET: NlItem = 14

public val CRNCYSTR: NlItem = 0x4000F

public val RADIXCHAR: NlItem = 0x10000

public val THOUSEP: NlItem = 0x10001

public val YESEXPR: NlItem = 0x50000

public val NOEXPR: NlItem = 0x50001

public val YESSTR: NlItem = 0x50002

public val NOSTR: NlItem = 0x50003

public val RUSAGE_CHILDREN: CInt = -1

public val L_TMPNAM: CUint = 20u

public val PC_LINK_MAX: CInt = 0

public val PC_MAX_CANON: CInt = 1

public val PC_MAX_INPUT: CInt = 2

public val PC_NAME_MAX: CInt = 3

public val PC_PATH_MAX: CInt = 4

public val PC_PIPE_BUF: CInt = 5

public val PC_CHOWN_RESTRICTED: CInt = 6

public val PC_NO_TRUNC: CInt = 7

public val PC_VDISABLE: CInt = 8

public val PC_SYNC_IO: CInt = 9

public val PC_ASYNC_IO: CInt = 10

public val PC_PRIO_IO: CInt = 11

public val PC_SOCK_MAXBUF: CInt = 12

public val PC_FILESIZEBITS: CInt = 13

public val PC_REC_INCR_XFER_SIZE: CInt = 14

public val PC_REC_MAX_XFER_SIZE: CInt = 15

public val PC_REC_MIN_XFER_SIZE: CInt = 16

public val PC_REC_XFER_ALIGN: CInt = 17

public val PC_ALLOC_SIZE_MIN: CInt = 18

public val PC_SYMLINK_MAX: CInt = 19

public val PC_2_SYMLINKS: CInt = 20

public val SC_ARG_MAX: CInt = 0

public val SC_CHILD_MAX: CInt = 1

public val SC_CLK_TCK: CInt = 2

public val SC_NGROUPS_MAX: CInt = 3

public val SC_OPEN_MAX: CInt = 4

public val SC_STREAM_MAX: CInt = 5

public val SC_TZNAME_MAX: CInt = 6

public val SC_JOB_CONTROL: CInt = 7

public val SC_SAVED_IDS: CInt = 8

public val SC_REALTIME_SIGNALS: CInt = 9

public val SC_PRIORITY_SCHEDULING: CInt = 10

public val SC_TIMERS: CInt = 11

public val SC_ASYNCHRONOUS_IO: CInt = 12

public val SC_PRIORITIZED_IO: CInt = 13

public val SC_SYNCHRONIZED_IO: CInt = 14

public val SC_FSYNC: CInt = 15

public val SC_MAPPED_FILES: CInt = 16

public val SC_MEMLOCK: CInt = 17

public val SC_MEMLOCK_RANGE: CInt = 18

public val SC_MEMORY_PROTECTION: CInt = 19

public val SC_MESSAGE_PASSING: CInt = 20

public val SC_SEMAPHORES: CInt = 21

public val SC_SHARED_MEMORY_OBJECTS: CInt = 22

public val SC_AIO_LISTIO_MAX: CInt = 23

public val SC_AIO_MAX: CInt = 24

public val SC_AIO_PRIO_DELTA_MAX: CInt = 25

public val SC_DELAYTIMER_MAX: CInt = 26

public val SC_MQ_OPEN_MAX: CInt = 27

public val SC_MQ_PRIO_MAX: CInt = 28

public val SC_VERSION: CInt = 29

public val SC_PAGESIZE: CInt = 30

public val SC_PAGE_SIZE: CInt = SC_PAGESIZE

public val SC_RTSIG_MAX: CInt = 31

public val SC_SEM_NSEMS_MAX: CInt = 32

public val SC_SEM_VALUE_MAX: CInt = 33

public val SC_SIGQUEUE_MAX: CInt = 34

public val SC_TIMER_MAX: CInt = 35

public val SC_BC_BASE_MAX: CInt = 36

public val SC_BC_DIM_MAX: CInt = 37

public val SC_BC_SCALE_MAX: CInt = 38

public val SC_BC_STRING_MAX: CInt = 39

public val SC_COLL_WEIGHTS_MAX: CInt = 40

public val SC_EXPR_NEST_MAX: CInt = 42

public val SC_LINE_MAX: CInt = 43

public val SC_RE_DUP_MAX: CInt = 44

public val SC_2_VERSION: CInt = 46

public val SC_2_C_BIND: CInt = 47

public val SC_2_C_DEV: CInt = 48

public val SC_2_FORT_DEV: CInt = 49

public val SC_2_FORT_RUN: CInt = 50

public val SC_2_SW_DEV: CInt = 51

public val SC_2_LOCALEDEF: CInt = 52

public val SC_UIO_MAXIOV: CInt = 60

public val SC_IOV_MAX: CInt = 60

public val SC_THREADS: CInt = 67

public val SC_THREAD_SAFE_FUNCTIONS: CInt = 68

public val SC_GETGR_R_SIZE_MAX: CInt = 69

public val SC_GETPW_R_SIZE_MAX: CInt = 70

public val SC_LOGIN_NAME_MAX: CInt = 71

public val SC_TTY_NAME_MAX: CInt = 72

public val SC_THREAD_DESTRUCTOR_ITERATIONS: CInt = 73

public val SC_THREAD_KEYS_MAX: CInt = 74

public val SC_THREAD_STACK_MIN: CInt = 75

public val SC_THREAD_THREADS_MAX: CInt = 76

public val SC_THREAD_ATTR_STACKADDR: CInt = 77

public val SC_THREAD_ATTR_STACKSIZE: CInt = 78

public val SC_THREAD_PRIORITY_SCHEDULING: CInt = 79

public val SC_THREAD_PRIO_INHERIT: CInt = 80

public val SC_THREAD_PRIO_PROTECT: CInt = 81

public val SC_THREAD_PROCESS_SHARED: CInt = 82

public val SC_NPROCESSORS_CONF: CInt = 83

public val SC_NPROCESSORS_ONLN: CInt = 84

public val SC_PHYS_PAGES: CInt = 85

public val SC_AVPHYS_PAGES: CInt = 86

public val SC_ATEXIT_MAX: CInt = 87

public val SC_PASS_MAX: CInt = 88

public val SC_XOPEN_VERSION: CInt = 89

public val SC_XOPEN_XCU_VERSION: CInt = 90

public val SC_XOPEN_UNIX: CInt = 91

public val SC_XOPEN_CRYPT: CInt = 92

public val SC_XOPEN_ENH_I18N: CInt = 93

public val SC_XOPEN_SHM: CInt = 94

public val SC_2_CHAR_TERM: CInt = 95

public val SC_2_UPE: CInt = 97

public val SC_XOPEN_XPG2: CInt = 98

public val SC_XOPEN_XPG3: CInt = 99

public val SC_XOPEN_XPG4: CInt = 100

public val SC_NZERO: CInt = 109

public val SC_XBS5_ILP32_OFF32: CInt = 125

public val SC_XBS5_ILP32_OFFBIG: CInt = 126

public val SC_XBS5_LP64_OFF64: CInt = 127

public val SC_XBS5_LPBIG_OFFBIG: CInt = 128

public val SC_XOPEN_LEGACY: CInt = 129

public val SC_XOPEN_REALTIME: CInt = 130

public val SC_XOPEN_REALTIME_THREADS: CInt = 131

public val SC_ADVISORY_INFO: CInt = 132

public val SC_BARRIERS: CInt = 133

public val SC_CLOCK_SELECTION: CInt = 137

public val SC_CPUTIME: CInt = 138

public val SC_THREAD_CPUTIME: CInt = 139

public val SC_MONOTONIC_CLOCK: CInt = 149

public val SC_READER_WRITER_LOCKS: CInt = 153

public val SC_SPIN_LOCKS: CInt = 154

public val SC_REGEXP: CInt = 155

public val SC_SHELL: CInt = 157

public val SC_SPAWN: CInt = 159

public val SC_SPORADIC_SERVER: CInt = 160

public val SC_THREAD_SPORADIC_SERVER: CInt = 161

public val SC_TIMEOUTS: CInt = 164

public val SC_TYPED_MEMORY_OBJECTS: CInt = 165

public val SC_2_PBS: CInt = 168

public val SC_2_PBS_ACCOUNTING: CInt = 169

public val SC_2_PBS_LOCATE: CInt = 170

public val SC_2_PBS_MESSAGE: CInt = 171

public val SC_2_PBS_TRACK: CInt = 172

public val SC_SYMLOOP_MAX: CInt = 173

public val SC_STREAMS: CInt = 174

public val SC_2_PBS_CHECKPOINT: CInt = 175

public val SC_V6_ILP32_OFF32: CInt = 176

public val SC_V6_ILP32_OFFBIG: CInt = 177

public val SC_V6_LP64_OFF64: CInt = 178

public val SC_V6_LPBIG_OFFBIG: CInt = 179

public val SC_HOST_NAME_MAX: CInt = 180

public val SC_TRACE: CInt = 181

public val SC_TRACE_EVENT_FILTER: CInt = 182

public val SC_TRACE_INHERIT: CInt = 183

public val SC_TRACE_LOG: CInt = 184

public val SC_IPV6: CInt = 235

public val SC_RAW_SOCKETS: CInt = 236

public val SC_V7_ILP32_OFF32: CInt = 237

public val SC_V7_ILP32_OFFBIG: CInt = 238

public val SC_V7_LP64_OFF64: CInt = 239

public val SC_V7_LPBIG_OFFBIG: CInt = 240

public val SC_SS_REPL_MAX: CInt = 241

public val SC_TRACE_EVENT_NAME_MAX: CInt = 242

public val SC_TRACE_NAME_MAX: CInt = 243

public val SC_TRACE_SYS_MAX: CInt = 244

public val SC_TRACE_USER_EVENT_MAX: CInt = 245

public val SC_XOPEN_STREAMS: CInt = 246

public val SC_THREAD_ROBUST_PRIO_INHERIT: CInt = 247

public val SC_THREAD_ROBUST_PRIO_PROTECT: CInt = 248

public val CS_PATH: CInt = 0

public val CS_POSIX_V6_WIDTH_RESTRICTED_ENVS: CInt = 1

public val CS_POSIX_V5_WIDTH_RESTRICTED_ENVS: CInt = 4

public val CS_POSIX_V7_WIDTH_RESTRICTED_ENVS: CInt = 5

public val CS_POSIX_V6_ILP32_OFF32_CFLAGS: CInt = 1116

public val CS_POSIX_V6_ILP32_OFF32_LDFLAGS: CInt = 1117

public val CS_POSIX_V6_ILP32_OFF32_LIBS: CInt = 1118

public val CS_POSIX_V6_ILP32_OFF32_LINTFLAGS: CInt = 1119

public val CS_POSIX_V6_ILP32_OFFBIG_CFLAGS: CInt = 1120

public val CS_POSIX_V6_ILP32_OFFBIG_LDFLAGS: CInt = 1121

public val CS_POSIX_V6_ILP32_OFFBIG_LIBS: CInt = 1122

public val CS_POSIX_V6_ILP32_OFFBIG_LINTFLAGS: CInt = 1123

public val CS_POSIX_V6_LP64_OFF64_CFLAGS: CInt = 1124

public val CS_POSIX_V6_LP64_OFF64_LDFLAGS: CInt = 1125

public val CS_POSIX_V6_LP64_OFF64_LIBS: CInt = 1126

public val CS_POSIX_V6_LP64_OFF64_LINTFLAGS: CInt = 1127

public val CS_POSIX_V6_LPBIG_OFFBIG_CFLAGS: CInt = 1128

public val CS_POSIX_V6_LPBIG_OFFBIG_LDFLAGS: CInt = 1129

public val CS_POSIX_V6_LPBIG_OFFBIG_LIBS: CInt = 1130

public val CS_POSIX_V6_LPBIG_OFFBIG_LINTFLAGS: CInt = 1131

public val CS_POSIX_V7_ILP32_OFF32_CFLAGS: CInt = 1132

public val CS_POSIX_V7_ILP32_OFF32_LDFLAGS: CInt = 1133

public val CS_POSIX_V7_ILP32_OFF32_LIBS: CInt = 1134

public val CS_POSIX_V7_ILP32_OFF32_LINTFLAGS: CInt = 1135

public val CS_POSIX_V7_ILP32_OFFBIG_CFLAGS: CInt = 1136

public val CS_POSIX_V7_ILP32_OFFBIG_LDFLAGS: CInt = 1137

public val CS_POSIX_V7_ILP32_OFFBIG_LIBS: CInt = 1138

public val CS_POSIX_V7_ILP32_OFFBIG_LINTFLAGS: CInt = 1139

public val CS_POSIX_V7_LP64_OFF64_CFLAGS: CInt = 1140

public val CS_POSIX_V7_LP64_OFF64_LDFLAGS: CInt = 1141

public val CS_POSIX_V7_LP64_OFF64_LIBS: CInt = 1142

public val CS_POSIX_V7_LP64_OFF64_LINTFLAGS: CInt = 1143

public val CS_POSIX_V7_LPBIG_OFFBIG_CFLAGS: CInt = 1144

public val CS_POSIX_V7_LPBIG_OFFBIG_LDFLAGS: CInt = 1145

public val CS_POSIX_V7_LPBIG_OFFBIG_LIBS: CInt = 1146

public val CS_POSIX_V7_LPBIG_OFFBIG_LINTFLAGS: CInt = 1147

public val RLIM_SAVED_MAX: RlimT = RLIM_INFINITY

public val RLIM_SAVED_CUR: RlimT = RLIM_INFINITY

// elf.h - Fields in the eIdent array.
public val EI_NIDENT: Int = 16

public val EI_MAG0: Int = 0

public val ELFMAG0: UByte = 0x7fu.toUByte()

public val EI_MAG1: Int = 1

public val ELFMAG1: UByte = 69u.toUByte()

public val EI_MAG2: Int = 2

public val ELFMAG2: UByte = 76u.toUByte()

public val EI_MAG3: Int = 3

public val ELFMAG3: UByte = 70u.toUByte()

public val SELFMAG: Int = 4

public val EI_CLASS: Int = 4

public val ELFCLASSNONE: UByte = 0u.toUByte()

public val ELFCLASS32: UByte = 1u.toUByte()

public val ELFCLASS64: UByte = 2u.toUByte()

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val ELFCLASSNUM: Int = 3

public val EI_DATA: Int = 5

public val ELFDATANONE: UByte = 0u.toUByte()

public val ELFDATA2LSB: UByte = 1u.toUByte()

public val ELFDATA2MSB: UByte = 2u.toUByte()

public val ELFDATANUM: Int = 3

public val EI_VERSION: Int = 6

public val EI_OSABI: Int = 7

public val ELFOSABI_NONE: UByte = 0u.toUByte()

public val ELFOSABI_SYSV: UByte = 0u.toUByte()

public val ELFOSABI_HPUX: UByte = 1u.toUByte()

public val ELFOSABI_NETBSD: UByte = 2u.toUByte()

public val ELFOSABI_GNU: UByte = 3u.toUByte()

public val ELFOSABI_LINUX: UByte = ELFOSABI_GNU

public val ELFOSABI_SOLARIS: UByte = 6u.toUByte()

public val ELFOSABI_AIX: UByte = 7u.toUByte()

public val ELFOSABI_IRIX: UByte = 8u.toUByte()

public val ELFOSABI_FREEBSD: UByte = 9u.toUByte()

public val ELFOSABI_TRU64: UByte = 10u.toUByte()

public val ELFOSABI_MODESTO: UByte = 11u.toUByte()

public val ELFOSABI_OPENBSD: UByte = 12u.toUByte()

public val ELFOSABI_ARM: UByte = 97u.toUByte()

public val ELFOSABI_STANDALONE: UByte = 255u.toUByte()

public val EI_ABIVERSION: Int = 8

public val EI_PAD: Int = 9

// elf.h - Legal values for eType (object file type).
public val ET_NONE: UShort = 0u.toUShort()

public val ET_REL: UShort = 1u.toUShort()

public val ET_EXEC: UShort = 2u.toUShort()

public val ET_DYN: UShort = 3u.toUShort()

public val ET_CORE: UShort = 4u.toUShort()

public val ET_NUM: UShort = 5u.toUShort()

public val ET_LOOS: UShort = 0xfe00u.toUShort()

public val ET_HIOS: UShort = 0xfeffu.toUShort()

public val ET_LOPROC: UShort = 0xff00u.toUShort()

public val ET_HIPROC: UShort = 0xffffu.toUShort()

// elf.h - Legal values for eMachine (architecture).
public val EM_NONE: UShort = 0u.toUShort()

public val EM_M32: UShort = 1u.toUShort()

public val EM_SPARC: UShort = 2u.toUShort()

public val EM_386: UShort = 3u.toUShort()

public val EM_68K: UShort = 4u.toUShort()

public val EM_88K: UShort = 5u.toUShort()

public val EM_860: UShort = 7u.toUShort()

public val EM_MIPS: UShort = 8u.toUShort()

public val EM_S370: UShort = 9u.toUShort()

public val EM_MIPS_RS3_LE: UShort = 10u.toUShort()

public val EM_PARISC: UShort = 15u.toUShort()

public val EM_VPP500: UShort = 17u.toUShort()

public val EM_SPARC32PLUS: UShort = 18u.toUShort()

public val EM_960: UShort = 19u.toUShort()

public val EM_PPC: UShort = 20u.toUShort()

public val EM_PPC64: UShort = 21u.toUShort()

public val EM_S390: UShort = 22u.toUShort()

public val EM_V800: UShort = 36u.toUShort()

public val EM_FR20: UShort = 37u.toUShort()

public val EM_RH32: UShort = 38u.toUShort()

public val EM_RCE: UShort = 39u.toUShort()

public val EM_ARM: UShort = 40u.toUShort()

public val EM_FAKE_ALPHA: UShort = 41u.toUShort()

public val EM_SH: UShort = 42u.toUShort()

public val EM_SPARCV9: UShort = 43u.toUShort()

public val EM_TRICORE: UShort = 44u.toUShort()

public val EM_ARC: UShort = 45u.toUShort()

public val EM_H8_300: UShort = 46u.toUShort()

public val EM_H8_300H: UShort = 47u.toUShort()

public val EM_H8S: UShort = 48u.toUShort()

public val EM_H8_500: UShort = 49u.toUShort()

public val EM_IA_64: UShort = 50u.toUShort()

public val EM_MIPS_X: UShort = 51u.toUShort()

public val EM_COLDFIRE: UShort = 52u.toUShort()

public val EM_68HC12: UShort = 53u.toUShort()

public val EM_MMA: UShort = 54u.toUShort()

public val EM_PCP: UShort = 55u.toUShort()

public val EM_NCPU: UShort = 56u.toUShort()

public val EM_NDR1: UShort = 57u.toUShort()

public val EM_STARCORE: UShort = 58u.toUShort()

public val EM_ME16: UShort = 59u.toUShort()

public val EM_ST100: UShort = 60u.toUShort()

public val EM_TINYJ: UShort = 61u.toUShort()

public val EM_X86_64: UShort = 62u.toUShort()

public val EM_PDSP: UShort = 63u.toUShort()

public val EM_FX66: UShort = 66u.toUShort()

public val EM_ST9PLUS: UShort = 67u.toUShort()

public val EM_ST7: UShort = 68u.toUShort()

public val EM_68HC16: UShort = 69u.toUShort()

public val EM_68HC11: UShort = 70u.toUShort()

public val EM_68HC08: UShort = 71u.toUShort()

public val EM_68HC05: UShort = 72u.toUShort()

public val EM_SVX: UShort = 73u.toUShort()

public val EM_ST19: UShort = 74u.toUShort()

public val EM_VAX: UShort = 75u.toUShort()

public val EM_CRIS: UShort = 76u.toUShort()

public val EM_JAVELIN: UShort = 77u.toUShort()

public val EM_FIREPATH: UShort = 78u.toUShort()

public val EM_ZSP: UShort = 79u.toUShort()

public val EM_MMIX: UShort = 80u.toUShort()

public val EM_HUANY: UShort = 81u.toUShort()

public val EM_PRISM: UShort = 82u.toUShort()

public val EM_AVR: UShort = 83u.toUShort()

public val EM_FR30: UShort = 84u.toUShort()

public val EM_D10V: UShort = 85u.toUShort()

public val EM_D30V: UShort = 86u.toUShort()

public val EM_V850: UShort = 87u.toUShort()

public val EM_M32R: UShort = 88u.toUShort()

public val EM_MN10300: UShort = 89u.toUShort()

public val EM_MN10200: UShort = 90u.toUShort()

public val EM_PJ: UShort = 91u.toUShort()

public val EM_OPENRISC: UShort = 92u.toUShort()

public val EM_ARC_A5: UShort = 93u.toUShort()

public val EM_XTENSA: UShort = 94u.toUShort()

public val EM_AARCH64: UShort = 183u.toUShort()

public val EM_TILEPRO: UShort = 188u.toUShort()

public val EM_TILEGX: UShort = 191u.toUShort()

public val EM_RISCV: UShort = 243u.toUShort()

public val EM_ALPHA: UShort = 0x9026u.toUShort()

// elf.h - Legal values for eVersion (version).
public val EV_NONE: UInt = 0u

public val EV_CURRENT: UInt = 1u

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val EV_NUM: UInt = 2u

// elf.h - Legal values for pType (segment type).
public val PT_NULL: UInt = 0u

public val PT_LOAD: UInt = 1u

public val PT_DYNAMIC: UInt = 2u

public val PT_INTERP: UInt = 3u

public val PT_NOTE: UInt = 4u

public val PT_SHLIB: UInt = 5u

public val PT_PHDR: UInt = 6u

public val PT_TLS: UInt = 7u

public val PT_NUM: UInt = 8u

public val PT_LOOS: UInt = 0x60000000u

public val PT_GNU_EH_FRAME: UInt = 0x6474e550u

public val PT_GNU_STACK: UInt = 0x6474e551u

public val PT_GNU_RELRO: UInt = 0x6474e552u

public val PT_LOSUNW: UInt = 0x6ffffffau

public val PT_SUNWBSS: UInt = 0x6ffffffau

public val PT_SUNWSTACK: UInt = 0x6ffffffbu

public val PT_HISUNW: UInt = 0x6fffffffu

public val PT_HIOS: UInt = 0x6fffffffu

public val PT_LOPROC: UInt = 0x70000000u

public val PT_HIPROC: UInt = 0x7fffffffu

// Legal values for pFlags (segment flags).
public val PF_X: UInt = 1u shl 0

public val PF_W: UInt = 1u shl 1

public val PF_R: UInt = 1u shl 2

public val PF_MASKOS: UInt = 0x0ff00000u

public val PF_MASKPROC: UInt = 0xf0000000u

// elf.h - Legal values for aType (entry type).
public val AT_NULL: CUlong = 0uL

public val AT_IGNORE: CUlong = 1uL

public val AT_EXECFD: CUlong = 2uL

public val AT_PHDR: CUlong = 3uL

public val AT_PHENT: CUlong = 4uL

public val AT_PHNUM: CUlong = 5uL

public val AT_PAGESZ: CUlong = 6uL

public val AT_BASE: CUlong = 7uL

public val AT_FLAGS: CUlong = 8uL

public val AT_ENTRY: CUlong = 9uL

public val AT_NOTELF: CUlong = 10uL

public val AT_UID: CUlong = 11uL

public val AT_EUID: CUlong = 12uL

public val AT_GID: CUlong = 13uL

public val AT_EGID: CUlong = 14uL

public val AT_PLATFORM: CUlong = 15uL

public val AT_HWCAP: CUlong = 16uL

public val AT_CLKTCK: CUlong = 17uL

public val AT_SECURE: CUlong = 23uL

public val AT_BASE_PLATFORM: CUlong = 24uL

public val AT_RANDOM: CUlong = 25uL

public val AT_HWCAP2: CUlong = 26uL

public val AT_HWCAP3: CUlong = 29uL

public val AT_HWCAP4: CUlong = 30uL

public val AT_EXECFN: CUlong = 31uL

// defined in arch/<arch>/include/uapi/asm/auxvec.h but has the same value
// wherever it is defined.
public val AT_SYSINFO_EHDR: CUlong = 33uL

public val AT_MINSIGSTKSZ: CUlong = 51uL

public val GLOB_ERR: CInt = 1 shl 0

public val GLOB_MARK: CInt = 1 shl 1

public val GLOB_NOSORT: CInt = 1 shl 2

public val GLOB_DOOFFS: CInt = 1 shl 3

public val GLOB_NOCHECK: CInt = 1 shl 4

public val GLOB_APPEND: CInt = 1 shl 5

public val GLOB_NOESCAPE: CInt = 1 shl 6

public val GLOB_NOSPACE: CInt = 1

public val GLOB_ABORTED: CInt = 2

public val GLOB_NOMATCH: CInt = 3

public val POSIX_MADV_NORMAL: CInt = 0

public val POSIX_MADV_RANDOM: CInt = 1

public val POSIX_MADV_SEQUENTIAL: CInt = 2

public val POSIX_MADV_WILLNEED: CInt = 3

public val POSIX_MADV_DONTNEED: CInt = 4

public val S_IEXEC: ModeT = 64u

public val S_IWRITE: ModeT = 128u

public val S_IREAD: ModeT = 256u

public val F_LOCK: CInt = 1

public val F_TEST: CInt = 3

public val F_TLOCK: CInt = 2

public val F_ULOCK: CInt = 0

public val ST_RDONLY: CUlong = 1uL

public val ST_NOSUID: CUlong = 2uL

public val ST_NODEV: CUlong = 4uL

public val ST_NOEXEC: CUlong = 8uL

public val ST_SYNCHRONOUS: CUlong = 16uL

public val ST_MANDLOCK: CUlong = 64uL

public val ST_WRITE: CUlong = 128uL

public val ST_APPEND: CUlong = 256uL

public val ST_IMMUTABLE: CUlong = 512uL

public val ST_NOATIME: CUlong = 1024uL

public val ST_NODIRATIME: CUlong = 2048uL

public val RTLD_NEXT: MutPtr = MutPtr((-1L))

public val RTLD_NODELETE: CInt = 0x1000

public val RTLD_NOW: CInt = 0x2

public val AT_EACCESS: CInt = 0x200

public val PTHREAD_BARRIER_SERIAL_THREAD: CInt = -1

public val PTHREAD_ONCE_INIT: PthreadOnceT = 0

public val PTHREAD_MUTEX_NORMAL: CInt = 0

public val PTHREAD_MUTEX_RECURSIVE: CInt = 1

public val PTHREAD_MUTEX_ERRORCHECK: CInt = 2

public val PTHREAD_MUTEX_DEFAULT: CInt = PTHREAD_MUTEX_NORMAL

public val PTHREAD_MUTEX_STALLED: CInt = 0

public val PTHREAD_MUTEX_ROBUST: CInt = 1

public val PTHREAD_PRIO_NONE: CInt = 0

public val PTHREAD_PRIO_INHERIT: CInt = 1

public val PTHREAD_PRIO_PROTECT: CInt = 2

public val PTHREAD_PROCESS_PRIVATE: CInt = 0

public val PTHREAD_PROCESS_SHARED: CInt = 1

public val PTHREAD_INHERIT_SCHED: CInt = 0

public val PTHREAD_EXPLICIT_SCHED: CInt = 1

public val SIZEOF_PTHREAD_COND_T: Int = 48

// System V IPC
public val IPC_PRIVATE: KeyT = 0

public val IPC_CREAT: CInt = 512

public val IPC_EXCL: CInt = 1024

public val IPC_NOWAIT: CInt = 2048

public val IPC_RMID: CInt = 0

public val IPC_SET: CInt = 1

public val IPC_STAT: CInt =
    if (false) {
        0x102
    } else {
        2
    }

public val IPC_INFO: CInt = 3

public val SHM_R: CInt = 256

public val SHM_W: CInt = 128

public val SHM_RDONLY: CInt = 4096

public val SHM_RND: CInt = 8192

public val SHM_REMAP: CInt = 16384

public val SHM_LOCK: CInt = 11

public val SHM_UNLOCK: CInt = 12

public val SHM_HUGETLB: CInt = 2048

public val SHM_NORESERVE: CInt = 4096

public val LOG_NFACILITIES: CInt = 24

public val AI_PASSIVE: CInt = 0x0001

public val AI_CANONNAME: CInt = 0x0002

public val AI_NUMERICHOST: CInt = 0x0004

public val AI_V4MAPPED: CInt = 0x0008

public val AI_ALL: CInt = 0x0010

public val AI_ADDRCONFIG: CInt = 0x0020

public val AI_NUMERICSERV: CInt = 0x0400

public val EAI_BADFLAGS: CInt = -1

public val EAI_NONAME: CInt = -2

public val EAI_AGAIN: CInt = -3

public val EAI_FAIL: CInt = -4

public val EAI_NODATA: CInt = -5

public val EAI_FAMILY: CInt = -6

public val EAI_SOCKTYPE: CInt = -7

public val EAI_SERVICE: CInt = -8

public val EAI_MEMORY: CInt = -10

public val EAI_SYSTEM: CInt = -11

public val EAI_OVERFLOW: CInt = -12

public val NI_NUMERICHOST: CInt = 1

public val NI_NUMERICSERV: CInt = 2

public val NI_NOFQDN: CInt = 4

public val NI_NAMEREQD: CInt = 8

public val NI_DGRAM: CInt = 16

public val NI_IDN: CInt = 32

public val AIO_CANCELED: CInt = 0

public val AIO_NOTCANCELED: CInt = 1

public val AIO_ALLDONE: CInt = 2

public val LIO_READ: CInt = 0

public val LIO_WRITE: CInt = 1

public val LIO_NOP: CInt = 2

public val LIO_WAIT: CInt = 0

public val LIO_NOWAIT: CInt = 1

public val RUSAGE_THREAD: CInt = 1

public val MSG_COPY: CInt = 16384

public val SHM_EXEC: CInt = 32768

public val IPV6_MULTICAST_ALL: CInt = 29

public val IPV6_ROUTER_ALERT_ISOLATE: CInt = 30

public val PACKET_MR_UNICAST: CInt = 3

public val PTRACE_EVENT_STOP: CInt = 128

public val UDP_SEGMENT: CInt = 103

public val UDP_GRO: CInt = 104

public val PR_SET_PDEATHSIG: CInt = 1

public val PR_GET_PDEATHSIG: CInt = 2

public val PR_GET_DUMPABLE: CInt = 3

public val PR_SET_DUMPABLE: CInt = 4

public val PR_GET_UNALIGN: CInt = 5

public val PR_SET_UNALIGN: CInt = 6

public val PR_UNALIGN_NOPRINT: CInt = 1

public val PR_UNALIGN_SIGBUS: CInt = 2

public val PR_GET_KEEPCAPS: CInt = 7

public val PR_SET_KEEPCAPS: CInt = 8

public val PR_GET_FPEMU: CInt = 9

public val PR_SET_FPEMU: CInt = 10

public val PR_FPEMU_NOPRINT: CInt = 1

public val PR_FPEMU_SIGFPE: CInt = 2

public val PR_GET_FPEXC: CInt = 11

public val PR_SET_FPEXC: CInt = 12

public val PR_FP_EXC_SW_ENABLE: CInt = 0x80

public val PR_FP_EXC_DIV: CInt = 0x010000

public val PR_FP_EXC_OVF: CInt = 0x020000

public val PR_FP_EXC_UND: CInt = 0x040000

public val PR_FP_EXC_RES: CInt = 0x080000

public val PR_FP_EXC_INV: CInt = 0x100000

public val PR_FP_EXC_DISABLED: CInt = 0

public val PR_FP_EXC_NONRECOV: CInt = 1

public val PR_FP_EXC_ASYNC: CInt = 2

public val PR_FP_EXC_PRECISE: CInt = 3

public val PR_GET_TIMING: CInt = 13

public val PR_SET_TIMING: CInt = 14

public val PR_TIMING_STATISTICAL: CInt = 0

public val PR_TIMING_TIMESTAMP: CInt = 1

public val PR_SET_NAME: CInt = 15

public val PR_GET_NAME: CInt = 16

public val PR_GET_ENDIAN: CInt = 19

public val PR_SET_ENDIAN: CInt = 20

public val PR_ENDIAN_BIG: CInt = 0

public val PR_ENDIAN_LITTLE: CInt = 1

public val PR_ENDIAN_PPC_LITTLE: CInt = 2

public val PR_GET_SECCOMP: CInt = 21

public val PR_SET_SECCOMP: CInt = 22

public val PR_CAPBSET_READ: CInt = 23

public val PR_CAPBSET_DROP: CInt = 24

public val PR_GET_TSC: CInt = 25

public val PR_SET_TSC: CInt = 26

public val PR_TSC_ENABLE: CInt = 1

public val PR_TSC_SIGSEGV: CInt = 2

public val PR_GET_SECUREBITS: CInt = 27

public val PR_SET_SECUREBITS: CInt = 28

public val PR_SET_TIMERSLACK: CInt = 29

public val PR_GET_TIMERSLACK: CInt = 30

public val PR_TASK_PERF_EVENTS_DISABLE: CInt = 31

public val PR_TASK_PERF_EVENTS_ENABLE: CInt = 32

public val PR_MCE_KILL: CInt = 33

public val PR_MCE_KILL_CLEAR: CInt = 0

public val PR_MCE_KILL_SET: CInt = 1

public val PR_MCE_KILL_LATE: CInt = 0

public val PR_MCE_KILL_EARLY: CInt = 1

public val PR_MCE_KILL_DEFAULT: CInt = 2

public val PR_MCE_KILL_GET: CInt = 34

public val PR_SET_MM: CInt = 35

public val PR_SET_MM_START_CODE: CInt = 1

public val PR_SET_MM_END_CODE: CInt = 2

public val PR_SET_MM_START_DATA: CInt = 3

public val PR_SET_MM_END_DATA: CInt = 4

public val PR_SET_MM_START_STACK: CInt = 5

public val PR_SET_MM_START_BRK: CInt = 6

public val PR_SET_MM_BRK: CInt = 7

public val PR_SET_MM_ARG_START: CInt = 8

public val PR_SET_MM_ARG_END: CInt = 9

public val PR_SET_MM_ENV_START: CInt = 10

public val PR_SET_MM_ENV_END: CInt = 11

public val PR_SET_MM_AUXV: CInt = 12

public val PR_SET_MM_EXE_FILE: CInt = 13

public val PR_SET_MM_MAP: CInt = 14

public val PR_SET_MM_MAP_SIZE: CInt = 15

public val PR_SET_PTRACER: CInt = 0x59616d61

public val PR_SET_PTRACER_ANY: CUlong = ((-1).toLong()).toULong()

public val PR_SET_CHILD_SUBREAPER: CInt = 36

public val PR_GET_CHILD_SUBREAPER: CInt = 37

public val PR_SET_NO_NEW_PRIVS: CInt = 38

public val PR_GET_NO_NEW_PRIVS: CInt = 39

public val PR_GET_TID_ADDRESS: CInt = 40

public val PR_SET_THP_DISABLE: CInt = 41

public val PR_GET_THP_DISABLE: CInt = 42

public val PR_MPX_ENABLE_MANAGEMENT: CInt = 43

public val PR_MPX_DISABLE_MANAGEMENT: CInt = 44

public val PR_SET_FP_MODE: CInt = 45

public val PR_GET_FP_MODE: CInt = 46

public val PR_FP_MODE_FR: CInt = 1 shl 0

public val PR_FP_MODE_FRE: CInt = 1 shl 1

public val PR_CAP_AMBIENT: CInt = 47

public val PR_CAP_AMBIENT_IS_SET: CInt = 1

public val PR_CAP_AMBIENT_RAISE: CInt = 2

public val PR_CAP_AMBIENT_LOWER: CInt = 3

public val PR_CAP_AMBIENT_CLEAR_ALL: CInt = 4

public val PR_SET_VMA: CInt = 0x53564d41

public val PR_SET_VMA_ANON_NAME: CInt = 0

public val PR_SCHED_CORE: CInt = 62

public val PR_SCHED_CORE_GET: CInt = 0

public val PR_SCHED_CORE_CREATE: CInt = 1

public val PR_SCHED_CORE_SHARE_TO: CInt = 2

public val PR_SCHED_CORE_SHARE_FROM: CInt = 3

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val PR_SCHED_CORE_MAX: CInt = 4

public val PR_SCHED_CORE_SCOPE_THREAD: CInt = 0

public val PR_SCHED_CORE_SCOPE_THREAD_GROUP: CInt = 1

public val PR_SCHED_CORE_SCOPE_PROCESS_GROUP: CInt = 2

public val ITIMER_REAL: CInt = 0

public val ITIMER_VIRTUAL: CInt = 1

public val ITIMER_PROF: CInt = 2

public val POSIX_VDISABLE: CcT = 0u.toUByte()

public val IPV6_RTHDR_LOOSE: CInt = 0

public val IPV6_RTHDR_STRICT: CInt = 1

public val IUTF8: TcflagT = 0x00004000u

public val CMSPAR: TcflagT = 1073741824u

public val MFD_CLOEXEC: CUint = 0x0001u

public val MFD_ALLOW_SEALING: CUint = 0x0002u

public val MFD_HUGETLB: CUint = 0x0004u

public val MFD_NOEXEC_SEAL: CUint = 0x0008u

public val MFD_EXEC: CUint = 0x0010u

public val MFD_HUGE_64KB: CUint = 0x40000000u

public val MFD_HUGE_512KB: CUint = 0x4c000000u

public val MFD_HUGE_1MB: CUint = 0x50000000u

public val MFD_HUGE_2MB: CUint = 0x54000000u

public val MFD_HUGE_8MB: CUint = 0x5c000000u

public val MFD_HUGE_16MB: CUint = 0x60000000u

public val MFD_HUGE_32MB: CUint = 0x64000000u

public val MFD_HUGE_256MB: CUint = 0x70000000u

public val MFD_HUGE_512MB: CUint = 0x74000000u

public val MFD_HUGE_1GB: CUint = 0x78000000u

public val MFD_HUGE_2GB: CUint = 0x7c000000u

public val MFD_HUGE_16GB: CUint = 0x88000000u

public val MFD_HUGE_MASK: CUint = 63u

public val MFD_HUGE_SHIFT: CUint = 26u

public val SIOCADDRT: CUlong = 0x0000890BuL

public val SIOCDELRT: CUlong = 0x0000890CuL

public val SIOCGIFNAME: CUlong = 0x00008910uL

public val SIOCSIFLINK: CUlong = 0x00008911uL

public val SIOCGIFCONF: CUlong = 0x00008912uL

public val SIOCGIFFLAGS: CUlong = 0x00008913uL

public val SIOCSIFFLAGS: CUlong = 0x00008914uL

public val SIOCGIFADDR: CUlong = 0x00008915uL

public val SIOCSIFADDR: CUlong = 0x00008916uL

public val SIOCGIFDSTADDR: CUlong = 0x00008917uL

public val SIOCSIFDSTADDR: CUlong = 0x00008918uL

public val SIOCGIFBRDADDR: CUlong = 0x00008919uL

public val SIOCSIFBRDADDR: CUlong = 0x0000891AuL

public val SIOCGIFNETMASK: CUlong = 0x0000891BuL

public val SIOCSIFNETMASK: CUlong = 0x0000891CuL

public val SIOCGIFMETRIC: CUlong = 0x0000891DuL

public val SIOCSIFMETRIC: CUlong = 0x0000891EuL

public val SIOCGIFMEM: CUlong = 0x0000891FuL

public val SIOCSIFMEM: CUlong = 0x00008920uL

public val SIOCGIFMTU: CUlong = 0x00008921uL

public val SIOCSIFMTU: CUlong = 0x00008922uL

public val SIOCSIFNAME: CUlong = 0x00008923uL

public val SIOCSIFHWADDR: CUlong = 0x00008924uL

public val SIOCGIFENCAP: CUlong = 0x00008925uL

public val SIOCSIFENCAP: CUlong = 0x00008926uL

public val SIOCGIFHWADDR: CUlong = 0x00008927uL

public val SIOCGIFSLAVE: CUlong = 0x00008929uL

public val SIOCSIFSLAVE: CUlong = 0x00008930uL

public val SIOCADDMULTI: CUlong = 0x00008931uL

public val SIOCDELMULTI: CUlong = 0x00008932uL

public val SIOCGIFINDEX: CUlong = 0x00008933uL

public val SIOGIFINDEX: CUlong = SIOCGIFINDEX

public val SIOCSIFPFLAGS: CUlong = 0x00008934uL

public val SIOCGIFPFLAGS: CUlong = 0x00008935uL

public val SIOCDIFADDR: CUlong = 0x00008936uL

public val SIOCSIFHWBROADCAST: CUlong = 0x00008937uL

public val SIOCGIFCOUNT: CUlong = 0x00008938uL

public val SIOCGIFBR: CUlong = 0x00008940uL

public val SIOCSIFBR: CUlong = 0x00008941uL

public val SIOCGIFTXQLEN: CUlong = 0x00008942uL

public val SIOCSIFTXQLEN: CUlong = 0x00008943uL

public val SIOCETHTOOL: CUlong = 0x00008946uL

public val SIOCGMIIPHY: CUlong = 0x00008947uL

public val SIOCGMIIREG: CUlong = 0x00008948uL

public val SIOCSMIIREG: CUlong = 0x00008949uL

public val SIOCWANDEV: CUlong = 0x0000894AuL

public val SIOCOUTQNSD: CUlong = 0x0000894BuL

public val SIOCGSKNS: CUlong = 0x0000894CuL

public val SIOCDARP: CUlong = 0x00008953uL

public val SIOCGARP: CUlong = 0x00008954uL

public val SIOCSARP: CUlong = 0x00008955uL

public val SIOCDRARP: CUlong = 0x00008960uL

public val SIOCGRARP: CUlong = 0x00008961uL

public val SIOCSRARP: CUlong = 0x00008962uL

public val SIOCGIFMAP: CUlong = 0x00008970uL

public val SIOCSIFMAP: CUlong = 0x00008971uL

public val IPTOS_TOS_MASK: UByte = 0x1Eu.toUByte()

public val IPTOS_PREC_MASK: UByte = 0xE0u.toUByte()

public val IPTOS_ECN_NOT_ECT: UByte = 0x00u.toUByte()

public val RTF_UP: CUshort = 0x0001u.toUShort()

public val RTF_GATEWAY: CUshort = 0x0002u.toUShort()

public val RTF_HOST: CUshort = 0x0004u.toUShort()

public val RTF_REINSTATE: CUshort = 0x0008u.toUShort()

public val RTF_DYNAMIC: CUshort = 0x0010u.toUShort()

public val RTF_MODIFIED: CUshort = 0x0020u.toUShort()

public val RTF_MTU: CUshort = 0x0040u.toUShort()

public val RTF_MSS: CUshort = RTF_MTU

public val RTF_WINDOW: CUshort = 0x0080u.toUShort()

public val RTF_IRTT: CUshort = 0x0100u.toUShort()

public val RTF_REJECT: CUshort = 0x0200u.toUShort()

public val RTF_STATIC: CUshort = 0x0400u.toUShort()

public val RTF_XRESOLVE: CUshort = 0x0800u.toUShort()

public val RTF_NOFORWARD: CUshort = 0x1000u.toUShort()

public val RTF_THROW: CUshort = 0x2000u.toUShort()

public val RTF_NOPMTUDISC: CUshort = 0x4000u.toUShort()

public val RTF_DEFAULT: UInt = 0x00010000u

public val RTF_ALLONLINK: UInt = 0x00020000u

public val RTF_ADDRCONF: UInt = 0x00040000u

public val RTF_LINKRT: UInt = 0x00100000u

public val RTF_NONEXTHOP: UInt = 0x00200000u

public val RTF_CACHE: UInt = 0x01000000u

public val RTF_FLOW: UInt = 0x02000000u

public val RTF_POLICY: UInt = 0x04000000u

public val RTCF_VALVE: UInt = 0x00200000u

public val RTCF_MASQ: UInt = 0x00400000u

public val RTCF_NAT: UInt = 0x00800000u

public val RTCF_DOREDIRECT: UInt = 0x01000000u

public val RTCF_LOG: UInt = 0x02000000u

public val RTCF_DIRECTSRC: UInt = 0x04000000u

public val RTF_LOCAL: UInt = 0x80000000u

public val RTF_INTERFACE: UInt = 0x40000000u

public val RTF_MULTICAST: UInt = 0x20000000u

public val RTF_BROADCAST: UInt = 0x10000000u

public val RTF_NAT: UInt = 0x08000000u

public val RTF_ADDRCLASSMASK: UInt = 0xF8000000u

public val RT_CLASS_UNSPEC: UByte = 0u.toUByte()

public val RT_CLASS_DEFAULT: UByte = 253u.toUByte()

public val RT_CLASS_MAIN: UByte = 254u.toUByte()

public val RT_CLASS_LOCAL: UByte = 255u.toUByte()

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val RT_CLASS_MAX: UByte = 255u.toUByte()

public val MAX_ADDR_LEN: Int = 7

public val ARPD_UPDATE: CUshort = 0x01u.toUShort()

public val ARPD_LOOKUP: CUshort = 0x02u.toUShort()

public val ARPD_FLUSH: CUshort = 0x03u.toUShort()

public val ATF_MAGIC: CInt = 0x80

// include/uapi/linux/udp.h
public val UDP_CORK: CInt = 1

public val UDP_ENCAP: CInt = 100

public val UDP_NO_CHECK6_TX: CInt = 101

public val UDP_NO_CHECK6_RX: CInt = 102

// include/uapi/asm-generic/mman-common.h
public val MAP_FIXED_NOREPLACE: CInt = 0x100000

public val MLOCK_ONFAULT: CUint = 0x01u

public val REG_EXTENDED: CInt = 1

public val REG_ICASE: CInt = 2

public val REG_NEWLINE: CInt = 4

public val REG_NOSUB: CInt = 8

public val REG_NOTBOL: CInt = 1

public val REG_NOTEOL: CInt = 2

public val REG_ENOSYS: CInt = -1

public val REG_NOMATCH: CInt = 1

public val REG_BADPAT: CInt = 2

public val REG_ECOLLATE: CInt = 3

public val REG_ECTYPE: CInt = 4

public val REG_EESCAPE: CInt = 5

public val REG_ESUBREG: CInt = 6

public val REG_EBRACK: CInt = 7

public val REG_EPAREN: CInt = 8

public val REG_EBRACE: CInt = 9

public val REG_BADBR: CInt = 10

public val REG_ERANGE: CInt = 11

public val REG_ESPACE: CInt = 12

public val REG_BADRPT: CInt = 13

// errno.h
public val EPERM: CInt = 1

public val ENOENT: CInt = 2

public val ESRCH: CInt = 3

public val EINTR: CInt = 4

public val EIO: CInt = 5

public val ENXIO: CInt = 6

public val E2BIG: CInt = 7

public val ENOEXEC: CInt = 8

public val EBADF: CInt = 9

public val ECHILD: CInt = 10

public val EAGAIN: CInt = 11

public val ENOMEM: CInt = 12

public val EACCES: CInt = 13

public val EFAULT: CInt = 14

public val ENOTBLK: CInt = 15

public val EBUSY: CInt = 16

public val EEXIST: CInt = 17

public val EXDEV: CInt = 18

public val ENODEV: CInt = 19

public val ENOTDIR: CInt = 20

public val EISDIR: CInt = 21

public val EINVAL: CInt = 22

public val ENFILE: CInt = 23

public val EMFILE: CInt = 24

public val ENOTTY: CInt = 25

public val ETXTBSY: CInt = 26

public val EFBIG: CInt = 27

public val ENOSPC: CInt = 28

public val ESPIPE: CInt = 29

public val EROFS: CInt = 30

public val EMLINK: CInt = 31

public val EPIPE: CInt = 32

public val EDOM: CInt = 33

public val ERANGE: CInt = 34

public val EWOULDBLOCK: CInt = EAGAIN

public val CSIGNAL: CInt = 0x000000ff

public val SCHED_NORMAL: CInt = 0

public val SCHED_OTHER: CInt = 0

public val SCHED_FIFO: CInt = 1

public val SCHED_RR: CInt = 2

public val SCHED_BATCH: CInt = 3

public val SCHED_IDLE: CInt = 5

public val SCHED_DEADLINE: CInt = 6

public val SCHED_RESET_ON_FORK: CInt = 0x40000000

// elf.h
public val NT_PRSTATUS: CInt = 1

public val NT_PRFPREG: CInt = 2

public val NT_FPREGSET: CInt = 2

public val NT_PRPSINFO: CInt = 3

public val NT_PRXREG: CInt = 4

public val NT_TASKSTRUCT: CInt = 4

public val NT_PLATFORM: CInt = 5

public val NT_AUXV: CInt = 6

public val NT_GWINDOWS: CInt = 7

public val NT_ASRS: CInt = 8

public val NT_PSTATUS: CInt = 10

public val NT_PSINFO: CInt = 13

public val NT_PRCRED: CInt = 14

public val NT_UTSNAME: CInt = 15

public val NT_LWPSTATUS: CInt = 16

public val NT_LWPSINFO: CInt = 17

public val NT_PRFPXREG: CInt = 20

// Note(1.0): C uses an unsigned int here.
public val MS_NOUSER: CUlong = 1uL shl 31

public val RTLD_DEFAULT: MutPtr = MutPtr(0L)

public val SEM_FAILED: MutPtr = MutPtr(0L)

public fun cmsgNxthdr(mhdr: ConstPtr, cmsg: ConstPtr): MutPtr {
    if ((((UnresolvedTypeLayout<Cmsghdr>()).readNative((cmsg).rawAddress).cmsgLen).toInt()) < sizeOf<Cmsghdr>(typeLayoutT = UnresolvedTypeLayout<Cmsghdr>())) {
        return MutPtr(0L)
    }
    // Note(msrv): `.wrappingByteAdd()` stabilized in 1.75
    val nextCmsg = ConstPtr((ConstPtr((ConstPtr((cmsg).rawAddress)).rawAddress + ((CMSG_ALIGN(((UnresolvedTypeLayout<Cmsghdr>()).readNative((cmsg).rawAddress).cmsgLen).toInt())).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)
    // In case the addition wrapped. `nextAddr> maxAddr`
    // would otherwise not work as intended.
    if ((nextCmsg.rawAddress.toInt()) < (cmsg.rawAddress.toInt())) {
        return MutPtr(0L)
    }
    var maxAddr = ((UnresolvedTypeLayout<Msghdr>()).readNative((mhdr).rawAddress).msgControl).rawAddress.toInt() + ((UnresolvedTypeLayout<Msghdr>()).readNative((mhdr).rawAddress).msgControllen).toInt()
    if (false) {
        // musl and some of its descendants do `>= maxAddr`
        // comparisons in the if statement below.
        // https://www.openwall.com/lists/musl/2025/12/27/1
        maxAddr -= 1
    }
    return if (nextCmsg.rawAddress.toInt() + sizeOf<Cmsghdr>(typeLayoutT = UnresolvedTypeLayout<Cmsghdr>()) > maxAddr) {
        MutPtr(0L)
    } else {
        MutPtr(nextCmsg.rawAddress, nextCmsg.rawMeta)
    }
}

public fun cpuAllocSize(count: CInt): SizeT {
    val dummy: CpuSetT = CpuSetT()
    val sizeInBits = 8 * sizeOfVal(dummy.bits[0])
    return ((count.toULong() + sizeInBits.toULong() - 1uL) / 8uL)
}

public fun cpuZero(cpuset: CpuSetT) {
    cpuset.bits.fill(0)
}

public fun cpuSet(cpu: Int, cpuset: CpuSetT) {
    val sizeInBits = 8 * sizeOfVal(cpuset.bits[0])
    // 32, 64 etc
    val (idx, offset) = Pair(cpu / sizeInBits, cpu % sizeInBits)
    cpuset.bits[idx] = cpuset.bits[idx] or (1uL shl offset)
}

public fun cpuClr(cpu: Int, cpuset: CpuSetT) {
    val sizeInBits = 8 * sizeOfVal(cpuset.bits[0])
    // 32, 64 etc
    val (idx, offset) = Pair(cpu / sizeInBits, cpu % sizeInBits)
    cpuset.bits[idx] = cpuset.bits[idx] and (1uL shl offset).inv()
}

public fun cpuIsset(cpu: Int, cpuset: CpuSetT): Boolean {
    val sizeInBits = 8 * sizeOfVal(cpuset.bits[0])
    val (idx, offset) = Pair(cpu / sizeInBits, cpu % sizeInBits)
    return 0uL != (cpuset.bits[idx] and (1uL shl offset))
}

public fun cpuCountS(size: Int, cpuset: CpuSetT): CInt {
    var s: UInt = 0u
    val sizeOfMask = sizeOfVal(cpuset.bits[0])
    for (i in ((cpuset.bits).sliceArray(0 until (size / sizeOfMask)))) {
        s += i.countOnes()
    }
    return s.toInt()
}

public fun cpuCount(cpuset: CpuSetT): CInt = cpuCountS(sizeOf<CpuSetT>(typeLayoutT = CpuSetT.typeLayout()), cpuset)

public fun cpuEqual(set1: CpuSetT, set2: CpuSetT): Boolean = set1.bits.contentEquals(set2.bits)

public fun iptosTos(tos: UByte): UByte = tos and IPTOS_TOS_MASK

public fun iptosPrec(tos: UByte): UByte = tos and IPTOS_PREC_MASK

public fun rtTos(tos: UByte): UByte = tos and IPTOS_TOS_MASK

public fun rtAddrclass(flags: UInt): UInt = flags shr 23

public fun rtLocaladdr(flags: UInt): Boolean = (flags and RTF_ADDRCLASSMASK) == (RTF_LOCAL or RTF_INTERFACE)

public fun elf32RSym(value: Elf32Word): Elf32Word = value shr 8

public fun elf32RType(value: Elf32Word): Elf32Word = value and 0xffu

public fun elf32RInfo(sym: Elf32Word, t: Elf32Word): Elf32Word = sym shl (8u + t).toInt() and 0xffu

public fun elf64RSym(value: Elf64Xword): Elf64Xword = value shr 32

public fun elf64RType(value: Elf64Xword): Elf64Xword = value and 0xffffffffuL

public fun elf64RInfo(sym: Elf64Xword, t: Elf64Xword): Elf64Xword = sym shl (32uL + t).toInt()

public fun makedev(major: CUint, minor: CUint): DevT {
    val major1 = major.toULong()
    val minor1 = minor.toULong()
    var dev = 0uL
    dev = dev or ((major1 and 0x00000fffuL) shl 8)
    dev = dev or ((major1 and 0xfffff000uL) shl 32)
    dev = dev or ((minor1 and 0x000000ffuL) shl 0)
    dev = dev or ((minor1 and 0xffffff00uL) shl 12)
    return dev
}

public fun major(dev: DevT): CUint {
    var major = 0uL
    major = major or ((dev and 0x00000000000fff00uL) shr 8)
    major = major or ((dev and 0xfffff00000000000uL) shr 32)
    return major.toUInt()
}

public fun minor(dev: DevT): CUint {
    var minor = 0uL
    minor = minor or ((dev and 0x00000000000000ffuL) shr 0)
    minor = minor or ((dev and 0x00000ffffff00000uL) shr 12)
    return minor.toUInt()
}

public expect fun iopl(level: CInt): CInt

public expect fun ioperm(from: CUlong, num: CUlong, turnOn: CInt): CInt

public expect fun aioRead(aiocbp: MutPtr): CInt

public expect fun aioWrite(aiocbp: MutPtr): CInt

public expect fun aioFsync(op: CInt, aiocbp: MutPtr): CInt

public expect fun aioError(aiocbp: ConstPtr): CInt

public expect fun aioReturn(aiocbp: MutPtr): SsizeT

public expect fun aioSuspend(aiocbList: ConstPtr, nitems: CInt, timeout: ConstPtr): CInt

public expect fun aioCancel(fd: CInt, aiocbp: MutPtr): CInt

public expect fun lioListio(mode: CInt, aiocbList: ConstPtr, nitems: CInt, sevp: MutPtr): CInt

public expect fun pwritev(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT

public expect fun preadv(fd: CInt, iov: ConstPtr, iovcnt: CInt, offset: OffT): SsizeT

public expect fun getnameinfo(sa: ConstPtr, salen: SocklenT, host: MutPtr, hostlen: SocklenT, serv: MutPtr, servlen: SocklenT, flags: CInt): CInt

public expect fun getloadavg(loadavg: MutPtr, nelem: CInt): CInt

public expect fun processVmReadv(pid: PidT, localIov: ConstPtr, liovcnt: CUlong, remoteIov: ConstPtr, riovcnt: CUlong, flags: CUlong): Long

public expect fun processVmWritev(pid: PidT, localIov: ConstPtr, liovcnt: CUlong, remoteIov: ConstPtr, riovcnt: CUlong, flags: CUlong): Long

public expect fun futimes(fd: CInt, times: ConstPtr): CInt

public expect fun strerrorR(errnum: CInt, buf: MutPtr, buflen: SizeT): CInt

public expect fun abs(i: CInt): CInt

public expect fun labs(i: CLong): CLong

public expect fun rand(): CInt

public expect fun srand(seed: CUint)

public expect fun drand48(): CDouble

public expect fun erand48(xseed: MutPtr): CDouble

public expect fun lrand48(): CLong

public expect fun nrand48(xseed: MutPtr): CLong

public expect fun jrand48(xseed: MutPtr): CLong

public expect fun srand48(seed: CLong)

public expect fun setpwent()

public expect fun endpwent()

public expect fun getpwent(): MutPtr

public expect fun setgrent()

public expect fun endgrent()

public expect fun getgrent(): MutPtr

public expect fun setspent()

public expect fun endspent()

public expect fun getspent(): MutPtr

public expect fun getspnam(name: ConstPtr): MutPtr

// System V IPC
public expect fun shmget(key: KeyT, size: SizeT, shmflg: CInt): CInt

public expect fun shmat(shmid: CInt, shmaddr: ConstPtr, shmflg: CInt): MutPtr

public expect fun shmdt(shmaddr: ConstPtr): CInt

public expect fun shmctl(shmid: CInt, cmd: CInt, buf: MutPtr): CInt

public expect fun mprotect(addr: MutPtr, len: SizeT, prot: CInt): CInt

public expect fun errnoLocation(): MutPtr

public expect fun glob(pattern: ConstPtr, flags: CInt, errfunc: GlobErrfuncCallback?, pglob: MutPtr): CInt

public expect fun globfree(pglob: MutPtr)

public expect fun seekdir(dirp: MutPtr, loc: CLong)

public expect fun telldir(dirp: MutPtr): CLong

public expect fun madvise(addr: MutPtr, len: SizeT, advice: CInt): CInt

public expect fun msync(addr: MutPtr, len: SizeT, flags: CInt): CInt

public expect fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT

public expect fun nlLanginfo(item: NlItem): MutPtr

public expect fun nlLanginfoL(item: NlItem, locale: LocaleT): MutPtr

public expect fun schedGetaffinity(pid: PidT, cpusetsize: SizeT, cpuset: MutPtr): CInt

public expect fun schedGetPriorityMax(policy: CInt): CInt

public expect fun settimeofday(tv: ConstPtr, tz: ConstPtr): CInt

public expect fun semTimedwait(sem: MutPtr, abstime: ConstPtr): CInt

public expect fun semGetvalue(sem: MutPtr, sval: MutPtr): CInt

public expect fun mount(src: ConstPtr, target: ConstPtr, fstype: ConstPtr, flags: CUlong, data: ConstPtr): CInt

public expect fun prctl(option: CInt, vararg variadicArgs: Long): CInt

public expect fun ppoll(fds: MutPtr, nfds: NfdsT, timeout: ConstPtr, sigmask: ConstPtr): CInt

public expect fun sethostname(name: ConstPtr, len: SizeT): CInt

public expect fun schedGetPriorityMin(policy: CInt): CInt

public expect fun sysinfo(info: MutPtr): CInt

public expect fun sigsuspend(mask: ConstPtr): CInt

public expect fun getgrgidR(gid: GidT, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt

public expect fun semClose(sem: MutPtr): CInt

public expect fun getgrnamR(name: ConstPtr, grp: MutPtr, buf: MutPtr, buflen: SizeT, result: MutPtr): CInt

public expect fun initgroups(user: ConstPtr, group: GidT): CInt

public expect fun semOpen(name: ConstPtr, oflag: CInt, vararg variadicArgs: Long): MutPtr

public expect fun getgrnam(name: ConstPtr): MutPtr

public expect fun semUnlink(name: ConstPtr): CInt

public expect fun daemon(nochdir: CInt, noclose: CInt): CInt

public expect fun sigwait(set: ConstPtr, sig: MutPtr): CInt

public expect fun getgrgid(gid: GidT): MutPtr

public expect fun popen(command: ConstPtr, mode: ConstPtr): MutPtr

public expect fun faccessat(dirfd: CInt, pathname: ConstPtr, mode: CInt, flags: CInt): CInt

public expect fun dlIteratePhdr(callback: DlIteratePhdrCallbackCallback?, data: MutPtr): CInt

public expect fun setmntent(filename: ConstPtr, ty: ConstPtr): MutPtr

public expect fun getmntent(stream: MutPtr): MutPtr

public expect fun addmntent(stream: MutPtr, mnt: ConstPtr): CInt

public expect fun endmntent(streamp: MutPtr): CInt

public expect fun hasmntopt(mnt: ConstPtr, opt: ConstPtr): MutPtr

public expect fun regcomp(preg: MutPtr, pattern: ConstPtr, cflags: CInt): CInt

public expect fun regexec(preg: ConstPtr, input: ConstPtr, nmatch: SizeT, pmatch: MutPtr, eflags: CInt): CInt

public expect fun regerror(errcode: CInt, preg: ConstPtr, errbuf: MutPtr, errbufSize: SizeT): SizeT

public expect fun regfree(preg: MutPtr)

public expect fun iconvOpen(tocode: ConstPtr, fromcode: ConstPtr): IconvT

public expect fun iconv(cd: IconvT, inbuf: MutPtr, inbytesleft: MutPtr, outbuf: MutPtr, outbytesleft: MutPtr): SizeT

public expect fun iconvClose(cd: IconvT): CInt

public expect fun gettid(): PidT

public expect fun timerCreate(clockid: ClockidT, sevp: MutPtr, timerid: MutPtr): CInt

public expect fun timerDelete(timerid: TimerT): CInt

public expect fun timerGetoverrun(timerid: TimerT): CInt

public expect fun timerGettime(timerid: TimerT, currValue: MutPtr): CInt

public expect fun timerSettime(timerid: TimerT, flags: CInt, newValue: ConstPtr, oldValue: MutPtr): CInt

public expect fun memmem(haystack: ConstPtr, haystacklen: SizeT, needle: ConstPtr, needlelen: SizeT): MutPtr

public expect fun schedGetcpu(): CInt

public expect fun getoptLong(argc: CInt, argv: ConstPtr, optstring: ConstPtr, longopts: ConstPtr, longindex: MutPtr): CInt

public expect fun copyFileRange(fdIn: CInt, offIn: MutPtr, fdOut: CInt, offOut: MutPtr, len: SizeT, flags: CUint): SsizeT

public expect fun freopen64(filename: ConstPtr, mode: ConstPtr, file: MutPtr): MutPtr

public expect fun fseeko64(stream: MutPtr, offset: Off64T, whence: CInt): CInt

public expect fun fsetpos64(stream: MutPtr, ptr: ConstPtr): CInt

public expect fun ftello64(stream: MutPtr): Off64T
