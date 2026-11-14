package io.github.kotlinmania.rustlang.std.sys.pal.windows

import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.io.error.Error

/**
 * Map a [Result.T.WinError] to [io.Result.T].
 */
public interface IoResult<T> {
    public fun ioResult(): Result<T, Error>
}

public interface IsZero {
    public fun isZero(): Boolean
}
