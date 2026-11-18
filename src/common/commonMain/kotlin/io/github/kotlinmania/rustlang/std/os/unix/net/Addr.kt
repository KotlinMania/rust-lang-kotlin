// port-lint: source os/unix/net/addr.rs
package io.github.kotlinmania.rustlang.std.os.unix.net

import io.github.kotlinmania.rustlang.core.bytestr.ByteStr
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.SimpleMessage
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.asMutPtr
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.constptr.len
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.len
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.libc.unix.bsd.SockaddrUn
import io.github.kotlinmania.rustlang.libc.unix.bsd.SocklenT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.AF_UNIX
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.os.net.linuxext.addr.SocketAddrExt
import io.github.kotlinmania.rustlang.std.os.raw.CChar
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.osstr.bytes.Slice
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.setthreaddescription.copyNonoverlapping

public val SUN_PATH_OFFSET: Int = 0

public fun sockaddrUn(path: Path): Result<Pair<SockaddrUn, SocklenT>, Error> {
    // SAFETY: All zeros is a valid representation for `sockaddrUn`.
    val addr: SockaddrUn  = SockaddrUn()
    addr.sunFamily = AF_UNIX.toUShort()
    val bytes = path.asOsStr().encodeToByteArray().map { it.toUByte() }
    if (bytes.contains(0)) {
        return Result.Err<Pair<SockaddrUn, SocklenT>, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "paths must not contain interior null bytes")))
    }
    if (bytes.size >= addr.sunPath.size) {
        return Result.Err<Pair<SockaddrUn, SocklenT>, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "path must be shorter than SUN_LEN")))
    }
    // SAFETY: `bytes` and `addr.sunPath` are not overlapping and
    // both point to valid memory.
    // NOTE: We zeroed the memory above, so the path is already null
    // terminated.
    copyNonoverlapping(bytes.asPtr(), addr.sunPath.asMutPtr(), bytes.size)
    var len = SUN_PATH_OFFSET + bytes.size
    when (val matchVal = bytes.get(0)) {
        0 -> {}
        null -> {}
        else -> {
            // on QNX7.1 and QNX8 the `len` value returned by the SUN_LEN
            // macro in its libc does not include the null byte in the count so
            // don't add it here to match what a C program passes to bind(2) and
            // similar functions
            if (true) {
                len += 1
            }
        }
    }
    return Result.Ok<Pair<SockaddrUn, SocklenT>, Error>(Pair(addr, len))
}

public sealed class AddressKind {
    public data object Unnamed : AddressKind()
    public data class Pathname(public var value: Path) : AddressKind()
    public data class Abstract(public var value: ByteStr) : AddressKind()
}

/**
 * An address associated with a Unix socket.
 *
 * # Examples
 *
 * use std.os.unix.net.UnixListener;
 *
 * let socket = match UnixListener.bind("/tmp/sock") (
 *     Ok(sock) => sock,
 *     Err(e) => (
 *         println("Couldn't bind: (e:?)");
 *         return
 *     )
 * );
 * let addr = socket.localAddr().expect("`UnixListener.localAddr` should not fail");
 * ```kotlin
 */
