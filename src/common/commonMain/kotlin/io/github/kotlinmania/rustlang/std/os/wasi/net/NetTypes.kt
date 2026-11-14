package io.github.kotlinmania.rustlang.std.os.wasi.net

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error

/**
 * WASI-specific extensions to [std.net.TcpListener].
 */
public interface TcpListenerExt {
    /**
     * Accept a socket.
     *
     * This corresponds to the `sockAccept` syscall.
     */
    public fun sockAccept(flags: UShort): Result<UInt, Error>
}
