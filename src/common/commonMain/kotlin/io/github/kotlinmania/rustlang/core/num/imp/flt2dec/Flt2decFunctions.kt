package io.github.kotlinmania.rustlang.core.num.imp.flt2dec

import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.kmCompareValues
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.decoder.DecodableFloat
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.decoder.Decoded
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.decoder.FullDecoded
import io.github.kotlinmania.rustlang.core.num.imp.flt2dec.decoder.decode
import io.github.kotlinmania.rustlang.core.num.imp.fmt.Formatted
import io.github.kotlinmania.rustlang.core.num.imp.fmt.Part
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed

/**
 * When `d` contains decimal digits, increase the last digit and propagate carry.
 * Returns a next digit when it causes the length to change.
 */
public fun roundUp(d: MutableList<UByte>): UByte? =
    run {
        val matchVal = ArrayDeque(d).indexOfLast({ c -> c != 57u.toUByte() }).takeIf { it >= 0 }
        when {
            matchVal != null -> {
                val i = matchVal
                // d[i+1..n] is all nines
                d[i] += 1u.toUByte()
                ((d).subList(i + 1, (d).size)).fill(48u.toUByte())
                null
            }
            matchVal == null && d.isEmpty() -> {
                // an empty buffer rounds up (a bit strange but reasonable)
                49u.toUByte()
            }
            else -> {
                // 999..999 rounds to 1000..000 with an increased exponent
                d[0] = 49u.toUByte()
                ((d).subList(1, (d).size)).fill(48u.toUByte())
                48u.toUByte()
            }
        }
    }

/**
 * Formats given decimal digits `0.<... buf ...> * 10 ^ exp` into the decimal form
 * with at least given number of fractional digits. The result is stored to
 * the supplied parts array and a slice of written parts is returned.
 *
 * `fracDigits` can be less than the number of actual fractional digits in `buf`;
 * it will be ignored and full digits will be printed. It is only used to print
 * additional zeroes after rendered digits. Thus `fracDigits` of 0 means that
 * it will only print given digits and nothing else.
 */
public fun digitsToDecStr(buf: List<UByte>, exp: Short, fracDigits: Int, parts: MutableList<MaybeUninit<Part>>): List<Part> {
    check(!buf.isEmpty())
    check(buf[0] > 48u.toUByte())
    check(parts.size >= 4)
    // if there is the restriction on the last digit position, `buf` is assumed to be
    // left-padded with the virtual zeroes. the number of virtual zeroes, `nzeroes`,
    // equals to `max(0, exp + fracDigits - buf.len())`, so that the position of
    // the last digit `exp - buf.len() - nzeroes` is no more than `- fracDigits`:
    //
    // |<-virtual->|
    // |<---- buf ---->|  zeroes   |     exp
    // 0. 1 2 3 4 5 6 7 8 9 _ _ _ _ _ _ x 10
    // |                  |           |
    // 10^exp    10^(exp-buf.len())   10^(exp-buf.len()-nzeroes)
    //
    // `nzeroes` is individually calculated for each case in order to avoid overflow.
    return if (exp <= 0.toShort()) {
        // the decimal point is before rendered digits: [0.][000...000][1234][____]
        val minusExp = -(exp.toInt())
        parts[0] = MaybeUninit.new(Part.Copy("0.".encodeToByteArray()), UnresolvedTypeLayout<Part>())
        parts[1] = MaybeUninit.new(Part.Zero(minusExp), UnresolvedTypeLayout<Part>())
        parts[2] = MaybeUninit.new(Part.Copy(buf), UnresolvedTypeLayout<Part>())
        if (fracDigits > buf.size && fracDigits - buf.size > minusExp) {
            parts[3] = MaybeUninit.new(Part.Zero((fracDigits - buf.size) - minusExp), UnresolvedTypeLayout<Part>())
            // SAFETY: we just initialized the elements `.. 4`.
            ((parts).subList(0, 4)).assumeInitRef()
        } else {
            // SAFETY: we just initialized the elements `.. 3`.
            ((parts).subList(0, 3)).assumeInitRef()
        }
    } else {
        val exp1 = exp.toInt()
        if (exp1 < buf.size) {
            // the decimal point is inside rendered digits: [12][.][34][____]
            parts[0] = MaybeUninit.new(Part.Copy(((buf).subList(0, exp1))), UnresolvedTypeLayout<Part>())
            parts[1] = MaybeUninit.new(Part.Copy(".".encodeToByteArray()), UnresolvedTypeLayout<Part>())
            parts[2] = MaybeUninit.new(Part.Copy(((buf).subList(exp1, (buf).size))), UnresolvedTypeLayout<Part>())
            if (fracDigits > buf.size - exp1) {
                parts[3] = MaybeUninit.new(Part.Zero(fracDigits - (buf.size - exp1)), UnresolvedTypeLayout<Part>())
                // SAFETY: we just initialized the elements `.. 4`.
                ((parts).subList(0, 4)).assumeInitRef()
            } else {
                // SAFETY: we just initialized the elements `.. 3`.
                ((parts).subList(0, 3)).assumeInitRef()
            }
        } else {
            // the decimal point is after rendered digits: [1234][____0000] or [1234][__][.][__].
            parts[0] = MaybeUninit.new(Part.Copy(buf), UnresolvedTypeLayout<Part>())
            parts[1] = MaybeUninit.new(Part.Zero(exp1 - buf.size), UnresolvedTypeLayout<Part>())
            if (fracDigits > 0) {
                parts[2] = MaybeUninit.new(Part.Copy(".".encodeToByteArray()), UnresolvedTypeLayout<Part>())
                parts[3] = MaybeUninit.new(Part.Zero(fracDigits), UnresolvedTypeLayout<Part>())
                // SAFETY: we just initialized the elements `.. 4`.
                ((parts).subList(0, 4)).assumeInitRef()
            } else {
                // SAFETY: we just initialized the elements `.. 2`.
                ((parts).subList(0, 2)).assumeInitRef()
            }
        }
    }
}

