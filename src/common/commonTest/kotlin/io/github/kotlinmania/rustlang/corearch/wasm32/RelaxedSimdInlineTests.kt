// port-lint: tests wasm32/relaxed_simd.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.core.mem.transmute
import io.github.kotlinmania.rustlang.wasm32.V128
import io.github.kotlinmania.rustlang.wasm32.f32x4
import io.github.kotlinmania.rustlang.wasm32.f64x2
import io.github.kotlinmania.rustlang.wasm32.i32x4
import io.github.kotlinmania.rustlang.wasm32.i8x16
import io.github.kotlinmania.rustlang.wasm32.u32x4
import io.github.kotlinmania.rustlang.wasm32.u8x16

public fun compareBytes(a: V128, b: List<V128>) {
    val a1: UByteArray = transmute(a)
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
        compareBytes(i8x16RelaxedSwizzle(i8x16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), i8x16(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1)), listOf(i8x16(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1)))
        compareBytes(i8x16RelaxedSwizzle(i8x16(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), u8x16(0x80u, 0xffu, 16u, 17u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)), listOf(i8x16(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), i8x16(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)))
        compareBytes(u8x16RelaxedSwizzle(u8x16(128u, 129u, 130u, 131u, 132u, 133u, 134u, 135u, 136u, 137u, 138u, 139u, 140u, 141u, 142u, 143u), u8x16(0x80u, 0xffu, 16u, 17u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u, 0u)), listOf(u8x16(128u, 128u, 128u, 129u, 128u, 128u, 128u, 128u, 128u, 128u, 128u, 128u, 128u, 128u, 128u, 128u), u8x16(0u, 0u, 0u, 0u, 128u, 128u, 128u, 128u, 128u, 128u, 128u, 128u, 128u, 128u, 128u, 128u)))
    }

    @kotlin.test.Test
    public fun testRelaxedTrunc() {
        compareBytes(i32x4RelaxedTruncF32x4(f32x4(1.0f, 2.0f, -1.0f, -4.0f)), listOf(i32x4(1, 2, -1, -4)))
        compareBytes(i32x4RelaxedTruncF32x4(f32x4(Float.NEGATIVE_INFINITY, Float.NaN, -0.0f, Float.POSITIVE_INFINITY)), listOf(i32x4(Int.MIN_VALUE, 0, 0, Int.MAX_VALUE), i32x4(Int.MIN_VALUE, Int.MIN_VALUE, 0, Int.MIN_VALUE)))
        compareBytes(i32x4RelaxedTruncF64x2Zero(f64x2(1.0, -3.0)), listOf(i32x4(1, -3, 0, 0)))
        compareBytes(i32x4RelaxedTruncF64x2Zero(f64x2(Double.POSITIVE_INFINITY, Double.NaN)), listOf(i32x4(Int.MAX_VALUE, 0, 0, 0), i32x4(Int.MIN_VALUE, Int.MIN_VALUE, 0, 0)))
        compareBytes(u32x4RelaxedTruncF32x4(f32x4(1.0f, 2.0f, 5.0f, 100.0f)), listOf(i32x4(1, 2, 5, 100)))
        compareBytes(u32x4RelaxedTruncF32x4(f32x4(Float.NEGATIVE_INFINITY, Float.NaN, -0.0f, Float.POSITIVE_INFINITY)), listOf(u32x4(UInt.MAX_VALUE, 0u, 0u, UInt.MAX_VALUE), u32x4(UInt.MAX_VALUE, UInt.MAX_VALUE, 0u, UInt.MAX_VALUE)))
        compareBytes(u32x4RelaxedTruncF64x2Zero(f64x2(1.0, 3.0)), listOf(u32x4(1u, 3u, 0u, 0u)))
        compareBytes(u32x4RelaxedTruncF64x2Zero(f64x2(Double.POSITIVE_INFINITY, Double.NaN)), listOf(i32x4(Int.MAX_VALUE, 0, 0, 0), i32x4(Int.MIN_VALUE, Int.MIN_VALUE, 0, 0)))
    }

    @kotlin.test.Test
    public fun testMadd() {
        val floats = floatArrayOf(Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, 1.0f, 2.0f, -1.0f, 0.0f, 100.3f, 7.8f, 9.4f)
        for (a in floats) {
            for (b in floats) {
                for (c in floats) {
                    val f1 = a * b + c
                    val f2 = a.mulAdd(b, c)
                    compareBytes(f32x4RelaxedMadd(f32x4(a, a, a, a), f32x4(b, b, b, b), f32x4(c, c, c, c)), listOf(f32x4(f1, f1, f1, f1), f32x4(f2, f2, f2, f2)))
                    val f11 = -a * b + c
                    val f21 = (-a).mulAdd(b, c)
                    compareBytes(f32x4RelaxedNmadd(f32x4(a, a, a, a), f32x4(b, b, b, b), f32x4(c, c, c, c)), listOf(f32x4(f11, f11, f11, f11), f32x4(f21, f21, f21, f21)))
                    val a1 = Double.from(a)
                    val b1 = Double.from(b)
                    val c1 = Double.from(c)
                    val f12 = a1 * b1 + c1
                    val f22 = a1.mulAdd(b1, c1)
                    compareBytes(f64x2RelaxedMadd(f64x2(a1, a1), f64x2(b1, b1), f64x2(c1, c1)), listOf(f64x2(f12, f12), f64x2(f22, f22)))
                    val f13 = -a1 * b1 + c1
                    val f23 = (-a1).mulAdd(b1, c1)
                    compareBytes(f64x2RelaxedNmadd(f64x2(a1, a1), f64x2(b1, b1), f64x2(c1, c1)), listOf(f64x2(f13, f13), f64x2(f23, f23)))
                }
            }
        }
    }
}
