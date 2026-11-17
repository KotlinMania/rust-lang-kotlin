// port-lint: source io/copy/generic.rs
package io.github.kotlinmania.rustlang.alloc.io.copy.generic

import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.VecDeque
import io.github.kotlinmania.rustlang.alloc.io.buffered.bufreader.BufReader
import io.github.kotlinmania.rustlang.alloc.io.buffered.bufwriter.BufWriter
import io.github.kotlinmania.rustlang.alloc.io.read.DEFAULT_BUF_SIZE
import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.alloc.io.read.read
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedBuf
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

/**
 * The userspace read-write-loop implementation of `io.copy` that is used when
 * OS-specific specializations for copy offloading are not available or not applicable.
 *
 * This function is able to perform a mild amount of specialization based on
 * the size of the `reader` and `writer` buffers, if they have any.
 *
 * * If `reader`s buffer is large enough ([`DefaultBufSize`](DEFAULT_BUF_SIZE)),
 *   _and_ it is larger than `writer`s buffer, copying will be controlled by `R`.
 * * Otherwise, copying will be controlled by `writer`.
 *
 * Currently, `[UByte]`, `List<UByte>`, `VecDeque<UByte>`, `BufReader<T>` and `BufWriter<T>`
 * are specialized with.
 */
public fun <R, W> genericCopy(reader: R, writer: W): Result<ULong> where R : Read<R>, W : Write {
    val readPriority = BufferedReaderSpec.BufferPriority(reader)
    val writePriority = BufferedWriterSpec.BufferPriority(writer)
    if (readPriority >= DEFAULT_BUF_SIZE && readPriority >= writePriority) {
        return reader.copyTo(writer)
    }
    return writer.copyFrom(reader)
}

/**
 * This is used by [genericCopy] to decide whether to use [BufferedReaderSpec.copyTo]
 * or [BufferedWriterSpec.copyFrom].
 */
public typealias BufferPriority = Int

/**
 * Unbuffered readers and writers have the lowest priority.
 */
public val UNBUFFERED: BufferPriority = Int.MIN_VALUE

/**
 * Readers and writers with their entire contents have the highest priority.
 */
public val IN_MEMORY: BufferPriority = Int.MAX_VALUE

/**
 * Specialization of the read-write loop in [genericCopy] that reuses the
 * internal buffer of a [BufReader]. If there's no buffer then the writer side
 * should be used instead.
 */
public interface BufferedReaderSpec {
    public fun bufferPriority(): BufferPriority

    public fun copyTo(to: Write): Result<ULong>
}

public fun <T> T.bufferPriority(): BufferPriority where T : Read<T> = UNBUFFERED

public fun <T> T.copyTo(to: Write): Result<ULong> where T : Read<T> {
    error("only called from specializations")
}

public fun List<UByte>.bufferPriority(): BufferPriority = IN_MEMORY

