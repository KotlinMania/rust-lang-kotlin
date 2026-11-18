package io.github.kotlinmania.rustlang.core.str

import io.github.kotlinmania.rustlang.core.char.methods.isAscii
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.intrinsics.constEvalSelect
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.linearMutSliceView
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.panicking.AssertKind
import io.github.kotlinmania.rustlang.core.panicking.assertFailed
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.len
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.len
import io.github.kotlinmania.rustlang.core.ptr.mutptr.splitAtMut
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.index.SliceIndex
import io.github.kotlinmania.rustlang.core.slice.iter
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.slice.splitAt
import io.github.kotlinmania.rustlang.core.slice.splitAtMut
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8Mut
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8Unchecked
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8UncheckedMut
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.core.str.iter.Bytes
import io.github.kotlinmania.rustlang.core.str.iter.CharIndices
import io.github.kotlinmania.rustlang.core.str.iter.Chars
import io.github.kotlinmania.rustlang.core.str.iter.EncodeUtf16
import io.github.kotlinmania.rustlang.core.str.iter.EscapeDebug
import io.github.kotlinmania.rustlang.core.str.iter.EscapeDefault
import io.github.kotlinmania.rustlang.core.str.iter.EscapeUnicode
import io.github.kotlinmania.rustlang.core.str.iter.Lines
import io.github.kotlinmania.rustlang.core.str.iter.LinesAny
import io.github.kotlinmania.rustlang.core.str.iter.MatchIndices
import io.github.kotlinmania.rustlang.core.str.iter.MatchIndicesInternal
import io.github.kotlinmania.rustlang.core.str.iter.Matches
import io.github.kotlinmania.rustlang.core.str.iter.MatchesInternal
import io.github.kotlinmania.rustlang.core.str.iter.RMatchIndices
import io.github.kotlinmania.rustlang.core.str.iter.RMatches
import io.github.kotlinmania.rustlang.core.str.iter.RSplit
import io.github.kotlinmania.rustlang.core.str.iter.RSplitN
import io.github.kotlinmania.rustlang.core.str.iter.RSplitTerminator
import io.github.kotlinmania.rustlang.core.str.iter.Split
import io.github.kotlinmania.rustlang.core.str.iter.SplitAsciiWhitespace
import io.github.kotlinmania.rustlang.core.str.iter.SplitInclusive
import io.github.kotlinmania.rustlang.core.str.iter.SplitInternal
import io.github.kotlinmania.rustlang.core.str.iter.SplitN
import io.github.kotlinmania.rustlang.core.str.iter.SplitNInternal
import io.github.kotlinmania.rustlang.core.str.iter.SplitTerminator
import io.github.kotlinmania.rustlang.core.str.iter.SplitWhitespace
import io.github.kotlinmania.rustlang.core.str.pattern.Pattern
import io.github.kotlinmania.rustlang.core.str.pattern.Searcher
import io.github.kotlinmania.rustlang.core.str.traits.FromStr
import io.github.kotlinmania.rustlang.core.str.traits.fromStr

public fun sliceErrorFail(s: String, begin: Int, end: Int): Nothing = constEvalSelect(Triple(s, begin, end), ::sliceErrorFailCt, ::sliceErrorFailRt)

public fun sliceErrorFailCt(arg0: String, arg1: Int, arg2: Int): Nothing {
    error("failed to slice string")
}

public fun sliceErrorFailRt(s: String, begin: Int, end: Int): Nothing {
    val len = s.length
    // 1. begin is OOB.
    if (begin > len) {
        error("start byte index $begin is out of bounds for string of length $len")
    }
    // 2. end is OOB.
    if (end > len) {
        error("end byte index $end is out of bounds for string of length $len")
    }
    // 3. range is backwards.
    if (begin > end) {
        error("byte range starts at $begin but ends at $end")
    }
    // 4. begin is inside a character.
    if (!s.isCharBoundary(begin)) {
        val floor = s.floorCharBoundary(begin)
        val ceil = s.ceilCharBoundary(begin)
        val range = floor until ceil
        val ch = s.substring(floor until ceil).chars().next()!!
        error("start byte index $begin is not a char boundary; it is inside $ch (bytes $range of string)")
    }
    // 5. end is inside a character.
    if (!s.isCharBoundary(end)) {
        error("end byte index $end is not a char boundary; it is inside $ch (bytes $range of string)")
    }
    if (false) {
        when (val matchVal = Pair(end, len)) {
            else -> {
                val leftVal = matchVal.first
                val rightVal = matchVal.second
                if (!(leftVal == rightVal)) {
                    val kind = AssertKind.Eq
                    assertFailed(kind, leftVal, rightVal, Option.None())
                }
            }
        }
    }
    error("end byte index $end is out of bounds for string of length $len")
}

/**
 * Returns the length of `self`.
 *
 * This length is in bytes, not [char]s or graphemes. In other words,
 * it might not be what a human considers the length of the string.
 *
 * [char]: prim@char
 *
 * # Examples
 *
 * ```kotlin
 * val len = "foo".len()
 * check(3 == len)
 * check("ƒoo".len() == 4)
 * // fancy f!
 * check("ƒoo".chars().count() == 3)
 * ```
 */
public fun String.len(): Int = (this.encodeToByteArray().map { it.toUByte() }).size

/**
 * Returns `true` if `self` has a length of zero bytes.
 *
 * # Examples
 *
 * ```kotlin
 * val s = ""
 * check(s.isEmpty())
 * val s1 = "not empty"
 * check(!s1.isEmpty())
 * ```
 */
public fun String.isEmpty(): Boolean = this.length == 0

/**
 * Converts a slice of bytes to a string slice.
 *
 * A string slice ([str]) is made of bytes ([u8]), and a byte slice
 * ([`&[u8]`][byteslice]) is made of bytes, so this function converts between
 * the two. Not all byte slices are valid string slices, however: [str] requires
 * that it is valid UTF-8. `fromUtf8()` checks to ensure that the bytes are valid
 * UTF-8, and then does the conversion.
 *
 * [str]: str
 * [byteslice]: prim@slice
 *
 * If you are sure that the byte slice is valid UTF-8, and you don't want to
 * incur the overhead of the validity check, there is an unchecked version of
 * this function, [fromUtf8Unchecked], which has the same
 * behavior but skips the check.
 *
 * If you need a `String` instead of a `String`, consider
 * [`String.fromUtf8`][string].
 *
 * [string]: ../std/string/struct.String.html#method.fromUtf8
 *
 * Because you can stack-allocate a `[UByte; N]`, and you can take a
 * [`&[u8]`][byteslice] of it, this function is one way to have a
 * stack-allocated string. There is an example of this in the
 * examples section below.
 *
 * [byteslice]: slice
 *
 * # Errors
 *
 * Returns `Err` if the slice is not UTF-8 with a description as to why the
 * provided slice is not UTF-8.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // some bytes, in a vector
 * val sparkleHeart = mutableListOf(240, 159, 146, 150)
 * // We can use the ? (try) operator to check if the bytes are valid
 * val sparkleHeart1 = String.fromUtf8(sparkleHeart).getOrElse { return Result.failure(it) }
 * check("💖" == sparkleHeart1)
 * ```
 *
 * Incorrect bytes:
 *
 * ```kotlin
 * // some invalid bytes, in a vector
 * val sparkleHeart = mutableListOf(0, 159, 146, 150)
 * check(String.fromUtf8(sparkleHeart).isErr())
 * ```
 *
 * See the docs for [Utf8Error] for more details on the kinds of
 * errors that can be returned.
 *
 * A "stack allocated string":
 *
 * ```kotlin
 * // some bytes, in a stack-allocated array
 * val sparkleHeart = listOf(240, 159, 146, 150)
 * // We know these bytes are valid, so just use `unwrap()`.
 * val sparkleHeart1: String = String.fromUtf8(sparkleHeart).unwrap()
 * check("💖" == sparkleHeart1)
 * ```
 */
public fun String.Companion.fromUtf8(v: List<UByte>): Result<String, Utf8Error> = io.github.kotlinmania.rustlang.core.str.converts.fromUtf8(v)

/**
 * Converts a mutable slice of bytes to a mutable string slice.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // "Hello, Rust!" as a mutable vector
 * var hellorust = mutableListOf(72, 101, 108, 108, 111, 44, 32, 82, 117, 115, 116, 33)
 * // As we know these bytes are valid, we can use `unwrap()`
 * val outstr = String.fromUtf8Mut(hellorust).unwrap()
 * check("Hello, Rust!" == outstr)
 * ```
 *
 * Incorrect bytes:
 *
 * ```kotlin
 * // Some invalid bytes in a mutable vector
 * var invalid = mutableListOf(128, 223)
 * check(String.fromUtf8Mut(invalid).isErr())
 * ```
 * See the docs for [Utf8Error] for more details on the kinds of
 * errors that can be returned.
 */
public fun String.Companion.fromUtf8Mut(v: MutableList<UByte>): Result<String, Utf8Error> = io.github.kotlinmania.rustlang.core.str.converts.fromUtf8Mut(v)

/**
 * Converts a slice of bytes to a string slice without checking
 * that the string contains valid UTF-8.
 *
 * See the safe version, [fromUtf8], for more information.
 *
 * # Safety
 *
 * The bytes passed in must be valid UTF-8.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * // some bytes, in a vector
 * val sparkleHeart = mutableListOf(240, 159, 146, 150)
 * val sparkleHeart1 = String.fromUtf8Unchecked(sparkleHeart)
 * check("💖" == sparkleHeart1)
 * ```
 */
public fun String.Companion.fromUtf8Unchecked(v: List<UByte>): String {
    // SAFETY: converts.fromUtf8Unchecked has the same safety requirements as this function.
    return io.github.kotlinmania.rustlang.core.str.converts.fromUtf8Unchecked(v)
}

/**
 * Converts a slice of bytes to a string slice without checking
 * that the string contains valid UTF-8; mutable version.
 *
 * # Safety
 *
 * See the immutable version, [fromUtf8Unchecked] for documentation and safety requirements.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * var heart = mutableListOf(240, 159, 146, 150)
 * val heart1 = String.fromUtf8UncheckedMut(heart)
 * check("💖" == heart1)
 * ```
 */
public fun String.Companion.fromUtf8UncheckedMut(v: MutableList<UByte>): String {
    // SAFETY: converts.fromUtf8UncheckedMut has the same safety requirements as this function.
    return io.github.kotlinmania.rustlang.core.str.converts.fromUtf8UncheckedMut(v)
}

/**
 * Checks that `index`-th byte is the first byte in a UTF-8 code point
 * sequence or the end of the string.
 *
 * The start and end of the string (when `index == self.len()`) are
 * considered to be boundaries.
 *
 * Returns `false` if `index` is greater than `self.len()`.
 *
 * # Examples
 *
 * ```kotlin
 * val s = "Löwe 老虎 Léopard"
 * check(s.isCharBoundary(0))
 * // start of `老`
 * check(s.isCharBoundary(6))
 * check(s.isCharBoundary(s.len()))
 * // second byte of `ö`
 * check(!s.isCharBoundary(2))
 * // third byte of `老`
 * check(!s.isCharBoundary(8))
 * ```
 */
public fun String.isCharBoundary(index: Int): Boolean {
    // 0 is always ok.
    // Test for 0 explicitly so that it can optimize out the check
    // easily and skip reading string data for that case.
    // Note that optimizing `self.get(.. index)` relies on this.
    if (index == 0) {
        return true
    }
    return if (index >= this.length) {
        // For `true` we have two options:
        //
        // - index == self.len()
        // Empty strings are valid, so return true
        // - index > self.len()
        // In this case return false
        //
        // The check is placed exactly here, because it improves generated
        // code on higher opt-levels. See PR #84751 for more details.
        index == this.length
    } else {
        this.encodeToByteArray().map { it.toUByte() }[index].isUtf8CharBoundary()
    }
}

