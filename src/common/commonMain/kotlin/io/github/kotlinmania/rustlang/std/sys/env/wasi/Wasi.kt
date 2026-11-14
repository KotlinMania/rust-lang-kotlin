// port-lint: source sys/env/wasi.rs
package io.github.kotlinmania.rustlang.std.sys.env.wasi

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sys.env.common.Env
import io.github.kotlinmania.rustlang.std.sys.env.unix.envReadLock
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runWithCstr
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvt

// This is not available yet in libc.
public expect fun wasilibcGetEnviron(): MutPtr

public fun env(): Env {
    // See src/libstd/sys/pal/unix/os.rs, same as that
    fun parse(input: List<UByte>): Pair<OsString, OsString>? {
        if (input.isEmpty()) {
            return null
        }
        val pos = memchr(61u, ((input).slice(1 until input.size))).map({ p -> p + 1 })
        return pos.map({ p ->
            Pair(OsStringExt.fromVec(((input).slice(0 until p)).toVec()), OsStringExt.fromVec(((input).slice(p + 1 until input.size)).toVec()))
        })
    }
    return run {
        // Use `__wasilibc_get_environ` instead of `environ` here so that we
        // don't require wasi-libc to eagerly initialize the environment
        // variables.
        var environ = wasilibcGetEnviron()
        val result = mutableListOf()
        if (!((environ).rawAddress == 0L)) {
            while (!(((PrimitiveLayouts.mutPtrLayout(64L)).readNative((environ).rawAddress)).rawAddress == 0L)) {
                when (val matchVal = parse(CStr.fromPtr((PrimitiveLayouts.mutPtrLayout(64L)).readNative((environ).rawAddress)).toBytes())) {
                    null -> {}
                    else -> {
                        val keyValue = matchVal
                        result.add(keyValue)
                    }
                }
                environ = MutPtr((environ).rawAddress + ((1).toLong() * (PrimitiveLayouts.mutPtrLayout(64L)).strideBytes))
            }
        }
        return Env.new(result)
    }
}

public fun getenv(k: OsStr): OsString? {
    // environment variables with a nul byte can't be set, so their value is
    // always None as well
    return runWithCstr(k.asBytes(), { k1 ->
        val guard = envReadLock()
        val v = constPtrFromRustCast(io.github.kotlinmania.libc.getenv(k1.asPtr()))
        if (((v).rawAddress == 0L)) {
            Result.Ok(null)
        } else {
            // SAFETY: `v` cannot be mutated while executing this line since we've a read lock
            val bytes = CStr.fromPtr(v).toBytes().toVec()
            Result.Ok(OsStringExt.fromVec(bytes))
        }
    }).ok().flatten()
}

public fun setenv(k: OsStr, v: OsStr): Result<Unit> =
    runWithCstr(k.asBytes(), { k1 ->
        runWithCstr(v.asBytes(), { v1 -> run {
            cvt(io.github.kotlinmania.libc.setenv(k1.asPtr(), v1.asPtr(), 1)).map(drop)
        } })
    })

public fun unsetenv(n: OsStr): Result<Unit> =
    runWithCstr(n.asBytes(), { nbuf -> run {
        cvt(io.github.kotlinmania.libc.unsetenv(nbuf.asPtr())).map(drop)
    } })
