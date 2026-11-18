// port-lint: source term/terminfo/parm.rs
package io.github.kotlinmania.rustlang.test.term.terminfo.parm

import io.github.kotlinmania.rustlang.core.char.methods.isAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.methods.isAsciiUppercase
import io.github.kotlinmania.rustlang.core.char.methods.toDigit
import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.iter.sources.repeatn.repeatN
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.test.term.terminfo.parm.Param.*
import io.github.kotlinmania.rustlang.test.term.terminfo.parm.States.*
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.experimental.xor

// Parameterized string expansion

public sealed class States {
    public data object Nothing : States()
    public data object Percent : States()
    public data object SetVar : States()
    public data object GetVar : States()
    public data object PushParam : States()
    public data object CharConstant : States()
    public data object CharClose : States()
    public data class IntConstant(public var value: Int) : States()
    public data class FormatPattern(public var field0: Flags, public var field1: FormatState) : States()
    public data class SeekIfElse(public var value: Int) : States()
    public data class SeekIfElsePercent(public var value: Int) : States()
    public data class SeekIfEnd(public var value: Int) : States()
    public data class SeekIfEndPercent(public var value: Int) : States()

    public fun rustEquals(other: States): Boolean = this == other
}

public sealed class FormatState {
    public data object Flags : FormatState()
    public data object Width : FormatState()
    public data object Precision : FormatState()

    public fun rustEquals(other: FormatState): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Flags -> 0
        Width -> 1
        Precision -> 2
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()
}

/**
 * Types of parameters a capability can use
 */
public sealed class Param {
    public data class Number(public var value: Int) : Param()
}

/**
 * Container for static and dynamic variable arrays
 */
public open class Variables(
    /**
     * Static variables A-Z
     */
    public var staVa: List<Param>,
    /**
     * Dynamic variables a-z
     */
    public var dynVa: List<Param>,
) {
    public companion object {
        /**
         * Returns a new zero-initialized Variables
         */
        public fun new(): Variables = Variables(staVa = listOf(Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0)), dynVa = listOf(Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0)))
    }
}

/**
 * Expand a parameterized capability
 *
 * # Arguments
 * * `cap`    - string to expand
 * * `params` - vector of params for %p1 etc
 * * `vars`   - Variables struct for %Pa etc
 *
 * To be compatible with ncurses, `vars` should be the same between calls to `expand` for
 * multiple capabilities for the same terminal.
 */
