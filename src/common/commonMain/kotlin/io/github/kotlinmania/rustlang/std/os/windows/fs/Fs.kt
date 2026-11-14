// port-lint: source os/windows/fs.rs
package io.github.kotlinmania.rustlang.std.os.windows.fs

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.fs.File
import io.github.kotlinmania.rustlang.std.fs.FileTimes
import io.github.kotlinmania.rustlang.std.fs.FileType
import io.github.kotlinmania.rustlang.std.fs.Metadata
import io.github.kotlinmania.rustlang.std.fs.OpenOptions
import io.github.kotlinmania.rustlang.std.fs.Permissions
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.fs.windows.symlinkInner
import io.github.kotlinmania.rustlang.std.time.SystemTime

// Windows-specific extensions to primitives in the [std.fs] module.

/**
 * Windows-specific extensions to [fs.File].
 */
public interface FileExt {
    /**
     * Seeks to a given position and reads a number of bytes.
     *
     * Returns the number of bytes read.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor. The current cursor **is** affected by this
     * function, it is set to the end of the read.
     *
     * Reading beyond the end of the file will always return with a length of
     * 0\.
     *
     * Note that similar to `File.read`, it is not an error to return with a
     * short read. When returning from such a short read, the file pointer is
     * still updated.
     *
     * # Examples
     *
     * use std.io;
     * use std.fs.File;
     * use std.os.windows.prelude::*;
     *
     * fn main() -> io.Result<()> {
     *     let mut file = File.open("foo.txt")?;
     *     let mut buffer = [0; 10];
     *
     *     // Read 10 bytes, starting 72 bytes from the
     *     // start of the file.
     *     file.seekRead(buffer[..], 72)?;
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun seekRead(buf: MutableList<UByte>, offset: ULong): Result<Int>

    /**
     * Seeks to a given position and reads the exact number of bytes required to fill `buf`.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor. The current cursor **is** affected by this
     * function, it is set to the end of the read.
     *
     * Similar to [io.Read.readExact] but uses [seekRead] instead of `read`.
     *
     * [seekRead]: FileExt.seekRead
     *
     * # Errors
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
     * @file:feature(seekReadExactSeekWriteAll)
     *
     * use std.io;
     * use std.fs.File;
     * use std.os.windows.prelude::*;
     *
     * fn main() -> io.Result<()> {
     *     let mut file = File.open("foo.txt")?;
     *     let mut buffer = [0; 10];
     *
     *     // Read 10 bytes, starting 72 bytes from the
     *     // start of the file.
     *     file.seekReadExact(buffer[..], 72)?;
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun seekReadExact(buf: MutableList<UByte>, offset: ULong): Result<Unit> {
        var buf1 = buf
        var offset1 = offset
        while (!buf1.isEmpty()) {
            run {
                val matchVal = this.seekRead(buf1, offset1)
                when {
                    matchVal is Result.Ok<*> && matchVal.value == 0 -> { break }
                    matchVal is Result.Ok<*> -> {
                        val n = (matchVal as Result.Ok<*>).value
                        buf1 = buf1.subList(n, buf1.size)
                        offset1 += n.toULong()
                    }
                    else -> {
                        val e = (matchVal as Result.Err<*>).error
                        return Result.Err(e)
                    }
                }
            }
        }
        return if (!buf1.isEmpty()) {
            Result.Err(io.Error.READ_EXACT_EOF)
        } else {
            Result.Ok(Unit)
        }
    }

    /**
     * Seeks to a given position and reads some bytes into the buffer.
     *
     * This is equivalent to the [`seekRead`](FileExt.seekRead) method, except that it is passed
     * a [BorrowedCursor] rather than `[UByte]` to allow use with uninitialized buffers. The
     * new data will be appended to any existing contents of `buf`.
     *
     * Reading beyond the end of the file will always succeed without reading any bytes.
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
     * use std.os.windows.prelude::*;
     *
     * fn main() -> io.Result<()> {
     *     let mut file = File.open("pi.txt")?;
     *
     *     // Read some bytes starting from offset 2
     *     let mut buf: [MaybeUninit<u8>; 10] = [MaybeUninit.uninit(); 10];
     *     let mut buf = BorrowedBuf.from(buf.asMutSlice());
     *     file.seekReadBuf(buf.unfilled(), 2)?;
     *
     *     assert(buf.filled().startsWith(b"1"));
     *
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun seekReadBuf(buf: BorrowedCursor<UByte>, offset: ULong): Result<Unit> = io.defaultReadBuf({ b -> this.seekRead(b, offset) }, buf)

    /**
     * Seeks to a given position and reads the exact number of bytes required to fill `buf`.
     *
     * This is equivalent to the [`seekReadExact`](FileExt.seekReadExact) method, except
     * that it is passed a [BorrowedCursor] rather than `[UByte]` to allow use with
     * uninitialized buffers. The new data will be appended to any existing contents of `buf`.
     *
     * Reading beyond the end of the file will always succeed without reading any bytes.
     *
     * # Examples
     *
     * @file:feature(coreIoBorrowedBuf)
     * @file:feature(seekReadExactSeekWriteAll)
     *
     * use std.io;
     * use std.io.BorrowedBuf;
     * use std.fs.File;
     * use std.mem.MaybeUninit;
     * use std.os.windows.prelude::*;
     *
     * fn main() -> io.Result<()> {
     *     let mut file = File.open("pi.txt")?;
     *
     *     // Read some bytes starting from offset 2
     *     let mut buf: [MaybeUninit<u8>; 10] = [MaybeUninit.uninit(); 10];
     *     let mut buf = BorrowedBuf.from(buf.asMutSlice());
     *     file.seekReadBufExact(buf.unfilled(), 2)?;
     *
     *     assert(buf.filled().startsWith(b"1"));
     *
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun seekReadBufExact(buf: BorrowedCursor<UByte>, offset: ULong): Result<Unit> {
        var offset1 = offset
        while (buf.capacity() > 0) {
            val prevWritten = buf.written()
            run {
                val matchVal = this.seekReadBuf(buf.reborrow(), offset1)
                when (matchVal) { is Result.Ok<*> -> {}; is Result.Err<*> -> {
                    val e = matchVal.error
                    return Result.Err(e)
                } }
            }
            val n = buf.written() - prevWritten
            offset1 += n.toULong()
            if (n == 0) {
                return Result.Err(io.Error.READ_EXACT_EOF)
            }
        }
        return Result.Ok(Unit)
    }

    /**
     * Seeks to a given position and writes a number of bytes.
     *
     * Returns the number of bytes written.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor. The current cursor **is** affected by this
     * function, it is set to the end of the write.
     *
     * When writing beyond the end of the file, the file is appropriately
     * extended and the intermediate bytes are set to zero.
     *
     * Note that similar to `File.write`, it is not an error to return a
     * short write. When returning from such a short write, the file pointer
     * is still updated.
     *
     * # Examples
     *
     * use std.fs.File;
     * use std.os.windows.prelude::*;
     *
     * fn main() -> std.io.Result<()> {
     *     let mut buffer = File.create("foo.txt")?;
     *
     *     // Write a byte string starting 72 bytes from
     *     // the start of the file.
     *     buffer.seekWrite(b"some bytes", 72)?;
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun seekWrite(buf: List<UByte>, offset: ULong): Result<Int>

    /**
     * Seeks to a given position and attempts to write an entire buffer.
     *
     * The offset is relative to the start of the file and thus independent
     * from the current cursor. The current cursor **is** affected by this
     * function, it is set to the end of the write.
     *
     * This method will continuously call [seekWrite] until there is no more data
     * to be written or an error of non-[io.ErrorKind.Interrupted] kind is
     * returned. This method will not return until the entire buffer has been
     * successfully written or such an error occurs. The first error that is
     * not of [io.ErrorKind.Interrupted] kind generated from this method will be
     * returned.
     *
     * # Errors
     *
     * This function will return the first error that [seekWrite] returns.
     *
     * [seekWrite]: FileExt.seekWrite
     *
     * # Examples
     *
     * @file:feature(seekReadExactSeekWriteAll)
     *
     * use std.fs.File;
     * use std.os.windows.prelude::*;
     *
     * fn main() -> std.io.Result<()> {
     *     let mut buffer = File.create("foo.txt")?;
     *
     *     // Write a byte string starting 72 bytes from
     *     // the start of the file.
     *     buffer.seekWriteAll(b"some bytes", 72)?;
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun seekWriteAll(buf: List<UByte>, offset: ULong): Result<Unit> {
        var buf1 = buf
        var offset1 = offset
        while (!buf1.isEmpty()) {
            run {
                val matchVal = this.seekWrite(buf1, offset1)
                when {
                    matchVal is Result.Ok<*> && matchVal.value == 0 -> {
                        return Result.Err(io.Error.WRITE_ALL_EOF)
                    }
                    matchVal is Result.Ok<*> -> {
                        val n = (matchVal as Result.Ok<*>).value
                        buf1 = ((buf1).slice(n until buf1.size))
                        offset1 += n.toULong()
                    }
                    else -> {
                        val e = (matchVal as Result.Err<*>).error
                        return Result.Err(e)
                    }
                }
            }
        }
        return Result.Ok(Unit)
    }
}

public fun File.seekRead(buf: MutableList<UByte>, offset: ULong): Result<Int> = this.asInner().readAt(buf, offset)

public fun File.seekReadBuf(buf: BorrowedCursor<UByte>, offset: ULong): Result<Unit> = this.asInner().readBufAt(buf, offset)

public fun File.seekWrite(buf: List<UByte>, offset: ULong): Result<Int> = this.asInner().writeAt(buf, offset)

// WARNING: This trait is not sealed. DON'T add any new methods!
// Add them to OpenOptionsExt2 instead.

/**
 * Windows-specific extensions to [fs.OpenOptions].
 */
public interface OpenOptionsExt<T> {
    /**
     * Overrides the `dwDesiredAccess` argument to the call to [CreateFile]
     * with the specified value.
     *
     * This will override the `read`, `write`, and `append` flags on the
     * `OpenOptions` structure. This method provides fine-grained control over
     * the permissions to read, write and append data, attributes (like hidden
     * and system), and extended attributes.
     *
     * # Examples
     *
     * use std.fs.OpenOptions;
     * use std.os.windows.prelude::*;
     *
     * // Open without read and write permission, for example if you only need
     * // to call `stat` on the file
     * let file = OpenOptions.new().accessMode(0).open("foo.txt");
     * ```kotlin
     *
     * [CreateFile]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilea
     * ```
     */
    public fun accessMode(access: UInt): T

