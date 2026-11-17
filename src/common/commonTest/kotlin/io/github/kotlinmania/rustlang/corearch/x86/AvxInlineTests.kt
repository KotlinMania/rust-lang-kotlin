// port-lint: tests x86/avx.rs
package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.hint.blackBox
import io.github.kotlinmania.rustlang.core.intrinsics.simd.*
import io.github.kotlinmania.rustlang.core.mem.AlignedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.asPtr
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

// Advanced Vector Extensions (AVX)
//
// The references are:
//
// - [Intel 64 and IA-32 Architectures Software Developer's Manual Volume 2:
//   Instruction Set Reference, A-Z][intel64Ref]. - [AMD64 Architecture
//   Programmer's Manual, Volume 3: General-Purpose and System
//   Instructions][amd64Ref].
//
// [Wikipedia][wiki] provides a quick overview of the instructions available.
//
// [intel64Ref]: https://www.intel.com/content/dam/www/public/us/en/documents/manuals/64-ia-32-architectures-software-developer-instruction-set-reference-manual-325383.pdf
// [amd64Ref]: https://docs.amd.com/v/u/en-US/24594_3.37
// [wiki]: https://en.wikipedia.org/wiki/AdvancedVectorExtensions

public fun testMm256AddPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256AddPd(a, b)
    val e = mm256SetrPd(6.0, 8.0, 10.0, 12.0)
    assertEqM256d(r, e)
}

public fun testMm256AddPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetrPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm256AddPs(a, b)
    val e = mm256SetrPs(10.0f, 12.0f, 14.0f, 16.0f, 18.0f, 20.0f, 22.0f, 24.0f)
    assertEqM256(r, e)
}

public fun testMm256AndPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256Set1Pd(0.6)
    val r = mm256AndPd(a, b)
    val e = mm256Set1Pd(0.5)
    assertEqM256d(r, e)
}

public fun testMm256AndPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256Set1Ps(0.6f)
    val r = mm256AndPs(a, b)
    val e = mm256Set1Ps(0.5f)
    assertEqM256(r, e)
}

public fun testMm256OrPd() {
    val a = mm256Set1Pd(1.0)
    val b = mm256Set1Pd(0.6)
    val r = mm256OrPd(a, b)
    val e = mm256Set1Pd(1.2)
    assertEqM256d(r, e)
}

public fun testMm256OrPs() {
    val a = mm256Set1Ps(1.0f)
    val b = mm256Set1Ps(0.6f)
    val r = mm256OrPs(a, b)
    val e = mm256Set1Ps(1.2f)
    assertEqM256(r, e)
}

public fun testMm256ShufflePd() {
    val a = mm256SetrPd(1.0, 4.0, 5.0, 8.0)
    val b = mm256SetrPd(2.0, 3.0, 6.0, 7.0)
    val r = mm256ShufflePd(a, b, 0xFF)
    val e = mm256SetrPd(4.0, 3.0, 8.0, 7.0)
    assertEqM256d(r, e)
}

public fun testMm256ShufflePs() {
    val a = mm256SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm256SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm256ShufflePs(a, b, 0xF)
    val e = mm256SetrPs(8.0f, 8.0f, 2.0f, 2.0f, 16.0f, 16.0f, 10.0f, 10.0f)
    assertEqM256(r, e)
}

public fun testMm256AndnotPd() {
    val a = mm256Set1Pd(0.0)
    val b = mm256Set1Pd(0.6)
    val r = mm256AndnotPd(a, b)
    assertEqM256d(r, b)
}

public fun testMm256AndnotPs() {
    val a = mm256Set1Ps(0.0f)
    val b = mm256Set1Ps(0.6f)
    val r = mm256AndnotPs(a, b)
    assertEqM256(r, b)
}

public fun testMm256MaxPd() {
    val a = mm256SetrPd(1.0, 4.0, 5.0, 8.0)
    val b = mm256SetrPd(2.0, 3.0, 6.0, 7.0)
    val r = mm256MaxPd(a, b)
    val e = mm256SetrPd(2.0, 4.0, 6.0, 8.0)
    assertEqM256d(r, e)
    // > If the values being compared are both 0.0s (of either sign), the
    // > value in the second operand (source operand) is returned.
    val w = mm256MaxPd(mm256Set1Pd(0.0), mm256Set1Pd(-0.0))
    val x = mm256MaxPd(mm256Set1Pd(-0.0), mm256Set1Pd(0.0))
    val wu = mm256CastpdSi256(w).asU64x4()
    val xu = mm256CastpdSi256(x).asU64x4()
    check(wu == u64x4.splat(0x8000000000000000uL))
    check(xu == u64x4.splat(0uL))
    // > If only one value is a NaN (SNaN or QNaN) for this instruction, the
    // > second operand (source operand), either a NaN or a valid
    // > floating-point value, is written to the result.
    val y = mm256MaxPd(mm256Set1Pd(Double.NaN), mm256Set1Pd(0.0))
    val z = mm256MaxPd(mm256Set1Pd(0.0), mm256Set1Pd(Double.NaN))
    assertEqM256d(y, mm256Set1Pd(0.0))
    val zf = z.asF64x4().asArray()
    check(zf.iter().all({ f -> f.isNan() })) { "$zf" }
}

public fun testMm256MaxPs() {
    val a = mm256SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm256SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm256MaxPs(a, b)
    val e = mm256SetrPs(2.0f, 4.0f, 6.0f, 8.0f, 10.0f, 12.0f, 14.0f, 16.0f)
    assertEqM256(r, e)
    // > If the values being compared are both 0.0s (of either sign), the
    // > value in the second operand (source operand) is returned.
    val w = mm256MaxPs(mm256Set1Ps(0.0f), mm256Set1Ps(-0.0f))
    val x = mm256MaxPs(mm256Set1Ps(-0.0f), mm256Set1Ps(0.0f))
    val wu = mm256CastpsSi256(w).asU32x8()
    val xu = mm256CastpsSi256(x).asU32x8()
    check(wu == u32x8.splat(0x80000000u))
    check(xu == u32x8.splat(0u))
    // > If only one value is a NaN (SNaN or QNaN) for this instruction, the
    // > second operand (source operand), either a NaN or a valid
    // > floating-point value, is written to the result.
    val y = mm256MaxPs(mm256Set1Ps(Float.NaN), mm256Set1Ps(0.0f))
    val z = mm256MaxPs(mm256Set1Ps(0.0f), mm256Set1Ps(Float.NaN))
    assertEqM256(y, mm256Set1Ps(0.0f))
    val zf = z.asF32x8().asArray()
    check(zf.iter().all({ f -> f.isNan() })) { "$zf" }
}

