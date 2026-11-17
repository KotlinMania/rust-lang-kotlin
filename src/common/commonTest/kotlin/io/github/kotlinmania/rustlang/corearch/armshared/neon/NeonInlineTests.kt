@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.armshared.neon

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveArrayLayouts
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.fixedListTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.corearch.aarch64.neon.crc32cd
import io.github.kotlinmania.rustlang.corearch.aarch64.neon.crc32d
import io.github.kotlinmania.rustlang.corearch.simd.I64x1
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U64x1

// ARMv7 NEON intrinsics

public fun testVld1LaneS8() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte()))
    val elem: Byte = 42
    val e = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 42.toByte()))
    val r = i8x8.from(vld1LaneS8(elem, a.into(), 7))
    check(r == e)
}

public fun testVld1qLaneS8() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte()))
    val elem: Byte = 42
    val e = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 42.toByte()))
    val r = i8x16.from(vld1qLaneS8(elem, a.into(), 15))
    check(r == e)
}

public fun testVld1LaneS16() {
    val a = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort()))
    val elem: Short = 42
    val e = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 42.toShort()))
    val r = i16x4.from(vld1LaneS16(elem, a.into(), 3))
    check(r == e)
}

public fun testVld1qLaneS16() {
    val a = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort()))
    val elem: Short = 42
    val e = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 42.toShort()))
    val r = i16x8.from(vld1qLaneS16(elem, a.into(), 7))
    check(r == e)
}

public fun testVld1LaneS32() {
    val a = Simd<Int>(listOf(0, 1))
    val elem: Int = 42
    val e = Simd<Int>(listOf(0, 42))
    val r = i32x2.from(vld1LaneS32(elem, a.into(), 1))
    check(r == e)
}

public fun testVld1qLaneS32() {
    val a = Simd<Int>(listOf(0, 1, 2, 3))
    val elem: Int = 42
    val e = Simd<Int>(listOf(0, 1, 2, 42))
    val r = i32x4.from(vld1qLaneS32(elem, a.into(), 3))
    check(r == e)
}

public fun testVld1LaneS64() {
    val a = Simd<Long>(listOf(0L))
    val elem: Long = 42L
    val e = Simd<Long>(listOf(42L))
    val r = i64x1.from(vld1LaneS64(elem, a.into(), 0))
    check(r == e)
}

public fun testVld1qLaneS64() {
    val a = Simd<Long>(listOf(0L, 1L))
    val elem: Long = 42L
    val e = Simd<Long>(listOf(0L, 42L))
    val r = i64x2.from(vld1qLaneS64(elem, a.into(), 1))
    check(r == e)
}

public fun testVld1LaneU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val elem: UByte = 42u
    val e = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 42u.toUByte()))
    val r = u8x8.from(vld1LaneU8(elem, a.into(), 7))
    check(r == e)
}

public fun testVld1qLaneU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val elem: UByte = 42u
    val e = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 42u.toUByte()))
    val r = u8x16.from(vld1qLaneU8(elem, a.into(), 15))
    check(r == e)
}

public fun testVld1LaneU16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort()))
    val elem: UShort = 42u
    val e = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 42u.toUShort()))
    val r = u16x4.from(vld1LaneU16(elem, a.into(), 3))
    check(r == e)
}

public fun testVld1qLaneU16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    val elem: UShort = 42u
    val e = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 42u.toUShort()))
    val r = u16x8.from(vld1qLaneU16(elem, a.into(), 7))
    check(r == e)
}

public fun testVld1LaneU32() {
    val a = Simd<UInt>(listOf(0u, 1u))
    val elem: UInt = 42u
    val e = Simd<UInt>(listOf(0u, 42u))
    val r = u32x2.from(vld1LaneU32(elem, a.into(), 1))
    check(r == e)
}

public fun testVld1qLaneU32() {
    val a = Simd<UInt>(listOf(0u, 1u, 2u, 3u))
    val elem: UInt = 42u
    val e = Simd<UInt>(listOf(0u, 1u, 2u, 42u))
    val r = u32x4.from(vld1qLaneU32(elem, a.into(), 3))
    check(r == e)
}

public fun testVld1LaneU64() {
    val a = Simd<ULong>(listOf(0uL))
    val elem: ULong = 42uL
    val e = Simd<ULong>(listOf(42uL))
    val r = u64x1.from(vld1LaneU64(elem, a.into(), 0))
    check(r == e)
}

public fun testVld1qLaneU64() {
    val a = Simd<ULong>(listOf(0uL, 1uL))
    val elem: ULong = 42uL
    val e = Simd<ULong>(listOf(0uL, 42uL))
    val r = u64x2.from(vld1qLaneU64(elem, a.into(), 1))
    check(r == e)
}

public fun testVld1LaneP8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val elem: P8 = 42u
    val e = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 42u.toUByte()))
    val r = u8x8.from(vld1LaneP8(elem, a.into(), 7))
    check(r == e)
}

public fun testVld1qLaneP8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val elem: P8 = 42u
    val e = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 42u.toUByte()))
    val r = u8x16.from(vld1qLaneP8(elem, a.into(), 15))
    check(r == e)
}

public fun testVld1LaneP16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort()))
    val elem: P16 = 42u
    val e = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 42u.toUShort()))
    val r = u16x4.from(vld1LaneP16(elem, a.into(), 3))
    check(r == e)
}

public fun testVld1qLaneP16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    val elem: P16 = 42u
    val e = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 42u.toUShort()))
    val r = u16x8.from(vld1qLaneP16(elem, a.into(), 7))
    check(r == e)
}

public fun testVld1LaneP64() {
    val a = Simd<ULong>(listOf(0uL))
    val elem: ULong = 42uL
    val e = Simd<ULong>(listOf(42uL))
    val r = u64x1.from(vld1LaneP64(elem, a.into(), 0))
    check(r == e)
}

public fun testVld1qLaneP64() {
    val a = Simd<ULong>(listOf(0uL, 1uL))
    val elem: ULong = 42uL
    val e = Simd<ULong>(listOf(0uL, 42uL))
    val r = u64x2.from(vld1qLaneP64(elem, a.into(), 1))
    check(r == e)
}

public fun testVld1LaneF32() {
    val a = Simd<Float>(listOf(0.0f.toFloat(), 1.0f.toFloat()))
    val elem: Float = 42.0f
    val e = Simd<Float>(listOf(0.0f.toFloat(), 42.0f.toFloat()))
    val r = f32x2.from(vld1LaneF32(elem, a.into(), 1))
    check(r == e)
}

public fun testVld1qLaneF32() {
    val a = Simd<Float>(listOf(0.0f.toFloat(), 1.0f.toFloat(), 2.0f.toFloat(), 3.0f.toFloat()))
    val elem: Float = 42.0f
    val e = Simd<Float>(listOf(0.0f.toFloat(), 1.0f.toFloat(), 2.0f.toFloat(), 42.0f.toFloat()))
    val r = f32x4.from(vld1qLaneF32(elem, a.into(), 3))
    check(r == e)
}

public fun testVld1DupS8() {
    val elem: Byte = 42
    val e = Simd<Byte>(listOf(42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte()))
    val r = i8x8.from(vld1DupS8(elem))
    check(r == e)
}

public fun testVld1qDupS8() {
    val elem: Byte = 42
    val e = Simd<Byte>(listOf(42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte()))
    val r = i8x16.from(vld1qDupS8(elem))
    check(r == e)
}

public fun testVld1DupS16() {
    val elem: Short = 42
    val e = Simd<Short>(listOf(42.toShort(), 42.toShort(), 42.toShort(), 42.toShort()))
    val r = i16x4.from(vld1DupS16(elem))
    check(r == e)
}

public fun testVld1qDupS16() {
    val elem: Short = 42
    val e = Simd<Short>(listOf(42.toShort(), 42.toShort(), 42.toShort(), 42.toShort(), 42.toShort(), 42.toShort(), 42.toShort(), 42.toShort()))
    val r = i16x8.from(vld1qDupS16(elem))
    check(r == e)
}

public fun testVld1DupS32() {
    val elem: Int = 42
    val e = Simd<Int>(listOf(42, 42))
    val r = i32x2.from(vld1DupS32(elem))
    check(r == e)
}

public fun testVld1qDupS32() {
    val elem: Int = 42
    val e = Simd<Int>(listOf(42, 42, 42, 42))
    val r = i32x4.from(vld1qDupS32(elem))
    check(r == e)
}

public fun testVld1DupS64() {
    val elem: Long = 42L
    val e = Simd<Long>(listOf(42L))
    val r = i64x1.from(vld1DupS64(elem))
    check(r == e)
}

public fun testVld1qDupS64() {
    val elem: Long = 42L
    val e = Simd<Long>(listOf(42L, 42L))
    val r = i64x2.from(vld1qDupS64(elem))
    check(r == e)
}

public fun testVld1DupU8() {
    val elem: UByte = 42u
    val e = Simd<UByte>(listOf(42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte()))
    val r = u8x8.from(vld1DupU8(elem))
    check(r == e)
}

public fun testVld1qDupU8() {
    val elem: UByte = 42u
    val e = Simd<UByte>(listOf(42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte()))
    val r = u8x16.from(vld1qDupU8(elem))
    check(r == e)
}

public fun testVld1DupU16() {
    val elem: UShort = 42u
    val e = Simd<UShort>(listOf(42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort()))
    val r = u16x4.from(vld1DupU16(elem))
    check(r == e)
}

public fun testVld1qDupU16() {
    val elem: UShort = 42u
    val e = Simd<UShort>(listOf(42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort()))
    val r = u16x8.from(vld1qDupU16(elem))
    check(r == e)
}

public fun testVld1DupU32() {
    val elem: UInt = 42u
    val e = Simd<UInt>(listOf(42u, 42u))
    val r = u32x2.from(vld1DupU32(elem))
    check(r == e)
}

public fun testVld1qDupU32() {
    val elem: UInt = 42u
    val e = Simd<UInt>(listOf(42u, 42u, 42u, 42u))
    val r = u32x4.from(vld1qDupU32(elem))
    check(r == e)
}

public fun testVld1DupU64() {
    val elem: ULong = 42uL
    val e = Simd<ULong>(listOf(42uL))
    val r = u64x1.from(vld1DupU64(elem))
    check(r == e)
}

public fun testVld1qDupU64() {
    val elem: ULong = 42uL
    val e = Simd<ULong>(listOf(42uL, 42uL))
    val r = u64x2.from(vld1qDupU64(elem))
    check(r == e)
}

public fun testVld1DupP8() {
    val elem: P8 = 42u
    val e = Simd<UByte>(listOf(42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte()))
    val r = u8x8.from(vld1DupP8(elem))
    check(r == e)
}

public fun testVld1qDupP8() {
    val elem: P8 = 42u
    val e = Simd<UByte>(listOf(42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte(), 42u.toUByte()))
    val r = u8x16.from(vld1qDupP8(elem))
    check(r == e)
}

public fun testVld1DupP16() {
    val elem: P16 = 42u
    val e = Simd<UShort>(listOf(42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort()))
    val r = u16x4.from(vld1DupP16(elem))
    check(r == e)
}

public fun testVld1qDupP16() {
    val elem: P16 = 42u
    val e = Simd<UShort>(listOf(42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort(), 42u.toUShort()))
    val r = u16x8.from(vld1qDupP16(elem))
    check(r == e)
}

public fun testVld1DupP64() {
    val elem: ULong = 42uL
    val e = Simd<ULong>(listOf(42uL))
    val r = u64x1.from(vld1DupP64(elem))
    check(r == e)
}

public fun testVld1qDupP64() {
    val elem: ULong = 42uL
    val e = Simd<ULong>(listOf(42uL, 42uL))
    val r = u64x2.from(vld1qDupP64(elem))
    check(r == e)
}

public fun testVld1DupF32() {
    val elem: Float = 42.0f
    val e = Simd<Float>(listOf(42.0f.toFloat(), 42.0f.toFloat()))
    val r = f32x2.from(vld1DupF32(elem))
    check(r == e)
}

public fun testVld1qDupF32() {
    val elem: Float = 42.0f
    val e = Simd<Float>(listOf(42.0f.toFloat(), 42.0f.toFloat(), 42.0f.toFloat(), 42.0f.toFloat()))
    val r = f32x4.from(vld1qDupF32(elem))
    check(r == e)
}

public fun testVextS64() {
    val a: I64x1 = Simd<Long>(listOf(0L))
    val b: I64x1 = Simd<Long>(listOf(1L))
    val e: I64x1 = Simd<Long>(listOf(0L))
    val r = i64x1.from(vextS64(a.into(), b.into(), 0))
    check(r == e)
}

public fun testVextU64() {
    val a: U64x1 = Simd<ULong>(listOf(0uL))
    val b: U64x1 = Simd<ULong>(listOf(1uL))
    val e: U64x1 = Simd<ULong>(listOf(0uL))
    val r = u64x1.from(vextU64(a.into(), b.into(), 0))
    check(r == e)
}

public fun testVdupqNS8() {
    val v: Byte = 42
    val e = Simd<Byte>(listOf(42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte(), 42.toByte()))
    val r = i8x16.from(vdupqNS8(v))
    check(r == e)
}

public fun testVdupqNS16() {
    val v: Short = 64
    val e = Simd<Short>(listOf(64.toShort(), 64.toShort(), 64.toShort(), 64.toShort(), 64.toShort(), 64.toShort(), 64.toShort(), 64.toShort()))
    val r = i16x8.from(vdupqNS16(v))
    check(r == e)
}

public fun testVdupqNS32() {
    val v: Int = 64
    val e = Simd<Int>(listOf(64, 64, 64, 64))
    val r = i32x4.from(vdupqNS32(v))
    check(r == e)
}

public fun testVdupqNS64() {
    val v: Long = 64L
    val e = Simd<Long>(listOf(64L, 64L))
    val r = i64x2.from(vdupqNS64(v))
    check(r == e)
}

public fun testVdupqNU8() {
    val v: UByte = 64u
    val e = Simd<UByte>(listOf(64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte()))
    val r = u8x16.from(vdupqNU8(v))
    check(r == e)
}

public fun testVdupqNU16() {
    val v: UShort = 64u
    val e = Simd<UShort>(listOf(64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort()))
    val r = u16x8.from(vdupqNU16(v))
    check(r == e)
}

public fun testVdupqNU32() {
    val v: UInt = 64u
    val e = Simd<UInt>(listOf(64u, 64u, 64u, 64u))
    val r = u32x4.from(vdupqNU32(v))
    check(r == e)
}

public fun testVdupqNU64() {
    val v: ULong = 64uL
    val e = Simd<ULong>(listOf(64uL, 64uL))
    val r = u64x2.from(vdupqNU64(v))
    check(r == e)
}

public fun testVdupqNP8() {
    val v: P8 = 64u
    val e = Simd<UByte>(listOf(64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte()))
    val r = u8x16.from(vdupqNP8(v))
    check(r == e)
}

public fun testVdupqNP16() {
    val v: P16 = 64u
    val e = Simd<UShort>(listOf(64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort()))
    val r = u16x8.from(vdupqNP16(v))
    check(r == e)
}

public fun testVdupqNF32() {
    val v: Float = 64.0f
    val e = Simd<Float>(listOf(64.0f.toFloat(), 64.0f.toFloat(), 64.0f.toFloat(), 64.0f.toFloat()))
    val r = f32x4.from(vdupqNF32(v))
    check(r == e)
}

public fun testVdupNS8() {
    val v: Byte = 64
    val e = Simd<Byte>(listOf(64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte()))
    val r = i8x8.from(vdupNS8(v))
    check(r == e)
}

public fun testVdupNS16() {
    val v: Short = 64
    val e = Simd<Short>(listOf(64.toShort(), 64.toShort(), 64.toShort(), 64.toShort()))
    val r = i16x4.from(vdupNS16(v))
    check(r == e)
}

public fun testVdupNS32() {
    val v: Int = 64
    val e = Simd<Int>(listOf(64, 64))
    val r = i32x2.from(vdupNS32(v))
    check(r == e)
}

public fun testVdupNS64() {
    val v: Long = 64L
    val e = Simd<Long>(listOf(64L))
    val r = i64x1.from(vdupNS64(v))
    check(r == e)
}

public fun testVdupNU8() {
    val v: UByte = 64u
    val e = Simd<UByte>(listOf(64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte()))
    val r = u8x8.from(vdupNU8(v))
    check(r == e)
}

public fun testVdupNU16() {
    val v: UShort = 64u
    val e = Simd<UShort>(listOf(64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort()))
    val r = u16x4.from(vdupNU16(v))
    check(r == e)
}

public fun testVdupNU32() {
    val v: UInt = 64u
    val e = Simd<UInt>(listOf(64u, 64u))
    val r = u32x2.from(vdupNU32(v))
    check(r == e)
}

public fun testVdupNU64() {
    val v: ULong = 64uL
    val e = Simd<ULong>(listOf(64uL))
    val r = u64x1.from(vdupNU64(v))
    check(r == e)
}

public fun testVdupNP8() {
    val v: P8 = 64u
    val e = Simd<UByte>(listOf(64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte()))
    val r = u8x8.from(vdupNP8(v))
    check(r == e)
}

