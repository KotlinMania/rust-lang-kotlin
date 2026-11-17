// port-lint: tests wasm32/relaxed_simd.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.corearch.simd.F32x4
import io.github.kotlinmania.rustlang.corearch.simd.F64x2
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x16
import io.github.kotlinmania.rustlang.corearch.simd.U32x4
import io.github.kotlinmania.rustlang.corearch.simd.U8x16

public fun compareBytes(a: V128, b: List<V128>) {
    val a1: UByteArray = transmuteWithLayouts<V128, UByteArray>(a, UnresolvedTypeLayout<V128>(), UnresolvedTypeLayout<UByteArray>())
    if (b.any({ b1 ->
        val b2: UByteArray = transmute(b1)
        a1.contentEquals(b2)
    })) {
        return
    }
    println("input vector $a")
    println("did not match any output:")
    for (b1 in b) {
        println("  $b")
    }
}

public class RelaxedSimdInlineTests {
    @kotlin.test.Test
    public fun testRelaxedSwizzle() {
        compareBytes(i8x16RelaxedSwizzle(I8x16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), I8x16(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1)), listOf(I8x16(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1)))
        compareBytes(i8x16RelaxedSwizzle(I8x16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), U8x16(0x80, 0xff, 16, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)), listOf(I8x16(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), I8x16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)))
        compareBytes(u8x16RelaxedSwizzle(U8x16(128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143), U8x16(0x80, 0xff, 16, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)), listOf(U8x16(128, 128, 128, 129, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128), U8x16(0, 0, 0, 0, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128, 128)))
    }

    @kotlin.test.Test
    public fun testRelaxedTrunc() {
        compareBytes(i32x4RelaxedTruncF32x4(F32x4(1.0, 2.0, -1.0, -4.0)), listOf(I32x4(1, 2, -1, -4)))
        compareBytes(i32x4RelaxedTruncF32x4(F32x4(Float.NEGATIVE_INFINITY, Float.NaN, -0.0, Float.POSITIVE_INFINITY)), listOf(I32x4(Int.MIN_VALUE, 0, 0, Int.MAX_VALUE), I32x4(Int.MIN_VALUE, Int.MIN_VALUE, 0, Int.MIN_VALUE)))
        compareBytes(i32x4RelaxedTruncF64x2Zero(F64x2(1.0, -3.0)), listOf(I32x4(1, -3, 0, 0)))
        compareBytes(i32x4RelaxedTruncF64x2Zero(F64x2(Double.POSITIVE_INFINITY, Double.NaN)), listOf(I32x4(Int.MAX_VALUE, 0, 0, 0), I32x4(Int.MIN_VALUE, Int.MIN_VALUE, 0, 0)))
        compareBytes(u32x4RelaxedTruncF32x4(F32x4(1.0, 2.0, 5.0, 100.0)), listOf(I32x4(1, 2, 5, 100)))
        compareBytes(u32x4RelaxedTruncF32x4(F32x4(Float.NEGATIVE_INFINITY, Float.NaN, -0.0, Float.POSITIVE_INFINITY)), listOf(U32x4(UInt.MAX_VALUE, 0, 0, UInt.MAX_VALUE), U32x4(UInt.MAX_VALUE, UInt.MAX_VALUE, 0, UInt.MAX_VALUE)))
        compareBytes(u32x4RelaxedTruncF64x2Zero(F64x2(1.0, 3.0)), listOf(U32x4(1, 3, 0, 0)))
        compareBytes(u32x4RelaxedTruncF64x2Zero(F64x2(Double.POSITIVE_INFINITY, Double.NaN)), listOf(I32x4(Int.MAX_VALUE, 0, 0, 0), I32x4(Int.MIN_VALUE, Int.MIN_VALUE, 0, 0)))
    }

    @kotlin.test.Test
    public fun testMadd() {
        val floats = floatArrayOf(Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, 1.0f, 2.0f, -1.0f, 0.0f, 100.3f, 7.8f, 9.4f)
        for (a in floats) {
            for (b in floats) {
                for (c in floats) {
                    val f1 = a * b + c
                    val f2 = a.mulAdd(b, c)
                    compareBytes(f32x4RelaxedMadd(F32x4(a, a, a, a), F32x4(b, b, b, b), F32x4(c, c, c, c)), listOf(F32x4(f1, f1, f1, f1), F32x4(f2, f2, f2, f2)))
                    val f11 = -a * b + c
                    val f21 = (-a).mulAdd(b, c)
                    compareBytes(f32x4RelaxedNmadd(F32x4(a, a, a, a), F32x4(b, b, b, b), F32x4(c, c, c, c)), listOf(F32x4(f11, f11, f11, f11), F32x4(f21, f21, f21, f21)))
                    val a1 = Double.from(a)
                    val b1 = Double.from(b)
                    val c1 = Double.from(c)
                    val f12 = a1 * b1 + c1
                    val f22 = a1.mulAdd(b1, c1)
                    compareBytes(f64x2RelaxedMadd(F64x2(a1, a1), F64x2(b1, b1), F64x2(c1, c1)), listOf(F64x2(f12, f12), F64x2(f22, f22)))
                    val f13 = -a1 * b1 + c1
                    val f23 = (-a1).mulAdd(b1, c1)
                    compareBytes(f64x2RelaxedNmadd(F64x2(a1, a1), F64x2(b1, b1), F64x2(c1, c1)), listOf(F64x2(f13, f13), F64x2(f23, f23)))
                }
            }
        }
    }
}
