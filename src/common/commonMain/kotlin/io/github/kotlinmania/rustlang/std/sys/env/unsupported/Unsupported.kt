// port-lint: source sys/env/unsupported.rs
package io.github.kotlinmania.rustlang.std.sys.env.unsupported

import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.io.ErrorKind
import io.github.kotlinmania.rustlang.std.io.constError

public open class Env(
    public var value: Nothing,
) : Iterable<Pair<OsString, OsString>> {
    public var field0: Nothing
        get() = value
        set(v) {
            value = v
        }

    override fun iterator(): Iterator<Pair<OsString, OsString>> = object : AbstractIterator<Pair<OsString, OsString>>() {
        override fun computeNext() {
            when (val v = this@Env.next()) {
                null -> done()
                else -> setNext(v)
            }
        }
    }

    public open fun fmt(arg0: StringBuilder): Result<Unit, Error> = this.value

    public open fun next(): Pair<OsString, OsString>? = this.value
}

public fun env(): Env {
    error("not supported on this platform")
}

public fun getenv(arg0: OsStr): OsString? = null

public fun setenv(arg0: OsStr, arg1: OsStr): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Err<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(constError(ErrorKind.Unsupported, "cannot set env vars on this platform"))

public fun unsetenv(arg0: OsStr): Result<Unit, io.github.kotlinmania.rustlang.std.io.error.Error> = Result.Err<Unit, io.github.kotlinmania.rustlang.std.io.error.Error>(constError(ErrorKind.Unsupported, "cannot unset env vars on this platform"))
