// port-lint: source slice/ascii.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.slice.ascii

import io.github.kotlinmania.rustlang.ascii.EscapeDefault
import io.github.kotlinmania.rustlang.ascii.escapeDefault
import io.github.kotlinmania.rustlang.core.char.isAsciiWhitespace
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.intrinsics.alignOf
import io.github.kotlinmania.rustlang.intrinsics.sizeOf
import io.github.kotlinmania.rustlang.iter.adapters.filter.Filter
import io.github.kotlinmania.rustlang.mmLoaduSi128
import io.github.kotlinmania.rustlang.mmMovemaskEpi8
import io.github.kotlinmania.rustlang.mmOrSi128
import io.github.kotlinmania.rustlang.ops.function.Fn
import io.github.kotlinmania.rustlang.ops.function.FnMut
import io.github.kotlinmania.rustlang.ops.function.FnOnce
import io.github.kotlinmania.rustlang.ops.trytrait.Try
import io.github.kotlinmania.rustlang.slice.iter.Iter
import io.github.kotlinmania.rustlang.slice.iter.Split
import io.github.kotlinmania.rustlang.str.BytesIsNotEmpty
import io.github.kotlinmania.rustlang.str.IsAsciiWhitespace
import io.github.kotlinmania.rustlang.str.converts.fromUtf8Unchecked
import io.github.kotlinmania.rustlang.str.count.USIZE_SIZE
import io.github.kotlinmania.rustlang.str.validations.NONASCII_MASK
import io.github.kotlinmania.rustlang.vld1qU8
import io.github.kotlinmania.rustlang.vmaxvqU8
import io.github.kotlinmania.rustlang.vorrqU8

// Operations on ASCII `[UByte]`.

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * Checks if all bytes in this slice are within the ASCII range.
 *
 * An empty slice returns `true`.
 */
public fun List<UByte>.isAscii(): Boolean = isAscii(this)

/**
 * If this slice [`isAscii`](Self.isAscii), returns it as a slice of
 * [ASCII characters](ascii.Char), otherwise returns `None`.
 */
public fun List<UByte>.asAscii(): List<io.github.kotlinmania.rustlang.ascii.Char>? =
    if (this.isAscii()) {
        // SAFETY: Just checked that it's ASCII
        this.asAsciiUnchecked()
    } else {
        null
    }

/**
 * Converts this slice of bytes into a slice of ASCII characters,
 * without checking whether they're valid.
 *
 * # Safety
 *
 * Every byte in the slice must be in `0 ..= 127`, or else this is UB.
 */
public fun List<UByte>.asAsciiUnchecked(): List<io.github.kotlinmania.rustlang.ascii.Char> {
    val bytePtr: ConstPtr = this
    val asciiPtr = ConstPtr(bytePtr.rawAddress, bytePtr.rawMeta)
    // SAFETY: The caller promised all the bytes are ASCII
    return (ObjectSlotLayout<List<io.github.kotlinmania.rustlang.ascii.Char>>()).readNative((asciiPtr).rawAddress)
}

/**
 * Checks that two slices are an ASCII case-insensitive match.
 *
 * Same as `toAsciiLowercase(a) == toAsciiLowercase(b)`,
 * but without allocating and copying temporaries.
 */
public fun List<UByte>.eqIgnoreAsciiCase(other: List<UByte>): Boolean {
    if (this.size != other.size) {
        return false
    }
    run {
        val CHUNK_SIZE: Int = 16
        // The following function has two invariants:
        // 1. The slice lengths must be equal, which we checked above.
        // 2. The slice lengths must greater than or equal to N, which this
        // if-statement is checking.
        if (this.size >= CHUNK_SIZE) {
            return this.eqIgnoreAsciiCaseChunks<ChunkSize>(other)
        }
    }
    return this.eqIgnoreAsciiCaseSimple(other)
}

/**
 * ASCII case-insensitive equality check without chunk-at-a-time
 * optimization.
 */
public fun List<UByte>.eqIgnoreAsciiCaseSimple(other: List<UByte>): Boolean {
    // Note(const-Note): This implementation can be reverted when
    // `core.iter.zip` is allowed in const. The original implementation:
    // self.len() == other.len() && iter.zip(self, other).all(|(a, b)| a.eqIgnoreAsciiCase(b))
    var a = this
    var b = other
    while (Pair(a, b) != null) {
        val firstA = Pair(a, b).first[0]
        val restA = Pair(a, b).first.subList(1, Pair(a, b).first.size)
        val firstB = Pair(a, b).second[0]
        val restB = Pair(a, b).second.subList(1, Pair(a, b).second.size)
        if (firstA.eqIgnoreAsciiCase(firstB)) {
            a = restA
            b = restB
        } else {
            return false
        }
    }
    return true
}

