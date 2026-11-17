// port-lint: source os/windows/net/addr.rs
package io.github.kotlinmania.rustlang.std.os.windows.net

import io.github.kotlinmania.rustlang.core.bytestr.ByteStr
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.asMutPtr
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.AF_UNIX
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SockaddrUn
import io.github.kotlinmania.rustlang.std.sys.pal.windows.cvtNz

public fun sockaddrUn(path: Path): Result<Pair<SockaddrUn, Int>, Error> {
    // SAFETY: All zeros is a valid representation for `sockaddrUn`.
    val addr: SockaddrUn  = SockaddrUn()
    addr.sunFamily = AF_UNIX
    // path to UTF-8 bytes
    val bytes = when (val tryResult = path.toStr().okOr(constError(ErrorKind.InvalidInput, "path must be valid UTF-8"))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Pair<SockaddrUn, Int>, Error>(tryResult.error) }.asBytes()
    if (bytes.size >= addr.sunPath.size) {
        return Result.Err<Pair<SockaddrUn, Int>, Error>(constError(ErrorKind.InvalidInput, "path too long"))
    }
    // SAFETY: `bytes` and `addr.sunPath` are not overlapping and
    // both point to valid memory.
    // NOTE: We zeroed the memory above, so the path is already null
    // terminated.
    ptr.copyNonoverlapping(bytes.asPtr(), addr.sunPath.asMutPtr(), bytes.size)
    val len = SUN_PATH_OFFSET + bytes.size + 1
    return Result.Ok<Pair<SockaddrUn, Int>, Error>(Pair(addr, len))
}

public val SUN_PATH_OFFSET: Int = 0

public open class SocketAddr(
    public var addr: SockaddrUn,
    public var len: UInt,
) {
    public open fun fmt(fmt: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> =
        when (val matchVal = this.address()) {
            AddressKind.Unnamed -> run { fmt.append("(unnamed)"); Result.Ok(Unit) }
            is AddressKind.Abstract -> {
                val (name) = matchVal
                run { fmt.append("$name (abstract)"); Result.Ok(Unit) }
            }
            is AddressKind.Pathname -> {
                val (path) = matchVal
                run { fmt.append("$path (pathname)"); Result.Ok(Unit) }
            }
        }

    /**
     * Returns the contents of this address if it is a `pathname` address.
     *
     * # Examples
     *
     * With a pathname:
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixListener;
     * use std.path.Path;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixListener.bind("/tmp/sock")?;
     *     let addr = socket.localAddr().expect("Couldn't get local address");
     *     assertEq(addr.asPathname(), Some(Path.new("/tmp/sock")));
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun asPathname(): Path? =
        when (val matchVal = this.address()) {
            is AddressKind.Pathname -> {
                val (path) = matchVal
                path
            }
            else -> {
                null
            }
        }

    public open fun address(): AddressKind {
        val len = this.len.toInt() - SUN_PATH_OFFSET
        val path = transmuteWithLayouts<List<Byte>, List<UByte>>(this.addr.sunPath, UnresolvedTypeLayout<List<Byte>>(), UnresolvedTypeLayout<List<UByte>>())
        return if (len == 0) {
            AddressKind.Unnamed
        } else if (this.addr.sunPath[0] == 0.toByte()) {
            AddressKind.Abstract(ByteStr.fromBytes(path[1 until len]))
        } else {
            AddressKind.Pathname(OsStr.fromEncodedBytesUnchecked(path[0 until len - 1]).asRef())
        }
    }

    /**
     * Returns `true` if the address is unnamed.
     *
     * # Examples
     *
     * A named address:
     *
     * @file:feature(windowsUnixDomainSockets)
     * use std.os.windows.net.UnixListener;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixListener.bind("/tmp/sock")?;
     *     let addr = socket.localAddr().expect("Couldn't get local address");
     *     assertEq(addr.isUnnamed(), false);
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun isUnnamed(): Boolean =
        when (this.address()) {
            AddressKind.Unnamed -> true
            else -> false
        }

    public companion object {
        public fun new(f: (MutPtr, MutPtr) -> Int): Result<SocketAddr, Error> {
            return run {
                val localStorageL60C12 = AddressableLocal(SockaddrUn(), UnresolvedTypeLayout<SockaddrUn>())
                val addr by localStorageL60C12
                val localStorageL61C12 = AddressableLocal(sizeOf<SockaddrUn>(typeLayoutT = UnresolvedTypeLayout<SockaddrUn>()).toInt(), PrimitiveLayouts.I32)
                val len by localStorageL61C12
                when (val tryResult = cvtNz(f(localStorageL60C12.asMutPtr(), RustMutableReference(localStorageL61C12.asMutPtr(), localStorageL61C12.layout)))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<SocketAddr, Error>(tryResult.error) }
                SocketAddr.fromParts(addr, len)
            }
        }

        public fun fromParts(addr: SockaddrUn, len: Int): Result<SocketAddr, Error> =
            if (addr.sunFamily != AF_UNIX) {
                Result.Err<SocketAddr, Error>(constError(ErrorKind.InvalidInput, "invalid address family"))
            } else if (len < SUN_PATH_OFFSET || len > sizeOf<SockaddrUn>(typeLayoutT = UnresolvedTypeLayout<SockaddrUn>()).toInt()) {
                Result.Err<SocketAddr, Error>(constError(ErrorKind.InvalidInput, "invalid address length"))
            } else {
                Result.Ok<SocketAddr, Error>(SocketAddr(addr = addr, len = len.toUInt()))
            }

        /**
         * Constructs a `SockAddr` with the family `AF_UNIX` and the provided path.
         *
         * # Errors
         *
         * Returns an error if the path is longer than `SUN_LEN` or if it contains
         * NULL bytes.
         *
         * # Examples
         *
         * @file:feature(windowsUnixDomainSockets)
         * use std.os.windows.net.SocketAddr;
         * use std.path.Path;
         *
         * # fn main() -> std.io.Result<()> (
         * let address = SocketAddr.fromPathname("/path/to/socket")?;
         * assertEq(address.asPathname(), Some(Path.new("/path/to/socket")));
         * # Ok(())
         * # )
         * ```kotlin
         *
         * Creating a `SocketAddr` with a NULL byte results in an error.
         *
         * @file:feature(windowsUnixDomainSockets)
         * import std.os.windows.net.SocketAddr;
         *
         * assert(SocketAddr.fromPathname("/path/with/\0/bytes").isErr());
         * ```
         */
        public fun fromPathname(path: Path): Result<SocketAddr, Error> = sockaddrUn(path).map({ (addr, len) -> SocketAddr(addr = addr, len = len.toUInt()) })
    }
}

public sealed class AddressKind {
    public data object Unnamed : AddressKind()
    public data class Pathname(public var value: Path) : AddressKind()
    public data class Abstract(public var value: ByteStr) : AddressKind()
}
