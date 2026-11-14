// port-lint: source ffi/os_str.rs
package io.github.kotlinmania.rustlang.std.ffi.osstr

import io.github.kotlinmania.cmp.Ordering
import io.github.kotlinmania.ops.RangeBounds
import io.github.kotlinmania.ops.RangeFull
import io.github.kotlinmania.rustlang.alloc.borrow.Cow
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.char.isAscii
import io.github.kotlinmania.rustlang.core.char.toAsciiLowercase
import io.github.kotlinmania.rustlang.core.char.toAsciiUppercase
import io.github.kotlinmania.rustlang.core.clone.CloneToUninit
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.core.str.traits.FromStr
import io.github.kotlinmania.rustlang.std.cmp
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.ops.range
import io.github.kotlinmania.rustlang.std.rc.Rc
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.osstr.bytes.Buf
import io.github.kotlinmania.rustlang.std.sys.osstr.bytes.Slice

// The [OsStr] and [OsString] types and associated utilities.

public typealias Owned = OsString

/**
 * A type that can represent owned, mutable platform-native strings, but is
 * cheaply inter-convertible with Rust strings.
 *
 * The need for this type arises from the fact that:
 *
 * * On Unix systems, strings are often arbitrary sequences of non-zero
 *   bytes, in many cases interpreted as UTF-8.
 *
 * * On Windows, strings are often arbitrary sequences of non-zero 16-bit
 *   values, interpreted as UTF-16 when it is valid to do so.
 *
 * * In Rust, strings are always valid UTF-8, which may contain zeros.
 *
 * `OsString` and [OsStr] bridge this gap by simultaneously representing Rust
 * and platform-native string values, and in particular allowing a Rust string
 * to be converted into an "OS" string with no cost if possible. A consequence
 * of this is that `OsString` instances are *not* `NUL` terminated; in order
 * to pass to e.g., Unix system call, you should create a [CStr].
 *
 * `OsString` is to <code>&[OsStr]</code> as [String] is to <code>&[str]</code>: the former
 * in each pair are owned strings; the latter are borrowed
 * references.
 *
 * Note, `OsString` and [OsStr] internally do not necessarily hold strings in
 * the form native to the platform; While on Unix, strings are stored as a
 * sequence of 8-bit values, on Windows, where strings are 16-bit value based
 * as just discussed, strings are also actually stored as a sequence of 8-bit
 * values, encoded in a less-strict variant of UTF-8. This is useful to
 * understand when handling capacity and length values.
 *
 * # Capacity of `OsString`
 *
 * Capacity uses units of UTF-8 bytes for OS strings which were created from valid unicode, and
 * uses units of bytes in an unspecified encoding for other contents. On a given target, all
 * `OsString` and `OsStr` values use the same units for capacity, so the following will work:
 * ```kotlin
 * import std.ffi.OsStr
 * import std.ffi.OsString
 *
 * fun concatOsStrings(a: OsStr, b: OsStr): OsString {
 *     var ret = OsString.withCapacity(a.len() + b.len())
 *     // This will allocate
 *     ret.push(a)
 *     // This will not allocate further
 *     ret.push(b)
 *     // This will not allocate further
 *     return ret
 * }
 * ```
 *
 * # Creating an `OsString`
 *
 * **From a Rust string**: `OsString` implements
 * <code>[From]<[String]></code>, so you can use <code>myString.[into]\()</code> to
 * create an `OsString` from a normal Rust string.
 *
 * **From slices:** Just like you can start with an empty Rust
 * [String] and then [String.pushStr] some <code>&[str]</code>
 * sub-string slices into it, you can create an empty `OsString` with
 * the [OsString.new] method and then push string slices into it with the
 * [OsString.push] method.
 *
 * # Extracting a borrowed reference to the whole OS string
 *
 * You can use the [OsString.asOsStr] method to get an <code>&[OsStr]</code> from
 * an `OsString`; this is effectively a borrowed reference to the
 * whole string.
 *
 * # Conversions
 *
 * See the [module's toplevel documentation about conversions][conversions] for a discussion on
 * the traits which `OsString` implements for [conversions] from/to native representations.
 *
 * [conversions]: super#conversions
 * [into]: Into.into
 */
