// port-lint: source sys/fs/hermit.rs
package io.github.kotlinmania.rustlang.std.sys.fs.hermit

import io.github.kotlinmania.hermitabi.Stat
import io.github.kotlinmania.hermitabi.getdents64
import io.github.kotlinmania.hermitabi.open
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.fs.TryLockError
import io.github.kotlinmania.rustlang.std.mem
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.Dirent64
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.O_APPEND
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.O_CREAT
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.O_DIRECTORY
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.O_EXCL
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.O_RDONLY
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.O_RDWR
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.O_TRUNC
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.O_WRONLY
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.S_IFDIR
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.S_IFLNK
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.S_IFMT
import io.github.kotlinmania.rustlang.std.os.hermit.hermitabi.S_IFREG
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.AsInnerMut
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fd.hermit.FileDesc
import io.github.kotlinmania.rustlang.std.sys.fs.common.Dir
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runPathWithCstr
import io.github.kotlinmania.rustlang.std.sys.io.DEFAULT_BUF_SIZE
import io.github.kotlinmania.rustlang.std.sys.osstr.bytes.Slice
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupportedErr
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.DT_DIR
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.DT_LNK
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.DT_REG
import io.github.kotlinmania.rustlang.std.sys.pal.solid.abi.DT_UNKNOWN
import io.github.kotlinmania.rustlang.std.sys.time.sgx.SystemTime
import io.github.kotlinmania.rustlang.std.os.freebsd.raw.Stat as StatStruct

