// port-lint: source sys/fs/vexos.rs
package io.github.kotlinmania.rustlang.std.sys.fs.vexos

import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadBuf
import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadVectored
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.io.write.defaultWriteVectored
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.fs.TryLockError
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.fs.vexos.unsupportedfs.Dir
import io.github.kotlinmania.rustlang.std.sys.fs.vexos.unsupportedfs.FileTimes
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runPathWithCstr
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupportedErr
import io.github.kotlinmania.rustlang.std.sys.time.sgx.SystemTime
import io.github.kotlinmania.vexsdk.Fresult
import io.github.kotlinmania.vexsdk.vexFileClose
import io.github.kotlinmania.vexsdk.vexFileOpen
import io.github.kotlinmania.vexsdk.vexFileOpenCreate
import io.github.kotlinmania.vexsdk.vexFileOpenWrite
import io.github.kotlinmania.vexsdk.vexFileRead
import io.github.kotlinmania.vexsdk.vexFileSeek
import io.github.kotlinmania.vexsdk.vexFileSize
import io.github.kotlinmania.vexsdk.vexFileStatus
import io.github.kotlinmania.vexsdk.vexFileSync
import io.github.kotlinmania.vexsdk.vexFileTell
import io.github.kotlinmania.vexsdk.vexFileWrite

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

/**
 * VEXos file descriptor.
 *
 * This stores an opaque pointer to a [FatFs file object structure] managed by VEXos
 * representing an open file on disk.
 *
 * [FatFs file object structure]: https://github.com/Xilinx/embeddedsw/blob/master/lib/swServices/xilffs/src/include/ff.h?rgh-link-date=2025-09-23T20%3A03%3A43Z#L215
 *
 * # Safety
 *
 * Since this platform uses a pointer to to an internal filesystem structure with a scope
 * associated with it (rather than a UNIX-style file descriptor table), care must be taken to
 * ensure that the pointer held by `FileDesc` is valid for as long as it exists.
 */
public open class FileDesc internal constructor(
    valueCtorArg: MutPtr,
) {
    public var value: MutPtr = valueCtorArg
        internal set

    public var field0: MutPtr
        get() = value
        set(v) {
            value = v
        }

    // SAFETY: VEXos's FDs can be used on a thread other than the one they were created on.

    // SAFETY: We assume an environment without threads (i.e. no RTOS).
    // (If there were threads, it is possible that a mutex would be required.)

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileDesc> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileDesc> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): FileDesc =
                    FileDesc(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileDesc) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileDesc =
                    FileDesc(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileDesc) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.value)
                }
            }

        public operator fun invoke(
            value: MutPtr,
        ): FileDesc = FileDesc(
            valueCtorArg = value,
        )
    }
}

