// port-lint: source os/net/linux_ext/tcp.rs
package io.github.kotlinmania.rustlang.std.os.net.linuxext.tcp

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.net.tcp.TcpStream

// Linux and Android-specific tcp extensions to primitives in the [std.net] module.

/**
 * Os-specific extensions for [TcpStream]
 *
 * [TcpStream]: net.TcpStream
 */
public interface TcpStreamExt {
    /**
     * Enable or disable `TCP_QUICKACK`.
     *
     * This flag causes Linux to eagerly send ACKs rather than delaying them.
     * Linux may reset this flag after further operations on the socket.
     *
     * See [`man.tcp`](https://man7.org/linux/man-pages/man7/tcp.7.html) and
     * [TCP delayed acknowledgement](https://en.wikipedia.org/wiki/TcpDelayedAcknowledgment)
     * for more information.
     *
     * # Examples
     *
     * use std.net.TcpStream;
     * @cfg(targetOs = "linux")
     * use std.os.linux.net.TcpStreamExt;
     * @cfg(targetOs = "android")
     * use std.os.android.net.TcpStreamExt;
     *
     * let stream = TcpStream.connect("127.0.0.1:8080")
     *         .expect("Couldn't connect to the server...");
     * stream.setQuickack(true).expect("setQuickack call failed");
     * ```kotlin
     */
    public fun setQuickack(quickack: Boolean): Result<Unit, Error>

    /**
     * Gets the value of the `TCP_QUICKACK` option on this socket.
     *
     * For more information about this option, see [TcpStreamExt.setQuickack].
     *
     * # Examples
     *
     * use std.net.TcpStream;
     * @cfg(targetOs = "linux")
     * use std.os.linux.net.TcpStreamExt;
     * @cfg(targetOs = "android")
     * use std.os.android.net.TcpStreamExt;
     *
     * let stream = TcpStream.connect("127.0.0.1:8080")
     *         .expect("Couldn't connect to the server...");
     * stream.setQuickack(true).expect("setQuickack call failed");
     * assertEq(stream.quickack().unwrapOr(false), true);
     * ```kotlin
     */
    public fun quickack(): Result<Boolean, Error>

    /**
     * A socket listener will be awakened solely when data arrives.
     *
     * The `accept` argument set the maximum delay until the
     * data is available to read, reducing the number of short lived
     * connections without data to process.
     * Contrary to other platforms `SO_ACCEPTFILTER` feature equivalent, there is
     * no necessity to set it after the `listen` call.
     * Note that the delay is expressed as Duration from user's perspective
     * the call rounds it down to the nearest second expressible as a `cInt`.
     *
     * See [`man.tcp`](https://man7.org/linux/man-pages/man7/tcp.7.html)
     *
     * # Examples
     *
     * ```kotlin
     * import std.net.TcpStream
     * import std.os.linux.net.TcpStreamExt
     * import std.time.Duration
     *
     * @feature(tcpDeferaccept)
     * public fun doctestEntry() {
     *     val stream = TcpStream.connect("127.0.0.1:8080").expect("Couldn't connect to the server...")
     *     stream.setDeferaccept(Duration.fromSecs(1uL)).expect("setDeferaccept call failed")
     * }
     * ```
     */
    public fun setDeferaccept(accept: Duration): Result<Unit, Error>

    /**
     * Gets the accept delay value of the `TCP_DEFER_ACCEPT` option.
     *
     * For more information about this option, see [TcpStreamExt.setDeferaccept].
     *
     * # Examples
     *
     * @file:feature(tcpDeferaccept)
     * use std.net.TcpStream;
     * use std.os.linux.net.TcpStreamExt;
     * use std.time.Duration;
     *
     * let stream = TcpStream.connect("127.0.0.1:8080")
     *         .expect("Couldn't connect to the server...");
     * stream.setDeferaccept(Duration.fromSecs(1u64)).expect("setDeferaccept call failed");
     * assertEq(stream.deferaccept().unwrap(), Duration.fromSecs(1u64));
     * ```kotlin
     */
    public fun deferaccept(): Result<Duration, Error>
}

public fun TcpStream.setQuickack(quickack: Boolean): Result<Unit, Error> = this.asInner().asInner().setQuickack(quickack)

public fun TcpStream.quickack(): Result<Boolean, Error> = this.asInner().asInner().quickack()

public fun TcpStream.setDeferaccept(accept: Duration): Result<Unit, Error> = this.asInner().asInner().setDeferaccept(accept)

public fun TcpStream.deferaccept(): Result<Duration, Error> = this.asInner().asInner().deferaccept()
