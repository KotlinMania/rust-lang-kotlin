// port-lint: source str.rs
package io.github.kotlinmania.rustlang.alloc.str

import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.slice.Join
import io.github.kotlinmania.rustlang.alloc.slice.join
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.char.methods.isAscii
import io.github.kotlinmania.rustlang.core.char.methods.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.methods.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.splitAtMut
import io.github.kotlinmania.rustlang.core.str.chars
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8Unchecked
import io.github.kotlinmania.rustlang.core.str.pattern.Pattern
import io.github.kotlinmania.rustlang.core.str.pattern.Searcher
import io.github.kotlinmania.rustlang.core.str.pattern.Utf8Pattern
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.conversions.toCasefold
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.conversions.toLower
import io.github.kotlinmania.rustlang.core.unicode.unicodedata.conversions.toUpper

// Utilities for the `String` primitive type.
//
// *[See also the `String` primitive type](str).*

public fun interface FnCallback {
    public operator fun invoke(arg0: UByte): UByte
}

public typealias Owned = String

/**
 * Note: `String` in `Concat<String>` is not meaningful here.
 * This type parameter of the trait only exists to enable another impl.
 */
public fun concatSSlice(slice: List<S>): String = Join.join(slice, "")

public fun joinSSlice(slice: List<S>, sep: String): String {
    // SAFETY: slice can be borrowed as `[String]` and sep is `String`, both are valid UTF-8,
    // so the returned `List<UByte>` of `joinGenericCopy` is also valid UTF-8.
    return String.fromUtf8Unchecked(joinGenericCopy(slice, sep.encodeToByteArray().map { it.toUByte() }))
}

