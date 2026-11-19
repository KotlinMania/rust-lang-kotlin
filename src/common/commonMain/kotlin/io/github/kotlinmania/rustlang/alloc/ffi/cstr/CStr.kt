// rust-kotlin: manual
// port-lint: source ffi/c_str.rs
package io.github.kotlinmania.rustlang.alloc.ffi.cstr

import io.github.kotlinmania.rustlang.core.borrow.Borrow
import io.github.kotlinmania.rustlang.core.convert.AsRef
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.ffi.cstr.strlen
import io.github.kotlinmania.rustlang.core.intrinsics.KmForgettableResource
import io.github.kotlinmania.rustlang.core.intrinsics.KmResourceCleaner
import io.github.kotlinmania.rustlang.core.intrinsics.kmAlloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmDealloc
import io.github.kotlinmania.rustlang.core.intrinsics.kmLoadU8
import io.github.kotlinmania.rustlang.core.intrinsics.kmRegisterResourceCleaner
import io.github.kotlinmania.rustlang.core.intrinsics.kmStoreU8
import io.github.kotlinmania.rustlang.core.mem.kmNormalizeUByteList
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZeroU8
import io.github.kotlinmania.rustlang.core.ops.drop.Drop
import io.github.kotlinmania.rustlang.core.ops.range.RangeFull
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.core.str.error.Utf8Error
import io.github.kotlinmania.rustlang.core.str.fromUtf8
import io.github.kotlinmania.rustlang.core.str.fromUtf8Lossy

internal class CStringAllocState(
    var addr: Long,
    var byteLen: Int,
    var owned: Boolean,
) {
    fun deallocIfOwned() {
        if (owned && addr != 0L) {
            owned = false
            kmDealloc(addr, byteLen.toLong(), 1L)
            addr = 0L
            byteLen = 0
        }
    }
}

