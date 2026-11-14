// port-lint: source fmt/num.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.fmt.num

import io.github.kotlinmania.numfmt.Formatted
import io.github.kotlinmania.numfmt.Part
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.core.char.isAsciiAlphabetic
import io.github.kotlinmania.rustlang.core.fmt.numbuffer.NumBuffer
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.rustCheckedPow
import io.github.kotlinmania.rustlang.core.num.rustPow
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr

// Integer and floating-point number formatting
//
// Formatting of integers with a non-decimal radix.
//
// Formatting of integers with a non-decimal radix.

/**
 * Format unsigned integers in the radix.
 */
public fun Int.fmt(f: StringBuilder): Result<Unit, Unit> {
    val DIG_TAB: List<UByte> = "01".encodeToByteArray().map { it.toUByte() }
    val BASE: Int = DIG_TAB.len()
    val MAX_DIG_N: Int = Int.MAX_VALUE.ilog(BASE).toInt() + 1
    run {
        check(0 == 0) { "need unsigned" }
        check("01".encodeToByteArray().isAscii()) { "need single-byte entries" }
    }
    val buf = MutableList((MAX_DIG_N).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var offset = buf.size
    var remain = this
    while (true) {
        val digit = remain % BASE
        remain /= BASE
        offset -= 1
        assertUnchecked(offset < buf.size)
        buf[offset].write(DIG_TAB[digit])
        if (remain == 0) {
            break
        }
    }
    val digits = sliceBufferToStr(buf, offset)
    return f.padIntegral(true, "0b", digits)
}

/**
 * Format unsigned integers in the radix.
 */
public fun UByte.fmt(f: StringBuilder): Result<Unit, Unit> {
    val DIG_TAB: List<UByte> = "01".encodeToByteArray().map { it.toUByte() }
    val BASE: UByte = DIG_TAB.len().toUByte()
    val MAX_DIG_N: Int = UByte.MAX_VALUE.ilog(BASE).toInt() + 1
    run {
        check(UByte.MIN_VALUE == 0u.toUByte()) { "need unsigned" }
        check("01".encodeToByteArray().isAscii()) { "need single-byte entries" }
    }
    val buf = MutableList((MAX_DIG_N).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var offset = buf.size
    var remain = this
    while (true) {
        val digit = remain % BASE
        remain /= BASE
        offset -= 1
        assertUnchecked(offset < buf.size)
        buf[offset].write(DIG_TAB[digit.toInt()])
        if (remain == 0u.toUByte()) {
            break
        }
    }
    val digits = sliceBufferToStr(buf, offset)
    return f.padIntegral(true, "0b", digits)
}

/**
 * Format signed integers in the two’s-complement form.
 */
public fun Byte.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.castUnsigned()).toString()); Result.Ok(Unit) }

/**
 * Format unsigned integers in the radix.
 */
public fun UShort.fmt(f: StringBuilder): Result<Unit, Unit> {
    val DIG_TAB: List<UByte> = "01".encodeToByteArray().map { it.toUByte() }
    val BASE: UShort = DIG_TAB.len().toUShort()
    val MAX_DIG_N: Int = UShort.MAX_VALUE.ilog(BASE).toInt() + 1
    run {
        check(UShort.MIN_VALUE == 0u.toUShort()) { "need unsigned" }
        check("01".encodeToByteArray().isAscii()) { "need single-byte entries" }
    }
    val buf = MutableList((MAX_DIG_N).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var offset = buf.size
    var remain = this
    while (true) {
        val digit = remain % BASE
        remain /= BASE
        offset -= 1
        assertUnchecked(offset < buf.size)
        buf[offset].write(DIG_TAB[digit.toInt()])
        if (remain == 0u.toUShort()) {
            break
        }
    }
    val digits = sliceBufferToStr(buf, offset)
    return f.padIntegral(true, "0b", digits)
}

/**
 * Format signed integers in the two’s-complement form.
 */
public fun Short.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.castUnsigned()).toString()); Result.Ok(Unit) }

/**
 * Format unsigned integers in the radix.
 */
