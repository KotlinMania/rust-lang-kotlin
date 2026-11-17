// port-lint: source io/stdio.rs
package io.github.kotlinmania.rustlang.std.io.stdio

import io.github.kotlinmania.rustlang.alloc.io.buffered.bufreader.BufReader
import io.github.kotlinmania.rustlang.alloc.io.buffered.linewriter.LineWriter
import io.github.kotlinmania.rustlang.alloc.io.util.Lines
import io.github.kotlinmania.rustlang.alloc.io.util.SpecReadByte
import io.github.kotlinmania.rustlang.alloc.io.util.lines
import io.github.kotlinmania.rustlang.core.cell.RefCell
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.sync.atomic.Align1
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicBool
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.MutexGuard
import io.github.kotlinmania.rustlang.std.sync.oncelock.OnceLock
import io.github.kotlinmania.rustlang.std.sync.reentrantlock.ReentrantLock
import io.github.kotlinmania.rustlang.std.sync.reentrantlock.ReentrantLockGuard
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.STDIN_BUF_SIZE
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.isEbadf
import io.github.kotlinmania.rustlang.std.thread.AccessError

public fun interface FnCallback {
    public operator fun invoke(): T
}

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

public typealias LocalStream = Mutex<List<UByte>>

/**
 * Flag to indicate OUTPUT_CAPTURE is used.
 *
 * If it is None and was never set on any thread, this flag is set to false,
 * and OUTPUT_CAPTURE can be safely ignored on all threads, saving some time
 * and memory registering an unused thread local.
 *
 * Note about memory ordering: This contains information about whether a
 * thread local variable might be in use. Although this is a global flag, the
 * memory ordering between threads does not matter: we only want this flag to
 * have a consistent order between setOutputCapture and printTo *within
 * the same thread*. Within the same thread, things always have a perfectly
 * consistent order. So Ordering.Relaxed is fine.
 */
public var OUTPUT_CAPTURE_USED: Atomic<Boolean, Align1<UByte>> = AtomicBool.new(false)

/**
 * A handle to a raw instance of the standard input stream of this process.
 *
 * This handle is not synchronized or buffered in any fashion. Constructed via
 * the `std.io.stdio.stdinRaw` function.
 */
public open class StdinRaw(
    public var value: io.github.kotlinmania.rustlang.std.sys.stdio.unix.Stdin,
) : Read {
    public var field0: io.github.kotlinmania.rustlang.std.sys.stdio.unix.Stdin
        get() = value
        set(v) {
            value = v
        }

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> = handleEbadf(this.value.read(buf), { Result.Ok(0) })

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, Error> = handleEbadf(this.value.readBuf(buf), { Result.Ok(Unit) })

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = handleEbadf(this.value.readVectored(bufs), { Result.Ok(0) })

    public open fun isReadVectored(): Boolean = this.value.isReadVectored()

    public open fun readExact(buf: MutableList<UByte>): Result<Unit, Error> {
        if (buf.isEmpty()) {
            return Result.Ok<Unit, Error>(Unit)
        }
        return handleEbadf(this.value.readExact(buf), { Result.Err(Error.READ_EXACT_EOF) })
    }

    public open fun readBufExact(buf: BorrowedCursor<UByte>): Result<Unit, Error> {
        if (buf.capacity() == 0) {
            return Result.Ok<Unit, Error>(Unit)
        }
        return handleEbadf(this.value.readBufExact(buf), { Result.Err(Error.READ_EXACT_EOF) })
    }

    public open fun readToEnd(buf: List<UByte>): Result<Int, Error> = handleEbadf(this.value.readToEnd(buf), { Result.Ok(0) })

    public open fun readToString(buf: String): Result<Int, Error> = handleEbadf(this.value.readToString(buf), { Result.Ok(0) })
}

/**
 * A handle to a raw instance of the standard output stream of this process.
 *
 * This handle is not synchronized or buffered in any fashion. Constructed via
 * the `std.io.stdio.stdoutRaw` function.
 */
public open class StdoutRaw(
    public var value: io.github.kotlinmania.rustlang.std.sys.stdio.unix.Stdout,
) : Write {
    public var field0: io.github.kotlinmania.rustlang.std.sys.stdio.unix.Stdout
        get() = value
        set(v) {
            value = v
        }

    public open fun write(buf: List<UByte>): Result<Int, Error> = handleEbadf(this.value.write(buf), { Result.Ok(buf.size) })

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> {
        val total = { Result.Ok(bufs.map({ b -> b.len() }).sum()) }
        return handleEbadf(this.value.writeVectored(bufs), total)
    }

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public open fun flush(): Result<Unit, Error> = handleEbadf(this.value.flush(), { Result.Ok(Unit) })

    public open fun writeAll(buf: List<UByte>): Result<Unit, Error> = handleEbadf(this.value.writeAll(buf), { Result.Ok(Unit) })

    public open fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit, Error> = handleEbadf(this.value.writeAllVectored(bufs), { Result.Ok(Unit) })

    public open fun writeFmt(fmt: Arguments): Result<Unit, Error> = handleEbadf(this.value.writeFmt(fmt), { Result.Ok(Unit) })
}