public open class File(
    public var fd: FileDesc,
) {
    public open fun fileAttr(): Result<FileAttr> {
        // `vexFileSize` returns -1 upon error, so u64.tryFrom will fail on error.
        return when (val matchVal = ULong.tryFrom(vexFileSize(this.fd.value))) {
            is Ok -> {
                val size = matchVal.value
                Result.Ok(FileAttr.File(size = size))
            }
            else -> {
                Result.Err(io.constError(io.ErrorKind.InvalidData, "failed to get file size"))
            }
        }
    }

    public open fun fsync(): Result<Unit> = this.flush()

    public open fun datasync(): Result<Unit> = this.flush()

    public open fun lock(): Result<Unit> = unsupported()

    public open fun lockShared(): Result<Unit> = unsupported()

    public open fun tryLock(): Result<Unit, TryLockError> = Result.Err(TryLockError.Error(unsupportedErr()))

    public open fun tryLockShared(): Result<Unit, TryLockError> = Result.Err(TryLockError.Error(unsupportedErr()))

    public open fun unlock(): Result<Unit> = unsupported()

    public open fun truncate(size: ULong): Result<Unit> = unsupported()

    public open fun read(buf: MutableList<UByte>): Result<Int> {
        val len = buf.size.toUInt()
        val bufPtr = buf.asMutPtr()
        val read = vexFileRead(MutPtr((bufPtr).rawAddress), 1, len, this.fd.value)
        return if (read < 0) {
            Result.Err(io.constError(io.ErrorKind.Other, "could not read from file"))
        } else {
            Result.Ok(read)
        }
    }

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = defaultReadVectored({ b -> this.read(b) }, bufs)

    public open fun isReadVectored(): Boolean = false

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = defaultReadBuf({ b -> this.read(b) }, cursor)

    public open fun write(buf: List<UByte>): Result<Int> {
        val len = buf.size.toUInt()
        val bufPtr = buf.asPtr()
        val written = vexFileWrite(MutPtr((MutPtr((bufPtr).rawAddress)).rawAddress), 1, len, this.fd.value)
        return if (written < 0) {
            Result.Err(io.constError(io.ErrorKind.Other, "could not write to file"))
        } else {
            Result.Ok(written.toInt())
        }
    }

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = defaultWriteVectored({ b -> this.write(b) }, bufs)

    public open fun isWriteVectored(): Boolean = false

    public open fun flush(): Result<Unit> {
        run {
            // SAFETY: `self.fd` contains a valid pointer to `FIL` for this struct's scope.
            vexFileSync(this.fd.value)
        }
        return Result.Ok(Unit)
    }

    public open fun tell(): Result<ULong> {
        // SAFETY: `self.fd` contains a valid pointer to `FIL` for this struct's scope.
        val position = vexFileTell(this.fd.value)
        return position.tryInto().mapErr({
            io.constError(io.ErrorKind.InvalidData, "failed to get current location in file")
        })
    }

    public open fun size(): Result<ULong>? = null

    public open fun seek(pos: SeekFrom): Result<ULong> {
        val SEEK_SET: Int = 0
        val SEEK_CUR: Int = 1
        val SEEK_END: Int = 2
        fun <T> tryConvertOffset(offset: T): Result<UInt> =
            offset.tryInto().mapErr({
                io.constError(io.ErrorKind.InvalidInput, "cannot seek to an offset too large to fit in a 32 bit integer")
            })
        // SAFETY: `self.fd` contains a valid pointer to `FIL` for this struct's scope.
        when (pos) {
            is SeekFrom.Start -> {
                val (offset) = pos
                when (val tryResult = mapFresult(vexFileSeek(this.fd.value, when (val tryResult = tryConvertOffset(offset)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, SEEK_SET))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            }
            is SeekFrom.End -> {
                val (offset1) = pos
                if (offset1 >= 0) {
                    when (val tryResult = mapFresult(vexFileSeek(this.fd.value, when (val tryResult = tryConvertOffset(offset1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, SEEK_END))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                } else {
                    // `vexFileSeek` does not support seeking with negative offset, meaning
                    // we have to calculate the offset from the end of the file ourselves.
                    // Seek to the end of the file to get the end position in the open buffer.
                    when (val tryResult = mapFresult(vexFileSeek(this.fd.value, 0, SEEK_END))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    val endPosition = when (val tryResult = this.tell()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    when (val tryResult = mapFresult(vexFileSeek(this.fd.value, when (val tryResult = tryConvertOffset(endPosition.toLong() + offset1)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, SEEK_SET))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
            }
            is SeekFrom.Current -> {
                val (offset2) = pos
                if (offset2 >= 0) {
                    when (val tryResult = mapFresult(vexFileSeek(this.fd.value, when (val tryResult = tryConvertOffset(offset2)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, SEEK_CUR))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                } else {
                    // `vexFileSeek` does not support seeking with negative offset, meaning
                    // we have to calculate the offset from the stream position ourselves.
                    when (val tryResult = mapFresult(vexFileSeek(this.fd.value, when (val tryResult = tryConvertOffset(((when (val tryResult = this.tell()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }).toLong()) + offset2)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }, SEEK_SET))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
            }
        }
        return Result.Ok(when (val tryResult = this.tell()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
    }

    public open fun duplicate(): Result<File> = unsupported()

    public open fun setPermissions(perm: FilePermissions): Result<Unit> = unsupported()

    public open fun setTimes(times: FileTimes): Result<Unit> = unsupported()

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "File").field("fd", this.fd.value).finish()
    }

    public open fun drop() {
        vexFileClose(this.fd.value)
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<File> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<File> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): File =
                    File(
                        (FileDesc.typeLayout()).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: File) {
                    (FileDesc.typeLayout()).writeNative(addr, value.fd)
                }
                override fun readBytes(buf: ByteArray, offset: Int): File =
                    File(
                        (FileDesc.typeLayout()).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: File) {
                    (FileDesc.typeLayout()).writeBytes(buf, offset, value.fd)
                }
            }

        public fun open(path: Path, opts: OpenOptions): Result<File> {
            return runPathWithCstr(path, closure@{ path1 ->
                // Enforce the invariants of `createNew`/`create`.
                //
                // Since VEXos doesn't have anything akin to POSIX's `oflags`, we need to enforce
                // the requirements that `createNew` can't have an existing file and `!create`
                // doesn't create a file ourselves.
                if (!opts.read && (opts.write || opts.append) && (opts.createNew || !opts.create)) {
                    val status = vexFileStatus(path1.asPtr())
                    if (opts.createNew && status != 0) {
                        return@closure Result.Err(io.constError(io.ErrorKind.AlreadyExists, "file exists"))
                    } else if (!opts.create && status == 0) {
                        return@closure Result.Err(io.constError(io.ErrorKind.NotFound, "no such file or directory"))
                    }
                }
                val file =
                    when {
                        // read + write - unsupported
                        opts.read == true && opts.write == true -> {
                            return@closure Result.Err(io.constError(io.ErrorKind.InvalidInput, "opening files with read and write access is unsupported on this target"))
                        }
                        // read
                        opts.read == true && opts.write == false && opts.truncate == false && opts.create == false && opts.createNew == false -> vexFileOpen(path1.asPtr(), "".asPtr())
                        // append
                        opts.read == false && opts.append == true && opts.truncate == false -> vexFileOpenWrite(path1.asPtr())
                        // write
                        opts.read == false && opts.write == true && opts.append == false -> {
                            val truncate = opts.truncate
                            if (truncate) {
                                vexFileOpenCreate(path1.asPtr())
                            } else {
                                // Open in append, but jump to the start of the file.
                                val fd = vexFileOpenWrite(path1.asPtr())
                                vexFileSeek(fd, 0, 0)
                                fd
                            }
                        }
                        else -> {
                            return@closure Result.Err(io.constError(io.ErrorKind.InvalidInput, "invalid argument"))
                        }
                    }
                if (file.isNull()) {
                    Result.Err(io.constError(io.ErrorKind.NotFound, "could not open file"))
                } else {
                    Result.Ok(File(fd = FileDesc(file)))
                }
            })
        }
    }
}

public sealed class FileAttr {
    public data object Dir : FileAttr()
    public data class File(public var size: ULong) : FileAttr()

    public open fun size(): ULong =
        when (this) {
            else -> {
                val size = this.size
                size
            }
            FileAttr.Dir -> 0uL
        }

    public open fun perm(): FilePermissions = FilePermissions()

    public open fun fileType(): FileType =
        FileType(isDir = when (this) {
            FileAttr.Dir -> true
            else -> false
        })

    public open fun modified(): Result<SystemTime> = unsupported()

    public open fun accessed(): Result<SystemTime> = unsupported()

    public open fun created(): Result<SystemTime> = unsupported()
}

public open class ReadDir(
    public var value: Nothing,
) : Iterable<Result<DirEntry>> {
    public var field0: Nothing
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

    public open fun dir(): Result<Dir> = this.value

    public open fun fmt(f: StringBuilder) {
        this.value
    }

    public open fun next(): Result<DirEntry>? = this.value

    public open fun drop() {
        // Dummy Drop so that `needsDrop<ReadDir>()` is true independent of platform.
        this.value
    }
}

public open class DirEntry(
    public var path: PathBuf,
) {
    public open fun path(): PathBuf = this.path.clone()

    public open fun fileName(): OsString = this.path.fileName().unwrapOrDefault().into()

    public open fun metadata(): Result<FileAttr> = stat(this.path)

    public open fun fileType(): Result<FileType> {
        return Result.Ok(when (val tryResult = this.metadata()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.fileType())
    }
}

public open class OpenOptions(
    public var read: Boolean,
    public var write: Boolean,
    public var append: Boolean,
    public var truncate: Boolean,
    public var create: Boolean,
    public var createNew: Boolean,
) {
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

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<OpenOptions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<OpenOptions> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 6L
                override fun readNative(addr: Long): OpenOptions =
                    OpenOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 1L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 2L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 3L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 4L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 5L),
                    )
                override fun writeNative(addr: Long, value: OpenOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.read)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.write)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 2L, value.append)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 3L, value.truncate)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 4L, value.create)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 5L, value.createNew)
                }
                override fun readBytes(buf: ByteArray, offset: Int): OpenOptions =
                    OpenOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 2),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 3),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 4),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 5),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: OpenOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.read)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.write)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 2, value.append)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 3, value.truncate)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 4, value.create)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 5, value.createNew)
                }
            }

        public fun new(): OpenOptions = OpenOptions(read = false, write = false, append = false, truncate = false, create = false, createNew = false)
    }
}

