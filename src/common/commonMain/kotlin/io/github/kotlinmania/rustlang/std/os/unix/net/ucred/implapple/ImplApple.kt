// port-lint: source os/unix/net/ucred.rs
package io.github.kotlinmania.rustlang.std.os.unix.net.ucred.implapple

import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.getpeereid
import io.github.kotlinmania.rustlang.libc.getsockopt
import io.github.kotlinmania.rustlang.libc.unix.PidT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.LOCAL_PEERPID
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.SOL_LOCAL
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixStream
import io.github.kotlinmania.rustlang.std.os.unix.net.ucred.UCred

public fun peerCred(socket: UnixStream): Result<UCred, Error> {
    val cred = UCred(uid = 1u, gid = 1u, pid = null)
    return run {
        val ret = getpeereid(socket.asRawFd(), cred.uid, cred.gid)
        if (ret != 0) {
            return Result.Err<UCred, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.lastOsError())
        }
        val localStorageL119C12 = AddressableLocal(1, UnresolvedTypeLayout<PidT>())
        val pid by localStorageL119C12
        val pidSize = sizeOf<PidT>(typeLayoutT = UnresolvedTypeLayout<PidT>())
        val ret1 = getsockopt(socket.asRawFd(), SOL_LOCAL, LOCAL_PEERPID, ((localStorageL119C12.asMutPtr())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, pidSize)
        if (ret1 == 0 && pidSize == sizeOf<PidT>(typeLayoutT = UnresolvedTypeLayout<PidT>())) {
            cred.pid = pid
            Result.Ok<UCred, Error>(cred)
        } else {
            Result.Err<UCred, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.lastOsError())
        }
    }
}