public fun UInt.fmt(f: StringBuilder): Result<Unit, Unit> {
    val DIG_TAB: List<UByte> = "01".encodeToByteArray().map { it.toUByte() }
    val BASE: UInt = DIG_TAB.len().toUInt()
    val MAX_DIG_N: Int = UInt.MAX_VALUE.ilog(BASE).toInt() + 1
    run {
        check(UInt.MIN_VALUE == 0u) { "need unsigned" }
        check("01".encodeToByteArray().isAscii()) { "need single-byte entries" }
    }
    val buf = MutableList((MAX_DIG_N).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var offset = buf.size
    var remain = this
    while (true) {
        val digit = remain % BASE
        remain /= BASE
        offset -= 1
        assertUnchecked(offset < buf.size)
        buf[offset].write(DIG_TAB[digit.toInt()])
        if (remain == 0u) {
            break
        }
    }
    val digits = sliceBufferToStr(buf, offset)
    return f.padIntegral(true, "0b", digits)
}

/**
 * Format unsigned integers in the radix.
 */
public fun ULong.fmt(f: StringBuilder): Result<Unit, Unit> {
    val DIG_TAB: List<UByte> = "01".encodeToByteArray().map { it.toUByte() }
    val BASE: ULong = DIG_TAB.len().toULong()
    val MAX_DIG_N: Int = ULong.MAX_VALUE.ilog(BASE).toInt() + 1
    run {
        check(ULong.MIN_VALUE == 0uL) { "need unsigned" }
        check("01".encodeToByteArray().isAscii()) { "need single-byte entries" }
    }
    val buf = MutableList((MAX_DIG_N).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var offset = buf.size
    var remain = this
    while (true) {
        val digit = remain % BASE
        remain /= BASE
        offset -= 1
        assertUnchecked(offset < buf.size)
        buf[offset].write(DIG_TAB[digit.toInt()])
        if (remain == 0uL) {
            break
        }
    }
    val digits = sliceBufferToStr(buf, offset)
    return f.padIntegral(true, "0b", digits)
}

/**
 * Format signed integers in the two’s-complement form.
 */
public fun Long.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.castUnsigned()).toString()); Result.Ok(Unit) }

/**
 * Format unsigned integers in the radix.
 */
public fun U128.fmt(f: StringBuilder): Result<Unit, Unit> {
    val DIG_TAB: List<UByte> = "01".encodeToByteArray().map { it.toUByte() }
    val BASE: U128 = U128.fromInt(DIG_TAB.len())
    val MAX_DIG_N: Int = U128.MAX_VALUE.ilog(BASE).toInt() + 1
    run {
        check(U128.MIN_VALUE == U128(0x0uL, 0x0uL)) { "need unsigned" }
        check("01".encodeToByteArray().isAscii()) { "need single-byte entries" }
    }
    val buf = MutableList((MAX_DIG_N).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    var offset = buf.size
    var remain = this
    while (true) {
        val digit = remain % BASE
        remain /= BASE
        offset -= 1
        assertUnchecked(offset < buf.size)
        buf[offset].write(DIG_TAB[digit.toInt()])
        if (remain == U128(0x0uL, 0x0uL)) {
            break
        }
    }
    val digits = sliceBufferToStr(buf, offset)
    return f.padIntegral(true, "0b", digits)
}

/**
 * Format signed integers in the two’s-complement form.
 */
public fun I128.fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.castUnsigned()).toString()); Result.Ok(Unit) }

// The string of all two-digit numbers in range 00..99 is used as a lookup table.
public var DECIMAL_PAIRS: UByteArray = "00010203040506070809101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899".encodeToByteArray().toUByteArray()

/**
 * This function converts a slice of ascii characters into a `String` starting from `offset`.
 *
 * # Safety
 *
 * `buf` content starting from `offset` index MUST BE initialized and MUST BE ascii
 * characters.
 */
public fun sliceBufferToStr(buf: List<MaybeUninit<UByte>>, offset: Int): String {
    // SAFETY: `offset` is always included between 0 and `buf`s length.
    val written = buf.getUnchecked(offset until Int.MAX_VALUE)
    // SAFETY: (`assumeInitRef`) All buf content since offset is set.
    // SAFETY: (`fromUtf8Unchecked`) Writes use ASCII from the lookup table exclusively.
    return String.fromUtf8Unchecked(written.assumeInitRef())
}

