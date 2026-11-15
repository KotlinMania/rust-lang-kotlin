// port-lint: source io/error/os_functions.rs
package io.github.kotlinmania.rustlang.core.io.error.osfunctions

import io.github.kotlinmania.rustlang.io.error.ErrorKind
import io.github.kotlinmania.rustlang.io.error.OsFunctions
import io.github.kotlinmania.rustlang.io.error.RawOsError

/**
 * # Safety
 *
 * The provided reference must point to data that is entirely constant; it must
 * not be created during runtime.
 */
public fun setFunctions(f: OsFunctions) {
    // Note: externally implementable items may allow for weak linkage, allowing
    // these methods to be overridden even when atomic pointers are not supported.
}

public fun formatOsError(errno: RawOsError, fmt: StringBuilder) {
    val f = OsFunctions.DEFAULT
    (f.formatOsError)(errno, fmt)
}

public fun decodeErrorKind(errno: RawOsError): ErrorKind {
    val f = OsFunctions.DEFAULT
    return (f.decodeErrorKind)(errno)
}

public fun isInterrupted(errno: RawOsError): Boolean {
    val f = OsFunctions.DEFAULT
    return (f.isInterrupted)(errno)
}
