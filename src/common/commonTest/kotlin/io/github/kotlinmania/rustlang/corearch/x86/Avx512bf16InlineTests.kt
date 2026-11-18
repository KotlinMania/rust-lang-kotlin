// port-lint: tests x86/avx512bf16.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.transmuteCopy
import io.github.kotlinmania.rustlang.corearch.simd.Simd
import io.github.kotlinmania.rustlang.corearch.simd.U16x4

// [AVX512BF16 intrinsics].
//
// [AVX512BF16 intrinsics]: https://software.intel.com/sites/landingpage/IntrinsicsGuide/#expand=1769&avx512techs=AVX512_BF16

public fun testMmCvtne2psPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-178.125f, -10.5f, -3.75f, -50.25f)
    val a = Simd<Float>((aArray).toList()).asM128()
    val b = Simd<Float>((bArray).toList()).asM128()
    val c: M128bh = mmCvtne2psPbh(a, b)
    val result = c.asU16x8().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort())
    check(result == expectedResult)
}

public fun testMmMaskCvtne2psPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-178.125f, -10.5f, -3.75f, -50.25f)
    val srcArray: UShortArray = ushortArrayOf(0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort())
    val src = Simd<UShort>((srcArray).toList()).asM128bh()
    val a = Simd<Float>((aArray).toList()).asM128()
    val b = Simd<Float>((bArray).toList()).asM128()
    val k: Mmask8 = 0xFFu.toUByte()
    val c: M128bh = mmMaskCvtne2psPbh(src, k, a, b)
    val result = c.asU16x8().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort())
    check(result == expectedResult)
    val k1 = 0x0
    val c1 = mmMaskCvtne2psPbh(src, k1, a, b)
    val result1 = c1.asU16x8().asArray()
    val expectedResult1 = srcArray
    check(result1 == expectedResult1)
}

public fun testMmMaskzCvtne2psPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-178.125f, -10.5f, -3.75f, -50.25f)
    val a = Simd<Float>((aArray).toList()).asM128()
    val b = Simd<Float>((bArray).toList()).asM128()
    val k: Mmask8 = 0xFFu.toUByte()
    val c: M128bh = mmMaskzCvtne2psPbh(k, a, b)
    val result = c.asU16x8().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort())
    check(result == expectedResult)
    val k1 = 0x3C
    val c1 = mmMaskzCvtne2psPbh(k1, a, b)
    val result1 = c1.asU16x8().asArray()
    val expectedResult1: UShortArray = ushortArrayOf(0u.toUShort(), 0u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0u.toUShort(), 0u.toUShort())
    check(result1 == expectedResult1)
}

public fun testMm256Cvtne2psPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val bArray = floatArrayOf(-178.125f, -10.5f, -3.75f, -50.25f, -16.5f, -255.11f, -1000.158f, -575.575f)
    val a = Simd<Float>((aArray).toList()).asM256()
    val b = Simd<Float>((bArray).toList()).asM256()
    val c: M256bh = mm256Cvtne2psPbh(a, b)
    val result = c.asU16x16().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
}

public fun testMm256MaskCvtne2psPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val bArray = floatArrayOf(-178.125f, -10.5f, -3.75f, -50.25f, -16.5f, -255.11f, -1000.158f, -575.575f)
    val srcArray: UShortArray = ushortArrayOf(0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort())
    val src = Simd<UShort>((srcArray).toList()).asM256bh()
    val a = Simd<Float>((aArray).toList()).asM256()
    val b = Simd<Float>((bArray).toList()).asM256()
    val k: Mmask16 = 0xffffu.toUShort()
    val c: M256bh = mm256MaskCvtne2psPbh(src, k, a, b)
    val result = c.asU16x16().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
    val k1: Mmask16 = 0u.toUShort()
    val c1: M256bh = mm256MaskCvtne2psPbh(src, k1, a, b)
    val result1 = c1.asU16x16().asArray()
    val expectedResult1 = srcArray
    check(result1 == expectedResult1)
}

