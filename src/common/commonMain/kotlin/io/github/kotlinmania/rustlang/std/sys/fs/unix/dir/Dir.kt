// port-lint: source sys/fs/unix/dir.rs
package io.github.kotlinmania.rustlang.std.sys.fs.unix.dir

import io.github.kotlinmania.libc.CInt
import io.github.kotlinmania.libc.fuchsia.AT_REMOVEDIR
import io.github.kotlinmania.libc.fuchsia.AT_SYMLINK_NOFOLLOW
import io.github.kotlinmania.libc.fuchsia.O_ACCMODE
import io.github.kotlinmania.libc.fuchsia.O_CLOEXEC
import io.github.kotlinmania.libc.fuchsia.O_DIRECTORY
import io.github.kotlinmania.libc.fuchsia.O_PATH
import io.github.kotlinmania.libc.mkdirat
import io.github.kotlinmania.libc.renameat
import io.github.kotlinmania.libc.unix.hurd.AT_STATX_SYNC_AS_STAT
import io.github.kotlinmania.libc.unix.hurd.STATX_BASIC_STATS
import io.github.kotlinmania.libc.unix.hurd.STATX_BTIME
import io.github.kotlinmania.libc.unlinkat
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.fd.BorrowedFd
import io.github.kotlinmania.rustlang.std.os.fd.FromRawFd
import io.github.kotlinmania.rustlang.std.os.fd.OwnedFd
import io.github.kotlinmania.rustlang.std.os.fd.RawFd
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.sys.fd.unix.FileDesc
import io.github.kotlinmania.rustlang.std.sys.fs.unix.File
import io.github.kotlinmania.rustlang.std.sys.fs.unix.OpenOptions
import io.github.kotlinmania.rustlang.std.sys.fs.unix.Stat64
import io.github.kotlinmania.rustlang.std.sys.fs.unix.cfgHasStatx
import io.github.kotlinmania.rustlang.std.sys.fs.unix.debugPathFd
import io.github.kotlinmania.rustlang.std.sys.fs.unix.open64
import io.github.kotlinmania.rustlang.std.sys.fs.unix.tryStatx
import io.github.kotlinmania.rustlang.std.sys.fs.unsupported.FileAttr
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runPathWithCstr
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvtR
import io.github.kotlinmania.libc.fstatat as fstatat64
import io.github.kotlinmania.libc.openat as openat64

public val TRAVERSE_DIRECTORY: Int = O_PATH

