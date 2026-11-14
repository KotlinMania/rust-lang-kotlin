// port-lint: source sys/fs/solid.rs
package io.github.kotlinmania.rustlang.std.sys.fs.solid

import io.github.kotlinmania.libc.fuchsia.EINVAL
import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.alloc.io.read.defaultReadVectored
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.io.write.defaultWriteVectored
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.abi.O_APPEND
import io.github.kotlinmania.rustlang.std.abi.O_CREAT
import io.github.kotlinmania.rustlang.std.abi.O_EXCL
import io.github.kotlinmania.rustlang.std.abi.O_RDONLY
import io.github.kotlinmania.rustlang.std.abi.O_RDWR
import io.github.kotlinmania.rustlang.std.abi.O_TRUNC
import io.github.kotlinmania.rustlang.std.abi.O_WRONLY
import io.github.kotlinmania.rustlang.std.abi.SEEK_CUR
import io.github.kotlinmania.rustlang.std.abi.SEEK_END
import io.github.kotlinmania.rustlang.std.abi.SEEK_SET
import io.github.kotlinmania.rustlang.std.abi.S_IFBLK
import io.github.kotlinmania.rustlang.std.abi.S_IFCHR
import io.github.kotlinmania.rustlang.std.abi.S_IFDIR
import io.github.kotlinmania.rustlang.std.abi.S_IFIFO
import io.github.kotlinmania.rustlang.std.abi.S_IFMT
import io.github.kotlinmania.rustlang.std.abi.S_IFREG
import io.github.kotlinmania.rustlang.std.abi.S_IWRITE
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.fs.TryLockError
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.os.freebsd.raw.Stat
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.os.raw.CShort
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.fs.common.Dir
import io.github.kotlinmania.rustlang.std.sys.helpers.ignoreNotfound
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupportedErr
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.DT_BLK
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.DT_CHR
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.DT_DIR
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.DT_FIFO
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.DT_REG
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.Dirent
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.O_ACCMODE
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.SDir
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.SOLID_ERR_NOTFOUND
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsChmod
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsClose
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsCloseDir
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsFtell
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsLseek
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsMkdir
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsOpen
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsOpenDir
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsRead
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsReadDir
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsRename
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsStat
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsSync
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsUnlink
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.solidFsWrite
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

public typealias CIntNotMinusOne = NotAllOnes<CInt>

/**
 * A file descriptor.
 */
public open class FileDesc internal constructor(
    fdCtorArg: CIntNotMinusOne,
) {
    public var fd: CIntNotMinusOne = fdCtorArg
        internal set

    public open fun raw(): CInt = this.fd.asInner()

    public companion object {
        public operator fun invoke(
            fd: CIntNotMinusOne,
        ): FileDesc = FileDesc(
            fdCtorArg = fd,
        )

        public fun new(fd: CInt): FileDesc = FileDesc(fd = CIntNotMinusOne.new(fd).expect("fd != -1"))
    }
}