/**
 * Optimized version of `eqIgnoreAsciiCase` to process chunks at a time.
 *
 * Platforms that have SIMD instructions may benefit from this
 * implementation over `eqIgnoreAsciiCaseSimple`.
 *
 * # Invariants
 *
 * The caller must guarantee that the slices are equal in length, and the
 * slice lengths are greater than or equal to `N` bytes.
 */
public fun List<UByte>.eqIgnoreAsciiCaseChunks(other: List<UByte>, n: Int): Boolean {
    // Branchless check to encourage auto-vectorization
    fun eqIgnoreAsciiInner(lhs: UByteArray, rhs: UByteArray, l: Int): Boolean {
        var equalAscii = true
        var j = 0
        while (j < l) {
            equalAscii = equalAscii and lhs[j].eqIgnoreAsciiCase(rhs[j])
            j += 1
        }
        return equalAscii
    }
    // Note(const-Note): The while-loops that follow should be replaced by
    // for-loops when available in const.
    val (selfChunks, selfRem) = this.asChunks()
    val (otherChunks, _) = other.asChunks()
    // Process the chunks, returning early if an inequality is found
    var i = 0
    while (i < selfChunks.len() && i < otherChunks.len()) {
        if (!eqIgnoreAsciiInner(selfChunks[i], otherChunks[i])) {
            return false
        }
        i += 1
    }
    if (false) {
        check(this.size >= n)
    }
    // If there are remaining tails, load the last N bytes in the slices to
    // avoid falling back to per-byte checking.
    if (!selfRem.isEmpty()) {
        if (Pair(this.lastChunk(), other.lastChunk()) != null) {
            val aRem = Pair(this.lastChunk(), other.lastChunk()).first
            val bRem = Pair(this.lastChunk(), other.lastChunk()).second
            if (!eqIgnoreAsciiInner(aRem, bRem)) {
                return false
            }
        }
    }
    return true
}

/**
 * Converts this slice to its ASCII upper case equivalent in-place.
 *
 * ASCII letters 'a' to 'z' are mapped to 'A' to 'Z',
 * but non-ASCII letters are unchanged.
 *
 * To return a new uppercased value without modifying the existing one, use
 * [toAsciiUppercase].
 *
 * [toAsciiUppercase]: #method.toAsciiUppercase
 */
public fun List<UByte>.makeAsciiUppercase() {
    // Note(const-Note): We would like to simply iterate using `for` loops but this isn't currently allowed in constant expressions.
    var i = 0
    while (i < this.size) {
        val byte = this[i]
        byte.makeAsciiUppercase()
        i += 1
    }
}

/**
 * Converts this slice to its ASCII lower case equivalent in-place.
 *
 * ASCII letters 'A' to 'Z' are mapped to 'a' to 'z',
 * but non-ASCII letters are unchanged.
 *
 * To return a new lowercased value without modifying the existing one, use
 * [toAsciiLowercase].
 *
 * [toAsciiLowercase]: #method.toAsciiLowercase
 */
public fun List<UByte>.makeAsciiLowercase() {
    // Note(const-Note): We would like to simply iterate using `for` loops but this isn't currently allowed in constant expressions.
    var i = 0
    while (i < this.size) {
        val byte = this[i]
        byte.makeAsciiLowercase()
        i += 1
    }
}

/**
 * Returns an iterator that produces an escaped version of this slice,
 * treating it as an ASCII string.
 *
 * # Examples
 *
 * ```kotlin
 * val s = "0\t\r\n'\"\\".encodeToByteArray()
 * val escaped = s.escapeAscii().toString()
 * check(escaped == "0\\t\\r\\n\\'\\\"\\\\\\x9d")
 * ```
 */
public fun List<UByte>.escapeAscii(): EscapeAscii = EscapeAscii(inner = this.flatMap(EscapeByte()))

/**
 * Returns a byte slice with leading ASCII whitespace bytes removed.
 *
 * 'Whitespace' refers to the definition used by
 * [u8.isAsciiWhitespace]. Importantly, this definition excludes
 * the `\0x0B` byte even though it has the Unicode [WhiteSpace] property
 * and is removed by [str.trimStart].
 *
 * [WhiteSpace]: https://www.unicode.org/reports/tr44/#WhiteSpace
 *
 * # Examples
 *
 * ```kotlin
 * check(" \t hello world\n".encodeToByteArray().trimAsciiStart() == "hello world\n".encodeToByteArray())
 * check("  ".encodeToByteArray().trimAsciiStart() == "".encodeToByteArray())
 * check("".encodeToByteArray().trimAsciiStart() == "".encodeToByteArray())
 * ```
 */
