// port-lint: source sys/pal/windows/handle.rs
package io.github.kotlinmania.rustlang.std.sys.pal.windows.handle

import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadVectored
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.io.write.defaultWriteVectored
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.io.ErrorKind
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.windows.io.AsHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.AsRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.BorrowedHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.FromRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.IntoRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.OwnedHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.RawHandle
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.c
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput

/**
 * An owned container for `HANDLE` object, closing them on Drop.
 *
 * All methods are inherited through a `Deref` impl to `RawHandle`
 */
public open class Handle(
    public var value: OwnedHandle,
) : AsInner<OwnedHandle>, IntoInner<OwnedHandle>, FromInner<OwnedHandle, Handle>, AsHandle, AsRawHandle, IntoRawHandle, FromRawHandle<Handle>, Read {
    public var field0: OwnedHandle
        get() = value
        set(v) {
            value = v
        }

    public override fun asInner(): OwnedHandle = this.value

    public override fun intoInner(): OwnedHandle = this.value

    public override fun asHandle(): BorrowedHandle = this.value.asHandle()

    public override fun asRawHandle(): RawHandle = this.value.asRawHandle()

    public override fun intoRawHandle(): RawHandle = this.value.intoRawHandle()

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> {
        val res = this.synchronousRead(buf.asMutPtr(), buf.size, null)
        return when {
            res is Result.Ok -> {
                val read = res.value
                Result.Ok<Int, Error>(read)
            }
            // The special treatment of BrokenPipe is to deal with Windows
            // pipe semantics, which yields this error when *reading* from
            // a pipe after the other end has closed; we interpret that as
            // EOF on the pipe.
            res is Result.Err && (res as Result.Err).error.kind() == ErrorKind.BrokenPipe -> {
                val e = res.error
                Result.Ok<Int, Error>(0)
            }
            else -> {
                val e1 = res.error
                Result.Err<Int, Error>(e1)
            }
        }
    }

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = defaultReadVectored({ buf -> this.read(buf) }, bufs)

    public open fun isReadVectored(): Boolean = false

    public open fun readAt(buf: MutableList<UByte>, offset: ULong): Result<Int, Error> {
        val res = this.synchronousRead(buf.asMutPtr(), buf.size, offset)
        return when {
            res is Result.Ok -> {
                val read = res.value
                Result.Ok<Int, Error>(read)
            }
            res is Result.Err && (res as Result.Err).error.rawOsError() == c.ERROR_HANDLE_EOF.toInt() -> {
                val e = res.error
                Result.Ok<Int, Error>(0)
            }
            else -> {
                val e1 = res.error
                Result.Err<Int, Error>(e1)
            }
        }
    }

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit, Error> {
        val res = this.synchronousRead(cursor.asMut().asMutPtr(), cursor.capacity(), null)
        return when {
            res is Result.Ok -> {
                val read = res.value
                // Safety: `read` bytes were written to the initialized portion of the buffer
                run {
                    cursor.advance(read)
                }
                Result.Ok<Unit, Error>(Unit)
            }
            // The special treatment of BrokenPipe is to deal with Windows
            // pipe semantics, which yields this error when *reading* from
            // a pipe after the other end has closed; we interpret that as
            // EOF on the pipe.
            res is Result.Err && (res as Result.Err).error.kind() == ErrorKind.BrokenPipe -> {
                val e = res.error
                Result.Ok<Unit, Error>(Unit)
            }
            else -> {
                val e1 = res.error
                Result.Err<Unit, Error>(e1)
            }
        }
    }

    public open fun readBufAt(cursor: BorrowedCursor<UByte>, offset: ULong): Result<Unit, Error> {
        // SAFETY: `cursor.asMut()` starts with `cursor.capacity()` writable bytes
        val read = when (val tryResult = this.synchronousRead(cursor.asMut().asMutPtr(), cursor.capacity(), offset)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
        // SAFETY: `read` bytes were written to the initialized portion of the buffer
        run {
            cursor.advance(read)
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun readToEnd(buf: List<UByte>): Result<Int, Error> {
        val me = this
        return Read.readToEnd(me, buf)
    }

    public open fun readOverlapped(buf: MutableList<MaybeUninit<UByte>>, overlapped: MutPtr): Result<Int?, Error> {
        // SAFETY: We have exclusive access to the buffer and it's up to the caller to
        // ensure the OVERLAPPED pointer is valid for the scope of this function.
        val (res1, amt1) =
            run {
                val len = minOf(buf.size, UInt.MAX_VALUE.toInt()).toUInt()
                val localStorageL163C12 = AddressableLocal(0, PrimitiveLayouts.I32)
                val amt by localStorageL163C12
                val res = cvt(c.ReadFile(this.asRawHandle(), MutPtr((buf.asMutPtr()).rawAddress), len, RustMutableReference(localStorageL163C12.asMutPtr(), localStorageL163C12.layout), overlapped))
                Pair(res, amt)
            }
        return when (res1) { is Result.Ok -> {
            Result.Ok<Int?, Error>(amt1.toInt())
        }; is Result.Err -> {
            val e = res1.error
            if (e.rawOsError() == c.ERROR_IO_PENDING.toInt()) {
                Result.Ok<Int?, Error>(null)
            } else if (e.rawOsError() == c.ERROR_BROKEN_PIPE.toInt()) {
                Result.Ok<Int?, Error>(0)
            } else {
                Result.Err<Int?, Error>(e)
            }
        } }
    }

    public open fun overlappedResult(overlapped: MutPtr, wait: Boolean): Result<Int, Error> =
        run {
            val localStorageL193C12 = AddressableLocal(0, PrimitiveLayouts.I32)
            val bytes by localStorageL193C12
            val wait1 =
                if (wait) {
                    c.TRUE
                } else {
                    c.FALSE
                }
            val res = cvt(c.GetOverlappedResult(this.asRawHandle(), overlapped, RustMutableReference(localStorageL193C12.asMutPtr(), localStorageL193C12.layout), wait1))
            when (res) { is Result.Ok -> {
                Result.Ok<Int, Error>(bytes)
            }; is Result.Err -> {
                val e = res.error
                if (e.rawOsError() == c.ERROR_HANDLE_EOF.toInt() || e.rawOsError() == c.ERROR_BROKEN_PIPE.toInt()) {
                    Result.Ok<Int, Error>(0)
                } else {
                    Result.Err<Int, Error>(e)
                }
            } }
        }

    public open fun cancelIo(): Result<Unit, Error> = cvt(c.CancelIo(this.asRawHandle())).map({ Unit })

    public open fun write(buf: List<UByte>): Result<Int, Error> = this.synchronousWrite(buf, null)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = defaultWriteVectored({ buf -> this.write(buf) }, bufs)

    public open fun isWriteVectored(): Boolean = false

    public open fun writeAt(buf: List<UByte>, offset: ULong): Result<Int, Error> = this.synchronousWrite(buf, offset)

    public open fun tryClone(): Result<Handle, Error> {
        return Result.Ok<Handle, Error>(Handle(when (val tryResult = this.value.tryClone()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Handle, Error>(tryResult.error) }))
    }

    public open fun duplicate(access: UInt, inherit: Boolean, options: UInt): Result<Handle, Error> {
        return Result.Ok<Handle, Error>(Handle(when (val tryResult = this.value.asHandle().duplicate(access, inherit, options)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Handle, Error>(tryResult.error) }))
    }

    /**
     * Performs a synchronous read.
     *
     * If the handle is opened for asynchronous I/O then this abort the process.
     * See #81357.
     *
     * If `offset` is `None` then the current file position is used.
     */
    public open fun synchronousRead(buf: MutPtr, len: Int, offset: ULong?): Result<Int, Error> {
        val ioStatus = c.IoStatusBlock.PENDING
        // The length is clamped at u32.Max.
        val len1 = minOf(len, UInt.MAX_VALUE.toInt()).toUInt()
        // SAFETY: It's up to the caller to ensure `buf` is writeable up to
        // the provided `len`.
        val status = c.NtReadFile(this.asRawHandle(), MutPtr(0L), null, MutPtr(0L), ioStatus, MutPtr((buf).rawAddress), len1, (offset?.let({ n -> n.cast() }) ?: ConstPtr(0L)), ConstPtr(0L))
        val status1 =
            if (status == c.STATUS_PENDING) {
                c.WaitForSingleObject(this.asRawHandle(), c.INFINITE)
                ioStatus.status()
            } else {
                status
            }
        return when {
            // If the operation has not completed then abort the process.
            // Doing otherwise means that the buffer and stack may be written to
            // after this function returns.
            status1 == c.STATUS_PENDING -> {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("I/O error: operation failed to complete synchronously").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("I/O error: operation failed to complete synchronously").toString() + ", aborting\n"
                }
                abort()
            }
            // Return `Ok(0)` when there's nothing more to read.
            status1 == c.STATUS_END_OF_FILE -> Result.Ok<Int, Error>(0)
            // Success!
            c.ntSuccess(status1) -> {
                val status2 = status1
                Result.Ok<Int, Error>(ioStatus.information)
            }
            else -> {
                val status3 = status1
                val error = c.RtlNtStatusToDosError(status3)
                Result.Err<Int, Error>(io.github.kotlinmania.rustlang.std.io.Error.fromRawOsError(((error as Any?) as Result<Int, Error>)))
            }
        }
    }

    /**
     * Performs a synchronous write.
     *
     * If the handle is opened for asynchronous I/O then this abort the process.
     * See #81357.
     *
     * If `offset` is `None` then the current file position is used.
     */
    public open fun synchronousWrite(buf: List<UByte>, offset: ULong?): Result<Int, Error> {
        val ioStatus = c.IoStatusBlock.PENDING
        // The length is clamped at u32.Max.
        val len = minOf(buf.size, UInt.MAX_VALUE.toInt()).toUInt()
        val status = c.NtWriteFile(this.asRawHandle(), MutPtr(0L), null, MutPtr(0L), ioStatus, ConstPtr((buf.asPtr()).rawAddress), len, (offset?.let({ n -> n.cast() }) ?: ConstPtr(0L)), ConstPtr(0L))
        val status1 =
            if (status == c.STATUS_PENDING) {
                c.WaitForSingleObject(this.asRawHandle(), c.INFINITE)
                ioStatus.status()
            } else {
                status
            }
        return when {
            // If the operation has not completed then abort the process.
            // Doing otherwise means that the buffer may be read and the stack
            // written to after this function returns.
            status1 == c.STATUS_PENDING -> {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("I/O error: operation failed to complete synchronously").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("I/O error: operation failed to complete synchronously").toString() + ", aborting\n"
                }
                abort()
            }
            // Success!
            c.ntSuccess(status1) -> {
                val status2 = status1
                Result.Ok<Int, Error>(ioStatus.information)
            }
            else -> {
                val status3 = status1
                val error = c.RtlNtStatusToDosError(status3)
                Result.Err<Int, Error>(io.github.kotlinmania.rustlang.std.io.Error.fromRawOsError(((error as Any?) as Result<Int, Error>)))
            }
        }
    }

    public companion object {
        public fun newEvent(manual: Boolean, init: Boolean): Result<Handle, Error> =
            run {
                val event = c.CreateEventW(MutPtr(0L), manual.toInt(), init.toInt(), ConstPtr(0L))
                if (event.isNull()) {
                    Result.Err<Handle, Error>(io.github.kotlinmania.rustlang.std.io.Error.lastOsError())
                } else {
                    Result.Ok<Handle, Error>(Handle.fromRawHandle(event))
                }
            }

        public fun fromInner(fileDesc: OwnedHandle): Handle = Handle(fileDesc)

        public fun fromRawHandle(rawHandle: RawHandle): Handle = Handle(FromRawHandle.fromRawHandle(rawHandle))
    }
}
