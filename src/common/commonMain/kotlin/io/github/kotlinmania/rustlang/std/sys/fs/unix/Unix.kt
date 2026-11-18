// port-lint: source sys/fs/unix.rs
package io.github.kotlinmania.rustlang.std.sys.fs.unix

import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.alloc.io.read.Read
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.ffi.primitives.CInt
import io.github.kotlinmania.rustlang.core.ffi.primitives.CSizeT
import io.github.kotlinmania.rustlang.core.ffi.primitives.CSsizeT
import io.github.kotlinmania.rustlang.core.fmt.Write
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.Result
import io.github.kotlinmania.rustlang.core.io.error.SimpleMessage
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.mem.AddressableLocal
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.RustMutableReference
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.addrOf
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.drop
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.mem.zeroed
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.constptr.add
import io.github.kotlinmania.rustlang.core.ptr.constptr.cast
import io.github.kotlinmania.rustlang.core.ptr.constptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.add
import io.github.kotlinmania.rustlang.core.ptr.mutptr.cast
import io.github.kotlinmania.rustlang.core.ptr.mutptr.isNull
import io.github.kotlinmania.rustlang.core.ptr.mutptr.writeBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU8
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.asPtr
import io.github.kotlinmania.rustlang.core.sync.atomic.load
import io.github.kotlinmania.rustlang.core.sync.atomic.store
import io.github.kotlinmania.rustlang.libc.chmod
import io.github.kotlinmania.rustlang.libc.closedir
import io.github.kotlinmania.rustlang.libc.dirfd
import io.github.kotlinmania.rustlang.libc.fchmod
import io.github.kotlinmania.rustlang.libc.fchmodat
import io.github.kotlinmania.rustlang.libc.fcntl
import io.github.kotlinmania.rustlang.libc.fdopendir
import io.github.kotlinmania.rustlang.libc.free
import io.github.kotlinmania.rustlang.libc.fsetattrlist
import io.github.kotlinmania.rustlang.libc.openat
import io.github.kotlinmania.rustlang.libc.opendir
import io.github.kotlinmania.rustlang.libc.realpath
import io.github.kotlinmania.rustlang.libc.setattrlist
import io.github.kotlinmania.rustlang.libc.timespec.default
import io.github.kotlinmania.rustlang.libc.unix.DT_BLK
import io.github.kotlinmania.rustlang.libc.unix.DT_CHR
import io.github.kotlinmania.rustlang.libc.unix.DT_DIR
import io.github.kotlinmania.rustlang.libc.unix.DT_FIFO
import io.github.kotlinmania.rustlang.libc.unix.DT_LNK
import io.github.kotlinmania.rustlang.libc.unix.DT_REG
import io.github.kotlinmania.rustlang.libc.unix.DT_SOCK
import io.github.kotlinmania.rustlang.libc.unix.DT_UNKNOWN
import io.github.kotlinmania.rustlang.libc.unix.S_ISGID
import io.github.kotlinmania.rustlang.libc.unix.S_ISUID
import io.github.kotlinmania.rustlang.libc.unix.S_ISVTX
import io.github.kotlinmania.rustlang.libc.unix.Timespec
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.ATTR_BIT_MAP_COUNT
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.ATTR_CMN_ACCTIME
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.ATTR_CMN_CRTIME
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.ATTR_CMN_MODTIME
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.Attrlist
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.FSOPT_NOFOLLOW
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.F_FULLFSYNC
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.Flock
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.AT_EMPTY_PATH
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.AT_STATX_SYNC_AS_STAT
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.LOCK_EX
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.LOCK_NB
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.LOCK_SH
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.LOCK_UN
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.STATX_BASIC_STATS
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.STATX_BTIME
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.Statx
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.StatxTimestamp
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.Ino64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.android.RENAME_NOREPLACE
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.linux.Blkcnt64T
import io.github.kotlinmania.rustlang.libc.unix.linuxlike.makedev
import io.github.kotlinmania.rustlang.libc.unlinkat
import io.github.kotlinmania.rustlang.libc.wasi.AT_FDCWD
import io.github.kotlinmania.rustlang.libc.wasi.AT_REMOVEDIR
import io.github.kotlinmania.rustlang.libc.wasi.AT_SYMLINK_NOFOLLOW
import io.github.kotlinmania.rustlang.libc.wasi.EBADF
import io.github.kotlinmania.rustlang.libc.wasi.EEXIST
import io.github.kotlinmania.rustlang.libc.wasi.EFAULT
import io.github.kotlinmania.rustlang.libc.wasi.EINVAL
import io.github.kotlinmania.rustlang.libc.wasi.ELOOP
import io.github.kotlinmania.rustlang.libc.wasi.ENOENT
import io.github.kotlinmania.rustlang.libc.wasi.ENOSYS
import io.github.kotlinmania.rustlang.libc.wasi.ENOTDIR
import io.github.kotlinmania.rustlang.libc.wasi.F_GETFD
import io.github.kotlinmania.rustlang.libc.wasi.F_GETFL
import io.github.kotlinmania.rustlang.libc.wasi.ModeT
import io.github.kotlinmania.rustlang.libc.wasi.O_ACCMODE
import io.github.kotlinmania.rustlang.libc.wasi.O_APPEND
import io.github.kotlinmania.rustlang.libc.wasi.O_CLOEXEC
import io.github.kotlinmania.rustlang.libc.wasi.O_CREAT
import io.github.kotlinmania.rustlang.libc.wasi.O_DIRECTORY
import io.github.kotlinmania.rustlang.libc.wasi.O_EXCL
import io.github.kotlinmania.rustlang.libc.wasi.O_NOFOLLOW
import io.github.kotlinmania.rustlang.libc.wasi.O_RDONLY
import io.github.kotlinmania.rustlang.libc.wasi.O_RDWR
import io.github.kotlinmania.rustlang.libc.wasi.O_TRUNC
import io.github.kotlinmania.rustlang.libc.wasi.O_WRONLY
import io.github.kotlinmania.rustlang.libc.wasi.SEEK_CUR
import io.github.kotlinmania.rustlang.libc.wasi.SEEK_END
import io.github.kotlinmania.rustlang.libc.wasi.SEEK_SET
import io.github.kotlinmania.rustlang.libc.wasi.S_IFBLK
import io.github.kotlinmania.rustlang.libc.wasi.S_IFCHR
import io.github.kotlinmania.rustlang.libc.wasi.S_IFDIR
import io.github.kotlinmania.rustlang.libc.wasi.S_IFIFO
import io.github.kotlinmania.rustlang.libc.wasi.S_IFLNK
import io.github.kotlinmania.rustlang.libc.wasi.S_IFMT
import io.github.kotlinmania.rustlang.libc.wasi.S_IFREG
import io.github.kotlinmania.rustlang.libc.wasi.S_IFSOCK
import io.github.kotlinmania.rustlang.libc.wasi.S_IRGRP
import io.github.kotlinmania.rustlang.libc.wasi.S_IROTH
import io.github.kotlinmania.rustlang.libc.wasi.S_IRUSR
import io.github.kotlinmania.rustlang.libc.wasi.S_IWGRP
import io.github.kotlinmania.rustlang.libc.wasi.S_IWOTH
import io.github.kotlinmania.rustlang.libc.wasi.S_IWUSR
import io.github.kotlinmania.rustlang.libc.wasi.S_IXGRP
import io.github.kotlinmania.rustlang.libc.wasi.S_IXOTH
import io.github.kotlinmania.rustlang.libc.wasi.S_IXUSR
import io.github.kotlinmania.rustlang.libc.wasi.UTIME_OMIT
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fs.Metadata
import io.github.kotlinmania.rustlang.std.fs.Permissions
import io.github.kotlinmania.rustlang.std.fs.TryLockError
import io.github.kotlinmania.rustlang.std.fs.metadata
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.fd.asFd
import io.github.kotlinmania.rustlang.std.os.fd.asRawFd
import io.github.kotlinmania.rustlang.std.os.fd.fromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.intoRawFd
import io.github.kotlinmania.rustlang.std.os.unix.ffi.osstr.asBytes
import io.github.kotlinmania.rustlang.std.os.unix.ffi.osstr.fromBytes
import io.github.kotlinmania.rustlang.std.os.unix.ffi.osstr.fromVec
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.AsInnerMut
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.std.sys.fromInner
import io.github.kotlinmania.rustlang.std.sys.fs.common.Dir
import io.github.kotlinmania.rustlang.std.sys.fs.common.NOT_FILE_ERROR
import io.github.kotlinmania.rustlang.std.sys.helpers.ignoreNotfound
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runPathWithCstr
import io.github.kotlinmania.rustlang.std.sys.io.Errno
import io.github.kotlinmania.rustlang.std.sys.io.error.unix.setErrno
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvtR
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.sys.time.unix.SystemTime
import io.github.kotlinmania.rustlang.std.sys.time.unix.UNIX_EPOCH
import io.github.kotlinmania.rustlang.std.sys.weak.syscall
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import io.github.kotlinmania.rustlang.core.result.Result as RustResult
import io.github.kotlinmania.rustlang.libc.fstat as fstat64
import io.github.kotlinmania.rustlang.libc.ftruncate as ftruncate64
import io.github.kotlinmania.rustlang.libc.lseek as lseek64
import io.github.kotlinmania.rustlang.libc.lstat as lstat64
import io.github.kotlinmania.rustlang.libc.open as open64
import io.github.kotlinmania.rustlang.libc.readdir as readdir64
import io.github.kotlinmania.rustlang.libc.wasi.Dirent as Dirent64
import io.github.kotlinmania.rustlang.libc.wasi.OffT as Off64T
import io.github.kotlinmania.rustlang.libc.wasi.Stat as Stat64

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

