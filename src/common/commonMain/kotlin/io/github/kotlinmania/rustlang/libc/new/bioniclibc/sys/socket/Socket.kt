// port-lint: source new/bionic_libc/sys/socket.rs
package io.github.kotlinmania.rustlang.libc.new.bioniclibc.sys.socket

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.SsizeT
import io.github.kotlinmania.rustlang.libc.unix.UidT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.b64.SocklenT

// Header: `sys / socket.h`

public open class Msghdr internal constructor(
    msgNameCtorArg: MutPtr,
    public var msgNamelen: SocklenT,
    msgIovCtorArg: MutPtr,
    public var msgIovlen: SizeT,
    msgControlCtorArg: MutPtr,
    public var msgControllen: SizeT,
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
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): Msghdr =
                    Msghdr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                        (PrimitiveLayouts.U32).readNative(addr + 8L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 16L),
                        (PrimitiveLayouts.U64).readNative(addr + 24L),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 32L),
                        (PrimitiveLayouts.U64).readNative(addr + 40L),
                        (PrimitiveLayouts.I32).readNative(addr + 48L),
                    )
                override fun writeNative(addr: Long, value: Msghdr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.msgName)
                    (PrimitiveLayouts.U32).writeNative(addr + 8L, value.msgNamelen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 16L, value.msgIov)
                    (PrimitiveLayouts.U64).writeNative(addr + 24L, value.msgIovlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 32L, value.msgControl)
                    (PrimitiveLayouts.U64).writeNative(addr + 40L, value.msgControllen)
                    (PrimitiveLayouts.I32).writeNative(addr + 48L, value.msgFlags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Msghdr =
                    Msghdr(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 32),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 40),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 48),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Msghdr) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.msgName)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 8, value.msgNamelen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 16, value.msgIov)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.msgIovlen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 32, value.msgControl)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 40, value.msgControllen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 48, value.msgFlags)
                }
            }

        public operator fun invoke(
            msgName: MutPtr,
            msgNamelen: SocklenT,
            msgIov: MutPtr,
            msgIovlen: SizeT,
            msgControl: MutPtr,
            msgControllen: SizeT,
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
    }
}

public open class Cmsghdr(
    public var cmsgLen: SizeT,
    public var cmsgLevel: CInt,
    public var cmsgType: CInt,
) {
    public open fun clone(): Cmsghdr = Cmsghdr(cmsgLen, cmsgLevel, cmsgType)

    public companion object {
        public fun typeLayout(): TypeLayout<Cmsghdr> =
            object : TypeLayout<Cmsghdr> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Cmsghdr =
                    Cmsghdr(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.I32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: Cmsghdr) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.cmsgLen)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.cmsgLevel)
                    (PrimitiveLayouts.I32).writeNative(addr + 12L, value.cmsgType)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Cmsghdr =
                    Cmsghdr(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Cmsghdr) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.cmsgLen)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.cmsgLevel)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 12, value.cmsgType)
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

public expect fun recvmmsg(sockfd: CInt, msgvec: MutPtr, vlen: CUint, flags: CInt, timeout: ConstPtr): CInt

public expect fun sendmmsg(sockfd: CInt, msgvec: ConstPtr, vlen: CUint, flags: CInt): CInt

public expect fun recvfrom(socket: CInt, buf: MutPtr, len: SizeT, flags: CInt, addr: MutPtr, addrlen: MutPtr): SsizeT