    /**
     * Overrides the `dwShareMode` argument to the call to [CreateFile] with
     * the specified value.
     *
     * By default `shareMode` is set to
     * `FILE_SHARE_READ | FILE_SHARE_WRITE | FILE_SHARE_DELETE`. This allows
     * other processes to read, write, and delete/rename the same file
     * while it is open. Removing any of the flags will prevent other
     * processes from performing the corresponding operation until the file
     * handle is closed.
     *
     * # Examples
     *
     * use std.fs.OpenOptions;
     * use std.os.windows.prelude::*;
     *
     * // Do not allow others to read or modify this file while we have it open
     * // for writing.
     * let file = OpenOptions.new()
     *     .write(true)
     *     .shareMode(0)
     *     .open("foo.txt");
     * ```kotlin
     *
     * [CreateFile]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilea
     * ```
     */
    public fun shareMode(value: UInt): T

    /**
     * Sets extra flags for the `dwFileFlags` argument to the call to
     * [CreateFile2] to the specified value (or combines it with
     * `attributes` and `securityQosFlags` to set the `dwFlagsAndAttributes`
     * for [CreateFile]).
     *
     * Custom flags can only set flags, not remove flags set by Rust's options.
     * This option overwrites any previously set custom flags.
     *
     * # Examples
     *
     * # @file:allow(unexpectedCfgs)
     * use std.fs.OpenOptions;
     * use std.os.windows.prelude::*;
     *
     * # pub const FILE_FLAG_DELETE_ON_CLOSE: u32 = 0x04000000;
     *
     * let file = OpenOptions.new()
     *     .create(true)
     *     .write(true)
     *     .customFlags(FILE_FLAG_DELETE_ON_CLOSE)
     *     .open("foo.txt");
     * ```kotlin
     *
     * [CreateFile]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilea
     * [CreateFile2]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfile2
     * ```
     */
    public fun customFlags(flags: UInt): T

