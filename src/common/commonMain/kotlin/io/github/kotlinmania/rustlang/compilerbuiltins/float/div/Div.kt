// port-lint: source float/div.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.div

import io.github.kotlinmania.rustlang.compilerbuiltins.support.CastInto
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Float
import io.github.kotlinmania.rustlang.compilerbuiltins.support.HInt
import io.github.kotlinmania.rustlang.compilerbuiltins.support.HalfRep
import io.github.kotlinmania.rustlang.compilerbuiltins.support.Int
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.rustWrappingNeg
import io.github.kotlinmania.rustlang.core.num.rustWrappingShl
import io.github.kotlinmania.rustlang.core.num.rustWrappingShr
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

// Floating point division routines.
//
// This module documentation gives an overview of the method used. More documentation is inline.
//
// # Relevant notation
//
// - `mA`: the mantissa of `a`, in base 2
// - `pA`: the exponent of `a`, in base 2. I.e. `a = mA * 2 ^ pA`
// - `uqN` (e.g. `uq1`): this refers to Q notation for fixed-point numbers. UQ1.31 is an unsigned
//   fixed-point number with 1 integral bit, and 31 decimal bits. A `uqN` variable of type `uM`
//   will have N bits of integer and M-N bits of fraction.
// - `hw`: half width, i.e. for `Double` this will be a `UInt`.
// - `x` is the best estimate of `1 / mB`
//
// # Method Overview
//
// Division routines must solve for `a / b`, which is `res = mA * 2 ^ pA / mB * 2 ^ pB`. The basic
// process is as follows:
//
// - Rearange the exponent and significand to simplify the operations:
//   `res = (mA / mB) * 2 ^ { pA - pB }`.
// - Check for early exits (infinity, zero, etc).
// - If `a` or `b` are subnormal, normalize by shifting the mantissa and adjusting the exponent.
// - Set the implicit bit so math is correct.
// - Shift mantissa significant digits (with implicit bit) fully left such that fixed-point UQ1
//   or UQ0 numbers can be used for mantissa math. These will have greater precision than the
//   actual mantissa, which is important for correct rounding.
// - Calculate the reciprocal of `mB`, `x`.
// - Use the reciprocal to multiply rather than divide: `res = mA * xB * 2 ^ { pA - pB }`.
// - Reapply rounding.
//
// # Reciprocal calculation
//
// Calculating the reciprocal is the most complicated part of this process. It uses the
// [Newton-Raphson method], which picks an initial estimation (of the reciprocal) and performs
// a number of iterations to increase its precision.
//
// In general, Newton's method takes the following form:
//
// ```text
// `xN` is a guess or the result of a previous iteration. Increasing `n` converges to the
// desired result.
//
// The result approaches a zero of `f(x)` by applying a correction to the previous gues.
//
// x_{n+1} = xN - f(xN) / f'(xN)
// ```
//
// Applying this to find the reciprocal:
//
// ```text
// 1 / x = b
//
// Rearrange so we can solve by finding a zero
// 0 = (1 / x) - b = f(x)
//
// f'(x) = -x^{-2}
//
// x_{n+1} = 2*xN - b*xN^2
// ```
//
// This is a process that can be repeated to calculate the reciprocal with enough precision to
// achieve a correctly rounded result for the overall division operation. The maximum required
// number of iterations is known since precision doubles with each iteration.
//
// # Half-width operations
//
// Calculating the reciprocal requires widening multiplication and performing arithmetic on the
// results, meaning that emulated integer arithmetic on `ULong` (for `Double`) and `u256` (for `f128`)
// gets used instead of native math.
//
// To make this more efficient, all but the final operation can be computed using half-width
// integers. For example, rather than computing four iterations using 128-bit integers for `Double`,
// we can instead perform three iterations using native 64-bit integers and only one final
// iteration using the full 128 bits.
//
// This works because of precision doubling. Some leeway is allowed here because the fixed-point
// number has more bits than the final mantissa will.
//
// [Newton-Raphson method]: https://en.wikipedia.org/wiki/Newton%27sMethod

