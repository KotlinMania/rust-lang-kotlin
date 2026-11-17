// port-lint: source sys/pal/unsupported/common.rs
package io.github.kotlinmania.rustlang.std.sys.pal.unsupported

import io.github.kotlinmania.rustlang.core.intrinsics.kmAbort
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.result.Result

// SAFETY: must be called only once during runtime initialization.
// NOTE: this is not guaranteed to run, for example when Rust code is called externally.
public fun init(argc: Int, argv: ConstPtr, sigpipe: UByte) {}

// SAFETY: must be called only once during runtime cleanup.
// NOTE: this is not guaranteed to run, for example when the program aborts.
public fun cleanup() {}

public fun <T> unsupported(): Result<T> = Result.Err(unsupportedErr())

public fun unsupportedErr(): Error = Error.UNSUPPORTED_PLATFORM

public fun abortInternal(): Nothing {
    kmAbort()
}
