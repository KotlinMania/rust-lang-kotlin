@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike

import io.github.kotlinmania.rustlang.core.ffi.primitives.CChar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CLong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.S64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U16
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U8
import io.github.kotlinmania.rustlang.libc.unix.In6Addr
import io.github.kotlinmania.rustlang.libc.unix.InAddrT
import io.github.kotlinmania.rustlang.libc.unix.InPortT
import io.github.kotlinmania.rustlang.libc.unix.Sigval
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.SocklenT

// intentionally not public, only used for fdSet
// https://github.com/search?q=repo%3Atorvalds%2Flinux+%22%23define+_IOC_NONE%22&type=code
// LFS64 extensions
//
// * musl and Emscripten has 64-bit versions only so aliases the LFS64 symbols to the standard ones
// * ulibc doesn't have preadv64/pwritev64
// The statx syscall, available on some libcs.

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
public open class Timezone(
    public var data: Unit,
    public var marker: Unit,
) {
    public open fun clone(): Timezone = Timezone(data, marker)
}

public open class InAddr(
    public var sAddr: InAddrT,
) {
    public open fun clone(): InAddr = InAddr(sAddr)

    public companion object {
        public fun typeLayout(): TypeLayout<InAddr> =
            object : TypeLayout<InAddr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): InAddr =
                    InAddr(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: InAddr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.sAddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InAddr =
                    InAddr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InAddr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.sAddr)
                }
            }
    }
}

public open class IpMreq(
    public var imrMultiaddr: InAddr,
    public var imrInterface: InAddr,
) {
    public open fun clone(): IpMreq = IpMreq(imrMultiaddr, imrInterface)

    public companion object {
        public fun typeLayout(): TypeLayout<IpMreq> =
            object : TypeLayout<IpMreq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): IpMreq =
                    IpMreq(
                        (InAddr.typeLayout()).readNative(addr),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: IpMreq) {
                    (InAddr.typeLayout()).writeNative(addr, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.imrInterface)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpMreq =
                    IpMreq(
                        (InAddr.typeLayout()).readBytes(buf, offset),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpMreq) {
                    (InAddr.typeLayout()).writeBytes(buf, offset, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.imrInterface)
                }
            }
    }
}

public open class IpMreqn(
    public var imrMultiaddr: InAddr,
    public var imrAddress: InAddr,
    public var imrIfindex: CInt,
) {
    public open fun clone(): IpMreqn = IpMreqn(imrMultiaddr, imrAddress, imrIfindex)

    public companion object {
        public fun typeLayout(): TypeLayout<IpMreqn> =
            object : TypeLayout<IpMreqn> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): IpMreqn =
                    IpMreqn(
                        (InAddr.typeLayout()).readNative(addr),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: IpMreqn) {
                    (InAddr.typeLayout()).writeNative(addr, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.imrAddress)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.imrIfindex)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpMreqn =
                    IpMreqn(
                        (InAddr.typeLayout()).readBytes(buf, offset),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpMreqn) {
                    (InAddr.typeLayout()).writeBytes(buf, offset, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.imrAddress)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.imrIfindex)
                }
            }
    }
}

// netinet/in.h: RFC 3678 multicast group membership requests
public open class IpMreqSource(
    public var imrMultiaddr: InAddr,
    public var imrInterface: InAddr,
    public var imrSourceaddr: InAddr,
) {
    public open fun clone(): IpMreqSource = IpMreqSource(imrMultiaddr, imrInterface, imrSourceaddr)

    public companion object {
        public fun typeLayout(): TypeLayout<IpMreqSource> =
            object : TypeLayout<IpMreqSource> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): IpMreqSource =
                    IpMreqSource(
                        (InAddr.typeLayout()).readNative(addr),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                        (InAddr.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: IpMreqSource) {
                    (InAddr.typeLayout()).writeNative(addr, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.imrInterface)
                    (InAddr.typeLayout()).writeNative(addr + 8L, value.imrSourceaddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): IpMreqSource =
                    IpMreqSource(
                        (InAddr.typeLayout()).readBytes(buf, offset),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                        (InAddr.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: IpMreqSource) {
                    (InAddr.typeLayout()).writeBytes(buf, offset, value.imrMultiaddr)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.imrInterface)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 8, value.imrSourceaddr)
                }
            }
    }
}

public open class GroupReq(
    public var grInterface: UInt,
    public var grGroup: SockaddrStorage,
) {
    public open fun clone(): GroupReq = GroupReq(grInterface, grGroup)

    public companion object {
        public fun typeLayout(): TypeLayout<GroupReq> =
            object : TypeLayout<GroupReq> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes((8L + SockaddrStorage.typeLayout().sizeBytes), 8L)
                override fun readNative(addr: Long): GroupReq =
                    GroupReq(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (SockaddrStorage.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: GroupReq) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.grInterface)
                    (SockaddrStorage.typeLayout()).writeNative(addr + 8L, value.grGroup)
                }
                override fun readBytes(buf: ByteArray, offset: Int): GroupReq =
                    GroupReq(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (SockaddrStorage.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: GroupReq) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.grInterface)
                    (SockaddrStorage.typeLayout()).writeBytes(buf, offset + 8, value.grGroup)
                }
            }
    }
}

public open class GroupSourceReq(
    public var gsrInterface: UInt,
    public var gsrGroup: SockaddrStorage,
    public var gsrSource: SockaddrStorage,
) {
    public open fun clone(): GroupSourceReq = GroupSourceReq(gsrInterface, gsrGroup, gsrSource)

    public companion object {
        public fun typeLayout(): TypeLayout<GroupSourceReq> =
            object : TypeLayout<GroupSourceReq> {
                override val alignBytes: Long = 8L
                private val off2: Long = alignUpBytes((8L + SockaddrStorage.typeLayout().sizeBytes), 8L)
                override val sizeBytes: Long = alignUpBytes((off2 + SockaddrStorage.typeLayout().sizeBytes), 8L)
                override fun readNative(addr: Long): GroupSourceReq =
                    GroupSourceReq(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (SockaddrStorage.typeLayout()).readNative(addr + 8L),
                        (SockaddrStorage.typeLayout()).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: GroupSourceReq) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.gsrInterface)
                    (SockaddrStorage.typeLayout()).writeNative(addr + 8L, value.gsrGroup)
                    (SockaddrStorage.typeLayout()).writeNative(addr + off2, value.gsrSource)
                }
                override fun readBytes(buf: ByteArray, offset: Int): GroupSourceReq =
                    GroupSourceReq(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (SockaddrStorage.typeLayout()).readBytes(buf, offset + 8),
                        (SockaddrStorage.typeLayout()).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: GroupSourceReq) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.gsrInterface)
                    (SockaddrStorage.typeLayout()).writeBytes(buf, offset + 8, value.gsrGroup)
                    (SockaddrStorage.typeLayout()).writeBytes(buf, offset + off2.toInt(), value.gsrSource)
                }
            }
    }
}