public fun <F : Float> div(a: F, b: F, typeLayoutF: TypeLayout<F>, fromBitsFFn: (ULong) -> F, normalizeFFn: (ULong) -> Any?): F {
    val one = F.Int.ONE
    val zero = F.Int.ZERO
    val oneHw = HalfRep.ONE
    val zeroHw = HalfRep.ZERO
    val hw = F.BITS / 2
    val loMask = F.Int.MAX shr hw.toInt()
    val significandBits = F.SIG_BITS
    // Saturated exponent, representing infinity
    val exponentSat: Int = F.EXP_SAT.cast()
    val exponentBias = F.EXP_BIAS
    val implicitBit = F.IMPLICIT_BIT
    val significandMask = F.SIG_MASK
    val signBit = F.SIGN_MASK
    val absMask = signBit - one
    val exponentMask = F.EXP_MASK
    val infRep = exponentMask
    val quietBit = implicitBit shr 1
    val qnanRep = exponentMask or quietBit
    var (halfIterations, fullIterations) = getIterations<F>(typeLayoutF = typeLayoutF)
    val recipPrecision = reciprocalPrecision<F>(typeLayoutF = typeLayoutF)
    if (F.BITS == 128) {
        // Note(tgross35): f128 seems to require one more half iteration than expected
        halfIterations += 1
    }
    val aRep = a.toBits()
    val bRep = b.toBits()
    // Exponent numeric representationm not accounting for bias
    val aExponent = (aRep shr significandBits.toInt()) and exponentSat.toULong()
    val bExponent = (bRep shr significandBits.toInt()) and exponentSat.toULong()
    val quotientSign = (aRep xor bRep) and signBit
    var aSignificand = aRep and significandMask
    var bSignificand = bRep and significandMask
    // The exponent of our final result in its encoded form
    var resExponent: Int = Int.castFrom(aExponent) - Int.castFrom(bExponent) + (exponentBias.toInt())
    // Detect if a or b is zero, denormal, infinity, or NaN.
    if ((aExponent - (one.toULong())) >= (exponentSat - one).toULong() || (bExponent - (one.toULong())) >= (exponentSat - one).toULong()) {
        val aAbs = aRep and absMask
        val bAbs = bRep and absMask
        // NaN / anything = qNaN
        if (aAbs > infRep) {
            return fromBitsFFn(aRep or quietBit)
        }
        // anything / NaN = qNaN
        if (bAbs > infRep) {
            return fromBitsFFn(bRep or quietBit)
        }
        if (aAbs == infRep) {
            if (bAbs == infRep) {
                // infinity / infinity = NaN
                return fromBitsFFn(qnanRep)
            } else {
                // infinity / anything else = +/- infinity
                return fromBitsFFn(aAbs or quotientSign)
            }
        }
        // anything else / infinity = +/- 0
        if (bAbs == infRep) {
            return fromBitsFFn(quotientSign)
        }
        if (aAbs == zero) {
            if (bAbs == zero) {
                // zero / zero = NaN
                return fromBitsFFn(qnanRep)
            } else {
                // zero / anything else = +/- zero
                return fromBitsFFn(quotientSign)
            }
        }
        // anything else / zero = +/- infinity
        if (bAbs == zero) {
            return fromBitsFFn(infRep or quotientSign)
        }
        // a is denormal. Renormalize it and set the scale to include the necessary exponent
        // adjustment.
        if (aAbs < implicitBit) {
            val (exponent, significand) = normalizeFFn(aSignificand)
            resExponent += exponent
            aSignificand = significand
        }
        // b is denormal. Renormalize it and set the scale to include the necessary exponent
        // adjustment.
        if (bAbs < implicitBit) {
            val (exponent1, significand1) = normalizeFFn(bSignificand)
            resExponent -= exponent1
            bSignificand = significand1
        }
    }
    // Set the implicit significand bit. If we fell through from the
    // denormal path it was already set by normalize( ), but setting it twice
    // won't hurt anything.
    aSignificand = aSignificand or implicitBit
    bSignificand = bSignificand or implicitBit
    // Transform to a fixed-point representation by shifting the significand to the high bits. We
    // know this is in the range [1.0, 2.0] since the implicit bit is set to 1 above.
    val bUq1 = bSignificand shl (F.BITS - significandBits - 1).toInt()
    // Align the significand of b as a UQ1.(n-1) fixed-point number in the range
    // [1.0, 2.0) and get a UQ0.n approximate reciprocal using a small minimax
    // polynomial approximation: x0 = 3/4 + 1/sqrt(2) - b/2.
    // The max error for this approximation is achieved at endpoints, so
    // abs(x0(b) - 1/b) <= abs(x0(1) - 1/1) = 3/4 - 1/sqrt(2) = 0.04289...,
    // which is about 4.5 bits.
    // The initial approximation is between x0(1.0) = 0.9571... and x0(2.0) = 0.4571...
    //
    // Then, refine the reciprocal estimate using a quadratically converging
    // Newton-Raphson iteration:
    // x_{n+1} = xN * (2 - xN * b)
    //
    // Let b be the original divisor considered "in infinite precision" and
    // obtained from IEEE754 representation of function argument (with the
    // implicit bit set). Corresponds to repT-sized bUq1 represented in
    // UQ1.(W-1).
    //
    // Let bHw be an infinitely precise number obtained from the highest (HW-1)
    // bits of divisor significand (with the implicit bit set). Corresponds to
    // halfRepT-sized bUq1Hw represented in UQ1.(HW-1) that is a **truncated**
    // version of bUq1.
    //
    // Let eN := xN - 1/bHw
    // EN := xN - 1/b
    // abs(EN) <= abs(eN) + (1/bHw - 1/b)
    // = abs(eN) + (b - bHw) / (b*bHw)
    // <= abs(eN) + 2 * 2^-HW
    //
    // repT-sized iterations may be slower than the corresponding half-width
    // variant depending on the handware and whether single/double/quad precision
    // is selected.
    //
    // NB: Using half-width iterations increases computation errors due to
    // rounding, so error estimations have to be computed taking the selected
    // mode into account!
    var xUq02 =
        if (halfIterations > 0) {
            // Starting with (n-1) half-width iterations
            val bUq1Hw: HalfRep<F> = bUq1.hi()
            // C is (3/4 + 1/sqrt(2)) - 1 truncated to W0 fractional bits as UQ0.HW
            // with W0 being either 16 or 32 and W0 <= HW.
            // That is, C is the aforementioned 3/4 + 1/sqrt(2) constant (from which
            // b/2 is subtracted to obtain x0) wrapped to [0, 1) range.
            val cHw = cHw<F>()
            // Check that the top bit is set, i.e. value is within `[1, 2)`.
            check(bUq1Hw and (oneHw shl (HalfRep.BITS - 1).toInt()) > zeroHw)
            // b >= 1, thus an upper bound for 3/4 + 1/sqrt(2) - b/2 is about 0.9572,
            // so x0 fits to UQ0.HW without wrapping.
            var xUq0Hw: HalfRep<F> = cHw.wrappingSub(bUq1Hw)
            // An e0 error is comprised of errors due to
            // * x0 being an inherently imprecise first approximation of 1/bHw
            // * CHw being some (irrational) number **truncated** to W0 bits
            // Please note that e0 is calculated against the infinitely precise
            // reciprocal of bHw (that is, **truncated** version of b).
            //
            // e0 <= 3/4 - 1/sqrt(2) + 2^-W0
            //
            // By construction, 1 <= b < 2
            // f(x)  = x * (2 - b*x) = 2*x - b*x^2
            // f'(x) = 2 * (1 - b*x)
            //
            // On the [0, 1] interval, f(0)   = 0,
            // then it increses until  f(1/b) = 1 / b, maximum on (0, 1),
            // then it decreses to     f(1)   = 2 - b
            //
            // Let g(x) = x - f(x) = b*x^2 - x.
            // On (0, 1/b), g(x) < 0 <=> f(x) > x
            // On (1/b, 1], g(x) > 0 <=> f(x) < x
            //
            // For half-width iterations, bHw is used instead of b.
            for (unused in 0 until halfIterations) {
                // corrUq1Hw can be **larger** than 2 - bHw*x by at most 1*Ulp
                // of corrUq1Hw.
                // "0.0 - (...)" is equivalent to "2.0 - (...)" in UQ1.(HW-1).
                // On the other hand, corrUq1Hw should not overflow from 2.0 to 0.0 provided
                // no overflow occurred earlier: ((repT)xUq0Hw * bUq1Hw >> HW) is
                // expected to be strictly positive because bUq1Hw has its highest bit set
                // and xUq0Hw should be rather large (it converges to 1/2 < 1/bHw <= 1).
                //
                // Now, we should multiply UQ0.HW and UQ1.(HW-1) numbers, naturally
                // obtaining an UQ1.(HW-1) number and proving its highest bit could be
                // considered to be 0 to be able to represent it in UQ0.HW.
                // From the above analysis of f(x), if corrUq1Hw would be represented
                // without any intermediate loss of precision (that is, in twiceRepT)
                // xUq0Hw could be at most [1.]000... if bHw is exactly 1.0 and strictly
                // less otherwise. On the other hand, to obtain [1.]000..., one have to pass
                // 1/bHw == 1.0 to f(x), so this cannot occur at all without overflow (due
                // to 1.0 being not representable as UQ0.HW).
                // The fact corrUq1Hw was virtually round up (due to result of
                // multiplication being **first** truncated, then negated - to improve
                // error estimations) can increase xUq0Hw by up to 2*Ulp of xUq0Hw.
                //
                // Now, either no overflow occurred or xUq0Hw is 0 or 1 in its halfRepT
                // representation. In the latter case, xUq0Hw will be either 0 or 1 after
                // any number of iterations, so just subtract 2 from the reciprocal
                // approximation after last iteration.
                //
                // In infinite precision, with 0 <= eps1, eps2 <= U = 2^-HW:
                // corrUq1Hw = 2 - (1/bHw + eN) * bHw + 2*eps1
                // = 1 - eN * bHw + 2*eps1
                // xUq0Hw = (1/bHw + eN) * (1 - eN*bHw + 2*eps1) - eps2
                // = 1/bHw - eN + 2*eps1/bHw + eN - eN^2*bHw + 2*eN*eps1 - eps2
                // = 1/bHw + 2*eps1/bHw - eN^2*bHw + 2*eN*eps1 - eps2
                // e_{n+1} = -eN^2*bHw + 2*eps1/bHw + 2*eN*eps1 - eps2
                // = 2*eN*eps1 - (eN^2*bHw + eps2) + 2*eps1/bHw
                // \------ >0 -------/   \-- >0 ---/
                // abs(e_{n+1}) <= 2*abs(eN)*U + max(2*eN^2 + U, 2 * U)
                xUq0Hw = nextGuess(xUq0Hw, bUq1Hw)
            }
            // For initial half-width iterations, U = 2^-HW
            // Let  abs(eN)     <= uN * U,
            // then abs(e_{n+1}) <= 2 * uN * U^2 + max(2 * uN^2 * U^2 + U, 2 * U)
            // u_{n+1} <= 2 * uN * U + max(2 * uN^2 * U + 1, 2)
            //
            // Account for possible overflow (see above). For an overflow to occur for the
            // first time, for "ideal" corrUq1Hw (that is, without intermediate
            // truncation), the result of xUq0Hw * corrUq1Hw should be either maximum
            // value representable in UQ0.HW or less by 1. This means that 1/bHw have to
            // be not below that value (see g(x) above), so it is safe to decrement just
            // once after the final iteration. On the other hand, an effective value of
            // divisor changes after this point (from bHw to b), so adjust here.
            xUq0Hw = xUq0Hw.wrappingSub(oneHw)
            // Error estimations for full-precision iterations are calculated just
            // as above, but with U := 2^-W and taking extra decrementing into account.
            // We need at least one such iteration.
            //
            // Simulating operations on a twiceRepT to perform a single final full-width
            // iteration. Using ad-hoc multiplication implementations to take advantage
            // of particular structure of operands.
            val blo: Int = bUq1 and loMask
            // xUq0 = xUq0Hw * 2^HW - 1
            // xUq0 * bUq1 = (xUq0Hw * 2^HW) * (bUq1Hw * 2^HW + blo) - bUq1
            //
            // <--- higher half ---><--- lower half --->
            // [xUq0Hw * bUq1Hw]
            // +            [  xUq0Hw *  blo  ]
            // -                      [      bUq1       ]
            // = [      result       ][.... discarded ...]
            val corrUq1: Int = rustWrappingNeg(((F.Int.from(xUq0Hw) * F.Int.from(bUq1Hw) + ((F.Int.from(xUq0Hw) * blo) shr hw.toInt())) - (one.toInt())))
            // account for *possible* carry
            val loCorr: Int = corrUq1 and loMask
            val hiCorr: Int = corrUq1 shr hw.toInt()
            // xUq0 * corrUq1 = (xUq0Hw * 2^HW) * (hiCorr * 2^HW + loCorr) - corrUq1
            var xUq0: Int = ((((F.Int.from(xUq0Hw) * hiCorr) shl 1u.toInt()) + ((F.Int.from(xUq0Hw) * loCorr) shr (hw - 1).toInt())) - F.Int.from(2u.toUByte()).toInt())
            xUq0 -= one
            // ... and then traditional fixup by 2 should work
            // On error estimation:
            // abs(E_{N-1}) <=   (u_{N-1} + 2 // due to conversion eN -> EN) * 2^-HW
            // + (2^-HW + 2^-W))
            // abs(E_{N-1}) <= (u_{N-1} + 3.01) * 2^-HW
            //
            // Then like for the half-width iterations:
            // With 0 <= eps1, eps2 < 2^-W
            // E_N  = 4 * E_{N-1} * eps1 - (E_{N-1}^2 * b + 4 * eps2) + 4 * eps1 / b
            // abs(E_N) <= 2^-W * [ 4 * abs(E_{N-1}) + max(2 * abs(E_{N-1})^2 * 2^W + 4, 8)) ]
            // abs(E_N) <= 2^-W * [ 4 * (u_{N-1} + 3.01) * 2^-HW + max(4 + 2 * (u_{N-1} + 3.01)^2, 8) ]
            xUq0
        } else {
            // C is (3/4 + 1/sqrt(2)) - 1 truncated to 64 fractional bits as UQ0.n
            val c: Int = F.Int.from(0x7504F333u) shl (F.BITS - 32).toInt()
            var xUq01: Int = (c - (bUq1.toInt()))
            // E_0 <= 3/4 - 1/sqrt(2) + 2 * 2^-64
            // xUq0
            for (unused1 in 0 until fullIterations) {
                xUq01 = nextGuess(xUq01, bUq1)
            }
            xUq01
        }
    // Finally, account for possible overflow, as explained above.
    xUq02 = xUq02.wrappingSub(2.cast())
    // Suppose 1/b - P * 2^-W < x < 1/b + P * 2^-W
    xUq02 -= recipPrecision.cast()
    // Now 1/b - (2*P) * 2^-W < x < 1/b
    // Note Is xUq0 still >= 0.5?
    var quotientUq1: Int = xUq02.widenMul(aSignificand shl 1).hi()
    // Now, a/b - 4*P * 2^-W < q < a/b for q=<quotientUq1:dummy> in UQ1.(SB+1+W).
    // quotientUq1 is in [0.5, 2.0) as UQ1.(SB+1),
    // adjust it to be in [1.0, 2.0) as UQ1.SB.
    var residualLo1 =
        if (quotientUq1 < (implicitBit shl 1)) {
            // Highest bit is 0, so just reinterpret quotientUq1 as UQ1.SB,
            // effectively doubling its value as well as its error estimation.
            val residualLo = ((aSignificand shl (significandBits + 1).toInt()) - (quotientUq1 * (bSignificand.toInt())).toULong())
            resExponent -= 1
            aSignificand = aSignificand shl 1
            residualLo
        } else {
            // Highest bit is 1 (the UQ1.(SB+1) value is in [1, 2)), convert it
            // to UQ1.SB by right shifting by 1. Least significant bit is omitted.
            quotientUq1 = quotientUq1 shr 1
            ((aSignificand shl significandBits.toInt()) - (quotientUq1 * (bSignificand.toInt())).toULong())
        }
    // drop mutability
    val quotient = quotientUq1
    // NB: residualLo is calculated above for the normal result case.
    // It is re-computed on denormal path that is expected to be not so
    // performance-sensitive.
    //
    // Now, q cannot be greater than a/b and can differ by at most 8*P * 2^-W + 2^-SB
    // Each NextAfter() increments the floating point value by at least 2^-SB
    // (more, if exponent was incremented).
    // Different cases (<---> is of 2^-SB length, * = a/b that is shown as a midpoint):
    // q
    // |   | * |   |   |       |       |
    // <--->      2^t
    // |   |   |   |   |   *   |       |
    // q
    // To require at most one NextAfter(), an error should be less than 1.5 * 2^-SB.
    // (8*P) * 2^-W + 2^-SB < 1.5 * 2^-SB
    // (8*P) * 2^-W         < 0.5 * 2^-SB
    // P < 2^(W-4-SB)
    // Generally, for at most R NextAfter() to be enough,
    // P < (2*R - 1) * 2^(W-4-SB)
    // For f32 (0+3): 10 < 32 (OK)
    // For f32 (2+1): 32 < 74 < 32 * 3, so two NextAfter() are required
    // For f64: 220 < 256 (OK)
    // For f128: 4096 * 3 < 13922 < 4096 * 5 (three NextAfter() are required)
    //
    // If we have overflowed the exponent, return infinity
    if (resExponent >= Int.castFrom(exponentSat)) {
        return fromBitsFFn(infRep or quotientSign)
    }
    // Now, quotient <= the correctly-rounded result
    // and may need taking NextAfter() up to 3 times (see error estimates above)
    // r = a - b * q
    var absResult =
        if (resExponent > 0) {
            var ret = quotient and significandMask
            ret = ret or (F.Int.from(resExponent.toUInt()) shl significandBits.toInt())
            residualLo1 = residualLo1 shl 1
            ret
        } else {
            if (((significandBits.toInt()) + resExponent) < 0) {
                return fromBitsFFn(quotientSign)
            }
            val ret1 = rustWrappingShr(quotient, UInt.castFrom(rustWrappingNeg(resExponent)) + 1u)
            residualLo1 = (rustWrappingShl(aSignificand, significandBits.wrappingAdd(CastInto.castLossy(resExponent)).toUInt()) - (((ret1 * (bSignificand.toInt())) shl 1).toULong()))
            ret1
        }
    residualLo1 += absResult and one
    // tie to even
    // conditionally turns the below LT comparison into LTE
    absResult += UByte.from(residualLo1 > bSignificand).into()
    if (F.BITS == 128 || (F.BITS == 32 && halfIterations > 0)) {
        // Do not round Infinity to NaN
        absResult += UByte.from(absResult < infRep && residualLo1 > (2uL + 1uL).cast() * bSignificand).into()
    }
    if (F.BITS == 128) {
        absResult += UByte.from(absResult < infRep && residualLo1 > (4uL + 1uL).cast() * bSignificand).into()
    }
    return fromBitsFFn(absResult or quotientSign.toInt())
}

