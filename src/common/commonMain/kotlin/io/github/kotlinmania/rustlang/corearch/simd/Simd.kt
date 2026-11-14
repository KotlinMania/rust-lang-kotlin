// port-lint: source simd.rs
package io.github.kotlinmania.rustlang.corearch.simd

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// Internal `@repr(simd)` types

public fun <T> simdImax(a: T, b: T): T {
    val mask: T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
    return run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
}

public fun <T> simdImin(a: T, b: T): T {
    val mask: T = run { val _simdArg0 = a; val _simdArg1 = b; _simdArg0 }
    return run { val _simdArg0 = mask; val _simdArg1 = a; val _simdArg2 = b; _simdArg1 }
}

/**
 * SAFETY: All bits patterns must be valid
 */
public interface SimdElement<T> {
    public val ZERO: T
        get() = null
}

public open class Simd<T : SimdElement<T>>(
    public var value: List<T>,
) {
    public var field0: List<T>
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun <T : SimdElement<T>> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<Simd<T>> =
            object : TypeLayout<Simd<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(((N).toLong() * (typeLayoutT).strideBytes), alignBytes)
                override fun readNative(addr: Long): Simd<T> =
                    Simd(
                        (fixedListTypeLayout(typeLayoutT, (N).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Simd<T>) {
                    (fixedListTypeLayout(typeLayoutT, (N).toInt())).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Simd<T> =
                    Simd(
                        (fixedListTypeLayout(typeLayoutT, (N).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Simd<T>) {
                    (fixedListTypeLayout(typeLayoutT, (N).toInt())).writeBytes(buf, offset, value.value)
                }
            }

        public fun <T : SimdElement<T>> new(x0: T, n: Int): Simd<T> = Simd(listOf(x0))

        public fun <T : SimdElement<T>> new(x0: T, x1: T, n: Int): Simd<T> = Simd(listOf(x0, x1))

        public fun <T : SimdElement<T>> new(x0: T, x1: T, x2: T, x3: T, n: Int): Simd<T> = Simd(listOf(x0, x1, x2, x3))

        public fun <T : SimdElement<T>> new(x0: T, x1: T, x2: T, x3: T, x4: T, x5: T, x6: T, x7: T, n: Int): Simd<T> = Simd(listOf(x0, x1, x2, x3, x4, x5, x6, x7))

        public fun <T : SimdElement<T>> new(x0: T, x1: T, x2: T, x3: T, x4: T, x5: T, x6: T, x7: T, x8: T, x9: T, x10: T, x11: T, x12: T, x13: T, x14: T, x15: T, n: Int): Simd<T> = Simd(listOf(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15))

        public fun <T : SimdElement<T>> new(x0: T, x1: T, x2: T, x3: T, x4: T, x5: T, x6: T, x7: T, x8: T, x9: T, x10: T, x11: T, x12: T, x13: T, x14: T, x15: T, x16: T, x17: T, x18: T, x19: T, x20: T, x21: T, x22: T, x23: T, x24: T, x25: T, x26: T, x27: T, x28: T, x29: T, x30: T, x31: T, n: Int): Simd<T> = Simd(listOf(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19, x20, x21, x22, x23, x24, x25, x26, x27, x28, x29, x30, x31))
    }
}

/**
 * A value of this type where all elements are zeroed out.
 */
public val ZERO: Simd<T, N> = Simd.splat(T.ZERO)

public fun <T : SimdElement<T>> Simd.Companion.fromArray(elements: List<T>, n: Int): Simd<T, N> = Simd(elements)

public fun <T : SimdElement<T>> Simd.Companion.splat(value: T, n: Int): Simd<T, N> = run { val _simdArg0 = value; _simdArg0 }

/**
 * Extract the element at position `index`. Note that `index` is not a constant so this
 * operation is not efficient on most platforms. Use for testing only.
 */
public fun <T : SimdElement<T>> Simd<T, N>.extractDyn(index: Int, n: Int): T {
    check(index < n)
    // SAFETY: self is a vector, T its element type.
    return run { val _simdArg0 = this; val _simdArg1 = index.toUInt(); _simdArg0 }
}

public fun <T : SimdElement<T>> Simd<T, N>.asArray(n: Int): List<T> {
    val simdPtr: ConstPtr = this
    val arrayPtr: ConstPtr = ConstPtr((simdPtr).rawAddress)
    // SAFETY: We can always read the prefix of a simd type as an array.
    // There might be more padding afterwards for some widths, but
    // that's not a problem for reading less than that.
    return (ObjectSlotLayout<List<T>>()).readNative((arrayPtr).rawAddress)
}

// `@derive(Clone)` causes ICE "Projecting into SIMD type coreArch.simd.Simd is banned by MCP#838"
public fun <T : SimdElement<T>> Simd<T, N>.clone(n: Int): Simd<T, N> = Simd(this.value)

public fun <T : SimdElement<T>> Simd<T, N>.eq(other: Simd<T, N>, n: Int): Boolean = this.asArray() == other.asArray()

public fun <T : SimdElement<T>> Simd<T, N>.fmt(f: Formatter, n: Int): Result = debugSimdFinish(f, "Simd", this.asArray())

public fun Simd<F16, N>.toBits(n: Int): Simd<UShort> {
    check(sizeOf<Simd<F16, N>>(typeLayoutT = Simd.typeLayout<F16, N>(typeLayoutT = PrimitiveLayouts.F16_LAYOUT)) == sizeOf<Simd<UShort>>(typeLayoutT = Simd.typeLayout<UShort, N>(typeLayoutT = PrimitiveLayouts.U16)))
    return transmuteCopy(this)
}

public fun Simd.Companion.fromBits(bits: Simd<UShort, N>, n: Int): Simd<F16, N> {
    check(sizeOf<Simd<F16, N>>(typeLayoutT = Simd.typeLayout<F16, N>(typeLayoutT = PrimitiveLayouts.F16_LAYOUT)) == sizeOf<Simd<UShort>>(typeLayoutT = Simd.typeLayout<UShort, N>(typeLayoutT = PrimitiveLayouts.U16)))
    return transmuteCopy(bits)
}

public open class SimdM<T : SimdElement<T>>(
    public var value: List<T>,
) {
    public var field0: List<T>
        get() = value
        set(v) {
            value = v
        }

    public companion object {
        public fun <T : SimdElement<T>> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<SimdM<T>> =
            object : TypeLayout<SimdM<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(((N).toLong() * (typeLayoutT).strideBytes), alignBytes)
                override fun readNative(addr: Long): SimdM<T> =
                    SimdM(
                        (fixedListTypeLayout(typeLayoutT, (N).toInt())).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: SimdM<T>) {
                    (fixedListTypeLayout(typeLayoutT, (N).toInt())).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): SimdM<T> =
                    SimdM(
                        (fixedListTypeLayout(typeLayoutT, (N).toInt())).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: SimdM<T>) {
                    (fixedListTypeLayout(typeLayoutT, (N).toInt())).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public fun <T : SimdElement<T>> SimdM.Companion.boolToInternal(x: Boolean, n: Int, typeLayoutT: TypeLayout<T>): T {
    // SAFETY: `T` implements `SimdElement`, so all bit patterns are valid.
    val ones =
        run {
            // Ideally, this would be `reinterpret([0xFFu8; sizeOf<T>()])`, but
            // `sizeOf<T>()` is not allowed to use a generic parameter there.
            val r = MaybeUninit.uninit(typeLayoutT)
            var i = 0
            while (i < sizeOf<T>(typeLayoutT = typeLayoutT)) {
                r.asBytesMut()[i] = MaybeUninit.new(0xFF, typeLayoutT)
                i += 1
            }
            r.assumeInit()
        }
    return listOf(T.ZERO, ones)[x.toInt()]
}

public fun <T : SimdElement<T>> SimdM.Companion.fromArray(elements: BooleanArray, n: Int): SimdM<T, N> {
    val internal = MutableList(n) { T.ZERO }
    var i = 0
    while (i < n) {
        internal[i] = SimdM.boolToInternal(elements[i])
        i += 1
    }
    return SimdM(internal)
}

public fun <T : SimdElement<T>> SimdM.Companion.splat(value: Boolean, n: Int): SimdM<T, N> = run { val _simdArg0 = SimdM.boolToInternal(value); _simdArg0 }

public fun <T : SimdElement<T>> SimdM<T, N>.asArray(n: Int): List<T> {
    val simdPtr: ConstPtr = this
    val arrayPtr: ConstPtr = ConstPtr((simdPtr).rawAddress)
    // SAFETY: We can always read the prefix of a simd type as an array.
    // There might be more padding afterwards for some widths, but
    // that's not a problem for reading less than that.
    return (ObjectSlotLayout<List<T>>()).readNative((arrayPtr).rawAddress)
}

// `@derive(Clone)` causes ICE "Projecting into SIMD type coreArch.simd.SimdM is banned by MCP#838"
public fun <T : SimdElement<T>> SimdM<T, N>.clone(n: Int): SimdM<T, N> = SimdM(this.value)

public fun <T : SimdElement<T>> SimdM<T, N>.eq(other: SimdM<T, N>, n: Int): Boolean = this.asArray() == other.asArray()

public fun <T : SimdElement<T>> SimdM<T, N>.fmt(f: Formatter, n: Int): Result = debugSimdFinish(f, "SimdM", this.asArray())

// 16-bit wide types:
public typealias U8x2 = Simd<UByte>

public typealias I8x2 = Simd<Byte>

// 32-bit wide types:
public typealias U8x4 = Simd<UByte>

public typealias U16x2 = Simd<UShort>

public typealias I8x4 = Simd<Byte>

public typealias I16x2 = Simd<Short>

// 64-bit wide types:
public typealias U8x8 = Simd<UByte>

public typealias U16x4 = Simd<UShort>

public typealias U32x2 = Simd<UInt>

public typealias U64x1 = Simd<ULong>

public typealias I8x8 = Simd<Byte>

public typealias I16x4 = Simd<Short>

public typealias I32x2 = Simd<Int>

public typealias I64x1 = Simd<Long>

public typealias F16x4 = Simd<F16>

public typealias F32x2 = Simd<Float>

public typealias F64x1 = Simd<Double>

// 128-bit wide types:
public typealias U8x16 = Simd<UByte>

public typealias U16x8 = Simd<UShort>

public typealias U32x4 = Simd<UInt>

public typealias U64x2 = Simd<ULong>

public typealias I8x16 = Simd<Byte>

public typealias I16x8 = Simd<Short>

public typealias I32x4 = Simd<Int>

public typealias I64x2 = Simd<Long>

public typealias F16x8 = Simd<F16>

public typealias F32x4 = Simd<Float>

public typealias F64x2 = Simd<Double>

public typealias M8x16 = SimdM<Byte>

public typealias M16x8 = SimdM<Short>

public typealias M32x4 = SimdM<Int>

public typealias M64x2 = SimdM<Long>

// 256-bit wide types:
public typealias U8x32 = Simd<UByte>

public typealias U16x16 = Simd<UShort>

public typealias U32x8 = Simd<UInt>

public typealias U64x4 = Simd<ULong>

public typealias U128x2 = Simd<U128>

public typealias I8x32 = Simd<Byte>

public typealias I16x16 = Simd<Short>

public typealias I32x8 = Simd<Int>

public typealias I64x4 = Simd<Long>

public typealias I128x2 = Simd<I128>

public typealias F16x16 = Simd<F16>

public typealias F32x8 = Simd<Float>

public typealias F64x4 = Simd<Double>

public typealias M8x32 = SimdM<Byte>

public typealias M16x16 = SimdM<Short>

public typealias M32x8 = SimdM<Int>

// 512-bit wide types:
public typealias U8x64 = Simd<UByte>

public typealias U16x32 = Simd<UShort>

public typealias U32x16 = Simd<UInt>

public typealias U64x8 = Simd<ULong>

public typealias U128x4 = Simd<U128>

public typealias I8x64 = Simd<Byte>

public typealias I16x32 = Simd<Short>

public typealias I32x16 = Simd<Int>

public typealias I64x8 = Simd<Long>

public typealias I128x4 = Simd<I128>

public typealias F16x32 = Simd<F16>

public typealias F32x16 = Simd<Float>

public typealias F64x8 = Simd<Double>

// 1024-bit wide types:
public typealias U16x64 = Simd<UShort>

public typealias U32x32 = Simd<UInt>

public typealias I32x32 = Simd<Int>

/**
 * Used to continue `Debug`ging SIMD types as `MySimd(1, 2, 3, 4)`, as they
 * were before moving to array-based simd.
 */
public fun <T> debugSimdFinish(formatter: Formatter, typeName: String, array: List<T>, n: Int): Result = Formatter.debugStructFieldsFinish(formatter, typeName, fromFn<Any>({ i -> array[i] }, n))
