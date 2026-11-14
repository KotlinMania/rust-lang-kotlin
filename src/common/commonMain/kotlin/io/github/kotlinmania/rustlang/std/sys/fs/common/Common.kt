// port-lint: source sys/fs/common.rs
package io.github.kotlinmania.rustlang.std.sys.fs.common

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.fs.createDir
import io.github.kotlinmania.rustlang.std.fs.removeDir
import io.github.kotlinmania.rustlang.std.fs.removeFile
import io.github.kotlinmania.rustlang.std.fs.rename
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Error
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.fs.hermit.File
import io.github.kotlinmania.rustlang.std.sys.fs.hermit.FileAttr
import io.github.kotlinmania.rustlang.std.sys.fs.hermit.OpenOptions
import io.github.kotlinmania.rustlang.std.sys.helpers.ignoreNotfound

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

internal val NOT_FILE_ERROR: Error = io.constError(ErrorKind.InvalidInput, "the source path is neither a regular file nor a symlink to a regular file")

public fun copy(from: Path, to: Path): Result<ULong> {
    val reader = when (val tryResult = fs.File.open(from)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val metadata = when (val tryResult = reader.metadata()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    if (!metadata.isFile()) {
        return Result.Err(NOT_FILE_ERROR)
    }
    val writer = when (val tryResult = fs.File.create(to)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val perm = metadata.permissions()
    val ret = when (val tryResult = io.copy(reader, writer)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    when (val tryResult = writer.setPermissions(perm)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return Result.Ok(ret)
}

public fun removeDirAll(path: Path): Result<Unit> {
    val filetype = when (val tryResult = fs.symlinkMetadata(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.fileType()
    return if (filetype.isSymlink()) {
        fs.removeFile(path)
    } else {
        removeDirAllRecursive(path)
    }
}

public fun removeDirAllRecursive(path: Path): Result<Unit> {
    for (child in when (val tryResult = fs.readDir(path)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }) {
        val result: Result<Unit> =
            run {
                val child1 = when (val tryResult = child) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                if (when (val tryResult = child1.fileType()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.isDir()) {
                    when (val tryResult = removeDirAllRecursive(child1.path())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                } else {
                    when (val tryResult = fs.removeFile(child1.path())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                }
            }
        // ignore internal NotFound errors to prevent race conditions
        if (result is Result.Err<*> && err.kind() != io.ErrorKind.NotFound) {
            return result
        }
    }
    return ignoreNotfound(fs.removeDir(path))
}

public fun exists(path: Path): Result<Boolean> =
    run {
        val matchVal = fs.metadata(path)
        when {
            matchVal is Result.Ok<*> -> Result.Ok(true)
            matchVal is Result.Err<*> && (matchVal as Result.Err<*>).error.kind() == io.ErrorKind.NotFound -> {
                val error = (matchVal as Result.Err<*>).error
                Result.Ok(false)
            }
            else -> {
                val error1 = (matchVal as Result.Err<*>).error
                Result.Err(error1)
            }
        }
    }

public open class Dir(
    public var path: PathBuf,
) {
    public open fun duplicate(): Result<Dir> = Result.Ok(Dir(path = this.path.clone()))

    public open fun openFile(path: Path, opts: OpenOptions): Result<File> = File.open(this.path.join(path), opts)

    public open fun selfMetadata(): Result<FileAttr> = this.path.metadata().map({ m -> m.intoInner() })

    public open fun removeFile(path: Path): Result<Unit> = removeFile(this.path.join(path))

    public open fun rename(from: Path, toDir: Dir, to: Path): Result<Unit> = rename(this.path.join(from), toDir.path.join(to))

    public open fun createDir(path: Path): Result<Unit> = createDir(this.path.join(path))

    public open fun openDir(path: Path, opts: OpenOptions): Result<Dir> = Dir.open(this.path.join(path), opts)

    public open fun removeDir(path: Path): Result<Unit> = removeDir(this.path.join(path))

    public open fun metadata(path: Path): Result<FileAttr> = this.path.join(path).metadata().map({ m -> m.intoInner() })

    public open fun symlinkMetadata(path: Path): Result<FileAttr> = this.path.join(path).symlinkMetadata().map({ m -> m.intoInner() })

    public open fun fmt(f: StringBuilder) {
        DebugStruct(f, "Dir").field("path", this.path).finish()
    }

    public companion object {
        public fun open(path: Path, opts: OpenOptions): Result<Dir> = path.canonicalize().map({ path1 -> Dir(path = path1) })

        public fun openForTraversal(path: Path): Result<Dir> {
            val opts = OpenOptions.new()
            opts.read(true)
            return Dir.open(path, opts)
        }
    }
}

public open class ExtraHomeDirs {
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<ExtraHomeDirs> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<ExtraHomeDirs> {
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
    public companion object {
        public fun typeLayout(): io.github.kotlinmania.rustlang.core.mem.TypeLayout<ExtraMediaDirs> =
            object : io.github.kotlinmania.rustlang.core.mem.TypeLayout<ExtraMediaDirs> {
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
