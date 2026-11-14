// port-lint: source sys/fd/motor.rs
package io.github.kotlinmania.rustlang.std.sys.fd.motor

import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadBuf
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fs.unix.runtimesymbols.read
import io.github.kotlinmania.rustlang.std.sys.pal.motor.ioSlices
import io.github.kotlinmania.rustlang.std.sys.pal.motor.ioSlicesMut
import io.github.kotlinmania.rustlang.std.sys.pal.motor.mapMotorError

public open class FileDesc(
    public var value: OwnedFd,
) : Read, AsInner<OwnedFd>, IntoInner<OwnedFd>, FromInner<OwnedFd, FileDesc>, AsFd, AsRawFd, IntoRawFd, FromRawFd<FileDesc, Any?, Any> {
    public var field0: OwnedFd
        get() = value
        set(v) {
            value = v
        }

    public open fun read(buf: MutableList<UByte>): Result<Int> = io.github.kotlinmania.motort.fs.read(this.asRawFd(), buf).mapErr(mapMotorError)

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = defaultReadBuf({ buf -> this.read(buf) }, cursor)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = io.github.kotlinmania.motort.fs.readVectored(this.asRawFd(), ioSlicesMut(bufs)).mapErr(mapMotorError)

    public open fun readToEnd(buf: List<UByte>): Result<Int> {
        val me = this
        return (me).readToEnd(buf)
    }

    public open fun write(buf: List<UByte>): Result<Int> = io.github.kotlinmania.motort.fs.write(this.asRawFd(), buf).mapErr(mapMotorError)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = io.github.kotlinmania.motort.fs.writeVectored(this.asRawFd(), ioSlices(bufs)).mapErr(mapMotorError)

    public open fun isWriteVectored(): Boolean = true

    public open fun isReadVectored(): Boolean = true

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = io.github.kotlinmania.motort.net.setNonblocking(this.asRawFd(), nonblocking).mapErr(mapMotorError)

    public open fun duplicate(): Result<FileDesc> {
        val fd = when (val tryResult = io.github.kotlinmania.motort.fs.duplicate(this.asRawFd()).mapErr(mapMotorError)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        // SAFETY: safe because we just got it from the OS runtime.
        return Result.Ok(FileDesc.fromRawFd(fd))
    }

    public open fun tryClone(): Result<FileDesc> = this.duplicate()

    public override fun asInner(): OwnedFd = this.value

    public override fun intoInner(): OwnedFd = this.value

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asRawFd(): RawFd = this.value.asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoRawFd()

    public companion object {
        public fun fromInner(ownedFd: OwnedFd): FileDesc = FileDesc(ownedFd)

        public fun fromRawFd(rawFd: RawFd): FileDesc = FileDesc(FromRawFd.fromRawFd(rawFd))
    }
}