public expect fun open(pathname: ConstPtr, flags: CInt, vararg variadicArgs: Long): CInt

public expect fun read(fd: CInt, buf: MutPtr, count: CSizeT): CSsizeT

public expect fun write(fd: CInt, buf: ConstPtr, count: CSizeT): CSsizeT

public expect fun close(fd: CInt): CInt

public open class File(
    public var value: FileDesc,
) : AsInner<FileDesc>, AsInnerMut<FileDesc>, IntoInner<FileDesc>, FromInner<FileDesc, File>, AsFd, AsRawFd, IntoRawFd, FromRawFd<File> {
    public var field0: FileDesc
        get() = value
        set(v) {
            value = v
        }

    public open fun fileAttr(): RustResult<FileAttr, Error> {
        val fd = this.asRawFd()
        when (val matchVal = tryStatx(fd, constPtrFromRustCast("".asPtr()), AT_EMPTY_PATH or AT_STATX_SYNC_AS_STAT, STATX_BASIC_STATS or STATX_BTIME)) {
            null -> {}
            else -> {
                val ret = matchVal
                return ret
            }
        }
        val stat: Stat64  = MaybeUninit.zeroed(UnresolvedTypeLayout<Stat64>()).assumeInit()
        when (val tryResult = cvt(fstat64(fd, stat))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<FileAttr, Error>(tryResult.errValue()) }
        return RustResult.Ok<FileAttr, Error>(FileAttr.fromStat64(stat))
    }

    public open fun fsync(): RustResult<Unit, Error> {
        fun osFsync(fd: CInt): CInt = fcntl(fd, F_FULLFSYNC)
        when (val tryResult = cvtR({ osFsync(this.asRawFd()) })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        return RustResult.Ok<Unit, Error>(Unit)
    }

    public open fun datasync(): RustResult<Unit, Error> {
        fun osDatasync(fd: CInt): CInt = fcntl(fd, F_FULLFSYNC)
        when (val tryResult = cvtR({ osDatasync(this.asRawFd()) })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        return RustResult.Ok<Unit, Error>(Unit)
    }

    public open fun lock(): RustResult<Unit, Error> {
        when (val tryResult = cvt(Flock(this.asRawFd(), LOCK_EX))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        return RustResult.Ok<Unit, Error>(Unit)
    }

    public open fun lockShared(): RustResult<Unit, Error> {
        when (val tryResult = cvt(Flock(this.asRawFd(), LOCK_SH))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        return RustResult.Ok<Unit, Error>(Unit)
    }

    public open fun tryLock(): RustResult<Unit, TryLockError> {
        val result = cvt(Flock(this.asRawFd(), LOCK_EX or LOCK_NB))
        return if (result is RustResult.Err<*, *>) {
            val err = result.errValue()
            if (err.kind() == ErrorKind.WouldBlock) {
                RustResult.Err<Unit, TryLockError>(TryLockError.WouldBlock)
            } else {
                RustResult.Err<Unit, TryLockError>(TryLockError.Error(err))
            }
        } else {
            RustResult.Ok<Unit, TryLockError>(Unit)
        }
    }

    public open fun tryLockShared(): RustResult<Unit, TryLockError> {
        val result = cvt(Flock(this.asRawFd(), LOCK_SH or LOCK_NB))
        return if (result is RustResult.Err<*, *>) {
            val err = result.errValue()
            if (err.kind() == ErrorKind.WouldBlock) {
                RustResult.Err<Unit, TryLockError>(TryLockError.WouldBlock)
            } else {
                RustResult.Err<Unit, TryLockError>(TryLockError.Error(err))
            }
        } else {
            RustResult.Ok<Unit, TryLockError>(Unit)
        }
    }

    public open fun unlock(): RustResult<Unit, Error> {
        when (val tryResult = cvt(Flock(this.asRawFd(), LOCK_UN))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        return RustResult.Ok<Unit, Error>(Unit)
    }

    public open fun truncate(size: ULong): RustResult<Unit, Error> {
        val size1: Off64T = when (val tryResult = size.tryInto().mapErr({ e -> Error.new(ErrorKind.InvalidInput, e) })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        return cvtR({ ftruncate64(this.asRawFd(), size1) }).map({ drop(it) })
    }

    public open fun read(buf: MutableList<UByte>): RustResult<Int, Error> = this.value.read(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): RustResult<Int, Error> = this.value.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.value.isReadVectored()

    public open fun readAt(buf: MutableList<UByte>, offset: ULong): RustResult<Int, Error> = this.value.readAt(buf, offset)

    public open fun readBuf(cursor: BorrowedCursor<UByte>): RustResult<Unit, Error> = this.value.readBuf(cursor)

    public open fun readBufAt(cursor: BorrowedCursor<UByte>, offset: ULong): RustResult<Unit, Error> = this.value.readBufAt(cursor, offset)

    public open fun readVectoredAt(bufs: MutableList<IoSliceMut>, offset: ULong): RustResult<Int, Error> = this.value.readVectoredAt(bufs, offset)

    public open fun write(buf: List<UByte>): RustResult<Int, Error> = this.value.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): RustResult<Int, Error> = this.value.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public open fun writeAt(buf: List<UByte>, offset: ULong): RustResult<Int, Error> = this.value.writeAt(buf, offset)

    public open fun writeVectoredAt(bufs: List<IoSlice>, offset: ULong): RustResult<Int, Error> = this.value.writeVectoredAt(bufs, offset)

    public open fun flush(): RustResult<Unit, Error> = RustResult.Ok<Unit, Error>(Unit)

    public open fun seek(pos: SeekFrom): RustResult<ULong, Error> {
        val (whence, pos1) =
            when (pos) {
                // Casting to `Long` is fine, too large values will end up as
                // negative which will cause an error in `lseek64`.
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
        val n = when (val tryResult = cvt(lseek64(this.asRawFd(), pos1.toLong(), whence))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ULong, Error>(tryResult.errValue()) }
        return RustResult.Ok<ULong, Error>(n.toULong())
    }

    public open fun size(): RustResult<ULong, Error>? =
        run {
            val matchVal = this.fileAttr().map({ attr -> attr.size() })
            when {
                // Fall back to default implementation if the returned size is 0,
                // we might be in a proc mount.
                matchVal is RustResult.Ok<*, *> && matchVal.okValue() == 0 -> {
                    val okValueVal = matchVal.okValue()
                    null
                }
                else -> {
                    val result = matchVal
                    result
                }
            }
        }

    public open fun tell(): RustResult<ULong, Error> = this.seek(SeekFrom.Current(0))

    public open fun duplicate(): RustResult<File, Error> = this.value.duplicate().map({ File(it) })

    public open fun setPermissions(perm: FilePermissions): RustResult<Unit, Error> {
        when (val tryResult = cvtR({ fchmod(this.asRawFd(), perm.mode) })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        return RustResult.Ok<Unit, Error>(Unit)
    }

    public open fun setTimes(times: FileTimes): RustResult<Unit, Error> {
        val ta = when (val tryResult = TimesAttrlist.fromTimes(times)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        when (val tryResult = cvt(fsetattrlist(this.asRawFd(), ta.attrlist(), ta.timesBuf(), ta.timesBufSize(), 0))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        return RustResult.Ok<Unit, Error>(Unit)
    }

    public override fun asInner(): FileDesc = this.value

    public override fun asInnerMut(): FileDesc = this.value

    public override fun intoInner(): FileDesc = this.value

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asRawFd(): RawFd = this.value.asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoRawFd()

    public open fun fmt(f: StringBuilder): RustResult<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val fd = this.asRawFd()
        val b = debugPathFd(fd, f, "File")
        return run { b.finish(); RustResult.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public companion object {
        public fun open(path: Path, opts: OpenOptions): RustResult<File, Error> = runPathWithCstr(path, { path1 -> File.openC(path1, opts) })

        public fun openC(path: CStr, opts: OpenOptions): RustResult<File, Error> {
            val flags = O_CLOEXEC or when (val tryResult = opts.getAccessMode()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<File, Error>(tryResult.errValue()) } or when (val tryResult = opts.getCreationMode()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<File, Error>(tryResult.errValue()) } or (opts.customFlags and O_ACCMODE.inv())
            // The third argument of `open64` is documented to have type `modeT`. On
            // some platforms (like macOS, where `open64` is actually `open`), `modeT` is `UShort`.
            // However, since this is a variadic function, C integer promotion rules mean that on
            // the ABI level, this still gets passed as `cInt` (aka `UInt` on Unix platforms).
            val fd = when (val tryResult = cvtR({ open64(path.asPtr(), flags, opts.mode.toInt()) })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<File, Error>(tryResult.errValue()) }
            return RustResult.Ok<File, Error>(File(FileDesc.fromRawFd(fd)))
        }

        public fun fromInner(fileDesc: FileDesc): File = File(fileDesc)

        public fun fromRawFd(rawFd: RawFd): File = File(FromRawFd.fromRawFd(rawFd))
    }
}

public open class FileAttr(
    public var stat: Stat64,
    public var statxExtraFields: StatxExtraFields?,
) : AsInner<Stat64> {
    public open fun clone(): FileAttr = FileAttr(stat, statxExtraFields)

    public open fun size(): ULong = this.stat.stSize.toULong()

    public open fun perm(): FilePermissions = FilePermissions(mode = (this.stat.stMode.toUInt()))

    public open fun fileType(): FileType = FileType(mode = this.stat.stMode.toUInt())

    public open fun modified(): RustResult<SystemTime, Error> {
        when (val matchVal = this.stxMtime()) {
            null -> {}
            else -> {
                val mtime = matchVal
                return SystemTime.new(mtime.tvSec, mtime.tvNsec.toLong())
            }
        }
        return SystemTime.new(this.stat.stMtime.toLong(), this.stat.stMtimeNsec.toLong())
    }

    public open fun accessed(): RustResult<SystemTime, Error> {
        when (val matchVal = this.stxAtime()) {
            null -> {}
            else -> {
                val atime = matchVal
                return SystemTime.new(atime.tvSec, atime.tvNsec.toLong())
            }
        }
        return SystemTime.new(this.stat.stAtime.toLong(), this.stat.stAtimeNsec.toLong())
    }

    public open fun created(): RustResult<SystemTime, Error> = SystemTime.new(this.stat.stBirthtime.toLong(), this.stat.stBirthtimeNsec.toLong())

    public override fun asInner(): Stat64 = this.stat

    public companion object {
        public fun fromStat64(stat: Stat64): FileAttr = FileAttr(stat = stat, statxExtraFields = null)
    }
}

public open class StatxExtraFields(
    // This is needed to check if btime is supported by the filesystem.
    public var stxMask: UInt,
    public var stxBtime: StatxTimestamp,
) {
    public open fun clone(): StatxExtraFields = StatxExtraFields(stxMask, stxBtime)
}

// We prefer `statx` on Linux if available, which contains file creation time,
// as well as 64-bit timestamps of all kinds.
// Default `stat64` contains no creation time and may have 32-bit `timeT`.
public fun tryStatx(fd: CInt, path: ConstPtr, flags: Int, mask: UInt): RustResult<FileAttr, Error>? {
    // Linux kernel prior to 4.11 or glibc prior to glibc 2.28 don't support `statx`.
    // We check for it on first failure and remember availability to avoid having to
    // do it again.
    sealed class StatxState(
        public val value: UByte,
    ) {
        public data object Unknown : StatxState(0u.toUByte())
        public data object Present : StatxState((Unknown.value + 1u).toUByte())
        public data object Unavailable : StatxState((Present.value + 1u).toUByte())

        public fun toByte(): Byte = value.toByte()

        public fun toUByte(): UByte = value

        public fun toShort(): Short = value.toShort()

        public fun toUShort(): UShort = value.toUShort()

        public fun toInt(): Int = value.toInt()

        public fun toUInt(): UInt = value.toUInt()

        public fun toLong(): Long = value.toLong()

        public fun toULong(): ULong = value.toULong()
    }
    val STATX_SAVED_STATE: AtomicU8  = AtomicU8.new(StatxState.Unknown.value.toUByte())
    syscall(statx, fd, cInt, pathname, cChar, flags, cInt, mask, libc, cUint, statxbuf, libc, statx, cInt)
    val statxAvailability = STATX_SAVED_STATE.load(Ordering.Relaxed)
    if (statxAvailability == StatxState.Unavailable.value.toUByte()) {
        return null
    }
    val localStorageL172C8 = AddressableLocal(Statx(), UnresolvedTypeLayout<Statx>())
    val buf by localStorageL172C8
    when (val matchVal = cvt(Statx(fd, path, flags, mask, RustMutableReference(localStorageL172C8.asMutPtr(), localStorageL172C8.layout)))) {
        is RustResult.Err<*, *> -> {
            val err = matchVal.errValue()
            if (STATX_SAVED_STATE.load(Ordering.Relaxed) == StatxState.Present.value.toUByte()) {
                return RustResult.Err<FileAttr, Error, io.github.kotlinmania.rustlang.core.io.error.Error>(err)
            }
            // We're not yet entirely sure whether `statx` is usable on this kernel
            // or not. Syscalls can return errors from things other than the kernel
            // per se, e.g. `EPERM` can be returned if seccomp is used to block the
            // syscall, or `ENOSYS` might be returned from a faulty FUSE driver.
            //
            // Availability is checked by performing a call which expects `EFAULT`
            // if the syscall is usable.
            //
            // See: https://github.com/rust-lang/rust/issues/65662
            //
            // Note what about transient conditions like `ENOMEM`?
            val err2 = cvt(Statx(0, ConstPtr(0L), 0, STATX_BASIC_STATS or STATX_BTIME, MutPtr(0L))).err().andThen({ e -> e.rawOsError() })
            if (err2 == EFAULT) {
                STATX_SAVED_STATE.store(StatxState.Present.value.toUByte(), Ordering.Relaxed)
                return RustResult.Err<FileAttr, Error, io.github.kotlinmania.rustlang.core.io.error.Error>(err)
            } else {
                STATX_SAVED_STATE.store(StatxState.Unavailable.value.toUByte(), Ordering.Relaxed)
                return null
            }
        }
        else -> {}
    }
    if (statxAvailability == StatxState.Unknown.value.toUByte()) {
        STATX_SAVED_STATE.store(StatxState.Present.value.toUByte(), Ordering.Relaxed)
    }
    // We cannot fill `stat64` exhaustively because of private padding fields.
    var stat: Stat64 = MaybeUninit.zeroed(UnresolvedTypeLayout<Stat64>()).assumeInit()
    // `cUlong` on gnu-mips, `devT` otherwise
    stat.stDev = makedev(buf.stxDevMajor, buf.stxDevMinor)
    stat.stIno = ((buf.stxIno as Any?) as Ino64T)
    stat.stNlink = buf.stxNlink.toUInt()
    stat.stMode = buf.stxMode.toUInt()
    stat.stUid = buf.stxUid.toUInt()
    stat.stGid = buf.stxGid.toUInt()
    stat.stRdev = makedev(buf.stxRdevMajor, buf.stxRdevMinor)
    stat.stSize = buf.stxSize.toLong()
    stat.stBlksize = buf.stxBlksize.toULong()
    stat.stBlocks = ((buf.stxBlocks as Any?) as Blkcnt64T)
    stat.stAtime = buf.stxAtime.tvSec.toLong()
    // `Long` on gnu-x8664-x32, `cUlong` otherwise.
    stat.stAtimeNsec = buf.stxAtime.tvNsec
    stat.stMtime = buf.stxMtime.tvSec.toLong()
    stat.stMtimeNsec = buf.stxMtime.tvNsec
    stat.stCtime = buf.stxCtime.tvSec.toLong()
    stat.stCtimeNsec = buf.stxCtime.tvNsec
    val extra = StatxExtraFields(stxMask = buf.stxMask, stxBtime = buf.stxBtime, stxAtime = buf.stxAtime, stxCtime = buf.stxCtime, stxMtime = buf.stxMtime)
    return RustResult.Ok<FileAttr, Error, io.github.kotlinmania.rustlang.core.io.error.Error>(FileAttr(stat = stat, statxExtraFields = extra))
}

// all DirEntry's will have a reference to this struct
public open class InnerReadDir(
    public var dirp: DirStream,
    public var root: PathBuf,
)

public open class ReadDir(
    public var inner: InnerReadDir,
    public var endOfStream: Boolean,
) : Iterable<RustResult<DirEntry, Error>> {
    override fun iterator(): Iterator<RustResult<DirEntry, Error>> = object : AbstractIterator<RustResult<DirEntry, Error>>() {
        override fun computeNext() {
            when (val v = this@ReadDir.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun dir(): RustResult<Dir, Error> {
        val options = OpenOptions.new()
        options.read(true)
        return Dir.open(this.inner.root, options)
    }

    public open fun fmt(f: StringBuilder): RustResult<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        // This will only be called from std.fs.ReadDir, which will add a "ReadDir()" frame.
        // Thus the result will be e g ReadDir("/home")'
        return run { f.append((this.inner.root).toString()); RustResult.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public open fun next(): RustResult<DirEntry, Error>? {
        if (this.endOfStream) {
            return null
        }
        while (true) {
            setErrno(0)
            val entryPtr: ConstPtr = readdir64(this.inner.dirp.value)
            if (((entryPtr).rawAddress == 0L)) {
                // We either encountered an error, or reached the end. Either way,
                // the next call to next() should return None.
                this.endOfStream = true
                // To distinguish between errors and end-of-directory, we had to clear
                // errno beforehand to check for an error now.
                return when (val matchVal = Errno()) {
                    0 -> null
                    else -> {
                        val e = matchVal
                        RustResult.Err<DirEntry, Error, io.github.kotlinmania.rustlang.core.io.error.Error>(Error.fromRawOsError(e))
                    }
                }
            }
            // The dirent64 struct is a weird imaginary thing that isn't ever supposed
            // to be worked with by value. Its trailing dName field is declared
            // variously as [cChar; 256] or [cChar; 1] on different systems but
            // either way that size is meaningless; only the offset of dName is
            // meaningful. The dirent64 pointers that libc returns from readdir64 are
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
            // dName is guaranteed to be null-terminated.
            val name = CStr.fromPtr((addrOf((UnresolvedTypeLayout<Dirent64>()).readNative((entryPtr).rawAddress).dName)).cast())
            val nameBytes = name.toBytes()
            if (nameBytes == ".".encodeToByteArray() || nameBytes == "..".encodeToByteArray()) {
                continue
            }
            // When loading from a field, we can skip the `raw const`; `(* entryPtr).dIno` as
            // a value expression will do the right thing: `byteOffset` to the field and then
            // only access those bytes.
            val entry = Dirent64Min(dIno = (UnresolvedTypeLayout<Dirent64>()).readNative((entryPtr).rawAddress).dFileno, dType = ((UnresolvedTypeLayout<Dirent64>()).readNative((entryPtr).rawAddress).dType).toUByte())
            return RustResult.Ok<DirEntry, Error, io.github.kotlinmania.rustlang.core.io.error.Error>(DirEntry(entry = entry, name = name.toOwned(), dir = this.inner))
        }
    }

    public companion object {
        public fun new(inner: InnerReadDir): ReadDir = ReadDir(inner = inner, endOfStream = false)
    }
}

public open class DirStream internal constructor(
    valueCtorArg: MutPtr,
) {
    public var value: MutPtr = valueCtorArg
        internal set

    public var field0: MutPtr
        get() = value
        set(v) {
            value = v
        }

    public open fun drop() {
        // dirfd isn't supported everywhere
        run {
            val fd = dirfd(this.value)
            debugAssertFdIsOpen(fd)
        }
        val r = closedir(this.value)
        check(r == 0 || Error.lastOsError().isInterrupted()) { "unexpected error during closedir: ${Error.lastOsError()}" }
    }

    // SAFETY: `int dirfd(DIR * dirstream)` is MT-safe, implying that the pointer
    // may be safely sent among threads.

    public companion object {
        public fun typeLayout(): TypeLayout<DirStream> =
            object : TypeLayout<DirStream> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): DirStream =
                    DirStream(
                        (PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: DirStream) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DirStream =
                    DirStream(
                        (PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DirStream) {
                    (PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.value)
                }
            }

        public operator fun invoke(
            value: MutPtr,
        ): DirStream = DirStream(
            valueCtorArg = value,
        )
    }
}

public fun debugPathFd(fd: CInt, f: StringBuilder, name: String): DebugStruct {
    fun getMode(fd1: CInt): Pair<Boolean, Boolean>? {
        val mode = fcntl(fd1, F_GETFL)
        if (mode == -1) {
            return null
        }
        return when (mode and O_ACCMODE) {
            O_RDONLY -> Pair(true, false)
            O_RDWR -> Pair(true, true)
            O_WRONLY -> Pair(false, true)
            else -> null
        }
    }
    val b = DebugStruct(f, name)
    b.field("fd", fd)
    when (val matchVal = getPathFromFd(fd)) {
        null -> {}
        else -> {
            val path = matchVal
            b.field("path", path)
        }
    }
    when (val matchVal1 = getMode(fd)) {
        null -> {}
        else -> {
            val read = matchVal1.first
            val write = matchVal1.second
            b.field("read", read).field("write", write)
        }
    }
    return b
}

public fun getPathFromFd(fd: CInt): PathBuf? {
    fun getPath(fd1: CInt): PathBuf? {
        val p = PathBuf.from("/proc/self/fd")
        p.push(fd1.toString())
        return runPathWithCstr(p, ::readlink).ok()
    }
    return getPath(fd)
}

public open class DirEntry(
    public var dir: InnerReadDir,
    public var entry: Dirent64Min,
    // We need to store an owned copy of the entry name on platforms that use
    // readdir() (not readdirR()), because a) struct dirent may use a flexible
    // array to store the name, b) it lives only until the next readdir() call.
    public var name: CString,
) {
    public open fun path(): PathBuf = this.dir.root.join(this.fileNameOsStr())

    public open fun fileName(): OsString = this.fileNameOsStr().toOsString()

    public open fun metadata(): RustResult<FileAttr, Error> {
        val fd = when (val tryResult = cvt(dirfd(this.dir.dirp.value))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<FileAttr, Error>(tryResult.errValue()) }
        // Make this FD into a directory handle. We don't actually drop it,
        // so having an `OwnedFd` is fine.
        val dirHandle = Dir(OwnedFd.fromRawFd(fd))
        return dirHandle.metadataC(this.name, true)
    }

    public open fun fileType(): RustResult<FileType, Error> {
        // Use `entry.dType` if available.
        when (this.entry.dType) {
            DT_CHR -> { return RustResult.Ok<FileType, Error>(FileType(mode = S_IFCHR)) }
            DT_FIFO -> { return RustResult.Ok<FileType, Error>(FileType(mode = S_IFIFO)) }
            DT_LNK -> { return RustResult.Ok<FileType, Error>(FileType(mode = S_IFLNK)) }
            DT_REG -> { return RustResult.Ok<FileType, Error>(FileType(mode = S_IFREG)) }
            DT_SOCK -> { return RustResult.Ok<FileType, Error>(FileType(mode = S_IFSOCK)) }
            DT_DIR -> { return RustResult.Ok<FileType, Error>(FileType(mode = S_IFDIR)) }
            DT_BLK -> { return RustResult.Ok<FileType, Error>(FileType(mode = S_IFBLK)) }
            else -> {}
        }
        // Fall back to loading the metadata.
        return this.metadata().map({ m -> m.fileType() })
    }

    public open fun ino(): ULong = this.entry.dIno

    public open fun fileNameOsStr(): OsStr = OsStr.fromBytes(this.name.asBytes())
}

// Define a minimal subset of fields we need from `dirent64`, especially since
// we're not using the immediate `dName` on these targets. Keeping this as an
// `entry` field in `DirEntry` helps reduce the `cfg` boilerplate elsewhere.
public open class Dirent64Min(
    public var dIno: ULong,
    public var dType: UByte,
) {
    public companion object {
        public fun typeLayout(): TypeLayout<Dirent64Min> =
            object : TypeLayout<Dirent64Min> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Dirent64Min =
                    Dirent64Min(
                        (PrimitiveLayouts.U64).readNative(addr),
                        (PrimitiveLayouts.U8).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Dirent64Min) {
                    (PrimitiveLayouts.U64).writeNative(addr, value.dIno)
                    (PrimitiveLayouts.U8).writeNative(addr + 8L, value.dType)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Dirent64Min =
                    Dirent64Min(
                        (PrimitiveLayouts.U64).readBytes(buf, offset),
                        (PrimitiveLayouts.U8).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Dirent64Min) {
                    (PrimitiveLayouts.U64).writeBytes(buf, offset, value.dIno)
                    (PrimitiveLayouts.U8).writeBytes(buf, offset + 8, value.dType)
                }
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
    public var customFlags: Int,
    public var mode: ModeT,
) {
    public open fun clone(): OpenOptions = OpenOptions(read, write, append, truncate, create, createNew, customFlags, mode)

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

    public open fun mode(mode: UInt) {
        this.mode = mode
    }

    public open fun getAccessMode(): RustResult<CInt, Error> =
        run {
            val matchVal = Triple(this.read, this.write, this.append)
            when {
                matchVal.first == true && matchVal.second == false && matchVal.third == false -> RustResult.Ok<CInt, Error>(O_RDONLY)
                matchVal.first == false && matchVal.second == true && matchVal.third == false -> RustResult.Ok<CInt, Error>(O_WRONLY)
                matchVal.first == true && matchVal.second == true && matchVal.third == false -> RustResult.Ok<CInt, Error>(O_RDWR)
                matchVal.first == false && matchVal.third == true -> RustResult.Ok<CInt, Error>(O_WRONLY or O_APPEND)
                matchVal.first == true && matchVal.third == true -> RustResult.Ok<CInt, Error>(O_RDWR or O_APPEND)
                else -> {
                    // If no access mode is set, check if any creation flags are set
                    // to provide a more descriptive error message
                    if (this.create || this.createNew || this.truncate) {
                        RustResult.Err<CInt, Error>(Error.new(ErrorKind.InvalidInput, "creating or truncating a file requires write or append access"))
                    } else {
                        RustResult.Err<CInt, Error>(Error.new(ErrorKind.InvalidInput, "must specify at least one of read, write, or append access"))
                    }
                }
            }
        }

    public open fun getCreationMode(): RustResult<CInt, Error> {
        run {
            val matchVal = Pair(this.write, this.append)
            when {
                matchVal.first == true && matchVal.second == false -> {}
                matchVal.first == false && matchVal.second == false -> {
                    if (this.truncate || this.create || this.createNew) {
                        return RustResult.Err<CInt, Error>(Error.new(ErrorKind.InvalidInput, "creating or truncating a file requires write or append access"))
                    }
                }
                else -> {
                    if (this.truncate && !this.createNew) {
                        return RustResult.Err<CInt, Error>(Error.new(ErrorKind.InvalidInput, "append and truncate cannot both be enabled"))
                    }
                }
            }
        }
        return RustResult.Ok<CInt, Error>(run {
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

    public open fun fmt(f: StringBuilder): RustResult<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val read = this.read
        val write = this.write
        val append = this.append
        val truncate = this.truncate
        val create = this.create
        val createNew = this.createNew
        val customFlags = this.customFlags
        val mode = this.mode
        return run { DebugStruct(f, "OpenOptions").field("read", read).field("write", write).field("append", append).field("truncate", truncate).field("create", create).field("create_new", createNew).field("custom_flags", customFlags).field("mode", Mode(mode)).finish(); RustResult.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<OpenOptions> =
            object : TypeLayout<OpenOptions> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): OpenOptions =
                    OpenOptions(
                        (PrimitiveLayouts.BOOL).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + 1L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 2L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 3L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 4L),
                        (PrimitiveLayouts.BOOL).readNative(addr + 5L),
                        (PrimitiveLayouts.I32).readNative(addr + 8L),
                        (PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: OpenOptions) {
                    (PrimitiveLayouts.BOOL).writeNative(addr, value.read)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.write)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 2L, value.append)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 3L, value.truncate)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 4L, value.create)
                    (PrimitiveLayouts.BOOL).writeNative(addr + 5L, value.createNew)
                    (PrimitiveLayouts.I32).writeNative(addr + 8L, value.customFlags)
                    (PrimitiveLayouts.U32).writeNative(addr + 12L, value.mode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): OpenOptions =
                    OpenOptions(
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 1),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 2),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 3),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 4),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + 5),
                        (PrimitiveLayouts.I32).readBytes(buf, offset + 8),
                        (PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: OpenOptions) {
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.read)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.write)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 2, value.append)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 3, value.truncate)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 4, value.create)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + 5, value.createNew)
                    (PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.customFlags)
                    (PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.mode)
                }
            }

        public fun new(): OpenOptions = OpenOptions(read = false, write = false, append = false, truncate = false, create = false, createNew = false, customFlags = 0, mode = 438u)
    }
}

public open class FilePermissions(
    public var mode: ModeT,
) : FromInner<UInt, FilePermissions> {
    public open fun clone(): FilePermissions = FilePermissions(mode)

    public open fun readonly(): Boolean {
        // check if any class (owner, group, others) has write permission
        return (this.mode and 146u == 0u).toUByte()
    }

    public open fun setReadonly(readonly: Boolean) {
        if (readonly) {
            // remove write permission for all classes; equivalent to `chmod a - w<file>`
            this.mode = this.mode and 146u.inv()
        } else {
            // add write permission for all classes; equivalent to `chmod a + w<file>`
            this.mode = this.mode or 146u
        }
    }

    public open fun mode(): UInt = this.mode

    public open fun fmt(f: StringBuilder): RustResult<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val mode = this.mode
        return run { DebugStruct(f, "FilePermissions").field("mode", Mode(mode)).finish(); RustResult.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

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
                    (PrimitiveLayouts.U32).writeNative(addr, value.mode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FilePermissions =
                    FilePermissions(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FilePermissions) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.mode)
                }
            }

        public fun fromInner(mode: UInt): FilePermissions = FilePermissions(mode = mode)
    }
}

public open class FileTimes(
    public var accessed: SystemTime?,
    public var modified: SystemTime?,
    public var created: SystemTime?,
) {
    public open fun clone(): FileTimes = FileTimes(accessed, modified, created)

    public open fun setAccessed(t: SystemTime) {
        this.accessed = t
    }

    public open fun setModified(t: SystemTime) {
        this.modified = t
    }

    public open fun setCreated(t: SystemTime) {
        this.created = t
    }
}

public open class FileType(
    public var mode: ModeT,
) {
    public open fun clone(): FileType = FileType(mode)

    public open fun eq(other: FileType): Boolean = (this.masked() == other.masked()).toUByte()

    public override fun hashCode(): Int {
        var state = 0
        state = 31 * state + (this.masked()).hashCode()
        return state
    }

    public open fun isDir(): Boolean = this.isMatch(S_IFDIR)

    public open fun isFile(): Boolean = this.isMatch(S_IFREG)

    public open fun isSymlink(): Boolean = this.isMatch(S_IFLNK)

    public open fun isMatch(mode: ModeT): Boolean = (this.masked() == mode).toUByte()

    public open fun masked(): ModeT = this.mode and S_IFMT

    public open fun fmt(f: StringBuilder): RustResult<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val mode = this.mode
        return run { DebugStruct(f, "FileType").field("mode", Mode(mode)).finish(); RustResult.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<FileType> =
            object : TypeLayout<FileType> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FileType =
                    FileType(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileType) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.mode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileType =
                    FileType(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileType) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.mode)
                }
            }
    }
}

