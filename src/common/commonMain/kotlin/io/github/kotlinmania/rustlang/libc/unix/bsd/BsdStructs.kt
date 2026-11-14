@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd

import io.github.kotlinmania.rustlang.core.ffi.CInt
import io.github.kotlinmania.rustlang.core.ffi.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.GidT
import io.github.kotlinmania.rustlang.libc.In6Addr
import io.github.kotlinmania.rustlang.libc.InPortT
import io.github.kotlinmania.rustlang.libc.TimeT
import io.github.kotlinmania.rustlang.libc.UidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.FD_SETSIZE

// Note(exhaustive): review for 1.0
public data class Sockaddr(
    public var saLen: UByte = 0u,
    public var saFamily: SaFamilyT = 0u,
    public var saData: UByteArray = UByteArray(14),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Sockaddr) return false
        if (this.saLen != other.saLen) return false
        if (this.saFamily != other.saFamily) return false
        if (!this.saData.contentEquals(other.saData)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.saLen.hashCode()
        result = 31 * result + this.saFamily.hashCode()
        result = 31 * result + this.saData.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Sockaddr> =
            object : TypeLayout<Sockaddr> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Sockaddr =
                    Sockaddr(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveArrayLayouts.uByteArray(14)).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Sockaddr) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.saLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.saFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeNative(addr + 2L, value.saData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sockaddr =
                    Sockaddr(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveArrayLayouts.uByteArray(14)).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sockaddr) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.saLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.saFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeBytes(buf, offset + 2, value.saData)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class SockaddrIn6(
    public var sin6Len: UByte = 0u,
    public var sin6Family: SaFamilyT = 0u,
    public var sin6Port: InPortT = 0u,
    public var sin6Flowinfo: UInt = 0u,
    public var sin6Addr: In6Addr = In6Addr(),
    public var sin6ScopeId: UInt = 0u,
)

