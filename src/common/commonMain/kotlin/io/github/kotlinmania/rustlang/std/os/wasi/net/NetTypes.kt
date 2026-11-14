package io.github.kotlinmania.rustlang.std.os.wasi.net

import io.github.kotlinmania.rustlang.std.thread.Result

/**
 * WASI-specific extensions to [std.net.TcpListener].
 */
public interface TcpListenerExt {
    /**
     * Accept a socket.
     *
     * This corresponds to the `sockAccept` syscall.
     */
    public fun sockAccept(flags: UShort): Result<UInt>
}
