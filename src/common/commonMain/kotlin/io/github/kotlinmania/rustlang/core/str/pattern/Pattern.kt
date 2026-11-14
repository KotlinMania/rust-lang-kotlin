// port-lint: source str/pattern.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.core.str.pattern

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.num.rustSaturatingSub
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.eq
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.core.str.iter.CharIndices
import io.github.kotlinmania.rustlang.core.str.rustStrChars
import io.github.kotlinmania.rustlang.core.simd.Mask8x16 as Mask
import io.github.kotlinmania.rustlang.core.simd.U8x16 as Block

// The string Pattern API.
//
// The Pattern API provides a generic mechanism for using different pattern
// types when searching through a string.
//
// For more details, see the traits [Pattern], [Searcher],
// [ReverseSearcher], and [DoubleEndedSearcher].
//
// Although this API is unstable, it is exposed via stable APIs on the
// [str] type.
//
// # Examples
//
// [Pattern] is [implemented][pattern-impls] in the stable API for
// [`str`][str], [char], slices of [char], and functions and closures
// implementing `FnMut(char) -> Boolean`.
//
// ```kotlin
// val s = "Can you find a needle in a haystack?"
// // &str pattern
// check(s.indexOf("you").takeIf { it >= 0 } == 4)
// // char pattern
// check(s.indexOf('n').takeIf { it >= 0 } == 2)
// // array of chars pattern
// check(s.indexOf(listOf('a', 'e', 'i', 'o', 'u')).takeIf { it >= 0 } == 1)
// // slice of chars pattern
// check(s.indexOf(listOf('a', 'e', 'i', 'o', 'u')).takeIf { it >= 0 } == 1)
// // closure pattern
// check(s.indexOf({ c: Char -> c.isAsciiPunctuation() }).takeIf { it >= 0 } == 35)
// ```
//
// [pattern-impls]: Pattern#implementors

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

// Pattern

/**
 * A string pattern.
 *
 * A `Pattern` expresses that the implementing type
 * can be used as a string pattern for searching in a [`str`][str].
 *
 * For example, both `'a'` and `"aa"` are patterns that
 * would match at index `1` in the string `"baaaab"`.
 *
 * The trait itself acts as a builder for an associated
 * [Searcher] type, which does the actual work of finding
 * occurrences of the pattern in a string.
 *
 * Depending on the type of the pattern, the behavior of methods like
 * [str.find] and [str.contains] can change. The table below describes
 * some of those behaviors.
 *
 * | Pattern type             | Match condition                           |
 * |--------------------------|-------------------------------------------|
 * | `String`                   | is substring                              |
 * | `char`                   | is contained in string                    |
 * | `[char]`                | any char in slice is contained in string  |
 * | `F: FnMut(char) -> Boolean` | `F` returns `true` for a char in string   |
 * | `&& String`                  | is substring                              |
 * | `String`                | is substring                              |
 *
 * # Safety
 *
 * Implementers must uphold the guarantees documented on `intoSearcher`.
 *
 * # Examples
 *
 * ```kotlin
 * // &str
 * check("abaaa".indexOf("ba").takeIf { it >= 0 } == 1)
 * check("abaaa".indexOf("bac").takeIf { it >= 0 } == null)
 * // char
 * check("abaaa".indexOf('a').takeIf { it >= 0 } == 0)
 * check("abaaa".indexOf('b').takeIf { it >= 0 } == 1)
 * check("abaaa".indexOf('c').takeIf { it >= 0 } == null)
 * // &[char; N]
 * check("ab".indexOf(listOf('b', 'a')).takeIf { it >= 0 } == 0)
 * check("abaaa".indexOf(listOf('a', 'z')).takeIf { it >= 0 } == 0)
 * check("abaaa".indexOf(listOf('c', 'd')).takeIf { it >= 0 } == null)
 * // &[char]
 * check("ab".indexOf(listOf('b', 'a')).takeIf { it >= 0 } == 0)
 * check("abaaa".indexOf(listOf('a', 'z')).takeIf { it >= 0 } == 0)
 * check("abaaa".indexOf(listOf('c', 'd')).takeIf { it >= 0 } == null)
 * // FnMut(char) -> bool
 * check("abcdefZ".indexOf({ ch -> ch > 'd' && ch < 'y' }).takeIf { it >= 0 } == 4)
 * check("abcdddZ".indexOf({ ch -> ch > 'd' && ch < 'y' }).takeIf { it >= 0 } == null)
 * ```
 */
public interface Pattern<Searcher> {
    /**
     * Constructs the associated searcher from
     * `self` and the `haystack` to search in.
     *
     * # Guarantees
     *
     * - The returned searcher must search the provided `haystack`.
     *   In particular, [Searcher.haystack] must return the
     *   same string slice passed to this method.
     */
    public fun intoSearcher(haystack: String): Searcher

    /**
     * Checks whether the pattern matches anywhere in the haystack
     */
    public fun isContainedIn(haystack: String): Boolean = this.intoSearcher(haystack).nextMatch() != null

    /**
     * Checks whether the pattern matches at the front of the haystack
     */
    public fun isPrefixOf(haystack: String): Boolean =
        run {
            val matchVal = this.intoSearcher(haystack).next()
            when {
                matchVal is SearchStep.Match && matchVal.field0 == 0 -> true
                else -> false
            }
        }

    /**
     * Checks whether the pattern matches at the back of the haystack
     */
    public fun isSuffixOf(haystack: String): Boolean =
        run {
            val matchVal = this.intoSearcher(haystack).nextBack()
            when {
                matchVal is SearchStep.Match && haystack.length == (matchVal as SearchStep.Match).field1 -> {
                    val (_, j) = matchVal
                    true
                }
                else -> false
            }
        }

    /**
     * Removes the pattern from the front of haystack, if it matches.
     */
    public fun stripPrefixOf(haystack: String): String? {
        return when (val matchVal = this.intoSearcher(haystack).next()) {
            is SearchStep.Match -> {
                val (start, len) = matchVal
                if (false) {
                    when (val matchVal1 = Pair(start, 0)) {
                        else -> {
                            val leftVal = matchVal1.first
                            val rightVal = matchVal1.second
                            if (!(leftVal == rightVal)) {
                                val kind = eq
                                assertFailed(kind, leftVal, rightVal, Option.Some("The first search step from Searcher must include the first character"))
                            }
                        }
                    }
                }
                // SAFETY: `Searcher` is known to return valid indices.
                haystack.getUnchecked(len until Int.MAX_VALUE)
            }
            else -> {
                null
            }
        }
    }

    /**
     * Removes the pattern from the back of haystack, if it matches.
     */
    public fun stripSuffixOf(haystack: String): String? {
        return when (val matchVal = this.intoSearcher(haystack).nextBack()) {
            is SearchStep.Match -> {
                val (start, end) = matchVal
                if (false) {
                    when (val matchVal1 = Pair(end, haystack.length)) {
                        else -> {
                            val leftVal = matchVal1.first
                            val rightVal = matchVal1.second
                            if (!(leftVal == rightVal)) {
                                val kind = eq
                                assertFailed(kind, leftVal, rightVal, Option.Some("The first search step from ReverseSearcher must include the last character"))
                            }
                        }
                    }
                }
                // SAFETY: `Searcher` is known to return valid indices.
                haystack.getUnchecked(0 until start)
            }
            else -> {
                null
            }
        }
    }

    /**
     * Returns the pattern as UTF-8 if possible.
     */
    public fun asUtf8Pattern(): Utf8Pattern? = null
}

/**
 * Result of calling [Pattern.asUtf8Pattern].
 * Can be used for inspecting the contents of a [Pattern] in cases
 * where the underlying representation can be represented as UTF-8.
 */
public sealed class Utf8Pattern {
    /**
     * Type returned by String and str types.
     * This stores `String` rather than bytes so callers cannot describe
     * non-UTF-8 string patterns through this API.
     */
    public data class StringPattern(public var value: String) : Utf8Pattern()
    /**
     * Type returned by char types.
     */
    public data class CharPattern(public var value: Char) : Utf8Pattern()

    public fun rustEquals(other: Utf8Pattern): Boolean = this == other
}

// Searcher

/**
 * Result of calling [Searcher.next] or [ReverseSearcher.nextBack].
 */
public sealed class SearchStep {
    /**
     * Expresses that a match of the pattern has been found at
     * `haystack[a .. b]`.
     */
    public data class Match(public var field0: Int, public var field1: Int) : SearchStep()
    /**
     * Expresses that `haystack[a .. b]` has been rejected as a possible match
     * of the pattern.
     *
     * Note that there might be more than one `Reject` between two `Match`es,
     * there is no requirement for them to be combined into one.
     */
    public data class Reject(public var field0: Int, public var field1: Int) : SearchStep()
    /**
     * Expresses that every byte of the haystack has been visited, ending
     * the iteration.
     */
    public data object Done : SearchStep()

    public fun rustEquals(other: SearchStep): Boolean = this == other
}

/**
 * A searcher for a string pattern.
 *
 * This trait provides methods for searching for non-overlapping
 * matches of a pattern starting from the front (left) of a string.
 *
 * It will be implemented by associated `Searcher`
 * types of the [Pattern] trait.
 *
 * # Safety
 *
 * Implementers must ensure that all trait functions abide by the
 * guarantees documented in the `# Guarantees` sections. The provided
 * methods automatically satisfy their guarantees.
 */
public interface Searcher {
    /**
     * Getter for the underlying string to be searched in
     *
     * # Guarantees
     *
     * Will always return the same [`str`][str].
     */
    public fun haystack(): String

