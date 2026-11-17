// port-lint: source new/musl/sys/socket.rs
package io.github.kotlinmania.rustlang.libc.new.musl.sys.socket

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.SocklenT

// Header: `sys / socket.h`
//
// * Headers: <https://git.musl-libc.org/cgit/musl/blob/arch/include/sys/socket.h> (official)
// * Headers: <https://github.com/kraj/musl/blob/kraj/master/include/sys/socket.h> (mirror)

public open class Msghdr internal constructor(
    msgNameCtorArg: MutPtr,
    public var msgNamelen: SocklenT,
    msgIovCtorArg: MutPtr,
    public var msgIovlen: CInt,
    public var pad1: CInt,
    msgControlCtorArg: MutPtr,
    public var msgControllen: SocklenT,
    public var pad2: CInt,
    public var msgFlags: CInt,
) {
    public var msgName: MutPtr = msgNameCtorArg
        internal set

    public var msgIov: MutPtr = msgIovCtorArg
        internal set

    public var msgControl: MutPtr = msgControlCtorArg
        internal set

    public open fun clone(): Msghdr = Msghdr(msgName, msgNamelen, msgIov, msgIovlen, pad1, msgControl, msgControllen, pad2, msgFlags)

    public companion object {
        public fun typeLayout(): TypeLayout<Msghdr> =
            object : TypeLayout<Msghdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): Msghdr =
                    Msghdr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.I32).readNative(addr + 24L),
                        (PrimitiveLayouts.I32).readNative(addr + 28L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.U32).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 44L),
                        (PrimitiveLayouts.I32).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Msghdr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.msgName)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.msgNamelen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.msgIov)
                    (PrimitiveLayouts.I32).writeNative(addr + 24L, value.msgIovlen)
                    (PrimitiveLayouts.I32).writeNative(addr + 28L, value.pad1)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.msgControl)
                    (PrimitiveLayouts.U32).writeNative(addr + 40L, value.msgControllen)
                    (PrimitiveLayouts.I32).writeNative(addr + 44L, value.pad2)
                    (PrimitiveLayouts.I32).writeNative(addr + 48L, value.msgFlags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Msghdr =
                    Msghdr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 28),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 44),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Msghdr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.msgName)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.msgNamelen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.msgIov)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 24, value.msgIovlen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 28, value.pad1)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.msgControl)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 40, value.msgControllen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 44, value.pad2)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 48, value.msgFlags)
                }
            }

        public operator fun invoke(
            msgName: MutPtr,
            msgNamelen: SocklenT,
            msgIov: MutPtr,
            msgIovlen: CInt,
            pad1: CInt,
            msgControl: MutPtr,
            msgControllen: SocklenT,
            pad2: CInt,
            msgFlags: CInt,
        ): Msghdr = Msghdr(
            msgNameCtorArg = msgName,
            msgNamelen = msgNamelen,
            msgIovCtorArg = msgIov,
            msgIovlen = msgIovlen,
            pad1 = pad1,
            msgControlCtorArg = msgControl,
            msgControllen = msgControllen,
            pad2 = pad2,
            msgFlags = msgFlags,
        )
    }
}

public open class Cmsghdr(
    public var cmsgLen: SocklenT,
    public var pad1: CInt,
    public var cmsgLevel: CInt,
    public var cmsgType: CInt,
) {
    public open fun clone(): Cmsghdr = Cmsghdr(cmsgLen, pad1, cmsgLevel, cmsgType)

    public companion object {
        public fun typeLayout(): TypeLayout<Cmsghdr> =
            object : TypeLayout<Cmsghdr> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Cmsghdr =
                    Cmsghdr(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 4L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Cmsghdr) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.cmsgLen)
                    (PrimitiveLayouts.I32).writeNative(addr + 4L, value.pad1)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.cmsgLevel)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.cmsgType)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Cmsghdr =
                    Cmsghdr(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Cmsghdr) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.cmsgLen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 4, value.pad1)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.cmsgLevel)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.cmsgType)
                }
            }
    }
}

public open class Mmsghdr(
    public var msgHdr: Msghdr,
    public var msgLen: CUint,
) {
    public open fun clone(): Mmsghdr = Mmsghdr(msgHdr, msgLen)

    public companion object {
        public fun typeLayout(): TypeLayout<Mmsghdr> =
            object : TypeLayout<Mmsghdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): Mmsghdr =
                    Mmsghdr(
                        (Msghdr.typeLayout()).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: Mmsghdr) {
                    (Msghdr.typeLayout()).writeNative(addr, value.msgHdr)
                    (PrimitiveLayouts.U32).writeNative(addr + 56L, value.msgLen)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Mmsghdr =
                    Mmsghdr(
                        (Msghdr.typeLayout()).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Mmsghdr) {
                    (Msghdr.typeLayout()).writeBytes(buf, offset, value.msgHdr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 56, value.msgLen)
                }
            }
    }
}

public expect fun sendmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CUint): CInt

public expect fun recvmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CUint, timeout: MutPtr): CInt

public val SHUT_RD: CInt = 0

public val SHUT_WR: CInt = 1

public val SHUT_RDWR: CInt = 2

public val SOCK_STREAM: CInt = 1

public val SOCK_DGRAM: CInt = 2

public val SOCK_RAW: CInt = 3

public val SOCK_RDM: CInt = 4

public val SOCK_SEQPACKET: CInt = 5

public val SOCK_DCCP: CInt = 6

public val SOCK_PACKET: CInt = 10

public val SOCK_CLOEXEC: CInt = 524288

public val SOCK_NONBLOCK: CInt = 2048

public val PF_IB: CInt = 27

public val PF_MPLS: CInt = 28

public val PF_NFC: CInt = 39

public val PF_VSOCK: CInt = 40

public val PF_XDP: CInt = 44

public val AF_IB: CInt = PF_IB

public val AF_MPLS: CInt = PF_MPLS

public val AF_NFC: CInt = PF_NFC

public val AF_VSOCK: CInt = PF_VSOCK

public val AF_XDP: CInt = PF_XDP
