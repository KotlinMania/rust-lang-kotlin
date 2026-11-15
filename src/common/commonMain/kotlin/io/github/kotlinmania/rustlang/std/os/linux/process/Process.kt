// port-lint: source os/linux/process.rs
package io.github.kotlinmania.rustlang.std.os.linux.process

import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.process.ExitStatus
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.std.sys.process.unix.pidfd.PidFd as InnerPidFd

// Linux-specific extensions to primitives in the [std.process] module.

/**
 * This type represents a file descriptor that refers to a process.
 *
 * A `PidFd` can be obtained by setting the corresponding option on [Command]
 * with [createPidfd]. Subsequently, the created pidfd can be retrieved
 * from the [Child] by calling [pidfd] or [intoPidfd].
 *
 * # Examples
 *
 * @file:feature(linuxPidfd)
 * use std.os.linux.process::{CommandExt, ChildExt};
 * use std.process.Command;
 *
 * let mut child = Command.new("echo")
 *     .createPidfd(true)
 *     .spawn()
 *     .expect("Failed to spawn child");
 *
 * let pidfd = child
 *     .intoPidfd()
 *     .expect("Failed to retrieve pidfd");
 *
 * // The file descriptor will be closed when `pidfd` is dropped.
 * ```kotlin
 * Refer to the man page of [pidfdOpen] for further details.
 *
 * [Command]: process.Command
 * [createPidfd]: CommandExt.createPidfd
 * [Child]: process.Child
 * [pidfd]: fn@ChildExt.pidfd
 * [intoPidfd]: ChildExt.intoPidfd
 * [pidfdOpen]: https://man7.org/linux/man-pages/man2/pidfdOpen.2.html
 * ```
 */
public open class PidFd internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<InnerPidFd>,
) : AsInner<InnerPidFd>, FromInner<InnerPidFd, PidFd>, IntoInner<InnerPidFd>, AsRawFd, FromRawFd<PidFd>, IntoRawFd, AsFd {
    public var inner: InnerPidFd
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(inner: InnerPidFd, fieldLayout: TypeLayout<InnerPidFd>) : this(allocateNativeCell(inner, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<PidFd> = TransparentTypeLayout(fieldLayout, { ptr, layout -> PidFd(ptr, layout) }, { it.inner })

    /**
     * Forces the child process to exit.
     *
     * Unlike [Child.kill] it is possible to attempt to kill
     * reaped children since PidFd does not suffer from pid recycling
     * races. But doing so will return an Error.
     *
     * [Child.kill]: process.Child.kill
     */
    public open fun kill(): Result<Unit> = this.inner.kill()

    /**
     * Waits for the child to exit completely, returning the status that it exited with.
     *
     * Unlike [Child.wait] it does not ensure that the stdin handle is closed.
     *
     * Additionally on kernels prior to 6.15 only the first attempt to
     * reap a child will return an ExitStatus, further attempts
     * will return an Error.
     *
     * [Child.wait]: process.Child.wait
     */
    public open fun wait(): Result<ExitStatus> = this.inner.wait().map(FromInner::fromInner)

    /**
     * Attempts to collect the exit status of the child if it has already exited.
     *
     * On kernels prior to 6.15, and unlike [Child.tryWait], only the first attempt
     * to reap a child will return an ExitStatus, further attempts will return an Error.
     *
     * [Child.tryWait]: process.Child.tryWait
     */
    public open fun tryWait(): Result<ExitStatus?> {
        return Result.Ok(when (val tryResult = this.inner.tryWait()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }.map(FromInner::fromInner))
    }

    public override fun asInner(): InnerPidFd = this.inner

    public override fun intoInner(): InnerPidFd = this.inner

    public override fun asRawFd(): RawFd = this.asInner().asInner().asRawFd()

    public override fun intoRawFd(): RawFd = this.intoInner().intoInner().intoRawFd()

    public override fun asFd(): BorrowedFd = this.asInner().asInner().asFd()

    public companion object {
        public operator fun invoke(inner: InnerPidFd, fieldLayout: TypeLayout<InnerPidFd>): PidFd = PidFd(inner, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<InnerPidFd>): PidFd = PidFd(fieldStorage, fieldLayout)

        public fun fromInner(inner: InnerPidFd, fieldLayout: TypeLayout<InnerPidFd>): PidFd = PidFd(inner = inner, fieldLayout = fieldLayout)

        public fun fromRawFd(fd: RawFd, fieldLayout: TypeLayout<InnerPidFd>): PidFd = PidFd.fromInner(InnerPidFd.fromRawFd(fd), fieldLayout = fieldLayout)

        public fun from(fd: OwnedFd, fieldLayout: TypeLayout<InnerPidFd>): PidFd = PidFd.fromInner(InnerPidFd.fromInner(FileDesc.fromInner(fd)), fieldLayout = fieldLayout)
    }
}

public fun fromOwnedFd(pidFd: PidFd): OwnedFd = pidFd.intoInner().intoInner().intoInner()

/**
 * Os-specific extensions for [Child]
 *
 * [Child]: process.Child
 */
public interface ChildExt<T> {
    /**
     * Obtains a reference to the [PidFd] created for this [Child], if available.
     *
     * A pidfd will only be available if its creation was requested with
     * [createPidfd] when the corresponding [Command] was created.
     *
     * Even if requested, a pidfd may not be available due to an older
     * version of Linux being in use, or if some other error occurred.
     *
     * [Command]: process.Command
     * [createPidfd]: CommandExt.createPidfd
     * [Child]: process.Child
     */
    public fun pidfd(): Result<PidFd>

    /**
     * Returns the [PidFd] created for this [Child], if available.
     * Otherwise self is returned.
     *
     * A pidfd will only be available if its creation was requested with
     * [createPidfd] when the corresponding [Command] was created.
     *
     * Taking ownership of the PidFd consumes the Child to avoid pid reuse
     * races. Use [pidfd] and [BorrowedFd.tryCloneToOwned] if
     * you don't want to disassemble the Child yet.
     *
     * Even if requested, a pidfd may not be available due to an older
     * version of Linux being in use, or if some other error occurred.
     *
     * [Command]: process.Command
     * [createPidfd]: CommandExt.createPidfd
     * [pidfd]: ChildExt.pidfd
     * [Child]: process.Child
     */
    public fun intoPidfd(): Result<PidFd, T>
}

/**
 * Os-specific extensions for [Command]
 *
 * [Command]: process.Command
 */
public interface CommandExt {
    /**
     * Sets whether a [`PidFd`](struct@PidFd) should be created for the [Child]
     * spawned by this [Command].
     * By default, no pidfd will be created.
     *
     * The pidfd can be retrieved from the child with [pidfd] or [intoPidfd].
     *
     * A pidfd will only be created if it is possible to do so
     * in a guaranteed race-free manner. Otherwise, [pidfd] will return an error.
     *
     * If a pidfd has been successfully created and not been taken from the `Child`
     * then calls to `kill()`, `wait()` and `tryWait()` will use the pidfd
     * instead of the pid. This can prevent pid recycling races, e.g.
     * those  caused by rogue libraries in the same process prematurely reaping
     * zombie children via `waitpid(- 1, ...)` calls.
     *
     * [Command]: process.Command
     * [Child]: process.Child
     * [pidfd]: fn@ChildExt.pidfd
     * [intoPidfd]: ChildExt.intoPidfd
     */
    public fun createPidfd(value: Boolean): process.Command
}

public fun process.Command.createPidfd(value: Boolean): process.Command {
    this.asInnerMut().createPidfd(value)
    return this
}
