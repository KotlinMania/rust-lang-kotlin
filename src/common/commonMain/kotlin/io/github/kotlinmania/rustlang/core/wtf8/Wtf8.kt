// port-lint: source wtf8.rs
package io.github.kotlinmania.rustlang.core.wtf8

import io.github.kotlinmania.rustlang.core.char.methods.EscapeDebugExtArgs
import io.github.kotlinmania.rustlang.core.char.methods.encodeUtf16Raw
import io.github.kotlinmania.rustlang.core.char.methods.isAscii
import io.github.kotlinmania.rustlang.core.clone.CloneToUninit
import io.github.kotlinmania.rustlang.core.clone.cloneToUninit
import io.github.kotlinmania.rustlang.core.convert.AsRef
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.nichetypes.CodePointInner
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.ops.range.Range
import io.github.kotlinmania.rustlang.core.ops.range.RangeFrom
import io.github.kotlinmania.rustlang.core.ops.range.RangeFull
import io.github.kotlinmania.rustlang.core.ops.range.RangeTo
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.str.chars
import io.github.kotlinmania.rustlang.core.str.validations.nextCodePoint

// Implementation of [the WTF-8 encoding](https://wtf-8.codeberg.page/).
//
// This library uses Rust’s type system to maintain
// [well-formedness](https://wtf-8.codeberg.page/#well-formed),
// like the `String` and `String` types do for UTF-8.
//
// Since [WTF-8 must not be used
// for interchange](https://wtf-8.codeberg.page/#intended-audience),
// this library deliberately does not provide access to the underlying bytes
// of WTF-8 strings,
// nor can it decode WTF-8 from arbitrary bytes.
// WTF-8 strings can be obtained from UTF-8, UTF-16, or code points.

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * A Unicode code point: from U+0000 to U+10FFFF.
 *
 * Compares with the `char` type,
 * which represents a Unicode scalar value:
 * a code point that is not a surrogate (U+D800 to U+DFFF).
 */
public open class CodePoint(
    public var value: CodePointInner,
) {
    public var field0: CodePointInner
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): CodePoint = CodePoint(value)

    /**
     * Format the code point as `U +` followed by four to six hexadecimal digits.
     * Example: `U + 1F4A9`
     */
    public open fun fmt(formatter: StringBuilder): Result<Unit, Unit> = formatter.writeFmt("U+${this.value.asInner()}")

    /**
     * Returns the numeric value of the code point.
     */
    public open fun toU32(): UInt = this.value.asInner()

    /**
     * Returns the numeric value of the code point if it is a leading surrogate.
     */
    public open fun toLeadSurrogate(): UShort? =
        when (val matchVal = this.toU32()) {
            in 0xD800u..0xDBFFu -> {
                val lead = matchVal
                lead.toUShort()
            }
            else -> null
        }

    /**
     * Returns the numeric value of the code point if it is a trailing surrogate.
     */
    public open fun toTrailSurrogate(): UShort? =
        when (val matchVal = this.toU32()) {
            in 0xDC00u..0xDFFFu -> {
                val trail = matchVal
                trail.toUShort()
            }
            else -> null
        }

    /**
     * Optionally returns a Unicode scalar value for the code point.
     *
     * Returns `None` if the code point is a surrogate (from U+D800 to U+DFFF).
     */
    public open fun toChar(): Char? =
        when (val matchVal = this.toU32()) {
            in 0xD800u..0xDFFFu -> null
            // SAFETY: We explicitly check that the char is valid.
            else -> {
                val valid = matchVal
                Char.fromU32Unchecked(valid)
            }
        }

    /**
     * Returns a Unicode scalar value for the code point.
     *
     * Returns `'\u(FFFD)'` (the replacement character “�”)
     * if the code point is a surrogate (from U+D800 to U+DFFF).
     */
    public open fun toCharLossy(): Char = (this.toChar() ?: Char.REPLACEMENT_CHARACTER)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.value).hashCode()
        return state
    }

    public companion object {
        /**
         * Unsafely creates a new `CodePoint` without checking the value.
         *
         * # Safety
         *
         * `value` must be less than or equal to 0x10FFFF.
         */
        public fun fromU32Unchecked(value: UInt): CodePoint {
            // SAFETY: Guaranteed by caller.
            return CodePoint(CodePointInner.newUnchecked(value))
        }

        /**
         * Creates a new `CodePoint` if the value is a valid code point.
         *
         * Returns `None` if `value` is above 0x10FFFF.
         */
        public fun fromU32(value: UInt): CodePoint? {
            return CodePoint((CodePointInner.new(value) ?: return null))
        }

        /**
         * Creates a new `CodePoint` from a `char`.
         *
         * Since all Unicode scalar values are code points, this always succeeds.
         */
        public fun fromChar(value: Char): CodePoint {
            // SAFETY: All char are valid for this type.
            return CodePoint.fromU32Unchecked(value.toUInt())
        }
    }
}

