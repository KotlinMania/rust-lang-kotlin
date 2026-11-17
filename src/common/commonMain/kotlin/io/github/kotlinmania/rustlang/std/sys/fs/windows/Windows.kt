// port-lint: source sys/fs/windows.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.sys.fs.windows

import io.github.kotlinmania.rustlang.alloc.alloc.alloc
import io.github.kotlinmania.rustlang.alloc.alloc.dealloc
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.mem.get
import io.github.kotlinmania.rustlang.core.mem.linearSliceView
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.sizeOfVal
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.orElse
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.splitAtMut
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fs.TryLockError
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.os.windows.io.AsHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.AsRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.BorrowedHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.FromRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.IntoRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.RawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.asHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.asRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.fromRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.intoRawHandle
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.path.absolute
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.args.windows.fromWideToUserPath
import io.github.kotlinmania.rustlang.std.sys.asInner
import io.github.kotlinmania.rustlang.std.sys.fromInner
import io.github.kotlinmania.rustlang.std.sys.fs.windows.dir.Dir
import io.github.kotlinmania.rustlang.std.sys.fs.windows.removedirall.removeDirAllIterative
import io.github.kotlinmania.rustlang.std.sys.intoInner
import io.github.kotlinmania.rustlang.std.sys.osstr.bytes.Slice
import io.github.kotlinmania.rustlang.std.sys.pal.windows.Align8
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.WinError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.getLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.getLogicalDrives
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.queryDosDevice
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.setFileInformationByHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.CREATE_NEW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.DELETE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_ACCESS_DENIED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_CANT_ACCESS_FILE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_DIRECTORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_INVALID_FUNCTION
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_INVALID_PARAMETER
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_NO_MORE_FILES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_PATH_NOT_FOUND
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_SHARING_VIOLATION
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_ATTRIBUTE_DIRECTORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_ATTRIBUTE_READONLY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_ATTRIBUTE_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_BEGIN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_CREATE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_CURRENT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_DISPOSITION_FLAG_DELETE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_DISPOSITION_FLAG_IGNORE_READONLY_ATTRIBUTE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_DISPOSITION_FLAG_POSIX_SEMANTICS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_END
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_FLAG_BACKUP_SEMANTICS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_FLAG_OPEN_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_FLAG_POSIX_SEMANTICS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_GENERIC_WRITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_LIST_DIRECTORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_OPEN
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_OPEN_IF
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_OVERWRITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_OVERWRITE_IF
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_RENAME_FLAG_POSIX_SEMANTICS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_RENAME_FLAG_REPLACE_IF_EXISTS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_SHARE_DELETE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_SHARE_READ
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_SHARE_WRITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_WRITE_ATTRIBUTES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_WRITE_DATA
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FSCTL_GET_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FSCTL_SET_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.GENERIC_WRITE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.INVALID_HANDLE_VALUE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.LOCKFILE_EXCLUSIVE_LOCK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.LOCKFILE_FAIL_IMMEDIATELY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.MOVEFILE_REPLACE_EXISTING
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.MountPointReparseBuffer
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.OPEN_ALWAYS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.OPEN_EXISTING
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.PROGRESS_CONTINUE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.SECURITY_SQOS_PRESENT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.SYMBOLIC_LINK_FLAG_ALLOW_UNPRIVILEGED_CREATE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.SYMBOLIC_LINK_FLAG_DIRECTORY
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.SYMLINK_FLAG_RELATIVE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.SymbolicLinkReparseBuffer
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.TRUNCATE_EXISTING
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.VOLUME_NAME_DOS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.VOLUME_NAME_NT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ByHandleFileInformation
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_IO_PENDING
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_LOCK_VIOLATION
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ERROR_NOT_LOCKED
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FALSE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_ID_BOTH_DIRECTORY_INFO
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_ID_BOTH_DIRECTORY_RESTART_INFO
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FILE_RENAME_INFO_EX
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FIND_EX_INFO_BASIC
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FIND_EX_SEARCH_NAME_MATCH
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileAllocationInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileAttributeTagInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileBasicInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileDispositionInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileDispositionInfoEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileEndOfFileInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileIdBothDirInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileRenameInfo
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileRenameInfo0
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Filetime
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.GENERIC_READ
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.IO_REPARSE_TAG_MOUNT_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.IO_REPARSE_TAG_SYMLINK
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.MAXIMUM_REPARSE_DATA_BUFFER_SIZE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.MAX_PATH
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Overlapped
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SecurityAttributes
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.TRUE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.Win32FindDataw
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.closeHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.copyFileExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.createDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.createEventW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.createFileW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.createHardLinkW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.createSymbolicLinkW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.deleteFileW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.deviceIoControl
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.findClose
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.findFirstFileExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.findNextFileW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.flushFileBuffers
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getFileInformationByHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getFileInformationByHandleEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getFileSizeEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getFinalPathNameByHandleW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getOverlappedResult
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.lockFileEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.moveFileExW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.removeDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.setFileAttributesW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.setFilePointerEx
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.setFileTime
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.unlockFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.fillUtf16Buf
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.toU16s
import io.github.kotlinmania.rustlang.std.sys.pal.windows.truncateUtf16AtNul
import io.github.kotlinmania.rustlang.std.sys.path.windows.WCStr
import io.github.kotlinmania.rustlang.std.sys.path.windows.maybeVerbatim
import io.github.kotlinmania.rustlang.std.sys.time.windows.SystemTime

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