    /**
     * Sets the `dwFileAttributes` argument to the call to [CreateFile2] to
     * the specified value (or combines it with `customFlags` and
     * `securityQosFlags` to set the `dwFlagsAndAttributes` for
     * [CreateFile]).
     *
     * If a _new_ file is created because it does not yet exist and
     * `.create(true)` or `.createNew(true)` are specified, the new file is
     * given the attributes declared with `.attributes()`.
     *
     * If an _existing_ file is opened with `.create(true).truncate(true)`, its
     * existing attributes are preserved and combined with the ones declared
     * with `.attributes()`.
     *
     * In all other cases the attributes get ignored.
     *
     * # Examples
     *
     * # @file:allow(unexpectedCfgs)
     * use std.fs.OpenOptions;
     * use std.os.windows.prelude::*;
     *
     * # pub const FILE_ATTRIBUTE_HIDDEN: u32 = 2;
     *
     * let file = OpenOptions.new()
     *     .write(true)
     *     .create(true)
     *     .attributes(FILE_ATTRIBUTE_HIDDEN)
     *     .open("foo.txt");
     * ```kotlin
     *
     * [CreateFile]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilea
     * [CreateFile2]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfile2
     * ```
     */
    public fun attributes(value: UInt): T

    /**
     * Sets the `dwSecurityQosFlags` argument to the call to [CreateFile2] to
     * the specified value (or combines it with `customFlags` and `attributes`
     * to set the `dwFlagsAndAttributes` for [CreateFile]).
     *
     * By default `securityQosFlags` is not set. It should be specified when
     * opening a named pipe, to control to which degree a server process can
     * act on behalf of a client process (security impersonation level).
     *
     * When `securityQosFlags` is not set, a malicious program can gain the
     * elevated privileges of a privileged Rust process when it allows opening
     * user-specified paths, by tricking it into opening a named pipe. So
     * arguably `securityQosFlags` should also be set when opening arbitrary
     * paths. However the bits can then conflict with other flags, specifically
     * `FILE_FLAG_OPEN_NO_RECALL`.
     *
     * For information about possible values, see [Impersonation Levels] on the
     * Windows Dev Center site. The `SECURITY_SQOS_PRESENT` flag is set
     * automatically when using this method.
     *
     * # Examples
     *
     * # @file:allow(unexpectedCfgs)
     * use std.fs.OpenOptions;
     * use std.os.windows.prelude::*;
     *
     * # pub const SECURITY_IDENTIFICATION: u32 = 0;
     *
     * let file = OpenOptions.new()
     *     .write(true)
     *     .create(true)
     *     .securityQosFlags(SECURITY_IDENTIFICATION)
     *     .open(r"\\.\pipe\MyPipe");
     * ```kotlin
     *
     * [CreateFile]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilea
     * [CreateFile2]: https://docs.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfile2
     * [Impersonation Levels]:
     * https:
     * ```
     */
    public fun securityQosFlags(flags: UInt): T
}

public fun OpenOptions.accessMode(access: UInt): OpenOptions {
    this.asInnerMut().accessMode(access)
    return this
}

public fun OpenOptions.shareMode(share: UInt): OpenOptions {
    this.asInnerMut().shareMode(share)
    return this
}

public fun OpenOptions.customFlags(flags: UInt): OpenOptions {
    this.asInnerMut().customFlags(flags)
    return this
}

public fun OpenOptions.attributes(attributes: UInt): OpenOptions {
    this.asInnerMut().attributes(attributes)
    return this
}

public fun OpenOptions.securityQosFlags(flags: UInt): OpenOptions {
    this.asInnerMut().securityQosFlags(flags)
    return this
}

public interface OpenOptionsExt2<T> {
    /**
     * If set to `true`, prevent the "last access time" of the file from being changed.
     *
     * Default to `false`.
     */
    public fun freezeLastAccessTime(freeze: Boolean): T

