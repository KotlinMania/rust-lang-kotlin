// port-lint: source sys/net/hostname/unix.rs
package io.github.kotlinmania.rustlang.std.sys.net.hostname.unix

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.libc.gethostname
import io.github.kotlinmania.rustlang.libc.strnlen
import io.github.kotlinmania.rustlang.libc.sysconf
import io.github.kotlinmania.rustlang.libc.wasi.EINVAL
import io.github.kotlinmania.rustlang.libc.wasi.ENAMETOOLONG
import io.github.kotlinmania.rustlang.libc.wasi.SC_HOST_NAME_MAX
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.sys.io.error.unix.errno

public fun hostname(): Result<OsString, Error> {
    // Query the system for the maximum host name length.
    val hostNameMax =
        when (val matchVal = sysconf(SC_HOST_NAME_MAX)) {
            // If this fails (possibly because there is no maximum length), then
            // assume a maximum length of _POSIX_HOST_NAME_MAX (255).
            -1 -> 255
            else -> {
                val max = matchVal
                max.toInt()
            }
        }
    // Reserve space for the nul terminator too.
    val buf = when (val tryResult = Vec.tryWithCapacity(hostNameMax + 1)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<OsString, Error>(tryResult.error) }
    while (true) {
        // SAFETY: `buf.capacity()` bytes of `buf` are writable.
        val r = gethostname(buf.asMutPtr().cast(), buf.capacity())
        when (val matchVal1 = (r != 0).then(errno)) {
            null -> {
                // Unfortunately, the UNIX specification says that the name will
                // be truncated if it does not fit in the buffer, without returning
                // an error. As additionally, the truncated name may still be null-
                // terminated, there is no reliable way to  detect truncation.
                // Fortunately, most platforms ignore what the specification says
                // and return an error (mostly ENAMETOOLONG). Should that not be
                // the case, the following detects truncation if the null-terminator
                // was omitted. Note that this check does not impact performance at
                // all as we need to find the length of the string anyways.
                //
                // Use `strnlen` as it does not place an initialization requirement
                // on the bytes after the nul terminator.
                //
                // SAFETY: `buf.capacity()` bytes of `buf` are accessible, and are
                // initialized up to and including a possible nul terminator.
                val len = strnlen(buf.asPtr().cast(), buf.capacity())
                if (len < buf.capacity()) {
                    // If the string is nul-terminated, we assume that is has not
                    // been truncated, as the capacity *should be* enough to hold
                    // `HOST_NAME_MAX` bytes.
                    // SAFETY: `len + 1` bytes have been initialized (we exclude
                    // the nul terminator from the string).
                    buf.setLen(len)
                    return Result.Ok<OsString, Error>(OsString.fromVec(buf))
                }
            }
            // As `buf.capacity()` is always less than or equal to `Long.MAX`
            // (Rust allocations cannot exceed that limit), the only way `EINVAL`
            // can be returned is if the system uses `EINVAL` to report that the
            // name does not fit in the provided buffer. In that case (or in the
            // case of `ENAMETOOLONG`), resize the buffer and try again.
            EINVAL, ENAMETOOLONG -> {}
            // Other error codes (e.g. EPERM) have nothing to do with the buffer
            // size and should be returned to the user.
            else -> {
                val err = matchVal1
                return Result.Err<OsString, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.fromRawOsError(err))
            }
        }
        // Resize the buffer (according to `List`s resizing rules) and try again.
        when (val tryResult = buf.tryReserve(buf.capacity() + 1)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<OsString, Error>(tryResult.error) }
    }
}