public open class FilePermissions {
    public open fun readonly(): Boolean = false

    public open fun setReadonly(readonly: Boolean) {
        error("Permissions do not exist")
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FilePermissions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FilePermissions> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): FilePermissions =
                    FilePermissions()
                override fun writeNative(addr: Long, value: FilePermissions) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): FilePermissions =
                    FilePermissions()
                override fun writeBytes(buf: ByteArray, offset: Int, value: FilePermissions) {
                }
            }
    }
}

public open class FileType(
    public var isDir: Boolean,
) {
    public open fun isDir(): Boolean = this.isDir

    public open fun isFile(): Boolean = (!this.isDir).toUByte()

    public open fun isSymlink(): Boolean {
        // No symlinks in VEXos - entries are either files or directories.
        return false
    }

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
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.isDir)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileType =
                    FileType(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileType) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.isDir)
                }
            }
    }
}

public fun readdir(p: Path): Result<ReadDir> {
    // While there *is* a userspace function for reading file directories,
    // the necessary implementation cannot currently be done cleanly, as
    // VEXos does not expose directory length to user programs.
    //
    // This means that we would need to create a large fixed-length buffer
    // and hope that the folder's contents didn't exceed that buffer's length,
    // which obviously isn't behavior we want to rely on in the standard library.
    return unsupported()
}