public open class Sockaddr(
    public var saFamily: SaFamilyT,
    public var saData: UByteArray,
) {
    public open fun clone(): Sockaddr = Sockaddr(saFamily, saData)

    public companion object {
        public fun typeLayout(): TypeLayout<Sockaddr> =
            object : TypeLayout<Sockaddr> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Sockaddr =
                    Sockaddr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(14)).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: Sockaddr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.saFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeNative(addr + 2L, value.saData)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Sockaddr =
                    Sockaddr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(14)).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Sockaddr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.saFamily)
                    (PrimitiveArrayLayouts.uByteArray(14)).writeBytes(buf, offset + 2, value.saData)
                }
            }
    }
}

public open class SockaddrIn(
    public var sinFamily: SaFamilyT,
    public var sinPort: InPortT,
    public var sinAddr: InAddr,
    public var sinZero: UByteArray,
) {
    public open fun clone(): SockaddrIn = SockaddrIn(sinFamily, sinPort, sinAddr, sinZero)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrIn> =
            object : TypeLayout<SockaddrIn> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockaddrIn =
                    SockaddrIn(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: SockaddrIn) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sinFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sinPort)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.sinAddr)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 8L, value.sinZero)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrIn =
                    SockaddrIn(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrIn) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sinFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sinPort)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.sinAddr)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 8, value.sinZero)
                }
            }
    }
}

public open class SockaddrIn6(
    public var sin6Family: SaFamilyT,
    public var sin6Port: InPortT,
    public var sin6Flowinfo: UInt,
    public var sin6Addr: In6Addr,
    public var sin6ScopeId: UInt,
) {
    public open fun clone(): SockaddrIn6 = SockaddrIn6(sin6Family, sin6Port, sin6Flowinfo, sin6Addr, sin6ScopeId)
}

// The order of the `aiAddr` field in this struct is crucial
// for converting between the Rust and C types.
public open class Addrinfo internal constructor(
    public var aiFlags: CInt,
    public var aiFamily: CInt,
    public var aiSocktype: CInt,
    public var aiProtocol: CInt,
    public var aiAddrlen: SocklenT,
    aiAddrCtorArg: MutPtr,
    aiCanonnameCtorArg: MutPtr,
    aiNextCtorArg: MutPtr,
) {
    public var aiAddr: MutPtr = aiAddrCtorArg
        internal set

    public var aiCanonname: MutPtr = aiCanonnameCtorArg
        internal set

    public var aiNext: MutPtr = aiNextCtorArg
        internal set

    public open fun clone(): Addrinfo = Addrinfo(aiFlags, aiFamily, aiSocktype, aiProtocol, aiAddrlen, aiAddr, aiCanonname, aiNext)

    public companion object {
        public fun typeLayout(): TypeLayout<Addrinfo> =
            object : TypeLayout<Addrinfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 48L
                override fun readNative(addr: Long): Addrinfo =
                    Addrinfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                    )
                override fun writeNative(addr: Long, value: Addrinfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.aiFlags)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.aiFamily)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.aiSocktype)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.aiProtocol)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.aiAddrlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.aiAddr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.aiCanonname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.aiNext)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Addrinfo =
                    Addrinfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Addrinfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.aiFlags)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.aiFamily)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.aiSocktype)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.aiProtocol)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.aiAddrlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.aiAddr)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.aiCanonname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.aiNext)
                }
            }

        public operator fun invoke(
            aiFlags: CInt,
            aiFamily: CInt,
            aiSocktype: CInt,
            aiProtocol: CInt,
            aiAddrlen: SocklenT,
            aiAddr: MutPtr,
            aiCanonname: MutPtr,
            aiNext: MutPtr,
        ): Addrinfo = Addrinfo(
            aiFlags = aiFlags,
            aiFamily = aiFamily,
            aiSocktype = aiSocktype,
            aiProtocol = aiProtocol,
            aiAddrlen = aiAddrlen,
            aiAddrCtorArg = aiAddr,
            aiCanonnameCtorArg = aiCanonname,
            aiNextCtorArg = aiNext,
        )
    }
}

public open class SockaddrLl(
    public var sllFamily: CUshort,
    public var sllProtocol: CUshort,
    public var sllIfindex: CInt,
    public var sllHatype: CUshort,
    public var sllPkttype: CUchar,
    public var sllHalen: CUchar,
    public var sllAddr: UByteArray,
) {
    public open fun clone(): SockaddrLl = SockaddrLl(sllFamily, sllProtocol, sllIfindex, sllHatype, sllPkttype, sllHalen, sllAddr)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrLl> =
            object : TypeLayout<SockaddrLl> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 20L
                override fun readNative(addr: Long): SockaddrLl =
                    SockaddrLl(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.U16).readNative(addr + 8L),
                        (PrimitiveLayouts.U8).readNative(addr + 10L),
                        (PrimitiveLayouts.U8).readNative(addr + 11L),
                        (PrimitiveArrayLayouts.uByteArray(8)).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: SockaddrLl) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sllFamily)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.sllProtocol)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.sllIfindex)
                    (PrimitiveLayouts.U16).writeNative(addr + 8L, value.sllHatype)
                    (PrimitiveLayouts.U8).writeNative(addr + 10L, value.sllPkttype)
                    (PrimitiveLayouts.U8).writeNative(addr + 11L, value.sllHalen)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeNative(addr + 12L, value.sllAddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrLl =
                    SockaddrLl(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 10),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 11),
                        (PrimitiveArrayLayouts.uByteArray(8)).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrLl) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sllFamily)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.sllProtocol)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.sllIfindex)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 8, value.sllHatype)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 10, value.sllPkttype)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 11, value.sllHalen)
                    (PrimitiveArrayLayouts.uByteArray(8)).writeBytes(buf, offset + 12, value.sllAddr)
                }
            }
    }
}