public fun testMm256MaskzCvtne2psPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val bArray = floatArrayOf(-178.125f, -10.5f, -3.75f, -50.25f, -16.5f, -255.11f, -1000.158f, -575.575f)
    val a = Simd<Float>((aArray).toList()).asM256()
    val b = Simd<Float>((bArray).toList()).asM256()
    val k: Mmask16 = 0xffffu.toUShort()
    val c: M256bh = mm256MaskzCvtne2psPbh(k, a, b)
    val result = c.asU16x16().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
    val k1: Mmask16 = 0x6C36u.toUShort()
    val c1: M256bh = mm256MaskzCvtne2psPbh(k1, a, b)
    val result1 = c1.asU16x16().asArray()
    val expectedResult1: UShortArray = ushortArrayOf(0u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0u.toUShort())
    check(result1 == expectedResult1)
}

public fun testMm512Cvtne2psPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f, 178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val bArray = floatArrayOf(-178.125f, -10.5f, -3.75f, -50.25f, -16.5f, -255.11f, -1000.158f, -575.575f, -178.125f, -10.5f, -3.75f, -50.25f, -16.5f, -255.11f, -1000.158f, -575.575f)
    val a = Simd<Float>((aArray).toList()).asM512()
    val b = Simd<Float>((bArray).toList()).asM512()
    val c: M512bh = mm512Cvtne2psPbh(a, b)
    val result = c.asU16x32().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort(), 0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
}

public fun testMm512MaskCvtne2psPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f, 178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val bArray = floatArrayOf(-178.125f, -10.5f, -3.75f, -50.25f, -16.5f, -255.11f, -1000.158f, -575.575f, -178.125f, -10.5f, -3.75f, -50.25f, -16.5f, -255.11f, -1000.158f, -575.575f)
    val srcArray: UShortArray = ushortArrayOf(0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort())
    val src = Simd<UShort>((srcArray).toList()).asM512bh()
    val a = Simd<Float>((aArray).toList()).asM512()
    val b = Simd<Float>((bArray).toList()).asM512()
    val k: Mmask32 = 0xffffffffu
    val c: M512bh = mm512MaskCvtne2psPbh(src, k, a, b)
    val result = c.asU16x32().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort(), 0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
    val k1: Mmask32 = 0u
    val c1: M512bh = mm512MaskCvtne2psPbh(src, k1, a, b)
    val result1 = c1.asU16x32().asArray()
    val expectedResult1 = srcArray
    check(result1 == expectedResult1)
}

public fun testMm512MaskzCvtne2psPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f, 178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val bArray = floatArrayOf(-178.125f, -10.5f, -3.75f, -50.25f, -16.5f, -255.11f, -1000.158f, -575.575f, -178.125f, -10.5f, -3.75f, -50.25f, -16.5f, -255.11f, -1000.158f, -575.575f)
    val a = Simd<Float>((aArray).toList()).asM512()
    val b = Simd<Float>((bArray).toList()).asM512()
    val k: Mmask32 = 0xffffffffu
    val c: M512bh = mm512MaskzCvtne2psPbh(k, a, b)
    val result = c.asU16x32().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort(), 0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
    val k1: Mmask32 = 0xCA96A356u
    val c1: M512bh = mm512MaskzCvtne2psPbh(k1, a, b)
    val result1 = c1.asU16x32().asArray()
    val expectedResult1: UShortArray = ushortArrayOf(0u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0u.toUShort(), 0xC184u.toUShort(), 0u.toUShort(), 0xC47Au.toUShort(), 0u.toUShort(), 0xC332u.toUShort(), 0xC128u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0xC37Fu.toUShort(), 0u.toUShort(), 0xC410u.toUShort(), 0u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0u.toUShort(), 0x4184u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0x4410u.toUShort(), 0u.toUShort(), 0x4128u.toUShort(), 0u.toUShort(), 0x4249u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result1 == expectedResult1)
}

