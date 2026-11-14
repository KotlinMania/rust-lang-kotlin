// port-lint: source io/pipe.rs
package io.github.kotlinmania.rustlang.std.io.pipe

import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.pipe.unix.Pipe

/**
 * Creates an anonymous pipe.
 *
 * # Behavior
 *
 * A pipe is a one-way data channel provided by the OS, which works across processes. A pipe is
 * typically used to communicate between two or more separate processes, as there are better,
 * faster ways to communicate within a single process.
 *
 * In particular:
 *
 * * A read on a [PipeReader] blocks until the pipe is non-empty.
 * * A write on a [PipeWriter] blocks when the pipe is full.
 * * When all copies of a [PipeWriter] are closed, a read on the corresponding [PipeReader]
 *   returns EOF.
 * * [PipeWriter] can be shared, and multiple processes or threads can write to it at once, but
 *   writes (above a target-specific threshold) may have their data interleaved.
 * * [PipeReader] can be shared, and multiple processes or threads can read it at once. Any
 *   given byte will only get consumed by one reader. There are no guarantees about data
 *   interleaving.
 * * Portable applications cannot assume any atomicity of messages larger than a single byte.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `pipe` function on Unix and the
 * `CreatePipe` function on Windows.
 *
 * Note that this [may change in the future][changes].
 *
 * # Capacity
 *
 * Pipe capacity is platform dependent. To quote the Linux [man page]:
 *
 * > Different implementations have different limits for the pipe capacity. Applications should
 * > not rely on a particular capacity: an application should be designed so that a reading process
 * > consumes data as soon as it is available, so that a writing process does not remain blocked.
 *
 * # Example
 *
 * ```kotlin
 * import std.io.Read
 * import std.io.Write
 * import std.io.pipe
 * import std.process.Command
 *
 * var (pingReader, pingWriter) = pipe().getOrElse { return Result.failure(it) }
 * var (pongReader, pongWriter) = pipe().getOrElse { return Result.failure(it) }
 * // Spawn a child process that echoes its input.
 * var echoCommand = Command.new("cat")
 * echoCommand.stdin(pingReader)
 * echoCommand.stdout(pongWriter)
 * var echoChild = echoCommand.spawn().getOrElse { return Result.failure(it) }
 * // Send input to the child process. Note that because we're writing all the input before we
 * // read any output, this could deadlock if the child's input and output pipe buffers both
 * // filled up. Those buffers are usually at least a few KB, so "hello" is fine, but for longer
 * // inputs we'd need to read and write at the same time, e.g. using threads.
 * pingWriter.writeAll("hello".encodeToByteArray()).getOrElse { return Result.failure(it) }
 * // `cat` exits when it reads EOF from stdin, but that can't happen while any ping writer
 * // remains open. We need to drop our ping writer, or readToString will deadlock below.
 * drop(pingWriter)
 * // The pong reader can't report EOF while any pong writer remains open. Our Command object is
 * // holding a pong writer, and again readToString will deadlock if we don't drop it.
 * drop(echoCommand)
 * var buf = ""
 * // Block until `cat` closes its stdout (a pong writer).
 * pongReader.readToString(buf).getOrElse { return Result.failure(it) }
 * check(buf == "hello")
 * // At this point we know `cat` has exited, but we still need to wait to clean up the "zombie".
 * echoChild.wait().getOrElse { return Result.failure(it) }
 * ```
 * [changes]: io#platform-specific-behavior
 * [man page]: https://man7.org/linux/man-pages/man7/pipe.7.html
 */
public fun pipe(): Result<Pair<PipeReader, PipeWriter>> = pipe().map({ (reader, writer) -> Pair(PipeReader(reader), PipeWriter(writer)) })

/**
 * Read end of an anonymous pipe.
 */
