// port-lint: source panicking.rs
package io.github.kotlinmania.rustlang.alloc.panicking

import io.github.kotlinmania.rustlang.core.RustAny

/**
 * An internal trait used by std to pass data from std to `panicUnwind` and
 * other panic runtimes. Not intended to be stabilized any time soon, do not
 * use.
 */
public interface PanicPayload {
    /**
     * Take full ownership of the contents.
     *
     * After this method got called, only some dummy default value is left in `self`.
     * Calling this method twice, or calling `get` after calling this method, is an error.
     *
     * The argument is borrowed because the panic runtime (`__rust_start_panic`) only
     * gets a borrowed `dynamic PanicPayload`.
     */
    public fun takeBox(): RustAny

    /**
     * Just borrow the contents.
     */
    public fun get(): RustAny

    /**
     * Tries to borrow the contents as `String`, if possible without doing any allocations.
     */
    public fun asStr(): String? = null
}
