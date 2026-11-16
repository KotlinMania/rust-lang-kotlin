@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.net.connection.socket

import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv4Addr
import io.github.kotlinmania.rustlang.core.net.ipaddr.Ipv6Addr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddr
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV4
import io.github.kotlinmania.rustlang.core.net.socketaddr.SocketAddrV6
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runWithCstr
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix.Socket
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix.cvtGai
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.unix.init
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt

// ///////////////////////////////////////////////////////////////////////////
// address conversions
// ///////////////////////////////////////////////////////////////////////////
public fun ipV4AddrToC(addr: Ipv4Addr): c.InAddr {
    // `sAddr` is stored as BE on all machines and the array is in BE order.
    // So the native endian conversion method is used so that it's never swapped.
    return c.InAddr(sAddr = run { val bytesTmp0 = addr.octets(); ((bytesTmp0[3].toUInt() shl 24) or (bytesTmp0[2].toUInt() shl 16) or (bytesTmp0[1].toUInt() shl 8) or bytesTmp0[0].toUInt()) })
}

public fun ipV6AddrToC(addr: Ipv6Addr): c.In6Addr = c.In6Addr(s6Addr = addr.octets())

public fun ipV4AddrFromC(addr: c.InAddr): Ipv4Addr = Ipv4Addr.from(run { val rawBits = addr.sAddr; ubyteArrayOf(rawBits.toUByte(), (rawBits shr 8).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 24).toUByte()) })

public fun ipV6AddrFromC(addr: c.In6Addr): Ipv6Addr = Ipv6Addr.from(addr.s6Addr)

public fun socketAddrV4ToC(addr: SocketAddrV4): c.SockaddrIn = c.SockaddrIn().copy(sinFamily = c.AF_INET.toUShort(), sinPort = addr.port().toBe(), sinAddr = ipV4AddrToC(addr.ip()))

public fun socketAddrV6ToC(addr: SocketAddrV6): c.SockaddrIn6 = c.SockaddrIn6().copy(sin6Family = c.AF_INET6.toUShort(), sin6Port = addr.port().toBe(), sin6Addr = ipV6AddrToC(addr.ip()), sin6Flowinfo = addr.flowinfo(), sin6ScopeId = addr.scopeId())

public fun socketAddrV4FromC(addr: c.SockaddrIn): SocketAddrV4 = SocketAddrV4.new(ipV4AddrFromC(addr.sinAddr), run { val swapVal0 = (addr.sinPort); (((((swapVal0).toUInt() and 0xFFu) shl 8) or (((swapVal0).toUInt() shr 8) and 0xFFu)).toUShort()) })

public fun socketAddrV6FromC(addr: c.SockaddrIn6): SocketAddrV6 = SocketAddrV6.new(ipV6AddrFromC(addr.sin6Addr), run { val swapVal0 = (addr.sin6Port); (((((swapVal0).toUInt() and 0xFFu) shl 8) or (((swapVal0).toUInt() shr 8) and 0xFFu)).toUShort()) }, addr.sin6Flowinfo, addr.sin6ScopeId)

public fun socketAddrToC(addr: SocketAddr): Pair<SocketAddrCRepr, c.SocklenT> =
    when (addr) {
        is SocketAddr.V4 -> {
            val (a) = addr
            val sockaddr = SocketAddrCRepr(v4 = socketAddrV4ToC(a))
            Pair(sockaddr, sizeOf<c.SockaddrIn>(typeLayoutT = UnresolvedTypeLayout<c.SockaddrIn>()))
        }
        is SocketAddr.V6 -> {
            val (a1) = addr
            val sockaddr1 = SocketAddrCRepr(v6 = socketAddrV6ToC(a1))
            Pair(sockaddr1, sizeOf<c.SockaddrIn6>(typeLayoutT = UnresolvedTypeLayout<c.SockaddrIn6>()))
        }
    }

public fun addrFamily(addr: SocketAddr): CInt =
    when (addr) {
        is SocketAddr.V4 -> c.AF_INET
        is SocketAddr.V6 -> c.AF_INET6
    }

/**
 * Converts the C socket address stored in `storage` to a Rust `SocketAddr`.
 *
 * # Safety
 * * `storage` must contain a valid C socket address whose length is no larger
 *   than `len`.
 */