/**
 * Finds the closest `x` not exceeding `index` where [isCharBoundary] is `true`.
 *
 * This method can help you truncate a string so that it's still valid UTF-8, but doesn't
 * exceed a given number of bytes. Note that this is done purely at the character level
 * and can still visually split graphemes, even though the underlying characters aren't
 * split. For example, the emoji 🧑‍🔬 (scientist) could be split so that the string only
 * includes 🧑 (person) instead.
 *
 * [isCharBoundary]: Self.isCharBoundary
 *
 * # Examples
 *
 * ```kotlin
 * val s = "❤️🧡💛💚💙💜"
 * check(s.len() == 26)
 * check(!s.isCharBoundary(13))
 * val closest = s.floorCharBoundary(13)
 * check(closest == 10)
 * check(s[0 until closest] == "❤️🧡")
 * ```
 */
public fun String.floorCharBoundary(index: Int): Int {
    if (index >= this.length) {
        return this.length
    }
    if (this.encodeToByteArray().map { it.toUByte() }[index].isUtf8CharBoundary()) {
        return index
    }
    if (false) {
        check(this.encodeToByteArray().map { it.toUByte() }[0].isUtf8CharBoundary())
    }
    // SAFETY: `self.asBytes()[0]` is always a char boundary with valid `String`
    assertUnchecked(index >= 1)
    if (this.encodeToByteArray().map { it.toUByte() }[index - 1].isUtf8CharBoundary()) {
        return index - 1
    }
    // SAFETY: `self.asBytes()[0]` is always a char boundary with valid `String`
    assertUnchecked(index >= 2)
    if (this.encodeToByteArray().map { it.toUByte() }[index - 2].isUtf8CharBoundary()) {
        return index - 2
    }
    if (false) {
        check(index >= 3)
    }
    if (false) {
        check(this.encodeToByteArray().map { it.toUByte() }[index - 3].isUtf8CharBoundary())
    }
    return index - 3
}

/**
 * Finds the closest `x` not below `index` where [isCharBoundary] is `true`.
 *
 * If `index` is greater than the length of the string, this returns the length of the string.
 *
 * This method is the natural complement to [floorCharBoundary]. See that method
 * for more details.
 *
 * [floorCharBoundary]: str.floorCharBoundary
 * [isCharBoundary]: Self.isCharBoundary
 *
 * # Examples
 *
 * ```kotlin
 * val s = "❤️🧡💛💚💙💜"
 * check(s.len() == 26)
 * check(!s.isCharBoundary(13))
 * val closest = s.ceilCharBoundary(13)
 * check(closest == 14)
 * check(s[0 until closest] == "❤️🧡💛")
 * ```
 */
public fun String.ceilCharBoundary(index: Int): Int =
    if (index >= this.length) {
        this.length
    } else {
        var i = index
        while (!this.encodeToByteArray().map { it.toUByte() }[i].isUtf8CharBoundary()) {
            i += 1
            if (i >= this.length) {
                break
            }
        }
        if (false) {
            check(i <= index + 3)
        }
        i
    }

/**
 * Converts a string slice to a byte slice. To convert the byte slice back
 * into a string slice, use the [fromUtf8] function.
 *
 * # Examples
 *
 * ```kotlin
 * val bytes = "bors".encodeToByteArray().map { it.toUByte() }
 * check("bors".encodeToByteArray() == bytes)
 * ```
 */
public fun String.asBytes(): List<UByte> {
    // SAFETY: const sound because we reinterpret two types with the same layout
    return transmuteWithLayouts<String, List<UByte>>(this, UnresolvedTypeLayout<String>(), UnresolvedTypeLayout<List<UByte>>())
}

/**
 * Converts a mutable string slice to a mutable byte slice.
 *
 * # Safety
 *
 * The caller must ensure that the content of the slice is valid UTF-8
 * before the borrow ends and the underlying `String` is used.
 *
 * Use of a `String` whose contents are not valid UTF-8 is undefined behavior.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * var s = "Hello"
 * val bytes = s.asBytesMut()
 * check("Hello".encodeToByteArray() == bytes)
 * ```
 *
 * Mutability:
 *
 * ```kotlin
 * var s = "🗻∈🌏"
 * run {
 *     val bytes = s.asBytesMut()
 *     bytes[0] = 0xF0u.toUByte()
 *     bytes[1] = 0x9Fu.toUByte()
 *     bytes[2] = 0x8Du.toUByte()
 *     bytes[3] = 0x94u.toUByte()
 * }
 * check("🍔∈🌏" == s)
 * ```
 */
