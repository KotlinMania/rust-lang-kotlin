// port-lint: source os/unix/net/ucred.rs
package io.github.kotlinmania.rustlang.std.os.unix.net.ucred.implapple

import io.github.kotlinmania.libc.fuchsia.PidT
import io.github.kotlinmania.libc.getpeereid
import io.github.kotlinmania.libc.getsockopt
import io.github.kotlinmania.libc.unix.bsd.apple.LOCAL_PEERPID
import io.github.kotlinmania.libc.unix.bsd.apple.SOL_LOCAL
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixStream
import io.github.kotlinmania.rustlang.std.os.unix.net.ucred.UCred

public fun peerCred(socket: UnixStream): Result<UCred, Error> {
    val cred = UCred(uid = 1u, gid = 1u, pid = null)
    return run {
        val ret = getpeereid(socket.asRawFd(), cred.uid, cred.gid)
        if (ret != 0) {
            return Result.Err(Error.lastOsError())
        }
        val localStorageL119C12 = AddressableLocal(1, ObjectSlotLayout<PidT>())
        val pid by localStorageL119C12
        val pidSize = sizeOf<PidT>(typeLayoutT = ObjectSlotLayout<PidT>())
        val ret1 = getsockopt(socket.asRawFd(), SOL_LOCAL, LOCAL_PEERPID, ((localStorageL119C12.asMutPtr())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, pidSize)
        if (ret1 == 0 && pidSize == sizeOf<PidT>(typeLayoutT = ObjectSlotLayout<PidT>())) {
            cred.pid = pid
            Result.Ok(cred)
        } else {
            Result.Err(Error.lastOsError())
        }
    }
}
