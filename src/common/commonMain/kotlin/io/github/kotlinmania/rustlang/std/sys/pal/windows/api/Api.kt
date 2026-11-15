// port-lint: source sys/pal/windows/api.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.pal.windows.api

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.rustLeadingOnes
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c

// # Safe(r) wrappers around Windows API functions.
//
// This module contains fairly thin wrappers around Windows API functions,
// aimed at centralising safety instead of having unchecked blocks spread
// throughout higher level code. This makes it much easier to audit FFI safety.
//
// Not all functions can be made completely safe without more context but in
// such cases we should still endeavour to reduce the caller's burden of safety
// as much as possible.
//
// ## Guidelines for wrappers
//
// Items here should be named similarly to their raw Windows API name, except
// that they follow Rust's case conventions. E.g. function names are
// lowerSnakeCase. The idea here is that it should be easy for a Windows
// C/C++ programmer to identify the underlying function that's being wrapped
// while not looking too out of place in Rust code.
//
// Every use of an `unchecked` block must have a related SAFETY comment, even if
// it's trivially safe (for example, see `getLastError`). Public unchecked
// functions must document what the caller has to do to call them safely.
//
// Avoid unchecked `as` casts. For integers, either assert that the integer
// is in range or use `tryInto` instead. For pointers, prefer to use
// `ptr.cast<Type>()` when possible.
//
// This module must only depend on core and not on std types as the eventual
// hope is to have std depend on sys and not the other way around.
// However, some amount of glue code may currently be necessary so such code
// should go in sys/pal/windows/mod.rs rather than here. See `IoResult` as an example.

/**
 * Gets the UTF-16 length of a UTF-8 string, for use in the wideStr macro.
 */
public fun utf16Len(s: String): Int {
    val s1 = s.encodeToByteArray().map { it.toUByte() }
    var i = 0
    var len = 0
    while (i < s1.size) {
        // the length of a UTF-8 encoded code-point is given by the number of
        // leading ones, except in the case of ASCII.
        val utf8Len =
            when (val matchVal = rustLeadingOnes(s1[i])) {
                0 -> 1
                else -> {
                    val n = matchVal
                    n.toInt()
                }
            }
        i += utf8Len
        // Note that UTF-16 surrogates (U+D800 to U+DFFF) are not encodable as UTF-8,
        // so (unlike with WTF-8) we don't have to worry about how they'll get re-encoded.
        len +=
            if (utf8Len < 4) {
                1
            } else {
                2
            }
    }
    return len
}

/**
 * Const convert UTF-8 to UTF-16, for use in the wideStr macro.
 *
 * Note that this is designed for use in const contexts so is not optimized.
 */
public fun toUtf16(s: String, utf16Len: Int): UShortArray {
    val output = UShortArray(utf16Len) { 0u.toUShort() }
    var pos = 0
    val s1 = s.encodeToByteArray().map { it.toUByte() }
    var i = 0
    while (i < s1.size) {
        when (rustLeadingOnes(s1[i])) {
            // Decode UTF-8 based on its length.
            // See https://en.wikipedia.org/wiki/UTF-8
            0 -> {
                // ASCII is the same in both encodings
                output[pos] = s1[i].toUShort()
                i += 1
                pos += 1
            }
            2 -> {
                // Bits: 110xxxxx 10xxxxxx
                output[pos] = (((s1[i].toUShort() and 0x1Fu.toUShort()).toUInt() shl 6).toUShort()) or (s1[i + 1].toUShort() and 0x3Fu.toUShort())
                i += 2
                pos += 1
            }
            3 -> {
                // Bits: 1110xxxx 10xxxxxx 10xxxxxx
                output[pos] = (((s1[i].toUShort() and 0xFu.toUShort()).toUInt() shl 12).toUShort()) or (((s1[i + 1].toUShort() and 0x3Fu.toUShort()).toUInt() shl 6).toUShort()) or (s1[i + 2].toUShort() and 0x3Fu.toUShort())
                i += 3
                pos += 1
            }
            4 -> {
                // Bits: 11110Note 10xxxxxx 10xxxxxx 10xxxxxx
                var c = ((s1[i].toUInt() and 0x7u) shl 18) or ((s1[i + 1].toUInt() and 0x3Fu) shl 12) or ((s1[i + 2].toUInt() and 0x3Fu) shl 6) or (s1[i + 3].toUInt() and 0x3Fu)
                // re-encode as UTF-16 (see https://en.wikipedia.org/wiki/UTF-16)
                // - Subtract 0x10000 from the code point
                // - For the high surrogate, shift right by 10 then add 0xD800
                // - For the low surrogate, take the low 10 bits then add 0xDC00
                c -= 0x10000u
                output[pos] = ((c shr 10) + 0xD800u).toUShort()
                output[pos + 1] = ((c and 0x3FFu) + 0xDC00u).toUShort()
                i += 4
                pos += 2
            }
            // valid UTF-8 cannot have any other values
            else -> error("unreachable")
        }
    }
    return output
}

