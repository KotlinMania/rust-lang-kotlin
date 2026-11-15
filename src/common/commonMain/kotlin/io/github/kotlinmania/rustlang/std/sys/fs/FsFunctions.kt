package io.github.kotlinmania.rustlang.std.sys.fs

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.path.Path
import io.github.kotlinmania.rustlang.path.PathBuf
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.sys.fs.unix.FileAttr
import io.github.kotlinmania.rustlang.sys.fs.unix.FilePermissions
import io.github.kotlinmania.rustlang.sys.fs.unix.FileTimes
import io.github.kotlinmania.rustlang.sys.fs.unix.ReadDir
import io.github.kotlinmania.rustlang.std.sys.fs.unix as imp

// Note: Replace this with platform-specific path conversion functions.
public fun <T> withNativePath(path: Path, f: (Path) -> Result<T, Error>): Result<T, Error> = f(path)

public fun readDir(path: Path): Result<ReadDir, Error> {
    // Note: use withNativePath on all platforms
    return imp.readdir(path)
}

public fun removeFile(path: Path): Result<Unit, Error> = withNativePath(path, imp.unlink)

public fun rename(old: Path, new: Path): Result<Unit, Error> = withNativePath(old, { old1 -> withNativePath(new, { new1 -> imp.rename(old1, new1) }) })

public fun renameNoreplace(old: Path, new: Path): Result<Unit, Error> = withNativePath(old, { old1 -> withNativePath(new, { new1 -> imp.renameNoreplace(old1, new1) }) })

public fun removeDir(path: Path): Result<Unit, Error> = withNativePath(path, imp.rmdir)

public fun removeDirAll(path: Path): Result<Unit, Error> = withNativePath(path, imp.removeDirAll)

public fun readLink(path: Path): Result<PathBuf, Error> = withNativePath(path, imp.readlink)

public fun symlink(original: Path, link: Path): Result<Unit, Error> {
    // Note: use withNativePath on all platforms
    return imp.symlink(original, link)
}

public fun hardLink(original: Path, link: Path): Result<Unit, Error> =
    withNativePath(original, { original1 ->
        withNativePath(link, { link1 -> imp.link(original1, link1) })
    })

public fun metadata(path: Path): Result<FileAttr, Error> = withNativePath(path, imp.stat)

public fun symlinkMetadata(path: Path): Result<FileAttr, Error> = withNativePath(path, imp.lstat)

public fun setPermissions(path: Path, perm: FilePermissions): Result<Unit, Error> = withNativePath(path, { path1 -> imp.setPerm(path1, perm.clone()) })

public fun setPermissionsNofollow(path: Path, perm: FilePermissions): Result<Unit, Error> = withNativePath(path, { path1 -> imp.setPermNofollow(path1, perm.clone()) })

public fun canonicalize(path: Path): Result<PathBuf, Error> = withNativePath(path, imp.canonicalize)

public fun copy(from: Path, to: Path): Result<ULong, Error> = withNativePath(from, { from1 -> withNativePath(to, { to1 -> imp.copy(from1, to1) }) })

public fun exists(path: Path): Result<Boolean, Error> = withNativePath(path, imp.exists)

public fun setTimes(path: Path, times: FileTimes): Result<Unit, Error> = withNativePath(path, { path1 -> imp.setTimes(path1, times.clone()) })

public fun setTimesNofollow(path: Path, times: FileTimes): Result<Unit, Error> = withNativePath(path, { path1 -> imp.setTimesNofollow(path1, times.clone()) })