public open class OsString(
    public var inner: Buf,
) : Appendable, Borrow<OsStr>, AsRef<OsStr>, FromInner<Buf, OsString>, IntoInner<Buf>, FromStr {
    /**
     * Converts to an [OsStr] slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.ffi.OsString
     *
     * val osString = OsString.from("foo")
     * val osStr = OsStr.new("foo")
     * check(osString.asOsStr() == osStr)
     * ```
     */
    public open fun asOsStr(): OsStr = this

    /**
     * Converts the `OsString` into a byte vector.  To convert the byte vector back into an
     * `OsString`, use the [OsString.fromEncodedBytesUnchecked] function.
     *
     * The byte encoding is an unspecified, platform-specific, self-synchronizing superset of UTF-8.
     * By being a self-synchronizing superset of UTF-8, this encoding is also a superset of 7-bit
     * ASCII.
     *
     * Note: As the encoding is unspecified, any sub-slice of bytes that is not valid UTF-8 should
     * be treated as opaque and only comparable within the same Rust version built for the same
     * target platform.  For example, sending the bytes over the network or storing it in a file
     * will likely result in incompatible data.  See [OsString] for more encoding details
     * and [std.ffi] for platform-specific, specified conversions.
     */
    public open fun intoEncodedBytes(): List<UByte> = this.inner.intoEncodedBytes()

    /**
     * Converts the `OsString` into a [String] if it contains valid Unicode data.
     *
     * On failure, ownership of the original `OsString` is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * val osString = OsString.from("foo")
     * val string = osString.intoString()
     * check(string == Result.success("foo"))
     * ```
     */
    public open fun intoString(): Result<String, OsString> = this.inner.intoString().mapErr({ buf -> OsString(inner = buf) })

    /**
     * Extends the string with the given <code>&[OsStr]</code> slice.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * var osString = OsString.from("foo")
     * osString.push("bar")
     * check(osString == "foobar")
     * ```
     */
    public open fun push(s: OsStr) {
        interface SpecPushTo {
            public fun specPushTo(buf: OsString)
        }
        fun OsStr.specPushTo(buf: OsString) {
            buf.inner.pushSlice(this.asRef().inner)
        }
        fun String.specPushTo(buf: OsString) {
            buf.inner.pushStr(this)
        }
        s.specPushTo(this)
    }

    /**
     * Truncates the `OsString` to zero length.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * var osString = OsString.from("foo")
     * check(osString == "foo")
     * osString.clear()
     * check(osString == "")
     * ```
     */
    public open fun clear() {
        this.inner.clear()
    }

    /**
     * Returns the capacity this `OsString` can hold without reallocating.
     *
     * See the main `OsString` documentation information about encoding and capacity units.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * val osString = OsString.withCapacity(10)
     * check(osString.capacity() >= 10)
     * ```
     */
    public open fun capacity(): Int = this.inner.capacity()

    /**
     * Reserves capacity for at least `additional` more capacity to be inserted
     * in the given `OsString`. Does nothing if the capacity is
     * already sufficient.
     *
     * The collection may reserve more space to speculatively avoid frequent reallocations.
     *
     * See the main `OsString` documentation information about encoding and capacity units.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * var s = OsString.new()
     * s.reserve(10)
     * check(s.capacity() >= 10)
     * ```
     */
    public open fun reserve(additional: Int) {
        this.inner.reserve(additional)
    }

    /**
     * Tries to reserve capacity for at least `additional` more length units
     * in the given `OsString`. The string may reserve more space to speculatively avoid
     * frequent reallocations. After calling `tryReserve`, capacity will be
     * greater than or equal to `self.len() + additional` if it returns `Ok(())`.
     * Does nothing if capacity is already sufficient. This method preserves
     * the contents even if an error occurs.
     *
     * See the main `OsString` documentation information about encoding and capacity units.
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.TryReserveError
     * import std.ffi.OsStr
     * import std.ffi.OsString
     *
     * fun processData(data: String): Result<OsString> {
     *     var s = OsString.new()
     *     // Pre-reserve the memory, exiting if we can't
     *     s.tryReserve(OsStr.new(data).len()).getOrElse { return Result.failure(it) }
     *     // Now we know this can't OOM in the middle of our complex work
     *     s.push(data)
     *     return Result.success(s)
     * }
     * ```
     */
    public open fun tryReserve(additional: Int): Result<Unit, TryReserveError> = this.inner.tryReserve(additional)

    /**
     * Reserves the minimum capacity for at least `additional` more capacity to
     * be inserted in the given `OsString`. Does nothing if the capacity is
     * already sufficient.
     *
     * Note that the allocator may give the collection more space than it
     * requests. Therefore, capacity can not be relied upon to be precisely
     * minimal. Prefer [reserve] if future insertions are expected.
     *
     * [reserve]: OsString.reserve
     *
     * See the main `OsString` documentation information about encoding and capacity units.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * var s = OsString.new()
     * s.reserveExact(10)
     * check(s.capacity() >= 10)
     * ```
     */
    public open fun reserveExact(additional: Int) {
        this.inner.reserveExact(additional)
    }

    /**
     * Tries to reserve the minimum capacity for at least `additional`
     * more length units in the given `OsString`. After calling
     * `tryReserveExact`, capacity will be greater than or equal to
     * `self.len() + additional` if it returns `Ok(())`.
     * Does nothing if the capacity is already sufficient.
     *
     * Note that the allocator may give the `OsString` more space than it
     * requests. Therefore, capacity can not be relied upon to be precisely
     * minimal. Prefer [tryReserve] if future insertions are expected.
     *
     * [tryReserve]: OsString.tryReserve
     *
     * See the main `OsString` documentation information about encoding and capacity units.
     *
     * # Errors
     *
     * If the capacity overflows, or the allocator reports a failure, then an error
     * is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import std.collections.TryReserveError
     * import std.ffi.OsStr
     * import std.ffi.OsString
     *
     * fun processData(data: String): Result<OsString> {
     *     var s = OsString.new()
     *     // Pre-reserve the memory, exiting if we can't
     *     s.tryReserveExact(OsStr.new(data).len()).getOrElse { return Result.failure(it) }
     *     // Now we know this can't OOM in the middle of our complex work
     *     s.push(data)
     *     return Result.success(s)
     * }
     * ```
     */
    public open fun tryReserveExact(additional: Int): Result<Unit, TryReserveError> = this.inner.tryReserveExact(additional)

    /**
     * Shrinks the capacity of the `OsString` to match its length.
     *
     * See the main `OsString` documentation information about encoding and capacity units.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * var s = OsString.from("foo")
     * s.reserve(100)
     * check(s.capacity() >= 100)
     * s.shrinkToFit()
     * check(3 == s.capacity())
     * ```
     */
    public open fun shrinkToFit() {
        this.inner.shrinkToFit()
    }

    /**
     * Shrinks the capacity of the `OsString` with a lower bound.
     *
     * The capacity will remain at least as large as both the length
     * and the supplied value.
     *
     * If the current capacity is less than the lower limit, this is a no-op.
     *
     * See the main `OsString` documentation information about encoding and capacity units.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * var s = OsString.from("foo")
     * s.reserve(100)
     * check(s.capacity() >= 100)
     * s.shrinkTo(10)
     * check(s.capacity() >= 10)
     * s.shrinkTo(0)
     * check(s.capacity() >= 3)
     * ```
     */
    public open fun shrinkTo(minCapacity: Int) {
        this.inner.shrinkTo(minCapacity)
    }

    /**
     * Converts this `OsString` into a boxed [OsStr].
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.ffi.OsString
     *
     * val s = OsString.from("hello")
     * val b: OsStr = s.intoBoxedOsStr()
     * ```
     */
    public open fun intoBoxedOsStr(): OsStr {
        val rw = mutPtrFromRustCast(Box.intoRaw(this.inner.intoBox()))
        return Box.fromRaw(rw)
    }

    /**
     * Consumes and leaks the `OsString`, returning a mutable reference to the contents,
     * `OsStr`.
     *
     * The caller has free choice over the returned scope, including static.
     * Indeed, this function is ideally used for data that lives for the remainder of
     * the program’s life, as dropping the returned reference will cause a memory leak.
     *
     * It does not reallocate or shrink the `OsString`, so the leaked allocation may include
     * unused capacity that is not part of the returned slice. If you want to discard excess
     * capacity, call [intoBoxedOsStr], and then [Box.leak] instead.
     * However, keep in mind that trimming the capacity may result in a reallocation and copy.
     *
     * [intoBoxedOsStr]: Self.intoBoxedOsStr
     */
    public open fun leak(): RustMutableReference<OsStr> = OsStr.fromInnerMut(this.inner.leak())

    /**
     * Truncate the `OsString` to the specified length.
     *
     * If `newLen` is greater than or equal to the string's current length,
     * this has no effect.
     *
     * # Panics
     *
     * Panics if `len` does not lie on a valid `OsStr` boundary
     * (as described in [OsStr.sliceEncodedBytes]).
     */
    public open fun truncate(len: Int) {
        if (len >= this.len()) {
            return
        }
        this.asOsStr().inner.checkPublicBoundary(len)
        // SAFETY: The length was just checked to be at a valid boundary.
        this.inner.truncateUnchecked(len)
    }

    /**
     * Provides plumbing to `List.extendFromSlice` without giving full
     * mutable access to the `List`.
     *
     * # Safety
     *
     * The slice must be valid for the platform encoding (as described in
     * [OsStr.fromEncodedBytesUnchecked]).
     *
     * This bypasses the encoding-dependent surrogate joining, so either
     * `self` must not end with a leading surrogate half, or `other` must not
     * start with a trailing surrogate half.
     */
    internal open fun extendFromSliceUnchecked(other: List<UByte>) {
        // SAFETY: Guaranteed by caller.
        this.inner.extendFromSliceUnchecked(other)
    }

    public open fun index(index: RangeFull): OsStr = OsStr.fromInner(this.inner.asSlice())

    public open fun indexMut(index: RangeFull): RustMutableReference<OsStr> = OsStr.fromInnerMut(this.inner.asMutSlice())

    public open fun deref(): OsStr = this

    public open fun derefMut(): RustMutableReference<OsStr> = this

    public open fun clone(): OsString = OsString(inner = this.inner.clone())

    /**
     * Clones the contents of `source` into `self`.
     *
     * This method is preferred over simply assigning `source.clone()` to `self`,
     * as it avoids reallocation if possible.
     */
    public open fun cloneFrom(source: OsString) {
        this.inner = source.inner
    }

    public open fun fmt(formatter: StringBuilder) {
        formatter.append((this).toString())
    }

    public open fun eq(other: OsString): Boolean = (this == other).toUByte()

    public open fun eq(other: String): Boolean = (this == other).toUByte()

    public open fun eqString(other: String): Boolean = (this == other).toUByte()

    public open fun partialCmp(other: OsString): Ordering? = this.partialCmp(other)

    public open fun lt(other: OsString): Boolean = (this < other).toUByte()

    public open fun le(other: OsString): Boolean = (this <= other).toUByte()

    public open fun gt(other: OsString): Boolean = (this > other).toUByte()

    public open fun ge(other: OsString): Boolean = (this >= other).toUByte()

    public open fun partialCmp(other: String): Ordering? = this.partialCmp(other)

    public open fun cmp(other: OsString): Ordering = this.cmp(other)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this).hashCode()
        return state
    }

    public open fun writeStr(s: String) {
        this.push(s)
    }

    public open fun eq(other: OsStr): Boolean = OsStr.eq(this, other)

    public open fun partialCmp(other: OsStr): Ordering? = OsStr.partialCmp(this, other)

    public open fun eqOsStr(other: OsStr): Boolean = OsStr.eq(this, other)

    public open fun partialCmpOsStr(other: OsStr): Ordering? = OsStr.partialCmp(this, other)

    public open fun eqCowOsStr(other: OsStr): Boolean = OsStr.eq(this, other)

    public open fun partialCmpCowOsStr(other: OsStr): Ordering? = OsStr.partialCmp(this, other)

    public open fun borrow(): OsStr = this

    public open fun asRef(): OsStr = this

    public override fun intoInner(): Buf = this.inner

    public open fun <I : Iterable<OsString>> extend(iter: I) {
        for (s in iter) {
            this.push(s)
        }
    }

    public open fun <I : Iterable<OsStr>> extendOsStr(iter: I) {
        for (s in iter) {
            this.push(s)
        }
    }

    public open fun <I : Iterable<OsStr>> extendCowOsStr(iter: I) {
        for (s in iter) {
            this.push(s)
        }
    }

    public companion object {
        /**
         * Constructs a new empty `OsString`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.ffi.OsString
         *
         * val osString = OsString.new()
         * ```
         */
        public fun new(): OsString = OsString(inner = Buf.fromString(""))

        /**
         * Converts bytes to an `OsString` without checking that the bytes contains
         * valid [OsStr]-encoded data.
         *
         * The byte encoding is an unspecified, platform-specific, self-synchronizing superset of UTF-8.
         * By being a self-synchronizing superset of UTF-8, this encoding is also a superset of 7-bit
         * ASCII.
         *
         * See the [module's toplevel documentation about conversions][conversions] for safe,
         * cross-platform [conversions] from/to native representations.
         *
         * # Safety
         *
         * As the encoding is unspecified, callers must pass in bytes that originated as a mixture of
         * validated UTF-8 and bytes from [OsStr.asEncodedBytes] from within the same Rust version
         * built for the same target platform.  For example, reconstructing an `OsString` from bytes sent
         * over the network or stored in a file will likely violate these safety rules.
         *
         * Due to the encoding being self-synchronizing, the bytes from [OsStr.asEncodedBytes] can be
         * split either immediately before or immediately after any valid non-empty UTF-8 substring.
         *
         * # Example
         *
         * ```kotlin
         * import std.ffi.OsStr
         *
         * val osStr = OsStr.new("Mary had a little lamb")
         * val bytes = osStr.asEncodedBytes()
         * val words = bytes.split({ b -> b == 32u })
         * val words1: List<OsStr> = words.map({ word ->
         *     // SAFETY:
         *     // - Each `word` only contains content that originated from `OsStr.asEncodedBytes`
         *     // - Only split with ASCII whitespace which is a non-empty UTF-8 substring
         *     OsStr.fromEncodedBytesUnchecked(word)
         * }).toMutableList()
         * ```
         *
         * [conversions]: super#conversions
         */
        public fun fromEncodedBytesUnchecked(bytes: List<UByte>): OsString = OsString(inner = Buf.fromEncodedBytesUnchecked(bytes))

        /**
         * Creates a new `OsString` with at least the given capacity.
         *
         * The string will be able to hold at least `capacity` length units of other
         * OS strings without reallocating. This method is allowed to allocate for
         * more units than `capacity`. If `capacity` is 0, the string will not
         * allocate.
         *
         * See the main `OsString` documentation information about encoding and capacity units.
         *
         * # Examples
         *
         * ```kotlin
         * import std.ffi.OsString
         *
         * var osString = OsString.withCapacity(10)
         * val capacity = osString.capacity()
         * // This push is done without reallocating
         * osString.push("foo")
         * check(capacity == osString.capacity())
         * ```
         */
        public fun withCapacity(capacity: Int): OsString = OsString(inner = Buf.withCapacity(capacity))

        /**
         * Converts a [String] into an [OsString].
         *
         * This conversion does not allocate or copy memory.
         */
        public fun from(s: String): OsString = OsString(inner = Buf.fromString(s))

        /**
         * Copies any value implementing <code>[AsRef]&lt;[OsStr]&gt;</code>
         * into a newly allocated [OsString].
         */
        public fun from(s: OsStr): OsString {
            interface SpecToOsString {
                public fun specToOsString(): OsString
            }
            fun OsStr.specToOsString(): OsString = this.asRef().toOsString()
            fun String.specToOsString(): OsString = OsString.from(this)
            return s.specToOsString()
        }

        /**
         * Constructs an empty `OsString`.
         */
        public fun default(): OsString = OsString.new()

        /**
         * Converts a <code>[Holder]<[OsStr]></code> into an [OsString] without copying or
         * allocating.
         */
        public fun fromBoxOsStr(boxed: OsStr): OsString = boxed.intoOsString()

        /**
         * Converts a `Cow<OsStr>` into an [OsString],
         * by copying the contents if they are borrowed.
         */
        public fun fromCowOsStr(s: OsStr): OsString = s.intoOwned()

        public fun fromInner(buf: Buf): OsString = OsString(inner = buf)

        public fun fromStr(s: String): Result<OsString, Nothing> = Result.Ok(OsString.from(s))

        public fun <I : Iterable<OsString>> fromIter(iter: I): OsString {
            val iterator = iter.iterator()
            // Because we're iterating over `OsString`s, we can avoid at least
            // one allocation by getting the first string from the iterator
            // and appending to it all the subsequent strings.
            return when (val matchVal = iterator.next()) {
                null -> OsString.new()
                else -> {
                    val buf = matchVal
                    buf.extend(iterator)
                    buf
                }
            }
        }

        public fun <I : Iterable<OsStr>> fromIterOsStr(iter: I): OsString {
            val buf = OsString.new()
            for (s in iter) {
                buf.push(s)
            }
            return buf
        }

        public fun <I : Iterable<OsStr>> fromIterCowOsStr(iter: I): OsString {
            val iterator = iter.iterator()
            // Because we're iterating over `OsString`s, we can avoid at least
            // one allocation by getting the first owned string from the iterator
            // and appending to it all the subsequent strings.
            return when (val matchVal = iterator.next()) {
                null -> OsString.new()
                is Cow.Owned -> {
                    val (buf) = matchVal as Cow.Owned
                    buf.extend(iterator)
                    buf
                }
                is Cow.Borrowed -> {
                    val (buf1) = matchVal as Cow.Borrowed
                    var buf2 = OsString.from(buf1)
                    buf2.extend(iterator)
                    buf2
                }
            }
        }
    }
}

