// port-lint: source os/fd/owned.rs
package io.github.kotlinmania.rustlang.std.os.fd

import io.github.kotlinmania.rustlang.alloc.alloc.Global
import io.github.kotlinmania.rustlang.alloc.rcs.arc.Arc
import io.github.kotlinmania.rustlang.alloc.rcs.rc.Rc
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.transmuteWithLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.close
import io.github.kotlinmania.rustlang.libc.fcntl
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.F_DUPFD_CLOEXEC
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.io.pipe.PipeReader
import io.github.kotlinmania.rustlang.std.io.pipe.PipeWriter
import io.github.kotlinmania.rustlang.std.io.stdio.IsTerminal
import io.github.kotlinmania.rustlang.std.io.stdio.Stderr
import io.github.kotlinmania.rustlang.std.io.stdio.StderrLock
import io.github.kotlinmania.rustlang.std.io.stdio.Stdin
import io.github.kotlinmania.rustlang.std.io.stdio.StdinLock
import io.github.kotlinmania.rustlang.std.io.stdio.Stdout
import io.github.kotlinmania.rustlang.std.io.stdio.StdoutLock
import io.github.kotlinmania.rustlang.std.io.stdio.isTerminal
import io.github.kotlinmania.rustlang.std.net.tcp.TcpListener
import io.github.kotlinmania.rustlang.std.net.tcp.TcpStream
import io.github.kotlinmania.rustlang.std.net.udp.UdpSocket
import io.github.kotlinmania.rustlang.std.os.raw.AsRawFd
import io.github.kotlinmania.rustlang.std.os.raw.FromRawFd
import io.github.kotlinmania.rustlang.std.os.raw.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.raw.RawFd
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.fromInner
import io.github.kotlinmania.rustlang.std.sys.fs.unix.debugAssertFdIsOpen
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt

// Owned and borrowed Unix-like file descriptors.