public open class File(
    public var fd: FileDesc,
) {
    public open fun fileAttr(): Result<FileAttr> = unsupported()

    public open fun fsync(): Result<Unit> = this.flush()

    public open fun datasync(): Result<Unit> = this.flush()

    public open fun lock(): Result<Unit> = unsupported()

    public open fun lockShared(): Result<Unit> = unsupported()

    public open fun tryLock(): Result<Unit, TryLockError> = Result.Err(TryLockError.Error(unsupportedErr()))

    public open fun tryLockShared(): Result<Unit, TryLockError> = Result.Err(TryLockError.Error(unsupportedErr()))

    public open fun unlock(): Result<Unit> = unsupported()

    public open fun truncate(size: ULong): Result<Unit> = unsupported()

    public open fun read(buf: MutableList<UByte>): Result<Int> {
        return run {
            val outNumBytes = MaybeUninit.uninit(ObjectSlotLayout<Result<Int>>())
            when (val tryResult = Error.SolidError.errIfNegative(solidFsRead(this.fd.raw(), buf.asMutPtr(), buf.size, outNumBytes.asMutPtr())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            Result.Ok(outNumBytes.assumeInit())
        }
    }

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> {
        return run {
            val len = cursor.capacity()
            val outNumBytes = MaybeUninit.uninit(ObjectSlotLayout<Result<Unit>>())
            when (val tryResult = Error.SolidError.errIfNegative(solidFsRead(this.fd.raw(), mutPtrFromRustCast(cursor.asMut().asMutPtr()), len, outNumBytes.asMutPtr())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            // Safety: `outNumBytes` is filled by the successful call to
            // `SolidFsRead`
            val numBytesRead = outNumBytes.assumeInit()
            // Safety: `numBytesRead` bytes were written to the unfilled
            // portion of the buffer
            cursor.advance(numBytesRead)
            Result.Ok(Unit)
        }
    }

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = defaultReadVectored({ buf -> this.read(buf) }, bufs)

    public open fun isReadVectored(): Boolean = false

    public open fun write(buf: List<UByte>): Result<Int> {
        return run {
            val outNumBytes = MaybeUninit.uninit(ObjectSlotLayout<Result<Int>>())
            when (val tryResult = Error.SolidError.errIfNegative(solidFsWrite(this.fd.raw(), buf.asPtr(), buf.size, outNumBytes.asMutPtr())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            Result.Ok(outNumBytes.assumeInit())
        }
    }

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = defaultWriteVectored({ buf -> this.write(buf) }, bufs)

    public open fun isWriteVectored(): Boolean = false

    public open fun flush(): Result<Unit> {
        when (val tryResult = Error.SolidError.errIfNegative(solidFsSync(this.fd.raw())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public open fun seek(pos: SeekFrom): Result<ULong> {
        val (whence, pos1) =
            when (pos) {
                // Casting to `Long` is fine, too large values will end up as
                // negative which will cause an error in `SolidFsLseek`.
                is SeekFrom.Start -> {
                    val (off) = pos
                    Pair(SEEK_SET, off.toLong())
                }
                is SeekFrom.End -> {
                    val (off1) = pos
                    Pair(SEEK_END, off1)
                }
                is SeekFrom.Current -> {
                    val (off2) = pos
                    Pair(SEEK_CUR, off2)
                }
            }
        when (val tryResult = Error.SolidError.errIfNegative(solidFsLseek(this.fd.raw(), pos1, whence)).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        // Get the new offset
        return this.tell()
    }

    public open fun size(): Result<ULong>? = null

    public open fun tell(): Result<ULong> {
        return run {
            val outOffset = MaybeUninit.uninit(ObjectSlotLayout<Result<ULong>>())
            when (val tryResult = Error.SolidError.errIfNegative(solidFsFtell(this.fd.raw(), outOffset.asMutPtr())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            Result.Ok(outOffset.assumeInit().toULong())
        }
    }

    public open fun duplicate(): Result<File> = unsupported()

    public open fun setPermissions(perm: FilePermissions): Result<Unit> = unsupported()

    public open fun setTimes(times: FileTimes): Result<Unit> = unsupported()

    public open fun drop() {
        solidFsClose(this.fd.raw())
    }

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "File").field("fd", this.fd.raw()).finish()
    }

    public companion object {
        public fun open(path: Path, opts: OpenOptions): Result<File> {
            val flags = when (val tryResult = opts.getAccessMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } or when (val tryResult = opts.getCreationMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } or (opts.customFlags and O_ACCMODE.inv())
            return run {
                var fd = MaybeUninit.uninit(ObjectSlotLayout<Result<File>>())
                when (val tryResult = Error.SolidError.errIfNegative(solidFsOpen(fd.asMutPtr(), when (val tryResult = cstr(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.asPtr(), flags)).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                Result.Ok(File(fd = FileDesc.new(fd.assumeInit())))
            }
        }
    }
}

public open class FileAttr(
    public var stat: Stat,
) {
    public open fun size(): ULong = this.stat.stSize.toULong()

    public open fun perm(): FilePermissions = FilePermissions(this.stat.stMode)

    public open fun fileType(): FileType = FileType(this.stat.stMode)

    public open fun modified(): Result<SystemTime> = Result.Ok(SystemTime.fromTimeT(this.stat.stMtime))

    public open fun accessed(): Result<SystemTime> = Result.Ok(SystemTime.fromTimeT(this.stat.stAtime))

    public open fun created(): Result<SystemTime> = Result.Ok(SystemTime.fromTimeT(this.stat.stCtime))
}

// all DirEntry's will have a reference to this struct
public open class InnerReadDir(
    public var dirp: SDir,
    public var root: PathBuf,
) {
    public open fun drop() {
        solidFsCloseDir(this.dirp)
    }
}

public open class ReadDir(
    public var inner: InnerReadDir,
) : Iterable<Result<DirEntry>> {
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
        return Dir.open(this.inner.root, options)
    }

    public open fun fmt(f: StringBuilder) {
        // This will only be called from std.fs.ReadDir, which will add a "ReadDir()" frame.
        // Thus the result will be e g ReadDir("/home")'
        f.append((this.inner.root).toString())
    }

    public open fun next(): Result<DirEntry>? {
        val entry =
            run {
                val outEntry = MaybeUninit.uninit(ObjectSlotLayout<Result<DirEntry>?>())
                run {
                    val matchVal = Error.SolidError.errIfNegative(solidFsReadDir(this.inner.dirp, outEntry.asMutPtr()))
                    when {
                        matchVal is Result.Ok<*> -> outEntry.assumeInit()
                        matchVal is Result.Err<*> && (matchVal as Result.Err<*>).error.asRaw() == SOLID_ERR_NOTFOUND -> {
                            val e = (matchVal as Result.Err<*>).error
                            return null
                        }
                        else -> {
                            val e1 = (matchVal as Result.Err<*>).error
                            return Result.Err(e1.asIoError())
                        }
                    }
                }
            }
        return (entry.dName[0] != 0).then({ Result.Ok(DirEntry(entry = entry, inner = this.inner)) })
    }
}

public open class DirEntry(
    public var entry: Dirent,
    public var inner: InnerReadDir,
) {
    public open fun path(): PathBuf = this.inner.root.join(OsStr.fromBytes(CStr.fromPtr(this.entry.dName.asPtr()).toBytes()))

    public open fun fileName(): OsString = OsStr.fromBytes(CStr.fromPtr(this.entry.dName.asPtr()).toBytes()).toOsString()

    public open fun metadata(): Result<FileAttr> = lstat(this.path())

    public open fun fileType(): Result<FileType> =
        when (this.entry.dType) {
            DT_CHR -> Result.Ok(FileType(S_IFCHR))
            DT_FIFO -> Result.Ok(FileType(S_IFIFO))
            DT_REG -> Result.Ok(FileType(S_IFREG))
            DT_DIR -> Result.Ok(FileType(S_IFDIR))
            DT_BLK -> Result.Ok(FileType(S_IFBLK))
            else -> lstat(this.path()).map({ m -> m.fileType() })
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
    public var customFlags: Int,
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

    public open fun customFlags(flags: Int) {
        this.customFlags = flags
    }

    public open fun mode(mode: UInt) {}

    public open fun getAccessMode(): Result<CInt> =
        run {
            val matchVal = Triple(this.read, this.write, this.append)
            when {
                matchVal.first == true && matchVal.second == false && matchVal.third == false -> Result.Ok(O_RDONLY)
                matchVal.first == false && matchVal.second == true && matchVal.third == false -> Result.Ok(O_WRONLY)
                matchVal.first == true && matchVal.second == true && matchVal.third == false -> Result.Ok(O_RDWR)
                matchVal.first == false && matchVal.third == true -> Result.Ok(O_WRONLY or O_APPEND)
                matchVal.first == true && matchVal.third == true -> Result.Ok(O_RDWR or O_APPEND)
                else -> Result.Err(io.Error.fromRawOsError(EINVAL))
            }
        }

    public open fun getCreationMode(): Result<CInt> {
        run {
            val matchVal = Pair(this.write, this.append)
            when {
                matchVal.first == true && matchVal.second == false -> {}
                matchVal.first == false && matchVal.second == false -> {
                    if (this.truncate || this.create || this.createNew) {
                        return Result.Err(io.Error.fromRawOsError(EINVAL))
                    }
                }
                else -> {
                    if (this.truncate && !this.createNew) {
                        return Result.Err(io.Error.fromRawOsError(EINVAL))
                    }
                }
            }
        }
        return Result.Ok(run {
            val matchVal1 = Triple(this.create, this.truncate, this.createNew)
            when {
                matchVal1.first == false && matchVal1.second == false && matchVal1.third == false -> 0
                matchVal1.first == true && matchVal1.second == false && matchVal1.third == false -> O_CREAT
                matchVal1.first == false && matchVal1.second == true && matchVal1.third == false -> O_TRUNC
                matchVal1.first == true && matchVal1.second == true && matchVal1.third == false -> O_CREAT or O_TRUNC
                else -> O_CREAT or O_EXCL
            }
        })
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<OpenOptions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<OpenOptions> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 12L
                override fun readNative(addr: Long): OpenOptions =
                    OpenOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 1L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 2L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 3L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 4L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 5L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: OpenOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.read)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.write)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 2L, value.append)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 3L, value.truncate)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 4L, value.create)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 5L, value.createNew)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 8L, value.customFlags)
                }
                override fun readBytes(buf: ByteArray, offset: Int): OpenOptions =
                    OpenOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 2),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 3),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 4),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readBytes(buf, offset + 5),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: OpenOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.read)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.write)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 2, value.append)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 3, value.truncate)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 4, value.create)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 5, value.createNew)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.customFlags)
                }
            }

        public fun new(): OpenOptions = OpenOptions(read = false, write = false, append = false, truncate = false, create = false, createNew = false, customFlags = 0)
    }
}

