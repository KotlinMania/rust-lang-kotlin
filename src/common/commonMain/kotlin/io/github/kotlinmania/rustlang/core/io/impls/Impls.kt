// port-lint: source io/impls.rs
package io.github.kotlinmania.rustlang.core.io.impls

import io.github.kotlinmania.rustlang.core.io.error.WRITE_ALL_EOF
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.mem.take
import io.github.kotlinmania.rustlang.core.result.Result

// =============================================================================
// Forwarding implementations
public fun <T> T.lowerBound(): Int = this.lowerBound()

public fun <T> T.upperBound(): Int? = this.upperBound()

// =============================================================================
// In-memory buffer implementations
public fun List<UByte>.lowerBound(): Int = this.size

public fun List<UByte>.upperBound(): Int? = this.size

/**
 * Write is implemented for `[UByte]` by copying into the slice, overwriting
 * its data.
 *
 * Note that writing updates the slice to point to the yet unwritten part.
 * The slice will be empty when it has been completely overwritten.
 *
 * If the number of bytes to be written exceeds the size of the slice, write operations will
 * return short writes: ultimately, `Ok(0)`; in this situation, `writeAll` returns an error of
 * kind `ErrorKind.WriteZero`.
 */
public fun MutableList<UByte>.write(data: List<UByte>): Result<Int> {
    val amt = minOf(data.size, this.size)
    val (a, b) = take(this).splitAtMut(amt)
    a.copyFromSlice(((data).slice(0 until amt)))
    this = b
    return Result.Ok(amt)
}

public fun MutableList<UByte>.writeVectored(bufs: List<IoSlice>): Result<Int> {
    var nwritten = 0
    for (buf in bufs) {
        nwritten += when (val tryResult = this.write(buf)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        if (this.isEmpty()) {
            break
        }
    }
    return Result.Ok(nwritten)
}

public fun MutableList<UByte>.isWriteVectored(): Boolean = true

public fun MutableList<UByte>.writeAll(data: List<UByte>): Result<Unit> {
    return if (when (val tryResult = this.write(data)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } < data.size) {
        Result.Err(WRITE_ALL_EOF)
    } else {
        Result.Ok(Unit)
    }
}

public fun MutableList<UByte>.writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit> {
    for (buf in bufs) {
        if (when (val tryResult = this.write(buf)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } < buf.len()) {
            return Result.Err(WRITE_ALL_EOF)
        }
    }
    return Result.Ok(Unit)
}

public fun MutableList<UByte>.flush(): Result<Unit> = Result.Ok(Unit)

public fun BorrowedCursor<UByte>.write(buf: List<UByte>): Result<Int> {
    val amt = minOf(buf.size, this.capacity())
    this.append(((buf).slice(0 until amt)))
    return Result.Ok(amt)
}

public fun BorrowedCursor<UByte>.writeVectored(bufs: List<IoSlice>): Result<Int> {
    var nwritten = 0
    for (buf in bufs) {
        val n = when (val tryResult = this.write(buf)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        nwritten += n
        if (n < buf.len()) {
            break
        }
    }
    return Result.Ok(nwritten)
}

public fun BorrowedCursor<UByte>.isWriteVectored(): Boolean = true

public fun BorrowedCursor<UByte>.writeAll(buf: List<UByte>): Result<Unit> {
    return if (when (val tryResult = this.write(buf)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } < buf.size) {
        Result.Err(WRITE_ALL_EOF)
    } else {
        Result.Ok(Unit)
    }
}

public fun BorrowedCursor<UByte>.writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit> {
    for (buf in bufs) {
        if (when (val tryResult = this.write(buf)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } < buf.len()) {
            return Result.Err(WRITE_ALL_EOF)
        }
    }
    return Result.Ok(Unit)
}

public fun BorrowedCursor<UByte>.flush(): Result<Unit> = Result.Ok(Unit)
