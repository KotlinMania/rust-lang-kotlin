// port-lint: source sys/fs/windows/remove_dir_all.rs
package io.github.kotlinmania.rustlang.std.sys.fs.windows.removedirall

import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.castMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Align4
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.sys.fs.windows.DirBuff
import io.github.kotlinmania.rustlang.std.sys.fs.windows.File
import io.github.kotlinmania.rustlang.std.sys.pal.api.unicodeStr
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.UnicodeStrRef
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.WinError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.DELETE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_LIST_DIRECTORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_OPEN_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_SHARE_DELETE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_SHARE_READ
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_SHARE_WRITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_SYNCHRONOUS_IO_NONALERT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.OBJ_DONT_REPARSE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.SYNCHRONIZE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ntSuccess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.IoStatusBlock
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ObjectAttributes
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.STATUS_DELETE_PENDING
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.rtlNtStatusToDosError

// The Windows implementation of std.fs.removeDirAll.
//
// This needs to address two issues:
//
// - It must not be possible to trick this into deleting files outside of
//   the parent directory (see CVE-2022-21658).
// - It should not fail if many threads or processes call `removeDirAll`
//   on the same path.
//
// The first is handled by using the low-level `NtOpenFile` API to open a file
// relative to a parent directory.
//
// The second is trickier. Deleting a file works by setting its "disposition"
// to delete. However, it isn't actually deleted until the file is closed.
// During the gap between these two events, the file is in a kind of limbo
// state where it still exists in the filesystem but anything trying to open
// it fails with an error.
//
// The mitigations we use here are:
//
// - When attempting to open the file, we treat ERROR_DELETE_PENDING as a
//   successful delete.
// - If the file still hasn't been removed from the filesystem by the time we
//   attempt to delete the parent directory, we try to wait for it to finish.
//   We can't wait indefinitely though so after some number of spins, we give
//   up and return an error.
//
// In short, we can't guarantee this will always succeed in the event of a
// race but we do make a best effort such that it *should* do so.

// The maximum number of times to spin when waiting for deletes to complete.
public val MAX_RETRIES: Int = 50

/**
 * A wrapper around a raw NtOpenFile call.
 *
 * This isn't completely safe because `OBJECT_ATTRIBUTES` contains raw pointers.
 */
public fun ntOpenFile(access: UInt, objectAttribute: ObjectAttributes, share: UInt, options: UInt): Result<File, WinError> =
    run {
        val handle = MutPtr(0L)
        val ioStatus = IoStatusBlock.PENDING
        val status = io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ntOpenFile(handle, access, objectAttribute, ioStatus, share, options)
        if (ntSuccess(status)) {
            Result.Ok<File, WinError>(File.fromRawHandle(handle))
        } else {
            // Convert an NTSTATUS to the more familiar Win32 error code (aka "DosError")
            val winError =
                if (status == STATUS_DELETE_PENDING) {
                    // We make a special exception for `STATUS_DELETE_PENDING` because
                    // otherwise this will be mapped to `ERROR_ACCESS_DENIED` which is
                    // very unhelpful because that can also mean a permission error.
                    WinError.DELETE_PENDING
                } else {
                    WinError.new(rtlNtStatusToDosError(status))
                }
            Result.Err<File, WinError>(winError)
        }
    }

/**
 * Open the file `path` in the directory `parent`, requesting the given `access` rights.
 * `options` will be OR'd with `FILE_OPEN_REPARSE_POINT`.
 */
public fun openLinkNoReparse(parent: File, path: UnicodeStrRef, access: UInt, options: UInt): Result<File?, WinError> {
    // This is implemented using the lower level `NtOpenFile` function as
    // unfortunately opening a file relative to a parent is not supported by
    // win32 functions.
    //
    // See https://learn.microsoft.com/windows/win32/api/winternl/nf-winternl-ntopenfile
    // The `OBJ_DONT_REPARSE` attribute ensures that we haven't been
    // tricked into following a symlink. However, it may not be available in
    // earlier versions of Windows.
    val ATTRIBUTES: Atomic<UInt, Align4<UInt>>  = AtomicU32.new(OBJ_DONT_REPARSE)
    val result1 =
        run {
            val obj = ObjectAttributes.withLength().copy(objectName = path.asPtr().castMut(), rootDirectory = parent.asRawHandle(), attributes = ATTRIBUTES.load(Ordering.Relaxed))
            val share = FILE_SHARE_DELETE or FILE_SHARE_READ or FILE_SHARE_WRITE
            val options1 = FILE_OPEN_REPARSE_POINT or options
            val result = ntOpenFile(access, obj, share, options1)
            // Retry without OBJ_DONT_REPARSE if it's not supported.
            if (when {
                result is Result.Err<*, *> && result.errValue() == WinError.INVALID_PARAMETER -> {
                    val errValueVal = result.errValue()
                    true
                }
                else -> false
            } && ATTRIBUTES.load(Ordering.Relaxed) == OBJ_DONT_REPARSE) {
                ATTRIBUTES.store(0, Ordering.Relaxed)
                obj.attributes = 0u
                ntOpenFile(access, obj, share, options1)
            } else {
                result
            }
        }
    // Ignore not found errors
    return when {
        result1 is Result.Ok<*, *> -> {
            val f = result1.okValue()
            Result.Ok<File?, WinError>(f)
        }
        result1 is Result.Err<*, *> && (result1.errValue() == WinError.FILE_NOT_FOUND || result1.errValue() == WinError.PATH_NOT_FOUND || result1.errValue() == WinError.BAD_NETPATH || result1.errValue() == WinError.BAD_NET_NAME || result1.errValue() == WinError.DELETE_PENDING) -> {
            val errValueVal1 = (result1 as Result.Err<*, *>).errValue()
            Result.Ok<File?, WinError>(null)
        }
        else -> {
            val e = (result1 as Result.Err<*, *>).errValue()
            Result.Err<File?, WinError>(e)
        }
    }
}

