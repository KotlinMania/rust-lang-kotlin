package io.github.kotlinmania.rustlang.std.sys.fs

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.linux.raw.arch.Stat
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.fs.unix.FilePermissions
import io.github.kotlinmania.rustlang.std.sys.fs.unix.FileTimes
import io.github.kotlinmania.rustlang.std.sys.fs.unix.ReadDir
import io.github.kotlinmania.rustlang.std.sys.fs.unix.link
import io.github.kotlinmania.rustlang.std.sys.fs.unix.lstat
import io.github.kotlinmania.rustlang.std.sys.fs.unix.readdir
import io.github.kotlinmania.rustlang.std.sys.fs.unix.readlink
import io.github.kotlinmania.rustlang.std.sys.fs.unix.rmdir
import io.github.kotlinmania.rustlang.std.sys.fs.unix.setPerm
import io.github.kotlinmania.rustlang.std.sys.fs.unix.setPermNofollow
import io.github.kotlinmania.rustlang.std.sys.fs.unix.unlink
import io.github.kotlinmania.rustlang.std.sys.fs.unsupported.FileAttr

// Note: Replace this with platform-specific path conversion functions.
public fun <T> withNativePath(path: Path, f: (Path) -> Result<T, Error>): Result<T, Error> = f(path)

public fun readDir(path: Path): Result<ReadDir, Error> {
    // Note: use withNativePath on all platforms
    return readdir(path)
}

public fun removeFile(path: Path): Result<Unit, Error> = withNativePath(path, unlink)

public fun rename(old: Path, new: Path): Result<Unit, Error> = withNativePath(old, { old1 -> withNativePath(new, { new1 -> rename(old1, new1) }) })

public fun renameNoreplace(old: Path, new: Path): Result<Unit, Error> = withNativePath(old, { old1 -> withNativePath(new, { new1 -> renameNoreplace(old1, new1) }) })

public fun removeDir(path: Path): Result<Unit, Error> = withNativePath(path, rmdir)

public fun removeDirAll(path: Path): Result<Unit, Error> = withNativePath(path, ::removeDirAll)

public fun readLink(path: Path): Result<PathBuf, Error> = withNativePath(path, readlink)

public fun symlink(original: Path, link: Path): Result<Unit, Error> {
    // Note: use withNativePath on all platforms
    return symlink(original, link)
}

public fun hardLink(original: Path, link: Path): Result<Unit, Error> =
    withNativePath(original, { original1 ->
        withNativePath(link, { link1 -> link(original1, link1) })
    })

public fun metadata(path: Path): Result<FileAttr, Error> = withNativePath(path, Stat)

public fun symlinkMetadata(path: Path): Result<FileAttr, Error> = withNativePath(path, lstat)

public fun setPermissions(path: Path, perm: FilePermissions): Result<Unit, Error> = withNativePath(path, { path1 -> setPerm(path1, perm.clone()) })

public fun setPermissionsNofollow(path: Path, perm: FilePermissions): Result<Unit, Error> = withNativePath(path, { path1 -> setPermNofollow(path1, perm.clone()) })

public fun canonicalize(path: Path): Result<PathBuf, Error> = withNativePath(path, ::canonicalize)

public fun copy(from: Path, to: Path): Result<ULong, Error> = withNativePath(from, { from1 -> withNativePath(to, { to1 -> copy(from1, to1) }) })

public fun exists(path: Path): Result<Boolean, Error> = withNativePath(path, ::exists)

public fun setTimes(path: Path, times: FileTimes): Result<Unit, Error> = withNativePath(path, { path1 -> setTimes(path1, times.clone()) })

public fun setTimesNofollow(path: Path, times: FileTimes): Result<Unit, Error> = withNativePath(path, { path1 -> setTimesNofollow(path1, times.clone()) })
