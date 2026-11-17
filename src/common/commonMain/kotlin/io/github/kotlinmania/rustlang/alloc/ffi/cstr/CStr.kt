// port-lint: source ffi/c_str.rs
package io.github.kotlinmania.rustlang.alloc.ffi.cstr

import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.rcs.arc.Arc
import io.github.kotlinmania.rustlang.alloc.rcs.rc.Rc
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.replace
import io.github.kotlinmania.rustlang.core.num.checkedAdd
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroU8Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ops.range.RangeFull
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.core.str.traits.FromStr

// [CString] and its related types.

public typealias Owned = CString

/**
 * A type representing an owned, C-compatible, nul-terminated string with no nul bytes in the
 * middle.
 *
 * This type serves the purpose of being able to safely generate a
 * C-compatible string from a Rust byte slice or vector. An instance of this
 * type is a static guarantee that the underlying bytes contain no interior 0
 * bytes ("nul characters") and that the final byte is 0 ("nul terminator").
 *
 * `CString` is to <code>&[CStr]</code> as [String] is to <code>&[str]</code>: the former
 * in each pair are owned strings; the latter are borrowed
 * references.
 *
 * # Creating a `CString`
 *
 * A `CString` is created from either a byte slice or a byte vector,
 * or anything that implements <code>[Into]<[Vec]<[u8]>></code> (for
 * example, you can build a `CString` straight out of a [String] or
 * a <code>&[str]</code>, since both implement that trait).
 * You can create a `CString` from a literal with `CString.from(c"Text")`.
 *
 * The [CString.new] method will actually check that the provided <code>&[[u8]]</code>
 * does not have 0 bytes in the middle, and return an error if it
 * finds one.
 *
 * # Extracting a raw pointer to the whole C string
 *
 * `CString` implements an [`asPtr`][CStr.asPtr] method through the [Deref]
 * trait. This method will give you a `* const cChar` which you can
 * feed directly to extern functions that expect a nul-terminated
 * string, like C's `strdup()`. Notice that [`asPtr`][CStr.asPtr] returns a
 * read-only pointer; if the C code writes to it, that causes
 * undefined behavior.
 *
 * # Extracting a slice of the whole C string
 *
 * Alternatively, you can obtain a <code>&[[u8]]</code> slice from a
 * `CString` with the [CString.asBytes] method. Slices produced in this
 * way do *not* contain the trailing nul terminator. This is useful
 * when you will be calling an extern function that takes a `*const
 * u8` argument which is not necessarily nul-terminated, plus another
 * argument with the length of the string — like C's `strndup()`.
 * You can of course get the slice's length with its
 * [`len`][slice.len] method.
 *
 * If you need a <code>&[[u8]]</code> slice *with* the nul terminator, you
 * can use [CString.asBytesWithNul] instead.
 *
 * Once you have the kind of slice you need (with or without a nul
 * terminator), you can call the slice's own
 * [`asPtr`][slice.asPtr] method to get a read-only raw pointer to pass to
 * extern functions. See the documentation for that function for a
 * discussion on ensuring the scope of the raw pointer.
 *
 * [str]: prim@str "str"
 * [Deref]: ops.Deref
 *
 * # Examples
 *
 * ```ignore (extern-declaration)
 * # fn main() {
 * use std.ffi.CString;
 * use std.os.raw.cChar;
 *
 * extern "C" {
 *     fn myPrinter(s: *const cChar);
 * }
 *
 * // We are certain that our string doesn't have 0 bytes in the middle,
 * // so we can .expect()
 * let cToPrint = CString.new("Hello, world!").expect("we provided a string without NUL bytes, so CString.new should not fail");
 * unchecked {
 *     myPrinter(cToPrint.asPtr());
 * }
 * # }
 * ```
 *
 * # Safety
 *
 * `CString` is intended for working with traditional C-style strings
 * (a sequence of non-nul bytes terminated by a single nul byte); the
 * primary use case for these kinds of strings is interoperating with C-like
 * code. Often you will need to transfer ownership to/from that external
 * code. It is strongly recommended that you thoroughly read through the
 * documentation of `CString` before use, as improper ownership management
 * of `CString` instances can lead to invalid memory accesses, memory leaks,
 * and other memory errors.
 */