    /**
     * Performs the next search step starting from the front.
     *
     * - Returns [`Match`][SearchStep.Match] if `haystack[a .. b]` matches
     *   the pattern.
     * - Returns [`Reject`][SearchStep.Reject] if `haystack[a .. b]` can
     *   not match the pattern, even partially.
     * - Returns [`Done`][SearchStep.Done] if every byte of the haystack has
     *   been visited.
     *
     * # Guarantees
     *
     * - The stream of [`Match`][SearchStep.Match] and
     *  [`Reject`][SearchStep.Reject] values up to a [`Done`][SearchStep.Done]
     *  will contain index ranges that are adjacent, non-overlapping,
     *  covering the whole haystack, and lying on utf8 boundaries. This enables
     *  consumers of this trait to slice the haystack without additional runtime checks.
     *
     * A [`Match`][SearchStep.Match] result needs to contain the whole matched
     * pattern, however [`Reject`][SearchStep.Reject] results may be split up
     * into arbitrary many adjacent fragments. Both ranges may have zero length.
     *
     * As an example, the pattern `"aaa"` and the haystack `"cbaaaaab"`
     * might produce the stream
     * `[Reject(0, 1), Reject(1, 2), Match(2, 5), Reject(5, 8)]`
     */
    public fun next(): SearchStep

    /**
     * Finds the next [`Match`][SearchStep.Match] result. See [`next`][Searcher.next].
     *
     * # Guarantees
     *
     * - Every returned `Some((a, b))` must satisfy `a <= b <= self.haystack().len()`,
     *   and both endpoints must lie on UTF-8 character boundaries.
     * - Matched ranges must be returned in forward order, without revisiting
     *   previously consumed portions of the haystack.
     *
     * Unlike [`next`][Searcher.next], there is no guarantee that the returned ranges
     * of this and [`nextReject`][Searcher.nextReject] will not overlap. This will return
     * `(startMatch, endMatch)`, where startMatch is the index of where
     * the match begins, and endMatch is the index after the end of the match.
     */
    public fun nextMatch(): Pair<Int, Int>? {
        while (true) {
            when (val matchVal = this.next()) {
                is SearchStep.Match -> {
                    val (a, b) = matchVal
                    return Pair(a, b)
                }
                SearchStep.Done -> { return null }
                else -> { continue }
            }
        }
    }

    /**
     * Finds the next [`Reject`][SearchStep.Reject] result. See [`next`][Searcher.next]
     * and [`nextMatch`][Searcher.nextMatch].
     *
     * # Guarantees
     *
     * - Every returned `Some((a, b))` must satisfy `a <= b <= self.haystack().len()`,
     *   and both endpoints must lie on UTF-8 character boundaries.
     * - Matched ranges must be returned in forward order, without revisiting
     *   previously consumed portions of the haystack.
     *
     * Unlike [`next`][Searcher.next], there is no guarantee that the returned ranges
     * of this and [`nextMatch`][Searcher.nextMatch] will overlap.
     */
    public fun nextReject(): Pair<Int, Int>? {
        while (true) {
            when (val matchVal = this.next()) {
                is SearchStep.Reject -> {
                    val (a, b) = matchVal
                    return Pair(a, b)
                }
                SearchStep.Done -> { return null }
                else -> { continue }
            }
        }
    }
}

/**
 * A reverse searcher for a string pattern.
 *
 * This trait provides methods for searching for non-overlapping
 * matches of a pattern starting from the back (right) of a string.
 *
 * It will be implemented by associated [Searcher]
 * types of the [Pattern] trait if the pattern supports searching
 * for it from the back.
 *
 * The index ranges returned by this trait are not required
 * to exactly match those of the forward search in reverse.
 *
 * # Safety
 *
 * Implementers must ensure that all trait functions abide by the
 * guarantees documented in the `# Guarantees` sections. The provided
 * methods automatically satisfy their guarantees.
 */
public interface ReverseSearcher : Searcher {
    /**
     * Performs the next search step starting from the back.
     *
     * - Returns [`Match`][SearchStep.Match] if `haystack[a .. b]`
     *   matches the pattern.
     * - Returns [`Reject`][SearchStep.Reject] if `haystack[a .. b]`
     *   can not match the pattern, even partially.
     * - Returns [`Done`][SearchStep.Done] if every byte of the haystack
     *   has been visited
     *
     * # Guarantees
     *
     * - The stream of [`Match`][SearchStep.Match] and
     *   [`Reject`][SearchStep.Reject] values up to a [`Done`][SearchStep.Done]
     *   will contain index ranges that are adjacent, non-overlapping,
     *   covering the whole haystack, and lying on utf8 boundaries.
     *
     * A [`Match`][SearchStep.Match] result needs to contain the whole matched
     * pattern, however [`Reject`][SearchStep.Reject] results may be split up
     * into arbitrary many adjacent fragments. Both ranges may have zero length.
     *
     * As an example, the pattern `"aaa"` and the haystack `"cbaaaaab"`
     * might produce the stream
     * `[Reject(7, 8), Match(4, 7), Reject(1, 4), Reject(0, 1)]`.
     */
    public fun nextBack(): SearchStep

    /**
     * Finds the next [`Match`][SearchStep.Match] result. See [`nextBack`][ReverseSearcher.nextBack].
     *
     * # Guarantees
     *
     * - Every returned `Some((a, b))` must satisfy `a <= b <= self.haystack().len()`,
     *   and both endpoints must lie on UTF-8 character boundaries.
     * - Matched ranges must be returned in forward order, without revisiting
     *   previously consumed portions of the haystack.
     */
    public fun nextMatchBack(): Pair<Int, Int>? {
        while (true) {
            when (val matchVal = this.nextBack()) {
                is SearchStep.Match -> {
                    val (a, b) = matchVal
                    return Pair(a, b)
                }
                SearchStep.Done -> { return null }
                else -> { continue }
            }
        }
    }

    /**
     * Finds the next [`Reject`][SearchStep.Reject] result. See [`nextBack`][ReverseSearcher.nextBack].
     *
     * # Guarantees
     *
     * - Every returned `Some((a, b))` must satisfy `a <= b <= self.haystack().len()`,
     *   and both endpoints must lie on UTF-8 character boundaries.
     * - Matched ranges must be returned in forward order, without revisiting
     *   previously consumed portions of the haystack.
     */
    public fun nextRejectBack(): Pair<Int, Int>? {
        while (true) {
            when (val matchVal = this.nextBack()) {
                is SearchStep.Reject -> {
                    val (a, b) = matchVal
                    return Pair(a, b)
                }
                SearchStep.Done -> { return null }
                else -> { continue }
            }
        }
    }
}

/**
 * A marker trait to express that a [ReverseSearcher]
 * can be used for a [DoubleEndedIterator] implementation.
 *
 * For this, the impl of [Searcher] and [ReverseSearcher] need
 * to follow these conditions:
 *
 * - All results of `next()` need to be identical
 *   to the results of `nextBack()` in reverse order.
 * - `next()` and `nextBack()` need to behave as
 *   the two ends of a range of values, that is they
 *   can not "walk past each other".
 *
 * # Examples
 *
 * `char.Searcher` is a `DoubleEndedSearcher` because searching for a
 * [char] only requires looking at one at a time, which behaves the same
 * from both ends.
 *
 * `(String).Searcher` is not a `DoubleEndedSearcher` because
 * the pattern `"aa"` in the haystack `"aaa"` matches as either
 * `"[aa]a"` or `"a[aa]"`, depending on which side it is searched.
 */
public interface DoubleEndedSearcher : ReverseSearcher

// ////////////////////////////////////////////////////////////////////////
// Impl for char
// ////////////////////////////////////////////////////////////////////////

/**
 * Associated type for `<char as Pattern>.Searcher<>`.
 */
