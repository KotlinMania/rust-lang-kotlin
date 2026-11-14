// port-lint: source ops.rs
package io.github.kotlinmania.rustlang.coresimd.ops

import io.github.kotlinmania.rustlang.core.slice.iter.rem
import io.github.kotlinmania.rustlang.coresimd.Simd
import io.github.kotlinmania.rustlang.coresimd.simd.SimdElement

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
// | N {/,%} 0        | UB   | panic()
// | <$int>::MIN / -1 | UB   | <$int>::MIN
// | <$int>::MIN % -1 | UB   | 0

public fun <Mask, I, T> Simd<T, N>.index(index: I, n: Int): Output where T : SimdElement<Any?>, I : SliceIndex<List<T>> = this.asArray()[index]

public fun <Mask, I, T> Simd<T, N>.indexMut(index: I, n: Int): Output where T : SimdElement<Any?>, I : SliceIndex<List<T>> = this.asMutArray()[index]

// Integers can always accept add, mul, sub, bitand, bitor, and bitxor.
// For all of these operations, simd_* intrinsics apply wrapping logic.
// We don't need any special precautions here:
// Floats always accept arithmetic ops, but may become NaN.
public fun <Mask> Simd<Byte, N>.add(rhs: Simd<Byte, N>, n: Int): Simd<Byte, N> = simd.simdAdd(this, rhs)

public fun <Mask> Simd<Short, N>.addSelf(rhs: Simd<Short, N>, n: Int): Simd<Short, N> = simd.simdAdd(this, rhs)

public fun <Mask> Simd<Byte, N>.mul(rhs: Simd<Byte, N>, n: Int): Simd<Byte, N> = simd.simdMul(this, rhs)

public fun <Mask> Simd<Short, N>.mulSelf(rhs: Simd<Short, N>, n: Int): Simd<Short, N> = simd.simdMul(this, rhs)

public fun <Mask> Simd<Byte, N>.sub(rhs: Simd<Byte, N>, n: Int): Simd<Byte, N> = simd.simdSub(this, rhs)

public fun <Mask> Simd<Short, N>.subSelf(rhs: Simd<Short, N>, n: Int): Simd<Short, N> = simd.simdSub(this, rhs)

public fun <Mask> Simd<Byte, N>.bitand(rhs: Simd<Byte, N>, n: Int): Simd<Byte, N> = simd.simdAnd(this, rhs)

public fun <Mask> Simd<Short, N>.bitandSelf(rhs: Simd<Short, N>, n: Int): Simd<Short, N> = simd.simdAnd(this, rhs)

public fun <Mask> Simd<Byte, N>.bitor(rhs: Simd<Byte, N>, n: Int): Simd<Byte, N> = simd.simdOr(this, rhs)

public fun <Mask> Simd<Short, N>.bitorSelf(rhs: Simd<Short, N>, n: Int): Simd<Short, N> = simd.simdOr(this, rhs)

public fun <Mask> Simd<Byte, N>.bitxor(rhs: Simd<Byte, N>, n: Int): Simd<Byte, N> = simd.simdXor(this, rhs)

public fun <Mask> Simd<Short, N>.bitxorSelf(rhs: Simd<Short, N>, n: Int): Simd<Short, N> = simd.simdXor(this, rhs)

public fun <Mask> Simd<Byte, N>.div(rhs: Simd<Byte, N>, n: Int): Simd<Byte, N> =
    if (rhs.simdEq(Simd.splat(((0 as Any?) as Simd<Byte, N>))).any()) {
        error("attempt to divide by zero")
    } else {
        val rhs1 =
            if (Byte.MIN_VALUE != 0.toByte()) {
                (this.simdEq(Simd.splat(Byte.MIN_VALUE)) and rhs.simdEq(Simd.splat(((-1L as Any?) as Simd<Byte, N>)))).select(Simd.splat(((1 as Any?) as Simd<Byte, N>)), rhs)
            } else {
                rhs
            }
        run {
            val out = Simd.splat(((0 as Any?) as Simd<Byte, N>))
            for (i in 0 until Simd.LEN) {
                out[i] = this[i] / rhs1[i]
            }
            out
        }
    }

