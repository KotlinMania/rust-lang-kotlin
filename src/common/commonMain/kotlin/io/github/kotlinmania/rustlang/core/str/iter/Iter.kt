// port-lint: source str/iter.rs
package io.github.kotlinmania.rustlang.core.str.iter

import io.github.kotlinmania.rustlang.core.iter.adapters.copied.Copied
import io.github.kotlinmania.rustlang.core.iter.adapters.filter.Filter
import io.github.kotlinmania.rustlang.core.iter.adapters.flatten.FlatMap
import io.github.kotlinmania.rustlang.core.iter.adapters.map.Map
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccess
import io.github.kotlinmania.rustlang.core.iter.adapters.zip.TrustedRandomAccessNoCoerce
import io.github.kotlinmania.rustlang.core.iter.traits.marker.TrustedLen
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroUsizeInner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.trytrait.Try
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.BytesIsNotEmpty
import io.github.kotlinmania.rustlang.core.str.CharEscapeDebug
import io.github.kotlinmania.rustlang.core.str.CharEscapeDefault
import io.github.kotlinmania.rustlang.core.str.CharEscapeUnicode
import io.github.kotlinmania.rustlang.core.str.IsAsciiWhitespace
import io.github.kotlinmania.rustlang.core.str.IsNotEmpty
import io.github.kotlinmania.rustlang.core.str.IsWhitespace
import io.github.kotlinmania.rustlang.core.str.LinesMap
import io.github.kotlinmania.rustlang.core.str.UnsafeBytesToStr
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8Unchecked
import io.github.kotlinmania.rustlang.core.str.count.countChars
import io.github.kotlinmania.rustlang.core.str.pattern.Pattern
import io.github.kotlinmania.rustlang.core.str.pattern.Searcher
import io.github.kotlinmania.rustlang.core.str.validations.nextCodePoint
import io.github.kotlinmania.rustlang.core.str.validations.nextCodePointReverse
import io.github.kotlinmania.rustlang.core.str.validations.utf8CharWidth
import io.github.kotlinmania.rustlang.core.str.validations.utf8IsContByte
import io.github.kotlinmania.rustlang.core.iteratorGetUnchecked as underlyingIteratorGetUnchecked
import io.github.kotlinmania.rustlang.core.slice.iter.Split as SliceSplit

// Iterators for `String` methods.
//
// This macro generates a Clone impl for string pattern API
// wrapper types of the form X<a, P>
//
// This macro generates two public iterator structs
// wrapping a private internal one that makes use of the `Pattern` API.
//
// For all patterns `P: Pattern` the following items will be
// generated (generics omitted):
//
// struct $forwardIterator($internalIterator);
// struct $reverseIterator($internalIterator);
//
// impl Iterator for $forwardIterator
// ( // internal ends up calling Searcher.nextMatch() )
//
// impl DoubleEndedIterator for $forwardIterator
//       where P.Searcher: DoubleEndedSearcher
// ( // internal ends up calling Searcher.nextMatchBack() )
//
// impl Iterator for $reverseIterator
//       where P.Searcher: ReverseSearcher
// ( // internal ends up calling Searcher.nextMatchBack() )
//
// impl DoubleEndedIterator for $reverseIterator
//       where P.Searcher: DoubleEndedSearcher
// ( // internal ends up calling Searcher.nextMatch() )
//
// The internal one is defined outside the macro, and has almost the same
// semantic as a DoubleEndedIterator by delegating to `pattern.Searcher` and
// `pattern.ReverseSearcher` for both forward and reverse iteration.
//
// "Almost", because a `Searcher` and a `ReverseSearcher` for a given
// `Pattern` might not return the same elements, so actually implementing
// `DoubleEndedIterator` for it would be incorrect.
// (See the docs in `str.pattern` for more details)
//
// However, the internal struct still represents a single ended iterator from
// either end, and depending on pattern is also a valid double ended iterator,
// so the two wrapper structs implement `Iterator`
// and `DoubleEndedIterator` depending on the concrete pattern type, leading
// to the complex impls seen above.

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
 * An iterator over the [char]s of a string slice.
 *
 * This struct is created by the [chars] method on [str].
 * See its documentation for more.
 *
 * [char]: prim@char
 * [chars]: str.chars
 */
