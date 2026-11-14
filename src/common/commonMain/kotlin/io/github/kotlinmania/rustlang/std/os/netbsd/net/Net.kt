// port-lint: source os/netbsd/net.rs
package io.github.kotlinmania.rustlang.std.os.netbsd.net

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixDatagram
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixStream
import io.github.kotlinmania.rustlang.std.thread.Result

// NetBSD-specific networking functionality.

/**
 * NetBSD-specific functionality for `AF_UNIX` sockets [UnixDatagram]
 * and [UnixStream].
 *
 * [UnixDatagram]: net.UnixDatagram
 * [UnixStream]: net.UnixStream
 */
public interface UnixSocketExt {
    /**
     * Query the current setting of socket option `LOCAL_CREDS`.
     */
    public fun localCreds(): Result<Boolean>

    /**
     * Enable or disable socket option `LOCAL_CREDS`.
     *
     * This option enables the credentials of the sending process to be
     * received as a control message in [AncillaryData].
     *
     * [AncillaryData]: net.AncillaryData
     *
     * # Examples
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.netbsd.net.UnixSocketExt;
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> {
     *     let sock = UnixDatagram.unbound()?;
     *     sock.setLocalCreds(true).expect("setLocalCreds failed");
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun setLocalCreds(localCreds: Boolean): Result<Unit>

    /**
     * Gets a filter name if one had been set previously on the socket.
     */
    public fun acceptfilter(): Result<CStr>

    /**
     * Set or disable a filter on the socket to filter incoming connections
     * to defer it before accept(2)
     */
    public fun setAcceptfilter(name: CStr): Result<Unit>
}

public fun UnixDatagram.localCreds(): Result<Boolean> = this.asInner().localCreds()

public fun UnixDatagram.setLocalCreds(localCreds: Boolean): Result<Unit> = this.asInner().setLocalCreds(localCreds)

public fun UnixDatagram.acceptfilter(): Result<CStr> = this.asInner().acceptfilter()

public fun UnixDatagram.setAcceptfilter(name: CStr): Result<Unit> = this.asInner().setAcceptfilter(name)

public fun UnixStream.localCreds(): Result<Boolean> = this.asInner().localCreds()

public fun UnixStream.setLocalCreds(localCreds: Boolean): Result<Unit> = this.asInner().setLocalCreds(localCreds)

public fun UnixStream.acceptfilter(): Result<CStr> = this.asInner().acceptfilter()

public fun UnixStream.setAcceptfilter(name: CStr): Result<Unit> = this.asInner().setAcceptfilter(name)
