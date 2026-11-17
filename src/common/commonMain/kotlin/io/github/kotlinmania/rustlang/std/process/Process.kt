// port-lint: source process.rs
package io.github.kotlinmania.rustlang.std.process

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.num.nichetypes.NonZeroI32Inner
import io.github.kotlinmania.rustlang.core.num.nonzero.NonZero
import io.github.kotlinmania.rustlang.core.ptr.nullVal
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.formatArgsNl
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.io.pipe.PipeReader
import io.github.kotlinmania.rustlang.std.io.pipe.PipeWriter
import io.github.kotlinmania.rustlang.std.io.stdio.Stderr
import io.github.kotlinmania.rustlang.std.io.stdio.Stdout
import io.github.kotlinmania.rustlang.std.io.stdio.attemptPrintToStderr
import io.github.kotlinmania.rustlang.std.io.stdio.stderr
import io.github.kotlinmania.rustlang.std.io.stdio.stdout
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.rt.cleanup
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.AsInnerMut
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fromInner
import io.github.kotlinmania.rustlang.std.sys.intoInner
import io.github.kotlinmania.rustlang.std.sys.pal.unix.abortInternal
import io.github.kotlinmania.rustlang.std.sys.process.env.CommandResolvedEnvs
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.ChildPipe
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.getpid
import io.github.kotlinmania.rustlang.std.sys.process.unix.common.readOutput
import io.github.kotlinmania.rustlang.std.sys.process.unix.unix.Process

// A module for working with processes.
//
// This module is mostly concerned with spawning and interacting with child
// processes, but it also provides [abort] and [exit] for terminating the
// current process.
//
// # Spawning a process
//
// The [Command] struct is used to configure and spawn processes:
//
// ```kotlin
// import std.process.Command
//
// val output = Command.new("echo").arg("Hello world").output().expect("echo command should execute successfully")
// check("Hello world\n".encodeToByteArray() == output.stdout.asSlice())
// ```
//
// Several methods on [Command], such as [spawn] or [output], can be used
// to spawn a process. In particular, [output] spawns the child process and
// waits until the process terminates, while [spawn] will return a [Child]
// that represents the spawned child process.
//
// # Handling I/O
//
// The [stdout], [stdin], and [stderr] of a child process can be
// configured by passing an [Stdio] to the corresponding method on
// [Command]. Once spawned, they can be accessed from the [Child]. For
// example, piping output from one command into another command can be done
// like so:
//
// ```kotlin
// import std.process.Command
// import std.process.Stdio
//
// // stdout must be configured with `Stdio.piped` in order to use
// // `echoChild.stdout`
// val echoChild = Command.new("echo").arg("Oh no, a tpyo!").stdout(Stdio.piped()).spawn().expect("echo command should start")
// // Note that `echoChild` is moved here, but we won't be needing
// // `echoChild` anymore
// val echoOut = echoChild.stdout.expect("child stdout should open")
// var sedChild = Command.new("sed").arg("s/tpyo/typo/").stdin(Stdio.from(echoOut)).stdout(Stdio.piped()).spawn().expect("sed command should start")
// val output = sedChild.waitWithOutput().expect("waitWithOutput on sed should succeed")
// check("Oh no, a typo!\n".encodeToByteArray() == output.stdout.asSlice())
// ```
//
// Note that [ChildStderr] and [ChildStdout] implement [Read] and
// [ChildStdin] implements [Write]:
//
// ```kotlin
// import std.io.Write
// import std.process.Command
// import std.process.Stdio
//
// var child = Command.new("/bin/cat").stdin(Stdio.piped()).stdout(Stdio.piped()).spawn().expect("child should start")
// // If the child process fills its stdout buffer, it may end up
// // waiting until the parent reads the stdout, and not be able to
// // read stdin in the meantime, causing a deadlock.
// // Writing from another thread ensures that stdout is being read
// // at the same time, avoiding the problem.
// var stdin = (child.stdin.also { child.stdin = null } ?: error("stdin should be able to be retrieved"))
// spawn({
//     stdin.writeAll("test".encodeToByteArray()).expect("writing to stdin should succeed")
// })
// val output = child.waitWithOutput().expect("waitWithOutput on child should succeed")
// check("test".encodeToByteArray() == output.stdout.asSlice())
// ```
//
// # Windows argument splitting
//
// On Unix systems arguments are passed to a new process as an array of strings,
// but on Windows arguments are passed as a single commandline string and it is
// up to the child process to parse it into an array. Therefore the parent and
// child processes must agree on how the commandline string is encoded.
//
// Most programs use the standard C run-time `argv`, which in practice results
// in consistent argument handling. However, some programs have their own way of
// parsing the commandline string. In these cases using [arg] or [args] may
// result in the child process seeing a different array of arguments than the
// parent process intended.
//
// Two ways of mitigating this are:
//
// * Validate untrusted input so that only a safe subset is allowed.
// * Use [rawArg] to build a custom commandline. This bypasses the escaping
//   rules used by [arg] so should be used with due caution.
//
// `cmd.exe` and `.bat` files use non-standard argument parsing and are especially
// vulnerable to malicious input as they may be used to run arbitrary shell
// commands. Untrusted arguments should be restricted as much as possible.
// For examples on handling this see [rawArg].
//
// ### Batch file special handling
//
// On Windows, `Command` uses the Windows API function [CreateProcessW] to
// spawn new processes. An undocumented feature of this function is that
// when given a `.bat` file as the application to run, it will automatically
// convert that into running `cmd.exe / c` with the batch file as the next argument.
//
// For historical reasons Rust currently preserves this behavior when using
// [Command.new], and escapes the arguments according to `cmd.exe` rules.
// Due to the complexity of `cmd.exe` argument handling, it might not be
// possible to safely escape some special characters, and using them will result
// in an error being returned at process spawn. The set of unescapeable
// special characters might change between releases.
//
// Also note that running batch scripts in this way may be removed in the
// future and so should not be relied upon.
//
// [spawn]: Command.spawn
// [output]: Command.output
//
// [stdout]: Command.stdout
// [stdin]: Command.stdin
// [stderr]: Command.stderr
//
// [Write]: io.Write
// [Read]: io.Read
//
// [arg]: Command.arg
// [args]: Command.args
//
// [CreateProcessW]: https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-createprocessw

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
 * Representation of a running or exited child process.
 *
 * This structure is used to represent and manage child processes. A child
 * process is created via the [Command] struct, which configures the
 * spawning process and can itself be constructed using a builder-style
 * interface.
 *
 * There is no implementation of [Drop] for child processes,
 * so if you do not ensure the `Child` has exited then it will continue to
 * run, even after the `Child` handle to the child process has gone out of
 * scope.
 *
 * Calling [wait] (or other functions that wrap around it) will make
 * the parent process wait until the child has actually exited before
 * continuing.
 *
 * # Warning
 *
 * On some systems, calling [wait] or similar is necessary for the OS to
 * release resources. A process that terminated but has not been waited on is
 * still around as a "zombie". Leaving too many zombies around may exhaust
 * global resources (for example process IDs).
 *
 * The standard library does *not* automatically wait on child processes (not
 * even if the `Child` is dropped), it is up to the application developer to do
 * so. As a consequence, dropping `Child` handles without waiting on them first
 * is not recommended in long-running applications.
 *
 * # Examples
 *
 * ```kotlin
 * import std.process.Command
 *
 * var child = Command.new("/bin/cat").arg("file.txt").spawn().expect("child should spawn")
 * val ecode = child.wait().expect("child should be running")
 * check(ecode.success())
 * ```
 *
 * [wait]: Child.wait
 */