public fun List<UByte>.trimAsciiStart(): List<UByte> {
    var bytes = this
    // Note: A pattern matching based approach (instead of indexing) allows
    // making the function const.
    while (bytes != null) {
        val first = bytes[0]
        val rest = bytes.subList(1, bytes.size)
        if (first.isAsciiWhitespace()) {
            bytes = rest
        } else {
            break
        }
    }
    return bytes
}

/**
 * Returns a byte slice with trailing ASCII whitespace bytes removed.
 *
 * 'Whitespace' refers to the definition used by
 * [u8.isAsciiWhitespace]. Importantly, this definition excludes
 * the `\0x0B` byte even though it has the Unicode [WhiteSpace] property
 * and is removed by [str.trimEnd].
 *
 * [WhiteSpace]: https://www.unicode.org/reports/tr44/#WhiteSpace
 *
 * # Examples
 *
 * ```kotlin
 * check("\r hello world\n ".encodeToByteArray().trimAsciiEnd() == "\r hello world".encodeToByteArray())
 * check("  ".encodeToByteArray().trimAsciiEnd() == "".encodeToByteArray())
 * check("".encodeToByteArray().trimAsciiEnd() == "".encodeToByteArray())
 * ```
 */
public fun List<UByte>.trimAsciiEnd(): List<UByte> {
    var bytes = this
    // Note: A pattern matching based approach (instead of indexing) allows
    // making the function const.
    while (bytes != null) {
        val rest = bytes.subList(0, bytes.size - 1)
        val last = bytes[bytes.size - 1]
        if (last.isAsciiWhitespace()) {
            bytes = rest
        } else {
            break
        }
    }
    return bytes
}

/**
 * Returns a byte slice with leading and trailing ASCII whitespace bytes
 * removed.
 *
 * 'Whitespace' refers to the definition used by
 * [u8.isAsciiWhitespace]. Importantly, this definition excludes
 * the `\0x0B` byte even though it has the Unicode [WhiteSpace] property
 * and is removed by [str.trim].
 *
 * [WhiteSpace]: https://www.unicode.org/reports/tr44/#WhiteSpace
 *
 * # Examples
 *
 * ```kotlin
 * check("\r hello world\n ".encodeToByteArray().trimAscii() == "hello world".encodeToByteArray())
 * check("  ".encodeToByteArray().trimAscii() == "".encodeToByteArray())
 * check("".encodeToByteArray().trimAscii() == "".encodeToByteArray())
 * ```
 */
public fun List<UByte>.trimAscii(): List<UByte> = this.trimAsciiStart().trimAsciiEnd()

/**
 * Splits a byte slice by ASCII whitespace.
 *
 * The returned iterator yields byte slices that are subslices of the
 * original byte slice, separated by any amount of ASCII whitespace.
 *
 * This uses the same definition as [u8.isAsciiWhitespace].
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @feature(u8SplitAsciiWhitespace)
 * public fun doctestEntry() {
 *     var iter = "A few words".encodeToByteArray().splitAsciiWhitespace()
 *     check("A".encodeToByteArray() == iter.next())
 *     check("few".encodeToByteArray() == iter.next())
 *     check("words".encodeToByteArray() == iter.next())
 *     check(null == iter.next())
 * }
 * ```
 *
 * Various kinds of ASCII whitespace are considered
 * (see [u8.isAsciiWhitespace]):
 *
 * ```kotlin
 * @feature(u8SplitAsciiWhitespace)
 * public fun doctestEntry() {
 *     var iter = " Mary   had\ta little  \n\t lamb".encodeToByteArray().splitAsciiWhitespace()
 *     check("Mary".encodeToByteArray() == iter.next())
 *     check("had".encodeToByteArray() == iter.next())
 *     check("a".encodeToByteArray() == iter.next())
 *     check("little".encodeToByteArray() == iter.next())
 *     check("lamb".encodeToByteArray() == iter.next())
 *     check(null == iter.next())
 * }
 * ```
 *
 * If the byte slice is empty or contains only ASCII whitespace, the iterator
 * yields no byte slices:
 *
 * ```kotlin
 * @feature(u8SplitAsciiWhitespace)
 * public fun doctestEntry() {
 *     check("".encodeToByteArray().splitAsciiWhitespace().next() == null)
 *     check("   ".encodeToByteArray().splitAsciiWhitespace().next() == null)
 * }
 * ```
 */
