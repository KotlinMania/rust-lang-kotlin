// port-lint: source os/motor/ffi.rs
package io.github.kotlinmania.rustlang.std.os.motor.ffi

import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString

// Motor OS-specific extensions to primitives in the [std.ffi] module.

/**
 * Motor OS–specific extensions to [OsString].
 */
public interface OsStringExt<T> {
    /**
     * Yields the underlying UTF-8 string of this [OsString].
     *
     * OS strings on Motor OS are guaranteed to be UTF-8, so are just strings.
     */
    public fun intoString(): String
}

public fun OsString.intoString(): String = this.intoInner().inner

/**
 * Motor OS–specific extensions to [OsString].
 */
public interface OsStrExt<T> {
    /**
     * Gets the underlying UTF-8 string view of the [OsStr] slice.
     *
     * OS strings on Motor OS are guaranteed to be UTF-8, so are just strings.
     */
    public fun asStr(): String
}

public fun OsStr.asStr(): String = this.asInner().inner
