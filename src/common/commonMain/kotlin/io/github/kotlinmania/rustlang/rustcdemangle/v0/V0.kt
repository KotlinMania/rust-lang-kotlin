// port-lint: source v0.rs
package io.github.kotlinmania.rustlang.rustcdemangle.v0

import io.github.kotlinmania.rustlang.core.char.methods.toDigit
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.convert.num.tryFrom
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.num.checkedSub
import io.github.kotlinmania.rustlang.core.num.saturatingSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.chunksExact
import io.github.kotlinmania.rustlang.core.str.chars
import kotlin.experimental.and
import kotlin.experimental.or

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
    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        val printer = Printer(parser = Result.Ok<Parser, ParseError>(Parser(sym = this.inner, next = 0, depth = 0u)), out = f, boundLifetimeDepth = 0u)
        return printer.printPath(true)
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

    private fun kmDiscriminant(): Int =
        when (this) {
        Invalid -> 0
        RecursedTooDeep -> 1
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

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
        return Result.Err<Pair<Demangle, String>, ParseError>(ParseError.Invalid)
    }
    // Paths always start with uppercase characters.
    when (inner.encodeToByteArray().map { it.toUByte() }[0]) {
        in 65u..90u -> {}
        else -> { return Result.Err<Pair<Demangle, String>, ParseError>(ParseError.Invalid) }
    }
    // only work with ascii text
    if (inner.bytes().any({ c -> c and 0x80 != 0 })) {
        return Result.Err<Pair<Demangle, String>, ParseError>(ParseError.Invalid)
    }
    // Verify that the symbol is indeed a valid path.
    val tryParsePath =
        { parser ->
            var dummyPrinter = Printer(parser = Result.Ok<Parser, ParseError>(parser), out = null, boundLifetimeDepth = 0u)
            dummyPrinter.printPath(false).expect("`fmt::Error`s should be impossible without a `fmt::Formatter`")
            dummyPrinter.parser
        }
    var parser = Parser(sym = inner, next = 0, depth = 0u)
    parser = when (val tryResult = tryParsePath(parser)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Demangle, String>, ParseError>(tryResult.errValue()) }
    // Instantiating crate (paths always start with uppercase characters).
    when (val matchVal = parser.sym.encodeToByteArray().map { it.toUByte() }.get(parser.next)) {
        is Some<*> -> {
            parser = when (val tryResult = tryParsePath(parser)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Pair<Demangle, String>, ParseError>(tryResult.errValue()) }
        }
        else -> {}
    }
    return Result.Ok<Pair<Demangle, String>, ParseError>(Pair(Demangle(inner = inner), parser.sym.substring(parser.next)))
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
                when (val tryResult = (out.getOrNull(outLen)?.let { Result.Ok(it) } ?: Result.Err(Unit))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()) }
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
            return Result.Err<Unit, Unit>(Unit)
        }
        var len = 0
        // Populate initial output from ASCII fragment.
        for (c in this.ascii.chars()) {
            when (val tryResult = insert(len, c)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
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
                val t = minOf(max(k.saturatingSub(bias.toInt()), tMin), tMax)
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
                        else -> { return Result.Err<Unit, Unit>(Unit) }
                    }
                val d3 = d2.toInt()
                delta = when (val tryResult = (delta.checkedAdd((when (val tryResult = (d3.checkedMul(w.toInt())?.let { Result.Ok<Int, Unit>(it) } ?: Result.Err<Int, Unit>(Unit))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }).toInt())?.let { Result.Ok<Int, Unit>(it) } ?: Result.Err<Int, Unit>(Unit))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
                if (d3 < t) {
                    break
                }
                w = when (val tryResult = (w.checkedMul(base - t)?.let { Result.Ok<Unit, Unit>(it) } ?: Result.Err<Unit, Unit>(Unit))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
            }
            // Compute the new insert position and character.
            len += 1
            i = when (val tryResult = (i.checkedAdd(delta)?.let { Result.Ok<Int, Unit>(it) } ?: Result.Err<Int, Unit>(Unit))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
            n = when (val tryResult = (n.checkedAdd(i / len)?.let { Result.Ok<Int, Unit>(it) } ?: Result.Err<Int, Unit>(Unit))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
            i %= len
            val nU32 = n.toUInt()
            val c1 =
                if (nU32.toInt() == n) {
                    when (val tryResult = Char.fromU32(nU32).okOr(Unit)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
                } else {
                    return Result.Err<Unit, Unit>(Unit)
                }
            // Insert the new character and increment the insert position.
            when (val tryResult = insert(i, c1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
            i += 1
            // If there are no more deltas, decoding is complete.
            if (punycodeBytes.peek() == null) {
                return Result.Ok<Unit, Unit>(Unit)
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

    public open fun fmt(f: StringBuilder): Result<Unit, Error> {
        return this.trySmallPunycodeDecode(closure1@{ chars ->
            for (c in chars) {
                when (val tryResult = c.fmt(f)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure1 Result.Err(tryResult.errValue()) }
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
                val half = { nibble: UByte -> (nibble.toInt().toChar()).toDigit(16u)!!.toUByte() }
                (half(hi) shl 4) or half(lo)
            })
        val chars =
            Iter.fromFn({
                // As long as there are any bytes left, there's at least one more
                // UTF-8-encoded `char` to decode (or the possibility of error).
                bytes.next()?.let(closure3@{ firstByte ->
                    // Note(eddyb) this `sealed class` and `fun` should be somewhere in `core`.
                    sealed class Utf8FirstByteError {
                        public data object ContinuationByte : Utf8FirstByteError()
                        public data object TooLong : Utf8FirstByteError()
    
                        private fun kmDiscriminant(): Int =
                            when (this) {
                            ContinuationByte -> 0
                            TooLong -> 1
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
                    fun utf8LenFromFirstByte(byte: UByte): Result<Int, Utf8FirstByteError> =
                        when (byte) {
                            in 0x00u.toUByte()..0x7fu.toUByte() -> { Result.Ok<Int, Utf8FirstByteError>(1) }
                            in 0x80u.toUByte()..0xbfu.toUByte() -> { Result.Err<Int, Utf8FirstByteError>(Utf8FirstByteError.ContinuationByte) }
                            in 0xc0u.toUByte()..0xdfu.toUByte() -> { Result.Ok<Int, Utf8FirstByteError>(2) }
                            in 0xe0u.toUByte()..0xefu.toUByte() -> { Result.Ok<Int, Utf8FirstByteError>(3) }
                            in 0xf0u.toUByte()..0xf7u.toUByte() -> { Result.Ok<Int, Utf8FirstByteError>(4) }
                            in 0xf8u.toUByte()..0xffu.toUByte() -> Result.Err<Int, Utf8FirstByteError>(Utf8FirstByteError.TooLong)
                        }
                    // Collect the appropriate amount of bytes (up to 4), according
                    // to the UTF-8 length implied by the first byte.
                    val utf8Len = when (val tryResult = utf8LenFromFirstByte(firstByte).mapErr({ Unit })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err<Char, Unit>(tryResult.errValue()) }
                    val utf8 = listOf(firstByte, 0, 0, 0)[0 until utf8Len]
                    for (i in 1 until utf8Len) {
                        utf8[i] = when (val tryResult = (bytes.next()?.let { Result.Ok<Char, Unit>(it) } ?: Result.Err<Char, Unit>(Unit))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err<Char, Unit>(tryResult.errValue()) }
                    }
                    // Fully validate the UTF-8 sequence.
                    val s = when (val tryResult = String.fromUtf8(utf8).mapErr({ Unit })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure3 Result.Err<Char, Unit>(tryResult.errValue()) }
                    // Since we included exactly one UTF-8 sequence, and validation
                    // succeeded, `str.chars` should return exactly one `char`.
                    var chars = s.chars()
                    run {
                        val matchVal = Pair(chars.next(), chars.next())
                        when {
                            matchVal.first != null && matchVal.second == null -> {
                                val c = matchVal.first
                                Result.Ok<Char, Unit>(c)
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
            Result.Err<Unit, ParseError>(ParseError.RecursedTooDeep)
        } else {
            Result.Ok<Unit, ParseError>(Unit)
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
        val b = when (val tryResult = (this.peek()?.let { Result.Ok<UByte, ParseError>(it) } ?: Result.Err<UByte, ParseError>(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<UByte, ParseError>(tryResult.errValue()) }
        this.next += 1
        return Result.Ok<UByte, ParseError>(b)
    }

    public open fun hexNibbles(): Result<HexNibbles, ParseError> {
        val start = this.next
        while (true) {
            when (when (val tryResult = this.next()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<HexNibbles, ParseError>(tryResult.errValue()) }) {
                in 48u..57u, in 97u..102u -> {}
                95u -> { break }
                else -> { return Result.Err<HexNibbles, ParseError>(ParseError.Invalid) }
            }
        }
        return Result.Ok<HexNibbles, ParseError>(HexNibbles(nibbles = this.sym.substring(start until this.next - 1)))
    }

    public open fun digit10(): Result<UByte, ParseError> {
        val d1 =
            when (val matchVal = this.peek()) {
                in 48u..57u -> {
                    val d = matchVal
                    d - 48u
                }
                else -> { return Result.Err<UByte, ParseError>(ParseError.Invalid) }
            }
        this.next += 1
        return Result.Ok<UByte, ParseError>(d1)
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
                else -> { return Result.Err<UByte, ParseError>(ParseError.Invalid) }
            }
        this.next += 1
        return Result.Ok<UByte, ParseError>(d3)
    }

    public open fun integer62(): Result<ULong, ParseError> {
        if (this.eat(95u)) {
            return Result.Ok<ULong, ParseError>(0uL)
        }
        var x: ULong = 0uL
        while (!this.eat(95u)) {
            val d = (when (val tryResult = this.digit62()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ULong, ParseError>(tryResult.errValue()) }).toULong()
            x = when (val tryResult = (x.checkedMul(62uL)?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ULong, ParseError>(tryResult.errValue()) }
            x = when (val tryResult = (x.checkedAdd(d)?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ULong, ParseError>(tryResult.errValue()) }
        }
        return (x.checkedAdd(1uL)?.let { Result.Ok<ULong, ParseError>(it) } ?: Result.Err<ULong, ParseError>(ParseError.Invalid))
    }

    public open fun optInteger62(tag: UByte): Result<ULong, ParseError> {
        if (!this.eat(tag)) {
            return Result.Ok<ULong, ParseError>(0uL)
        }
        return ((when (val tryResult = this.integer62()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ULong, ParseError>(tryResult.errValue()) }.checkedAdd(1))?.let { Result.Ok<ULong, ParseError>(it) } ?: Result.Err<ULong, ParseError>(ParseError.Invalid))
    }

    public open fun disambiguator(): Result<ULong, ParseError> = this.optInteger62(115u)

    public open fun namespace(): Result<Char?, ParseError> {
        return when (val matchVal = when (val tryResult = this.next()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Char?, ParseError>(tryResult.errValue()) }) {
            // Special namespaces, like closures and shims.
            in 65u..90u -> {
                val ns = matchVal
                Result.Ok<Char?, ParseError>(ns.toInt().toChar())
            }
            // Implementation-specific/unspecified namespaces.
            in 97u..122u -> Result.Ok<Char?, ParseError>(null)
            else -> Result.Err<Char?, ParseError>(ParseError.Invalid)
        }
    }

    public open fun backref(): Result<Parser, ParseError> {
        val sStart = this.next - 1
        val i = when (val tryResult = this.integer62()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Parser, ParseError>(tryResult.errValue()) }
        if (i >= sStart.toULong()) {
            return Result.Err<Parser, ParseError>(ParseError.Invalid)
        }
        val newParser = Parser(sym = this.sym, next = i.toInt(), depth = this.depth)
        when (val tryResult = newParser.pushDepth()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Parser, ParseError>(tryResult.errValue()) }
        return Result.Ok<Parser, ParseError>(newParser)
    }

    public open fun ident(): Result<Ident, ParseError> {
        val isPunycode = this.eat(117u)
        var len = (when (val tryResult = this.digit10()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Ident, ParseError>(tryResult.errValue()) }).toInt()
        if (len != 0) {
            while (true) {
                val matchVal = this.digit10()
                if (matchVal !is Result.Ok<*, *>) break
                val d = (matchVal as Result.Ok<*, *>).okValue()
                len = when (val tryResult = (len.checkedMul(10)?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Ident, ParseError>(tryResult.errValue()) }
                len = when (val tryResult = (len.checkedAdd(d.toInt())?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Ident, ParseError>(tryResult.errValue()) }
            }
        }
        // Skip past the optional `_` separator.
        this.eat(95u)
        val start = this.next
        this.next = when (val tryResult = (this.next.checkedAdd(len)?.let { Result.Ok(it) } ?: Result.Err(ParseError.Invalid))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Ident, ParseError>(tryResult.errValue()) }
        if (this.next > this.sym.length) {
            return Result.Err<Ident, ParseError>(ParseError.Invalid)
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
                return Result.Err<Ident, ParseError>(ParseError.Invalid)
            }
            Result.Ok<Ident, ParseError>(ident1)
        } else {
            Result.Ok<Ident, ParseError>(Ident(ascii = ident, punycode = ""))
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
     * Cumulative number of scopes bound by `for<...>` binders (`G`),
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
    public open fun skippingPrinting(f: (Printer) -> Result<Unit, Error>) {
        val origOut = this.out.also { this.out = null }
        f(this).expect("`fmt::Error`s should be impossible without a `fmt::Formatter`")
        this.out = origOut
    }

    /**
     * Print the target of a backref, using the given closure.
     * When printing is being skipped, the backref will only be parsed,
     * ignoring the backref's target completely.
     */
    public open fun printBackref(f: (Printer) -> Result<Unit, Error>): Result<Unit, Error> {
        val backrefParser =
            run {
                val matchVal = this.parser
                when (matchVal) { is Result.Ok<*, *> -> {
                    val parser = matchVal.okValue()
                    run {
                        val matchVal1 = parser.backref()
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            val x = matchVal1.okValue()
                            x
                        }; is Result.Err<*, *> -> {
                            val err = matchVal1.errValue()
                            when (val tryResult = this.print(err.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            this.parser = Result.Err<Parser, ParseError>(err)
                            return Result.Ok<Unit, Error>(Unit)
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }
        if (this.out == null) {
            return Result.Ok<Unit, Error>(Unit)
        }
        val origParser = replace(this.parser, Result.Ok<Unit, Error>(backrefParser))
        val r = f(this)
        this.parser = origParser
        return r
    }

    public open fun popDepth() {
        when (val matchVal = this.parser) {
            is Result.Ok<*, *> -> {
                val parser = matchVal.okValue()
                parser.popDepth()
            }
            else -> {}
        }
    }

    /**
     * Output the given value to `self.out` (using `fmt.Display` formatting),
     * if printing isn't being skipped.
     */
    public open fun print(x: Any): Result<Unit, Error> {
        when (val matchVal = this.out) {
            null -> {}
            else -> {
                val out = matchVal
                out.append((x).toString())
            }
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    /**
     * Output the given `char`s (escaped using `char.escapeDebug`), with the
     * whole sequence wrapped in quotes, for either a `char` or `String` literal,
     * if printing isn't being skipped.
     */
    public open fun printQuotedEscapedChars(quote: Char, chars: Any): Result<Unit, Error> {
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
        return Result.Ok<Unit, Error>(Unit)
    }

    /**
     * Print the scope according to the previously decoded index.
     * An index of `0` always refers to `_`, but starting with `1`,
     * indices refer to late-bound scopes introduced by a binder.
     */
    public open fun printLifetimeFromIndex(lt: ULong): Result<Unit, Error> {
        // Bound scopes aren't tracked when skipping printing.
        if (this.out == null) {
            return Result.Ok<Unit, Error>(Unit)
        }
        when (val tryResult = this.print("'")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        if (lt == 0uL) {
            return this.print("_")
        }
        return when (val matchVal = (this.boundLifetimeDepth.toULong()).checkedSub(lt)) {
            null -> {
                val err = ParseError.Invalid
                when (val tryResult = this.print(err.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                this.parser = Result.Err<Parser, ParseError>(err)
                return Result.Ok<Unit, Error>(Unit)
            }
            else -> {
                val depth = matchVal
                // Try to print scopes alphabetically first.
                if (depth < 26uL) {
                    val c = (97u + depth.toUByte()).toInt().toChar()
                    this.print(c)
                } else {
                    // Use `_123` after running out of letters.
                    when (val tryResult = this.print("_")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    this.print(depth)
                }
            }
        }
    }

    /**
     * Optionally enter a binder (`G`) for late-bound scopes,
     * printing e.g. `for<>` before calling the closure,
     * and make those scopes visible to it (via depth level).
     */
    public open fun inBinder(f: (Printer) -> Result<Unit, Error>): Result<Unit, Error> {
        val boundLifetimes =
            run {
                val matchVal = this.parser
                when (matchVal) { is Result.Ok<*, *> -> {
                    val parser = matchVal.okValue()
                    run {
                        val matchVal1 = parser.optInteger62(71u)
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            val x = matchVal1.okValue()
                            x
                        }; is Result.Err<*, *> -> {
                            val err = matchVal1.errValue()
                            when (val tryResult = this.print(err.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            this.parser = Result.Err<Parser, ParseError>(err)
                            return Result.Ok<Unit, Error>(Unit)
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
            when (val tryResult = this.print("for<")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            for (i in 0 until boundLifetimes) {
                if (i > 0) {
                    when (val tryResult = this.print(", ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
                this.boundLifetimeDepth += 1u
                when (val tryResult = this.printLifetimeFromIndex(1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            when (val tryResult = this.print("> ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        }
        val r = f(this)
        // Restore `boundLifetimeDepth` to the previous value.
        this.boundLifetimeDepth -= boundLifetimes.toUInt()
        return r
    }

    /**
     * Print list elements using the given closure and separator,
     * until the end of the list (`E`) is found, or the parser errors.
     * Returns the number of elements printed.
     */
    public open fun printSepList(f: (Printer) -> Result<Unit, Error>, sep: String): Result<Int, Error> {
        var i = 0
        while (this.parser.isOk() && !this.eat(69u)) {
            if (i > 0) {
                when (val tryResult = this.print(sep)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
            }
            when (val tryResult = f(this)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Int, Error>(tryResult.errValue()) }
            i += 1
        }
        return Result.Ok<Int, Error>(i)
    }

    public open fun printPath(inValue: Boolean): Result<Unit, Error> {
        run {
            val matchVal = this.parser
            when (matchVal) { is Result.Ok<*, *> -> {
                val parser = matchVal.okValue()
                run {
                    val matchVal1 = parser.pushDepth()
                    when (matchVal1) { is Result.Ok<*, *> -> {
                        val x = matchVal1.okValue()
                        x
                    }; is Result.Err<*, *> -> {
                        val err = matchVal1.errValue()
                        when (val tryResult = this.print(err.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        this.parser = Result.Err<Parser, ParseError>(err)
                        return Result.Ok<Unit, Error>(Unit)
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
                    val parser1 = matchVal2.okValue()
                    run {
                        val matchVal3 = parser1.next()
                        when (matchVal3) { is Result.Ok<*, *> -> {
                            val x1 = matchVal3.okValue()
                            x1
                        }; is Result.Err<*, *> -> {
                            val err1 = matchVal3.errValue()
                            when (val tryResult = this.print(err1.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            this.parser = Result.Err<Parser, ParseError>(err1)
                            return Result.Ok<Unit, Error>(Unit)
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
                            val parser2 = matchVal4.okValue()
                            run {
                                val matchVal5 = parser2.disambiguator()
                                when (matchVal5) { is Result.Ok<*, *> -> {
                                    val x2 = matchVal5.okValue()
                                    x2
                                }; is Result.Err<*, *> -> {
                                    val err2 = matchVal5.errValue()
                                    when (val tryResult = this.print(err2.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                    this.parser = Result.Err<Parser, ParseError>(err2)
                                    return Result.Ok<Unit, Error>(Unit)
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
                            val parser3 = matchVal6.okValue()
                            run {
                                val matchVal7 = parser3.ident()
                                when (matchVal7) { is Result.Ok<*, *> -> {
                                    val x3 = matchVal7.okValue()
                                    x3
                                }; is Result.Err<*, *> -> {
                                    val err3 = matchVal7.errValue()
                                    when (val tryResult = this.print(err3.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                    this.parser = Result.Err<Parser, ParseError>(err3)
                                    return Result.Ok<Unit, Error>(Unit)
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                when (val tryResult = this.print(name)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
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
                            val parser4 = matchVal9.okValue()
                            run {
                                val matchVal10 = parser4.namespace()
                                when (matchVal10) { is Result.Ok<*, *> -> {
                                    val x4 = matchVal10.okValue()
                                    x4
                                }; is Result.Err<*, *> -> {
                                    val err4 = matchVal10.errValue()
                                    when (val tryResult = this.print(err4.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                    this.parser = Result.Err<Parser, ParseError>(err4)
                                    return Result.Ok<Unit, Error>(Unit)
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                when (val tryResult = this.printPath(inValue)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                // Note(eddyb) if the parser is already marked as having errored,
                // `parse` below will print a `?` without its preceding `.`
                // (because printing the `.` is skipped in certain conditions,
                // i.e. a lowercase namespace with an empty identifier),
                // so in order to get `.?`, the `.` has to be printed here.
                if (this.parser.isErr()) {
                    when (val tryResult = this.print("::")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
                val dis1 =
                    run {
                        val matchVal11 = this.parser
                        when (matchVal11) { is Result.Ok<*, *> -> {
                            val parser5 = matchVal11.okValue()
                            run {
                                val matchVal12 = parser5.disambiguator()
                                when (matchVal12) { is Result.Ok<*, *> -> {
                                    val x5 = matchVal12.okValue()
                                    x5
                                }; is Result.Err<*, *> -> {
                                    val err5 = matchVal12.errValue()
                                    when (val tryResult = this.print(err5.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                    this.parser = Result.Err<Parser, ParseError>(err5)
                                    return Result.Ok<Unit, Error>(Unit)
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
                            val parser6 = matchVal13.okValue()
                            run {
                                val matchVal14 = parser6.ident()
                                when (matchVal14) { is Result.Ok<*, *> -> {
                                    val x6 = matchVal14.okValue()
                                    x6
                                }; is Result.Err<*, *> -> {
                                    val err6 = matchVal14.errValue()
                                    when (val tryResult = this.print(err6.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                    this.parser = Result.Err<Parser, ParseError>(err6)
                                    return Result.Ok<Unit, Error>(Unit)
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
                            when (val tryResult = this.print("::")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            when (val tryResult = this.print(name1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        }
                    }
                    // Special namespaces, like closures and shims.
                    else -> {
                        val ns1 = ns
                        when (val tryResult = this.print("::{")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        when (ns1) {
                            'C' -> when (val tryResult = this.print("closure")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            'S' -> when (val tryResult = this.print("shim")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            else -> when (val tryResult = this.print(ns1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        }
                        if (!name1.ascii.isEmpty() || !name1.punycode.isEmpty()) {
                            when (val tryResult = this.print(":")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            when (val tryResult = this.print(name1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        }
                        when (val tryResult = this.print("#")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        when (val tryResult = this.print(dis1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        when (val tryResult = this.print("}")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    }
                }
            }
            77u, 88u, 89u -> {
                if (tag != 89u) {
                    run {
                        val matchVal15 = this.parser
                        when (matchVal15) { is Result.Ok<*, *> -> {
                            val parser7 = matchVal15.okValue()
                            run {
                                val matchVal16 = parser7.disambiguator()
                                when (matchVal16) { is Result.Ok<*, *> -> {
                                    val x7 = matchVal16.okValue()
                                    x7
                                }; is Result.Err<*, *> -> {
                                    val err7 = matchVal16.errValue()
                                    when (val tryResult = this.print(err7.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                    this.parser = Result.Err<Parser, ParseError>(err7)
                                    return Result.Ok<Unit, Error>(Unit)
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                    this.skippingPrinting({ thisRef -> thisRef.printPath(false) })
                }
                when (val tryResult = this.print("<")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.printType()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                if (tag != 77u) {
                    when (val tryResult = this.print(" as ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    when (val tryResult = this.printPath(false)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
                when (val tryResult = this.print(">")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            73u -> {
                when (val tryResult = this.printPath(inValue)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                if (inValue) {
                    when (val tryResult = this.print("::")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
                when (val tryResult = this.print("<")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.printSepList({ it.printGenericArg() }, ", ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.print(">")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            66u -> {
                when (val tryResult = this.printBackref({ thisRef -> thisRef.printPath(inValue) })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            else -> {
                val err8 = ParseError.Invalid
                when (val tryResult = this.print(err8.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                this.parser = Result.Err<Parser, ParseError>(err8)
                return Result.Ok<Unit, Error>(Unit)
            }
        }
        this.popDepth()
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun printGenericArg(): Result<Unit, Error> {
        return if (this.eat(76u)) {
            val lt =
                run {
                    val matchVal = this.parser
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val parser = matchVal.okValue()
                        run {
                            val matchVal1 = parser.integer62()
                            when (matchVal1) { is Result.Ok<*, *> -> {
                                val x = matchVal1.okValue()
                                x
                            }; is Result.Err<*, *> -> {
                                val err = matchVal1.errValue()
                                when (val tryResult = this.print(err.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                this.parser = Result.Err<Parser, ParseError>(err)
                                return Result.Ok<Unit, Error>(Unit)
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

    public open fun printType(): Result<Unit, Error> {
        val tag =
            run {
                val matchVal = this.parser
                when (matchVal) { is Result.Ok<*, *> -> {
                    val parser = matchVal.okValue()
                    run {
                        val matchVal1 = parser.next()
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            val x = matchVal1.okValue()
                            x
                        }; is Result.Err<*, *> -> {
                            val err = matchVal1.errValue()
                            when (val tryResult = this.print(err.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            this.parser = Result.Err<Parser, ParseError>(err)
                            return Result.Ok<Unit, Error>(Unit)
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
                val parser1 = matchVal3.okValue()
                run {
                    val matchVal4 = parser1.pushDepth()
                    when (matchVal4) { is Result.Ok<*, *> -> {
                        val x1 = matchVal4.okValue()
                        x1
                    }; is Result.Err<*, *> -> {
                        val err1 = matchVal4.errValue()
                        when (val tryResult = this.print(err1.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        this.parser = Result.Err<Parser, ParseError>(err1)
                        return Result.Ok<Unit, Error>(Unit)
                    } }
                }
            }; is Result.Err<*, *> -> {
                return this.print("?")
            } }
        }
        when (tag) {
            82u, 81u -> {
                when (val tryResult = this.print("&")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                if (this.eat(76u)) {
                    val lt =
                        run {
                            val matchVal5 = this.parser
                            when (matchVal5) { is Result.Ok<*, *> -> {
                                val parser2 = matchVal5.okValue()
                                run {
                                    val matchVal6 = parser2.integer62()
                                    when (matchVal6) { is Result.Ok<*, *> -> {
                                        val x2 = matchVal6.okValue()
                                        x2
                                    }; is Result.Err<*, *> -> {
                                        val err2 = matchVal6.errValue()
                                        when (val tryResult = this.print(err2.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                        this.parser = Result.Err<Parser, ParseError>(err2)
                                        return Result.Ok<Unit, Error>(Unit)
                                    } }
                                }
                            }; is Result.Err<*, *> -> {
                                return this.print("?")
                            } }
                        }
                    if (lt != 0) {
                        when (val tryResult = this.printLifetimeFromIndex(lt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        when (val tryResult = this.print(" ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    }
                }
                if (tag != 82u) {
                    when (val tryResult = this.print("mut ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
                when (val tryResult = this.printType()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            80u, 79u -> {
                when (val tryResult = this.print("*")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                if (tag != 80u) {
                    when (val tryResult = this.print("mut ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                } else {
                    when (val tryResult = this.print("const ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
                when (val tryResult = this.printType()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            65u, 83u -> {
                when (val tryResult = this.print("[")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.printType()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                if (tag == 65u) {
                    when (val tryResult = this.print("; ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    when (val tryResult = this.printConst(true)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
                when (val tryResult = this.print("]")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            84u -> {
                when (val tryResult = this.print("(")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                val count = when (val tryResult = this.printSepList({ it.printType() }, ", ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                if (count == 1) {
                    when (val tryResult = this.print(",")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
                when (val tryResult = this.print(")")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            70u -> when (val tryResult = this.inBinder(closure4@{ thisRef ->
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
                                        val parser3 = matchVal7.okValue()
                                        run {
                                            val matchVal8 = parser3.ident()
                                            when (matchVal8) { is Result.Ok<*, *> -> {
                                                val x3 = matchVal8.okValue()
                                                x3
                                            }; is Result.Err<*, *> -> {
                                                val err3 = matchVal8.errValue()
                                                when (val tryResult = thisRef.print(err3.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
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
                                    when (val tryResult = thisRef.print(err4.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
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
                    when (val tryResult = thisRef.print("unsafe ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                }
                if (abi1 != null) {
                    val abi2 = abi1
                    when (val tryResult = thisRef.print("extern \"")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                    // If the ABI had any `-`, they were replaced with `_`,
                    // so the parts between `_` have to be re-joined with `-`.
                    val parts = abi2.split('_')
                    when (val tryResult = thisRef.print(parts.next()!!)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                    for (part in parts) {
                        when (val tryResult = thisRef.print("-")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                        when (val tryResult = thisRef.print(part)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                    }
                    when (val tryResult = thisRef.print("\" ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                }
                when (val tryResult = thisRef.print("fn(")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                when (val tryResult = thisRef.printSepList({ it.printType() }, ", ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                when (val tryResult = thisRef.print(")")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                if (thisRef.eat(117u)) {
                    // Skip printing the return type if it's `u`, i.e. `()`.
                } else {
                    when (val tryResult = thisRef.print(" -> ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                    when (val tryResult = thisRef.printType()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure4 Result.Err(tryResult.errValue()) }
                }
                Result.Ok(Unit)
            })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            68u -> {
                when (val tryResult = this.print("dyn ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.inBinder(closure5@{ thisRef ->
                    when (val tryResult = thisRef.printSepList({ it.printDynTrait() }, " + ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure5 Result.Err(tryResult.errValue()) }
                    Result.Ok(Unit)
                })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                if (!this.eat(76u)) {
                    run {
                        val err3 = ParseError.Invalid
                        when (val tryResult = this.print(err3.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        this.parser = Result.Err<Parser, ParseError>(err3)
                        return Result.Ok<Unit, Error>(Unit)
                    }
                }
                val lt1 =
                    run {
                        val matchVal7 = this.parser
                        when (matchVal7) { is Result.Ok<*, *> -> {
                            val parser3 = matchVal7.okValue()
                            run {
                                val matchVal8 = parser3.integer62()
                                when (matchVal8) { is Result.Ok<*, *> -> {
                                    val x3 = matchVal8.okValue()
                                    x3
                                }; is Result.Err<*, *> -> {
                                    val err4 = matchVal8.errValue()
                                    when (val tryResult = this.print(err4.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                    this.parser = Result.Err<Parser, ParseError>(err4)
                                    return Result.Ok<Unit, Error>(Unit)
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }
                if (lt1 != 0) {
                    when (val tryResult = this.print(" + ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    when (val tryResult = this.printLifetimeFromIndex(lt1)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
            }
            66u -> {
                when (val tryResult = this.printBackref({ it.printType() })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            else -> {
                // Go back to the tag, so `printPath` also sees it.
                this.parser.asMut().map({ p -> p.next -= 1 })
                when (val tryResult = this.printPath(false)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
        }
        this.popDepth()
        return Result.Ok<Unit, Error>(Unit)
    }

    /**
     * A trait in a trait object may have some "existential projections"
     * (i.e. associated type bindings) after it, which should be printed
     * in the `<...>` of the trait, e.g. `dynamic Trait<T, U, Assoc = X>`.
     * To this end, this method will keep the `<...>` of an `I` path
     * open, by omitting the `>`, and return `Ok(true)` in that case.
     */
    public open fun printPathMaybeOpenGenerics(): Result<Boolean, Error> {
        return if (this.eat(66u)) {
            // NOTE(eddyb) the closure may not run if printing is being skipped,
            // but in that case the returned boolean doesn't matter.
            var open = false
            when (val tryResult = this.printBackref(closure6@{ thisRef ->
                open = when (val tryResult = thisRef.printPathMaybeOpenGenerics()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure6 Result.Err(tryResult.errValue()) }
                Result.Ok(Unit)
            })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
            Result.Ok<Boolean, Error>(open)
        } else if (this.eat(73u)) {
            when (val tryResult = this.printPath(false)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
            when (val tryResult = this.print("<")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
            when (val tryResult = this.printSepList({ it.printGenericArg() }, ", ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
            Result.Ok<Boolean, Error>(true)
        } else {
            when (val tryResult = this.printPath(false)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Boolean, Error>(tryResult.errValue()) }
            Result.Ok<Boolean, Error>(false)
        }
    }

    public open fun printDynTrait(): Result<Unit, Error> {
        var open = when (val tryResult = this.printPathMaybeOpenGenerics()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        while (this.eat(112u)) {
            if (!open) {
                when (val tryResult = this.print("<")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                open = true
            } else {
                when (val tryResult = this.print(", ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            val name =
                run {
                    val matchVal = this.parser
                    when (matchVal) { is Result.Ok<*, *> -> {
                        val parser = matchVal.okValue()
                        run {
                            val matchVal1 = parser.ident()
                            when (matchVal1) { is Result.Ok<*, *> -> {
                                val x = matchVal1.okValue()
                                x
                            }; is Result.Err<*, *> -> {
                                val err = matchVal1.errValue()
                                when (val tryResult = this.print(err.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                this.parser = Result.Err<Parser, ParseError>(err)
                                return Result.Ok<Unit, Error>(Unit)
                            } }
                        }
                    }; is Result.Err<*, *> -> {
                        return this.print("?")
                    } }
                }
            when (val tryResult = this.print(name)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            when (val tryResult = this.print(" = ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            when (val tryResult = this.printType()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        }
        if (open) {
            when (val tryResult = this.print(">")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun printConst(inValue: Boolean): Result<Unit, Error> {
        val tag =
            run {
                val matchVal = this.parser
                when (matchVal) { is Result.Ok<*, *> -> {
                    val parser = matchVal.okValue()
                    run {
                        val matchVal1 = parser.next()
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            val x = matchVal1.okValue()
                            x
                        }; is Result.Err<*, *> -> {
                            val err = matchVal1.errValue()
                            when (val tryResult = this.print(err.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            this.parser = Result.Err<Parser, ParseError>(err)
                            return Result.Ok<Unit, Error>(Unit)
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }
        run {
            val matchVal2 = this.parser
            when (matchVal2) { is Result.Ok<*, *> -> {
                val parser1 = matchVal2.okValue()
                run {
                    val matchVal3 = parser1.pushDepth()
                    when (matchVal3) { is Result.Ok<*, *> -> {
                        val x1 = matchVal3.okValue()
                        x1
                    }; is Result.Err<*, *> -> {
                        val err1 = matchVal3.errValue()
                        when (val tryResult = this.print(err1.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        this.parser = Result.Err<Parser, ParseError>(err1)
                        return Result.Ok<Unit, Error>(Unit)
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
            112u -> when (val tryResult = this.print("_")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            // Primitive leaves with hex-encoded values (see `basicType`).
            104u, 116u, 109u, 121u, 111u, 106u -> when (val tryResult = this.printConstUint(tag)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            97u, 115u, 108u, 120u, 110u, 105u -> {
                if (this.eat(110u)) {
                    when (val tryResult = this.print("-")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
                when (val tryResult = this.printConstUint(tag)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            98u -> when (val matchVal6 = run {
                val matchVal4 = this.parser
                when (matchVal4) { is Result.Ok<*, *> -> {
                    val parser2 = matchVal4.okValue()
                    run {
                        val matchVal5 = parser2.hexNibbles()
                        when (matchVal5) { is Result.Ok<*, *> -> {
                            val x2 = matchVal5.okValue()
                            x2
                        }; is Result.Err<*, *> -> {
                            val err2 = matchVal5.errValue()
                            when (val tryResult = this.print(err2.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            this.parser = Result.Err<Parser, ParseError>(err2)
                            return Result.Ok<Unit, Error>(Unit)
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }.tryParseUint()) {
                0 -> when (val tryResult = this.print("false")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                1 -> when (val tryResult = this.print("true")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                else -> {
                    val err3 = ParseError.Invalid
                    when (val tryResult = this.print(err3.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    this.parser = Result.Err<Parser, ParseError>(err3)
                    return Result.Ok<Unit, Error>(Unit)
                }
            }
            99u -> {
                val validChar =
                    run {
                        val matchVal7 = this.parser
                        when (matchVal7) { is Result.Ok<*, *> -> {
                            val parser3 = matchVal7.okValue()
                            run {
                                val matchVal8 = parser3.hexNibbles()
                                when (matchVal8) { is Result.Ok<*, *> -> {
                                    val x3 = matchVal8.okValue()
                                    x3
                                }; is Result.Err<*, *> -> {
                                    val err4 = matchVal8.errValue()
                                    when (val tryResult = this.print(err4.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                    this.parser = Result.Err<Parser, ParseError>(err4)
                                    return Result.Ok<Unit, Error>(Unit)
                                } }
                            }
                        }; is Result.Err<*, *> -> {
                            return this.print("?")
                        } }
                    }.tryParseUint().andThen({ v -> UInt.tryFrom(v).ok() }).andThen(Char::fromU32)
                when (validChar) {
                    null -> {
                        val err5 = ParseError.Invalid
                        when (val tryResult = this.print(err5.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        this.parser = Result.Err<Parser, ParseError>(err5)
                        return Result.Ok<Unit, Error>(Unit)
                    }
                    else -> {
                        val c = validChar
                        when (val tryResult = this.printQuotedEscapedChars('\'', Iter.once(c))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    }
                }
            }
            101u -> {
                // NOTE(eddyb) a string literal `"..."` has type `String`, so
                // to get back the type `String`, `* "..."` syntax is needed
                // (even if that may not be valid in Rust itself).
                when (val tryResult = openBraceIfOutsideExpr(this)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.print("*")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.printConstStrLiteral()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            82u, 81u -> {
                // NOTE(eddyb) this prints `"..."` instead of `* "..."`, which
                // is what `Re ... _` would imply (see comment for `String` above).
                if (tag == 82u && this.eat(101u)) {
                    when (val tryResult = this.printConstStrLiteral()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                } else {
                    when (val tryResult = openBraceIfOutsideExpr(this)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    when (val tryResult = this.print("&")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    if (tag != 82u) {
                        when (val tryResult = this.print("mut ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    }
                    when (val tryResult = this.printConst(true)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
            }
            65u -> {
                when (val tryResult = openBraceIfOutsideExpr(this)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.print("[")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.printSepList({ thisRef -> thisRef.printConst(true) }, ", ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.print("]")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            84u -> {
                when (val tryResult = openBraceIfOutsideExpr(this)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.print("(")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                val count = when (val tryResult = this.printSepList({ thisRef -> thisRef.printConst(true) }, ", ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                if (count == 1) {
                    when (val tryResult = this.print(",")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
                when (val tryResult = this.print(")")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            86u -> {
                when (val tryResult = openBraceIfOutsideExpr(this)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.printPath(true)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (run {
                    val matchVal9 = this.parser
                    when (matchVal9) { is Result.Ok<*, *> -> {
                        val parser4 = matchVal9.okValue()
                        run {
                            val matchVal10 = parser4.next()
                            when (matchVal10) { is Result.Ok<*, *> -> {
                                val x4 = matchVal10.okValue()
                                x4
                            }; is Result.Err<*, *> -> {
                                val err6 = matchVal10.errValue()
                                when (val tryResult = this.print(err6.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                                this.parser = Result.Err<Parser, ParseError>(err6)
                                return Result.Ok<Unit, Error>(Unit)
                            } }
                        }
                    }; is Result.Err<*, *> -> {
                        return this.print("?")
                    } }
                }) {
                    85u -> {}
                    84u -> {
                        when (val tryResult = this.print("(")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        when (val tryResult = this.printSepList({ thisRef -> thisRef.printConst(true) }, ", ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        when (val tryResult = this.print(")")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    }
                    83u -> {
                        when (val tryResult = this.print(" { ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        when (val tryResult = this.printSepList(closure8@{ thisRef ->
                            run {
                                val matchVal11 = thisRef.parser
                                when (matchVal11) { is Result.Ok<*, *> -> {
                                    val parser5 = matchVal11.okValue()
                                    run {
                                        val matchVal12 = parser5.disambiguator()
                                        when (matchVal12) { is Result.Ok<*, *> -> {
                                            val x5 = matchVal12.okValue()
                                            x5
                                        }; is Result.Err<*, *> -> {
                                            val err7 = matchVal12.errValue()
                                            when (val tryResult = thisRef.print(err7.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.errValue()) }
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
                                        val parser6 = matchVal13.okValue()
                                        run {
                                            val matchVal14 = parser6.ident()
                                            when (matchVal14) { is Result.Ok<*, *> -> {
                                                val x6 = matchVal14.okValue()
                                                x6
                                            }; is Result.Err<*, *> -> {
                                                val err8 = matchVal14.errValue()
                                                when (val tryResult = thisRef.print(err8.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.errValue()) }
                                                thisRef.parser = Result.Err(err8)
                                                return@closure8 Result.Ok(Unit)
                                            } }
                                        }
                                    }; is Result.Err<*, *> -> {
                                        return@closure8 thisRef.print("?")
                                    } }
                                }
                            when (val tryResult = thisRef.print(name)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.errValue()) }
                            when (val tryResult = thisRef.print(": ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure8 Result.Err(tryResult.errValue()) }
                            thisRef.printConst(true)
                        }, ", ")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        when (val tryResult = this.print(" }")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                    }
                    else -> {
                        val err7 = ParseError.Invalid
                        when (val tryResult = this.print(err7.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        this.parser = Result.Err<Parser, ParseError>(err7)
                        return Result.Ok<Unit, Error>(Unit)
                    }
                }
            }
            66u -> {
                when (val tryResult = this.printBackref({ thisRef -> thisRef.printConst(inValue) })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            else -> {
                val err8 = ParseError.Invalid
                when (val tryResult = this.print(err8.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                this.parser = Result.Err<Parser, ParseError>(err8)
                return Result.Ok<Unit, Error>(Unit)
            }
        }
        if (openedBrace) {
            when (val tryResult = this.print("}")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        }
        this.popDepth()
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun printConstUint(tyTag: UByte): Result<Unit, Error> {
        val hex =
            run {
                val matchVal = this.parser
                when (matchVal) { is Result.Ok<*, *> -> {
                    val parser = matchVal.okValue()
                    run {
                        val matchVal1 = parser.hexNibbles()
                        when (matchVal1) { is Result.Ok<*, *> -> {
                            val x = matchVal1.okValue()
                            x
                        }; is Result.Err<*, *> -> {
                            val err = matchVal1.errValue()
                            when (val tryResult = this.print(err.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                            this.parser = Result.Err<Parser, ParseError>(err)
                            return Result.Ok<Unit, Error>(Unit)
                        } }
                    }
                }; is Result.Err<*, *> -> {
                    return this.print("?")
                } }
            }
        when (val matchVal2 = hex.tryParseUint()) {
            // Print anything that doesn't fit in `ULong` verbatim.
            null -> {
                when (val tryResult = this.print("0x")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                when (val tryResult = this.print(hex.nibbles)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
            else -> {
                val v = matchVal2
                when (val tryResult = this.print(v)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
            }
        }
        when (val matchVal3 = this.out) {
            null -> {}
            else -> {
                val out = matchVal3
                if (!out.alternate()) {
                    val ty = basicType(tyTag)!!
                    when (val tryResult = this.print(ty)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
            }
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun printConstStrLiteral(): Result<Unit, Error> {
        return when (val matchVal2 = run {
            val matchVal = this.parser
            when (matchVal) { is Result.Ok<*, *> -> {
                val parser = matchVal.okValue()
                run {
                    val matchVal1 = parser.hexNibbles()
                    when (matchVal1) { is Result.Ok<*, *> -> {
                        val x = matchVal1.okValue()
                        x
                    }; is Result.Err<*, *> -> {
                        val err = matchVal1.errValue()
                        when (val tryResult = this.print(err.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                        this.parser = Result.Err<Parser, ParseError>(err)
                        return Result.Ok<Unit, Error>(Unit)
                    } }
                }
            }; is Result.Err<*, *> -> {
                return this.print("?")
            } }
        }.tryParseStrChars()) {
            null -> {
                val err1 = ParseError.Invalid
                when (val tryResult = this.print(err1.message())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                this.parser = Result.Err<Parser, ParseError>(err1)
                return Result.Ok<Unit, Error>(Unit)
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