public open class Chars(
    public var iter: ArrayDeque<UByte>,
) : Iterable<Char> {
    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@Chars.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Chars = Chars(iter)

    public open fun next(): Char? {
        // SAFETY: `String` invariant says `self.iter` is a valid UTF-8 string and
        // the resulting `ch` is a valid Unicode Scalar Value.
        return nextCodePoint(this.iter).map({ ch -> Char.fromU32Unchecked(ch) })
    }

    public open fun count(): Int = countChars(this.asStr())

    public open fun advanceBy(remainder: Int): Result<Unit, NonZero<Int, NonZeroUsizeInner>> {
        var remainder1 = remainder
        val CHUNK_SIZE: Int = 32
        if (remainder1 >= CHUNK_SIZE) {
            val chunks = this.iter.toList().asChunks<ChunkSize>().first.iter()
            var bytesSkipped: Int = 0
            while (remainder1 > CHUNK_SIZE && chunks.next() != null) {
                bytesSkipped += CHUNK_SIZE
                val startBytes = BooleanArray((CHUNK_SIZE).toInt()) { false }
                for (i in 0 until CHUNK_SIZE) {
                    startBytes[i] = !utf8IsContByte(chunk[i])
                }
                remainder1 -= startBytes.map({ i1 -> i1.toUByte() }).sum<UByte>().toInt()
            }
            // SAFETY: The amount of bytes exists since we just iterated over them,
            // so advanceBy will succeed.
            this.iter.advanceBy(bytesSkipped).unwrapUnchecked()
            // skip trailing continuation bytes
            while (this.iter.size > 0) {
                val b = this.iter.toList()[0]
                if (!utf8IsContByte(b)) {
                    break
                }
                // SAFETY: We just peeked at the byte, therefore it exists
                this.iter.advanceBy(1).unwrapUnchecked()
            }
        }
        while ((remainder1 > 0) && (this.iter.size > 0)) {
            remainder1 -= 1
            val b1 = this.iter.toList()[0]
            val slurp = utf8CharWidth(b1)
            // SAFETY: utf8 validity requires that the string must contain
            // the continuation bytes (if any)
            this.iter.advanceBy(slurp).unwrapUnchecked()
        }
        return ((NonZero.new<NonZeroUsizeInner, Int>(remainder1))?.let({ Result.Err(it) }) ?: Result.Ok<Unit, NonZero<Int, NonZeroUsizeInner>>(Unit))
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val len = this.iter.size
        return Pair(len.divCeil(4), len)
    }

    public open fun last(): Char? {
        // No need to go through the entire string.
        return this.nextBack()
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        when (val tryResult = f.writeFmt("Chars(")) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Unit>(tryResult.error) }
        when (val tryResult = object { val entries = mutableListOf<Any?>(); fun entry(v: Any?) = apply { entries.add(v) }; fun entries(iter: Iterable<*>) = apply { for (e in iter) entries.add(e) }; fun finish() { f.append(entries.joinToString(", ", "[", "]")) } }.entries(this.clone()).finish()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Unit>(tryResult.error) }
        when (val tryResult = f.writeFmt(")")) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Unit>(tryResult.error) }
        return Result.Ok<Unit, Unit>(Unit)
    }

    public open fun nextBack(): Char? {
        // SAFETY: `String` invariant says `self.iter` is a valid UTF-8 string and
        // the resulting `ch` is a valid Unicode Scalar Value.
        return nextCodePointReverse(this.iter)?.let({ ch -> Char.fromU32Unchecked(ch) })
    }

    /**
     * Views the underlying data as a subslice of the original data.
     *
     * This has the same scope as the original slice, and so the
     * iterator can continue to be used while this exists.
     *
     * # Examples
     *
     * ```kotlin
     * var chars = "abc".chars()
     * check(chars == "abc")
     * chars.next()
     * check(chars == "bc")
     * chars.next()
     * chars.next()
     * check(chars == "")
     * ```
     */
    public open fun asStr(): String {
        // SAFETY: `Chars` is only made from a str, which guarantees the iter is valid UTF-8.
        return fromUtf8Unchecked(this.iter.toList())
    }
}

/**
 * An iterator over the [char]s of a string slice, and their positions.
 *
 * This struct is created by the [charIndices] method on [str].
 * See its documentation for more.
 *
 * [char]: prim@char
 * [charIndices]: str.charIndices
 */
