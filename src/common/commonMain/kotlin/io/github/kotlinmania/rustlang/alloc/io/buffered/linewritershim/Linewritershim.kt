// port-lint: source io/buffered/linewritershim.rs
package io.github.kotlinmania.rustlang.alloc.io.buffered.linewritershim

import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.num.rustSaturatingAdd
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.io.buffered.bufwriter.BufWriter

/**
 * Private helper struct for implementing the line-buffered writing logic.
 *
 * This shim temporarily wraps a BufWriter, and uses its internals to
 * implement a line-buffered writer (specifically by using the internal
 * methods like writeToBuf and flushBuf). In this way, a more
 * efficient abstraction can be created than one that only had access to
 * `write` and `flush`, without needlessly duplicating a lot of the
 * implementation details of BufWriter. This also allows existing
 * `BufWriters` to be temporarily given line-buffering logic; this is what
 * enables Stdout to be alternately in line-buffered or block-buffered mode.
 */
public open class LineWriterShim<W : Write> internal constructor(
    bufferCtorArg: BufWriter<W>,
) : Write {
    public var buffer: BufWriter<W> = bufferCtorArg
        internal set

    /**
     * Gets a reference to the inner writer (that is, the writer
     * wrapped by the BufWriter).
     */
    public open fun inner(): W = this.buffer.getRef()

    /**
     * Gets a mutable reference to the inner writer (that is, the writer
     * wrapped by the BufWriter). Be careful with this writer, as writes to
     * it will bypass the buffer.
     */
    public open fun innerMut(): W = this.buffer.getMut()

    /**
     * Gets the content currently buffered in self.buffer
     */
    public open fun buffered(): List<UByte> = this.buffer.buffer()

    /**
     * Flushes the buffer if and only if the last byte is a newline
     * (indicating that an earlier write only succeeded partially, and we
     * want to retry flushing the buffered line before continuing with a
     * subsequent write).
     */
    public open fun flushIfCompletedLine(): Result<Unit> =
        when (val matchVal = this.buffered().last()) {
            10u -> this.buffer.flushBuf()
            else -> Result.Ok(Unit)
        }

    /**
     * Vectored line-buffered write over an already-capped list of buffers.
     *
     * The caller is responsible for trimming `bufs` to the prefix it is
     * willing to scan (see `MAX_BUFS_TO_SCAN`). This method only ever writes
     * or buffers bytes from `bufs`, so any newline it might bury in the
     * `BufWriter` is one it has itself scanned for -- buffers the caller
     * dropped past the cap can never end up stuck in the buffer. Bytes not
     * accounted for in the return value are left for the next call.
     */
    public open fun writeVectoredScanned(bufs: List<IoSlice>): Result<Int> {
        // Find the buffer containing the last newline.
        val lastNewlineBufIdx = bufs.mapIndexed { index, item -> Pair(index, item) }.rev().findMap({ (i, buf) -> memchr.memchr(10u, buf)?.let({ i }) })
        // If there are no new newlines (that is, if this write is less than
        // one line), just do a regular buffered write.
        val lastNewlineBufIdx1 =
            when (lastNewlineBufIdx) {
                null -> {
                    when (val tryResult = this.flushIfCompletedLine()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                    return this.buffer.writeVectored(bufs)
                }
                else -> {
                    val i = lastNewlineBufIdx
                    i
                }
            }
        // Flush existing content to prepare for our write.
        when (val tryResult = this.buffer.flushBuf()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        // This is what we're going to try to write directly to the inner
        // writer. The rest will be buffered, if nothing goes wrong.
        val (lines, tail) = bufs.splitAt(lastNewlineBufIdx1 + 1)
        // Write `lines` directly to the inner writer. In keeping with the
        // `write` convention, make at most one attempt to add new (unbuffered)
        // data. Because this write doesn't touch the BufWriter state directly,
        // and the buffer is known to be empty, we don't need to worry about
        // self.panicked here.
        val flushed = when (val tryResult = this.innerMut().writeVectored(lines)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        // If inner returns Ok(0), propagate that to the caller without
        // doing additional buffering; otherwise we're just guaranteeing
        // an "ErrorKind.WriteZero" later.
        if (flushed == 0) {
            return Result.Ok(0)
        }
        // Don't try to reconstruct the exact amount written; just bail
        // in the event of a partial write.
        var linesLen: Int = 0
        for (buf in lines) {
            // With overlapping/duplicate slices the total length may in theory
            // exceed usize.Max
            linesLen = rustSaturatingAdd(linesLen, buf.len())
            if (flushed < linesLen) {
                return Result.Ok(flushed)
            }
        }
        // Now that the write has succeeded, buffer the rest (or as much of the
        // rest as possible). `tail` is the part of the scanned prefix after the
        // last newline, so it cannot contain a newline of its own.
        val buffered: Int = tail.iter().filter({ buf1 -> !buf1.isEmpty() }).map({ buf1 -> this.buffer.writeToBuf(buf1) }).takeWhile({ n -> n > 0 }).sum()
        return Result.Ok(flushed + buffered)
    }

    /**
     * Writes some data into this BufWriter with line buffering.
     *
     * This means that, if any newlines are present in the data, the data up to
     * the last newline is sent directly to the underlying writer, and data
     * after it is buffered. Returns the number of bytes written.
     *
     * This function operates on a "best effort basis"; in keeping with the
     * convention of `Write.write`, it makes at most one attempt to write
     * new data to the underlying writer. If that write only reports a partial
     * success, the remaining data will be buffered.
     *
     * Because this function attempts to send completed lines to the underlying
     * writer, it will also flush the existing buffer if it ends with a
     * newline, even if the incoming data does not contain any newlines.
     */
    public open fun write(buf: List<UByte>): Result<Int> {
        val newlineIdx1 =
            when (val matchVal = memchr.memrchr(10u, buf)) {
                // If there are no new newlines (that is, if this write is less than
                // one line), just do a regular buffered write (which may flush if
                // we exceed the inner buffer's size)
                null -> {
                    when (val tryResult = this.flushIfCompletedLine()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                    return this.buffer.write(buf)
                }
                // Otherwise, arrange for the lines to be written directly to the
                // inner writer.
                else -> {
                    val newlineIdx = matchVal
                    newlineIdx + 1
                }
            }
        // Flush existing content to prepare for our write. We have to do this
        // before attempting to write `buf` in order to maintain consistency;
        // if we add `buf` to the buffer then try to flush it all at once,
        // we're obligated to return Ok(), which would mean suppressing any
        // errors that occur during flush.
        when (val tryResult = this.buffer.flushBuf()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        // This is what we're going to try to write directly to the inner
        // writer. The rest will be buffered, if nothing goes wrong.
        val lines = ((buf).subList(0, newlineIdx1))
        // Write `lines` directly to the inner writer. In keeping with the
        // `write` convention, make at most one attempt to add new (unbuffered)
        // data. Because this write doesn't touch the BufWriter state directly,
        // and the buffer is known to be empty, we don't need to worry about
        // self.buffer.panicked here.
        val flushed = when (val tryResult = this.innerMut().write(lines)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
        // If buffer returns Ok(0), propagate that to the caller without
        // doing additional buffering; otherwise we're just guaranteeing
        // an "ErrorKind.WriteZero" later.
        if (flushed == 0) {
            return Result.Ok(0)
        }
        // Now that the write has succeeded, buffer the rest (or as much of
        // the rest as possible). If there were any unwritten newlines, we
        // only buffer out to the last unwritten newline that fits in the
        // buffer; this helps prevent flushing partial lines on subsequent
        // calls to LineWriterShim.write.
        // Handle the cases in order of most-common to least-common, under
        // the presumption that most writes succeed in totality, and that most
        // writes are smaller than the buffer.
        // - Is this a partial line (ie, no newlines left in the unwritten tail)
        // - If not, does the data out to the last unwritten newline fit in
        // the buffer?
        // - If not, scan for the last newline that *does* fit in the buffer
        val tail1 =
            if (flushed >= newlineIdx1) {
                val tail = ((buf).subList(flushed, (buf).size))
                // Avoid unnecessary short writes by not splitting the remaining
                // bytes if they're larger than the buffer.
                // They can be written in full by the next call to write.
                if (tail.size >= this.buffer.capacity()) {
                    return Result.Ok(flushed)
                }
                tail
            } else if (newlineIdx1 - flushed <= this.buffer.capacity()) {
                ((buf).subList(flushed, newlineIdx1))
            } else {
                val scanArea = ((buf).subList(flushed, (buf).size))
                val scanArea1 = ((scanArea).subList(0, this.buffer.capacity()))
                when (val matchVal1 = memchr.memrchr(10u, scanArea1)) {
                    null -> scanArea1
                    else -> {
                        val newlineIdx2 = matchVal1
                        scanArea1[0 until newlineIdx2 + 1]
                    }
                }
            }
        val buffered = this.buffer.writeToBuf(tail1)
        return Result.Ok(flushed + buffered)
    }

    public open fun flush(): Result<Unit> = this.buffer.flush()

    /**
     * Writes some vectored data into this BufWriter with line buffering.
     *
     * This means that, if any newlines are present in the data, the data up to
     * and including the buffer containing the last newline is sent directly to
     * the inner writer, and the data after it is buffered. Returns the number
     * of bytes written.
     *
     * This function operates on a "best effort basis"; in keeping with the
     * convention of `Write.write`, it makes at most one attempt to write
     * new data to the underlying writer.
     *
     * Because this function attempts to send completed lines to the underlying
     * writer, it will also flush the existing buffer if it contains any
     * newlines.
     *
     * Because sorting through an array of `IoSlice` can be a bit convoluted,
     * This method differs from write in the following ways:
     *
     * - It attempts to write the full content of all the buffers up to and
     *   including the one containing the last newline. This means that it
     *   may attempt to write a partial line, that buffer has data past the
     *   newline.
     * - If the write only reports partial success, it does not attempt to
     *   find the precise location of the written bytes and buffer the rest.
     *
     * If the underlying vector doesn't support vectored writing, we instead
     * simply write the first non-empty buffer with `write`. This way, we
     * get the benefits of more granular partial-line handling without losing
     * anything in efficiency
     */
    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> {
        // Only scan (and operate on) the first MAX_BUFS_TO_SCAN slices. The cap
        // is what keeps writeAllVectored() from going quadratic when callers
        // pass many newline-free slices -- without it, every iteration of the
        // outer loop rescans every remaining buffer. 1024 is a portable,
        // generous upper bound: it is the value of UIO_MAXIOV / IOV_MAX on
        // Linux and the BSDs (and the hardcoded cap in
        // sys.net.connection.socket.solid), so on those platforms it also
        // lines up with the most a single writev() can retire. On platforms
        // whose syscall cap is smaller (POSIX requires only 16) or that have no
        // cap at all (Windows), the constant still serves its primary purpose
        // of bounding scan work.
        //
        // Everything past the cap is left untouched for the next call; the
        // outer loop in writeAllVectored() makes forward progress via the
        // short return value, and correctness is preserved everywhere. We hand
        // the capped prefix to a helper so the rest of the logic can only ever
        // see -- and therefore only ever write or buffer -- buffers we have
        // actually scanned for newlines.
        val MAX_BUFS_TO_SCAN: Int = 1024
        // If there's no specialized behavior for writeVectored, just use
        // write. This has the benefit of more granular partial-line handling.
        if (!this.isWriteVectored()) {
            return when (val matchVal = bufs.indexOf({ buf -> !buf.isEmpty() }).takeIf { it >= 0 }) {
                null -> Result.Ok(0)
                else -> {
                    val buf = matchVal
                    this.write(buf)
                }
            }
        }
        return this.writeVectoredScanned(((bufs).subList(0, bufs.size.min(MAX_BUFS_TO_SCAN))))
    }

    public open fun isWriteVectored(): Boolean = this.inner().isWriteVectored()

    /**
     * Writes some data into this BufWriter with line buffering.
     *
     * This means that, if any newlines are present in the data, the data up to
     * the last newline is sent directly to the underlying writer, and data
     * after it is buffered.
     *
     * Because this function attempts to send completed lines to the underlying
     * writer, it will also flush the existing buffer if it contains any
     * newlines, even if the incoming data does not contain any newlines.
     */
    public open fun writeAll(buf: List<UByte>): Result<Unit> {
        return when (val matchVal = memchr.memrchr(10u, buf)) {
            // If there are no new newlines (that is, if this write is less than
            // one line), just do a regular buffered write (which may flush if
            // we exceed the inner buffer's size)
            null -> {
                when (val tryResult = this.flushIfCompletedLine()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                this.buffer.writeAll(buf)
            }
            else -> {
                val newlineIdx = matchVal
                val (lines, tail) = buf.splitAt(newlineIdx + 1)
                if (this.buffered().isEmpty()) {
                    when (val tryResult = this.innerMut().writeAll(lines)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                } else {
                    // If there is any buffered data, we add the incoming lines
                    // to that buffer before flushing, which saves us at least
                    // one write call. We can't really do this with `write`,
                    // since we can't do this *and* not suppress errors *and*
                    // report a consistent state to the caller in a return
                    // value, but here in writeAll it's fine.
                    when (val tryResult = this.buffer.writeAll(lines)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                    when (val tryResult = this.buffer.flushBuf()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err(tryResult.error) }
                }
                this.buffer.writeAll(tail)
            }
        }
    }

    public companion object {
        public operator fun <W : Write> invoke(
            buffer: BufWriter<W>,
        ): LineWriterShim<W> = LineWriterShim<W>(
            bufferCtorArg = buffer,
        )

        public fun <W : Write> new(buffer: BufWriter<W>): LineWriterShim<W> = LineWriterShim(buffer = buffer)
    }
}