public open class Child(
    public var handle: Process,
    /**
     * The handle for writing to the child's standard input (stdin), if it
     * has been captured. You might find it helpful to do
     *
     * ```ignore (incomplete)
     * let stdin = child.stdin.take().expect("handle should be present");
     * ```
     *
     * to avoid partially moving the `child` and thus blocking yourself from calling
     * functions on `child` while using `stdin`.
     */
    public var stdin: ChildStdin?,
    /**
     * The handle for reading from the child's standard output (stdout), if it
     * has been captured. You might find it helpful to do
     *
     * ```ignore (incomplete)
     * let stdout = child.stdout.take().expect("handle should be present");
     * ```
     *
     * to avoid partially moving the `child` and thus blocking yourself from calling
     * functions on `child` while using `stdout`.
     */
    public var stdout: ChildStdout?,
    /**
     * The handle for reading from the child's standard error (stderr), if it
     * has been captured. You might find it helpful to do
     *
     * ```ignore (incomplete)
     * let stderr = child.stderr.take().expect("handle should be present");
     * ```
     *
     * to avoid partially moving the `child` and thus blocking yourself from calling
     * functions on `child` while using `stderr`.
     */
    public var stderr: ChildStderr?,
) : AsInner<Process>, FromInner<Pair<Process, StdioPipes>, Child>, IntoInner<Process> {
    public override fun asInner(): Process = this.handle

    public override fun intoInner(): Process = this.handle

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "Child").field("stdin", this.stdin).field("stdout", this.stdout).field("stderr", this.stderr).finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    /**
     * Forces the child process to exit. If the child has already exited, `Ok(())`
     * is returned.
     *
     * The mapping to [ErrorKind]s is not part of the compatibility contract of the function.
     *
     * This is equivalent to sending a SIGKILL on Unix platforms.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * var command = Command.new("yes")
     * when (val matchVal = command.spawn()) {
     *     is Ok -> {
     *         val (child) = matchVal
     *         child.kill().expect("process should be killed")
     *     }
     *     else -> {
     *         println("yes command didn't start")
     *     }
     * }
     * ```
     *
     * [ErrorKind]: io.ErrorKind
     * [InvalidInput]: io.ErrorKind.InvalidInput
     */
    public open fun kill(): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.handle.kill()

    /**
     * Returns the OS-assigned process identifier associated with this child.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * var command = Command.new("ls")
     * when (val matchVal = command.spawn()) {
     *     is Ok -> {
     *         val (child) = matchVal
     *         println("Child's ID is ${child.id()}")
     *     }
     *     else -> {
     *         println("ls command didn't start")
     *     }
     * }
     * ```
     */
    public open fun id(): UInt = this.handle.id()

    /**
     * Waits for the child to exit completely, returning the status that it
     * exited with. This function will continue to have the same return value
     * after it has been called at least once.
     *
     * The stdin handle to the child process, if any, will be closed
     * before waiting. This helps avoid deadlock: it ensures that the
     * child does not block waiting for input from the parent, while
     * the parent waits for the child to exit.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * var command = Command.new("ls")
     * when (val matchVal = command.spawn()) {
     *     is Ok -> {
     *         val (child) = matchVal
     *         child.wait().expect("child should be running")
     *         println("Child has finished its execution!")
     *     }
     *     else -> {
     *         println("ls command didn't start")
     *     }
     * }
     * ```
     */
    public open fun wait(): Result<ExitStatus, io.github.kotlinmania.rustlang.core.io.error.Error> {
        (this.stdin.also { this.stdin = null }).let { }
        return this.handle.wait().map({ ExitStatus(it) })
    }

    /**
     * Attempts to collect the exit status of the child if it has already
     * exited.
     *
     * This function will not block the calling thread and will only
     * check to see if the child process has exited or not. If the child has
     * exited then on Unix the process ID is reaped. This function is
     * guaranteed to repeatedly return a successful exit status so long as the
     * child has already exited.
     *
     * If the child has exited, then `Ok(Some(status))` is returned. If the
     * exit status is not available at this time then `Ok(None)` is returned.
     * If an error occurs, then that error is returned.
     *
     * Note that unlike `wait`, this function will not attempt to drop stdin.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * var child = Command.new("ls").spawn().getOrElse { return Result.failure(it) }
     * run {
     *     val matchVal = child.tryWait()
     *     when {
     *         matchVal is Ok && matchVal.value != null -> {
     *             val status = matchVal.value
     *             println("exited with: $status")
     *         }
     *         matchVal is Ok && matchVal.value == null -> {
     *             val (None) = matchVal
     *             println("status not ready yet, let's really wait")
     *             val res = child.wait()
     *             println("result: $res")
     *         }
     *         else -> {
     *             val (e) = matchVal as Err
     *             println("error attempting to wait: $e")
     *         }
     *     }
     * }
     * ```
     */
    public open fun tryWait(): Result<ExitStatus?, io.github.kotlinmania.rustlang.core.io.error.Error> {
        return Result.Ok<ExitStatus?, io.github.kotlinmania.rustlang.core.io.error.Error>(when (val tryResult = this.handle.tryWait()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ExitStatus?, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }.map({ ExitStatus(it) }))
    }

    /**
     * Simultaneously waits for the child to exit and collect all remaining
     * output on the stdout/stderr handles, returning an `Output`
     * instance.
     *
     * The stdin handle to the child process, if any, will be closed
     * before waiting. This helps avoid deadlock: it ensures that the
     * child does not block waiting for input from the parent, while
     * the parent waits for the child to exit.
     *
     * By default, stdin, stdout and stderr are inherited from the parent.
     * In order to capture the output into this `Result<Output>` it is
     * necessary to create new pipes between parent and child. Use
     * `stdout(Stdio.piped())` or `stderr(Stdio.piped())`, respectively.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     * import std.process.Stdio
     *
     * val child = Command.new("/bin/cat").arg("file.txt").stdout(Stdio.piped()).spawn().expect("child should spawn")
     * val output = child.waitWithOutput().expect("waitWithOutput on child should succeed")
     * check(output.status.success())
     * ```
     */
    public open fun waitWithOutput(): Result<Output, io.github.kotlinmania.rustlang.core.io.error.Error> {
        (this.stdin.also { this.stdin = null }).let { }
        var (stdout, stderr) = Pair(mutableListOf(), mutableListOf())
        run {
            val matchVal = Pair(this.stdout.also { this.stdout = null }, this.stderr.also { this.stderr = null })
            when {
                matchVal.first == null && matchVal.second == null -> {}
                matchVal.first != null && matchVal.second == null -> {
                    val out = matchVal.first
                    val res = out.readToEnd(stdout)
                    res.unwrap()
                }
                matchVal.first == null && matchVal.second != null -> {
                    val err = matchVal.second
                    val res1 = err.readToEnd(stderr)
                    res1.unwrap()
                }
                else -> {
                    val out1 = matchVal.first
                    val err1 = matchVal.second
                    val res2 = readOutput(out1.inner, stdout, err1.inner, stderr)
                    res2.unwrap()
                }
            }
        }
        val status = when (val tryResult = this.wait()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Output, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        return Result.Ok<Output, io.github.kotlinmania.rustlang.core.io.error.Error>(Output(status = status, stdout = stdout, stderr = stderr))
    }

    public companion object {
        public fun fromInner(tupleArg0: Pair<Process, StdioPipes>): Child {
            val (handle, io) = tupleArg0
            return Child(handle = handle, stdin = io.stdin.map(ChildStdin::fromInner), stdout = io.stdout.map(ChildStdout::fromInner), stderr = io.stderr.map(ChildStderr::fromInner))
        }
    }
}

/**
 * The pipes connected to a spawned process.
 *
 * Used to pass pipe handles between this module and [imp].
 */
public open class StdioPipes internal constructor(
    stdinCtorArg: ChildPipe?,
    stdoutCtorArg: ChildPipe?,
    stderrCtorArg: ChildPipe?,
) {
    public var stdin: ChildPipe? = stdinCtorArg
        internal set

    public var stdout: ChildPipe? = stdoutCtorArg
        internal set

    public var stderr: ChildPipe? = stderrCtorArg
        internal set

    public companion object {
        public operator fun invoke(
            stdin: ChildPipe?,
            stdout: ChildPipe?,
            stderr: ChildPipe?,
        ): StdioPipes = StdioPipes(
            stdinCtorArg = stdin,
            stdoutCtorArg = stdout,
            stderrCtorArg = stderr,
        )
    }
}

/**
 * A handle to a child process's standard input (stdin).
 *
 * This struct is used in the [stdin] field on [Child].
 *
 * When an instance of `ChildStdin` is [dropped], the `ChildStdin`s underlying
 * file handle will be closed. If the child process was blocked on input prior
 * to being dropped, it will become unblocked after dropping.
 *
 * [stdin]: Child.stdin
 * [dropped]: Drop
 */
public open class ChildStdin internal constructor(
    innerCtorArg: ChildPipe,
) : Write, AsInner<ChildPipe>, IntoInner<ChildPipe>, FromInner<ChildPipe, ChildStdin> {
    public var inner: ChildPipe = innerCtorArg
        internal set

    // In addition to the `implementation`s here, `ChildStdin` also has `implementation`s for
    // `AsFd`/`From<OwnedFd>`/`Into<OwnedFd>` and
    // `AsRawFd`/`IntoRawFd`/`FromRawFd`, on Unix and WASI, and
    // `AsHandle`/`From<OwnedHandle>`/`Into<OwnedHandle>` and
    // `AsRawHandle`/`IntoRawHandle`/`FromRawHandle` on Windows.
    public open fun write(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = (this).write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = (this).writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = Write.isWriteVectored(this)

    public open fun flush(): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = (this).flush()

    public override fun asInner(): ChildPipe = this.inner

    public override fun intoInner(): ChildPipe = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "ChildStdin").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun invoke(
            inner: ChildPipe,
        ): ChildStdin = ChildStdin(
            innerCtorArg = inner,
        )

        public fun fromInner(pipe: ChildPipe): ChildStdin = ChildStdin(inner = pipe)
    }
}

/**
 * A handle to a child process's standard output (stdout).
 *
 * This struct is used in the [stdout] field on [Child].
 *
 * When an instance of `ChildStdout` is [dropped], the `ChildStdout`s
 * underlying file handle will be closed.
 *
 * [stdout]: Child.stdout
 * [dropped]: Drop
 */