public fun testMm256MinPd() {
    val a = mm256SetrPd(1.0, 4.0, 5.0, 8.0)
    val b = mm256SetrPd(2.0, 3.0, 6.0, 7.0)
    val r = mm256MinPd(a, b)
    val e = mm256SetrPd(1.0, 3.0, 5.0, 7.0)
    assertEqM256d(r, e)
    // > If the values being compared are both 0.0s (of either sign), the
    // > value in the second operand (source operand) is returned.
    val w = mm256MinPd(mm256Set1Pd(0.0), mm256Set1Pd(-0.0))
    val x = mm256MinPd(mm256Set1Pd(-0.0), mm256Set1Pd(0.0))
    val wu = mm256CastpdSi256(w).asU64x4()
    val xu = mm256CastpdSi256(x).asU64x4()
    check(wu == u64x4.splat(0x8000000000000000uL))
    check(xu == u64x4.splat(0uL))
    // > If only one value is a NaN (SNaN or QNaN) for this instruction, the
    // > second operand (source operand), either a NaN or a valid
    // > floating-point value, is written to the result.
    val y = mm256MinPd(mm256Set1Pd(Double.NaN), mm256Set1Pd(0.0))
    val z = mm256MinPd(mm256Set1Pd(0.0), mm256Set1Pd(Double.NaN))
    assertEqM256d(y, mm256Set1Pd(0.0))
    val zf = z.asF64x4().asArray()
    check(zf.iter().all({ f -> f.isNan() })) { "$zf" }
}

public fun testMm256MinPs() {
    val a = mm256SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm256SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm256MinPs(a, b)
    val e = mm256SetrPs(1.0f, 3.0f, 5.0f, 7.0f, 9.0f, 11.0f, 13.0f, 15.0f)
    assertEqM256(r, e)
    // > If the values being compared are both 0.0s (of either sign), the
    // > value in the second operand (source operand) is returned.
    val w = mm256MinPs(mm256Set1Ps(0.0f), mm256Set1Ps(-0.0f))
    val x = mm256MinPs(mm256Set1Ps(-0.0f), mm256Set1Ps(0.0f))
    val wu = mm256CastpsSi256(w).asU32x8()
    val xu = mm256CastpsSi256(x).asU32x8()
    check(wu == u32x8.splat(0x80000000u))
    check(xu == u32x8.splat(0u))
    // > If only one value is a NaN (SNaN or QNaN) for this instruction, the
    // > second operand (source operand), either a NaN or a valid
    // > floating-point value, is written to the result.
    val y = mm256MinPs(mm256Set1Ps(Float.NaN), mm256Set1Ps(0.0f))
    val z = mm256MinPs(mm256Set1Ps(0.0f), mm256Set1Ps(Float.NaN))
    assertEqM256(y, mm256Set1Ps(0.0f))
    val zf = z.asF32x8().asArray()
    check(zf.iter().all({ f -> f.isNan() })) { "$zf" }
}

public fun testMm256MulPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256MulPd(a, b)
    val e = mm256SetrPd(5.0, 12.0, 21.0, 32.0)
    assertEqM256d(r, e)
}

public fun testMm256MulPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetrPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm256MulPs(a, b)
    val e = mm256SetrPs(9.0f, 20.0f, 33.0f, 48.0f, 65.0f, 84.0f, 105.0f, 128.0f)
    assertEqM256(r, e)
}

public fun testMm256AddsubPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256AddsubPd(a, b)
    val e = mm256SetrPd(-4.0, 8.0, -4.0, 12.0)
    assertEqM256d(r, e)
}

public fun testMm256AddsubPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)
    val b = mm256SetrPs(5.0f, 6.0f, 7.0f, 8.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256AddsubPs(a, b)
    val e = mm256SetrPs(-4.0f, 8.0f, -4.0f, 12.0f, -4.0f, 8.0f, -4.0f, 12.0f)
    assertEqM256(r, e)
}

public fun testMm256SubPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256SubPd(a, b)
    val e = mm256SetrPd(-4.0, -4.0, -4.0, -4.0)
    assertEqM256d(r, e)
}

public fun testMm256SubPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, -1.0f, -2.0f, -3.0f, -4.0f)
    val b = mm256SetrPs(5.0f, 6.0f, 7.0f, 8.0f, 3.0f, 2.0f, 1.0f, 0.0f)
    val r = mm256SubPs(a, b)
    val e = mm256SetrPs(-4.0f, -4.0f, -4.0f, -4.0f, -4.0f, -4.0f, -4.0f, -4.0f)
    assertEqM256(r, e)
}

public fun testMm256RoundPd() {
    val a = mm256SetrPd(1.55, 2.2, 3.99, -1.2)
    val resultClosest = mm256RoundPd(a, 0x0)
    val resultDown = mm256RoundPd(a, 0x1)
    val resultUp = mm256RoundPd(a, 0x2)
    val expectedClosest = mm256SetrPd(2.0, 2.0, 4.0, -1.0)
    val expectedDown = mm256SetrPd(1.0, 2.0, 3.0, -2.0)
    val expectedUp = mm256SetrPd(2.0, 3.0, 4.0, -1.0)
    assertEqM256d(resultClosest, expectedClosest)
    assertEqM256d(resultDown, expectedDown)
    assertEqM256d(resultUp, expectedUp)
}

public fun testMm256FloorPd() {
    val a = mm256SetrPd(1.55, 2.2, 3.99, -1.2)
    val resultDown = mm256FloorPd(a)
    val expectedDown = mm256SetrPd(1.0, 2.0, 3.0, -2.0)
    assertEqM256d(resultDown, expectedDown)
}

public fun testMm256CeilPd() {
    val a = mm256SetrPd(1.55, 2.2, 3.99, -1.2)
    val resultUp = mm256CeilPd(a)
    val expectedUp = mm256SetrPd(2.0, 3.0, 4.0, -1.0)
    assertEqM256d(resultUp, expectedUp)
}

public fun testMm256RoundPs() {
    val a = mm256SetrPs(1.55f, 2.2f, 3.99f, -1.2f, 1.55f, 2.2f, 3.99f, -1.2f)
    val resultClosest = mm256RoundPs(a, 0x0)
    val resultDown = mm256RoundPs(a, 0x1)
    val resultUp = mm256RoundPs(a, 0x2)
    val expectedClosest = mm256SetrPs(2.0f, 2.0f, 4.0f, -1.0f, 2.0f, 2.0f, 4.0f, -1.0f)
    val expectedDown = mm256SetrPs(1.0f, 2.0f, 3.0f, -2.0f, 1.0f, 2.0f, 3.0f, -2.0f)
    val expectedUp = mm256SetrPs(2.0f, 3.0f, 4.0f, -1.0f, 2.0f, 3.0f, 4.0f, -1.0f)
    assertEqM256(resultClosest, expectedClosest)
    assertEqM256(resultDown, expectedDown)
    assertEqM256(resultUp, expectedUp)
}

public fun testMm256FloorPs() {
    val a = mm256SetrPs(1.55f, 2.2f, 3.99f, -1.2f, 1.55f, 2.2f, 3.99f, -1.2f)
    val resultDown = mm256FloorPs(a)
    val expectedDown = mm256SetrPs(1.0f, 2.0f, 3.0f, -2.0f, 1.0f, 2.0f, 3.0f, -2.0f)
    assertEqM256(resultDown, expectedDown)
}

public fun testMm256CeilPs() {
    val a = mm256SetrPs(1.55f, 2.2f, 3.99f, -1.2f, 1.55f, 2.2f, 3.99f, -1.2f)
    val resultUp = mm256CeilPs(a)
    val expectedUp = mm256SetrPs(2.0f, 3.0f, 4.0f, -1.0f, 2.0f, 3.0f, 4.0f, -1.0f)
    assertEqM256(resultUp, expectedUp)
}

public fun testMm256SqrtPd() {
    val a = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    val r = mm256SqrtPd(a)
    val e = mm256SetrPd(2.0, 3.0, 4.0, 5.0)
    assertEqM256d(r, e)
}

