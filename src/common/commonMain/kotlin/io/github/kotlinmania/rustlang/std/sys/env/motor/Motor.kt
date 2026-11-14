// port-lint: source sys/env/motor.rs
package io.github.kotlinmania.rustlang.std.sys.env.motor

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sys.env.common.Env

public fun env(): Env {
    val motorEnv: List<Pair<String, String>> = io.github.kotlinmania.motort.process.env()
    val rustEnv = mutableListOf()
    for ((k, v) in motorEnv) {
        rustEnv.add(Pair(OsString.from(k), OsString.from(v)))
    }
    return Env.new(rustEnv)
}

public fun getenv(key: OsStr): OsString? = io.github.kotlinmania.motort.process.getenv(key.asStr()).map({ s -> OsString.from(s) })

public fun setenv(key: OsStr, value: OsStr): Result<Unit> = Result.Ok(io.github.kotlinmania.motort.process.setenv(key.asStr(), value.asStr()))

public fun unsetenv(key: OsStr): Result<Unit> = Result.Ok(io.github.kotlinmania.motort.process.unsetenv(key.asStr()))