// Optimized join implementation that works for both Vec<T> (T: Copy) and String's inner vec
// Currently (2018-05-13) there is a bug with type inference and specialization (see issue #36262)
// For this reason SliceConcat<T> is not specialized for T: Copy and SliceConcat<str> is the
// only user of this function. It is left in place for the time when that is fixed.
//
// the bounds for String-join are S: Borrow<str> and for Vec-join Borrow<[T]>
// [T] and str both impl AsRef<[T]> for some T
// => s.borrow().asRef() and we always have slices
//
// # Safety notes
//
// `Borrow` is a safe trait, and implementations are not required
// to be deterministic. An inconsistent `Borrow` implementation could return slices
// of different lengths on consecutive calls (e.g. by using interior mutability).
//
// This implementation calls `borrow()` multiple times:
// 1. To calculate `reservedLen`, all elements are borrowed once.
// 2. All elements, except the first, are borrowed a second time when building the mapped iterator.
//
// Risks and Mitigations:
// - If elements 2..N GROW on their second borrow, the target slice bounds set by `checkedSub`
// means that `splitAtMut` inside `copySliceAndAdvance` will correctly panic.
// - If elements SHRINK on their second borrow, the spare space is never written, and the final
// length set via `setLen` masks trailing uninitialized bytes.
public fun <B, T, S> joinGenericCopy(slice: List<S>, sep: List<T>): List<T> where B : AsRef<List<T>>, S : Borrow<B> {
    val sepLen = sep.size
    val iter = slice.iter()
    // the first slice is the only one without a separator preceding it
    // we take care to only borrow this once during the length calculation
    // to avoid inconsistent Borrow implementations from breaking our assumptions
    val first1 =
        when (val matchVal = iter.next()) {
            null -> { return mutableListOf() }
            else -> {
                val first = matchVal
                first.borrow().asRef()
            }
        }
    // compute the exact total length of the joined Vec
    // if the `len` calculation overflows, we'll panic
    // we would have run out of memory anyway and the rest of the function requires
    // the entire Vec pre-allocated for safety
    val reservedLen =
        (sepLen.checkedMul(iter.len())?.let({ n -> n.checkedAdd(first1.len()) })?.let({ n ->
            // iter starts from the second element as we've already taken the first
            // it's cloned so we can reuse the same iterator below
            iter.clone().map({ s -> s.borrow().asRef().len() }).tryFold(n, Int::checkedAdd)
        }) ?: error("attempt to join into collection with len > usize::MAX"))
    // prepare an uninitialized buffer
    val result = ArrayList<T>(reservedLen)
    check(result.capacity() >= reservedLen)
    result.addAll(first1)
    val pos = result.size
    check(reservedLen >= pos)
    // SAFETY: The size of remaining spare is at least `reservedLen - pos`.
    val target = result.spareCapacityMut().getUncheckedMut(0 until reservedLen - pos)
    // Convert the separator and slices to slices of MaybeUninit
    // to simplify implementation in specializeForLengths.
    // SAFETY: `sep` is a slice of `T`, so `sep.asPtr().cast()` is a valid pointer,
    // and `sep.len()` is the number of elements in the slice, so `fromRawParts` is safe.
    val sepUninit = linearSliceView(sep.asPtr(), sep.size, UnresolvedTypeLayout<T>())
    val iterUninit =
        iter.map({ it ->
            val it1 = it.borrow().asRef()
            // SAFETY: `it` is a slice of `T`, so `it.asPtr().cast()` is a valid pointer,
            // and `it.len()` is the number of elements in the slice, so `fromRawParts` is safe.
            linearSliceView(it1.asPtr().cast(), it1.len().toInt(), PrimitiveLayouts.U8)
        })
    // copy separator and slices over without bounds checks.
    // `specializeForLengths` internally calls `s.borrow()`, but because it uses
    // the bounds-checked `splitAtMut` any misbehaving implementation
    // will not write out of bounds.
    val remain = run {
        var target1 = target
        val iter1 = iterUninit
        val sepBytes = sepUninit
        when (sepUninit.len()) {
            0 -> {
                for (s in iter1) {
                    val len = sepBytes.len()
                    val (head, tail) = target1.splitAtMut(len)
                    head.copyFromSlice(sepBytes)
                    target1 = tail
                    val contentBytes = s.borrow().asRef()
                    val len1 = contentBytes.len()
                    val (head1, tail1) = target1.splitAtMut(len1)
                    head1.copyFromSlice(contentBytes)
                    target1 = tail1
                }
            }
            1 -> {
                for (s1 in iter1) {
                    val len2 = sepBytes.len()
                    val (head2, tail2) = target1.splitAtMut(len2)
                    head2.copyFromSlice(sepBytes)
                    target1 = tail2
                    val contentBytes1 = s1.borrow().asRef()
                    val len3 = contentBytes1.len()
                    val (head3, tail3) = target1.splitAtMut(len3)
                    head3.copyFromSlice(contentBytes1)
                    target1 = tail3
                }
            }
            2 -> {
                for (s2 in iter1) {
                    val len4 = sepBytes.len()
                    val (head4, tail4) = target1.splitAtMut(len4)
                    head4.copyFromSlice(sepBytes)
                    target1 = tail4
                    val contentBytes2 = s2.borrow().asRef()
                    val len5 = contentBytes2.len()
                    val (head5, tail5) = target1.splitAtMut(len5)
                    head5.copyFromSlice(contentBytes2)
                    target1 = tail5
                }
            }
            3 -> {
                for (s3 in iter1) {
                    val len6 = sepBytes.len()
                    val (head6, tail6) = target1.splitAtMut(len6)
                    head6.copyFromSlice(sepBytes)
                    target1 = tail6
                    val contentBytes3 = s3.borrow().asRef()
                    val len7 = contentBytes3.len()
                    val (head7, tail7) = target1.splitAtMut(len7)
                    head7.copyFromSlice(contentBytes3)
                    target1 = tail7
                }
            }
            4 -> {
                for (s4 in iter1) {
                    val len8 = sepBytes.len()
                    val (head8, tail8) = target1.splitAtMut(len8)
                    head8.copyFromSlice(sepBytes)
                    target1 = tail8
                    val contentBytes4 = s4.borrow().asRef()
                    val len9 = contentBytes4.len()
                    val (head9, tail9) = target1.splitAtMut(len9)
                    head9.copyFromSlice(contentBytes4)
                    target1 = tail9
                }
            }
            else -> {
                for (s5 in iter1) {
                    val len10 = sepBytes.len()
                    val (head10, tail10) = target1.splitAtMut(len10)
                    head10.copyFromSlice(sepBytes)
                    target1 = tail10
                    val contentBytes5 = s5.borrow().asRef()
                    val len11 = contentBytes5.len()
                    val (head11, tail11) = target1.splitAtMut(len11)
                    head11.copyFromSlice(contentBytes5)
                    target1 = tail11
                }
            }
        }
        target1
    }
    // A weird borrow implementation may return different
    // slices for the length calculation and the actual copy.
    // Make sure we don't expose uninitialized bytes to the caller.
    val resultLen = reservedLen - remain.len()
    // SAFETY: `resultLen` is less than `reservedLen`, and all elements in `0 .. resultLen` are initialized.
    run {
        result.setLen(resultLen)
    }
    return result
}

/**
 * Helper for final sigma lowercase
 */