/**
 * Formats the given decimal digits `0.<... buf ...> * 10 ^ exp` into the exponential
 * form with at least the given number of fractional digits. When `upper` is `true`,
 * the exponent will be prefixed by `E`; otherwise that's `e`. The result is
 * stored to the supplied parts array and a slice of written parts is returned.
 *
 * `fracDigits` can be less than the number of actual fractional digits in `buf`;
 * it will be ignored and full digits will be printed. It is only used to print
 * additional zeroes after rendered digits. Thus, `fracDigits == 0` means that
 * it will only print the given digits and nothing else.
 *
 * For example, `buf = b"123", exp = 3, fracDigits = 4` yields the parts for
 * `1.2300e2`.
 */
public fun digitsToExpStr(buf: List<UByte>, exp: Short, fracDigits: Int, upper: Boolean, parts: MutableList<MaybeUninit<Part>>): List<Part> {
    check(!buf.isEmpty())
    check(buf[0] > 48u.toUByte())
    check(parts.size >= 6)
    var n = 0
    parts[n] = MaybeUninit.new(Part.Copy(((buf).subList(0, 1))), UnresolvedTypeLayout<Part>())
    n += 1
    // The first generated digit becomes the integral digit, anything after that is already part of
    // the fractional portion we can emit verbatim.
    val actualFracDigits = buf.size - 1
    if (actualFracDigits > 0 || fracDigits > 0) {
        // Emit a decimal point either when we already have fractional digits or when the requested
        // precision needs trailing zeroes after the radix point.
        parts[n] = MaybeUninit.new(Part.Copy(".".encodeToByteArray()), UnresolvedTypeLayout<Part>())
        n += 1
        if (actualFracDigits > 0) {
            parts[n] = MaybeUninit.new(Part.Copy(((buf).subList(1, (buf).size))), UnresolvedTypeLayout<Part>())
            n += 1
        }
        if (fracDigits > actualFracDigits) {
            // formatExact exhausted the meaningful digits, so extend the fractional part with
            // zeroes up to the requested precision.
            parts[n] = MaybeUninit.new(Part.Zero(fracDigits - actualFracDigits), UnresolvedTypeLayout<Part>())
            n += 1
        }
    }
    // 0.1234 x 10^exp = 1.234 x 10^(exp-1)
    val exp1 = exp.toInt() - 1
    // avoid underflow when exp is i16.Min
    if (exp1 < 0) {
        parts[n] = MaybeUninit.new(Part.Copy(if (upper) {
            "E-".encodeToByteArray()
        } else {
            "e-".encodeToByteArray()
        }), UnresolvedTypeLayout<Part>())
        parts[n + 1] = MaybeUninit.new(Part.Num((-exp1).toUShort()), UnresolvedTypeLayout<Part>())
    } else {
        parts[n] = MaybeUninit.new(Part.Copy(if (upper) {
            "E".encodeToByteArray()
        } else {
            "e".encodeToByteArray()
        }), UnresolvedTypeLayout<Part>())
        parts[n + 1] = MaybeUninit.new(Part.Num(exp1.toUShort()), UnresolvedTypeLayout<Part>())
    }
    // SAFETY: we just initialized the elements `.. n + 2`.
    return ((parts).subList(0, n + 2)).assumeInitRef()
}

