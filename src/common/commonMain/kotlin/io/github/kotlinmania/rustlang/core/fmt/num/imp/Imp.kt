// port-lint: source fmt/num.rs
package io.github.kotlinmania.rustlang.core.fmt.num.imp

import io.github.kotlinmania.rustlang.core.char.methods.isAsciiAlphabetic
import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.fmt.num.DECIMAL_PAIRS
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.hint.unreachableUnchecked
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.checkedIlog10
import io.github.kotlinmania.rustlang.core.num.checkedPow
import io.github.kotlinmania.rustlang.core.num.ilog10
import io.github.kotlinmania.rustlang.core.num.imp.fmt.Formatted
import io.github.kotlinmania.rustlang.core.num.imp.fmt.Part
import io.github.kotlinmania.rustlang.core.num.pow
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import kotlin.experimental.and

public fun expU64(f: StringBuilder, n: ULong, isNonnegative: Boolean, letterE: UByte): Result<Unit, Error> {
    val MAX_DEC_N: Int = ULong.MAX_VALUE.ilog10().toInt() + 1
    val MAX_COEF_LEN: Int = MAX_DEC_N + ".".len()
    val MAX_TEXT_LEN: Int = MAX_COEF_LEN + "e99".len()
    if (false) {
        check(letterE.isAsciiAlphabetic()) { "single-byte character" }
    }
    var exp = n.checkedIlog10().unwrapOr(0).toInt()
    if (false) {
        check(n / (10.toULong()).pow(exp.toUInt()) < 10uL)
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
                    val scale = (10.toULong()).checkedPow(lessPrec.toUInt()).unwrapUnchecked()
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
                            when (val matchVal1 = Pair(coef, (10.toULong()).pow(coefPrec.toUInt() + 1u))) {
                                else -> {
                                    val leftVal = matchVal1.first
                                    val rightVal = matchVal1.second
                                    if (!(leftVal == rightVal)) {
                                        val kind = AssertKind.Eq
                                        assertFailed(kind, leftVal, rightVal, Option.None())
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
            buf[1].write(46u.toUByte())
            val fractionRange = 2 until (2 + coefPrec)
            var remain = coef
            for (i in fractionRange.clone().rev()) {
                val digit = (remain % 10uL).toInt()
                remain /= 10uL
                buf[i].write(48u.toUByte() + digit.toUByte())
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
                    buf[fractionRange.start].write(48u.toUByte() + digit1.toUByte())
                }
            }
            Pair(remain, fractionRange.end)
        }
    if (false) {
        check(leadDec < 10uL)
    }
    if (false) {
        check(leadDec != 0uL || coef == 0uL) { "significant digits only" }
    }
    buf[0].write(48u.toUByte() + leadDec.toUByte())
    assertUnchecked(coefLen <= MAX_COEF_LEN)
    buf[coefLen].write(letterE)
    val textLen: Int =
        when {
            exp < 10 -> {
                buf[coefLen + 1].write(48u.toUByte() + exp.toUByte())
                coefLen + 2
            }
            exp in 10 until 100 -> {
                run {
                    buf[coefLen + 1].write(48u.toUByte() + (exp / 10).toUByte())
                    buf[coefLen + 2].write(48u.toUByte() + (exp % 10).toUByte())
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

public fun Byte.fmt(f: StringBuilder): Result<Unit, Unit> = expU64(f, this.unsignedAbs().toULong(), this >= 0.toByte(), 101u.toUByte())

public fun UByte.fmt(f: StringBuilder): Result<Unit, Unit> = expU64(f, this.toULong(), true, 101u.toUByte())

public fun Short.fmt(f: StringBuilder): Result<Unit, Unit> = expU64(f, this.unsignedAbs().toULong(), this >= 0.toShort(), 101u.toUByte())

public fun UShort.fmt(f: StringBuilder): Result<Unit, Unit> = expU64(f, this.toULong(), true, 101u.toUByte())

public fun Int.fmt(f: StringBuilder): Result<Unit, Unit> = expU64(f, this.unsignedAbs().toULong(), this >= 0, 101u.toUByte())

public fun UInt.fmt(f: StringBuilder): Result<Unit, Unit> = expU64(f, this.toULong(), true, 101u.toUByte())

public fun Long.fmt(f: StringBuilder): Result<Unit, Unit> = expU64(f, this.unsignedAbs().toULong(), this >= 0L, 101u.toUByte())

public fun ULong.fmt(f: StringBuilder): Result<Unit, Unit> = expU64(f, this, true, 101u.toUByte())
