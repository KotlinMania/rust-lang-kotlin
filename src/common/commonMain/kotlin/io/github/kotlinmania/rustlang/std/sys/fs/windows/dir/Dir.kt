// port-lint: source sys/fs/windows/dir.rs
package io.github.kotlinmania.rustlang.std.sys.fs.windows.dir

import io.github.kotlinmania.rustlang.alloc.alloc.alloc
import io.github.kotlinmania.rustlang.alloc.alloc.dealloc
import io.github.kotlinmania.rustlang.core.alloc.layout.Layout
import io.github.kotlinmania.rustlang.core.intrinsics.kmCopyNonoverlapping
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.alignOf
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.num.rustCheckedMul
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.io.ErrorKind
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.windows.io.BorrowedHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.FromRawHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.HandleOrInvalid
import io.github.kotlinmania.rustlang.std.os.windows.io.OwnedHandle
import io.github.kotlinmania.rustlang.std.os.windows.io.RawHandle
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.c
import io.github.kotlinmania.rustlang.std.sys.fs.unix.File
import io.github.kotlinmania.rustlang.std.sys.fs.unix.OpenOptions
import io.github.kotlinmania.rustlang.std.sys.fs.unsupported.FileAttr
import io.github.kotlinmania.rustlang.std.sys.fs.windows.ReparsePoint
import io.github.kotlinmania.rustlang.std.sys.fs.windows.debugPathHandle
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.UnicodeStrRef
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.WinError
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
        return Result.Ok(Dir(handle = when (val tryResult = this.handle.tryClone()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
    }

    public open fun openFile(path: Path, opts: OpenOptions): Result<File, Error> {
        // NtCreateFile will fail if given an absolute path and a non-null RootDirectory
        if (path.isAbsolute()) {
            return File.open(path, opts)
        }
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return this.openFileNative(path1, opts, c.FILE_NON_DIRECTORY_FILE).map({ handle -> File(handle = handle) })
    }

    public open fun removeFile(path: Path): Result<Unit, Error> {
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return this.removeNative(path1, false)
    }

    public open fun rename(from: Path, toDir: Dir, to: Path): Result<Unit, Error> {
        val from1 = when (val tryResult = toU16sWithoutNul(from)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val to1 = when (val tryResult = toU16sWithoutNul(to)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
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
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return this.openFileNative(path1, opts, c.FILE_DIRECTORY_FILE).map({ handle -> Dir(handle = handle) })
    }

    public open fun removeDir(path: Path): Result<Unit, Error> {
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return this.removeNative(path1, true)
    }

    public open fun openFileNative(path: List<UShort>, opts: OpenOptions, createOpt: UInt): Result<Handle, Error> {
        val name = UnicodeStrRef.fromSlice(path)
        val objectAttributes = c.ObjectAttributes.withLength().copy(rootDirectory = this.handle.asRawHandle(), objectName = name.asPtr().castMut())
        return ntCreateFile(opts, objectAttributes, createOpt)
    }

    public open fun removeNative(path: List<UShort>, dir: Boolean): Result<Unit, Error> {
        val opts = OpenOptions.new()
        opts.accessMode(c.DELETE)
        var createOpt =
            if (dir) {
                c.FILE_DIRECTORY_FILE
            } else {
                c.FILE_NON_DIRECTORY_FILE
            }
        createOpt = createOpt or c.FILE_OPEN_REPARSE_POINT
        val handle = when (val tryResult = this.openFileNative(path, opts, createOpt)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return File.fromInner(handle).delete().ioResult()
    }

    public open fun renameNative(from: List<UShort>, toDir: Dir, to: List<UShort>): Result<Unit, Error> {
        // Calculate the layout of the `FILE_RENAME_INFORMATION` we pass to `NtSetInformationFile`
        // This is a dynamically sized struct so we need to get the position of the last field to calculate the actual size.
        val opts = OpenOptions.new()
        opts.accessMode(c.DELETE)
        val handle = when (val tryResult = this.openFileNative(from, opts, c.FILE_OPEN_REPARSE_POINT)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val structSize = when (val tryResult = (rustCheckedMul(to.size, 2)?.let({ x -> x.checkedAdd(0) })?.let { Result.Ok(it) } ?: Result.Err(tooLongErr))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val layout = when (val tryResult = Layout.fromSizeAlign(structSize, alignOf<c.FileRenameInformation>(typeLayoutT = ObjectSlotLayout<c.FileRenameInformation>())).mapErr({ tooLongErr })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val structSize1 = when (val tryResult = UInt.tryFrom(structSize).mapErr({ tooLongErr })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val toByteLen = when (val tryResult = UInt.tryFrom(to.size * 2).mapErr({ tooLongErr })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val fileRenameInfo: Any?
        // SAFETY: We allocate enough memory for a full FILE_RENAME_INFORMATION struct and the filename.
        run {
            fileRenameInfo = alloc(layout).cast()
            if (fileRenameInfo.isNull()) {
                return Result.Err(ErrorKind.OutOfMemory.into())
            }
            (addrOfMut(fileRenameInfo.anonymous)).write(c.FileRenameInformation0(flags = c.FILE_RENAME_FLAG_REPLACE_IF_EXISTS or c.FILE_RENAME_FLAG_POSIX_SEMANTICS))
            (addrOfMut(fileRenameInfo.rootDirectory)).write(toDir.handle.asRawHandle())
            // Don't include the NULL in the size
            (addrOfMut(fileRenameInfo.fileNameLength)).write(toByteLen)
            kmCopyNonoverlapping((to.asPtr()).rawAddress, ((addrOfMut(fileRenameInfo.fileName)).cast()).rawAddress, (to.size).toLong() * (PrimitiveLayouts.U16).sizeBytes)
        }
        val status = c.NtSetInformationFile(handle.asRawHandle(), c.IoStatusBlock.default(), fileRenameInfo.cast(), structSize1, c.FileRenameInformation)
        dealloc(fileRenameInfo.cast(), layout)
        return if (c.ntSuccess(status)) {
            // SAFETY: ntSuccess guarantees that handle is no longer null
            Result.Ok(Unit)
        } else {
            Result.Err(WinError.new(c.RtlNtStatusToDosError(status)))
        }.ioResult()
    }

    public open fun selfMetadata(): Result<FileAttr, Error> {
        // Reuse the implementation for files, which should work for all handles.
        val handle = this.handle.asRawHandle()
        val f = File(handle = Handle.fromRawHandle(handle))
        return f.fileAttr()
    }

    public open fun metadata(path: Path): Result<FileAttr, Error> {
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        // Same as the `stat` logic used for `fs.metadata`
        return run {
            val matchVal = this.metadataNative(path1, ReparsePoint.Follow)
            when {
                matchVal is Result.Err<*> && (matchVal as Result.Err<*>).error.rawOsError() == c.ERROR_CANT_ACCESS_FILE.toInt() -> {
                    val err = (matchVal as Result.Err<*>).error
                    // Fallback to opening reparse points when following fails. Needed for UNIX domain
                    // sockets. See <https://github.com/rust-lang/rust/issues/109106>.
                    when (val matchVal1 = this.metadataNative(path1, ReparsePoint.Open)) {
                        is Ok -> {
                            val attrs = matchVal1.value
                            if (!attrs.fileType().isSymlink()) {
                                return Result.Ok(attrs)
                            }
                        }
                        else -> {}
                    }
                    Result.Err(err)
                }
                else -> {
                    val result = matchVal
                    result
                }
            }
        }
    }

    public open fun symlinkMetadata(path: Path): Result<FileAttr, Error> {
        val path1 = when (val tryResult = toU16sWithoutNul(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return this.metadataNative(path1, ReparsePoint.Open)
    }

    public open fun metadataNative(path: List<UShort>, reparse: ReparsePoint): Result<FileAttr, Error> {
        val opts = OpenOptions.new()
        // the NT functions need at least c.FileReadAttributes
        opts.accessMode(c.FILE_READ_ATTRIBUTES)
        val createOpt =
            if (reparse == ReparsePoint.Open) {
                c.FILE_OPEN_REPARSE_POINT
            } else {
                0
            }
        val name = UnicodeStrRef.fromSlice(path)
        val objectAttributes = c.ObjectAttributes.withLength().copy(rootDirectory = this.handle.asRawHandle(), objectName = name.asPtr().castMut())
        val handle = when (val tryResult = ntCreateFile(opts, objectAttributes, createOpt)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return File(handle = handle).fileAttr()
    }

    public open fun fmt(f: StringBuilder) {
        val b = debugPathHandle(this.handle.asHandle(), f, "Dir")
        b.finish()
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
            opts.accessMode(c.FILE_TRAVERSE)
            return withNativePath(path, { path1 -> Dir.openWithNative(path1, opts) })
        }

        public fun openWithNative(path: WCStr, opts: OpenOptions): Result<Dir, Error> {
            val creation = when (val tryResult = opts.getCreationMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            val localStorageL119C8 = AddressableLocal(c.SecurityAttributes(nLength = sizeOf<c.SecurityAttributes>(typeLayoutT = ObjectSlotLayout<c.SecurityAttributes>()).toUInt(), lpSecurityDescriptor = MutPtr(0L), bInheritHandle = opts.inheritHandle.toInt()), ObjectSlotLayout<SecurityAttributes>())
            val handle = c.CreateFileW(path.asPtr(), when (val tryResult = opts.getAccessMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, opts.shareMode, localStorageL119C8.asConstPtr(), creation, opts.getFlagsAndAttributes() or c.FILE_FLAG_BACKUP_SEMANTICS, MutPtr(0L))
            return run {
                val matchVal = OwnedHandle.tryFrom(HandleOrInvalid.fromRawHandle(handle))
                when (matchVal) { is Result.Ok<*> -> {
                    val handle1 = matchVal.value
                    Result.Ok(Dir(handle = Handle.fromInner(handle1)))
                }; is Result.Err<*> -> {
                    Result.Err(io.github.kotlinmania.rustlang.std.io.Error.lastOsError())
                } }
            }
        }
    }
}

public fun toU16sWithoutNul(path: Path): Result<List<UShort>, Error> {
    val path1 = when (val tryResult = toU16s(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    path1.pop()
    return Result.Ok(path1)
}

/**
 * A wrapper around a raw NtCreateFile call.
 *
 * This isn't completely safe because `OBJECT_ATTRIBUTES` contains raw pointers.
 */
public fun ntCreateFile(opts: OpenOptions, objectAttributes: c.ObjectAttributes, createOptions: UInt): Result<Handle, Error> {
    val handle = MutPtr(0L)
    val ioStatus = c.IoStatusBlock.PENDING
    // SYNCHRONIZE is included in FILE_GENERIC_READ, but not GENERIC_READ, so we add it manually
    val access = when (val tryResult = opts.getAccessMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } or c.SYNCHRONIZE
    // one of FILE_SYNCHRONOUS_IO_{,NON}ALERT is required for later operations to succeed.
    val options = createOptions or c.FILE_SYNCHRONOUS_IO_NONALERT
    val status = c.NtCreateFile(handle, access, objectAttributes, ioStatus, ConstPtr(0L), c.FILE_ATTRIBUTE_NORMAL, opts.shareMode, when (val tryResult = opts.getDisposition()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, options, ConstPtr(0L), 0)
    return if (c.ntSuccess(status)) {
        // SAFETY: ntSuccess guarantees that handle is no longer null
        Result.Ok(Handle.fromRawHandle(handle))
    } else {
        Result.Err(WinError.new(c.RtlNtStatusToDosError(status))).ioResult()
    }
}

public fun io.github.kotlinmania.rustlang.std.fs.Dir.asRawHandle(): RawHandle = this.asInner().handle.asRawHandle()

public fun io.github.kotlinmania.rustlang.std.fs.Dir.intoRawHandle(): RawHandle = this.intoInner().handle.intoRawHandle()

public fun fromRawHandleDir(handle: RawHandle): io.github.kotlinmania.rustlang.std.fs.Dir = io.github.kotlinmania.rustlang.std.fs.Dir.fromInner(Dir(handle = FromRawHandle.fromRawHandle(handle)))

public fun io.github.kotlinmania.rustlang.std.fs.Dir.asHandle(): BorrowedHandle = this.asInner().handle.asHandle()

public fun fromOwnedHandle(value: io.github.kotlinmania.rustlang.std.fs.Dir): OwnedHandle = value.intoInner().handle.intoInner()

public fun fromDir(value: OwnedHandle): io.github.kotlinmania.rustlang.std.fs.Dir = io.github.kotlinmania.rustlang.std.fs.Dir.fromInner(Dir(handle = Handle.fromInner(value)))