public open class CharSearcher(
    public var haystack: String,
    // safety invariant: `finger`/`fingerBack` must be a valid utf8 byte index of `haystack`
    // This invariant can be broken *within* nextMatch and nextMatchBack, however
    // they must exit with fingers on valid code point boundaries.
    /**
     * `finger` is the current byte index of the forward search.
     * Imagine that it exists before the byte at its index, i.e.
     * `haystack[finger]` is the first byte of the slice we must inspect during
     * forward searching
     */
    public var finger: Int,
    /**
     * `fingerBack` is the current byte index of the reverse search.
     * Imagine that it exists after the byte at its index, i.e.
     * haystack[fingerBack - 1] is the last byte of the slice we must inspect during
     * forward searching (and thus the first byte to be inspected when calling nextBack()).
     */
    public var fingerBack: Int,
    /**
     * The character being searched for
     */
    public var needle: Char,
    // safety invariant: `utf8Size` must be less than 5
    /**
     * The number of bytes `needle` takes up when encoded in utf8.
     */
    public var utf8Size: UByte,
    /**
     * A utf8 encoded copy of the `needle`
     */
    public var utf8Encoded: UByteArray,
) : Searcher, ReverseSearcher, DoubleEndedSearcher {
    public open fun utf8Size(): Int = this.utf8Size.into()

    public override fun haystack(): String = this.haystack

    public override fun next(): SearchStep {
        val oldFinger = this.finger
        // SAFETY: 1-4 guarantee safety of `getUnchecked`
        // 1. `self.finger` and `self.fingerBack` are kept on unicode boundaries
        // (this is invariant)
        // 2. `self.finger >= 0` since it starts at 0 and only increases
        // 3. `self.finger<self.fingerBack` because otherwise the char `iter`
        // would return `SearchStep.Done`
        // 4. `self.finger` comes before the end of the haystack because `self.fingerBack`
        // starts at the end and only decreases
        val slice = this.haystack.getUnchecked(oldFinger until this.fingerBack)
        val iter = slice.chars()
        val oldLen = iter.iter.len()
        return when (val matchVal = iter.next()) {
            null -> {
                SearchStep.Done
            }
            else -> {
                val ch = matchVal
                // add byte offset of current character
                // without re-encoding as utf-8
                this.finger += oldLen - iter.iter.len()
                if (ch == this.needle) {
                    SearchStep.Match(oldFinger, this.finger)
                } else {
                    SearchStep.Reject(oldFinger, this.finger)
                }
            }
        }
    }

    public override fun nextMatch(): Pair<Int, Int>? {
        while (true) {
            // get the haystack after the last character found
            val bytes = (this.haystack.encodeToByteArray().map { it.toUByte() }.get(this.finger until this.fingerBack) ?: return null)
            // the last byte of the utf8 encoded needle
            // SAFETY: we have an invariant that `utf8Size<5`
            val lastByte = this.utf8Encoded.getUnchecked(this.utf8Size() - 1)
            when (val matchVal = memchr.memchr(lastByte, bytes)) {
                null -> {
                    // found nothing, exit
                    this.finger = this.fingerBack
                    return null
                }
                else -> {
                    val index = matchVal
                    // The new finger is the index of the byte we found,
                    // plus one, since we memchr'd for the last byte of the character.
                    //
                    // Note that this doesn't always give us a finger on a UTF8 boundary.
                    // If we *didn't* find our character
                    // we may have indexed to the non-last byte of a 3-byte or 4-byte character.
                    // We can't just skip to the next valid starting byte because a character like
                    // ꁁ (U+A041 YI SYLLABLE PA), utf-8 `EA 81 81` will have us always find
                    // the second byte when searching for the third.
                    //
                    // However, this is totally okay. While we have the invariant that
                    // self.finger is on a UTF8 boundary, this invariant is not relied upon
                    // within this method (it is relied upon in CharSearcher.next()).
                    //
                    // We only exit this method when we reach the end of the string, or if we
                    // find something. When we find something the `finger` will be set
                    // to a UTF8 boundary.
                    this.finger += index + 1
                    if (this.finger >= this.utf8Size()) {
                        val foundChar = this.finger - this.utf8Size()
                        when (val matchVal1 = this.haystack.encodeToByteArray().map { it.toUByte() }.get(foundChar until this.finger)) {
                            null -> {}
                            else -> {
                                val slice = matchVal1
                                if (slice.contentEquals(((this.utf8Encoded).sliceArray(0 until this.utf8Size())))) {
                                    return Pair(foundChar, this.finger)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public override fun nextBack(): SearchStep {
        val oldFinger = this.fingerBack
        // SAFETY: see the comment for next() above
        val slice = this.haystack.getUnchecked(this.finger until oldFinger)
        val iter = slice.chars()
        val oldLen = iter.iter.len()
        return when (val matchVal = iter.nextBack()) {
            null -> {
                SearchStep.Done
            }
            else -> {
                val ch = matchVal
                // subtract byte offset of current character
                // without re-encoding as utf-8
                this.fingerBack -= oldLen - iter.iter.len()
                if (ch == this.needle) {
                    SearchStep.Match(this.fingerBack, oldFinger)
                } else {
                    SearchStep.Reject(this.fingerBack, oldFinger)
                }
            }
        }
    }

    public override fun nextMatchBack(): Pair<Int, Int>? {
        val haystack = this.haystack.encodeToByteArray().map { it.toUByte() }
        while (true) {
            // get the haystack up to but not including the last character searched
            val bytes = (haystack.get(this.finger until this.fingerBack) ?: return null)
            // the last byte of the utf8 encoded needle
            // SAFETY: we have an invariant that `utf8Size<5`
            val lastByte = this.utf8Encoded.getUnchecked(this.utf8Size() - 1)
            when (val matchVal = memchr.memrchr(lastByte, bytes)) {
                null -> {
                    this.fingerBack = this.finger
                    // found nothing, exit
                    return null
                }
                else -> {
                    val index = matchVal
                    // we searched a slice that was offset by self.finger,
                    // add self.finger to recoup the original index
                    val index1 = this.finger + index
                    // memrchr will return the index of the byte we wish to
                    // find. In case of an ASCII character, this is indeed
                    // were we wish our new finger to be ("after" the found
                    // char in the paradigm of reverse iteration). For
                    // multibyte chars we need to skip down by the number of more
                    // bytes they have than ASCII
                    val shift = this.utf8Size() - 1
                    if (index1 >= shift) {
                        val foundChar = index1 - shift
                        when (val matchVal1 = haystack.get(foundChar until (foundChar + this.utf8Size()))) {
                            null -> {}
                            else -> {
                                val slice = matchVal1
                                if (slice.contentEquals(((this.utf8Encoded).sliceArray(0 until this.utf8Size())))) {
                                    // move finger to before the character found (i.e., at its start index)
                                    this.fingerBack = foundChar
                                    return Pair(this.fingerBack, this.fingerBack + this.utf8Size())
                                }
                            }
                        }
                    }
                    // We can't use fingerBack = index - size + 1 here. If we found the last char
                    // of a different-sized character (or the middle byte of a different character)
                    // we need to bump the fingerBack down to `index`. This similarly makes
                    // `fingerBack` have the potential to no longer be on a boundary,
                    // but this is OK since we only exit this function on a boundary
                    // or when the haystack has been searched completely.
                    //
                    // Unlike nextMatch this does not
                    // have the problem of repeated bytes in utf-8 because
                    // we're searching for the last byte, and we can only have
                    // found the last byte when searching in reverse.
                    this.fingerBack = index1
                }
            }
        }
    }
}

/**
 * Searches for chars that are equal to a given [char].
 *
 * # Examples
 *
 * ```kotlin
 * check("Hello world".indexOf('o').takeIf { it >= 0 } == 4)
 * ```
 */
public fun Char.intoSearcher(haystack: String): CharSearcher {
    var utf8Encoded = MutableList((Char.MAX_LEN_UTF8).toInt()) { 0 }
    val utf8Size = this.encodeUtf8(utf8Encoded).len().tryInto().expect("char len should be less than 255")
    return CharSearcher(haystack = haystack, finger = 0, fingerBack = haystack.length, needle = this, utf8Size = utf8Size, utf8Encoded = utf8Encoded)
}

public fun Char.isContainedIn(haystack: String): Boolean =
    if ((this.toUInt()) < 128u) {
        haystack.encodeToByteArray().map { it.toUByte() }.contains((this.toUByte()))
    } else {
        val buffer = UByteArray(4) { 0u.toUByte() }
        this.encodeUtf8(buffer).isContainedIn(haystack)
    }

public fun Char.isPrefixOf(haystack: String): Boolean = this.encodeUtf8(UByteArray(4) { 0u.toUByte() }).isPrefixOf(haystack)

public fun Char.stripPrefixOf(haystack: String): String? = this.encodeUtf8(UByteArray(4) { 0u.toUByte() }).stripPrefixOf(haystack)

public fun Char.isSuffixOf(haystack: String): Boolean = this.encodeUtf8(UByteArray(4) { 0u.toUByte() }).isSuffixOf(haystack)

public fun Char.stripSuffixOf(haystack: String): String? = this.encodeUtf8(UByteArray(4) { 0u.toUByte() }).stripSuffixOf(haystack)

public fun Char.asUtf8Pattern(): Utf8Pattern? = Utf8Pattern.CharPattern(this)

// ////////////////////////////////////////////////////////////////////////
// Impl for a MultiCharEq wrapper
// ////////////////////////////////////////////////////////////////////////
public interface MultiCharEq {
    public fun matches(c: Char): Boolean
}

public fun ((Char) -> Boolean).matches(c: Char): Boolean = this(c)

public fun List<Char>.matchesFromCharArrayn(c: Char, n: Int): Boolean = this.contains(c)

public fun List<Char>.matches(c: Char, n: Int): Boolean = this.contains(c)

public fun List<Char>.matches(c: Char): Boolean = this.contains(c)

public open class MultiCharEqPattern<C : MultiCharEq>(
    public var value: C,
) : Pattern<Any?, MultiCharEqSearcher<C>> {
    public var field0: C
        get() = value
        set(v) {
            value = v
        }

    public override fun intoSearcher(haystack: String): MultiCharEqSearcher<C> = MultiCharEqSearcher(haystack = haystack, charEq = this.value, charIndices = haystack.charIndices())

    public companion object {
        public fun <C : MultiCharEq> typeLayout(typeLayoutC: io.github.kotlinmania.rustlang.core.mem.TypeLayout<C>): io.github.kotlinmania.rustlang.core.mem.TypeLayout<MultiCharEqPattern<C>> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<MultiCharEqPattern<C>> {
                override val alignBytes: Long = listOf(1L, typeLayoutC.alignBytes).max()
                override val sizeBytes: Long = io.github.kotlinmania.rustlang.core.mem.alignUpBytes(typeLayoutC.sizeBytes, alignBytes)
                override fun readNative(addr: Long): MultiCharEqPattern<C> =
                    MultiCharEqPattern(
                        (typeLayoutC).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: MultiCharEqPattern<C>) {
                    (typeLayoutC).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): MultiCharEqPattern<C> =
                    MultiCharEqPattern(
                        (typeLayoutC).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: MultiCharEqPattern<C>) {
                    (typeLayoutC).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class MultiCharEqSearcher<C : MultiCharEq>(
    public var charEq: C,
    public var haystack: String,
    public var charIndices: CharIndices,
) : Searcher, ReverseSearcher, DoubleEndedSearcher {
    public open fun clone(): MultiCharEqSearcher<C> = MultiCharEqSearcher(charEq, haystack, charIndices)

    public override fun haystack(): String = this.haystack

    public override fun next(): SearchStep {
        val s = this.charIndices
        // Compare lengths of the internal byte slice iterator
        // to find length of current char
        val preLen = s.iter.iter.len()
        when (val matchVal = s.next()) {
            null -> {}
            else -> {
                val i = matchVal.first
                val c = matchVal.second
                val len = s.iter.iter.len()
                val charLen = preLen - len
                if (this.charEq.matches(c)) {
                    return SearchStep.Match(i, i + charLen)
                } else {
                    return SearchStep.Reject(i, i + charLen)
                }
            }
        }
        return SearchStep.Done
    }

    public override fun nextBack(): SearchStep {
        val s = this.charIndices
        // Compare lengths of the internal byte slice iterator
        // to find length of current char
        val preLen = s.iter.iter.len()
        when (val matchVal = s.nextBack()) {
            null -> {}
            else -> {
                val i = matchVal.first
                val c = matchVal.second
                val len = s.iter.iter.len()
                val charLen = preLen - len
                if (this.charEq.matches(c)) {
                    return SearchStep.Match(i, i + charLen)
                } else {
                    return SearchStep.Reject(i, i + charLen)
                }
            }
        }
        return SearchStep.Done
    }
}

/**
 * Associated type for `<[char; N] as Pattern>.Searcher<>`.
 */
public open class CharArraySearcher(
    public var value: Searcher,
) : Searcher, ReverseSearcher, DoubleEndedSearcher {
    public var field0: Searcher
        get() = value
        set(v) {
            value = v
        }

    public override fun haystack(): String = this.value.haystack()

    public override fun next(): SearchStep = this.value.next()

    public override fun nextMatch(): Pair<Int, Int>? = this.value.nextMatch()

    public override fun nextReject(): Pair<Int, Int>? = this.value.nextReject()

    public override fun nextBack(): SearchStep = this.value.nextBack()

    public override fun nextMatchBack(): Pair<Int, Int>? = this.value.nextMatchBack()

    public override fun nextRejectBack(): Pair<Int, Int>? = this.value.nextRejectBack()
}

/**
 * Associated type for `<[char; N] as Pattern>.Searcher<>`.
 */
public open class CharArrayRefSearcher(
    public var value: Searcher,
) : Searcher, ReverseSearcher, DoubleEndedSearcher {
    public var field0: Searcher
        get() = value
        set(v) {
            value = v
        }

    public override fun haystack(): String = this.value.haystack()

    public override fun next(): SearchStep = this.value.next()

    public override fun nextMatch(): Pair<Int, Int>? = this.value.nextMatch()

    public override fun nextReject(): Pair<Int, Int>? = this.value.nextReject()

    public override fun nextBack(): SearchStep = this.value.nextBack()

    public override fun nextMatchBack(): Pair<Int, Int>? = this.value.nextMatchBack()

    public override fun nextRejectBack(): Pair<Int, Int>? = this.value.nextRejectBack()
}

/**
 * Searches for chars that are equal to any of the [char]s in the array.
 *
 * # Examples
 *
 * ```kotlin
 * check("Hello world".indexOf(listOf('o', 'l')).takeIf { it >= 0 } == 2)
 * check("Hello world".indexOf(listOf('h', 'w')).takeIf { it >= 0 } == 6)
 * ```
 */
public fun List<Char>.intoSearcherFromCharArrayn(haystack: String, n: Int): CharArraySearcher = (CharArraySearcher)((MultiCharEqPattern)(this).intoSearcher(haystack))

public fun List<Char>.isContainedInFromCharArrayn(haystack: String, n: Int): Boolean = (MultiCharEqPattern)(this).isContainedIn(haystack)

public fun List<Char>.isPrefixOfFromCharArrayn(haystack: String, n: Int): Boolean = (MultiCharEqPattern)(this).isPrefixOf(haystack)

public fun List<Char>.stripPrefixOfFromCharArrayn(haystack: String, n: Int): String? = (MultiCharEqPattern)(this).stripPrefixOf(haystack)

public fun List<Char>.isSuffixOfFromCharArrayn(haystack: String, n: Int): Boolean = (MultiCharEqPattern)(this).isSuffixOf(haystack)

public fun List<Char>.stripSuffixOfFromCharArrayn(haystack: String, n: Int): String? = (MultiCharEqPattern)(this).stripSuffixOf(haystack)

/**
 * Searches for chars that are equal to any of the [char]s in the array.
 *
 * # Examples
 *
 * ```kotlin
 * check("Hello world".indexOf(listOf('o', 'l')).takeIf { it >= 0 } == 2)
 * check("Hello world".indexOf(listOf('h', 'w')).takeIf { it >= 0 } == 6)
 * ```
 */
public fun List<Char>.intoSearcher(haystack: String, n: Int): CharArrayRefSearcher = (CharArrayRefSearcher)((MultiCharEqPattern)(this).intoSearcher(haystack))

public fun List<Char>.isContainedIn(haystack: String, n: Int): Boolean = (MultiCharEqPattern)(this).isContainedIn(haystack)

public fun List<Char>.isPrefixOf(haystack: String, n: Int): Boolean = (MultiCharEqPattern)(this).isPrefixOf(haystack)

public fun List<Char>.stripPrefixOf(haystack: String, n: Int): String? = (MultiCharEqPattern)(this).stripPrefixOf(haystack)

public fun List<Char>.isSuffixOf(haystack: String, n: Int): Boolean = (MultiCharEqPattern)(this).isSuffixOf(haystack)

public fun List<Char>.stripSuffixOf(haystack: String, n: Int): String? = (MultiCharEqPattern)(this).stripSuffixOf(haystack)

// ////////////////////////////////////////////////////////////////////////
// Impl for &[char]
// ////////////////////////////////////////////////////////////////////////
// Note: Change / Remove due to ambiguity in meaning.

/**
 * Associated type for `<[char] as Pattern>.Searcher<>`.
 */
public open class CharSliceSearcher(
    public var value: Searcher,
) : Searcher, ReverseSearcher, DoubleEndedSearcher {
    public var field0: Searcher
        get() = value
        set(v) {
            value = v
        }

    public override fun haystack(): String = this.value.haystack()

    public override fun next(): SearchStep = this.value.next()

    public override fun nextMatch(): Pair<Int, Int>? = this.value.nextMatch()

    public override fun nextReject(): Pair<Int, Int>? = this.value.nextReject()

    public override fun nextBack(): SearchStep = this.value.nextBack()

    public override fun nextMatchBack(): Pair<Int, Int>? = this.value.nextMatchBack()

    public override fun nextRejectBack(): Pair<Int, Int>? = this.value.nextRejectBack()
}

/**
 * Searches for chars that are equal to any of the [char]s in the slice.
 *
 * # Examples
 *
 * ```kotlin
 * check("Hello world".indexOf(listOf('o', 'l')).takeIf { it >= 0 } == 2)
 * check("Hello world".indexOf(listOf('h', 'w')).takeIf { it >= 0 } == 6)
 * ```
 */
public fun List<Char>.intoSearcher(haystack: String): CharSliceSearcher = (CharSliceSearcher)((MultiCharEqPattern)(this).intoSearcher(haystack))

public fun List<Char>.isContainedIn(haystack: String): Boolean = (MultiCharEqPattern)(this).isContainedIn(haystack)

public fun List<Char>.isPrefixOf(haystack: String): Boolean = (MultiCharEqPattern)(this).isPrefixOf(haystack)

public fun List<Char>.stripPrefixOf(haystack: String): String? = (MultiCharEqPattern)(this).stripPrefixOf(haystack)

public fun List<Char>.isSuffixOf(haystack: String): Boolean = (MultiCharEqPattern)(this).isSuffixOf(haystack)

public fun List<Char>.stripSuffixOf(haystack: String): String? = (MultiCharEqPattern)(this).stripSuffixOf(haystack)

// ////////////////////////////////////////////////////////////////////////
// Impl for F: FnMut(char) -> bool
// ////////////////////////////////////////////////////////////////////////

/**
 * Associated type for `<F as Pattern>.Searcher<>`.
 */
public open class CharPredicateSearcher<F>(
    public var value: Searcher,
) : Searcher, ReverseSearcher, DoubleEndedSearcher {
    public var field0: Searcher
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): CharPredicateSearcher<F> = CharPredicateSearcher(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = DebugStruct(f, "CharPredicateSearcher").field("haystack", this.value.haystack).field("char_indices", this.value.charIndices).finish()

    public override fun haystack(): String = this.value.haystack()

    public override fun next(): SearchStep = this.value.next()

    public override fun nextMatch(): Pair<Int, Int>? = this.value.nextMatch()

    public override fun nextReject(): Pair<Int, Int>? = this.value.nextReject()

    public override fun nextBack(): SearchStep = this.value.nextBack()

    public override fun nextMatchBack(): Pair<Int, Int>? = this.value.nextMatchBack()

    public override fun nextRejectBack(): Pair<Int, Int>? = this.value.nextRejectBack()
}

/**
 * Searches for [char]s that match the given predicate.
 *
 * # Examples
 *
 * ```kotlin
 * check("Hello world".indexOf(Char.isUppercase).takeIf { it >= 0 } == 0)
 * check("Hello world".indexOf({ c -> "aeiou".contains(c) }).takeIf { it >= 0 } == 1)
 * ```
 */
public fun ((Char) -> Boolean).intoSearcher(haystack: String): CharPredicateSearcher<(Char) -> Boolean> = (CharPredicateSearcher)((MultiCharEqPattern)(this).intoSearcher(haystack))

public fun ((Char) -> Boolean).isContainedIn(haystack: String): Boolean = (MultiCharEqPattern)(this).isContainedIn(haystack)

public fun ((Char) -> Boolean).isPrefixOf(haystack: String): Boolean = (MultiCharEqPattern)(this).isPrefixOf(haystack)

public fun ((Char) -> Boolean).stripPrefixOf(haystack: String): String? = (MultiCharEqPattern)(this).stripPrefixOf(haystack)

public fun ((Char) -> Boolean).isSuffixOf(haystack: String): Boolean = (MultiCharEqPattern)(this).isSuffixOf(haystack)

public fun ((Char) -> Boolean).stripSuffixOf(haystack: String): String? = (MultiCharEqPattern)(this).stripSuffixOf(haystack)

// ////////////////////////////////////////////////////////////////////////
// Impl for &&str
// ////////////////////////////////////////////////////////////////////////

/**
 * Delegates to the `String` impl.
 */
public fun String.intoSearcher(haystack: String): StrSearcher = ({ s -> s })(({ s -> s })(this).intoSearcher(haystack))

public fun String.isContainedIn(haystack: String): Boolean = ({ s -> s })(this).isContainedIn(haystack)

public fun String.isPrefixOf(haystack: String): Boolean = ({ s -> s })(this).isPrefixOf(haystack)

public fun String.stripPrefixOf(haystack: String): String? = ({ s -> s })(this).stripPrefixOf(haystack)

public fun String.isSuffixOf(haystack: String): Boolean = ({ s -> s })(this).isSuffixOf(haystack)

public fun String.stripSuffixOf(haystack: String): String? = ({ s -> s })(this).stripSuffixOf(haystack)

// ////////////////////////////////////////////////////////////////////////
// Impl for &str
// ////////////////////////////////////////////////////////////////////////

/**
 * Non-allocating substring search.
 *
 * Will handle the pattern `""` as returning empty matches at each character
 * boundary.
 *
 * # Examples
 *
 * ```kotlin
 * check("Hello world".indexOf("world").takeIf { it >= 0 } == 6)
 * ```
 */
public fun String.asUtf8Pattern(): Utf8Pattern? = Utf8Pattern.StringPattern(this)

// ////////////////////////////////////////////////////////////////////////
// Two Way substring searcher
// ////////////////////////////////////////////////////////////////////////

/**
 * Associated type for `<String as Pattern>.Searcher<>`.
 */
public open class StrSearcher(
    public var haystack: String,
    public var needle: String,
    public var searcher: StrSearcherImpl,
) : Searcher, ReverseSearcher {
    public override fun haystack(): String = this.haystack

    public override fun next(): SearchStep {
        return when (val matchVal = this.searcher) {
            is StrSearcherImpl.Empty -> {
                val (searcher) = matchVal
                if (searcher.isFinished) {
                    return SearchStep.Done
                }
                // empty needle rejects every char and matches every empty string between them
                val isMatch = searcher.isMatchFw
                searcher.isMatchFw = !searcher.isMatchFw
                val pos = searcher.position
                run {
                    val matchVal1 = this.haystack.substring(pos).rustStrChars().nextCodeOrNull()?.toChar()
                    when {
                        isMatch -> SearchStep.Match(pos, pos)
                        matchVal1 == null -> {
                            searcher.isFinished = true
                            SearchStep.Done
                        }
                        else -> {
                            val ch = matchVal1
                            searcher.position += ch.lenUtf8()
                            SearchStep.Reject(pos, searcher.position)
                        }
                    }
                }
            }
            is StrSearcherImpl.Byte -> {
                val (searcher1) = matchVal
                val bytes = this.haystack.encodeToByteArray().map { it.toUByte() }
                val pos1 = searcher1.position
                if (pos1 >= bytes.size) {
                    return SearchStep.Done
                }
                if (bytes[pos1] == searcher1.b) {
                    searcher1.position = pos1 + 1
                    SearchStep.Match(pos1, pos1 + 1)
                } else {
                    // `pos` is always on a char boundary, so this rejects
                    // exactly the char starting at `pos`.
                    val end = this.haystack.ceilCharBoundary(pos1 + 1)
                    searcher1.position = end
                    SearchStep.Reject(pos1, end)
                }
            }
            is StrSearcherImpl.TwoWay -> {
                val (searcher2) = matchVal
                // TwoWaySearcher produces valid *Match* indices that split at char boundaries
                // as long as it does correct matching and that haystack and needle are
                // valid UTF-8
                // *Rejects* from the algorithm can fall on any indices, but we will walk them
                // manually to the next character boundary, so that they are utf-8 safe.
                if (searcher2.position == this.haystack.length) {
                    return SearchStep.Done
                }
                val isLong = searcher2.memory == Int.MAX_VALUE
                when (val matchVal2 = searcher2.next<RejectAndMatch>(this.haystack.encodeToByteArray().map { it.toUByte() }, this.needle.encodeToByteArray().map { it.toUByte() }, isLong)) {
                    is SearchStep.Reject -> {
                        val (a, b) = matchVal2
                        // skip to next char boundary
                        val b1 = this.haystack.ceilCharBoundary(b)
                        searcher2.position = cmp.max(b1, searcher2.position)
                        SearchStep.Reject(a, b1)
                    }
                    else -> {
                        val otherwise = matchVal2
                        otherwise
                    }
                }
            }
        }
    }

    public override fun nextMatch(): Pair<Int, Int>? {
        return when (val matchVal = this.searcher) {
            is StrSearcherImpl.Empty -> while (true) {
                when (val matchVal1 = this.next()) {
                    is SearchStep.Match -> {
                        val (a, b) = matchVal1
                        return Pair(a, b)
                    }
                    SearchStep.Done -> { return null }
                    is SearchStep.Reject -> {}
                }
            }
            is StrSearcherImpl.Byte -> {
                val (searcher) = matchVal
                val bytes = this.haystack.encodeToByteArray().map { it.toUByte() }
                if (searcher.position >= bytes.size) {
                    return null
                }
                when (val matchVal2 = memchr.memchr(searcher.b, ((bytes).slice(searcher.position until bytes.size)))) {
                    null -> {
                        searcher.position = bytes.size
                        null
                    }
                    else -> {
                        val i = matchVal2
                        val pos = searcher.position + i
                        searcher.position = pos + 1
                        Pair(pos, pos + 1)
                    }
                }
            }
            is StrSearcherImpl.TwoWay -> {
                val (searcher1) = matchVal
                val isLong = searcher1.memory == Int.MAX_VALUE
                // write out `true` and `false` cases to encourage the compiler
                // to specialize the two cases separately.
                if (isLong) {
                    searcher1.next<MatchOnly>(this.haystack.encodeToByteArray().map { it.toUByte() }, this.needle.encodeToByteArray().map { it.toUByte() }, true)
                } else {
                    searcher1.next<MatchOnly>(this.haystack.encodeToByteArray().map { it.toUByte() }, this.needle.encodeToByteArray().map { it.toUByte() }, false)
                }
            }
        }
    }

    public override fun nextBack(): SearchStep {
        return when (val matchVal = this.searcher) {
            is StrSearcherImpl.Empty -> {
                val (searcher) = matchVal
                if (searcher.isFinished) {
                    return SearchStep.Done
                }
                val isMatch = searcher.isMatchBw
                searcher.isMatchBw = !searcher.isMatchBw
                val end = searcher.end
                run {
                    val matchVal1 = this.haystack.substring(0 until end).rustStrChars().nextBack()
                    when {
                        isMatch -> SearchStep.Match(end, end)
                        matchVal1 == null -> {
                            searcher.isFinished = true
                            SearchStep.Done
                        }
                        else -> {
                            val ch = matchVal1
                            searcher.end -= ch.lenUtf8()
                            SearchStep.Reject(searcher.end, end)
                        }
                    }
                }
            }
            is StrSearcherImpl.Byte -> {
                val (searcher1) = matchVal
                val end1 = searcher1.end
                if (end1 == 0) {
                    return SearchStep.Done
                }
                val bytes = this.haystack.encodeToByteArray().map { it.toUByte() }
                if (bytes[end1 - 1] == searcher1.b) {
                    searcher1.end = end1 - 1
                    SearchStep.Match(end1 - 1, end1)
                } else {
                    val start = this.haystack.floorCharBoundary(end1 - 1)
                    searcher1.end = start
                    SearchStep.Reject(start, end1)
                }
            }
            is StrSearcherImpl.TwoWay -> {
                val (searcher2) = matchVal
                if (searcher2.end == 0) {
                    return SearchStep.Done
                }
                val isLong = searcher2.memory == Int.MAX_VALUE
                when (val matchVal2 = searcher2.nextBack<RejectAndMatch>(this.haystack.encodeToByteArray().map { it.toUByte() }, this.needle.encodeToByteArray().map { it.toUByte() }, isLong)) {
                    is SearchStep.Reject -> {
                        val (a, b) = matchVal2
                        // skip to previous char boundary
                        val a1 = this.haystack.floorCharBoundary(a)
                        searcher2.end = minOf(a1, searcher2.end)
                        SearchStep.Reject(a1, b)
                    }
                    else -> {
                        val otherwise = matchVal2
                        otherwise
                    }
                }
            }
        }
    }

    public override fun nextMatchBack(): Pair<Int, Int>? {
        return when (val matchVal = this.searcher) {
            is StrSearcherImpl.Empty -> while (true) {
                when (val matchVal1 = this.nextBack()) {
                    is SearchStep.Match -> {
                        val (a, b) = matchVal1
                        return Pair(a, b)
                    }
                    SearchStep.Done -> { return null }
                    is SearchStep.Reject -> {}
                }
            }
            is StrSearcherImpl.Byte -> {
                val (searcher) = matchVal
                if (searcher.end == 0) {
                    return null
                }
                val bytes = this.haystack.encodeToByteArray().map { it.toUByte() }
                when (val matchVal2 = memchr.memrchr(searcher.b, ((bytes).slice(0 until searcher.end)))) {
                    null -> {
                        searcher.end = 0
                        null
                    }
                    else -> {
                        val i = matchVal2
                        searcher.end = i
                        Pair(i, i + 1)
                    }
                }
            }
            is StrSearcherImpl.TwoWay -> {
                val (searcher1) = matchVal
                val isLong = searcher1.memory == Int.MAX_VALUE
                // write out `true` and `false`, like `nextMatch`
                if (isLong) {
                    searcher1.nextBack<MatchOnly>(this.haystack.encodeToByteArray().map { it.toUByte() }, this.needle.encodeToByteArray().map { it.toUByte() }, true)
                } else {
                    searcher1.nextBack<MatchOnly>(this.haystack.encodeToByteArray().map { it.toUByte() }, this.needle.encodeToByteArray().map { it.toUByte() }, false)
                }
            }
        }
    }

    public companion object {
        public fun new(haystack: String, needle: String): StrSearcher =
            if (needle.isEmpty()) {
                StrSearcher(haystack = haystack, needle = needle, searcher = StrSearcherImpl.Empty(EmptyNeedle(position = 0, end = haystack.length, isMatchFw = true, isMatchBw = true, isFinished = false)))
            } else if (needle.encodeToByteArray().map { it.toUByte() } != null) {
                val b = needle.encodeToByteArray().map { it.toUByte() }[0]
                StrSearcher(haystack = haystack, needle = needle, searcher = StrSearcherImpl.Byte(ByteNeedle(b = b, position = 0, end = haystack.length)))
            } else {
                StrSearcher(haystack = haystack, needle = needle, searcher = StrSearcherImpl.TwoWay(TwoWaySearcher.new(needle.encodeToByteArray().map { it.toUByte() }, haystack.length)))
            }
    }
}

public sealed class StrSearcherImpl {
    public data class Empty(public var value: EmptyNeedle) : StrSearcherImpl()
    public data class Byte(public var value: ByteNeedle) : StrSearcherImpl()
    public data class TwoWay(public var value: TwoWaySearcher) : StrSearcherImpl()
}

public open class EmptyNeedle(
    public var position: Int,
    public var end: Int,
    public var isMatchFw: Boolean,
    public var isMatchBw: Boolean,
    // Needed in case of an empty haystack, see #85462
    public var isFinished: Boolean,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<EmptyNeedle> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<EmptyNeedle> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): EmptyNeedle =
                    EmptyNeedle(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 17L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 18L),
                    )
                override fun writeNative(addr: Long, value: EmptyNeedle) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.position)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.end)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 16L, value.isMatchFw)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 17L, value.isMatchBw)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 18L, value.isFinished)
                }
                override fun readBytes(buf: ByteArray, offset: Int): EmptyNeedle =
                    EmptyNeedle(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 17),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 18),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: EmptyNeedle) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.position)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.end)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 16, value.isMatchFw)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 17, value.isMatchBw)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 18, value.isFinished)
                }
            }
    }
}

