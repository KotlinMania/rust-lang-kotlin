@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CLong
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.ffi.CUlong
import io.github.kotlinmania.rustlang.core.ffi.CUlonglong
import io.github.kotlinmania.rustlang.core.ffi.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.GidT
import io.github.kotlinmania.rustlang.libc.SighandlerT
import io.github.kotlinmania.rustlang.libc.UidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.FsidT

public fun interface SigactionSaRestorerCallback {
    public operator fun invoke()
}

public data class SigsetT(
    public var value: ULongArray = ULongArray(1),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SigsetT) return false
        if (!this.value.contentEquals(other.value)) return false
        return true
    }

    override fun hashCode(): Int = this.value.contentHashCode()

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

public data class Sigaction(
    public var saFlags: CInt = 0,
    public var saSigaction: SighandlerT = 0uL,
    public var saMask: io.github.kotlinmania.rustlang.libc.SigsetT = io.github.kotlinmania.rustlang.libc.SigsetT(),
    public var saRestorer: SigactionSaRestorerCallback? = null,
)

// Note(1.0,deprecate): lfs binding to be removed
public data class Rlimit64(
    public var rlimCur: CUlonglong = 0uL,
    public var rlimMax: CUlonglong = 0uL,
) {
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

public class PthreadAttrT internal constructor(
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

    public constructor() : this(
        flags = 0u,
        stackBaseCtorArg = MutPtr(0L),
        stackSize = 0uL,
        guardSize = 0uL,
        schedPolicy = 0,
        schedPriority = 0,
        reserved = UByteArray(16),
    )

    public operator fun component1(): UInt = this.flags

    public operator fun component2(): MutPtr = this.stackBase

    public operator fun component3(): SizeT = this.stackSize

    public operator fun component4(): SizeT = this.guardSize

    public operator fun component5(): Int = this.schedPolicy

    public operator fun component6(): Int = this.schedPriority

    public operator fun component7(): UByteArray = this.reserved

    public fun copy(flags: UInt = this.flags, stackBase: MutPtr = this.stackBase, stackSize: SizeT = this.stackSize, guardSize: SizeT = this.guardSize, schedPolicy: Int = this.schedPolicy, schedPriority: Int = this.schedPriority, reserved: UByteArray = this.reserved): PthreadAttrT = PthreadAttrT(flags = flags, stackBaseCtorArg = stackBase, stackSize = stackSize, guardSize = guardSize, schedPolicy = schedPolicy, schedPriority = schedPriority, reserved = reserved)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadAttrT) return false
        if (this.flags != other.flags) return false
        if (this.stackBase != other.stackBase) return false
        if (this.stackSize != other.stackSize) return false
        if (this.guardSize != other.guardSize) return false
        if (this.schedPolicy != other.schedPolicy) return false
        if (this.schedPriority != other.schedPriority) return false
        if (!this.reserved.contentEquals(other.reserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.flags.hashCode()
        result = 31 * result + this.stackBase.hashCode()
        result = 31 * result + this.stackSize.hashCode()
        result = 31 * result + this.guardSize.hashCode()
        result = 31 * result + this.schedPolicy.hashCode()
        result = 31 * result + this.schedPriority.hashCode()
        result = 31 * result + this.reserved.contentHashCode()
        return result
    }

    public fun clone(): PthreadAttrT = PthreadAttrT(flags, stackBase, stackSize, guardSize, schedPolicy, schedPriority, reserved)

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
            flags: UInt = 0u,
            stackBase: MutPtr = MutPtr(0L),
            stackSize: SizeT = 0uL,
            guardSize: SizeT = 0uL,
            schedPolicy: Int = 0,
            schedPriority: Int = 0,
            reserved: UByteArray = UByteArray(16),
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

public class Passwd internal constructor(
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

    public constructor() : this(
        pwNameCtorArg = MutPtr(0L),
        pwPasswdCtorArg = MutPtr(0L),
        pwUid = 0,
        pwGid = 0,
        pwGecosCtorArg = MutPtr(0L),
        pwDirCtorArg = MutPtr(0L),
        pwShellCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.pwName

    public operator fun component2(): MutPtr = this.pwPasswd

    public operator fun component3(): UidT = this.pwUid

    public operator fun component4(): GidT = this.pwGid

    public operator fun component5(): MutPtr = this.pwGecos

    public operator fun component6(): MutPtr = this.pwDir

    public operator fun component7(): MutPtr = this.pwShell

    public fun copy(pwName: MutPtr = this.pwName, pwPasswd: MutPtr = this.pwPasswd, pwUid: UidT = this.pwUid, pwGid: GidT = this.pwGid, pwGecos: MutPtr = this.pwGecos, pwDir: MutPtr = this.pwDir, pwShell: MutPtr = this.pwShell): Passwd = Passwd(pwNameCtorArg = pwName, pwPasswdCtorArg = pwPasswd, pwUid = pwUid, pwGid = pwGid, pwGecosCtorArg = pwGecos, pwDirCtorArg = pwDir, pwShellCtorArg = pwShell)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Passwd) return false
        if (this.pwName != other.pwName) return false
        if (this.pwPasswd != other.pwPasswd) return false
        if (this.pwUid != other.pwUid) return false
        if (this.pwGid != other.pwGid) return false
        if (this.pwGecos != other.pwGecos) return false
        if (this.pwDir != other.pwDir) return false
        if (this.pwShell != other.pwShell) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.pwName.hashCode()
        result = 31 * result + this.pwPasswd.hashCode()
        result = 31 * result + this.pwUid.hashCode()
        result = 31 * result + this.pwGid.hashCode()
        result = 31 * result + this.pwGecos.hashCode()
        result = 31 * result + this.pwDir.hashCode()
        result = 31 * result + this.pwShell.hashCode()
        return result
    }

    public fun clone(): Passwd = Passwd(pwName, pwPasswd, pwUid, pwGid, pwGecos, pwDir, pwShell)

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
            pwName: MutPtr = MutPtr(0L),
            pwPasswd: MutPtr = MutPtr(0L),
            pwUid: UidT = 0,
            pwGid: GidT = 0,
            pwGecos: MutPtr = MutPtr(0L),
            pwDir: MutPtr = MutPtr(0L),
            pwShell: MutPtr = MutPtr(0L),
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

public data class Statfs(
    public var fType: ULong = 0uL,
    public var fBsize: ULong = 0uL,
    public var fBlocks: ULong = 0uL,
    public var fBfree: ULong = 0uL,
    public var fBavail: ULong = 0uL,
    public var fFiles: ULong = 0uL,
    public var fFfree: ULong = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fNamelen: ULong = 0uL,
    public var fFrsize: ULong = 0uL,
    public var fFlags: ULong = 0uL,
    public var fSpare: ULongArray = ULongArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statfs) return false
        if (this.fType != other.fType) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fNamelen != other.fNamelen) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fFlags != other.fFlags) return false
        if (!this.fSpare.contentEquals(other.fSpare)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fType.hashCode()
        result = 31 * result + this.fBsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fNamelen.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fFlags.hashCode()
        result = 31 * result + this.fSpare.contentHashCode()
        return result
    }
}