// Note(exhaustive): review for 1.0
public class Passwd internal constructor(
    pwNameCtorArg: MutPtr,
    pwPasswdCtorArg: MutPtr,
    public var pwUid: UidT,
    public var pwGid: GidT,
    public var pwChange: TimeT,
    pwClassCtorArg: MutPtr,
    pwGecosCtorArg: MutPtr,
    pwDirCtorArg: MutPtr,
    pwShellCtorArg: MutPtr,
    public var pwExpire: TimeT,
    public var pwFields: CInt,
) {
    public var pwName: MutPtr = pwNameCtorArg
        internal set

    public var pwPasswd: MutPtr = pwPasswdCtorArg
        internal set

    public var pwClass: MutPtr = pwClassCtorArg
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
        pwChange = 0L,
        pwClassCtorArg = MutPtr(0L),
        pwGecosCtorArg = MutPtr(0L),
        pwDirCtorArg = MutPtr(0L),
        pwShellCtorArg = MutPtr(0L),
        pwExpire = 0L,
        pwFields = 0,
    )

    public operator fun component1(): MutPtr = this.pwName

    public operator fun component2(): MutPtr = this.pwPasswd

    public operator fun component3(): UidT = this.pwUid

    public operator fun component4(): GidT = this.pwGid

    public operator fun component5(): TimeT = this.pwChange

    public operator fun component6(): MutPtr = this.pwClass

    public operator fun component7(): MutPtr = this.pwGecos

    public operator fun component8(): MutPtr = this.pwDir

    public operator fun component9(): MutPtr = this.pwShell

    public operator fun component10(): TimeT = this.pwExpire

    public operator fun component11(): CInt = this.pwFields

    public fun copy(pwName: MutPtr = this.pwName, pwPasswd: MutPtr = this.pwPasswd, pwUid: UidT = this.pwUid, pwGid: GidT = this.pwGid, pwChange: TimeT = this.pwChange, pwClass: MutPtr = this.pwClass, pwGecos: MutPtr = this.pwGecos, pwDir: MutPtr = this.pwDir, pwShell: MutPtr = this.pwShell, pwExpire: TimeT = this.pwExpire, pwFields: CInt = this.pwFields): Passwd = Passwd(pwNameCtorArg = pwName, pwPasswdCtorArg = pwPasswd, pwUid = pwUid, pwGid = pwGid, pwChange = pwChange, pwClassCtorArg = pwClass, pwGecosCtorArg = pwGecos, pwDirCtorArg = pwDir, pwShellCtorArg = pwShell, pwExpire = pwExpire, pwFields = pwFields)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Passwd) return false
        if (this.pwName != other.pwName) return false
        if (this.pwPasswd != other.pwPasswd) return false
        if (this.pwUid != other.pwUid) return false
        if (this.pwGid != other.pwGid) return false
        if (this.pwChange != other.pwChange) return false
        if (this.pwClass != other.pwClass) return false
        if (this.pwGecos != other.pwGecos) return false
        if (this.pwDir != other.pwDir) return false
        if (this.pwShell != other.pwShell) return false
        if (this.pwExpire != other.pwExpire) return false
        if (this.pwFields != other.pwFields) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.pwName.hashCode()
        result = 31 * result + this.pwPasswd.hashCode()
        result = 31 * result + this.pwUid.hashCode()
        result = 31 * result + this.pwGid.hashCode()
        result = 31 * result + this.pwChange.hashCode()
        result = 31 * result + this.pwClass.hashCode()
        result = 31 * result + this.pwGecos.hashCode()
        result = 31 * result + this.pwDir.hashCode()
        result = 31 * result + this.pwShell.hashCode()
        result = 31 * result + this.pwExpire.hashCode()
        result = 31 * result + this.pwFields.hashCode()
        return result
    }

    public fun clone(): Passwd = Passwd(pwName, pwPasswd, pwUid, pwGid, pwChange, pwClass, pwGecos, pwDir, pwShell, pwExpire, pwFields)

    public companion object {
        public fun typeLayout(): TypeLayout<Passwd> =
            object : TypeLayout<Passwd> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 80L
                override fun readNative(addr: Long): Passwd =
                    Passwd(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I64).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveLayouts.I64).readNative(addr + 64L),
                        (PrimitiveLayouts.I32).readNative(addr + 72L),
                    )
                override fun writeNative(addr: Long, value: Passwd) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.pwName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.pwPasswd)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.pwUid)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.pwGid)
                    (PrimitiveLayouts.I64).writeNative(addr + 24L, value.pwChange)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.pwClass)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.pwGecos)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.pwDir)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.pwShell)
                    (PrimitiveLayouts.I64).writeNative(addr + 64L, value.pwExpire)
                    (PrimitiveLayouts.I32).writeNative(addr + 72L, value.pwFields)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Passwd =
                    Passwd(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 72),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Passwd) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.pwName)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.pwPasswd)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.pwUid)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.pwGid)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 24, value.pwChange)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.pwClass)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.pwGecos)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.pwDir)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.pwShell)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 64, value.pwExpire)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 72, value.pwFields)
                }
            }

        public operator fun invoke(
            pwName: MutPtr = MutPtr(0L),
            pwPasswd: MutPtr = MutPtr(0L),
            pwUid: UidT = 0,
            pwGid: GidT = 0,
            pwChange: TimeT = 0L,
            pwClass: MutPtr = MutPtr(0L),
            pwGecos: MutPtr = MutPtr(0L),
            pwDir: MutPtr = MutPtr(0L),
            pwShell: MutPtr = MutPtr(0L),
            pwExpire: TimeT = 0L,
            pwFields: CInt = 0,
        ): Passwd = Passwd(
            pwNameCtorArg = pwName,
            pwPasswdCtorArg = pwPasswd,
            pwUid = pwUid,
            pwGid = pwGid,
            pwChange = pwChange,
            pwClassCtorArg = pwClass,
            pwGecosCtorArg = pwGecos,
            pwDirCtorArg = pwDir,
            pwShellCtorArg = pwShell,
            pwExpire = pwExpire,
            pwFields = pwFields,
        )
    }
}