public fun testMm256SqrtPs() {
    val a = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 4.0f, 9.0f, 16.0f, 25.0f)
    val r = mm256SqrtPs(a)
    val e = mm256SetrPs(2.0f, 3.0f, 4.0f, 5.0f, 2.0f, 3.0f, 4.0f, 5.0f)
    assertEqM256(r, e)
}

public fun testMm256DivPs() {
    val a = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 4.0f, 9.0f, 16.0f, 25.0f)
    val b = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val r = mm256DivPs(a, b)
    val e = mm256SetrPs(1.0f, 3.0f, 8.0f, 5.0f, 0.5f, 1.0f, 0.25f, 0.5f)
    assertEqM256(r, e)
}

public fun testMm256DivPd() {
    val a = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    val b = mm256SetrPd(4.0, 3.0, 2.0, 5.0)
    val r = mm256DivPd(a, b)
    val e = mm256SetrPd(1.0, 3.0, 8.0, 5.0)
    assertEqM256d(r, e)
}

public fun testMm256BlendPd() {
    val a = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    val b = mm256SetrPd(4.0, 3.0, 2.0, 5.0)
    val r = mm256BlendPd(a, b, 0x0)
    assertEqM256d(r, mm256SetrPd(4.0, 9.0, 16.0, 25.0))
    val r1 = mm256BlendPd(a, b, 0x3)
    assertEqM256d(r1, mm256SetrPd(4.0, 3.0, 16.0, 25.0))
    val r2 = mm256BlendPd(a, b, 0xF)
    assertEqM256d(r2, mm256SetrPd(4.0, 3.0, 2.0, 5.0))
}

public fun testMm256BlendPs() {
    val a = mm256SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f)
    val b = mm256SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 10.0f, 11.0f, 14.0f, 15.0f)
    val r = mm256BlendPs(a, b, 0x0)
    assertEqM256(r, mm256SetrPs(1.0f, 4.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f))
    val r1 = mm256BlendPs(a, b, 0x3)
    assertEqM256(r1, mm256SetrPs(2.0f, 3.0f, 5.0f, 8.0f, 9.0f, 12.0f, 13.0f, 16.0f))
    val r2 = mm256BlendPs(a, b, 0xF)
    assertEqM256(r2, mm256SetrPs(2.0f, 3.0f, 6.0f, 7.0f, 9.0f, 12.0f, 13.0f, 16.0f))
}

public fun testMm256BlendvPd() {
    val a = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    val b = mm256SetrPd(4.0, 3.0, 2.0, 5.0)
    val c = mm256SetrPd(0.0, 0.0, (0.inv()).toDouble(), (0.inv()).toDouble())
    val r = mm256BlendvPd(a, b, c)
    val e = mm256SetrPd(4.0, 9.0, 2.0, 5.0)
    assertEqM256d(r, e)
}

public fun testMm256BlendvPs() {
    val a = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 4.0f, 9.0f, 16.0f, 25.0f)
    val b = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val c = mm256SetrPs(0.0f, 0.0f, 0.0f, 0.0f, (0.inv()).toFloat(), (0.inv()).toFloat(), (0.inv()).toFloat(), (0.inv()).toFloat())
    val r = mm256BlendvPs(a, b, c)
    val e = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    assertEqM256(r, e)
}

public fun testMm256DpPs() {
    val a = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 4.0f, 9.0f, 16.0f, 25.0f)
    val b = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val r = mm256DpPs(a, b, 0xFF)
    val e = mm256SetrPs(200.0f, 200.0f, 200.0f, 200.0f, 2387.0f, 2387.0f, 2387.0f, 2387.0f)
    assertEqM256(r, e)
}

public fun testMm256HaddPd() {
    val a = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    val b = mm256SetrPd(4.0, 3.0, 2.0, 5.0)
    val r = mm256HaddPd(a, b)
    val e = mm256SetrPd(13.0, 7.0, 41.0, 7.0)
    assertEqM256d(r, e)
    val a1 = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b1 = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r1 = mm256HaddPd(a1, b1)
    val e1 = mm256SetrPd(3.0, 11.0, 7.0, 15.0)
    assertEqM256d(r1, e1)
}

public fun testMm256HaddPs() {
    val a = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 4.0f, 9.0f, 16.0f, 25.0f)
    val b = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val r = mm256HaddPs(a, b)
    val e = mm256SetrPs(13.0f, 41.0f, 7.0f, 7.0f, 13.0f, 41.0f, 17.0f, 114.0f)
    assertEqM256(r, e)
    val a1 = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)
    val b1 = mm256SetrPs(5.0f, 6.0f, 7.0f, 8.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r1 = mm256HaddPs(a1, b1)
    val e1 = mm256SetrPs(3.0f, 7.0f, 11.0f, 15.0f, 3.0f, 7.0f, 11.0f, 15.0f)
    assertEqM256(r1, e1)
}

public fun testMm256HsubPd() {
    val a = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    val b = mm256SetrPd(4.0, 3.0, 2.0, 5.0)
    val r = mm256HsubPd(a, b)
    val e = mm256SetrPd(-5.0, 1.0, -9.0, -3.0)
    assertEqM256d(r, e)
    val a1 = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b1 = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r1 = mm256HsubPd(a1, b1)
    val e1 = mm256SetrPd(-1.0, -1.0, -1.0, -1.0)
    assertEqM256d(r1, e1)
}

public fun testMm256HsubPs() {
    val a = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 4.0f, 9.0f, 16.0f, 25.0f)
    val b = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val r = mm256HsubPs(a, b)
    val e = mm256SetrPs(-5.0f, -9.0f, 1.0f, -3.0f, -5.0f, -9.0f, -1.0f, 14.0f)
    assertEqM256(r, e)
    val a1 = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)
    val b1 = mm256SetrPs(5.0f, 6.0f, 7.0f, 8.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r1 = mm256HsubPs(a1, b1)
    val e1 = mm256SetrPs(-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    assertEqM256(r1, e1)
}

public fun testMm256XorPd() {
    val a = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    val b = mm256Set1Pd(0.0)
    val r = mm256XorPd(a, b)
    assertEqM256d(r, a)
}

public fun testMm256XorPs() {
    val a = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 4.0f, 9.0f, 16.0f, 25.0f)
    val b = mm256Set1Ps(0.0f)
    val r = mm256XorPs(a, b)
    assertEqM256(r, a)
}

public fun testMmCmpPd() {
    val a = mmSetrPd(4.0, 9.0)
    val b = mmSetrPd(4.0, 3.0)
    val r = mmCmpPd(a, b, CMP_GE_OS)
    check(getM128d(r, 0).isNan())
    check(getM128d(r, 1).isNan())
}

public fun testMm256CmpPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256CmpPd(a, b, CMP_GE_OS)
    val e = mm256Set1Pd(0.0)
    assertEqM256d(r, e)
}

public fun testMmCmpPs() {
    val a = mmSetrPs(4.0f, 3.0f, 2.0f, 5.0f)
    val b = mmSetrPs(4.0f, 9.0f, 16.0f, 25.0f)
    val r = mmCmpPs(a, b, CMP_GE_OS)
    check(getM128(r, 0).isNan())
    check(getM128(r, 1) == 0.0)
    check(getM128(r, 2) == 0.0)
    check(getM128(r, 3) == 0.0)
}

