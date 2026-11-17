@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.SighandlerT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.FsidT

public fun interface SigactionSaRestorerCallback {
    public operator fun invoke()
}

public open class SigsetT(
    public var value: ULongArray,
) {
    public open fun clone(): SigsetT = SigsetT(value)

    public companion object {
        public fun typeLayout(): TypeLayout<SigsetT> =
            object : TypeLayout<SigsetT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): SigsetT =
                    SigsetT(
                        (PrimitiveArrayLayouts.uLongArray(1)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SigsetT) {
                    (PrimitiveArrayLayouts.uLongArray(1)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SigsetT =
                    SigsetT(
                        (PrimitiveArrayLayouts.uLongArray(1)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SigsetT) {
                    (PrimitiveArrayLayouts.uLongArray(1)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class Sigaction(
    public var saFlags: CInt,
    public var saSigaction: SighandlerT,
    public var saMask: SigsetT,
    public var saRestorer: SigactionSaRestorerCallback?,
) {
    public open fun clone(): Sigaction = Sigaction(saFlags, saSigaction, saMask, saRestorer)
}

// Note(1.0,deprecate): lfs binding to be removed
public open class Rlimit64(
    public var rlimCur: CUlonglong,
    public var rlimMax: CUlonglong,
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

public open class PthreadAttrT internal constructor(
    public var flags: UInt,
    stackBaseCtorArg: MutPtr,
    public var stackSize: SizeT,
    public var guardSize: SizeT,
    public var schedPolicy: Int,
    public var schedPriority: Int,
    public var reserved: UByteArray,
) {
    public var stackBase: MutPtr = stackBaseCtorArg
        internal set

    public open fun clone(): PthreadAttrT = PthreadAttrT(flags, stackBase, stackSize, guardSize, schedPolicy, schedPriority, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadAttrT> =
            object : TypeLayout<PthreadAttrT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): PthreadAttrT =
                    PthreadAttrT(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I32).readNative(addr + 36L),
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: PthreadAttrT) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.flags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.stackBase)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.stackSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.guardSize)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.schedPolicy)
                    (PrimitiveLayouts.I32).writeNative(addr + 36L, value.schedPriority)
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr + 40L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadAttrT =
                    PthreadAttrT(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 36),
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadAttrT) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.flags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.stackBase)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.stackSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.guardSize)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.schedPolicy)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 36, value.schedPriority)
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset + 40, value.reserved)
                }
            }

        public operator fun invoke(
            flags: UInt,
            stackBase: MutPtr,
            stackSize: SizeT,
            guardSize: SizeT,
            schedPolicy: Int,
            schedPriority: Int,
            reserved: UByteArray,
        ): PthreadAttrT = PthreadAttrT(
            flags = flags,
            stackBaseCtorArg = stackBase,
            stackSize = stackSize,
            guardSize = guardSize,
            schedPolicy = schedPolicy,
            schedPriority = schedPriority,
            reserved = reserved,
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

public open class Statfs(
    public var fType: ULong,
    public var fBsize: ULong,
    public var fBlocks: ULong,
    public var fBfree: ULong,
    public var fBavail: ULong,
    public var fFiles: ULong,
    public var fFfree: ULong,
    public var fFsid: FsidT,
    public var fNamelen: ULong,
    public var fFrsize: ULong,
    public var fFlags: ULong,
    public var fSpare: ULongArray,
) {
    public open fun clone(): Statfs = Statfs(fType, fBsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFsid, fNamelen, fFrsize, fFlags, fSpare)
}

public open class Sysinfo(
    public var uptime: CLong,
    public var loads: ULongArray,
    public var totalram: CUlong,
    public var freeram: CUlong,
    public var sharedram: CUlong,
    public var bufferram: CUlong,
    public var totalswap: CUlong,
    public var freeswap: CUlong,
    public var procs: CUshort,
    public var pad: CUshort,
    public var totalhigh: CUlong,
    public var freehigh: CUlong,
    public var memUnit: CUint,
    public var f: UByteArray,
) {
    public open fun clone(): Sysinfo = Sysinfo(uptime, loads, totalram, freeram, sharedram, bufferram, totalswap, freeswap, procs, pad, totalhigh, freehigh, memUnit, f)

    public companion object {
        public fun typeLayout(): TypeLayout<Sysinfo> =
            object : TypeLayout<Sysinfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Sysinfo =
                    Sysinfo(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveArrayLayouts.uLongArray(3)).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (PrimitiveLayouts.U64).readNative(addr + 64L),
                        (PrimitiveLayouts.U64).readNative(addr + 72L),
                        (PrimitiveLayouts.U16).readNative(addr + 80L),
                        (PrimitiveLayouts.U16).readNative(addr + 82L),
                        (PrimitiveLayouts.U64).readNative(addr + 88L),
                        (PrimitiveLayouts.U64).readNative(addr + 96L),
                        (PrimitiveLayouts.U32).readNative(addr + 104L),
                        (PrimitiveArrayLayouts.uByteArray(0)).readNative(addr + 108L),
                    )
                override fun writeNative(addr: Long, value: Sysinfo) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.uptime)
                    (PrimitiveArrayLayouts.uLongArray(3)).writeNative(addr + 8L, value.loads)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.totalram)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.freeram)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.sharedram)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.bufferram)
                    (PrimitiveLayouts.U64).writeNative(addr + 64L, value.totalswap)
                    (PrimitiveLayouts.U64).writeNative(addr + 72L, value.freeswap)
                    (PrimitiveLayouts.U16).writeNative(addr + 80L, value.procs)
                    (PrimitiveLayouts.U16).writeNative(addr + 82L, value.pad)
                    (PrimitiveLayouts.U64).writeNative(addr + 88L, value.totalhigh)
                    (PrimitiveLayouts.U64).writeNative(addr + 96L, value.freehigh)
                    (PrimitiveLayouts.U32).writeNative(addr + 104L, value.memUnit)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeNative(addr + 108L, value.f)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sysinfo =
                    Sysinfo(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uLongArray(3)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 82),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 96),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 104),
                        (PrimitiveArrayLayouts.uByteArray(0)).readBytes(buf, offset + 108),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sysinfo) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.uptime)
                    (PrimitiveArrayLayouts.uLongArray(3)).writeBytes(buf, offset + 8, value.loads)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.totalram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.freeram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.sharedram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.bufferram)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 64, value.totalswap)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 72, value.freeswap)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 80, value.procs)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 82, value.pad)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 88, value.totalhigh)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 96, value.freehigh)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 104, value.memUnit)
                    (PrimitiveArrayLayouts.uByteArray(0)).writeBytes(buf, offset + 108, value.f)
                }
            }
    }
}

