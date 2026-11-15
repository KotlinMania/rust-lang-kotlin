// port-lint: source powerpc/vsx.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.SimdShuffleIdx
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.powerpc.VectorBoolInt
import io.github.kotlinmania.rustlang.powerpc.VectorFloat
import io.github.kotlinmania.rustlang.powerpc.VectorSignedInt
import io.github.kotlinmania.rustlang.powerpc.VectorUnsignedChar
import io.github.kotlinmania.rustlang.powerpc.VectorUnsignedInt
import io.github.kotlinmania.rustlang.powerpc.vecPerm
import io.github.kotlinmania.rustlang.simd.I64x2
import io.github.kotlinmania.rustlang.simd.M64x2
import io.github.kotlinmania.rustlang.simd.Simd
import io.github.kotlinmania.rustlang.simd.debugSimdFinish

// PowerPC Vector Scalar eXtensions (VSX) intrinsics.
//
// The references are: [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA
// NVlink)] and [POWER ISA v3.0B (for POWER9)].
//
// [POWER ISA v2.07B (for POWER8 & POWER8 with NVIDIA NVlink)]: https://ibm.box.com/s/jd5w15gz301s5b5dt375mshpq9c3lh4u
// [POWER ISA v3.0B (for POWER9)]: https://ibm.box.com/s/1hzcwkwf8rbju5h9iyf44wm94amnlcrv

/**
 * PowerPC-specific 128-bit wide vector of two packed `Long`
 */
public open class VectorSignedLong(
    public var value: LongArray,
) {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorSignedLong = VectorSignedLong(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): LongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.longArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_signed_long", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<VectorSignedLong> =
            object : TypeLayout<VectorSignedLong> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorSignedLong =
                    VectorSignedLong(
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorSignedLong) {
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorSignedLong =
                    VectorSignedLong(
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorSignedLong) {
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Long): VectorSignedLong = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Long>): VectorSignedLong = transmute(simd)
    }
}

public fun fromSimdLong(simd: VectorSignedLong): Simd<Long> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector of two packed `ULong`
 */
public open class VectorUnsignedLong(
    public var value: ULongArray,
) {
    public var field0: ULongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorUnsignedLong = VectorUnsignedLong(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): ULongArray = (PrimitiveArrayLayouts.uLongArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): ULongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.uLongArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_unsigned_long", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<VectorUnsignedLong> =
            object : TypeLayout<VectorUnsignedLong> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorUnsignedLong =
                    VectorUnsignedLong(
                        (PrimitiveArrayLayouts.uLongArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorUnsignedLong) {
                    (PrimitiveArrayLayouts.uLongArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorUnsignedLong =
                    VectorUnsignedLong(
                        (PrimitiveArrayLayouts.uLongArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorUnsignedLong) {
                    (PrimitiveArrayLayouts.uLongArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: ULong): VectorUnsignedLong = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<ULong>): VectorUnsignedLong = transmute(simd)
    }
}

public fun fromSimdULong(simd: VectorUnsignedLong): Simd<ULong> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector mask of two `Long`
 */
public open class VectorBoolLong(
    public var value: LongArray,
) {
    public var field0: LongArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorBoolLong = VectorBoolLong(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): LongArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.longArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_bool_long", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<VectorBoolLong> =
            object : TypeLayout<VectorBoolLong> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorBoolLong =
                    VectorBoolLong(
                        (PrimitiveArrayLayouts.longArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorBoolLong) {
                    (PrimitiveArrayLayouts.longArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorBoolLong =
                    VectorBoolLong(
                        (PrimitiveArrayLayouts.longArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorBoolLong) {
                    (PrimitiveArrayLayouts.longArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Long): VectorBoolLong = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Long>): VectorBoolLong = transmute(simd)

        public fun from(value: M64x2): VectorBoolLong = transmute(value)
    }
}

public fun fromSimdLong(simd: VectorBoolLong): Simd<Long> = transmute(simd)

/**
 * PowerPC-specific 128-bit wide vector of two packed `Double`
 */
public open class VectorDouble(
    public var value: DoubleArray,
) {
    public var field0: DoubleArray
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): VectorDouble = VectorDouble(value)

    /**
     * Returns an array reference containing the entire SIMD vector.
     */
    public open fun asArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((2).toInt())).readNative((((constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    /**
     * Returns a mutable array reference containing the entire SIMD vector.
     */
    public open fun asMutArray(): DoubleArray = RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), PrimitiveArrayLayouts.doubleArray((2).toInt()))

    public open fun fmt(f: Formatter): Result = debugSimdFinish(f, "vector_double", this.asArray())

    public companion object {
        public fun typeLayout(): TypeLayout<VectorDouble> =
            object : TypeLayout<VectorDouble> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): VectorDouble =
                    VectorDouble(
                        (PrimitiveArrayLayouts.doubleArray(2)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: VectorDouble) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): VectorDouble =
                    VectorDouble(
                        (PrimitiveArrayLayouts.doubleArray(2)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: VectorDouble) {
                    (PrimitiveArrayLayouts.doubleArray(2)).writeBytes(buf, offset, value.value)
                }
            }

        /**
         * Put the same value in every lane.
         */
        public fun splat(value: Double): VectorDouble = run { val _simdArg0 = value; _simdArg0 }

        public fun from(simd: Simd<Double>): VectorDouble = transmute(simd)
    }
}

public fun fromSimdDouble(simd: VectorDouble): Simd<Double> = transmute(simd)

public fun fromM64x2(value: VectorBoolLong): M64x2 = transmute(value)

public expect fun vperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt

public interface VectorPermDI<T> {
    public fun vecXxpermdi(b: T, dm: UByte): T
}

// xxpermdi has an big-endian bias and extended mnemonics
public fun xxpermdi(a: VectorSignedLong, b: VectorSignedLong, dm: UByte): VectorSignedLong {
    val a1: I64x2 = transmuteWithLayouts(a, VectorSignedLong.typeLayout(), VectorSignedLong.typeLayout())
    val b1: I64x2 = transmuteWithLayouts(b, VectorSignedLong.typeLayout(), VectorSignedLong.typeLayout())
    val r: I64x2 =
        when (dm and 0x3u.toUByte()) {
            0 -> {
                run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0x0, 0x2)); _simdArg0 }
            }
            1 -> {
                run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0x1, 0x2)); _simdArg0 }
            }
            2 -> {
                run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0x0, 0x3)); _simdArg0 }
            }
            else -> {
                run { val _simdArg0 = a1; val _simdArg1 = b1; val _simdArg2 = SimdShuffleIdx(listOf(0x1, 0x3)); _simdArg0 }
            }
        }
    return transmute(r)
}

