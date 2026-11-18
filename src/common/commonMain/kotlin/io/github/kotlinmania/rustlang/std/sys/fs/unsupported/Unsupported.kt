// port-lint: source sys/fs/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.fs.unsupported

import io.github.kotlinmania.rustlang.core.io.borrowedbuf.BorrowedCursor
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.io.seek.SeekFrom
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fs.TryLockError
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.fs.common.Dir
import io.github.kotlinmania.rustlang.std.sys.pal.unsupported.unsupported
import io.github.kotlinmania.rustlang.std.sys.time.unsupported.SystemTime

public open class File(
    public var value: Nothing,
) : AutoCloseable {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    override fun close() {
        this.drop()
    }

    // Dummy Drops so that `needsDrop<std.fs.File>()` etc. is true independent of platform,
    // avoiding a portability hazard.
    // The types not listed here are pure data and don’t implement Drop even when real.
    public open fun drop() {
        this.value
    }

    public open fun fileAttr(): Result<FileAttr, Error> = this.value

    public open fun fsync(): Result<Unit, Error> = this.value

    public open fun datasync(): Result<Unit, Error> = this.value

    public open fun lock(): Result<Unit, Error> = this.value

    public open fun lockShared(): Result<Unit, Error> = this.value

    public open fun tryLock(): Result<Unit, TryLockError> = this.value

    public open fun tryLockShared(): Result<Unit, TryLockError> = this.value

    public open fun unlock(): Result<Unit, Error> = this.value

    public open fun truncate(size: ULong): Result<Unit, Error> = this.value

    public open fun read(buf: MutableList<UByte>): Result<Int, Error> = this.value

    public open fun readVectored(bufs: MutableList<IoSliceMut>): Result<Int, Error> = this.value

    public open fun isReadVectored(): Boolean = this.value

    public open fun readBuf(cursor: BorrowedCursor<UByte>): Result<Unit, Error> = this.value

    public open fun write(buf: List<UByte>): Result<Int, Error> = this.value

    public open fun writeVectored(bufs: List<IoSlice>): Result<Int, Error> = this.value

    public open fun isWriteVectored(): Boolean = this.value

    public open fun flush(): Result<Unit, Error> = this.value

    public open fun seek(pos: SeekFrom): Result<ULong, Error> = this.value

    public open fun size(): Result<ULong, Error>? = this.value

    public open fun tell(): Result<ULong, Error> = this.value

    public open fun duplicate(): Result<File, Error> = this.value

    public open fun setPermissions(perm: FilePermissions): Result<Unit, Error> = this.value

    public open fun setTimes(times: FileTimes): Result<Unit, Error> = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.value

    public companion object {
        public fun open(path: Path, opts: OpenOptions): Result<File, Error> = unsupported()
    }
}

public open class FileAttr(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun size(): ULong = this.value

    public open fun perm(): FilePermissions = this.value

    public open fun fileType(): FileType = this.value

    public open fun modified(): Result<SystemTime, Error> = this.value

    public open fun accessed(): Result<SystemTime, Error> = this.value

    public open fun created(): Result<SystemTime, Error> = this.value

    public open fun clone(): FileAttr = this.value

    public companion object
}

public open class ReadDir(
    public var value: Nothing,
) : Iterable<Result<DirEntry, Error>>, AutoCloseable {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Result<DirEntry, Error>> = object : AbstractIterator<Result<DirEntry, Error>>() {
        override fun computeNext() {
            when (val v = this@ReadDir.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    override fun close() {
        this.drop()
    }

    public open fun drop() {
        this.value
    }

    public open fun dir(): Result<Dir, Error> = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.value

    public open fun next(): Result<DirEntry, Error>? = this.value

    public companion object
}

public open class DirEntry(
    public var value: Nothing,
) : AutoCloseable {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    override fun close() {
        this.drop()
    }

    public open fun drop() {
        this.value
    }

    public open fun path(): PathBuf = this.value

    public open fun fileName(): OsString = this.value

    public open fun metadata(): Result<FileAttr, Error> = this.value

    public open fun fileType(): Result<FileType, Error> = this.value

    public companion object
}

public open class OpenOptions {
    public open fun clone(): OpenOptions = OpenOptions()

    public open fun read(read: Boolean) {}

    public open fun write(write: Boolean) {}

    public open fun append(append: Boolean) {}

    public open fun truncate(truncate: Boolean) {}

    public open fun create(create: Boolean) {}

    public open fun createNew(createNew: Boolean) {}

    public companion object {
        public fun new(): OpenOptions = OpenOptions()
    }
}

public open class FileTimes {
    public open fun clone(): FileTimes = FileTimes()

    public open fun setAccessed(t: SystemTime) {}

    public open fun setModified(t: SystemTime) {}

    public companion object
}

public open class FilePermissions(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun readonly(): Boolean = this.value

    public open fun setReadonly(readonly: Boolean) {
        this.value
    }

    public open fun clone(): FilePermissions = this.value

    public open fun eq(other: FilePermissions): Boolean = this.value

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.value

    public companion object
}

public open class FileType(
    public var value: Nothing,
) {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    public open fun isDir(): Boolean = this.value

    public open fun isFile(): Boolean = this.value

    public open fun isSymlink(): Boolean = this.value

    public open fun clone(): FileType = this.value

    public open fun eq(other: FileType): Boolean = this.value

    public override fun hashCode(): Int {
        this.value
        return 0
    }

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = this.value

    public companion object
}

public open class DirBuilder {
    public open fun mkdir(p: Path): Result<Unit, Error> = unsupported()

    public companion object {
        public fun new(): DirBuilder = DirBuilder()
    }
}

public fun readdir(p: Path): Result<ReadDir, Error> = unsupported()

public fun unlink(p: Path): Result<Unit, Error> = unsupported()

public fun rename(old: Path, new: Path): Result<Unit, Error> = unsupported()

public fun renameNoreplace(old: Path, new: Path): Result<Unit, Error> = unsupported()

public fun setPerm(p: Path, perm: FilePermissions): Result<Unit, Error> = when (perm.value) {}

public fun setPermNofollow(p: Path, perm: FilePermissions): Result<Unit, Error> = when (perm.value) {}

public fun setTimes(p: Path, times: FileTimes): Result<Unit, Error> = unsupported()

public fun setTimesNofollow(p: Path, times: FileTimes): Result<Unit, Error> = unsupported()

public fun rmdir(p: Path): Result<Unit, Error> = unsupported()

public fun removeDirAll(path: Path): Result<Unit, Error> = unsupported()

public fun exists(path: Path): Result<Boolean, Error> = unsupported()

public fun readlink(p: Path): Result<PathBuf, Error> = unsupported()

public fun symlink(original: Path, link: Path): Result<Unit, Error> = unsupported()

public fun link(src: Path, dst: Path): Result<Unit, Error> = unsupported()

public fun stat(p: Path): Result<FileAttr, Error> = unsupported()

public fun lstat(p: Path): Result<FileAttr, Error> = unsupported()

public fun canonicalize(p: Path): Result<PathBuf, Error> = unsupported()

public fun copy(from: Path, to: Path): Result<ULong, Error> = unsupported()