public open class FdSet(
    public var fdsBits: ULongArray,
) {
    public open fun clone(): FdSet = FdSet(fdsBits)

    public companion object {
        public fun typeLayout(): TypeLayout<FdSet> =
            object : TypeLayout<FdSet> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = alignUpBytes(((FD_SETSIZE / ULONG_SIZE).toLong() * (PrimitiveLayouts.U64).strideBytes), 8L)
                override fun readNative(addr: Long): FdSet =
                    FdSet(
                        (PrimitiveArrayLayouts.uLongArray((FD_SETSIZE / ULONG_SIZE).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FdSet) {
                    (PrimitiveArrayLayouts.uLongArray((FD_SETSIZE / ULONG_SIZE).toInt())).writeNative(addr, value.fdsBits)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FdSet =
                    FdSet(
                        (PrimitiveArrayLayouts.uLongArray((FD_SETSIZE / ULONG_SIZE).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FdSet) {
                    (PrimitiveArrayLayouts.uLongArray((FD_SETSIZE / ULONG_SIZE).toInt())).writeBytes(buf, offset, value.fdsBits)
                }
            }
    }
}

public open class Tm internal constructor(
    public var tmSec: CInt,
    public var tmMin: CInt,
    public var tmHour: CInt,
    public var tmMday: CInt,
    public var tmMon: CInt,
    public var tmYear: CInt,
    public var tmWday: CInt,
    public var tmYday: CInt,
    public var tmIsdst: CInt,
    public var tmGmtoff: CLong,
    tmZoneCtorArg: ConstPtr,
) {
    public var tmZone: ConstPtr = tmZoneCtorArg
        internal set

    public open fun clone(): Tm = Tm(tmSec, tmMin, tmHour, tmMday, tmMon, tmYear, tmWday, tmYday, tmIsdst, tmGmtoff, tmZone)

    public companion object {
        public fun typeLayout(): TypeLayout<Tm> =
            object : TypeLayout<Tm> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): Tm =
                    Tm(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 20L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (PrimitiveLayouts.I64).readNative(addr + 40L),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Tm) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.tmSec)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.tmMin)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.tmHour)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.tmMday)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.tmMon)
                    (PrimitiveLayouts.I32).writeNative(addr + 20L, value.tmYear)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.tmWday)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.tmYday)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.tmIsdst)
                    (PrimitiveLayouts.I64).writeNative(addr + 40L, value.tmGmtoff)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 48L, value.tmZone)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tm =
                    Tm(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.I64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tm) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.tmSec)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.tmMin)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.tmHour)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.tmMday)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.tmMon)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 20, value.tmYear)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.tmWday)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.tmYday)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.tmIsdst)
                    (PrimitiveLayouts.I64).writeBytes(buf, offset + 40, value.tmGmtoff)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 48, value.tmZone)
                }
            }

        public operator fun invoke(
            tmSec: CInt,
            tmMin: CInt,
            tmHour: CInt,
            tmMday: CInt,
            tmMon: CInt,
            tmYear: CInt,
            tmWday: CInt,
            tmYday: CInt,
            tmIsdst: CInt,
            tmGmtoff: CLong,
            tmZone: ConstPtr,
        ): Tm = Tm(
            tmSec = tmSec,
            tmMin = tmMin,
            tmHour = tmHour,
            tmMday = tmMday,
            tmMon = tmMon,
            tmYear = tmYear,
            tmWday = tmWday,
            tmYday = tmYday,
            tmIsdst = tmIsdst,
            tmGmtoff = tmGmtoff,
            tmZoneCtorArg = tmZone,
        )
    }
}

public open class SchedParam(
    public var schedPriority: CInt,
) {
    public open fun clone(): SchedParam = SchedParam(schedPriority)

    public companion object {
        public fun typeLayout(): TypeLayout<SchedParam> =
            object : TypeLayout<SchedParam> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): SchedParam =
                    SchedParam(
                        (PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SchedParam) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.schedPriority)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SchedParam =
                    SchedParam(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SchedParam) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.schedPriority)
                }
            }
    }
}

public open class DlInfo internal constructor(
    dliFnameCtorArg: ConstPtr,
    dliFbaseCtorArg: MutPtr,
    dliSnameCtorArg: ConstPtr,
    dliSaddrCtorArg: MutPtr,
) {
    public var dliFname: ConstPtr = dliFnameCtorArg
        internal set

    public var dliFbase: MutPtr = dliFbaseCtorArg
        internal set

    public var dliSname: ConstPtr = dliSnameCtorArg
        internal set

    public var dliSaddr: MutPtr = dliSaddrCtorArg
        internal set

    public open fun clone(): DlInfo = DlInfo(dliFname, dliFbase, dliSname, dliSaddr)

    public companion object {
        public fun typeLayout(): TypeLayout<DlInfo> =
            object : TypeLayout<DlInfo> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): DlInfo =
                    DlInfo(
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.constPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: DlInfo) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr, value.dliFname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.dliFbase)
                    (PrimitiveLayouts.constPtrLayout(64)).writeNative(addr + 16L, value.dliSname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.dliSaddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DlInfo =
                    DlInfo(
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.constPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DlInfo) {
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset, value.dliFname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.dliFbase)
                    (PrimitiveLayouts.constPtrLayout(64)).writeBytes(buf, offset + 16, value.dliSname)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.dliSaddr)
                }
            }

        public operator fun invoke(
            dliFname: ConstPtr,
            dliFbase: MutPtr,
            dliSname: ConstPtr,
            dliSaddr: MutPtr,
        ): DlInfo = DlInfo(
            dliFnameCtorArg = dliFname,
            dliFbaseCtorArg = dliFbase,
            dliSnameCtorArg = dliSname,
            dliSaddrCtorArg = dliSaddr,
        )
    }
}

