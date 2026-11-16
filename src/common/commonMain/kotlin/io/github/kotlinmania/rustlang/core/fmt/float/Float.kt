// port-lint: source fmt/float.rs
package io.github.kotlinmania.rustlang.core.fmt.float

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.fmt.Formatter
import io.github.kotlinmania.rustlang.core.fmt.Result
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf32
import io.github.kotlinmania.rustlang.core.intrinsics.kmFabsf64
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.MAX_SIG_DIGITS
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.Sign
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.decoder.DecodableFloat
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.strategy.grisu.formatExact
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.strategy.grisu.formatShortest
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.toExactExpStr
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.toExactFixedStr
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.toShortestExpStr
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.toShortestStr
import io.github.kotlinmania.rustlang.core.fmt.fmt as numfmt

public interface GeneralFormat {
    /**
     * Determines if a value should use exponential based on its magnitude, given the precondition
     * that it will not be rounded any further before it is displayed.
     */
    public fun alreadyRoundedValueShouldUseExponential(): Boolean
}

public fun F16.alreadyRoundedValueShouldUseExponential(): Boolean {
    val maxAbs = 1e16
    val abs = F16.fromFloat(kmFabsf32(this.toFloat()))
    return (abs != F16.fromFloat(0.0f) && abs < F16.fromFloat(1e-4f)) || abs >= maxAbs
}

public fun kotlin.Float.alreadyRoundedValueShouldUseExponential(): Boolean {
    val maxAbs = 1e16
    val abs = kmFabsf32(this)
    return (abs != 0.0f && abs < 1e-4f) || abs >= maxAbs
}

public fun Double.alreadyRoundedValueShouldUseExponential(): Boolean {
    val maxAbs = 1e16
    val abs = kmFabsf64(this)
    return (abs != 0.0 && abs < 1e-4) || abs >= maxAbs
}

// Don't inline this so callers don't use the stack space this function
// requires unless they have to.
public fun <T> floatToDecimalCommonExact(fmt: Formatter, num: T, sign: Sign, precision: UShort): Result where T : DecodableFloat<T> {
    val buf: List<MaybeUninit<UByte>>  = List(1024) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    // enough for f32 and f64
    val parts: List<MaybeUninit<numfmt.Part>>  = List(4) { MaybeUninit.uninit(UnresolvedTypeLayout<numfmt.Part>()) }
    val formatted = toExactFixedStr(formatExact, num, sign, precision.into(), buf, parts)
    // SAFETY: `toExactFixedStr` and `formatExact` produce only ASCII characters.
    return fmt.padFormattedParts(formatted)
}

// Don't inline this so callers that call both this and the above won't wind
// up using the combined stack space of both functions in some cases.
public fun <T> floatToDecimalCommonShortest(fmt: Formatter, num: T, sign: Sign, precision: UShort): Result where T : DecodableFloat<T> {
    // enough for f32 and f64
    val buf: List<MaybeUninit<UByte>>  = List((MAX_SIG_DIGITS).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    val parts: List<MaybeUninit<numfmt.Part>>  = List(4) { MaybeUninit.uninit(UnresolvedTypeLayout<numfmt.Part>()) }
    val formatted = toShortestStr(formatShortest, num, sign, precision.into(), buf, parts)
    // SAFETY: `toShortestStr` and `formatShortest` produce only ASCII characters.
    return fmt.padFormattedParts(formatted)
}

public fun <T> floatToDecimalDisplay(fmt: Formatter, num: T): Result where T : DecodableFloat<T> {
    val forceSign = fmt.signPlus()
    val sign =
        when (forceSign) {
            false -> Sign.Minus
            true -> Sign.MinusPlus
        }
    return when (val matchVal = fmt.options.getPrecision()) {
        null -> {
            val minPrecision = 0
            floatToDecimalCommonShortest(fmt, num, sign, minPrecision)
        }
        else -> {
            val precision = matchVal
            floatToDecimalCommonExact(fmt, num, sign, precision)
        }
    }
}

// Don't inline this so callers don't use the stack space this function
// requires unless they have to.
public fun <T> floatToExponentialCommonExact(fmt: Formatter, num: T, sign: Sign, precision: UShort, upper: Boolean): Result where T : DecodableFloat<T> {
    val buf: List<MaybeUninit<UByte>>  = List(1024) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    // enough for f32 and f64
    val parts: List<MaybeUninit<numfmt.Part>>  = List(6) { MaybeUninit.uninit(UnresolvedTypeLayout<numfmt.Part>()) }
    val formatted = toExactExpStr(formatExact, num, sign, precision.into(), upper, buf, parts)
    // SAFETY: `toExactExpStr` and `formatExact` produce only ASCII characters.
    return fmt.padFormattedParts(formatted)
}

// Don't inline this so callers that call both this and the above won't wind
// up using the combined stack space of both functions in some cases.
public fun <T> floatToExponentialCommonShortest(fmt: Formatter, num: T, sign: Sign, upper: Boolean): Result where T : DecodableFloat<T> {
    // enough for f32 and f64
    val buf: List<MaybeUninit<UByte>>  = List((MAX_SIG_DIGITS).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    val parts: List<MaybeUninit<numfmt.Part>>  = List(6) { MaybeUninit.uninit(UnresolvedTypeLayout<numfmt.Part>()) }
    val formatted = toShortestExpStr(formatShortest, num, sign, Pair(0, 0), upper, buf, parts)
    // SAFETY: `toShortestExpStr` and `formatShortest` produce only ASCII characters.
    return fmt.padFormattedParts(formatted)
}

// Common code of floating point LowerExp and UpperExp.
public fun <T> floatToExponentialCommon(fmt: Formatter, num: T, upper: Boolean): Result where T : DecodableFloat<T> {
    val forceSign = fmt.signPlus()
    val sign =
        when (forceSign) {
            false -> Sign.Minus
            true -> Sign.MinusPlus
        }
    return when (val matchVal = fmt.options.getPrecision()) {
        null -> {
            floatToExponentialCommonShortest(fmt, num, sign, upper)
        }
        else -> {
            val precision = matchVal
            floatToExponentialCommonExact(fmt, num, sign, precision, upper)
        }
    }
}

public fun <T> floatToGeneralDebug(fmt: Formatter, num: T): Result where T : DecodableFloat<T>, T : GeneralFormat {
    val forceSign = fmt.signPlus()
    val sign =
        when (forceSign) {
            false -> Sign.Minus
            true -> Sign.MinusPlus
        }
    return when (val matchVal = fmt.options.getPrecision()) {
        null -> {
            // since there is no precision, there will be no rounding
            if (num.alreadyRoundedValueShouldUseExponential()) {
                val upper = false
                floatToExponentialCommonShortest(fmt, num, sign, upper)
            } else {
                val minPrecision = 1
                floatToDecimalCommonShortest(fmt, num, sign, minPrecision)
            }
        }
        else -> {
            val precision = matchVal
            // this behavior of {:.PREC?} predates exponential formatting for {:?}
            floatToDecimalCommonExact(fmt, num, sign, precision)
        }
    }
}

public fun kotlin.Float.fmt(fmt: Formatter): Result = floatToGeneralDebug(fmt, this)

public fun Double.fmt(fmt: Formatter): Result = floatToGeneralDebug(fmt, this)

public fun F16.fmt(fmt: Formatter): Result = floatToGeneralDebug(fmt, this)

// Note(f16): A fallback is used when the backend+target does not support f16 well, in order
// to avoid ICEs.

public fun F128.fmt(f: Formatter): Result = f.writeFmt("${this.toBits()}")