// Note(1.0,deprecate): lfs binding to be removed
public open class Statfs64(
    public var fType: ULong,
    public var fBsize: ULong,
    public var fBlocks: ULong,
    public var fBfree: ULong,
    public var fBavail: ULong,
    public var fFiles: ULong,
    public var fFfree: ULong,
    public var fFsid: FsidT,
    public var fNamelen: ULong,
    public var fFrsize: ULong,
    public var fFlags: ULong,
    public var fSpare: ULongArray,
) {
    public open fun clone(): Statfs64 = Statfs64(fType, fBsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFsid, fNamelen, fFrsize, fFlags, fSpare)
}

// Note(1.0,deprecate): lfs binding to be removed
public open class Statvfs64(
    public var fBsize: CUlong,
    public var fFrsize: CUlong,
    public var fBlocks: ULong,
    public var fBfree: ULong,
    public var fBavail: ULong,
    public var fFiles: ULong,
    public var fFfree: ULong,
    public var fFavail: ULong,
    public var fFsid: CUlong,
    public var fFlag: CUlong,
    public var fNamemax: CUlong,
    public var fSpare: IntArray,
) {
    public open fun clone(): Statvfs64 = Statvfs64(fBsize, fFrsize, fBlocks, fBfree, fBavail, fFiles, fFfree, fFavail, fFsid, fFlag, fNamemax, fSpare)

    public companion object {
        public fun typeLayout(): TypeLayout<Statvfs64> =
            object : TypeLayout<Statvfs64> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 112L
                override fun readNative(addr: Long): Statvfs64 =
                    Statvfs64(
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
                        (PrimitiveArrayLayouts.intArray(6)).readNative(addr + 88L),
                    )
                override fun writeNative(addr: Long, value: Statvfs64) {
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
                    (PrimitiveArrayLayouts.intArray(6)).writeNative(addr + 88L, value.fSpare)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Statvfs64 =
                    Statvfs64(
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
                        (PrimitiveArrayLayouts.intArray(6)).readBytes(buf, offset + 88),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Statvfs64) {
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
                    (PrimitiveArrayLayouts.intArray(6)).writeBytes(buf, offset + 88, value.fSpare)
                }
            }
    }
}

