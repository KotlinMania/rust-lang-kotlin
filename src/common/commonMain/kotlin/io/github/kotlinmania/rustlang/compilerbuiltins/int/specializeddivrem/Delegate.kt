// port-lint: source int/specialized_div_rem/delegate.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.int.specializeddivrem

import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.num.rustWrappingSub

// Creates an unsigned division function that uses a combination of hardware division and
// binary long division to divide integers larger than what hardware division by itself can do. This
// function is intended for microarchitectures that have division hardware, but not fast enough
// multiplication hardware for `implTrifecta` to be faster.

// used on SPARC

/**
 * Returns `n / d` and sets `* rem = n % d`.
 *
 * This specialization exists because:
 *  - The LLVM backend for 32-bit SPARC cannot compile functions that return `(ULong, ULong)`,
 *    so we have to use an old fashioned `ULong` argument to return the remainder.
 *  - 64-bit SPARC does not have u64 * u64 => u128 widening multiplication, which makes the
 *    delegate algorithm strategy the only reasonably fast way to perform `ULong` division.
 */
public fun u128DivideSparc(duo: U128, div: U128, rem: RustMutableReference<U128>): U128 {
    val duo1 = duo
    val div1 = div
    val rem1 = rem
    val duoLo = duo1.toULong()
    val duoHi = (duo1 shr 64).toULong()
    val divLo = div1.toULong()
    val divHi = (div1 shr 64).toULong()
    return run {
        val matchVal = Triple(divLo == 0uL, divHi == 0uL, duoHi == 0uL)
        when {
            matchVal.first == true && matchVal.second == true -> zeroDivFn()
            matchVal.second == false && matchVal.third == true -> {
                (rem1).write(duo1)
                return U128(0x0uL, 0x0uL)
            }
            matchVal.first == false && matchVal.second == true && matchVal.third == true -> {
                val tmp = u64ByU64DivRem(duoLo, divLo)
                (rem1).write(U128.fromLong(tmp.second.toLong()))
                return U128.fromLong(tmp.first.toLong())
            }
            matchVal.first == false && matchVal.second == true && matchVal.third == false -> {
                if (duoHi < divLo) {
                    val normShift = u64NormalizationShift(divLo, duoHi, false)
                    val shl =
                        if (normShift == 0) {
                            64 - 1
                        } else {
                            64 - normShift
                        }
                    var div2: U128 = div1 shl shl.toInt()
                    var powLo: ULong = 1uL shl shl.toInt()
                    var quoLo: ULong = 0uL
                    var duo2 = duo1
                    while (true) {
                        val sub = duo2.wrappingSub(div2)
                        if (I128(0x0L, 0x0uL) <= (I128.fromLong(sub.toLong()))) {
                            duo2 = sub
                            quoLo = quoLo or powLo
                            val duoHi1 = (duo2 shr 64).toULong()
                            if (duoHi1 == 0uL) {
                                val tmp1 = u64ByU64DivRem(duo2.toULong(), divLo)
                                (rem1).write(U128.fromLong(tmp1.second.toLong()))
                                return U128.fromULong((quoLo or tmp1.first))
                            }
                        }
                        div2 = div2 shr 1
                        powLo = powLo shr 1
                    }
                } else if (duoHi == divLo) {
                    val tmp2 = u64ByU64DivRem(duo1.toULong(), div1.toULong())
                    (rem1).write(U128.fromLong(tmp2.second.toLong()))
                    return (U128(0x0uL, 0x1uL) shl 64) or (U128.fromLong(tmp2.first.toLong()))
                } else {
                    if ((divLo shr 32) == 0uL) {
                        val div0 = (divLo.toUInt()).toULong()
                        val (quoHi, rem3) = u64ByU64DivRem(duoHi, div0)
                        val duoMid = (((duo1 shr 32).toUInt()).toULong()) or (rem3 shl 32)
                        val (quo1, rem2) = u64ByU64DivRem(duoMid, div0)
                        val duoLo1 = ((duo1.toUInt()).toULong()) or (rem2 shl 32)
                        val (quo0, rem1) = u64ByU64DivRem(duoLo1, div0)
                        (rem1).write(U128.fromLong(rem1.pointer.rawAddress))
                        return (U128.fromLong(quo0.toLong())) or ((U128.fromLong(quo1.toLong())) shl 32) or ((U128.fromLong(quoHi.toLong())) shl 64)
                    }
                    val duoLo2 = duo1.toULong()
                    val tmp3 = u64ByU64DivRem(duoHi, divLo)
                    val quoHi1 = tmp3.first
                    var duo3 = (U128.fromULong(duoLo2)) or ((U128.fromLong(tmp3.second.toLong())) shl 64)
                    if (duo3 < div1) {
                        (rem1).write(duo3)
                        return (U128.fromLong(quoHi1.toLong())) shl 64
                    }
                    var div3: U128 = div1 shl (64 - 1).toInt()
                    var powLo1: ULong = 1uL shl (64 - 1).toInt()
                    var quoLo1: ULong = 0uL
                    while (true) {
                        val sub1 = rustWrappingSub(duo3, div3)
                        if (I128(0x0L, 0x0uL) <= (sub1.toI128())) {
                            duo3 = sub1
                            quoLo1 = quoLo1 or powLo1
                            val duoHi2 = (duo3 shr 64).toULong()
                            if (duoHi2 == 0uL) {
                                val tmp4 = u64ByU64DivRem(duo3.toULong(), divLo)
                                (rem1).write(U128.fromLong(tmp4.second.toLong()))
                                return U128.fromLong((tmp4.first).toLong()) or (U128.fromULong(quoLo1)) or ((U128.fromLong(quoHi1.toLong())) shl 64)
                            }
                        }
                        div3 = div3 shr 1
                        powLo1 = powLo1 shr 1
                    }
                }
            }
            else -> {
                if (duo1 < div1) {
                    (rem1).write(duo1)
                    return U128(0x0uL, 0x0uL)
                }
                val divOriginal = div1
                val shl1 = u64NormalizationShift(duoHi, divHi, false)
                var duo4 = duo1
                var div4: U128 = div1 shl shl1.toInt()
                var powLo2: ULong = 1uL shl shl1.toInt()
                var quoLo2: ULong = 0uL
                while (true) {
                    val sub2 = duo4.wrappingSub(div4)
                    if (I128(0x0L, 0x0uL) <= (I128.fromLong(sub2.toLong()))) {
                        duo4 = sub2
                        quoLo2 = quoLo2 or powLo2
                        if (duo4 < divOriginal) {
                            (rem1).write(duo4)
                            return U128.fromULong(quoLo2)
                        }
                    }
                    div4 = div4 shr 1
                    powLo2 = powLo2 shr 1
                }
            }
        }
    }
}