/**
 * A handle to a raw instance of the standard output stream of this process.
 *
 * This handle is not synchronized or buffered in any fashion. Constructed via
 * the `std.io.stdio.stderrRaw` function.
 */
public open class StderrRaw(
    public var value: io.github.kotlinmania.rustlang.std.sys.stdio.unix.Stderr,
) : Write {
    public var field0: io.github.kotlinmania.rustlang.std.sys.stdio.unix.Stderr
        get() = value
        set(v) {
            value = v
        }

    public open fun write(buf: List<UByte>): Result<Int, Error> = handleEbadf(this.value.write(buf), { Result.Ok(buf.size) })

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> {
        val total = { Result.Ok(bufs.map({ b -> b.len() }).sum()) }
        return handleEbadf(this.value.writeVectored(bufs), total)
    }

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public open fun flush(): Result<Unit, Error> = handleEbadf(this.value.flush(), { Result.Ok(Unit) })

    public open fun writeAll(buf: List<UByte>): Result<Unit, Error> = handleEbadf(this.value.writeAll(buf), { Result.Ok(Unit) })

    public open fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit, Error> = handleEbadf(this.value.writeAllVectored(bufs), { Result.Ok(Unit) })

    public open fun writeFmt(fmt: Arguments): Result<Unit, Error> = handleEbadf(this.value.writeFmt(fmt), { Result.Ok(Unit) })
}

/**
 * Constructs a new raw handle to the standard input of this process.
 *
 * The returned handle does not interact with any other handles created nor
 * handles returned by `std.io.stdin`. Data buffered by the `std.io.stdin`
 * handles is **not** available to raw handles returned from this function.
 *
 * The returned handle has no external synchronization or buffering.
 */
public fun stdinRaw(): StdinRaw = StdinRaw(io.github.kotlinmania.rustlang.std.sys.stdio.unix.Stdin.new())

/**
 * Constructs a new raw handle to the standard output stream of this process.
 *
 * The returned handle does not interact with any other handles created nor
 * handles returned by `std.io.stdout`. Note that data is buffered by the
 * `std.io.stdout` handles so writes which happen via this raw handle may
 * appear before previous writes.
 *
 * The returned handle has no external synchronization or buffering layered on
 * top.
 */
public fun stdoutRaw(): StdoutRaw = StdoutRaw(io.github.kotlinmania.rustlang.std.sys.stdio.unix.Stdout.new())

/**
 * Constructs a new raw handle to the standard error stream of this process.
 *
 * The returned handle does not interact with any other handles created nor
 * handles returned by `std.io.stderr`.
 *
 * The returned handle has no external synchronization or buffering layered on
 * top.
 */
public fun stderrRaw(): StderrRaw = StderrRaw(io.github.kotlinmania.rustlang.std.sys.stdio.unix.Stderr.new())

public fun <T> handleEbadf(r: Result<T, Error>, default: () -> Result<T, Error>): Result<T, Error> =
    when {
        r is Result.Err<*, *> && isEbadf(r.error) -> {
            val e = r.error
            default()
        }
        else -> {
            val r1 = r
            r1
        }
    }

