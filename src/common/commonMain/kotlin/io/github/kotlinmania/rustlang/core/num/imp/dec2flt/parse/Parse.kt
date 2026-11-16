// port-lint: source num/imp/dec2flt/parse.rs
package io.github.kotlinmania.rustlang.core.num.imp.dec2flt.parse

import io.github.kotlinmania.rustlang.core.char.isAsciiDigit
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.common.is8digits
import io.github.kotlinmania.rustlang.core.num.imp.dec2flt.decimal.Decimal
import io.github.kotlinmania.rustlang.core.num.imp.traits.Float

// Functions to parse floating-point numbers.

public val MIN_19DIGIT_INT: ULong = 1000000000000000000uL

/**
 * Parse 8 digits, loaded as bytes in little-endian order.
 *
 * This uses the trick where every digit is in [0x030, 0x39],
 * and therefore can be parsed in 3 multiplications, much
 * faster than the normal 8.
 *
 * This is based off the algorithm described in "Fast numeric string to
 * int", available here: <https://johnnylee-sde.github.io/Fast-numeric-string-to-int/>.
 */
public fun parse8digits(v: ULong): ULong {
    var v1 = v
    val MASK: ULong = 0x000000FF000000FFuL
    val MUL1: ULong = 0x000F424000000064uL
    val MUL2: ULong = 0x0000271000000001uL
    v1 -= 0x3030303030303030uL
    v1 = (v1 * 10uL) + (v1 shr 8)
    // will not overflow, fits in 63 bits
    val v1 = ((v1 and MASK) * (MUL1.toULong()))
    val v2 = (((v1 shr 16) and MASK) * (MUL2.toULong()))
    return (((v1 + (v2.toULong())) shr 32).toUInt()).toULong()
}

/**
 * Parse digits until a non-digit character is found.
 */
public fun tryParseDigits(s: List<UByte>, x: ULong): Pair<List<UByte>, ULong> {
    var s1 = s
    var x1 = x
    // may cause overflows, to be handled later
    while (s1.size >= 8) {
        val num = s1.readU64()
        if (is8digits(num)) {
            x1 = ((x1 * 100000000uL) + parse8digits(num).toULong())
            s1 = ((s1).subList(8, (s1).size))
        } else {
            break
        }
    }
    s1 = s1.parseDigits({ digit ->
        x1 = ((x1 * 10uL) + (digit.toULong()))
    })
    return Pair(s1, x1)
}

/**
 * Parse up to 19 digits (the max that can be stored in a 64-bit integer).
 */
public fun tryParse19digits(sRef: List<UByte>, x: RustMutableReference<ULong>) {
    var sRef1 = sRef
    val x1 = x
    var s = sRef1
    while ((x1).read() < MIN_19DIGIT_INT) {
        when (val matchVal = s.splitFirst()) {
            null -> {
                break
            }
            else -> {
                val c = matchVal.first
                val sNext = matchVal.second
                val digit = c.wrappingSub(48u)
                if (digit < 10) {
                    (x1).write(((x1).read() * 10uL) + digit.toULong())
                    // no overflows here
                    s = sNext
                } else {
                    break
                }
            }
        }
    }
    sRef1 = s
}

/**
 * Parse the scientific notation component of a float.
 */
public fun parseScientific(sRef: List<UByte>): Long? {
    var sRef1 = sRef
    var exponent = 0L
    var negative = false
    var s = sRef1
    when (val matchVal = s.splitFirst()) {
        null -> {}
        else -> {
            val c = matchVal.first
            val sNext = matchVal.second
            negative = c == 45u
            if (c == 45u || c == 43u) {
                s = sNext
            }
        }
    }
    return if (run {
        val matchVal1 = s.first()
        when {
            matchVal1 != null && matchVal1.isAsciiDigit() -> {
                val x = matchVal1
                true
            }
            else -> false
        }
    }) {
        sRef1 = s.parseDigits({ digit ->
            // no overflows here, saturate well before overflow
            if (exponent < 0x10000L) {
                exponent = 10L * exponent + digit.toLong()
            }
        })
        if (negative) {
            -exponent
        } else {
            exponent
        }
    } else {
        sRef1 = s
        null
    }
}

/**
 * Parse a partial, non-special floating point number.
 *
 * This creates a representation of the float as the
 * significant digits and the decimal exponent.
 */