public open class DirBuilder(
    public var mode: ModeT,
) {
    public open fun mkdir(p: Path): RustResult<Unit, Error> = runPathWithCstr(p, { p1 -> cvt(io.github.kotlinmania.rustlang.libc.mkdir(p1.asPtr(), this.mode)).map({ Unit }) })

    public open fun setMode(mode: UInt) {
        this.mode = mode
    }

    public open fun fmt(f: StringBuilder): RustResult<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val mode = this.mode
        return run { DebugStruct(f, "DirBuilder").field("mode", Mode(mode)).finish(); RustResult.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<DirBuilder> =
            object : TypeLayout<DirBuilder> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): DirBuilder =
                    DirBuilder(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: DirBuilder) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.mode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DirBuilder =
                    DirBuilder(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DirBuilder) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.mode)
                }
            }

        public fun new(): DirBuilder = DirBuilder(mode = 511u)
    }
}

public open class Mode(
    public var value: ModeT,
) {
    public var field0: ModeT
        get() = value
        set(v) {
            value = v
        }

    public open fun clone(): Mode = Mode(value)

    // Format in octal, followed by the mode format used in `ls - l`.
    //
    // References:
    // https://pubs.opengroup.org/onlinepubs/9799919799/utilities/ls.html
    // https://www.gnu.org/software/libc/manual/htmlNode/Testing-File-Type.html
    // https://www.gnu.org/software/libc/manual/htmlNode/Permission-Bits.html
    //
    // Example:
    // 0o100664 (-rw-rw-r--)
    public open fun fmt(f: StringBuilder): RustResult<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
        val mode = this.value
        f.append("0o$mode")
        val entryType =
            when (mode and S_IFMT) {
                S_IFDIR -> 'd'
                S_IFBLK -> 'b'
                S_IFCHR -> 'c'
                S_IFLNK -> 'l'
                S_IFIFO -> 'p'
                S_IFREG -> '-'
                else -> { return RustResult.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
            }
        f.append(" (")
        f.append(entryType)
        // Owner permissions
        f.append(if (mode and S_IRUSR != 0u) {
            'r'
        } else {
            '-'
        })
        f.append(if (mode and S_IWUSR != 0u) {
            'w'
        } else {
            '-'
        })
        val ownerExecutable = mode and S_IXUSR != 0u
        val setuid = mode.toInt() and S_ISUID.toInt() != 0
        f.append(run {
            val matchVal = Pair(ownerExecutable, setuid)
            when {
                matchVal.first == true && matchVal.second == true -> 's'
                // executable and setuid
                matchVal.first == false && matchVal.second == true -> 'S'
                // setuid
                matchVal.first == true && matchVal.second == false -> 'x'
                // executable
                else -> '-'
            }
        })
        // Group permissions
        f.append(if (mode and S_IRGRP != 0u) {
            'r'
        } else {
            '-'
        })
        f.append(if (mode and S_IWGRP != 0u) {
            'w'
        } else {
            '-'
        })
        val groupExecutable = mode and S_IXGRP != 0u
        val setgid = mode.toInt() and S_ISGID.toInt() != 0
        f.append(run {
            val matchVal1 = Pair(groupExecutable, setgid)
            when {
                matchVal1.first == true && matchVal1.second == true -> 's'
                // executable and setgid
                matchVal1.first == false && matchVal1.second == true -> 'S'
                // setgid
                matchVal1.first == true && matchVal1.second == false -> 'x'
                // executable
                else -> '-'
            }
        })
        // Other permissions
        f.append(if (mode and S_IROTH != 0u) {
            'r'
        } else {
            '-'
        })
        f.append(if (mode and S_IWOTH != 0u) {
            'w'
        } else {
            '-'
        })
        val otherExecutable = mode and S_IXOTH != 0u
        val sticky = mode.toInt() and S_ISVTX.toInt() != 0
        f.append(run {
            val matchVal2 = Triple(entryType, otherExecutable, sticky)
            when {
                matchVal2.first == 'd' && matchVal2.second == true && matchVal2.third == true -> 't'
                // searchable and restricted deletion
                matchVal2.first == 'd' && matchVal2.second == false && matchVal2.third == true -> 'T'
                // restricted deletion
                matchVal2.second == true -> 'x'
                // executable
                else -> '-'
            }
        })
        return run { f.append(')'); RustResult.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<Mode> =
            object : TypeLayout<Mode> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Mode =
                    Mode(
                        (PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Mode) {
                    (PrimitiveLayouts.U32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Mode =
                    Mode(
                        (PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Mode) {
                    (PrimitiveLayouts.U32).writeBytes(buf, offset, value.value)
                }
            }
    }
}

/**
 * Aborts the process if a file descriptor is not open, if debug asserts are enabled
 *
 * Many IO syscalls can't be fully trusted about EBADF error codes because those
 * might get bubbled up from a remote FUSE server rather than the file descriptor
 * in the current process being invalid.
 *
 * So we check file flags instead which live on the file descriptor and not the underlying file.
 * The downside is that it costs an extra syscall, so we only do it for debug.
 */
public fun debugAssertFdIsOpen(fd: RawFd) {
    // this is similar to assertUnsafePrecondition() but it doesn't require const
    if (checkLibraryUb()) {
        if (fcntl(fd, F_GETFD) == -1 && Errno() == EBADF) {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        out.writeFmt("fatal runtime error: " + ("IO Safety violation: owned file descriptor already closed").toString() + ", aborting\n")
                    }
                }
                run {
                    "fatal runtime error: " + ("IO Safety violation: owned file descriptor already closed").toString() + ", aborting\n"
                }
                abort()
            }
        }
    }
}

public fun fileTimeToTimespec(time: SystemTime?): RustResult<Timespec, Error> =
    when {
        time != null && time!!.t.toTimespec() != null -> {
            val time1 = time
            RustResult.Ok<Timespec, Error>(ts)
        }
        time != null && time > UNIX_EPOCH -> {
            val time2 = time
            RustResult.Err<Timespec, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "timestamp is too large to set as a file time")))
        }
        time != null -> RustResult.Err<Timespec, Error>(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "timestamp is too small to set as a file time")))
        else -> RustResult.Ok<Timespec, Error>(run {
            val ts = default()
            ts.tvSec = 0
            ts.tvNsec = UTIME_OMIT
            ts
        })
    }

