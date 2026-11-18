// port-lint: tests wasm32/simd128.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.convert.num.from
import io.github.kotlinmania.rustlang.core.intrinsics.kmCeilf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmCeilf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloorf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFloorf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmMaxnumf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmMaxnumf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmMinnumf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmMinnumf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmRoundf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmRoundf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmSqrtf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmSqrtf64
import io.github.kotlinmania.rustlang.core.intrinsics.kmTruncf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmTruncf64
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.num.saturatingSub
import io.github.kotlinmania.rustlang.core.num.wrappingAbs
import io.github.kotlinmania.rustlang.core.num.wrappingNeg
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.sub
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.sub
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import kotlin.experimental.inv

// This module implements the [WebAssembly `SIMD128` ISA].
//
// [WebAssembly `SIMD128` ISA]:
// https://github.com/WebAssembly/simd/blob/master/proposals/simd/SIMD.md

public val C1: V128 = i8x16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)

public val C2: V128 = u8x16(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u)

public val C3: V128 = i16x8(0, 1, 2, 3, 4, 5, 6, 7)

public val C4: V128 = u16x8(0u, 1u, 2u, 3u, 4u, 5u, 6u, 7u)

public val C5: V128 = i32x4(0, 1, 2, 3)

public val C6: V128 = u32x4(0u, 1u, 2u, 3u)

public val C7: V128 = i64x2(0L, 1L)

public val C8: V128 = u64x2(0uL, 1uL)

public val C9: V128 = f32x4(0.0f, 1.0f, 2.0f, 3.0f)

public val C10: V128 = f64x2(0.0, 1.0)

