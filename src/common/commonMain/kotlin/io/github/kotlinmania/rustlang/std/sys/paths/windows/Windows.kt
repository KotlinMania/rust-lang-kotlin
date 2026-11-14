// port-lint: source sys/paths/windows.rs
package io.github.kotlinmania.rustlang.std.sys.paths.windows

import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.std.env.varOs
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.os.windows.ffi.EncodeWide
import io.github.kotlinmania.rustlang.std.os.windows.ffi.OsStringExt
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sys.pal.unix.cvt
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.WinError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.api.getLastError
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.getTempPath2W
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getCurrentDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getModuleFileNameW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.getUserProfileDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.c.windowssys.setCurrentDirectoryW
import io.github.kotlinmania.rustlang.std.sys.pal.windows.fillUtf16Buf
import io.github.kotlinmania.rustlang.std.sys.pal.windows.os2path

// Implementation of `std.os` functionality for Windows.

public open class SplitPaths(
    public var data: EncodeWide,
    public var mustYield: Boolean,
) : Iterable<PathBuf> {
    override fun iterator(): Iterator<PathBuf> = object : AbstractIterator<PathBuf>() {
        override fun computeNext() {
            when (val v = this@SplitPaths.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun next(): PathBuf? {
        // On Windows, the PATH environment variable is semicolon separated.
        // Double quotes are used as a way of introducing literal semicolons
        // (since c:\some;dir is a valid Windows path). Double quotes are not
        // themselves permitted in path names, so there is no way to escape a
        // double quote. Quoted regions can appear in arbitrary locations, so
        //
        // c:\foo;c:\som"e;di"r;c:\bar
        //
        // Should parse as [c:\foo, c:\some;dir, c:\bar].
        //
        // (The above is based on testing; there is no clear reference available
        // for the grammar.)
        val mustYield = this.mustYield
        this.mustYield = false
        val inProgress = mutableListOf()
        var inQuote = false
        for (b in this.data.byRef()) {
            if (b == '"'.code.toUShort()) {
                inQuote = !inQuote
            } else if (b == (';').code.toUShort() && !inQuote) {
                this.mustYield = true
                break
            } else {
                inProgress.add(b)
            }
        }
        return if (!mustYield && inProgress.isEmpty()) {
            null
        } else {
            os2path(inProgress)
        }
    }
}

public fun splitPaths(unparsed: OsStr): SplitPaths = SplitPaths(data = unparsed.encodeWide(), mustYield = true)

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

public fun splitPathsRef(unparsed: OsStr): SplitPathsRef? = null

public open class JoinPathsError {
    public open fun fmt(f: StringBuilder) {
        "path segment contains `\"`".fmt(f)
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

public fun <I> joinPaths(paths: I): Result<OsString, JoinPathsError> where I : Iterator<OsStr> {
    val joined = mutableListOf<UShort>()
    val sep = 59u.toUShort()
    for ((i, path) in paths.mapIndexed { index, item -> Pair(index, item) }) {
        val path1 = path.asRef()
        if (i > 0) {
            joined.add(sep)
        }
        val v = path1.encodeWide().toMutableList()
        if (v.contains((34u.toUShort()))) {
            return Result.Err(JoinPathsError())
        } else if (v.contains(sep)) {
            joined.add(34u.toUShort())
            joined.addAll(v)
            joined.add(34u.toUShort())
        } else {
            joined.addAll(v)
        }
    }
    return Result.Ok(OsStringExt.fromWide(joined))
}

public fun currentExe(): Result<PathBuf, Error> = fillUtf16Buf({ buf, sz -> getModuleFileNameW(MutPtr(0L), buf, sz) }, os2path)

public fun getcwd(): Result<PathBuf, Error> = fillUtf16Buf({ buf, sz -> getCurrentDirectoryW(sz, buf) }, os2path)

public fun chdir(p: Path): Result<Unit, Error> {
    val p1: OsStr = p.asRef()
    val p2 = p1.encodeWide().collect<Vec>()
    p2.push(0)
    return cvt(setCurrentDirectoryW(p2.asPtr())).map({ Unit })
}

public fun tempDir(): PathBuf = fillUtf16Buf({ buf, sz -> getTempPath2W(sz, buf) }, os2path).unwrap()

public fun homeDirCrt(): PathBuf? =
    run {
        // Defined in processthreadsapi.h.
        val CURRENT_PROCESS_TOKEN: Int = -4
        fillUtf16Buf({ buf, sz ->
            // GetUserProfileDirectoryW does not quite use the usual protocol for
            // negotiating the buffer size, so we have to translate.
            run {
                val matchVal = getUserProfileDirectoryW(withoutProvenanceMut(CURRENT_PROCESS_TOKEN), buf, sz)
                when {
                    matchVal == 0 && getLastError() != WinError.INSUFFICIENT_BUFFER -> 0
                    matchVal == 0 -> sz
                    else -> sz - 1
                }
            }
        }, os2path).ok()
    }

public fun homeDir(): PathBuf? = (varOs("USERPROFILE").filter({ s -> !s.isEmpty() })?.let(PathBuf::from) ?: .homeDirCrt())
