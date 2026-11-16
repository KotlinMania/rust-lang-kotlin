// port-lint: source sys/pipe/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.pipe.unsupported

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd

public open class Pipe(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun tryClone(): Result<Pipe, Error> = this.value

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> = this.value

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit, Error> = this.value

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = this.value

    public open fun isReadVectored(): Boolean = this.value

    public open fun readToEnd(buf: List<UByte>): Result<Int, Error> = this.value

    public open fun write(buf: List<UByte>): Result<Int, Error> = this.value

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = this.value

    public open fun isWriteVectored(): Boolean = this.value

    public open fun diverge(): Nothing = this.value

    public open fun fmt(arg0: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.value
}

public fun pipe(): Result<Pair<Pipe, Pipe>, Error> = Result.Err<Pair<Pipe, Pipe>, Error>(io.github.kotlinmania.rustlang.std.io.Error.UNSUPPORTED_PLATFORM)

public fun Pipe.asRawFd(): RawFd = this.value

public fun Pipe.asFd(): BorrowedFd = this.value

public fun Pipe.intoRawFd(): RawFd = this.value

public fun fromRawFdPipe(arg0: RawFd): Pipe {
    error("creating pipe on this platform is unsupported!")
}

public fun fromInnerPipe(arg0: OwnedFd): Pipe {
    error("creating pipe on this platform is unsupported!")
}

public fun Pipe.intoInner(): OwnedFd = this.value