/**
 * Helper method for getting the size of `T` as a u32.
 * Errors at compile time if the size would overflow.
 *
 * While a type larger than u32.Max is unlikely, it is possible if only because of a bug.
 * However, one key motivation for this function is to avoid the temptation to
 * use frequent `as` casts. This is risky because they are too powerful.
 * For example, the following will compile today:
 *
 * `sizeOf<ULong> as UInt`
 *
 * Note that `sizeOf` is never actually called, instead a function pointer is
 * converted to a `UInt`. Clippy would warn about this but, alas, it's not run
 * on the standard library.
 */
public fun <T> win32SizeOf(): UInt {
    // Const assert that the size does not exceed u32.Max.
    // Uses a trait to workaround restriction on using generic types in inner items.
    interface Win32SizeOf {
        public val WIN32_SIZE_OF: UInt
            get() = run {
            val size = sizeOf<Win32SizeOf>(typeLayoutT = ObjectSlotLayout<Win32SizeOf>())
            check(size <= UInt.MAX_VALUE.toInt())
            size.toUInt()
        }
    }
    return T.WIN32_SIZE_OF
}

/**
 * The `SetFileInformationByHandle` function takes a generic parameter by
 * making the user specify the type (class), a pointer to the data and its
 * size. This trait allows attaching that information to a Rust type so that
 * [setFileInformationByHandle] can be called safely.
 *
 * This trait is designed so that it can support variable sized types.
 * However, currently Rust's std only uses fixed sized structures.
 *
 * # Safety
 *
 * * `asPtr` must return a pointer to memory that is readable up to `size` bytes.
 * * `CLASS` must accurately reflect the type pointed to by `asPtr`. E.g.
 * the `FILE_BASIC_INFO` structure has the class `FileBasicInfo`.
 */
public interface SetFileInformation {
    /**
     * The type of information to set.
     */
    public val CLASS: Int

    /**
     * A pointer to the file information to set.
     */
    public fun asPtr(): ConstPtr

    /**
     * The size of the type pointed to by `asPtr`.
     */
    public fun size(): UInt
}

/**
 * Helper trait for implementing `SetFileInformation` for statically sized types.
 */
public interface SizedSetFileInformation {
    public val CLASS: Int
}

public val CLASS: Int = T.CLASS

public fun <T : SizedSetFileInformation> T.asPtr(): ConstPtr = ConstPtr(((this)).rawAddress)

public fun <T : SizedSetFileInformation> T.size(): UInt = win32SizeOf<T>()

// SAFETY: FILE_BASIC_INFO, FILE_END_OF_FILE_INFO, FILE_ALLOCATION_INFO,
// FILE_DISPOSITION_INFO, FILE_DISPOSITION_INFO_EX and FILE_IO_PRIORITY_HINT_INFO
// are all plain `repr(C)` structs that only contain primitive types.
// The given information classes correctly match with the struct.
public val CLASS1: Int = c.FileBasicInfo

public val CLASS2: Int = c.FileEndOfFileInfo

public val CLASS3: Int = c.FileAllocationInfo

public val CLASS4: Int = c.FileDispositionInfo

public val CLASS5: Int = c.FileDispositionInfoEx

public val CLASS6: Int = c.FileIoPriorityHintInfo

public fun <T : SetFileInformation> setFileInformationByHandle(handle: c.Handle, info: T): Result<Unit, WinError> {
    fun setInfo(handle1: c.Handle, cls: Int, info1: ConstPtr, size: UInt): Result<Unit, WinError> =
        run {
            val result = c.SetFileInformationByHandle(handle1, cls, info1, size)
            (result != 0).thenSome().okOrElse(::getLastError)
        }
    // SAFETY: The `SetFileInformation` trait ensures that this is safe.
    return setInfo(handle, T.CLASS, info.asPtr(), info.size())
}