private fun kmAllocateCBytes(bytes: List<UByte>): Long {
    val size = bytes.size.coerceAtLeast(1)
    val addr = kmAlloc(size.toLong(), 1L)
    for (idx in bytes.indices) {
        kmStoreU8(addr + idx.toLong(), bytes[idx])
    }
    if (bytes.isEmpty()) {
        kmStoreU8(addr, 0u.toUByte())
    }
    return addr
}

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
public open class CString private constructor(
    bytes: List<UByte>,
    nativeAddr: Long,
    owned: Boolean,
) : Borrow<CStr>,
    AsRef<CStr>,
    Drop,
    KmForgettableResource {
    private var bytesBacking: List<UByte> = bytes
    private val allocState: CStringAllocState = CStringAllocState(nativeAddr, if (owned) bytes.size.coerceAtLeast(1) else 0, owned)
    private val cleaner: KmResourceCleaner =
        allocState.let { state ->
            kmRegisterResourceCleaner(this) { state.deallocIfOwned() }
        }

    // Invariant 1: the slice ends with a zero byte and has a length of at least one.
    // Invariant 2: the slice contains only one zero byte.
    // Improper usage of unchecked function can break Invariant 2, but not Invariant 1.
    public constructor(inner: List<UByte>) : this(
        bytes = kmNormalizeUByteList(inner).toList(),
        nativeAddr = kmAllocateCBytes(kmNormalizeUByteList(inner)),
        owned = true,
    )

    public val inner: List<UByte>
        get() = this.bytesBacking

    internal fun replaceBytes(newBytes: List<UByte>) {
        this.allocState.deallocIfOwned()
        val copy = newBytes.toList()
        val newAddr = kmAllocateCBytes(copy)
        this.allocState.addr = newAddr
        this.allocState.byteLen = copy.size.coerceAtLeast(1)
        this.allocState.owned = true
        this.bytesBacking = copy
    }

    public open fun clone(): CString = CString(this.bytesBacking)

    public override fun close() {
        this.drop()
    }

    public override fun disarmCleaner() {
        this.allocState.owned = false
        this.cleaner.disarm()
    }

    public open fun asPtr(): ConstPtr = ConstPtr(this.allocState.addr)

    public open fun intoRaw(): MutPtr {
        val raw = this.allocState.addr
        this.disarmCleaner()
        this.allocState.addr = 0L
        this.allocState.byteLen = 0
        return MutPtr(raw)
    }

    public open fun intoString(): Result<String, IntoStringError> {
        val bytes = this.asBytes()
        return when (val utf8 = fromUtf8(bytes)) {
            is Result.Ok -> {
                val strVal = utf8.value
                this.drop()
                Result.Ok(strVal)
            }
            is Result.Err -> Result.Err(IntoStringError(inner = this, error = utf8.value))
        }
    }

    public open fun intoBytes(): List<UByte> {
        val res = this.asBytes().toList()
        this.drop()
        return res
    }

    public open fun intoBytesWithNul(): List<UByte> {
        val res = this.bytesBacking.toList()
        this.drop()
        return res
    }

    public open fun asBytes(): List<UByte> {
        // SAFETY: CString has a length at least 1
        return this.bytesBacking.subList(0, (this.bytesBacking.size - 1).coerceAtLeast(0))
    }

    public open fun asBytesWithNul(): List<UByte> = this.bytesBacking

    public open fun asCStr(): CStr {
        val addr = this.allocState.addr
        return if (addr != 0L) {
            CStr.fromPtr(ConstPtr(addr))
        } else {
            CStr.fromBytesWithNulUnchecked(this.bytesBacking)
        }
    }

    public open fun intoBoxedCStr(): CStr {
        val res = CStr.fromBytesWithNulUnchecked(this.bytesBacking.toList())
        this.drop()
        return res
    }

    public open fun intoInner(): List<UByte> {
        val res = this.bytesBacking.toList()
        this.drop()
        return res
    }

    public override fun drop() {
        this.cleaner.cleanNow()
        this.bytesBacking = listOf(0u.toUByte())
    }

    public open fun deref(): CStr = this.asCStr()

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> {
        f.append(fromUtf8Lossy(this.asBytes()))
        return Result.Ok(Unit)
    }

    public override fun borrow(): CStr = this.asCStr()

    public open fun eq(other: CStr): Boolean = this.asBytesWithNul() == other.toBytesWithNul()

    public open fun ne(other: CStr): Boolean = this.asBytesWithNul() != other.toBytesWithNul()

    public open fun eqCStr(other: CStr): Boolean = this.asBytesWithNul() == other.toBytesWithNul()

    public open fun neCStr(other: CStr): Boolean = this.asBytesWithNul() != other.toBytesWithNul()

    public open fun eqCowCStr(other: CStr): Boolean = this.asBytesWithNul() == other.toBytesWithNul()

    public open fun neCowCStr(other: CStr): Boolean = this.asBytesWithNul() != other.toBytesWithNul()

    public open fun index(index: RangeFull): CStr = this.asCStr()

    public override fun asRef(): CStr = this.asCStr()

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
            val normalized = kmNormalizeUByteList(t)
            return when (val nulPos = memchr(0u.toUByte(), normalized)) {
                null -> Result.Ok(CString.fromVecUnchecked(normalized))
                else -> Result.Err(NulError(nulPos, normalized))
            }
        }

        public fun new(t: String): Result<CString, NulError> = new(t.encodeToByteArray().map { it.toUByte() })

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
            val normalized = kmNormalizeUByteList(v)
            check(memchr(0u.toUByte(), normalized) == null)
            // SAFETY: Upheld by caller.
            return CString(normalized + 0u.toUByte())
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
            val cLen = strlen(ConstPtr(ptr.rawAddress)).value
            val snapshot = List(cLen + 1) { idx -> kmLoadU8(ptr.rawAddress + idx.toLong()) }
            return CString(bytes = snapshot, nativeAddr = ptr.rawAddress, owned = true)
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
            val normalized = kmNormalizeUByteList(v)
            check(memchr(0u.toUByte(), normalized)!! + 1 == normalized.size)
            // SAFETY: Upheld by caller.
            return CString(normalized.toList())
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
            val normalized = kmNormalizeUByteList(v)
            val nulPos = memchr(0u.toUByte(), normalized)
            return when {
                nulPos != null && nulPos + 1 == normalized.size -> {
                    // SAFETY: We know there is only one nul byte, at the end
                    // of the vec.
                    Result.Ok<CString, FromVecWithNulError>(CString.fromVecWithNulUnchecked(normalized))
                }
                nulPos != null -> {
                    val nulPos2 = nulPos
                    Result.Err<CString, FromVecWithNulError>(FromVecWithNulError(errorKind = FromBytesWithNulErrorKind.InteriorNul(nulPos2), bytes = normalized))
                }
                else -> Result.Err<CString, FromVecWithNulError>(FromVecWithNulError(errorKind = FromBytesWithNulErrorKind.NotNulTerminated, bytes = normalized))
            }
        }

        /**
         * Creates an empty `CString`.
         */
        public fun default(): CString = CString(listOf(0u.toUByte()))

        /**
         * Converts a `Cow<CStr>` into a `CString`, by copying the contents if they are
         * borrowed.
         */
        public fun from(s: CStr): CString = s.toOwned()

        /**
         * Converts a <code>[Holder]<[CStr]></code> into a [CString] without copying or allocating.
         */
        public fun fromBoxCStr(s: CStr): CString = s.toOwned()

        /**
         * Converts a <code>[Vec]<[NonZero]<[u8]>></code> into a [CString] without
         * copying nor checking for inner nul bytes.
         */
        public fun from(v: List<NonZeroU8>): CString =
            CString.fromVecUnchecked(v.map { it.get() })

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

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append("nul byte found in provided data at position: ${this.field0}")
            Result.Ok<Unit, Unit>(Unit)
        }

    public companion object
}

