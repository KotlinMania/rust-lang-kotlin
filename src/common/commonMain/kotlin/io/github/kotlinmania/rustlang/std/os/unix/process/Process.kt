// port-lint: source os/unix/process.rs
package io.github.kotlinmania.rustlang.std.os.unix.process

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.SIGKILL
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.process.ChildStderr
import io.github.kotlinmania.rustlang.std.process.ChildStdin
import io.github.kotlinmania.rustlang.std.process.ChildStdout
import io.github.kotlinmania.rustlang.std.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.ChildPipe
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.getppid
import io.github.kotlinmania.rustlang.std.sys.process.unix.unix.Process

// Unix-specific extensions to primitives in the [std.process] module.

// Both IDs are signed, see `sys / targetNto.h` of the QNX SDP.
// Only positive values should be used, see e.g.
// https://www.qnx.com/developers/docs/7.1/com.qnx.doc.neutrino.libRef/topic/s/setuid.html
public typealias UserId = Int

public typealias GroupId = Int

/**
 * Unix-specific extensions to the [process.Command] builder.
 */
public interface CommandExt {
    /**
     * Sets the child process's user ID. This translates to a
     * `setuid` call in the child process. Failure in the `setuid`
     * call will cause the spawn to fail.
     *
     * # Notes
     *
     * This will also trigger a call to `setgroups(0, NULL)` in the child
     * process if no groups have been specified.
     * This removes supplementary groups that might have given the child
     * unwanted permissions.
     */
    public fun uid(id: UserId): Command

    /**
     * Similar to `uid`, but sets the group ID of the child process. This has
     * the same semantics as the `uid` field.
     */
    public fun gid(id: GroupId): Command

    /**
     * Sets the supplementary group IDs for the calling process. Translates to
     * a `setgroups` call in the child process.
     */
    public fun groups(groups: List<GroupId>): Command

    /**
     * Schedules a closure to be run just before the `exec` function is
     * invoked.
     *
     * The closure is allowed to return an I/O error whose OS error code will
     * be communicated back to the parent and returned as an error from when
     * the spawn was requested.
     *
     * Multiple closures can be registered and they will be called in order of
     * their registration. If a closure returns `Err` then no further closures
     * will be called and the spawn operation will immediately return with a
     * failure.
     *
     * # Notes and Safety
     *
     * This closure will be run in the context of the child process after a
     * `fork`. This primarily means that any modifications made to memory on
     * behalf of this closure will **not** be visible to the parent process.
     * This is often a very constrained environment where normal operations
     * like `malloc`, accessing environment variables through [std.env]
     * or acquiring a mutex are not guaranteed to work (due to
     * other threads perhaps still running when the `fork` was run).
     *
     * Note that the list of allocating functions includes [Error.new] and
     * [Error.other]. To signal a non-trivial error, prefer [panic].
     *
     * For further details refer to the [POSIX fork() specification]
     * and the equivalent documentation for any targeted
     * platform, especially the requirements around *async-signal-safety*.
     *
     * This also means that all resources such as file descriptors and
     * memory-mapped regions got duplicated. It is your responsibility to make
     * sure that the closure does not violate library invariants by making
     * invalid use of these duplicates.
     *
     * Panicking in the closure is safe only if all the format arguments for the
     * panic message can be safely formatted; this is because although
     * `Command` calls [`std.panic.alwaysAbort`](panic.alwaysAbort)
     * before calling the preExec hook, panic will still try to format the
     * panic message.
     *
     * When this closure is run, aspects such as the stdio file descriptors and
     * working directory have successfully been changed, so output to these
     * locations might not appear where intended.
     *
     * [POSIX fork() specification]:
     *     https://pubs.opengroup.org/onlinepubs/9799919799/functions/fork.html
     * [std.env]: mod@env
     * [Error.new]: ../../../io/struct.Error.html#method.new
     * [Error.other]: ../../../io/struct.Error.html#method.other
     */
    public fun preExec(f: () -> Result<Unit, Error>): Command

