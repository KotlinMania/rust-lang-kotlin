// port-lint: source io/impls.rs
package io.github.kotlinmania.rustlang.alloc.io.impls

import io.github.kotlinmania.rustlang.alloc.collections.vecdeque.VecDeque
import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.alloc.io.read.appendToString
import io.github.kotlinmania.rustlang.alloc.io.read.read
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.fmt.Arguments
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.Seek
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.io.sizehint.SizeHint
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

public fun <T> T.lowerBound(): Int = SizeHint.lowerBound(this)

public fun <T> T.upperBound(): Int? = SizeHint.upperBound(this)

public fun <W : Write, A : Allocator> W.write(buf: List<UByte>): Result<Int> = this.write(buf)

public fun <W : Write, A : Allocator> W.writeVectored(bufs: List<IoSlice>): Result<Int> = this.writeVectored(bufs)

public fun <W : Write, A : Allocator> W.isWriteVectored(): Boolean = this.isWriteVectored()

public fun <W : Write, A : Allocator> W.flush(): Result<Unit> = this.flush()

public fun <W : Write, A : Allocator> W.writeAll(buf: List<UByte>): Result<Unit> = this.writeAll(buf)

public fun <W : Write, A : Allocator> W.writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit> = this.writeAllVectored(bufs)

public fun <W : Write, A : Allocator> W.writeFmt(fmt: Arguments): Result<Unit> = this.writeFmt(fmt)

public fun <S : Seek, A : Allocator> S.seek(pos: SeekFrom): Result<ULong> = this.seek(pos)

public fun <S : Seek, A : Allocator> S.rewind(): Result<Unit> = this.rewind()

public fun <S : Seek, A : Allocator> S.streamLen(): Result<ULong> = this.streamLen()

public fun <S : Seek, A : Allocator> S.streamPosition(): Result<ULong> = this.streamPosition()

public fun <S : Seek, A : Allocator> S.seekRelative(offset: Long): Result<Unit> = this.seekRelative(offset)

// =============================================================================
// In-memory buffer implementations

/**
 * Read is implemented for `[UByte]` by copying from the slice.
 *
 * Note that reading updates the slice to point to the yet unread part.
 * The slice will be empty when EOF is reached.
 */
public fun List<UByte>.read(buf: MutableList<UByte>): Result<Int> {
    var selfMut = this
    val amt = minOf(buf.size, selfMut.size)
    val (a, b) = selfMut.splitAt(amt)
    // First check if the amount of bytes we want to read is small:
    // `copyFromSlice` will generally expand to a call to `memcpy`, and
    // for a single byte the overhead is significant.
    if (amt == 1) {
        buf[0] = a[0]
    } else {
        run { val dstTmp0 = buf; val srcTmp0 = a; for (idxTmp0 in srcTmp0.indices) dstTmp0[idxTmp0] = srcTmp0[idxTmp0] }
    }
    selfMut = b
    return RustResult.Ok(amt)
}

public fun List<UByte>.readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> {
    var selfMut = this
    val amt = minOf(cursor.capacity(), selfMut.size)
    val (a, b) = selfMut.splitAt(amt)
    cursor.append(a)
    selfMut = b
    return RustResult.Ok(Unit)
}