public open class CString(
    // Invariant 1: the slice ends with a zero byte and has a length of at least one.
    // Invariant 2: the slice contains only one zero byte.
    // Improper usage of unchecked function can break Invariant 2, but not Invariant 1.
    public var inner: List<UByte>,
) : Borrow<CStr>, FromStr<NulError>, AsRef<CStr> {
    public open fun clone(): CString = CString(inner)

    /**
     * Consumes the `CString` and transfers ownership of the string to a C caller.
     *
     * The pointer which this function returns must be returned to Rust and reconstituted using
     * [CString.fromRaw] to be properly deallocated. Specifically, one
     * should *not* use the standard C `free()` function to deallocate
     * this string.
     *
     * Failure to call [CString.fromRaw] will lead to a memory leak.
     *
     * The C side must **not** modify the length of the string (by writing a
     * nul byte somewhere inside the string or removing the final one) before
     * it makes it back into Rust using [CString.fromRaw]. See the safety section
     * in [CString.fromRaw].
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     *
     * val cString = CString.from("foo")
     * val ptr = cString.intoRaw()
     * run {
     *     check(102u == ptr.toUByte())
     *     check(111u == ptr.add(1).toUByte())
     *     check(111u == ptr.add(2).toUByte())
     *     check(0u == ptr.add(3).toUByte())
     *     // retake pointer to free memory
     *     CString.fromRaw(ptr)
     * }
     * ```
     */
    public open fun intoRaw(): MutPtr = mutPtrFromRustCast(Box.intoRaw(this.intoInner()))

    /**
     * Converts the `CString` into a [String] if it contains valid UTF-8 data.
     *
     * On failure, ownership of the original `CString` is returned.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     *
     * val validUtf8 = mutableListOf(102u, 111u, 111u)
     * val cstring = CString.new(validUtf8).expect("we provided bytes that do not have a NUL byte, so CString.new should not fail")
     * check(cstring.intoString().expect("we provided bytes that are valid UTF-8, so `intoString` should not fail") == "foo")
     * val invalidUtf8 = mutableListOf(102u, 0xff, 111u, 111u)
     * val cstring1 = CString.new(invalidUtf8).expect("we provided bytes that do not have a NUL byte, so CString.new should not fail")
     * val err = cstring1.intoString().expectErr("we provided bytes that are invalid UTF-8, so `intoString` should fail")
     * check(err.utf8Error().validUpTo() == 1)
     * ```
     */
    public open fun intoString(): Result<String, IntoStringError> = String.fromUtf8(this.intoBytes()).mapErr({ e -> IntoStringError(error = e.utf8Error(), inner = CString.fromVecUnchecked(e.intoBytes())) })

    /**
     * Consumes the `CString` and returns the underlying byte buffer.
     *
     * The returned buffer does **not** contain the trailing nul
     * terminator, and it is guaranteed to not have any interior nul
     * bytes.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     *
     * val cString = CString.from("foo")
     * val bytes = cString.intoBytes()
     * check(bytes == mutableListOf(102u, 111u, 111u))
     * ```
     */
    public open fun intoBytes(): List<UByte> {
        val vec = this.intoInner().intoVec()
        val nul = vec.pop()
        check(nul == 0u.toUByte())
        return vec
    }

    /**
     * Equivalent to [CString.intoBytes] except that the
     * returned vector includes the trailing nul terminator.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     *
     * val cString = CString.from("foo")
     * val bytes = cString.intoBytesWithNul()
     * check(bytes == mutableListOf(102u, 111u, 111u, 0u))
     * ```
     */
    public open fun intoBytesWithNul(): List<UByte> = this.intoInner().intoVec()

    /**
     * Returns the contents of this `CString` as a slice of bytes.
     *
     * The returned slice does **not** contain the trailing nul
     * terminator, and it is guaranteed to not have any interior nul
     * bytes. If you need the nul terminator, use
     * [CString.asBytesWithNul] instead.
     *
     * # Examples
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     *
     * public fun doctestEntry() {
     *     val cString = CString.from("foo")
     *     val bytes = cString.encodeToByteArray().map { it.toUByte() }
     *     check(bytes == ubyteArrayOf(102u, 111u, 111u))
     * }
     * ```
     */
    public open fun asBytes(): List<UByte> {
        // SAFETY: CString has a length at least 1
        return this.inner.getUnchecked(0 until this.inner.size - 1)
    }

    /**
     * Equivalent to [CString.asBytes] except that the
     * returned slice includes the trailing nul terminator.
     *
     * # Examples
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     *
     * public fun doctestEntry() {
     *     val cString = CString.from("foo")
     *     val bytes = cString.asBytesWithNul()
     *     check(bytes == ubyteArrayOf(102u, 111u, 111u, 0u))
     * }
     * ```
     */
    public open fun asBytesWithNul(): List<UByte> = this.inner

    /**
     * Extracts a [CStr] slice containing the entire string.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     * import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
     *
     * val cString = CString.from("foo")
     * val cstr = cString.asCStr()
     * check(cstr == CStr.fromBytesWithNul("foo\u0000".encodeToByteArray()).expect("we provided bytes that has one NUL byte exactly at the end, so CStr.fromBytesWithNul should not fail"))
     * ```
     */
    public open fun asCStr(): CStr {
        // SAFETY: Ensured by `asBytesWithNul`.
        return CStr.fromBytesWithNulUnchecked(this.asBytesWithNul())
    }

    /**
     * Converts this `CString` into a boxed [CStr].
     *
     * # Examples
     *
     * ```kotlin
     * val cString = "foo".toOwned()
     * val boxed = cString.intoBoxedCStr()
     * check(boxed.toBytesWithNul() == "foo\u0000".encodeToByteArray())
     * ```
     */
    public open fun intoBoxedCStr(): CStr {
        // SAFETY: Typecast of [u8] to CStr is valid and we know contents have
        // no nulls except for the terminating byte.
        return Box.fromRaw(mutPtrFromRustCast(Box.intoRaw(this.intoInner())))
    }

    /**
     * Bypass "move out of struct which implements [Drop] trait" restriction.
     */
    public open fun intoInner(): List<UByte> {
        val thisRef = this
        // SAFETY: `mem.forget(self)` invalidates the previous call to `ptr.read(self.inner)`
        // so we use `ManuallyDrop` to ensure `self` is not dropped.
        // Then we can return the box directly without invalidating it.
        // See https://github.com/rust-lang/rust/issues/62553.
        return read(thisRef.inner)
    }

    // Turns this `CString` into an empty string to prevent
    // memory-unchecked code from working by accident. Inline
    // to prevent LLVM from optimizing it away in debug builds.
    public open fun drop() {
        // SAFETY: Length is always at least one.
        run {
            this.inner.getUncheckedMut(0) = 0
        }
    }

    public open fun deref(): CStr = this.asCStr()

    /**
     * Delegates to the [CStr] implementation of [fmt.Debug],
     * showing invalid UTF-8 as hex escapes.
     */
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append((this.asCStr()).toString()); Result.Ok(Unit) }

    public open fun borrow(): CStr = this

    public open fun eq(other: CStr): Boolean = this == other

    public open fun ne(other: CStr): Boolean = this != other

    public open fun eqCStr(other: CStr): Boolean = this == other

    public open fun neCStr(other: CStr): Boolean = this != other

    public open fun eqCowCStr(other: CStr): Boolean = this == other

    public open fun neCowCStr(other: CStr): Boolean = this != other

    public open fun index(index: RangeFull): CStr = this

    public open fun asRef(): CStr = this

    public companion object {
        /**
         * Creates a new C-compatible string from a container of bytes.
         *
         * This function will consume the provided data and use the
         * underlying bytes to construct a new string, ensuring that
         * there is a trailing 0 byte. This trailing 0 byte will be
         * appended by this function; the provided data should *not*
         * contain any 0 bytes in it.
         *
         * # Examples
         *
         * ```ignore (extern-declaration)
         * use std.ffi.CString;
         * use std.os.raw.cChar;
         *
         * extern "C" { fn puts(s: *const cChar); }
         *
         * let toPrint = CString.new("Hello!").expect("we provided a string without NUL bytes, so CString.new should not fail");
         * unchecked {
         *     puts(toPrint.asPtr());
         * }
         * ```
         *
         * # Errors
         *
         * This function will return an error if the supplied bytes contain an
         * internal 0 byte. The [NulError] returned will contain the bytes as well as
         * the position of the nul byte.
         */
        public fun new(t: List<UByte>): Result<CString, NulError> {
            interface SpecNewImpl {
                public fun specNewImpl(): Result<CString, NulError>
            }
            fun List<UByte>.specNewImpl(): Result<CString, NulError> {
                val bytes: List<UByte> = this.into()
                return when (val matchVal = memchr(0, bytes)) {
                    // SAFETY: We ensured there's no null bytes.
                    null -> Result.Ok<CString, NulError>(CString.fromVecUnchecked(bytes))
                    else -> {
                        val i = matchVal
                        Result.Err<CString, NulError>(NulError(i, bytes))
                    }
                }
            }
            // Specialization for avoiding reallocation
            // Without that it is not inlined into specializations
            fun specNewImplBytes(bytes: List<UByte>): Result<CString, NulError> {
                // We cannot have such large slice that we would overflow here
                // but using `checkedAdd` allows LLVM to assume that capacity never overflows
                // and generate twice shorter code.
                // `saturatingAdd` doesn't help for some reason.
                val capacity = bytes.size.checkedAdd(1)!!
                // Allocate before validation to avoid duplication of allocation code.
                // We still need to allocate and copy memory even if we get an error.
                val buffer = ArrayList<UByte>(capacity)
                buffer.extend(bytes)
                // Check memory of self instead of new buffer.
                // This allows better optimizations if lto enabled.
                return when (val matchVal = memchr(0, bytes)) {
                    // SAFETY: We ensured there's no null bytes.
                    null -> Result.Ok<CString, NulError>(CString.fromVecUnchecked(buffer))
                    else -> {
                        val i = matchVal
                        Result.Err<CString, NulError>(NulError(i, buffer))
                    }
                }
            }
            fun String.specNewImpl(): Result<CString, NulError> = specNewImplBytes(this.encodeToByteArray().map { it.toUByte() })
            fun MutableList<UByte>.specNewImpl(): Result<CString, NulError> = specNewImplBytes(this)
            return t.specNewImpl()
        }

        /**
         * Creates a C-compatible string by consuming a byte vector,
         * without checking for interior 0 bytes.
         *
         * Trailing 0 byte will be appended by this function.
         *
         * This method is equivalent to [CString.new] except that no runtime
         * assertion is made that `v` contains no 0 bytes, and it requires an
         * actual byte vector, not anything that can be converted to one with Into.
         *
         * # Safety
         *
         * The caller must ensure `v` contains no nul bytes in its contents.
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
         *
         * val raw = "foo".encodeToByteArray().toVec()
         * run {
         *     val cString = CString.fromVecUnchecked(raw)
         * }
         * ```
         */
        public fun fromVecUnchecked(v: List<UByte>): CString {
            check(memchr(0, v) == null)
            // SAFETY: Upheld by caller.
            return CString.fromVecUnchecked(v)
        }

        /**
         * Retakes ownership of a `CString` that was transferred to C via
         * [CString.intoRaw].
         *
         * Additionally, the length of the string will be recalculated from the pointer.
         *
         * # Safety
         *
         * This should only ever be called with a pointer that was earlier
         * obtained by calling [CString.intoRaw], and the memory it points to must not be accessed
         * through any other pointer during the scope of reconstructed `CString`.
         * Other usage (e.g., trying to take ownership of a string that was allocated by foreign code)
         * is likely to lead to undefined behavior or allocator corruption.
         *
         * This function does not validate ownership of the raw pointer's memory.
         * A double-free may occur if the function is called twice on the same raw pointer.
         * Additionally, the caller must ensure the pointer is not dangling.
         *
         * It should be noted that the length isn't just "recomputed," but that
         * the recomputed length must match the original length from the
         * [CString.intoRaw] call. This means the [CString.intoRaw]/`fromRaw`
         * methods should not be used when passing the string to C functions that can
         * modify the string's length.
         *
         * > **Note:** If you need to borrow a string that was allocated by
         * > foreign code, use [CStr]. If you need to take ownership of
         * > a string that was allocated by foreign code, you will need to
         * > make your own provisions for freeing it appropriately, likely
         * > with the foreign code's API to do that.
         *
         * # Examples
         *
         * Creates a `CString`, pass ownership to an `extern` function (via raw pointer), then retake
         * ownership with `fromRaw`:
         *
         * ```ignore (extern-declaration)
         * use std.ffi.CString;
         * use std.os.raw.cChar;
         *
         * extern "C" {
         *     fn someExternFunction(s: *mut cChar);
         * }
         *
         * let cString = CString.from(c"Hello!");
         * let raw = cString.intoRaw();
         * unchecked {
         *     someExternFunction(raw);
         *     let cString = CString.fromRaw(raw);
         * }
         * ```
         */
        public fun fromRaw(ptr: MutPtr): CString {
            // SAFETY: This is called with a pointer that was obtained from a call
            // to `CString.intoRaw` and the length has not been modified. As such,
            // we know there is a NUL byte (and only one) at the end and that the
            // information about the size of the allocation is correct on Rust's
            // side.
            return run {
                /**
                 * Provided by libc or compilerBuiltins.
                 */
                fun strlen(s: ConstPtr): ULong = 0uL
                val len = strlen(ptr) + 1
                // Including the NUL byte
                val slice = Slice.fromRawPartsMut(ptr, len)
                CString(inner = Box.fromRaw((mutPtrFromRustCast(slice)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }))
            }
        }

        /**
         * Converts a <code>[Vec]<[u8]></code> to a [CString] without checking the
         * invariants on the given [Vec].
         *
         * # Safety
         *
         * The given [Vec] **must** have one nul byte as its last element.
         * This means it cannot be empty nor have any other nul byte anywhere else.
         *
         * # Example
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
         *
         * check(CString.fromVecWithNulUnchecked("abc\u0000".encodeToByteArray().toVec()) == CString.fromVecUnchecked("abc".encodeToByteArray().toVec()))
         * ```
         */
        public fun fromVecWithNulUnchecked(v: List<UByte>): CString {
            check(memchr(0, v)!! + 1 == v.size)
            // SAFETY: Upheld by caller.
            return CString.fromVecWithNulUnchecked(v)
        }

        /**
         * Attempts to convert a <code>[Vec]<[u8]></code> to a [CString].
         *
         * Runtime checks are present to ensure there is only one nul byte in the
         * [Vec], its last element.
         *
         * # Errors
         *
         * If a nul byte is present and not the last element or no nul bytes
         * is present, an error will be returned.
         *
         * # Examples
         *
         * A successful conversion will produce the same result as [CString.new]
         * when called without the ending nul byte.
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
         *
         * check(CString.fromVecWithNul("abc\u0000".encodeToByteArray().toVec()).expect("we provided bytes that has one NUL byte exactly at the end, so CString.fromVecWithNul should not fail") == "abc".toOwned())
         * ```
         *
         * An incorrectly formatted [Vec] will produce an error.
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
         * import std.ffi.FromVecWithNulError
         *
         * // Interior nul byte
         * val unused: FromVecWithNulError = CString.fromVecWithNul("a\u0000bc".encodeToByteArray().toVec()).unwrapErr()
         * // No nul byte
         * val unused: FromVecWithNulError = CString.fromVecWithNul("abc".encodeToByteArray().toVec()).unwrapErr()
         * ```
         */
        public fun fromVecWithNul(v: List<UByte>): Result<CString, FromVecWithNulError> {
            val nulPos = memchr(0, v)
            return when {
                nulPos != null && nulPos + 1 == v.size -> {
                    val nulPos1 = nulPos
                    // SAFETY: We know there is only one nul byte, at the end
                    // of the vec.
                    Result.Ok<CString, FromVecWithNulError>(CString.fromVecWithNulUnchecked(v))
                }
                nulPos != null -> {
                    val nulPos2 = nulPos
                    Result.Err<CString, FromVecWithNulError>(FromVecWithNulError(errorKind = FromBytesWithNulErrorKind.InteriorNul(nulPos2), bytes = v))
                }
                else -> Result.Err<CString, FromVecWithNulError>(FromVecWithNulError(errorKind = FromBytesWithNulErrorKind.NotNulTerminated, bytes = v))
            }
        }

        /**
         * Creates an empty `CString`.
         */
        public fun default(): CString {
            val a: CStr = CStr.default()
            return a.toOwned()
        }

        /**
         * Converts a `Cow<CStr>` into a `CString`, by copying the contents if they are
         * borrowed.
         */
        public fun from(s: CStr): CString = s.intoOwned()

        /**
         * Converts a <code>[Holder]<[CStr]></code> into a [CString] without copying or allocating.
         */
        public fun fromBoxCStr(s: CStr): CString {
            val raw = mutPtrFromRustCast(Box.intoRaw(s))
            // SAFETY: Converting a *mut CStr -> *mut [u8] -> CString is valid.
            return CString(inner = Box.fromRaw(raw))
        }

        /**
         * Converts a <code>[Vec]<[NonZero]<[u8]>></code> into a [CString] without
         * copying nor checking for inner nul bytes.
         */
        public fun from(v: List<NonZero<UByte, NonZeroU8Inner>>): CString {
            // Transmute `List<NonZero<UByte>>` to `List<UByte>`.
            val v1: List<UByte> = run {
                val (ptr: MutPtr, len, cap) = Vec.intoRawParts(v)
                // SAFETY:
                // - transmuting between `NonZero<UByte>` and `UByte` is sound;
                // - `alloc.Layout<NonZero<UByte>> == alloc.Layout<UByte>`.
                Vec.fromRawParts(MutPtr((ptr).rawAddress), len, cap)
            }
            // SAFETY: `v` cannot contain nul bytes, given the type-level
            // invariant of `NonZero<UByte>`.
            return CString.fromVecUnchecked(v1)
        }

        /**
         * Converts a string `s` into a [CString].
         *
         * This method is equivalent to [CString.new].
         */
        public fun fromStr(s: String): Result<CString, NulError> = CString.new(s)

        /**
         * Converts a <code>&[CStr]</code> into a [CString]
         * by copying the contents into a new allocation.
         */
        public fun fromCStr(s: CStr): CString = s.toOwned()
    }
}

