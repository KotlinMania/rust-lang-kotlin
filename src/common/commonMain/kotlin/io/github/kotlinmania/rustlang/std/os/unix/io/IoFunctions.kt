package io.github.kotlinmania.rustlang.std.os.unix.io

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.os.solid.io.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.solid.io.OwnedFd
import io.github.kotlinmania.rustlang.std.sync.mpmc.counter.new
import io.github.kotlinmania.rustlang.std.sys.fs.unix.runtimesymbols.write
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvt

public fun nullFd(): Result<OwnedFd> {
    val nullDev = when (val tryResult = new().read(true).write(true).open("/dev/null")) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(nullDev.into())
}

/**
 * Replaces the underlying file descriptor with the one from `other`.
 * Does not set CLOEXEC.
 */
public fun replaceStdioFd(thisRef: BorrowedFd, other: OwnedFd): Result<Unit> = cfgSelect(all, targetOs, "wasi", targetEnv, "p1", cvt, libc, wasilibcFdRenumber, other, asRawFd, thisRef, asRawFd, map, not, any, all, targetFamily, "wasm", not, targetOs, "emscripten", targetOs, "hermit", targetOs, "trusty", targetOs, "motor", cvt, libc, dup2, other, asRawFd, thisRef, asRawFd, map, thisRef, other, Err, io, Error, UNSUPPORTED_PLATFORM)