public fun testMm256CvtnepsPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val a = Simd<Float>((aArray).toList()).asM256()
    val c: M128bh = mm256CvtnepsPbh(a)
    val result = c.asU16x8().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
}

public fun testMm256MaskCvtnepsPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val srcArray: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort())
    val src = Simd<UShort>((srcArray).toList()).asM128bh()
    val a = Simd<Float>((aArray).toList()).asM256()
    val k: Mmask8 = 0xffu.toUByte()
    val b = mm256MaskCvtnepsPbh(src, k, a)
    val result = b.asU16x8().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
    val k1: Mmask8 = 0x0u.toUByte()
    val b1: M128bh = mm256MaskCvtnepsPbh(src, k1, a)
    val result1 = b1.asU16x8().asArray()
    val expectedResult1: UShortArray = srcArray
    check(result1 == expectedResult1)
}

public fun testMm256MaskzCvtnepsPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val a = Simd<Float>((aArray).toList()).asM256()
    val k: Mmask8 = 0xffu.toUByte()
    val b = mm256MaskzCvtnepsPbh(k, a)
    val result = b.asU16x8().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
    val k1: Mmask8 = 0x6u.toUByte()
    val b1: M128bh = mm256MaskzCvtnepsPbh(k1, a)
    val result1 = b1.asU16x8().asArray()
    val expectedResult1: UShortArray = ushortArrayOf(0u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort())
    check(result1 == expectedResult1)
}

public fun testMm512CvtnepsPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f, 178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val a = Simd<Float>((aArray).toList()).asM512()
    val c: M256bh = mm512CvtnepsPbh(a)
    val result = c.asU16x16().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
}

public fun testMm512MaskCvtnepsPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f, 178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val srcArray: UShortArray = ushortArrayOf(0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort(), 0xC332u.toUShort(), 0xC128u.toUShort(), 0xC070u.toUShort(), 0xC249u.toUShort(), 0xC184u.toUShort(), 0xC37Fu.toUShort(), 0xC47Au.toUShort(), 0xC410u.toUShort())
    val src = Simd<UShort>((srcArray).toList()).asM256bh()
    val a = Simd<Float>((aArray).toList()).asM512()
    val k: Mmask16 = 0xffffu.toUShort()
    val c: M256bh = mm512MaskCvtnepsPbh(src, k, a)
    val result = c.asU16x16().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
    val k1: Mmask16 = 0u.toUShort()
    val c1: M256bh = mm512MaskCvtnepsPbh(src, k1, a)
    val result1 = c1.asU16x16().asArray()
    val expectedResult1 = srcArray
    check(result1 == expectedResult1)
}

public fun testMm512MaskzCvtnepsPbh() {
    val aArray = floatArrayOf(178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f, 178.125f, 10.5f, 3.75f, 50.25f, 16.5f, 255.11f, 1000.158f, 575.575f)
    val a = Simd<Float>((aArray).toList()).asM512()
    val k: Mmask16 = 0xffffu.toUShort()
    val c: M256bh = mm512MaskzCvtnepsPbh(k, a)
    val result = c.asU16x16().asArray()
    val expectedResult: UShortArray = ushortArrayOf(0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort(), 0x4332u.toUShort(), 0x4128u.toUShort(), 0x4070u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0x4410u.toUShort())
    check(result == expectedResult)
    val k1: Mmask16 = 0x653au.toUShort()
    val c1: M256bh = mm512MaskzCvtnepsPbh(k1, a)
    val result1 = c1.asU16x16().asArray()
    val expectedResult1: UShortArray = ushortArrayOf(0u.toUShort(), 0x4128u.toUShort(), 0u.toUShort(), 0x4249u.toUShort(), 0x4184u.toUShort(), 0x437Fu.toUShort(), 0u.toUShort(), 0u.toUShort(), 0x4332u.toUShort(), 0u.toUShort(), 0x4070u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0x437Fu.toUShort(), 0x447Au.toUShort(), 0u.toUShort())
    check(result1 == expectedResult1)
}