public data class Sysinfo(
    public var uptime: CLong = 0L,
    public var loads: ULongArray = ULongArray(3),
    public var totalram: CUlong = 0uL,
    public var freeram: CUlong = 0uL,
    public var sharedram: CUlong = 0uL,
    public var bufferram: CUlong = 0uL,
    public var totalswap: CUlong = 0uL,
    public var freeswap: CUlong = 0uL,
    public var procs: CUshort = 0u,
    public var pad: CUshort = 0u,
    public var totalhigh: CUlong = 0uL,
    public var freehigh: CUlong = 0uL,
    public var memUnit: CUint = 0u,
    public var f: UByteArray = UByteArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sysinfo) return false
        if (this.uptime != other.uptime) return false
        if (!this.loads.contentEquals(other.loads)) return false
        if (this.totalram != other.totalram) return false
        if (this.freeram != other.freeram) return false
        if (this.sharedram != other.sharedram) return false
        if (this.bufferram != other.bufferram) return false
        if (this.totalswap != other.totalswap) return false
        if (this.freeswap != other.freeswap) return false
        if (this.procs != other.procs) return false
        if (this.pad != other.pad) return false
        if (this.totalhigh != other.totalhigh) return false
        if (this.freehigh != other.freehigh) return false
        if (this.memUnit != other.memUnit) return false
        if (!this.f.contentEquals(other.f)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.uptime.hashCode()
        result = 31 * result + this.loads.contentHashCode()
        result = 31 * result + this.totalram.hashCode()
        result = 31 * result + this.freeram.hashCode()
        result = 31 * result + this.sharedram.hashCode()
        result = 31 * result + this.bufferram.hashCode()
        result = 31 * result + this.totalswap.hashCode()
        result = 31 * result + this.freeswap.hashCode()
        result = 31 * result + this.procs.hashCode()
        result = 31 * result + this.pad.hashCode()
        result = 31 * result + this.totalhigh.hashCode()
        result = 31 * result + this.freehigh.hashCode()
        result = 31 * result + this.memUnit.hashCode()
        result = 31 * result + this.f.contentHashCode()
        return result
    }

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
public data class Statfs64(
    public var fType: ULong = 0uL,
    public var fBsize: ULong = 0uL,
    public var fBlocks: ULong = 0uL,
    public var fBfree: ULong = 0uL,
    public var fBavail: ULong = 0uL,
    public var fFiles: ULong = 0uL,
    public var fFfree: ULong = 0uL,
    public var fFsid: FsidT = FsidT(),
    public var fNamelen: ULong = 0uL,
    public var fFrsize: ULong = 0uL,
    public var fFlags: ULong = 0uL,
    public var fSpare: ULongArray = ULongArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statfs64) return false
        if (this.fType != other.fType) return false
        if (this.fBsize != other.fBsize) return false
        if (this.fBlocks != other.fBlocks) return false
        if (this.fBfree != other.fBfree) return false
        if (this.fBavail != other.fBavail) return false
        if (this.fFiles != other.fFiles) return false
        if (this.fFfree != other.fFfree) return false
        if (this.fFsid != other.fFsid) return false
        if (this.fNamelen != other.fNamelen) return false
        if (this.fFrsize != other.fFrsize) return false
        if (this.fFlags != other.fFlags) return false
        if (!this.fSpare.contentEquals(other.fSpare)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fType.hashCode()
        result = 31 * result + this.fBsize.hashCode()
        result = 31 * result + this.fBlocks.hashCode()
        result = 31 * result + this.fBfree.hashCode()
        result = 31 * result + this.fBavail.hashCode()
        result = 31 * result + this.fFiles.hashCode()
        result = 31 * result + this.fFfree.hashCode()
        result = 31 * result + this.fFsid.hashCode()
        result = 31 * result + this.fNamelen.hashCode()
        result = 31 * result + this.fFrsize.hashCode()
        result = 31 * result + this.fFlags.hashCode()
        result = 31 * result + this.fSpare.contentHashCode()
        return result
    }
}

