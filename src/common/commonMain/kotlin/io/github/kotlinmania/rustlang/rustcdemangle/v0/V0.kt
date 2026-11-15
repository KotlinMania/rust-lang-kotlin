// port-lint: source v0.rs
package io.github.kotlinmania.rustlang.rustcdemangle.v0

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.num.rustCheckedAdd
import io.github.kotlinmania.rustlang.core.num.rustCheckedMul
import io.github.kotlinmania.rustlang.core.num.rustCheckedSub
import io.github.kotlinmania.rustlang.core.num.rustSaturatingSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.get
import io.github.kotlinmania.rustlang.core.str.rustStrChars
import io.github.kotlinmania.rustlang.core.str.rustToDigit

// Mark the parser as errored (with `ParseError.Invalid`), print the
// appropriate message (see `ParseError.message`) and return early.
//
// Call a parser method (if the parser hasn't errored yet),
// and mark the parser as errored if it returns `Err`.
//
// If the parser errored, before or now, this returns early,
// from the current function, after printing either:
// * for a new error, the appropriate message (see `ParseError.message`)
// * for an earlier error, only `?` -  this allows callers to keep printing
//   the approximate syntax of the path/type/const, despite having errors,
//   e.g. `List<[(A, ?); ?]>` instead of `Vec<[(A, ?`

// Maximum recursion depth when parsing symbols before we just bail out saying
// "this symbol is invalid"
public val MAX_DEPTH: UInt = 500u

/**
 * Representation of a demangled symbol name.
 */
public open class Demangle(
    public var inner: String,
) {
    public open fun fmt(f: StringBuilder) {
        val printer = Printer(parser = Result.Ok(Parser(sym = this.inner, next = 0, depth = 0u)), out = f, boundLifetimeDepth = 0u)
        printer.printPath(true)
    }
}

public sealed class ParseError {
    /**
     * Symbol doesn't match the expected `v0` grammar.
     */
    public data object Invalid : ParseError()
    /**
     * Parsing the symbol crossed the recursion limit (see `MAX_DEPTH`).
     */
    public data object RecursedTooDeep : ParseError()

    public fun rustEquals(other: ParseError): Boolean = this == other

    /**
     * Snippet to print when the error is initially encountered.
     */
    public open fun message(): String =
        when (this) {
            ParseError.Invalid -> "{invalid syntax}"
            ParseError.RecursedTooDeep -> "{recursion limit reached}"
        }
}

/**
 * De-mangles a Rust symbol into a more readable version
 *
 * This function will take a **mangled** symbol and return a value. When printed,
 * the de-mangled version will be written. If the symbol does not look like
 * a mangled symbol, the original value will be written instead.
 */
