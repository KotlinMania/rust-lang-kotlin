// port-lint: source os/wasi/fs.rs
package io.github.kotlinmania.rustlang.std.os.wasi.fs

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.wasi.S_IFBLK
import io.github.kotlinmania.rustlang.libc.wasi.S_IFCHR
import io.github.kotlinmania.rustlang.libc.wasi.S_IFSOCK
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.fs.DirEntry
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.fs.FileType
import io.github.kotlinmania.rustlang.std.fs.Metadata
import io.github.kotlinmania.rustlang.std.fs.OpenOptions
import io.github.kotlinmania.rustlang.std.io.ErrorKind
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.pal.wasi.err2io
import io.github.kotlinmania.rustlang.wasip1.ADVICE_DONTNEED
import io.github.kotlinmania.rustlang.wasip1.ADVICE_NOREUSE
import io.github.kotlinmania.rustlang.wasip1.ADVICE_NORMAL
import io.github.kotlinmania.rustlang.wasip1.ADVICE_RANDOM
import io.github.kotlinmania.rustlang.wasip1.ADVICE_SEQUENTIAL
import io.github.kotlinmania.rustlang.wasip1.ADVICE_WILLNEED
import io.github.kotlinmania.rustlang.wasip1.Fd
import io.github.kotlinmania.rustlang.wasip1.fdAdvise
import io.github.kotlinmania.rustlang.wasip1.fdAllocate
import io.github.kotlinmania.rustlang.wasip1.fdFdstatSetFlags
import io.github.kotlinmania.rustlang.wasip1.fdFdstatSetRights
import io.github.kotlinmania.rustlang.wasip1.pathCreateDirectory
import io.github.kotlinmania.rustlang.wasip1.pathLink
import io.github.kotlinmania.rustlang.wasip1.pathRemoveDirectory
import io.github.kotlinmania.rustlang.wasip1.pathRename
import io.github.kotlinmania.rustlang.wasip1.pathSymlink
import io.github.kotlinmania.rustlang.wasip1.pathUnlinkFile

// WASIp1-specific extensions to primitives in the [std.fs] module.

/**
 * WASI-specific extensions to [File].
 */
public interface FileExt {
    /**
     * Reads a number of bytes starting from a given offset.
     *
     * Returns the number of bytes read.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor.
     *
     * The current file cursor is not affected by this function.
     *
     * Note that similar to [File.read], it is not an error to return with a
     * short read.
     */
    public fun readAt(buf: MutableList<UByte>, offset: ULong): Result<Int, Error>

    /**
     * Reads a number of bytes starting from a given offset.
     *
     * Returns the number of bytes read.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor.
     *
     * The current file cursor is not affected by this function.
     *
     * Note that similar to [File.readVectored], it is not an error to
     * return with a short read.
     */
    public fun readVectoredAt(bufs: MutableList<IoSliceMut>, offset: ULong): Result<Int, Error>

    /**
     * Reads some bytes starting from a given offset into the buffer.
     *
     * This equivalent to the [`readAt`](FileExt.readAt) method, except that it is passed a
     * [BorrowedCursor] rather than `[UByte]` to allow use with uninitialized buffers. The new
     * data will be appended to any existing contents of `buf`.
     */
    public fun readBufAt(buf: BorrowedCursor<UByte>, offset: ULong): Result<Unit, Error>