// `OsStr.fromInner` and `implementation CloneToUninit for OsStr` current implementation relies
// on `OsStr` being layout-compatible with `Slice`.
// However, `OsStr` layout is considered an implementation detail and must not be relied upon.

/**
 * Borrowed reference to an OS string (see [OsString]).
 *
 * This type represents a borrowed reference to a string in the operating system's preferred
 * representation.
 *
 * `OsStr` is to [OsString] as <code>&[str]</code> is to [String]: the
 * former in each pair are borrowed references; the latter are owned strings.
 *
 * See the [module's toplevel documentation about conversions][conversions] for a discussion on
 * the traits which `OsStr` implements for [conversions] from/to native representations.
 *
 * [conversions]: super#conversions
 */
public open class OsStr internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<Slice>,
) : CloneToUninit, ToOwned, AsRef<OsStr>, AsInner<Slice> {
    public var inner: Slice
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(inner: Slice, fieldLayout: TypeLayout<Slice>) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<OsStr> = TransparentTypeLayout(fieldLayout, { ptr, layout -> OsStr(ptr, layout) }, { it.inner })

    /**
     * Yields a <code>&[str]</code> slice if the `OsStr` is valid Unicode.
     *
     * This conversion may entail doing a check for UTF-8 validity.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     *
     * val osStr = OsStr.new("foo")
     * check(osStr.toStr() == "foo")
     * ```
     */
    public open fun toStr(): String? = this.inner.toStr().ok()

    /**
     * Converts an `OsStr` to a <code>[Cow]<[str]></code>.
     *
     * Any non-UTF-8 sequences are replaced with
     * [`U.Fffd.Replacement.Character`][U+FFFD].
     *
     * [U+FFFD]: char.ReplacementCharacter
     *
     * # Examples
     *
     * Calling `toStringLossy` on an `OsStr` with invalid unicode:
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.ffi.OsString
     * import std.os.unix.ffi.OsStrExt
     * import std.os.windows.prelude.*
     *
     * // Note, due to differences in how Unix and Windows represent strings,
     * // we are forced to complicate this example, setting up example `OsStr`s
     * // with different source data and via different platform extensions.
     * // Understand that in reality you could end up with such example invalid
     * // sequences simply through collecting user command line arguments, for
     * // example.
     * run {
     *     // Here, the values 0x66 and 0x6f correspond to 'f' and 'o'
     *     // respectively. The value 0x80 is a lone continuation byte, invalid
     *     // in a UTF-8 sequence.
     *     val source = listOf(0x66, 0x6f, 0x80, 0x6f)
     *     val osStr = OsStr.fromBytes(source)
     *     check(osStr.toStringLossy() == "fo�o")
     * }
     * run {
     *     // Here the values 0x0066 and 0x006f correspond to 'f' and 'o'
     *     // respectively. The value 0xD800 is a lone surrogate half, invalid
     *     // in a UTF-16 sequence.
     *     val source1 = listOf(0x0066, 0x006f, 0xD800, 0x006f)
     *     val osString = OsString.fromWide(source1)
     *     val osStr1 = osString.asOsStr()
     *     check(osStr1.toStringLossy() == "fo�o")
     * }
     * ```
     */
    public open fun toStringLossy(): String = this.inner.toStringLossy()

    /**
     * Copies the slice into an owned [OsString].
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.ffi.OsString
     *
     * val osStr = OsStr.new("foo")
     * val osString = osStr.toOsString()
     * check(osString == OsString.from("foo"))
     * ```
     */
    public open fun toOsString(): OsString = OsString(inner = this.inner.toOwned())

    /**
     * Checks whether the `OsStr` is empty.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     *
     * val osStr = OsStr.new("")
     * check(osStr.isEmpty())
     * val osStr1 = OsStr.new("foo")
     * check(!osStr1.isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean = this.inner.inner.isEmpty()

    /**
     * Returns the length of this `OsStr`.
     *
     * Note that this does **not** return the number of bytes in the string in
     * OS string form.
     *
     * The length returned is that of the underlying storage used by `OsStr`.
     * As discussed in the [OsString] introduction, [OsString] and `OsStr`
     * store strings in a form best suited for cheap inter-conversion between
     * native-platform and Rust string forms, which may differ significantly
     * from both of them, including in storage size and encoding.
     *
     * This number is simply useful for passing to other methods, like
     * [OsString.withCapacity] to avoid reallocations.
     *
     * See the main `OsString` documentation information about encoding and capacity units.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     *
     * val osStr = OsStr.new("")
     * check(osStr.len() == 0)
     * val osStr1 = OsStr.new("foo")
     * check(osStr1.len() == 3)
     * ```
     */
    public open fun len(): Int = this.inner.inner.length

    /**
     * Converts a <code>[Holder]<[OsStr]></code> into an [OsString] without copying or allocating.
     */
    public open fun intoOsString(): OsString {
        val boxed = Box.fromRaw(mutPtrFromRustCast(Box.intoRaw(this)))
        return OsString(inner = Buf.fromBox(boxed))
    }

    /**
     * Divides one string slice into two at an index.
     *
     * The two slices returned go from the start of the string slice to `mid`, and from `mid` to the end of the string slice.
     *
     * The argument, `mid`, should be a byte offset from the start of the string.
     * It must also be on a valid `OsStr` boundary.
     * See [`splitAtChecked`][Self.splitAtChecked] for the definition of a valid boundary.
     *
     * Panics
     *
     * Panics if `mid` is not on a valid boundary, or if it is past the end of the last code point of the string slice.
     * For a non-panicking alternative see [`splitAtChecked`][Self.splitAtChecked].
     */
    public open fun splitAt(mid: Int): Pair<OsStr, OsStr> {
        this.inner.checkPublicBoundary(mid)
        // SAFETY: we've checked it's in bounds and a valid boundary
        return this.splitAtUnchecked(mid)
    }

    /**
     * Divides one string slice into two at an index.
     *
     * The two slices returned go from the start of the string slice to `mid`, and from `mid` to the end of the string slice.
     *
     * The argument, `mid`, should be a valid byte offset from the start of the string.
     * It must also be on a valid `OsStr` boundary.
     * The method returns `None` if that’s not the case.
     * A valid `OsStr` boundary is one of:
     * - The start of the string
     * - The end of the string
     * - The start of a valid non-empty UTF-8 substring
     * - Immediately follows a valid non-empty UTF-8 substring
     */
    public open fun splitAtChecked(mid: Int): Pair<OsStr, OsStr>? {
        (this.inner.tryCheckPublicBoundary(mid) ?: return null)
        // SAFETY: we've checked it's in bounds and a valid boundary
        return this.splitAtUnchecked(mid)
    }

    /**
     * Splits an `OsStr` without checking if `mid` is a valid boundary.
     * You should use `splitAt` or `splitAtChecked` instead.
     *
     * # Safety
     *
     * Any caller must ensure `mid` is within bounds and lies on
     * a valid `OsStr` boundary for the platform.
     */
    public open fun splitAtUnchecked(mid: Int): Pair<OsStr, OsStr> {
        check(this.inner.tryCheckPublicBoundary(mid) != null) { "OsStr::split_at_unchecked requires the index to be on a valid `OsStr` boundary" }
        // SAFETY: it's up to the caller to ensure this is safe.
        return run {
            val (first, second) = this.asEncodedBytes().splitAtUnchecked(mid)
            Pair(OsStr.fromEncodedBytesUnchecked(first), OsStr.fromEncodedBytesUnchecked(second))
        }
    }

    /**
     * Converts an OS string slice to a byte slice.  To convert the byte slice back into an OS
     * string slice, use the [OsStr.fromEncodedBytesUnchecked] function.
     *
     * The byte encoding is an unspecified, platform-specific, self-synchronizing superset of UTF-8.
     * By being a self-synchronizing superset of UTF-8, this encoding is also a superset of 7-bit
     * ASCII.
     *
     * Note: As the encoding is unspecified, any sub-slice of bytes that is not valid UTF-8 should
     * be treated as opaque and only comparable within the same Rust version built for the same
     * target platform.  For example, sending the slice over the network or storing it in a file
     * will likely result in incompatible byte slices.  See [OsString] for more encoding details
     * and [std.ffi] for platform-specific, specified conversions.
     */
    public open fun asEncodedBytes(): List<UByte> = this.inner.asEncodedBytes()

    /**
     * Takes a substring based on a range that corresponds to the return value of
     * [OsStr.asEncodedBytes].
     *
     * The range's start and end must lie on valid `OsStr` boundaries.
     * A valid `OsStr` boundary is one of:
     * - The start of the string
     * - The end of the string
     * - Immediately before a valid non-empty UTF-8 substring
     * - Immediately after a valid non-empty UTF-8 substring
     *
     * # Panics
     *
     * Panics if `range` does not lie on valid `OsStr` boundaries or if it
     * exceeds the end of the string.
     *
     * # Example
     *
     * ```kotlin
     * import std.ffi.OsStr
     *
     * @feature(osStrSlice)
     * public fun doctestEntry() {
     *     val osStr = OsStr.new("foo=bar")
     *     val bytes = osStr.asEncodedBytes()
     *     when (val matchVal = bytes.iter().indexOfFirst({ b -> b == 61u }).takeIf { it >= 0 }) {
     *         null -> {}
     *         else -> {
     *             val index = matchVal
     *             val key = osStr.sliceEncodedBytes(0 until index)
     *             val value = osStr.sliceEncodedBytes(index + 1 until Int.MAX_VALUE)
     *             check(key == "foo")
     *             check(value == "bar")
     *         }
     *     }
     * }
     * ```
     */
    public open fun <R : RangeBounds<Int>> sliceEncodedBytes(range: R): OsStr {
        val encodedBytes = this.asEncodedBytes()
        val structVal = Slice.range(range, 0 until encodedBytes.size)
        val start = structVal.start
        val end = structVal.end
        // `checkPublicBoundary` should panic if the index does not lie on an
        // `OsStr` boundary as described above. It's possible to do this in an
        // encoding-agnostic way, but details of the internal encoding might
        // permit a more efficient implementation.
        this.inner.checkPublicBoundary(start)
        this.inner.checkPublicBoundary(end)
        // SAFETY: `slice.range` ensures that `start` and `end` are valid
        val slice = encodedBytes.getUnchecked(start until end)
        // SAFETY: `slice` comes from `self` and we validated the boundaries
        return OsStr.fromEncodedBytesUnchecked(slice)
    }

    /**
     * Converts this string to its ASCII lower case equivalent in-place.
     *
     * ASCII letters 'A' to 'Z' are mapped to 'a' to 'z',
     * but non-ASCII letters are unchanged.
     *
     * To return a new lowercased value without modifying the existing one, use
     * [OsStr.toAsciiLowercase].
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * var s = OsString.from("GRÜßE, JÜRGEN ❤")
     * s.makeAsciiLowercase()
     * check("grÜße, jÜrgen ❤" == s)
     * ```
     */
    public open fun makeAsciiLowercase() {
        this.inner.makeAsciiLowercase()
    }

    /**
     * Converts this string to its ASCII upper case equivalent in-place.
     *
     * ASCII letters 'a' to 'z' are mapped to 'A' to 'Z',
     * but non-ASCII letters are unchanged.
     *
     * To return a new uppercased value without modifying the existing one, use
     * [OsStr.toAsciiUppercase].
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * var s = OsString.from("Grüße, Jürgen ❤")
     * s.makeAsciiUppercase()
     * check("GRüßE, JüRGEN ❤" == s)
     * ```
     */
    public open fun makeAsciiUppercase() {
        this.inner.makeAsciiUppercase()
    }

    /**
     * Returns a copy of this string where each character is mapped to its
     * ASCII lower case equivalent.
     *
     * ASCII letters 'A' to 'Z' are mapped to 'a' to 'z',
     * but non-ASCII letters are unchanged.
     *
     * To lowercase the value in-place, use [OsStr.makeAsciiLowercase].
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * val s = OsString.from("Grüße, Jürgen ❤")
     * check("grüße, jürgen ❤" == s.toAsciiLowercase())
     * ```
     */
    public open fun toAsciiLowercase(): OsString = OsString.fromInner(this.inner.toAsciiLowercase())

    /**
     * Returns a copy of this string where each character is mapped to its
     * ASCII upper case equivalent.
     *
     * ASCII letters 'a' to 'z' are mapped to 'A' to 'Z',
     * but non-ASCII letters are unchanged.
     *
     * To uppercase the value in-place, use [OsStr.makeAsciiUppercase].
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * val s = OsString.from("Grüße, Jürgen ❤")
     * check("GRüßE, JüRGEN ❤" == s.toAsciiUppercase())
     * ```
     */
    public open fun toAsciiUppercase(): OsString = OsString.fromInner(this.inner.toAsciiUppercase())

    /**
     * Checks if all characters in this string are within the ASCII range.
     *
     * An empty string returns `true`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * val ascii = OsString.from("hello!\n")
     * val nonAscii = OsString.from("Grüße, Jürgen ❤")
     * check(ascii.isAscii())
     * check(!nonAscii.isAscii())
     * ```
     */
    public open fun isAscii(): Boolean = this.inner.isAscii()

    /**
     * Checks that two strings are an ASCII case-insensitive match.
     *
     * Same as `toAsciiLowercase(a) == toAsciiLowercase(b)`,
     * but without allocating and copying temporaries.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsString
     *
     * check(OsString.from("Ferris").eqIgnoreAsciiCase("FERRIS"))
     * check(OsString.from("Ferrös").eqIgnoreAsciiCase("FERRöS"))
     * check(!OsString.from("Ferrös").eqIgnoreAsciiCase("FERRÖS"))
     * ```
     */
    public open fun eqIgnoreAsciiCase(other: OsStr): Boolean = this.inner.eqIgnoreAsciiCase(other.inner)

    /**
     * Returns an object that implements [Display] for safely printing an
     * [OsStr] that may contain non-Unicode data. This may perform lossy
     * conversion, depending on the platform.  If you would like an
     * implementation which escapes the [OsStr] please use [Debug]
     * instead.
     *
     * [Display]: fmt.Display
     * [Debug]: fmt.Debug
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     *
     * val s = OsStr.new("Hello, world!")
     * println("${s.display()}")
     * ```
     */
    public open fun display(): Display = Display(osStr = this)

    /**
     * Returns the same string as a string slice `OsStr`.
     *
     * This method is redundant when used directly on `OsStr`, but
     * it helps dereferencing other string-like types to string slices,
     * for example references to `Holder<OsStr>` or `Arc<OsStr>`.
     */
    public open fun asOsStr(): OsStr = this

    public open fun clone(): OsStr = Box.cloneFromRefIn(OsStr(this.inner), OsStr.allocator(this).clone())

    public open fun cloneToUninit(dst: MutPtr) {
        // SAFETY: we're just a transparent wrapper around a platform-specific Slice
        this.inner.cloneToUninit(dst)
    }

    public open fun eq(other: OsStr): Boolean = this.asEncodedBytes().eq(other.asEncodedBytes())

    public open fun eq(other: String): Boolean = (this == OsStr.new(other)).toUByte()

    public open fun partialCmp(other: OsStr): Ordering? = this.asEncodedBytes().partialCmp(other.asEncodedBytes())

    public open fun lt(other: OsStr): Boolean = this.asEncodedBytes().lt(other.asEncodedBytes())

    public open fun le(other: OsStr): Boolean = this.asEncodedBytes().le(other.asEncodedBytes())

    public open fun gt(other: OsStr): Boolean = this.asEncodedBytes().gt(other.asEncodedBytes())

    public open fun ge(other: OsStr): Boolean = this.asEncodedBytes().ge(other.asEncodedBytes())

    public open fun partialCmp(other: String): Ordering? = this.partialCmp(OsStr.new(other))

    // Note (#19470): cannot provide PartialOrd<OsStr> for str until we
    // have more flexible coherence rules.
    public open fun cmp(other: OsStr): Ordering = this.asEncodedBytes().cmp(other.asEncodedBytes())

    public open fun eq(other: OsString): Boolean = OsStr.eq(this, other)

    public open fun partialCmp(other: OsString): Ordering? = OsStr.partialCmp(this, other)

    public open fun eqCowOsStr(other: OsStr): Boolean = OsStr.eq(this, other)

    public open fun partialCmpCowOsStr(other: OsStr): Ordering? = OsStr.partialCmp(this, other)

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.asEncodedBytes()).hashCode()
        return state
    }

    public open fun fmt(formatter: StringBuilder) {
        formatter.append((this.inner).toString())
    }

    public open fun toOwned(): OsString = this.toOsString()

    public open fun cloneInto(target: OsString) {
        this.inner.cloneInto(target.inner)
    }

    public open fun asRef(): OsStr = this

    public override fun asInner(): Slice = this.inner

    public companion object {
        public operator fun invoke(inner: Slice, fieldLayout: TypeLayout<Slice>): OsStr = OsStr(inner, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<Slice>): OsStr = OsStr(fieldStorage, fieldLayout)

        /**
         * Coerces into an `OsStr` slice.
         *
         * # Examples
         *
         * ```kotlin
         * import std.ffi.OsStr
         *
         * val osStr = OsStr.new("foo")
         * ```
         */
        public fun new(s: OsStr): OsStr = s.asRef()

        /**
         * Converts a slice of bytes to an OS string slice without checking that the string contains
         * valid `OsStr`-encoded data.
         *
         * The byte encoding is an unspecified, platform-specific, self-synchronizing superset of UTF-8.
         * By being a self-synchronizing superset of UTF-8, this encoding is also a superset of 7-bit
         * ASCII.
         *
         * See the [module's toplevel documentation about conversions][conversions] for safe,
         * cross-platform [conversions] from/to native representations.
         *
         * # Safety
         *
         * As the encoding is unspecified, callers must pass in bytes that originated as a mixture of
         * validated UTF-8 and bytes from [OsStr.asEncodedBytes] from within the same Rust version
         * built for the same target platform.  For example, reconstructing an `OsStr` from bytes sent
         * over the network or stored in a file will likely violate these safety rules.
         *
         * Due to the encoding being self-synchronizing, the bytes from [OsStr.asEncodedBytes] can be
         * split either immediately before or immediately after any valid non-empty UTF-8 substring.
         *
         * # Example
         *
         * ```kotlin
         * import std.ffi.OsStr
         *
         * val osStr = OsStr.new("Mary had a little lamb")
         * val bytes = osStr.asEncodedBytes()
         * val words = bytes.split({ b -> b == 32u })
         * val words1: List<OsStr> = words.map({ word ->
         *     // SAFETY:
         *     // - Each `word` only contains content that originated from `OsStr.asEncodedBytes`
         *     // - Only split with ASCII whitespace which is a non-empty UTF-8 substring
         *     OsStr.fromEncodedBytesUnchecked(word)
         * }).toMutableList()
         * ```
         *
         * [conversions]: super#conversions
         */
        public fun fromEncodedBytesUnchecked(bytes: List<UByte>): OsStr = OsStr.fromInner(Slice.fromEncodedBytesUnchecked(bytes))

        public fun fromInner(inner: Slice): OsStr {
            // SAFETY: OsStr is just a wrapper of Slice,
            // therefore converting &Slice to &OsStr is safe.
            return (ObjectSlotLayout<OsStr>()).readNative((((constPtrFromRustCast(inner)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
        }

        public fun fromInnerMut(inner: Slice): RustMutableReference<OsStr> {
            // SAFETY: OsStr is just a wrapper of Slice,
            // therefore converting Slice to OsStr is safe.
            // Any method that mutates OsStr must be careful not to
            // break platform-specific encoding, in particular Wtf8 on Windows.
            return RustMutableReference(MutPtr((((mutPtrFromRustCast(inner)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress), ObjectSlotLayout<OsStr>())
        }

        /**
         * Copies the string into a newly allocated <code>[Holder]&lt;[OsStr]&gt;</code>.
         */
        public fun from(s: OsStr): OsStr = Box.cloneFromRef(s)

        /**
         * Copies the string into a newly allocated <code>[Holder]&lt;[OsStr]&gt;</code>.
         */
        public fun from(s: RustMutableReference<OsStr>): OsStr = OsStr.from((s).read())

        /**
         * Converts a `Cow<OsStr>` into a <code>[Holder]&lt;[OsStr]&gt;</code>,
         * by copying the contents if they are borrowed.
         */
        public fun fromCowOsStr(cow: OsStr): OsStr =
            when (cow) {
                is Cow.Borrowed -> {
                    val (s) = cow
                    Box.from(s)
                }
                is Cow.Owned -> {
                    val (s1) = cow
                    Box.from(s1)
                }
            }

        /**
         * Converts an [OsString] into a <code>[Holder]<[OsStr]></code> without copying or allocating.
         */
        public fun from(s: OsString): OsStr = s.intoBoxedOsStr()

        public fun default(): OsStr {
            val rw = mutPtrFromRustCast(Box.intoRaw(Slice.emptyBox()))
            return Box.fromRaw(rw)
        }
    }
}

public fun String.eq(other: OsString): Boolean = (other == this).toUByte()

public fun String.eqOsString(other: OsString): Boolean = (other == this).toUByte()

/**
 * Converts an [OsString] into an <code>[Arc]<[OsStr]></code> by moving the [OsString]
 * data into a new [Arc] buffer.
 */
public fun fromArcOsStr(s: OsString): OsStr {
    val arc = s.inner.intoArc()
    return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
}

/**
 * Copies the string into a newly allocated <code>[Arc]&lt;[OsStr]&gt;</code>.
 */
public fun fromArcOsStr(s: OsStr): OsStr {
    val arc = s.inner.intoArc()
    return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
}

/**
 * Copies the string into a newly allocated <code>[Arc]&lt;[OsStr]&gt;</code>.
 */
public fun fromArcOsStr(s: RustMutableReference<OsStr>): OsStr = Arc.from((s).read())

/**
 * Converts an [OsString] into an <code>[Rc]<[OsStr]></code> by moving the [OsString]
 * data into a new [Rc] buffer.
 */
public fun fromRcOsStr(s: OsString): OsStr {
    val rc = s.inner.intoRc()
    return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
}

/**
 * Copies the string into a newly allocated <code>[Rc]&lt;[OsStr]&gt;</code>.
 */
public fun fromRcOsStr(s: OsStr): OsStr {
    val rc = s.inner.intoRc()
    return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
}

/**
 * Copies the string into a newly allocated <code>[Rc]&lt;[OsStr]&gt;</code>.
 */
public fun fromRcOsStr(s: RustMutableReference<OsStr>): OsStr = Rc.from((s).read())

/**
 * Moves the string into a [Cow.Owned].
 */
public fun fromCowOsStr(s: OsString): OsStr = s

/**
 * Converts the string reference into a [Cow.Borrowed].
 */
public fun fromCowOsStr(s: OsStr): OsStr = s

/**
 * Tries to convert an `OsStr` to a `String`.
 *
 * ```kotlin
 * import std.ffi.OsStr
 *
 * val osStr = OsStr.new("foo")
 * val asStr = String.tryFrom(osStr).unwrap()
 * check(asStr == "foo")
 * ```
 */
public fun tryFromString(value: OsStr): Result<String, Utf8Error> = value.inner.toStr()

public fun String.eq(other: OsStr): Boolean = (other == OsStr.new(this)).toUByte()

public fun OsStr.eq(other: OsStr): Boolean = OsStr.eq(this, other)

public fun OsStr.partialCmp(other: OsStr): Ordering? = OsStr.partialCmp(this, other)

public fun OsStr.eqOsStr(other: OsStr): Boolean = OsStr.eq(this, other)

public fun OsStr.partialCmpOsStr(other: OsStr): Ordering? = OsStr.partialCmp(this, other)

public fun OsStr.eq(other: OsString): Boolean = OsStr.eq(this, other)

public fun OsStr.partialCmp(other: OsString): Ordering? = OsStr.partialCmp(this, other)

/**
 * Helper struct for safely printing an [OsStr] with [format] and `{}`.
 *
 * An [OsStr] might contain non-Unicode data. This `class` implements the
 * [Display] trait in a way that mitigates that. It is created by the
 * [`display`](OsStr.display) method on [OsStr]. This may perform lossy
 * conversion, depending on the platform. If you would like an implementation
 * which escapes the [OsStr] please use [Debug] instead.
 *
 * # Examples
 *
 * ```kotlin
 * import std.ffi.OsStr
 *
 * val s = OsStr.new("Hello, world!")
 * println("${s.display()}")
 * ```
 *
 * [Display]: fmt.Display
 */
public open class Display(
    public var osStr: OsStr,
) {
    public open fun fmt(f: StringBuilder) {
        f.append((this.osStr).toString())
    }

    public open fun fmtDisplay(f: StringBuilder) {
        f.append((this.osStr.inner).toString())
    }
}

public fun joinSSlice(slice: List<S>, sep: OsStr): OsString {
    val (first, suffix) = slice.splitFirst() ?: return OsString.new()
    val firstOwned = first.borrow().toOwned()
    return suffix.iter().fold(firstOwned, { a, b ->
        a.push(sep)
        a.push(b.borrow())
        a
    })
}

public fun String.asRef(): OsStr = OsStr.fromInner(Slice.fromStr(this))

public fun String.asRefOsStr(): OsStr = this