public fun mapUppercaseSigma(from: String, i: Int): Char {
    fun <I : Iterator<Char>> caseIgnorableThenCased(iter: I): Boolean =
        when (val matchVal = iter.skipWhile({ c -> c.isCaseIgnorable() }).next()) {
            null -> false
            else -> {
                val c = matchVal
                c.isCased()
            }
        }
    // See https://www.unicode.org/versions/latest/core-spec/chapter-3/#G54277
    // for the definition of `FinalSigma`.
    val isWordFinal = caseIgnorableThenCased(from.substring(0 until i).chars().rev()) && !caseIgnorableThenCased(from.substring(i + '\u03a3'.lenUtf8()).chars())
    return if (isWordFinal) {
        '\u03c2'
    } else {
        '\u03c3'
    }
}

public fun String.borrow(): String = this

public fun String.borrowMut(): String = this

public fun String.toOwned(): String {
    // SAFETY: `self` is a valid UTF-8 str.
    return String.fromUtf8Unchecked(this.encodeToByteArray().map { it.toUByte() }.toOwned())
}

public fun String.cloneInto(target: String) {
    target = ""
    target += this
}

/**
 * Methods for string slices.
 */
/**
 * Converts a `Holder<String>` into a `Holder<[UByte]>` without copying or allocating.
 *
 * # Examples
 *
 * ```kotlin
 * val s = "this is a string"
 * val boxedStr = s.toOwned().intoBoxedStr()
 * val boxedBytes = boxedStr.intoBoxedBytes()
 * check(boxedBytes == s.encodeToByteArray().map { it.toUByte() })
 * ```
 */
public fun String.intoBoxedBytes(): List<UByte> = this.into()

/**
 * Replaces all matches of a pattern with another string.
 *
 * `replace` creates a new [String], and copies the data from this string slice into it.
 * While doing so, it attempts to find matches of a pattern. If it finds any, it
 * replaces them with the replacement string slice.
 *
 * # Examples
 *
 * ```kotlin
 * val s = "this is old"
 * check("this is new" == s.replace("old", "new"))
 * check("than an old" == s.replace("is", "an"))
 * ```
 *
 * When the pattern doesn't match, it returns this string slice as [String]:
 *
 * ```kotlin
 * val s = "this is old"
 * check(s == s.replace("cookie monster", "little lamb"))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.replace(from: P, to: String): String {
    // Fast path for replacing a single ASCII character with another.
    when (val matchVal2 = when (val matchVal = from.asUtf8Pattern()) {
        is Utf8Pattern.StringPattern -> {
            val (s) = matchVal as Utf8Pattern.StringPattern
            run {
                val matchVal1 = s.encodeToByteArray().map { it.toUByte() }
                when {
                    matchVal1.size == 1 -> {
                        val fromByte = matchVal1[0]
                        fromByte
                    }
                    else -> null
                }
            }
        }
        is Utf8Pattern.CharPattern -> {
            val (c) = matchVal as Utf8Pattern.CharPattern
            c.asAscii().map({ asciiChar -> asciiChar.toU8() })
        }
        else -> null
    }) {
        null -> {}
        else -> {
            val fromByte1 = matchVal2
            if (to.encodeToByteArray().map { it.toUByte() } != null) {
                val toByte = to.encodeToByteArray().map { it.toUByte() }[0]
                // SAFETY: `self` is a valid UTF-8 str, `fromByte` and `toByte` are ASCII bytes.
                return replaceAscii(this.encodeToByteArray().map { it.toUByte() }, fromByte1, toByte)
            }
        }
    }
    // Set result capacity to self.len() when from.len() <= to.len()
    val defaultCapacity =
        run {
            val matchVal3 = from.asUtf8Pattern()
            when {
                matchVal3 != null && matchVal3 is Utf8Pattern.StringPattern && matchVal3.value.len() <= to.length -> {
                    val (s1) = matchVal3 as Utf8Pattern.StringPattern
                    this.length
                }
                matchVal3 != null && matchVal3 is Utf8Pattern.CharPattern && matchVal3.value.lenUtf8() <= to.length -> {
                    val (c1) = matchVal3 as Utf8Pattern.CharPattern
                    this.length
                }
                else -> 0
            }
        }
    var result = ""
    var lastEnd = 0
    for ((start, part) in this.matchIndices(from)) {
        // SAFETY: `lastEnd` does not exceed `start`, and `start` does not exceed `self.len()`.
        // Therefore, `lastEnd .. start` is within bounds of `self`.
        // For each iteration, `lastEnd` and `start` lie on UTF-8 sequence boundaries.
        result += this.getUnchecked(lastEnd until start)
        result += to
        lastEnd = start + part.len()
    }
    // SAFETY: `lastEnd` is the start of the remaining unmatched suffix of `self`.
    // It is 0 or the end of a match returned by `matchIndices`, so it is
    // a UTF-8 boundary within `self`. `self.len()` is also a UTF-8 boundary.
    result += this.getUnchecked(lastEnd until this.length)
    return result
}

/**
 * Replaces first N matches of a pattern with another string.
 *
 * `replacen` creates a new [String], and copies the data from this string slice into it.
 * While doing so, it attempts to find matches of a pattern. If it finds any, it
 * replaces them with the replacement string slice at most `count` times.
 *
 * # Examples
 *
 * ```kotlin
 * val s = "foo foo 123 foo"
 * check("new new 123 foo" == s.replacen("foo", "new", 2))
 * check("faa fao 123 foo" == s.replacen('o', "a", 3))
 * check("foo foo new23 foo" == s.replacen(Char.isNumeric, "new", 1))
 * ```
 *
 * When the pattern doesn't match, it returns this string slice as [String]:
 *
 * ```kotlin
 * val s = "this is old"
 * check(s == s.replacen("cookie monster", "little lamb", 10))
 * ```
 */
