// port-lint: source sys/paths/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.paths.unsupported

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.path.Path
import io.github.kotlinmania.rustlang.path.PathBuf
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.sys.pal.unsupported.unsupported

public fun getcwd(): Result<PathBuf, Error> = unsupported()

public fun chdir(arg0: path.Path): Result<Unit, Error> = unsupported()

public open class SplitPaths(
    public var field0: Nothing,
    public var field1: Unit,
) : Iterable<PathBuf> {
    override fun iterator(): Iterator<PathBuf> = object : AbstractIterator<PathBuf>() {
        override fun computeNext() {
            when (val v = this@SplitPaths.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): PathBuf? = this.field0
}

public open class SplitPathsRef(
    public var field0: Nothing,
    public var field1: Unit,
) : Iterable<Path> {
    override fun iterator(): Iterator<Path> = object : AbstractIterator<Path>() {
        override fun computeNext() {
            when (val v = this@SplitPathsRef.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): Path? = this.field0
}

public fun splitPaths(unparsed: OsStr): SplitPaths {
    error("unsupported")
}

public fun splitPathsRef(unparsed: OsStr): SplitPathsRef? = null

public open class JoinPathsError {
    public open fun fmt(f: StringBuilder) {
        "not supported on this platform yet".fmt(f)
    }

    public companion object {
        public fun typeLayout(): TypeLayout<JoinPathsError> =
            object : TypeLayout<JoinPathsError> {
                override val alignBytes: Long = 1L
                override val sizeBytes: Long = 0L
                override fun readNative(addr: Long): JoinPathsError =
                    JoinPathsError()
                override fun writeNative(addr: Long, value: JoinPathsError) {
                }
                override fun readBytes(buf: ByteArray, offset: Int): JoinPathsError =
                    JoinPathsError()
                override fun writeBytes(buf: ByteArray, offset: Int, value: JoinPathsError) {
                }
            }
    }
}

public fun <I> joinPaths(paths: I): Result<OsString, JoinPathsError> where I : Iterator<OsStr> = Result.Err(JoinPathsError())

public fun currentExe(): Result<PathBuf, Error> = unsupported()

public fun tempDir(): PathBuf {
    error("no filesystem on this platform")
}

public fun homeDir(): PathBuf? = null
