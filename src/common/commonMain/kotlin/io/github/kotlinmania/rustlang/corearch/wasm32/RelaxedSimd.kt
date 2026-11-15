// port-lint: source wasm32/relaxed_simd.rs
package io.github.kotlinmania.rustlang.corearch.wasm32

import io.github.kotlinmania.rustlang.wasm32.V128

public expect fun llvmRelaxedSwizzle(a: I8x16, b: I8x16): I8x16

public expect fun llvmRelaxedTruncSigned(a: F32x4): I32x4

public expect fun llvmRelaxedTruncUnsigned(a: F32x4): I32x4

public expect fun llvmRelaxedTruncSignedZero(a: F64x2): I32x4

public expect fun llvmRelaxedTruncUnsignedZero(a: F64x2): I32x4

public expect fun llvmF32x4Fma(a: F32x4, b: F32x4, c: F32x4): F32x4

public expect fun llvmF32x4Fms(a: F32x4, b: F32x4, c: F32x4): F32x4

public expect fun llvmF64x2Fma(a: F64x2, b: F64x2, c: F64x2): F64x2

public expect fun llvmF64x2Fms(a: F64x2, b: F64x2, c: F64x2): F64x2

public expect fun llvmI8x16Laneselect(a: I8x16, b: I8x16, c: I8x16): I8x16

public expect fun llvmI16x8Laneselect(a: I16x8, b: I16x8, c: I16x8): I16x8

public expect fun llvmI32x4Laneselect(a: I32x4, b: I32x4, c: I32x4): I32x4

public expect fun llvmI64x2Laneselect(a: I64x2, b: I64x2, c: I64x2): I64x2

public expect fun llvmF32x4RelaxedMin(a: F32x4, b: F32x4): F32x4

public expect fun llvmF64x2RelaxedMin(a: F64x2, b: F64x2): F64x2

public expect fun llvmF32x4RelaxedMax(a: F32x4, b: F32x4): F32x4

public expect fun llvmF64x2RelaxedMax(a: F64x2, b: F64x2): F64x2

public expect fun llvmRelaxedQ15mulrSigned(a: I16x8, b: I16x8): I16x8

public expect fun llvmI16x8RelaxedDotI8x16I7x16S(a: I8x16, b: I8x16): I16x8

public expect fun llvmI32x4RelaxedDotI8x16I7x16AddS(a: I8x16, b: I8x16, c: I32x4): I32x4

/**
 * A relaxed version of `i8x16Swizzle(a, s)` which selects lanes from `a`
 * using indices in `s`.
 *
 * Indices in the range `[0, 15]` will select the `i`-th element of `a`.
 * If the high bit of any element of `s` is set (meaning 128 or greater) then
 * the corresponding output lane is guaranteed to be zero. Otherwise if the
 * element of `s` is within the range `[16,128)` then the output lane is either
 * 0 or `a[s[i] % 16]` depending on the implementation.
 */
public fun i8x16RelaxedSwizzle(a: V128, s: V128): V128 = llvmRelaxedSwizzle(a.asI8x16(), s.asI8x16()).v128()

/**
 * A relaxed version of `i32x4TruncSatF32x4(a)` converts the `Float` lanes
 * of `a` to signed 32-bit integers.
 *
 * Values which don't fit in 32-bit integers or are NaN may have the same
 * result as `i32x4TruncSatF32x4` or may return `Int.MIN`.
 */
public fun i32x4RelaxedTruncF32x4(a: V128): V128 = llvmRelaxedTruncSigned(a.asF32x4()).v128()

/**
 * A relaxed version of `u32x4TruncSatF32x4(a)` converts the `Float` lanes
 * of `a` to unsigned 32-bit integers.
 *
 * Values which don't fit in 32-bit unsigned integers or are NaN may have the
 * same result as `u32x4TruncSatF32x4` or may return `UInt.MAX`.
 */
public fun u32x4RelaxedTruncF32x4(a: V128): V128 = llvmRelaxedTruncUnsigned(a.asF32x4()).v128()