    /**
     * Schedules a closure to be run just before the `exec` function is
     * invoked.
     *
     * `beforeExec` used to be a safe method, but it needs to be unchecked since the closure may only
     * perform operations that are *async-signal-safe*. Hence it got deprecated in favor of the
     * unchecked [preExec]. Meanwhile, Rust gained the ability to make an existing safe method
     * fully unchecked in a new edition, which is how `beforeExec` became `unchecked`. It still also
     * remains deprecated; `preExec` should be used instead.
     *
     * [preExec]: CommandExt.preExec
     */
    public fun beforeExec(f: () -> Result<Unit, Error>): Command = this.preExec(f)

    /**
     * Performs all the required setup by this `Command`, followed by calling
     * the `execvp` syscall.
     *
     * On success this function will not return, and otherwise it will return
     * an error indicating why the exec (or another part of the setup of the
     * `Command`) failed.
     *
     * `exec` not returning has the same implications as calling
     * [process.exit] – no destructors on the current stack or any other
     * thread’s stack will be run. Therefore, it is recommended to only call
     * `exec` at a point where it is fine to not run any destructors. Note,
     * that the `execvp` syscall independently guarantees that all memory is
     * freed and all file descriptors with the `CLOEXEC` option (set by default
     * on all file descriptors opened by the standard library) are closed.
     *
     * This function, unlike `spawn`, will **not** `fork` the process to create
     * a new child. Like spawn, however, the default behavior for the stdio
     * descriptors will be to inherit them from the current process.
     *
     * # Notes
     *
     * The process may be in a "broken state" if this function returns in
     * error. For example the working directory, environment variables, signal
     * handling settings, various user/group information, or aspects of stdio
     * file descriptors may have changed. If a "transactional spawn" is
     * required to gracefully handle errors it is recommended to use the
     * cross-platform `spawn` instead.
     */
    public fun exec(): io.github.kotlinmania.rustlang.core.io.error.Error

    /**
     * Set executable argument
     *
     * Set the first process argument, `argv[0]`, to something other than the
     * default executable path.
     */
    public fun arg0(arg: OsStr): Command

    /**
     * Sets the process group ID (PGID) of the child process. Equivalent to a
     * `setpgid` call in the child process, but may be more efficient.
     *
     * Process groups determine which processes receive signals.
     *
     * # Examples
     *
     * Pressing Ctrl-C in a terminal will send SIGINT to all processes in
     * the current foreground process group. By spawning the `sleep`
     * subprocess in a new process group, it will not receive SIGINT from the
     * terminal.
     *
     * The parent process could install a signal handler and manage the
     * subprocess on its own terms.
     *
     * A process group ID of 0 will use the process ID as the PGID.
     *
     * use std.process.Command;
     * use std.os.unix.process.CommandExt;
     *
     * Command.new("sleep")
     *     .arg("10")
     *     .processGroup(0)
     *     .spawn()?
     *     .wait()?;
     * #
     * # Ok::<_, Holder<dynamic std.error.Error>>(())
     * ```kotlin
     */
    public fun processGroup(pgroup: Int): Command

    /**
     * Set the root of the child process. This calls `chroot` in the child process before executing
     * the command.
     *
     * This happens before changing to the directory specified with
     * [process.Command.currentDir], and that directory will be relative to the new root.
     *
     * If no directory has been specified with [process.Command.currentDir], this will set the
     * directory to `/`, to avoid leaving the current directory outside the chroot. (This is an
     * intentional difference from the underlying `chroot` system call.)
     *
     * This typically requires privileges, such as root or a specific capability.
     *
     * Note that `chroot` is not guaranteed to be async-signal-safe on all OSes, and functions
     * called between `fork` and `exec` must be async-signal-safe. However, in practice, the
     * ability to `fork` then `chroot` then `exec` is a capability widely provided by Rust targets
     * and relied upon by numerous programs.
     *
     * `chroot` is not, by itself, a complete security or sandboxing measure. It may form some part
     * of a containment mechanism, but fully containing a process requires additional steps.
     *
     * See the [chroot] manual page for more details.
     *
     * [chroot]: https://man7.org/linux/man-pages/man2/chroot.2.html
     *
     * # Examples
     *
     * @file:feature(processChroot)
     * use std.process.Command;
     * use std.os.unix.process.CommandExt;
     *
     * Command.new("binary-within-new-root")
     *    .chroot("/new-root")
     *    .spawn()?
     *    .wait()?;
     * #
     * # Ok::<_, Holder<dynamic std.error.Error>>(())
     * ```kotlin
     */
    public fun chroot(dir: Path): Command