public fun <Searcher, P : Pattern<Searcher>> String.replacen(pat: P, to: String, count: Int): String {
    // Hope to reduce the times of re-allocation
    var result = ""
    var lastEnd = 0
    for ((start, part) in this.matchIndices(pat).take(count)) {
        // SAFETY: `lastEnd` does not exceed `start`, and `start` does not exceed `self.len()`.
        // Therefore, `lastEnd .. start` is within bounds of `self`.
        // For each iteration, `lastEnd` and `start` lie on UTF-8 sequence boundaries.
        result += this.getUnchecked(lastEnd until start)
        result += to
        lastEnd = start + part.len()
    }
    // SAFETY: `lastEnd` is the start of the remaining suffix of `self`.
    // It is 0 or the end of a match returned by `matchIndices`, so it is
    // a UTF-8 boundary within `self`. `self.len()` is also a UTF-8 boundary.
    result += this.getUnchecked(lastEnd until this.length)
    return result
}

/**
 * Returns the lowercase equivalent of this string slice, as a new [String].
 *
 * `Lowercase` is defined according to the terms of
 * [Chapter 3 (Conformance)](https://www.unicode.org/versions/latest/core-spec/chapter-3/#G34432)
 * of the Unicode standard.
 *
 * Since some characters can expand into multiple characters when changing
 * the case, this function returns a [String] instead of modifying the
 * parameter in-place.
 *
 * Unlike [char.toLowercase], this method fully handles the context-dependent
 * casing of Greek sigma. However, like that method, it does not handle locale-specific
 * casing, like Turkish and Azeri I/ı/İ/i. See its documentation
 * for more information.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val s = "HELLO WORLD"
 * check("hello world" == s.lowercase())
 * ```
 *
 * Tricky examples, with sigma:
 *
 * ```kotlin
 * val sigma = "Σ"
 * check("σ" == sigma.lowercase())
 * // but at the end of a word, it's ς, not σ:
 * val odysseus = "ὈΔΥΣΣΕΎΣ"
 * check("ὀδυσσεύς" == odysseus.lowercase())
 * val odysseusKingOfIthaca = "Ο ΟΔΥΣΣΈΑΣ ΒΑΣΙΛΙΆΣ ΤΗΣ ΙΘΆΚΗΣ"
 * check("ο οδυσσέας βασιλιάς της ιθάκης" == odysseusKingOfIthaca.lowercase())
 * ```
 *
 * Languages without case are not changed:
 *
 * ```kotlin
 * val newYear = "农历新年"
 * check(newYear == newYear.lowercase())
 * ```
 */
public fun String.toLowercase(): String {
    // SAFETY: `toAsciiLowercase` preserves ASCII bytes, so the converted
    // prefix remains valid UTF-8.
    var (s, rest) = convertWhileAscii(this, UByte::toAsciiLowercase)
    val prefixLen = s.len()
    for ((i, c) in rest.charIndices()) {
        if (c == '\u03a3') {
            // Σ maps to σ, except at the end of a word where it maps to ς.
            // This is the only conditional (contextual) but language-independent mapping
            // in `SpecialCasing.txt`,
            // so hard-code it rather than have a generic "condition" mechanism.
            // See https://github.com/rust-lang/rust/issues/26035
            val sigmaLowercase = mapUppercaseSigma(this, prefixLen + i)
            s.push(sigmaLowercase)
        } else {
            run {
                val matchVal = toLower(c)
                when {
                    matchVal.size == 3 && matchVal[1] == '\u0000' -> {
                        val a = matchVal[0]
                        s.push(a)
                    }
                    matchVal.size == 3 && matchVal[2] == '\u0000' -> {
                        val a1 = matchVal[0]
                        val b = matchVal[1]
                        s.push(a1)
                        s.push(b)
                    }
                    else -> {
                        val a2 = matchVal[0]
                        val b1 = matchVal[1]
                        val c1 = matchVal[2]
                        s.push(a2)
                        s.push(b1)
                        s.push(c1)
                    }
                }
            }
        }
    }
    return s
}

