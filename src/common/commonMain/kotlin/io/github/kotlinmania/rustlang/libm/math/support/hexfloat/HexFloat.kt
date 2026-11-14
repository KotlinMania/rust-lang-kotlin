// port-lint: source math/support/hex_float.rs
package io.github.kotlinmania.rustlang.libm.math.support.hexfloat

import io.github.kotlinmania.rustlang.core.F128
import io.github.kotlinmania.rustlang.core.F16
import io.github.kotlinmania.rustlang.core.I128
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.num.rustSaturatingMul
import io.github.kotlinmania.rustlang.core.num.rustTrailingZeros
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libm.math.exp.exp
import io.github.kotlinmania.rustlang.libm.math.exp2m1.T1
import io.github.kotlinmania.rustlang.libm.math.exp2m1.T2
import io.github.kotlinmania.rustlang.libm.math.lgammar.T3
import io.github.kotlinmania.rustlang.libm.math.support.env.Round
import io.github.kotlinmania.rustlang.libm.math.support.env.Status
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.Float
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.f32FromBits
import io.github.kotlinmania.rustlang.libm.math.support.floattraits.f64FromBits
import io.github.kotlinmania.rustlang.libm.math.support.macros.cfgSelectNofmt

// Utilities for working with hex float formats.

/**
 * Construct a 16-bit float from hex float representation (C-style)
 */
public fun hf16(s: String): F16 =
    run {
        val matchVal = parseHexExact(s, 16u, 10u)
        when {
            matchVal is Result.Ok<*> -> {
                val bits = (matchVal as Result.Ok<*>).value
                F16.fromBits(bits.toUShort())
            }
            else -> error("$s")
        }
    }

/**
 * Construct a 32-bit float from hex float representation (C-style)
 */
public fun hf32(s: String): kotlin.Float =
    run {
        val matchVal = parseHexExact(s, 32u, 23u)
        when {
            matchVal is Result.Ok<*> -> {
                val bits = (matchVal as Result.Ok<*>).value
                f32FromBits(bits.toUInt())
            }
            else -> error("$s")
        }
    }

/**
 * Construct a 64-bit float from hex float representation (C-style)
 */
public fun hf64(s: String): Double =
    run {
        val matchVal = parseHexExact(s, 64u, 52u)
        when {
            matchVal is Result.Ok<*> -> {
                val bits = (matchVal as Result.Ok<*>).value
                f64FromBits(bits.toULong())
            }
            else -> error("$s")
        }
    }

/**
 * Construct a 128-bit float from hex float representation (C-style)
 */
public fun hf128(s: String): F128 =
    run {
        val matchVal = parseHexExact(s, 128u, 112u)
        when {
            matchVal is Result.Ok<*> -> {
                val bits = (matchVal as Result.Ok<*>).value
                F128.fromBits(bits)
            }
            else -> error("$s")
        }
    }

public open class HexFloatParseError(
    public var value: String,
) {
    public var field0: String
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): HexFloatParseError = HexFloatParseError(value)
}

/**
 * Parses any float to its bitwise representation, returning an error if it cannot be represented exactly
 */
public fun parseHexExact(s: String, bits: UInt, sigBits: UInt): Result<U128, HexFloatParseError> =
    run {
        val matchVal = parseAny(s, bits, sigBits, Round.Nearest)
        when {
            matchVal is Result.Err<*> -> {
                val e = (matchVal as Result.Err<*>).error
                Result.Err(e)
            }
            matchVal is Result.Ok<*> && matchVal.value.second == Status.OK -> Result.Ok(bits)
            matchVal is Result.Ok<*> && (matchVal as Result.Ok<*>).value.second.overflow() -> Result.Err(HexFloatParseError("the value is too huge"))
            matchVal is Result.Ok<*> && (matchVal as Result.Ok<*>).value.second.underflow() -> Result.Err(HexFloatParseError("the value is too tiny"))
            matchVal is Result.Ok<*> && (matchVal as Result.Ok<*>).value.second.inexact() -> Result.Err(HexFloatParseError("the value is too precise"))
            else -> error("unreachable")
        }
    }