public fun VectorUnsignedLong.vecXxpermdi(b: VectorUnsignedLong, dm: UByte): VectorUnsignedLong = transmute(xxpermdi(transmute(this), transmuteWithLayouts(b, VectorUnsignedLong.typeLayout(), VectorUnsignedLong.typeLayout()), dm))

public fun VectorSignedLong.vecXxpermdi(b: VectorSignedLong, dm: UByte): VectorSignedLong = transmute(xxpermdi(transmute(this), transmuteWithLayouts(b, VectorSignedLong.typeLayout(), VectorSignedLong.typeLayout()), dm))

public fun VectorBoolLong.vecXxpermdi(b: VectorBoolLong, dm: UByte): VectorBoolLong = transmute(xxpermdi(transmute(this), transmuteWithLayouts(b, VectorBoolLong.typeLayout(), VectorBoolLong.typeLayout()), dm))

public fun VectorDouble.vecXxpermdi(b: VectorDouble, dm: UByte): VectorDouble = transmute(xxpermdi(transmute(this), transmuteWithLayouts(b, VectorDouble.typeLayout(), VectorDouble.typeLayout()), dm))

public interface VectorMergeEo<T> {
    public fun vecMergee(b: T): T

    public fun vecMergeo(b: T): T
}

public fun mergee(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val p = transmute(u8x16.new(0x00, 0x01, 0x02, 0x03, 0x10, 0x11, 0x12, 0x13, 0x08, 0x09, 0x0A, 0x0B, 0x18, 0x19, 0x1A, 0x1B))
    return vecPerm(a, b, p)
}

public fun mergeo(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val p = transmute(u8x16.new(0x04, 0x05, 0x06, 0x07, 0x14, 0x15, 0x16, 0x17, 0x0C, 0x0D, 0x0E, 0x0F, 0x1C, 0x1D, 0x1E, 0x1F))
    return vecPerm(a, b, p)
}

public fun VectorSignedInt.vecMergee(b: VectorSignedInt): VectorSignedInt = transmute(mergee(transmute(this), transmute(b)))

public fun VectorSignedInt.vecMergeo(b: VectorSignedInt): VectorSignedInt = transmute(mergeo(transmute(this), transmute(b)))

public fun VectorUnsignedInt.vecMergee(b: VectorUnsignedInt): VectorUnsignedInt = transmute(mergee(transmute(this), transmute(b)))

public fun VectorUnsignedInt.vecMergeo(b: VectorUnsignedInt): VectorUnsignedInt = transmute(mergeo(transmute(this), transmute(b)))

public fun VectorBoolInt.vecMergee(b: VectorBoolInt): VectorBoolInt = transmute(mergee(transmute(this), transmute(b)))

public fun VectorBoolInt.vecMergeo(b: VectorBoolInt): VectorBoolInt = transmute(mergeo(transmute(this), transmute(b)))

public fun VectorFloat.vecMergee(b: VectorFloat): VectorFloat = transmute(mergee(transmute(this), transmute(b)))

public fun VectorFloat.vecMergeo(b: VectorFloat): VectorFloat = transmute(mergeo(transmute(this), transmute(b)))

// @rustcLegacyConstGenerics(2)

/**
 * Vector permute.
 */
public fun <T> vecXxpermdi(a: T, b: T, dm: Int): T where T : VectorPermDI<T> {
    run {
        check(0 <= dm && dm < (1 shl 2)) { "DM doesn't fit in 2 bits (unsigned)" }
    }
    return a.vecXxpermdi(b, dm.toUByte())
}

/**
 * Vector Merge Even
 *
 * ## Purpose
 * Merges the even-numbered values from two vectors.
 *
 * ## Result value
 * The even-numbered elements of a are stored into the even-numbered elements of r.
 * The even-numbered elements of b are stored into the odd-numbered elements of r.
 */
public fun <T> vecMergee(a: T, b: T): T where T : VectorMergeEo<T> = a.vecMergee(b)

/**
 * Vector Merge Odd
 *
 * ## Purpose
 * Merges the odd-numbered values from two vectors.
 *
 * ## Result value
 * The odd-numbered elements of a are stored into the even-numbered elements of r.
 * The odd-numbered elements of b are stored into the odd-numbered elements of r.
 */
public fun <T> vecMergeo(a: T, b: T): T where T : VectorMergeEo<T> = a.vecMergeo(b)