private class DebugStruct(
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

public typealias ValidRawFd = NotAllOnes<RawFd>

/**
 * A borrowed file descriptor.
 *
 * This has a scope parameter to tie it to the scope of something that owns the file
 * descriptor. For the duration of that scope, it is guaranteed that nobody will close the file
 * descriptor.
 *
 * This uses `repr(transparent)` and has the representation of a host file
 * descriptor, so it can be used in FFI in places where a file descriptor is
 * passed as an argument, it is not captured or consumed, and it never has the
 * value `- 1`.
 *
 * This type does not have a [`ToOwned`][borrow.ToOwned]
 * implementation. Calling `.toOwned()` on a variable of this type will call
 * it on `BorrowedFd` and use `Clone.clone()` like `ToOwned` does for all
 * types implementing `Clone`. The result will be descriptor borrowed under
 * the same scope.
 *
 * To obtain an [OwnedFd], you can use [BorrowedFd.tryCloneToOwned]
 * instead, but this is not supported on all platforms.
 */
public open class BorrowedFd internal constructor(
    fdCtorArg: ValidRawFd,
    public var phantom: Unit,
) : AsRawFd, IsTerminal, AsFd, AsRef<File> {
    public var fd: ValidRawFd = fdCtorArg
        internal set

    public open fun clone(): BorrowedFd = BorrowedFd(fd, phantom)

    /**
     * Converts a `BorrowedFd` into a reference to a file-like type.
     *
     * # @file:feature(fdView)
     * # use std.fs.File;
     * # use std.io;
     * # use std.os.fd.AsFd;
     * let meta = io.stdout().asFd().toView::<File>().metadata().unwrap();
     * if meta.isFile() (
     *     println("stdout is a regular file");
     * )
     * ```kotlin
     */
    public open fun <T> toView(): T = this.asRef()

    /**
     * Creates a new `OwnedFd` instance that shares the same underlying file
     * description as the existing `BorrowedFd` instance.
     */
    public open fun tryCloneToOwned(): Result<OwnedFd, Error> {
        // We want to atomically duplicate this file descriptor and set the
        // CLOEXEC flag, and currently that's done via F_DUPFD_CLOEXEC. This
        // is a POSIX flag that was added to Linux in 2.6.24.
        val cmd = F_DUPFD_CLOEXEC
        // Avoid using file descriptors below 3 as they are used for stdio
        val fd = when (val tryResult = cvt(fcntl(this.asRawFd(), cmd, 3))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<OwnedFd, Error>(tryResult.errValue()) }
        return Result.Ok<OwnedFd, Error>(OwnedFd.fromRawFd(fd, fieldLayout = UnresolvedTypeLayout<NotAllOnes<RawFd>>()))
    }

    public override fun asRawFd(): RawFd = this.fd.asInner()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "BorrowedFd").field("fd", this.fd).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.isatty.isTerminal(this)

    public override fun asFd(): BorrowedFd = this

    public open fun asRef(): File {
        fun <T : AsFd> typeCheck() {}
        run {
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<File>(typeLayoutT = UnresolvedTypeLayout<File>()))
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<BorrowedFd>(typeLayoutT = UnresolvedTypeLayout<BorrowedFd>()))
        }
        typeCheck<File>()
        return transmuteWithLayouts<BorrowedFd, File>(this, UnresolvedTypeLayout<BorrowedFd>(), UnresolvedTypeLayout<File>())
    }

    public open fun asRefTcpStream(): TcpStream {
        fun <T : AsFd> typeCheck() {}
        run {
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<TcpStream>(typeLayoutT = UnresolvedTypeLayout<TcpStream>()))
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<BorrowedFd>(typeLayoutT = UnresolvedTypeLayout<BorrowedFd>()))
        }
        typeCheck<TcpStream>()
        return transmuteWithLayouts<BorrowedFd, TcpStream>(this, UnresolvedTypeLayout<BorrowedFd>(), UnresolvedTypeLayout<TcpStream>())
    }

    public open fun asRefTcpListener(): TcpListener {
        fun <T : AsFd> typeCheck() {}
        run {
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<TcpListener>(typeLayoutT = UnresolvedTypeLayout<TcpListener>()))
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<BorrowedFd>(typeLayoutT = UnresolvedTypeLayout<BorrowedFd>()))
        }
        typeCheck<TcpListener>()
        return transmuteWithLayouts<BorrowedFd, TcpListener>(this, UnresolvedTypeLayout<BorrowedFd>(), UnresolvedTypeLayout<TcpListener>())
    }

    public open fun asRefUdpSocket(): UdpSocket {
        fun <T : AsFd> typeCheck() {}
        run {
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<UdpSocket>(typeLayoutT = UnresolvedTypeLayout<UdpSocket>()))
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<BorrowedFd>(typeLayoutT = UnresolvedTypeLayout<BorrowedFd>()))
        }
        typeCheck<UdpSocket>()
        return transmuteWithLayouts<BorrowedFd, UdpSocket>(this, UnresolvedTypeLayout<BorrowedFd>(), UnresolvedTypeLayout<UdpSocket>())
    }

    public open fun asRefPipeWriter(): PipeWriter {
        fun <T : AsFd> typeCheck() {}
        run {
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<PipeWriter>(typeLayoutT = UnresolvedTypeLayout<PipeWriter>()))
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<BorrowedFd>(typeLayoutT = UnresolvedTypeLayout<BorrowedFd>()))
        }
        typeCheck<PipeWriter>()
        return transmuteWithLayouts<BorrowedFd, PipeWriter>(this, UnresolvedTypeLayout<BorrowedFd>(), UnresolvedTypeLayout<PipeWriter>())
    }

    public open fun asRefPipeReader(): PipeReader {
        fun <T : AsFd> typeCheck() {}
        run {
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<PipeReader>(typeLayoutT = UnresolvedTypeLayout<PipeReader>()))
            check(sizeOf<RawFd>(typeLayoutT = UnresolvedTypeLayout<RawFd>()) == sizeOf<BorrowedFd>(typeLayoutT = UnresolvedTypeLayout<BorrowedFd>()))
        }
        typeCheck<PipeReader>()
        return transmuteWithLayouts<BorrowedFd, PipeReader>(this, UnresolvedTypeLayout<BorrowedFd>(), UnresolvedTypeLayout<PipeReader>())
    }

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
         * the returned `BorrowedFd`.
         *
         * # Panics
         *
         * Panics if the raw file descriptor has the value `- 1`.
         */
        public fun borrowRaw(fd: RawFd): BorrowedFd = BorrowedFd(fd = NotAllOnes.new(fd).expect("fd != -1"), phantom = Unit)
    }
}