public fun expand(cap: List<UByte>, params: List<Param>, vars: Variables): Result<List<UByte>, String> {
    var state = Nothing
    // expanded cap will only rarely be larger than the cap itself
    val output = ArrayList<UByte>(cap.size)
    val stack: List<Param>  = mutableListOf()
    // Copy parameters into a local vector for mutability
    val mparams = mutableListOf(Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0), Number(0))
    for ((dst, src) in (ArrayDeque(mparams).zip(params))) {
        dst = src.clone()
    }
    for (c in cap) {
        val cur = c.toInt().toChar()
        var oldState = state
        when (state) {
            Nothing -> {
                if (cur == '%') {
                    state = Percent
                } else {
                    output.add(c)
                }
            }
            Percent -> {
                when (cur) {
                    '%' -> {
                        output.add(c)
                        state = Nothing
                    }
                    'c' -> {
                        run {
                            val matchVal = stack.removeLastOrNull()
                            when {
                                // if c is 0, use 0200 (128) for ncurses compatibility
                                matchVal != null && matchVal is Number && matchVal.value == 0 -> output.add(128u.toUByte())
                                // Don't check bounds. ncurses just casts and truncates.
                                matchVal != null && matchVal is Number -> {
                                    val (c1) = matchVal as Number
                                    output.add(c1.toUByte())
                                }
                                else -> { return Result.Err<List<UByte>, String>("stack is empty".toString()) }
                            }
                        }
                    }
                    'p' -> state = PushParam
                    'P' -> state = SetVar
                    'g' -> state = GetVar
                    '\'' -> state = CharConstant
                    '{' -> state = IntConstant(0)
                    'l' -> when (val matchVal1 = stack.removeLastOrNull()) {
                        null -> { return Result.Err<List<UByte>, String>("stack is empty".toString()) }
                        else -> { return Result.Err<List<UByte>, String>("a non-str was used with %l".toString()) }
                    }
                    '+', '-', '/', '*', '^', '&', '|', 'm' -> {
                        run {
                            val matchVal2 = Pair(stack.removeLastOrNull(), stack.removeLastOrNull())
                            when {
                                matchVal2.first != null && matchVal2.first is Number && matchVal2.second != null && matchVal2.second is Number -> {
                                    val (y) = matchVal2.first as Number
                                    val (x) = matchVal2.second as Number
                                    stack.add(Number(when (cur) {
                                        '+' -> x + y
                                        '-' -> x - y
                                        '*' -> x * y
                                        '/' -> x / y
                                        '|' -> x or y
                                        '&' -> x and y
                                        '^' -> x xor y
                                        'm' -> x % y
                                        else -> error("All cases handled")
                                    }))
                                }
                                else -> { return Result.Err<List<UByte>, String>("stack is empty".toString()) }
                            }
                        }
                    }
                    '=', '>', '<', 'A', 'O' -> run {
                        val matchVal3 = Pair(stack.removeLastOrNull(), stack.removeLastOrNull())
                        when {
                            matchVal3.first != null && matchVal3.first is Number && matchVal3.second != null && matchVal3.second is Number -> {
                                val (y1) = matchVal3.first as Number
                                val (x1) = matchVal3.second as Number
                                stack.add(Number(if (when (cur) {
                                    '=' -> x1 == y1
                                    '<' -> x1 < y1
                                    '>' -> x1 > y1
                                    'A' -> x1 > 0 && y1 > 0
                                    'O' -> x1 > 0 || y1 > 0
                                    else -> error("unreachable")
                                }) {
                                    1
                                } else {
                                    0
                                }))
                            }
                            else -> { return Result.Err<List<UByte>, String>("stack is empty".toString()) }
                        }
                    }
                    '!', '~' -> when (val matchVal4 = stack.removeLastOrNull()) {
                        is Number -> {
                            val (x2) = matchVal4 as Number
                            stack.add(Number(when {
                                cur == '!' && x2 > 0 -> 0
                                cur == '!' -> 1
                                cur == '~' -> x2.inv()
                                else -> error("unreachable")
                            }))
                        }
                        null -> { return Result.Err<List<UByte>, String>("stack is empty".toString()) }
                    }
                    'i' -> run {
                        val matchVal5 = Pair(mparams[0], mparams[1])
                        when {
                            else -> {
                                val (x3) = matchVal5.first as Number
                                val (y2) = matchVal5.second as Number
                                mparams[0] = Number(x3 + 1)
                                mparams[1] = Number(y2 + 1)
                            }
                        }
                    }
                    // printf-style support for %doxXs
                    'd', 'o', 'x', 'X', 's' -> {
                        when (val matchVal6 = stack.removeLastOrNull()) {
                            null -> {
                                return Result.Err<List<UByte>, String>("stack is empty".toString())
                            }
                            else -> {
                                val arg = matchVal6
                                val flags = Flags.new()
                                val res = when (val tryResult = format(arg, FormatOp.fromChar(cur), flags)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UByte>, String>(tryResult.errValue()) }
                                output.extend(res.iter().cloned())
                            }
                        }
                    }
                    ':', '#', ' ', '.', in '0'..'9' -> {
                        val flags1 = Flags.new()
                        var fstate = FormatState.Flags
                        when (cur) {
                            ':' -> Unit
                            '#' -> flags1.alternate = true
                            ' ' -> flags1.space = true
                            '.' -> { fstate = FormatState.Precision }
                            in '0'..'9' -> {
                                flags1.width = cur.code - '0'.code
                                fstate = FormatState.Width
                            }
                            else -> error("unreachable")
                        }
                        state = FormatPattern(flags1, fstate)
                    }
                    // conditionals
                    '?' -> Unit
                    't' -> run {
                        val matchVal7 = stack.removeLastOrNull()
                        when {
                            matchVal7 != null && matchVal7 is Number && matchVal7.value == 0 -> state = SeekIfElse(0)
                            matchVal7 != null && matchVal7 is Number -> Unit
                            else -> { return Result.Err<List<UByte>, String>("stack is empty".toString()) }
                        }
                    }
                    'e' -> state = SeekIfEnd(0)
                    ';' -> Unit
                    else -> { return Result.Err<List<UByte>, String>("unrecognized format option $cur") }
                }
            }
            PushParam -> {
                // params are 1-indexed
                stack.add(mparams[when (val matchVal8 = cur.toDigit(10u)) {
                    null -> { return Result.Err<List<UByte>, String>("bad param number".toString()) }
                    else -> {
                        val d = matchVal8
                        d.toInt() - 1
                    }
                }].clone())
            }
            SetVar -> {
                if (cur.isAsciiUppercase()) {
                    when (val matchVal9 = stack.removeLastOrNull()) {
                        null -> {
                            return Result.Err<List<UByte>, String>("stack is empty".toString())
                        }
                        else -> {
                            val arg1 = matchVal9
                            val idx = (cur.code.toUByte()) - 65u.toUByte()
                            vars.staVa[idx.toInt()] = arg1
                        }
                    }
                } else if (cur.isAsciiLowercase()) {
                    when (val matchVal10 = stack.removeLastOrNull()) {
                        null -> {
                            return Result.Err<List<UByte>, String>("stack is empty".toString())
                        }
                        else -> {
                            val arg2 = matchVal10
                            val idx1 = (cur.code.toUByte()) - 97u.toUByte()
                            vars.dynVa[idx1.toInt()] = arg2
                        }
                    }
                } else {
                    return Result.Err<List<UByte>, String>("bad variable name in %P".toString())
                }
            }
            GetVar -> {
                if (cur.isAsciiUppercase()) {
                    val idx2 = (cur.code.toUByte()) - 65u.toUByte()
                    stack.add(vars.staVa[idx2.toInt()].clone())
                } else if (cur.isAsciiLowercase()) {
                    val idx3 = (cur.code.toUByte()) - 97u.toUByte()
                    stack.add(vars.dynVa[idx3.toInt()].clone())
                } else {
                    return Result.Err<List<UByte>, String>("bad variable name in %g".toString())
                }
            }
            CharConstant -> {
                stack.add(Number(c.toInt()))
                state = CharClose
            }
            CharClose -> {
                if (cur != '\'') {
                    return Result.Err<List<UByte>, String>("malformed character constant".toString())
                }
            }
            is IntConstant -> {
                val (i) = state
                if (cur == '}') {
                    stack.add(Number(i))
                    state = Nothing
                } else when (val matchVal11 = cur.toDigit(10u)) {
                    null -> {
                        return Result.Err<List<UByte>, String>("bad int constant".toString())
                    }
                    else -> {
                        val digit = matchVal11
                        when (val matchVal12 = i.checkedMul(10)?.let({ iTen -> iTen.checkedAdd(digit.toInt()) })) {
                            null -> { return Result.Err<List<UByte>, String>("int constant too large".toString()) }
                            else -> {
                                val i1 = matchVal12
                                state = IntConstant(i1)
                                oldState = Nothing
                            }
                        }
                    }
                }
            }
            is FormatPattern -> {
                val (flags2, fstate1) = state
                oldState = Nothing
                run {
                    val matchVal13 = Pair(fstate1, cur)
                    when {
                        matchVal13.second == 'd' -> {
                            when (val matchVal14 = stack.removeLastOrNull()) {
                                null -> {
                                    return Result.Err<List<UByte>, String>("stack is empty".toString())
                                }
                                else -> {
                                    val arg3 = matchVal14
                                    val res1 = when (val tryResult = format(arg3, FormatOp.fromChar(cur), flags2)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UByte>, String>(tryResult.errValue()) }
                                    output.extend(res1.iter().cloned())
                                    // will cause state to go to Nothing
                                    oldState = FormatPattern(flags2, fstate1)
                                }
                            }
                        }
                        matchVal13.second == 'o' -> {
                            when (val matchVal15 = stack.removeLastOrNull()) {
                                null -> {
                                    return Result.Err<List<UByte>, String>("stack is empty".toString())
                                }
                                else -> {
                                    val arg4 = matchVal15
                                    val res2 = when (val tryResult = format(arg4, FormatOp.fromChar(cur), flags2)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UByte>, String>(tryResult.errValue()) }
                                    output.extend(res2.iter().cloned())
                                    oldState = FormatPattern(flags2, fstate1)
                                }
                            }
                        }
                        matchVal13.second == 'x' -> {
                            when (val matchVal16 = stack.removeLastOrNull()) {
                                null -> {
                                    return Result.Err<List<UByte>, String>("stack is empty".toString())
                                }
                                else -> {
                                    val arg5 = matchVal16
                                    val res3 = when (val tryResult = format(arg5, FormatOp.fromChar(cur), flags2)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UByte>, String>(tryResult.errValue()) }
                                    output.extend(res3.iter().cloned())
                                    oldState = FormatPattern(flags2, fstate1)
                                }
                            }
                        }
                        matchVal13.second == 'X' -> {
                            when (val matchVal17 = stack.removeLastOrNull()) {
                                null -> {
                                    return Result.Err<List<UByte>, String>("stack is empty".toString())
                                }
                                else -> {
                                    val arg6 = matchVal17
                                    val res4 = when (val tryResult = format(arg6, FormatOp.fromChar(cur), flags2)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UByte>, String>(tryResult.errValue()) }
                                    output.extend(res4.iter().cloned())
                                    oldState = FormatPattern(flags2, fstate1)
                                }
                            }
                        }
                        matchVal13.second == 's' -> {
                            when (val matchVal18 = stack.removeLastOrNull()) {
                                null -> {
                                    return Result.Err<List<UByte>, String>("stack is empty".toString())
                                }
                                else -> {
                                    val arg7 = matchVal18
                                    val res5 = when (val tryResult = format(arg7, FormatOp.fromChar(cur), flags2)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UByte>, String>(tryResult.errValue()) }
                                    output.extend(res5.iter().cloned())
                                    oldState = FormatPattern(flags2, fstate1)
                                }
                            }
                        }
                        matchVal13.first == FormatState.Flags && matchVal13.second == '#' -> {
                            flags2.alternate = true
                        }
                        matchVal13.first == FormatState.Flags && matchVal13.second == '-' -> {
                            flags2.left = true
                        }
                        matchVal13.first == FormatState.Flags && matchVal13.second == '+' -> {
                            flags2.sign = true
                        }
                        matchVal13.first == FormatState.Flags && matchVal13.second == ' ' -> {
                            flags2.space = true
                        }
                        matchVal13.first == FormatState.Flags && matchVal13.second in '0'..'9' -> {
                            flags2.width = cur.code - '0'.code
                            fstate1 = FormatState.Width
                        }
                        matchVal13.first == FormatState.Flags && matchVal13.second == '.' -> {
                            fstate1 = FormatState.Precision
                        }
                        matchVal13.first == FormatState.Width && matchVal13.second in '0'..'9' -> {
                            val old = flags2.width
                            flags2.width = flags2.width * 10 + (cur.code - '0'.code)
                            if (flags2.width < old) {
                                return Result.Err<List<UByte>, String>("format width overflow".toString())
                            }
                        }
                        matchVal13.first == FormatState.Width && matchVal13.second == '.' -> {
                            fstate1 = FormatState.Precision
                        }
                        matchVal13.first == FormatState.Precision && matchVal13.second in '0'..'9' -> {
                            val old1 = flags2.precision
                            flags2.precision = flags2.precision * 10 + (cur.code - '0'.code)
                            if (flags2.precision < old1) {
                                return Result.Err<List<UByte>, String>("format precision overflow".toString())
                            }
                        }
                        else -> { return Result.Err<List<UByte>, String>("invalid format specifier".toString()) }
                    }
                }
            }
            is SeekIfElse -> {
                val (level) = state
                if (cur == '%') {
                    state = SeekIfElsePercent(level)
                }
                oldState = Nothing
            }
            is SeekIfElsePercent -> {
                val (level1) = state
                if (cur == ';') {
                    if (level1 == 0) {
                        state = Nothing
                    } else {
                        state = SeekIfElse(level1 - 1)
                    }
                } else if (cur == 'e' && level1 == 0) {
                    state = Nothing
                } else if (cur == '?') {
                    state = SeekIfElse(level1 + 1)
                } else {
                    state = SeekIfElse(level1)
                }
            }
            is SeekIfEnd -> {
                val (level2) = state
                if (cur == '%') {
                    state = SeekIfEndPercent(level2)
                }
                oldState = Nothing
            }
            is SeekIfEndPercent -> {
                val (level3) = state
                if (cur == ';') {
                    if (level3 == 0) {
                        state = Nothing
                    } else {
                        state = SeekIfEnd(level3 - 1)
                    }
                } else if (cur == '?') {
                    state = SeekIfEnd(level3 + 1)
                } else {
                    state = SeekIfEnd(level3)
                }
            }
        }
        if (state == oldState) {
            state = Nothing
        }
    }
    return Result.Ok<List<UByte>, String>(output)
}

