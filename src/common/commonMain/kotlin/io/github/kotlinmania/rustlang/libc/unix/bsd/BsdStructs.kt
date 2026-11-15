@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.bsd

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.In6Addr
import io.github.kotlinmania.rustlang.libc.unix.InPortT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.FD_SETSIZE
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.TimeT

// Note(exhaustive): review for 1.0
public open class Sockaddr(
    public var saLen: UByte,
    public var saFamily: SaFamilyT,
    public var saData: UByteArray,
) {
    public open fun clone(): Sockaddr = Sockaddr(saLen, saFamily, saData)

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

        public fun default(): Sockaddr = Sockaddr(saLen = Default.default(), saFamily = Default.default(), saData = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class SockaddrIn6(
    public var sin6Len: UByte,
    public var sin6Family: SaFamilyT,
    public var sin6Port: InPortT,
    public var sin6Flowinfo: UInt,
    public var sin6Addr: In6Addr,
    public var sin6ScopeId: UInt,
) {
    public open fun clone(): SockaddrIn6 = SockaddrIn6(sin6Len, sin6Family, sin6Port, sin6Flowinfo, sin6Addr, sin6ScopeId)

    public companion object {
        public fun default(): SockaddrIn6 = SockaddrIn6(sin6Len = Default.default(), sin6Family = Default.default(), sin6Port = Default.default(), sin6Flowinfo = Default.default(), sin6Addr = Default.default(), sin6ScopeId = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Passwd internal constructor(
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

    public open fun clone(): Passwd = Passwd(pwName, pwPasswd, pwUid, pwGid, pwChange, pwClass, pwGecos, pwDir, pwShell, pwExpire, pwFields)

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
            pwName: MutPtr,
            pwPasswd: MutPtr,
            pwUid: UidT,
            pwGid: GidT,
            pwChange: TimeT,
            pwClass: MutPtr,
            pwGecos: MutPtr,
            pwDir: MutPtr,
            pwShell: MutPtr,
            pwExpire: TimeT,
            pwFields: CInt,
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

        public fun default(): Passwd = Passwd(pwName = MutPtr(0L), pwPasswd = MutPtr(0L), pwUid = Default.default(), pwGid = Default.default(), pwChange = Default.default(), pwClass = MutPtr(0L), pwGecos = MutPtr(0L), pwDir = MutPtr(0L), pwShell = MutPtr(0L), pwExpire = Default.default(), pwFields = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Ifaddrs internal constructor(
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

    public open fun clone(): Ifaddrs = Ifaddrs(ifaNext, ifaName, ifaFlags, ifaAddr, ifaNetmask, ifaDstaddr, ifaData)

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
            ifaNext: MutPtr,
            ifaName: MutPtr,
            ifaFlags: CUint,
            ifaAddr: MutPtr,
            ifaNetmask: MutPtr,
            ifaDstaddr: MutPtr,
            ifaData: MutPtr,
        ): Ifaddrs = Ifaddrs(
            ifaNextCtorArg = ifaNext,
            ifaNameCtorArg = ifaName,
            ifaFlags = ifaFlags,
            ifaAddrCtorArg = ifaAddr,
            ifaNetmaskCtorArg = ifaNetmask,
            ifaDstaddrCtorArg = ifaDstaddr,
            ifaDataCtorArg = ifaData,
        )

        public fun default(): Ifaddrs = Ifaddrs(ifaNext = MutPtr(0L), ifaName = MutPtr(0L), ifaFlags = Default.default(), ifaAddr = MutPtr(0L), ifaNetmask = MutPtr(0L), ifaDstaddr = MutPtr(0L), ifaData = MutPtr(0L), ifaAddrflags = Default.default())
    }
}

public open class FdSet(
    public var fdsBits: IntArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): FdSet = FdSet(fdsBits, nonExhaustive)

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

        public fun default(): FdSet = FdSet(fdsBits = Default.default(), nonExhaustive = Unit)
    }
}

// Note(exhaustive): review for 1.0
public open class Msghdr internal constructor(
    msgNameCtorArg: MutPtr,
    public var msgNamelen: SocklenT,
    msgIovCtorArg: MutPtr,
    public var msgIovlen: CInt,
    msgControlCtorArg: MutPtr,
    public var msgControllen: SocklenT,
    public var msgFlags: CInt,
) {
    public var msgName: MutPtr = msgNameCtorArg
        internal set

    public var msgIov: MutPtr = msgIovCtorArg
        internal set

    public var msgControl: MutPtr = msgControlCtorArg
        internal set

    public open fun clone(): Msghdr = Msghdr(msgName, msgNamelen, msgIov, msgIovlen, msgControl, msgControllen, msgFlags)

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
            msgName: MutPtr,
            msgNamelen: SocklenT,
            msgIov: MutPtr,
            msgIovlen: CInt,
            msgControl: MutPtr,
            msgControllen: SocklenT,
            msgFlags: CInt,
        ): Msghdr = Msghdr(
            msgNameCtorArg = msgName,
            msgNamelen = msgNamelen,
            msgIovCtorArg = msgIov,
            msgIovlen = msgIovlen,
            msgControlCtorArg = msgControl,
            msgControllen = msgControllen,
            msgFlags = msgFlags,
        )

        public fun default(): Msghdr = Msghdr(msgName = MutPtr(0L), msgNamelen = Default.default(), msgIov = MutPtr(0L), msgIovlen = Default.default(), msgControl = MutPtr(0L), msgControllen = Default.default(), msgFlags = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class Cmsghdr(
    public var cmsgLen: SocklenT,
    public var cmsgLevel: CInt,
    public var cmsgType: CInt,
) {
    public open fun clone(): Cmsghdr = Cmsghdr(cmsgLen, cmsgLevel, cmsgType)

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

        public fun default(): Cmsghdr = Cmsghdr(cmsgLen = Default.default(), cmsgLevel = Default.default(), cmsgType = Default.default())
    }
}

public open class FsidT(
    public var fsidVal: IntArray,
    public var nonExhaustive: Unit,
) {
    public open fun clone(): FsidT = FsidT(fsidVal, nonExhaustive)

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

        public fun default(): FsidT = FsidT(fsidVal = Default.default(), nonExhaustive = Unit)
    }
}

// Note(exhaustive): review for 1.0
public open class IfNameindex internal constructor(
    public var ifIndex: CUint,
    ifNameCtorArg: MutPtr,
) {
    public var ifName: MutPtr = ifNameCtorArg
        internal set

    public open fun clone(): IfNameindex = IfNameindex(ifIndex, ifName)

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
            ifIndex: CUint,
            ifName: MutPtr,
        ): IfNameindex = IfNameindex(
            ifIndex = ifIndex,
            ifNameCtorArg = ifName,
        )

        public fun default(): IfNameindex = IfNameindex(ifIndex = Default.default(), ifName = MutPtr(0L))
    }
}