/**
 * Returns the static byte string corresponding to the sign to be formatted.
 * It can be either `""`, `"+"` or `"-"`.
 */
public fun determineSign(sign: Sign, decoded: FullDecoded, negative: Boolean): String =
    run {
        val matchVal = Pair(decoded, sign)
        when {
            matchVal.first == FullDecoded.Nan -> ""
            matchVal.second == Sign.Minus -> {
                if (negative) {
                    "-"
                } else {
                    ""
                }
            }
            else -> {
                if (negative) {
                    "-"
                } else {
                    "+"
                }
            }
        }
    }

/**
 * Formats the given floating point number into the decimal form with at least
 * given number of fractional digits. The result is stored to the supplied parts
 * array while utilizing given byte buffer as a scratch. `upper` is currently
 * unused but left for the future decision to change the case of non-finite values,
 * i.e., `inf` and `nan`. The first part to be rendered is always a `Part.Sign`
 * (which can be an empty string if no sign is rendered).
 *
 * `formatShortest` should be the underlying digit-generation function.
 * It should return the part of the buffer that it initialized.
 * You probably would want `strategy.grisu.formatShortest` for this.
 *
 * `fracDigits` can be less than the number of actual fractional digits in `v`;
 * it will be ignored and full digits will be printed. It is only used to print
 * additional zeroes after rendered digits. Thus `fracDigits` of 0 means that
 * it will only print given digits and nothing else.
 *
 * The byte buffer should be at least `MAX_SIG_DIGITS` bytes long.
 * There should be at least 4 parts available, due to the worst case like
 * `[+][0.][0000][2][0000]` with `fracDigits = 10`.
 */
public fun <Int, T> toShortestStr(formatShortest: (Decoded, MutableList<MaybeUninit<UByte>>) -> Pair<List<UByte>, Short>, v: T, sign: Sign, fracDigits: kotlin.Int, buf: MutableList<MaybeUninit<UByte>>, parts: MutableList<MaybeUninit<Part>>): Formatted where T : DecodableFloat<T, Int> {
    check(parts.size >= 4)
    check(buf.size >= MAX_SIG_DIGITS)
    val (negative, fullDecoded) = decode(v)
    val sign1 = determineSign(sign, fullDecoded, negative)
    return when (fullDecoded) {
        FullDecoded.Nan -> {
            parts[0] = MaybeUninit.new(Part.Copy("NaN".encodeToByteArray()), UnresolvedTypeLayout<Part>())
            // SAFETY: we just initialized the elements `.. 1`.
            Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
        }
        FullDecoded.Infinite -> {
            parts[0] = MaybeUninit.new(Part.Copy("inf".encodeToByteArray()), UnresolvedTypeLayout<Part>())
            // SAFETY: we just initialized the elements `.. 1`.
            Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
        }
        FullDecoded.Zero -> {
            if ((kmCompareValues(fracDigits, 0) > 0)) {
                // [0.][0000]
                parts[0] = MaybeUninit.new(Part.Copy("0.".encodeToByteArray()), UnresolvedTypeLayout<Part>())
                parts[1] = MaybeUninit.new(Part.Zero(fracDigits), UnresolvedTypeLayout<Part>())
                Formatted(sign = sign1, parts = ((parts).subList(0, 2)).assumeInitRef())
            } else {
                parts[0] = MaybeUninit.new(Part.Copy("0".encodeToByteArray()), UnresolvedTypeLayout<Part>())
                Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
            }
        }
        is FullDecoded.Finite -> {
            val (decoded) = fullDecoded
            val (buf1, exp) = formatShortest(decoded, buf)
            Formatted(sign = sign1, parts = digitsToDecStr(buf1, exp, fracDigits, parts))
        }
    }
}