public fun expU128(f: StringBuilder, n: U128, isNonnegative: Boolean, letterE: UByte) {
    val MAX_DEC_N: Int = U128.MAX_VALUE.ilog10().toInt() + 1
    val MAX_COEF_LEN: Int = MAX_DEC_N + ".".len()
    val MAX_TEXT_LEN: Int = MAX_COEF_LEN + "e99".len()
    if (false) {
        check(letterE.isAsciiAlphabetic()) { "single-byte character" }
    }
    var exp = n.checkedIlog10().unwrapOr(0).toInt()
    if (false) {
        check(n / rustPow((U128.fromLong(10.toLong())), exp.toUInt()) < U128(0x0uL, 0xauL))
    }
    var coefPrec = exp
    var coef = n
    val morePrec =
        run {
            val matchVal = f.precision()
            when {
                matchVal == null -> {
                    while (coefPrec != 0 && coef % U128(0x0uL, 0xauL) == U128(0x0uL, 0x0uL)) {
                        coef /= U128(0x0uL, 0xauL)
                        coefPrec -= 1
                    }
                    0
                }
                matchVal != null && matchVal >= coefPrec -> {
                    val fmtPrec = matchVal
                    fmtPrec - coefPrec
                }
                else -> {
                    val fmtPrec1 = matchVal
                    val lessPrec = coefPrec - fmtPrec1
                    check(lessPrec > 0)
                    val scale = rustCheckedPow((U128.fromLong(10.toLong())), lessPrec.toUInt()).unwrapUnchecked()
                    val floor = coef / scale
                    val over = coef % scale
                    val half = scale / 2
                    val roundUp =
                        if (over < half) {
                            0
                        } else if (over > half) {
                            1
                        } else {
                            floor and U128(0x0uL, 0x1uL)
                        }
                    coef = floor + roundUp
                    coefPrec = fmtPrec1
                    if (roundUp != 0 && coef.checkedIlog10().unwrapOr(0).toInt() > coefPrec) {
                        if (false) {
                            when (val matchVal1 = Pair(coef, rustPow((U128.fromLong(10.toLong())), coefPrec.toUInt() + 1u))) {
                                else -> {
                                    val leftVal = matchVal1.first
                                    val rightVal = matchVal1.second
                                    if (!(leftVal == rightVal)) {
                                        val kind = AssertKind.Eq
                                        assertFailed(kind, leftVal, rightVal, Option.None)
                                    }
                                }
                            }
                        }
                        coef /= U128(0x0uL, 0xauL)
                        exp += 1
                    }
                    0
                }
            }
        }
    val buf = MutableList((MAX_TEXT_LEN).toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) }
    val (leadDec, coefLen) =
        if (coefPrec == 0 && morePrec == 0) {
            Pair(coef, 1)
        } else {
            buf[1].write(46u)
            val fractionRange = 2 until (2 + coefPrec)
            var remain = coef
            for (i in fractionRange.clone().rev()) {
                val digit = (remain % U128(0x0uL, 0xauL)).toInt()
                remain /= U128(0x0uL, 0xauL)
                buf[i].write(48u + digit.toUByte())
            }
            run {
                for (i1 in fractionRange.clone().skip(1).rev().stepBy(2)) {
                    val pair = (remain % U128(0x0uL, 0x64uL)).toInt()
                    remain /= U128(0x0uL, 0x64uL)
                    buf[i1 - 1].write(DECIMAL_PAIRS[pair * 2 + 0])
                    buf[i1 - 0].write(DECIMAL_PAIRS[pair * 2 + 1])
                }
                if (coefPrec and 1 != 0) {
                    val digit1 = (remain % U128(0x0uL, 0xauL)).toInt()
                    remain /= U128(0x0uL, 0xauL)
                    buf[fractionRange.start].write(48u + digit1.toUByte())
                }
            }
            Pair(remain, fractionRange.end)
        }
    if (false) {
        check(leadDec < 10)
    }
    if (false) {
        check(leadDec != 0 || coef == U128(0x0uL, 0x0uL)) { "significant digits only" }
    }
    buf[0].write(48u + leadDec.toUByte())
    assertUnchecked(coefLen <= MAX_COEF_LEN)
    buf[coefLen].write(letterE)
    val textLen: Int =
        when {
            exp < 10 -> {
                buf[coefLen + 1].write(48u + exp.toUByte())
                coefLen + 2
            }
            exp in 10 until 100 -> {
                run {
                    buf[coefLen + 1].write(48u + (exp / 10).toUByte())
                    buf[coefLen + 2].write(48u + (exp % 10).toUByte())
                }
                run {
                    buf[coefLen + 1].write(DECIMAL_PAIRS[exp * 2 + 0])
                    buf[coefLen + 2].write(DECIMAL_PAIRS[exp * 2 + 1])
                }
                coefLen + 3
            }
            else -> {
                check(U128.MAX_VALUE.ilog10() < 100)
                unreachableUnchecked()
            }
        }
    val text = buf[0 until textLen].assumeInitRef()
    if (morePrec == 0) {
        val asStr = String.fromUtf8Unchecked(text)
        f.padIntegral(isNonnegative, "", asStr)
    } else {
        val parts = listOf(Part.Copy(text[0 until coefLen]), Part.Zero(morePrec), Part.Copy(text[coefLen until text.size]))
        val sign =
            if (!isNonnegative) {
                "-"
            } else if (f.signPlus()) {
                "+"
            } else {
                ""
            }
        f.padFormattedParts(Formatted(sign = sign, parts = parts))
    }
}