/**
 * An owned file descriptor.
 *
 * This closes the file descriptor on drop. It is guaranteed that nobody else will close the file
 * descriptor.
 *
 * This uses `repr(transparent)` and has the representation of a host file
 * descriptor, so it can be used in FFI in places where a file descriptor is
 * passed as a consumed argument or returned as an owned value, and it never
 * has the value `- 1`.
 *
 * You can use [AsFd.asFd] to obtain a [BorrowedFd].
 */
public open class OwnedFd internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<ValidRawFd> = UnresolvedTypeLayout(),
) : AsRawFd, IntoRawFd, FromRawFd<OwnedFd>, IsTerminal, AsFd {
    public var fd: ValidRawFd
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(fd: ValidRawFd, fieldLayout: TypeLayout<ValidRawFd> = UnresolvedTypeLayout()) : this(allocateNativeCell(fd, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<OwnedFd> = TransparentTypeLayout(fieldLayout, { ptr, layout -> OwnedFd(ptr, layout) }, { it.fd })

    /**
     * Creates a new `OwnedFd` instance that shares the same underlying file
     * description as the existing `OwnedFd` instance.
     */
    public open fun tryClone(): Result<OwnedFd, Error> = this.asFd().tryCloneToOwned()

    public override fun asRawFd(): RawFd = this.fd.asInner()

    public override fun intoRawFd(): RawFd = this.fd.asInner()

    public open fun drop() {
        run {
            // Note that errors are ignored when closing a file descriptor. According to POSIX 2024,
            // we can and indeed should retry `close` on `EINTR`
            // (https://pubs.opengroup.org/onlinepubs/9799919799/functions/close.html),
            // but it is not clear yet how well widely-used implementations are conforming with this
            // mandate since older versions of POSIX left the state of the FD after an `EINTR`
            // unspecified. Ignoring errors is "fine" because some of the major Unices (in
            // particular, Linux) do make sure to always close the FD, even when `close()` is
            // interrupted, and the scenario is rare to begin with. If we retried on a
            // not-POSIX-compliant implementation, the consequences could be really bad since we may
            // close the wrong FD. Helpful link to an epic discussion by POSIX workgroup that led to
            // the latest POSIX wording: http://austingroupbugs.net/view.php?id=529
            run {
                debugAssertFdIsOpen(this.fd.asInner())
                close(this.fd.asInner())
            }
        }
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "OwnedFd").field("fd", this.fd).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.isatty.isTerminal(this)

    public override fun asFd(): BorrowedFd {
        // Safety: `OwnedFd` and `BorrowedFd` have the same validity
        // invariants, and the `BorrowedFd` is bounded by the scope
        // of `self`.
        return BorrowedFd.borrowRaw(this.asRawFd())
    }

    public companion object {
        public operator fun invoke(fd: ValidRawFd, fieldLayout: TypeLayout<ValidRawFd> = UnresolvedTypeLayout()): OwnedFd = OwnedFd(fd, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<ValidRawFd> = UnresolvedTypeLayout()): OwnedFd = OwnedFd(fieldStorage, fieldLayout)

        /**
         * Constructs a new instance of `Self` from the given raw file descriptor.
         *
         * # Safety
         *
         * The resource pointed to by `fd` must be open and suitable for assuming
         * [ownership][io-safety]. The resource must not require any cleanup other than `close`.
         *
         * [io-safety]: io#io-safety
         *
         * # Panics
         *
         * Panics if the raw file descriptor has the value `- 1`.
         */
        public fun fromRawFd(fd: RawFd, fieldLayout: TypeLayout<NotAllOnes<RawFd>> = UnresolvedTypeLayout()): OwnedFd = OwnedFd(fd = NotAllOnes.new(fd).expect("fd != -1"), fieldLayout = fieldLayout)

        /**
         * Takes ownership of a [`File`](fs.File)'s underlying file descriptor.
         */
        public fun from(file: File): OwnedFd = file.intoInner().intoInner().intoInner()

        /**
         * Takes ownership of a [`TcpStream`](net.TcpStream)'s socket file descriptor.
         */
        public fun from(tcpStream: TcpStream): OwnedFd = tcpStream.intoInner().intoSocket().intoInner().intoInner()

        /**
         * Takes ownership of a [`TcpListener`](net.TcpListener)'s socket file descriptor.
         */
        public fun from(tcpListener: TcpListener): OwnedFd = tcpListener.intoInner().intoSocket().intoInner().intoInner()

        /**
         * Takes ownership of a [`UdpSocket`](net.UdpSocket)'s file descriptor.
         */
        public fun from(udpSocket: UdpSocket): OwnedFd = udpSocket.intoInner().intoSocket().intoInner().intoInner()

        public fun from(pipe: PipeReader): OwnedFd = pipe.value.intoInner()

        public fun from(pipe: PipeWriter): OwnedFd = pipe.value.intoInner()
    }
}

/**
 * A trait to borrow the file descriptor from an underlying object.
 *
 * This is only available on unix platforms and must be imported in order to
 * call the method. Windows platforms have a corresponding `AsHandle` and
 * `AsSocket` set of traits.
 */
public interface AsFd {
    /**
     * Borrows the file descriptor.
     *
     * # Example
     *
     * ```kotlin
     * import std.fs.File
     *
     * var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     * val borrowedFd: BorrowedFd = f.asFd()
     * ```
     */
    public fun asFd(): BorrowedFd
}

public fun File.asFd(): BorrowedFd = this.asInner().asFd()

/**
 * Returns a [`File`](fs.File) that takes ownership of the given
 * file descriptor.
 */
public fun fromFile(ownedFd: OwnedFd): File = File.fromInner(FromInner.fromInner(FromInner.fromInner(ownedFd)))

public fun TcpStream.asFd(): BorrowedFd = this.asInner().socket().asFd()

public fun fromTcpStream(ownedFd: OwnedFd): TcpStream = TcpStream.fromInner(FromInner.fromInner(FromInner.fromInner(FromInner.fromInner(ownedFd))))

public fun TcpListener.asFd(): BorrowedFd = this.asInner().socket().asFd()

public fun fromTcpListener(ownedFd: OwnedFd): TcpListener = TcpListener.fromInner(FromInner.fromInner(FromInner.fromInner(FromInner.fromInner(ownedFd))))

public fun UdpSocket.asFd(): BorrowedFd = this.asInner().socket().asFd()

public fun fromUdpSocket(ownedFd: OwnedFd): UdpSocket = UdpSocket.fromInner(FromInner.fromInner(FromInner.fromInner(FromInner.fromInner(ownedFd))))

/**
 * This impl allows implementing traits that require `AsFd` on Arc.
 * ```kotlin
 * import std.net.UdpSocket
 *
 * interface MyTrait : AsFd
 * ```
 */
public fun <T : AsFd> Arc<T, Global>.asFd(): BorrowedFd = this.asFd()

public fun <T : AsFd> Rc<T, Global>.asFd(): BorrowedFd = this.asFd()

public fun <T : AsFd> Rc.UniqueRc<T>.asFd(): BorrowedFd = this.asFd()

public fun Stdin.asFd(): BorrowedFd = BorrowedFd.borrowRaw(0)

public fun StdinLock.asFd(): BorrowedFd {
    // SAFETY: user code should not close stdin out from under the standard library
    return BorrowedFd.borrowRaw(0)
}

public fun Stdout.asFd(): BorrowedFd = BorrowedFd.borrowRaw(1)

public fun StdoutLock.asFd(): BorrowedFd {
    // SAFETY: user code should not close stdout out from under the standard library
    return BorrowedFd.borrowRaw(1)
}

public fun Stderr.asFd(): BorrowedFd = BorrowedFd.borrowRaw(2)

public fun StderrLock.asFd(): BorrowedFd {
    // SAFETY: user code should not close stderr out from under the standard library
    return BorrowedFd.borrowRaw(2)
}

public fun PipeReader.asFd(): BorrowedFd = this.value.asFd()

public fun PipeWriter.asFd(): BorrowedFd = this.value.asFd()

public fun fromPipeReader(ownedFd: OwnedFd): PipeReader = PipeReader(FromInner.fromInner(ownedFd))

public fun fromPipeWriter(ownedFd: OwnedFd): PipeWriter = PipeWriter(FromInner.fromInner(ownedFd))