public open class Dir(
    internal var value: OwnedFd,
) {
    public var field0: OwnedFd
        get() = value
        set(v) {
            value = v
        }

    public open fun duplicate(): Result<Dir, Error> {
        return Result.Ok(Dir(when (val tryResult = this.value.tryClone()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }))
    }

    public open fun openFile(path: Path, opts: OpenOptions): Result<File, Error> = runPathWithCstr(path.asRef(), { path1 -> this.openFileC(path1, opts, 0) }).map(FileDesc::fromInner).map({ File(it) })

    public open fun selfMetadata(): Result<FileAttr, Error> {
        // Reuse the implementation for files, which should work for all FDs.
        val fd = this.value.asRawFd()
        val f = File(FileDesc.fromRawFd(fd))
        return f.fileAttr()
    }

    public open fun removeFile(path: Path): Result<Unit, Error> = runPathWithCstr(path, { path1 -> this.removeC(path1, false) })

    public open fun rename(from: Path, toDir: Dir, to: Path): Result<Unit, Error> =
        runPathWithCstr(from, { from1 ->
            runPathWithCstr(to, { to1 -> this.renameC(from1, toDir, to1) })
        })

    public open fun openDir(path: Path, opts: OpenOptions): Result<Dir, Error> = runPathWithCstr(path, { path1 -> this.openFileC(path1, opts, O_DIRECTORY) }).map({ Dir(it) })

    public open fun createDir(path: Path): Result<Unit, Error> = runPathWithCstr(path.asRef(), { path1 -> this.createDirC(path1) })

    public open fun removeDir(path: Path): Result<Unit, Error> = runPathWithCstr(path, { path1 -> this.removeC(path1, true) })

    public open fun metadata(path: Path): Result<FileAttr, Error> =
        runPathWithCstr(path, { path1 ->
            this.metadataC(path1, false)
        })

    public open fun symlinkMetadata(path: Path): Result<FileAttr, Error> = runPathWithCstr(path, { path1 -> this.metadataC(path1, true) })

    public open fun openFileC(path: CStr, opts: OpenOptions, extraFlags: CInt): Result<OwnedFd, Error> {
        val flags = O_CLOEXEC or when (val tryResult = opts.getAccessMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } or when (val tryResult = opts.getCreationMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } or (opts.customFlags and O_ACCMODE.inv()) or extraFlags
        val fd = when (val tryResult = cvtR({ openat64(this.value.asRawFd(), path.asPtr(), flags, opts.mode.toInt()) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(OwnedFd.fromRawFd(fd))
    }

    public open fun removeC(path: CStr, removeDir: Boolean): Result<Unit, Error> =
        cvt(unlinkat(this.value.asRawFd(), path.asPtr(), if (removeDir) {
            AT_REMOVEDIR
        } else {
            0
        })).map({ Unit })

    public open fun renameC(from: CStr, toDir: Dir, to: CStr): Result<Unit, Error> = cvt(renameat(this.value.asRawFd(), from.asPtr(), toDir.value.asRawFd(), to.asPtr())).map({ Unit })

    public open fun createDirC(path: CStr): Result<Unit, Error> = cvt(mkdirat(this.value.asRawFd(), path.asPtr(), 511)).map({ Unit })

    internal open fun metadataC(path: CStr, symlinkNofollow: Boolean): Result<FileAttr, Error> {
        val fd = this.value.asRawFd()
        val flag =
            if (symlinkNofollow) {
                AT_SYMLINK_NOFOLLOW
            } else {
                0
            }
        cfgHasStatx(when (val matchVal = tryStatx(fd, path.asPtr(), flag or AT_STATX_SYNC_AS_STAT, STATX_BASIC_STATS or STATX_BTIME)) {
            null -> {}
            else -> {
                val ret = matchVal
                return ret
            }
        })
        val stat: Stat64?  = null
        when (val tryResult = cvt(fstatat64(fd, path.asPtr(), stat, flag))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
        return Result.Ok(FileAttr.fromStat64(stat))
    }

    public open fun fmt(f: StringBuilder) {
        val fd = this.value.asRawFd()
        val b = debugPathFd(fd, f, "Dir")
        b.finish()
    }

    public companion object {
        public fun open(path: Path, opts: OpenOptions): Result<Dir, Error> = runPathWithCstr(path, { path1 -> Dir.openWithC(path1, opts) })

        public fun openForTraversal(path: Path): Result<Dir, Error> = runPathWithCstr(path, { path1 -> Dir.openTraversalC(path1) })

        public fun openWithC(path: CStr, opts: OpenOptions): Result<Dir, Error> {
            val flags = O_CLOEXEC or O_DIRECTORY or when (val tryResult = opts.getAccessMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } or when (val tryResult = opts.getCreationMode()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) } or (opts.customFlags and O_ACCMODE.inv())
            val fd = when (val tryResult = cvtR({ open64(path.asPtr(), flags, opts.mode.toInt()) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(Dir(OwnedFd.fromRawFd(fd)))
        }

        public fun openTraversalC(path: CStr): Result<Dir, Error> {
            val flags = O_CLOEXEC or O_DIRECTORY or TRAVERSE_DIRECTORY
            val fd = when (val tryResult = cvtR({ open64(path.asPtr(), flags, 0) })) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            return Result.Ok(Dir(OwnedFd.fromRawFd(fd)))
        }
    }
}

public fun io.github.kotlinmania.rustlang.std.fs.Dir.asRawFd(): RawFd = this.asInner().first.asRawFd()

public fun io.github.kotlinmania.rustlang.std.fs.Dir.intoRawFd(): RawFd = this.intoInner().first.intoRawFd()

public fun fromRawFdDir(fd: RawFd): io.github.kotlinmania.rustlang.std.fs.Dir = io.github.kotlinmania.rustlang.std.fs.Dir.fromInner(Dir(FromRawFd.fromRawFd(fd)))

public fun io.github.kotlinmania.rustlang.std.fs.Dir.asFd(): BorrowedFd = this.asInner().first.asFd()

public fun fromOwnedFd(value: io.github.kotlinmania.rustlang.std.fs.Dir): OwnedFd = value.intoInner().first

public fun fromDir(value: OwnedFd): io.github.kotlinmania.rustlang.std.fs.Dir = io.github.kotlinmania.rustlang.std.fs.Dir.fromInner(Dir(value))
