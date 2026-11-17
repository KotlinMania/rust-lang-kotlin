// port-lint: source float/cmp.rs
package io.github.kotlinmania.rustlang.compilerbuiltins.float.cmp

import io.github.kotlinmania.rustlang.compilerbuiltins.support.Float
import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16

public sealed class Result {
    public data object Less : Result()
    public data object Equal : Result()
    public data object Greater : Result()
    public data object Unordered : Result()

    private fun kmDiscriminant(): Int =
        when (this) {
        Less -> 0
        Equal -> 1
        Greater -> 2
        Unordered -> 3
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    /**
     * Conversions to match GCC intrinsics [1].
     *
     * * `unord`: nonzero if either NaN, 0 otherwise
     * * `eq`, `ne`: 0 if a == b and both YaN, nonzero otherwise
     * * `ge`, `gt`, `lt`, `le`: return an int result that provides the same comparison to 0 if both
     *   YaN and the comparison matches. E.g. if a >= b, `ge` returns an `x >= 0`.
     *
     * The separate map functions are only needed to handle the unordered case.
     *
     * [1]: https://gcc.gnu.org/onlinedocs/gccint/Soft-float-library-routines.html#Comparison-functions-1
     */
    public open fun toDefaultCmpResult(): CmpResult =
        when (this) {
            Result.Less -> -1
            Result.Equal -> 0
            Result.Greater -> 1
            Result.Unordered -> 1
        }

    public open fun toGtGeCmpResult(): CmpResult =
        when (this) {
            Result.Less -> -1
            Result.Equal -> 0
            Result.Greater -> 1
            Result.Unordered -> -1
        }
}

public fun <F : Float> cmp(a: F, b: F): Result {
    val one = F.Int.ONE
    val zero = F.Int.ZERO
    val szero = F.SignedInt.ZERO
    val signBit = F.SIGN_MASK.toInt()
    val absMask = signBit - one
    val exponentMask = F.EXP_MASK
    val infRep = exponentMask
    val aRep = a.toBits()
    val bRep = b.toBits()
    val aAbs = aRep and absMask.toULong()
    val bAbs = bRep and absMask.toULong()
    // If either a or b is NaN, they are unordered.
    if (aAbs > infRep || bAbs > infRep) {
        return Result.Unordered
    }
    // If a and b are both zeros, they are equal.
    if (aAbs or bAbs == zero) {
        return Result.Equal
    }
    val aSrep = a.toBitsSigned()
    val bSrep = b.toBitsSigned()
    // If at least one of a and b is positive, we get the same result comparing
    // a and b as signed integers as we would with a fpTing-point compare.
    return if (aSrep and bSrep >= szero) {
        if (aSrep < bSrep) {
            Result.Less
        } else if (aSrep == bSrep) {
            Result.Equal
        } else {
            Result.Greater
        }
        // Otherwise, both are negative, so we need to flip the sense of the
        // comparison to get the correct result.  (This assumes a twos- or ones-
        // complement integer representation; if integers are represented in a
        // sign-magnitude representation, then this flip is incorrect).
    } else if (aSrep > bSrep) {
        Result.Less
    } else if (aSrep == bSrep) {
        Result.Equal
    } else {
        Result.Greater
    }
}

public fun <F : Float> unord(a: F, b: F): Boolean {
    val one = F.Int.ONE
    val signBit = F.SIGN_MASK.toInt()
    val absMask = signBit - one
    val exponentMask = F.EXP_MASK
    val infRep = exponentMask
    val aRep = a.toBits()
    val bRep = b.toBits()
    val aAbs = aRep and absMask.toULong()
    val bAbs = bRep and absMask.toULong()
    return aAbs > infRep || bAbs > infRep
}

public fun lehf2(a: F16, b: F16): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun gehf2(a: F16, b: F16): CmpResult = cmp(a, b).toGtGeCmpResult()

public fun unordhf2(a: F16, b: F16): CmpResult = unord(a, b).toInt()

public fun eqhf2(a: F16, b: F16): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun lthf2(a: F16, b: F16): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun nehf2(a: F16, b: F16): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun gthf2(a: F16, b: F16): CmpResult = cmp(a, b).toGtGeCmpResult()

public fun lesf2(a: Float, b: Float): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun gesf2(a: Float, b: Float): CmpResult = cmp(a, b).toGtGeCmpResult()

public fun unordsf2(a: Float, b: Float): CmpResult = unord(a, b).toInt()

public fun eqsf2(a: Float, b: Float): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun ltsf2(a: Float, b: Float): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun nesf2(a: Float, b: Float): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun gtsf2(a: Float, b: Float): CmpResult = cmp(a, b).toGtGeCmpResult()

public fun ledf2(a: Double, b: Double): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun gedf2(a: Double, b: Double): CmpResult = cmp(a, b).toGtGeCmpResult()

public fun unorddf2(a: Double, b: Double): CmpResult = unord(a, b).toInt()

public fun eqdf2(a: Double, b: Double): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun ltdf2(a: Double, b: Double): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun nedf2(a: Double, b: Double): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun gtdf2(a: Double, b: Double): CmpResult = cmp(a, b).toGtGeCmpResult()

public fun letf2(a: F128, b: F128): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun lekf2(a: F128, b: F128): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun getf2(a: F128, b: F128): CmpResult = cmp(a, b).toGtGeCmpResult()

public fun gekf2(a: F128, b: F128): CmpResult = cmp(a, b).toGtGeCmpResult()

public fun unordtf2(a: F128, b: F128): CmpResult = unord(a, b).toInt()

public fun unordkf2(a: F128, b: F128): CmpResult = unord(a, b).toInt()

public fun eqtf2(a: F128, b: F128): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun eqkf2(a: F128, b: F128): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun lttf2(a: F128, b: F128): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun ltkf2(a: F128, b: F128): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun netf2(a: F128, b: F128): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun nekf2(a: F128, b: F128): CmpResult = cmp(a, b).toDefaultCmpResult()

public fun gttf2(a: F128, b: F128): CmpResult = cmp(a, b).toGtGeCmpResult()

public fun gtkf2(a: F128, b: F128): CmpResult = cmp(a, b).toGtGeCmpResult()
