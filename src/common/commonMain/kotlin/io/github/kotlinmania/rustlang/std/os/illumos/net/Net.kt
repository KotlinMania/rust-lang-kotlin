// port-lint: source os/illumos/net.rs
package io.github.kotlinmania.rustlang.std.os.illumos.net

import io.github.kotlinmania.rustlang.std.os.unix.net.UnixDatagram
import io.github.kotlinmania.rustlang.std.os.unix.net.UnixStream
import io.github.kotlinmania.rustlang.std.thread.Result

// illumos-specific networking functionality.

/**
 * illumos-specific functionality for `AF_UNIX` sockets [UnixDatagram]
 * and [UnixStream].
 *
 * [UnixDatagram]: net.UnixDatagram
 * [UnixStream]: net.UnixStream
 */
public interface UnixSocketExt {
    /**
     * Enables exclusive binding on the socket.
     *
     * If true and if the socket had been set with `SO_REUSEADDR`,
     * it neutralises its effect.
     * See [`man.tcp`](https://docs.oracle.com/cd/E88353_01/html/E37843/setsockopt-3c.html)
     */
    public fun soExclbind(excl: Boolean): Result<Unit>

    /**
     * Get the bind exclusivity bind state of the socket.
     */
    public fun exclbind(): Result<Boolean>
}

public fun UnixDatagram.exclbind(): Result<Boolean> = this.asInner().exclbind()

public fun UnixDatagram.soExclbind(excl: Boolean): Result<Unit> = this.asInner().setExclbind(excl)

public fun UnixStream.exclbind(): Result<Boolean> = this.asInner().exclbind()

public fun UnixStream.soExclbind(excl: Boolean): Result<Unit> = this.asInner().setExclbind(excl)
