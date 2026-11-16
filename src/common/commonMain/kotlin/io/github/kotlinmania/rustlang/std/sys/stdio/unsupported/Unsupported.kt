// port-lint: source sys/stdio/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.stdio.unsupported

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.Error
import io.github.kotlinmania.rustlang.std.io.Read
import io.github.kotlinmania.rustlang.std.io.Write

public open class Stdin : Read {
    public open fun read(buf: MutableList<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok<Int, io.github.kotlinmania.rustlang.std.io.error.Error>(0)

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(Unit)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok<Int, io.github.kotlinmania.rustlang.std.io.error.Error>(0)

    public open fun isReadVectored(): Boolean {
        // Do not force `Chain<Empty, T>` or `Chain<T, Empty>` to use vectored
        // reads, unless the other reader is vectored.
        return false
    }

    public open fun readExact(buf: MutableList<UByte>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> =
        if (!buf.isEmpty()) {
            Result.Err<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(Error.READ_EXACT_EOF)
        } else {
            Result.Ok<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(Unit)
        }

    public open fun readBufExact(cursor: BorrowedCursor<UByte>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> =
        if (cursor.capacity() != 0) {
            Result.Err<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(Error.READ_EXACT_EOF)
        } else {
            Result.Ok<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(Unit)
        }

    public open fun readToEnd(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok<Int, io.github.kotlinmania.rustlang.std.io.error.Error>(0)

    public open fun readToString(buf: String): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok<Int, io.github.kotlinmania.rustlang.std.io.error.Error>(0)

    public companion object {
        public fun typeLayout(): TypeLayout<Stdin> =
            object : TypeLayout<Stdin> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Stdin =
                    Stdin()
                override fun writeNative(addr: Long, value: Stdin) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stdin =
                    Stdin()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stdin) {
                }
            }

        public fun new(): Stdin = Stdin()
    }
}

public open class Stdout : Write {
    public open fun write(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok<Int, io.github.kotlinmania.rustlang.std.io.error.Error>(buf.size)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> {
        val totalLen = bufs.map({ b -> b.len() }).sum()
        return Result.Ok<Int, io.github.kotlinmania.rustlang.std.io.error.Error>(totalLen)
    }

    public open fun isWriteVectored(): Boolean = true

    public open fun writeAll(buf: List<UByte>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(Unit)

    public open fun writeAllVectored(bufs: MutableList<IoSlice>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(Unit)

    // Keep the default writeFmt so the `fmt.Arguments` are still evaluated.
    public open fun flush(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(Unit)

    public companion object {
        public fun typeLayout(): TypeLayout<Stdout> =
            object : TypeLayout<Stdout> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Stdout =
                    Stdout()
                override fun writeNative(addr: Long, value: Stdout) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stdout =
                    Stdout()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stdout) {
                }
            }

        public fun new(): Stdout = Stdout()
    }
}

public typealias Stderr = Stdout

public val STDIN_BUF_SIZE: Int = 0

public fun isEbadf(err: Error): Boolean = true

public fun panicOutput(): List<UByte>? = null
