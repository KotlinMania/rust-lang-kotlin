// port-lint: source sys/stdio/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.stdio.unsupported

import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.len
import io.github.kotlinmania.rustlang.core.str.len

public open class Stdin : Read<Stdin> {
    public override fun read(buf: MutableList<UByte>): Result<Int, Error> = Result.Ok<Int, Error>(0)

    public override fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit, Error> = Result.Ok<Unit, Error>(Unit)

    public override fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = Result.Ok<Int, Error>(0)

    public override fun isReadVectored(): Boolean {
        // Do not force `Chain<Empty, T>` or `Chain<T, Empty>` to use vectored
        // reads, unless the other reader is vectored.
        return false
    }

    public override fun readExact(buf: MutableList<UByte>): Result<Unit, Error> =
        if (!buf.isEmpty()) {
            Result.Err<Unit, Error>(Error.READ_EXACT_EOF)
        } else {
            Result.Ok<Unit, Error>(Unit)
        }

    public override fun readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit, Error> =
        if (cursor.capacity() != 0) {
            Result.Err<Unit, Error>(Error.READ_EXACT_EOF)
        } else {
            Result.Ok<Unit, Error>(Unit)
        }

    public override fun readToEnd(buf: MutableList<UByte>): Result<Int, Error> = Result.Ok<Int, Error>(0)

    public override fun readToString(buf: String): Result<Int, Error> = Result.Ok<Int, Error>(0)

    public companion object {
        public fun new(): Stdin = Stdin()
    }
}

public open class Stdout : Write<Stdout> {
    public override fun write(buf: List<UByte>): Result<Int, Error> = Result.Ok<Int, Error>(buf.size)

    public override fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> {
        val totalLen = bufs.map({ b -> b.len() }).sum()
        return Result.Ok<Int, Error>(totalLen)
    }

    public override fun isWriteVectored(): Boolean = true

    public override fun writeAll(buf: List<UByte>): Result<Unit, Error> = Result.Ok<Unit, Error>(Unit)

    public override fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit, Error> = Result.Ok<Unit, Error>(Unit)

    // Keep the default writeFmt so the `fmt.Arguments` are still evaluated.
    public override fun flush(): Result<Unit, Error> = Result.Ok<Unit, Error>(Unit)

    public companion object {
        public fun new(): Stdout = Stdout()
    }
}

public typealias Stderr = Stdout

public val STDIN_BUF_SIZE: Int = 0

public fun isEbadf(err: Error): Boolean = true

public fun panicOutput(): List<UByte>? = null