public fun testMmDpbf16Ps() {
    val aArray = floatArrayOf(8.5f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f)
    val a1 = Simd<Float>((aArray).toList()).asM128()
    val b1 = Simd<Float>((bArray).toList()).asM128()
    val src = Simd<Float>((floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f)).toList()).asM128()
    val a: M128bh = mmCvtne2psPbh(a1, a1)
    val b: M128bh = mmCvtne2psPbh(b1, b1)
    val c: M128 = mmDpbf16Ps(src, a, b)
    val result = c.asF32x4().asArray()
    val expectedResult: FloatArray = floatArrayOf(-18.0f, -52.0f, -16.0f, -50.0f)
    check(result == expectedResult)
}

public fun testMmMaskDpbf16Ps() {
    val aArray = floatArrayOf(8.5f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f)
    val a1 = Simd<Float>((aArray).toList()).asM128()
    val b1 = Simd<Float>((bArray).toList()).asM128()
    val k: Mmask8 = 0xf3u.toUByte()
    val src = Simd<Float>((floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f)).toList()).asM128()
    val a: M128bh = mmCvtne2psPbh(a1, a1)
    val b: M128bh = mmCvtne2psPbh(b1, b1)
    val c: M128 = mmMaskDpbf16Ps(src, k, a, b)
    val result = c.asF32x4().asArray()
    val expectedResult: FloatArray = floatArrayOf(-18.0f, -52.0f, 3.0f, 4.0f)
    check(result == expectedResult)
    val k1: Mmask8 = 0xffu.toUByte()
    val c1: M128 = mmMaskDpbf16Ps(src, k1, a, b)
    val result1 = c1.asF32x4().asArray()
    val expectedResult1: FloatArray = floatArrayOf(-18.0f, -52.0f, -16.0f, -50.0f)
    check(result1 == expectedResult1)
    val k2: Mmask8 = 0u.toUByte()
    val c2: M128 = mmMaskDpbf16Ps(src, k2, a, b)
    val result2 = c2.asF32x4().asArray()
    val expectedResult2: FloatArray = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f)
    check(result2 == expectedResult2)
}

public fun testMmMaskzDpbf16Ps() {
    val aArray = floatArrayOf(8.5f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f)
    val a1 = Simd<Float>((aArray).toList()).asM128()
    val b1 = Simd<Float>((bArray).toList()).asM128()
    val k: Mmask8 = 0xf3u.toUByte()
    val src = Simd<Float>((floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f)).toList()).asM128()
    val a: M128bh = mmCvtne2psPbh(a1, a1)
    val b: M128bh = mmCvtne2psPbh(b1, b1)
    val c: M128 = mmMaskzDpbf16Ps(k, src, a, b)
    val result = c.asF32x4().asArray()
    val expectedResult: FloatArray = floatArrayOf(-18.0f, -52.0f, 0.0f, 0.0f)
    check(result == expectedResult)
    val k1: Mmask8 = 0xffu.toUByte()
    val c1: M128 = mmMaskzDpbf16Ps(k1, src, a, b)
    val result1 = c1.asF32x4().asArray()
    val expectedResult1: FloatArray = floatArrayOf(-18.0f, -52.0f, -16.0f, -50.0f)
    check(result1 == expectedResult1)
    val k2: Mmask8 = 0u.toUByte()
    val c2: M128 = mmMaskzDpbf16Ps(k2, src, a, b)
    val result2 = c2.asF32x4().asArray()
    val expectedResult2: FloatArray = floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f)
    check(result2 == expectedResult2)
}