/**
 * Parse any float from hex to its bitwise representation.
 */
public fun parseAny(s: String, bits: UInt, sigBits: UInt, round: Round): Result<Pair<U128, Status>, HexFloatParseError> {
    var b = s.encodeToByteArray().map { it.toUByte() }
    if (sigBits > 119u || bits > 128u || bits < sigBits + 3u || bits > sigBits + 30u) {
        return Result.Err(HexFloatParseError("unsupported target float configuration"))
    }
    val neg =
        when {
            b.size >= 1 && b[0] == 45u -> true
            else -> false
        }
    if (b != null) {
        val rest = b.subList(1, b.size)
        b = rest
    }
    val signBit = 1 shl (bits - 1u).toInt()
    val quietBit = 1 shl (sigBits - 1u).toInt()
    val nan = signBit - quietBit
    val inf = nan - quietBit
    var (x, status) =
        when {
            b.size == 3 && (b[0] == 105u || b[0] == 73u) && (b[1] == 110u || b[1] == 78u) && (b[2] == 102u || b[2] == 70u) -> Pair(inf, Status.OK)
            b.size == 3 && (b[0] == 110u || b[0] == 78u) && (b[1] == 97u || b[1] == 65u) && (b[2] == 110u || b[2] == 78u) -> Pair(nan, Status.OK)
            b.size >= 2 && b[0] == 48u && (b[1] == 120u || b[1] == 88u) -> {
                val rest1 = b.subList(2, b.size)
                val round1 =
                    run {
                        val matchVal = Pair(neg, round)
                        when {
                            // parse("-x", Round.Positive) == -parse("x", Round.Negative)
                            matchVal.first == true && matchVal.second == Round.Positive -> Round.Negative
                            matchVal.first == true && matchVal.second == Round.Negative -> Round.Positive
                            // rounding toward nearest or zero are symmetric
                            matchVal.first == true && (matchVal.second == Round.Nearest || matchVal.second == Round.Zero) -> round
                            else -> round
                        }
                    }
                run {
                    val matchVal1 = parseFinite(rest1, bits, sigBits, round1)
                    when (matchVal1) { is Result.Ok<*> -> {
                        val res = matchVal1.value
                        res
                    }; is Result.Err<*> -> {
                        val e = matchVal1.error
                        return Result.Err(e)
                    } }
                }
            }
            else -> { return Result.Err(HexFloatParseError("no hex indicator")) }
        }
    if (neg) {
        x = x xor signBit
    }
    return Result.Ok(Pair(x, status))
}