    /**
     * Reads the exact number of byte required to fill `buf` from the given offset.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor.
     *
     * The current file cursor is not affected by this function.
     *
     * Similar to [Read.readExact] but uses [readAt] instead of `read`.
     *
     * [readAt]: FileExt.readAt
     *
     * # Errors
     *
     * If this function encounters an error of the kind
     * [io.ErrorKind.Interrupted] then the error is ignored and the operation
     * will continue.
     *
     * If this function encounters an "end of file" before completely filling
     * the buffer, it returns an error of the kind [io.ErrorKind.UnexpectedEof].
     * The contents of `buf` are unspecified in this case.
     *
     * If any other read error is encountered then this function immediately
     * returns. The contents of `buf` are unspecified in this case.
     *
     * If this function returns an error, it is unspecified how many bytes it
     * has read, but it will never read more than would be necessary to
     * completely fill the buffer.
     */
    public fun readExactAt(buf: MutableList<UByte>, offset: ULong): Result<Unit, Error> {
        var buf1 = buf
        var offset1 = offset
        while (!buf1.isEmpty()) {
            run {
                val matchVal = this.readAt(buf1, offset1)
                when {
                    matchVal is Result.Ok && matchVal.value == 0 -> { break }
                    matchVal is Result.Ok -> {
                        val n = matchVal.value
                        val tmp = buf1
                        buf1 = tmp.subList(n, tmp.size)
                        offset1 += n.toULong()
                    }
                    matchVal is Result.Err && (matchVal as Result.Err).error.isInterrupted() -> {
                        val e = matchVal.error
                    }
                    else -> {
                        val e1 = matchVal.error
                        return Result.Err<Unit, Error>(e1)
                    }
                }
            }
        }
        return if (!buf1.isEmpty()) {
            Result.Err<Unit, Error>(io.github.kotlinmania.rustlang.std.io.Error.READ_EXACT_EOF)
        } else {
            Result.Ok<Unit, Error>(Unit)
        }
    }

    /**
     * Writes a number of bytes starting from a given offset.
     *
     * Returns the number of bytes written.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor.
     *
     * The current file cursor is not affected by this function.
     *
     * When writing beyond the end of the file, the file is appropriately
     * extended and the intermediate bytes are initialized with the value 0.
     *
     * Note that similar to [File.write], it is not an error to return a
     * short write.
     */
    public fun writeAt(buf: List<UByte>, offset: ULong): Result<Int, Error>

    /**
     * Writes a number of bytes starting from a given offset.
     *
     * Returns the number of bytes written.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor.
     *
     * The current file cursor is not affected by this function.
     *
     * When writing beyond the end of the file, the file is appropriately
     * extended and the intermediate bytes are initialized with the value 0.
     *
     * Note that similar to [File.writeVectored], it is not an error to return a
     * short write.
     */
    public fun writeVectoredAt(bufs: List<IoSlice>, offset: ULong): Result<Int, Error>