    public fun setsid(setsid: Boolean): Command
}

public fun Command.uid(id: UserId): Command {
    this.asInnerMut().uid(id)
    return this
}

public fun Command.gid(id: GroupId): Command {
    this.asInnerMut().gid(id)
    return this
}

public fun Command.groups(groups: List<GroupId>): Command {
    this.asInnerMut().groups(groups)
    return this
}

public fun Command.preExec(f: () -> Result<Unit, Error>): Command {
    this.asInnerMut().preExec(f)
    return this
}

public fun Command.exec(): io.github.kotlinmania.rustlang.core.io.error.Error {
    // NOTE: This may *not* be safe to call after `libc.fork`, because it
    // may allocate. That may be worth fixing at some point in the future.
    return this.asInnerMut().exec(Stdio.Inherit)
}

public fun Command.arg0(arg: OsStr): Command {
    this.asInnerMut().setArg0(arg)
    return this
}

public fun Command.processGroup(pgroup: Int): Command {
    this.asInnerMut().pgroup(pgroup)
    return this
}

public fun Command.chroot(dir: Path): Command {
    this.asInnerMut().chroot(dir)
    return this
}

public fun Command.setsid(setsid: Boolean): Command {
    this.asInnerMut().setsid(setsid)
    return this
}

/**
 * Unix-specific extensions to [ExitStatus] and [ExitStatusError].
 *
 * On Unix, [ExitStatus] **does not necessarily represent an exit status**, as
 * passed to the `_exit` system call or returned by
 * [`ExitStatus.code`](ExitStatus.code).  It represents **any wait status**
 * as returned by one of the [wait] family of system
 * calls.
 *
 * A Unix wait status (a Rust [ExitStatus]) can represent a Unix exit status, but can also
 * represent other kinds of process event.
 *
 * [wait]: https://pubs.opengroup.org/onlinepubs/9799919799/functions/wait.html
 */
public interface ExitStatusExt<T> {
    /**
     * Creates a new [ExitStatus] or [ExitStatusError] from the raw underlying integer status
     * value from [wait].
     *
     * The value should be a **wait status, not an exit status**.
     *
     * # Example
     *
     * A signal-terminated [wait] status carries the signal number, which [ExitStatus.signal]
     * recovers using the platform's [`Wtermsig`][wait] macro. Note that the bit layout of a
     * wait status is **not** specified by POSIX and is platform-specific. By convention on most
     * Unix platforms, the signal number occupies the low 7 bits with the exit-code byte left
     * zero, so a bare signal number between 1 and 126 is treated as a signal-terminated wait
     * status. The following example relies on that convention and is therefore not guaranteed to
     * hold on every target:
     *
     * # if cfg(targetOs = "fuchsia") ( return; )
     * use std.os.unix.process.ExitStatusExt;
     * use std.process.ExitStatus;
     *
     * let signal = 15; // SIGTERM
     * assert(signal > 0 && signal < 0x7f, "not a valid Unix termination signal: (signal)");
     *
     * let status = ExitStatus.fromRaw(signal);
     * assert(!status.success());
     * assertEq(status.code(), None);
     * assertEq(status.signal(), Some(15));
     * ```kotlin
     *
     * Generating an [ExitStatus] with a given exit code (0-255) is system-dependent.
     * The value returned by [ExitStatus.code] is specified to come from applying the
     * [`Wexitstatus`][wait] macro, but there is no POSIX-specified constructor and the bit
     * layout is left unspecified.By near - universal convention every Unix libc stores the
     * 8-bit exit code in bits 8..16, so a status built with `(code & 0xff)<<8` will usually
     * round - trip back to the original exit code:
     *
     * import std.os.unix.process.ExitStatusExt;
     * import std.process.ExitStatus;
     *
     * val code = 41;
     * val status = ExitStatus.fromRaw((code & 0xff)<<8);
     * assertEq(status.code(), Some(41));
     * assert(!status.success());
     * ```
     *
     * # Panics
     *
     * - `ExitStatusError.fromRaw` panics on an attempt to make an [ExitStatusError] from a
     *    [wait] status of `0`.
     * - `ExitStatus.fromRaw` always succeeds and never panics.
     *
     * [wait]: https://pubs.opengroup.org/onlinepubs/9799919799/functions/wait.html
     */
    public fun fromRaw(raw: Int): T

