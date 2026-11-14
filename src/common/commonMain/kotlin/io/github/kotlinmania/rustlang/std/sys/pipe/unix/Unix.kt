// port-lint: source sys/pipe/unix.rs
package io.github.kotlinmania.rustlang.std.sys.pipe.unix

import io.github.kotlinmania.libc.fuchsia.O_CLOEXEC
import io.github.kotlinmania.libc.pipe2
import io.github.kotlinmania.rustlang.core.mem.AddressableArrayLocal
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt

public typealias Pipe = FileDesc

public fun pipe(): Result<Pair<Pipe, Pipe>, Error> {
    val localStorageL9C4 = AddressableArrayLocal.fromIntArray(IntArray(2) { 0 })
    val fds by localStorageL9C4
    return run {
        when (val tryResult = cvt(pipe2(localStorageL9C4.elementMutPtr(0), O_CLOEXEC))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        Result.Ok(Pair(Pipe.fromRawFd(fds[0]), Pipe.fromRawFd(fds[1])))
    }
}