/**
 * Formats the given floating point number into the decimal form or
 * the exponential form, depending on the resulting exponent. The result is
 * stored to the supplied parts array while utilizing given byte buffer
 * as a scratch. `upper` is used to determine the case of non-finite values
 * (`inf` and `nan`) or the case of the exponent prefix (`e` or `E`).
 * The first part to be rendered is always a `Part.Sign` (which can be
 * an empty string if no sign is rendered).
 *
 * `formatShortest` should be the underlying digit-generation function.
 * It should return the part of the buffer that it initialized.
 * You probably would want `strategy.grisu.formatShortest` for this.
 *
 * The `decBounds` is a tuple `(lo, hi)` such that the number is formatted
 * as decimal only when `10 ^ lo <= V<10 ^ hi`. Note that this is the *apparent* `V`
 * instead of the actual `v`! Thus any printed exponent in the exponential form
 * cannot be in this range, avoiding any confusion.
 *
 * The byte buffer should be at least `MAX_SIG_DIGITS` bytes long.
 * There should be at least 6 parts available, due to the worst case like
 * `[+][1][.][2345][e][-][6]`.
 */
public fun <Int, T> toShortestExpStr(formatShortest: (Decoded, MutableList<MaybeUninit<UByte>>) -> Pair<List<UByte>, Short>, v: T, sign: Sign, decBounds: Pair<Short, Short>, upper: Boolean, buf: MutableList<MaybeUninit<UByte>>, parts: MutableList<MaybeUninit<Part>>): Formatted where T : DecodableFloat<T, Int> {
    check(parts.size >= 6)
    check(buf.size >= MAX_SIG_DIGITS)
    check(decBounds.first <= decBounds.second)
    val (negative, fullDecoded) = decode(v)
    val sign1 = determineSign(sign, fullDecoded, negative)
    return when (fullDecoded) {
        FullDecoded.Nan -> {
            parts[0] = MaybeUninit.new(Part.Copy("NaN".encodeToByteArray()), UnresolvedTypeLayout<Part>())
            // SAFETY: we just initialized the elements `.. 1`.
            Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
        }
        FullDecoded.Infinite -> {
            parts[0] = MaybeUninit.new(Part.Copy("inf".encodeToByteArray()), UnresolvedTypeLayout<Part>())
            // SAFETY: we just initialized the elements `.. 1`.
            Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
        }
        FullDecoded.Zero -> {
            parts[0] = if (decBounds.first <= 0.toShort() && 0.toShort() < decBounds.second) {
                MaybeUninit.new(Part.Copy("0".encodeToByteArray()), UnresolvedTypeLayout<Part>())
            } else {
                MaybeUninit.new(Part.Copy(if (upper) {
                    "0E0".encodeToByteArray()
                } else {
                    "0e0".encodeToByteArray()
                }), UnresolvedTypeLayout<Part>())
            }
            // SAFETY: we just initialized the elements `.. 1`.
            Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
        }
        is FullDecoded.Finite -> {
            val (decoded) = fullDecoded
            val (buf1, exp) = formatShortest(decoded, buf)
            val visExp = ((exp as Any?) as kotlin.Int) - 1
            val parts1 =
                if ((kmCompareValues(((decBounds.first as Any?) as kotlin.Int), visExp) <= 0) && (kmCompareValues(visExp, ((decBounds.second as Any?) as kotlin.Int)) < 0)) {
                    digitsToDecStr(buf1, exp, 0, parts)
                } else {
                    digitsToExpStr(buf1, exp, 0, upper, parts)
                }
            Formatted(sign = sign1, parts = parts1)
        }
    }
}

/**
 * Returns a rather crude approximation (upper bound) for the maximum buffer size
 * calculated from the given decoded exponent.
 *
 * The exact limit is:
 *
 * - when `exp<0`, the maximum length is `ceil(log10(5 ^ - exp * (2 ^ 64 - 1)))`.
 * - when `exp >= 0`, the maximum length is `ceil(log10(2 ^ exp * (2 ^ 64 - 1)))`.
 *
 * `ceil(log10(x ^ exp * (2 ^ 64 - 1)))` is less than `ceil(log10 (2^64 - 1)) +
 * ceil(exp * log10 x)`, which is in turn less than`20 + (1 + exp * log10 x)`.
 * We use the facts that `log10 2<5 / 16` and `log10 5<12 / 16`, which is
 * enough for our purposes.
 *
 * Why do we need this? `formatExact` functions will fill the entire buffer
 * unless limited by the last digit restriction, but it is possible that
 * the number of digits requested is ridiculously large (say, 30,000 digits).
 * The vast majority of buffer will be filled with zeroes, so we don't want to
 * allocate all the buffer beforehand. Consequently, for any given arguments,
 * 826 bytes of buffer should be sufficient for `Double`. Compare this with
 * the actual number for the worst case: 770 bytes (when `exp = - 1074`).
 */