public open class RegexT internal constructor(
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

    public open fun clone(): RegexT = RegexT(reMagic, reNsub, reEndp, reG, nonExhaustive)

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
            reMagic: CInt,
            reNsub: SizeT,
            reEndp: ConstPtr,
            reG: MutPtr,
            nonExhaustive: Unit,
        ): RegexT = RegexT(
            reMagic = reMagic,
            reNsub = reNsub,
            reEndpCtorArg = reEndp,
            reGCtorArg = reG,
            nonExhaustive = nonExhaustive,
        )

        public fun default(): RegexT = RegexT(reMagic = Default.default(), reNsub = Default.default(), reEndp = MutPtr(0L), reG = MutPtr(0L), nonExhaustive = Unit)
    }
}

// Note(exhaustive): review for 1.0
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

        public fun default(): RegmatchT = RegmatchT(rmSo = Default.default(), rmEo = Default.default())
    }
}

// Note(exhaustive): review for 1.0
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

        public fun default(): Option = Option(name = MutPtr(0L), hasArg = Default.default(), flag = MutPtr(0L), value = Default.default())
    }
}

// Note(exhaustive): review for 1.0
public open class SockaddrUn(
    public var sunLen: UByte,
    public var sunFamily: SaFamilyT,
    public var sunPath: UByteArray,
) {
    public open fun clone(): SockaddrUn = SockaddrUn(sunLen, sunFamily, sunPath)

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

        public fun default(): SockaddrUn = SockaddrUn(sunLen = Default.default(), sunFamily = Default.default(), sunPath = UByteArray(104) { 0u.toUByte() })
    }
}

// Note(exhaustive): review for 1.0
public open class Utsname(
    public var sysname: UByteArray,
    public var nodename: UByteArray,
    public var release: UByteArray,
    public var version: UByteArray,
    public var machine: UByteArray,
) {
    public open fun clone(): Utsname = Utsname(sysname, nodename, release, version, machine)

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

        public fun default(): Utsname = Utsname(sysname = UByteArray(256) { 0u.toUByte() }, nodename = UByteArray(256) { 0u.toUByte() }, release = UByteArray(256) { 0u.toUByte() }, version = UByteArray(256) { 0u.toUByte() }, machine = UByteArray(256) { 0u.toUByte() })
    }
}