public fun socketAddrFromC(storage: ConstPtr, len: Int): Result<SocketAddr, Error> =
    when (((UnresolvedTypeLayout<c.SockaddrStorage>()).readNative((storage).rawAddress).ssFamily).toInt()) {
        c.afInet -> {
            check(len >= sizeOf<c.SockaddrIn>(typeLayoutT = UnresolvedTypeLayout<c.SockaddrIn>()))
            Result.Ok<SocketAddr, Error>(SocketAddr.v4(socketAddrV4FromC((UnresolvedTypeLayout<c.SockaddrIn>()).readNative((((storage).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress))))
        }
        c.afInet6 -> {
            check(len >= sizeOf<c.SockaddrIn6>(typeLayoutT = UnresolvedTypeLayout<c.SockaddrIn6>()))
            Result.Ok<SocketAddr, Error>(SocketAddr.v6(socketAddrV6FromC((UnresolvedTypeLayout<c.SockaddrIn6>()).readNative((((storage).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })).rawAddress))))
        }
        else -> Result.Err<SocketAddr, Error>(constError(ErrorKind.InvalidInput, "invalid argument"))
    }

// ///////////////////////////////////////////////////////////////////////////
// sockaddr and misc bindings
// ///////////////////////////////////////////////////////////////////////////

/**
 * Sets the value of a socket option.
 *
 * # Safety
 * `T` must be the type associated with the given socket option.
 */
public fun <T> setsockopt(sock: Socket, level: CInt, optionName: CInt, optionValue: T, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<Unit, Error> {
    val optionLen = sizeOf<T>(typeLayoutT = typeLayoutT)
    // SAFETY:
    // * `sock` is opened for the duration of this call, as `sock` owns the socket.
    // * the pointer to `optionValue` is readable at a size of `sizeOf<T>`
    // bytes
    // * the value of `optionValue` has a valid type for the given socket option
    // (guaranteed by caller).
    when (val tryResult = cvt(c.setsockopt(sock.asRaw(), level, optionName, (addrOf(optionValue)), optionLen))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
    return Result.Ok<Unit, Error>(Unit)
}

/**
 * Gets the value of a socket option.
 *
 * # Safety
 * `T` must be the type associated with the given socket option.
 */
public fun <T> getsockopt(sock: Socket, level: CInt, optionName: CInt, typeLayoutT: TypeLayout<T> = UnresolvedTypeLayout()): Result<T, Error> {
    val optionValue = MaybeUninit.zeroed(typeLayoutT)
    val optionLen = sizeOf<T>(typeLayoutT = typeLayoutT)
    // SAFETY:
    // * `sock` is opened for the duration of this call, as `sock` owns the socket.
    // * the pointer to `optionValue` is writable and the stack allocation has
    // space for `sizeOf<T>` bytes.
    when (val tryResult = cvt(c.getsockopt(sock.asRaw(), level, optionName, optionValue.asMutPtr().cast(), optionLen))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<T, Error>(tryResult.error) }
    // SAFETY: the `getsockopt` call succeeded and the caller guarantees that
    // `T` is the type of this option, thus `optionValue` must have
    // been initialized by the system.
    return Result.Ok<T, Error>(optionValue.assumeInit())
}

/**
 * Wraps a call to a platform function that returns a socket address.
 *
 * # Safety
 * * if `f` returns a success (i.e. `cvt` returns `Ok` when called on the
 *   return value), the buffer provided to `f` must have been initialized
 *   with a valid C socket address, the length of which must be written
 *   to the second argument.
 */
public fun sockname(f: (MutPtr, MutPtr) -> CInt): Result<SocketAddr, Error> {
    val storage = MaybeUninit.zeroed(UnresolvedTypeLayout<c.SockaddrStorage>())
    val len = sizeOf<c.SockaddrStorage>(typeLayoutT = UnresolvedTypeLayout<c.SockaddrStorage>())
    when (val tryResult = cvt(f(storage.asMutPtr().cast(), len))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<SocketAddr, Error>(tryResult.error) }
    // SAFETY:
    // The caller guarantees that the storage has been successfully initialized
    // and its size written to `len` if `f` returns a success.
    return socketAddrFromC(storage.asPtr(), len)
}

public fun toIpv6mrInterface(value: UInt): CInt = value.toInt()

public fun lookupHost(host: String, port: UShort): Result<LookupHost, Error> {
    init()
    return runWithCstr(host.encodeToByteArray().map { it.toUByte() }, { cHost ->
        var hints: c.Addrinfo = MaybeUninit.zeroed(UnresolvedTypeLayout<c.Addrinfo>()).assumeInit()
        hints.aiSocktype = c.SOCK_STREAM
        var res = MutPtr(0L)
        cvtGai(c.getaddrinfo(cHost.asPtr(), ConstPtr(0L), hints, res)).map({ LookupHost(original = res, cur = res, port = port) })
    })
}
