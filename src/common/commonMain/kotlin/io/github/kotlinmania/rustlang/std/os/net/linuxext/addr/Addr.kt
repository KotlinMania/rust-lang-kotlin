// port-lint: source os/net/linux_ext/addr.rs
package io.github.kotlinmania.rustlang.std.os.net.linuxext.addr

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr

// Linux and Android-specific extensions to socket addresses.

/**
 * Platform-specific extensions to [SocketAddr].
 */
public interface SocketAddrExt {
    /**
     * Creates a Unix socket address in the abstract namespace.
     *
     * The abstract namespace is a Linux-specific extension that allows Unix
     * sockets to be bound without creating an entry in the filesystem.
     * Abstract sockets are unaffected by filesystem layout or permissions,
     * and no cleanup is necessary when the socket is closed.
     *
     * An abstract socket address name may contain any bytes, including zero.
     *
     * # Errors
     *
     * Returns an error if the name is longer than `SUN_LEN - 1`.
     *
     * # Examples
     *
     * use std.os.unix.net::{UnixListener, SocketAddr};
     * @cfg(targetOs = "linux")
     * use std.os.linux.net.SocketAddrExt;
     * @cfg(targetOs = "android")
     * use std.os.android.net.SocketAddrExt;
     *
     * fn main() -> std.io.Result<()> {
     *     let addr = SocketAddr.fromAbstractName(b"hidden")?;
     *     let listener = match UnixListener.bindAddr(&addr) {
     *         Ok(sock) => sock,
     *         Err(err) => {
     *             println("Couldn't bind: {err:?}");
     *             return Err(err);
     *         }
     *     };
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun fromAbstractName(name: List<UByte>): Result<SocketAddr, Error>

    /**
     * Returns the contents of this address if it is in the abstract namespace.
     *
     * # Examples
     *
     * use std.os.unix.net::{UnixListener, SocketAddr};
     * @cfg(targetOs = "linux")
     * use std.os.linux.net.SocketAddrExt;
     * @cfg(targetOs = "android")
     * use std.os.android.net.SocketAddrExt;
     *
     * fn main() -> std.io.Result<()> {
     *     let name = b"hidden";
     *     let nameAddr = SocketAddr.fromAbstractName(name)?;
     *     let socket = UnixListener.bindAddr(&nameAddr)?;
     *     let localAddr = socket.localAddr().expect("Couldn't get local address");
     *     assertEq(localAddr.asAbstractName(), Some(&name[..]));
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun asAbstractName(): List<UByte>?
}