/**
 * An error indicating that an interior nul byte was found.
 *
 * While Rust strings may contain nul bytes in the middle, C strings
 * can't, as that byte would effectively truncate the string.
 *
 * This error is created by the [`new`][CString.new] method on
 * [CString]. See its documentation for more.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
 * import std.ffi.NulError
 *
 * val unused: NulError = CString.new("f\u0000oo".encodeToByteArray().toVec()).unwrapErr()
 * ```
 */
public open class NulError(
    public var field0: Int,
    public var field1: List<UByte>,
) {
    public open fun clone(): NulError = NulError(field0, field1)

    /**
     * Returns the position of the nul byte in the slice that caused
     * [CString.new] to fail.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     *
     * val nulError = CString.new("foo\u0000bar").unwrapErr()
     * check(nulError.nulPosition() == 3)
     * val nulError1 = CString.new("foo bar\u0000").unwrapErr()
     * check(nulError1.nulPosition() == 7)
     * ```
     */
    public open fun nulPosition(): Int = this.field0

    /**
     * Consumes this error, returning the underlying vector of bytes which
     * generated the error in the first place.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     *
     * val nulError = CString.new("foo\u0000bar").unwrapErr()
     * check(nulError.intoVec() == "foo\u0000bar".encodeToByteArray())
     * ```
     */
    public open fun intoVec(): List<UByte> = this.field1

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append("nul byte found in provided data at position: ${this.field0}"); Result.Ok(Unit) }
}