public fun estimateMaxBufLen(exp: Short): Int =
    21 +
        ((if (exp < 0.toShort()) {
            -12
        } else {
            5
        } * exp.toInt()) shr 4)

/**
 * Formats given floating point number into the exponential form with
 * exactly given number of fractional digits. The result is stored to
 * the supplied parts array while utilizing given byte buffer as a scratch.
 * `upper` is used to determine the case of the exponent prefix (`e` or `E`).
 * The first part to be rendered is always a `Part.Sign` (which can be
 * an empty string if no sign is rendered).
 *
 * `formatExact` should be the underlying digit-generation function.
 * It should return the part of the buffer that it initialized.
 * You probably would want `strategy.grisu.formatExact` for this.
 *
 * The returned format is `[sign][digit][.fraction][zero padding][e | E][exponent]`.
 *
 * The byte buffer should be at least `fracDigits + 1` bytes long unless
 * `fracDigits` is so large that only the fixed number of digits will be ever written.
 * (The tipping point for `Double` is about 800, so 1000 bytes should be enough.)
 * There should be at least 6 parts available, due to the worst case like
 * `[+][1][.][2345][e][-][6]`.
 */
public fun <Int, T> toExactExpStr(formatExact: (Decoded, MutableList<MaybeUninit<UByte>>, Short) -> Pair<List<UByte>, Short>, v: T, sign: Sign, fracDigits: kotlin.Int, upper: Boolean, buf: MutableList<MaybeUninit<UByte>>, parts: MutableList<MaybeUninit<Part>>): Formatted where T : DecodableFloat<T, Int> {
    check(parts.size >= 6)
    val (negative, fullDecoded) = decode(v)
    val sign1 = determineSign(sign, fullDecoded, negative)
    return when (fullDecoded) {
        FullDecoded.Nan -> {
            parts[0] = MaybeUninit.new(Part.Copy("NaN".encodeToByteArray()), UnresolvedTypeLayout<Part>())
            // SAFETY: we just initialized the elements `.. 1`.
            Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
        }
        FullDecoded.Infinite -> {
            parts[0] = MaybeUninit.new(Part.Copy("inf".encodeToByteArray()), UnresolvedTypeLayout<Part>())
            // SAFETY: we just initialized the elements `.. 1`.
            Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
        }
        FullDecoded.Zero -> {
            if ((kmCompareValues(fracDigits, 0) > 0)) {
                // [0.][0000][e0]
                parts[0] = MaybeUninit.new(Part.Copy("0.".encodeToByteArray()), UnresolvedTypeLayout<Part>())
                parts[1] = MaybeUninit.new(Part.Zero(fracDigits), UnresolvedTypeLayout<Part>())
                parts[2] = MaybeUninit.new(Part.Copy(if (upper) {
                    "E0".encodeToByteArray()
                } else {
                    "e0".encodeToByteArray()
                }), UnresolvedTypeLayout<Part>())
                Formatted(sign = sign1, parts = ((parts).subList(0, 3)).assumeInitRef())
            } else {
                parts[0] = MaybeUninit.new(Part.Copy(if (upper) {
                    "0E0".encodeToByteArray()
                } else {
                    "0e0".encodeToByteArray()
                }), UnresolvedTypeLayout<Part>())
                Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
            }
        }
        is FullDecoded.Finite -> {
            val (decoded) = fullDecoded
            val maxlen = estimateMaxBufLen(decoded.exp)
            // Scratch space is only needed for the significant digits that `formatExact` can
            // actually generate. Any remaining requested fractional precision becomes a trailing
            // `Part.Zero`.
            val sigDigits =
                if ((kmCompareValues(fracDigits, maxlen) < 0)) {
                    fracDigits + 1
                } else {
                    maxlen
                }
            check(buf.size >= sigDigits)
            val (buf1, exp) = formatExact(decoded, buf.subList(0, sigDigits), Short.MIN_VALUE)
            Formatted(sign = sign1, parts = digitsToExpStr(buf1, exp, fracDigits, upper, parts))
        }
    }
}

