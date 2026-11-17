// port-lint: source escape.rs
package io.github.kotlinmania.rustlang.core.escape

import io.github.kotlinmania.rustlang.core.convert.From
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.leadingZeros
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result

// Helper code for character escaping.

public val HEX_DIGITS: List<io.github.kotlinmania.rustlang.core.ascii.Char> = "0123456789abcdef".encodeToByteArray().asAscii().unwrap()

/**
 * Escapes a character with `\x` representation.
 *
 * Returns a buffer with the escaped representation and its corresponding range.
 */
public fun backslash(a: io.github.kotlinmania.rustlang.core.ascii.Char, n: Int): Pair<List<io.github.kotlinmania.rustlang.core.ascii.Char>, Range<UByte>> {
    check(n >= 2)
    val output = MutableList(n) { io.github.kotlinmania.rustlang.core.ascii.Char.Null }
    output[0] = io.github.kotlinmania.rustlang.core.ascii.Char.ReverseSolidus
    output[1] = a
    return Pair(output, 0 until 2)
}

/**
 * Escapes a character with `\xNN` representation.
 *
 * Returns a buffer with the escaped representation and its corresponding range.
 */
public fun hexEscape(byte: UByte, n: Int): Pair<List<io.github.kotlinmania.rustlang.core.ascii.Char>, Range<UByte>> {
    check(n >= 4)
    val output = MutableList(n) { io.github.kotlinmania.rustlang.core.ascii.Char.Null }
    val hi = HEX_DIGITS[((byte.toUInt() shr 4).toUByte()).toInt()]
    val lo = HEX_DIGITS[(byte and 0xfu.toUByte()).toInt()]
    output[0] = io.github.kotlinmania.rustlang.core.ascii.Char.ReverseSolidus
    output[1] = io.github.kotlinmania.rustlang.core.ascii.Char.SmallX
    output[2] = hi
    output[3] = lo
    return Pair(output, 0 until 4)
}

/**
 * Returns a buffer with the verbatim character and its corresponding range.
 */
public fun verbatim(a: io.github.kotlinmania.rustlang.core.ascii.Char, n: Int): Pair<List<io.github.kotlinmania.rustlang.core.ascii.Char>, Range<UByte>> {
    check(n >= 1)
    val output = MutableList(n) { io.github.kotlinmania.rustlang.core.ascii.Char.Null }
    output[0] = a
    return Pair(output, 0 until 1)
}

/**
 * Escapes an ASCII character.
 *
 * Returns a buffer with the escaped representation and its corresponding range.
 */
public fun escapeAscii(byte: UByte, n: Int): Pair<List<io.github.kotlinmania.rustlang.core.ascii.Char>, Range<UByte>> {
    check(n >= 4)
    return when (byte) {
        9u -> backslash(io.github.kotlinmania.rustlang.core.ascii.Char.SmallT, n)
        13u -> backslash(io.github.kotlinmania.rustlang.core.ascii.Char.SmallR, n)
        10u -> backslash(io.github.kotlinmania.rustlang.core.ascii.Char.SmallN, n)
        92u -> backslash(io.github.kotlinmania.rustlang.core.ascii.Char.ReverseSolidus, n)
        39u -> backslash(io.github.kotlinmania.rustlang.core.ascii.Char.Apostrophe, n)
        34u -> { backslash(io.github.kotlinmania.rustlang.core.ascii.Char.QuotationMark, n) }
        in 0x00u.toUByte()..0x1Fu.toUByte(), 0x7Fu.toUByte() -> hexEscape(byte, n)
        else -> when (val matchVal = io.github.kotlinmania.rustlang.core.ascii.Char.fromU8(byte)) {
            null -> hexEscape(byte, n)
            else -> {
                val a = matchVal
                verbatim(a, n)
            }
        }
    }
}

/**
 * Escapes a character with `\u(NNNN)` representation.
 *
 * Returns a buffer with the escaped representation and its corresponding range.
 */
public fun escapeUnicode(c: Char, n: Int): Pair<List<io.github.kotlinmania.rustlang.core.ascii.Char>, Range<UByte>> {
    check(n >= 10 && n < UByte.MAX_VALUE.toInt())
    val c1 = c.toUInt()
    // OR-ing `1` ensures that for `c == 0` the code computes that
    // one digit should be printed.
    val start = (c1 or 1u).leadingZeros().toInt() / 4 - 2
    val output = MutableList(n) { io.github.kotlinmania.rustlang.core.ascii.Char.Null }
    output[3] = HEX_DIGITS[((c1 shr 20) and 15u).toInt()]
    output[4] = HEX_DIGITS[((c1 shr 16) and 15u).toInt()]
    output[5] = HEX_DIGITS[((c1 shr 12) and 15u).toInt()]
    output[6] = HEX_DIGITS[((c1 shr 8) and 15u).toInt()]
    output[7] = HEX_DIGITS[((c1 shr 4) and 15u).toInt()]
    output[8] = HEX_DIGITS[((c1 shr 0) and 15u).toInt()]
    output[9] = io.github.kotlinmania.rustlang.core.ascii.Char.RightCurlyBracket
    output[start + 0] = io.github.kotlinmania.rustlang.core.ascii.Char.ReverseSolidus
    output[start + 1] = io.github.kotlinmania.rustlang.core.ascii.Char.SmallU
    output[start + 2] = io.github.kotlinmania.rustlang.core.ascii.Char.LeftCurlyBracket
    return Pair(output, (start.toUByte()) until (n.toUByte()))
}

