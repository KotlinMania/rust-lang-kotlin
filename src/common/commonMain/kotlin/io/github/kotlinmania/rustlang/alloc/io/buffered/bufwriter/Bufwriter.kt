// port-lint: source io/buffered/bufwriter.rs
package io.github.kotlinmania.rustlang.alloc.io.buffered.bufwriter

import io.github.kotlinmania.rustlang.alloc.io.buffered.IntoInnerError
import io.github.kotlinmania.rustlang.alloc.io.constError
import io.github.kotlinmania.rustlang.alloc.io.read.DEFAULT_BUF_SIZE
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.core.cmp.min
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.seek.Seek
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.take
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.copyNonoverlapping
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.ptr.read
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

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

// Note(#74481): Hard-links required to link from `alloc` to `std`

/**
 * Wraps a writer and buffers its output.
 *
 * It can be excessively inefficient to work directly with something that
 * implements [Write]. For example, every call to
 * [`write`][TcpStream.write] on [TcpStream] results in a system call. A
 * `BufWriter<W>` keeps an in-memory buffer of data and writes it to an underlying
 * writer in large, infrequent batches.
 *
 * `BufWriter<W>` can improve the speed of programs that make *small* and
 * *repeated* write calls to the same file or network socket. It does not
 * help when writing very large amounts at once, or writing just one or a few
 * times. It also provides no advantage when writing to a destination that is
 * in memory, like a <code>[Vec]\<u8></code>.
 *
 * It is critical to call [flush] before `BufWriter<W>` is dropped. Though
 * dropping will attempt to flush the contents of the buffer, any errors
 * that happen in the process of dropping will be ignored. Calling [flush]
 * ensures that the buffer is empty and thus dropping will not even attempt
 * file operations.
 *
 * # Examples
 *
 * Let's write the numbers one through ten to a [TcpStream]:
 *
 * ```kotlin
 * import std.io.prelude.*
 * import std.net.TcpStream
 *
 * var stream = TcpStream.connect("127.0.0.1:34254").unwrap()
 * for (i in 0 until 10) {
 *     stream.write(listOf(i + 1)).unwrap()
 * }
 * ```
 *
 * Because we're not buffering, we write each one in turn, incurring the
 * overhead of a system call per byte written. We can fix this with a
 * `BufWriter<W>`:
 *
 * ```kotlin
 * import std.io.BufWriter
 * import std.io.prelude.*
 * import std.net.TcpStream
 *
 * var stream = BufWriter.new(TcpStream.connect("127.0.0.1:34254").unwrap())
 * for (i in 0 until 10) {
 *     stream.write(listOf(i + 1)).unwrap()
 * }
 * stream.flush().unwrap()
 * ```
 *
 * By wrapping the stream with a `BufWriter<W>`, these ten writes are all grouped
 * together by the buffer and will all be written out in one system call when
 * the `stream` is flushed.
 *
 * [TcpStream.write]: ../../std/net/struct.TcpStream.html#method.write
 * [TcpStream]: ../../std/net/struct.TcpStream.html
 * [flush]: BufWriter.flush
 */