public fun List<UByte>.readVectored(bufs: MutableList<IoSliceMut>): Result<Int> {
    var nread = 0
    for (buf in bufs) {
        nread += when (val tryResult = this.read(buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
        if (this.isEmpty()) {
            break
        }
    }
    return RustResult.Ok(nread)
}

public fun List<UByte>.isReadVectored(): Boolean = true

public fun List<UByte>.readExact(buf: MutableList<UByte>): Result<Unit> {
    var selfMut = this
    if (buf.size > selfMut.size) {
        // `readExact` makes no promise about the content of `buf` if it
        // fails so don't bother about that.
        selfMut = ((selfMut).subList(selfMut.size, (selfMut).size))
        return RustResult.Err(Error.READ_EXACT_EOF)
    }
    val (a, b) = selfMut.splitAt(buf.size)
    // First check if the amount of bytes we want to read is small:
    // `copyFromSlice` will generally expand to a call to `memcpy`, and
    // for a single byte the overhead is significant.
    if (buf.size == 1) {
        buf[0] = a[0]
    } else {
        run { val dstTmp1 = buf; val srcTmp1 = a; for (idxTmp1 in srcTmp1.indices) dstTmp1[idxTmp1] = srcTmp1[idxTmp1] }
    }
    selfMut = b
    return RustResult.Ok(Unit)
}

public fun List<UByte>.readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit> {
    var selfMut = this
    if (cursor.capacity() > selfMut.size) {
        // Append everything we can to the cursor.
        cursor.append(selfMut)
        selfMut = ((selfMut).subList(selfMut.size, (selfMut).size))
        return RustResult.Err(Error.READ_EXACT_EOF)
    }
    val (a, b) = selfMut.splitAt(cursor.capacity())
    cursor.append(a)
    selfMut = b
    return RustResult.Ok(Unit)
}

public fun List<UByte>.readToEnd(buf: List<UByte>): Result<Int> {
    val len = this.size
    when (val tryResult = buf.tryReserve(len)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    run {
        when (val tryResult = buf.tryExtendFromSliceOfBytes(this)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    } * this = ((this).subList(len, (this).size))
    return RustResult.Ok(len)
}

public fun List<UByte>.readToString(buf: String): Result<Int> {
    val content = when (val tryResult = String.fromUtf8(this).mapErr({ Error.INVALID_UTF8 })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    val len = this.size
    when (val tryResult = buf.tryReserve(len)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    run {
        when (val tryResult = buf.tryPushStr(content)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    } * this = ((this).subList(len, (this).size))
    return RustResult.Ok(len)
}

public fun List<UByte>.fillBuf(): Result<List<UByte>> = RustResult.Ok(this)

public fun List<UByte>.consume(amt: Int) {
    var selfMut = this
    selfMut = ((selfMut).subList(amt, (selfMut).size))
}

/**
 * Write is implemented for `List<UByte>` by appending to the vector.
 * The vector will grow as needed.
 */
public fun <A : Allocator> List<UByte>.write(buf: List<UByte>): Result<Int> {
    when (val tryResult = List.writeAll(this, buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok(buf.size)
}

public fun <A : Allocator> List<UByte>.writeVectored(bufs: List<IoSlice>): Result<Int> {
    val len = bufs.map({ b -> b.len() }).sum()
    when (val tryResult = this.tryReserve(len)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    for (buf in bufs) {
        when (val tryResult = List.writeAll(this, buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    }
    return RustResult.Ok(len)
}

public fun <A : Allocator> List<UByte>.isWriteVectored(): Boolean = true

public fun <A : Allocator> List<UByte>.writeAll(buf: List<UByte>): Result<Unit> {
    when (val tryResult = this.tryExtendFromSliceOfBytes(buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok(Unit)
}

public fun <A : Allocator> List<UByte>.writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit> {
    when (val tryResult = this.writeVectored(bufs)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok(Unit)
}

public fun <A : Allocator> List<UByte>.flush(): Result<Unit> = RustResult.Ok(Unit)

/**
 * Read is implemented for `VecDeque<UByte>` by consuming bytes from the front of the `VecDeque`.
 */
/**
 * Fill `buf` with the contents of the "front" slice as returned by
 * [`asSlices`][VecDeque.asSlices]. If the contained byte slices of the `VecDeque` are
 * discontiguous, multiple calls to `read` will be needed to read the entire content.
 */
public fun <A : Allocator> VecDeque<UByte, A>.read(buf: MutableList<UByte>): Result<Int> {
    var (front, _) = this.asSlices()
    val n = when (val tryResult = Read.Read(front, buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    this.drain(0 until n)
    return RustResult.Ok(n)
}

public fun <A : Allocator> VecDeque<UByte, A>.readExact(buf: MutableList<UByte>): Result<Unit> {
    val (front, back) = this.asSlices()
    // Use only the front buffer if it is big enough to fill `buf`, else use
    // the back buffer too.
    when (val matchVal = buf.splitAtMutChecked(front.len())) {
        null -> run { val dstTmp2 = buf; val srcTmp2 = front[0 until buf.size]; for (idxTmp2 in srcTmp2.indices) dstTmp2[idxTmp2] = srcTmp2[idxTmp2] }
        else -> {
            val bufFront = matchVal.first
            val bufBack = matchVal.second
            when (val matchVal1 = back.splitAtChecked(bufBack.len())) {
                null -> {
                    this.clear()
                    return RustResult.Err(Error.READ_EXACT_EOF)
                }
                else -> {
                    val back1 = matchVal1.first
                    bufFront.copyFromSlice(front)
                    bufBack.copyFromSlice(back1)
                }
            }
        }
    }
    this.drain(0 until buf.size)
    return RustResult.Ok(Unit)
}

public fun <A : Allocator> VecDeque<UByte, A>.readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> {
    var (front, _) = this.asSlices()
    val n = minOf(cursor.capacity(), front.len())
    when (val tryResult = front.readBuf(cursor)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    this.drain(0 until n)
    return RustResult.Ok(Unit)
}

public fun <A : Allocator> VecDeque<UByte, A>.readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit> {
    val len = cursor.capacity()
    val (front, back) = this.asSlices()
    when (val matchVal = front.splitAtChecked(cursor.capacity())) {
        null -> {
            cursor.append(front)
            when (val matchVal1 = back.splitAtChecked(cursor.capacity())) {
                null -> {
                    cursor.append(back)
                    this.clear()
                    return RustResult.Err(Error.READ_EXACT_EOF)
                }
                else -> {
                    val back1 = matchVal1.first
                    cursor.append(back1)
                }
            }
        }
        else -> {
            val front1 = matchVal.first
            cursor.append(front1)
        }
    }
    this.drain(0 until len)
    return RustResult.Ok(Unit)
}

public fun <A : Allocator> VecDeque<UByte, A>.readToEnd(buf: List<UByte>): Result<Int> {
    // The total len is known upfront so we can reserve it in a single call.
    val len = this.len()
    when (val tryResult = buf.tryReserve(len)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    val (front, back) = this.asSlices()
    buf.extendFromSlice(front)
    buf.extendFromSlice(back)
    this.clear()
    return RustResult.Ok(len)
}

public fun <A : Allocator> VecDeque<UByte, A>.readToString(buf: String): Result<Int> {
    // SAFETY: We only append to the buffer
    return appendToString(buf, { buf1 -> this.readToEnd(buf1) })
}

/**
 * BufRead is implemented for `VecDeque<UByte>` by reading bytes from the front of the `VecDeque`.
 */
/**
 * Returns the contents of the "front" slice as returned by
 * [`asSlices`][VecDeque.asSlices]. If the contained byte slices of the `VecDeque` are
 * discontiguous, multiple calls to `fillBuf` will be needed to read the entire content.
 */
public fun <A : Allocator> VecDeque<UByte, A>.fillBuf(): Result<List<UByte>> {
    val (front, _) = this.asSlices()
    return RustResult.Ok(front)
}

public fun <A : Allocator> VecDeque<UByte, A>.consume(amt: Int) {
    this.drain(0 until amt)
}

/**
 * Write is implemented for `VecDeque<UByte>` by appending to the `VecDeque`, growing it as needed.
 */
public fun <A : Allocator> VecDeque<UByte, A>.write(buf: List<UByte>): Result<Int> {
    this.extend(buf)
    return RustResult.Ok(buf.size)
}

public fun <A : Allocator> VecDeque<UByte, A>.writeVectored(bufs: List<IoSlice>): Result<Int> {
    val len = bufs.map({ b -> b.len() }).sum()
    this.reserve(len)
    for (buf in bufs) {
        this.extend(buf)
    }
    return RustResult.Ok(len)
}

public fun <A : Allocator> VecDeque<UByte, A>.isWriteVectored(): Boolean = true

public fun <A : Allocator> VecDeque<UByte, A>.writeAll(buf: List<UByte>): Result<Unit> {
    this.extend(buf)
    return RustResult.Ok(Unit)
}

public fun <A : Allocator> VecDeque<UByte, A>.writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit> {
    when (val tryResult = this.writeVectored(bufs)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, io.github.kotlinmania.rustlang.core.io.error.Error>(tryResult.errValue()) }
    return RustResult.Ok(Unit)
}

public fun <A : Allocator> VecDeque<UByte, A>.flush(): Result<Unit> = RustResult.Ok(Unit)