public open class MaybeEscapedCharacter(
    public var escapeSeq: List<io.github.kotlinmania.rustlang.core.ascii.Char>? = null,
    public var literal: Char? = null,
)

/**
 * Marker type to indicate that the character is always escaped,
 * used to optimize the iterator implementation.
 */
public open class AlwaysEscaped {
    public open fun clone(): AlwaysEscaped = AlwaysEscaped()

    public companion object {
        public fun typeLayout(): TypeLayout<AlwaysEscaped> =
            object : TypeLayout<AlwaysEscaped> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): AlwaysEscaped =
                    AlwaysEscaped()
                override fun writeNative(addr: Long, value: AlwaysEscaped) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): AlwaysEscaped =
                    AlwaysEscaped()
                override fun writeBytes(buf: ByteArray, offset: Int, value: AlwaysEscaped) {
                }
            }
    }
}

/**
 * Marker type to indicate that the character may be escaped,
 * used to optimize the iterator implementation.
 */
public open class MaybeEscaped {
    public open fun clone(): MaybeEscaped = MaybeEscaped()

    public companion object {
        public fun typeLayout(): TypeLayout<MaybeEscaped> =
            object : TypeLayout<MaybeEscaped> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): MaybeEscaped =
                    MaybeEscaped()
                override fun writeNative(addr: Long, value: MaybeEscaped) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): MaybeEscaped =
                    MaybeEscaped()
                override fun writeBytes(buf: ByteArray, offset: Int, value: MaybeEscaped) {
                }
            }
    }
}

/**
 * An iterator over a possibly escaped character.
 */
public open class EscapeIterInner<Escaping> internal constructor(
    // Invariant:
    //
    // If `alive.end <= Self.LITERAL_ESCAPE_START`, `data` must contain
    // printable ASCII characters in the `alive` range of its `escapeSeq` variant.
    //
    // If `alive.end> Self.LITERAL_ESCAPE_START`, `data` must contain a
    // `char` in its `literal` variant, and the `alive` range must have a
    // length of at most `1`.
    public var data: MaybeEscapedCharacter,
    aliveCtorArg: Range<UByte>,
    public var escaping: Unit,
) {
    public var alive: Range<UByte> = aliveCtorArg
        internal set

    public open fun clone(): EscapeIterInner<Escaping> = EscapeIterInner(data, alive, escaping)

    public companion object {
        public operator fun <Escaping> invoke(
            data: MaybeEscapedCharacter,
            alive: Range<UByte>,
            escaping: Unit,
        ): EscapeIterInner<Escaping> = EscapeIterInner<Escaping>(
            data = data,
            aliveCtorArg = alive,
            escaping = escaping,
        )
    }
}

public val LITERAL_ESCAPE_START: UByte = 128u

/**
 * # Safety
 *
 * `data.escapeSeq` must contain an escape sequence in the range given by `alive`.
 */
public fun <Escaping> EscapeIterInner.Companion.new(data: MaybeEscapedCharacter, alive: Range<UByte>, n: Int): EscapeIterInner<Escaping> {
    // Longer escape sequences are not useful given `alive.end` is at most
    // `Self.LITERAL_ESCAPE_START`.
    check(n < EscapeIterInner.LITERAL_ESCAPE_START.toInt())
    if (false) {
        check(alive.end <= (n + 1).toUByte())
    }
    return EscapeIterInner(data = data, alive = alive, escaping = Unit)
}

public fun <Escaping> EscapeIterInner.Companion.backslash(c: io.github.kotlinmania.rustlang.core.ascii.Char, n: Int): EscapeIterInner<Escaping> {
    val (escapeSeq, alive) = backslash(c, n)
    // SAFETY: `escapeSeq` contains an escape sequence in the range given by `alive`.
    return EscapeIterInner.new(MaybeEscapedCharacter(escapeSeq = escapeSeq), alive, n)
}

