// port-lint: source sys/stdio/unix.rs
package io.github.kotlinmania.rustlang.std.sys.stdio.unix

import io.github.kotlinmania.libc.fuchsia.EBADF
import io.github.kotlinmania.libc.hermit.STDERR_FILENO
import io.github.kotlinmania.libc.hermit.STDIN_FILENO
import io.github.kotlinmania.libc.hermit.STDOUT_FILENO
import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.std.sys.io.DEFAULT_BUF_SIZE

public open class Stdin : Read {
    public open fun read(buf: MutableList<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = FileDesc.fromRawFd(STDIN_FILENO).read(buf)

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = FileDesc.fromRawFd(STDIN_FILENO).readBuf(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = FileDesc.fromRawFd(STDIN_FILENO).readVectored(bufs)

    public open fun isReadVectored(): Boolean = true

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
    public open fun write(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = FileDesc.fromRawFd(STDOUT_FILENO).write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = FileDesc.fromRawFd(STDOUT_FILENO).writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = true

    public open fun flush(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok(Unit)

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

public open class Stderr : Write {
    public open fun write(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = FileDesc.fromRawFd(STDERR_FILENO).write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = FileDesc.fromRawFd(STDERR_FILENO).writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = true

    public open fun flush(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Ok(Unit)

    public companion object {
        public fun typeLayout(): TypeLayout<Stderr> =
            object : TypeLayout<Stderr> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): Stderr =
                    Stderr()
                override fun writeNative(addr: Long, value: Stderr) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): Stderr =
                    Stderr()
                override fun writeBytes(buf: ByteArray, offset: Int, value: Stderr) {
                }
            }

        public fun new(): Stderr = Stderr()
    }
}

public fun isEbadf(err: Error): Boolean = (err.rawOsError() == EBADF.toInt()).toUByte()

public val STDIN_BUF_SIZE: Int = DEFAULT_BUF_SIZE

public fun panicOutput(): Write? = Stderr.new()