/**
 * Fast searcher for a single-byte needle using `memchr`/`memrchr`.
 */
public open class ByteNeedle(
    public var b: UByte,
    /**
     * Forward cursor: `haystack[.. position]` has already been reported.
     */
    public var position: Int,
    /**
     * Backward cursor: `haystack[end ..]` has already been reported.
     */
    public var end: Int,
) {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<ByteNeedle> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<ByteNeedle> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 24L
                override fun readNative(addr: Long): ByteNeedle =
                    ByteNeedle(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: ByteNeedle) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr, value.b)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.position)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 16L, value.end)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ByteNeedle =
                    ByteNeedle(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ByteNeedle) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset, value.b)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.position)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 16, value.end)
                }
            }
    }
}

/**
 * The internal state of the two-way substring search algorithm.
 */
public open class TwoWaySearcher(
    // constants
    /**
     * critical factorization index
     */
    public var critPos: Int,
    /**
     * critical factorization index for reversed needle
     */
    public var critPosBack: Int,
    public var period: Int,
    /**
     * `byteset` is an extension (not part of the two way algorithm);
     * it's a 64-bit "fingerprint" where each set bit `j` corresponds
     * to a (byte & 63) == j present in the needle.
     */
    public var byteset: ULong,
    // variables
    public var position: Int,
    public var end: Int,
    /**
     * index into needle before which we have already matched
     */
    public var memory: Int,
    /**
     * index into needle after which we have already matched
     */
    public var memoryBack: Int,
) {
    //
    // This is the Two-Way search algorithm, which was introduced in the paper:
    // Crochemore, M., Perrin, D., 1991, Two-way string-matching, Journal of the ACM 38(3):651-675.
    //
    // Here's some background information.
    //
    // A *word* is a string of symbols. The *length* of a word should be a familiar
    // notion, and here we denote it for any word x by |x|.
    // (We also allow for the possibility of the *empty word*, a word of length zero).
    //
    // If x is any non-empty word, then an integer p with 0 < p <= |x| is said to be a
    // period* for x iff for all i with 0 <= i <= |x| - p - 1, we have x[i] == x[i+p].
    // For example, both 1 and 2 are periods for the string "aa". As another example,
    // the only period of the string "abcd" is 4.
    //
    // We denote by period(x) the *smallest* period of x (provided that x is non-empty).
    // This is always well-defined since every non-empty word x has at least one period,
    // |x|. We sometimes call this *the period* of x.
    //
    // If u, v and x are words such that x = uv, where uv is the concatenation of u and
    // v, then we say that (u, v) is a *factorization* of x.
    //
    // Let (u, v) be a factorization for a word x. Then if w is a non-empty word such
    // that both of the following hold
    //
    // - either w is a suffix of u or u is a suffix of w
    // - either w is a prefix of v or v is a prefix of w
    //
    // then w is said to be a *repetition* for the factorization (u, v).
    //
    // Just to unpack this, there are four possibilities here. Let w = "abc". Then we
    // might have:
    //
    // - w is a suffix of u and w is a prefix of v. ex: ("lolabc", "abcde")
    // - w is a suffix of u and v is a prefix of w. ex: ("lolabc", "ab")
    // - u is a suffix of w and w is a prefix of v. ex: ("bc", "abchi")
    // - u is a suffix of w and v is a prefix of w. ex: ("bc", "a")
    //
    // Note that the word vu is a repetition for any factorization (u,v) of x = uv,
    // so every factorization has at least one repetition.
    //
    // If x is a string and (u, v) is a factorization for x, then a *local period* for
    // (u, v) is an integer r such that there is some word w such that |w| = r and w is
    // a repetition for (u, v).
    //
    // We denote by localPeriod(u, v) the smallest local period of (u, v). We sometimes
    // call this *the local period* of (u, v). Provided that x = uv is non-empty, this
    // is well-defined (because each non-empty word has at least one factorization, as
    // noted above).
    //
    // It can be proven that the following is an equivalent definition of a local period
    // for a factorization (u, v): any positive integer r such that x[i] == x[i+r] for
    // all i such that |u| - r <= i <= |u| - 1 and such that both x[i] and x[i+r] are
    // defined. (i.e., i > 0 and i + r < |x|).
    //
    // Using the above reformulation, it is easy to prove that
    //
    // 1 <= localPeriod(u, v) <= period(uv)
    //
    // A factorization (u, v) of x such that localPeriod(u,v) = period(x) is called a
    // critical factorization*.
    //
    // The algorithm hinges on the following theorem, which is stated without proof:
    //
    // *Critical Factorization Theorem** Any word x has at least one critical
    // factorization (u, v) such that |u| < period(x).
    //
    // The purpose of maximalSuffix is to find such a critical factorization.
    //
    // If the period is short, compute another factorization x = u' v' to use
    // for reverse search, chosen instead so that |v'| < period(x).
    //
    //
    public open fun bytesetContains(byte: UByte): Boolean = (this.byteset shr ((byte and 0x3fu.toUByte()).toInt())) and 1uL != 0uL

    // One of the main ideas of Two-Way is that we factorize the needle into
    // two halves, (u, v), and begin trying to find v in the haystack by scanning
    // left to right. If v matches, we try to match u by scanning right to left.
    // How far we can jump when we encounter a mismatch is all based on the fact
    // that (u, v) is a critical factorization for the needle.
    public open fun <Output, S> next(haystack: List<UByte>, needle: List<UByte>, longPeriod: Boolean, rejectingSFn: (Int, Int) -> Output, useEarlyRejectSFn: () -> Boolean, matchingSFn: (Int, Int) -> Output): Output where S : TwoWayStrategy<Any?> {
        // `next()` uses `self.position` as its cursor
        val oldPos = this.position
        val needleLast = needle.size - 1
        while (true) {
            // Check that we have room to search in
            // position + needleLast can not overflow if we assume slices
            // are bounded by isize's range.
            val tailByte =
                when (val matchVal = haystack.get(this.position + needleLast)) {
                    null -> {
                        this.position = haystack.size
                        return rejectingSFn(oldPos, this.position)
                    }
                    else -> {
                        val b = matchVal
                        b
                    }
                }
            if (useEarlyRejectSFn() && oldPos != this.position) {
                return rejectingSFn(oldPos, this.position)
            }
            // Quickly skip by large portions unrelated to our substring
            if (!this.bytesetContains(tailByte)) {
                this.position += needle.size
                if (!longPeriod) {
                    this.memory = 0
                }
                continue
            }
            // See if the right part of the needle matches
            val start =
                if (longPeriod) {
                    this.critPos
                } else {
                    cmp.max(this.critPos, this.memory)
                }
            for (i in start until needle.size) {
                // SAFETY: on every iteration of `search`, the `haystack.get(self.position + needleLast)`
                // check returned `Some`, so `self.position + needleLast<haystack.len()`.
                // Since `i<needle.len()` implies `i <= needleLast`, we have
                // `self.position + i<haystack.len()`.
                // Every path that mutates `self.position` below either returns or re-enters `search`,
                // which re-runs the check before reaching the loop again.
                if (needle[i] != haystack.getUnchecked(this.position + i)) {
                    this.position += i - this.critPos + 1
                    if (!longPeriod) {
                        this.memory = 0
                    }
                    continue
                }
            }
            // See if the left part of the needle matches
            val start1 =
                if (longPeriod) {
                    0
                } else {
                    this.memory
                }
            for (i1 in (start1 until this.critPos).rev()) {
                // SAFETY: on every iteration of `search`, the `haystack.get(self.position + needleLast)`
                // check returned `Some`, so `self.position + needleLast<haystack.len()`.
                // Since `i<self.critPos <= needle.len()`, we have `i <= needleLast`, and thus
                // `self.position + i <= self.position + needleLast<haystack.len()`.
                // Every path that mutates `self.position` below either returns or re-enters `search`,
                // which re-runs the check before reaching the loop again.
                if (needle[i1] != haystack.getUnchecked(this.position + i1)) {
                    this.position += this.period
                    if (!longPeriod) {
                        this.memory = needle.size - this.period
                    }
                    continue
                }
            }
            // We have found a match!
            val matchPos = this.position
            // Note: add self.period instead of needle.len() to have overlapping matches
            this.position += needle.size
            if (!longPeriod) {
                this.memory = 0
                // set to needle.len() - self.period for overlapping matches
            }
            return matchingSFn(matchPos, matchPos + needle.size)
        }
    }

    // Follows the ideas in `next()`.
    //
    // The definitions are symmetrical, with period(x) = period(reverse(x))
    // and localPeriod(u, v) = localPeriod(reverse(v), reverse(u)), so if (u, v)
    // is a critical factorization, so is (reverse(v), reverse(u)).
    //
    // For the reverse case we have computed a critical factorization x = u' v'
    // (field `critPosBack`). We need |u| < period(x) for the forward case and
    // thus |v'| < period(x) for the reverse.
    //
    // To search in reverse through the haystack, we search forward through
    // a reversed haystack with a reversed needle, matching first u' and then v'.
    public open fun <Output, S> nextBack(haystack: List<UByte>, needle: List<UByte>, longPeriod: Boolean, rejectingSFn: (Int, Int) -> Output, useEarlyRejectSFn: () -> Boolean, matchingSFn: (Int, Int) -> Output): Output where S : TwoWayStrategy<Any?> {
        // `nextBack()` uses `self.end` as its cursor -- so that `next()` and `nextBack()`
        // are independent.
        val oldEnd = this.end
        while (true) {
            // Check that we have room to search in
            // end - needle.len() will wrap around when there is no more room,
            // but due to slice length limits it can never wrap all the way back
            // into the length of haystack.
            val frontByte =
                when (val matchVal = haystack.get((this.end - needle.size))) {
                    null -> {
                        this.end = 0
                        return rejectingSFn(0, oldEnd)
                    }
                    else -> {
                        val b = matchVal
                        b
                    }
                }
            if (useEarlyRejectSFn() && oldEnd != this.end) {
                return rejectingSFn(this.end, oldEnd)
            }
            // Quickly skip by large portions unrelated to our substring
            if (!this.bytesetContains(frontByte)) {
                this.end -= needle.size
                if (!longPeriod) {
                    this.memoryBack = needle.size
                }
                continue
            }
            // See if the left part of the needle matches
            val crit =
                if (longPeriod) {
                    this.critPosBack
                } else {
                    minOf(this.critPosBack, this.memoryBack)
                }
            for (i in (0 until crit).rev()) {
                // SAFETY: On every iteration of `search`, `haystack.get(self.end.wrappingSub(needle.len()))`
                // returned `Some`, so `self.end >= needle.len()` and `self.end - needle.len()<haystack.len()`.
                // Since `self.end <= haystack.len()` and `i<needle.len()`, we have
                // `self.end - needle.len() + i<self.end <= haystack.len()`, so
                // `haystack.getUnchecked(self.end - needle.len() + i)` is safe.
                // - The path that mutates `self.end` either re-enters `search`, which re-runs the checks
                // before reaching this loop again, or returns on match, so the invariant holds.
                if (needle[i] != haystack.getUnchecked(this.end - needle.size + i)) {
                    this.end -= this.critPosBack - i
                    if (!longPeriod) {
                        this.memoryBack = needle.size
                    }
                    continue
                }
            }
            // See if the right part of the needle matches
            val needleEnd =
                if (longPeriod) {
                    needle.size
                } else {
                    this.memoryBack
                }
            for (i1 in this.critPosBack until needleEnd) {
                // SAFETY: The same `self.end - needle.len() + i<haystack.len()` argument as the
                // left-part loop applies: the `haystack.get(self.end.wrappingSub(needle.len()))`
                // check at the top of `search` established the bound for this iteration, and
                // every mutation of `self.end` is followed by `continue` (which re-runs
                // the check) or a `return` (which exits before any further unchecked access).
                if (needle[i1] != haystack.getUnchecked(this.end - needle.size + i1)) {
                    this.end -= this.period
                    if (!longPeriod) {
                        this.memoryBack = this.period
                    }
                    continue
                }
            }
            // We have found a match!
            val matchPos = this.end - needle.size
            // Note: sub self.period instead of needle.len() to have overlapping matches
            this.end -= needle.size
            if (!longPeriod) {
                this.memoryBack = needle.size
            }
            return matchingSFn(matchPos, matchPos + needle.size)
        }
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<TwoWaySearcher> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<TwoWaySearcher> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 64L
                override fun readNative(addr: Long): TwoWaySearcher =
                    TwoWaySearcher(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 16L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr + 24L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 32L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 40L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 48L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readNative(addr + 56L),
                    )
                override fun writeNative(addr: Long, value: TwoWaySearcher) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr, value.critPos)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 8L, value.critPosBack)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 16L, value.period)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr + 24L, value.byteset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 32L, value.position)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 40L, value.end)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 48L, value.memory)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeNative(addr + 56L, value.memoryBack)
                }
                override fun readBytes(buf: ByteArray, offset: Int): TwoWaySearcher =
                    TwoWaySearcher(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 16),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset + 24),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 32),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 40),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 48),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).readBytes(buf, offset + 56),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: TwoWaySearcher) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset, value.critPos)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 8, value.critPosBack)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 16, value.period)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset + 24, value.byteset)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 32, value.position)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 40, value.end)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 48, value.memory)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.USIZE64).writeBytes(buf, offset + 56, value.memoryBack)
                }
            }

        public fun new(needle: List<UByte>, end: Int): TwoWaySearcher {
            val (critPosFalse, periodFalse) = TwoWaySearcher.maximalSuffix(needle, false)
            val (critPosTrue, periodTrue) = TwoWaySearcher.maximalSuffix(needle, true)
            val (critPos, period) =
                if (critPosFalse > critPosTrue) {
                    Pair(critPosFalse, periodFalse)
                } else {
                    Pair(critPosTrue, periodTrue)
                }
            // A particularly readable explanation of what's going on here can be found
            // in Crochemore and Rytter's book "Text Algorithms", ch 13. Specifically
            // see the code for "Algorithm CP" on p. 323.
            //
            // What's going on is we have some critical factorization (u, v) of the
            // needle, and we want to determine whether u is a suffix of
            // &v[..period]. If it is, we use "Algorithm CP1". Otherwise we use
            // "Algorithm CP2", which is optimized for when the period of the needle
            // is large.
            return if (((needle).slice(0 until critPos)) == ((needle).slice(period until period + critPos))) {
                // short period case -- the period is exact
                // compute a separate critical factorization for the reversed needle
                // x = u' v' where |v'| < period(x).
                //
                // This is sped up by the period being known already.
                // Note that a case like x = "acba" may be factored exactly forwards
                // (critPos = 1, period = 3) while being factored with approximate
                // period in reverse (critPos = 2, period = 2). We use the given
                // reverse factorization but keep the exact period.
                val critPosBack = needle.size - cmp.max(TwoWaySearcher.reverseMaximalSuffix(needle, period, false), TwoWaySearcher.reverseMaximalSuffix(needle, period, true))
                TwoWaySearcher(critPos = critPos, critPosBack = critPosBack, period = period, byteset = TwoWaySearcher.bytesetCreate(((needle).slice(0 until period))), position = 0, end = end, memory = 0, memoryBack = needle.size)
            } else {
                // long period case -- we have an approximation to the actual period,
                // and don't use memorization.
                //
                // Approximate the period by lower bound max(|u|, |v|) + 1.
                // The critical factorization is efficient to use for both forward and
                // reverse search.
                TwoWaySearcher(critPos = critPos, critPosBack = critPos, period = cmp.max(critPos, needle.size - critPos) + 1, byteset = TwoWaySearcher.bytesetCreate(needle), position = 0, end = end, memory = Int.MAX_VALUE, memoryBack = Int.MAX_VALUE)
            }
        }

        public fun bytesetCreate(bytes: List<UByte>): ULong = bytes.fold(0, { a, b -> (1 shl (b and 0x3f).toInt()) or a })

        // Compute the maximal suffix of `arr`.
        //
        // The maximal suffix is a possible critical factorization (u, v) of `arr`.
        //
        // Returns (`i`, `p`) where `i` is the starting index of v and `p` is the
        // period of v.
        //
        // `orderGreater` determines if lexical order is `<` or `>`. Both
        // orders must be computed -- the ordering with the largest `i` gives
        // a critical factorization.
        //
        // For long period cases, the resulting period is not exact (it is too short).
        public fun maximalSuffix(arr: List<UByte>, orderGreater: Boolean): Pair<Int, Int> {
            var left = 0
            // Corresponds to i in the paper
            var right = 1
            // Corresponds to j in the paper
            var offset = 0
            // Corresponds to k in the paper, but starting at 0
            // to match 0-based indexing.
            var period = 1
            // Corresponds to p in the paper
            while (true) {
                val matchVal = arr.get(right + offset)
                if (matchVal == null) break
                val a = matchVal
                // `left` will be inbounds when `right` is.
                val b = arr[left + offset]
                if ((a < b && !orderGreater) || (a > b && orderGreater)) {
                    // Suffix is smaller, period is entire prefix so far.
                    right += offset + 1
                    offset = 0
                    period = right - left
                } else if (a == b) {
                    // Advance through repetition of the current period.
                    if (offset + 1 == period) {
                        right += offset + 1
                        offset = 0
                    } else {
                        offset += 1
                    }
                } else {
                    // Suffix is larger, start over from current location.
                    left = right
                    right += 1
                    offset = 0
                    period = 1
                }
            }
            return Pair(left, period)
        }

        // Compute the maximal suffix of the reverse of `arr`.
        //
        // The maximal suffix is a possible critical factorization (u', v') of `arr`.
        //
        // Returns `i` where `i` is the starting index of v', from the back;
        // returns immediately when a period of `knownPeriod` is reached.
        //
        // `orderGreater` determines if lexical order is `<` or `>`. Both
        // orders must be computed -- the ordering with the largest `i` gives
        // a critical factorization.
        //
        // For long period cases, the resulting period is not exact (it is too short).
        public fun reverseMaximalSuffix(arr: List<UByte>, knownPeriod: Int, orderGreater: Boolean): Int {
            var left = 0
            // Corresponds to i in the paper
            var right = 1
            // Corresponds to j in the paper
            var offset = 0
            // Corresponds to k in the paper, but starting at 0
            // to match 0-based indexing.
            var period = 1
            // Corresponds to p in the paper
            val n = arr.size
            while (right + offset < n) {
                val a = arr[n - (1 + right + offset)]
                val b = arr[n - (1 + left + offset)]
                if ((a < b && !orderGreater) || (a > b && orderGreater)) {
                    // Suffix is smaller, period is entire prefix so far.
                    right += offset + 1
                    offset = 0
                    period = right - left
                } else if (a == b) {
                    // Advance through repetition of the current period.
                    if (offset + 1 == period) {
                        right += offset + 1
                        offset = 0
                    } else {
                        offset += 1
                    }
                } else {
                    // Suffix is larger, start over from current location.
                    left = right
                    right += 1
                    offset = 0
                    period = 1
                }
                if (period == knownPeriod) {
                    break
                }
            }
            if (false) {
                check(period <= knownPeriod)
            }
            return left
        }
    }
}