public open class CharIndices(
    public var frontOffset: Int,
    public var iter: Chars,
) : Iterable<Pair<Int, Char>> {
    override fun iterator(): Iterator<Pair<Int, Char>> = object : AbstractIterator<Pair<Int, Char>>() {
        override fun computeNext() {
            when (val v = this@CharIndices.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): CharIndices = CharIndices(frontOffset, iter)

    public open fun next(): Pair<Int, Char>? {
        val preLen = this.iter.iter.size
        return when (val matchVal = this.iter.next()) {
            null -> null
            else -> {
                val ch = matchVal
                val index = this.frontOffset
                val len = this.iter.iter.size
                this.frontOffset += preLen - len
                Pair(index, ch)
            }
        }
    }

    public open fun count(): Int = this.iter.count()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun last(): Pair<Int, Char>? {
        // No need to go through the entire string.
        return this.nextBack()
    }

    public open fun nextBack(): Pair<Int, Char>? =
        this.iter.nextBack()?.let({ ch ->
            val index = this.frontOffset + this.iter.iter.size
            Pair(index, ch)
        })

    /**
     * Views the underlying data as a subslice of the original data.
     *
     * This has the same scope as the original slice, and so the
     * iterator can continue to be used while this exists.
     */
    public open fun asStr(): String = this.iter.asStr()

    /**
     * Returns the byte position of the next character, or the length
     * of the underlying string if there are no more characters.
     *
     * This means that, when the iterator has not been fully consumed,
     * the returned value will match the index that will be returned
     * by the next call to [`next`](Self.next).
     *
     * # Examples
     *
     * ```kotlin
     * var chars = "a楽".charIndices()
     * // `next()` has not been called yet, so `offset()` returns the byte
     * // index of the first character of the string, which is always 0.
     * check(chars.offset() == 0)
     * // As expected, the first call to `next()` also returns 0 as index.
     * check(chars.next() == Pair(0, 'a'))
     * // `next()` has been called once, so `offset()` returns the byte index
     * // of the second character ...
     * check(chars.offset() == 1)
     * // ... which matches the index returned by the next call to `next()`.
     * check(chars.next() == Pair(1, '\u697d'))
     * // Once the iterator has been consumed, `offset()` returns the length
     * // in bytes of the string.
     * check(chars.offset() == 4)
     * check(chars.next() == null)
     * ```
     */
    public open fun offset(): Int = this.frontOffset
}

/**
 * An iterator over the bytes of a string slice.
 *
 * This struct is created by the [bytes] method on [str].
 * See its documentation for more.
 *
 * [bytes]: str.bytes
 */
public open class Bytes internal constructor(
    valueCtorArg: Copied<ArrayDeque<UByte>>,
) : TrustedLen, TrustedRandomAccess, TrustedRandomAccessNoCoerce, Iterable<UByte> {
    public var value: Copied<ArrayDeque<UByte>> = valueCtorArg
        internal set

    public var field0: Copied<ArrayDeque<UByte>>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<UByte> = object : AbstractIterator<UByte>() {
        override fun computeNext() {
            when (val v = this@Bytes.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Bytes = Bytes(value)

    public override fun next(): UByte? = this.value.next()

    public override fun sizeHint(): Pair<Int, Int?> = this.value.sizeHint()

    public override fun count(): Int = this.value.count()

    public override fun last(): UByte? = this.value.last()

    public override fun nth(n: Int): UByte? = this.value.nth(n)

    public override fun all(f: (UByte) -> Boolean): Boolean = this.value.all(f)

    public override fun any(f: (UByte) -> Boolean): Boolean = this.value.any(f)

    public override fun find(predicate: (UByte) -> Boolean): UByte? = this.value.indexOf(predicate).takeIf { it >= 0 }

    public override fun position(predicate: (UByte) -> Boolean): Int? = this.value.indexOfFirst(predicate).takeIf { it >= 0 }

    public override fun rposition(predicate: (UByte) -> Boolean): Int? = this.value.indexOfLast(predicate).takeIf { it >= 0 }

    public override fun iteratorGetUnchecked(idx: Int): UByte {
        // SAFETY: the caller must uphold the safety contract
        // for `Iterator.__iterator_get_unchecked`.
        return this.value.underlyingIteratorGetUnchecked(idx)
    }

    public open fun nextBack(): UByte? = this.value.nextBack()

    public open fun nthBack(n: Int): UByte? = this.value.nthBack(n)

    public open fun rfind(predicate: (UByte) -> Boolean): UByte? = this.value.rfind(predicate)

    public open fun len(): Int = this.value.len()

    public open fun isEmpty(): Boolean = this.value.isEmpty()

    public companion object {
        public operator fun invoke(
            value: Copied<ArrayDeque<UByte>>,
        ): Bytes = Bytes(
            valueCtorArg = value,
        )

        public val MAY_HAVE_SIDE_EFFECT: Boolean = false
    }
}

public open class SplitInternal<P : Pattern<Searcher>, Searcher>(
    public var start: Int,
    public var end: Int,
    public var matcher: Searcher,
    public var allowTrailingEmpty: Boolean,
    public var finished: Boolean,
) {
    public open fun clone(): SplitInternal<P, Searcher> {
        val s = this
        return s.copy(matcher = s.matcher.clone())
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "SplitInternal").field("start", this.start).field("end", this.end).field("matcher", this.matcher).field("allow_trailing_empty", this.allowTrailingEmpty).field("finished", this.finished).finish()

    public open fun getEnd(): String? {
        if (!this.finished) {
            this.finished = true
            if (this.allowTrailingEmpty || this.end - this.start > 0) {
                // SAFETY: `self.start` and `self.end` always lie on unicode boundaries.
                val string = this.matcher.haystack().getUnchecked(this.start until this.end)
                return string
            }
        }
        return null
    }

    public open fun next(): String? {
        if (this.finished) {
            return null
        }
        val haystack = this.matcher.haystack()
        return when (val matchVal = this.matcher.nextMatch()) {
            null -> this.getEnd()
            // SAFETY: `Searcher` guarantees that:
            // - `a` and `b` lie on unicode boundaries.
            // - `a <= b <= haystack().len()` is satisfied.
            else -> {
                val a = matchVal.first
                val b = matchVal.second
                run {
                    val elt = haystack.getUnchecked(this.start until a)
                    this.start = b
                    elt
                }
            }
        }
    }

    public open fun nextInclusive(): String? {
        if (this.finished) {
            return null
        }
        val haystack = this.matcher.haystack()
        return when (val matchVal = this.matcher.nextMatch()) {
            null -> this.getEnd()
            // SAFETY: `Searcher` guarantees that:
            // - `b` lies on unicode boundary.
            // - `self.start` is either the start of the original string,
            // or `b` was assigned to it, so it also lies on unicode boundary.
            // - `self.start <= b <= haystack().len()` is satisfied.
            else -> {
                val b = matchVal.second
                run {
                    val elt = haystack.getUnchecked(this.start until b)
                    this.start = b
                    elt
                }
            }
        }
    }

    public open fun nextBack(): String? {
        if (this.finished) {
            return null
        }
        if (!this.allowTrailingEmpty) {
            this.allowTrailingEmpty = true
            run {
                val matchVal = this.nextBack()
                when {
                    matchVal != null && !matchVal.isEmpty() -> {
                        val elt = matchVal
                        return elt
                    }
                    else -> {
                        if (this.finished) {
                            return null
                        }
                    }
                }
            }
        }
        val haystack = this.matcher.haystack()
        return when (val matchVal1 = this.matcher.nextMatchBack()) {
            // SAFETY: `self.start` and `self.end` always lie on unicode boundaries.
            null -> run {
                this.finished = true
                haystack.getUnchecked(this.start until this.end)
            }
            // SAFETY: `Searcher` guarantees that:
            // - `a` and `b` lie on unicode boundaries.
            // - `self.end` is either the end of the original string,
            // or `a` was assigned to it, so it also lies on unicode boundary.
            // - `a <= b <= haystack().len()` is satisfied.
            else -> {
                val a = matchVal1.first
                val b = matchVal1.second
                run {
                    val elt1 = haystack.getUnchecked(b until this.end)
                    this.end = a
                    elt1
                }
            }
        }
    }

    public open fun nextBackInclusive(): String? {
        if (this.finished) {
            return null
        }
        if (!this.allowTrailingEmpty) {
            this.allowTrailingEmpty = true
            run {
                val matchVal = this.nextBackInclusive()
                when {
                    matchVal != null && !matchVal.isEmpty() -> {
                        val elt = matchVal
                        return elt
                    }
                    else -> {
                        if (this.finished) {
                            return null
                        }
                    }
                }
            }
        }
        val haystack = this.matcher.haystack()
        return when (val matchVal1 = this.matcher.nextMatchBack()) {
            // SAFETY: self.start is either the start of the original string,
            // or start of a substring that represents the part of the string that hasn't
            // iterated yet. Either way, it is guaranteed to lie on unicode boundary.
            // self.end is either the end of the original string,
            // or `b` was assigned to it, so it also lies on unicode boundary.
            null -> run {
                this.finished = true
                haystack.getUnchecked(this.start until this.end)
            }
            // SAFETY: `Searcher` guarantees that:
            // - `b` lies on unicode boundary.
            // - self.end is either the end of the original string,
            // or `b` was assigned to it, so it also lies on unicode boundary.
            // - `self.end <= b <= haystack.len()` is satisfied.
            else -> {
                val b = matchVal1.second
                run {
                    val elt1 = haystack.getUnchecked(b until this.end)
                    this.end = b
                    elt1
                }
            }
        }
    }

    public open fun remainder(): String? {
        // `Self.getEnd` doesn't change `self.start`
        if (this.finished) {
            return null
        }
        // SAFETY: `self.start` and `self.end` always lie on unicode boundaries.
        return this.matcher.haystack().getUnchecked(this.start until this.end)
    }
}

/**
 * Created with the method [split].
 *
 * [split]: str.split
 */
public open class Split<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: SplitInternal<P, Searcher>,
) : Iterable<String> {
    public var value: SplitInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: SplitInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@Split.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "Split").field(this.value).finish()

    public open fun next(): String? = this.value.next()

    public open fun clone(): Split<P, Searcher> = Split(this.value.clone())

    public open fun nextBack(): String? = this.value.nextBack()

    /**
     * Returns remainder of the split string.
     *
     * If the iterator is empty, returns `None`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(strSplitRemainder)
     * public fun doctestEntry() {
     *     var split = "Mary had a little lamb".split(' ')
     *     check(split.remainder() == "Mary had a little lamb")
     *     split.next()
     *     check(split.remainder() == "had a little lamb")
     *     split.byRef().forEach(drop)
     *     check(split.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): String? = this.value.remainder()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: SplitInternal<P, Searcher>,
        ): Split<P, Searcher> = Split<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

/**
 * Created with the method [rsplit].
 *
 * [rsplit]: str.rsplit
 */
public open class RSplit<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: SplitInternal<P, Searcher>,
) : Iterable<String> {
    public var value: SplitInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: SplitInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@RSplit.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "RSplit").field(this.value).finish()

    public open fun next(): String? = this.value.nextBack()

    public open fun clone(): RSplit<P, Searcher> = RSplit(this.value.clone())

    public open fun nextBack(): String? = this.value.next()

    /**
     * Returns remainder of the split string.
     *
     * If the iterator is empty, returns `None`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(strSplitRemainder)
     * public fun doctestEntry() {
     *     var split = "Mary had a little lamb".rsplit(' ')
     *     check(split.remainder() == "Mary had a little lamb")
     *     split.next()
     *     check(split.remainder() == "Mary had a little")
     *     split.byRef().forEach(drop)
     *     check(split.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): String? = this.value.remainder()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: SplitInternal<P, Searcher>,
        ): RSplit<P, Searcher> = RSplit<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

/**
 * Created with the method [splitTerminator].
 *
 * [splitTerminator]: str.splitTerminator
 */
public open class SplitTerminator<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: SplitInternal<P, Searcher>,
) : Iterable<String> {
    public var value: SplitInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: SplitInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@SplitTerminator.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "SplitTerminator").field(this.value).finish()

    public open fun next(): String? = this.value.next()

    public open fun clone(): SplitTerminator<P, Searcher> = SplitTerminator(this.value.clone())

    public open fun nextBack(): String? = this.value.nextBack()

    /**
     * Returns remainder of the split string.
     *
     * If the iterator is empty, returns `None`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(strSplitRemainder)
     * public fun doctestEntry() {
     *     var split = "A..B..".splitTerminator('.')
     *     check(split.remainder() == "A..B..")
     *     split.next()
     *     check(split.remainder() == ".B..")
     *     split.byRef().forEach(drop)
     *     check(split.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): String? = this.value.remainder()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: SplitInternal<P, Searcher>,
        ): SplitTerminator<P, Searcher> = SplitTerminator<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

/**
 * Created with the method [rsplitTerminator].
 *
 * [rsplitTerminator]: str.rsplitTerminator
 */
public open class RSplitTerminator<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: SplitInternal<P, Searcher>,
) : Iterable<String> {
    public var value: SplitInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: SplitInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@RSplitTerminator.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "RSplitTerminator").field(this.value).finish()

    public open fun next(): String? = this.value.nextBack()

    public open fun clone(): RSplitTerminator<P, Searcher> = RSplitTerminator(this.value.clone())

    public open fun nextBack(): String? = this.value.next()

    /**
     * Returns remainder of the split string.
     *
     * If the iterator is empty, returns `None`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(strSplitRemainder)
     * public fun doctestEntry() {
     *     var split = "A..B..".rsplitTerminator('.')
     *     check(split.remainder() == "A..B..")
     *     split.next()
     *     check(split.remainder() == "A..B")
     *     split.byRef().forEach(drop)
     *     check(split.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): String? = this.value.remainder()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: SplitInternal<P, Searcher>,
        ): RSplitTerminator<P, Searcher> = RSplitTerminator<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

public open class SplitNInternal<P : Pattern<Searcher>, Searcher> internal constructor(
    iterCtorArg: SplitInternal<P, Searcher>,
    /**
     * The number of splits remaining
     */
    public var count: Int,
) {
    public var iter: SplitInternal<P, Searcher> = iterCtorArg
        internal set

    public open fun clone(): SplitNInternal<P, Searcher> {
        val s = this
        return s.copy(iter = s.iter.clone())
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "SplitNInternal").field("iter", this.iter).field("count", this.count).finish()

    public open fun next(): String? =
        when (this.count) {
            0 -> null
            1 -> {
                this.count = 0
                this.iter.getEnd()
            }
            else -> {
                this.count -= 1
                this.iter.next()
            }
        }

    public open fun nextBack(): String? =
        when (this.count) {
            0 -> null
            1 -> {
                this.count = 0
                this.iter.getEnd()
            }
            else -> {
                this.count -= 1
                this.iter.nextBack()
            }
        }

    public open fun remainder(): String? = this.iter.remainder()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            iter: SplitInternal<P, Searcher>,
            count: Int,
        ): SplitNInternal<P, Searcher> = SplitNInternal<P, Searcher>(
            iterCtorArg = iter,
            count = count,
        )
    }
}

