package io.github.kotlinmania.rustlang.compilerbuiltins.int.specializeddivrem

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.num.checkedDiv
import io.github.kotlinmania.rustlang.core.num.checkedRem
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.num.wrappingAdd
import io.github.kotlinmania.rustlang.core.num.wrappingMul
import io.github.kotlinmania.rustlang.core.num.wrappingShr
import io.github.kotlinmania.rustlang.core.num.wrappingSub

/**
 * The behavior of all divisions by zero is controlled by this function. This function should be
 * impossible to reach by Rust users, unless `compiler - builtins` public division functions or
 * `core / std.uncheckedDiv / rem` are directly used without a zero check in front.
 */
public fun zeroDivFn(): Nothing {
    // Calling the intrinsic directly, to avoid the `assertUnsafePrecondition` that cannot be used
    // here because it involves non-`inline` functions
    // (https://github.com/rust-lang/compiler-builtins/issues/491).
    error("Entered Rust intrinsics::unreachable")
}

// Whether `trifecta` or `delegate` is faster for 128 bit division depends on the speed at which a
// microarchitecture can multiply and divide. We decide to be optimistic and assume `trifecta` is
// faster if the target pointer width is at least 64. Note that this
// implementation is additionally included on WebAssembly despite the typical
// pointer width there being 32 because it's typically run on a 64-bit machine
// that has access to faster 64-bit operations.
// If the pointer width less than 64 and this isn't wasm, then the target
// architecture almost certainly does not have the fast 64 to 128 bit widening
// multiplication needed for `trifecta` to be faster.
// use `asymmetric` instead of `trifecta` on x8664
// When not on x86 and the pointer width is not 64, use `delegate` since the division size is larger
// than register size.
// When not on x86 and the pointer width is 64, use `binaryLong`.
// use `asymmetric` instead of `delegate` on x86
// 32 bits is the smallest division used by `compiler - builtins`, so we end with binary long division

/**
 * Finds the shift left that the divisor `div` would need to be normalized for a binary
 * long division step with the dividend `duo`. NOTE: This function assumes that these edge
 * cases have been handled before reaching it:
 * `
 * if div == 0 (
 *     panic("attempt to divide by zero")
 * )
 * if duo < div (
 *     return (0, duo)
 * )
 * `
 *
 * Normalization is defined as (where `shl` is the output of this function):
 * `
 * if duo.leadingZeros() != (div << shl).leadingZeros() (
 *     // If the most significant bits of `duo` and `div<<shl` are not in the same place,
 *     // then `div<<shl` has one more leading zero than `duo`.
 *     assertEq(duo.leadingZeros() + 1, (div << shl).leadingZeros());
 *     // Also, `2 * (div<<shl)` is not more than `duo` (otherwise the first division step
 *     // would not be able to clear the msb of `duo`)
 *     assert(duo < (div << (shl + 1)));
 * )
 * if fullNormalization (
 *     // Some algorithms do not need "full" normalization, which means that `duo` is
 *     // larger than `div<<shl` when the most significant bits are aligned.
 *     assert((div << shl) <= duo);
 * )
 * `
 *
 * Note: If the software bisection algorithm is being used in this function, it happens
 * that full normalization always occurs, so be careful that new algorithms are not
 * invisibly depending on this invariant when `fullNormalization` is set to `false`.
 */
public fun u32NormalizationShift(duo: UInt, div: UInt, fullNormalization: Boolean): Int {
    var shl: Int
    if (USE_LZ) {
        shl = (div.leadingZeros() - duo.leadingZeros()).toInt()
        if (fullNormalization) {
            if (duo < (div shl shl)) {
                shl -= 1
            }
        }
    } else {
        var test = duo
        shl = 0
        var lvl = 32 shr 1
        while (true) {
            val tmp = test shr lvl.toInt()
            if (div <= tmp) {
                test = tmp
                shl += lvl
            }
            lvl = lvl shr 1
            if (lvl == 0) {
                break
            }
        }
    }
    return shl
}