public fun testMm256CmpPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 1.0f, 2.0f, 3.0f, 4.0f)
    val b = mm256SetrPs(5.0f, 6.0f, 7.0f, 8.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256CmpPs(a, b, CMP_GE_OS)
    val e = mm256Set1Ps(0.0f)
    assertEqM256(r, e)
}

public fun testMmCmpSd() {
    val a = mmSetrPd(4.0, 9.0)
    val b = mmSetrPd(4.0, 3.0)
    val r = mmCmpSd(a, b, CMP_GE_OS)
    check(getM128d(r, 0).isNan())
    check(getM128d(r, 1) == 9.0)
}

public fun testMmCmpSs() {
    val a = mmSetrPs(4.0f, 3.0f, 2.0f, 5.0f)
    val b = mmSetrPs(4.0f, 9.0f, 16.0f, 25.0f)
    val r = mmCmpSs(a, b, CMP_GE_OS)
    check(getM128(r, 0).isNan())
    check(getM128(r, 1) == 3.0)
    check(getM128(r, 2) == 2.0)
    check(getM128(r, 3) == 5.0)
}

public fun testMm256Cvtepi32Pd() {
    val a = mmSetrEpi32(4, 9, 16, 25)
    val r = mm256Cvtepi32Pd(a)
    val e = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    assertEqM256d(r, e)
}

public fun testMm256Cvtepi32Ps() {
    val a = mm256SetrEpi32(4, 9, 16, 25, 4, 9, 16, 25)
    val r = mm256Cvtepi32Ps(a)
    val e = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 4.0f, 9.0f, 16.0f, 25.0f)
    assertEqM256(r, e)
}

public fun testMm256CvtpdPs() {
    val a = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    val r = mm256CvtpdPs(a)
    val e = mmSetrPs(4.0f, 9.0f, 16.0f, 25.0f)
    assertEqM128(r, e)
}

public fun testMm256CvtpsEpi32() {
    val a = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 4.0f, 9.0f, 16.0f, 25.0f)
    val r = mm256CvtpsEpi32(a)
    val e = mm256SetrEpi32(4, 9, 16, 25, 4, 9, 16, 25)
    assertEqM256i(r, e)
}

public fun testMm256CvtpsPd() {
    val a = mmSetrPs(4.0f, 9.0f, 16.0f, 25.0f)
    val r = mm256CvtpsPd(a)
    val e = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    assertEqM256d(r, e)
}

public fun testMm256CvtsdF64() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256CvtsdF64(a)
    check(r == 1.0.toDouble())
}

public fun testMm256CvttpdEpi32() {
    val a = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    val r = mm256CvttpdEpi32(a)
    val e = mmSetrEpi32(4, 9, 16, 25)
    assertEqM128i(r, e)
}

public fun testMm256CvtpdEpi32() {
    val a = mm256SetrPd(4.0, 9.0, 16.0, 25.0)
    val r = mm256CvtpdEpi32(a)
    val e = mmSetrEpi32(4, 9, 16, 25)
    assertEqM128i(r, e)
}

public fun testMm256CvttpsEpi32() {
    val a = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 4.0f, 9.0f, 16.0f, 25.0f)
    val r = mm256CvttpsEpi32(a)
    val e = mm256SetrEpi32(4, 9, 16, 25, 4, 9, 16, 25)
    assertEqM256i(r, e)
}

public fun testMm256Extractf128Ps() {
    val a = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val r = mm256Extractf128Ps(a, 0)
    val e = mmSetrPs(4.0f, 3.0f, 2.0f, 5.0f)
    assertEqM128(r, e)
}

public fun testMm256Extractf128Pd() {
    val a = mm256SetrPd(4.0, 3.0, 2.0, 5.0)
    val r = mm256Extractf128Pd(a, 0)
    val e = mmSetrPd(4.0, 3.0)
    assertEqM128d(r, e)
}

public fun testMm256Extractf128Si256() {
    val a = mm256SetrEpi64x(4L, 3L, 2L, 5L)
    val r = mm256Extractf128Si256(a, 0)
    val e = mmSetrEpi64x(4, 3)
    assertEqM128i(r, e)
}

public fun testMm256ExtractEpi32() {
    val a = mm256SetrEpi32(-1, 1, 2, 3, 4, 5, 6, 7)
    val r1 = mm256ExtractEpi32(a, 0)
    val r2 = mm256ExtractEpi32(a, 3)
    check(r1 == -1)
    check(r2 == 3)
}

public fun testMm256Cvtsi256Si32() {
    val a = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256Cvtsi256Si32(a)
    check(r == 1)
}

public fun testMm256Zeroall() {
    mm256Zeroall()
}

public fun testMm256Zeroupper() {
    mm256Zeroupper()
}

