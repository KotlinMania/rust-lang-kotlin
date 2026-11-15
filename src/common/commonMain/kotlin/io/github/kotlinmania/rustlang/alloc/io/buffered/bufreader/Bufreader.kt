// port-lint: source io/buffered/bufreader.rs
package io.github.kotlinmania.rustlang.alloc.io.buffered.bufreader

import io.github.kotlinmania.rustlang.alloc.io.buffered.bufreader.buffer.Buffer
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.Seek
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.io.sizehint.SizeHint
import io.github.kotlinmania.rustlang.core.num.rustCheckedAdd
import io.github.kotlinmania.rustlang.core.num.rustCheckedSub
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.io.Error
import io.github.kotlinmania.rustlang.io.bufread.BufRead
import io.github.kotlinmania.rustlang.io.read.DEFAULT_BUF_SIZE
import io.github.kotlinmania.rustlang.io.read.Read
import io.github.kotlinmania.rustlang.io.read.appendToString
import io.github.kotlinmania.rustlang.io.read.defaultReadBufExact
import io.github.kotlinmania.rustlang.io.read.defaultReadExact
import io.github.kotlinmania.rustlang.io.util.SpecReadByte
import io.github.kotlinmania.rustlang.io.util.uninlinedSlowReadByte

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
 * The `BufReader<R>` struct adds buffering to any reader.
 *
 * It can be excessively inefficient to work directly with a [Read] instance.
 * For example, every call to [`read`][TcpStream.read] on [TcpStream]
 * results in a system call. A `BufReader<R>` performs large, infrequent reads on
 * the underlying [Read] and maintains an in-memory buffer of the results.
 *
 * `BufReader<R>` can improve the speed of programs that make *small* and
 * *repeated* read calls to the same file or network socket. It does not
 * help when reading very large amounts at once, or reading just one or a few
 * times. It also provides no advantage when reading from a source that is
 * already in memory, like a <code>[Vec]\<u8></code>.
 *
 * When the `BufReader<R>` is dropped, the contents of its buffer will be
 * discarded. Creating multiple instances of a `BufReader<R>` on the same
 * stream can cause data loss. Reading from the underlying reader after
 * unwrapping the `BufReader<R>` with [BufReader.intoInner] can also cause
 * data loss.
 *
 * [TcpStream.read]: ../../std/net/struct.TcpStream.html#method.read
 * [TcpStream]: ../../std/net/struct.TcpStream.html
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs.File
 * import std.io.BufReader
 * import std.io.prelude.*
 *
 * fun main(): Result<Unit> {
 *     val f = File.open("log.txt").getOrElse { return Result.failure(it) }
 *     var reader = BufReader.new(f)
 *     var line = ""
 *     val len = reader.readLine(line).getOrElse { return Result.failure(it) }
 *     println("First line is $len bytes long")
 *     return Result.success(Unit)
 * }
 * ```
 */
public open class BufReader<R>(
    public var buf: Buffer,
    public var inner: R,
) : SpecReadByte, Read<BufReader<R>>, BufRead<BufReader<R>>, Seek {
    /**
     * Attempt to look ahead `n` bytes.
     *
     * `n` must be less than or equal to `capacity`.
     *
     * The returned slice may be less than `n` bytes long if
     * end of file is reached.
     *
     * After calling this method, you may call [`consume`](BufRead.consume)
     * with a value less than or equal to `n` to advance over some or all of
     * the returned bytes.
     *
     * ## Examples
     *
     * ```kotlin
     * import std.io.BufReader
     * import std.io.Read
     *
     * @feature(bufreaderPeek)
     * public fun doctestEntry() {
     *     var bytes = "oh, hello there".encodeToByteArray()
     *     var rdr = BufReader.withCapacity(6, bytes)
     *     check(rdr.peek(2).unwrap() == "oh".encodeToByteArray())
     *     var buf = MutableList(4) { 0 }
     *     rdr.read(buf).unwrap()
     *     check(buf == "oh, ".encodeToByteArray())
     *     check(rdr.peek(5).unwrap() == "hello".encodeToByteArray())
     *     var s = ""
     *     rdr.readToString(s).unwrap()
     *     check(s == "hello there")
     *     check(rdr.peek(1).unwrap().len() == 0)
     * }
     * ```
     */
    public open fun peek(n: Int): Result<List<UByte>> {
        check(n <= this.capacity())
        while (n > this.buf.buffer().len()) {
            if (this.buf.pos() > 0) {
                this.buf.backshift()
            }
            val new = when (val tryResult = this.buf.readMore(this.inner)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            if (new == 0) {
                // end of file, no more bytes to read
                return Result.Ok(this.buf.buffer())
            }
            check(this.buf.pos() == 0)
        }
        return Result.Ok(this.buf.buffer()[0 until n])
    }

    /**
     * Gets a reference to the underlying reader.
     *
     * It is inadvisable to directly read from the underlying reader.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.BufReader
     *
     * fun main(): Result<Unit> {
     *     val f1 = File.open("log.txt").getOrElse { return Result.failure(it) }
     *     val reader = BufReader.new(f1)
     *     val f2 = reader.getRef()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun getRef(): R = this.inner

    /**
     * Gets a mutable reference to the underlying reader.
     *
     * It is inadvisable to directly read from the underlying reader.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.BufReader
     *
     * fun main(): Result<Unit> {
     *     val f1 = File.open("log.txt").getOrElse { return Result.failure(it) }
     *     var reader = BufReader.new(f1)
     *     val f2 = reader.getMut()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun getMut(): kotlin.reflect.KMutableProperty0<R> {
        return this::inner
    }

    /**
     * Returns a reference to the internally buffered data.
     *
     * Unlike [fillBuf], this will not attempt to fill the buffer if it is empty.
     *
     * [fillBuf]: BufRead.fillBuf
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.BufRead
     * import std.io.BufReader
     *
     * fun main(): Result<Unit> {
     *     val f = File.open("log.txt").getOrElse { return Result.failure(it) }
     *     var reader = BufReader.new(f)
     *     check(reader.buffer().isEmpty())
     *     if (reader.fillBuf().getOrElse { return Result.failure(it) }.len() > 0) {
     *         check(!reader.buffer().isEmpty())
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun buffer(): List<UByte> = this.buf.buffer()

    /**
     * Returns the number of bytes the internal buffer can hold at once.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.BufRead
     * import std.io.BufReader
     *
     * fun main(): Result<Unit> {
     *     val f = File.open("log.txt").getOrElse { return Result.failure(it) }
     *     var reader = BufReader.new(f)
     *     val capacity = reader.capacity()
     *     val buffer = reader.fillBuf().getOrElse { return Result.failure(it) }
     *     check(buffer.len() <= capacity)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun capacity(): Int = this.buf.capacity()

    /**
     * Unwraps this `BufReader<R>`, returning the underlying reader.
     *
     * Note that any leftover data in the internal buffer is lost. Therefore,
     * a following read from the underlying reader may lead to data loss.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.BufReader
     *
     * fun main(): Result<Unit> {
     *     val f1 = File.open("log.txt").getOrElse { return Result.failure(it) }
     *     val reader = BufReader.new(f1)
     *     val f2 = reader.intoInner()
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun intoInner(): R = this.inner

    /**
     * Invalidates all data in the internal buffer.
     */
    public open fun discardBuffer() {
        this.buf.discardBuffer()
    }

    // This is only used by a test which asserts that the initialization-tracking is correct.
    public open fun initialized(): Boolean = this.buf.initialized()

    /**
     * Seeks relative to the current position. If the new position lies within the buffer,
     * the buffer will not be flushed, allowing for more efficient seeks.
     * This method does not return the location of the underlying reader, so the caller
     * must track this information themselves if it is required.
     */
    public open fun seekRelative(offset: Long): Result<Unit> {
        val pos = this.buf.pos().toULong()
        if (offset < 0L) {
            when (val matchVal = rustCheckedSub(pos, (-offset).toULong())) {
                null -> {}
                else -> {
                    this.buf.unconsume((-offset).toInt())
                    return Result.Ok(Unit)
                }
            }
        } else when (val matchVal1 = rustCheckedAdd(pos, offset.toULong())) {
            null -> {}
            else -> {
                val newPos = matchVal1
                if (newPos <= this.buf.filled().toULong()) {
                    this.buf.consume(offset.toInt())
                    return Result.Ok(Unit)
                }
            }
        }
        return this.seek(SeekFrom.Current(offset)).map({ Unit })
    }

    public override fun specReadByte(): Result<UByte>? {
        var byte = 0
        if (this.buf.consumeWith(1, { claimed -> byte = claimed[0] })) {
            return Result.Ok(byte)
        }
        // Fallback case, only reached once per buffer refill.
        return uninlinedSlowReadByte(this)
    }

    public override fun read(buf: MutableList<UByte>): Result<Int> {
        // If we don't have any buffered data and we're doing a massive read
        // (larger than our internal buffer), bypass our internal buffer
        // entirely.
        if (this.buf.pos() == this.buf.filled() && buf.size >= this.capacity()) {
            this.discardBuffer()
            return this.inner.read(buf)
        }
        val rem = when (val tryResult = this.fillBuf()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        val nread = when (val tryResult = rem.read(buf)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        this.consume(nread)
        return Result.Ok(nread)
    }

    public override fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> {
        // If we don't have any buffered data and we're doing a massive read
        // (larger than our internal buffer), bypass our internal buffer
        // entirely.
        if (this.buf.pos() == this.buf.filled() && cursor.capacity() >= this.capacity()) {
            this.discardBuffer()
            return this.inner.readBuf(cursor)
        }
        val prev = cursor.written()
        val rem = when (val tryResult = this.fillBuf()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        when (val tryResult = rem.readBuf(cursor.reborrow())) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        // actually never fails
        this.consume(cursor.written() - prev)
        // slice impl of readBuf known to never unfill buf
        return Result.Ok(Unit)
    }

    // Small readExacts from a BufReader are extremely common when used with a deserializer.
    // The default implementation calls read in a loop, which results in surprisingly poor code
    // generation for the common path where the buffer has enough bytes to fill the passed-in
    // buffer.
    public override fun readExact(buf: MutableList<UByte>): Result<Unit> {
        if (this.buf.consumeWith(buf.size, { claimed -> run { val dstTmp0 = buf; val srcTmp0 = claimed; for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] } })) {
            return Result.Ok(Unit)
        }
        return defaultReadExact(this, buf)
    }

    public override fun readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit> {
        if (this.buf.consumeWith(cursor.capacity(), { claimed -> cursor.append(claimed) })) {
            return Result.Ok(Unit)
        }
        return defaultReadBufExact(this, cursor)
    }

    public override fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> {
        val totalLen = bufs.map({ b -> b.len() }).sum<Int>()
        if (this.buf.pos() == this.buf.filled() && totalLen >= this.capacity()) {
            this.discardBuffer()
            return this.inner.readVectored(bufs)
        }
        val rem = when (val tryResult = this.fillBuf()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        val nread = when (val tryResult = rem.readVectored(bufs)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        this.consume(nread)
        return Result.Ok(nread)
    }

    public override fun isReadVectored(): Boolean = this.inner.isReadVectored()

    // The inner reader might have an optimized `readToEnd`. Drain our buffer and then
    // delegate to the inner implementation.
    public override fun readToEnd(buf: List<UByte>): Result<Int> {
        val innerBuf = this.buffer()
        when (val tryResult = buf.tryReserve(innerBuf.len())) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        when (val tryResult = buf.tryExtendFromSliceOfBytes(innerBuf)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        val nread = innerBuf.len()
        this.discardBuffer()
        return Result.Ok(nread + when (val tryResult = this.inner.readToEnd(buf)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) })
    }

    // The inner reader might have an optimized `readToEnd`. Drain our buffer and then
    // delegate to the inner implementation.
    public override fun readToString(buf: String): Result<Int> {
        // In the general `else` case below we must read bytes into a side buffer, check
        // that they are valid UTF-8, and then append them to `buf`. This requires a
        // potentially large memcpy.
        //
        // If `buf` is empty--the most common case--we can leverage `appendToString`
        // to read directly into `buf`s internal byte buffer, saving an allocation and
        // a memcpy.
        return if (buf.isEmpty()) {
            // `appendToString`s safety relies on the buffer only being appended to since
            // it only checks the UTF-8 validity of new data. If there were existing content in
            // `buf` then an untrustworthy reader (i.e. `self.inner`) could not only append
            // bytes but also modify existing bytes and render them invalid. On the other hand,
            // if `buf` is empty then by definition any writes must be appends and
            // `appendToString` will validate all of the new bytes.
            // ignore-tidy-undocumented-unchecked
            appendToString(buf, { b -> this.readToEnd(b) })
        } else {
            // We cannot append our byte buffer directly onto the `buf` String as there could
            // be an incomplete UTF-8 sequence that has only been partially read. We must read
            // everything into a side buffer first and then call `fromUtf8` on the complete
            // buffer.
            val bytes = mutableListOf()
            when (val tryResult = this.readToEnd(bytes)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            val string = when (val tryResult = io.github.kotlinmania.rustlang.string.String.fromUtf8(bytes).mapErr({ Error.INVALID_UTF8 })) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            when (val tryResult = buf.tryPushStr(string)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            Result.Ok(string.len())
        }
    }

    public override fun fillBuf(): Result<List<UByte>> = this.buf.fillBuf(this.inner)

    public override fun consume(amt: Int) {
        this.buf.consume(amt)
    }

    public open fun fmt(fmt: StringBuilder) {
        DebugStruct(fmt, "BufReader").field("reader", this.inner).field("buffer", "${this.buf.filled() - this.buf.pos()}/${this.capacity()}").finish()
    }

    /**
     * Seek to an offset, in bytes, in the underlying reader.
     *
     * The position used for seeking with <code>[SeekFrom.Current]\(_)</code> is the
     * position the underlying reader would be at if the `BufReader<R>` had no
     * internal buffer.
     *
     * Seeking always discards the internal buffer, even if the seek position
     * would otherwise fall within it. This guarantees that calling
     * [BufReader.intoInner] immediately after a seek yields the underlying reader
     * at the same position.
     *
     * To seek without discarding the internal buffer, use [BufReader.seekRelative].
     *
     * See [std.io.Seek] for more details.
     *
     * Note: In the edge case where you're seeking with <code>[SeekFrom.Current]\(n)</code>
     * where `n` minus the internal buffer length overflows an `Long`, two
     * seeks will be performed instead of one. If the second seek returns
     * [Err], the underlying reader will be left at the same position it would
     * have if you called `seek` with <code>[SeekFrom.Current]\(0)</code>.
     *
     * [std.io.Seek]: Seek
     */
    public open fun seek(pos: SeekFrom): Result<ULong> {
        val result: ULong
        if (pos is SeekFrom.Current) {
            val (n) = pos
            val remainder = (this.buf.filled() - this.buf.pos()).toLong()
            // it should be safe to assume that remainder fits within an i64 as the alternative
            // means we managed to allocate 8 exbibytes and that's absurd.
            // But it's not out of the realm of possibility for some weird underlying reader to
            // support seeking by i64.Min so we need to handle underflow when subtracting
            // remainder.
            when (val matchVal = n.checkedSub(remainder)) {
                null -> {
                    // seek backwards by our remainder, and then by the offset
                    when (val tryResult = this.inner.seek(SeekFrom.Current(-remainder))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                    this.discardBuffer()
                    result = when (val tryResult = this.inner.seek(SeekFrom.Current(n))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                }
                else -> {
                    val offset = matchVal
                    result = when (val tryResult = this.inner.seek(SeekFrom.Current(offset))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                }
            }
        } else {
            // Seeking with Start/End doesn't care about our buffer length.
            result = when (val tryResult = this.inner.seek(pos)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        }
        this.discardBuffer()
        return Result.Ok(result)
    }

    /**
     * Returns the current seek position from the start of the stream.
     *
     * The value returned is equivalent to `self.seek(SeekFrom.Current(0))`
     * but does not flush the internal buffer. Due to this optimization the
     * function does not guarantee that calling `.intoInner()` immediately
     * afterwards will yield the underlying reader at the same position. Use
     * [BufReader.seek] instead if you require that guarantee.
     *
     * # Panics
     *
     * This function will panic if the position of the inner reader is smaller
     * than the amount of buffered data. That can happen if the inner reader
     * has an incorrect implementation of [Seek.streamPosition], or if the
     * position has gone out of sync due to calling [Seek.seek] directly on
     * the underlying reader.
     *
     * # Example
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.BufRead
     * import std.io.BufReader
     * import std.io.Seek
     *
     * fun main(): io.Result<Unit> {
     *     var f = BufReader.new(File.open("foo.txt").getOrElse { return Result.failure(it) })
     *     val before = f.streamPosition().getOrElse { return Result.failure(it) }
     *     f.readLine("").getOrElse { return Result.failure(it) }
     *     val after = f.streamPosition().getOrElse { return Result.failure(it) }
     *     println("The first line was ${after - before} bytes long")
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun streamPosition(): Result<ULong> {
        val remainder = (this.buf.filled() - this.buf.pos()).toULong()
        return this.inner.streamPosition().map({ pos ->
            (pos.checkedSub(remainder) ?: error("overflow when subtracting remaining buffer size from inner stream position"))
        })
    }

    /**
     * Seeks relative to the current position.
     *
     * If the new position lies within the buffer, the buffer will not be
     * flushed, allowing for more efficient seeks. This method does not return
     * the location of the underlying reader, so the caller must track this
     * information themselves if it is required.
     */
    public open fun seekRelativeSeek(offset: Long): Result<Unit> = this.seekRelative(offset)

    public companion object {
        /**
         * Creates a new `BufReader<R>` with a default buffer capacity. The default is currently 8 KiB,
         * but may change in the future.
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.File
         * import std.io.BufReader
         *
         * fun main(): Result<Unit> {
         *     val f = File.open("log.txt").getOrElse { return Result.failure(it) }
         *     val reader = BufReader.new(f)
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <R> new(inner: R): BufReader<R> = BufReader.withCapacity(DEFAULT_BUF_SIZE, inner)

        /**
         * Attempts to allocate an internal buffer, _then_ calls the provided function
         * to retrieve the inner reader `R`.
         */
        public fun <R> tryNewWith(f: () -> Result<R>): Result<BufReader<R>> {
            val buf = when (val tryResult = Buffer.tryWithCapacity(DEFAULT_BUF_SIZE)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            val inner = when (val tryResult = f()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
            return Result.Ok(BufReader(inner = inner, buf = buf))
        }

        /**
         * Creates a new `BufReader<R>` with the specified buffer capacity.
         *
         * # Examples
         *
         * Creating a buffer with ten bytes of capacity:
         *
         * ```kotlin
         * import std.fs.File
         * import std.io.BufReader
         *
         * fun main(): Result<Unit> {
         *     val f = File.open("log.txt").getOrElse { return Result.failure(it) }
         *     val reader = BufReader.withCapacity(10, f)
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun <R> withCapacity(capacity: Int, inner: R): BufReader<R> = BufReader(inner = inner, buf = Buffer.withCapacity(capacity))
    }
}

public fun <T> BufReader<T>.lowerBound(): Int = SizeHint.lowerBound(this.getRef()) + this.buffer().size

public fun <T> BufReader<T>.upperBound(): Int? = SizeHint.upperBound(this.getRef()).andThen({ up -> rustCheckedAdd(this.buffer().size, up.toInt()) })