public open class BufWriter<W : Write>(
    // The buffer. Avoid using this like a normal `List` in common code paths.
    // That is, don't use `buf.push`, `buf.extendFromSlice`, or any other
    // methods that require bounds checking or the like. This makes an enormous
    // difference to performance (we may want to stop using a `List` entirely).
    public var buf: List<UByte>,
    // #30888: If the inner writer panics in a call to write, we don't want to
    // write the buffered data a second time in BufWriter's destructor. This
    // flag tells the Drop impl if it should skip the flush.
    public var panicked: Boolean,
    public var inner: W,
) : Write, Seek {
    /**
     * Unwraps this `BufWriter<W>`, returning the underlying writer.
     *
     * The buffer is written out before returning the writer.
     *
     * # Errors
     *
     * An [Err] will be returned if an error occurs while flushing the buffer.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.BufWriter
     * import std.net.TcpStream
     *
     * var buffer = BufWriter.new(TcpStream.connect("127.0.0.1:34254").unwrap())
     * // unwrap the TcpStream and flush the buffer
     * val stream = buffer.intoInner().unwrap()
     * ```
     */
    public open fun intoInner(): RustResult<W, IntoInnerError<BufWriter<W>>> =
        run {
            val matchVal = this.flushBuf()
            when (matchVal) { is RustResult.Ok<*, *> -> {
                RustResult.Ok<W, IntoInnerError<BufWriter<W>>>(this.intoParts().first)
            }; is RustResult.Err<*, *> -> {
                val e = matchVal.errValue()
                RustResult.Err<W, IntoInnerError<BufWriter<W>>>(IntoInnerError.new(this, e))
            } }
        }

    /**
     * Disassembles this `BufWriter<W>`, returning the underlying writer, and any buffered but
     * unwritten data.
     *
     * If the underlying writer panicked, it is not known what portion of the data was written.
     * In this case, we return `WriterPanicked` for the buffered data (from which the buffer
     * contents can still be recovered).
     *
     * `intoParts` makes no attempt to flush data and cannot fail.
     *
     * # Examples
     *
     * ```kotlin
     * @file:OptIn(ExperimentalUnsignedTypes.cls)
     *
     * import std.io.BufWriter
     * import std.io.Write
     *
     * public fun doctestEntry() {
     *     var buffer = UByteArray(10) { 0u.toUByte() }
     *     var stream = BufWriter.new(buffer.asMut())
     *     stream.append("too much data").unwrap()
     *     stream.flush().expectErr("it doesn't fit")
     *     val (recoveredWriter, bufferedData) = stream.intoParts()
     *     check(recoveredWriter.len() == 0)
     *     check(bufferedData.unwrap() == "ata".encodeToByteArray())
     * }
     * ```
     */
    public open fun intoParts(): Pair<W, RustResult<List<UByte>, WriterPanicked>> {
        val thisRef = this
        val buf = take(thisRef.buf)
        val buf1 =
            if (!thisRef.panicked) {
                RustResult.Ok(buf)
            } else {
                RustResult.Err(WriterPanicked(buf = buf))
            }
        // SAFETY: double-drops are prevented by putting `this` in a ManuallyDrop that is never dropped
        val inner = read(thisRef.inner)
        return Pair(inner, buf1)
    }

    /**
     * Send data in our local buffer into the inner writer, looping as
     * necessary until either it's all been sent or an error occurs.
     *
     * Because all the data in the buffer has been reported to our owner as
     * "successfully written" (by returning nonzero success values from
     * `write`), any 0-length writes from `inner` must be reported as i/o
     * errors from this method.
     */
    public open fun flushBuf(): Result<Unit> {
        // SAFETY: `<BufWriter as BufferedWriterSpec>.copyFrom` assumes that
        // this will not de-initialize any elements of `self.buf`s spare
        // capacity.

        /**
         * Helper struct to ensure the buffer is updated after all the writes
         * are complete. It tracks the number of written bytes and drains them
         * all from the front of the buffer when dropped.
         */
        open class BufGuard(
            public var buffer: MutableList<UByte>,
            public var written: Int,
        ) {
            public open fun new(buffer: List<UByte>): BufGuard = BufGuard(buffer = buffer, written = 0)

            /**
             * The unwritten part of the buffer
             */
            public open fun remaining(): List<UByte> = ((this.buffer).subList(this.written, (this.buffer).size))

            /**
             * Flag some bytes as removed from the front of the buffer
             */
            public open fun consume(amt: Int) {
                this.written += amt
            }

            /**
             * true if all of the bytes have been written
             */
            public open fun done(): Boolean = this.written >= this.buffer.size

            public open fun drop() {
                if (this.written > 0) {
                    // Like `self.buffer.drain(.. self.written)` but more obviously
                    // preserving the spare capacity; see note above.
                    val newLen = this.buffer.size - this.written
                    // SAFETY: Assumes `List.asMutSlice` will not
                    // de-initialize any elements of `self.buf`s spare capacity,
                    // and that `<[UByte]>.copyWithin` will not do so either.
                    this.buffer.asMutSlice().copyWithin(this.written until Int.MAX_VALUE, 0)
                    // SAFETY: Assumes `List.truncate` will not de-initialize
                    // any elements of `self.buf`s spare capacity,
                    this.buffer.truncate(newLen)
                }
            }
        }
        val guard = BufGuard.new(this.buf)
        while (!guard.done()) {
            this.panicked = true
            val r = this.inner.write(guard.remaining())
            this.panicked = false
            when {
                r is RustResult.Ok<*, *> && r.okValue() == 0 -> {
                    val okValueVal = r.okValue()
                    return RustResult.Err(constError(ErrorKind.WriteZero, "failed to write the buffered data"))
                }
                r is RustResult.Ok<*, *> -> {
                    val n = r.okValue()
                    guard.consume(n)
                }
                r is RustResult.Err<*, *> && r.errValue().isInterrupted() -> {
                    val e = r.errValue()
                }
                else -> {
                    val e1 = (r as RustResult.Err<*, *>).errValue()
                    return RustResult.Err(e1)
                }
            }
        }
        return RustResult.Ok(Unit)
    }

    /**
     * Buffer some data without flushing it, regardless of the size of the
     * data. Writes as much as possible without exceeding capacity. Returns
     * the number of bytes written.
     */
    public open fun writeToBuf(buf: List<UByte>): Int {
        val available = this.spareCapacity()
        val amtToBuffer = available.min(buf.size)
        // SAFETY: `amtToBuffer` is <= buffer's spare capacity by construction.
        run {
            this.writeToBufferUnchecked(((buf).subList(0, amtToBuffer)))
        }
        return amtToBuffer
    }

    /**
     * Gets a reference to the underlying writer.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.BufWriter
     * import std.net.TcpStream
     *
     * var buffer = BufWriter.new(TcpStream.connect("127.0.0.1:34254").unwrap())
     * // we can use reference just like buffer
     * val reference = buffer.getRef()
     * ```
     */
    public open fun getRef(): W = this.inner

    /**
     * Gets a mutable reference to the underlying writer.
     *
     * It is inadvisable to directly write to the underlying writer.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.BufWriter
     * import std.net.TcpStream
     *
     * var buffer = BufWriter.new(TcpStream.connect("127.0.0.1:34254").unwrap())
     * // we can use reference just like buffer
     * val reference = buffer.getMut()
     * ```
     */
    public open fun getMut(): kotlin.reflect.KMutableProperty0<W> {
        return this::inner
    }

    /**
     * Returns a reference to the internally buffered data.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.BufWriter
     * import std.net.TcpStream
     *
     * val bufWriter = BufWriter.new(TcpStream.connect("127.0.0.1:34254").unwrap())
     * // See how many bytes are currently buffered
     * val bytesBuffered = bufWriter.buffer().len()
     * ```
     */
    public open fun buffer(): List<UByte> = this.buf

    /**
     * Returns a mutable reference to the internal buffer.
     *
     * This can be used to write data directly into the buffer without triggering writers
     * to the underlying writer.
     *
     * That the buffer is a `List` is an implementation detail.
     * Callers should not modify the capacity as there currently is no public API to do so
     * and thus any capacity changes would be unexpected by the user.
     */
    public open fun bufferMut(): kotlin.reflect.KMutableProperty0<List<UByte>> {
        return this::buf
    }

    /**
     * Returns the number of bytes the internal buffer can hold without flushing.
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.BufWriter
     * import std.net.TcpStream
     *
     * val bufWriter = BufWriter.new(TcpStream.connect("127.0.0.1:34254").unwrap())
     * // Check the capacity of the inner buffer
     * val capacity = bufWriter.capacity()
     * // Calculate how many bytes can be written without flushing
     * val withoutFlush = capacity - bufWriter.buffer().len()
     * ```
     */
    public open fun capacity(): Int = this.buf.capacity()

    // Ensure this function does not get inlined into `write`, so that it
    // remains inlineable and its common path remains as short as possible.
    // If this function ends up being called frequently relative to `write`,
    // it's likely a sign that the client is using an improperly sized buffer
    // or their write patterns are somewhat pathological.
    public open fun writeCold(buf: List<UByte>): Result<Int> {
        if (buf.size > this.spareCapacity()) {
            when (val tryResult = this.flushBuf()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
        }
        // Why not len > capacity? To avoid a needless trip through the buffer when the input
        // exactly fills it. We'd just need to flush it to the underlying writer anyway.
        return if (buf.size >= this.buf.capacity()) {
            this.panicked = true
            val r = this.getMut().write(buf)
            this.panicked = false
            r
        } else {
            // Write to the buffer. In this case, we write to the buffer even if it fills it
            // exactly. Doing otherwise would mean flushing the buffer, then writing this
            // input to the inner writer, which in many cases would be a worse strategy.
            // SAFETY: There was either enough spare capacity already, or there wasn't and we
            // flushed the buffer to ensure that there is. In the latter case, we know that there
            // is because flushing ensured that our entire buffer is spare capacity, and we entered
            // this block because the input buffer length is less than that capacity. In either
            // case, it's safe to write the input buffer to our buffer.
            run {
                this.writeToBufferUnchecked(buf)
            }
            RustResult.Ok(buf.size)
        }
    }

    // Ensure this function does not get inlined into `writeAll`, so that it
    // remains inlineable and its common path remains as short as possible.
    // If this function ends up being called frequently relative to `writeAll`,
    // it's likely a sign that the client is using an improperly sized buffer
    // or their write patterns are somewhat pathological.
    public open fun writeAllCold(buf: List<UByte>): Result<Unit> {
        // Normally, `writeAll` just calls `write` in a loop. We can do better
        // by calling `self.getMut().writeAll()` directly, which avoids
        // round trips through the buffer in the event of a series of partial
        // writes in some circumstances.
        if (buf.size > this.spareCapacity()) {
            when (val tryResult = this.flushBuf()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        }
        // Why not len > capacity? To avoid a needless trip through the buffer when the input
        // exactly fills it. We'd just need to flush it to the underlying writer anyway.
        return if (buf.size >= this.buf.capacity()) {
            this.panicked = true
            val r = this.getMut().writeAll(buf)
            this.panicked = false
            r
        } else {
            // Write to the buffer. In this case, we write to the buffer even if it fills it
            // exactly. Doing otherwise would mean flushing the buffer, then writing this
            // input to the inner writer, which in many cases would be a worse strategy.
            // SAFETY: There was either enough spare capacity already, or there wasn't and we
            // flushed the buffer to ensure that there is. In the latter case, we know that there
            // is because flushing ensured that our entire buffer is spare capacity, and we entered
            // this block because the input buffer length is less than that capacity. In either
            // case, it's safe to write the input buffer to our buffer.
            run {
                this.writeToBufferUnchecked(buf)
            }
            RustResult.Ok(Unit)
        }
    }

    // SAFETY: Requires `buf.len() <= self.buf.capacity() - self.buf.len()`,
    // i.e., that input buffer length is less than or equal to spare capacity.
    public open fun writeToBufferUnchecked(buf: List<UByte>) {
        check(buf.size <= this.spareCapacity())
        val oldLen = this.buf.size
        val bufLen = buf.size
        val src = buf.asPtr()
        // ignore-tidy-undocumented-unchecked
        run {
            val dst = MutPtr((this.buf.asMutPtr()).rawAddress + ((oldLen).toLong() * (PrimitiveLayouts.U8).strideBytes))
            copyNonoverlapping(src, dst, bufLen, typeLayoutT = PrimitiveLayouts.U8)
            this.buf.setLen(oldLen + bufLen)
        }
    }

    public open fun spareCapacity(): Int = this.buf.capacity() - this.buf.size

    public open fun write(buf: List<UByte>): Result<Int> {
        // Use < instead of <= to avoid a needless trip through the buffer in some cases.
        // See `writeCold` for details.
        return if (buf.size < this.spareCapacity()) {
            // SAFETY: safe by above conditional.
            run {
                this.writeToBufferUnchecked(buf)
            }
            RustResult.Ok(buf.size)
        } else {
            this.writeCold(buf)
        }
    }

    public open fun writeAll(buf: List<UByte>): Result<Unit> {
        // Use < instead of <= to avoid a needless trip through the buffer in some cases.
        // See `writeAllCold` for details.
        return if (buf.size < this.spareCapacity()) {
            // SAFETY: safe by above conditional.
            run {
                this.writeToBufferUnchecked(buf)
            }
            RustResult.Ok(Unit)
        } else {
            this.writeAllCold(buf)
        }
    }

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> {
        // Note: Consider applying `@inline` / `@inline(never)` optimizations already applied
        // to `write` and `writeAll`. The performance benefits can be significant. See #79930.
        return if (this.getRef().isWriteVectored()) {
            // We have to handle the possibility that the total length of the buffers overflows
            // `Int` (even though this can only happen if multiple `IoSlice`s reference the
            // same underlying buffer, as otherwise the buffers wouldn't fit in memory). If the
            // computation overflows, then surely the input cannot fit in our buffer, so we forward
            // to the inner writer's `writeVectored` method to let it handle it appropriately.
            var saturatedTotalLen: Int = 0
            for (buf in bufs) {
                saturatedTotalLen = saturatedTotalLen.saturatingAdd(buf.len())
                if (saturatedTotalLen > this.spareCapacity() && !this.buf.isEmpty()) {
                    // Flush if the total length of the input exceeds our buffer's spare capacity.
                    // If we would have overflowed, this condition also holds, and we need to flush.
                    when (val tryResult = this.flushBuf()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
                }
                if (saturatedTotalLen >= this.buf.capacity()) {
                    // Forward to our inner writer if the total length of the input is greater than or
                    // equal to our buffer capacity. If we would have overflowed, this condition also
                    // holds, and we punt to the inner writer.
                    this.panicked = true
                    val r = this.getMut().writeVectored(bufs)
                    this.panicked = false
                    return r
                }
            }
            // `saturatedTotalLen<self.buf.capacity()` implies that we did not saturate.
            // SAFETY: We checked whether or not the spare capacity was large enough above. If
            // it was, then we're safe already. If it wasn't, we flushed, making sufficient
            // room for any input <= the buffer size, which includes this input.
            run {
                bufs.forEach({ b -> this.writeToBufferUnchecked(b) })
            }
            RustResult.Ok(saturatedTotalLen)
        } else {
            val iter = bufs.iter()
            var totalWritten =
                when (val matchVal = iter.byRef().indexOf({ buf1 -> !buf1.isEmpty() }).takeIf { it >= 0 }) {
                    null -> {
                        return RustResult.Ok(0)
                    }
                    else -> {
                        val buf1 = matchVal
                        // This is the first non-empty slice to write, so if it does
                        // not fit in the buffer, we still get to flush and proceed.
                        if (buf1.len() > this.spareCapacity()) {
                            when (val tryResult = this.flushBuf()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
                        }
                        if (buf1.len() >= this.buf.capacity()) {
                            // The slice is at least as large as the buffering capacity,
                            // so it's better to write it directly, bypassing the buffer.
                            this.panicked = true
                            val r1 = this.getMut().write(buf1)
                            this.panicked = false
                            return r1
                        } else {
                            // SAFETY: We checked whether or not the spare capacity was large enough above.
                            // If it was, then we're safe already. If it wasn't, we flushed, making
                            // sufficient room for any input <= the buffer size, which includes this input.
                            run {
                                this.writeToBufferUnchecked(buf1)
                            }
                            buf1.len()
                        }
                    }
                }
            check(totalWritten != 0)
            for (buf2 in iter) {
                if (buf2.len() <= this.spareCapacity()) {
                    // SAFETY: safe by above conditional.
                    run {
                        this.writeToBufferUnchecked(buf2)
                    }
                    // This cannot overflow `Int`. If we are here, we've written all of the bytes
                    // so far to our buffer, and we've ensured that we never exceed the buffer's
                    // capacity. Therefore, `totalWritten` <= `self.buf.capacity()` <= `Int.MAX`.
                    totalWritten += buf2.len()
                } else {
                    break
                }
            }
            RustResult.Ok(totalWritten)
        }
    }

    public open fun isWriteVectored(): Boolean = true

    public open fun flush(): Result<Unit> {
        when (val tryResult = this.flushBuf()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        return this.getMut().flush()
    }

    public open fun fmt(fmt: StringBuilder): RustResult<Unit, Error> = run { DebugStruct(fmt, "BufWriter").field("writer", this.inner).field("buffer", "${this.buf.size}/${this.buf.capacity()}").finish(); RustResult.Ok<Unit, Error>(Unit) }

    /**
     * Seek to the offset, in bytes, in the underlying writer.
     *
     * Seeking always writes out the internal buffer before seeking.
     */
    public open fun seek(pos: SeekFrom): Result<ULong> {
        when (val tryResult = this.flushBuf()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ULong, Error>(tryResult.errValue()) }
        return this.getMut().seek(pos)
    }

    public open fun drop() {
        if (!this.panicked) {
            // dtors should not panic, so we ignore a failed flush
        }
    }

    public companion object {
        /**
         * Creates a new `BufWriter<W>` with a default buffer capacity. The default is currently 8 KiB,
         * but may change in the future.
         *
         * # Examples
         *
         * ```kotlin
         * import std.io.BufWriter
         * import std.net.TcpStream
         *
         * var buffer = BufWriter.new(TcpStream.connect("127.0.0.1:34254").unwrap())
         * ```
         */
        public fun <W : Write> new(inner: W): BufWriter<W> = BufWriter.withCapacity(DEFAULT_BUF_SIZE, inner)

        /**
         * Attempts to allocate an internal buffer, _then_ calls the provided function
         * to retrieve the inner writer `W`.
         */
        public fun <W : Write> tryNewWith(f: () -> Result<W>): Result<BufWriter<W>> {
            val buf =
                when (val tryResult = Vec.tryWithCapacity(DEFAULT_BUF_SIZE).mapErr({
                    constError(ErrorKind.OutOfMemory, "failed to allocate write buffer")
                })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<BufWriter<W>, Error>(tryResult.errValue()) }
            val inner = when (val tryResult = f()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<BufWriter<W>, Error>(tryResult.errValue()) }
            return RustResult.Ok(BufWriter(inner = inner, buf = buf, panicked = false))
        }

        /**
         * Creates a new `BufWriter<W>` with at least the specified buffer capacity.
         *
         * # Examples
         *
         * Creating a buffer with a buffer of at least a hundred bytes.
         *
         * ```kotlin
         * import std.io.BufWriter
         * import std.net.TcpStream
         *
         * val stream = TcpStream.connect("127.0.0.1:34254").unwrap()
         * var buffer = BufWriter.withCapacity(100, stream)
         * ```
         */
        public fun <W : Write> withCapacity(capacity: Int, inner: W): BufWriter<W> = BufWriter(inner = inner, buf = ArrayList(capacity), panicked = false)
    }
}

/**
 * Error returned for the buffered data from `BufWriter.intoParts`, when the underlying
 * writer has previously panicked.  Contains the (possibly partly written) buffered data.
 *
 * # Example
 *
 * ```kotlin
 * import std.io.BufWriter
 * import std.io.Write
 * import std.panic.AssertUnwindSafe
 * import std.panic.catchUnwind
 *
 * open class PanickingWriter : Write {
 *     public open fun write(buf: List<UByte>): io.Result<Int> {
 *         return error("panic")
 *     }
 *
 *     public open fun flush(): io.Result<Unit> {
 *         return error("panic")
 *     }
 * }
 * var stream = BufWriter.new(PanickingWriter())
 * stream.append("some data").unwrap()
 * val result = catchUnwind(AssertUnwindSafe({
 *     stream.flush().unwrap()
 * }))
 * check(result.isErr())
 * val (recoveredWriter, bufferedData) = stream.intoParts()
 * check(when (recoveredWriter) {
 *     PanickingWriter -> true
 *     else -> false
 * })
 * check(bufferedData.unwrapErr().intoInner() == "some data".encodeToByteArray())
 * ```
 */
public open class WriterPanicked(
    public var buf: List<UByte>,
) {
    /**
     * Returns the perhaps-unwritten data.  Some of this data may have been written by the
     * panicking call(s) to the underlying writer, so simply writing it again is not a good idea.
     */
    public open fun intoInner(): List<UByte> = this.buf

    public open fun fmt(f: StringBuilder): RustResult<Unit, Error> = "BufWriter inner writer panicked, what data remains unwritten is not known".fmt(f)

    public open fun fmtDebug(f: StringBuilder): RustResult<Unit, Error> = run { DebugStruct(f, "WriterPanicked").field("buffer", "${this.buf.size}/${this.buf.capacity()}").finish(); RustResult.Ok<Unit, Error>(Unit) }
}