public fun testMm256PermutevarPs() {
    val a = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val b = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256PermutevarPs(a, b)
    val e = mm256SetrPs(3.0f, 2.0f, 5.0f, 4.0f, 9.0f, 64.0f, 50.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMmPermutevarPs() {
    val a = mmSetrPs(4.0f, 3.0f, 2.0f, 5.0f)
    val b = mmSetrEpi32(1, 2, 3, 4)
    val r = mmPermutevarPs(a, b)
    val e = mmSetrPs(3.0f, 2.0f, 5.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMm256PermutePs() {
    val a = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val r = mm256PermutePs(a, 0x1b)
    val e = mm256SetrPs(5.0f, 2.0f, 3.0f, 4.0f, 50.0f, 64.0f, 9.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMmPermutePs() {
    val a = mmSetrPs(4.0f, 3.0f, 2.0f, 5.0f)
    val r = mmPermutePs(a, 0x1b)
    val e = mmSetrPs(5.0f, 2.0f, 3.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMm256PermutevarPd() {
    val a = mm256SetrPd(4.0, 3.0, 2.0, 5.0)
    val b = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val r = mm256PermutevarPd(a, b)
    val e = mm256SetrPd(4.0, 3.0, 5.0, 2.0)
    assertEqM256d(r, e)
}

public fun testMmPermutevarPd() {
    val a = mmSetrPd(4.0, 3.0)
    val b = mmSetrEpi64x(3, 0)
    val r = mmPermutevarPd(a, b)
    val e = mmSetrPd(3.0, 4.0)
    assertEqM128d(r, e)
}

public fun testMm256PermutePd() {
    val a = mm256SetrPd(4.0, 3.0, 2.0, 5.0)
    val r = mm256PermutePd(a, 5)
    val e = mm256SetrPd(3.0, 4.0, 5.0, 2.0)
    assertEqM256d(r, e)
}

public fun testMmPermutePd() {
    val a = mmSetrPd(4.0, 3.0)
    val r = mmPermutePd(a, 1)
    val e = mmSetrPd(3.0, 4.0)
    assertEqM128d(r, e)
}

public fun testMm256Permute2f128Ps() {
    val a = mm256SetrPs(11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f, 17.0f, 18.0f)
    val b = mm256SetrPs(21.0f, 22.0f, 23.0f, 24.0f, 25.0f, 26.0f, 27.0f, 28.0f)
    val r = mm256Permute2f128Ps(a, b, 0x13)
    val e = mm256SetrPs(25.0f, 26.0f, 27.0f, 28.0f, 15.0f, 16.0f, 17.0f, 18.0f)
    assertEqM256(r, e)
    // Setting bits 3 or 7 (zero-indexed) zeroes the corresponding field.
    val r1 = mm256Permute2f128Ps(a, b, 0x9B)
    val z = mm256SetrPs(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM256(r1, z)
}

public fun testMm256Permute2f128Pd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256Permute2f128Pd(a, b, 0x31)
    val e = mm256SetrPd(3.0, 4.0, 7.0, 8.0)
    assertEqM256d(r, e)
    // Setting bits 3 or 7 (zero-indexed) zeroes the corresponding field.
    val r1 = mm256Permute2f128Pd(a, b, 0xB9)
    val e1 = mm256SetrPd(0.0, 0.0, 0.0, 0.0)
    assertEqM256d(r1, e1)
}

public fun testMm256Permute2f128Si256() {
    val a = mm256SetrEpi32(11, 12, 13, 14, 15, 16, 17, 18)
    val b = mm256SetrEpi32(21, 22, 23, 24, 25, 26, 27, 28)
    val r = mm256Permute2f128Si256(a, b, 0x20)
    val e = mm256SetrEpi32(11, 12, 13, 14, 21, 22, 23, 24)
    assertEqM256i(r, e)
    // Setting bits 3 or 7 (zero-indexed) zeroes the corresponding field.
    val r1 = mm256Permute2f128Si256(a, b, 0xA8)
    val e1 = mm256SetrEpi32(0, 0, 0, 0, 0, 0, 0, 0)
    assertEqM256i(r1, e1)
}

public fun testMm256BroadcastSs() {
    val r = mm256BroadcastSs(3.0f)
    val e = mm256Set1Ps(3.0f)
    assertEqM256(r, e)
}

public fun testMmBroadcastSs() {
    val r = mmBroadcastSs(3.0f)
    val e = mmSet1Ps(3.0f)
    assertEqM128(r, e)
}

public fun testMm256BroadcastSd() {
    val r = mm256BroadcastSd(3.0)
    val e = mm256Set1Pd(3.0)
    assertEqM256d(r, e)
}

public fun testMm256BroadcastPs() {
    val a = mmSetrPs(4.0f, 3.0f, 2.0f, 5.0f)
    val r = mm256BroadcastPs(a)
    val e = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 4.0f, 3.0f, 2.0f, 5.0f)
    assertEqM256(r, e)
}

public fun testMm256BroadcastPd() {
    val a = mmSetrPd(4.0, 3.0)
    val r = mm256BroadcastPd(a)
    val e = mm256SetrPd(4.0, 3.0, 4.0, 3.0)
    assertEqM256d(r, e)
}

public fun testMm256Insertf128Ps() {
    val a = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val b = mmSetrPs(4.0f, 9.0f, 16.0f, 25.0f)
    val r = mm256Insertf128Ps(a, b, 0)
    val e = mm256SetrPs(4.0f, 9.0f, 16.0f, 25.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    assertEqM256(r, e)
}

public fun testMm256Insertf128Pd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mmSetrPd(5.0, 6.0)
    val r = mm256Insertf128Pd(a, b, 0)
    val e = mm256SetrPd(5.0, 6.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256Insertf128Si256() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val b = mmSetrEpi64x(5, 6)
    val r = mm256Insertf128Si256(a, b, 0)
    val e = mm256SetrEpi64x(5L, 6L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256InsertEpi8() {
    val a = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm256InsertEpi8(a, 0, 31)
    val e = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 0)
    assertEqM256i(r, e)
}

public fun testMm256InsertEpi16() {
    val a = mm256SetrEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val r = mm256InsertEpi16(a, 0, 15)
    val e = mm256SetrEpi16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0)
    assertEqM256i(r, e)
}

public fun testMm256InsertEpi32() {
    val a = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    val r = mm256InsertEpi32(a, 0, 7)
    val e = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 0)
    assertEqM256i(r, e)
}

public fun testMm256LoadPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val p = constPtrFromRustCast(addrOf(a))
    val r = mm256LoadPd(p)
    val e = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256StorePd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256UndefinedPd()
    run {
        mm256StorePd(mutPtrFromRustCast(addrOfMut(r)), a)
    }
    assertEqM256d(r, a)
}

public fun testMm256LoadPs() {
    val a = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val p = constPtrFromRustCast(addrOf(a))
    val r = mm256LoadPs(p)
    val e = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    assertEqM256(r, e)
}

public fun testMm256StorePs() {
    val a = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    val r = mm256UndefinedPs()
    run {
        mm256StorePs(mutPtrFromRustCast(addrOfMut(r)), a)
    }
    assertEqM256(r, a)
}

public fun testMm256LoaduPd() {
    val a = doubleArrayOf(1.0, 2.0, 3.0, 4.0)
    val p = a.asPtr()
    val r = mm256LoaduPd(blackBox(p))
    val e = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256StoreuPd() {
    val a = mm256Set1Pd(9.0)
    val r = mm256UndefinedPd()
    run {
        mm256StoreuPd(mutPtrFromRustCast(addrOfMut(r)), a)
    }
    assertEqM256d(r, a)
}

public fun testMm256LoaduPs() {
    val a = listOf(4.0, 3.0, 2.0, 5.0, 8.0, 9.0, 64.0, 50.0)
    val p = a.asPtr()
    val r = mm256LoaduPs(blackBox(p))
    val e = mm256SetrPs(4.0f, 3.0f, 2.0f, 5.0f, 8.0f, 9.0f, 64.0f, 50.0f)
    assertEqM256(r, e)
}

public fun testMm256StoreuPs() {
    val a = mm256Set1Ps(9.0f)
    val r = mm256UndefinedPs()
    run {
        mm256StoreuPs(mutPtrFromRustCast(addrOfMut(r)), a)
    }
    assertEqM256(r, a)
}

public fun testMm256LoadSi256() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val p = addrOf(a)
    val r = mm256LoadSi256(p)
    val e = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256StoreSi256() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val r = mm256UndefinedSi256()
    run {
        mm256StoreSi256(addrOfMut(r), a)
    }
    assertEqM256i(r, a)
}

public fun testMm256LoaduSi256() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val p = addrOf(a)
    val r = mm256LoaduSi256(blackBox(p))
    val e = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, e)
}

public fun testMm256StoreuSi256() {
    val a = mm256Set1Epi8(9)
    val r = mm256UndefinedSi256()
    run {
        mm256StoreuSi256(addrOfMut(r), a)
    }
    assertEqM256i(r, a)
}

public fun testMm256MaskloadPd() {
    val a = doubleArrayOf(1.0, 2.0, 3.0, 4.0)
    val p = a.asPtr()
    val mask = mm256SetrEpi64x(0L, 0L.inv(), 0L, 0L.inv())
    val r = mm256MaskloadPd(blackBox(p), mask)
    val e = mm256SetrPd(0.0, 2.0, 0.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256MaskstorePd() {
    val r = mm256Set1Pd(0.0)
    val mask = mm256SetrEpi64x(0L, 0L.inv(), 0L, 0L.inv())
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    run {
        mm256MaskstorePd(mutPtrFromRustCast(addrOfMut(r)), mask, a)
    }
    val e = mm256SetrPd(0.0, 2.0, 0.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMmMaskloadPd() {
    val a = doubleArrayOf(1.0, 2.0)
    val p = a.asPtr()
    val mask = mmSetrEpi64x(0, 0.inv())
    val r = mmMaskloadPd(blackBox(p), mask)
    val e = mmSetrPd(0.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMmMaskstorePd() {
    val r = mmSet1Pd(0.0)
    val mask = mmSetrEpi64x(0, 0.inv())
    val a = mmSetrPd(1.0, 2.0)
    run {
        mmMaskstorePd(mutPtrFromRustCast(addrOfMut(r)), mask, a)
    }
    val e = mmSetrPd(0.0, 2.0)
    assertEqM128d(r, e)
}

public fun testMm256MaskloadPs() {
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val p = a.asPtr()
    val mask = mm256SetrEpi32(0, 0.inv(), 0, 0.inv(), 0, 0.inv(), 0, 0.inv())
    val r = mm256MaskloadPs(blackBox(p), mask)
    val e = mm256SetrPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 6.0f, 0.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MaskstorePs() {
    val r = mm256Set1Ps(0.0f)
    val mask = mm256SetrEpi32(0, 0.inv(), 0, 0.inv(), 0, 0.inv(), 0, 0.inv())
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    run {
        mm256MaskstorePs(mutPtrFromRustCast(addrOfMut(r)), mask, a)
    }
    val e = mm256SetrPs(0.0f, 2.0f, 0.0f, 4.0f, 0.0f, 6.0f, 0.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMmMaskloadPs() {
    val a = floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f)
    val p = a.asPtr()
    val mask = mmSetrEpi32(0, 0.inv(), 0, 0.inv())
    val r = mmMaskloadPs(blackBox(p), mask)
    val e = mmSetrPs(0.0f, 2.0f, 0.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMmMaskstorePs() {
    val r = mmSet1Ps(0.0f)
    val mask = mmSetrEpi32(0, 0.inv(), 0, 0.inv())
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    run {
        mmMaskstorePs(mutPtrFromRustCast(addrOfMut(r)), mask, a)
    }
    val e = mmSetrPs(0.0f, 2.0f, 0.0f, 4.0f)
    assertEqM128(r, e)
}

public fun testMm256MovehdupPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MovehdupPs(a)
    val e = mm256SetrPs(2.0f, 2.0f, 4.0f, 4.0f, 6.0f, 6.0f, 8.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256MoveldupPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256MoveldupPs(a)
    val e = mm256SetrPs(1.0f, 1.0f, 3.0f, 3.0f, 5.0f, 5.0f, 7.0f, 7.0f)
    assertEqM256(r, e)
}

public fun testMm256MovedupPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256MovedupPd(a)
    val e = mm256SetrPd(1.0, 1.0, 3.0, 3.0)
    assertEqM256d(r, e)
}

public fun testMm256LddquSi256() {
    val a = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val p = addrOf(a)
    val r = mm256LddquSi256(blackBox(p))
    val e = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM256i(r, e)
}

// Inline asm (for non-temporal store), which is not supported by Miri
public fun testMm256StreamSi256() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val r = mm256UndefinedSi256()
    run {
        mm256StreamSi256(addrOfMut(r), a)
    }
    mmSfence()
    assertEqM256i(r, a)
}

// Inline asm (for non-temporal store), which is not supported by Miri
public fun testMm256StreamPd() {
    open class Memory(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout(),
    ) {
        public var data: DoubleArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: DoubleArray, fieldLayout: TypeLayout<DoubleArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Memory> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Memory(ptr, layout) }, { it.data })
    }
    val a = mm256Set1Pd(7.0)
    val mem = Memory(data = FloatArray(4) { -1.0f })
    run {
        mm256StreamPd(addrOfMut(mem.data[0]), a)
    }
    mmSfence()
    for (i in 0 until 4) {
        check(mem.data[i] == getM256d(a, i))
    }
}

// Inline asm (for non-temporal store), which is not supported by Miri
public fun testMm256StreamPs() {
    open class Memory(
        public val fieldStorage: MutPtr,
        public val fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout(),
    ) {
        public var data: FloatArray
            get() = fieldLayout.readNative(fieldStorage.rawAddress)
            set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

        public constructor(data: FloatArray, fieldLayout: TypeLayout<FloatArray> = UnresolvedTypeLayout()) : this(allocateNativeCell(data, AlignedTypeLayout(fieldLayout, 32L)), AlignedTypeLayout(fieldLayout, 32L))

        public fun pointerLayout(): TypeLayout<Memory> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Memory(ptr, layout) }, { it.data })
    }
    val a = mm256Set1Ps(7.0f)
    val mem = Memory(data = FloatArray(8) { -1.0f })
    run {
        mm256StreamPs(addrOfMut(mem.data[0]), a)
    }
    mmSfence()
    for (i in 0 until 8) {
        check(mem.data[i] == getM256(a, i))
    }
}

public fun testMm256RcpPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256RcpPs(a)
    val e = mm256SetrPs(0.99975586f, 0.49987793f, 0.33325195f, 0.24993896f, 0.19995117f, 0.16662598f, 0.14282227f, 0.12496948f)
    val relErr = 0.00048828125
    for (i in 0 until 8) {
        assertApproxEq(getM256(r, i), getM256(e, i), 2.0 * relErr)
    }
}

public fun testMm256RsqrtPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256RsqrtPs(a)
    val e = mm256SetrPs(0.99975586f, 0.7069092f, 0.5772705f, 0.49987793f, 0.44714355f, 0.40820313f, 0.3779297f, 0.3534546f)
    val relErr = 0.00048828125
    for (i in 0 until 8) {
        assertApproxEq(getM256(r, i), getM256(e, i), 2.0 * relErr)
    }
}

public fun testMm256UnpackhiPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256UnpackhiPd(a, b)
    val e = mm256SetrPd(2.0, 6.0, 4.0, 8.0)
    assertEqM256d(r, e)
}

public fun testMm256UnpackhiPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetrPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm256UnpackhiPs(a, b)
    val e = mm256SetrPs(3.0f, 11.0f, 4.0f, 12.0f, 7.0f, 15.0f, 8.0f, 16.0f)
    assertEqM256(r, e)
}

public fun testMm256UnpackloPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256UnpackloPd(a, b)
    val e = mm256SetrPd(1.0, 5.0, 3.0, 7.0)
    assertEqM256d(r, e)
}

public fun testMm256UnpackloPs() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val b = mm256SetrPs(9.0f, 10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f)
    val r = mm256UnpackloPs(a, b)
    val e = mm256SetrPs(1.0f, 9.0f, 2.0f, 10.0f, 5.0f, 13.0f, 6.0f, 14.0f)
    assertEqM256(r, e)
}

public fun testMm256TestzSi256() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetrEpi64x(5L, 6L, 7L, 8L)
    val r = mm256TestzSi256(a, b)
    check(r == 0)
    val b1 = mm256Set1Epi64x(0L)
    val r1 = mm256TestzSi256(a, b1)
    check(r1 == 1)
}

public fun testMm256TestcSi256() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetrEpi64x(5L, 6L, 7L, 8L)
    val r = mm256TestcSi256(a, b)
    check(r == 0)
    val b1 = mm256Set1Epi64x(0L)
    val r1 = mm256TestcSi256(a, b1)
    check(r1 == 1)
}

