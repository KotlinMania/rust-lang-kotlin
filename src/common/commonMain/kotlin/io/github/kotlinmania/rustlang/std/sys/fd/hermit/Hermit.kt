// port-lint: source sys/fd/hermit.rs
package io.github.kotlinmania.rustlang.std.sys.fd.hermit

import io.github.kotlinmania.hermitabi.IOV_MAX
import io.github.kotlinmania.hermitabi.SEEK_CUR
import io.github.kotlinmania.hermitabi.SEEK_END
import io.github.kotlinmania.hermitabi.SEEK_SET
import io.github.kotlinmania.hermitabi.lseek
import io.github.kotlinmania.hermitabi.readv
import io.github.kotlinmania.hermitabi.writev
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
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
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported

public fun maxIov(): Int = IOV_MAX

public open class FileDesc(
    public var fd: OwnedFd,
) : Read, IntoInner<OwnedFd>, FromInner<OwnedFd, FileDesc>, FromRawFd<FileDesc, Any?, Any>, AsInner<OwnedFd>, AsFd, AsRawFd, IntoRawFd {
    public open fun read(buf: MutableList<UByte>): Result<Int> {
        val result = when (val tryResult = cvt(io.github.kotlinmania.hermitabi.read(this.fd.asRawFd(), buf.asMutPtr(), buf.size))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(result.toInt())
    }

    public open fun readBuf(buf: BorrowedCursor<UByte>): Result<Unit> {
        // SAFETY: The `read` syscall does not read from the buffer, so it is
        // safe to use `[MaybeUninit<UByte>]`.
        val result = when (val tryResult = cvt(read(this.fd.asRawFd(), mutPtrFromRustCast(buf.asMut().asMutPtr()), buf.capacity()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        // SAFETY: Exactly `result` bytes have been filled.
        buf.advance(result.toInt())
        return Result.Ok(Unit)
    }

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> {
        val ret = when (val tryResult = cvt(readv(this.asRawFd(), ((bufs.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(bufs.size, maxIov())))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(ret.toInt())
    }

    public open fun isReadVectored(): Boolean = true

    public open fun readToEnd(buf: List<UByte>): Result<Int> {
        val me = this
        return (me).readToEnd(buf)
    }

    public open fun write(buf: List<UByte>): Result<Int> {
        val result = when (val tryResult = cvt(io.github.kotlinmania.hermitabi.write(this.fd.asRawFd(), buf.asPtr(), buf.size))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(result.toInt())
    }

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> {
        val ret = when (val tryResult = cvt(writev(this.asRawFd(), (bufs.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, minOf(bufs.size, maxIov())))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(ret.toInt())
    }

    public open fun isWriteVectored(): Boolean = true

    public open fun seek(pos: SeekFrom): Result<ULong> {
        val (whence, pos1) =
            when (pos) {
                // Casting to `Long` is fine, too large values will end up as
                // negative which will cause an error in `lseek`.
                is SeekFrom.Start -> {
                    val (off) = pos
                    Pair(SEEK_SET, off.toLong())
                }
                is SeekFrom.End -> {
                    val (off1) = pos
                    Pair(SEEK_END, off1)
                }
                is SeekFrom.Current -> {
                    val (off2) = pos
                    Pair(SEEK_CUR, off2)
                }
            }
        val n = when (val tryResult = cvt(lseek(this.asRawFd(), pos1.toInt(), whence))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(n.toULong())
    }

    public open fun tell(): Result<ULong> = this.seek(SeekFrom.Current(0))

    public open fun duplicate(): Result<FileDesc> = this.duplicatePath(listOf())

    public open fun duplicatePath(path: List<UByte>): Result<FileDesc> = unsupported()

    public open fun nonblocking(): Result<Boolean> = Result.Ok(false)

    public open fun setCloexec(): Result<Unit> = unsupported()

    public open fun setNonblocking(nonblocking: Boolean): Result<Unit> = unsupported()

    public open fun fstat(stat: MutPtr): Result<Unit> {
        when (val tryResult = cvt(io.github.kotlinmania.hermitabi.fstat(this.fd.asRawFd(), stat))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public override fun intoInner(): OwnedFd = this.fd

    public override fun asInner(): OwnedFd = this.fd

    public override fun asFd(): BorrowedFd = this.fd.asFd()

    public override fun asRawFd(): RawFd = this.fd.asRawFd()

    public override fun intoRawFd(): RawFd = this.fd.intoRawFd()

    public companion object {
        public fun fromInner(ownedFd: OwnedFd): FileDesc = FileDesc(fd = ownedFd)

        public fun fromRawFd(rawFd: RawFd): FileDesc {
            val fd = OwnedFd.fromRawFd(rawFd)
            return FileDesc(fd = fd)
        }
    }
}
