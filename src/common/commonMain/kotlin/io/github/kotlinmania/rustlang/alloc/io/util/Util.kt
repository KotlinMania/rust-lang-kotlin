// port-lint: source io/util.rs
package io.github.kotlinmania.rustlang.alloc.io.util

import io.github.kotlinmania.rustlang.alloc.io.Error
import io.github.kotlinmania.rustlang.alloc.io.ErrorKind
import io.github.kotlinmania.rustlang.alloc.io.bufread.BufRead
import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
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

public fun Empty.read(buf: MutableList<UByte>): Result<Int> = Result.Ok(0)

public fun Empty.readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = Result.Ok(Unit)

public fun Empty.readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = Result.Ok(0)

public fun Empty.isReadVectored(): Boolean {
    // Do not force `Chain<Empty, T>` or `Chain<T, Empty>` to use vectored
    // reads, unless the other reader is vectored.
    return false
}

public fun Empty.readExact(buf: MutableList<UByte>): Result<Unit> =
    if (!buf.isEmpty()) {
        Result.Err(Error.READ_EXACT_EOF)
    } else {
        Result.Ok(Unit)
    }

public fun Empty.readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit> =
    if (cursor.capacity() != 0) {
        Result.Err(Error.READ_EXACT_EOF)
    } else {
        Result.Ok(Unit)
    }

public fun Empty.readToEnd(buf: List<UByte>): Result<Int> = Result.Ok(0)

public fun Empty.readToString(buf: String): Result<Int> = Result.Ok(0)

public fun Empty.fillBuf(): Result<List<UByte>> = Result.Ok(listOf())

public fun Empty.consume(n: Int) {}

public fun Empty.hasDataLeft(): Result<Boolean> = Result.Ok(false)

public fun Empty.readUntil(byte: UByte, buf: List<UByte>): Result<Int> = Result.Ok(0)

public fun Empty.skipUntil(byte: UByte): Result<Int> = Result.Ok(0)

public fun Empty.readLine(buf: String): Result<Int> = Result.Ok(0)

public fun Repeat.read(buf: MutableList<UByte>): Result<Int> {
    buf.fill(this.byte)
    return Result.Ok(buf.size)
}

public fun Repeat.readExact(buf: MutableList<UByte>): Result<Unit> {
    buf.fill(this.byte)
    return Result.Ok(Unit)
}

public fun Repeat.readBuf(buf: BorrowedCursor<UByte>): Result<Unit> {
    // SAFETY: No uninit bytes are being written.
    buf.asMut().writeFilled(this.byte)
    // SAFETY: the entire unfilled portion of buf has been initialized.
    buf.advance(buf.capacity())
    return Result.Ok(Unit)
}

public fun Repeat.readBufExact(buf: BorrowedCursor<UByte>): Result<Unit> = this.readBuf(buf)

/**
 * This function is not supported by `io.Repeat`, because there's no end of its data
 */
public fun Repeat.readToEnd(arg0: List<UByte>): Result<Int> = Result.Err(Error.from(ErrorKind.OutOfMemory))

/**
 * This function is not supported by `io.Repeat`, because there's no end of its data
 */
public fun Repeat.readToString(arg0: String): Result<Int> = Result.Err(Error.from(ErrorKind.OutOfMemory))

public fun Repeat.readVectored(bufs: MutableList<IoSliceMut>): Result<Int> {
    var nwritten = 0
    for (buf in bufs) {
        nwritten +=
            when (val tryResult = this.read(buf)) {
                is Result.Ok<*, *> -> tryResult.value
                is Result.Err<*, *> -> return Result.Err(tryResult.error)
            }
    }
    return Result.Ok(nwritten)
}

public fun Repeat.isReadVectored(): Boolean = true

public fun <T : Read<T>, U : Read<U>> Chain<T, U>.read(buf: MutableList<UByte>): Result<Int> {
    if (!this.doneFirst) {
        run {
            val matchVal =
                when (val tryResult = this.first.read(buf)) {
                    is Result.Ok<*, *> -> tryResult.value
                    is Result.Err<*, *> -> return Result.Err(tryResult.error)
                }
            when {
                matchVal == 0 && !buf.isEmpty() -> this.doneFirst = true
                else -> {
                    val n = matchVal
                    return Result.Ok(n)
                }
            }
        }
    }
    return this.second.read(buf)
}

