// port-lint: source sys/env/unix.rs
package io.github.kotlinmania.rustlang.std.sys.env.unix

import io.github.kotlinmania.libc.nsgetEnviron
import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.core.slice.asPtr
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sync.nonpoison.rwlock.RwLock
import io.github.kotlinmania.rustlang.std.sync.poison.PoisonError
import io.github.kotlinmania.rustlang.std.sys.env.common.Env
import io.github.kotlinmania.rustlang.std.sys.helpers.smallcstring.runWithCstr
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.cvt

// Use `_NSGetEnviron` on Apple platforms.
//
// `_NSGetEnviron` is the documented alternative (see `man environ`), and has
// been available since the first versions of both macOS and iOS.
//
// Nowadays, specifically since macOS 10.8, `environ` has been exposed through
// `libdyld.dylib`, which is linked via. `libSystem.dylib`:
// <https://github.com/apple-oss-distributions/dyld/blob/dyld-1160.6/libdyld/libdyldGlue.cpp#L913>
//
// So in the end, it likely doesn't really matter which option we use, but the
// performance cost of using `_NSGetEnviron` is extremely miniscule, and it
// might be ever so slightly more supported, so let's just use that.
//
// NOTE: The header where this is defined (`crtExterns.h`) was added to the
// iOS 13.0 SDK, which has been the source of a great deal of confusion in the
// past about the availability of this API.
//
// NOTE(madsmtm): Neither this nor using `environ` has been verified to not
// cause App Store rejections; if this is found to be the case, an alternative
// implementation of this is possible using `[NSProcessInfo environment]`
// - which internally uses `_NSGetEnviron` and a system-wide lock on the
// environment variables to protect against `setenv`, so using that might be
// desirable anyhow? Though it also means that we have to link to Foundation.
public fun environ(): MutPtr = mutPtrFromRustCast(nsgetEnviron())

public var ENV_LOCK: RwLock<Unit> = RwLock.new()

public fun envReadLock(): Any = ENV_LOCK.read().unwrapOrElse(PoisonError::intoInner)

/**
 * Returns a vector of (variable, value) byte-vector pairs for all the
 * environment variables of the current process.
 */
public fun env(): Env {
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
        // A null return means the platform could not locate the symbol;
        // treat it like an empty environment.
        val environPtr = environ()
        if (!((environPtr).rawAddress == 0L)) {
            var environ = (PrimitiveLayouts.constPtrLayout(64L)).readNative((environPtr).rawAddress)
            if (!((environ).rawAddress == 0L)) {
                while (!(((PrimitiveLayouts.constPtrLayout(64L)).readNative((environ).rawAddress)).rawAddress == 0L)) {
                    when (val matchVal = parse(CStr.fromPtr((PrimitiveLayouts.constPtrLayout(64L)).readNative((environ).rawAddress)).toBytes())) {
                        null -> {}
                        else -> {
                            val keyValue = matchVal
                            result.add(keyValue)
                        }
                    }
                    environ = ConstPtr((environ).rawAddress + ((1).toLong() * (PrimitiveLayouts.constPtrLayout(64L)).strideBytes))
                }
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
            cvt(io.github.kotlinmania.libc.setenv(k1.asPtr(), v1.asPtr(), 1)).map(drop)
        })
    })

public fun unsetenv(n: OsStr): Result<Unit> =
    runWithCstr(n.asBytes(), { nbuf ->
        val guard = ENV_LOCK.write()
        cvt(io.github.kotlinmania.libc.unsetenv(nbuf.asPtr())).map(drop)
    })