public open class ChildStdout internal constructor(
    innerCtorArg: ChildPipe,
) : Read, AsInner<ChildPipe>, IntoInner<ChildPipe>, FromInner<ChildPipe, ChildStdout> {
    public var inner: ChildPipe = innerCtorArg
        internal set

    // In addition to the `implementation`s here, `ChildStdout` also has `implementation`s for
    // `AsFd`/`From<OwnedFd>`/`Into<OwnedFd>` and
    // `AsRawFd`/`IntoRawFd`/`FromRawFd`, on Unix and WASI, and
    // `AsHandle`/`From<OwnedHandle>`/`Into<OwnedHandle>` and
    // `AsRawHandle`/`IntoRawHandle`/`FromRawHandle` on Windows.
    public open fun read(buf: MutableList<UByte>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = this.inner.read(buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.inner.readBuf(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = this.inner.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.inner.isReadVectored()

    public open fun readToEnd(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = this.inner.readToEnd(buf)

    public override fun asInner(): ChildPipe = this.inner

    public override fun intoInner(): ChildPipe = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "ChildStdout").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun invoke(
            inner: ChildPipe,
        ): ChildStdout = ChildStdout(
            innerCtorArg = inner,
        )

        public fun fromInner(pipe: ChildPipe): ChildStdout = ChildStdout(inner = pipe)
    }
}

/**
 * A handle to a child process's stderr.
 *
 * This struct is used in the [stderr] field on [Child].
 *
 * When an instance of `ChildStderr` is [dropped], the `ChildStderr`s
 * underlying file handle will be closed.
 *
 * [stderr]: Child.stderr
 * [dropped]: Drop
 */
public open class ChildStderr internal constructor(
    innerCtorArg: ChildPipe,
) : Read, AsInner<ChildPipe>, IntoInner<ChildPipe>, FromInner<ChildPipe, ChildStderr> {
    public var inner: ChildPipe = innerCtorArg
        internal set

    // In addition to the `implementation`s here, `ChildStderr` also has `implementation`s for
    // `AsFd`/`From<OwnedFd>`/`Into<OwnedFd>` and
    // `AsRawFd`/`IntoRawFd`/`FromRawFd`, on Unix and WASI, and
    // `AsHandle`/`From<OwnedHandle>`/`Into<OwnedHandle>` and
    // `AsRawHandle`/`IntoRawHandle`/`FromRawHandle` on Windows.
    public open fun read(buf: MutableList<UByte>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = this.inner.read(buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, io.github.kotlinmania.rustlang.core.io.error.Error> = this.inner.readBuf(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = this.inner.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.inner.isReadVectored()

    public open fun readToEnd(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.core.io.error.Error> = this.inner.readToEnd(buf)

    public override fun asInner(): ChildPipe = this.inner

    public override fun intoInner(): ChildPipe = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "ChildStderr").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun invoke(
            inner: ChildPipe,
        ): ChildStderr = ChildStderr(
            innerCtorArg = inner,
        )

        public fun fromInner(pipe: ChildPipe): ChildStderr = ChildStderr(inner = pipe)
    }
}

/**
 * A process builder, providing fine-grained control
 * over how a new process should be spawned.
 *
 * A default configuration can be
 * generated using `Command.new(program)`, where `program` gives a path to the
 * program to be executed. Additional builder methods allow the configuration
 * to be changed (for example, by adding arguments) prior to spawning:
 *
 * ```kotlin
 * import std.process.Command
 *
 * val output = if (false) {
 *     Command.new("cmd").args(listOf("/C", "echo hello")).output().expect("process should execute successfully")
 * } else {
 *     Command.new("sh").arg("-c").arg("echo hello").output().expect("process should execute successfully")
 * }
 * val hello = output.stdout
 * ```
 *
 * `Command` can be reused to spawn multiple processes. The builder methods
 * change the command without needing to immediately spawn the process.
 *
 * ```kotlin
 * import std.process.Command
 *
 * var echoHello = Command.new("sh")
 * echoHello.arg("-c").arg("echo hello")
 * val hello1 = echoHello.output().expect("process should execute successfully")
 * val hello2 = echoHello.output().expect("process should execute successfully")
 * ```
 *
 * Similarly, you can call builder methods after spawning a process and then
 * spawn a new process with the modified settings.
 *
 * ```kotlin
 * import std.process.Command
 *
 * var listDir = Command.new("ls")
 * // Execute `ls` in the current directory of the program.
 * listDir.status().expect("process should execute successfully")
 * println("")
 * // Change `ls` to execute in the root directory.
 * listDir.currentDir("/")
 * // And then execute `ls` again but in the root directory.
 * listDir.status().expect("process should execute successfully")
 * ```
 */
public open class Command(
    public var inner: io.github.kotlinmania.rustlang.std.sys.process.unix.common.Command,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.process.unix.common.Command>, AsInnerMut<io.github.kotlinmania.rustlang.std.sys.process.unix.common.Command> {
    /**
     * Adds an argument to pass to the program.
     *
     * Only one argument can be passed per use. So instead of:
     *
     * ```kotlin
     * Command.new("sh").arg("-C /path/to/repo")
     * ```
     *
     * usage would be:
     *
     * ```kotlin
     * Command.new("sh").arg("-C").arg("/path/to/repo")
     * ```
     *
     * To pass multiple arguments see [args].
     *
     * [args]: Command.args
     *
     * Note that the argument is not passed through a shell, but given
     * literally to the program. This means that shell syntax like quotes,
     * escaped characters, word splitting, glob patterns, variable substitution,
     * etc. have no effect.
     *
     * <div class="warning">
     *
     * On Windows, use caution with untrusted inputs. Most applications use the
     * standard convention for decoding arguments passed to them. These are safe to
     * use with `arg`. However, some applications such as `cmd.exe` and `.bat` files
     * use a non-standard way of decoding arguments. They are therefore vulnerable
     * to malicious input.
     *
     * In the case of `cmd.exe` this is especially important because a malicious
     * argument can potentially run arbitrary shell commands.
     *
     * See [Windows argument splitting][windows-args] for more details
     * or [rawArg] for manually implementing non-standard argument encoding.
     *
     * </div>
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * Command.new("ls").arg("-l").arg("-a").spawn().expect("ls command should start")
     * ```
     */
    public open fun arg(arg: OsStr): Command {
        this.inner.arg(arg)
        return this
    }

    /**
     * Adds multiple arguments to pass to the program.
     *
     * To pass a single argument see [arg].
     *
     * [arg]: Command.arg
     *
     * Note that the arguments are not passed through a shell, but given
     * literally to the program. This means that shell syntax like quotes,
     * escaped characters, word splitting, glob patterns, variable substitution, etc.
     * have no effect.
     *
     * <div class="warning">
     *
     * On Windows, use caution with untrusted inputs. Most applications use the
     * standard convention for decoding arguments passed to them. These are safe to
     * use with `arg`. However, some applications such as `cmd.exe` and `.bat` files
     * use a non-standard way of decoding arguments. They are therefore vulnerable
     * to malicious input.
     *
     * In the case of `cmd.exe` this is especially important because a malicious
     * argument can potentially run arbitrary shell commands.
     *
     * See [Windows argument splitting][windows-args] for more details
     * or [rawArg] for manually implementing non-standard argument encoding.
     *
     * </div>
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * Command.new("ls").args(listOf("-l", "-a")).spawn().expect("ls command should start")
     * ```
     */
    public open fun <I> args(args: I): Command where I : Iterable<OsStr> {
        for (arg in args) {
            this.arg(arg.asRef())
        }
        return this
    }

    /**
     * Inserts or updates an explicit environment variable mapping.
     *
     * This method allows you to add an environment variable mapping to the spawned process or
     * overwrite a previously set value. You can use [Command.envs] to set multiple environment
     * variables simultaneously.
     *
     * Child processes will inherit environment variables from their parent process by default.
     * Environment variables explicitly set using [Command.env] take precedence over inherited
     * variables. You can disable environment variable inheritance entirely using
     * [Command.envClear] or for a single key using [Command.envRemove].
     *
     * Note that environment variable names are case-insensitive (but
     * case-preserving) on Windows and case-sensitive on all other platforms.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * Command.new("ls").env("PATH", "/bin").spawn().expect("ls command should start")
     * ```
     */
    public open fun env(key: OsStr, value: OsStr): Command {
        this.inner.envMut().set(key, value)
        return this
    }

    /**
     * Inserts or updates multiple explicit environment variable mappings.
     *
     * This method allows you to add multiple environment variable mappings to the spawned process
     * or overwrite previously set values. You can use [Command.env] to set a single environment
     * variable.
     *
     * Child processes will inherit environment variables from their parent process by default.
     * Environment variables explicitly set using [Command.envs] take precedence over inherited
     * variables. You can disable environment variable inheritance entirely using
     * [Command.envClear] or for a single key using [Command.envRemove].
     *
     * Note that environment variable names are case-insensitive (but case-preserving) on Windows
     * and case-sensitive on all other platforms.
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
     * import std.env
     * import std.process.Command
     * import std.process.Stdio
     *
     * val filteredEnv: MutableMap<String, String> = vars().filter({ (k, _) -> k == "TERM" || k == "TZ" || k == "LANG" || k == "PATH" }).toMap(LinkedHashMap())
     * Command.new("printenv").stdin(ConstPtr(0L)).stdout(Stdio.inherit()).envClear().envs(filteredEnv).spawn().expect("printenv command should start")
     * ```
     */
    public open fun <I> envs(vars: I): Command where I : Iterable<Pair<OsStr, OsStr>> {
        for ((key, value) in vars) {
            this.inner.envMut().set(key.asRef(), value.asRef())
        }
        return this
    }

    /**
     * Removes an explicitly set environment variable and prevents inheriting it from a parent
     * process.
     *
     * This method will remove the explicit value of an environment variable set via
     * [Command.env] or [Command.envs]. In addition, it will prevent the spawned child
     * process from inheriting that environment variable from its parent process.
     *
     * After calling [Command.envRemove], the value associated with its key from
     * [Command.getEnvs] will be [None].
     *
     * To clear all explicitly set environment variables and disable all environment variable
     * inheritance, you can use [Command.envClear].
     *
     * # Examples
     *
     * Prevent any inherited `GIT_DIR` variable from changing the target of the `git` command,
     * while allowing all other variables, like `GIT_AUTHOR_NAME`.
     *
     * ```kotlin
     * import std.process.Command
     *
     * Command.new("git").arg("commit").envRemove("GIT_DIR").spawn().getOrElse { return Result.failure(it) }
     * ```
     */
    public open fun envRemove(key: OsStr): Command {
        this.inner.envMut().remove(key)
        return this
    }

    /**
     * Clears all explicitly set environment variables and prevents inheriting any parent process
     * environment variables.
     *
     * This method will remove all explicitly added environment variables set via [Command.env]
     * or [Command.envs]. In addition, it will prevent the spawned child process from inheriting
     * any environment variable from its parent process.
     *
     * After calling [Command.envClear], the iterator from [Command.getEnvs] will be
     * empty.
     *
     * You can use [Command.envRemove] to clear a single mapping.
     *
     * # Examples
     *
     * The behavior of `sort` is affected by `LANG` and `LC_ *` environment variables.
     * Clearing the environment makes `sort`s behavior independent of the parent processes' language.
     *
     * ```kotlin
     * import std.process.Command
     *
     * Command.new("sort").arg("file.txt").envClear().spawn().getOrElse { return Result.failure(it) }
     * ```
     */
    public open fun envClear(): Command {
        this.inner.envMut().clear()
        return this
    }

    /**
     * Sets the working directory for the child process.
     *
     * # Platform-specific behavior
     *
     * If the program path is relative (e.g., `"./script.sh"`), it's ambiguous
     * whether it should be interpreted relative to the parent's working
     * directory or relative to `currentDir`. The behavior in this case is
     * platform specific and unstable, and it's recommended to use
     * [canonicalize] to get an absolute program path instead.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * Command.new("ls").currentDir("/bin").spawn().expect("ls command should start")
     * ```
     */
    public open fun currentDir(dir: Path): Command {
        this.inner.cwd(dir)
        return this
    }

    /**
     * Configuration for the child process's standard input (stdin) handle.
     *
     * Defaults to [inherit] when used with [spawn] or [status], and
     * defaults to [piped] when used with [output].
     *
     * [inherit]: Stdio.inherit
     * [piped]: Stdio.piped
     * [spawn]: Self.spawn
     * [status]: Self.status
     * [output]: Self.output
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
     * import std.process.Command
     * import std.process.Stdio
     *
     * Command.new("ls").stdin(ConstPtr(0L)).spawn().expect("ls command should start")
     * ```
     */
    public open fun stdin(cfg: Stdio): Command {
        this.inner.stdin(cfg.first)
        return this
    }

    /**
     * Configuration for the child process's standard output (stdout) handle.
     *
     * Defaults to [inherit] when used with [spawn] or [status], and
     * defaults to [piped] when used with [output].
     *
     * [inherit]: Stdio.inherit
     * [piped]: Stdio.piped
     * [spawn]: Self.spawn
     * [status]: Self.status
     * [output]: Self.output
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
     * import std.process.Command
     * import std.process.Stdio
     *
     * Command.new("ls").stdout(ConstPtr(0L)).spawn().expect("ls command should start")
     * ```
     */
    public open fun stdout(cfg: Stdio): Command {
        this.inner.stdout(cfg.first)
        return this
    }

    /**
     * Configuration for the child process's standard error (stderr) handle.
     *
     * Defaults to [inherit] when used with [spawn] or [status], and
     * defaults to [piped] when used with [output].
     *
     * [inherit]: Stdio.inherit
     * [piped]: Stdio.piped
     * [spawn]: Self.spawn
     * [status]: Self.status
     * [output]: Self.output
     *
     * # Examples
     *
     * ```kotlin
     * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
     * import std.process.Command
     * import std.process.Stdio
     *
     * Command.new("ls").stderr(ConstPtr(0L)).spawn().expect("ls command should start")
     * ```
     */
    public open fun stderr(cfg: Stdio): Command {
        this.inner.stderr(cfg.first)
        return this
    }

    /**
     * Executes the command as a child process, returning a handle to it.
     *
     * By default, stdin, stdout and stderr are inherited from the parent.
     *
     * # Errors
     *
     * This method returns an [io.Error] if the child process could not be
     * spawned. Common reasons include:
     *
     * * the program could not be found (for example, it does not exist, or,
     *   when given a bare name, it is not present in the `PATH`);
     * * the current process does not have permission to execute the program
     *   (for example, the file is not marked executable, or execution is
     *   denied by a security policy such as `seccomp`);
     * * the operating system could not create the new process because of
     *   resource exhaustion (for example, a limit on the number of processes
     *   was reached).
     *
     * An error is only returned for failures that occur while the child is
     * being spawned. Once the child has started successfully, anything that
     * happens to it afterwards — including being terminated by a signal — is
     * reported through its [ExitStatus] rather than as an error from the
     * spawning method.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * Command.new("ls").spawn().expect("ls command should start")
     * ```
     */
    public open fun spawn(): Result<Child, io.github.kotlinmania.rustlang.core.io.error.Error> = this.inner.spawn(io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio.Inherit, true).map(Child::fromInner)

    /**
     * Executes the command as a child process, waiting for it to finish and
     * collecting all of its output.
     *
     * By default, stdout and stderr are captured (and used to provide the
     * resulting output). Stdin is not inherited from the parent and any
     * attempt by the child process to read from the stdin stream will result
     * in the stream immediately closing.
     *
     * # Errors
     *
     * Like [spawn], this method returns an [io.Error] if the child
     * process could not be spawned; see [spawn] for the common reasons. It
     * may also return an error if reading the child's output or waiting on the
     * child fails.
     *
     * Note that this method does **not** return an error if the child runs and
     * then exits unsuccessfully, or is terminated by a signal. In those cases
     * it still returns [Ok], and the outcome is reflected in the
     * [ExitStatus] stored in the returned [Output].
     *
     * [spawn]: Command.spawn
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Write
     * import std.process.Command
     *
     * val output = Command.new("/bin/cat").arg("file.txt").output().getOrElse { return Result.failure(it) }
     * println("status: ${output.status}")
     * io.stdout().writeAll(output.stdout).getOrElse { return Result.failure(it) }
     * io.stderr().writeAll(output.stderr).getOrElse { return Result.failure(it) }
     * check(output.status.success())
     * ```
     */
    public open fun output(): Result<Output, io.github.kotlinmania.rustlang.core.io.error.Error> {
        val (status, stdout, stderr) = when (val tryResult = io.github.kotlinmania.rustlang.std.sys.process.output(this.inner)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Output, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        return Result.Ok<Output, io.github.kotlinmania.rustlang.core.io.error.Error>(Output(status = ExitStatus(status), stdout = stdout, stderr = stderr))
    }

    /**
     * Executes a command as a child process, waiting for it to finish and
     * collecting its status.
     *
     * By default, stdin, stdout and stderr are inherited from the parent.
     *
     * # Errors
     *
     * Like [spawn], this method returns an [io.Error] if the child
     * process could not be spawned; see [spawn] for the common reasons. It
     * may also return an error if waiting on the child fails.
     *
     * Note that this method does **not** return an error if the child runs and
     * then exits unsuccessfully, or is terminated by a signal. In those cases
     * it still returns [Ok], and the outcome is reflected in the returned
     * [ExitStatus].
     *
     * [spawn]: Command.spawn
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * val status = Command.new("/bin/cat").arg("file.txt").status().expect("process should execute successfully")
     * println("process finished with: $status")
     * check(status.success())
     * ```
     */
    public open fun status(): Result<ExitStatus, io.github.kotlinmania.rustlang.core.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.process.status(this.inner).map({ ExitStatus(it) })

    /**
     * Returns the path to the program that was given to [Command.new].
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * val cmd = Command.new("echo")
     * check(cmd.getProgram() == "echo")
     * ```
     */
    public open fun getProgram(): OsStr = this.inner.getProgram()

    /**
     * Returns an iterator of the arguments that will be passed to the program.
     *
     * This does not include the path to the program as the first argument;
     * it only includes the arguments specified with [Command.arg] and
     * [Command.args].
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.process.Command
     *
     * var cmd = Command.new("echo")
     * cmd.arg("first").arg("second")
     * val args: List<OsStr> = cmd.getArgs().toMutableList()
     * check(args == listOf("first", "second"))
     * ```
     */
    public open fun getArgs(): CommandArgs = CommandArgs(inner = this.inner.getArgs())

    /**
     * Returns an iterator of the environment variables explicitly set for the child process.
     *
     * Environment variables explicitly set using [Command.env], [Command.envs], and
     * [Command.envRemove] can be retrieved with this method.
     *
     * Note that this output does not include environment variables inherited from the parent
     * process. To see the full list of environment variables, including those inherited from the
     * parent process, use [Command.getResolvedEnvs].
     *
     * Each element is a tuple key/value pair `(OsStr, Option<OsStr>)`. A [None] value
     * indicates its key was explicitly removed via [Command.envRemove]. The associated key for
     * the [None] value will no longer inherit from its parent process.
     *
     * An empty iterator can indicate that no explicit mappings were added or that
     * [Command.envClear] was called. After calling [Command.envClear], the child process
     * will not inherit any environment variables from its parent process.
     *
     * # Examples
     *
     * ```kotlin
     * import std.ffi.OsStr
     * import std.process.Command
     *
     * var cmd = Command.new("ls")
     * cmd.env("TERM", "dumb").envRemove("TZ")
     * val envs: List<Pair<OsStr, OsStr?>> = cmd.getEnvs().toMutableList()
     * check(envs == listOf(Pair(OsStr.new("TERM"), OsStr.new("dumb")), Pair(OsStr.new("TZ"), null)))
     * ```
     */
    public open fun getEnvs(): CommandEnvs = CommandEnvs(iter = this.inner.getEnvs())

    /**
     * Returns an iterator of the environment variables that will be set when the process is spawned.
     *
     * This returns the environment as it would be if the command were executed at the time of calling
     * this method. The returned environment includes:
     * - All inherited environment variables from the parent process (unless [Command.envClear] was called)
     * - All environment variables explicitly set via [Command.env] or [Command.envs]
     * - Excluding any environment variables removed via [Command.envRemove]
     *
     * Note that the returned environment is a snapshot at the time this method is called and will not
     * reflect any subsequent changes to the `Command` or the parent process's environment. Additionally,
     * it will not reflect changes made in a `preExec` hook (on Unix platforms).
     *
     * Each element is a tuple `(OsString, OsString)` representing an environment variable key and value.
     *
     * # Examples
     *
     * ```kotlin
     * import std.env
     * import std.ffi.OsStr
     * import std.ffi.OsString
     * import std.process.Command
     *
     * @feature(commandResolvedEnvs)
     * public fun doctestEntry() {
     *     var cmd = Command.new("ls")
     *     cmd.env("TZ", "UTC")
     *     run {
     *         setVar("EDITOR", "vim")
     *     }
     *     val resolved: MutableMap<OsString, OsString> = cmd.getResolvedEnvs().toMap(LinkedHashMap())
     *     check(resolved.get(OsStr.new("TZ")) == OsString.from("UTC"))
     *     check(resolved.get(OsStr.new("EDITOR")) == OsString.from("vim"))
     * }
     * ```
     */
    public open fun getResolvedEnvs(): CommandResolvedEnvs = this.inner.getResolvedEnvs()

    /**
     * Returns the working directory for the child process.
     *
     * This returns [None] if the working directory will not be changed.
     *
     * # Examples
     *
     * ```kotlin
     * import std.path.Path
     * import std.process.Command
     *
     * var cmd = Command.new("ls")
     * check(cmd.getCurrentDir() == null)
     * cmd.currentDir("/bin")
     * check(cmd.getCurrentDir() == Path.new("/bin"))
     * ```
     */
    public open fun getCurrentDir(): Path? = this.inner.getCurrentDir()

    /**
     * Returns whether the environment will be cleared for the child process.
     *
     * This returns `true` if [Command.envClear] was called, and `false` otherwise.
     * When `true`, the child process will not inherit any environment variables from
     * its parent process.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * @feature(commandResolvedEnvs)
     * public fun doctestEntry() {
     *     var cmd = Command.new("ls")
     *     check(cmd.getEnvClear() == false)
     *     cmd.envClear()
     *     check(cmd.getEnvClear() == true)
     * }
     * ```
     */
    public open fun getEnvClear(): Boolean = this.inner.getEnvClear()

    /**
     * Format the program and arguments of a Command for display. Any
     * non-utf8 data is either escaped or lossily converted using the
     * [utf8 replacement character].
     *
     * The default format approximates a shell invocation of the program along with its
     * arguments. It does not include most of the other command properties. The output is not guaranteed to work
     * (e.g. due to lack of shell-escaping or differences in path resolution).
     * On some platforms you can use [the alternate syntax] to show more fields.
     *
     * Note that the debug implementation is platform-specific.
     *
     * [the alternate syntax]: fmt#sign0
     * [utf8 replacement character]: char.ReplacementCharacter
     */
    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.inner.fmt(f)

    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.process.unix.common.Command = this.inner

    public override fun asInnerMut(): io.github.kotlinmania.rustlang.std.sys.process.unix.common.Command = this.inner

    public companion object {
        /**
         * Constructs a new `Command` for launching the program at
         * path `program`, with the following default configuration:
         *
         * * No arguments to the program
         * * Inherit the current process's environment
         * * Inherit the current process's working directory
         * * Inherit stdin/stdout/stderr for [spawn] or [status], but create pipes for [output]
         *
         * [spawn]: Self.spawn
         * [status]: Self.status
         * [output]: Self.output
         *
         * Builder methods are provided to change these defaults and
         * otherwise configure the process.
         *
         * If `program` is not an absolute path, the `PATH` environment variable
         * will be searched in an OS-defined way.
         *
         * # Platform-specific behavior
         *
         * The details below describe the current behavior, but these details
         * may change in future versions of Rust.
         *
         * On Unix, the `PATH` searched comes from the child's environment:
         *
         * - If the environment is unmodified, the child inherits the parent's
         *   `PATH` and that is what is searched.
         * - If `PATH` is explicitly set via [env], that new value is searched.
         * - If [envClear] or [envRemove] removes `PATH` without a
         *   replacement, `execvp` falls back to an OS-defined default (typically
         *   `/ bin: / usr / bin`), **not** the parent's `PATH`. This may fail to find
         *   programs that rely on the parent's `PATH`.
         *
         * To avoid surprises, use an absolute path or explicitly set `PATH` on
         * the `Command` when modifying the child's environment.
         *
         * On Windows, Rust resolves the executable path before spawning, rather
         * than passing the name to `CreateProcessW` for resolution. When
         * `program` is not an absolute path, the following locations are searched
         * in order:
         *
         * 1. The child's `PATH`, if explicitly set via [env].
         * 2. The directory of the current executable.
         * 3. The system directory (`GetSystemDirectoryW`).
         * 4. The Windows directory (`GetWindowsDirectoryW`).
         * 5. The parent process's `PATH`.
         *
         * Note: when `PATH` is cleared via [envClear] or [envRemove] on
         * Windows, step 1 is skipped but the parent process's `PATH` is still
         * searched at step 5, unlike on Unix.
         *
         * For executable files, the `.exe` extension may be omitted. Files with
         * other extensions must include the extension, otherwise they will not be
         * found. Note that this behavior has some known limitations
         * (see issue #37519).
         *
         * [env]: Self.env
         * [envRemove]: Self.envRemove
         * [envClear]: Self.envClear
         *
         * # Examples
         *
         * ```kotlin
         * import std.process.Command
         *
         * Command.new("sh").spawn().expect("sh command should start")
         * ```
         *
         * # Caveats
         *
         * [Command.new] is only intended to accept the path of the program. If you pass a program
         * path along with arguments like `Command.new("ls -l").spawn()`, it will try to search for
         * `ls - l` literally. The arguments need to be passed separately, such as via [arg] or
         * [args].
         *
         * ```kotlin
         * import std.process.Command
         *
         * Command.new("ls").arg("-l").spawn().expect("ls command should start")
         * ```
         *
         * [arg]: Self.arg
         * [args]: Self.args
         */
        public fun new(program: OsStr): Command = Command(inner = io.github.kotlinmania.rustlang.std.sys.process.unix.common.Command.new(program))
    }
}

/**
 * An iterator over the command arguments.
 *
 * This struct is created by [Command.getArgs]. See its documentation for
 * more.
 */
public open class CommandArgs(
    public var inner: io.github.kotlinmania.rustlang.std.sys.process.unix.common.CommandArgs,
) : Iterable<OsStr> {
    override fun iterator(): Iterator<OsStr> = object : AbstractIterator<OsStr>() {
        override fun computeNext() {
            when (val v = this@CommandArgs.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): OsStr? = this.inner.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.inner.sizeHint()

    public open fun len(): Int = this.inner.len()

    public open fun isEmpty(): Boolean = this.inner.isEmpty()
}

public fun <T> assertSend() {}

public fun <T> assertSync() {}

/**
 * An iterator over the command environment variables.
 *
 * This struct is created by
 * [`Command.getEnvs`][process.Command.getEnvs]. See its
 * documentation for more.
 */
public open class CommandEnvs(
    public var iter: io.github.kotlinmania.rustlang.std.sys.process.env.CommandEnvs,
) : Iterable<Pair<OsStr, OsStr?>> {
    override fun iterator(): Iterator<Pair<OsStr, OsStr?>> = object : AbstractIterator<Pair<OsStr, OsStr?>>() {
        override fun computeNext() {
            when (val v = this@CommandEnvs.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Pair<OsStr, OsStr?>? = this.iter.next()

    public open fun sizeHint(): Pair<Int, Int?> = this.iter.sizeHint()

    public open fun len(): Int = this.iter.len()

    public open fun isEmpty(): Boolean = this.iter.isEmpty()

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.iter.fmt(f)
}

/**
 * The output of a finished process.
 *
 * This is returned in a Result by either the [output] method of a
 * [Command], or the [waitWithOutput] method of a [Child]
 * process.
 *
 * [output]: Command.output
 * [waitWithOutput]: Child.waitWithOutput
 */
public open class Output(
    /**
     * The status (exit code) of the process.
     */
    public var status: ExitStatus,
    /**
     * The data that the process wrote to stdout.
     */
    public var stdout: List<UByte>,
    /**
     * The data that the process wrote to stderr.
     */
    public var stderr: List<UByte>,
) {
    public open fun clone(): Output = Output(status, stdout, stderr)

    /**
     * Returns an error if a nonzero exit status was received.
     *
     * If the [Command] exited successfully,
     * `self` is returned.
     *
     * This is equivalent to calling [`exitOk`](ExitStatus.exitOk)
     * on [`Output.status`](Output.status).
     *
     * Note that this will throw away the [Output.stderr] field in the error case.
     * If the child process outputs useful informantion to stderr, you can:
     * * Use `cmd.stderr(Stdio.inherit())` to forward the
     *   stderr child process to the parent's stderr,
     *   usually printing it to console where the user can see it.
     *   This is usually correct for command-line applications.
     * * Capture `stderr` using a custom error type.
     *   This is usually correct for libraries.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * @feature(exitStatusError)
     * public fun doctestEntry() {
     *     check(Command.new("false").output().unwrap().exitOk().isErr())
     * }
     * ```
     */
    public open fun exitOk(): Result<Output, ExitStatusError> {
        when (val tryResult = this.status.exitOk()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Output, ExitStatusError>(tryResult.errValue()) }
        return Result.Ok<Output, ExitStatusError>(this)
    }

    // If either stderr or stdout are valid utf8 strings it prints the valid
    // strings, otherwise it prints the byte sequence instead
    public open fun fmt(fmt: StringBuilder): Result<Unit, Error> {
        val stdoutUtf8 = String.fromUtf8(this.stdout)
        val stdoutDebug: Any =
            when (stdoutUtf8) { is Result.Ok<*, *> -> {
                val s = stdoutUtf8.okValue()
                s
            }; is Result.Err<*, *> -> {
                this.stdout
            } }
        val stderrUtf8 = String.fromUtf8(this.stderr)
        val stderrDebug: Any =
            when (stderrUtf8) { is Result.Ok<*, *> -> {
                val s1 = stderrUtf8.okValue()
                s1
            }; is Result.Err<*, *> -> {
                this.stderr
            } }
        return run { DebugStruct(fmt, "Output").field("status", this.status).field("stdout", stdoutDebug).field("stderr", stderrDebug).finish(); Result.Ok<Unit, Error>(Unit) }
    }
}

/**
 * Describes what to do with a standard I/O stream for a child process when
 * passed to the [stdin], [stdout], and [stderr] methods of [Command].
 *
 * [stdin]: Command.stdin
 * [stdout]: Command.stdout
 * [stderr]: Command.stderr
 */
public open class Stdio(
    public var value: io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio,
) : FromInner<io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio, Stdio> {
    public var field0: io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio
        get() = value
        set(v) {
            value = v
        }

    /**
     * Returns `true` if this requires [Command] to create a new pipe.
     *
     * # Example
     *
     * ```kotlin
     * import std.process.Stdio
     *
     * @feature(stdioMakesPipe)
     * public fun doctestEntry() {
     *     val io = Stdio.piped()
     *     check(io.makesPipe() == true)
     * }
     * ```
     */
    public open fun makesPipe(): Boolean =
        when (this.value) {
            io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio.MakePipe -> true
            else -> false
        }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { DebugStruct(f, "Stdio").finishNonExhaustive(); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        /**
         * A new pipe should be arranged to connect the parent and child processes.
         *
         * # Examples
         *
         * With stdout:
         *
         * ```kotlin
         * import std.process.Command
         * import std.process.Stdio
         *
         * val output = Command.new("echo").arg("Hello, world!").stdout(Stdio.piped()).output().expect("process should execute successfully")
         * check(String.fromUtf8Lossy(output.stdout) == "Hello, world!\n")
         * // Nothing echoed to console
         * ```
         *
         * With stdin:
         *
         * ```kotlin
         * import std.io.Write
         * import std.process.Command
         * import std.process.Stdio
         *
         * var child = Command.new("rev").stdin(Stdio.piped()).stdout(Stdio.piped()).spawn().expect("rev command should start")
         * var stdin = (child.stdin.also { child.stdin = null } ?: error("child stdin should be retrievable"))
         * spawn({
         *     stdin.writeAll(("Hello, world!").encodeToByteArray().map { it.toUByte() }).expect("writing to child stdin should succeed")
         * })
         * val output = child.waitWithOutput().expect("child stdout should be able to be read")
         * check(String.fromUtf8Lossy(output.stdout) == "!dlrow ,olleH")
         * ```
         *
         * Writing more than a pipe buffer's worth of input to stdin without also reading
         * stdout and stderr at the same time may cause a deadlock.
         * This is an issue when running any program that doesn't guarantee that it reads
         * its entire stdin before writing more than a pipe buffer's worth of output.
         * The size of a pipe buffer varies on different targets.
         */
        public fun piped(): Stdio = Stdio(io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio.MakePipe)

        /**
         * The child inherits from the corresponding parent descriptor.
         *
         * # Examples
         *
         * With stdout:
         *
         * ```kotlin
         * import std.process.Command
         * import std.process.Stdio
         *
         * val output = Command.new("echo").arg("Hello, world!").stdout(Stdio.inherit()).output().expect("process should execute successfully")
         * check(String.fromUtf8Lossy(output.stdout) == "")
         * // "Hello, world!" echoed to console
         * ```
         *
         * With stdin:
         *
         * ```kotlin
         * import std.io.Write
         * import std.process.Command
         * import std.process.Stdio
         *
         * val output = Command.new("rev").stdin(Stdio.inherit()).stdout(Stdio.piped()).output().getOrElse { return Result.failure(it) }
         * print("You piped in the reverse of: ")
         * io.stdout().writeAll(output.stdout).getOrElse { return Result.failure(it) }
         * ```
         */
        public fun inherit(): Stdio = Stdio(io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio.Inherit)

        /**
         * This stream will be ignored. This is the equivalent of attaching the
         * stream to `/ dev / null`.
         *
         * # Examples
         *
         * With stdout:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
         * import std.process.Command
         * import std.process.Stdio
         *
         * val output = Command.new("echo").arg("Hello, world!").stdout(ConstPtr(0L)).output().expect("process should execute successfully")
         * check(String.fromUtf8Lossy(output.stdout) == "")
         * // Nothing echoed to console
         * ```
         *
         * With stdin:
         *
         * ```kotlin
         * import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
         * import std.process.Command
         * import std.process.Stdio
         *
         * val output = Command.new("rev").stdin(ConstPtr(0L)).stdout(Stdio.piped()).output().expect("process should execute successfully")
         * check(String.fromUtf8Lossy(output.stdout) == "")
         * // Ignores any piped-in input
         * ```
         */
        public fun nullVal(): Stdio = Stdio(io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio.Null)

        public fun fromInner(inner: io.github.kotlinmania.rustlang.std.sys.process.unix.common.Stdio): Stdio = Stdio(inner)

        /**
         * Converts a [ChildStdin] into a [Stdio].
         *
         * # Examples
         *
         * `ChildStdin` will be converted to `Stdio` using `Stdio.from` under the hood.
         *
         * ```kotlin
         * import std.process.Command
         * import std.process.Stdio
         *
         * val reverse = Command.new("rev").stdin(Stdio.piped()).spawn().expect("rev command should start")
         * val echo = Command.new("echo").arg("Hello, world!").stdout(reverse.stdin.unwrap()).output().expect("echo command should execute successfully")
         * // "!dlrow ,olleH" echoed to console
         * ```
         */
        public fun from(child: ChildStdin): Stdio = Stdio.fromInner(child.intoInner().into())

        /**
         * Converts a [ChildStdout] into a [Stdio].
         *
         * # Examples
         *
         * `ChildStdout` will be converted to `Stdio` using `Stdio.from` under the hood.
         *
         * ```kotlin
         * import std.process.Command
         * import std.process.Stdio
         *
         * val hello = Command.new("echo").arg("Hello, world!").stdout(Stdio.piped()).spawn().expect("echo command should start")
         * val reverse = Command.new("rev").stdin(hello.stdout.unwrap()).output().expect("rev command should execute successfully")
         * check(reverse.stdout == "!dlrow ,olleH\n".encodeToByteArray())
         * ```
         */
        public fun from(child: ChildStdout): Stdio = Stdio.fromInner(child.intoInner().into())

        /**
         * Converts a [ChildStderr] into a [Stdio].
         *
         * # Examples
         *
         * ```kotlin
         * import std.process.Command
         * import std.process.Stdio
         *
         * val reverse = Command.new("rev").arg("nonExistingFile.txt").stderr(Stdio.piped()).spawn().expect("rev command should start")
         * val cat = Command.new("cat").arg("-").stdin(reverse.stderr.unwrap()).output().expect("cat command should execute successfully")
         * check(String.fromUtf8Lossy(cat.stdout) == "rev: cannot open nonExistingFile.txt: No such file or directory\n")
         * ```
         */
        public fun from(child: ChildStderr): Stdio = Stdio.fromInner(child.intoInner().into())

        /**
         * Converts a [`File`](fs.File) into a [Stdio].
         *
         * # Examples
         *
         * `File` will be converted to `Stdio` using `Stdio.from` under the hood.
         *
         * ```kotlin
         * import std.fs.File
         * import std.process.Command
         *
         * // With the `foo.txt` file containing "Hello, world!"
         * val file = File.open("foo.txt").getOrElse { return Result.failure(it) }
         * val reverse = Command.new("rev").stdin(file).output().getOrElse { return Result.failure(it) }
         * check(reverse.stdout == "!dlrow ,olleH".encodeToByteArray())
         * ```
         */
        public fun from(file: File): Stdio = Stdio.fromInner(file.intoInner().into())

        // "whoami" is a command which exists on both Unix and Windows,
        // and which succeeds, producing some stdout output but no stderr.

        /**
         * Redirect command stdout/stderr to our stdout
         *
         * # Examples
         *
         * ```kotlin
         * import std.io
         * import std.process.Command
         *
         * @feature(exitStatusError)
         * public fun doctestEntry(): Result<Unit> {
         *     val output = Command.new("whoami").stdout(io.stdout()).output().getOrElse { return Result.failure(it) }
         *     output.status.exitOk().getOrElse { return Result.failure(it) }
         *     check(output.stdout.isEmpty())
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun from(inherit: Stdout): Stdio = Stdio.fromInner(inherit.into())

        /**
         * Redirect command stdout/stderr to our stderr
         *
         * # Examples
         *
         * ```kotlin
         * import std.io
         * import std.process.Command
         *
         * @feature(exitStatusError)
         * public fun doctestEntry(): Result<Unit> {
         *     val output = Command.new("whoami").stdout(io.stderr()).output().getOrElse { return Result.failure(it) }
         *     output.status.exitOk().getOrElse { return Result.failure(it) }
         *     check(output.stdout.isEmpty())
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun from(inherit: Stderr): Stdio = Stdio.fromInner(inherit.into())

        public fun from(pipe: PipeWriter): Stdio = Stdio.fromInner(pipe.intoInner().into())

        public fun from(pipe: PipeReader): Stdio = Stdio.fromInner(pipe.intoInner().into())
    }
}

//
// We speak slightly loosely (here and in various other places in the stdlib docs) about `exit`
// vs `_exit`.  Naming of Unix system calls is not standardised across Unices, so terminology is a
// matter of convention and tradition.  For clarity we usually speak of `exit`, even when we might
// mean an underlying system call such as `_exit`.

/**
 * Describes the result of a process after it has terminated.
 *
 * This `class` is used to represent the exit status or other termination of a child process.
 * Child processes are created via the [Command] struct and their exit
 * status is exposed through the [status] method, or the [wait] method
 * of a [Child] process.
 *
 * An `ExitStatus` represents every possible disposition of a process.  On Unix this
 * is the **wait status**.  It is *not* simply an *exit status* (a value passed to `exit`).
 *
 * For proper error reporting of failed processes, print the value of `ExitStatus` or
 * `ExitStatusError` using their implementations of [`Display`](fmt.Display).
 *
 * # Differences from `ExitCode`
 *
 * [ExitCode] is intended for terminating the currently running process, via
 * the `Termination` trait, in contrast to `ExitStatus`, which represents the
 * termination of a child process. These APIs are separate due to platform
 * compatibility differences and their expected usage; it is not generally
 * possible to exactly reproduce an `ExitStatus` from a child for the current
 * process after the fact.
 *
 * [status]: Command.status
 * [wait]: Child.wait
 */
public open class ExitStatus(
    public var value: io.github.kotlinmania.rustlang.std.sys.process.unix.unix.ExitStatus,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.process.unix.unix.ExitStatus>, FromInner<io.github.kotlinmania.rustlang.std.sys.process.unix.unix.ExitStatus, ExitStatus> {
    public var field0: io.github.kotlinmania.rustlang.std.sys.process.unix.unix.ExitStatus
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ExitStatus = ExitStatus(value)

    /**
     * Was termination successful?  Returns a `Result`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * @feature(exitStatusError)
     * public fun doctestEntry() {
     *     val status = Command.new("ls").arg("/dev/nonexistent").status().expect("ls command should execute successfully")
     *     println("ls: $status")
     *     status.exitOk().expectErr("/dev/nonexistent could be listed!")
     * }
     * ```
     */
    public open fun exitOk(): Result<Unit, ExitStatusError> = this.value.exitOk().mapErr({ ExitStatusError(it) })

    /**
     * Was termination successful? Signal termination is not considered a
     * success, and success is defined as a zero exit status.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * val status = Command.new("mkdir").arg("projects").status().expect("mkdir command should execute successfully")
     * if (status.success()) {
     *     println("'projects/' directory created")
     * } else {
     *     println("failed to create projects/' directory: $status")
     * }
     * ```
     */
    public open fun success(): Boolean = this.value.exitOk().isOk()

    /**
     * Returns the exit code of the process, if any.
     *
     * In Unix terms the return value is the **exit status**: the value passed to `exit`, if the
     * process finished by calling `exit`.  Note that on Unix the exit status is truncated to 8
     * bits, and that values that didn't come from a program's call to `exit` may be invented by the
     * runtime system (often, for example, 255, 254, 127 or 126).
     *
     * On Unix, this will return `None` if the process was terminated by a signal.
     * [`ExitStatusExt`](os.unix.process.ExitStatusExt) is an
     * extension trait for extracting any such signal, and other details, from the `ExitStatus`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * val status = Command.new("mkdir").arg("projects").status().expect("mkdir command should execute successfully")
     * when (val matchVal = status.code()) {
     *     null -> println("Process terminated by signal")
     *     else -> {
     *         val code = matchVal
     *         println("Exited with status code: $code")
     *     }
     * }
     * ```
     */
    public open fun code(): Int? = this.value.code()

    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.process.unix.unix.ExitStatus = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = this.value.fmt(f)

    public companion object {
        /**
         * The default value is one which indicates successful completion.
         */
        public fun default(): ExitStatus {
            // Ideally this would be done by ExitCode.default().into() but that is complicated.
            return ExitStatus.fromInner(io.github.kotlinmania.rustlang.std.sys.process.unix.unix.ExitStatus.default())
        }

        public fun fromInner(s: io.github.kotlinmania.rustlang.std.sys.process.unix.unix.ExitStatus): ExitStatus = ExitStatus(s)

        public fun from(error: ExitStatusError): ExitStatus = ExitStatus(error.value.into())
    }
}

// The definition of imp.ExitStatusError should ideally be such that
// Result<(), imp.ExitStatusError> has an identical representation to imp.ExitStatus.

/**
 * Describes the result of a process after it has failed
 *
 * Produced by the [`exitOk`](ExitStatus.exitOk) method on [ExitStatus].
 *
 * # Examples
 *
 * ```kotlin
 * import std.process.Command
 * import std.process.ExitStatusError
 *
 * @feature(exitStatusError)
 * public fun doctestEntry(): Result<Unit> {
 *     fun run(cmd: String): Result<Unit> {
 *         Command.new(cmd).status().unwrap().exitOk().getOrElse { return Result.failure(it) }
 *         return Result.success(Unit)
 *     }
 *     run("true").unwrap()
 *     run("false").unwrapErr()
 *     return Result.success(Unit)
 * }
 * ```
 */
public open class ExitStatusError(
    public var value: io.github.kotlinmania.rustlang.std.sys.process.unix.unix.ExitStatusError,
) {
    public var field0: io.github.kotlinmania.rustlang.std.sys.process.unix.unix.ExitStatusError
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ExitStatusError = ExitStatusError(value)

    /**
     * Reports the exit code, if applicable, from an `ExitStatusError`.
     *
     * In Unix terms the return value is the **exit status**: the value passed to `exit`, if the
     * process finished by calling `exit`.  Note that on Unix the exit status is truncated to 8
     * bits, and that values that didn't come from a program's call to `exit` may be invented by the
     * runtime system (often, for example, 255, 254, 127 or 126).
     *
     * On Unix, this will return `None` if the process was terminated by a signal.  If you want to
     * handle such situations specially, consider using methods from
     * [`ExitStatusExt`](os.unix.process.ExitStatusExt).
     *
     * If the process finished by calling `exit` with a nonzero value, this will return
     * that exit status.
     *
     * If the error was something else, it will return `None`.
     *
     * If the process exited successfully (ie, by calling `exit(0)`), there is no
     * `ExitStatusError`.  So the return value from `ExitStatusError.code()` is always nonzero.
     *
     * # Examples
     *
     * ```kotlin
     * import std.process.Command
     *
     * @feature(exitStatusError)
     * public fun doctestEntry() {
     *     val bad = Command.new("false").status().unwrap().exitOk().unwrapErr()
     *     check(bad.code() == 1)
     * }
     * ```
     */
    public open fun code(): Int? = this.codeNonzero()?.let(Into::into)

    /**
     * Reports the exit code, if applicable, from an `ExitStatusError`, as a [NonZero].
     *
     * This is exactly like [`code`](Self.code), except that it returns a <code>[NonZero]<[i32]></code>.
     *
     * Plain `code`, returning a plain integer, is provided because it is often more convenient.
     * The returned value from `code()` is indeed also nonzero; use `codeNonzero()` when you want
     * a type-level guarantee of nonzeroness.
     *
     * # Examples
     *
     * ```kotlin
     * import std.num.NonZero
     * import std.process.Command
     *
     * @feature(exitStatusError)
     * public fun doctestEntry() {
     *     val bad = Command.new("false").status().unwrap().exitOk().unwrapErr()
     *     check(bad.codeNonzero().unwrap() == NonZero.new(1).unwrap())
     * }
     * ```
     */
    public open fun codeNonzero(): NonZero<Int, NonZeroI32Inner>? = this.value.code()

    /**
     * Converts an `ExitStatusError` (back) to an `ExitStatus`.
     */
    public open fun intoStatus(): ExitStatus = ExitStatus(this.value.into())

    public open fun fmt(f: StringBuilder): Result<Unit, Error> = run { f.append("process exited unsuccessfully: ${this.intoStatus()}"); Result.Ok<Unit, Error>(Unit) }
}

/**
 * This type represents the status code the current process can return
 * to its parent under normal termination.
 *
 * `ExitCode` is intended to be consumed only by the standard library (via
 * [Termination.report]). For forwards compatibility with potentially
 * unusual targets, this type currently does not provide `Eq`, `Hash`, or
 * access to the raw value. This type does provide `PartialEq` for
 * comparison, but note that there may potentially be multiple failure
 * codes, some of which will _not_ compare equal to `ExitCode.FAILURE`.
 * The standard library provides the canonical `SUCCESS` and `FAILURE`
 * exit codes as well as `From<UByte> for ExitCode` for constructing other
 * arbitrary exit codes.
 *
 * # Portability
 *
 * Numeric values used in this type don't have portable meanings, and
 * different platforms may mask different amounts of them.
 *
 * For the platform's canonical successful and unsuccessful codes, see
 * the [Success] and [Failure] associated items.
 *
 * [Success]: ExitCode.Success
 * [Failure]: ExitCode.Failure
 *
 * # Differences from `ExitStatus`
 *
 * `ExitCode` is intended for terminating the currently running process, via
 * the `Termination` trait, in contrast to [ExitStatus], which represents the
 * termination of a child process. These APIs are separate due to platform
 * compatibility differences and their expected usage; it is not generally
 * possible to exactly reproduce an `ExitStatus` from a child for the current
 * process after the fact.
 *
 * # Examples
 *
 * `ExitCode` can be returned from the `main` function of a crate, as it implements
 * [Termination]:
 *
 * ```kotlin
 * import std.process.ExitCode
 *
 * fun main(): ExitCode {
 *     if (!checkFoo()) {
 *         return ExitCode.from(42)
 *     }
 *     return ExitCode.SUCCESS
 * }
 * ```
 */
public open class ExitCode(
    public var value: io.github.kotlinmania.rustlang.std.sys.process.unix.common.ExitCode,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.process.unix.common.ExitCode>, FromInner<io.github.kotlinmania.rustlang.std.sys.process.unix.common.ExitCode, ExitCode>, Termination {
    public var field0: io.github.kotlinmania.rustlang.std.sys.process.unix.common.ExitCode
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): ExitCode = ExitCode(value)

    /**
     * Exit the current process with the given `ExitCode`.
     *
     * Note that this has the same caveats as [`process.exit`][exit], namely that this function
     * terminates the process immediately, so no destructors on the current stack or any other
     * thread's stack will be run. Also see those docs for some important notes on interop with C
     * code. If a clean shutdown is needed, it is recommended to simply return this ExitCode from
     * the `main` function, as demonstrated in the [type documentation](#examples).
     *
     * # Differences from `process.exit()`
     *
     * `process.exit()` accepts any `Int` value as the exit code for the process; however, there
     * are platforms that only use a subset of that value (see [`process.exit` platform-specific
     * behavior][exit#platform-specific-behavior]). `ExitCode` exists because of this; only
     * `ExitCode`s that are supported by a majority of our platforms can be created, so those
     * problems don't exist (as much) with this method.
     *
     * # Examples
     *
     * ```kotlin
     * @feature(exitcodeExitMethod)
     * public fun doctestEntry() {
     *     // there's no way to gracefully recover from an UhOhError, so we just
     *     // print a message and exit
     *     fun handleUnrecoverableError(err: UhOhError): Nothing {
     *         println("UH OH! $err")
     *         val code = when (err) {
     *             UhOhError.GenericProblem -> ExitCode.FAILURE
     *             UhOhError.Specific -> ExitCode.from(3)
     *             is UhOhError.WithCode -> {
     *                 val exitCode = err.exitCode
     *                 exitCode
     *             }
     *         }
     *         return code.exitProcess()
     *     }
     * }
     * ```
     */
    public open fun exitProcess(): Nothing = exit(this.toI32())

    // This is private/perma-unstable because ExitCode is opaque; we don't know that i32 will serve
    // all usecases, for example windows seems to use u32, unix uses the 8-15th bits of an i32, we
    // likely want to isolate users anything that could restrict the platform specific
    // representation of an ExitCode
    //
    // More info: https://internals.rust-lang.org/t/mini-pre-rfc-redesigning-process-exitstatus/5426

    /**
     * Converts an `ExitCode` into an i32
     */
    public open fun toI32(): Int = this.value.asI32()

    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.process.unix.common.ExitCode = this.value

    public override fun report(): ExitCode = this

    public companion object {
        /**
         * The canonical `ExitCode` for successful termination on this platform.
         *
         * Note that a `()`-returning `main` implicitly results in a successful
         * termination, so there's no need to return this from `main` unless
         * you're also returning other possible codes.
         */
        public val SUCCESS: ExitCode = ExitCode(io.github.kotlinmania.rustlang.std.sys.process.unix.common.ExitCode.SUCCESS)

        /**
         * The canonical `ExitCode` for unsuccessful termination on this platform.
         *
         * If you're only returning this and `SUCCESS` from `main`, consider
         * instead returning `Err(_)` and `Ok(())` respectively, which will
         * return the same codes (but will also `eprintln` the error).
         */
        public val FAILURE: ExitCode = ExitCode(io.github.kotlinmania.rustlang.std.sys.process.unix.common.ExitCode.FAILURE)

        /**
         * The default value is [ExitCode.Success]
         */
        public fun default(): ExitCode = ExitCode.SUCCESS

        /**
         * Constructs an `ExitCode` from an arbitrary u8 value.
         */
        public fun from(code: UByte): ExitCode = ExitCode(io.github.kotlinmania.rustlang.std.sys.process.unix.common.ExitCode.from(code))

        public fun fromInner(s: io.github.kotlinmania.rustlang.std.sys.process.unix.common.ExitCode): ExitCode = ExitCode(s)
    }
}

/**
 * Terminates the current process with the specified exit code.
 *
 * This function will never return and will immediately terminate the current
 * process. The exit code is passed through to the underlying OS and will be
 * available for consumption by another process.
 *
 * Note that because this function never returns, and that it terminates the
 * process, no destructors on the current stack or any other thread's stack
 * will be run. If a clean shutdown is needed it is recommended to only call
 * this function at a known point where there are no more destructors left
 * to run; or, preferably, simply return a type implementing [Termination]
 * (such as [ExitCode] or `Result`) from the `main` function and avoid this
 * function altogether:
 *
 * ```kotlin
 * fun main(): Result<Unit> {
 *     // ...
 *     return Result.success(Unit)
 * }
 * ```
 *
 * In its current implementation, this function will execute exit handlers registered with `atexit`
 * as well as other platform-specific exit handlers (e.g. `fini` sections of ELF shared objects).
 * This means that Rust requires that all exit handlers are safe to execute at any time. In
 * particular, if an exit handler cleans up some state that might be concurrently accessed by other
 * threads, it is required that the exit handler performs suitable synchronization with those
 * threads. (The alternative to this requirement would be to not run exit handlers at all, which is
 * considered undesirable. Note that returning from `main` also calls `exit`, so making `exit` an
 * unchecked operation is not an option.)
 *
 * ## Platform-specific behavior
 *
 * **Unix**: On Unix-like platforms, it is unlikely that all 32 bits of `exit`
 * will be visible to a parent process inspecting the exit code. On most
 * Unix-like platforms, only the eight least-significant bits are considered.
 *
 * For example, the exit code for this example will be `0` on Linux, but `256`
 * on Windows:
 *
 * ```kotlin
 * import std.process
 *
 * exit(0x0100)
 * ```
 *
 * ### Safe interop with C code
 *
 * On Unix, this function is currently implemented using the `exit` C function [`exit`][C-exit]. As
 * of C23, the C standard does not permit multiple threads to call `exit` concurrently. Rust
 * mitigates this with a lock, but if C code calls `exit`, that can still cause undefined behavior.
 * Note that returning from `main` is equivalent to calling `exit`.
 *
 * Therefore, it is undefined behavior to have two concurrent threads perform the following
 * without synchronization:
 * - One thread calls Rust's `exit` function or returns from Rust's `main` function
 * - Another thread calls the C function `exit` or `quickExit`, or returns from C's `main` function
 *
 * Note that if a binary contains multiple copies of the Rust runtime (e.g., when combining
 * multiple `cdylib` or `staticlib`), they each have their own separate lock, so from the
 * perspective of code running in one of the Rust runtimes, the "outside" Rust code is basically C
 * code, and concurrent `exit` again causes undefined behavior.
 *
 * Individual C implementations might provide more guarantees than the standard and permit concurrent
 * calls to `exit`; consult the documentation of your C implementation for details.
 *
 * For some of the on-going discussion to make `exit` thread-safe in C, see:
 * - [Rust issue #126600](https://github.com/rust-lang/rust/issues/126600)
 * - [Austin Group Bugzilla (for POSIX)](https://austingroupbugs.net/view.php?id=1845)
 * - [GNU C library Bugzilla](https://sourceware.org/bugzilla/showBug.cgi?id=31997)
 *
 * [C-exit]: https://en.cppreference.com/w/c/program/exit
 */
public fun exit(code: Int): Nothing {
    cleanup()
    return io.github.kotlinmania.rustlang.std.sys.exit.exit(code)
}

// even without panics, this helps for Miri backtraces

/**
 * Terminates the process in an abnormal fashion.
 *
 * The function will never return and will immediately terminate the current
 * process in a platform specific "abnormal" manner. As a consequence,
 * no destructors on the current stack or any other thread's stack
 * will be run, Rust IO buffers (eg, from `BufWriter`) will not be flushed,
 * and C stdio buffers will (on most platforms) not be flushed.
 *
 * This is in contrast to the default behavior of [panic] which unwinds
 * the current thread's stack and calls all destructors.
 * When `panic = "abort"` is set, either as an argument to `rustc` or in a
 * crate's Cargo.toml, [panic] and `abort` are similar. However,
 * [panic] will still call the [panic hook] while `abort` will not.
 *
 * If a clean shutdown is needed it is recommended to only call
 * this function at a known point where there are no more destructors left
 * to run.
 *
 * The process's termination will be similar to that from the C `abort()`
 * function.  On Unix, the process will terminate with signal `SIGABRT`, which
 * typically means that the shell prints "Aborted".
 *
 * # Examples
 *
 * ```kotlin
 * import std.process
 *
 * fun main() {
 *     println("aborting")
 *     abort()
 *     // execution never gets here
 * }
 * ```
 *
 * The `abort` function terminates the process, so the destructor will not
 * get run on the example below:
 *
 * ```kotlin
 * import std.process
 *
 * open class HasDrop {
 *     public open fun drop() {
 *         println("This will never be printed!")
 *     }
 * }
 * fun main() {
 *     val x = HasDrop()
 *     abort()
 *     // the destructor implemented for HasDrop will never get run
 * }
 * ```
 */
public fun abort(): Nothing {
    abortInternal()
}

/**
 * Returns the OS-assigned process identifier associated with this process.
 *
 * # Examples
 *
 * ```kotlin
 * import std.process
 *
 * println("My pid is ${id()}")
 * ```
 */
public fun id(): UInt = getpid()

/**
 * A trait for implementing arbitrary return types in the `main` function.
 *
 * The C-main function only supports returning integers.
 * So, every type implementing the `Termination` trait has to be converted
 * to an integer.
 *
 * The default implementations are returning `libc.EXIT_SUCCESS` to indicate
 * a successful execution. In case of a failure, `libc.EXIT_FAILURE` is returned.
 *
 * Because different runtimes have different specifications on the return value
 * of the `main` function, this trait is likely to be available only on
 * standard library's runtime for convenience. Other runtimes are not required
 * to provide similar functionality.
 */
public interface Termination {
    /**
     * Is called to get the representation of the value as status code.
     * This status code is returned to the operating system.
     */
    public fun report(): ExitCode
}

public fun Unit.report(): ExitCode = ExitCode.SUCCESS

public fun Nothing.report(): ExitCode = this

public fun <T : Termination, E> Result<T, E>.report(): ExitCode =
    when (this) { is Result.Ok<*, *> -> {
        val value = this.okValue()
        value.report()
    }; is Result.Err<*, *> -> {
        val err = this.errValue()
        attemptPrintToStderr(formatArgsNl("Error: {err:?}"))
        ExitCode.FAILURE
    } }