public sealed class FromBytesWithNulErrorKind {
    public data class InteriorNul(public var value: Int) : FromBytesWithNulErrorKind()
    public data object NotNulTerminated : FromBytesWithNulErrorKind()

    public fun rustEquals(other: FromBytesWithNulErrorKind): Boolean = this == other
}

/**
 * An error indicating that a nul byte was not in the expected position.
 *
 * The vector used to create a [CString] must have one and only one nul byte,
 * positioned at the end.
 *
 * This error is created by the [CString.fromVecWithNul] method.
 * See its documentation for more.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
 * import std.ffi.FromVecWithNulError
 *
 * val unused: FromVecWithNulError = CString.fromVecWithNul("f\u0000oo".encodeToByteArray().toVec()).unwrapErr()
 * ```
 */
public open class FromVecWithNulError(
    public var errorKind: FromBytesWithNulErrorKind,
    public var bytes: List<UByte>,
) {
    public open fun clone(): FromVecWithNulError = FromVecWithNulError(errorKind, bytes)

    /**
     * Returns a slice of [u8]s bytes that were attempted to convert to a [CString].
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     *
     * // Some invalid bytes in a vector
     * val bytes = "f\u0000oo".encodeToByteArray().toVec()
     * val value = CString.fromVecWithNul(bytes.clone())
     * check(bytes == value.unwrapErr().encodeToByteArray().map { it.toUByte() })
     * ```
     */
    public open fun asBytes(): List<UByte> = this.bytes

    /**
     * Returns the bytes that were attempted to convert to a [CString].
     *
     * This method is carefully constructed to avoid allocation. It will
     * consume the error, moving out the bytes, so that a copy of the bytes
     * does not need to be made.
     *
     * # Examples
     *
     * Basic usage:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
     *
     * // Some invalid bytes in a vector
     * val bytes = "f\u0000oo".encodeToByteArray().toVec()
     * val value = CString.fromVecWithNul(bytes.clone())
     * check(bytes == value.unwrapErr().intoBytes())
     * ```
     */
    public open fun intoBytes(): List<UByte> = this.bytes

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        when (val matchVal = this.errorKind) {
            is FromBytesWithNulErrorKind.InteriorNul -> {
                val (pos) = matchVal
                run { f.append("data provided contains an interior nul byte at pos $pos"); Result.Ok(Unit) }
            }
            FromBytesWithNulErrorKind.NotNulTerminated -> {
                run { f.append("data provided is not nul terminated"); Result.Ok(Unit) }
            }
        }
}