public fun <T : Read<T>, U : Read<U>> Chain<T, U>.readVectored(bufs: MutableList<IoSliceMut>): Result<Int> {
    if (!this.doneFirst) {
        run {
            val matchVal =
                when (val tryResult = this.first.readVectored(bufs)) {
                    is Result.Ok<*, *> -> tryResult.value
                    is Result.Err<*, *> -> return Result.Err(tryResult.error)
                }
            when {
                matchVal == 0 && bufs.any({ b -> !b.isEmpty() }) -> this.doneFirst = true
                else -> {
                    val n = matchVal
                    return Result.Ok(n)
                }
            }
        }
    }
    return this.second.readVectored(bufs)
}

public fun <T : Read<T>, U : Read<U>> Chain<T, U>.isReadVectored(): Boolean = this.first.isReadVectored() || this.second.isReadVectored()

public fun <T : Read<T>, U : Read<U>> Chain<T, U>.readToEnd(buf: List<UByte>): Result<Int> {
    var read = 0
    if (!this.doneFirst) {
        read +=
            when (val tryResult = this.first.readToEnd(buf)) {
                is Result.Ok<*, *> -> tryResult.value
                is Result.Err<*, *> -> return Result.Err(tryResult.error)
            }
        this.doneFirst = true
    }
    read +=
        when (val tryResult = this.second.readToEnd(buf)) {
            is Result.Ok<*, *> -> tryResult.value
            is Result.Err<*, *> -> return Result.Err(tryResult.error)
        }
    return Result.Ok(read)
}

// We don't override `readToString` here because an UTF-8 sequence could
// be split between the two parts of the chain
public fun <T : Read<T>, U : Read<U>> Chain<T, U>.readBuf(buf: BorrowedCursor<UByte>): Result<Unit> {
    if (buf.capacity() == 0) {
        return Result.Ok(Unit)
    }
    if (!this.doneFirst) {
        val oldLen = buf.written()
        when (val tryResult = this.first.readBuf(buf.reborrow())) {
            is Result.Ok<*, *> -> tryResult.value
            is Result.Err<*, *> -> return Result.Err(tryResult.error)
        }
        if (buf.written() != oldLen) {
            return Result.Ok(Unit)
        } else {
            this.doneFirst = true
        }
    }
    return this.second.readBuf(buf)
}

