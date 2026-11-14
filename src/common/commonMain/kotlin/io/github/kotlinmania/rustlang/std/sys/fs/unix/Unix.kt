// port-lint: source sys/fs/unix.rs
package io.github.kotlinmania.rustlang.std.sys.fs.unix

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.chmod
import io.github.kotlinmania.libc.closedir
import io.github.kotlinmania.libc.fchmod
import io.github.kotlinmania.libc.fcntl
import io.github.kotlinmania.libc.fdopendir
import io.github.kotlinmania.libc.fuchsia.AT_EMPTY_PATH
import io.github.kotlinmania.libc.fuchsia.AT_FDCWD
import io.github.kotlinmania.libc.fuchsia.AT_REMOVEDIR
import io.github.kotlinmania.libc.fuchsia.AT_SYMLINK_NOFOLLOW
import io.github.kotlinmania.libc.fuchsia.DT_BLK
import io.github.kotlinmania.libc.fuchsia.DT_CHR
import io.github.kotlinmania.libc.fuchsia.DT_DIR
import io.github.kotlinmania.libc.fuchsia.DT_FIFO
import io.github.kotlinmania.libc.fuchsia.DT_LNK
import io.github.kotlinmania.libc.fuchsia.DT_REG
import io.github.kotlinmania.libc.fuchsia.DT_SOCK
import io.github.kotlinmania.libc.fuchsia.EBADF
import io.github.kotlinmania.libc.fuchsia.EEXIST
import io.github.kotlinmania.libc.fuchsia.EINVAL
import io.github.kotlinmania.libc.fuchsia.ELOOP
import io.github.kotlinmania.libc.fuchsia.ENOENT
import io.github.kotlinmania.libc.fuchsia.ENOSYS
import io.github.kotlinmania.libc.fuchsia.ENOTDIR
import io.github.kotlinmania.libc.fuchsia.F_GETFD
import io.github.kotlinmania.libc.fuchsia.F_GETFL
import io.github.kotlinmania.libc.fuchsia.ModeT
import io.github.kotlinmania.libc.fuchsia.O_ACCMODE
import io.github.kotlinmania.libc.fuchsia.O_APPEND
import io.github.kotlinmania.libc.fuchsia.O_CLOEXEC
import io.github.kotlinmania.libc.fuchsia.O_CREAT
import io.github.kotlinmania.libc.fuchsia.O_DIRECTORY
import io.github.kotlinmania.libc.fuchsia.O_EXCL
import io.github.kotlinmania.libc.fuchsia.O_NOFOLLOW
import io.github.kotlinmania.libc.fuchsia.O_RDONLY
import io.github.kotlinmania.libc.fuchsia.O_RDWR
import io.github.kotlinmania.libc.fuchsia.O_TRUNC
import io.github.kotlinmania.libc.fuchsia.O_WRONLY
import io.github.kotlinmania.libc.fuchsia.SEEK_CUR
import io.github.kotlinmania.libc.fuchsia.SEEK_END
import io.github.kotlinmania.libc.fuchsia.SEEK_SET
import io.github.kotlinmania.libc.fuchsia.S_IFBLK
import io.github.kotlinmania.libc.fuchsia.S_IFCHR
import io.github.kotlinmania.libc.fuchsia.S_IFDIR
import io.github.kotlinmania.libc.fuchsia.S_IFIFO
import io.github.kotlinmania.libc.fuchsia.S_IFLNK
import io.github.kotlinmania.libc.fuchsia.S_IFMT
import io.github.kotlinmania.libc.fuchsia.S_IFREG
import io.github.kotlinmania.libc.fuchsia.S_IFSOCK
import io.github.kotlinmania.libc.fuchsia.S_IRGRP
import io.github.kotlinmania.libc.fuchsia.S_IROTH
import io.github.kotlinmania.libc.fuchsia.S_IRUSR
import io.github.kotlinmania.libc.fuchsia.S_ISGID
import io.github.kotlinmania.libc.fuchsia.S_ISUID
import io.github.kotlinmania.libc.fuchsia.S_ISVTX
import io.github.kotlinmania.libc.fuchsia.S_IWGRP
import io.github.kotlinmania.libc.fuchsia.S_IWOTH
import io.github.kotlinmania.libc.fuchsia.S_IWUSR
import io.github.kotlinmania.libc.fuchsia.S_IXGRP
import io.github.kotlinmania.libc.fuchsia.S_IXOTH
import io.github.kotlinmania.libc.fuchsia.S_IXUSR
import io.github.kotlinmania.libc.fuchsia.Timespec
import io.github.kotlinmania.libc.fuchsia.UTIME_OMIT
import io.github.kotlinmania.libc.openat
import io.github.kotlinmania.libc.opendir
import io.github.kotlinmania.libc.psp.free
import io.github.kotlinmania.libc.realpath
import io.github.kotlinmania.libc.timespec.default
import io.github.kotlinmania.libc.unix.bsd.apple.ATTR_BIT_MAP_COUNT
import io.github.kotlinmania.libc.unix.bsd.apple.ATTR_CMN_ACCTIME
import io.github.kotlinmania.libc.unix.bsd.apple.ATTR_CMN_CRTIME
import io.github.kotlinmania.libc.unix.bsd.apple.ATTR_CMN_MODTIME
import io.github.kotlinmania.libc.unix.bsd.apple.Attrlist
import io.github.kotlinmania.libc.unix.bsd.apple.F_FULLFSYNC
import io.github.kotlinmania.libc.unix.bsd.netbsdlike.netbsd.dirfd
import io.github.kotlinmania.libc.unix.hurd.AT_STATX_SYNC_AS_STAT
import io.github.kotlinmania.libc.unix.hurd.STATX_BASIC_STATS
import io.github.kotlinmania.libc.unix.hurd.STATX_BTIME
import io.github.kotlinmania.libc.unlinkat
import io.github.kotlinmania.rustlang.alloc.ffi.cstr.CString
import io.github.kotlinmania.rustlang.core.ffi.CSizeT
import io.github.kotlinmania.rustlang.core.ffi.CSsizeT
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.ObjectSlotLayout
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.fs.Metadata
import io.github.kotlinmania.rustlang.std.fs.TryLockError
import io.github.kotlinmania.rustlang.std.mem
import io.github.kotlinmania.rustlang.std.os.fd.AsFd
import io.github.kotlinmania.rustlang.std.os.fd.AsRawFd
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.IntoRawFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.ptr
import io.github.kotlinmania.rustlang.std.sys.AsInner
import io.github.kotlinmania.rustlang.std.sys.AsInnerMut
import io.github.kotlinmania.rustlang.std.sys.FromInner
import io.github.kotlinmania.rustlang.std.sys.IntoInner
import io.github.kotlinmania.rustlang.std.sys.fd.hermit.FileDesc
import io.github.kotlinmania.rustlang.std.sys.fs.common.NOT_FILE_ERROR
import io.github.kotlinmania.rustlang.std.sys.fs.hermit.FileAttr
import io.github.kotlinmania.rustlang.std.sys.fs.unix.dir.Dir
import io.github.kotlinmania.rustlang.std.sys.fs.windows.getPath
import io.github.kotlinmania.rustlang.std.sys.helpers.ignoreNotfound
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runPathWithCstr
import io.github.kotlinmania.rustlang.std.sys.io.error.generic.errno
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvtR
import io.github.kotlinmania.rustlang.std.sys.pal.unix.unsupported
import io.github.kotlinmania.rustlang.std.sys.stdio.motor.panicOutput
import io.github.kotlinmania.rustlang.std.sys.time.sgx.SystemTime
import io.github.kotlinmania.rustlang.std.sys.time.sgx.UNIX_EPOCH
import io.github.kotlinmania.rustlang.std.thread.Result

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
) : AsInner<FileDesc>, AsInnerMut<FileDesc>, IntoInner<FileDesc>, FromInner<FileDesc, File>, AsFd, AsRawFd, IntoRawFd, FromRawFd<File, Any?, Any> {
    public var field0: FileDesc
        get() = value
        set(v) {
            value = v
        }

    public open fun fileAttr(): Result<FileAttr> {
        val fd = this.asRawFd()
        when (val matchVal = tryStatx(fd, constPtrFromRustCast("".asPtr()), AT_EMPTY_PATH or AT_STATX_SYNC_AS_STAT, STATX_BASIC_STATS or STATX_BTIME)) {
            null -> {}
            else -> {
                val ret = matchVal
                return ret
            }
        }
        val stat: Stat64?  = null
        when (val tryResult = cvt(fstat64(fd, stat))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(FileAttr.fromStat64(stat))
    }

    public open fun fsync(): Result<Unit> {
        fun osFsync(fd: CInt): CInt = fcntl(fd, F_FULLFSYNC)
        when (val tryResult = cvtR({ osFsync(this.asRawFd()) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public open fun datasync(): Result<Unit> {
        fun osDatasync(fd: CInt): CInt = fcntl(fd, F_FULLFSYNC)
        when (val tryResult = cvtR({ osDatasync(this.asRawFd()) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public open fun lock(): Result<Unit> = cfgSelect(any, targetOs, "freebsd", targetOs, "fuchsia", targetOs, "hurd", targetOs, "linux", targetOs, "netbsd", targetOs, "openbsd", targetOs, "cygwin", targetOs, "illumos", targetOs, "aix", targetOs, "haiku", targetOs, "android", targetOs, "redox", targetVendor, "apple", cvt, libc, flock, this, asRawFd, libc, LOCK_EX, Ok, Err, io, constError, io, ErrorKind, Unsupported, "lock() not supported")

    public open fun lockShared(): Result<Unit> = cfgSelect(any, targetOs, "freebsd", targetOs, "fuchsia", targetOs, "hurd", targetOs, "linux", targetOs, "netbsd", targetOs, "openbsd", targetOs, "cygwin", targetOs, "illumos", targetOs, "aix", targetOs, "haiku", targetOs, "android", targetOs, "redox", targetVendor, "apple", cvt, libc, flock, this, asRawFd, libc, LOCK_SH, Ok, Err, io, constError, io, ErrorKind, Unsupported, "lock_shared() not supported")

    public open fun tryLock(): Result<Unit, TryLockError> = cfgSelect(any, targetOs, "freebsd", targetOs, "fuchsia", targetOs, "hurd", targetOs, "linux", targetOs, "netbsd", targetOs, "openbsd", targetOs, "cygwin", targetOs, "illumos", targetOs, "aix", targetOs, "haiku", targetOs, "android", targetOs, "redox", targetVendor, "apple", result, cvt, libc, flock, this, asRawFd, libc, LOCK_EX, libc, LOCK_NB, Err, err, result, err, kind, io, ErrorKind, WouldBlock, Err, TryLockError, WouldBlock, Err, TryLockError, Error, err, Ok, Err, TryLockError, Error, io, constError, io, ErrorKind, Unsupported, "try_lock() not supported")

    public open fun tryLockShared(): Result<Unit, TryLockError> = cfgSelect(any, targetOs, "freebsd", targetOs, "fuchsia", targetOs, "hurd", targetOs, "linux", targetOs, "netbsd", targetOs, "openbsd", targetOs, "cygwin", targetOs, "illumos", targetOs, "aix", targetOs, "haiku", targetOs, "android", targetOs, "redox", targetVendor, "apple", result, cvt, libc, flock, this, asRawFd, libc, LOCK_SH, libc, LOCK_NB, Err, err, result, err, kind, io, ErrorKind, WouldBlock, Err, TryLockError, WouldBlock, Err, TryLockError, Error, err, Ok, Err, TryLockError, Error, io, constError, io, ErrorKind, Unsupported, "try_lock_shared() not supported")

    public open fun unlock(): Result<Unit> = cfgSelect(any, targetOs, "freebsd", targetOs, "fuchsia", targetOs, "hurd", targetOs, "linux", targetOs, "netbsd", targetOs, "openbsd", targetOs, "cygwin", targetOs, "illumos", targetOs, "aix", targetOs, "haiku", targetOs, "android", targetOs, "redox", targetVendor, "apple", cvt, libc, flock, this, asRawFd, libc, LOCK_UN, Ok, Err, io, constError, io, ErrorKind, Unsupported, "unlock() not supported")

    public open fun truncate(size: ULong): Result<Unit> {
        val size1: Off64T = when (val tryResult = size.tryInto().mapErr({ e -> io.Error.new(io.ErrorKind.InvalidInput, e) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return cvtR({ ftruncate64(this.asRawFd(), size1) }).map(drop)
    }

    public open fun read(buf: MutableList<UByte>): Result<Int> = this.value.read(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = this.value.readVectored(bufs)

    public open fun isReadVectored(): Boolean = this.value.isReadVectored()

    public open fun readAt(buf: MutableList<UByte>, offset: ULong): Result<Int> = this.value.readAt(buf, offset)

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = this.value.readBuf(cursor)

    public open fun readBufAt(cursor: BorrowedCursor<UByte>, offset: ULong): Result<Unit> = this.value.readBufAt(cursor, offset)

    public open fun readVectoredAt(bufs: MutableList<IoSliceMut>, offset: ULong): Result<Int> = this.value.readVectoredAt(bufs, offset)

    public open fun write(buf: List<UByte>): Result<Int> = this.value.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = this.value.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.value.isWriteVectored()

    public open fun writeAt(buf: List<UByte>, offset: ULong): Result<Int> = this.value.writeAt(buf, offset)

    public open fun writeVectoredAt(bufs: List<IoSlice>, offset: ULong): Result<Int> = this.value.writeVectoredAt(bufs, offset)

    public open fun flush(): Result<Unit> = Result.Ok(Unit)

    public open fun seek(pos: SeekFrom): Result<ULong> {
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
        val n = when (val tryResult = cvt(lseek64(this.asRawFd(), pos1.toLong(), whence))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(n.toULong())
    }

    public open fun size(): Result<ULong>? =
        run {
            val matchVal = this.fileAttr().map({ attr -> attr.size() })
            when {
                // Fall back to default implementation if the returned size is 0,
                // we might be in a proc mount.
                matchVal is Result.Ok<*> && matchVal.value == 0 -> null
                else -> {
                    val result = matchVal
                    result
                }
            }
        }

    public open fun tell(): Result<ULong> = this.seek(SeekFrom.Current(0))

    public open fun duplicate(): Result<File> = this.value.duplicate().map({ File(it) })

    public open fun setPermissions(perm: FilePermissions): Result<Unit> {
        when (val tryResult = cvtR({ fchmod(this.asRawFd(), perm.mode) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(Unit)
    }

    public open fun setTimes(times: FileTimes): Result<Unit> = cfgSelect(any, targetOs, "redox", targetOs, "espidf", targetOs, "horizon", targetOs, "nuttx", targetOs, "l4re", times, Err, io, constError, io, ErrorKind, Unsupported, "setting file times not supported", targetVendor, "apple", ta, TimesAttrlist, fromTimes, times, cvt, libc, fsetattrlist, this, asRawFd, ta, attrlist, ta, timesBuf, ta, timesBufSize, 0, Ok, targetOs, "android", times, fileTimeToTimespec, times, accessed, fileTimeToTimespec, times, modified, cvt, weak, futimens, fd, cInt, times, libc, timespec, cInt, futimens, get, Some, futimens, futimens, this, asRawFd, times, asPtr, None, Err, io, constError, io, ErrorKind, Unsupported, "setting file times requires Android API level >= 19", Ok, cfg, all, targetOs, "linux", targetEnv, "gnu", targetPointerWidth, "32", not, targetArch, "riscv32", sys, pal, time, timespec64, sys, pal, weak, weak, weak, futimens64, fd, cInt, times, timespec64, cInt, Some, futimens64, futimens64, get, toTimespec, time, Option, SystemTime, time, map, time, time, t, toTimespec64, unwrapOr, timespec64, new, 0, libc, UTIME_OMIT, times, toTimespec, times, accessed, toTimespec, times, modified, cvt, futimens64, this, asRawFd, times, asPtr, Ok, times, fileTimeToTimespec, times, accessed, fileTimeToTimespec, times, modified, cvt, libc, futimens, this, asRawFd, times, asPtr, Ok)

    public override fun asInner(): FileDesc = this.value

    public override fun asInnerMut(): FileDesc = this.value

    public override fun intoInner(): FileDesc = this.value

    public override fun asFd(): BorrowedFd = this.value.asFd()

    public override fun asRawFd(): RawFd = this.value.asRawFd()

    public override fun intoRawFd(): RawFd = this.value.intoRawFd()

    public open fun fmt(f: StringBuilder) {
        val fd = this.asRawFd()
        val b = debugPathFd(fd, f, "File")
        b.finish()
    }

    public companion object {
        public fun open(path: Path, opts: OpenOptions): Result<File> = runPathWithCstr(path, { path1 -> File.openC(path1, opts) })

        public fun openC(path: CStr, opts: OpenOptions): Result<File> {
            val flags = O_CLOEXEC or when (val tryResult = opts.getAccessMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } or when (val tryResult = opts.getCreationMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } or (opts.customFlags and O_ACCMODE.inv())
            // The third argument of `open64` is documented to have type `modeT`. On
            // some platforms (like macOS, where `open64` is actually `open`), `modeT` is `UShort`.
            // However, since this is a variadic function, C integer promotion rules mean that on
            // the ABI level, this still gets passed as `cInt` (aka `UInt` on Unix platforms).
            val fd = when (val tryResult = cvtR({ open64(path.asPtr(), flags, opts.mode.toInt()) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(File(FileDesc.fromRawFd(fd)))
        }

        public fun fromInner(fileDesc: FileDesc): File = File(fileDesc)

        public fun fromRawFd(rawFd: RawFd): File = File(FromRawFd.fromRawFd(rawFd))
    }
}

// all DirEntry's will have a reference to this struct
public open class InnerReadDir(
    public var dirp: DirStream,
    public var root: PathBuf,
)

public open class ReadDir(
    public var inner: InnerReadDir,
    public var endOfStream: Boolean,
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
        return cfgSelect(any, targetOs, "redox", targetOs, "espidf", targetOs, "horizon", targetOs, "vita", targetOs, "nto", targetOs, "qnx", targetOs, "vxworks", targetOs, "rtems", targetOs, "l4re", Dir, open, this, inner, root, options, fd, libc, dirfd, this, inner, dirp, 0, dirHandle, mem, ManuallyDrop, new, dir, Dir, OwnedFd, fromRawFd, fd, dirHandle, openDir, Path, new, ".", options)
    }

    public open fun fmt(f: StringBuilder) {
        // This will only be called from std.fs.ReadDir, which will add a "ReadDir()" frame.
        // Thus the result will be e g ReadDir("/home")'
        f.append((this.inner.root).toString())
    }

    public open fun next(): Result<DirEntry>? {
        if (this.endOfStream) {
            return null
        }
        return while (true) {
            // POSIX.1-2024 formalized what was already guaranteed by a lot
            // of implementations and required readdir() to be thread-safe as
            // long as an individual DIR* is not accessed concurrently. Taking
            // a mutable reference to the `ReadDir` iterator prevents that.
            // Even POSIX.1-1994 specified that the data in the returned
            // dirent
            // > is not overwritten by another call to readdir() on a
            // > different directory stream.
            //
            // and that guarantee together with the requirement that the
            // underlying syscalls need to be thread-safe because of readdirR
            // make it very unlikely for an implementation to be non-conforming.
            // Nevertheless, there are still some platforms where we either
            // cannot confirm `readdir` to be thread-safe or know that it
            // isn't.
            cfgSelect(any, targetOs, "espidf", targetOs, "lynxos178", targetOs, "qurt", targetOs, "rtems", targetOs, "vxworks", mem, MaybeUninit, entry, MaybeUninit, uninit, entryPtr, dirent64, ptr, nullMut, err, libc, readdirR, this, inner, dirp, 0, entry, asMutPtr, entryPtr, err, 0, entryPtr, isNull, this, endOfStream, true, Some, Err, Error, fromRawOsError, err, entryPtr, isNull, None, entryPtr, entryPtr, castConst, cfg, not, any, all, targetOs, "linux", not, targetEnv, "musl", targetOs, "hurd", targetOs, "l4re", libc, readdir, readdir64, cfg, any, all, targetOs, "linux", not, targetEnv, "musl", targetOs, "hurd", targetOs, "l4re", libc, readdir64, sys, io, errno, setErrno, setErrno, 0, entryPtr, dirent64, readdir64, this, inner, dirp, 0, entryPtr, isNull, this, endOfStream, true, errno, 0, None, e, Some, Err, Error, fromRawOsError, e)
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
            val name = CStr.fromPtr((addrOf(entryPtr.dName)).cast())
            val nameBytes = name.toBytes()
            if (nameBytes == ".".encodeToByteArray() || nameBytes == "..".encodeToByteArray()) {
                continue
            }
            // When loading from a field, we can skip the `raw const`; `(* entryPtr).dIno` as
            // a value expression will do the right thing: `byteOffset` to the field and then
            // only access those bytes.
            val entry = Dirent64Min(dIno = entryPtr.dFileno, dType = entryPtr.dType.toUByte())
            return Result.Ok(DirEntry(entry = entry, name = name.toOwned(), dir = this.inner))
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
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<DirStream> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<DirStream> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 8L
                override fun readNative(addr: Long): DirStream =
                    DirStream(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: DirStream) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): DirStream =
                    DirStream(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: DirStream) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.mutPtrLayout(64)).writeBytes(buf, offset, value.value)
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

    public open fun metadata(): Result<FileAttr> = cfgSelect(not, any, targetOs, "redox", targetOs, "nto", targetOs, "qnx", targetOs, "vita", targetOs, "espidf", targetOs, "horizon", targetOs, "vxworks", targetOs, "rtems", targetOs, "l4re", fd, cvt, libc, dirfd, this, dir, dirp, 0, dirHandle, mem, ManuallyDrop, new, dir, Dir, OwnedFd, fromRawFd, fd, dirHandle, metadataC, this, name, true, runPathWithCstr, this, path, lstat)

    public open fun fileType(): Result<FileType> {
        // Use `entry.dType` if available.
        when (this.entry.dType) {
            DT_CHR -> { return Result.Ok(FileType(mode = S_IFCHR)) }
            DT_FIFO -> { return Result.Ok(FileType(mode = S_IFIFO)) }
            DT_LNK -> { return Result.Ok(FileType(mode = S_IFLNK)) }
            DT_REG -> { return Result.Ok(FileType(mode = S_IFREG)) }
            DT_SOCK -> { return Result.Ok(FileType(mode = S_IFSOCK)) }
            DT_DIR -> { return Result.Ok(FileType(mode = S_IFDIR)) }
            DT_BLK -> { return Result.Ok(FileType(mode = S_IFBLK)) }
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
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Dirent64Min> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Dirent64Min> {
                override val alignBytes: Long = 8L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): Dirent64Min =
                    Dirent64Min(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readNative(addr + 8L),
                    )
                override fun writeNative(addr: Long, value: Dirent64Min) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeNative(addr, value.dIno)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeNative(addr + 8L, value.dType)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Dirent64Min =
                    Dirent64Min(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).readBytes(buf, offset),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).readBytes(buf, offset + 8),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Dirent64Min) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U64).writeBytes(buf, offset, value.dIno)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U8).writeBytes(buf, offset + 8, value.dType)
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

    public open fun getAccessMode(): Result<CInt> =
        run {
            val matchVal = Triple(this.read, this.write, this.append)
            when {
                matchVal.first == true && matchVal.second == false && matchVal.third == false -> Result.Ok(O_RDONLY)
                matchVal.first == false && matchVal.second == true && matchVal.third == false -> Result.Ok(O_WRONLY)
                matchVal.first == true && matchVal.second == true && matchVal.third == false -> Result.Ok(O_RDWR)
                matchVal.first == false && matchVal.third == true -> Result.Ok(O_WRONLY or O_APPEND)
                matchVal.first == true && matchVal.third == true -> Result.Ok(O_RDWR or O_APPEND)
                else -> {
                    // If no access mode is set, check if any creation flags are set
                    // to provide a more descriptive error message
                    if (this.create || this.createNew || this.truncate) {
                        Result.Err(io.Error.new(io.ErrorKind.InvalidInput, "creating or truncating a file requires write or append access"))
                    } else {
                        Result.Err(io.Error.new(io.ErrorKind.InvalidInput, "must specify at least one of read, write, or append access"))
                    }
                }
            }
        }

    public open fun getCreationMode(): Result<CInt> {
        run {
            val matchVal = Pair(this.write, this.append)
            when {
                matchVal.first == true && matchVal.second == false -> {}
                matchVal.first == false && matchVal.second == false -> {
                    if (this.truncate || this.create || this.createNew) {
                        return Result.Err(io.Error.new(io.ErrorKind.InvalidInput, "creating or truncating a file requires write or append access"))
                    }
                }
                else -> {
                    if (this.truncate && !this.createNew) {
                        return Result.Err(io.Error.new(io.ErrorKind.InvalidInput, "append and truncate cannot both be enabled"))
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

    public open fun fmt(f: StringBuilder) {
        val read = this.read
        val write = this.write
        val append = this.append
        val truncate = this.truncate
        val create = this.create
        val createNew = this.createNew
        val customFlags = this.customFlags
        val mode = this.mode
        DebugStruct(f, "OpenOptions").field("read", read).field("write", write).field("append", append).field("truncate", truncate).field("create", create).field("create_new", createNew).field("custom_flags", customFlags).field("mode", Mode(mode)).finish()
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<OpenOptions> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<OpenOptions> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 16L
                override fun readNative(addr: Long): OpenOptions =
                    OpenOptions(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 1L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 2L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 3L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 4L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).readNative(addr + 5L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).readNative(addr + 8L),
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr + 12L),
                    )
                override fun writeNative(addr: Long, value: OpenOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr, value.read)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 1L, value.write)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 2L, value.append)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 3L, value.truncate)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 4L, value.create)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeNative(addr + 5L, value.createNew)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeNative(addr + 8L, value.customFlags)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr + 12L, value.mode)
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
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset + 12),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: OpenOptions) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset, value.read)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 1, value.write)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 2, value.append)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 3, value.truncate)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 4, value.create)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.BOOL).writeBytes(buf, offset + 5, value.createNew)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.I32).writeBytes(buf, offset + 8, value.customFlags)
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset + 12, value.mode)
                }
            }

        public fun new(): OpenOptions = OpenOptions(read = false, write = false, append = false, truncate = false, create = false, createNew = false, customFlags = 0, mode = 438u)
    }
}

public open class FilePermissions(
    public var mode: ModeT,
) : FromInner<UInt, FilePermissions> {
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

    public open fun fmt(f: StringBuilder) {
        val mode = this.mode
        DebugStruct(f, "FilePermissions").field("mode", Mode(mode)).finish()
    }

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

        public fun fromInner(mode: UInt): FilePermissions = FilePermissions(mode = mode)
    }
}

public open class FileTimes(
    public var accessed: SystemTime?,
    public var modified: SystemTime?,
    public var created: SystemTime?,
) {
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

    public open fun fmt(f: StringBuilder) {
        val mode = this.mode
        DebugStruct(f, "FileType").field("mode", Mode(mode)).finish()
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileType> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<FileType> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): FileType =
                    FileType(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: FileType) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.mode)
                }
                override fun readBytes(buf: ByteArray, offset: Int): FileType =
                    FileType(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: FileType) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.mode)
                }
            }
    }
}

