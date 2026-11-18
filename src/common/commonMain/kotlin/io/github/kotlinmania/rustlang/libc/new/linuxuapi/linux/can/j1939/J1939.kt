// port-lint: source new/linux_uapi/linux/can/j1939.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.can.j1939

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUchar
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUint
import io.github.kotlinmania.rustlang.core.ffi.primitives.CUlong
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.can.CAN_J1939
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.can.SOL_CAN_BASE

// `linux / can / j1939.h`

public val J1939_MAX_UNICAST_ADDR: CUchar = 0xfdu.toUByte()

public val J1939_IDLE_ADDR: CUchar = 0xfeu.toUByte()

public val J1939_NO_ADDR: CUchar = 0xffu.toUByte()

public val J1939_NO_NAME: CUlong = 0uL

public val J1939_PGN_REQUEST: CUint = 0x0ea00u

public val J1939_PGN_ADDRESS_CLAIMED: CUint = 0x0ee00u

public val J1939_PGN_ADDRESS_COMMANDED: CUint = 0x0fed8u

public val J1939_PGN_PDU1_MAX: CUint = 0x3ff00u

public val J1939_PGN_MAX: CUint = 0x3ffffu

public val J1939_NO_PGN: CUint = 0x40000u

public typealias PgnT = UInt

public typealias PriorityT = UByte

public typealias NameT = ULong

public val SOL_CAN_J1939: CInt = SOL_CAN_BASE + CAN_J1939

// Note(cleanup): these could use cEnum if it can accept anonymous enums.
public val SO_J1939_FILTER: CInt = 1

public val SO_J1939_PROMISC: CInt = 2

public val SO_J1939_SEND_PRIO: CInt = 3

public val SO_J1939_ERRQUEUE: CInt = 4

public val SCM_J1939_DEST_ADDR: CInt = 1

public val SCM_J1939_DEST_NAME: CInt = 2

public val SCM_J1939_PRIO: CInt = 3

public val SCM_J1939_ERRQUEUE: CInt = 4

public val J1939_NLA_PAD: CInt = 0

public val J1939_NLA_BYTES_ACKED: CInt = 1

public val J1939_NLA_TOTAL_SIZE: CInt = 2

public val J1939_NLA_PGN: CInt = 3

public val J1939_NLA_SRC_NAME: CInt = 4

public val J1939_NLA_DEST_NAME: CInt = 5

public val J1939_NLA_SRC_ADDR: CInt = 6

public val J1939_NLA_DEST_ADDR: CInt = 7

public val J1939_EE_INFO_NONE: CInt = 0

public val J1939_EE_INFO_TX_ABORT: CInt = 1

public val J1939_EE_INFO_RX_RTS: CInt = 2

public val J1939_EE_INFO_RX_DPO: CInt = 3

public val J1939_EE_INFO_RX_ABORT: CInt = 4

public open class J1939Filter(
    public var name: NameT,
    public var nameMask: NameT,
    public var pgn: PgnT,
    public var pgnMask: PgnT,
    public var addr: UByte,
    public var addrMask: UByte,
) {
    public open fun clone(): J1939Filter = J1939Filter(name, nameMask, pgn, pgnMask, addr, addrMask)

    public companion object {
        public fun typeLayout(): TypeLayout<J1939Filter> =
            object : TypeLayout<J1939Filter> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): J1939Filter =
                    J1939Filter(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                        (PrimitiveLayouts.U8).readNative(addr + 24L),
                        (PrimitiveLayouts.U8).readNative(addr + 25L),
                    )
                override fun writeNative(addr: Long, value: J1939Filter) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.name)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.nameMask)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.pgn)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.pgnMask)
                    (PrimitiveLayouts.U8).writeNative(addr + 24L, value.addr)
                    (PrimitiveLayouts.U8).writeNative(addr + 25L, value.addrMask)
                }
                override fun readBytes(buf: ByteArray, offset: Int): J1939Filter =
                    J1939Filter(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 24),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 25),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: J1939Filter) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.name)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.nameMask)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.pgn)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.pgnMask)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 24, value.addr)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 25, value.addrMask)
                }
            }
    }
}

/**
 * Constants may change across releases. See the [usage guidelines](crate#usage-guidelines)
 * for details.
 */
public val J1939_FILTER_MAX: CInt = 512
