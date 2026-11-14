// port-lint: source sys/fs/motor.rs
package io.github.kotlinmania.rustlang.std.sys.fs.motor

import io.github.kotlinmania.motort.RtFd
import io.github.kotlinmania.motort.fs.FILETYPE_DIRECTORY
import io.github.kotlinmania.motort.fs.FILETYPE_FILE
import io.github.kotlinmania.motort.fs.FileLock
import io.github.kotlinmania.motort.fs.LOCK_EXCLUSIVE
import io.github.kotlinmania.motort.fs.LOCK_SHARED
import io.github.kotlinmania.motort.fs.O_APPEND
import io.github.kotlinmania.motort.fs.O_CREATE
import io.github.kotlinmania.motort.fs.O_CREATE_NEW
import io.github.kotlinmania.motort.fs.O_READ
import io.github.kotlinmania.motort.fs.O_TRUNCATE
import io.github.kotlinmania.motort.fs.O_WRITE
import io.github.kotlinmania.motort.fs.PERM_READ
import io.github.kotlinmania.motort.fs.PERM_WRITE
import io.github.kotlinmania.motort.fs.SEEK_CUR
import io.github.kotlinmania.motort.fs.SEEK_END
import io.github.kotlinmania.motort.fs.SEEK_SET
import io.github.kotlinmania.motort.fs.Seek
import io.github.kotlinmania.motort.fs.Stat
import io.github.kotlinmania.motort.fs.TRY_LOCK_EXCLUSIVE
import io.github.kotlinmania.motort.fs.TRY_LOCK_SHARED
import io.github.kotlinmania.motort.fs.closedir
import io.github.kotlinmania.motort.fs.getFileAttr
import io.github.kotlinmania.motort.fs.opendir
import io.github.kotlinmania.motort.fs.rmdirAll
import io.github.kotlinmania.motort.fs.setFilePerm
import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadBuf
import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fs.TryLockError
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sync.nonpoison.WouldBlock
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.AsInnerMut
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fd.hermit.FileDesc
import io.github.kotlinmania.rustlang.std.sys.fs.common.Dir
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.sys.pal.motor.ioSlices
import io.github.kotlinmania.rustlang.std.sys.pal.motor.ioSlicesMut
import io.github.kotlinmania.rustlang.std.sys.pal.motor.mapMotorError
import io.github.kotlinmania.rustlang.std.sys.time.sgx.SystemTime

public fun tryLock(fd: RawFd, operation: UByte): Result<Unit, TryLockError> =
    FileLock(fd, operation).mapErr({ err ->
        val err1 = mapMotorError(err)
        if (err1.kind() == io.ErrorKind.WouldBlock) {
            WouldBlock
        } else {
            Error(err1)
        }
    })

public open class FileType(
    public var rtFiletype: UByte,
) {
    public open fun isDir(): Boolean = (this.rtFiletype == FILETYPE_DIRECTORY).toUByte()

    public open fun isFile(): Boolean = (this.rtFiletype == FILETYPE_FILE).toUByte()

    public open fun isSymlink(): Boolean = false

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileType> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileType> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): FileType =
                    FileType(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileType) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr, value.rtFiletype)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileType =
                    FileType(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileType) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset, value.rtFiletype)
                }
            }
    }
}

public open class FilePermissions(
    public var rtPerm: ULong,
) {
    public open fun readonly(): Boolean = ((this.rtPerm and PERM_WRITE == 0uL) && (this.rtPerm and PERM_READ != 0uL)).toUByte()

    public open fun setReadonly(readonly: Boolean) {
        if (readonly) {
            this.rtPerm = PERM_READ
        } else {
            this.rtPerm = PERM_READ or PERM_WRITE
        }
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FilePermissions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FilePermissions> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FilePermissions =
                    FilePermissions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FilePermissions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.rtPerm)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FilePermissions =
                    FilePermissions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FilePermissions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.rtPerm)
                }
            }
    }
}

public open class FileTimes(
    public var modified: U128,
    public var accessed: U128,
) {
    public open fun setAccessed(t: SystemTime) {
        this.accessed = t.asU128()
    }

    public open fun setModified(t: SystemTime) {
        this.modified = t.asU128()
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileTimes> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileTimes> {
                override val alignBytes: Long = 16L
                override val sizeBytes: Long = 32L
                override fun readNative(addr: Long): FileTimes =
                    FileTimes(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readNative(addr + 16L),
                    )
                override fun writeNative(addr: Long, value: FileTimes) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeNative(addr, value.modified)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeNative(addr + 16L, value.accessed)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileTimes =
                    FileTimes(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).readBytes(buf, offset + 16),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileTimes) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeBytes(buf, offset, value.modified)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.u128Layout(::U128, { it.high }, { it.low })).writeBytes(buf, offset + 16, value.accessed)
                }
            }
    }
}