public fun parseFinite(b: List<UByte>, bits: UInt, sigBits: UInt, roundingMode: Round): Result<Pair<U128, Status>, HexFloatParseError> {
    val expBits: UInt = bits - sigBits - 1u
    val maxMsb: Int = (1 shl (expBits - 1u).toInt()) - 1
    // The exponent of one ULP in the subnormals
    val minLsb: Int = 1 - maxMsb - sigBits.toInt()
    var (sig, exp) =
        run {
            val matchVal = parseHex(b)
            when {
                matchVal is Result.Err<*> -> {
                    val e = (matchVal as Result.Err<*>).error
                    return Result.Err(e)
                }
                matchVal is Result.Ok<*> && matchVal.value.sig == 0 -> { return Result.Ok(Pair(0, Status.OK)) }
                else -> Pair(sig, exp)
            }
        }
    var roundBits = sig.ilog2().toInt() - sigBits.toInt()
    // Round at least up to minLsb
    if (exp < minLsb - roundBits) {
        roundBits = minLsb - exp
    }
    var status = Status.OK
    exp += roundBits
    if (roundBits > 0) {
        // first, prepare for rounding exactly two bits
        if (roundBits == 1) {
            sig = sig shl 1
        } else if (roundBits > 2) {
            sig = shrOddRounding(sig, (roundBits - 2).toUInt())
        }
        if (sig and 0x3 != 0) {
            status = Status.INEXACT
        }
        sig = shr2Round(sig, roundingMode)
    } else if (roundBits < 0) {
        sig = sig shl -roundBits
    }
    // The parsed value is X = sig * 2^exp
    // Expressed as a multiple U of the smallest subnormal value:
    // X = U * 2^minLsb, so U = sig * 2^(exp-minLsb)
    val uexp = U128.fromInt((exp - minLsb))
    val uexp1 = uexp shl sigBits.toInt()
    // Note that it is possible for the exponent bits to equal 2 here
    // if the value rounded up, but that means the mantissa is all zeroes
    // so the value is still correct
    check(sig <= 2 shl sigBits.toInt())
    val inf = ((1 shl expBits.toInt()) - 1) shl sigBits.toInt()
    val bits2 =
        run {
            val matchVal1 = sig.checkedAdd(uexp1)
            when {
                matchVal1 != null && bits < inf -> {
                    val bits1 = matchVal1
                    // inexact subnormal or zero?
                    if (status.inexact() && bits1 < (1 shl sigBits.toInt())) {
                        status = status.with(Status.UNDERFLOW)
                    }
                    bits1
                }
                else -> {
                    // overflow to infinity
                    status = status.with(Status.OVERFLOW).with(Status.INEXACT)
                    when (roundingMode) {
                        Round.Positive, Round.Nearest -> inf
                        Round.Negative, Round.Zero -> inf - 1
                    }
                }
            }
        }
    return Result.Ok(Pair(bits2, status))
}

/**
 * Shift right, rounding all inexact divisions to the nearest odd number
 * E.g. (0 >> 4) -> 0, (1..=31 >> 4) -> 1, (32 >> 4) -> 2, ...
 *
 * Useful for reducing a number before rounding the last two bits, since
 * the result of the final rounding is preserved for all rounding modes.
 */
public fun shrOddRounding(x: U128, k: UInt): U128 =
    if (k < 128u) {
        val inexact = rustTrailingZeros(x) < k
        (x shr k.toInt()) or (U128.fromLong(inexact.toLong()))
    } else {
        (if (x != U128(0x0uL, 0x0uL)) U128.ONE else U128.ZERO)
    }

/**
 * Divide by 4, rounding with the given mode
 */
public fun shr2Round(x: U128, round: Round): U128 {
    var x1 = x
    val t = (x1.toUInt()) and 0x7u
    x1 = x1 shr 2
    return when (round) {
        // Look-up-table on the last three bits for when to round up
        Round.Nearest -> x1 + U128.fromUByte(((((0xC8u.toUByte()).toUInt() shr (t.toInt())).toUByte()) and 1u.toUByte()))
        Round.Negative -> x1
        Round.Zero -> x1
        Round.Positive -> x1 + (if (t and 0x3u != 0u) U128.ONE else U128.ZERO)
    }
}

/**
 * A parsed finite and unsigned floating point number.
 */
public open class Parsed(
    /**
     * Absolute value sig * 2^exp
     */
    public var sig: U128,
    public var exp: Int,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Parsed> =
            object : TypeLayout<Parsed> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): Parsed =
                    Parsed(
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readNative(addr),
                        (PrimitiveLayouts.I32).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: Parsed) {
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeNative(addr, value.sig)
                    (PrimitiveLayouts.I32).writeNative(addr + 16L, value.exp)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Parsed =
                    Parsed(
                        (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readBytes(buf, offset),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Parsed) {
                    (PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeBytes(buf, offset, value.sig)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 16, value.exp)
                }
            }
    }
}

/**
 * Parse a hexadecimal float x
 */