// TwoWayStrategy allows the algorithm to either skip non-matches as quickly
// as possible, or to work in a mode where it emits Rejects relatively quickly.
public interface TwoWayStrategy<out Output> {
    public fun useEarlyReject(): Boolean

    public fun rejecting(a: Int, b: Int): Output

    public fun matching(a: Int, b: Int): Output
}

/**
 * Skip to match intervals as quickly as possible
 */
public object MatchOnly : TwoWayStrategy<Any?, Pair<Int, Int>?> {
    public override fun useEarlyReject(): Boolean = false

    public override fun rejecting(a: Int, b: Int): Pair<Int, Int>? = null

    public override fun matching(a: Int, b: Int): Pair<Int, Int>? = Pair(a, b)
}

/**
 * Emit Rejects regularly
 */
public object RejectAndMatch : TwoWayStrategy<Any?, SearchStep> {
    public override fun useEarlyReject(): Boolean = true

    public override fun rejecting(a: Int, b: Int): SearchStep = SearchStep.Reject(a, b)

    public override fun matching(a: Int, b: Int): SearchStep = SearchStep.Match(a, b)
}

/**
 * SIMD search for short needles based on
 * Wojciech Muła's "SIMD-friendly algorithms for substring searching"[0]
 *
 * It skips ahead by the vector width on each iteration (rather than the needle length as two-way
 * does) by probing the first and last byte of the needle for the whole vector width
 * and only doing full needle comparisons when the vectorized probe indicated potential matches.
 *
 * Since the x8664 baseline only offers SSE2 we only use u8x16 here.
 * If we ever ship std with for x86-64-v3 or adapt this for other platforms then wider vectors
 * should be evaluated.
 *
 * Similarly, on LoongArch the 128-bit LSX vector extension is the baseline,
 * so we also use `u8x16` there. Wider vector widths may be considered
 * for future LoongArch extensions (e.g., LASX).
 *
 * For haystacks smaller than vector-size + needle length it falls back to
 * a naive O(n*m) search so this implementation should not be called on larger needles.
 *
 * [0]: http://0x80.pl/articles/simd-strfind.html#sse-avx2
 */
