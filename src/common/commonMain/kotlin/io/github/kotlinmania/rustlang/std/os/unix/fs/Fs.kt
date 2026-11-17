// port-lint: source os/unix/fs.rs
package io.github.kotlinmania.rustlang.std.os.unix.fs

import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadBuf
import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadVectored
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.write.defaultWriteVectored
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.libc.wasi.S_IFBLK
import io.github.kotlinmania.rustlang.libc.wasi.S_IFCHR
import io.github.kotlinmania.rustlang.libc.wasi.S_IFIFO
import io.github.kotlinmania.rustlang.libc.wasi.S_IFSOCK
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.fs.DirBuilder
import io.github.kotlinmania.rustlang.std.fs.DirEntry
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.fs.FileType
import io.github.kotlinmania.rustlang.std.fs.Metadata
import io.github.kotlinmania.rustlang.std.fs.OpenOptions
import io.github.kotlinmania.rustlang.std.fs.Permissions
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.FromInner

// Unix-specific extensions to primitives in the [std.fs] module.

/**
 * Unix-specific extensions to [fs.File].
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
     *
     * [File.read]: fs.File.read
     *
     * # Examples
     *
     * use std.io;
     * use std.fs.File;
     * use std.os.unix.prelude.FileExt;
     *
     * fn main() -> io.Result<()> (
     *     let mut buf = [0u8; 8];
     *     let file = File.open("foo.txt")?;
     *
     *     // We now read 8 bytes from the offset 10.
     *     let numBytesRead = file.readAt(buf, 10)?;
     *     println("read (numBytesRead) bytes: (buf:?)");
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun readAt(buf: MutableList<UByte>, offset: ULong): Result<Int, Error>

    /**
     * Like `readAt`, except that it reads into a slice of buffers.
     *
     * Data is copied to fill each buffer in order, with the final buffer
     * written to possibly being only partially filled. This method must behave
     * equivalently to a single call to read with concatenated buffers.
     */
    public fun readVectoredAt(bufs: MutableList<IoSliceMut>, offset: ULong): Result<Int, Error> = defaultReadVectored({ b -> this.readAt(b, offset) }, bufs)

    /**
     * Reads the exact number of bytes required to fill `buf` from the given offset.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor.
     *
     * The current file cursor is not affected by this function.
     *
     * Similar to [io.Read.readExact] but uses [readAt] instead of `read`.
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
     *
     * # Examples
     *
     * use std.io;
     * use std.fs.File;
     * use std.os.unix.prelude.FileExt;
     *
     * fn main() -> io.Result<()> (
     *     let mut buf = [0u8; 8];
     *     let file = File.open("foo.txt")?;
     *
     *     // We now read exactly 8 bytes from the offset 10.
     *     file.readExactAt(buf, 10)?;
     *     println("read () bytes: (:?)", buf.len(), buf);
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun readExactAt(buf: MutableList<UByte>, offset: ULong): Result<Unit, Error> {
        var buf1 = buf
        var offset1 = offset
        while (!buf1.isEmpty()) {
            run {
                val matchVal = this.readAt(buf1, offset1)
                when {
                    matchVal is Result.Ok && matchVal.value == 0 -> {
                        val valueVal = matchVal.value
                        break
                    }
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
            Result.Err<Unit, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.READ_EXACT_EOF)
        } else {
            Result.Ok<Unit, Error>(Unit)
        }
    }

    /**
     * Reads some bytes starting from a given offset into the buffer.
     *
     * This equivalent to the [`readAt`](FileExt.readAt) method, except that it is passed a
     * [BorrowedCursor] rather than `[UByte]` to allow use with uninitialized buffers. The new
     * data will be appended to any existing contents of `buf`.
     *
     * # Examples
     *
     * @file:feature(coreIoBorrowedBuf)
     * @file:feature(readBufAt)
     *
     * use std.io;
     * use std.io.BorrowedBuf;
     * use std.fs.File;
     * use std.mem.MaybeUninit;
     * use std.os.unix.prelude::*;
     *
     * fn main() -> io.Result<()> (
     *     let mut file = File.open("pi.txt")?;
     *
     *     // Read some bytes starting from offset 2
     *     let mut buf: [MaybeUninit<u8>; 10] = [MaybeUninit.uninit(); 10];
     *     let mut buf = BorrowedBuf.from(buf.asMutSlice());
     *     file.readBufAt(buf.unfilled(), 2)?;
     *
     *     assert(buf.filled().startsWith(b"1"));
     *
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun readBufAt(buf: BorrowedCursor<UByte>, offset: ULong): Result<Unit, Error> = defaultReadBuf({ b -> this.readAt(b, offset) }, buf)

    /**
     * Reads the exact number of bytes required to fill the buffer from a given offset.
     *
     * This is equivalent to the [`readExactAt`](FileExt.readExactAt) method, except that it
     * is passed a [BorrowedCursor] rather than `[UByte]` to allow use with uninitialized
     * buffers. The new data will be appended to any existing contents of `buf`.
     *
     * # Examples
     *
     * @file:feature(coreIoBorrowedBuf)
     * @file:feature(readBufAt)
     *
     * use std.io;
     * use std.io.BorrowedBuf;
     * use std.fs.File;
     * use std.mem.MaybeUninit;
     * use std.os.unix.prelude::*;
     *
     * fn main() -> io.Result<()> (
     *     let mut file = File.open("pi.txt")?;
     *
     *     // Read exactly 10 bytes starting from offset 2
     *     let mut buf: [MaybeUninit<u8>; 10] = [MaybeUninit.uninit(); 10];
     *     let mut buf = BorrowedBuf.from(buf.asMutSlice());
     *     file.readBufExactAt(buf.unfilled(), 2)?;
     *
     *     assertEq(buf.filled(), b"1415926535");
     *
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun readBufExactAt(buf: BorrowedCursor<UByte>, offset: ULong): Result<Unit, Error> {
        var offset1 = offset
        while (buf.capacity() > 0) {
            val prevWritten = buf.written()
            run {
                val matchVal = this.readBufAt(buf.reborrow(), offset1)
                when {
                    matchVal is Result.Ok -> {
                        val valueVal = matchVal.value
                    }
                    matchVal is Result.Err && (matchVal as Result.Err).error.isInterrupted() -> {
                        val e = matchVal.error
                        continue
                    }
                    else -> {
                        val e1 = matchVal.error
                        return Result.Err<Unit, Error>(e1)
                    }
                }
            }
            val n = buf.written() - prevWritten
            offset1 += n.toULong()
            if (n == 0) {
                return Result.Err<Unit, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.READ_EXACT_EOF)
            }
        }
        return Result.Ok<Unit, Error>(Unit)
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
     *
     * # Bug
     * On some systems, `writeAt` utilises [pwrite64] to write to files.
     * However, this syscall has a [bug] where files opened with the `O_APPEND`
     * flag fail to respect the offset parameter, always appending to the end
     * of the file instead.
     *
     * It is possible to inadvertently set this flag, like in the example below.
     * Therefore, it is important to be vigilant while changing options to mitigate
     * unexpected behavior.
     *
     * use std.fs.File;
     * use std.io;
     * use std.os.unix.prelude.FileExt;
     *
     * fn main() -> io.Result<()> (
     *     // Open a file with the append option (sets the `O_APPEND` flag)
     *     let file = File.options().append(true).open("foo.txt")?;
     *
     *     // We attempt to write at offset 10; instead appended to EOF
     *     file.writeAt(b"sushi", 10)?;
     *
     *     // foo.txt is 5 bytes long instead of 15
     *     Ok(())
     * )
     * ```kotlin
     *
     * [File.write]: fs.File.write
     * [pwrite64]: https://man7.org/linux/man-pages/man2/pwrite.2.html
     * [bug]: https:
     *
     * import std.fs.File;
     * import std.io;
     * import std.os.unix.prelude.FileExt;
     *
     * fn main() -> io.Result<()> (
     * val file = File.create("foo.txt");
     *
     * file.writeAt(b"sushi", 10);
     * Ok(())
     * )
     * ```
     */
    public fun writeAt(buf: List<UByte>, offset: ULong): Result<Int, Error>

    /**
     * Like `writeAt`, except that it writes from a slice of buffers.
     *
     * Data is copied from each buffer in order, with the final buffer read
     * from possibly being only partially consumed. This method must behave as
     * a call to `writeAt` with the buffers concatenated would.
     */
    public fun writeVectoredAt(bufs: List<IoSlice>, offset: ULong): Result<Int, Error> = defaultWriteVectored({ b -> this.writeAt(b, offset) }, bufs)

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
     *
     * # Examples
     *
     * use std.fs.File;
     * use std.io;
     * use std.os.unix.prelude.FileExt;
     *
     * fn main() -> io.Result<()> (
     *     let file = File.open("foo.txt")?;
     *
     *     // We now write at the offset 10.
     *     file.writeAllAt(b"sushi", 10)?;
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun writeAllAt(buf: List<UByte>, offset: ULong): Result<Unit, Error> {
        var buf1 = buf
        var offset1 = offset
        while (!buf1.isEmpty()) {
            run {
                val matchVal = this.writeAt(buf1, offset1)
                when {
                    matchVal is Result.Ok && matchVal.value == 0 -> {
                        val valueVal = matchVal.value
                        return Result.Err<Unit, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.WRITE_ALL_EOF)
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
}

public fun File.readAt(buf: MutableList<UByte>, offset: ULong): Result<Int, Error> = this.asInner().readAt(buf, offset)

public fun File.readBufAt(buf: BorrowedCursor<UByte>, offset: ULong): Result<Unit, Error> = this.asInner().readBufAt(buf, offset)

public fun File.readVectoredAt(bufs: MutableList<IoSliceMut>, offset: ULong): Result<Int, Error> = this.asInner().readVectoredAt(bufs, offset)

public fun File.writeAt(buf: List<UByte>, offset: ULong): Result<Int, Error> = this.asInner().writeAt(buf, offset)

public fun File.writeVectoredAt(bufs: List<IoSlice>, offset: ULong): Result<Int, Error> = this.asInner().writeVectoredAt(bufs, offset)

/**
 * Unix-specific extensions to [fs.Permissions].
 *
 * # Examples
 *
 * use std.fs::(File, Permissions);
 * use std.io::(ErrorKind, Result as IoResult);
 * use std.os.unix.fs.PermissionsExt;
 *
 * fn main() -> IoResult<()> (
 *     let name = "testFileForPermissions";
 *
 *     // make sure file does not exist
 *     let _ = std.fs.removeFile(name);
 *     assertEq(
 *         File.open(name).unwrapErr().kind(),
 *         ErrorKind.NotFound,
 *         "file already exists"
 *     );
 *
 *     // full read/write/execute mode bits for owner of file
 *     // that we want to add to existing mode bits
 *     let myMode = 0o700;
 *
 *     // create new file with specified permissions
 *     (
 *         let file = File.create(name)?;
 *         let mut permissions = file.metadata()?.permissions();
 *         eprintln("Current permissions: (:o)", permissions.mode());
 *
 *         // make sure new permissions are not already set
 *         assert(
 *             permissions.mode() & myMode != myMode,
 *             "permissions already set"
 *         );
 *
 *         // either use `setMode` to change an existing Permissions struct
 *         permissions.setMode(permissions.mode() | myMode);
 *
 *         // or use `fromMode` to construct a new Permissions struct
 *         permissions = Permissions.fromMode(permissions.mode() | myMode);
 *
 *         // write new permissions to file
 *         file.setPermissions(permissions)?;
 *     )
 *
 *     let permissions = File.open(name)?.metadata()?.permissions();
 *     eprintln("New permissions: (:o)", permissions.mode());
 *
 *     // assert new permissions were set
 *     assertEq(
 *         permissions.mode() & myMode,
 *         myMode,
 *         "new permissions not set"
 *     );
 *     Ok(())
 * )
 * ```kotlin
 * import std.fs.Permissions
 * import std.os.unix.fs.PermissionsExt
 *
 * // read/write for owner and read for others
 * val myMode = 420
 * var permissions = Permissions.fromMode(myMode)
 * check(permissions.mode() == myMode)
 * // read/write/execute for owner
 * val otherMode = 448
 * permissions.setMode(otherMode)
 * check(permissions.mode() == otherMode)
 * ```
 */
public interface PermissionsExt<T> {
    /**
     * Returns the mode permission bits
     */
    public fun mode(): UInt

    /**
     * Sets the mode permission bits.
     */
    public fun setMode(mode: UInt)

    /**
     * Creates a new instance from the given mode permission bits.
     */
    public fun fromMode(mode: UInt): T

    public companion object {
        /**
         * Creates a new instance from the given mode permission bits.
         */
        public fun fromMode(mode: UInt): Permissions = permissionsExtFromModePermissions(mode)
    }
}

public fun Permissions.mode(): UInt = this.asInner().mode()

public fun Permissions.setMode(mode: UInt) {
    var selfMut = this
    selfMut = Permissions.fromInner(FromInner.fromInner(mode))
}

public fun permissionsExtFromModePermissions(mode: UInt): Permissions = Permissions.fromInner(FromInner.fromInner(mode))

/**
 * Unix-specific extensions to [fs.OpenOptions].
 */
public interface OpenOptionsExt<T> {
    /**
     * Sets the mode bits that a new file will be created with.
     *
     * If a new file is created as part of an `OpenOptions.open` call then this
     * specified `mode` will be used as the permission bits for the new file.
     * If no `mode` is set, the default of `0o666` will be used.
     * The operating system masks out bits with the system's `umask`, to produce
     * the final permissions.
     *
     * # Examples
     *
     * use std.fs.OpenOptions;
     * use std.os.unix.fs.OpenOptionsExt;
     *
     * # fn main() (
     * let mut options = OpenOptions.new();
     * options.mode(0o644); // Give read/write for owner and read for others.
     * let file = options.open("foo.txt");
     * # )
     * ```kotlin
     */
    public fun mode(mode: UInt): T

    /**
     * Pass custom flags to the `flags` argument of `open`.
     *
     * The bits that define the access mode are masked out with `O_ACCMODE`, to
     * ensure they do not interfere with the access mode set by Rust's options.
     *
     * Custom flags can only set flags, not remove flags set by Rust's options.
     * This function overwrites any previously-set custom flags.
     *
     * # Examples
     *
     * # mod libc ( pub const O_NOFOLLOW: i32 = 0; )
     * use std.fs.OpenOptions;
     * use std.os.unix.fs.OpenOptionsExt;
     *
     * # fn main() (
     * let mut options = OpenOptions.new();
     * options.write(true);
     * options.customFlags(libc.ONofollow);
     * let file = options.open("foo.txt");
     * # )
     * ```kotlin
     */
    public fun customFlags(flags: Int): T
}

public fun OpenOptions.mode(mode: UInt): OpenOptions {
    this.asInnerMut().mode(mode)
    return this
}

public fun OpenOptions.customFlags(flags: Int): OpenOptions {
    this.asInnerMut().customFlags(flags)
    return this
}

/**
 * Unix-specific extensions to [fs.Metadata].
 */
public interface MetadataExt {
    /**
     * Returns the ID of the device containing the file.
     *
     * # Examples
     *
     * use std.io;
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let devId = meta.dev();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun dev(): ULong

    /**
     * Returns the inode number.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let inode = meta.ino();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun ino(): ULong

    /**
     * Returns the rights applied to this file.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let mode = meta.mode();
     *     let userHasWriteAccess      = mode & 0o200;
     *     let userHasReadWriteAccess = mode & 0o600;
     *     let groupHasReadAccess      = mode & 0o040;
     *     let othersHaveExecAccess    = mode & 0o001;
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun mode(): UInt

    /**
     * Returns the number of hard links pointing to this file.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let nbHardLinks = meta.nlink();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun nlink(): ULong

    /**
     * Returns the user ID of the owner of this file.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let userId = meta.uid();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun uid(): UInt

    /**
     * Returns the group ID of the owner of this file.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let groupId = meta.gid();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun gid(): UInt

    /**
     * Returns the device ID of this file (if it is a special one).
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let deviceId = meta.rdev();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun rdev(): ULong

    /**
     * Returns the total size of this file in bytes.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let fileSize = meta.size();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun size(): ULong

    /**
     * Returns the last access time of the file, in seconds since Unix Epoch.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let lastAccessTime = meta.atime();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun atime(): Long

    /**
     * Returns the last access time of the file, in nanoseconds since [atime].
     *
     * [atime]: MetadataExt.atime
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let nanoLastAccessTime = meta.atimeNsec();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun atimeNsec(): Long

    /**
     * Returns the last modification time of the file, in seconds since Unix Epoch.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let lastModificationTime = meta.mtime();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun mtime(): Long

    /**
     * Returns the last modification time of the file, in nanoseconds since [mtime].
     *
     * [mtime]: MetadataExt.mtime
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let nanoLastModificationTime = meta.mtimeNsec();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun mtimeNsec(): Long

    /**
     * Returns the last status change time of the file, in seconds since Unix Epoch.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let lastStatusChangeTime = meta.ctime();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun ctime(): Long

    /**
     * Returns the last status change time of the file, in nanoseconds since [ctime].
     *
     * [ctime]: MetadataExt.ctime
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let nanoLastStatusChangeTime = meta.ctimeNsec();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun ctimeNsec(): Long

    /**
     * Returns the block size for filesystem I/O.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let blockSize = meta.blksize();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun blksize(): ULong

    /**
     * Returns the number of blocks allocated to the file, in 512-byte units.
     *
     * Please note that this may be smaller than `stSize / 512` when the file has holes.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.MetadataExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("someFile")?;
     *     let blocks = meta.blocks();
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun blocks(): ULong
}

public fun Metadata.dev(): ULong = this.stDev()

public fun Metadata.ino(): ULong = this.stIno()

public fun Metadata.mode(): UInt = this.stMode()

public fun Metadata.nlink(): ULong = this.stNlink()

public fun Metadata.uid(): UInt = this.stUid()

public fun Metadata.gid(): UInt = this.stGid()

public fun Metadata.rdev(): ULong = this.stRdev()

public fun Metadata.size(): ULong = this.stSize()

public fun Metadata.atime(): Long = this.stAtime()

public fun Metadata.atimeNsec(): Long = this.stAtimeNsec()

public fun Metadata.mtime(): Long = this.stMtime()

public fun Metadata.mtimeNsec(): Long = this.stMtimeNsec()

public fun Metadata.ctime(): Long = this.stCtime()

public fun Metadata.ctimeNsec(): Long = this.stCtimeNsec()

public fun Metadata.blksize(): ULong = this.stBlksize()

public fun Metadata.blocks(): ULong = this.stBlocks()

/**
 * Unix-specific extensions for [fs.FileType].
 *
 * Adds support for special Unix file types such as block/character devices,
 * pipes, and sockets.
 */
public interface FileTypeExt {
    /**
     * Returns `true` if this file type is a block device.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.FileTypeExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("blockDeviceFile")?;
     *     let fileType = meta.fileType();
     *     assert(fileType.isBlockDevice());
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun isBlockDevice(): Boolean

    /**
     * Returns `true` if this file type is a char device.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.FileTypeExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("charDeviceFile")?;
     *     let fileType = meta.fileType();
     *     assert(fileType.isCharDevice());
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun isCharDevice(): Boolean

    /**
     * Returns `true` if this file type is a fifo.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.FileTypeExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("fifoFile")?;
     *     let fileType = meta.fileType();
     *     assert(fileType.isFifo());
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun isFifo(): Boolean

    /**
     * Returns `true` if this file type is a socket.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.FileTypeExt;
     * use std.io;
     *
     * fn main() -> io.Result<()> (
     *     let meta = fs.metadata("unix.socket")?;
     *     let fileType = meta.fileType();
     *     assert(fileType.isSocket());
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun isSocket(): Boolean
}

public fun FileType.isBlockDevice(): Boolean = this.asInner().isMatch(S_IFBLK)

public fun FileType.isCharDevice(): Boolean = this.asInner().isMatch(S_IFCHR)

public fun FileType.isFifo(): Boolean = this.asInner().isMatch(S_IFIFO)

public fun FileType.isSocket(): Boolean = this.asInner().isMatch(S_IFSOCK)

/**
 * Unix-specific extension methods for [fs.DirEntry].
 */
public interface DirEntryExt {
    /**
     * Returns the underlying `dIno` field in the contained `dirent`
     * structure.
     *
     * # Examples
     *
     * use std.fs;
     * use std.os.unix.fs.DirEntryExt;
     *
     * if let Ok(entries) = fs.readDir(".") (
     *     for entry in entries (
     *         if let Ok(entry) = entry (
     *             // Here, `entry` is a `DirEntry`.
     *             println("(:?): ()", entry.fileName(), entry.ino());
     *         )
     *     )
     * )
     * ```kotlin
     */
    public fun ino(): ULong
}

public fun DirEntry.ino(): ULong = this.asInner().ino()

/**
 * Unix-specific extension methods for [fs.DirEntry].
 */
public interface DirEntryExt2 {
    /**
     * Returns a reference to the underlying `OsStr` of this entry's filename.
     *
     * # Examples
     *
     * @file:feature(dirEntryExt2)
     * use std.os.unix.fs.DirEntryExt2;
     * use std::(fs, io);
     *
     * fn main() -> io.Result<()> (
     *     let mut entries = fs.readDir(".")?.collect::<Result<Vec<_>, io.Error>>()?;
     *     entries.sortUnstableBy(|a, b| a.fileNameRef().cmp(b.fileNameRef()));
     *
     *     for p in entries (
     *         println("(p:?)");
     *     )
     *
     *     Ok(())
     * )
     * ```kotlin
     */
    public fun fileNameRef(): OsStr
}

public fun DirEntry.fileNameRef(): OsStr = this.asInner().fileNameOsStr()

/**
 * Creates a new symbolic link on the filesystem.
 *
 * The `link` path will be a symbolic link pointing to the `original` path.
 *
 * # Examples
 *
 * use std.os.unix.fs;
 *
 * fn main() -> std.io.Result<()> (
 *     fs.symlink("a.txt", "b.txt")?;
 *     Ok(())
 * )
 * ```kotlin
 */
public fun symlink(original: Path, link: Path): Result<Unit, Error> = io.github.kotlinmania.rustlang.std.sys.fs.symlink(original, link)

/**
 * Unix-specific extensions to [fs.DirBuilder].
 */
public interface DirBuilderExt<T> {
    /**
     * Sets the mode to create new directories with. This option defaults to
     * 0o777.
     *
     * # Examples
     *
     * use std.fs.DirBuilder;
     * use std.os.unix.fs.DirBuilderExt;
     *
     * let mut builder = DirBuilder.new();
     * builder.mode(0o755);
     * ```kotlin
     */
    public fun mode(mode: UInt): T
}

public fun DirBuilder.mode(mode: UInt): DirBuilder {
    this.asInnerMut().setMode(mode)
    return this
}

/**
 * Change the owner and group of the specified path.
 *
 * Specifying either the uid or gid as `None` will leave it unchanged.
 *
 * Changing the owner typically requires privileges, such as root or a specific capability.
 * Changing the group typically requires either being the owner and a member of the group, or
 * having privileges.
 *
 * Be aware that changing owner clears the `suid` and `sgid` permission bits in most cases
 * according to POSIX, usually even if the user is root. The sgid is not cleared when
 * the file is non-group-executable. See: <https://www.man7.org/linux/man-pages/man2/chown.2.html>
 * This call may also clear file capabilities, if there was any.
 *
 * If called on a symbolic link, this will change the owner and group of the link target. To
 * change the owner and group of the link itself, see [lchown].
 *
 * # Examples
 *
 * use std.os.unix.fs;
 *
 * fn main() -> std.io.Result<()> (
 *     fs.chown("/sandbox", Some(0), Some(0))?;
 *     Ok(())
 * )
 * ```kotlin
 */
public fun chown(dir: Path, uid: UInt?, gid: UInt?): Result<Unit, Error> = io.github.kotlinmania.rustlang.std.sys.fs.unix.chown(dir, (uid ?: UInt.MAX_VALUE), (gid ?: UInt.MAX_VALUE))

/**
 * Change the owner and group of the file referenced by the specified open file descriptor.
 *
 * For semantics and required privileges, see [chown].
 *
 * # Examples
 *
 * use std.os.unix.fs;
 *
 * fn main() -> std.io.Result<()> (
 *     let f = std.fs.File.open("/file")?;
 *     fs.fchown(&f, Some(0), Some(0))?;
 *     Ok(())
 * )
 * ```kotlin
 */
public fun <F : AsFd> fchown(fd: F, uid: UInt?, gid: UInt?): Result<Unit, Error> = io.github.kotlinmania.rustlang.std.sys.fs.unix.fchown(fd.asFd().asRawFd(), (uid ?: UInt.MAX_VALUE), (gid ?: UInt.MAX_VALUE))

/**
 * Change the owner and group of the specified path, without dereferencing symbolic links.
 *
 * Identical to [chown], except that if called on a symbolic link, this will change the owner
 * and group of the link itself rather than the owner and group of the link target.
 *
 * # Examples
 *
 * use std.os.unix.fs;
 *
 * fn main() -> std.io.Result<()> (
 *     fs.lchown("/symlink", Some(0), Some(0))?;
 *     Ok(())
 * )
 * ```kotlin
 */
public fun lchown(dir: Path, uid: UInt?, gid: UInt?): Result<Unit, Error> = io.github.kotlinmania.rustlang.std.sys.fs.unix.lchown(dir, (uid ?: UInt.MAX_VALUE), (gid ?: UInt.MAX_VALUE))

/**
 * Change the root directory of the current process to the specified path.
 *
 * This typically requires privileges, such as root or a specific capability.
 *
 * This does not change the current working directory; you should call
 * [`std.env.setCurrentDir`][env.setCurrentDir] afterwards.
 *
 * # Examples
 *
 * use std.os.unix.fs;
 *
 * fn main() -> std.io.Result<()> (
 *     fs.chroot("/sandbox")?;
 *     std.env.setCurrentDir("/")?;
 *     // continue working in sandbox
 *     Ok(())
 * )
 * ```kotlin
 */
public fun chroot(dir: Path): Result<Unit, Error> = io.github.kotlinmania.rustlang.std.sys.fs.unix.chroot(dir)

/**
 * Create a FIFO special file at the specified path with the specified mode.
 *
 * # Examples
 *
 * # @file:feature(unixMkfifo)
 * # @cfg(not(unix))
 * # fn main() ()
 * # @cfg(unix)
 * # fn main() -> std.io.Result<()> (
 * # use std::(
 * #     os.unix.fs::(mkfifo, PermissionsExt),
 * #     fs::(File, Permissions, removeFile),
 * #     io::(Write, Read),
 * # );
 * # let _ = removeFile("/tmp/fifo");
 * mkfifo("/tmp/fifo", Permissions.fromMode(0o774))?;
 *
 * let mut wx = File.options().read(true).write(true).open("/tmp/fifo")?;
 * let mut rx = File.open("/tmp/fifo")?;
 *
 * wx.writeAll(b"hello, world!")?;
 * drop(wx);
 *
 * let mut s = String.new();
 * rx.readToString(s)?;
 *
 * assertEq(s, "hello, world!");
 * # Ok(())
 * # )
 * ```kotlin
 */
public fun mkfifo(path: Path, permissions: Permissions): Result<Unit, Error> = io.github.kotlinmania.rustlang.std.sys.fs.unix.mkfifo(path, permissions.mode())
