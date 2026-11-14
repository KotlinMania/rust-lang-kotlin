// port-lint: source sys/pal/unix/conf.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unix.conf

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.libc.sysconf
import io.github.kotlinmania.rustlang.libc.wasi.SC_PAGESIZE
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.error.Error

public fun pageSize(): Int = sysconf(SC_PAGESIZE).toInt()

/**
 * Returns the value for [`confstr`][posixConfstr]. Currently only
 * used on Darwin, but should work on any unix (in case we need to get
 * `_CS_PATH` or `_CS_V[67] _ENV` in the future).
 *
 * [posixConfstr]:
 *     https://pubs.opengroup.org/onlinepubs/9799919799/functions/confstr.html
 */
public fun confstr(key: CInt, sizeHint: Int?): Result<OsString, Error> {
    val buf: List<UByte>  = ArrayList(0)
    var bytesNeededIncludingNul = (sizeHint ?: io.github.kotlinmania.rustlang.libc.confstr(key, MutPtr(0L), 0)).max(1)
    // If the value returned by `confstr` is greater than the len passed into
    // it, then the value was truncated, meaning we need to retry. Note that
    // while `confstr` results don't seem to change for a process, it's unclear
    // if this is guaranteed anywhere, so looping does seem required.
    while (bytesNeededIncludingNul > buf.capacity()) {
        // We write into the spare capacity of `buf`. This lets us avoid
        // changing buf's `len`, which both simplifies `reserve` computation,
        // allows working with `List<UByte>` instead of `List<MaybeUninit<UByte>>`, and
        // may avoid a copy, since the Vec knows that none of the bytes are needed
        // when reallocating (well, in theory anyway).
        buf.reserve(bytesNeededIncludingNul)
        // `confstr` returns
        // - 0 in the case of errors: we break and return an error.
        // - The number of bytes written, iff the provided buffer is enough to
        // hold the entire value: we break and return the data in `buf`.
        // - Otherwise, the number of bytes needed (including nul): we go
        // through the loop again.
        bytesNeededIncludingNul = io.github.kotlinmania.rustlang.libc.confstr(key, buf.asMutPtr(), buf.capacity())
    }
    // `confstr` returns 0 in the case of an error.
    if (bytesNeededIncludingNul == 0) {
        return Result.Err(io.github.kotlinmania.rustlang.std.io.Error.lastOsError())
    }
    // Safety: `confstr(..., buf.asMutPtr(), buf.capacity())` returned a
    // non-zero value, meaning `bytesNeededIncludingNul` bytes were
    // initialized.
    run {
        buf.setLen(bytesNeededIncludingNul)
        // Remove the NUL-terminator.
        val lastByte = buf.removeLastOrNull()
        // ... and smoke-check that it *was* a NUL-terminator.
        check(lastByte == 0)
    }
    return Result.Ok(OsString.fromVec(buf))
}

public fun glibcVersion(): Pair<Int, Int>? {
    fun gnuGetLibcVersion(): ConstPtr = ConstPtr(0L)
    val versionCstr = CStr.fromPtr(gnuGetLibcVersion())
    return when (val matchVal = versionCstr.toStr()) {
        is Ok -> {
            val versionStr = matchVal.value
            parseGlibcVersion(versionStr)
        }
        else -> {
            null
        }
    }
}

/**
 * Returns Some((major, minor)) if the string is a valid "x.y" version,
 * ignoring any extra dot-separated parts. Otherwise return None.
 */
public fun parseGlibcVersion(version: String): Pair<Int, Int>? {
    val parsedInts = version.split('.').map(String::parse<Int>).fuse()
    return run {
        val matchVal = Pair(parsedInts.next(), parsedInts.next())
        when {
            matchVal.first != null && matchVal.first is Result.Ok<*> && matchVal.second != null && matchVal.second is Result.Ok<*> -> {
                val major = (matchVal.first as Result.Ok<*>).value
                val minor = (matchVal.second as Result.Ok<*>).value
                Pair(major, minor)
            }
            else -> null
        }
    }
}