public fun testMm256Dpbf16Ps() {
    val aArray = floatArrayOf(8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    val a1 = Simd<Float>((aArray).toList()).asM256()
    val b1 = Simd<Float>((bArray).toList()).asM256()
    val src = Simd<Float>((floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)).toList()).asM256()
    val a: M256bh = mm256Cvtne2psPbh(a1, a1)
    val b: M256bh = mm256Cvtne2psPbh(b1, b1)
    val c: M256 = mm256Dpbf16Ps(src, a, b)
    val result = c.asF32x8().asArray()
    val expectedResult: FloatArray = floatArrayOf(-18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f)
    check(result == expectedResult)
}

public fun testMm256MaskDpbf16Ps() {
    val aArray = floatArrayOf(8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    val a1 = Simd<Float>((aArray).toList()).asM256()
    val b1 = Simd<Float>((bArray).toList()).asM256()
    val k: Mmask8 = 0x33u.toUByte()
    val src = Simd<Float>((floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)).toList()).asM256()
    val a: M256bh = mm256Cvtne2psPbh(a1, a1)
    val b: M256bh = mm256Cvtne2psPbh(b1, b1)
    val c: M256 = mm256MaskDpbf16Ps(src, k, a, b)
    val result = c.asF32x8().asArray()
    val expectedResult: FloatArray = floatArrayOf(-18.0f, -52.0f, 3.0f, 4.0f, -18.0f, -52.0f, 3.0f, 4.0f)
    check(result == expectedResult)
    val k1: Mmask8 = 0xffu.toUByte()
    val c1: M256 = mm256MaskDpbf16Ps(src, k1, a, b)
    val result1 = c1.asF32x8().asArray()
    val expectedResult1: FloatArray = floatArrayOf(-18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f)
    check(result1 == expectedResult1)
    val k2: Mmask8 = 0u.toUByte()
    val c2: M256 = mm256MaskDpbf16Ps(src, k2, a, b)
    val result2 = c2.asF32x8().asArray()
    val expectedResult2: FloatArray = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)
    check(result2 == expectedResult2)
}

public fun testMm256MaskzDpbf16Ps() {
    val aArray = floatArrayOf(8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    val a1 = Simd<Float>((aArray).toList()).asM256()
    val b1 = Simd<Float>((bArray).toList()).asM256()
    val k: Mmask8 = 0x33u.toUByte()
    val src = Simd<Float>((floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)).toList()).asM256()
    val a: M256bh = mm256Cvtne2psPbh(a1, a1)
    val b: M256bh = mm256Cvtne2psPbh(b1, b1)
    val c: M256 = mm256MaskzDpbf16Ps(k, src, a, b)
    val result = c.asF32x8().asArray()
    val expectedResult: FloatArray = floatArrayOf(-18.0f, -52.0f, 0.0f, 0.0f, -18.0f, -52.0f, 0.0f, 0.0f)
    check(result == expectedResult)
    val k1: Mmask8 = 0xffu.toUByte()
    val c1: M256 = mm256MaskzDpbf16Ps(k1, src, a, b)
    val result1 = c1.asF32x8().asArray()
    val expectedResult1: FloatArray = floatArrayOf(-18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f)
    check(result1 == expectedResult1)
    val k2: Mmask8 = 0u.toUByte()
    val c2: M256 = mm256MaskzDpbf16Ps(k2, src, a, b)
    val result2 = c2.asF32x8().asArray()
    val expectedResult2: FloatArray = floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    check(result2 == expectedResult2)
}

public fun testMm512Dpbf16Ps() {
    val aArray = floatArrayOf(8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    val a1 = Simd<Float>((aArray).toList()).asM512()
    val b1 = Simd<Float>((bArray).toList()).asM512()
    val src = Simd<Float>((floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)).toList()).asM512()
    val a: M512bh = mm512Cvtne2psPbh(a1, a1)
    val b: M512bh = mm512Cvtne2psPbh(b1, b1)
    val c: M512 = mm512Dpbf16Ps(src, a, b)
    val result = c.asF32x16().asArray()
    val expectedResult: FloatArray = floatArrayOf(-18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f)
    check(result == expectedResult)
}