/**
 * Calculate the number of iterations required for a float type's precision.
 *
 * This returns `(h, f)` where `h` is the number of iterations to be done using integers at half
 * the float's bit width, and `f` is the number of iterations done using integers of the float's
 * full width. This is further explained in the module documentation.
 *
 * # Requirements
 *
 * The initial estimate should have at least 8 bits of precision. If this is not true, results
 * will be inaccurate.
 */
public fun <F : Float> getIterations(typeLayoutF: TypeLayout<F>): Pair<Int, Int> {
    // Precision doubles with each iteration. Assume we start with 8 bits of precision.
    val totalIterations = F.BITS.ilog2().toInt() - 2
    return if (2 * sizeOf<F>(typeLayoutT = typeLayoutF) <= sizeOf<ConstPtr>(typeLayoutT = PrimitiveLayouts.constPtrLayout(64L))) {
        // If widening multiplication will be efficient (uses word-sized integers), there is no
        // reason to use half-sized iterations.
        Pair(0, totalIterations)
    } else {
        // Otherwise, do as many iterations as possible at half width.
        Pair(totalIterations - 1, 1)
    }
}

/**
 * `uN` for different precisions (with N-1 half-width iterations).
 *
 * W0 is the precision of C
 *   u0 = (3/4 - 1/sqrt(2) + 2^-W0) * 2^HW
 *
 * Estimated with bc:
 *
 * ```text
 *   define half1(un) { return 2.0 * (un + un^2) / 2.0^hw + 1.0; }
 *   define half2(un) { return 2.0 * un / 2.0^hw + 2.0; }
 *   define full1(un) { return 4.0 * (un + 3.01) / 2.0^hw + 2.0 * (un + 3.01)^2 + 4.0; }
 *   define full2(un) { return 4.0 * (un + 3.01) / 2.0^hw + 8.0; }
 *
 *             | f32 (0 + 3) | f32 (2 + 1)  | f64 (3 + 1)  | f128 (4 + 1)
 * u0         | < 184224974 | < 2812.1     | < 184224974  | < 791240234244348797
 * u1         | < 15804007  | < 242.7      | < 15804007   | < 67877681371350440
 * u2         | < 116308    | < 2.81       | < 116308     | < 499533100252317
 * u3         | < 7.31      |              | < 7.31       | < 27054456580
 * u4         |             |              |              | < 80.4
 * Final (U_N) | same as u3 | < 72         | < 218        | < 13920
 * ````
 *
 * Add 2 to `U_N` due to final decrement.
 */