public open class TimesAttrlist internal constructor(
    bufCtorArg: List<MaybeUninit<Timespec>>,
    public var attrlist: Attrlist,
    public var numTimes: Int,
) {
    public var buf: List<MaybeUninit<Timespec>> = bufCtorArg
        internal set

    public open fun attrlist(): MutPtr = MutPtr((ConstPtr(((addrOf(this.attrlist))).rawAddress)).rawAddress)

    public open fun timesBuf(): MutPtr = MutPtr((ConstPtr((this.buf.asPtr()).rawAddress)).rawAddress)

    public open fun timesBufSize(): Int = this.numTimes * sizeOf<Timespec>(typeLayoutT = UnresolvedTypeLayout<Timespec>())

    public companion object {
        public operator fun invoke(
            buf: List<MaybeUninit<Timespec>>,
            attrlist: Attrlist,
            numTimes: Int,
        ): TimesAttrlist = TimesAttrlist(
            bufCtorArg = buf,
            attrlist = attrlist,
            numTimes = numTimes,
        )

        public fun fromTimes(times: FileTimes): RustResult<TimesAttrlist, Error> {
            val thisRef = TimesAttrlist(buf = List(3) { MaybeUninit.uninit(UnresolvedTypeLayout<Timespec>()) }, attrlist = Attrlist(), numTimes = 0)
            thisRef.attrlist.bitmapcount = ATTR_BIT_MAP_COUNT
            if (times.created != null) {
                thisRef.buf[thisRef.numTimes].write(when (val tryResult = fileTimeToTimespec(times.created)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<TimesAttrlist, Error>(tryResult.errValue()) })
                thisRef.numTimes += 1
                thisRef.attrlist.commonattr = thisRef.attrlist.commonattr or ATTR_CMN_CRTIME
            }
            if (times.modified != null) {
                thisRef.buf[thisRef.numTimes].write(when (val tryResult = fileTimeToTimespec(times.modified)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<TimesAttrlist, Error>(tryResult.errValue()) })
                thisRef.numTimes += 1
                thisRef.attrlist.commonattr = thisRef.attrlist.commonattr or ATTR_CMN_MODTIME
            }
            if (times.accessed != null) {
                thisRef.buf[thisRef.numTimes].write(when (val tryResult = fileTimeToTimespec(times.accessed)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<TimesAttrlist, Error>(tryResult.errValue()) })
                thisRef.numTimes += 1
                thisRef.attrlist.commonattr = thisRef.attrlist.commonattr or ATTR_CMN_ACCTIME
            }
            return RustResult.Ok<TimesAttrlist, Error>(thisRef)
        }
    }
}