/**
 * An error indicating invalid UTF-8 when converting a [CString] into a [String].
 *
 * `CString` is just a wrapper over a buffer of bytes with a nul terminator;
 * [CString.intoString] performs UTF-8 validation on those bytes and may
 * return this error.
 *
 * This `class` is created by [CString.intoString]. See
 * its documentation for more.
 */
public open class IntoStringError(
    public var inner: CString,
    public var error: Utf8Error,
) {
    public open fun clone(): IntoStringError = IntoStringError(inner, error)

    /**
     * Consumes this error, returning original [CString] which generated the
     * error.
     */
    public open fun intoCstring(): CString = this.inner

    /**
     * Access the underlying UTF-8 error that was the cause of this error.
     */
    public open fun utf8Error(): Utf8Error = this.error

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = "C string contained non-utf8 bytes".fmt(f)

    public open fun source(): Any? = this.error
}

/**
 * Converts a [CString] into a <code>[Vec]<[u8]></code>.
 *
 * The conversion consumes the [CString], and removes the terminating NUL byte.
 */
public fun fromVecUByte(s: CString): List<UByte> = s.intoBytes()

/**
 * Converts a `CStr` into a `Holder<CStr>`,
 * by copying the contents into a newly allocated [Box].
 */
public fun fromBoxCStr(s: CStr): CStr = Box.cloneFromRef(s)

