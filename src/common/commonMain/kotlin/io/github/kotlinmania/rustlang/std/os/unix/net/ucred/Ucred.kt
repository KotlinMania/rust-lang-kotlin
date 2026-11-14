// port-lint: source os/unix/net/ucred.rs
package io.github.kotlinmania.rustlang.std.os.unix.net.ucred

import io.github.kotlinmania.rustlang.libc.helenos.PidT
import io.github.kotlinmania.rustlang.libc.unix.GidT
import io.github.kotlinmania.rustlang.libc.unix.UidT

/**
 * Credentials for a UNIX process for credentials passing.
 */
public open class UCred(
    /**
     * The UID part of the peer credential. This is the effective UID of the process at the domain
     * socket's endpoint.
     */
    public var uid: UidT,
    /**
     * The GID part of the peer credential. This is the effective GID of the process at the domain
     * socket's endpoint.
     */
    public var gid: GidT,
    /**
     * The PID part of the peer credential. This field is optional because the PID part of the
     * peer credentials is not supported on every platform. On platforms where the mechanism to
     * discover the PID exists, this field will be populated to the PID of the process at the
     * domain socket's endpoint. Otherwise, it will be set to None.
     */
    public var pid: PidT?,
) {
    public open fun clone(): UCred = UCred(uid, gid, pid)
}