public open class Lconv internal constructor(
    decimalPointCtorArg: MutPtr,
    thousandsSepCtorArg: MutPtr,
    groupingCtorArg: MutPtr,
    intCurrSymbolCtorArg: MutPtr,
    currencySymbolCtorArg: MutPtr,
    monDecimalPointCtorArg: MutPtr,
    monThousandsSepCtorArg: MutPtr,
    monGroupingCtorArg: MutPtr,
    positiveSignCtorArg: MutPtr,
    negativeSignCtorArg: MutPtr,
    public var intFracDigits: CChar,
    public var fracDigits: CChar,
    public var pCsPrecedes: CChar,
    public var pSepBySpace: CChar,
    public var nCsPrecedes: CChar,
    public var nSepBySpace: CChar,
    public var pSignPosn: CChar,
    public var nSignPosn: CChar,
    public var intPCsPrecedes: CChar,
    public var intPSepBySpace: CChar,
    public var intNCsPrecedes: CChar,
    public var intNSepBySpace: CChar,
    public var intPSignPosn: CChar,
    public var intNSignPosn: CChar,
) {
    public var decimalPoint: MutPtr = decimalPointCtorArg
        internal set

    public var thousandsSep: MutPtr = thousandsSepCtorArg
        internal set

    public var grouping: MutPtr = groupingCtorArg
        internal set

    public var intCurrSymbol: MutPtr = intCurrSymbolCtorArg
        internal set

    public var currencySymbol: MutPtr = currencySymbolCtorArg
        internal set

    public var monDecimalPoint: MutPtr = monDecimalPointCtorArg
        internal set

    public var monThousandsSep: MutPtr = monThousandsSepCtorArg
        internal set

    public var monGrouping: MutPtr = monGroupingCtorArg
        internal set

    public var positiveSign: MutPtr = positiveSignCtorArg
        internal set

    public var negativeSign: MutPtr = negativeSignCtorArg
        internal set

    public open fun clone(): Lconv = Lconv(decimalPoint, thousandsSep, grouping, intCurrSymbol, currencySymbol, monDecimalPoint, monThousandsSep, monGrouping, positiveSign, negativeSign, intFracDigits, fracDigits, pCsPrecedes, pSepBySpace, nCsPrecedes, nSepBySpace, pSignPosn, nSignPosn, intPCsPrecedes, intPSepBySpace, intNCsPrecedes, intNSepBySpace, intPSignPosn, intNSignPosn)

    public companion object {
        public fun typeLayout(): TypeLayout<Lconv> =
            object : TypeLayout<Lconv> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 96L
                override fun readNative(addr: Long): Lconv =
                    Lconv(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 40L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 48L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 56L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 64L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 72L),
                        (PrimitiveLayouts.U8).readNative(addr + 80L),
                        (PrimitiveLayouts.U8).readNative(addr + 81L),
                        (PrimitiveLayouts.U8).readNative(addr + 82L),
                        (PrimitiveLayouts.U8).readNative(addr + 83L),
                        (PrimitiveLayouts.U8).readNative(addr + 84L),
                        (PrimitiveLayouts.U8).readNative(addr + 85L),
                        (PrimitiveLayouts.U8).readNative(addr + 86L),
                        (PrimitiveLayouts.U8).readNative(addr + 87L),
                        (PrimitiveLayouts.U8).readNative(addr + 88L),
                        (PrimitiveLayouts.U8).readNative(addr + 89L),
                        (PrimitiveLayouts.U8).readNative(addr + 90L),
                        (PrimitiveLayouts.U8).readNative(addr + 91L),
                        (PrimitiveLayouts.U8).readNative(addr + 92L),
                        (PrimitiveLayouts.U8).readNative(addr + 93L),
                    )
                override fun writeNative(addr: Long, value: Lconv) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.decimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.thousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.grouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 24L, value.intCurrSymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.currencySymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.monDecimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 48L, value.monThousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 56L, value.monGrouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 64L, value.positiveSign)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 72L, value.negativeSign)
                    (PrimitiveLayouts.U8).writeNative(addr + 80L, value.intFracDigits)
                    (PrimitiveLayouts.U8).writeNative(addr + 81L, value.fracDigits)
                    (PrimitiveLayouts.U8).writeNative(addr + 82L, value.pCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 83L, value.pSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 84L, value.nCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 85L, value.nSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 86L, value.pSignPosn)
                    (PrimitiveLayouts.U8).writeNative(addr + 87L, value.nSignPosn)
                    (PrimitiveLayouts.U8).writeNative(addr + 88L, value.intPCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 89L, value.intPSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 90L, value.intNCsPrecedes)
                    (PrimitiveLayouts.U8).writeNative(addr + 91L, value.intNSepBySpace)
                    (PrimitiveLayouts.U8).writeNative(addr + 92L, value.intPSignPosn)
                    (PrimitiveLayouts.U8).writeNative(addr + 93L, value.intNSignPosn)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Lconv =
                    Lconv(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 56),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 64),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 72),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 80),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 81),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 82),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 83),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 84),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 85),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 86),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 87),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 88),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 89),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 90),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 91),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 92),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 93),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Lconv) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.decimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.thousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.grouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 24, value.intCurrSymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.currencySymbol)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.monDecimalPoint)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.monThousandsSep)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 56, value.monGrouping)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 64, value.positiveSign)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 72, value.negativeSign)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 80, value.intFracDigits)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 81, value.fracDigits)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 82, value.pCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 83, value.pSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 84, value.nCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 85, value.nSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 86, value.pSignPosn)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 87, value.nSignPosn)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 88, value.intPCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 89, value.intPSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 90, value.intNCsPrecedes)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 91, value.intNSepBySpace)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 92, value.intPSignPosn)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 93, value.intNSignPosn)
                }
            }

        public operator fun invoke(
            decimalPoint: MutPtr,
            thousandsSep: MutPtr,
            grouping: MutPtr,
            intCurrSymbol: MutPtr,
            currencySymbol: MutPtr,
            monDecimalPoint: MutPtr,
            monThousandsSep: MutPtr,
            monGrouping: MutPtr,
            positiveSign: MutPtr,
            negativeSign: MutPtr,
            intFracDigits: CChar,
            fracDigits: CChar,
            pCsPrecedes: CChar,
            pSepBySpace: CChar,
            nCsPrecedes: CChar,
            nSepBySpace: CChar,
            pSignPosn: CChar,
            nSignPosn: CChar,
            intPCsPrecedes: CChar,
            intPSepBySpace: CChar,
            intNCsPrecedes: CChar,
            intNSepBySpace: CChar,
            intPSignPosn: CChar,
            intNSignPosn: CChar,
        ): Lconv = Lconv(
            decimalPointCtorArg = decimalPoint,
            thousandsSepCtorArg = thousandsSep,
            groupingCtorArg = grouping,
            intCurrSymbolCtorArg = intCurrSymbol,
            currencySymbolCtorArg = currencySymbol,
            monDecimalPointCtorArg = monDecimalPoint,
            monThousandsSepCtorArg = monThousandsSep,
            monGroupingCtorArg = monGrouping,
            positiveSignCtorArg = positiveSign,
            negativeSignCtorArg = negativeSign,
            intFracDigits = intFracDigits,
            fracDigits = fracDigits,
            pCsPrecedes = pCsPrecedes,
            pSepBySpace = pSepBySpace,
            nCsPrecedes = nCsPrecedes,
            nSepBySpace = nSepBySpace,
            pSignPosn = pSignPosn,
            nSignPosn = nSignPosn,
            intPCsPrecedes = intPCsPrecedes,
            intPSepBySpace = intPSepBySpace,
            intNCsPrecedes = intNCsPrecedes,
            intNSepBySpace = intNSepBySpace,
            intPSignPosn = intPSignPosn,
            intNSignPosn = intNSignPosn,
        )
    }
}

public open class InPktinfo(
    public var ipiIfindex: CInt,
    public var ipiSpecDst: InAddr,
    public var ipiAddr: InAddr,
) {
    public open fun clone(): InPktinfo = InPktinfo(ipiIfindex, ipiSpecDst, ipiAddr)

    public companion object {
        public fun typeLayout(): TypeLayout<InPktinfo> =
            object : TypeLayout<InPktinfo> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): InPktinfo =
                    InPktinfo(
                        (PrimitiveLayouts.I32).readNative(addr),
                        (InAddr.typeLayout()).readNative(addr + 4L),
                        (InAddr.typeLayout()).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: InPktinfo) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.ipiIfindex)
                    (InAddr.typeLayout()).writeNative(addr + 4L, value.ipiSpecDst)
                    (InAddr.typeLayout()).writeNative(addr + 8L, value.ipiAddr)
                }
                override fun readBytes(buf: ByteArray, offset: Int): InPktinfo =
                    InPktinfo(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                        (InAddr.typeLayout()).readBytes(buf, offset + 4),
                        (InAddr.typeLayout()).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: InPktinfo) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.ipiIfindex)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 4, value.ipiSpecDst)
                    (InAddr.typeLayout()).writeBytes(buf, offset + 8, value.ipiAddr)
                }
            }
    }
}