/**
 * Converts a [CString] into a [String] if it contains valid UTF-8 data.
 *
 * This method is equivalent to [CString.intoString].
 */
public fun tryFromString(value: CString): Result<String, IntoStringError> = value.intoString()

public fun <A : Allocator> CStr.clone(): CStr = Box.cloneFromRefIn(this, CStr.allocator(this).clone())

/**
 * Converts a [CString] into a <code>[Holder]<[CStr]></code> without copying or allocating.
 */
public fun fromBoxCStr(s: CString): CStr = s.intoBoxedCStr()

/**
 * Converts a [CString] into an owned [Cow] without copying or allocating.
 */
public fun fromCowCStr(s: CString): CStr = s

/**
 * Converts a [CStr] into a borrowed [Cow] without copying or allocating.
 */
public fun fromCowCStr(s: CStr): CStr = s

/**
 * Converts a [CString] into an <code>[Arc]<[CStr]></code> by moving the [CString]
 * data into a new [Arc] buffer.
 */
public fun fromArcCStr(s: CString): CStr {
    val arc: List<UByte> = Arc.from(s.intoInner())
    // SAFETY: Type conversion is valid.
    return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
}

/**
 * Converts a `CStr` into a `Arc<CStr>`,
 * by copying the contents into a newly allocated [Arc].
 */
