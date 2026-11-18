// port-lint: source powerpc/vsx.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.powerpc

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.intrinsics.kmAllocZeroed
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.corearch.SimdShuffleIdx
import io.github.kotlinmania.rustlang.corearch.simd.I64x2
import io.github.kotlinmania.rustlang.corearch.simd.M64x2
import io.github.kotlinmania.rustlang.corearch.simd.Simd

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
    public var value: LongArray = LongArray(2),
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
    public open fun asMutArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_signed_long"); Result.Ok<Unit, Error>(Unit) }

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
        public fun splat(value: Long): VectorSignedLong = run { val simdArg0 = value; VectorSignedLong() }

        public fun from(simd: Simd<Long>): VectorSignedLong = transmuteWithLayouts<Simd<Long>, VectorSignedLong>(simd, UnresolvedTypeLayout<Simd<Long>>(), VectorSignedLong.typeLayout())
    }
}

public fun fromSimdLong(simd: VectorSignedLong): Simd<Long> = transmuteWithLayouts<VectorSignedLong, Simd<Long>>(simd, VectorSignedLong.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

/**
 * PowerPC-specific 128-bit wide vector of two packed `ULong`
 */
public open class VectorUnsignedLong(
    public var value: ULongArray = ULongArray(2),
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
    public open fun asMutArray(): ULongArray = (PrimitiveArrayLayouts.uLongArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_unsigned_long"); Result.Ok<Unit, Error>(Unit) }

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
        public fun splat(value: ULong): VectorUnsignedLong = run { val simdArg0 = value; VectorUnsignedLong() }

        public fun from(simd: Simd<ULong>): VectorUnsignedLong = transmuteWithLayouts<Simd<ULong>, VectorUnsignedLong>(simd, UnresolvedTypeLayout<Simd<ULong>>(), VectorUnsignedLong.typeLayout())
    }
}

public fun fromSimdULong(simd: VectorUnsignedLong): Simd<ULong> = transmuteWithLayouts<VectorUnsignedLong, Simd<ULong>>(simd, VectorUnsignedLong.typeLayout(), UnresolvedTypeLayout<Simd<ULong>>())

/**
 * PowerPC-specific 128-bit wide vector mask of two `Long`
 */
public open class VectorBoolLong(
    public var value: LongArray = LongArray(2),
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
    public open fun asMutArray(): LongArray = (PrimitiveArrayLayouts.longArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_bool_long"); Result.Ok<Unit, Error>(Unit) }

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
        public fun splat(value: Long): VectorBoolLong = run { val simdArg0 = value; VectorBoolLong() }

        public fun from(simd: Simd<Long>): VectorBoolLong = transmuteWithLayouts<Simd<Long>, VectorBoolLong>(simd, UnresolvedTypeLayout<Simd<Long>>(), VectorBoolLong.typeLayout())

        public fun from(value: M64x2): VectorBoolLong = transmuteWithLayouts<M64x2, VectorBoolLong>(value, UnresolvedTypeLayout<M64x2>(), VectorBoolLong.typeLayout())
    }
}

public fun fromSimdLong(simd: VectorBoolLong): Simd<Long> = transmuteWithLayouts<VectorBoolLong, Simd<Long>>(simd, VectorBoolLong.typeLayout(), UnresolvedTypeLayout<Simd<Long>>())

/**
 * PowerPC-specific 128-bit wide vector of two packed `Double`
 */
public open class VectorDouble(
    public var value: DoubleArray = DoubleArray(2),
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
    public open fun asMutArray(): DoubleArray = (PrimitiveArrayLayouts.doubleArray((2).toInt())).readNative((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { val simdDbgArr = this.asArray(); (f).append("vector_double"); Result.Ok<Unit, Error>(Unit) }

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
        public fun splat(value: Double): VectorDouble = run { val simdArg0 = value; VectorDouble() }

        public fun from(simd: Simd<Double>): VectorDouble = transmuteWithLayouts<Simd<Double>, VectorDouble>(simd, UnresolvedTypeLayout<Simd<Double>>(), VectorDouble.typeLayout())
    }
}

public fun fromSimdDouble(simd: VectorDouble): Simd<Double> = transmuteWithLayouts<VectorDouble, Simd<Double>>(simd, VectorDouble.typeLayout(), UnresolvedTypeLayout<Simd<Double>>())

public fun fromM64x2(value: VectorBoolLong): M64x2 = transmuteWithLayouts<VectorBoolLong, M64x2>(value, VectorBoolLong.typeLayout(), UnresolvedTypeLayout<M64x2>())

public expect fun vperm(a: VectorSignedInt, b: VectorSignedInt, c: VectorUnsignedChar): VectorSignedInt

public interface VectorPermDI<T> {
    public fun vecXxpermdi(b: T, dm: UByte): T
}

// xxpermdi has an big-endian bias and extended mnemonics
public fun xxpermdi(a: VectorSignedLong, b: VectorSignedLong, dm: UByte): VectorSignedLong {
    val a1: I64x2 = transmuteWithLayouts<VectorSignedLong, I64x2>(a, VectorSignedLong.typeLayout(), UnresolvedTypeLayout<I64x2>())
    val b1: I64x2 = transmuteWithLayouts<VectorSignedLong, I64x2>(b, VectorSignedLong.typeLayout(), UnresolvedTypeLayout<I64x2>())
    val r: I64x2 =
        when (dm and 0x3u.toUByte()) {
            0u.toUByte() -> {
                run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0x0u, 0x2u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }
            }
            1u.toUByte() -> {
                run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0x1u, 0x2u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }
            }
            2u.toUByte() -> {
                run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0x0u, 0x3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }
            }
            else -> {
                run { val simdArg0 = a1; val simdArg1 = b1; val simdArg2 = SimdShuffleIdx(uintArrayOf(0x1u, 0x3u)); run { val ptr = kmAllocZeroed(maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); val res = (UnresolvedTypeLayout<I64x2>()).readNative(ptr); kmDealloc(ptr, maxOf(1L, (UnresolvedTypeLayout<I64x2>()).sizeBytes), maxOf(1L, (UnresolvedTypeLayout<I64x2>()).alignBytes)); res } }
            }
        }
    return transmuteWithLayouts<I64x2, VectorSignedLong>(r, UnresolvedTypeLayout<I64x2>(), VectorSignedLong.typeLayout())
}

