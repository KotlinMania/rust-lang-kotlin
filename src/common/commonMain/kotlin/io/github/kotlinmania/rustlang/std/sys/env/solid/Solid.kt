// port-lint: source sys/env/solid.rs
package io.github.kotlinmania.rustlang.std.sys.env.solid

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.os.raw.CInt
import io.github.kotlinmania.rustlang.std.os.solid.ffi.OsStringExt
import io.github.kotlinmania.rustlang.std.sync.nonpoison.rwlock.RwLock
import io.github.kotlinmania.rustlang.std.sync.poison.PoisonError
import io.github.kotlinmania.rustlang.std.sys.env.common.Env
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runWithCstr

public var ENV_LOCK: RwLock<Unit> = RwLock.new()

public fun envReadLock(): Any = ENV_LOCK.read().unwrapOrElse(PoisonError::intoInner)

/**
 * Returns a vector of (variable, value) byte-vector pairs for all the
 * environment variables of the current process.
 */
public fun env(): Env {
    var ENVIRON: ConstPtr = ConstPtr(0L)
    fun parse(input: List<UByte>): Pair<OsString, OsString>? {
        // Strategy (copied from glibc): Variable name and value are separated
        // by an ASCII equals sign '='. Since a variable name must not be
        // empty, allow variable names starting with an equals sign. Skip all
        // malformed lines.
        if (input.isEmpty()) {
            return null
        }
        val pos = memchr(61u, ((input).slice(1 until input.size))).map({ p -> p + 1 })
        return pos.map({ p ->
            Pair(OsStringExt.fromVec(((input).slice(0 until p)).toVec()), OsStringExt.fromVec(((input).slice(p + 1 until input.size)).toVec()))
        })
    }
    return run {
        val result = mutableListOf()
        if (!ENVIRON.isNull()) {
            while (!ENVIRON.isNull()) {
                when (val matchVal = parse(CStr.fromPtr(ENVIRON).toBytes())) {
                    null -> {}
                    else -> {
                        val keyValue = matchVal
                        result.add(keyValue)
                    }
                }
                ENVIRON = ENVIRON.add(1)
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
        runWithCstr(v.asBytes(), { v1 ->
            val guard = ENV_LOCK.write()
            cvtEnv(io.github.kotlinmania.libc.setenv(k1.asPtr(), v1.asPtr(), 1)).map(drop)
        })
    })

public fun unsetenv(n: OsStr): Result<Unit> =
    runWithCstr(n.asBytes(), { nbuf ->
        val guard = ENV_LOCK.write()
        cvtEnv(io.github.kotlinmania.libc.unsetenv(nbuf.asPtr())).map(drop)
    })

/**
 * In kmclib, `setenv` and `unsetenv` don't always set `errno`, so this
 * function just returns a generic error.
 */
public fun cvtEnv(t: CInt): Result<CInt> =
    if (t == -1) {
        Result.Err(io.constError(io.ErrorKind.Uncategorized, "failure"))
    } else {
        Result.Ok(t)
    }