public fun fromArcCStr(s: CStr): CStr {
    val arc: List<UByte> = Arc.from(s.toBytesWithNul())
    // SAFETY: Type conversion is valid.
    return Arc.fromRaw(constPtrFromRustCast(Arc.intoRaw(arc)))
}

/**
 * Converts a [CString] into an <code>[Rc]<[CStr]></code> by moving the [CString]
 * data into a new [Rc] buffer.
 */
public fun fromRcCStr(s: CString): CStr {
    val rc: List<UByte> = Rc.from(s.intoInner())
    // SAFETY: Type conversion is valid.
    return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
}

/**
 * Converts a `CStr` into a `Rc<CStr>`,
 * by copying the contents into a newly allocated [Rc].
 */
public fun fromRcCStr(s: CStr): CStr {
    val rc: List<UByte> = Rc.from(s.toBytesWithNul())
    // SAFETY: Type conversion is valid.
    return Rc.fromRaw(constPtrFromRustCast(Rc.intoRaw(rc)))
}

/**
 * Creates an empty CStr inside an Rc
 *
 * This may or may not share an allocation with other Rcs on the same thread.
 */
public fun defaultRcCStr(): CStr = Rc.from("")

public fun defaultBoxCStr(): CStr = Box.from("")

public fun CStr.toOwned(): CString = CString(inner = this.toBytesWithNul().into())