/**
 * Finds the shift left that the divisor `div` would need to be normalized for a binary
 * long division step with the dividend `duo`. NOTE: This function assumes that these edge
 * cases have been handled before reaching it:
 * `
 * if div == 0 (
 *     panic("attempt to divide by zero")
 * )
 * if duo < div (
 *     return (0, duo)
 * )
 * `
 *
 * Normalization is defined as (where `shl` is the output of this function):
 * `
 * if duo.leadingZeros() != (div << shl).leadingZeros() (
 *     // If the most significant bits of `duo` and `div<<shl` are not in the same place,
 *     // then `div<<shl` has one more leading zero than `duo`.
 *     assertEq(duo.leadingZeros() + 1, (div << shl).leadingZeros());
 *     // Also, `2 * (div<<shl)` is not more than `duo` (otherwise the first division step
 *     // would not be able to clear the msb of `duo`)
 *     assert(duo < (div << (shl + 1)));
 * )
 * if fullNormalization (
 *     // Some algorithms do not need "full" normalization, which means that `duo` is
 *     // larger than `div<<shl` when the most significant bits are aligned.
 *     assert((div << shl) <= duo);
 * )
 * `
 *
 * Note: If the software bisection algorithm is being used in this function, it happens
 * that full normalization always occurs, so be careful that new algorithms are not
 * invisibly depending on this invariant when `fullNormalization` is set to `false`.
 */
public fun u64NormalizationShift(duo: ULong, div: ULong, fullNormalization: Boolean): Int {
    var shl: Int
    if (USE_LZ) {
        shl = (div.leadingZeros() - duo.leadingZeros()).toInt()
        if (fullNormalization) {
            if (duo < (div shl shl)) {
                shl -= 1
            }
        }
    } else {
        var test = duo
        shl = 0
        var lvl = 64 shr 1
        while (true) {
            val tmp = test shr lvl.toInt()
            if (div <= tmp) {
                test = tmp
                shl += lvl
            }
            lvl = lvl shr 1
            if (lvl == 0) {
                break
            }
        }
    }
    return shl
}

/**
 * Divides `duo` by `div` and returns a tuple of the quotient and the remainder.
 * `checkedDiv` and `checkedRem` are used to avoid bringing in panic function
 * dependencies.
 */
public fun u64ByU64DivRem(duo: ULong, div: ULong): Pair<ULong, ULong> {
    if (duo.checkedDiv(div) != null && duo.checkedRem(div) != null) {
        return Pair(quo, rem)
    }
    return zeroDivFn()
}

/**
 * Computes the quotient and remainder of `duo` divided by `div` and returns them as a
 * tuple.
 */