public fun testMm512MaskDpbf16Ps() {
    val aArray = floatArrayOf(8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    val a1 = Simd<Float>((aArray).toList()).asM512()
    val b1 = Simd<Float>((bArray).toList()).asM512()
    val k: Mmask16 = 0x3333u.toUShort()
    val src = Simd<Float>((floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)).toList()).asM512()
    val a: M512bh = mm512Cvtne2psPbh(a1, a1)
    val b: M512bh = mm512Cvtne2psPbh(b1, b1)
    val c: M512 = mm512MaskDpbf16Ps(src, k, a, b)
    val result = c.asF32x16().asArray()
    val expectedResult: FloatArray = floatArrayOf(-18.0f, -52.0f, 3.0f, 4.0f, -18.0f, -52.0f, 3.0f, 4.0f, -18.0f, -52.0f, 3.0f, 4.0f, -18.0f, -52.0f, 3.0f, 4.0f)
    check(result == expectedResult)
    val k1: Mmask16 = 0xffffu.toUShort()
    val c1: M512 = mm512MaskDpbf16Ps(src, k1, a, b)
    val result1 = c1.asF32x16().asArray()
    val expectedResult1: FloatArray = floatArrayOf(-18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f)
    check(result1 == expectedResult1)
    val k2: Mmask16 = 0u.toUShort()
    val c2: M512 = mm512MaskDpbf16Ps(src, k2, a, b)
    val result2 = c2.asF32x16().asArray()
    val expectedResult2: FloatArray = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)
    check(result2 == expectedResult2)
}

public fun testMm512MaskzDpbf16Ps() {
    val aArray = floatArrayOf(8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f, 8.5f, 10.5f, 3.75f, 50.25f)
    val bArray = floatArrayOf(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    val a1 = Simd<Float>((aArray).toList()).asM512()
    val b1 = Simd<Float>((bArray).toList()).asM512()
    val k: Mmask16 = 0x3333u.toUShort()
    val src = Simd<Float>((floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)).toList()).asM512()
    val a: M512bh = mm512Cvtne2psPbh(a1, a1)
    val b: M512bh = mm512Cvtne2psPbh(b1, b1)
    val c: M512 = mm512MaskzDpbf16Ps(k, src, a, b)
    val result = c.asF32x16().asArray()
    val expectedResult: FloatArray = floatArrayOf(-18.0f, -52.0f, 0.0f, 0.0f, -18.0f, -52.0f, 0.0f, 0.0f, -18.0f, -52.0f, 0.0f, 0.0f, -18.0f, -52.0f, 0.0f, 0.0f)
    check(result == expectedResult)
    val k1: Mmask16 = 0xffffu.toUShort()
    val c1: M512 = mm512MaskzDpbf16Ps(k1, src, a, b)
    val result1 = c1.asF32x16().asArray()
    val expectedResult1: FloatArray = floatArrayOf(-18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f, -18.0f, -52.0f, -16.0f, -50.0f)
    check(result1 == expectedResult1)
    val k2: Mmask16 = 0u.toUShort()
    val c2: M512 = mm512MaskzDpbf16Ps(k2, src, a, b)
    val result2 = c2.asF32x16().asArray()
    val expectedResult2: FloatArray = floatArrayOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    check(result2 == expectedResult2)
}

public val BF16_ONE: UShort = 0x3F80u.toUShort()

public val BF16_TWO: UShort = 0x4000u.toUShort()

public val BF16_THREE: UShort = 0x4040u.toUShort()

public val BF16_FOUR: UShort = 0x4080u.toUShort()

public val BF16_FIVE: UShort = 0x40A0u.toUShort()

public val BF16_SIX: UShort = 0x40C0u.toUShort()

public val BF16_SEVEN: UShort = 0x40E0u.toUShort()

public val BF16_EIGHT: UShort = 0x4100u.toUShort()

