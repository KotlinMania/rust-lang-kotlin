// port-lint: source sys/fs/windows/dir.rs
package io.github.kotlinmania.rustlang.std.sys.fs.windows.dir

import io.github.kotlinmania.rustlang.alloc.alloc.alloc
import io.github.kotlinmania.rustlang.alloc.alloc.dealloc
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.checkedMul
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.castMut
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.read
import io.github.kotlinmania.rustlang.core.ptr.mutptr.write
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.os.windows.io.BorrowedHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.FromRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.HandleOrInvalid
import io.github.kotlinmania.rustlang.std.os.windows.io.OwnedHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.RawHandle
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.fs.windows.File
import io.github.kotlinmania.rustlang.std.sys.fs.windows.FileAttr
import io.github.kotlinmania.rustlang.std.sys.fs.windows.OpenOptions
import io.github.kotlinmania.rustlang.std.sys.fs.windows.ReparsePoint
import io.github.kotlinmania.rustlang.std.sys.fs.windows.debugPathHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.UnicodeStrRef
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.WinError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.DELETE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ERROR_CANT_ACCESS_FILE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_ATTRIBUTE_NORMAL
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_DIRECTORY_FILE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_FLAG_BACKUP_SEMANTICS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_NON_DIRECTORY_FILE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_OPEN_REPARSE_POINT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_READ_ATTRIBUTES
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_RENAME_FLAG_POSIX_SEMANTICS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_RENAME_FLAG_REPLACE_IF_EXISTS
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_SYNCHRONOUS_IO_NONALERT
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.FILE_TRAVERSE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.SYNCHRONIZE
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.ntSuccess
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileRenameInformation
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.FileRenameInformation0
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.IoStatusBlock
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ObjectAttributes
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.SecurityAttributes
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.createFileW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ntSetInformationFile
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.rtlNtStatusToDosError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.handle.Handle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.toU16s
import io.github.kotlinmania.rustlang.std.sys.path.windows.WCStr
import io.github.kotlinmania.rustlang.std.sys.path.windows.withNativePath