/**
 * Created with the method [splitn].
 *
 * [splitn]: str.splitn
 */
public open class SplitN<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: SplitNInternal<P, Searcher>,
) : Iterable<String> {
    public var value: SplitNInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: SplitNInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@SplitN.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "SplitN").field(this.value).finish()

    public open fun next(): String? = this.value.next()

    public open fun clone(): SplitN<P, Searcher> = SplitN(this.value.clone())

    /**
     * Returns remainder of the split string.
     *
     * If the iterator is empty, returns `None`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(strSplitRemainder)
     * public fun doctestEntry() {
     *     var split = "Mary had a little lamb".splitn(3, ' ')
     *     check(split.remainder() == "Mary had a little lamb")
     *     split.next()
     *     check(split.remainder() == "had a little lamb")
     *     split.byRef().forEach(drop)
     *     check(split.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): String? = this.value.remainder()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: SplitNInternal<P, Searcher>,
        ): SplitN<P, Searcher> = SplitN<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

/**
 * Created with the method [rsplitn].
 *
 * [rsplitn]: str.rsplitn
 */
public open class RSplitN<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: SplitNInternal<P, Searcher>,
) : Iterable<String> {
    public var value: SplitNInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: SplitNInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@RSplitN.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "RSplitN").field(this.value).finish()

    public open fun next(): String? = this.value.nextBack()

    public open fun clone(): RSplitN<P, Searcher> = RSplitN(this.value.clone())

    /**
     * Returns remainder of the split string.
     *
     * If the iterator is empty, returns `None`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(strSplitRemainder)
     * public fun doctestEntry() {
     *     var split = "Mary had a little lamb".rsplitn(3, ' ')
     *     check(split.remainder() == "Mary had a little lamb")
     *     split.next()
     *     check(split.remainder() == "Mary had a little")
     *     split.byRef().forEach(drop)
     *     check(split.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): String? = this.value.remainder()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: SplitNInternal<P, Searcher>,
        ): RSplitN<P, Searcher> = RSplitN<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

public open class MatchIndicesInternal<P : Pattern<Searcher>, Searcher>(
    public var value: Searcher,
) {
    public var field0: Searcher
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): MatchIndicesInternal<P, Searcher> {
        val s = this
        return MatchIndicesInternal(s.value.clone())
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "MatchIndicesInternal").field(this.value).finish()

    public open fun next(): Pair<Int, String>? = this.value.nextMatch().map({ (start, end) -> Pair(start, this.value.haystack().getUnchecked(start until end)) })

    public open fun nextBack(): Pair<Int, String>? = this.value.nextMatchBack().map({ (start, end) -> Pair(start, this.value.haystack().getUnchecked(start until end)) })
}

/**
 * Created with the method [matchIndices].
 *
 * [matchIndices]: str.matchIndices
 */
