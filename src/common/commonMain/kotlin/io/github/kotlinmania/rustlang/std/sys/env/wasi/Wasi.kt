// port-lint: source sys/env/wasi.rs
package io.github.kotlinmania.rustlang.std.sys.env.wasi

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.os.unix.ffi.osstr.OsStringExt
import io.github.kotlinmania.rustlang.os.unix.ffi.osstr.asBytes
import io.github.kotlinmania.rustlang.os.unix.ffi.osstr.fromVec
import io.github.kotlinmania.rustlang.std.io.error.Error
import io.github.kotlinmania.rustlang.std.sys.env.common.Env
import io.github.kotlinmania.rustlang.sync.nonpoison.rwlock.RwLock
import io.github.kotlinmania.rustlang.sys.helpers.smallcstring.runWithCstr
import io.github.kotlinmania.rustlang.sys.pal.unix.cvt

// This is not available yet in libc.
public expect fun wasilibcGetEnviron(): MutPtr

public var ENV_LOCK: RwLock<Unit> = RwLock.new()

public fun envReadLock(): Any = ENV_LOCK.read().unwrapOrElse({ it.intoInner() })

public fun envWriteLock(): Any = ENV_LOCK.write().unwrapOrElse({ it.intoInner() })

public fun env(): Env {
    // See src/libstd/sys/pal/unix/os.rs, same as that
    fun parse(input: List<UByte>): Pair<OsString, OsString>? {
        if (input.isEmpty()) {
            return null
        }
        val pos = memchr.memchr(61u, ((input).subList(1, (input).size)))?.let({ p -> p + 1 })
        return pos?.let({ p ->
            Pair(OsStringExt.fromVec(((input).subList(0, p)).toMutableList()), OsStringExt.fromVec(((input).subList(p + 1, (input).size)).toMutableList()))
        })
    }
    run {
        // Use `__wasilibc_get_environ` instead of `environ` here so that we
        // don't require wasi-libc to eagerly initialize the environment
        // variables.
        var environ = wasilibcGetEnviron()
        val result = mutableListOf<Pair<OsString, OsString>>()
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
        val v = constPtrFromRustCast(io.github.kotlinmania.rustlang.libc.getenv(k1.asPtr()))
        if (((v).rawAddress == 0L)) {
            Result.Ok(null)
        } else {
            // SAFETY: `v` cannot be mutated while executing this line since we've a read lock
            val bytes = CStr.fromPtr(v).toBytes().toMutableList()
            Result.Ok(OsStringExt.fromVec(bytes))
        }
    }).ok()
}

public fun setenv(k: OsStr, v: OsStr): Result<Unit, Error> =
    runWithCstr(k.asBytes(), { k1 ->
        runWithCstr(v.asBytes(), { v1 -> run {
            cvt(io.github.kotlinmania.rustlang.libc.setenv(k1.asPtr(), v1.asPtr(), 1)).map({ Unit })
        } })
    })

public fun unsetenv(n: OsStr): Result<Unit, Error> =
    runWithCstr(n.asBytes(), { nbuf -> run {
        cvt(io.github.kotlinmania.rustlang.libc.unsetenv(nbuf.asPtr())).map({ Unit })
    } })