public fun readdir(path: Path): RustResult<ReadDir, Error> {
    val ptr = when (val tryResult = runPathWithCstr(path, { p -> RustResult.Ok(opendir(p.asPtr())) })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ReadDir, Error>(tryResult.errValue()) }
    return if (ptr.isNull()) {
        RustResult.Err<ReadDir, Error>(Error.lastOsError())
    } else {
        val root = path.toPathBuf()
        val inner = InnerReadDir(dirp = DirStream(ptr), root = root)
        RustResult.Ok<ReadDir, Error>(ReadDir.new(inner))
    }
}

public fun unlink(p: CStr): RustResult<Unit, Error> = cvt(io.github.kotlinmania.rustlang.libc.unlink(p.asPtr())).map({ Unit })

public fun rename(old: CStr, new: CStr): RustResult<Unit, Error> = cvt(io.github.kotlinmania.rustlang.libc.rename(old.asPtr(), new.asPtr())).map({ Unit })

public fun renameNoreplace(old: CStr, new: CStr): RustResult<Unit, Error> {
    syscall(renameat2, olddirfd, cInt, oldpath, libc, cChar, newdirfd, cInt, newpath, libc, cChar, flags, libc, cUint, cInt)
    // `RENAME_NOREPLACE` is `cUint` on linux and `cInt` on Android, so casting is needed here.
    val flags = RENAME_NOREPLACE.toUInt()
    val res = cvt(renameat2(AT_FDCWD, old.asPtr(), AT_FDCWD, new.asPtr(), flags))
    return when (res) { is RustResult.Ok<*, *> -> {
        RustResult.Ok<Unit, Error>(Unit)
    }; is RustResult.Err<*, *> -> {
        val err = res.errValue()
        when (val matchVal = err.rawOsError()) {
            // The kernel predates `renameat2`.
            ENOSYS -> linkThenUnlink(old, new)
            // The filesystem does not support `RENAME_NOREPLACE`.
            // `EINVAL` also covers misuse, so report it unchanged unless the fallback found `new`.
            EINVAL -> run {
                val matchVal1 = linkThenUnlink(old, new)
                when {
                    matchVal1 is RustResult.Err<*, *> && matchVal1.errValue().rawOsError() != EEXIST -> {
                        val fallback = matchVal1.errValue()
                        RustResult.Err<Unit, Error>(err)
                    }
                    else -> {
                        val res1 = matchVal1
                        res1
                    }
                }
            }
            else -> RustResult.Err<Unit, Error>(err)
        }
    } }
}