/**
 * Returns the titlecase equivalent of this string slice,
 * which is assumed to represent a single word,
 * as a new [String].
 *
 * Essentially, this consists of uppercasing the first cased letter
 * (with [char.toTitlecase]), and lowercasing everything that follows.
 *
 * `Titlecase` is defined according to the terms of
 * [Chapter 3 (Conformance)](https://www.unicode.org/versions/latest/core-spec/chapter-3/#G34082)
 * of the Unicode standard.
 *
 * Since some characters can expand into multiple characters when changing
 * the case, this function returns a [String] instead of modifying the
 * parameter in-place.
 *
 * Unlike [char.toLowercase], this method fully handles the context-dependent
 * casing of Greek sigma. However, like that method, it does not handle locale-specific
 * casing, like Turkish and Azeri I/ı/İ/i. See its documentation
 * for more information.
 *
 * This method does not perform any kind of word segmentation.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     val s = "HELLO WORLD"
 *     check("Hello world" == s.wordToTitlecase())
 * }
 * ```
 *
 * The first *cased* letter is uppercased:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     val theNightBeforeChristmas = "'twas"
 *     check("'Twas" == theNightBeforeChristmas.wordToTitlecase())
 * }
 * ```
 *
 * Languages without case are not changed:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     val newYear = "农历新年"
 *     check(newYear == newYear.wordToTitlecase())
 * }
 * ```
 *
 * Georgian uppercase ("Mtavruli") letters are not used in titlecase:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     val georgian = "ერთობაშია"
 *     check(georgian == georgian.wordToTitlecase())
 * }
 * ```
 *
 * No word segmentation is performed,
 * so only the first cased letter in the whole string gets uppercased:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     val blazinglyFast = "ferris and I"
 *     check("Ferris and i" == blazinglyFast.wordToTitlecase())
 * }
 * ```
 *
 * Tricky examples, with sigma:
 *
 * ```kotlin
 * @feature(titlecase)
 * public fun doctestEntry() {
 *     val odysseus = "ὈΔΥΣΣΕΎΣ"
 *     check("Ὀδυσσεύς" == odysseus.wordToTitlecase())
 *     val odysseusKingOfIthaca = "Ο ΟΔΥΣΣΈΑΣ ΒΑΣΙΛΙΆΣ ΤΗΣ ΙΘΆΚΗΣ"
 *     check("Ο οδυσσέας βασιλιάς της ιθάκης" == odysseusKingOfIthaca.wordToTitlecase())
 * }
 * ```
 */
public fun String.wordToTitlecase(): String {
    var s = ""
    val chars = this.charIndices()
    // The first cased character is title-cased; leading uncased characters pass through.
    for ((_, c) in chars.byRef()) {
        if (c.isCased()) {
            s.extend(c.toTitlecase())
            break
        } else {
            s += c
        }
    }
    // Everything after the first cased character is lower-cased. Use the ASCII fast
    // path (auto-vectorized) for its ASCII prefix, mirroring `toLowercase`.
    val remainder = chars
    val restStart = this.length - remainder.length
    // SAFETY: `toAsciiLowercase` preserves ASCII bytes, so the prefix stays valid UTF-8.
    val (ascii, rest) = convertWhileAscii(remainder, UByte::toAsciiLowercase)
    s += ascii
    val prefixLen = restStart + ascii.len()
    for ((i, c1) in rest.charIndices()) {
        if (c1 == '\u03a3') {
            // Σ maps to σ, except at the end of a word where it maps to ς.
            // This is the only conditional (contextual) but language-independent mapping
            // in `SpecialCasing.txt`,
            // so hard-code it rather than have a generic "condition" mechanism.
            // See https://github.com/rust-lang/rust/issues/26035
            val sigmaLowercase = mapUppercaseSigma(this, prefixLen + i)
            s += sigmaLowercase
        } else {
            run {
                val matchVal = toLower(c1)
                when {
                    matchVal.size == 3 && matchVal[1] == '\u0000' -> {
                        val a = matchVal[0]
                        s += a
                    }
                    matchVal.size == 3 && matchVal[2] == '\u0000' -> {
                        val a1 = matchVal[0]
                        val b = matchVal[1]
                        s += a1
                        s += b
                    }
                    else -> {
                        val a2 = matchVal[0]
                        val b1 = matchVal[1]
                        val c2 = matchVal[2]
                        s += a2
                        s += b1
                        s += c2
                    }
                }
            }
        }
    }
    return s
}

