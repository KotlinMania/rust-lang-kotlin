// port-lint: source os/solid/io.rs
package io.github.kotlinmania.rustlang.std.os.solid.io

import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.Rc
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.io.IsTerminal
import io.github.kotlinmania.rustlang.std.net.tcp.TcpListener
import io.github.kotlinmania.rustlang.std.net.tcp.TcpStream
import io.github.kotlinmania.rustlang.std.net.udp.UdpSocket
import io.github.kotlinmania.rustlang.std.sync.Arc
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.net.connection.motor.Socket
import io.github.kotlinmania.rustlang.std.sys.net.connection.socket.solid.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.close
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.sockets.dup

// SOLID-specific extensions to general I/O primitives
//
// Just like raw pointers, raw SOLID Sockets file descriptors point to
// resources with dynamic scopes, and they can dangle if they outlive their
// resources or be forged if they're created from invalid values.
//
// This module provides three types for representing raw file descriptors
// with different ownership properties: raw, borrowed, and owned, which are
// analogous to types used for representing pointers:
//
// | Type               | Analogous to |
// | ------------------ | ------------ |
// | [RawFd]          | `* const _`   |
// | [BorrowedFd.a] | `_`      |
// | [OwnedFd]        | `Holder<_>`     |
//
// Like raw pointers, `RawFd` values are primitive values. And in new code,
// they should be considered unchecked to do I/O on (analogous to dereferencing
// them). Rust did not always provide this guidance, so existing code in the
// Rust ecosystem often doesn't mark `RawFd` usage as unchecked. Once the
// `ioSafety` feature is stable, libraries will be encouraged to migrate,
// either by adding `unchecked` to APIs that dereference `RawFd` values, or by
// using to `BorrowedFd` or `OwnedFd` instead.
//
// Like references, `BorrowedFd` values are tied to a scope, to ensure
// that they don't outlive the resource they point to. These are safe to
// use. `BorrowedFd` values may be used in APIs which provide safe access to
// any system call except for:
//
//  - `close`, because that would end the dynamic scope of the resource
//    without ending the scope of the file descriptor.
//
//  - `dup2`/`dup3`, in the second argument, because this argument is
//    closed and assigned a new resource, which may break the assumptions
//    other code using that file descriptor.
//
// `BorrowedFd` values may be used in APIs which provide safe access to `dup`
// system calls, so types implementing `AsFd` or `From<OwnedFd>` should not
// assume they always have exclusive access to the underlying file
// description.
//
// Like boxes, `OwnedFd` values conceptually own the resource they point to,
// and free (close) it when they are dropped.

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

/**
 * Raw file descriptors.
 */
public typealias RawFd = Int

// The max of this is -2, in two's complement. -1 is `SOLID_NET_INVALID_FD`.
public typealias ValidRawFd = NotAllOnes<RawFd>

/**
 * A borrowed SOLID Sockets file descriptor.
 *
 * This has a scope parameter to tie it to the scope of something that
 * owns the socket.
 *
 * This uses `repr(transparent)` and has the representation of a host file
 * descriptor, so it can be used in FFI in places where a socket is passed as
 * an argument, it is not captured or consumed, and it never has the value
 * `SOLID_NET_INVALID_FD`.
 *
 * This type's `.toOwned()` implementation returns another `BorrowedFd`
 * rather than an `OwnedFd`. It just makes a trivial copy of the raw
 * socket, which is then borrowed under the same scope.
 */
