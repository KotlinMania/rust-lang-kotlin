// port-lint: source sys/fs/uefi.rs
package io.github.kotlinmania.rustlang.std.sys.fs.uefi

import io.github.kotlinmania.refi.protocols.file.MODE_READ
import io.github.kotlinmania.refi.protocols.file.directory
import io.github.kotlinmania.refi.protocols.file.readOnly
import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadBuf
import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadVectored
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.io.write.defaultWriteVectored
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.fs.TryLockError
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.path.Component
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.path.absolute
import io.github.kotlinmania.rustlang.std.sys.fs.common.Dir
import io.github.kotlinmania.rustlang.std.sys.fs.uefi.uefifs.fileNameFromUefi
import io.github.kotlinmania.rustlang.std.sys.fs.uefi.uefifs.systemtimeToUefi
import io.github.kotlinmania.rustlang.std.sys.fs.uefi.uefifs.uefiToSystemtime
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.UefiBox
import io.github.kotlinmania.rustlang.std.sys.time.sgx.SystemTime

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

public val FILE_PERMISSIONS_MASK: ULong = readOnly

public open class File(
    public var value: File,
) {
    public var field0: File
        get() = value
        set(v) {
            value = v
        }

    public open fun fileAttr(): Result<FileAttr> = this.value.fileInfo().map(FileAttr::fromUefi)

    public open fun fsync(): Result<Unit> = this.datasync()

    public open fun datasync(): Result<Unit> = this.value.flush()

    public open fun lock(): Result<Unit> = unsupported()

    public open fun lockShared(): Result<Unit> = unsupported()

    public open fun tryLock(): Result<Unit, TryLockError> = unsupported().mapErr({ TryLockError.Error(it) })

    public open fun tryLockShared(): Result<Unit, TryLockError> = unsupported().mapErr({ TryLockError.Error(it) })

    public open fun unlock(): Result<Unit> = unsupported()

    public open fun truncate(size: ULong): Result<Unit> {
        val fileInfo = when (val tryResult = this.value.fileInfo()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        fileInfo.asMutPtr().fileSize = size
        return this.value.setFileInfo(fileInfo)
    }

    public open fun read(buf: MutableList<UByte>): Result<Int> = this.value.read(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = defaultReadVectored({ b -> this.read(b) }, bufs)

    public open fun isReadVectored(): Boolean = false

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = defaultReadBuf({ buf -> this.read(buf) }, cursor)

    public open fun write(buf: List<UByte>): Result<Int> = this.value.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = defaultWriteVectored({ b -> this.write(b) }, bufs)

    public open fun isWriteVectored(): Boolean = false

    // Write.flush is only meant for buffered writers. So should be noop for unbuffered files.
    public open fun flush(): Result<Unit> = Result.Ok(Unit)

    public open fun seek(pos: SeekFrom): Result<ULong> {
        val NEG_OFF_ERR: Error = io.constError(io.ErrorKind.InvalidInput, "cannot seek to negative offset.")
        val off =
            when (pos) {
                is SeekFrom.Start -> {
                    val (p) = pos
                    p
                }
                is SeekFrom.End -> {
                    val (p1) = pos
                    when (val tryResult = when (val tryResult = this.fileAttr()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.size().checkedAddSigned(p1).okOr(NEG_OFF_ERR)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
                is SeekFrom.Current -> {
                    val (p2) = pos
                    when (val tryResult = when (val tryResult = this.tell()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.checkedAddSigned(p2).okOr(NEG_OFF_ERR)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
            }
        return this.value.setPosition(off).map({ off })
    }

    public open fun size(): Result<ULong>? =
        run {
            val matchVal = this.fileAttr()
            when (matchVal) { is Result.Ok<*> -> {
                val x = matchVal.value
                Result.Ok(x.size())
            }; is Result.Err<*> -> {
                val e = matchVal.error
                Result.Err(e)
            } }
        }

    public open fun tell(): Result<ULong> = this.value.position()

    public open fun duplicate(): Result<File> = unsupported()

    public open fun setPermissions(perm: FilePermissions): Result<Unit> = setPermInner(this.value, perm)

    public open fun setTimes(times: FileTimes): Result<Unit> = setTimesInner(this.value, times)

    public open fun fmt(f: StringBuilder) {
        val b = DebugStruct(f, "File")
        b.field("path", this.value.path())
        b.finish()
    }

    public companion object {
        public fun open(path: Path, opts: OpenOptions): Result<File> {
            if (!opts.isModeValid()) {
                return Result.Err(io.constError(io.ErrorKind.InvalidInput, "Invalid open options"))
            }
            if (opts.createNew && when (val tryResult = exists(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }) {
                return Result.Err(io.constError(io.ErrorKind.AlreadyExists, "File already exists"))
            }
            val f = when (val tryResult = File.fromPath(path, opts.mode, 0uL).map({ File(it) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            if (opts.truncate) {
                when (val tryResult = f.truncate(0)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
            if (opts.append) {
                when (val tryResult = f.seek(io.SeekFrom.End(0))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
            return Result.Ok(f)
        }
    }
}

public open class FileAttr(
    public var attr: ULong,
    public var size: ULong,
    public var fileTime: FileTimes,
    public var created: SystemTime?,
) {
    public open fun size(): ULong = this.size

    public open fun perm(): FilePermissions = FilePermissions.fromAttr(this.attr)

    public open fun fileType(): FileType = FileType.fromAttr(this.attr)

    public open fun modified(): Result<SystemTime> = (this.fileTime.modified?.let { Result.Ok(it) } ?: Result.Err(io.constError(io.ErrorKind.InvalidData, "modification time is not valid")))

    public open fun accessed(): Result<SystemTime> = (this.fileTime.accessed?.let { Result.Ok(it) } ?: Result.Err(io.constError(io.ErrorKind.InvalidData, "last access time is not valid")))

    public open fun created(): Result<SystemTime> = (this.created?.let { Result.Ok(it) } ?: Result.Err(io.constError(io.ErrorKind.InvalidData, "creation time is not valid")))

    public companion object {
        public fun fromUefi(info: UefiBox<Info>): FileAttr = FileAttr(attr = info.asPtr().attribute, size = info.asPtr().fileSize, fileTime = FileTimes(modified = uefiToSystemtime(info.asPtr().modificationTime), accessed = uefiToSystemtime(info.asPtr().lastAccessTime)), created = uefiToSystemtime(info.asPtr().createTime))
    }
}

public open class ReadDir(
    public var value: File,
) : Iterable<Result<DirEntry>> {
    public var field0: File
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): kotlin.collections.Iterator<Result<DirEntry>> = object : AbstractIterator<Result<DirEntry>>() {
        override fun computeNext() {
            when (val v = this@ReadDir.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun dir(): Result<Dir> {
        val options = OpenOptions.new()
        options.read(true)
        return Dir.open(this.value.path(), options)
    }

    public open fun fmt(f: StringBuilder) {
        val b = DebugStruct(f, "ReadDir")
        b.field("path", this.value.path())
        b.finish()
    }

    public open fun next(): Result<DirEntry>? =
        run {
            val matchVal = this.value.readDirEntry()
            when {
                matchVal is Result.Ok<*> && matchVal.value == null -> {
                    val None = (matchVal as Result.Ok<*>).value
                    null
                }
                matchVal is Result.Ok<*> && matchVal.value != null -> {
                    val temp = DirEntry.fromUefi(x, this.value.path())
                    // Ignore "." and "..". This is how ReadDir behaves in Unix.
                    if (temp.fileName == "." || temp.fileName == "..") {
                        this.next()
                    } else {
                        Result.Ok(temp)
                    }
                }
                else -> {
                    val e = (matchVal as Result.Err<*>).error
                    Result.Err(e)
                }
            }
        }
}

public open class DirEntry(
    public var attr: FileAttr,
    public var fileName: OsString,
    public var path: PathBuf,
) {
    public open fun path(): PathBuf = this.path.clone()

    public open fun fileName(): OsString = this.fileName.clone()

    public open fun metadata(): Result<FileAttr> = Result.Ok(this.attr.clone())

    public open fun fileType(): Result<FileType> = Result.Ok(this.attr.fileType())

    public companion object {
        public fun fromUefi(info: UefiBox<Info>, parent: Path): DirEntry {
            val fileName = fileNameFromUefi(info)
            val path = parent.join(fileName)
            return DirEntry(fileName = fileName, path = path, attr = FileAttr.fromUefi(info))
        }
    }
}

public open class OpenOptions(
    public var mode: ULong,
    public var append: Boolean,
    public var truncate: Boolean,
    public var createNew: Boolean,
) {
    public open fun read(read: Boolean) {
        if (read) {
            this.mode = this.mode or File.MODE_READ
        } else {
            this.mode = this.mode and File.MODE_READ.inv()
        }
    }

    public open fun write(write: Boolean) {
        if (write) {
            // Valid Combinations: Read, Read/Write, Read/Write/Create
            this.read(true)
            this.mode = this.mode or File.MODE_WRITE
        } else {
            this.mode = this.mode and File.MODE_WRITE.inv()
        }
    }

    public open fun append(append: Boolean) {
        // Docs state that `.write(true).append(true)` has the same effect as `.append(true)`
        if (append) {
            this.write(true)
        }
        this.append = append
    }

    public open fun truncate(truncate: Boolean) {
        this.truncate = truncate
    }

    public open fun create(create: Boolean) {
        if (create) {
            this.mode = this.mode or File.MODE_CREATE
        } else {
            this.mode = this.mode and File.MODE_CREATE.inv()
        }
    }

    public open fun createNew(createNew: Boolean) {
        this.createNew = createNew
        if (createNew) {
            this.create(true)
        }
    }

    public open fun isModeValid(): Boolean {
        // Valid Combinations: Read, Read/Write, Read/Write/Create
        return (this.mode == File.MODE_READ || this.mode == (File.MODE_READ or File.MODE_WRITE) || this.mode == (File.MODE_READ or File.MODE_WRITE or File.MODE_CREATE)).toUByte()
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<OpenOptions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<OpenOptions> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): OpenOptions =
                    OpenOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 9L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 10L),
                    )
                override fun writeNative(addr: Long, value: OpenOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.mode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 8L, value.append)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 9L, value.truncate)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 10L, value.createNew)
                }
                override fun readBytes(buf: ByteArray, offset: Int): OpenOptions =
                    OpenOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 8),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 9),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 10),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: OpenOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.mode)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 8, value.append)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 9, value.truncate)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 10, value.createNew)
                }
            }

        public fun new(): OpenOptions = OpenOptions(mode = 0uL, append = false, createNew = false, truncate = false)
    }
}

public open class FileTimes(
    public var accessed: SystemTime?,
    public var modified: SystemTime?,
) {
    public open fun setAccessed(t: SystemTime) {
        this.accessed = t
    }

    public open fun setModified(t: SystemTime) {
        this.modified = t
    }
}

// Bool indicates if file is readonly
public open class FilePermissions(
    public var value: Boolean,
) {
    public var field0: Boolean
        get() = value
        set(v) {
            value = v
        }

    public open fun readonly(): Boolean = this.value

    public open fun setReadonly(readonly: Boolean) {
        this.value = readonly
    }

    public open fun toAttr(): ULong =
        if (this.value) {
            readOnly
        } else {
            0uL
        }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FilePermissions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FilePermissions> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): FilePermissions =
                    FilePermissions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FilePermissions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FilePermissions =
                    FilePermissions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FilePermissions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.value)
                }
            }

        public fun fromAttr(attr: ULong): FilePermissions = FilePermissions(attr and readOnly != 0uL)
    }
}

