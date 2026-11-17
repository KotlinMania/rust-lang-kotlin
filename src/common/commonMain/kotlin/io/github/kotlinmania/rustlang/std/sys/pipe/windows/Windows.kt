// port-lint: source sys/pipe/windows.rs
package io.github.kotlinmania.rustlang.std.sys.pipe.windows

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.INVALID_HANDLE_VALUE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.createPipe
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle

public typealias Pipe = Handle

public fun pipe(): Result<Pair<Pipe, Pipe>, Error> {
    val readPipe = INVALID_HANDLE_VALUE
    val writePipe = INVALID_HANDLE_VALUE
    val ret = createPipe(readPipe, writePipe, MutPtr(0L), 0u)
    return if (ret == 0) {
        Result.Err<Pair<Pipe, Pipe>, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.lastOsError())
    } else {
        Result.Ok<Pair<Pipe, Pipe>, Error>(Pair(Handle.fromRawHandle(readPipe), Handle.fromRawHandle(writePipe)))
    }
}