public val U128_MAX_DEC_N: Int = U128.MAX_VALUE.ilog10().toInt() + 1

/**
 * Format optimized for u128. Computation of 128 bits is limited by processing
 * in batches of 16 decimals at a time.
 */
public fun U128.fmt(buf: MutableList<MaybeUninit<UByte>>): String {
    // SAFETY: `buf` will always be big enough to contain all digits.
    val offset = this.fmtInner(buf)
    // SAFETY: Starting from `offset`, all elements of the slice have been set.
    return sliceBufferToStr(buf, offset)
}

public fun U128.fmtInner(buf: MutableList<MaybeUninit<UByte>>): Int {
    // Optimize common-case zero, which would also need special treatment due to
    // its "leading" zero.
    if (this == U128(0x0uL, 0x0uL)) {
        val offset = buf.size - 1
        buf[offset].write(48u)
        return offset
    }
    // Take the 16 least-significant decimals.
    val (quot1e16, mod1e16) = divRem1e16(this)
    var (remain, offset1) =
        if (quot1e16 == 0) {
            Pair(mod1e16, U128_MAX_DEC_N)
        } else {
            // Write digits at buf[23..39].
            //
            // SAFETY: `mod1e16<1e16` (remainder), and `U128_MAX_DEC_N - 16 + 16 == buf.len()`.
            enc16lsd(buf, mod1e16, U128_MAX_DEC_N - 16)
            // Take another 16 decimals.
            val (quot2, mod2) = divRem1e16(quot1e16)
            if (quot2 == 0) {
                Pair(mod2, U128_MAX_DEC_N - 16)
            } else {
                // Write digits at buf[7..23].
                //
                // SAFETY: `mod2<1e16` (remainder), and `U128_MAX_DEC_N - 32 + 16 <= buf.len()`.
                enc16lsd(buf, mod2, U128_MAX_DEC_N - 32)
                // Quot2 has at most 7 decimals remaining after two 1e16 divisions.
                Pair(quot2.toULong(), U128_MAX_DEC_N - 32)
            }
        }
    // Format per four digits from the lookup table.
    while (remain > 999) {
        // SAFETY: All of the decimals fit in buf due to U128_MAX_DEC_N
        // and the while condition ensures at least 4 more decimals.
        assertUnchecked(offset1 >= 4)
        // SAFETY: The offset counts down from its initial buf.len()
        // without underflow due to the previous precondition.
        assertUnchecked(offset1 <= buf.size)
        offset1 -= 4
        val quad = remain % 10000
        remain /= 10000
        // SAFETY: quad is a remainder modulo 10_000. The offset checks
        // above reserve exactly four bytes in buf.
        run {
            writeQuad(buf.getUncheckedMut(offset1 until offset1 + 4), quad)
        }
    }
    // Format per two digits from the lookup table.
    if (remain > 9) {
        // SAFETY: All of the decimals fit in buf due to U128_MAX_DEC_N
        // and the if condition ensures at least 2 more decimals.
        assertUnchecked(offset1 >= 2)
        // SAFETY: The offset counts down from its initial buf.len()
        // without underflow due to the previous precondition.
        assertUnchecked(offset1 <= buf.size)
        offset1 -= 2
        val pair = (remain % 100).toInt()
        remain /= 100
        buf[offset1 + 0].write(DECIMAL_PAIRS[pair * 2 + 0])
        buf[offset1 + 1].write(DECIMAL_PAIRS[pair * 2 + 1])
    }
    // Format the last remaining digit, if any.
    if (remain != 0) {
        // SAFETY: All of the decimals fit in buf due to U128_MAX_DEC_N
        // and the if condition ensures (at least) 1 more decimals.
        assertUnchecked(offset1 >= 1)
        // SAFETY: The offset counts down from its initial buf.len()
        // without underflow due to the previous precondition.
        assertUnchecked(offset1 <= buf.size)
        offset1 -= 1
        // Either the compiler sees that remain < 10, or it prevents
        // a boundary check up next.
        val last = (remain and 15).toInt()
        buf[offset1].write(DECIMAL_PAIRS[last * 2 + 1])
        // not used: remain = 0;
    }
    return offset1
}