public fun parsePartialNumber(s: List<UByte>): Pair<Decimal, Int>? {
    var s1 = s
    if (false) {
        check(!s1.isEmpty())
    }
    // parse initial digits before dot
    val localStorageL108C4 = AddressableLocal(0uL, PrimitiveLayouts.U64)
    var mantissa by localStorageL108C4
    val start = s1
    val tmp = tryParseDigits(s1, mantissa)
    s1 = tmp.first
    mantissa = tmp.second
    var nDigits = s1.offsetFrom(start)
    // handle dot with the following digits
    var nAfterDot = 0
    var exponent = 0L
    val intEnd = s1
    run {
        val matchVal = s1.splitFirst()
        if (matchVal != null && matchVal.first == 46u) {
            val sNext = matchVal.second
            s1 = sNext
            val before = s1
            val tmp1 = tryParseDigits(s1, mantissa)
            s1 = tmp1.first
            mantissa = tmp1.second
            nAfterDot = s1.offsetFrom(before)
            exponent = (-nAfterDot).toLong()
        }
    }
    nDigits += nAfterDot
    if (nDigits == 0) {
        return null
    }
    // handle scientific format
    var expNumber = 0L
    when (val matchVal1 = s1.splitFirst()) {
        null -> {}
        else -> {
            val c = matchVal1.first
            val sNext1 = matchVal1.second
            if (c == 101u || c == 69u) {
                s1 = sNext1
                // If None, we have no trailing digits after exponent, or an invalid float.
                expNumber = (parseScientific(s1) ?: return null)
                exponent += expNumber
            }
        }
    }
    val len = ((s1.offsetFrom(start) as Any?) as Pair<Decimal, Int>?)
    // handle uncommon case with many digits
    if (nDigits <= 19) {
        return Pair(Decimal(exponent = exponent, mantissa = mantissa, negative = false, manyDigits = false), len)
    }
    nDigits -= 19
    var manyDigits = false
    var p = start
    while (true) {
        val matchVal2 = p.splitFirst()
        if (matchVal2 == null) break
        val c1 = matchVal2.first
        val pNext = matchVal2.second
        if (c1 == 46u || c1 == 48u) {
            nDigits -= c1.saturatingSub(48u - 1u).toInt()
            p = pNext
        } else {
            break
        }
    }
    if (nDigits > 0) {
        // at this point we have more than 19 significant digits, let's try again
        manyDigits = true
        mantissa = 0uL
        var s2 = start
        tryParse19digits(s2, RustMutableReference(localStorageL108C4.asMutPtr(), localStorageL108C4.layout))
        exponent = (if (mantissa >= MIN_19DIGIT_INT) {
            // big int
            intEnd.offsetFrom(s2)
        } else {
            s2 = ((s2).subList(1, (s2).size))
            val before1 = s2
            tryParse19digits(s2, RustMutableReference(localStorageL108C4.asMutPtr(), localStorageL108C4.layout))
            -s2.offsetFrom(before1)
        }).toLong()
        // add back the explicit part
        exponent += expNumber
    }
    return Pair(Decimal(exponent = exponent, mantissa = mantissa, negative = false, manyDigits = manyDigits), len)
}

/**
 * Try to parse a non-special floating point number,
 * as well as two slices with integer and fractional parts
 * and the parsed exponent.
 */
public fun parseNumber(s: List<UByte>): Decimal? {
    when (val matchVal = parsePartialNumber(s)) {
        null -> {}
        else -> {
            val float = matchVal.first
            val rest = matchVal.second
            if (rest == s.size) {
                return float
            }
        }
    }
    return null
}

/**
 * Try to parse a special, non-finite float.
 */
public fun <Int, F : Float<F, Any?>> parseInfNan(s: List<UByte>, negative: Boolean): F? {
    // u64 values corresponding to relevant cases
    val INF_3: ULong = 0x464E49uL
    // "INF"
    val INF_8: ULong = 0x5954494E49464E49uL
    // "INFINITY"
    val NAN: ULong = 0x4E414EuL
    // Since a valid string has at most the length 8, we can load
    // all relevant characters into a u64 and work from there.
    // This also generates much better code.
    var register: Any?
    val len: Int
    // All valid strings are either of length 8 or 3.
    if (s.size == 8) {
        register = s.readU64()
        len = 8
    } else if (s.size == 3) {
        val a = s[0].toULong()
        val b = s[1].toULong()
        val c = s[2].toULong()
        register = (c shl 16) or (b shl 8) or a
        len = 3
    } else {
        return null
    }
    // Clear out the bits which turn ASCII uppercase characters into
    // lowercase characters. The resulting string is all uppercase.
    // What happens to other characters is irrelevant.
    register = register and 0xDFDFDFDFDFDFDFDFuL
    // "NAN"
    // Match register value to constant to parse string.
    // Also match on the string length to catch edge cases
    // like "inf\0\0\0\0\0".
    val float =
        run {
            val matchVal = Pair(register, len)
            when {
                matchVal.first == INF_3 && matchVal.second == 3 -> F.INFINITY
                matchVal.first == INF_8 && matchVal.second == 8 -> F.INFINITY
                matchVal.first == NAN && matchVal.second == 3 -> F.NAN
                else -> { return null }
            }
        }
    return if (negative) {
        -float
    } else {
        float
    }
}