public open class Ifaddrs internal constructor(
    ifaNextCtorArg: MutPtr,
    ifaNameCtorArg: MutPtr,
    public var ifaFlags: CUint,
    ifaAddrCtorArg: MutPtr,
    ifaNetmaskCtorArg: MutPtr,
    ifaIfuCtorArg: MutPtr,
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

    public var ifaIfu: MutPtr = ifaIfuCtorArg
        internal set

    // Note(union) This should be a union
    public var ifaData: MutPtr = ifaDataCtorArg
        internal set

    public open fun clone(): Ifaddrs = Ifaddrs(ifaNext, ifaName, ifaFlags, ifaAddr, ifaNetmask, ifaIfu, ifaData)

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
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 40L, value.ifaIfu)
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
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 40, value.ifaIfu)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 48, value.ifaData)
                }
            }

        public operator fun invoke(
            ifaNext: MutPtr,
            ifaName: MutPtr,
            ifaFlags: CUint,
            ifaAddr: MutPtr,
            ifaNetmask: MutPtr,
            ifaIfu: MutPtr,
            ifaData: MutPtr,
        ): Ifaddrs = Ifaddrs(
            ifaNextCtorArg = ifaNext,
            ifaNameCtorArg = ifaName,
            ifaFlags = ifaFlags,
            ifaAddrCtorArg = ifaAddr,
            ifaNetmaskCtorArg = ifaNetmask,
            ifaIfuCtorArg = ifaIfu,
            ifaDataCtorArg = ifaData,
        )
    }
}

public open class In6Rtmsg(
    public var rtmsgDst: In6Addr,
    public var rtmsgSrc: In6Addr,
    public var rtmsgGateway: In6Addr,
    public var rtmsgType: UInt,
    public var rtmsgDstLen: UShort,
    public var rtmsgSrcLen: UShort,
    public var rtmsgMetric: UInt,
    public var rtmsgInfo: CUlong,
    public var rtmsgFlags: UInt,
    public var rtmsgIfindex: CInt,
) {
    public open fun clone(): In6Rtmsg = In6Rtmsg(rtmsgDst, rtmsgSrc, rtmsgGateway, rtmsgType, rtmsgDstLen, rtmsgSrcLen, rtmsgMetric, rtmsgInfo, rtmsgFlags, rtmsgIfindex)
}

public open class Arpreq(
    public var arpPa: Sockaddr,
    public var arpHa: Sockaddr,
    public var arpFlags: CInt,
    public var arpNetmask: Sockaddr,
    public var arpDev: UByteArray,
) {
    public open fun clone(): Arpreq = Arpreq(arpPa, arpHa, arpFlags, arpNetmask, arpDev)

    public companion object {
        public fun typeLayout(): TypeLayout<Arpreq> =
            object : TypeLayout<Arpreq> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 68L
                override fun readNative(addr: Long): Arpreq =
                    Arpreq(
                        (Sockaddr.typeLayout()).readNative(addr),
                        (Sockaddr.typeLayout()).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (Sockaddr.typeLayout()).readNative(addr + 36L),
                        (PrimitiveArrayLayouts.uByteArray(16)).readNative(addr + 52L),
                    )
                override fun writeNative(addr: Long, value: Arpreq) {
                    (Sockaddr.typeLayout()).writeNative(addr, value.arpPa)
                    (Sockaddr.typeLayout()).writeNative(addr + 16L, value.arpHa)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.arpFlags)
                    (Sockaddr.typeLayout()).writeNative(addr + 36L, value.arpNetmask)
                    (PrimitiveArrayLayouts.uByteArray(16)).writeNative(addr + 52L, value.arpDev)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Arpreq =
                    Arpreq(
                        (Sockaddr.typeLayout()).readBytes(buf, offset),
                        (Sockaddr.typeLayout()).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (Sockaddr.typeLayout()).readBytes(buf, offset + 36),
                        (PrimitiveArrayLayouts.uByteArray(16)).readBytes(buf, offset + 52),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Arpreq) {
                    (Sockaddr.typeLayout()).writeBytes(buf, offset, value.arpPa)
                    (Sockaddr.typeLayout()).writeBytes(buf, offset + 16, value.arpHa)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.arpFlags)
                    (Sockaddr.typeLayout()).writeBytes(buf, offset + 36, value.arpNetmask)
                    (PrimitiveArrayLayouts.uByteArray(16)).writeBytes(buf, offset + 52, value.arpDev)
                }
            }
    }
}

public open class ArpreqOld(
    public var arpPa: Sockaddr,
    public var arpHa: Sockaddr,
    public var arpFlags: CInt,
    public var arpNetmask: Sockaddr,
) {
    public open fun clone(): ArpreqOld = ArpreqOld(arpPa, arpHa, arpFlags, arpNetmask)

    public companion object {
        public fun typeLayout(): TypeLayout<ArpreqOld> =
            object : TypeLayout<ArpreqOld> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 52L
                override fun readNative(addr: Long): ArpreqOld =
                    ArpreqOld(
                        (Sockaddr.typeLayout()).readNative(addr),
                        (Sockaddr.typeLayout()).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 32L),
                        (Sockaddr.typeLayout()).readNative(addr + 36L),
                    )
                override fun writeNative(addr: Long, value: ArpreqOld) {
                    (Sockaddr.typeLayout()).writeNative(addr, value.arpPa)
                    (Sockaddr.typeLayout()).writeNative(addr + 16L, value.arpHa)
                    (PrimitiveLayouts.I32).writeNative(addr + 32L, value.arpFlags)
                    (Sockaddr.typeLayout()).writeNative(addr + 36L, value.arpNetmask)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ArpreqOld =
                    ArpreqOld(
                        (Sockaddr.typeLayout()).readBytes(buf, offset),
                        (Sockaddr.typeLayout()).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 32),
                        (Sockaddr.typeLayout()).readBytes(buf, offset + 36),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ArpreqOld) {
                    (Sockaddr.typeLayout()).writeBytes(buf, offset, value.arpPa)
                    (Sockaddr.typeLayout()).writeBytes(buf, offset + 16, value.arpHa)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 32, value.arpFlags)
                    (Sockaddr.typeLayout()).writeBytes(buf, offset + 36, value.arpNetmask)
                }
            }
    }
}

public open class Arphdr(
    public var arHrd: UShort,
    public var arPro: UShort,
    public var arHln: UByte,
    public var arPln: UByte,
    public var arOp: UShort,
) {
    public open fun clone(): Arphdr = Arphdr(arHrd, arPro, arHln, arPln, arOp)

    public companion object {
        public fun typeLayout(): TypeLayout<Arphdr> =
            object : TypeLayout<Arphdr> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): Arphdr =
                    Arphdr(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U16).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 4L),
                        (PrimitiveLayouts.U8).readNative(addr + 5L),
                        (PrimitiveLayouts.U16).readNative(addr + 6L),
                    )
                override fun writeNative(addr: Long, value: Arphdr) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.arHrd)
                    (PrimitiveLayouts.U16).writeNative(addr + 2L, value.arPro)
                    (PrimitiveLayouts.U8).writeNative(addr + 4L, value.arHln)
                    (PrimitiveLayouts.U8).writeNative(addr + 5L, value.arPln)
                    (PrimitiveLayouts.U16).writeNative(addr + 6L, value.arOp)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Arphdr =
                    Arphdr(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 6),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Arphdr) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.arHrd)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 2, value.arPro)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 4, value.arHln)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 5, value.arPln)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 6, value.arOp)
                }
            }
    }
}