public open class MatchIndices<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: MatchIndicesInternal<P, Searcher>,
) : Iterable<Pair<Int, String>> {
    public var value: MatchIndicesInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: MatchIndicesInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Pair<Int, String>> = object : AbstractIterator<Pair<Int, String>>() {
        override fun computeNext() {
            when (val v = this@MatchIndices.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "MatchIndices").field(this.value).finish()

    public open fun next(): Pair<Int, String>? = this.value.next()

    public open fun clone(): MatchIndices<P, Searcher> = MatchIndices(this.value.clone())

    public open fun nextBack(): Pair<Int, String>? = this.value.nextBack()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: MatchIndicesInternal<P, Searcher>,
        ): MatchIndices<P, Searcher> = MatchIndices<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

/**
 * Created with the method [rmatchIndices].
 *
 * [rmatchIndices]: str.rmatchIndices
 */
public open class RMatchIndices<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: MatchIndicesInternal<P, Searcher>,
) : Iterable<Pair<Int, String>> {
    public var value: MatchIndicesInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: MatchIndicesInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Pair<Int, String>> = object : AbstractIterator<Pair<Int, String>>() {
        override fun computeNext() {
            when (val v = this@RMatchIndices.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "RMatchIndices").field(this.value).finish()

    public open fun next(): Pair<Int, String>? = this.value.nextBack()

    public open fun clone(): RMatchIndices<P, Searcher> = RMatchIndices(this.value.clone())

    public open fun nextBack(): Pair<Int, String>? = this.value.next()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: MatchIndicesInternal<P, Searcher>,
        ): RMatchIndices<P, Searcher> = RMatchIndices<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

public open class MatchesInternal<P : Pattern<Searcher>, Searcher>(
    public var value: Searcher,
) {
    public var field0: Searcher
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): MatchesInternal<P, Searcher> {
        val s = this
        return MatchesInternal(s.value.clone())
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "MatchesInternal").field(this.value).finish()

    public open fun next(): String? {
        // SAFETY: `Searcher` guarantees that `start` and `end` lie on unicode boundaries.
        return this.value.nextMatch().map({ (a, b) -> this.value.haystack().getUnchecked(a until b) })
    }

    public open fun nextBack(): String? {
        // SAFETY: `Searcher` guarantees that `start` and `end` lie on unicode boundaries.
        return this.value.nextMatchBack().map({ (a, b) -> this.value.haystack().getUnchecked(a until b) })
    }
}

/**
 * Created with the method [matches].
 *
 * [matches]: str.matches
 */
public open class Matches<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: MatchesInternal<P, Searcher>,
) : Iterable<String> {
    public var value: MatchesInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: MatchesInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@Matches.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "Matches").field(this.value).finish()

    public open fun next(): String? = this.value.next()

    public open fun clone(): Matches<P, Searcher> = Matches(this.value.clone())

    public open fun nextBack(): String? = this.value.nextBack()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: MatchesInternal<P, Searcher>,
        ): Matches<P, Searcher> = Matches<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

