// port-lint: source sys/pipe/windows.rs
package io.github.kotlinmania.rustlang.std.sys.pipe.windows

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.sys.c
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle

public typealias Pipe = Handle

public fun pipe(): Result<Pair<Pipe, Pipe>, Error> {
    val readPipe = c.INVALID_HANDLE_VALUE
    val writePipe = c.INVALID_HANDLE_VALUE
    val ret = c.CreatePipe(readPipe, writePipe, MutPtr(0L), 0)
    return if (ret == 0) {
        Result.Err<Pair<Pipe, Pipe>, Error>(io.github.kotlinmania.rustlang.std.io.Error.lastOsError())
    } else {
        Result.Ok<Pair<Pipe, Pipe>, Error>(Pair(Handle.fromRawHandle(readPipe), Handle.fromRawHandle(writePipe)))
    }
}