public fun testMm256TestnzcSi256() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val b = mm256SetrEpi64x(5L, 6L, 7L, 8L)
    val r = mm256TestnzcSi256(a, b)
    check(r == 1)
    val a1 = mm256SetrEpi64x(0L, 0L, 0L, 0L)
    val b1 = mm256SetrEpi64x(0L, 0L, 0L, 0L)
    val r1 = mm256TestnzcSi256(a1, b1)
    check(r1 == 0)
}

public fun testMm256TestzPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256TestzPd(a, b)
    check(r == 1)
    val a1 = mm256Set1Pd(-1.0)
    val r1 = mm256TestzPd(a1, a1)
    check(r1 == 0)
}

public fun testMm256TestcPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256TestcPd(a, b)
    check(r == 1)
    val a1 = mm256Set1Pd(1.0)
    val b1 = mm256Set1Pd(-1.0)
    val r1 = mm256TestcPd(a1, b1)
    check(r1 == 0)
}

public fun testMm256TestnzcPd() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val b = mm256SetrPd(5.0, 6.0, 7.0, 8.0)
    val r = mm256TestnzcPd(a, b)
    check(r == 0)
    val a1 = mm256SetrPd(1.0, -1.0, -1.0, -1.0)
    val b1 = mm256SetrPd(-1.0, -1.0, 1.0, 1.0)
    val r1 = mm256TestnzcPd(a1, b1)
    check(r1 == 1)
}

