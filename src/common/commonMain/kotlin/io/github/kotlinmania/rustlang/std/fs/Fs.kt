// port-lint: source fs.rs
package io.github.kotlinmania.rustlang.std.fs

import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.io.IoHandle
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.Seek
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.BufReader
import io.github.kotlinmania.rustlang.std.io.BufWriter
import io.github.kotlinmania.rustlang.std.io.ErrorKind
import io.github.kotlinmania.rustlang.std.io.defaultReadToEnd
import io.github.kotlinmania.rustlang.std.io.defaultReadToString
import io.github.kotlinmania.rustlang.std.io.streamLenDefault
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.AsInnerMut
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fs.symlink
import io.github.kotlinmania.rustlang.std.sys.fs.unix.FilePermissions
import io.github.kotlinmania.rustlang.std.sys.fs.unsupported.FileAttr
import io.github.kotlinmania.rustlang.std.time.SystemTime

// Filesystem manipulation operations.
//
// This module contains basic methods to manipulate the contents of the local
// filesystem. All methods in this module represent cross-platform filesystem
// operations. Extra platform-specific functionality can be found in the
// extension traits of `std.os.$ platform`.
//
// # Time of Check to Time of Use (TOCTOU)
//
// Many filesystem operations are subject to a race condition known as "Time of Check to Time of Use"
// (TOCTOU). This occurs when a program checks a condition (like file existence or permissions)
// and then uses the result of that check to make a decision, but the condition may have changed
// between the check and the use.
//
// For example, checking if a file exists and then creating it if it doesn't is vulnerable to
// TOCTOU - another process could create the file between your check and creation attempt.
//
// Another example is with symbolic links: when removing a directory, if another process replaces
// the directory with a symbolic link between the check and the removal operation, the removal
// might affect the wrong location. This is why operations like [removeDirAll] need to use
// atomic operations to prevent such race conditions.
//
// To avoid TOCTOU issues:
// - Be aware that metadata operations (like [metadata] or [symlinkMetadata]) may be affected by
// changes made by other processes.
// - Use atomic operations when possible (like [File.createNew] instead of checking existence then creating).
// - Keep file open for the duration of operations.

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

/**
 * An object providing access to an open file on the filesystem.
 *
 * An instance of a `File` can be read and/or written depending on what options
 * it was opened with. Files also implement [Seek] to alter the logical cursor
 * that the file contains internally.
 *
 * Files are automatically closed when they go out of scope.  Errors detected
 * on closing are ignored by the implementation of `Drop`.  Use the method
 * [syncAll] if these errors must be manually handled.
 *
 * `File` does not buffer reads and writes. For efficiency, consider wrapping the
 * file in a [BufReader] or [BufWriter] when performing many small [read]
 * or [write] calls, unless unbuffered reads and writes are required.
 *
 * # Examples
 *
 * Creates a new file and write bytes to it (you can also use [write]):
 *
 * ```kotlin
 * import std.fs.File
 * import std.io.prelude.*
 *
 * fun main(): Result<Unit> {
 *     var file = File.create("foo.txt").getOrElse { return Result.failure(it) }
 *     file.writeAll("Hello, world!".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Reads the contents of a file into a [String] (you can also use [read]):
 *
 * ```kotlin
 * import std.fs.File
 * import std.io.prelude.*
 *
 * fun main(): Result<Unit> {
 *     var file = File.open("foo.txt").getOrElse { return Result.failure(it) }
 *     var contents = ""
 *     file.readToString(contents).getOrElse { return Result.failure(it) }
 *     check(contents == "Hello, world!")
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Using a buffered [Read]er:
 *
 * ```kotlin
 * import std.fs.File
 * import std.io.BufReader
 * import std.io.prelude.*
 *
 * fun main(): Result<Unit> {
 *     val file = File.open("foo.txt").getOrElse { return Result.failure(it) }
 *     var bufReader = BufReader.new(file)
 *     var contents = ""
 *     bufReader.readToString(contents).getOrElse { return Result.failure(it) }
 *     check(contents == "Hello, world!")
 *     return Result.success(Unit)
 * }
 * ```
 *
 * Note that, although read and write methods require a `File`, because
 * of the interfaces for [Read] and [Write], the holder of a `File` can
 * still modify the file, either through methods that take `File` or by
 * retrieving the underlying OS object and modifying the file that way.
 * Additionally, many operating systems allow concurrent modification of files
 * by different processes. Avoid assuming that holding a `File` means that the
 * file will not change.
 *
 * # Platform-specific behavior
 *
 * On Windows, the implementation of [Read] and [Write] traits for `File`
 * perform synchronous I/O operations. Therefore the underlying file must not
 * have been opened for asynchronous I/O (e.g. by using `FILE_FLAG_OVERLAPPED`).
 *
 * [BufReader]: io.BufReader
 * [BufWriter]: io.BufWriter
 * [syncAll]: File.syncAll
 * [write]: File.write
 * [read]: File.read
 */