public open class SockaddrUn(
    public var sunFamily: SaFamilyT,
    public var sunPath: UByteArray,
) {
    public open fun clone(): SockaddrUn = SockaddrUn(sunFamily, sunPath)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrUn> =
            object : TypeLayout<SockaddrUn> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 110L
                override fun readNative(addr: Long): SockaddrUn =
                    SockaddrUn(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(108)).readNative(addr + 2L),
                    )
                override fun writeNative(addr: Long, value: SockaddrUn) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.sunFamily)
                    (PrimitiveArrayLayouts.uByteArray(108)).writeNative(addr + 2L, value.sunPath)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrUn =
                    SockaddrUn(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(108)).readBytes(buf, offset + 2),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrUn) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.sunFamily)
                    (PrimitiveArrayLayouts.uByteArray(108)).writeBytes(buf, offset + 2, value.sunPath)
                }
            }
    }
}

public open class SockaddrStorage(
    public var ssFamily: SaFamilyT,
    public var ssPad2: UByteArray,
    public var ssAlign: SizeT,
) {
    public open fun clone(): SockaddrStorage = SockaddrStorage(ssFamily, ssPad2, ssAlign)

    public companion object {
        public fun typeLayout(): TypeLayout<SockaddrStorage> =
            object : TypeLayout<SockaddrStorage> {
                override val alignBytes: Long = 8L
                private val off2: Long = alignUpBytes((2L + ((128 - 2 - 8).toLong() * (PrimitiveLayouts.U8).strideBytes)), 8L)
                override val sizeBytes: Long = alignUpBytes((off2 + 8L), 8L)
                override fun readNative(addr: Long): SockaddrStorage =
                    SockaddrStorage(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray((128 - 2 - 8).toInt())).readNative(addr + 2L),
                        (PrimitiveLayouts.U64).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: SockaddrStorage) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.ssFamily)
                    (PrimitiveArrayLayouts.uByteArray((128 - 2 - 8).toInt())).writeNative(addr + 2L, value.ssPad2)
                    (PrimitiveLayouts.U64).writeNative(addr + off2, value.ssAlign)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockaddrStorage =
                    SockaddrStorage(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray((128 - 2 - 8).toInt())).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockaddrStorage) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.ssFamily)
                    (PrimitiveArrayLayouts.uByteArray((128 - 2 - 8).toInt())).writeBytes(buf, offset + 2, value.ssPad2)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + off2.toInt(), value.ssAlign)
                }
            }
    }
}

public open class Utsname(
    public var sysname: UByteArray,
    public var nodename: UByteArray,
    public var release: UByteArray,
    public var version: UByteArray,
    public var machine: UByteArray,
    public var domainname: UByteArray,
) {
    public open fun clone(): Utsname = Utsname(sysname, nodename, release, version, machine, domainname)

    public companion object {
        public fun typeLayout(): TypeLayout<Utsname> =
            object : TypeLayout<Utsname> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 390L
                override fun readNative(addr: Long): Utsname =
                    Utsname(
                        (PrimitiveArrayLayouts.uByteArray(65)).readNative(addr),
                        (PrimitiveArrayLayouts.uByteArray(65)).readNative(addr + 65L),
                        (PrimitiveArrayLayouts.uByteArray(65)).readNative(addr + 130L),
                        (PrimitiveArrayLayouts.uByteArray(65)).readNative(addr + 195L),
                        (PrimitiveArrayLayouts.uByteArray(65)).readNative(addr + 260L),
                        (PrimitiveArrayLayouts.uByteArray(65)).readNative(addr + 325L),
                    )
                override fun writeNative(addr: Long, value: Utsname) {
                    (PrimitiveArrayLayouts.uByteArray(65)).writeNative(addr, value.sysname)
                    (PrimitiveArrayLayouts.uByteArray(65)).writeNative(addr + 65L, value.nodename)
                    (PrimitiveArrayLayouts.uByteArray(65)).writeNative(addr + 130L, value.release)
                    (PrimitiveArrayLayouts.uByteArray(65)).writeNative(addr + 195L, value.version)
                    (PrimitiveArrayLayouts.uByteArray(65)).writeNative(addr + 260L, value.machine)
                    (PrimitiveArrayLayouts.uByteArray(65)).writeNative(addr + 325L, value.domainname)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Utsname =
                    Utsname(
                        (PrimitiveArrayLayouts.uByteArray(65)).readBytes(buf, offset),
                        (PrimitiveArrayLayouts.uByteArray(65)).readBytes(buf, offset + 65),
                        (PrimitiveArrayLayouts.uByteArray(65)).readBytes(buf, offset + 130),
                        (PrimitiveArrayLayouts.uByteArray(65)).readBytes(buf, offset + 195),
                        (PrimitiveArrayLayouts.uByteArray(65)).readBytes(buf, offset + 260),
                        (PrimitiveArrayLayouts.uByteArray(65)).readBytes(buf, offset + 325),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Utsname) {
                    (PrimitiveArrayLayouts.uByteArray(65)).writeBytes(buf, offset, value.sysname)
                    (PrimitiveArrayLayouts.uByteArray(65)).writeBytes(buf, offset + 65, value.nodename)
                    (PrimitiveArrayLayouts.uByteArray(65)).writeBytes(buf, offset + 130, value.release)
                    (PrimitiveArrayLayouts.uByteArray(65)).writeBytes(buf, offset + 195, value.version)
                    (PrimitiveArrayLayouts.uByteArray(65)).writeBytes(buf, offset + 260, value.machine)
                    (PrimitiveArrayLayouts.uByteArray(65)).writeBytes(buf, offset + 325, value.domainname)
                }
            }
    }
}

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
    }
}

public open class FileCloneRange(
    public var srcFd: S64,
    public var srcOffset: U64,
    public var srcLength: U64,
    public var destOffset: U64,
) {
    public open fun clone(): FileCloneRange = FileCloneRange(srcFd, srcOffset, srcLength, destOffset)

    public companion object {
        public fun typeLayout(): TypeLayout<FileCloneRange> =
            object : TypeLayout<FileCloneRange> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): FileCloneRange =
                    FileCloneRange(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U64).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                    )
                override fun writeNative(addr: Long, value: FileCloneRange) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.srcFd)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.srcOffset)
                    (PrimitiveLayouts.U64).writeNative(addr + 16L, value.srcLength)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.destOffset)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileCloneRange =
                    FileCloneRange(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileCloneRange) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.srcFd)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.srcOffset)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 16, value.srcLength)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.destOffset)
                }
            }
    }
}