public fun testMmTestzPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 6.0)
    val r = mmTestzPd(a, b)
    check(r == 1)
    val a1 = mmSet1Pd(-1.0)
    val r1 = mmTestzPd(a1, a1)
    check(r1 == 0)
}

public fun testMmTestcPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 6.0)
    val r = mmTestcPd(a, b)
    check(r == 1)
    val a1 = mmSet1Pd(1.0)
    val b1 = mmSet1Pd(-1.0)
    val r1 = mmTestcPd(a1, b1)
    check(r1 == 0)
}

public fun testMmTestnzcPd() {
    val a = mmSetrPd(1.0, 2.0)
    val b = mmSetrPd(5.0, 6.0)
    val r = mmTestnzcPd(a, b)
    check(r == 0)
    val a1 = mmSetrPd(1.0, -1.0)
    val b1 = mmSetrPd(-1.0, -1.0)
    val r1 = mmTestnzcPd(a1, b1)
    check(r1 == 1)
}

public fun testMm256TestzPs() {
    val a = mm256Set1Ps(1.0f)
    val r = mm256TestzPs(a, a)
    check(r == 1)
    val a1 = mm256Set1Ps(-1.0f)
    val r1 = mm256TestzPs(a1, a1)
    check(r1 == 0)
}

public fun testMm256TestcPs() {
    val a = mm256Set1Ps(1.0f)
    val r = mm256TestcPs(a, a)
    check(r == 1)
    val b = mm256Set1Ps(-1.0f)
    val r1 = mm256TestcPs(a, b)
    check(r1 == 0)
}

public fun testMm256TestnzcPs() {
    val a = mm256Set1Ps(1.0f)
    val r = mm256TestnzcPs(a, a)
    check(r == 0)
    val a1 = mm256SetrPs(1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    val b = mm256SetrPs(-1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    val r1 = mm256TestnzcPs(a1, b)
    check(r1 == 1)
}

public fun testMmTestzPs() {
    val a = mmSet1Ps(1.0f)
    val r = mmTestzPs(a, a)
    check(r == 1)
    val a1 = mmSet1Ps(-1.0f)
    val r1 = mmTestzPs(a1, a1)
    check(r1 == 0)
}

public fun testMmTestcPs() {
    val a = mmSet1Ps(1.0f)
    val r = mmTestcPs(a, a)
    check(r == 1)
    val b = mmSet1Ps(-1.0f)
    val r1 = mmTestcPs(a, b)
    check(r1 == 0)
}

public fun testMmTestnzcPs() {
    val a = mmSet1Ps(1.0f)
    val r = mmTestnzcPs(a, a)
    check(r == 0)
    val a1 = mmSetrPs(1.0f, -1.0f, -1.0f, -1.0f)
    val b = mmSetrPs(-1.0f, -1.0f, 1.0f, 1.0f)
    val r1 = mmTestnzcPs(a1, b)
    check(r1 == 1)
}

public fun testMm256MovemaskPd() {
    val a = mm256SetrPd(1.0, -2.0, 3.0, -4.0)
    val r = mm256MovemaskPd(a)
    check(r == 0xA)
}

public fun testMm256MovemaskPs() {
    val a = mm256SetrPs(1.0f, -2.0f, 3.0f, -4.0f, 1.0f, -2.0f, 3.0f, -4.0f)
    val r = mm256MovemaskPs(a)
    check(r == 0xAA)
}

public fun testMm256SetzeroPd() {
    val r = mm256SetzeroPd()
    assertEqM256d(r, mm256Set1Pd(0.0))
}

public fun testMm256SetzeroPs() {
    val r = mm256SetzeroPs()
    assertEqM256(r, mm256Set1Ps(0.0f))
}

public fun testMm256SetzeroSi256() {
    val r = mm256SetzeroSi256()
    assertEqM256i(r, mm256Set1Epi8(0))
}

public fun testMm256SetPd() {
    val r = mm256SetPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, mm256SetrPd(4.0, 3.0, 2.0, 1.0))
}

public fun testMm256SetPs() {
    val r = mm256SetPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, mm256SetrPs(8.0f, 7.0f, 6.0f, 5.0f, 4.0f, 3.0f, 2.0f, 1.0f))
}

public fun testMm256SetEpi8() {
    val r = mm256SetEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val e = mm256SetrEpi8(32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    assertEqM256i(r, e)
}

public fun testMm256SetEpi16() {
    val r = mm256SetEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val e = mm256SetrEpi16(16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
    assertEqM256i(r, e)
}

public fun testMm256SetEpi32() {
    val r = mm256SetEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM256i(r, mm256SetrEpi32(8, 7, 6, 5, 4, 3, 2, 1))
}

public fun testMm256SetEpi64x() {
    val r = mm256SetEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, mm256SetrEpi64x(4L, 3L, 2L, 1L))
}

public fun testMm256SetrPd() {
    val r = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, mm256SetrPd(1.0, 2.0, 3.0, 4.0))
}

public fun testMm256SetrPs() {
    val r = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f))
}

public fun testMm256SetrEpi8() {
    val r = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val e = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM256i(r, e)
}

public fun testMm256SetrEpi16() {
    val r = mm256SetrEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val e = mm256SetrEpi16(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM256i(r, e)
}

public fun testMm256SetrEpi32() {
    val r = mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8)
    assertEqM256i(r, mm256SetrEpi32(1, 2, 3, 4, 5, 6, 7, 8))
}

public fun testMm256SetrEpi64x() {
    val r = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    assertEqM256i(r, mm256SetrEpi64x(1L, 2L, 3L, 4L))
}

public fun testMm256Set1Pd() {
    val r = mm256Set1Pd(1.0)
    assertEqM256d(r, mm256Set1Pd(1.0))
}

public fun testMm256Set1Ps() {
    val r = mm256Set1Ps(1.0f)
    assertEqM256(r, mm256Set1Ps(1.0f))
}

public fun testMm256Set1Epi8() {
    val r = mm256Set1Epi8(1)
    assertEqM256i(r, mm256Set1Epi8(1))
}

public fun testMm256Set1Epi16() {
    val r = mm256Set1Epi16(1)
    assertEqM256i(r, mm256Set1Epi16(1))
}