public open class Flags(
    public var width: Int,
    public var precision: Int,
    public var alternate: Boolean,
    public var left: Boolean,
    public var sign: Boolean,
    public var space: Boolean,
) {
    public open fun clone(): Flags = Flags(width, precision, alternate, left, sign, space)

    public companion object {
        public fun new(): Flags = Flags(width = 0, precision = 0, alternate = false, left = false, sign = false, space = false)
    }
}

public sealed class FormatOp {
    public data object Digit : FormatOp()
    public data object Octal : FormatOp()
    public data object LowerHex : FormatOp()
    public data object UpperHex : FormatOp()
    public data object String : FormatOp()

    private fun kmDiscriminant(): Int =
        when (this) {
        Digit -> 0
        Octal -> 1
        LowerHex -> 2
        UpperHex -> 3
        String -> 4
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public companion object {
        public fun fromChar(c: Char): FormatOp =
            when (c) {
                'd' -> FormatOp.Digit
                'o' -> FormatOp.Octal
                'x' -> FormatOp.LowerHex
                'X' -> FormatOp.UpperHex
                's' -> FormatOp.String
                else -> error("bad FormatOp char")
            }
    }
}

public fun format(value: Param, op: FormatOp, flags: Flags): Result<List<UByte>, String> {
    var s =
        when (value) {
            is Number -> {
                val (d) = value
                when (op) {
                    FormatOp.Digit -> {
                        if (flags.sign) {
                            "$d"
                        } else if (d < 0) {
                            // C doesn't take sign into account in precision calculation.
                            "$d"
                        } else if (flags.space) {
                            " $d"
                        } else {
                            "$d"
                        }
                    }
                    FormatOp.Octal -> {
                        if (flags.alternate) {
                            // Leading octal zero counts against precision.
                            "0$d"
                        } else {
                            "$d"
                        }
                    }
                    FormatOp.LowerHex -> {
                        if (flags.alternate && d != 0) {
                            "0x$d"
                        } else {
                            "$d"
                        }
                    }
                    FormatOp.UpperHex -> {
                        if (flags.alternate && d != 0) {
                            "0X$d"
                        } else {
                            "$d"
                        }
                    }
                    FormatOp.String -> { return Result.Err<List<UByte>, String>("non-number on stack with %s".toString()) }
                }.intoBytes()
            }
        }
    if (flags.width > s.len()) {
        val n = flags.width - s.len()
        if (flags.left) {
            s.extend(repeatN(32u.toUByte(), n))
        } else {
            var s = ArrayList<UByte>(flags.width)
            s.extend(repeatN(32u.toUByte(), n))
            s.extend(s)
            s = s
        }
    }
    return Result.Ok<List<UByte>, String>(s)
}
