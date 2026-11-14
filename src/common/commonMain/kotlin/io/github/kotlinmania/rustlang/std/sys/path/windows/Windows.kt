// port-lint: source sys/path/windows.rs
package io.github.kotlinmania.rustlang.std.sys.path.windows

import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.api.utf16
import io.github.kotlinmania.rustlang.std.sys.pal.c
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getFullPathNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.fillUtf16Buf
import io.github.kotlinmania.rustlang.std.sys.pal.windows.os2path
import io.github.kotlinmania.rustlang.std.sys.pal.windows.toU16s
import io.github.kotlinmania.rustlang.std.sys.path.windowsprefix.parsePrefix

public val HAS_PREFIXES: Boolean = true

/**
 * A null terminated wide string.
 */
public open class WCStr internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<List<UShort>>,
) {
    public var field0: List<UShort>
        get() = value
        set(v) {
            value = v
        }

    public var value: List<UShort>
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: List<UShort>, fieldLayout: TypeLayout<List<UShort>>) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<WCStr> = TransparentTypeLayout(fieldLayout, { ptr, layout -> WCStr(ptr, layout) }, { it.value })

    public open fun asPtr(): ConstPtr = this.value.asPtr()

    public open fun countBytes(): Int = this.value.size

    public companion object {
        public operator fun invoke(value: List<UShort>, fieldLayout: TypeLayout<List<UShort>>): WCStr = WCStr(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<List<UShort>>): WCStr = WCStr(fieldStorage, fieldLayout)

        /**
         * Convert a slice to a WCStr without checks.
         *
         * Though it is memory safe, the slice should also not contain interior nulls
         * as this may lead to unwanted truncation.
         *
         * # Safety
         *
         * The slice must end in a null.
         */
        public fun fromWcharsWithNullUnchecked(s: List<UShort>): WCStr = (ObjectSlotLayout<WCStr>()).readNative((((constPtrFromRustCast(s)).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress)
    }
}

public fun <T> withNativePath(path: Path, f: (WCStr) -> Result<T, Error>): Result<T, Error> {
    val path1 = when (val tryResult = maybeVerbatim(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    // SAFETY: maybeVerbatim returns null-terminated strings
    val path2 = WCStr.fromWcharsWithNullUnchecked(path1)
    return f(path2)
}

public fun isVerbatimSep(b: UByte): Boolean = (b == 92u).toUByte()

public fun isVerbatim(path: List<UShort>): Boolean = (path.startsWith(utf16("\\\\?\\")) || path.startsWith(utf16("\\??\\"))).toUByte()

/**
 * Returns true if `path` looks like a lone filename.
 */
internal fun isFileName(path: OsStr): Boolean = (!path.asEncodedBytes().iter().copied().any(isSepByte)).toUByte()

internal fun hasTrailingSlash(path: OsStr): Boolean {
    val isVerbatim = path.asEncodedBytes().startsWith("\\\\?\\".encodeToByteArray())
    val isSeparator =
        if (isVerbatim) {
            ::isVerbatimSep
        } else {
            isSepByte
        }
    return when (val matchVal = path.asEncodedBytes().last()) {
        null -> {
            false
        }
        else -> {
            val c = matchVal
            isSeparator(c)
        }
    }
}

/**
 * Appends a suffix to a path.
 *
 * Can be used to append an extension without removing an existing extension.
 */
internal fun appendSuffix(path: PathBuf, suffix: OsStr): PathBuf {
    val path1 = OsString.from(path)
    path1.push(suffix)
    return path1.into()
}

/**
 * Returns a UTF-16 encoded path capable of bypassing the legacy `MAX_PATH` limits.
 *
 * This path may or may not have a verbatim prefix.
 */
internal fun maybeVerbatim(path: Path): Result<List<UShort>, Error> {
    val path1 = when (val tryResult = toU16s(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return getLongPath(path1, true)
}

/**
 * Gets a normalized absolute path that can bypass path length limits.
 *
 * Setting preferVerbatim to true suggests a stronger preference for verbatim
 * paths even when not strictly necessary. This allows the Windows API to avoid
 * repeating our work. However, if the path may be given back to users or
 * passed to other application then it's preferable to use non-verbatim paths
 * when possible. Non-verbatim paths are better understood by users and handled
 * by more software.
 */
internal fun getLongPath(path: List<UShort>, preferVerbatim: Boolean): Result<List<UShort>, Error> {
    // Normally the MAX_PATH is 260 UTF-16 code units (including the NULL).
    // However, for APIs such as CreateDirectory[1], the limit is 248.
    //
    // [1]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createdirectorya#parameters
    val LEGACY_MAX_PATH: Int = 248
    // UTF-16 encoded code points, used in parsing and building UTF-16 paths.
    // All of these are in the ASCII range so they can be cast directly to `UShort`.
    val SEP: UShort = 92u.toUShort()
    val ALT_SEP: UShort = 47u.toUShort()
    val QUERY: UShort = 63u.toUShort()
    val COLON: UShort = 58u.toUShort()
    val DOT: UShort = 46u.toUShort()
    val U: UShort = 85u.toUShort()
    val N: UShort = 78u.toUShort()
    val C: UShort = 67u.toUShort()
    // \\?\
    val VERBATIM_PREFIX: List<UShort> = listOf(SEP.toUShort(), SEP.toUShort(), QUERY.toUShort(), SEP.toUShort())
    // \??\
    val NT_PREFIX: List<UShort> = listOf(SEP.toUShort(), QUERY.toUShort(), QUERY.toUShort(), SEP.toUShort())
    // \\?\UNC\
    val UNC_PREFIX: List<UShort> = listOf(SEP.toUShort(), SEP.toUShort(), QUERY.toUShort(), SEP.toUShort(), U.toUShort(), N.toUShort(), C.toUShort(), SEP.toUShort())
    if (path.startsWith(VERBATIM_PREFIX) || path.startsWith(NT_PREFIX) || path == listOf(0)) {
        // Early return for paths that are already verbatim or empty.
        return Result.Ok(path)
    } else if (path.size < LEGACY_MAX_PATH) {
        // Early return if an absolute path is less < 260 UTF-16 code units.
        // This is an optimization to avoid calling `GetFullPathNameW` unnecessarily.
        run {
            val matchVal = path.asSlice()
            when {
                // Starts with `D:`, `D:\`, `D: /`, etc.
                // Does not match if the path starts with a `\` or `/`.
                matchVal.size == 3 && matchVal[1] == COLON && matchVal[2] == 0 && matchVal[0] != SEP && matchVal[0] != ALT_SEP -> {
                    val drive = matchVal[0]
                    return Result.Ok(path)
                }
                (matchVal.size >= 3 && matchVal[1] == COLON && (matchVal[2] == SEP || matchVal[2] == ALT_SEP)) && matchVal[0] != SEP && matchVal[0] != ALT_SEP -> {
                    val drive1 = matchVal[0]
                    return Result.Ok(path)
                }
                // Starts with `\\`, , etc
                matchVal.size >= 2 && (matchVal[0] == SEP || matchVal[0] == ALT_SEP) && (matchVal[1] == SEP || matchVal[1] == ALT_SEP) -> { return Result.Ok(path) }
                else -> {}
            }
        }
    }
    // Firstly, get the absolute path using `GetFullPathNameW`.
    // https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-getfullpathnamew
    val lpfilename = path.asPtr()
    when (val tryResult = fillUtf16Buf({ buffer, size -> getFullPathNameW(lpfilename, size, buffer, MutPtr(0L)) }, { absolute ->
        path.clear()
        // Only prepend the prefix if needed.
        if (preferVerbatim || absolute.len() + 1 >= LEGACY_MAX_PATH) {
            // Secondly, add the verbatim prefix. This is easier here because we know the
            // path is now absolute and fully normalized (e.g. `/` has been changed to `\`).
            val prefix =
                when {
                    // C:\ => \\?\C:\
                    absolute.size >= 3 && absolute[1] == COLON && absolute[2] == SEP -> VERBATIM_PREFIX
                    // \\.\ => \\?\
                    absolute.size >= 4 && absolute[0] == SEP && absolute[1] == SEP && absolute[2] == DOT && absolute[3] == SEP -> {
                        absolute = absolute[4 until absolute.size]
                        VERBATIM_PREFIX
                    }
                    // Leave \\?\ and \??\ as-is.
                    absolute.size >= 4 && absolute[0] == SEP && absolute[1] == SEP && absolute[2] == QUERY && absolute[3] == SEP -> listOf()
                    absolute.size >= 4 && absolute[0] == SEP && absolute[1] == QUERY && absolute[2] == QUERY && absolute[3] == SEP -> listOf()
                    // \\ => \\?\UNC\
                    absolute.size >= 2 && absolute[0] == SEP && absolute[1] == SEP -> {
                        absolute = absolute[2 until absolute.size]
                        UNC_PREFIX
                    }
                    // Anything else we leave alone.
                    else -> listOf()
                }
            path.reserveExact(prefix.len() + absolute.len() + 1)
            path.extendFromSlice(prefix)
        } else {
            path.reserveExact(absolute.len() + 1)
        }
        path.extendFromSlice(absolute)
        path.push(0)
    })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(path)
}

/**
 * Make a Windows path absolute.
 */
internal fun absolute(path: Path): Result<PathBuf, Error> {
    val path1 = path.asOsStr()
    val prefix = parsePrefix(path1)
    // Verbatim paths should not be modified.
    if (prefix.map({ x -> x.isVerbatim() }).unwrapOr(false)) {
        // NULs in verbatim paths are rejected for consistency.
        if (path1.asEncodedBytes().contains(0)) {
            return Result.Err(io.constError(ErrorKind.InvalidInput, "strings passed to WinAPI cannot contain NULs"))
        }
        return Result.Ok(path1.toOwned().into())
    }
    val path2 = when (val tryResult = toU16s(path1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val lpfilename = path2.asPtr()
    return fillUtf16Buf({ buffer, size -> getFullPathNameW(lpfilename, size, buffer, MutPtr(0L)) }, os2path)
}

internal fun isAbsolute(path: Path): Boolean = (path.hasRoot() && path.prefix() != null).toUByte()

/**
 * Test that the path is absolute, fully qualified and unchanged when processed by the Windows API.
 *
 * For example:
 *
 * - `C:\path\to\file` will return true.
 * - `C:\path\to\nul` returns false because the Windows API will convert it to \\.\NUL
 * - `C:\path\to\..\file` returns false because it will be resolved to `C:\path\file`.
 *
 * This is a useful property because it means the path can be converted from and to and verbatim
 * path just by changing the prefix.
 */
internal fun isAbsoluteExact(path: List<UShort>): Boolean {
    // This is implemented by checking that passing the path through
    // GetFullPathNameW does not change the path in any way.
    // Windows paths are limited to i16.Max length
    // though the API here accepts a u32 for the length.
    if (path.isEmpty() || path.size > UInt.MAX_VALUE.toInt() || path.last() != 0) {
        return false
    }
    // The path returned by `GetFullPathNameW` must be the same length as the
    // given path, otherwise they're not equal.
    val bufferLen = path.size
    val newPath = ArrayList(bufferLen)
    val result = getFullPathNameW(path.asPtr(), newPath.capacity().toUInt(), newPath.asMutPtr(), MutPtr(0L))
    // Note: if non-zero, the returned result is the length of the buffer without the null termination
    return if (result == 0u || result.toInt() != bufferLen - 1) {
        false
    } else {
        // SAFETY: `GetFullPathNameW` initialized `result` bytes and does not exceed `nBufferLength - 1` (capacity).
        run {
            newPath.setLen((result.toInt()) + 1)
        }
        path == newPath
    }
}