public fun testVdupNP16() {
    val v: P16 = 64u
    val e = Simd<UShort>(listOf(64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort()))
    val r = u16x4.from(vdupNP16(v))
    check(r == e)
}

public fun testVdupNF32() {
    val v: Float = 64.0f
    val e = Simd<Float>(listOf(64.0f.toFloat(), 64.0f.toFloat()))
    val r = f32x2.from(vdupNF32(v))
    check(r == e)
}

public fun testVldrqP128() {
    val v: List<P128> = listOf(1, 2)
    val e: P128 = U128(0x0uL, 0x2uL)
    val r: P128 = vldrqP128(((v).subList(1, (v).size)).asPtr())
    check(r == e)
}

public fun testVstrqP128() {
    val v: List<P128> = listOf(1, 2)
    val e: P128 = U128(0x0uL, 0x2uL)
    val r: P128  = U128(0x0uL, 0x1uL)
    run {
        vstrqP128(r, v[1])
    }
    check(r == e)
}

public fun testVmovNS8() {
    val v: Byte = 64
    val e = Simd<Byte>(listOf(64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte()))
    val r = i8x8.from(vmovNS8(v))
    check(r == e)
}

public fun testVmovNS16() {
    val v: Short = 64
    val e = Simd<Short>(listOf(64.toShort(), 64.toShort(), 64.toShort(), 64.toShort()))
    val r = i16x4.from(vmovNS16(v))
    check(r == e)
}

public fun testVmovNS32() {
    val v: Int = 64
    val e = Simd<Int>(listOf(64, 64))
    val r = i32x2.from(vmovNS32(v))
    check(r == e)
}

public fun testVmovNS64() {
    val v: Long = 64L
    val e = Simd<Long>(listOf(64L))
    val r = i64x1.from(vmovNS64(v))
    check(r == e)
}

public fun testVmovNU8() {
    val v: UByte = 64u
    val e = Simd<UByte>(listOf(64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte()))
    val r = u8x8.from(vmovNU8(v))
    check(r == e)
}

public fun testVmovNU16() {
    val v: UShort = 64u
    val e = Simd<UShort>(listOf(64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort()))
    val r = u16x4.from(vmovNU16(v))
    check(r == e)
}

public fun testVmovNU32() {
    val v: UInt = 64u
    val e = Simd<UInt>(listOf(64u, 64u))
    val r = u32x2.from(vmovNU32(v))
    check(r == e)
}

public fun testVmovNU64() {
    val v: ULong = 64uL
    val e = Simd<ULong>(listOf(64uL))
    val r = u64x1.from(vmovNU64(v))
    check(r == e)
}

public fun testVmovNP8() {
    val v: P8 = 64u
    val e = Simd<UByte>(listOf(64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte()))
    val r = u8x8.from(vmovNP8(v))
    check(r == e)
}

public fun testVmovNP16() {
    val v: P16 = 64u
    val e = Simd<UShort>(listOf(64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort()))
    val r = u16x4.from(vmovNP16(v))
    check(r == e)
}

public fun testVmovNF32() {
    val v: Float = 64.0f
    val e = Simd<Float>(listOf(64.0f.toFloat(), 64.0f.toFloat()))
    val r = f32x2.from(vmovNF32(v))
    check(r == e)
}

public fun testVmovqNS8() {
    val v: Byte = 64
    val e = Simd<Byte>(listOf(64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte(), 64.toByte()))
    val r = i8x16.from(vmovqNS8(v))
    check(r == e)
}

public fun testVmovqNS16() {
    val v: Short = 64
    val e = Simd<Short>(listOf(64.toShort(), 64.toShort(), 64.toShort(), 64.toShort(), 64.toShort(), 64.toShort(), 64.toShort(), 64.toShort()))
    val r = i16x8.from(vmovqNS16(v))
    check(r == e)
}

public fun testVmovqNS32() {
    val v: Int = 64
    val e = Simd<Int>(listOf(64, 64, 64, 64))
    val r = i32x4.from(vmovqNS32(v))
    check(r == e)
}

public fun testVmovqNS64() {
    val v: Long = 64L
    val e = Simd<Long>(listOf(64L, 64L))
    val r = i64x2.from(vmovqNS64(v))
    check(r == e)
}

public fun testVmovqNU8() {
    val v: UByte = 64u
    val e = Simd<UByte>(listOf(64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte()))
    val r = u8x16.from(vmovqNU8(v))
    check(r == e)
}

public fun testVmovqNU16() {
    val v: UShort = 64u
    val e = Simd<UShort>(listOf(64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort()))
    val r = u16x8.from(vmovqNU16(v))
    check(r == e)
}

public fun testVmovqNU32() {
    val v: UInt = 64u
    val e = Simd<UInt>(listOf(64u, 64u, 64u, 64u))
    val r = u32x4.from(vmovqNU32(v))
    check(r == e)
}

public fun testVmovqNU64() {
    val v: ULong = 64uL
    val e = Simd<ULong>(listOf(64uL, 64uL))
    val r = u64x2.from(vmovqNU64(v))
    check(r == e)
}

public fun testVmovqNP8() {
    val v: P8 = 64u
    val e = Simd<UByte>(listOf(64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte(), 64u.toUByte()))
    val r = u8x16.from(vmovqNP8(v))
    check(r == e)
}

public fun testVmovqNP16() {
    val v: P16 = 64u
    val e = Simd<UShort>(listOf(64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort(), 64u.toUShort()))
    val r = u16x8.from(vmovqNP16(v))
    check(r == e)
}

public fun testVmovqNF32() {
    val v: Float = 64.0f
    val e = Simd<Float>(listOf(64.0f.toFloat(), 64.0f.toFloat(), 64.0f.toFloat(), 64.0f.toFloat()))
    val r = f32x4.from(vmovqNF32(v))
    check(r == e)
}