    /**
     * If set to `true`, prevent the "last write time" of the file from being changed.
     *
     * Default to `false`.
     */
    public fun freezeLastWriteTime(freeze: Boolean): T
}

public fun OpenOptions.freezeLastAccessTime(freeze: Boolean): OpenOptions {
    this.asInnerMut().freezeLastAccessTime(freeze)
    return this
}

public fun OpenOptions.freezeLastWriteTime(freeze: Boolean): OpenOptions {
    this.asInnerMut().freezeLastWriteTime(freeze)
    return this
}

/**
 * Windows-specific extensions to [fs.Permissions]. This extension trait
 * provides extra utilities to shows what Windows file attributes are enabled
 * in [Permissions] and to manually set file attributes on [Permissions].
 *
 * See Microsoft's [File.Attribute.Constants] page to know what file
 * attribute metadata are defined and stored on Windows files.
 *
 * [Permissions]: fs.Permissions
 * [File.Attribute.Constants]:
 *     https://learn.microsoft.com/en-us/windows/win32/fileio/file-attribute-constants
 *
 * # Example
 *
 * @file:feature(windowsPermissionsExt)
 * use std.fs.Permissions;
 * use std.os.windows.fs.PermissionsExt;
 *
 * const FILE_ATTRIBUTE_SYSTEM: u32 = 0x4;
 * const FILE_ATTRIBUTE_ARCHIVE: u32 = 0x20;
 * let myFileAttr = FILE_ATTRIBUTE_SYSTEM | FILE_ATTRIBUTE_ARCHIVE;
 * let mut permissions = Permissions.fromFileAttributes(myFileAttr);
 * assertEq(permissions.fileAttributes(), myFileAttr);
 *
 * const FILE_ATTRIBUTE_HIDDEN: u32 = 0x2;
 * let newFileAttr = permissions.fileAttributes() | FILE_ATTRIBUTE_HIDDEN;
 * permissions.setFileAttributes(newFileAttr);
 * assertEq(permissions.fileAttributes(), newFileAttr);
 * ```kotlin
 */
public interface PermissionsExt<T> {
    /**
     * Returns the file attribute bits.
     */
    public fun fileAttributes(): UInt