public fun u128DivRem(duo: U128, div: U128): Pair<U128, U128> {
    val duo1 = duo
    fun carryingMul(lhs: ULong, rhs: ULong): Pair<ULong, ULong> {
        val tmp = (U128.fromULong(lhs)).wrappingMul(U128.fromULong(rhs))
        return Pair(tmp.toULong(), (tmp shr (32 * 2).toInt()).toULong())
    }
    fun carryingMulAdd(lhs: ULong, mul: ULong, add: ULong): Pair<ULong, ULong> {
        val tmp = (U128.fromULong(lhs)).wrappingMul(U128.fromULong(mul)).wrappingAdd(U128.fromULong(add))
        return Pair(tmp.toULong(), (tmp shr (32 * 2).toInt()).toULong())
    }
    val n = 32 * 2
    if (div == U128(0x0uL, 0x0uL)) {
        zeroDivFn()
    }
    val divLz = div.leadingZeros()
    var duoLz = duo1.leadingZeros()
    if (divLz <= duoLz) {
        if (duo1 >= div) {
            return Pair(1, duo1 - div)
        } else {
            return Pair(0, duo1)
        }
    }
    if (duoLz >= n) {
        val (quo, rem) = u64ByU64DivRem(duo1.toULong(), div.toULong())
        return Pair(U128.fromLong(quo.toLong()), U128.fromLong(rem.toLong()))
    }
    if (divLz >= (n + 32u)) {
        val duoHi = (duo1 shr n.toInt()).toULong()
        val div0 = (div.toUInt()).toULong()
        val (quoHi, rem3) = u64ByU64DivRem(duoHi, div0)
        val duoMid = (((duo1 shr 32).toUInt()).toULong()) or (rem3 shl 32)
        val (quo1, rem2) = u64ByU64DivRem(duoMid, div0)
        val duoLo = ((duo1.toUInt()).toULong()) or (rem2 shl 32)
        val (quo0, rem1) = u64ByU64DivRem(duoLo, div0)
        return Pair((U128.fromLong(quo0.toLong())) or ((U128.fromLong(quo1.toLong())) shl 32) or ((U128.fromLong(quoHi.toLong())) shl n.toInt()), U128.fromLong(rem1.toLong()))
    }
    val lzDiff = divLz - duoLz
    if (lzDiff < 32u) {
        val shift = n - duoLz
        val duoSigN = (duo1 shr shift.toInt()).toULong()
        val divSigN = (div shr shift.toInt()).toULong()
        val quo1 = u64ByU64DivRem(duoSigN, divSigN).first
        val divLo = div.toULong()
        val divHi = (div shr n.toInt()).toULong()
        val (tmpLo, carry) = carryingMul(quo1, divLo)
        val (tmpHi, overflow) = carryingMulAdd(quo1, divHi, carry)
        val tmp = (U128.fromLong(tmpLo.toLong())) or ((U128.fromLong(tmpHi.toLong())) shl n.toInt())
        if ((overflow != 0) || (duo1 < tmp)) {
            return Pair(U128.fromLong((quo1 - 1).toLong()), duo1.wrappingAdd(div).wrappingSub(tmp))
        } else {
            return Pair(U128.fromLong(quo1.toLong()), duo1 - tmp)
        }
    }
    var duo2 = duo1
    var quo2: U128 = U128(0x0uL, 0x0uL)
    val divExtra = (n + 32u) - divLz
    val divSigNH = (div shr divExtra.toInt()).toUInt()
    val divSigNHAdd1 = (divSigNH.toULong()) + 1uL
    while (true) {
        val duoExtra = n - duoLz
        val duoSigN1 = (duo2 shr duoExtra.toInt()).toULong()
        if (divExtra <= duoExtra) {
            val quoPart = U128.fromLong((u64ByU64DivRem(duoSigN1, divSigNHAdd1).first).toLong())
            val extraShl = duoExtra - divExtra
            quo2 += (quoPart shl extraShl.toInt())
            duo2 -= (div.wrappingMul(quoPart) shl extraShl.toInt())
        } else {
            val shift1 = n - duoLz
            val duoSigN2 = (duo2 shr shift1.toInt()).toULong()
            val divSigN1 = (div shr shift1.toInt()).toULong()
            val quoPart1 = u64ByU64DivRem(duoSigN2, divSigN1).first
            val divLo1 = div.toULong()
            val divHi1 = (div shr n.toInt()).toULong()
            val (tmpLo1, carry1) = carryingMul(quoPart1, divLo1)
            val (tmpHi1, _) = carryingMulAdd(quoPart1, divHi1, carry1)
            val tmp1 = (U128.fromLong(tmpLo1.toLong())) or ((U128.fromLong(tmpHi1.toLong())) shl n.toInt())
            if (duo2 < tmp1) {
                return Pair(quo2 + (U128.fromLong((quoPart1 - 1).toLong())), duo2.wrappingAdd(div).wrappingSub(tmp1))
            } else {
                return Pair(quo2 + (U128.fromLong(quoPart1.toLong())), duo2 - tmp1)
            }
        }
        duoLz = duo2.leadingZeros()
        if (divLz <= duoLz) {
            if (div <= duo2) {
                return Pair(quo2 + U128(0x0uL, 0x1uL), duo2 - div)
            } else {
                return Pair(quo2, duo2)
            }
        }
        if (n <= duoLz) {
            val tmp2 = u64ByU64DivRem(duo2.toULong(), div.toULong())
            return Pair(quo2 + (U128.fromLong(tmp2.first.toLong())), U128.fromLong(tmp2.second.toLong()))
        }
    }
}

/**
 * Divides `duo` by `div` and returns a tuple of the quotient and the remainder.
 *
 * # Safety
 *
 * If the quotient does not fit in a `ULong`, a floating point exception occurs.
 * If `div == 0`, then a division by zero exception occurs.
 */
