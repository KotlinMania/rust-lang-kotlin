// port-lint: source os/unix/net/ucred.rs
package io.github.kotlinmania.rustlang.std.os.unix.net.ucred.impllinux

import io.github.kotlinmania.libc.fuchsia.SOL_SOCKET
import io.github.kotlinmania.libc.fuchsia.SO_PEERCRED
import io.github.kotlinmania.libc.fuchsia.Ucred
import io.github.kotlinmania.libc.getsockopt
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixStream
import io.github.kotlinmania.rustlang.std.os.unix.net.ucred.UCred

public fun peerCred(socket: UnixStream): Result<UCred, Error> {
    val ucredSize = sizeOf<Ucred>(typeLayoutT = ObjectSlotLayout<Ucred>())
    // Trivial sanity checks.
    check(sizeOf<UInt>(typeLayoutT = PrimitiveLayouts.U32) <= sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64))
    check(ucredSize <= UInt.MAX_VALUE.toInt())
    val ucredSize1 = ucredSize
    val localStorageL57C8 = AddressableLocal(Ucred(pid = 1, uid = 1, gid = 1), ObjectSlotLayout<Ucred>())
    val ucred by localStorageL57C8
    return run {
        val ret = getsockopt(socket.asRawFd(), SOL_SOCKET, SO_PEERCRED, ((localStorageL57C8.asMutPtr())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, ucredSize1)
        if (ret == 0 && ucredSize1 == sizeOf(ucred, typeLayoutT = ObjectSlotLayout<Ucred>())) {
            Result.Ok(UCred(uid = ucred.uid, gid = ucred.gid, pid = ucred.pid))
        } else {
            Result.Err(Error.lastOsError())
        }
    }
}