/**
 * Formats this integer as an unsigned decimal number, using the memory pointed to by
 * `buf` as storage for the returned string slice.
 *
 * This method can be used to convert integers to strings without involving the
 * dynamic dispatch that using [`Display`][fmt.Display] would.
 * This may be more efficient in situations where [fmt] is not otherwise used.
 *
 * # Examples
 *
 * ```kotlin
 * import core.fmt.NumBuffer
 *
 * val n = U128(0x0uL, 0x0uL)
 * var buf = NumBuffer.new()
 * check(n.formatInto(buf) == "0")
 * val n1 = U128(0x0uL, 0x20uL)
 * var buf1 = NumBuffer.new()
 * check(n1.formatInto(buf1) == "32")
 * val n2 = U128.MAX_VALUE
 * var buf2 = NumBuffer.new()
 * check(n2.formatInto(buf2) == U128.MAX_VALUE.toString())
 * ```
 */
public fun U128.formatInto(buf: NumBuffer<U128>): String {
    val diff = buf.buf.len() - U128_MAX_DEC_N
    // Note: Once const generics are better, use `NumberBufferTrait.BUF_SIZE` as generic const
    // for `fmtU128Inner`.
    //
    // In the meantime, we have to use a slice starting at index 1 and add 1 to the returned
    // offset to ensure the number is correctly generated at the end of the buffer.
    // SAFETY: `diff` will always be between 0 and its initial value.
    return this.fmt(buf.buf.getUncheckedMut(diff until Int.MAX_VALUE))
}

/**
 * Formats this integer as a signed decimal number, using the memory pointed to by
 * `buf` as storage for the returned string slice.
 *
 * This method can be used to convert integers to strings without involving the
 * dynamic dispatch that using [`Display`][fmt.Display] would.
 * This may be more efficient in situations where [fmt] is not otherwise used.
 *
 * # Examples
 *
 * ```kotlin
 * import core.fmt.NumBuffer
 *
 * val n = I128(0x0L, 0x0uL)
 * var buf = NumBuffer.new()
 * check(n.formatInto(buf) == "0")
 * val n1 = I128.MIN_VALUE
 * check(n1.formatInto(buf) == I128.MIN_VALUE.toString())
 * val n2 = I128.MAX_VALUE
 * check(n2.formatInto(buf) == I128.MAX_VALUE.toString())
 * ```
 */
public fun I128.formatInto(buf: NumBuffer<I128>): String {
    val diff = buf.buf.len() - U128_MAX_DEC_N
    // Note: Once const generics are better, use `NumberBufferTrait.BUF_SIZE` as generic const
    // for `fmtU128Inner`.
    //
    // In the meantime, we have to use a slice starting at index 1 and add 1 to the returned
    // offset to ensure the number is correctly generated at the end of the buffer.
    var offset = this.unsignedAbs().fmtInner(buf.buf.getUncheckedMut(diff until Int.MAX_VALUE))
    // We put back the offset at the right position.
    offset += diff
    // Only difference between signed and unsigned are these 4 lines.
    if (this < I128(0x0L, 0x0uL)) {
        offset -= 1
        // SAFETY: `buf` will always be big enough to contain all digits plus the minus sign.
        run {
            buf.buf.getUncheckedMut(offset).write(45u)
        }
    }
    // SAFETY: Starting from `offset`, all elements of the slice have been set.
    return sliceBufferToStr(buf.buf, offset)
}

