// port-lint: source sys/env/sgx.rs
package io.github.kotlinmania.rustlang.std.sys.env.sgx

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sync.oncelock.OnceLock
import io.github.kotlinmania.rustlang.std.sys.env.common.Env

public typealias EnvStore = Mutex<MutableMap<OsString, OsString>>

// Specifying linkage/symbol name is solely to ensure a single instance between this crate and its unit tests
public var ENV: OnceLock<EnvStore> = OnceLock.new()

public fun env(): Env {
    val env = ENV.get()?.let({ env -> env.lock().unwrap().iter().map({ (k, v) -> Pair(k.clone(), v.clone()) }).collect() }).unwrapOrDefault()
    return Env.new(env)
}

public fun getenv(k: OsStr): OsString? = ENV.get()?.let({ s -> s.lock().unwrap().get(k) })

public fun setenv(k: OsStr, v: OsStr): Result<Unit> {
    ENV.getOrInit({ EnvStore.default() }).lock().unwrap().insert(k.toOwned(), v.toOwned())
    return Result.Ok(Unit)
}

public fun unsetenv(k: OsStr): Result<Unit> {
    when (val matchVal = ENV.get()) {
        null -> {}
        else -> {
            val env = matchVal
            env.lock().unwrap().remove(k)
        }
    }
    return Result.Ok(Unit)
}