/**
 * Formats given floating point number into the decimal form with exactly
 * given number of fractional digits. The result is stored to the supplied parts
 * array while utilizing given byte buffer as a scratch. `upper` is currently
 * unused but left for the future decision to change the case of non-finite values,
 * i.e., `inf` and `nan`. The first part to be rendered is always a `Part.Sign`
 * (which can be an empty string if no sign is rendered).
 *
 * `formatExact` should be the underlying digit-generation function.
 * It should return the part of the buffer that it initialized.
 * You probably would want `strategy.grisu.formatExact` for this.
 *
 * The byte buffer should be enough for the output unless `fracDigits` is
 * so large that only the fixed number of digits will be ever written.
 * (The tipping point for `Double` is about 800, and 1000 bytes should be enough.)
 * There should be at least 4 parts available, due to the worst case like
 * `[+][0.][0000][2][0000]` with `fracDigits = 10`.
 */
public fun <Int, T> toExactFixedStr(formatExact: (Decoded, MutableList<MaybeUninit<UByte>>, Short) -> Pair<List<UByte>, Short>, v: T, sign: Sign, fracDigits: kotlin.Int, buf: MutableList<MaybeUninit<UByte>>, parts: MutableList<MaybeUninit<Part>>): Formatted where T : DecodableFloat<T, Int> {
    check(parts.size >= 4)
    val (negative, fullDecoded) = decode(v)
    val sign1 = determineSign(sign, fullDecoded, negative)
    return when (fullDecoded) {
        FullDecoded.Nan -> {
            parts[0] = MaybeUninit.new(Part.Copy("NaN".encodeToByteArray()), UnresolvedTypeLayout<Part>())
            // SAFETY: we just initialized the elements `.. 1`.
            Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
        }
        FullDecoded.Infinite -> {
            parts[0] = MaybeUninit.new(Part.Copy("inf".encodeToByteArray()), UnresolvedTypeLayout<Part>())
            // SAFETY: we just initialized the elements `.. 1`.
            Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
        }
        FullDecoded.Zero -> {
            if ((kmCompareValues(fracDigits, 0) > 0)) {
                // [0.][0000]
                parts[0] = MaybeUninit.new(Part.Copy("0.".encodeToByteArray()), UnresolvedTypeLayout<Part>())
                parts[1] = MaybeUninit.new(Part.Zero(fracDigits), UnresolvedTypeLayout<Part>())
                Formatted(sign = sign1, parts = ((parts).subList(0, 2)).assumeInitRef())
            } else {
                parts[0] = MaybeUninit.new(Part.Copy("0".encodeToByteArray()), UnresolvedTypeLayout<Part>())
                Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
            }
        }
        is FullDecoded.Finite -> {
            val (decoded) = fullDecoded
            val maxlen = estimateMaxBufLen(decoded.exp)
            check(buf.size >= maxlen)
            // it *is* possible that `fracDigits` is ridiculously large.
            // `formatExact` will end rendering digits much earlier in this case,
            // because we are strictly limited by `maxlen`.
            val limit =
                if ((kmCompareValues(fracDigits, 0x8000) < 0)) {
                    -(fracDigits.toShort())
                } else {
                    Short.MIN_VALUE
                }
            val (buf1, exp) = formatExact(decoded, buf.subList(0, maxlen), limit)
            if (exp <= limit) {
                if (false) {
                    when (val matchVal = Pair(buf1.size, 0)) {
                        else -> {
                            val leftVal = matchVal.first
                            val rightVal = matchVal.second
                            if (!(leftVal == rightVal)) {
                                val kind = AssertKind.Eq
                                assertFailed(kind, leftVal, rightVal, Option.None())
                            }
                        }
                    }
                }
                if ((kmCompareValues(fracDigits, 0) > 0)) {
                    // [0.][0000]
                    parts[0] = MaybeUninit.new(Part.Copy("0.".encodeToByteArray()), UnresolvedTypeLayout<Part>())
                    parts[1] = MaybeUninit.new(Part.Zero(fracDigits), UnresolvedTypeLayout<Part>())
                    Formatted(sign = sign1, parts = ((parts).subList(0, 2)).assumeInitRef())
                } else {
                    parts[0] = MaybeUninit.new(Part.Copy("0".encodeToByteArray()), UnresolvedTypeLayout<Part>())
                    Formatted(sign = sign1, parts = ((parts).subList(0, 1)).assumeInitRef())
                }
            } else {
                Formatted(sign = sign1, parts = digitsToDecStr(buf1, exp, fracDigits, parts))
            }
        }
    }
}