public open class File(
    public var value: FileDesc,
) : AsInner<FileDesc>, AsInnerMut<FileDesc>, IntoInner<FileDesc>, FromInner<FileDesc, File>, AsFd, AsRawFd, IntoRawFd, FromRawFd<File, Any?, Any> {
    public var field0: FileDesc
        get() = value
        set(v) {
            value = v
        }

    public open fun fileAttr(): Result<FileAttr> {
        val statVal: StatStruct?  = null
        when (val tryResult = this.value.fstat(statVal)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(FileAttr.fromStat(statVal))
    }

    public open fun fsync(): Result<Unit> {
        when (val tryResult = cvt(io.github.kotlinmania.hermitabi.fsync(this.asRawFd()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public open fun datasync(): Result<Unit> = this.fsync()

    public open fun lock(): Result<Unit> = unsupported()

    public open fun lockShared(): Result<Unit> = unsupported()

    public open fun tryLock(): Result<Unit, TryLockError> = Result.Err(TryLockError.Error(unsupportedErr()))

    public open fun tryLockShared(): Result<Unit, TryLockError> = Result.Err(TryLockError.Error(unsupportedErr()))

    public open fun unlock(): Result<Unit> = unsupported()

    public open fun truncate(size: ULong): Result<Unit> = unsupported()

    public open fun read(buf: MutableList<UByte>): Result<Int> = this.value.read(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = this.value.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.value.isReadVectored()

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = this.value.readBuf(cursor)

    public open fun write(buf: List<UByte>): Result<Int> = this.value.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = this.value.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public open fun flush(): Result<Unit> = Result.Ok(Unit)

    public open fun seek(pos: SeekFrom): Result<ULong> = this.value.seek(pos)

    public open fun size(): Result<ULong>? = null

    public open fun tell(): Result<ULong> = this.value.tell()

    public open fun duplicate(): Result<File> = unsupported()

    public open fun setPermissions(perm: FilePermissions): Result<Unit> = unsupported()

    public open fun setTimes(times: FileTimes): Result<Unit> = unsupported()

    public override fun asInner(): FileDesc = this.value

    public override fun asInnerMut(): FileDesc = this.value

    public override fun intoInner(): FileDesc = this.value

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asRawFd(): RawFd = this.value.asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoRawFd()

    public companion object {
        public fun open(path: Path, opts: OpenOptions): Result<File> = runPathWithCstr(path, { path1 -> File.openC(path1, opts) })

        public fun openC(path: CStr, opts: OpenOptions): Result<File> {
            var flags = when (val tryResult = opts.getAccessMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            flags = flags or when (val tryResult = opts.getCreationMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            val mode: Any?
            if (flags and O_CREAT == O_CREAT) {
                mode = opts.mode
            } else {
                mode = 0
            }
            val fd = when (val tryResult = cvt(open(path.asPtr(), flags, mode))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(File(FileDesc.fromRawFd(fd)))
        }

        public fun fromInner(fileDesc: FileDesc): File = File(fileDesc)

        public fun fromRawFd(rawFd: RawFd): File {
            val fileDesc = FileDesc.fromRawFd(rawFd)
            return File(fileDesc)
        }
    }
}

public open class FileAttr(
    public var statVal: StatStruct,
) {
    public open fun modified(): Result<SystemTime> = SystemTime.new(this.statVal.stMtim.tvSec, this.statVal.stMtim.tvNsec.into())

    public open fun accessed(): Result<SystemTime> = SystemTime.new(this.statVal.stAtim.tvSec, this.statVal.stAtim.tvNsec.into())

    public open fun created(): Result<SystemTime> = SystemTime.new(this.statVal.stCtim.tvSec, this.statVal.stCtim.tvNsec.into())

    public open fun size(): ULong = this.statVal.stSize.toULong()

    public open fun perm(): FilePermissions = FilePermissions(mode = this.statVal.stMode)

    public open fun fileType(): FileType {
        val maskedMode = this.statVal.stMode and S_IFMT
        val mode =
            when (maskedMode) {
                S_IFDIR -> DT_DIR
                S_IFLNK -> DT_LNK
                S_IFREG -> DT_REG
                else -> DT_UNKNOWN
            }
        return FileType(mode = mode)
    }

    public companion object {
        public fun fromStat(statVal: StatStruct): FileAttr = FileAttr(statVal = statVal)
    }
}

// all DirEntry's will have a reference to this struct
public open class InnerReadDir(
    public var root: PathBuf,
)

public open class ReadDir(
    public var inner: InnerReadDir,
    public var fd: FileDesc,
    public var buf: GetdentsBuffer,
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
        // Thus the result will be e.g. ReadDir("/home")'
        f.append((this.inner.root).toString())
    }

    public open fun next(): Result<DirEntry>? {
        while (true) {
            val buf1 =
                run {
                    val matchVal = this.buf.fillBuf(this.fd.asFd())
                    when (matchVal) { is Result.Ok<*> -> {
                        val buf = matchVal.value
                        buf
                    }; is Result.Err<*> -> {
                        val err = matchVal.error
                        return Result.Err(err)
                    } }
                }
            if (buf1.len() == 0) {
                // No more entries left.
                return null
            }
            val entryPtr = buf1.asPtr().cast()
            // The dirent64 struct is a weird imaginary thing that isn't ever supposed
            // to be worked with by value. Its trailing dName field is declared
            // variously as [cChar; 256] or [cChar; 1] on different systems but
            // either way that size is meaningless; only the offset of dName is
            // meaningful. The dirent64 pointers that libc returns from getdents64 are
            // allowed to point to allocations smaller _or_ LARGER than implied by the
            // definition of the struct.
            //
            // As such, we need to be even more careful with dirent64 than if its
            // contents were "simply" partially initialized data.
            //
            // Like for uninitialized contents, converting entryPtr to `dirent64`
            // would not be legal. However, we can use `raw const(* entryPtr).dName`
            // to refer the fields individually, because that operation is equivalent
            // to `byteOffset` and thus does not require the full extent of `* entryPtr`
            // to be in bounds of the same allocation, only the offset of the field
            // being referenced.
            this.buf.consume(Int.from(entryPtr.dReclen))
            // dName is guaranteed to be null-terminated.
            val name = CStr.fromPtr((addrOf(entryPtr.dName)).cast())
            val nameBytes = name.toBytes()
            if (nameBytes == ".".encodeToByteArray() || nameBytes == "..".encodeToByteArray()) {
                continue
            }
            return Result.Ok(DirEntry(dir = this.inner, ino = entryPtr.dIno, type = entryPtr.dType, name = OsString.fromVec(nameBytes.toVec())))
        }
    }
}

/**
 * A buffer containing [dirent64]s, filled with [getdents64].
 *
 * This struct is roughly modeled after the `BufReader`s `Buffer`.
 */
public open class GetdentsBuffer internal constructor(
    bufCtorArg: List<MaybeUninit<Dirent64>>,
    // The current seek offset into `buf`, must always be <= `filled`.
    public var pos: Int,
    // Each call to `fillBuf` sets `filled` to indicate how many bytes at the start of `buf` are
    // initialized with bytes from a read.
    public var filled: Int,
) {
    // The buffer.
    public var buf: List<MaybeUninit<Dirent64>> = bufCtorArg
        internal set

    public open fun buffer(): List<UByte> {
        // SAFETY: self.pos and self.filled are valid, and self.filled >= self.pos, and
        // that region is initialized because those are all invariants of this type.
        return run {
            val ptr = ConstPtr((ConstPtr((this.buf.asPtr()).rawAddress)).rawAddress + ((this.pos).toLong() * (ObjectSlotLayout<MaybeUninit<UByte>>()).strideBytes))
            Slice.fromRawParts(ptr, this.filled - this.pos).assumeInitRef()
        }
    }

    public open fun consume(amt: Int) {
        this.pos = minOf(this.pos + amt, this.filled)
    }

    public open fun fillBuf(fd: BorrowedFd): Result<List<UByte>> {
        // If we've reached the end of our internal buffer then we need to fetch
        // some more data from the reader.
        // Branch using `>=` instead of the more correct `==`
        // to tell the compiler that the pos..cap slice is always valid.
        if (this.pos >= this.filled) {
            check(this.pos == this.filled)
            val result = cvt(getdents64(fd.asRawFd(), this.buf.asMutPtr(), this.buf.size * sizeOf<Dirent64>(typeLayoutT = ObjectSlotLayout<Dirent64>())))
            this.pos = 0
            this.filled = 0
            this.filled = (when (val tryResult = result) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }).toInt()
        }
        return Result.Ok(this.buffer())
    }

    public companion object {
        public operator fun invoke(
            buf: List<MaybeUninit<Dirent64>>,
            pos: Int,
            filled: Int,
        ): GetdentsBuffer = GetdentsBuffer(
            bufCtorArg = buf,
            pos = pos,
            filled = filled,
        )

        /**
         * Creates a new buffer with at least `capacity` bytes for use with dirent.
         */
        public fun withCapacity(capacity: Int): GetdentsBuffer {
            val buf = Box.newUninitSlice(capacity.divCeil(sizeOf<Dirent64>(typeLayoutT = ObjectSlotLayout<Dirent64>())))
            return GetdentsBuffer(buf = buf, pos = 0, filled = 0)
        }
    }
}

public open class DirEntry(
    public var dir: InnerReadDir,
    /**
     * 64-bit inode number
     */
    public var ino: ULong,
    /**
     * File type
     */
    public var type: UByte,
    /**
     * name of the entry
     */
    public var name: OsString,
) {
    public open fun path(): PathBuf = this.dir.root.join(this.fileNameOsStr())

    public open fun fileName(): OsString = this.fileNameOsStr().toOsString()

    public open fun metadata(): Result<FileAttr> {
        val path = this.path()
        path.setFileName(this.fileNameOsStr())
        return lstat(path)
    }

    public open fun fileType(): Result<FileType> = Result.Ok(FileType(mode = this.type))

    public open fun ino(): ULong = this.ino

    public open fun fileNameOsStr(): OsStr = this.name.asOsStr()
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
    public var mode: Int,
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

    public open fun getAccessMode(): Result<Int> =
        run {
            val matchVal = Triple(this.read, this.write, this.append)
            when {
                matchVal.first == true && matchVal.second == false && matchVal.third == false -> Result.Ok(O_RDONLY)
                matchVal.first == false && matchVal.second == true && matchVal.third == false -> Result.Ok(O_WRONLY)
                matchVal.first == true && matchVal.second == true && matchVal.third == false -> Result.Ok(O_RDWR)
                matchVal.first == false && matchVal.third == true -> Result.Ok(O_WRONLY or O_APPEND)
                matchVal.first == true && matchVal.third == true -> Result.Ok(O_RDWR or O_APPEND)
                else -> {
                    Result.Err(io.constError(ErrorKind.InvalidInput, "invalid access mode"))
                }
            }
        }

    public open fun getCreationMode(): Result<Int> {
        run {
            val matchVal = Pair(this.write, this.append)
            when {
                matchVal.first == true && matchVal.second == false -> {}
                matchVal.first == false && matchVal.second == false -> {
                    if (this.truncate || this.create || this.createNew) {
                        return Result.Err(io.constError(ErrorKind.InvalidInput, "invalid creation mode"))
                    }
                }
                else -> {
                    if (this.truncate && !this.createNew) {
                        return Result.Err(io.constError(ErrorKind.InvalidInput, "invalid creation mode"))
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
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 8L, value.mode)
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
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.mode)
                }
            }

        public fun new(): OpenOptions = OpenOptions(read = false, write = false, append = false, truncate = false, create = false, createNew = false, mode = 511)
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
    public var mode: UInt,
) {
    public open fun readonly(): Boolean {
        // check if any class (owner, group, others) has write permission
        return (this.mode and 146u == 0u).toUByte()
    }

    public open fun setReadonly(readonly: Boolean) {
        error("unsupported")
    }

    public open fun mode(): UInt = this.mode

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FilePermissions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FilePermissions> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FilePermissions =
                    FilePermissions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FilePermissions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.mode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FilePermissions =
                    FilePermissions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FilePermissions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.mode)
                }
            }
    }
}

public open class FileType(
    public var mode: UByte,
) {
    public open fun eq(other: FileType): Boolean = (this.mode == other.mode).toUByte()

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.mode).hashCode()
        return state
    }

    public open fun isDir(): Boolean = (this.mode == DT_DIR).toUByte()

    public open fun isFile(): Boolean = (this.mode == DT_REG).toUByte()

    public open fun isSymlink(): Boolean = (this.mode == DT_LNK).toUByte()

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
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr, value.mode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileType =
                    FileType(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileType) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset, value.mode)
                }
            }
    }
}