public fun CStr.cloneInto(target: CString) {
    val src = this.toBytesWithNul()
    // If the lengths match, we can reuse the existing allocation without any overhead.
    if (target.inner.size == src.len()) {
        run { val dstTmp0 = target.inner; val srcTmp0 = src; for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] }
    } else {
        // Reuse the existing allocation's capacity by converting to a Vec.
        // We temporarily replace `target` with a valid dummy to remain panic-safe.
        val b = replace(target.inner, listOf(0)).intoVec()
        this.toBytesWithNul().cloneInto(b)
        target.inner = b.intoBoxedSlice()
    }
}

/**
 * Converts a `CStr` into a <code>[Cow]<[str]></code>.
 *
 * If the contents of the `CStr` are valid UTF-8 data, this
 * function will return a <code>[Cow]::[Borrowed]\(&[str])</code>
 * with the corresponding <code>&[str]</code> slice. Otherwise, it will
 * replace any invalid UTF-8 sequences with
 * [`U.Fffd.Replacement.Character`][U+FFFD] and return a
 * <code>[Cow]::[Owned]\([String])</code> with the result.
 *
 * [str]: prim@str "str"
 * [Borrowed]: Cow.Borrowed
 * [Owned]: Cow.Owned
 * [U+FFFD]: char.ReplacementCharacter
 *
 * # Examples
 *
 * Calling `toStringLossy` on a `CStr` containing valid UTF-8. The leading
 * `c` on the string literal denotes a `CStr`.
 *
 * ```kotlin
 * import std.borrow.Cow
 *
 * check("Hello World".toStringLossy() == "Hello World")
 * ```
 *
 * Calling `toStringLossy` on a `CStr` containing invalid UTF-8:
 *
 * ```kotlin
 * import std.borrow.Cow
 *
 * check("Hello �World".toStringLossy() == (("Hello �World" as Any?) as String))
 * ```
 */
public fun CStr.toStringLossy(): String = String.fromUtf8Lossy(this.toBytes())

/**
 * Converts a <code>[Holder]<[CStr]></code> into a [CString] without copying or allocating.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
 * import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
 *
 * val boxed: CStr = Holder.from("foo")
 * val cString: CString = "foo".toOwned()
 * check(boxed.intoCString() == cString)
 * ```
 */
public fun CStr.intoCString(): CString = CString.from(this)

public fun CStr.eq(other: CString): Boolean = this == other

public fun CStr.ne(other: CString): Boolean = this != other

public fun CStr.eq(other: CStr): Boolean = this == other

public fun CStr.ne(other: CStr): Boolean = this != other

public fun CStr.eqCStr(other: CStr): Boolean = this == other

public fun CStr.neCStr(other: CStr): Boolean = this != other

public fun CStr.eqCString(other: CString): Boolean = this == other

public fun CStr.neCString(other: CString): Boolean = this != other