public open class FileTimes {
    public open fun setAccessed(t: SystemTime) {}

    public open fun setModified(t: SystemTime) {}

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileTimes> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileTimes> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): FileTimes =
                    FileTimes()
                override fun writeNative(addr: Long, value: FileTimes) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileTimes =
                    FileTimes()
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileTimes) {
                }
            }
    }
}

public open class FilePermissions(
    public var value: CShort,
) {
    public var field0: CShort
        get() = value
        set(v) {
            value = v
        }

    public open fun readonly(): Boolean = (((this.value.toInt() and S_IWRITE.toInt()).toShort()) == 0.toShort()).toUByte()

    public open fun setReadonly(readonly: Boolean) {
        if (readonly) {
            this.value = this.value and S_IWRITE.inv()
        } else {
            this.value = this.value or S_IWRITE
        }
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FilePermissions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FilePermissions> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): FilePermissions =
                    FilePermissions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FilePermissions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FilePermissions =
                    FilePermissions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FilePermissions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class FileType(
    public var value: CShort,
) {
    public var field0: CShort
        get() = value
        set(v) {
            value = v
        }

    public open fun isDir(): Boolean = this.isMatch(S_IFDIR)

    public open fun isFile(): Boolean = this.isMatch(S_IFREG)

    public open fun isSymlink(): Boolean = false

    public open fun isMatch(mode: CShort): Boolean = ((this.value.toInt() and S_IFMT.toInt()).toShort() == mode).toUByte()

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileType> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileType> {
                override val alignBytes: Long = 2L
                override val sizeBytes: Long = 2L
                override fun readNative(addr: Long): FileType =
                    FileType(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileType) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileType =
                    FileType(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileType) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I16).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public open class DirBuilder {
    public open fun mkdir(p: Path): Result<Unit> {
        when (val tryResult = Error.SolidError.errIfNegative(solidFsMkdir(when (val tryResult = cstr(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.asPtr())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
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

public fun readdir(p: Path): Result<ReadDir> {
    return run {
        val dir = MaybeUninit.uninit(ObjectSlotLayout<Result<ReadDir>>())
        when (val tryResult = Error.SolidError.errIfNegative(solidFsOpenDir(when (val tryResult = cstr(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.asPtr(), dir.asMutPtr())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        val inner = InnerReadDir(dirp = dir.assumeInit(), root = p.toOwned())
        Result.Ok(ReadDir(inner = inner))
    }
}

public fun cstr(path: Path): Result<CString> {
    // Apply the thread-safety wrapper
    val SAFE_PREFIX: List<UByte> = "\\TS".encodeToByteArray().map { it.toUByte() }
    val path1 = path.asOsStr().encodeToByteArray().map { it.toUByte() }
    if (!path1.startsWith("\\".encodeToByteArray())) {
        // Relative paths aren't supported
        return Result.Err(constError(unsupported, "relative path is not supported on this platform"))
    }
    val wrappedPath = listOf(SAFE_PREFIX, path1, listOf(0)).concat()
    return CString.fromVecWithNul(wrappedPath).mapErr({
        constError(io.ErrorKind.InvalidInput, "path provided contains a nul byte")
    })
}

public fun unlink(p: Path): Result<Unit> {
    return if (when (val tryResult = stat(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.fileType().isDir()) {
        Result.Err(io.constError(io.ErrorKind.IsADirectory, "is a directory"))
    } else {
        when (val tryResult = Error.SolidError.errIfNegative(solidFsUnlink(when (val tryResult = cstr(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.asPtr())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        Result.Ok(Unit)
    }
}

public fun rename(old: Path, new: Path): Result<Unit> {
    when (val tryResult = Error.SolidError.errIfNegative(solidFsRename(when (val tryResult = cstr(old)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.asPtr(), when (val tryResult = cstr(new)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.asPtr())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(Unit)
}

public fun renameNoreplace(old: Path, new: Path): Result<Unit> = unsupported()

public fun setPerm(p: Path, perm: FilePermissions): Result<Unit> {
    // Solid does not support symlinks
    return setPermNofollow(p, perm)
}

public fun setPermNofollow(p: Path, perm: FilePermissions): Result<Unit> {
    when (val tryResult = Error.SolidError.errIfNegative(solidFsChmod(when (val tryResult = cstr(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.asPtr(), perm.value.into())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(Unit)
}

public fun setTimes(p: Path, times: FileTimes): Result<Unit> = unsupported()

public fun setTimesNofollow(p: Path, times: FileTimes): Result<Unit> = unsupported()

public fun rmdir(p: Path): Result<Unit> {
    return if (when (val tryResult = stat(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.fileType().isDir()) {
        when (val tryResult = Error.SolidError.errIfNegative(solidFsUnlink(when (val tryResult = cstr(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.asPtr())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        Result.Ok(Unit)
    } else {
        Result.Err(io.constError(io.ErrorKind.NotADirectory, "not a directory"))
    }
}

public fun removeDirAll(path: Path): Result<Unit> {
    for (child in when (val tryResult = readdir(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }) {
        val result: Result<Unit> =
            run {
                val child1 = when (val tryResult = child) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                val childType = when (val tryResult = child1.fileType()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                if (childType.isDir()) {
                    when (val tryResult = removeDirAll(child1.path())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                } else {
                    when (val tryResult = unlink(child1.path())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
            }
        // ignore internal NotFound errors
        if (result is Result.Err<*> && err.kind() != io.ErrorKind.NotFound) {
            return result
        }
    }
    return ignoreNotfound(rmdir(path))
}

public fun readlink(p: Path): Result<PathBuf> {
    // This target doesn't support symlinks
    when (val tryResult = stat(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Err(io.constError(io.ErrorKind.InvalidInput, "not a symbolic link"))
}

public fun symlink(original: Path, link: Path): Result<Unit> {
    // This target doesn't support symlinks
    return unsupported()
}

public fun link(src: Path, dst: Path): Result<Unit> {
    // This target doesn't support symlinks
    return unsupported()
}

public fun stat(p: Path): Result<FileAttr> {
    // This target doesn't support symlinks
    return lstat(p)
}

public fun lstat(p: Path): Result<FileAttr> {
    return run {
        val outStat = MaybeUninit.uninit(ObjectSlotLayout<Result<FileAttr>>())
        when (val tryResult = Error.SolidError.errIfNegative(solidFsStat(when (val tryResult = cstr(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.asPtr(), outStat.asMutPtr())).mapErr({ e -> e.asIoError() })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        Result.Ok(FileAttr(stat = outStat.assumeInit()))
    }
}

public fun canonicalize(p: Path): Result<PathBuf> = unsupported()

public fun copy(from: Path, to: Path): Result<ULong> {
    val reader = when (val tryResult = File.open(from)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val writer = when (val tryResult = File.create(to)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return io.copy(reader, writer)
}