public sealed class FromBytesWithNulErrorKind {
    public data class InteriorNul(
        public var value: Int,
    ) : FromBytesWithNulErrorKind()
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

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        when (val matchVal = this.errorKind) {
            is FromBytesWithNulErrorKind.InteriorNul -> {
                val (pos) = matchVal
                run {
                    f.append("data provided contains an interior nul byte at pos $pos")
                    Result.Ok<Unit, Unit>(Unit)
                }
            }
            FromBytesWithNulErrorKind.NotNulTerminated -> {
                run {
                    f.append("data provided is not nul terminated")
                    Result.Ok<Unit, Unit>(Unit)
                }
            }
        }

    public companion object
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

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        run {
            f.append("C string contained non-utf8 bytes")
            Result.Ok<Unit, Unit>(Unit)
        }

    public open fun source(): Any? = this.error

    public companion object
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
public fun fromBoxCStr(s: CStr): CStr = CStr.fromBytesWithNulUnchecked(s.toBytesWithNul().toList())

/**
 * Converts a [CString] into a [String] if it contains valid UTF-8 data.
 *
 * This method is equivalent to [CString.intoString].
 */
public fun tryFromCString(value: CString): Result<String, IntoStringError> = value.intoString()

public fun CStr.clone(): CStr = CStr.fromBytesWithNulUnchecked(this.toBytesWithNul().toList())

/**
 * Converts a [CString] into a <code>[Holder]<[CStr]></code> without copying or allocating.
 */
public fun fromBoxCStr(s: CString): CStr = s.intoBoxedCStr()

/**
 * Converts a [CString] into an owned [Cow] without copying or allocating.
 */
public fun fromCowCStr(s: CString): CStr = s.asCStr()

/**
 * Converts a [CStr] into a borrowed [Cow] without copying or allocating.
 */
public fun fromCowCStr(s: CStr): CStr = s

public fun defaultBoxCStr(): CStr = CStr.default()

public fun CStr.toOwned(): CString = CString(inner = this.toBytesWithNul().toList())

public fun CStr.cloneInto(target: CString) {
    target.replaceBytes(this.toBytesWithNul())
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
public fun CStr.toStringLossy(): String = fromUtf8Lossy(this.toBytes())

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

public fun CStr.eq(other: CString): Boolean = this.toBytes() == other.asBytes()

public fun CStr.ne(other: CString): Boolean = !this.eq(other)

public fun CStr.eqCStr(other: CStr): Boolean = this.eq(other)

public fun CStr.neCStr(other: CStr): Boolean = this.ne(other)

public fun CStr.eqCString(other: CString): Boolean = this.eq(other)

public fun CStr.neCString(other: CString): Boolean = this.ne(other)
