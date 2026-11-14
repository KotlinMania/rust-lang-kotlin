// port-lint: source sys/env/uefi.rs
package io.github.kotlinmania.rustlang.std.sys.env.uefi

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asMutPtr
import io.github.kotlinmania.rustlang.core.slice.rawSliceList
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.sys.env.common.Env
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupportedErr
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.openShell
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.osStringFromRaw
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.osStringToRaw

public fun env(): Env {
    val env = getAll().expect("not supported on this platform")
    return Env.new(env)
}

public fun getenv(key: OsStr): OsString? = get(key)

public fun setenv(key: OsStr, value: OsStr): Result<Unit> = set(key, value)

public fun unsetenv(key: OsStr): Result<Unit> = unset(key)

internal fun get(key: OsStr): OsString? {
    val shell = (openShell() ?: return null)
    val keyPtr = (osStringToRaw(key) ?: return null)
    return getRaw(shell, keyPtr.asMutPtr())
}

internal fun set(key: OsStr, value: OsStr): Result<Unit> {
    val keyPtr = when (val tryResult = osStringToRaw(key).okOr(io.constError(io.ErrorKind.InvalidInput, "invalid key"))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val valPtr = when (val tryResult = osStringToRaw(value).okOr(io.constError(io.ErrorKind.InvalidInput, "invalid value"))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    return setRaw(keyPtr.asMutPtr(), valPtr.asMutPtr())
}

internal fun unset(key: OsStr): Result<Unit> {
    val keyPtr = when (val tryResult = osStringToRaw(key).okOr(io.constError(io.ErrorKind.InvalidInput, "invalid key"))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val r = setRaw(keyPtr.asMutPtr(), MutPtr(0L))
    // The UEFI Shell spec only lists `EFI_SUCCESS` as a possible return value for
    // `SetEnv`, but the edk2 implementation can return errors. Allow most of these
    // errors to bubble up to the caller, but ignore `NotFound` errors; deleting a
    // nonexistent variable is not listed as an error condition of
    // `std.env.removeVar`.
    return if (r is Result.Err<*> && err.kind() == io.ErrorKind.NotFound) {
        Result.Ok(Unit)
    } else {
        r
    }
}

internal fun getAll(): Result<List<Pair<OsString, OsString>>> {
    val shell = when (val tryResult = openShell().okOr(unsupportedErr())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val vars = mutableListOf<Pair<OsString, OsString>>()
    val value = (shell.asPtr().getEnv)(MutPtr(0L))
    if (value.isNull()) {
        return Result.Ok(vars)
    }
    var start = 0
    // UEFI Shell returns all keys separated by NULL.
    // End of string is denoted by two NULLs
    for (i in 0 until Int.MAX_VALUE) {
        if (value.add(i) == 0) {
            // Two NULL signal end of string
            if (i == start) {
                break
            }
            val key = OsString.fromWide(rawSliceList(value.add(start), (i - start).toInt(), PrimitiveLayouts.U8))
            // SAFETY: val.add(start) is always NULL terminated
            val value1 = when (val tryResult = ((getRaw(shell, value.add(start)))?.let { Result.Ok(it) } ?: Result.Err(io.constError(io.ErrorKind.InvalidInput, "invalid value")))) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
            vars.add(Pair(key, value1))
            start = i + 1
        }
    }
    return Result.Ok(vars)
}

public fun getRaw(shell: NonNull, keyPtr: MutPtr): OsString? {
    val value = (shell.asPtr().getEnv)(keyPtr)
    return osStringFromRaw(value)
}

public fun setRaw(keyPtr: MutPtr, valPtr: MutPtr): Result<Unit> {
    val shell = when (val tryResult = openShell().okOr(unsupportedErr())) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
    val volatile = io.github.kotlinmania.refi.efi.Boolean.TRUE
    val r = (shell.asPtr().setEnv)(keyPtr, valPtr, volatile)
    return if (r.isError()) {
        Result.Err(io.Error.fromRawOsError(r.asUsize()))
    } else {
        Result.Ok(Unit)
    }
}