public fun setPerm(p: CStr, perm: FilePermissions): RustResult<Unit, Error> = cvtR({ chmod(p.asPtr(), perm.mode) }).map({ Unit })

public fun setPermNofollow(p: CStr, perm: FilePermissions): RustResult<Unit, Error> {
    /**
     * Helper function for fallback open with `O_NOFOLLOW` + `fchmod` behavior. This will
     * successfully change the permissions of non-symlinks and fail when there's a symlink.
     */
    fun openAndSetPermissions(p1: CStr, perm1: FilePermissions): RustResult<Unit, Error> {
        val options = OpenOptions.new()
        options.read(true)
        // ESP-IDF and Horizon do not support O_NOFOLLOW, so we skip setting it.
        // Their filesystems do not have symbolic links, so no special handling is required.
        run {
            options.customFlags(O_NOFOLLOW)
        }
        // SAFETY: Since this function is called with `withNativePath`
        // and that successfully converted the `Path` to a `CString`,
        // it should be safe to convert the `CStr` back to a `Path`.
        val osStr = OsStr.fromEncodedBytesUnchecked(p1.toBytes())
        return when (val tryResult = options.open(Path.new(osStr))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }.setPermissions(Permissions.fromInner(perm1))
    }
    // This res value is modified for platforms that support the `fchmodat` syscall.
    var res: RustResult<Unit, Error> = RustResult.Err<Unit, Error>(ErrorKind.Unsupported.into())
    // These platforms support `fchmodat`, so utilize this syscall over `open` + `fchmod`.
    // We can *not* use this on Android due to <https://github.com/android/ndk/issues/1258>.
    run {
        res = cvtR({ fchmodat(AT_FDCWD, p.asPtr(), perm.mode, AT_SYMLINK_NOFOLLOW) }).map({ Unit })
    }
    // If fchmodat fails with `ErrorKind.Unsupported` fallback to using open + fchmod. This is just in case
    // for older systems like Ubuntu 20.04 where fchmodat fails with EOPNOTSUPP on both regular files and
    // symlinks when AT_SYMLINK_NOFOLLOW is passed in.
    return when (res) { is RustResult.Ok<*, *> -> {
        RustResult.Ok<Unit, Error>(Unit)
    }; is RustResult.Err<*, *> -> {
        val err = res.errValue()
        if (err.kind() == ErrorKind.Unsupported) {
            run {
                val matchVal = openAndSetPermissions(p, perm)
                when (matchVal) { is RustResult.Ok<*, *> -> {
                    return RustResult.Ok<Unit, Error>(Unit)
                }; is RustResult.Err<*, *> -> {
                    val e = matchVal.errValue()
                    if (e.kind() == ErrorKind.FilesystemLoop) {
                        // When open is used with O_NOFOLLOW flag, if the trailing component of
                        // a path is a symbolic link, it should fail with ELOOP error. Instead of
                        // returning `FilesystemLoop`, this returns `Unsupported` to keep it consistent
                        // with what `fchmodat` would return when chmoding a symlink using AT_SYMLINK_NOFOLLOW.
                        return RustResult.Err<Unit, Error>(err)
                    }
                    return RustResult.Err<Unit, Error>(e)
                } }
            }
        }
        RustResult.Err<Unit, Error>(err)
    } }
}

