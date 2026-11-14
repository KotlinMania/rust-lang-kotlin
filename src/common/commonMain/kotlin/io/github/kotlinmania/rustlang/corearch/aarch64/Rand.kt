// port-lint: source aarch64/rand.rs
package io.github.kotlinmania.rustlang.corearch.aarch64

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// AArch64 Random Number intrinsics
//
// [ACLE documentation](https://arm-software.github.io/acle/main/acle.html#random-number-generation-intrinsics)

public expect fun rndr(): Tuple

public expect fun rndrrs(): Tuple

public open class Tuple(
    public var bits: ULong,
    public var status: Boolean,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Tuple> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Tuple> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Tuple =
                    Tuple(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Tuple) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.bits)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 8L, value.status)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Tuple =
                    Tuple(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Tuple) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.bits)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 8, value.status)
                }
            }
    }
}

/**
 * Stores a 64-bit random number into the object pointed to by the argument and returns
 * zero. If the implementation could not generate a random number within a reasonable
 * period of time the object pointed to by the input is set to zero and a non-zero value
 * is returned.
 */
public fun rndr(value: MutPtr): Int {
    val value1 = value
    val structVal = rndr()
    val bits = structVal.bits
    val status = structVal.status
    (PrimitiveLayouts.U64).writeNative((value1).rawAddress, bits)
    return status.toInt()
}

/**
 * Reseeds the random number generator. After that stores a 64-bit random number into
 * the object pointed to by the argument and returns zero. If the implementation could
 * not generate a random number within a reasonable period of time the object pointed
 * to by the input is set to zero and a non-zero value is returned.
 */
public fun rndrrs(value: MutPtr): Int {
    val value1 = value
    val structVal = rndrrs()
    val bits = structVal.bits
    val status = structVal.status
    (PrimitiveLayouts.U64).writeNative((value1).rawAddress, bits)
    return status.toInt()
}
