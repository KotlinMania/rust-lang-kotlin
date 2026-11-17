// port-lint: source new/linux_uapi/linux/futex.rs
package io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.futex

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U32
import io.github.kotlinmania.rustlang.libc.new.linuxuapi.linux.types.U64
import io.github.kotlinmania.rustlang.libc.types.u32CastInt

// Header: `linux / futex.h`

public val FUTEX_WAIT: CInt = 0

public val FUTEX_WAKE: CInt = 1

public val FUTEX_FD: CInt = 2

public val FUTEX_REQUEUE: CInt = 3

public val FUTEX_CMP_REQUEUE: CInt = 4

public val FUTEX_WAKE_OP: CInt = 5

public val FUTEX_LOCK_PI: CInt = 6

public val FUTEX_UNLOCK_PI: CInt = 7

public val FUTEX_TRYLOCK_PI: CInt = 8

public val FUTEX_WAIT_BITSET: CInt = 9

public val FUTEX_WAKE_BITSET: CInt = 10

public val FUTEX_WAIT_REQUEUE_PI: CInt = 11

public val FUTEX_CMP_REQUEUE_PI: CInt = 12

public val FUTEX_LOCK_PI2: CInt = 13

public val FUTEX_PRIVATE_FLAG: CInt = 128

public val FUTEX_CLOCK_REALTIME: CInt = 256

public val FUTEX_ROBUST_UNLOCK: CInt = 512

public val FUTEX_ROBUST_LIST32: CInt = 1024

public val FUTEX_CMD_MASK: CInt = (FUTEX_PRIVATE_FLAG or FUTEX_CLOCK_REALTIME or FUTEX_ROBUST_UNLOCK or FUTEX_ROBUST_LIST32).inv()

public val FUTEX2_SIZE_U8: CInt = 0x00

public val FUTEX2_SIZE_U16: CInt = 0x01

public val FUTEX2_SIZE_U32: CInt = 0x02

public val FUTEX2_SIZE_U64: CInt = 0x03

public val FUTEX2_NUMA: CInt = 0x04

public val FUTEX2_PRIVATE: CInt = FUTEX_PRIVATE_FLAG

public val FUTEX2_SIZE_MASK: CInt = 0x03

public val FUTEX_32: CInt = FUTEX2_SIZE_U32

public val FUTEX_WAITV_MAX: CInt = 128

public open class FutexWaitv(
    public var value: U64,
    public var uaddr: U64,
    public var flags: U32,
    public var reserved: U32,
) {
    public open fun clone(): FutexWaitv = FutexWaitv(value, uaddr, flags, reserved)

    public companion object {
        public fun typeLayout(): TypeLayout<FutexWaitv> =
            object : TypeLayout<FutexWaitv> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): FutexWaitv =
                    FutexWaitv(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U64).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 16L),
                        (PrimitiveLayouts.U32).readNative(addr + 20L),
                    )
                override fun writeNative(addr: Long, value: FutexWaitv) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.value)
                    (PrimitiveLayouts.U64).writeNative(addr + 8L, value.uaddr)
                    (PrimitiveLayouts.U32).writeNative(addr + 16L, value.flags)
                    (PrimitiveLayouts.U32).writeNative(addr + 20L, value.reserved)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FutexWaitv =
                    FutexWaitv(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U64).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 16),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 20),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FutexWaitv) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.value)
                    (PrimitiveLayouts.U64).writeBytes(buf, offset + 8, value.uaddr)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 16, value.flags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 20, value.reserved)
                }
            }
    }
}

public val FUTEX_WAITERS: UInt = 0x80000000u

public val FUTEX_OWNER_DIED: UInt = 0x40000000u

public val FUTEX_TID_MASK: UInt = 0x3fffffffu

public val FUTEX_OP_SET: CInt = 0

public val FUTEX_OP_ADD: CInt = 1

public val FUTEX_OP_OR: CInt = 2

public val FUTEX_OP_ANDN: CInt = 3

public val FUTEX_OP_XOR: CInt = 4

public val FUTEX_OP_OPARG_SHIFT: CInt = 8

public val FUTEX_OP_CMP_EQ: CInt = 0

public val FUTEX_OP_CMP_NE: CInt = 1

public val FUTEX_OP_CMP_LT: CInt = 2

public val FUTEX_OP_CMP_LE: CInt = 3

public val FUTEX_OP_CMP_GT: CInt = 4

public val FUTEX_OP_CMP_GE: CInt = 5

public val FUTEX_BITSET_MATCH_ANY: CInt = u32CastInt(0xffffffffu)

public fun futexOp(op: CInt, oparg: CInt, cmp: CInt, cmparg: CInt): CInt = ((op and 0xf) shl 28) or ((cmp and 0xf) shl 24) or ((oparg and 0xfff) shl 12) or (cmparg and 0xfff)