/**
 * A handle to the standard input stream of a process.
 *
 * Each handle is a shared reference to a global buffer of input data to this
 * process. A handle can be `lock`d to gain full access to [BufRead] methods
 * (e.g., `.lines()`). Reads to this handle are otherwise locked with respect
 * to other reads.
 *
 * This handle implements the `Read` trait, but beware that concurrent reads
 * of `Stdin` must be executed with care.
 *
 * Created by the [io.stdin] method.
 *
 * [io.stdin]: stdin
 *
 * ### Note: Windows Portability Considerations
 *
 * When operating in a console, the Windows implementation of this stream does not support
 * non-UTF-8 byte sequences. Attempting to read bytes that are not valid UTF-8 will return
 * an error.
 *
 * In a process with a detached console, such as one using
 * `@file:windowsSubsystem= "windows"`, or in a child process spawned from such a process,
 * the contained handle will be null. In such cases, the standard library's `Read` and
 * `Write` will do nothing and silently succeed. All other I/O operations, via the
 * standard library or via raw Windows API calls, will fail.
 *
 * # Examples
 *
 * ```kotlin
 * import std.io
 *
 * fun main(): io.Result<Unit> {
 *     var buffer = ""
 *     val stdin = io.stdin()
 *     // We get `Stdin` here.
 *     stdin.readLine(buffer).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public open class Stdin internal constructor(
    innerCtorArg: Mutex<BufReader<StdinRaw>>,
) : Read, IsTerminal {
    public var inner: Mutex<BufReader<StdinRaw>> = innerCtorArg
        internal set

    /**
     * Locks this handle to the standard input stream, returning a readable
     * guard.
     *
     * The lock is released when the returned lock goes out of scope. The
     * returned guard also implements the [Read] and [BufRead] traits for
     * accessing the underlying data.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.BufRead
     *
     * fun main(): io.Result<Unit> {
     *     var buffer = ""
     *     val stdin = io.stdin()
     *     var handle = stdin.lock()
     *     handle.readLine(buffer).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun lock(): StdinLock {
        // Locks this handle with static scope. This depends on the
        // implementation detail that the underlying `Mutex` is static.
        return StdinLock(inner = this.inner.lock().unwrapOrElse({ e -> e.intoInner() }))
    }

    /**
     * Locks this handle and reads a line of input, appending it to the specified buffer.
     *
     * For detailed semantics of this method, see the documentation on
     * [BufRead.readLine]. In particular:
     * * Previous content of the buffer will be preserved. To avoid appending
     *   to the buffer, you need to [clear] it first.
     * * The trailing newline character, if any, is included in the buffer.
     *
     * [clear]: String.clear
     *
     * # Examples
     *
     * ```kotlin
     * import std.io
     *
     * var input = ""
     * run {
     *     val matchVal = io.stdin().readLine(input)
     *     if (matchVal.isSuccess) {
     *         val n = matchVal.getOrThrow()
     *         println("$n bytes read")
     *         println("$input")
     *     } else {
     *         val error = matchVal.exceptionOrNull()!!
     *         println("error: $error")
     *     }
     * }
     * ```
     *
     * You can run the example one of two ways:
     *
     * - Pipe some text to it, e.g., `printf foo | path / to / executable`
     * - Give it text interactively by running the executable directly,
     *   in which case it will wait for the Enter key to be pressed before
     *   continuing
     */
    public open fun readLine(buf: String): Result<Int, Error> = this.lock().readLine(buf)

    /**
     * Consumes this handle and returns an iterator over input lines.
     *
     * For detailed semantics of this method, see the documentation on
     * [BufRead.lines].
     *
     * # Examples
     *
     * ```kotlin
     * import std.io
     *
     * val lines = io.stdin().lines()
     * for (line in lines) {
     *     println("got a line: ${line.unwrap()}")
     * }
     * ```
     */
    public open fun lines(): Lines<StdinLock> = this.lock().lines()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "Stdin").finishNonExhaustive(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> = this.lock().read(buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, Error> = this.lock().readBuf(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = this.lock().readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.lock().isReadVectored()

    public open fun readToEnd(buf: List<UByte>): Result<Int, Error> = this.lock().readToEnd(buf)

    public open fun readToString(buf: String): Result<Int, Error> = this.lock().readToString(buf)

    public open fun readExact(buf: MutableList<UByte>): Result<Unit, Error> = this.lock().readExact(buf)

    public open fun readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit, Error> = this.lock().readBufExact(cursor)

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.isatty.isTerminal(this)

    public companion object {
        public operator fun invoke(
            inner: Mutex<BufReader<StdinRaw>>,
        ): Stdin = Stdin(
            innerCtorArg = inner,
        )
    }
}

/**
 * A locked reference to the [Stdin] handle.
 *
 * This handle implements both the [Read] and [BufRead] traits, and
 * is constructed via the [Stdin.lock] method.
 *
 * ### Note: Windows Portability Considerations
 *
 * When operating in a console, the Windows implementation of this stream does not support
 * non-UTF-8 byte sequences. Attempting to read bytes that are not valid UTF-8 will return
 * an error.
 *
 * In a process with a detached console, such as one using
 * `@file:windowsSubsystem= "windows"`, or in a child process spawned from such a process,
 * the contained handle will be null. In such cases, the standard library's `Read` and
 * `Write` will do nothing and silently succeed. All other I/O operations, via the
 * standard library or via raw Windows API calls, will fail.
 *
 * # Examples
 *
 * ```kotlin
 * import std.io.BufRead
 *
 * fun main(): io.Result<Unit> {
 *     var buffer = ""
 *     val stdin = io.stdin()
 *     // We get `Stdin` here.
 *     run {
 *         var handle = stdin.lock()
 *         // We get `StdinLock` here.
 *         handle.readLine(buffer).getOrElse { return Result.failure(it) }
 *     }
 *     // `StdinLock` is dropped here.
 *     return Result.success(Unit)
 * }
 * ```
 */
public open class StdinLock internal constructor(
    innerCtorArg: MutexGuard<BufReader<StdinRaw>>,
) : Read, SpecReadByte, BufRead, IsTerminal {
    public var inner: MutexGuard<BufReader<StdinRaw>> = innerCtorArg
        internal set

    // only used by platform-dependent io.copy specializations, i.e. unused on some platforms
    public open fun asMutBuf(): kotlin.reflect.KMutableProperty0<MutexGuard<BufReader<StdinRaw>>> {
        return this::inner
    }

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> = this.inner.read(buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, Error> = this.inner.readBuf(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = this.inner.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.inner.isReadVectored()

    public open fun readToEnd(buf: List<UByte>): Result<Int, Error> = this.inner.readToEnd(buf)

    public open fun readToString(buf: String): Result<Int, Error> = this.inner.readToString(buf)

    public open fun readExact(buf: MutableList<UByte>): Result<Unit, Error> = this.inner.readExact(buf)

    public open fun readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit, Error> = this.inner.readBufExact(cursor)

    public open fun specReadByte(): Result<UByte, Error>? = BufReader.specReadByte(this.inner)

    public open fun fillBuf(): Result<List<UByte>, Error> = this.inner.fillBuf()

    public open fun consume(n: Int) {
        this.inner.consume(n)
    }

    public open fun readUntil(byte: UByte, buf: List<UByte>): Result<Int, Error> = this.inner.readUntil(byte, buf)

    public open fun readLine(buf: String): Result<Int, Error> = this.inner.readLine(buf)

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "StdinLock").finishNonExhaustive(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.isatty.isTerminal(this)

    public companion object {
        public operator fun invoke(
            inner: MutexGuard<BufReader<StdinRaw>>,
        ): StdinLock = StdinLock(
            innerCtorArg = inner,
        )
    }
}

/**
 * Constructs a new handle to the standard input of the current process.
 *
 * Each handle returned is a reference to a shared global buffer whose access
 * is synchronized via a mutex. If you need more explicit control over
 * locking, see the [Stdin.lock] method.
 *
 * ### Note: Windows Portability Considerations
 *
 * When operating in a console, the Windows implementation of this stream does not support
 * non-UTF-8 byte sequences. Attempting to read bytes that are not valid UTF-8 will return
 * an error.
 *
 * In a process with a detached console, such as one using
 * `@file:windowsSubsystem= "windows"`, or in a child process spawned from such a process,
 * the contained handle will be null. In such cases, the standard library's `Read` and
 * `Write` will do nothing and silently succeed. All other I/O operations, via the
 * standard library or via raw Windows API calls, will fail.
 *
 * # Examples
 *
 * Using implicit synchronization:
 *
 * ```kotlin
 * import std.io
 *
 * fun main(): io.Result<Unit> {
 *     var buffer = ""
 *     io.stdin().readLine(buffer).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Using explicit synchronization:
 *
 * ```kotlin
 * import std.io.BufRead
 *
 * fun main(): io.Result<Unit> {
 *     var buffer = ""
 *     val stdin = io.stdin()
 *     var handle = stdin.lock()
 *     handle.readLine(buffer).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun stdin(): Stdin {
    val INSTANCE: OnceLock<Mutex<BufReader<StdinRaw>>>  = OnceLock.new()
    return Stdin(inner = INSTANCE.getOrInit({
        Mutex.new(BufReader.withCapacity(STDIN_BUF_SIZE, stdinRaw()))
    }))
}

/**
 * A handle to the global standard output stream of the current process.
 *
 * Each handle shares a global buffer of data to be written to the standard
 * output stream. Access is also synchronized via a lock and explicit control
 * over locking is available via the [lock] method.
 *
 * By default, the handle is line-buffered when connected to a terminal, meaning
 * it flushes automatically when a newline (`\n`) is encountered. For immediate
 * output, you can manually call the [flush] method. When the handle goes out
 * of scope, the buffer is automatically flushed.
 *
 * Created by the [io.stdout] method.
 *
 * ### Note: Windows Portability Considerations
 *
 * When operating in a console, the Windows implementation of this stream decodes bytes
 * as UTF-8, replacing malformed sequences with the Unicode replacement character (U+FFFD).
 *
 * In a process with a detached console, such as one using
 * `@file:windowsSubsystem= "windows"`, or in a child process spawned from such a process,
 * the contained handle will be null. In such cases, the standard library's `Read` and
 * `Write` will do nothing and silently succeed. All other I/O operations, via the
 * standard library or via raw Windows API calls, will fail.
 *
 * [lock]: Stdout.lock
 * [flush]: Write.flush
 * [io.stdout]: stdout
 */
public open class Stdout internal constructor(
    innerCtorArg: ReentrantLock<RefCell<LineWriter<StdoutRaw>>>,
) : Write, IsTerminal {
    // Note: this should be LineWriter or BufWriter depending on the state of
    // stdout (tty or not). Note that if this is not line buffered it
    // should also flush-on-panic or some form of flush-on-abort.
    public var inner: ReentrantLock<RefCell<LineWriter<StdoutRaw>>> = innerCtorArg
        internal set

    /**
     * Locks this handle to the standard output stream, returning a writable
     * guard.
     *
     * The lock is released when the returned lock goes out of scope. The
     * returned guard also implements the `Write` trait for writing data.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Write
     *
     * fun main(): io.Result<Unit> {
     *     var stdout = io.stdout().lock()
     *     stdout.writeAll("hello world".encodeToByteArray()).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun lock(): StdoutLock {
        // Locks this handle with static scope. This depends on the
        // implementation detail that the underlying `ReentrantMutex` is
        // static.
        return StdoutLock(inner = this.inner.lock())
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "Stdout").finishNonExhaustive(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun write(buf: List<UByte>): Result<Int, Error> = (this).write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = (this).writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = Write.isWriteVectored(this)

    public open fun flush(): Result<Unit, Error> = (this).flush()

    public open fun writeAll(buf: List<UByte>): Result<Unit, Error> = (this).writeAll(buf)

    public open fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit, Error> = (this).writeAllVectored(bufs)

    public open fun writeFmt(args: Arguments): Result<Unit, Error> = (this).writeFmt(args)

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.isatty.isTerminal(this)

    public companion object {
        public operator fun invoke(
            inner: ReentrantLock<RefCell<LineWriter<StdoutRaw>>>,
        ): Stdout = Stdout(
            innerCtorArg = inner,
        )
    }
}

/**
 * A locked reference to the [Stdout] handle.
 *
 * This handle implements the [Write] trait, and is constructed via
 * the [Stdout.lock] method. See its documentation for more.
 *
 * By default, the handle is line-buffered when connected to a terminal, meaning
 * it flushes automatically when a newline (`\n`) is encountered. For immediate
 * output, you can manually call the [flush] method. When the handle goes out
 * of scope, the buffer is automatically flushed.
 *
 * ### Note: Windows Portability Considerations
 *
 * When operating in a console, the Windows implementation of this stream decodes bytes
 * as UTF-8, replacing malformed sequences with the Unicode replacement character (U+FFFD).
 *
 * In a process with a detached console, such as one using
 * `@file:windowsSubsystem= "windows"`, or in a child process spawned from such a process,
 * the contained handle will be null. In such cases, the standard library's `Read` and
 * `Write` will do nothing and silently succeed. All other I/O operations, via the
 * standard library or via raw Windows API calls, will fail.
 *
 * [flush]: Write.flush
 */
public open class StdoutLock internal constructor(
    innerCtorArg: ReentrantLockGuard<RefCell<LineWriter<StdoutRaw>>>,
) : Write, IsTerminal {
    public var inner: ReentrantLockGuard<RefCell<LineWriter<StdoutRaw>>> = innerCtorArg
        internal set

    public open fun write(buf: List<UByte>): Result<Int, Error> = this.inner.borrowMut().write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = this.inner.borrowMut().writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.inner.borrowMut().isWriteVectored()

    public open fun flush(): Result<Unit, Error> = this.inner.borrowMut().flush()

    public open fun writeAll(buf: List<UByte>): Result<Unit, Error> = this.inner.borrowMut().writeAll(buf)

    public open fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit, Error> = this.inner.borrowMut().writeAllVectored(bufs)

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "StdoutLock").finishNonExhaustive(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.isatty.isTerminal(this)

    public companion object {
        public operator fun invoke(
            inner: ReentrantLockGuard<RefCell<LineWriter<StdoutRaw>>>,
        ): StdoutLock = StdoutLock(
            innerCtorArg = inner,
        )
    }
}

public var STDOUT: OnceLock<ReentrantLock<RefCell<LineWriter<StdoutRaw>>>> = OnceLock.new()

/**
 * Constructs a new handle to the standard output of the current process.
 *
 * Each handle returned is a reference to a shared global buffer whose access
 * is synchronized via a mutex. If you need more explicit control over
 * locking, see the [Stdout.lock] method.
 *
 * By default, the handle is line-buffered when connected to a terminal, meaning
 * it flushes automatically when a newline (`\n`) is encountered. For immediate
 * output, you can manually call the [flush] method. When the handle goes out
 * of scope, the buffer is automatically flushed.
 *
 * ### Note: Windows Portability Considerations
 *
 * When operating in a console, the Windows implementation of this stream decodes bytes
 * as UTF-8, replacing malformed sequences with the Unicode replacement character (U+FFFD).
 *
 * In a process with a detached console, such as one using
 * `@file:windowsSubsystem= "windows"`, or in a child process spawned from such a process,
 * the contained handle will be null. In such cases, the standard library's `Read` and
 * `Write` will do nothing and silently succeed. All other I/O operations, via the
 * standard library or via raw Windows API calls, will fail.
 *
 * # Examples
 *
 * Using implicit synchronization:
 *
 * ```kotlin
 * import std.io.Write
 *
 * fun main(): io.Result<Unit> {
 *     io.stdout().writeAll("hello world".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Using explicit synchronization:
 *
 * ```kotlin
 * import std.io.Write
 *
 * fun main(): io.Result<Unit> {
 *     val stdout = io.stdout()
 *     var handle = stdout.lock()
 *     handle.writeAll("hello world".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Ensuring output is flushed immediately:
 *
 * ```kotlin
 * import std.io.Write
 *
 * fun main(): io.Result<Unit> {
 *     var stdout = io.stdout()
 *     stdout.writeAll("hello, ".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     stdout.flush().getOrElse { return Result.failure(it) }
 *     // Manual flush
 *     stdout.writeAll("world!\n".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     // Automatically flushed
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [flush]: Write.flush
 */
public fun stdout(): Stdout = Stdout(inner = STDOUT.getOrInit({ ReentrantLock.new(RefCell.new(LineWriter.new(stdoutRaw()))) }))

// Flush the data and disable buffering during shutdown
// by replacing the line writer by one with zero
// buffering capacity.
public fun cleanup() {
    var initialized = false
    val stdout =
        STDOUT.getOrInit({
            initialized = true
            ReentrantLock.new(RefCell.new(LineWriter.withCapacity(0, stdoutRaw())))
        })
    if (!initialized) {
        // The buffer was previously initialized, overwrite it here.
        // We use tryLock() instead of lock(), because someone
        // might have leaked a StdoutLock, which would
        // otherwise cause a deadlock here.
        when (val matchVal = stdout.tryLock()) {
            null -> {}
            else -> {
                val lock = matchVal
                lock.borrowMut() = LineWriter.withCapacity(0, stdoutRaw())
            }
        }
    }
}

/**
 * A handle to the standard error stream of a process.
 *
 * For more information, see the [io.stderr] method.
 *
 * [io.stderr]: stderr
 *
 * ### Note: Windows Portability Considerations
 *
 * When operating in a console, the Windows implementation of this stream decodes bytes
 * as UTF-8, replacing malformed sequences with the Unicode replacement character (U+FFFD).
 *
 * In a process with a detached console, such as one using
 * `@file:windowsSubsystem= "windows"`, or in a child process spawned from such a process,
 * the contained handle will be null. In such cases, the standard library's `Read` and
 * `Write` will do nothing and silently succeed. All other I/O operations, via the
 * standard library or via raw Windows API calls, will fail.
 */
public open class Stderr internal constructor(
    innerCtorArg: ReentrantLock<RefCell<StderrRaw>>,
) : Write, IsTerminal {
    public var inner: ReentrantLock<RefCell<StderrRaw>> = innerCtorArg
        internal set

    /**
     * Locks this handle to the standard error stream, returning a writable
     * guard.
     *
     * The lock is released when the returned lock goes out of scope. The
     * returned guard also implements the [Write] trait for writing data.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Write
     *
     * fun foo(): io.Result<Unit> {
     *     val stderr = io.stderr()
     *     var handle = stderr.lock()
     *     handle.writeAll("hello world".encodeToByteArray()).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun lock(): StderrLock {
        // Locks this handle with static scope. This depends on the
        // implementation detail that the underlying `ReentrantMutex` is
        // static.
        return StderrLock(inner = this.inner.lock())
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "Stderr").finishNonExhaustive(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public open fun write(buf: List<UByte>): Result<Int, Error> = (this).write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = (this).writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = Write.isWriteVectored(this)

    public open fun flush(): Result<Unit, Error> = (this).flush()

    public open fun writeAll(buf: List<UByte>): Result<Unit, Error> = (this).writeAll(buf)

    public open fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit, Error> = (this).writeAllVectored(bufs)

    public open fun writeFmt(args: Arguments): Result<Unit, Error> = (this).writeFmt(args)

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.isatty.isTerminal(this)

    public companion object {
        public operator fun invoke(
            inner: ReentrantLock<RefCell<StderrRaw>>,
        ): Stderr = Stderr(
            innerCtorArg = inner,
        )
    }
}

/**
 * A locked reference to the [Stderr] handle.
 *
 * This handle implements the [Write] trait and is constructed via
 * the [Stderr.lock] method. See its documentation for more.
 *
 * ### Note: Windows Portability Considerations
 *
 * When operating in a console, the Windows implementation of this stream decodes bytes
 * as UTF-8, replacing malformed sequences with the Unicode replacement character (U+FFFD).
 *
 * In a process with a detached console, such as one using
 * `@file:windowsSubsystem= "windows"`, or in a child process spawned from such a process,
 * the contained handle will be null. In such cases, the standard library's `Read` and
 * `Write` will do nothing and silently succeed. All other I/O operations, via the
 * standard library or via raw Windows API calls, will fail.
 */
public open class StderrLock internal constructor(
    innerCtorArg: ReentrantLockGuard<RefCell<StderrRaw>>,
) : Write, IsTerminal {
    public var inner: ReentrantLockGuard<RefCell<StderrRaw>> = innerCtorArg
        internal set

    public open fun write(buf: List<UByte>): Result<Int, Error> = this.inner.borrowMut().write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = this.inner.borrowMut().writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.inner.borrowMut().isWriteVectored()

    public open fun flush(): Result<Unit, Error> = this.inner.borrowMut().flush()

    public open fun writeAll(buf: List<UByte>): Result<Unit, Error> = this.inner.borrowMut().writeAll(buf)

    public open fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit, Error> = this.inner.borrowMut().writeAllVectored(bufs)

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "StderrLock").finishNonExhaustive(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public override fun isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.isatty.isTerminal(this)

    public companion object {
        public operator fun invoke(
            inner: ReentrantLockGuard<RefCell<StderrRaw>>,
        ): StderrLock = StderrLock(
            innerCtorArg = inner,
        )
    }
}

/**
 * Constructs a new handle to the standard error of the current process.
 *
 * This handle is not buffered.
 *
 * ### Note: Windows Portability Considerations
 *
 * When operating in a console, the Windows implementation of this stream decodes bytes
 * as UTF-8, replacing malformed sequences with the Unicode replacement character (U+FFFD).
 *
 * In a process with a detached console, such as one using
 * `@file:windowsSubsystem= "windows"`, or in a child process spawned from such a process,
 * the contained handle will be null. In such cases, the standard library's `Read` and
 * `Write` will do nothing and silently succeed. All other I/O operations, via the
 * standard library or via raw Windows API calls, will fail.
 *
 * # Examples
 *
 * Using implicit synchronization:
 *
 * ```kotlin
 * import std.io.Write
 *
 * fun main(): io.Result<Unit> {
 *     io.stderr().writeAll("hello world".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Using explicit synchronization:
 *
 * ```kotlin
 * import std.io.Write
 *
 * fun main(): io.Result<Unit> {
 *     val stderr = io.stderr()
 *     var handle = stderr.lock()
 *     handle.writeAll("hello world".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun stderr(): Stderr {
    // Note that unlike `stdout()` we don't use `atExit` here to register a
    // destructor. Stderr is not buffered, so there's no need to run a
    // destructor for flushing the buffer
    val INSTANCE: ReentrantLock<RefCell<StderrRaw>>  = ReentrantLock.new(RefCell.new(stderrRaw()))
    return Stderr(inner = INSTANCE)
}

/**
 * Sets the thread-local output capture buffer and returns the old one.
 */
public fun setOutputCapture(sink: LocalStream?): LocalStream? = trySetOutputCapture(sink).expect("cannot access a Thread Local Storage value during or after destruction")

/**
 * Tries to set the thread-local output capture buffer and returns the old one.
 * This may fail once thread-local destructors are called. It's used in panic
 * handling instead of `setOutputCapture`.
 */
public fun trySetOutputCapture(sink: LocalStream?): Result<LocalStream?, AccessError> {
    if (sink == null && !OUTPUT_CAPTURE_USED.load(Ordering.Relaxed)) {
        // OUTPUT_CAPTURE is definitely None since OUTPUT_CAPTURE_USED is false.
        return Result.Ok<LocalStream?, AccessError>(null)
    }
    OUTPUT_CAPTURE_USED.store(true, Ordering.Relaxed)
    return OUTPUT_CAPTURE.tryWith({ slot -> slot.replace(sink) })
}

/**
 * Writes `args` to the capture buffer if enabled and possible, or `globalS`
 * otherwise. `label` identifies the stream in a panic message.
 *
 * This function is used to print error messages, so it takes extra
 * care to avoid causing a panic when `OUTPUT_CAPTURE` is unusable.
 * For instance, if the TLS key for output capturing is already destroyed, or
 * if the local stream is in use by another thread, it will just fall back to
 * the global stream.
 *
 * However, if the actual I/O causes an error, this function does panic.
 *
 * Writing to non-blocking stdout/stderr can cause an error, which will lead
 * this function to panic.
 */
public fun <T> printTo(args: Arguments, globalS: () -> T, label: String) where T : Write {
    if (printToBufferIfCaptureUsed(args)) {
        // Successfully wrote to capture buffer.
        return
    }
    when (val matchVal = globalS().writeFmt(args)) {
        is Result.Err<*, *> -> {
            val e = matchVal.error
            error("failed printing to $label: $e")
        }
        else -> {}
    }
}

public fun printToBufferIfCaptureUsed(args: Arguments): Boolean =
    (OUTPUT_CAPTURE_USED.load(Ordering.Relaxed) &&
        (OUTPUT_CAPTURE.tryWith({ s ->
            // Note that we completely remove a local sink to write to in case
            // our printing recursively panics/prints, so the recursive
            // panic/print goes to the global sink instead of our local sink.
            (s.also { s = null })?.let({ w ->
                w.lock().unwrapOrElse({ e -> e.intoInner() }).writeFmt(args)
                s.set(w)
            })
        })).rustEquals(Result.Ok(Unit))).toUByte()

/**
 * Used by impl Termination for Result to print error after `main` or a test
 * has returned. Should avoid panicking, although we can't help it if one of
 * the Display impls inside args decides to.
 */
public fun attemptPrintToStderr(args: Arguments) {
    if (printToBufferIfCaptureUsed(args)) {
        return
    }
    // Ignore error if the write fails, for example because stderr is already
    // closed. There is not much point panicking at this point.
    stderr().writeFmt(args)
}

/**
 * Trait to determine if a descriptor/handle refers to a terminal/tty.
 */
public interface IsTerminal {
    /**
     * Returns `true` if the descriptor/handle refers to a terminal/tty.
     *
     * On platforms where Rust does not know how to detect a terminal yet, this will return
     * `false`. This will also return `false` if an unexpected error occurred, such as from
     * passing an invalid file descriptor.
     *
     * # Platform-specific behavior
     *
     * On Windows, in addition to detecting consoles, this currently uses some heuristics to
     * detect older msys/cygwin/mingw pseudo-terminals based on device name: devices with names
     * starting with `msys -` or `cygwin -` and ending in `- pty` will be considered terminals.
     * Note that this [may change in the future][changes].
     *
     * # Examples
     *
     * An example of a type for which `IsTerminal` is implemented is [Stdin]:
     *
     * ```kotlin
     * import std.io.IsTerminal
     * import std.io.Write
     *
     * fun main(): io.Result<Unit> {
     *     val stdin = io.stdin()
     *     // Indicate that the user is prompted for input, if this is a terminal.
     *     if (stdin.isTerminal()) {
     *         print("> ")
     *         io.stdout().flush().getOrElse { return Result.failure(it) }
     *     }
     *     var name = ""
     *     stdin.readLine(name).getOrElse { return Result.failure(it) }
     *     println("Hello ${name.trimEnd()}")
     *     return Result.success(Unit)
     * }
     * ```
     *
     * The example can be run in two ways:
     *
     * - If you run this example by piping some text to it, e.g. `echo "foo" | path / to / executable`
     *   it will print: `Hello foo`.
     * - If you instead run the example interactively by running `path / to / executable` directly, it will
     *   prompt for input.
     *
     * [changes]: io#platform-specific-behavior
     */
    public fun isTerminal(): Boolean
}

public fun File.isTerminal(): Boolean = io.github.kotlinmania.rustlang.std.sys.io.isterminal.isatty.isTerminal(this)

public fun print(args: Arguments) {
    printTo(args, ::stdout, "stdout")
}

public fun eprint(args: Arguments) {
    printTo(args, ::stderr, "stderr")
}