public fun testVaddS8() {
    testAriS8({ i, j -> vaddS8(i, j) }, { a: Byte, b: Byte ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddqS8() {
    testqAriS8({ i, j -> vaddqS8(i, j) }, { a: Byte, b: Byte ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddS16() {
    testAriS16({ i, j -> vaddS16(i, j) }, { a: Short, b: Short ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddqS16() {
    testqAriS16({ i, j -> vaddqS16(i, j) }, { a: Short, b: Short ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddS32() {
    testAriS32({ i, j -> vaddS32(i, j) }, { a: Int, b: Int ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddqS32() {
    testqAriS32({ i, j -> vaddqS32(i, j) }, { a: Int, b: Int ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddU8() {
    testAriU8({ i, j -> vaddU8(i, j) }, { a: UByte, b: UByte ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddqU8() {
    testqAriU8({ i, j -> vaddqU8(i, j) }, { a: UByte, b: UByte ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddU16() {
    testAriU16({ i, j -> vaddU16(i, j) }, { a: UShort, b: UShort ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddqU16() {
    testqAriU16({ i, j -> vaddqU16(i, j) }, { a: UShort, b: UShort ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddU32() {
    testAriU32({ i, j -> vaddU32(i, j) }, { a: UInt, b: UInt ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddqU32() {
    testqAriU32({ i, j -> vaddqU32(i, j) }, { a: UInt, b: UInt ->
        a.overflowingAdd(b).first
    })
}

public fun testVaddF32() {
    testAriF32({ i, j -> vaddF32(i, j) }, { a: Float, b: Float ->
        a + b
    })
}

public fun testVaddqF32() {
    testqAriF32({ i, j -> vaddqF32(i, j) }, { a: Float, b: Float ->
        a + b
    })
}

public fun testVaddlS8() {
    val v = Byte.MAX_VALUE
    val a = Simd<Byte>(listOf(v, v, v, v, v, v, v, v))
    val v1 = 2 * (v.toShort())
    val e = Simd<Short>(listOf(v1, v1, v1, v1, v1, v1, v1, v1))
    val r = i16x8.from(vaddlS8(a.into(), a.into()))
    check(r == e)
}

public fun testVaddlS16() {
    val v = Short.MAX_VALUE
    val a = Simd<Short>(listOf(v, v, v, v))
    val v1 = 2 * (v.toInt())
    val e = Simd<Int>(listOf(v1, v1, v1, v1))
    val r = i32x4.from(vaddlS16(a.into(), a.into()))
    check(r == e)
}

public fun testVaddlS32() {
    val v = Int.MAX_VALUE
    val a = Simd<Int>(listOf(v, v))
    val v1 = 2L * (v.toLong())
    val e = Simd<Long>(listOf(v1, v1))
    val r = i64x2.from(vaddlS32(a.into(), a.into()))
    check(r == e)
}

public fun testVaddlU8() {
    val v = UByte.MAX_VALUE
    val a = Simd<UByte>(listOf(v, v, v, v, v, v, v, v))
    val v1 = 2u * (v.toUShort())
    val e = Simd<UShort>(listOf(v1, v1, v1, v1, v1, v1, v1, v1))
    val r = u16x8.from(vaddlU8(a.into(), a.into()))
    check(r == e)
}

public fun testVaddlU16() {
    val v = UShort.MAX_VALUE
    val a = Simd<UShort>(listOf(v, v, v, v))
    val v1 = 2u * (v.toUInt())
    val e = Simd<UInt>(listOf(v1, v1, v1, v1))
    val r = u32x4.from(vaddlU16(a.into(), a.into()))
    check(r == e)
}

public fun testVaddlU32() {
    val v = UInt.MAX_VALUE
    val a = Simd<UInt>(listOf(v, v))
    val v1 = 2uL * (v.toULong())
    val e = Simd<ULong>(listOf(v1, v1))
    val r = u64x2.from(vaddlU32(a.into(), a.into()))
    check(r == e)
}

public fun testVaddwS8() {
    val x = Short.MAX_VALUE
    val a = Simd<Short>(listOf(x, 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort()))
    val y = Byte.MAX_VALUE
    val b = Simd<Byte>(listOf(y, y, y, y, y, y, y, y))
    val y1 = y.toShort()
    val e = Simd<Short>(listOf((x + (y1.toShort())).toShort(), (1 + y1).toShort(), (2 + y1).toShort(), (3 + y1).toShort(), (4 + y1).toShort(), (5 + y1).toShort(), (6 + y1).toShort(), (7 + y1).toShort()))
    val r = i16x8.from(vaddwS8(a.into(), b.into()))
    check(r == e)
}

public fun testVaddwS16() {
    val x = Int.MAX_VALUE
    val a = Simd<Int>(listOf(x, 1, 2, 3))
    val y = Short.MAX_VALUE
    val b = Simd<Short>(listOf(y, y, y, y))
    val y1 = y.toInt()
    val e = Simd<Int>(listOf((x + (y1.toInt())), 1 + y1, 2 + y1, 3 + y1))
    val r = i32x4.from(vaddwS16(a.into(), b.into()))
    check(r == e)
}

public fun testVaddwS32() {
    val x = Long.MAX_VALUE
    val a = Simd<Long>(listOf(x, 1L))
    val y = Int.MAX_VALUE
    val b = Simd<Int>(listOf(y, y))
    val y1 = y.toLong()
    val e = Simd<Long>(listOf((x + (y1.toLong())), 1L + y1))
    val r = i64x2.from(vaddwS32(a.into(), b.into()))
    check(r == e)
}

public fun testVaddwU8() {
    val x = UShort.MAX_VALUE
    val a = Simd<UShort>(listOf(x, 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    val y = UByte.MAX_VALUE
    val b = Simd<UByte>(listOf(y, y, y, y, y, y, y, y))
    val y1 = y.toUShort()
    val e = Simd<UShort>(listOf((x + (y1.toUShort())).toUShort(), (1u + y1).toUShort(), (2u + y1).toUShort(), (3u + y1).toUShort(), (4u + y1).toUShort(), (5u + y1).toUShort(), (6u + y1).toUShort(), (7u + y1).toUShort()))
    val r = u16x8.from(vaddwU8(a.into(), b.into()))
    check(r == e)
}

public fun testVaddwU16() {
    val x = UInt.MAX_VALUE
    val a = Simd<UInt>(listOf(x, 1u, 2u, 3u))
    val y = UShort.MAX_VALUE
    val b = Simd<UShort>(listOf(y, y, y, y))
    val y1 = y.toUInt()
    val e = Simd<UInt>(listOf((x + (y1.toUInt())), 1u + y1, 2u + y1, 3u + y1))
    val r = u32x4.from(vaddwU16(a.into(), b.into()))
    check(r == e)
}

public fun testVaddwU32() {
    val x = ULong.MAX_VALUE
    val a = Simd<ULong>(listOf(x, 1uL))
    val y = UInt.MAX_VALUE
    val b = Simd<UInt>(listOf(y, y))
    val y1 = y.toULong()
    val e = Simd<ULong>(listOf((x + (y1.toULong())), 1uL + y1))
    val r = u64x2.from(vaddwU32(a.into(), b.into()))
    check(r == e)
}

public fun testVmvnS8() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte()))
    val e = Simd<Byte>(listOf((-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte()))
    val r = i8x8.from(vmvnS8(a.into()))
    check(r == e)
}

public fun testVmvnqS8() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte()))
    val e = Simd<Byte>(listOf((-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte(), (-16).toByte()))
    val r = i8x16.from(vmvnqS8(a.into()))
    check(r == e)
}

public fun testVmvnS16() {
    val a = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort()))
    val e = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort()))
    val r = i16x4.from(vmvnS16(a.into()))
    check(r == e)
}

public fun testVmvnqS16() {
    val a = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort()))
    val e = Simd<Short>(listOf((-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-5).toShort(), (-6).toShort(), (-7).toShort(), (-8).toShort()))
    val r = i16x8.from(vmvnqS16(a.into()))
    check(r == e)
}

public fun testVmvnS32() {
    val a = Simd<Int>(listOf(0, 1))
    val e = Simd<Int>(listOf(-1, -2))
    val r = i32x2.from(vmvnS32(a.into()))
    check(r == e)
}

public fun testVmvnqS32() {
    val a = Simd<Int>(listOf(0, 1, 2, 3))
    val e = Simd<Int>(listOf(-1, -2, -3, -4))
    val r = i32x4.from(vmvnqS32(a.into()))
    check(r == e)
}

public fun testVmvnU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val e = Simd<UByte>(listOf(255u.toUByte(), 254u.toUByte(), 253u.toUByte(), 252u.toUByte(), 251u.toUByte(), 250u.toUByte(), 249u.toUByte(), 248u.toUByte()))
    val r = u8x8.from(vmvnU8(a.into()))
    check(r == e)
}

public fun testVmvnqU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val e = Simd<UByte>(listOf(255u.toUByte(), 254u.toUByte(), 253u.toUByte(), 252u.toUByte(), 251u.toUByte(), 250u.toUByte(), 249u.toUByte(), 248u.toUByte(), 247u.toUByte(), 246u.toUByte(), 245u.toUByte(), 244u.toUByte(), 243u.toUByte(), 242u.toUByte(), 241u.toUByte(), 240u.toUByte()))
    val r = u8x16.from(vmvnqU8(a.into()))
    check(r == e)
}

public fun testVmvnU16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort()))
    val e = Simd<UShort>(listOf(65535u.toUShort(), 65534u.toUShort(), 65533u.toUShort(), 65532u.toUShort()))
    val r = u16x4.from(vmvnU16(a.into()))
    check(r == e)
}

public fun testVmvnqU16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    val e = Simd<UShort>(listOf(65535u.toUShort(), 65534u.toUShort(), 65533u.toUShort(), 65532u.toUShort(), 65531u.toUShort(), 65530u.toUShort(), 65529u.toUShort(), 65528u.toUShort()))
    val r = u16x8.from(vmvnqU16(a.into()))
    check(r == e)
}

public fun testVmvnU32() {
    val a = Simd<UInt>(listOf(0u, 1u))
    val e = Simd<UInt>(listOf(4294967295u, 4294967294u))
    val r = u32x2.from(vmvnU32(a.into()))
    check(r == e)
}

public fun testVmvnqU32() {
    val a = Simd<UInt>(listOf(0u, 1u, 2u, 3u))
    val e = Simd<UInt>(listOf(4294967295u, 4294967294u, 4294967293u, 4294967292u))
    val r = u32x4.from(vmvnqU32(a.into()))
    check(r == e)
}

public fun testVmvnP8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val e = Simd<UByte>(listOf(255u.toUByte(), 254u.toUByte(), 253u.toUByte(), 252u.toUByte(), 251u.toUByte(), 250u.toUByte(), 249u.toUByte(), 248u.toUByte()))
    val r = u8x8.from(vmvnP8(a.into()))
    check(r == e)
}

public fun testVmvnqP8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val e = Simd<UByte>(listOf(255u.toUByte(), 254u.toUByte(), 253u.toUByte(), 252u.toUByte(), 251u.toUByte(), 250u.toUByte(), 249u.toUByte(), 248u.toUByte(), 247u.toUByte(), 246u.toUByte(), 245u.toUByte(), 244u.toUByte(), 243u.toUByte(), 242u.toUByte(), 241u.toUByte(), 240u.toUByte()))
    val r = u8x16.from(vmvnqP8(a.into()))
    check(r == e)
}

public fun testVbicS8() {
    val a = Simd<Byte>(listOf(0.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte()))
    val b = Simd<Byte>(listOf(1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte()))
    val e = Simd<Byte>(listOf(0.toByte(), (-2).toByte(), (-2).toByte(), (-4).toByte(), (-4).toByte(), (-6).toByte(), (-6).toByte(), (-8).toByte()))
    val r = i8x8.from(vbicS8(a.into(), b.into()))
    check(r == e)
}

public fun testVbicqS8() {
    val a = Simd<Byte>(listOf(0.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte()))
    val b = Simd<Byte>(listOf(1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte()))
    val e = Simd<Byte>(listOf(0.toByte(), (-2).toByte(), (-2).toByte(), (-4).toByte(), (-4).toByte(), (-6).toByte(), (-6).toByte(), (-8).toByte(), (-8).toByte(), (-10).toByte(), (-10).toByte(), (-12).toByte(), (-12).toByte(), (-14).toByte(), (-14).toByte(), (-16).toByte()))
    val r = i8x16.from(vbicqS8(a.into(), b.into()))
    check(r == e)
}

public fun testVbicS16() {
    val a = Simd<Short>(listOf(0.toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort()))
    val b = Simd<Short>(listOf(1.toShort(), 1.toShort(), 1.toShort(), 1.toShort()))
    val e = Simd<Short>(listOf(0.toShort(), (-2).toShort(), (-2).toShort(), (-4).toShort()))
    val r = i16x4.from(vbicS16(a.into(), b.into()))
    check(r == e)
}

public fun testVbicqS16() {
    val a = Simd<Short>(listOf(0.toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-5).toShort(), (-6).toShort(), (-7).toShort()))
    val b = Simd<Short>(listOf(1.toShort(), 1.toShort(), 1.toShort(), 1.toShort(), 1.toShort(), 1.toShort(), 1.toShort(), 1.toShort()))
    val e = Simd<Short>(listOf(0.toShort(), (-2).toShort(), (-2).toShort(), (-4).toShort(), (-4).toShort(), (-6).toShort(), (-6).toShort(), (-8).toShort()))
    val r = i16x8.from(vbicqS16(a.into(), b.into()))
    check(r == e)
}

public fun testVbicS32() {
    val a = Simd<Int>(listOf(0, -1))
    val b = Simd<Int>(listOf(1, 1))
    val e = Simd<Int>(listOf(0, -2))
    val r = i32x2.from(vbicS32(a.into(), b.into()))
    check(r == e)
}

public fun testVbicqS32() {
    val a = Simd<Int>(listOf(0, -1, -2, -3))
    val b = Simd<Int>(listOf(1, 1, 1, 1))
    val e = Simd<Int>(listOf(0, -2, -2, -4))
    val r = i32x4.from(vbicqS32(a.into(), b.into()))
    check(r == e)
}

public fun testVbicS64() {
    val a = Simd<Long>(listOf(-1L))
    val b = Simd<Long>(listOf(1L))
    val e = Simd<Long>(listOf(-2L))
    val r = i64x1.from(vbicS64(a.into(), b.into()))
    check(r == e)
}

public fun testVbicqS64() {
    val a = Simd<Long>(listOf(0L, -1L))
    val b = Simd<Long>(listOf(1L, 1L))
    val e = Simd<Long>(listOf(0L, -2L))
    val r = i64x2.from(vbicqS64(a.into(), b.into()))
    check(r == e)
}

public fun testVbicU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte()))
    val e = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 2u.toUByte(), 4u.toUByte(), 4u.toUByte(), 6u.toUByte(), 6u.toUByte()))
    val r = u8x8.from(vbicU8(a.into(), b.into()))
    check(r == e)
}

public fun testVbicqU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte()))
    val e = Simd<UByte>(listOf(0u.toUByte(), 0u.toUByte(), 2u.toUByte(), 2u.toUByte(), 4u.toUByte(), 4u.toUByte(), 6u.toUByte(), 6u.toUByte(), 8u.toUByte(), 8u.toUByte(), 10u.toUByte(), 10u.toUByte(), 12u.toUByte(), 12u.toUByte(), 14u.toUByte(), 14u.toUByte()))
    val r = u8x16.from(vbicqU8(a.into(), b.into()))
    check(r == e)
}

public fun testVbicU16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort()))
    val b = Simd<UShort>(listOf(1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort()))
    val e = Simd<UShort>(listOf(0u.toUShort(), 0u.toUShort(), 2u.toUShort(), 2u.toUShort()))
    val r = u16x4.from(vbicU16(a.into(), b.into()))
    check(r == e)
}

public fun testVbicqU16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    val b = Simd<UShort>(listOf(1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort()))
    val e = Simd<UShort>(listOf(0u.toUShort(), 0u.toUShort(), 2u.toUShort(), 2u.toUShort(), 4u.toUShort(), 4u.toUShort(), 6u.toUShort(), 6u.toUShort()))
    val r = u16x8.from(vbicqU16(a.into(), b.into()))
    check(r == e)
}

public fun testVbicU32() {
    val a = Simd<UInt>(listOf(0u, 1u))
    val b = Simd<UInt>(listOf(1u, 1u))
    val e = Simd<UInt>(listOf(0u, 0u))
    val r = u32x2.from(vbicU32(a.into(), b.into()))
    check(r == e)
}

public fun testVbicqU32() {
    val a = Simd<UInt>(listOf(0u, 1u, 2u, 3u))
    val b = Simd<UInt>(listOf(1u, 1u, 1u, 1u))
    val e = Simd<UInt>(listOf(0u, 0u, 2u, 2u))
    val r = u32x4.from(vbicqU32(a.into(), b.into()))
    check(r == e)
}

public fun testVbicU64() {
    val a = Simd<ULong>(listOf(1uL))
    val b = Simd<ULong>(listOf(1uL))
    val e = Simd<ULong>(listOf(0uL))
    val r = u64x1.from(vbicU64(a.into(), b.into()))
    check(r == e)
}

public fun testVbicqU64() {
    val a = Simd<ULong>(listOf(0uL, 1uL))
    val b = Simd<ULong>(listOf(1uL, 1uL))
    val e = Simd<ULong>(listOf(0uL, 0uL))
    val r = u64x2.from(vbicqU64(a.into(), b.into()))
    check(r == e)
}

public fun testVbslS8() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 1u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte()))
    val b = Simd<Byte>(listOf(Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE))
    val c = Simd<Byte>(listOf(Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE))
    val e = Simd<Byte>(listOf(Byte.MAX_VALUE, (Byte.MIN_VALUE.toInt() or (1.toByte()).toInt()).toByte(), Byte.MAX_VALUE, (Byte.MIN_VALUE.toInt() or (2.toByte()).toInt()).toByte(), Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE))
    val r = i8x8.from(vbslS8(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslS16() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 0u.toUShort(), 1u.toUShort(), 2u.toUShort()))
    val b = Simd<Short>(listOf(Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE))
    val c = Simd<Short>(listOf(Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE))
    val e = Simd<Short>(listOf(Short.MAX_VALUE, Short.MIN_VALUE, (Short.MIN_VALUE.toInt() or (1.toShort()).toInt()).toShort(), (Short.MIN_VALUE.toInt() or (2.toShort()).toInt()).toShort()))
    val r = i16x4.from(vbslS16(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslS32() {
    val a = Simd<UInt>(listOf(UInt.MAX_VALUE, 1u))
    val b = Simd<Int>(listOf(Int.MAX_VALUE, Int.MAX_VALUE))
    val c = Simd<Int>(listOf(Int.MIN_VALUE, Int.MIN_VALUE))
    val e = Simd<Int>(listOf(Int.MAX_VALUE, Int.MIN_VALUE or 1))
    val r = i32x2.from(vbslS32(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslS64() {
    val a = Simd<ULong>(listOf(1uL))
    val b = Simd<Long>(listOf(Long.MAX_VALUE))
    val c = Simd<Long>(listOf(Long.MIN_VALUE))
    val e = Simd<Long>(listOf(Long.MIN_VALUE or 1L))
    val r = i64x1.from(vbslS64(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslU8() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 1u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte()))
    val b = Simd<UByte>(listOf(UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE))
    val c = Simd<UByte>(listOf(UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE))
    val e = Simd<UByte>(listOf(UByte.MAX_VALUE, 1u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE))
    val r = u8x8.from(vbslU8(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslU16() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 0u.toUShort(), 1u.toUShort(), 2u.toUShort()))
    val b = Simd<UShort>(listOf(UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE))
    val c = Simd<UShort>(listOf(UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE))
    val e = Simd<UShort>(listOf(UShort.MAX_VALUE, 0u.toUShort(), 1u.toUShort(), 2u.toUShort()))
    val r = u16x4.from(vbslU16(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslU32() {
    val a = Simd<UInt>(listOf(UInt.MAX_VALUE, 2u))
    val b = Simd<UInt>(listOf(UInt.MAX_VALUE, UInt.MAX_VALUE))
    val c = Simd<UInt>(listOf(UInt.MIN_VALUE, UInt.MIN_VALUE))
    val e = Simd<UInt>(listOf(UInt.MAX_VALUE, 2u))
    val r = u32x2.from(vbslU32(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslU64() {
    val a = Simd<ULong>(listOf(2uL))
    val b = Simd<ULong>(listOf(ULong.MAX_VALUE))
    val c = Simd<ULong>(listOf(ULong.MIN_VALUE))
    val e = Simd<ULong>(listOf(2uL))
    val r = u64x1.from(vbslU64(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslF32() {
    val a = Simd<UInt>(listOf(1u, 0x80000000u))
    val b = Simd<Float>(listOf(8388609.0f.toFloat(), -1.23f))
    val c = Simd<Float>(listOf(2097152.0f.toFloat(), 2.34f))
    val e = Simd<Float>(listOf(2097152.25f, -2.34f))
    val r = f32x2.from(vbslF32(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslP8() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 1u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte()))
    val b = Simd<UByte>(listOf(UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE))
    val c = Simd<UByte>(listOf(UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE))
    val e = Simd<UByte>(listOf(UByte.MAX_VALUE, 1u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE))
    val r = u8x8.from(vbslP8(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslP16() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 0u.toUShort(), 1u.toUShort(), 2u.toUShort()))
    val b = Simd<UShort>(listOf(UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE))
    val c = Simd<UShort>(listOf(UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE))
    val e = Simd<UShort>(listOf(UShort.MAX_VALUE, 0u.toUShort(), 1u.toUShort(), 2u.toUShort()))
    val r = u16x4.from(vbslP16(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqS8() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 1u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte()))
    val b = Simd<Byte>(listOf(Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE))
    val c = Simd<Byte>(listOf(Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE))
    val e = Simd<Byte>(listOf(Byte.MAX_VALUE, (Byte.MIN_VALUE.toInt() or (1.toByte()).toInt()).toByte(), Byte.MAX_VALUE, (Byte.MIN_VALUE.toInt() or (2.toByte()).toInt()).toByte(), Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE))
    val r = i8x16.from(vbslqS8(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqS16() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 1u.toUShort(), UShort.MAX_VALUE, 2u.toUShort(), UShort.MAX_VALUE, 0u.toUShort(), UShort.MAX_VALUE, 0u.toUShort()))
    val b = Simd<Short>(listOf(Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE, Short.MAX_VALUE))
    val c = Simd<Short>(listOf(Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE))
    val e = Simd<Short>(listOf(Short.MAX_VALUE, (Short.MIN_VALUE.toInt() or (1.toShort()).toInt()).toShort(), Short.MAX_VALUE, (Short.MIN_VALUE.toInt() or (2.toShort()).toInt()).toShort(), Short.MAX_VALUE, Short.MIN_VALUE, Short.MAX_VALUE, Short.MIN_VALUE))
    val r = i16x8.from(vbslqS16(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqS32() {
    val a = Simd<UInt>(listOf(UInt.MAX_VALUE, 1u, UInt.MAX_VALUE, 2u))
    val b = Simd<Int>(listOf(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE))
    val c = Simd<Int>(listOf(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE))
    val e = Simd<Int>(listOf(Int.MAX_VALUE, Int.MIN_VALUE or 1, Int.MAX_VALUE, Int.MIN_VALUE or 2))
    val r = i32x4.from(vbslqS32(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqS64() {
    val a = Simd<ULong>(listOf(ULong.MAX_VALUE, 1uL))
    val b = Simd<Long>(listOf(Long.MAX_VALUE, Long.MAX_VALUE))
    val c = Simd<Long>(listOf(Long.MIN_VALUE, Long.MIN_VALUE))
    val e = Simd<Long>(listOf(Long.MAX_VALUE, Long.MIN_VALUE or 1L))
    val r = i64x2.from(vbslqS64(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqU8() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 1u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte()))
    val b = Simd<UByte>(listOf(UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE))
    val c = Simd<UByte>(listOf(UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE))
    val e = Simd<UByte>(listOf(UByte.MAX_VALUE, 1u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE))
    val r = u8x16.from(vbslqU8(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqU16() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 1u.toUShort(), UShort.MAX_VALUE, 2u.toUShort(), UShort.MAX_VALUE, 0u.toUShort(), UShort.MAX_VALUE, 0u.toUShort()))
    val b = Simd<UShort>(listOf(UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE))
    val c = Simd<UShort>(listOf(UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE))
    val e = Simd<UShort>(listOf(UShort.MAX_VALUE, 1u.toUShort(), UShort.MAX_VALUE, 2u.toUShort(), UShort.MAX_VALUE, UShort.MIN_VALUE, UShort.MAX_VALUE, UShort.MIN_VALUE))
    val r = u16x8.from(vbslqU16(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqU32() {
    val a = Simd<UInt>(listOf(UInt.MAX_VALUE, 1u, UInt.MAX_VALUE, 2u))
    val b = Simd<UInt>(listOf(UInt.MAX_VALUE, UInt.MAX_VALUE, UInt.MAX_VALUE, UInt.MAX_VALUE))
    val c = Simd<UInt>(listOf(UInt.MIN_VALUE, UInt.MIN_VALUE, UInt.MIN_VALUE, UInt.MIN_VALUE))
    val e = Simd<UInt>(listOf(UInt.MAX_VALUE, 1u, UInt.MAX_VALUE, 2u))
    val r = u32x4.from(vbslqU32(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqU64() {
    val a = Simd<ULong>(listOf(ULong.MAX_VALUE, 1uL))
    val b = Simd<ULong>(listOf(ULong.MAX_VALUE, ULong.MAX_VALUE))
    val c = Simd<ULong>(listOf(ULong.MIN_VALUE, ULong.MIN_VALUE))
    val e = Simd<ULong>(listOf(ULong.MAX_VALUE, 1uL))
    val r = u64x2.from(vbslqU64(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqF32() {
    val a = Simd<UInt>(listOf(UInt.MAX_VALUE, 0u, 1u, 0x80000000u))
    val b = Simd<Float>(listOf(-1.23f, -1.23f, 8388609.0f.toFloat(), -1.23f))
    val c = Simd<Float>(listOf(2.34f, 2.34f, 2097152.0f.toFloat(), 2.34f))
    val e = Simd<Float>(listOf(-1.23f, 2.34f, 2097152.25f, -2.34f))
    val r = f32x4.from(vbslqF32(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqP8() {
    val a = Simd<UByte>(listOf(UByte.MAX_VALUE, 1u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte(), UByte.MAX_VALUE, 0u.toUByte()))
    val b = Simd<UByte>(listOf(UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE, UByte.MAX_VALUE))
    val c = Simd<UByte>(listOf(UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE, UByte.MIN_VALUE))
    val e = Simd<UByte>(listOf(UByte.MAX_VALUE, 1u.toUByte(), UByte.MAX_VALUE, 2u.toUByte(), UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE, UByte.MAX_VALUE, UByte.MIN_VALUE))
    val r = u8x16.from(vbslqP8(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVbslqP16() {
    val a = Simd<UShort>(listOf(UShort.MAX_VALUE, 1u.toUShort(), UShort.MAX_VALUE, 2u.toUShort(), UShort.MAX_VALUE, 0u.toUShort(), UShort.MAX_VALUE, 0u.toUShort()))
    val b = Simd<UShort>(listOf(UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE, UShort.MAX_VALUE))
    val c = Simd<UShort>(listOf(UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE, UShort.MIN_VALUE))
    val e = Simd<UShort>(listOf(UShort.MAX_VALUE, 1u.toUShort(), UShort.MAX_VALUE, 2u.toUShort(), UShort.MAX_VALUE, UShort.MIN_VALUE, UShort.MAX_VALUE, UShort.MIN_VALUE))
    val r = u16x8.from(vbslqP16(a.into(), b.into(), c.into()))
    check(r == e)
}

public fun testVornS8() {
    val a = Simd<Byte>(listOf(0.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte()))
    val b = Simd<Byte>(listOf((-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte()))
    val e = Simd<Byte>(listOf(1.toByte(), (-1).toByte(), (-1).toByte(), (-3).toByte(), (-3).toByte(), (-5).toByte(), (-5).toByte(), (-7).toByte()))
    val r = i8x8.from(vornS8(a.into(), b.into()))
    check(r == e)
}

public fun testVornqS8() {
    val a = Simd<Byte>(listOf(0.toByte(), (-1).toByte(), (-2).toByte(), (-3).toByte(), (-4).toByte(), (-5).toByte(), (-6).toByte(), (-7).toByte(), (-8).toByte(), (-9).toByte(), (-10).toByte(), (-11).toByte(), (-12).toByte(), (-13).toByte(), (-14).toByte(), (-15).toByte()))
    val b = Simd<Byte>(listOf((-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte(), (-2).toByte()))
    val e = Simd<Byte>(listOf(1.toByte(), (-1).toByte(), (-1).toByte(), (-3).toByte(), (-3).toByte(), (-5).toByte(), (-5).toByte(), (-7).toByte(), (-7).toByte(), (-9).toByte(), (-9).toByte(), (-11).toByte(), (-11).toByte(), (-13).toByte(), (-13).toByte(), (-15).toByte()))
    val r = i8x16.from(vornqS8(a.into(), b.into()))
    check(r == e)
}

public fun testVornS16() {
    val a = Simd<Short>(listOf(0.toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort()))
    val b = Simd<Short>(listOf((-2).toShort(), (-2).toShort(), (-2).toShort(), (-2).toShort()))
    val e = Simd<Short>(listOf(1.toShort(), (-1).toShort(), (-1).toShort(), (-3).toShort()))
    val r = i16x4.from(vornS16(a.into(), b.into()))
    check(r == e)
}

public fun testVornqS16() {
    val a = Simd<Short>(listOf(0.toShort(), (-1).toShort(), (-2).toShort(), (-3).toShort(), (-4).toShort(), (-5).toShort(), (-6).toShort(), (-7).toShort()))
    val b = Simd<Short>(listOf((-2).toShort(), (-2).toShort(), (-2).toShort(), (-2).toShort(), (-2).toShort(), (-2).toShort(), (-2).toShort(), (-2).toShort()))
    val e = Simd<Short>(listOf(1.toShort(), (-1).toShort(), (-1).toShort(), (-3).toShort(), (-3).toShort(), (-5).toShort(), (-5).toShort(), (-7).toShort()))
    val r = i16x8.from(vornqS16(a.into(), b.into()))
    check(r == e)
}

public fun testVornS32() {
    val a = Simd<Int>(listOf(0, -1))
    val b = Simd<Int>(listOf(-2, -2))
    val e = Simd<Int>(listOf(1, -1))
    val r = i32x2.from(vornS32(a.into(), b.into()))
    check(r == e)
}

public fun testVornqS32() {
    val a = Simd<Int>(listOf(0, -1, -2, -3))
    val b = Simd<Int>(listOf(-2, -2, -2, -2))
    val e = Simd<Int>(listOf(1, -1, -1, -3))
    val r = i32x4.from(vornqS32(a.into(), b.into()))
    check(r == e)
}

public fun testVornS64() {
    val a = Simd<Long>(listOf(0L))
    val b = Simd<Long>(listOf(-2L))
    val e = Simd<Long>(listOf(1L))
    val r = i64x1.from(vornS64(a.into(), b.into()))
    check(r == e)
}

public fun testVornqS64() {
    val a = Simd<Long>(listOf(0L, -1L))
    val b = Simd<Long>(listOf(-2L, -2L))
    val e = Simd<Long>(listOf(1L, -1L))
    val r = i64x2.from(vornqS64(a.into(), b.into()))
    check(r == e)
}

public fun testVornU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val t = UByte.MAX_VALUE - 1u
    val b = Simd<UByte>(listOf(t, t, t, t, t, t, t, t))
    val e = Simd<UByte>(listOf(1u.toUByte(), 1u.toUByte(), 3u.toUByte(), 3u.toUByte(), 5u.toUByte(), 5u.toUByte(), 7u.toUByte(), 7u.toUByte()))
    val r = u8x8.from(vornU8(a.into(), b.into()))
    check(r == e)
}

public fun testVornqU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val t = UByte.MAX_VALUE - 1u
    val b = Simd<UByte>(listOf(t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t))
    val e = Simd<UByte>(listOf(1u.toUByte(), 1u.toUByte(), 3u.toUByte(), 3u.toUByte(), 5u.toUByte(), 5u.toUByte(), 7u.toUByte(), 7u.toUByte(), 9u.toUByte(), 9u.toUByte(), 11u.toUByte(), 11u.toUByte(), 13u.toUByte(), 13u.toUByte(), 15u.toUByte(), 15u.toUByte()))
    val r = u8x16.from(vornqU8(a.into(), b.into()))
    check(r == e)
}

public fun testVornU16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort()))
    val t = UShort.MAX_VALUE - 1u
    val b = Simd<UShort>(listOf(t, t, t, t))
    val e = Simd<UShort>(listOf(1u.toUShort(), 1u.toUShort(), 3u.toUShort(), 3u.toUShort()))
    val r = u16x4.from(vornU16(a.into(), b.into()))
    check(r == e)
}

public fun testVornqU16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    val t = UShort.MAX_VALUE - 1u
    val b = Simd<UShort>(listOf(t, t, t, t, t, t, t, t))
    val e = Simd<UShort>(listOf(1u.toUShort(), 1u.toUShort(), 3u.toUShort(), 3u.toUShort(), 5u.toUShort(), 5u.toUShort(), 7u.toUShort(), 7u.toUShort()))
    val r = u16x8.from(vornqU16(a.into(), b.into()))
    check(r == e)
}

public fun testVornU32() {
    val a = Simd<UInt>(listOf(0u, 1u))
    val t = UInt.MAX_VALUE - 1u
    val b = Simd<UInt>(listOf(t, t))
    val e = Simd<UInt>(listOf(1u, 1u))
    val r = u32x2.from(vornU32(a.into(), b.into()))
    check(r == e)
}

public fun testVornqU32() {
    val a = Simd<UInt>(listOf(0u, 1u, 2u, 3u))
    val t = UInt.MAX_VALUE - 1u
    val b = Simd<UInt>(listOf(t, t, t, t))
    val e = Simd<UInt>(listOf(1u, 1u, 3u, 3u))
    val r = u32x4.from(vornqU32(a.into(), b.into()))
    check(r == e)
}

public fun testVornU64() {
    val a = Simd<ULong>(listOf(0uL))
    val t = ULong.MAX_VALUE - 1uL
    val b = Simd<ULong>(listOf(t))
    val e = Simd<ULong>(listOf(1uL))
    val r = u64x1.from(vornU64(a.into(), b.into()))
    check(r == e)
}

public fun testVornqU64() {
    val a = Simd<ULong>(listOf(0uL, 1uL))
    val t = ULong.MAX_VALUE - 1uL
    val b = Simd<ULong>(listOf(t, t))
    val e = Simd<ULong>(listOf(1uL, 1uL))
    val r = u64x2.from(vornqU64(a.into(), b.into()))
    check(r == e)
}

public fun testVmovnS16() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort()))
    val e = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte()))
    val r = i8x8.from(vmovnS16(a.into()))
    check(r == e)
}

public fun testVmovnS32() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val e = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val r = i16x4.from(vmovnS32(a.into()))
    check(r == e)
}

public fun testVmovnS64() {
    val a = Simd<Long>(listOf(1L, 2L))
    val e = Simd<Int>(listOf(1, 2))
    val r = i32x2.from(vmovnS64(a.into()))
    check(r == e)
}

public fun testVmovnU16() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort()))
    val e = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte()))
    val r = u8x8.from(vmovnU16(a.into()))
    check(r == e)
}

public fun testVmovnU32() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val e = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val r = u16x4.from(vmovnU32(a.into()))
    check(r == e)
}

public fun testVmovnU64() {
    val a = Simd<ULong>(listOf(1uL, 2uL))
    val e = Simd<UInt>(listOf(1u, 2u))
    val r = u32x2.from(vmovnU64(a.into()))
    check(r == e)
}

public fun testVmovlS8() {
    val e = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort()))
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte()))
    val r = i16x8.from(vmovlS8(a.into()))
    check(r == e)
}

public fun testVmovlS16() {
    val e = Simd<Int>(listOf(1, 2, 3, 4))
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val r = i32x4.from(vmovlS16(a.into()))
    check(r == e)
}

public fun testVmovlS32() {
    val e = Simd<Long>(listOf(1L, 2L))
    val a = Simd<Int>(listOf(1, 2))
    val r = i64x2.from(vmovlS32(a.into()))
    check(r == e)
}

public fun testVmovlU8() {
    val e = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort()))
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte()))
    val r = u16x8.from(vmovlU8(a.into()))
    check(r == e)
}

public fun testVmovlU16() {
    val e = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val r = u32x4.from(vmovlU16(a.into()))
    check(r == e)
}

public fun testVmovlU32() {
    val e = Simd<ULong>(listOf(1uL, 2uL))
    val a = Simd<UInt>(listOf(1u, 2u))
    val r = u64x2.from(vmovlU32(a.into()))
    check(r == e)
}

public fun testVandS8() {
    testBitS8({ i, j -> vandS8(i, j) }, { a: Byte, b: Byte ->
        (a.toInt() and b.toInt()).toByte()
    })
}

public fun testVandqS8() {
    testqBitS8({ i, j -> vandqS8(i, j) }, { a: Byte, b: Byte ->
        (a.toInt() and b.toInt()).toByte()
    })
}

public fun testVandS16() {
    testBitS16({ i, j -> vandS16(i, j) }, { a: Short, b: Short ->
        (a.toInt() and b.toInt()).toShort()
    })
}

public fun testVandqS16() {
    testqBitS16({ i, j -> vandqS16(i, j) }, { a: Short, b: Short ->
        (a.toInt() and b.toInt()).toShort()
    })
}

public fun testVandS32() {
    testBitS32({ i, j -> vandS32(i, j) }, { a: Int, b: Int ->
        a and b
    })
}

public fun testVandqS32() {
    testqBitS32({ i, j -> vandqS32(i, j) }, { a: Int, b: Int ->
        a and b
    })
}

public fun testVandS64() {
    testBitS64({ i, j -> vandS64(i, j) }, { a: Long, b: Long ->
        a and b
    })
}

public fun testVandqS64() {
    testqBitS64({ i, j -> vandqS64(i, j) }, { a: Long, b: Long ->
        a and b
    })
}

public fun testVandU8() {
    testBitU8({ i, j -> vandU8(i, j) }, { a: UByte, b: UByte ->
        a and b
    })
}

public fun testVandqU8() {
    testqBitU8({ i, j -> vandqU8(i, j) }, { a: UByte, b: UByte ->
        a and b
    })
}

public fun testVandU16() {
    testBitU16({ i, j -> vandU16(i, j) }, { a: UShort, b: UShort ->
        a and b
    })
}

public fun testVandqU16() {
    testqBitU16({ i, j -> vandqU16(i, j) }, { a: UShort, b: UShort ->
        a and b
    })
}

public fun testVandU32() {
    testBitU32({ i, j -> vandU32(i, j) }, { a: UInt, b: UInt ->
        a and b
    })
}

public fun testVandqU32() {
    testqBitU32({ i, j -> vandqU32(i, j) }, { a: UInt, b: UInt ->
        a and b
    })
}

public fun testVandU64() {
    testBitU64({ i, j -> vandU64(i, j) }, { a: ULong, b: ULong ->
        a and b
    })
}

public fun testVandqU64() {
    testqBitU64({ i, j -> vandqU64(i, j) }, { a: ULong, b: ULong ->
        a and b
    })
}

public fun testVorrS8() {
    testBitS8({ i, j -> vorrS8(i, j) }, { a: Byte, b: Byte ->
        (a.toInt() or b.toInt()).toByte()
    })
}

public fun testVorrqS8() {
    testqBitS8({ i, j -> vorrqS8(i, j) }, { a: Byte, b: Byte ->
        (a.toInt() or b.toInt()).toByte()
    })
}

public fun testVorrS16() {
    testBitS16({ i, j -> vorrS16(i, j) }, { a: Short, b: Short ->
        (a.toInt() or b.toInt()).toShort()
    })
}

public fun testVorrqS16() {
    testqBitS16({ i, j -> vorrqS16(i, j) }, { a: Short, b: Short ->
        (a.toInt() or b.toInt()).toShort()
    })
}

public fun testVorrS32() {
    testBitS32({ i, j -> vorrS32(i, j) }, { a: Int, b: Int ->
        a or b
    })
}

public fun testVorrqS32() {
    testqBitS32({ i, j -> vorrqS32(i, j) }, { a: Int, b: Int ->
        a or b
    })
}

public fun testVorrS64() {
    testBitS64({ i, j -> vorrS64(i, j) }, { a: Long, b: Long ->
        a or b
    })
}

public fun testVorrqS64() {
    testqBitS64({ i, j -> vorrqS64(i, j) }, { a: Long, b: Long ->
        a or b
    })
}

public fun testVorrU8() {
    testBitU8({ i, j -> vorrU8(i, j) }, { a: UByte, b: UByte ->
        a or b
    })
}

public fun testVorrqU8() {
    testqBitU8({ i, j -> vorrqU8(i, j) }, { a: UByte, b: UByte ->
        a or b
    })
}

public fun testVorrU16() {
    testBitU16({ i, j -> vorrU16(i, j) }, { a: UShort, b: UShort ->
        a or b
    })
}

public fun testVorrqU16() {
    testqBitU16({ i, j -> vorrqU16(i, j) }, { a: UShort, b: UShort ->
        a or b
    })
}

public fun testVorrU32() {
    testBitU32({ i, j -> vorrU32(i, j) }, { a: UInt, b: UInt ->
        a or b
    })
}

public fun testVorrqU32() {
    testqBitU32({ i, j -> vorrqU32(i, j) }, { a: UInt, b: UInt ->
        a or b
    })
}

public fun testVorrU64() {
    testBitU64({ i, j -> vorrU64(i, j) }, { a: ULong, b: ULong ->
        a or b
    })
}

public fun testVorrqU64() {
    testqBitU64({ i, j -> vorrqU64(i, j) }, { a: ULong, b: ULong ->
        a or b
    })
}

public fun testVeorS8() {
    testBitS8({ i, j -> veorS8(i, j) }, { a: Byte, b: Byte ->
        (a.toInt() xor b.toInt()).toByte()
    })
}

public fun testVeorqS8() {
    testqBitS8({ i, j -> veorqS8(i, j) }, { a: Byte, b: Byte ->
        (a.toInt() xor b.toInt()).toByte()
    })
}

public fun testVeorS16() {
    testBitS16({ i, j -> veorS16(i, j) }, { a: Short, b: Short ->
        (a.toInt() xor b.toInt()).toShort()
    })
}

public fun testVeorqS16() {
    testqBitS16({ i, j -> veorqS16(i, j) }, { a: Short, b: Short ->
        (a.toInt() xor b.toInt()).toShort()
    })
}

public fun testVeorS32() {
    testBitS32({ i, j -> veorS32(i, j) }, { a: Int, b: Int ->
        a xor b
    })
}

public fun testVeorqS32() {
    testqBitS32({ i, j -> veorqS32(i, j) }, { a: Int, b: Int ->
        a xor b
    })
}

public fun testVeorS64() {
    testBitS64({ i, j -> veorS64(i, j) }, { a: Long, b: Long ->
        a xor b
    })
}

public fun testVeorqS64() {
    testqBitS64({ i, j -> veorqS64(i, j) }, { a: Long, b: Long ->
        a xor b
    })
}

public fun testVeorU8() {
    testBitU8({ i, j -> veorU8(i, j) }, { a: UByte, b: UByte ->
        a xor b
    })
}

public fun testVeorqU8() {
    testqBitU8({ i, j -> veorqU8(i, j) }, { a: UByte, b: UByte ->
        a xor b
    })
}

public fun testVeorU16() {
    testBitU16({ i, j -> veorU16(i, j) }, { a: UShort, b: UShort ->
        a xor b
    })
}

public fun testVeorqU16() {
    testqBitU16({ i, j -> veorqU16(i, j) }, { a: UShort, b: UShort ->
        a xor b
    })
}

public fun testVeorU32() {
    testBitU32({ i, j -> veorU32(i, j) }, { a: UInt, b: UInt ->
        a xor b
    })
}

public fun testVeorqU32() {
    testqBitU32({ i, j -> veorqU32(i, j) }, { a: UInt, b: UInt ->
        a xor b
    })
}

public fun testVeorU64() {
    testBitU64({ i, j -> veorU64(i, j) }, { a: ULong, b: ULong ->
        a xor b
    })
}

public fun testVeorqU64() {
    testqBitU64({ i, j -> veorqU64(i, j) }, { a: ULong, b: ULong ->
        a xor b
    })
}

public fun testVceqS8() {
    testCmpS8({ i, j -> vceqS8(i, j) }, { a: Byte, b: Byte ->
        if (a == b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVceqqS8() {
    testqCmpS8({ i, j -> vceqqS8(i, j) }, { a: Byte, b: Byte ->
        if (a == b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVceqS16() {
    testCmpS16({ i, j -> vceqS16(i, j) }, { a: Short, b: Short ->
        if (a == b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVceqqS16() {
    testqCmpS16({ i, j -> vceqqS16(i, j) }, { a: Short, b: Short ->
        if (a == b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVceqS32() {
    testCmpS32({ i, j -> vceqS32(i, j) }, { a: Int, b: Int ->
        if (a == b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVceqqS32() {
    testqCmpS32({ i, j -> vceqqS32(i, j) }, { a: Int, b: Int ->
        if (a == b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVceqU8() {
    testCmpU8({ i, j -> vceqU8(i, j) }, { a: UByte, b: UByte ->
        if (a == b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVceqqU8() {
    testqCmpU8({ i, j -> vceqqU8(i, j) }, { a: UByte, b: UByte ->
        if (a == b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVceqU16() {
    testCmpU16({ i, j -> vceqU16(i, j) }, { a: UShort, b: UShort ->
        if (a == b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVceqqU16() {
    testqCmpU16({ i, j -> vceqqU16(i, j) }, { a: UShort, b: UShort ->
        if (a == b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVceqU32() {
    testCmpU32({ i, j -> vceqU32(i, j) }, { a: UInt, b: UInt ->
        if (a == b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVceqqU32() {
    testqCmpU32({ i, j -> vceqqU32(i, j) }, { a: UInt, b: UInt ->
        if (a == b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVceqF32() {
    testCmpF32({ i, j -> vcgeF32(i, j) }, { a: Float, b: Float ->
        if (a == b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVceqqF32() {
    testqCmpF32({ i, j -> vcgeqF32(i, j) }, { a: Float, b: Float ->
        if (a == b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgtS8() {
    testCmpS8({ i, j -> vcgtS8(i, j) }, { a: Byte, b: Byte ->
        if (a > b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcgtqS8() {
    testqCmpS8({ i, j -> vcgtqS8(i, j) }, { a: Byte, b: Byte ->
        if (a > b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcgtS16() {
    testCmpS16({ i, j -> vcgtS16(i, j) }, { a: Short, b: Short ->
        if (a > b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgtqS16() {
    testqCmpS16({ i, j -> vcgtqS16(i, j) }, { a: Short, b: Short ->
        if (a > b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgtS32() {
    testCmpS32({ i, j -> vcgtS32(i, j) }, { a: Int, b: Int ->
        if (a > b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgtqS32() {
    testqCmpS32({ i, j -> vcgtqS32(i, j) }, { a: Int, b: Int ->
        if (a > b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgtU8() {
    testCmpU8({ i, j -> vcgtU8(i, j) }, { a: UByte, b: UByte ->
        if (a > b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcgtqU8() {
    testqCmpU8({ i, j -> vcgtqU8(i, j) }, { a: UByte, b: UByte ->
        if (a > b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcgtU16() {
    testCmpU16({ i, j -> vcgtU16(i, j) }, { a: UShort, b: UShort ->
        if (a > b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgtqU16() {
    testqCmpU16({ i, j -> vcgtqU16(i, j) }, { a: UShort, b: UShort ->
        if (a > b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgtU32() {
    testCmpU32({ i, j -> vcgtU32(i, j) }, { a: UInt, b: UInt ->
        if (a > b) {
            0xFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgtqU32() {
    testqCmpU32({ i, j -> vcgtqU32(i, j) }, { a: UInt, b: UInt ->
        if (a > b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgtF32() {
    testCmpF32({ i, j -> vcgtF32(i, j) }, { a: Float, b: Float ->
        if (a > b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgtqF32() {
    testqCmpF32({ i, j -> vcgtqF32(i, j) }, { a: Float, b: Float ->
        if (a > b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcltS8() {
    testCmpS8({ i, j -> vcltS8(i, j) }, { a: Byte, b: Byte ->
        if (a < b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcltqS8() {
    testqCmpS8({ i, j -> vcltqS8(i, j) }, { a: Byte, b: Byte ->
        if (a < b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcltS16() {
    testCmpS16({ i, j -> vcltS16(i, j) }, { a: Short, b: Short ->
        if (a < b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcltqS16() {
    testqCmpS16({ i, j -> vcltqS16(i, j) }, { a: Short, b: Short ->
        if (a < b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcltS32() {
    testCmpS32({ i, j -> vcltS32(i, j) }, { a: Int, b: Int ->
        if (a < b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcltqS32() {
    testqCmpS32({ i, j -> vcltqS32(i, j) }, { a: Int, b: Int ->
        if (a < b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcltU8() {
    testCmpU8({ i, j -> vcltU8(i, j) }, { a: UByte, b: UByte ->
        if (a < b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcltqU8() {
    testqCmpU8({ i, j -> vcltqU8(i, j) }, { a: UByte, b: UByte ->
        if (a < b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcltU16() {
    testCmpU16({ i, j -> vcltU16(i, j) }, { a: UShort, b: UShort ->
        if (a < b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcltqU16() {
    testqCmpU16({ i, j -> vcltqU16(i, j) }, { a: UShort, b: UShort ->
        if (a < b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcltU32() {
    testCmpU32({ i, j -> vcltU32(i, j) }, { a: UInt, b: UInt ->
        if (a < b) {
            0xFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcltqU32() {
    testqCmpU32({ i, j -> vcltqU32(i, j) }, { a: UInt, b: UInt ->
        if (a < b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcltF32() {
    testCmpF32({ i, j -> vcltF32(i, j) }, { a: Float, b: Float ->
        if (a < b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcltqF32() {
    testqCmpF32({ i, j -> vcltqF32(i, j) }, { a: Float, b: Float ->
        if (a < b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcleS8() {
    testCmpS8({ i, j -> vcleS8(i, j) }, { a: Byte, b: Byte ->
        if (a <= b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcleqS8() {
    testqCmpS8({ i, j -> vcleqS8(i, j) }, { a: Byte, b: Byte ->
        if (a <= b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcleS16() {
    testCmpS16({ i, j -> vcleS16(i, j) }, { a: Short, b: Short ->
        if (a <= b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcleqS16() {
    testqCmpS16({ i, j -> vcleqS16(i, j) }, { a: Short, b: Short ->
        if (a <= b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcleS32() {
    testCmpS32({ i, j -> vcleS32(i, j) }, { a: Int, b: Int ->
        if (a <= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcleqS32() {
    testqCmpS32({ i, j -> vcleqS32(i, j) }, { a: Int, b: Int ->
        if (a <= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcleU8() {
    testCmpU8({ i, j -> vcleU8(i, j) }, { a: UByte, b: UByte ->
        if (a <= b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcleqU8() {
    testqCmpU8({ i, j -> vcleqU8(i, j) }, { a: UByte, b: UByte ->
        if (a <= b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcleU16() {
    testCmpU16({ i, j -> vcleU16(i, j) }, { a: UShort, b: UShort ->
        if (a <= b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcleqU16() {
    testqCmpU16({ i, j -> vcleqU16(i, j) }, { a: UShort, b: UShort ->
        if (a <= b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcleU32() {
    testCmpU32({ i, j -> vcleU32(i, j) }, { a: UInt, b: UInt ->
        if (a <= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcleqU32() {
    testqCmpU32({ i, j -> vcleqU32(i, j) }, { a: UInt, b: UInt ->
        if (a <= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcleF32() {
    testCmpF32({ i, j -> vcleF32(i, j) }, { a: Float, b: Float ->
        if (a <= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcleqF32() {
    testqCmpF32({ i, j -> vcleqF32(i, j) }, { a: Float, b: Float ->
        if (a <= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgeS8() {
    testCmpS8({ i, j -> vcgeS8(i, j) }, { a: Byte, b: Byte ->
        if (a >= b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcgeqS8() {
    testqCmpS8({ i, j -> vcgeqS8(i, j) }, { a: Byte, b: Byte ->
        if (a >= b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcgeS16() {
    testCmpS16({ i, j -> vcgeS16(i, j) }, { a: Short, b: Short ->
        if (a >= b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgeqS16() {
    testqCmpS16({ i, j -> vcgeqS16(i, j) }, { a: Short, b: Short ->
        if (a >= b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgeS32() {
    testCmpS32({ i, j -> vcgeS32(i, j) }, { a: Int, b: Int ->
        if (a >= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgeqS32() {
    testqCmpS32({ i, j -> vcgeqS32(i, j) }, { a: Int, b: Int ->
        if (a >= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgeU8() {
    testCmpU8({ i, j -> vcgeU8(i, j) }, { a: UByte, b: UByte ->
        if (a >= b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcgeqU8() {
    testqCmpU8({ i, j -> vcgeqU8(i, j) }, { a: UByte, b: UByte ->
        if (a >= b) {
            0xFFu
        } else {
            0u
        }
    })
}

public fun testVcgeU16() {
    testCmpU16({ i, j -> vcgeU16(i, j) }, { a: UShort, b: UShort ->
        if (a >= b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgeqU16() {
    testqCmpU16({ i, j -> vcgeqU16(i, j) }, { a: UShort, b: UShort ->
        if (a >= b) {
            0xFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgeU32() {
    testCmpU32({ i, j -> vcgeU32(i, j) }, { a: UInt, b: UInt ->
        if (a >= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgeqU32() {
    testqCmpU32({ i, j -> vcgeqU32(i, j) }, { a: UInt, b: UInt ->
        if (a >= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgeF32() {
    testCmpF32({ i, j -> vcgeF32(i, j) }, { a: Float, b: Float ->
        if (a >= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVcgeqF32() {
    testqCmpF32({ i, j -> vcgeqF32(i, j) }, { a: Float, b: Float ->
        if (a >= b) {
            0xFFFFFFFFu
        } else {
            0u
        }
    })
}

public fun testVqsubS8() {
    testAriS8({ i, j -> vqsubS8(i, j) }, { a: Byte, b: Byte ->
        a.saturatingSub(b)
    })
}

public fun testVqsubqS8() {
    testqAriS8({ i, j -> vqsubqS8(i, j) }, { a: Byte, b: Byte ->
        a.saturatingSub(b)
    })
}

public fun testVqsubS16() {
    testAriS16({ i, j -> vqsubS16(i, j) }, { a: Short, b: Short ->
        a.saturatingSub(b)
    })
}

public fun testVqsubqS16() {
    testqAriS16({ i, j -> vqsubqS16(i, j) }, { a: Short, b: Short ->
        a.saturatingSub(b)
    })
}

public fun testVqsubS32() {
    testAriS32({ i, j -> vqsubS32(i, j) }, { a: Int, b: Int ->
        a.saturatingSub(b)
    })
}

public fun testVqsubqS32() {
    testqAriS32({ i, j -> vqsubqS32(i, j) }, { a: Int, b: Int ->
        a.saturatingSub(b)
    })
}

public fun testVqsubU8() {
    testAriU8({ i, j -> vqsubU8(i, j) }, { a: UByte, b: UByte ->
        a.saturatingSub(b)
    })
}

public fun testVqsubqU8() {
    testqAriU8({ i, j -> vqsubqU8(i, j) }, { a: UByte, b: UByte ->
        a.saturatingSub(b)
    })
}

public fun testVqsubU16() {
    testAriU16({ i, j -> vqsubU16(i, j) }, { a: UShort, b: UShort ->
        a.saturatingSub(b)
    })
}

public fun testVqsubqU16() {
    testqAriU16({ i, j -> vqsubqU16(i, j) }, { a: UShort, b: UShort ->
        a.saturatingSub(b)
    })
}

public fun testVqsubU32() {
    testAriU32({ i, j -> vqsubU32(i, j) }, { a: UInt, b: UInt ->
        a.saturatingSub(b)
    })
}

public fun testVqsubqU32() {
    testqAriU32({ i, j -> vqsubqU32(i, j) }, { a: UInt, b: UInt ->
        a.saturatingSub(b)
    })
}

public fun testVhaddS8() {
    testAriS8({ i, j -> vhaddS8(i, j) }, { a: Byte, b: Byte ->
        (a.toInt() and b.toInt()).toByte()
    })
}

public fun testVhaddqS8() {
    testqAriS8({ i, j -> vhaddqS8(i, j) }, { a: Byte, b: Byte ->
        (a.toInt() and b.toInt()).toByte()
    })
}

public fun testVhaddS16() {
    testAriS16({ i, j -> vhaddS16(i, j) }, { a: Short, b: Short ->
        (a.toInt() and b.toInt()).toShort()
    })
}

public fun testVhaddqS16() {
    testqAriS16({ i, j -> vhaddqS16(i, j) }, { a: Short, b: Short ->
        (a.toInt() and b.toInt()).toShort()
    })
}

public fun testVhaddS32() {
    testAriS32({ i, j -> vhaddS32(i, j) }, { a: Int, b: Int ->
        a and b
    })
}

public fun testVhaddqS32() {
    testqAriS32({ i, j -> vhaddqS32(i, j) }, { a: Int, b: Int ->
        a and b
    })
}

public fun testVhaddU8() {
    testAriU8({ i, j -> vhaddU8(i, j) }, { a: UByte, b: UByte ->
        a and b
    })
}

public fun testVhaddqU8() {
    testqAriU8({ i, j -> vhaddqU8(i, j) }, { a: UByte, b: UByte ->
        a and b
    })
}

public fun testVhaddU16() {
    testAriU16({ i, j -> vhaddU16(i, j) }, { a: UShort, b: UShort ->
        a and b
    })
}

public fun testVhaddqU16() {
    testqAriU16({ i, j -> vhaddqU16(i, j) }, { a: UShort, b: UShort ->
        a and b
    })
}

public fun testVhaddU32() {
    testAriU32({ i, j -> vhaddU32(i, j) }, { a: UInt, b: UInt ->
        a and b
    })
}

public fun testVhaddqU32() {
    testqAriU32({ i, j -> vhaddqU32(i, j) }, { a: UInt, b: UInt ->
        a and b
    })
}

public fun testVrhaddS8() {
    testAriS8({ i, j -> vrhaddS8(i, j) }, { a: Byte, b: Byte ->
        (a.toInt() and b.toInt()).toByte()
    })
}

public fun testVrhaddqS8() {
    testqAriS8({ i, j -> vrhaddqS8(i, j) }, { a: Byte, b: Byte ->
        (a.toInt() and b.toInt()).toByte()
    })
}

public fun testVrhaddS16() {
    testAriS16({ i, j -> vrhaddS16(i, j) }, { a: Short, b: Short ->
        (a.toInt() and b.toInt()).toShort()
    })
}

public fun testVrhaddqS16() {
    testqAriS16({ i, j -> vrhaddqS16(i, j) }, { a: Short, b: Short ->
        (a.toInt() and b.toInt()).toShort()
    })
}

public fun testVrhaddS32() {
    testAriS32({ i, j -> vrhaddS32(i, j) }, { a: Int, b: Int ->
        a and b
    })
}

public fun testVrhaddqS32() {
    testqAriS32({ i, j -> vrhaddqS32(i, j) }, { a: Int, b: Int ->
        a and b
    })
}

public fun testVrhaddU8() {
    testAriU8({ i, j -> vrhaddU8(i, j) }, { a: UByte, b: UByte ->
        a and b
    })
}

public fun testVrhaddqU8() {
    testqAriU8({ i, j -> vrhaddqU8(i, j) }, { a: UByte, b: UByte ->
        a and b
    })
}

public fun testVrhaddU16() {
    testAriU16({ i, j -> vrhaddU16(i, j) }, { a: UShort, b: UShort ->
        a and b
    })
}

public fun testVrhaddqU16() {
    testqAriU16({ i, j -> vrhaddqU16(i, j) }, { a: UShort, b: UShort ->
        a and b
    })
}

public fun testVrhaddU32() {
    testAriU32({ i, j -> vrhaddU32(i, j) }, { a: UInt, b: UInt ->
        a and b
    })
}

public fun testVrhaddqU32() {
    testqAriU32({ i, j -> vrhaddqU32(i, j) }, { a: UInt, b: UInt ->
        a and b
    })
}

public fun testVqaddS8() {
    testAriS8({ i, j -> vqaddS8(i, j) }, { a: Byte, b: Byte ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddqS8() {
    testqAriS8({ i, j -> vqaddqS8(i, j) }, { a: Byte, b: Byte ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddS16() {
    testAriS16({ i, j -> vqaddS16(i, j) }, { a: Short, b: Short ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddqS16() {
    testqAriS16({ i, j -> vqaddqS16(i, j) }, { a: Short, b: Short ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddS32() {
    testAriS32({ i, j -> vqaddS32(i, j) }, { a: Int, b: Int ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddqS32() {
    testqAriS32({ i, j -> vqaddqS32(i, j) }, { a: Int, b: Int ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddU8() {
    testAriU8({ i, j -> vqaddU8(i, j) }, { a: UByte, b: UByte ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddqU8() {
    testqAriU8({ i, j -> vqaddqU8(i, j) }, { a: UByte, b: UByte ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddU16() {
    testAriU16({ i, j -> vqaddU16(i, j) }, { a: UShort, b: UShort ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddqU16() {
    testqAriU16({ i, j -> vqaddqU16(i, j) }, { a: UShort, b: UShort ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddU32() {
    testAriU32({ i, j -> vqaddU32(i, j) }, { a: UInt, b: UInt ->
        a.saturatingAdd(b)
    })
}

public fun testVqaddqU32() {
    testqAriU32({ i, j -> vqaddqU32(i, j) }, { a: UInt, b: UInt ->
        a.saturatingAdd(b)
    })
}

public fun testVmulS8() {
    testAriS8({ i, j -> vmulS8(i, j) }, { a: Byte, b: Byte ->
        a.overflowingMul(b).first
    })
}

public fun testVmulqS8() {
    testqAriS8({ i, j -> vmulqS8(i, j) }, { a: Byte, b: Byte ->
        a.overflowingMul(b).first
    })
}

public fun testVmulS16() {
    testAriS16({ i, j -> vmulS16(i, j) }, { a: Short, b: Short ->
        a.overflowingMul(b).first
    })
}

public fun testVmulqS16() {
    testqAriS16({ i, j -> vmulqS16(i, j) }, { a: Short, b: Short ->
        a.overflowingMul(b).first
    })
}

public fun testVmulS32() {
    testAriS32({ i, j -> vmulS32(i, j) }, { a: Int, b: Int ->
        a.overflowingMul(b).first
    })
}

public fun testVmulqS32() {
    testqAriS32({ i, j -> vmulqS32(i, j) }, { a: Int, b: Int ->
        a.overflowingMul(b).first
    })
}

public fun testVmulU8() {
    testAriU8({ i, j -> vmulU8(i, j) }, { a: UByte, b: UByte ->
        a.overflowingMul(b).first
    })
}

public fun testVmulqU8() {
    testqAriU8({ i, j -> vmulqU8(i, j) }, { a: UByte, b: UByte ->
        a.overflowingMul(b).first
    })
}

public fun testVmulU16() {
    testAriU16({ i, j -> vmulU16(i, j) }, { a: UShort, b: UShort ->
        a.overflowingMul(b).first
    })
}

public fun testVmulqU16() {
    testqAriU16({ i, j -> vmulqU16(i, j) }, { a: UShort, b: UShort ->
        a.overflowingMul(b).first
    })
}

public fun testVmulU32() {
    testAriU32({ i, j -> vmulU32(i, j) }, { a: UInt, b: UInt ->
        a.overflowingMul(b).first
    })
}

public fun testVmulqU32() {
    testqAriU32({ i, j -> vmulqU32(i, j) }, { a: UInt, b: UInt ->
        a.overflowingMul(b).first
    })
}

public fun testVmulF32() {
    testAriF32({ i, j -> vmulF32(i, j) }, { a: Float, b: Float ->
        a * b
    })
}

public fun testVmulqF32() {
    testqAriF32({ i, j -> vmulqF32(i, j) }, { a: Float, b: Float ->
        a * b
    })
}

public fun testVsubS8() {
    testAriS8({ i, j -> vsubS8(i, j) }, { a: Byte, b: Byte ->
        a - b
    })
}

public fun testVsubqS8() {
    testqAriS8({ i, j -> vsubqS8(i, j) }, { a: Byte, b: Byte ->
        a - b
    })
}

public fun testVsubS16() {
    testAriS16({ i, j -> vsubS16(i, j) }, { a: Short, b: Short ->
        a - b
    })
}

public fun testVsubqS16() {
    testqAriS16({ i, j -> vsubqS16(i, j) }, { a: Short, b: Short ->
        a - b
    })
}

public fun testVsubS32() {
    testAriS32({ i, j -> vsubS32(i, j) }, { a: Int, b: Int ->
        a - b
    })
}

public fun testVsubqS32() {
    testqAriS32({ i, j -> vsubqS32(i, j) }, { a: Int, b: Int ->
        a - b
    })
}

public fun testVsubU8() {
    testAriU8({ i, j -> vsubU8(i, j) }, { a: UByte, b: UByte ->
        a - b
    })
}

public fun testVsubqU8() {
    testqAriU8({ i, j -> vsubqU8(i, j) }, { a: UByte, b: UByte ->
        a - b
    })
}

public fun testVsubU16() {
    testAriU16({ i, j -> vsubU16(i, j) }, { a: UShort, b: UShort ->
        a - b
    })
}

public fun testVsubqU16() {
    testqAriU16({ i, j -> vsubqU16(i, j) }, { a: UShort, b: UShort ->
        a - b
    })
}

public fun testVsubU32() {
    testAriU32({ i, j -> vsubU32(i, j) }, { a: UInt, b: UInt ->
        a - b
    })
}

public fun testVsubqU32() {
    testqAriU32({ i, j -> vsubqU32(i, j) }, { a: UInt, b: UInt ->
        a - b
    })
}

public fun testVsubF32() {
    testAriF32({ i, j -> vsubF32(i, j) }, { a: Float, b: Float ->
        a - b
    })
}

public fun testVsubqF32() {
    testqAriF32({ i, j -> vsubqF32(i, j) }, { a: Float, b: Float ->
        a - b
    })
}

public fun testVhsubS8() {
    testAriS8({ i, j -> vhsubS8(i, j) }, { a: Byte, b: Byte ->
        (((a.toShort()) - (b.toShort())) / 2).toByte()
    })
}

public fun testVhsubqS8() {
    testqAriS8({ i, j -> vhsubqS8(i, j) }, { a: Byte, b: Byte ->
        (((a.toShort()) - (b.toShort())) / 2).toByte()
    })
}

public fun testVhsubS16() {
    testAriS16({ i, j -> vhsubS16(i, j) }, { a: Short, b: Short ->
        (((a.toInt()) - (b.toInt())) / 2).toShort()
    })
}

public fun testVhsubqS16() {
    testqAriS16({ i, j -> vhsubqS16(i, j) }, { a: Short, b: Short ->
        (((a.toInt()) - (b.toInt())) / 2).toShort()
    })
}

public fun testVhsubS32() {
    testAriS32({ i, j -> vhsubS32(i, j) }, { a: Int, b: Int ->
        (((a.toLong()) - (b.toLong())) / 2L).toInt()
    })
}

public fun testVhsubqS32() {
    testqAriS32({ i, j -> vhsubqS32(i, j) }, { a: Int, b: Int ->
        (((a.toLong()) - (b.toLong())) / 2L).toInt()
    })
}

public fun testVhsubU8() {
    testAriU8({ i, j -> vhsubU8(i, j) }, { a: UByte, b: UByte ->
        (((a.toUShort()) - (b.toUShort())) / 2u).toUByte()
    })
}

public fun testVhsubqU8() {
    testqAriU8({ i, j -> vhsubqU8(i, j) }, { a: UByte, b: UByte ->
        (((a.toUShort()) - (b.toUShort())) / 2u).toUByte()
    })
}

public fun testVhsubU16() {
    testAriU16({ i, j -> vhsubU16(i, j) }, { a: UShort, b: UShort ->
        (((a.toUShort()) - (b.toUShort())) / 2u)
    })
}

public fun testVhsubqU16() {
    testqAriU16({ i, j -> vhsubqU16(i, j) }, { a: UShort, b: UShort ->
        (((a.toUShort()) - (b.toUShort())) / 2u)
    })
}

public fun testVhsubU32() {
    testAriU32({ i, j -> vhsubU32(i, j) }, { a: UInt, b: UInt ->
        (((a.toULong()) - (b.toULong())) / 2uL).toUInt()
    })
}

public fun testVhsubqU32() {
    testqAriU32({ i, j -> vhsubqU32(i, j) }, { a: UInt, b: UInt ->
        (((a.toULong()) - (b.toULong())) / 2uL).toUInt()
    })
}

public fun testVabaS8() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte()))
    val b = Simd<Byte>(listOf(1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte()))
    val c = Simd<Byte>(listOf(10.toByte(), 9.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte()))
    val r = i8x8.from(vabaS8(a.into(), b.into(), c.into()))
    val e = Simd<Byte>(listOf(10.toByte(), 10.toByte(), 10.toByte(), 10.toByte(), 10.toByte(), 10.toByte(), 10.toByte(), 10.toByte()))
    check(r == e)
}

public fun testVabaS16() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort()))
    val b = Simd<Short>(listOf(1.toShort(), 1.toShort(), 1.toShort(), 1.toShort()))
    val c = Simd<Short>(listOf(10.toShort(), 9.toShort(), 8.toShort(), 7.toShort()))
    val r = i16x4.from(vabaS16(a.into(), b.into(), c.into()))
    val e = Simd<Short>(listOf(10.toShort(), 10.toShort(), 10.toShort(), 10.toShort()))
    check(r == e)
}

public fun testVabaS32() {
    val a = Simd<Int>(listOf(1, 2))
    val b = Simd<Int>(listOf(1, 1))
    val c = Simd<Int>(listOf(10, 9))
    val r = i32x2.from(vabaS32(a.into(), b.into(), c.into()))
    val e = Simd<Int>(listOf(10, 10))
    check(r == e)
}

public fun testVabaU8() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte()))
    val c = Simd<UByte>(listOf(10u.toUByte(), 9u.toUByte(), 8u.toUByte(), 7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 3u.toUByte()))
    val r = u8x8.from(vabaU8(a.into(), b.into(), c.into()))
    val e = Simd<UByte>(listOf(10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 10u.toUByte()))
    check(r == e)
}

public fun testVabaU16() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort()))
    val b = Simd<UShort>(listOf(1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort()))
    val c = Simd<UShort>(listOf(10u.toUShort(), 9u.toUShort(), 8u.toUShort(), 7u.toUShort()))
    val r = u16x4.from(vabaU16(a.into(), b.into(), c.into()))
    val e = Simd<UShort>(listOf(10u.toUShort(), 10u.toUShort(), 10u.toUShort(), 10u.toUShort()))
    check(r == e)
}

public fun testVabaU32() {
    val a = Simd<UInt>(listOf(1u, 2u))
    val b = Simd<UInt>(listOf(1u, 1u))
    val c = Simd<UInt>(listOf(10u, 9u))
    val r = u32x2.from(vabaU32(a.into(), b.into(), c.into()))
    val e = Simd<UInt>(listOf(10u, 10u))
    check(r == e)
}

public fun testVabaqS8() {
    val a = Simd<Byte>(listOf(1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte()))
    val b = Simd<Byte>(listOf(1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte(), 1.toByte()))
    val c = Simd<Byte>(listOf(10.toByte(), 9.toByte(), 8.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte(), 16.toByte(), 17.toByte(), 18.toByte(), 19.toByte()))
    val r = i8x16.from(vabaqS8(a.into(), b.into(), c.into()))
    val e = Simd<Byte>(listOf(10.toByte(), 10.toByte(), 10.toByte(), 10.toByte(), 10.toByte(), 10.toByte(), 10.toByte(), 10.toByte(), 20.toByte(), 20.toByte(), 20.toByte(), 20.toByte(), 20.toByte(), 20.toByte(), 20.toByte(), 20.toByte()))
    check(r == e)
}

public fun testVabaqS16() {
    val a = Simd<Short>(listOf(1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort(), 8.toShort()))
    val b = Simd<Short>(listOf(1.toShort(), 1.toShort(), 1.toShort(), 1.toShort(), 1.toShort(), 1.toShort(), 1.toShort(), 1.toShort()))
    val c = Simd<Short>(listOf(10.toShort(), 9.toShort(), 8.toShort(), 7.toShort(), 6.toShort(), 5.toShort(), 4.toShort(), 3.toShort()))
    val r = i16x8.from(vabaqS16(a.into(), b.into(), c.into()))
    val e = Simd<Short>(listOf(10.toShort(), 10.toShort(), 10.toShort(), 10.toShort(), 10.toShort(), 10.toShort(), 10.toShort(), 10.toShort()))
    check(r == e)
}

public fun testVabaqS32() {
    val a = Simd<Int>(listOf(1, 2, 3, 4))
    val b = Simd<Int>(listOf(1, 1, 1, 1))
    val c = Simd<Int>(listOf(10, 9, 8, 7))
    val r = i32x4.from(vabaqS32(a.into(), b.into(), c.into()))
    val e = Simd<Int>(listOf(10, 10, 10, 10))
    check(r == e)
}

public fun testVabaqU8() {
    val a = Simd<UByte>(listOf(1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 8u.toUByte(), 7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte()))
    val b = Simd<UByte>(listOf(1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte(), 1u.toUByte()))
    val c = Simd<UByte>(listOf(10u.toUByte(), 9u.toUByte(), 8u.toUByte(), 7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 3u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte(), 16u.toUByte(), 17u.toUByte(), 18u.toUByte(), 19u.toUByte()))
    val r = u8x16.from(vabaqU8(a.into(), b.into(), c.into()))
    val e = Simd<UByte>(listOf(10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 10u.toUByte(), 20u.toUByte(), 20u.toUByte(), 20u.toUByte(), 20u.toUByte(), 20u.toUByte(), 20u.toUByte(), 20u.toUByte(), 20u.toUByte()))
    check(r == e)
}

public fun testVabaqU16() {
    val a = Simd<UShort>(listOf(1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort(), 8u.toUShort()))
    val b = Simd<UShort>(listOf(1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort(), 1u.toUShort()))
    val c = Simd<UShort>(listOf(10u.toUShort(), 9u.toUShort(), 8u.toUShort(), 7u.toUShort(), 6u.toUShort(), 5u.toUShort(), 4u.toUShort(), 3u.toUShort()))
    val r = u16x8.from(vabaqU16(a.into(), b.into(), c.into()))
    val e = Simd<UShort>(listOf(10u.toUShort(), 10u.toUShort(), 10u.toUShort(), 10u.toUShort(), 10u.toUShort(), 10u.toUShort(), 10u.toUShort(), 10u.toUShort()))
    check(r == e)
}

public fun testVabaqU32() {
    val a = Simd<UInt>(listOf(1u, 2u, 3u, 4u))
    val b = Simd<UInt>(listOf(1u, 1u, 1u, 1u))
    val c = Simd<UInt>(listOf(10u, 9u, 8u, 7u))
    val r = u32x4.from(vabaqU32(a.into(), b.into(), c.into()))
    val e = Simd<UInt>(listOf(10u, 10u, 10u, 10u))
    check(r == e)
}

public fun testVrev16S8() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte()))
    val r = Simd<Byte>(listOf(1.toByte(), 0.toByte(), 3.toByte(), 2.toByte(), 5.toByte(), 4.toByte(), 7.toByte(), 6.toByte()))
    val e = i8x8.from(vrev16S8(a.into()))
    check(r == e)
}

public fun testVrev16qS8() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte()))
    val r = Simd<Byte>(listOf(1.toByte(), 0.toByte(), 3.toByte(), 2.toByte(), 5.toByte(), 4.toByte(), 7.toByte(), 6.toByte(), 9.toByte(), 8.toByte(), 11.toByte(), 10.toByte(), 13.toByte(), 12.toByte(), 15.toByte(), 14.toByte()))
    val e = i8x16.from(vrev16qS8(a.into()))
    check(r == e)
}

public fun testVrev16U8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val r = Simd<UByte>(listOf(1u.toUByte(), 0u.toUByte(), 3u.toUByte(), 2u.toUByte(), 5u.toUByte(), 4u.toUByte(), 7u.toUByte(), 6u.toUByte()))
    val e = u8x8.from(vrev16U8(a.into()))
    check(r == e)
}

public fun testVrev16qU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val r = Simd<UByte>(listOf(1u.toUByte(), 0u.toUByte(), 3u.toUByte(), 2u.toUByte(), 5u.toUByte(), 4u.toUByte(), 7u.toUByte(), 6u.toUByte(), 9u.toUByte(), 8u.toUByte(), 11u.toUByte(), 10u.toUByte(), 13u.toUByte(), 12u.toUByte(), 15u.toUByte(), 14u.toUByte()))
    val e = u8x16.from(vrev16qU8(a.into()))
    check(r == e)
}

public fun testVrev16P8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val r = Simd<UByte>(listOf(1u.toUByte(), 0u.toUByte(), 3u.toUByte(), 2u.toUByte(), 5u.toUByte(), 4u.toUByte(), 7u.toUByte(), 6u.toUByte()))
    val e = u8x8.from(vrev16P8(a.into()))
    check(r == e)
}

public fun testVrev16qP8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val r = Simd<UByte>(listOf(1u.toUByte(), 0u.toUByte(), 3u.toUByte(), 2u.toUByte(), 5u.toUByte(), 4u.toUByte(), 7u.toUByte(), 6u.toUByte(), 9u.toUByte(), 8u.toUByte(), 11u.toUByte(), 10u.toUByte(), 13u.toUByte(), 12u.toUByte(), 15u.toUByte(), 14u.toUByte()))
    val e = u8x16.from(vrev16qP8(a.into()))
    check(r == e)
}

public fun testVrev32S8() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte()))
    val r = Simd<Byte>(listOf(3.toByte(), 2.toByte(), 1.toByte(), 0.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte()))
    val e = i8x8.from(vrev32S8(a.into()))
    check(r == e)
}

public fun testVrev32qS8() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte()))
    val r = Simd<Byte>(listOf(3.toByte(), 2.toByte(), 1.toByte(), 0.toByte(), 7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 11.toByte(), 10.toByte(), 9.toByte(), 8.toByte(), 15.toByte(), 14.toByte(), 13.toByte(), 12.toByte()))
    val e = i8x16.from(vrev32qS8(a.into()))
    check(r == e)
}

public fun testVrev32U8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val r = Simd<UByte>(listOf(3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte()))
    val e = u8x8.from(vrev32U8(a.into()))
    check(r == e)
}

public fun testVrev32qU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val r = Simd<UByte>(listOf(3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 11u.toUByte(), 10u.toUByte(), 9u.toUByte(), 8u.toUByte(), 15u.toUByte(), 14u.toUByte(), 13u.toUByte(), 12u.toUByte()))
    val e = u8x16.from(vrev32qU8(a.into()))
    check(r == e)
}

public fun testVrev32S16() {
    val a = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort()))
    val r = Simd<Short>(listOf(1.toShort(), 0.toShort(), 3.toShort(), 2.toShort()))
    val e = i16x4.from(vrev32S16(a.into()))
    check(r == e)
}

public fun testVrev32qS16() {
    val a = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort()))
    val r = Simd<Short>(listOf(1.toShort(), 0.toShort(), 3.toShort(), 2.toShort(), 5.toShort(), 4.toShort(), 7.toShort(), 6.toShort()))
    val e = i16x8.from(vrev32qS16(a.into()))
    check(r == e)
}

public fun testVrev32P16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort()))
    val r = Simd<UShort>(listOf(1u.toUShort(), 0u.toUShort(), 3u.toUShort(), 2u.toUShort()))
    val e = u16x4.from(vrev32P16(a.into()))
    check(r == e)
}

public fun testVrev32qP16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    val r = Simd<UShort>(listOf(1u.toUShort(), 0u.toUShort(), 3u.toUShort(), 2u.toUShort(), 5u.toUShort(), 4u.toUShort(), 7u.toUShort(), 6u.toUShort()))
    val e = u16x8.from(vrev32qP16(a.into()))
    check(r == e)
}

public fun testVrev32U16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort()))
    val r = Simd<UShort>(listOf(1u.toUShort(), 0u.toUShort(), 3u.toUShort(), 2u.toUShort()))
    val e = u16x4.from(vrev32U16(a.into()))
    check(r == e)
}

public fun testVrev32qU16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    val r = Simd<UShort>(listOf(1u.toUShort(), 0u.toUShort(), 3u.toUShort(), 2u.toUShort(), 5u.toUShort(), 4u.toUShort(), 7u.toUShort(), 6u.toUShort()))
    val e = u16x8.from(vrev32qU16(a.into()))
    check(r == e)
}

public fun testVrev32P8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val r = Simd<UByte>(listOf(3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte()))
    val e = u8x8.from(vrev32P8(a.into()))
    check(r == e)
}

public fun testVrev32qP8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val r = Simd<UByte>(listOf(3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 11u.toUByte(), 10u.toUByte(), 9u.toUByte(), 8u.toUByte(), 15u.toUByte(), 14u.toUByte(), 13u.toUByte(), 12u.toUByte()))
    val e = u8x16.from(vrev32qP8(a.into()))
    check(r == e)
}

public fun testVrev64S8() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte()))
    val r = Simd<Byte>(listOf(7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 0.toByte()))
    val e = i8x8.from(vrev64S8(a.into()))
    check(r == e)
}

public fun testVrev64qS8() {
    val a = Simd<Byte>(listOf(0.toByte(), 1.toByte(), 2.toByte(), 3.toByte(), 4.toByte(), 5.toByte(), 6.toByte(), 7.toByte(), 8.toByte(), 9.toByte(), 10.toByte(), 11.toByte(), 12.toByte(), 13.toByte(), 14.toByte(), 15.toByte()))
    val r = Simd<Byte>(listOf(7.toByte(), 6.toByte(), 5.toByte(), 4.toByte(), 3.toByte(), 2.toByte(), 1.toByte(), 0.toByte(), 15.toByte(), 14.toByte(), 13.toByte(), 12.toByte(), 11.toByte(), 10.toByte(), 9.toByte(), 8.toByte()))
    val e = i8x16.from(vrev64qS8(a.into()))
    check(r == e)
}

public fun testVrev64S16() {
    val a = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort()))
    val r = Simd<Short>(listOf(3.toShort(), 2.toShort(), 1.toShort(), 0.toShort()))
    val e = i16x4.from(vrev64S16(a.into()))
    check(r == e)
}

public fun testVrev64qS16() {
    val a = Simd<Short>(listOf(0.toShort(), 1.toShort(), 2.toShort(), 3.toShort(), 4.toShort(), 5.toShort(), 6.toShort(), 7.toShort()))
    val r = Simd<Short>(listOf(3.toShort(), 2.toShort(), 1.toShort(), 0.toShort(), 7.toShort(), 6.toShort(), 5.toShort(), 4.toShort()))
    val e = i16x8.from(vrev64qS16(a.into()))
    check(r == e)
}

public fun testVrev64S32() {
    val a = Simd<Int>(listOf(0, 1))
    val r = Simd<Int>(listOf(1, 0))
    val e = i32x2.from(vrev64S32(a.into()))
    check(r == e)
}

public fun testVrev64qS32() {
    val a = Simd<Int>(listOf(0, 1, 2, 3))
    val r = Simd<Int>(listOf(1, 0, 3, 2))
    val e = i32x4.from(vrev64qS32(a.into()))
    check(r == e)
}

public fun testVrev64U8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val r = Simd<UByte>(listOf(7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte()))
    val e = u8x8.from(vrev64U8(a.into()))
    check(r == e)
}

public fun testVrev64qU8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val r = Simd<UByte>(listOf(7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 15u.toUByte(), 14u.toUByte(), 13u.toUByte(), 12u.toUByte(), 11u.toUByte(), 10u.toUByte(), 9u.toUByte(), 8u.toUByte()))
    val e = u8x16.from(vrev64qU8(a.into()))
    check(r == e)
}

public fun testVrev64U16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort()))
    val r = Simd<UShort>(listOf(3u.toUShort(), 2u.toUShort(), 1u.toUShort(), 0u.toUShort()))
    val e = u16x4.from(vrev64U16(a.into()))
    check(r == e)
}

public fun testVrev64qU16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    val r = Simd<UShort>(listOf(3u.toUShort(), 2u.toUShort(), 1u.toUShort(), 0u.toUShort(), 7u.toUShort(), 6u.toUShort(), 5u.toUShort(), 4u.toUShort()))
    val e = u16x8.from(vrev64qU16(a.into()))
    check(r == e)
}

public fun testVrev64U32() {
    val a = Simd<UInt>(listOf(0u, 1u))
    val r = Simd<UInt>(listOf(1u, 0u))
    val e = u32x2.from(vrev64U32(a.into()))
    check(r == e)
}

public fun testVrev64qU32() {
    val a = Simd<UInt>(listOf(0u, 1u, 2u, 3u))
    val r = Simd<UInt>(listOf(1u, 0u, 3u, 2u))
    val e = u32x4.from(vrev64qU32(a.into()))
    check(r == e)
}

public fun testVrev64F32() {
    val a = Simd<Float>(listOf(1.0f.toFloat(), 2.0f.toFloat()))
    val r = Simd<Float>(listOf(2.0f.toFloat(), 1.0f.toFloat()))
    val e = f32x2.from(vrev64F32(a.into()))
    check(r == e)
}

public fun testVrev64qF32() {
    val a = Simd<Float>(listOf(1.0f.toFloat(), 2.0f.toFloat(), (-2.0f).toFloat(), (-1.0f).toFloat()))
    val r = Simd<Float>(listOf(2.0f.toFloat(), 1.0f.toFloat(), (-1.0f).toFloat(), (-2.0f).toFloat()))
    val e = f32x4.from(vrev64qF32(a.into()))
    check(r == e)
}

public fun testVrev64P8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte()))
    val r = Simd<UByte>(listOf(7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte()))
    val e = u8x8.from(vrev64P8(a.into()))
    check(r == e)
}

public fun testVrev64qP8() {
    val a = Simd<UByte>(listOf(0u.toUByte(), 1u.toUByte(), 2u.toUByte(), 3u.toUByte(), 4u.toUByte(), 5u.toUByte(), 6u.toUByte(), 7u.toUByte(), 8u.toUByte(), 9u.toUByte(), 10u.toUByte(), 11u.toUByte(), 12u.toUByte(), 13u.toUByte(), 14u.toUByte(), 15u.toUByte()))
    val r = Simd<UByte>(listOf(7u.toUByte(), 6u.toUByte(), 5u.toUByte(), 4u.toUByte(), 3u.toUByte(), 2u.toUByte(), 1u.toUByte(), 0u.toUByte(), 15u.toUByte(), 14u.toUByte(), 13u.toUByte(), 12u.toUByte(), 11u.toUByte(), 10u.toUByte(), 9u.toUByte(), 8u.toUByte()))
    val e = u8x16.from(vrev64qP8(a.into()))
    check(r == e)
}

public fun testVrev64P16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort()))
    val r = Simd<UShort>(listOf(3u.toUShort(), 2u.toUShort(), 1u.toUShort(), 0u.toUShort()))
    val e = u16x4.from(vrev64P16(a.into()))
    check(r == e)
}

public fun testVrev64qP16() {
    val a = Simd<UShort>(listOf(0u.toUShort(), 1u.toUShort(), 2u.toUShort(), 3u.toUShort(), 4u.toUShort(), 5u.toUShort(), 6u.toUShort(), 7u.toUShort()))
    val r = Simd<UShort>(listOf(3u.toUShort(), 2u.toUShort(), 1u.toUShort(), 0u.toUShort(), 7u.toUShort(), 6u.toUShort(), 5u.toUShort(), 4u.toUShort()))
    val e = u16x8.from(vrev64qP16(a.into()))
    check(r == e)
}

public fun testVld2LaneS8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x8x2T = transmuteWithLayouts<ByteArray, Int8x8x2T>(vals, PrimitiveArrayLayouts.byteArray((16).toInt()), UnresolvedTypeLayout<Int8x8x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromByteArray(ByteArray(4) { 0.toByte() })
    vst2LaneS8(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Int8x8x2T = vld2LaneS8(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: ByteArray = transmuteWithLayouts<Int8x8x2T, ByteArray>(r, UnresolvedTypeLayout<Int8x8x2T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld3LaneS8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x8x3T = transmuteWithLayouts<ByteArray, Int8x8x3T>(vals, PrimitiveArrayLayouts.byteArray((24).toInt()), UnresolvedTypeLayout<Int8x8x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromByteArray(ByteArray(4) { 0.toByte() })
    vst3LaneS8(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Int8x8x3T = vld3LaneS8(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: ByteArray = transmuteWithLayouts<Int8x8x3T, ByteArray>(r, UnresolvedTypeLayout<Int8x8x3T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld4LaneS8() {
    val vals: ByteArray = fromFn({ i -> i.toByte() })
    val a: Int8x8x4T = transmuteWithLayouts<ByteArray, Int8x8x4T>(vals, PrimitiveArrayLayouts.byteArray((32).toInt()), UnresolvedTypeLayout<Int8x8x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromByteArray(ByteArray(4) { 0.toByte() })
    vst4LaneS8(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Int8x8x4T = vld4LaneS8(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: ByteArray = transmuteWithLayouts<Int8x8x4T, ByteArray>(r, UnresolvedTypeLayout<Int8x8x4T>(), UnresolvedTypeLayout<ByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld2LaneU8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x8x2T = transmuteWithLayouts<UByteArray, Uint8x8x2T>(vals, PrimitiveArrayLayouts.uByteArray((16).toInt()), UnresolvedTypeLayout<Uint8x8x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUByteArray(UByteArray(4) { 0.toUByte() })
    vst2LaneU8(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Uint8x8x2T = vld2LaneU8(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: UByteArray = transmuteWithLayouts<Uint8x8x2T, UByteArray>(r, UnresolvedTypeLayout<Uint8x8x2T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld3LaneU8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x8x3T = transmuteWithLayouts<UByteArray, Uint8x8x3T>(vals, PrimitiveArrayLayouts.uByteArray((24).toInt()), UnresolvedTypeLayout<Uint8x8x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUByteArray(UByteArray(4) { 0.toUByte() })
    vst3LaneU8(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Uint8x8x3T = vld3LaneU8(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: UByteArray = transmuteWithLayouts<Uint8x8x3T, UByteArray>(r, UnresolvedTypeLayout<Uint8x8x3T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld4LaneU8() {
    val vals: UByteArray = fromFn({ i -> i.toUByte() })
    val a: Uint8x8x4T = transmuteWithLayouts<UByteArray, Uint8x8x4T>(vals, PrimitiveArrayLayouts.uByteArray((32).toInt()), UnresolvedTypeLayout<Uint8x8x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUByteArray(UByteArray(4) { 0.toUByte() })
    vst4LaneU8(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Uint8x8x4T = vld4LaneU8(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: UByteArray = transmuteWithLayouts<Uint8x8x4T, UByteArray>(r, UnresolvedTypeLayout<Uint8x8x4T>(), UnresolvedTypeLayout<UByteArray>())
    check((out).contentEquals(vals))
}

public fun testVld2LaneS16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x4x2T = transmuteWithLayouts<ShortArray, Int16x4x2T>(vals, PrimitiveArrayLayouts.shortArray((8).toInt()), UnresolvedTypeLayout<Int16x4x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromShortArray(ShortArray(4) { 0.toShort() })
    vst2LaneS16(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Int16x4x2T = vld2LaneS16(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: ShortArray = transmuteWithLayouts<Int16x4x2T, ShortArray>(r, UnresolvedTypeLayout<Int16x4x2T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld3LaneS16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x4x3T = transmuteWithLayouts<ShortArray, Int16x4x3T>(vals, PrimitiveArrayLayouts.shortArray((12).toInt()), UnresolvedTypeLayout<Int16x4x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromShortArray(ShortArray(4) { 0.toShort() })
    vst3LaneS16(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Int16x4x3T = vld3LaneS16(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: ShortArray = transmuteWithLayouts<Int16x4x3T, ShortArray>(r, UnresolvedTypeLayout<Int16x4x3T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld4LaneS16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x4x4T = transmuteWithLayouts<ShortArray, Int16x4x4T>(vals, PrimitiveArrayLayouts.shortArray((16).toInt()), UnresolvedTypeLayout<Int16x4x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromShortArray(ShortArray(4) { 0.toShort() })
    vst4LaneS16(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Int16x4x4T = vld4LaneS16(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: ShortArray = transmuteWithLayouts<Int16x4x4T, ShortArray>(r, UnresolvedTypeLayout<Int16x4x4T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qLaneS16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x8x2T = transmuteWithLayouts<ShortArray, Int16x8x2T>(vals, PrimitiveArrayLayouts.shortArray((16).toInt()), UnresolvedTypeLayout<Int16x8x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromShortArray(ShortArray(4) { 0.toShort() })
    vst2qLaneS16(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Int16x8x2T = vld2qLaneS16(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: ShortArray = transmuteWithLayouts<Int16x8x2T, ShortArray>(r, UnresolvedTypeLayout<Int16x8x2T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qLaneS16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x8x3T = transmuteWithLayouts<ShortArray, Int16x8x3T>(vals, PrimitiveArrayLayouts.shortArray((24).toInt()), UnresolvedTypeLayout<Int16x8x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromShortArray(ShortArray(4) { 0.toShort() })
    vst3qLaneS16(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Int16x8x3T = vld3qLaneS16(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: ShortArray = transmuteWithLayouts<Int16x8x3T, ShortArray>(r, UnresolvedTypeLayout<Int16x8x3T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qLaneS16() {
    val vals: ShortArray = fromFn({ i -> i.toShort() })
    val a: Int16x8x4T = transmuteWithLayouts<ShortArray, Int16x8x4T>(vals, PrimitiveArrayLayouts.shortArray((32).toInt()), UnresolvedTypeLayout<Int16x8x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromShortArray(ShortArray(4) { 0.toShort() })
    vst4qLaneS16(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Int16x8x4T = vld4qLaneS16(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: ShortArray = transmuteWithLayouts<Int16x8x4T, ShortArray>(r, UnresolvedTypeLayout<Int16x8x4T>(), UnresolvedTypeLayout<ShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2LaneU16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x4x2T = transmuteWithLayouts<UShortArray, Uint16x4x2T>(vals, PrimitiveArrayLayouts.uShortArray((8).toInt()), UnresolvedTypeLayout<Uint16x4x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUShortArray(UShortArray(4) { 0.toUShort() })
    vst2LaneU16(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Uint16x4x2T = vld2LaneU16(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: UShortArray = transmuteWithLayouts<Uint16x4x2T, UShortArray>(r, UnresolvedTypeLayout<Uint16x4x2T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld3LaneU16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x4x3T = transmuteWithLayouts<UShortArray, Uint16x4x3T>(vals, PrimitiveArrayLayouts.uShortArray((12).toInt()), UnresolvedTypeLayout<Uint16x4x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUShortArray(UShortArray(4) { 0.toUShort() })
    vst3LaneU16(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Uint16x4x3T = vld3LaneU16(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: UShortArray = transmuteWithLayouts<Uint16x4x3T, UShortArray>(r, UnresolvedTypeLayout<Uint16x4x3T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld4LaneU16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x4x4T = transmuteWithLayouts<UShortArray, Uint16x4x4T>(vals, PrimitiveArrayLayouts.uShortArray((16).toInt()), UnresolvedTypeLayout<Uint16x4x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUShortArray(UShortArray(4) { 0.toUShort() })
    vst4LaneU16(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Uint16x4x4T = vld4LaneU16(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: UShortArray = transmuteWithLayouts<Uint16x4x4T, UShortArray>(r, UnresolvedTypeLayout<Uint16x4x4T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qLaneU16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x8x2T = transmuteWithLayouts<UShortArray, Uint16x8x2T>(vals, PrimitiveArrayLayouts.uShortArray((16).toInt()), UnresolvedTypeLayout<Uint16x8x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUShortArray(UShortArray(4) { 0.toUShort() })
    vst2qLaneU16(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Uint16x8x2T = vld2qLaneU16(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: UShortArray = transmuteWithLayouts<Uint16x8x2T, UShortArray>(r, UnresolvedTypeLayout<Uint16x8x2T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qLaneU16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x8x3T = transmuteWithLayouts<UShortArray, Uint16x8x3T>(vals, PrimitiveArrayLayouts.uShortArray((24).toInt()), UnresolvedTypeLayout<Uint16x8x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUShortArray(UShortArray(4) { 0.toUShort() })
    vst3qLaneU16(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Uint16x8x3T = vld3qLaneU16(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: UShortArray = transmuteWithLayouts<Uint16x8x3T, UShortArray>(r, UnresolvedTypeLayout<Uint16x8x3T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qLaneU16() {
    val vals: UShortArray = fromFn({ i -> i.toUShort() })
    val a: Uint16x8x4T = transmuteWithLayouts<UShortArray, Uint16x8x4T>(vals, PrimitiveArrayLayouts.uShortArray((32).toInt()), UnresolvedTypeLayout<Uint16x8x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUShortArray(UShortArray(4) { 0.toUShort() })
    vst4qLaneU16(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Uint16x8x4T = vld4qLaneU16(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: UShortArray = transmuteWithLayouts<Uint16x8x4T, UShortArray>(r, UnresolvedTypeLayout<Uint16x8x4T>(), UnresolvedTypeLayout<UShortArray>())
    check((out).contentEquals(vals))
}

public fun testVld2LaneS32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x2x2T = transmuteWithLayouts<IntArray, Int32x2x2T>(vals, PrimitiveArrayLayouts.intArray((4).toInt()), UnresolvedTypeLayout<Int32x2x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    vst2LaneS32(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Int32x2x2T = vld2LaneS32(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: IntArray = transmuteWithLayouts<Int32x2x2T, IntArray>(r, UnresolvedTypeLayout<Int32x2x2T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld3LaneS32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x2x3T = transmuteWithLayouts<IntArray, Int32x2x3T>(vals, PrimitiveArrayLayouts.intArray((6).toInt()), UnresolvedTypeLayout<Int32x2x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    vst3LaneS32(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Int32x2x3T = vld3LaneS32(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: IntArray = transmuteWithLayouts<Int32x2x3T, IntArray>(r, UnresolvedTypeLayout<Int32x2x3T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld4LaneS32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x2x4T = transmuteWithLayouts<IntArray, Int32x2x4T>(vals, PrimitiveArrayLayouts.intArray((8).toInt()), UnresolvedTypeLayout<Int32x2x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    vst4LaneS32(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Int32x2x4T = vld4LaneS32(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: IntArray = transmuteWithLayouts<Int32x2x4T, IntArray>(r, UnresolvedTypeLayout<Int32x2x4T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qLaneS32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x4x2T = transmuteWithLayouts<IntArray, Int32x4x2T>(vals, PrimitiveArrayLayouts.intArray((8).toInt()), UnresolvedTypeLayout<Int32x4x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    vst2qLaneS32(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Int32x4x2T = vld2qLaneS32(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: IntArray = transmuteWithLayouts<Int32x4x2T, IntArray>(r, UnresolvedTypeLayout<Int32x4x2T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qLaneS32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x4x3T = transmuteWithLayouts<IntArray, Int32x4x3T>(vals, PrimitiveArrayLayouts.intArray((12).toInt()), UnresolvedTypeLayout<Int32x4x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    vst3qLaneS32(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Int32x4x3T = vld3qLaneS32(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: IntArray = transmuteWithLayouts<Int32x4x3T, IntArray>(r, UnresolvedTypeLayout<Int32x4x3T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qLaneS32() {
    val vals: IntArray = fromFn({ i -> i.toInt() })
    val a: Int32x4x4T = transmuteWithLayouts<IntArray, Int32x4x4T>(vals, PrimitiveArrayLayouts.intArray((16).toInt()), UnresolvedTypeLayout<Int32x4x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromIntArray(IntArray(4) { 0 })
    vst4qLaneS32(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Int32x4x4T = vld4qLaneS32(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: IntArray = transmuteWithLayouts<Int32x4x4T, IntArray>(r, UnresolvedTypeLayout<Int32x4x4T>(), UnresolvedTypeLayout<IntArray>())
    check((out).contentEquals(vals))
}

public fun testVld2LaneU32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x2x2T = transmuteWithLayouts<UIntArray, Uint32x2x2T>(vals, PrimitiveArrayLayouts.uIntArray((4).toInt()), UnresolvedTypeLayout<Uint32x2x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUIntArray(UIntArray(4) { 0.toUInt() })
    vst2LaneU32(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Uint32x2x2T = vld2LaneU32(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: UIntArray = transmuteWithLayouts<Uint32x2x2T, UIntArray>(r, UnresolvedTypeLayout<Uint32x2x2T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld3LaneU32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x2x3T = transmuteWithLayouts<UIntArray, Uint32x2x3T>(vals, PrimitiveArrayLayouts.uIntArray((6).toInt()), UnresolvedTypeLayout<Uint32x2x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUIntArray(UIntArray(4) { 0.toUInt() })
    vst3LaneU32(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Uint32x2x3T = vld3LaneU32(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: UIntArray = transmuteWithLayouts<Uint32x2x3T, UIntArray>(r, UnresolvedTypeLayout<Uint32x2x3T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld4LaneU32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x2x4T = transmuteWithLayouts<UIntArray, Uint32x2x4T>(vals, PrimitiveArrayLayouts.uIntArray((8).toInt()), UnresolvedTypeLayout<Uint32x2x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUIntArray(UIntArray(4) { 0.toUInt() })
    vst4LaneU32(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Uint32x2x4T = vld4LaneU32(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: UIntArray = transmuteWithLayouts<Uint32x2x4T, UIntArray>(r, UnresolvedTypeLayout<Uint32x2x4T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qLaneU32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x4x2T = transmuteWithLayouts<UIntArray, Uint32x4x2T>(vals, PrimitiveArrayLayouts.uIntArray((8).toInt()), UnresolvedTypeLayout<Uint32x4x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUIntArray(UIntArray(4) { 0.toUInt() })
    vst2qLaneU32(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Uint32x4x2T = vld2qLaneU32(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: UIntArray = transmuteWithLayouts<Uint32x4x2T, UIntArray>(r, UnresolvedTypeLayout<Uint32x4x2T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qLaneU32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x4x3T = transmuteWithLayouts<UIntArray, Uint32x4x3T>(vals, PrimitiveArrayLayouts.uIntArray((12).toInt()), UnresolvedTypeLayout<Uint32x4x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUIntArray(UIntArray(4) { 0.toUInt() })
    vst3qLaneU32(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Uint32x4x3T = vld3qLaneU32(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: UIntArray = transmuteWithLayouts<Uint32x4x3T, UIntArray>(r, UnresolvedTypeLayout<Uint32x4x3T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qLaneU32() {
    val vals: UIntArray = fromFn({ i -> i.toUInt() })
    val a: Uint32x4x4T = transmuteWithLayouts<UIntArray, Uint32x4x4T>(vals, PrimitiveArrayLayouts.uIntArray((16).toInt()), UnresolvedTypeLayout<Uint32x4x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromUIntArray(UIntArray(4) { 0.toUInt() })
    vst4qLaneU32(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Uint32x4x4T = vld4qLaneU32(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: UIntArray = transmuteWithLayouts<Uint32x4x4T, UIntArray>(r, UnresolvedTypeLayout<Uint32x4x4T>(), UnresolvedTypeLayout<UIntArray>())
    check((out).contentEquals(vals))
}

public fun testVld2LaneF32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x2x2T = transmuteWithLayouts<FloatArray, Float32x2x2T>(vals, PrimitiveArrayLayouts.floatArray((4).toInt()), UnresolvedTypeLayout<Float32x2x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromFloatArray(FloatArray(4) { 0.toFloat() })
    vst2LaneF32(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Float32x2x2T = vld2LaneF32(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: FloatArray = transmuteWithLayouts<Float32x2x2T, FloatArray>(r, UnresolvedTypeLayout<Float32x2x2T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld3LaneF32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x2x3T = transmuteWithLayouts<FloatArray, Float32x2x3T>(vals, PrimitiveArrayLayouts.floatArray((6).toInt()), UnresolvedTypeLayout<Float32x2x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromFloatArray(FloatArray(4) { 0.toFloat() })
    vst3LaneF32(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Float32x2x3T = vld3LaneF32(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: FloatArray = transmuteWithLayouts<Float32x2x3T, FloatArray>(r, UnresolvedTypeLayout<Float32x2x3T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld4LaneF32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x2x4T = transmuteWithLayouts<FloatArray, Float32x2x4T>(vals, PrimitiveArrayLayouts.floatArray((8).toInt()), UnresolvedTypeLayout<Float32x2x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromFloatArray(FloatArray(4) { 0.toFloat() })
    vst4LaneF32(localStorageL1C0.elementMutPtr(0), a, 1)
    val r: Float32x2x4T = vld4LaneF32(localStorageL1C0.elementConstPtr(0), a, 1)
    val out: FloatArray = transmuteWithLayouts<Float32x2x4T, FloatArray>(r, UnresolvedTypeLayout<Float32x2x4T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld2qLaneF32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x4x2T = transmuteWithLayouts<FloatArray, Float32x4x2T>(vals, PrimitiveArrayLayouts.floatArray((8).toInt()), UnresolvedTypeLayout<Float32x4x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromFloatArray(FloatArray(4) { 0.toFloat() })
    vst2qLaneF32(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Float32x4x2T = vld2qLaneF32(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: FloatArray = transmuteWithLayouts<Float32x4x2T, FloatArray>(r, UnresolvedTypeLayout<Float32x4x2T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld3qLaneF32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x4x3T = transmuteWithLayouts<FloatArray, Float32x4x3T>(vals, PrimitiveArrayLayouts.floatArray((12).toInt()), UnresolvedTypeLayout<Float32x4x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromFloatArray(FloatArray(4) { 0.toFloat() })
    vst3qLaneF32(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Float32x4x3T = vld3qLaneF32(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: FloatArray = transmuteWithLayouts<Float32x4x3T, FloatArray>(r, UnresolvedTypeLayout<Float32x4x3T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld4qLaneF32() {
    val vals: FloatArray = fromFn({ i -> i.toFloat() })
    val a: Float32x4x4T = transmuteWithLayouts<FloatArray, Float32x4x4T>(vals, PrimitiveArrayLayouts.floatArray((16).toInt()), UnresolvedTypeLayout<Float32x4x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromFloatArray(FloatArray(4) { 0.toFloat() })
    vst4qLaneF32(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Float32x4x4T = vld4qLaneF32(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: FloatArray = transmuteWithLayouts<Float32x4x4T, FloatArray>(r, UnresolvedTypeLayout<Float32x4x4T>(), UnresolvedTypeLayout<FloatArray>())
    check((out).contentEquals(vals))
}

public fun testVld2LaneF16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x4x2T = transmuteWithLayouts<List<F16>, Float16x4x2T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (8).toInt()), UnresolvedTypeLayout<Float16x4x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromList(List(4) { F16.fromDouble(0.toDouble()) }, PrimitiveLayouts.F16_LAYOUT)
    vst2LaneF16(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Float16x4x2T = vld2LaneF16(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: List<F16> = transmuteWithLayouts<Float16x4x2T, List<F16>>(r, UnresolvedTypeLayout<Float16x4x2T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld3LaneF16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x4x3T = transmuteWithLayouts<List<F16>, Float16x4x3T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (12).toInt()), UnresolvedTypeLayout<Float16x4x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromList(List(4) { F16.fromDouble(0.toDouble()) }, PrimitiveLayouts.F16_LAYOUT)
    vst3LaneF16(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Float16x4x3T = vld3LaneF16(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: List<F16> = transmuteWithLayouts<Float16x4x3T, List<F16>>(r, UnresolvedTypeLayout<Float16x4x3T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld4LaneF16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x4x4T = transmuteWithLayouts<List<F16>, Float16x4x4T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (16).toInt()), UnresolvedTypeLayout<Float16x4x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromList(List(4) { F16.fromDouble(0.toDouble()) }, PrimitiveLayouts.F16_LAYOUT)
    vst4LaneF16(localStorageL1C0.elementMutPtr(0), a, 3)
    val r: Float16x4x4T = vld4LaneF16(localStorageL1C0.elementConstPtr(0), a, 3)
    val out: List<F16> = transmuteWithLayouts<Float16x4x4T, List<F16>>(r, UnresolvedTypeLayout<Float16x4x4T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld2qLaneF16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x8x2T = transmuteWithLayouts<List<F16>, Float16x8x2T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (16).toInt()), UnresolvedTypeLayout<Float16x8x2T>())
    val localStorageL1C0 = AddressableArrayLocal.fromList(List(4) { F16.fromDouble(0.toDouble()) }, PrimitiveLayouts.F16_LAYOUT)
    vst2qLaneF16(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Float16x8x2T = vld2qLaneF16(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: List<F16> = transmuteWithLayouts<Float16x8x2T, List<F16>>(r, UnresolvedTypeLayout<Float16x8x2T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld3qLaneF16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x8x3T = transmuteWithLayouts<List<F16>, Float16x8x3T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (24).toInt()), UnresolvedTypeLayout<Float16x8x3T>())
    val localStorageL1C0 = AddressableArrayLocal.fromList(List(4) { F16.fromDouble(0.toDouble()) }, PrimitiveLayouts.F16_LAYOUT)
    vst3qLaneF16(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Float16x8x3T = vld3qLaneF16(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: List<F16> = transmuteWithLayouts<Float16x8x3T, List<F16>>(r, UnresolvedTypeLayout<Float16x8x3T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

public fun testVld4qLaneF16() {
    val vals: List<F16> = fromFn({ i -> F16.fromDouble(i.toDouble()) })
    val a: Float16x8x4T = transmuteWithLayouts<List<F16>, Float16x8x4T>(vals, fixedListTypeLayout(PrimitiveLayouts.F16_LAYOUT, (32).toInt()), UnresolvedTypeLayout<Float16x8x4T>())
    val localStorageL1C0 = AddressableArrayLocal.fromList(List(4) { F16.fromDouble(0.toDouble()) }, PrimitiveLayouts.F16_LAYOUT)
    vst4qLaneF16(localStorageL1C0.elementMutPtr(0), a, 7)
    val r: Float16x8x4T = vld4qLaneF16(localStorageL1C0.elementConstPtr(0), a, 7)
    val out: List<F16> = transmuteWithLayouts<Float16x8x4T, List<F16>>(r, UnresolvedTypeLayout<Float16x8x4T>(), UnresolvedTypeLayout<List<F16>>())
    check(out == vals)
}

// Note: simdTest (which uses isArmFeatureDetected) does not support the v8
// feature that we need on arm.
public fun crc32() {
    check(crc32b(UInt.MAX_VALUE, UByte.MAX_VALUE) == 16777215u)
    check(crc32h(UInt.MAX_VALUE, UShort.MAX_VALUE) == 65535u)
    check(crc32w(UInt.MAX_VALUE, UInt.MAX_VALUE) == 0u)
    check(crc32cb(UInt.MAX_VALUE, UByte.MAX_VALUE) == 16777215u)
    check(crc32ch(UInt.MAX_VALUE, UShort.MAX_VALUE) == 65535u)
    check(crc32cw(UInt.MAX_VALUE, UInt.MAX_VALUE) == 0u)
    check(crc32d(UInt.MAX_VALUE, ULong.MAX_VALUE) == 3736805603u)
    check(crc32cd(UInt.MAX_VALUE, ULong.MAX_VALUE) == 3080238136u)
}

public fun testVld1LaneF16() {
    val a = Simd<F16>(listOf(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f)))
    val elem: F16 = F16.fromFloat(42.0f)
    val e = Simd<F16>(listOf(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(42.0f)))
    val r = f16x4.from(vld1LaneF16(elem, a.into(), 3))
    check(r == e)
}

public fun testVld1qLaneF16() {
    val a = Simd<F16>(listOf(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(7.0f)))
    val elem: F16 = F16.fromFloat(42.0f)
    val e = Simd<F16>(listOf(F16.fromFloat(0.0f), F16.fromFloat(1.0f), F16.fromFloat(2.0f), F16.fromFloat(3.0f), F16.fromFloat(4.0f), F16.fromFloat(5.0f), F16.fromFloat(6.0f), F16.fromFloat(42.0f)))
    val r = f16x8.from(vld1qLaneF16(elem, a.into(), 7))
    check(r == e)
}

public fun testVld1DupF16() {
    val elem: F16 = F16.fromFloat(42.0f)
    val e = Simd<F16>(listOf(F16.fromFloat(42.0f), F16.fromFloat(42.0f), F16.fromFloat(42.0f), F16.fromFloat(42.0f)))
    val r = f16x4.from(vld1DupF16(elem))
    check(r == e)
}

public fun testVld1qDupF16() {
    val elem: F16 = F16.fromFloat(42.0f)
    val e = Simd<F16>(listOf(F16.fromFloat(42.0f), F16.fromFloat(42.0f), F16.fromFloat(42.0f), F16.fromFloat(42.0f), F16.fromFloat(42.0f), F16.fromFloat(42.0f), F16.fromFloat(42.0f), F16.fromFloat(42.0f)))
    val r = f16x8.from(vld1qDupF16(elem))
    check(r == e)
}