// Note(exhaustive): review for 1.0
public class Ifaddrs internal constructor(
    ifaNextCtorArg: MutPtr,
    ifaNameCtorArg: MutPtr,
    public var ifaFlags: CUint,
    ifaAddrCtorArg: MutPtr,
    ifaNetmaskCtorArg: MutPtr,
    ifaDstaddrCtorArg: MutPtr,
    ifaDataCtorArg: MutPtr,
) {
    public var ifaNext: MutPtr = ifaNextCtorArg
        internal set

    public var ifaName: MutPtr = ifaNameCtorArg
        internal set

    public var ifaAddr: MutPtr = ifaAddrCtorArg
        internal set

    public var ifaNetmask: MutPtr = ifaNetmaskCtorArg
        internal set

    public var ifaDstaddr: MutPtr = ifaDstaddrCtorArg
        internal set

    public var ifaData: MutPtr = ifaDataCtorArg
        internal set

    public constructor() : this(
        ifaNextCtorArg = MutPtr(0L),
        ifaNameCtorArg = MutPtr(0L),
        ifaFlags = 0u,
        ifaAddrCtorArg = MutPtr(0L),
        ifaNetmaskCtorArg = MutPtr(0L),
        ifaDstaddrCtorArg = MutPtr(0L),
        ifaDataCtorArg = MutPtr(0L),
    )

    public operator fun component1(): MutPtr = this.ifaNext

    public operator fun component2(): MutPtr = this.ifaName

    public operator fun component3(): CUint = this.ifaFlags

    public operator fun component4(): MutPtr = this.ifaAddr

    public operator fun component5(): MutPtr = this.ifaNetmask

    public operator fun component6(): MutPtr = this.ifaDstaddr

    public operator fun component7(): MutPtr = this.ifaData

    public fun copy(ifaNext: MutPtr = this.ifaNext, ifaName: MutPtr = this.ifaName, ifaFlags: CUint = this.ifaFlags, ifaAddr: MutPtr = this.ifaAddr, ifaNetmask: MutPtr = this.ifaNetmask, ifaDstaddr: MutPtr = this.ifaDstaddr, ifaData: MutPtr = this.ifaData): Ifaddrs = Ifaddrs(ifaNextCtorArg = ifaNext, ifaNameCtorArg = ifaName, ifaFlags = ifaFlags, ifaAddrCtorArg = ifaAddr, ifaNetmaskCtorArg = ifaNetmask, ifaDstaddrCtorArg = ifaDstaddr, ifaDataCtorArg = ifaData)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ifaddrs) return false
        if (this.ifaNext != other.ifaNext) return false
        if (this.ifaName != other.ifaName) return false
        if (this.ifaFlags != other.ifaFlags) return false
        if (this.ifaAddr != other.ifaAddr) return false
        if (this.ifaNetmask != other.ifaNetmask) return false
        if (this.ifaDstaddr != other.ifaDstaddr) return false
        if (this.ifaData != other.ifaData) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifaNext.hashCode()
        result = 31 * result + this.ifaName.hashCode()
        result = 31 * result + this.ifaFlags.hashCode()
        result = 31 * result + this.ifaAddr.hashCode()
        result = 31 * result + this.ifaNetmask.hashCode()
        result = 31 * result + this.ifaDstaddr.hashCode()
        result = 31 * result + this.ifaData.hashCode()
        return result
    }

    public fun clone(): Ifaddrs = Ifaddrs(ifaNext, ifaName, ifaFlags, ifaAddr, ifaNetmask, ifaDstaddr, ifaData)

    public companion object {
        public fun typeLayout(): TypeLayout<Ifaddrs> =
            object : TypeLayout<Ifaddrs> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): Ifaddrs =
                    Ifaddrs(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Ifaddrs) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.ifaNext)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.ifaName)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.ifaFlags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.ifaAddr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.ifaNetmask)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.ifaDstaddr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.ifaData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Ifaddrs =
                    Ifaddrs(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Ifaddrs) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.ifaNext)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.ifaName)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.ifaFlags)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.ifaAddr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.ifaNetmask)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.ifaDstaddr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.ifaData)
                }
            }

        public operator fun invoke(
            ifaNext: MutPtr = MutPtr(0L),
            ifaName: MutPtr = MutPtr(0L),
            ifaFlags: CUint = 0u,
            ifaAddr: MutPtr = MutPtr(0L),
            ifaNetmask: MutPtr = MutPtr(0L),
            ifaDstaddr: MutPtr = MutPtr(0L),
            ifaData: MutPtr = MutPtr(0L),
        ): Ifaddrs = Ifaddrs(
            ifaNextCtorArg = ifaNext,
            ifaNameCtorArg = ifaName,
            ifaFlags = ifaFlags,
            ifaAddrCtorArg = ifaAddr,
            ifaNetmaskCtorArg = ifaNetmask,
            ifaDstaddrCtorArg = ifaDstaddr,
            ifaDataCtorArg = ifaData,
        )
    }
}