public fun <T : BufRead<T>, U : BufRead<U>> Chain<T, U>.fillBuf(): Result<List<UByte>> {
    if (!this.doneFirst) {
        run {
            val matchVal =
                when (val tryResult = this.first.fillBuf()) {
                    is Result.Ok<*, *> -> tryResult.value
                    is Result.Err<*, *> -> return Result.Err(tryResult.error)
                }
            when {
                matchVal.isEmpty() -> {
                    val buf = matchVal
                    this.doneFirst = true
                }
                else -> {
                    val buf1 = matchVal
                    return Result.Ok(buf1)
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

public fun <T : BufRead<T>, U : BufRead<U>> Chain<T, U>.readUntil(byte: UByte, buf: List<UByte>): Result<Int> {
    var read = 0
    if (!this.doneFirst) {
        val n =
            when (val tryResult = this.first.readUntil(byte, buf)) {
                is Result.Ok<*, *> -> tryResult.value
                is Result.Err<*, *> -> return Result.Err(tryResult.error)
            }
        read += n
        run {
            val matchVal = buf.last()
            when {
                matchVal != null && matchVal == byte.toInt() && n != 0 -> {
                    val b = matchVal
                    return Result.Ok(read)
                }
                else -> this.doneFirst = true
            }
        }
    }
    read +=
        when (val tryResult = this.second.readUntil(byte, buf)) {
            is Result.Ok<*, *> -> tryResult.value
            is Result.Err<*, *> -> return Result.Err(tryResult.error)
        }
    return Result.Ok(read)
}

public fun <T : Read<T>> Take<T>.read(buf: MutableList<UByte>): Result<Int> {
    // Don't call into inner reader at all at EOF because it may still block
    if (this.limit == 0) {
        return Result.Ok(0)
    }
    val max = minOf(buf.size.toULong(), this.limit).toInt()
    val n =
        when (val tryResult = this.inner.read(buf.subList(0, max))) {
            is Result.Ok<*, *> -> tryResult.value
            is Result.Err<*, *> -> return Result.Err(tryResult.error)
        }
    check(n.toULong() <= this.limit) { "number of read bytes exceeds limit" }
    this.limit -= n.toULong()
    return Result.Ok(n)
}

public fun <T : Read<T>> Take<T>.readBuf(buf: BorrowedCursor<UByte>): Result<Unit> {
    // Don't call into inner reader at all at EOF because it may still block
    if (this.limit == 0) {
        return Result.Ok(Unit)
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
        return Result.Ok(listOf())
    }
    val buf =
        when (val tryResult = this.inner.fillBuf()) {
            is Result.Ok<*, *> -> tryResult.value
            is Result.Err<*, *> -> return Result.Err(tryResult.error)
        }
    val cap = minOf(buf.len().toULong(), this.limit).toInt()
    return Result.Ok(buf[0 until cap])
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
) : Iterable<Result<UByte>> {
    override fun iterator(): Iterator<Result<UByte>> =
        object : AbstractIterator<Result<UByte>>() {
            override fun computeNext() {
                when (val v = this@Bytes.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    // Not `@inline`. This function gets inlined even without it, but having
    // the inline annotation can result in worse code generation. See #116785.
    public open fun next(): Result<UByte>? = SpecReadByte.SpecReadByte(this.inner)

    public open fun sizeHint(): Pair<Int, Int?> = SizeHint.sizeHint(this.inner)

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
                matchVal is Result.Ok<*, *> && matchVal.value == 0 -> null
                matchVal is Result.Ok<*, *> -> Result.Ok(byte)
                matchVal is Result.Err<*, *> && (matchVal as Result.Err<*, *>).error.isInterrupted() -> {
                    val e = (matchVal as Result.Err<*, *>).error
                    continue
                }
                else -> {
                    val e1 = (matchVal as Result.Err<*, *>).error
                    Result.Err(e1)
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
) : Iterable<Result<List<UByte>>> {
    override fun iterator(): Iterator<Result<List<UByte>>> =
        object : AbstractIterator<Result<List<UByte>>>() {
            override fun computeNext() {
                when (val v = this@Split.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun next(): Result<List<UByte>>? {
        val buf = mutableListOf()
        return run {
            val matchVal = this.buf.readUntil(this.delim, buf)
            when {
                matchVal is Result.Ok<*, *> && matchVal.value == 0 -> null
                matchVal is Result.Ok<*, *> -> {
                    if (buf[buf.size - 1] == this.delim) {
                        buf.removeLastOrNull()
                    }
                    Result.Ok(buf)
                }
                else -> {
                    val e = (matchVal as Result.Err<*, *>).error
                    Result.Err(e)
                }
            }
        }
    }

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
) : Iterable<Result<String>> {
    override fun iterator(): Iterator<Result<String>> =
        object : AbstractIterator<Result<String>>() {
            override fun computeNext() {
                when (val v = this@Lines.next()) {
                    null -> done()
                    else -> setNext(v)
                }
            }
        }

    public open fun next(): Result<String>? {
        val buf = ""
        return run {
            val matchVal = this.buf.readLine(buf)
            when {
                matchVal is Result.Ok<*, *> && matchVal.value == 0 -> null
                matchVal is Result.Ok<*, *> -> {
                    if (buf.endsWith('\n')) {
                        buf.pop()
                        if (buf.endsWith('\r')) {
                            buf.pop()
                        }
                    }
                    Result.Ok(buf)
                }
                else -> {
                    val e = (matchVal as Result.Err<*, *>).error
                    Result.Err(e)
                }
            }
        }
    }

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

public fun <B> lines(buf: B): Lines<B> = Lines(buf = buf)