// linux/filter.h
public open class SockFilter(
    public var code: U16,
    public var jt: U8,
    public var jf: U8,
    public var k: U32,
) {
    public open fun clone(): SockFilter = SockFilter(code, jt, jf, k)

    public companion object {
        public fun typeLayout(): TypeLayout<SockFilter> =
            object : TypeLayout<SockFilter> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): SockFilter =
                    SockFilter(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: SockFilter) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.code)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.jt)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.jf)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.k)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockFilter =
                    SockFilter(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockFilter) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.code)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.jt)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.jf)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.k)
                }
            }
    }
}

public open class SockFprog internal constructor(
    public var len: CUshort,
    filterCtorArg: MutPtr,
) {
    public var filter: MutPtr = filterCtorArg
        internal set

    public open fun clone(): SockFprog = SockFprog(len, filter)

    public companion object {
        public fun typeLayout(): TypeLayout<SockFprog> =
            object : TypeLayout<SockFprog> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): SockFprog =
                    SockFprog(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: SockFprog) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.len)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.filter)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SockFprog =
                    SockFprog(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SockFprog) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.len)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.filter)
                }
            }

        public operator fun invoke(
            len: CUshort,
            filter: MutPtr,
        ): SockFprog = SockFprog(
            len = len,
            filterCtorArg = filter,
        )
    }
}

public open class Statx(
    public var stxMask: U32,
    public var stxBlksize: U32,
    public var stxAttributes: U64,
    public var stxNlink: U32,
    public var stxUid: U32,
    public var stxGid: U32,
    public var stxMode: U16,
    public var statxPad1: UShortArray,
    public var stxIno: U64,
    public var stxSize: U64,
    public var stxBlocks: U64,
    public var stxAttributesMask: U64,
    public var stxAtime: StatxTimestamp,
    public var stxBtime: StatxTimestamp,
    public var stxCtime: StatxTimestamp,
    public var stxMtime: StatxTimestamp,
    public var stxRdevMajor: U32,
    public var stxRdevMinor: U32,
    public var stxDevMajor: U32,
    public var stxDevMinor: U32,
    public var stxMntId: U64,
    public var stxDioMemAlign: U32,
    public var stxDioOffsetAlign: U32,
    // The following fields are not available on Android as of
    // 2026-06.
    public var stxSubvol: U64,
    public var stxAtomicWriteUnitMin: U32,
    public var stxAtomicWriteUnitMax: U32,
    public var stxAtomicWriteSegmentsMax: U32,
    public var stxDioReadOffsetAlign: U32,
    public var stxAtomicWriteUnitMaxOpt: U32,
    public var statxPad2: UIntArray,
    public var statxPad3: ULongArray,
) {
    public open fun clone(): Statx = Statx(stxMask, stxBlksize, stxAttributes, stxNlink, stxUid, stxGid, stxMode, statxPad1, stxIno, stxSize, stxBlocks, stxAttributesMask, stxAtime, stxBtime, stxCtime, stxMtime, stxRdevMajor, stxRdevMinor, stxDevMajor, stxDevMinor, stxMntId, stxDioMemAlign, stxDioOffsetAlign, stxSubvol, stxAtomicWriteUnitMin, stxAtomicWriteUnitMax, stxAtomicWriteSegmentsMax, stxDioReadOffsetAlign, stxAtomicWriteUnitMaxOpt, statxPad2, statxPad3)

    public companion object {
        public fun typeLayout(): TypeLayout<Statx> =
            object : TypeLayout<Statx> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 256L
                override fun readNative(addr: Long): Statx =
                    Statx(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U32).readNative(addr + 24L),
                        (PrimitiveLayouts.U16).readNative(addr + 28L),
                        (PrimitiveArrayLayouts.uShortArray(1)).readNative(addr + 30L),
                        (PrimitiveLayouts.U64).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.U64).readNative(addr + 48L),
                        (PrimitiveLayouts.U64).readNative(addr + 56L),
                        (StatxTimestamp.typeLayout()).readNative(addr + 64L),
                        (StatxTimestamp.typeLayout()).readNative(addr + 80L),
                        (StatxTimestamp.typeLayout()).readNative(addr + 96L),
                        (StatxTimestamp.typeLayout()).readNative(addr + 112L),
                        (PrimitiveLayouts.U32).readNative(addr + 128L),
                        (PrimitiveLayouts.U32).readNative(addr + 132L),
                        (PrimitiveLayouts.U32).readNative(addr + 136L),
                        (PrimitiveLayouts.U32).readNative(addr + 140L),
                        (PrimitiveLayouts.U64).readNative(addr + 144L),
                        (PrimitiveLayouts.U32).readNative(addr + 152L),
                        (PrimitiveLayouts.U32).readNative(addr + 156L),
                        (PrimitiveLayouts.U64).readNative(addr + 160L),
                        (PrimitiveLayouts.U32).readNative(addr + 168L),
                        (PrimitiveLayouts.U32).readNative(addr + 172L),
                        (PrimitiveLayouts.U32).readNative(addr + 176L),
                        (PrimitiveLayouts.U32).readNative(addr + 180L),
                        (PrimitiveLayouts.U32).readNative(addr + 184L),
                        (PrimitiveArrayLayouts.uIntArray(1)).readNative(addr + 188L),
                        (PrimitiveArrayLayouts.uLongArray(8)).readNative(addr + 192L),
                    )
                override fun writeNative(addr: Long, value: Statx) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.stxMask)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.stxBlksize)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.stxAttributes)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.stxNlink)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.stxUid)
                    (PrimitiveLayouts.U32).writeNative(addr + 24L, value.stxGid)
                    (PrimitiveLayouts.U16).writeNative(addr + 28L, value.stxMode)
                    (PrimitiveArrayLayouts.uShortArray(1)).writeNative(addr + 30L, value.statxPad1)
                    (PrimitiveLayouts.U64).writeNative(addr + 32L, value.stxIno)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.stxSize)
                    (PrimitiveLayouts.U64).writeNative(addr + 48L, value.stxBlocks)
                    (PrimitiveLayouts.U64).writeNative(addr + 56L, value.stxAttributesMask)
                    (StatxTimestamp.typeLayout()).writeNative(addr + 64L, value.stxAtime)
                    (StatxTimestamp.typeLayout()).writeNative(addr + 80L, value.stxBtime)
                    (StatxTimestamp.typeLayout()).writeNative(addr + 96L, value.stxCtime)
                    (StatxTimestamp.typeLayout()).writeNative(addr + 112L, value.stxMtime)
                    (PrimitiveLayouts.U32).writeNative(addr + 128L, value.stxRdevMajor)
                    (PrimitiveLayouts.U32).writeNative(addr + 132L, value.stxRdevMinor)
                    (PrimitiveLayouts.U32).writeNative(addr + 136L, value.stxDevMajor)
                    (PrimitiveLayouts.U32).writeNative(addr + 140L, value.stxDevMinor)
                    (PrimitiveLayouts.U64).writeNative(addr + 144L, value.stxMntId)
                    (PrimitiveLayouts.U32).writeNative(addr + 152L, value.stxDioMemAlign)
                    (PrimitiveLayouts.U32).writeNative(addr + 156L, value.stxDioOffsetAlign)
                    (PrimitiveLayouts.U64).writeNative(addr + 160L, value.stxSubvol)
                    (PrimitiveLayouts.U32).writeNative(addr + 168L, value.stxAtomicWriteUnitMin)
                    (PrimitiveLayouts.U32).writeNative(addr + 172L, value.stxAtomicWriteUnitMax)
                    (PrimitiveLayouts.U32).writeNative(addr + 176L, value.stxAtomicWriteSegmentsMax)
                    (PrimitiveLayouts.U32).writeNative(addr + 180L, value.stxDioReadOffsetAlign)
                    (PrimitiveLayouts.U32).writeNative(addr + 184L, value.stxAtomicWriteUnitMaxOpt)
                    (PrimitiveArrayLayouts.uIntArray(1)).writeNative(addr + 188L, value.statxPad2)
                    (PrimitiveArrayLayouts.uLongArray(8)).writeNative(addr + 192L, value.statxPad3)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Statx =
                    Statx(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U16).readBytes(buf, offset + 28),
                        (PrimitiveArrayLayouts.uShortArray(1)).readBytes(buf, offset + 30),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 48),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 56),
                        (StatxTimestamp.typeLayout()).readBytes(buf, offset + 64),
                        (StatxTimestamp.typeLayout()).readBytes(buf, offset + 80),
                        (StatxTimestamp.typeLayout()).readBytes(buf, offset + 96),
                        (StatxTimestamp.typeLayout()).readBytes(buf, offset + 112),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 128),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 132),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 136),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 140),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 144),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 152),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 156),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 160),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 168),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 172),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 176),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 180),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 184),
                        (PrimitiveArrayLayouts.uIntArray(1)).readBytes(buf, offset + 188),
                        (PrimitiveArrayLayouts.uLongArray(8)).readBytes(buf, offset + 192),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Statx) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.stxMask)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.stxBlksize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.stxAttributes)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.stxNlink)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.stxUid)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 24, value.stxGid)
                    (PrimitiveLayouts.U16).writeBytes(buf, offset + 28, value.stxMode)
                    (PrimitiveArrayLayouts.uShortArray(1)).writeBytes(buf, offset + 30, value.statxPad1)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 32, value.stxIno)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.stxSize)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 48, value.stxBlocks)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 56, value.stxAttributesMask)
                    (StatxTimestamp.typeLayout()).writeBytes(buf, offset + 64, value.stxAtime)
                    (StatxTimestamp.typeLayout()).writeBytes(buf, offset + 80, value.stxBtime)
                    (StatxTimestamp.typeLayout()).writeBytes(buf, offset + 96, value.stxCtime)
                    (StatxTimestamp.typeLayout()).writeBytes(buf, offset + 112, value.stxMtime)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 128, value.stxRdevMajor)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 132, value.stxRdevMinor)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 136, value.stxDevMajor)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 140, value.stxDevMinor)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 144, value.stxMntId)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 152, value.stxDioMemAlign)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 156, value.stxDioOffsetAlign)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 160, value.stxSubvol)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 168, value.stxAtomicWriteUnitMin)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 172, value.stxAtomicWriteUnitMax)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 176, value.stxAtomicWriteSegmentsMax)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 180, value.stxDioReadOffsetAlign)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 184, value.stxAtomicWriteUnitMaxOpt)
                    (PrimitiveArrayLayouts.uIntArray(1)).writeBytes(buf, offset + 188, value.statxPad2)
                    (PrimitiveArrayLayouts.uLongArray(8)).writeBytes(buf, offset + 192, value.statxPad3)
                }
            }
    }
}