    /**
     * If the process was terminated by a signal, returns that signal.
     *
     * In other words, if [`Wifsignaled`][wait], this returns [`Wtermsig`][wait]. For such a status,
     * [ExitStatus.code] returns `None`:
     *
     * # if cfg(targetOs = "fuchsia") ( return; )
     * use std.os.unix.process.ExitStatusExt;
     * use std.process.ExitStatus;
     *
     * let sigterm = 15;
     * let status = ExitStatus.fromRaw(sigterm);
     * assertEq(status.code(), None);
     * assertEq(status.signal(), Some(sigterm));
     * ```kotlin
     *
     * A process that receives a signal may catch and handle it, then exit normally with an
     * exit code. When that happens, `signal` returns `None`.
     *
     * Rust does not pass commands through a shell, such as `bash` and `sh`, but it
     * is possible to do so manually.When invoking a shell, the signal value indicates whether
     * the top - level shell itself received a terminating signal.If instead a command * within *
     * an invoked shell receives a terminating signal, many shells convert the signal number
     * into an exit code by adding 128. For example, a command run under `sh` that receives a
     * [Sigterm] canonically causes the shell to report an exit code of `15 + 128`, i.e. `143`.
     *
     * [Sigterm]: https://pubs.opengroup.org/onlinepubs/9799919799/utilities/kill.html
     * [wait]: https://pubs.opengroup.org/onlinepubs/9799919799/functions/wait.html
     * ```
     */
    public fun signal(): Int?

    /**
     * If the process was terminated by a signal, says whether it dumped core.
     */
    public fun coreDumped(): Boolean

    /**
     * If the process was stopped by a signal, returns that signal.
     *
     * In other words, if [`Wifstopped`][wait], this returns [`Wstopsig`][wait].  This is only possible if the status came from
     * a [wait] system call which was passed [`Wuntraced`][wait], and was then converted into an [ExitStatus].
     *
     * [wait]: https://pubs.opengroup.org/onlinepubs/9799919799/functions/wait.html
     */
    public fun stoppedSignal(): Int?

    /**
     * Whether the process was continued from a stopped status.
     *
     * I.e. [`Wifcontinued`][wait].  This is only possible if the status came from a [wait] system call
     * which was passed [`Wcontinued`][wait], and was then converted into an [ExitStatus].
     *
     * [wait]: https://pubs.opengroup.org/onlinepubs/9799919799/functions/wait.html
     */
    public fun continued(): Boolean

    /**
     * Returns the underlying raw [wait] status.
     *
     * The returned integer is a **wait status, not an exit status**.
     *
     * [wait]: https://pubs.opengroup.org/onlinepubs/9799919799/functions/wait.html
     */
    public fun intoRaw(): Int
}