public fun <F : Float> reciprocalPrecision(typeLayoutF: TypeLayout<F>): UShort {
    val (halfIterations, fullIterations) = getIterations<F>(typeLayoutF = typeLayoutF)
    if (fullIterations < 1) {
        error("Must have at least one full iteration")
    }
    // Note(tgross35): calculate this programmatically
    return if (F.BITS == 32 && halfIterations == 2 && fullIterations == 1) {
        74u.toUShort()
    } else if (F.BITS == 32 && halfIterations == 0 && fullIterations == 3) {
        10u
    } else if (F.BITS == 64 && halfIterations == 3 && fullIterations == 1) {
        220u
    } else if (F.BITS == 128 && halfIterations == 4 && fullIterations == 1) {
        13922u
    } else {
        error("Invalid number of iterations")
    }
}

/**
 * The value of `C` adjusted to half width.
 *
 * C is (3/4 + 1/sqrt(2)) - 1 truncated to W0 fractional bits as UQ0.HW with W0 being either
 * 16 or 32 and W0 <= HW. That is, C is the aforementioned 3/4 + 1/sqrt(2) constant (from
 * which b/2 is subtracted to obtain x0) wrapped to [0, 1) range.
 */
public fun <F : Float> cHw(): HalfRep<F> {
    val C_U128: U128 = U128(0x7504f333f9de6108uL, 0xb2fb1366eaa6a542uL)
    return C_U128 shr (128u - HalfRep.BITS).toInt().cast()
}

