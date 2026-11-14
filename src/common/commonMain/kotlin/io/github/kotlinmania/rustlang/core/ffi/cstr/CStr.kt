// port-lint: source ffi/c_str.rs
package io.github.kotlinmania.rustlang.core.ffi.cstr

import io.github.kotlinmania.rustlang.core.bytestr.ByteStr
import io.github.kotlinmania.rustlang.core.ffi.primitives.cchardefinition.CChar
import io.github.kotlinmania.rustlang.core.hint.assertUnchecked
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.num.nichetypes.UsizeNoHighBitMinusOne
import io.github.kotlinmania.rustlang.core.ops.range.RangeFrom
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering

// [CStr] and its related types.

// Note: because this is doc(inline)d, we *have* to use intra-doc links because the actual link
// depends on where the item is being documented. however, since this is libcore, we can't
// actually reference libstd or liballoc in intra-doc links. so, the best we can do is remove the
// links to `CString` and `String` for now until a solution is developed
// `fun from` in `implementation From<CStr> for Holder<CStr>` current implementation relies
// on `CStr` being layout-compatible with `[UByte]`.
// However, `CStr` layout is considered an implementation detail and must not be relied upon. We
// want `repr(transparent)` but we don't want it to show up in rustdoc, so we hide it under
// `cfg(doc)`. This is an ad-hoc implementation of attribute privacy.

/**
 * A dynamically-sized view of a C string.
 *
 * The type `CStr` represents a reference to a borrowed nul-terminated
 * array of bytes. It can be constructed safely from a <code>&[[u8]]</code>
 * slice, or unsafely from a raw `* const cChar`. It can be expressed as a
 * literal in the form `c"Hello world"`.
 *
 * The `CStr` can then be converted to a Rust <code>&[str]</code> by performing
 * UTF-8 validation, or into an owned `CString`.
 *
 * `CStr` is to `CString` as <code>&[str]</code> is to `String`: the former
 * in each pair are borrowing references; the latter are owned
 * strings.
 *
 * Note that this structure does **not** have a guaranteed layout (the `repr(transparent)`
 * notwithstanding) and should not be placed in the signatures of FFI functions.
 * Instead, safe wrappers of FFI functions may leverage [CStr.asPtr] and the unchecked
 * [CStr.fromPtr] constructor to provide a safe interface to other consumers.
 *
 * # Examples
 *
 * Inspecting a foreign C string:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ffi.CChar
 * import io.github.kotlinmania.rustlang.core.ffi.CStr
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * fun myString(): ConstPtr = ConstPtr(0L)
 * run {
 *     val slice = CStr.fromPtr(myString())
 *     println("string buffer size without nul terminator: ${slice.toBytes().len()}")
 * }
 * ```
 *
 * Passing a Rust-originating C string:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ffi.CChar
 * import io.github.kotlinmania.rustlang.core.ffi.CStr
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * fun work(data: CStr) {
 *     fun workWith(s: ConstPtr) {}
 *     workWith(data.asPtr())
 * }
 * val s = "Hello world!"
 * work(s)
 * ```
 *
 * Converting a foreign C string into a Rust `String`:
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ffi.CChar
 * import io.github.kotlinmania.rustlang.core.ffi.CStr
 * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
 *
 * fun myString(): ConstPtr = ConstPtr(0L)
 * fun myStringSafe(): String {
 *     val cstr = CStr.fromPtr(myString())
 *     // Get a copy-on-write Cow<_, str>, then extract the
 *     // allocated String (or allocate a fresh one if needed).
 *     return cstr.toStringLossy().intoOwned()
 * }
 * println("string: ${myStringSafe()}")
 * ```
 *
 * [str]: prim@str "str"
 */