public open class PipeReader(
    internal var value: Pipe,
) : FromInner<Pipe, PipeReader>, IntoInner<Pipe>, Read {
    public var field0: Pipe
        get() = value
        set(v) {
            value = v
        }

    public override fun intoInner(): Pipe = this.value

    /**
     * Creates a new [PipeReader] instance that shares the same underlying file description.
     *
     * # Examples
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import std.io.Write
     * import std.io.pipe
     * import std.process.Command
     *
     * public fun doctestEntry(): Result<Unit> {
     *     val NUM_SLOT: UByte = 2u
     *     val NUM_PROC: UByte = 5u
     *     val OUTPUT: String = "work.txt"
     *     var jobs = mutableListOf()
     *     var (reader, writer) = pipe().getOrElse { return Result.failure(it) }
     *     // Write NUM_SLOT characters the pipe.
     *     writer.writeAll(UByteArray(NUM_SLOT.toInt()) { 124u }).getOrElse { return Result.failure(it) }
     *     // Spawn several processes that read a character from the pipe, do some work, then
     *     // write back to the pipe. When the pipe is empty, the processes block, so only
     *     // NUM_SLOT processes can be working at any given time.
     *     for (unused in 0 until NUM_PROC) {
     *         jobs.add(Command.new("bash").args(listOf("-c", "read -n 1\necho -n 'x' >> '$output'\necho -n '|'")).stdin(reader.tryClone().getOrElse { return Result.failure(it) }).stdout(writer.tryClone().getOrElse { return Result.failure(it) }).spawn().getOrElse { return Result.failure(it) })
     *     }
     *     // Wait for all jobs to finish.
     *     for (job in jobs) {
     *         job.wait().getOrElse { return Result.failure(it) }
     *     }
     *     // Check our work and clean up.
     *     val xs = readToString(OUTPUT).getOrElse { return Result.failure(it) }
     *     removeFile(OUTPUT).getOrElse { return Result.failure(it) }
     *     check(xs == "x".repeat(NUM_PROC.into()))
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun tryClone(): Result<PipeReader> = this.value.tryClone().map({ PipeReader(it) })

    public open fun read(buf: MutableList<UByte>): Result<Int> = this.value.read(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = this.value.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.value.isReadVectored()

    public open fun readToEnd(buf: List<UByte>): Result<Int> = this.value.readToEnd(buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit> = this.value.readBuf(buf)

    public open fun readVectoredRead(bufs: MutableList<IoSliceMut>): Result<Int> = this.value.readVectored(bufs)

    public open fun isReadVectoredRead(): Boolean = this.value.isReadVectored()

    public open fun readToEndRead(buf: List<UByte>): Result<Int> = this.value.readToEnd(buf)

    public open fun readBufRead(buf: BorrowedCursor<UByte>): Result<Unit> = this.value.readBuf(buf)

    public companion object {
        public fun fromInner(inner: Pipe): PipeReader = PipeReader(inner)
    }
}

/**
 * Write end of an anonymous pipe.
 */
public open class PipeWriter(
    internal var value: Pipe,
) : FromInner<Pipe, PipeWriter>, IntoInner<Pipe>, Write {
    public var field0: Pipe
        get() = value
        set(v) {
            value = v
        }

    public override fun intoInner(): Pipe = this.value

    /**
     * Creates a new [PipeWriter] instance that shares the same underlying file description.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Read
     * import std.io.pipe
     * import std.process.Command
     *
     * var (reader, writer) = pipe().getOrElse { return Result.failure(it) }
     * // Spawn a process that writes to stdout and stderr.
     * var peer = Command.new("bash").args(listOf("-c", "echo -n foo\necho -n bar >&2")).stdout(writer.tryClone().getOrElse { return Result.failure(it) }).stderr(writer).spawn().getOrElse { return Result.failure(it) }
     * // Read and check the result.
     * var msg = ""
     * reader.readToString(msg).getOrElse { return Result.failure(it) }
     * check(msg == "foobar")
     * peer.wait().getOrElse { return Result.failure(it) }
     * ```
     */
    public open fun tryClone(): Result<PipeWriter> = this.value.tryClone().map({ PipeWriter(it) })

    public open fun write(buf: List<UByte>): Result<Int> = this.value.write(buf)

    public open fun flush(): Result<Unit> = Result.Ok(Unit)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = this.value.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public open fun flushWrite(): Result<Unit> = Result.Ok(Unit)

    public open fun writeVectoredWrite(bufs: List<IoSlice>): Result<Int> = this.value.writeVectored(bufs)

    public open fun isWriteVectoredWrite(): Boolean = this.value.isWriteVectored()

    public companion object {
        public fun fromInner(inner: Pipe): PipeWriter = PipeWriter(inner)
    }
}
