// port-lint: source sys/env/xous.rs
package io.github.kotlinmania.rustlang.std.sys.env.xous

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sync.oncelock.OnceLock
import io.github.kotlinmania.rustlang.std.sys.env.common.Env
import io.github.kotlinmania.rustlang.std.sys.env.uefi.uefienv.get
import io.github.kotlinmania.rustlang.std.sys.pal.params
import io.github.kotlinmania.rustlang.std.sys.pal.xous.params.EnvironmentBlock

public typealias EnvStore = Mutex<MutableMap<OsString, OsString>>

public var ENV: OnceLock<EnvStore> = OnceLock.new()

public fun getEnvStore(): EnvStore =
    ENV.getOrInit({
        var envStore = mutableMapOf()
        when (val matchVal = get()) {
            null -> {}
            else -> {
                val params = matchVal
                for (param in params) {
                    when (val matchVal1 = EnvironmentBlock.tryFrom(param)) {
                        is Ok -> {
                            val envs = matchVal1.value
                            for (env in envs) {
                                envStore.put(env.key.into(), env.value.into())
                            }
                            break
                        }
                        else -> {}
                    }
                }
            }
        }
        Mutex.new(envStore)
    })

public fun env(): Env {
    val env = getEnvStore().lock().unwrap().iter().map({ (k, v) -> Pair(k.clone(), v.clone()) }).collect()
    return Env.new(env)
}

public fun getenv(k: OsStr): OsString? = getEnvStore().lock().unwrap().get(k)

public fun setenv(k: OsStr, v: OsStr): Result<Unit> {
    val (k1, v1) = Pair(k.toOwned(), v.toOwned())
    getEnvStore().lock().unwrap().insert(k1, v1)
    return Result.Ok(Unit)
}

public fun unsetenv(k: OsStr): Result<Unit> {
    getEnvStore().lock().unwrap().remove(k)
    return Result.Ok(Unit)
}