/**
 * A borrowed slice of well-formed WTF-8 data.
 *
 * Similar to `String`, but can additionally contain surrogate code points
 * if they’re not in a surrogate pair.
 */
public open class Wtf8 internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout(),
) : AsRef<List<UByte>>, CloneToUninit {
    public var bytes: List<UByte>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(bytes: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()) : this(allocateNativeCell(bytes, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<Wtf8> = TransparentTypeLayout(fieldLayout, { ptr, layout -> Wtf8(ptr, layout) }, { it.bytes })

    public override fun asRef(): List<UByte> = this.bytes

    /**
     * Formats the string in double quotes, with characters escaped according to
     * [char.escapeDebug] and unpaired surrogates represented as `\u(xxxx)`,
     * where each `x` is a hexadecimal digit.
     */
    public open fun fmt(formatter: StringBuilder): Result<Unit, Unit> {
        fun writeStrEscaped(f: StringBuilder, s: String): Result<Unit, Error> {
            for (c in s.chars().flatMap({ c ->
                c.escapeDebugExt(EscapeDebugExtArgs(escapeSingleQuote = false, escapeDoubleQuote = true))
            })) {
                f.append(c)
            }
            return Result.Ok<Unit, Error>(Unit)
        }
        formatter.append('"')
        var pos = 0
        while (true) {
            val matchVal = this.nextSurrogate(pos)
            if (matchVal == null) break
            val surrogatePos = matchVal.first
            val surrogate = matchVal.second
            // SAFETY: nextSurrogate provides an index for a range of valid UTF-8 bytes.
            when (val tryResult = writeStrEscaped(formatter, String.fromUtf8Unchecked(((this.bytes).subList(pos, surrogatePos))))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
            when (val tryResult = formatter.writeFmt("\\u{$surrogate}")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
            pos = surrogatePos + 3
        }
        // SAFETY: after nextSurrogate returns None, the remainder is valid UTF-8.
        when (val tryResult = writeStrEscaped(formatter, String.fromUtf8Unchecked(((this.bytes).subList(pos, (this.bytes).size))))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        return run { formatter.append('"'); Result.Ok<Unit, Unit>(Unit) }
    }

    /**
     * Formats the string with unpaired surrogates substituted with the replacement
     * character, U+FFFD.
     */
    public open fun fmtDisplay(formatter: StringBuilder): Result<Unit, Unit> {
        val wtf8Bytes = this.bytes
        var pos = 0
        while (true) {
            when (val matchVal = this.nextSurrogate(pos)) {
                null -> {
                    // SAFETY: after nextSurrogate returns None, the remainder is valid UTF-8.
                    val s = String.fromUtf8Unchecked(((wtf8Bytes).subList(pos, (wtf8Bytes).size)))
                    if (pos == 0) {
                        return s.fmt(formatter)
                    } else {
                        return run { formatter.append(s); Result.Ok<Unit, Unit>(Unit) }
                    }
                }
                else -> {
                    val surrogatePos = matchVal.first
                    // SAFETY: nextSurrogate provides an index for a range of valid UTF-8 bytes.
                    formatter.append(String.fromUtf8Unchecked(((wtf8Bytes).subList(pos, surrogatePos))))
                    formatter.append(Char.REPLACEMENT_CHARACTER)
                    pos = surrogatePos + 3
                }
            }
        }
    }

    /**
     * Returns the length, in WTF-8 bytes.
     */
    public open fun len(): Int = this.bytes.size

    public open fun isEmpty(): Boolean = this.bytes.isEmpty()

    /**
     * Returns the code point at `position` if it is in the ASCII range,
     * or `b'\xFF'` otherwise.
     *
     * # Panics
     *
     * Panics if `position` is beyond the end of the string.
     */
    public open fun asciiByteAt(position: Int): UByte =
        when (val matchVal = this.bytes[position]) {
            in 0x00u.toUByte()..0x7Fu.toUByte() -> {
                val asciiByte = matchVal
                asciiByte
            }
            else -> 0xFFu
        }

    /**
     * Returns an iterator for the string’s code points.
     */
    public open fun codePoints(): Wtf8CodePoints = Wtf8CodePoints(bytes = this.bytes.iter())

    /**
     * Access raw bytes of WTF-8 data
     */
    public open fun asBytes(): List<UByte> = this.bytes

    /**
     * Tries to convert the string to UTF-8 and return a `String` slice.
     *
     * Returns `None` if the string contains surrogates.
     *
     * This does not copy the data.
     */
    public open fun asStr(): Result<String, Utf8Error> = String.fromUtf8(this.bytes)

    /**
     * Converts the WTF-8 string to potentially ill-formed UTF-16
     * and return an iterator of 16-bit code units.
     *
     * This is lossless:
     * calling `Wtf8Buf.fromIllFormedUtf16` on the resulting code units
     * would always return the original WTF-8 string.
     */
    public open fun encodeWide(): EncodeWide = EncodeWide(codePoints = this.codePoints(), extra = 0u)

    public open fun nextSurrogate(pos: Int): Pair<Int, UShort>? {
        var pos1 = pos
        val iter = ((this.bytes).subList(pos1, (this.bytes).size)).iter()
        while (true) {
            val b = (iter.next() ?: return null)
            if (b < 0x80) {
                pos1 += 1
            } else if (b < 0xE0) {
                iter.next()
                pos1 += 2
            } else if (b == 0xED) {
                run {
                    val matchVal = Pair(iter.next(), iter.next())
                    when {
                        matchVal.first != null && matchVal.second != null && matchVal.first >= 0xA0 -> {
                            val b2 = matchVal.first
                            val b3 = matchVal.second
                            return Pair(pos1, decodeSurrogate(b2, b3))
                        }
                        else -> pos1 += 3
                    }
                }
            } else if (b < 0xF0) {
                iter.next()
                iter.next()
                pos1 += 3
            } else {
                iter.next()
                iter.next()
                iter.next()
                pos1 += 4
            }
        }
    }

    public open fun finalLeadSurrogate(): UShort? =
        run {
            val matchVal = this.bytes
            when {
                matchVal.size >= 3 && matchVal[matchVal.size - 3] == 0xED && matchVal[matchVal.size - 2] in 0xA0..0xAF -> {
                    val b2 = matchVal[matchVal.size - 2]
                    val b3 = matchVal[matchVal.size - 1]
                    decodeSurrogate(b2, b3)
                }
                else -> null
            }
        }

    public open fun initialTrailSurrogate(): UShort? =
        run {
            val matchVal = this.bytes
            when {
                matchVal.size >= 3 && matchVal[0] == 0xED && matchVal[1] in 0xB0..0xBF -> {
                    val b2 = matchVal[1]
                    val b3 = matchVal[2]
                    decodeSurrogate(b2, b3)
                }
                else -> null
            }
        }

    public open fun makeAsciiLowercase() {
        this.bytes.makeAsciiLowercase()
    }

    public open fun makeAsciiUppercase() {
        this.bytes.makeAsciiUppercase()
    }

    public open fun isAscii(): Boolean = this.bytes.isAscii()

    public open fun eqIgnoreAsciiCase(other: Wtf8): Boolean = this.bytes.eqIgnoreAsciiCase(other.bytes)

    /**
     * Returns a slice of the given string for the byte range \[`begin`..`end`).
     *
     * # Panics
     *
     * Panics when `begin` and `end` do not point to code point boundaries,
     * or point beyond the end of the string.
     */
    public open fun index(range: Range<Int>): Wtf8 =
        if (range.start <= range.end && this.isCodePointBoundary(range.start) && this.isCodePointBoundary(range.end)) {
            // SAFETY: isCodePointBoundary checks that the index is valid
            sliceUnchecked(this, range.start.toInt(), range.end)
        } else {
            sliceErrorFail(this, range.start.toInt(), range.end)
        }

    /**
     * Returns a slice of the given string from byte `begin` to its end.
     *
     * # Panics
     *
     * Panics when `begin` is not at a code point boundary,
     * or is beyond the end of the string.
     */
    public open fun index(range: RangeFrom<Int>): Wtf8 =
        if (this.isCodePointBoundary(range.start)) {
            // SAFETY: isCodePointBoundary checks that the index is valid
            sliceUnchecked(this, range.start, this.len())
        } else {
            sliceErrorFail(this, range.start, this.len())
        }

    /**
     * Returns a slice of the given string from its beginning to byte `end`.
     *
     * # Panics
     *
     * Panics when `end` is not at a code point boundary,
     * or is beyond the end of the string.
     */
    public open fun index(range: RangeTo<Int>): Wtf8 =
        if (this.isCodePointBoundary(range.end)) {
            // SAFETY: isCodePointBoundary checks that the index is valid
            sliceUnchecked(this, 0, range.end)
        } else {
            sliceErrorFail(this, 0, range.end)
        }

    public open fun index(range: RangeFull): Wtf8 = this

    /**
     * Copied from str.isCharBoundary
     */
    public open fun isCodePointBoundary(index: Int): Boolean {
        if (index == 0) {
            return true
        }
        return when (val matchVal = this.bytes.get(index)) {
            null -> index == this.len()
            else -> {
                val b = matchVal
                (b.toByte()) >= (-0x40).toByte()
            }
        }
    }

    /**
     * Verify that `index` is at the edge of either a valid UTF-8 codepoint
     * (i.e. a codepoint that's not a surrogate) or of the whole string.
     *
     * These are the cases currently permitted by `OsStr.selfEncodedBytes`.
     * Splitting between surrogates is valid as far as WTF-8 is concerned, but
     * we do not permit it in the public API because WTF-8 is considered an
     * implementation detail.
     */
    public open fun checkUtf8Boundary(index: Int) {
        val err = when (val tryResult = this.tryCheckUtf8Boundary(index)) { is Result.Err<*, *> -> tryResult.errValue(); is Result.Ok<*, *> -> return }
        when (err) {
            Utf8BoundaryError.NotABoundary -> {
                error("byte index $index is not a codepoint boundary")
            }
            Utf8BoundaryError.OutOfBounds -> error("byte index $index is out of bounds")
            Utf8BoundaryError.BetweenSurrogates -> {
                error("byte index $index lies between surrogate codepoints")
            }
        }
    }

    public open fun tryCheckUtf8Boundary(index: Int): Result<Unit, Utf8BoundaryError> {
        if (index == 0) {
            return Result.Ok<Unit, Utf8BoundaryError>(Unit)
        }
        run {
            val matchVal = this.bytes.get(index)
            when {
                matchVal != null && matchVal == 0xED -> Unit
                // Might be a surrogate
                matchVal != null && (matchVal.toByte()) >= (-0x40).toByte() -> {
                    val b = matchVal
                    return Result.Ok<Unit, Utf8BoundaryError>(Unit)
                }
                matchVal != null -> { return Result.Err<Unit, Utf8BoundaryError>(Utf8BoundaryError.NotABoundary) }
                matchVal == null && index == this.len() -> { return Result.Ok<Unit, Utf8BoundaryError>(Unit) }
                else -> { return Result.Err<Unit, Utf8BoundaryError>(Utf8BoundaryError.OutOfBounds) }
            }
        }
        if (this.bytes[index + 1] >= 0xA0u.toUByte()) {
            // There's a surrogate after index. Now check before index.
            if (index >= 3 && this.bytes[index - 3] == 0xEDu.toUByte() && this.bytes[index - 2] >= 0xA0u.toUByte()) {
                return Result.Err<Unit, Utf8BoundaryError>(Utf8BoundaryError.BetweenSurrogates)
            }
        }
        return Result.Ok<Unit, Utf8BoundaryError>(Unit)
    }

    public override fun hashCode(): Int {
        var state = 0
        state.write(this.bytes)
        state = 31 * state + (0xfeu.toUByte()).hashCode()
        return state
    }

    public override fun cloneToUninit(dst: MutPtr) {
        // SAFETY: we're just a transparent wrapper around [u8]
        this.bytes.cloneToUninit(dst)
    }

    public companion object {
        public operator fun invoke(bytes: List<UByte>, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Wtf8 = Wtf8(bytes, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<List<UByte>> = UnresolvedTypeLayout()): Wtf8 = Wtf8(fieldStorage, fieldLayout)

        /**
         * Creates a WTF-8 slice from a UTF-8 `String` slice.
         */
        public fun fromStr(value: String): Wtf8 {
            // SAFETY: Since WTF-8 is a superset of UTF-8, this always is valid.
            return Wtf8.fromBytesUnchecked(value.encodeToByteArray().map { it.toUByte() })
        }

        /**
         * Creates a WTF-8 slice from a WTF-8 byte slice.
         *
         * # Safety
         *
         * `value` must contain well-formed WTF-8.
         */
        public fun fromBytesUnchecked(value: List<UByte>): Wtf8 {
            // SAFETY: start with &[u8], end with fancy &[u8]
            return (UnresolvedTypeLayout<Wtf8>()).readNative((((constPtrFromRustCast(value)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
        }

        /**
         * Creates a mutable WTF-8 slice from a mutable WTF-8 byte slice.
         *
         * # Safety
         *
         * `value` must contain well-formed WTF-8.
         */
        public fun fromMutBytesUnchecked(value: MutableList<UByte>): RustMutableReference<Wtf8> {
            // SAFETY: start with [u8], end with fancy [u8]
            return RustMutableReference(MutPtr((((mutPtrFromRustCast(value)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<Wtf8>())
        }
    }
}

public fun decodeSurrogate(secondByte: UByte, thirdByte: UByte): UShort {
    // The first byte is assumed to be 0xED
    return 0xD800u.toUShort() or ((secondByte.toUShort() and 0x3Fu.toUShort()).toUInt() shl 6).toUShort() or thirdByte.toUShort() and 0x3Fu.toUShort()
}

// This error type is only used temporarily to provide better panic messages
// It does not implement Error.
public sealed class Utf8BoundaryError {
    public data object NotABoundary : Utf8BoundaryError()
    public data object OutOfBounds : Utf8BoundaryError()
    public data object BetweenSurrogates : Utf8BoundaryError()

    private fun kmDiscriminant(): Int =
        when (this) {
        NotABoundary -> 0
        OutOfBounds -> 1
        BetweenSurrogates -> 2
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
 * Copied from core.str.raw.sliceUnchecked
 */
public fun sliceUnchecked(s: Wtf8, begin: Int, end: Int): Wtf8 {
    // SAFETY: memory layout of a &[u8] and &Wtf8 are the same
    return run {
        val len = end - begin
        val start = ConstPtr((s.asBytes().asPtr()).rawAddress + ((begin).toLong() * (PrimitiveLayouts.U8).strideBytes))
        Wtf8.fromBytesUnchecked(linearSliceView(start, len.toInt(), PrimitiveLayouts.U8))
    }
}

public fun sliceErrorFail(s: Wtf8, begin: Int, end: Int): Nothing {
    val len = s.len()
    if (begin > len) {
        error("start byte index $begin is out of bounds for string of length $len")
    }
    if (end > len) {
        error("end byte index $end is out of bounds for string of length $len")
    }
    if (begin > end) {
        error("byte range starts at $begin but ends at $end")
    }
    if (!s.isCodePointBoundary(begin)) {
        error("byte index $begin is not a code point boundary")
    }
    error("byte index $end is not a code point boundary")
}

/**
 * Iterator for the code points of a WTF-8 string.
 *
 * Created with the method `.codePoints()`.
 */
public open class Wtf8CodePoints(
    public var bytes: ArrayDeque<UByte>,
) : Iterable<CodePoint> {
    override fun iterator(): Iterator<CodePoint> = object : AbstractIterator<CodePoint>() {
        override fun computeNext() {
            when (val v = this@Wtf8CodePoints.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Wtf8CodePoints = Wtf8CodePoints(bytes)

    public open fun next(): CodePoint? {
        // SAFETY: `self.bytes` has been created from a WTF-8 string
        return nextCodePoint(this.bytes).map({ c -> CodePoint.fromU32Unchecked(c) })
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val len = this.bytes.size
        return Pair(len.saturatingAdd(3) / 4, len)
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { DebugTuple(f, "Wtf8CodePoints").field(Wtf8.fromBytesUnchecked(this.bytes.toList())).finish(); Result.Ok<Unit, Unit>(Unit) }
}

/**
 * Generates a wide character sequence for potentially ill-formed UTF-16.
 */
public open class EncodeWide(
    public var codePoints: Wtf8CodePoints,
    public var extra: UShort,
) : Iterable<UShort> {
    override fun iterator(): Iterator<UShort> = object : AbstractIterator<UShort>() {
        override fun computeNext() {
            when (val v = this@EncodeWide.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EncodeWide = EncodeWide(codePoints, extra)

    // Copied from libunicode/uStr.rs
    public open fun next(): UShort? {
        if (this.extra != 0u.toUShort()) {
            val tmp = this.extra
            this.extra = 0u
            return tmp
        }
        val buf = MutableList((Char.MAX_LEN_UTF16).toInt()) { 0 }
        return this.codePoints.next()?.let({ codePoint ->
            val n = encodeUtf16Raw(codePoint.toU32(), buf).len()
            if (n == 2) {
                this.extra = buf[1]
            }
            buf[0]
        })
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val (low, high) = this.codePoints.sizeHint()
        val ext = (if (this.extra != 0u.toUShort()) 1 else 0)
        // every code point gets either one u16 or two u16,
        // so this iterator is between 1 or 2 times as
        // long as the underlying iterator.
        return Pair(low + ext, high.andThen({ n -> n.checkedMul(2) }).andThen({ n -> n.checkedAdd(ext) }))
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        open class CodeUnit(
            public var value: UShort,
        ) {
            public var field0: UShort
                get() = value
                set(v) {
                    value = v
                }

            public open fun fmt(f1: StringBuilder): Result<Unit, Unit> {
                // This output attempts to balance readability with precision.
                // Render characters which take only one WTF-16 code unit using
                // `char` syntax and everything else as code units with hex
                // integer syntax (including paired and unpaired surrogate
                // halves). Since Rust has no `char`-like type for WTF-16, this
                // isn't perfect, so if this output isn't suitable, it is open
                // to being changed (see #140153).
                return when (val matchVal = Char.fromU32(this.value.toUInt())) {
                    null -> f1.writeFmt("0x${this.value}")
                    else -> {
                        val c = matchVal
                        f1.writeFmt("$c")
                    }
                }
            }
        }
        when (val tryResult = f.writeFmt("EncodeWide(")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone().map({ CodeUnit(it) })).finish()
        when (val tryResult = f.writeFmt(")")) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        return Result.Ok<Unit, Unit>(Unit)
    }
}
