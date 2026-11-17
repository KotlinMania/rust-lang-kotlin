// port-lint: source io/cursor.rs
package io.github.kotlinmania.rustlang.alloc.io.cursor

import io.github.kotlinmania.rustlang.alloc.io.constError
import io.github.kotlinmania.rustlang.core.alloc.Allocator
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopy
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.cursor.Cursor
import io.github.kotlinmania.rustlang.core.io.cursor.sliceWrite
import io.github.kotlinmania.rustlang.core.io.cursor.sliceWriteAll
import io.github.kotlinmania.rustlang.core.io.cursor.sliceWriteAllVectored
import io.github.kotlinmania.rustlang.core.io.cursor.sliceWriteVectored
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.num.saturatingAdd
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.mutptr.asRef
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.str.converts.fromUtf8
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

public fun Cursor<T>.read(buf: MutableList<UByte>): Result<Int> {
    val n = when (val tryResult = (Cursor.split(this).second).read(buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
    this.setPosition(this.position() + n.toULong())
    return RustResult.Ok(n)
}

public fun Cursor<T>.readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> {
    val prevWritten = cursor.written()
    when (val tryResult = (Cursor.split(this).second).readBuf(cursor.reborrow())) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    this.setPosition(this.position() + (cursor.written() - prevWritten).toULong())
    return RustResult.Ok(Unit)
}

public fun Cursor<T>.readVectored(bufs: MutableList<IoSliceMut>): Result<Int> {
    var nread = 0
    for (buf in bufs) {
        val n = when (val tryResult = this.read(buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
        nread += n
        if (n < buf.len()) {
            break
        }
    }
    return RustResult.Ok(nread)
}

public fun Cursor<T>.isReadVectored(): Boolean = true

public fun Cursor<T>.readExact(buf: MutableList<UByte>): Result<Unit> {
    val result = (Cursor.split(this).second).readExact(buf)
    when (result) { is RustResult.Ok<*, *> -> {
        this.setPosition(this.position() + buf.size.toULong())
    }; is RustResult.Err<*, *> -> {
        this.setPosition(this.getRef().asRef().len().toULong())
    } }
    return result
}

public fun Cursor<T>.readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit> {
    val prevWritten = cursor.written()
    val result = (Cursor.split(this).second).readBufExact(cursor.reborrow())
    this.setPosition(this.position() + (cursor.written() - prevWritten).toULong())
    return result
}

public fun Cursor<T>.readToEnd(buf: List<UByte>): Result<Int> {
    val content = Cursor.split(this).second
    val len = content.len()
    when (val tryResult = buf.tryReserve(len)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
    when (val tryResult = buf.tryExtendFromSliceOfBytes(content)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
    this.setPosition(this.position() + len.toULong())
    return RustResult.Ok(len)
}

public fun Cursor<T>.readToString(buf: String): Result<Int> {
    val content = when (val tryResult = fromUtf8(Cursor.split(this).second).mapErr({ Error.INVALID_UTF8 })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
    val len = content.len()
    when (val tryResult = buf.tryReserve(len)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
    when (val tryResult = buf.tryPushStr(content)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
    this.setPosition(this.position() + len.toULong())
    return RustResult.Ok(len)
}

public fun Cursor<T>.fillBuf(): Result<List<UByte>> = RustResult.Ok(Cursor.split(this).second)

public fun Cursor<T>.consume(amt: Int) {
    this.setPosition(this.position() + amt.toULong())
}

/**
 * Reserves the required space, and pads the vec with 0s if necessary.
 */
public fun <A : Allocator> reserveAndPad(posMut: RustMutableReference<ULong>, vec: List<UByte>, bufLen: Int): Result<Int> {
    val pos: Int =
        when (val tryResult = (posMut).read().tryInto().mapErr({
            constError(ErrorKind.InvalidInput, "cursor position exceeds maximum possible vector length")
        })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
    // For safety reasons, we don't want these numbers to overflow
    // otherwise our allocation won't be enough
    val desiredCap = pos.saturatingAdd(bufLen)
    if (desiredCap > vec.capacity()) {
        when (val tryResult = vec.tryReserve(desiredCap - vec.size)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
    }
    // Pad if pos is above the current len.
    if (pos > vec.size) {
        val diff = pos - vec.size
        // Unfortunately, `resize()` would suffice but the optimiser does not
        // realise the `reserve` it does can be eliminated. So we do it manually
        // to eliminate that extra branch
        val spare = vec.spareCapacityMut()
        check(spare.len() >= diff)
        // SAFETY: we have allocated enough capacity for this.
        // And we are only writing, not reading
        run {
            spare.getUncheckedMut(0 until diff).fill(MaybeUninit.new(0, UnresolvedTypeLayout<Result<Int>>()))
            vec.setLen(pos)
        }
    }
    return RustResult.Ok(pos)
}

/**
 * Writes the slice to the vec without allocating.
 *
 * # Safety
 *
 * `vec` must have `buf.len()` spare capacity.
 */
public fun <A> vecWriteAllUnchecked(pos: Int, vec: List<UByte>, buf: List<UByte>): Int where A : Allocator {
    check(vec.capacity() >= pos + buf.size)
    // SAFETY: Upheld by caller.
    kmCopy((buf.asPtr()).rawAddress, (MutPtr((vec.asMutPtr()).rawAddress + ((pos).toLong() * (PrimitiveLayouts.U8).strideBytes))).rawAddress, (buf.size).toLong() * (PrimitiveLayouts.U8).sizeBytes)
    return pos + buf.size
}

/**
 * Resizing `writeAll` implementation for [Cursor].
 *
 * Cursor is allowed to have a pre-allocated and initialised
 * vector body, but with a position of 0. This means the [Write]
 * will overwrite the contents of the vec.
 *
 * This also allows for the vec body to be empty, but with a position of N.
 * This means that [Write] will pad the vec with 0 initially,
 * before writing anything from that point
 */
public fun <A> vecWriteAll(posMut: RustMutableReference<ULong>, vec: List<UByte>, buf: List<UByte>): Result<Int> where A : Allocator {
    val posMut1 = posMut
    val bufLen = buf.size
    var pos = when (val tryResult = reserveAndPad(posMut1, vec, bufLen)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
    // Write the buf then progress the vec forward if necessary
    // SAFETY: we have ensured that the capacity is available
    // and that all bytes get written up to pos
    run {
        pos = vecWriteAllUnchecked(pos, vec, buf)
        if (pos > vec.size) {
            vec.setLen(pos)
        }
    }
    // Bump us forward
    (posMut1).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (bufLen.toULong()); targetPlace.write(currentValue) }
    return RustResult.Ok(bufLen)
}

/**
 * Resizing `writeAllVectored` implementation for [Cursor].
 *
 * Cursor is allowed to have a pre-allocated and initialised
 * vector body, but with a position of 0. This means the [Write]
 * will overwrite the contents of the vec.
 *
 * This also allows for the vec body to be empty, but with a position of N.
 * This means that [Write] will pad the vec with 0 initially,
 * before writing anything from that point
 */
public fun <A> vecWriteAllVectored(posMut: RustMutableReference<ULong>, vec: List<UByte>, bufs: List<IoSlice>): Result<Int> where A : Allocator {
    val posMut1 = posMut
    // For safety reasons, we don't want this sum to overflow ever.
    // If this saturates, the reserve should panic to avoid any unsound writing.
    val bufLen = bufs.fold(0, { a, b -> a.saturatingAdd(b.len()) })
    var pos = when (val tryResult = reserveAndPad(posMut1, vec, bufLen)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Int, Error>(tryResult.errValue()) }
    // Write the buf then progress the vec forward if necessary.
    // SAFETY: We have ensured that the capacity is available
    // and that all bytes get written up to the last pos
    run {
        for (buf in bufs) {
            pos = vecWriteAllUnchecked(pos, vec, buf)
        }
        if (pos > vec.size) {
            vec.setLen(pos)
        }
    }
    // Bump us forward
    (posMut1).let { targetPlace -> var currentValue = targetPlace.read(); currentValue += (bufLen.toULong()); targetPlace.write(currentValue) }
    return RustResult.Ok(bufLen)
}

public fun <A> writeThroughCursorWriteVecUByteA(thisRef: Cursor<List<UByte>>, buf: List<UByte>): Result<Int> where A : Allocator {
    val (pos, inner) = thisRef.intoPartsMut()
    return vecWriteAll(pos, inner, buf)
}

public fun <A> writeThroughCursorWriteVectoredVecUByteA(thisRef: Cursor<List<UByte>>, bufs: List<IoSlice>): Result<Int> where A : Allocator {
    val (pos, inner) = thisRef.intoPartsMut()
    return vecWriteAllVectored(pos, inner, bufs)
}

public fun <A> writeThroughCursorIsWriteVectoredVecUByteA(thisRef: Cursor<List<UByte>>): Boolean where A : Allocator = true

public fun <A> writeThroughCursorWriteAllVecUByteA(thisRef: Cursor<List<UByte>>, buf: List<UByte>): Result<Unit> where A : Allocator {
    val (pos, inner) = thisRef.intoPartsMut()
    when (val tryResult = vecWriteAll(pos, inner, buf)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    return RustResult.Ok(Unit)
}

public fun <A> writeThroughCursorWriteAllVectoredVecUByteA(thisRef: Cursor<List<UByte>>, bufs: MutableList<IoSlice>): Result<Unit> where A : Allocator {
    val (pos, inner) = thisRef.intoPartsMut()
    when (val tryResult = vecWriteAllVectored(pos, inner, bufs)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    return RustResult.Ok(Unit)
}

public fun <A> writeThroughCursorFlushVecUByteA(thisRef: Cursor<List<UByte>>): Result<Unit> where A : Allocator = RustResult.Ok(Unit)

public fun <A> writeThroughCursorWriteBoxUByteSliceA(thisRef: Cursor<List<UByte>>, buf: List<UByte>): Result<Int> where A : Allocator {
    val (pos, inner) = thisRef.intoPartsMut()
    return sliceWrite(pos, inner, buf)
}

public fun <A> writeThroughCursorWriteVectoredBoxUByteSliceA(thisRef: Cursor<List<UByte>>, bufs: List<IoSlice>): Result<Int> where A : Allocator {
    val (pos, inner) = thisRef.intoPartsMut()
    return sliceWriteVectored(pos, inner, bufs)
}

public fun <A> writeThroughCursorIsWriteVectoredBoxUByteSliceA(thisRef: Cursor<List<UByte>>): Boolean where A : Allocator = true

public fun <A> writeThroughCursorWriteAllBoxUByteSliceA(thisRef: Cursor<List<UByte>>, buf: List<UByte>): Result<Unit> where A : Allocator {
    val (pos, inner) = thisRef.intoPartsMut()
    return sliceWriteAll(pos, inner, buf)
}

public fun <A> writeThroughCursorWriteAllVectoredBoxUByteSliceA(thisRef: Cursor<List<UByte>>, bufs: MutableList<IoSlice>): Result<Unit> where A : Allocator {
    val (pos, inner) = thisRef.intoPartsMut()
    return sliceWriteAllVectored(pos, inner, bufs)
}

public fun <A> writeThroughCursorFlushBoxUByteSliceA(thisRef: Cursor<List<UByte>>): Result<Unit> where A : Allocator = RustResult.Ok(Unit)
