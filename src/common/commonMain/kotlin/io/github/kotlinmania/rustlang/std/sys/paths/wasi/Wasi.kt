// port-lint: source sys/paths/wasi.rs
package io.github.kotlinmania.rustlang.std.sys.paths.wasi

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.libc.wasi.ERANGE
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.unix.ffi.osstr.fromVec
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runPathWithCstr

public fun getcwd(): Result<PathBuf, Error> {
    val buf = ArrayList(512)
    while (true) {
        run {
            val ptr = (buf.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
            if (!io.github.kotlinmania.rustlang.libc.getcwd(ptr, buf.capacity()).isNull()) {
                val len = CStr.fromPtr((buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).toBytes().len()
                buf.setLen(len)
                buf.shrinkToFit()
                return Result.Ok<PathBuf, Error>(PathBuf.from(OsString.fromVec(buf)))
            } else {
                val error = io.github.kotlinmania.rustlang.std.io.Error.lastOsError()
                if (error.rawOsError() != ERANGE) {
                    return Result.Err<PathBuf, Error>(error)
                }
            }
            // Trigger the internal buffer resizing logic of `List` by requiring
            // more space than the current capacity.
            val cap = buf.capacity()
            buf.setLen(cap)
            buf.reserve(1)
        }
    }
}

public fun chdir(p: path.Path): Result<Unit, Error> {
    val result = when (val tryResult = runPathWithCstr(p, { p1 -> Result.Ok(io.github.kotlinmania.rustlang.libc.chdir(p1.asPtr())) })) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
    return when (result == (0)) {
        true -> Result.Ok<Unit, Error>(Unit)
        false -> Result.Err<Unit, Error>(io.github.kotlinmania.rustlang.std.io.Error.lastOsError())
    }
}

public fun tempDir(): PathBuf {
    error("not supported by WASI yet")
}