/**
 * Returns the uppercase equivalent of this string slice, as a new [String].
 *
 * `Uppercase` is defined according to the terms of
 * [Chapter 3 (Conformance)](https://www.unicode.org/versions/latest/core-spec/chapter-3/#G34431)
 * of the Unicode standard.
 *
 * Since some characters can expand into multiple characters when changing
 * the case, this function returns a [String] instead of modifying the
 * parameter in-place.
 *
 * Like [char.toUppercase] this method does not handle language-specific
 * casing, like Turkish and Azeri I/ı/İ/i. See that method's documentation
 * for more information.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * val s = "hello world"
 * check("HELLO WORLD" == s.uppercase())
 * ```
 *
 * Scripts without case are not changed:
 *
 * ```kotlin
 * val newYear = "农历新年"
 * check(newYear == newYear.uppercase())
 * ```
 *
 * One character can become multiple:
 * ```kotlin
 * val s = "tschüß"
 * check("TSCHÜSS" == s.uppercase())
 * ```
 */
public fun String.toUppercase(): String {
    // SAFETY: `toAsciiUppercase` preserves ASCII bytes, so the converted
    // prefix remains valid UTF-8.
    var (s, rest) = convertWhileAscii(this, UByte::toAsciiUppercase)
    for (c in rest.chars()) {
        run {
            val matchVal = toUpper(c)
            when {
                matchVal.size == 3 && matchVal[1] == '\u0000' -> {
                    val a = matchVal[0]
                    s.push(a)
                }
                matchVal.size == 3 && matchVal[2] == '\u0000' -> {
                    val a1 = matchVal[0]
                    val b = matchVal[1]
                    s.push(a1)
                    s.push(b)
                }
                else -> {
                    val a2 = matchVal[0]
                    val b1 = matchVal[1]
                    val c1 = matchVal[2]
                    s.push(a2)
                    s.push(b1)
                    s.push(c1)
                }
            }
        }
    }
    return s
}

/**
 * Returns the case-folded equivalent of this string slice, as a new [String].
 *
 * Case folding is a transformation, mostly matching lowercase, that is meant to be used
 * for case-insensitive string comparisons. Case-folded strings should not usually
 * be exposed directly to users.
 *
 * For the precise specification of case folding, see
 * [Chapter 3 (Conformance)](https://www.unicode.org/versions/latest/core-spec/chapter-3/#G63737)
 * of the Unicode standard.
 *
 * Since some characters can expand into multiple characters when case folding,
 * this function returns a [String] instead of modifying the parameter in-place.
 *
 * No [normalization] (e.g. NFC) is performed, so visually and semantically identical strings
 * might still casefold differently. For example, `"Å"` (U+00C5 LATIN CAPITAL LETTER A WITH RING ABOVE)
 * is considered distinct from `"Å"` (A followed by U+030A COMBINING RING ABOVE),
 * even though Unicode considers them canonically equivalent.
 *
 * Like [char.toCasefoldUnnormalized] this method does not handle language-specific
 * casing, like Turkish and Azeri I/ı/İ/i. See that method's documentation
 * for more information.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * @feature(casefold)
 * public fun doctestEntry() {
 *     val s0 = "HELLO"
 *     val s1 = "Hello"
 *     check(s0.toCasefoldUnnormalized() == s1.toCasefoldUnnormalized())
 *     check(s0.toCasefoldUnnormalized() == "hello")
 * }
 * ```
 *
 * Scripts without case are not changed:
 *
 * ```kotlin
 * @feature(casefold)
 * public fun doctestEntry() {
 *     val newYear = "农历新年"
 *     check(newYear == newYear.toCasefoldUnnormalized())
 * }
 * ```
 *
 * One character can become multiple:
 *
 * ```kotlin
 * @feature(casefold)
 * public fun doctestEntry() {
 *     val s0 = "TSCHÜẞ"
 *     val s1 = "TSCHÜSS"
 *     val s2 = "tschüß"
 *     check(s0.toCasefoldUnnormalized() == s1.toCasefoldUnnormalized())
 *     check(s0.toCasefoldUnnormalized() == s2.toCasefoldUnnormalized())
 *     check(s0.toCasefoldUnnormalized() == "tschüss")
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
 *     check(comp.toCasefoldUnnormalized() == "å")
 *     check(decomp.toCasefoldUnnormalized() == "å")
 * }
 * ```
 *
 * [normalization]: https://www.unicode.org/faq/normalization.html
 */