public fun exitStatusExtFromRawExitStatus(raw: Int): ExitStatus = Process.ExitStatus.fromInner(From.from(raw))

public fun ExitStatus.signal(): Int? = this.asInner().signal()

public fun ExitStatus.coreDumped(): Boolean = this.asInner().coreDumped()

public fun ExitStatus.stoppedSignal(): Int? = this.asInner().stoppedSignal()

public fun ExitStatus.continued(): Boolean = this.asInner().continued()

public fun ExitStatus.intoRaw(): Int = this.asInner().intoRaw().into()

public fun exitStatusExtFromRawExitStatusError(raw: Int): ExitStatusError = Process.ExitStatus.fromRaw(raw).exitOk().expectErr("<ExitStatusError as ExitStatusExt>::from_raw(0) but zero is not an error")

public fun ExitStatusError.signal(): Int? = this.intoStatus().signal()

public fun ExitStatusError.coreDumped(): Boolean = this.intoStatus().coreDumped()

public fun ExitStatusError.stoppedSignal(): Int? = this.intoStatus().stoppedSignal()

public fun ExitStatusError.continued(): Boolean = this.intoStatus().continued()

public fun ExitStatusError.intoRaw(): Int = this.intoStatus().intoRaw()

public interface ChildExt<T> {
    /**
     * Sends a signal to a child process.
     *
     * # Errors
     *
     * This function will return an error if the signal is invalid. The integer values associated
     * with signals are implementation-specific, so it's encouraged to use a crate that provides
     * posix bindings.
     *
     * # Examples
     *
     * @file:feature(unixSendSignal)
     *
     * use std::(io, os.unix.process.ChildExt, process::(Command, Stdio));
     *
     * use libc.Sigterm;
     *
     * fn main() -> io.Result<()> (
     *     # if cfg(not(all(targetVendor = "apple", not(targetOs = "macos")))) (
     *     let child = Command.new("cat").stdin(Stdio.piped()).spawn()?;
     *     child.sendSignal(SIGTERM)?;
     *     # )
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun sendSignal(signal: Int): Result<Unit, Error>

    /**
     * Sends a signal to a child process's process group.
     *
     * # Errors
     *
     * This function will return an error if the signal is invalid or if the
     * child process does not have a process group. The integer values
     * associated with signals are implementation-specific, so it's encouraged
     * to use a crate that provides posix bindings.
     *
     * # Examples
     *
     * @file:feature(unixSendSignal)
     *
     * use std::(io, os.unix.process::(ChildExt, CommandExt), process::(Command, Stdio));
     *
     * use libc.Sigterm;
     *
     * fn main() -> io.Result<()> (
     *     # if cfg(not(all(targetVendor = "apple", not(targetOs = "macos")))) (
     *     let child = Command.new("cat")
     *         .stdin(Stdio.piped())
     *         .processGroup(0)
     *         .spawn()?;
     *     child.sendProcessGroupSignal(SIGTERM)?;
     *     # )
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun sendProcessGroupSignal(signal: Int): Result<Unit, Error>

    /**
     * Forces the child process's process group to exit.
     *
     * This is analogous to [Child.kill] but applies to every process in
     * the child process's process group.
     *
     * Use [CommandExt.processGroup] to assign a child process to an
     * existing process group, or to make it the leader of a new process group.
     * By default spawned processes are in the parent's process group.
     *
     * # Examples
     *
     * @file:feature(unixKillProcessGroup)
     *
     * use std::(os.unix.process::(ChildExt, CommandExt), process::(Command, Stdio));
     *
     * fn main() -> std.io.Result<()> (
     *     let mut child = Command.new("cat")
     *         .stdin(Stdio.piped())
     *         .processGroup(0)
     *         .spawn()?;
     *     child.killProcessGroup()?;
     *     Ok(())
     * )
     * ```kotlin
     *
     * [Child.kill]: process.Child.kill
     * ```
     */
    public fun killProcessGroup(): Result<Unit, Error>
}