public fun testMm256Set1Epi32() {
    val r = mm256Set1Epi32(1)
    assertEqM256i(r, mm256Set1Epi32(1))
}

public fun testMm256Set1Epi64x() {
    val r = mm256Set1Epi64x(1L)
    assertEqM256i(r, mm256Set1Epi64x(1L))
}

public fun testMm256CastpdPs() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256CastpdPs(a)
    val e = mm256SetrPs(0.0f, 1.875f, 0.0f, 2.0f, 0.0f, 2.125f, 0.0f, 2.25f)
    assertEqM256(r, e)
}

public fun testMm256CastpsPd() {
    val a = mm256SetrPs(0.0f, 1.875f, 0.0f, 2.0f, 0.0f, 2.125f, 0.0f, 2.25f)
    val r = mm256CastpsPd(a)
    val e = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256CastpsSi256() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256CastpsSi256(a)
    val e = mm256SetrEpi8(0, 0, (-128).toByte(), 63, 0, 0, 0, 64, 0, 0, 64, 64, 0, 0, (-128).toByte(), 64, 0, 0, (-96).toByte(), 64, 0, 0, (-64).toByte(), 64, 0, 0, (-32).toByte(), 64, 0, 0, 0, 65)
    assertEqM256i(r, e)
}

public fun testMm256Castsi256Ps() {
    val a = mm256SetrEpi8(0, 0, (-128).toByte(), 63, 0, 0, 0, 64, 0, 0, 64, 64, 0, 0, (-128).toByte(), 64, 0, 0, (-96).toByte(), 64, 0, 0, (-64).toByte(), 64, 0, 0, (-32).toByte(), 64, 0, 0, 0, 65)
    val r = mm256Castsi256Ps(a)
    val e = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256CastpdSi256() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256CastpdSi256(a)
    assertEqM256d(transmute(r), a)
}

public fun testMm256Castsi256Pd() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Castsi256Pd(a)
    assertEqM256d(r, transmute(a))
}

public fun testMm256Castps256Ps128() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256Castps256Ps128(a)
    assertEqM128(r, mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f))
}

public fun testMm256Castpd256Pd128() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val r = mm256Castpd256Pd128(a)
    assertEqM128d(r, mmSetrPd(1.0, 2.0))
}

public fun testMm256Castsi256Si128() {
    val a = mm256SetrEpi64x(1L, 2L, 3L, 4L)
    val r = mm256Castsi256Si128(a)
    assertEqM128i(r, mmSetrEpi64x(1, 2))
}

public fun testMm256Castps128Ps256() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256Castps128Ps256(a)
    assertEqM128(mm256Castps256Ps128(r), a)
}

public fun testMm256Castpd128Pd256() {
    val a = mmSetrPd(1.0, 2.0)
    val r = mm256Castpd128Pd256(a)
    assertEqM128d(mm256Castpd256Pd128(r), a)
}

public fun testMm256Castsi128Si256() {
    val a = mmSetrEpi32(1, 2, 3, 4)
    val r = mm256Castsi128Si256(a)
    assertEqM128i(mm256Castsi256Si128(r), a)
}

public fun testMm256Zextps128Ps256() {
    val a = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256Zextps128Ps256(a)
    val e = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    assertEqM256(r, e)
}

public fun testMm256Zextsi128Si256() {
    val a = mmSetrEpi64x(1, 2)
    val r = mm256Zextsi128Si256(a)
    val e = mm256SetrEpi64x(1L, 2L, 0L, 0L)
    assertEqM256i(r, e)
}

public fun testMm256Zextpd128Pd256() {
    val a = mmSetrPd(1.0, 2.0)
    val r = mm256Zextpd128Pd256(a)
    val e = mm256SetrPd(1.0, 2.0, 0.0, 0.0)
    assertEqM256d(r, e)
}

public fun testMm256SetM128() {
    val hi = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val lo = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val r = mm256SetM128(hi, lo)
    val e = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256SetM128d() {
    val hi = mmSetrPd(3.0, 4.0)
    val lo = mmSetrPd(1.0, 2.0)
    val r = mm256SetM128d(hi, lo)
    val e = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256SetM128i() {
    val hi = mmSetrEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val lo = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm256SetM128i(hi, lo)
    val e = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM256i(r, e)
}

public fun testMm256SetrM128() {
    val lo = mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f)
    val hi = mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256SetrM128(lo, hi)
    val e = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256SetrM128d() {
    val lo = mmSetrPd(1.0, 2.0)
    val hi = mmSetrPd(3.0, 4.0)
    val r = mm256SetrM128d(lo, hi)
    val e = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256SetrM128i() {
    val lo = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val hi = mmSetrEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val r = mm256SetrM128i(lo, hi)
    val e = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM256i(r, e)
}

public fun testMm256Loadu2M128() {
    val hi = listOf(5.0, 6.0, 7.0, 8.0)
    val hiaddr = hi.asPtr()
    val lo = listOf(1.0, 2.0, 3.0, 4.0)
    val loaddr = lo.asPtr()
    val r = mm256Loadu2M128(hiaddr, loaddr)
    val e = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    assertEqM256(r, e)
}

public fun testMm256Loadu2M128d() {
    val hi = listOf(3.0, 4.0)
    val hiaddr = hi.asPtr()
    val lo = listOf(1.0, 2.0)
    val loaddr = lo.asPtr()
    val r = mm256Loadu2M128d(hiaddr, loaddr)
    val e = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    assertEqM256d(r, e)
}

public fun testMm256Loadu2M128i() {
    val hi = mmSetrEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val lo = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    val r = mm256Loadu2M128i(constPtrFromRustCast(addrOf(hi)), constPtrFromRustCast(addrOf(lo)))
    val e = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    assertEqM256i(r, e)
}

public fun testMm256Storeu2M128() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val hi = mmUndefinedPs()
    val lo = mmUndefinedPs()
    run {
        mm256Storeu2M128(mutPtrFromRustCast(addrOfMut(hi)), mutPtrFromRustCast(addrOfMut(lo)), a)
    }
    assertEqM128(hi, mmSetrPs(5.0f, 6.0f, 7.0f, 8.0f))
    assertEqM128(lo, mmSetrPs(1.0f, 2.0f, 3.0f, 4.0f))
}

public fun testMm256Storeu2M128d() {
    val a = mm256SetrPd(1.0, 2.0, 3.0, 4.0)
    val hi = mmUndefinedPd()
    val lo = mmUndefinedPd()
    run {
        mm256Storeu2M128d(mutPtrFromRustCast(addrOfMut(hi)), mutPtrFromRustCast(addrOfMut(lo)), a)
    }
    assertEqM128d(hi, mmSetrPd(3.0, 4.0))
    assertEqM128d(lo, mmSetrPd(1.0, 2.0))
}

public fun testMm256Storeu2M128i() {
    val a = mm256SetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val hi = mmUndefinedSi128()
    val lo = mmUndefinedSi128()
    run {
        mm256Storeu2M128i(addrOfMut(hi), addrOfMut(lo), a)
    }
    val eHi = mmSetrEpi8(17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32)
    val eLo = mmSetrEpi8(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
    assertEqM128i(hi, eHi)
    assertEqM128i(lo, eLo)
}

public fun testMm256CvtssF32() {
    val a = mm256SetrPs(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f)
    val r = mm256CvtssF32(a)
    check(r == 1.0f.toFloat())
}
