// port-lint: source sys/env/hermit.rs
package io.github.kotlinmania.rustlang.std.sys.env.hermit

import io.github.kotlinmania.rustlang.core.ffi.cstr.CStr
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.memchr.memchr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.os.hermit.ffi.OsStringExt
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sys.env.common.Env

public var ENV: Mutex<MutableMap<OsString, OsString>?> = Mutex.new(null)

public fun init(env: ConstPtr) {
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
    val guard = ENV.lock().unwrap()
    val map = guard.insert(mutableMapOf())
    if (((env).rawAddress == 0L)) {
        return
    }
    run {
        var environ = env
        while (!(((PrimitiveLayouts.constPtrLayout(64L)).readNative((environ).rawAddress)).rawAddress == 0L)) {
            when (val matchVal = parse(CStr.fromPtr((PrimitiveLayouts.constPtrLayout(64L)).readNative((environ).rawAddress)).toBytes())) {
                null -> {}
                else -> {
                    val key = matchVal.first
                    val value = matchVal.second
                    map.insert(key, value)
                }
            }
            environ = ConstPtr((environ).rawAddress + ((1).toLong() * (PrimitiveLayouts.constPtrLayout(64L)).strideBytes))
        }
    }
}

/**
 * Returns a vector of (variable, value) byte-vector pairs for all the
 * environment variables of the current process.
 */
public fun env(): Env {
    val guard = ENV.lock().unwrap()
    val env = guard.asRef().unwrap()
    val result = env.iter().map({ (key, value) -> Pair(key.clone(), value.clone()) }).collect()
    return Env.new(result)
}

public fun getenv(k: OsStr): OsString? = ENV.lock().unwrap().asRef().unwrap().get(k)

public fun setenv(k: OsStr, v: OsStr): Result<Unit> {
    val (k1, v1) = Pair(k.toOwned(), v.toOwned())
    ENV.lock().unwrap().asMut().unwrap().insert(k1, v1)
    return Result.Ok(Unit)
}

public fun unsetenv(k: OsStr): Result<Unit> {
    ENV.lock().unwrap().asMut().unwrap().remove(k)
    return Result.Ok(Unit)
}