    /**
     * Attempts to write an entire buffer starting from a given offset.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor.
     *
     * The current file cursor is not affected by this function.
     *
     * This method will continuously call [writeAt] until there is no more data
     * to be written or an error of non-[io.ErrorKind.Interrupted] kind is
     * returned. This method will not return until the entire buffer has been
     * successfully written or such an error occurs. The first error that is
     * not of [io.ErrorKind.Interrupted] kind generated from this method will be
     * returned.
     *
     * # Errors
     *
     * This function will return the first error of
     * non-[io.ErrorKind.Interrupted] kind that [writeAt] returns.
     *
     * [writeAt]: FileExt.writeAt
     */
    public fun writeAllAt(buf: List<UByte>, offset: ULong): Result<Unit, Error> {
        var buf1 = buf
        var offset1 = offset
        while (!buf1.isEmpty()) {
            run {
                val matchVal = this.writeAt(buf1, offset1)
                when {
                    matchVal is Result.Ok && matchVal.value == 0 -> {
                        return Result.Err<Unit, Error>(io.github.kotlinmania.rustlang.std.io.Error.WRITE_ALL_EOF)
                    }
                    matchVal is Result.Ok -> {
                        val n = matchVal.value
                        buf1 = ((buf1).subList(n, (buf1).size))
                        offset1 += n.toULong()
                    }
                    matchVal is Result.Err && (matchVal as Result.Err).error.isInterrupted() -> {
                        val e = matchVal.error
                    }
                    else -> {
                        val e1 = matchVal.error
                        return Result.Err<Unit, Error>(e1)
                    }
                }
            }
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    /**
     * Adjusts the flags associated with this file.
     *
     * This corresponds to the `fdFdstatSetFlags` syscall.
     */
    public fun fdstatSetFlags(flags: UShort): Result<Unit, Error>

    /**
     * Adjusts the rights associated with this file.
     *
     * This corresponds to the `fdFdstatSetRights` syscall.
     */
    public fun fdstatSetRights(rights: ULong, inheriting: ULong): Result<Unit, Error>

    /**
     * Provides file advisory information on a file descriptor.
     *
     * This corresponds to the `fdAdvise` syscall.
     */
    public fun advise(offset: ULong, len: ULong, advice: UByte): Result<Unit, Error>

    /**
     * Forces the allocation of space in a file.
     *
     * This corresponds to the `fdAllocate` syscall.
     */
    public fun allocate(offset: ULong, len: ULong): Result<Unit, Error>

    /**
     * Creates a directory.
     *
     * This corresponds to the `pathCreateDirectory` syscall.
     */
    public fun createDirectory(dir: Path): Result<Unit, Error>

    /**
     * Unlinks a file.
     *
     * This corresponds to the `pathUnlinkFile` syscall.
     */
    public fun removeFile(path: Path): Result<Unit, Error>

    /**
     * Removes a directory.
     *
     * This corresponds to the `pathRemoveDirectory` syscall.
     */
    public fun removeDirectory(path: Path): Result<Unit, Error>
}

// Note: bind fdFdstatGet - need to define a custom return type
// Note: bind fdReaddir - can't return `ReadDir` since we only have entry name
// Note: bind fdFilestatSetTimes maybe? - on crates.io for unix
// Note: bind pathFilestatSetTimes maybe? - on crates.io for unix
// Note: bind pollOneoff maybe? - probably should wait for I/O to settle
// Note: bind randomGet maybe? - on crates.io for unix
public fun File.readAt(buf: MutableList<UByte>, offset: ULong): Result<Int, Error> = this.asInner().readAt(buf, offset)

public fun File.readBufAt(buf: BorrowedCursor<UByte>, offset: ULong): Result<Unit, Error> = this.asInner().readBufAt(buf, offset)

public fun File.readVectoredAt(bufs: MutableList<IoSliceMut>, offset: ULong): Result<Int, Error> = this.asInner().readVectoredAt(bufs, offset)

public fun File.writeAt(buf: List<UByte>, offset: ULong): Result<Int, Error> = this.asInner().writeAt(buf, offset)

public fun File.writeVectoredAt(bufs: List<IoSlice>, offset: ULong): Result<Int, Error> = this.asInner().writeVectoredAt(bufs, offset)

public fun File.fdstatSetFlags(flags: UShort): Result<Unit, Error> = fdFdstatSetFlags(((this.asRawFd() as Any?) as Fd), flags).mapErr(err2io)

public fun File.fdstatSetRights(rights: ULong, inheriting: ULong): Result<Unit, Error> = fdFdstatSetRights(((this.asRawFd() as Any?) as Fd), rights, inheriting).mapErr(err2io)

public fun File.advise(offset: ULong, len: ULong, advice: UByte): Result<Unit, Error> {
    val advice1 =
        when {
            advice == ADVICE_NORMAL.raw() -> {
                val a = advice
                ADVICE_NORMAL
            }
            advice == ADVICE_SEQUENTIAL.raw() -> {
                val a1 = advice
                ADVICE_SEQUENTIAL
            }
            advice == ADVICE_RANDOM.raw() -> {
                val a2 = advice
                ADVICE_RANDOM
            }
            advice == ADVICE_WILLNEED.raw() -> {
                val a3 = advice
                ADVICE_WILLNEED
            }
            advice == ADVICE_DONTNEED.raw() -> {
                val a4 = advice
                ADVICE_DONTNEED
            }
            advice == ADVICE_NOREUSE.raw() -> {
                val a5 = advice
                ADVICE_NOREUSE
            }
            else -> {
                return Result.Err<Unit, Error>(constError(ErrorKind.InvalidInput, "invalid parameter 'advice'"))
            }
        }
    return fdAdvise(((this.asRawFd() as Any?) as Fd), offset, len, advice1).mapErr(err2io)
}

public fun File.allocate(offset: ULong, len: ULong): Result<Unit, Error> = fdAllocate(((this.asRawFd() as Any?) as Fd), offset, len).mapErr(err2io)

public fun File.createDirectory(dir: Path): Result<Unit, Error> {
    val path = when (val tryResult = osstr2str(dir)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
    return pathCreateDirectory(((this.asRawFd() as Any?) as Fd), path).mapErr(err2io)
}

public fun File.removeFile(path: Path): Result<Unit, Error> {
    val path1 = when (val tryResult = osstr2str(path)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
    return pathUnlinkFile(((this.asRawFd() as Any?) as Fd), path1).mapErr(err2io)
}

public fun File.removeDirectory(path: Path): Result<Unit, Error> {
    val path1 = when (val tryResult = osstr2str(path)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
    return pathRemoveDirectory(((this.asRawFd() as Any?) as Fd), path1).mapErr(err2io)
}

/**
 * WASI-specific extensions to [OpenOptions].
 */
public interface OpenOptionsExt<T> {
    /**
     * Pass custom flags to the `flags` argument of `open`.
     */
    public fun customFlags(flags: Int): T
}

public fun OpenOptions.customFlags(flags: Int): OpenOptions {
    this.asInnerMut().customFlags(flags)
    return this
}

/**
 * WASI-specific extensions to [fs.Metadata].
 */
public interface MetadataExt {
    /**
     * Returns the `stDev` field of the internal `filestatT`
     */
    public fun dev(): ULong

    /**
     * Returns the `stIno` field of the internal `filestatT`
     */
    public fun ino(): ULong

    /**
     * Returns the `stNlink` field of the internal `filestatT`
     */
    public fun nlink(): ULong
}

public fun Metadata.dev(): ULong = this.asInner().asInner().stDev

public fun Metadata.ino(): ULong = this.asInner().asInner().stIno

public fun Metadata.nlink(): ULong = this.asInner().asInner().stNlink

/**
 * WASI-specific extensions for [fs.FileType].
 *
 * Adds support for special WASI file types such as block/character devices,
 * pipes, and sockets.
 */
public interface FileTypeExt {
    /**
     * Returns `true` if this file type is a block device.
     */
    public fun isBlockDevice(): Boolean

    /**
     * Returns `true` if this file type is a character device.
     */
    public fun isCharDevice(): Boolean

    /**
     * Returns `true` if this file type is any type of socket.
     */
    public fun isSocket(): Boolean
}

public fun FileType.isBlockDevice(): Boolean = this.asInner().isMatch(S_IFBLK)

public fun FileType.isCharDevice(): Boolean = this.asInner().isMatch(S_IFCHR)

public fun FileType.isSocket(): Boolean = this.asInner().isMatch(S_IFSOCK)

/**
 * WASI-specific extension methods for [fs.DirEntry].
 */
public interface DirEntryExt {
    /**
     * Returns the underlying `dIno` field of the `direntT`
     */
    public fun ino(): ULong
}

public fun DirEntry.ino(): ULong = this.asInner().ino()

/**
 * Creates a hard link.
 *
 * This corresponds to the `pathLink` syscall.
 */
public fun link(oldFd: File, oldFlags: UInt, oldPath: Path, newFd: File, newPath: Path): Result<Unit, Error> {
    return pathLink(((oldFd.asRawFd() as Any?) as Fd), oldFlags, when (val tryResult = osstr2str(oldPath)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }, ((newFd.asRawFd() as Any?) as Fd), when (val tryResult = osstr2str(newPath)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }).mapErr(err2io)
}

/**
 * Renames a file or directory.
 *
 * This corresponds to the `pathRename` syscall.
 */
public fun rename(oldFd: File, oldPath: Path, newFd: File, newPath: Path): Result<Unit, Error> {
    return pathRename(((oldFd.asRawFd() as Any?) as Fd), when (val tryResult = osstr2str(oldPath)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }, ((newFd.asRawFd() as Any?) as Fd), when (val tryResult = osstr2str(newPath)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }).mapErr(err2io)
}

/**
 * Creates a symbolic link.
 *
 * This corresponds to the `pathSymlink` syscall.
 */
public fun symlink(oldPath: Path, fd: File, newPath: Path): Result<Unit, Error> {
    return pathSymlink(when (val tryResult = osstr2str(oldPath)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }, ((fd.asRawFd() as Any?) as Fd), when (val tryResult = osstr2str(newPath)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }).mapErr(err2io)
}

/**
 * Creates a symbolic link.
 *
 * This is a convenience API similar to `std.os.unix.fs.symlink` and
 * `std.os.windows.fs.symlinkFile` and `std.os.windows.fs.symlinkDir`.
 */
public fun symlinkPath(oldPath: Path, newPath: Path): Result<Unit, Error> = io.github.kotlinmania.rustlang.std.sys.fs.symlink(oldPath, newPath)

public fun osstr2str(f: OsStr): Result<String, Error> = f.toStr().okOrElse({ constError(ErrorKind.Uncategorized, "input must be utf-8") })