public open class FileAttr(
    public var inner: io.github.kotlinmania.motort.fs.FileAttr,
) {
    public open fun size(): ULong = this.inner.size

    public open fun perm(): FilePermissions = FilePermissions(rtPerm = this.inner.perm)

    public open fun fileType(): FileType = FileType(rtFiletype = this.inner.fileType)

    public open fun modified(): Result<SystemTime> =
        when (val matchVal = this.inner.modified) {
            0 -> Result.Err(Error.from(ErrorKind.Other))
            else -> {
                val x = matchVal
                Result.Ok(SystemTime.fromU128(x))
            }
        }

    public open fun accessed(): Result<SystemTime> =
        when (val matchVal = this.inner.accessed) {
            0 -> Result.Err(Error.from(ErrorKind.Other))
            else -> {
                val x = matchVal
                Result.Ok(SystemTime.fromU128(x))
            }
        }

    public open fun created(): Result<SystemTime> =
        when (val matchVal = this.inner.created) {
            0 -> Result.Err(Error.from(ErrorKind.Other))
            else -> {
                val x = matchVal
                Result.Ok(SystemTime.fromU128(x))
            }
        }
}

public open class OpenOptions(
    public var rtOpenOptions: UInt,
) {
    public open fun read(read: Boolean) {
        if (read) {
            this.rtOpenOptions = this.rtOpenOptions or O_READ
        } else {
            this.rtOpenOptions = this.rtOpenOptions and O_READ.inv()
        }
    }

    public open fun write(write: Boolean) {
        if (write) {
            this.rtOpenOptions = this.rtOpenOptions or O_WRITE
        } else {
            this.rtOpenOptions = this.rtOpenOptions and O_WRITE.inv()
        }
    }

    public open fun append(append: Boolean) {
        if (append) {
            this.rtOpenOptions = this.rtOpenOptions or O_APPEND
        } else {
            this.rtOpenOptions = this.rtOpenOptions and O_APPEND.inv()
        }
    }

    public open fun truncate(truncate: Boolean) {
        if (truncate) {
            this.rtOpenOptions = this.rtOpenOptions or O_TRUNCATE
        } else {
            this.rtOpenOptions = this.rtOpenOptions and O_TRUNCATE.inv()
        }
    }

    public open fun create(create: Boolean) {
        if (create) {
            this.rtOpenOptions = this.rtOpenOptions or O_CREATE
        } else {
            this.rtOpenOptions = this.rtOpenOptions and O_CREATE.inv()
        }
    }

    public open fun createNew(createNew: Boolean) {
        if (createNew) {
            this.rtOpenOptions = this.rtOpenOptions or O_CREATE_NEW
        } else {
            this.rtOpenOptions = this.rtOpenOptions and O_CREATE_NEW.inv()
        }
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<OpenOptions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<OpenOptions> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): OpenOptions =
                    OpenOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: OpenOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.rtOpenOptions)
                }
                override fun readBytes(buf: ByteArray, offset: Int): OpenOptions =
                    OpenOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: OpenOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.rtOpenOptions)
                }
            }

        public fun new(): OpenOptions = OpenOptions(rtOpenOptions = 0u)
    }
}