public fun rmdir(p: CStr): RustResult<Unit, Error> = cvt(io.github.kotlinmania.rustlang.libc.rmdir(p.asPtr())).map({ Unit })

public fun readlink(cPath: CStr): RustResult<PathBuf, Error> {
    val p = cPath.asPtr()
    val buf = ArrayList(256)
    while (true) {
        val bufRead = (when (val tryResult = cvt(io.github.kotlinmania.rustlang.libc.readlink(p, buf.asMutPtr(), buf.capacity()))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<PathBuf, Error>(tryResult.errValue()) }).toInt()
        run {
            buf.setLen(bufRead)
        }
        if (bufRead != buf.capacity()) {
            buf.shrinkToFit()
            return RustResult.Ok<PathBuf, Error>(PathBuf.from(OsString.fromVec(buf)))
        }
        // Trigger the internal buffer resizing logic of `List` by requiring
        // more space than the current capacity. The length is guaranteed to be
        // the same as the capacity due to the if statement above.
        buf.reserve(1)
    }
}

public fun symlink(original: CStr, link: CStr): RustResult<Unit, Error> = cvt(io.github.kotlinmania.rustlang.libc.symlink(original.asPtr(), link.asPtr())).map({ Unit })

public fun link(original: CStr, link: CStr): RustResult<Unit, Error> {
    when (val tryResult = cvt(io.github.kotlinmania.rustlang.libc.link(original.asPtr(), link.asPtr()))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    return RustResult.Ok<Unit, Error>(Unit)
}

public fun stat(p: CStr): RustResult<FileAttr, Error> {
    when (val matchVal = tryStatx(AT_FDCWD, p.asPtr(), AT_STATX_SYNC_AS_STAT, STATX_BASIC_STATS or STATX_BTIME)) {
        null -> {}
        else -> {
            val ret = matchVal
            return ret
        }
    }
    val stat: Stat64  = MaybeUninit.zeroed(UnresolvedTypeLayout<Stat64>()).assumeInit()
    when (val tryResult = cvt(Stat64(p.asPtr(), stat))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<FileAttr, Error>(tryResult.errValue()) }
    return RustResult.Ok<FileAttr, Error>(FileAttr.fromStat64(stat))
}

public fun lstat(p: CStr): RustResult<FileAttr, Error> {
    when (val matchVal = tryStatx(AT_FDCWD, p.asPtr(), AT_SYMLINK_NOFOLLOW or AT_STATX_SYNC_AS_STAT, STATX_BASIC_STATS or STATX_BTIME)) {
        null -> {}
        else -> {
            val ret = matchVal
            return ret
        }
    }
    val stat: Stat64  = MaybeUninit.zeroed(UnresolvedTypeLayout<Stat64>()).assumeInit()
    when (val tryResult = cvt(lstat64(p.asPtr(), stat))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<FileAttr, Error>(tryResult.errValue()) }
    return RustResult.Ok<FileAttr, Error>(FileAttr.fromStat64(stat))
}

public fun canonicalize(path: CStr): RustResult<PathBuf, Error> {
    val r = realpath(path.asPtr(), MutPtr(0L))
    if (r.isNull()) {
        return RustResult.Err<PathBuf, Error>(Error.lastOsError())
    }
    return RustResult.Ok<PathBuf, Error>(PathBuf.from(OsString.fromVec(run {
        val buf = CStr.fromPtr(r).toBytes().toMutableList()
        free(mutPtrFromRustCast(r))
        buf
    })))
}

public fun linkThenUnlink(old: CStr, new: CStr): RustResult<Unit, Error> {
    when (val tryResult = link(old, new)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    return unlink(old)
}

public fun openFrom(from: Path): RustResult<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>, Error> {
    val reader = when (val tryResult = File.open(from)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>, Error>(tryResult.errValue()) }
    val metadata = when (val tryResult = reader.metadata()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>, Error>(tryResult.errValue()) }
    if (!metadata.isFile()) {
        return RustResult.Err<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>, Error>(NOT_FILE_ERROR)
    }
    return RustResult.Ok<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>, Error>(Pair(reader, metadata))
}

public fun setTimesImpl(p: CStr, times: FileTimes, followSymlinks: Boolean): RustResult<Unit, Error> {
    // Apple platforms use setattrlist which supports setting times on symlinks
    val ta = when (val tryResult = TimesAttrlist.fromTimes(times)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    val options =
        if (followSymlinks) {
            0
        } else {
            FSOPT_NOFOLLOW
        }
    when (val tryResult = cvt(setattrlist(p.asPtr(), ta.attrlist(), ta.timesBuf(), ta.timesBufSize(), options.toUInt()))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    return RustResult.Ok<Unit, Error>(Unit)
}

public fun setTimes(p: CStr, times: FileTimes): RustResult<Unit, Error> = setTimesImpl(p, times, true)

public fun setTimesNofollow(p: CStr, times: FileTimes): RustResult<Unit, Error> = setTimesImpl(p, times, false)

public fun openToAndSetPermissions(to: Path, readerMetadata: Metadata): RustResult<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>, Error> {
    val writer = when (val tryResult = OpenOptions.new().write(true).create(true).truncate(true).open(to)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>, Error>(tryResult.errValue()) }
    val writerMetadata = when (val tryResult = writer.metadata()) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>, Error>(tryResult.errValue()) }
    return RustResult.Ok<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>, Error>(Pair(writer, writerMetadata))
}

public open class CachedFileMetadata(
    public var field0: File,
    public var field1: Metadata,
) : Read<CachedFileMetadata>, Write<CachedFileMetadata> {
    public override fun read(buf: MutableList<UByte>): Result<Int> = this.field0.read(buf)

    public override fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = this.field0.readVectored(bufs)

    public override fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = this.field0.readBuf(cursor)

    public override fun isReadVectored(): Boolean = this.field0.isReadVectored()

    public override fun readToEnd(buf: MutableList<UByte>): Result<Int> = this.field0.readToEnd(buf)

    public override fun readToString(buf: String): Result<Int> = this.field0.readToString(buf)

    public override fun write(buf: List<UByte>): Result<Int> = this.field0.write(buf)

    public override fun writeVectored(bufs: List<IoSlice>): Result<Int> = this.field0.writeVectored(bufs)

    public override fun isWriteVectored(): Boolean = this.field0.isWriteVectored()

    public override fun flush(): Result<Unit> = this.field0.flush()
}

public fun copy(from: Path, to: Path): RustResult<ULong, Error> {
    val (reader, readerMetadata) = when (val tryResult = openFrom(from)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ULong, Error>(tryResult.errValue()) }
    val (writer, writerMetadata) = when (val tryResult = openToAndSetPermissions(to, readerMetadata)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<ULong, Error>(tryResult.errValue()) }
    return io.github.kotlinmania.rustlang.alloc.io.copy.copy(CachedFileMetadata(reader, readerMetadata), CachedFileMetadata(writer, writerMetadata))
}

public fun chown(path: Path, uid: UInt, gid: UInt): RustResult<Unit, Error> =
    runPathWithCstr(path, { path1 ->
        cvt(io.github.kotlinmania.rustlang.libc.chown(path1.asPtr(), uid, gid)).map({ Unit })
    })

public fun fchown(fd: CInt, uid: UInt, gid: UInt): RustResult<Unit, Error> {
    when (val tryResult = cvt(io.github.kotlinmania.rustlang.libc.fchown(fd, uid, gid))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    return RustResult.Ok<Unit, Error>(Unit)
}

public fun lchown(path: Path, uid: UInt, gid: UInt): RustResult<Unit, Error> =
    runPathWithCstr(path, { path1 ->
        cvt(io.github.kotlinmania.rustlang.libc.lchown(path1.asPtr(), uid, gid)).map({ Unit })
    })

public fun chroot(dir: Path): RustResult<Unit, Error> = runPathWithCstr(dir, { dir1 -> cvt(io.github.kotlinmania.rustlang.libc.chroot(dir1.asPtr())).map({ Unit }) })

public fun mkfifo(path: Path, mode: UInt): RustResult<Unit, Error> =
    runPathWithCstr(path, { path1 ->
        cvt(io.github.kotlinmania.rustlang.libc.mkfifo(path1.asPtr(), mode.tryInto().unwrap())).map({ Unit })
    })

public fun openatNofollowDironly(parentFd: RawFd?, p: CStr): RustResult<OwnedFd, Error> {
    val fd = when (val tryResult = cvtR({ openat((parentFd ?: AT_FDCWD), p.asPtr(), O_CLOEXEC or O_RDONLY or O_NOFOLLOW or O_DIRECTORY) })) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<OwnedFd, Error>(tryResult.errValue()) }
    return RustResult.Ok<OwnedFd, Error>(OwnedFd.fromRawFd(fd))
}

public fun fdreaddir(dirFd: OwnedFd): RustResult<Pair<ReadDir, RawFd>, Error> {
    val ptr = fdopendir(dirFd.asRawFd())
    if (ptr.isNull()) {
        return RustResult.Err<Pair<ReadDir, RawFd>, Error>(Error.lastOsError())
    }
    val dirp = DirStream(ptr)
    // file descriptor is automatically closed by libc.closedir() now, so give up ownership
    val newParentFd = dirFd.intoRawFd()
    // a valid root is not needed because we do not call any functions involving the full path
    // of the `DirEntry`s.
    val dummyRoot = PathBuf.new()
    val inner = InnerReadDir(dirp = dirp, root = dummyRoot)
    return RustResult.Ok<Pair<ReadDir, RawFd>, Error>(Pair(ReadDir.new(inner), newParentFd))
}

public fun isDir(ent: DirEntry): Boolean? =
    when (ent.entry.dType) {
        DT_UNKNOWN -> null
        DT_DIR -> true
        else -> false
    }

public fun isEnoent(result: RustResult<Unit, Error>): Boolean =
    if (result is RustResult.Err<*, *> &&
        when (val matchVal = err.rawOsError()) {
            ENOENT -> true
            else -> false
        }) {
        true
    } else {
        false
    }

/**
 * `parentFd` being `None` indicates that this is the root where we start.
 * In that case, `path` will only be deleted if it is a directory.
 * When it is `Some`, this will delete anything.
 */
public fun removeDirAllRecursive(parentFd: RawFd?, path: CStr): RustResult<Unit, Error> {
    // try opening as directory
    val fd =
        run {
            val matchVal = openatNofollowDironly(parentFd, path)
            when {
                matchVal is RustResult.Err<*, *> && when (val matchVal1 = matchVal.errValue().rawOsError()) {
                    ENOTDIR, ELOOP -> true
                    else -> false
                } -> {
                    val err = matchVal.errValue()
                    // not a directory - don't traverse further
                    // (for symlinks, older Linux kernels may return ELOOP instead of ENOTDIR)
                    return when (parentFd) {
                        // ...unless this was supposed to be the deletion root directory. We don't want
                        // to delete a file at the root, so we just forward the error.
                        null -> RustResult.Err<Unit, Error>(err)
                        // unlink...
                        else -> {
                            val parentFd1 = parentFd
                            cvt(unlinkat(parentFd1, path.asPtr(), 0)).map({ drop(it) })
                        }
                    }
                }
                else -> {
                    val result = matchVal
                    when (val tryResult = result) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
                }
            }
        }
    // open the directory passing ownership of the fd
    val (dir, fd1) = when (val tryResult = fdreaddir(fd)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    // For WASI all directory entries for this directory are read first
    // before any removal is done. This works around the fact that the
    // WASIp1 API for reading directories is not well-designed for handling
    // mutations between invocations of reading a directory. By reading all
    // the entries at once this ensures that, at least without concurrent
    // modifications, it should be possible to delete everything.
    val dir1 = dir.collect<Vec>()
    for (child in dir1) {
        val child1 = when (val tryResult = child) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        // we need an inner try block, because if one of these
        // directories has already been deleted, then we need to
        // continue the loop, not return ok.
        val result1: RustResult<Unit, Error> =
            when (val matchVal2 = isDir(child1)) {
                true -> {
                    when (val tryResult = removeDirAllRecursive(fd1, child1.name)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
                }
                false -> {
                    when (val tryResult = cvt(unlinkat(fd1, child1.name.asPtr(), 0))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
                }
                null -> {
                    // POSIX specifies that calling unlink()/unlinkat(..., 0) on a directory can succeed
                    // if the process has the appropriate privileges. This however can causing orphaned
                    // directories requiring an fsck e.g. on Solaris and Illumos. So we try recursing
                    // into it first instead of trying to unlink() it.
                    when (val tryResult = removeDirAllRecursive(fd1, child1.name)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
                }
            }
        if (result1.isErr() && !isEnoent(result1)) {
            return result1
        }
    }
    // unlink the directory after removing its contents
    when (val tryResult = ignoreNotfound(cvt(unlinkat((parentFd ?: AT_FDCWD), path.asPtr(), AT_REMOVEDIR)))) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    return RustResult.Ok<Unit, Error>(Unit)
}

public fun removeDirAllModern(p: CStr): RustResult<Unit, Error> {
    // We cannot just call removeDirAllRecursive() here because that would not delete a passed
    // symlink. No need to worry about races, because removeDirAllRecursive() does not recurse
    // into symlinks.
    val attr = when (val tryResult = lstat(p)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
    return if (attr.fileType().isSymlink()) {
        // This would error in `removeDirAllRecursive` as it is not a directory.
        // We promise to delete directory symlinks, so use `unlink` to do that. On Unix, we
        // inadvertently also delete symlinks to files.
        unlink(p)
    } else {
        removeDirAllRecursive(null, p)
    }
}

public fun removeDirAll(p: Path): RustResult<Unit, Error> = runPathWithCstr(p, ::removeDirAllModern)