/**
 * Created with the method [rmatches].
 *
 * [rmatches]: str.rmatches
 */
public open class RMatches<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: MatchesInternal<P, Searcher>,
) : Iterable<String> {
    public var value: MatchesInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: MatchesInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@RMatches.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugTuple(f, "RMatches").field(this.value).finish()

    public open fun next(): String? = this.value.nextBack()

    public open fun clone(): RMatches<P, Searcher> = RMatches(this.value.clone())

    public open fun nextBack(): String? = this.value.next()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: MatchesInternal<P, Searcher>,
        ): RMatches<P, Searcher> = RMatches<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

/**
 * An iterator over the lines of a string, as string slices.
 *
 * This struct is created with the [lines] method on [str].
 * See its documentation for more.
 *
 * [lines]: str.lines
 */
public open class Lines internal constructor(
    valueCtorArg: Map<SplitInclusive<Char, Searcher>, LinesMap>,
) : Iterable<String> {
    public var value: Map<SplitInclusive<Char, Searcher>, LinesMap> = valueCtorArg
        internal set

    public var field0: Map<SplitInclusive<Char, Searcher>, LinesMap>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@Lines.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Lines = Lines(value)

    public open fun next(): String? = this.value.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.value.sizeHint()

    public open fun last(): String? = this.nextBack()

    public open fun nextBack(): String? = this.value.nextBack()

    /**
     * Returns the remaining lines of the split string.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(strLinesRemainder)
     * public fun doctestEntry() {
     *     var lines = "a\nb\nc\nd".lines()
     *     check(lines.remainder() == "a\nb\nc\nd")
     *     lines.next()
     *     check(lines.remainder() == "b\nc\nd")
     *     lines.byRef().forEach(drop)
     *     check(lines.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): String? = this.value.iter.remainder()

    public companion object {
        public operator fun invoke(
            value: Map<SplitInclusive<Char, Searcher>, LinesMap>,
        ): Lines = Lines(
            valueCtorArg = value,
        )
    }
}

/**
 * Created with the method [linesAny].
 *
 * [linesAny]: str.linesAny
 */
public open class LinesAny(
    public var value: Lines,
) : Iterable<String> {
    public var field0: Lines
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@LinesAny.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): LinesAny = LinesAny(value)

    public open fun next(): String? = this.value.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.value.sizeHint()

    public open fun nextBack(): String? = this.value.nextBack()
}

/**
 * An iterator over the non-whitespace substrings of a string,
 * separated by any amount of whitespace.
 *
 * This struct is created by the [splitWhitespace] method on [str].
 * See its documentation for more.
 *
 * [splitWhitespace]: str.splitWhitespace
 */
public open class SplitWhitespace internal constructor(
    innerCtorArg: Filter<Split<IsWhitespace, Searcher>, IsNotEmpty>,
) : Iterable<String> {
    public var inner: Filter<Split<IsWhitespace, Searcher>, IsNotEmpty> = innerCtorArg
        internal set

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@SplitWhitespace.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): SplitWhitespace = SplitWhitespace(inner)

    public open fun next(): String? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun last(): String? = this.nextBack()

    public open fun nextBack(): String? = this.inner.nextBack()

    /**
     * Returns remainder of the split string
     *
     * # Examples
     *
     * ```kotlin
     * @feature(strSplitWhitespaceRemainder)
     * public fun doctestEntry() {
     *     var split = "Mary had a little lamb".splitWhitespace()
     *     check(split.remainder() == "Mary had a little lamb")
     *     split.next()
     *     check(split.remainder() == "had a little lamb")
     *     split.byRef().forEach(drop)
     *     check(split.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): String? = this.inner.iter.remainder()

    public companion object {
        public operator fun invoke(
            inner: Filter<Split<IsWhitespace, Searcher>, IsNotEmpty>,
        ): SplitWhitespace = SplitWhitespace(
            innerCtorArg = inner,
        )
    }
}

/**
 * An iterator over the non-ASCII-whitespace substrings of a string,
 * separated by any amount of ASCII whitespace.
 *
 * This struct is created by the [splitAsciiWhitespace] method on [str].
 * See its documentation for more.
 *
 * [splitAsciiWhitespace]: str.splitAsciiWhitespace
 */
