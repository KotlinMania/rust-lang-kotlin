// port-lint: source io/util.rs
package io.github.kotlinmania.rustlang.alloc.io.util

import io.github.kotlinmania.rustlang.alloc.io.bufread.BufRead
import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.sizehint.SizeHint
import io.github.kotlinmania.rustlang.core.io.util.Chain
import io.github.kotlinmania.rustlang.core.io.util.Empty
import io.github.kotlinmania.rustlang.core.io.util.Repeat
import io.github.kotlinmania.rustlang.core.io.util.Take
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.len
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

public fun Empty.read(buf: MutableList<UByte>): Result<Int> = RustResult.Ok(0)

public fun Empty.readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = RustResult.Ok(Unit)

public fun Empty.readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = RustResult.Ok(0)

public fun Empty.isReadVectored(): Boolean {
    // Do not force `Chain<Empty, T>` or `Chain<T, Empty>` to use vectored
    // reads, unless the other reader is vectored.
    return false
}

public fun Empty.readExact(buf: MutableList<UByte>): Result<Unit> =
    if (!buf.isEmpty()) {
        RustResult.Err(Error.READ_EXACT_EOF)
    } else {
        RustResult.Ok(Unit)
    }

public fun Empty.readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit> =
    if (cursor.capacity() != 0) {
        RustResult.Err(Error.READ_EXACT_EOF)
    } else {
        RustResult.Ok(Unit)
    }

public fun Empty.readToEnd(buf: MutableList<UByte>): Result<Int> = RustResult.Ok(0)

public fun Empty.readToString(buf: String): Result<Int> = RustResult.Ok(0)

public fun Empty.fillBuf(): Result<List<UByte>> = RustResult.Ok(listOf())

public fun Empty.consume(n: Int) {}

public fun Empty.hasDataLeft(): Result<Boolean> = RustResult.Ok(false)

public fun Empty.readUntil(byte: UByte, buf: MutableList<UByte>): Result<Int> = RustResult.Ok(0)

public fun Empty.skipUntil(byte: UByte): Result<Int> = RustResult.Ok(0)

public fun Empty.readLine(buf: String): Result<Int> = RustResult.Ok(0)

public fun Repeat.read(buf: MutableList<UByte>): Result<Int> {
    buf.fill(this.byte)
    return RustResult.Ok(buf.size)
}

public fun Repeat.readExact(buf: MutableList<UByte>): Result<Unit> {
    buf.fill(this.byte)
    return RustResult.Ok(Unit)
}

public fun Repeat.readBuf(buf: BorrowedCursor<UByte>): Result<Unit> {
    // SAFETY: No uninit bytes are being written.
    buf.asMut().writeFilled(this.byte)
    // SAFETY: the entire unfilled portion of buf has been initialized.
    buf.advance(buf.capacity())
    return RustResult.Ok(Unit)
}

public fun Repeat.readBufExact(buf: BorrowedCursor<UByte>): Result<Unit> = this.readBuf(buf)

/**
 * This function is not supported by `io.Repeat`, because there's no end of its data
 */
public fun Repeat.readToEnd(arg0: MutableList<UByte>): Result<Int> = RustResult.Err(Error.from(ErrorKind.OutOfMemory))

/**
 * This function is not supported by `io.Repeat`, because there's no end of its data
 */
public fun Repeat.readToString(arg0: String): Result<Int> = RustResult.Err(Error.from(ErrorKind.OutOfMemory))