public fun String.toCasefoldUnnormalized(): String {
    // SAFETY: `toAsciiLowercase` preserves ASCII bytes, so the converted
    // prefix remains valid UTF-8.
    var (s, rest) = convertWhileAscii(this, UByte::toAsciiLowercase)
    for (c in rest.chars()) {
        run {
            val matchVal = toCasefold(c)
            when {
                matchVal.size == 3 && matchVal[1] == '\u0000' -> {
                    val a = matchVal[0]
                    s.push(a)
                }
                matchVal.size == 3 && matchVal[2] == '\u0000' -> {
                    val a1 = matchVal[0]
                    val b = matchVal[1]
                    s.push(a1)
                    s.push(b)
                }
                else -> {
                    val a2 = matchVal[0]
                    val b1 = matchVal[1]
                    val c1 = matchVal[2]
                    s.push(a2)
                    s.push(b1)
                    s.push(c1)
                }
            }
        }
    }
    return s
}

/**
 * Converts a [Box.str] into a [String] without copying or allocating.
 *
 * # Examples
 *
 * ```kotlin
 * val string = "birthday gift"
 * val boxedStr = string.clone().intoBoxedStr()
 * check(boxedStr.intoString() == string)
 * ```
 */
public fun String.intoString(): String {
    val slice = Box.from(this)
    // SAFETY: `slice` is a valid UTF-8 sequence because it is created from a `Holder<String>`.
    return String.fromUtf8Unchecked(slice.intoVec())
}

/**
 * Creates a new [String] by repeating a string `n` times.
 *
 * # Panics
 *
 * This function will panic if the capacity would overflow.
 *
 * # Examples
 *
 * Basic usage:
 *
 * ```kotlin
 * check("abc".repeat(4) == "abcabcabcabc")
 * ```
 *
 * A panic upon overflow:
 *
 * ```kotlin
 * // this will panic at runtime
 * val huge = "0123456789abcdef".repeat(Int.MAX_VALUE)
 * ```
 */
public fun String.repeat(n: Int): String {
    // SAFETY: The created Vec<u8> is valid UTF-8 because `self` is str.
    return String.fromUtf8Unchecked(this.encodeToByteArray().map { it.toUByte() }.repeat(n))
}

/**
 * Returns a copy of this string where each character is mapped to its
 * ASCII upper case equivalent.
 *
 * ASCII letters `a` to `z` are mapped to `A` to `Z`,
 * but non-ASCII letters are unchanged.
 *
 * To uppercase the value in-place, use [makeAsciiUppercase].
 *
 * To uppercase ASCII characters in addition to non-ASCII characters, use
 * [toUppercase].
 *
 * # Examples
 *
 * ```kotlin
 * val s = "Grüße, Jürgen ❤"
 * check("GRüßE, JüRGEN ❤" == s.toAsciiUppercase())
 * ```
 *
 * [makeAsciiUppercase]: str.makeAsciiUppercase
 * [toUppercase]: #method.toUppercase
 */
public fun String.toAsciiUppercase(): String {
    val bytes = this.encodeToByteArray().map { it.toUByte() }.toAsciiUppercase()
    // SAFETY: ASCII case conversion only maps a-z to A-Z and leaves
    // all other bytes unchanged as valid UTF-8
    return String.fromUtf8Unchecked(bytes)
}

/**
 * Returns a copy of this string where each character is mapped to its
 * ASCII lower case equivalent.
 *
 * ASCII letters `A` to `Z` are mapped to `a` to `z`,
 * but non-ASCII letters are unchanged.
 *
 * To lowercase the value in-place, use [makeAsciiLowercase].
 *
 * To lowercase ASCII characters in addition to non-ASCII characters, use
 * [toLowercase].
 *
 * # Examples
 *
 * ```kotlin
 * val s = "Grüße, Jürgen ❤"
 * check("grüße, jürgen ❤" == s.toAsciiLowercase())
 * ```
 *
 * [makeAsciiLowercase]: str.makeAsciiLowercase
 * [toLowercase]: #method.toLowercase
 */
public fun String.toAsciiLowercase(): String {
    val bytes = this.encodeToByteArray().map { it.toUByte() }.toAsciiLowercase()
    // SAFETY: ASCII case conversion only maps A-Z to a-z and leaves
    // all other bytes unchanged as valid UTF-8
    return String.fromUtf8Unchecked(bytes)
}

/**
 * Converts a boxed slice of bytes to a boxed string slice without checking
 * that the string contains valid UTF-8.
 *
 * # Safety
 *
 * * The provided bytes must contain a valid UTF-8 sequence.
 *
 * # Examples
 *
 * ```kotlin
 * val smileUtf8 = listOf(226, 152, 186)
 * val smile = String.fromBoxedUtf8Unchecked(smileUtf8)
 * check("☺" == smile)
 * ```
 */
public fun fromBoxedUtf8Unchecked(v: List<UByte>): String {
    // SAFETY: Upheld by caller.
    return Box.fromRaw(mutPtrFromRustCast(Box.intoRaw(v)))
}

