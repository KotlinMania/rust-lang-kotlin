// port-lint: source sys/fd/unix.rs
package io.github.kotlinmania.rustlang.std.sys.fd.unix

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.fuchsia.FIOCLEX
import io.github.kotlinmania.libc.fuchsia.FIONBIO
import io.github.kotlinmania.libc.unix.aix.IOV_MAX
import io.github.kotlinmania.libc.unix.linuxlike.android.Ioctl
import io.github.kotlinmania.libc.unix.linuxlike.emscripten.pread64
import io.github.kotlinmania.libc.unix.linuxlike.emscripten.pwrite64
import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.libc.preadv
import io.github.kotlinmania.rustlang.libc.pwritev
import io.github.kotlinmania.rustlang.libc.read
import io.github.kotlinmania.rustlang.libc.readv
import io.github.kotlinmania.rustlang.libc.writev
import io.github.kotlinmania.rustlang.std.io.error.Error
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
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt

public open class FileDesc(
    public var value: OwnedFd,
) : Read, AsInner<OwnedFd>, IntoInner<OwnedFd>, FromInner<OwnedFd, FileDesc>, AsFd, AsRawFd, IntoRawFd, FromRawFd<FileDesc> {
    public var field0: OwnedFd
        get() = value
        set(v) {
            value = v
        }

    public open fun tryClone(): Result<FileDesc, Error> = this.duplicate()

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> {
        val ret = when (val tryResult = cvt(io.github.kotlinmania.rustlang.libc.read(this.asRawFd(), (buf.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(buf.size, READ_LIMIT)))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Int, Error>(tryResult.error) }
        return Result.Ok<Int, Error>(ret.toInt())
    }

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> {
        val ret = when (val tryResult = cvt(readv(this.asRawFd(), ((bufs.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(bufs.size, maxIov()).toInt()))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Int, Error>(tryResult.error) }
        return Result.Ok<Int, Error>(ret.toInt())
    }

    public open fun isReadVectored(): Boolean = true

    public open fun readToEnd(buf: List<UByte>): Result<Int, Error> {
        val me = this
        return (me).readToEnd(buf)
    }

    public open fun readAt(buf: MutableList<UByte>, offset: ULong): Result<Int, Error> = cvt(pread64(this.asRawFd(), (buf.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(buf.size, READ_LIMIT), offset.toLong())).map({ n -> n.toInt() })

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit, Error> {
        // SAFETY: `cursor.asMut()` starts with `cursor.capacity()` writable bytes
        val ret = when (val tryResult = cvt(read(this.asRawFd(), cursor.asMut().asMutPtr().cast(), minOf(cursor.capacity(), READ_LIMIT)))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
        // SAFETY: `ret` bytes were written to the initialized portion of the buffer
        run {
            cursor.advance(ret.toInt())
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun readBufAt(cursor: BorrowedCursor<UByte>, offset: ULong): Result<Unit, Error> {
        // SAFETY: `cursor.asMut()` starts with `cursor.capacity()` writable bytes
        val ret = when (val tryResult = cvt(pread64(this.asRawFd(), cursor.asMut().asMutPtr().cast(), minOf(cursor.capacity(), READ_LIMIT), offset.toLong()))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
        // SAFETY: `ret` bytes were written to the initialized portion of the buffer
        run {
            cursor.advance(ret.toInt())
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun readVectoredAt(bufs: MutableList<IoSliceMut>, offset: ULong): Result<Int, Error> {
        val ret = when (val tryResult = cvt(preadv(this.asRawFd(), ((bufs.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(bufs.size, maxIov()).toInt(), ((offset as Any?) as Result<Int, Error>)))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Int, Error>(tryResult.error) }
        return Result.Ok<Int, Error>(ret.toInt())
    }

    public open fun write(buf: List<UByte>): Result<Int, Error> {
        val ret = when (val tryResult = cvt(io.github.kotlinmania.rustlang.libc.write(this.asRawFd(), (buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(buf.size, READ_LIMIT)))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Int, Error>(tryResult.error) }
        return Result.Ok<Int, Error>(ret.toInt())
    }

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> {
        val ret = when (val tryResult = cvt(writev(this.asRawFd(), (bufs.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(bufs.size, maxIov()).toInt()))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Int, Error>(tryResult.error) }
        return Result.Ok<Int, Error>(ret.toInt())
    }

    public open fun isWriteVectored(): Boolean = true

    public open fun writeAt(buf: List<UByte>, offset: ULong): Result<Int, Error> = cvt(pwrite64(this.asRawFd(), (buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(buf.size, READ_LIMIT), offset.toLong())).map({ n -> n.toInt() })

    public open fun writeVectoredAt(bufs: List<IoSlice>, offset: ULong): Result<Int, Error> {
        val ret = when (val tryResult = cvt(pwritev(this.asRawFd(), (bufs.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(bufs.size, maxIov()).toInt(), ((offset as Any?) as Result<Int, Error>)))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Int, Error>(tryResult.error) }
        return Result.Ok<Int, Error>(ret.toInt())
    }

    public open fun setCloexec(): Result<Unit, Error> {
        return run {
            when (val tryResult = cvt(Ioctl(this.asRawFd(), FIOCLEX))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
            Result.Ok<Unit, Error>(Unit)
        }
    }

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit, Error> {
        return run {
            when (val tryResult = cvt(Ioctl(this.asRawFd(), FIONBIO, v))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
            Result.Ok<Unit, Error>(Unit)
        }
    }

    public open fun duplicate(): Result<FileDesc, Error> {
        return Result.Ok<FileDesc, Error>(FileDesc(when (val tryResult = this.value.tryClone()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<FileDesc, Error>(tryResult.error) }))
    }

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

// The maximum read limit on most POSIX-like systems is `SSIZE_MAX`,
// with the man page quoting that if the count of bytes to read is
// greater than `SSIZE_MAX` the result is "unspecified".
//
// On Apple targets however, apparently the 64-bit libc is either buggy or
// intentionally showing odd behavior by rejecting any read with a size
// larger than INT_MAX.
//
// Meanwhile on QNX, reads/writes/sends larger than INT_MAX return the wrong
// number of bytes written (eg, writing 2^31 bytes returns (2^64 - 2^31) instead
// of the correct byte count).
public val READ_LIMIT: Int = CInt.MAX_VALUE.toInt()

public fun maxIov(): Int = IOV_MAX.toInt()