public open class File internal constructor(
    handleCtorArg: io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle,
) : AsInner<io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle>, IntoInner<io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle>, FromInner<io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle, File>, AsHandle, AsRawHandle, IntoRawHandle, FromRawHandle<File> {
    public var handle: io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle = handleCtorArg
        internal set

    public open fun fsync(): Result<Unit, Error> {
        when (val tryResult = cvt(flushFileBuffers(this.handle.asRawHandle()))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun datasync(): Result<Unit, Error> = this.fsync()

    public open fun acquireLock(flags: UInt): Result<Unit, Error> {
        return run {
            val overlapped: Overlapped  = Overlapped()
            val event = createEventW(MutPtr(0L), FALSE, FALSE, ConstPtr(0L))
            if (event.isNull()) {
                return Result.Err<Unit, Error>(Error.lastOsError())
            }
            overlapped.hEvent = event
            val lockResult = cvt(lockFileEx(this.handle.asRawHandle(), flags, 0u, UInt.MAX_VALUE, UInt.MAX_VALUE, overlapped))
            val finalResult =
                when (lockResult) { is Result.Ok<*, *> -> {
                    Result.Ok<Unit, Error>(Unit)
                }; is Result.Err<*, *> -> {
                    val err = lockResult.error
                    if (err.rawOsError() == ERROR_IO_PENDING.toInt()) {
                        // Wait for the lock to be acquired, and get the lock operation status.
                        // This can happen asynchronously, if the file handle was opened for async IO
                        val localStorageL426C24 = AddressableLocal(0, PrimitiveLayouts.I32)
                        cvt(getOverlappedResult(this.handle.asRawHandle(), overlapped, RustMutableReference(localStorageL426C24.asMutPtr(), localStorageL426C24.layout), TRUE)).map({ Unit })
                    } else {
                        Result.Err<Unit, Error>(err)
                    }
                } }
            closeHandle(overlapped.hEvent)
            finalResult
        }
    }

    public open fun lock(): Result<Unit, Error> = this.acquireLock(LOCKFILE_EXCLUSIVE_LOCK)

    public open fun lockShared(): Result<Unit, Error> = this.acquireLock(0)

    public open fun tryLock(): Result<Unit, TryLockError> {
        val result =
            cvt(run {
                val overlapped = MaybeUninit.zeroed(UnresolvedTypeLayout<Result<Unit, TryLockError>>()).assumeInit()
                lockFileEx(this.handle.asRawHandle(), LOCKFILE_EXCLUSIVE_LOCK or LOCKFILE_FAIL_IMMEDIATELY, 0u, UInt.MAX_VALUE, UInt.MAX_VALUE, overlapped)
            })
        return when {
            result is Result.Ok<*, *> -> Result.Ok<Unit, TryLockError>(Unit)
            result is Result.Err<*, *> && result.error.rawOsError() == ERROR_LOCK_VIOLATION.toInt() -> {
                val err = result.error
                Result.Err<Unit, TryLockError>(TryLockError.WouldBlock)
            }
            else -> {
                val err1 = (result as Result.Err<*, *>).error
                Result.Err<Unit, TryLockError>(TryLockError.Error(err1))
            }
        }
    }

    public open fun tryLockShared(): Result<Unit, TryLockError> {
        val result =
            cvt(run {
                val overlapped = MaybeUninit.zeroed(UnresolvedTypeLayout<Result<Unit, TryLockError>>()).assumeInit()
                lockFileEx(this.handle.asRawHandle(), LOCKFILE_FAIL_IMMEDIATELY, 0u, UInt.MAX_VALUE, UInt.MAX_VALUE, overlapped)
            })
        return when {
            result is Result.Ok<*, *> -> Result.Ok<Unit, TryLockError>(Unit)
            result is Result.Err<*, *> && result.error.rawOsError() == ERROR_LOCK_VIOLATION.toInt() -> {
                val err = result.error
                Result.Err<Unit, TryLockError>(TryLockError.WouldBlock)
            }
            else -> {
                val err1 = (result as Result.Err<*, *>).error
                Result.Err<Unit, TryLockError>(TryLockError.Error(err1))
            }
        }
    }

    public open fun unlock(): Result<Unit, Error> {
        // Unlock the handle twice because LockFileEx() allows a file handle to acquire
        // both an exclusive and shared lock, in which case the documentation states that:
        // "...two unlock operations are necessary to unlock the region; the first unlock operation
        // unlocks the exclusive lock, the second unlock operation unlocks the shared lock"
        when (val tryResult = cvt(unlockFile(this.handle.asRawHandle(), 0u, 0u, UInt.MAX_VALUE, UInt.MAX_VALUE))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
        val result = cvt(unlockFile(this.handle.asRawHandle(), 0u, 0u, UInt.MAX_VALUE, UInt.MAX_VALUE))
        return when {
            result is Result.Ok<*, *> -> Result.Ok<Unit, Error>(Unit)
            result is Result.Err<*, *> && result.error.rawOsError() == ERROR_NOT_LOCKED.toInt() -> {
                val err = result.error
                Result.Ok<Unit, Error>(Unit)
            }
            else -> {
                val err1 = (result as Result.Err<*, *>).error
                Result.Err<Unit, Error>(err1)
            }
        }
    }

    public open fun truncate(size: ULong): Result<Unit, Error> {
        val info = FileEndOfFileInfo(endOfFile = size.toLong())
        return setFileInformationByHandle(this.handle.asRawHandle(), info).ioResult()
    }

    public open fun fileAttr(): Result<FileAttr, Error> {
        return run {
            val info: ByHandleFileInformation  = ByHandleFileInformation()
            when (val tryResult = cvt(getFileInformationByHandle(this.handle.asRawHandle(), info))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<FileAttr, Error>(tryResult.error) }
            var reparseTag = 0
            if (info.dwFileAttributes and FILE_ATTRIBUTE_REPARSE_POINT != 0u) {
                val localStorageL523C16 = AddressableLocal(FileAttributeTagInfo(), UnresolvedTypeLayout<FileAttributeTagInfo>())
                val attrTag by localStorageL523C16
                when (val tryResult = cvt(getFileInformationByHandleEx(this.handle.asRawHandle(), FileAttributeTagInfo, (localStorageL523C16.asMutPtr()), sizeOf<FileAttributeTagInfo>(typeLayoutT = UnresolvedTypeLayout<FileAttributeTagInfo>()).tryInto().unwrap()))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<FileAttr, Error>(tryResult.error) }
                if (attrTag.fileAttributes and FILE_ATTRIBUTE_REPARSE_POINT != 0u) {
                    reparseTag = attrTag.reparseTag
                }
            }
            Result.Ok<FileAttr, Error>(FileAttr(attributes = info.dwFileAttributes, creationTime = info.ftCreationTime, lastAccessTime = info.ftLastAccessTime, lastWriteTime = info.ftLastWriteTime, changeTime = null, fileSize = (info.nFileSizeLow.toULong()) or ((info.nFileSizeHigh.toULong()) shl 32), reparseTag = reparseTag, volumeSerialNumber = info.dwVolumeSerialNumber, numberOfLinks = info.nNumberOfLinks, fileIndex = (info.nFileIndexLow.toULong()) or ((info.nFileIndexHigh.toULong()) shl 32)))
        }
    }

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> = this.handle.read(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = this.handle.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.handle.isReadVectored()

    public open fun readAt(buf: MutableList<UByte>, offset: ULong): Result<Int, Error> = this.handle.readAt(buf, offset)

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit, Error> = this.handle.readBuf(cursor)

    public open fun readBufAt(cursor: BorrowedCursor<UByte>, offset: ULong): Result<Unit, Error> = this.handle.readBufAt(cursor, offset)

    public open fun write(buf: List<UByte>): Result<Int, Error> = this.handle.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = this.handle.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.handle.isWriteVectored()

    public open fun writeAt(buf: List<UByte>, offset: ULong): Result<Int, Error> = this.handle.writeAt(buf, offset)

    public open fun flush(): Result<Unit, Error> = Result.Ok<Unit, Error>(Unit)

    public open fun seek(pos: SeekFrom): Result<ULong, Error> {
        val (whence, pos1) =
            when (pos) {
                // Casting to `Long` is fine, `SetFilePointerEx` reinterprets this
                // integer as `ULong`.
                is SeekFrom.Start -> {
                    val (n) = pos
                    Pair(FILE_BEGIN, n.toLong())
                }
                is SeekFrom.End -> {
                    val (n1) = pos
                    Pair(FILE_END, n1)
                }
                is SeekFrom.Current -> {
                    val (n2) = pos
                    Pair(FILE_CURRENT, n2)
                }
            }
        val pos2 = pos1.toLong()
        val localStorageL667C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        val newpos by localStorageL667C8
        when (val tryResult = cvt(setFilePointerEx(this.handle.asRawHandle(), pos2, RustMutableReference(localStorageL667C8.asMutPtr(), localStorageL667C8.layout), whence))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<ULong, Error>(tryResult.error) }
        return Result.Ok<ULong, Error>(newpos.toULong())
    }

    public open fun size(): Result<ULong, Error>? {
        val localStorageL673C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        val result by localStorageL673C8
        return cvt(getFileSizeEx(this.handle.asRawHandle(), RustMutableReference(localStorageL673C8.asMutPtr(), localStorageL673C8.layout))).map({ result.toULong() })
    }

    public open fun tell(): Result<ULong, Error> = this.seek(SeekFrom.Current(0))

    public open fun duplicate(): Result<File, Error> {
        return Result.Ok<File, Error>(File(handle = when (val tryResult = this.handle.tryClone()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<File, Error>(tryResult.error) }))
    }

    // NB: returned pointer is derived from `space`, and has provenance to
    // match. A raw pointer is returned rather than a reference in order to
    // avoid narrowing provenance to the actual `REPARSE_DATA_BUFFER`.
    public open fun reparsePoint(space: Align8<List<MaybeUninit<UByte>>>): Result<Pair<UInt, MutPtr>, Error> {
        return run {
            val localStorageL696C12 = AddressableLocal(0, PrimitiveLayouts.I32)
            val bytes by localStorageL696C12
            when (val tryResult = cvt(run {
                // Grab this in advance to avoid it invalidating the pointer
                // we get from `space.0.asMutPtr()`.
                val len = space.first.len()
                deviceIoControl(this.handle.asRawHandle(), FSCTL_GET_REPARSE_POINT, MutPtr(0L), 0u, space.first.asMutPtr().cast(), len.toUInt(), RustMutableReference(localStorageL696C12.asMutPtr(), localStorageL696C12.layout), MutPtr(0L))
            })) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Pair<UInt, MutPtr>, Error>(tryResult.error) }
            Result.Ok<Pair<UInt, MutPtr>, Error>(Pair(bytes, space.first.asMutPtr().cast()))
        }
    }

    public open fun readlink(): Result<PathBuf, Error> {
        val space = Align8(List(MAXIMUM_REPARSE_DATA_BUFFER_SIZE.toInt()) { MaybeUninit.uninit(PrimitiveLayouts.U8) })
        val (bytes, buf) = when (val tryResult = this.reparsePoint(space)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<PathBuf, Error>(tryResult.error) }
        return run {
            val (pathBuffer, substOff, substLen, relative) =
                when (buf.reparseTag) {
                    IO_REPARSE_TAG_SYMLINK -> {
                        val info: MutPtr = (addrOfMut(buf.rest)).cast()
                        check(info.isAligned())
                        listOf(MutPtr(((addrOfMut((UnresolvedTypeLayout<SymbolicLinkReparseBuffer>()).readNative((info).rawAddress).pathBuffer))).rawAddress), (UnresolvedTypeLayout<SymbolicLinkReparseBuffer>()).readNative((info).rawAddress).substituteNameOffset / 2u, (UnresolvedTypeLayout<SymbolicLinkReparseBuffer>()).readNative((info).rawAddress).substituteNameLength / 2u, (UnresolvedTypeLayout<SymbolicLinkReparseBuffer>()).readNative((info).rawAddress).flags and SYMLINK_FLAG_RELATIVE != 0uL)
                    }
                    IO_REPARSE_TAG_MOUNT_POINT -> {
                        val info1: MutPtr = (addrOfMut(buf.rest)).cast()
                        check(info1.isAligned())
                        listOf(MutPtr(((addrOfMut((UnresolvedTypeLayout<MountPointReparseBuffer>()).readNative((info1).rawAddress).pathBuffer))).rawAddress), (UnresolvedTypeLayout<MountPointReparseBuffer>()).readNative((info1).rawAddress).substituteNameOffset / 2u, (UnresolvedTypeLayout<MountPointReparseBuffer>()).readNative((info1).rawAddress).substituteNameLength / 2u, false)
                    }
                    else -> {
                        return Result.Err<PathBuf, Error>(constError(ErrorKind.Uncategorized, "Unsupported reparse point type"))
                    }
                }
            // Check that the reparse point offsets and length stay within the data that
            // was actually returned, before the slice is built from them. This keeps
            // malformed filesystem data from causing an out-of-bounds access.
            val pathOffset = pathBuffer.addr() - buf.addr()
            val end = pathOffset + (substOff.toInt() * 2) + (substLen.toInt() * 2)
            if (end > bytes.toInt()) {
                return Result.Err<PathBuf, Error>(constError(ErrorKind.Uncategorized, "Invalid reparse point data"))
            }
            val substPtr = pathBuffer.add(substOff.into())
            val subst = Slice.fromRawPartsMut(substPtr, substLen.toInt())
            // Absolute paths start with an NT internal namespace prefix `\??\`
            // We should not let it leak through.
            if (!relative && subst.startsWith(ushortArrayOf(92u.toUShort(), 63u.toUShort(), 63u.toUShort(), 92u.toUShort()))) {
                // Turn `\??\` into `\\?\` (a verbatim path).
                subst[1] = 92u.toUShort()
                // Attempt to convert to a more user-friendly path.
                val user = when (val tryResult = fromWideToUserPath(subst.iter().copied().chain(listOf(0)).collect())) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<PathBuf, Error>(tryResult.error) }
                Result.Ok<PathBuf, Error>(PathBuf.from(OsString.fromWide((user.takeIf { it.endsWith(listOf(0)) }?.removeSuffix(listOf(0)) ?: user))))
            } else {
                Result.Ok<PathBuf, Error>(PathBuf.from(OsString.fromWide(subst)))
            }
        }
    }

    public open fun setPermissions(perm: FilePermissions): Result<Unit, Error> {
        val info = FileBasicInfo(creationTime = 0L, lastAccessTime = 0L, lastWriteTime = 0L, changeTime = 0L, fileAttributes = perm.attrs)
        return setFileInformationByHandle(this.handle.asRawHandle(), info).ioResult()
    }

    public open fun setTimes(times: FileTimes): Result<Unit, Error> {
        val isZero = { t: Filetime -> t.dwLowDateTime == 0 && t.dwHighDateTime == 0 }
        if (times.accessed.isSomeAnd(isZero) || times.modified.isSomeAnd(isZero) || times.created.isSomeAnd(isZero)) {
            return Result.Err<Unit, Error>(constError(ErrorKind.InvalidInput, "cannot set file timestamp to 0"))
        }
        val isMax = { t: Filetime -> t.dwLowDateTime == UInt.MAX_VALUE && t.dwHighDateTime == UInt.MAX_VALUE }
        if (times.accessed.isSomeAnd(isMax) || times.modified.isSomeAnd(isMax) || times.created.isSomeAnd(isMax)) {
            return Result.Err<Unit, Error>(constError(ErrorKind.InvalidInput, "cannot set file timestamp to 0xFFFF_FFFF_FFFF_FFFF"))
        }
        when (val tryResult = cvt(run {
            val created = (times.created?.let({ a -> constPtrFromRustCast(a) }) ?: ConstPtr(0L))
            val accessed = (times.accessed?.let({ a -> constPtrFromRustCast(a) }) ?: ConstPtr(0L))
            val modified = (times.modified?.let({ a -> constPtrFromRustCast(a) }) ?: ConstPtr(0L))
            setFileTime(this.asRawHandle(), created, accessed, modified)
        })) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
        return Result.Ok<Unit, Error>(Unit)
    }

    /**
     * Gets only basic file information such as attributes and file times.
     */
    public open fun basicInfo(): Result<FileBasicInfo, Error> {
        return run {
            val localStorageL826C12 = AddressableLocal(FileBasicInfo(), UnresolvedTypeLayout<FileBasicInfo>())
            val info by localStorageL826C12
            val size = sizeOfVal(info, typeLayoutT = UnresolvedTypeLayout<FileBasicInfo>())
            when (val tryResult = cvt(getFileInformationByHandleEx(this.handle.asRawHandle(), FileBasicInfo, ((localStorageL826C12.asMutPtr())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, size.toUInt()))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<FileBasicInfo, Error>(tryResult.error) }
            Result.Ok<FileBasicInfo, Error>(info)
        }
    }

    /**
     * Deletes the file, consuming the file handle to ensure the delete occurs
     * as immediately as possible.
     * This attempts to use `posixDelete` but falls back to `win32Delete`
     * if that is not supported by the filesystem.
     */
    public open fun delete(): Result<Unit, WinError> {
        // If POSIX delete is not supported for this filesystem then fallback to win32 delete.
        return run {
            val matchVal = this.posixDelete()
            when {
                matchVal is Result.Err<*, *> && matchVal.error == WinError.INVALID_PARAMETER -> {
                    val errorVal = matchVal.error
                    this.win32Delete()
                }
                matchVal is Result.Err<*, *> && matchVal.error == WinError.NOT_SUPPORTED -> {
                    val errorVal1 = matchVal.error
                    this.win32Delete()
                }
                matchVal is Result.Err<*, *> && matchVal.error == WinError.INVALID_FUNCTION -> {
                    val errorVal2 = matchVal.error
                    this.win32Delete()
                }
                else -> {
                    val result = matchVal
                    result
                }
            }
        }
    }

    /**
     * Delete using POSIX semantics.
     *
     * Files will be deleted as soon as the handle is closed. This is supported
     * for Windows 10 1607 (aka RS1) and later. However some filesystem
     * drivers will not support it even then, e.g. FAT32.
     *
     * If the operation is not supported for this filesystem or OS version
     * then errors will be `ERROR_NOT_SUPPORTED` or `ERROR_INVALID_PARAMETER`.
     */
    public open fun posixDelete(): Result<Unit, WinError> {
        val info = FileDispositionInfoEx(flags = FILE_DISPOSITION_FLAG_DELETE or FILE_DISPOSITION_FLAG_POSIX_SEMANTICS or FILE_DISPOSITION_FLAG_IGNORE_READONLY_ATTRIBUTE)
        return setFileInformationByHandle(this.handle.asRawHandle(), info)
    }

    /**
     * Delete a file using win32 semantics. The file won't actually be deleted
     * until all file handles are closed. However, marking a file for deletion
     * will prevent anyone from opening a new handle to the file.
     */
    public open fun win32Delete(): Result<Unit, WinError> {
        val info = FileDispositionInfo(deleteFile = true)
        return setFileInformationByHandle(this.handle.asRawHandle(), info)
    }

    /**
     * Fill the given buffer with as many directory entries as will fit.
     * This will remember its position and continue from the last call unless
     * `restart` is set to `true`.
     *
     * The returned bool indicates if there are more entries or not.
     * You must only iterate the buffer if this returned `true`!
     * It is an error if `self` is not a directory.
     *
     * # Symlinks and other reparse points
     *
     * On Windows a file is either a directory or a non-directory.
     * A symlink directory is simply an empty directory with some "reparse" metadata attached.
     * So if you open a link (not its target) and iterate the directory,
     * you will always iterate an empty directory regardless of the target.
     */
    public open fun fillDirBuff(buffer: DirBuff, restart: Boolean): Result<Boolean, WinError> {
        val cls =
            if (restart) {
                FILE_ID_BOTH_DIRECTORY_RESTART_INFO
            } else {
                FILE_ID_BOTH_DIRECTORY_INFO
            }
        return run {
            val result = getFileInformationByHandleEx(this.asRawHandle(), cls, buffer.asMutPtr(), buffer.capacity().toUInt())
            if (result == 0) {
                // Apparently ERROR_NO_MORE_FILES also means that the buffer is empty / was left
                // unchanged. Note: find docs that say this.
                val err = getLastError()
                if (err.code == ERROR_NO_MORE_FILES) {
                    Result.Ok<Boolean, WinError>(false)
                } else {
                    Result.Err<Boolean, WinError>(err)
                }
            } else {
                Result.Ok<Boolean, WinError>(true)
            }
        }
    }

    public override fun asInner(): io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle = this.handle

    public override fun intoInner(): io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle = this.handle

    public override fun asHandle(): BorrowedHandle = this.asInner().asHandle()

    public override fun asRawHandle(): RawHandle = this.asInner().asRawHandle()

    public override fun intoRawHandle(): RawHandle = this.intoInner().intoRawHandle()

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val b = debugPathHandle(this.handle.asHandle(), f, "File")
        return run { b.finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public companion object {
        public operator fun invoke(
            handle: io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle,
        ): File = File(
            handleCtorArg = handle,
        )

        public fun open(path: Path, opts: OpenOptions): Result<File, Error> {
            val path1 = when (val tryResult = maybeVerbatim(path)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<File, Error>(tryResult.error) }
            // SAFETY: maybeVerbatim returns null-terminated strings
            val path2 = WCStr.fromWcharsWithNullUnchecked(path1)
            return File.openNative(path2, opts)
        }

        public fun openNative(path: WCStr, opts: OpenOptions): Result<File, Error> {
            val creation = when (val tryResult = opts.getCreationMode()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<File, Error>(tryResult.error) }
            val sa = SecurityAttributes(nLength = sizeOf<SecurityAttributes>(typeLayoutT = UnresolvedTypeLayout<SecurityAttributes>()).toUInt(), lpSecurityDescriptor = MutPtr(0L), bInheritHandle = opts.inheritHandle.toInt())
            val handle =
                createFileW(path.asPtr(), when (val tryResult = opts.getAccessMode()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<File, Error>(tryResult.error) }, opts.shareMode, if (opts.inheritHandle) {
                    sa
                } else {
                    ConstPtr(0L)
                }, creation, opts.getFlagsAndAttributes(), MutPtr(0L))
            val handle1 = HandleOrInvalid.fromRawHandle(handle)
            return when (val matchVal = OwnedHandle.tryFrom(handle1)) {
                is Result.Ok<*, *> -> {
                    val handle2 = matchVal.value
                    if (opts.freezeLastAccessTime || opts.freezeLastWriteTime) {
                        val fileTime = Filetime(dwLowDateTime = 0xFFFFFFFFu, dwHighDateTime = 0xFFFFFFFFu)
                        when (val tryResult = cvt(setFileTime(handle2.asRawHandle(), ConstPtr(0L), if (opts.freezeLastAccessTime) {
                            fileTime
                        } else {
                            ConstPtr(0L)
                        }, if (opts.freezeLastWriteTime) {
                            fileTime
                        } else {
                            ConstPtr(0L)
                        }))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<File, Error>(tryResult.error) }
                    }
                    // Manual truncation. See #115745.
                    if (opts.truncate && creation == OPEN_ALWAYS && getLastError() == WinError.ALREADY_EXISTS) {
                        // This first tries `FileAllocationInfo` but falls back to
                        // `FileEndOfFileInfo` in order to support WINE.
                        // If WINE gains support for FileAllocationInfo, we should
                        // remove the fallback.
                        val alloc = FileAllocationInfo(allocationSize = 0L)
                        when (val tryResult = setFileInformationByHandle(handle2.asRawHandle(), alloc).orElse({
                            val eof = FileEndOfFileInfo(endOfFile = 0L)
                            setFileInformationByHandle(handle2.asRawHandle(), eof)
                        }).ioResult()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<File, Error>(tryResult.error) }
                    }
                    Result.Ok<File, Error>(File(handle = Handle.fromInner(handle2)))
                }
                else -> {
                    Result.Err<File, Error>(Error.lastOsError())
                }
            }
        }

        public fun fromInner(handle: io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle): File = File(handle = handle)

        public fun fromRawHandle(rawHandle: RawHandle): File = File(handle = FromInner.fromInner(FromRawHandle.fromRawHandle(rawHandle)))
    }
}

public open class FileAttr(
    public var attributes: UInt,
    public var creationTime: Filetime,
    public var lastAccessTime: Filetime,
    public var lastWriteTime: Filetime,
    public var changeTime: Filetime?,
    public var fileSize: ULong,
    public var reparseTag: UInt,
    public var volumeSerialNumber: UInt?,
    public var numberOfLinks: UInt?,
    public var fileIndex: ULong?,
) {
    public open fun clone(): FileAttr = FileAttr(attributes, creationTime, lastAccessTime, lastWriteTime, changeTime, fileSize, reparseTag, volumeSerialNumber, numberOfLinks, fileIndex)

    public open fun size(): ULong = this.fileSize

    public open fun perm(): FilePermissions = FilePermissions(attrs = this.attributes)

    public open fun attrs(): UInt = this.attributes

    public open fun fileType(): FileType = FileType.new(this.attributes, this.reparseTag)

    public open fun modified(): Result<SystemTime, Error> = Result.Ok<SystemTime, Error>(SystemTime.from(this.lastWriteTime))

    public open fun accessed(): Result<SystemTime, Error> = Result.Ok<SystemTime, Error>(SystemTime.from(this.lastAccessTime))

    public open fun created(): Result<SystemTime, Error> = Result.Ok<SystemTime, Error>(SystemTime.from(this.creationTime))

    public open fun modifiedU64(): ULong = toU64(this.lastWriteTime)

    public open fun accessedU64(): ULong = toU64(this.lastAccessTime)

    public open fun createdU64(): ULong = toU64(this.creationTime)

    public open fun changedU64(): ULong? = this.changeTime?.let(::toU64)

    public open fun volumeSerialNumber(): UInt? = this.volumeSerialNumber

    public open fun numberOfLinks(): UInt? = this.numberOfLinks

    public open fun fileIndex(): ULong? = this.fileIndex

    public companion object {
        public fun from(wfd: Win32FindDataw): FileAttr =
            FileAttr(attributes = wfd.dwFileAttributes, creationTime = wfd.ftCreationTime, lastAccessTime = wfd.ftLastAccessTime, lastWriteTime = wfd.ftLastWriteTime, changeTime = null, fileSize = ((wfd.nFileSizeHigh.toULong()) shl 32) or (wfd.nFileSizeLow.toULong()), reparseTag = if (wfd.dwFileAttributes and FILE_ATTRIBUTE_REPARSE_POINT != 0u) {
                // reserved unless this is a reparse point
                wfd.dwReserved0
            } else {
                0u
            }, volumeSerialNumber = null, numberOfLinks = null, fileIndex = null)
    }
}

public open class FileType(
    public var isDirectory: Boolean,
    public var isSymlink: Boolean,
) {
    public open fun clone(): FileType = FileType(isDirectory, isSymlink)

    public open fun isDir(): Boolean = (!this.isSymlink && this.isDirectory).toUByte()

    public open fun isFile(): Boolean = (!this.isSymlink && !this.isDirectory).toUByte()

    public open fun isSymlink(): Boolean = this.isSymlink

    public open fun isSymlinkDir(): Boolean = (this.isSymlink && this.isDirectory).toUByte()

    public open fun isSymlinkFile(): Boolean = (this.isSymlink && !this.isDirectory).toUByte()

    public companion object {
        public fun typeLayout(): TypeLayout<FileType> =
            object : TypeLayout<FileType> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): FileType =
                    FileType(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + 1L),
                    )
                override fun writeNative(addr: Long, value: FileType) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.isDirectory)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.isSymlink)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileType =
                    FileType(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileType) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.isDirectory)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.isSymlink)
                }
            }

        public fun new(attributes: UInt, reparseTag: UInt): FileType {
            val isDirectory = attributes and FILE_ATTRIBUTE_DIRECTORY != 0u
            val isSymlink = run {
                val isReparsePoint = attributes and FILE_ATTRIBUTE_REPARSE_POINT != 0u
                val isReparseTagNameSurrogate = reparseTag and 0x20000000u != 0u
                isReparsePoint && isReparseTagNameSurrogate
            }
            return FileType(isDirectory = isDirectory, isSymlink = isSymlink)
        }
    }
}