public fun testMm512CvtpbhPs() {
    val a = m256bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT, BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    val r = mm512CvtpbhPs(a)
    val e = mm512SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskCvtpbhPs() {
    val a = m256bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT, BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    val src = mm512SetrPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f, 9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val k = 0xAAAA
    val r = mm512MaskCvtpbhPs(src, k, a)
    val e = mm512SetrPs(9.0f, 2.0f, 11.0f, 4.0f, 13.0f, 6.0f, 15.0f, 8.0f, 9.0f, 2.0f, 11.0f, 4.0f, 13.0f, 6.0f, 15.0f, 8.0f)
    assertEqM512(r, e)
}

public fun testMm512MaskzCvtpbhPs() {
    val a = m256bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT, BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    val k = 0xAAAA
    val r = mm512MaskzCvtpbhPs(k, a)
    val e = mm512SetrPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 6.0f, 0.0f, 8.0f, 0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 6.0f, 0.0f, 8.0f)
    assertEqM512(r, e)
}

public fun testMm256CvtpbhPs() {
    val a = m128bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    val r = mm256CvtpbhPs(a)
    val e = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskCvtpbhPs() {
    val a = m128bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    val src = mm256SetrPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val k = 0xAA
    val r = mm256MaskCvtpbhPs(src, k, a)
    val e = mm256SetrPs(9.0f, 2.0f, 11.0f, 4.0f, 13.0f, 6.0f, 15.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskzCvtpbhPs() {
    val a = m128bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, BF16_FIVE, BF16_SIX, BF16_SEVEN, BF16_EIGHT))
    val k = 0xAA
    val r = mm256MaskzCvtpbhPs(k, a)
    val e = mm256SetrPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 6.0f, 0.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMmCvtpbhPs() {
    val a = m128bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort()))
    val r = mmCvtpbhPs(a)
    val e = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmMaskCvtpbhPs() {
    val a = m128bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort()))
    val src = mmSetrPs(9.0f, 10.0f, 11.0f, 12.0f)
    val k = 0xA
    val r = mmMaskCvtpbhPs(src, k, a)
    val e = mmSetrPs(9.0f, 2.0f, 11.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmMaskzCvtpbhPs() {
    val a = m128bh(ushortArrayOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR, 0u.toUShort(), 0u.toUShort(), 0u.toUShort(), 0u.toUShort()))
    val k = 0xA
    val r = mmMaskzCvtpbhPs(k, a)
    val e = mmSetrPs(0.0f, 2.0f, 0.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmCvtsbhSs() {
    val r = mmCvtsbhSs(Bf16.fromBits(BF16_ONE))
    check(r == 1.0f.toFloat())
}

public fun testMmCvtnepsPbh() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r: U16x4 = transmuteCopy(mmCvtnepsPbh(a))
    val e = Simd<UShort>(listOf(BF16_ONE, BF16_TWO, BF16_THREE, BF16_FOUR))
    check(r == e)
}

public fun testMmMaskCvtnepsPbh() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val src = m128bh(listOf(5, 6, 7, 8, 0.inv(), 0.inv(), 0.inv(), 0.inv()))
    val k = 0xA
    val r: U16x4 = transmuteCopy(mmMaskCvtnepsPbh(src, k, a))
    val e = Simd<UShort>(listOf(5u.toUShort(), BF16_TWO, 7u.toUShort(), BF16_FOUR))
    check(r == e)
}

public fun testMmMaskzCvtnepsPbh() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val k = 0xA
    val r: U16x4 = transmuteCopy(mmMaskzCvtnepsPbh(k, a))
    val e = Simd<UShort>(listOf(0u.toUShort(), BF16_TWO, 0u.toUShort(), BF16_FOUR))
    check(r == e)
}

public fun testMmCvtnessSbh() {
    val r = mmCvtnessSbh(1.0f)
    check(r.toBits() == BF16_ONE)
}