public data class FdSet(
    public var fdsBits: IntArray = IntArray(FD_SETSIZE / 32),
    public var nonExhaustive: Unit = Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FdSet) return false
        if (!this.fdsBits.contentEquals(other.fdsBits)) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fdsBits.contentHashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<FdSet> =
            object : TypeLayout<FdSet> {
                override val alignBytes: Long = 4L
                private val off1: Long = alignUpBytes(((FD_SETSIZE / 32).toLong() * (PrimitiveLayouts.I32).strideBytes), 1L)
                override val sizeBytes: Long = alignUpBytes((off1 + 0L), 4L)
                override fun readNative(addr: Long): FdSet =
                    FdSet(
                        (PrimitiveArrayLayouts.intArray((FD_SETSIZE / 32).toInt())).readNative(addr),
                        (PrimitiveLayouts.UNIT).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: FdSet) {
                    (PrimitiveArrayLayouts.intArray((FD_SETSIZE / 32).toInt())).writeNative(addr, value.fdsBits)
                    (PrimitiveLayouts.UNIT).writeNative(addr + off1, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FdSet =
                    FdSet(
                        (PrimitiveArrayLayouts.intArray((FD_SETSIZE / 32).toInt())).readBytes(buf, offset),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FdSet) {
                    (PrimitiveArrayLayouts.intArray((FD_SETSIZE / 32).toInt())).writeBytes(buf, offset, value.fdsBits)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + off1.toInt(), value.nonExhaustive)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class Msghdr internal constructor(
    msgNameCtorArg: MutPtr,
    public var msgNamelen: io.github.kotlinmania.rustlang.libc.SocklenT,
    msgIovCtorArg: MutPtr,
    public var msgIovlen: CInt,
    msgControlCtorArg: MutPtr,
    public var msgControllen: io.github.kotlinmania.rustlang.libc.SocklenT,
    public var msgFlags: CInt,
) {
    public var msgName: MutPtr = msgNameCtorArg
        internal set

    public var msgIov: MutPtr = msgIovCtorArg
        internal set

    public var msgControl: MutPtr = msgControlCtorArg
        internal set

    public constructor() : this(
        msgNameCtorArg = MutPtr(0L),
        msgNamelen = 0u,
        msgIovCtorArg = MutPtr(0L),
        msgIovlen = 0,
        msgControlCtorArg = MutPtr(0L),
        msgControllen = 0u,
        msgFlags = 0,
    )

    public operator fun component1(): MutPtr = this.msgName

    public operator fun component2(): io.github.kotlinmania.rustlang.libc.SocklenT = this.msgNamelen

    public operator fun component3(): MutPtr = this.msgIov

    public operator fun component4(): CInt = this.msgIovlen

    public operator fun component5(): MutPtr = this.msgControl

    public operator fun component6(): io.github.kotlinmania.rustlang.libc.SocklenT = this.msgControllen

    public operator fun component7(): CInt = this.msgFlags

    public fun copy(msgName: MutPtr = this.msgName, msgNamelen: io.github.kotlinmania.rustlang.libc.SocklenT = this.msgNamelen, msgIov: MutPtr = this.msgIov, msgIovlen: CInt = this.msgIovlen, msgControl: MutPtr = this.msgControl, msgControllen: io.github.kotlinmania.rustlang.libc.SocklenT = this.msgControllen, msgFlags: CInt = this.msgFlags): Msghdr = Msghdr(msgNameCtorArg = msgName, msgNamelen = msgNamelen, msgIovCtorArg = msgIov, msgIovlen = msgIovlen, msgControlCtorArg = msgControl, msgControllen = msgControllen, msgFlags = msgFlags)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Msghdr) return false
        if (this.msgName != other.msgName) return false
        if (this.msgNamelen != other.msgNamelen) return false
        if (this.msgIov != other.msgIov) return false
        if (this.msgIovlen != other.msgIovlen) return false
        if (this.msgControl != other.msgControl) return false
        if (this.msgControllen != other.msgControllen) return false
        if (this.msgFlags != other.msgFlags) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.msgName.hashCode()
        result = 31 * result + this.msgNamelen.hashCode()
        result = 31 * result + this.msgIov.hashCode()
        result = 31 * result + this.msgIovlen.hashCode()
        result = 31 * result + this.msgControl.hashCode()
        result = 31 * result + this.msgControllen.hashCode()
        result = 31 * result + this.msgFlags.hashCode()
        return result
    }

    public fun clone(): Msghdr = Msghdr(msgName, msgNamelen, msgIov, msgIovlen, msgControl, msgControllen, msgFlags)

    public companion object {
        public fun typeLayout(): TypeLayout<Msghdr> =
            object : TypeLayout<Msghdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Msghdr =
                    Msghdr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 44L),
                    )
                override fun writeNative(addr: Long, value: Msghdr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.msgName)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.msgNamelen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.msgIov)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.msgIovlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.msgControl)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.msgControllen)
                    (PrimitiveLayouts.I32).writeNative(addr + 44L, value.msgFlags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Msghdr =
                    Msghdr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 44),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Msghdr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.msgName)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.msgNamelen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.msgIov)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.msgIovlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.msgControl)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.msgControllen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 44, value.msgFlags)
                }
            }

        public operator fun invoke(
            msgName: MutPtr = MutPtr(0L),
            msgNamelen: io.github.kotlinmania.rustlang.libc.SocklenT = 0u,
            msgIov: MutPtr = MutPtr(0L),
            msgIovlen: CInt = 0,
            msgControl: MutPtr = MutPtr(0L),
            msgControllen: io.github.kotlinmania.rustlang.libc.SocklenT = 0u,
            msgFlags: CInt = 0,
        ): Msghdr = Msghdr(
            msgNameCtorArg = msgName,
            msgNamelen = msgNamelen,
            msgIovCtorArg = msgIov,
            msgIovlen = msgIovlen,
            msgControlCtorArg = msgControl,
            msgControllen = msgControllen,
            msgFlags = msgFlags,
        )
    }
}