public fun setPerm(p: Path, perm: FilePermissions): Result<Unit> = unsupported()

public fun setPermNofollow(p: Path, perm: FilePermissions): Result<Unit> = unsupported()

public fun setTimes(p: Path, times: FileTimes): Result<Unit> = unsupported()

public fun setTimesNofollow(p: Path, times: FileTimes): Result<Unit> = unsupported()

public fun exists(path: Path): Result<Boolean> = runPathWithCstr(path, { path1 -> Result.Ok(vexFileStatus(path1.asPtr()) != 0) })

public fun stat(p: Path): Result<FileAttr> {
    // `vexFileStatus` returns 3 if the given path is a directory, 1 if the path is a
    // file, or 0 if no such path exists.
    val FILE_STATUS_DIR: UInt = 3u
    return runPathWithCstr(p, closure1@{ cPath ->
        val fileType = vexFileStatus(cPath.asPtr())
        // We can't get the size if its a directory because we cant open it as a file
        if (fileType == FILE_STATUS_DIR) {
            Result.Ok(FileAttr.Dir)
        } else {
            val opts = OpenOptions.new()
            opts.read(true)
            val file = when (val tryResult = File.open(p, opts)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure1 Result.Err(tryResult.error) }
            file.fileAttr()
        }
    })
}