/**
 * Internal; same as `fromBoxedUtf8Unchecked` but allocator-generic. Name
 * probably not suitable for being made `public` as-is.
 */
public fun <A : Allocator> fromBoxedUtf8UncheckedIn(v: List<UByte>): String {
    val (ptr, alloc) = Box.intoRawWithAllocator(v)
    // SAFETY: Upheld by caller.
    return Box.fromRawIn(mutPtrFromRustCast(ptr), alloc)
}

/**
 * Converts leading ascii bytes in `s` by calling the `convert` function.
 *
 * For better average performance, this happens in chunks of `2 * sizeOf<Int>()`.
 *
 * Returns a tuple of the converted prefix and the remainder starting from
 * the first non-ascii character.
 *
 * This function is only public so that it can be verified in a codegen test,
 * see `issue - 123712 - String - to - lower - autovectorization.rs`.
 *
 * # Safety
 *
 * `convert` must return an ASCII byte for every ASCII input byte.
 */
public fun convertWhileAscii(s: String, convert: FnCallback): Pair<String, String> {
    // Process the input in chunks of 16 bytes to enable auto-vectorization.
    // Previously the chunk size depended on the size of `Int`,
    // but on 32-bit platforms with sse or neon is also the better choice.
    // The only downside on other platforms would be a bit more loop-unrolling.
    val N: Int = 16
    var slice = s.encodeToByteArray().map { it.toUByte() }
    val out = ArrayList(slice.size)
    var outSlice = out.spareCapacityMut()
    var asciiPrefixLen = 0
    val isAscii = BooleanArray((N).toInt()) { false }
    while (slice.size >= N) {
        // SAFETY: checked in loop condition
        val chunk = slice.getUnchecked(0 until N)
        // SAFETY: outSlice has at least same length as input slice and gets sliced with the same offsets
        val outChunk = outSlice.getUncheckedMut(0 until N)
        for (j in 0 until N) {
            isAscii[j] = chunk[j] <= 127
        }
        // Auto-vectorization for this check is a bit fragile, sum and comparing against the chunk
        // size gives the best result, specifically a pmovmsk instruction on x86.
        // See https://github.com/llvm/llvm-project/issues/96395 for why llvm currently does not
        // currently recognize other similar idioms.
        if (isAscii.map({ x -> x.toUByte() }).sum<UByte>().toInt() != N) {
            break
        }
        for (j1 in 0 until N) {
            outChunk[j1] = MaybeUninit.new(convert(chunk[j1]), UnresolvedTypeLayout<Pair<String, String>>())
        }
        asciiPrefixLen += N
        // SAFETY: checked in loop condition.
        slice = slice.getUnchecked(N until Int.MAX_VALUE)
        // SAFETY: outSlice has at least same length as input slice.
        outSlice = outSlice.getUncheckedMut(N until Int.MAX_VALUE)
    }
    // handle the remainder as individual bytes
    while (!slice.isEmpty()) {
        val byte = slice[0]
        if (byte > 127u.toUByte()) {
            break
        }
        // SAFETY: outSlice has at least same length as input slice
        run {
            outSlice.getUncheckedMut(0) = MaybeUninit.new(convert(byte), UnresolvedTypeLayout<Pair<String, String>>())
        }
        asciiPrefixLen += 1
        // SAFETY: slice has at least one byte, so slicing from 1.. is safe
        slice = slice.getUnchecked(1 until Int.MAX_VALUE)
        // SAFETY: outSlice has at least same length as input slice
        outSlice = outSlice.getUncheckedMut(1 until Int.MAX_VALUE)
    }
    // SAFETY: asciiPrefixLen bytes have been initialized above
    out.setLen(asciiPrefixLen)
    // SAFETY: We have written only valid ascii to the output vec
    val asciiString = String.fromUtf8Unchecked(out)
    // SAFETY: we know this is a valid char boundary
    // since we only skipped over leading ascii bytes
    val rest = String.fromUtf8Unchecked(slice)
    return Pair(asciiString, rest)
}

/**
 * Faster implementation of string replacement for ASCII to ASCII cases.
 * Should produce fast vectorized code.
 *
 * # Safety
 *
 * * `utf8Bytes` must contain valid UTF-8.
 * * Both `from` and `to` must be ASCII bytes (at most `0x7F`).
 */
public fun replaceAscii(utf8Bytes: List<UByte>, from: UByte, to: UByte): String {
    val result: List<UByte> =
        utf8Bytes.map({ b -> if (b == from.toInt()) {
            to
        } else {
            b
        } }).toMutableList()
    // SAFETY: We replaced ascii with ascii on valid utf8 strings.
    return String.fromUtf8Unchecked(result)
}
