// port-lint: source arm_shared/neon/generated.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared.neon

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.aarch64.neon.vld1P64
import io.github.kotlinmania.rustlang.corearch.aarch64.neon.vld1S64
import io.github.kotlinmania.rustlang.corearch.aarch64.neon.vld1U64
import io.github.kotlinmania.rustlang.corearch.deinterleaveMask
import io.github.kotlinmania.rustlang.corearch.identity
import io.github.kotlinmania.rustlang.corearch.interleaveMask
import io.github.kotlinmania.rustlang.corearch.simd.F16x4
import io.github.kotlinmania.rustlang.corearch.simd.F16x8
import io.github.kotlinmania.rustlang.corearch.simd.I16x4
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x2
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.I8x8
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x4
import io.github.kotlinmania.rustlang.corearch.simd.U16x8
import io.github.kotlinmania.rustlang.corearch.simd.U32x2
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U64x2
import io.github.kotlinmania.rustlang.corearch.simd.U8x16
import io.github.kotlinmania.rustlang.corearch.simd.U8x8
import io.github.kotlinmania.rustlang.corearch.simdShuffle

public typealias V = Simd<F16>

public typealias Arr = List<F16>

public typealias W = Simd<F16>

/**
 * CRC32 single round checksum for bytes (8 bits).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/__crc32b)
 */
public fun crc32b(crc: UInt, data: UByte): UInt {
    fun crc32b(crc1: UInt, data1: UInt): UInt = 0u
    return crc32b(crc, (data.toUInt()).toUByte())
}

/**
 * CRC32-C single round checksum for bytes (8 bits).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/__crc32cb)
 */
public fun crc32cb(crc: UInt, data: UByte): UInt {
    fun crc32cb(crc1: UInt, data1: UInt): UInt = 0u
    return crc32cb(crc, (data.toUInt()).toUByte())
}

/**
 * CRC32-C single round checksum for bytes (16 bits).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/__crc32ch)
 */
public fun crc32ch(crc: UInt, data: UShort): UInt {
    fun crc32ch(crc1: UInt, data1: UInt): UInt = 0u
    return crc32ch(crc, (data.toUInt()).toUShort())
}

/**
 * CRC32-C single round checksum for bytes (32 bits).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/__crc32cw)
 */
public fun crc32cw(crc: UInt, data: UInt): UInt {
    fun crc32cw(crc1: UInt, data1: UInt): UInt = 0u
    return crc32cw(crc, data)
}

/**
 * CRC32 single round checksum for bytes (16 bits).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/__crc32h)
 */
public fun crc32h(crc: UInt, data: UShort): UInt {
    fun crc32h(crc1: UInt, data1: UInt): UInt = 0u
    return crc32h(crc, (data.toUInt()).toUShort())
}

/**
 * CRC32 single round checksum for bytes (32 bits).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/__crc32w)
 */
public fun crc32w(crc: UInt, data: UInt): UInt {
    fun crc32w(crc1: UInt, data1: UInt): UInt = 0u
    return crc32w(crc, data)
}

/**
 * Absolute difference and accumulate (64-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaS16)
 */
public fun vabaS16(a: Int16x4T, b: Int16x4T, c: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = vabdS16(b, c); _simdArg0 }

/**
 * Absolute difference and accumulate (64-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaS32)
 */
public fun vabaS32(a: Int32x2T, b: Int32x2T, c: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = vabdS32(b, c); _simdArg0 }

/**
 * Absolute difference and accumulate (64-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaS8)
 */
public fun vabaS8(a: Int8x8T, b: Int8x8T, c: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = vabdS8(b, c); _simdArg0 }

/**
 * Absolute difference and accumulate (64-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaU16)
 */
public fun vabaU16(a: Uint16x4T, b: Uint16x4T, c: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = vabdU16(b, c); _simdArg0 }

/**
 * Absolute difference and accumulate (64-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaU32)
 */
public fun vabaU32(a: Uint32x2T, b: Uint32x2T, c: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = vabdU32(b, c); _simdArg0 }

/**
 * Absolute difference and accumulate (64-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaU8)
 */
public fun vabaU8(a: Uint8x8T, b: Uint8x8T, c: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = vabdU8(b, c); _simdArg0 }

/**
 * Signed Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalS8)
 */
public fun vabalS8(a: Int16x8T, b: Int8x8T, c: Int8x8T): Int16x8T {
    val d: Int8x8T = vabdS8(b, c)
    return run {
        val e: Uint8x8T = run { val _simdArg0 = d; Uint8x8T() }
        run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = e; _simdArg0 }; _simdArg0 }
    }
}

/**
 * Signed Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalS16)
 */
public fun vabalS16(a: Int32x4T, b: Int16x4T, c: Int16x4T): Int32x4T {
    val d: Int16x4T = vabdS16(b, c)
    return run {
        val e: Uint16x4T = run { val _simdArg0 = d; Uint16x4T() }
        run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = e; _simdArg0 }; _simdArg0 }
    }
}

/**
 * Signed Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalS32)
 */
public fun vabalS32(a: Int64x2T, b: Int32x2T, c: Int32x2T): Int64x2T {
    val d: Int32x2T = vabdS32(b, c)
    return run {
        val e: Uint32x2T = run { val _simdArg0 = d; Uint32x2T() }
        run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = e; _simdArg0 }; _simdArg0 }
    }
}

/**
 * Unsigned Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalU8)
 */
public fun vabalU8(a: Uint16x8T, b: Uint8x8T, c: Uint8x8T): Uint16x8T {
    val d: Uint8x8T = vabdU8(b, c)
    return run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = d; _simdArg0 }; _simdArg0 }
}

/**
 * Unsigned Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalU16)
 */
public fun vabalU16(a: Uint32x4T, b: Uint16x4T, c: Uint16x4T): Uint32x4T {
    val d: Uint16x4T = vabdU16(b, c)
    return run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = d; _simdArg0 }; _simdArg0 }
}

/**
 * Unsigned Absolute difference and Accumulate Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabalU32)
 */
public fun vabalU32(a: Uint64x2T, b: Uint32x2T, c: Uint32x2T): Uint64x2T {
    val d: Uint32x2T = vabdU32(b, c)
    return run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = d; _simdArg0 }; _simdArg0 }
}

/**
 * Absolute difference and accumulate (128-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaqS16)
 */
public fun vabaqS16(a: Int16x8T, b: Int16x8T, c: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = vabdqS16(b, c); _simdArg0 }

/**
 * Absolute difference and accumulate (128-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaqS32)
 */
public fun vabaqS32(a: Int32x4T, b: Int32x4T, c: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = vabdqS32(b, c); _simdArg0 }

/**
 * Absolute difference and accumulate (128-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaqS8)
 */
public fun vabaqS8(a: Int8x16T, b: Int8x16T, c: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = vabdqS8(b, c); _simdArg0 }

/**
 * Absolute difference and accumulate (128-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaqU16)
 */
public fun vabaqU16(a: Uint16x8T, b: Uint16x8T, c: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = vabdqU16(b, c); _simdArg0 }

/**
 * Absolute difference and accumulate (128-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaqU32)
 */
public fun vabaqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = vabdqU32(b, c); _simdArg0 }

/**
 * Absolute difference and accumulate (128-bit)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabaqU8)
 */
public fun vabaqU8(a: Uint8x16T, b: Uint8x16T, c: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = vabdqU8(b, c); _simdArg0 }

/**
 * Absolute difference between the arguments of Floating
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdF16)
 */
public fun vabdF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun vabdF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return vabdF16(a, b)
}

/**
 * Absolute difference between the arguments of Floating
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdqF16)
 */
public fun vabdqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun vabdqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return vabdqF16(a, b)
}

/**
 * Absolute difference between the arguments of Floating
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdF32)
 */
public fun vabdF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun vabdF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return vabdF32(a, b)
}

/**
 * Absolute difference between the arguments of Floating
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdqF32)
 */
public fun vabdqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun vabdqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return vabdqF32(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdS8)
 */
public fun vabdS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vabdS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vabdS8(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdqS8)
 */
public fun vabdqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    fun vabdqS8(a1: Int8x16T, b1: Int8x16T): Int8x16T = Int8x16T()
    return vabdqS8(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdS16)
 */
public fun vabdS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vabdS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vabdS16(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdqS16)
 */
public fun vabdqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun vabdqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return vabdqS16(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdS32)
 */
public fun vabdS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vabdS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vabdS32(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdqS32)
 */
public fun vabdqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun vabdqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return vabdqS32(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdU8)
 */
public fun vabdU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T {
    fun vabdU8(a1: Uint8x8T, b1: Uint8x8T): Uint8x8T = Uint8x8T()
    return vabdU8(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdqU8)
 */
public fun vabdqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T {
    fun vabdqU8(a1: Uint8x16T, b1: Uint8x16T): Uint8x16T = Uint8x16T()
    return vabdqU8(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdU16)
 */
public fun vabdU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T {
    fun vabdU16(a1: Uint16x4T, b1: Uint16x4T): Uint16x4T = Uint16x4T()
    return vabdU16(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdqU16)
 */
public fun vabdqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T {
    fun vabdqU16(a1: Uint16x8T, b1: Uint16x8T): Uint16x8T = Uint16x8T()
    return vabdqU16(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdU32)
 */
public fun vabdU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T {
    fun vabdU32(a1: Uint32x2T, b1: Uint32x2T): Uint32x2T = Uint32x2T()
    return vabdU32(a, b)
}

/**
 * Absolute difference between the arguments
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdqU32)
 */
public fun vabdqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T {
    fun vabdqU32(a1: Uint32x4T, b1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vabdqU32(a, b)
}

/**
 * Signed Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlS8)
 */
public fun vabdlS8(a: Int8x8T, b: Int8x8T): Int16x8T =
    run {
        val c: Uint8x8T = run { val _simdArg0 = vabdS8(a, b); Uint8x8T() }
        run { val _simdArg0 = c; _simdArg0 }
    }

/**
 * Signed Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlS16)
 */
public fun vabdlS16(a: Int16x4T, b: Int16x4T): Int32x4T =
    run {
        val c: Uint16x4T = run { val _simdArg0 = vabdS16(a, b); Uint16x4T() }
        run { val _simdArg0 = c; _simdArg0 }
    }

/**
 * Signed Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlS32)
 */
public fun vabdlS32(a: Int32x2T, b: Int32x2T): Int64x2T =
    run {
        val c: Uint32x2T = run { val _simdArg0 = vabdS32(a, b); Uint32x2T() }
        run { val _simdArg0 = c; _simdArg0 }
    }

/**
 * Unsigned Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlU8)
 */
public fun vabdlU8(a: Uint8x8T, b: Uint8x8T): Uint16x8T = run { val _simdArg0 = vabdU8(a, b); _simdArg0 }

/**
 * Unsigned Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlU16)
 */
public fun vabdlU16(a: Uint16x4T, b: Uint16x4T): Uint32x4T = run { val _simdArg0 = vabdU16(a, b); _simdArg0 }

/**
 * Unsigned Absolute difference Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabdlU32)
 */
public fun vabdlU32(a: Uint32x2T, b: Uint32x2T): Uint64x2T = run { val _simdArg0 = vabdU32(a, b); _simdArg0 }

/**
 * Floating-point absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsF16)
 */
public fun vabsF16(a: Float16x4T): Float16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Floating-point absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsqF16)
 */
public fun vabsqF16(a: Float16x8T): Float16x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Floating-point absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsF32)
 */
public fun vabsF32(a: Float32x2T): Float32x2T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Floating-point absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsqF32)
 */
public fun vabsqF32(a: Float32x4T): Float32x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Absolute value (wrapping).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsS8)
 */
public fun vabsS8(a: Int8x8T): Int8x8T =
    run {
        val neg: Int8x8T = run { val _simdArg0 = a; _simdArg0 }
        val mask: Int8x8T = run { val _simdArg0 = a; val _simdArg1 = neg; Int8x8T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = neg; _simdArg1 }
    }

/**
 * Absolute value (wrapping).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsqS8)
 */
public fun vabsqS8(a: Int8x16T): Int8x16T =
    run {
        val neg: Int8x16T = run { val _simdArg0 = a; _simdArg0 }
        val mask: Int8x16T = run { val _simdArg0 = a; val _simdArg1 = neg; Int8x16T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = neg; _simdArg1 }
    }

/**
 * Absolute value (wrapping).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsS16)
 */
public fun vabsS16(a: Int16x4T): Int16x4T =
    run {
        val neg: Int16x4T = run { val _simdArg0 = a; _simdArg0 }
        val mask: Int16x4T = run { val _simdArg0 = a; val _simdArg1 = neg; Int16x4T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = neg; _simdArg1 }
    }

/**
 * Absolute value (wrapping).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsqS16)
 */
public fun vabsqS16(a: Int16x8T): Int16x8T =
    run {
        val neg: Int16x8T = run { val _simdArg0 = a; _simdArg0 }
        val mask: Int16x8T = run { val _simdArg0 = a; val _simdArg1 = neg; Int16x8T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = neg; _simdArg1 }
    }

/**
 * Absolute value (wrapping).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsS32)
 */
public fun vabsS32(a: Int32x2T): Int32x2T =
    run {
        val neg: Int32x2T = run { val _simdArg0 = a; _simdArg0 }
        val mask: Int32x2T = run { val _simdArg0 = a; val _simdArg1 = neg; Int32x2T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = neg; _simdArg1 }
    }

/**
 * Absolute value (wrapping).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabsqS32)
 */
public fun vabsqS32(a: Int32x4T): Int32x4T =
    run {
        val neg: Int32x4T = run { val _simdArg0 = a; _simdArg0 }
        val mask: Int32x4T = run { val _simdArg0 = a; val _simdArg1 = neg; Int32x4T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = neg; _simdArg1 }
    }

/**
 * Floating-point absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vabshF16)
 */
public fun vabshF16(a: F16): F16 = vgetLaneF16(vabsF16(vdupNF16(a)), F16.fromFloat(0.0f))

/**
 * Floating-point Add (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddF16)
 */
public fun vaddF16(a: Float16x4T, b: Float16x4T): Float16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point Add (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqF16)
 */
public fun vaddqF16(a: Float16x8T, b: Float16x8T): Float16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddF32)
 */
public fun vaddF32(a: Float32x2T, b: Float32x2T): Float32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddS16)
 */
public fun vaddS16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddS32)
 */
public fun vaddS32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddS8)
 */
public fun vaddS8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddU16)
 */
public fun vaddU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddU32)
 */
public fun vaddU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddU8)
 */
public fun vaddU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqF32)
 */
public fun vaddqF32(a: Float32x4T, b: Float32x4T): Float32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqS16)
 */
public fun vaddqS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqS32)
 */
public fun vaddqS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqS64)
 */
public fun vaddqS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqS8)
 */
public fun vaddqS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqU16)
 */
public fun vaddqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqU32)
 */
public fun vaddqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqU64)
 */
public fun vaddqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector add.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqU8)
 */
public fun vaddqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Bitwise exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddP8)
 */
public fun vaddP8(a: Poly8x8T, b: Poly8x8T): Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Bitwise exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqP8)
 */
public fun vaddqP8(a: Poly8x16T, b: Poly8x16T): Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Bitwise exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddP16)
 */
public fun vaddP16(a: Poly16x4T, b: Poly16x4T): Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Bitwise exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqP16)
 */
public fun vaddqP16(a: Poly16x8T, b: Poly16x8T): Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Bitwise exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddP64)
 */
public fun vaddP64(a: Poly64x1T, b: Poly64x1T): Poly64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Bitwise exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqP64)
 */
public fun vaddqP64(a: Poly64x2T, b: Poly64x2T): Poly64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhF16)
 */
public fun vaddhF16(a: F16, b: F16): F16 = a + b

/**
 * Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnHighS16)
 */
public fun vaddhnHighS16(r: Int8x8T, a: Int16x8T, b: Int16x8T): Int8x16T =
    run {
        val x = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = int16x8T.splat(8); _simdArg0 }; _simdArg0 }
        vcombineS8(r, x)
    }

/**
 * Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnHighS32)
 */
public fun vaddhnHighS32(r: Int16x4T, a: Int32x4T, b: Int32x4T): Int16x8T =
    run {
        val x = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = int32x4T.splat(16); _simdArg0 }; _simdArg0 }
        vcombineS16(r, x)
    }

/**
 * Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnHighS64)
 */
public fun vaddhnHighS64(r: Int32x2T, a: Int64x2T, b: Int64x2T): Int32x4T =
    run {
        val x = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = int64x2T.splat(32); _simdArg0 }; _simdArg0 }
        vcombineS32(r, x)
    }

/**
 * Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnHighU16)
 */
public fun vaddhnHighU16(r: Uint8x8T, a: Uint16x8T, b: Uint16x8T): Uint8x16T =
    run {
        val x = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = uint16x8T.splat(8); _simdArg0 }; _simdArg0 }
        vcombineU8(r, x)
    }

/**
 * Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnHighU32)
 */
public fun vaddhnHighU32(r: Uint16x4T, a: Uint32x4T, b: Uint32x4T): Uint16x8T =
    run {
        val x = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = uint32x4T.splat(16); _simdArg0 }; _simdArg0 }
        vcombineU16(r, x)
    }

/**
 * Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnHighU64)
 */
public fun vaddhnHighU64(r: Uint32x2T, a: Uint64x2T, b: Uint64x2T): Uint32x4T =
    run {
        val x = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = uint64x2T.splat(32); _simdArg0 }; _simdArg0 }
        vcombineU32(r, x)
    }

/**
 * Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnS16)
 */
public fun vaddhnS16(a: Int16x8T, b: Int16x8T): Int8x8T = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = int16x8T.splat(8); _simdArg0 }; _simdArg0 }

/**
 * Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnS32)
 */
public fun vaddhnS32(a: Int32x4T, b: Int32x4T): Int16x4T = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = int32x4T.splat(16); _simdArg0 }; _simdArg0 }

/**
 * Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnS64)
 */
public fun vaddhnS64(a: Int64x2T, b: Int64x2T): Int32x2T = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = int64x2T.splat(32); _simdArg0 }; _simdArg0 }

/**
 * Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnU16)
 */
public fun vaddhnU16(a: Uint16x8T, b: Uint16x8T): Uint8x8T = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = uint16x8T.splat(8); _simdArg0 }; _simdArg0 }

/**
 * Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnU32)
 */
public fun vaddhnU32(a: Uint32x4T, b: Uint32x4T): Uint16x4T = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = uint32x4T.splat(16); _simdArg0 }; _simdArg0 }

/**
 * Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddhnU64)
 */
public fun vaddhnU64(a: Uint64x2T, b: Uint64x2T): Uint32x2T = run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = uint64x2T.splat(32); _simdArg0 }; _simdArg0 }

/**
 * Signed Add Long (vector, high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlHighS16)
 */
public fun vaddlHighS16(a: Int16x8T, b: Int16x8T): Int32x4T {
    val a1: Int16x4T = vgetHighS16(a)
    val b1: Int16x4T = vgetHighS16(b)
    return run {
        val a2: Int32x4T = run { val _simdArg0 = a1; Int32x4T() }
        val b2: Int32x4T = run { val _simdArg0 = b1; Int32x4T() }
        run { val _simdArg0 = a2; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Signed Add Long (vector, high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlHighS32)
 */
public fun vaddlHighS32(a: Int32x4T, b: Int32x4T): Int64x2T {
    val a1: Int32x2T = vgetHighS32(a)
    val b1: Int32x2T = vgetHighS32(b)
    return run {
        val a2: Int64x2T = run { val _simdArg0 = a1; Int64x2T() }
        val b2: Int64x2T = run { val _simdArg0 = b1; Int64x2T() }
        run { val _simdArg0 = a2; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Signed Add Long (vector, high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlHighS8)
 */
public fun vaddlHighS8(a: Int8x16T, b: Int8x16T): Int16x8T {
    val a1: Int8x8T = vgetHighS8(a)
    val b1: Int8x8T = vgetHighS8(b)
    return run {
        val a2: Int16x8T = run { val _simdArg0 = a1; Int16x8T() }
        val b2: Int16x8T = run { val _simdArg0 = b1; Int16x8T() }
        run { val _simdArg0 = a2; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Signed Add Long (vector, high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlHighU16)
 */
public fun vaddlHighU16(a: Uint16x8T, b: Uint16x8T): Uint32x4T {
    val a1: Uint16x4T = vgetHighU16(a)
    val b1: Uint16x4T = vgetHighU16(b)
    return run {
        val a2: Uint32x4T = run { val _simdArg0 = a1; Uint32x4T() }
        val b2: Uint32x4T = run { val _simdArg0 = b1; Uint32x4T() }
        run { val _simdArg0 = a2; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Signed Add Long (vector, high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlHighU32)
 */
public fun vaddlHighU32(a: Uint32x4T, b: Uint32x4T): Uint64x2T {
    val a1: Uint32x2T = vgetHighU32(a)
    val b1: Uint32x2T = vgetHighU32(b)
    return run {
        val a2: Uint64x2T = run { val _simdArg0 = a1; Uint64x2T() }
        val b2: Uint64x2T = run { val _simdArg0 = b1; Uint64x2T() }
        run { val _simdArg0 = a2; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Signed Add Long (vector, high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlHighU8)
 */
public fun vaddlHighU8(a: Uint8x16T, b: Uint8x16T): Uint16x8T {
    val a1: Uint8x8T = vgetHighU8(a)
    val b1: Uint8x8T = vgetHighU8(b)
    return run {
        val a2: Uint16x8T = run { val _simdArg0 = a1; Uint16x8T() }
        val b2: Uint16x8T = run { val _simdArg0 = b1; Uint16x8T() }
        run { val _simdArg0 = a2; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Add Long (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlS16)
 */
public fun vaddlS16(a: Int16x4T, b: Int16x4T): Int32x4T =
    run {
        val a1: Int32x4T = run { val _simdArg0 = a; Int32x4T() }
        val b1: Int32x4T = run { val _simdArg0 = b; Int32x4T() }
        run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Add Long (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlS32)
 */
public fun vaddlS32(a: Int32x2T, b: Int32x2T): Int64x2T =
    run {
        val a1: Int64x2T = run { val _simdArg0 = a; Int64x2T() }
        val b1: Int64x2T = run { val _simdArg0 = b; Int64x2T() }
        run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Add Long (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlS8)
 */
public fun vaddlS8(a: Int8x8T, b: Int8x8T): Int16x8T =
    run {
        val a1: Int16x8T = run { val _simdArg0 = a; Int16x8T() }
        val b1: Int16x8T = run { val _simdArg0 = b; Int16x8T() }
        run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Add Long (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlU16)
 */
public fun vaddlU16(a: Uint16x4T, b: Uint16x4T): Uint32x4T =
    run {
        val a1: Uint32x4T = run { val _simdArg0 = a; Uint32x4T() }
        val b1: Uint32x4T = run { val _simdArg0 = b; Uint32x4T() }
        run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Add Long (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlU32)
 */
public fun vaddlU32(a: Uint32x2T, b: Uint32x2T): Uint64x2T =
    run {
        val a1: Uint64x2T = run { val _simdArg0 = a; Uint64x2T() }
        val b1: Uint64x2T = run { val _simdArg0 = b; Uint64x2T() }
        run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Add Long (vector).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddlU8)
 */
public fun vaddlU8(a: Uint8x8T, b: Uint8x8T): Uint16x8T =
    run {
        val a1: Uint16x8T = run { val _simdArg0 = a; Uint16x8T() }
        val b1: Uint16x8T = run { val _simdArg0 = b; Uint16x8T() }
        run { val _simdArg0 = a1; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Bitwise exclusive OR
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddqP128)
 */
public fun vaddqP128(a: P128, b: P128): P128 = a xor b

/**
 * Add Wide (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwHighS16)
 */
public fun vaddwHighS16(a: Int32x4T, b: Int16x8T): Int32x4T {
    val b1 = vgetHighS16(b)
    return run {
        val b2: Int32x4T = run { val _simdArg0 = b1; Int32x4T() }
        run { val _simdArg0 = a; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Add Wide (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwHighS32)
 */
public fun vaddwHighS32(a: Int64x2T, b: Int32x4T): Int64x2T {
    val b1 = vgetHighS32(b)
    return run {
        val b2: Int64x2T = run { val _simdArg0 = b1; Int64x2T() }
        run { val _simdArg0 = a; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Add Wide (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwHighS8)
 */
public fun vaddwHighS8(a: Int16x8T, b: Int8x16T): Int16x8T {
    val b1 = vgetHighS8(b)
    return run {
        val b2: Int16x8T = run { val _simdArg0 = b1; Int16x8T() }
        run { val _simdArg0 = a; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Add Wide (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwHighU16)
 */
public fun vaddwHighU16(a: Uint32x4T, b: Uint16x8T): Uint32x4T {
    val b1 = vgetHighU16(b)
    return run {
        val b2: Uint32x4T = run { val _simdArg0 = b1; Uint32x4T() }
        run { val _simdArg0 = a; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Add Wide (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwHighU32)
 */
public fun vaddwHighU32(a: Uint64x2T, b: Uint32x4T): Uint64x2T {
    val b1 = vgetHighU32(b)
    return run {
        val b2: Uint64x2T = run { val _simdArg0 = b1; Uint64x2T() }
        run { val _simdArg0 = a; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Add Wide (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwHighU8)
 */
public fun vaddwHighU8(a: Uint16x8T, b: Uint8x16T): Uint16x8T {
    val b1 = vgetHighU8(b)
    return run {
        val b2: Uint16x8T = run { val _simdArg0 = b1; Uint16x8T() }
        run { val _simdArg0 = a; val _simdArg1 = b2; _simdArg0 }
    }
}

/**
 * Add Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwS16)
 */
public fun vaddwS16(a: Int32x4T, b: Int16x4T): Int32x4T =
    run {
        val b1: Int32x4T = run { val _simdArg0 = b; Int32x4T() }
        run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Add Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwS32)
 */
public fun vaddwS32(a: Int64x2T, b: Int32x2T): Int64x2T =
    run {
        val b1: Int64x2T = run { val _simdArg0 = b; Int64x2T() }
        run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Add Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwS8)
 */
public fun vaddwS8(a: Int16x8T, b: Int8x8T): Int16x8T =
    run {
        val b1: Int16x8T = run { val _simdArg0 = b; Int16x8T() }
        run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Add Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwU16)
 */
public fun vaddwU16(a: Uint32x4T, b: Uint16x4T): Uint32x4T =
    run {
        val b1: Uint32x4T = run { val _simdArg0 = b; Uint32x4T() }
        run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Add Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwU32)
 */
public fun vaddwU32(a: Uint64x2T, b: Uint32x2T): Uint64x2T =
    run {
        val b1: Uint64x2T = run { val _simdArg0 = b; Uint64x2T() }
        run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * Add Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaddwU8)
 */
public fun vaddwU8(a: Uint16x8T, b: Uint8x8T): Uint16x8T =
    run {
        val b1: Uint16x8T = run { val _simdArg0 = b; Uint16x8T() }
        run { val _simdArg0 = a; val _simdArg1 = b1; _simdArg0 }
    }

/**
 * AES single round encryption.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaesdqU8)
 */
public fun vaesdqU8(data: Uint8x16T, key: Uint8x16T): Uint8x16T {
    fun vaesdqU8(data1: Uint8x16T, key1: Uint8x16T): Uint8x16T = Uint8x16T()
    return vaesdqU8(data, key)
}

/**
 * AES single round encryption.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaeseqU8)
 */
public fun vaeseqU8(data: Uint8x16T, key: Uint8x16T): Uint8x16T {
    fun vaeseqU8(data1: Uint8x16T, key1: Uint8x16T): Uint8x16T = Uint8x16T()
    return vaeseqU8(data, key)
}

/**
 * AES inverse mix columns.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaesimcqU8)
 */
public fun vaesimcqU8(data: Uint8x16T): Uint8x16T {
    fun vaesimcqU8(data1: Uint8x16T): Uint8x16T = Uint8x16T()
    return vaesimcqU8(data)
}

/**
 * AES mix columns.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vaesmcqU8)
 */
public fun vaesmcqU8(data: Uint8x16T): Uint8x16T {
    fun vaesmcqU8(data1: Uint8x16T): Uint8x16T = Uint8x16T()
    return vaesmcqU8(data)
}

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandS8)
 */
public fun vandS8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandqS8)
 */
public fun vandqS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandS16)
 */
public fun vandS16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandqS16)
 */
public fun vandqS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandS32)
 */
public fun vandS32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandqS32)
 */
public fun vandqS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandS64)
 */
public fun vandS64(a: Int64x1T, b: Int64x1T): Int64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandqS64)
 */
public fun vandqS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandU8)
 */
public fun vandU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandqU8)
 */
public fun vandqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandU16)
 */
public fun vandU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandqU16)
 */
public fun vandqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandU32)
 */
public fun vandU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandqU32)
 */
public fun vandqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandU64)
 */
public fun vandU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise and
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vandqU64)
 */
public fun vandqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicS16)
 */
public fun vbicS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    val c = int16x4T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicS32)
 */
public fun vbicS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    val c = int32x2T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicS64)
 */
public fun vbicS64(a: Int64x1T, b: Int64x1T): Int64x1T {
    val c = int64x1T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicS8)
 */
public fun vbicS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    val c = int8x8T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicqS16)
 */
public fun vbicqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    val c = int16x8T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicqS32)
 */
public fun vbicqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    val c = int32x4T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicqS64)
 */
public fun vbicqS64(a: Int64x2T, b: Int64x2T): Int64x2T {
    val c = int64x2T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicqS8)
 */
public fun vbicqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    val c = int8x16T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicU16)
 */
public fun vbicU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T {
    val c = int16x4T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicU32)
 */
public fun vbicU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T {
    val c = int32x2T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicU64)
 */
public fun vbicU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T {
    val c = int64x1T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicU8)
 */
public fun vbicU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T {
    val c = int8x8T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicqU16)
 */
public fun vbicqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T {
    val c = int16x8T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicqU32)
 */
public fun vbicqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T {
    val c = int32x4T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicqU64)
 */
public fun vbicqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T {
    val c = int64x2T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise bit clear.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbicqU8)
 */
public fun vbicqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T {
    val c = int8x16T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslF16)
 */
public fun vbslF16(a: Uint16x4T, b: Float16x4T, c: Float16x4T): Float16x4T {
    val not = int16x4T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqF16)
 */
public fun vbslqF16(a: Uint16x8T, b: Float16x8T, c: Float16x8T): Float16x8T {
    val not = int16x8T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslF32)
 */
public fun vbslF32(a: Uint32x2T, b: Float32x2T, c: Float32x2T): Float32x2T {
    val not = int32x2T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslP16)
 */
public fun vbslP16(a: Uint16x4T, b: Poly16x4T, c: Poly16x4T): Poly16x4T {
    val not = int16x4T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslP8)
 */
public fun vbslP8(a: Uint8x8T, b: Poly8x8T, c: Poly8x8T): Poly8x8T {
    val not = int8x8T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslS16)
 */
public fun vbslS16(a: Uint16x4T, b: Int16x4T, c: Int16x4T): Int16x4T {
    val not = int16x4T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslS32)
 */
public fun vbslS32(a: Uint32x2T, b: Int32x2T, c: Int32x2T): Int32x2T {
    val not = int32x2T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslS64)
 */
public fun vbslS64(a: Uint64x1T, b: Int64x1T, c: Int64x1T): Int64x1T {
    val not = int64x1T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslS8)
 */
public fun vbslS8(a: Uint8x8T, b: Int8x8T, c: Int8x8T): Int8x8T {
    val not = int8x8T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqF32)
 */
public fun vbslqF32(a: Uint32x4T, b: Float32x4T, c: Float32x4T): Float32x4T {
    val not = int32x4T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqP16)
 */
public fun vbslqP16(a: Uint16x8T, b: Poly16x8T, c: Poly16x8T): Poly16x8T {
    val not = int16x8T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqP8)
 */
public fun vbslqP8(a: Uint8x16T, b: Poly8x16T, c: Poly8x16T): Poly8x16T {
    val not = int8x16T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqS16)
 */
public fun vbslqS16(a: Uint16x8T, b: Int16x8T, c: Int16x8T): Int16x8T {
    val not = int16x8T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqS32)
 */
public fun vbslqS32(a: Uint32x4T, b: Int32x4T, c: Int32x4T): Int32x4T {
    val not = int32x4T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqS64)
 */
public fun vbslqS64(a: Uint64x2T, b: Int64x2T, c: Int64x2T): Int64x2T {
    val not = int64x2T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqS8)
 */
public fun vbslqS8(a: Uint8x16T, b: Int8x16T, c: Int8x16T): Int8x16T {
    val not = int8x16T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslU16)
 */
public fun vbslU16(a: Uint16x4T, b: Uint16x4T, c: Uint16x4T): Uint16x4T {
    val not = int16x4T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = c; _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslU32)
 */
public fun vbslU32(a: Uint32x2T, b: Uint32x2T, c: Uint32x2T): Uint32x2T {
    val not = int32x2T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = c; _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslU64)
 */
public fun vbslU64(a: Uint64x1T, b: Uint64x1T, c: Uint64x1T): Uint64x1T {
    val not = int64x1T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = c; _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslU8)
 */
public fun vbslU8(a: Uint8x8T, b: Uint8x8T, c: Uint8x8T): Uint8x8T {
    val not = int8x8T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = c; _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqU16)
 */
public fun vbslqU16(a: Uint16x8T, b: Uint16x8T, c: Uint16x8T): Uint16x8T {
    val not = int16x8T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = c; _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqU32)
 */
public fun vbslqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T): Uint32x4T {
    val not = int32x4T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = c; _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqU64)
 */
public fun vbslqU64(a: Uint64x2T, b: Uint64x2T, c: Uint64x2T): Uint64x2T {
    val not = int64x2T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = c; _simdArg0 }; _simdArg0 })
}

/**
 * Bitwise Select.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vbslqU8)
 */
public fun vbslqU8(a: Uint8x16T, b: Uint8x16T, c: Uint8x16T): Uint8x16T {
    val not = int8x16T.splat(-1)
    return transmute(run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = transmute(not); _simdArg0 }; val _simdArg1 = c; _simdArg0 }; _simdArg0 })
}

/**
 * Floating-point absolute compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcageF16)
 */
public fun vcageF16(a: Float16x4T, b: Float16x4T): Uint16x4T {
    fun vcageF16(a1: Float16x4T, b1: Float16x4T): Uint16x4T = Uint16x4T()
    return vcageF16(a, b)
}

/**
 * Floating-point absolute compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcageqF16)
 */
public fun vcageqF16(a: Float16x8T, b: Float16x8T): Uint16x8T {
    fun vcageqF16(a1: Float16x8T, b1: Float16x8T): Uint16x8T = Uint16x8T()
    return vcageqF16(a, b)
}

/**
 * Floating-point absolute compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcageF32)
 */
public fun vcageF32(a: Float32x2T, b: Float32x2T): Uint32x2T {
    fun vcageF32(a1: Float32x2T, b1: Float32x2T): Uint32x2T = Uint32x2T()
    return vcageF32(a, b)
}

/**
 * Floating-point absolute compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcageqF32)
 */
public fun vcageqF32(a: Float32x4T, b: Float32x4T): Uint32x4T {
    fun vcageqF32(a1: Float32x4T, b1: Float32x4T): Uint32x4T = Uint32x4T()
    return vcageqF32(a, b)
}

/**
 * Floating-point absolute compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagtF16)
 */
public fun vcagtF16(a: Float16x4T, b: Float16x4T): Uint16x4T {
    fun vcagtF16(a1: Float16x4T, b1: Float16x4T): Uint16x4T = Uint16x4T()
    return vcagtF16(a, b)
}

/**
 * Floating-point absolute compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagtqF16)
 */
public fun vcagtqF16(a: Float16x8T, b: Float16x8T): Uint16x8T {
    fun vcagtqF16(a1: Float16x8T, b1: Float16x8T): Uint16x8T = Uint16x8T()
    return vcagtqF16(a, b)
}

/**
 * Floating-point absolute compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagtF32)
 */
public fun vcagtF32(a: Float32x2T, b: Float32x2T): Uint32x2T {
    fun vcagtF32(a1: Float32x2T, b1: Float32x2T): Uint32x2T = Uint32x2T()
    return vcagtF32(a, b)
}

/**
 * Floating-point absolute compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcagtqF32)
 */
public fun vcagtqF32(a: Float32x4T, b: Float32x4T): Uint32x4T {
    fun vcagtqF32(a1: Float32x4T, b1: Float32x4T): Uint32x4T = Uint32x4T()
    return vcagtqF32(a, b)
}

/**
 * Floating-point absolute compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaleF16)
 */
public fun vcaleF16(a: Float16x4T, b: Float16x4T): Uint16x4T = vcageF16(b, a)

/**
 * Floating-point absolute compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaleqF16)
 */
public fun vcaleqF16(a: Float16x8T, b: Float16x8T): Uint16x8T = vcageqF16(b, a)

/**
 * Floating-point absolute compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaleF32)
 */
public fun vcaleF32(a: Float32x2T, b: Float32x2T): Uint32x2T = vcageF32(b, a)

/**
 * Floating-point absolute compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaleqF32)
 */
public fun vcaleqF32(a: Float32x4T, b: Float32x4T): Uint32x4T = vcageqF32(b, a)

/**
 * Floating-point absolute compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaltF16)
 */
public fun vcaltF16(a: Float16x4T, b: Float16x4T): Uint16x4T = vcagtF16(b, a)

/**
 * Floating-point absolute compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaltqF16)
 */
public fun vcaltqF16(a: Float16x8T, b: Float16x8T): Uint16x8T = vcagtqF16(b, a)

/**
 * Floating-point absolute compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaltF32)
 */
public fun vcaltF32(a: Float32x2T, b: Float32x2T): Uint32x2T = vcagtF32(b, a)

/**
 * Floating-point absolute compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcaltqF32)
 */
public fun vcaltqF32(a: Float32x4T, b: Float32x4T): Uint32x4T = vcagtqF32(b, a)

/**
 * Floating-point compare equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqF16)
 */
public fun vceqF16(a: Float16x4T, b: Float16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqF16)
 */
public fun vceqqF16(a: Float16x8T, b: Float16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqF32)
 */
public fun vceqF32(a: Float32x2T, b: Float32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqF32)
 */
public fun vceqqF32(a: Float32x4T, b: Float32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqS8)
 */
public fun vceqS8(a: Int8x8T, b: Int8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqS8)
 */
public fun vceqqS8(a: Int8x16T, b: Int8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqS16)
 */
public fun vceqS16(a: Int16x4T, b: Int16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqS16)
 */
public fun vceqqS16(a: Int16x8T, b: Int16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqS32)
 */
public fun vceqS32(a: Int32x2T, b: Int32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqS32)
 */
public fun vceqqS32(a: Int32x4T, b: Int32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqU8)
 */
public fun vceqU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqU8)
 */
public fun vceqqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqU16)
 */
public fun vceqU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqU16)
 */
public fun vceqqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqU32)
 */
public fun vceqU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqU32)
 */
public fun vceqqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqP8)
 */
public fun vceqP8(a: Poly8x8T, b: Poly8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare bitwise Equal (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vceqqP8)
 */
public fun vceqqP8(a: Poly8x16T, b: Poly8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeF16)
 */
public fun vcgeF16(a: Float16x4T, b: Float16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqF16)
 */
public fun vcgeqF16(a: Float16x8T, b: Float16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeF32)
 */
public fun vcgeF32(a: Float32x2T, b: Float32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqF32)
 */
public fun vcgeqF32(a: Float32x4T, b: Float32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeS8)
 */
public fun vcgeS8(a: Int8x8T, b: Int8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqS8)
 */
public fun vcgeqS8(a: Int8x16T, b: Int8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeS16)
 */
public fun vcgeS16(a: Int16x4T, b: Int16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqS16)
 */
public fun vcgeqS16(a: Int16x8T, b: Int16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeS32)
 */
public fun vcgeS32(a: Int32x2T, b: Int32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqS32)
 */
public fun vcgeqS32(a: Int32x4T, b: Int32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeU8)
 */
public fun vcgeU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqU8)
 */
public fun vcgeqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeU16)
 */
public fun vcgeU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqU16)
 */
public fun vcgeqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeU32)
 */
public fun vcgeU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgeqU32)
 */
public fun vcgeqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezF16)
 */
public fun vcgezF16(a: Float16x4T): Uint16x4T {
    val b: F16x4 = f16x4.new(0.0, 0.0, 0.0, 0.0)
    return run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }
}

/**
 * Floating-point compare greater than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgezqF16)
 */
public fun vcgezqF16(a: Float16x8T): Uint16x8T {
    val b: F16x8 = f16x8.new(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    return run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }
}

/**
 * Floating-point compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtF16)
 */
public fun vcgtF16(a: Float16x4T, b: Float16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqF16)
 */
public fun vcgtqF16(a: Float16x8T, b: Float16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtF32)
 */
public fun vcgtF32(a: Float32x2T, b: Float32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqF32)
 */
public fun vcgtqF32(a: Float32x4T, b: Float32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtS8)
 */
public fun vcgtS8(a: Int8x8T, b: Int8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqS8)
 */
public fun vcgtqS8(a: Int8x16T, b: Int8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtS16)
 */
public fun vcgtS16(a: Int16x4T, b: Int16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqS16)
 */
public fun vcgtqS16(a: Int16x8T, b: Int16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtS32)
 */
public fun vcgtS32(a: Int32x2T, b: Int32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqS32)
 */
public fun vcgtqS32(a: Int32x4T, b: Int32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtU8)
 */
public fun vcgtU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqU8)
 */
public fun vcgtqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtU16)
 */
public fun vcgtU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqU16)
 */
public fun vcgtqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtU32)
 */
public fun vcgtU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned greater than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtqU32)
 */
public fun vcgtqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzF16)
 */
public fun vcgtzF16(a: Float16x4T): Uint16x4T {
    val b: F16x4 = f16x4.new(0.0, 0.0, 0.0, 0.0)
    return run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }
}

/**
 * Floating-point compare greater than zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcgtzqF16)
 */
public fun vcgtzqF16(a: Float16x8T): Uint16x8T {
    val b: F16x8 = f16x8.new(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    return run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }
}

/**
 * Floating-point compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleF16)
 */
public fun vcleF16(a: Float16x4T, b: Float16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqF16)
 */
public fun vcleqF16(a: Float16x8T, b: Float16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleF32)
 */
public fun vcleF32(a: Float32x2T, b: Float32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqF32)
 */
public fun vcleqF32(a: Float32x4T, b: Float32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleS8)
 */
public fun vcleS8(a: Int8x8T, b: Int8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqS8)
 */
public fun vcleqS8(a: Int8x16T, b: Int8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleS16)
 */
public fun vcleS16(a: Int16x4T, b: Int16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqS16)
 */
public fun vcleqS16(a: Int16x8T, b: Int16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleS32)
 */
public fun vcleS32(a: Int32x2T, b: Int32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqS32)
 */
public fun vcleqS32(a: Int32x4T, b: Int32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleU8)
 */
public fun vcleU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqU8)
 */
public fun vcleqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleU16)
 */
public fun vcleU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqU16)
 */
public fun vcleqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleU32)
 */
public fun vcleU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than or equal
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcleqU32)
 */
public fun vcleqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezF16)
 */
public fun vclezF16(a: Float16x4T): Uint16x4T {
    val b: F16x4 = f16x4.new(0.0, 0.0, 0.0, 0.0)
    return run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }
}

/**
 * Floating-point compare less than or equal to zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclezqF16)
 */
public fun vclezqF16(a: Float16x8T): Uint16x8T {
    val b: F16x8 = f16x8.new(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    return run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }
}

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsS8)
 */
public fun vclsS8(a: Int8x8T): Int8x8T {
    fun vclsS8(a1: Int8x8T): Int8x8T = Int8x8T()
    return vclsS8(a)
}

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsqS8)
 */
public fun vclsqS8(a: Int8x16T): Int8x16T {
    fun vclsqS8(a1: Int8x16T): Int8x16T = Int8x16T()
    return vclsqS8(a)
}

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsS16)
 */
public fun vclsS16(a: Int16x4T): Int16x4T {
    fun vclsS16(a1: Int16x4T): Int16x4T = Int16x4T()
    return vclsS16(a)
}

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsqS16)
 */
public fun vclsqS16(a: Int16x8T): Int16x8T {
    fun vclsqS16(a1: Int16x8T): Int16x8T = Int16x8T()
    return vclsqS16(a)
}

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsS32)
 */
public fun vclsS32(a: Int32x2T): Int32x2T {
    fun vclsS32(a1: Int32x2T): Int32x2T = Int32x2T()
    return vclsS32(a)
}

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsqS32)
 */
public fun vclsqS32(a: Int32x4T): Int32x4T {
    fun vclsqS32(a1: Int32x4T): Int32x4T = Int32x4T()
    return vclsqS32(a)
}

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsU8)
 */
public fun vclsU8(a: Uint8x8T): Int8x8T = vclsS8(transmute(a))

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsqU8)
 */
public fun vclsqU8(a: Uint8x16T): Int8x16T = vclsqS8(transmute(a))

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsU16)
 */
public fun vclsU16(a: Uint16x4T): Int16x4T = vclsS16(transmute(a))

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsqU16)
 */
public fun vclsqU16(a: Uint16x8T): Int16x8T = vclsqS16(transmute(a))

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsU32)
 */
public fun vclsU32(a: Uint32x2T): Int32x2T = vclsS32(transmute(a))

/**
 * Count leading sign bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclsqU32)
 */
public fun vclsqU32(a: Uint32x4T): Int32x4T = vclsqS32(transmute(a))

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltF16)
 */
public fun vcltF16(a: Float16x4T, b: Float16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqF16)
 */
public fun vcltqF16(a: Float16x8T, b: Float16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltF32)
 */
public fun vcltF32(a: Float32x2T, b: Float32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqF32)
 */
public fun vcltqF32(a: Float32x4T, b: Float32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltS8)
 */
public fun vcltS8(a: Int8x8T, b: Int8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqS8)
 */
public fun vcltqS8(a: Int8x16T, b: Int8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltS16)
 */
public fun vcltS16(a: Int16x4T, b: Int16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqS16)
 */
public fun vcltqS16(a: Int16x8T, b: Int16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltS32)
 */
public fun vcltS32(a: Int32x2T, b: Int32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare signed less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqS32)
 */
public fun vcltqS32(a: Int32x4T, b: Int32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltU8)
 */
public fun vcltU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqU8)
 */
public fun vcltqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltU16)
 */
public fun vcltU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqU16)
 */
public fun vcltqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltU32)
 */
public fun vcltU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Compare unsigned less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltqU32)
 */
public fun vcltqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzF16)
 */
public fun vcltzF16(a: Float16x4T): Uint16x4T {
    val b: F16x4 = f16x4.new(0.0, 0.0, 0.0, 0.0)
    return run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }
}

/**
 * Floating-point compare less than
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcltzqF16)
 */
public fun vcltzqF16(a: Float16x8T): Uint16x8T {
    val b: F16x8 = f16x8.new(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    return run { val _simdArg0 = a; val _simdArg1 = transmute(b); _simdArg0 }
}

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzS8)
 */
public fun vclzS8(a: Int8x8T): Int8x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzqS8)
 */
public fun vclzqS8(a: Int8x16T): Int8x16T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzS16)
 */
public fun vclzS16(a: Int16x4T): Int16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzqS16)
 */
public fun vclzqS16(a: Int16x8T): Int16x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzS32)
 */
public fun vclzS32(a: Int32x2T): Int32x2T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzqS32)
 */
public fun vclzqS32(a: Int32x4T): Int32x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzU16)
 */
public fun vclzU16(a: Uint16x4T): Uint16x4T = transmute(vclzS16(transmute(a)))

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzqU16)
 */
public fun vclzqU16(a: Uint16x8T): Uint16x8T = transmute(vclzqS16(transmute(a)))

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzU32)
 */
public fun vclzU32(a: Uint32x2T): Uint32x2T = transmute(vclzS32(transmute(a)))

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzqU32)
 */
public fun vclzqU32(a: Uint32x4T): Uint32x4T = transmute(vclzqS32(transmute(a)))

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzU8)
 */
public fun vclzU8(a: Uint8x8T): Uint8x8T = transmute(vclzS8(transmute(a)))

/**
 * Count leading zero bits
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vclzqU8)
 */
public fun vclzqU8(a: Uint8x16T): Uint8x16T = transmute(vclzqS8(transmute(a)))

/**
 * Population count per byte.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcntS8)
 */
public fun vcntS8(a: Int8x8T): Int8x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Population count per byte.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcntqS8)
 */
public fun vcntqS8(a: Int8x16T): Int8x16T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Population count per byte.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcntU8)
 */
public fun vcntU8(a: Uint8x8T): Uint8x8T = transmute(vcntS8(transmute(a)))

/**
 * Population count per byte.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcntqU8)
 */
public fun vcntqU8(a: Uint8x16T): Uint8x16T = transmute(vcntqS8(transmute(a)))

/**
 * Population count per byte.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcntP8)
 */
public fun vcntP8(a: Poly8x8T): Poly8x8T = transmute(vcntS8(transmute(a)))

/**
 * Population count per byte.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcntqP8)
 */
public fun vcntqP8(a: Poly8x16T): Poly8x16T = transmute(vcntqS8(transmute(a)))

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineF16)
 */
public fun vcombineF16(a: Float16x4T, b: Float16x4T): Float16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineF32)
 */
public fun vcombineF32(a: Float32x2T, b: Float32x2T): Float32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineS8)
 */
public fun vcombineS8(a: Int8x8T, b: Int8x8T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineS16)
 */
public fun vcombineS16(a: Int16x4T, b: Int16x4T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineS32)
 */
public fun vcombineS32(a: Int32x2T, b: Int32x2T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineS64)
 */
public fun vcombineS64(a: Int64x1T, b: Int64x1T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineU8)
 */
public fun vcombineU8(a: Uint8x8T, b: Uint8x8T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineU16)
 */
public fun vcombineU16(a: Uint16x4T, b: Uint16x4T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineU32)
 */
public fun vcombineU32(a: Uint32x2T, b: Uint32x2T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineU64)
 */
public fun vcombineU64(a: Uint64x1T, b: Uint64x1T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineP8)
 */
public fun vcombineP8(a: Poly8x8T, b: Poly8x8T): Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineP16)
 */
public fun vcombineP16(a: Poly16x4T, b: Poly16x4T): Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }

/**
 * Join two smaller vectors into a single larger vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcombineP64)
 */
public fun vcombineP64(a: Poly64x1T, b: Poly64x1T): Poly64x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateF16)
 */
public fun vcreateF16(a: ULong): Float16x4T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateF32)
 */
public fun vcreateF32(a: ULong): Float32x2T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateS8)
 */
public fun vcreateS8(a: ULong): Int8x8T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateS16)
 */
public fun vcreateS16(a: ULong): Int16x4T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateS32)
 */
public fun vcreateS32(a: ULong): Int32x2T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateS64)
 */
public fun vcreateS64(a: ULong): Int64x1T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateU8)
 */
public fun vcreateU8(a: ULong): Uint8x8T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateU16)
 */
public fun vcreateU16(a: ULong): Uint16x4T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateU32)
 */
public fun vcreateU32(a: ULong): Uint32x2T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateU64)
 */
public fun vcreateU64(a: ULong): Uint64x1T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateP8)
 */
public fun vcreateP8(a: ULong): Poly8x8T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateP16)
 */
public fun vcreateP16(a: ULong): Poly16x4T = transmute(a)

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcreateP64)
 */
public fun vcreateP64(a: ULong): Poly64x1T = transmute(a)

/**
 * Floating-point convert to lower precision narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtF16F32)
 */
public fun vcvtF16F32(a: Float32x4T): Float16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtF16S16)
 */
public fun vcvtF16S16(a: Int16x4T): Float16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqF16S16)
 */
public fun vcvtqF16S16(a: Int16x8T): Float16x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtF16U16)
 */
public fun vcvtF16U16(a: Uint16x4T): Float16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqF16U16)
 */
public fun vcvtqF16U16(a: Uint16x8T): Float16x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Floating-point convert to higher precision long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtF32F16)
 */
public fun vcvtF32F16(a: Float16x4T): Float32x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtF32S32)
 */
public fun vcvtF32S32(a: Int32x2T): Float32x2T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqF32S32)
 */
public fun vcvtqF32S32(a: Int32x4T): Float32x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtF32U32)
 */
public fun vcvtF32U32(a: Uint32x2T): Float32x2T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqF32U32)
 */
public fun vcvtqF32U32(a: Uint32x4T): Float32x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNF16S16)
 */
public fun vcvtNF16S16(a: Int16x4T, n: Int): Float16x4T {
    fun vcvtNF16S16(a1: Int16x4T, n: Int): Float16x4T = Float16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvtNF16S16(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNF16S16)
 */
public fun vcvtqNF16S16(a: Int16x8T, n: Int): Float16x8T {
    fun vcvtqNF16S16(a1: Int16x8T, n: Int): Float16x8T = Float16x8T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvtqNF16S16(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNF16U16)
 */
public fun vcvtNF16U16(a: Uint16x4T, n: Int): Float16x4T {
    fun vcvtNF16U16(a1: Uint16x4T, n: Int): Float16x4T = Float16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvtNF16U16(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNF16U16)
 */
public fun vcvtqNF16U16(a: Uint16x8T, n: Int): Float16x8T {
    fun vcvtqNF16U16(a1: Uint16x8T, n: Int): Float16x8T = Float16x8T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvtqNF16U16(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNF32S32)
 */
public fun vcvtNF32S32(a: Int32x2T, n: Int): Float32x2T {
    fun vcvtNF32S32(a1: Int32x2T, n: Int): Float32x2T = Float32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vcvtNF32S32(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNF32S32)
 */
public fun vcvtqNF32S32(a: Int32x4T, n: Int): Float32x4T {
    fun vcvtqNF32S32(a1: Int32x4T, n: Int): Float32x4T = Float32x4T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vcvtqNF32S32(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNF32U32)
 */
public fun vcvtNF32U32(a: Uint32x2T, n: Int): Float32x2T {
    fun vcvtNF32U32(a1: Uint32x2T, n: Int): Float32x2T = Float32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vcvtNF32U32(a, n)
}

/**
 * Fixed-point convert to floating-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNF32U32)
 */
public fun vcvtqNF32U32(a: Uint32x4T, n: Int): Float32x4T {
    fun vcvtqNF32U32(a1: Uint32x4T, n: Int): Float32x4T = Float32x4T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vcvtqNF32U32(a, n)
}

/**
 * Floating-point convert to signed fixed-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNS16F16)
 */
public fun vcvtNS16F16(a: Float16x4T, n: Int): Int16x4T {
    fun vcvtNS16F16(a1: Float16x4T, n: Int): Int16x4T = Int16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvtNS16F16(a, n)
}

/**
 * Floating-point convert to signed fixed-point
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNS16F16)
 */
public fun vcvtqNS16F16(a: Float16x8T, n: Int): Int16x8T {
    fun vcvtqNS16F16(a1: Float16x8T, n: Int): Int16x8T = Int16x8T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvtqNS16F16(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNS32F32)
 */
public fun vcvtNS32F32(a: Float32x2T, n: Int): Int32x2T {
    fun vcvtNS32F32(a1: Float32x2T, n: Int): Int32x2T = Int32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vcvtNS32F32(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNS32F32)
 */
public fun vcvtqNS32F32(a: Float32x4T, n: Int): Int32x4T {
    fun vcvtqNS32F32(a1: Float32x4T, n: Int): Int32x4T = Int32x4T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vcvtqNS32F32(a, n)
}

/**
 * Fixed-point convert to unsigned fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNU16F16)
 */
public fun vcvtNU16F16(a: Float16x4T, n: Int): Uint16x4T {
    fun vcvtNU16F16(a1: Float16x4T, n: Int): Uint16x4T = Uint16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvtNU16F16(a, n)
}

/**
 * Fixed-point convert to unsigned fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNU16F16)
 */
public fun vcvtqNU16F16(a: Float16x8T, n: Int): Uint16x8T {
    fun vcvtqNU16F16(a1: Float16x8T, n: Int): Uint16x8T = Uint16x8T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vcvtqNU16F16(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtNU32F32)
 */
public fun vcvtNU32F32(a: Float32x2T, n: Int): Uint32x2T {
    fun vcvtNU32F32(a1: Float32x2T, n: Int): Uint32x2T = Uint32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vcvtNU32F32(a, n)
}

/**
 * Floating-point convert to fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqNU32F32)
 */
public fun vcvtqNU32F32(a: Float32x4T, n: Int): Uint32x4T {
    fun vcvtqNU32F32(a1: Float32x4T, n: Int): Uint32x4T = Uint32x4T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vcvtqNU32F32(a, n)
}

/**
 * Floating-point convert to signed fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtS16F16)
 */
public fun vcvtS16F16(a: Float16x4T): Int16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Floating-point convert to signed fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqS16F16)
 */
public fun vcvtqS16F16(a: Float16x8T): Int16x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Floating-point convert to signed fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtS32F32)
 */
public fun vcvtS32F32(a: Float32x2T): Int32x2T {
    fun vcvtS32F32(a1: Float32x2T): Int32x2T = Int32x2T()
    return vcvtS32F32(a)
}

/**
 * Floating-point convert to signed fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqS32F32)
 */
public fun vcvtqS32F32(a: Float32x4T): Int32x4T {
    fun vcvtqS32F32(a1: Float32x4T): Int32x4T = Int32x4T()
    return vcvtqS32F32(a)
}

/**
 * Floating-point convert to unsigned fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtU16F16)
 */
public fun vcvtU16F16(a: Float16x4T): Uint16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Floating-point convert to unsigned fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqU16F16)
 */
public fun vcvtqU16F16(a: Float16x8T): Uint16x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Floating-point convert to unsigned fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtU32F32)
 */
public fun vcvtU32F32(a: Float32x2T): Uint32x2T {
    fun vcvtU32F32(a1: Float32x2T): Uint32x2T = Uint32x2T()
    return vcvtU32F32(a)
}

/**
 * Floating-point convert to unsigned fixed-point, rounding toward zero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vcvtqU32F32)
 */
public fun vcvtqU32F32(a: Float32x4T): Uint32x4T {
    fun vcvtqU32F32(a1: Float32x4T): Uint32x4T = Uint32x4T()
    return vcvtqU32F32(a)
}

/**
 * Dot product arithmetic (indexed)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotLaneS32)
 */
public fun vdotLaneS32(a: Int32x2T, b: Int8x8T, c: Int8x8T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretS32S8(c)
    val c2 = vdupLaneS32(c1, lane)
    return vdotS32(a, b, vreinterpretS8S32(c2))
}

/**
 * Dot product arithmetic (indexed)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotqLaneS32)
 */
public fun vdotqLaneS32(a: Int32x4T, b: Int8x16T, c: Int8x8T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretS32S8(c)
    val c2 = vdupqLaneS32(c1, lane)
    return vdotqS32(a, b, vreinterpretqS8S32(c2))
}

/**
 * Dot product arithmetic (indexed)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotLaneU32)
 */
public fun vdotLaneU32(a: Uint32x2T, b: Uint8x8T, c: Uint8x8T, lane: Int): Uint32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32U8(c)
    val c2 = vdupLaneU32(c1, lane)
    return vdotU32(a, b, vreinterpretU8U32(c2))
}

/**
 * Dot product arithmetic (indexed)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotqLaneU32)
 */
public fun vdotqLaneU32(a: Uint32x4T, b: Uint8x16T, c: Uint8x8T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32U8(c)
    val c2 = vdupqLaneU32(c1, lane)
    return vdotqU32(a, b, vreinterpretqU8U32(c2))
}

/**
 * Dot product arithmetic (indexed)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotLaneqS32)
 */
public fun vdotLaneqS32(a: Int32x2T, b: Int8x8T, c: Int8x16T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqS32S8(c)
    val c2 = vdupLaneqS32(c1, lane)
    return vdotS32(a, b, vreinterpretS8S32(c2))
}

/**
 * Dot product arithmetic (indexed)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotqLaneqS32)
 */
public fun vdotqLaneqS32(a: Int32x4T, b: Int8x16T, c: Int8x16T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqS32S8(c)
    val c2 = vdupqLaneqS32(c1, lane)
    return vdotqS32(a, b, vreinterpretqS8S32(c2))
}

/**
 * Dot product arithmetic (indexed)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotLaneqU32)
 */
public fun vdotLaneqU32(a: Uint32x2T, b: Uint8x8T, c: Uint8x16T, lane: Int): Uint32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32U8(c)
    val c2 = vdupLaneqU32(c1, lane)
    return vdotU32(a, b, vreinterpretU8U32(c2))
}

/**
 * Dot product arithmetic (indexed)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotqLaneqU32)
 */
public fun vdotqLaneqU32(a: Uint32x4T, b: Uint8x16T, c: Uint8x16T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32U8(c)
    val c2 = vdupqLaneqU32(c1, lane)
    return vdotqU32(a, b, vreinterpretqU8U32(c2))
}

/**
 * Dot product arithmetic (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotS32)
 */
public fun vdotS32(a: Int32x2T, b: Int8x8T, c: Int8x8T): Int32x2T {
    fun vdotS32(a1: Int32x2T, b1: Int8x8T, c1: Int8x8T): Int32x2T = Int32x2T()
    return vdotS32(a, b, c)
}

/**
 * Dot product arithmetic (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotqS32)
 */
public fun vdotqS32(a: Int32x4T, b: Int8x16T, c: Int8x16T): Int32x4T {
    fun vdotqS32(a1: Int32x4T, b1: Int8x16T, c1: Int8x16T): Int32x4T = Int32x4T()
    return vdotqS32(a, b, c)
}

/**
 * Dot product arithmetic (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotU32)
 */
public fun vdotU32(a: Uint32x2T, b: Uint8x8T, c: Uint8x8T): Uint32x2T {
    fun vdotU32(a1: Uint32x2T, b1: Uint8x8T, c1: Uint8x8T): Uint32x2T = Uint32x2T()
    return vdotU32(a, b, c)
}

/**
 * Dot product arithmetic (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdotqU32)
 */
public fun vdotqU32(a: Uint32x4T, b: Uint8x16T, c: Uint8x16T): Uint32x4T {
    fun vdotqU32(a1: Uint32x4T, b1: Uint8x16T, c1: Uint8x16T): Uint32x4T = Uint32x4T()
    return vdotqU32(a, b, c)
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneF16)
 */
public fun vdupLaneF16(a: Float16x4T, n: Int): Float16x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(4) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneF16)
 */
public fun vdupqLaneF16(a: Float16x4T, n: Int): Float16x8T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneF32)
 */
public fun vdupLaneF32(a: Float32x2T, n: Int): Float32x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneS32)
 */
public fun vdupLaneS32(a: Int32x2T, n: Int): Int32x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneU32)
 */
public fun vdupLaneU32(a: Uint32x2T, n: Int): Uint32x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneF32)
 */
public fun vdupqLaneF32(a: Float32x2T, n: Int): Float32x4T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt(), n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneS32)
 */
public fun vdupqLaneS32(a: Int32x2T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt(), n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneU32)
 */
public fun vdupqLaneU32(a: Uint32x2T, n: Int): Uint32x4T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt(), n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneP16)
 */
public fun vdupLaneP16(a: Poly16x4T, n: Int): Poly16x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(4) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneS16)
 */
public fun vdupLaneS16(a: Int16x4T, n: Int): Int16x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(4) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneU16)
 */
public fun vdupLaneU16(a: Uint16x4T, n: Int): Uint16x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(4) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneP16)
 */
public fun vdupqLaneP16(a: Poly16x4T, n: Int): Poly16x8T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneS16)
 */
public fun vdupqLaneS16(a: Int16x4T, n: Int): Int16x8T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneU16)
 */
public fun vdupqLaneU16(a: Uint16x4T, n: Int): Uint16x8T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneP8)
 */
public fun vdupLaneP8(a: Poly8x8T, n: Int): Poly8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneS8)
 */
public fun vdupLaneS8(a: Int8x8T, n: Int): Int8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneU8)
 */
public fun vdupLaneU8(a: Uint8x8T, n: Int): Uint8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneP8)
 */
public fun vdupqLaneP8(a: Poly8x8T, n: Int): Poly8x16T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(16) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneS8)
 */
public fun vdupqLaneS8(a: Int8x8T, n: Int): Int8x16T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(16) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneU8)
 */
public fun vdupqLaneU8(a: Uint8x8T, n: Int): Uint8x16T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(16) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneS64)
 */
public fun vdupLaneS64(a: Int64x1T, n: Int): Int64x1T {
    run {
        check(n == 0)
    }
    return a
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneU64)
 */
public fun vdupLaneU64(a: Uint64x1T, n: Int): Uint64x1T {
    run {
        check(n == 0)
    }
    return a
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqF16)
 */
public fun vdupLaneqF16(a: Float16x8T, n: Int): Float16x4T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(4) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqF16)
 */
public fun vdupqLaneqF16(a: Float16x8T, n: Int): Float16x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqF32)
 */
public fun vdupLaneqF32(a: Float32x4T, n: Int): Float32x2T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqS32)
 */
public fun vdupLaneqS32(a: Int32x4T, n: Int): Int32x2T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqU32)
 */
public fun vdupLaneqU32(a: Uint32x4T, n: Int): Uint32x2T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqF32)
 */
public fun vdupqLaneqF32(a: Float32x4T, n: Int): Float32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt(), n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqS32)
 */
public fun vdupqLaneqS32(a: Int32x4T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt(), n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqU32)
 */
public fun vdupqLaneqU32(a: Uint32x4T, n: Int): Uint32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt(), n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqP16)
 */
public fun vdupLaneqP16(a: Poly16x8T, n: Int): Poly16x4T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(4) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqS16)
 */
public fun vdupLaneqS16(a: Int16x8T, n: Int): Int16x4T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(4) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqU16)
 */
public fun vdupLaneqU16(a: Uint16x8T, n: Int): Uint16x4T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(4) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqP16)
 */
public fun vdupqLaneqP16(a: Poly16x8T, n: Int): Poly16x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqS16)
 */
public fun vdupqLaneqS16(a: Int16x8T, n: Int): Int16x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqU16)
 */
public fun vdupqLaneqU16(a: Uint16x8T, n: Int): Uint16x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqP8)
 */
public fun vdupLaneqP8(a: Poly8x16T, n: Int): Poly8x8T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqS8)
 */
public fun vdupLaneqS8(a: Int8x16T, n: Int): Int8x8T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqU8)
 */
public fun vdupLaneqU8(a: Uint8x16T, n: Int): Uint8x8T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(8) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqP8)
 */
public fun vdupqLaneqP8(a: Poly8x16T, n: Int): Poly8x16T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(16) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqS8)
 */
public fun vdupqLaneqS8(a: Int8x16T, n: Int): Int8x16T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(16) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqU8)
 */
public fun vdupqLaneqU8(a: Uint8x16T, n: Int): Uint8x16T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(UIntArray(16) { n.toUInt() }); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqS64)
 */
public fun vdupLaneqS64(a: Int64x2T, n: Int): Int64x1T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return transmute(vgetqLaneS64(a, n))
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupLaneqU64)
 */
public fun vdupLaneqU64(a: Uint64x2T, n: Int): Uint64x1T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return transmute(vgetqLaneU64(a, n))
}

/**
 * Create a new vector with all lanes set to a value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNF16)
 */
public fun vdupNF16(a: F16): Float16x4T = float16x4T.splat(a)

/**
 * Create a new vector with all lanes set to a value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNF16)
 */
public fun vdupqNF16(a: F16): Float16x8T = float16x8T.splat(a)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNF32)
 */
public fun vdupNF32(value: Float): Float32x2T = float32x2T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNP16)
 */
public fun vdupNP16(value: P16): Poly16x4T = poly16x4T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNP8)
 */
public fun vdupNP8(value: P8): Poly8x8T = poly8x8T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNS16)
 */
public fun vdupNS16(value: Short): Int16x4T = int16x4T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNS32)
 */
public fun vdupNS32(value: Int): Int32x2T = int32x2T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNS64)
 */
public fun vdupNS64(value: Long): Int64x1T = int64x1T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNS8)
 */
public fun vdupNS8(value: Byte): Int8x8T = int8x8T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNU16)
 */
public fun vdupNU16(value: UShort): Uint16x4T = uint16x4T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNU32)
 */
public fun vdupNU32(value: UInt): Uint32x2T = uint32x2T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNU64)
 */
public fun vdupNU64(value: ULong): Uint64x1T = uint64x1T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNU8)
 */
public fun vdupNU8(value: UByte): Uint8x8T = uint8x8T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNF32)
 */
public fun vdupqNF32(value: Float): Float32x4T = float32x4T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNP16)
 */
public fun vdupqNP16(value: P16): Poly16x8T = poly16x8T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNP8)
 */
public fun vdupqNP8(value: P8): Poly8x16T = poly8x16T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNS16)
 */
public fun vdupqNS16(value: Short): Int16x8T = int16x8T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNS32)
 */
public fun vdupqNS32(value: Int): Int32x4T = int32x4T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNS64)
 */
public fun vdupqNS64(value: Long): Int64x2T = int64x2T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNS8)
 */
public fun vdupqNS8(value: Byte): Int8x16T = int8x16T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNU16)
 */
public fun vdupqNU16(value: UShort): Uint16x8T = uint16x8T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNU32)
 */
public fun vdupqNU32(value: UInt): Uint32x4T = uint32x4T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNU64)
 */
public fun vdupqNU64(value: ULong): Uint64x2T = uint64x2T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNU8)
 */
public fun vdupqNU8(value: UByte): Uint8x16T = uint8x16T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupNF32Vfp4)
 */
public fun vdupNF32Vfp4(value: Float): Float32x2T = float32x2T.splat(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqNF32Vfp4)
 */
public fun vdupqNF32Vfp4(value: Float): Float32x4T = float32x4T.splat(value)

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneS64)
 */
public fun vdupqLaneS64(a: Int64x1T, n: Int): Int64x2T {
    run {
        check(n == 0)
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneU64)
 */
public fun vdupqLaneU64(a: Uint64x1T, n: Int): Uint64x2T {
    run {
        check(n == 0)
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqS64)
 */
public fun vdupqLaneqS64(a: Int64x2T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Set all vector lanes to the same value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vdupqLaneqU64)
 */
public fun vdupqLaneqU64(a: Uint64x2T, n: Int): Uint64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt())); _simdArg0 }
}

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorS8)
 */
public fun veorS8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorqS8)
 */
public fun veorqS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorS16)
 */
public fun veorS16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorqS16)
 */
public fun veorqS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorS32)
 */
public fun veorS32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorqS32)
 */
public fun veorqS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorS64)
 */
public fun veorS64(a: Int64x1T, b: Int64x1T): Int64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorqS64)
 */
public fun veorqS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorU8)
 */
public fun veorU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorqU8)
 */
public fun veorqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorU16)
 */
public fun veorU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorqU16)
 */
public fun veorqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorU32)
 */
public fun veorU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorqU32)
 */
public fun veorqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorU64)
 */
public fun veorU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise exclusive or (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/veorqU64)
 */
public fun veorqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextF16)
 */
public fun vextF16(a: Float16x4T, b: Float16x4T, n: Int): Float16x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextF32)
 */
public fun vextF32(a: Float32x2T, b: Float32x2T, n: Int): Float32x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextS32)
 */
public fun vextS32(a: Int32x2T, b: Int32x2T, n: Int): Int32x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextU32)
 */
public fun vextU32(a: Uint32x2T, b: Uint32x2T, n: Int): Uint32x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vextS64(a: Int64x1T, b: Int64x1T, n: Int): Int64x1T {
    run {
        check(n == 0)
    }
    return a
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vextU64(a: Uint64x1T, b: Uint64x1T, n: Int): Uint64x1T {
    run {
        check(n == 0)
    }
    return a
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextS8)
 */
public fun vextS8(a: Int8x8T, b: Int8x8T, n: Int): Int8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u, n.toUInt() + 4u, n.toUInt() + 5u, n.toUInt() + 6u, n.toUInt() + 7u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqS16)
 */
public fun vextqS16(a: Int16x8T, b: Int16x8T, n: Int): Int16x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u, n.toUInt() + 4u, n.toUInt() + 5u, n.toUInt() + 6u, n.toUInt() + 7u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextU8)
 */
public fun vextU8(a: Uint8x8T, b: Uint8x8T, n: Int): Uint8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u, n.toUInt() + 4u, n.toUInt() + 5u, n.toUInt() + 6u, n.toUInt() + 7u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqU16)
 */
public fun vextqU16(a: Uint16x8T, b: Uint16x8T, n: Int): Uint16x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u, n.toUInt() + 4u, n.toUInt() + 5u, n.toUInt() + 6u, n.toUInt() + 7u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextP8)
 */
public fun vextP8(a: Poly8x8T, b: Poly8x8T, n: Int): Poly8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u, n.toUInt() + 4u, n.toUInt() + 5u, n.toUInt() + 6u, n.toUInt() + 7u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqP16)
 */
public fun vextqP16(a: Poly16x8T, b: Poly16x8T, n: Int): Poly16x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u, n.toUInt() + 4u, n.toUInt() + 5u, n.toUInt() + 6u, n.toUInt() + 7u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqF16)
 */
public fun vextqF16(a: Float16x8T, b: Float16x8T, n: Int): Float16x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u, n.toUInt() + 4u, n.toUInt() + 5u, n.toUInt() + 6u, n.toUInt() + 7u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqF32)
 */
public fun vextqF32(a: Float32x4T, b: Float32x4T, n: Int): Float32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextS16)
 */
public fun vextS16(a: Int16x4T, b: Int16x4T, n: Int): Int16x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqS32)
 */
public fun vextqS32(a: Int32x4T, b: Int32x4T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextU16)
 */
public fun vextU16(a: Uint16x4T, b: Uint16x4T, n: Int): Uint16x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqU32)
 */
public fun vextqU32(a: Uint32x4T, b: Uint32x4T, n: Int): Uint32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextP16)
 */
public fun vextP16(a: Poly16x4T, b: Poly16x4T, n: Int): Poly16x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqS64)
 */
public fun vextqS64(a: Int64x2T, b: Int64x2T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqU64)
 */
public fun vextqU64(a: Uint64x2T, b: Uint64x2T, n: Int): Uint64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqS8)
 */
public fun vextqS8(a: Int8x16T, b: Int8x16T, n: Int): Int8x16T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u, n.toUInt() + 4u, n.toUInt() + 5u, n.toUInt() + 6u, n.toUInt() + 7u, n.toUInt() + 8u, n.toUInt() + 9u, n.toUInt() + 10u, n.toUInt() + 11u, n.toUInt() + 12u, n.toUInt() + 13u, n.toUInt() + 14u, n.toUInt() + 15u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqU8)
 */
public fun vextqU8(a: Uint8x16T, b: Uint8x16T, n: Int): Uint8x16T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u, n.toUInt() + 4u, n.toUInt() + 5u, n.toUInt() + 6u, n.toUInt() + 7u, n.toUInt() + 8u, n.toUInt() + 9u, n.toUInt() + 10u, n.toUInt() + 11u, n.toUInt() + 12u, n.toUInt() + 13u, n.toUInt() + 14u, n.toUInt() + 15u)); _simdArg0 }
}

/**
 * Extract vector from pair of vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vextqP8)
 */
public fun vextqP8(a: Poly8x16T, b: Poly8x16T, n: Int): Poly8x16T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(uintArrayOf(n.toUInt(), n.toUInt() + 1u, n.toUInt() + 2u, n.toUInt() + 3u, n.toUInt() + 4u, n.toUInt() + 5u, n.toUInt() + 6u, n.toUInt() + 7u, n.toUInt() + 8u, n.toUInt() + 9u, n.toUInt() + 10u, n.toUInt() + 11u, n.toUInt() + 12u, n.toUInt() + 13u, n.toUInt() + 14u, n.toUInt() + 15u)); _simdArg0 }
}

/**
 * Floating-point fused Multiply-Add to accumulator (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaF16)
 */
public fun vfmaF16(a: Float16x4T, b: Float16x4T, c: Float16x4T): Float16x4T = run { val _simdArg0 = b; val _simdArg1 = c; val _simdArg2 = a; _simdArg0 }

/**
 * Floating-point fused Multiply-Add to accumulator (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqF16)
 */
public fun vfmaqF16(a: Float16x8T, b: Float16x8T, c: Float16x8T): Float16x8T = run { val _simdArg0 = b; val _simdArg1 = c; val _simdArg2 = a; _simdArg0 }

/**
 * Floating-point fused Multiply-Add to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaF32)
 */
public fun vfmaF32(a: Float32x2T, b: Float32x2T, c: Float32x2T): Float32x2T = run { val _simdArg0 = b; val _simdArg1 = c; val _simdArg2 = a; _simdArg0 }

/**
 * Floating-point fused Multiply-Add to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqF32)
 */
public fun vfmaqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T): Float32x4T = run { val _simdArg0 = b; val _simdArg1 = c; val _simdArg2 = a; _simdArg0 }

/**
 * Floating-point fused Multiply-Add to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaNF32)
 */
public fun vfmaNF32(a: Float32x2T, b: Float32x2T, c: Float): Float32x2T = vfmaF32(a, b, vdupNF32Vfp4(c))

/**
 * Floating-point fused Multiply-Add to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmaqNF32)
 */
public fun vfmaqNF32(a: Float32x4T, b: Float32x4T, c: Float): Float32x4T = vfmaqF32(a, b, vdupqNF32Vfp4(c))

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsF16)
 */
public fun vfmsF16(a: Float16x4T, b: Float16x4T, c: Float16x4T): Float16x4T =
    run {
        val b1: Float16x4T = run { val _simdArg0 = b; _simdArg0 }
        vfmaF16(a, b1, c)
    }

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqF16)
 */
public fun vfmsqF16(a: Float16x8T, b: Float16x8T, c: Float16x8T): Float16x8T =
    run {
        val b1: Float16x8T = run { val _simdArg0 = b; _simdArg0 }
        vfmaqF16(a, b1, c)
    }

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsF32)
 */
public fun vfmsF32(a: Float32x2T, b: Float32x2T, c: Float32x2T): Float32x2T =
    run {
        val b1: Float32x2T = run { val _simdArg0 = b; _simdArg0 }
        vfmaF32(a, b1, c)
    }

/**
 * Floating-point fused multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqF32)
 */
public fun vfmsqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T): Float32x4T =
    run {
        val b1: Float32x4T = run { val _simdArg0 = b; _simdArg0 }
        vfmaqF32(a, b1, c)
    }

/**
 * Floating-point fused Multiply-subtract to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsNF32)
 */
public fun vfmsNF32(a: Float32x2T, b: Float32x2T, c: Float): Float32x2T = vfmsF32(a, b, vdupNF32Vfp4(c))

/**
 * Floating-point fused Multiply-subtract to accumulator(vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vfmsqNF32)
 */
public fun vfmsqNF32(a: Float32x4T, b: Float32x4T, c: Float): Float32x4T = vfmsqF32(a, b, vdupqNF32Vfp4(c))

/**
 * Duplicate vector element to vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighF16)
 */
public fun vgetHighF16(a: Float16x8T): Float16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(4, 5, 6, 7)); _simdArg0 }

/**
 * Duplicate vector element to vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowF16)
 */
public fun vgetLowF16(a: Float16x8T): Float16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighF32)
 */
public fun vgetHighF32(a: Float32x4T): Float32x2T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(2, 3)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighP16)
 */
public fun vgetHighP16(a: Poly16x8T): Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(4, 5, 6, 7)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighP8)
 */
public fun vgetHighP8(a: Poly8x16T): Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(8, 9, 10, 11, 12, 13, 14, 15)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighS16)
 */
public fun vgetHighS16(a: Int16x8T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(4, 5, 6, 7)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighS32)
 */
public fun vgetHighS32(a: Int32x4T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(2, 3)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighS8)
 */
public fun vgetHighS8(a: Int8x16T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(8, 9, 10, 11, 12, 13, 14, 15)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighU16)
 */
public fun vgetHighU16(a: Uint16x8T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(4, 5, 6, 7)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighU32)
 */
public fun vgetHighU32(a: Uint32x4T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(2, 3)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighU8)
 */
public fun vgetHighU8(a: Uint8x16T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(8, 9, 10, 11, 12, 13, 14, 15)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighP64)
 */
public fun vgetHighP64(a: Poly64x2T): Poly64x1T = transmute(u64x1.new(run { val _simdArg0 = a; val _simdArg1 = 1; _simdArg0 }))

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighS64)
 */
public fun vgetHighS64(a: Int64x2T): Int64x1T = int64x1T(listOf(run { val _simdArg0 = a; val _simdArg1 = 1; _simdArg0 }))

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetHighU64)
 */
public fun vgetHighU64(a: Uint64x2T): Uint64x1T = uint64x1T(listOf(run { val _simdArg0 = a; val _simdArg1 = 1; _simdArg0 }))

/**
 * Duplicate vector element to scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneF16)
 */
public fun vgetLaneF16(a: Float16x4T, lane: Int): F16 {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = lane.toUInt(); _simdArg0 }
}

/**
 * Duplicate vector element to scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneF16)
 */
public fun vgetqLaneF16(a: Float16x8T, lane: Int): F16 {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = lane.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneF32)
 */
public fun vgetLaneF32(v: Float32x2T, imm5: Int): Float {
    run {
        check(0 <= imm5 && imm5 < (1 shl 1)) { "IMM5 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneP16)
 */
public fun vgetLaneP16(v: Poly16x4T, imm5: Int): P16 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 2)) { "IMM5 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneP8)
 */
public fun vgetLaneP8(v: Poly8x8T, imm5: Int): P8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 3)) { "IMM5 doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneS16)
 */
public fun vgetLaneS16(v: Int16x4T, imm5: Int): Short {
    run {
        check(0 <= imm5 && imm5 < (1 shl 2)) { "IMM5 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneS32)
 */
public fun vgetLaneS32(v: Int32x2T, imm5: Int): Int {
    run {
        check(0 <= imm5 && imm5 < (1 shl 1)) { "IMM5 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneS8)
 */
public fun vgetLaneS8(v: Int8x8T, imm5: Int): Byte {
    run {
        check(0 <= imm5 && imm5 < (1 shl 3)) { "IMM5 doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneU16)
 */
public fun vgetLaneU16(v: Uint16x4T, imm5: Int): UShort {
    run {
        check(0 <= imm5 && imm5 < (1 shl 2)) { "IMM5 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneU32)
 */
public fun vgetLaneU32(v: Uint32x2T, imm5: Int): UInt {
    run {
        check(0 <= imm5 && imm5 < (1 shl 1)) { "IMM5 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneU8)
 */
public fun vgetLaneU8(v: Uint8x8T, imm5: Int): UByte {
    run {
        check(0 <= imm5 && imm5 < (1 shl 3)) { "IMM5 doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneF32)
 */
public fun vgetqLaneF32(v: Float32x4T, imm5: Int): Float {
    run {
        check(0 <= imm5 && imm5 < (1 shl 2)) { "IMM5 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneP16)
 */
public fun vgetqLaneP16(v: Poly16x8T, imm5: Int): P16 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 3)) { "IMM5 doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneP64)
 */
public fun vgetqLaneP64(v: Poly64x2T, imm5: Int): P64 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 1)) { "IMM5 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneP8)
 */
public fun vgetqLaneP8(v: Poly8x16T, imm5: Int): P8 {
    run {
        check(0 <= imm5 && imm5 < (1 shl 4)) { "IMM5 doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneS16)
 */
public fun vgetqLaneS16(v: Int16x8T, imm5: Int): Short {
    run {
        check(0 <= imm5 && imm5 < (1 shl 3)) { "IMM5 doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneS32)
 */
public fun vgetqLaneS32(v: Int32x4T, imm5: Int): Int {
    run {
        check(0 <= imm5 && imm5 < (1 shl 2)) { "IMM5 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneS64)
 */
public fun vgetqLaneS64(v: Int64x2T, imm5: Int): Long {
    run {
        check(0 <= imm5 && imm5 < (1 shl 1)) { "IMM5 doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneS8)
 */
public fun vgetqLaneS8(v: Int8x16T, imm5: Int): Byte {
    run {
        check(0 <= imm5 && imm5 < (1 shl 4)) { "IMM5 doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneU16)
 */
public fun vgetqLaneU16(v: Uint16x8T, imm5: Int): UShort {
    run {
        check(0 <= imm5 && imm5 < (1 shl 3)) { "IMM5 doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneU32)
 */
public fun vgetqLaneU32(v: Uint32x4T, imm5: Int): UInt {
    run {
        check(0 <= imm5 && imm5 < (1 shl 2)) { "IMM5 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneU64)
 */
public fun vgetqLaneU64(v: Uint64x2T, imm5: Int): ULong {
    run {
        check(0 <= imm5 && imm5 < (1 shl 2)) { "IMM5 doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetqLaneU8)
 */
public fun vgetqLaneU8(v: Uint8x16T, imm5: Int): UByte {
    run {
        check(0 <= imm5 && imm5 < (1 shl 4)) { "IMM5 doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneP64)
 */
public fun vgetLaneP64(v: Poly64x1T, imm5: Int): P64 {
    run {
        check(imm5 == 0)
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneS64)
 */
public fun vgetLaneS64(v: Int64x1T, imm5: Int): Long {
    run {
        check(imm5 == 0)
    }
    return run { val _simdArg0 = v; val _simdArg1 = imm5.toUInt(); _simdArg0 }
}

/**
 * Move vector element to general-purpose register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLaneU64)
 */
public fun vgetLaneU64(v: Uint64x1T, imm5: Int): ULong {
    run {
        check(imm5 == 0)
    }
    return run { val _simdArg0 = v; val _simdArg1 = 0uL; _simdArg0 }
}

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowF32)
 */
public fun vgetLowF32(a: Float32x4T): Float32x2T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowP16)
 */
public fun vgetLowP16(a: Poly16x8T): Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowP8)
 */
public fun vgetLowP8(a: Poly8x16T): Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowS16)
 */
public fun vgetLowS16(a: Int16x8T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowS32)
 */
public fun vgetLowS32(a: Int32x4T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowS8)
 */
public fun vgetLowS8(a: Int8x16T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowU16)
 */
public fun vgetLowU16(a: Uint16x8T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowU32)
 */
public fun vgetLowU32(a: Uint32x4T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowU8)
 */
public fun vgetLowU8(a: Uint8x16T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(0, 1, 2, 3, 4, 5, 6, 7)); _simdArg0 }

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowP64)
 */
public fun vgetLowP64(a: Poly64x2T): Poly64x1T = transmute(u64x1.new(run { val _simdArg0 = a; val _simdArg1 = 0; _simdArg0 }))

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowS64)
 */
public fun vgetLowS64(a: Int64x2T): Int64x1T = int64x1T(listOf(run { val _simdArg0 = a; val _simdArg1 = 0; _simdArg0 }))

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vgetLowU64)
 */
public fun vgetLowU64(a: Uint64x2T): Uint64x1T = uint64x1T(listOf(run { val _simdArg0 = a; val _simdArg1 = 0; _simdArg0 }))

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddS8)
 */
public fun vhaddS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vhaddS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vhaddS8(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddqS8)
 */
public fun vhaddqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    fun vhaddqS8(a1: Int8x16T, b1: Int8x16T): Int8x16T = Int8x16T()
    return vhaddqS8(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddS16)
 */
public fun vhaddS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vhaddS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vhaddS16(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddqS16)
 */
public fun vhaddqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun vhaddqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return vhaddqS16(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddS32)
 */
public fun vhaddS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vhaddS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vhaddS32(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddqS32)
 */
public fun vhaddqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun vhaddqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return vhaddqS32(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddU8)
 */
public fun vhaddU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T {
    fun vhaddU8(a1: Uint8x8T, b1: Uint8x8T): Uint8x8T = Uint8x8T()
    return vhaddU8(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddqU8)
 */
public fun vhaddqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T {
    fun vhaddqU8(a1: Uint8x16T, b1: Uint8x16T): Uint8x16T = Uint8x16T()
    return vhaddqU8(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddU16)
 */
public fun vhaddU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T {
    fun vhaddU16(a1: Uint16x4T, b1: Uint16x4T): Uint16x4T = Uint16x4T()
    return vhaddU16(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddqU16)
 */
public fun vhaddqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T {
    fun vhaddqU16(a1: Uint16x8T, b1: Uint16x8T): Uint16x8T = Uint16x8T()
    return vhaddqU16(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddU32)
 */
public fun vhaddU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T {
    fun vhaddU32(a1: Uint32x2T, b1: Uint32x2T): Uint32x2T = Uint32x2T()
    return vhaddU32(a, b)
}

/**
 * Halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhaddqU32)
 */
public fun vhaddqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T {
    fun vhaddqU32(a1: Uint32x4T, b1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vhaddqU32(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubS16)
 */
public fun vhsubS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vhsubS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vhsubS16(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubqS16)
 */
public fun vhsubqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun vhsubqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return vhsubqS16(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubS32)
 */
public fun vhsubS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vhsubS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vhsubS32(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubqS32)
 */
public fun vhsubqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun vhsubqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return vhsubqS32(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubS8)
 */
public fun vhsubS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vhsubS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vhsubS8(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubqS8)
 */
public fun vhsubqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    fun vhsubqS8(a1: Int8x16T, b1: Int8x16T): Int8x16T = Int8x16T()
    return vhsubqS8(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubU8)
 */
public fun vhsubU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T {
    fun vhsubU8(a1: Uint8x8T, b1: Uint8x8T): Uint8x8T = Uint8x8T()
    return vhsubU8(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubqU8)
 */
public fun vhsubqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T {
    fun vhsubqU8(a1: Uint8x16T, b1: Uint8x16T): Uint8x16T = Uint8x16T()
    return vhsubqU8(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubU16)
 */
public fun vhsubU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T {
    fun vhsubU16(a1: Uint16x4T, b1: Uint16x4T): Uint16x4T = Uint16x4T()
    return vhsubU16(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubqU16)
 */
public fun vhsubqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T {
    fun vhsubqU16(a1: Uint16x8T, b1: Uint16x8T): Uint16x8T = Uint16x8T()
    return vhsubqU16(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubU32)
 */
public fun vhsubU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T {
    fun vhsubU32(a1: Uint32x2T, b1: Uint32x2T): Uint32x2T = Uint32x2T()
    return vhsubU32(a, b)
}

/**
 * Signed halving subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vhsubqU32)
 */
public fun vhsubqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T {
    fun vhsubqU32(a1: Uint32x4T, b1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vhsubqU32(a, b)
}

/**
 * Load one single-element structure and replicate to all lanes of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupF16(ptr: ConstPtr): Float16x4T {
    val x: Float16x4T = vld1LaneF16(ptr, transmute(f16x4.splat(0.0)), 0)
    return run { val _simdArg0 = x; val _simdArg1 = x; val _simdArg2 = SimdShuffleIdx(listOf(0, 0, 0, 0)); _simdArg0 }
}

/**
 * Load one single-element structure and replicate to all lanes of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupF16(ptr: ConstPtr): Float16x8T {
    val x: Float16x8T = vld1qLaneF16(ptr, transmute(f16x8.splat(0.0)), 0)
    return run { val _simdArg0 = x; val _simdArg1 = x; val _simdArg2 = SimdShuffleIdx(listOf(0, 0, 0, 0, 0, 0, 0, 0)); _simdArg0 }
}

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupF32(ptr: ConstPtr): Float32x2T = transmute(f32x2.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F32)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupP16(ptr: ConstPtr): Poly16x4T = transmute(u16x4.splat(readUnaligned(ptr, typeLayoutT = ObjectSlotLayout<P16>())))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupP8(ptr: ConstPtr): Poly8x8T = transmute(u8x8.splat(readUnaligned(ptr, typeLayoutT = ObjectSlotLayout<P8>())))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupS16(ptr: ConstPtr): Int16x4T = transmute(i16x4.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I16)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupS32(ptr: ConstPtr): Int32x2T = transmute(i32x2.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I32)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupS8(ptr: ConstPtr): Int8x8T = transmute(i8x8.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I8)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupU16(ptr: ConstPtr): Uint16x4T = transmute(u16x4.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U16)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupU32(ptr: ConstPtr): Uint32x2T = transmute(u32x2.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U32)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupU8(ptr: ConstPtr): Uint8x8T = transmute(u8x8.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U8)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupF32(ptr: ConstPtr): Float32x4T = transmute(f32x4.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F32)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupP16(ptr: ConstPtr): Poly16x8T = transmute(u16x8.splat(readUnaligned(ptr, typeLayoutT = ObjectSlotLayout<P16>())))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupP8(ptr: ConstPtr): Poly8x16T = transmute(u8x16.splat(readUnaligned(ptr, typeLayoutT = ObjectSlotLayout<P8>())))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupS16(ptr: ConstPtr): Int16x8T = transmute(i16x8.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I16)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupS32(ptr: ConstPtr): Int32x4T = transmute(i32x4.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I32)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupS64(ptr: ConstPtr): Int64x2T = transmute(i64x2.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I64)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupS8(ptr: ConstPtr): Int8x16T = transmute(i8x16.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I8)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupU16(ptr: ConstPtr): Uint16x8T = transmute(u16x8.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U16)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupU32(ptr: ConstPtr): Uint32x4T = transmute(u32x4.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U32)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupU64(ptr: ConstPtr): Uint64x2T = transmute(u64x2.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U64)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupU8(ptr: ConstPtr): Uint8x16T = transmute(u8x16.splat(readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U8)))

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupP64(ptr: ConstPtr): Poly64x1T {
    val x: Poly64x1T
    run {
        x = vld1P64(ptr)
    }
    return x
}

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupS64(ptr: ConstPtr): Int64x1T {
    val x: Int64x1T
    run {
        x = vld1S64(ptr)
    }
    return x
}

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1DupU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1DupU64(ptr: ConstPtr): Uint64x1T {
    val x: Uint64x1T
    run {
        x = vld1U64(ptr)
    }
    return x
}

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F16X2(a: ConstPtr): Float16x4x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F16X3(a: ConstPtr): Float16x4x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F16X4(a: ConstPtr): Float16x4x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF16X2(a: ConstPtr): Float16x8x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF16X3(a: ConstPtr): Float16x8x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF16X4(a: ConstPtr): Float16x8x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F32X2(a: ConstPtr): Float32x2x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F32X3(a: ConstPtr): Float32x2x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1F32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1F32X4(a: ConstPtr): Float32x2x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF32X2(a: ConstPtr): Float32x4x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF32X3(a: ConstPtr): Float32x4x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qF32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qF32X4(a: ConstPtr): Float32x4x4T = readUnaligned(a)

/**
 * Load one single-element structure to one lane of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneF16(ptr: ConstPtr, src: Float16x4T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F16_LAYOUT); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneF16(ptr: ConstPtr, src: Float16x8T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F16_LAYOUT); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneF32(ptr: ConstPtr, src: Float32x2T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F32); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneP16(ptr: ConstPtr, src: Poly16x4T, lane: Int): Poly16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = ObjectSlotLayout<P16>()); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneP8(ptr: ConstPtr, src: Poly8x8T, lane: Int): Poly8x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = ObjectSlotLayout<P8>()); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneS16(ptr: ConstPtr, src: Int16x4T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I16); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneS32(ptr: ConstPtr, src: Int32x2T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I32); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneS64(ptr: ConstPtr, src: Int64x1T, lane: Int): Int64x1T {
    run {
        check(lane == 0)
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I64); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneS8(ptr: ConstPtr, src: Int8x8T, lane: Int): Int8x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I8); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneU16(ptr: ConstPtr, src: Uint16x4T, lane: Int): Uint16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U16); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneU32(ptr: ConstPtr, src: Uint32x2T, lane: Int): Uint32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U32); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneU64(ptr: ConstPtr, src: Uint64x1T, lane: Int): Uint64x1T {
    run {
        check(lane == 0)
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U64); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneU8(ptr: ConstPtr, src: Uint8x8T, lane: Int): Uint8x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U8); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneF32(ptr: ConstPtr, src: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.F32); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneP16(ptr: ConstPtr, src: Poly16x8T, lane: Int): Poly16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = ObjectSlotLayout<P16>()); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneP8(ptr: ConstPtr, src: Poly8x16T, lane: Int): Poly8x16T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = ObjectSlotLayout<P8>()); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneS16(ptr: ConstPtr, src: Int16x8T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I16); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneS32(ptr: ConstPtr, src: Int32x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I32); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneS64(ptr: ConstPtr, src: Int64x2T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I64); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneS8(ptr: ConstPtr, src: Int8x16T, lane: Int): Int8x16T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.I8); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneU16(ptr: ConstPtr, src: Uint16x8T, lane: Int): Uint16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U16); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneU32(ptr: ConstPtr, src: Uint32x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U32); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneU64(ptr: ConstPtr, src: Uint64x2T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U64); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneU8(ptr: ConstPtr, src: Uint8x16T, lane: Int): Uint8x16T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = PrimitiveLayouts.U8); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1LaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1LaneP64(ptr: ConstPtr, src: Poly64x1T, lane: Int): Poly64x1T {
    run {
        check(lane == 0)
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = ObjectSlotLayout<P64>()); _simdArg0 }
}

/**
 * Load one single-element structure to one lane of one register.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qLaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qLaneP64(ptr: ConstPtr, src: Poly64x2T, lane: Int): Poly64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = src; val _simdArg1 = lane.toUInt(); val _simdArg2 = readUnaligned(ptr, typeLayoutT = ObjectSlotLayout<P64>()); _simdArg0 }
}

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P64X2(a: ConstPtr): Poly64x1x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P64X3(a: ConstPtr): Poly64x1x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P64X4(a: ConstPtr): Poly64x1x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP64X2(a: ConstPtr): Poly64x2x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP64X3(a: ConstPtr): Poly64x2x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP64X4(a: ConstPtr): Poly64x2x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S8X2(a: ConstPtr): Int8x8x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S8X3(a: ConstPtr): Int8x8x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S8X4(a: ConstPtr): Int8x8x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS8X2(a: ConstPtr): Int8x16x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS8X3(a: ConstPtr): Int8x16x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS8X4(a: ConstPtr): Int8x16x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S16X2(a: ConstPtr): Int16x4x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S16X3(a: ConstPtr): Int16x4x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S16X4(a: ConstPtr): Int16x4x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS16X2(a: ConstPtr): Int16x8x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS16X3(a: ConstPtr): Int16x8x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS16X4(a: ConstPtr): Int16x8x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S32X2(a: ConstPtr): Int32x2x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S32X3(a: ConstPtr): Int32x2x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S32X4(a: ConstPtr): Int32x2x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS32X2(a: ConstPtr): Int32x4x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS32X3(a: ConstPtr): Int32x4x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS32X4(a: ConstPtr): Int32x4x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S64X2(a: ConstPtr): Int64x1x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S64X3(a: ConstPtr): Int64x1x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1S64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1S64X4(a: ConstPtr): Int64x1x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS64X2(a: ConstPtr): Int64x2x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS64X3(a: ConstPtr): Int64x2x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qS64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qS64X4(a: ConstPtr): Int64x2x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U8X2(a: ConstPtr): Uint8x8x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U8X3(a: ConstPtr): Uint8x8x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U8X4(a: ConstPtr): Uint8x8x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU8X2(a: ConstPtr): Uint8x16x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU8X3(a: ConstPtr): Uint8x16x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU8X4(a: ConstPtr): Uint8x16x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U16X2(a: ConstPtr): Uint16x4x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U16X3(a: ConstPtr): Uint16x4x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U16X4(a: ConstPtr): Uint16x4x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU16X2(a: ConstPtr): Uint16x8x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU16X3(a: ConstPtr): Uint16x8x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU16X4(a: ConstPtr): Uint16x8x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U32X2(a: ConstPtr): Uint32x2x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U32X3(a: ConstPtr): Uint32x2x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U32X4(a: ConstPtr): Uint32x2x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU32X2(a: ConstPtr): Uint32x4x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU32X3(a: ConstPtr): Uint32x4x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU32X4(a: ConstPtr): Uint32x4x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U64X2(a: ConstPtr): Uint64x1x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U64X3(a: ConstPtr): Uint64x1x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1U64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1U64X4(a: ConstPtr): Uint64x1x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU64X2(a: ConstPtr): Uint64x2x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU64X3(a: ConstPtr): Uint64x2x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qU64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qU64X4(a: ConstPtr): Uint64x2x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P8X2(a: ConstPtr): Poly8x8x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P8X3(a: ConstPtr): Poly8x8x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P8X4(a: ConstPtr): Poly8x8x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP8X2(a: ConstPtr): Poly8x16x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP8X3(a: ConstPtr): Poly8x16x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP8X4(a: ConstPtr): Poly8x16x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P16X2(a: ConstPtr): Poly16x4x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P16X3(a: ConstPtr): Poly16x4x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1P16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1P16X4(a: ConstPtr): Poly16x4x4T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP16X2(a: ConstPtr): Poly16x8x2T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP16X3(a: ConstPtr): Poly16x8x3T = readUnaligned(a)

/**
 * Load multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qP16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qP16X4(a: ConstPtr): Poly16x8x4T = readUnaligned(a)

/**
 * Load one single-element structure and Replicate to all lanes (of one register).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld1qDupP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld1qDupP64(ptr: ConstPtr): Poly64x2T {
    val x = vld1qLaneP64(ptr, transmute(u64x2.splat(0)), 0)
    return run { val _simdArg0 = x; val _simdArg1 = x; val _simdArg2 = SimdShuffleIdx(listOf(0, 0)); _simdArg0 }
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupF16(a: ConstPtr): Float16x4x2T {
    fun vld2DupF16(ptr: ConstPtr): Float16x4x2T = Float16x4x2T()
    return vld2DupF16(((a as Any?) as Float16x4x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupF16(a: ConstPtr): Float16x8x2T {
    fun vld2qDupF16(ptr: ConstPtr): Float16x8x2T = Float16x8x2T()
    return vld2qDupF16(((a as Any?) as Float16x8x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupF32(a: ConstPtr): Float32x2x2T {
    fun vld2DupF32(ptr: ConstPtr): Float32x2x2T = Float32x2x2T()
    return vld2DupF32(((a as Any?) as Float32x2x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupF32(a: ConstPtr): Float32x4x2T {
    fun vld2qDupF32(ptr: ConstPtr): Float32x4x2T = Float32x4x2T()
    return vld2qDupF32(((a as Any?) as Float32x4x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupS8(a: ConstPtr): Int8x8x2T {
    fun vld2DupS8(ptr: ConstPtr): Int8x8x2T = Int8x8x2T()
    return vld2DupS8(((a as Any?) as Int8x8x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupS8(a: ConstPtr): Int8x16x2T {
    fun vld2qDupS8(ptr: ConstPtr): Int8x16x2T = Int8x16x2T()
    return vld2qDupS8(((a as Any?) as Int8x16x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupS16(a: ConstPtr): Int16x4x2T {
    fun vld2DupS16(ptr: ConstPtr): Int16x4x2T = Int16x4x2T()
    return vld2DupS16(((a as Any?) as Int16x4x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupS16(a: ConstPtr): Int16x8x2T {
    fun vld2qDupS16(ptr: ConstPtr): Int16x8x2T = Int16x8x2T()
    return vld2qDupS16(((a as Any?) as Int16x8x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupS32(a: ConstPtr): Int32x2x2T {
    fun vld2DupS32(ptr: ConstPtr): Int32x2x2T = Int32x2x2T()
    return vld2DupS32(((a as Any?) as Int32x2x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupS32(a: ConstPtr): Int32x4x2T {
    fun vld2qDupS32(ptr: ConstPtr): Int32x4x2T = Int32x4x2T()
    return vld2qDupS32(((a as Any?) as Int32x4x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupP64(a: ConstPtr): Poly64x1x2T = transmute(vld2DupS64(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupS64(a: ConstPtr): Int64x1x2T {
    fun vld2DupS64(ptr: ConstPtr): Int64x1x2T = Int64x1x2T()
    return vld2DupS64(((a as Any?) as Int64x1x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupU64(a: ConstPtr): Uint64x1x2T = transmute(vld2DupS64(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupU8(a: ConstPtr): Uint8x8x2T = transmute(vld2DupS8(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupU8(a: ConstPtr): Uint8x16x2T = transmute(vld2qDupS8(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupU16(a: ConstPtr): Uint16x4x2T = transmute(vld2DupS16(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupU16(a: ConstPtr): Uint16x8x2T = transmute(vld2qDupS16(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupU32(a: ConstPtr): Uint32x2x2T = transmute(vld2DupS32(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupU32(a: ConstPtr): Uint32x4x2T = transmute(vld2qDupS32(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupP8(a: ConstPtr): Poly8x8x2T = transmute(vld2DupS8(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupP8(a: ConstPtr): Poly8x16x2T = transmute(vld2qDupS8(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2DupP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2DupP16(a: ConstPtr): Poly16x4x2T = transmute(vld2DupS16(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qDupP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qDupP16(a: ConstPtr): Poly16x8x2T = transmute(vld2qDupS16(transmute(a)))

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2F16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2F16(a: ConstPtr): Float16x4x2T {
    fun vld2F16(ptr: ConstPtr): Float16x4x2T = Float16x4x2T()
    return vld2F16(((a as Any?) as Float16x4x2T))
}

/**
 * Load single 2-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qF16(a: ConstPtr): Float16x8x2T {
    fun vld2qF16(ptr: ConstPtr): Float16x8x2T = Float16x8x2T()
    return vld2qF16(((a as Any?) as Float16x8x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2F32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2F32(a: ConstPtr): Float32x2x2T {
    fun vld2F32(ptr: ConstPtr): Float32x2x2T = Float32x2x2T()
    return vld2F32(((a as Any?) as Float32x2x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qF32(a: ConstPtr): Float32x4x2T {
    fun vld2qF32(ptr: ConstPtr): Float32x4x2T = Float32x4x2T()
    return vld2qF32(((a as Any?) as Float32x4x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2S8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2S8(a: ConstPtr): Int8x8x2T {
    fun vld2S8(ptr: ConstPtr): Int8x8x2T = Int8x8x2T()
    return vld2S8(((a as Any?) as Int8x8x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qS8(a: ConstPtr): Int8x16x2T {
    fun vld2qS8(ptr: ConstPtr): Int8x16x2T = Int8x16x2T()
    return vld2qS8(((a as Any?) as Int8x16x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2S16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2S16(a: ConstPtr): Int16x4x2T {
    fun vld2S16(ptr: ConstPtr): Int16x4x2T = Int16x4x2T()
    return vld2S16(((a as Any?) as Int16x4x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qS16(a: ConstPtr): Int16x8x2T {
    fun vld2qS16(ptr: ConstPtr): Int16x8x2T = Int16x8x2T()
    return vld2qS16(((a as Any?) as Int16x8x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2S32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2S32(a: ConstPtr): Int32x2x2T {
    fun vld2S32(ptr: ConstPtr): Int32x2x2T = Int32x2x2T()
    return vld2S32(((a as Any?) as Int32x2x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qS32(a: ConstPtr): Int32x4x2T {
    fun vld2qS32(ptr: ConstPtr): Int32x4x2T = Int32x4x2T()
    return vld2qS32(((a as Any?) as Int32x4x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneF16(a: ConstPtr, b: Float16x4x2T, lane: Int): Float16x4x2T {
    fun vld2LaneF16(a1: Float16x4T, b1: Float16x4T, n: Long, ptr: ConstPtr): Float16x4x2T = Float16x4x2T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld2LaneF16(b.first, b.second, lane.toLong(), ((a as Any?) as Float16x4x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneF16(a: ConstPtr, b: Float16x8x2T, lane: Int): Float16x8x2T {
    fun vld2qLaneF16(a1: Float16x8T, b1: Float16x8T, n: Long, ptr: ConstPtr): Float16x8x2T = Float16x8x2T()
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vld2qLaneF16(b.first, b.second, lane.toLong(), ((a as Any?) as Float16x8x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneF32(a: ConstPtr, b: Float32x2x2T, lane: Int): Float32x2x2T {
    fun vld2LaneF32(a1: Float32x2T, b1: Float32x2T, n: Long, ptr: ConstPtr): Float32x2x2T = Float32x2x2T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld2LaneF32(b.first, b.second, lane.toLong(), ((a as Any?) as Float32x2x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneF32(a: ConstPtr, b: Float32x4x2T, lane: Int): Float32x4x2T {
    fun vld2qLaneF32(a1: Float32x4T, b1: Float32x4T, n: Long, ptr: ConstPtr): Float32x4x2T = Float32x4x2T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld2qLaneF32(b.first, b.second, lane.toLong(), ((a as Any?) as Float32x4x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneS8(a: ConstPtr, b: Int8x8x2T, lane: Int): Int8x8x2T {
    fun vld2LaneS8(a1: Int8x8T, b1: Int8x8T, n: Long, ptr: ConstPtr): Int8x8x2T = Int8x8x2T()
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vld2LaneS8(b.first, b.second, lane.toLong(), ((a as Any?) as Int8x8x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneS16(a: ConstPtr, b: Int16x4x2T, lane: Int): Int16x4x2T {
    fun vld2LaneS16(a1: Int16x4T, b1: Int16x4T, n: Long, ptr: ConstPtr): Int16x4x2T = Int16x4x2T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld2LaneS16(b.first, b.second, lane.toLong(), ((a as Any?) as Int16x4x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneS16(a: ConstPtr, b: Int16x8x2T, lane: Int): Int16x8x2T {
    fun vld2qLaneS16(a1: Int16x8T, b1: Int16x8T, n: Long, ptr: ConstPtr): Int16x8x2T = Int16x8x2T()
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vld2qLaneS16(b.first, b.second, lane.toLong(), ((a as Any?) as Int16x8x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneS32(a: ConstPtr, b: Int32x2x2T, lane: Int): Int32x2x2T {
    fun vld2LaneS32(a1: Int32x2T, b1: Int32x2T, n: Long, ptr: ConstPtr): Int32x2x2T = Int32x2x2T()
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vld2LaneS32(b.first, b.second, lane.toLong(), ((a as Any?) as Int32x2x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneS32(a: ConstPtr, b: Int32x4x2T, lane: Int): Int32x4x2T {
    fun vld2qLaneS32(a1: Int32x4T, b1: Int32x4T, n: Long, ptr: ConstPtr): Int32x4x2T = Int32x4x2T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld2qLaneS32(b.first, b.second, lane.toLong(), ((a as Any?) as Int32x4x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneU8(a: ConstPtr, b: Uint8x8x2T, lane: Int): Uint8x8x2T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld2LaneS8(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneU16(a: ConstPtr, b: Uint16x4x2T, lane: Int): Uint16x4x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(vld2LaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneU16(a: ConstPtr, b: Uint16x8x2T, lane: Int): Uint16x8x2T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld2qLaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneU32(a: ConstPtr, b: Uint32x2x2T, lane: Int): Uint32x2x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmute(vld2LaneS32(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneU32(a: ConstPtr, b: Uint32x4x2T, lane: Int): Uint32x4x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(vld2qLaneS32(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneP8(a: ConstPtr, b: Poly8x8x2T, lane: Int): Poly8x8x2T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld2LaneS8(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2LaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2LaneP16(a: ConstPtr, b: Poly16x4x2T, lane: Int): Poly16x4x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(vld2LaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qLaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qLaneP16(a: ConstPtr, b: Poly16x8x2T, lane: Int): Poly16x8x2T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld2qLaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2P64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2P64(a: ConstPtr): Poly64x1x2T = transmute(vld2S64(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2S64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2S64(a: ConstPtr): Int64x1x2T {
    fun vld2S64(ptr: ConstPtr): Int64x1x2T = Int64x1x2T()
    return vld2S64(((a as Any?) as Int64x1x2T))
}

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2U64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2U64(a: ConstPtr): Uint64x1x2T = transmute(vld2S64(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2U8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2U8(a: ConstPtr): Uint8x8x2T = transmute(vld2S8(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qU8(a: ConstPtr): Uint8x16x2T = transmute(vld2qS8(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2U16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2U16(a: ConstPtr): Uint16x4x2T = transmute(vld2S16(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qU16(a: ConstPtr): Uint16x8x2T = transmute(vld2qS16(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2U32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2U32(a: ConstPtr): Uint32x2x2T = transmute(vld2S32(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qU32(a: ConstPtr): Uint32x4x2T = transmute(vld2qS32(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2P8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2P8(a: ConstPtr): Poly8x8x2T = transmute(vld2S8(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qP8(a: ConstPtr): Poly8x16x2T = transmute(vld2qS8(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2P16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2P16(a: ConstPtr): Poly16x4x2T = transmute(vld2S16(transmute(a)))

/**
 * Load multiple 2-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld2qP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld2qP16(a: ConstPtr): Poly16x8x2T = transmute(vld2qS16(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupF16(a: ConstPtr): Float16x4x3T {
    fun vld3DupF16(ptr: ConstPtr): Float16x4x3T = Float16x4x3T()
    return vld3DupF16(((a as Any?) as Float16x4x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupF16(a: ConstPtr): Float16x8x3T {
    fun vld3qDupF16(ptr: ConstPtr): Float16x8x3T = Float16x8x3T()
    return vld3qDupF16(((a as Any?) as Float16x8x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupF32(a: ConstPtr): Float32x2x3T {
    fun vld3DupF32(ptr: ConstPtr): Float32x2x3T = Float32x2x3T()
    return vld3DupF32(((a as Any?) as Float32x2x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupF32(a: ConstPtr): Float32x4x3T {
    fun vld3qDupF32(ptr: ConstPtr): Float32x4x3T = Float32x4x3T()
    return vld3qDupF32(((a as Any?) as Float32x4x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupS8(a: ConstPtr): Int8x8x3T {
    fun vld3DupS8(ptr: ConstPtr): Int8x8x3T = Int8x8x3T()
    return vld3DupS8(((a as Any?) as Int8x8x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupS8(a: ConstPtr): Int8x16x3T {
    fun vld3qDupS8(ptr: ConstPtr): Int8x16x3T = Int8x16x3T()
    return vld3qDupS8(((a as Any?) as Int8x16x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupS16(a: ConstPtr): Int16x4x3T {
    fun vld3DupS16(ptr: ConstPtr): Int16x4x3T = Int16x4x3T()
    return vld3DupS16(((a as Any?) as Int16x4x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupS16(a: ConstPtr): Int16x8x3T {
    fun vld3qDupS16(ptr: ConstPtr): Int16x8x3T = Int16x8x3T()
    return vld3qDupS16(((a as Any?) as Int16x8x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupS32(a: ConstPtr): Int32x2x3T {
    fun vld3DupS32(ptr: ConstPtr): Int32x2x3T = Int32x2x3T()
    return vld3DupS32(((a as Any?) as Int32x2x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupS32(a: ConstPtr): Int32x4x3T {
    fun vld3qDupS32(ptr: ConstPtr): Int32x4x3T = Int32x4x3T()
    return vld3qDupS32(((a as Any?) as Int32x4x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupS64(a: ConstPtr): Int64x1x3T {
    fun vld3DupS64(ptr: ConstPtr): Int64x1x3T = Int64x1x3T()
    return vld3DupS64(((a as Any?) as Int64x1x3T))
}

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupP64(a: ConstPtr): Poly64x1x3T = transmute(vld3DupS64(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupU64(a: ConstPtr): Uint64x1x3T = transmute(vld3DupS64(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupU8(a: ConstPtr): Uint8x8x3T = transmute(vld3DupS8(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupU8(a: ConstPtr): Uint8x16x3T = transmute(vld3qDupS8(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupU16(a: ConstPtr): Uint16x4x3T = transmute(vld3DupS16(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupU16(a: ConstPtr): Uint16x8x3T = transmute(vld3qDupS16(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupU32(a: ConstPtr): Uint32x2x3T = transmute(vld3DupS32(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupU32(a: ConstPtr): Uint32x4x3T = transmute(vld3qDupS32(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupP8(a: ConstPtr): Poly8x8x3T = transmute(vld3DupS8(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupP8(a: ConstPtr): Poly8x16x3T = transmute(vld3qDupS8(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3DupP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3DupP16(a: ConstPtr): Poly16x4x3T = transmute(vld3DupS16(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qDupP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qDupP16(a: ConstPtr): Poly16x8x3T = transmute(vld3qDupS16(transmute(a)))

/**
 * Load single 3-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3F16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3F16(a: ConstPtr): Float16x4x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (4 * 3).toInt()))
        val w: W = W.fromArray(arr)
        val v0: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 2 > Unit)
        transmute(Triple(v0, v1, v2))
    }

/**
 * Load single 3-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qF16(a: ConstPtr): Float16x8x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (8 * 3).toInt()))
        val w: W = W.fromArray(arr)
        val v0: V = simdShuffle(w, w, deinterleaveMask, 8, 3, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 8, 3, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 8, 3, 2 > Unit)
        transmute(Triple(v0, v1, v2))
    }

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3F32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3F32(a: ConstPtr): Float32x2x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = PrimitiveArrayLayouts.floatArray((2 * 3).toInt()))
        val w: W = W.fromArray(arr)
        val v0: V = simdShuffle(w, w, deinterleaveMask, 2, 3, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 2, 3, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 2, 3, 2 > Unit)
        transmute(Triple(v0, v1, v2))
    }

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qF32(a: ConstPtr): Float32x4x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = PrimitiveArrayLayouts.floatArray((4 * 3).toInt()))
        val w: W = W.fromArray(arr)
        val v0: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 2 > Unit)
        transmute(Triple(v0, v1, v2))
    }

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3S8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3S8(a: ConstPtr): Int8x8x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = PrimitiveArrayLayouts.byteArray((8 * 3).toInt()))
        val w: W = W.fromArray(arr)
        val v0: V = simdShuffle(w, w, deinterleaveMask, 8, 3, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 8, 3, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 8, 3, 2 > Unit)
        transmute(Triple(v0, v1, v2))
    }

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qS8(a: ConstPtr): Int8x16x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = PrimitiveArrayLayouts.byteArray((16 * 3).toInt()))
        val w: W = W.fromArray(arr)
        val v0: V = simdShuffle(w, w, deinterleaveMask, 16, 3, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 16, 3, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 16, 3, 2 > Unit)
        transmute(Triple(v0, v1, v2))
    }

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3S16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3S16(a: ConstPtr): Int16x4x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = PrimitiveArrayLayouts.shortArray((4 * 3).toInt()))
        val w: W = W.fromArray(arr)
        val v0: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 2 > Unit)
        transmute(Triple(v0, v1, v2))
    }

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qS16(a: ConstPtr): Int16x8x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = PrimitiveArrayLayouts.shortArray((8 * 3).toInt()))
        val w: W = W.fromArray(arr)
        val v0: V = simdShuffle(w, w, deinterleaveMask, 8, 3, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 8, 3, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 8, 3, 2 > Unit)
        transmute(Triple(v0, v1, v2))
    }

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3S32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3S32(a: ConstPtr): Int32x2x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = PrimitiveArrayLayouts.intArray((2 * 3).toInt()))
        val w: W = W.fromArray(arr)
        val v0: V = simdShuffle(w, w, deinterleaveMask, 2, 3, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 2, 3, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 2, 3, 2 > Unit)
        transmute(Triple(v0, v1, v2))
    }

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qS32(a: ConstPtr): Int32x4x3T =
    run {
        val arr: Arr = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = PrimitiveArrayLayouts.intArray((4 * 3).toInt()))
        val w: W = W.fromArray(arr)
        val v0: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 4, 3, 2 > Unit)
        transmute(Triple(v0, v1, v2))
    }

/**
 * Load multiple 3-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneF16(a: ConstPtr, b: Float16x4x3T, lane: Int): Float16x4x3T {
    fun vld3LaneF16(a1: Float16x4T, b1: Float16x4T, c: Float16x4T, n: Long, ptr: ConstPtr): Float16x4x3T = Float16x4x3T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld3LaneF16(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Float16x4x3T))
}

/**
 * Load multiple 3-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneF16(a: ConstPtr, b: Float16x8x3T, lane: Int): Float16x8x3T {
    fun vld3qLaneF16(a1: Float16x8T, b1: Float16x8T, c: Float16x8T, n: Long, ptr: ConstPtr): Float16x8x3T = Float16x8x3T()
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vld3qLaneF16(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Float16x8x3T))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneF32(a: ConstPtr, b: Float32x2x3T, lane: Int): Float32x2x3T {
    fun vld3LaneF32(a1: Float32x2T, b1: Float32x2T, c: Float32x2T, n: Long, ptr: ConstPtr): Float32x2x3T = Float32x2x3T()
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vld3LaneF32(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Float32x2x3T))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneF32(a: ConstPtr, b: Float32x4x3T, lane: Int): Float32x4x3T {
    fun vld3qLaneF32(a1: Float32x4T, b1: Float32x4T, c: Float32x4T, n: Long, ptr: ConstPtr): Float32x4x3T = Float32x4x3T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld3qLaneF32(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Float32x4x3T))
}

/**
 * Load multiple 3-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneS8(a: ConstPtr, b: Int8x8x3T, lane: Int): Int8x8x3T {
    fun vld3LaneS8(a1: Int8x8T, b1: Int8x8T, c: Int8x8T, n: Long, ptr: ConstPtr): Int8x8x3T = Int8x8x3T()
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vld3LaneS8(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Int8x8x3T))
}

/**
 * Load multiple 3-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneS16(a: ConstPtr, b: Int16x4x3T, lane: Int): Int16x4x3T {
    fun vld3LaneS16(a1: Int16x4T, b1: Int16x4T, c: Int16x4T, n: Long, ptr: ConstPtr): Int16x4x3T = Int16x4x3T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld3LaneS16(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Int16x4x3T))
}

/**
 * Load multiple 3-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneS16(a: ConstPtr, b: Int16x8x3T, lane: Int): Int16x8x3T {
    fun vld3qLaneS16(a1: Int16x8T, b1: Int16x8T, c: Int16x8T, n: Long, ptr: ConstPtr): Int16x8x3T = Int16x8x3T()
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return vld3qLaneS16(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Int16x8x3T))
}

/**
 * Load multiple 3-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneS32(a: ConstPtr, b: Int32x2x3T, lane: Int): Int32x2x3T {
    fun vld3LaneS32(a1: Int32x2T, b1: Int32x2T, c: Int32x2T, n: Long, ptr: ConstPtr): Int32x2x3T = Int32x2x3T()
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vld3LaneS32(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Int32x2x3T))
}

/**
 * Load multiple 3-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneS32(a: ConstPtr, b: Int32x4x3T, lane: Int): Int32x4x3T {
    fun vld3qLaneS32(a1: Int32x4T, b1: Int32x4T, c: Int32x4T, n: Long, ptr: ConstPtr): Int32x4x3T = Int32x4x3T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld3qLaneS32(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Int32x4x3T))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneU8(a: ConstPtr, b: Uint8x8x3T, lane: Int): Uint8x8x3T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld3LaneS8(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneU16(a: ConstPtr, b: Uint16x4x3T, lane: Int): Uint16x4x3T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(vld3LaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneU16(a: ConstPtr, b: Uint16x8x3T, lane: Int): Uint16x8x3T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld3qLaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneU32(a: ConstPtr, b: Uint32x2x3T, lane: Int): Uint32x2x3T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmute(vld3LaneS32(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneU32(a: ConstPtr, b: Uint32x4x3T, lane: Int): Uint32x4x3T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(vld3qLaneS32(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneP8(a: ConstPtr, b: Poly8x8x3T, lane: Int): Poly8x8x3T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld3LaneS8(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3LaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3LaneP16(a: ConstPtr, b: Poly16x4x3T, lane: Int): Poly16x4x3T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(vld3LaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qLaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qLaneP16(a: ConstPtr, b: Poly16x8x3T, lane: Int): Poly16x8x3T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld3qLaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3P64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3P64(a: ConstPtr): Poly64x1x3T = transmute(vld3S64(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3S64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3S64(a: ConstPtr): Int64x1x3T = readUnaligned(a)

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3U64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3U64(a: ConstPtr): Uint64x1x3T = transmute(vld3S64(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3U8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3U8(a: ConstPtr): Uint8x8x3T = transmute(vld3S8(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qU8(a: ConstPtr): Uint8x16x3T = transmute(vld3qS8(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3U16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3U16(a: ConstPtr): Uint16x4x3T = transmute(vld3S16(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qU16(a: ConstPtr): Uint16x8x3T = transmute(vld3qS16(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3U32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3U32(a: ConstPtr): Uint32x2x3T = transmute(vld3S32(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qU32(a: ConstPtr): Uint32x4x3T = transmute(vld3qS32(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3P8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3P8(a: ConstPtr): Poly8x8x3T = transmute(vld3S8(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qP8(a: ConstPtr): Poly8x16x3T = transmute(vld3qS8(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3P16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3P16(a: ConstPtr): Poly16x4x3T = transmute(vld3S16(transmute(a)))

/**
 * Load multiple 3-element structures to three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld3qP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld3qP16(a: ConstPtr): Poly16x8x3T = transmute(vld3qS16(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupF16(a: ConstPtr): Float16x4x4T {
    fun vld4DupF16(ptr: ConstPtr): Float16x4x4T = Float16x4x4T()
    return vld4DupF16(((a as Any?) as Float16x4x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupF16(a: ConstPtr): Float16x8x4T {
    fun vld4qDupF16(ptr: ConstPtr): Float16x8x4T = Float16x8x4T()
    return vld4qDupF16(((a as Any?) as Float16x8x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupF32(a: ConstPtr): Float32x2x4T {
    fun vld4DupF32(ptr: ConstPtr): Float32x2x4T = Float32x2x4T()
    return vld4DupF32(((a as Any?) as Float32x2x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupF32(a: ConstPtr): Float32x4x4T {
    fun vld4qDupF32(ptr: ConstPtr): Float32x4x4T = Float32x4x4T()
    return vld4qDupF32(((a as Any?) as Float32x4x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupS8(a: ConstPtr): Int8x8x4T {
    fun vld4DupS8(ptr: ConstPtr): Int8x8x4T = Int8x8x4T()
    return vld4DupS8(((a as Any?) as Int8x8x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupS8(a: ConstPtr): Int8x16x4T {
    fun vld4qDupS8(ptr: ConstPtr): Int8x16x4T = Int8x16x4T()
    return vld4qDupS8(((a as Any?) as Int8x16x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupS16(a: ConstPtr): Int16x4x4T {
    fun vld4DupS16(ptr: ConstPtr): Int16x4x4T = Int16x4x4T()
    return vld4DupS16(((a as Any?) as Int16x4x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupS16(a: ConstPtr): Int16x8x4T {
    fun vld4qDupS16(ptr: ConstPtr): Int16x8x4T = Int16x8x4T()
    return vld4qDupS16(((a as Any?) as Int16x8x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupS32(a: ConstPtr): Int32x2x4T {
    fun vld4DupS32(ptr: ConstPtr): Int32x2x4T = Int32x2x4T()
    return vld4DupS32(((a as Any?) as Int32x2x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupS32(a: ConstPtr): Int32x4x4T {
    fun vld4qDupS32(ptr: ConstPtr): Int32x4x4T = Int32x4x4T()
    return vld4qDupS32(((a as Any?) as Int32x4x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupS64(a: ConstPtr): Int64x1x4T {
    fun vld4DupS64(ptr: ConstPtr): Int64x1x4T = Int64x1x4T()
    return vld4DupS64(((a as Any?) as Int64x1x4T))
}

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupP64(a: ConstPtr): Poly64x1x4T = transmute(vld4DupS64(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupU64(a: ConstPtr): Uint64x1x4T = transmute(vld4DupS64(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupU8(a: ConstPtr): Uint8x8x4T = transmute(vld4DupS8(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupU8(a: ConstPtr): Uint8x16x4T = transmute(vld4qDupS8(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupU16(a: ConstPtr): Uint16x4x4T = transmute(vld4DupS16(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupU16(a: ConstPtr): Uint16x8x4T = transmute(vld4qDupS16(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupU32(a: ConstPtr): Uint32x2x4T = transmute(vld4DupS32(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupU32(a: ConstPtr): Uint32x4x4T = transmute(vld4qDupS32(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupP8(a: ConstPtr): Poly8x8x4T = transmute(vld4DupS8(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupP8(a: ConstPtr): Poly8x16x4T = transmute(vld4qDupS8(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4DupP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4DupP16(a: ConstPtr): Poly16x4x4T = transmute(vld4DupS16(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qDupP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qDupP16(a: ConstPtr): Poly16x8x4T = transmute(vld4qDupS16(transmute(a)))

/**
 * Load single 4-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4F16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4F16(a: ConstPtr): Float16x4x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = ObjectSlotLayout<W>())
        val v0: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 2 > Unit)
        val v3: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 3 > Unit)
        transmute(listOf(v0, v1, v2, v3))
    }

/**
 * Load single 4-element structure and replicate to all lanes of two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qF16(a: ConstPtr): Float16x8x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = ObjectSlotLayout<W>())
        val v0: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 2 > Unit)
        val v3: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 3 > Unit)
        transmute(listOf(v0, v1, v2, v3))
    }

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4F32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4F32(a: ConstPtr): Float32x2x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = ObjectSlotLayout<W>())
        val v0: V = simdShuffle(w, w, deinterleaveMask, 2, 4, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 2, 4, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 2, 4, 2 > Unit)
        val v3: V = simdShuffle(w, w, deinterleaveMask, 2, 4, 3 > Unit)
        transmute(listOf(v0, v1, v2, v3))
    }

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qF32(a: ConstPtr): Float32x4x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = ObjectSlotLayout<W>())
        val v0: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 2 > Unit)
        val v3: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 3 > Unit)
        transmute(listOf(v0, v1, v2, v3))
    }

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4S8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4S8(a: ConstPtr): Int8x8x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = ObjectSlotLayout<W>())
        val v0: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 2 > Unit)
        val v3: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 3 > Unit)
        transmute(listOf(v0, v1, v2, v3))
    }

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qS8(a: ConstPtr): Int8x16x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = ObjectSlotLayout<W>())
        val v0: V = simdShuffle(w, w, deinterleaveMask, 16, 4, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 16, 4, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 16, 4, 2 > Unit)
        val v3: V = simdShuffle(w, w, deinterleaveMask, 16, 4, 3 > Unit)
        transmute(listOf(v0, v1, v2, v3))
    }

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4S16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4S16(a: ConstPtr): Int16x4x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = ObjectSlotLayout<W>())
        val v0: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 2 > Unit)
        val v3: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 3 > Unit)
        transmute(listOf(v0, v1, v2, v3))
    }

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qS16(a: ConstPtr): Int16x8x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = ObjectSlotLayout<W>())
        val v0: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 2 > Unit)
        val v3: V = simdShuffle(w, w, deinterleaveMask, 8, 4, 3 > Unit)
        transmute(listOf(v0, v1, v2, v3))
    }

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4S32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4S32(a: ConstPtr): Int32x2x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = ObjectSlotLayout<W>())
        val v0: V = simdShuffle(w, w, deinterleaveMask, 2, 4, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 2, 4, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 2, 4, 2 > Unit)
        val v3: V = simdShuffle(w, w, deinterleaveMask, 2, 4, 3 > Unit)
        transmute(listOf(v0, v1, v2, v3))
    }

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qS32(a: ConstPtr): Int32x4x4T =
    run {
        val w: W = readUnaligned(ConstPtr(a.rawAddress, a.rawMeta), typeLayoutT = ObjectSlotLayout<W>())
        val v0: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 0 > Unit)
        val v1: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 1 > Unit)
        val v2: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 2 > Unit)
        val v3: V = simdShuffle(w, w, deinterleaveMask, 4, 4, 3 > Unit)
        transmute(listOf(v0, v1, v2, v3))
    }

/**
 * Load multiple 4-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneF16(a: ConstPtr, b: Float16x4x4T, lane: Int): Float16x4x4T {
    fun vld4LaneF16(a1: Float16x4T, b1: Float16x4T, c: Float16x4T, d: Float16x4T, n: Long, ptr: ConstPtr): Float16x4x4T = Float16x4x4T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld4LaneF16(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Float16x4x4T))
}

/**
 * Load multiple 4-element structures to two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneF16(a: ConstPtr, b: Float16x8x4T, lane: Int): Float16x8x4T {
    fun vld4qLaneF16(a1: Float16x8T, b1: Float16x8T, c: Float16x8T, d: Float16x8T, n: Long, ptr: ConstPtr): Float16x8x4T = Float16x8x4T()
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vld4qLaneF16(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Float16x8x4T))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneF32(a: ConstPtr, b: Float32x2x4T, lane: Int): Float32x2x4T {
    fun vld4LaneF32(a1: Float32x2T, b1: Float32x2T, c: Float32x2T, d: Float32x2T, n: Long, ptr: ConstPtr): Float32x2x4T = Float32x2x4T()
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vld4LaneF32(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Float32x2x4T))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneF32(a: ConstPtr, b: Float32x4x4T, lane: Int): Float32x4x4T {
    fun vld4qLaneF32(a1: Float32x4T, b1: Float32x4T, c: Float32x4T, d: Float32x4T, n: Long, ptr: ConstPtr): Float32x4x4T = Float32x4x4T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld4qLaneF32(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Float32x4x4T))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneS8(a: ConstPtr, b: Int8x8x4T, lane: Int): Int8x8x4T {
    fun vld4LaneS8(a1: Int8x8T, b1: Int8x8T, c: Int8x8T, d: Int8x8T, n: Long, ptr: ConstPtr): Int8x8x4T = Int8x8x4T()
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vld4LaneS8(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Int8x8x4T))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneS16(a: ConstPtr, b: Int16x4x4T, lane: Int): Int16x4x4T {
    fun vld4LaneS16(a1: Int16x4T, b1: Int16x4T, c: Int16x4T, d: Int16x4T, n: Long, ptr: ConstPtr): Int16x4x4T = Int16x4x4T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld4LaneS16(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Int16x4x4T))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneS16(a: ConstPtr, b: Int16x8x4T, lane: Int): Int16x8x4T {
    fun vld4qLaneS16(a1: Int16x8T, b1: Int16x8T, c: Int16x8T, d: Int16x8T, n: Long, ptr: ConstPtr): Int16x8x4T = Int16x8x4T()
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vld4qLaneS16(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Int16x8x4T))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneS32(a: ConstPtr, b: Int32x2x4T, lane: Int): Int32x2x4T {
    fun vld4LaneS32(a1: Int32x2T, b1: Int32x2T, c: Int32x2T, d: Int32x2T, n: Long, ptr: ConstPtr): Int32x2x4T = Int32x2x4T()
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vld4LaneS32(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Int32x2x4T))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneS32(a: ConstPtr, b: Int32x4x4T, lane: Int): Int32x4x4T {
    fun vld4qLaneS32(a1: Int32x4T, b1: Int32x4T, c: Int32x4T, d: Int32x4T, n: Long, ptr: ConstPtr): Int32x4x4T = Int32x4x4T()
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vld4qLaneS32(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Int32x4x4T))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneU8(a: ConstPtr, b: Uint8x8x4T, lane: Int): Uint8x8x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld4LaneS8(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneU16(a: ConstPtr, b: Uint16x4x4T, lane: Int): Uint16x4x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(vld4LaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneU16(a: ConstPtr, b: Uint16x8x4T, lane: Int): Uint16x8x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld4qLaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneU32(a: ConstPtr, b: Uint32x2x4T, lane: Int): Uint32x2x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return transmute(vld4LaneS32(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneU32(a: ConstPtr, b: Uint32x4x4T, lane: Int): Uint32x4x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(vld4qLaneS32(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneP8(a: ConstPtr, b: Poly8x8x4T, lane: Int): Poly8x8x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld4LaneS8(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4LaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4LaneP16(a: ConstPtr, b: Poly16x4x4T, lane: Int): Poly16x4x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return transmute(vld4LaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qLaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qLaneP16(a: ConstPtr, b: Poly16x8x4T, lane: Int): Poly16x8x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return transmute(vld4qLaneS16(transmute(a), transmute(b), lane))
}

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4P64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4P64(a: ConstPtr): Poly64x1x4T = transmute(vld4S64(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4S64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4S64(a: ConstPtr): Int64x1x4T = readUnaligned(a)

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4U64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4U64(a: ConstPtr): Uint64x1x4T = transmute(vld4S64(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4U8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4U8(a: ConstPtr): Uint8x8x4T = transmute(vld4S8(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qU8(a: ConstPtr): Uint8x16x4T = transmute(vld4qS8(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4U16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4U16(a: ConstPtr): Uint16x4x4T = transmute(vld4S16(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qU16(a: ConstPtr): Uint16x8x4T = transmute(vld4qS16(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4U32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4U32(a: ConstPtr): Uint32x2x4T = transmute(vld4S32(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qU32(a: ConstPtr): Uint32x4x4T = transmute(vld4qS32(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4P8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4P8(a: ConstPtr): Poly8x8x4T = transmute(vld4S8(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qP8(a: ConstPtr): Poly8x16x4T = transmute(vld4qS8(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4P16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4P16(a: ConstPtr): Poly16x4x4T = transmute(vld4S16(transmute(a)))

/**
 * Load multiple 4-element structures to four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vld4qP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vld4qP16(a: ConstPtr): Poly16x8x4T = transmute(vld4qS16(transmute(a)))

/**
 * Store SIMD&FP register (immediate offset)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vldrqP128)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vldrqP128(a: ConstPtr): P128 = (ObjectSlotLayout<P128>()).readNative((a).rawAddress)

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxF16)
 */
public fun vmaxF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun vmaxF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return vmaxF16(a, b)
}

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxqF16)
 */
public fun vmaxqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun vmaxqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return vmaxqF16(a, b)
}

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxF32)
 */
public fun vmaxF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun vmaxF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return vmaxF32(a, b)
}

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxqF32)
 */
public fun vmaxqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun vmaxqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return vmaxqF32(a, b)
}

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxS8)
 */
public fun vmaxS8(a: Int8x8T, b: Int8x8T): Int8x8T =
    run {
        val mask: Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; Int8x8T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxqS8)
 */
public fun vmaxqS8(a: Int8x16T, b: Int8x16T): Int8x16T =
    run {
        val mask: Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; Int8x16T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxS16)
 */
public fun vmaxS16(a: Int16x4T, b: Int16x4T): Int16x4T =
    run {
        val mask: Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; Int16x4T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxqS16)
 */
public fun vmaxqS16(a: Int16x8T, b: Int16x8T): Int16x8T =
    run {
        val mask: Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; Int16x8T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxS32)
 */
public fun vmaxS32(a: Int32x2T, b: Int32x2T): Int32x2T =
    run {
        val mask: Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; Int32x2T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxqS32)
 */
public fun vmaxqS32(a: Int32x4T, b: Int32x4T): Int32x4T =
    run {
        val mask: Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; Int32x4T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxU8)
 */
public fun vmaxU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T =
    run {
        val mask: Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; Uint8x8T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxqU8)
 */
public fun vmaxqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T =
    run {
        val mask: Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; Uint8x16T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxU16)
 */
public fun vmaxU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T =
    run {
        val mask: Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; Uint16x4T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxqU16)
 */
public fun vmaxqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T =
    run {
        val mask: Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; Uint16x8T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxU32)
 */
public fun vmaxU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T =
    run {
        val mask: Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; Uint32x2T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Maximum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxqU32)
 */
public fun vmaxqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T =
    run {
        val mask: Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; Uint32x4T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Floating-point Maximum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmF16)
 */
public fun vmaxnmF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun vmaxnmF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return vmaxnmF16(a, b)
}

/**
 * Floating-point Maximum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmqF16)
 */
public fun vmaxnmqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun vmaxnmqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return vmaxnmqF16(a, b)
}

/**
 * Floating-point Maximum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmF32)
 */
public fun vmaxnmF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun vmaxnmF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return vmaxnmF32(a, b)
}

/**
 * Floating-point Maximum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmaxnmqF32)
 */
public fun vmaxnmqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun vmaxnmqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return vmaxnmqF32(a, b)
}

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminF16)
 */
public fun vminF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun vminF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return vminF16(a, b)
}

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminqF16)
 */
public fun vminqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun vminqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return vminqF16(a, b)
}

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminF32)
 */
public fun vminF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun vminF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return vminF32(a, b)
}

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminqF32)
 */
public fun vminqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun vminqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return vminqF32(a, b)
}

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminS8)
 */
public fun vminS8(a: Int8x8T, b: Int8x8T): Int8x8T =
    run {
        val mask: Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; Int8x8T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminqS8)
 */
public fun vminqS8(a: Int8x16T, b: Int8x16T): Int8x16T =
    run {
        val mask: Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; Int8x16T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminS16)
 */
public fun vminS16(a: Int16x4T, b: Int16x4T): Int16x4T =
    run {
        val mask: Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; Int16x4T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminqS16)
 */
public fun vminqS16(a: Int16x8T, b: Int16x8T): Int16x8T =
    run {
        val mask: Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; Int16x8T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminS32)
 */
public fun vminS32(a: Int32x2T, b: Int32x2T): Int32x2T =
    run {
        val mask: Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; Int32x2T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminqS32)
 */
public fun vminqS32(a: Int32x4T, b: Int32x4T): Int32x4T =
    run {
        val mask: Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; Int32x4T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminU8)
 */
public fun vminU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T =
    run {
        val mask: Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; Uint8x8T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminqU8)
 */
public fun vminqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T =
    run {
        val mask: Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; Uint8x16T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminU16)
 */
public fun vminU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T =
    run {
        val mask: Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; Uint16x4T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminqU16)
 */
public fun vminqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T =
    run {
        val mask: Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; Uint16x8T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminU32)
 */
public fun vminU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T =
    run {
        val mask: Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; Uint32x2T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Minimum (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminqU32)
 */
public fun vminqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T =
    run {
        val mask: Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; Uint32x4T() }
        run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
    }

/**
 * Floating-point Minimum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmF16)
 */
public fun vminnmF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun vminnmF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return vminnmF16(a, b)
}

/**
 * Floating-point Minimum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmqF16)
 */
public fun vminnmqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun vminnmqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return vminnmqF16(a, b)
}

/**
 * Floating-point Minimum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmF32)
 */
public fun vminnmF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun vminnmF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return vminnmF32(a, b)
}

/**
 * Floating-point Minimum Number (vector)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vminnmqF32)
 */
public fun vminnmqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun vminnmqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return vminnmqF32(a, b)
}

/**
 * Floating-point multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaF32)
 */
public fun vmlaF32(a: Float32x2T, b: Float32x2T, c: Float32x2T): Float32x2T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Floating-point multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqF32)
 */
public fun vmlaqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T): Float32x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaLaneF32)
 */
public fun vmlaLaneF32(a: Float32x2T, b: Float32x2T, c: Float32x2T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlaF32(a, b, vdupLaneF32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaLaneqF32)
 */
public fun vmlaLaneqF32(a: Float32x2T, b: Float32x2T, c: Float32x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlaF32(a, b, vdupLaneqF32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqLaneF32)
 */
public fun vmlaqLaneF32(a: Float32x4T, b: Float32x4T, c: Float32x2T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlaqF32(a, b, vdupqLaneF32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqLaneqF32)
 */
public fun vmlaqLaneqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlaqF32(a, b, vdupqLaneqF32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaLaneS16)
 */
public fun vmlaLaneS16(a: Int16x4T, b: Int16x4T, c: Int16x4T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlaS16(a, b, vdupLaneS16(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaLaneU16)
 */
public fun vmlaLaneU16(a: Uint16x4T, b: Uint16x4T, c: Uint16x4T, lane: Int): Uint16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlaU16(a, b, vdupLaneU16(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaLaneqS16)
 */
public fun vmlaLaneqS16(a: Int16x4T, b: Int16x4T, c: Int16x8T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlaS16(a, b, vdupLaneqS16(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaLaneqU16)
 */
public fun vmlaLaneqU16(a: Uint16x4T, b: Uint16x4T, c: Uint16x8T, lane: Int): Uint16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlaU16(a, b, vdupLaneqU16(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqLaneS16)
 */
public fun vmlaqLaneS16(a: Int16x8T, b: Int16x8T, c: Int16x4T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlaqS16(a, b, vdupqLaneS16(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqLaneU16)
 */
public fun vmlaqLaneU16(a: Uint16x8T, b: Uint16x8T, c: Uint16x4T, lane: Int): Uint16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlaqU16(a, b, vdupqLaneU16(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqLaneqS16)
 */
public fun vmlaqLaneqS16(a: Int16x8T, b: Int16x8T, c: Int16x8T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlaqS16(a, b, vdupqLaneqS16(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqLaneqU16)
 */
public fun vmlaqLaneqU16(a: Uint16x8T, b: Uint16x8T, c: Uint16x8T, lane: Int): Uint16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlaqU16(a, b, vdupqLaneqU16(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaLaneS32)
 */
public fun vmlaLaneS32(a: Int32x2T, b: Int32x2T, c: Int32x2T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlaS32(a, b, vdupLaneS32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaLaneU32)
 */
public fun vmlaLaneU32(a: Uint32x2T, b: Uint32x2T, c: Uint32x2T, lane: Int): Uint32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlaU32(a, b, vdupLaneU32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaLaneqS32)
 */
public fun vmlaLaneqS32(a: Int32x2T, b: Int32x2T, c: Int32x4T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlaS32(a, b, vdupLaneqS32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaLaneqU32)
 */
public fun vmlaLaneqU32(a: Uint32x2T, b: Uint32x2T, c: Uint32x4T, lane: Int): Uint32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlaU32(a, b, vdupLaneqU32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqLaneS32)
 */
public fun vmlaqLaneS32(a: Int32x4T, b: Int32x4T, c: Int32x2T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlaqS32(a, b, vdupqLaneS32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqLaneU32)
 */
public fun vmlaqLaneU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x2T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlaqU32(a, b, vdupqLaneU32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqLaneqS32)
 */
public fun vmlaqLaneqS32(a: Int32x4T, b: Int32x4T, c: Int32x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlaqS32(a, b, vdupqLaneqS32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqLaneqU32)
 */
public fun vmlaqLaneqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlaqU32(a, b, vdupqLaneqU32(c, lane))
}

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaNF32)
 */
public fun vmlaNF32(a: Float32x2T, b: Float32x2T, c: Float): Float32x2T = vmlaF32(a, b, vdupNF32(c))

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqNF32)
 */
public fun vmlaqNF32(a: Float32x4T, b: Float32x4T, c: Float): Float32x4T = vmlaqF32(a, b, vdupqNF32(c))

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaNS16)
 */
public fun vmlaNS16(a: Int16x4T, b: Int16x4T, c: Short): Int16x4T = vmlaS16(a, b, vdupNS16(c))

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqNS16)
 */
public fun vmlaqNS16(a: Int16x8T, b: Int16x8T, c: Short): Int16x8T = vmlaqS16(a, b, vdupqNS16(c))

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaNU16)
 */
public fun vmlaNU16(a: Uint16x4T, b: Uint16x4T, c: UShort): Uint16x4T = vmlaU16(a, b, vdupNU16(c))

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqNU16)
 */
public fun vmlaqNU16(a: Uint16x8T, b: Uint16x8T, c: UShort): Uint16x8T = vmlaqU16(a, b, vdupqNU16(c))

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaNS32)
 */
public fun vmlaNS32(a: Int32x2T, b: Int32x2T, c: Int): Int32x2T = vmlaS32(a, b, vdupNS32(c))

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqNS32)
 */
public fun vmlaqNS32(a: Int32x4T, b: Int32x4T, c: Int): Int32x4T = vmlaqS32(a, b, vdupqNS32(c))

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaNU32)
 */
public fun vmlaNU32(a: Uint32x2T, b: Uint32x2T, c: UInt): Uint32x2T = vmlaU32(a, b, vdupNU32(c))

/**
 * Vector multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqNU32)
 */
public fun vmlaqNU32(a: Uint32x4T, b: Uint32x4T, c: UInt): Uint32x4T = vmlaqU32(a, b, vdupqNU32(c))

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaS8)
 */
public fun vmlaS8(a: Int8x8T, b: Int8x8T, c: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqS8)
 */
public fun vmlaqS8(a: Int8x16T, b: Int8x16T, c: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaS16)
 */
public fun vmlaS16(a: Int16x4T, b: Int16x4T, c: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqS16)
 */
public fun vmlaqS16(a: Int16x8T, b: Int16x8T, c: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaS32)
 */
public fun vmlaS32(a: Int32x2T, b: Int32x2T, c: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqS32)
 */
public fun vmlaqS32(a: Int32x4T, b: Int32x4T, c: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaU8)
 */
public fun vmlaU8(a: Uint8x8T, b: Uint8x8T, c: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqU8)
 */
public fun vmlaqU8(a: Uint8x16T, b: Uint8x16T, c: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaU16)
 */
public fun vmlaU16(a: Uint16x4T, b: Uint16x4T, c: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqU16)
 */
public fun vmlaqU16(a: Uint16x8T, b: Uint16x8T, c: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaU32)
 */
public fun vmlaU32(a: Uint32x2T, b: Uint32x2T, c: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-add to accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlaqU32)
 */
public fun vmlaqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalLaneS16)
 */
public fun vmlalLaneS16(a: Int32x4T, b: Int16x4T, c: Int16x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlalS16(a, b, vdupLaneS16(c, lane))
}

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalLaneqS16)
 */
public fun vmlalLaneqS16(a: Int32x4T, b: Int16x4T, c: Int16x8T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlalS16(a, b, vdupLaneqS16(c, lane))
}

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalLaneS32)
 */
public fun vmlalLaneS32(a: Int64x2T, b: Int32x2T, c: Int32x2T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlalS32(a, b, vdupLaneS32(c, lane))
}

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalLaneqS32)
 */
public fun vmlalLaneqS32(a: Int64x2T, b: Int32x2T, c: Int32x4T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlalS32(a, b, vdupLaneqS32(c, lane))
}

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalLaneU16)
 */
public fun vmlalLaneU16(a: Uint32x4T, b: Uint16x4T, c: Uint16x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlalU16(a, b, vdupLaneU16(c, lane))
}

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalLaneqU16)
 */
public fun vmlalLaneqU16(a: Uint32x4T, b: Uint16x4T, c: Uint16x8T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlalU16(a, b, vdupLaneqU16(c, lane))
}

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalLaneU32)
 */
public fun vmlalLaneU32(a: Uint64x2T, b: Uint32x2T, c: Uint32x2T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlalU32(a, b, vdupLaneU32(c, lane))
}

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalLaneqU32)
 */
public fun vmlalLaneqU32(a: Uint64x2T, b: Uint32x2T, c: Uint32x4T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlalU32(a, b, vdupLaneqU32(c, lane))
}

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalNS16)
 */
public fun vmlalNS16(a: Int32x4T, b: Int16x4T, c: Short): Int32x4T = vmlalS16(a, b, vdupNS16(c))

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalNS32)
 */
public fun vmlalNS32(a: Int64x2T, b: Int32x2T, c: Int): Int64x2T = vmlalS32(a, b, vdupNS32(c))

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalNU16)
 */
public fun vmlalNU16(a: Uint32x4T, b: Uint16x4T, c: UShort): Uint32x4T = vmlalU16(a, b, vdupNU16(c))

/**
 * Vector widening multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalNU32)
 */
public fun vmlalNU32(a: Uint64x2T, b: Uint32x2T, c: UInt): Uint64x2T = vmlalU32(a, b, vdupNU32(c))

/**
 * Signed multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalS8)
 */
public fun vmlalS8(a: Int16x8T, b: Int8x8T, c: Int8x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = vmullS8(b, c); _simdArg0 }

/**
 * Signed multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalS16)
 */
public fun vmlalS16(a: Int32x4T, b: Int16x4T, c: Int16x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = vmullS16(b, c); _simdArg0 }

/**
 * Signed multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalS32)
 */
public fun vmlalS32(a: Int64x2T, b: Int32x2T, c: Int32x2T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = vmullS32(b, c); _simdArg0 }

/**
 * Unsigned multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalU8)
 */
public fun vmlalU8(a: Uint16x8T, b: Uint8x8T, c: Uint8x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = vmullU8(b, c); _simdArg0 }

/**
 * Unsigned multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalU16)
 */
public fun vmlalU16(a: Uint32x4T, b: Uint16x4T, c: Uint16x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = vmullU16(b, c); _simdArg0 }

/**
 * Unsigned multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlalU32)
 */
public fun vmlalU32(a: Uint64x2T, b: Uint32x2T, c: Uint32x2T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = vmullU32(b, c); _simdArg0 }

/**
 * Floating-point multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsF32)
 */
public fun vmlsF32(a: Float32x2T, b: Float32x2T, c: Float32x2T): Float32x2T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Floating-point multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqF32)
 */
public fun vmlsqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T): Float32x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsLaneF32)
 */
public fun vmlsLaneF32(a: Float32x2T, b: Float32x2T, c: Float32x2T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlsF32(a, b, vdupLaneF32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsLaneqF32)
 */
public fun vmlsLaneqF32(a: Float32x2T, b: Float32x2T, c: Float32x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlsF32(a, b, vdupLaneqF32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqLaneF32)
 */
public fun vmlsqLaneF32(a: Float32x4T, b: Float32x4T, c: Float32x2T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlsqF32(a, b, vdupqLaneF32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqLaneqF32)
 */
public fun vmlsqLaneqF32(a: Float32x4T, b: Float32x4T, c: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlsqF32(a, b, vdupqLaneqF32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsLaneS16)
 */
public fun vmlsLaneS16(a: Int16x4T, b: Int16x4T, c: Int16x4T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlsS16(a, b, vdupLaneS16(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsLaneU16)
 */
public fun vmlsLaneU16(a: Uint16x4T, b: Uint16x4T, c: Uint16x4T, lane: Int): Uint16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlsU16(a, b, vdupLaneU16(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsLaneqS16)
 */
public fun vmlsLaneqS16(a: Int16x4T, b: Int16x4T, c: Int16x8T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlsS16(a, b, vdupLaneqS16(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsLaneqU16)
 */
public fun vmlsLaneqU16(a: Uint16x4T, b: Uint16x4T, c: Uint16x8T, lane: Int): Uint16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlsU16(a, b, vdupLaneqU16(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqLaneS16)
 */
public fun vmlsqLaneS16(a: Int16x8T, b: Int16x8T, c: Int16x4T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlsqS16(a, b, vdupqLaneS16(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqLaneU16)
 */
public fun vmlsqLaneU16(a: Uint16x8T, b: Uint16x8T, c: Uint16x4T, lane: Int): Uint16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlsqU16(a, b, vdupqLaneU16(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqLaneqS16)
 */
public fun vmlsqLaneqS16(a: Int16x8T, b: Int16x8T, c: Int16x8T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlsqS16(a, b, vdupqLaneqS16(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqLaneqU16)
 */
public fun vmlsqLaneqU16(a: Uint16x8T, b: Uint16x8T, c: Uint16x8T, lane: Int): Uint16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlsqU16(a, b, vdupqLaneqU16(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsLaneS32)
 */
public fun vmlsLaneS32(a: Int32x2T, b: Int32x2T, c: Int32x2T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlsS32(a, b, vdupLaneS32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsLaneU32)
 */
public fun vmlsLaneU32(a: Uint32x2T, b: Uint32x2T, c: Uint32x2T, lane: Int): Uint32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlsU32(a, b, vdupLaneU32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsLaneqS32)
 */
public fun vmlsLaneqS32(a: Int32x2T, b: Int32x2T, c: Int32x4T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlsS32(a, b, vdupLaneqS32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsLaneqU32)
 */
public fun vmlsLaneqU32(a: Uint32x2T, b: Uint32x2T, c: Uint32x4T, lane: Int): Uint32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlsU32(a, b, vdupLaneqU32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqLaneS32)
 */
public fun vmlsqLaneS32(a: Int32x4T, b: Int32x4T, c: Int32x2T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlsqS32(a, b, vdupqLaneS32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqLaneU32)
 */
public fun vmlsqLaneU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x2T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlsqU32(a, b, vdupqLaneU32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqLaneqS32)
 */
public fun vmlsqLaneqS32(a: Int32x4T, b: Int32x4T, c: Int32x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlsqS32(a, b, vdupqLaneqS32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqLaneqU32)
 */
public fun vmlsqLaneqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlsqU32(a, b, vdupqLaneqU32(c, lane))
}

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsNF32)
 */
public fun vmlsNF32(a: Float32x2T, b: Float32x2T, c: Float): Float32x2T = vmlsF32(a, b, vdupNF32(c))

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqNF32)
 */
public fun vmlsqNF32(a: Float32x4T, b: Float32x4T, c: Float): Float32x4T = vmlsqF32(a, b, vdupqNF32(c))

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsNS16)
 */
public fun vmlsNS16(a: Int16x4T, b: Int16x4T, c: Short): Int16x4T = vmlsS16(a, b, vdupNS16(c))

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqNS16)
 */
public fun vmlsqNS16(a: Int16x8T, b: Int16x8T, c: Short): Int16x8T = vmlsqS16(a, b, vdupqNS16(c))

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsNU16)
 */
public fun vmlsNU16(a: Uint16x4T, b: Uint16x4T, c: UShort): Uint16x4T = vmlsU16(a, b, vdupNU16(c))

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqNU16)
 */
public fun vmlsqNU16(a: Uint16x8T, b: Uint16x8T, c: UShort): Uint16x8T = vmlsqU16(a, b, vdupqNU16(c))

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsNS32)
 */
public fun vmlsNS32(a: Int32x2T, b: Int32x2T, c: Int): Int32x2T = vmlsS32(a, b, vdupNS32(c))

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqNS32)
 */
public fun vmlsqNS32(a: Int32x4T, b: Int32x4T, c: Int): Int32x4T = vmlsqS32(a, b, vdupqNS32(c))

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsNU32)
 */
public fun vmlsNU32(a: Uint32x2T, b: Uint32x2T, c: UInt): Uint32x2T = vmlsU32(a, b, vdupNU32(c))

/**
 * Vector multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqNU32)
 */
public fun vmlsqNU32(a: Uint32x4T, b: Uint32x4T, c: UInt): Uint32x4T = vmlsqU32(a, b, vdupqNU32(c))

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsS8)
 */
public fun vmlsS8(a: Int8x8T, b: Int8x8T, c: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqS8)
 */
public fun vmlsqS8(a: Int8x16T, b: Int8x16T, c: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsS16)
 */
public fun vmlsS16(a: Int16x4T, b: Int16x4T, c: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqS16)
 */
public fun vmlsqS16(a: Int16x8T, b: Int16x8T, c: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsS32)
 */
public fun vmlsS32(a: Int32x2T, b: Int32x2T, c: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqS32)
 */
public fun vmlsqS32(a: Int32x4T, b: Int32x4T, c: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsU8)
 */
public fun vmlsU8(a: Uint8x8T, b: Uint8x8T, c: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqU8)
 */
public fun vmlsqU8(a: Uint8x16T, b: Uint8x16T, c: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsU16)
 */
public fun vmlsU16(a: Uint16x4T, b: Uint16x4T, c: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqU16)
 */
public fun vmlsqU16(a: Uint16x8T, b: Uint16x8T, c: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsU32)
 */
public fun vmlsU32(a: Uint32x2T, b: Uint32x2T, c: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Multiply-subtract from accumulator
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlsqU32)
 */
public fun vmlsqU32(a: Uint32x4T, b: Uint32x4T, c: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; _simdArg0 }

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslLaneS16)
 */
public fun vmlslLaneS16(a: Int32x4T, b: Int16x4T, c: Int16x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlslS16(a, b, vdupLaneS16(c, lane))
}

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslLaneqS16)
 */
public fun vmlslLaneqS16(a: Int32x4T, b: Int16x4T, c: Int16x8T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlslS16(a, b, vdupLaneqS16(c, lane))
}

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslLaneS32)
 */
public fun vmlslLaneS32(a: Int64x2T, b: Int32x2T, c: Int32x2T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlslS32(a, b, vdupLaneS32(c, lane))
}

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslLaneqS32)
 */
public fun vmlslLaneqS32(a: Int64x2T, b: Int32x2T, c: Int32x4T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlslS32(a, b, vdupLaneqS32(c, lane))
}

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslLaneU16)
 */
public fun vmlslLaneU16(a: Uint32x4T, b: Uint16x4T, c: Uint16x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlslU16(a, b, vdupLaneU16(c, lane))
}

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslLaneqU16)
 */
public fun vmlslLaneqU16(a: Uint32x4T, b: Uint16x4T, c: Uint16x8T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmlslU16(a, b, vdupLaneqU16(c, lane))
}

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslLaneU32)
 */
public fun vmlslLaneU32(a: Uint64x2T, b: Uint32x2T, c: Uint32x2T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmlslU32(a, b, vdupLaneU32(c, lane))
}

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslLaneqU32)
 */
public fun vmlslLaneqU32(a: Uint64x2T, b: Uint32x2T, c: Uint32x4T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmlslU32(a, b, vdupLaneqU32(c, lane))
}

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslNS16)
 */
public fun vmlslNS16(a: Int32x4T, b: Int16x4T, c: Short): Int32x4T = vmlslS16(a, b, vdupNS16(c))

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslNS32)
 */
public fun vmlslNS32(a: Int64x2T, b: Int32x2T, c: Int): Int64x2T = vmlslS32(a, b, vdupNS32(c))

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslNU16)
 */
public fun vmlslNU16(a: Uint32x4T, b: Uint16x4T, c: UShort): Uint32x4T = vmlslU16(a, b, vdupNU16(c))

/**
 * Vector widening multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslNU32)
 */
public fun vmlslNU32(a: Uint64x2T, b: Uint32x2T, c: UInt): Uint64x2T = vmlslU32(a, b, vdupNU32(c))

/**
 * Signed multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslS8)
 */
public fun vmlslS8(a: Int16x8T, b: Int8x8T, c: Int8x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = vmullS8(b, c); _simdArg0 }

/**
 * Signed multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslS16)
 */
public fun vmlslS16(a: Int32x4T, b: Int16x4T, c: Int16x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = vmullS16(b, c); _simdArg0 }

/**
 * Signed multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslS32)
 */
public fun vmlslS32(a: Int64x2T, b: Int32x2T, c: Int32x2T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = vmullS32(b, c); _simdArg0 }

/**
 * Unsigned multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslU8)
 */
public fun vmlslU8(a: Uint16x8T, b: Uint8x8T, c: Uint8x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = vmullU8(b, c); _simdArg0 }

/**
 * Unsigned multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslU16)
 */
public fun vmlslU16(a: Uint32x4T, b: Uint16x4T, c: Uint16x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = vmullU16(b, c); _simdArg0 }

/**
 * Unsigned multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmlslU32)
 */
public fun vmlslU32(a: Uint64x2T, b: Uint32x2T, c: Uint32x2T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = vmullU32(b, c); _simdArg0 }

/**
 * 8-bit integer matrix multiply-accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmmlaqS32)
 */
public fun vmmlaqS32(a: Int32x4T, b: Int8x16T, c: Int8x16T): Int32x4T {
    fun vmmlaqS32(a1: Int32x4T, b1: Int8x16T, c1: Int8x16T): Int32x4T = Int32x4T()
    return vmmlaqS32(a, b, c)
}

/**
 * 8-bit integer matrix multiply-accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmmlaqU32)
 */
public fun vmmlaqU32(a: Uint32x4T, b: Uint8x16T, c: Uint8x16T): Uint32x4T {
    fun vmmlaqU32(a1: Uint32x4T, b1: Uint8x16T, c1: Uint8x16T): Uint32x4T = Uint32x4T()
    return vmmlaqU32(a, b, c)
}

/**
 * Duplicate element to vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNF16)
 */
public fun vmovNF16(a: F16): Float16x4T = vdupNF16(a)

/**
 * Duplicate element to vector
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNF16)
 */
public fun vmovqNF16(a: F16): Float16x8T = vdupqNF16(a)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNF32)
 */
public fun vmovNF32(value: Float): Float32x2T = vdupNF32(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNP16)
 */
public fun vmovNP16(value: P16): Poly16x4T = vdupNP16(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNP8)
 */
public fun vmovNP8(value: P8): Poly8x8T = vdupNP8(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNS16)
 */
public fun vmovNS16(value: Short): Int16x4T = vdupNS16(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNS32)
 */
public fun vmovNS32(value: Int): Int32x2T = vdupNS32(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNS64)
 */
public fun vmovNS64(value: Long): Int64x1T = vdupNS64(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNS8)
 */
public fun vmovNS8(value: Byte): Int8x8T = vdupNS8(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNU16)
 */
public fun vmovNU16(value: UShort): Uint16x4T = vdupNU16(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNU32)
 */
public fun vmovNU32(value: UInt): Uint32x2T = vdupNU32(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNU64)
 */
public fun vmovNU64(value: ULong): Uint64x1T = vdupNU64(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovNU8)
 */
public fun vmovNU8(value: UByte): Uint8x8T = vdupNU8(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNF32)
 */
public fun vmovqNF32(value: Float): Float32x4T = vdupqNF32(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNP16)
 */
public fun vmovqNP16(value: P16): Poly16x8T = vdupqNP16(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNP8)
 */
public fun vmovqNP8(value: P8): Poly8x16T = vdupqNP8(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNS16)
 */
public fun vmovqNS16(value: Short): Int16x8T = vdupqNS16(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNS32)
 */
public fun vmovqNS32(value: Int): Int32x4T = vdupqNS32(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNS64)
 */
public fun vmovqNS64(value: Long): Int64x2T = vdupqNS64(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNS8)
 */
public fun vmovqNS8(value: Byte): Int8x16T = vdupqNS8(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNU16)
 */
public fun vmovqNU16(value: UShort): Uint16x8T = vdupqNU16(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNU32)
 */
public fun vmovqNU32(value: UInt): Uint32x4T = vdupqNU32(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNU64)
 */
public fun vmovqNU64(value: ULong): Uint64x2T = vdupqNU64(value)

/**
 * Duplicate vector element to vector or scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovqNU8)
 */
public fun vmovqNU8(value: UByte): Uint8x16T = vdupqNU8(value)

/**
 * Vector long move.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlS16)
 */
public fun vmovlS16(a: Int16x4T): Int32x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector long move.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlS32)
 */
public fun vmovlS32(a: Int32x2T): Int64x2T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector long move.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlS8)
 */
public fun vmovlS8(a: Int8x8T): Int16x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector long move.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlU16)
 */
public fun vmovlU16(a: Uint16x4T): Uint32x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector long move.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlU32)
 */
public fun vmovlU32(a: Uint32x2T): Uint64x2T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector long move.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovlU8)
 */
public fun vmovlU8(a: Uint8x8T): Uint16x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector narrow integer.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnS16)
 */
public fun vmovnS16(a: Int16x8T): Int8x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector narrow integer.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnS32)
 */
public fun vmovnS32(a: Int32x4T): Int16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector narrow integer.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnS64)
 */
public fun vmovnS64(a: Int64x2T): Int32x2T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector narrow integer.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnU16)
 */
public fun vmovnU16(a: Uint16x8T): Uint8x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector narrow integer.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnU32)
 */
public fun vmovnU32(a: Uint32x4T): Uint16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector narrow integer.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmovnU64)
 */
public fun vmovnU64(a: Uint64x2T): Uint32x2T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulF16)
 */
public fun vmulF16(a: Float16x4T, b: Float16x4T): Float16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqF16)
 */
public fun vmulqF16(a: Float16x8T, b: Float16x8T): Float16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulF32)
 */
public fun vmulF32(a: Float32x2T, b: Float32x2T): Float32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqF32)
 */
public fun vmulqF32(a: Float32x4T, b: Float32x4T): Float32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneF16)
 */
public fun vmulLaneF16(a: Float16x4T, v: Float16x4T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneF16(v, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneF16)
 */
public fun vmulqLaneF16(a: Float16x8T, v: Float16x4T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneF16(v, lane); _simdArg0 }
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneF32)
 */
public fun vmulLaneF32(a: Float32x2T, b: Float32x2T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneF32(b, lane); _simdArg0 }
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneqF32)
 */
public fun vmulLaneqF32(a: Float32x2T, b: Float32x4T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneqF32(b, lane); _simdArg0 }
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneF32)
 */
public fun vmulqLaneF32(a: Float32x4T, b: Float32x2T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneF32(b, lane); _simdArg0 }
}

/**
 * Floating-point multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneqF32)
 */
public fun vmulqLaneqF32(a: Float32x4T, b: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneqF32(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneS16)
 */
public fun vmulLaneS16(a: Int16x4T, b: Int16x4T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneS16(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneS16)
 */
public fun vmulqLaneS16(a: Int16x8T, b: Int16x4T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneS16(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneS32)
 */
public fun vmulLaneS32(a: Int32x2T, b: Int32x2T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneS32(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneS32)
 */
public fun vmulqLaneS32(a: Int32x4T, b: Int32x2T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneS32(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneU16)
 */
public fun vmulLaneU16(a: Uint16x4T, b: Uint16x4T, lane: Int): Uint16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneU16(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneU16)
 */
public fun vmulqLaneU16(a: Uint16x8T, b: Uint16x4T, lane: Int): Uint16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneU16(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneU32)
 */
public fun vmulLaneU32(a: Uint32x2T, b: Uint32x2T, lane: Int): Uint32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneU32(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneU32)
 */
public fun vmulqLaneU32(a: Uint32x4T, b: Uint32x2T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneU32(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneqS16)
 */
public fun vmulLaneqS16(a: Int16x4T, b: Int16x8T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneqS16(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneqS16)
 */
public fun vmulqLaneqS16(a: Int16x8T, b: Int16x8T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneqS16(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneqS32)
 */
public fun vmulLaneqS32(a: Int32x2T, b: Int32x4T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneqS32(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneqS32)
 */
public fun vmulqLaneqS32(a: Int32x4T, b: Int32x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneqS32(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneqU16)
 */
public fun vmulLaneqU16(a: Uint16x4T, b: Uint16x8T, lane: Int): Uint16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneqU16(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneqU16)
 */
public fun vmulqLaneqU16(a: Uint16x8T, b: Uint16x8T, lane: Int): Uint16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneqU16(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulLaneqU32)
 */
public fun vmulLaneqU32(a: Uint32x2T, b: Uint32x4T, lane: Int): Uint32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupLaneqU32(b, lane); _simdArg0 }
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqLaneqU32)
 */
public fun vmulqLaneqU32(a: Uint32x4T, b: Uint32x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqLaneqU32(b, lane); _simdArg0 }
}

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulNF16)
 */
public fun vmulNF16(a: Float16x4T, b: F16): Float16x4T = run { val _simdArg0 = a; val _simdArg1 = vdupNF16(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqNF16)
 */
public fun vmulqNF16(a: Float16x8T, b: F16): Float16x8T = run { val _simdArg0 = a; val _simdArg1 = vdupqNF16(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulNF32)
 */
public fun vmulNF32(a: Float32x2T, b: Float): Float32x2T = run { val _simdArg0 = a; val _simdArg1 = vdupNF32(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqNF32)
 */
public fun vmulqNF32(a: Float32x4T, b: Float): Float32x4T = run { val _simdArg0 = a; val _simdArg1 = vdupqNF32(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulNS16)
 */
public fun vmulNS16(a: Int16x4T, b: Short): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = vdupNS16(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqNS16)
 */
public fun vmulqNS16(a: Int16x8T, b: Short): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = vdupqNS16(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulNS32)
 */
public fun vmulNS32(a: Int32x2T, b: Int): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = vdupNS32(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqNS32)
 */
public fun vmulqNS32(a: Int32x4T, b: Int): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = vdupqNS32(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulNU16)
 */
public fun vmulNU16(a: Uint16x4T, b: UShort): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = vdupNU16(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqNU16)
 */
public fun vmulqNU16(a: Uint16x8T, b: UShort): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = vdupqNU16(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulNU32)
 */
public fun vmulNU32(a: Uint32x2T, b: UInt): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = vdupNU32(b); _simdArg0 }

/**
 * Vector multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqNU32)
 */
public fun vmulqNU32(a: Uint32x4T, b: UInt): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = vdupqNU32(b); _simdArg0 }

/**
 * Polynomial multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulP8)
 */
public fun vmulP8(a: Poly8x8T, b: Poly8x8T): Poly8x8T {
    fun vmulP8(a1: Poly8x8T, b1: Poly8x8T): Poly8x8T = Poly8x8T()
    return vmulP8(a, b)
}

/**
 * Polynomial multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqP8)
 */
public fun vmulqP8(a: Poly8x16T, b: Poly8x16T): Poly8x16T {
    fun vmulqP8(a1: Poly8x16T, b1: Poly8x16T): Poly8x16T = Poly8x16T()
    return vmulqP8(a, b)
}

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulS16)
 */
public fun vmulS16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqS16)
 */
public fun vmulqS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulU16)
 */
public fun vmulU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqU16)
 */
public fun vmulqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulS32)
 */
public fun vmulS32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqS32)
 */
public fun vmulqS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulU32)
 */
public fun vmulU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqU32)
 */
public fun vmulqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulS8)
 */
public fun vmulS8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqS8)
 */
public fun vmulqS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulU8)
 */
public fun vmulU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Multiply
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmulqU8)
 */
public fun vmulqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullLaneS16)
 */
public fun vmullLaneS16(a: Int16x4T, b: Int16x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmullS16(a, vdupLaneS16(b, lane))
}

/**
 * Vector long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullLaneqS16)
 */
public fun vmullLaneqS16(a: Int16x4T, b: Int16x8T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmullS16(a, vdupLaneqS16(b, lane))
}

/**
 * Vector long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullLaneS32)
 */
public fun vmullLaneS32(a: Int32x2T, b: Int32x2T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmullS32(a, vdupLaneS32(b, lane))
}

/**
 * Vector long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullLaneqS32)
 */
public fun vmullLaneqS32(a: Int32x2T, b: Int32x4T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmullS32(a, vdupLaneqS32(b, lane))
}

/**
 * Vector long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullLaneU16)
 */
public fun vmullLaneU16(a: Uint16x4T, b: Uint16x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmullU16(a, vdupLaneU16(b, lane))
}

/**
 * Vector long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullLaneqU16)
 */
public fun vmullLaneqU16(a: Uint16x4T, b: Uint16x8T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vmullU16(a, vdupLaneqU16(b, lane))
}

/**
 * Vector long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullLaneU32)
 */
public fun vmullLaneU32(a: Uint32x2T, b: Uint32x2T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return vmullU32(a, vdupLaneU32(b, lane))
}

/**
 * Vector long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullLaneqU32)
 */
public fun vmullLaneqU32(a: Uint32x2T, b: Uint32x4T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vmullU32(a, vdupLaneqU32(b, lane))
}

/**
 * Vector long multiply with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullNS16)
 */
public fun vmullNS16(a: Int16x4T, b: Short): Int32x4T = vmullS16(a, vdupNS16(b))

/**
 * Vector long multiply with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullNS32)
 */
public fun vmullNS32(a: Int32x2T, b: Int): Int64x2T = vmullS32(a, vdupNS32(b))

/**
 * Vector long multiply with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullNU16)
 */
public fun vmullNU16(a: Uint16x4T, b: UShort): Uint32x4T = vmullU16(a, vdupNU16(b))

/**
 * Vector long multiply with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullNU32)
 */
public fun vmullNU32(a: Uint32x2T, b: UInt): Uint64x2T = vmullU32(a, vdupNU32(b))

/**
 * Polynomial multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullP8)
 */
public fun vmullP8(a: Poly8x8T, b: Poly8x8T): Poly16x8T {
    fun vmullP8(a1: Poly8x8T, b1: Poly8x8T): Poly16x8T = Poly16x8T()
    return vmullP8(a, b)
}

/**
 * Signed multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullS16)
 */
public fun vmullS16(a: Int16x4T, b: Int16x4T): Int32x4T = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Signed multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullS32)
 */
public fun vmullS32(a: Int32x2T, b: Int32x2T): Int64x2T = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Signed multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullS8)
 */
public fun vmullS8(a: Int8x8T, b: Int8x8T): Int16x8T = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Unsigned multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullU8)
 */
public fun vmullU8(a: Uint8x8T, b: Uint8x8T): Uint16x8T = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Unsigned multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullU16)
 */
public fun vmullU16(a: Uint16x4T, b: Uint16x4T): Uint32x4T = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Unsigned multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmullU32)
 */
public fun vmullU32(a: Uint32x2T, b: Uint32x2T): Uint64x2T = run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnP8)
 */
public fun vmvnP8(a: Poly8x8T): Poly8x8T {
    val b = poly8x8T.splat(255)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnS16)
 */
public fun vmvnS16(a: Int16x4T): Int16x4T {
    val b = int16x4T.splat(-1)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnS32)
 */
public fun vmvnS32(a: Int32x2T): Int32x2T {
    val b = int32x2T.splat(-1)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnS8)
 */
public fun vmvnS8(a: Int8x8T): Int8x8T {
    val b = int8x8T.splat(-1)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnU16)
 */
public fun vmvnU16(a: Uint16x4T): Uint16x4T {
    val b = uint16x4T.splat(65535)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnU32)
 */
public fun vmvnU32(a: Uint32x2T): Uint32x2T {
    val b = uint32x2T.splat(4294967295)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnU8)
 */
public fun vmvnU8(a: Uint8x8T): Uint8x8T {
    val b = uint8x8T.splat(255)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnqP8)
 */
public fun vmvnqP8(a: Poly8x16T): Poly8x16T {
    val b = poly8x16T.splat(255)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnqS16)
 */
public fun vmvnqS16(a: Int16x8T): Int16x8T {
    val b = int16x8T.splat(-1)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnqS32)
 */
public fun vmvnqS32(a: Int32x4T): Int32x4T {
    val b = int32x4T.splat(-1)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnqS8)
 */
public fun vmvnqS8(a: Int8x16T): Int8x16T {
    val b = int8x16T.splat(-1)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnqU16)
 */
public fun vmvnqU16(a: Uint16x8T): Uint16x8T {
    val b = uint16x8T.splat(65535)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnqU32)
 */
public fun vmvnqU32(a: Uint32x4T): Uint32x4T {
    val b = uint32x4T.splat(4294967295)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Vector bitwise not.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vmvnqU8)
 */
public fun vmvnqU8(a: Uint8x16T): Uint8x16T {
    val b = uint8x16T.splat(255)
    return run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
}

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegF16)
 */
public fun vnegF16(a: Float16x4T): Float16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegqF16)
 */
public fun vnegqF16(a: Float16x8T): Float16x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegF32)
 */
public fun vnegF32(a: Float32x2T): Float32x2T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegqF32)
 */
public fun vnegqF32(a: Float32x4T): Float32x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegS8)
 */
public fun vnegS8(a: Int8x8T): Int8x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegqS8)
 */
public fun vnegqS8(a: Int8x16T): Int8x16T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegS16)
 */
public fun vnegS16(a: Int16x4T): Int16x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegqS16)
 */
public fun vnegqS16(a: Int16x8T): Int16x8T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegS32)
 */
public fun vnegS32(a: Int32x2T): Int32x2T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vnegqS32)
 */
public fun vnegqS32(a: Int32x4T): Int32x4T = run { val _simdArg0 = a; _simdArg0 }

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornS16)
 */
public fun vornS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    val c = int16x4T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornS32)
 */
public fun vornS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    val c = int32x2T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornS64)
 */
public fun vornS64(a: Int64x1T, b: Int64x1T): Int64x1T {
    val c = int64x1T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornS8)
 */
public fun vornS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    val c = int8x8T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornqS16)
 */
public fun vornqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    val c = int16x8T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornqS32)
 */
public fun vornqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    val c = int32x4T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornqS64)
 */
public fun vornqS64(a: Int64x2T, b: Int64x2T): Int64x2T {
    val c = int64x2T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornqS8)
 */
public fun vornqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    val c = int8x16T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = c; _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornU16)
 */
public fun vornU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T {
    val c = int16x4T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornU32)
 */
public fun vornU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T {
    val c = int32x2T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornU64)
 */
public fun vornU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T {
    val c = int64x1T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornU8)
 */
public fun vornU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T {
    val c = int8x8T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornqU16)
 */
public fun vornqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T {
    val c = int16x8T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornqU32)
 */
public fun vornqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T {
    val c = int32x4T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornqU64)
 */
public fun vornqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T {
    val c = int64x2T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise inclusive OR NOT
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vornqU8)
 */
public fun vornqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T {
    val c = int8x16T.splat(-1)
    return run { val _simdArg0 = run { val _simdArg0 = b; val _simdArg1 = transmute(c); _simdArg0 }; val _simdArg1 = a; _simdArg0 }
}

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrS8)
 */
public fun vorrS8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrqS8)
 */
public fun vorrqS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrS16)
 */
public fun vorrS16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrqS16)
 */
public fun vorrqS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrS32)
 */
public fun vorrS32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrqS32)
 */
public fun vorrqS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrS64)
 */
public fun vorrS64(a: Int64x1T, b: Int64x1T): Int64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrqS64)
 */
public fun vorrqS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrU8)
 */
public fun vorrU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrqU8)
 */
public fun vorrqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrU16)
 */
public fun vorrU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrqU16)
 */
public fun vorrqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrU32)
 */
public fun vorrU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrqU32)
 */
public fun vorrqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrU64)
 */
public fun vorrU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector bitwise or (immediate, inclusive)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vorrqU64)
 */
public fun vorrqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalS8)
 */
public fun vpadalS8(a: Int16x4T, b: Int8x8T): Int16x4T {
    val x: Int16x4T
    run {
        x = run { val _simdArg0 = vpaddlS8(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalqS8)
 */
public fun vpadalqS8(a: Int16x8T, b: Int8x16T): Int16x8T {
    val x: Int16x8T
    run {
        x = run { val _simdArg0 = vpaddlqS8(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalS16)
 */
public fun vpadalS16(a: Int32x2T, b: Int16x4T): Int32x2T {
    val x: Int32x2T
    run {
        x = run { val _simdArg0 = vpaddlS16(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalqS16)
 */
public fun vpadalqS16(a: Int32x4T, b: Int16x8T): Int32x4T {
    val x: Int32x4T
    run {
        x = run { val _simdArg0 = vpaddlqS16(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalS32)
 */
public fun vpadalS32(a: Int64x1T, b: Int32x2T): Int64x1T {
    val x: Int64x1T
    run {
        x = run { val _simdArg0 = vpaddlS32(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalqS32)
 */
public fun vpadalqS32(a: Int64x2T, b: Int32x4T): Int64x2T {
    val x: Int64x2T
    run {
        x = run { val _simdArg0 = vpaddlqS32(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalU8)
 */
public fun vpadalU8(a: Uint16x4T, b: Uint8x8T): Uint16x4T {
    val x: Uint16x4T
    run {
        x = run { val _simdArg0 = vpaddlU8(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalqU8)
 */
public fun vpadalqU8(a: Uint16x8T, b: Uint8x16T): Uint16x8T {
    val x: Uint16x8T
    run {
        x = run { val _simdArg0 = vpaddlqU8(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalU16)
 */
public fun vpadalU16(a: Uint32x2T, b: Uint16x4T): Uint32x2T {
    val x: Uint32x2T
    run {
        x = run { val _simdArg0 = vpaddlU16(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalqU16)
 */
public fun vpadalqU16(a: Uint32x4T, b: Uint16x8T): Uint32x4T {
    val x: Uint32x4T
    run {
        x = run { val _simdArg0 = vpaddlqU16(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalU32)
 */
public fun vpadalU32(a: Uint64x1T, b: Uint32x2T): Uint64x1T {
    val x: Uint64x1T
    run {
        x = run { val _simdArg0 = vpaddlU32(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpadalqU32)
 */
public fun vpadalqU32(a: Uint64x2T, b: Uint32x4T): Uint64x2T {
    val x: Uint64x2T
    run {
        x = run { val _simdArg0 = vpaddlqU32(b); val _simdArg1 = a; _simdArg0 }
    }
    return x
}

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddF16)
 */
public fun vpaddF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun vpaddF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return vpaddF16(a, b)
}

/**
 * Floating-point add pairwise
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddF32)
 */
public fun vpaddF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun vpaddF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return vpaddF32(a, b)
}

/**
 * Add pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddS8)
 */
public fun vpaddS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vpaddS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vpaddS8(a, b)
}

/**
 * Add pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddS16)
 */
public fun vpaddS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vpaddS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vpaddS16(a, b)
}

/**
 * Add pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddS32)
 */
public fun vpaddS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vpaddS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vpaddS32(a, b)
}

/**
 * Add pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddU8)
 */
public fun vpaddU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = transmute(vpaddS8(transmute(a), transmute(b)))

/**
 * Add pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddU16)
 */
public fun vpaddU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = transmute(vpaddS16(transmute(a), transmute(b)))

/**
 * Add pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddU32)
 */
public fun vpaddU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = transmute(vpaddS32(transmute(a), transmute(b)))

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlS8)
 */
public fun vpaddlS8(a: Int8x8T): Int16x4T {
    fun vpaddlS8(a1: Int8x8T): Int16x4T = Int16x4T()
    return vpaddlS8(a)
}

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlqS8)
 */
public fun vpaddlqS8(a: Int8x16T): Int16x8T {
    fun vpaddlqS8(a1: Int8x16T): Int16x8T = Int16x8T()
    return vpaddlqS8(a)
}

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlS16)
 */
public fun vpaddlS16(a: Int16x4T): Int32x2T {
    fun vpaddlS16(a1: Int16x4T): Int32x2T = Int32x2T()
    return vpaddlS16(a)
}

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlqS16)
 */
public fun vpaddlqS16(a: Int16x8T): Int32x4T {
    fun vpaddlqS16(a1: Int16x8T): Int32x4T = Int32x4T()
    return vpaddlqS16(a)
}

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlS32)
 */
public fun vpaddlS32(a: Int32x2T): Int64x1T {
    fun vpaddlS32(a1: Int32x2T): Int64x1T = Int64x1T()
    return vpaddlS32(a)
}

/**
 * Signed Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlqS32)
 */
public fun vpaddlqS32(a: Int32x4T): Int64x2T {
    fun vpaddlqS32(a1: Int32x4T): Int64x2T = Int64x2T()
    return vpaddlqS32(a)
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlU8)
 */
public fun vpaddlU8(a: Uint8x8T): Uint16x4T {
    fun vpaddlU8(a1: Uint8x8T): Uint16x4T = Uint16x4T()
    return vpaddlU8(a)
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlqU8)
 */
public fun vpaddlqU8(a: Uint8x16T): Uint16x8T {
    fun vpaddlqU8(a1: Uint8x16T): Uint16x8T = Uint16x8T()
    return vpaddlqU8(a)
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlU16)
 */
public fun vpaddlU16(a: Uint16x4T): Uint32x2T {
    fun vpaddlU16(a1: Uint16x4T): Uint32x2T = Uint32x2T()
    return vpaddlU16(a)
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlqU16)
 */
public fun vpaddlqU16(a: Uint16x8T): Uint32x4T {
    fun vpaddlqU16(a1: Uint16x8T): Uint32x4T = Uint32x4T()
    return vpaddlqU16(a)
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlU32)
 */
public fun vpaddlU32(a: Uint32x2T): Uint64x1T {
    fun vpaddlU32(a1: Uint32x2T): Uint64x1T = Uint64x1T()
    return vpaddlU32(a)
}

/**
 * Unsigned Add and Accumulate Long Pairwise.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpaddlqU32)
 */
public fun vpaddlqU32(a: Uint32x4T): Uint64x2T {
    fun vpaddlqU32(a1: Uint32x4T): Uint64x2T = Uint64x2T()
    return vpaddlqU32(a)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxF32)
 */
public fun vpmaxF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun vpmaxF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return vpmaxF32(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxS8)
 */
public fun vpmaxS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vpmaxS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vpmaxS8(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxS16)
 */
public fun vpmaxS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vpmaxS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vpmaxS16(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxS32)
 */
public fun vpmaxS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vpmaxS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vpmaxS32(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxU8)
 */
public fun vpmaxU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T {
    fun vpmaxU8(a1: Uint8x8T, b1: Uint8x8T): Uint8x8T = Uint8x8T()
    return vpmaxU8(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxU16)
 */
public fun vpmaxU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T {
    fun vpmaxU16(a1: Uint16x4T, b1: Uint16x4T): Uint16x4T = Uint16x4T()
    return vpmaxU16(a, b)
}

/**
 * Folding maximum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpmaxU32)
 */
public fun vpmaxU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T {
    fun vpmaxU32(a1: Uint32x2T, b1: Uint32x2T): Uint32x2T = Uint32x2T()
    return vpmaxU32(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminF32)
 */
public fun vpminF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun vpminF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return vpminF32(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminS8)
 */
public fun vpminS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vpminS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vpminS8(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminS16)
 */
public fun vpminS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vpminS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vpminS16(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminS32)
 */
public fun vpminS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vpminS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vpminS32(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminU8)
 */
public fun vpminU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T {
    fun vpminU8(a1: Uint8x8T, b1: Uint8x8T): Uint8x8T = Uint8x8T()
    return vpminU8(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminU16)
 */
public fun vpminU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T {
    fun vpminU16(a1: Uint16x4T, b1: Uint16x4T): Uint16x4T = Uint16x4T()
    return vpminU16(a, b)
}

/**
 * Folding minimum of adjacent pairs
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vpminU32)
 */
public fun vpminU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T {
    fun vpminU32(a1: Uint32x2T, b1: Uint32x2T): Uint32x2T = Uint32x2T()
    return vpminU32(a, b)
}

/**
 * Signed saturating Absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabsS8)
 */
public fun vqabsS8(a: Int8x8T): Int8x8T {
    fun vqabsS8(a1: Int8x8T): Int8x8T = Int8x8T()
    return vqabsS8(a)
}

/**
 * Signed saturating Absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabsqS8)
 */
public fun vqabsqS8(a: Int8x16T): Int8x16T {
    fun vqabsqS8(a1: Int8x16T): Int8x16T = Int8x16T()
    return vqabsqS8(a)
}

/**
 * Signed saturating Absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabsS16)
 */
public fun vqabsS16(a: Int16x4T): Int16x4T {
    fun vqabsS16(a1: Int16x4T): Int16x4T = Int16x4T()
    return vqabsS16(a)
}

/**
 * Signed saturating Absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabsqS16)
 */
public fun vqabsqS16(a: Int16x8T): Int16x8T {
    fun vqabsqS16(a1: Int16x8T): Int16x8T = Int16x8T()
    return vqabsqS16(a)
}

/**
 * Signed saturating Absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabsS32)
 */
public fun vqabsS32(a: Int32x2T): Int32x2T {
    fun vqabsS32(a1: Int32x2T): Int32x2T = Int32x2T()
    return vqabsS32(a)
}

/**
 * Signed saturating Absolute value
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqabsqS32)
 */
public fun vqabsqS32(a: Int32x4T): Int32x4T {
    fun vqabsqS32(a1: Int32x4T): Int32x4T = Int32x4T()
    return vqabsqS32(a)
}

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddS8)
 */
public fun vqaddS8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddqS8)
 */
public fun vqaddqS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddS16)
 */
public fun vqaddS16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddqS16)
 */
public fun vqaddqS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddS32)
 */
public fun vqaddS32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddqS32)
 */
public fun vqaddqS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddS64)
 */
public fun vqaddS64(a: Int64x1T, b: Int64x1T): Int64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddqS64)
 */
public fun vqaddqS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddU8)
 */
public fun vqaddU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddqU8)
 */
public fun vqaddqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddU16)
 */
public fun vqaddU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddqU16)
 */
public fun vqaddqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddU32)
 */
public fun vqaddU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddqU32)
 */
public fun vqaddqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddU64)
 */
public fun vqaddU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqaddqU64)
 */
public fun vqaddqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Vector widening saturating doubling multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalLaneS16)
 */
public fun vqdmlalLaneS16(a: Int32x4T, b: Int16x4T, c: Int16x4T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vqaddqS32(a, vqdmullLaneS16(b, c, n))
}

/**
 * Vector widening saturating doubling multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalLaneS32)
 */
public fun vqdmlalLaneS32(a: Int64x2T, b: Int32x2T, c: Int32x2T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return vqaddqS64(a, vqdmullLaneS32(b, c, n))
}

/**
 * Vector widening saturating doubling multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalNS16)
 */
public fun vqdmlalNS16(a: Int32x4T, b: Int16x4T, c: Short): Int32x4T = vqaddqS32(a, vqdmullNS16(b, c))

/**
 * Vector widening saturating doubling multiply accumulate with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalNS32)
 */
public fun vqdmlalNS32(a: Int64x2T, b: Int32x2T, c: Int): Int64x2T = vqaddqS64(a, vqdmullNS32(b, c))

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalS16)
 */
public fun vqdmlalS16(a: Int32x4T, b: Int16x4T, c: Int16x4T): Int32x4T = vqaddqS32(a, vqdmullS16(b, c))

/**
 * Signed saturating doubling multiply-add long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlalS32)
 */
public fun vqdmlalS32(a: Int64x2T, b: Int32x2T, c: Int32x2T): Int64x2T = vqaddqS64(a, vqdmullS32(b, c))

/**
 * Vector widening saturating doubling multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslLaneS16)
 */
public fun vqdmlslLaneS16(a: Int32x4T, b: Int16x4T, c: Int16x4T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    return vqsubqS32(a, vqdmullLaneS16(b, c, n))
}

/**
 * Vector widening saturating doubling multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslLaneS32)
 */
public fun vqdmlslLaneS32(a: Int64x2T, b: Int32x2T, c: Int32x2T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    return vqsubqS64(a, vqdmullLaneS32(b, c, n))
}

/**
 * Vector widening saturating doubling multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslNS16)
 */
public fun vqdmlslNS16(a: Int32x4T, b: Int16x4T, c: Short): Int32x4T = vqsubqS32(a, vqdmullNS16(b, c))

/**
 * Vector widening saturating doubling multiply subtract with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslNS32)
 */
public fun vqdmlslNS32(a: Int64x2T, b: Int32x2T, c: Int): Int64x2T = vqsubqS64(a, vqdmullNS32(b, c))

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslS16)
 */
public fun vqdmlslS16(a: Int32x4T, b: Int16x4T, c: Int16x4T): Int32x4T = vqsubqS32(a, vqdmullS16(b, c))

/**
 * Signed saturating doubling multiply-subtract long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmlslS32)
 */
public fun vqdmlslS32(a: Int64x2T, b: Int32x2T, c: Int32x2T): Int64x2T = vqsubqS64(a, vqdmullS32(b, c))

/**
 * Vector saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhLaneqS16)
 */
public fun vqdmulhLaneqS16(a: Int16x4T, b: Int16x8T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vqdmulhS16(a, vdupNS16(vgetqLaneS16(b, lane)))
}

/**
 * Vector saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhqLaneqS16)
 */
public fun vqdmulhqLaneqS16(a: Int16x8T, b: Int16x8T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return vqdmulhqS16(a, vdupqNS16(vgetqLaneS16(b, lane)))
}

/**
 * Vector saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhLaneqS32)
 */
public fun vqdmulhLaneqS32(a: Int32x2T, b: Int32x4T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqdmulhS32(a, vdupNS32(vgetqLaneS32(b, lane)))
}

/**
 * Vector saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhqLaneqS32)
 */
public fun vqdmulhqLaneqS32(a: Int32x4T, b: Int32x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return vqdmulhqS32(a, vdupqNS32(vgetqLaneS32(b, lane)))
}

/**
 * Vector saturating doubling multiply high with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhNS16)
 */
public fun vqdmulhNS16(a: Int16x4T, b: Short): Int16x4T {
    val b1: Int16x4T = vdupNS16(b)
    return vqdmulhS16(a, b1)
}

/**
 * Vector saturating doubling multiply high with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhqNS16)
 */
public fun vqdmulhqNS16(a: Int16x8T, b: Short): Int16x8T {
    val b1: Int16x8T = vdupqNS16(b)
    return vqdmulhqS16(a, b1)
}

/**
 * Vector saturating doubling multiply high with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhNS32)
 */
public fun vqdmulhNS32(a: Int32x2T, b: Int): Int32x2T {
    val b1: Int32x2T = vdupNS32(b)
    return vqdmulhS32(a, b1)
}

/**
 * Vector saturating doubling multiply high with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhqNS32)
 */
public fun vqdmulhqNS32(a: Int32x4T, b: Int): Int32x4T {
    val b1: Int32x4T = vdupqNS32(b)
    return vqdmulhqS32(a, b1)
}

/**
 * Signed saturating doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhS16)
 */
public fun vqdmulhS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vqdmulhS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vqdmulhS16(a, b)
}

/**
 * Signed saturating doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhqS16)
 */
public fun vqdmulhqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun vqdmulhqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return vqdmulhqS16(a, b)
}

/**
 * Signed saturating doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhS32)
 */
public fun vqdmulhS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vqdmulhS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vqdmulhS32(a, b)
}

/**
 * Signed saturating doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmulhqS32)
 */
public fun vqdmulhqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun vqdmulhqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return vqdmulhqS32(a, b)
}

/**
 * Vector saturating doubling long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullLaneS16)
 */
public fun vqdmullLaneS16(a: Int16x4T, b: Int16x4T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 2)) { "N doesn't fit in 2 bits (unsigned)" }
    }
    val b1 = vdupLaneS16(b, n)
    return vqdmullS16(a, b1)
}

/**
 * Vector saturating doubling long multiply by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullLaneS32)
 */
public fun vqdmullLaneS32(a: Int32x2T, b: Int32x2T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 1)) { "N doesn't fit in 1 bits (unsigned)" }
    }
    val b1 = vdupLaneS32(b, n)
    return vqdmullS32(a, b1)
}

/**
 * Vector saturating doubling long multiply with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullNS16)
 */
public fun vqdmullNS16(a: Int16x4T, b: Short): Int32x4T = vqdmullS16(a, vdupNS16(b))

/**
 * Vector saturating doubling long multiply with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullNS32)
 */
public fun vqdmullNS32(a: Int32x2T, b: Int): Int64x2T = vqdmullS32(a, vdupNS32(b))

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullS16)
 */
public fun vqdmullS16(a: Int16x4T, b: Int16x4T): Int32x4T {
    fun vqdmullS16(a1: Int16x4T, b1: Int16x4T): Int32x4T = Int32x4T()
    return vqdmullS16(a, b)
}

/**
 * Signed saturating doubling multiply long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqdmullS32)
 */
public fun vqdmullS32(a: Int32x2T, b: Int32x2T): Int64x2T {
    fun vqdmullS32(a1: Int32x2T, b1: Int32x2T): Int64x2T = Int64x2T()
    return vqdmullS32(a, b)
}

/**
 * Signed saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnS16)
 */
public fun vqmovnS16(a: Int16x8T): Int8x8T {
    fun vqmovnS16(a1: Int16x8T): Int8x8T = Int8x8T()
    return vqmovnS16(a)
}

/**
 * Signed saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnS32)
 */
public fun vqmovnS32(a: Int32x4T): Int16x4T {
    fun vqmovnS32(a1: Int32x4T): Int16x4T = Int16x4T()
    return vqmovnS32(a)
}

/**
 * Signed saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnS64)
 */
public fun vqmovnS64(a: Int64x2T): Int32x2T {
    fun vqmovnS64(a1: Int64x2T): Int32x2T = Int32x2T()
    return vqmovnS64(a)
}

/**
 * Unsigned saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnU16)
 */
public fun vqmovnU16(a: Uint16x8T): Uint8x8T {
    fun vqmovnU16(a1: Uint16x8T): Uint8x8T = Uint8x8T()
    return vqmovnU16(a)
}

/**
 * Unsigned saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnU32)
 */
public fun vqmovnU32(a: Uint32x4T): Uint16x4T {
    fun vqmovnU32(a1: Uint32x4T): Uint16x4T = Uint16x4T()
    return vqmovnU32(a)
}

/**
 * Unsigned saturating extract narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovnU64)
 */
public fun vqmovnU64(a: Uint64x2T): Uint32x2T {
    fun vqmovnU64(a1: Uint64x2T): Uint32x2T = Uint32x2T()
    return vqmovnU64(a)
}

/**
 * Signed saturating extract unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovunS16)
 */
public fun vqmovunS16(a: Int16x8T): Uint8x8T {
    fun vqmovunS16(a1: Int16x8T): Uint8x8T = Uint8x8T()
    return vqmovunS16(a)
}

/**
 * Signed saturating extract unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovunS32)
 */
public fun vqmovunS32(a: Int32x4T): Uint16x4T {
    fun vqmovunS32(a1: Int32x4T): Uint16x4T = Uint16x4T()
    return vqmovunS32(a)
}

/**
 * Signed saturating extract unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqmovunS64)
 */
public fun vqmovunS64(a: Int64x2T): Uint32x2T {
    fun vqmovunS64(a1: Int64x2T): Uint32x2T = Uint32x2T()
    return vqmovunS64(a)
}

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegS8)
 */
public fun vqnegS8(a: Int8x8T): Int8x8T {
    fun vqnegS8(a1: Int8x8T): Int8x8T = Int8x8T()
    return vqnegS8(a)
}

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegqS8)
 */
public fun vqnegqS8(a: Int8x16T): Int8x16T {
    fun vqnegqS8(a1: Int8x16T): Int8x16T = Int8x16T()
    return vqnegqS8(a)
}

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegS16)
 */
public fun vqnegS16(a: Int16x4T): Int16x4T {
    fun vqnegS16(a1: Int16x4T): Int16x4T = Int16x4T()
    return vqnegS16(a)
}

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegqS16)
 */
public fun vqnegqS16(a: Int16x8T): Int16x8T {
    fun vqnegqS16(a1: Int16x8T): Int16x8T = Int16x8T()
    return vqnegqS16(a)
}

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegS32)
 */
public fun vqnegS32(a: Int32x2T): Int32x2T {
    fun vqnegS32(a1: Int32x2T): Int32x2T = Int32x2T()
    return vqnegS32(a)
}

/**
 * Signed saturating negate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqnegqS32)
 */
public fun vqnegqS32(a: Int32x4T): Int32x4T {
    fun vqnegqS32(a1: Int32x4T): Int32x4T = Int32x4T()
    return vqnegqS32(a)
}

/**
 * Vector rounding saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhLaneS16)
 */
public fun vqrdmulhLaneS16(a: Int16x4T, b: Int16x4T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val b1 = vdupLaneS16(b, lane)
    return vqrdmulhS16(a, b1)
}

/**
 * Vector rounding saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhLaneS32)
 */
public fun vqrdmulhLaneS32(a: Int32x2T, b: Int32x2T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val b1 = vdupLaneS32(b, lane)
    return vqrdmulhS32(a, b1)
}

/**
 * Vector rounding saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhLaneqS16)
 */
public fun vqrdmulhLaneqS16(a: Int16x4T, b: Int16x8T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    val b1 = vdupLaneqS16(b, lane)
    return vqrdmulhS16(a, b1)
}

/**
 * Vector rounding saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhLaneqS32)
 */
public fun vqrdmulhLaneqS32(a: Int32x2T, b: Int32x4T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val b1 = vdupLaneqS32(b, lane)
    return vqrdmulhS32(a, b1)
}

/**
 * Vector rounding saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhqLaneS16)
 */
public fun vqrdmulhqLaneS16(a: Int16x8T, b: Int16x4T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val b1 = vdupqLaneS16(b, lane)
    return vqrdmulhqS16(a, b1)
}

/**
 * Vector rounding saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhqLaneS32)
 */
public fun vqrdmulhqLaneS32(a: Int32x4T, b: Int32x2T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val b1 = vdupqLaneS32(b, lane)
    return vqrdmulhqS32(a, b1)
}

/**
 * Vector rounding saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhqLaneqS16)
 */
public fun vqrdmulhqLaneqS16(a: Int16x8T, b: Int16x8T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    val b1 = vdupqLaneqS16(b, lane)
    return vqrdmulhqS16(a, b1)
}

/**
 * Vector rounding saturating doubling multiply high by scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhqLaneqS32)
 */
public fun vqrdmulhqLaneqS32(a: Int32x4T, b: Int32x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val b1 = vdupqLaneqS32(b, lane)
    return vqrdmulhqS32(a, b1)
}

/**
 * Vector saturating rounding doubling multiply high with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhNS16)
 */
public fun vqrdmulhNS16(a: Int16x4T, b: Short): Int16x4T = vqrdmulhS16(a, vdupNS16(b))

/**
 * Vector saturating rounding doubling multiply high with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhqNS16)
 */
public fun vqrdmulhqNS16(a: Int16x8T, b: Short): Int16x8T = vqrdmulhqS16(a, vdupqNS16(b))

/**
 * Vector saturating rounding doubling multiply high with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhNS32)
 */
public fun vqrdmulhNS32(a: Int32x2T, b: Int): Int32x2T = vqrdmulhS32(a, vdupNS32(b))

/**
 * Vector saturating rounding doubling multiply high with scalar
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhqNS32)
 */
public fun vqrdmulhqNS32(a: Int32x4T, b: Int): Int32x4T = vqrdmulhqS32(a, vdupqNS32(b))

/**
 * Signed saturating rounding doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhS16)
 */
public fun vqrdmulhS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vqrdmulhS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vqrdmulhS16(a, b)
}

/**
 * Signed saturating rounding doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhqS16)
 */
public fun vqrdmulhqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun vqrdmulhqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return vqrdmulhqS16(a, b)
}

/**
 * Signed saturating rounding doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhS32)
 */
public fun vqrdmulhS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vqrdmulhS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vqrdmulhS32(a, b)
}

/**
 * Signed saturating rounding doubling multiply returning high half
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrdmulhqS32)
 */
public fun vqrdmulhqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun vqrdmulhqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return vqrdmulhqS32(a, b)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlS8)
 */
public fun vqrshlS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vqrshlS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vqrshlS8(a, b)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlqS8)
 */
public fun vqrshlqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    fun vqrshlqS8(a1: Int8x16T, b1: Int8x16T): Int8x16T = Int8x16T()
    return vqrshlqS8(a, b)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlS16)
 */
public fun vqrshlS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vqrshlS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vqrshlS16(a, b)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlqS16)
 */
public fun vqrshlqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun vqrshlqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return vqrshlqS16(a, b)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlS32)
 */
public fun vqrshlS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vqrshlS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vqrshlS32(a, b)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlqS32)
 */
public fun vqrshlqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun vqrshlqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return vqrshlqS32(a, b)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlS64)
 */
public fun vqrshlS64(a: Int64x1T, b: Int64x1T): Int64x1T {
    fun vqrshlS64(a1: Int64x1T, b1: Int64x1T): Int64x1T = Int64x1T()
    return vqrshlS64(a, b)
}

/**
 * Signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlqS64)
 */
public fun vqrshlqS64(a: Int64x2T, b: Int64x2T): Int64x2T {
    fun vqrshlqS64(a1: Int64x2T, b1: Int64x2T): Int64x2T = Int64x2T()
    return vqrshlqS64(a, b)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlU8)
 */
public fun vqrshlU8(a: Uint8x8T, b: Int8x8T): Uint8x8T {
    fun vqrshlU8(a1: Uint8x8T, b1: Int8x8T): Uint8x8T = Uint8x8T()
    return vqrshlU8(a, b)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlqU8)
 */
public fun vqrshlqU8(a: Uint8x16T, b: Int8x16T): Uint8x16T {
    fun vqrshlqU8(a1: Uint8x16T, b1: Int8x16T): Uint8x16T = Uint8x16T()
    return vqrshlqU8(a, b)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlU16)
 */
public fun vqrshlU16(a: Uint16x4T, b: Int16x4T): Uint16x4T {
    fun vqrshlU16(a1: Uint16x4T, b1: Int16x4T): Uint16x4T = Uint16x4T()
    return vqrshlU16(a, b)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlqU16)
 */
public fun vqrshlqU16(a: Uint16x8T, b: Int16x8T): Uint16x8T {
    fun vqrshlqU16(a1: Uint16x8T, b1: Int16x8T): Uint16x8T = Uint16x8T()
    return vqrshlqU16(a, b)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlU32)
 */
public fun vqrshlU32(a: Uint32x2T, b: Int32x2T): Uint32x2T {
    fun vqrshlU32(a1: Uint32x2T, b1: Int32x2T): Uint32x2T = Uint32x2T()
    return vqrshlU32(a, b)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlqU32)
 */
public fun vqrshlqU32(a: Uint32x4T, b: Int32x4T): Uint32x4T {
    fun vqrshlqU32(a1: Uint32x4T, b1: Int32x4T): Uint32x4T = Uint32x4T()
    return vqrshlqU32(a, b)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlU64)
 */
public fun vqrshlU64(a: Uint64x1T, b: Int64x1T): Uint64x1T {
    fun vqrshlU64(a1: Uint64x1T, b1: Int64x1T): Uint64x1T = Uint64x1T()
    return vqrshlU64(a, b)
}

/**
 * Unsigned signed saturating rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshlqU64)
 */
public fun vqrshlqU64(a: Uint64x2T, b: Int64x2T): Uint64x2T {
    fun vqrshlqU64(a1: Uint64x2T, b1: Int64x2T): Uint64x2T = Uint64x2T()
    return vqrshlqU64(a, b)
}

/**
 * Signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnNS16)
 */
public fun vqrshrnNS16(a: Int16x8T, n: Int): Int8x8T {
    fun vqrshrnNS16(a1: Int16x8T, n: Int): Int8x8T = Int8x8T()
    run {
        check(n >= 1 && n <= 8)
    }
    return vqrshrnNS16(a, n)
}

/**
 * Signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnNS32)
 */
public fun vqrshrnNS32(a: Int32x4T, n: Int): Int16x4T {
    fun vqrshrnNS32(a1: Int32x4T, n: Int): Int16x4T = Int16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vqrshrnNS32(a, n)
}

/**
 * Signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnNS64)
 */
public fun vqrshrnNS64(a: Int64x2T, n: Int): Int32x2T {
    fun vqrshrnNS64(a1: Int64x2T, n: Int): Int32x2T = Int32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vqrshrnNS64(a, n)
}

/**
 * Unsigned signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnNU16)
 */
public fun vqrshrnNU16(a: Uint16x8T, n: Int): Uint8x8T {
    fun vqrshrnNU16(a1: Uint16x8T, n: Int): Uint8x8T = Uint8x8T()
    run {
        check(n >= 1 && n <= 8)
    }
    return vqrshrnNU16(a, n)
}

/**
 * Unsigned signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnNU32)
 */
public fun vqrshrnNU32(a: Uint32x4T, n: Int): Uint16x4T {
    fun vqrshrnNU32(a1: Uint32x4T, n: Int): Uint16x4T = Uint16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vqrshrnNU32(a, n)
}

/**
 * Unsigned signed saturating rounded shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrnNU64)
 */
public fun vqrshrnNU64(a: Uint64x2T, n: Int): Uint32x2T {
    fun vqrshrnNU64(a1: Uint64x2T, n: Int): Uint32x2T = Uint32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vqrshrnNU64(a, n)
}

/**
 * Signed saturating rounded shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrunNS16)
 */
public fun vqrshrunNS16(a: Int16x8T, n: Int): Uint8x8T {
    fun vqrshrunNS16(a1: Int16x8T, n: Int): Uint8x8T = Uint8x8T()
    run {
        check(n >= 1 && n <= 8)
    }
    return vqrshrunNS16(a, n)
}

/**
 * Signed saturating rounded shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrunNS32)
 */
public fun vqrshrunNS32(a: Int32x4T, n: Int): Uint16x4T {
    fun vqrshrunNS32(a1: Int32x4T, n: Int): Uint16x4T = Uint16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vqrshrunNS32(a, n)
}

/**
 * Signed saturating rounded shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqrshrunNS64)
 */
public fun vqrshrunNS64(a: Int64x2T, n: Int): Uint32x2T {
    fun vqrshrunNS64(a1: Int64x2T, n: Int): Uint32x2T = Uint32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vqrshrunNS64(a, n)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlNS8)
 */
public fun vqshlNS8(a: Int8x8T, n: Int): Int8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vqshlS8(a, vdupNS8(n.toByte()))
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqNS8)
 */
public fun vqshlqNS8(a: Int8x16T, n: Int): Int8x16T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vqshlqS8(a, vdupqNS8(n.toByte()))
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlNS16)
 */
public fun vqshlNS16(a: Int16x4T, n: Int): Int16x4T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vqshlS16(a, vdupNS16(n.toShort()))
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqNS16)
 */
public fun vqshlqNS16(a: Int16x8T, n: Int): Int16x8T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vqshlqS16(a, vdupqNS16(n.toShort()))
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlNS32)
 */
public fun vqshlNS32(a: Int32x2T, n: Int): Int32x2T {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return vqshlS32(a, vdupNS32(n))
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqNS32)
 */
public fun vqshlqNS32(a: Int32x4T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return vqshlqS32(a, vdupqNS32(n))
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlNS64)
 */
public fun vqshlNS64(a: Int64x1T, n: Int): Int64x1T {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return vqshlS64(a, vdupNS64(n.toLong()))
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqNS64)
 */
public fun vqshlqNS64(a: Int64x2T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return vqshlqS64(a, vdupqNS64(n.toLong()))
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlNU8)
 */
public fun vqshlNU8(a: Uint8x8T, n: Int): Uint8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vqshlU8(a, vdupNS8(n.toByte()))
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqNU8)
 */
public fun vqshlqNU8(a: Uint8x16T, n: Int): Uint8x16T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vqshlqU8(a, vdupqNS8(n.toByte()))
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlNU16)
 */
public fun vqshlNU16(a: Uint16x4T, n: Int): Uint16x4T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vqshlU16(a, vdupNS16(n.toShort()))
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqNU16)
 */
public fun vqshlqNU16(a: Uint16x8T, n: Int): Uint16x8T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vqshlqU16(a, vdupqNS16(n.toShort()))
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlNU32)
 */
public fun vqshlNU32(a: Uint32x2T, n: Int): Uint32x2T {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return vqshlU32(a, vdupNS32(n))
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqNU32)
 */
public fun vqshlqNU32(a: Uint32x4T, n: Int): Uint32x4T {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return vqshlqU32(a, vdupqNS32(n))
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlNU64)
 */
public fun vqshlNU64(a: Uint64x1T, n: Int): Uint64x1T {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return vqshlU64(a, vdupNS64(n.toLong()))
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqNU64)
 */
public fun vqshlqNU64(a: Uint64x2T, n: Int): Uint64x2T {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return vqshlqU64(a, vdupqNS64(n.toLong()))
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlS8)
 */
public fun vqshlS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vqshlS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vqshlS8(a, b)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqS8)
 */
public fun vqshlqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    fun vqshlqS8(a1: Int8x16T, b1: Int8x16T): Int8x16T = Int8x16T()
    return vqshlqS8(a, b)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlS16)
 */
public fun vqshlS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vqshlS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vqshlS16(a, b)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqS16)
 */
public fun vqshlqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun vqshlqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return vqshlqS16(a, b)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlS32)
 */
public fun vqshlS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vqshlS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vqshlS32(a, b)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqS32)
 */
public fun vqshlqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun vqshlqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return vqshlqS32(a, b)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlS64)
 */
public fun vqshlS64(a: Int64x1T, b: Int64x1T): Int64x1T {
    fun vqshlS64(a1: Int64x1T, b1: Int64x1T): Int64x1T = Int64x1T()
    return vqshlS64(a, b)
}

/**
 * Signed saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqS64)
 */
public fun vqshlqS64(a: Int64x2T, b: Int64x2T): Int64x2T {
    fun vqshlqS64(a1: Int64x2T, b1: Int64x2T): Int64x2T = Int64x2T()
    return vqshlqS64(a, b)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlU8)
 */
public fun vqshlU8(a: Uint8x8T, b: Int8x8T): Uint8x8T {
    fun vqshlU8(a1: Uint8x8T, b1: Int8x8T): Uint8x8T = Uint8x8T()
    return vqshlU8(a, b)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqU8)
 */
public fun vqshlqU8(a: Uint8x16T, b: Int8x16T): Uint8x16T {
    fun vqshlqU8(a1: Uint8x16T, b1: Int8x16T): Uint8x16T = Uint8x16T()
    return vqshlqU8(a, b)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlU16)
 */
public fun vqshlU16(a: Uint16x4T, b: Int16x4T): Uint16x4T {
    fun vqshlU16(a1: Uint16x4T, b1: Int16x4T): Uint16x4T = Uint16x4T()
    return vqshlU16(a, b)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqU16)
 */
public fun vqshlqU16(a: Uint16x8T, b: Int16x8T): Uint16x8T {
    fun vqshlqU16(a1: Uint16x8T, b1: Int16x8T): Uint16x8T = Uint16x8T()
    return vqshlqU16(a, b)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlU32)
 */
public fun vqshlU32(a: Uint32x2T, b: Int32x2T): Uint32x2T {
    fun vqshlU32(a1: Uint32x2T, b1: Int32x2T): Uint32x2T = Uint32x2T()
    return vqshlU32(a, b)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqU32)
 */
public fun vqshlqU32(a: Uint32x4T, b: Int32x4T): Uint32x4T {
    fun vqshlqU32(a1: Uint32x4T, b1: Int32x4T): Uint32x4T = Uint32x4T()
    return vqshlqU32(a, b)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlU64)
 */
public fun vqshlU64(a: Uint64x1T, b: Int64x1T): Uint64x1T {
    fun vqshlU64(a1: Uint64x1T, b1: Int64x1T): Uint64x1T = Uint64x1T()
    return vqshlU64(a, b)
}

/**
 * Unsigned saturating shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshlqU64)
 */
public fun vqshlqU64(a: Uint64x2T, b: Int64x2T): Uint64x2T {
    fun vqshlqU64(a1: Uint64x2T, b1: Int64x2T): Uint64x2T = Uint64x2T()
    return vqshlqU64(a, b)
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshluNS8)
 */
public fun vqshluNS8(a: Int8x8T, n: Int): Uint8x8T {
    fun vqshluNS8(a1: Int8x8T, n: Int8x8T): Uint8x8T = Uint8x8T()
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vqshluNS8(a, int8x8T(ByteArray(8) { n.toByte() }))
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshluqNS8)
 */
public fun vqshluqNS8(a: Int8x16T, n: Int): Uint8x16T {
    fun vqshluqNS8(a1: Int8x16T, n: Int8x16T): Uint8x16T = Uint8x16T()
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return vqshluqNS8(a, int8x16T(ByteArray(16) { n.toByte() }))
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshluNS16)
 */
public fun vqshluNS16(a: Int16x4T, n: Int): Uint16x4T {
    fun vqshluNS16(a1: Int16x4T, n: Int16x4T): Uint16x4T = Uint16x4T()
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vqshluNS16(a, int16x4T(ShortArray(4) { n.toShort() }))
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshluqNS16)
 */
public fun vqshluqNS16(a: Int16x8T, n: Int): Uint16x8T {
    fun vqshluqNS16(a1: Int16x8T, n: Int16x8T): Uint16x8T = Uint16x8T()
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return vqshluqNS16(a, int16x8T(ShortArray(8) { n.toShort() }))
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshluNS32)
 */
public fun vqshluNS32(a: Int32x2T, n: Int): Uint32x2T {
    fun vqshluNS32(a1: Int32x2T, n: Int32x2T): Uint32x2T = Uint32x2T()
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return vqshluNS32(a, int32x2T(IntArray(2) { n }))
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshluqNS32)
 */
public fun vqshluqNS32(a: Int32x4T, n: Int): Uint32x4T {
    fun vqshluqNS32(a1: Int32x4T, n: Int32x4T): Uint32x4T = Uint32x4T()
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return vqshluqNS32(a, int32x4T(IntArray(4) { n }))
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshluNS64)
 */
public fun vqshluNS64(a: Int64x1T, n: Int): Uint64x1T {
    fun vqshluNS64(a1: Int64x1T, n: Int64x1T): Uint64x1T = Uint64x1T()
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return vqshluNS64(a, int64x1T(longArrayOf(n.toLong())))
}

/**
 * Signed saturating shift left unsigned
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshluqNS64)
 */
public fun vqshluqNS64(a: Int64x2T, n: Int): Uint64x2T {
    fun vqshluqNS64(a1: Int64x2T, n: Int64x2T): Uint64x2T = Uint64x2T()
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return vqshluqNS64(a, int64x2T(LongArray(2) { n.toLong() }))
}

/**
 * Signed saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnNS16)
 */
public fun vqshrnNS16(a: Int16x8T, n: Int): Int8x8T {
    fun vqshrnNS16(a1: Int16x8T, n: Int): Int8x8T = Int8x8T()
    run {
        check(n >= 1 && n <= 8)
    }
    return vqshrnNS16(a, n)
}

/**
 * Signed saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnNS32)
 */
public fun vqshrnNS32(a: Int32x4T, n: Int): Int16x4T {
    fun vqshrnNS32(a1: Int32x4T, n: Int): Int16x4T = Int16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vqshrnNS32(a, n)
}

/**
 * Signed saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnNS64)
 */
public fun vqshrnNS64(a: Int64x2T, n: Int): Int32x2T {
    fun vqshrnNS64(a1: Int64x2T, n: Int): Int32x2T = Int32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vqshrnNS64(a, n)
}

/**
 * Unsigned saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnNU16)
 */
public fun vqshrnNU16(a: Uint16x8T, n: Int): Uint8x8T {
    fun vqshrnNU16(a1: Uint16x8T, n: Int): Uint8x8T = Uint8x8T()
    run {
        check(n >= 1 && n <= 8)
    }
    return vqshrnNU16(a, n)
}

/**
 * Unsigned saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnNU32)
 */
public fun vqshrnNU32(a: Uint32x4T, n: Int): Uint16x4T {
    fun vqshrnNU32(a1: Uint32x4T, n: Int): Uint16x4T = Uint16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vqshrnNU32(a, n)
}

/**
 * Unsigned saturating shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrnNU64)
 */
public fun vqshrnNU64(a: Uint64x2T, n: Int): Uint32x2T {
    fun vqshrnNU64(a1: Uint64x2T, n: Int): Uint32x2T = Uint32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vqshrnNU64(a, n)
}

/**
 * Signed saturating shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrunNS16)
 */
public fun vqshrunNS16(a: Int16x8T, n: Int): Uint8x8T {
    fun vqshrunNS16(a1: Int16x8T, n: Int): Uint8x8T = Uint8x8T()
    run {
        check(n >= 1 && n <= 8)
    }
    return vqshrunNS16(a, n)
}

/**
 * Signed saturating shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrunNS32)
 */
public fun vqshrunNS32(a: Int32x4T, n: Int): Uint16x4T {
    fun vqshrunNS32(a1: Int32x4T, n: Int): Uint16x4T = Uint16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vqshrunNS32(a, n)
}

/**
 * Signed saturating shift right unsigned narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqshrunNS64)
 */
public fun vqshrunNS64(a: Int64x2T, n: Int): Uint32x2T {
    fun vqshrunNS64(a1: Int64x2T, n: Int): Uint32x2T = Uint32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vqshrunNS64(a, n)
}

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubS8)
 */
public fun vqsubS8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubqS8)
 */
public fun vqsubqS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubS16)
 */
public fun vqsubS16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubqS16)
 */
public fun vqsubqS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubS32)
 */
public fun vqsubS32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubqS32)
 */
public fun vqsubqS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubS64)
 */
public fun vqsubS64(a: Int64x1T, b: Int64x1T): Int64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubqS64)
 */
public fun vqsubqS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubU8)
 */
public fun vqsubU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubqU8)
 */
public fun vqsubqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubU16)
 */
public fun vqsubU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubqU16)
 */
public fun vqsubqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubU32)
 */
public fun vqsubU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubqU32)
 */
public fun vqsubqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubU64)
 */
public fun vqsubU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Saturating subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vqsubqU64)
 */
public fun vqsubqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Rounding Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnHighS16)
 */
public fun vraddhnHighS16(a: Int8x8T, b: Int16x8T, c: Int16x8T): Int8x16T {
    val x = vraddhnS16(b, c)
    return vcombineS8(a, x)
}

/**
 * Rounding Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnHighS32)
 */
public fun vraddhnHighS32(a: Int16x4T, b: Int32x4T, c: Int32x4T): Int16x8T {
    val x = vraddhnS32(b, c)
    return vcombineS16(a, x)
}

/**
 * Rounding Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnHighS64)
 */
public fun vraddhnHighS64(a: Int32x2T, b: Int64x2T, c: Int64x2T): Int32x4T {
    val x = vraddhnS64(b, c)
    return vcombineS32(a, x)
}

/**
 * Rounding Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnHighU16)
 */
public fun vraddhnHighU16(a: Uint8x8T, b: Uint16x8T, c: Uint16x8T): Uint8x16T =
    run {
        val x: Uint8x8T = transmute(vraddhnS16(transmute(b), transmute(c)))
        vcombineU8(a, x)
    }

/**
 * Rounding Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnHighU32)
 */
public fun vraddhnHighU32(a: Uint16x4T, b: Uint32x4T, c: Uint32x4T): Uint16x8T =
    run {
        val x: Uint16x4T = transmute(vraddhnS32(transmute(b), transmute(c)))
        vcombineU16(a, x)
    }

/**
 * Rounding Add returning High Narrow (high half).
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnHighU64)
 */
public fun vraddhnHighU64(a: Uint32x2T, b: Uint64x2T, c: Uint64x2T): Uint32x4T =
    run {
        val x: Uint32x2T = transmute(vraddhnS64(transmute(b), transmute(c)))
        vcombineU32(a, x)
    }

/**
 * Rounding Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnS16)
 */
public fun vraddhnS16(a: Int16x8T, b: Int16x8T): Int8x8T {
    fun vraddhnS16(a1: Int16x8T, b1: Int16x8T): Int8x8T = Int8x8T()
    return vraddhnS16(a, b)
}

/**
 * Rounding Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnS32)
 */
public fun vraddhnS32(a: Int32x4T, b: Int32x4T): Int16x4T {
    fun vraddhnS32(a1: Int32x4T, b1: Int32x4T): Int16x4T = Int16x4T()
    return vraddhnS32(a, b)
}

/**
 * Rounding Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnS64)
 */
public fun vraddhnS64(a: Int64x2T, b: Int64x2T): Int32x2T {
    fun vraddhnS64(a1: Int64x2T, b1: Int64x2T): Int32x2T = Int32x2T()
    return vraddhnS64(a, b)
}

/**
 * Rounding Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnU16)
 */
public fun vraddhnU16(a: Uint16x8T, b: Uint16x8T): Uint8x8T = transmute(vraddhnS16(transmute(a), transmute(b)))

/**
 * Rounding Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnU32)
 */
public fun vraddhnU32(a: Uint32x4T, b: Uint32x4T): Uint16x4T = transmute(vraddhnS32(transmute(a), transmute(b)))

/**
 * Rounding Add returning High Narrow.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vraddhnU64)
 */
public fun vraddhnU64(a: Uint64x2T, b: Uint64x2T): Uint32x2T = transmute(vraddhnS64(transmute(a), transmute(b)))

/**
 * Reciprocal estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpeF16)
 */
public fun vrecpeF16(a: Float16x4T): Float16x4T {
    fun vrecpeF16(a1: Float16x4T): Float16x4T = Float16x4T()
    return vrecpeF16(a)
}

/**
 * Reciprocal estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpeqF16)
 */
public fun vrecpeqF16(a: Float16x8T): Float16x8T {
    fun vrecpeqF16(a1: Float16x8T): Float16x8T = Float16x8T()
    return vrecpeqF16(a)
}

/**
 * Reciprocal estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpeF32)
 */
public fun vrecpeF32(a: Float32x2T): Float32x2T {
    fun vrecpeF32(a1: Float32x2T): Float32x2T = Float32x2T()
    return vrecpeF32(a)
}

/**
 * Reciprocal estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpeqF32)
 */
public fun vrecpeqF32(a: Float32x4T): Float32x4T {
    fun vrecpeqF32(a1: Float32x4T): Float32x4T = Float32x4T()
    return vrecpeqF32(a)
}

/**
 * Unsigned reciprocal estimate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpeU32)
 */
public fun vrecpeU32(a: Uint32x2T): Uint32x2T {
    fun vrecpeU32(a1: Uint32x2T): Uint32x2T = Uint32x2T()
    return vrecpeU32(a)
}

/**
 * Unsigned reciprocal estimate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpeqU32)
 */
public fun vrecpeqU32(a: Uint32x4T): Uint32x4T {
    fun vrecpeqU32(a1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vrecpeqU32(a)
}

/**
 * Floating-point reciprocal step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpsF16)
 */
public fun vrecpsF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun vrecpsF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return vrecpsF16(a, b)
}

/**
 * Floating-point reciprocal step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpsqF16)
 */
public fun vrecpsqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun vrecpsqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return vrecpsqF16(a, b)
}

/**
 * Floating-point reciprocal step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpsF32)
 */
public fun vrecpsF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun vrecpsF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return vrecpsF32(a, b)
}

/**
 * Floating-point reciprocal step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrecpsqF32)
 */
public fun vrecpsqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun vrecpsqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return vrecpsqF32(a, b)
}

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32F16)
 */
public fun vreinterpretF32F16(a: Float16x4T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8F16)
 */
public fun vreinterpretS8F16(a: Float16x4T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32F16)
 */
public fun vreinterpretS32F16(a: Float16x4T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64F16)
 */
public fun vreinterpretS64F16(a: Float16x4T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8F16)
 */
public fun vreinterpretU8F16(a: Float16x4T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32F16)
 */
public fun vreinterpretU32F16(a: Float16x4T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64F16)
 */
public fun vreinterpretU64F16(a: Float16x4T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8F16)
 */
public fun vreinterpretP8F16(a: Float16x4T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32F16)
 */
public fun vreinterpretqF32F16(a: Float16x8T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8F16)
 */
public fun vreinterpretqS8F16(a: Float16x8T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32F16)
 */
public fun vreinterpretqS32F16(a: Float16x8T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64F16)
 */
public fun vreinterpretqS64F16(a: Float16x8T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8F16)
 */
public fun vreinterpretqU8F16(a: Float16x8T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32F16)
 */
public fun vreinterpretqU32F16(a: Float16x8T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64F16)
 */
public fun vreinterpretqU64F16(a: Float16x8T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8F16)
 */
public fun vreinterpretqP8F16(a: Float16x8T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16F32)
 */
public fun vreinterpretF16F32(a: Float32x2T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16F32)
 */
public fun vreinterpretqF16F32(a: Float32x4T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16S8)
 */
public fun vreinterpretF16S8(a: Int8x8T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16S8)
 */
public fun vreinterpretqF16S8(a: Int8x16T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16S32)
 */
public fun vreinterpretF16S32(a: Int32x2T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16S32)
 */
public fun vreinterpretqF16S32(a: Int32x4T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16S64)
 */
public fun vreinterpretF16S64(a: Int64x1T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16S64)
 */
public fun vreinterpretqF16S64(a: Int64x2T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16U8)
 */
public fun vreinterpretF16U8(a: Uint8x8T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16U8)
 */
public fun vreinterpretqF16U8(a: Uint8x16T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16U32)
 */
public fun vreinterpretF16U32(a: Uint32x2T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16U32)
 */
public fun vreinterpretqF16U32(a: Uint32x4T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16U64)
 */
public fun vreinterpretF16U64(a: Uint64x1T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16U64)
 */
public fun vreinterpretqF16U64(a: Uint64x2T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16P8)
 */
public fun vreinterpretF16P8(a: Poly8x8T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16P8)
 */
public fun vreinterpretqF16P8(a: Poly8x16T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16F16)
 */
public fun vreinterpretS16F16(a: Float16x4T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16F16)
 */
public fun vreinterpretU16F16(a: Float16x4T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16F16)
 */
public fun vreinterpretP16F16(a: Float16x4T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16F16)
 */
public fun vreinterpretqS16F16(a: Float16x8T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16F16)
 */
public fun vreinterpretqU16F16(a: Float16x8T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16F16)
 */
public fun vreinterpretqP16F16(a: Float16x8T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16S16)
 */
public fun vreinterpretF16S16(a: Int16x4T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16S16)
 */
public fun vreinterpretqF16S16(a: Int16x8T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16U16)
 */
public fun vreinterpretF16U16(a: Uint16x4T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16U16)
 */
public fun vreinterpretqF16U16(a: Uint16x8T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16P16)
 */
public fun vreinterpretF16P16(a: Poly16x4T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16P16)
 */
public fun vreinterpretqF16P16(a: Poly16x8T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32F32)
 */
public fun vreinterpretS32F32(a: Float32x2T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32F32)
 */
public fun vreinterpretU32F32(a: Float32x2T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32F32)
 */
public fun vreinterpretqS32F32(a: Float32x4T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32F32)
 */
public fun vreinterpretqU32F32(a: Float32x4T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8S8)
 */
public fun vreinterpretU8S8(a: Int8x8T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8S8)
 */
public fun vreinterpretP8S8(a: Int8x8T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8S8)
 */
public fun vreinterpretqU8S8(a: Int8x16T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8S8)
 */
public fun vreinterpretqP8S8(a: Int8x16T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16S16)
 */
public fun vreinterpretU16S16(a: Int16x4T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16S16)
 */
public fun vreinterpretP16S16(a: Int16x4T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16S16)
 */
public fun vreinterpretqU16S16(a: Int16x8T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16S16)
 */
public fun vreinterpretqP16S16(a: Int16x8T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32S32)
 */
public fun vreinterpretF32S32(a: Int32x2T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32S32)
 */
public fun vreinterpretU32S32(a: Int32x2T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32S32)
 */
public fun vreinterpretqF32S32(a: Int32x4T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32S32)
 */
public fun vreinterpretqU32S32(a: Int32x4T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64S64)
 */
public fun vreinterpretU64S64(a: Int64x1T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64S64)
 */
public fun vreinterpretqU64S64(a: Int64x2T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8U8)
 */
public fun vreinterpretS8U8(a: Uint8x8T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8U8)
 */
public fun vreinterpretP8U8(a: Uint8x8T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8U8)
 */
public fun vreinterpretqS8U8(a: Uint8x16T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8U8)
 */
public fun vreinterpretqP8U8(a: Uint8x16T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16U16)
 */
public fun vreinterpretS16U16(a: Uint16x4T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16U16)
 */
public fun vreinterpretP16U16(a: Uint16x4T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16U16)
 */
public fun vreinterpretqS16U16(a: Uint16x8T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16U16)
 */
public fun vreinterpretqP16U16(a: Uint16x8T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32U32)
 */
public fun vreinterpretF32U32(a: Uint32x2T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32U32)
 */
public fun vreinterpretS32U32(a: Uint32x2T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32U32)
 */
public fun vreinterpretqF32U32(a: Uint32x4T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32U32)
 */
public fun vreinterpretqS32U32(a: Uint32x4T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64U64)
 */
public fun vreinterpretS64U64(a: Uint64x1T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64U64)
 */
public fun vreinterpretqS64U64(a: Uint64x2T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8P8)
 */
public fun vreinterpretS8P8(a: Poly8x8T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8P8)
 */
public fun vreinterpretU8P8(a: Poly8x8T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8P8)
 */
public fun vreinterpretqS8P8(a: Poly8x16T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8P8)
 */
public fun vreinterpretqU8P8(a: Poly8x16T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16P16)
 */
public fun vreinterpretS16P16(a: Poly16x4T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16P16)
 */
public fun vreinterpretU16P16(a: Poly16x4T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16P16)
 */
public fun vreinterpretqS16P16(a: Poly16x8T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16P16)
 */
public fun vreinterpretqU16P16(a: Poly16x8T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16P128)
 */
public fun vreinterpretqF16P128(a: P128): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64F16)
 */
public fun vreinterpretP64F16(a: Float16x4T): Poly64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128F16)
 */
public fun vreinterpretqP128F16(a: Float16x8T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64F16)
 */
public fun vreinterpretqP64F16(a: Float16x8T): Poly64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF16P64)
 */
public fun vreinterpretF16P64(a: Poly64x1T): Float16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF16P64)
 */
public fun vreinterpretqF16P64(a: Poly64x2T): Float16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32P128)
 */
public fun vreinterpretqF32P128(a: P128): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8F32)
 */
public fun vreinterpretS8F32(a: Float32x2T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16F32)
 */
public fun vreinterpretS16F32(a: Float32x2T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64F32)
 */
public fun vreinterpretS64F32(a: Float32x2T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8F32)
 */
public fun vreinterpretU8F32(a: Float32x2T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16F32)
 */
public fun vreinterpretU16F32(a: Float32x2T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64F32)
 */
public fun vreinterpretU64F32(a: Float32x2T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8F32)
 */
public fun vreinterpretP8F32(a: Float32x2T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16F32)
 */
public fun vreinterpretP16F32(a: Float32x2T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128F32)
 */
public fun vreinterpretqP128F32(a: Float32x4T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8F32)
 */
public fun vreinterpretqS8F32(a: Float32x4T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16F32)
 */
public fun vreinterpretqS16F32(a: Float32x4T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64F32)
 */
public fun vreinterpretqS64F32(a: Float32x4T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8F32)
 */
public fun vreinterpretqU8F32(a: Float32x4T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16F32)
 */
public fun vreinterpretqU16F32(a: Float32x4T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64F32)
 */
public fun vreinterpretqU64F32(a: Float32x4T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8F32)
 */
public fun vreinterpretqP8F32(a: Float32x4T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16F32)
 */
public fun vreinterpretqP16F32(a: Float32x4T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32S8)
 */
public fun vreinterpretF32S8(a: Int8x8T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16S8)
 */
public fun vreinterpretS16S8(a: Int8x8T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32S8)
 */
public fun vreinterpretS32S8(a: Int8x8T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64S8)
 */
public fun vreinterpretS64S8(a: Int8x8T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16S8)
 */
public fun vreinterpretU16S8(a: Int8x8T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32S8)
 */
public fun vreinterpretU32S8(a: Int8x8T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64S8)
 */
public fun vreinterpretU64S8(a: Int8x8T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16S8)
 */
public fun vreinterpretP16S8(a: Int8x8T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64S8)
 */
public fun vreinterpretP64S8(a: Int8x8T): Poly64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128S8)
 */
public fun vreinterpretqP128S8(a: Int8x16T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32S8)
 */
public fun vreinterpretqF32S8(a: Int8x16T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16S8)
 */
public fun vreinterpretqS16S8(a: Int8x16T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32S8)
 */
public fun vreinterpretqS32S8(a: Int8x16T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64S8)
 */
public fun vreinterpretqS64S8(a: Int8x16T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16S8)
 */
public fun vreinterpretqU16S8(a: Int8x16T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32S8)
 */
public fun vreinterpretqU32S8(a: Int8x16T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64S8)
 */
public fun vreinterpretqU64S8(a: Int8x16T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16S8)
 */
public fun vreinterpretqP16S8(a: Int8x16T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64S8)
 */
public fun vreinterpretqP64S8(a: Int8x16T): Poly64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32S16)
 */
public fun vreinterpretF32S16(a: Int16x4T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8S16)
 */
public fun vreinterpretS8S16(a: Int16x4T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32S16)
 */
public fun vreinterpretS32S16(a: Int16x4T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64S16)
 */
public fun vreinterpretS64S16(a: Int16x4T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8S16)
 */
public fun vreinterpretU8S16(a: Int16x4T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32S16)
 */
public fun vreinterpretU32S16(a: Int16x4T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64S16)
 */
public fun vreinterpretU64S16(a: Int16x4T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8S16)
 */
public fun vreinterpretP8S16(a: Int16x4T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64S16)
 */
public fun vreinterpretP64S16(a: Int16x4T): Poly64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128S16)
 */
public fun vreinterpretqP128S16(a: Int16x8T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32S16)
 */
public fun vreinterpretqF32S16(a: Int16x8T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8S16)
 */
public fun vreinterpretqS8S16(a: Int16x8T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32S16)
 */
public fun vreinterpretqS32S16(a: Int16x8T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64S16)
 */
public fun vreinterpretqS64S16(a: Int16x8T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8S16)
 */
public fun vreinterpretqU8S16(a: Int16x8T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32S16)
 */
public fun vreinterpretqU32S16(a: Int16x8T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64S16)
 */
public fun vreinterpretqU64S16(a: Int16x8T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8S16)
 */
public fun vreinterpretqP8S16(a: Int16x8T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64S16)
 */
public fun vreinterpretqP64S16(a: Int16x8T): Poly64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8S32)
 */
public fun vreinterpretS8S32(a: Int32x2T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16S32)
 */
public fun vreinterpretS16S32(a: Int32x2T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64S32)
 */
public fun vreinterpretS64S32(a: Int32x2T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8S32)
 */
public fun vreinterpretU8S32(a: Int32x2T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16S32)
 */
public fun vreinterpretU16S32(a: Int32x2T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64S32)
 */
public fun vreinterpretU64S32(a: Int32x2T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8S32)
 */
public fun vreinterpretP8S32(a: Int32x2T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16S32)
 */
public fun vreinterpretP16S32(a: Int32x2T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64S32)
 */
public fun vreinterpretP64S32(a: Int32x2T): Poly64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128S32)
 */
public fun vreinterpretqP128S32(a: Int32x4T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8S32)
 */
public fun vreinterpretqS8S32(a: Int32x4T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16S32)
 */
public fun vreinterpretqS16S32(a: Int32x4T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64S32)
 */
public fun vreinterpretqS64S32(a: Int32x4T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8S32)
 */
public fun vreinterpretqU8S32(a: Int32x4T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16S32)
 */
public fun vreinterpretqU16S32(a: Int32x4T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64S32)
 */
public fun vreinterpretqU64S32(a: Int32x4T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8S32)
 */
public fun vreinterpretqP8S32(a: Int32x4T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16S32)
 */
public fun vreinterpretqP16S32(a: Int32x4T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64S32)
 */
public fun vreinterpretqP64S32(a: Int32x4T): Poly64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32S64)
 */
public fun vreinterpretF32S64(a: Int64x1T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8S64)
 */
public fun vreinterpretS8S64(a: Int64x1T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16S64)
 */
public fun vreinterpretS16S64(a: Int64x1T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32S64)
 */
public fun vreinterpretS32S64(a: Int64x1T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8S64)
 */
public fun vreinterpretU8S64(a: Int64x1T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16S64)
 */
public fun vreinterpretU16S64(a: Int64x1T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32S64)
 */
public fun vreinterpretU32S64(a: Int64x1T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8S64)
 */
public fun vreinterpretP8S64(a: Int64x1T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16S64)
 */
public fun vreinterpretP16S64(a: Int64x1T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128S64)
 */
public fun vreinterpretqP128S64(a: Int64x2T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32S64)
 */
public fun vreinterpretqF32S64(a: Int64x2T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8S64)
 */
public fun vreinterpretqS8S64(a: Int64x2T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16S64)
 */
public fun vreinterpretqS16S64(a: Int64x2T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32S64)
 */
public fun vreinterpretqS32S64(a: Int64x2T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8S64)
 */
public fun vreinterpretqU8S64(a: Int64x2T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16S64)
 */
public fun vreinterpretqU16S64(a: Int64x2T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32S64)
 */
public fun vreinterpretqU32S64(a: Int64x2T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8S64)
 */
public fun vreinterpretqP8S64(a: Int64x2T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16S64)
 */
public fun vreinterpretqP16S64(a: Int64x2T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32U8)
 */
public fun vreinterpretF32U8(a: Uint8x8T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16U8)
 */
public fun vreinterpretS16U8(a: Uint8x8T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32U8)
 */
public fun vreinterpretS32U8(a: Uint8x8T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64U8)
 */
public fun vreinterpretS64U8(a: Uint8x8T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16U8)
 */
public fun vreinterpretU16U8(a: Uint8x8T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32U8)
 */
public fun vreinterpretU32U8(a: Uint8x8T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64U8)
 */
public fun vreinterpretU64U8(a: Uint8x8T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16U8)
 */
public fun vreinterpretP16U8(a: Uint8x8T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64U8)
 */
public fun vreinterpretP64U8(a: Uint8x8T): Poly64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128U8)
 */
public fun vreinterpretqP128U8(a: Uint8x16T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32U8)
 */
public fun vreinterpretqF32U8(a: Uint8x16T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16U8)
 */
public fun vreinterpretqS16U8(a: Uint8x16T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32U8)
 */
public fun vreinterpretqS32U8(a: Uint8x16T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64U8)
 */
public fun vreinterpretqS64U8(a: Uint8x16T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16U8)
 */
public fun vreinterpretqU16U8(a: Uint8x16T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32U8)
 */
public fun vreinterpretqU32U8(a: Uint8x16T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64U8)
 */
public fun vreinterpretqU64U8(a: Uint8x16T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16U8)
 */
public fun vreinterpretqP16U8(a: Uint8x16T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64U8)
 */
public fun vreinterpretqP64U8(a: Uint8x16T): Poly64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32U16)
 */
public fun vreinterpretF32U16(a: Uint16x4T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8U16)
 */
public fun vreinterpretS8U16(a: Uint16x4T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32U16)
 */
public fun vreinterpretS32U16(a: Uint16x4T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64U16)
 */
public fun vreinterpretS64U16(a: Uint16x4T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8U16)
 */
public fun vreinterpretU8U16(a: Uint16x4T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32U16)
 */
public fun vreinterpretU32U16(a: Uint16x4T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64U16)
 */
public fun vreinterpretU64U16(a: Uint16x4T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8U16)
 */
public fun vreinterpretP8U16(a: Uint16x4T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64U16)
 */
public fun vreinterpretP64U16(a: Uint16x4T): Poly64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128U16)
 */
public fun vreinterpretqP128U16(a: Uint16x8T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32U16)
 */
public fun vreinterpretqF32U16(a: Uint16x8T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8U16)
 */
public fun vreinterpretqS8U16(a: Uint16x8T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32U16)
 */
public fun vreinterpretqS32U16(a: Uint16x8T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64U16)
 */
public fun vreinterpretqS64U16(a: Uint16x8T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8U16)
 */
public fun vreinterpretqU8U16(a: Uint16x8T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32U16)
 */
public fun vreinterpretqU32U16(a: Uint16x8T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64U16)
 */
public fun vreinterpretqU64U16(a: Uint16x8T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8U16)
 */
public fun vreinterpretqP8U16(a: Uint16x8T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64U16)
 */
public fun vreinterpretqP64U16(a: Uint16x8T): Poly64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8U32)
 */
public fun vreinterpretS8U32(a: Uint32x2T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16U32)
 */
public fun vreinterpretS16U32(a: Uint32x2T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64U32)
 */
public fun vreinterpretS64U32(a: Uint32x2T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8U32)
 */
public fun vreinterpretU8U32(a: Uint32x2T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16U32)
 */
public fun vreinterpretU16U32(a: Uint32x2T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64U32)
 */
public fun vreinterpretU64U32(a: Uint32x2T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8U32)
 */
public fun vreinterpretP8U32(a: Uint32x2T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16U32)
 */
public fun vreinterpretP16U32(a: Uint32x2T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64U32)
 */
public fun vreinterpretP64U32(a: Uint32x2T): Poly64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128U32)
 */
public fun vreinterpretqP128U32(a: Uint32x4T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8U32)
 */
public fun vreinterpretqS8U32(a: Uint32x4T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16U32)
 */
public fun vreinterpretqS16U32(a: Uint32x4T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64U32)
 */
public fun vreinterpretqS64U32(a: Uint32x4T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8U32)
 */
public fun vreinterpretqU8U32(a: Uint32x4T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16U32)
 */
public fun vreinterpretqU16U32(a: Uint32x4T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64U32)
 */
public fun vreinterpretqU64U32(a: Uint32x4T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8U32)
 */
public fun vreinterpretqP8U32(a: Uint32x4T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16U32)
 */
public fun vreinterpretqP16U32(a: Uint32x4T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64U32)
 */
public fun vreinterpretqP64U32(a: Uint32x4T): Poly64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32U64)
 */
public fun vreinterpretF32U64(a: Uint64x1T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8U64)
 */
public fun vreinterpretS8U64(a: Uint64x1T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16U64)
 */
public fun vreinterpretS16U64(a: Uint64x1T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32U64)
 */
public fun vreinterpretS32U64(a: Uint64x1T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8U64)
 */
public fun vreinterpretU8U64(a: Uint64x1T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16U64)
 */
public fun vreinterpretU16U64(a: Uint64x1T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32U64)
 */
public fun vreinterpretU32U64(a: Uint64x1T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8U64)
 */
public fun vreinterpretP8U64(a: Uint64x1T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16U64)
 */
public fun vreinterpretP16U64(a: Uint64x1T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128U64)
 */
public fun vreinterpretqP128U64(a: Uint64x2T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32U64)
 */
public fun vreinterpretqF32U64(a: Uint64x2T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8U64)
 */
public fun vreinterpretqS8U64(a: Uint64x2T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16U64)
 */
public fun vreinterpretqS16U64(a: Uint64x2T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32U64)
 */
public fun vreinterpretqS32U64(a: Uint64x2T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8U64)
 */
public fun vreinterpretqU8U64(a: Uint64x2T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16U64)
 */
public fun vreinterpretqU16U64(a: Uint64x2T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32U64)
 */
public fun vreinterpretqU32U64(a: Uint64x2T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8U64)
 */
public fun vreinterpretqP8U64(a: Uint64x2T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16U64)
 */
public fun vreinterpretqP16U64(a: Uint64x2T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32P8)
 */
public fun vreinterpretF32P8(a: Poly8x8T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16P8)
 */
public fun vreinterpretS16P8(a: Poly8x8T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32P8)
 */
public fun vreinterpretS32P8(a: Poly8x8T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64P8)
 */
public fun vreinterpretS64P8(a: Poly8x8T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16P8)
 */
public fun vreinterpretU16P8(a: Poly8x8T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32P8)
 */
public fun vreinterpretU32P8(a: Poly8x8T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64P8)
 */
public fun vreinterpretU64P8(a: Poly8x8T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16P8)
 */
public fun vreinterpretP16P8(a: Poly8x8T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64P8)
 */
public fun vreinterpretP64P8(a: Poly8x8T): Poly64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128P8)
 */
public fun vreinterpretqP128P8(a: Poly8x16T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32P8)
 */
public fun vreinterpretqF32P8(a: Poly8x16T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16P8)
 */
public fun vreinterpretqS16P8(a: Poly8x16T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32P8)
 */
public fun vreinterpretqS32P8(a: Poly8x16T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64P8)
 */
public fun vreinterpretqS64P8(a: Poly8x16T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16P8)
 */
public fun vreinterpretqU16P8(a: Poly8x16T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32P8)
 */
public fun vreinterpretqU32P8(a: Poly8x16T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64P8)
 */
public fun vreinterpretqU64P8(a: Poly8x16T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16P8)
 */
public fun vreinterpretqP16P8(a: Poly8x16T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64P8)
 */
public fun vreinterpretqP64P8(a: Poly8x16T): Poly64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretF32P16)
 */
public fun vreinterpretF32P16(a: Poly16x4T): Float32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8P16)
 */
public fun vreinterpretS8P16(a: Poly16x4T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32P16)
 */
public fun vreinterpretS32P16(a: Poly16x4T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS64P16)
 */
public fun vreinterpretS64P16(a: Poly16x4T): Int64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8P16)
 */
public fun vreinterpretU8P16(a: Poly16x4T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32P16)
 */
public fun vreinterpretU32P16(a: Poly16x4T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU64P16)
 */
public fun vreinterpretU64P16(a: Poly16x4T): Uint64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8P16)
 */
public fun vreinterpretP8P16(a: Poly16x4T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP64P16)
 */
public fun vreinterpretP64P16(a: Poly16x4T): Poly64x1T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128P16)
 */
public fun vreinterpretqP128P16(a: Poly16x8T): P128 = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqF32P16)
 */
public fun vreinterpretqF32P16(a: Poly16x8T): Float32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8P16)
 */
public fun vreinterpretqS8P16(a: Poly16x8T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32P16)
 */
public fun vreinterpretqS32P16(a: Poly16x8T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64P16)
 */
public fun vreinterpretqS64P16(a: Poly16x8T): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8P16)
 */
public fun vreinterpretqU8P16(a: Poly16x8T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32P16)
 */
public fun vreinterpretqU32P16(a: Poly16x8T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64P16)
 */
public fun vreinterpretqU64P16(a: Poly16x8T): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8P16)
 */
public fun vreinterpretqP8P16(a: Poly16x8T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64P16)
 */
public fun vreinterpretqP64P16(a: Poly16x8T): Poly64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS8P64)
 */
public fun vreinterpretS8P64(a: Poly64x1T): Int8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS16P64)
 */
public fun vreinterpretS16P64(a: Poly64x1T): Int16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretS32P64)
 */
public fun vreinterpretS32P64(a: Poly64x1T): Int32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU8P64)
 */
public fun vreinterpretU8P64(a: Poly64x1T): Uint8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU16P64)
 */
public fun vreinterpretU16P64(a: Poly64x1T): Uint16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretU32P64)
 */
public fun vreinterpretU32P64(a: Poly64x1T): Uint32x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP8P64)
 */
public fun vreinterpretP8P64(a: Poly64x1T): Poly8x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretP16P64)
 */
public fun vreinterpretP16P64(a: Poly64x1T): Poly16x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8P64)
 */
public fun vreinterpretqS8P64(a: Poly64x2T): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16P64)
 */
public fun vreinterpretqS16P64(a: Poly64x2T): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32P64)
 */
public fun vreinterpretqS32P64(a: Poly64x2T): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8P64)
 */
public fun vreinterpretqU8P64(a: Poly64x2T): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16P64)
 */
public fun vreinterpretqU16P64(a: Poly64x2T): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32P64)
 */
public fun vreinterpretqU32P64(a: Poly64x2T): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8P64)
 */
public fun vreinterpretqP8P64(a: Poly64x2T): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16P64)
 */
public fun vreinterpretqP16P64(a: Poly64x2T): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS8P128)
 */
public fun vreinterpretqS8P128(a: P128): Int8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS16P128)
 */
public fun vreinterpretqS16P128(a: P128): Int16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS32P128)
 */
public fun vreinterpretqS32P128(a: P128): Int32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqS64P128)
 */
public fun vreinterpretqS64P128(a: P128): Int64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU8P128)
 */
public fun vreinterpretqU8P128(a: P128): Uint8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU16P128)
 */
public fun vreinterpretqU16P128(a: P128): Uint16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU32P128)
 */
public fun vreinterpretqU32P128(a: P128): Uint32x4T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqU64P128)
 */
public fun vreinterpretqU64P128(a: P128): Uint64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP8P128)
 */
public fun vreinterpretqP8P128(a: P128): Poly8x16T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP16P128)
 */
public fun vreinterpretqP16P128(a: P128): Poly16x8T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP64P128)
 */
public fun vreinterpretqP64P128(a: P128): Poly64x2T = transmute(a)

/**
 * Vector reinterpret cast operation
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vreinterpretqP128P64)
 */
public fun vreinterpretqP128P64(a: Poly64x2T): P128 = transmute(a)

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev16P8)
 */
public fun vrev16P8(a: Poly8x8T): Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2, 5, 4, 7, 6)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev16S8)
 */
public fun vrev16S8(a: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2, 5, 4, 7, 6)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev16U8)
 */
public fun vrev16U8(a: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2, 5, 4, 7, 6)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev16qP8)
 */
public fun vrev16qP8(a: Poly8x16T): Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2, 5, 4, 7, 6, 9, 8, 11, 10, 13, 12, 15, 14)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev16qS8)
 */
public fun vrev16qS8(a: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2, 5, 4, 7, 6, 9, 8, 11, 10, 13, 12, 15, 14)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev16qU8)
 */
public fun vrev16qU8(a: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2, 5, 4, 7, 6, 9, 8, 11, 10, 13, 12, 15, 14)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32P16)
 */
public fun vrev32P16(a: Poly16x4T): Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32P8)
 */
public fun vrev32P8(a: Poly8x8T): Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0, 7, 6, 5, 4)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32S16)
 */
public fun vrev32S16(a: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32S8)
 */
public fun vrev32S8(a: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0, 7, 6, 5, 4)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32U16)
 */
public fun vrev32U16(a: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32U8)
 */
public fun vrev32U8(a: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0, 7, 6, 5, 4)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32qP16)
 */
public fun vrev32qP16(a: Poly16x8T): Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2, 5, 4, 7, 6)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32qP8)
 */
public fun vrev32qP8(a: Poly8x16T): Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0, 7, 6, 5, 4, 11, 10, 9, 8, 15, 14, 13, 12)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32qS16)
 */
public fun vrev32qS16(a: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2, 5, 4, 7, 6)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32qS8)
 */
public fun vrev32qS8(a: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0, 7, 6, 5, 4, 11, 10, 9, 8, 15, 14, 13, 12)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32qU16)
 */
public fun vrev32qU16(a: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2, 5, 4, 7, 6)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev32qU8)
 */
public fun vrev32qU8(a: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0, 7, 6, 5, 4, 11, 10, 9, 8, 15, 14, 13, 12)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64F32)
 */
public fun vrev64F32(a: Float32x2T): Float32x2T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64P16)
 */
public fun vrev64P16(a: Poly16x4T): Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64P8)
 */
public fun vrev64P8(a: Poly8x8T): Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(7, 6, 5, 4, 3, 2, 1, 0)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64S16)
 */
public fun vrev64S16(a: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64S32)
 */
public fun vrev64S32(a: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64S8)
 */
public fun vrev64S8(a: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(7, 6, 5, 4, 3, 2, 1, 0)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64U16)
 */
public fun vrev64U16(a: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64U32)
 */
public fun vrev64U32(a: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64U8)
 */
public fun vrev64U8(a: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(7, 6, 5, 4, 3, 2, 1, 0)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64qF32)
 */
public fun vrev64qF32(a: Float32x4T): Float32x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64qP16)
 */
public fun vrev64qP16(a: Poly16x8T): Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0, 7, 6, 5, 4)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64qP8)
 */
public fun vrev64qP8(a: Poly8x16T): Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64qS16)
 */
public fun vrev64qS16(a: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0, 7, 6, 5, 4)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64qS32)
 */
public fun vrev64qS32(a: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64qS8)
 */
public fun vrev64qS8(a: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64qU16)
 */
public fun vrev64qU16(a: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0, 7, 6, 5, 4)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64qU32)
 */
public fun vrev64qU32(a: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(1, 0, 3, 2)); _simdArg0 }

/**
 * Reversing vector elements (swap endianness)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64qU8)
 */
public fun vrev64qU8(a: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8)); _simdArg0 }

/**
 * Reverse elements in 64-bit doublewords
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64F16)
 */
public fun vrev64F16(a: Float16x4T): Float16x4T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0)); _simdArg0 }

/**
 * Reverse elements in 64-bit doublewords
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrev64qF16)
 */
public fun vrev64qF16(a: Float16x8T): Float16x8T = run { val _simdArg0 = a; val _simdArg1 = a; val _simdArg2 = SimdShuffleIdx(listOf(3, 2, 1, 0, 7, 6, 5, 4)); _simdArg0 }

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddS8)
 */
public fun vrhaddS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vrhaddS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vrhaddS8(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddqS8)
 */
public fun vrhaddqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    fun vrhaddqS8(a1: Int8x16T, b1: Int8x16T): Int8x16T = Int8x16T()
    return vrhaddqS8(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddS16)
 */
public fun vrhaddS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vrhaddS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vrhaddS16(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddqS16)
 */
public fun vrhaddqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun vrhaddqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return vrhaddqS16(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddS32)
 */
public fun vrhaddS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vrhaddS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vrhaddS32(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddqS32)
 */
public fun vrhaddqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun vrhaddqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return vrhaddqS32(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddU8)
 */
public fun vrhaddU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T {
    fun vrhaddU8(a1: Uint8x8T, b1: Uint8x8T): Uint8x8T = Uint8x8T()
    return vrhaddU8(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddqU8)
 */
public fun vrhaddqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T {
    fun vrhaddqU8(a1: Uint8x16T, b1: Uint8x16T): Uint8x16T = Uint8x16T()
    return vrhaddqU8(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddU16)
 */
public fun vrhaddU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T {
    fun vrhaddU16(a1: Uint16x4T, b1: Uint16x4T): Uint16x4T = Uint16x4T()
    return vrhaddU16(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddqU16)
 */
public fun vrhaddqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T {
    fun vrhaddqU16(a1: Uint16x8T, b1: Uint16x8T): Uint16x8T = Uint16x8T()
    return vrhaddqU16(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddU32)
 */
public fun vrhaddU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T {
    fun vrhaddU32(a1: Uint32x2T, b1: Uint32x2T): Uint32x2T = Uint32x2T()
    return vrhaddU32(a, b)
}

/**
 * Rounding halving add
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrhaddqU32)
 */
public fun vrhaddqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T {
    fun vrhaddqU32(a1: Uint32x4T, b1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vrhaddqU32(a, b)
}

/**
 * Floating-point round to integral, to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndnF16)
 */
public fun vrndnF16(a: Float16x4T): Float16x4T {
    fun vrndnF16(a1: Float16x4T): Float16x4T = Float16x4T()
    return vrndnF16(a)
}

/**
 * Floating-point round to integral, to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndnqF16)
 */
public fun vrndnqF16(a: Float16x8T): Float16x8T {
    fun vrndnqF16(a1: Float16x8T): Float16x8T = Float16x8T()
    return vrndnqF16(a)
}

/**
 * Floating-point round to integral, to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndnF32)
 */
public fun vrndnF32(a: Float32x2T): Float32x2T {
    fun vrndnF32(a1: Float32x2T): Float32x2T = Float32x2T()
    return vrndnF32(a)
}

/**
 * Floating-point round to integral, to nearest with ties to even
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrndnqF32)
 */
public fun vrndnqF32(a: Float32x4T): Float32x4T {
    fun vrndnqF32(a1: Float32x4T): Float32x4T = Float32x4T()
    return vrndnqF32(a)
}

/**
 * Signed rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlS8)
 */
public fun vrshlS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vrshlS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vrshlS8(a, b)
}

/**
 * Signed rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlqS8)
 */
public fun vrshlqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    fun vrshlqS8(a1: Int8x16T, b1: Int8x16T): Int8x16T = Int8x16T()
    return vrshlqS8(a, b)
}

/**
 * Signed rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlS16)
 */
public fun vrshlS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vrshlS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vrshlS16(a, b)
}

/**
 * Signed rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlqS16)
 */
public fun vrshlqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun vrshlqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return vrshlqS16(a, b)
}

/**
 * Signed rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlS32)
 */
public fun vrshlS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vrshlS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vrshlS32(a, b)
}

/**
 * Signed rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlqS32)
 */
public fun vrshlqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun vrshlqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return vrshlqS32(a, b)
}

/**
 * Signed rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlS64)
 */
public fun vrshlS64(a: Int64x1T, b: Int64x1T): Int64x1T {
    fun vrshlS64(a1: Int64x1T, b1: Int64x1T): Int64x1T = Int64x1T()
    return vrshlS64(a, b)
}

/**
 * Signed rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlqS64)
 */
public fun vrshlqS64(a: Int64x2T, b: Int64x2T): Int64x2T {
    fun vrshlqS64(a1: Int64x2T, b1: Int64x2T): Int64x2T = Int64x2T()
    return vrshlqS64(a, b)
}

/**
 * Unsigned rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlU8)
 */
public fun vrshlU8(a: Uint8x8T, b: Int8x8T): Uint8x8T {
    fun vrshlU8(a1: Uint8x8T, b1: Int8x8T): Uint8x8T = Uint8x8T()
    return vrshlU8(a, b)
}

/**
 * Unsigned rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlqU8)
 */
public fun vrshlqU8(a: Uint8x16T, b: Int8x16T): Uint8x16T {
    fun vrshlqU8(a1: Uint8x16T, b1: Int8x16T): Uint8x16T = Uint8x16T()
    return vrshlqU8(a, b)
}

/**
 * Unsigned rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlU16)
 */
public fun vrshlU16(a: Uint16x4T, b: Int16x4T): Uint16x4T {
    fun vrshlU16(a1: Uint16x4T, b1: Int16x4T): Uint16x4T = Uint16x4T()
    return vrshlU16(a, b)
}

/**
 * Unsigned rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlqU16)
 */
public fun vrshlqU16(a: Uint16x8T, b: Int16x8T): Uint16x8T {
    fun vrshlqU16(a1: Uint16x8T, b1: Int16x8T): Uint16x8T = Uint16x8T()
    return vrshlqU16(a, b)
}

/**
 * Unsigned rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlU32)
 */
public fun vrshlU32(a: Uint32x2T, b: Int32x2T): Uint32x2T {
    fun vrshlU32(a1: Uint32x2T, b1: Int32x2T): Uint32x2T = Uint32x2T()
    return vrshlU32(a, b)
}

/**
 * Unsigned rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlqU32)
 */
public fun vrshlqU32(a: Uint32x4T, b: Int32x4T): Uint32x4T {
    fun vrshlqU32(a1: Uint32x4T, b1: Int32x4T): Uint32x4T = Uint32x4T()
    return vrshlqU32(a, b)
}

/**
 * Unsigned rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlU64)
 */
public fun vrshlU64(a: Uint64x1T, b: Int64x1T): Uint64x1T {
    fun vrshlU64(a1: Uint64x1T, b1: Int64x1T): Uint64x1T = Uint64x1T()
    return vrshlU64(a, b)
}

/**
 * Unsigned rounding shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshlqU64)
 */
public fun vrshlqU64(a: Uint64x2T, b: Int64x2T): Uint64x2T {
    fun vrshlqU64(a1: Uint64x2T, b1: Int64x2T): Uint64x2T = Uint64x2T()
    return vrshlqU64(a, b)
}

/**
 * Signed rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrNS8)
 */
public fun vrshrNS8(a: Int8x8T, n: Int): Int8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vrshlS8(a, vdupNS8((-n).toByte()))
}

/**
 * Signed rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrqNS8)
 */
public fun vrshrqNS8(a: Int8x16T, n: Int): Int8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vrshlqS8(a, vdupqNS8((-n).toByte()))
}

/**
 * Signed rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrNS16)
 */
public fun vrshrNS16(a: Int16x4T, n: Int): Int16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vrshlS16(a, vdupNS16((-n).toShort()))
}

/**
 * Signed rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrqNS16)
 */
public fun vrshrqNS16(a: Int16x8T, n: Int): Int16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vrshlqS16(a, vdupqNS16((-n).toShort()))
}

/**
 * Signed rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrNS32)
 */
public fun vrshrNS32(a: Int32x2T, n: Int): Int32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vrshlS32(a, vdupNS32(-n))
}

/**
 * Signed rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrqNS32)
 */
public fun vrshrqNS32(a: Int32x4T, n: Int): Int32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vrshlqS32(a, vdupqNS32(-n))
}

/**
 * Signed rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrNS64)
 */
public fun vrshrNS64(a: Int64x1T, n: Int): Int64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    return vrshlS64(a, vdupNS64((-n).toLong()))
}

/**
 * Signed rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrqNS64)
 */
public fun vrshrqNS64(a: Int64x2T, n: Int): Int64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    return vrshlqS64(a, vdupqNS64((-n).toLong()))
}

/**
 * Unsigned rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrNU8)
 */
public fun vrshrNU8(a: Uint8x8T, n: Int): Uint8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vrshlU8(a, vdupNS8((-n).toByte()))
}

/**
 * Unsigned rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrqNU8)
 */
public fun vrshrqNU8(a: Uint8x16T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return vrshlqU8(a, vdupqNS8((-n).toByte()))
}

/**
 * Unsigned rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrNU16)
 */
public fun vrshrNU16(a: Uint16x4T, n: Int): Uint16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vrshlU16(a, vdupNS16((-n).toShort()))
}

/**
 * Unsigned rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrqNU16)
 */
public fun vrshrqNU16(a: Uint16x8T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return vrshlqU16(a, vdupqNS16((-n).toShort()))
}

/**
 * Unsigned rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrNU32)
 */
public fun vrshrNU32(a: Uint32x2T, n: Int): Uint32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vrshlU32(a, vdupNS32(-n))
}

/**
 * Unsigned rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrqNU32)
 */
public fun vrshrqNU32(a: Uint32x4T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return vrshlqU32(a, vdupqNS32(-n))
}

/**
 * Unsigned rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrNU64)
 */
public fun vrshrNU64(a: Uint64x1T, n: Int): Uint64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    return vrshlU64(a, vdupNS64((-n).toLong()))
}

/**
 * Unsigned rounding shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrqNU64)
 */
public fun vrshrqNU64(a: Uint64x2T, n: Int): Uint64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    return vrshlqU64(a, vdupqNS64((-n).toLong()))
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnNS16)
 */
public fun vrshrnNS16(a: Int16x8T, n: Int): Int8x8T {
    fun vrshrnNS16(a1: Int16x8T, n: Int): Int8x8T = Int8x8T()
    run {
        check(n >= 1 && n <= 8)
    }
    return vrshrnNS16(a, n)
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnNS32)
 */
public fun vrshrnNS32(a: Int32x4T, n: Int): Int16x4T {
    fun vrshrnNS32(a1: Int32x4T, n: Int): Int16x4T = Int16x4T()
    run {
        check(n >= 1 && n <= 16)
    }
    return vrshrnNS32(a, n)
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnNS64)
 */
public fun vrshrnNS64(a: Int64x2T, n: Int): Int32x2T {
    fun vrshrnNS64(a1: Int64x2T, n: Int): Int32x2T = Int32x2T()
    run {
        check(n >= 1 && n <= 32)
    }
    return vrshrnNS64(a, n)
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnNU16)
 */
public fun vrshrnNU16(a: Uint16x8T, n: Int): Uint8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return transmute(vrshrnNS16(transmute(a), n))
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnNU32)
 */
public fun vrshrnNU32(a: Uint32x4T, n: Int): Uint16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return transmute(vrshrnNS32(transmute(a), n))
}

/**
 * Rounding shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrshrnNU64)
 */
public fun vrshrnNU64(a: Uint64x2T, n: Int): Uint32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return transmute(vrshrnNS64(transmute(a), n))
}

/**
 * Reciprocal square-root estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrteF16)
 */
public fun vrsqrteF16(a: Float16x4T): Float16x4T {
    fun vrsqrteF16(a1: Float16x4T): Float16x4T = Float16x4T()
    return vrsqrteF16(a)
}

/**
 * Reciprocal square-root estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrteqF16)
 */
public fun vrsqrteqF16(a: Float16x8T): Float16x8T {
    fun vrsqrteqF16(a1: Float16x8T): Float16x8T = Float16x8T()
    return vrsqrteqF16(a)
}

/**
 * Reciprocal square-root estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrteF32)
 */
public fun vrsqrteF32(a: Float32x2T): Float32x2T {
    fun vrsqrteF32(a1: Float32x2T): Float32x2T = Float32x2T()
    return vrsqrteF32(a)
}

/**
 * Reciprocal square-root estimate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrteqF32)
 */
public fun vrsqrteqF32(a: Float32x4T): Float32x4T {
    fun vrsqrteqF32(a1: Float32x4T): Float32x4T = Float32x4T()
    return vrsqrteqF32(a)
}

/**
 * Unsigned reciprocal square root estimate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrteU32)
 */
public fun vrsqrteU32(a: Uint32x2T): Uint32x2T {
    fun vrsqrteU32(a1: Uint32x2T): Uint32x2T = Uint32x2T()
    return vrsqrteU32(a)
}

/**
 * Unsigned reciprocal square root estimate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrteqU32)
 */
public fun vrsqrteqU32(a: Uint32x4T): Uint32x4T {
    fun vrsqrteqU32(a1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vrsqrteqU32(a)
}

/**
 * Floating-point reciprocal square root step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtsF16)
 */
public fun vrsqrtsF16(a: Float16x4T, b: Float16x4T): Float16x4T {
    fun vrsqrtsF16(a1: Float16x4T, b1: Float16x4T): Float16x4T = Float16x4T()
    return vrsqrtsF16(a, b)
}

/**
 * Floating-point reciprocal square root step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtsqF16)
 */
public fun vrsqrtsqF16(a: Float16x8T, b: Float16x8T): Float16x8T {
    fun vrsqrtsqF16(a1: Float16x8T, b1: Float16x8T): Float16x8T = Float16x8T()
    return vrsqrtsqF16(a, b)
}

/**
 * Floating-point reciprocal square root step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtsF32)
 */
public fun vrsqrtsF32(a: Float32x2T, b: Float32x2T): Float32x2T {
    fun vrsqrtsF32(a1: Float32x2T, b1: Float32x2T): Float32x2T = Float32x2T()
    return vrsqrtsF32(a, b)
}

/**
 * Floating-point reciprocal square root step
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsqrtsqF32)
 */
public fun vrsqrtsqF32(a: Float32x4T, b: Float32x4T): Float32x4T {
    fun vrsqrtsqF32(a1: Float32x4T, b1: Float32x4T): Float32x4T = Float32x4T()
    return vrsqrtsqF32(a, b)
}

/**
 * Signed rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraNS8)
 */
public fun vrsraNS8(a: Int8x8T, b: Int8x8T, n: Int): Int8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrNS8(b, n); _simdArg0 }
}

/**
 * Signed rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraqNS8)
 */
public fun vrsraqNS8(a: Int8x16T, b: Int8x16T, n: Int): Int8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrqNS8(b, n); _simdArg0 }
}

/**
 * Signed rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraNS16)
 */
public fun vrsraNS16(a: Int16x4T, b: Int16x4T, n: Int): Int16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrNS16(b, n); _simdArg0 }
}

/**
 * Signed rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraqNS16)
 */
public fun vrsraqNS16(a: Int16x8T, b: Int16x8T, n: Int): Int16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrqNS16(b, n); _simdArg0 }
}

/**
 * Signed rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraNS32)
 */
public fun vrsraNS32(a: Int32x2T, b: Int32x2T, n: Int): Int32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrNS32(b, n); _simdArg0 }
}

/**
 * Signed rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraqNS32)
 */
public fun vrsraqNS32(a: Int32x4T, b: Int32x4T, n: Int): Int32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrqNS32(b, n); _simdArg0 }
}

/**
 * Signed rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraNS64)
 */
public fun vrsraNS64(a: Int64x1T, b: Int64x1T, n: Int): Int64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrNS64(b, n); _simdArg0 }
}

/**
 * Signed rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraqNS64)
 */
public fun vrsraqNS64(a: Int64x2T, b: Int64x2T, n: Int): Int64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrqNS64(b, n); _simdArg0 }
}

/**
 * Unsigned rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraNU8)
 */
public fun vrsraNU8(a: Uint8x8T, b: Uint8x8T, n: Int): Uint8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrNU8(b, n); _simdArg0 }
}

/**
 * Unsigned rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraqNU8)
 */
public fun vrsraqNU8(a: Uint8x16T, b: Uint8x16T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrqNU8(b, n); _simdArg0 }
}

/**
 * Unsigned rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraNU16)
 */
public fun vrsraNU16(a: Uint16x4T, b: Uint16x4T, n: Int): Uint16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrNU16(b, n); _simdArg0 }
}

/**
 * Unsigned rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraqNU16)
 */
public fun vrsraqNU16(a: Uint16x8T, b: Uint16x8T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrqNU16(b, n); _simdArg0 }
}

/**
 * Unsigned rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraNU32)
 */
public fun vrsraNU32(a: Uint32x2T, b: Uint32x2T, n: Int): Uint32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrNU32(b, n); _simdArg0 }
}

/**
 * Unsigned rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraqNU32)
 */
public fun vrsraqNU32(a: Uint32x4T, b: Uint32x4T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrqNU32(b, n); _simdArg0 }
}

/**
 * Unsigned rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraNU64)
 */
public fun vrsraNU64(a: Uint64x1T, b: Uint64x1T, n: Int): Uint64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrNU64(b, n); _simdArg0 }
}

/**
 * Unsigned rounding shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsraqNU64)
 */
public fun vrsraqNU64(a: Uint64x2T, b: Uint64x2T, n: Int): Uint64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vrshrqNU64(b, n); _simdArg0 }
}

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnS16)
 */
public fun vrsubhnS16(a: Int16x8T, b: Int16x8T): Int8x8T {
    fun vrsubhnS16(a1: Int16x8T, b1: Int16x8T): Int8x8T = Int8x8T()
    return vrsubhnS16(a, b)
}

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnS32)
 */
public fun vrsubhnS32(a: Int32x4T, b: Int32x4T): Int16x4T {
    fun vrsubhnS32(a1: Int32x4T, b1: Int32x4T): Int16x4T = Int16x4T()
    return vrsubhnS32(a, b)
}

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnS64)
 */
public fun vrsubhnS64(a: Int64x2T, b: Int64x2T): Int32x2T {
    fun vrsubhnS64(a1: Int64x2T, b1: Int64x2T): Int32x2T = Int32x2T()
    return vrsubhnS64(a, b)
}

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnU16)
 */
public fun vrsubhnU16(a: Uint16x8T, b: Uint16x8T): Uint8x8T = transmute(vrsubhnS16(transmute(a), transmute(b)))

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnU32)
 */
public fun vrsubhnU32(a: Uint32x4T, b: Uint32x4T): Uint16x4T = transmute(vrsubhnS32(transmute(a), transmute(b)))

/**
 * Rounding subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vrsubhnU64)
 */
public fun vrsubhnU64(a: Uint64x2T, b: Uint64x2T): Uint32x2T = transmute(vrsubhnS64(transmute(a), transmute(b)))

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneF16)
 */
public fun vsetLaneF16(a: F16, b: Float16x4T, lane: Int): Float16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneF16)
 */
public fun vsetqLaneF16(a: F16, b: Float16x8T, lane: Int): Float16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneF32)
 */
public fun vsetLaneF32(a: Float, b: Float32x2T, lane: Int): Float32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneF32)
 */
public fun vsetqLaneF32(a: Float, b: Float32x4T, lane: Int): Float32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneS8)
 */
public fun vsetLaneS8(a: Byte, b: Int8x8T, lane: Int): Int8x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneS8)
 */
public fun vsetqLaneS8(a: Byte, b: Int8x16T, lane: Int): Int8x16T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneS16)
 */
public fun vsetLaneS16(a: Short, b: Int16x4T, lane: Int): Int16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneS16)
 */
public fun vsetqLaneS16(a: Short, b: Int16x8T, lane: Int): Int16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneS32)
 */
public fun vsetLaneS32(a: Int, b: Int32x2T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneS32)
 */
public fun vsetqLaneS32(a: Int, b: Int32x4T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneS64)
 */
public fun vsetqLaneS64(a: Long, b: Int64x2T, lane: Int): Int64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneU8)
 */
public fun vsetLaneU8(a: UByte, b: Uint8x8T, lane: Int): Uint8x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneU8)
 */
public fun vsetqLaneU8(a: UByte, b: Uint8x16T, lane: Int): Uint8x16T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneU16)
 */
public fun vsetLaneU16(a: UShort, b: Uint16x4T, lane: Int): Uint16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneU16)
 */
public fun vsetqLaneU16(a: UShort, b: Uint16x8T, lane: Int): Uint16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneU32)
 */
public fun vsetLaneU32(a: UInt, b: Uint32x2T, lane: Int): Uint32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneU32)
 */
public fun vsetqLaneU32(a: UInt, b: Uint32x4T, lane: Int): Uint32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneU64)
 */
public fun vsetqLaneU64(a: ULong, b: Uint64x2T, lane: Int): Uint64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneP8)
 */
public fun vsetLaneP8(a: P8, b: Poly8x8T, lane: Int): Poly8x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneP8)
 */
public fun vsetqLaneP8(a: P8, b: Poly8x16T, lane: Int): Poly8x16T {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneP16)
 */
public fun vsetLaneP16(a: P16, b: Poly16x4T, lane: Int): Poly16x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneP16)
 */
public fun vsetqLaneP16(a: P16, b: Poly16x8T, lane: Int): Poly16x8T {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneP64)
 */
public fun vsetLaneP64(a: P64, b: Poly64x1T, lane: Int): Poly64x1T {
    run {
        check(lane == 0)
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneS64)
 */
public fun vsetLaneS64(a: Long, b: Int64x1T, lane: Int): Int64x1T {
    run {
        check(lane == 0)
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetLaneU64)
 */
public fun vsetLaneU64(a: ULong, b: Uint64x1T, lane: Int): Uint64x1T {
    run {
        check(lane == 0)
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * Insert vector element from another vector element
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsetqLaneP64)
 */
public fun vsetqLaneP64(a: P64, b: Poly64x2T, lane: Int): Poly64x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    return run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); val _simdArg2 = a; _simdArg0 }
}

/**
 * SHA1 hash update accelerator, choose.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha1cqU32)
 */
public fun vsha1cqU32(hashAbcd: Uint32x4T, hashE: UInt, wk: Uint32x4T): Uint32x4T {
    fun vsha1cqU32(hashAbcd1: Uint32x4T, hashE1: UInt, wk1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vsha1cqU32(hashAbcd, hashE, wk)
}

/**
 * SHA1 fixed rotate.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha1hU32)
 */
public fun vsha1hU32(hashE: UInt): UInt {
    fun vsha1hU32(hashE1: UInt): UInt = 0u
    return vsha1hU32(hashE)
}

/**
 * SHA1 hash update accelerator, majority
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha1mqU32)
 */
public fun vsha1mqU32(hashAbcd: Uint32x4T, hashE: UInt, wk: Uint32x4T): Uint32x4T {
    fun vsha1mqU32(hashAbcd1: Uint32x4T, hashE1: UInt, wk1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vsha1mqU32(hashAbcd, hashE, wk)
}

/**
 * SHA1 hash update accelerator, parity
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha1pqU32)
 */
public fun vsha1pqU32(hashAbcd: Uint32x4T, hashE: UInt, wk: Uint32x4T): Uint32x4T {
    fun vsha1pqU32(hashAbcd1: Uint32x4T, hashE1: UInt, wk1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vsha1pqU32(hashAbcd, hashE, wk)
}

/**
 * SHA1 schedule update accelerator, first part.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha1su0qU32)
 */
public fun vsha1su0qU32(w03: Uint32x4T, w47: Uint32x4T, w811: Uint32x4T): Uint32x4T {
    fun vsha1su0qU32(w031: Uint32x4T, w471: Uint32x4T, w8111: Uint32x4T): Uint32x4T = Uint32x4T()
    return vsha1su0qU32(w03, w47, w811)
}

/**
 * SHA1 schedule update accelerator, second part.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha1su1qU32)
 */
public fun vsha1su1qU32(tw03: Uint32x4T, w1215: Uint32x4T): Uint32x4T {
    fun vsha1su1qU32(tw031: Uint32x4T, w12151: Uint32x4T): Uint32x4T = Uint32x4T()
    return vsha1su1qU32(tw03, w1215)
}

/**
 * SHA1 schedule update accelerator, upper part.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha256h2qU32)
 */
public fun vsha256h2qU32(hashAbcd: Uint32x4T, hashEfgh: Uint32x4T, wk: Uint32x4T): Uint32x4T {
    fun vsha256h2qU32(hashAbcd1: Uint32x4T, hashEfgh1: Uint32x4T, wk1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vsha256h2qU32(hashAbcd, hashEfgh, wk)
}

/**
 * SHA1 schedule update accelerator, first part.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha256hqU32)
 */
public fun vsha256hqU32(hashAbcd: Uint32x4T, hashEfgh: Uint32x4T, wk: Uint32x4T): Uint32x4T {
    fun vsha256hqU32(hashAbcd1: Uint32x4T, hashEfgh1: Uint32x4T, wk1: Uint32x4T): Uint32x4T = Uint32x4T()
    return vsha256hqU32(hashAbcd, hashEfgh, wk)
}

/**
 * SHA256 schedule update accelerator, first part.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha256su0qU32)
 */
public fun vsha256su0qU32(w03: Uint32x4T, w47: Uint32x4T): Uint32x4T {
    fun vsha256su0qU32(w031: Uint32x4T, w471: Uint32x4T): Uint32x4T = Uint32x4T()
    return vsha256su0qU32(w03, w47)
}

/**
 * SHA256 schedule update accelerator, second part.
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsha256su1qU32)
 */
public fun vsha256su1qU32(tw03: Uint32x4T, w811: Uint32x4T, w1215: Uint32x4T): Uint32x4T {
    fun vsha256su1qU32(tw031: Uint32x4T, w8111: Uint32x4T, w12151: Uint32x4T): Uint32x4T = Uint32x4T()
    return vsha256su1qU32(tw03, w811, w1215)
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlNS8)
 */
public fun vshlNS8(a: Int8x8T, n: Int): Int8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNS8(n.toByte()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqNS8)
 */
public fun vshlqNS8(a: Int8x16T, n: Int): Int8x16T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNS8(n.toByte()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlNS16)
 */
public fun vshlNS16(a: Int16x4T, n: Int): Int16x4T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNS16(n.toShort()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqNS16)
 */
public fun vshlqNS16(a: Int16x8T, n: Int): Int16x8T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNS16(n.toShort()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlNS32)
 */
public fun vshlNS32(a: Int32x2T, n: Int): Int32x2T {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNS32(n); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqNS32)
 */
public fun vshlqNS32(a: Int32x4T, n: Int): Int32x4T {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNS32(n); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlNS64)
 */
public fun vshlNS64(a: Int64x1T, n: Int): Int64x1T {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNS64(n.toLong()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqNS64)
 */
public fun vshlqNS64(a: Int64x2T, n: Int): Int64x2T {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNS64(n.toLong()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlNU8)
 */
public fun vshlNU8(a: Uint8x8T, n: Int): Uint8x8T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNU8(n.toUByte()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqNU8)
 */
public fun vshlqNU8(a: Uint8x16T, n: Int): Uint8x16T {
    run {
        check(0 <= n && n < (1 shl 3)) { "N doesn't fit in 3 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNU8(n.toUByte()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlNU16)
 */
public fun vshlNU16(a: Uint16x4T, n: Int): Uint16x4T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNU16(n.toUShort()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqNU16)
 */
public fun vshlqNU16(a: Uint16x8T, n: Int): Uint16x8T {
    run {
        check(0 <= n && n < (1 shl 4)) { "N doesn't fit in 4 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNU16(n.toUShort()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlNU32)
 */
public fun vshlNU32(a: Uint32x2T, n: Int): Uint32x2T {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNU32(n.toUInt()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqNU32)
 */
public fun vshlqNU32(a: Uint32x4T, n: Int): Uint32x4T {
    run {
        check(0 <= n && n < (1 shl 5)) { "N doesn't fit in 5 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNU32(n.toUInt()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlNU64)
 */
public fun vshlNU64(a: Uint64x1T, n: Int): Uint64x1T {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNU64(n.toULong()); _simdArg0 }
}

/**
 * Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqNU64)
 */
public fun vshlqNU64(a: Uint64x2T, n: Int): Uint64x2T {
    run {
        check(0 <= n && n < (1 shl 6)) { "N doesn't fit in 6 bits (unsigned)" }
    }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNU64(n.toULong()); _simdArg0 }
}

/**
 * Signed Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlS8)
 */
public fun vshlS8(a: Int8x8T, b: Int8x8T): Int8x8T {
    fun vshlS8(a1: Int8x8T, b1: Int8x8T): Int8x8T = Int8x8T()
    return vshlS8(a, b)
}

/**
 * Signed Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqS8)
 */
public fun vshlqS8(a: Int8x16T, b: Int8x16T): Int8x16T {
    fun vshlqS8(a1: Int8x16T, b1: Int8x16T): Int8x16T = Int8x16T()
    return vshlqS8(a, b)
}

/**
 * Signed Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlS16)
 */
public fun vshlS16(a: Int16x4T, b: Int16x4T): Int16x4T {
    fun vshlS16(a1: Int16x4T, b1: Int16x4T): Int16x4T = Int16x4T()
    return vshlS16(a, b)
}

/**
 * Signed Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqS16)
 */
public fun vshlqS16(a: Int16x8T, b: Int16x8T): Int16x8T {
    fun vshlqS16(a1: Int16x8T, b1: Int16x8T): Int16x8T = Int16x8T()
    return vshlqS16(a, b)
}

/**
 * Signed Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlS32)
 */
public fun vshlS32(a: Int32x2T, b: Int32x2T): Int32x2T {
    fun vshlS32(a1: Int32x2T, b1: Int32x2T): Int32x2T = Int32x2T()
    return vshlS32(a, b)
}

/**
 * Signed Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqS32)
 */
public fun vshlqS32(a: Int32x4T, b: Int32x4T): Int32x4T {
    fun vshlqS32(a1: Int32x4T, b1: Int32x4T): Int32x4T = Int32x4T()
    return vshlqS32(a, b)
}

/**
 * Signed Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlS64)
 */
public fun vshlS64(a: Int64x1T, b: Int64x1T): Int64x1T {
    fun vshlS64(a1: Int64x1T, b1: Int64x1T): Int64x1T = Int64x1T()
    return vshlS64(a, b)
}

/**
 * Signed Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqS64)
 */
public fun vshlqS64(a: Int64x2T, b: Int64x2T): Int64x2T {
    fun vshlqS64(a1: Int64x2T, b1: Int64x2T): Int64x2T = Int64x2T()
    return vshlqS64(a, b)
}

/**
 * Unsigned Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlU8)
 */
public fun vshlU8(a: Uint8x8T, b: Int8x8T): Uint8x8T {
    fun vshlU8(a1: Uint8x8T, b1: Int8x8T): Uint8x8T = Uint8x8T()
    return vshlU8(a, b)
}

/**
 * Unsigned Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqU8)
 */
public fun vshlqU8(a: Uint8x16T, b: Int8x16T): Uint8x16T {
    fun vshlqU8(a1: Uint8x16T, b1: Int8x16T): Uint8x16T = Uint8x16T()
    return vshlqU8(a, b)
}

/**
 * Unsigned Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlU16)
 */
public fun vshlU16(a: Uint16x4T, b: Int16x4T): Uint16x4T {
    fun vshlU16(a1: Uint16x4T, b1: Int16x4T): Uint16x4T = Uint16x4T()
    return vshlU16(a, b)
}

/**
 * Unsigned Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqU16)
 */
public fun vshlqU16(a: Uint16x8T, b: Int16x8T): Uint16x8T {
    fun vshlqU16(a1: Uint16x8T, b1: Int16x8T): Uint16x8T = Uint16x8T()
    return vshlqU16(a, b)
}

/**
 * Unsigned Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlU32)
 */
public fun vshlU32(a: Uint32x2T, b: Int32x2T): Uint32x2T {
    fun vshlU32(a1: Uint32x2T, b1: Int32x2T): Uint32x2T = Uint32x2T()
    return vshlU32(a, b)
}

/**
 * Unsigned Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqU32)
 */
public fun vshlqU32(a: Uint32x4T, b: Int32x4T): Uint32x4T {
    fun vshlqU32(a1: Uint32x4T, b1: Int32x4T): Uint32x4T = Uint32x4T()
    return vshlqU32(a, b)
}

/**
 * Unsigned Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlU64)
 */
public fun vshlU64(a: Uint64x1T, b: Int64x1T): Uint64x1T {
    fun vshlU64(a1: Uint64x1T, b1: Int64x1T): Uint64x1T = Uint64x1T()
    return vshlU64(a, b)
}

/**
 * Unsigned Shift left
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshlqU64)
 */
public fun vshlqU64(a: Uint64x2T, b: Int64x2T): Uint64x2T {
    fun vshlqU64(a1: Uint64x2T, b1: Int64x2T): Uint64x2T = Uint64x2T()
    return vshlqU64(a, b)
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllNS16)
 */
public fun vshllNS16(a: Int16x4T, n: Int): Int32x4T {
    run {
        check(n >= 0 && n <= 16)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = vdupqNS32(n); _simdArg0 }
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllNS32)
 */
public fun vshllNS32(a: Int32x2T, n: Int): Int64x2T {
    run {
        check(n >= 0 && n <= 32)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = vdupqNS64(n.toLong()); _simdArg0 }
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllNS8)
 */
public fun vshllNS8(a: Int8x8T, n: Int): Int16x8T {
    run {
        check(n >= 0 && n <= 8)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = vdupqNS16(n.toShort()); _simdArg0 }
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllNU16)
 */
public fun vshllNU16(a: Uint16x4T, n: Int): Uint32x4T {
    run {
        check(n >= 0 && n <= 16)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = vdupqNU32(n.toUInt()); _simdArg0 }
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllNU32)
 */
public fun vshllNU32(a: Uint32x2T, n: Int): Uint64x2T {
    run {
        check(n >= 0 && n <= 32)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = vdupqNU64(n.toULong()); _simdArg0 }
}

/**
 * Signed shift left long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshllNU8)
 */
public fun vshllNU8(a: Uint8x8T, n: Int): Uint16x8T {
    run {
        check(n >= 0 && n <= 8)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; _simdArg0 }; val _simdArg1 = vdupqNU16(n.toUShort()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrNS8)
 */
public fun vshrNS8(a: Int8x8T, n: Int): Int8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    val n: Int =
        if (n == 8) {
            7
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNS8(n.toByte()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrqNS8)
 */
public fun vshrqNS8(a: Int8x16T, n: Int): Int8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    val n: Int =
        if (n == 8) {
            7
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNS8(n.toByte()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrNS16)
 */
public fun vshrNS16(a: Int16x4T, n: Int): Int16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    val n: Int =
        if (n == 16) {
            15
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNS16(n.toShort()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrqNS16)
 */
public fun vshrqNS16(a: Int16x8T, n: Int): Int16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    val n: Int =
        if (n == 16) {
            15
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNS16(n.toShort()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrNS32)
 */
public fun vshrNS32(a: Int32x2T, n: Int): Int32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    val n: Int =
        if (n == 32) {
            31
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNS32(n); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrqNS32)
 */
public fun vshrqNS32(a: Int32x4T, n: Int): Int32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    val n: Int =
        if (n == 32) {
            31
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNS32(n); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrNS64)
 */
public fun vshrNS64(a: Int64x1T, n: Int): Int64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    val n: Int =
        if (n == 64) {
            63
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNS64(n.toLong()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrqNS64)
 */
public fun vshrqNS64(a: Int64x2T, n: Int): Int64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    val n: Int =
        if (n == 64) {
            63
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNS64(n.toLong()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrNU8)
 */
public fun vshrNU8(a: Uint8x8T, n: Int): Uint8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    val n: Int =
        if (n == 8) {
            return vdupNU8(0u)
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNU8(n.toUByte()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrqNU8)
 */
public fun vshrqNU8(a: Uint8x16T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    val n: Int =
        if (n == 8) {
            return vdupqNU8(0u)
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNU8(n.toUByte()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrNU16)
 */
public fun vshrNU16(a: Uint16x4T, n: Int): Uint16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    val n: Int =
        if (n == 16) {
            return vdupNU16(0u)
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNU16(n.toUShort()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrqNU16)
 */
public fun vshrqNU16(a: Uint16x8T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    val n: Int =
        if (n == 16) {
            return vdupqNU16(0u)
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNU16(n.toUShort()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrNU32)
 */
public fun vshrNU32(a: Uint32x2T, n: Int): Uint32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    val n: Int =
        if (n == 32) {
            return vdupNU32(0u)
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNU32(n.toUInt()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrqNU32)
 */
public fun vshrqNU32(a: Uint32x4T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    val n: Int =
        if (n == 32) {
            return vdupqNU32(0u)
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNU32(n.toUInt()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrNU64)
 */
public fun vshrNU64(a: Uint64x1T, n: Int): Uint64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    val n: Int =
        if (n == 64) {
            return vdupNU64(0uL)
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupNU64(n.toULong()); _simdArg0 }
}

/**
 * Shift right
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrqNU64)
 */
public fun vshrqNU64(a: Uint64x2T, n: Int): Uint64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    val n: Int =
        if (n == 64) {
            return vdupqNU64(0uL)
        } else {
            n
        }
    return run { val _simdArg0 = a; val _simdArg1 = vdupqNU64(n.toULong()); _simdArg0 }
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnNS16)
 */
public fun vshrnNS16(a: Int16x8T, n: Int): Int8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = vdupqNS16(n.toShort()); _simdArg0 }; _simdArg0 }
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnNS32)
 */
public fun vshrnNS32(a: Int32x4T, n: Int): Int16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = vdupqNS32(n); _simdArg0 }; _simdArg0 }
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnNS64)
 */
public fun vshrnNS64(a: Int64x2T, n: Int): Int32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = vdupqNS64(n.toLong()); _simdArg0 }; _simdArg0 }
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnNU16)
 */
public fun vshrnNU16(a: Uint16x8T, n: Int): Uint8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = vdupqNU16(n.toUShort()); _simdArg0 }; _simdArg0 }
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnNU32)
 */
public fun vshrnNU32(a: Uint32x4T, n: Int): Uint16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = vdupqNU32(n.toUInt()); _simdArg0 }; _simdArg0 }
}

/**
 * Shift right narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vshrnNU64)
 */
public fun vshrnNU64(a: Uint64x2T, n: Int): Uint32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = vdupqNU64(n.toULong()); _simdArg0 }; _simdArg0 }
}

/**
 * Signed shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraNS8)
 */
public fun vsraNS8(a: Int8x8T, b: Int8x8T, n: Int): Int8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrNS8(b, n); _simdArg0 }
}

/**
 * Signed shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraqNS8)
 */
public fun vsraqNS8(a: Int8x16T, b: Int8x16T, n: Int): Int8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrqNS8(b, n); _simdArg0 }
}

/**
 * Signed shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraNS16)
 */
public fun vsraNS16(a: Int16x4T, b: Int16x4T, n: Int): Int16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrNS16(b, n); _simdArg0 }
}

/**
 * Signed shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraqNS16)
 */
public fun vsraqNS16(a: Int16x8T, b: Int16x8T, n: Int): Int16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrqNS16(b, n); _simdArg0 }
}

/**
 * Signed shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraNS32)
 */
public fun vsraNS32(a: Int32x2T, b: Int32x2T, n: Int): Int32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrNS32(b, n); _simdArg0 }
}

/**
 * Signed shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraqNS32)
 */
public fun vsraqNS32(a: Int32x4T, b: Int32x4T, n: Int): Int32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrqNS32(b, n); _simdArg0 }
}

/**
 * Signed shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraNS64)
 */
public fun vsraNS64(a: Int64x1T, b: Int64x1T, n: Int): Int64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrNS64(b, n); _simdArg0 }
}

/**
 * Signed shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraqNS64)
 */
public fun vsraqNS64(a: Int64x2T, b: Int64x2T, n: Int): Int64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrqNS64(b, n); _simdArg0 }
}

/**
 * Unsigned shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraNU8)
 */
public fun vsraNU8(a: Uint8x8T, b: Uint8x8T, n: Int): Uint8x8T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrNU8(b, n); _simdArg0 }
}

/**
 * Unsigned shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraqNU8)
 */
public fun vsraqNU8(a: Uint8x16T, b: Uint8x16T, n: Int): Uint8x16T {
    run {
        check(n >= 1 && n <= 8)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrqNU8(b, n); _simdArg0 }
}

/**
 * Unsigned shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraNU16)
 */
public fun vsraNU16(a: Uint16x4T, b: Uint16x4T, n: Int): Uint16x4T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrNU16(b, n); _simdArg0 }
}

/**
 * Unsigned shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraqNU16)
 */
public fun vsraqNU16(a: Uint16x8T, b: Uint16x8T, n: Int): Uint16x8T {
    run {
        check(n >= 1 && n <= 16)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrqNU16(b, n); _simdArg0 }
}

/**
 * Unsigned shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraNU32)
 */
public fun vsraNU32(a: Uint32x2T, b: Uint32x2T, n: Int): Uint32x2T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrNU32(b, n); _simdArg0 }
}

/**
 * Unsigned shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraqNU32)
 */
public fun vsraqNU32(a: Uint32x4T, b: Uint32x4T, n: Int): Uint32x4T {
    run {
        check(n >= 1 && n <= 32)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrqNU32(b, n); _simdArg0 }
}

/**
 * Unsigned shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraNU64)
 */
public fun vsraNU64(a: Uint64x1T, b: Uint64x1T, n: Int): Uint64x1T {
    run {
        check(n >= 1 && n <= 64)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrNU64(b, n); _simdArg0 }
}

/**
 * Unsigned shift right and accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsraqNU64)
 */
public fun vsraqNU64(a: Uint64x2T, b: Uint64x2T, n: Int): Uint64x2T {
    run {
        check(n >= 1 && n <= 64)
    }
    return run { val _simdArg0 = a; val _simdArg1 = vshrqNU64(b, n); _simdArg0 }
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F16X2(a: MutPtr, b: Float16x4x2T) {
    fun vst1F16X2(a1: Float16x4T, b1: Float16x4T, ptr: MutPtr) {}
    vst1F16X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF16X2(a: MutPtr, b: Float16x8x2T) {
    fun vst1qF16X2(a1: Float16x8T, b1: Float16x8T, ptr: MutPtr) {}
    vst1qF16X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F16X3(a: MutPtr, b: Float16x4x3T) {
    fun vst1F16X3(a1: Float16x4T, b1: Float16x4T, c: Float16x4T, ptr: MutPtr) {}
    vst1F16X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF16X3(a: MutPtr, b: Float16x8x3T) {
    fun vst1qF16X3(a1: Float16x8T, b1: Float16x8T, c: Float16x8T, ptr: MutPtr) {}
    vst1qF16X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F16X4(a: MutPtr, b: Float16x4x4T) {
    fun vst1F16X4(a1: Float16x4T, b1: Float16x4T, c: Float16x4T, d: Float16x4T, ptr: MutPtr) {}
    vst1F16X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF16X4(a: MutPtr, b: Float16x8x4T) {
    fun vst1qF16X4(a1: Float16x8T, b1: Float16x8T, c: Float16x8T, d: Float16x8T, ptr: MutPtr) {}
    vst1qF16X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F32X2(a: MutPtr, b: Float32x2x2T) {
    fun vst1F32X2(a1: Float32x2T, b1: Float32x2T, ptr: MutPtr) {}
    vst1F32X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF32X2(a: MutPtr, b: Float32x4x2T) {
    fun vst1qF32X2(a1: Float32x4T, b1: Float32x4T, ptr: MutPtr) {}
    vst1qF32X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F32X3(a: MutPtr, b: Float32x2x3T) {
    fun vst1F32X3(a1: Float32x2T, b1: Float32x2T, c: Float32x2T, ptr: MutPtr) {}
    vst1F32X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF32X3(a: MutPtr, b: Float32x4x3T) {
    fun vst1qF32X3(a1: Float32x4T, b1: Float32x4T, c: Float32x4T, ptr: MutPtr) {}
    vst1qF32X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1F32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1F32X4(a: MutPtr, b: Float32x2x4T) {
    fun vst1F32X4(a1: Float32x2T, b1: Float32x2T, c: Float32x2T, d: Float32x2T, ptr: MutPtr) {}
    vst1F32X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qF32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qF32X4(a: MutPtr, b: Float32x4x4T) {
    fun vst1qF32X4(a1: Float32x4T, b1: Float32x4T, c: Float32x4T, d: Float32x4T, ptr: MutPtr) {}
    vst1qF32X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneF16(a: MutPtr, b: Float16x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneF16(a: MutPtr, b: Float16x8T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.F16_LAYOUT)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneF32(a: MutPtr, b: Float32x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.F32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneF32(a: MutPtr, b: Float32x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.F32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneS8(a: MutPtr, b: Int8x8T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.I8)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneS8(a: MutPtr, b: Int8x16T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.I8)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneS16(a: MutPtr, b: Int16x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.I16)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneS16(a: MutPtr, b: Int16x8T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.I16)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneS32(a: MutPtr, b: Int32x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.I32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneS32(a: MutPtr, b: Int32x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.I32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneS64(a: MutPtr, b: Int64x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.I64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneU8(a: MutPtr, b: Uint8x8T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.U8)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneU8(a: MutPtr, b: Uint8x16T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.U8)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneU16(a: MutPtr, b: Uint16x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.U16)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneU16(a: MutPtr, b: Uint16x8T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.U16)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneU32(a: MutPtr, b: Uint32x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.U32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneU32(a: MutPtr, b: Uint32x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.U32)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneU64(a: MutPtr, b: Uint64x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.U64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneP8(a: MutPtr, b: Poly8x8T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = ObjectSlotLayout<P8>())
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneP8(a: MutPtr, b: Poly8x16T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 4)) { "LANE doesn't fit in 4 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = ObjectSlotLayout<P8>())
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneP16(a: MutPtr, b: Poly16x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = ObjectSlotLayout<P16>())
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneP16(a: MutPtr, b: Poly16x8T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = ObjectSlotLayout<P16>())
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneP64(a: MutPtr, b: Poly64x1T, lane: Int) {
    run {
        check(lane == 0)
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = ObjectSlotLayout<P64>())
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneS64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneS64(a: MutPtr, b: Int64x1T, lane: Int) {
    run {
        check(lane == 0)
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.I64)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1LaneU64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1LaneU64(a: MutPtr, b: Uint64x1T, lane: Int) {
    run {
        check(lane == 0)
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = PrimitiveLayouts.U64)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P64X2(a: MutPtr, b: Poly64x1x2T) {
    vst1S64X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P64X3(a: MutPtr, b: Poly64x1x3T) {
    vst1S64X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P64X4(a: MutPtr, b: Poly64x1x4T) {
    vst1S64X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP64X2(a: MutPtr, b: Poly64x2x2T) {
    vst1qS64X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP64X3(a: MutPtr, b: Poly64x2x3T) {
    vst1qS64X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP64X4(a: MutPtr, b: Poly64x2x4T) {
    vst1qS64X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S8X2(a: MutPtr, b: Int8x8x2T) {
    fun vst1S8X2(a1: Int8x8T, b1: Int8x8T, ptr: MutPtr) {}
    vst1S8X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS8X2(a: MutPtr, b: Int8x16x2T) {
    fun vst1qS8X2(a1: Int8x16T, b1: Int8x16T, ptr: MutPtr) {}
    vst1qS8X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S16X2(a: MutPtr, b: Int16x4x2T) {
    fun vst1S16X2(a1: Int16x4T, b1: Int16x4T, ptr: MutPtr) {}
    vst1S16X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS16X2(a: MutPtr, b: Int16x8x2T) {
    fun vst1qS16X2(a1: Int16x8T, b1: Int16x8T, ptr: MutPtr) {}
    vst1qS16X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S32X2(a: MutPtr, b: Int32x2x2T) {
    fun vst1S32X2(a1: Int32x2T, b1: Int32x2T, ptr: MutPtr) {}
    vst1S32X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS32X2(a: MutPtr, b: Int32x4x2T) {
    fun vst1qS32X2(a1: Int32x4T, b1: Int32x4T, ptr: MutPtr) {}
    vst1qS32X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S64X2(a: MutPtr, b: Int64x1x2T) {
    fun vst1S64X2(a1: Int64x1T, b1: Int64x1T, ptr: MutPtr) {}
    vst1S64X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS64X2(a: MutPtr, b: Int64x2x2T) {
    fun vst1qS64X2(a1: Int64x2T, b1: Int64x2T, ptr: MutPtr) {}
    vst1qS64X2(b.first, b.second, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S8X3(a: MutPtr, b: Int8x8x3T) {
    fun vst1S8X3(a1: Int8x8T, b1: Int8x8T, c: Int8x8T, ptr: MutPtr) {}
    vst1S8X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS8X3(a: MutPtr, b: Int8x16x3T) {
    fun vst1qS8X3(a1: Int8x16T, b1: Int8x16T, c: Int8x16T, ptr: MutPtr) {}
    vst1qS8X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S16X3(a: MutPtr, b: Int16x4x3T) {
    fun vst1S16X3(a1: Int16x4T, b1: Int16x4T, c: Int16x4T, ptr: MutPtr) {}
    vst1S16X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS16X3(a: MutPtr, b: Int16x8x3T) {
    fun vst1qS16X3(a1: Int16x8T, b1: Int16x8T, c: Int16x8T, ptr: MutPtr) {}
    vst1qS16X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S32X3(a: MutPtr, b: Int32x2x3T) {
    fun vst1S32X3(a1: Int32x2T, b1: Int32x2T, c: Int32x2T, ptr: MutPtr) {}
    vst1S32X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS32X3(a: MutPtr, b: Int32x4x3T) {
    fun vst1qS32X3(a1: Int32x4T, b1: Int32x4T, c: Int32x4T, ptr: MutPtr) {}
    vst1qS32X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S64X3(a: MutPtr, b: Int64x1x3T) {
    fun vst1S64X3(a1: Int64x1T, b1: Int64x1T, c: Int64x1T, ptr: MutPtr) {}
    vst1S64X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS64X3(a: MutPtr, b: Int64x2x3T) {
    fun vst1qS64X3(a1: Int64x2T, b1: Int64x2T, c: Int64x2T, ptr: MutPtr) {}
    vst1qS64X3(b.first, b.second, b.third, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S8X4(a: MutPtr, b: Int8x8x4T) {
    fun vst1S8X4(a1: Int8x8T, b1: Int8x8T, c: Int8x8T, d: Int8x8T, ptr: MutPtr) {}
    vst1S8X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS8X4(a: MutPtr, b: Int8x16x4T) {
    fun vst1qS8X4(a1: Int8x16T, b1: Int8x16T, c: Int8x16T, d: Int8x16T, ptr: MutPtr) {}
    vst1qS8X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S16X4(a: MutPtr, b: Int16x4x4T) {
    fun vst1S16X4(a1: Int16x4T, b1: Int16x4T, c: Int16x4T, d: Int16x4T, ptr: MutPtr) {}
    vst1S16X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS16X4(a: MutPtr, b: Int16x8x4T) {
    fun vst1qS16X4(a1: Int16x8T, b1: Int16x8T, c: Int16x8T, d: Int16x8T, ptr: MutPtr) {}
    vst1qS16X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S32X4(a: MutPtr, b: Int32x2x4T) {
    fun vst1S32X4(a1: Int32x2T, b1: Int32x2T, c: Int32x2T, d: Int32x2T, ptr: MutPtr) {}
    vst1S32X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS32X4(a: MutPtr, b: Int32x4x4T) {
    fun vst1qS32X4(a1: Int32x4T, b1: Int32x4T, c: Int32x4T, d: Int32x4T, ptr: MutPtr) {}
    vst1qS32X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1S64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1S64X4(a: MutPtr, b: Int64x1x4T) {
    fun vst1S64X4(a1: Int64x1T, b1: Int64x1T, c: Int64x1T, d: Int64x1T, ptr: MutPtr) {}
    vst1S64X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qS64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qS64X4(a: MutPtr, b: Int64x2x4T) {
    fun vst1qS64X4(a1: Int64x2T, b1: Int64x2T, c: Int64x2T, d: Int64x2T, ptr: MutPtr) {}
    vst1qS64X4(b.first, b.second, b.third, b.field3, a)
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U8X2(a: MutPtr, b: Uint8x8x2T) {
    vst1S8X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U8X3(a: MutPtr, b: Uint8x8x3T) {
    vst1S8X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U8X4(a: MutPtr, b: Uint8x8x4T) {
    vst1S8X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU8X2(a: MutPtr, b: Uint8x16x2T) {
    vst1qS8X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU8X3(a: MutPtr, b: Uint8x16x3T) {
    vst1qS8X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU8X4(a: MutPtr, b: Uint8x16x4T) {
    vst1qS8X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U16X2(a: MutPtr, b: Uint16x4x2T) {
    vst1S16X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U16X3(a: MutPtr, b: Uint16x4x3T) {
    vst1S16X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U16X4(a: MutPtr, b: Uint16x4x4T) {
    vst1S16X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU16X2(a: MutPtr, b: Uint16x8x2T) {
    vst1qS16X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU16X3(a: MutPtr, b: Uint16x8x3T) {
    vst1qS16X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU16X4(a: MutPtr, b: Uint16x8x4T) {
    vst1qS16X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U32X2(a: MutPtr, b: Uint32x2x2T) {
    vst1S32X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U32X3(a: MutPtr, b: Uint32x2x3T) {
    vst1S32X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U32X4(a: MutPtr, b: Uint32x2x4T) {
    vst1S32X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU32X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU32X2(a: MutPtr, b: Uint32x4x2T) {
    vst1qS32X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU32X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU32X3(a: MutPtr, b: Uint32x4x3T) {
    vst1qS32X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU32X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU32X4(a: MutPtr, b: Uint32x4x4T) {
    vst1qS32X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U64X2(a: MutPtr, b: Uint64x1x2T) {
    vst1S64X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U64X3(a: MutPtr, b: Uint64x1x3T) {
    vst1S64X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1U64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1U64X4(a: MutPtr, b: Uint64x1x4T) {
    vst1S64X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU64X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU64X2(a: MutPtr, b: Uint64x2x2T) {
    vst1qS64X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU64X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU64X3(a: MutPtr, b: Uint64x2x3T) {
    vst1qS64X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qU64X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qU64X4(a: MutPtr, b: Uint64x2x4T) {
    vst1qS64X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P8X2(a: MutPtr, b: Poly8x8x2T) {
    vst1S8X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P8X3(a: MutPtr, b: Poly8x8x3T) {
    vst1S8X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P8X4(a: MutPtr, b: Poly8x8x4T) {
    vst1S8X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP8X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP8X2(a: MutPtr, b: Poly8x16x2T) {
    vst1qS8X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP8X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP8X3(a: MutPtr, b: Poly8x16x3T) {
    vst1qS8X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP8X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP8X4(a: MutPtr, b: Poly8x16x4T) {
    vst1qS8X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P16X2(a: MutPtr, b: Poly16x4x2T) {
    vst1S16X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P16X3(a: MutPtr, b: Poly16x4x3T) {
    vst1S16X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1P16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1P16X4(a: MutPtr, b: Poly16x4x4T) {
    vst1S16X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP16X2)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP16X2(a: MutPtr, b: Poly16x8x2T) {
    vst1qS16X2(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP16X3)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP16X3(a: MutPtr, b: Poly16x8x3T) {
    vst1qS16X3(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures to one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qP16X4)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qP16X4(a: MutPtr, b: Poly16x8x4T) {
    vst1qS16X4(transmute(a), transmute(b))
}

/**
 * Store multiple single-element structures from one, two, three, or four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst1qLaneP64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst1qLaneP64(a: MutPtr, b: Poly64x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    writeUnaligned(a, run { val _simdArg0 = b; val _simdArg1 = lane.toUInt(); _simdArg0 }, typeLayoutT = ObjectSlotLayout<P64>())
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2F16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2F16(a: MutPtr, b: Float16x4x2T) {
    fun vst2F16(a1: Float16x4T, b1: Float16x4T, ptr: MutPtr) {}
    vst2F16(b.first, b.second, ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qF16(a: MutPtr, b: Float16x8x2T) {
    fun vst2qF16(a1: Float16x8T, b1: Float16x8T, ptr: MutPtr) {}
    vst2qF16(b.first, b.second, ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2F32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2F32(a: MutPtr, b: Float32x2x2T) {
    run {
        val w: W = simdShuffle(b.first, b.second, interleaveMask, 2, 2, 2, 2 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qF32(a: MutPtr, b: Float32x4x2T) {
    run {
        val w: W = simdShuffle(b.first, b.second, interleaveMask, 4, 2, 4, 2 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2S8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2S8(a: MutPtr, b: Int8x8x2T) {
    run {
        val w: W = simdShuffle(b.first, b.second, interleaveMask, 8, 2, 8, 2 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qS8(a: MutPtr, b: Int8x16x2T) {
    run {
        val w: W = simdShuffle(b.first, b.second, interleaveMask, 16, 2, 16, 2 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2S16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2S16(a: MutPtr, b: Int16x4x2T) {
    run {
        val w: W = simdShuffle(b.first, b.second, interleaveMask, 4, 2, 4, 2 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qS16(a: MutPtr, b: Int16x8x2T) {
    run {
        val w: W = simdShuffle(b.first, b.second, interleaveMask, 8, 2, 8, 2 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2S32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2S32(a: MutPtr, b: Int32x2x2T) {
    run {
        val w: W = simdShuffle(b.first, b.second, interleaveMask, 2, 2, 2, 2 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qS32(a: MutPtr, b: Int32x4x2T) {
    run {
        val w: W = simdShuffle(b.first, b.second, interleaveMask, 4, 2, 4, 2 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneF16(a: MutPtr, b: Float16x4x2T, lane: Int) {
    fun vst2LaneF16(a1: Float16x4T, b1: Float16x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst2LaneF16(b.first, b.second, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneF16(a: MutPtr, b: Float16x8x2T, lane: Int) {
    fun vst2qLaneF16(a1: Float16x8T, b1: Float16x8T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst2qLaneF16(b.first, b.second, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneF32(a: MutPtr, b: Float32x2x2T, lane: Int) {
    fun vst2LaneF32(a1: Float32x2T, b1: Float32x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst2LaneF32(b.first, b.second, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneF32(a: MutPtr, b: Float32x4x2T, lane: Int) {
    fun vst2qLaneF32(a1: Float32x4T, b1: Float32x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst2qLaneF32(b.first, b.second, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneS8(a: MutPtr, b: Int8x8x2T, lane: Int) {
    fun vst2LaneS8(a1: Int8x8T, b1: Int8x8T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst2LaneS8(b.first, b.second, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneS16(a: MutPtr, b: Int16x4x2T, lane: Int) {
    fun vst2LaneS16(a1: Int16x4T, b1: Int16x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst2LaneS16(b.first, b.second, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneS16(a: MutPtr, b: Int16x8x2T, lane: Int) {
    fun vst2qLaneS16(a1: Int16x8T, b1: Int16x8T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst2qLaneS16(b.first, b.second, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneS32(a: MutPtr, b: Int32x2x2T, lane: Int) {
    fun vst2LaneS32(a1: Int32x2T, b1: Int32x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst2LaneS32(b.first, b.second, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneS32(a: MutPtr, b: Int32x4x2T, lane: Int) {
    fun vst2qLaneS32(a1: Int32x4T, b1: Int32x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst2qLaneS32(b.first, b.second, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneU8(a: MutPtr, b: Uint8x8x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst2LaneS8(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneU16(a: MutPtr, b: Uint16x4x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst2LaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneU16(a: MutPtr, b: Uint16x8x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst2qLaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneU32(a: MutPtr, b: Uint32x2x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst2LaneS32(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneU32(a: MutPtr, b: Uint32x4x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst2qLaneS32(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneP8(a: MutPtr, b: Poly8x8x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst2LaneS8(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2LaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2LaneP16(a: MutPtr, b: Poly16x4x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst2LaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qLaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qLaneP16(a: MutPtr, b: Poly16x8x2T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst2qLaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2P64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2P64(a: MutPtr, b: Poly64x1x2T) {
    vst2S64(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2S64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2S64(a: MutPtr, b: Int64x1x2T) {
    writeUnaligned(a, b)
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2U64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2U64(a: MutPtr, b: Uint64x1x2T) {
    vst2S64(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2U8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2U8(a: MutPtr, b: Uint8x8x2T) {
    vst2S8(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qU8(a: MutPtr, b: Uint8x16x2T) {
    vst2qS8(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2U16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2U16(a: MutPtr, b: Uint16x4x2T) {
    vst2S16(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qU16(a: MutPtr, b: Uint16x8x2T) {
    vst2qS16(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2U32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2U32(a: MutPtr, b: Uint32x2x2T) {
    vst2S32(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qU32(a: MutPtr, b: Uint32x4x2T) {
    vst2qS32(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2P8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2P8(a: MutPtr, b: Poly8x8x2T) {
    vst2S8(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qP8(a: MutPtr, b: Poly8x16x2T) {
    vst2qS8(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2P16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2P16(a: MutPtr, b: Poly16x4x2T) {
    vst2S16(transmute(a), transmute(b))
}

/**
 * Store multiple 2-element structures from two registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst2qP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst2qP16(a: MutPtr, b: Poly16x8x2T) {
    vst2qS16(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3F16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3F16(a: MutPtr, b: Float16x4x3T) {
    fun vst3F16(a1: Float16x4T, b1: Float16x4T, c: Float16x4T, ptr: MutPtr) {}
    vst3F16(b.first, b.second, b.third, ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qF16(a: MutPtr, b: Float16x8x3T) {
    fun vst3qF16(a1: Float16x8T, b1: Float16x8T, c: Float16x8T, ptr: MutPtr) {}
    vst3qF16(b.first, b.second, b.third, ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3F32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3F32(a: MutPtr, b: Float32x2x3T) {
    run {
        val v0v1: Simd<Float> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(2 * 2)); _simdArg0 }
        val v2v2: Simd<Float> = run { val _simdArg0 = b.third; val _simdArg1 = b.third; val _simdArg2 = SimdShuffleIdx(identity(2 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v2, interleaveMask, 2, 3, 2, 3 > Unit)
        val arr: FloatArray = transmuteCopy(w)
        (PrimitiveArrayLayouts.floatArray((2 * 3).toInt())).writeNative((MutPtr((a).rawAddress)).rawAddress, arr)
    }
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qF32(a: MutPtr, b: Float32x4x3T) {
    run {
        val v0v1: Simd<Float> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val v2v2: Simd<Float> = run { val _simdArg0 = b.third; val _simdArg1 = b.third; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v2, interleaveMask, 4, 3, 4, 3 > Unit)
        val arr: FloatArray = transmuteCopy(w)
        (PrimitiveArrayLayouts.floatArray((4 * 3).toInt())).writeNative((MutPtr((a).rawAddress)).rawAddress, arr)
    }
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3S8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3S8(a: MutPtr, b: Int8x8x3T) {
    run {
        val v0v1: Simd<Byte> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(8 * 2)); _simdArg0 }
        val v2v2: Simd<Byte> = run { val _simdArg0 = b.third; val _simdArg1 = b.third; val _simdArg2 = SimdShuffleIdx(identity(8 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v2, interleaveMask, 8, 3, 8, 3 > Unit)
        val arr: ByteArray = transmuteCopy(w)
        (PrimitiveArrayLayouts.byteArray((8 * 3).toInt())).writeNative((MutPtr((a).rawAddress)).rawAddress, arr)
    }
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qS8(a: MutPtr, b: Int8x16x3T) {
    run {
        val v0v1: Simd<Byte> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(16 * 2)); _simdArg0 }
        val v2v2: Simd<Byte> = run { val _simdArg0 = b.third; val _simdArg1 = b.third; val _simdArg2 = SimdShuffleIdx(identity(16 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v2, interleaveMask, 16, 3, 16, 3 > Unit)
        val arr: ByteArray = transmuteCopy(w)
        (PrimitiveArrayLayouts.byteArray((16 * 3).toInt())).writeNative((MutPtr((a).rawAddress)).rawAddress, arr)
    }
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3S16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3S16(a: MutPtr, b: Int16x4x3T) {
    run {
        val v0v1: Simd<Short> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val v2v2: Simd<Short> = run { val _simdArg0 = b.third; val _simdArg1 = b.third; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v2, interleaveMask, 4, 3, 4, 3 > Unit)
        val arr: ShortArray = transmuteCopy(w)
        (PrimitiveArrayLayouts.shortArray((4 * 3).toInt())).writeNative((MutPtr((a).rawAddress)).rawAddress, arr)
    }
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qS16(a: MutPtr, b: Int16x8x3T) {
    run {
        val v0v1: Simd<Short> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(8 * 2)); _simdArg0 }
        val v2v2: Simd<Short> = run { val _simdArg0 = b.third; val _simdArg1 = b.third; val _simdArg2 = SimdShuffleIdx(identity(8 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v2, interleaveMask, 8, 3, 8, 3 > Unit)
        val arr: ShortArray = transmuteCopy(w)
        (PrimitiveArrayLayouts.shortArray((8 * 3).toInt())).writeNative((MutPtr((a).rawAddress)).rawAddress, arr)
    }
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3S32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3S32(a: MutPtr, b: Int32x2x3T) {
    run {
        val v0v1: Simd<Int> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(2 * 2)); _simdArg0 }
        val v2v2: Simd<Int> = run { val _simdArg0 = b.third; val _simdArg1 = b.third; val _simdArg2 = SimdShuffleIdx(identity(2 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v2, interleaveMask, 2, 3, 2, 3 > Unit)
        val arr: IntArray = transmuteCopy(w)
        (PrimitiveArrayLayouts.intArray((2 * 3).toInt())).writeNative((MutPtr((a).rawAddress)).rawAddress, arr)
    }
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qS32(a: MutPtr, b: Int32x4x3T) {
    run {
        val v0v1: Simd<Int> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val v2v2: Simd<Int> = run { val _simdArg0 = b.third; val _simdArg1 = b.third; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v2, interleaveMask, 4, 3, 4, 3 > Unit)
        val arr: IntArray = transmuteCopy(w)
        (PrimitiveArrayLayouts.intArray((4 * 3).toInt())).writeNative((MutPtr((a).rawAddress)).rawAddress, arr)
    }
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneF16(a: MutPtr, b: Float16x4x3T, lane: Int) {
    fun vst3LaneF16(a1: Float16x4T, b1: Float16x4T, c: Float16x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst3LaneF16(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneF16(a: MutPtr, b: Float16x8x3T, lane: Int) {
    fun vst3qLaneF16(a1: Float16x8T, b1: Float16x8T, c: Float16x8T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst3qLaneF16(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneF32(a: MutPtr, b: Float32x2x3T, lane: Int) {
    fun vst3LaneF32(a1: Float32x2T, b1: Float32x2T, c: Float32x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst3LaneF32(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneF32(a: MutPtr, b: Float32x4x3T, lane: Int) {
    fun vst3qLaneF32(a1: Float32x4T, b1: Float32x4T, c: Float32x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst3qLaneF32(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneS8(a: MutPtr, b: Int8x8x3T, lane: Int) {
    fun vst3LaneS8(a1: Int8x8T, b1: Int8x8T, c: Int8x8T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst3LaneS8(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneS16(a: MutPtr, b: Int16x4x3T, lane: Int) {
    fun vst3LaneS16(a1: Int16x4T, b1: Int16x4T, c: Int16x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst3LaneS16(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneS16(a: MutPtr, b: Int16x8x3T, lane: Int) {
    fun vst3qLaneS16(a1: Int16x8T, b1: Int16x8T, c: Int16x8T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst3qLaneS16(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneS32(a: MutPtr, b: Int32x2x3T, lane: Int) {
    fun vst3LaneS32(a1: Int32x2T, b1: Int32x2T, c: Int32x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst3LaneS32(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneS32(a: MutPtr, b: Int32x4x3T, lane: Int) {
    fun vst3qLaneS32(a1: Int32x4T, b1: Int32x4T, c: Int32x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst3qLaneS32(b.first, b.second, b.third, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneU8(a: MutPtr, b: Uint8x8x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst3LaneS8(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneU16(a: MutPtr, b: Uint16x4x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst3LaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneU16(a: MutPtr, b: Uint16x8x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst3qLaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneU32(a: MutPtr, b: Uint32x2x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst3LaneS32(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneU32(a: MutPtr, b: Uint32x4x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst3qLaneS32(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneP8(a: MutPtr, b: Poly8x8x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst3LaneS8(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3LaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3LaneP16(a: MutPtr, b: Poly16x4x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst3LaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qLaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qLaneP16(a: MutPtr, b: Poly16x8x3T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst3qLaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3P64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3P64(a: MutPtr, b: Poly64x1x3T) {
    vst3S64(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3S64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3S64(a: MutPtr, b: Int64x1x3T) {
    writeUnaligned(a, b)
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3U64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3U64(a: MutPtr, b: Uint64x1x3T) {
    vst3S64(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3U8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3U8(a: MutPtr, b: Uint8x8x3T) {
    vst3S8(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qU8(a: MutPtr, b: Uint8x16x3T) {
    vst3qS8(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3U16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3U16(a: MutPtr, b: Uint16x4x3T) {
    vst3S16(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qU16(a: MutPtr, b: Uint16x8x3T) {
    vst3qS16(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3U32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3U32(a: MutPtr, b: Uint32x2x3T) {
    vst3S32(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qU32(a: MutPtr, b: Uint32x4x3T) {
    vst3qS32(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3P8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3P8(a: MutPtr, b: Poly8x8x3T) {
    vst3S8(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qP8(a: MutPtr, b: Poly8x16x3T) {
    vst3qS8(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3P16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3P16(a: MutPtr, b: Poly16x4x3T) {
    vst3S16(transmute(a), transmute(b))
}

/**
 * Store multiple 3-element structures from three registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst3qP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst3qP16(a: MutPtr, b: Poly16x8x3T) {
    vst3qS16(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4F16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4F16(a: MutPtr, b: Float16x4x4T) {
    fun vst4F16(a1: Float16x4T, b1: Float16x4T, c: Float16x4T, d: Float16x4T, ptr: MutPtr) {}
    vst4F16(b.first, b.second, b.third, b.field3, ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qF16(a: MutPtr, b: Float16x8x4T) {
    fun vst4qF16(a1: Float16x8T, b1: Float16x8T, c: Float16x8T, d: Float16x8T, ptr: MutPtr) {}
    vst4qF16(b.first, b.second, b.third, b.field3, ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4F32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4F32(a: MutPtr, b: Float32x2x4T) {
    run {
        val v0v1: Simd<Float> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(2 * 2)); _simdArg0 }
        val v2v3: Simd<Float> = run { val _simdArg0 = b.third; val _simdArg1 = b.field3; val _simdArg2 = SimdShuffleIdx(identity(2 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v3, interleaveMask, 2, 4, 2, 4 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qF32(a: MutPtr, b: Float32x4x4T) {
    run {
        val v0v1: Simd<Float> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val v2v3: Simd<Float> = run { val _simdArg0 = b.third; val _simdArg1 = b.field3; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v3, interleaveMask, 4, 4, 4, 4 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4S8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4S8(a: MutPtr, b: Int8x8x4T) {
    run {
        val v0v1: Simd<Byte> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(8 * 2)); _simdArg0 }
        val v2v3: Simd<Byte> = run { val _simdArg0 = b.third; val _simdArg1 = b.field3; val _simdArg2 = SimdShuffleIdx(identity(8 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v3, interleaveMask, 8, 4, 8, 4 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qS8(a: MutPtr, b: Int8x16x4T) {
    run {
        val v0v1: Simd<Byte> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(16 * 2)); _simdArg0 }
        val v2v3: Simd<Byte> = run { val _simdArg0 = b.third; val _simdArg1 = b.field3; val _simdArg2 = SimdShuffleIdx(identity(16 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v3, interleaveMask, 16, 4, 16, 4 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4S16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4S16(a: MutPtr, b: Int16x4x4T) {
    run {
        val v0v1: Simd<Short> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val v2v3: Simd<Short> = run { val _simdArg0 = b.third; val _simdArg1 = b.field3; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v3, interleaveMask, 4, 4, 4, 4 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qS16(a: MutPtr, b: Int16x8x4T) {
    run {
        val v0v1: Simd<Short> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(8 * 2)); _simdArg0 }
        val v2v3: Simd<Short> = run { val _simdArg0 = b.third; val _simdArg1 = b.field3; val _simdArg2 = SimdShuffleIdx(identity(8 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v3, interleaveMask, 8, 4, 8, 4 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4S32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4S32(a: MutPtr, b: Int32x2x4T) {
    run {
        val v0v1: Simd<Int> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(2 * 2)); _simdArg0 }
        val v2v3: Simd<Int> = run { val _simdArg0 = b.third; val _simdArg1 = b.field3; val _simdArg2 = SimdShuffleIdx(identity(2 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v3, interleaveMask, 2, 4, 2, 4 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qS32(a: MutPtr, b: Int32x4x4T) {
    run {
        val v0v1: Simd<Int> = run { val _simdArg0 = b.first; val _simdArg1 = b.second; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val v2v3: Simd<Int> = run { val _simdArg0 = b.third; val _simdArg1 = b.field3; val _simdArg2 = SimdShuffleIdx(identity(4 * 2)); _simdArg0 }
        val w: W = simdShuffle(v0v1, v2v3, interleaveMask, 4, 4, 4, 4 > Unit)
        writeUnaligned(MutPtr(a.rawAddress, a.rawMeta), w, typeLayoutT = ObjectSlotLayout<W>())
    }
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneF16(a: MutPtr, b: Float16x4x4T, lane: Int) {
    fun vst4LaneF16(a1: Float16x4T, b1: Float16x4T, c: Float16x4T, d: Float16x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst4LaneF16(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneF16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneF16(a: MutPtr, b: Float16x8x4T, lane: Int) {
    fun vst4qLaneF16(a1: Float16x8T, b1: Float16x8T, c: Float16x8T, d: Float16x8T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst4qLaneF16(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneF32(a: MutPtr, b: Float32x2x4T, lane: Int) {
    fun vst4LaneF32(a1: Float32x2T, b1: Float32x2T, c: Float32x2T, d: Float32x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst4LaneF32(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneF32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneF32(a: MutPtr, b: Float32x4x4T, lane: Int) {
    fun vst4qLaneF32(a1: Float32x4T, b1: Float32x4T, c: Float32x4T, d: Float32x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst4qLaneF32(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneS8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneS8(a: MutPtr, b: Int8x8x4T, lane: Int) {
    fun vst4LaneS8(a1: Int8x8T, b1: Int8x8T, c: Int8x8T, d: Int8x8T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst4LaneS8(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneS16(a: MutPtr, b: Int16x4x4T, lane: Int) {
    fun vst4LaneS16(a1: Int16x4T, b1: Int16x4T, c: Int16x4T, d: Int16x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst4LaneS16(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneS16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneS16(a: MutPtr, b: Int16x8x4T, lane: Int) {
    fun vst4qLaneS16(a1: Int16x8T, b1: Int16x8T, c: Int16x8T, d: Int16x8T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst4qLaneS16(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneS32(a: MutPtr, b: Int32x2x4T, lane: Int) {
    fun vst4LaneS32(a1: Int32x2T, b1: Int32x2T, c: Int32x2T, d: Int32x2T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst4LaneS32(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneS32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneS32(a: MutPtr, b: Int32x4x4T, lane: Int) {
    fun vst4qLaneS32(a1: Int32x4T, b1: Int32x4T, c: Int32x4T, d: Int32x4T, n: Long, ptr: MutPtr) {}
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst4qLaneS32(b.first, b.second, b.third, b.field3, lane.toLong(), ((a as Any?) as Unit))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneU8(a: MutPtr, b: Uint8x8x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst4LaneS8(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneU16(a: MutPtr, b: Uint16x4x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst4LaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneU16(a: MutPtr, b: Uint16x8x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst4qLaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneU32(a: MutPtr, b: Uint32x2x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    vst4LaneS32(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneU32(a: MutPtr, b: Uint32x4x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst4qLaneS32(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneP8(a: MutPtr, b: Poly8x8x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst4LaneS8(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4LaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4LaneP16(a: MutPtr, b: Poly16x4x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    vst4LaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qLaneP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qLaneP16(a: MutPtr, b: Poly16x8x4T, lane: Int) {
    run {
        check(0 <= lane && lane < (1 shl 3)) { "LANE doesn't fit in 3 bits (unsigned)" }
    }
    vst4qLaneS16(transmute(a), transmute(b), lane)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4P64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4P64(a: MutPtr, b: Poly64x1x4T) {
    vst4S64(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4S64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4S64(a: MutPtr, b: Int64x1x4T) {
    writeUnaligned(a, b)
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4U64)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4U64(a: MutPtr, b: Uint64x1x4T) {
    vst4S64(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4U8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4U8(a: MutPtr, b: Uint8x8x4T) {
    vst4S8(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qU8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qU8(a: MutPtr, b: Uint8x16x4T) {
    vst4qS8(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4U16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4U16(a: MutPtr, b: Uint16x4x4T) {
    vst4S16(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qU16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qU16(a: MutPtr, b: Uint16x8x4T) {
    vst4qS16(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4U32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4U32(a: MutPtr, b: Uint32x2x4T) {
    vst4S32(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qU32)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qU32(a: MutPtr, b: Uint32x4x4T) {
    vst4qS32(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4P8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4P8(a: MutPtr, b: Poly8x8x4T) {
    vst4S8(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qP8)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qP8(a: MutPtr, b: Poly8x16x4T) {
    vst4qS8(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4P16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4P16(a: MutPtr, b: Poly16x4x4T) {
    vst4S16(transmute(a), transmute(b))
}

/**
 * Store multiple 4-element structures from four registers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vst4qP16)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vst4qP16(a: MutPtr, b: Poly16x8x4T) {
    vst4qS16(transmute(a), transmute(b))
}

/**
 * Store SIMD&FP register (immediate offset)
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vstrqP128)
 * ## Safety
 *  * Neon intrinsic unchecked
 */
public fun vstrqP128(a: MutPtr, b: P128) {
    val a1 = a
    (ObjectSlotLayout<P128>()).writeNative((a1).rawAddress, b)
}

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubF16)
 */
public fun vsubF16(a: Float16x4T, b: Float16x4T): Float16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqF16)
 */
public fun vsubqF16(a: Float16x8T, b: Float16x8T): Float16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubF32)
 */
public fun vsubF32(a: Float32x2T, b: Float32x2T): Float32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqF32)
 */
public fun vsubqF32(a: Float32x4T, b: Float32x4T): Float32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubS16)
 */
public fun vsubS16(a: Int16x4T, b: Int16x4T): Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqS16)
 */
public fun vsubqS16(a: Int16x8T, b: Int16x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubU16)
 */
public fun vsubU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqU16)
 */
public fun vsubqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubS32)
 */
public fun vsubS32(a: Int32x2T, b: Int32x2T): Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqS32)
 */
public fun vsubqS32(a: Int32x4T, b: Int32x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubU32)
 */
public fun vsubU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqU32)
 */
public fun vsubqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubS64)
 */
public fun vsubS64(a: Int64x1T, b: Int64x1T): Int64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqS64)
 */
public fun vsubqS64(a: Int64x2T, b: Int64x2T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubU64)
 */
public fun vsubU64(a: Uint64x1T, b: Uint64x1T): Uint64x1T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqU64)
 */
public fun vsubqU64(a: Uint64x2T, b: Uint64x2T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubS8)
 */
public fun vsubS8(a: Int8x8T, b: Int8x8T): Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqS8)
 */
public fun vsubqS8(a: Int8x16T, b: Int8x16T): Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubU8)
 */
public fun vsubU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubqU8)
 */
public fun vsubqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnHighS16)
 */
public fun vsubhnHighS16(a: Int8x8T, b: Int16x8T, c: Int16x8T): Int8x16T {
    val d = vsubhnS16(b, c)
    return vcombineS8(a, d)
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnHighS32)
 */
public fun vsubhnHighS32(a: Int16x4T, b: Int32x4T, c: Int32x4T): Int16x8T {
    val d = vsubhnS32(b, c)
    return vcombineS16(a, d)
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnHighS64)
 */
public fun vsubhnHighS64(a: Int32x2T, b: Int64x2T, c: Int64x2T): Int32x4T {
    val d = vsubhnS64(b, c)
    return vcombineS32(a, d)
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnHighU16)
 */
public fun vsubhnHighU16(a: Uint8x8T, b: Uint16x8T, c: Uint16x8T): Uint8x16T {
    val d = vsubhnU16(b, c)
    return vcombineU8(a, d)
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnHighU32)
 */
public fun vsubhnHighU32(a: Uint16x4T, b: Uint32x4T, c: Uint32x4T): Uint16x8T {
    val d = vsubhnU32(b, c)
    return vcombineU16(a, d)
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnHighU64)
 */
public fun vsubhnHighU64(a: Uint32x2T, b: Uint64x2T, c: Uint64x2T): Uint32x4T {
    val d = vsubhnU64(b, c)
    return vcombineU32(a, d)
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnS16)
 */
public fun vsubhnS16(a: Int16x8T, b: Int16x8T): Int8x8T {
    val c: I16x8 = i16x8.new(8, 8, 8, 8, 8, 8, 8, 8)
    return run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 }
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnS32)
 */
public fun vsubhnS32(a: Int32x4T, b: Int32x4T): Int16x4T {
    val c: I32x4 = i32x4.new(16, 16, 16, 16)
    return run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 }
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnS64)
 */
public fun vsubhnS64(a: Int64x2T, b: Int64x2T): Int32x2T {
    val c: I64x2 = i64x2.new(32, 32)
    return run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 }
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnU16)
 */
public fun vsubhnU16(a: Uint16x8T, b: Uint16x8T): Uint8x8T {
    val c: U16x8 = u16x8.new(8, 8, 8, 8, 8, 8, 8, 8)
    return run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 }
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnU32)
 */
public fun vsubhnU32(a: Uint32x4T, b: Uint32x4T): Uint16x4T {
    val c: U32x4 = u32x4.new(16, 16, 16, 16)
    return run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 }
}

/**
 * Subtract returning high narrow
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubhnU64)
 */
public fun vsubhnU64(a: Uint64x2T, b: Uint64x2T): Uint32x2T {
    val c: U64x2 = u64x2.new(32, 32)
    return run { val _simdArg0 = run { val _simdArg0 = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }; val _simdArg1 = transmute(c); _simdArg0 }; _simdArg0 }
}

/**
 * Signed Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublS8)
 */
public fun vsublS8(a: Int8x8T, b: Int8x8T): Int16x8T =
    run {
        val c: Int16x8T = run { val _simdArg0 = a; Int16x8T() }
        val d: Int16x8T = run { val _simdArg0 = b; Int16x8T() }
        run { val _simdArg0 = c; val _simdArg1 = d; _simdArg0 }
    }

/**
 * Signed Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublS16)
 */
public fun vsublS16(a: Int16x4T, b: Int16x4T): Int32x4T =
    run {
        val c: Int32x4T = run { val _simdArg0 = a; Int32x4T() }
        val d: Int32x4T = run { val _simdArg0 = b; Int32x4T() }
        run { val _simdArg0 = c; val _simdArg1 = d; _simdArg0 }
    }

/**
 * Signed Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublS32)
 */
public fun vsublS32(a: Int32x2T, b: Int32x2T): Int64x2T =
    run {
        val c: Int64x2T = run { val _simdArg0 = a; Int64x2T() }
        val d: Int64x2T = run { val _simdArg0 = b; Int64x2T() }
        run { val _simdArg0 = c; val _simdArg1 = d; _simdArg0 }
    }

/**
 * Unsigned Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublU8)
 */
public fun vsublU8(a: Uint8x8T, b: Uint8x8T): Uint16x8T =
    run {
        val c: Uint16x8T = run { val _simdArg0 = a; Uint16x8T() }
        val d: Uint16x8T = run { val _simdArg0 = b; Uint16x8T() }
        run { val _simdArg0 = c; val _simdArg1 = d; _simdArg0 }
    }

/**
 * Unsigned Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublU16)
 */
public fun vsublU16(a: Uint16x4T, b: Uint16x4T): Uint32x4T =
    run {
        val c: Uint32x4T = run { val _simdArg0 = a; Uint32x4T() }
        val d: Uint32x4T = run { val _simdArg0 = b; Uint32x4T() }
        run { val _simdArg0 = c; val _simdArg1 = d; _simdArg0 }
    }

/**
 * Unsigned Subtract Long
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsublU32)
 */
public fun vsublU32(a: Uint32x2T, b: Uint32x2T): Uint64x2T =
    run {
        val c: Uint64x2T = run { val _simdArg0 = a; Uint64x2T() }
        val d: Uint64x2T = run { val _simdArg0 = b; Uint64x2T() }
        run { val _simdArg0 = c; val _simdArg1 = d; _simdArg0 }
    }

/**
 * Signed Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwS8)
 */
public fun vsubwS8(a: Int16x8T, b: Int8x8T): Int16x8T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Signed Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwS16)
 */
public fun vsubwS16(a: Int32x4T, b: Int16x4T): Int32x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Signed Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwS32)
 */
public fun vsubwS32(a: Int64x2T, b: Int32x2T): Int64x2T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Unsigned Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwU8)
 */
public fun vsubwU8(a: Uint16x8T, b: Uint8x8T): Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Unsigned Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwU16)
 */
public fun vsubwU16(a: Uint32x4T, b: Uint16x4T): Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Unsigned Subtract Wide
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsubwU32)
 */
public fun vsubwU32(a: Uint64x2T, b: Uint32x2T): Uint64x2T = run { val _simdArg0 = a; val _simdArg1 = run { val _simdArg0 = b; _simdArg0 }; _simdArg0 }

/**
 * Dot product index form with signed and unsigned integers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsudotLaneS32)
 */
public fun vsudotLaneS32(a: Int32x2T, b: Int8x8T, c: Uint8x8T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32U8(c)
    val c2 = vdupLaneU32(c1, lane)
    return vusdotS32(a, vreinterpretU8U32(c2), b)
}

/**
 * Dot product index form with signed and unsigned integers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsudotqLaneS32)
 */
public fun vsudotqLaneS32(a: Int32x4T, b: Int8x16T, c: Uint8x8T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretU32U8(c)
    val c2 = vdupqLaneU32(c1, lane)
    return vusdotqS32(a, vreinterpretqU8U32(c2), b)
}

/**
 * Dot product index form with signed and unsigned integers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsudotLaneqS32)
 */
public fun vsudotLaneqS32(a: Int32x2T, b: Int8x8T, c: Uint8x16T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32U8(c)
    val c2 = vdupLaneqU32(c1, lane)
    return vusdotS32(a, vreinterpretU8U32(c2), b)
}

/**
 * Dot product index form with signed and unsigned integers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vsudotqLaneqS32)
 */
public fun vsudotqLaneqS32(a: Int32x4T, b: Int8x16T, c: Uint8x16T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqU32U8(c)
    val c2 = vdupqLaneqU32(c1, lane)
    return vusdotqS32(a, vreinterpretqU8U32(c2), b)
}

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnF16)
 */
public fun vtrnF16(a: Float16x4T, b: Float16x4T): Float16x4x2T =
    run {
        val a1: Float16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 2, 6)); Float16x4T() }
        val b1: Float16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 5, 3, 7)); Float16x4T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnqF16)
 */
public fun vtrnqF16(a: Float16x8T, b: Float16x8T): Float16x8x2T =
    run {
        val a1: Float16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 2, 10, 4, 12, 6, 14)); Float16x8T() }
        val b1: Float16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 9, 3, 11, 5, 13, 7, 15)); Float16x8T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnF32)
 */
public fun vtrnF32(a: Float32x2T, b: Float32x2T): Float32x2x2T =
    run {
        val a1: Float32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); Float32x2T() }
        val b1: Float32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); Float32x2T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnS32)
 */
public fun vtrnS32(a: Int32x2T, b: Int32x2T): Int32x2x2T =
    run {
        val a1: Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); Int32x2T() }
        val b1: Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); Int32x2T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnU32)
 */
public fun vtrnU32(a: Uint32x2T, b: Uint32x2T): Uint32x2x2T =
    run {
        val a1: Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); Uint32x2T() }
        val b1: Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); Uint32x2T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnqF32)
 */
public fun vtrnqF32(a: Float32x4T, b: Float32x4T): Float32x4x2T =
    run {
        val a1: Float32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 2, 6)); Float32x4T() }
        val b1: Float32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 5, 3, 7)); Float32x4T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnS8)
 */
public fun vtrnS8(a: Int8x8T, b: Int8x8T): Int8x8x2T =
    run {
        val a1: Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 2, 10, 4, 12, 6, 14)); Int8x8T() }
        val b1: Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 9, 3, 11, 5, 13, 7, 15)); Int8x8T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnqS8)
 */
public fun vtrnqS8(a: Int8x16T, b: Int8x16T): Int8x16x2T =
    run {
        val a1: Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 16, 2, 18, 4, 20, 6, 22, 8, 24, 10, 26, 12, 28, 14, 30)); Int8x16T() }
        val b1: Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 17, 3, 19, 5, 21, 7, 23, 9, 25, 11, 27, 13, 29, 15, 31)); Int8x16T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnS16)
 */
public fun vtrnS16(a: Int16x4T, b: Int16x4T): Int16x4x2T =
    run {
        val a1: Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 2, 6)); Int16x4T() }
        val b1: Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 5, 3, 7)); Int16x4T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnqS16)
 */
public fun vtrnqS16(a: Int16x8T, b: Int16x8T): Int16x8x2T =
    run {
        val a1: Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 2, 10, 4, 12, 6, 14)); Int16x8T() }
        val b1: Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 9, 3, 11, 5, 13, 7, 15)); Int16x8T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnqS32)
 */
public fun vtrnqS32(a: Int32x4T, b: Int32x4T): Int32x4x2T =
    run {
        val a1: Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 2, 6)); Int32x4T() }
        val b1: Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 5, 3, 7)); Int32x4T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnU8)
 */
public fun vtrnU8(a: Uint8x8T, b: Uint8x8T): Uint8x8x2T =
    run {
        val a1: Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 2, 10, 4, 12, 6, 14)); Uint8x8T() }
        val b1: Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 9, 3, 11, 5, 13, 7, 15)); Uint8x8T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnqU8)
 */
public fun vtrnqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16x2T =
    run {
        val a1: Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 16, 2, 18, 4, 20, 6, 22, 8, 24, 10, 26, 12, 28, 14, 30)); Uint8x16T() }
        val b1: Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 17, 3, 19, 5, 21, 7, 23, 9, 25, 11, 27, 13, 29, 15, 31)); Uint8x16T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnU16)
 */
public fun vtrnU16(a: Uint16x4T, b: Uint16x4T): Uint16x4x2T =
    run {
        val a1: Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 2, 6)); Uint16x4T() }
        val b1: Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 5, 3, 7)); Uint16x4T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnqU16)
 */
public fun vtrnqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8x2T =
    run {
        val a1: Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 2, 10, 4, 12, 6, 14)); Uint16x8T() }
        val b1: Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 9, 3, 11, 5, 13, 7, 15)); Uint16x8T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnqU32)
 */
public fun vtrnqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4x2T =
    run {
        val a1: Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 2, 6)); Uint32x4T() }
        val b1: Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 5, 3, 7)); Uint32x4T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnP8)
 */
public fun vtrnP8(a: Poly8x8T, b: Poly8x8T): Poly8x8x2T =
    run {
        val a1: Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 2, 10, 4, 12, 6, 14)); Poly8x8T() }
        val b1: Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 9, 3, 11, 5, 13, 7, 15)); Poly8x8T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnqP8)
 */
public fun vtrnqP8(a: Poly8x16T, b: Poly8x16T): Poly8x16x2T =
    run {
        val a1: Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 16, 2, 18, 4, 20, 6, 22, 8, 24, 10, 26, 12, 28, 14, 30)); Poly8x16T() }
        val b1: Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 17, 3, 19, 5, 21, 7, 23, 9, 25, 11, 27, 13, 29, 15, 31)); Poly8x16T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnP16)
 */
public fun vtrnP16(a: Poly16x4T, b: Poly16x4T): Poly16x4x2T =
    run {
        val a1: Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 2, 6)); Poly16x4T() }
        val b1: Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 5, 3, 7)); Poly16x4T() }
        transmute(Pair(a1, b1))
    }

/**
 * Transpose elements
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtrnqP16)
 */
public fun vtrnqP16(a: Poly16x8T, b: Poly16x8T): Poly16x8x2T =
    run {
        val a1: Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 2, 10, 4, 12, 6, 14)); Poly16x8T() }
        val b1: Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 9, 3, 11, 5, 13, 7, 15)); Poly16x8T() }
        transmute(Pair(a1, b1))
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstS8)
 */
public fun vtstS8(a: Int8x8T, b: Int8x8T): Uint8x8T =
    run {
        val c: Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: I8x8 = i8x8.new(0, 0, 0, 0, 0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqS8)
 */
public fun vtstqS8(a: Int8x16T, b: Int8x16T): Uint8x16T =
    run {
        val c: Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: I8x16 = i8x16.new(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstS16)
 */
public fun vtstS16(a: Int16x4T, b: Int16x4T): Uint16x4T =
    run {
        val c: Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: I16x4 = i16x4.new(0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqS16)
 */
public fun vtstqS16(a: Int16x8T, b: Int16x8T): Uint16x8T =
    run {
        val c: Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: I16x8 = i16x8.new(0, 0, 0, 0, 0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstS32)
 */
public fun vtstS32(a: Int32x2T, b: Int32x2T): Uint32x2T =
    run {
        val c: Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: I32x2 = i32x2.new(0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqS32)
 */
public fun vtstqS32(a: Int32x4T, b: Int32x4T): Uint32x4T =
    run {
        val c: Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: I32x4 = i32x4.new(0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstP8)
 */
public fun vtstP8(a: Poly8x8T, b: Poly8x8T): Uint8x8T =
    run {
        val c: Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: I8x8 = i8x8.new(0, 0, 0, 0, 0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqP8)
 */
public fun vtstqP8(a: Poly8x16T, b: Poly8x16T): Uint8x16T =
    run {
        val c: Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: I8x16 = i8x16.new(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstP16)
 */
public fun vtstP16(a: Poly16x4T, b: Poly16x4T): Uint16x4T =
    run {
        val c: Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: I16x4 = i16x4.new(0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Signed compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqP16)
 */
public fun vtstqP16(a: Poly16x8T, b: Poly16x8T): Uint16x8T =
    run {
        val c: Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: I16x8 = i16x8.new(0, 0, 0, 0, 0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Unsigned compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstU8)
 */
public fun vtstU8(a: Uint8x8T, b: Uint8x8T): Uint8x8T =
    run {
        val c: Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: U8x8 = u8x8.new(0, 0, 0, 0, 0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Unsigned compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqU8)
 */
public fun vtstqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16T =
    run {
        val c: Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: U8x16 = u8x16.new(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Unsigned compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstU16)
 */
public fun vtstU16(a: Uint16x4T, b: Uint16x4T): Uint16x4T =
    run {
        val c: Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: U16x4 = u16x4.new(0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Unsigned compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqU16)
 */
public fun vtstqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8T =
    run {
        val c: Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: U16x8 = u16x8.new(0, 0, 0, 0, 0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Unsigned compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstU32)
 */
public fun vtstU32(a: Uint32x2T, b: Uint32x2T): Uint32x2T =
    run {
        val c: Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: U32x2 = u32x2.new(0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Unsigned compare bitwise Test bits nonzero
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vtstqU32)
 */
public fun vtstqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4T =
    run {
        val c: Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
        val d: U32x4 = u32x4.new(0, 0, 0, 0)
        run { val _simdArg0 = c; val _simdArg1 = transmute(d); _simdArg0 }
    }

/**
 * Dot product index form with unsigned and signed integers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vusdotLaneS32)
 */
public fun vusdotLaneS32(a: Int32x2T, b: Uint8x8T, c: Int8x8T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretS32S8(c)
    val c2 = vdupLaneS32(c1, lane)
    return vusdotS32(a, b, vreinterpretS8S32(c2))
}

/**
 * Dot product index form with unsigned and signed integers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vusdotqLaneS32)
 */
public fun vusdotqLaneS32(a: Int32x4T, b: Uint8x16T, c: Int8x8T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 1)) { "LANE doesn't fit in 1 bits (unsigned)" }
    }
    val c1 = vreinterpretS32S8(c)
    val c2 = vdupqLaneS32(c1, lane)
    return vusdotqS32(a, b, vreinterpretqS8S32(c2))
}

/**
 * Dot product index form with unsigned and signed integers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vusdotLaneqS32)
 */
public fun vusdotLaneqS32(a: Int32x2T, b: Uint8x8T, c: Int8x16T, lane: Int): Int32x2T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqS32S8(c)
    val c2 = vdupLaneqS32(c1, lane)
    return vusdotS32(a, b, vreinterpretS8S32(c2))
}

/**
 * Dot product index form with unsigned and signed integers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vusdotqLaneqS32)
 */
public fun vusdotqLaneqS32(a: Int32x4T, b: Uint8x16T, c: Int8x16T, lane: Int): Int32x4T {
    run {
        check(0 <= lane && lane < (1 shl 2)) { "LANE doesn't fit in 2 bits (unsigned)" }
    }
    val c1 = vreinterpretqS32S8(c)
    val c2 = vdupqLaneqS32(c1, lane)
    return vusdotqS32(a, b, vreinterpretqS8S32(c2))
}

/**
 * Dot product vector form with unsigned and signed integers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vusdotS32)
 */
public fun vusdotS32(a: Int32x2T, b: Uint8x8T, c: Int8x8T): Int32x2T {
    fun vusdotS32(a1: Int32x2T, b1: Uint8x8T, c1: Int8x8T): Int32x2T = Int32x2T()
    return vusdotS32(a, b, c)
}

/**
 * Dot product vector form with unsigned and signed integers
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vusdotqS32)
 */
public fun vusdotqS32(a: Int32x4T, b: Uint8x16T, c: Int8x16T): Int32x4T {
    fun vusdotqS32(a1: Int32x4T, b1: Uint8x16T, c1: Int8x16T): Int32x4T = Int32x4T()
    return vusdotqS32(a, b, c)
}

/**
 * Unsigned and signed 8-bit integer matrix multiply-accumulate
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vusmmlaqS32)
 */
public fun vusmmlaqS32(a: Int32x4T, b: Uint8x16T, c: Int8x16T): Int32x4T {
    fun vusmmlaqS32(a1: Int32x4T, b1: Uint8x16T, c1: Int8x16T): Int32x4T = Int32x4T()
    return vusmmlaqS32(a, b, c)
}

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpF16)
 */
public fun vuzpF16(a: Float16x4T, b: Float16x4T): Float16x4x2T =
    run {
        val a0: Float16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6)); Float16x4T() }
        val b0: Float16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7)); Float16x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpqF16)
 */
public fun vuzpqF16(a: Float16x8T, b: Float16x8T): Float16x8x2T =
    run {
        val a0: Float16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14)); Float16x8T() }
        val b0: Float16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15)); Float16x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpF32)
 */
public fun vuzpF32(a: Float32x2T, b: Float32x2T): Float32x2x2T =
    run {
        val a0: Float32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); Float32x2T() }
        val b0: Float32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); Float32x2T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpS32)
 */
public fun vuzpS32(a: Int32x2T, b: Int32x2T): Int32x2x2T =
    run {
        val a0: Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); Int32x2T() }
        val b0: Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); Int32x2T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpU32)
 */
public fun vuzpU32(a: Uint32x2T, b: Uint32x2T): Uint32x2x2T =
    run {
        val a0: Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); Uint32x2T() }
        val b0: Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); Uint32x2T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpqF32)
 */
public fun vuzpqF32(a: Float32x4T, b: Float32x4T): Float32x4x2T =
    run {
        val a0: Float32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6)); Float32x4T() }
        val b0: Float32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7)); Float32x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpS8)
 */
public fun vuzpS8(a: Int8x8T, b: Int8x8T): Int8x8x2T =
    run {
        val a0: Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14)); Int8x8T() }
        val b0: Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15)); Int8x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpqS8)
 */
public fun vuzpqS8(a: Int8x16T, b: Int8x16T): Int8x16x2T =
    run {
        val a0: Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30)); Int8x16T() }
        val b0: Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)); Int8x16T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpS16)
 */
public fun vuzpS16(a: Int16x4T, b: Int16x4T): Int16x4x2T =
    run {
        val a0: Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6)); Int16x4T() }
        val b0: Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7)); Int16x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpqS16)
 */
public fun vuzpqS16(a: Int16x8T, b: Int16x8T): Int16x8x2T =
    run {
        val a0: Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14)); Int16x8T() }
        val b0: Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15)); Int16x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpqS32)
 */
public fun vuzpqS32(a: Int32x4T, b: Int32x4T): Int32x4x2T =
    run {
        val a0: Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6)); Int32x4T() }
        val b0: Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7)); Int32x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpU8)
 */
public fun vuzpU8(a: Uint8x8T, b: Uint8x8T): Uint8x8x2T =
    run {
        val a0: Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14)); Uint8x8T() }
        val b0: Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15)); Uint8x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpqU8)
 */
public fun vuzpqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16x2T =
    run {
        val a0: Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30)); Uint8x16T() }
        val b0: Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)); Uint8x16T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpU16)
 */
public fun vuzpU16(a: Uint16x4T, b: Uint16x4T): Uint16x4x2T =
    run {
        val a0: Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6)); Uint16x4T() }
        val b0: Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7)); Uint16x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpqU16)
 */
public fun vuzpqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8x2T =
    run {
        val a0: Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14)); Uint16x8T() }
        val b0: Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15)); Uint16x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpqU32)
 */
public fun vuzpqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4x2T =
    run {
        val a0: Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6)); Uint32x4T() }
        val b0: Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7)); Uint32x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpP8)
 */
public fun vuzpP8(a: Poly8x8T, b: Poly8x8T): Poly8x8x2T =
    run {
        val a0: Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14)); Poly8x8T() }
        val b0: Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15)); Poly8x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpqP8)
 */
public fun vuzpqP8(a: Poly8x16T, b: Poly8x16T): Poly8x16x2T =
    run {
        val a0: Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30)); Poly8x16T() }
        val b0: Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31)); Poly8x16T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpP16)
 */
public fun vuzpP16(a: Poly16x4T, b: Poly16x4T): Poly16x4x2T =
    run {
        val a0: Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6)); Poly16x4T() }
        val b0: Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7)); Poly16x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Unzip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vuzpqP16)
 */
public fun vuzpqP16(a: Poly16x8T, b: Poly16x8T): Poly16x8x2T =
    run {
        val a0: Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2, 4, 6, 8, 10, 12, 14)); Poly16x8T() }
        val b0: Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3, 5, 7, 9, 11, 13, 15)); Poly16x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipF16)
 */
public fun vzipF16(a: Float16x4T, b: Float16x4T): Float16x4x2T =
    run {
        val a0: Float16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 1, 5)); Float16x4T() }
        val b0: Float16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(2, 6, 3, 7)); Float16x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipqF16)
 */
public fun vzipqF16(a: Float16x8T, b: Float16x8T): Float16x8x2T =
    run {
        val a0: Float16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 1, 9, 2, 10, 3, 11)); Float16x8T() }
        val b0: Float16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(4, 12, 5, 13, 6, 14, 7, 15)); Float16x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipF32)
 */
public fun vzipF32(a: Float32x2T, b: Float32x2T): Float32x2x2T =
    run {
        val a0: Float32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); Float32x2T() }
        val b0: Float32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); Float32x2T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipS32)
 */
public fun vzipS32(a: Int32x2T, b: Int32x2T): Int32x2x2T =
    run {
        val a0: Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); Int32x2T() }
        val b0: Int32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); Int32x2T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipU32)
 */
public fun vzipU32(a: Uint32x2T, b: Uint32x2T): Uint32x2x2T =
    run {
        val a0: Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 2)); Uint32x2T() }
        val b0: Uint32x2T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(1, 3)); Uint32x2T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipS8)
 */
public fun vzipS8(a: Int8x8T, b: Int8x8T): Int8x8x2T =
    run {
        val a0: Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 1, 9, 2, 10, 3, 11)); Int8x8T() }
        val b0: Int8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(4, 12, 5, 13, 6, 14, 7, 15)); Int8x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipS16)
 */
public fun vzipS16(a: Int16x4T, b: Int16x4T): Int16x4x2T =
    run {
        val a0: Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 1, 5)); Int16x4T() }
        val b0: Int16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(2, 6, 3, 7)); Int16x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipU8)
 */
public fun vzipU8(a: Uint8x8T, b: Uint8x8T): Uint8x8x2T =
    run {
        val a0: Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 1, 9, 2, 10, 3, 11)); Uint8x8T() }
        val b0: Uint8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(4, 12, 5, 13, 6, 14, 7, 15)); Uint8x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipU16)
 */
public fun vzipU16(a: Uint16x4T, b: Uint16x4T): Uint16x4x2T =
    run {
        val a0: Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 1, 5)); Uint16x4T() }
        val b0: Uint16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(2, 6, 3, 7)); Uint16x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipP8)
 */
public fun vzipP8(a: Poly8x8T, b: Poly8x8T): Poly8x8x2T =
    run {
        val a0: Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 1, 9, 2, 10, 3, 11)); Poly8x8T() }
        val b0: Poly8x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(4, 12, 5, 13, 6, 14, 7, 15)); Poly8x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipP16)
 */
public fun vzipP16(a: Poly16x4T, b: Poly16x4T): Poly16x4x2T =
    run {
        val a0: Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 1, 5)); Poly16x4T() }
        val b0: Poly16x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(2, 6, 3, 7)); Poly16x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipqF32)
 */
public fun vzipqF32(a: Float32x4T, b: Float32x4T): Float32x4x2T =
    run {
        val a0: Float32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 1, 5)); Float32x4T() }
        val b0: Float32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(2, 6, 3, 7)); Float32x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipqS8)
 */
public fun vzipqS8(a: Int8x16T, b: Int8x16T): Int8x16x2T =
    run {
        val a0: Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 16, 1, 17, 2, 18, 3, 19, 4, 20, 5, 21, 6, 22, 7, 23)); Int8x16T() }
        val b0: Int8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(8, 24, 9, 25, 10, 26, 11, 27, 12, 28, 13, 29, 14, 30, 15, 31)); Int8x16T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipqS16)
 */
public fun vzipqS16(a: Int16x8T, b: Int16x8T): Int16x8x2T =
    run {
        val a0: Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 1, 9, 2, 10, 3, 11)); Int16x8T() }
        val b0: Int16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(4, 12, 5, 13, 6, 14, 7, 15)); Int16x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipqS32)
 */
public fun vzipqS32(a: Int32x4T, b: Int32x4T): Int32x4x2T =
    run {
        val a0: Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 1, 5)); Int32x4T() }
        val b0: Int32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(2, 6, 3, 7)); Int32x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipqU8)
 */
public fun vzipqU8(a: Uint8x16T, b: Uint8x16T): Uint8x16x2T =
    run {
        val a0: Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 16, 1, 17, 2, 18, 3, 19, 4, 20, 5, 21, 6, 22, 7, 23)); Uint8x16T() }
        val b0: Uint8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(8, 24, 9, 25, 10, 26, 11, 27, 12, 28, 13, 29, 14, 30, 15, 31)); Uint8x16T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipqU16)
 */
public fun vzipqU16(a: Uint16x8T, b: Uint16x8T): Uint16x8x2T =
    run {
        val a0: Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 1, 9, 2, 10, 3, 11)); Uint16x8T() }
        val b0: Uint16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(4, 12, 5, 13, 6, 14, 7, 15)); Uint16x8T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipqU32)
 */
public fun vzipqU32(a: Uint32x4T, b: Uint32x4T): Uint32x4x2T =
    run {
        val a0: Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 4, 1, 5)); Uint32x4T() }
        val b0: Uint32x4T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(2, 6, 3, 7)); Uint32x4T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipqP8)
 */
public fun vzipqP8(a: Poly8x16T, b: Poly8x16T): Poly8x16x2T =
    run {
        val a0: Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 16, 1, 17, 2, 18, 3, 19, 4, 20, 5, 21, 6, 22, 7, 23)); Poly8x16T() }
        val b0: Poly8x16T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(8, 24, 9, 25, 10, 26, 11, 27, 12, 28, 13, 29, 14, 30, 15, 31)); Poly8x16T() }
        transmute(Pair(a0, b0))
    }

/**
 * Zip vectors
 * [Arm's documentation](https://developer.arm.com/architectures/instruction-sets/intrinsics/vzipqP16)
 */
public fun vzipqP16(a: Poly16x8T, b: Poly16x8T): Poly16x8x2T =
    run {
        val a0: Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(0, 8, 1, 9, 2, 10, 3, 11)); Poly16x8T() }
        val b0: Poly16x8T = run { val _simdArg0 = a; val _simdArg1 = b; val _simdArg2 = SimdShuffleIdx(listOf(4, 12, 5, 13, 6, 14, 7, 15)); Poly16x8T() }
        transmute(Pair(a0, b0))
    }
