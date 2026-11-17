@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.b64

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.SizeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.MsglenT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.MsgqnumT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.TimeT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.musl.b64.aarch64.IpcPerm

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

public open class PthreadAttrT(
    public var size: ULongArray,
) {
    public open fun clone(): PthreadAttrT = PthreadAttrT(size)

    public companion object {
        public fun typeLayout(): TypeLayout<PthreadAttrT> =
            object : TypeLayout<PthreadAttrT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 56L
                override fun readNative(addr: Long): PthreadAttrT =
                    PthreadAttrT(
                        (PrimitiveArrayLayouts.uLongArray(7)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: PthreadAttrT) {
                    (PrimitiveArrayLayouts.uLongArray(7)).writeNative(addr, value.size)
                }
                override fun readBytes(buf: ByteArray, offset: Int): PthreadAttrT =
                    PthreadAttrT(
                        (PrimitiveArrayLayouts.uLongArray(7)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: PthreadAttrT) {
                    (PrimitiveArrayLayouts.uLongArray(7)).writeBytes(buf, offset, value.size)
                }
            }
    }
}

public open class SigsetT(
    public var value: ULongArray,
) {
    public open fun clone(): SigsetT = SigsetT(value)

    public companion object {
        public fun typeLayout(): TypeLayout<SigsetT> =
            object : TypeLayout<SigsetT> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 128L
                override fun readNative(addr: Long): SigsetT =
                    SigsetT(
                        (PrimitiveArrayLayouts.uLongArray(16)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SigsetT) {
                    (PrimitiveArrayLayouts.uLongArray(16)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SigsetT =
                    SigsetT(
                        (PrimitiveArrayLayouts.uLongArray(16)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SigsetT) {
                    (PrimitiveArrayLayouts.uLongArray(16)).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class ShmidDs(
    public var shmPerm: IpcPerm,
    public var shmSegsz: SizeT,
    public var shmAtime: TimeT,
    public var shmDtime: TimeT,
    public var shmCtime: TimeT,
    public var shmCpid: PidT,
    public var shmLpid: PidT,
    public var shmNattch: CUlong,
    public var pad1: CUlong,
    public var pad2: CUlong,
) {
    public open fun clone(): ShmidDs = ShmidDs(shmPerm, shmSegsz, shmAtime, shmDtime, shmCtime, shmCpid, shmLpid, shmNattch, pad1, pad2)
}

public open class MsqidDs(
    public var msgPerm: IpcPerm,
    public var msgStime: TimeT,
    public var msgRtime: TimeT,
    public var msgCtime: TimeT,
    public var msgCbytes: CUlong,
    public var msgQnum: MsgqnumT,
    public var msgQbytes: MsglenT,
    public var msgLspid: PidT,
    public var msgLrpid: PidT,
    public var pad1: CUlong,
    public var pad2: CUlong,
) {
    public open fun clone(): MsqidDs = MsqidDs(msgPerm, msgStime, msgRtime, msgCtime, msgCbytes, msgQnum, msgQbytes, msgLspid, msgLrpid, pad1, pad2)
}

public open class SemT(
    public var value: IntArray,
) {
    public open fun clone(): SemT = SemT(value)

    public companion object {
        public fun typeLayout(): TypeLayout<SemT> =
            object : TypeLayout<SemT> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): SemT =
                    SemT(
                        (PrimitiveArrayLayouts.intArray(8)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SemT) {
                    (PrimitiveArrayLayouts.intArray(8)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SemT =
                    SemT(
                        (PrimitiveArrayLayouts.intArray(8)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SemT) {
                    (PrimitiveArrayLayouts.intArray(8)).writeBytes(buf, offset, value.value)
                }
            }
    }
}