public fun VectorUnsignedLong.vecXxpermdi(b: VectorUnsignedLong, dm: UByte): VectorUnsignedLong = transmuteWithLayouts<VectorSignedLong, VectorUnsignedLong>(xxpermdi(transmute(this), transmute(b), dm), VectorSignedLong.typeLayout(), VectorUnsignedLong.typeLayout())

public fun VectorSignedLong.vecXxpermdi(b: VectorSignedLong, dm: UByte): VectorSignedLong = xxpermdi(transmute(this), transmute(b), dm)

public fun VectorBoolLong.vecXxpermdi(b: VectorBoolLong, dm: UByte): VectorBoolLong = transmuteWithLayouts<VectorSignedLong, VectorBoolLong>(xxpermdi(transmute(this), transmute(b), dm), VectorSignedLong.typeLayout(), VectorBoolLong.typeLayout())

public fun VectorDouble.vecXxpermdi(b: VectorDouble, dm: UByte): VectorDouble = transmuteWithLayouts<VectorSignedLong, VectorDouble>(xxpermdi(transmute(this), transmute(b), dm), VectorSignedLong.typeLayout(), VectorDouble.typeLayout())

public interface VectorMergeEo<T> {
    public fun vecMergee(b: T): T

    public fun vecMergeo(b: T): T
}

public fun mergee(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val p = transmuteWithLayouts<Any?, VectorSignedInt>(Simd<UByte>(listOf(0x00u.toUByte(), 0x01u.toUByte(), 0x02u.toUByte(), 0x03u.toUByte(), 0x10u.toUByte(), 0x11u.toUByte(), 0x12u.toUByte(), 0x13u.toUByte(), 0x08u.toUByte(), 0x09u.toUByte(), 0x0Au.toUByte(), 0x0Bu.toUByte(), 0x18u.toUByte(), 0x19u.toUByte(), 0x1Au.toUByte(), 0x1Bu.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
    return vecPerm(a, b, p)
}

public fun mergeo(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt {
    val p = transmuteWithLayouts<Any?, VectorSignedInt>(Simd<UByte>(listOf(0x04u.toUByte(), 0x05u.toUByte(), 0x06u.toUByte(), 0x07u.toUByte(), 0x14u.toUByte(), 0x15u.toUByte(), 0x16u.toUByte(), 0x17u.toUByte(), 0x0Cu.toUByte(), 0x0Du.toUByte(), 0x0Eu.toUByte(), 0x0Fu.toUByte(), 0x1Cu.toUByte(), 0x1Du.toUByte(), 0x1Eu.toUByte(), 0x1Fu.toUByte())), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<VectorSignedInt>())
    return vecPerm(a, b, p)
}

public fun VectorSignedInt.vecMergee(b: VectorSignedInt): VectorSignedInt = mergee(transmute(this), transmute(b))

public fun VectorSignedInt.vecMergeo(b: VectorSignedInt): VectorSignedInt = mergeo(transmute(this), transmute(b))

public fun VectorUnsignedInt.vecMergee(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(mergee(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorUnsignedInt.vecMergeo(b: VectorUnsignedInt): VectorUnsignedInt = transmuteWithLayouts<VectorSignedInt, VectorUnsignedInt>(mergeo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorUnsignedInt>())

public fun VectorBoolInt.vecMergee(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(mergee(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorBoolInt.vecMergeo(b: VectorBoolInt): VectorBoolInt = transmuteWithLayouts<VectorSignedInt, VectorBoolInt>(mergeo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorBoolInt>())

public fun VectorFloat.vecMergee(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedInt, VectorFloat>(mergee(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())

public fun VectorFloat.vecMergeo(b: VectorFloat): VectorFloat = transmuteWithLayouts<VectorSignedInt, VectorFloat>(mergeo(transmute(this), transmute(b)), UnresolvedTypeLayout<VectorSignedInt>(), UnresolvedTypeLayout<VectorFloat>())

// @rustcLegacyConstGenerics(2)

/**
 * Vector permute.
 */
public fun <T> vecXxpermdi(a: T, b: T, dm: Int): T where T : VectorPermDI<T> {
    run {
        check(0 <= dm && dm < (1 shl 2)) { "DM doesn't fit in 2 bits (unsigned)" }
    }
    return a.vecXxpermdi(b, dm.toUByte(), dm)
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