public fun compareBytes(a: V128, b: V128) {
    val a1: UByteArray = transmuteWithLayouts<V128, UByteArray>(a, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
    val b1: UByteArray = transmuteWithLayouts<V128, UByteArray>(b, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
    check((a1).contentEquals(b1))
}

public interface Avgr<T> {
    public fun avgr(other: T): T
}

public fun UByte.avgr(other: UByte): UByte = ((this.toULong() + other.toULong() + 1uL) / 2uL).toUByte()

public fun UShort.avgr(other: UShort): UShort = ((this.toULong() + other.toULong() + 1uL) / 2uL).toUShort()

public interface IsNan {
    public fun isNan(): Boolean = false
}

public class Simd128InlineTests {
    @kotlin.test.Test
    public fun testLoad() {
        run {
            val localStorageL4301C12 = AddressableArrayLocal.fromIntArray(intArrayOf(0, 1, 2, 3))
            val vec = v128Load((localStorageL4301C12.elementConstPtr(0)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
            compareBytes(vec, i32x4(0, 1, 2, 3))
        }
    }

    @kotlin.test.Test
    public fun testLoadExtend() {
        run {
            val localStorageL4310C12 = AddressableArrayLocal.fromByteArray(byteArrayOf((-3).toByte(), (-2).toByte(), (-1).toByte(), 0, 1, 2, 3, 4))
            val vec = i16x8LoadExtendI8x8(localStorageL4310C12.elementConstPtr(0))
            compareBytes(vec, i16x8((-3).toShort(), (-2).toShort(), (-1).toShort(), 0, 1, 2, 3, 4))
            val vec1 = i16x8LoadExtendU8x8((localStorageL4310C12.elementConstPtr(0)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
            compareBytes(vec1, i16x8(253, 254, 255, 0, 1, 2, 3, 4))
            val localStorageL4316C12 = AddressableArrayLocal.fromShortArray(shortArrayOf((-1).toShort(), 0, 1, 2))
            val vec2 = i32x4LoadExtendI16x4(localStorageL4316C12.elementConstPtr(0))
            compareBytes(vec2, i32x4(-1, 0, 1, 2))
            val vec3 = i32x4LoadExtendU16x4((localStorageL4316C12.elementConstPtr(0)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
            compareBytes(vec3, i32x4(65535, 0, 1, 2))
            val localStorageL4322C12 = AddressableArrayLocal.fromIntArray(intArrayOf(-1, 1))
            val vec4 = i64x2LoadExtendI32x2(localStorageL4322C12.elementConstPtr(0))
            compareBytes(vec4, i64x2(-1L, 1L))
            val vec5 = i64x2LoadExtendU32x2((localStorageL4322C12.elementConstPtr(0)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })
            compareBytes(vec5, i64x2(UInt.MAX_VALUE.into(), 1L))
        }
    }

    @kotlin.test.Test
    public fun testLoadSplat() {
        run {
            compareBytes(v128Load8Splat(8), i8x16Splat(8))
            compareBytes(v128Load16Splat(9), i16x8Splat(9))
            compareBytes(v128Load32Splat(10), i32x4Splat(10))
            compareBytes(v128Load64Splat(11), i64x2Splat(11L))
        }
    }

    @kotlin.test.Test
    public fun testLoadZero() {
        run {
            compareBytes(v128Load32Zero(10), i32x4(10, 0, 0, 0))
            compareBytes(v128Load64Zero(11), i64x2(11L, 0L))
        }
    }

    @kotlin.test.Test
    public fun testStore() {
        run {
            val localStorageL4351C12 = AddressableLocal(i8x16Splat(0), UnresolvedTypeLayout<V128>())
            val spot by localStorageL4351C12
            v128Store(RustMutableReference(localStorageL4351C12.asMutPtr(), localStorageL4351C12.layout), i8x16Splat(1))
            compareBytes(spot, i8x16Splat(1))
        }
    }

    @kotlin.test.Test
    public fun testLoadLane() {
        run {
            val zero = i8x16Splat(0)
            compareBytes(v128Load8Lane(zero, 1, 2), i8x16ReplaceLane(zero, 1, 2))
            compareBytes(v128Load16Lane(zero, 1, 2), i16x8ReplaceLane(zero, 1, 2))
            compareBytes(v128Load32Lane(zero, 1, 2), i32x4ReplaceLane(zero, 1, 2))
            compareBytes(v128Load64Lane(zero, 1, 1), i64x2ReplaceLane(zero, 1L, 1))
        }
    }

    @kotlin.test.Test
    public fun testStoreLane() {
        run {
            val localStorageL4386C12 = AddressableLocal(0, PrimitiveLayouts.I32)
            var spot by localStorageL4386C12
            val zero = i8x16Splat(0)
            v128Store8Lane(i8x16ReplaceLane(zero, 7, 5), RustMutableReference(localStorageL4386C12.asMutPtr(), localStorageL4386C12.layout), 5)
            check(spot == 7)
            val localStorageL4391C12 = AddressableLocal(0, PrimitiveLayouts.I32)
            var spot1 by localStorageL4391C12
            v128Store16Lane(i16x8ReplaceLane(zero, 7, 5), RustMutableReference(localStorageL4391C12.asMutPtr(), localStorageL4391C12.layout), 5)
            check(spot1 == 7)
            val localStorageL4395C12 = AddressableLocal(0, PrimitiveLayouts.I32)
            var spot2 by localStorageL4395C12
            v128Store32Lane(i32x4ReplaceLane(zero, 7, 3), RustMutableReference(localStorageL4395C12.asMutPtr(), localStorageL4395C12.layout), 3)
            check(spot2 == 7)
            val localStorageL4399C12 = AddressableLocal(0, PrimitiveLayouts.I32)
            var spot3 by localStorageL4399C12
            v128Store64Lane(i64x2ReplaceLane(zero, 7L, 0), RustMutableReference(localStorageL4399C12.asMutPtr(), localStorageL4399C12.layout), 0)
            check(spot3 == 7)
        }
    }

    @kotlin.test.Test
    public fun testI8x16() {
        val A: V128 = I8x16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
        compareBytes(A, A)
        val bytes: ShortArray = transmuteWithLayouts<V128, ShortArray>(i16x8((-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-5).toShort(), (-6).toShort(), (-7).toShort(), (-8).toShort()), UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
        check(bytes == shortArrayOf((-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-5).toShort(), (-6).toShort(), (-7).toShort(), (-8).toShort()))
        val bytes1: ByteArray = transmuteWithLayouts<V128, ByteArray>(i8x16((-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte(), (-16).toByte()), UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
        check(bytes1 == byteArrayOf((-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte(), (-16).toByte()))
    }

    @kotlin.test.Test
    public fun testShuffle() {
        val vecA = i8x16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
        val vecB = i8x16(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31)
        val vecR = i8x16Shuffle(vecA, vecB, 0, 16, 2, 18, 4, 20, 6, 22, 8, 24, 10, 26, 12, 28, 14, 30)
        val vecE = i8x16(0, 16, 2, 18, 4, 20, 6, 22, 8, 24, 10, 26, 12, 28, 14, 30)
        compareBytes(vecR, vecE)
        val vecA1 = i16x8(0, 1, 2, 3, 4, 5, 6, 7)
        val vecB1 = i16x8(8, 9, 10, 11, 12, 13, 14, 15)
        val vecR1 = i16x8Shuffle(vecA1, vecB1, 0, 8, 2, 10, 4, 12, 6, 14)
        val vecE1 = i16x8(0, 8, 2, 10, 4, 12, 6, 14)
        compareBytes(vecR1, vecE1)
        val vecA2 = i32x4(0, 1, 2, 3)
        val vecB2 = i32x4(4, 5, 6, 7)
        val vecR2 = i32x4Shuffle(vecA2, vecB2, 0, 4, 2, 6)
        val vecE2 = i32x4(0, 4, 2, 6)
        compareBytes(vecR2, vecE2)
        val vecA3 = i64x2(0L, 1L)
        val vecB3 = i64x2(2L, 3L)
        val vecR3 = i64x2Shuffle(vecA3, vecB3, 0, 2)
        val vecE3 = i64x2(0L, 2L)
        compareBytes(vecR3, vecE3)
    }

    @kotlin.test.Test
    public fun testI8x16ExtractReplace() {
        run {
            val arr: ByteArray = ByteArray(16) { 123.toByte() }
            val vec: V128 = transmuteWithLayouts<ByteArray, V128>(arr, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            check(i8x16ExtractLane(vec, 0) == 123.toByte())
            check(i8x16ExtractLane(vec, 1) == 123.toByte())
            check(i8x16ExtractLane(vec, 2) == 123.toByte())
            check(i8x16ExtractLane(vec, 3) == 123.toByte())
            check(i8x16ExtractLane(vec, 4) == 123.toByte())
            check(i8x16ExtractLane(vec, 5) == 123.toByte())
            check(i8x16ExtractLane(vec, 6) == 123.toByte())
            check(i8x16ExtractLane(vec, 7) == 123.toByte())
            check(i8x16ExtractLane(vec, 8) == 123.toByte())
            check(i8x16ExtractLane(vec, 9) == 123.toByte())
            check(i8x16ExtractLane(vec, 10) == 123.toByte())
            check(i8x16ExtractLane(vec, 11) == 123.toByte())
            check(i8x16ExtractLane(vec, 12) == 123.toByte())
            check(i8x16ExtractLane(vec, 13) == 123.toByte())
            check(i8x16ExtractLane(vec, 14) == 123.toByte())
            check(i8x16ExtractLane(vec, 15) == 123.toByte())
            val arr1: ByteArray = byteArrayOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte())
            val vec1: V128 = transmuteWithLayouts<ByteArray, V128>(arr1, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            check(i8x16ExtractLane(vec1, 0) == 0.toByte())
            val tmp = i8x16ReplaceLane(vec1, 124.toByte(), 0)
            check(i8x16ExtractLane(tmp, 0) == 124.toByte())
            check(i8x16ExtractLane(vec1, 1) == 1.toByte())
            val tmp1 = i8x16ReplaceLane(vec1, 124.toByte(), 1)
            check(i8x16ExtractLane(tmp1, 1) == 124.toByte())
            check(i8x16ExtractLane(vec1, 2) == 2.toByte())
            val tmp2 = i8x16ReplaceLane(vec1, 124.toByte(), 2)
            check(i8x16ExtractLane(tmp2, 2) == 124.toByte())
            check(i8x16ExtractLane(vec1, 3) == 3.toByte())
            val tmp3 = i8x16ReplaceLane(vec1, 124.toByte(), 3)
            check(i8x16ExtractLane(tmp3, 3) == 124.toByte())
            check(i8x16ExtractLane(vec1, 4) == 4.toByte())
            val tmp4 = i8x16ReplaceLane(vec1, 124.toByte(), 4)
            check(i8x16ExtractLane(tmp4, 4) == 124.toByte())
            check(i8x16ExtractLane(vec1, 5) == 5.toByte())
            val tmp5 = i8x16ReplaceLane(vec1, 124.toByte(), 5)
            check(i8x16ExtractLane(tmp5, 5) == 124.toByte())
            check(i8x16ExtractLane(vec1, 6) == 6.toByte())
            val tmp6 = i8x16ReplaceLane(vec1, 124.toByte(), 6)
            check(i8x16ExtractLane(tmp6, 6) == 124.toByte())
            check(i8x16ExtractLane(vec1, 7) == 7.toByte())
            val tmp7 = i8x16ReplaceLane(vec1, 124.toByte(), 7)
            check(i8x16ExtractLane(tmp7, 7) == 124.toByte())
            check(i8x16ExtractLane(vec1, 8) == 8.toByte())
            val tmp8 = i8x16ReplaceLane(vec1, 124.toByte(), 8)
            check(i8x16ExtractLane(tmp8, 8) == 124.toByte())
            check(i8x16ExtractLane(vec1, 9) == 9.toByte())
            val tmp9 = i8x16ReplaceLane(vec1, 124.toByte(), 9)
            check(i8x16ExtractLane(tmp9, 9) == 124.toByte())
            check(i8x16ExtractLane(vec1, 10) == 10.toByte())
            val tmp10 = i8x16ReplaceLane(vec1, 124.toByte(), 10)
            check(i8x16ExtractLane(tmp10, 10) == 124.toByte())
            check(i8x16ExtractLane(vec1, 11) == 11.toByte())
            val tmp11 = i8x16ReplaceLane(vec1, 124.toByte(), 11)
            check(i8x16ExtractLane(tmp11, 11) == 124.toByte())
            check(i8x16ExtractLane(vec1, 12) == 12.toByte())
            val tmp12 = i8x16ReplaceLane(vec1, 124.toByte(), 12)
            check(i8x16ExtractLane(tmp12, 12) == 124.toByte())
            check(i8x16ExtractLane(vec1, 13) == 13.toByte())
            val tmp13 = i8x16ReplaceLane(vec1, 124.toByte(), 13)
            check(i8x16ExtractLane(tmp13, 13) == 124.toByte())
            check(i8x16ExtractLane(vec1, 14) == 14.toByte())
            val tmp14 = i8x16ReplaceLane(vec1, 124.toByte(), 14)
            check(i8x16ExtractLane(tmp14, 14) == 124.toByte())
            check(i8x16ExtractLane(vec1, 15) == 15.toByte())
            val tmp15 = i8x16ReplaceLane(vec1, 124.toByte(), 15)
            check(i8x16ExtractLane(tmp15, 15) == 124.toByte())
        }
    }

    @kotlin.test.Test
    public fun testI16x8ExtractReplace() {
        run {
            val arr: ShortArray = ShortArray(8) { 123.toShort() }
            val vec: V128 = transmuteWithLayouts<ShortArray, V128>(arr, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            check(i16x8ExtractLane(vec, 0) == 123.toShort())
            check(i16x8ExtractLane(vec, 1) == 123.toShort())
            check(i16x8ExtractLane(vec, 2) == 123.toShort())
            check(i16x8ExtractLane(vec, 3) == 123.toShort())
            check(i16x8ExtractLane(vec, 4) == 123.toShort())
            check(i16x8ExtractLane(vec, 5) == 123.toShort())
            check(i16x8ExtractLane(vec, 6) == 123.toShort())
            check(i16x8ExtractLane(vec, 7) == 123.toShort())
            val arr1: ShortArray = shortArrayOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort())
            val vec1: V128 = transmuteWithLayouts<ShortArray, V128>(arr1, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            check(i16x8ExtractLane(vec1, 0) == 0.toShort())
            val tmp = i16x8ReplaceLane(vec1, 124.toShort(), 0)
            check(i16x8ExtractLane(tmp, 0) == 124.toShort())
            check(i16x8ExtractLane(vec1, 1) == 1.toShort())
            val tmp1 = i16x8ReplaceLane(vec1, 124.toShort(), 1)
            check(i16x8ExtractLane(tmp1, 1) == 124.toShort())
            check(i16x8ExtractLane(vec1, 2) == 2.toShort())
            val tmp2 = i16x8ReplaceLane(vec1, 124.toShort(), 2)
            check(i16x8ExtractLane(tmp2, 2) == 124.toShort())
            check(i16x8ExtractLane(vec1, 3) == 3.toShort())
            val tmp3 = i16x8ReplaceLane(vec1, 124.toShort(), 3)
            check(i16x8ExtractLane(tmp3, 3) == 124.toShort())
            check(i16x8ExtractLane(vec1, 4) == 4.toShort())
            val tmp4 = i16x8ReplaceLane(vec1, 124.toShort(), 4)
            check(i16x8ExtractLane(tmp4, 4) == 124.toShort())
            check(i16x8ExtractLane(vec1, 5) == 5.toShort())
            val tmp5 = i16x8ReplaceLane(vec1, 124.toShort(), 5)
            check(i16x8ExtractLane(tmp5, 5) == 124.toShort())
            check(i16x8ExtractLane(vec1, 6) == 6.toShort())
            val tmp6 = i16x8ReplaceLane(vec1, 124.toShort(), 6)
            check(i16x8ExtractLane(tmp6, 6) == 124.toShort())
            check(i16x8ExtractLane(vec1, 7) == 7.toShort())
            val tmp7 = i16x8ReplaceLane(vec1, 124.toShort(), 7)
            check(i16x8ExtractLane(tmp7, 7) == 124.toShort())
        }
    }

    @kotlin.test.Test
    public fun testI32x4ExtractReplace() {
        run {
            val arr: IntArray = IntArray(4) { 123 }
            val vec: V128 = transmuteWithLayouts<IntArray, V128>(arr, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            check(i32x4ExtractLane(vec, 0) == 123)
            check(i32x4ExtractLane(vec, 1) == 123)
            check(i32x4ExtractLane(vec, 2) == 123)
            check(i32x4ExtractLane(vec, 3) == 123)
            val arr1: IntArray = intArrayOf(0, 1, 2, 3)
            val vec1: V128 = transmuteWithLayouts<IntArray, V128>(arr1, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            check(i32x4ExtractLane(vec1, 0) == 0)
            val tmp = i32x4ReplaceLane(vec1, 124, 0)
            check(i32x4ExtractLane(tmp, 0) == 124)
            check(i32x4ExtractLane(vec1, 1) == 1)
            val tmp1 = i32x4ReplaceLane(vec1, 124, 1)
            check(i32x4ExtractLane(tmp1, 1) == 124)
            check(i32x4ExtractLane(vec1, 2) == 2)
            val tmp2 = i32x4ReplaceLane(vec1, 124, 2)
            check(i32x4ExtractLane(tmp2, 2) == 124)
            check(i32x4ExtractLane(vec1, 3) == 3)
            val tmp3 = i32x4ReplaceLane(vec1, 124, 3)
            check(i32x4ExtractLane(tmp3, 3) == 124)
        }
    }

    @kotlin.test.Test
    public fun testI64x2ExtractReplace() {
        run {
            val arr: LongArray = LongArray(2) { 123.toLong() }
            val vec: V128 = transmuteWithLayouts<LongArray, V128>(arr, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            check(i64x2ExtractLane(vec, 0) == 123.toLong())
            check(i64x2ExtractLane(vec, 1) == 123.toLong())
            val arr1: LongArray = longArrayOf(0.toLong(), 1.toLong())
            val vec1: V128 = transmuteWithLayouts<LongArray, V128>(arr1, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            check(i64x2ExtractLane(vec1, 0) == 0.toLong())
            val tmp = i64x2ReplaceLane(vec1, 124.toLong(), 0)
            check(i64x2ExtractLane(tmp, 0) == 124.toLong())
            check(i64x2ExtractLane(vec1, 1) == 1.toLong())
            val tmp1 = i64x2ReplaceLane(vec1, 124.toLong(), 1)
            check(i64x2ExtractLane(tmp1, 1) == 124.toLong())
        }
    }

    @kotlin.test.Test
    public fun testF32x4ExtractReplace() {
        run {
            val arr: FloatArray = FloatArray(4) { 123.toFloat() }
            val vec: V128 = transmuteWithLayouts<FloatArray, V128>(arr, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            check(f32x4ExtractLane(vec, 0) == 123.toFloat())
            check(f32x4ExtractLane(vec, 1) == 123.toFloat())
            check(f32x4ExtractLane(vec, 2) == 123.toFloat())
            check(f32x4ExtractLane(vec, 3) == 123.toFloat())
            val arr1: FloatArray = floatArrayOf(0.toFloat(), 1.toFloat(), 2.toFloat(), 3.toFloat())
            val vec1: V128 = transmuteWithLayouts<FloatArray, V128>(arr1, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            check(f32x4ExtractLane(vec1, 0) == 0.toFloat())
            val tmp = f32x4ReplaceLane(vec1, 124.toFloat(), 0)
            check(f32x4ExtractLane(tmp, 0) == 124.toFloat())
            check(f32x4ExtractLane(vec1, 1) == 1.toFloat())
            val tmp1 = f32x4ReplaceLane(vec1, 124.toFloat(), 1)
            check(f32x4ExtractLane(tmp1, 1) == 124.toFloat())
            check(f32x4ExtractLane(vec1, 2) == 2.toFloat())
            val tmp2 = f32x4ReplaceLane(vec1, 124.toFloat(), 2)
            check(f32x4ExtractLane(tmp2, 2) == 124.toFloat())
            check(f32x4ExtractLane(vec1, 3) == 3.toFloat())
            val tmp3 = f32x4ReplaceLane(vec1, 124.toFloat(), 3)
            check(f32x4ExtractLane(tmp3, 3) == 124.toFloat())
        }
    }

    @kotlin.test.Test
    public fun testF64x2ExtractReplace() {
        run {
            val arr: DoubleArray = DoubleArray(2) { 123.toDouble() }
            val vec: V128 = transmuteWithLayouts<DoubleArray, V128>(arr, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            check(f64x2ExtractLane(vec, 0) == 123.toDouble())
            check(f64x2ExtractLane(vec, 1) == 123.toDouble())
            val arr1: DoubleArray = doubleArrayOf(0.toDouble(), 1.toDouble())
            val vec1: V128 = transmuteWithLayouts<DoubleArray, V128>(arr1, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            check(f64x2ExtractLane(vec1, 0) == 0.toDouble())
            val tmp = f64x2ReplaceLane(vec1, 124.toDouble(), 0)
            check(f64x2ExtractLane(tmp, 0) == 124.toDouble())
            check(f64x2ExtractLane(vec1, 1) == 1.toDouble())
            val tmp1 = f64x2ReplaceLane(vec1, 124.toDouble(), 1)
            check(f64x2ExtractLane(tmp1, 1) == 124.toDouble())
        }
    }

    @kotlin.test.Test
    public fun testSwizzle() {
        compareBytes(i8x16Swizzle(i32x4(1, 2, 3, 4), i8x16(32, 31, 30, 29, 0, 1, 2, 3, 12, 13, 14, 15, 0, 4, 8, 12)), i32x4(0, 1, 4, 0x04030201))
    }

    @kotlin.test.Test
    public fun testBitmasks() {
        val zero = i8x16Splat(0)
        val ones = i8x16Splat((0.inv()).toByte())
        check(i8x16Bitmask(zero) == 0u.toUShort())
        check(i8x16Bitmask(ones) == 0xffffu.toUShort())
        check(i8x16Bitmask(i8x16Splat(Byte.MAX_VALUE)) == 0u.toUShort())
        check(i8x16Bitmask(i8x16Splat(Byte.MIN_VALUE)) == 0xffffu.toUShort())
        check(i8x16Bitmask(i8x16ReplaceLane(zero, (-1).toByte(), 1)) == 0x2u.toUShort())
        check(i16x8Bitmask(zero) == 0u.toUByte())
        check(i16x8Bitmask(ones) == 0xffu.toUByte())
        check(i16x8Bitmask(i16x8Splat(Short.MAX_VALUE)) == 0u.toUByte())
        check(i16x8Bitmask(i16x8Splat(Short.MIN_VALUE)) == 0xffu.toUByte())
        check(i16x8Bitmask(i16x8ReplaceLane(zero, (-1).toShort(), 1)) == 0x2u.toUByte())
        check(i32x4Bitmask(zero) == 0u.toUByte())
        check(i32x4Bitmask(ones) == 0xFu.toUByte())
        check(i32x4Bitmask(i32x4Splat(Int.MAX_VALUE)) == 0u.toUByte())
        check(i32x4Bitmask(i32x4Splat(Int.MIN_VALUE)) == 0xFu.toUByte())
        check(i32x4Bitmask(i32x4ReplaceLane(zero, -1, 1)) == 0x2u.toUByte())
        check(i64x2Bitmask(zero) == 0u.toUByte())
        check(i64x2Bitmask(ones) == 0x3u.toUByte())
        check(i64x2Bitmask(i64x2Splat(Long.MAX_VALUE)) == 0u.toUByte())
        check(i64x2Bitmask(i64x2Splat(Long.MIN_VALUE)) == 0x3u.toUByte())
        check(i64x2Bitmask(i64x2ReplaceLane(zero, -1L, 1)) == 0x2u.toUByte())
    }

    @kotlin.test.Test
    public fun testNarrow() {
        val zero = i8x16Splat(0)
        val ones = i8x16Splat((0.inv()).toByte())
        compareBytes(i8x16NarrowI16x8(zero, zero), zero)
        compareBytes(u8x16NarrowI16x8(zero, zero), zero)
        compareBytes(i8x16NarrowI16x8(ones, ones), ones)
        compareBytes(u8x16NarrowI16x8(ones, ones), zero)
        compareBytes(i8x16NarrowI16x8(i16x8(0, 1, 2, (-1).toShort(), Byte.MIN_VALUE.into(), Byte.MAX_VALUE.into(), UByte.MIN_VALUE.into(), UByte.MAX_VALUE.into()), i16x8(Short.MIN_VALUE, Short.MAX_VALUE, UShort.MIN_VALUE.toShort(), UShort.MAX_VALUE.toShort(), 0, 0, 0, 0)), i8x16(0, 1, 2, (-1).toByte(), (-128).toByte(), 127, 0, 127, (-128).toByte(), 127, 0, (-1).toByte(), 0, 0, 0, 0))
        compareBytes(u8x16NarrowI16x8(i16x8(0, 1, 2, (-1).toShort(), Byte.MIN_VALUE.into(), Byte.MAX_VALUE.into(), UByte.MIN_VALUE.into(), UByte.MAX_VALUE.into()), i16x8(Short.MIN_VALUE, Short.MAX_VALUE, UShort.MIN_VALUE.toShort(), UShort.MAX_VALUE.toShort(), 0, 0, 0, 0)), i8x16(0, 1, 2, 0, 0, 127, 0, (-1).toByte(), 0, (-1).toByte(), 0, 0, 0, 0, 0, 0))
        compareBytes(i16x8NarrowI32x4(zero, zero), zero)
        compareBytes(u16x8NarrowI32x4(zero, zero), zero)
        compareBytes(i16x8NarrowI32x4(ones, ones), ones)
        compareBytes(u16x8NarrowI32x4(ones, ones), zero)
        compareBytes(i16x8NarrowI32x4(i32x4(0, -1, Short.MIN_VALUE.into(), Short.MAX_VALUE.into()), i32x4(Int.MIN_VALUE, Int.MAX_VALUE, UInt.MIN_VALUE.toInt(), UInt.MAX_VALUE.toInt())), i16x8(0, (-1).toShort(), Short.MIN_VALUE, Short.MAX_VALUE, Short.MIN_VALUE, Short.MAX_VALUE, 0, (-1).toShort()))
        compareBytes(u16x8NarrowI32x4(i32x4(UShort.MAX_VALUE.into(), -1, Short.MIN_VALUE.into(), Short.MAX_VALUE.into()), i32x4(Int.MIN_VALUE, Int.MAX_VALUE, UInt.MIN_VALUE.toInt(), UInt.MAX_VALUE.toInt())), i16x8((-1).toShort(), 0, 0, Short.MAX_VALUE, 0, (-1).toShort(), 0, 0))
    }

    @kotlin.test.Test
    public fun testExtend() {
        val zero = i8x16Splat(0)
        val ones = i8x16Splat((0.inv()).toByte())
        compareBytes(i16x8ExtendLowI8x16(zero), zero)
        compareBytes(i16x8ExtendHighI8x16(zero), zero)
        compareBytes(i16x8ExtendLowU8x16(zero), zero)
        compareBytes(i16x8ExtendHighU8x16(zero), zero)
        compareBytes(i16x8ExtendLowI8x16(ones), ones)
        compareBytes(i16x8ExtendHighI8x16(ones), ones)
        val halves = u16x8Splat(UByte.MAX_VALUE.into())
        compareBytes(i16x8ExtendLowU8x16(ones), halves)
        compareBytes(i16x8ExtendHighU8x16(ones), halves)
        compareBytes(i32x4ExtendLowI16x8(zero), zero)
        compareBytes(i32x4ExtendHighI16x8(zero), zero)
        compareBytes(i32x4ExtendLowU16x8(zero), zero)
        compareBytes(i32x4ExtendHighU16x8(zero), zero)
        compareBytes(i32x4ExtendLowI16x8(ones), ones)
        compareBytes(i32x4ExtendHighI16x8(ones), ones)
        val halves1 = u32x4Splat(UShort.MAX_VALUE.into())
        compareBytes(i32x4ExtendLowU16x8(ones), halves1)
        compareBytes(i32x4ExtendHighU16x8(ones), halves1)
        compareBytes(i64x2ExtendLowI32x4(zero), zero)
        compareBytes(i64x2ExtendHighI32x4(zero), zero)
        compareBytes(i64x2ExtendLowU32x4(zero), zero)
        compareBytes(i64x2ExtendHighU32x4(zero), zero)
        compareBytes(i64x2ExtendLowI32x4(ones), ones)
        compareBytes(i64x2ExtendHighI32x4(ones), ones)
        val halves2 = i64x2Splat(UInt.MAX_VALUE.into())
        compareBytes(u64x2ExtendLowU32x4(ones), halves2)
        compareBytes(u64x2ExtendHighU32x4(ones), halves2)
    }

    @kotlin.test.Test
    public fun testDot() {
        val zero = i8x16Splat(0)
        val ones = i8x16Splat((0.inv()).toByte())
        val two = i32x4Splat(2)
        compareBytes(i32x4DotI16x8(zero, zero), zero)
        compareBytes(i32x4DotI16x8(ones, ones), two)
    }

    @kotlin.test.Test
    public fun testI8x16Add() {
        run {
            val v1 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ByteArray, V128>(v1, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ByteArray, V128>(v2, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i8x16Add(v1V128, v2V128)
            var v3 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] + v2[i].toByte()).toByte()
                check(actual == expected)
            }
            val v11 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v21 = listOf(-2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -18)
            val v1V1281: V128 = transmuteWithLayouts<ByteArray, V128>(v11, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i8x16Add(v1V1281, v2V1281)
            var v31 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v31 = transmuteWithLayouts<V128, ByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] + v21[i1].toByte()).toByte()
                check(actual1 == expected1)
            }
            val v12 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v22 = listOf(127, -44, 43, 126, 4, 2, 9, -3, -59, -43, 39, -69, 79, -3, 9, -24)
            val v1V1282: V128 = transmuteWithLayouts<ByteArray, V128>(v12, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<Any?, V128>(v22, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = i8x16Add(v1V1282, v2V1282)
            var v32 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v32 = transmuteWithLayouts<V128, ByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = (v12[i2] + v22[i2].toByte()).toByte()
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16AddSatS() {
        run {
            val v1 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ByteArray, V128>(v1, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ByteArray, V128>(v2, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i8x16AddSat(v1V128, v2V128)
            var v3 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].saturatingAdd(v2[i])
                check(actual == expected)
            }
            val v11 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v21 = listOf(-2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -18)
            val v1V1281: V128 = transmuteWithLayouts<ByteArray, V128>(v11, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i8x16AddSat(v1V1281, v2V1281)
            var v31 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v31 = transmuteWithLayouts<V128, ByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].saturatingAdd(v21[i1].toByte())
                check(actual1 == expected1)
            }
            val v12 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v22 = listOf(127, -44, 43, 126, 4, 2, 9, -3, -59, -43, 39, -69, 79, -3, 9, -24)
            val v1V1282: V128 = transmuteWithLayouts<ByteArray, V128>(v12, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<Any?, V128>(v22, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = i8x16AddSat(v1V1282, v2V1282)
            var v32 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v32 = transmuteWithLayouts<V128, ByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].saturatingAdd(v22[i2].toByte())
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16AddSatU() {
        run {
            val v1 = ubyteArrayOf(0u.toUByte(), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val v2 = ubyteArrayOf(1u.toUByte(), 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u)
            val v1V128: V128 = transmuteWithLayouts<UByteArray, V128>(v1, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<UByteArray, V128>(v2, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = u8x16AddSat(v1V128, v2V128)
            var v3 = ubyteArrayOf(0u.toUByte(), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].saturatingAdd(v2[i])
                check(actual == expected)
            }
            val v11 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            val v21 = listOf(255, 254, 253, 252, 251, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240)
            val v1V1281: V128 = transmuteWithLayouts<UByteArray, V128>(v11, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<IntArray, V128>(v21, PrimitiveArrayLayouts.intArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val v3V1281 = u8x16AddSat(v1V1281, v2V1281)
            var v31 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            v31 = transmuteWithLayouts<V128, UByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].saturatingAdd(v21[i1].toUByte())
                check(actual1 == expected1)
            }
            val v12 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            val v22 = ubyteArrayOf(127u, (-44).toUByte(), 43u, 126u, 4u, 2u, 9u, (-3).toUByte(), (-59).toUByte(), (-43).toUByte(), 39u, (-69).toUByte(), 79u, (-3).toUByte(), 9u, (-24).toUByte())
            val v1V1282: V128 = transmuteWithLayouts<UByteArray, V128>(v12, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<UByteArray, V128>(v22, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = u8x16AddSat(v1V1282, v2V1282)
            var v32 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            v32 = transmuteWithLayouts<V128, UByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].saturatingAdd(v22[i2])
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16Sub() {
        run {
            val v1 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ByteArray, V128>(v1, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ByteArray, V128>(v2, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i8x16Sub(v1V128, v2V128)
            var v3 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] - v2[i].toByte()).toByte()
                check(actual == expected)
            }
            val v11 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v21 = listOf(-2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -18)
            val v1V1281: V128 = transmuteWithLayouts<ByteArray, V128>(v11, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i8x16Sub(v1V1281, v2V1281)
            var v31 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v31 = transmuteWithLayouts<V128, ByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] - v21[i1].toByte()).toByte()
                check(actual1 == expected1)
            }
            val v12 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v22 = listOf(-127, -44, 43, 126, 4, 2, 9, -3, -59, -43, 39, -69, 79, -3, 4, 8)
            val v1V1282: V128 = transmuteWithLayouts<ByteArray, V128>(v12, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<Any?, V128>(v22, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = i8x16Sub(v1V1282, v2V1282)
            var v32 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v32 = transmuteWithLayouts<V128, ByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = (v12[i2] - v22[i2].toByte()).toByte()
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16SubSatS() {
        run {
            val v1 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ByteArray, V128>(v1, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ByteArray, V128>(v2, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i8x16SubSat(v1V128, v2V128)
            var v3 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].saturatingSub(v2[i])
                check(actual == expected)
            }
            val v11 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v21 = listOf(-2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -18)
            val v1V1281: V128 = transmuteWithLayouts<ByteArray, V128>(v11, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i8x16SubSat(v1V1281, v2V1281)
            var v31 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v31 = transmuteWithLayouts<V128, ByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].saturatingSub(v21[i1].toByte())
                check(actual1 == expected1)
            }
            val v12 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v22 = listOf(-127, -44, 43, 126, 4, 2, 9, -3, -59, -43, 39, -69, 79, -3, 4, 8)
            val v1V1282: V128 = transmuteWithLayouts<ByteArray, V128>(v12, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<Any?, V128>(v22, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = i8x16SubSat(v1V1282, v2V1282)
            var v32 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v32 = transmuteWithLayouts<V128, ByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].saturatingSub(v22[i2].toByte())
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16SubSatU() {
        run {
            val v1 = ubyteArrayOf(0u.toUByte(), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val v2 = ubyteArrayOf(1u.toUByte(), 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u)
            val v1V128: V128 = transmuteWithLayouts<UByteArray, V128>(v1, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<UByteArray, V128>(v2, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = u8x16SubSat(v1V128, v2V128)
            var v3 = ubyteArrayOf(0u.toUByte(), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].saturatingSub(v2[i])
                check(actual == expected)
            }
            val v11 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            val v21 = listOf(255, 254, 253, 252, 251, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240)
            val v1V1281: V128 = transmuteWithLayouts<UByteArray, V128>(v11, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<IntArray, V128>(v21, PrimitiveArrayLayouts.intArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val v3V1281 = u8x16SubSat(v1V1281, v2V1281)
            var v31 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            v31 = transmuteWithLayouts<V128, UByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].saturatingSub(v21[i1].toUByte())
                check(actual1 == expected1)
            }
            val v12 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            val v22 = ubyteArrayOf(127u, (-44).toUByte(), 43u, 126u, 4u, 2u, 9u, (-3).toUByte(), (-59).toUByte(), (-43).toUByte(), 39u, (-69).toUByte(), 79u, (-3).toUByte(), 9u, (-24).toUByte())
            val v1V1282: V128 = transmuteWithLayouts<UByteArray, V128>(v12, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<UByteArray, V128>(v22, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = u8x16SubSat(v1V1282, v2V1282)
            var v32 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            v32 = transmuteWithLayouts<V128, UByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].saturatingSub(v22[i2])
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16MinS() {
        run {
            val v1 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ByteArray, V128>(v1, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ByteArray, V128>(v2, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i8x16Min(v1V128, v2V128)
            var v3 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].min(v2[i])
                check(actual == expected)
            }
            val v11 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v21 = listOf(-2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -18)
            val v1V1281: V128 = transmuteWithLayouts<ByteArray, V128>(v11, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i8x16Min(v1V1281, v2V1281)
            var v31 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v31 = transmuteWithLayouts<V128, ByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].min(v21[i1])
                check(actual1 == expected1)
            }
            val v12 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v22 = listOf(-127, -44, 43, 126, 4, 2, 9, -3, -59, -43, 39, -69, 79, -3, 4, 8)
            val v1V1282: V128 = transmuteWithLayouts<ByteArray, V128>(v12, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<Any?, V128>(v22, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = i8x16Min(v1V1282, v2V1282)
            var v32 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v32 = transmuteWithLayouts<V128, ByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].min(v22[i2])
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16MinU() {
        run {
            val v1 = ubyteArrayOf(0u.toUByte(), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val v2 = ubyteArrayOf(1u.toUByte(), 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u)
            val v1V128: V128 = transmuteWithLayouts<UByteArray, V128>(v1, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<UByteArray, V128>(v2, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = u8x16Min(v1V128, v2V128)
            var v3 = ubyteArrayOf(0u.toUByte(), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].min(v2[i])
                check(actual == expected)
            }
            val v11 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            val v21 = listOf(255, 254, 253, 252, 251, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240)
            val v1V1281: V128 = transmuteWithLayouts<UByteArray, V128>(v11, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<IntArray, V128>(v21, PrimitiveArrayLayouts.intArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val v3V1281 = u8x16Min(v1V1281, v2V1281)
            var v31 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            v31 = transmuteWithLayouts<V128, UByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].min(v21[i1])
                check(actual1 == expected1)
            }
            val v12 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            val v22 = ubyteArrayOf(127u, (-44).toUByte(), 43u, 126u, 4u, 2u, 9u, (-3).toUByte(), (-59).toUByte(), (-43).toUByte(), 39u, (-69).toUByte(), 79u, (-3).toUByte(), 9u, (-24).toUByte())
            val v1V1282: V128 = transmuteWithLayouts<UByteArray, V128>(v12, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<UByteArray, V128>(v22, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = u8x16Min(v1V1282, v2V1282)
            var v32 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            v32 = transmuteWithLayouts<V128, UByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].min(v22[i2])
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16MaxS() {
        run {
            val v1 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ByteArray, V128>(v1, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ByteArray, V128>(v2, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i8x16Max(v1V128, v2V128)
            var v3 = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].max(v2[i])
                check(actual == expected)
            }
            val v11 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v21 = listOf(-2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, -15, -16, -18)
            val v1V1281: V128 = transmuteWithLayouts<ByteArray, V128>(v11, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i8x16Max(v1V1281, v2V1281)
            var v31 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v31 = transmuteWithLayouts<V128, ByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].max(v21[i1])
                check(actual1 == expected1)
            }
            val v12 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            val v22 = listOf(-127, -44, 43, 126, 4, 2, 9, -3, -59, -43, 39, -69, 79, -3, 4, 8)
            val v1V1282: V128 = transmuteWithLayouts<ByteArray, V128>(v12, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<Any?, V128>(v22, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = i8x16Max(v1V1282, v2V1282)
            var v32 = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
            v32 = transmuteWithLayouts<V128, ByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].max(v22[i2])
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16MaxU() {
        run {
            val v1 = ubyteArrayOf(0u.toUByte(), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val v2 = ubyteArrayOf(1u.toUByte(), 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u)
            val v1V128: V128 = transmuteWithLayouts<UByteArray, V128>(v1, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<UByteArray, V128>(v2, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = u8x16Max(v1V128, v2V128)
            var v3 = ubyteArrayOf(0u.toUByte(), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].max(v2[i])
                check(actual == expected)
            }
            val v11 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            val v21 = listOf(255, 254, 253, 252, 251, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240)
            val v1V1281: V128 = transmuteWithLayouts<UByteArray, V128>(v11, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<IntArray, V128>(v21, PrimitiveArrayLayouts.intArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val v3V1281 = u8x16Max(v1V1281, v2V1281)
            var v31 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            v31 = transmuteWithLayouts<V128, UByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].max(v21[i1])
                check(actual1 == expected1)
            }
            val v12 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            val v22 = ubyteArrayOf(127u, (-44).toUByte(), 43u, 126u, 4u, 2u, 9u, (-3).toUByte(), (-59).toUByte(), (-43).toUByte(), 39u, (-69).toUByte(), 79u, (-3).toUByte(), 9u, (-24).toUByte())
            val v1V1282: V128 = transmuteWithLayouts<UByteArray, V128>(v12, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<UByteArray, V128>(v22, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = u8x16Max(v1V1282, v2V1282)
            var v32 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            v32 = transmuteWithLayouts<V128, UByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].max(v22[i2])
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16AvgrU() {
        run {
            val v1 = ubyteArrayOf(0u.toUByte(), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val v2 = ubyteArrayOf(1u.toUByte(), 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u, 1u)
            val v1V128: V128 = transmuteWithLayouts<UByteArray, V128>(v1, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<UByteArray, V128>(v2, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = u8x16Avgr(v1V128, v2V128)
            var v3 = ubyteArrayOf(0u.toUByte(), 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UByteArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].avgr(v2[i])
                check(actual == expected)
            }
            val v11 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            val v21 = listOf(255, 254, 253, 252, 251, 250, 249, 248, 247, 246, 245, 244, 243, 242, 241, 240)
            val v1V1281: V128 = transmuteWithLayouts<UByteArray, V128>(v11, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<IntArray, V128>(v21, PrimitiveArrayLayouts.intArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val v3V1281 = u8x16Avgr(v1V1281, v2V1281)
            var v31 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            v31 = transmuteWithLayouts<V128, UByteArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].avgr(v21[i1])
                check(actual1 == expected1)
            }
            val v12 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            val v22 = ubyteArrayOf(127u, (-44).toUByte(), 43u, 126u, 4u, 2u, 9u, (-3).toUByte(), (-59).toUByte(), (-43).toUByte(), 39u, (-69).toUByte(), 79u, (-3).toUByte(), 9u, (-24).toUByte())
            val v1V1282: V128 = transmuteWithLayouts<UByteArray, V128>(v12, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<UByteArray, V128>(v22, UnresolvedTypeLayout<UByteArray>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = u8x16Avgr(v1V1282, v2V1282)
            var v32 = ubyteArrayOf(1u.toUByte(), 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u, 16u)
            v32 = transmuteWithLayouts<V128, UByteArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].avgr(v22[i2])
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8Add() {
        run {
            val v1 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ShortArray, V128>(v1, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ShortArray, V128>(v2, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i16x8Add(v1V128, v2V128)
            var v3 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] + v2[i].toShort()).toShort()
                check(actual == expected)
            }
            val v11 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            val v21 = listOf(32767, 8, -2494, -4, 4882, -4, 848, 3830)
            val v1V1281: V128 = transmuteWithLayouts<ShortArray, V128>(v11, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i16x8Add(v1V1281, v2V1281)
            var v31 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            v31 = transmuteWithLayouts<V128, ShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] + v21[i1].toShort()).toShort()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8AddSatS() {
        run {
            val v1 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ShortArray, V128>(v1, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ShortArray, V128>(v2, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i16x8AddSat(v1V128, v2V128)
            var v3 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].saturatingAdd(v2[i])
                check(actual == expected)
            }
            val v11 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            val v21 = listOf(32767, 8, -2494, -4, 4882, -4, 848, 3830)
            val v1V1281: V128 = transmuteWithLayouts<ShortArray, V128>(v11, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i16x8AddSat(v1V1281, v2V1281)
            var v31 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            v31 = transmuteWithLayouts<V128, ShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].saturatingAdd(v21[i1].toShort())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8AddSatU() {
        run {
            val v1 = ushortArrayOf(0u.toUShort(), 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val v2 = ushortArrayOf(1u.toUShort(), 1u, 1u, 1u, 1u, 1u, 1u, 1u)
            val v1V128: V128 = transmuteWithLayouts<UShortArray, V128>(v1, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<UShortArray, V128>(v2, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = u16x8AddSat(v1V128, v2V128)
            var v3 = ushortArrayOf(0u.toUShort(), 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].saturatingAdd(v2[i])
                check(actual == expected)
            }
            val v11 = ushortArrayOf(1u.toUShort(), 2u, 3u, 4u, 5u, 6u, 7u, 8u)
            val v21 = ushortArrayOf(32767u, 8u, (-2494).toUShort(), (-4).toUShort(), 4882u, (-4).toUShort(), 848u, 3830u)
            val v1V1281: V128 = transmuteWithLayouts<UShortArray, V128>(v11, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<UShortArray, V128>(v21, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = u16x8AddSat(v1V1281, v2V1281)
            var v31 = ushortArrayOf(1u.toUShort(), 2u, 3u, 4u, 5u, 6u, 7u, 8u)
            v31 = transmuteWithLayouts<V128, UShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].saturatingAdd(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8Sub() {
        run {
            val v1 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ShortArray, V128>(v1, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ShortArray, V128>(v2, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i16x8Sub(v1V128, v2V128)
            var v3 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] - v2[i].toShort()).toShort()
                check(actual == expected)
            }
            val v11 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            val v21 = listOf(32767, 8, -2494, -4, 4882, -4, 848, 3830)
            val v1V1281: V128 = transmuteWithLayouts<ShortArray, V128>(v11, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i16x8Sub(v1V1281, v2V1281)
            var v31 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            v31 = transmuteWithLayouts<V128, ShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] - v21[i1].toShort()).toShort()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8SubSatS() {
        run {
            val v1 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ShortArray, V128>(v1, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ShortArray, V128>(v2, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i16x8SubSat(v1V128, v2V128)
            var v3 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].saturatingSub(v2[i])
                check(actual == expected)
            }
            val v11 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            val v21 = listOf(32767, 8, -2494, -4, 4882, -4, 848, 3830)
            val v1V1281: V128 = transmuteWithLayouts<ShortArray, V128>(v11, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i16x8SubSat(v1V1281, v2V1281)
            var v31 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            v31 = transmuteWithLayouts<V128, ShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].saturatingSub(v21[i1].toShort())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8SubSatU() {
        run {
            val v1 = ushortArrayOf(0u.toUShort(), 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val v2 = ushortArrayOf(1u.toUShort(), 1u, 1u, 1u, 1u, 1u, 1u, 1u)
            val v1V128: V128 = transmuteWithLayouts<UShortArray, V128>(v1, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<UShortArray, V128>(v2, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = u16x8SubSat(v1V128, v2V128)
            var v3 = ushortArrayOf(0u.toUShort(), 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].saturatingSub(v2[i])
                check(actual == expected)
            }
            val v11 = ushortArrayOf(1u.toUShort(), 2u, 3u, 4u, 5u, 6u, 7u, 8u)
            val v21 = ushortArrayOf(32767u, 8u, (-2494).toUShort(), (-4).toUShort(), 4882u, (-4).toUShort(), 848u, 3830u)
            val v1V1281: V128 = transmuteWithLayouts<UShortArray, V128>(v11, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<UShortArray, V128>(v21, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = u16x8SubSat(v1V1281, v2V1281)
            var v31 = ushortArrayOf(1u.toUShort(), 2u, 3u, 4u, 5u, 6u, 7u, 8u)
            v31 = transmuteWithLayouts<V128, UShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].saturatingSub(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8Mul() {
        run {
            val v1 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ShortArray, V128>(v1, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ShortArray, V128>(v2, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i16x8Mul(v1V128, v2V128)
            var v3 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] * v2[i].toShort()).toShort()
                check(actual == expected)
            }
            val v11 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            val v21 = listOf(32767, 8, -2494, -4, 4882, -4, 848, 3830)
            val v1V1281: V128 = transmuteWithLayouts<ShortArray, V128>(v11, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i16x8Mul(v1V1281, v2V1281)
            var v31 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            v31 = transmuteWithLayouts<V128, ShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] * v21[i1].toShort()).toShort()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8MinS() {
        run {
            val v1 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ShortArray, V128>(v1, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ShortArray, V128>(v2, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i16x8Min(v1V128, v2V128)
            var v3 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].min(v2[i])
                check(actual == expected)
            }
            val v11 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            val v21 = listOf(32767, 8, -2494, -4, 4882, -4, 848, 3830)
            val v1V1281: V128 = transmuteWithLayouts<ShortArray, V128>(v11, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i16x8Min(v1V1281, v2V1281)
            var v31 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            v31 = transmuteWithLayouts<V128, ShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].min(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8MinU() {
        run {
            val v1 = ushortArrayOf(0u.toUShort(), 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val v2 = ushortArrayOf(1u.toUShort(), 1u, 1u, 1u, 1u, 1u, 1u, 1u)
            val v1V128: V128 = transmuteWithLayouts<UShortArray, V128>(v1, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<UShortArray, V128>(v2, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = u16x8Min(v1V128, v2V128)
            var v3 = ushortArrayOf(0u.toUShort(), 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].min(v2[i])
                check(actual == expected)
            }
            val v11 = ushortArrayOf(1u.toUShort(), 2u, 3u, 4u, 5u, 6u, 7u, 8u)
            val v21 = ushortArrayOf(32767u, 8u, (-2494).toUShort(), (-4).toUShort(), 4882u, (-4).toUShort(), 848u, 3830u)
            val v1V1281: V128 = transmuteWithLayouts<UShortArray, V128>(v11, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<UShortArray, V128>(v21, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = u16x8Min(v1V1281, v2V1281)
            var v31 = ushortArrayOf(1u.toUShort(), 2u, 3u, 4u, 5u, 6u, 7u, 8u)
            v31 = transmuteWithLayouts<V128, UShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].min(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8MaxS() {
        run {
            val v1 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            val v2 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ShortArray, V128>(v1, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<ShortArray, V128>(v2, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = i16x8Max(v1V128, v2V128)
            var v3 = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, ShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].max(v2[i])
                check(actual == expected)
            }
            val v11 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            val v21 = listOf(32767, 8, -2494, -4, 4882, -4, 848, 3830)
            val v1V1281: V128 = transmuteWithLayouts<ShortArray, V128>(v11, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i16x8Max(v1V1281, v2V1281)
            var v31 = shortArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
            v31 = transmuteWithLayouts<V128, ShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].max(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8MaxU() {
        run {
            val v1 = ushortArrayOf(0u.toUShort(), 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val v2 = ushortArrayOf(1u.toUShort(), 1u, 1u, 1u, 1u, 1u, 1u, 1u)
            val v1V128: V128 = transmuteWithLayouts<UShortArray, V128>(v1, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<UShortArray, V128>(v2, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = u16x8Max(v1V128, v2V128)
            var v3 = ushortArrayOf(0u.toUShort(), 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].max(v2[i])
                check(actual == expected)
            }
            val v11 = ushortArrayOf(1u.toUShort(), 2u, 3u, 4u, 5u, 6u, 7u, 8u)
            val v21 = ushortArrayOf(32767u, 8u, (-2494).toUShort(), (-4).toUShort(), 4882u, (-4).toUShort(), 848u, 3830u)
            val v1V1281: V128 = transmuteWithLayouts<UShortArray, V128>(v11, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<UShortArray, V128>(v21, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = u16x8Max(v1V1281, v2V1281)
            var v31 = ushortArrayOf(1u.toUShort(), 2u, 3u, 4u, 5u, 6u, 7u, 8u)
            v31 = transmuteWithLayouts<V128, UShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].max(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8AvgrU() {
        run {
            val v1 = ushortArrayOf(0u.toUShort(), 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val v2 = ushortArrayOf(1u.toUShort(), 1u, 1u, 1u, 1u, 1u, 1u, 1u)
            val v1V128: V128 = transmuteWithLayouts<UShortArray, V128>(v1, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<UShortArray, V128>(v2, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V128 = u16x8Avgr(v1V128, v2V128)
            var v3 = ushortArrayOf(0u.toUShort(), 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UShortArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UShortArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].avgr(v2[i])
                check(actual == expected)
            }
            val v11 = ushortArrayOf(1u.toUShort(), 2u, 3u, 4u, 5u, 6u, 7u, 8u)
            val v21 = ushortArrayOf(32767u, 8u, (-2494).toUShort(), (-4).toUShort(), 4882u, (-4).toUShort(), 848u, 3830u)
            val v1V1281: V128 = transmuteWithLayouts<UShortArray, V128>(v11, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<UShortArray, V128>(v21, UnresolvedTypeLayout<UShortArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = u16x8Avgr(v1V1281, v2V1281)
            var v31 = ushortArrayOf(1u.toUShort(), 2u, 3u, 4u, 5u, 6u, 7u, 8u)
            v31 = transmuteWithLayouts<V128, UShortArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UShortArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].avgr(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI32x4Add() {
        run {
            val v1 = intArrayOf(0, 0, 0, 0)
            val v2 = listOf(1, 2, 3, 4)
            val v1V128: V128 = transmuteWithLayouts<IntArray, V128>(v1, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<IntArray, V128>(v2, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v3V128 = i32x4Add(v1V128, v2V128)
            var v3 = intArrayOf(0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, IntArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] + v2[i].toInt())
                check(actual == expected)
            }
            val v11 = intArrayOf(1, 1283, Int.MAX_VALUE, Int.MIN_VALUE)
            val v21 = IntArray(4) { Int.MAX_VALUE }
            val v1V1281: V128 = transmuteWithLayouts<IntArray, V128>(v11, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<IntArray, V128>(v21, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i32x4Add(v1V1281, v2V1281)
            var v31 = intArrayOf(1, 1283, Int.MAX_VALUE, Int.MIN_VALUE)
            v31 = transmuteWithLayouts<V128, IntArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] + v21[i1].toInt())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI32x4Sub() {
        run {
            val v1 = intArrayOf(0, 0, 0, 0)
            val v2 = listOf(1, 2, 3, 4)
            val v1V128: V128 = transmuteWithLayouts<IntArray, V128>(v1, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<IntArray, V128>(v2, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v3V128 = i32x4Sub(v1V128, v2V128)
            var v3 = intArrayOf(0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, IntArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] - v2[i].toInt())
                check(actual == expected)
            }
            val v11 = intArrayOf(1, 1283, Int.MAX_VALUE, Int.MIN_VALUE)
            val v21 = IntArray(4) { Int.MAX_VALUE }
            val v1V1281: V128 = transmuteWithLayouts<IntArray, V128>(v11, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<IntArray, V128>(v21, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i32x4Sub(v1V1281, v2V1281)
            var v31 = intArrayOf(1, 1283, Int.MAX_VALUE, Int.MIN_VALUE)
            v31 = transmuteWithLayouts<V128, IntArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] - v21[i1].toInt())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI32x4Mul() {
        run {
            val v1 = intArrayOf(0, 0, 0, 0)
            val v2 = listOf(1, 2, 3, 4)
            val v1V128: V128 = transmuteWithLayouts<IntArray, V128>(v1, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<IntArray, V128>(v2, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v3V128 = i32x4Mul(v1V128, v2V128)
            var v3 = intArrayOf(0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, IntArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] * v2[i].toInt())
                check(actual == expected)
            }
            val v11 = intArrayOf(1, 1283, Int.MAX_VALUE, Int.MIN_VALUE)
            val v21 = IntArray(4) { Int.MAX_VALUE }
            val v1V1281: V128 = transmuteWithLayouts<IntArray, V128>(v11, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<IntArray, V128>(v21, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i32x4Mul(v1V1281, v2V1281)
            var v31 = intArrayOf(1, 1283, Int.MAX_VALUE, Int.MIN_VALUE)
            v31 = transmuteWithLayouts<V128, IntArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] * v21[i1].toInt())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI32x4MinS() {
        run {
            val v1 = intArrayOf(0, 0, 0, 0)
            val v2 = listOf(1, 2, 3, 4)
            val v1V128: V128 = transmuteWithLayouts<IntArray, V128>(v1, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<IntArray, V128>(v2, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v3V128 = i32x4Min(v1V128, v2V128)
            var v3 = intArrayOf(0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, IntArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].min(v2[i])
                check(actual == expected)
            }
            val v11 = intArrayOf(1, 1283, Int.MAX_VALUE, Int.MIN_VALUE)
            val v21 = IntArray(4) { Int.MAX_VALUE }
            val v1V1281: V128 = transmuteWithLayouts<IntArray, V128>(v11, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<IntArray, V128>(v21, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i32x4Min(v1V1281, v2V1281)
            var v31 = intArrayOf(1, 1283, Int.MAX_VALUE, Int.MIN_VALUE)
            v31 = transmuteWithLayouts<V128, IntArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].min(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI32x4MinU() {
        run {
            val v1 = uintArrayOf(0u, 0u, 0u, 0u)
            val v2 = listOf(1, 2, 3, 4)
            val v1V128: V128 = transmuteWithLayouts<UIntArray, V128>(v1, UnresolvedTypeLayout<UIntArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<IntArray, V128>(v2, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v3V128 = u32x4Min(v1V128, v2V128)
            var v3 = uintArrayOf(0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UIntArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UIntArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].min(v2[i])
                check(actual == expected)
            }
            val v11 = uintArrayOf(1u, 1283u, Int.MAX_VALUE.toUInt(), Int.MIN_VALUE.toUInt())
            val v21 = UIntArray(4) { Int.MAX_VALUE.toUInt() }
            val v1V1281: V128 = transmuteWithLayouts<UIntArray, V128>(v11, UnresolvedTypeLayout<UIntArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<UIntArray, V128>(v21, PrimitiveArrayLayouts.uIntArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v3V1281 = u32x4Min(v1V1281, v2V1281)
            var v31 = uintArrayOf(1u, 1283u, Int.MAX_VALUE.toUInt(), Int.MIN_VALUE.toUInt())
            v31 = transmuteWithLayouts<V128, UIntArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UIntArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].min(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI32x4MaxS() {
        run {
            val v1 = intArrayOf(0, 0, 0, 0)
            val v2 = listOf(1, 2, 3, 4)
            val v1V128: V128 = transmuteWithLayouts<IntArray, V128>(v1, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<IntArray, V128>(v2, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v3V128 = i32x4Max(v1V128, v2V128)
            var v3 = intArrayOf(0, 0, 0, 0)
            v3 = transmuteWithLayouts<V128, IntArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].max(v2[i])
                check(actual == expected)
            }
            val v11 = intArrayOf(1, 1283, Int.MAX_VALUE, Int.MIN_VALUE)
            val v21 = IntArray(4) { Int.MAX_VALUE }
            val v1V1281: V128 = transmuteWithLayouts<IntArray, V128>(v11, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<IntArray, V128>(v21, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i32x4Max(v1V1281, v2V1281)
            var v31 = intArrayOf(1, 1283, Int.MAX_VALUE, Int.MIN_VALUE)
            v31 = transmuteWithLayouts<V128, IntArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].max(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI32x4MaxU() {
        run {
            val v1 = uintArrayOf(0u, 0u, 0u, 0u)
            val v2 = listOf(1, 2, 3, 4)
            val v1V128: V128 = transmuteWithLayouts<UIntArray, V128>(v1, UnresolvedTypeLayout<UIntArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<IntArray, V128>(v2, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v3V128 = u32x4Max(v1V128, v2V128)
            var v3 = uintArrayOf(0u, 0u, 0u, 0u)
            v3 = transmuteWithLayouts<V128, UIntArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UIntArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].max(v2[i])
                check(actual == expected)
            }
            val v11 = uintArrayOf(1u, 1283u, Int.MAX_VALUE.toUInt(), Int.MIN_VALUE.toUInt())
            val v21 = UIntArray(4) { Int.MAX_VALUE.toUInt() }
            val v1V1281: V128 = transmuteWithLayouts<UIntArray, V128>(v11, UnresolvedTypeLayout<UIntArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<UIntArray, V128>(v21, PrimitiveArrayLayouts.uIntArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v3V1281 = u32x4Max(v1V1281, v2V1281)
            var v31 = uintArrayOf(1u, 1283u, Int.MAX_VALUE.toUInt(), Int.MIN_VALUE.toUInt())
            v31 = transmuteWithLayouts<V128, UIntArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UIntArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].max(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI64x2Add() {
        run {
            val v1 = longArrayOf(0L, 0L)
            val v2 = listOf(1, 2)
            val v1V128: V128 = transmuteWithLayouts<LongArray, V128>(v1, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<IntArray, V128>(v2, PrimitiveArrayLayouts.intArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val v3V128 = i64x2Add(v1V128, v2V128)
            var v3 = longArrayOf(0L, 0L)
            v3 = transmuteWithLayouts<V128, LongArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] + v2[i].toLong())
                check(actual == expected)
            }
            val v11 = longArrayOf(Long.MIN_VALUE, Long.MAX_VALUE)
            val v21 = longArrayOf(Long.MAX_VALUE, Long.MIN_VALUE)
            val v1V1281: V128 = transmuteWithLayouts<LongArray, V128>(v11, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<LongArray, V128>(v21, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i64x2Add(v1V1281, v2V1281)
            var v31 = longArrayOf(Long.MIN_VALUE, Long.MAX_VALUE)
            v31 = transmuteWithLayouts<V128, LongArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] + v21[i1].toLong())
                check(actual1 == expected1)
            }
            val v12 = LongArray(2) { Long.MAX_VALUE }
            val v22 = LongArray(2) { Long.MAX_VALUE }
            val v1V1282: V128 = transmuteWithLayouts<LongArray, V128>(v12, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<LongArray, V128>(v22, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = i64x2Add(v1V1282, v2V1282)
            var v32 = LongArray(2) { Long.MAX_VALUE }
            v32 = transmuteWithLayouts<V128, LongArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = (v12[i2] + v22[i2].toLong())
                check(actual2 == expected2)
            }
            val v13 = longArrayOf(-4L, -4L)
            val v23 = listOf(800, 939)
            val v1V1283: V128 = transmuteWithLayouts<LongArray, V128>(v13, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1283: V128 = transmuteWithLayouts<IntArray, V128>(v23, PrimitiveArrayLayouts.intArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val v3V1283 = i64x2Add(v1V1283, v2V1283)
            var v33 = longArrayOf(-4L, -4L)
            v33 = transmuteWithLayouts<V128, LongArray>(v3V1283, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i3, actual3) in v33.mapIndexed { index, item -> Pair(index, item) }) {
                val expected3 = (v13[i3] + v23[i3].toLong())
                check(actual3 == expected3)
            }
        }
    }

    @kotlin.test.Test
    public fun testI64x2Sub() {
        run {
            val v1 = longArrayOf(0L, 0L)
            val v2 = listOf(1, 2)
            val v1V128: V128 = transmuteWithLayouts<LongArray, V128>(v1, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<IntArray, V128>(v2, PrimitiveArrayLayouts.intArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val v3V128 = i64x2Sub(v1V128, v2V128)
            var v3 = longArrayOf(0L, 0L)
            v3 = transmuteWithLayouts<V128, LongArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] - v2[i].toLong())
                check(actual == expected)
            }
            val v11 = longArrayOf(Long.MIN_VALUE, Long.MAX_VALUE)
            val v21 = longArrayOf(Long.MAX_VALUE, Long.MIN_VALUE)
            val v1V1281: V128 = transmuteWithLayouts<LongArray, V128>(v11, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<LongArray, V128>(v21, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i64x2Sub(v1V1281, v2V1281)
            var v31 = longArrayOf(Long.MIN_VALUE, Long.MAX_VALUE)
            v31 = transmuteWithLayouts<V128, LongArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] - v21[i1].toLong())
                check(actual1 == expected1)
            }
            val v12 = LongArray(2) { Long.MAX_VALUE }
            val v22 = LongArray(2) { Long.MAX_VALUE }
            val v1V1282: V128 = transmuteWithLayouts<LongArray, V128>(v12, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<LongArray, V128>(v22, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = i64x2Sub(v1V1282, v2V1282)
            var v32 = LongArray(2) { Long.MAX_VALUE }
            v32 = transmuteWithLayouts<V128, LongArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = (v12[i2] - v22[i2].toLong())
                check(actual2 == expected2)
            }
            val v13 = longArrayOf(-4L, -4L)
            val v23 = listOf(800, 939)
            val v1V1283: V128 = transmuteWithLayouts<LongArray, V128>(v13, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1283: V128 = transmuteWithLayouts<IntArray, V128>(v23, PrimitiveArrayLayouts.intArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val v3V1283 = i64x2Sub(v1V1283, v2V1283)
            var v33 = longArrayOf(-4L, -4L)
            v33 = transmuteWithLayouts<V128, LongArray>(v3V1283, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i3, actual3) in v33.mapIndexed { index, item -> Pair(index, item) }) {
                val expected3 = (v13[i3] - v23[i3].toLong())
                check(actual3 == expected3)
            }
        }
    }

    @kotlin.test.Test
    public fun testI64x2Mul() {
        run {
            val v1 = longArrayOf(0L, 0L)
            val v2 = listOf(1, 2)
            val v1V128: V128 = transmuteWithLayouts<LongArray, V128>(v1, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<IntArray, V128>(v2, PrimitiveArrayLayouts.intArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val v3V128 = i64x2Mul(v1V128, v2V128)
            var v3 = longArrayOf(0L, 0L)
            v3 = transmuteWithLayouts<V128, LongArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = (v1[i] * v2[i].toLong())
                check(actual == expected)
            }
            val v11 = longArrayOf(Long.MIN_VALUE, Long.MAX_VALUE)
            val v21 = longArrayOf(Long.MAX_VALUE, Long.MIN_VALUE)
            val v1V1281: V128 = transmuteWithLayouts<LongArray, V128>(v11, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<LongArray, V128>(v21, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = i64x2Mul(v1V1281, v2V1281)
            var v31 = longArrayOf(Long.MIN_VALUE, Long.MAX_VALUE)
            v31 = transmuteWithLayouts<V128, LongArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = (v11[i1] * v21[i1].toLong())
                check(actual1 == expected1)
            }
            val v12 = LongArray(2) { Long.MAX_VALUE }
            val v22 = LongArray(2) { Long.MAX_VALUE }
            val v1V1282: V128 = transmuteWithLayouts<LongArray, V128>(v12, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282: V128 = transmuteWithLayouts<LongArray, V128>(v22, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v3V1282 = i64x2Mul(v1V1282, v2V1282)
            var v32 = LongArray(2) { Long.MAX_VALUE }
            v32 = transmuteWithLayouts<V128, LongArray>(v3V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i2, actual2) in v32.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = (v12[i2] * v22[i2].toLong())
                check(actual2 == expected2)
            }
            val v13 = longArrayOf(-4L, -4L)
            val v23 = listOf(800, 939)
            val v1V1283: V128 = transmuteWithLayouts<LongArray, V128>(v13, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1283: V128 = transmuteWithLayouts<IntArray, V128>(v23, PrimitiveArrayLayouts.intArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val v3V1283 = i64x2Mul(v1V1283, v2V1283)
            var v33 = longArrayOf(-4L, -4L)
            v33 = transmuteWithLayouts<V128, LongArray>(v3V1283, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i3, actual3) in v33.mapIndexed { index, item -> Pair(index, item) }) {
                val expected3 = (v13[i3] * v23[i3].toLong())
                check(actual3 == expected3)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Add() {
        run {
            val v1 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            val v2 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f32x4Add(v1V128, v2V128)
            var v3 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            v3 = transmuteWithLayouts<V128, FloatArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].add(v2[i])
                check(actual == expected)
            }
            val v11 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            val v21 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f32x4Add(v1V1281, v2V1281)
            var v31 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            v31 = transmuteWithLayouts<V128, FloatArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].add(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Sub() {
        run {
            val v1 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            val v2 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f32x4Sub(v1V128, v2V128)
            var v3 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            v3 = transmuteWithLayouts<V128, FloatArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].sub(v2[i])
                check(actual == expected)
            }
            val v11 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            val v21 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f32x4Sub(v1V1281, v2V1281)
            var v31 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            v31 = transmuteWithLayouts<V128, FloatArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].sub(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Mul() {
        run {
            val v1 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            val v2 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f32x4Mul(v1V128, v2V128)
            var v3 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            v3 = transmuteWithLayouts<V128, FloatArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].mul(v2[i])
                check(actual == expected)
            }
            val v11 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            val v21 = listOf(1.0, 2.0, 1.0, 0.0)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f32x4Mul(v1V1281, v2V1281)
            var v31 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            v31 = transmuteWithLayouts<V128, FloatArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].mul(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Div() {
        run {
            val v1 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            val v2 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f32x4Div(v1V128, v2V128)
            var v3 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            v3 = transmuteWithLayouts<V128, FloatArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].div(v2[i])
                check(actual == expected)
            }
            val v11 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            val v21 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f32x4Div(v1V1281, v2V1281)
            var v31 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            v31 = transmuteWithLayouts<V128, FloatArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].div(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Min() {
        run {
            val v1 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            val v2 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f32x4Min(v1V128, v2V128)
            var v3 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            v3 = transmuteWithLayouts<V128, FloatArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmMinnumf32(v1[i], v2[i].toFloat())
                check(actual == expected)
            }
            val v11 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            val v21 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f32x4Min(v1V1281, v2V1281)
            var v31 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            v31 = transmuteWithLayouts<V128, FloatArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmMinnumf32(v11[i1], v21[i1].toFloat())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Max() {
        run {
            val v1 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            val v2 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f32x4Max(v1V128, v2V128)
            var v3 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            v3 = transmuteWithLayouts<V128, FloatArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmMaxnumf32(v1[i], v2[i].toFloat())
                check(actual == expected)
            }
            val v11 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            val v21 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f32x4Max(v1V1281, v2V1281)
            var v31 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            v31 = transmuteWithLayouts<V128, FloatArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmMaxnumf32(v11[i1], v21[i1].toFloat())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Pmin() {
        run {
            val v1 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            val v2 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f32x4Pmin(v1V128, v2V128)
            var v3 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            v3 = transmuteWithLayouts<V128, FloatArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmMinnumf32(v1[i], v2[i].toFloat())
                check(actual == expected)
            }
            val v11 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            val v21 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f32x4Pmin(v1V1281, v2V1281)
            var v31 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            v31 = transmuteWithLayouts<V128, FloatArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmMinnumf32(v11[i1], v21[i1].toFloat())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Pmax() {
        run {
            val v1 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            val v2 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f32x4Pmax(v1V128, v2V128)
            var v3 = floatArrayOf(-1.0f, 2.0f, 3.0f, 4.0f)
            v3 = transmuteWithLayouts<V128, FloatArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmMaxnumf32(v1[i], v2[i].toFloat())
                check(actual == expected)
            }
            val v11 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            val v21 = listOf(1.0, 2.0, 0.0, 0.0)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f32x4Pmax(v1V1281, v2V1281)
            var v31 = floatArrayOf(Float.POSITIVE_INFINITY, -0.0f, Float.NEGATIVE_INFINITY, 3.0f)
            v31 = transmuteWithLayouts<V128, FloatArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmMaxnumf32(v11[i1], v21[i1].toFloat())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Add() {
        run {
            val v1 = doubleArrayOf(-1.0, 2.0)
            val v2 = listOf(1.0, 2.0)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f64x2Add(v1V128, v2V128)
            var v3 = doubleArrayOf(-1.0, 2.0)
            v3 = transmuteWithLayouts<V128, DoubleArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].add(v2[i])
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            val v21 = listOf(1.0, 2.0)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f64x2Add(v1V1281, v2V1281)
            var v31 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            v31 = transmuteWithLayouts<V128, DoubleArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].add(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Sub() {
        run {
            val v1 = doubleArrayOf(-1.0, 2.0)
            val v2 = listOf(1.0, 2.0)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f64x2Sub(v1V128, v2V128)
            var v3 = doubleArrayOf(-1.0, 2.0)
            v3 = transmuteWithLayouts<V128, DoubleArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].sub(v2[i])
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            val v21 = listOf(1.0, 2.0)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f64x2Sub(v1V1281, v2V1281)
            var v31 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            v31 = transmuteWithLayouts<V128, DoubleArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].sub(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Mul() {
        run {
            val v1 = doubleArrayOf(-1.0, 2.0)
            val v2 = listOf(1.0, 2.0)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f64x2Mul(v1V128, v2V128)
            var v3 = doubleArrayOf(-1.0, 2.0)
            v3 = transmuteWithLayouts<V128, DoubleArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].mul(v2[i])
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            val v21 = listOf(1.0, 2.0)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f64x2Mul(v1V1281, v2V1281)
            var v31 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            v31 = transmuteWithLayouts<V128, DoubleArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].mul(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Div() {
        run {
            val v1 = doubleArrayOf(-1.0, 2.0)
            val v2 = listOf(1.0, 2.0)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f64x2Div(v1V128, v2V128)
            var v3 = doubleArrayOf(-1.0, 2.0)
            v3 = transmuteWithLayouts<V128, DoubleArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].div(v2[i])
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            val v21 = listOf(1.0, 2.0)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f64x2Div(v1V1281, v2V1281)
            var v31 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            v31 = transmuteWithLayouts<V128, DoubleArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].div(v21[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Min() {
        run {
            val v1 = doubleArrayOf(-1.0, 2.0)
            val v2 = listOf(1.0, 2.0)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f64x2Min(v1V128, v2V128)
            var v3 = doubleArrayOf(-1.0, 2.0)
            v3 = transmuteWithLayouts<V128, DoubleArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmMinnumf64(v1[i], v2[i].toDouble())
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            val v21 = listOf(1.0, 2.0)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f64x2Min(v1V1281, v2V1281)
            var v31 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            v31 = transmuteWithLayouts<V128, DoubleArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmMinnumf64(v11[i1], v21[i1].toDouble())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Max() {
        run {
            val v1 = doubleArrayOf(-1.0, 2.0)
            val v2 = listOf(1.0, 2.0)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f64x2Max(v1V128, v2V128)
            var v3 = doubleArrayOf(-1.0, 2.0)
            v3 = transmuteWithLayouts<V128, DoubleArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmMaxnumf64(v1[i], v2[i].toDouble())
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            val v21 = listOf(1.0, 2.0)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f64x2Max(v1V1281, v2V1281)
            var v31 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            v31 = transmuteWithLayouts<V128, DoubleArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmMaxnumf64(v11[i1], v21[i1].toDouble())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Pmin() {
        run {
            val v1 = doubleArrayOf(-1.0, 2.0)
            val v2 = listOf(1.0, 2.0)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f64x2Pmin(v1V128, v2V128)
            var v3 = doubleArrayOf(-1.0, 2.0)
            v3 = transmuteWithLayouts<V128, DoubleArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmMinnumf64(v1[i], v2[i].toDouble())
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            val v21 = listOf(1.0, 2.0)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f64x2Pmin(v1V1281, v2V1281)
            var v31 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            v31 = transmuteWithLayouts<V128, DoubleArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmMinnumf64(v11[i1], v21[i1].toDouble())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Pmax() {
        run {
            val v1 = doubleArrayOf(-1.0, 2.0)
            val v2 = listOf(1.0, 2.0)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128: V128 = transmuteWithLayouts<Any?, V128>(v2, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V128 = f64x2Pmax(v1V128, v2V128)
            var v3 = doubleArrayOf(-1.0, 2.0)
            v3 = transmuteWithLayouts<V128, DoubleArray>(v3V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v3.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmMaxnumf64(v1[i], v2[i].toDouble())
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            val v21 = listOf(1.0, 2.0)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281: V128 = transmuteWithLayouts<Any?, V128>(v21, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val v3V1281 = f64x2Pmax(v1V1281, v2V1281)
            var v31 = doubleArrayOf(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)
            v31 = transmuteWithLayouts<V128, DoubleArray>(v3V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v31.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmMaxnumf64(v11[i1], v21[i1].toDouble())
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16Abs() {
        run {
            val v1 = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ByteArray, V128>(v1, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = i8x16Abs(v1V128)
            var v2 = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
            v2 = transmuteWithLayouts<V128, ByteArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].wrappingAbs()
                check(actual == expected)
            }
            val v11 = byteArrayOf((-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte(), (-16).toByte(), (-18).toByte())
            val v1V1281: V128 = transmuteWithLayouts<ByteArray, V128>(v11, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = i8x16Abs(v1V1281)
            var v21 = byteArrayOf((-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte(), (-16).toByte(), (-18).toByte())
            v21 = transmuteWithLayouts<V128, ByteArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].wrappingAbs()
                check(actual1 == expected1)
            }
            val v12 = byteArrayOf((-127).toByte(), (-44).toByte(), 43, 126, 4, (-128).toByte(), 127, (-59).toByte(), (-43).toByte(), 39, (-69).toByte(), 79, (-3).toByte(), 35, 83, 13)
            val v1V1282: V128 = transmuteWithLayouts<ByteArray, V128>(v12, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282 = i8x16Abs(v1V1282)
            var v22 = byteArrayOf((-127).toByte(), (-44).toByte(), 43, 126, 4, (-128).toByte(), 127, (-59).toByte(), (-43).toByte(), 39, (-69).toByte(), 79, (-3).toByte(), 35, 83, 13)
            v22 = transmuteWithLayouts<V128, ByteArray>(v2V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i2, actual2) in v22.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].wrappingAbs()
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI8x16Neg() {
        run {
            val v1 = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ByteArray, V128>(v1, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = i8x16Neg(v1V128)
            var v2 = byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
            v2 = transmuteWithLayouts<V128, ByteArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].wrappingNeg()
                check(actual == expected)
            }
            val v11 = byteArrayOf((-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte(), (-16).toByte(), (-18).toByte())
            val v1V1281: V128 = transmuteWithLayouts<ByteArray, V128>(v11, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = i8x16Neg(v1V1281)
            var v21 = byteArrayOf((-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte(), (-16).toByte(), (-18).toByte())
            v21 = transmuteWithLayouts<V128, ByteArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].wrappingNeg()
                check(actual1 == expected1)
            }
            val v12 = byteArrayOf((-127).toByte(), (-44).toByte(), 43, 126, 4, (-128).toByte(), 127, (-59).toByte(), (-43).toByte(), 39, (-69).toByte(), 79, (-3).toByte(), 35, 83, 13)
            val v1V1282: V128 = transmuteWithLayouts<ByteArray, V128>(v12, UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val v2V1282 = i8x16Neg(v1V1282)
            var v22 = byteArrayOf((-127).toByte(), (-44).toByte(), 43, 126, 4, (-128).toByte(), 127, (-59).toByte(), (-43).toByte(), 39, (-69).toByte(), 79, (-3).toByte(), 35, 83, 13)
            v22 = transmuteWithLayouts<V128, ByteArray>(v2V1282, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            for ((i2, actual2) in v22.mapIndexed { index, item -> Pair(index, item) }) {
                val expected2 = v12[i2].wrappingNeg()
                check(actual2 == expected2)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8Abs() {
        run {
            val v1 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ShortArray, V128>(v1, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = i16x8Abs(v1V128)
            var v2 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            v2 = transmuteWithLayouts<V128, ShortArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].wrappingAbs()
                check(actual == expected)
            }
            val v11 = shortArrayOf(2, 0x7fff, (0.inv()).toShort(), 4, 42, (-5).toShort(), 33, (-4847).toShort())
            val v1V1281: V128 = transmuteWithLayouts<ShortArray, V128>(v11, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = i16x8Abs(v1V1281)
            var v21 = shortArrayOf(2, 0x7fff, (0.inv()).toShort(), 4, 42, (-5).toShort(), 33, (-4847).toShort())
            v21 = transmuteWithLayouts<V128, ShortArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].wrappingAbs()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI16x8Neg() {
        run {
            val v1 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            val v1V128: V128 = transmuteWithLayouts<ShortArray, V128>(v1, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = i16x8Neg(v1V128)
            var v2 = shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1)
            v2 = transmuteWithLayouts<V128, ShortArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].wrappingNeg()
                check(actual == expected)
            }
            val v11 = shortArrayOf(2, 0x7fff, (0.inv()).toShort(), 4, 42, (-5).toShort(), 33, (-4847).toShort())
            val v1V1281: V128 = transmuteWithLayouts<ShortArray, V128>(v11, UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = i16x8Neg(v1V1281)
            var v21 = shortArrayOf(2, 0x7fff, (0.inv()).toShort(), 4, 42, (-5).toShort(), 33, (-4847).toShort())
            v21 = transmuteWithLayouts<V128, ShortArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].wrappingNeg()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI32x4Abs() {
        run {
            val v1 = intArrayOf(1, 2, 3, 4)
            val v1V128: V128 = transmuteWithLayouts<IntArray, V128>(v1, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v2V128 = i32x4Abs(v1V128)
            var v2 = intArrayOf(1, 2, 3, 4)
            v2 = transmuteWithLayouts<V128, IntArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].wrappingAbs()
                check(actual == expected)
            }
            val v11 = intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 0, 4)
            val v1V1281: V128 = transmuteWithLayouts<IntArray, V128>(v11, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = i32x4Abs(v1V1281)
            var v21 = intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 0, 4)
            v21 = transmuteWithLayouts<V128, IntArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].wrappingAbs()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI32x4Neg() {
        run {
            val v1 = intArrayOf(1, 2, 3, 4)
            val v1V128: V128 = transmuteWithLayouts<IntArray, V128>(v1, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val v2V128 = i32x4Neg(v1V128)
            var v2 = intArrayOf(1, 2, 3, 4)
            v2 = transmuteWithLayouts<V128, IntArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].wrappingNeg()
                check(actual == expected)
            }
            val v11 = intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 0, 4)
            val v1V1281: V128 = transmuteWithLayouts<IntArray, V128>(v11, UnresolvedTypeLayout<IntArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = i32x4Neg(v1V1281)
            var v21 = intArrayOf(Int.MIN_VALUE, Int.MAX_VALUE, 0, 4)
            v21 = transmuteWithLayouts<V128, IntArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].wrappingNeg()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI64x2Abs() {
        run {
            val v1 = longArrayOf(1L, 2L)
            val v1V128: V128 = transmuteWithLayouts<LongArray, V128>(v1, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = i64x2Abs(v1V128)
            var v2 = longArrayOf(1L, 2L)
            v2 = transmuteWithLayouts<V128, LongArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].wrappingAbs()
                check(actual == expected)
            }
            val v11 = longArrayOf(Long.MIN_VALUE, Long.MAX_VALUE)
            val v1V1281: V128 = transmuteWithLayouts<LongArray, V128>(v11, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = i64x2Abs(v1V1281)
            var v21 = longArrayOf(Long.MIN_VALUE, Long.MAX_VALUE)
            v21 = transmuteWithLayouts<V128, LongArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].wrappingAbs()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testI64x2Neg() {
        run {
            val v1 = longArrayOf(1L, 2L)
            val v1V128: V128 = transmuteWithLayouts<LongArray, V128>(v1, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = i64x2Neg(v1V128)
            var v2 = longArrayOf(1L, 2L)
            v2 = transmuteWithLayouts<V128, LongArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].wrappingNeg()
                check(actual == expected)
            }
            val v11 = longArrayOf(Long.MIN_VALUE, Long.MAX_VALUE)
            val v1V1281: V128 = transmuteWithLayouts<LongArray, V128>(v11, UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = i64x2Neg(v1V1281)
            var v21 = longArrayOf(Long.MIN_VALUE, Long.MAX_VALUE)
            v21 = transmuteWithLayouts<V128, LongArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].wrappingNeg()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Ceil() {
        run {
            val v1 = floatArrayOf(1.0f, 2.0f, 2.5f, 3.3f)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f32x4Ceil(v1V128)
            var v2 = floatArrayOf(1.0f, 2.0f, 2.5f, 3.3f)
            v2 = transmuteWithLayouts<V128, FloatArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmCeilf32(v1[i])
                check(actual == expected)
            }
            val v11 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f32x4Ceil(v1V1281)
            var v21 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            v21 = transmuteWithLayouts<V128, FloatArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmCeilf32(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Floor() {
        run {
            val v1 = floatArrayOf(1.0f, 2.0f, 2.5f, 3.3f)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f32x4Floor(v1V128)
            var v2 = floatArrayOf(1.0f, 2.0f, 2.5f, 3.3f)
            v2 = transmuteWithLayouts<V128, FloatArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmFloorf32(v1[i])
                check(actual == expected)
            }
            val v11 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f32x4Floor(v1V1281)
            var v21 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            v21 = transmuteWithLayouts<V128, FloatArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmFloorf32(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Trunc() {
        run {
            val v1 = floatArrayOf(1.0f, 2.0f, 2.5f, 3.3f)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f32x4Trunc(v1V128)
            var v2 = floatArrayOf(1.0f, 2.0f, 2.5f, 3.3f)
            v2 = transmuteWithLayouts<V128, FloatArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmTruncf32(v1[i])
                check(actual == expected)
            }
            val v11 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f32x4Trunc(v1V1281)
            var v21 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            v21 = transmuteWithLayouts<V128, FloatArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmTruncf32(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Nearest() {
        run {
            val v1 = floatArrayOf(1.0f, 2.0f, 2.6f, 3.3f)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f32x4Nearest(v1V128)
            var v2 = floatArrayOf(1.0f, 2.0f, 2.6f, 3.3f)
            v2 = transmuteWithLayouts<V128, FloatArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmRoundf32(v1[i])
                check(actual == expected)
            }
            val v11 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f32x4Nearest(v1V1281)
            var v21 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            v21 = transmuteWithLayouts<V128, FloatArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmRoundf32(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Abs() {
        run {
            val v1 = floatArrayOf(1.0f, 2.0f, 2.6f, 3.3f)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f32x4Abs(v1V128)
            var v2 = floatArrayOf(1.0f, 2.0f, 2.6f, 3.3f)
            v2 = transmuteWithLayouts<V128, FloatArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmFabsf32(v1[i])
                check(actual == expected)
            }
            val v11 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f32x4Abs(v1V1281)
            var v21 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            v21 = transmuteWithLayouts<V128, FloatArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmFabsf32(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Neg() {
        run {
            val v1 = floatArrayOf(1.0f, 2.0f, 2.6f, 3.3f)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f32x4Neg(v1V128)
            var v2 = floatArrayOf(1.0f, 2.0f, 2.6f, 3.3f)
            v2 = transmuteWithLayouts<V128, FloatArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].neg()
                check(actual == expected)
            }
            val v11 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f32x4Neg(v1V1281)
            var v21 = floatArrayOf(0.0f, -0.3f, Float.POSITIVE_INFINITY, -0.0f)
            v21 = transmuteWithLayouts<V128, FloatArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].neg()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF32x4Sqrt() {
        run {
            val v1 = floatArrayOf(1.0f, 2.0f, 2.6f, 3.3f)
            val v1V128: V128 = transmuteWithLayouts<FloatArray, V128>(v1, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f32x4Sqrt(v1V128)
            var v2 = floatArrayOf(1.0f, 2.0f, 2.6f, 3.3f)
            v2 = transmuteWithLayouts<V128, FloatArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmSqrtf32(v1[i])
                check(actual == expected)
            }
            val v11 = floatArrayOf(0.0f, 0.3f, Float.POSITIVE_INFINITY, 0.1f)
            val v1V1281: V128 = transmuteWithLayouts<FloatArray, V128>(v11, UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f32x4Sqrt(v1V1281)
            var v21 = floatArrayOf(0.0f, 0.3f, Float.POSITIVE_INFINITY, 0.1f)
            v21 = transmuteWithLayouts<V128, FloatArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmSqrtf32(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Ceil() {
        run {
            val v1 = doubleArrayOf(1.0, 2.3)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f64x2Ceil(v1V128)
            var v2 = doubleArrayOf(1.0, 2.3)
            v2 = transmuteWithLayouts<V128, DoubleArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmCeilf64(v1[i])
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f64x2Ceil(v1V1281)
            var v21 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            v21 = transmuteWithLayouts<V128, DoubleArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmCeilf64(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Floor() {
        run {
            val v1 = doubleArrayOf(1.0, 2.3)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f64x2Floor(v1V128)
            var v2 = doubleArrayOf(1.0, 2.3)
            v2 = transmuteWithLayouts<V128, DoubleArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmFloorf64(v1[i])
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f64x2Floor(v1V1281)
            var v21 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            v21 = transmuteWithLayouts<V128, DoubleArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmFloorf64(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Trunc() {
        run {
            val v1 = doubleArrayOf(1.0, 2.3)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f64x2Trunc(v1V128)
            var v2 = doubleArrayOf(1.0, 2.3)
            v2 = transmuteWithLayouts<V128, DoubleArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmTruncf64(v1[i])
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f64x2Trunc(v1V1281)
            var v21 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            v21 = transmuteWithLayouts<V128, DoubleArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmTruncf64(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Nearest() {
        run {
            val v1 = doubleArrayOf(1.0, 2.3)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f64x2Nearest(v1V128)
            var v2 = doubleArrayOf(1.0, 2.3)
            v2 = transmuteWithLayouts<V128, DoubleArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmRoundf64(v1[i])
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f64x2Nearest(v1V1281)
            var v21 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            v21 = transmuteWithLayouts<V128, DoubleArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmRoundf64(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Abs() {
        run {
            val v1 = doubleArrayOf(1.0, 2.3)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f64x2Abs(v1V128)
            var v2 = doubleArrayOf(1.0, 2.3)
            v2 = transmuteWithLayouts<V128, DoubleArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmFabsf64(v1[i])
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f64x2Abs(v1V1281)
            var v21 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            v21 = transmuteWithLayouts<V128, DoubleArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmFabsf64(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Neg() {
        run {
            val v1 = doubleArrayOf(1.0, 2.3)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f64x2Neg(v1V128)
            var v2 = doubleArrayOf(1.0, 2.3)
            v2 = transmuteWithLayouts<V128, DoubleArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = v1[i].neg()
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f64x2Neg(v1V1281)
            var v21 = doubleArrayOf(Double.POSITIVE_INFINITY, -0.1)
            v21 = transmuteWithLayouts<V128, DoubleArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = v11[i1].neg()
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun testF64x2Sqrt() {
        run {
            val v1 = doubleArrayOf(1.0, 2.3)
            val v1V128: V128 = transmuteWithLayouts<DoubleArray, V128>(v1, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V128 = f64x2Sqrt(v1V128)
            var v2 = doubleArrayOf(1.0, 2.3)
            v2 = transmuteWithLayouts<V128, DoubleArray>(v2V128, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i, actual) in v2.mapIndexed { index, item -> Pair(index, item) }) {
                val expected = kmSqrtf64(v1[i])
                check(actual == expected)
            }
            val v11 = doubleArrayOf(Double.POSITIVE_INFINITY, 0.1)
            val v1V1281: V128 = transmuteWithLayouts<DoubleArray, V128>(v11, UnresolvedTypeLayout<DoubleArray>(), UnresolvedTypeLayout<V128>())
            val v2V1281 = f64x2Sqrt(v1V1281)
            var v21 = doubleArrayOf(Double.POSITIVE_INFINITY, 0.1)
            v21 = transmuteWithLayouts<V128, DoubleArray>(v2V1281, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            for ((i1, actual1) in v21.mapIndexed { index, item -> Pair(index, item) }) {
                val expected1 = kmSqrtf64(v11[i1])
                check(actual1 == expected1)
            }
        }
    }

    @kotlin.test.Test
    public fun i8x16ShlTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, (-1).toByte(), 2, 3, 4, 5, 6, Byte.MAX_VALUE, 1, 1, 1, 1, 1, 1, 1, 1)
            val output: ByteArray = byteArrayOf(0, (-2).toByte(), 4, 6, 8, 10, 12, (-2).toByte(), 2, 2, 2, 2, 2, 2, 2, 2)
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i8x16Shl(aVecIn, 1u)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i16x8ShlTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, (-1).toShort(), 2, 3, 4, 5, 6, Short.MAX_VALUE)
            val output: ShortArray = shortArrayOf(0, (-2).toShort(), 4, 6, 8, 10, 12, (-2).toShort())
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i16x8Shl(aVecIn, 1u)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i32x4ShlTest() {
        run {
            val aInput: IntArray = intArrayOf(0, -1, 2, 3)
            val output: IntArray = intArrayOf(0, -2, 4, 6)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i32x4Shl(aVecIn, 1u)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i64x2ShlTest() {
        run {
            val aInput: LongArray = longArrayOf(0L, -1L)
            val output: LongArray = longArrayOf(0L, -2L)
            val aVecIn: V128 = transmuteWithLayouts<LongArray, V128>(aInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i64x2Shl(aVecIn, 1u)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i8x16ShrSTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, (-1).toByte(), 2, 3, 4, 5, 6, Byte.MAX_VALUE, 1, 1, 1, 1, 1, 1, 1, 1)
            val output: ByteArray = byteArrayOf(0, (-1).toByte(), 1, 1, 2, 2, 3, 63, 0, 0, 0, 0, 0, 0, 0, 0)
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i8x16Shr(aVecIn, 1u)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i16x8ShrSTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, (-1).toShort(), 2, 3, 4, 5, 6, Short.MAX_VALUE)
            val output: ShortArray = shortArrayOf(0, (-1).toShort(), 1, 1, 2, 2, 3, (Short.MAX_VALUE / 2).toShort())
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i16x8Shr(aVecIn, 1u)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i32x4ShrSTest() {
        run {
            val aInput: IntArray = intArrayOf(0, -1, 2, 3)
            val output: IntArray = intArrayOf(0, -1, 1, 1)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i32x4Shr(aVecIn, 1u)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i64x2ShrSTest() {
        run {
            val aInput: LongArray = longArrayOf(0L, -1L)
            val output: LongArray = longArrayOf(0L, -1L)
            val aVecIn: V128 = transmuteWithLayouts<LongArray, V128>(aInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i64x2Shr(aVecIn, 1u)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i8x16UhrUTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, (-1).toByte(), 2, 3, 4, 5, 6, Byte.MAX_VALUE, 1, 1, 1, 1, 1, 1, 1, 1)
            val output: ByteArray = byteArrayOf(0, Byte.MAX_VALUE, 1, 1, 2, 2, 3, 63, 0, 0, 0, 0, 0, 0, 0, 0)
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u8x16Shr(aVecIn, 1u)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i16x8UhrUTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, (-1).toShort(), 2, 3, 4, 5, 6, Short.MAX_VALUE)
            val output: ShortArray = shortArrayOf(0, Short.MAX_VALUE, 1, 1, 2, 2, 3, (Short.MAX_VALUE / 2).toShort())
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u16x8Shr(aVecIn, 1u)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i32x4UhrUTest() {
        run {
            val aInput: IntArray = intArrayOf(0, -1, 2, 3)
            val output: IntArray = intArrayOf(0, Int.MAX_VALUE, 1, 1)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u32x4Shr(aVecIn, 1u)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun i64x2UhrUTest() {
        run {
            val aInput: LongArray = longArrayOf(0L, -1L)
            val output: LongArray = longArrayOf(0L, Long.MAX_VALUE)
            val aVecIn: V128 = transmuteWithLayouts<LongArray, V128>(aInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u64x2Shr(aVecIn, 1u)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun v128BitwiseLogicalOps() {
        run {
            val a: UIntArray = uintArrayOf(UInt.MAX_VALUE, 0u, UInt.MAX_VALUE, 0u)
            val b: UIntArray = UIntArray(4) { UInt.MAX_VALUE }
            val c: UIntArray = UIntArray(4) { 0u }
            val vecA: V128 = transmuteWithLayouts<UIntArray, V128>(a, PrimitiveArrayLayouts.uIntArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecB: V128 = transmuteWithLayouts<UIntArray, V128>(b, PrimitiveArrayLayouts.uIntArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecC: V128 = transmuteWithLayouts<UIntArray, V128>(c, PrimitiveArrayLayouts.uIntArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val r: V128 = v128And(vecA, vecA)
            compareBytes(r, vecA)
            val r1: V128 = v128And(vecA, vecB)
            compareBytes(r1, vecA)
            val r2: V128 = v128Andnot(vecA, vecB)
            compareBytes(r2, vecC)
            val r3: V128 = v128Andnot(vecA, vecA)
            compareBytes(r3, vecC)
            val r4: V128 = v128Andnot(vecA, vecC)
            compareBytes(r4, vecA)
            val r5: V128 = v128Or(vecA, vecB)
            compareBytes(r5, vecB)
            val r6: V128 = v128Not(vecB)
            compareBytes(r6, vecC)
            val r7: V128 = v128Xor(vecA, vecC)
            compareBytes(r7, vecA)
            val r8: V128 = v128Bitselect(vecB, vecC, vecB)
            compareBytes(r8, vecB)
            val r9: V128 = v128Bitselect(vecB, vecC, vecC)
            compareBytes(r9, vecC)
            val r10: V128 = v128Bitselect(vecB, vecC, vecA)
            compareBytes(r10, vecA)
        }
    }

    @kotlin.test.Test
    public fun i8x16BooleanReductions() {
        run {
            val vecA: V128 = transmuteWithLayouts<ByteArray, V128>(byteArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val vecB: V128 = transmuteWithLayouts<ByteArray, V128>(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            val vecC: V128 = transmuteWithLayouts<ByteArray, V128>(byteArrayOf(1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0), UnresolvedTypeLayout<ByteArray>(), UnresolvedTypeLayout<V128>())
            check(i8x16AllTrue(vecA) == true)
            check(i8x16AllTrue(vecB) == false)
            check(i8x16AllTrue(vecC) == false)
        }
    }

    @kotlin.test.Test
    public fun i16x8BooleanReductions() {
        run {
            val vecA: V128 = transmuteWithLayouts<ShortArray, V128>(shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1), UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val vecB: V128 = transmuteWithLayouts<ShortArray, V128>(shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0), UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            val vecC: V128 = transmuteWithLayouts<ShortArray, V128>(shortArrayOf(1, 0, 1, 0, 1, 0, 1, 0), UnresolvedTypeLayout<ShortArray>(), UnresolvedTypeLayout<V128>())
            check(i16x8AllTrue(vecA) == true)
            check(i16x8AllTrue(vecB) == false)
            check(i16x8AllTrue(vecC) == false)
        }
    }

    @kotlin.test.Test
    public fun i32x4BooleanReductions() {
        run {
            val vecA: V128 = transmuteWithLayouts<IntArray, V128>(intArrayOf(1, 1, 1, 1), PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecB: V128 = transmuteWithLayouts<IntArray, V128>(intArrayOf(0, 0, 0, 0), PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecC: V128 = transmuteWithLayouts<IntArray, V128>(intArrayOf(1, 0, 1, 0), PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            check(i32x4AllTrue(vecA) == true)
            check(i32x4AllTrue(vecB) == false)
            check(i32x4AllTrue(vecC) == false)
        }
    }

    @kotlin.test.Test
    public fun i64x2BooleanReductions() {
        run {
            val vecA: V128 = transmuteWithLayouts<LongArray, V128>(longArrayOf(1L, 1L), UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val vecB: V128 = transmuteWithLayouts<LongArray, V128>(longArrayOf(0L, 0L), UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            val vecC: V128 = transmuteWithLayouts<LongArray, V128>(longArrayOf(1L, 0L), UnresolvedTypeLayout<LongArray>(), UnresolvedTypeLayout<V128>())
            check(i64x2AllTrue(vecA) == true)
            check(i64x2AllTrue(vecB) == false)
            check(i64x2AllTrue(vecC) == false)
        }
    }

    @kotlin.test.Test
    public fun i8x16EqTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
            val bInput: ByteArray = byteArrayOf(0, 2, 2, 4, 4, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, 15)
            val output: ByteArray = byteArrayOf((-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), (-1).toByte())
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(bInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i8x16Eq(aVecIn, bVecIn)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 16) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i16x8EqTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
            val bInput: ShortArray = shortArrayOf(0, 2, 2, 4, 4, 6, 6, 7)
            val output: ShortArray = shortArrayOf((-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), (-1).toShort())
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(bInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i16x8Eq(aVecIn, bVecIn)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 8) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i32x4EqTest() {
        run {
            val aInput: IntArray = intArrayOf(0, 1, 2, 3)
            val bInput: IntArray = intArrayOf(0, 2, 2, 4)
            val output: IntArray = intArrayOf(-1, 0, -1, 0)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<IntArray, V128>(bInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i32x4Eq(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i64x2EqTest() {
        run {
            val aInput: LongArray = longArrayOf(0L, 1L)
            val bInput: LongArray = longArrayOf(0L, 2L)
            val output: LongArray = longArrayOf(-1L, 0L)
            val aVecIn: V128 = transmuteWithLayouts<LongArray, V128>(aInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<LongArray, V128>(bInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i64x2Eq(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4EqTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f)
            val bInput: FloatArray = floatArrayOf(0.0f, 2.0f, 2.0f, 4.0f)
            val output: IntArray = intArrayOf(-1, 0, -1, 0)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Eq(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2EqTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, 1.0)
            val bInput: DoubleArray = doubleArrayOf(0.0, 2.0)
            val output: LongArray = longArrayOf(-1L, 0L)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Eq(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i8x16NeTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
            val bInput: ByteArray = byteArrayOf(0, 2, 2, 4, 4, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, 15)
            val output: ByteArray = byteArrayOf(0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, 0, 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, 0)
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(bInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i8x16Ne(aVecIn, bVecIn)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 16) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i16x8NeTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
            val bInput: ShortArray = shortArrayOf(0, 2, 2, 4, 4, 6, 6, 7)
            val output: ShortArray = shortArrayOf(0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, 0)
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(bInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i16x8Ne(aVecIn, bVecIn)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 8) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i32x4NeTest() {
        run {
            val aInput: IntArray = intArrayOf(0, 1, 2, 3)
            val bInput: IntArray = intArrayOf(0, 2, 2, 4)
            val output: IntArray = intArrayOf(0, -1, 0, -1)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<IntArray, V128>(bInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i32x4Ne(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i64x2NeTest() {
        run {
            val aInput: LongArray = longArrayOf(0L, 1L)
            val bInput: LongArray = longArrayOf(0L, 2L)
            val output: LongArray = longArrayOf(0L, -1L)
            val aVecIn: V128 = transmuteWithLayouts<LongArray, V128>(aInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<LongArray, V128>(bInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i64x2Ne(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4NeTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f)
            val bInput: FloatArray = floatArrayOf(0.0f, 2.0f, 2.0f, 4.0f)
            val output: IntArray = intArrayOf(0, -1, 0, -1)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Ne(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2NeTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, 1.0)
            val bInput: DoubleArray = doubleArrayOf(0.0, 2.0)
            val output: LongArray = longArrayOf(0L, -1L)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Ne(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i8x16LtSTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, (-12).toByte(), 13, 14, 15)
            val bInput: ByteArray = byteArrayOf(0, 2, 2, 4, 4, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, 15)
            val output: ByteArray = byteArrayOf(0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, 0, 0, (-1).toByte(), 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), 0, 0)
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(bInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i8x16Lt(aVecIn, bVecIn)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 16) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i8x16LtUTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, (-12).toByte(), 13, 14, 15)
            val bInput: ByteArray = byteArrayOf(0, 2, 2, 4, 4, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, 15)
            val output: ByteArray = byteArrayOf(0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, 0, 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, 0)
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(bInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u8x16Lt(aVecIn, bVecIn)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 16) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i16x8LtSTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, (-7).toShort())
            val bInput: ShortArray = shortArrayOf(0, 2, 2, 4, 4, 6, 6, 7)
            val output: ShortArray = shortArrayOf(0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort())
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(bInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i16x8Lt(aVecIn, bVecIn)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 8) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i16x8LtUTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, (-7).toShort())
            val bInput: ShortArray = shortArrayOf(0, 2, 2, 4, 4, 6, 6, 7)
            val output: ShortArray = shortArrayOf(0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, 0)
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(bInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u16x8Lt(aVecIn, bVecIn)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 8) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i32x4LtSTest() {
        run {
            val aInput: IntArray = intArrayOf(-1, 1, 2, 3)
            val bInput: IntArray = intArrayOf(0, 2, 2, 4)
            val output: IntArray = intArrayOf(-1, -1, 0, -1)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<IntArray, V128>(bInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i32x4Lt(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i32x4LtUTest() {
        run {
            val aInput: IntArray = intArrayOf(-1, 1, 2, 3)
            val bInput: IntArray = intArrayOf(0, 2, 2, 4)
            val output: IntArray = intArrayOf(0, -1, 0, -1)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<IntArray, V128>(bInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u32x4Lt(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i64x2LtSTest() {
        run {
            val aInput: LongArray = longArrayOf(-1L, 3L)
            val bInput: LongArray = longArrayOf(0L, 2L)
            val output: LongArray = longArrayOf(-1L, 0L)
            val aVecIn: V128 = transmuteWithLayouts<LongArray, V128>(aInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<LongArray, V128>(bInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i64x2Lt(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4LtTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f)
            val bInput: FloatArray = floatArrayOf(0.0f, 2.0f, 2.0f, 4.0f)
            val output: IntArray = intArrayOf(0, -1, 0, -1)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Lt(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2LtTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, 1.0)
            val bInput: DoubleArray = doubleArrayOf(0.0, 2.0)
            val output: LongArray = longArrayOf(0L, -1L)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Lt(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i8x16GtSTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, 2, 2, 4, 4, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, (-15).toByte())
            val bInput: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
            val output: ByteArray = byteArrayOf(0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, 0, 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, 0)
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(bInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i8x16Gt(aVecIn, bVecIn)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 16) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i8x16GtUTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, 2, 2, 4, 4, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, (-15).toByte())
            val bInput: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
            val output: ByteArray = byteArrayOf(0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, 0, 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte())
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(bInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u8x16Gt(aVecIn, bVecIn)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 16) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i16x8GtSTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, 2, 2, 4, 4, 6, 6, (-7).toShort())
            val bInput: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
            val output: ShortArray = shortArrayOf(0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, 0)
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(bInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i16x8Gt(aVecIn, bVecIn)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 8) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i16x8GtUTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, 2, 2, 4, 4, 6, 6, (-7).toShort())
            val bInput: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
            val output: ShortArray = shortArrayOf(0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort())
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(bInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u16x8Gt(aVecIn, bVecIn)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 8) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i32x4GtSTest() {
        run {
            val aInput: IntArray = intArrayOf(0, 2, 2, -4)
            val bInput: IntArray = intArrayOf(0, 1, 2, 3)
            val output: IntArray = intArrayOf(0, -1, 0, 0)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<IntArray, V128>(bInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i32x4Gt(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i32x4GtUTest() {
        run {
            val aInput: IntArray = intArrayOf(0, 2, 2, -4)
            val bInput: IntArray = intArrayOf(0, 1, 2, 3)
            val output: IntArray = intArrayOf(0, -1, 0, -1)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<IntArray, V128>(bInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u32x4Gt(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i64x2GtSTest() {
        run {
            val aInput: LongArray = longArrayOf(-1L, 2L)
            val bInput: LongArray = longArrayOf(0L, 1L)
            val output: LongArray = longArrayOf(0L, -1L)
            val aVecIn: V128 = transmuteWithLayouts<LongArray, V128>(aInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<LongArray, V128>(bInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i64x2Gt(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4GtTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, 2.0f, 2.0f, 4.0f)
            val bInput: FloatArray = floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f)
            val output: IntArray = intArrayOf(0, -1, 0, -1)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Gt(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2GtTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, 2.0)
            val bInput: DoubleArray = doubleArrayOf(0.0, 1.0)
            val output: LongArray = longArrayOf(0L, -1L)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Gt(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i8x16GeSTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, (-15).toByte())
            val bInput: ByteArray = byteArrayOf(0, 2, 2, 4, 4, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, 15)
            val output: ByteArray = byteArrayOf((-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0)
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(bInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i8x16Ge(aVecIn, bVecIn)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 16) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i8x16GeUTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, (-15).toByte())
            val bInput: ByteArray = byteArrayOf(0, 2, 2, 4, 4, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, 15)
            val output: ByteArray = byteArrayOf((-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), (-1).toByte())
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(bInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u8x16Ge(aVecIn, bVecIn)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 16) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i16x8GeSTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, (-7).toShort())
            val bInput: ShortArray = shortArrayOf(0, 2, 2, 4, 4, 6, 6, 7)
            val output: ShortArray = shortArrayOf((-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0)
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(bInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i16x8Ge(aVecIn, bVecIn)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 8) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i16x8GeUTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, (-7).toShort())
            val bInput: ShortArray = shortArrayOf(0, 2, 2, 4, 4, 6, 6, 7)
            val output: ShortArray = shortArrayOf((-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), (-1).toShort())
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(bInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u16x8Ge(aVecIn, bVecIn)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 8) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i32x4GeSTest() {
        run {
            val aInput: IntArray = intArrayOf(0, 1, 2, -3)
            val bInput: IntArray = intArrayOf(0, 2, 2, 4)
            val output: IntArray = intArrayOf(-1, 0, -1, 0)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<IntArray, V128>(bInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i32x4Ge(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i32x4GeUTest() {
        run {
            val aInput: IntArray = intArrayOf(0, 1, 2, -3)
            val bInput: IntArray = intArrayOf(0, 2, 2, 4)
            val output: IntArray = intArrayOf(-1, 0, -1, -1)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<IntArray, V128>(bInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u32x4Ge(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i64x2GeSTest() {
        run {
            val aInput: LongArray = longArrayOf(0L, 1L)
            val bInput: LongArray = longArrayOf(-1L, 2L)
            val output: LongArray = longArrayOf(-1L, 0L)
            val aVecIn: V128 = transmuteWithLayouts<LongArray, V128>(aInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<LongArray, V128>(bInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i64x2Ge(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4GeTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f)
            val bInput: FloatArray = floatArrayOf(0.0f, 2.0f, 2.0f, 4.0f)
            val output: IntArray = intArrayOf(-1, 0, -1, 0)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Ge(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2GeTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, 1.0)
            val bInput: DoubleArray = doubleArrayOf(0.0, 2.0)
            val output: LongArray = longArrayOf(-1L, 0L)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Ge(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i8x16LeSTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, 2, 2, 4, 4, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, (-15).toByte())
            val bInput: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
            val output: ByteArray = byteArrayOf((-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), (-1).toByte())
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(bInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i8x16Le(aVecIn, bVecIn)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 16) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i8x16LeUTest() {
        run {
            val aInput: ByteArray = byteArrayOf(0, 2, 2, 4, 4, 6, 6, 7, 8, 10, 10, 12, 12, 14, 14, (-15).toByte())
            val bInput: ByteArray = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
            val output: ByteArray = byteArrayOf((-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), (-1).toByte(), (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0, (-1).toByte(), 0)
            val aVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(aInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ByteArray, V128>(bInput, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u8x16Le(aVecIn, bVecIn)
            val res: ByteArray = transmuteWithLayouts<V128, ByteArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ByteArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 16) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i16x8LeSTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, 2, 2, 4, 4, 6, 6, (-7).toShort())
            val bInput: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
            val output: ShortArray = shortArrayOf((-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), (-1).toShort())
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(bInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i16x8Le(aVecIn, bVecIn)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 8) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i16x8LeUTest() {
        run {
            val aInput: ShortArray = shortArrayOf(0, 2, 2, 4, 4, 6, 6, (-7).toShort())
            val bInput: ShortArray = shortArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
            val output: ShortArray = shortArrayOf((-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0, (-1).toShort(), 0)
            val aVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(aInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<ShortArray, V128>(bInput, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u16x8Le(aVecIn, bVecIn)
            val res: ShortArray = transmuteWithLayouts<V128, ShortArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<ShortArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 8) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i32x4LeSTest() {
        run {
            val aInput: IntArray = intArrayOf(0, 2, 2, -4)
            val bInput: IntArray = intArrayOf(0, 1, 2, 3)
            val output: IntArray = intArrayOf(-1, 0, -1, -1)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<IntArray, V128>(bInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i32x4Le(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i32x4LeUTest() {
        run {
            val aInput: IntArray = intArrayOf(0, 2, 2, -4)
            val bInput: IntArray = intArrayOf(0, 1, 2, 3)
            val output: IntArray = intArrayOf(-1, 0, -1, 0)
            val aVecIn: V128 = transmuteWithLayouts<IntArray, V128>(aInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<IntArray, V128>(bInput, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = u32x4Le(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun i64x2LeSTest() {
        run {
            val aInput: LongArray = longArrayOf(0L, 2L)
            val bInput: LongArray = longArrayOf(0L, 1L)
            val output: LongArray = longArrayOf(-1L, 0L)
            val aVecIn: V128 = transmuteWithLayouts<LongArray, V128>(aInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<LongArray, V128>(bInput, PrimitiveArrayLayouts.longArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = i64x2Le(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!false) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4LeTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, 2.0f, 2.0f, 4.0f)
            val bInput: FloatArray = floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f)
            val output: IntArray = intArrayOf(-1, 0, -1, -0)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Le(aVecIn, bVecIn)
            val res: IntArray = transmuteWithLayouts<V128, IntArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<IntArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2LeTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, 2.0)
            val bInput: DoubleArray = doubleArrayOf(0.0, 1.0)
            val output: LongArray = longArrayOf(-1L, 0L)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Le(aVecIn, bVecIn)
            val res: LongArray = transmuteWithLayouts<V128, LongArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<LongArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNan() == o.isNan())
                    if (!r.isNan()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4NegTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f)
            val output: FloatArray = floatArrayOf(0.0f, -1.0f, -2.0f, -3.0f)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Neg(aVecIn)
            val res: FloatArray = transmuteWithLayouts<V128, FloatArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun f32x4AbsTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, -1.0f, 2.0f, -3.0f)
            val output: FloatArray = floatArrayOf(0.0f, 1.0f, 2.0f, 3.0f)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Abs(aVecIn)
            val res: FloatArray = transmuteWithLayouts<V128, FloatArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun f32x4MinTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, -1.0f, 7.0f, 8.0f)
            val bInput: FloatArray = floatArrayOf(1.0f, -3.0f, -4.0f, 10.0f)
            val output: FloatArray = floatArrayOf(0.0f, -3.0f, -4.0f, 8.0f)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Min(aVecIn, bVecIn)
            val res: FloatArray = transmuteWithLayouts<V128, FloatArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4MinTestNan() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, -1.0f, 7.0f, 8.0f)
            val bInput: FloatArray = floatArrayOf(1.0f, -3.0f, -4.0f, Float.NaN)
            val output: FloatArray = floatArrayOf(0.0f, -3.0f, -4.0f, Float.NaN)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Min(aVecIn, bVecIn)
            val res: FloatArray = transmuteWithLayouts<V128, FloatArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4MaxTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, -1.0f, 7.0f, 8.0f)
            val bInput: FloatArray = floatArrayOf(1.0f, -3.0f, -4.0f, 10.0f)
            val output: FloatArray = floatArrayOf(1.0f, -1.0f, 7.0f, 10.0f)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Max(aVecIn, bVecIn)
            val res: FloatArray = transmuteWithLayouts<V128, FloatArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4MaxTestNan() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, -1.0f, 7.0f, 8.0f)
            val bInput: FloatArray = floatArrayOf(1.0f, -3.0f, -4.0f, Float.NaN)
            val output: FloatArray = floatArrayOf(1.0f, -1.0f, 7.0f, Float.NaN)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Max(aVecIn, bVecIn)
            val res: FloatArray = transmuteWithLayouts<V128, FloatArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4AddTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, -1.0f, 7.0f, 8.0f)
            val bInput: FloatArray = floatArrayOf(1.0f, -3.0f, -4.0f, 10.0f)
            val output: FloatArray = floatArrayOf(1.0f, -4.0f, 3.0f, 18.0f)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Add(aVecIn, bVecIn)
            val res: FloatArray = transmuteWithLayouts<V128, FloatArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4SubTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, -1.0f, 7.0f, 8.0f)
            val bInput: FloatArray = floatArrayOf(1.0f, -3.0f, -4.0f, 10.0f)
            val output: FloatArray = floatArrayOf(-1.0f, 2.0f, 11.0f, -2.0f)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Sub(aVecIn, bVecIn)
            val res: FloatArray = transmuteWithLayouts<V128, FloatArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4MulTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, -1.0f, 7.0f, 8.0f)
            val bInput: FloatArray = floatArrayOf(1.0f, -3.0f, -4.0f, 10.0f)
            val output: FloatArray = floatArrayOf(0.0f, 3.0f, -28.0f, 80.0f)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Mul(aVecIn, bVecIn)
            val res: FloatArray = transmuteWithLayouts<V128, FloatArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4DivTest() {
        run {
            val aInput: FloatArray = floatArrayOf(0.0f, -8.0f, 70.0f, 8.0f)
            val bInput: FloatArray = floatArrayOf(1.0f, 4.0f, 10.0f, 2.0f)
            val output: FloatArray = floatArrayOf(0.0f, -2.0f, 7.0f, 4.0f)
            val aVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(aInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<FloatArray, V128>(bInput, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f32x4Div(aVecIn, bVecIn)
            val res: FloatArray = transmuteWithLayouts<V128, FloatArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<FloatArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 4) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2NegTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, 1.0)
            val output: DoubleArray = doubleArrayOf(0.0, -1.0)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Neg(aVecIn)
            val res: DoubleArray = transmuteWithLayouts<V128, DoubleArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun f64x2AbsTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, -1.0)
            val output: DoubleArray = doubleArrayOf(0.0, 1.0)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Abs(aVecIn)
            val res: DoubleArray = transmuteWithLayouts<V128, DoubleArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            check((res).contentEquals(output))
        }
    }

    @kotlin.test.Test
    public fun f64x2MinTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, -1.0)
            val bInput: DoubleArray = doubleArrayOf(1.0, -3.0)
            val output: DoubleArray = doubleArrayOf(0.0, -3.0)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Min(aVecIn, bVecIn)
            val res: DoubleArray = transmuteWithLayouts<V128, DoubleArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2MinTestNan() {
        run {
            val aInput: DoubleArray = doubleArrayOf(7.0, 8.0)
            val bInput: DoubleArray = doubleArrayOf(-4.0, Double.NaN)
            val output: DoubleArray = doubleArrayOf(-4.0, Double.NaN)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Min(aVecIn, bVecIn)
            val res: DoubleArray = transmuteWithLayouts<V128, DoubleArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2MaxTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, -1.0)
            val bInput: DoubleArray = doubleArrayOf(1.0, -3.0)
            val output: DoubleArray = doubleArrayOf(1.0, -1.0)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Max(aVecIn, bVecIn)
            val res: DoubleArray = transmuteWithLayouts<V128, DoubleArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2MaxTestNan() {
        run {
            val aInput: DoubleArray = doubleArrayOf(7.0, 8.0)
            val bInput: DoubleArray = doubleArrayOf(-4.0, Double.NaN)
            val output: DoubleArray = doubleArrayOf(7.0, Double.NaN)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Max(aVecIn, bVecIn)
            val res: DoubleArray = transmuteWithLayouts<V128, DoubleArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2AddTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, -1.0)
            val bInput: DoubleArray = doubleArrayOf(1.0, -3.0)
            val output: DoubleArray = doubleArrayOf(1.0, -4.0)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Add(aVecIn, bVecIn)
            val res: DoubleArray = transmuteWithLayouts<V128, DoubleArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2SubTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, -1.0)
            val bInput: DoubleArray = doubleArrayOf(1.0, -3.0)
            val output: DoubleArray = doubleArrayOf(-1.0, 2.0)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Sub(aVecIn, bVecIn)
            val res: DoubleArray = transmuteWithLayouts<V128, DoubleArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2MulTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, -1.0)
            val bInput: DoubleArray = doubleArrayOf(1.0, -3.0)
            val output: DoubleArray = doubleArrayOf(0.0, 3.0)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Mul(aVecIn, bVecIn)
            val res: DoubleArray = transmuteWithLayouts<V128, DoubleArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f64x2DivTest() {
        run {
            val aInput: DoubleArray = doubleArrayOf(0.0, -8.0)
            val bInput: DoubleArray = doubleArrayOf(1.0, 4.0)
            val output: DoubleArray = doubleArrayOf(0.0, -2.0)
            val aVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(aInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val bVecIn: V128 = transmuteWithLayouts<DoubleArray, V128>(bInput, PrimitiveArrayLayouts.doubleArray((2).toInt()), UnresolvedTypeLayout<V128>())
            val vecRes: V128 = f64x2Div(aVecIn, bVecIn)
            val res: DoubleArray = transmuteWithLayouts<V128, DoubleArray>(vecRes, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<DoubleArray>())
            if (!true) {
                check((res).contentEquals(output))
            } else {
                for (i in 0 until 2) {
                    val r = res[i]
                    val o = output[i]
                    check(r.isNaN() == o.isNaN())
                    if (!r.isNaN()) {
                        check(r == o)
                    }
                }
            }
        }
    }

    @kotlin.test.Test
    public fun f32x4ConvertSI32x4() {
        run {
            val from: V128 = transmuteWithLayouts<IntArray, V128>(intArrayOf(1, 2, 3, 4), PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<V128>())
            val to: V128 = transmuteWithLayouts<Any?, V128>(listOf(1.0f, 2.0, 3.0, 4.0), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())
            val r: V128 = f32x4ConvertI32x4(from)
            compareBytes(r, to)
        }
    }

    @kotlin.test.Test
    public fun f32x4ConvertUI32x4() {
        run {
            val from: V128 = transmuteWithLayouts<UIntArray, V128>(uintArrayOf(UInt.MAX_VALUE, 2u, 3u, 4u), UnresolvedTypeLayout<UIntArray>(), UnresolvedTypeLayout<V128>())
            val to: V128 = transmuteWithLayouts<FloatArray, V128>(floatArrayOf(UInt.MAX_VALUE.toFloat(), 2.0f, 3.0f, 4.0f), UnresolvedTypeLayout<FloatArray>(), UnresolvedTypeLayout<V128>())
            val r: V128 = f32x4ConvertU32x4(from)
            compareBytes(r, to)
        }
    }

    @kotlin.test.Test
    public fun testConversions() {
        compareBytes(i32x4TruncSatF32x4(f32x4(1.0f, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NaN)), i32x4(1, Int.MIN_VALUE, Int.MAX_VALUE, 0))
        compareBytes(u32x4TruncSatF32x4(f32x4(1.0f, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NaN)), u32x4(1u, 0u, UInt.MAX_VALUE, 0u))
        compareBytes(f64x2ConvertLowI32x4(i32x4(1, 2, 3, 4)), f64x2(1.0, 2.0))
        compareBytes(f64x2ConvertLowI32x4(i32x4(Int.MIN_VALUE, Int.MAX_VALUE, 3, 4)), f64x2(Double.from(Int.MIN_VALUE), Double.from(Int.MAX_VALUE)))
        compareBytes(f64x2ConvertLowU32x4(u32x4(1u, 2u, 3u, 4u)), f64x2(1.0, 2.0))
        compareBytes(f64x2ConvertLowU32x4(u32x4(UInt.MIN_VALUE, UInt.MAX_VALUE, 3u, 4u)), f64x2(Double.from(UInt.MIN_VALUE), Double.from(UInt.MAX_VALUE)))
        compareBytes(i32x4TruncSatF64x2Zero(f64x2(1.0, Double.NEGATIVE_INFINITY)), i32x4(1, Int.MIN_VALUE, 0, 0))
        compareBytes(i32x4TruncSatF64x2Zero(f64x2(Double.NaN, Double.POSITIVE_INFINITY)), i32x4(0, Int.MAX_VALUE, 0, 0))
        compareBytes(u32x4TruncSatF64x2Zero(f64x2(1.0, Double.NEGATIVE_INFINITY)), u32x4(1u, 0u, 0u, 0u))
        compareBytes(u32x4TruncSatF64x2Zero(f64x2(Double.NaN, Double.POSITIVE_INFINITY)), u32x4(0u, UInt.MAX_VALUE, 0u, 0u))
    }

    @kotlin.test.Test
    public fun testPopcnt() {
        run {
            for (i in 0..255) {
                compareBytes(i8x16Popcnt(u8x16Splat(i)), u8x16Splat(i.countOnes().toUByte()))
            }
            val vectors = listOf(ubyteArrayOf(0u.toUByte(), 1u, 2u, 3u, 4u, 5u, 6u, 7u, 8u, 9u, 10u, 11u, 12u, 13u, 14u, 15u), listOf(100, 200, 50, 0, 10, 7, 38, 185, 192, 3, 34, 85, 93, 7, 31, 99))
            for (vector in vectors.iter()) {
                compareBytes(i8x16Popcnt(transmuteWithLayouts<Any?, V128>(vector, UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<V128>())), i8x16(vector[0].countOnes().toByte(), vector[1].countOnes().toByte(), vector[2].countOnes().toByte(), vector[3].countOnes().toByte(), vector[4].countOnes().toByte(), vector[5].countOnes().toByte(), vector[6].countOnes().toByte(), vector[7].countOnes().toByte(), vector[8].countOnes().toByte(), vector[9].countOnes().toByte(), vector[10].countOnes().toByte(), vector[11].countOnes().toByte(), vector[12].countOnes().toByte(), vector[13].countOnes().toByte(), vector[14].countOnes().toByte(), vector[15].countOnes().toByte()))
            }
        }
    }

    @kotlin.test.Test
    public fun testPromoteDemote() {
        val tests = listOf(listOf(1.0, 2.0), doubleArrayOf(Double.NaN, Double.POSITIVE_INFINITY), listOf(100.0, 201.0), listOf(0.0, -0.0), doubleArrayOf(Double.NEGATIVE_INFINITY, 0.0))
        for ((a, b) in tests) {
            compareBytes(f32x4DemoteF64x2Zero(f64x2(a, b)), f32x4(a.toFloat(), b.toFloat(), 0.0f, 0.0f))
            compareBytes(f64x2PromoteLowF32x4(f32x4(a.toFloat(), b.toFloat(), 0.0f, 0.0f)), f64x2(a, b))
        }
    }

    @kotlin.test.Test
    public fun testExtmul() {
        run {
            val a: ByteArray = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            val b: ByteArray = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            val low = transmuteWithLayouts<V128, ShortArray>(i16x8ExtmulLowI8x16(i8x16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), i8x16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()))
            val high = transmuteWithLayouts<V128, ShortArray>(i16x8ExtmulHighI8x16(i8x16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), i8x16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()))
            val half = a.size / 2
            for (i in 0 until half) {
                check(((a[i].toShort()) * (b[i].toShort())).toShort() == low[i])
                check(((a[half + i].toShort()) * (b[half + i].toShort())).toShort() == high[i])
            }
        }
        run {
            val a1: ByteArray = byteArrayOf((-1).toByte(), (-2).toByte(), 3, 100, 124, (-38).toByte(), 33, 87, 92, 108, 22, 8, (-43).toByte(), (-128).toByte(), 22, 0)
            val b1: ByteArray = byteArrayOf((-5).toByte(), (-2).toByte(), 6, 10, 45, (-4).toByte(), 4, (-2).toByte(), 0, 88, 92, (-102).toByte(), (-98).toByte(), 83, 73, 54)
            val low1 = transmuteWithLayouts<V128, ShortArray>(i16x8ExtmulLowI8x16(i8x16((-1).toByte(), (-2).toByte(), 3, 100, 124, (-38).toByte(), 33, 87, 92, 108, 22, 8, (-43).toByte(), (-128).toByte(), 22, 0), i8x16((-5).toByte(), (-2).toByte(), 6, 10, 45, (-4).toByte(), 4, (-2).toByte(), 0, 88, 92, (-102).toByte(), (-98).toByte(), 83, 73, 54)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()))
            val high1 = transmuteWithLayouts<V128, ShortArray>(i16x8ExtmulHighI8x16(i8x16((-1).toByte(), (-2).toByte(), 3, 100, 124, (-38).toByte(), 33, 87, 92, 108, 22, 8, (-43).toByte(), (-128).toByte(), 22, 0), i8x16((-5).toByte(), (-2).toByte(), 6, 10, 45, (-4).toByte(), 4, (-2).toByte(), 0, 88, 92, (-102).toByte(), (-98).toByte(), 83, 73, 54)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()))
            val half1 = a1.size / 2
            for (i1 in 0 until half1) {
                check(((a1[i1].toShort()) * (b1[i1].toShort())).toShort() == low1[i1])
                check(((a1[half1 + i1].toShort()) * (b1[half1 + i1].toShort())).toShort() == high1[i1])
            }
        }
        run {
            val a2: UByteArray = ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val b2: UByteArray = ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val low2 = transmuteWithLayouts<Any?, UShortArray>(u16x8ExtmulLowU8x16(u8x16(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u), u8x16(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uShortArray((16 / sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16)).toInt()))
            val high2 = transmuteWithLayouts<Any?, UShortArray>(u16x8ExtmulHighU8x16(u8x16(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u), u8x16(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uShortArray((16 / sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16)).toInt()))
            val half2 = a2.size / 2
            for (i2 in 0 until half2) {
                check(((a2[i2].toUShort()) * (b2[i2].toUShort())).toUShort() == low2[i2])
                check(((a2[half2 + i2].toUShort()) * (b2[half2 + i2].toUShort())).toUShort() == high2[i2])
            }
        }
        run {
            val a3: UByteArray = ubyteArrayOf(1u, 2u, 3u, 100u, 124u, 38u, 33u, 87u, 92u, 198u, 22u, 8u, 43u, 128u, 22u, 0u)
            val b3: UByteArray = ubyteArrayOf(5u, 200u, 6u, 10u, 45u, 248u, 4u, 2u, 0u, 2u, 92u, 102u, 234u, 83u, 73u, 54u)
            val low3 = transmuteWithLayouts<Any?, UShortArray>(u16x8ExtmulLowU8x16(u8x16(1u, 2u, 3u, 100u, 124u, 38u, 33u, 87u, 92u, 198u, 22u, 8u, 43u, 128u, 22u, 0u), u8x16(5u, 200u, 6u, 10u, 45u, 248u, 4u, 2u, 0u, 2u, 92u, 102u, 234u, 83u, 73u, 54u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uShortArray((16 / sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16)).toInt()))
            val high3 = transmuteWithLayouts<Any?, UShortArray>(u16x8ExtmulHighU8x16(u8x16(1u, 2u, 3u, 100u, 124u, 38u, 33u, 87u, 92u, 198u, 22u, 8u, 43u, 128u, 22u, 0u), u8x16(5u, 200u, 6u, 10u, 45u, 248u, 4u, 2u, 0u, 2u, 92u, 102u, 234u, 83u, 73u, 54u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uShortArray((16 / sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16)).toInt()))
            val half3 = a3.size / 2
            for (i3 in 0 until half3) {
                check(((a3[i3].toUShort()) * (b3[i3].toUShort())).toUShort() == low3[i3])
                check(((a3[half3 + i3].toUShort()) * (b3[half3 + i3].toUShort())).toUShort() == high3[i3])
            }
        }
        run {
            val a4: ShortArray = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            val b4: ShortArray = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            val low4 = transmuteWithLayouts<V128, IntArray>(i32x4ExtmulLowI16x8(i16x8(0, 0, 0, 0, 0, 0, 0, 0), i16x8(0, 0, 0, 0, 0, 0, 0, 0)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.intArray((16 / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()))
            val high4 = transmuteWithLayouts<V128, IntArray>(i32x4ExtmulHighI16x8(i16x8(0, 0, 0, 0, 0, 0, 0, 0), i16x8(0, 0, 0, 0, 0, 0, 0, 0)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.intArray((16 / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()))
            val half4 = a4.size / 2
            for (i4 in 0 until half4) {
                check(((a4[i4].toInt()) * (b4[i4].toInt())) == low4[i4])
                check(((a4[half4 + i4].toInt()) * (b4[half4 + i4].toInt())) == high4[i4])
            }
        }
        run {
            val a5: ShortArray = shortArrayOf((-1).toShort(), 0, Short.MAX_VALUE, 19931, (-2259).toShort(), 64, 200, 87)
            val b5: ShortArray = shortArrayOf(1, 1, Short.MIN_VALUE, 29391, 105, 2, 100, (-2).toShort())
            val low5 = transmuteWithLayouts<V128, IntArray>(i32x4ExtmulLowI16x8(i16x8((-1).toShort(), 0, Short.MAX_VALUE, 19931, (-2259).toShort(), 64, 200, 87), i16x8(1, 1, Short.MIN_VALUE, 29391, 105, 2, 100, (-2).toShort())), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.intArray((16 / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()))
            val high5 = transmuteWithLayouts<V128, IntArray>(i32x4ExtmulHighI16x8(i16x8((-1).toShort(), 0, Short.MAX_VALUE, 19931, (-2259).toShort(), 64, 200, 87), i16x8(1, 1, Short.MIN_VALUE, 29391, 105, 2, 100, (-2).toShort())), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.intArray((16 / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()))
            val half5 = a5.size / 2
            for (i5 in 0 until half5) {
                check(((a5[i5].toInt()) * (b5[i5].toInt())) == low5[i5])
                check(((a5[half5 + i5].toInt()) * (b5[half5 + i5].toInt())) == high5[i5])
            }
        }
        run {
            val a6: UShortArray = ushortArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val b6: UShortArray = ushortArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val low6 = transmuteWithLayouts<Any?, UIntArray>(u32x4ExtmulLowU16x8(u16x8(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u), u16x8(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uIntArray((16 / sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32)).toInt()))
            val high6 = transmuteWithLayouts<Any?, UIntArray>(u32x4ExtmulHighU16x8(u16x8(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u), u16x8(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uIntArray((16 / sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32)).toInt()))
            val half6 = a6.size / 2
            for (i6 in 0 until half6) {
                check(((a6[i6].toUInt()) * (b6[i6].toUInt())) == low6[i6])
                check(((a6[half6 + i6].toUInt()) * (b6[half6 + i6].toUInt())) == high6[i6])
            }
        }
        run {
            val a7: UShortArray = ushortArrayOf(1u, 0u, UShort.MAX_VALUE, 19931u, 2259u, 64u, 200u, 87u)
            val b7: UShortArray = ushortArrayOf(1u, 1u, 3u, 29391u, 105u, 2u, 100u, 2u)
            val low7 = transmuteWithLayouts<Any?, UIntArray>(u32x4ExtmulLowU16x8(u16x8(1u, 0u, UShort.MAX_VALUE, 19931u, 2259u, 64u, 200u, 87u), u16x8(1u, 1u, 3u, 29391u, 105u, 2u, 100u, 2u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uIntArray((16 / sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32)).toInt()))
            val high7 = transmuteWithLayouts<Any?, UIntArray>(u32x4ExtmulHighU16x8(u16x8(1u, 0u, UShort.MAX_VALUE, 19931u, 2259u, 64u, 200u, 87u), u16x8(1u, 1u, 3u, 29391u, 105u, 2u, 100u, 2u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uIntArray((16 / sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32)).toInt()))
            val half7 = a7.size / 2
            for (i7 in 0 until half7) {
                check(((a7[i7].toUInt()) * (b7[i7].toUInt())) == low7[i7])
                check(((a7[half7 + i7].toUInt()) * (b7[half7 + i7].toUInt())) == high7[i7])
            }
        }
        run {
            val a8: IntArray = intArrayOf(0, 0, 0, 0)
            val b8: IntArray = intArrayOf(0, 0, 0, 0)
            val low8 = transmuteWithLayouts<V128, LongArray>(i64x2ExtmulLowI32x4(i32x4(0, 0, 0, 0), i32x4(0, 0, 0, 0)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.longArray((16 / sizeOf<Long>(typeLayoutT = PrimitiveLayouts.I64)).toInt()))
            val high8 = transmuteWithLayouts<V128, LongArray>(i64x2ExtmulHighI32x4(i32x4(0, 0, 0, 0), i32x4(0, 0, 0, 0)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.longArray((16 / sizeOf<Long>(typeLayoutT = PrimitiveLayouts.I64)).toInt()))
            val half8 = a8.size / 2
            for (i8 in 0 until half8) {
                check(((a8[i8].toLong()) * (b8[i8].toLong())) == low8[i8])
                check(((a8[half8 + i8].toLong()) * (b8[half8 + i8].toLong())) == high8[i8])
            }
        }
        run {
            val a9: IntArray = intArrayOf(-1, 0, Int.MAX_VALUE, 19931)
            val b9: IntArray = intArrayOf(1, 1, Int.MIN_VALUE, 29391)
            val low9 = transmuteWithLayouts<V128, LongArray>(i64x2ExtmulLowI32x4(i32x4(-1, 0, Int.MAX_VALUE, 19931), i32x4(1, 1, Int.MIN_VALUE, 29391)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.longArray((16 / sizeOf<Long>(typeLayoutT = PrimitiveLayouts.I64)).toInt()))
            val high9 = transmuteWithLayouts<V128, LongArray>(i64x2ExtmulHighI32x4(i32x4(-1, 0, Int.MAX_VALUE, 19931), i32x4(1, 1, Int.MIN_VALUE, 29391)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.longArray((16 / sizeOf<Long>(typeLayoutT = PrimitiveLayouts.I64)).toInt()))
            val half9 = a9.size / 2
            for (i9 in 0 until half9) {
                check(((a9[i9].toLong()) * (b9[i9].toLong())) == low9[i9])
                check(((a9[half9 + i9].toLong()) * (b9[half9 + i9].toLong())) == high9[i9])
            }
        }
        run {
            val a10: IntArray = intArrayOf(Int.MAX_VALUE, 3003183, 3 shl 20, 0xffffff)
            val b10: IntArray = intArrayOf(Int.MAX_VALUE, Int.MIN_VALUE, -40042, 300)
            val low10 = transmuteWithLayouts<V128, LongArray>(i64x2ExtmulLowI32x4(i32x4(Int.MAX_VALUE, 3003183, 3 shl 20, 0xffffff), i32x4(Int.MAX_VALUE, Int.MIN_VALUE, -40042, 300)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.longArray((16 / sizeOf<Long>(typeLayoutT = PrimitiveLayouts.I64)).toInt()))
            val high10 = transmuteWithLayouts<V128, LongArray>(i64x2ExtmulHighI32x4(i32x4(Int.MAX_VALUE, 3003183, 3 shl 20, 0xffffff), i32x4(Int.MAX_VALUE, Int.MIN_VALUE, -40042, 300)), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.longArray((16 / sizeOf<Long>(typeLayoutT = PrimitiveLayouts.I64)).toInt()))
            val half10 = a10.size / 2
            for (i10 in 0 until half10) {
                check(((a10[i10].toLong()) * (b10[i10].toLong())) == low10[i10])
                check(((a10[half10 + i10].toLong()) * (b10[half10 + i10].toLong())) == high10[i10])
            }
        }
        run {
            val a11: UIntArray = uintArrayOf(0u, 0u, 0u, 0u)
            val b11: UIntArray = uintArrayOf(0u, 0u, 0u, 0u)
            val low11 = transmuteWithLayouts<Any?, ULongArray>(u64x2ExtmulLowU32x4(u32x4(0u, 0u, 0u, 0u), u32x4(0u, 0u, 0u, 0u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uLongArray((16 / sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64)).toInt()))
            val high11 = transmuteWithLayouts<Any?, ULongArray>(u64x2ExtmulHighU32x4(u32x4(0u, 0u, 0u, 0u), u32x4(0u, 0u, 0u, 0u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uLongArray((16 / sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64)).toInt()))
            val half11 = a11.size / 2
            for (i11 in 0 until half11) {
                check(((a11[i11].toULong()) * (b11[i11].toULong())) == low11[i11])
                check(((a11[half11 + i11].toULong()) * (b11[half11 + i11].toULong())) == high11[i11])
            }
        }
        run {
            val a12: UIntArray = uintArrayOf(1u, 0u, UInt.MAX_VALUE, 19931u)
            val b12: UIntArray = uintArrayOf(1u, 1u, 3u, 29391u)
            val low12 = transmuteWithLayouts<Any?, ULongArray>(u64x2ExtmulLowU32x4(u32x4(1u, 0u, UInt.MAX_VALUE, 19931u), u32x4(1u, 1u, 3u, 29391u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uLongArray((16 / sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64)).toInt()))
            val high12 = transmuteWithLayouts<Any?, ULongArray>(u64x2ExtmulHighU32x4(u32x4(1u, 0u, UInt.MAX_VALUE, 19931u), u32x4(1u, 1u, 3u, 29391u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uLongArray((16 / sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64)).toInt()))
            val half12 = a12.size / 2
            for (i12 in 0 until half12) {
                check(((a12[i12].toULong()) * (b12[i12].toULong())) == low12[i12])
                check(((a12[half12 + i12].toULong()) * (b12[half12 + i12].toULong())) == high12[i12])
            }
        }
        run {
            val a13: UIntArray = uintArrayOf(UInt.MAX_VALUE, 3003183u, 3u shl 20, 0xffffffu)
            val b13: UIntArray = uintArrayOf(UInt.MAX_VALUE, 3000u, 40042u, 300u)
            val low13 = transmuteWithLayouts<Any?, ULongArray>(u64x2ExtmulLowU32x4(u32x4(UInt.MAX_VALUE, 3003183u, 3u shl 20, 0xffffffu), u32x4(UInt.MAX_VALUE, 3000u, 40042u, 300u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uLongArray((16 / sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64)).toInt()))
            val high13 = transmuteWithLayouts<Any?, ULongArray>(u64x2ExtmulHighU32x4(u32x4(UInt.MAX_VALUE, 3003183u, 3u shl 20, 0xffffffu), u32x4(UInt.MAX_VALUE, 3000u, 40042u, 300u)), UnresolvedTypeLayout<Any?>(), PrimitiveArrayLayouts.uLongArray((16 / sizeOf<ULong>(typeLayoutT = PrimitiveLayouts.U64)).toInt()))
            val half13 = a13.size / 2
            for (i13 in 0 until half13) {
                check(((a13[i13].toULong()) * (b13[i13].toULong())) == low13[i13])
                check(((a13[half13 + i13].toULong()) * (b13[half13 + i13].toULong())) == high13[i13])
            }
        }
    }

    @kotlin.test.Test
    public fun testQ15mulrSatS() {
        fun test(a: ShortArray, b: ShortArray) {
            val aV = i16x8(a[0], a[1], a[2], a[3], a[4], a[5], a[6], a[7])
            val bV = i16x8(b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7])
            val result = i16x8Q15mulrSat(aV, bV)
            val result1 = transmuteWithLayouts<V128, ShortArray>(result, UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((8).toInt()))
            for ((i, a1, b1) in (a.zip(b)).mapIndexed { index, item -> Pair(index, item) }) {
                check(result1[i] == (((a1.toInt()) * (b1.toInt()) + 0x4000) shr 15).toShort())
            }
        }
        test(shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0), shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0))
        test(shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1), shortArrayOf(1, 1, 1, 1, 1, 1, 1, 1))
        test(shortArrayOf((-1).toShort(), 100, 2003, (-29494).toShort(), 12, 128, 994, 1), shortArrayOf((-4049).toShort(), 8494, (-10483).toShort(), 0, 5, 2222, 883, (-9).toShort()))
    }

    @kotlin.test.Test
    public fun testExtadd() {
        run {
            val a: ByteArray = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            val aV = transmuteWithLayouts<ByteArray, V128>(a, PrimitiveArrayLayouts.byteArray((16 / sizeOf<Byte>(typeLayoutT = PrimitiveLayouts.I8)).toInt()), UnresolvedTypeLayout<V128>())
            val r = transmuteWithLayouts<V128, ShortArray>(i16x8ExtaddPairwiseI8x16(aV), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()))
            val half = a.size / 2
            for (i in 0 until half) {
                check(((a[2 * i].toShort()) + (a[2 * i + 1].toShort())).toShort() == r[i])
            }
        }
        run {
            val a1: ByteArray = byteArrayOf((-1).toByte(), (-2).toByte(), 3, 100, 124, (-38).toByte(), 33, 87, 92, 108, 22, 8, (-43).toByte(), (-128).toByte(), 22, 0)
            val aV1 = transmuteWithLayouts<ByteArray, V128>(a1, PrimitiveArrayLayouts.byteArray((16 / sizeOf<Byte>(typeLayoutT = PrimitiveLayouts.I8)).toInt()), UnresolvedTypeLayout<V128>())
            val r1 = transmuteWithLayouts<V128, ShortArray>(i16x8ExtaddPairwiseI8x16(aV1), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()))
            val half1 = a1.size / 2
            for (i1 in 0 until half1) {
                check(((a1[2 * i1].toShort()) + (a1[2 * i1 + 1].toShort())).toShort() == r1[i1])
            }
        }
        run {
            val a2: ByteArray = byteArrayOf((-5).toByte(), (-2).toByte(), 6, 10, 45, (-4).toByte(), 4, (-2).toByte(), 0, 88, 92, (-102).toByte(), (-98).toByte(), 83, 73, 54)
            val aV2 = transmuteWithLayouts<ByteArray, V128>(a2, PrimitiveArrayLayouts.byteArray((16 / sizeOf<Byte>(typeLayoutT = PrimitiveLayouts.I8)).toInt()), UnresolvedTypeLayout<V128>())
            val r2 = transmuteWithLayouts<V128, ShortArray>(i16x8ExtaddPairwiseI8x16(aV2), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()))
            val half2 = a2.size / 2
            for (i2 in 0 until half2) {
                check(((a2[2 * i2].toShort()) + (a2[2 * i2 + 1].toShort())).toShort() == r2[i2])
            }
        }
        run {
            val a3: UByteArray = ubyteArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val aV3 = transmuteWithLayouts<UByteArray, V128>(a3, PrimitiveArrayLayouts.uByteArray((16 / sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8)).toInt()), UnresolvedTypeLayout<V128>())
            val r3 = transmuteWithLayouts<V128, ShortArray>(i16x8ExtaddPairwiseU8x16(aV3), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()))
            val half3 = a3.size / 2
            for (i3 in 0 until half3) {
                check(((a3[2 * i3].toShort()) + (a3[2 * i3 + 1].toShort())).toShort() == r3[i3])
            }
        }
        run {
            val a4: UByteArray = ubyteArrayOf(1u, 2u, 3u, 100u, 124u, 38u, 33u, 87u, 92u, 198u, 22u, 8u, 43u, 128u, 22u, 0u)
            val aV4 = transmuteWithLayouts<UByteArray, V128>(a4, PrimitiveArrayLayouts.uByteArray((16 / sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8)).toInt()), UnresolvedTypeLayout<V128>())
            val r4 = transmuteWithLayouts<V128, ShortArray>(i16x8ExtaddPairwiseU8x16(aV4), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()))
            val half4 = a4.size / 2
            for (i4 in 0 until half4) {
                check(((a4[2 * i4].toShort()) + (a4[2 * i4 + 1].toShort())).toShort() == r4[i4])
            }
        }
        run {
            val a5: UByteArray = ubyteArrayOf(5u, 200u, 6u, 10u, 45u, 248u, 4u, 2u, 0u, 2u, 92u, 102u, 234u, 83u, 73u, 54u)
            val aV5 = transmuteWithLayouts<UByteArray, V128>(a5, PrimitiveArrayLayouts.uByteArray((16 / sizeOf<UByte>(typeLayoutT = PrimitiveLayouts.U8)).toInt()), UnresolvedTypeLayout<V128>())
            val r5 = transmuteWithLayouts<V128, ShortArray>(i16x8ExtaddPairwiseU8x16(aV5), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()))
            val half5 = a5.size / 2
            for (i5 in 0 until half5) {
                check(((a5[2 * i5].toShort()) + (a5[2 * i5 + 1].toShort())).toShort() == r5[i5])
            }
        }
        run {
            val a6: ShortArray = shortArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            val aV6 = transmuteWithLayouts<ShortArray, V128>(a6, PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()), UnresolvedTypeLayout<V128>())
            val r6 = transmuteWithLayouts<V128, IntArray>(i32x4ExtaddPairwiseI16x8(aV6), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.intArray((16 / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()))
            val half6 = a6.size / 2
            for (i6 in 0 until half6) {
                check(((a6[2 * i6].toInt()) + (a6[2 * i6 + 1].toInt())) == r6[i6])
            }
        }
        run {
            val a7: ShortArray = shortArrayOf((-1).toShort(), 0, Short.MAX_VALUE, 19931, (-2259).toShort(), 64, 200, 87)
            val aV7 = transmuteWithLayouts<ShortArray, V128>(a7, PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()), UnresolvedTypeLayout<V128>())
            val r7 = transmuteWithLayouts<V128, IntArray>(i32x4ExtaddPairwiseI16x8(aV7), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.intArray((16 / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()))
            val half7 = a7.size / 2
            for (i7 in 0 until half7) {
                check(((a7[2 * i7].toInt()) + (a7[2 * i7 + 1].toInt())) == r7[i7])
            }
        }
        run {
            val a8: ShortArray = shortArrayOf(1, 1, Short.MIN_VALUE, 29391, 105, 2, 100, (-2).toShort())
            val aV8 = transmuteWithLayouts<ShortArray, V128>(a8, PrimitiveArrayLayouts.shortArray((16 / sizeOf<Short>(typeLayoutT = PrimitiveLayouts.I16)).toInt()), UnresolvedTypeLayout<V128>())
            val r8 = transmuteWithLayouts<V128, IntArray>(i32x4ExtaddPairwiseI16x8(aV8), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.intArray((16 / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()))
            val half8 = a8.size / 2
            for (i8 in 0 until half8) {
                check(((a8[2 * i8].toInt()) + (a8[2 * i8 + 1].toInt())) == r8[i8])
            }
        }
        run {
            val a9: UShortArray = ushortArrayOf(0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)
            val aV9 = transmuteWithLayouts<UShortArray, V128>(a9, PrimitiveArrayLayouts.uShortArray((16 / sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16)).toInt()), UnresolvedTypeLayout<V128>())
            val r9 = transmuteWithLayouts<V128, IntArray>(i32x4ExtaddPairwiseU16x8(aV9), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.intArray((16 / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()))
            val half9 = a9.size / 2
            for (i9 in 0 until half9) {
                check(((a9[2 * i9].toInt()) + (a9[2 * i9 + 1].toInt())) == r9[i9])
            }
        }
        run {
            val a10: UShortArray = ushortArrayOf(1u, 0u, UShort.MAX_VALUE, 19931u, 2259u, 64u, 200u, 87u)
            val aV10 = transmuteWithLayouts<UShortArray, V128>(a10, PrimitiveArrayLayouts.uShortArray((16 / sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16)).toInt()), UnresolvedTypeLayout<V128>())
            val r10 = transmuteWithLayouts<V128, IntArray>(i32x4ExtaddPairwiseU16x8(aV10), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.intArray((16 / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()))
            val half10 = a10.size / 2
            for (i10 in 0 until half10) {
                check(((a10[2 * i10].toInt()) + (a10[2 * i10 + 1].toInt())) == r10[i10])
            }
        }
        run {
            val a11: UShortArray = ushortArrayOf(1u, 1u, 3u, 29391u, 105u, 2u, 100u, 2u)
            val aV11 = transmuteWithLayouts<UShortArray, V128>(a11, PrimitiveArrayLayouts.uShortArray((16 / sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16)).toInt()), UnresolvedTypeLayout<V128>())
            val r11 = transmuteWithLayouts<V128, IntArray>(i32x4ExtaddPairwiseU16x8(aV11), UnresolvedTypeLayout<V128>(), PrimitiveArrayLayouts.intArray((16 / sizeOf<Int>(typeLayoutT = PrimitiveLayouts.I32)).toInt()))
            val half11 = a11.size / 2
            for (i11 in 0 until half11) {
                check(((a11[2 * i11].toInt()) + (a11[2 * i11 + 1].toInt())) == r11[i11])
            }
        }
    }
}

public class Simd128SplatsInlineTests {
    @kotlin.test.Test
    public fun i8x16Splat() {
        val a = i8x16Splat(42)
        val b = u8x16(42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte(), 42.toUByte())
        compareBytes(a, b)
    }

    @kotlin.test.Test
    public fun i16x8Splat() {
        val a = i16x8Splat(42)
        val b = u8x16(42.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte())
        compareBytes(a, b)
    }

    @kotlin.test.Test
    public fun i32x4Splat() {
        val a = i32x4Splat(42)
        val b = u8x16(42.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte())
        compareBytes(a, b)
    }

    @kotlin.test.Test
    public fun i64x2Splat() {
        val a = i64x2Splat(42L)
        val b = u8x16(42.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 42.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte())
        compareBytes(a, b)
    }

    @kotlin.test.Test
    public fun f32x4Splat() {
        val a = f32x4Splat(42.0f)
        val b = u8x16(0.toUByte(), 0.toUByte(), 40.toUByte(), 66.toUByte(), 0.toUByte(), 0.toUByte(), 40.toUByte(), 66.toUByte(), 0.toUByte(), 0.toUByte(), 40.toUByte(), 66.toUByte(), 0.toUByte(), 0.toUByte(), 40.toUByte(), 66.toUByte())
        compareBytes(a, b)
    }

    @kotlin.test.Test
    public fun f64x2Splat() {
        val a = f64x2Splat(42.0)
        val b = u8x16(0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 69.toUByte(), 64.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 0.toUByte(), 69.toUByte(), 64.toUByte())
        compareBytes(a, b)
    }
}