public open class SplitAsciiWhitespace internal constructor(
    innerCtorArg: Map<Filter<SliceSplit<UByte, IsAsciiWhitespace>, BytesIsNotEmpty>, UnsafeBytesToStr>,
) : Iterable<String> {
    public var inner: Map<Filter<SliceSplit<UByte, IsAsciiWhitespace>, BytesIsNotEmpty>, UnsafeBytesToStr> = innerCtorArg
        internal set

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@SplitAsciiWhitespace.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): SplitAsciiWhitespace = SplitAsciiWhitespace(inner)

    public open fun next(): String? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun last(): String? = this.nextBack()

    public open fun nextBack(): String? = this.inner.nextBack()

    /**
     * Returns remainder of the split string.
     *
     * If the iterator is empty, returns `None`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(strSplitWhitespaceRemainder)
     * public fun doctestEntry() {
     *     var split = "Mary had a little lamb".splitAsciiWhitespace()
     *     check(split.remainder() == "Mary had a little lamb")
     *     split.next()
     *     check(split.remainder() == "had a little lamb")
     *     split.byRef().forEach(drop)
     *     check(split.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): String? {
        if (this.inner.iter.iter.finished) {
            return null
        }
        // SAFETY: Slice is created from str.
        return fromUtf8Unchecked(this.inner.iter.iter.v)
    }

    public companion object {
        public operator fun invoke(
            inner: Map<Filter<SliceSplit<UByte, IsAsciiWhitespace>, BytesIsNotEmpty>, UnsafeBytesToStr>,
        ): SplitAsciiWhitespace = SplitAsciiWhitespace(
            innerCtorArg = inner,
        )
    }
}

/**
 * An iterator over the substrings of a string,
 * terminated by a substring matching to a predicate function.
 * Unlike `Split`, it contains the matched part as a terminator
 * of the subslice.
 *
 * This struct is created by the [splitInclusive] method on [str].
 * See its documentation for more.
 *
 * [splitInclusive]: str.splitInclusive
 */