/**
 * Writes `quad` as exactly four digits (for example: `42` becomes `"0042"`).
 *
 * # Safety
 *
 * `quad` must be below 10_000 and `buf` must contain exactly four bytes.
 */
public fun writeQuad(buf: MutableList<MaybeUninit<UByte>>, quad: ULong) {
    // SAFETY: These are this function's caller-provided invariants.
    run {
        assertUnchecked(quad < 10000uL)
        assertUnchecked(buf.size == 4)
    }
    val quad1 = quad.toUInt()
    // Note: this is equivalent to `quad / 100`, but contains no division instructions.
    val high = (quad1 * (1u shl 19) / 100u + 1u) shr 19
    val low = quad1 - high * 100u
    val high1 = high.toInt()
    val low1 = low.toInt()
    // SAFETY: `high` and `low` are below 100 because `quad` is below 10_000.
    assertUnchecked(high1 < 100 && low1 < 100)
    ((buf).slice(0 until 2)).writeCopyOfSlice(((DECIMAL_PAIRS).sliceArray(high1 * 2 until high1 * 2 + 2)))
    ((buf).slice(2 until 4)).writeCopyOfSlice(((DECIMAL_PAIRS).sliceArray(low1 * 2 until low1 * 2 + 2)))
}

/**
 * Encodes the 16 least-significant decimals of n into `buf[OFFSET .. OFFSET +
 * 16 ]`.
 *
 * # Safety
 *
 * `n` must be below 1e16, and `buf` must be at least `OFFSET + 16` bytes long.
 */
public fun enc16lsd(buf: MutableList<MaybeUninit<UByte>>, n: ULong, offset: Int) {
    // SAFETY: Every caller passes a remainder produced by division by 10^16,
    // and every used `OFFSET` specialization reserves sixteen bytes in `buf`.
    run {
        assertUnchecked(n < 10000000000000000uL)
        assertUnchecked(offset + 16 <= buf.size)
    }
    // Peel four digits at a time from right to left (12345678 -> 1234 | 5678).
    // Since 10_000 is constant, LLVM replaces each division with multiply or shift.
    var remain = n
    for (quadIndex in (1 until 4).rev()) {
        val quad = remain % 10000uL
        remain /= 10000uL
        // SAFETY: `OFFSET + quadIndex * 4` starts one of the four
        // non-overlapping four-byte regions proven in bounds above.
        run {
            writeQuad(buf.getUncheckedMut(offset + quadIndex * 4 until offset + (quadIndex + 1) * 4), quad)
        }
    }
    // SAFETY: OFFSET starts the first four-byte region proven in bounds above.
    run {
        writeQuad(buf.getUncheckedMut(offset until offset + 4), remain)
    }
}

/**
 * Euclidean division plus remainder with constant 1E16 basically consumes 16
 * decimals from n.
 *
 * The integer division algorithm is based on the following paper:
 *
 *   T. Granlund and P. Montgomery, “Division by Invariant Integers Using Multiplication”
 *   in Proc. of the SIGPLAN94 Conference on Programming Language Design and
 *   Implementation, 1994, pp. 61–72
 */
public fun divRem1e16(n: U128): Pair<U128, ULong> {
    val D: U128 = U128(0x0uL, 0x2386f26fc10000uL)
    // These constant values are computed with the CHOOSE_MULTIPLIER procedure
    // from the Granlund & Montgomery paper, using N=128, prec=128 and d=1E16.
    val M_HIGH: U128 = U128(0x39a5652fb1137856uL, 0xd30baf9a1e626a6duL)
    val SH_POST: UByte = 51u
    // The check inlines well with the caller flow.
    if (n < D) {
        return Pair(0, n.toULong())
    }
    val quot = n.carryingMul(M_HIGH, 0).second shr SH_POST.toInt()
    val rem = n - quot * D
    return Pair(quot, rem.toULong())
}