public open class DirBuilder(
    public var mode: ModeT,
) {
    public open fun mkdir(p: Path): Result<Unit> = runPathWithCstr(p, { p1 -> cvt(io.github.kotlinmania.libc.mkdir(p1.asPtr(), this.mode)).map({ Unit }) })

    public open fun setMode(mode: UInt) {
        this.mode = mode
    }

    public open fun fmt(f: StringBuilder) {
        val mode = this.mode
        DebugStruct(f, "DirBuilder").field("mode", Mode(mode)).finish()
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

public open class Mode(
    public var value: ModeT,
) {
    public var field0: ModeT
        get() = value
        set(v) {
            value = v
        }

    // Format in octal, followed by the mode format used in `ls - l`.
    //
    // References:
    // https://pubs.opengroup.org/onlinepubs/9799919799/utilities/ls.html
    // https://www.gnu.org/software/libc/manual/htmlNode/Testing-File-Type.html
    // https://www.gnu.org/software/libc/manual/htmlNode/Permission-Bits.html
    //
    // Example:
    // 0o100664 (-rw-rw-r--)
    public open fun fmt(f: StringBuilder) {
        val mode = this
        f.append("0o$mode")
        val entryType =
            when (mode and S_IFMT) {
                S_IFDIR -> 'd'
                S_IFBLK -> 'b'
                S_IFCHR -> 'c'
                S_IFLNK -> 'l'
                S_IFIFO -> 'p'
                S_IFREG -> '-'
                else -> { return Unit }
            }
        f.append(" (")
        f.append(entryType)
        // Owner permissions
        f.append(if (mode and S_IRUSR != 0) {
            'r'
        } else {
            '-'
        })
        f.append(if (mode and S_IWUSR != 0) {
            'w'
        } else {
            '-'
        })
        val ownerExecutable = mode and S_IXUSR != 0
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
        f.append(if (mode and S_IRGRP != 0) {
            'r'
        } else {
            '-'
        })
        f.append(if (mode and S_IWGRP != 0) {
            'w'
        } else {
            '-'
        })
        val groupExecutable = mode and S_IXGRP != 0
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
        f.append(if (mode and S_IROTH != 0) {
            'r'
        } else {
            '-'
        })
        f.append(if (mode and S_IWOTH != 0) {
            'w'
        } else {
            '-'
        })
        val otherExecutable = mode and S_IXOTH != 0
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
        f.append(')')
    }

    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<Mode> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<Mode> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): Mode =
                    Mode(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: Mode) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeNative(addr, value.value)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Mode =
                    Mode(
                        (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Mode) {
                    (io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts.U32).writeBytes(buf, offset, value.value)
                }
            }
    }
}

