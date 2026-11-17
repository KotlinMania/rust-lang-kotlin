package io.github.kotlinmania.rustlang.std.sys.helpers

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.result.Result

// Not used on all platforms.

/**
 * Computes `(value * numerator) / denom` without overflow, as long as both
 * `numerator * denom` and the overall result fit into `ULong` (which is the case
 * for our time conversions).
 */
public fun mulDivU64(value: ULong, numerator: ULong, denom: ULong): ULong {
    val q = value / denom
    val r = value % denom
    // Decompose value as (value/denom*denom + value%denom),
    // substitute into (value*numerator)/denom and simplify.
    // r < denom, so (denom*numerator) is the upper bound of (r*numerator)
    return q * numerator + r * numerator / denom
}

// Not used on all platforms.
public fun <T> ignoreNotfound(result: Result<T, Error>): Result<Unit, Error> =
    when {
        result is Result.Err<*, *> && result.errValue().kind() == ErrorKind.NotFound -> {
            val err = result.errValue()
            Result.Ok<Unit, Error>(Unit)
        }
        result is Result.Ok<*, *> -> Result.Ok<Unit, Error>(Unit)
        else -> {
            val err1 = (result as Result.Err<*, *>).errValue()
            Result.Err<Unit, Error>(err1)
        }
    }