public open class File(
    public var inner: io.github.kotlinmania.rustlang.std.sys.fs.unix.File,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.fs.unix.File>, FromInner<io.github.kotlinmania.rustlang.std.sys.fs.unix.File, File>, IntoInner<io.github.kotlinmania.rustlang.std.sys.fs.unix.File>, Read, Write, Seek, IoHandle {
    /**
     * Attempts to sync all OS-internal file content and metadata to disk.
     *
     * This function will attempt to ensure that all in-memory data reaches the
     * filesystem before returning.
     *
     * This can be used to handle errors that would otherwise only be caught
     * when the `File` is closed, as dropping a `File` will ignore all errors.
     * Note, however, that `syncAll` is generally more expensive than closing
     * a file by dropping it, because the latter is not required to block until
     * the data has been written to the filesystem.
     *
     * If synchronizing the metadata is not required, use [syncData] instead.
     *
     * [syncData]: File.syncData
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.prelude.*
     *
     * fun main(): Result<Unit> {
     *     var f = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     f.writeAll("Hello, world!".encodeToByteArray()).getOrElse { return Result.failure(it) }
     *     f.syncAll().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun syncAll(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.fsync()

    /**
     * This function is similar to [syncAll], except that it might not
     * synchronize file metadata to the filesystem.
     *
     * This is intended for use cases that must synchronize content, but don't
     * need the metadata on disk. The goal of this method is to reduce disk
     * operations.
     *
     * Note that some platforms may simply implement this in terms of
     * [syncAll].
     *
     * [syncAll]: File.syncAll
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.prelude.*
     *
     * fun main(): Result<Unit> {
     *     var f = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     f.writeAll("Hello, world!".encodeToByteArray()).getOrElse { return Result.failure(it) }
     *     f.syncData().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun syncData(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.datasync()

    /**
     * Acquire an exclusive lock on the file. Blocks until the lock can be acquired.
     *
     * This acquires an exclusive lock. No *other* file handle to this file, in this or any other
     * process, may acquire another lock.
     * If this file handle/descriptor, or a clone of it, already holds a lock, the exact behavior
     * is unspecified and platform dependent, including the possibility that it will deadlock.
     * However, if this method returns, then an exclusive lock is held.
     *
     * This lock may be advisory or mandatory. This lock is meant to interact with [lock],
     * [tryLock], [lockShared], [tryLockShared], and [unlock]. Its interactions with
     * other methods, such as [read] and [write] are platform specific, and it may or may not
     * cause non-lockholders to block.
     *
     * If the file is not open for writing, it is unspecified whether this function returns an error.
     *
     * The lock will be released when this file (along with any other file descriptors/handles
     * duplicated or inherited from it) is closed, or if the [unlock] method is called.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `flock` function on Unix with the `LOCK_EX` flag,
     * and the `LockFileEx` function on Windows with the `LOCKFILE_EXCLUSIVE_LOCK` flag. Note that,
     * this [may change in the future][changes].
     *
     * On Windows, locking a file will fail if the file is opened only for append. To lock a file,
     * open it with one of `.read(true)`, `.read(true).append(true)`, or `.write(true)`.
     *
     * [changes]: io#platform-specific-behavior
     *
     * [lock]: File.lock
     * [lockShared]: File.lockShared
     * [tryLock]: File.tryLock
     * [tryLockShared]: File.tryLockShared
     * [unlock]: File.unlock
     * [read]: Read.read
     * [write]: Write.write
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     *
     * fun main(): Result<Unit> {
     *     val f = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     f.lock().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun lock(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.lock()

    /**
     * Acquire a shared (non-exclusive) lock on the file. Blocks until the lock can be acquired.
     *
     * This acquires a shared lock. More than one file handle to this file, in this or any other
     * process, may hold a shared lock, but no *other* file handle may hold an exclusive lock at
     * the same time.
     * If this file handle/descriptor, or a clone of it, already holds a lock, the exact
     * behavior is unspecified and platform dependent, including the possibility that it will
     * deadlock. However, if this method returns, then a shared lock is held.
     *
     * This lock may be advisory or mandatory. This lock is meant to interact with [lock],
     * [tryLock], [lockShared], [tryLockShared], and [unlock]. Its interactions with
     * other methods, such as [read] and [write] are platform specific, and it may or may not
     * cause non-lockholders to block.
     *
     * The lock will be released when this file (along with any other file descriptors/handles
     * duplicated or inherited from it) is closed, or if the [unlock] method is called.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `flock` function on Unix with the `LOCK_SH` flag,
     * and the `LockFileEx` function on Windows. Note that, this
     * [may change in the future][changes].
     *
     * On Windows, locking a file will fail if the file is opened only for append. To lock a file,
     * open it with one of `.read(true)`, `.read(true).append(true)`, or `.write(true)`.
     *
     * [changes]: io#platform-specific-behavior
     *
     * [lock]: File.lock
     * [lockShared]: File.lockShared
     * [tryLock]: File.tryLock
     * [tryLockShared]: File.tryLockShared
     * [unlock]: File.unlock
     * [read]: Read.read
     * [write]: Write.write
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     *
     * fun main(): Result<Unit> {
     *     val f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     f.lockShared().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun lockShared(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.lockShared()

    /**
     * Try to acquire an exclusive lock on the file.
     *
     * Returns `Err(TryLockError.WouldBlock)` if a different lock is already held on this file
     * (via another handle/descriptor).
     *
     * This acquires an exclusive lock; no other file handle to this file, in this or any other
     * process, may acquire another lock.
     *
     * This lock may be advisory or mandatory. This lock is meant to interact with [lock],
     * [tryLock], [lockShared], [tryLockShared], and [unlock]. Its interactions with
     * other methods, such as [read] and [write] are platform specific, and it may or may not
     * cause non-lockholders to block.
     *
     * If this file handle/descriptor, or a clone of it, already holds a lock, the exact behavior
     * is unspecified and platform dependent, including the possibility that it will deadlock.
     * However, if this method returns `Ok(())`, then it has acquired an exclusive lock.
     *
     * If the file is not open for writing, it is unspecified whether this function returns an error.
     *
     * The lock will be released when this file (along with any other file descriptors/handles
     * duplicated or inherited from it) is closed, or if the [unlock] method is called.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `flock` function on Unix with the `LOCK_EX` and
     * `LOCK_NB` flags, and the `LockFileEx` function on Windows with the `LOCKFILE_EXCLUSIVE_LOCK`
     * and `LOCKFILE_FAIL_IMMEDIATELY` flags. Note that, this
     * [may change in the future][changes].
     *
     * On Windows, locking a file will fail if the file is opened only for append. To lock a file,
     * open it with one of `.read(true)`, `.read(true).append(true)`, or `.write(true)`.
     *
     * [changes]: io#platform-specific-behavior
     *
     * [lock]: File.lock
     * [lockShared]: File.lockShared
     * [tryLock]: File.tryLock
     * [tryLockShared]: File.tryLockShared
     * [unlock]: File.unlock
     * [read]: Read.read
     * [write]: Write.write
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.fs.TryLockError
     *
     * fun main(): Result<Unit> {
     *     val f = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     // Explicit handling of the WouldBlock error
     *     run {
     *         val matchVal = f.tryLock()
     *         when {
     *             matchVal is Ok -> Unit
     *             matchVal is Err && matchVal.value == TryLockError.WouldBlock -> Unit
     *             // Lock not acquired
     *             else -> {
     *                 val (err) = matchVal.value as TryLockError.Error
     *                 return Result.failure(err)
     *             }
     *         }
     *     }
     *     // Alternately, propagate the error as an io.Error
     *     f.tryLock().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun tryLock(): Result<Unit, TryLockError> = this.inner.tryLock()

    /**
     * Try to acquire a shared (non-exclusive) lock on the file.
     *
     * Returns `Err(TryLockError.WouldBlock)` if a different lock is already held on this file
     * (via another handle/descriptor).
     *
     * This acquires a shared lock; more than one file handle, in this or any other process, may
     * hold a shared lock, but none may hold an exclusive lock at the same time.
     *
     * This lock may be advisory or mandatory. This lock is meant to interact with [lock],
     * [tryLock], [lockShared], [tryLockShared], and [unlock]. Its interactions with
     * other methods, such as [read] and [write] are platform specific, and it may or may not
     * cause non-lockholders to block.
     *
     * If this file handle, or a clone of it, already holds a lock, the exact behavior is
     * unspecified and platform dependent, including the possibility that it will deadlock.
     * However, if this method returns `Ok(())`, then it has acquired a shared lock.
     *
     * The lock will be released when this file (along with any other file descriptors/handles
     * duplicated or inherited from it) is closed, or if the [unlock] method is called.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `flock` function on Unix with the `LOCK_SH` and
     * `LOCK_NB` flags, and the `LockFileEx` function on Windows with the
     * `LOCKFILE_FAIL_IMMEDIATELY` flag. Note that, this
     * [may change in the future][changes].
     *
     * On Windows, locking a file will fail if the file is opened only for append. To lock a file,
     * open it with one of `.read(true)`, `.read(true).append(true)`, or `.write(true)`.
     *
     * [changes]: io#platform-specific-behavior
     *
     * [lock]: File.lock
     * [lockShared]: File.lockShared
     * [tryLock]: File.tryLock
     * [tryLockShared]: File.tryLockShared
     * [unlock]: File.unlock
     * [read]: Read.read
     * [write]: Write.write
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.fs.TryLockError
     *
     * fun main(): Result<Unit> {
     *     val f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     // Explicit handling of the WouldBlock error
     *     run {
     *         val matchVal = f.tryLockShared()
     *         when {
     *             matchVal is Ok -> Unit
     *             matchVal is Err && matchVal.value == TryLockError.WouldBlock -> Unit
     *             // Lock not acquired
     *             else -> {
     *                 val (err) = matchVal.value as TryLockError.Error
     *                 return Result.failure(err)
     *             }
     *         }
     *     }
     *     // Alternately, propagate the error as an io.Error
     *     f.tryLockShared().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun tryLockShared(): Result<Unit, TryLockError> = this.inner.tryLockShared()

    /**
     * Release all locks on the file.
     *
     * All locks are released when the file (along with any other file descriptors/handles
     * duplicated or inherited from it) is closed. This method allows releasing locks without
     * closing the file.
     *
     * If no lock is currently held via this file descriptor/handle, this method may return an
     * error, or may return successfully without taking any action.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `flock` function on Unix with the `LOCK_UN` flag,
     * and the `UnlockFile` function on Windows. Note that, this
     * [may change in the future][changes].
     *
     * On Windows, locking a file will fail if the file is opened only for append. To lock a file,
     * open it with one of `.read(true)`, `.read(true).append(true)`, or `.write(true)`.
     *
     * [changes]: io#platform-specific-behavior
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     *
     * fun main(): Result<Unit> {
     *     val f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     f.lock().getOrElse { return Result.failure(it) }
     *     f.unlock().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun unlock(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.unlock()

    /**
     * Truncates or extends the underlying file, updating the size of
     * this file to become `size`.
     *
     * If the `size` is less than the current file's size, then the file will
     * be shrunk. If it is greater than the current file's size, then the file
     * will be extended to `size` and have all of the intermediate data filled
     * in with 0s.
     *
     * The file's cursor isn't changed. In particular, if the cursor was at the
     * end and the file is shrunk using this operation, the cursor will now be
     * past the end.
     *
     * # Errors
     *
     * This function will return an error if the file is not opened for writing.
     * Also, [`std.io.ErrorKind.InvalidInput`](io.ErrorKind.InvalidInput)
     * will be returned if the desired length would cause an overflow due to
     * the implementation specifics.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     *
     * fun main(): Result<Unit> {
     *     var f = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     f.setLen(10).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     *
     * Note that this method alters the content of the underlying file, even
     * though it takes `self` rather than `self`.
     */
    public open fun setLen(size: ULong): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.truncate(size)

    /**
     * Queries metadata about the underlying file.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     *
     * fun main(): Result<Unit> {
     *     var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     val metadata = f.metadata().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun metadata(): Result<Metadata, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.fileAttr().map({ Metadata(it) })

    /**
     * Creates a new `File` instance that shares the same underlying file handle
     * as the existing `File` instance. Reads, writes, and seeks will affect
     * both `File` instances simultaneously.
     *
     * # Examples
     *
     * Creates two handles for a file named `foo.txt`:
     *
     * ```kotlin
     * import std.fs.File
     *
     * fun main(): Result<Unit> {
     *     var file = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     val fileCopy = file.tryClone().getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     *
     * Assuming there’s a file named `foo.txt` with contents `abcdef\n`, create
     * two handles, seek one of them, and read the remaining bytes from the
     * other handle:
     *
     * ```kotlin
     * import std.fs.File
     * import std.io.SeekFrom
     * import std.io.prelude.*
     *
     * fun main(): Result<Unit> {
     *     var file = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var fileCopy = file.tryClone().getOrElse { return Result.failure(it) }
     *     file.seek(SeekFrom.Start(3)).getOrElse { return Result.failure(it) }
     *     var contents = mutableListOf()
     *     fileCopy.readToEnd(contents).getOrElse { return Result.failure(it) }
     *     check(contents == "def\n".encodeToByteArray())
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun tryClone(): Result<File, io.github.kotlinmania.rustlang.std.io.error.Error> {
        return Result.Ok<File, io.github.kotlinmania.rustlang.std.io.error.Error>(File(inner = when (val tryResult = this.inner.duplicate()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<File, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }))
    }

    /**
     * Changes the permissions on the underlying file.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `fchmod` function on Unix and
     * the `SetFileInformationByHandle` function on Windows. Note that, this
     * [may change in the future][changes].
     *
     * [changes]: io#platform-specific-behavior
     *
     * # Errors
     *
     * This function will return an error if the user lacks permission change
     * attributes on the underlying file. It may also return an error in other
     * os-specific unspecified cases.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     *
     * fun main(): Result<Unit> {
     *     val file = File.open("foo.txt").getOrElse { return Result.failure(it) }
     *     var perms = file.metadata().getOrElse { return Result.failure(it) }.permissions()
     *     perms.setReadonly(true)
     *     file.setPermissions(perms).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     *
     * Note that this method alters the permissions of the underlying file,
     * even though it takes `self` rather than `self`.
     */
    public open fun setPermissions(perm: Permissions): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.setPermissions(perm.value)

    /**
     * Changes the timestamps of the underlying file.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `futimens` function on Unix (falling back to
     * `futimes` on macOS before 10.13) and the `SetFileTime` function on Windows. Note that this
     * [may change in the future][changes].
     *
     * On most platforms, including UNIX and Windows platforms, this function can also change the
     * timestamps of a directory. To get a `File` representing a directory in order to call
     * `setTimes`, open the directory with `File.open` without attempting to obtain write
     * permission.
     *
     * [changes]: io#platform-specific-behavior
     *
     * # Errors
     *
     * This function will return an error if the user lacks permission to change timestamps on the
     * underlying file. It may also return an error in other os-specific unspecified cases.
     *
     * This function may return an error if the operating system lacks support to change one or
     * more of the timestamps set in the `FileTimes` structure.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     * import std.fs.FileTimes
     *
     * fun main(): Result<Unit> {
     *     val src = fs.metadata("src").getOrElse { return Result.failure(it) }
     *     val dest = File.open("dest").getOrElse { return Result.failure(it) }
     *     val times = FileTimes.new().setAccessed(src.accessed().getOrElse { return Result.failure(it) }).setModified(src.modified().getOrElse { return Result.failure(it) })
     *     dest.setTimes(times).getOrElse { return Result.failure(it) }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun setTimes(times: FileTimes): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.setTimes(times.value)

    /**
     * Changes the modification time of the underlying file.
     *
     * This is an alias for `setTimes(FileTimes.new().setModified(time))`.
     */
    public open fun setModified(time: SystemTime): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.setTimes(FileTimes.new().setModified(time))

    // In addition to the `implementation`s here, `File` also has `implementation`s for
    // `AsFd`/`From<OwnedFd>`/`Into<OwnedFd>` and
    // `AsRawFd`/`IntoRawFd`/`FromRawFd`, on Unix and WASI, and
    // `AsHandle`/`From<OwnedHandle>`/`Into<OwnedHandle>` and
    // `AsRawHandle`/`IntoRawHandle`/`FromRawHandle` on Windows.
    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.fs.unix.File = this.inner

    public override fun intoInner(): io.github.kotlinmania.rustlang.std.sys.fs.unix.File = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.inner.fmt(f)

    /**
     * Reads some bytes from the file.
     *
     * See [Read.read] docs for more info.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `read` function on Unix and
     * the `NtReadFile` function on Windows. Note that this [may change in
     * the future][changes].
     *
     * [changes]: io#platform-specific-behavior
     */
    public open fun read(buf: MutableList<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.read(buf)

    /**
     * Like `read`, except that it reads into a slice of buffers.
     *
     * See [Read.readVectored] docs for more info.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `readv` function on Unix and
     * falls back to the `read` implementation on Windows. Note that this
     * [may change in the future][changes].
     *
     * [changes]: io#platform-specific-behavior
     */
    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.readVectored(bufs)

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.readBuf(cursor)

    /**
     * Determines if `File` has an efficient `readVectored` implementation.
     *
     * See [Read.isReadVectored] docs for more info.
     *
     * # Platform-specific behavior
     *
     * This function currently returns `true` on Unix and `false` on Windows.
     * Note that this [may change in the future][changes].
     *
     * [changes]: io#platform-specific-behavior
     */
    public open fun isReadVectored(): Boolean = this.inner.isReadVectored()

    // Reserves space in the buffer based on the file size when available.
    public open fun readToEnd(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> {
        val size = bufferCapacityRequired(this)
        when (val tryResult = buf.tryReserve((size ?: 0))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Int, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        return defaultReadToEnd(this, buf, size)
    }

    // Reserves space in the buffer based on the file size when available.
    public open fun readToString(buf: String): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> {
        val size = bufferCapacityRequired(this)
        when (val tryResult = buf.tryReserve((size ?: 0))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Int, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        return defaultReadToString(this, buf, size)
    }

    /**
     * Writes some bytes to the file.
     *
     * See [Write.write] docs for more info.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `write` function on Unix and
     * the `NtWriteFile` function on Windows. Note that this [may change in
     * the future][changes].
     *
     * [changes]: io#platform-specific-behavior
     */
    public open fun write(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.write(buf)

    /**
     * Like `write`, except that it writes into a slice of buffers.
     *
     * See [Write.writeVectored] docs for more info.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `writev` function on Unix
     * and falls back to the `write` implementation on Windows. Note that this
     * [may change in the future][changes].
     *
     * [changes]: io#platform-specific-behavior
     */
    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.writeVectored(bufs)

    /**
     * Determines if `File` has an efficient `writeVectored` implementation.
     *
     * See [Write.isWriteVectored] docs for more info.
     *
     * # Platform-specific behavior
     *
     * This function currently returns `true` on Unix and `false` on Windows.
     * Note that this [may change in the future][changes].
     *
     * [changes]: io#platform-specific-behavior
     */
    public open fun isWriteVectored(): Boolean = this.inner.isWriteVectored()

    /**
     * Flushes the file, ensuring that all intermediately buffered contents
     * reach their destination.
     *
     * See [Write.flush] docs for more info.
     *
     * # Platform-specific behavior
     *
     * Since a `File` structure doesn't contain any buffers, this function is
     * currently a no-op on Unix and Windows. Note that this [may change in
     * the future][changes].
     *
     * [changes]: io#platform-specific-behavior
     */
    public open fun flush(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.flush()

    /**
     * Seek to an offset, in bytes in a file.
     *
     * See [Seek.seek] docs for more info.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `lseek64` function on Unix
     * and the `SetFilePointerEx` function on Windows. Note that this [may
     * change in the future][changes].
     *
     * [changes]: io#platform-specific-behavior
     */
    public open fun seek(pos: SeekFrom): Result<ULong, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.seek(pos)

    /**
     * Returns the length of this file (in bytes).
     *
     * See [Seek.streamLen] docs for more info.
     *
     * # Platform-specific behavior
     *
     * This function currently corresponds to the `statx` function on Linux
     * (with fallbacks) and the `GetFileSizeEx` function on Windows. Note that
     * this [may change in the future][changes].
     *
     * [changes]: io#platform-specific-behavior
     */
    public open fun streamLen(): Result<ULong, io.github.kotlinmania.rustlang.std.io.error.Error> {
        when (val matchVal = this.inner.size()) {
            null -> {}
            else -> {
                val result = matchVal
                return result
            }
        }
        return streamLenDefault(this)
    }

    public open fun streamPosition(): Result<ULong, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.tell()

    public open fun readVectoredRead(bufs: MutableList<IoSliceMut>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = (this).readVectored(bufs)

    public open fun readBufRead(cursor: BorrowedCursor<UByte>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = (this).readBuf(cursor)

    public open fun isReadVectoredRead(): Boolean = (this).isReadVectored()

    public open fun readToEndRead(buf: List<UByte>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = (this).readToEnd(buf)

    public open fun readToStringRead(buf: String): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = (this).readToString(buf)

    public open fun writeVectoredWrite(bufs: List<IoSlice>): Result<Int, io.github.kotlinmania.rustlang.std.io.error.Error> = (this).writeVectored(bufs)

    public open fun isWriteVectoredWrite(): Boolean = (this).isWriteVectored()

    public open fun flushWrite(): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = (this).flush()

    public open fun streamLenSeek(): Result<ULong, io.github.kotlinmania.rustlang.std.io.error.Error> = (this).streamLen()

    public open fun streamPositionSeek(): Result<ULong, io.github.kotlinmania.rustlang.std.io.error.Error> = (this).streamPosition()

    public companion object {
        /**
         * Attempts to open a file in read-only mode.
         *
         * See the [OpenOptions.open] method for more details.
         *
         * If you only need to read the entire file contents,
         * consider [`std.fs.read`][read] or
         * [`std.fs.readToString`][readToString] instead.
         *
         * # Errors
         *
         * This function will return an error if `path` does not already exist.
         * Other errors may also be returned according to [OpenOptions.open].
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.File
         * import std.io.Read
         *
         * fun main(): Result<Unit> {
         *     var f = File.open("foo.txt").getOrElse { return Result.failure(it) }
         *     var data = mutableListOf()
         *     f.readToEnd(data).getOrElse { return Result.failure(it) }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun open(path: Path): Result<File, io.github.kotlinmania.rustlang.std.io.error.Error> = OpenOptions.new().read(true).open(path)

        /**
         * Attempts to open a file in read-only mode with buffering.
         *
         * See the [OpenOptions.open] method, the [`BufReader`][io.BufReader] type,
         * and the [`BufRead`][io.BufRead] trait for more details.
         *
         * If you only need to read the entire file contents,
         * consider [`std.fs.read`][read] or
         * [`std.fs.readToString`][readToString] instead.
         *
         * # Errors
         *
         * This function will return an error if `path` does not already exist,
         * or if memory allocation fails for the new buffer.
         * Other errors may also be returned according to [OpenOptions.open].
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.File
         * import std.io.BufRead
         *
         * @feature(fileBuffered)
         * public fun doctestEntry(): Result<Unit> {
         *     fun main(): Result<Unit> {
         *         var f = File.openBuffered("foo.txt").getOrElse { return Result.failure(it) }
         *         check(f.capacity() > 0)
         *         for ((line, i) in (f.lines().zip(1 until Int.MAX_VALUE))) {
         *             println("$i: ${line.getOrElse { return Result.failure(it) }}")
         *         }
         *         return Result.success(Unit)
         *     }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun openBuffered(path: Path): Result<BufReader<File>, io.github.kotlinmania.rustlang.std.io.error.Error> {
            // Allocate the buffer *first* so we don't affect the filesystem otherwise.
            return BufReader.tryNewWith({ File.open(path) })
        }

        /**
         * Opens a file in write-only mode.
         *
         * This function will create a file if it does not exist,
         * and will truncate it if it does.
         *
         * Depending on the platform, this function may fail if the
         * full directory path does not exist.
         * See the [OpenOptions.open] function for more details.
         *
         * See also [`std.fs.write`][write] for a simple function to
         * create a file with some given data.
         *
         * # Examples
         *
         * ```kotlin
         * @file:OptIn(ExperimentalUnsignedTypes.cls)
         *
         * import std.fs.File
         * import std.io.Write
         *
         * public fun doctestEntry(): Result<Unit> {
         *     fun main(): Result<Unit> {
         *         var f = File.create("foo.txt").getOrElse { return Result.failure(it) }
         *         f.writeAll(run { val rawBits = 1234u; ubyteArrayOf((rawBits shr 24).toUByte(), (rawBits shr 16).toUByte(), (rawBits shr 8).toUByte(), rawBits.toUByte()) }).getOrElse { return Result.failure(it) }
         *         return Result.success(Unit)
         *     }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun create(path: Path): Result<File, io.github.kotlinmania.rustlang.std.io.error.Error> = OpenOptions.new().write(true).create(true).truncate(true).open(path)

        /**
         * Opens a file in write-only mode with buffering.
         *
         * This function will create a file if it does not exist,
         * and will truncate it if it does.
         *
         * Depending on the platform, this function may fail if the
         * full directory path does not exist.
         *
         * See the [OpenOptions.open] method and the
         * [`BufWriter`][io.BufWriter] type for more details.
         *
         * See also [`std.fs.write`][write] for a simple function to
         * create a file with some given data.
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.File
         * import std.io.Write
         *
         * @feature(fileBuffered)
         * public fun doctestEntry(): Result<Unit> {
         *     fun main(): Result<Unit> {
         *         var f = File.createBuffered("foo.txt").getOrElse { return Result.failure(it) }
         *         check(f.capacity() > 0)
         *         for (i in 0 until 100) {
         *             f.appendLine("$i")
         *         }
         *         f.flush().getOrElse { return Result.failure(it) }
         *         return Result.success(Unit)
         *     }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun createBuffered(path: Path): Result<BufWriter<File>, io.github.kotlinmania.rustlang.std.io.error.Error> {
            // Allocate the buffer *first* so we don't affect the filesystem otherwise.
            return BufWriter.tryNewWith({ File.create(path) })
        }

        /**
         * Creates a new file in read-write mode; error if the file exists.
         *
         * This function will create a file if it does not exist, or return an error if it does. This
         * way, if the call succeeds, the file returned is guaranteed to be new.
         * If a file exists at the target location, creating a new file will fail with [AlreadyExists]
         * or another error based on the situation. See [OpenOptions.open] for a
         * non-exhaustive list of likely errors.
         *
         * This option is useful because it is atomic. Otherwise between checking whether a file
         * exists and creating a new one, the file may have been created by another process (a [TOCTOU]
         * race condition / attack).
         *
         * This can also be written using
         * `File.options().read(true).write(true).createNew(true).open(...)`.
         *
         * [TOCTOU]: self#time-of-check-to-time-of-use-toctou
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.File
         * import std.io.Write
         *
         * fun main(): Result<Unit> {
         *     var f = File.createNew("foo.txt").getOrElse { return Result.failure(it) }
         *     f.writeAll(("Hello, world!").encodeToByteArray().map { it.toUByte() }).getOrElse { return Result.failure(it) }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun createNew(path: Path): Result<File, io.github.kotlinmania.rustlang.std.io.error.Error> = OpenOptions.new().read(true).write(true).createNew(true).open(path)

        /**
         * Returns a new OpenOptions object.
         *
         * This function returns a new OpenOptions object that you can use to
         * open or create a file with specific options if `open()` or `create()`
         * are not appropriate.
         *
         * It is equivalent to `OpenOptions.new()`, but allows you to write more
         * readable code. Instead of
         * `OpenOptions.new().append(true).open("example.log")`,
         * you can write `File.options().append(true).open("example.log")`. This
         * also avoids the need to import `OpenOptions`.
         *
         * See the [OpenOptions.new] function for more details.
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.File
         * import std.io.Write
         *
         * fun main(): Result<Unit> {
         *     var f = File.options().append(true).open("example.log").getOrElse { return Result.failure(it) }
         *     f.appendLine("new line")
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun options(): OpenOptions = OpenOptions.new()

        public fun fromInner(f: io.github.kotlinmania.rustlang.std.sys.fs.unix.File): File = File(inner = f)
    }
}

/**
 * An enumeration of possible errors which can occur while trying to acquire a lock
 * from the [tryLock] method and [tryLockShared] method on a [File].
 *
 * [tryLock]: File.tryLock
 * [tryLockShared]: File.tryLockShared
 */
public sealed class TryLockError {
    /**
     * The lock could not be acquired due to an I/O error on the file. The standard library will
     * not return an [ErrorKind.WouldBlock] error inside [TryLockError.Error]
     *
     * [ErrorKind.WouldBlock]: io.ErrorKind.WouldBlock
     */
    public data class Error(public var value: Error) : TryLockError()
    /**
     * The lock could not be acquired at this time because it is held by another handle/process.
     */
    public data object WouldBlock : TryLockError()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> =
        when (this) {
            is TryLockError.Error -> {
                val (err) = this
                err.fmt(f)
            }
            TryLockError.WouldBlock -> "WouldBlock".fmt(f)
        }

    public open fun fmtDisplay(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> =
        when (this) {
            is TryLockError.Error -> "lock acquisition failed due to I/O error"
            TryLockError.WouldBlock -> "lock acquisition failed because the operation would block"
        }.fmt(f)
}

/**
 * An object providing access to a directory on the filesystem.
 *
 * Directories are automatically closed when they go out of scope.  Errors detected
 * on closing are ignored by the implementation of `Drop`.
 *
 * # Platform-specific behavior
 *
 * On supported systems (including Windows and some UNIX-based OSes), this function acquires a
 * handle/file descriptor for the directory. This allows functions like [Dir.openFile] to
 * avoid [TOCTOU] errors when the directory itself is being moved.
 *
 * On other systems, it stores an absolute path (see [canonicalize]). In the latter case, no
 * [TOCTOU] guarantees are made.
 *
 * # Examples
 *
 * Opens a directory and then a file inside it.
 *
 * ```kotlin
 * import std.fs.Dir
 * import std.io
 *
 * @feature(dirfd)
 * public fun doctestEntry(): Result<Unit> {
 *     fun main(): io.Result<Unit> {
 *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
 *         var file = dir.openFile("bar.txt").getOrElse { return Result.failure(it) }
 *         val contents = io.readToString(file).getOrElse { return Result.failure(it) }
 *         check(contents == "Hello, world!")
 *         return Result.success(Unit)
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * [TOCTOU]: self#time-of-check-to-time-of-use-toctou
 */
public open class Dir(
    public var inner: io.github.kotlinmania.rustlang.std.sys.fs.common.Dir,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.fs.common.Dir>, FromInner<io.github.kotlinmania.rustlang.std.sys.fs.common.Dir, Dir>, IntoInner<io.github.kotlinmania.rustlang.std.sys.fs.common.Dir> {
    /**
     * Queries metadata about the underlying directory.
     *
     * This is equivalent to `dir.metadata(".")` but can be more efficient.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.Dir
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         val metadata = dir.selfMetadata().getOrElse { return Result.failure(it) }
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun selfMetadata(): Result<Metadata, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.selfMetadata().map({ Metadata(it) })

    /**
     * Attempts to open a file in read-only mode relative to this directory.
     *
     * This function interprets `path` relative to the directory provided by `self`. To open a file
     * relative to the current working directory, or at an absolute path, see [File.open].
     *
     * # Errors
     *
     * This function will return an error if `path` does not point to an existing file.
     * Other errors may also be returned according to [OpenOptions.open].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.Dir
     * import std.io
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): io.Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         var f = dir.openFile("bar.txt").getOrElse { return Result.failure(it) }
     *         val contents = io.readToString(f).getOrElse { return Result.failure(it) }
     *         check(contents == "Hello, world!")
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun openFile(path: Path): Result<File, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.openFile(path, OpenOptions.new().read(true).value).map({ f -> File(inner = f) })

    /**
     * Attempts to open a file according to `opts` relative to this directory.
     *
     * This function interprets `path` relative to the directory provided by `self`. To open a file
     * relative to the current working directory, or at an absolute path, see [File.open].
     *
     * # Errors
     *
     * This function will return an error if `path` does not point to an existing file.
     * Other errors may also be returned according to [OpenOptions.open].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.Dir
     * import std.fs.OpenOptions
     * import std.io.Write
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): io.Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         var opts = OpenOptions.new()
     *         opts.read(true).write(true)
     *         var f = dir.openFileWith("bar.txt", opts).getOrElse { return Result.failure(it) }
     *         f.writeAll("Hello, world!".encodeToByteArray()).getOrElse { return Result.failure(it) }
     *         val contents = io.readToString(f).getOrElse { return Result.failure(it) }
     *         check(contents == "Hello, world!")
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun openFileWith(path: Path, opts: OpenOptions): Result<File, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.openFile(path, opts.value).map({ f -> File(inner = f) })

    /**
     * Attempts to remove a file relative to this directory.
     *
     * This function interprets `path` relative to the directory provided by `self`. To remove a file
     * relative to the current working directory, or at an absolute path, see [`fs.removeFile`][removeFile].
     *
     * # Errors
     *
     * This function will return an error if `path` does not point to an existing file.
     * Other errors may also be returned according to [OpenOptions.open].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.Dir
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         dir.removeFile("bar.txt").getOrElse { return Result.failure(it) }
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun removeFile(path: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.removeFile(path)

    /**
     * Attempts to rename a file or directory relative to this directory to a new name, replacing
     * the destination file if present.
     *
     * This function interprets `from` relative to the directory provided by `self` and `to` relative to the directory
     * provided by `toDir`. To rename a file relative to the current working directory, or at an absolute path, see [`fs.rename`][rename].
     *
     * # Errors
     *
     * This function will return an error if `from` does not point to an existing file or directory.
     * Other errors may also be returned according to [OpenOptions.open].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.Dir
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         dir.rename("bar.txt", dir, "quux.txt").getOrElse { return Result.failure(it) }
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun rename(from: Path, toDir: Dir, to: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.rename(from, toDir.inner, to)

    /**
     * Attempts to create a directory relative to this directory.
     *
     * This function interprets `path` relative to the directory provided by `self`. To create a directory
     * relative to the current working directory, or at an absolute path, see
     * [`fs.createDir`][fs.createDir].
     */
    public open fun createDir(path: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.createDir(path)

    /**
     * Attempts to open a directory in read-only mode relative to this directory.
     *
     * This function interprets `path` relative to the directory provided by `self`. To open a directory
     * relative to the current working directory, or at an absolute path, see [Dir.open].
     *
     * # Errors
     *
     * This function will return an error if `path` does not point to an existing directory.
     * Other errors may also be returned according to [OpenOptions.open].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.Dir
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         val foobar = dir.openDir("bar").getOrElse { return Result.failure(it) }
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun openDir(path: Path): Result<Dir, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.openDir(path, OpenOptions.new().read(true).value).map({ inner -> Dir(inner = inner) })

    /**
     * Attempts to open a directory relative to this directory according to `opts`.
     *
     * This function interprets `path` relative to the directory provided by `self`. To open a directory
     * relative to the current working directory, or at an absolute path, see [Dir.open].
     *
     * # Errors
     *
     * This function will return errors according to [OpenOptions.open].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.Dir
     * import std.fs.OpenOptions
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         val foobarW = dir.openDirWith("bar", OpenOptions.new().write(true)).getOrElse { return Result.failure(it) }
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun openDirWith(path: Path, opts: OpenOptions): Result<Dir, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.openDir(path, opts.value).map({ inner -> Dir(inner = inner) })

    /**
     * Attempts to remove a directory relative to this directory.
     *
     * This function interprets `path` relative to the directory provided by `self`. To remove a directory
     * relative to the current working directory, or at an absolute path, see
     * [`fs.removeDir`][fs.removeDir].
     *
     * # Errors
     *
     * This function will return an error if `path` does not point to an existing directory.
     * Other errors may also be returned according to [OpenOptions.open].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.Dir
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         dir.removeDir("bar").getOrElse { return Result.failure(it) }
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun removeDir(path: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.removeDir(path)

    /**
     * Creates a new `Dir` instance that shares the same underlying directory handle
     * as the existing `Dir` instance.
     *
     * # Examples
     *
     * Creates two handles for a directory named `foo`:
     *
     * ```kotlin
     * import std.fs.Dir
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         val dirCopy = dir.tryClone().getOrElse { return Result.failure(it) }
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun tryClone(): Result<Dir, io.github.kotlinmania.rustlang.std.io.error.Error> {
        return Result.Ok<Dir, io.github.kotlinmania.rustlang.std.io.error.Error>(Dir(inner = when (val tryResult = this.inner.duplicate()) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Dir, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }))
    }

    /**
     * Queries the file system to get information about a file, directory, etc. relative to this
     * directory.
     *
     * This function will traverse symbolic links to query information about the destination file.
     * To query metadata about the path itself without following symbolic links, use
     * [`symlinkMetadata`][Self.symlinkMetadata].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.Dir
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         val metadata = dir.metadata("subdir/file.txt").getOrElse { return Result.failure(it) }
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun metadata(path: Path): Result<Metadata, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.metadata(path).map({ Metadata(it) })

    /**
     * Queries the file system to get information about a file, directory, etc. relative to this
     * directory.
     *
     * This function will return the [Metadata] of the exact path without traversing symbolic
     * links to a resolved destination file. Using this function on a path that is a file or
     * directory (not a symbolic link) will behave the same as [`metadata`][Self.metadata].
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.Dir
     *
     * @feature(dirfd)
     * public fun doctestEntry(): Result<Unit> {
     *     fun main(): Result<Unit> {
     *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
     *         val metadata = dir.symlinkMetadata("subdir/file.txt").getOrElse { return Result.failure(it) }
     *         return Result.success(Unit)
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun symlinkMetadata(path: Path): Result<Metadata, io.github.kotlinmania.rustlang.std.io.error.Error> = this.inner.symlinkMetadata(path).map({ Metadata(it) })

    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.fs.common.Dir = this.inner

    public override fun intoInner(): io.github.kotlinmania.rustlang.std.sys.fs.common.Dir = this.inner

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.inner.fmt(f)

    public companion object {
        /**
         * Attempts to open a directory at `path` in read-only mode.
         *
         * This function opens a directory. To open a file instead, see [File.open].
         *
         * # Errors
         *
         * This function will return an error if `path` does not point to an existing directory.
         * Other errors may also be returned according to [OpenOptions.open].
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.Dir
         * import std.io
         *
         * @feature(dirfd)
         * public fun doctestEntry(): Result<Unit> {
         *     fun main(): io.Result<Unit> {
         *         val dir = Dir.open("foo").getOrElse { return Result.failure(it) }
         *         var f = dir.openFile("bar.txt").getOrElse { return Result.failure(it) }
         *         val contents = io.readToString(f).getOrElse { return Result.failure(it) }
         *         check(contents == "Hello, world!")
         *         return Result.success(Unit)
         *     }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun open(path: Path): Result<Dir, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.common.Dir.open(path, OpenOptions.new().read(true).value).map({ inner -> Dir(inner = inner) })

        /**
         * Attempts to open a directory at `path` according to `opts`.
         *
         * This function opens a directory. To open a file instead, see [File.open].
         *
         * # Errors
         *
         * This function will return an error if `path` does not point to an existing directory.
         * Other errors may also be returned according to [OpenOptions.open].
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.Dir
         * import std.fs.OpenOptions
         * import std.io
         *
         * @feature(dirfd)
         * public fun doctestEntry(): Result<Unit> {
         *     fun main(): io.Result<Unit> {
         *         val dir = Dir.openWith("foo", OpenOptions.new().read(true)).getOrElse { return Result.failure(it) }
         *         var f = dir.openFile("bar.txt").getOrElse { return Result.failure(it) }
         *         val contents = io.readToString(f).getOrElse { return Result.failure(it) }
         *         check(contents == "Hello, world!")
         *         return Result.success(Unit)
         *     }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun openWith(path: Path, opts: OpenOptions): Result<Dir, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.common.Dir.open(path, opts.value).map({ inner -> Dir(inner = inner) })

        /**
         * Attempts to open a directory at `path` with the minimum permissions for traversal.
         *
         * The permissions requested by this function are guaranteed to be sufficient to open a child
         * file or folder, but not necessarily to list all children.
         *
         * # Errors
         *
         * This function may return an error according to [OpenOptions.open].
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.Dir
         * import std.io
         *
         * @feature(dirfd)
         * public fun doctestEntry(): Result<Unit> {
         *     fun main(): io.Result<Unit> {
         *         val foo = Dir.openForTraversal("foo").getOrElse { return Result.failure(it) }
         *         val foobar = foo.openDir("bar").getOrElse { return Result.failure(it) }
         *         var foobarbaz = foobar.openFile("baz").getOrElse { return Result.failure(it) }
         *         val contents = io.readToString(foobarbaz).getOrElse { return Result.failure(it) }
         *         check(contents == "Hello, world!")
         *         return Result.success(Unit)
         *     }
         *     return Result.success(Unit)
         * }
         * ```
         */
        public fun openForTraversal(path: Path): Result<Dir, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.common.Dir.openForTraversal(path).map({ inner -> Dir(inner = inner) })

        public fun fromInner(f: io.github.kotlinmania.rustlang.std.sys.fs.common.Dir): Dir = Dir(inner = f)
    }
}

/**
 * Metadata information about a file.
 *
 * This structure is returned from the [metadata] or
 * [symlinkMetadata] function or method and represents known
 * metadata about a file such as its permissions, size, modification
 * times, etc.
 */
public open class Metadata(
    public var value: FileAttr,
) : IntoInner<FileAttr>, AsInner<FileAttr>, FromInner<FileAttr, Metadata> {
    public var field0: FileAttr
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Metadata = Metadata(value)

    /**
     * Returns the file type for this metadata.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = metadata("foo.txt").getOrElse { return Result.failure(it) }
     *     println("${metadata.fileType()}")
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun fileType(): FileType = FileType(this.value.fileType())

    /**
     * Returns `true` if this metadata is for a directory. The result is mutually exclusive to the
     * result of [Metadata.isFile] and [Metadata.isSymlink] (i.e., at most one of these
     * three methods returns `true`).
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = metadata("foo.txt").getOrElse { return Result.failure(it) }
     *     check(!metadata.isDir())
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun isDir(): Boolean = this.fileType().isDir()

    /**
     * Returns `true` if this metadata is for a regular file. The result is mutually exclusive to
     * the result of [Metadata.isDir] and [Metadata.isSymlink] (i.e., at most one of these
     * three methods returns `true`).
     *
     * When the goal is simply to read from (or write to) the source, the most
     * reliable way to test the source can be read (or written to) is to open
     * it. Only using `isFile` can break workflows like `diff<(progA)` on
     * a Unix-like system for example. See [File.open] or
     * [OpenOptions.open] for more information.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = metadata("foo.txt").getOrElse { return Result.failure(it) }
     *     check(metadata.isFile())
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun isFile(): Boolean = this.fileType().isFile()

    /**
     * Returns `true` if this metadata is for a symbolic link.
     *
     * # Examples
     *
     * use std.fs;
     * use std.path.Path;
     * use std.os.unix.fs.symlink;
     *
     * fn main() -> std.io.Result<()> (
     *     let linkPath = Path.new("link");
     *     symlink("/originDoesNotExist/", linkPath)?;
     *
     *     let metadata = fs.symlinkMetadata(linkPath)?;
     *
     *     assert(metadata.isSymlink());
     *     Ok(())
     * )
     * ```kotlin
     */
    public open fun isSymlink(): Boolean = this.fileType().isSymlink()

    /**
     * Returns the size of the file, in bytes, this metadata is for.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = metadata("foo.txt").getOrElse { return Result.failure(it) }
     *     check(0 == metadata.len())
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun len(): ULong = this.value.size()

    /**
     * Returns the permissions of the file this metadata is for.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = metadata("foo.txt").getOrElse { return Result.failure(it) }
     *     check(!metadata.permissions().readonly())
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun permissions(): Permissions = Permissions(this.value.perm())

    /**
     * Returns the last modification time listed in this metadata.
     *
     * The returned value corresponds to the `mtime` field of `stat` on Unix
     * platforms and the `ftLastWriteTime` field on Windows platforms.
     *
     * # Errors
     *
     * This field might not be available on all platforms, and will return an
     * `Err` on platforms where it is not available.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = metadata("foo.txt").getOrElse { return Result.failure(it) }
     *     when (val matchVal = metadata.modified()) {
     *         is Ok -> {
     *             val (time) = matchVal
     *             println("$time")
     *         }
     *         else -> {
     *             println("Not supported on this platform")
     *         }
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun modified(): Result<SystemTime, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.modified().map(FromInner::fromInner)

    /**
     * Returns the last access time of this metadata.
     *
     * The returned value corresponds to the `atime` field of `stat` on Unix
     * platforms and the `ftLastAccessTime` field on Windows platforms.
     *
     * Note that not all platforms will keep this field update in a file's
     * metadata, for example Windows has an option to disable updating this
     * time when files are accessed and Linux similarly has `noatime`.
     *
     * # Errors
     *
     * This field might not be available on all platforms, and will return an
     * `Err` on platforms where it is not available.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = metadata("foo.txt").getOrElse { return Result.failure(it) }
     *     when (val matchVal = metadata.accessed()) {
     *         is Ok -> {
     *             val (time) = matchVal
     *             println("$time")
     *         }
     *         else -> {
     *             println("Not supported on this platform")
     *         }
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun accessed(): Result<SystemTime, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.accessed().map(FromInner::fromInner)

    /**
     * Returns the creation time listed in this metadata.
     *
     * The returned value corresponds to the `btime` field of `statx` on
     * Linux kernel starting from to 4.11, the `birthtime` field of `stat` on other
     * Unix platforms, and the `ftCreationTime` field on Windows platforms.
     *
     * # Errors
     *
     * This field might not be available on all platforms, and will return an
     * `Err` on platforms or filesystems where it is not available.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = metadata("foo.txt").getOrElse { return Result.failure(it) }
     *     when (val matchVal = metadata.created()) {
     *         is Ok -> {
     *             val (time) = matchVal
     *             println("$time")
     *         }
     *         else -> {
     *             println("Not supported on this platform or filesystem")
     *         }
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun created(): Result<SystemTime, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.created().map(FromInner::fromInner)

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val debug = DebugStruct(f, "Metadata")
        debug.field("file_type", this.fileType())
        debug.field("permissions", this.permissions())
        debug.field("len", this.len())
        when (val matchVal = this.modified()) {
            is Ok -> {
                val modified = matchVal.value
                debug.field("modified", modified)
            }
            else -> {}
        }
        when (val matchVal1 = this.accessed()) {
            is Ok -> {
                val accessed = matchVal1.value
                debug.field("accessed", accessed)
            }
            else -> {}
        }
        when (val matchVal2 = this.created()) {
            is Ok -> {
                val created = matchVal2.value
                debug.field("created", created)
            }
            else -> {}
        }
        return debug.finishNonExhaustive()
    }

    public override fun intoInner(): FileAttr = this.value

    public override fun asInner(): FileAttr = this.value

    public companion object {
        public fun fromInner(attr: FileAttr): Metadata = Metadata(attr)
    }
}

/**
 * Iterator over the entries in a directory.
 *
 * This iterator is returned from the [readDir] function of this module and
 * will yield instances of <code>[io.Result]<[DirEntry]></code>. Through a [DirEntry]
 * information like the entry's path and possibly other metadata can be
 * learned.
 *
 * The order in which this iterator returns entries is platform and filesystem
 * dependent.
 *
 * # Errors
 * This [io.Result] will be an [Err] if an error occurred while fetching
 * the next entry from the OS.
 */
public open class ReadDir(
    public var value: io.github.kotlinmania.rustlang.std.sys.fs.unix.ReadDir,
) : Iterable<Result<DirEntry, io.github.kotlinmania.rustlang.std.io.error.Error>> {
    public var field0: io.github.kotlinmania.rustlang.std.sys.fs.unix.ReadDir
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Result<DirEntry, io.github.kotlinmania.rustlang.std.io.error.Error>> = object : AbstractIterator<Result<DirEntry, io.github.kotlinmania.rustlang.std.io.error.Error>>() {
        override fun computeNext() {
            when (val v = this@ReadDir.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    // Note: before stabilization, ensure that we have a race-free implementation on Windows!

    /**
     * Returns a handle for the directory this `ReadDir` is reading.
     *
     * Whenever possible, the new handle will be created in a race-free manner from the underlying
     * handle used by `ReadDir`. However, this may fall back to opening a new handle based on the
     * path, which is subject to race conditions if the file system has changed since the `ReadDir`
     * was created.
     */
    public open fun dir(): Result<Dir, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.dir().map({ d -> Dir(inner = d) })

    public open fun next(): Result<DirEntry, io.github.kotlinmania.rustlang.std.io.error.Error>? = this.value.next()?.let({ entry -> entry.map({ DirEntry(it) }) })
}

/**
 * Entries returned by the [ReadDir] iterator.
 *
 * An instance of `DirEntry` represents an entry inside of a directory on the
 * filesystem. Each entry can be inspected via methods to learn about the full
 * path or possibly other metadata through per-platform extension traits.
 *
 * # Platform-specific behavior
 *
 * On Unix, the `DirEntry` struct contains an internal reference to the open
 * directory. Holding `DirEntry` objects will consume a file handle even
 * after the `ReadDir` iterator is dropped.
 *
 * Note that this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 */
public open class DirEntry(
    public var value: io.github.kotlinmania.rustlang.std.sys.fs.unix.DirEntry,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.fs.unix.DirEntry> {
    public var field0: io.github.kotlinmania.rustlang.std.sys.fs.unix.DirEntry
        get() = value
        set(v) {
            value = v
        }

    /**
     * Returns the full path to the file that this entry represents.
     *
     * The full path is created by joining the original path to `readDir`
     * with the filename of this entry.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     for (entry in readDir(".").getOrElse { return Result.failure(it) }) {
     *         val dir = entry.getOrElse { return Result.failure(it) }
     *         println("${dir.path()}")
     *     }
     *     return Result.success(Unit)
     * }
     * ```
     *
     * This prints output like:
     *
     * ```text
     * "./whatever.txt"
     * "./foo.html"
     * "./helloWorld.rs"
     * ```
     *
     * The exact text, of course, depends on what files you have in `.`.
     */
    public open fun path(): PathBuf = this.value.path()

    /**
     * Returns the metadata for the file that this entry points at.
     *
     * This function will not traverse symlinks if this entry points at a
     * symlink. To traverse symlinks use [fs.metadata] or [fs.File.metadata].
     *
     * [fs.metadata]: metadata
     * [fs.File.metadata]: File.metadata
     *
     * # Platform-specific behavior
     *
     * On Windows this function is cheap to call (no extra system calls
     * needed), but on Unix platforms this function is the equivalent of
     * calling `symlinkMetadata` on the path. In that case, if the file has been renamed or
     * replaced since the `DirEntry` was created, this can fail or return the metadata of a
     * different file
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * when (val matchVal = readDir(".")) {
     *     is Ok -> {
     *         val (entries) = matchVal
     *         for (entry in entries) {
     *             if (entry is Ok) {
     *                 val (entry1) = entry
     *                 // Here, `entry` is a `DirEntry`.
     *                 when (val matchVal1 = entry1.metadata()) {
     *                     is Ok -> {
     *                         val (metadata) = matchVal1
     *                         // Now let's show our entry's permissions!
     *                         println("${entry1.path()}: ${metadata.permissions()}")
     *                     }
     *                     else -> {
     *                         println("Couldn't get metadata for ${entry1.path()}")
     *                     }
     *                 }
     *             }
     *         }
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun metadata(): Result<Metadata, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.metadata().map({ Metadata(it) })

    /**
     * Returns the file type for the file that this entry points at.
     *
     * This function will not traverse symlinks if this entry points at a
     * symlink.
     *
     * # Platform-specific behavior
     *
     * On Windows and most Unix platforms and file systems this function is free (no extra
     * system calls needed), but some Unix platforms or file systems may require the equivalent of
     * calling `symlinkMetadata` to learn about the target file type. In that case, if the file
     * has been renamed or replaced since the `DirEntry` was created, this can fail or return the
     * file type of a different file
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * when (val matchVal = readDir(".")) {
     *     is Ok -> {
     *         val (entries) = matchVal
     *         for (entry in entries) {
     *             if (entry is Ok) {
     *                 val (entry1) = entry
     *                 // Here, `entry` is a `DirEntry`.
     *                 when (val matchVal1 = entry1.fileType()) {
     *                     is Ok -> {
     *                         val (fileType) = matchVal1
     *                         // Now let's show our entry's file type!
     *                         println("${entry1.path()}: $fileType")
     *                     }
     *                     else -> {
     *                         println("Couldn't get file type for ${entry1.path()}")
     *                     }
     *                 }
     *             }
     *         }
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun fileType(): Result<FileType, io.github.kotlinmania.rustlang.std.io.error.Error> = this.value.fileType().map({ FileType(it) })

    /**
     * Returns the file name of this directory entry without any
     * leading path component(s).
     *
     * As an example,
     * the output of the function will result in "foo" for all the following paths:
     * - "./foo"
     * - "/the/foo"
     * - "../../foo"
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * when (val matchVal = readDir(".")) {
     *     is Ok -> {
     *         val (entries) = matchVal
     *         for (entry in entries) {
     *             if (entry is Ok) {
     *                 val (entry1) = entry
     *                 // Here, `entry` is a `DirEntry`.
     *                 println("${entry1.fileName()}")
     *             }
     *         }
     *     }
     *     else -> {}
     * }
     * ```
     */
    public open fun fileName(): OsString = this.value.fileName()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = DebugTuple(f, "DirEntry").field(this.path()).finish()

    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.fs.unix.DirEntry = this.value
}

/**
 * Options and flags which can be used to configure how a file is opened.
 *
 * This builder exposes the ability to configure how a [File] is opened and
 * what operations are permitted on the open file. The [File.open] and
 * [File.create] methods are aliases for commonly used options using this
 * builder.
 *
 * Generally speaking, when using `OpenOptions`, you'll first call
 * [OpenOptions.new], then chain calls to methods to set each option, then
 * call [OpenOptions.open], passing the path of the file you're trying to
 * open. This will give you a [io.Result] with a [File] inside that you
 * can further operate on.
 *
 * # Examples
 *
 * Opening a file to read:
 *
 * ```kotlin
 * import std.fs.OpenOptions
 *
 * val file = OpenOptions.new().read(true).open("foo.txt")
 * ```
 *
 * Opening a file for both reading and writing, as well as creating it if it
 * doesn't exist:
 *
 * ```kotlin
 * import std.fs.OpenOptions
 *
 * val file = OpenOptions.new().read(true).write(true).create(true).open("foo.txt")
 * ```
 */
public open class OpenOptions(
    public var value: io.github.kotlinmania.rustlang.std.sys.fs.unix.OpenOptions,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.fs.unix.OpenOptions>, AsInnerMut<io.github.kotlinmania.rustlang.std.sys.fs.unix.OpenOptions> {
    public var field0: io.github.kotlinmania.rustlang.std.sys.fs.unix.OpenOptions
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): OpenOptions = OpenOptions(value)

    /**
     * Sets the option for read access.
     *
     * This option, when true, will indicate that the file should be
     * `read`-able if opened.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.OpenOptions
     *
     * val file = OpenOptions.new().read(true).open("foo.txt")
     * ```
     */
    public open fun read(read: Boolean): OpenOptions {
        this.value.read(read)
        return this
    }

    /**
     * Sets the option for write access.
     *
     * This option, when true, will indicate that the file should be
     * `write`-able if opened.
     *
     * If the file already exists, any write calls on it will overwrite its
     * contents, without truncating it.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.OpenOptions
     *
     * val file = OpenOptions.new().write(true).open("foo.txt")
     * ```
     */
    public open fun write(write: Boolean): OpenOptions {
        this.value.write(write)
        return this
    }

    /**
     * Sets the option for the append mode.
     *
     * This option, when true, means that writes will append to a file instead
     * of overwriting previous contents.
     * Note that setting `.write(true).append(true)` has the same effect as
     * setting only `.append(true)`.
     *
     * Append mode guarantees that writes will be positioned at the current end of file,
     * even when there are other processes or threads appending to the same file. This is
     * unlike <code>[seek]\([SeekFrom]::[End]\(0))</code> followed by `write()`, which
     * has a race between seeking and writing during which another writer can write, with
     * our `write()` overwriting their data.
     *
     * Keep in mind that this does not necessarily guarantee that data appended by
     * different processes or threads does not interleave. The amount of data accepted a
     * single `write()` call depends on the operating system and file system. A
     * successful `write()` is allowed to write only part of the given data, so even if
     * you're careful to provide the whole message in a single call to `write()`, there
     * is no guarantee that it will be written out in full. If you rely on the filesystem
     * accepting the message in a single write, make sure that all data that belongs
     * together is written in one operation. This can be done by concatenating strings
     * before passing them to [write].
     *
     * If a file is opened with both read and append access, beware that after
     * opening, and after every write, the position for reading may be set at the
     * end of the file. So, before writing, save the current position (using
     * <code>[Seek]::[streamPosition]</code>), and restore it before the next read.
     *
     * ## Note
     *
     * This function doesn't create the file if it doesn't exist. Use the
     * [OpenOptions.create] method to do so.
     *
     * [write]: Write.write "io.Write.write"
     * [flush]: Write.flush "io.Write.flush"
     * [streamPosition]: Seek.streamPosition "io.Seek.streamPosition"
     * [seek]: Seek.seek "io.Seek.seek"
     * [Current]: SeekFrom.Current "io.SeekFrom.Current"
     * [End]: SeekFrom.End "io.SeekFrom.End"
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.OpenOptions
     *
     * val file = OpenOptions.new().append(true).open("foo.txt")
     * ```
     */
    public open fun append(append: Boolean): OpenOptions {
        this.value.append(append)
        return this
    }

    /**
     * Sets the option for truncating a previous file.
     *
     * If a file is successfully opened with this option set to true, it will truncate
     * the file to 0 length if it already exists.
     *
     * The file must be opened with write access for truncate to work.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.OpenOptions
     *
     * val file = OpenOptions.new().write(true).truncate(true).open("foo.txt")
     * ```
     */
    public open fun truncate(truncate: Boolean): OpenOptions {
        this.value.truncate(truncate)
        return this
    }

    /**
     * Sets the option to create a new file, or open it if it already exists.
     *
     * In order for the file to be created, [OpenOptions.write] or
     * [OpenOptions.append] access must be used.
     *
     * See also [`std.fs.write`][write] for a simple function to
     * create a file with some given data.
     *
     * # Errors
     *
     * If `.create(true)` is set without `.write(true)` or `.append(true)`,
     * calling [`open`](Self.open) will fail with [`InvalidInput`](io.ErrorKind.InvalidInput) error.
     * # Examples
     *
     * ```kotlin
     * import std.fs.OpenOptions
     *
     * val file = OpenOptions.new().write(true).create(true).open("foo.txt")
     * ```
     */
    public open fun create(create: Boolean): OpenOptions {
        this.value.create(create)
        return this
    }

    /**
     * Sets the option to create a new file, failing if it already exists.
     *
     * No file is allowed to exist at the target location, also no (dangling) symlink. In this
     * way, if the call succeeds, the file returned is guaranteed to be new.
     * If a file exists at the target location, creating a new file will fail with [AlreadyExists]
     * or another error based on the situation. See [OpenOptions.open] for a
     * non-exhaustive list of likely errors.
     *
     * This option is useful because it is atomic. Otherwise between checking
     * whether a file exists and creating a new one, the file may have been
     * created by another process (a [TOCTOU] race condition / attack).
     *
     * If `.createNew(true)` is set, [create] and [truncate] are
     * ignored.
     *
     * The file must be opened with write or append access in order to create
     * a new file.
     *
     * [create]: OpenOptions.create
     * [truncate]: OpenOptions.truncate
     * [AlreadyExists]: io.ErrorKind.AlreadyExists
     * [TOCTOU]: self#time-of-check-to-time-of-use-toctou
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.OpenOptions
     *
     * val file = OpenOptions.new().write(true).createNew(true).open("foo.txt")
     * ```
     */
    public open fun createNew(createNew: Boolean): OpenOptions {
        this.value.createNew(createNew)
        return this
    }

    /**
     * Opens a file at `path` with the options specified by `self`.
     *
     * # Errors
     *
     * This function will return an error under a number of different
     * circumstances. Some of these error conditions are listed here, together
     * with their [io.ErrorKind]. The mapping to [io.ErrorKind]s is not
     * part of the compatibility contract of the function.
     *
     * * [NotFound]: The specified file does not exist and neither `create`
     *   or `createNew` is set.
     * * [NotFound]: One of the directory components of the file path does
     *   not exist.
     * * [PermissionDenied]: The user lacks permission to get the specified
     *   access rights for the file.
     * * [PermissionDenied]: The user lacks permission to open one of the
     *   directory components of the specified path.
     * * [AlreadyExists]: `createNew` was specified and the file already
     *   exists.
     * * [InvalidInput]: Invalid combinations of open options (truncate
     *   without write access, create without write or append access,
     *   no access mode set, etc.).
     *
     * The following errors don't match any existing [io.ErrorKind] at the moment:
     * * One of the directory components of the specified file path
     *   was not, in fact, a directory.
     * * Filesystem-level errors: full disk, write permission
     *   requested on a read-only file system, exceeded disk quota, too many
     *   open files, too long filename, too many symbolic links in the
     *   specified path (Unix-like systems only), etc.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.OpenOptions
     *
     * val file = OpenOptions.new().read(true).open("foo.txt")
     * ```
     *
     * [AlreadyExists]: io.ErrorKind.AlreadyExists
     * [InvalidInput]: io.ErrorKind.InvalidInput
     * [NotFound]: io.ErrorKind.NotFound
     * [PermissionDenied]: io.ErrorKind.PermissionDenied
     */
    public open fun open(path: Path): Result<File, io.github.kotlinmania.rustlang.std.io.error.Error> = this.open(path)

    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.fs.unix.OpenOptions = this.value

    public override fun asInnerMut(): io.github.kotlinmania.rustlang.std.sys.fs.unix.OpenOptions = this.value

    public companion object {
        /**
         * Creates a blank new set of options ready for configuration.
         *
         * All options are initially set to `false`.
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.OpenOptions
         *
         * var options = OpenOptions.new()
         * val file = options.read(true).open("foo.txt")
         * ```
         */
        public fun new(): OpenOptions = OpenOptions(io.github.kotlinmania.rustlang.std.sys.fs.unix.OpenOptions.new())
    }
}

/**
 * Representation of the various timestamps on a file.
 */
public open class FileTimes(
    public var value: io.github.kotlinmania.rustlang.std.sys.fs.unix.FileTimes,
) : AsInnerMut<io.github.kotlinmania.rustlang.std.sys.fs.unix.FileTimes> {
    public var field0: io.github.kotlinmania.rustlang.std.sys.fs.unix.FileTimes
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): FileTimes = FileTimes(value)

    /**
     * Set the last access time of a file.
     */
    public open fun setAccessed(t: SystemTime): FileTimes {
        this.value.setAccessed(t.intoInner())
        return this
    }

    /**
     * Set the last modified time of a file.
     */
    public open fun setModified(t: SystemTime): FileTimes {
        this.value.setModified(t.intoInner())
        return this
    }

    public override fun asInnerMut(): io.github.kotlinmania.rustlang.std.sys.fs.unix.FileTimes = this.value

    public companion object {
        /**
         * Creates a new `FileTimes` with no times set.
         *
         * Using the resulting `FileTimes` in [File.setTimes] will not modify any timestamps.
         */
        public fun new(): FileTimes = FileTimes.default()
    }
}

/**
 * Representation of the various permissions on a file.
 *
 * This module only currently provides one bit of information,
 * [Permissions.readonly], which is exposed on all currently supported
 * platforms. Unix-specific functionality, such as mode bits, is available
 * through the [PermissionsExt] trait.
 */
public open class Permissions(
    public var value: FilePermissions,
) : FromInner<FilePermissions, Permissions>, AsInner<FilePermissions> {
    public var field0: FilePermissions
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Permissions = Permissions(value)

    /**
     * Returns `true` if these permissions describe a readonly (unwritable) file.
     *
     * # Note
     *
     * This function does not take Access Control Lists (ACLs), Unix group
     * membership and other nuances into account.
     * Therefore the return value of this function cannot be relied upon
     * to predict whether attempts to read or write the file will actually succeed.
     *
     * # Windows
     *
     * On Windows this returns [`FileAttributeReadonly`](https://docs.microsoft.com/en-us/windows/win32/fileio/file-attribute-constants).
     * If `FILE_ATTRIBUTE_READONLY` is set then writes to the file will fail
     * but the user may still have permission to change this flag. If
     * `FILE_ATTRIBUTE_READONLY` is *not* set then writes may still fail due
     * to lack of write permission.
     * The behavior of this attribute for directories depends on the Windows
     * version.
     *
     * # Unix (including macOS)
     *
     * On Unix-based platforms this checks if *any* of the owner, group or others
     * write permission bits are set. It does not consider anything else, including:
     *
     * * Whether the current user is in the file's assigned group.
     * * Permissions granted by ACL.
     * * That `root` user can write to files that do not have any write bits set.
     * * Writable files on a filesystem that is mounted read-only.
     *
     * The [PermissionsExt] trait gives direct access to the permission bits but
     * also does not read ACLs.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     *
     * fun main(): Result<Unit> {
     *     var f = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     val metadata = f.metadata().getOrElse { return Result.failure(it) }
     *     check(false == metadata.permissions().readonly())
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun readonly(): Boolean = this.value.readonly()

    /**
     * Modifies the readonly flag for this set of permissions. If the
     * `readonly` argument is `true`, using the resulting `Permission` will
     * update file permissions to forbid writing. Conversely, if it's `false`,
     * using the resulting `Permission` will update file permissions to allow
     * writing.
     *
     * This operation does **not** modify the files attributes. This only
     * changes the in-memory value of these attributes for this `Permissions`
     * instance. To modify the files attributes use the [setPermissions]
     * function which commits these attribute changes to the file.
     *
     * # Note
     *
     * `setReadonly(false)` makes the file *world-writable* on Unix.
     * You can use the [PermissionsExt] trait on Unix to avoid this issue.
     *
     * It also does not take Access Control Lists (ACLs) or Unix group
     * membership into account.
     *
     * # Windows
     *
     * On Windows this sets or clears [`FileAttributeReadonly`](https://docs.microsoft.com/en-us/windows/win32/fileio/file-attribute-constants).
     * If `FILE_ATTRIBUTE_READONLY` is set then writes to the file will fail
     * but the user may still have permission to change this flag. If
     * `FILE_ATTRIBUTE_READONLY` is *not* set then the write may still fail if
     * the user does not have permission to write to the file.
     *
     * In Windows 7 and earlier this attribute prevents deleting empty
     * directories. It does not prevent modifying the directory contents.
     * On later versions of Windows this attribute is ignored for directories.
     *
     * # Unix (including macOS)
     *
     * On Unix-based platforms this sets or clears the write access bit for
     * the owner, group *and* others, equivalent to `chmod a + w<file>`
     * or `chmod a - w<file>` respectively. The latter will grant write access
     * to all users! You can use the [PermissionsExt] trait on Unix
     * to avoid this issue.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.File
     *
     * fun main(): Result<Unit> {
     *     val f = File.create("foo.txt").getOrElse { return Result.failure(it) }
     *     val metadata = f.metadata().getOrElse { return Result.failure(it) }
     *     var permissions = metadata.permissions()
     *     permissions.setReadonly(true)
     *     // filesystem doesn't change, only the in memory state of the
     *     // readonly permission
     *     check(false == metadata.permissions().readonly())
     *     // just this particular `permissions`.
     *     check(true == permissions.readonly())
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun setReadonly(readonly: Boolean) {
        this.value.setReadonly(readonly)
    }

    public override fun asInner(): FilePermissions = this.value

    public companion object {
        public fun fromInner(f: FilePermissions): Permissions = Permissions(f)
    }
}

/**
 * A structure representing a type of file with accessors for each file type.
 * It is returned by [Metadata.fileType] method.
 */
public open class FileType(
    public var value: io.github.kotlinmania.rustlang.std.sys.fs.unix.FileType,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.fs.unix.FileType> {
    public var field0: io.github.kotlinmania.rustlang.std.sys.fs.unix.FileType
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): FileType = FileType(value)

    /**
     * Tests whether this file type represents a directory. The
     * result is mutually exclusive to the results of
     * [isFile] and [isSymlink]; only zero or one of these
     * tests may pass.
     *
     * [isFile]: FileType.isFile
     * [isSymlink]: FileType.isSymlink
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = metadata("foo.txt").getOrElse { return Result.failure(it) }
     *     val fileType = metadata.fileType()
     *     check(fileType.isDir() == false)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun isDir(): Boolean = this.value.isDir()

    /**
     * Tests whether this file type represents a regular file.
     * The result is mutually exclusive to the results of
     * [isDir] and [isSymlink]; only zero or one of these
     * tests may pass.
     *
     * When the goal is simply to read from (or write to) the source, the most
     * reliable way to test the source can be read (or written to) is to open
     * it. Only using `isFile` can break workflows like `diff<(progA)` on
     * a Unix-like system for example. See [File.open] or
     * [OpenOptions.open] for more information.
     *
     * [isDir]: FileType.isDir
     * [isSymlink]: FileType.isSymlink
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = metadata("foo.txt").getOrElse { return Result.failure(it) }
     *     val fileType = metadata.fileType()
     *     check(fileType.isFile() == true)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun isFile(): Boolean = this.value.isFile()

    /**
     * Tests whether this file type represents a symbolic link.
     * The result is mutually exclusive to the results of
     * [isDir] and [isFile]; only zero or one of these
     * tests may pass.
     *
     * The underlying [Metadata] struct needs to be retrieved
     * with the [fs.symlinkMetadata] function and not the
     * [fs.metadata] function. The [fs.metadata] function
     * follows symbolic links, so [isSymlink] would always
     * return `false` for the target file.
     *
     * [fs.metadata]: metadata
     * [fs.symlinkMetadata]: symlinkMetadata
     * [isDir]: FileType.isDir
     * [isFile]: FileType.isFile
     * [isSymlink]: FileType.isSymlink
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs
     *
     * fun main(): Result<Unit> {
     *     val metadata = symlinkMetadata("foo.txt").getOrElse { return Result.failure(it) }
     *     val fileType = metadata.fileType()
     *     check(fileType.isSymlink() == false)
     *     return Result.success(Unit)
     * }
     * ```
     */
    public open fun isSymlink(): Boolean = this.value.isSymlink()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = DebugStruct(f, "FileType").field("is_file", this.isFile()).field("is_dir", this.isDir()).field("is_symlink", this.isSymlink()).finishNonExhaustive()

    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.fs.unix.FileType = this.value
}

/**
 * A builder used to create directories in various manners.
 *
 * This builder also supports platform-specific options.
 */
public open class DirBuilder(
    public var inner: io.github.kotlinmania.rustlang.std.sys.fs.unix.DirBuilder,
    public var recursive: Boolean,
) : AsInnerMut<io.github.kotlinmania.rustlang.std.sys.fs.unix.DirBuilder> {
    /**
     * Indicates that directories should be created recursively, creating all
     * parent directories. Parents that do not exist are created with the same
     * security and permissions settings.
     *
     * This option defaults to `false`.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.DirBuilder
     *
     * var builder = DirBuilder.new()
     * builder.recursive(true)
     * ```
     */
    public open fun recursive(recursive: Boolean): DirBuilder {
        this.recursive = recursive
        return this
    }

    /**
     * Creates the specified directory with the options configured in this
     * builder.
     *
     * It is considered an error if the directory already exists unless
     * recursive mode is enabled.
     *
     * # Examples
     *
     * ```kotlin
     * import std.fs.DirBuilder
     *
     * val path = "/tmp/foo/bar/baz"
     * DirBuilder.new().recursive(true).create(path).unwrap()
     * check(fs.metadata(path).unwrap().isDir())
     * ```
     */
    public open fun create(path: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = this.create(path)

    public open fun createDirAll(path: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> {
        // if path's parent is None, it is "/" path, which should
        // return Ok immediately
        if (path.isEmpty() || path.parent() == null) {
            return Result.Ok<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(Unit)
        }
        val ancestors = path.ancestors()
        var uncreatedDirs = 0
        for (ancestor in ancestors) {
            // for relative paths like "foo/bar", the parent of
            // "foo" will be "" which there's no need to invoke
            // a mkdir syscall on
            if (ancestor.isEmpty() || ancestor.parent() == null) {
                break
            }
            run {
                val matchVal = this.inner.mkdir(ancestor)
                when {
                    matchVal is Result.Ok -> { break }
                    matchVal is Result.Err && (matchVal as Result.Err).error.kind() == ErrorKind.NotFound -> {
                        val e = matchVal.error
                        uncreatedDirs += 1
                    }
                    // we check if the err is AlreadyExists for two reasons
                    // - in case the path exists as a *file*
                    // - and to avoid calls to .isDir() in case of other errs
                    // (i.e. PermissionDenied)
                    matchVal is Result.Err && (matchVal as Result.Err).error.kind() == ErrorKind.AlreadyExists && ancestor.isDir() -> {
                        val e1 = matchVal.error
                        break
                    }
                    else -> {
                        val e2 = matchVal.error
                        return Result.Err<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(e2)
                    }
                }
            }
        }
        // collect only the uncreated directories w/o letting the vec resize
        val uncreatedDirsVec = ArrayList(uncreatedDirs)
        uncreatedDirsVec.extend(ancestors.take(uncreatedDirs))
        for (uncreatedDir in ArrayDeque(uncreatedDirsVec).rev()) {
            when (val matchVal1 = this.inner.mkdir(uncreatedDir)) {
                is Err -> {
                    val e3 = matchVal1.error
                    if (e3.kind() != ErrorKind.AlreadyExists || !uncreatedDir.isDir()) {
                        return Result.Err<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(e3)
                    }
                }
                else -> {}
            }
        }
        return Result.Ok<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(Unit)
    }

    public override fun asInnerMut(): io.github.kotlinmania.rustlang.std.sys.fs.unix.DirBuilder = this.inner

    public companion object {
        /**
         * Creates a new set of options with default mode/security settings for all
         * platforms and also non-recursive.
         *
         * # Examples
         *
         * ```kotlin
         * import std.fs.DirBuilder
         *
         * val builder = DirBuilder.new()
         * ```
         */
        public fun new(): DirBuilder = DirBuilder(inner = io.github.kotlinmania.rustlang.std.sys.fs.unix.DirBuilder.new(), recursive = false)
    }
}

/**
 * Reads the entire contents of a file into a bytes vector.
 *
 * This is a convenience function for using [File.open] and [readToEnd]
 * with fewer imports and without an intermediate variable.
 *
 * [readToEnd]: Read.readToEnd
 *
 * # Errors
 *
 * This function will return an error if `path` does not already exist.
 * Other errors may also be returned according to [OpenOptions.open].
 *
 * While reading from the file, this function handles [io.ErrorKind.Interrupted]
 * with automatic retries. See [io.Read] documentation for details.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     val data: List<UByte> = read("image.jpg").getOrElse { return Result.failure(it) }
 *     check(((data).subList(0, 3)) == listOf(0xFFu, 0xD8u, 0xFFu))
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun read(path: Path): Result<List<UByte>, io.github.kotlinmania.rustlang.std.io.error.Error> {
    fun inner(path1: Path): Result<List<UByte>, io.github.kotlinmania.rustlang.std.io.error.Error> {
        val file = when (val tryResult = File.open(path1)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<List<UByte>, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        val size = file.metadata().map({ m -> Int.tryFrom(m.len()).unwrapOr(Int.MAX_VALUE) }).ok()
        val bytes = when (val tryResult = Vec.tryWithCapacity(size.unwrapOr(0))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<List<UByte>, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        when (val tryResult = defaultReadToEnd(file, bytes, size)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<List<UByte>, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        return Result.Ok<List<UByte>, io.github.kotlinmania.rustlang.std.io.error.Error>(bytes)
    }
    return inner(path)
}

/**
 * Reads the entire contents of a file into a string.
 *
 * This is a convenience function for using [File.open] and [readToString]
 * with fewer imports and without an intermediate variable.
 *
 * [readToString]: Read.readToString
 *
 * # Errors
 *
 * This function will return an error if `path` does not already exist.
 * Other errors may also be returned according to [OpenOptions.open].
 *
 * If the contents of the file are not valid UTF-8, then an error will also be
 * returned.
 *
 * While reading from the file, this function handles [io.ErrorKind.Interrupted]
 * with automatic retries. See [io.Read] documentation for details.
 *
 * # Examples
 *
 * ```kotlin
 * import std.error.Error
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     val message: String = readToString("message.txt").getOrElse { return Result.failure(it) }
 *     println("$message")
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun readToString(path: Path): Result<String, io.github.kotlinmania.rustlang.std.io.error.Error> {
    fun inner(path1: Path): Result<String, io.github.kotlinmania.rustlang.std.io.error.Error> {
        val file = when (val tryResult = File.open(path1)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<String, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        val size = file.metadata().map({ m -> Int.tryFrom(m.len()).unwrapOr(Int.MAX_VALUE) }).ok()
        val string = ""
        when (val tryResult = string.tryReserveExact(size.unwrapOr(0))) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<String, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        when (val tryResult = defaultReadToString(file, string, size)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<String, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }
        return Result.Ok<String, io.github.kotlinmania.rustlang.std.io.error.Error>(string)
    }
    return inner(path)
}

/**
 * Writes a slice as the entire contents of a file.
 *
 * This function will create a file if it does not exist,
 * and will entirely replace its contents if it does.
 *
 * Depending on the platform, this function may fail if the
 * full directory path does not exist.
 *
 * This is a convenience function for using [File.create] and [writeAll]
 * with fewer imports.
 *
 * [writeAll]: Write.writeAll
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     write("foo.txt", "Lorem ipsum".encodeToByteArray()).getOrElse { return Result.failure(it) }
 *     write("bar.txt", "dolor sit").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun write(path: Path, contents: List<UByte>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> {
    fun inner(path1: Path, contents1: List<UByte>): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> {
        return when (val tryResult = File.create(path1)) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(tryResult.error) }.writeAll(contents1)
    }
    return inner(path, contents)
}

/**
 * Changes the timestamps of the file or directory at the specified path.
 *
 * This function will attempt to set the access and modification times
 * to the times specified. If the path refers to a symbolic link, this function
 * will follow the link and change the timestamps of the target file.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `utimensat` function on Unix platforms, the
 * `setattrlist` function on Apple platforms, and the `SetFileTime` function on Windows.
 *
 * # Errors
 *
 * This function will return an error if the user lacks permission to change timestamps on the
 * target file or symlink. It may also return an error if the OS does not support it.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs.FileTimes
 * import std.time.SystemTime
 *
 * fun main(): Result<Unit> {
 *     val now = SystemTime.now()
 *     val times = FileTimes.new().setAccessed(now).setModified(now)
 *     fs.setTimes("foo.txt", times).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun setTimes(path: Path, times: FileTimes): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.setTimes(path, times.value)

/**
 * Changes the timestamps of the file or symlink at the specified path.
 *
 * This function will attempt to set the access and modification times
 * to the times specified. Differ from `setTimes`, if the path refers to a symbolic link,
 * this function will change the timestamps of the symlink itself, not the target file.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `utimensat` function with `AT_SYMLINK_NOFOLLOW` on
 * Unix platforms, the `setattrlist` function with `FSOPT_NOFOLLOW` on Apple platforms, and the
 * `SetFileTime` function on Windows.
 *
 * # Errors
 *
 * This function will return an error if the user lacks permission to change timestamps on the
 * target file or symlink. It may also return an error if the OS does not support it.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs.FileTimes
 * import std.time.SystemTime
 *
 * fun main(): Result<Unit> {
 *     val now = SystemTime.now()
 *     val times = FileTimes.new().setAccessed(now).setModified(now)
 *     fs.setTimesNofollow("symlink.txt", times).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun setTimesNofollow(path: Path, times: FileTimes): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.setTimesNofollow(path, times.value)

public fun fromError(err: TryLockError): Error =
    when (err) {
        is TryLockError.Error -> {
            val (err1) = err
            err1
        }
        TryLockError.WouldBlock -> ErrorKind.WouldBlock.into()
    }

/**
 * Indicates how much extra capacity is needed to read the rest of the file.
 */
public fun bufferCapacityRequired(file: File): Int? {
    val size = (file.metadata().map({ m -> m.len() }).ok() ?: return null)
    val pos = (file.streamPosition().ok() ?: return null)
    // Don't worry about `Int` overflow because reading will fail regardless
    // in that case.
    return size.saturatingSub(pos).toInt()
}

/**
 * Removes a file from the filesystem.
 *
 * Note that there is no
 * guarantee that the file is immediately deleted (e.g., depending on
 * platform, other open file descriptors may prevent immediate removal).
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `unlink` function on Unix.
 * On Windows, `DeleteFile` is used or `CreateFileW` and `SetInformationByHandle` for readonly files.
 * Note that, this [may change in the future][changes].
 *
 * On Windows, symbolic links to directories cannot currently be removed with this function.
 * Use [removeDir] for that purpose.
 *
 * [changes]: io#platform-specific-behavior
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * `path` points to a directory.
 * * The file doesn't exist.
 * * The user lacks permissions to remove the file.
 *
 * This function will only ever return an error of kind `NotFound` if the given
 * path does not exist. Note that the inverse is not true,
 * i.e. if a path does not exist, its removal may fail for a number of reasons,
 * such as insufficient permissions.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     removeFile("a.txt").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun removeFile(path: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.removeFile(path)

/**
 * Given a path, queries the file system to get information about a file,
 * directory, etc.
 *
 * This function will traverse symbolic links to query information about the
 * destination file. To query metadata about the path itself without following
 * symbolic links, use [symlinkMetadata].
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `stat` function on Unix
 * and the `GetFileInformationByHandle` function on Windows.
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * The user lacks permissions to perform `metadata` call on `path`.
 * * `path` does not exist.
 * * `path` is a symbolic link, but the destination file cannot be resolved.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     val attr = metadata("/some/file/path.txt").getOrElse { return Result.failure(it) }
 *     // inspect attr ...
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun metadata(path: Path): Result<Metadata, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.metadata(path).map({ Metadata(it) })

/**
 * Queries the metadata about a file without following symlinks.
 *
 * This function will return the [Metadata] of the exact path without
 * traversing symbolic links to a resolved destination file. Using this function
 * on a path that is a file or directory (not a symbolic link) will behave the
 * same as [metadata].
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `lstat` function on Unix
 * and the `GetFileInformationByHandle` function on Windows.
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * The user lacks permissions to perform `metadata` call on `path`.
 * * `path` does not exist.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     val attr = symlinkMetadata("/some/file/path.txt").getOrElse { return Result.failure(it) }
 *     // inspect attr ...
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun symlinkMetadata(path: Path): Result<Metadata, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.symlinkMetadata(path).map({ Metadata(it) })

/**
 * Renames a file or directory to a new name, replacing the original file if
 * `to` already exists.
 *
 * This will not work if the new name is on a different mount point.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the [rename] function on Unix, and
 * `MoveFileExW` with a fallback to `SetFileInformationByHandle` on Windows.
 * The exact behavior differs:
 *
 * - If `to` does not exist, `from` can be anything.
 * - On Unix, when `from` is a directory and `to` exists, `to` must be an empty directory.
 * - On Unix, when `from` is not a directory and `to` exists, `to` may not be a directory.
 * - On Windows 10 version 1607 and above, the behavior is the same as Unix if the
 *   filesystem supports  `FileRenameInfoEx`.
 * - Otherwise on Windows, `from` can be anything but `to` must not be a directory.
 *
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 * [rename]: https://pubs.opengroup.org/onlinepubs/9799919799/functions/rename.html
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * `from` does not exist.
 * * The user lacks permissions to view contents.
 * * `from` and `to` are on separate filesystems.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     rename("a.txt", "b.txt").getOrElse { return Result.failure(it) }
 *     // Rename a.txt to b.txt
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun rename(from: Path, to: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.rename(from, to)

/**
 * Renames a file or directory to a new name, returning [AlreadyExists] if `to` already
 * exists.
 *
 * Unlike [fs.rename], the destination is never overwritten. The check happens as part of the
 * same operation, which avoids check-then-rename races.
 *
 * If the system crashes in the middle, it may be possible to end up with both copies of the file
 * existing, but it should not be possible to end up with zero copies, or with a half-written file.
 *
 * This will not work if the new name is on a different mount point.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to:
 *
 * * [renameat2] with `RENAME_NOREPLACE` on Linux and Android
 * * `renamexNp` with `RENAME_EXCL` on Apple platforms
 * * [MoveFileExW] without the `MOVEFILE_REPLACE_EXISTING` flag, with a fallback to
 *   `SetFileInformationByHandle`, on Windows
 * * `link` followed by `unlink` on other Unix platforms and on WASI
 * * Otherwise, this function returns [Unsupported]
 *
 * The `link` and `unlink` fallback is also used at runtime on Linux, Android and Apple platforms
 * when the kernel or the filesystem does not support the flag, so which of these applies can
 * differ between filesystems on the same machine.
 *
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 * [renameat2]: https://man7.org/linux/man-pages/man2/renameat2.2.html
 * [MoveFileExW]: https://learn.microsoft.com/en-us/windows/win32/api/winbase/nf-winbase-movefileexw
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * `from` does not exist.
 * * The user lacks permissions to view contents.
 * * `from` and `to` are on separate filesystems.
 * * `to` already exists.
 * * The platform does not support this operation.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 * import std.io.ErrorKind
 *
 * @feature(renameNoreplace)
 * public fun doctestEntry(): Result<Unit> {
 *     fun main(): Result<Unit> {
 *         // Renames a.txt to b.txt, b.txt does not exist yet.
 *         renameNoreplace("a.txt", "b.txt").getOrElse { return Result.failure(it) }
 *         // b.txt exists now, so this fails instead of silently overwriting it.
 *         val err = renameNoreplace("c.txt", "b.txt").unwrapErr()
 *         check(err.kind() == ErrorKind.AlreadyExists)
 *         return Result.success(Unit)
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun renameNoreplace(from: Path, to: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.renameNoreplace(from, to)

/**
 * Copies the contents of one file to another. This function will also
 * copy the permission bits of the original file to the destination file.
 *
 * This function will **overwrite** the contents of `to`.
 *
 * Note that if `from` and `to` both point to the same file, then the file
 * will likely get truncated by this operation.
 *
 * On success, the total number of bytes copied is returned and it is equal to
 * the length of the `to` file as reported by `metadata`.
 *
 * If you want to copy the contents of one file to another and you’re
 * working with [File]s, see the [`io.copy`](io.copy()) function.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `open` function in Unix
 * with `O_RDONLY` for `from` and `O_WRONLY`, `O_CREAT`, and `O_TRUNC` for `to`.
 * `O_CLOEXEC` is set for returned file descriptors.
 *
 * On Linux (including Android), this function uses copyFileRange(2),
 * sendfile(2), or splice(2) syscalls to move data directly between files
 * if possible.
 *
 * On Windows, this function currently corresponds to `CopyFileEx`. Alternate
 * NTFS streams are copied but only the size of the main stream is returned by
 * this function.
 *
 * On MacOS, this function corresponds to `fclonefileat` and `fcopyfile`.
 *
 * Note that platform-specific behavior [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * `from` is neither a regular file nor a symlink to a regular file.
 * * `from` does not exist.
 * * The current process does not have the permission rights to read
 *   `from` or write `to`.
 * * The parent directory of `to` doesn't exist.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     copy("foo.txt", "bar.txt").getOrElse { return Result.failure(it) }
 *     // Copy foo.txt to bar.txt
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun copy(from: Path, to: Path): Result<ULong, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.copy(from, to)

/**
 * Creates a new hard link on the filesystem.
 *
 * The `link` path will be a link pointing to the `original` path. Note that
 * systems often require these two paths to both be located on the same
 * filesystem.
 *
 * If `original` names a symbolic link, it is platform-specific whether the
 * symbolic link is followed. On platforms where it's possible to not follow
 * it, it is not followed, and the created hard link points to the symbolic
 * link itself.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `CreateHardLink` function on Windows.
 * On most Unix systems, it corresponds to the `linkat` function with no flags.
 * On VxWorks and Redox, it instead corresponds to the `link` function.
 * On MacOS, it uses the `linkat` function if it is available, but on very old
 * systems where `linkat` is not available, `link` is selected at runtime instead.
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * The `original` path is not a file or doesn't exist.
 * * The `link` path already exists.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     hardLink("a.txt", "b.txt").getOrElse { return Result.failure(it) }
 *     // Hard link a.txt to b.txt
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun hardLink(original: Path, link: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.hardLink(original, link)

/**
 * Creates a new symbolic link on the filesystem.
 *
 * The `link` path will be a symbolic link pointing to the `original` path.
 * On Windows, this will be a file symlink, not a directory symlink;
 * for this reason, the platform-specific [std.os.unix.fs.symlink]
 * and [std.os.windows.fs.symlinkFile] or [symlinkDir] should be
 * used instead to make the intent explicit.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     softLink("a.txt", "b.txt").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun softLink(original: Path, link: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = symlink(original, link)

/**
 * Reads a symbolic link, returning the file that the link points to.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `readlink` function on Unix
 * and the `CreateFile` function with `FILE_FLAG_OPEN_REPARSE_POINT` and
 * `FILE_FLAG_BACKUP_SEMANTICS` flags on Windows.
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * `path` is not a symbolic link.
 * * `path` does not exist.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     val path = readLink("a.txt").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun readLink(path: Path): Result<PathBuf, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.readLink(path)

/**
 * Returns the canonical, absolute form of a path with all intermediate
 * components normalized and symbolic links resolved.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `realpath` function on Unix
 * and the `CreateFile` and `GetFinalPathNameByHandle` functions on Windows.
 * Note that this [may change in the future][changes].
 *
 * On Windows, this converts the path to use [extended length path][path]
 * syntax, which allows your program to use longer path names, but means you
 * can only join backslash-delimited paths to it, and it may be incompatible
 * with other applications (if passed to the application on the command-line,
 * or written to a file another application may read).
 *
 * [changes]: io#platform-specific-behavior
 * [path]: https://docs.microsoft.com/en-us/windows/win32/fileio/naming-a-file
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * `path` does not exist.
 * * A non-final component in path is not a directory.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     val path = canonicalize("../a/../foo.txt").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun canonicalize(path: Path): Result<PathBuf, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.canonicalize(path)

/**
 * Creates a new, empty directory at the provided path.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `mkdir` function on Unix
 * and the `CreateDirectoryW` function on Windows.
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 *
 * **NOTE**: If a parent of the given path doesn't exist, this function will
 * return an error. To create a directory and all its missing parents at the
 * same time, use the [createDirAll] function.
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * User lacks permissions to create directory at `path`.
 * * A parent of the given path doesn't exist. (To create a directory and all
 *   its missing parents at the same time, use the [createDirAll]
 *   function.)
 * * `path` already exists.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     createDir("/some/dir").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun createDir(path: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = DirBuilder.new().create(path)

/**
 * Recursively create a directory and all of its parent components if they
 * are missing.
 *
 * This function is not atomic. If it returns an error, any parent components it was able to create
 * will remain.
 *
 * If the empty path is passed to this function, it always succeeds without
 * creating any directories.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to multiple calls to the `mkdir`
 * function on Unix and the `CreateDirectoryW` function on Windows.
 *
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 *
 * # Errors
 *
 * The function will return an error if any directory specified in path does not exist and
 * could not be created. There may be other error conditions; see [fs.createDir] for specifics.
 *
 * Notable exception is made for situations where any of the directories
 * specified in the `path` could not be created as it was being created concurrently.
 * Such cases are considered to be successful. That is, calling `createDirAll`
 * concurrently from multiple threads or processes is guaranteed not to fail
 * due to a race condition with itself.
 *
 * [fs.createDir]: createDir
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     createDirAll("/some/dir").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun createDirAll(path: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = DirBuilder.new().recursive(true).create(path)

/**
 * Removes an empty directory.
 *
 * If you want to remove a directory that is not empty, as well as all
 * of its contents recursively, consider using [removeDirAll]
 * instead.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `rmdir` function on Unix
 * and the `RemoveDirectory` function on Windows.
 * Note that, this [may change in the future][changes].
 *
 * On Windows, this can also be used to remove symbolic links to directories.
 * On Unix, [removeFile] must be used for that purpose.
 *
 * [changes]: io#platform-specific-behavior
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * `path` doesn't exist.
 * * `path` isn't a directory.
 * * The user lacks permissions to remove the directory at the provided `path`.
 * * The directory isn't empty.
 *
 * This function will only ever return an error of kind `NotFound` if the given
 * path does not exist. Note that the inverse is not true,
 * i.e. if a path does not exist, its removal may fail for a number of reasons,
 * such as insufficient permissions.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     removeDir("/some/dir").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun removeDir(path: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.removeDir(path)

/**
 * Removes a directory at this path, after removing all its contents. Use
 * carefully!
 *
 * This function does **not** follow symbolic links and it will simply remove the
 * symbolic link itself. If `path` itself is a symbolic link to a directory, it will get
 * removed. If it is a symbolic link to a file, behavior is platform-specific:
 * currently, it will get removed on Unix-like systems but not on Windows.
 *
 * # Platform-specific behavior
 *
 * These implementation details [may change in the future][changes].
 *
 * - "Unix-like": By default, this function currently corresponds to
 * `openat`, `fdopendir`, `unlinkat` and `lstat`
 * on Unix-family platforms, except where noted otherwise.
 * - "Windows": This function currently corresponds to `CreateFileW`,
 * `GetFileInformationByHandleEx`, `SetFileInformationByHandle`, and `NtCreateFile`.
 *
 * ## Time-of-check to time-of-use (TOCTOU) race conditions
 * See the [module-level TOCTOU explanation](self#time-of-check-to-time-of-use-toctou).
 *
 * On most platforms, `fs.removeDirAll` protects against symlink TOCTOU races by default.
 * However, on the following platforms, this protection is not provided and the function should
 * not be used in security-sensitive contexts:
 * - **Miri**: Even when emulating targets where the underlying implementation will protect against
 *   TOCTOU races, Miri will not do so.
 * - **ESP-IDF**, **Horizon**, **PS Vita**, **QNX**, **Redox OS**, **VxWorks**: This function does
 *   not protect against TOCTOU races, as the underlying platform does not implement the required
 *   platform support to do so.
 *
 * [TOCTOU]: self#time-of-check-to-time-of-use-toctou
 * [changes]: io#platform-specific-behavior
 *
 * # Errors
 *
 * See [fs.removeFile] and [fs.removeDir].
 *
 * [removeDirAll] will fail if [removeDir] or [removeFile] fail on *any* constituent
 * paths, *including* the root `path`. Consequently,
 *
 * - The directory you are deleting *must* exist, meaning that this function is *not idempotent*.
 * - [removeDirAll] will fail if the `path` is *not* a directory.
 *
 * Consider ignoring the error if validating the removal is not required for your use case.
 *
 * This function may return [io.ErrorKind.DirectoryNotEmpty] if the directory is concurrently
 * written into, which typically indicates some contents were removed but not all.
 * [io.ErrorKind.NotFound] is only returned if no removal occurs.
 *
 * [fs.removeFile]: removeFile
 * [fs.removeDir]: removeDir
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     removeDirAll("/some/dir").getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun removeDirAll(path: Path): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.removeDirAll(path)

/**
 * Returns an iterator over the entries within a directory.
 *
 * The iterator will yield instances of <code>[io.Result]<[DirEntry]></code>.
 * New errors may be encountered after an iterator is initially constructed.
 * Entries for the current and parent directories (typically `.` and `..`) are
 * skipped.
 *
 * The order in which `readDir` returns entries can change between calls. If reproducible
 * ordering is required, the entries should be explicitly sorted.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `opendir` function on Unix
 * and the `FindFirstFileEx` function on Windows. Advancing the iterator
 * currently corresponds to `readdir` on Unix and `FindNextFile` on Windows.
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 *
 * The order in which this iterator returns entries is platform and filesystem
 * dependent.
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * The provided `path` doesn't exist.
 * * The process lacks permissions to view the contents.
 * * The `path` points at a non-directory file.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs.DirEntry
 * import std.io
 * import std.path.Path
 *
 * // one possible implementation of walking a directory only visiting files
 * fun visitDirs(dir: Path, cb: (DirEntry) -> Unit): io.Result<Unit> {
 *     if (dir.isDir()) {
 *         for (entry in fs.readDir(dir).getOrElse { return Result.failure(it) }) {
 *             val entry1 = entry.getOrElse { return Result.failure(it) }
 *             val path = entry1.path()
 *             if (path.isDir()) {
 *                 visitDirs(path, cb).getOrElse { return Result.failure(it) }
 *             } else {
 *                 cb(entry1)
 *             }
 *         }
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * ```kotlin
 * import std.fs
 * import std.io
 *
 * fun main(): io.Result<Unit> {
 *     var entries = readDir(".").getOrElse { return Result.failure(it) }.map({ res -> res.map({ e -> e.path() }) }).collect<Result<Vec>>().getOrElse { return Result.failure(it) }
 *     // The order in which `readDir` returns entries is not guaranteed. If reproducible
 *     // ordering is required the entries should be explicitly sorted.
 *     entries.sort()
 *     // The entries have now been sorted by their path.
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun readDir(path: Path): Result<ReadDir, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.readDir(path).map({ ReadDir(it) })

/**
 * Changes the permissions found on a file or a directory.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the `chmod` function on Unix
 * and the `SetFileAttributes` function on Windows.
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 *
 * ## Symlinks
 * On UNIX-like systems, this function will update the permission bits
 * of the file pointed to by the symlink.
 *
 * Note that this behavior can lead to privilege escalation vulnerabilities,
 * where the ability to create a symlink in one directory allows you to
 * cause the permissions of another file or directory to be modified.
 *
 * For this reason, using this function with symlinks should be avoided.
 * When possible, permissions should be set at creation time instead.
 *
 * # Rationale
 * POSIX does not specify an `lchmod` function,
 * and symlinks can be followed regardless of what permission bits are set.
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * `path` does not exist.
 * * The user lacks the permission to change attributes of the file.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * fun main(): Result<Unit> {
 *     var perms = metadata("foo.txt").getOrElse { return Result.failure(it) }.permissions()
 *     perms.setReadonly(true)
 *     setPermissions("foo.txt", perms).getOrElse { return Result.failure(it) }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun setPermissions(path: Path, perm: Permissions): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.setPermissions(path, perm.value)

/**
 * Changes the permissions found on a file or a directory. On certain platforms, if the file
 * is a symlink, it will change the permissions bits on the symlink itself rather than
 * the target (e.g. Windows, BSD, MacOS). On other platforms, this results in an error when
 * attempting to change permissions on a symlink (e.g. Linux).
 *
 * Note that non-final path elements are allowed to be symlinks.
 *
 * # Platform-specific behavior
 *
 * This function currently corresponds to the following underlying operations:
 * * Android: returns [Unsupported] on all files.
 * * Linux, BSD-based platforms, QNX, NTO: `fchmodat` with `AT_SYMLINK_NOFOLLOW`.
 *   If that is not supported, we fall back to:
 *   * Unix-based platforms with symlinks: `open` with `O_NOFOLLOW` followed by
 *   [fs.setPermissions].
 *   * Unix-based platforms without symlinks: `open` followed by [fs.setPermissions].
 * * Windows: `CreateFileW` with `FILE_FLAG_OPEN_REPARSE_POINT` followed
 *   by `SetFileInformationByHandle`.
 *
 * Note that, this [may change in the future][changes].
 *
 * [changes]: io#platform-specific-behavior
 *
 * # Errors
 *
 * This function will return an error in the following situations, but is not
 * limited to just these cases:
 *
 * * `path` does not exist.
 * * The user lacks the permission to change attributes of the file.
 *
 * Note: On Linux and other Unix-based platforms with symlinks (non-BSD-based),
 * this will result in an [Unsupported] error if the final element is a symlink.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * @feature(setPermissionsNofollow)
 * public fun doctestEntry(): Result<Unit> {
 *     fun main(): Result<Unit> {
 *         var perms = symlinkMetadata("foo.txt").getOrElse { return Result.failure(it) }.permissions()
 *         perms.setReadonly(true)
 *         // This should result in an error on certain platforms or
 *         // succeed in modifying the permissions of a symlink
 *         setPermissionsNofollow("foo.txt", perms).getOrElse { return Result.failure(it) }
 *         return Result.success(Unit)
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 */
public fun setPermissionsNofollow(path: Path, perm: Permissions): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.setPermissionsNofollow(path, perm.value)

/**
 * Returns `Ok(true)` if the path points at an existing entity.
 *
 * This function will traverse symbolic links to query information about the
 * destination file. In case of broken symbolic links this will return `Ok(false)`.
 *
 * As opposed to the [Path.exists] method, this will only return `Ok(true)` or `Ok(false)`
 * if the path was _verified_ to exist or not exist. If its existence can neither be confirmed
 * nor denied, an `Err(_)` will be propagated instead. This can be the case if e.g. listing
 * permission is denied on one of the parent directories.
 *
 * Note that while this avoids some pitfalls of the `exists()` method, it still can not
 * prevent time-of-check to time-of-use ([TOCTOU]) bugs. You should only use it in scenarios
 * where those bugs are not an issue.
 *
 * # Examples
 *
 * ```kotlin
 * import std.fs
 *
 * check(!exists("doesNotExist.txt").expect("Can't check existence of file doesNotExist.txt"))
 * check(exists("/root/secretFile.txt").isErr())
 * ```
 *
 * [TOCTOU]: self#time-of-check-to-time-of-use-toctou
 */
public fun exists(path: Path): Result<Boolean, io.github.kotlinmania.rustlang.std.io.error.Error> = io.github.kotlinmania.rustlang.std.sys.fs.exists(path)