/**
 * A relaxed version of `i32x4TruncSatF64x2Zero(a)` converts the `Double`
 * lanes of `a` to signed 32-bit integers and the upper two lanes are zero.
 *
 * Values which don't fit in 32-bit integers or are NaN may have the same
 * result as `i32x4TruncSatF32x4` or may return `Int.MIN`.
 */
public fun i32x4RelaxedTruncF64x2Zero(a: V128): V128 = llvmRelaxedTruncSignedZero(a.asF64x2()).v128()

/**
 * A relaxed version of `u32x4TruncSatF64x2Zero(a)` converts the `Double`
 * lanes of `a` to unsigned 32-bit integers and the upper two lanes are zero.
 *
 * Values which don't fit in 32-bit unsigned integers or are NaN may have the
 * same result as `u32x4TruncSatF32x4` or may return `UInt.MAX`.
 */
public fun u32x4RelaxedTruncF64x2Zero(a: V128): V128 = llvmRelaxedTruncUnsignedZero(a.asF64x2()).v128()

/**
 * Computes `a * b + c` with either one rounding or two roundings.
 */
public fun f32x4RelaxedMadd(a: V128, b: V128, c: V128): V128 = llvmF32x4Fma(a.asF32x4(), b.asF32x4(), c.asF32x4()).v128()

/**
 * Computes `- a * b + c` with either one rounding or two roundings.
 */
public fun f32x4RelaxedNmadd(a: V128, b: V128, c: V128): V128 = llvmF32x4Fms(a.asF32x4(), b.asF32x4(), c.asF32x4()).v128()

/**
 * Computes `a * b + c` with either one rounding or two roundings.
 */
public fun f64x2RelaxedMadd(a: V128, b: V128, c: V128): V128 = llvmF64x2Fma(a.asF64x2(), b.asF64x2(), c.asF64x2()).v128()

/**
 * Computes `- a * b + c` with either one rounding or two roundings.
 */
public fun f64x2RelaxedNmadd(a: V128, b: V128, c: V128): V128 = llvmF64x2Fms(a.asF64x2(), b.asF64x2(), c.asF64x2()).v128()

/**
 * A relaxed version of `v128Bitselect` where this either behaves the same as
 * `v128Bitselect` or the high bit of each lane `m` is inspected and the
 * corresponding lane of `a` is chosen if the bit is 1 or the lane of `b` is
 * chosen if it's zero.
 *
 * If the `m` mask's lanes are either all-one or all-zero then this instruction
 * is the same as `v128Bitselect`.
 */
public fun i8x16RelaxedLaneselect(a: V128, b: V128, m: V128): V128 = llvmI8x16Laneselect(a.asI8x16(), b.asI8x16(), m.asI8x16()).v128()

/**
 * A relaxed version of `v128Bitselect` where this either behaves the same as
 * `v128Bitselect` or the high bit of each lane `m` is inspected and the
 * corresponding lane of `a` is chosen if the bit is 1 or the lane of `b` is
 * chosen if it's zero.
 *
 * If the `m` mask's lanes are either all-one or all-zero then this instruction
 * is the same as `v128Bitselect`.
 */
public fun i16x8RelaxedLaneselect(a: V128, b: V128, m: V128): V128 = llvmI16x8Laneselect(a.asI16x8(), b.asI16x8(), m.asI16x8()).v128()

/**
 * A relaxed version of `v128Bitselect` where this either behaves the same as
 * `v128Bitselect` or the high bit of each lane `m` is inspected and the
 * corresponding lane of `a` is chosen if the bit is 1 or the lane of `b` is
 * chosen if it's zero.
 *
 * If the `m` mask's lanes are either all-one or all-zero then this instruction
 * is the same as `v128Bitselect`.
 */
public fun i32x4RelaxedLaneselect(a: V128, b: V128, m: V128): V128 = llvmI32x4Laneselect(a.asI32x4(), b.asI32x4(), m.asI32x4()).v128()