public open class ReadDir(
    public var handle: FindNextFileHandle?,
    public var root: PathBuf,
    public var first: Win32FindDataw?,
) : Iterable<Result<DirEntry, Error>> {
    override fun iterator(): Iterator<Result<DirEntry, Error>> = object : AbstractIterator<Result<DirEntry, Error>>() {
        override fun computeNext() {
            when (val v = this@ReadDir.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun dir(): Result<Dir, Error> {
        val options = OpenOptions.new()
        options.read(true)
        // Note: use race-free handle-based approach instead.
        return Dir.open(this.root, options)
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        // This will only be called from std.fs.ReadDir, which will add a "ReadDir()" frame.
        // Thus the result will be e g ReadDir("C:\")'
        return run { f.append((this.root).toString()); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public open fun next(): Result<DirEntry, Error>? {
        val handle = this.handle ?: // This iterator was initialized with an `INVALID_HANDLE_VALUE` as its handle.
// Simply return `None` because this is only the case when `FindFirstFileExW` in
// the construction of this iterator returns `ERROR_FILE_NOT_FOUND` which means
// no matchhing files can be found.
return null
        when (val matchVal = this.first.also { this.first = null }) {
            null -> {}
            else -> {
                val first = matchVal
                when (val matchVal1 = DirEntry.new(this.root, first)) {
                    null -> {}
                    else -> {
                        val e = matchVal1
                        return Result.Ok(e)
                    }
                }
            }
        }
        run {
            val wfd = MaybeUninit.zeroed(UnresolvedTypeLayout<Result<DirEntry, Error>>()).assumeInit()
            while (true) {
                if (findNextFileW(handle!!.value, wfd) == 0) {
                    this.handle = null
                    when (val matchVal2 = getLastError()) {
                        WinError.noMoreFiles -> { return null }
                        else -> {
                            val code = matchVal2.code
                            return Result.Err(Error.fromRawOsError(code.toInt()))
                        }
                    }
                }
                when (val matchVal3 = DirEntry.new(this.root, wfd)) {
                    null -> {}
                    else -> {
                        val e1 = matchVal3
                        return Result.Ok(e1)
                    }
                }
            }
        }
    }
}

public open class FindNextFileHandle internal constructor(
    valueCtorArg: Handle,
) {
    public var value: Handle = valueCtorArg
        internal set

    public var field0: Handle
        get() = value
        set(v) {
            value = v
        }

    public open fun drop() {
        val r = findClose(this.value)
        check(r != 0)
    }

    public companion object {
        public operator fun invoke(
            value: Handle,
        ): FindNextFileHandle = FindNextFileHandle(
            valueCtorArg = value,
        )
    }
}

public open class DirEntry(
    public var root: PathBuf,
    public var data: Win32FindDataw,
) {
    public open fun path(): PathBuf = this.root.join(this.fileName())

    public open fun fileName(): OsString {
        val filename = truncateUtf16AtNul(this.data.cFileName)
        return OsString.fromWide(filename)
    }

    public open fun fileType(): Result<FileType, Error> = Result.Ok<FileType, Error>(FileType.new(this.data.dwFileAttributes, this.data.dwReserved0))

    public open fun metadata(): Result<FileAttr, Error> = Result.Ok<FileAttr, Error>(this.data.into())

    public companion object {
        public fun new(root: PathBuf, wfd: Win32FindDataw): DirEntry? {
            run {
                val matchVal = ((wfd.cFileName).sliceArray(0 until 3))
                when {
                    // check for '.' and '..'
                    matchVal.size >= 2 && matchVal[0] == 46 && matchVal[1] == 0 -> { return null }
                    matchVal.size >= 3 && matchVal[0] == 46 && matchVal[1] == 46 && matchVal[2] == 0 -> { return null }
                    else -> {}
                }
            }
            return DirEntry(root = root.clone(), data = wfd)
        }
    }
}

public open class OpenOptions(
    // generic
    public var read: Boolean,
    public var write: Boolean,
    public var append: Boolean,
    public var truncate: Boolean,
    public var create: Boolean,
    public var createNew: Boolean,
    // system-specific
    public var customFlags: UInt,
    public var accessMode: UInt?,
    public var attributes: UInt,
    public var shareMode: UInt,
    public var securityQosFlags: UInt,
    public var inheritHandle: Boolean,
    public var freezeLastAccessTime: Boolean,
    public var freezeLastWriteTime: Boolean,
) {
    public open fun clone(): OpenOptions = OpenOptions(read, write, append, truncate, create, createNew, customFlags, accessMode, attributes, shareMode, securityQosFlags, inheritHandle, freezeLastAccessTime, freezeLastWriteTime)

    public open fun read(read: Boolean) {
        this.read = read
    }

    public open fun write(write: Boolean) {
        this.write = write
    }

    public open fun append(append: Boolean) {
        this.append = append
    }

    public open fun truncate(truncate: Boolean) {
        this.truncate = truncate
    }

    public open fun create(create: Boolean) {
        this.create = create
    }

    public open fun createNew(createNew: Boolean) {
        this.createNew = createNew
    }

    public open fun customFlags(flags: UInt) {
        this.customFlags = flags
    }

    public open fun accessMode(accessMode: UInt) {
        this.accessMode = accessMode
    }

    public open fun shareMode(shareMode: UInt) {
        this.shareMode = shareMode
    }

    public open fun attributes(attrs: UInt) {
        this.attributes = attrs
    }

    public open fun securityQosFlags(flags: UInt) {
        // We have to set `SECURITY_SQOS_PRESENT` here, because one of the valid flags we can
        // receive is `SECURITY_ANONYMOUS = 0x0`, which we can't check for later on.
        this.securityQosFlags = flags or SECURITY_SQOS_PRESENT
    }

    public open fun inheritHandle(inherit: Boolean) {
        this.inheritHandle = inherit
    }

    public open fun freezeLastAccessTime(freeze: Boolean) {
        this.freezeLastAccessTime = freeze
    }

    public open fun freezeLastWriteTime(freeze: Boolean) {
        this.freezeLastWriteTime = freeze
    }

    public open fun getAccessMode(): Result<UInt, Error> {
        when (val matchVal = this.accessMode) {
            null -> {}
            else -> {
                val access = matchVal
                return Result.Ok<UInt, Error>(access)
            }
        }
        return run {
            val matchVal1 = Triple(this.read, this.write, this.append)
            when {
                matchVal1.second == false && matchVal1.third == false && (this.truncate || this.create || this.createNew) -> {
                    Result.Err<UInt, Error>(Error.new(ErrorKind.InvalidInput, "creating or truncating a file requires write or append access"))
                }
                matchVal1.third == true && this.truncate && !this.createNew -> Result.Err<UInt, Error>(Error.new(ErrorKind.InvalidInput, "append and truncate cannot both be enabled"))
                matchVal1.first == true && matchVal1.second == false && matchVal1.third == false -> Result.Ok<UInt, Error>(GENERIC_READ)
                matchVal1.first == false && matchVal1.second == true && matchVal1.third == false -> Result.Ok<UInt, Error>(GENERIC_WRITE)
                matchVal1.first == true && matchVal1.second == true && matchVal1.third == false -> Result.Ok<UInt, Error>(GENERIC_READ or GENERIC_WRITE)
                matchVal1.first == false && matchVal1.third == true -> Result.Ok<UInt, Error>(FILE_GENERIC_WRITE and FILE_WRITE_DATA.inv())
                matchVal1.first == true && matchVal1.third == true -> Result.Ok<UInt, Error>(GENERIC_READ or (FILE_GENERIC_WRITE and FILE_WRITE_DATA.inv()))
                else -> Result.Err<UInt, Error>(Error.new(ErrorKind.InvalidInput, "must specify at least one of read, write, or append access"))
            }
        }
    }

    public open fun getCmodeDisposition(): Result<Pair<UInt, UInt>, Error> =
        Result.Ok<Pair<UInt, UInt>, Error>(run {
            val matchVal = Triple(this.create, this.truncate, this.createNew)
            when {
                matchVal.first == false && matchVal.second == false && matchVal.third == false -> Pair(OPEN_EXISTING, FILE_OPEN)
                matchVal.first == true && matchVal.second == false && matchVal.third == false -> Pair(OPEN_ALWAYS, FILE_OPEN_IF)
                matchVal.first == false && matchVal.second == true && matchVal.third == false -> Pair(TRUNCATE_EXISTING, FILE_OVERWRITE)
                // `CREATE_ALWAYS` has weird semantics so we emulate it using
                // `OPEN_ALWAYS` and a manual truncation step. See #115745.
                matchVal.first == true && matchVal.second == true && matchVal.third == false -> Pair(OPEN_ALWAYS, FILE_OVERWRITE_IF)
                else -> Pair(CREATE_NEW, FILE_CREATE)
            }
        })

    public open fun getCreationMode(): Result<UInt, Error> = this.getCmodeDisposition().map({ (mode, _) -> mode })

    public open fun getDisposition(): Result<UInt, Error> = this.getCmodeDisposition().map({ (_, mode) -> mode })

    public open fun getFlagsAndAttributes(): UInt =
        this.customFlags or this.attributes or this.securityQosFlags or
            if (this.createNew) {
                FILE_FLAG_OPEN_REPARSE_POINT
            } else {
                0u
            }

    public companion object {
        public fun new(): OpenOptions = OpenOptions(read = false, write = false, append = false, truncate = false, create = false, createNew = false, customFlags = 0u, accessMode = null, shareMode = FILE_SHARE_READ or FILE_SHARE_WRITE or FILE_SHARE_DELETE, attributes = 0u, securityQosFlags = 0u, inheritHandle = false, freezeLastAccessTime = false, freezeLastWriteTime = false)
    }
}

public open class FilePermissions(
    public var attrs: UInt,
) : FromInner<UInt, FilePermissions> {
    public open fun clone(): FilePermissions = FilePermissions(attrs)

    public open fun readonly(): Boolean = (this.attrs and FILE_ATTRIBUTE_READONLY != 0u).toUByte()

    public open fun setReadonly(readonly: Boolean) {
        if (readonly) {
            this.attrs = this.attrs or FILE_ATTRIBUTE_READONLY
        } else {
            this.attrs = this.attrs and FILE_ATTRIBUTE_READONLY.inv()
        }
    }

    public open fun fileAttributes(): UInt = this.attrs

    public companion object {
        public fun typeLayout(): TypeLayout<FilePermissions> =
            object : TypeLayout<FilePermissions> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FilePermissions =
                    FilePermissions(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FilePermissions) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.attrs)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FilePermissions =
                    FilePermissions(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FilePermissions) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.attrs)
                }
            }

        public fun fromInner(attrs: UInt): FilePermissions = FilePermissions(attrs = attrs)
    }
}

public open class FileTimes(
    public var accessed: Filetime?,
    public var modified: Filetime?,
    public var created: Filetime?,
) {
    public open fun clone(): FileTimes = FileTimes(accessed, modified, created)

    public open fun setAccessed(t: SystemTime) {
        this.accessed = t.intoInner()
    }

    public open fun setModified(t: SystemTime) {
        this.modified = t.intoInner()
    }

    public open fun setCreated(t: SystemTime) {
        this.created = t.intoInner()
    }
}

public fun Filetime.fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
    val time = ((this.dwHighDateTime.toULong()) shl 32) or this.dwLowDateTime.toULong()
    return run { DebugTuple(f, "FILETIME").field(time).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
}

public open class DirBuilder {
    public open fun mkdir(p: Path): Result<Unit, Error> {
        val p1 = when (val tryResult = maybeVerbatim(p)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
        when (val tryResult = cvt(createDirectoryW(p1.asPtr(), MutPtr(0L)))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
        return Result.Ok<Unit, Error>(Unit)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<DirBuilder> =
            object : TypeLayout<DirBuilder> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): DirBuilder =
                    DirBuilder()
                override fun writeNative(addr: Long, value: DirBuilder) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): DirBuilder =
                    DirBuilder()
                override fun writeBytes(buf: ByteArray, offset: Int, value: DirBuilder) {
                }
            }

        public fun new(): DirBuilder = DirBuilder()
    }
}