public fun List<UByte>.copyTo(to: Write): Result<ULong> {
    var selfMut = this
    val len = selfMut.size
    when (val tryResult = to.writeAll(selfMut)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ULong, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    selfMut = ((selfMut).subList(len, (selfMut).size))
    return RustResult.Ok(len.toULong())
}

public fun <A : Allocator> VecDeque<UByte, A>.bufferPriority(): BufferPriority = IN_MEMORY

public fun <A : Allocator> VecDeque<UByte, A>.copyTo(to: Write): Result<ULong> {
    val len = this.len()
    val (front, back) = this.asSlices()
    val bufs = listOf(IoSlice.new(front), IoSlice.new(back))
    when (val tryResult = to.writeAllVectored(bufs)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ULong, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    this.clear()
    return RustResult.Ok(len.toULong())
}

public fun <I> BufReader<I>.bufferPriority(): BufferPriority = this.capacity()

public fun <I> BufReader<I>.copyTo(to: Write): Result<ULong> {
    var len = 0
    while (true) {
        // Note: this relies on `implementation Read for BufReader` always calling fillBuf
        // if the buffer is empty, even for empty slices.
        // It can't be called directly here since specialization prevents us
        // from adding I: Read
        run {
            val matchVal = this.read(listOf())
            when {
                matchVal is RustResult.Ok<*, *> -> {}
                matchVal is RustResult.Err<*, *> && matchVal.errValue().isInterrupted() -> {
                    val e = matchVal.errValue()
                    continue
                }
                else -> {
                    val e1 = (matchVal as RustResult.Err<*, *>).errValue()
                    return RustResult.Err(e1)
                }
            }
        }
        val buf = this.buffer()
        if (this.buffer().isEmpty()) {
            return RustResult.Ok(len)
        }
        // In case the writer side is a BufWriter then its writeAll
        // implements an optimization that passes through large
        // buffers to the underlying writer. That code path is @cold
        // but we're still avoiding redundant memcopies when doing
        // a copy between buffered inputs and outputs.
        when (val tryResult = to.writeAll(buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ULong, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        len += buf.len().toULong()
        this.discardBuffer()
    }
}

/**
 * Specialization of the read-write loop in `genericCopy` that either uses a
 * stack buffer or reuses the internal buffer of a [BufWriter].
 */
public interface BufferedWriterSpec : Write {
    public fun bufferPriority(): BufferPriority

    public fun <R : Read<R>> copyFrom(reader: R): Result<ULong>
}

public fun <W : Write> W.bufferPriority(): BufferPriority = UNBUFFERED

public fun <W : Write, R : Read<R>> W.copyFrom(reader: R): Result<ULong> {
    // Unlike `BufferedReaderSpec.copyTo`, this _will_ be called as the fallback
    // when both the reader and writer provide no specialization.
    return stackBufferCopy(reader, this)
}

public fun <I : Write> BufWriter<I>.bufferPriority(): BufferPriority = this.capacity()

public fun <I : Write, R : Read<R>> BufWriter<I>.copyFrom(reader: R): Result<ULong> {
    if (this.capacity() < DEFAULT_BUF_SIZE) {
        // Since neither this buffer nor the reader's buffer are large enough,
        // fall back to the unspecialized implementation.
        return stackBufferCopy(reader, this)
    }
    var len = 0
    var init = false
    while (true) {
        val buf = this.bufferMut()
        val readBuf: BorrowedBuf<UByte>  = buf.spareCapacityMut().into()
        if (init) {
            // SAFETY: `init` is only true after `reader` initializes
            // `readBuf`. See the comment about `flushBuf` below.
            readBuf.setInit()
        }
        if (readBuf.capacity() >= DEFAULT_BUF_SIZE) {
            val cursor = readBuf.unfilled()
            run {
                val matchVal = reader.readBuf(cursor.reborrow())
                when {
                    matchVal is RustResult.Ok<*, *> -> {
                        val okValueVal = matchVal.okValue()
                        val bytesRead = cursor.written()
                        if (bytesRead == 0) {
                            return RustResult.Ok(len)
                        }
                        init = readBuf.isInit()
                        len += bytesRead.toULong()
                        // SAFETY: BorrowedBuf guarantees all of its filled bytes are init
                        buf.setLen(buf.len() + bytesRead)
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
        } else {
            // SAFETY: `flushBuf` will not de-initialize any elements of
            // the spare capacity so we can remember `init` across this.
            when (val tryResult = this.flushBuf()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ULong, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        }
    }
}

public fun <R : Read<R>> List<UByte>.copyFrom(reader: R): Result<ULong> = reader.readToEnd(this).map({ bytes -> ULong.tryFrom(bytes).expect("usize overflowed u64") })

/**
 * Copies from `reader` to `writer` using a stack-allocated buffer (a fixed sized array).
 */
public fun <R : Read<R>, W : Write> stackBufferCopy(reader: R, writer: W): Result<ULong> {
    val buf: MutableList<Any?> = MutableList(DEFAULT_BUF_SIZE) { MaybeUninit.uninit(UnresolvedTypeLayout<Any?>()) }
    val buf1: BorrowedBuf<UByte>  = buf.into()
    var len = 0
    while (true) {
        run {
            val matchVal = reader.readBuf(buf1.unfilled())
            when {
                matchVal is RustResult.Ok<*, *> -> {
                    val okValueVal = matchVal.okValue()
                }
                matchVal is RustResult.Err<*, *> && matchVal.errValue().isInterrupted() -> {
                    val e = matchVal.errValue()
                    continue
                }
                else -> {
                    val e1 = (matchVal as RustResult.Err<*, *>).errValue()
                    return RustResult.Err(e1)
                }
            }
        }
        if (buf1.filled().isEmpty()) {
            break
        }
        len += buf1.filled().len().toULong()
        when (val tryResult = writer.writeAll(buf1.filled())) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ULong, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        buf1.clear()
    }
    return RustResult.Ok(len)
}