public open class Dir internal constructor(
    handleCtorArg: Handle,
) {
    public var handle: Handle = handleCtorArg
        internal set

    public open fun duplicate(): Result<Dir, Error> {
        return Result.Ok<Dir, Error>(Dir(handle = when (val tryResult = this.handle.tryClone()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Dir, Error>(tryResult.errValue()) }))
    }

    public open fun openFile(path: Path, opts: OpenOptions): Result<File, Error> {
        // NtCreateFile will fail if given an absolute path and a non-null RootDirectory
        if (path.isAbsolute()) {
            return File.open(path, opts)
        }
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<File, Error>(tryResult.errValue()) }
        return this.openFileNative(path1, opts, FILE_NON_DIRECTORY_FILE).map({ handle -> File(handle = handle) })
    }

    public open fun removeFile(path: Path): Result<Unit, Error> {
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        return this.removeNative(path1, false)
    }

    public open fun rename(from: Path, toDir: Dir, to: Path): Result<Unit, Error> {
        val from1 = when (val tryResult = toU16sWithoutNul(from)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        val to1 = when (val tryResult = toU16sWithoutNul(to)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        return this.renameNative(from1, toDir, to1)
    }

    public open fun createDir(path: Path): Result<Unit, Error> {
        val opts = OpenOptions.new()
        opts.read(true)
        opts.write(true)
        opts.createNew(true)
        return this.openDir(path, opts).map({ Unit })
    }

    public open fun openDir(path: Path, opts: OpenOptions): Result<Dir, Error> {
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Dir, Error>(tryResult.errValue()) }
        return this.openFileNative(path1, opts, FILE_DIRECTORY_FILE).map({ handle -> Dir(handle = handle) })
    }

    public open fun removeDir(path: Path): Result<Unit, Error> {
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        return this.removeNative(path1, true)
    }

    public open fun openFileNative(path: List<UShort>, opts: OpenOptions, createOpt: UInt): Result<Handle, Error> {
        val name = UnicodeStrRef.fromSlice(path)
        val objectAttributes = ObjectAttributes.withLength().copy(rootDirectory = this.handle.asRawHandle(), objectName = name.asPtr().castMut())
        return ntCreateFile(opts, objectAttributes, createOpt)
    }

    public open fun removeNative(path: List<UShort>, dir: Boolean): Result<Unit, Error> {
        val opts = OpenOptions.new()
        opts.accessMode(DELETE)
        var createOpt =
            if (dir) {
                FILE_DIRECTORY_FILE
            } else {
                FILE_NON_DIRECTORY_FILE
            }
        createOpt = createOpt or FILE_OPEN_REPARSE_POINT
        val handle = when (val tryResult = this.openFileNative(path, opts, createOpt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        return File.FromInner(handle).delete().ioResult()
    }

    public open fun renameNative(from: List<UShort>, toDir: Dir, to: List<UShort>): Result<Unit, Error> {
        // Calculate the layout of the `FILE_RENAME_INFORMATION` we pass to `NtSetInformationFile`
        // This is a dynamically sized struct so we need to get the position of the last field to calculate the actual size.
        val opts = OpenOptions.new()
        opts.accessMode(DELETE)
        val handle = when (val tryResult = this.openFileNative(from, opts, FILE_OPEN_REPARSE_POINT)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        val structSize = when (val tryResult = (to.size.checkedMul(2)?.let({ x -> x.checkedAdd(0) })?.let { Result.Ok<Any, Error>(it) } ?: Result.Err<Any, Error>(tooLongErr))) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        val layout = when (val tryResult = Layout.fromSizeAlign(structSize, alignOf<FileRenameInformation>(typeLayoutT = UnresolvedTypeLayout<FileRenameInformation>())).mapErr({ tooLongErr })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        val structSize1 = when (val tryResult = UInt.tryFrom(structSize).mapErr({ tooLongErr })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        val toByteLen = when (val tryResult = UInt.tryFrom(to.size * 2).mapErr({ tooLongErr })) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        val fileRenameInfo: Any?
        // SAFETY: We allocate enough memory for a full FILE_RENAME_INFORMATION struct and the filename.
        run {
            fileRenameInfo = alloc(layout).cast()
            if (fileRenameInfo.isNull()) {
                return Result.Err<Unit, Error>(ErrorKind.OutOfMemory.into())
            }
            (addrOfMut(fileRenameInfo.anonymous)).write(FileRenameInformation0(flags = FILE_RENAME_FLAG_REPLACE_IF_EXISTS or FILE_RENAME_FLAG_POSIX_SEMANTICS))
            (addrOfMut(fileRenameInfo.rootDirectory)).write(toDir.handle.asRawHandle())
            // Don't include the NULL in the size
            (addrOfMut(fileRenameInfo.fileNameLength)).write(toByteLen)
            kmCopyNonoverlapping((to.asPtr()).rawAddress, ((addrOfMut(fileRenameInfo.fileName)).cast()).rawAddress, (to.size).toLong() * (PrimitiveLayouts.U16).sizeBytes)
        }
        val status = ntSetInformationFile(handle.asRawHandle(), IoStatusBlock.default(), fileRenameInfo.cast(), structSize1, FileRenameInformation)
        dealloc(fileRenameInfo.cast(), layout)
        return if (ntSuccess(status)) {
            // SAFETY: ntSuccess guarantees that handle is no longer null
            Result.Ok<Unit, Error>(Unit)
        } else {
            Result.Err<Unit, Error>(WinError.new(rtlNtStatusToDosError(status)))
        }.ioResult()
    }

    public open fun selfMetadata(): Result<FileAttr, Error> {
        // Reuse the implementation for files, which should work for all handles.
        val handle = this.handle.asRawHandle()
        val f = File(handle = Handle.FromRawHandle(handle))
        return f.fileAttr()
    }

    public open fun metadata(path: Path): Result<FileAttr, Error> {
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<FileAttr, Error>(tryResult.errValue()) }
        // Same as the `stat` logic used for `fs.metadata`
        return run {
            val matchVal = this.metadataNative(path1, ReparsePoint.Follow)
            when {
                matchVal is Result.Err<*, *> && matchVal.errValue().rawOsError() == ERROR_CANT_ACCESS_FILE.toInt() -> {
                    val err = matchVal.errValue()
                    // Fallback to opening reparse points when following fails. Needed for UNIX domain
                    // sockets. See <https://github.com/rust-lang/rust/issues/109106>.
                    when (val matchVal1 = this.metadataNative(path1, ReparsePoint.Open)) {
                        is Result.Ok<*, *> -> {
                            val attrs = matchVal1.okValue()
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

    public open fun symlinkMetadata(path: Path): Result<FileAttr, Error> {
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<FileAttr, Error>(tryResult.errValue()) }
        return this.metadataNative(path1, ReparsePoint.Open)
    }

    public open fun metadataNative(path: List<UShort>, reparse: ReparsePoint): Result<FileAttr, Error> {
        val opts = OpenOptions.new()
        // the NT functions need at least c.FileReadAttributes
        opts.accessMode(FILE_READ_ATTRIBUTES)
        val createOpt =
            if (reparse == ReparsePoint.Open) {
                FILE_OPEN_REPARSE_POINT
            } else {
                0
            }
        val name = UnicodeStrRef.fromSlice(path)
        val objectAttributes = ObjectAttributes.withLength().copy(rootDirectory = this.handle.asRawHandle(), objectName = name.asPtr().castMut())
        val handle = when (val tryResult = ntCreateFile(opts, objectAttributes, createOpt)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<FileAttr, Error>(tryResult.errValue()) }
        return File(handle = handle).fileAttr()
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val b = debugPathHandle(this.handle.asHandle(), f, "Dir")
        return run { b.finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public companion object {
        public operator fun invoke(
            handle: Handle,
        ): Dir = Dir(
            handleCtorArg = handle,
        )

        public fun open(path: Path, opts: OpenOptions): Result<Dir, Error> = withNativePath(path, { path1 -> Dir.openWithNative(path1, opts) })

        public fun openForTraversal(path: Path): Result<Dir, Error> {
            val opts = OpenOptions.new()
            opts.accessMode(FILE_TRAVERSE)
            return withNativePath(path, { path1 -> Dir.openWithNative(path1, opts) })
        }

        public fun openWithNative(path: WCStr, opts: OpenOptions): Result<Dir, Error> {
            val creation = when (val tryResult = opts.getCreationMode()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Dir, Error>(tryResult.errValue()) }
            val localStorageL119C8 = AddressableLocal(SecurityAttributes(nLength = sizeOf<SecurityAttributes>(typeLayoutT = UnresolvedTypeLayout<SecurityAttributes>()).toUInt(), lpSecurityDescriptor = MutPtr(0L), bInheritHandle = opts.inheritHandle.toInt()), UnresolvedTypeLayout<SecurityAttributes>())
            val handle = createFileW(path.asPtr(), when (val tryResult = opts.getAccessMode()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Dir, Error>(tryResult.errValue()) }, opts.shareMode, localStorageL119C8.asConstPtr(), creation, opts.getFlagsAndAttributes() or FILE_FLAG_BACKUP_SEMANTICS, MutPtr(0L))
            return run {
                val matchVal = OwnedHandle.tryFrom(HandleOrInvalid.FromRawHandle(handle))
                when (matchVal) { is Result.Ok<*, *> -> {
                    val handle1 = matchVal.okValue()
                    Result.Ok<Dir, Error>(Dir(handle = Handle.FromInner(handle1)))
                }; is Result.Err<*, *> -> {
                    Result.Err<Dir, Error>(Error.lastOsError())
                } }
            }
        }
    }
}

public fun toU16sWithoutNul(path: Path): Result<List<UShort>, Error> {
    val path1 = when (val tryResult = toU16s(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<List<UShort>, Error>(tryResult.errValue()) }
    path1.pop()
    return Result.Ok<List<UShort>, Error>(path1)
}

/**
 * A wrapper around a raw NtCreateFile call.
 *
 * This isn't completely safe because `OBJECT_ATTRIBUTES` contains raw pointers.
 */
public fun ntCreateFile(opts: OpenOptions, objectAttributes: ObjectAttributes, createOptions: UInt): Result<Handle, Error> {
    val handle = MutPtr(0L)
    val ioStatus = IoStatusBlock.PENDING
    // SYNCHRONIZE is included in FILE_GENERIC_READ, but not GENERIC_READ, so we add it manually
    val access = when (val tryResult = opts.getAccessMode()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Handle, Error>(tryResult.errValue()) } or SYNCHRONIZE
    // one of FILE_SYNCHRONOUS_IO_(,NON)ALERT is required for later operations to succeed.
    val options = createOptions or FILE_SYNCHRONOUS_IO_NONALERT
    val status = io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.ntCreateFile(handle, access, objectAttributes, ioStatus, ConstPtr(0L), FILE_ATTRIBUTE_NORMAL, opts.shareMode, when (val tryResult = opts.getDisposition()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Handle, Error>(tryResult.errValue()) }, options, ConstPtr(0L), 0u)
    return if (ntSuccess(status)) {
        // SAFETY: ntSuccess guarantees that handle is no longer null
        Result.Ok<Handle, Error>(Handle.FromRawHandle(handle))
    } else {
        Result.Err<Handle, Error>(WinError.new(rtlNtStatusToDosError(status))).ioResult()
    }
}

public fun io.github.kotlinmania.rustlang.std.fs.Dir.asRawHandle(): RawHandle = this.asInner().handle.asRawHandle()

public fun io.github.kotlinmania.rustlang.std.fs.Dir.intoRawHandle(): RawHandle = this.intoInner().handle.intoRawHandle()

public fun fromRawHandleDir(handle: RawHandle): io.github.kotlinmania.rustlang.std.fs.Dir = io.github.kotlinmania.rustlang.std.fs.Dir.FromInner(Dir(handle = FromRawHandle.FromRawHandle(handle)))

public fun io.github.kotlinmania.rustlang.std.fs.Dir.asHandle(): BorrowedHandle = this.asInner().handle.asHandle()

public fun fromOwnedHandle(value: io.github.kotlinmania.rustlang.std.fs.Dir): OwnedHandle = value.intoInner().handle.intoInner()

public fun fromDir(value: OwnedHandle): io.github.kotlinmania.rustlang.std.fs.Dir = io.github.kotlinmania.rustlang.std.fs.Dir.FromInner(Dir(handle = Handle.FromInner(value)))