public fun lstat(p: Path): Result<FileAttr> {
    // Symlinks aren't supported in this filesystem
    return stat(p)
}

// Cannot use `copy` from `common` here, since `File.setPermissions` is unsupported on this target.
public fun copy(from: Path, to: Path): Result<ULong> {
    // NOTE: If `from` is a directory, this call should fail due to vexFileOpen* returning null.
    val reader = when (val tryResult = File.open(from)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val writer = when (val tryResult = File.create(to)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return io.copy(reader, writer)
}

public fun mapFresult(fresult: Fresult): Result<Unit> {
    // VEX uses a derivative of FatFs (Xilinx's xilffs library) for filesystem operations.
    return when (fresult) {
        Fresult.FR_OK -> Result.Ok(Unit)
        Fresult.FR_DISK_ERR -> Result.Err(io.constError(io.ErrorKind.InputOutputError, "internal function reported an unrecoverable hard error"))
        Fresult.FR_INT_ERR -> Result.Err(io.constError(io.ErrorKind.Uncategorized, "internal error in filesystem runtime"))
        Fresult.FR_NOT_READY -> Result.Err(io.constError(io.ErrorKind.Uncategorized, "the storage device could not be prepared to work"))
        Fresult.FR_NO_FILE -> Result.Err(io.constError(io.ErrorKind.NotFound, "could not find the file in the directory"))
        Fresult.FR_NO_PATH -> Result.Err(io.constError(io.ErrorKind.NotFound, "a directory in the path name could not be found"))
        Fresult.FR_INVALID_NAME -> Result.Err(io.constError(io.ErrorKind.InvalidInput, "the given string is invalid as a path name"))
        Fresult.FR_DENIED -> Result.Err(io.constError(io.ErrorKind.PermissionDenied, "the required access for this operation was denied"))
        Fresult.FR_EXIST -> Result.Err(io.constError(io.ErrorKind.AlreadyExists, "an object with the same name already exists in the directory"))
        Fresult.FR_INVALID_OBJECT -> Result.Err(io.constError(io.ErrorKind.Uncategorized, "invalid or null file/directory object"))
        Fresult.FR_WRITE_PROTECTED -> Result.Err(io.constError(io.ErrorKind.PermissionDenied, "a write operation was performed on write-protected media"))
        Fresult.FR_INVALID_DRIVE -> Result.Err(io.constError(io.ErrorKind.InvalidInput, "an invalid drive number was specified in the path name"))
        Fresult.FR_NOT_ENABLED -> Result.Err(io.constError(io.ErrorKind.Uncategorized, "work area for the logical drive has not been registered"))
        Fresult.FR_NO_FILESYSTEM -> Result.Err(io.constError(io.ErrorKind.Uncategorized, "valid FAT volume could not be found on the drive"))
        Fresult.FR_MKFS_ABORTED -> Result.Err(io.constError(io.ErrorKind.Uncategorized, "failed to create filesystem volume"))
        Fresult.FR_TIMEOUT -> Result.Err(io.constError(io.ErrorKind.TimedOut, "the function was canceled due to a timeout of thread-safe control"))
        Fresult.FR_LOCKED -> Result.Err(io.constError(io.ErrorKind.Uncategorized, "the operation to the object was rejected by file sharing control"))
        Fresult.FR_NOT_ENOUGH_CORE -> {
            Result.Err(io.constError(io.ErrorKind.OutOfMemory, "not enough memory for the operation"))
        }
        Fresult.FR_TOO_MANY_OPEN_FILES -> Result.Err(io.constError(io.ErrorKind.TooManyOpenFiles, "maximum number of open files has been reached"))
        Fresult.FR_INVALID_PARAMETER -> {
            Result.Err(io.constError(io.ErrorKind.InvalidInput, "a given parameter was invalid"))
        }
        else -> error("unreachable")
    }
}