public open class SocketAddr(
    public var addr: SockaddrUn,
    public var len: SocklenT,
) : SocketAddrExt {
    public open fun clone(): SocketAddr = SocketAddr(addr, len)

    /**
     * Returns `true` if the address is unnamed.
     *
     * # Examples
     *
     * A named address:
     *
     * use std.os.unix.net.UnixListener;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixListener.bind("/tmp/sock")?;
     *     let addr = socket.localAddr().expect("`UnixListener.localAddr` should not fail");
     *     assertEq(addr.isUnnamed(), false);
     *     Ok(())
     * )
     * ```kotlin
     * An unnamed address: import std.os.unix.net.UnixDatagram; fun main() -> std.io.Result<Unit> { val socket = UnixDatagram.unbound(); val addr = socket.localAddr().expect("`UnixListener.localAddr` should not fail"); assertEq(addr.isUnnamed(), true); Ok(()) }
     * ```
     */
    public open fun isUnnamed(): Boolean =
        when (this.address()) {
            AddressKind.Unnamed -> true
            else -> false
        }

    /**
     * Returns the contents of this address if it is a `pathname` address.
     *
     * # Examples
     *
     * With a pathname:
     *
     * use std.os.unix.net.UnixListener;
     * use std.path.Path;
     *
     * fn main() -> std.io.Result<()> (
     *     let socket = UnixListener.bind("/tmp/sock")?;
     *     let addr = socket.localAddr().expect("`UnixListener.localAddr` should not fail");
     *     assertEq(addr.asPathname(), Some(Path.new("/tmp/sock")));
     *     Ok(())
     * )
     * ```kotlin
     * Without a pathname: import std.os.unix.net.UnixDatagram; fun main() -> std.io.Result<Unit> { val socket = UnixDatagram.unbound(); val addr = socket.localAddr().expect("`UnixListener.localAddr` should not fail"); assertEq(addr.asPathname(), None); Ok(()) }
     * ```
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
        val len = this.len - SUN_PATH_OFFSET
        val path = transmuteWithLayouts<List<CChar>, List<UByte>>(this.addr.sunPath, UnresolvedTypeLayout<List<CChar>>(), UnresolvedTypeLayout<List<UByte>>())
        // macOS seems to return a len of 16 and a zeroed sunPath for unnamed addresses
        return if (len == 0 || (true && this.addr.sunPath[0] == 0.toByte())) {
            AddressKind.Unnamed
        } else if (this.addr.sunPath[0] == 0.toByte()) {
            AddressKind.Abstract(ByteStr.fromBytes(path[1 until len]))
        } else {
            // linux adds a trailing NUL and counts it in the length, freebsd, netbsd
            // and qnx do not, and a caller may bind(2) without one either. unix(7)
            // gives the portable rule: strnlen(sunPath, len - offsetof(sunPath))
            val end = (Slice.memchr(0, path[0 until len]) ?: len)
            AddressKind.Pathname(OsStr.fromBytes(path[0 until end]).asRef())
        }
    }

    public override fun asAbstractName(): List<UByte>? =
        when (val matchVal = this.address()) {
            is AddressKind.Abstract -> {
                val (name) = matchVal
                name.asBytes()
            }
            else -> {
                null
            }
        }

    public open fun fmt(fmt: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> =
        when (val matchVal = this.address()) {
            AddressKind.Unnamed -> run { fmt.append("(unnamed)"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
            is AddressKind.Abstract -> {
                val (name) = matchVal
                run { fmt.append("$name (abstract)"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
            }
            is AddressKind.Pathname -> {
                val (path) = matchVal
                run { fmt.append("$path (pathname)"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
            }
        }

    public companion object {
        public fun new(f: (MutPtr, MutPtr) -> CInt): Result<SocketAddr, Error> {
            return run {
                val localStorageL107C12 = AddressableLocal(SockaddrUn(), UnresolvedTypeLayout<SockaddrUn>())
                val addr by localStorageL107C12
                val localStorageL108C12 = AddressableLocal(sizeOf<SockaddrUn>(typeLayoutT = UnresolvedTypeLayout<SockaddrUn>()).toInt(), UnresolvedTypeLayout<SocklenT>())
                val len by localStorageL108C12
                when (val tryResult = cvt(f((localStorageL107C12.asMutPtr()), RustMutableReference(localStorageL108C12.asMutPtr(), localStorageL108C12.layout)))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<SocketAddr, Error>(tryResult.errValue()) }
                SocketAddr.fromParts(addr, len)
            }
        }

        public fun fromParts(addr: SockaddrUn, len: SocklenT): Result<SocketAddr, Error> {
            var len1 = len
            if (false) {
                // on OpenBSD, getsockname(2) returns the actual size of the socket address,
                // and not the len of the content. Figure out the length for ourselves.
                // https://marc.info/?l=openbsd-bugs&m=170105481926736&w=2
                val sunPath: List<UByte> = transmuteWithLayouts<List<CChar>, List<UByte>>(addr.sunPath, UnresolvedTypeLayout<List<CChar>>(), UnresolvedTypeLayout<List<UByte>>())
                len1 = (Slice.memchr(0, sunPath)?.let({ newLen -> (newLen + SUN_PATH_OFFSET) }) ?: len1)
            }
            len1 = len1.min(sizeOf<SockaddrUn>(typeLayoutT = UnresolvedTypeLayout<SockaddrUn>()).toInt())
            if (len1 == 0) {
                // When there is a datagram from unnamed unix socket
                // linux returns zero bytes of address
                len1 = SUN_PATH_OFFSET
                // i.e., zero-length address
            } else if (addr.sunFamily != AF_UNIX.toUShort()) {
                return Result.Err<SocketAddr, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "file descriptor did not correspond to a Unix socket")))
            }
            return Result.Ok<SocketAddr, Error>(SocketAddr(addr = addr, len = len1))
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
         * use std.os.unix.net.SocketAddr;
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
         * import std.os.unix.net.SocketAddr;
         *
         * assert(SocketAddr.fromPathname("/path/with/\0/bytes").isErr());
         * ```
         */
        public fun fromPathname(path: Path): Result<SocketAddr, Error> = sockaddrUn(path).map({ (addr, len) -> SocketAddr(addr = addr, len = len) })

        public fun fromAbstractName(name: List<UByte>): Result<SocketAddr, Error> {
            val name1 = name
            return run {
                val addr: SockaddrUn  = SockaddrUn()
                addr.sunFamily = AF_UNIX.toUShort()
                if (name1.len() + 1 > addr.sunPath.size) {
                    return Result.Err<SocketAddr, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "abstract socket name must be shorter than SUN_LEN")))
                }
                ptr.copyNonoverlapping(name1.asPtr(), (MutPtr((addr.sunPath.asMutPtr()).rawAddress + ((1).toLong() * (PrimitiveLayouts.I8).strideBytes))).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, name1.len())
                val len = (SUN_PATH_OFFSET + 1 + name1.len())
                SocketAddr.fromParts(addr, len)
            }
        }
    }
}