public fun demangle(s: String): Result<Pair<Demangle, String>, ParseError> {
    // First validate the symbol. If it doesn't look like anything we're
    // expecting, we just print it literally. Note that we must handle non-Rust
    // symbols because we could have any function in the backtrace.
    val inner: Any?
    if (s.length > 2 && s.startsWith("_R")) {
        inner = s.substring(2)
    } else if (s.length > 1 && s.startsWith('R')) {
        // On Windows, dbghelp strips leading underscores, so we accept "R..."
        // form too.
        inner = s.substring(1)
    } else if (s.length > 3 && s.startsWith("__R")) {
        // On OSX, symbols are prefixed with an extra _
        inner = s.substring(3)
    } else {
        return Result.Err(ParseError.Invalid)
    }
    // Paths always start with uppercase characters.
    when (inner.encodeToByteArray().map { it.toUByte() }[0]) {
        in 65u..90u -> {}
        else -> { return Result.Err(ParseError.Invalid) }
    }
    // only work with ascii text
    if (inner.bytes().any({ c -> c and 0x80 != 0 })) {
        return Result.Err(ParseError.Invalid)
    }
    // Verify that the symbol is indeed a valid path.
    val tryParsePath =
        { parser ->
            var dummyPrinter = Printer(parser = Result.Ok(parser), out = null, boundLifetimeDepth = 0u)
            dummyPrinter.printPath(false).expect("`fmt::Error`s should be impossible without a `fmt::Formatter`")
            dummyPrinter.parser
        }
    var parser = Parser(sym = inner, next = 0, depth = 0u)
    parser = when (val tryResult = tryParsePath(parser)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
    // Instantiating crate (paths always start with uppercase characters).
    when (val matchVal = parser.sym.encodeToByteArray().map { it.toUByte() }.get(parser.next)) {
        is Some -> {
            parser = when (val tryResult = tryParsePath(parser)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        }
        else -> {}
    }
    return Result.Ok(Pair(Demangle(inner = inner), parser.sym.substring(parser.next)))
}

public open class Ident(
    /**
     * ASCII part of the identifier.
     */
    public var ascii: String,
    /**
     * Punycode insertion codes for Unicode codepoints, if any.
     */
    public var punycode: String,
) {
    /**
     * Attempt to decode punycode on the stack (allocation-free),
     * and pass the char slice to the closure, if successful.
     * This supports up to `SMALL_PUNYCODE_LEN` characters.
     */
    public open fun <R> trySmallPunycodeDecode(f: (List<Char>) -> R): R? {
        val out = MutableList(SMALL_PUNYCODE_LEN) { '\u0000' }
        var outLen = 0
        val r =
            this.punycodeDecode(closure@{ i, c ->
                // Check there's space left for another character.
                when (val tryResult = out.getOrNull(outLen).okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure Result.Err(tryResult.error) }
                // Move the characters after the insert position.
                var j = outLen
                outLen += 1
                while (j > i) {
                    out[j] = out[j - 1]
                    j -= 1
                }
                // Insert the new character.
                out[i] = c
                Result.Ok(Unit)
            })
        return if (r.isOk()) {
            f(out[0 until outLen])
        } else {
            null
        }
    }

    /**
     * Decode punycode as insertion positions and characters
     * and pass them to the closure, which can return `Err(())`
     * to stop the decoding process.
     */
    public open fun punycodeDecode(insert: (Int, Char) -> Result<Unit, Unit>): Result<Unit, Unit> {
        val punycodeBytes = this.punycode.bytes().peekable()
        if (punycodeBytes.peek() == null) {
            return Result.Err(Unit)
        }
        var len = 0
        // Populate initial output from ASCII fragment.
        for (c in this.ascii.rustStrChars()) {
            when (val tryResult = insert(len, c)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            len += 1
        }
        // Punycode parameters and initial state.
        val base = 36
        val tMin = 1
        val tMax = 26
        val skew = 38
        var damp = 700
        var bias = 72
        var i: Int = 0
        var n: Int = 0x80
        while (true) {
            // Read one delta value.
            var delta: Int = 0
            var w = 1
            var k: Int = 0
            while (true) {
                k += base
                val t = minOf(max(rustSaturatingSub(k, bias.toInt()), tMin), tMax)
                val d2 =
                    when (val matchVal = punycodeBytes.next()) {
                        in 97u..122u -> {
                            val d = matchVal
                            d - 97u
                        }
                        in 48u..57u -> {
                            val d1 = matchVal
                            26u + (d1 - 48u)
                        }
                        else -> { return Result.Err(Unit) }
                    }
                val d3 = d2.toInt()
                delta = when (val tryResult = rustCheckedAdd(delta, (when (val tryResult = rustCheckedMul(d3, w.toInt()).okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }).toInt()).okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                if (d3 < t) {
                    break
                }
                w = when (val tryResult = w.checkedMul(base - t).okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            }
            // Compute the new insert position and character.
            len += 1
            i = when (val tryResult = rustCheckedAdd(i, delta).okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            n = when (val tryResult = rustCheckedAdd(n, i / len).okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            i %= len
            val nU32 = n.toUInt()
            val c1 =
                if (nU32.toInt() == n) {
                    when (val tryResult = Char.fromU32(nU32).okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                } else {
                    return Result.Err(Unit)
                }
            // Insert the new character and increment the insert position.
            when (val tryResult = insert(i, c1)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            i += 1
            // If there are no more deltas, decoding is complete.
            if (punycodeBytes.peek() == null) {
                return Result.Ok(Unit)
            }
            // Perform bias adaptation.
            delta /= damp
            damp = 2
            delta += delta / len
            var k1 = 0
            while (delta > ((base - tMin) * tMax) / 2) {
                delta /= base - tMin
                k1 += base
            }
            bias = k1 + ((base - tMin + 1) * delta) / (delta + skew)
        }
    }

    public open fun fmt(f: StringBuilder) {
        this.trySmallPunycodeDecode(closure1@{ chars ->
            for (c in chars) {
                when (val tryResult = c.fmt(f)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.error) }
            }
            Result.Ok(Unit)
        }).unwrapOrElse(closure2@{
            if (!this.punycode.isEmpty()) {
                f.append("punycode{")
                // Reconstruct a standard Punycode encoding,
                // by using `-` as the separator.
                if (!this.ascii.isEmpty()) {
                    f.append(this.ascii)
                    f.append("-")
                }
                f.append(this.punycode)
                f.append("}")
            } else {
                f.append(this.ascii)
            }
        })
    }
}

public val SMALL_PUNYCODE_LEN: Int = 128

/**
 * Sequence of lowercase hexadecimal nibbles (`0 - 9a - f`), used by leaf consts.
 */
public open class HexNibbles(
    public var nibbles: String,
) {
    // Note(eddyb) should this "just" use `ULong` instead?

    /**
     * Decode an integer value (with the "most significant nibble" first),
     * returning `None` if it can't fit in an `ULong`.
     */
    public open fun tryParseUint(): ULong? {
        val nibbles = this.nibbles.trimStartMatches("0")
        if (nibbles.len() > 16) {
            return null
        }
        var v = 0
        for (nibble in nibbles.chars()) {
            v = (v shl 4) or (nibble.toDigit(16u).unwrap().toULong())
        }
        return v
    }

    /**
     * Decode a UTF-8 byte sequence (with each byte using a pair of nibbles)
     * into individual `char`s, returning `None` for invalid UTF-8.
     */
    public open fun tryParseStrChars(): Any? {
        if (this.nibbles.length % 2 != 0) {
            return null
        }
        // Note(eddyb) use `arrayChunks` instead, when that becomes stable.
        var bytes =
            this.nibbles.encodeToByteArray().map { it.toUByte() }.chunksExact(2).map({ slice -> when {
                slice.size == 2 -> {
                    val a = slice[0]
                    val b = slice[1]
                    listOf(a, b)
                }
                else -> error("unreachable")
            } }).map({ param0 ->
                val half = { nibble: UByte -> (nibble.toInt().toChar()).rustToDigit(16u)!!.toUByte() }
                (half(hi) shl 4) or half(lo)
            })
        val chars =
            iter.fromFn({
                // As long as there are any bytes left, there's at least one more
                // UTF-8-encoded `char` to decode (or the possibility of error).
                bytes.next()?.let(closure3@{ firstByte ->
                    // Note(eddyb) this `sealed class` and `fun` should be somewhere in `core`.
                    sealed class Utf8FirstByteError {
                        public data object ContinuationByte : Utf8FirstByteError()
                        public data object TooLong : Utf8FirstByteError()
                    }
                    fun utf8LenFromFirstByte(byte: UByte): Result<Int, Utf8FirstByteError> =
                        when (byte) {
                            in 0x00..0x7f -> { Result.Ok(1) }
                            in 0x80..0xbf -> { Result.Err(Utf8FirstByteError.ContinuationByte) }
                            in 0xc0..0xdf -> { Result.Ok(2) }
                            in 0xe0..0xef -> { Result.Ok(3) }
                            in 0xf0..0xf7 -> { Result.Ok(4) }
                            in 0xf8..0xff -> Result.Err(Utf8FirstByteError.TooLong)
                        }
                    // Collect the appropriate amount of bytes (up to 4), according
                    // to the UTF-8 length implied by the first byte.
                    val utf8Len = when (val tryResult = utf8LenFromFirstByte(firstByte).mapErr({ Unit })) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.error) }
                    val utf8 = listOf(firstByte, 0, 0, 0)[0 until utf8Len]
                    for (i in 1 until utf8Len) {
                        utf8[i] = when (val tryResult = bytes.next().okOr()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.error) }
                    }
                    // Fully validate the UTF-8 sequence.
                    val s = when (val tryResult = String.fromUtf8(utf8).mapErr({ Unit })) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure3 Result.Err(tryResult.error) }
                    // Since we included exactly one UTF-8 sequence, and validation
                    // succeeded, `str.chars` should return exactly one `char`.
                    var chars = s.chars()
                    run {
                        val matchVal = Pair(chars.next(), chars.next())
                        when {
                            matchVal.first != null && matchVal.second == null -> {
                                val c = matchVal.first
                                Result.Ok(c)
                            }
                            else -> error("str::from_utf8($utf8) = $s was expected to have 1 char, but ${s.chars().count()} chars were found")
                        }
                    }
                })
            })
        // Note(eddyb) doing a separate validation iteration like this might be
        // wasteful, but it's easier to avoid starting to print a string literal
        // in the first place, than to abort it mid-string.
        return if (chars.clone().any({ r -> r.isErr() })) {
            null
        } else {
            chars.map(Result::unwrap)
        }
    }
}

public fun basicType(tag: UByte): String? {
    return when (tag) {
        98u -> "bool"
        99u -> "char"
        101u -> "str"
        117u -> "()"
        97u -> "i8"
        115u -> "i16"
        108u -> "i32"
        120u -> "i64"
        110u -> "i128"
        105u -> "isize"
        104u -> "u8"
        116u -> "u16"
        109u -> "u32"
        121u -> "u64"
        111u -> "u128"
        106u -> "usize"
        102u -> "f32"
        100u -> "f64"
        122u -> "!"
        112u -> "_"
        118u -> "..."
        else -> { return null }
    }
}

public open class Parser(
    public var sym: String,
    public var next: Int,
    public var depth: UInt,
) {
    public open fun pushDepth(): Result<Unit, ParseError> {
        this.depth += 1u
        return if (this.depth > MAX_DEPTH) {
            Result.Err(ParseError.RecursedTooDeep)
        } else {
            Result.Ok(Unit)
        }
    }

    public open fun popDepth() {
        this.depth -= 1u
    }

    public open fun peek(): UByte? = this.sym.encodeToByteArray().map { it.toUByte() }.get(this.next)

    public open fun eat(b: UByte): Boolean =
        if (this.peek() == b) {
            this.next += 1
            true
        } else {
            false
        }

    public open fun next(): Result<UByte, ParseError> {
        val b = when (val tryResult = (this.peek()?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        this.next += 1
        return Result.Ok(b)
    }

    public open fun hexNibbles(): Result<HexNibbles, ParseError> {
        val start = this.next
        while (true) {
            when (when (val tryResult = this.next()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }) {
                in 48u..57u, in 97u..102u -> {}
                95u -> { break }
                else -> { return Result.Err(ParseError.Invalid) }
            }
        }
        return Result.Ok(HexNibbles(nibbles = this.sym.substring(start until this.next - 1)))
    }

    public open fun digit10(): Result<UByte, ParseError> {
        val d1 =
            when (val matchVal = this.peek()) {
                in 48u..57u -> {
                    val d = matchVal
                    d - 48u
                }
                else -> { return Result.Err(ParseError.Invalid) }
            }
        this.next += 1
        return Result.Ok(d1)
    }

    public open fun digit62(): Result<UByte, ParseError> {
        val d3 =
            when (val matchVal = this.peek()) {
                in 48u..57u -> {
                    val d = matchVal
                    d - 48u
                }
                in 97u..122u -> {
                    val d1 = matchVal
                    10u + (d1 - 97u)
                }
                in 65u..90u -> {
                    val d2 = matchVal
                    10u + 26u + (d2 - 65u)
                }
                else -> { return Result.Err(ParseError.Invalid) }
            }
        this.next += 1
        return Result.Ok(d3)
    }

    public open fun integer62(): Result<ULong, ParseError> {
        if (this.eat(95u)) {
            return Result.Ok(0uL)
        }
        var x: ULong = 0uL
        while (!this.eat(95u)) {
            val d = (when (val tryResult = this.digit62()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }).toULong()
            x = when (val tryResult = (rustCheckedMul(x, 62uL)?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            x = when (val tryResult = (rustCheckedAdd(x, d)?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        }
        return (rustCheckedAdd(x, 1uL)?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))
    }

    public open fun optInteger62(tag: UByte): Result<ULong, ParseError> {
        if (!this.eat(tag)) {
            return Result.Ok(0uL)
        }
        return ((when (val tryResult = this.integer62()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }.checkedAdd(1))?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))
    }

    public open fun disambiguator(): Result<ULong, ParseError> = this.optInteger62(115u)

    public open fun namespace(): Result<Char?, ParseError> {
        return when (val matchVal = when (val tryResult = this.next()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }) {
            // Special namespaces, like closures and shims.
            in 65u..90u -> {
                val ns = matchVal
                Result.Ok(ns.toInt().toChar())
            }
            // Implementation-specific/unspecified namespaces.
            in 97u..122u -> Result.Ok(null)
            else -> Result.Err(ParseError.Invalid)
        }
    }

    public open fun backref(): Result<Parser, ParseError> {
        val sStart = this.next - 1
        val i = when (val tryResult = this.integer62()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        if (i >= sStart.toULong()) {
            return Result.Err(ParseError.Invalid)
        }
        val newParser = Parser(sym = this.sym, next = i.toInt(), depth = this.depth)
        when (val tryResult = newParser.pushDepth()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        return Result.Ok(newParser)
    }

    public open fun ident(): Result<Ident, ParseError> {
        val isPunycode = this.eat(117u)
        var len = (when (val tryResult = this.digit10()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }).toInt()
        if (len != 0) {
            while (true) {
                val matchVal = this.digit10()
                if (matchVal !is Result.Ok<*, *>) break
                val d = (matchVal as Result.Ok<*, *>).value
                len = when (val tryResult = (rustCheckedMul(len, 10)?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                len = when (val tryResult = (rustCheckedAdd(len, d.toInt())?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            }
        }
        // Skip past the optional `_` separator.
        this.eat(95u)
        val start = this.next
        this.next = when (val tryResult = (rustCheckedAdd(this.next, len)?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        if (this.next > this.sym.length) {
            return Result.Err(ParseError.Invalid)
        }
        val ident = this.sym.substring(start until this.next)
        return if (isPunycode) {
            val ident1 =
                when (val matchVal1 = ident.bytes().indexOfLast({ b -> b == 95u }).takeIf { it >= 0 }) {
                    null -> Ident(ascii = "", punycode = ident)
                    else -> {
                        val i = matchVal1
                        Ident(ascii = ident.substring(0 until i), punycode = ident.substring(i + 1))
                    }
                }
            if (ident1.punycode.isEmpty()) {
                return Result.Err(ParseError.Invalid)
            }
            Result.Ok(ident1)
        } else {
            Result.Ok(Ident(ascii = ident, punycode = ""))
        }
    }
}

public open class Printer internal constructor(
    parserCtorArg: Result<Parser, ParseError>,
    /**
     * The output formatter to demangle to, or `None` while skipping printing.
     */
    public var out: StringBuilder?,
    /**
     * Cumulative number of scopes bound by `for<...>` binders ('G'),
     * anywhere "around" the current entity (e.g. type) being demangled.
     * This value is not tracked while skipping printing, as it'd be unused.
     *
     * See also the documentation on the `Printer.inBinder` method.
     */
    public var boundLifetimeDepth: UInt,
) {
    /**
     * The input parser to demangle from, or `Err` if any (parse) error was
     * encountered (in order to disallow further likely-incorrect demangling).
     *
     * See also the documentation on the `invalid` and `parse` macros below.
     */
    public var parser: Result<Parser, ParseError> = parserCtorArg
        internal set

    /**
     * Eat the given character from the parser,
     * returning `false` if the parser errored.
     */
    public open fun eat(b: UByte): Boolean = (this.parser.asMut().map({ p -> p.eat(b) })).rustEquals(Result.Ok(true))

    /**
     * Skip printing (i.e. `self.out` will be `None`) for the duration of the
     * given closure. This should not change parsing behavior, only disable the
     * output, but there may be optimizations (such as not traversing backrefs).
     */
    public open fun skippingPrinting(f: (Printer) -> Unit) {
        val origOut = this.out.also { this.out = null }
        f(this).expect("`fmt::Error`s should be impossible without a `fmt::Formatter`")
        this.out = origOut
    }

    /**
     * Print the target of a backref, using the given closure.
     * When printing is being skipped, the backref will only be parsed,
     * ignoring the backref's target completely.
     */
    public open fun printBackref(f: (Printer) -> Unit) {
        val backrefParser =
            run {
                val matchVal = this.parser
                when (matchVal) { is Result.Ok<*, *> -> {
                    val parser = matchVal.value
                    run {
                        val matchVal1 = parser.backref()
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            val x = matchVal1.value
                            x
                        }; is Result.Err<*, *> -> {
                            val err = matchVal1.error
                            this.print(err.message())
                            this.parser = Result.Err(err)
                            return Unit
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }
        if (this.out == null) {
            return Unit
        }
        val origParser = replace(this.parser, Result.Ok(backrefParser))
        val r = f(this)
        this.parser = origParser
        r
    }

    public open fun popDepth() {
        when (val matchVal = this.parser) {
            is Ok -> {
                val parser = matchVal.value
                parser.popDepth()
            }
            else -> {}
        }
    }

    /**
     * Output the given value to `self.out` (using `fmt.Display` formatting),
     * if printing isn't being skipped.
     */
    public open fun print(x: Any) {
        when (val matchVal = this.out) {
            null -> {}
            else -> {
                val out = matchVal
                out.append((x).toString())
            }
        }
    }

    /**
     * Output the given `char`s (escaped using `char.escapeDebug`), with the
     * whole sequence wrapped in quotes, for either a `char` or `String` literal,
     * if printing isn't being skipped.
     */
    public open fun printQuotedEscapedChars(quote: Char, chars: Any) {
        when (val matchVal = this.out) {
            null -> {}
            else -> {
                val out = matchVal
                out.append(quote)
                for (c in chars) {
                    // Special-case not escaping a single/double quote, when
                    // inside the opposite kind of quote.
                    if (run {
                        val matchVal1 = Pair(quote, c)
                        when {
                            matchVal1.first == '\'' && matchVal1.second == '"' -> true
                            matchVal1.first == '"' && matchVal1.second == '\'' -> true
                            else -> false
                        }
                    }) {
                        out.append(c)
                        continue
                    }
                    for (escaped in c.escapeDebug()) {
                        out.append(escaped)
                    }
                }
                out.append(quote)
            }
        }
    }

    /**
     * Print the scope according to the previously decoded index.
     * An index of `0` always refers to `_`, but starting with `1`,
     * indices refer to late-bound scopes introduced by a binder.
     */
    public open fun printLifetimeFromIndex(lt: ULong) {
        // Bound scopes aren't tracked when skipping printing.
        if (this.out == null) {
            return Unit
        }
        this.print("'")
        if (lt == 0uL) {
            return this.print("_")
        }
        when (val matchVal = rustCheckedSub((this.boundLifetimeDepth.toULong()), lt)) {
            null -> {
                val err = ParseError.Invalid
                this.print(err.message())
                this.parser = Result.Err(err)
                return Unit
            }
            else -> {
                val depth = matchVal
                // Try to print scopes alphabetically first.
                if (depth < 26uL) {
                    val c = (97u + depth.toUByte()).toInt().toChar()
                    this.print(c)
                } else {
                    // Use `_123` after running out of letters.
                    this.print("_")
                    this.print(depth)
                }
            }
        }
    }

    /**
     * Optionally enter a binder ('G') for late-bound scopes,
     * printing e.g. `for<>` before calling the closure,
     * and make those scopes visible to it (via depth level).
     */
    public open fun inBinder(f: (Printer) -> Unit) {
        val boundLifetimes =
            run {
                val matchVal = this.parser
                when (matchVal) { is Result.Ok<*, *> -> {
                    val parser = matchVal.value
                    run {
                        val matchVal1 = parser.optInteger62(71u)
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            val x = matchVal1.value
                            x
                        }; is Result.Err<*, *> -> {
                            val err = matchVal1.error
                            this.print(err.message())
                            this.parser = Result.Err(err)
                            return Unit
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }
        // Don't track bound scopes when skipping printing.
        if (this.out == null) {
            return f(this)
        }
        if (boundLifetimes > 0) {
            this.print("for<")
            for (i in 0 until boundLifetimes) {
                if (i > 0) {
                    this.print(", ")
                }
                this.boundLifetimeDepth += 1u
                this.printLifetimeFromIndex(1)
            }
            this.print("> ")
        }
        val r = f(this)
        // Restore `boundLifetimeDepth` to the previous value.
        this.boundLifetimeDepth -= boundLifetimes.toUInt()
        r
    }

    /**
     * Print list elements using the given closure and separator,
     * until the end of the list ('E') is found, or the parser errors.
     * Returns the number of elements printed.
     */
    public open fun printSepList(f: (Printer) -> Unit, sep: String): Result<Int, Unit> {
        var i = 0
        while (this.parser.isOk() && !this.eat(69u)) {
            if (i > 0) {
                this.print(sep)
            }
            when (val tryResult = f(this)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            i += 1
        }
        return Result.Ok(i)
    }

    public open fun printPath(inValue: Boolean) {
        run {
            val matchVal = this.parser
            when (matchVal) { is Result.Ok<*, *> -> {
                val parser = matchVal.value
                run {
                    val matchVal1 = parser.pushDepth()
                    when (matchVal1) { is Result.Ok<*, *> -> {
                        val x = matchVal1.value
                        x
                    }; is Result.Err<*, *> -> {
                        val err = matchVal1.error
                        this.print(err.message())
                        this.parser = Result.Err(err)
                        return Unit
                    } }
                }
            }; is Result.Err<*, *> -> {
                return this.print("?")
            } }
        }
        val tag =
            run {
                val matchVal2 = this.parser
                when (matchVal2) { is Result.Ok<*, *> -> {
                    val parser1 = matchVal2.value
                    run {
                        val matchVal3 = parser1.next()
                        when (matchVal3) { is Result.Ok<*, *> -> {
                            val x1 = matchVal3.value
                            x1
                        }; is Result.Err<*, *> -> {
                            val err1 = matchVal3.error
                            this.print(err1.message())
                            this.parser = Result.Err(err1)
                            return Unit
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }
        when (tag) {
            67u -> {
                val dis =
                    run {
                        val matchVal4 = this.parser
                        when (matchVal4) { is Result.Ok<*, *> -> {
                            val parser2 = matchVal4.value
                            run {
                                val matchVal5 = parser2.disambiguator()
                                when (matchVal5) { is Result.Ok<*, *> -> {
                                    val x2 = matchVal5.value
                                    x2
                                }; is Result.Err<*, *> -> {
                                    val err2 = matchVal5.error
                                    this.print(err2.message())
                                    this.parser = Result.Err(err2)
                                    return Unit
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                val name =
                    run {
                        val matchVal6 = this.parser
                        when (matchVal6) { is Result.Ok<*, *> -> {
                            val parser3 = matchVal6.value
                            run {
                                val matchVal7 = parser3.ident()
                                when (matchVal7) { is Result.Ok<*, *> -> {
                                    val x3 = matchVal7.value
                                    x3
                                }; is Result.Err<*, *> -> {
                                    val err3 = matchVal7.error
                                    this.print(err3.message())
                                    this.parser = Result.Err(err3)
                                    return Unit
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                this.print(name)
                when (val matchVal8 = this.out) {
                    null -> {}
                    else -> {
                        val out = matchVal8
                        if (!out.alternate() && dis != 0) {
                            out.append("[")
                            out.append((dis).toString())
                            out.append("]")
                        }
                    }
                }
            }
            78u -> {
                val ns =
                    run {
                        val matchVal9 = this.parser
                        when (matchVal9) { is Result.Ok<*, *> -> {
                            val parser4 = matchVal9.value
                            run {
                                val matchVal10 = parser4.namespace()
                                when (matchVal10) { is Result.Ok<*, *> -> {
                                    val x4 = matchVal10.value
                                    x4
                                }; is Result.Err<*, *> -> {
                                    val err4 = matchVal10.error
                                    this.print(err4.message())
                                    this.parser = Result.Err(err4)
                                    return Unit
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                this.printPath(inValue)
                // Note(eddyb) if the parser is already marked as having errored,
                // `parse` below will print a `?` without its preceding `.`
                // (because printing the `.` is skipped in certain conditions,
                // i.e. a lowercase namespace with an empty identifier),
                // so in order to get `.?`, the `.` has to be printed here.
                if (this.parser.isErr()) {
                    this.print("::")
                }
                val dis1 =
                    run {
                        val matchVal11 = this.parser
                        when (matchVal11) { is Result.Ok<*, *> -> {
                            val parser5 = matchVal11.value
                            run {
                                val matchVal12 = parser5.disambiguator()
                                when (matchVal12) { is Result.Ok<*, *> -> {
                                    val x5 = matchVal12.value
                                    x5
                                }; is Result.Err<*, *> -> {
                                    val err5 = matchVal12.error
                                    this.print(err5.message())
                                    this.parser = Result.Err(err5)
                                    return Unit
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                val name1 =
                    run {
                        val matchVal13 = this.parser
                        when (matchVal13) { is Result.Ok<*, *> -> {
                            val parser6 = matchVal13.value
                            run {
                                val matchVal14 = parser6.ident()
                                when (matchVal14) { is Result.Ok<*, *> -> {
                                    val x6 = matchVal14.value
                                    x6
                                }; is Result.Err<*, *> -> {
                                    val err6 = matchVal14.error
                                    this.print(err6.message())
                                    this.parser = Result.Err(err6)
                                    return Unit
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                when (ns) {
                    // Implementation-specific/unspecified namespaces.
                    null -> {
                        if (!name1.ascii.isEmpty() || !name1.punycode.isEmpty()) {
                            this.print("::")
                            this.print(name1)
                        }
                    }
                    // Special namespaces, like closures and shims.
                    else -> {
                        val ns1 = ns
                        this.print("::{")
                        when (ns1) {
                            'C' -> this.print("closure")
                            'S' -> this.print("shim")
                            else -> this.print(ns1)
                        }
                        if (!name1.ascii.isEmpty() || !name1.punycode.isEmpty()) {
                            this.print(":")
                            this.print(name1)
                        }
                        this.print("#")
                        this.print(dis1)
                        this.print("}")
                    }
                }
            }
            77u, 88u, 89u -> {
                if (tag != 89u) {
                    run {
                        val matchVal15 = this.parser
                        when (matchVal15) { is Result.Ok<*, *> -> {
                            val parser7 = matchVal15.value
                            run {
                                val matchVal16 = parser7.disambiguator()
                                when (matchVal16) { is Result.Ok<*, *> -> {
                                    val x7 = matchVal16.value
                                    x7
                                }; is Result.Err<*, *> -> {
                                    val err7 = matchVal16.error
                                    this.print(err7.message())
                                    this.parser = Result.Err(err7)
                                    return Unit
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                    this.skippingPrinting({ thisRef -> thisRef.printPath(false) })
                }
                this.print("<")
                this.printType()
                if (tag != 77u) {
                    this.print(" as ")
                    this.printPath(false)
                }
                this.print(">")
            }
            73u -> {
                this.printPath(inValue)
                if (inValue) {
                    this.print("::")
                }
                this.print("<")
                this.printSepList({ it.printGenericArg() }, ", ")
                this.print(">")
            }
            66u -> {
                this.printBackref({ thisRef -> thisRef.printPath(inValue) })
            }
            else -> {
                val err8 = ParseError.Invalid
                this.print(err8.message())
                this.parser = Result.Err(err8)
                return Unit
            }
        }
        this.popDepth()
    }

    public open fun printGenericArg() {
        if (this.eat(76u)) {
            val lt =
                run {
                    val matchVal = this.parser
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val parser = matchVal.value
                        run {
                            val matchVal1 = parser.integer62()
                            when (matchVal1) { is Result.Ok<*, *> -> {
                                val x = matchVal1.value
                                x
                            }; is Result.Err<*, *> -> {
                                val err = matchVal1.error
                                this.print(err.message())
                                this.parser = Result.Err(err)
                                return Unit
                            } }
                        }
                    }; is Result.Err<*, *> -> {
                        return this.print("?")
                    } }
                }
            this.printLifetimeFromIndex(lt)
        } else if (this.eat(75u)) {
            this.printConst(false)
        } else {
            this.printType()
        }
    }

    public open fun printType() {
        val tag =
            run {
                val matchVal = this.parser
                when (matchVal) { is Result.Ok<*, *> -> {
                    val parser = matchVal.value
                    run {
                        val matchVal1 = parser.next()
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            val x = matchVal1.value
                            x
                        }; is Result.Err<*, *> -> {
                            val err = matchVal1.error
                            this.print(err.message())
                            this.parser = Result.Err(err)
                            return Unit
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }
        when (val matchVal2 = basicType(tag)) {
            null -> {}
            else -> {
                val ty = matchVal2
                return this.print(ty)
            }
        }
        run {
            val matchVal3 = this.parser
            when (matchVal3) { is Result.Ok<*, *> -> {
                val parser1 = matchVal3.value
                run {
                    val matchVal4 = parser1.pushDepth()
                    when (matchVal4) { is Result.Ok<*, *> -> {
                        val x1 = matchVal4.value
                        x1
                    }; is Result.Err<*, *> -> {
                        val err1 = matchVal4.error
                        this.print(err1.message())
                        this.parser = Result.Err(err1)
                        return Unit
                    } }
                }
            }; is Result.Err<*, *> -> {
                return this.print("?")
            } }
        }
        when (tag) {
            82u, 81u -> {
                this.print("&")
                if (this.eat(76u)) {
                    val lt =
                        run {
                            val matchVal5 = this.parser
                            when (matchVal5) { is Result.Ok<*, *> -> {
                                val parser2 = matchVal5.value
                                run {
                                    val matchVal6 = parser2.integer62()
                                    when (matchVal6) { is Result.Ok<*, *> -> {
                                        val x2 = matchVal6.value
                                        x2
                                    }; is Result.Err<*, *> -> {
                                        val err2 = matchVal6.error
                                        this.print(err2.message())
                                        this.parser = Result.Err(err2)
                                        return Unit
                                    } }
                                }
                            }; is Result.Err<*, *> -> {
                                return this.print("?")
                            } }
                        }
                    if (lt != 0) {
                        this.printLifetimeFromIndex(lt)
                        this.print(" ")
                    }
                }
                if (tag != 82u) {
                    this.print("mut ")
                }
                this.printType()
            }
            80u, 79u -> {
                this.print("*")
                if (tag != 80u) {
                    this.print("mut ")
                } else {
                    this.print("const ")
                }
                this.printType()
            }
            65u, 83u -> {
                this.print("[")
                this.printType()
                if (tag == 65u) {
                    this.print("; ")
                    this.printConst(true)
                }
                this.print("]")
            }
            84u -> {
                this.print("(")
                val count = this.printSepList({ it.printType() }, ", ")
                if (count == 1) {
                    this.print(",")
                }
                this.print(")")
            }
            70u -> this.inBinder(closure4@{ thisRef ->
                val isUnsafe = thisRef.eat(85u)
                val abi1 =
                    if (thisRef.eat(75u)) {
                        if (thisRef.eat(67u)) {
                            "C"
                        } else {
                            val abi =
                                run {
                                    val matchVal7 = thisRef.parser
                                    when (matchVal7) { is Result.Ok<*, *> -> {
                                        val parser3 = matchVal7.value
                                        run {
                                            val matchVal8 = parser3.ident()
                                            when (matchVal8) { is Result.Ok<*, *> -> {
                                                val x3 = matchVal8.value
                                                x3
                                            }; is Result.Err<*, *> -> {
                                                val err3 = matchVal8.error
                                                when (val tryResult = thisRef.print(err3.message())) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                                                thisRef.parser = Result.Err(err3)
                                                return@closure4 Result.Ok(Unit)
                                            } }
                                        }
                                    }; is Result.Err<*, *> -> {
                                        return@closure4 thisRef.print("?")
                                    } }
                                }
                            if (abi.ascii.isEmpty() || !abi.punycode.isEmpty()) {
                                run {
                                    val err4 = ParseError.Invalid
                                    when (val tryResult = thisRef.print(err4.message())) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                                    thisRef.parser = Result.Err(err4)
                                    return@closure4 Result.Ok(Unit)
                                }
                            }
                            abi.ascii
                        }
                    } else {
                        null
                    }
                if (isUnsafe) {
                    when (val tryResult = thisRef.print("unsafe ")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                }
                if (abi1 != null) {
                    val abi2 = abi1
                    when (val tryResult = thisRef.print("extern \"")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                    // If the ABI had any `-`, they were replaced with `_`,
                    // so the parts between `_` have to be re-joined with `-`.
                    val parts = abi2.split('_')
                    when (val tryResult = thisRef.print(parts.next()!!)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                    for (part in parts) {
                        when (val tryResult = thisRef.print("-")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                        when (val tryResult = thisRef.print(part)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                    }
                    when (val tryResult = thisRef.print("\" ")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                }
                when (val tryResult = thisRef.print("fn(")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                when (val tryResult = thisRef.printSepList({ it.printType() }, ", ")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                when (val tryResult = thisRef.print(")")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                if (thisRef.eat(117u)) {
                    // Skip printing the return type if it's 'u', i.e. `()`.
                } else {
                    when (val tryResult = thisRef.print(" -> ")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                    when (val tryResult = thisRef.printType()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.error) }
                }
                Result.Ok(Unit)
            })
            68u -> {
                this.print("dyn ")
                this.inBinder(closure5@{ thisRef ->
                    when (val tryResult = thisRef.printSepList({ it.printDynTrait() }, " + ")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure5 Result.Err(tryResult.error) }
                    Result.Ok(Unit)
                })
                if (!this.eat(76u)) {
                    run {
                        val err3 = ParseError.Invalid
                        this.print(err3.message())
                        this.parser = Result.Err(err3)
                        return Unit
                    }
                }
                val lt1 =
                    run {
                        val matchVal7 = this.parser
                        when (matchVal7) { is Result.Ok<*, *> -> {
                            val parser3 = matchVal7.value
                            run {
                                val matchVal8 = parser3.integer62()
                                when (matchVal8) { is Result.Ok<*, *> -> {
                                    val x3 = matchVal8.value
                                    x3
                                }; is Result.Err<*, *> -> {
                                    val err4 = matchVal8.error
                                    this.print(err4.message())
                                    this.parser = Result.Err(err4)
                                    return Unit
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                if (lt1 != 0) {
                    this.print(" + ")
                    this.printLifetimeFromIndex(lt1)
                }
            }
            66u -> {
                this.printBackref({ it.printType() })
            }
            else -> {
                // Go back to the tag, so `printPath` also sees it.
                this.parser.asMut().map({ p -> p.next -= 1 })
                this.printPath(false)
            }
        }
        this.popDepth()
    }

    /**
     * A trait in a trait object may have some "existential projections"
     * (i.e. associated type bindings) after it, which should be printed
     * in the `<...>` of the trait, e.g. `dynamic Trait<T, U, Assoc = X>`.
     * To this end, this method will keep the `<...>` of an 'I' path
     * open, by omitting the `>`, and return `Ok(true)` in that case.
     */
    public open fun printPathMaybeOpenGenerics(): Result<Boolean, Unit> {
        return if (this.eat(66u)) {
            // NOTE(eddyb) the closure may not run if printing is being skipped,
            // but in that case the returned boolean doesn't matter.
            var open = false
            when (val tryResult = this.printBackref(closure6@{ thisRef ->
                open = when (val tryResult = thisRef.printPathMaybeOpenGenerics()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure6 Result.Err(tryResult.error) }
                Result.Ok(Unit)
            })) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            Result.Ok(open)
        } else if (this.eat(73u)) {
            this.printPath(false)
            this.print("<")
            when (val tryResult = this.printSepList({ it.printGenericArg() }, ", ")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            Result.Ok(true)
        } else {
            this.printPath(false)
            Result.Ok(false)
        }
    }

    public open fun printDynTrait() {
        var open = this.printPathMaybeOpenGenerics()
        while (this.eat(112u)) {
            if (!open) {
                this.print("<")
                open = true
            } else {
                this.print(", ")
            }
            val name =
                run {
                    val matchVal = this.parser
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val parser = matchVal.value
                        run {
                            val matchVal1 = parser.ident()
                            when (matchVal1) { is Result.Ok<*, *> -> {
                                val x = matchVal1.value
                                x
                            }; is Result.Err<*, *> -> {
                                val err = matchVal1.error
                                this.print(err.message())
                                this.parser = Result.Err(err)
                                return Unit
                            } }
                        }
                    }; is Result.Err<*, *> -> {
                        return this.print("?")
                    } }
                }
            this.print(name)
            this.print(" = ")
            this.printType()
        }
        if (open) {
            this.print(">")
        }
    }

    public open fun printConst(inValue: Boolean) {
        val tag =
            run {
                val matchVal = this.parser
                when (matchVal) { is Result.Ok<*, *> -> {
                    val parser = matchVal.value
                    run {
                        val matchVal1 = parser.next()
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            val x = matchVal1.value
                            x
                        }; is Result.Err<*, *> -> {
                            val err = matchVal1.error
                            this.print(err.message())
                            this.parser = Result.Err(err)
                            return Unit
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }
        run {
            val matchVal2 = this.parser
            when (matchVal2) { is Result.Ok<*, *> -> {
                val parser1 = matchVal2.value
                run {
                    val matchVal3 = parser1.pushDepth()
                    when (matchVal3) { is Result.Ok<*, *> -> {
                        val x1 = matchVal3.value
                        x1
                    }; is Result.Err<*, *> -> {
                        val err1 = matchVal3.error
                        this.print(err1.message())
                        this.parser = Result.Err(err1)
                        return Unit
                    } }
                }
            }; is Result.Err<*, *> -> {
                return this.print("?")
            } }
        }
        // Only literals (and the names of `const` generic parameters, but they
        // don't get mangled at all), can appear in generic argument position
        // without any disambiguation, all other expressions require braces.
        // To avoid duplicating the mapping between `tag` and what syntax gets
        // used (especially any special-casing), every case that needs braces
        // has to call `openBrace(self)` (and the closing brace is automatic).
        var openedBrace = false
        var openBraceIfOutsideExpr =
            closure7@{ thisRef: Printer ->
                // If this expression is nested in another, braces aren't required.
                if (inValue) {
                    return@closure7 Result.Ok(Unit)
                }
                openedBrace = true
                thisRef.print("{")
            }
        when (tag) {
            112u -> this.print("_")
            // Primitive leaves with hex-encoded values (see `basicType`).
            104u, 116u, 109u, 121u, 111u, 106u -> this.printConstUint(tag)
            97u, 115u, 108u, 120u, 110u, 105u -> {
                if (this.eat(110u)) {
                    this.print("-")
                }
                this.printConstUint(tag)
            }
            98u -> when (val matchVal6 = run {
                val matchVal4 = this.parser
                when (matchVal4) { is Result.Ok<*, *> -> {
                    val parser2 = matchVal4.value
                    run {
                        val matchVal5 = parser2.hexNibbles()
                        when (matchVal5) { is Result.Ok<*, *> -> {
                            val x2 = matchVal5.value
                            x2
                        }; is Result.Err<*, *> -> {
                            val err2 = matchVal5.error
                            this.print(err2.message())
                            this.parser = Result.Err(err2)
                            return Unit
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }.tryParseUint()) {
                0 -> this.print("false")
                1 -> this.print("true")
                else -> {
                    val err3 = ParseError.Invalid
                    this.print(err3.message())
                    this.parser = Result.Err(err3)
                    return Unit
                }
            }
            99u -> {
                val validChar =
                    run {
                        val matchVal7 = this.parser
                        when (matchVal7) { is Result.Ok<*, *> -> {
                            val parser3 = matchVal7.value
                            run {
                                val matchVal8 = parser3.hexNibbles()
                                when (matchVal8) { is Result.Ok<*, *> -> {
                                    val x3 = matchVal8.value
                                    x3
                                }; is Result.Err<*, *> -> {
                                    val err4 = matchVal8.error
                                    this.print(err4.message())
                                    this.parser = Result.Err(err4)
                                    return Unit
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }.tryParseUint().andThen({ v -> UInt.tryFrom(v).ok() }).andThen(Char::fromU32)
                when (validChar) {
                    null -> {
                        val err5 = ParseError.Invalid
                        this.print(err5.message())
                        this.parser = Result.Err(err5)
                        return Unit
                    }
                    else -> {
                        val c = validChar
                        this.printQuotedEscapedChars('\'', listOf(c))
                    }
                }
            }
            101u -> {
                // NOTE(eddyb) a string literal `"..."` has type `String`, so
                // to get back the type `String`, `* "..."` syntax is needed
                // (even if that may not be valid in Rust itself).
                openBraceIfOutsideExpr(this)
                this.print("*")
                this.printConstStrLiteral()
            }
            82u, 81u -> {
                // NOTE(eddyb) this prints `"..."` instead of `* "..."`, which
                // is what `Re ... _` would imply (see comment for `String` above).
                if (tag == 82u && this.eat(101u)) {
                    this.printConstStrLiteral()
                } else {
                    openBraceIfOutsideExpr(this)
                    this.print("&")
                    if (tag != 82u) {
                        this.print("mut ")
                    }
                    this.printConst(true)
                }
            }
            65u -> {
                openBraceIfOutsideExpr(this)
                this.print("[")
                this.printSepList({ thisRef -> thisRef.printConst(true) }, ", ")
                this.print("]")
            }
            84u -> {
                openBraceIfOutsideExpr(this)
                this.print("(")
                val count = this.printSepList({ thisRef -> thisRef.printConst(true) }, ", ")
                if (count == 1) {
                    this.print(",")
                }
                this.print(")")
            }
            86u -> {
                openBraceIfOutsideExpr(this)
                this.printPath(true)
                when (run {
                    val matchVal9 = this.parser
                    when (matchVal9) { is Result.Ok<*, *> -> {
                        val parser4 = matchVal9.value
                        run {
                            val matchVal10 = parser4.next()
                            when (matchVal10) { is Result.Ok<*, *> -> {
                                val x4 = matchVal10.value
                                x4
                            }; is Result.Err<*, *> -> {
                                val err6 = matchVal10.error
                                this.print(err6.message())
                                this.parser = Result.Err(err6)
                                return Unit
                            } }
                        }
                    }; is Result.Err<*, *> -> {
                        return this.print("?")
                    } }
                }) {
                    85u -> {}
                    84u -> {
                        this.print("(")
                        this.printSepList({ thisRef -> thisRef.printConst(true) }, ", ")
                        this.print(")")
                    }
                    83u -> {
                        this.print(" { ")
                        this.printSepList(closure8@{ thisRef ->
                            run {
                                val matchVal11 = thisRef.parser
                                when (matchVal11) { is Result.Ok<*, *> -> {
                                    val parser5 = matchVal11.value
                                    run {
                                        val matchVal12 = parser5.disambiguator()
                                        when (matchVal12) { is Result.Ok<*, *> -> {
                                            val x5 = matchVal12.value
                                            x5
                                        }; is Result.Err<*, *> -> {
                                            val err7 = matchVal12.error
                                            when (val tryResult = thisRef.print(err7.message())) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.error) }
                                            thisRef.parser = Result.Err(err7)
                                            return@closure8 Result.Ok(Unit)
                                        } }
                                    }
                                }; is Result.Err<*, *> -> {
                                    return@closure8 thisRef.print("?")
                                } }
                            }
                            val name =
                                run {
                                    val matchVal13 = thisRef.parser
                                    when (matchVal13) { is Result.Ok<*, *> -> {
                                        val parser6 = matchVal13.value
                                        run {
                                            val matchVal14 = parser6.ident()
                                            when (matchVal14) { is Result.Ok<*, *> -> {
                                                val x6 = matchVal14.value
                                                x6
                                            }; is Result.Err<*, *> -> {
                                                val err8 = matchVal14.error
                                                when (val tryResult = thisRef.print(err8.message())) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.error) }
                                                thisRef.parser = Result.Err(err8)
                                                return@closure8 Result.Ok(Unit)
                                            } }
                                        }
                                    }; is Result.Err<*, *> -> {
                                        return@closure8 thisRef.print("?")
                                    } }
                                }
                            when (val tryResult = thisRef.print(name)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.error) }
                            when (val tryResult = thisRef.print(": ")) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.error) }
                            thisRef.printConst(true)
                        }, ", ")
                        this.print(" }")
                    }
                    else -> {
                        val err7 = ParseError.Invalid
                        this.print(err7.message())
                        this.parser = Result.Err(err7)
                        return Unit
                    }
                }
            }
            66u -> {
                this.printBackref({ thisRef -> thisRef.printConst(inValue) })
            }
            else -> {
                val err8 = ParseError.Invalid
                this.print(err8.message())
                this.parser = Result.Err(err8)
                return Unit
            }
        }
        if (openedBrace) {
            this.print("}")
        }
        this.popDepth()
    }

    public open fun printConstUint(tyTag: UByte) {
        val hex =
            run {
                val matchVal = this.parser
                when (matchVal) { is Result.Ok<*, *> -> {
                    val parser = matchVal.value
                    run {
                        val matchVal1 = parser.hexNibbles()
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            val x = matchVal1.value
                            x
                        }; is Result.Err<*, *> -> {
                            val err = matchVal1.error
                            this.print(err.message())
                            this.parser = Result.Err(err)
                            return Unit
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }
        when (val matchVal2 = hex.tryParseUint()) {
            // Print anything that doesn't fit in `ULong` verbatim.
            null -> {
                this.print("0x")
                this.print(hex.nibbles)
            }
            else -> {
                val v = matchVal2
                this.print(v)
            }
        }
        when (val matchVal3 = this.out) {
            null -> {}
            else -> {
                val out = matchVal3
                if (!out.alternate()) {
                    val ty = basicType(tyTag)!!
                    this.print(ty)
                }
            }
        }
    }

    public open fun printConstStrLiteral() {
        when (val matchVal2 = run {
            val matchVal = this.parser
            when (matchVal) { is Result.Ok<*, *> -> {
                val parser = matchVal.value
                run {
                    val matchVal1 = parser.hexNibbles()
                    when (matchVal1) { is Result.Ok<*, *> -> {
                        val x = matchVal1.value
                        x
                    }; is Result.Err<*, *> -> {
                        val err = matchVal1.error
                        this.print(err.message())
                        this.parser = Result.Err(err)
                        return Unit
                    } }
                }
            }; is Result.Err<*, *> -> {
                return this.print("?")
            } }
        }.tryParseStrChars()) {
            null -> {
                val err1 = ParseError.Invalid
                this.print(err1.message())
                this.parser = Result.Err(err1)
                return Unit
            }
            else -> {
                val chars = matchVal2
                this.printQuotedEscapedChars('"', chars)
            }
        }
    }

    public companion object {
        public operator fun invoke(
            parser: Result<Parser, ParseError>,
            out: StringBuilder?,
            boundLifetimeDepth: UInt,
        ): Printer = Printer(
            parserCtorArg = parser,
            out = out,
            boundLifetimeDepth = boundLifetimeDepth,
        )
    }
}