public open class DirBuilder(
    public var mode: UInt,
) {
    public open fun mkdir(path: Path): Result<Unit> =
        runPathWithCstr(path, { path1 ->
            cvt(io.github.kotlinmania.hermitabi.mkdir(path1.asPtr().cast(), this.mode.into())).map({ Unit })
        })

    public open fun setMode(mode: UInt) {
        this.mode = mode
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<DirBuilder> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<DirBuilder> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): DirBuilder =
                    DirBuilder(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: DirBuilder) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.mode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DirBuilder =
                    DirBuilder(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DirBuilder) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.mode)
                }
            }

        public fun new(): DirBuilder = DirBuilder(mode = 511u)
    }
}

public fun readdir(path: Path): Result<ReadDir> {
    val fdRaw =
        when (val tryResult = runPathWithCstr(path, { path1 ->
            cvt(open(path1.asPtr(), O_RDONLY or O_DIRECTORY, 0))
        })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val fd = FileDesc.fromRawFd(fdRaw)
    val root = path.toPathBuf()
    val inner = InnerReadDir(root = root)
    val bufSize = maxOf(DEFAULT_BUF_SIZE, sizeOf<Dirent64>(typeLayoutT = ObjectSlotLayout<Dirent64>()))
    val buf = GetdentsBuffer.withCapacity(bufSize)
    return Result.Ok(ReadDir(inner = inner, fd = fd, buf = buf))
}

public fun unlink(path: Path): Result<Unit> = runPathWithCstr(path, { path1 -> cvt(io.github.kotlinmania.hermitabi.unlink(path1.asPtr())).map({ Unit }) })

public fun rename(old: Path, new: Path): Result<Unit> = unsupported()

public fun renameNoreplace(old: Path, new: Path): Result<Unit> = unsupported()

public fun setPerm(p: Path, perm: FilePermissions): Result<Unit> = unsupported()

public fun setPermNofollow(p: Path, perm: FilePermissions): Result<Unit> = unsupported()

public fun setTimes(p: Path, times: FileTimes): Result<Unit> = unsupported()

public fun setTimesNofollow(p: Path, times: FileTimes): Result<Unit> = unsupported()

public fun rmdir(path: Path): Result<Unit> = runPathWithCstr(path, { path1 -> cvt(io.github.kotlinmania.hermitabi.rmdir(path1.asPtr())).map({ Unit }) })

public fun removeDirAll(path: Path): Result<Unit> = unsupported()

public fun readlink(p: Path): Result<PathBuf> = unsupported()

public fun symlink(original: Path, link: Path): Result<Unit> = unsupported()

public fun link(original: Path, link: Path): Result<Unit> = unsupported()

public fun stat(path: Path): Result<FileAttr> {
    return runPathWithCstr(path, closure@{ path1 ->
        var statVal: StatStruct? = null
        when (val tryResult = cvt(Stat(path1.asPtr(), statVal))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure Result.Err(tryResult.error) }
        Result.Ok(FileAttr.fromStat(statVal))
    })
}

public fun lstat(path: Path): Result<FileAttr> {
    return runPathWithCstr(path, closure1@{ path1 ->
        var statVal: StatStruct? = null
        when (val tryResult = cvt(io.github.kotlinmania.hermitabi.lstat(path1.asPtr(), statVal))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return@closure1 Result.Err(tryResult.error) }
        Result.Ok(FileAttr.fromStat(statVal))
    })
}

public fun canonicalize(p: Path): Result<PathBuf> = unsupported()