public fun simdContains(needle: String, haystack: String): Boolean? {
    // The loop condition must ensure that there's enough headroom to read LANE bytes,
    // and not only at the current index but also at the index shifted by blockOffset
    val UNROLL: Int = 4
    val needle1 = needle.encodeToByteArray().map { it.toUByte() }
    val haystack1 = haystack.encodeToByteArray().map { it.toUByte() }
    if (false) {
        check(needle1.size > 1)
    }
    val firstProbe = needle1[0]
    val lastByteOffset = needle1.size - 1
    // the offset used for the 2nd vector
    val secondProbeOffset1 =
        if (needle1.size == 2) {
            // never bail out on len=2 needles because the probes will fully cover them and have
            // no degenerate cases.
            1
        } else {
            // try a few bytes in case first and last byte of the needle are the same
            val secondProbeOffset = (rustSaturatingSub(needle1.size, 4) until needle1.size).rfind({ idx -> needle1[idx] != firstProbe }) ?: // fall back to other search methods if we can't find any different bytes
    // since we could otherwise hit some degenerate cases
    return null
            secondProbeOffset
        }
    // do a naive search if the haystack is too small to fit
    if (haystack1.size < Block.LEN + lastByteOffset) {
        return haystack1.windows(needle1.size).any({ c -> c == needle1 })
    }
    val firstProbe1: Block = Block.splat(firstProbe)
    val secondProbe: Block = Block.splat(needle1[secondProbeOffset1])
    // first byte are already checked by the outer loop. to verify a match only the
    // remainder has to be compared.
    val trimmedNeedle = ((needle1).slice(1 until needle1.size))
    // this @cold is load-bearing, benchmark before removing it...
    val checkMask =
        closure@{ idx, mask: UShort, skip: Boolean ->
            if (skip) {
                return@closure false
            }
            // and so is this. optimizations are weird.
            var mask1 = mask
            while (mask1 != 0) {
                val trailing = mask1.trailingZeros()
                val offset = idx + trailing.toInt() + 1
                // SAFETY: mask is between 0 and 15 trailing zeroes, we skip one additional byte that was already compared
                // and then take trimmedNeedle.len() bytes. This is within the bounds defined by the outer loop
                run {
                    val sub = haystack1.getUnchecked(offset until Int.MAX_VALUE).getUnchecked(0 until trimmedNeedle.size)
                    if (smallSliceEq(sub, trimmedNeedle)) {
                        return@closure true
                    }
                }
                mask1 = mask1 and (1 shl trailing.toInt()).inv()
            }
            false
        }
    val testChunk =
        { idx ->
            // SAFETY: this requires at least LANES bytes being readable at idx
            // that is ensured by the loop ranges (see comments below)
            val a: Block = (ObjectSlotLayout<Block>()).readNative((ConstPtr((ConstPtr((haystack1.asPtr()).rawAddress + ((idx).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)).rawAddress)
            // SAFETY: this requires LANES + blockOffset bytes being readable at idx
            val b: Block = (ObjectSlotLayout<Block>()).readNative((ConstPtr((ConstPtr((ConstPtr((haystack1.asPtr()).rawAddress + ((idx).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress + ((secondProbeOffset1).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress)).rawAddress)
            val eqFirst: Mask = a.simdEq(firstProbe1)
            val eqLast: Mask = b.simdEq(secondProbe)
            val both = eqFirst.bitand(eqLast)
            both.toBitmask().toUShort()
        }
    var i = 0
    var result = false
    while (i + lastByteOffset + UNROLL * Block.LEN < haystack1.size && !result) {
        val masks = UShortArray((UNROLL).toInt()) { 0u.toUShort() }
        for (j in 0 until UNROLL) {
            masks[j] = testChunk(i + j * Block.LEN)
        }
        for (j1 in 0 until UNROLL) {
            val mask = masks[j1]
            if (mask != 0u.toUShort()) {
                result = result or checkMask(i + j1 * Block.LEN, mask, result)
            }
        }
        i += UNROLL * Block.LEN
    }
    while (i + lastByteOffset + Block.LEN < haystack1.size && !result) {
        val mask1 = testChunk(i)
        if (mask1 != 0) {
            result = result or checkMask(i, mask1, result)
        }
        i += Block.LEN
    }
    // Process the tail that didn't fit into LANES-sized steps.
    // This simply repeats the same procedure but as right-aligned chunk instead
    // of a left-aligned one. The last byte must be exactly flush with the string end so
    // we don't miss a single byte or read out of bounds.
    val i1 = haystack1.size - lastByteOffset - Block.LEN
    val mask2 = testChunk(i1)
    if (mask2 != 0) {
        result = result or checkMask(i1, mask2, result)
    }
    return result
}

/**
 * Compares short slices for equality.
 *
 * It avoids a call to libc's memcmp which is faster on long slices
 * due to SIMD optimizations but it incurs a function call overhead.
 *
 * # Safety
 *
 * Both slices must have the same length.
 */
public fun smallSliceEq(x: List<UByte>, y: List<UByte>): Boolean {
    if (false) {
        when (val matchVal = Pair(x.size, y.size)) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (!(leftVal == rightVal)) {
                    val kind = eq
                    assertFailed(kind, leftVal, rightVal, Option.None)
                }
            }
        }
    }
    // This function is adapted from
    // https://github.com/BurntSushi/memchr/blob/8037d11b4357b0f07be2bb66dc2659d9cf28ad32/src/memmem/util.rs#L32
    // If we don't have enough bytes to do 4-byte at a time loads, then
    // fall back to the naive slow version.
    //
    // Potential alternative: We could do a copyNonoverlapping combined with a mask instead
    // of a loop. Benchmark it.
    if (x.size < 4) {
        for ((b1, b2) in (x.zip(y))) {
            if (b1 != b2) {
                return false
            }
        }
        return true
    }
    // When we have 4 or more bytes to compare, then proceed in chunks of 4 at
    // a time using unaligned loads.
    //
    // Also, why do 4 byte loads instead of, say, 8 byte loads? The reason is
    // that this particular version of memcmp is likely to be called with tiny
    // needles. That means that if we do 8 byte loads, then a higher proportion
    // of memcmp calls will use the slower variant above. With that said, this
    // is a hypothesis and is only loosely supported by benchmarks. There's
    // likely some improvement that could be made here. The main thing here
    // though is to optimize for latency, not throughput.
    // SAFETY: Via the conditional above, we know that both `px` and `py`
    // have the same length, so `px<pxend` implies that `py<pyend`.
    // Thus, dereferencing both `px` and `py` in the loop below is safe.
    //
    // Moreover, we set `pxend` and `pyend` to be 4 bytes before the actual
    // end of `px` and `py`. Thus, the final dereference outside of the
    // loop is guaranteed to be valid. (The final comparison will overlap with
    // the last comparison done in the loop for lengths that aren't multiples
    // of four.)
    //
    // Finally, we needn't worry about alignment here, since we do unaligned
    // loads.
    return run {
        var (px, py) = Pair(x.asPtr(), y.asPtr())
        val (pxend, pyend) = Pair(ConstPtr((px).rawAddress + ((x.size - 4).toLong() * (PrimitiveLayouts.U8).strideBytes)), ConstPtr((py).rawAddress + ((y.size - 4).toLong() * (PrimitiveLayouts.U8).strideBytes)))
        while (((px).rawAddress.toULong() < (pxend).rawAddress.toULong())) {
            val vx = (PrimitiveLayouts.U32).readNative(((ConstPtr(px.rawAddress, px.rawMeta))).rawAddress)
            val vy = (PrimitiveLayouts.U32).readNative(((ConstPtr(py.rawAddress, py.rawMeta))).rawAddress)
            if (vx != vy) {
                return false
            }
            px = ConstPtr((px).rawAddress + ((4).toLong() * (PrimitiveLayouts.U8).strideBytes))
            py = ConstPtr((py).rawAddress + ((4).toLong() * (PrimitiveLayouts.U8).strideBytes))
        }
        val vx1 = (PrimitiveLayouts.U32).readNative(((ConstPtr(pxend.rawAddress, pxend.rawMeta))).rawAddress)
        val vy1 = (PrimitiveLayouts.U32).readNative(((ConstPtr(pyend.rawAddress, pyend.rawMeta))).rawAddress)
        vx1 == vy1
    }
}