    /**
     * Sets the file attribute bits.
     */
    public fun setFileAttributes(mask: UInt)

    /**
     * Creates a new instance from the given file attribute bits.
     */
    public fun fromFileAttributes(mask: UInt): T
}

public fun Permissions.fileAttributes(): UInt = this.asInner().fileAttributes()

public fun Permissions.setFileAttributes(mask: UInt) {
    this = Permissions.fromInner(FromInner.fromInner(mask))
}

public fun permissionsExtFromFileAttributesPermissions(mask: UInt): Permissions = Permissions.fromInner(FromInner.fromInner(mask))

/**
 * Windows-specific extensions to [fs.Metadata].
 *
 * The data members that this trait exposes correspond to the members
 * of the [ByHandleFileInformation] structure.
 *
 * [ByHandleFileInformation]:
 *     https://docs.microsoft.com/windows/win32/api/fileapi/ns-fileapi-byHandleFileInformation
 */
public interface MetadataExt {
    /**
     * Returns the value of the `dwFileAttributes` field of this metadata.
     *
     * This field contains the file system attribute information for a file
     * or directory. For possible values and their descriptions, see
     * [File Attribute Constants] in the Windows Dev Center.
     *
     * # Examples
     *
     * use std.io;
     * use std.fs;
     * use std.os.windows.prelude::*;
     *
     * fn main() -> io.Result<()> {
     *     let metadata = fs.metadata("foo.txt")?;
     *     let attributes = metadata.fileAttributes();
     *     Ok(())
     * }
     * ```kotlin
     * [File Attribute Constants]: https:
     * ```
     */
    public fun fileAttributes(): UInt

    /**
     * Returns the value of the `ftCreationTime` field of this metadata.
     *
     * The returned 64-bit value is equivalent to a [Filetime] struct,
     * which represents the number of 100-nanosecond intervals since
     * January 1, 1601 (UTC). The struct is automatically
     * converted to a `ULong` value, as that is the recommended way
     * to use it.
     *
     * If the underlying filesystem does not support creation time, the
     * returned value is 0.
     *
     * # Examples
     *
     * use std.io;
     * use std.fs;
     * use std.os.windows.prelude::*;
     *
     * fn main() -> io.Result<()> {
     *     let metadata = fs.metadata("foo.txt")?;
     *     let creationTime = metadata.creationTime();
     *     Ok(())
     * }
     * ```kotlin
     *
     * [Filetime]: https://docs.microsoft.com/windows/win32/api/minwinbase/ns-minwinbase-filetime
     * ```
     */
    public fun creationTime(): ULong

