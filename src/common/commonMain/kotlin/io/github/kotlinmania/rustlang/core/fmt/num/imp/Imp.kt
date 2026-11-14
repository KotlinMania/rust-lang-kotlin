// port-lint: source fmt/num.rs
package io.github.kotlinmania.rustlang.core.fmt.num.imp

import io.github.kotlinmania.numfmt.Formatted
import io.github.kotlinmania.numfmt.Part
import io.github.kotlinmania.rustlang.core.char.isAsciiAlphabetic
import io.github.kotlinmania.rustlang.core.fmt.Formatter
import io.github.kotlinmania.rustlang.core.fmt.num.DECIMAL_PAIRS
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.rustCheckedPow
import io.github.kotlinmania.rustlang.core.num.rustPow
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr

public fun expU64(f: Formatter, n: ULong, isNonnegative: Boolean, letterE: UByte): Result {
    val MAX_DEC_N: Int = ULong.MAX_VALUE.ilog10().toInt() + 1
    val MAX_COEF_LEN: Int = MAX_DEC_N + ".".len()
    val MAX_TEXT_LEN: Int = MAX_COEF_LEN + "e99".len()
    if (false) {
        check(letterE.isAsciiAlphabetic()) { "single-byte character" }
    }
    var exp = n.checkedIlog10().unwrapOr(0).toInt()
    if (false) {
        check(n / rustPow((10.toULong()), exp.toUInt()) < 10uL)
    }
    var coefPrec = exp
    var coef = n
    val morePrec =
        run {
            val matchVal = f.precision()
            when {
                matchVal == null -> {
                    while (coefPrec != 0 && coef % 10uL == 0uL) {
                        coef /= 10uL
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
                    val scale = rustCheckedPow((10.toULong()), lessPrec.toUInt()).unwrapUnchecked()
                    val floor = coef / scale
                    val over = coef % scale
                    val half = scale / 2
                    val roundUp =
                        if (over < half) {
                            0
                        } else if (over > half) {
                            1
                        } else {
                            floor and 1uL
                        }
                    coef = floor + roundUp
                    coefPrec = fmtPrec1
                    if (roundUp != 0 && coef.checkedIlog10().unwrapOr(0).toInt() > coefPrec) {
                        if (false) {
                            when (val matchVal1 = Pair(coef, rustPow((10.toULong()), coefPrec.toUInt() + 1u))) {
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
                        coef /= 10uL
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
                val digit = (remain % 10uL).toInt()
                remain /= 10uL
                buf[i].write(48u + digit.toUByte())
            }
            run {
                for (i1 in fractionRange.clone().skip(1).rev().stepBy(2)) {
                    val pair = (remain % 100uL).toInt()
                    remain /= 100uL
                    buf[i1 - 1].write(DECIMAL_PAIRS[pair * 2 + 0])
                    buf[i1 - 0].write(DECIMAL_PAIRS[pair * 2 + 1])
                }
                if (coefPrec and 1 != 0) {
                    val digit1 = (remain % 10uL).toInt()
                    remain /= 10uL
                    buf[fractionRange.start].write(48u + digit1.toUByte())
                }
            }
            Pair(remain, fractionRange.end)
        }
    if (false) {
        check(leadDec < 10)
    }
    if (false) {
        check(leadDec != 0 || coef == 0uL) { "significant digits only" }
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
                check(ULong.MAX_VALUE.ilog10() < 100)
                unreachableUnchecked()
            }
        }
    val text = buf[0 until textLen].assumeInitRef()
    return if (morePrec == 0) {
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

public fun Byte.fmt(f: Formatter): Result<Unit, Unit> = expU64(f, this.unsignedAbs().toULong(), this >= 0.toByte(), 101u)

public fun UByte.fmt(f: Formatter): Result<Unit, Unit> = expU64(f, this.toULong(), true, 101u)

public fun Short.fmt(f: Formatter): Result<Unit, Unit> = expU64(f, this.unsignedAbs().toULong(), this >= 0.toShort(), 101u)

public fun UShort.fmt(f: Formatter): Result<Unit, Unit> = expU64(f, this.toULong(), true, 101u)

public fun Int.fmt(f: Formatter): Result<Unit, Unit> = expU64(f, this.unsignedAbs().toULong(), this >= 0, 101u)

public fun UInt.fmt(f: Formatter): Result<Unit, Unit> = expU64(f, this.toULong(), true, 101u)

public fun Long.fmt(f: Formatter): Result<Unit, Unit> = expU64(f, this.unsignedAbs().toULong(), this >= 0L, 101u)

public fun ULong.fmt(f: Formatter): Result<Unit, Unit> = expU64(f, this, true, 101u)
