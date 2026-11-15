// port-lint: source new/apple/xnu/net/bpf.rs
package io.github.kotlinmania.rustlang.libc.new.apple.xnu.net.bpf

import io.github.kotlinmania.rustlang.*
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUshort
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.ior
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.iow
import io.github.kotlinmania.rustlang.new.apple.xnu.sys.ioccom.iowr
import io.github.kotlinmania.rustlang.unix.SizeT

// Header: `net / bpf.h`
//
// <https://github.com/apple-oss-distributions/xnu/blob/main/bsd/net/bpf.h>

public data class BpfInsn(
    public var code: CUshort = 0u,
    public var jt: CUchar = 0u,
    public var jf: CUchar = 0u,
    public var k: UInt = 0u,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<BpfInsn> =
            object : TypeLayout<BpfInsn> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): BpfInsn =
                    BpfInsn(
                        (PrimitiveLayouts.U16).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 2L),
                        (PrimitiveLayouts.U8).readNative(addr + 3L),
                        (PrimitiveLayouts.U32).readNative(addr + 4L),
                    )
                override fun writeNative(addr: Long, value: BpfInsn) {
                    (PrimitiveLayouts.U16).writeNative(addr, value.code)
                    (PrimitiveLayouts.U8).writeNative(addr + 2L, value.jt)
                    (PrimitiveLayouts.U8).writeNative(addr + 3L, value.jf)
                    (PrimitiveLayouts.U32).writeNative(addr + 4L, value.k)
                }
                override fun readBytes(buf: ByteArray, offset: Int): BpfInsn =
                    BpfInsn(
                        (PrimitiveLayouts.U16).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 4),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: BpfInsn) {
                    (PrimitiveLayouts.U16).writeBytes(buf, offset, value.code)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 2, value.jt)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 3, value.jf)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 4, value.k)
                }
            }
    }
}

public class BpfProgram internal constructor(
    public var bfLen: CUint,
    bfInsnsCtorArg: MutPtr,
) {
    public var bfInsns: MutPtr = bfInsnsCtorArg
        internal set

    public constructor() : this(
        bfLen = 0u,
        bfInsnsCtorArg = MutPtr(0L),
    )

    public operator fun component1(): CUint = this.bfLen

    public operator fun component2(): MutPtr = this.bfInsns

    public fun copy(bfLen: CUint = this.bfLen, bfInsns: MutPtr = this.bfInsns): BpfProgram = BpfProgram(bfLen = bfLen, bfInsnsCtorArg = bfInsns)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BpfProgram) return false
        if (this.bfLen != other.bfLen) return false
        if (this.bfInsns != other.bfInsns) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.bfLen.hashCode()
        result = 31 * result + this.bfInsns.hashCode()
        return result
    }

    public fun clone(): BpfProgram = BpfProgram(bfLen, bfInsns)

    public companion object {
        public fun typeLayout(): TypeLayout<BpfProgram> =
            object : TypeLayout<BpfProgram> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): BpfProgram =
                    BpfProgram(
                        (PrimitiveLayouts.U32).readNative(addr),
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: BpfProgram) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.bfLen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr + 8L, value.bfInsns)
                }
                override fun readBytes(buf: ByteArray, offset: Int): BpfProgram =
                    BpfProgram(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: BpfProgram) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.bfLen)
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset + 8, value.bfInsns)
                }
            }

        public operator fun invoke(
            bfLen: CUint = 0u,
            bfInsns: MutPtr = MutPtr(0L),
        ): BpfProgram = BpfProgram(
            bfLen = bfLen,
            bfInsnsCtorArg = bfInsns,
        )
    }
}

public data class BpfDltlist(
    public var bflLen: UInt = 0u,
    public var bflU: CAnonymousBflU = CAnonymousBflU(),
)

public class CAnonymousBflU internal constructor(
    bfluListCtorArg: MutPtr,
    public var bfluPad: ULong,
) {
    public var bfluList: MutPtr = bfluListCtorArg
        internal set

    public constructor() : this(
        bfluListCtorArg = MutPtr(0L),
        bfluPad = 0uL,
    )

    public operator fun component1(): MutPtr = this.bfluList

    public operator fun component2(): ULong = this.bfluPad

    public fun copy(bfluList: MutPtr = this.bfluList, bfluPad: ULong = this.bfluPad): CAnonymousBflU = CAnonymousBflU(bfluListCtorArg = bfluList, bfluPad = bfluPad)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CAnonymousBflU) return false
        if (this.bfluList != other.bfluList) return false
        if (this.bfluPad != other.bfluPad) return false
        return true
    }

    override fun hashCode(): Int {
        var result = this.bfluList.hashCode()
        result = 31 * result + this.bfluPad.hashCode()
        return result
    }

    public companion object {
        public operator fun invoke(
            bfluList: MutPtr = MutPtr(0L),
            bfluPad: ULong = 0uL,
        ): CAnonymousBflU = CAnonymousBflU(
            bfluListCtorArg = bfluList,
            bfluPad = bfluPad,
        )
    }
}

public val DLT_NULL: CUint = 0u

// no link-layer encapsulation
public val DLT_EN10MB: CUint = 1u

// Ethernet (10Mb)
public val DLT_EN3MB: CUint = 2u

// Experimental Ethernet (3Mb)
public val DLT_AX25: CUint = 3u

// Amateur Radio AX.25
public val DLT_PRONET: CUint = 4u

// Proteon ProNET Token Ring
public val DLT_CHAOS: CUint = 5u

// Chaos
public val DLT_IEEE802: CUint = 6u

// IEEE 802 Networks
public val DLT_ARCNET: CUint = 7u

// ARCNET
public val DLT_SLIP: CUint = 8u

// Serial Line IP
public val DLT_PPP: CUint = 9u

// Point-to-point Protocol
public val DLT_FDDI: CUint = 10u

// FDDI
public val DLT_ATM_RFC1483: CUint = 11u

// LLC/SNAP encapsulated atm
public val DLT_RAW: CUint = 12u

// raw IP
public val DLT_LOOP: CUint = 108u

public val BPF_ALIGNMENT: SizeT = sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32).toULong()

public fun bpfWordalign(x: Int): Int = (x + (BPF_ALIGNMENT.toInt() - 1)) and (BPF_ALIGNMENT.toInt() - 1).inv()

public val BIOCGRSIG: CUlong = ior<CUint>('B'.code.toULong(), 114uL)

public val BIOCSRSIG: CUlong = iow<CUint>('B'.code.toULong(), 115uL)

public val BIOCGSEESENT: CUlong = ior<CUint>('B'.code.toULong(), 118uL)

public val BIOCSSEESENT: CUlong = iow<CUint>('B'.code.toULong(), 119uL)

public val BIOCSDLT: CUlong = iow<CUint>('B'.code.toULong(), 120uL)

public val BIOCGDLTLIST: CUlong = iowr<BpfDltlist>('B'.code.toULong(), 121uL)