public fun Child.sendSignal(signal: Int): Result<Unit, Error> = this.handle.sendSignal(signal)

public fun Child.sendProcessGroupSignal(signal: Int): Result<Unit, Error> = this.handle.sendProcessGroupSignal(signal)

public fun Child.killProcessGroup(): Result<Unit, Error> = this.handle.sendProcessGroupSignal(SIGKILL)

public fun fromRawFdStdio(fd: RawFd): Stdio {
    val fd1 = FileDesc.fromRawFd(fd)
    val io = Stdio.Fd(fd1)
    return Process.Stdio.fromInner(io)
}

/**
 * Takes ownership of a file descriptor and returns a [`Stdio`](process.Stdio)
 * that can attach a stream to it.
 */
public fun fromStdio(fd: OwnedFd): Stdio {
    val fd1 = FileDesc.fromInner(fd)
    val io = Stdio.Fd(fd1)
    return Process.Stdio.fromInner(io)
}

public fun ChildStdin.asRawFd(): RawFd = this.asInner().asRawFd()

public fun ChildStdout.asRawFd(): RawFd = this.asInner().asRawFd()

public fun ChildStderr.asRawFd(): RawFd = this.asInner().asRawFd()

public fun ChildStdin.intoRawFd(): RawFd = this.intoInner().intoInner().intoRawFd()

public fun ChildStdout.intoRawFd(): RawFd = this.intoInner().intoInner().intoRawFd()

public fun ChildStderr.intoRawFd(): RawFd = this.intoInner().intoInner().intoRawFd()

public fun ChildStdin.asFd(): BorrowedFd = this.asInner().asFd()

/**
 * Takes ownership of a [`ChildStdin`](process.ChildStdin)'s file descriptor.
 */
public fun fromOwnedFd(childStdin: ChildStdin): OwnedFd = childStdin.intoInner().intoInner()

/**
 * Creates a `ChildStdin` from the provided `OwnedFd`.
 *
 * The provided file descriptor must point to a pipe
 * with the `CLOEXEC` flag set.
 */
public fun fromChildStdin(fd: OwnedFd): ChildStdin {
    val pipe = ChildPipe.fromInner(fd)
    return Process.ChildStdin.fromInner(pipe)
}

public fun ChildStdout.asFd(): BorrowedFd = this.asInner().asFd()

/**
 * Takes ownership of a [`ChildStdout`](process.ChildStdout)'s file descriptor.
 */
public fun fromOwnedFd(childStdout: ChildStdout): OwnedFd = childStdout.intoInner().intoInner()

/**
 * Creates a `ChildStdout` from the provided `OwnedFd`.
 *
 * The provided file descriptor must point to a pipe
 * with the `CLOEXEC` flag set.
 */
public fun fromChildStdout(fd: OwnedFd): ChildStdout {
    val pipe = ChildPipe.fromInner(fd)
    return Process.ChildStdout.fromInner(pipe)
}

public fun ChildStderr.asFd(): BorrowedFd = this.asInner().asFd()

/**
 * Takes ownership of a [`ChildStderr`](process.ChildStderr)'s file descriptor.
 */
public fun fromOwnedFd(childStderr: ChildStderr): OwnedFd = childStderr.intoInner().intoInner()

/**
 * Creates a `ChildStderr` from the provided `OwnedFd`.
 *
 * The provided file descriptor must point to a pipe
 * with the `CLOEXEC` flag set.
 */
public fun fromChildStderr(fd: OwnedFd): ChildStderr {
    val pipe = ChildPipe.fromInner(fd)
    return Process.ChildStderr.fromInner(pipe)
}

/**
 * Returns the OS-assigned process identifier associated with this process's parent.
 *
 * # Example
 *
 * use std.os.unix.process;
 * let parent = process.parentId();
 * println("The parent process has ID ()", parent);
 * ```kotlin
 */
public fun parentId(): UInt = getppid()