    /**
     * Returns the value of the `ftLastAccessTime` field of this metadata.
     *
     * The returned 64-bit value is equivalent to a [Filetime] struct,
     * which represents the number of 100-nanosecond intervals since
     * January 1, 1601 (UTC). The struct is automatically
     * converted to a `ULong` value, as that is the recommended way
     * to use it.
     *
     * For a file, the value specifies the last time that a file was read
     * from or written to. For a directory, the value specifies when
     * the directory was created. For both files and directories, the
     * specified date is correct, but the time of day is always set to
     * midnight.
     *
     * If the underlying filesystem does not support last access time, the
     * returned value is 0.
     *
     * # Examples
     *
     * use std.io;
     * use std.fs;
     * use std.os.windows.prelude::*;
     *
     * fn main() -> io.Result<()> {
     *     let metadata = fs.metadata("foo.txt")?;
     *     let lastAccessTime = metadata.lastAccessTime();
     *     Ok(())
     * }
     * ```kotlin
     *
     * [Filetime]: https://docs.microsoft.com/windows/win32/api/minwinbase/ns-minwinbase-filetime
     * ```
     */
    public fun lastAccessTime(): ULong

    /**
     * Returns the value of the `ftLastWriteTime` field of this metadata.
     *
     * The returned 64-bit value is equivalent to a [Filetime] struct,
     * which represents the number of 100-nanosecond intervals since
     * January 1, 1601 (UTC). The struct is automatically
     * converted to a `ULong` value, as that is the recommended way
     * to use it.
     *
     * For a file, the value specifies the last time that a file was written
     * to. For a directory, the structure specifies when the directory was
     * created.
     *
     * If the underlying filesystem does not support the last write time,
     * the returned value is 0.
     *
     * # Examples
     *
     * use std.io;
     * use std.fs;
     * use std.os.windows.prelude::*;
     *
     * fn main() -> io.Result<()> {
     *     let metadata = fs.metadata("foo.txt")?;
     *     let lastWriteTime = metadata.lastWriteTime();
     *     Ok(())
     * }
     * ```kotlin
     *
     * [Filetime]: https://docs.microsoft.com/windows/win32/api/minwinbase/ns-minwinbase-filetime
     * ```
     */
    public fun lastWriteTime(): ULong

    /**
     * Returns the value of the `nFileSize` fields of this
     * metadata.
     *
     * The returned value does not have meaning for directories.
     *
     * # Examples
     *
     * use std.io;
     * use std.fs;
     * use std.os.windows.prelude::*;
     *
     * fn main() -> io.Result<()> {
     *     let metadata = fs.metadata("foo.txt")?;
     *     let fileSize = metadata.fileSize();
     *     Ok(())
     * }
     * ```kotlin
     */
    public fun fileSize(): ULong

    /**
     * Returns the value of the `dwVolumeSerialNumber` field of this
     * metadata.
     *
     * This will return `None` if the `Metadata` instance was created from a
     * call to `DirEntry.metadata`. If this `Metadata` was created by using
     * `fs.metadata` or `File.metadata`, then this will return `Some`.
     */
    public fun volumeSerialNumber(): UInt?

    /**
     * Returns the value of the `nNumberOfLinks` field of this
     * metadata.
     *
     * This will return `None` if the `Metadata` instance was created from a
     * call to `DirEntry.metadata`. If this `Metadata` was created by using
     * `fs.metadata` or `File.metadata`, then this will return `Some`.
     */
    public fun numberOfLinks(): UInt?

    /**
     * Returns the value of the `nFileIndex` fields of this
     * metadata.
     *
     * This will return `None` if the `Metadata` instance was created from a
     * call to `DirEntry.metadata`. If this `Metadata` was created by using
     * `fs.metadata` or `File.metadata`, then this will return `Some`.
     */
    public fun fileIndex(): ULong?

    /**
     * Returns the value of the `ChangeTime` fields of this metadata.
     *
     * `ChangeTime` is the last time file metadata was changed, such as
     * renames, attributes, etc.
     *
     * This will return `None` if `Metadata` instance was created from a call to
     * `DirEntry.metadata` or if the `targetVendor` is outside the current platform
     * support for this api.
     */
    public fun changeTime(): ULong?
}

public fun Metadata.fileAttributes(): UInt = this.asInner().attrs()

public fun Metadata.creationTime(): ULong = this.asInner().createdU64()

public fun Metadata.lastAccessTime(): ULong = this.asInner().accessedU64()

public fun Metadata.lastWriteTime(): ULong = this.asInner().modifiedU64()

public fun Metadata.fileSize(): ULong = this.asInner().size()

public fun Metadata.volumeSerialNumber(): UInt? = this.asInner().volumeSerialNumber()

public fun Metadata.numberOfLinks(): UInt? = this.asInner().numberOfLinks()

public fun Metadata.fileIndex(): ULong? = this.asInner().fileIndex()

public fun Metadata.changeTime(): ULong? = this.asInner().changedU64()

/**
 * Windows-specific extensions to [fs.FileType].
 *
 * On Windows, a symbolic link knows whether it is a file or directory.
 */
public interface FileTypeExt {
    /**
     * Returns `true` if this file type is a symbolic link that is also a directory.
     */
    public fun isSymlinkDir(): Boolean

