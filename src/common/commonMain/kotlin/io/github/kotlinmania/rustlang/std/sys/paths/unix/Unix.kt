// port-lint: source sys/paths/unix.rs
package io.github.kotlinmania.rustlang.std.sys.paths.unix

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.libc.unix.bsd.apple.CS_DARWIN_USER_TEMP_DIR
import io.github.kotlinmania.rustlang.libc.wasi.ERANGE
import io.github.kotlinmania.rustlang.std.env.varOs
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fs.readLink
import io.github.kotlinmania.rustlang.std.io.constError
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.unix.ffi.osstr.OsStringExt
import io.github.kotlinmania.rustlang.std.os.unix.ffi.osstr.asBytes
import io.github.kotlinmania.rustlang.std.os.unix.ffi.osstr.fromBytes
import io.github.kotlinmania.rustlang.std.os.unix.ffi.osstr.fromVec
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runPathWithCstr
import io.github.kotlinmania.rustlang.std.sys.pal.unix.conf.confstr

// Implementation of `std.os` functionality for unix systems

public val PATH_SEPARATOR: UByte = 58u

public fun getcwd(): Result<PathBuf, Error> {
    val buf = ArrayList(512)
    while (true) {
        run {
            val ptr = (buf.asMutPtr()).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }
            if (!io.github.kotlinmania.rustlang.libc.getcwd(ptr, buf.capacity()).isNull()) {
                val len = CStr.fromPtr((buf.asPtr()).let { ptrSrc -> ConstPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).toBytes().len()
                buf.setLen(len)
                buf.shrinkToFit()
                return Result.Ok<PathBuf, Error>(PathBuf.from(OsString.fromVec(buf)))
            } else {
                val error = io.github.kotlinmania.rustlang.core.io.error.Error.lastOsError()
                if (error.rawOsError() != ERANGE) {
                    return Result.Err<PathBuf, Error>(error)
                }
            }
            // Trigger the internal buffer resizing logic of `List` by requiring
            // more space than the current capacity.
            val cap = buf.capacity()
            buf.setLen(cap)
            buf.reserve(1)
        }
    }
}

public fun chdir(p: path.Path): Result<Unit, Error> {
    val result = when (val tryResult = runPathWithCstr(p, { p1 -> Result.Ok(io.github.kotlinmania.rustlang.libc.chdir(p1.asPtr())) })) { is Result.Ok -> tryResult.value; is Result.Err -> return Result.Err<Unit, Error>(tryResult.error) }
    return if (result == 0) {
        Result.Ok<Unit, Error>(Unit)
    } else {
        Result.Err<Unit, Error>(io.github.kotlinmania.rustlang.core.io.error.Error.lastOsError())
    }
}

// This can't just be `implementation Iterator` because that requires `a` to be live on
// drop (see #146045).
public typealias SplitPaths = Map<Split<UByte, (UByte) -> Boolean>, (List<UByte>) -> PathBuf>

public fun splitPaths(unparsed: OsStr): SplitPaths {
    fun isSeparator(b: UByte): Boolean = (b == PATH_SEPARATOR).toUByte()
    fun intoPathbuf(part: List<UByte>): PathBuf = PathBuf.from(OsStr.fromBytes(part))
    return unparsed.asBytes().split(::isSeparator).map(::intoPathbuf)
}

public typealias SplitPathsRef = Map<Split<UByte, (UByte) -> Boolean>, (List<UByte>) -> Path>

public fun splitPathsRef(unparsed: OsStr): SplitPathsRef? {
    fun splitPathsRef(unparsed1: OsStr): SplitPathsRef {
        fun isSeparator(b: UByte): Boolean = (b == PATH_SEPARATOR).toUByte()
        fun intoPath(part: List<UByte>): Path = Path.new(OsStr.fromBytes(part))
        return unparsed1.asBytes().split(::isSeparator).map(::intoPath)
    }
    return splitPathsRef(unparsed)
}

public open class JoinPathsError {
    public open fun fmt(f: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> = run { f.append("path segment contains separator `${Char.from(PATH_SEPARATOR)}`"); Result.Ok(Unit) }

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

public fun <I> joinPaths(paths: I): Result<OsString, JoinPathsError> where I : Iterator<OsStr> {
    val joined = mutableListOf<UByte>()
    for ((i, path) in paths.mapIndexed { index, item -> Pair(index, item) }) {
        val path1 = path.asRef().encodeToByteArray().map { it.toUByte() }
        if (i > 0) {
            joined.add(PATH_SEPARATOR)
        }
        if (path1.contains(PATH_SEPARATOR)) {
            return Result.Err<OsString, JoinPathsError>(JoinPathsError())
        }
        joined.addAll(path1)
    }
    return Result.Ok<OsString, JoinPathsError>(OsStringExt.fromVec(joined))
}

public fun currentExe(): Result<PathBuf, Error> =
    run {
        val matchVal = readLink("/proc/self/exe")
        when {
            matchVal is Result.Err && (matchVal as Result.Err).error.kind() == ErrorKind.NotFound -> {
                val e = matchVal.error
                Result.Err<PathBuf, Error>(constError(ErrorKind.Uncategorized, "no /proc/self/exe available. Is /proc mounted?"))
            }
            else -> {
                val other = matchVal
                other
            }
        }
    }

public fun darwinTempDir(): PathBuf {
    return confstr(CS_DARWIN_USER_TEMP_DIR, 64).map(PathBuf::from).unwrapOrElse({
        // It failed for whatever reason (there are several possible reasons),
        // so return the global one.
        PathBuf.from("/tmp")
    })
}

public fun tempDir(): PathBuf = (varOs("TMPDIR")?.let(PathBuf::from) ?: darwinTempDir())

public fun homeDir(): PathBuf? {
    fun fallback(): OsString? = null
    return (varOs("HOME").filter({ s -> !s.isEmpty() }) ?: fallback())?.let(PathBuf::from)
}