public open class CStr internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<List<CChar>>,
) : AsRef<CStr> {
    public var inner: List<CChar>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(inner: List<CChar>, fieldLayout: TypeLayout<List<CChar>>) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<CStr> = TransparentTypeLayout(fieldLayout, { ptr, layout -> CStr(ptr, layout) }, { it.inner })

    /**
     * Shows the underlying bytes as a normal string, with invalid UTF-8
     * presented as hex escape sequences.
     */
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((ByteStr.fromBytes(this.toBytes())).toString()); Result.Ok(Unit) }

    /**
     * Returns the inner pointer to this C string.
     *
     * The returned pointer will be valid for as long as `self` is, and points
     * to a contiguous region of memory terminated with a 0 byte to represent
     * the end of the string.
     *
     * The type of the returned pointer is
     * [`const.cChar`][ffi.cChar], and whether it's
     * an alias for `* const Byte` or `* const UByte` is platform-specific.
     *
     * **WARNING**
     *
     * The returned pointer is read-only; writing to it (including passing it
     * to C code that writes to it) causes undefined behavior.
     *
     * It is your responsibility to make sure that the underlying memory is not
     * freed too early. For example, the following code will cause undefined
     * behavior when `ptr` is used inside the `unchecked` block:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.CString
     * import io.github.kotlinmania.rustlang.core.ffi.CStr
     *
     * // 💀 The meaning of this entire program is undefined,
     * // 💀 and nothing about its behavior is guaranteed,
     * // 💀 not even that its behavior resembles the code as written,
     * // 💀 just because it contains a single instance of undefined behavior!
     * // 🚨 creates a dangling pointer to a temporary `CString`
     * // 🚨 that is deallocated at the end of the statement
     * val ptr = CString.new(("Hi!").uppercase()).unwrap().asPtr()
     * // without undefined behavior, you would expect that `ptr` equals:
     * dbg(CStr.fromBytesWithNul("HI!\u0000".encodeToByteArray()).unwrap())
     * // 🙏 Possibly the program behaved as expected so far,
     * // 🙏 and this just shows `ptr` is now garbage..., but
     * // 💀 this violates `CStr.fromPtr`s safety contract
     * // 💀 leading to a dereference of a dangling pointer,
     * // 💀 which is immediate undefined behavior.
     * // 💀 *BOOM*, you're dead, your entire program has no meaning.
     * dbg(CStr.fromPtr(ptr))
     * ```
     *
     * This happens because, the pointer returned by `asPtr` does not carry any
     * scope information, and the `CString` is deallocated immediately after
     * the expression that it is part of has been evaluated.
     * To fix the problem, bind the `CString` to a local variable:
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.alloc.ffi.CString
     * import io.github.kotlinmania.rustlang.core.ffi.CStr
     *
     * val cStr = CString.new(("Hi!").uppercase()).unwrap()
     * val ptr = cStr.asPtr()
     * check(CStr.fromPtr(ptr) == "HI!")
     * ```
     */
    public open fun asPtr(): ConstPtr = this.inner.asPtr()

    /**
     * We could eventually expose this publicly, if we wanted.
     */
    public open fun asNonNullPtr(): NonNull {
        // Note(constTraitImpl) replace with `NonNull.from`
        // SAFETY: a reference is never null
        return NonNull.newUnchecked((constPtrFromRustCast(this.inner)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).asNonNullPtr()
    }

    /**
     * Returns the length of `self`. Like C's `strlen`, this does not include the nul terminator.
     *
     * > **Note**: This method is currently implemented as a constant-time
     * > cast, but it is planned to alter its definition in the future to
     * > perform the length calculation whenever this method is called.
     *
     * # Examples
     *
     * ```kotlin
     * check("foo".countBytes() == 3)
     * check("".countBytes() == 0)
     * ```
     */
    public open fun countBytes(): Int {
        // SAFETY: This length includes the nul-terminator, so it's at least one.
        return this.inner.size.uncheckedSub(1)
    }

    /**
     * Returns `true` if `self.toBytes()` has a length of 0.
     *
     * # Examples
     *
     * ```kotlin
     * check(!"foo".isEmpty())
     * check("".isEmpty())
     * ```
     */
    public open fun isEmpty(): Boolean {
        // SAFETY: We know there is at least one byte; for empty strings it
        // is the NUL terminator.
        // Note(const-Note): use getUnchecked
        return (ObjectSlotLayout<CChar>()).readNative((this.inner.asPtr()).rawAddress) == 0u.toUByte()
    }

    /**
     * Converts this C string to a byte slice.
     *
     * The returned slice will **not** contain the trailing nul terminator that this C
     * string has.
     *
     * > **Note**: This method is currently implemented as a constant-time
     * > cast, but it is planned to alter its definition in the future to
     * > perform the length calculation whenever this method is called.
     *
     * # Examples
     *
     * ```kotlin
     * check("foo".toBytes() == "foo".encodeToByteArray())
     * ```
     */
    public open fun toBytes(): List<UByte> {
        val bytes = this.toBytesWithNul()
        // Note(const-Note) replace with range index
        // SAFETY: toBytesWithNul returns slice with length at least 1
        return rawSliceList(bytes.asPtr(), (bytes.size - 1).toInt(), PrimitiveLayouts.U8)
    }

    /**
     * Converts this C string to a byte slice containing the trailing 0 byte.
     *
     * This function is the equivalent of [CStr.toBytes] except that it
     * will retain the trailing nul terminator instead of chopping it off.
     *
     * > **Note**: This method is currently implemented as a 0-cost cast, but
     * > it is planned to alter its definition in the future to perform the
     * > length calculation whenever this method is called.
     *
     * # Examples
     *
     * ```kotlin
     * check("foo".toBytesWithNul() == "foo\u0000".encodeToByteArray())
     * ```
     */
    public open fun toBytesWithNul(): List<UByte> {
        // SAFETY: Transmuting a slice of `cChar`s to a slice of `UByte`s
        // is safe on all supported targets.
        val bytes = (ObjectSlotLayout<List<UByte>>()).readNative(((((addrOf(this.inner))).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
        // SAFETY: A valid `CStr` always contains at least its trailing nul byte.
        assertUnchecked(!bytes.isEmpty())
        return bytes
    }

    /**
     * Iterates over the bytes in this C string.
     *
     * The returned iterator will **not** contain the trailing nul terminator
     * that this C string has.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(cstrBytes)
     * public fun doctestEntry() {
     *     check("foo".bytes().eq("foo".encodeToByteArray()))
     * }
     * ```
     */
    public open fun bytes(): Bytes = Bytes.new(this)

    /**
     * Yields a <code>&[str]</code> slice if the `CStr` contains valid UTF-8.
     *
     * If the contents of the `CStr` are valid UTF-8 data, this
     * function will return the corresponding <code>&[str]</code> slice. Otherwise,
     * it will return an error with details of where UTF-8 validation failed.
     *
     * [str]: prim@str "str"
     *
     * # Examples
     *
     * ```kotlin
     * check("foo".toStr() == Result.success("foo"))
     * ```
     */
    public open fun toStr(): Result<String, Utf8Error> {
        // N.B., when `CStr` is changed to perform the length check in `.toBytes()`
        // instead of in `fromPtr()`, it may be worth considering if this should
        // be rewritten to do the UTF-8 check inline with the length calculation
        // instead of doing it afterwards.
        return String.fromUtf8(this.toBytes())
    }

    /**
     * Returns an object that implements [Display] for safely printing a [CStr] that may
     * contain non-Unicode data.
     *
     * Behaves as if `self` were first lossily converted to a `String`, with invalid UTF-8 presented
     * as the Unicode replacement character: �.
     *
     * [Display]: fmt.Display
     *
     * # Examples
     *
     * ```kotlin
     * val cstr = "Hello, world!"
     * println("${cstr.display()}")
     * ```
     */
    public open fun display(): Display = Display(cStr = this)

    /**
     * Returns the same string as a string slice `CStr`.
     *
     * This method is redundant when used directly on `CStr`, but
     * it helps dereferencing other string-like types to string slices,
     * for example references to `Holder<CStr>` or `Arc<CStr>`.
     */
    public open fun asCStr(): CStr = this

    public open fun eq(other: CStr): Boolean = this == other

    public open fun ne(other: CStr): Boolean = this != other

    // `.toBytes()` representations are compared instead of the inner `[cChar]`s,
    // because `cChar` is `Byte` (not `UByte`) on some platforms.
    // That is why this is implemented manually and not derived.
    public open fun partialCmp(other: CStr): Ordering? = this.toBytes().partialCmp(other.toBytes())

    public open fun cmp(other: CStr): Ordering = this.toBytes().cmp(other.toBytes())

    public open fun index(index: RangeFrom<Int>): CStr {
        val bytes = this.toBytesWithNul()
        // we need to manually check the starting index to account for the null
        // byte, since otherwise we could get an empty string that doesn't end
        // in a null.
        return if (index.start < bytes.size) {
            // SAFETY: Non-empty tail of a valid `CStr` is still a valid `CStr`.
            CStr.fromBytesWithNulUnchecked(((bytes).slice(index.start until bytes.size)))
        } else {
            error("index out of bounds: the len is ${bytes.size} but the index is ${index.start}")
        }
    }

    public open fun indexRangeFromInt(index: RangeFrom<Int>): CStr = this.index(RangeFrom.from(index))

    public override fun asRef(): CStr = this

    public companion object {
        public operator fun invoke(inner: List<CChar>, fieldLayout: TypeLayout<List<CChar>>): CStr = CStr(inner, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<List<CChar>>): CStr = CStr(fieldStorage, fieldLayout)

        public fun default(): CStr = ""

        // inline is necessary for codegen to see strlen.

        /**
         * Wraps a raw C string with a safe C string wrapper.
         *
         * This function will wrap the provided `ptr` with a `CStr` wrapper, which
         * allows inspection and interoperation of non-owned C strings. The total
         * size of the terminated buffer must be smaller than [isize.Max] **bytes**
         * in memory (a restriction from [slice.fromRawParts]).
         *
         * # Safety
         *
         * * The memory pointed to by `ptr` must contain a valid nul terminator at the
         *   end of the string.
         *
         * * `ptr` must be [valid] for reads of bytes up to and including the nul terminator.
         *   This means in particular:
         *
         *     * The entire memory range of this `CStr` must be contained within a single allocation!
         *     * `ptr` must be non-null even for a zero-length cstr.
         *
         * * The memory referenced by the returned `CStr` must not be mutated for
         *   the duration of scope `a`.
         *
         * * The nul terminator must be within `Long.MAX` from `ptr`
         *
         * > **Note**: This operation is intended to be a 0-cost cast but it is
         * > currently implemented with an up-front calculation of the length of
         * > the string. This is not guaranteed to always be the case.
         *
         * # Caveat
         *
         * The scope for the returned slice is inferred from its usage. To prevent accidental misuse,
         * it's suggested to tie the scope to whichever source scope is safe in the context,
         * such as by providing a helper function taking the scope of a host value for the slice,
         * or by explicit annotation.
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.ffi.CChar
         * import io.github.kotlinmania.rustlang.core.ffi.CStr
         * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
         *
         * fun myString(): ConstPtr {
         *     return "hello".asPtr()
         * }
         * run {
         *     val slice = CStr.fromPtr(myString())
         *     check(slice.toStr().unwrap() == "hello")
         * }
         * ```
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.ffi.CChar
         * import io.github.kotlinmania.rustlang.core.ffi.CStr
         * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
         *
         * val HELLO_PTR: ConstPtr = run {
         *     val BYTES: List<UByte> = "Hello, world!\u0000".encodeToByteArray().map { it.toUByte() }
         *     BYTES.asPtr().cast()
         * }
         * val HELLO: CStr = CStr.fromPtr(HELLO_PTR)
         * check("Hello, world!" == HELLO)
         * ```
         */
        public fun fromPtr(ptr: ConstPtr): CStr {
            // SAFETY: The caller has provided a pointer that points to a valid C
            // string with a NUL terminator less than `Long.MAX` from `ptr`.
            val len = strlen(ptr)
            // SAFETY: The caller has provided a valid pointer with length less than
            // `Long.MAX`, so `fromRawParts` is safe. The content remains valid
            // and doesn't change for the scope of the returned `CStr`. This
            // means the call to `fromBytesWithNulUnchecked` is correct.
            //
            // The cast from cChar to u8 is ok because a cChar is always one byte.
            return CStr.fromBytesWithNulUnchecked(rawSliceList(ConstPtr((ptr).rawAddress), (len.asInner() + 1).toInt(), PrimitiveLayouts.U8))
        }

        /**
         * Creates a C string wrapper from a byte slice with any number of nuls.
         *
         * This method will create a `CStr` from any byte slice that contains at
         * least one nul byte. Unlike with [CStr.fromBytesWithNul], the caller
         * does not need to know where the nul byte is located.
         *
         * If the first byte is a nul character, this method will return an
         * empty `CStr`. If multiple nul characters are present, the `CStr` will
         * end at the first one.
         *
         * If the slice only has a single nul byte at the end, this method is
         * equivalent to [CStr.fromBytesWithNul].
         *
         * # Examples
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import io.github.kotlinmania.rustlang.core.ffi.CStr
         * import io.github.kotlinmania.rustlang.core.intrinsics.kmWriteBytes
         * import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
         * import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
         *
         * public fun doctestEntry() {
         *     val localStorageL5C0 = AddressableArrayLocal.fromUByteArray(UByteArray(16) { 0u.toUByte() })
         *     val buffer by localStorageL5C0
         *     run {
         *         // Here we might call an unchecked C function that writes a string
         *         // into the buffer.
         *         val bufPtr = localStorageL5C0.elementMutPtr(0)
         *         kmWriteBytes((bufPtr).rawAddress, (65u).toUByte(), (8).toLong() * (PrimitiveLayouts.U8).sizeBytes)
         *     }
         *     // Attempt to extract a C nul-terminated string from the buffer.
         *     val cStr = CStr.fromBytesUntilNul(buffer).unwrap()
         *     check(cStr.toStr().unwrap() == "AAAAAAAA")
         * }
         * ```
         */
        public fun fromBytesUntilNul(bytes: List<UByte>): Result<CStr, FromBytesUntilNulError> {
            val nulPos = memchr.memchr(0, bytes)
            return when (nulPos) {
                null -> Result.Err(FromBytesUntilNulError())
                else -> {
                    val nulPos1 = nulPos
                    // Note(const-Note) replace with range index
                    // SAFETY: nulPos + 1 <= bytes.len()
                    val subslice = rawSliceList(bytes.asPtr(), (nulPos1 + 1).toInt(), PrimitiveLayouts.U8)
                    // SAFETY: We know there is a nul byte at nulPos, so this slice
                    // (ending at the nul byte) is a well-formed C string.
                    Result.Ok(CStr.fromBytesWithNulUnchecked(subslice))
                }
            }
        }

        /**
         * Creates a C string wrapper from a byte slice with exactly one nul
         * terminator.
         *
         * This function will cast the provided `bytes` to a `CStr`
         * wrapper after ensuring that the byte slice is nul-terminated
         * and does not contain any interior nul bytes.
         *
         * If the nul byte may not be at the end,
         * [CStr.fromBytesUntilNul] can be used instead.
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.ffi.CStr
         *
         * val cstr = CStr.fromBytesWithNul("hello\u0000".encodeToByteArray())
         * check(cstr == Result.success("hello"))
         * ```
         *
         * Creating a `CStr` without a trailing nul terminator is an error:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.ffi.CStr
         * import std.ffi.FromBytesWithNulError
         *
         * val cstr = CStr.fromBytesWithNul("hello".encodeToByteArray())
         * check(cstr == Result.failure(FromBytesWithNulError.NotNulTerminated))
         * ```
         *
         * Creating a `CStr` with an interior nul byte is an error:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.ffi.CStr
         * import std.ffi.FromBytesWithNulError
         *
         * val cstr = CStr.fromBytesWithNul("he\u0000llo\u0000".encodeToByteArray())
         * check(cstr == Result.failure(FromBytesWithNulError.InteriorNul(position = 2)))
         * ```
         */
        public fun fromBytesWithNul(bytes: List<UByte>): Result<CStr, FromBytesWithNulError> {
            val nulPos = memchr.memchr(0, bytes)
            return when {
                nulPos != null && nulPos + 1 == bytes.size -> {
                    val nulPos1 = nulPos
                    // SAFETY: We know there is only one nul byte, at the end
                    // of the byte slice.
                    Result.Ok(CStr.fromBytesWithNulUnchecked(bytes))
                }
                nulPos != null -> {
                    val position = nulPos
                    Result.Err(FromBytesWithNulError.InteriorNul(position = position))
                }
                else -> Result.Err(FromBytesWithNulError.NotNulTerminated)
            }
        }

        /**
         * Unsafely creates a C string wrapper from a byte slice.
         *
         * This function will cast the provided `bytes` to a `CStr` wrapper without
         * performing any sanity checks.
         *
         * # Safety
         * The provided slice **must** be nul-terminated and not contain any interior
         * nul bytes.
         *
         * # Examples
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.ffi.CStr
         *
         * val bytes = "Hello world!\u0000".encodeToByteArray()
         * val cstr = CStr.fromBytesWithNulUnchecked(bytes)
         * check(cstr.toBytesWithNul() == bytes)
         * ```
         */
        public fun fromBytesWithNulUnchecked(bytes: List<UByte>): CStr =
            run {
                check(!bytes.isEmpty() && bytes[bytes.size - 1] == 0u.toUByte())
                (ObjectSlotLayout<CStr>()).readNative((((constPtrFromRustCast(bytes)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
            }
    }
}

/**
 * An error indicating that a nul byte was not in the expected position.
 *
 * The slice used to create a [CStr] must have one and only one nul byte,
 * positioned at the end.
 *
 * This error is created by the [CStr.fromBytesWithNul] method.
 * See its documentation for more.
 *
 * # Examples
 *
 * ```kotlin
 * import io.github.kotlinmania.rustlang.core.ffi.CStr
 * import std.ffi.FromBytesWithNulError
 *
 * val unused: FromBytesWithNulError = CStr.fromBytesWithNul("f\u0000oo".encodeToByteArray()).unwrapErr()
 * ```
 */
public sealed class FromBytesWithNulError {
    /**
     * Data provided contains an interior nul byte at byte `position`.
     */
    public data class InteriorNul(public var position: Int) : FromBytesWithNulError()
    /**
     * Data provided is not nul terminated.
     */
    public data object NotNulTerminated : FromBytesWithNulError()

    public fun rustEquals(other: FromBytesWithNulError): Boolean = this == other

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> =
        when (this) {
            is InteriorNul -> {
                val position = this.position
                f.writeFmt("data provided contains an interior nul byte at byte position $position")
            }
            FromBytesWithNulError.NotNulTerminated -> f.writeFmt("data provided is not nul terminated")
        }
}

/**
 * An error indicating that no nul byte was present.
 *
 * A slice used to create a [CStr] must contain a nul byte somewhere
 * within the slice.
 *
 * This error is created by the [CStr.fromBytesUntilNul] method.
 */
public open class FromBytesUntilNulError(
    public var value: Unit,
) {
    public var field0: Unit
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): FromBytesUntilNulError = FromBytesUntilNulError(value)

    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = f.writeFmt("data provided does not contain a nul")

    public companion object {
        public fun typeLayout(): TypeLayout<FromBytesUntilNulError> =
            object : TypeLayout<FromBytesUntilNulError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): FromBytesUntilNulError =
                    FromBytesUntilNulError(
                        (PrimitiveLayouts.UNIT).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FromBytesUntilNulError) {
                    (PrimitiveLayouts.UNIT).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FromBytesUntilNulError =
                    FromBytesUntilNulError(
                        (PrimitiveLayouts.UNIT).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FromBytesUntilNulError) {
                    (PrimitiveLayouts.UNIT).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * Calculate the length of a nul-terminated string. Defers to C's `strlen` when possible.
 *
 * # Safety
 *
 * The pointer must point to a valid buffer that contains a NUL terminator. The NUL must be
 * located within `Long.MAX` from `ptr`.
 */
public fun strlen(ptr: ConstPtr): UsizeNoHighBitMinusOne =
    run {
        /**
         * Provided by libc or compilerBuiltins.
         */
        fun strlen(s: ConstPtr): ULong = 0uL
        UsizeNoHighBitMinusOne.newUnchecked(strlen(s))
    }

/**
 * An iterator over the bytes of a [CStr], without the nul terminator.
 *
 * This struct is created by the [bytes] method on [CStr].
 * See its documentation for more.
 *
 * [bytes]: CStr.bytes
 */
public open class Bytes internal constructor(
    ptrCtorArg: NonNull,
    public var phantom: Unit,
) : Iterable<UByte> {
    // since we know the string is nul-terminated, we only need one pointer
    public var ptr: NonNull = ptrCtorArg
        internal set

    override fun iterator(): Iterator<UByte> = object : AbstractIterator<UByte>() {
        override fun computeNext() {
            when (val v = this@Bytes.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun clone(): Bytes = Bytes(ptr, phantom)

    public open fun isEmpty(): Boolean {
        // SAFETY: We uphold that the pointer is always valid to dereference
        // by starting with a valid C string and then never incrementing beyond
        // the nul terminator.
        return this.ptr.read() == 0
    }

    public open fun next(): UByte? {
        // SAFETY: We only choose a pointer from a valid C string, which must
        // be non-null and contain at least one value. Since we always stop at
        // the nul terminator, which is guaranteed to exist, we can assume that
        // the pointer is non-null and valid. This lets us safely dereference
        // it and assume that adding 1 will create a new, non-null, valid
        // pointer.
        return run {
            val ret = this.ptr.read()
            if (ret == 0) {
                null
            } else {
                this.ptr = this.ptr.add(1)
                ret
            }
        }
    }

    public open fun sizeHint(): Pair<Int, Int?> =
        if (this.isEmpty()) {
            Pair(0, 0)
        } else {
            Pair(1, null)
        }

    public open fun count(): Int {
        // SAFETY: We always hold a valid pointer to a C string
        return strlen(this.ptr.asPtr().cast()).toInt().asInner()
    }

    public companion object {
        public operator fun invoke(
            ptr: NonNull,
            phantom: Unit,
        ): Bytes = Bytes(
            ptrCtorArg = ptr,
            phantom = phantom,
        )

        public fun new(s: CStr): Bytes = Bytes(ptr = s.asNonNullPtr().cast(), phantom = Unit)
    }
}

/**
 * Helper struct for safely printing a [CStr] with [format] and `{}`.
 *
 * A [CStr] might contain non-Unicode data. This `class` implements the
 * [Display] trait in a way that mitigates that. It is created by the
 * [`display`](CStr.display) method on [CStr]. This may perform lossy
 * conversion, depending on the platform. If you would like an implementation
 * which escapes the [CStr] please use [Debug] instead.
 *
 * # Examples
 *
 * ```kotlin
 * val s = "Hello, world!"
 * println("${s.display()}")
 * ```
 *
 * [Display]: fmt.Display
 * [format]: ../../../std/macro.format.html
 */
public open class Display(
    public var cStr: CStr,
) {
    public open fun fmt(f: StringBuilder): Result<Unit, Unit> = run { f.append((this.cStr).toString()); Result.Ok(Unit) }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, Unit> = run { f.append((ByteStr.fromBytes(this.cStr.toBytes())).toString()); Result.Ok(Unit) }
}