public open class PthreadBarrierT(
    public var private: LongArray,
) {
    public open fun clone(): PthreadBarrierT = PthreadBarrierT(private)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadBarrierT> =
            object : TypeLayout<PthreadBarrierT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): PthreadBarrierT =
                    PthreadBarrierT(
                        (PrimitiveArrayLayouts.longArray(4)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadBarrierT) {
                    (PrimitiveArrayLayouts.longArray(4)).writeNative(addr, value.private)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadBarrierT =
                    PthreadBarrierT(
                        (PrimitiveArrayLayouts.longArray(4)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadBarrierT) {
                    (PrimitiveArrayLayouts.longArray(4)).writeBytes(buf, offset, value.private)
                }
            }
    }
}

public open class PthreadSpinlockT(
    public var private: Long,
) {
    public open fun clone(): PthreadSpinlockT = PthreadSpinlockT(private)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadSpinlockT> =
            object : TypeLayout<PthreadSpinlockT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): PthreadSpinlockT =
                    PthreadSpinlockT(
                        (PrimitiveLayouts.I64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadSpinlockT) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.private)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadSpinlockT =
                    PthreadSpinlockT(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadSpinlockT) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.private)
                }
            }
    }
}

public open class PthreadMutexT(
    public var value: CInt,
    public var reserved: UByteArray,
) {
    public open fun clone(): PthreadMutexT = PthreadMutexT(value, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadMutexT> =
            object : TypeLayout<PthreadMutexT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 40L
                override fun readNative(addr: Long): PthreadMutexT =
                    PthreadMutexT(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(36)).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: PthreadMutexT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.value)
                    (PrimitiveArrayLayouts.uByteArray(36)).writeNative(addr + 4L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadMutexT =
                    PthreadMutexT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(36)).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadMutexT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.value)
                    (PrimitiveArrayLayouts.uByteArray(36)).writeBytes(buf, offset + 4, value.reserved)
                }
            }
    }
}

public open class PthreadCondT(
    public var value: CInt,
    public var reserved: UByteArray,
) {
    public open fun clone(): PthreadCondT = PthreadCondT(value, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadCondT> =
            object : TypeLayout<PthreadCondT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): PthreadCondT =
                    PthreadCondT(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(44)).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: PthreadCondT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.value)
                    (PrimitiveArrayLayouts.uByteArray(44)).writeNative(addr + 4L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadCondT =
                    PthreadCondT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(44)).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadCondT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.value)
                    (PrimitiveArrayLayouts.uByteArray(44)).writeBytes(buf, offset + 4, value.reserved)
                }
            }
    }
}

public open class PthreadRwlockT(
    public var numLocks: CInt,
    public var writerThreadId: CInt,
    public var pendingReaders: CInt,
    public var pendingWriters: CInt,
    public var attr: Int,
    public var reserved: UByteArray,
) {
    public open fun clone(): PthreadRwlockT = PthreadRwlockT(numLocks, writerThreadId, pendingReaders, pendingWriters, attr, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadRwlockT> =
            object : TypeLayout<PthreadRwlockT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): PthreadRwlockT =
                    PthreadRwlockT(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveArrayLayouts.uByteArray(36)).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: PthreadRwlockT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.numLocks)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.writerThreadId)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.pendingReaders)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.pendingWriters)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.attr)
                    (PrimitiveArrayLayouts.uByteArray(36)).writeNative(addr + 20L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadRwlockT =
                    PthreadRwlockT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveArrayLayouts.uByteArray(36)).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadRwlockT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.numLocks)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.writerThreadId)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.pendingReaders)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.pendingWriters)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.attr)
                    (PrimitiveArrayLayouts.uByteArray(36)).writeBytes(buf, offset + 20, value.reserved)
                }
            }
    }
}

// Note(1.0,deprecate): lfs binding to be removed
public open class Sigset64T(
    public var bits: ULongArray,
) {
    public open fun clone(): Sigset64T = Sigset64T(bits)

    public companion object {
        public fun typeLayout(): TypeLayout<Sigset64T> =
            object : TypeLayout<Sigset64T> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Sigset64T =
                    Sigset64T(
                        (PrimitiveArrayLayouts.uLongArray(1)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Sigset64T) {
                    (PrimitiveArrayLayouts.uLongArray(1)).writeNative(addr, value.bits)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sigset64T =
                    Sigset64T(
                        (PrimitiveArrayLayouts.uLongArray(1)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sigset64T) {
                    (PrimitiveArrayLayouts.uLongArray(1)).writeBytes(buf, offset, value.bits)
                }
            }
    }
}