public fun Repeat.readVectored(bufs: MutableList<IoSliceMut>): Result<Int> {
    var nwritten = 0
    for (buf in bufs) {
        nwritten += when (val tryResult = this.read(buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    }
    return RustResult.Ok(nwritten)
}

public fun Repeat.isReadVectored(): Boolean = true

public fun <T : Read<T>, U : Read<U>> Chain<T, U>.read(buf: MutableList<UByte>): Result<Int> {
    if (!this.doneFirst) {
        run {
            val matchVal = when (val tryResult = this.first.read(buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            when {
                matchVal == 0 && !buf.isEmpty() -> this.doneFirst = true
                else -> {
                    val n = matchVal
                    return RustResult.Ok(n)
                }
            }
        }
    }
    return this.second.read(buf)
}

public fun <T : Read<T>, U : Read<U>> Chain<T, U>.readVectored(bufs: MutableList<IoSliceMut>): Result<Int> {
    if (!this.doneFirst) {
        run {
            val matchVal = when (val tryResult = this.first.readVectored(bufs)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            when {
                matchVal == 0 && bufs.any({ b -> !b.isEmpty() }) -> this.doneFirst = true
                else -> {
                    val n = matchVal
                    return RustResult.Ok(n)
                }
            }
        }
    }
    return this.second.readVectored(bufs)
}

public fun <T : Read<T>, U : Read<U>> Chain<T, U>.isReadVectored(): Boolean = this.first.isReadVectored() || this.second.isReadVectored()

public fun <T : Read<T>, U : Read<U>> Chain<T, U>.readToEnd(buf: MutableList<UByte>): Result<Int> {
    var read = 0
    if (!this.doneFirst) {
        read += when (val tryResult = this.first.readToEnd(buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        this.doneFirst = true
    }
    read += when (val tryResult = this.second.readToEnd(buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok(read)
}

// We don't override `readToString` here because an UTF-8 sequence could
// be split between the two parts of the chain
public fun <T : Read<T>, U : Read<U>> Chain<T, U>.readBuf(buf: BorrowedCursor<UByte>): Result<Unit> {
    if (buf.capacity() == 0) {
        return RustResult.Ok(Unit)
    }
    if (!this.doneFirst) {
        val oldLen = buf.written()
        when (val tryResult = this.first.readBuf(buf.reborrow())) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        if (buf.written() != oldLen) {
            return RustResult.Ok(Unit)
        } else {
            this.doneFirst = true
        }
    }
    return this.second.readBuf(buf)
}

public fun <T : BufRead<T>, U : BufRead<U>> Chain<T, U>.fillBuf(): Result<List<UByte>> {
    if (!this.doneFirst) {
        run {
            val matchVal = when (val tryResult = this.first.fillBuf()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<List<UByte>, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
            when {
                matchVal.isEmpty() -> {
                    val buf = matchVal
                    this.doneFirst = true
                }
                else -> {
                    val buf1 = matchVal
                    return RustResult.Ok(buf1)
                }
            }
        }
    }
    return this.second.fillBuf()
}

public fun <T : BufRead<T>, U : BufRead<U>> Chain<T, U>.consume(amt: Int) {
    if (!this.doneFirst) {
        this.first.consume(amt)
    } else {
        this.second.consume(amt)
    }
}

public fun <T : BufRead<T>, U : BufRead<U>> Chain<T, U>.readUntil(byte: UByte, buf: MutableList<UByte>): Result<Int> {
    var read = 0
    if (!this.doneFirst) {
        val n = when (val tryResult = this.first.readUntil(byte, buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        read += n
        run {
            val matchVal = buf.last()
            when {
                matchVal != null && matchVal == byte.toInt() && n != 0 -> {
                    val b = matchVal
                    return RustResult.Ok(read)
                }
                else -> this.doneFirst = true
            }
        }
    }
    read += when (val tryResult = this.second.readUntil(byte, buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok(read)
}

public fun <T : Read<T>> Take<T>.read(buf: MutableList<UByte>): Result<Int> {
    // Don't call into inner reader at all at EOF because it may still block
    if (this.limit == 0) {
        return RustResult.Ok(0)
    }
    val max = minOf(buf.size.toULong(), this.limit).toInt()
    val n = when (val tryResult = this.inner.read(buf.subList(0, max))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    check(n.toULong() <= this.limit) { "number of read bytes exceeds limit" }
    this.limit -= n.toULong()
    return RustResult.Ok(n)
}

public fun <T : Read<T>> Take<T>.readBuf(buf: BorrowedCursor<UByte>): Result<Unit> {
    // Don't call into inner reader at all at EOF because it may still block
    if (this.limit == 0) {
        return RustResult.Ok(Unit)
    }
    return if (this.limit < buf.capacity().toULong()) {
        // The condition above guarantees that `self.limit` fits in `Int`.
        val limit = this.limit.toInt()
        val isInit = buf.isInit()
        // SAFETY: no uninit data is written to ibuf
        val slicedBuf = BorrowedBuf.from(buf.asMut()[0 until limit])
        if (isInit) {
            // SAFETY: `slicedBuf` is a subslice of `buf`, so if `buf` was initialized then
            // `slicedBuf` is.
            slicedBuf.setInit()
        }
        val result = this.inner.readBuf(slicedBuf.unfilled())
        val didInitUpToLimit = slicedBuf.isInit()
        val filled = slicedBuf.len()
        // slicedBuf must drop here
        // Avoid accidentally quadratic behaviour by initializing the whole
        // cursor if only part of it was initialized.
        if (didInitUpToLimit && !isInit) {
            // SAFETY: No uninit data will be written.
            val unfilledBeforeAdvance = buf.asMut()
            unfilledBeforeAdvance[limit until unfilledBeforeAdvance.size].writeFilled(0)
            // SAFETY: `unfilledBeforeAdvance[.. limit]` was initialized by `T.readBuf`, and
            // `unfilledBeforeAdvance[limit ..]` was just initialized.
            buf.setInit()
        }
        // SAFETY: filled bytes have been filled
        run {
            buf.advance(filled)
        }
        this.limit -= filled.toULong()
        result
    } else {
        val written = buf.written()
        val result1 = this.inner.readBuf(buf.reborrow())
        this.limit -= (buf.written() - written).toULong()
        result1
    }
}

public fun <T : BufRead<T>> Take<T>.fillBuf(): Result<List<UByte>> {
    // Don't call into inner reader at all at EOF because it may still block
    if (this.limit == 0) {
        return RustResult.Ok(listOf())
    }
    val buf = when (val tryResult = this.inner.fillBuf()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<List<UByte>, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    val cap = minOf(buf.len().toULong(), this.limit).toInt()
    return RustResult.Ok(buf[0 until cap])
}

public fun <T : BufRead<T>> Take<T>.consume(amt: Int) {
    // Don't let callers reset the limit by passing an overlarge value
    val amt1 = minOf(amt.toULong(), this.limit).toInt()
    this.limit -= amt1.toULong()
    this.inner.consume(amt1)
}

/**
 * An iterator over `UByte` values of a reader.
 *
 * This struct is generally created by calling [bytes] on a reader.
 * Please see the documentation of [bytes] for more details.
 *
 * [bytes]: Read.bytes
 */
public open class Bytes<R>(
    public var inner: R,
) {
    public companion object {
        public fun <R> typeLayout(typeLayoutR: TypeLayout<R>): TypeLayout<Bytes<R>> =
            object : TypeLayout<Bytes<R>> {
                override val alignBytes: Long = listOf(1L, typeLayoutR.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutR.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Bytes<R> =
                    Bytes(
                        (typeLayoutR).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Bytes<R>) {
                    (typeLayoutR).writeNative(addr, value.inner)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Bytes<R> =
                    Bytes(
                        (typeLayoutR).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Bytes<R>) {
                    (typeLayoutR).writeBytes(buf, offset, value.inner)
                }
            }
    }
}

// Not `@inline`. This function gets inlined even without it, but having
// the inline annotation can result in worse code generation. See #116785.
public fun <R : Read<R>> Bytes<R>.next(): Result<UByte>? = this.inner.specReadByte()

public fun <R : Read<R>> Bytes<R>.sizeHint(): Pair<Int, Int?> = SizeHint.sizeHint(this.inner)

/**
 * For the specialization of `Bytes.next`.
 */
public interface SpecReadByte {
    public fun specReadByte(): Result<UByte>?
}

public fun <R> R.specReadByte(): Result<UByte>? where R : Read<R> = inlinedSlowReadByte(this)

/**
 * Reads a single byte in a slow, generic way. This is used by the default
 * `specReadByte`.
 */
public fun <R : Read<R>> inlinedSlowReadByte(reader: R): Result<UByte>? {
    val localStorageL377C4 = AddressableLocal(0, PrimitiveLayouts.I32)
    val byte by localStorageL377C4
    while (true) {
        return run {
            val matchVal = reader.read(RustMutableReference(localStorageL377C4.asMutPtr(), localStorageL377C4.layout))
            when {
                matchVal is RustResult.Ok<*, *> && matchVal.okValue() == 0 -> {
                    val okValueVal = matchVal.okValue()
                    null
                }
                matchVal is RustResult.Ok<*, *> -> RustResult.Ok(byte)
                matchVal is RustResult.Err<*, *> && matchVal.errValue().isInterrupted() -> {
                    val e = matchVal.errValue()
                    continue
                }
                else -> {
                    val e1 = matchVal.errValue()
                    RustResult.Err(e1)
                }
            }
        }
    }
}

// Used by `BufReader.specReadByte`, for which the `inline(never)` is
// important.
public fun <R : Read<R>> uninlinedSlowReadByte(reader: R): Result<UByte>? = inlinedSlowReadByte(reader)

public fun <R> bytes(inner: R): Bytes<R> = Bytes(inner = inner)

/**
 * An iterator over the contents of an instance of `BufRead` split on a
 * particular byte.
 *
 * This struct is generally created by calling [split] on a `BufRead`.
 * Please see the documentation of [split] for more details.
 *
 * [split]: BufRead.split
 */
public open class Split<B>(
    public var buf: B,
    public var delim: UByte,
) {
    public companion object {
        public fun <B> typeLayout(typeLayoutB: TypeLayout<B>): TypeLayout<Split<B>> =
            object : TypeLayout<Split<B>> {
                override val alignBytes: Long = listOf(1L, typeLayoutB.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutB.sizeBytes, 1L)
                override val sizeBytes: Long = alignUpBytes((off1 + 1L), alignBytes)
                override fun readNative(addr: Long): Split<B> =
                    Split(
                        (typeLayoutB).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + off1),
                    )
                override fun writeNative(addr: Long, value: Split<B>) {
                    (typeLayoutB).writeNative(addr, value.buf)
                    (PrimitiveLayouts.U8).writeNative(addr + off1, value.delim)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Split<B> =
                    Split(
                        (typeLayoutB).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + off1.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Split<B>) {
                    (typeLayoutB).writeBytes(buf, offset, value.buf)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + off1.toInt(), value.delim)
                }
            }
    }
}

public fun <B : BufRead<B>> Split<B>.next(): Result<List<UByte>>? {
    val buf = mutableListOf()
    return run {
        val matchVal = this.buf.readUntil(this.delim, buf)
        when {
            matchVal is RustResult.Ok<*, *> && matchVal.okValue() == 0 -> {
                val okValueVal = matchVal.okValue()
                null
            }
            matchVal is RustResult.Ok<*, *> -> {
                if (buf[buf.size - 1] == this.delim) {
                    buf.removeLastOrNull()
                }
                RustResult.Ok(buf)
            }
            else -> {
                val e = matchVal.errValue()
                RustResult.Err(e)
            }
        }
    }
}

public fun <B> split(buf: B, delim: UByte): Split<B> = Split(buf = buf, delim = delim)

/**
 * An iterator over the lines of an instance of [BufRead].
 *
 * This struct is generally created by calling [lines] on a [BufRead].
 * Please see the documentation of [lines] for more details.
 *
 * [lines]: BufRead.lines
 */
public open class Lines<B>(
    public var buf: B,
) {
    public companion object {
        public fun <B> typeLayout(typeLayoutB: TypeLayout<B>): TypeLayout<Lines<B>> =
            object : TypeLayout<Lines<B>> {
                override val alignBytes: Long = listOf(1L, typeLayoutB.alignBytes).max()
                override val sizeBytes: Long = alignUpBytes(typeLayoutB.sizeBytes, alignBytes)
                override fun readNative(addr: Long): Lines<B> =
                    Lines(
                        (typeLayoutB).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Lines<B>) {
                    (typeLayoutB).writeNative(addr, value.buf)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Lines<B> =
                    Lines(
                        (typeLayoutB).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Lines<B>) {
                    (typeLayoutB).writeBytes(buf, offset, value.buf)
                }
            }
    }
}

public fun <B : BufRead<B>> Lines<B>.next(): Result<String>? {
    val buf = ""
    return run {
        val matchVal = this.buf.readLine(buf)
        when {
            matchVal is RustResult.Ok<*, *> && matchVal.okValue() == 0 -> {
                val okValueVal = matchVal.okValue()
                null
            }
            matchVal is RustResult.Ok<*, *> -> {
                if (buf.endsWith('\n')) {
                    buf.pop()
                    if (buf.endsWith('\r')) {
                        buf.pop()
                    }
                }
                RustResult.Ok(buf)
            }
            else -> {
                val e = matchVal.errValue()
                RustResult.Err(e)
            }
        }
    }
}

public fun <B> lines(buf: B): Lines<B> = Lines(buf = buf)