public fun u128ByU64DivRem(duo: U128, div: ULong): Pair<ULong, ULong> {
    val quo: ULong
    val rem: ULong
    run {
        // divides the combined registers rdx:rax (`duo` is split into two 64 bit parts to do this)
        // by `div`. The quotient is stored in rax and the remainder in rdx.
        Unit
    }
    return Pair(quo, rem)
}

/**
 * Divides `duo` by `div` and returns a tuple of the quotient and the remainder.
 * `checkedDiv` and `checkedRem` are used to avoid bringing in panic function
 * dependencies.
 */
public fun u32ByU32DivRem(duo: UInt, div: UInt): Pair<UInt, UInt> {
    if (duo.checkedDiv(div) != null && duo.checkedRem(div) != null) {
        return Pair(quo, rem)
    }
    return zeroDivFn()
}

/**
 * Computes the quotient and remainder of `duo` divided by `div` and returns them as a
 * tuple.
 */
public fun u64DivRem(duo: ULong, div: ULong): Pair<ULong, ULong> {
    val duo1 = duo
    val div1 = div
    var duo2 = duo1
    if (div1 == 0uL) {
        zeroDivFn()
    }
    if (duo2 < div1) {
        return Pair(0, duo2)
    }
    val divOriginal = div1
    var shl = u64NormalizationShift(duo2, div1, true)
    var div2: ULong = (div1 shl shl)
    duo2 = duo2.wrappingSub(div2)
    var quo: ULong = 1uL shl shl
    if (duo2 < divOriginal) {
        return Pair(quo, duo2)
    }
    val mask: ULong
    if ((div2.toLong()) < 0L) {
        div2 = div2 shr 1
        shl -= 1
        val tmp = 1 shl shl
        mask = tmp - 1uL
        val sub = duo2.wrappingSub(div2)
        if ((sub.toLong()) >= 0L) {
            duo2 = sub
            quo = quo or tmp
        }
        if (duo2 < divOriginal) {
            return Pair(quo, duo2)
        }
    } else {
        mask = quo - 1uL
    }
    div2 = (div2 - 1uL)
    var i = shl
    while (true) {
        if (i == 0) {
            break
        }
        i -= 1
        duo2 = duo2.wrappingShl(1).wrappingSub(div2)
        val mask1 = (duo2.toLong()).wrappingShr(64u - 1u).toULong()
        duo2 = duo2.wrappingAdd(div2 and mask1)
    }
    return Pair((duo2 and mask) or quo, duo2 shr shl)
}

/**
 * Computes the quotient and remainder of `duo` divided by `div` and returns them as a
 * tuple.
 */
public fun u32DivRem(duo: UInt, div: UInt): Pair<UInt, UInt> {
    val duo1 = duo
    val div1 = div
    var duo2 = duo1
    if (div1 == 0u) {
        zeroDivFn()
    }
    if (duo2 < div1) {
        return Pair(0, duo2)
    }
    val divOriginal = div1
    var shl = u32NormalizationShift(duo2, div1, true)
    var div2: UInt = (div1 shl shl)
    duo2 = duo2.wrappingSub(div2)
    var quo: UInt = 1u shl shl
    if (duo2 < divOriginal) {
        return Pair(quo, duo2)
    }
    val mask: UInt
    if ((div2.toInt()) < 0) {
        div2 = div2 shr 1
        shl -= 1
        val tmp = 1 shl shl
        mask = tmp - 1u
        val sub = duo2.wrappingSub(div2)
        if ((sub.toInt()) >= 0) {
            duo2 = sub
            quo = quo or tmp
        }
        if (duo2 < divOriginal) {
            return Pair(quo, duo2)
        }
    } else {
        mask = quo - 1u
    }
    div2 = (div2 - 1u)
    var i = shl
    while (true) {
        if (i == 0) {
            break
        }
        i -= 1
        duo2 = duo2.wrappingShl(1).wrappingSub(div2)
        val mask1 = (duo2.toInt()).wrappingShr(32u - 1u).toUInt()
        duo2 = duo2.wrappingAdd(div2 and mask1)
    }
    return Pair((duo2 and mask) or quo, duo2 shr shl)
}