public open class File(
    public var value: FileDesc,
) : AsInner<FileDesc>, AsInnerMut<FileDesc>, IntoInner<FileDesc>, FromInner<FileDesc, File>, AsFd, AsRawFd, IntoRawFd, FromRawFd<File, Any?, Any> {
    public var field0: FileDesc
        get() = value
        set(v) {
            value = v
        }

    public open fun fileAttr(): Result<FileAttr> =
        getFileAttr(this.asRawFd()).map({ inner ->
            FileAttr(inner = inner)
        }).mapErr(mapMotorError)

    public open fun fsync(): Result<Unit> = io.github.kotlinmania.motort.fs.fsync(this.asRawFd()).mapErr(mapMotorError)

    public open fun datasync(): Result<Unit> = io.github.kotlinmania.motort.fs.datasync(this.asRawFd()).mapErr(mapMotorError)

    public open fun truncate(size: ULong): Result<Unit> = io.github.kotlinmania.motort.fs.truncate(this.asRawFd(), size).mapErr(mapMotorError)

    public open fun read(buf: MutableList<UByte>): Result<Int> = io.github.kotlinmania.motort.fs.read(this.asRawFd(), buf).mapErr(mapMotorError)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = io.github.kotlinmania.motort.fs.readVectored(this.asRawFd(), ioSlicesMut(bufs)).mapErr(mapMotorError)

    public open fun isReadVectored(): Boolean = true

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = defaultReadBuf({ buf -> this.read(buf) }, cursor)

    public open fun write(buf: List<UByte>): Result<Int> = io.github.kotlinmania.motort.fs.write(this.asRawFd(), buf).mapErr(mapMotorError)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = io.github.kotlinmania.motort.fs.writeVectored(this.asRawFd(), ioSlices(bufs)).mapErr(mapMotorError)

    public open fun isWriteVectored(): Boolean = true

    public open fun flush(): Result<Unit> = io.github.kotlinmania.motort.fs.flush(this.asRawFd()).mapErr(mapMotorError)

    public open fun seek(pos: SeekFrom): Result<ULong> =
        when (pos) {
            is SeekFrom.Start -> {
                val (offset) = pos
                Seek(this.asRawFd(), offset.toLong(), SEEK_SET).mapErr(mapMotorError)
            }
            is SeekFrom.End -> {
                val (offset1) = pos
                Seek(this.asRawFd(), offset1, SEEK_END).mapErr(mapMotorError)
            }
            is SeekFrom.Current -> {
                val (offset2) = pos
                Seek(this.asRawFd(), offset2, SEEK_CUR).mapErr(mapMotorError)
            }
        }

    public open fun tell(): Result<ULong> = this.seek(SeekFrom.Current(0))

    public open fun duplicate(): Result<File> = io.github.kotlinmania.motort.fs.duplicate(this.asRawFd()).map({ fd -> File.fromRawFd(fd) }).mapErr(mapMotorError)

    public open fun setPermissions(perm: FilePermissions): Result<Unit> = setFilePerm(this.asRawFd(), perm.rtPerm).mapErr(mapMotorError)

    public open fun setTimes(times: FileTimes): Result<Unit> {
        return unsupported()
        // Let's not do that.
    }

    public open fun lock(): Result<Unit> = FileLock(this.asRawFd(), LOCK_EXCLUSIVE).mapErr(mapMotorError)

    public open fun lockShared(): Result<Unit> = FileLock(this.asRawFd(), LOCK_SHARED).mapErr(mapMotorError)

    public open fun tryLock(): Result<Unit, TryLockError> = tryLock(this.asRawFd(), TRY_LOCK_EXCLUSIVE)

    public open fun tryLockShared(): Result<Unit, TryLockError> = tryLock(this.asRawFd(), TRY_LOCK_SHARED)

    public open fun unlock(): Result<Unit> = FileLock(this.asRawFd(), io.github.kotlinmania.motort.fs.unlock).mapErr(mapMotorError)

    public open fun size(): Result<ULong>? = null

    public override fun asInner(): FileDesc = this.value

    public override fun asInnerMut(): FileDesc = this.value

    public override fun intoInner(): FileDesc = this.value

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asRawFd(): RawFd = this.value.asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoRawFd()

    public companion object {
        public fun open(path: Path, opts: OpenOptions): Result<File> {
            val path1 = when (val tryResult = path.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return io.github.kotlinmania.motort.fs.open(path1, opts.rtOpenOptions).map({ fd -> File.fromRawFd(fd) }).mapErr(mapMotorError)
        }

        public fun fromInner(fileDesc: FileDesc): File = File(fileDesc)

        public fun fromRawFd(rawFd: RawFd): File = File(FromRawFd.fromRawFd(rawFd))
    }
}

public open class DirBuilder {
    public open fun mkdir(path: Path): Result<Unit> {
        val path1 = when (val tryResult = path.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return io.github.kotlinmania.motort.fs.mkdir(path1).mapErr(mapMotorError)
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<DirBuilder> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<DirBuilder> {
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

public fun unlink(path: Path): Result<Unit> {
    val path1 = when (val tryResult = path.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return io.github.kotlinmania.motort.fs.unlink(path1).mapErr(mapMotorError)
}

public fun rename(old: Path, new: Path): Result<Unit> {
    val old1 = when (val tryResult = old.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val new1 = when (val tryResult = new.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return io.github.kotlinmania.motort.fs.rename(old1, new1).mapErr(mapMotorError)
}

public fun renameNoreplace(old: Path, new: Path): Result<Unit> = unsupported()

public fun rmdir(path: Path): Result<Unit> {
    val path1 = when (val tryResult = path.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return io.github.kotlinmania.motort.fs.rmdir(path1).mapErr(mapMotorError)
}

public fun removeDirAll(path: Path): Result<Unit> {
    val path1 = when (val tryResult = path.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return rmdirAll(path1).mapErr(mapMotorError)
}

public fun setPerm(path: Path, perm: FilePermissions): Result<Unit> {
    // Motor does not support symlinks
    return setPermNofollow(path, perm)
}

public fun setPermNofollow(path: Path, perm: FilePermissions): Result<Unit> {
    val path1 = when (val tryResult = path.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return io.github.kotlinmania.motort.fs.setPerm(path1, perm.rtPerm).mapErr(mapMotorError)
}

public fun setTimes(p: Path, times: FileTimes): Result<Unit> = unsupported()

public fun setTimesNofollow(p: Path, times: FileTimes): Result<Unit> = unsupported()

public fun readlink(p: Path): Result<PathBuf> = unsupported()

public fun symlink(original: Path, link: Path): Result<Unit> = unsupported()

public fun link(src: Path, dst: Path): Result<Unit> = unsupported()

public fun stat(path: Path): Result<FileAttr> {
    val path1 = when (val tryResult = path.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val inner = when (val tryResult = Stat(path1).mapErr(mapMotorError)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(FileAttr(inner = inner))
}

public fun lstat(path: Path): Result<FileAttr> = stat(path)

public fun canonicalize(path: Path): Result<PathBuf> {
    val path1 = when (val tryResult = path.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val path2 = when (val tryResult = io.github.kotlinmania.motort.fs.canonicalize(path1).mapErr(mapMotorError)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(path2.into())
}

public fun copy(from: Path, to: Path): Result<ULong> {
    val from1 = when (val tryResult = from.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val to1 = when (val tryResult = to.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return io.github.kotlinmania.motort.fs.copy(from1, to1).mapErr(mapMotorError)
}

public open class ReadDir(
    public var rtFd: RtFd,
    public var path: String,
) : Iterable<Result<DirEntry>> {
    override fun iterator(): kotlin.collections.Iterator<Result<DirEntry>> = object : AbstractIterator<Result<DirEntry>>() {
        override fun computeNext() {
            when (val v = this@ReadDir.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun drop() {
        closedir(this.rtFd).unwrap()
    }

    public open fun dir(): Result<Dir> {
        val options = OpenOptions.new()
        options.read(true)
        return Dir.open(Path.new(this.path), options)
    }

    public open fun next(): Result<DirEntry>? =
        run {
            val matchVal = io.github.kotlinmania.motort.fs.readdir(this.rtFd).mapErr(mapMotorError)
            when (matchVal) { is Result.Ok<*> -> {
                val maybeItem = matchVal.value
                when (maybeItem) {
                    null -> null
                    else -> {
                        val inner = maybeItem
                        Result.Ok(DirEntry(inner = inner, parentPath = this.path.clone()))
                    }
                }
            }; is Result.Err<*> -> {
                val err = matchVal.error
                Result.Err(err)
            } }
        }
}

public fun readdir(path: Path): Result<ReadDir> {
    val path1 = when (val tryResult = path.toStr().okOr(io.Error.from(io.ErrorKind.InvalidFilename))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(ReadDir(rtFd = when (val tryResult = opendir(path1).mapErr(mapMotorError)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, path = path1.toOwned()))
}

public open class DirEntry(
    public var parentPath: String,
    public var inner: io.github.kotlinmania.motort.fs.DirEntry,
) {
    public open fun filename(): String = String.fromUtf8(rawSliceList(this.inner.fname.asPtr(), this.inner.fnameSize.toInt(), PrimitiveLayouts.U8)).unwrap()

    public open fun path(): PathBuf {
        var path = this.parentPath.clone()
        path += "/"
        path += this.filename()
        return path.into()
    }

    public open fun fileName(): OsString = this.filename().toOwned().into()

    public open fun metadata(): Result<FileAttr> = Result.Ok(FileAttr(inner = this.inner.attr))

    public open fun fileType(): Result<FileType> = Result.Ok(FileType(rtFiletype = this.inner.attr.fileType))
}