public fun parseHex(b: List<UByte>): Result<Parsed, HexFloatParseError> {
    var b1 = b
    var sig: U128 = U128(0x0uL, 0x0uL)
    var exp: Int = 0
    var seenPoint = false
    var someDigits = false
    var inexact = false
    while (b1 != null) {
        val c = b1[0]
        val rest = b1.subList(1, b1.size)
        b1 = rest
        when (c) {
            46u -> {
                if (seenPoint) {
                    return Result.Err(HexFloatParseError("unexpected '.' parsing fractional digits"))
                }
                seenPoint = true
                continue
            }
            112u, 80u -> { break }
            else -> {
                val c1 = c
                val digit =
                    when (val matchVal = hexDigit(c1)) {
                        null -> { return Result.Err(HexFloatParseError("expected hexadecimal digit")) }
                        else -> {
                            val d = matchVal
                            d
                        }
                    }
                someDigits = true
                if ((sig shr 124) == U128(0x0uL, 0x0uL)) {
                    sig = sig shl 4
                    sig = sig or (U128.fromLong(digit.toLong()))
                } else {
                    // Note: it is technically possible for exp to overflow if parsing a string with >500M digits
                    exp += 4
                    inexact = inexact or (digit != 0)
                }
                // Up until the fractional point, the value grows
                // with more digits, but after it the exponent is
                // compensated to match.
                if (seenPoint) {
                    exp -= 4
                }
            }
        }
    }
    // If we've set inexact, the exact value has more than 125
    // significant bits, and lies somewhere between sig and sig + 1.
    // Because we'll round off at least two of the trailing bits,
    // setting the last bit gives correct rounding for inexact values.
    sig = sig or (U128.fromLong(inexact.toLong()))
    if (!someDigits) {
        return Result.Err(HexFloatParseError("at least one digit is required"))
    }
    someDigits = false
    val negateExp =
        when {
            b1.size >= 1 && b1[0] == 45u -> true
            else -> false
        }
    if (b1 != null) {
        val rest1 = b1.subList(1, b1.size)
        b1 = rest1
    }
    var pexp: UInt = 0u
    while (b1 != null) {
        val c2 = b1[0]
        val rest2 = b1.subList(1, b1.size)
        b1 = rest2
        val digit1 =
            when (val matchVal1 = decDigit(c2)) {
                null -> { return Result.Err(HexFloatParseError("expected decimal digit")) }
                else -> {
                    val d1 = matchVal1
                    d1
                }
            }
        someDigits = true
        pexp = rustSaturatingMul(pexp, 10u)
        pexp += digit1.toUInt()
    }
    if (!someDigits) {
        return Result.Err(HexFloatParseError("at least one exponent digit is required"))
    }
    if (negateExp) {
        exp = exp.saturatingSubUnsigned(pexp)
    } else {
        exp = exp.saturatingAddUnsigned(pexp)
    }
    return Result.Ok(Parsed(sig = sig, exp = exp))
}

public fun decDigit(c: UByte): UByte? =
    when (c) {
        in 48u..57u -> (c - 48u).toUByte()
        else -> null
    }

public fun hexDigit(c: UByte): UByte? =
    when (c) {
        in 48u..57u -> { (c - 48u).toUByte() }
        in 97u..102u -> { (c - 97u + 10u).toUByte() }
        in 65u..70u -> (c - 65u + 10u).toUByte()
        else -> null
    }

// Adapted from https://github.com/ericseppanen/hexfloat2/blob/a5c27932f0ff/src/format.rs
public fun <Int, SignedInt, F : Float<F, Any?, Any?>> fmtAnyHex(x: F, f: StringBuilder) {
    if (x.isSignNegative()) {
        f.append("-")
    } else {
        f.append("+")
    }
    if (x.isNan()) {
        if (x.isSnan()) {
            f.append("sNaN")
        } else if (x.isNan()) {
            f.append("qNaN")
        }
        val payload = x.frac() and F.SIG_TOP_BIT.inv()
        f.append("($payload)")
        return Unit
    }
    if (x.isInfinite()) {
        return f.append("inf")
    } else if (x == F.ZERO) {
        return f.append("0x0p+0")
    }
    var exponent = x.expUnbiased()
    val sig = x.toBits() and F.SIG_MASK
    val bias = F.EXP_BIAS.toInt()
    // The mantissa MSB needs to be shifted up to the nearest nibble.
    // The width is rounded up to the nearest char (4 bits)
    val leading =
        if (exponent == -bias) {
            // subnormal number means we shift our output by 1 bit.
            exponent += 1
            "0."
        } else {
            "1."
        }
    f.append("0x$leading${sig}p$exponent")
}

