// port-lint: source os/net/linux_ext/socket.rs
package io.github.kotlinmania.rustlang.std.os.net.linuxext.socket

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.os.unix.net.UnixDatagram
import io.github.kotlinmania.rustlang.os.unix.net.UnixStream
import io.github.kotlinmania.rustlang.std.io.error.Error

// Linux and Android-specific socket functionality.

/**
 * Linux-specific functionality for `AF_UNIX` sockets [UnixDatagram]
 * and [UnixStream].
 *
 * [UnixDatagram]: net.UnixDatagram
 * [UnixStream]: net.UnixStream
 */
public interface UnixSocketExt {
    /**
     * Query the current setting of socket option `SO_PASSCRED`.
     */
    public fun passcred(): Result<Boolean, Error>

    /**
     * Enable or disable socket option `SO_PASSCRED`.
     *
     * This option enables the credentials of the sending process to be
     * received as a control message in [AncillaryData].
     *
     * [AncillaryData]: net.AncillaryData
     *
     * # Examples
     *
     * @file:feature(unixSocketAncillaryData)
     * @cfg(targetOs = "linux")
     * use std.os.linux.net.UnixSocketExt;
     * @cfg(targetOs = "android")
     * use std.os.android.net.UnixSocketExt;
     * use std.os.unix.net.UnixDatagram;
     *
     * fn main() -> std.io.Result<()> {
     *     let sock = UnixDatagram.unbound()?;
     *     sock.setPasscred(true).expect("setPasscred failed");
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun setPasscred(passcred: Boolean): Result<Unit, Error>
}

public fun UnixDatagram.passcred(): Result<Boolean, Error> = this.asInner().passcred()

public fun UnixDatagram.setPasscred(passcred: Boolean): Result<Unit, Error> = this.asInner().setPasscred(passcred)

public fun UnixStream.passcred(): Result<Boolean, Error> = this.asInner().passcred()

public fun UnixStream.setPasscred(passcred: Boolean): Result<Unit, Error> = this.asInner().setPasscred(passcred)