public fun List<UByte>.splitAsciiWhitespace(): SplitAsciiWhitespace {
    val inner = this.split(IsAsciiWhitespace).filter(BytesIsNotEmpty)
    return SplitAsciiWhitespace(inner = inner)
}

/**
 * An iterator over the non-ASCII-whitespace subslices of a byte slice,
 * separated by any amount of ASCII whitespace.
 *
 * This struct is created by the [splitAsciiWhitespace] method on [`[u8]`][byteslice].
 * See its documentation for more.
 *
 * [splitAsciiWhitespace]: slice.splitAsciiWhitespace
 * [byteslice]: prim@slice
 */
public open class SplitAsciiWhitespace internal constructor(
    innerCtorArg: Filter<Split<UByte, IsAsciiWhitespace>, BytesIsNotEmpty>,
) : Iterable<List<UByte>> {
    public var inner: Filter<Split<UByte, IsAsciiWhitespace>, BytesIsNotEmpty> = innerCtorArg
        internal set

    override fun iterator(): Iterator<List<UByte>> = object : AbstractIterator<List<UByte>>() {
        override fun computeNext() {
            when (val v = this@SplitAsciiWhitespace.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): SplitAsciiWhitespace = SplitAsciiWhitespace(inner)

    public open fun next(): List<UByte>? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun last(): List<UByte>? = this.nextBack()

    public open fun nextBack(): List<UByte>? = this.inner.nextBack()

    // This is also blocked on: https://github.com/rust-lang/rust/issues/77998

    /**
     * Returns remainder of the split slice.
     *
     * If the iterator is empty, returns `None`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(u8SplitAsciiWhitespace)
     * public fun doctestEntry() {
     *     var split = "Mary had a little lamb".encodeToByteArray().splitAsciiWhitespace()
     *     check(split.remainder() == "Mary had a little lamb".encodeToByteArray().asSlice())
     *     split.next()
     *     check(split.remainder() == "had a little lamb".encodeToByteArray().asSlice())
     *     split.byRef().forEach(drop)
     *     check(split.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): List<UByte>? {
        if (this.inner.iter.finished) {
            return null
        }
        return this.inner.iter.v
    }

    public companion object {
        public operator fun invoke(
            inner: Filter<Split<UByte, IsAsciiWhitespace>, BytesIsNotEmpty>,
        ): SplitAsciiWhitespace = SplitAsciiWhitespace(
            innerCtorArg = inner,
        )
    }
}

public open class EscapeByte : Fn<UByte>, FnMut<UByte>, FnOnce<UByte, EscapeDefault> {
    public open fun clone(): EscapeByte = EscapeByte()

    public override fun call(tupleArg0: UByte): EscapeDefault = escapeDefault(byte)

    public override fun callMut(tupleArg0: UByte): EscapeDefault = this.call(byte)

    public override fun callOnce(tupleArg0: UByte): EscapeDefault = this.call(byte)

    public companion object {
        public fun typeLayout(): TypeLayout<EscapeByte> =
            object : TypeLayout<EscapeByte> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): EscapeByte =
                    EscapeByte()
                override fun writeNative(addr: Long, value: EscapeByte) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): EscapeByte =
                    EscapeByte()
                override fun writeBytes(buf: ByteArray, offset: Int, value: EscapeByte) {
                }
            }
    }
}

/**
 * An iterator over the escaped version of a byte slice.
 *
 * This `class` is created by the [slice.escapeAscii] method. See its
 * documentation for more information.
 */
public open class EscapeAscii internal constructor(
    innerCtorArg: FlatMap<Iter<UByte>, EscapeDefault, EscapeByte>,
) : Iterable<UByte> {
    public var inner: FlatMap<Iter<UByte>, EscapeDefault, EscapeByte> = innerCtorArg
        internal set

    override fun iterator(): Iterator<UByte> = object : AbstractIterator<UByte>() {
        override fun computeNext() {
            when (val v = this@EscapeAscii.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EscapeAscii = EscapeAscii(inner)

    public open fun next(): UByte? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <Acc, R> tryFold(init: Acc, fold: (Acc, UByte) -> R): R where R : Try<R, Acc, Any?> = this.inner.tryFold(init, fold)

    public open fun <Acc> fold(init: Acc, fold: (Acc, UByte) -> Acc): Acc = this.inner.fold(init, fold)

    public open fun last(): UByte? = this.nextBack()

    public open fun nextBack(): UByte? = this.inner.nextBack()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        fun needsEscape(b: UByte): Boolean = b > 0x7Eu.toUByte() || b < 0x20u.toUByte() || b == 92u || b == 39u || b == 34u
        // disassemble iterator, including front/back parts of flatmap in case it has been partially consumed
        val (front, slice, back) = this.clone().inner.intoParts()
        val front1 = front.unwrapOr(EscapeDefault.empty())
        var bytes = slice.unwrapOrDefault().asSlice()
        val back1 = back.unwrapOr(EscapeDefault.empty())
        // usually empty, so the formatter won't have to do any work
        for (byte in front1) {
            f.append(byte.toInt().toChar())
        }
        while (!bytes.isEmpty()) {
            // fast path for the printable, non-escaped subset of ascii
            val prefix = bytes.iter().takeWhile({ b -> !needsEscape(b) }).count()
            // SAFETY: prefix length was derived by counting bytes in the same splice, so it's in-bounds
            val (prefix1, remainder) = bytes.splitAtUnchecked(prefix)
            // SAFETY: prefix is a valid utf8 sequence, as it's a subset of ASCII
            val prefix2 = fromUtf8Unchecked(prefix1)
            f.append(prefix2)
            // the fast part
            bytes = remainder
            when (val matchVal = bytes.first()) {
                null -> {}
                else -> {
                    val b = matchVal
                    // guaranteed to be non-empty, better to write it as a str
                    f.append((escapeDefault(b)).toString())
                    bytes = bytes[1 until bytes.size]
                }
            }
        }
        // also usually empty
        for (byte1 in back1) {
            f.append(byte1.toInt().toChar())
        }
        return Result.Ok(Unit)
    }

    public open fun fmtDebug(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "EscapeAscii").finishNonExhaustive()

    public companion object {
        public operator fun invoke(
            inner: FlatMap<Iter<UByte>, EscapeDefault, EscapeByte>,
        ): EscapeAscii = EscapeAscii(
            innerCtorArg = inner,
        )
    }
}

/**
 * ASCII test *without* the chunk-at-a-time optimizations.
 *
 * This is carefully structured to produce nice small code -- it's smaller in
 * `- O` than what the "obvious" ways produces under `- C opt - level = s`.  If you
 * touch it, be sure to run (and update if needed) the assembly test.
 */
public fun isAsciiSimple(bytes: List<UByte>): Boolean {
    var bytes1 = bytes
    while (bytes1 != null) {
        val rest = bytes1.subList(0, bytes1.size - 1)
        val last = bytes1[bytes1.size - 1]
        if (!last.isAscii()) {
            break
        }
        bytes1 = rest
    }
    return bytes1.isEmpty()
}

// fallback impl has same behavior

/**
 * Optimized ASCII test that will use Int-at-a-time operations instead of
 * byte-at-a-time operations (when possible).
 *
 * The algorithm we use here is pretty simple. If `s` is too short, we just
 * check each byte and be done with it. Otherwise:
 *
 * - Read the first word with an unaligned load.
 * - Align the pointer, read subsequent words until end with aligned loads.
 * - Read the last `Int` from `s` with an unaligned load.
 *
 * If any of these loads produces something for which `containsNonascii`
 * (above) returns true, then we know the answer is false.
 */
public fun isAscii(s: List<UByte>): Boolean {
    // The runtime version behaves the same as the compiletime version, it's
    // just more optimized.
    return run {
        /**
         * Returns `true` if any byte in the word `v` is nonascii (>= 128). Snarfed
         * from `.. / String / mod.rs`, which does something similar for utf8 validation.
         */
        fun containsNonascii(v: Int): Boolean {
            val NONASCII_MASK: Int = Int.repeatU8(0x80u)
            return (NONASCII_MASK and v) != 0
        }
        val USIZE_SIZE: Int = sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)
        val len = s.size
        val alignOffset = s.asPtr().alignOffset(USIZE_SIZE)
        if (len < USIZE_SIZE || len < alignOffset || USIZE_SIZE < alignOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)) {
            return isAsciiSimple(s)
        }
        val offsetToAligned =
            if (alignOffset == 0) {
                USIZE_SIZE
            } else {
                alignOffset
            }
        val start = s.asPtr()
        val firstWord = (PrimitiveLayouts.USIZE64).readNative(((ConstPtr(start.rawAddress, start.rawMeta))).rawAddress)
        if (containsNonascii(firstWord)) {
            return false
        }
        check(offsetToAligned <= len)
        var wordPtr = (ConstPtr((start).rawAddress + ((offsetToAligned).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
        var bytePos = offsetToAligned
        check(wordPtr.isAlignedTo(alignOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)))
        while (bytePos < len - USIZE_SIZE) {
            check(bytePos + USIZE_SIZE <= len)
            check(((ConstPtr((wordPtr).rawAddress)).rawAddress.toULong() == (ConstPtr((start).rawAddress + ((bytePos).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress.toULong()))
            val word = (PrimitiveLayouts.USIZE64).readNative((wordPtr).rawAddress)
            if (containsNonascii(word)) {
                return false
            }
            bytePos += USIZE_SIZE
            wordPtr = ConstPtr((wordPtr).rawAddress + ((1).toLong() * (PrimitiveLayouts.USIZE64).strideBytes))
        }
        check(bytePos <= len && len - bytePos <= USIZE_SIZE)
        val lastWord = (PrimitiveLayouts.USIZE64).readNative((((ConstPtr((start).rawAddress + ((len - USIZE_SIZE).toLong() * (PrimitiveLayouts.U8).strideBytes))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
        !containsNonascii(lastWord)
    }
}

/**
 * Chunk size for SSE2 vectorized ASCII checking (4x 16-byte loads).
 */
public val SSE2_CHUNK_SIZE: Int = 64

public fun isAsciiSse2(bytes: List<UByte>): Boolean {
    val (chunks, rest) = bytes.asChunks()
    for (chunk in chunks) {
        val ptr = chunk.asPtr()
        // SAFETY: chunk is 64 bytes. SSE2 is baseline on x8664.
        val mask =
            run {
                val a1 = mmLoaduSi128(constPtrFromRustCast(ptr))
                val a2 = mmLoaduSi128(constPtrFromRustCast(ptr.add(16)))
                val b1 = mmLoaduSi128(constPtrFromRustCast(ptr.add(32)))
                val b2 = mmLoaduSi128(constPtrFromRustCast(ptr.add(48)))
                // OR all chunks - if any byte has high bit set, combined will too.
                val combined = mmOrSi128(mmOrSi128(a1, a2), mmOrSi128(b1, b2))
                // Create a mask from the MSBs of each byte.
                // If any byte is >= 128, its MSB is 1, so the mask will be non-zero.
                mmMovemaskEpi8(combined)
            }
        if (mask != 0) {
            return false
        }
    }
    // Handle remaining bytes
    return rest.iter().all({ b -> b.isAscii() })
}

/**
 * Chunk size for NEON vectorized ASCII checking (4x 16-byte loads).
 */
public val NEON_CHUNK_SIZE: Int = 64

/**
 * Width of a single NEON vector, used to vectorize the tail left over by the
 * unrolled `NEON_CHUNK_SIZE` loop.
 */
public val NEON_VECTOR_SIZE: Int = 16

public fun isAsciiNeon(bytes: List<UByte>): Boolean {
    val (chunks, rest) = bytes.asChunks()
    for (chunk in chunks) {
        val ptr = chunk.asPtr()
        // SAFETY: chunk is 64 bytes, and `vld1qU8` has no alignment requirement.
        val max =
            run {
                val a1 = vld1qU8(ptr)
                val a2 = vld1qU8(ptr.add(16))
                val b1 = vld1qU8(ptr.add(32))
                val b2 = vld1qU8(ptr.add(48))
                // OR all chunks - if any byte has high bit set, combined will too.
                val combined = vorrqU8(vorrqU8(a1, a2), vorrqU8(b1, b2))
                // `vmaxvqU8` is a horizontal reduction with a longer latency than
                // `vorrqU8`, so it runs once per 64 bytes rather than once per load.
                vmaxvqU8(combined)
            }
        if (max >= 128.0f) {
            return false
        }
    }
    // The unrolled loop above leaves up to 63 bytes, so sweep those a vector at
    // a time before falling back to a byte-at-a-time check.
    val (vectors, rest1) = rest.asChunks()
    for (vector in vectors) {
        // SAFETY: vector is 16 bytes, and `vld1qU8` has no alignment requirement.
        val max1 = vmaxvqU8(vld1qU8(vector.asPtr()))
        if (max1 >= 128.0f) {
            return false
        }
    }
    // Handle remaining bytes
    return rest1.iter().all({ b -> b.isAscii() })
}
