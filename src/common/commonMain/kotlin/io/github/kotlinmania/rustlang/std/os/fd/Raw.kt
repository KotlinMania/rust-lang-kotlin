// port-lint: source os/fd/raw.rs
package io.github.kotlinmania.rustlang.std.os.fd

import io.github.kotlinmania.libc.hermit.STDERR_FILENO
import io.github.kotlinmania.libc.hermit.STDIN_FILENO
import io.github.kotlinmania.libc.hermit.STDOUT_FILENO
import io.github.kotlinmania.rustlang.std.Rc
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.io.pipe.PipeReader
import io.github.kotlinmania.rustlang.std.io.pipe.PipeWriter
import io.github.kotlinmania.rustlang.std.io.stdio.Stderr
import io.github.kotlinmania.rustlang.std.io.stdio.StderrLock
import io.github.kotlinmania.rustlang.std.io.stdio.Stdin
import io.github.kotlinmania.rustlang.std.io.stdio.StdinLock
import io.github.kotlinmania.rustlang.std.io.stdio.Stdout
import io.github.kotlinmania.rustlang.std.io.stdio.StdoutLock
import io.github.kotlinmania.rustlang.std.raw.CInt
import io.github.kotlinmania.rustlang.std.sync.Arc

// Raw Unix-like file descriptors.

/**
 * Raw file descriptors.
 */
public typealias RawFd = CInt

/**
 * A trait to extract the raw file descriptor from an underlying object.
 *
 * This is only available on unix and WASI platforms and must be imported in
 * order to call the method. Windows platforms have a corresponding
 * `AsRawHandle` and `AsRawSocket` set of traits.
 */
public interface AsRawFd {
    /**
     * Extracts the raw file descriptor.
     *
     * This function is typically used to **borrow** an owned file descriptor.
     * When used in this way, this method does **not** pass ownership of the
     * raw file descriptor to the caller, and the file descriptor is only
     * guaranteed to be valid while the original object has not yet been
     * destroyed.
     *
     * However, borrowing is not strictly required. See [AsFd.asFd]
     * for an API which strictly borrows a file descriptor.
     *
     * # Example
     *
     * ```kotlin
     * import std.fs.File
     * import std.os.fd.AsRawFd
     * import std.os.fd.RawFd
     *
     * var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     * // Note that `rawFd` is only valid as long as `f` exists.
     * val rawFd: RawFd = f.asRawFd()
     * ```
     */
    public fun asRawFd(): RawFd
}

/**
 * A trait to express the ability to construct an object from a raw file
 * descriptor.
 */
public interface FromRawFd<T> {
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
     *
     * # Example
     *
     * ```kotlin
     * import std.fs.File
     * import std.os.fd.FromRawFd
     * import std.os.fd.IntoRawFd
     * import std.os.fd.RawFd
     *
     * val f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     * val rawFd: RawFd = f.intoRawFd()
     * // SAFETY: no other functions should call `fromRawFd`, so there
     * // is only one owner for the file descriptor.
     * val f1 = File.fromRawFd(rawFd)
     * ```
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
     * This function is typically used to **transfer ownership** of the underlying
     * file descriptor to the caller. When used in this way, callers are then the unique
     * owners of the file descriptor and must close it once it's no longer needed.
     *
     * However, transferring ownership is not strictly required. Use a
     * [Into.OwnedFd.into] implementation for an API which strictly
     * transfers ownership.
     *
     * # Example
     *
     * ```kotlin
     * import std.fs.File
     * import std.os.fd.IntoRawFd
     * import std.os.fd.RawFd
     *
     * val f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     * val rawFd: RawFd = f.intoRawFd()
     * ```
     */
    public fun intoRawFd(): RawFd
}

public fun RawFd.asRawFd(): RawFd = this

public fun RawFd.intoRawFd(): RawFd = this

public fun fromRawFdRawFd(fd: RawFd): RawFd = fd

public fun File.asRawFd(): RawFd = this.asInner().asRawFd()

public fun fromRawFdFile(fd: RawFd): File = File.from(OwnedFd.FromRawFd(fd))

public fun File.intoRawFd(): RawFd = this.intoInner().intoInner().intoRawFd()

public fun Stdin.asRawFd(): RawFd = STDIN_FILENO

public fun Stdout.asRawFd(): RawFd = STDOUT_FILENO

public fun Stderr.asRawFd(): RawFd = STDERR_FILENO

public fun StdinLock.asRawFd(): RawFd = STDIN_FILENO

public fun StdoutLock.asRawFd(): RawFd = STDOUT_FILENO

public fun StderrLock.asRawFd(): RawFd = STDERR_FILENO

/**
 * This impl allows implementing traits that require `AsRawFd` on Arc.
 * ```kotlin
 * import std.net.UdpSocket
 *
 * interface MyTrait : AsRawFd
 * ```
 */
public fun <T : AsRawFd> Arc<T>.asRawFd(): RawFd = this.asRawFd()

public fun <T : AsRawFd> Rc.Rc<T>.asRawFd(): RawFd = this.asRawFd()

public fun <T : AsRawFd> Rc.UniqueRc<T>.asRawFd(): RawFd = this.asRawFd()

public fun PipeReader.asRawFd(): RawFd = this.value.asRawFd()

public fun fromRawFdPipeReader(rawFd: RawFd): PipeReader = PipeReader.fromInner(FromRawFd.FromRawFd(rawFd))

public fun PipeReader.intoRawFd(): RawFd = this.value.intoRawFd()

public fun PipeWriter.asRawFd(): RawFd = this.value.asRawFd()

public fun fromRawFdPipeWriter(rawFd: RawFd): PipeWriter = PipeWriter.fromInner(FromRawFd.FromRawFd(rawFd))

public fun PipeWriter.intoRawFd(): RawFd = this.value.intoRawFd()