// Note(exhaustive): review for 1.0
public data class Cmsghdr(
    public var cmsgLen: io.github.kotlinmania.rustlang.libc.SocklenT = 0u,
    public var cmsgLevel: CInt = 0,
    public var cmsgType: CInt = 0,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Cmsghdr> =
            object : TypeLayout<Cmsghdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): Cmsghdr =
                    Cmsghdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Cmsghdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cmsgLen)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.cmsgLevel)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.cmsgType)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Cmsghdr =
                    Cmsghdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Cmsghdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cmsgLen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.cmsgLevel)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.cmsgType)
                }
            }
    }
}

public data class FsidT(
    public var fsidVal: IntArray = IntArray(2),
    public var nonExhaustive: Unit = Unit,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FsidT) return false
        if (!this.fsidVal.contentEquals(other.fsidVal)) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.fsidVal.contentHashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<FsidT> =
            object : TypeLayout<FsidT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FsidT =
                    FsidT(
                        (PrimitiveArrayLayouts.intArray(2)).readNative(addr),
                        (PrimitiveLayouts.UNIT).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: FsidT) {
                    (PrimitiveArrayLayouts.intArray(2)).writeNative(addr, value.fsidVal)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 8L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FsidT =
                    FsidT(
                        (PrimitiveArrayLayouts.intArray(2)).readBytes(buf, offset),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FsidT) {
                    (PrimitiveArrayLayouts.intArray(2)).writeBytes(buf, offset, value.fsidVal)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 8, value.nonExhaustive)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public class IfNameindex internal constructor(
    public var ifIndex: CUint,
    ifNameCtorArg: MutPtr,
) {
    public var ifName: MutPtr = ifNameCtorArg
        internal set

    public constructor() : this(
        ifIndex = 0u,
        ifNameCtorArg = MutPtr(0L),
    )

    public operator fun component1(): CUint = this.ifIndex

    public operator fun component2(): MutPtr = this.ifName

    public fun copy(ifIndex: CUint = this.ifIndex, ifName: MutPtr = this.ifName): IfNameindex = IfNameindex(ifIndex = ifIndex, ifNameCtorArg = ifName)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IfNameindex) return false
        if (this.ifIndex != other.ifIndex) return false
        if (this.ifName != other.ifName) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.ifIndex.hashCode()
        result = 31 * result + this.ifName.hashCode()
        return result
    }

    public fun clone(): IfNameindex = IfNameindex(ifIndex, ifName)

    public companion object {
        public fun typeLayout(): TypeLayout<IfNameindex> =
            object : TypeLayout<IfNameindex> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): IfNameindex =
                    IfNameindex(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: IfNameindex) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.ifIndex)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.ifName)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IfNameindex =
                    IfNameindex(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IfNameindex) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.ifIndex)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.ifName)
                }
            }

        public operator fun invoke(
            ifIndex: CUint = 0u,
            ifName: MutPtr = MutPtr(0L),
        ): IfNameindex = IfNameindex(
            ifIndex = ifIndex,
            ifNameCtorArg = ifName,
        )
    }
}