/**
 * A relaxed version of `v128Bitselect` where this either behaves the same as
 * `v128Bitselect` or the high bit of each lane `m` is inspected and the
 * corresponding lane of `a` is chosen if the bit is 1 or the lane of `b` is
 * chosen if it's zero.
 *
 * If the `m` mask's lanes are either all-one or all-zero then this instruction
 * is the same as `v128Bitselect`.
 */
public fun i64x2RelaxedLaneselect(a: V128, b: V128, m: V128): V128 = llvmI64x2Laneselect(a.asI64x2(), b.asI64x2(), m.asI64x2()).v128()

/**
 * A relaxed version of `f32x4Min` which has implementation-specific behavior
 * when its operands are NaN or signed zeroes. For more information, see [the
 * WebAssembly
 * specification](https://webassembly.github.io/spec/core/exec/numerics.html#op-frelaxed-min).
 */
public fun f32x4RelaxedMin(a: V128, b: V128): V128 = llvmF32x4RelaxedMin(a.asF32x4(), b.asF32x4()).v128()

/**
 * A relaxed version of `f32x4Max` which has implementation-specific behavior
 * when its operands are NaN or signed zeroes. For more information, see [the
 * WebAssembly
 * specification](https://webassembly.github.io/spec/core/exec/numerics.html#op-frelaxed-max).
 */
public fun f32x4RelaxedMax(a: V128, b: V128): V128 = llvmF32x4RelaxedMax(a.asF32x4(), b.asF32x4()).v128()

/**
 * A relaxed version of `f64x2Min` which has implementation-specific behavior
 * when its operands are NaN or signed zeroes. For more information, see [the
 * WebAssembly
 * specification](https://webassembly.github.io/spec/core/exec/numerics.html#op-frelaxed-min).
 */
public fun f64x2RelaxedMin(a: V128, b: V128): V128 = llvmF64x2RelaxedMin(a.asF64x2(), b.asF64x2()).v128()

/**
 * A relaxed version of `f64x2Max` which has implementation-specific behavior
 * when its operands are NaN or signed zeroes. For more information, see [the
 * WebAssembly
 * specification](https://webassembly.github.io/spec/core/exec/numerics.html#op-frelaxed-max).
 */
public fun f64x2RelaxedMax(a: V128, b: V128): V128 = llvmF64x2RelaxedMax(a.asF64x2(), b.asF64x2()).v128()

/**
 * A relaxed version of `i16x8RelaxedQ15mulr` where if both lanes are
 * `Short.MIN` then the result is either `Short.MIN` or `Short.MAX`.
 */
public fun i16x8RelaxedQ15mulr(a: V128, b: V128): V128 = llvmRelaxedQ15mulrSigned(a.asI16x8(), b.asI16x8()).v128()

/**
 * A relaxed dot-product instruction.
 *
 * This instruction will perform pairwise products of the 8-bit values in `a`
 * and `b` and then accumulate adjacent pairs into 16-bit results producing a
 * final `i16x8` vector. The bytes of `a` are always interpreted as signed and
 * the bytes in `b` may be interpreted as signed or unsigned. If the top bit in
 * `b` isn't set then the value is the same regardless of whether it's signed
 * or unsigned.
 *
 * The accumulation into 16-bit values may be saturated on some platforms, and
 * on other platforms it may wrap-around on overflow.
 */
public fun i16x8RelaxedDotI8x16I7x16(a: V128, b: V128): V128 = llvmI16x8RelaxedDotI8x16I7x16S(a.asI8x16(), b.asI8x16()).v128()

/**
 * Similar to [i16x8RelaxedDotI8x16I7x16] except that the intermediate
 * `i16x8` result is fed into `i32x4ExtaddPairwiseI16x8` followed by
 * `i32x4Add` to add the value `c` to the result.
 */
public fun i32x4RelaxedDotI8x16I7x16Add(a: V128, b: V128, c: V128): V128 = llvmI32x4RelaxedDotI8x16I7x16AddS(a.asI8x16(), b.asI8x16(), c.asI32x4()).v128()