public open class SplitInclusive<P : Pattern<Searcher>, Searcher> internal constructor(
    valueCtorArg: SplitInternal<P, Searcher>,
) : Iterable<String> {
    public var value: SplitInternal<P, Searcher> = valueCtorArg
        internal set

    public var field0: SplitInternal<P, Searcher>
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<String> = object : AbstractIterator<String>() {
        override fun computeNext() {
            when (val v = this@SplitInclusive.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): String? = this.value.nextInclusive()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "SplitInclusive").field("0", this.value).finish()

    // Note(#26925) Remove in favor of `@derive(Clone)`
    public open fun clone(): SplitInclusive<P, Searcher> = SplitInclusive(this.value.clone())

    public open fun nextBack(): String? = this.value.nextBackInclusive()

    /**
     * Returns remainder of the split string.
     *
     * If the iterator is empty, returns `None`.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(strSplitInclusiveRemainder)
     * public fun doctestEntry() {
     *     var split = "Mary had a little lamb".splitInclusive(' ')
     *     check(split.remainder() == "Mary had a little lamb")
     *     split.next()
     *     check(split.remainder() == "had a little lamb")
     *     split.byRef().forEach(drop)
     *     check(split.remainder() == null)
     * }
     * ```
     */
    public open fun remainder(): String? = this.value.remainder()

    public companion object {
        public operator fun <P : Pattern<Searcher>, Searcher> invoke(
            value: SplitInternal<P, Searcher>,
        ): SplitInclusive<P, Searcher> = SplitInclusive<P, Searcher>(
            valueCtorArg = value,
        )
    }
}

/**
 * An iterator of [u16] over the string encoded as UTF-16.
 *
 * This struct is created by the [encodeUtf16] method on [str].
 * See its documentation for more.
 *
 * [encodeUtf16]: str.encodeUtf16
 */
public open class EncodeUtf16(
    public var chars: Chars,
    public var extra: UShort,
) : Iterable<UShort> {
    override fun iterator(): Iterator<UShort> = object : AbstractIterator<UShort>() {
        override fun computeNext() {
            when (val v = this@EncodeUtf16.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EncodeUtf16 = EncodeUtf16(chars, extra)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "EncodeUtf16").finishNonExhaustive()

    public open fun next(): UShort? {
        if (this.extra != 0u.toUShort()) {
            val tmp = this.extra
            this.extra = 0u
            return tmp
        }
        val buf = MutableList(2) { 0 }
        return this.chars.next()?.let({ ch ->
            val n = ch.encodeUtf16(buf).len()
            if (n == 2) {
                this.extra = buf[1]
            }
            buf[0]
        })
    }

    public open fun sizeHint(): Pair<Int, Int?> {
        val len = this.chars.iter.size
        // The highest bytes:code units ratio occurs for 3-byte sequences,
        // since a 4-byte sequence results in 2 code units. The lower bound
        // is therefore determined by assuming the remaining bytes contain as
        // many 3-byte sequences as possible. The highest bytes:code units
        // ratio is for 1-byte sequences, so use this for the upper bound.
        return if (this.extra == 0u.toUShort()) {
            Pair(len.divCeil(3), len)
        } else {
            // We're in the middle of a surrogate pair, so add the remaining
            // surrogate to the bounds.
            Pair(len.divCeil(3) + 1, len + 1)
        }
    }

    public open fun count(): Int {
        val extra =
            if (this.extra == 0u.toUShort()) {
                0
            } else {
                1
            }
        // Find UTF-8 non-continuation bytes and map:
        //
        // lenUtf8() | lenUtf16()
        // -----------|------------
        // 1          | 1
        // 2          | 1
        // 3          | 1
        // 4          | 2
        //
        // Because we never map to larger values the result is never larger than
        // `self.chars.asStr().len()` so the sum can never overflow
        return this.chars.asStr().encodeToByteArray().map { it.toUByte() }.fold(extra, { acc, byte ->
            acc.wrappingAdd(if (byte < 0x80) {
                // 0b0xxxXxxx: ASCII-compatible U+0000 to U+007F
                1
            } else if (byte < 0xC0) {
                // 0b10xxXxxx: UTF-8 continuation byte, skip
                0
            } else if (byte < 0xF0) {
                // 0b110xXxxx: start of a 2-byte UTF-8 sequence for U+0080 to U+07FF
                // 0b1110Xxxx: start of a 3-byte UTF-8 sequence for U+0800 to U+FFFF
                1
            } else {
                // 0b11110xxx: start of a 4-byte UTF-8 sequence for U+010000 to U+10FFFF
                // This is exactly the range encoded as a surrogate pair in UTF-16
                //
                // 0b11111xxx: would fall here but never occurs in valid UTF-8
                2
            })
        })
    }
}

/**
 * The return type of [str.escapeDebug].
 */
public open class EscapeDebug internal constructor(
    innerCtorArg: FlatMap<Chars, io.github.kotlinmania.rustlang.core.char.EscapeDebug, CharEscapeDebug>,
) : Iterable<Char> {
    public var inner: FlatMap<Chars, io.github.kotlinmania.rustlang.core.char.EscapeDebug, CharEscapeDebug> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@EscapeDebug.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EscapeDebug = EscapeDebug(inner)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = this.clone().tryForEach({ c -> f.append(c) })

    public open fun next(): Char? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <Residual, Acc, R> tryFold(init: Acc, fold: (Acc, Char) -> R): R where R : Try<R, Acc, Residual> = this.inner.tryFold(init, fold)

    public open fun <Acc> fold(init: Acc, fold: (Acc, Char) -> Acc): Acc = this.inner.fold(init, fold)

    public companion object {
        public operator fun invoke(
            inner: FlatMap<Chars, io.github.kotlinmania.rustlang.core.char.EscapeDebug, CharEscapeDebug>,
        ): EscapeDebug = EscapeDebug(
            innerCtorArg = inner,
        )
    }
}

/**
 * The return type of [str.escapeDefault].
 */
public open class EscapeDefault internal constructor(
    innerCtorArg: FlatMap<Chars, io.github.kotlinmania.rustlang.core.char.EscapeDefault, CharEscapeDefault>,
) : Iterable<Char> {
    public var inner: FlatMap<Chars, io.github.kotlinmania.rustlang.core.char.EscapeDefault, CharEscapeDefault> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@EscapeDefault.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EscapeDefault = EscapeDefault(inner)

    /**
     * Disassembles the iterator into its internal state.
     * Used by alloc for an optimized toString impl.
     */
    public open fun intoParts(): Triple<Any?, Chars?, Any?> = this.inner.intoParts()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = this.clone().tryForEach({ c -> f.append(c) })

    public open fun next(): Char? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <Residual, Acc, R> tryFold(init: Acc, fold: (Acc, Char) -> R): R where R : Try<R, Acc, Residual> = this.inner.tryFold(init, fold)

    public open fun <Acc> fold(init: Acc, fold: (Acc, Char) -> Acc): Acc = this.inner.fold(init, fold)

    public companion object {
        public operator fun invoke(
            inner: FlatMap<Chars, io.github.kotlinmania.rustlang.core.char.EscapeDefault, CharEscapeDefault>,
        ): EscapeDefault = EscapeDefault(
            innerCtorArg = inner,
        )
    }
}

/**
 * The return type of [str.escapeUnicode].
 */
public open class EscapeUnicode internal constructor(
    innerCtorArg: FlatMap<Chars, io.github.kotlinmania.rustlang.core.char.EscapeUnicode, CharEscapeUnicode>,
) : Iterable<Char> {
    public var inner: FlatMap<Chars, io.github.kotlinmania.rustlang.core.char.EscapeUnicode, CharEscapeUnicode> = innerCtorArg
        internal set

    override fun iterator(): Iterator<Char> = object : AbstractIterator<Char>() {
        override fun computeNext() {
            when (val v = this@EscapeUnicode.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): EscapeUnicode = EscapeUnicode(inner)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = this.clone().tryForEach({ c -> f.append(c) })

    public open fun next(): Char? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun <Residual, Acc, R> tryFold(init: Acc, fold: (Acc, Char) -> R): R where R : Try<R, Acc, Residual> = this.inner.tryFold(init, fold)

    public open fun <Acc> fold(init: Acc, fold: (Acc, Char) -> Acc): Acc = this.inner.fold(init, fold)

    public companion object {
        public operator fun invoke(
            inner: FlatMap<Chars, io.github.kotlinmania.rustlang.core.char.EscapeUnicode, CharEscapeUnicode>,
        ): EscapeUnicode = EscapeUnicode(
            innerCtorArg = inner,
        )
    }
}