public fun <Escaping> EscapeIterInner.Companion.ascii(c: UByte, n: Int): EscapeIterInner<Escaping> {
    val (escapeSeq, alive) = escapeAscii(c, n)
    // SAFETY: `escapeSeq` contains an escape sequence in the range given by `alive`.
    return EscapeIterInner.new(MaybeEscapedCharacter(escapeSeq = escapeSeq), alive, n)
}

public fun <Escaping> EscapeIterInner.Companion.unicode(c: Char, n: Int): EscapeIterInner<Escaping> {
    val (escapeSeq, alive) = escapeUnicode(c, n)
    // SAFETY: `escapeSeq` contains an escape sequence in the range given by `alive`.
    return EscapeIterInner.new(MaybeEscapedCharacter(escapeSeq = escapeSeq), alive, n)
}

public fun <Escaping> EscapeIterInner.Companion.empty(n: Int): EscapeIterInner<Escaping> {
    // SAFETY: `0 .. 0` ensures an empty escape sequence.
    return EscapeIterInner.new(MaybeEscapedCharacter(escapeSeq = List(n) { io.github.kotlinmania.rustlang.core.ascii.Char.Null }), 0 until 0, n)
}

public fun <Escaping> EscapeIterInner<Escaping>.len(n: Int): Int = Int.From(this.alive.end - this.alive.start)

public fun <Escaping> EscapeIterInner<Escaping>.advanceBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.alive.advanceBy(n)

public fun <Escaping> EscapeIterInner<Escaping>.advanceBackBy(n: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> = this.alive.advanceBackBy(n)

/**
 * Returns a `char` if `self.data` contains one in its `literal` variant.
 */
public fun <Escaping> EscapeIterInner<Escaping>.toChar(n: Int): Char? {
    if (this.alive.end > EscapeIterInner.LITERAL_ESCAPE_START) {
        // SAFETY: We just checked that `self.data` contains a `char` in
        // its `literal` variant.
        return this.data.literal
    }
    return null
}

/**
 * Returns the printable ASCII characters in the `escapeSeq` variant of `self.data`
 * as a string.
 *
 * # Safety
 *
 * - `self.data` must contain printable ASCII characters in its `escapeSeq` variant.
 * - `self.alive` must be a valid range for `self.data.escapeSeq`.
 */
public fun <Escaping> EscapeIterInner<Escaping>.toStrUnchecked(n: Int): String {
    if (false) {
        check(this.alive.end <= EscapeIterInner.LITERAL_ESCAPE_START)
    }
    // SAFETY: The caller guarantees `self.data` contains printable ASCII
    // characters in its `escapeSeq` variant, and `self.alive` is
    // a valid range for `self.data.escapeSeq`.
    return this.data.escapeSeq.getUnchecked(Int.From(this.alive.start) until Int.From(this.alive.end)).asStr()
}

public fun EscapeIterInner<AlwaysEscaped>.next(n: Int): UByte? {
    val i = (this.alive.next() ?: return null)
    // SAFETY: The `AlwaysEscaped` marker guarantees that `self.data`
    // contains printable ASCII characters in its `escapeSeq`
    // variant, and `i` is guaranteed to be a valid index for
    // `self.data.escapeSeq`.
    return this.data.escapeSeq.getUnchecked(Int.From(i)).toU8()
}

public fun EscapeIterInner<AlwaysEscaped>.nextBack(n: Int): UByte? {
    val i = (this.alive.nextBack() ?: return null)
    // SAFETY: The `AlwaysEscaped` marker guarantees that `self.data`
    // contains printable ASCII characters in its `escapeSeq`
    // variant, and `i` is guaranteed to be a valid index for
    // `self.data.escapeSeq`.
    return this.data.escapeSeq.getUnchecked(Int.From(i)).toU8()
}

// This is the only way to create any `EscapeIterInner` containing a `char` in
// the `literal` variant of its `self.data`, meaning the `AlwaysEscaped` marker
// guarantees that `self.data` contains printable ASCII characters in its
// `escapeSeq` variant.
public fun EscapeIterInner.Companion.printable(c: Char, n: Int): EscapeIterInner<MaybeEscaped> = EscapeIterInner(data = MaybeEscapedCharacter(literal = c), alive = EscapeIterInner.LITERAL_ESCAPE_START until (EscapeIterInner.LITERAL_ESCAPE_START + 1), escaping = Unit)

public fun EscapeIterInner<AlwaysEscaped>.fmt(f: StringBuilder, n: Int): Result<Unit, Unit> {
    // SAFETY: The `AlwaysEscaped` marker guarantees that `self.data`
    // contains printable ASCII chars, and `self.alive` is
    // guaranteed to be a valid range for `self.data`.
    return run { f.append(this.toStrUnchecked(n)); Result.Ok<Unit, Unit>(Unit) }
}
