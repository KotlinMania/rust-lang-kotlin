package io.github.kotlinmania.rustlang.std.sys.pal.windows

import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * Map a [Result.T.WinError] to [io.Result.T].
 */
public interface IoResult<T> {
    public fun ioResult(): Result<T, Error>
}

public interface IsZero {
    public fun isZero(): Boolean
}