/**
 * Types that can be formatted as hex via `Hex`. For ints we always print with a fixed
 * number of leading zeros. For floats we use the IEEE hex (`% a`) representation. The `-`
 * format modifier is used to print the integer hex representation rather than hex float.
 */
public interface DisplayHex {
    public fun fmt(f: StringBuilder)
}

// Only used for tests and public test internals

/**
 * A wrapper implementing formatting traits via `DisplayHex`.
 */
public open class Hex<T>(
    public var value: T,
) {
    public var field0: T
        get() = value
        set(v) {
            value = v
        }

    public open fun fmt(f: StringBuilder) {
        this.value.fmt(f)
    }

    public open fun fmtDisplay(f: StringBuilder) {
        this.value.fmt(f)
    }

    public open fun fmtLowerHex(f: StringBuilder) {
        this.value.fmt(f)
    }

    public companion object {
        public fun <T> typeLayout(typeLayoutT: TypeLayout<T>): TypeLayout<Hex<T>> =
            object : TypeLayout<Hex<T>> {
                override val alignBytes: Long = listOf(1L, typeLayoutT.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutT.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Hex<T> =
                    Hex(
                        (typeLayoutT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Hex<T>) {
                    (typeLayoutT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Hex<T> =
                    Hex(
                        (typeLayoutT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Hex<T>) {
                    (typeLayoutT).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public fun <Int, SignedInt, F : Float<F, Any?, Any?>> F.fmt(f: StringBuilder) {
    if (f.signMinus()) {
        this.toBits().fmt(f)
    } else {
        fmtAnyHex(this, f)
    }
}

public fun Byte.fmt(f: StringBuilder) {
    f.append("$this")
}

public fun Short.fmt(f: StringBuilder) {
    f.append("$this")
}

public fun Int.fmt(f: StringBuilder) {
    f.append("$this")
}

public fun Long.fmt(f: StringBuilder) {
    f.append("$this")
}

public fun I128.fmt(f: StringBuilder) {
    f.append("$this")
}

public fun UByte.fmt(f: StringBuilder) {
    f.append("$this")
}

public fun UShort.fmt(f: StringBuilder) {
    f.append("$this")
}

public fun UInt.fmt(f: StringBuilder) {
    f.append("$this")
}

public fun ULong.fmt(f: StringBuilder) {
    f.append("$this")
}

public fun U128.fmt(f: StringBuilder) {
    f.append("$this")
}

// Not really a meaningful impl, but makes some generics easier.
public fun Boolean.fmt(f: StringBuilder) {
    cfgSelectNofmt(feature, "compiler-builtins", f, unsupportedBranch, write, f, "{self}")
}

public fun <T1, T2> Pair<T1, T2>.fmt(f: StringBuilder) where T1 : DisplayHex, T2 : DisplayHex {
    f.append("(")
    this.value.fmt(f)
    f.append(", ")
    this.field1.fmt(f)
    f.append(")")
}

public fun <T1, T2, T3> Triple<T1, T2, T3>.fmt(f: StringBuilder) where T1 : DisplayHex, T2 : DisplayHex, T3 : DisplayHex {
    f.append("(")
    this.value.fmt(f)
    f.append(", ")
    this.field1.fmt(f)
    f.append(", ")
    this.field2.fmt(f)
    f.append(")")
}