public open class BorrowedFd internal constructor(
    fdCtorArg: ValidRawFd,
    public var phantom: Unit,
) : AsRawFd, IsTerminal, AsFd {
    public var fd: ValidRawFd = fdCtorArg
        internal set

    /**
     * Creates a new `OwnedFd` instance that shares the same underlying file
     * description as the existing `BorrowedFd` instance.
     */
    public open fun tryCloneToOwned(): Result<OwnedFd> {
        val fd = when (val tryResult = cvt(dup(this.asRawFd()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(OwnedFd.FromRawFd(fd))
    }

    public override fun asRawFd(): RawFd = this.fd.asInner()

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "BorrowedFd").field("fd", this.fd).finish()
    }

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.IsTerminal(this)

    public override fun asFd(): BorrowedFd = this

    public companion object {
        public operator fun invoke(
            fd: ValidRawFd,
            phantom: Unit,
        ): BorrowedFd = BorrowedFd(
            fdCtorArg = fd,
            phantom = phantom,
        )

        /**
         * Returns a `BorrowedFd` holding the given raw file descriptor.
         *
         * # Safety
         *
         * The resource pointed to by `fd` must remain open for the duration of
         * the returned `BorrowedFd`, and it must not have the value
         * `SOLID_NET_INVALID_FD`.
         */
        public fun borrowRaw(fd: RawFd): BorrowedFd = BorrowedFd(fd = ValidRawFd.new(fd).expect("fd != -1"), phantom = Unit)
    }
}

/**
 * An owned SOLID Sockets file descriptor.
 *
 * This closes the file descriptor on drop.
 *
 * This uses `repr(transparent)` and has the representation of a host file
 * descriptor, so it can be used in FFI in places where a socket is passed as
 * an argument, it is not captured or consumed, and it never has the value
 * `SOLID_NET_INVALID_FD`.
 */
public open class OwnedFd internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<ValidRawFd>,
) : AsRawFd, IntoRawFd, FromRawFd<OwnedFd, Any?, Any>, IsTerminal, AsFd {
    public var fd: ValidRawFd
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(fd: ValidRawFd, fieldLayout: TypeLayout<ValidRawFd>) : this(allocateNativeCell(fd, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<OwnedFd> = TransparentTypeLayout(fieldLayout, { ptr, layout -> OwnedFd(ptr, layout) }, { it.fd })

    /**
     * Creates a new `OwnedFd` instance that shares the same underlying file
     * description as the existing `OwnedFd` instance.
     */
    public open fun tryClone(): Result<OwnedFd> = this.asFd().tryCloneToOwned()

    public override fun asRawFd(): RawFd = this.fd.asInner()

    public override fun intoRawFd(): RawFd = this.fd.asInner()

    public open fun drop() {
        close(this.fd.asInner())
    }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "OwnedFd").field("fd", this.fd).finish()
    }

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.IsTerminal(this)

    public override fun asFd(): BorrowedFd {
        // Safety: `OwnedFd` and `BorrowedFd` have the same validity
        // invariants, and the `BorrowedFd` is bounded by the scope
        // of `self`.
        return BorrowedFd.borrowRaw(this.asRawFd())
    }

    public companion object {
        public operator fun invoke(fd: ValidRawFd, fieldLayout: TypeLayout<ValidRawFd>): OwnedFd = OwnedFd(fd, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ValidRawFd>): OwnedFd = OwnedFd(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of `Self` from the given raw file descriptor.
         *
         * # Safety
         *
         * The resource pointed to by `fd` must be open and suitable for assuming
         * ownership. The resource must not require any cleanup other than `close`.
         */
        public fun fromRawFd(fd: RawFd, fieldLayout: TypeLayout<NotAllOnes<RawFd>>): OwnedFd = OwnedFd(fd = ValidRawFd.new(fd).expect("fd != -1"), fieldLayout = fieldLayout)

        public fun from(socket: TcpStream): OwnedFd = socket.intoInner().intoSocket().intoInner()

        public fun from(socket: TcpListener): OwnedFd = socket.intoInner().intoSocket().intoInner()

        public fun from(socket: UdpSocket): OwnedFd = socket.intoInner().intoSocket().intoInner()
    }
}

/**
 * A trait to borrow the SOLID Sockets file descriptor from an underlying
 * object.
 */
public interface AsFd {
    /**
     * Borrows the file descriptor.
     */
    public fun asFd(): BorrowedFd
}

public fun TcpStream.asFd(): BorrowedFd = this.asInner().socket().asFd()

public fun fromTcpStream(ownedFd: OwnedFd): TcpStream = TcpStream.fromInner(FromInner.fromInner(FromInner.fromInner(ownedFd)))

public fun TcpListener.asFd(): BorrowedFd = this.asInner().socket().asFd()

public fun fromTcpListener(ownedFd: OwnedFd): TcpListener = TcpListener.fromInner(FromInner.fromInner(FromInner.fromInner(ownedFd)))

public fun UdpSocket.asFd(): BorrowedFd = this.asInner().socket().asFd()

public fun fromUdpSocket(ownedFd: OwnedFd): UdpSocket = UdpSocket.fromInner(FromInner.fromInner(FromInner.fromInner(ownedFd)))

/**
 * This impl allows implementing traits that require `AsFd` on Arc.
 * # @cfg(targetOs = "solidAsp3") mod groupCfg {
 * # use std.os.solid.io.AsFd;
 * use std.net.UdpSocket;
 * use std.sync.Arc;
 *
 * trait MyTrait: AsFd {}
 * impl MyTrait for Arc<UdpSocket> {}
 * impl MyTrait for Holder<UdpSocket> {}
 * # }
 * ```kotlin
 */
public fun <T : AsFd> Arc<T>.asFd(): BorrowedFd = this.asFd()

public fun <T : AsFd> Rc.Rc<T>.asFd(): BorrowedFd = this.asFd()

/**
 * A trait to extract the raw SOLID Sockets file descriptor from an underlying
 * object.
 */
public interface AsRawFd {
    /**
     * Extracts the raw file descriptor.
     *
     * This method does **not** pass ownership of the raw file descriptor
     * to the caller. The descriptor is only guaranteed to be valid while
     * the original object has not yet been destroyed.
     */
    public fun asRawFd(): RawFd
}

/**
 * A trait to express the ability to construct an object from a raw file
 * descriptor.
 */
public interface FromRawFd<T, Metadata> {
    /**
     * Constructs a new instance of `Self` from the given raw file
     * descriptor.
     *
     * This function is typically used to **consume ownership** of the
     * specified file descriptor. When used in this way, the returned object
     * will take responsibility for closing it when the object goes out of
     * scope.
     *
     * However, consuming ownership is not strictly required. Use a
     * [From.OwnedFd.from] implementation for an API which strictly
     * consumes ownership.
     *
     * # Safety
     *
     * The `fd` passed in must be an [owned file descriptor][io-safety];
     * in particular, it must be open.
     *
     * [io-safety]: io#io-safety
     */
    public fun fromRawFd(fd: RawFd): T
}

/**
 * A trait to express the ability to consume an object and acquire ownership of
 * its raw file descriptor.
 */
public interface IntoRawFd {
    /**
     * Consumes this object, returning the raw underlying file descriptor.
     *
     * This function **transfers ownership** of the underlying file descriptor
     * to the caller. Callers are then the unique owners of the file descriptor
     * and must close the descriptor once it's no longer needed.
     */
    public fun intoRawFd(): RawFd
}

public fun RawFd.asRawFd(): RawFd = this

public fun RawFd.intoRawFd(): RawFd = this

public fun fromRawFdRawFd(fd: RawFd): RawFd = fd

public fun TcpStream.asRawFd(): RawFd = this.asInner().socket().asRawFd()

public fun TcpListener.asRawFd(): RawFd = this.asInner().socket().asRawFd()

public fun UdpSocket.asRawFd(): RawFd = this.asInner().socket().asRawFd()

public fun fromRawFdTcpStream(fd: RawFd): TcpStream {
    val socket = Socket.fromRawFd(fd)
    return net.TcpStream.fromInner(io.github.kotlinmania.rustlang.std.sys.net.connection.motor.TcpStream.fromInner(socket))
}

public fun fromRawFdTcpListener(fd: RawFd): TcpListener {
    val socket = Socket.fromRawFd(fd)
    return net.TcpListener.fromInner(io.github.kotlinmania.rustlang.std.sys.net.connection.motor.TcpListener.fromInner(socket))
}

public fun fromRawFdUdpSocket(fd: RawFd): UdpSocket {
    val socket = Socket.fromRawFd(fd)
    return net.UdpSocket.fromInner(io.github.kotlinmania.rustlang.std.sys.net.connection.motor.UdpSocket.fromInner(socket))
}

public fun TcpStream.intoRawFd(): RawFd = this.intoInner().intoSocket().intoRawFd()

public fun TcpListener.intoRawFd(): RawFd = this.intoInner().intoSocket().intoRawFd()

public fun UdpSocket.intoRawFd(): RawFd = this.intoInner().intoSocket().intoRawFd()