/**
 * Perform one iteration at any width to approach `1 / b`, given previous guess `x`. Returns
 * the next `x` as a UQ0 number.
 *
 * This is the `x_ { n + 1 } = 2 * xN - b * xN ^ 2` algorithm, implemented as `xN * (2 - b * xN)`. It
 * uses widening multiplication to calculate the result with necessary precision.
 */
public fun <I> nextGuess(xUq0: I, bUq1: I): I where I : Int, I : HInt {
    // `corr = 2 - b * xN`
    //
    // This looks like `0 - b * xN`. However, this works - in `UQ1`, `0.0 - x = 2.0 - x`.
    val corrUq1: I = I.ZERO.wrappingSub(xUq0.widenMul(bUq1).hi())
    // `xN * corr = xN * (2 - b * xN)`
    return (xUq0.widenMul(corrUq1) shr (I.BITS - 1).toInt()).lo()
}

public fun divsf3(a: Float, b: Float): Float = div(a, b, typeLayoutF = PrimitiveLayouts.F32)

public fun divdf3(a: Double, b: Double): Double = div(a, b, typeLayoutF = PrimitiveLayouts.F64)

public fun divtf3(a: F128, b: F128): F128 = div(a, b, typeLayoutF = ObjectSlotLayout<F128>())

public fun divkf3(a: F128, b: F128): F128 = div(a, b, typeLayoutF = ObjectSlotLayout<F128>())