public fun String.asBytesMut(): MutableList<UByte> {
    // SAFETY: the cast from `String` to `[UByte]` is safe since `String`
    // has the same layout as `[UByte]` (only std can make this guarantee).
    // The pointer dereference is safe since it comes from a mutable reference which
    // is guaranteed to be valid for writes.
    return RustMutableReference(MutPtr((((mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), UnresolvedTypeLayout<List<UByte>>())
}

/**
 * Converts a string slice to a raw pointer.
 *
 * As string slices are a slice of bytes, the raw pointer points to a
 * [u8]. This pointer will be pointing to the first byte of the string
 * slice.
 *
 * The caller must ensure that the returned pointer is never written to.
 * If you need to mutate the contents of the string slice, use [asMutPtr].
 *
 * [asMutPtr]: str.asMutPtr
 *
 * # Examples
 *
 * ```kotlin
 * val s = "Hello"
 * val ptr = s.asPtr()
 * ```
 */
public fun String.asPtr(): ConstPtr = (constPtrFromRustCast(this)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

/**
 * Converts a mutable string slice to a raw pointer.
 *
 * As string slices are a slice of bytes, the raw pointer points to a
 * [u8]. This pointer will be pointing to the first byte of the string
 * slice.
 *
 * It is your responsibility to make sure that the string slice only gets
 * modified in a way that it remains valid UTF-8.
 */
public fun String.asMutPtr(): MutPtr = (mutPtrFromRustCast(this)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }

/**
 * Returns a subslice of `String`.
 *
 * This is the non-panicking alternative to indexing the `String`. Returns
 * [None] whenever equivalent indexing operation would panic.
 *
 * # Examples
 *
 * ```kotlin
 * val v = "🗻∈🌏"
 * check("🗻" == v.get(0 until 4))
 * // indices not on UTF-8 sequence boundaries
 * check(v.get(1 until Int.MAX_VALUE) == null)
 * check(v.get(0 until 8) == null)
 * // out of bounds
 * check(v.get(0 until 42) == null)
 * ```
 */
public fun <Output, I : SliceIndex<String, Output>> String.get(i: I): Output? = i.get(this)

/**
 * Returns a mutable subslice of `String`.
 *
 * This is the non-panicking alternative to indexing the `String`. Returns
 * [None] whenever equivalent indexing operation would panic.
 *
 * # Examples
 *
 * ```kotlin
 * var v = "hello"
 * // correct length
 * check(v.getMut(0 until 5) != null)
 * // out of bounds
 * check(v.getMut(0 until 42) == null)
 * check("he" == v.getMut(0 until 2)?.let({ v1 -> v1 }))
 * check("hello" == v)
 * run {
 *     val s = v.getMut(0 until 2)
 *     val s1 = s.map({ s1 ->
 *         s1.makeAsciiUppercase()
 *         s1
 *     })
 *     check("HE" == s1)
 * }
 * check("HEllo" == v)
 * ```
 */
public fun <Output, I : SliceIndex<String, Output>> String.getMut(i: I): Output? = i.getMut(this)

/**
 * Returns an unchecked subslice of `String`.
 *
 * This is the unchecked alternative to indexing the `String`.
 *
 * # Safety
 *
 * Callers of this function are responsible that these preconditions are
 * satisfied:
 *
 * * The starting index must not exceed the ending index;
 * * Indexes must be within bounds of the original slice;
 * * Indexes must lie on UTF-8 sequence boundaries.
 *
 * Failing that, the returned string slice may reference invalid memory or
 * violate the invariants communicated by the `String` type.
 *
 * # Examples
 *
 * ```kotlin
 * val v = "🗻∈🌏"
 * run {
 *     check("🗻" == v.getUnchecked(0 until 4))
 *     check("∈" == v.getUnchecked(4 until 7))
 *     check("🌏" == v.getUnchecked(7 until 11))
 * }
 * ```
 */
public fun <Output, I : SliceIndex<String, Output>> String.getUnchecked(i: I): Output {
    // SAFETY: the caller must uphold the safety contract for `getUnchecked`;
    // the slice is dereferenceable because `self` is a safe reference.
    // The returned pointer is safe because impls of `SliceIndex` have to guarantee that it is.
    return i.getUnchecked(this)
}

/**
 * Returns a mutable, unchecked subslice of `String`.
 *
 * This is the unchecked alternative to indexing the `String`.
 *
 * # Safety
 *
 * Callers of this function are responsible that these preconditions are
 * satisfied:
 *
 * * The starting index must not exceed the ending index;
 * * Indexes must be within bounds of the original slice;
 * * Indexes must lie on UTF-8 sequence boundaries.
 *
 * Failing that, the returned string slice may reference invalid memory or
 * violate the invariants communicated by the `String` type.
 *
 * # Examples
 *
 * ```kotlin
 * var v = "🗻∈🌏"
 * run {
 *     check("🗻" == v.getUncheckedMut(0 until 4))
 *     check("∈" == v.getUncheckedMut(4 until 7))
 *     check("🌏" == v.getUncheckedMut(7 until 11))
 * }
 * ```
 */
public fun <Output, I : SliceIndex<String, Output>> String.getUncheckedMut(i: I): Output {
    // SAFETY: the caller must uphold the safety contract for `getUncheckedMut`;
    // the slice is dereferenceable because `self` is a safe reference.
    // The returned pointer is safe because impls of `SliceIndex` have to guarantee that it is.
    return i.getUncheckedMut(this)
}

/**
 * Creates a string slice from another string slice, bypassing safety
 * checks.
 *
 * This is generally not recommended, use with caution! For a safe
 * alternative see [str] and [Index].
 *
 * This new slice goes from `begin` to `end`, including `begin` but
 * excluding `end`.
 *
 * To get a mutable string slice instead, see the
 * [sliceMutUnchecked] method.
 *
 * [sliceMutUnchecked]: str.sliceMutUnchecked
 *
 * # Safety
 *
 * Callers of this function are responsible that three preconditions are
 * satisfied:
 *
 * * `begin` must not exceed `end`.
 * * `begin` and `end` must be byte positions within the string slice.
 * * `begin` and `end` must lie on UTF-8 sequence boundaries.
 *
 * # Examples
 *
 * ```kotlin
 * val s = "Löwe 老虎 Léopard"
 * run {
 *     check("Löwe 老虎 Léopard" == s.sliceUnchecked(0, 21))
 * }
 * val s1 = "Hello, world!"
 * run {
 *     check("world" == s1.sliceUnchecked(7, 12))
 * }
 * ```
 */
public fun String.sliceUnchecked(begin: Int, end: Int): String {
    // SAFETY: the caller must uphold the safety contract for `getUnchecked`;
    // the slice is dereferenceable because `self` is a safe reference.
    // The returned pointer is safe because impls of `SliceIndex` have to guarantee that it is.
    return (begin until end).getUnchecked(this)
}

/**
 * Creates a string slice from another string slice, bypassing safety
 * checks.
 *
 * This is generally not recommended, use with caution! For a safe
 * alternative see [str] and [IndexMut].
 *
 * This new slice goes from `begin` to `end`, including `begin` but
 * excluding `end`.
 *
 * To get an immutable string slice instead, see the
 * [sliceUnchecked] method.
 *
 * [sliceUnchecked]: str.sliceUnchecked
 *
 * # Safety
 *
 * Callers of this function are responsible that three preconditions are
 * satisfied:
 *
 * * `begin` must not exceed `end`.
 * * `begin` and `end` must be byte positions within the string slice.
 * * `begin` and `end` must lie on UTF-8 sequence boundaries.
 */
public fun String.sliceMutUnchecked(begin: Int, end: Int): String {
    // SAFETY: the caller must uphold the safety contract for `getUncheckedMut`;
    // the slice is dereferenceable because `self` is a safe reference.
    // The returned pointer is safe because impls of `SliceIndex` have to guarantee that it is.
    return (begin until end).getUncheckedMut(this)
}

/**
 * Divides one string slice into two at a byte offset.
 *
 * The argument, `mid`, should be a byte offset from the start of the
 * string. It must also be on the boundary of a UTF-8 code point.
 *
 * The first returned slice contains exactly the first `mid` bytes, and the
 * second contains all remaining bytes.
 *
 * To get mutable string slices instead, see the [splitAtMut]
 * method.
 *
 * [splitAtMut]: str.splitAtMut
 *
 * # Panics
 *
 * Panics if `mid` is not on a UTF-8 code point boundary, or if it is past
 * the end of the last code point of the string slice.  For a non-panicking
 * alternative see [`splitAtChecked`](str.splitAtChecked).
 *
 * # Examples
 *
 * ```kotlin
 * val s = "Per Martin-Löf"
 * val (first, last) = s.splitAt(3)
 * check("Per" == first)
 * check(" Martin-Löf" == last)
 * ```
 */
public fun String.splitAt(mid: Int): Pair<String, String> =
    when (val matchVal = this.splitAtChecked(mid)) {
        null -> sliceErrorFail(this, 0, mid)
        else -> {
            val pair = matchVal
            pair
        }
    }

/**
 * Divides one mutable string slice into two at a byte offset.
 *
 * The argument, `mid`, should be a byte offset from the start of the
 * string. It must also be on the boundary of a UTF-8 code point.
 *
 * The first returned slice contains exactly the first `mid` bytes, and the
 * second contains all remaining bytes.
 *
 * To get immutable string slices instead, see the [splitAt] method.
 *
 * [splitAt]: str.splitAt
 *
 * # Panics
 *
 * Panics if `mid` is not on a UTF-8 code point boundary, or if it is past
 * the end of the last code point of the string slice.  For a non-panicking
 * alternative see [`splitAtMutChecked`](str.splitAtMutChecked).
 *
 * # Examples
 *
 * ```kotlin
 * var s = "Per Martin-Löf".toString()
 * run {
 *     val (first, last) = s.splitAtMut(3)
 *     first.makeAsciiUppercase()
 *     check("PER" == first)
 *     check(" Martin-Löf" == last)
 * }
 * check("PER Martin-Löf" == s)
 * ```
 */
public fun String.splitAtMut(mid: Int): Pair<String, String> {
    // isCharBoundary checks that the index is in [0, .len()]
    return if (this.isCharBoundary(mid)) {
        // SAFETY: just checked that `mid` is on a char boundary.
        this.splitAtMutUnchecked(mid)
    } else {
        sliceErrorFail(this, 0, mid)
    }
}

/**
 * Divides one string slice into two at a byte offset.
 *
 * The argument, `mid`, should be a valid byte offset from the start of the
 * string. It must also be on the boundary of a UTF-8 code point. The
 * method returns `None` if that’s not the case.
 *
 * The first returned slice contains exactly the first `mid` bytes, and the
 * second contains all remaining bytes.
 *
 * To get mutable string slices instead, see the [splitAtMutChecked]
 * method.
 *
 * [splitAtMutChecked]: str.splitAtMutChecked
 *
 * # Examples
 *
 * ```kotlin
 * val s = "Per Martin-Löf"
 * val (first, last) = s.splitAtChecked(3).unwrap()
 * check("Per" == first)
 * check(" Martin-Löf" == last)
 * check(null == s.splitAtChecked(13))
 * // Inside “ö”
 * check(null == s.splitAtChecked(16))
 * // Beyond the string length
 * ```
 */
public fun String.splitAtChecked(mid: Int): Pair<String, String>? {
    // isCharBoundary checks that the index is in [0, .len()]
    return if (this.isCharBoundary(mid)) {
        // SAFETY: just checked that `mid` is on a char boundary.
        this.splitAtUnchecked(mid)
    } else {
        null
    }
}

/**
 * Divides one mutable string slice into two at a byte offset.
 *
 * The argument, `mid`, should be a valid byte offset from the start of the
 * string. It must also be on the boundary of a UTF-8 code point. The
 * method returns `None` if that’s not the case.
 *
 * The first returned slice contains exactly the first `mid` bytes, and the
 * second contains all remaining bytes.
 *
 * To get immutable string slices instead, see the [splitAtChecked] method.
 *
 * [splitAtChecked]: str.splitAtChecked
 *
 * # Examples
 *
 * ```kotlin
 * var s = "Per Martin-Löf".toString()
 * when (val matchVal = s.splitAtMutChecked(3)) {
 *     null -> {}
 *     else -> {
 *         val first = matchVal.first
 *         val last = matchVal.second
 *         first.makeAsciiUppercase()
 *         check("PER" == first)
 *         check(" Martin-Löf" == last)
 *     }
 * }
 * check("PER Martin-Löf" == s)
 * check(null == s.splitAtMutChecked(13))
 * // Inside “ö”
 * check(null == s.splitAtMutChecked(16))
 * // Beyond the string length
 * ```
 */
public fun String.splitAtMutChecked(mid: Int): Pair<String, String>? {
    // isCharBoundary checks that the index is in [0, .len()]
    return if (this.isCharBoundary(mid)) {
        // SAFETY: just checked that `mid` is on a char boundary.
        this.splitAtMutUnchecked(mid)
    } else {
        null
    }
}

/**
 * Divides one string slice into two at a byte offset.
 *
 * # Safety
 *
 * The caller must ensure that `mid` is a valid byte offset from the start
 * of the string and falls on the boundary of a UTF-8 code point.
 */
public fun String.splitAtUnchecked(mid: Int): Pair<String, String> {
    val len = this.length
    val ptr = this.asPtr()
    Unit
    // SAFETY: caller guarantees `mid` is on a char boundary.
    return Pair(fromUtf8Unchecked(linearSliceView(ptr, mid.toInt(), PrimitiveLayouts.U8)), fromUtf8Unchecked(linearSliceView(ConstPtr((ptr).rawAddress + ((mid).toLong() * (PrimitiveLayouts.U8).strideBytes)), (len - mid).toInt(), PrimitiveLayouts.U8)))
}

/**
 * Divides one mutable string slice into two at a byte offset.
 *
 * # Safety
 *
 * The caller must ensure that `mid` is a valid byte offset from the start
 * of the string and falls on the boundary of a UTF-8 code point.
 */
public fun String.splitAtMutUnchecked(mid: Int): Pair<String, String> {
    val len = this.length
    val ptr = this.asMutPtr()
    Unit
    // SAFETY: caller guarantees `mid` is on a char boundary.
    return Pair(fromUtf8UncheckedMut(linearMutSliceView(ptr, mid.toInt(), PrimitiveLayouts.U8)), fromUtf8UncheckedMut(linearMutSliceView(MutPtr((ptr).rawAddress + ((mid).toLong() * (PrimitiveLayouts.U8).strideBytes)), (len - mid).toInt(), PrimitiveLayouts.U8)))
}

/**
 * Returns an iterator over the [char]s of a string slice.
 *
 * As a string slice consists of valid UTF-8, we can iterate through a
 * string slice by [char]. This method returns such an iterator.
 *
 * It's important to remember that [char] represents a Unicode Scalar
 * Value, and might not match your idea of what a `character` is. Iteration
 * over grapheme clusters may be what you actually want. This functionality
 * is not provided by Rust's standard library, check crates.io instead.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val word = "goodbye"
 * val count = word.chars().count()
 * check(7 == count)
 * var chars = word.chars()
 * check('g' == chars.next())
 * check('o' == chars.next())
 * check('o' == chars.next())
 * check('d' == chars.next())
 * check('b' == chars.next())
 * check('y' == chars.next())
 * check('e' == chars.next())
 * check(null == chars.next())
 * ```
 *
 * Remember, [char]s might not match your intuition about characters:
 *
 * [char]: prim@char
 *
 * ```kotlin
 * val y = "y̆"
 * var chars = y.chars()
 * check('y' == chars.next())
 * // not y̆'
 * check('\u0306' == chars.next())
 * check(null == chars.next())
 * ```
 */
public fun String.chars(): Chars = Chars(iter = this.encodeToByteArray().map { it.toUByte() }.iter())

/**
 * Returns an iterator over the [char]s of a string slice, and their
 * positions.
 *
 * As a string slice consists of valid UTF-8, we can iterate through a
 * string slice by [char]. This method returns an iterator of both
 * these [char]s, as well as their byte positions.
 *
 * The iterator yields tuples. The position is first, the [char] is
 * second.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val word = "goodbye"
 * val count = word.charIndices().count()
 * check(7 == count)
 * var charIndices = word.charIndices()
 * check(Pair(0, 'g') == charIndices.next())
 * check(Pair(1, 'o') == charIndices.next())
 * check(Pair(2, 'o') == charIndices.next())
 * check(Pair(3, 'd') == charIndices.next())
 * check(Pair(4, 'b') == charIndices.next())
 * check(Pair(5, 'y') == charIndices.next())
 * check(Pair(6, 'e') == charIndices.next())
 * check(null == charIndices.next())
 * ```
 *
 * Remember, [char]s might not match your intuition about characters:
 *
 * [char]: prim@char
 *
 * ```kotlin
 * val yes = "y̆es"
 * var charIndices = yes.charIndices()
 * check(Pair(0, 'y') == charIndices.next())
 * // not (0, y̆')
 * check(Pair(1, '\u0306') == charIndices.next())
 * // note the 3 here - the previous character took up two bytes
 * check(Pair(3, 'e') == charIndices.next())
 * check(Pair(4, 's') == charIndices.next())
 * check(null == charIndices.next())
 * ```
 */
public fun String.charIndices(): CharIndices = CharIndices(frontOffset = 0, iter = this.chars())

/**
 * Returns an iterator over the bytes of a string slice.
 *
 * As a string slice consists of a sequence of bytes, we can iterate
 * through a string slice by byte. This method returns such an iterator.
 *
 * # Examples
 *
 * ```kotlin
 * var bytes = "bors".bytes()
 * check(98u.toUByte() == bytes.next())
 * check(111u.toUByte() == bytes.next())
 * check(114u.toUByte() == bytes.next())
 * check(115u.toUByte() == bytes.next())
 * check(null == bytes.next())
 * ```
 */
public fun String.bytes(): Bytes = Bytes(this.encodeToByteArray().map { it.toUByte() }.iter().copied())

/**
 * Splits a string slice by whitespace.
 *
 * The iterator returned will return string slices that are sub-slices of
 * the original string slice, separated by any amount of whitespace.
 *
 * `Whitespace` is defined according to the terms of the Unicode Derived
 * Core Property `WhiteSpace`. If you only want to split on ASCII whitespace
 * instead, use [splitAsciiWhitespace].
 *
 * [splitAsciiWhitespace]: str.splitAsciiWhitespace
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * var iter = "A few words".splitWhitespace()
 * check("A" == iter.next())
 * check("few" == iter.next())
 * check("words" == iter.next())
 * check(null == iter.next())
 * ```
 *
 * All kinds of whitespace are considered:
 *
 * ```kotlin
 * var iter = " Mary   had\ta little  \n\t lamb".splitWhitespace()
 * check("Mary" == iter.next())
 * check("had" == iter.next())
 * check("a" == iter.next())
 * check("little" == iter.next())
 * check("lamb" == iter.next())
 * check(null == iter.next())
 * ```
 *
 * If the string is empty or all whitespace, the iterator yields no string slices:
 * ```kotlin
 * check("".splitWhitespace().next() == null)
 * check("   ".splitWhitespace().next() == null)
 * ```
 */
public fun String.splitWhitespace(): SplitWhitespace = SplitWhitespace(inner = this.split(IsWhitespace()).filter(IsNotEmpty()))

/**
 * Splits a string slice by ASCII whitespace.
 *
 * The iterator returned will return string slices that are sub-slices of
 * the original string slice, separated by any amount of ASCII whitespace.
 *
 * This uses the same definition as [char.isAsciiWhitespace].
 * To split by Unicode `Whitespace` instead, use [splitWhitespace].
 * Note that because of this difference in definition, even if `s.isAscii()`
 * is `true`, `s.splitAsciiWhitespace()` behavior will differ from `s.splitWhitespace()`
 * if `s` contains U+000B VERTICAL TAB.
 *
 * [splitWhitespace]: str.splitWhitespace
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * var iter = "A few words".splitAsciiWhitespace()
 * check("A" == iter.next())
 * check("few" == iter.next())
 * check("words" == iter.next())
 * check(null == iter.next())
 * ```
 *
 * Various kinds of ASCII whitespace are considered
 * (see [char.isAsciiWhitespace]):
 *
 * ```kotlin
 * var iter = " Mary   had\ta little  \n\t lamb".splitAsciiWhitespace()
 * check("Mary" == iter.next())
 * check("had" == iter.next())
 * check("a" == iter.next())
 * check("little" == iter.next())
 * check("lamb" == iter.next())
 * check(null == iter.next())
 * ```
 *
 * If the string is empty or all ASCII whitespace, the iterator yields no string slices:
 * ```kotlin
 * check("".splitAsciiWhitespace().next() == null)
 * check("   ".splitAsciiWhitespace().next() == null)
 * ```
 */
public fun String.splitAsciiWhitespace(): SplitAsciiWhitespace {
    val inner = this.encodeToByteArray().map { it.toUByte() }.splitAsciiWhitespace().inner.map(UnsafeBytesToStr())
    return SplitAsciiWhitespace(inner = inner)
}

/**
 * Returns an iterator over the lines of a string, as string slices.
 *
 * Lines are split at line endings that are either newlines (`\n`) or
 * sequences of a carriage return followed by a line feed (`\r\n`).
 *
 * Line terminators are not included in the lines returned by the iterator.
 *
 * Note that any carriage return (`\r`) not immediately followed by a
 * line feed (`\n`) does not split a line. These carriage returns are
 * thereby included in the produced lines.
 *
 * The final line ending is optional. A string that ends with a final line
 * ending will return the same lines as an otherwise identical string
 * without a final line ending.
 *
 * An empty string returns an empty iterator.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val text = "foo\r\nbar\n\nbaz\r"
 * var lines = text.lines()
 * check("foo" == lines.next())
 * check("bar" == lines.next())
 * check("" == lines.next())
 * // Trailing carriage return is included in the last line
 * check("baz\r" == lines.next())
 * check(null == lines.next())
 * ```
 *
 * The final line does not require any ending:
 *
 * ```kotlin
 * val text = "foo\nbar\n\r\nbaz"
 * var lines = text.lines()
 * check("foo" == lines.next())
 * check("bar" == lines.next())
 * check("" == lines.next())
 * check("baz" == lines.next())
 * check(null == lines.next())
 * ```
 *
 * An empty string returns an empty iterator:
 *
 * ```kotlin
 * val text = ""
 * var lines = text.lines()
 * check(lines.next() == null)
 * ```
 */
public fun String.lines(): Lines = Lines(this.splitInclusive('\n').map(LinesMap()))

/**
 * Returns an iterator over the lines of a string.
 */
public fun String.linesAny(): LinesAny = LinesAny(this.lines())

/**
 * Returns an iterator of `UShort` over the string encoded
 * as native endian UTF-16 (without byte-order mark).
 *
 * # Examples
 *
 * ```kotlin
 * val text = "Zażółć gęślą jaźń"
 * val utf8Len = text.len()
 * val utf16Len = text.encodeUtf16().count()
 * check(utf16Len <= utf8Len)
 * ```
 */
public fun String.encodeUtf16(): EncodeUtf16 = EncodeUtf16(chars = this.chars(), extra = 0u.toUShort())

/**
 * Returns `true` if the given pattern matches a sub-slice of
 * this string slice.
 *
 * Returns `false` if it does not.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Examples
 *
 * ```kotlin
 * val bananas = "bananas"
 * check(bananas.contains("nana"))
 * check(!bananas.contains("apples"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.contains(pat: P): Boolean = pat.isContainedIn(this)

/**
 * Returns `true` if the given pattern matches a prefix of this
 * string slice.
 *
 * Returns `false` if it does not.
 *
 * The [pattern] can be a `String`, in which case this function will return true if
 * the `String` is a prefix of this string slice.
 *
 * The [pattern] can also be a [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 * These will only be checked against the first character of this string slice.
 * Look at the second example below regarding behavior for slices of [char]s.
 *
 * [char]: prim@char
 *
 * # Examples
 *
 * ```kotlin
 * val bananas = "bananas"
 * check(bananas.startsWith("bana"))
 * check(!bananas.startsWith("nana"))
 * ```
 *
 * ```kotlin
 * val bananas = "bananas"
 * // Note that both of these assert successfully.
 * check(bananas.startsWith(listOf('b', 'a', 'n', 'a')))
 * check(bananas.startsWith(listOf('a', 'b', 'c', 'd')))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.startsWith(pat: P): Boolean = pat.isPrefixOf(this)

/**
 * Returns `true` if the given pattern matches a suffix of this
 * string slice.
 *
 * Returns `false` if it does not.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Examples
 *
 * ```kotlin
 * val bananas = "bananas"
 * check(bananas.endsWith("anas"))
 * check(!bananas.endsWith("nana"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.endsWith(pat: P): Boolean = pat.isSuffixOf(this)

/**
 * Returns the byte index of the first character of this string slice that
 * matches the pattern.
 *
 * Returns [None] if the pattern doesn't match.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Examples
 *
 * Simple patterns:
 *
 * ```kotlin
 * val s = "Löwe 老虎 Léopard Gepardi"
 * check(s.indexOf('L').takeIf { it >= 0 } == 0)
 * check(s.indexOf('\u00e9').takeIf { it >= 0 } == 14)
 * check(s.indexOf("pard").takeIf { it >= 0 } == 17)
 * ```
 *
 * More complex patterns using point-free style and closures:
 *
 * ```kotlin
 * val s = "Löwe 老虎 Léopard"
 * check(s.indexOf(Char.isWhitespace).takeIf { it >= 0 } == 5)
 * check(s.indexOf(Char.isLowercase).takeIf { it >= 0 } == 1)
 * check(s.indexOf({ c: Char -> c.isWhitespace() || c.isLowercase() }).takeIf { it >= 0 } == 1)
 * check(s.indexOf({ c: Char -> (c < 'o') && (c > 'a') }).takeIf { it >= 0 } == 4)
 * ```
 *
 * Not finding the pattern:
 *
 * ```kotlin
 * val s = "Löwe 老虎 Léopard"
 * val x: List<Any?> = listOf('1', '2')
 * check(s.indexOf(x).takeIf { it >= 0 } == null)
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.find(pat: P): Int? = pat.intoSearcher(this).nextMatch().map({ (i, _) -> i })

/**
 * Returns the byte index for the first character of the last match of the pattern in
 * this string slice.
 *
 * Returns [None] if the pattern doesn't match.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Examples
 *
 * Simple patterns:
 *
 * ```kotlin
 * val s = "Löwe 老虎 Léopard Gepardi"
 * check(s.rfind('L') == 13)
 * check(s.rfind('\u00e9') == 14)
 * check(s.rfind("pard") == 24)
 * ```
 *
 * More complex patterns with closures:
 *
 * ```kotlin
 * val s = "Löwe 老虎 Léopard"
 * check(s.rfind(Char.isWhitespace) == 12)
 * check(s.rfind(Char.isLowercase) == 20)
 * ```
 *
 * Not finding the pattern:
 *
 * ```kotlin
 * val s = "Löwe 老虎 Léopard"
 * val x: List<Any?> = listOf('1', '2')
 * check(s.rfind(x) == null)
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.rfind(pat: P): Int? = pat.intoSearcher(this).nextMatchBack().map({ (i, _) -> i })

/**
 * Returns an iterator over substrings of this string slice, separated by
 * characters matched by a pattern.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * If there are no matches the full string slice is returned as the only
 * item in the iterator.
 *
 * [char]: prim@char
 *
 * # Iterator behavior
 *
 * The returned iterator will be a [DoubleEndedIterator] if the pattern
 * allows a reverse search and forward/reverse search yields the same
 * elements. This is true for, e.g., [char], but not for `String`.
 *
 * If the pattern allows a reverse search but its results might differ
 * from a forward search, the [rsplit] method can be used.
 *
 * [rsplit]: str.rsplit
 *
 * # Examples
 *
 * Simple patterns:
 *
 * ```kotlin
 * val v: List<String> = "Mary had a little lamb".split(' ').toMutableList()
 * check(v == listOf("Mary", "had", "a", "little", "lamb"))
 * val v1: List<String> = "".split('X').toMutableList()
 * check(v1 == listOf(""))
 * val v2: List<String> = "lionXXtigerXleopard".split('X').toMutableList()
 * check(v2 == listOf("lion", "", "tiger", "leopard"))
 * val v3: List<String> = "lion.tiger.leopard".split("::").toMutableList()
 * check(v3 == listOf("lion", "tiger", "leopard"))
 * val v4: List<String> = "AABBCC".split("DD").toMutableList()
 * check(v4 == listOf("AABBCC"))
 * val v5: List<String> = "abc1def2ghi".split(Char.isNumeric).toMutableList()
 * check(v5 == listOf("abc", "def", "ghi"))
 * val v6: List<String> = "lionXtigerXleopard".split(Char.isUppercase).toMutableList()
 * check(v6 == listOf("lion", "tiger", "leopard"))
 * ```
 *
 * If the pattern is a slice of chars, split on each occurrence of any of the characters:
 *
 * ```kotlin
 * val v: List<String> = "2020-11-03 23:59".split(listOf('-', ' ', ':', '@')).toMutableList()
 * check(v == listOf("2020", "11", "03", "23", "59"))
 * ```
 *
 * A more complex pattern, using a closure:
 *
 * ```kotlin
 * val v: List<String> = "abc1defXghi".split({ c -> c == '1' || c == 'X' }).toMutableList()
 * check(v == listOf("abc", "def", "ghi"))
 * ```
 *
 * If a string contains multiple contiguous separators, you will end up
 * with empty strings in the output:
 *
 * ```kotlin
 * val x = "||||a||b|c".toString()
 * val d: Vec = x.split('|').collect()
 * check(d == listOf("", "", "", "", "a", "", "b", "c"))
 * ```
 *
 * Contiguous separators are separated by the empty string.
 *
 * ```kotlin
 * val x = "(///)".toString()
 * val d: Vec = x.split('/').collect()
 * check(d == listOf("(", "", "", ")"))
 * ```
 *
 * Separators at the start or end of a string are neighbored
 * by empty strings.
 *
 * ```kotlin
 * val d: Vec = "010".split("0").collect()
 * check(d == listOf("", "1", ""))
 * ```
 *
 * When the empty string is used as a separator, it separates
 * every character in the string, along with the beginning
 * and end of the string.
 *
 * ```kotlin
 * val f: Vec = "rust".split("").collect()
 * check(f == listOf("", "r", "u", "s", "t", ""))
 * ```
 *
 * Contiguous separators can lead to possibly surprising behavior
 * when whitespace is used as the separator. This code is correct:
 *
 * ```kotlin
 * val x = "    a  b c".toString()
 * val d: Vec = x.split(' ').collect()
 * check(d == listOf("", "", "", "", "a", "", "b", "c"))
 * ```
 *
 * It does _not_ give you:
 *
 * ```,ignore
 * assertEq(d, &["a", "b", "c"]);
 * ```
 *
 * Use [splitWhitespace] for this behavior.
 *
 * [splitWhitespace]: str.splitWhitespace
 */
public fun <Searcher, P : Pattern<Searcher>> String.split(pat: P): Split<P> = Split(SplitInternal(start = 0, end = this.length, matcher = pat.intoSearcher(this), allowTrailingEmpty = true, finished = false))

/**
 * Returns an iterator over substrings of this string slice, separated by
 * characters matched by a pattern.
 *
 * Differs from the iterator produced by `split` in that `splitInclusive`
 * leaves the matched part as the terminator of the substring.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Examples
 *
 * ```kotlin
 * val v: List<String> = "Mary had a little lamb\nlittle lamb\nlittle lamb.".splitInclusive('\n').toMutableList()
 * check(v == listOf("Mary had a little lamb\n", "little lamb\n", "little lamb."))
 * ```
 *
 * If the last element of the string is matched,
 * that element will be considered the terminator of the preceding substring.
 * That substring will be the last item returned by the iterator.
 *
 * ```kotlin
 * val v: List<String> = "Mary had a little lamb\nlittle lamb\nlittle lamb.\n".splitInclusive('\n').toMutableList()
 * check(v == listOf("Mary had a little lamb\n", "little lamb\n", "little lamb.\n"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.splitInclusive(pat: P): SplitInclusive<P> = SplitInclusive(SplitInternal(start = 0, end = this.length, matcher = pat.intoSearcher(this), allowTrailingEmpty = false, finished = false))

/**
 * Returns an iterator over substrings of the given string slice, separated
 * by characters matched by a pattern and yielded in reverse order.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Iterator behavior
 *
 * The returned iterator requires that the pattern supports a reverse
 * search, and it will be a [DoubleEndedIterator] if a forward/reverse
 * search yields the same elements.
 *
 * For iterating from the front, the [split] method can be used.
 *
 * [split]: str.split
 *
 * # Examples
 *
 * Simple patterns:
 *
 * ```kotlin
 * val v: List<String> = "Mary had a little lamb".rsplit(' ').toMutableList()
 * check(v == listOf("lamb", "little", "a", "had", "Mary"))
 * val v1: List<String> = "".rsplit('X').toMutableList()
 * check(v1 == listOf(""))
 * val v2: List<String> = "lionXXtigerXleopard".rsplit('X').toMutableList()
 * check(v2 == listOf("leopard", "tiger", "", "lion"))
 * val v3: List<String> = "lion.tiger.leopard".rsplit("::").toMutableList()
 * check(v3 == listOf("leopard", "tiger", "lion"))
 * ```
 *
 * A more complex pattern, using a closure:
 *
 * ```kotlin
 * val v: List<String> = "abc1defXghi".rsplit({ c -> c == '1' || c == 'X' }).toMutableList()
 * check(v == listOf("ghi", "def", "abc"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.rsplit(pat: P): RSplit<P> = RSplit(this.split(pat).value)

/**
 * Returns an iterator over substrings of the given string slice, separated
 * by characters matched by a pattern.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * Equivalent to [split], except that the trailing substring
 * is skipped if empty.
 *
 * [split]: str.split
 *
 * This method can be used for string data that is _terminated_,
 * rather than _separated_ by a pattern.
 *
 * # Iterator behavior
 *
 * The returned iterator will be a [DoubleEndedIterator] if the pattern
 * allows a reverse search and forward/reverse search yields the same
 * elements. This is true for, e.g., [char], but not for `String`.
 *
 * If the pattern allows a reverse search but its results might differ
 * from a forward search, the [rsplitTerminator] method can be used.
 *
 * [rsplitTerminator]: str.rsplitTerminator
 *
 * # Examples
 *
 * ```kotlin
 * val v: List<String> = "A.B.".splitTerminator('.').toMutableList()
 * check(v == listOf("A", "B"))
 * val v1: List<String> = "A..B..".splitTerminator(".").toMutableList()
 * check(v1 == listOf("A", "", "B", ""))
 * val v2: List<String> = "A.B:C.D".splitTerminator(listOf('.', ':')).toMutableList()
 * check(v2 == listOf("A", "B", "C", "D"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.splitTerminator(pat: P): SplitTerminator<P> = SplitTerminator(this.split(pat).value.copy(allowTrailingEmpty = false))

/**
 * Returns an iterator over substrings of `self`, separated by characters
 * matched by a pattern and yielded in reverse order.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * Equivalent to [split], except that the trailing substring is
 * skipped if empty.
 *
 * [split]: str.split
 *
 * This method can be used for string data that is _terminated_,
 * rather than _separated_ by a pattern.
 *
 * # Iterator behavior
 *
 * The returned iterator requires that the pattern supports a
 * reverse search, and it will be double ended if a forward/reverse
 * search yields the same elements.
 *
 * For iterating from the front, the [splitTerminator] method can be
 * used.
 *
 * [splitTerminator]: str.splitTerminator
 *
 * # Examples
 *
 * ```kotlin
 * val v: List<String> = "A.B.".rsplitTerminator('.').toMutableList()
 * check(v == listOf("B", "A"))
 * val v1: List<String> = "A..B..".rsplitTerminator(".").toMutableList()
 * check(v1 == listOf("", "B", "", "A"))
 * val v2: List<String> = "A.B:C.D".rsplitTerminator(listOf('.', ':')).toMutableList()
 * check(v2 == listOf("D", "C", "B", "A"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.rsplitTerminator(pat: P): RSplitTerminator<P> = RSplitTerminator(this.splitTerminator(pat).value)

/**
 * Returns an iterator over substrings of the given string slice, separated
 * by a pattern, restricted to returning at most `n` items.
 *
 * If `n` substrings are returned, the last substring (the `n`th substring)
 * will contain the remainder of the string.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Iterator behavior
 *
 * The returned iterator will not be double ended, because it is
 * not efficient to support.
 *
 * If the pattern allows a reverse search, the [rsplitn] method can be
 * used.
 *
 * [rsplitn]: str.rsplitn
 *
 * # Examples
 *
 * Simple patterns:
 *
 * ```kotlin
 * val v: List<String> = "Mary had a little lambda".splitn(3, ' ').toMutableList()
 * check(v == listOf("Mary", "had", "a little lambda"))
 * val v1: List<String> = "lionXXtigerXleopard".splitn(3, "X").toMutableList()
 * check(v1 == listOf("lion", "", "tigerXleopard"))
 * val v2: List<String> = "abcXdef".splitn(1, 'X').toMutableList()
 * check(v2 == listOf("abcXdef"))
 * val v3: List<String> = "".splitn(1, 'X').toMutableList()
 * check(v3 == listOf(""))
 * ```
 *
 * A more complex pattern, using a closure:
 *
 * ```kotlin
 * val v: List<String> = "abc1defXghi".splitn(2, { c -> c == '1' || c == 'X' }).toMutableList()
 * check(v == listOf("abc", "defXghi"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.splitn(n: Int, pat: P): SplitN<P> = SplitN(SplitNInternal(iter = this.split(pat).value, count = n))

/**
 * Returns an iterator over substrings of this string slice, separated by a
 * pattern, starting from the end of the string, restricted to returning at
 * most `n` items.
 *
 * If `n` substrings are returned, the last substring (the `n`th substring)
 * will contain the remainder of the string.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Iterator behavior
 *
 * The returned iterator will not be double ended, because it is not
 * efficient to support.
 *
 * For splitting from the front, the [splitn] method can be used.
 *
 * [splitn]: str.splitn
 *
 * # Examples
 *
 * Simple patterns:
 *
 * ```kotlin
 * val v: List<String> = "Mary had a little lamb".rsplitn(3, ' ').toMutableList()
 * check(v == listOf("lamb", "little", "Mary had a"))
 * val v1: List<String> = "lionXXtigerXleopard".rsplitn(3, 'X').toMutableList()
 * check(v1 == listOf("leopard", "tiger", "lionX"))
 * val v2: List<String> = "lion.tiger.leopard".rsplitn(2, "::").toMutableList()
 * check(v2 == listOf("leopard", "lion.tiger"))
 * ```
 *
 * A more complex pattern, using a closure:
 *
 * ```kotlin
 * val v: List<String> = "abc1defXghi".rsplitn(2, { c -> c == '1' || c == 'X' }).toMutableList()
 * check(v == listOf("ghi", "abc1def"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.rsplitn(n: Int, pat: P): RSplitN<P> = RSplitN(this.splitn(n, pat).value)

/**
 * Splits the string on the first occurrence of the specified delimiter and
 * returns prefix before delimiter and suffix after delimiter.
 *
 * # Examples
 *
 * ```kotlin
 * check("cfg".let { s -> val idx = s.indexOf('='); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } == null)
 * check(("cfg=").let { s -> val idx = s.indexOf('='); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } == Pair("cfg", ""))
 * check(("cfg=foo").let { s -> val idx = s.indexOf('='); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } == Pair("cfg", "foo"))
 * check(("cfg=foo=bar").let { s -> val idx = s.indexOf('='); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } == Pair("cfg", "foo=bar"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.splitOnce(delimiter: P): Pair<String, String>? {
    val (start, end) = (delimiter.intoSearcher(this).nextMatch() ?: return null)
    // SAFETY: `Searcher` is known to return valid indices.
    return Pair(this.getUnchecked(0 until start), this.getUnchecked(end until Int.MAX_VALUE))
}

/**
 * Splits the string on the last occurrence of the specified delimiter and
 * returns prefix before delimiter and suffix after delimiter.
 *
 * # Examples
 *
 * ```kotlin
 * check("cfg".let { s -> val idx = s.lastIndexOf('='); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } == null)
 * check(("cfg=").let { s -> val idx = s.lastIndexOf('='); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } == Pair("cfg", ""))
 * check(("cfg=foo").let { s -> val idx = s.lastIndexOf('='); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } == Pair("cfg", "foo"))
 * check(("cfg=foo=bar").let { s -> val idx = s.lastIndexOf('='); if (idx < 0) null else Pair(s.substring(0, idx), s.substring(idx + 1)) } == Pair("cfg=foo", "bar"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.rsplitOnce(delimiter: P): Pair<String, String>? {
    val (start, end) = (delimiter.intoSearcher(this).nextMatchBack() ?: return null)
    // SAFETY: `Searcher` is known to return valid indices.
    return Pair(this.getUnchecked(0 until start), this.getUnchecked(end until Int.MAX_VALUE))
}

/**
 * Returns an iterator over the disjoint matches of a pattern within the
 * given string slice.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Iterator behavior
 *
 * The returned iterator will be a [DoubleEndedIterator] if the pattern
 * allows a reverse search and forward/reverse search yields the same
 * elements. This is true for, e.g., [char], but not for `String`.
 *
 * If the pattern allows a reverse search but its results might differ
 * from a forward search, the [rmatches] method can be used.
 *
 * [rmatches]: str.rmatches
 *
 * # Examples
 *
 * ```kotlin
 * val v: List<String> = "abcXXXabcYYYabc".matches("abc").toMutableList()
 * check(v == listOf("abc", "abc", "abc"))
 * val v1: List<String> = "1abc2abc3".matches(Char.isNumeric).toMutableList()
 * check(v1 == listOf("1", "2", "3"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.matches(pat: P): Matches<P> = Matches(MatchesInternal(pat.intoSearcher(this)))

/**
 * Returns an iterator over the disjoint matches of a pattern within this
 * string slice, yielded in reverse order.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Iterator behavior
 *
 * The returned iterator requires that the pattern supports a reverse
 * search, and it will be a [DoubleEndedIterator] if a forward/reverse
 * search yields the same elements.
 *
 * For iterating from the front, the [matches] method can be used.
 *
 * [matches]: str.matches
 *
 * # Examples
 *
 * ```kotlin
 * val v: List<String> = "abcXXXabcYYYabc".rmatches("abc").toMutableList()
 * check(v == listOf("abc", "abc", "abc"))
 * val v1: List<String> = "1abc2abc3".rmatches(Char.isNumeric).toMutableList()
 * check(v1 == listOf("3", "2", "1"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.rmatches(pat: P): RMatches<P> = RMatches(this.matches(pat).value)

/**
 * Returns an iterator over the disjoint matches of a pattern within this string
 * slice as well as the index that the match starts at.
 *
 * For matches of `pat` within `self` that overlap, only the indices
 * corresponding to the first match are returned.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Iterator behavior
 *
 * The returned iterator will be a [DoubleEndedIterator] if the pattern
 * allows a reverse search and forward/reverse search yields the same
 * elements. This is true for, e.g., [char], but not for `String`.
 *
 * If the pattern allows a reverse search but its results might differ
 * from a forward search, the [rmatchIndices] method can be used.
 *
 * [rmatchIndices]: str.rmatchIndices
 *
 * # Examples
 *
 * ```kotlin
 * val v: Vec = "abcXXXabcYYYabc".matchIndices("abc").collect()
 * check(v == listOf(Pair(0, "abc"), Pair(6, "abc"), Pair(12, "abc")))
 * val v1: Vec = "1abcabc2".matchIndices("abc").collect()
 * check(v1 == listOf(Pair(1, "abc"), Pair(4, "abc")))
 * val v2: Vec = "ababa".matchIndices("aba").collect()
 * check(v2 == listOf(Pair(0, "aba")))
 * // only the first `aba`
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.matchIndices(pat: P): MatchIndices<P> = MatchIndices(MatchIndicesInternal(pat.intoSearcher(this)))

/**
 * Returns an iterator over the disjoint matches of a pattern within `self`,
 * yielded in reverse order along with the index of the match.
 *
 * For matches of `pat` within `self` that overlap, only the indices
 * corresponding to the last match are returned.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Iterator behavior
 *
 * The returned iterator requires that the pattern supports a reverse
 * search, and it will be a [DoubleEndedIterator] if a forward/reverse
 * search yields the same elements.
 *
 * For iterating from the front, the [matchIndices] method can be used.
 *
 * [matchIndices]: str.matchIndices
 *
 * # Examples
 *
 * ```kotlin
 * val v: Vec = "abcXXXabcYYYabc".rmatchIndices("abc").collect()
 * check(v == listOf(Pair(12, "abc"), Pair(6, "abc"), Pair(0, "abc")))
 * val v1: Vec = "1abcabc2".rmatchIndices("abc").collect()
 * check(v1 == listOf(Pair(4, "abc"), Pair(1, "abc")))
 * val v2: Vec = "ababa".rmatchIndices("aba").collect()
 * check(v2 == listOf(Pair(2, "aba")))
 * // only the last `aba`
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.rmatchIndices(pat: P): RMatchIndices<P> = RMatchIndices(this.matchIndices(pat).value)

/**
 * Returns a string slice with leading and trailing whitespace removed.
 *
 * `Whitespace` is defined according to the terms of the Unicode Derived
 * Core Property `WhiteSpace`, which includes newlines.
 *
 * # Examples
 *
 * ```kotlin
 * val s = "\n Hello\tworld\t\n"
 * check("Hello\tworld" == s.trim())
 * ```
 */
public fun String.trim(): String = this.trimMatches(Char::isWhitespace)

/**
 * Returns a string slice with leading whitespace removed.
 *
 * `Whitespace` is defined according to the terms of the Unicode Derived
 * Core Property `WhiteSpace`, which includes newlines.
 *
 * # Text directionality
 *
 * A string is a sequence of bytes. `start` in this context means the first
 * position of that byte string; for a left-to-right language like English or
 * Russian, this will be left side, and for right-to-left languages like
 * Arabic or Hebrew, this will be the right side.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val s = "\n Hello\tworld\t\n"
 * check("Hello\tworld\t\n" == s.trimStart())
 * ```
 *
 * Directionality:
 *
 * ```kotlin
 * val s = "  English  "
 * check('E' == s.trimStart().chars().next())
 * val s1 = "  עברית  "
 * check('\u05e2' == s1.trimStart().chars().next())
 * ```
 */
public fun String.trimStart(): String = this.trimStartMatches(Char::isWhitespace)

/**
 * Returns a string slice with trailing whitespace removed.
 *
 * `Whitespace` is defined according to the terms of the Unicode Derived
 * Core Property `WhiteSpace`, which includes newlines.
 *
 * # Text directionality
 *
 * A string is a sequence of bytes. `end` in this context means the last
 * position of that byte string; for a left-to-right language like English or
 * Russian, this will be right side, and for right-to-left languages like
 * Arabic or Hebrew, this will be the left side.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val s = "\n Hello\tworld\t\n"
 * check("\n Hello\tworld" == s.trimEnd())
 * ```
 *
 * Directionality:
 *
 * ```kotlin
 * val s = "  English  "
 * check('h' == s.trimEnd().chars().rev().next())
 * val s1 = "  עברית  "
 * check('\u05ea' == s1.trimEnd().chars().rev().next())
 * ```
 */
public fun String.trimEnd(): String = this.trimEndMatches(Char::isWhitespace)

/**
 * Returns a string slice with leading whitespace removed.
 *
 * `Whitespace` is defined according to the terms of the Unicode Derived
 * Core Property `WhiteSpace`.
 *
 * # Text directionality
 *
 * A string is a sequence of bytes. `Left` in this context means the first
 * position of that byte string; for a language like Arabic or Hebrew
 * which are right to left' rather than left to right', this will be
 * the _right_ side, not the left.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val s = " Hello\tworld\t"
 * check("Hello\tworld\t" == s.trimLeft())
 * ```
 *
 * Directionality:
 *
 * ```kotlin
 * val s = "  English"
 * check('E' == s.trimLeft().chars().next())
 * val s1 = "  עברית"
 * check('\u05e2' == s1.trimLeft().chars().next())
 * ```
 */
public fun String.trimLeft(): String = this.trimStart()

/**
 * Returns a string slice with trailing whitespace removed.
 *
 * `Whitespace` is defined according to the terms of the Unicode Derived
 * Core Property `WhiteSpace`.
 *
 * # Text directionality
 *
 * A string is a sequence of bytes. `Right` in this context means the last
 * position of that byte string; for a language like Arabic or Hebrew
 * which are right to left' rather than left to right', this will be
 * the _left_ side, not the right.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val s = " Hello\tworld\t"
 * check(" Hello\tworld" == s.trimRight())
 * ```
 *
 * Directionality:
 *
 * ```kotlin
 * val s = "English  "
 * check('h' == s.trimRight().chars().rev().next())
 * val s1 = "עברית  "
 * check('\u05ea' == s1.trimRight().chars().rev().next())
 * ```
 */
public fun String.trimRight(): String = this.trimEnd()

/**
 * Returns a string slice with all prefixes and suffixes that match a
 * pattern repeatedly removed.
 *
 * The [pattern] can be a [char], a slice of [char]s, or a function
 * or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Examples
 *
 * Simple patterns:
 *
 * ```kotlin
 * check("11foo1bar11".trimMatches('1') == "foo1bar")
 * check("123foo1bar123".trimMatches(Char.isNumeric) == "foo1bar")
 * val x: List<Any?> = listOf('1', '2')
 * check("12foo1bar12".trimMatches(x) == "foo1bar")
 * ```
 *
 * A more complex pattern, using a closure:
 *
 * ```kotlin
 * check("1foo1barXX".trimMatches({ c -> c == '1' || c == 'X' }) == "foo1bar")
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.trimMatches(pat: P): String {
    var i = 0
    var j = 0
    val matcher = pat.intoSearcher(this)
    when (val matchVal = matcher.nextReject()) {
        null -> {}
        else -> {
            val a = matchVal.first
            val b = matchVal.second
            i = a
            j = b
            // Remember earliest known match, correct it below if
            // last match is different
        }
    }
    when (val matchVal1 = matcher.nextRejectBack()) {
        null -> {}
        else -> {
            val b1 = matchVal1.second
            j = b1
        }
    }
    // SAFETY: `Searcher` is known to return valid indices.
    return this.getUnchecked(i until j)
}

/**
 * Returns a string slice with all prefixes that match a pattern
 * repeatedly removed.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Text directionality
 *
 * A string is a sequence of bytes. `start` in this context means the first
 * position of that byte string; for a left-to-right language like English or
 * Russian, this will be left side, and for right-to-left languages like
 * Arabic or Hebrew, this will be the right side.
 *
 * # Examples
 *
 * ```kotlin
 * check("11foo1bar11".trimStartMatches('1') == "foo1bar11")
 * check("123foo1bar123".trimStartMatches(Char.isNumeric) == "foo1bar123")
 * val x: List<Any?> = listOf('1', '2')
 * check("12foo1bar12".trimStartMatches(x) == "foo1bar12")
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.trimStartMatches(pat: P): String {
    var i = this.length
    val matcher = pat.intoSearcher(this)
    when (val matchVal = matcher.nextReject()) {
        null -> {}
        else -> {
            val a = matchVal.first
            i = a
        }
    }
    // SAFETY: `Searcher` is known to return valid indices.
    return this.getUnchecked(i until this.length)
}

/**
 * Returns a string slice with the prefix removed.
 *
 * If the string starts with the pattern `prefix`, returns the substring after the prefix,
 * wrapped in `Some`. Unlike [trimStartMatches], this method removes the prefix exactly once.
 *
 * If the string does not start with `prefix`, returns `None`.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 * [trimStartMatches]: Self.trimStartMatches
 *
 * # Examples
 *
 * ```kotlin
 * check(("foo:bar").takeIf { it.startsWith("foo:") }?.removePrefix("foo:") == "bar")
 * check(("foo:bar").takeIf { it.startsWith("bar") }?.removePrefix("bar") == null)
 * check("foofoo".takeIf { it.startsWith("foo") }?.removePrefix("foo") == "foo")
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.stripPrefix(prefix: P): String? = prefix.stripPrefixOf(this)

/**
 * Returns a string slice with the suffix removed.
 *
 * If the string ends with the pattern `suffix`, returns the substring before the suffix,
 * wrapped in `Some`.  Unlike [trimEndMatches], this method removes the suffix exactly once.
 *
 * If the string does not end with `suffix`, returns `None`.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 * [trimEndMatches]: Self.trimEndMatches
 *
 * # Examples
 *
 * ```kotlin
 * check(("bar:foo").takeIf { it.endsWith(":foo") }?.removeSuffix(":foo") == "bar")
 * check(("bar:foo").takeIf { it.endsWith("bar") }?.removeSuffix("bar") == null)
 * check("foofoo".takeIf { it.endsWith("foo") }?.removeSuffix("foo") == "foo")
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.stripSuffix(suffix: P): String? = suffix.stripSuffixOf(this)

/**
 * Returns a string slice with the prefix and suffix removed.
 *
 * If the string starts with the pattern `prefix` and ends with
 * the pattern `suffix`, and the prefix and suffix don't overlap, returns
 * the substring after the prefix and before the suffix, wrapped in `Some`.
 * Unlike [trimStartMatches] and [trimEndMatches], this method removes both the prefix
 * and suffix exactly once.
 *
 * If the string does not start with `prefix`, does not end with `suffix`,
 * or the prefix and suffix overlap in the string, returns `None`.
 *
 * Each [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 * [trimStartMatches]: Self.trimStartMatches
 * [trimEndMatches]: Self.trimEndMatches
 *
 * # Examples
 *
 * ```kotlin
 * check("bar:hello:foo".stripCircumfix("bar:", ":foo") == "hello")
 * check("bar:foo".stripCircumfix("foo", "foo") == null)
 * check("foo:bar;".stripCircumfix("foo:", ';') == "bar")
 * check("foo:bar:baz".stripCircumfix("foo:bar:", ":bar:baz") == null)
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>, S : Pattern<Searcher>> String.stripCircumfix(prefix: P, suffix: S): String? {
    return ((this.takeIf { it.startsWith(prefix) }?.removePrefix(prefix) ?: return null)).takeIf { it.endsWith(suffix) }?.removeSuffix(suffix)
}

/**
 * Returns a string slice with the optional prefix removed.
 *
 * If the string starts with the pattern `prefix`, returns the substring after the prefix.
 * Unlike [stripPrefix], this method always returns `String` for easy method chaining,
 * instead of returning [Option.str].
 *
 * If the string does not start with `prefix`, returns the original string unchanged.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 * [stripPrefix]: Self.stripPrefix
 *
 * # Examples
 *
 * ```kotlin
 * // Prefix present - removes it
 * check("foo:bar".trimPrefix("foo:") == "bar")
 * check("foofoo".trimPrefix("foo") == "foo")
 * // Prefix absent - returns original string
 * check("foo:bar".trimPrefix("bar") == "foo:bar")
 * // Method chaining example
 * check("<https://example.com/>".trimPrefix('<').trimSuffix('>') == "https://example.com/")
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.trimPrefix(prefix: P): String = prefix.stripPrefixOf(this).unwrapOr(this)

/**
 * Returns a string slice with the optional suffix removed.
 *
 * If the string ends with the pattern `suffix`, returns the substring before the suffix.
 * Unlike [stripSuffix], this method always returns `String` for easy method chaining,
 * instead of returning [Option.str].
 *
 * If the string does not end with `suffix`, returns the original string unchanged.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 * [stripSuffix]: Self.stripSuffix
 *
 * # Examples
 *
 * ```kotlin
 * // Suffix present - removes it
 * check("bar:foo".trimSuffix(":foo") == "bar")
 * check("foofoo".trimSuffix("foo") == "foo")
 * // Suffix absent - returns original string
 * check("bar:foo".trimSuffix("bar") == "bar:foo")
 * // Method chaining example
 * check("<https://example.com/>".trimPrefix('<').trimSuffix('>') == "https://example.com/")
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.trimSuffix(suffix: P): String = suffix.stripSuffixOf(this).unwrapOr(this)

/**
 * Returns a string slice with all suffixes that match a pattern
 * repeatedly removed.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Text directionality
 *
 * A string is a sequence of bytes. `end` in this context means the last
 * position of that byte string; for a left-to-right language like English or
 * Russian, this will be right side, and for right-to-left languages like
 * Arabic or Hebrew, this will be the left side.
 *
 * # Examples
 *
 * Simple patterns:
 *
 * ```kotlin
 * check("11foo1bar11".trimEndMatches('1') == "11foo1bar")
 * check("123foo1bar123".trimEndMatches(Char.isNumeric) == "123foo1bar")
 * val x: List<Any?> = listOf('1', '2')
 * check("12foo1bar12".trimEndMatches(x) == "12foo1bar")
 * ```
 *
 * A more complex pattern, using a closure:
 *
 * ```kotlin
 * check("1fooX".trimEndMatches({ c -> c == '1' || c == 'X' }) == "1foo")
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.trimEndMatches(pat: P): String {
    var j = 0
    val matcher = pat.intoSearcher(this)
    when (val matchVal = matcher.nextRejectBack()) {
        null -> {}
        else -> {
            val b = matchVal.second
            j = b
        }
    }
    // SAFETY: `Searcher` is known to return valid indices.
    return this.getUnchecked(0 until j)
}

/**
 * Returns a string slice with all prefixes that match a pattern
 * repeatedly removed.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Text directionality
 *
 * A string is a sequence of bytes. `Left` in this context means the first
 * position of that byte string; for a language like Arabic or Hebrew
 * which are right to left' rather than left to right', this will be
 * the _right_ side, not the left.
 *
 * # Examples
 *
 * ```kotlin
 * check("11foo1bar11".trimLeftMatches('1') == "foo1bar11")
 * check("123foo1bar123".trimLeftMatches(Char.isNumeric) == "foo1bar123")
 * val x: List<Any?> = listOf('1', '2')
 * check("12foo1bar12".trimLeftMatches(x) == "foo1bar12")
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.trimLeftMatches(pat: P): String = this.trimStartMatches(pat)

/**
 * Returns a string slice with all suffixes that match a pattern
 * repeatedly removed.
 *
 * The [pattern] can be a `String`, [char], a slice of [char]s, or a
 * function or closure that determines if a character matches.
 *
 * [char]: prim@char
 *
 * # Text directionality
 *
 * A string is a sequence of bytes. `Right` in this context means the last
 * position of that byte string; for a language like Arabic or Hebrew
 * which are right to left' rather than left to right', this will be
 * the _left_ side, not the right.
 *
 * # Examples
 *
 * Simple patterns:
 *
 * ```kotlin
 * check("11foo1bar11".trimRightMatches('1') == "11foo1bar")
 * check("123foo1bar123".trimRightMatches(Char.isNumeric) == "123foo1bar")
 * val x: List<Any?> = listOf('1', '2')
 * check("12foo1bar12".trimRightMatches(x) == "12foo1bar")
 * ```
 *
 * A more complex pattern, using a closure:
 *
 * ```kotlin
 * check("1fooX".trimRightMatches({ c -> c == '1' || c == 'X' }) == "1foo")
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.trimRightMatches(pat: P): String = this.trimEndMatches(pat)

/**
 * Parses this string slice into another type.
 *
 * Because `parse` is so general, it can cause problems with type
 * inference. As such, `parse` is one of the few times you'll see
 * the syntax affectionately known as the `turbofish`: `<>`. This
 * helps the inference algorithm understand specifically which type
 * you're trying to parse into.
 *
 * `parse` can parse into any type that implements the [FromStr] trait.
 *
 * # Errors
 *
 * Will return [Err] if it's not possible to parse this string slice into
 * the desired type.
 *
 * [Err]: FromStr.Err
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val four: UInt = "4".parse().unwrap()
 * check(4u == four)
 * ```
 *
 * Using the `turbofish` instead of annotating `four`:
 *
 * ```kotlin
 * val four = "4".parse<UInt>()
 * check(Result.success(4) == four)
 * ```
 *
 * Failing to parse:
 *
 * ```kotlin
 * val nope = "j".parse<UInt>()
 * check(nope.isErr())
 * ```
 */
public fun <Err, F : FromStr<F, Err>> String.parse(): Result<F, Err> = FromStr.fromStr(this)

/**
 * Checks if all characters in this string are within the ASCII range.
 *
 * An empty string returns `true`.
 *
 * # Examples
 *
 * ```kotlin
 * import ascii
 *
 * val ascii = "hello!\n"
 * val nonAscii = "Grüße, Jürgen ❤"
 * check(ascii.isAscii())
 * check(!nonAscii.isAscii())
 * ```
 */
public fun String.isAscii(): Boolean {
    // We can treat each byte as character here: all multibyte characters
    // start with a byte that is not in the ASCII range, so we will stop
    // there already.
    return this.encodeToByteArray().map { it.toUByte() }.isAscii()
}

/**
 * If this string slice [`isAscii`](Self.isAscii), returns it as a slice
 * of [ASCII characters](`ascii.Char`), otherwise returns `None`.
 */
public fun String.asAscii(): List<io.github.kotlinmania.rustlang.core.ascii.Char>? {
    // Like in `isAscii`, we can work on the bytes directly.
    return this.encodeToByteArray().map { it.toUByte() }.asAscii()
}

/**
 * Converts this string slice into a slice of [ASCII characters](ascii.Char),
 * without checking whether they are valid.
 *
 * # Safety
 *
 * Every character in this string must be ASCII, or else this is UB.
 */
public fun String.asAsciiUnchecked(): List<io.github.kotlinmania.rustlang.core.ascii.Char> {
    Unit
    // SAFETY: the caller promised that every byte of this string slice
    // is ASCII.
    return this.encodeToByteArray().map { it.toUByte() }.asAsciiUnchecked()
}

/**
 * Checks that two strings are an ASCII case-insensitive match.
 *
 * Same as `toAsciiLowercase(a) == toAsciiLowercase(b)`,
 * but without allocating and copying temporaries.
 *
 * For Unicode-aware case-insensitive matching, consider
 * [str.eqIgnoreCaseUnnormalized].
 *
 * # Examples
 *
 * ```kotlin
 * check("Ferris".eqIgnoreAsciiCase("FERRIS"))
 * check("Ferrös".eqIgnoreAsciiCase("FERRöS"))
 * check(!"Ferrös".eqIgnoreAsciiCase("FERRÖS"))
 * ```
 */
public fun String.eqIgnoreAsciiCase(other: String): Boolean = this.encodeToByteArray().map { it.toUByte() }.eqIgnoreAsciiCase(other.encodeToByteArray().map { it.toUByte() })

/**
 * Checks that two strings are a caseless match, according to
 * [Definition 144] in Chapter 3 of the Unicode Standard.
 *
 * [Definition 144]: https://www.unicode.org/versions/latest/core-spec/chapter-3/#G53513
 *
 * Same as `a.toCasefoldUnnormalized() == b.toCasefoldUnnormalized()`,
 * but without allocating. See that method's documentation,
 * as well as [char.toCasefoldUnnormalized],
 * for more information about case folding.
 *
 * No [normalization] (e.g. NFC) is performed, so visually and semantically identical strings
 * might still compare unequal. For example, `"Å"` (U+00C5 LATIN CAPITAL LETTER A WITH RING ABOVE)
 * is considered distinct from `"Å"` (A followed by U+030A COMBINING RING ABOVE),
 * even though Unicode considers them canonically equivalent.
 *
 * In addition, this method is independent of language/locale,
 * so the special behavior of I/ı/İ/i in Turkish and Azeri is not handled.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(casefold)
 * public fun doctestEntry() {
 *     check("Ferris".eqIgnoreCaseUnnormalized("FERRIS"))
 *     check("Ferrös".eqIgnoreCaseUnnormalized("FERRÖS"))
 *     check("ẞ".eqIgnoreCaseUnnormalized("ss"))
 * }
 * ```
 *
 * No NFC [normalization] is performed:
 *
 * ```kotlin
 * @feature(casefold)
 * public fun doctestEntry() {
 *     // These two strings are visually and semantically identical...
 *     val comp = "Å"
 *     val decomp = "Å"
 *     // ... but not codepoint-for-codepoint equal.
 *     check(comp == "Å")
 *     check(decomp == "Å")
 *     // Their case-foldings are likewise unequal:
 *     check(!comp.eqIgnoreCaseUnnormalized(decomp))
 * }
 * ```
 *
 * [normalization]: https://www.unicode.org/faq/normalization.html
 */
public fun String.eqIgnoreCaseUnnormalized(other: String): Boolean = this.chars().flatMap(Char::toCasefoldUnnormalized).eq(other.chars().flatMap(Char::toCasefoldUnnormalized))

/**
 * Converts this string to its ASCII upper case equivalent in-place.
 *
 * ASCII letters `a` to `z` are mapped to `A` to `Z`,
 * but all other characters are unchanged.
 *
 * To return a new uppercased value without modifying the existing one, use
 * [toAsciiUppercase].
 *
 * [toAsciiUppercase]: #method.toAsciiUppercase
 *
 * # Examples
 *
 * ```kotlin
 * var s = "Grüße, Jürgen ❤"
 * s.makeAsciiUppercase()
 * check("GRüßE, JüRGEN ❤" == s)
 * ```
 */
public fun String.makeAsciiUppercase() {
    // SAFETY: changing ASCII letters only does not invalidate UTF-8.
    val me = this.asBytesMut()
    me.makeAsciiUppercase()
}

/**
 * Converts this string to its ASCII lower case equivalent in-place.
 *
 * ASCII letters `A` to `Z` are mapped to `a` to `z`,
 * but all other characters are unchanged.
 *
 * To return a new lowercased value without modifying the existing one, use
 * [toAsciiLowercase].
 *
 * [toAsciiLowercase]: #method.toAsciiLowercase
 *
 * # Examples
 *
 * ```kotlin
 * var s = "GRÜßE, JÜRGEN ❤"
 * s.makeAsciiLowercase()
 * check("grÜße, jÜrgen ❤" == s)
 * ```
 */
public fun String.makeAsciiLowercase() {
    // SAFETY: changing ASCII letters only does not invalidate UTF-8.
    val me = this.asBytesMut()
    me.makeAsciiLowercase()
}

/**
 * Copies the string from `src` into `self`, using a memcpy.
 *
 * The length of `src` must be the same as `self`.
 *
 * # Panics
 *
 * This function will panic if the two strings have different lengths.
 *
 * # Examples
 *
 * ```kotlin
 * @feature(strCopyFromStr)
 * public fun doctestEntry() {
 *     val src = "Saludos"
 *     var dst = "Grüße, Jürgen"
 *     // Because the strings have to be the same length,
 *     // we slice the destination slice from sixteen bytes
 *     // to seven. It will panic if we don't do this.
 *     dst.substring(0 until 7).copyFromStr(src)
 *     check(src == "Saludos")
 *     check(dst == "Saludos, Jürgen")
 * }
 * ```
 *
 * Rust enforces that there can only be one mutable reference with no
 * immutable references to a particular piece of data in a particular
 * scope. Because of this, attempting to use `copyFromStr` on a
 * single string will result in a compile failure:
 *
 * ```compile_fail
 * @file:feature(strCopyFromStr)
 * let mut string = String.from("Abcde");
 *
 * string[..2].copyFromStr(&string[3..]); // compile fail!
 * ```
 *
 * To work around this, we can use [splitAtMut] to create two distinct
 * sub-slices from a string:
 *
 * ```kotlin
 * @feature(strCopyFromStr)
 * public fun doctestEntry() {
 *     var string = "Abcde"
 *     run {
 *         val (left, right) = string.splitAtMut(2)
 *         left.copyFromStr(right.substring(1))
 *     }
 *     check(string == "decde")
 * }
 * ```
 *
 * [splitAtMut]: str.splitAtMut
 */
public fun String.copyFromStr(src: String) {
    // SAFETY: `copyFromSlice` panics unless the lengths are equal, and copying same-length
    // UTF-8 into a `String` keeps it valid UTF-8.
    val me = this.asBytesMut()
    run { val dstTmp0 = me; val srcTmp0 = (src.encodeToByteArray().map { it.toUByte() }); for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] }
}

/**
 * Returns a string slice with leading ASCII whitespace removed.
 *
 * `Whitespace` refers to the definition used by
 * [u8.isAsciiWhitespace]. Importantly, this definition excludes
 * the U+000B code point even though it has the Unicode [WhiteSpace] property
 * and is removed by [str.trimStart].
 *
 * [u8.isAsciiWhitespace]: u8.isAsciiWhitespace
 * [WhiteSpace]: https://www.unicode.org/reports/tr44/#WhiteSpace
 *
 * # Examples
 *
 * ```kotlin
 * check(" \t 　hello world\n".trimAsciiStart() == "　hello world\n")
 * check("  ".trimAsciiStart() == "")
 * check("".trimAsciiStart() == "")
 * ```
 */
public fun String.trimAsciiStart(): String {
    // SAFETY: Removing ASCII characters from a `String` does not invalidate
    // UTF-8.
    return fromUtf8Unchecked(this.encodeToByteArray().map { it.toUByte() }.trimAsciiStart())
}

/**
 * Returns a string slice with trailing ASCII whitespace removed.
 *
 * `Whitespace` refers to the definition used by
 * [u8.isAsciiWhitespace]. Importantly, this definition excludes
 * the U+000B code point even though it has the Unicode [WhiteSpace] property
 * and is removed by [str.trimEnd].
 *
 * [u8.isAsciiWhitespace]: u8.isAsciiWhitespace
 * [WhiteSpace]: https://www.unicode.org/reports/tr44/#WhiteSpace
 *
 * # Examples
 *
 * ```kotlin
 * check("\r hello world　\n ".trimAsciiEnd() == "\r hello world　")
 * check("  ".trimAsciiEnd() == "")
 * check("".trimAsciiEnd() == "")
 * ```
 */
public fun String.trimAsciiEnd(): String {
    // SAFETY: Removing ASCII characters from a `String` does not invalidate
    // UTF-8.
    return fromUtf8Unchecked(this.encodeToByteArray().map { it.toUByte() }.trimAsciiEnd())
}

/**
 * Returns a string slice with leading and trailing ASCII whitespace
 * removed.
 *
 * `Whitespace` refers to the definition used by
 * [u8.isAsciiWhitespace]. Importantly, this definition excludes
 * the U+000B code point even though it has the Unicode [WhiteSpace] property
 * and is removed by [str.trim].
 *
 * [u8.isAsciiWhitespace]: u8.isAsciiWhitespace
 * [WhiteSpace]: https://www.unicode.org/reports/tr44/#WhiteSpace
 *
 * # Examples
 *
 * ```kotlin
 * check("\r hello world\n ".trimAscii() == "hello world")
 * check("  ".trimAscii() == "")
 * check("".trimAscii() == "")
 * ```
 */
public fun String.trimAscii(): String {
    // SAFETY: Removing ASCII characters from a `String` does not invalidate
    // UTF-8.
    return fromUtf8Unchecked(this.encodeToByteArray().map { it.toUByte() }.trimAscii())
}

/**
 * Returns an iterator that escapes each char in `self` with [char.escapeDebug].
 *
 * # Examples
 *
 * As an iterator:
 *
 * ```kotlin
 * for (c in "❤\n!".escapeDebug()) {
 *     print("$c")
 * }
 * println("")
 * ```
 *
 * Using `println` directly:
 *
 * ```kotlin
 * println(("❤\n!".escapeDebug()).toString())
 * ```
 *
 * Both are equivalent to:
 *
 * ```kotlin
 * println("❤\\n!")
 * ```
 *
 * Using `toString`:
 *
 * ```kotlin
 * check("❤\n!".escapeDebug().toString() == "❤\\n!")
 * ```
 */
public fun String.escapeDebug(): EscapeDebug = EscapeDebug(inner = this.chars().flatMap(CharEscapeDebug()))

/**
 * Returns an iterator that escapes each char in `self` with [char.escapeDefault].
 *
 * # Examples
 *
 * As an iterator:
 *
 * ```kotlin
 * for (c in "❤\n!".escapeDefault()) {
 *     print("$c")
 * }
 * println("")
 * ```
 *
 * Using `println` directly:
 *
 * ```kotlin
 * println(("❤\n!".escapeDefault()).toString())
 * ```
 *
 * Both are equivalent to:
 *
 * ```kotlin
 * println("\\u{2764}\\n!")
 * ```
 *
 * Using `toString`:
 *
 * ```kotlin
 * check("❤\n!".escapeDefault().toString() == "\\u{2764}\\n!")
 * ```
 */
public fun String.escapeDefault(): EscapeDefault = EscapeDefault(inner = this.chars().flatMap(CharEscapeDefault()))

/**
 * Returns an iterator that escapes each char in `self` with [char.escapeUnicode].
 *
 * # Examples
 *
 * As an iterator:
 *
 * ```kotlin
 * for (c in "❤\n!".escapeUnicode()) {
 *     print("$c")
 * }
 * println("")
 * ```
 *
 * Using `println` directly:
 *
 * ```kotlin
 * println(("❤\n!".escapeUnicode()).toString())
 * ```
 *
 * Both are equivalent to:
 *
 * ```kotlin
 * println("\\u{2764}\\u{a}\\u{21}")
 * ```
 *
 * Using `toString`:
 *
 * ```kotlin
 * check("❤\n!".escapeUnicode().toString() == "\\u{2764}\\u{a}\\u{21}")
 * ```
 */
public fun String.escapeUnicode(): EscapeUnicode = EscapeUnicode(inner = this.chars().flatMap(CharEscapeUnicode()))

/**
 * Returns the range that a substring points to.
 *
 * Returns `None` if `substr` does not point within `self`.
 *
 * Unlike [str.find], **this does not search through the string**.
 * Instead, it uses pointer arithmetic to find where in the string
 * `substr` is derived from.
 *
 * This is useful for extending [str.split] and similar methods.
 *
 * Note that this method may return false positives (typically either
 * `Some(0 .. 0)` or `Some(self.len() .. self.len())`) if `substr` is a
 * zero-length `String` that points at the beginning or end of another,
 * independent, `String`.
 *
 * # Examples
 * ```kotlin
 * import range.Range
 *
 * val data = "a, b, b, a"
 * var iter = data.split(", ").map({ s -> data.substrRange(s).unwrap() })
 * check(iter.next() == Range(start = 0, end = 1))
 * check(iter.next() == Range(start = 3, end = 4))
 * check(iter.next() == Range(start = 6, end = 7))
 * check(iter.next() == Range(start = 9, end = 10))
 * ```
 */
public fun String.substrRange(substr: String): IntRange? = this.encodeToByteArray().map { it.toUByte() }.subsliceRange(substr.encodeToByteArray().map { it.toUByte() })

/**
 * Returns the same string as a string slice `String`.
 *
 * This method is redundant when used directly on `String`, but
 * it helps dereferencing other string-like types to string slices,
 * for example references to `Holder<String>` or `Arc<String>`.
 */
public fun String.asStr(): String = this

public fun String.asRef(): List<UByte> = this.encodeToByteArray().map { it.toUByte() }

/**
 * Creates an empty str
 */
public fun defaultString(): String = ""

// This is required to make `implementation From<String> for Holder<dynamic Error>` and `implementation<E> From<E> for Holder<dynamic Error>` not overlap.
