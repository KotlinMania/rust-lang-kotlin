// port-lint: source io/write.rs
package io.github.kotlinmania.rustlang.core.io.write

import io.github.kotlinmania.rustlang.core.fmt.Arguments
import io.github.kotlinmania.rustlang.core.fmt.write
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

/**
 * A trait for objects which are byte-oriented sinks.
 *
 * Implementors of the `Write` trait are sometimes called `writers`.
 *
 * Writers are defined by two required methods, [write] and [flush]:
 *
 * * The [write] method will attempt to write some data into the object,
 *   returning how many bytes were successfully written.
 *
 * * The [flush] method is useful for adapters and explicit buffers
 *   themselves for ensuring that all buffered data has been pushed out to the
 *   true sink'.
 *
 * Writers are intended to be composable with one another. Many implementors
 * throughout [std.io] take and provide types which implement the `Write`
 * trait.
 *
 * [write]: Write.write
 * [flush]: Write.flush
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs.File
 * import std.io.prelude.*
 *
 * fun main(): Result<Unit> {
 *     val data = "some bytes".encodeToByteArray()
 *     var pos = 0
 *     var buffer = File.create("foo.txt").getOrElse { return Result.failure(it) }
 *     while (pos < data.size) {
 *         val bytesWritten = buffer.write(((data).sliceArray(pos until data.size))).getOrElse { return Result.failure(it) }
 *         pos += bytesWritten
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * The trait also provides convenience methods like [writeAll], which calls
 * `write` in a loop until its entire input has been written.
 *
 * [writeAll]: Write.writeAll
 */
public interface Write<T> {
    /**
     * Writes a buffer into this writer, returning how many bytes were written.
     *
     * This function will attempt to write the entire contents of `buf`, but
     * the entire write might not succeed, or the write may also generate an
     * error. Typically, a call to `write` represents one attempt to write to
     * any wrapped object.
     *
     * Calls to `write` are not guaranteed to block waiting for data to be
     * written, and a write which would otherwise block can be indicated through
     * an [Err] variant.
     *
     * If this method consumed `n> 0` bytes of `buf` it must return [Ok].
     * If the return value is `Ok(n)` then `n` must satisfy `n <= buf.len()`.
     * A return value of `Ok(0)` typically means that the underlying object is
     * no longer able to accept bytes and will likely not be able to in the
     * future as well, or that the buffer provided is empty.
     *
     * # Errors
     *
     * Each call to `write` may generate an I/O error indicating that the
     * operation could not be completed. If an error is returned then no bytes
     * in the buffer were written to this writer.
     *
     * It is **not** considered an error if the entire buffer could not be
     * written to this writer.
     *
     * An error of the [ErrorKind.Interrupted] kind is non-fatal and the
     * write operation should be retried if there is nothing else to do.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.prelude.*
     *
     * fun main(): Result<Unit> {
     *     var buffer = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     // Writes some prefix of the byte string, not necessarily all of it.
     *     buffer.write("some bytes".encodeToByteArray()).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     *
     * [Ok]: Ok
     */
    public fun write(buf: List<UByte>): Result<Int>

    /**
     * Like [write], except that it writes from a slice of buffers.
     *
     * Data is copied from each buffer in order, with the final buffer
     * read from possibly being only partially consumed. This method must
     * behave as a call to [write] with the buffers concatenated would.
     *
     * The default implementation calls [write] with either the first nonempty
     * buffer provided, or an empty one if none exists.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.IoSlice
     * import std.io.prelude.*
     *
     * fun main(): Result<Unit> {
     *     val data1 = List(8) { 1 }
     *     val data2 = List(8) { 15 }
     *     val ioSlice1 = IoSlice.new(data1)
     *     val ioSlice2 = IoSlice.new(data2)
     *     var buffer = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     // Writes some prefix of the byte string, not necessarily all of it.
     *     buffer.writeVectored(listOf(ioSlice1, ioSlice2)).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     *
     * [write]: Write.write
     */
    public fun writeVectored(bufs: List<IoSlice>): Result<Int> = defaultWriteVectored({ b -> this.write(b) }, bufs)

    /**
     * Determines if this `Write`r has an efficient [writeVectored]
     * implementation.
     *
     * If a `Write`r does not override the default [writeVectored]
     * implementation, code using it may want to avoid the method all together
     * and coalesce writes into a single buffer for higher performance.
     *
     * The default implementation returns `false`.
     *
     * [writeVectored]: Write.writeVectored
     */
    public fun isWriteVectored(): Boolean = false

