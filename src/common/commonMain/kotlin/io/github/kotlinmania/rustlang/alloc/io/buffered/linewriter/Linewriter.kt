// port-lint: source io/buffered/linewriter.rs
package io.github.kotlinmania.rustlang.alloc.io.buffered.linewriter

import io.github.kotlinmania.rustlang.alloc.io.buffered.IntoInnerError
import io.github.kotlinmania.rustlang.alloc.io.buffered.bufwriter.BufWriter
import io.github.kotlinmania.rustlang.alloc.io.buffered.linewritershim.LineWriterShim
import io.github.kotlinmania.rustlang.core.fmt.Arguments
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

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

/**
 * Wraps a writer and buffers output to it, flushing whenever a newline
 * (`0x0a`, `'\n'`) is detected.
 *
 * The [BufWriter] struct wraps a writer and buffers its output.
 * But it only does this batched write when it goes out of scope, or when the
 * internal buffer is full. Sometimes, you'd prefer to write each line as it's
 * completed, rather than the entire buffer at once. Enter `LineWriter`. It
 * does exactly that.
 *
 * Like [BufWriter], a `LineWriter`’s buffer will also be flushed when the
 * `LineWriter` goes out of scope or when its internal buffer is full.
 *
 * If there's still a partial line in the buffer when the `LineWriter` is
 * dropped, it will flush those contents.
 *
 * # Examples
 *
 * We can use `LineWriter` to write one line at a time, significantly
 * reducing the number of actual writes to the file.
 *
 * ```kotlin
 * import std.fs.File
 * import std.io.LineWriter
 * import std.io.prelude.*
 *
 * fun main(): Result<Unit> {
 *     val roadNotTaken = "I shall be telling this with a sigh\nSomewhere ages and ages hence:\nTwo roads diverged in a wood, and I -\nI took the one less traveled by,\nAnd that has made all the difference.".encodeToByteArray()
 *     val file = File.create("poem.txt").getOrElse { return Result.failure(it) }
 *     var file1 = LineWriter.new(file)
 *     file1.writeAll("I shall be telling this with a sigh".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     // No bytes are written until a newline is encountered (or
 *     // the internal buffer is filled).
 *     check(fs.readToString("poem.txt").getOrElse { return Result.failure(it) } == "")
 *     file1.writeAll("\n".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     check(fs.readToString("poem.txt").getOrElse { return Result.failure(it) } == "I shall be telling this with a sigh\n")
 *     // Write the rest of the poem.
 *     file1.writeAll("Somewhere ages and ages hence:\nTwo roads diverged in a wood, and I -\nI took the one less traveled by,\nAnd that has made all the difference.".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     // The last line of the poem doesn't end in a newline, so
 *     // we have to flush or drop the `LineWriter` to finish
 *     // writing.
 *     file1.flush().getOrElse { return Result.failure(it) }
 *     // Confirm the whole poem was written.
 *     check(fs.read("poem.txt").getOrElse { return Result.failure(it) } == roadNotTaken)
 *     return Result.success(Unit)
 * }
 * ```
 */
public open class LineWriter<W : Write<W>> internal constructor(
    innerCtorArg: BufWriter<W>,
) : Write<LineWriter<W>> {
    public var inner: BufWriter<W> = innerCtorArg
        internal set

    /**
     * Gets a mutable reference to the underlying writer.
     *
     * Caution must be taken when calling methods on the mutable reference
     * returned as extra writes could corrupt the output stream.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.LineWriter
     *
     * fun main(): Result<Unit> {
     *     val file = File.create("poem.txt").getOrElse { return Result.failure(it) }
     *     var file1 = LineWriter.new(file)
     *     // we can use reference just like file
     *     val reference = file1.getMut()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun getMut(): W = this.inner.getMut()

    /**
     * Unwraps this `LineWriter`, returning the underlying writer.
     *
     * The internal buffer is written out before returning the writer.
     *
     * # Errors
     *
     * An [Err] will be returned if an error occurs while flushing the buffer.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.LineWriter
     *
     * fun main(): Result<Unit> {
     *     val file = File.create("poem.txt").getOrElse { return Result.failure(it) }
     *     val writer: LineWriter<File> = LineWriter.new(file)
     *     val file1: File = writer.intoInner().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun intoInner(): Result<W, IntoInnerError<LineWriter<W>>> = this.inner.intoInner().mapErr({ err -> err.newWrapped({ inner -> LineWriter(inner = inner) }) })

    /**
     * Gets a reference to the underlying writer.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.LineWriter
     *
     * fun main(): Result<Unit> {
     *     val file = File.create("poem.txt").getOrElse { return Result.failure(it) }
     *     val file1 = LineWriter.new(file)
     *     val reference = file1.getRef()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun getRef(): W = this.inner.getRef()

    public override fun write(buf: List<UByte>): Result<Int> = LineWriterShim.new(this.inner).write(buf)

    public override fun flush(): Result<Unit> = this.inner.flush()

    public override fun writeVectored(bufs: List<IoSlice>): Result<Int> = LineWriterShim.new(this.inner).writeVectored(bufs)

    public override fun isWriteVectored(): Boolean = this.inner.isWriteVectored()

    public override fun writeAll(buf: List<UByte>): Result<Unit> = LineWriterShim.new(this.inner).writeAll(buf)

    public override fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit> = LineWriterShim.new(this.inner).writeAllVectored(bufs)

    public override fun writeFmt(fmt: Arguments): Result<Unit> = LineWriterShim.new(this.inner).writeFmt(fmt)

    public open fun fmt(fmt: StringBuilder): RustResult<Unit, Error> = run { DebugStruct(fmt, "LineWriter").field("writer", this.getRef()).field("buffer", "${this.inner.buffer().len()}/${this.inner.capacity()}").finishNonExhaustive(); RustResult.Ok<Unit, Error>(Unit) }

    public companion object {
        public operator fun <W : Write<W>> invoke(
            inner: BufWriter<W>,
        ): LineWriter<W> = LineWriter<W>(
            innerCtorArg = inner,
        )

        /**
         * Creates a new `LineWriter`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.File
         * import std.io.LineWriter
         *
         * fun main(): Result<Unit> {
         *     val file = File.create("poem.txt").getOrElse { return Result.failure(it) }
         *     val file1 = LineWriter.new(file)
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <W : Write<W>> new(inner: W): LineWriter<W> {
            // Lines typically aren't that long, don't use a giant buffer
            return LineWriter.withCapacity(1024, inner)
        }

        /**
         * Creates a new `LineWriter` with at least the specified capacity for the
         * internal buffer.
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.File
         * import std.io.LineWriter
         *
         * fun main(): Result<Unit> {
         *     val file = File.create("poem.txt").getOrElse { return Result.failure(it) }
         *     val file1 = LineWriter.withCapacity(100, file)
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <W : Write<W>> withCapacity(capacity: Int, inner: W): LineWriter<W> = LineWriter(inner = BufWriter.withCapacity(capacity, inner))
    }
}