public class RegexT internal constructor(
    public var reMagic: CInt,
    public var reNsub: SizeT,
    reEndpCtorArg: ConstPtr,
    reGCtorArg: MutPtr,
    public var nonExhaustive: Unit,
) {
    public var reEndp: ConstPtr = reEndpCtorArg
        internal set

    public var reG: MutPtr = reGCtorArg
        internal set

    public constructor() : this(
        reMagic = 0,
        reNsub = 0uL,
        reEndpCtorArg = ConstPtr(0L),
        reGCtorArg = MutPtr(0L),
        nonExhaustive = Unit,
    )

    public operator fun component1(): CInt = this.reMagic

    public operator fun component2(): SizeT = this.reNsub

    public operator fun component3(): ConstPtr = this.reEndp

    public operator fun component4(): MutPtr = this.reG

    public operator fun component5(): Unit = this.nonExhaustive

    public fun copy(reMagic: CInt = this.reMagic, reNsub: SizeT = this.reNsub, reEndp: ConstPtr = this.reEndp, reG: MutPtr = this.reG, nonExhaustive: Unit = this.nonExhaustive): RegexT = RegexT(reMagic = reMagic, reNsub = reNsub, reEndpCtorArg = reEndp, reGCtorArg = reG, nonExhaustive = nonExhaustive)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RegexT) return false
        if (this.reMagic != other.reMagic) return false
        if (this.reNsub != other.reNsub) return false
        if (this.reEndp != other.reEndp) return false
        if (this.reG != other.reG) return false
        if (this.nonExhaustive != other.nonExhaustive) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.reMagic.hashCode()
        result = 31 * result + this.reNsub.hashCode()
        result = 31 * result + this.reEndp.hashCode()
        result = 31 * result + this.reG.hashCode()
        result = 31 * result + this.nonExhaustive.hashCode()
        return result
    }

    public fun clone(): RegexT = RegexT(reMagic, reNsub, reEndp, reG, nonExhaustive)

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
                        (PrimitiveLayouts.UNIT).readNative(addr + 32L),
                    )
                override fun writeNative(addr: Long, value: RegexT) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.reMagic)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.reNsub)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 16L, value.reEndp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.reG)
                    (PrimitiveLayouts.UNIT).writeNative(addr + 32L, value.nonExhaustive)
                }
                override fun readBytes(buf: ByteArray, offset: Int): RegexT =
                    RegexT(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset + 32),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: RegexT) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.reMagic)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.reNsub)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 16, value.reEndp)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.reG)
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset + 32, value.nonExhaustive)
                }
            }

        public operator fun invoke(
            reMagic: CInt = 0,
            reNsub: SizeT = 0uL,
            reEndp: ConstPtr = ConstPtr(0L),
            reG: MutPtr = MutPtr(0L),
            nonExhaustive: Unit = Unit,
        ): RegexT = RegexT(
            reMagic = reMagic,
            reNsub = reNsub,
            reEndpCtorArg = reEndp,
            reGCtorArg = reG,
            nonExhaustive = nonExhaustive,
        )
    }
}