public fun openDir(parent: File, name: UnicodeStrRef): Result<File?, WinError> {
    // Open the directory for synchronous directory listing.
    return openLinkNoReparse(parent, name, SYNCHRONIZE or FILE_LIST_DIRECTORY, FILE_SYNCHRONOUS_IO_NONALERT)
}

public fun delete(parent: File, name: UnicodeStrRef): Result<Unit, WinError> {
    // Note that the `delete` function consumes the opened file to ensure it's
    // dropped immediately. See module comments for why this is important.
    return run {
        val matchVal = openLinkNoReparse(parent, name, DELETE, 0u)
        when {
            matchVal is Result.Ok<*, *> && matchVal.okValue() != null -> {
                val okValueVal = matchVal.okValue()
                val f = okValueVal
                f.delete()
            }
            matchVal is Result.Ok<*, *> && matchVal.okValue() == null -> {
                val None = matchVal.okValue()
                Result.Ok<Unit, WinError>(Unit)
            }
            else -> {
                val e = (matchVal as Result.Err<*, *>).errValue()
                Result.Err<Unit, WinError>(e)
            }
        }
    }
}

/**
 * A simple retry loop that keeps running `f` while it fails with the given
 * error code or until `MAX_RETRIES` is reached.
 */
public fun <T> retry(f: () -> Result<T, WinError>, ignore: WinError): Result<T, WinError> {
    var i = MAX_RETRIES
    while (true) {
        i -= 1
        if (i == 0) {
            return f()
        } else {
            val result = f()
            if (!(result).rustEquals(Result.Err<T, WinError>(ignore))) {
                return result
            }
        }
        thread.yieldNow()
    }
}

public fun removeDirAllIterative(dir: File): Result<Unit, WinError> {
    val dir1 = dir
    val buffer = DirBuff.new()
    val dirlist = mutableListOf(dir1)
    while (true) {
        val matchVal = dirlist.removeLastOrNull()
        if (matchVal == null) break
        val dir2 = matchVal
        val restart = false
        // we never have to restart, we iterate every directory only once
        val moreData = when (val tryResult = dir2.fillDirBuff(buffer, restart)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, WinError>(tryResult.errValue()) }
        if (!moreData) {
            // This directory is done!
            // Attempt to delete, retrying on not empty errors because we may
            // need to wait some time for files to be removed from the filesystem.
            val name = unicodeStr("")
            when (val tryResult = retry({ delete(dir2, name) }, WinError.DIR_NOT_EMPTY)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, WinError>(tryResult.errValue()) }
            continue
        }
        // There is more data, so we have to re-insert `dir` so it gets visited again. We need to
        // push it first so that it gets visited after any subdirs that we push below. We also need
        // to still access it during the `for` loop so remember where we put it.
        val dirIdx = dirlist.size
        dirlist.add(dir2)
        // To avoid indexing `dirlist` each time through the loop, we use a reference that we
        // re-compute whenever the borrow got invalidated.
        var dir3 = dirlist[dirIdx]
        for ((name1, isDirectory) in buffer.iter()) {
            val name2 = unicodeStr(name1)
            if (isDirectory) {
                val subdir = when (val tryResult = openDir(dir3, name2)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, WinError>(tryResult.errValue()) } ?: continue
                dirlist.add(subdir)
                // We need to re-create the borrow.
                dir3 = dirlist[dirIdx]
            } else {
                // Attempt to delete, retrying on sharing violation errors as these
                // can often be very temporary. E.g. if something takes just a
                // bit longer than expected to release a file handle.
                when (val tryResult = retry({ delete(dir3, name2) }, WinError.SHARING_VIOLATION)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, WinError>(tryResult.errValue()) }
            }
        }
    }
    return Result.Ok<Unit, WinError>(Unit)
}