/**
 * A buffer for holding directory entries.
 */
public open class DirBuff internal constructor(
    bufferCtorArg: Align8<List<MaybeUninit<UByte>>>,
) : AsRef<List<MaybeUninit<UByte>>> {
    public var buffer: Align8<List<MaybeUninit<UByte>>> = bufferCtorArg
        internal set

    public open fun capacity(): Int = this.buffer.first.len()

    public open fun asMutPtr(): MutPtr = this.buffer.first.asMutPtr().cast()

    /**
     * Returns a `DirBuffIter`.
     */
    public open fun iter(): DirBuffIter = DirBuffIter.new(this)

    public open fun asRef(): List<MaybeUninit<UByte>> = this.buffer.first

    public companion object {
        public operator fun invoke(
            buffer: Align8<List<MaybeUninit<UByte>>>,
        ): DirBuff = DirBuff(
            bufferCtorArg = buffer,
        )

        public val BUFFER_SIZE: Int = 1024

        public fun new(): DirBuff = DirBuff(buffer = Box.newUninit().assumeInit())
    }
}

/**
 * An iterator over entries stored in a `DirBuff`.
 *
 * Currently only returns file names (UTF-16 encoded).
 */
public open class DirBuffIter internal constructor(
    bufferCtorArg: List<MaybeUninit<UByte>>?,
    public var cursor: Int,
) : Iterable<Pair<List<UShort>, Boolean>> {
    public var buffer: List<MaybeUninit<UByte>>? = bufferCtorArg
        internal set

    override fun iterator(): Iterator<Pair<List<UShort>, Boolean>> = object : AbstractIterator<Pair<List<UShort>, Boolean>>() {
        override fun computeNext() {
            when (val v = this@DirBuffIter.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Pair<List<UShort>, Boolean>? {
        // Skip `.` and `..` pseudo entries.
        val DOT: UShort = 46u.toUShort()
        val buffer = (((this.buffer ?: return null)).subList(this.cursor, ((this.buffer ?: return null)).size))
        // Get the name and next entry from the buffer.
        // SAFETY:
        // - The buffer contains a `FILE_ID_BOTH_DIR_INFO` struct but the last
        // field (the file name) is unsized. So an offset has to be used to
        // get the file name slice.
        // - The OS has guaranteed initialization of the fields of
        // `FILE_ID_BOTH_DIR_INFO` and the trailing filename (for at least
        // `FileNameLength` bytes)
        val (name1, isDirectory1, nextEntry1) =
            run {
                val info = ConstPtr((buffer.asPtr()).rawAddress)
                // While this is guaranteed to be aligned in documentation for
                // https://docs.microsoft.com/en-us/windows/win32/api/winbase/ns-winbase-fileIdBothDirInfo
                // it does not seem that reality is so kind, and assuming this
                // caused crashes in some cases (https://github.com/rust-lang/rust/issues/104530).
                // Presumably, this can be blamed on buggy filesystem drivers, but who knows.
                val nextEntry = (PrimitiveLayouts.U32).readNative(((addrOf((UnresolvedTypeLayout<FileIdBothDirInfo>()).readNative((info).rawAddress).nextEntryOffset))).rawAddress).toInt()
                val length = (PrimitiveLayouts.U32).readNative(((addrOf((UnresolvedTypeLayout<FileIdBothDirInfo>()).readNative((info).rawAddress).fileNameLength))).rawAddress).toInt()
                val attrs = (PrimitiveLayouts.U32).readNative(((addrOf((UnresolvedTypeLayout<FileIdBothDirInfo>()).readNative((info).rawAddress).fileAttributes))).rawAddress)
                val name = fromMaybeUnaligned(ConstPtr(((addrOf((UnresolvedTypeLayout<FileIdBothDirInfo>()).readNative((info).rawAddress).fileName))).rawAddress), length / sizeOf<UShort>(typeLayoutT = PrimitiveLayouts.U16))
                val isDirectory = (attrs and FILE_ATTRIBUTE_DIRECTORY) != 0
                Triple(name, isDirectory, nextEntry)
            }
        if (nextEntry1 == 0) {
            this.buffer = null
        } else {
            this.cursor += nextEntry1
        }
        return when {
            name1.size == 1 && name1[0] == DOT -> this.next()
            name1.size == 2 && name1[0] == DOT && name1[1] == DOT -> this.next()
            else -> Pair(name1, isDirectory1)
        }
    }

    public companion object {
        public operator fun invoke(
            buffer: List<MaybeUninit<UByte>>?,
            cursor: Int,
        ): DirBuffIter = DirBuffIter(
            bufferCtorArg = buffer,
            cursor = cursor,
        )

        public fun new(buffer: DirBuff): DirBuffIter = DirBuffIter(buffer = buffer.asRef(), cursor = 0)
    }
}

public fun fromMaybeUnaligned(p: ConstPtr, len: Int): List<UShort> =
    if (p.isAligned()) {
        linearSliceView(p, len.toInt(), PrimitiveLayouts.U16)
    } else {
        (0 until len).map({ i -> (PrimitiveLayouts.U16).readNative((ConstPtr((p).rawAddress + ((i).toLong() * (PrimitiveLayouts.U16).strideBytes))).rawAddress) }).toMutableList()
    }

public fun debugPathHandle(handle: BorrowedHandle, f: StringBuilder, name: String): DebugStruct {
    // Note(#24570): add more info here (e.g., mode)
    val b = DebugStruct(f, name)
    b.field("handle", handle.asRawHandle())
    when (val matchVal = getPath(handle)) {
        is Result.Ok<*, *> -> {
            val path = matchVal.value
            b.field("path", path)
        }
        else -> {}
    }
    return b
}

public fun toU64(ft: Filetime): ULong = (ft.dwLowDateTime.toULong()) or ((ft.dwHighDateTime.toULong()) shl 32)

public fun readdir(p: Path): Result<ReadDir, Error> {
    // We push a `*` to the end of the path which cause the empty path to be
    // treated as the current directory. So, for consistency with other platforms,
    // we explicitly error on the empty path.
    if (p.asOsStr().isEmpty()) {
        // Return an error code consistent with other ways of opening files.
        // E.g. fs.metadata or File.open.
        return Result.Err<ReadDir, Error>(Error.fromRawOsError(ERROR_PATH_NOT_FOUND.toInt()))
    }
    val root = p.toPathBuf()
    val star = p.join("*")
    val path = when (val tryResult = maybeVerbatim(star)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<ReadDir, Error>(tryResult.error) }
    return run {
        val localStorageL1257C8 = AddressableLocal(Win32FindDataw(), UnresolvedTypeLayout<Win32FindDataw>())
        val wfd by localStorageL1257C8
        // this is like FindFirstFileW (see https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-findfirstfileexw),
        // but with FindExInfoBasic it should skip filling WIN32_FIND_DATAW.cAlternateFileName
        // (see https://learn.microsoft.com/en-us/windows/win32/api/minwinbase/ns-minwinbase-win32FindDataw)
        // (which will be always null string value and currently unused) and should be faster.
        //
        // We can pass FIND_FIRST_EX_LARGE_FETCH to dwAdditionalFlags to speed up things more,
        // but as we don't know user's use profile of this function, lets be conservative.
        val findHandle = findFirstFileExW(path.asPtr(), FIND_EX_INFO_BASIC, MutPtr(localStorageL1257C8.asMutPtr().rawAddress), FIND_EX_SEARCH_NAME_MATCH, ConstPtr(0L), 0u)
        if (findHandle != INVALID_HANDLE_VALUE) {
            Result.Ok<ReadDir, Error>(ReadDir(handle = FindNextFileHandle(findHandle), root = root, first = wfd))
        } else {
            // The status `ERROR_FILE_NOT_FOUND` is returned by the `FindFirstFileExW` function
            // if no matching files can be found, but not necessarily that the path to find the
            // files in does not exist.
            //
            // Hence, a check for whether the path to search in exists is added when the last
            // os error returned by Windows is `ERROR_FILE_NOT_FOUND` to handle this scenario.
            // If that is the case, an empty `ReadDir` iterator is returned as it returns `None`
            // in the initial `.next()` invocation because `ERROR_NO_MORE_FILES` would have been
            // returned by the `FindNextFileW` function.
            //
            // See issue #120040: https://github.com/rust-lang/rust/issues/120040.
            val lastError = getLastError()
            if (lastError == WinError.FILE_NOT_FOUND) {
                return Result.Ok<ReadDir, Error>(ReadDir(handle = null, root = root, first = null))
            }
            // Just return the error constructed from the raw OS error if the above is not the case.
            //
            // Note: `ERROR_PATH_NOT_FOUND` would have been returned by the `FindFirstFileExW` function
            // when the path to search in does not exist in the first place.
            Result.Err<ReadDir, Error>(Error.fromRawOsError(lastError.code))
        }
    }
}

public fun unlink(path: WCStr): Result<Unit, Error> {
    return if (deleteFileW(path.asPtr()) == 0) {
        val err = getLastError()
        // if `DeleteFileW` fails with ERROR_ACCESS_DENIED then try to remove
        // the file while ignoring the readonly attribute.
        // This is accomplished by calling the `posixDelete` function on an open file handle.
        if (err == WinError.ACCESS_DENIED) {
            val opts = OpenOptions.new()
            opts.accessMode(DELETE)
            opts.customFlags(FILE_FLAG_OPEN_REPARSE_POINT)
            when (val matchVal = File.openNative(path, opts)) {
                is Result.Ok<*, *> -> {
                    val f = matchVal.value
                    if (f.posixDelete().isOk()) {
                        return Result.Ok<Unit, Error>(Unit)
                    }
                }
                else -> {}
            }
        }
        // return the original error if any of the above fails.
        Result.Err<Unit, Error>(Error.fromRawOsError(err.code))
    } else {
        Result.Ok<Unit, Error>(Unit)
    }
}

public fun renameInner(old: WCStr, new: WCStr, moveFlags: UInt, renameFlags: UInt): Result<Unit, Error> {
    if (moveFileExW(old.asPtr(), new.asPtr(), moveFlags) == 0) {
        val err = getLastError()
        // if `MoveFileExW` fails with ERROR_ACCESS_DENIED then try to move
        // the file while ignoring the readonly attribute.
        // This is accomplished by calling `SetFileInformationByHandle` with `FileRenameInfoEx`.
        if (err == WinError.ACCESS_DENIED) {
            val opts = OpenOptions.new()
            opts.accessMode(DELETE)
            opts.customFlags(FILE_FLAG_OPEN_REPARSE_POINT or FILE_FLAG_BACKUP_SEMANTICS)
            val f = when (val tryResult = File.openNative(old, opts)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(err).ioResult() }
            // Calculate the layout of the `FILE_RENAME_INFO` we pass to `SetFileInformation`
            // This is a dynamically sized struct so we need to get the position of the last field to calculate the actual size.
            val newLenWithoutNulInBytes: Result<UInt> = when (val tryResult1 = ((new.countBytes() - 1) * 2).tryInto()) { is Result.Ok<*, *> -> tryResult1.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(err).ioResult() }
            val offset: UInt = 0u.tryInto().unwrap()
            val structSize = offset + newLenWithoutNulInBytes + 2u
            val layout = Layout.fromSizeAlign(structSize.toInt(), alignOf<FileRenameInfo>(typeLayoutT = UnresolvedTypeLayout<FileRenameInfo>())).unwrap()
            val fileRenameInfo: Any?
            // SAFETY: We allocate enough memory for a full FILE_RENAME_INFO struct and a filename.
            run {
                fileRenameInfo = alloc(layout).cast()
                if (fileRenameInfo.isNull()) {
                    return Result.Err<Unit, Error>(ErrorKind.OutOfMemory.into())
                }
                (addrOfMut(fileRenameInfo.anonymous)).write(FileRenameInfo0(flags = renameFlags))
                (addrOfMut(fileRenameInfo.rootDirectory)).write(MutPtr(0L))
                // Don't include the NULL in the size
                (addrOfMut(fileRenameInfo.fileNameLength)).write(newLenWithoutNulInBytes)
                kmCopyNonoverlapping((new.asPtr()).rawAddress, ((addrOfMut(fileRenameInfo.fileName)).cast()).rawAddress, (new.countBytes()).toLong() * (UnresolvedTypeLayout<WCStr>()).sizeBytes)
            }
            val result = io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.setFileInformationByHandle(f.asRawHandle(), FILE_RENAME_INFO_EX, fileRenameInfo.cast(), structSize)
            dealloc(fileRenameInfo.cast(), layout)
            if (result == 0) {
                if (getLastError() == WinError.DIR_NOT_EMPTY) {
                    return Result.Err<Unit, Error>(WinError.DIR_NOT_EMPTY).ioResult()
                } else {
                    return Result.Err<Unit, Error>(err).ioResult()
                }
            }
        } else {
            return Result.Err<Unit, Error>(err).ioResult()
        }
    }
    return Result.Ok<Unit, Error>(Unit)
}

public fun rename(old: WCStr, new: WCStr): Result<Unit, Error> = renameInner(old, new, MOVEFILE_REPLACE_EXISTING, FILE_RENAME_FLAG_REPLACE_IF_EXISTS or FILE_RENAME_FLAG_POSIX_SEMANTICS)

public fun renameNoreplace(old: WCStr, new: WCStr): Result<Unit, Error> {
    // Without `MOVEFILE_REPLACE_EXISTING`, `MoveFileExW` fails if the destination
    // exists.
    return renameInner(old, new, 0u, FILE_RENAME_FLAG_POSIX_SEMANTICS)
}

public fun rmdir(p: WCStr): Result<Unit, Error> {
    when (val tryResult = cvt(removeDirectoryW(p.asPtr()))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
    return Result.Ok<Unit, Error>(Unit)
}

public fun removeDirAll(path: WCStr): Result<Unit, Error> {
    // Open a file or directory without following symlinks.
    val opts = OpenOptions.new()
    opts.accessMode(FILE_LIST_DIRECTORY)
    // `FILE_FLAG_BACKUP_SEMANTICS` allows opening directories.
    // `FILE_FLAG_OPEN_REPARSE_POINT` opens a link instead of its target.
    opts.customFlags(FILE_FLAG_BACKUP_SEMANTICS or FILE_FLAG_OPEN_REPARSE_POINT)
    val file = when (val tryResult = File.openNative(path, opts)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
    // Test if the file is not a directory or a symlink to a directory.
    if ((when (val tryResult = file.basicInfo()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }.fileAttributes and FILE_ATTRIBUTE_DIRECTORY) == 0u) {
        return Result.Err<Unit, Error>(Error.fromRawOsError(ERROR_DIRECTORY))
    }
    // Remove the directory and all its contents.
    return removeDirAllIterative(file).ioResult()
}

public fun readlink(path: WCStr): Result<PathBuf, Error> {
    // Open the link with no access mode, instead of generic read.
    // By default FILE_LIST_DIRECTORY is denied for the junction "C:\Documents and Settings", so
    // this is needed for a common case.
    val opts = OpenOptions.new()
    opts.accessMode(0)
    opts.customFlags(FILE_FLAG_OPEN_REPARSE_POINT or FILE_FLAG_BACKUP_SEMANTICS)
    val file = when (val tryResult = File.openNative(path, opts)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<PathBuf, Error>(tryResult.error) }
    return file.readlink()
}

public fun symlink(original: Path, link: Path): Result<Unit, Error> = symlinkInner(original, link, false)

public fun symlinkInner(original: Path, link: Path, dir: Boolean): Result<Unit, Error> {
    val original1 = when (val tryResult = toU16s(original)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
    val link1 = when (val tryResult = maybeVerbatim(link)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
    val flags =
        if (dir) {
            SYMBOLIC_LINK_FLAG_DIRECTORY
        } else {
            0
        }
    // Formerly, symlink creation required the SeCreateSymbolicLink privilege. For the Windows 10
    // Creators Update, Microsoft loosened this to allow unprivileged symlink creation if the
    // computer is in Developer Mode, but SYMBOLIC_LINK_FLAG_ALLOW_UNPRIVILEGED_CREATE must be
    // added to dwFlags to opt into this behavior.
    val result = cvt(createSymbolicLinkW(link1.asPtr(), original1.asPtr(), flags or SYMBOLIC_LINK_FLAG_ALLOW_UNPRIVILEGED_CREATE).toInt())
    if (result is Result.Err<*, *>) {
        val err = result.error
        if (err.rawOsError() == ERROR_INVALID_PARAMETER.toInt()) {
            // Older Windows objects to SYMBOLIC_LINK_FLAG_ALLOW_UNPRIVILEGED_CREATE,
            // so if we encounter ERROR_INVALID_PARAMETER, retry without that flag.
            when (val tryResult = cvt(createSymbolicLinkW(link1.asPtr(), original1.asPtr(), flags).toInt())) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
        } else {
            return Result.Err<Unit, Error>(err)
        }
    }
    return Result.Ok<Unit, Error>(Unit)
}

public fun link(original: WCStr, link: WCStr): Result<Unit, Error> {
    when (val tryResult = cvt(createHardLinkW(link.asPtr(), original.asPtr(), MutPtr(0L)))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
    return Result.Ok<Unit, Error>(Unit)
}

public fun stat(path: WCStr): Result<FileAttr, Error> {
    return run {
        val matchVal = metadata(path, ReparsePoint.Follow)
        when {
            matchVal is Result.Err<*, *> && matchVal.error.rawOsError() == ERROR_CANT_ACCESS_FILE.toInt() -> {
                val err = matchVal.error
                // Fallback to opening reparse points when following fails. Needed for UNIX domain
                // sockets. See <https://github.com/rust-lang/rust/issues/109106>.
                when (val matchVal1 = metadata(path, ReparsePoint.Open)) {
                    is Result.Ok<*, *> -> {
                        val attrs = matchVal1.value
                        if (!attrs.fileType().isSymlink()) {
                            return Result.Ok<FileAttr, Error>(attrs)
                        }
                    }
                    else -> {}
                }
                Result.Err<FileAttr, Error>(err)
            }
            else -> {
                val result = matchVal
                result
            }
        }
    }
}

public fun lstat(path: WCStr): Result<FileAttr, Error> = metadata(path, ReparsePoint.Open)

public sealed class ReparsePoint(
    public val value: UInt,
) {
    public data object Follow : ReparsePoint(0u)
    public data object Open : ReparsePoint(FILE_FLAG_OPEN_REPARSE_POINT)

    public fun rustEquals(other: ReparsePoint): Boolean = this == other

    public open fun asFlag(): UInt = this.toUInt()
}

public fun metadata(path: WCStr, reparse: ReparsePoint): Result<FileAttr, Error> {
    val opts = OpenOptions.new()
    // No read or write permissions are necessary
    opts.accessMode(0)
    opts.customFlags(FILE_FLAG_BACKUP_SEMANTICS or reparse.asFlag())
    // Attempt to open the file normally.
    // If that fails with `ERROR_SHARING_VIOLATION` then retry using `FindFirstFileExW`.
    // If the fallback fails for any reason we return the original error.
    return run {
        val matchVal = File.openNative(path, opts)
        when {
            matchVal is Result.Ok<*, *> -> {
                val file = matchVal.value
                file.fileAttr()
            }
            matchVal is Result.Err<*, *> && listOf(ERROR_SHARING_VIOLATION, ERROR_ACCESS_DENIED).contains(matchVal.error.rawOsError()) -> {
                val e = matchVal.error
                // `ERROR_ACCESS_DENIED` is returned when the user doesn't have permission for the resource.
                // One such example is `System Volume Information` as default but can be created as well
                // `ERROR_SHARING_VIOLATION` will almost never be returned.
                // Usually if a file is locked you can still read some metadata.
                // However, there are special system files, such as
                // `C:\hiberfil.sys`, that are locked in a way that denies even that.
                run {
                    // `FindFirstFileExW` accepts wildcard file names.
                    // Fortunately wildcards are not valid file names and
                    // `ERROR_SHARING_VIOLATION` means the file exists (but is locked)
                    // therefore it's safe to assume the file name given does not
                    // include wildcards.
                    val localStorageL1550C16 = AddressableLocal(Win32FindDataw(), UnresolvedTypeLayout<Win32FindDataw>())
                    val wfd by localStorageL1550C16
                    val handle = findFirstFileExW(path.asPtr(), FIND_EX_INFO_BASIC, MutPtr(localStorageL1550C16.asMutPtr().rawAddress), FIND_EX_SEARCH_NAME_MATCH, ConstPtr(0L), 0u)
                    if (handle == INVALID_HANDLE_VALUE) {
                        // This can fail if the user does not have read access to the
                        // directory.
                        Result.Err<FileAttr, Error>(e)
                    } else {
                        // We no longer need the find handle.
                        findClose(handle)
                        // `FindFirstFileExW` reads the cached file information from the
                        // directory. The downside is that this metadata may be outdated.
                        val attrs = FileAttr.from(wfd)
                        if (reparse == ReparsePoint.Follow && attrs.fileType().isSymlink()) {
                            Result.Err<FileAttr, Error>(e)
                        } else {
                            Result.Ok<FileAttr, Error>(attrs)
                        }
                    }
                }
            }
            else -> {
                val e1 = (matchVal as Result.Err<*, *>).error
                Result.Err<FileAttr, Error>(e1)
            }
        }
    }
}

public fun setPerm(p: WCStr, perm: FilePermissions): Result<Unit, Error> {
    return run {
        when (val tryResult = cvt(setFileAttributesW(p.asPtr(), perm.attrs))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
        Result.Ok<Unit, Error>(Unit)
    }
}

public fun setPermNofollow(p: WCStr, perm: FilePermissions): Result<Unit, Error> {
    val opts = OpenOptions.new()
    opts.accessMode(FILE_WRITE_ATTRIBUTES)
    // `FILE_FLAG_OPEN_REPARSE_POINT` for noFollow behavior
    opts.customFlags(FILE_FLAG_BACKUP_SEMANTICS or FILE_FLAG_OPEN_REPARSE_POINT)
    val file = when (val tryResult = File.openNative(p, opts)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
    return file.setPermissions(perm)
}

public fun setTimes(p: WCStr, times: FileTimes): Result<Unit, Error> {
    val opts = OpenOptions.new()
    opts.accessMode(FILE_WRITE_ATTRIBUTES)
    opts.customFlags(FILE_FLAG_BACKUP_SEMANTICS)
    val file = when (val tryResult = File.openNative(p, opts)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
    return file.setTimes(times)
}

public fun setTimesNofollow(p: WCStr, times: FileTimes): Result<Unit, Error> {
    val opts = OpenOptions.new()
    opts.accessMode(FILE_WRITE_ATTRIBUTES)
    // `FILE_FLAG_OPEN_REPARSE_POINT` for noFollow behavior
    opts.customFlags(FILE_FLAG_BACKUP_SEMANTICS or FILE_FLAG_OPEN_REPARSE_POINT)
    val file = when (val tryResult = File.openNative(p, opts)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
    return file.setTimes(times)
}

public fun getPath(f: AsRawHandle): Result<PathBuf, Error> {
    val h = f.asRawHandle()
    // If getting the canonical path fails with ERROR_INVALID_FUNCTION
    // then it's likely it failed to resolve the path's drive.
    // In that case, use the fallback method to resolve it.
    val invalidFunction = ERROR_INVALID_FUNCTION.toInt()
    return run {
        val matchVal = getPathCanonical(h)
        when {
            matchVal is Result.Err<*, *> && matchVal.error.rawOsError() == invalidFunction -> {
                val e = matchVal.error
                (getPathFallback(h)?.let { Result.Ok<PathBuf, Error>(it) } ?: Result.Err<PathBuf, Error>(e))
            }
            else -> {
                val result = matchVal
                result
            }
        }
    }
}

public fun getPathCanonical(handle: Handle): Result<PathBuf, Error> = fillUtf16Buf({ buf, sz -> getFinalPathNameByHandleW(handle, buf, sz, VOLUME_NAME_DOS) }, { buf -> PathBuf.from(OsString.fromWide(buf)) })

/**
 * Fallback in case `getPathCanonical` fails.
 *
 * `getPathCanonical` can fail if the Win32 drive name cannot be resolved.
 * This can happen with certain third party drivers that don't integrate
 * with the mount manager.
 *
 * Instead we manually do the same job by getting the NT path
 * and then finding the first drive letter that points to a prefix of
 * that path. From there we can construct a Win32 path.
 *
 * It's implemented by first getting the NT path, which should always succeed.
 * Then we use [GetLogicalDrives] to get a bit array of win32 drive letters
 * from `A` to `Z`. If the corresponding bit is set then it means that drive exists.
 * E.g. bit 2 being set means there's a `C:` drive.
 *
 * Then for each drive we use [QueryDosDeviceW] to see the NT path that drive resolves to.
 * If that path is a prefix to the path we got initially then we treat that as the canonical drive letter.
 * So in the unlikely even two drives point to the same device, the lowest one is considered canonical.
 *
 * [GetLogicalDrives]: https://learn.microsoft.com/windows/win32/api/fileapi/nf-fileapi-getlogicaldrives
 * [QueryDosDeviceW]: https://learn.microsoft.com/windows/win32/api/fileapi/nf-fileapi-querydosdevicew
 */
public fun getPathFallback(handle: Handle): PathBuf? {
    return fillUtf16Buf({ buf, sz -> getFinalPathNameByHandleW(handle, buf, sz, VOLUME_NAME_NT) }, closure@{ ntPath ->
        var buf = UShortArray(MAX_PATH.toInt()) { 0u.toUShort() }
        for (letter in getLogicalDrives()) {
            val deviceName = ushortArrayOf(letter.toUShort(), 58u.toUShort(), 0u)
            // SAFETY: `deviceName` is a null terminated u16 string
            when (val matchVal = queryDosDevice(deviceName, buf)) {
                null -> {}
                else -> {
                    val drivePath = matchVal
                    when (val matchVal1 = ntPath.takeIf { it.startsWith(drivePath) }?.removePrefix(drivePath)) {
                        null -> {}
                        else -> {
                            val ntPath1 = matchVal1
                            // Reserve approximately enough space for the drive + path.
                            val path = ArrayList("\\\\?\\C:".len() + ntPath1.len())
                            // Create a verbatim drive root (e.g. \\?\D:)
                            val verbatimRoot = "\\\\?\\C:".encodeToByteArray()
                            verbatimRoot[4] = letter
                            path.addAll(verbatimRoot)
                            path.extend(OsString.fromWide(ntPath1).intoEncodedBytes())
                            // SAFETY: All characters are either in the ASCII range (the prefix)
                            // or else came from an OsString.
                            run {
                                return@closure OsString.fromEncodedBytesUnchecked(path).into()
                            }
                        }
                    }
                }
            }
        }
        null
    }).ok()
}

public fun canonicalize(p: WCStr): Result<PathBuf, Error> {
    val opts = OpenOptions.new()
    // No read or write permissions are necessary
    opts.accessMode(0)
    // This flag is so we can open directories too
    opts.customFlags(FILE_FLAG_BACKUP_SEMANTICS)
    val f = when (val tryResult = File.openNative(p, opts)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<PathBuf, Error>(tryResult.error) }
    return getPath(f.handle)
}

public fun copy(from: WCStr, to: WCStr): Result<ULong, Error> {
    fun callback(totalFileSize: Long, totalBytesTransferred: Long, streamSize: Long, streamBytesTransferred: Long, dwStreamNumber: UInt, dwCallbackReason: UInt, hSourceFile: Handle, hDestinationFile: Handle, lpData: ConstPtr): UInt =
        run {
            if (dwStreamNumber == 1u) {
                (PrimitiveLayouts.I64).writeNative(((MutPtr(lpData.rawAddress, lpData.rawMeta))).rawAddress, streamBytesTransferred)
            }
            PROGRESS_CONTINUE
        }
    val localStorageL1717C4 = AddressableLocal(0L, PrimitiveLayouts.I64)
    val size by localStorageL1717C4
    when (val tryResult = cvt(copyFileExW(from.asPtr(), to.asPtr(), ::callback, (localStorageL1717C4.asMutPtr()), MutPtr(0L), 0u))) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<ULong, Error>(tryResult.error) }
    return Result.Ok<ULong, Error>(size.toULong())
}

public fun junctionPoint(original: Path, link: Path): Result<Unit, Error> {
    // Defined inline so we don't have to mess about with variable length buffer.
    open class MountPointBuffer(
        public var reparseTag: UInt,
        public var reparseDataLength: UShort,
        public var reserved: UShort,
        public var substituteNameOffset: UShort,
        public var substituteNameLength: UShort,
        public var printNameOffset: UShort,
        public var printNameLength: UShort,
        // `MAXIMUM_REPARSE_DATA_BUFFER_SIZE` is a size in bytes, but this is a
        // buffer of `UShort`s, so it holds half as many elements.
        public var pathBuffer: List<MaybeUninit<UShort>>,
    )
    // Create and open a new directory in one go.
    val opts = OpenOptions.new()
    opts.createNew(true)
    opts.write(true)
    opts.customFlags(FILE_FLAG_BACKUP_SEMANTICS or FILE_FLAG_POSIX_SEMANTICS)
    opts.attributes(FILE_ATTRIBUTE_DIRECTORY)
    val d = when (val tryResult = File.open(link, opts)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }
    // We need to get an absolute, NT-style path.
    val pathBytes = original.asOsStr().asEncodedBytes()
    val absPath1: List<UShort> =
        if (pathBytes.startsWith("\\\\?\\".encodeToByteArray()) || pathBytes.startsWith("\\??\\".encodeToByteArray())) {
            // It's already an absolute path, we just need to convert the prefix to `\??\`
            val bytes = OsStr.fromEncodedBytesUnchecked(pathBytes[4 until pathBytes.size])
            "\\??\\".encodeUtf16().chain(bytes.encodeWide()).toMutableList()
        } else {
            // Get an absolute path and then convert the prefix to `\??\`
            val absPath = when (val tryResult = absolute(original)) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.error) }.intoOsString().intoEncodedBytes()
            if (!absPath.isEmpty() && absPath[1 until absPath.size].startsWith(":\\".encodeToByteArray())) {
                val bytes1 = OsStr.fromEncodedBytesUnchecked(absPath)
                "\\??\\".encodeUtf16().chain(bytes1.encodeWide()).toMutableList()
            } else if (absPath.startsWith("\\\\.\\".encodeToByteArray())) {
                val bytes2 = OsStr.fromEncodedBytesUnchecked(absPath[4 until absPath.size])
                "\\??\\".encodeUtf16().chain(bytes2.encodeWide()).toMutableList()
            } else if (absPath.startsWith("\\\\".encodeToByteArray())) {
                val bytes3 = OsStr.fromEncodedBytesUnchecked(absPath[2 until absPath.size])
                "\\??\\UNC\\".encodeUtf16().chain(bytes3.encodeWide()).toMutableList()
            } else {
                return Result.Err<Unit, Error>(constError(ErrorKind.InvalidInput, "path is not valid"))
            }
        }
    val localStorageL1778C4 = AddressableLocal(MountPointBuffer(reparseTag = IO_REPARSE_TAG_MOUNT_POINT, reparseDataLength = 0u, reserved = 0u, substituteNameOffset = 0u, substituteNameLength = (absPath1.size * 2).toUShort(), printNameOffset = ((absPath1.size + 1) * 2).toUShort(), printNameLength = 0u, pathBuffer = List(MAXIMUM_REPARSE_DATA_BUFFER_SIZE.toInt() / 2) { MaybeUninit.uninit(PrimitiveLayouts.U16) }), UnresolvedTypeLayout<MountPointBuffer>())
    val header by localStorageL1778C4
    // A mount point reparse point requires both the substitute name and the
    // (empty) print name to be null terminated, even though their lengths are
    // explicit. Bounds-check and copy in a single step so an over-long path
    // fails cleanly instead of overflowing the buffer.
    val pathBuffer = header.pathBuffer.getMut(0 until absPath1.size + 2) ?: return Result.Err<Unit, Error>(constError(ErrorKind.InvalidInput, "`original` path is too long"))
    val (substituteName, terminators) = pathBuffer.splitAtMut(absPath1.size)
    substituteName.writeCopyOfSlice(absPath1)
    terminators.writeCopyOfSlice(listOf(0, 0))
    // Total size of the structure: the fixed header fields, the path, and the
    // two null terminators.
    val totalLen = 0 + (absPath1.size + 2) * 2
    // `ReparseDataLength` counts only the bytes after the 8-byte common header
    // (`ReparseTag`, `ReparseDataLength`, `Reserved`), i.e.
    // `SubstituteNameLength + PrintNameLength + 12`.
    header.reparseDataLength = (totalLen - 0).toUShort()
    return run {
        val localStorageL1808C8 = AddressableLocal(0, PrimitiveLayouts.I32)
        cvt(deviceIoControl(d.asRawHandle(), FSCTL_SET_REPARSE_POINT, ConstPtr(((localStorageL1778C4.asConstPtr())).rawAddress), totalLen.toUInt(), MutPtr(0L), 0u, RustMutableReference(localStorageL1808C8.asMutPtr(), localStorageL1808C8.layout), MutPtr(0L))).map({ drop(it) })
    }
}

// Try to see if a file exists but, unlike `exists`, report I/O errors.
public fun exists(path: WCStr): Result<Boolean, Error> {
    // Open the file to ensure any symlinks are followed to their target.
    val opts = OpenOptions.new()
    // No read, write, etc access rights are needed.
    opts.accessMode(0)
    // Backup semantics enables opening directories as well as files.
    opts.customFlags(FILE_FLAG_BACKUP_SEMANTICS)
    return run {
        val matchVal = File.openNative(path, opts)
        when (matchVal) { is Result.Ok<*, *> -> {
            Result.Ok<Boolean, Error>(true)
        }; is Result.Err<*, *> -> {
            val e = matchVal.error
            run {
                val matchVal1 = e.kind()
                when {
                    // The file definitely does not exist
                    matchVal1 == ErrorKind.NotFound -> Result.Ok<Boolean, Error>(false)
                    // `ERROR_SHARING_VIOLATION` means that the file has been locked by
                    // another process. This is often temporary so we simply report it
                    // as the file existing.
                    e.rawOsError() == ERROR_SHARING_VIOLATION.toInt() -> Result.Ok<Boolean, Error>(true)
                    // `ERROR_CANT_ACCESS_FILE` means that a file exists but that the
                    // reparse point could not be handled by `CreateFile`.
                    // This can happen for special files such as:
                    // * Unix domain sockets which you need to `connect` to
                    // * App exec links which require using `CreateProcess`
                    e.rawOsError() == ERROR_CANT_ACCESS_FILE.toInt() -> Result.Ok<Boolean, Error>(true)
                    // Other errors such as `ERROR_ACCESS_DENIED` may indicate that the
                    // file exists. However, these types of errors are usually more
                    // permanent so we report them here.
                    else -> Result.Err<Boolean, Error>(e)
                }
            }
        } }
    }
}