/**
 * Gets the error from the last function.
 * This must be called immediately after the function that sets the error to
 * avoid the risk of another function overwriting it.
 */
public fun getLastError(): WinError {
    // SAFETY: This just returns a thread-local u32 and has no other effects.
    return WinError(code = c.GetLastError(), fieldLayout = PrimitiveLayouts.U32)
}

/**
 * An error code as returned by [getLastError].
 *
 * This is usually a 16-bit Win32 error code but may be a 32-bit HRESULT or NTSTATUS.
 * Check the documentation of the Windows API function being called for expected errors.
 */
public open class WinError internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<UInt>,
) {
    public var code: UInt
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(code: UInt, fieldLayout: TypeLayout<UInt>) : this(allocateNativeCell(code, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<WinError> = TransparentTypeLayout(fieldLayout, { ptr, layout -> WinError(ptr, layout) }, { it.code })

    public open fun clone(): WinError = WinError(code, fieldLayout)

    public companion object {
        public operator fun invoke(code: UInt, fieldLayout: TypeLayout<UInt>): WinError = WinError(code, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<UInt>): WinError = WinError(fieldStorage, fieldLayout)

        public fun new(code: UInt, fieldLayout: TypeLayout<UInt>): WinError = WinError(code = code, fieldLayout = fieldLayout)

        // Error code constants.
        // The constant names should be the same as the winapi constants except for the leading `ERROR_`.
        // Due to the sheer number of codes, error codes should only be added here on an as-needed basis.
        // However, they should never be removed as the assumption is they may be useful again in the future.

        /**
         * Success is not an error.
         * Some Windows APIs do use this to distinguish between a zero return and an error return
         * but we should never return this to users as an error.
         */
        public val SUCCESS: WinError = WinError.new(c.ERROR_SUCCESS, fieldLayout = PrimitiveLayouts.U32)

        // tidy-alphabetical-start
        public val ACCESS_DENIED: WinError = WinError.new(c.ERROR_ACCESS_DENIED, fieldLayout = PrimitiveLayouts.U32)

        public val ALREADY_EXISTS: WinError = WinError.new(c.ERROR_ALREADY_EXISTS, fieldLayout = PrimitiveLayouts.U32)

        public val BAD_NETPATH: WinError = WinError.new(c.ERROR_BAD_NETPATH, fieldLayout = PrimitiveLayouts.U32)

        public val BAD_NET_NAME: WinError = WinError.new(c.ERROR_BAD_NET_NAME, fieldLayout = PrimitiveLayouts.U32)

        public val CANT_ACCESS_FILE: WinError = WinError.new(c.ERROR_CANT_ACCESS_FILE, fieldLayout = PrimitiveLayouts.U32)

        public val DELETE_PENDING: WinError = WinError.new(c.ERROR_DELETE_PENDING, fieldLayout = PrimitiveLayouts.U32)

        public val DIRECTORY: WinError = WinError.new(c.ERROR_DIRECTORY, fieldLayout = PrimitiveLayouts.U32)

        public val DIR_NOT_EMPTY: WinError = WinError.new(c.ERROR_DIR_NOT_EMPTY, fieldLayout = PrimitiveLayouts.U32)

        public val FILE_NOT_FOUND: WinError = WinError.new(c.ERROR_FILE_NOT_FOUND, fieldLayout = PrimitiveLayouts.U32)

        public val INSUFFICIENT_BUFFER: WinError = WinError.new(c.ERROR_INSUFFICIENT_BUFFER, fieldLayout = PrimitiveLayouts.U32)

        public val INVALID_FUNCTION: WinError = WinError.new(c.ERROR_INVALID_FUNCTION, fieldLayout = PrimitiveLayouts.U32)

        public val INVALID_HANDLE: WinError = WinError.new(c.ERROR_INVALID_HANDLE, fieldLayout = PrimitiveLayouts.U32)

        public val INVALID_PARAMETER: WinError = WinError.new(c.ERROR_INVALID_PARAMETER, fieldLayout = PrimitiveLayouts.U32)

        public val NOT_FOUND: WinError = WinError.new(c.ERROR_NOT_FOUND, fieldLayout = PrimitiveLayouts.U32)

        public val NOT_SUPPORTED: WinError = WinError.new(c.ERROR_NOT_SUPPORTED, fieldLayout = PrimitiveLayouts.U32)

        public val NO_MORE_FILES: WinError = WinError.new(c.ERROR_NO_MORE_FILES, fieldLayout = PrimitiveLayouts.U32)

        public val OPERATION_ABORTED: WinError = WinError.new(c.ERROR_OPERATION_ABORTED, fieldLayout = PrimitiveLayouts.U32)

        public val PATH_NOT_FOUND: WinError = WinError.new(c.ERROR_PATH_NOT_FOUND, fieldLayout = PrimitiveLayouts.U32)

        public val SHARING_VIOLATION: WinError = WinError.new(c.ERROR_SHARING_VIOLATION, fieldLayout = PrimitiveLayouts.U32)

        public val TIMEOUT: WinError = WinError.new(c.ERROR_TIMEOUT, fieldLayout = PrimitiveLayouts.U32)
    }
}

/**
 * A wrapper around a UNICODE_STRING that is equivalent to `[UShort]`.
 *
 * It is preferable to use the `unicodeStr` macro as that contains mitigations for  #143078.
 *
 * If the MaximumLength field of the underlying UNICODE_STRING is greater than
 * the Length field then you can test if the string is null terminated by inspecting
 * the u16 directly after the string. You cannot otherwise depend on nul termination.
 */
public open class UnicodeStrRef(
    public var s: c.UnicodeString,
    public var lifetime: Unit,
) {
    public open fun clone(): UnicodeStrRef = UnicodeStrRef(s, lifetime)

    /**
     * Returns a pointer to the underlying UNICODE_STRING
     */
    public open fun asPtr(): ConstPtr = this.s

    public companion object {
        public fun new(slice: List<UShort>, isNullTerminated: Boolean): UnicodeStrRef {
            val (len1, maxLen, ptr) =
                if (slice.isEmpty()) {
                    Triple(0, 2, MutPtr((EMPTY_STRING_NULL_TERMINATED.asPtr()).rawAddress))
                } else {
                    val len = slice.size - (isNullTerminated.toInt())
                    Triple(len * 2, sizeOfVal(slice), MutPtr((slice.asPtr()).rawAddress))
                }
            return UnicodeStrRef(s = c.UnicodeString(length = len1.toUShort(), maximumLength = maxLen.toUShort(), buffer = ptr), lifetime = Unit)
        }

        public fun fromSliceWithNul(slice: List<UShort>): UnicodeStrRef {
            if (!slice.isEmpty()) {
                check(slice[slice.size - 1] == 0u.toUShort())
            }
            return UnicodeStrRef.new(slice, true)
        }

        public fun fromSlice(slice: List<UShort>): UnicodeStrRef = UnicodeStrRef.new(slice, false)
    }
}

public var EMPTY_STRING_NULL_TERMINATED: List<UShort> = listOf(0u.toUShort())

/**
 * Returns a list of enabled drive letters.
 *
 * This is a wrapper around [GetLogicalDrives].
 * Each letter is returned as an ascii byte.
 *
 * [GetLogicalDrives]: (https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-getlogicaldrives)
 */
public fun getLogicalDrives(): Any {
    // SAFETY: `GetLogicalDrives` only returns information.
    val drives = c.GetLogicalDrives()
    return (65u..90u).filter({ letter -> (drives.toUInt() shr ((letter - 65u).toInt())).toUByte() and 1 == 1 })
}

// Note: Use a null-terminated wide string type to assert validity, similar to CStr.
// Then this function can be safe.

/**
 * Get the NT path a device name points to.
 *
 * # Safety
 *
 * `deviceName` must be null-terminated.
 */
public fun queryDosDevice(deviceName: List<UShort>, buffer: MutableList<UShort>): List<UShort>? {
    val devicePtr = deviceName.asPtr()
    val bufferPtr = buffer.asMutPtr()
    val bufferLen = (buffer.size.tryInto().ok() ?: return null)
    // SAFETY: `devicePtr` points to a null-terminated u16 string.
    // `bufferPtr` is writeable up to bufferLen u16s.
    val result = (c.QueryDosDeviceW(devicePtr, bufferPtr, bufferLen)).toInt()
    return if (result > 0) {
        // QueryDosDeviceW returns a list of null-terminated strings where the list itself is also null-terminated
        // In the case where you pass a device name (which we always) it only returns one string.
        // Therefore to get the string we trim off both the list null termination and the string null termination.
        ((buffer).subList(0, result)).trimSuffix(listOf(0, 0))
    } else {
        null
    }
}
