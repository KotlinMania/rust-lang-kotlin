// port-lint: source os/freebsd/net.rs
package io.github.kotlinmania.rustlang.std.os.freebsd.net

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixDatagram
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixStream
import io.github.kotlinmania.rustlang.std.thread.Result

// FreeBSD-specific networking functionality.

/**
 * FreeBSD-specific functionality for `AF_UNIX` sockets [UnixDatagram]
 * and [UnixStream].
 *
 * [UnixDatagram]: net.UnixDatagram
 * [UnixStream]: net.UnixStream
 */
public interface UnixSocketExt {
    /**
     * Query the current setting of socket option `LOCAL_CREDS_PERSISTENT`.
     */
    public fun localCredsPersistent(): Result<Boolean>

    /**
     * Enable or disable socket option `LOCAL_CREDS_PERSISTENT`.
     *
     * This option enables the credentials of the sending process to be
     * received as a control message in [AncillaryData].
     *
     * [AncillaryData]: net.AncillaryData
     *
     * # Examples
     *
     * @file:feature(unixSocketAncillaryData)
     * use std.os.freebsd.net.UnixSocketExt;
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> {
     *     let sock = UnixDatagram.unbound()?;
     *     sock.setLocalCredsPersistent(true).expect("setLocalCredsPersistent failed");
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun setLocalCredsPersistent(localCredsPersistent: Boolean): Result<Unit>

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

public fun UnixDatagram.localCredsPersistent(): Result<Boolean> = this.asInner().localCredsPersistent()

public fun UnixDatagram.setLocalCredsPersistent(localCredsPersistent: Boolean): Result<Unit> = this.asInner().setLocalCredsPersistent(localCredsPersistent)

public fun UnixDatagram.acceptfilter(): Result<CStr> = this.asInner().acceptfilter()

public fun UnixDatagram.setAcceptfilter(name: CStr): Result<Unit> = this.asInner().setAcceptfilter(name)

public fun UnixStream.localCredsPersistent(): Result<Boolean> = this.asInner().localCredsPersistent()

public fun UnixStream.setLocalCredsPersistent(localCredsPersistent: Boolean): Result<Unit> = this.asInner().setLocalCredsPersistent(localCredsPersistent)

public fun UnixStream.acceptfilter(): Result<CStr> = this.asInner().acceptfilter()

public fun UnixStream.setAcceptfilter(name: CStr): Result<Unit> = this.asInner().setAcceptfilter(name)
