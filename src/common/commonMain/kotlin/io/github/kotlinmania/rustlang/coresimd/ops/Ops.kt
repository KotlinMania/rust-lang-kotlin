// port-lint: source ops.rs
package io.github.kotlinmania.rustlang.coresimd.ops

import io.github.kotlinmania.rustlang.coresimd.Simd

// SAFETY: This macro should not be used for anything except Shl or Shr, and passed the appropriate shift intrinsic.
// It handles performing a bitand in addition to calling the shift operator, so that the result
// is well-defined: LLVM can return a poison value if you shl, lshr, or ashr if `rhs >= <Int>.BITS`
// At worst, this will maybe add another instruction and cycle,
// at best, it may open up more optimization opportunities,
// or simply be elided entirely, especially for SIMD ISAs which default to this.
//
// SAFETY: This macro must only be used to impl Div or Rem and given the matching intrinsic.
// It guards against LLVM's UB conditions for integer div or rem using masks and selects,
// thus guaranteeing a Rust value returns instead.
//
// |                  | LLVM | Rust
// | :--------------: | :--- | :----------
// | N (/,%) 0        | UB   | panic()
// | <$int>::MIN / -1 | UB   | <$int>::MIN
// | <$int>::MIN % -1 | UB   | 0

public fun <Mask, Output, I, T> Simd<T>.index(index: I, n: Int): Output where I : SliceIndex<List<T>, Output> = this.asArray()[index]

public fun <Mask, Output, I, T> Simd<T>.indexMut(index: I, n: Int): Output where I : SliceIndex<List<T>, Output> = this.asMutArray()[index]

// Integers can always accept add, mul, sub, bitand, bitor, and bitxor.
// For all of these operations, simd_* intrinsics apply wrapping logic.
// We don't need any special precautions here:
// Floats always accept arithmetic ops, but may become NaN.
public fun <Mask> Simd<Byte>.add(rhs: Simd<Byte>, n: Int): Simd<Byte> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Short>.addSelf(rhs: Simd<Short>, n: Int): Simd<Short> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Byte>.mul(rhs: Simd<Byte>, n: Int): Simd<Byte> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Short>.mulSelf(rhs: Simd<Short>, n: Int): Simd<Short> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Byte>.sub(rhs: Simd<Byte>, n: Int): Simd<Byte> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Short>.subSelf(rhs: Simd<Short>, n: Int): Simd<Short> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Byte>.bitand(rhs: Simd<Byte>, n: Int): Simd<Byte> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Short>.bitandSelf(rhs: Simd<Short>, n: Int): Simd<Short> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Byte>.bitor(rhs: Simd<Byte>, n: Int): Simd<Byte> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Short>.bitorSelf(rhs: Simd<Short>, n: Int): Simd<Short> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Byte>.bitxor(rhs: Simd<Byte>, n: Int): Simd<Byte> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Short>.bitxorSelf(rhs: Simd<Short>, n: Int): Simd<Short> = run { val _simdArg0 = this; val _simdArg1 = rhs; _simdArg0 }

public fun <Mask> Simd<Byte>.div(rhs: Simd<Byte>, n: Int): Simd<Byte> =
    if (rhs.simdEq(Simd.splat(0, n)).any()) {
        error("attempt to divide by zero")
    } else {
        val rhs1 =
            if (Byte.MIN_VALUE != 0.toByte()) {
                (this.simdEq(Simd.splat(Byte.MIN_VALUE, n)) and rhs.simdEq(Simd.splat(-1L, n))).select(Simd.splat(1, n), rhs)
            } else {
                rhs
            }
        run {
            val out = Simd.splat(0, n)
            for (i in 0 until Simd.LEN) {
                out[i] = this[i] / rhs1[i]
            }
            out
        }
    }

public fun <Mask> Simd<Short>.divSelf(rhs: Simd<Short>, n: Int): Simd<Short> =
    if (rhs.simdEq(Simd.splat(0, n)).any()) {
        error("attempt to divide by zero")
    } else {
        val rhs1 =
            if (Short.MIN_VALUE != 0.toShort()) {
                (this.simdEq(Simd.splat(Short.MIN_VALUE, n)) and rhs.simdEq(Simd.splat(-1L, n))).select(Simd.splat(1, n), rhs)
            } else {
                rhs
            }
        run {
            val out = Simd.splat(0, n)
            for (i in 0 until Simd.LEN) {
                out[i] = this[i] / rhs1[i]
            }
            out
        }
    }

public fun <Mask> Simd<Byte>.rem(rhs: Simd<Byte>, n: Int): Simd<Byte> =
    if (rhs.simdEq(Simd.splat(0, n)).any()) {
        error("attempt to calculate the remainder with a divisor of zero")
    } else {
        val rhs1 =
            if (Byte.MIN_VALUE != 0.toByte()) {
                (this.simdEq(Simd.splat(Byte.MIN_VALUE, n)) and rhs.simdEq(Simd.splat(-1L, n))).select(Simd.splat(1, n), rhs)
            } else {
                rhs
            }
        run {
            val out = Simd.splat(0, n)
            for (i in 0 until Simd.LEN) {
                out[i] = this[i] % rhs1[i]
            }
            out
        }
    }

public fun <Mask> Simd<Short>.remSelf(rhs: Simd<Short>, n: Int): Simd<Short> =
    if (rhs.simdEq(Simd.splat(0, n)).any()) {
        error("attempt to calculate the remainder with a divisor of zero")
    } else {
        val rhs1 =
            if (Short.MIN_VALUE != 0.toShort()) {
                (this.simdEq(Simd.splat(Short.MIN_VALUE, n)) and rhs.simdEq(Simd.splat(-1L, n))).select(Simd.splat(1, n), rhs)
            } else {
                rhs
            }
        run {
            val out = Simd.splat(0, n)
            for (i in 0 until Simd.LEN) {
                out[i] = this[i] % rhs1[i]
            }
            out
        }
    }

public fun <Mask> Simd<Byte>.shl(rhs: Simd<Byte>, n: Int): Simd<Byte> = run { val _simdArg0 = this; val _simdArg1 = rhs.bitand(Simd.splat(8u.toByte() - 1, n), n); _simdArg0 }

public fun <Mask> Simd<Short>.shlSelf(rhs: Simd<Short>, n: Int): Simd<Short> = run { val _simdArg0 = this; val _simdArg1 = rhs.bitand(Simd.splat(16u.toShort() - 1, n), n); _simdArg0 }

public fun <Mask> Simd<Byte>.shr(rhs: Simd<Byte>, n: Int): Simd<Byte> = run { val _simdArg0 = this; val _simdArg1 = rhs.bitand(Simd.splat(8u.toByte() - 1, n), n); _simdArg0 }

public fun <Mask> Simd<Short>.shrSelf(rhs: Simd<Short>, n: Int): Simd<Short> = run { val _simdArg0 = this; val _simdArg1 = rhs.bitand(Simd.splat(16u.toShort() - 1, n), n); _simdArg0 }