public fun <Mask> Simd<Short, N>.divSelf(rhs: Simd<Short, N>, n: Int): Simd<Short, N> =
    if (rhs.simdEq(Simd.splat(((0 as Any?) as Simd<Short, N>))).any()) {
        error("attempt to divide by zero")
    } else {
        val rhs1 =
            if (Short.MIN_VALUE != 0.toShort()) {
                (this.simdEq(Simd.splat(Short.MIN_VALUE)) and rhs.simdEq(Simd.splat(((-1L as Any?) as Simd<Short, N>)))).select(Simd.splat(((1 as Any?) as Simd<Short, N>)), rhs)
            } else {
                rhs
            }
        run {
            val out = Simd.splat(((0 as Any?) as Simd<Short, N>))
            for (i in 0 until Simd.LEN) {
                out[i] = this[i] / rhs1[i]
            }
            out
        }
    }

public fun <Mask> Simd<Byte, N>.rem(rhs: Simd<Byte, N>, n: Int): Simd<Byte, N> =
    if (rhs.simdEq(Simd.splat(((0 as Any?) as Simd<Byte, N>))).any()) {
        error("attempt to calculate the remainder with a divisor of zero")
    } else {
        val rhs1 =
            if (Byte.MIN_VALUE != 0.toByte()) {
                (this.simdEq(Simd.splat(Byte.MIN_VALUE)) and rhs.simdEq(Simd.splat(((-1L as Any?) as Simd<Byte, N>)))).select(Simd.splat(((1 as Any?) as Simd<Byte, N>)), rhs)
            } else {
                rhs
            }
        run {
            val out = Simd.splat(((0 as Any?) as Simd<Byte, N>))
            for (i in 0 until Simd.LEN) {
                out[i] = this[i] % rhs1[i]
            }
            out
        }
    }

public fun <Mask> Simd<Short, N>.remSelf(rhs: Simd<Short, N>, n: Int): Simd<Short, N> =
    if (rhs.simdEq(Simd.splat(((0 as Any?) as Simd<Short, N>))).any()) {
        error("attempt to calculate the remainder with a divisor of zero")
    } else {
        val rhs1 =
            if (Short.MIN_VALUE != 0.toShort()) {
                (this.simdEq(Simd.splat(Short.MIN_VALUE)) and rhs.simdEq(Simd.splat(((-1L as Any?) as Simd<Short, N>)))).select(Simd.splat(((1 as Any?) as Simd<Short, N>)), rhs)
            } else {
                rhs
            }
        run {
            val out = Simd.splat(((0 as Any?) as Simd<Short, N>))
            for (i in 0 until Simd.LEN) {
                out[i] = this[i] % rhs1[i]
            }
            out
        }
    }

public fun <Mask> Simd<Byte, N>.shl(rhs: Simd<Byte, N>, n: Int): Simd<Byte, N> = simd.simdShl(this, rhs.bitand(Simd.splat(8u.toByte() - 1)))

public fun <Mask> Simd<Short, N>.shlSelf(rhs: Simd<Short, N>, n: Int): Simd<Short, N> = simd.simdShl(this, rhs.bitand(Simd.splat(16u.toShort() - 1)))

public fun <Mask> Simd<Byte, N>.shr(rhs: Simd<Byte, N>, n: Int): Simd<Byte, N> = simd.simdShr(this, rhs.bitand(Simd.splat(8u.toByte() - 1)))

public fun <Mask> Simd<Short, N>.shrSelf(rhs: Simd<Short, N>, n: Int): Simd<Short, N> = simd.simdShr(this, rhs.bitand(Simd.splat(16u.toShort() - 1)))