// Bool indicates if directory
public open class FileType(
    public var value: Boolean,
) {
    public var field0: Boolean
        get() = value
        set(v) {
            value = v
        }

    public open fun isDir(): Boolean = this.value

    public open fun isFile(): Boolean = (!this.isDir()).toUByte()

    // Symlinks are not supported in UEFI
    public open fun isSymlink(): Boolean = false

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileType> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileType> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 1L
                override fun readNative(addr: Long): FileType =
                    FileType(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileType) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileType =
                    FileType(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileType) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.value)
                }
            }

        public fun fromAttr(attr: ULong): FileType = FileType(attr and directory != 0uL)
    }
}

public open class DirBuilder {
    public open fun mkdir(p: Path): Result<Unit> = io.github.kotlinmania.rustlang.std.sys.fs.uefi.uefifs.mkdir(p)

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

public fun readdir(p: Path): Result<ReadDir> {
    val path = when (val tryResult = absolute(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val f = when (val tryResult = File.fromPath(path, File.MODE_READ, 0uL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val fileInfo = when (val tryResult = f.fileInfo()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val fileAttr = FileAttr.fromUefi(fileInfo)
    return if (fileAttr.fileType().isDir()) {
        Result.Ok(ReadDir(f))
    } else {
        Result.Err(io.constError(io.ErrorKind.NotADirectory, "expected a directory but got a file"))
    }
}

public fun unlink(p: Path): Result<Unit> {
    val f = when (val tryResult = File.fromPath(p, File.MODE_READ or File.MODE_WRITE, 0uL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val fileInfo = when (val tryResult = f.fileInfo()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val fileAttr = FileAttr.fromUefi(fileInfo)
    return if (fileAttr.fileType().isFile()) {
        f.delete()
    } else {
        Result.Err(io.constError(io.ErrorKind.IsADirectory, "expected a file but got a directory"))
    }
}

/**
 * The implementation mirrors `mv` implementation in UEFI shell:
 * https://github.com/tianocore/edk2/blob/66346d5edeac2a00d3cf2f2f3b5f66d423c07b3e/ShellPkg/Library/UefiShellLevel2CommandsLib/Mv.c#L455
 *
 * In a nutshell we do the following:
 * 1. Convert both old and new paths to absolute paths.
 * 2. Check that both lie in the same disk.
 * 3. Construct the target path relative to the current disk root.
 * 4. Set this target path as the fileName in the fileInfo structure.
 */
public fun rename(old: Path, new: Path): Result<Unit> {
    val oldAbsolute = when (val tryResult = absolute(old)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val newAbsolute = when (val tryResult = absolute(new)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val oldComponents = oldAbsolute.components()
    val newComponents = newAbsolute.components()
    val oldDisk = oldComponents.next() ?: return Result.Err(io.constError(io.ErrorKind.InvalidInput, "Old path is not valid"))
    val newDisk = newComponents.next() ?: return Result.Err(io.constError(io.ErrorKind.InvalidInput, "New path is not valid"))
    // Ensure that paths are on the same device.
    if (oldDisk != newDisk) {
        return Result.Err(io.constError(io.ErrorKind.CrossesDevices, "Cannot rename across device"))
    }
    // Construct an path relative the current disk root.
    val newRelative = listOf(Component.RootDir).intoIter().chain(newComponents).collect<PathBuf>()
    val f = when (val tryResult = File.fromPath(old, File.MODE_READ or File.MODE_WRITE, 0uL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val fileInfo = when (val tryResult = f.fileInfo()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val newInfo = when (val tryResult = fileInfo.withFileName(newRelative.asOsStr())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return f.setFileInfo(newInfo)
}

public fun renameNoreplace(old: Path, new: Path): Result<Unit> = unsupported()

public fun setPerm(p: Path, perm: FilePermissions): Result<Unit> {
    // UEFI does not support symlinks
    return setPermNofollow(p, perm)
}

public fun setPermNofollow(p: Path, perm: FilePermissions): Result<Unit> {
    val f = when (val tryResult = File.fromPath(p, File.MODE_READ or File.MODE_WRITE, 0uL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return setPermInner(f, perm)
}

public fun setTimes(p: Path, times: FileTimes): Result<Unit> {
    // UEFI does not support symlinks
    return setTimesNofollow(p, times)
}

public fun setTimesNofollow(p: Path, times: FileTimes): Result<Unit> {
    val f = when (val tryResult = File.fromPath(p, File.MODE_READ or File.MODE_WRITE, 0uL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return setTimesInner(f, times)
}

public fun rmdir(p: Path): Result<Unit> {
    val f = when (val tryResult = File.fromPath(p, File.MODE_READ or File.MODE_WRITE, 0uL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val fileInfo = when (val tryResult = f.fileInfo()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val fileAttr = FileAttr.fromUefi(fileInfo)
    return if (fileAttr.fileType().isDir()) {
        f.delete()
    } else {
        Result.Err(io.constError(io.ErrorKind.NotADirectory, "expected a directory but got a file"))
    }
}

public fun exists(path: Path): Result<Boolean> {
    val f = File.fromPath(path, MODE_READ, 0uL)
    return when {
        f is Result.Ok<*> -> Result.Ok(true)
        f is Result.Err<*> && (f as Result.Err<*>).error.kind() == io.ErrorKind.NotFound -> {
            val e = (f as Result.Err<*>).error
            Result.Ok(false)
        }
        else -> {
            val e1 = (f as Result.Err<*>).error
            Result.Err(e1)
        }
    }
}

public fun readlink(p: Path): Result<PathBuf> = unsupported()

public fun symlink(original: Path, link: Path): Result<Unit> = unsupported()

public fun link(src: Path, dst: Path): Result<Unit> = unsupported()

public fun stat(p: Path): Result<FileAttr> {
    val f = when (val tryResult = File.fromPath(p, MODE_READ, 0uL)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val inf = when (val tryResult = f.fileInfo()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(FileAttr.fromUefi(inf))
}

public fun lstat(p: Path): Result<FileAttr> = stat(p)

public fun canonicalize(p: Path): Result<PathBuf> = absolute(p)

public fun setPermInner(f: File, perm: FilePermissions): Result<Unit> {
    val fileInfo = when (val tryResult = f.fileInfo()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    fileInfo.asMutPtr().attribute = (fileInfo.asPtr().attribute and FILE_PERMISSIONS_MASK.inv()) or perm.toAttr()
    return f.setFileInfo(fileInfo)
}

public fun setTimesInner(f: File, times: FileTimes): Result<Unit> {
    val fileInfo = when (val tryResult = f.fileInfo()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    when (val matchVal = times.accessed) {
        null -> {}
        else -> {
            val x = matchVal
            run {
                fileInfo.asMutPtr().lastAccessTime = systemtimeToUefi(x)
            }
        }
    }
    when (val matchVal1 = times.modified) {
        null -> {}
        else -> {
            val x1 = matchVal1
            run {
                fileInfo.asMutPtr().modificationTime = systemtimeToUefi(x1)
            }
        }
    }
    return f.setFileInfo(fileInfo)
}
