package io.github.kotlinmania.rustlang.alloc.wtf8

import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.core.char.methods.encodeUtf8Raw
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.wtf8.CodePoint
import io.github.kotlinmania.rustlang.core.wtf8.Wtf8

/**
 * An owned, growable string of [well-formed WTF-8](https://wtf-8.codeberg.page/#well-formed) data.
 *
 * Similar to `String`, but can additionally contain surrogate code points
 * if they’re not in a surrogate pair.
 */
public open class Wtf8Buf(
    public var bytes: MutableList<UByte>,
    /**
     * Do we know that `bytes` holds a valid UTF-8 encoding? We can easily
     * know this if we're constructed from a `String` or `String`.
     *
     * It is possible for `bytes` to have valid UTF-8 without this being
     * set, such as when we're concatenating `Wtf8`s and surrogates become
     * paired, as we don't bother to rescan the entire string.
     */
    public var isKnownUtf8: Boolean,
) {
    public open fun clone(): Wtf8Buf = Wtf8Buf(bytes, isKnownUtf8)

    public open fun deref(): Wtf8 = this.asSlice()

    public open fun derefMut(): Wtf8 = this.asMutSlice()

    /**
     * Formats the string in double quotes, with characters escaped according to
     * [char.escapeDebug] and unpaired surrogates represented as `\u(xxxx)`,
     * where each `x` is a hexadecimal digit.
     *
     * For example, the code units [U+0061, U+D800, U+000A] are formatted as
     * `"a\u(D800)\n"`.
     */
    public open fun fmt(formatter: StringBuilder): Result<Unit, Error> = run { formatter.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    /**
     * Formats the string with unpaired surrogates substituted with the replacement
     * character, U+FFFD.
     */
    public open fun fmtDisplay(formatter: StringBuilder): Result<Unit, Error> =
        when (val matchVal = this.asKnownUtf8()) {
            null -> {
                run { formatter.append((this).toString()); Result.Ok<Unit, Error>(Unit) }
            }
            else -> {
                val s = matchVal
                run { formatter.append((s).toString()); Result.Ok<Unit, Error>(Unit) }
            }
        }

    public open fun clear() {
        this.bytes.clear()
        this.isKnownUtf8 = true
    }

    /**
     * Appends the given `char` to the end of this string.
     * This does **not** include the WTF-8 concatenation check or `isKnownUtf8` check.
     * Copied from String.push.
     *
     * # Safety
     *
     * `self` must contain [well-formed WTF-8](https://wtf-8.codeberg.page/#well-formed),
     * and appending `codePoint` must preserve that invariant. In particular,
     * `codePoint` must not be a trailing surrogate if `self` ends with a leading surrogate.
     *
     * If `self.isKnownUtf8` is true, `codePoint` must not be a surrogate.
     */
    public open fun pushCodePointUnchecked(codePoint: CodePoint) {
        val bytes = MutableList((Char.MAX_LEN_UTF8).toInt()) { 0 }
        val bytes1 = encodeUtf8Raw(codePoint.toU32(), bytes)
        this.bytes.addAll(bytes1)
    }

    public open fun asSlice(): Wtf8 {
        // SAFETY: `self` maintains `bytes` as well-formed WTF-8.
        return Wtf8.fromBytesUnchecked(this.bytes)
    }

    public open fun asMutSlice(): Wtf8 {
        // SAFETY: `self` maintains `bytes` as well-formed WTF-8.
        return Wtf8.fromMutBytesUnchecked(this.bytes)
    }

    /**
     * Converts the string to UTF-8 without validation, if it was created from
     * valid UTF-8.
     */
    public open fun asKnownUtf8(): String? =
        if (this.isKnownUtf8) {
            // SAFETY: The buffer is known to be valid UTF-8.
            String.fromUtf8Unchecked(this.asBytes())
        } else {
            null
        }

    /**
     * Reserves capacity for at least `additional` more bytes to be inserted
     * in the given `Wtf8Buf`.
     * The collection may reserve more space to avoid frequent reallocations.
     *
     * # Panics
     *
     * Panics if the new capacity exceeds `Long.MAX` bytes.
     */
    public open fun reserve(additional: Int) {
        this.bytes.reserve(additional)
    }

    /**
     * Tries to reserve capacity for at least `additional` more bytes to be
     * inserted in the given `Wtf8Buf`. The `Wtf8Buf` may reserve more space to
     * avoid frequent reallocations. After calling `tryReserve`, capacity will
     * be greater than or equal to `self.len() + additional`. Does nothing if
     * capacity is already sufficient. This method preserves the contents even
     * if an error occurs.
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.bytes.tryReserve(additional)

    public open fun reserveExact(additional: Int) {
        this.bytes.reserveExact(additional)
    }

    /**
     * Tries to reserve the minimum capacity for exactly `additional` more
     * bytes to be inserted in the given `Wtf8Buf`. After calling
     * `tryReserveExact`, capacity will be greater than or equal to
     * `self.len() + additional` if it returns `Ok(())`.
     * Does nothing if the capacity is already sufficient.
     *
     * Note that the allocator may give the `Wtf8Buf` more space than it
     * requests. Therefore, capacity can not be relied upon to be precisely
     * minimal. Prefer [tryReserve] if future insertions are expected.
     *
     * [tryReserve]: Wtf8Buf.tryReserve
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     */
    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> = this.bytes.tryReserveExact(additional)

    public open fun shrinkToFit() {
        this.bytes.shrinkToFit()
    }

    public open fun shrinkTo(minCapacity: Int) {
        this.bytes.shrinkTo(minCapacity)
    }

    public open fun leak(): Wtf8 {
        // SAFETY: `self` maintains `bytes` as well-formed WTF-8.
        return Wtf8.fromMutBytesUnchecked(this.bytes.leak())
    }

    /**
     * Returns the number of bytes that this string buffer can hold without reallocating.
     */
    public open fun capacity(): Int = this.bytes.capacity()

    /**
     * Append a UTF-8 slice at the end of the string.
     */
    public open fun pushStr(other: String) {
        this.bytes.addAll(other.encodeToByteArray().map { it.toUByte() })
    }

    /**
     * Append a WTF-8 slice at the end of the string.
     *
     * This replaces newly paired surrogates at the boundary
     * with a supplementary code point,
     * like concatenating ill-formed UTF-16 strings effectively would.
     */
    public open fun pushWtf8(other: Wtf8) {
        run {
            val matchVal = Pair(this.finalLeadSurrogate(), other.initialTrailSurrogate())
            when {
                // Replace newly paired surrogates by a supplementary code point.
                matchVal.first != null && matchVal.second != null -> {
                    val lead = matchVal.first
                    val trail = matchVal.second
                    val lenWithoutLeadSurrogate = this.len() - 3
                    this.bytes.truncate(lenWithoutLeadSurrogate)
                    val otherWithoutTrailSurrogate = ((other.asBytes()).subList(3, (other.asBytes()).size))
                    // 4 bytes for the supplementary code point
                    this.bytes.reserve(4 + otherWithoutTrailSurrogate.size)
                    this.pushChar(decodeSurrogatePair(lead, trail))
                    this.bytes.addAll(otherWithoutTrailSurrogate)
                }
                else -> {
                    // If we'll be pushing a string containing a surrogate, we may
                    // no longer have UTF-8.
                    if (this.isKnownUtf8 && other.nextSurrogate(0) != null) {
                        this.isKnownUtf8 = false
                    }
                    this.bytes.addAll(other.asBytes())
                }
            }
        }
    }

    /**
     * Append a Unicode scalar value at the end of the string.
     */
    public open fun pushChar(c: Char) {
        // SAFETY: It's always safe to push a char.
        this.pushCodePointUnchecked(CodePoint.fromChar(c))
    }

    /**
     * Append a code point at the end of the string.
     *
     * This replaces newly paired surrogates at the boundary
     * with a supplementary code point,
     * like concatenating ill-formed UTF-16 strings effectively would.
     */
    public open fun push(codePoint: CodePoint) {
        when (val matchVal = codePoint.toTrailSurrogate()) {
            null -> {
                if (codePoint.toLeadSurrogate() != null) {
                    // We're pushing a leading surrogate.
                    this.isKnownUtf8 = false
                }
            }
            else -> {
                val trail = matchVal
                when (val matchVal1 = this.finalLeadSurrogate()) {
                    null -> {}
                    else -> {
                        val lead = matchVal1
                        val lenWithoutLeadSurrogate = this.len() - 3
                        this.bytes.truncate(lenWithoutLeadSurrogate)
                        this.pushChar(decodeSurrogatePair(lead, trail))
                        return
                    }
                }
                // We're pushing a trailing surrogate.
                this.isKnownUtf8 = false
            }
        }
        // SAFETY: We have checked that no newly paired surrogates at the boundary.
        this.pushCodePointUnchecked(codePoint)
    }

    /**
     * Shortens a string to the specified length.
     *
     * If `newLen` is greater than or equal to the string's current length,
     * this has no effect.
     *
     * # Panics
     *
     * Panics if `newLen` does not lie on a code point boundary.
     */
    public open fun truncate(newLen: Int) {
        if (newLen >= this.len()) {
            return
        }
        check(this.isCodePointBoundary(newLen))
        this.bytes.truncate(newLen)
    }

    /**
     * Consumes the WTF-8 string and tries to convert it to a vec of bytes.
     */
    public open fun intoBytes(): List<UByte> = this.bytes

    /**
     * Consumes the WTF-8 string and tries to convert it to UTF-8.
     *
     * This does not copy the data.
     *
     * If the contents are not well-formed UTF-8
     * (that is, if the string contains surrogates),
     * the original WTF-8 string is returned instead.
     */
    public open fun intoString(): Result<String, Wtf8Buf> =
        if (this.isKnownUtf8 || this.nextSurrogate(0) == null) {
            // SAFETY: We have checked that `self.bytes` contains valid UTF-8.
            Result.Ok<String, Wtf8Buf>(String.fromUtf8Unchecked(this.bytes))
        } else {
            Result.Err<String, Wtf8Buf>(this)
        }

    /**
     * Consumes the WTF-8 string and converts it lossily to UTF-8.
     *
     * This does not copy the data (but may overwrite parts of it in place).
     *
     * Surrogates are replaced with `"\u(FFFD)"` (the replacement character “�”)
     */
    public open fun intoStringLossy(): String {
        if (!this.isKnownUtf8) {
            var pos = 0
            while (true) {
                val matchVal = this.nextSurrogate(pos)
                if (matchVal == null) break
                val surrogatePos = matchVal.first
                pos = surrogatePos + 3
                // Surrogates and the replacement character are all 3 bytes, so
                // they can substituted in-place.
                run { val dstTmp0 = this.bytes; val srcTmp0 = (("�").encodeToByteArray().map { it.toUByte() }); for (idxTmp0 in srcTmp0.indices) dstTmp0[(surrogatePos) + idxTmp0] = srcTmp0[idxTmp0] }
            }
        }
        // SAFETY: Now `self.bytes` contains valid UTF-8.
        return String.fromUtf8Unchecked(this.bytes)
    }

    /**
     * Converts this `Wtf8Buf` into a boxed `Wtf8`.
     */
    public open fun intoBox(): Wtf8 {
        // SAFETY: `Wtf8` is a transparent wrapper around `[UByte]`, and
        // `self.bytes.intoBoxedSlice()` returns a `Holder<[UByte]>`.
        // Therefore, transmuting `Holder<[UByte]>` to `Holder<Wtf8>` is safe.
        return transmuteWithLayouts<Any?, Wtf8>(this.bytes.intoBoxedSlice(), UnresolvedTypeLayout<Any?>(), UnresolvedTypeLayout<Wtf8>())
    }

    /**
     * Provides plumbing to core `List.extendFromSlice`.
     * More well behaving alternative to allowing outer types
     * full mutable access to the core `List`.
     *
     * # Safety
     *
     * `self` and `other` must contain [well-formed WTF-8](https://wtf-8.codeberg.page/#well-formed),
     * and appending `other` to `self` must preserve that invariant.
     * In particular, `self` must not end with a leading surrogate,
     * or `other` must not start with a trailing surrogate.
     */
    public open fun extendFromSliceUnchecked(other: List<UByte>) {
        this.bytes.addAll(other)
        this.isKnownUtf8 = false
    }

    /**
     * Append code points from an iterator to the string.
     *
     * This replaces surrogate code point pairs with supplementary code points,
     * like concatenating ill-formed UTF-16 strings effectively would.
     */
    public open fun <I : Iterable<CodePoint>> extend(iter: I) {
        val iterator = iter.iterator()
        val (low, high) = iterator.sizeHint()
        // Lower bound of one byte per code point (ASCII only)
        this.bytes.reserve(low)
        iterator.forEach({ codePoint -> this.push(codePoint) })
    }

    public open fun extendOne(codePoint: CodePoint) {
        this.push(codePoint)
    }

    public open fun extendReserve(additional: Int) {
        // Lower bound of one byte per code point (ASCII only)
        this.bytes.reserve(additional)
    }

    public override fun hashCode(): Int {
        var state = 0
        state.write(this.bytes)
        state = 31 * state + (0xfeu.toUByte()).hashCode()
        return state
    }

    public companion object {
        /**
         * Creates a new, empty WTF-8 string.
         */
        public fun new(): Wtf8Buf = Wtf8Buf(bytes = mutableListOf(), isKnownUtf8 = true)

        /**
         * Creates a new, empty WTF-8 string with pre-allocated capacity for `capacity` bytes.
         */
        public fun withCapacity(capacity: Int): Wtf8Buf = Wtf8Buf(bytes = ArrayList(capacity), isKnownUtf8 = true)

        /**
         * Creates a WTF-8 string from a WTF-8 byte vec.
         *
         * # Safety
         *
         * `value` must contain [well-formed WTF-8](https://wtf-8.codeberg.page/#well-formed).
         */
        public fun fromBytesUnchecked(value: List<UByte>): Wtf8Buf = Wtf8Buf(bytes = value, isKnownUtf8 = false)

        /**
         * Creates a WTF-8 string from a UTF-8 `String`.
         *
         * This takes ownership of the `String` and does not copy.
         *
         * Since WTF-8 is a superset of UTF-8, this always succeeds.
         */
        public fun fromString(string: String): Wtf8Buf = Wtf8Buf(bytes = string.intoBytes(), isKnownUtf8 = true)

        /**
         * Creates a WTF-8 string from a UTF-8 `String` slice.
         *
         * This copies the content of the slice.
         *
         * Since WTF-8 is a superset of UTF-8, this always succeeds.
         */
        public fun fromStr(s: String): Wtf8Buf = Wtf8Buf(bytes = (s.encodeToByteArray().map { it.toUByte() }).toMutableList(), isKnownUtf8 = true)

        /**
         * Creates a WTF-8 string from a potentially ill-formed UTF-16 slice of 16-bit code units.
         *
         * This is lossless: calling `.encodeWide()` on the resulting string
         * will always return the original code units.
         */
        public fun fromWide(v: List<UShort>): Wtf8Buf {
            val string = Wtf8Buf.withCapacity(v.size)
            for (item in Char.decodeUtf16(v.iter().cloned())) {
                when (item) { is Result.Ok<*, *> -> {
                    val ch = item.okValue()
                    string.pushChar(ch)
                }; is Result.Err<*, *> -> {
                    val surrogate = item.errValue()
                    val surrogate1 = surrogate.unpairedSurrogate()
                    // SAFETY: Surrogates are known to be in the code point range.
                    val codePoint = CodePoint.fromU32Unchecked(surrogate1.toUInt())
                    // The string will now contain an unpaired surrogate.
                    string.isKnownUtf8 = false
                    // SAFETY: `decodeUtf16` reports only unpaired surrogates here,
                    // so this code point cannot form a surrogate pair with the
                    // preceding and succeeding contents. The existing buffer is
                    // well-formed WTF-8, and appending this encoded surrogate
                    // preserves that invariant.
                    run {
                        string.pushCodePointUnchecked(codePoint)
                    }
                } }
            }
            return string
        }

        /**
         * Converts a `Holder<Wtf8>` into a `Wtf8Buf`.
         */
        public fun fromBox(boxed: Wtf8): Wtf8Buf {
            // SAFETY: `Wtf8` is a transparent wrapper around `[UByte]`, and `boxed` is
            // a `Holder<Wtf8>`. Therefore, transmuting `Holder<Wtf8>` to `Holder<[UByte]>` is safe.
            val bytes: List<UByte> = transmuteWithLayouts<Wtf8, List<UByte>>(boxed, UnresolvedTypeLayout<Wtf8>(), UnresolvedTypeLayout<List<UByte>>())
            return Wtf8Buf(bytes = bytes.intoVec(), isKnownUtf8 = false)
        }

        /**
         * Creates a new WTF-8 string from an iterator of code points.
         *
         * This replaces surrogate code point pairs with supplementary code points,
         * like concatenating ill-formed UTF-16 strings effectively would.
         */
        public fun <I : Iterable<CodePoint>> fromIter(iter: I): Wtf8Buf {
            val string = Wtf8Buf.new()
            string.extend(iter)
            return string
        }
    }
}