    /**
     * Returns `true` if this file type is a symbolic link that is also a file.
     */
    public fun isSymlinkFile(): Boolean
}

public fun FileType.isSymlinkDir(): Boolean = this.asInner().isSymlinkDir()

public fun FileType.isSymlinkFile(): Boolean = this.asInner().isSymlinkFile()

/**
 * Windows-specific extensions to [fs.FileTimes].
 */
public interface FileTimesExt<T> {
    /**
     * Set the creation time of a file.
     */
    public fun setCreated(t: SystemTime): T
}

public fun FileTimes.setCreated(t: SystemTime): FileTimes {
    this.asInnerMut().setCreated(t.intoInner())
    return this
}

/**
 * Creates a new symlink to a non-directory file on the filesystem.
 *
 * The `link` path will be a file symbolic link pointing to the `original`
 * path.
 *
 * The `original` path should not be a directory or a symlink to a directory,
 * otherwise the symlink will be broken. Use [symlinkDir] for directories.
 *
 * This function currently corresponds to [`CreateSymbolicLinkW`][CreateSymbolicLinkW].
 * Note that this [may change in the future][changes].
 *
 * [CreateSymbolicLinkW]: https://docs.microsoft.com/en-us/windows/win32/api/winbase/nf-winbase-createsymboliclinkw
 * [changes]: io#platform-specific-behavior
 *
 * # Examples
 *
 * use std.os.windows.fs;
 *
 * fn main() -> std.io.Result<()> {
 *     fs.symlinkFile("a.txt", "b.txt")?;
 *     Ok(())
 * }
 * ```kotlin
 *
 * Windows treats symlink creation as a[privileged action][symlink - security],
 * therefore this function is likely to fail unless the user makes changes to
 * their system to permit symlink creation.Users can try enabling Developer
 * Mode, granting the `SeCreateSymbolicLinkPrivilege` privilege, or running
 * the process as an administrator.
 *
 * [symlink - security]: https:
 * ```
 */
public fun symlinkFile(original: Path, link: Path): Result<Unit> = symlinkInner(original, link, false)

/**
 * Creates a new symlink to a directory on the filesystem.
 *
 * The `link` path will be a directory symbolic link pointing to the `original`
 * path.
 *
 * The `original` path must be a directory or a symlink to a directory,
 * otherwise the symlink will be broken. Use [symlinkFile] for other files.
 *
 * This function currently corresponds to [`CreateSymbolicLinkW`][CreateSymbolicLinkW].
 * Note that this [may change in the future][changes].
 *
 * [CreateSymbolicLinkW]: https://docs.microsoft.com/en-us/windows/win32/api/winbase/nf-winbase-createsymboliclinkw
 * [changes]: io#platform-specific-behavior
 *
 * # Examples
 *
 * use std.os.windows.fs;
 *
 * fn main() -> std.io.Result<()> {
 *     fs.symlinkDir("a", "b")?;
 *     Ok(())
 * }
 * ```kotlin
 *
 * Windows treats symlink creation as a[privileged action][symlink - security],
 * therefore this function is likely to fail unless the user makes changes to
 * their system to permit symlink creation.Users can try enabling Developer
 * Mode, granting the `SeCreateSymbolicLinkPrivilege` privilege, or running
 * the process as an administrator.
 *
 * [symlink - security]: https:
 * ```
 */
public fun symlinkDir(original: Path, link: Path): Result<Unit> = symlinkInner(original, link, true)

/**
 * Creates a junction point.
 *
 * The `link` path will be a directory junction pointing to the original path.
 * If `link` is a relative path then it will be made absolute prior to creating the junction point.
 * The `original` path must be a directory or a link to a directory, otherwise the junction point will be broken.
 *
 * If either path is not a local file path then this will fail.
 */
public fun junctionPoint(original: Path, link: Path): Result<Unit> = io.github.kotlinmania.rustlang.std.sys.fs.windows.junctionPoint(original, link)
