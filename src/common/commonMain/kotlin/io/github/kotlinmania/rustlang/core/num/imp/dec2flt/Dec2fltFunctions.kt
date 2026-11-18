package io.github.kotlinmania.rustlang.core.num.imp.dec2flt

import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.num.floatparse.FloatErrorKind
import io.github.kotlinmania.rustlang.core.num.floatparse.ParseFloatError
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.common.BiasedFp
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.lemire.computeFloat
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.parse.parseInfNan
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.parse.parseNumber
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.slow.parseLongMantissa
import io.github.kotlinmania.rustlang.core.num.imp.traits.FloatExt
import io.github.kotlinmania.rustlang.core.result.Result

public fun pfeEmpty(): ParseFloatError = ParseFloatError(kind = FloatErrorKind.Empty)

// Only used in unit tests, keep public.
public fun pfeInvalid(): ParseFloatError = ParseFloatError(kind = FloatErrorKind.Invalid)

/**
 * Converts a `BiasedFp` to the closest machine float type.
 */
public fun <Int, F : FloatExt<F, Int>> biasedFpToFloat(x: BiasedFp, fromU64BitsFFn: (ULong) -> F): F {
    var word = x.m
    word = word or ((x.pBiased.toULong()) shl F.SIG_BITS.toInt())
    return fromU64BitsFFn(word)
}

// Will be inlined into a function with `@inline(never)`, see above

/**
 * Converts a decimal string into a floating point number.
 */
public fun <Int, F : Lemire<F, Int>> dec2flt(s: String): Result<F, ParseFloatError> {
    val s1 = s
    var s2 = s1.encodeToByteArray().map { it.toUByte() }
    val c = s2.first() ?: return Result.Err<F, ParseFloatError>(pfeEmpty())
    val negative = c == 45u.toUByte()
    if (c == 45u.toUByte() || c == 43u.toUByte()) {
        s2 = ((s2).subList(1, (s2).size))
    }
    if (s2.isEmpty()) {
        return Result.Err<F, ParseFloatError>(pfeInvalid())
    }
    var num =
        run {
            val matchVal = parseNumber(s2)
            when {
                matchVal != null -> {
                    val r = matchVal
                    r
                }
                matchVal == null && parseInfNan(s2, negative) != null -> { return Result.Ok<F, ParseFloatError>(value) }
                else -> { return Result.Err<F, ParseFloatError>(pfeInvalid()) }
            }
        }
    num.negative = negative
    if (!false) {
        when (val matchVal1 = num.tryFastPath<F>()) {
            null -> {}
            else -> {
                val value = matchVal1
                return Result.Ok<F, ParseFloatError>(value)
            }
        }
    }
    // If significant digits were truncated, then we can have rounding error
    // only if `mantissa + 1` produces a different result. We also avoid
    // redundantly using the Eisel-Lemire algorithm if it was unable to
    // correctly round on the first pass.
    var fp = computeFloat<F>(num.exponent, num.mantissa)
    if (num.manyDigits && fp.pBiased >= 0 && fp != computeFloat<F>(num.exponent, num.mantissa + 1uL)) {
        fp.pBiased = -1
    }
    // Unable to correctly round the float using the Eisel-Lemire algorithm.
    // Fallback to a slower, but always correct algorithm.
    if (fp.pBiased < 0) {
        fp = parseLongMantissa<F>(s2)
    }
    var float = biasedFpToFloat<F>(fp)
    if (num.negative) {
        float = -float
    }
    return Result.Ok<F, ParseFloatError>(float)
}

public fun lemirePow10FastPathF16(exponent: Int): F16 {
    val TABLE: List<F16> = listOf(F16.fromFloat(1e0f), F16.fromFloat(1e1f), F16.fromFloat(1e2f), F16.fromFloat(1e3f), F16.fromFloat(1e4f), F16.fromFloat(0.0f), F16.fromFloat(0.0f), F16.fromFloat(0.0f))
    return TABLE[exponent and 7]
}

public fun lemireFromU64F16(v: ULong): F16 {
    if (false) {
        check(v <= F16.MAX_MANTISSA_FAST_PATH)
    }
    return F16.fromULong(v)
}

public fun lemirePow10FastPathFloat(exponent: Int): kotlin.Float {
    val TABLE: FloatArray = floatArrayOf(1e0f, 1e1f, 1e2f, 1e3f, 1e4f, 1e5f, 1e6f, 1e7f, 1e8f, 1e9f, 1e10f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    return TABLE[exponent and 15]
}

public fun lemireFromU64Float(v: ULong): kotlin.Float {
    if (false) {
        check(v <= kotlin.Float.MAX_MANTISSA_FAST_PATH)
    }
    return v.toFloat()
}

public fun lemirePow10FastPathDouble(exponent: Int): Double {
    val TABLE: DoubleArray = doubleArrayOf(1e0, 1e1, 1e2, 1e3, 1e4, 1e5, 1e6, 1e7, 1e8, 1e9, 1e10, 1e11, 1e12, 1e13, 1e14, 1e15, 1e16, 1e17, 1e18, 1e19, 1e20, 1e21, 1e22, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    return TABLE[exponent and 31]
}

public fun lemireFromU64Double(v: ULong): Double {
    if (false) {
        check(v <= Double.MAX_MANTISSA_FAST_PATH)
    }
    return v.toDouble()
}