public fun FileAttr.size(): ULong = this.stat.stSize.toULong()

public fun FileAttr.perm(): FilePermissions = FilePermissions(mode = (this.stat.stMode))

public fun FileAttr.fileType(): FileType = FileType(mode = this.stat.stMode)

public fun FileAttr.modified(): Result<SystemTime> = SystemTime.new(this.stat.stMtime, this.stat.stMtimensec.toLong())

public fun FileAttr.accessed(): Result<SystemTime> = SystemTime.new(this.stat.stAtime, this.stat.stAtimensec.toLong())

public fun FileAttr.created(): Result<SystemTime> = SystemTime.new(this.stat.stBirthtime, this.stat.stBirthtimensec.toLong())

public fun FileAttr.asInner(): Stat64 = this.stat

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
internal fun debugAssertFdIsOpen(fd: RawFd) {
    // this is similar to assertUnsafePrecondition() but it doesn't require const
    if (checkLibraryUb()) {
        if (fcntl(fd, F_GETFD) == -1 && errno() == EBADF) {
            run {
                when (val matchVal = panicOutput()) {
                    null -> {}
                    else -> {
                        val out = matchVal
                        Write.writeFmt(out, "fatal runtime error: " + ("IO Safety violation: owned file descriptor already closed").toString() + ", aborting\n")
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

public fun fileTimeToTimespec(time: SystemTime?): Result<Timespec> =
    when {
        time != null && time!!.t.toTimespec() != null -> {
            val time1 = time
            Result.Ok(ts)
        }
        time != null && time > UNIX_EPOCH -> {
            val time2 = time
            Result.Err(io.constError(io.ErrorKind.InvalidInput, "timestamp is too large to set as a file time"))
        }
        time != null -> Result.Err(io.constError(io.ErrorKind.InvalidInput, "timestamp is too small to set as a file time"))
        else -> Result.Ok(run {
            val ts = default()
            ts.tvSec = 0
            ts.tvNsec = ((UTIME_OMIT as Any?) as Result<Timespec>)
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

    public open fun timesBufSize(): Int = this.numTimes * sizeOf<Timespec>(typeLayoutT = ObjectSlotLayout<Timespec>())

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

        public fun fromTimes(times: FileTimes): Result<TimesAttrlist> {
            val thisRef = TimesAttrlist(buf = List(3) { MaybeUninit.uninit(ObjectSlotLayout<Timespec>()) }, attrlist = null, numTimes = 0)
            thisRef.attrlist.bitmapcount = ATTR_BIT_MAP_COUNT
            if (times.created != null) {
                thisRef.buf[thisRef.numTimes].write(when (val tryResult = fileTimeToTimespec(times.created)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
                thisRef.numTimes += 1
                thisRef.attrlist.commonattr = thisRef.attrlist.commonattr or ATTR_CMN_CRTIME
            }
            if (times.modified != null) {
                thisRef.buf[thisRef.numTimes].write(when (val tryResult = fileTimeToTimespec(times.modified)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
                thisRef.numTimes += 1
                thisRef.attrlist.commonattr = thisRef.attrlist.commonattr or ATTR_CMN_MODTIME
            }
            if (times.accessed != null) {
                thisRef.buf[thisRef.numTimes].write(when (val tryResult = fileTimeToTimespec(times.accessed)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) })
                thisRef.numTimes += 1
                thisRef.attrlist.commonattr = thisRef.attrlist.commonattr or ATTR_CMN_ACCTIME
            }
            return Result.Ok(thisRef)
        }
    }
}

public fun readdir(path: Path): Result<ReadDir> {
    val ptr = when (val tryResult = runPathWithCstr(path, { p -> Result.Ok(opendir(p.asPtr())) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return if (ptr.isNull()) {
        Result.Err(Error.lastOsError())
    } else {
        val root = path.toPathBuf()
        val inner = InnerReadDir(dirp = DirStream(ptr), root = root)
        Result.Ok(ReadDir.new(inner))
    }
}

public fun unlink(p: CStr): Result<Unit> = cvt(io.github.kotlinmania.libc.unlink(p.asPtr())).map({ Unit })

public fun rename(old: CStr, new: CStr): Result<Unit> = cvt(io.github.kotlinmania.libc.rename(old.asPtr(), new.asPtr())).map({ Unit })

public fun renameNoreplace(old: CStr, new: CStr): Result<Unit> {
    syscall(renameat2, olddirfd, cInt, oldpath, libc, cChar, newdirfd, cInt, newpath, libc, cChar, flags, libc, cUint, cInt)
    // `RENAME_NOREPLACE` is `cUint` on linux and `cInt` on Android, so casting is needed here.
    val flags = io.github.kotlinmania.libc.renameNoreplace.toUInt()
    val res = cvt(renameat2(AT_FDCWD, old.asPtr(), AT_FDCWD, new.asPtr(), flags))
    return when (res) { is Result.Ok<*> -> {
        Result.Ok(Unit)
    }; is Result.Err<*> -> {
        val err = res.error
        when (val matchVal = err.rawOsError()) {
            // The kernel predates `renameat2`.
            ENOSYS -> linkThenUnlink(old, new)
            // The filesystem does not support `RENAME_NOREPLACE`.
            // `EINVAL` also covers misuse, so report it unchanged unless the fallback found `new`.
            EINVAL -> run {
                val matchVal1 = linkThenUnlink(old, new)
                when {
                    matchVal1 is Result.Err<*> && (matchVal1 as Result.Err<*>).error.rawOsError() != EEXIST -> {
                        val fallback = (matchVal1 as Result.Err<*>).error
                        Result.Err(err)
                    }
                    else -> {
                        val res1 = matchVal1
                        res1
                    }
                }
            }
            else -> Result.Err(err)
        }
    } }
}

public fun setPerm(p: CStr, perm: FilePermissions): Result<Unit> = cvtR({ chmod(p.asPtr(), perm.mode) }).map({ Unit })

public fun setPermNofollow(p: CStr, perm: FilePermissions): Result<Unit> {
    // VxWorks has no `O_NOFOLLOW`, and its `fchmodat` rejects
    // `AT_SYMLINK_NOFOLLOW` with `ENOTSUP`, so a no-follow chmod is unsupported.
    return Result.Err(unsupported.into())
}

public fun rmdir(p: CStr): Result<Unit> = cvt(io.github.kotlinmania.libc.rmdir(p.asPtr())).map({ Unit })

public fun readlink(cPath: CStr): Result<PathBuf> {
    val p = cPath.asPtr()
    val buf = ArrayList(256)
    while (true) {
        val bufRead = (when (val tryResult = cvt(io.github.kotlinmania.libc.readlink(p, buf.asMutPtr(), buf.capacity()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }).toInt()
        run {
            buf.setLen(bufRead)
        }
        if (bufRead != buf.capacity()) {
            buf.shrinkToFit()
            return Result.Ok(PathBuf.from(OsString.fromVec(buf)))
        }
        // Trigger the internal buffer resizing logic of `List` by requiring
        // more space than the current capacity. The length is guaranteed to be
        // the same as the capacity due to the if statement above.
        buf.reserve(1)
    }
}

public fun symlink(original: CStr, link: CStr): Result<Unit> = cvt(io.github.kotlinmania.libc.symlink(original.asPtr(), link.asPtr())).map({ Unit })

public fun link(original: CStr, link: CStr): Result<Unit> {
    cfgSelect(any, targetOs, "vxworks", targetOs, "redox", targetOs, "espidf", targetOs, "horizon", targetOs, "vita", targetOs, "l4re", targetEnv, "nto70", cvt, libc, link, original, asPtr, link, asPtr, cvt, libc, linkat, libc, AT_FDCWD, original, asPtr, libc, AT_FDCWD, link, asPtr, 0)
    return Result.Ok(Unit)
}

public fun stat(p: CStr): Result<FileAttr> {
    when (val matchVal = tryStatx(AT_FDCWD, p.asPtr(), AT_STATX_SYNC_AS_STAT, STATX_BASIC_STATS or STATX_BTIME)) {
        null -> {}
        else -> {
            val ret = matchVal
            return ret
        }
    }
    val stat: Stat64?  = null
    when (val tryResult = cvt(stat64(p.asPtr(), stat))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(FileAttr.fromStat64(stat))
}

public fun lstat(p: CStr): Result<FileAttr> {
    when (val matchVal = tryStatx(AT_FDCWD, p.asPtr(), AT_SYMLINK_NOFOLLOW or AT_STATX_SYNC_AS_STAT, STATX_BASIC_STATS or STATX_BTIME)) {
        null -> {}
        else -> {
            val ret = matchVal
            return ret
        }
    }
    val stat: Stat64?  = null
    when (val tryResult = cvt(lstat64(p.asPtr(), stat))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(FileAttr.fromStat64(stat))
}

public fun canonicalize(path: CStr): Result<PathBuf> {
    val r = realpath(path.asPtr(), MutPtr(0L))
    if (r.isNull()) {
        return Result.Err(io.Error.lastOsError())
    }
    return Result.Ok(PathBuf.from(OsString.fromVec(run {
        val buf = CStr.fromPtr(r).toBytes().toVec()
        free(mutPtrFromRustCast(r))
        buf
    })))
}

public fun linkThenUnlink(old: CStr, new: CStr): Result<Unit> {
    when (val tryResult = link(old, new)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return unlink(old)
}

public fun openFrom(from: Path): Result<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>> {
    val reader = when (val tryResult = File.open(from)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val metadata = when (val tryResult = reader.metadata()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    if (!metadata.isFile()) {
        return Result.Err(NOT_FILE_ERROR)
    }
    return Result.Ok(Pair(reader, metadata))
}

public fun setTimesImpl(p: CStr, times: FileTimes, followSymlinks: Boolean): Result<Unit> = cfgSelect(any, targetOs, "redox", targetOs, "espidf", targetOs, "horizon", targetOs, "nuttx", targetOs, "vita", targetOs, "rtems", p, times, followSymlinks, Err, io, constError, io, ErrorKind, Unsupported, "setting file times not supported", targetVendor, "apple", ta, TimesAttrlist, fromTimes, times, options, followSymlinks, 0, libc, FSOPT_NOFOLLOW, cvt, libc, setattrlist, p, asPtr, ta, attrlist, ta, timesBuf, ta, timesBufSize, options, Ok, targetOs, "android", times, fileTimeToTimespec, times, accessed, fileTimeToTimespec, times, modified, flags, followSymlinks, 0, libc, AT_SYMLINK_NOFOLLOW, cvt, weak, utimensat, dirfd, cInt, path, libc, cChar, times, libc, timespec, flags, cInt, cInt, utimensat, get, Some, utimensat, utimensat, libc, AT_FDCWD, p, asPtr, times, asPtr, flags, None, Err, io, constError, io, ErrorKind, Unsupported, "setting file times requires Android API level >= 19", Ok, flags, followSymlinks, 0, libc, AT_SYMLINK_NOFOLLOW, cfg, all, targetOs, "linux", targetEnv, "gnu", targetPointerWidth, "32", not, targetArch, "riscv32", sys, pal, time, timespec64, sys, pal, weak, weak, weak, utimensat64, dirfd, cInt, path, cChar, times, timespec64, flags, cInt, cInt, Some, utimensat64, utimensat64, get, toTimespec, time, Option, SystemTime, time, map, time, time, t, toTimespec64, unwrapOr, timespec64, new, 0, libc, UTIME_OMIT, times, toTimespec, times, accessed, toTimespec, times, modified, cvt, utimensat64, libc, AT_FDCWD, p, asPtr, times, asPtr, flags, Ok, times, fileTimeToTimespec, times, accessed, fileTimeToTimespec, times, modified, cvt, libc, utimensat, libc, AT_FDCWD, p, asPtr, times, asPtr, flags, Ok)

public fun setTimes(p: CStr, times: FileTimes): Result<Unit> = setTimesImpl(p, times, true)

public fun setTimesNofollow(p: CStr, times: FileTimes): Result<Unit> = setTimesImpl(p, times, false)

public fun openToAndSetPermissions(to: Path, readerMetadata: Metadata): Result<Pair<io.github.kotlinmania.rustlang.std.fs.File, Metadata>> {
    val writer = when (val tryResult = OpenOptions.new().write(true).create(true).truncate(true).open(to)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val writerMetadata = when (val tryResult = writer.metadata()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(Pair(writer, writerMetadata))
}

public open class CachedFileMetadata(
    public var field0: File,
    public var field1: Metadata,
) : Read, Write {
    public open fun read(buf: MutableList<UByte>): Result<Int> = this.field0.read(buf)

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int> = this.field0.readVectored(bufs)

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit> = this.field0.readBuf(cursor)

    public open fun isReadVectored(): Boolean = this.field0.isReadVectored()

    public open fun readToEnd(buf: List<UByte>): Result<Int> = this.field0.readToEnd(buf)

    public open fun readToString(buf: String): Result<Int> = this.field0.readToString(buf)

    public open fun write(buf: List<UByte>): Result<Int> = this.field0.write(buf)

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int> = this.field0.writeVectored(bufs)

    public open fun isWriteVectored(): Boolean = this.field0.isWriteVectored()

    public open fun flush(): Result<Unit> = this.field0.flush()
}

public fun copy(from: Path, to: Path): Result<ULong> {
    val (reader, readerMetadata) = when (val tryResult = openFrom(from)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val (writer, writerMetadata) = when (val tryResult = openToAndSetPermissions(to, readerMetadata)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return io.copy(CachedFileMetadata(reader, readerMetadata), CachedFileMetadata(writer, writerMetadata))
}

public fun chown(path: Path, uid: UInt, gid: UInt): Result<Unit> =
    runPathWithCstr(path, { path1 ->
        cvt(io.github.kotlinmania.libc.chown(path1.asPtr(), uid.toInt(), gid.toInt())).map({ Unit })
    })

public fun fchown(fd: CInt, uid: UInt, gid: UInt): Result<Unit> {
    when (val tryResult = cvt(io.github.kotlinmania.libc.fchown(fd, uid.toInt(), gid.toInt()))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(Unit)
}

public fun lchown(path: Path, uid: UInt, gid: UInt): Result<Unit> =
    runPathWithCstr(path, { path1 ->
        cvt(io.github.kotlinmania.libc.lchown(path1.asPtr(), uid.toInt(), gid.toInt())).map({ Unit })
    })

public fun chroot(dir: Path): Result<Unit> = runPathWithCstr(dir, { dir1 -> cvt(io.github.kotlinmania.libc.chroot(dir1.asPtr())).map({ Unit }) })

public fun mkfifo(path: Path, mode: UInt): Result<Unit> =
    runPathWithCstr(path, { path1 ->
        cvt(io.github.kotlinmania.libc.mkfifo(path1.asPtr(), mode.tryInto().unwrap())).map({ Unit })
    })

public fun openatNofollowDironly(parentFd: RawFd?, p: CStr): Result<OwnedFd> {
    val fd = when (val tryResult = cvtR({ openat((parentFd ?: AT_FDCWD), p.asPtr(), O_CLOEXEC or O_RDONLY or O_NOFOLLOW or O_DIRECTORY) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(OwnedFd.fromRawFd(fd))
}

public fun fdreaddir(dirFd: OwnedFd): Result<Pair<ReadDir, RawFd>> {
    val ptr = fdopendir(dirFd.asRawFd())
    if (ptr.isNull()) {
        return Result.Err(io.Error.lastOsError())
    }
    val dirp = DirStream(ptr)
    // file descriptor is automatically closed by libc.closedir() now, so give up ownership
    val newParentFd = dirFd.intoRawFd()
    // a valid root is not needed because we do not call any functions involving the full path
    // of the `DirEntry`s.
    val dummyRoot = PathBuf.new()
    val inner = InnerReadDir(dirp = dirp, root = dummyRoot)
    return Result.Ok(Pair(ReadDir.new(inner), newParentFd))
}

public fun isDir(ent: DirEntry): Boolean? = null

public fun isEnoent(result: Result<Unit>): Boolean =
    if (result is Result.Err<*> &&
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
public fun removeDirAllRecursive(parentFd: RawFd?, path: CStr): Result<Unit> {
    // try opening as directory
    val fd =
        run {
            val matchVal = openatNofollowDironly(parentFd, path)
            when {
                matchVal is Result.Err<*> && when (val matchVal1 = (matchVal as Result.Err<*>).error.rawOsError()) {
                    ENOTDIR, ELOOP -> true
                    else -> false
                } -> {
                    val err = (matchVal as Result.Err<*>).error
                    // not a directory - don't traverse further
                    // (for symlinks, older Linux kernels may return ELOOP instead of ENOTDIR)
                    return when (parentFd) {
                        // ...unless this was supposed to be the deletion root directory. We don't want
                        // to delete a file at the root, so we just forward the error.
                        null -> Result.Err(err)
                        // unlink...
                        else -> {
                            val parentFd1 = parentFd
                            cvt(unlinkat(parentFd1, path.asPtr(), 0)).map(drop)
                        }
                    }
                }
                else -> {
                    val result = matchVal
                    when (val tryResult = result) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
            }
        }
    // open the directory passing ownership of the fd
    val (dir, fd1) = when (val tryResult = fdreaddir(fd)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    // For WASI all directory entries for this directory are read first
    // before any removal is done. This works around the fact that the
    // WASIp1 API for reading directories is not well-designed for handling
    // mutations between invocations of reading a directory. By reading all
    // the entries at once this ensures that, at least without concurrent
    // modifications, it should be possible to delete everything.
    val dir1 = dir.collect<Vec>()
    for (child in dir1) {
        val child1 = when (val tryResult = child) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        // we need an inner try block, because if one of these
        // directories has already been deleted, then we need to
        // continue the loop, not return ok.
        val result1: Result<Unit> =
            when (val matchVal2 = isDir(child1)) {
                true -> {
                    when (val tryResult = removeDirAllRecursive(fd1, child1.name)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
                false -> {
                    when (val tryResult = cvt(unlinkat(fd1, child1.name.asPtr(), 0))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
                null -> {
                    // POSIX specifies that calling unlink()/unlinkat(..., 0) on a directory can succeed
                    // if the process has the appropriate privileges. This however can causing orphaned
                    // directories requiring an fsck e.g. on Solaris and Illumos. So we try recursing
                    // into it first instead of trying to unlink() it.
                    when (val tryResult = removeDirAllRecursive(fd1, child1.name)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
            }
        if (result1.isErr() && !isEnoent(result1)) {
            return result1
        }
    }
    // unlink the directory after removing its contents
    when (val tryResult = ignoreNotfound(cvt(unlinkat((parentFd ?: AT_FDCWD), path.asPtr(), AT_REMOVEDIR)))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(Unit)
}

public fun removeDirAllModern(p: CStr): Result<Unit> {
    // We cannot just call removeDirAllRecursive() here because that would not delete a passed
    // symlink. No need to worry about races, because removeDirAllRecursive() does not recurse
    // into symlinks.
    val attr = when (val tryResult = lstat(p)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return if (attr.fileType().isSymlink()) {
        // This would error in `removeDirAllRecursive` as it is not a directory.
        // We promise to delete directory symlinks, so use `unlink` to do that. On Unix, we
        // inadvertently also delete symlinks to files.
        unlink(p)
    } else {
        removeDirAllRecursive(null, p)
    }
}

public fun removeDirAll(p: Path): Result<Unit> = runPathWithCstr(p, ::removeDirAllModern)
