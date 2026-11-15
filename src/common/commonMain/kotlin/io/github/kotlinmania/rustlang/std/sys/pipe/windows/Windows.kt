// port-lint: source sys/pipe/windows.rs
package io.github.kotlinmania.rustlang.std.sys.pipe.windows

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.os.windows.raw.Handle
import io.github.kotlinmania.rustlang.std.io.error.Error

public typealias Pipe = Handle

public fun pipe(): Result<Pair<Pipe, Pipe>, Error> {
    val readPipe = INVALID_HANDLE_VALUE
    val writePipe = INVALID_HANDLE_VALUE
    val ret = CreatePipe(readPipe, writePipe, MutPtr(0L), 0)
    return if (ret == 0) {
        Result.Err(io.github.kotlinmania.rustlang.io.Error.lastOsError())
    } else {
        Result.Ok(Pair(Handle.fromRawHandle(readPipe), Handle.fromRawHandle(writePipe)))
    }
}