    /**
     * Flushes this output stream, ensuring that all intermediately buffered
     * contents reach their destination.
     *
     * # Errors
     *
     * It is considered an error if not all bytes could be written due to
     * I/O errors or EOF being reached.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.BufWriter
     * import std.io.prelude.*
     *
     * fun main(): Result<Unit> {
     *     var buffer = BufWriter.new(File.create("foo.txt").getOrElse { return Result.failure(it) })
     *     buffer.writeAll("some bytes".encodeToByteArray()).getOrElse { return Result.failure(it) }
     *     buffer.flush().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun flush(): Result<Unit>

    /**
     * Attempts to write an entire buffer into this writer.
     *
     * This method will continuously call [write] until there is no more data
     * to be written or an error of non-[ErrorKind.Interrupted] kind is
     * returned. This method will not return until the entire buffer has been
     * successfully written or such an error occurs. The first error that is
     * not of [ErrorKind.Interrupted] kind generated from this method will be
     * returned.
     *
     * If the buffer contains no data, this will never call [write].
     *
     * # Errors
     *
     * This function will return the first error of
     * non-[ErrorKind.Interrupted] kind that [write] returns.
     *
     * [write]: Write.write
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.prelude.*
     *
     * fun main(): Result<Unit> {
     *     var buffer = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     buffer.writeAll("some bytes".encodeToByteArray()).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun writeAll(buf: List<UByte>): Result<Unit> {
        var buf1 = buf
        while (!buf1.isEmpty()) {
            run {
                val matchVal = this.write(buf1)
                when {
                    matchVal is RustResult.Ok<*, *> && matchVal.okValue() == 0 -> {
                        val okValueVal = matchVal.okValue()
                        return RustResult.Err(Error.WRITE_ALL_EOF)
                    }
                    matchVal is RustResult.Ok<*, *> -> {
                        val n = matchVal.okValue()
                        buf1 = ((buf1).subList(n, (buf1).size))
                    }
                    matchVal is RustResult.Err<*, *> && matchVal.errValue().isInterrupted() -> {
                        val e = matchVal.errValue()
                    }
                    else -> {
                        val e1 = (matchVal as RustResult.Err<*, *>).errValue()
                        return RustResult.Err(e1)
                    }
                }
            }
        }
        return RustResult.Ok(Unit)
    }

    /**
     * Attempts to write multiple buffers into this writer.
     *
     * This method will continuously call [writeVectored] until there is no
     * more data to be written or an error of non-[ErrorKind.Interrupted]
     * kind is returned. This method will not return until all buffers have
     * been successfully written or such an error occurs. The first error that
     * is not of [ErrorKind.Interrupted] kind generated from this method
     * will be returned.
     *
     * If the buffer contains no data, this will never call [writeVectored].
     *
     * # Notes
     *
     * Unlike [writeVectored], this takes a *mutable* reference to
     * a slice of [IoSlice]s, not an immutable one. That's because we need to
     * modify the slice to keep track of the bytes already written.
     *
     * Once this function returns, the contents of `bufs` are unspecified, as
     * this depends on how many calls to [writeVectored] were necessary. It is
     * best to understand this function as taking ownership of `bufs` and to
     * not use `bufs` afterwards. The underlying buffers, to which the
     * [IoSlice]s point (but not the [IoSlice]s themselves), are unchanged and
     * can be reused.
     *
     * [writeVectored]: Write.writeVectored
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.IoSlice
     * import std.io.Write
     *
     * @feature(writeAllVectored)
     * public fun doctestEntry(): Result<Unit> {
     *     var writer = mutableListOf()
     *     val bufs = listOf(IoSlice.new(listOf(1)), IoSlice.new(listOf(2, 3)), IoSlice.new(listOf(4, 5, 6)))
     *     writer.writeAllVectored(bufs).getOrElse { return Result.failure(it) }
     *     // Note: the contents of `bufs` is now undefined, see the Notes section.
     *     check(writer == listOf(1, 2, 3, 4, 5, 6))
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit> {
        // Guarantee that bufs is empty if it contains no data,
        // to avoid calling writeVectored if there is no data to be written.
        IoSlice.advanceSlices(bufs, 0)
        while (!bufs.isEmpty()) {
            run {
                val matchVal = this.writeVectored(bufs)
                when {
                    matchVal is RustResult.Ok<*, *> && matchVal.okValue() == 0 -> {
                        val okValueVal = matchVal.okValue()
                        return RustResult.Err(Error.WRITE_ALL_EOF)
                    }
                    matchVal is RustResult.Ok<*, *> -> {
                        val n = matchVal.okValue()
                        IoSlice.advanceSlices(bufs, n)
                    }
                    matchVal is RustResult.Err<*, *> && matchVal.errValue().isInterrupted() -> {
                        val e = matchVal.errValue()
                    }
                    else -> {
                        val e1 = (matchVal as RustResult.Err<*, *>).errValue()
                        return RustResult.Err(e1)
                    }
                }
            }
        }
        return RustResult.Ok(Unit)
    }

    /**
     * Writes a formatted string into this writer, returning any error
     * encountered.
     *
     * This method is primarily used to interface with the
     * [formatArgs] macro, and it is rare that this should
     * explicitly be called. The [write] macro should be favored to
     * invoke this method instead.
     *
     * This function internally uses the [writeAll] method on
     * this trait and hence will continuously write data so long as no errors
     * are received. This also means that partial writes are not indicated in
     * this signature.
     *
     * [writeAll]: Write.writeAll
     *
     * # Errors
     *
     * This function will return any I/O error reported while formatting.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.prelude.*
     *
     * fun main(): Result<Unit> {
     *     var buffer = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     // this call
     *     buffer.append("${2}")
     *     // turns into this:
     *     buffer.writeFmt("${2}").getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun writeFmt(args: Arguments): Result<Unit> =
        when (val matchVal = args.asStaticallyKnownStr()) {
            null -> {
                defaultWriteFmt(this, args)
            }
            else -> {
                val s = matchVal
                this.writeAll(s.encodeToByteArray().map { it.toUByte() })
            }
        }

    /**
     * Creates a "by reference" adapter for this instance of `Write`.
     *
     * The returned adapter also implements `Write` and will simply borrow this
     * current writer.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.Write
     *
     * fun main(): Result<Unit> {
     *     var buffer = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     val reference = buffer.byRef()
     *     // we can use reference just like our original buffer
     *     reference.writeAll("some bytes".encodeToByteArray()).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public fun byRef(): T = this
}

/**
 * Default implementation of [Write.writeVectored], which is currently used
 * in `libstd` for file system implementations of similar methods.
 */
public fun defaultWriteVectored(write: (List<UByte>) -> Result<Int>, bufs: List<IoSlice>): Result<Int> {
    val buf = ((bufs.indexOf({ b -> !b.isEmpty() }).takeIf { it >= 0 })?.let({ b -> b }) ?: listOf())
    return write(buf)
}

public fun <W : Write<W>> defaultWriteFmt(thisRef: W, args: Arguments): Result<Unit> {
    // Create a shim which translates a `Write` to a `fmt.Write` and saves off
    // I/O errors, instead of discarding them.
    open class Adapter<T>(
        public var inner: T,
        public var error: Result<Unit>,
    )
    fun <T : Write<T>> Adapter<T>.writeStr(s: String): RustResult<Unit, Error> =
        run {
            val matchVal = this.inner.writeAll(s.encodeToByteArray().map { it.toUByte() })
            when (matchVal) { is RustResult.Ok<*, *> -> {
                RustResult.Ok<Unit, Error>(Unit)
            }; is RustResult.Err<*, *> -> {
                val e = matchVal.errValue()
                this.error = RustResult.Err<Unit, Error>(e)
                RustResult.Err<Unit, Error>(Error())
            } }
        }
    val output = Adapter(inner = thisRef, error = RustResult.Ok(Unit))
    return run {
        val matchVal = write(output, args)
        when (matchVal) { is RustResult.Ok<*, *> -> {
            RustResult.Ok(Unit)
        }; is RustResult.Err<*, *> -> {
            // Check whether the error came from the underlying `Write`.
            if (output.error.isErr()) {
                output.error
            } else {
                // This shouldn't happen: the underlying stream did not error,
                // but somehow the formatter still errored?
                error("a formatting trait implementation returned an error when the underlying stream did not")
            }
        } }
    }
}
