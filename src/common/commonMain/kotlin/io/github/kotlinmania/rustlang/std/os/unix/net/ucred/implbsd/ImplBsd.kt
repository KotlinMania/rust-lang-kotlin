// port-lint: source os/unix/net/ucred.rs
package io.github.kotlinmania.rustlang.std.os.unix.net.ucred.implbsd

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.getpeereid
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixStream
import io.github.kotlinmania.rustlang.std.os.unix.net.ucred.UCred

public fun peerCred(socket: UnixStream): Result<UCred, Error> {
    val cred = UCred(uid = 1u, gid = 1u, pid = null)
    return run {
        val ret = getpeereid(socket.asRawFd(), cred.uid, cred.gid)
        if (ret == 0) {
            Result.Ok(cred)
        } else {
            Result.Err(io.github.kotlinmania.rustlang.std.io.Error.lastOsError())
        }
    }
}
