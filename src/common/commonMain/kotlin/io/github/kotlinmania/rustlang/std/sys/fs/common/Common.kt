// port-lint: source sys/fs/common.rs
package io.github.kotlinmania.rustlang.std.sys.fs.common

import io.github.kotlinmania.rustlang.core.clone.clone
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.SimpleMessage
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.fs.createDir
import io.github.kotlinmania.rustlang.std.fs.metadata
import io.github.kotlinmania.rustlang.std.fs.readDir
import io.github.kotlinmania.rustlang.std.fs.removeDir
import io.github.kotlinmania.rustlang.std.fs.removeFile
import io.github.kotlinmania.rustlang.std.fs.rename
import io.github.kotlinmania.rustlang.std.fs.symlinkMetadata
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.fs.unix.File
import io.github.kotlinmania.rustlang.std.sys.fs.unix.FileAttr
import io.github.kotlinmania.rustlang.std.sys.fs.unix.OpenOptions
import io.github.kotlinmania.rustlang.std.sys.helpers.ignoreNotfound

private class DebugStruct(
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

public val NOT_FILE_ERROR: Error = Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "the source path is neither a regular file nor a symlink to a regular file"))

public fun copy(from: Path, to: Path): Result<ULong, Error> {
    val reader = when (val tryResult = File.open(from)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ULong, Error>(tryResult.errValue()) }
    val metadata = when (val tryResult = reader.metadata()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ULong, Error>(tryResult.errValue()) }
    if (!metadata.isFile()) {
        return Result.Err<ULong, Error>(NOT_FILE_ERROR)
    }
    val writer = when (val tryResult = File.create(to)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ULong, Error>(tryResult.errValue()) }
    val perm = metadata.permissions()
    val ret = when (val tryResult = io.github.kotlinmania.rustlang.alloc.io.copy.copy(reader, writer)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ULong, Error>(tryResult.errValue()) }
    when (val tryResult = writer.setPermissions(perm)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<ULong, Error>(tryResult.errValue()) }
    return Result.Ok<ULong, Error>(ret)
}

public fun removeDirAll(path: Path): Result<Unit, Error> {
    val filetype = when (val tryResult = symlinkMetadata(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }.fileType()
    return if (filetype.isSymlink()) {
        removeFile(path)
    } else {
        removeDirAllRecursive(path)
    }
}

public fun removeDirAllRecursive(path: Path): Result<Unit, Error> {
    for (child in when (val tryResult = readDir(path)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }) {
        val result: Result<Unit, Error> =
            run {
                val child1 = when (val tryResult = child) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                if (when (val tryResult = child1.fileType()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }.isDir()) {
                    when (val tryResult = removeDirAllRecursive(child1.path())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                } else {
                    when (val tryResult = removeFile(child1.path())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
                }
            }
        // ignore internal NotFound errors to prevent race conditions
        if (result is Result.Err<*, *> && err.kind() != ErrorKind.NotFound) {
            return result
        }
    }
    return ignoreNotfound(removeDir(path))
}

public fun exists(path: Path): Result<Boolean, Error> =
    run {
        val matchVal = metadata(path)
        when {
            matchVal is Result.Ok<*, *> -> Result.Ok<Boolean, Error>(true)
            matchVal is Result.Err<*, *> && matchVal.errValue().kind() == ErrorKind.NotFound -> {
                val error = matchVal.errValue()
                Result.Ok<Boolean, Error>(false)
            }
            else -> {
                val error1 = matchVal.errValue()
                Result.Err<Boolean, Error>(error1)
            }
        }
    }

public open class Dir(
    public var path: PathBuf,
) {
    public open fun duplicate(): Result<Dir, Error> = Result.Ok<Dir, Error>(Dir(path = this.path.clone()))

    public open fun openFile(path: Path, opts: OpenOptions): Result<io.github.kotlinmania.rustlang.std.sys.fs.unix.File, Error> = File.open(this.path.join(path), opts)

    public open fun selfMetadata(): Result<FileAttr, Error> = this.path.metadata().map({ m -> m.intoInner() })

    public open fun removeFile(path: Path): Result<Unit, Error> = io.github.kotlinmania.rustlang.std.sys.fs.removeFile(this.path.join(path))

    public open fun rename(from: Path, toDir: Dir, to: Path): Result<Unit, Error> = rename(this.path.join(from), toDir.path.join(to))

    public open fun createDir(path: Path): Result<Unit, Error> = createDir(this.path.join(path))

    public open fun openDir(path: Path, opts: OpenOptions): Result<Dir, Error> = Dir.open(this.path.join(path), opts)

    public open fun removeDir(path: Path): Result<Unit, Error> = io.github.kotlinmania.rustlang.std.sys.fs.removeDir(this.path.join(path))

    public open fun metadata(path: Path): Result<FileAttr, Error> = this.path.join(path).metadata().map({ m -> m.intoInner() })

    public open fun symlinkMetadata(path: Path): Result<FileAttr, Error> = this.path.join(path).symlinkMetadata().map({ m -> m.intoInner() })

    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { DebugStruct(f, "Dir").field("path", this.path).finish(); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }

    public companion object {
        public fun open(path: Path, opts: OpenOptions): Result<Dir, Error> = path.canonicalize().map({ path1 -> Dir(path = path1) })

        public fun openForTraversal(path: Path): Result<Dir, Error> {
            val opts = OpenOptions.new()
            opts.read(true)
            return Dir.open(path, opts)
        }
    }
}

public open class ExtraHomeDirs {
    public open fun clone(): ExtraHomeDirs = ExtraHomeDirs()

    public companion object {
        public fun typeLayout(): TypeLayout<ExtraHomeDirs> =
            object : TypeLayout<ExtraHomeDirs> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): ExtraHomeDirs =
                    ExtraHomeDirs()
                override fun writeNative(addr: Long, value: ExtraHomeDirs) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExtraHomeDirs =
                    ExtraHomeDirs()
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExtraHomeDirs) {
                }
            }
    }
}

public open class ExtraMediaDirs {
    public open fun clone(): ExtraMediaDirs = ExtraMediaDirs()

    public companion object {
        public fun typeLayout(): TypeLayout<ExtraMediaDirs> =
            object : TypeLayout<ExtraMediaDirs> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): ExtraMediaDirs =
                    ExtraMediaDirs()
                override fun writeNative(addr: Long, value: ExtraMediaDirs) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): ExtraMediaDirs =
                    ExtraMediaDirs()
                override fun writeBytes(buf: ByteArray, offset: Int, value: ExtraMediaDirs) {
                }
            }
    }
}