// Note(exhaustive): review for 1.0
public data class RegmatchT(
    public var rmSo: RegoffT = 0L,
    public var rmEo: RegoffT = 0L,
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

// Note(exhaustive): review for 1.0
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

// Note(exhaustive): review for 1.0
public data class SockaddrUn(
    public var sunLen: UByte = 0u,
    public var sunFamily: SaFamilyT = 0u,
    public var sunPath: UByteArray = UByteArray(104),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SockaddrUn) return false
        if (this.sunLen != other.sunLen) return false
        if (this.sunFamily != other.sunFamily) return false
        if (!this.sunPath.contentEquals(other.sunPath)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sunLen.hashCode()
        result = 31 * result + this.sunFamily.hashCode()
        result = 31 * result + this.sunPath.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrUn> =
            object : TypeLayout<SockaddrUn> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 106L
                override fun readNative(addr: Long): SockaddrUn =
                    SockaddrUn(
                        (PrimitiveLayouts.U8).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 1L),
                        (PrimitiveArrayLayouts.uByteArray(104)).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: SockaddrUn) {
                    (PrimitiveLayouts.U8).writeNative(addr, value.sunLen)
                    (PrimitiveLayouts.U8).writeNative(addr + 1L, value.sunFamily)
                    (PrimitiveArrayLayouts.uByteArray(104)).writeNative(addr + 2L, value.sunPath)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrUn =
                    SockaddrUn(
                        (PrimitiveLayouts.U8).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 1),
                        (PrimitiveArrayLayouts.uByteArray(104)).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrUn) {
                    (PrimitiveLayouts.U8).writeBytes(buf, offset, value.sunLen)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 1, value.sunFamily)
                    (PrimitiveArrayLayouts.uByteArray(104)).writeBytes(buf, offset + 2, value.sunPath)
                }
            }
    }
}

// Note(exhaustive): review for 1.0
public data class Utsname(
    public var sysname: UByteArray = UByteArray(256),
    public var nodename: UByteArray = UByteArray(256),
    public var release: UByteArray = UByteArray(256),
    public var version: UByteArray = UByteArray(256),
    public var machine: UByteArray = UByteArray(256),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Utsname) return false
        if (!this.sysname.contentEquals(other.sysname)) return false
        if (!this.nodename.contentEquals(other.nodename)) return false
        if (!this.release.contentEquals(other.release)) return false
        if (!this.version.contentEquals(other.version)) return false
        if (!this.machine.contentEquals(other.machine)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.sysname.contentHashCode()
        result = 31 * result + this.nodename.contentHashCode()
        result = 31 * result + this.release.contentHashCode()
        result = 31 * result + this.version.contentHashCode()
        result = 31 * result + this.machine.contentHashCode()
        return result
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Utsname> =
            object : TypeLayout<Utsname> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1280L
                override fun readNative(addr: Long): Utsname =
                    Utsname(
                        (PrimitiveArrayLayouts.uByteArray(256)).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(256)).readNative(addr + 256L),
                        (PrimitiveArrayLayouts.uByteArray(256)).readNative(addr + 512L),
                        (PrimitiveArrayLayouts.uByteArray(256)).readNative(addr + 768L),
                        (PrimitiveArrayLayouts.uByteArray(256)).readNative(addr + 1024L),
                    )
                override fun writeNative(addr: Long, value: Utsname) {
                    (PrimitiveArrayLayouts.uByteArray(256)).writeNative(addr, value.sysname)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeNative(addr + 256L, value.nodename)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeNative(addr + 512L, value.release)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeNative(addr + 768L, value.version)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeNative(addr + 1024L, value.machine)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Utsname =
                    Utsname(
                        (PrimitiveArrayLayouts.uByteArray(256)).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(256)).readBytes(buf, offset + 256),
                        (PrimitiveArrayLayouts.uByteArray(256)).readBytes(buf, offset + 512),
                        (PrimitiveArrayLayouts.uByteArray(256)).readBytes(buf, offset + 768),
                        (PrimitiveArrayLayouts.uByteArray(256)).readBytes(buf, offset + 1024),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Utsname) {
                    (PrimitiveArrayLayouts.uByteArray(256)).writeBytes(buf, offset, value.sysname)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeBytes(buf, offset + 256, value.nodename)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeBytes(buf, offset + 512, value.release)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeBytes(buf, offset + 768, value.version)
                    (PrimitiveArrayLayouts.uByteArray(256)).writeBytes(buf, offset + 1024, value.machine)
                }
            }
    }
}