public open class StatxTimestamp(
    public var tvSec: S64,
    public var tvNsec: U32,
    public var statxTimestampPad1: IntArray,
) {
    public open fun clone(): StatxTimestamp = StatxTimestamp(tvSec, tvNsec, statxTimestampPad1)

    public companion object {
        public fun typeLayout(): TypeLayout<StatxTimestamp> =
            object : TypeLayout<StatxTimestamp> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): StatxTimestamp =
                    StatxTimestamp(
                        (PrimitiveLayouts.I64).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveArrayLayouts.intArray(1)).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: StatxTimestamp) {
                    (PrimitiveLayouts.I64).writeNative(addr, value.tvSec)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.tvNsec)
                    (PrimitiveArrayLayouts.intArray(1)).writeNative(addr + 12L, value.statxTimestampPad1)
                }
                override fun readBytes(buf: ByteArray, offset: Int): StatxTimestamp =
                    StatxTimestamp(
                        (PrimitiveLayouts.I64).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveArrayLayouts.intArray(1)).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: StatxTimestamp) {
                    (PrimitiveLayouts.I64).writeBytes(buf, offset, value.tvSec)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.tvNsec)
                    (PrimitiveArrayLayouts.intArray(1)).writeBytes(buf, offset + 12, value.statxTimestampPad1)
                }
            }
    }
}

public open class EpollEvent(
    public var events: UInt,
    public var u64: ULong,
) {
    public open fun clone(): EpollEvent = EpollEvent(events, u64)

    public open fun eq(other: EpollEvent): Boolean = this.events == other.events && this.u64 == other.u64

    public override fun hashCode(): Int {
        var state = 0
        val events = this.events
        val u64 = this.u64
        state = 31 * state + (events).hashCode()
        state = 31 * state + (u64).hashCode()
        return state
    }

    public companion object {
        public fun typeLayout(): TypeLayout<EpollEvent> =
            object : TypeLayout<EpollEvent> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): EpollEvent =
                    EpollEvent(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: EpollEvent) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.events)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.u64)
                }
                override fun readBytes(buf: ByteArray, offset: Int): EpollEvent =
                    EpollEvent(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: EpollEvent) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.events)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.u64)
                }
            }
    }
}

public open class Sigevent(
    public var sigevValue: Sigval,
    public var sigevSigno: CInt,
    public var sigevNotify: CInt,
    // Actually a union.  We only expose sigevNotifyThreadId because it's
    // the most useful member
    public var sigevNotifyThreadId: CInt,
    public var unused1: IntArray,
) {
    public open fun clone(): Sigevent = Sigevent(sigevValue, sigevSigno, sigevNotify, sigevNotifyThreadId, unused1)

    public open fun eq(other: Sigevent): Boolean = this.sigevValue == other.sigevValue && this.sigevSigno == other.sigevSigno && this.sigevNotify == other.sigevNotify && this.sigevNotifyThreadId == other.sigevNotifyThreadId

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.sigevValue).hashCode()
        state = 31 * state + (this.sigevSigno).hashCode()
        state = 31 * state + (this.sigevNotify).hashCode()
        state = 31 * state + (this.sigevNotifyThreadId).hashCode()
        return state
    }
}