// Note(1.0,deprecate): lfs binding to be removed
public data class Statvfs64(
    public var fBsize: CUlong = 0uL,
    public var fFrsize: CUlong = 0uL,
    public var fBlocks: ULong = 0uL,
    public var fBfree: ULong = 0uL,
    public var fBavail: ULong = 0uL,
    public var fFiles: ULong = 0uL,
    public var fFfree: ULong = 0uL,
    public var fFavail: ULong = 0uL,
    public var fFsid: CUlong = 0uL,
    public var fFlag: CUlong = 0uL,
    public var fNamemax: CUlong = 0uL,
    public var fSpare: IntArray = IntArray(6),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Statvfs64) return false
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
        if (!this.fSpare.contentEquals(other.fSpare)) return false
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
        result = 31 * result + this.fSpare.contentHashCode()
        return result
    }

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

public data class PthreadBarrierT(
    public var private: LongArray = LongArray(4),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadBarrierT) return false
        if (!this.private.contentEquals(other.private)) return false
        return true
    }

    override fun hashCode(): Int = this.private.contentHashCode()

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

public data class PthreadSpinlockT(
    public var private: Long = 0L,
) {
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

public data class PthreadMutexT(
    public var value: CInt = 0,
    public var reserved: UByteArray = UByteArray(36),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadMutexT) return false
        if (this.value != other.value) return false
        if (!this.reserved.contentEquals(other.reserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.value.hashCode()
        result = 31 * result + this.reserved.contentHashCode()
        return result
    }

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

public data class PthreadCondT(
    public var value: CInt = 0,
    public var reserved: UByteArray = UByteArray(44),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadCondT) return false
        if (this.value != other.value) return false
        if (!this.reserved.contentEquals(other.reserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.value.hashCode()
        result = 31 * result + this.reserved.contentHashCode()
        return result
    }

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

public data class PthreadRwlockT(
    public var numLocks: CInt = 0,
    public var writerThreadId: CInt = 0,
    public var pendingReaders: CInt = 0,
    public var pendingWriters: CInt = 0,
    public var attr: Int = 0,
    public var reserved: UByteArray = UByteArray(36),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PthreadRwlockT) return false
        if (this.numLocks != other.numLocks) return false
        if (this.writerThreadId != other.writerThreadId) return false
        if (this.pendingReaders != other.pendingReaders) return false
        if (this.pendingWriters != other.pendingWriters) return false
        if (this.attr != other.attr) return false
        if (!this.reserved.contentEquals(other.reserved)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.numLocks.hashCode()
        result = 31 * result + this.writerThreadId.hashCode()
        result = 31 * result + this.pendingReaders.hashCode()
        result = 31 * result + this.pendingWriters.hashCode()
        result = 31 * result + this.attr.hashCode()
        result = 31 * result + this.reserved.contentHashCode()
        return result
    }

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
public data class Sigset64T(
    public var bits: ULongArray = ULongArray(1),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sigset64T) return false
        if (!this.bits.contentEquals(other.bits)) return false
        return true
    }

    override fun hashCode(): Int = this.bits.contentHashCode()

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
