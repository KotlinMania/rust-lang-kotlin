// port-lint: source io/error/os_functions_atomic.rs
package io.github.kotlinmania.rustlang.core.io.error.osfunctionsatomic

import io.github.kotlinmania.rustlang.core.cmp.Ordering
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.error.OsFunctions
import io.github.kotlinmania.rustlang.core.io.error.RawOsError
import io.github.kotlinmania.rustlang.core.mem.constPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic

// OS-dependent functions
//
// `Error` needs OS functionalities to work interpret raw OS errors, but
// we can't link to anythink here in `alloc`. Therefore, we restrict
// creation of `Error` from raw OS errors in `std`, and require providing
// a vtable of operations when creating one.

/**
 * These default functions are not reachable, but have them just to be safe.
 */
public var OS_FUNCTIONS: AtomicPtr = Atomic.AtomicPtr.new((ConstPtr(OsFunctions.DEFAULT.toLong())).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) })

public fun getOsFunctions(): OsFunctions {
    // SAFETY:
    // * `OS_FUNCTIONS` is initially a pointer to `OsFunctions.DEFAULT`, which is valid for a static scope.
    // * `OS_FUNCTIONS` can only be changed by `setFunctions`, which only accepts `OsFunctions`.
    // * Therefore, `OS_FUNCTIONS` must always contain a valid non-null pointer with a static scope.
    // * `Relaxed` ordering is sufficient as the only way to write to `OS_FUNCTIONS` is through
    // `setFunctions`, which has as a safety precondition that any value passed in must
    // be constant and not created during runtime.
    return OS_FUNCTIONS.load(Atomic.Ordering.Relaxed)
}

/**
 * # Safety
 *
 * The provided reference must point to data that is entirely constant; it must
 * not be created during runtime.
 */
public fun setFunctions(f: OsFunctions) {
    fun setFunctionsInner(f1: OsFunctions) {
        OS_FUNCTIONS.store((constPtrFromRustCast(f1)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }, Atomic.Ordering.Relaxed)
    }
    if (((OS_FUNCTIONS.load(Atomic.Ordering.Relaxed)).rawAddress.toULong() != ((constPtrFromRustCast(f)).let { ptrSrc -> MutPtr(ptrSrc.rawAddress, ptrSrc.rawMeta) }).rawAddress.toULong())) {
        setFunctionsInner(f)
    }
}

public fun formatOsError(errno: RawOsError, fmt: StringBuilder): Result<Unit, Error> {
    val f = getOsFunctions()
    return (f.formatOsError)(errno, fmt)
}

public fun decodeErrorKind(errno: RawOsError): ErrorKind {
    val f = getOsFunctions()
    return (f.decodeErrorKind)(errno)
}

public fun isInterrupted(errno: RawOsError): Boolean {
    val f = getOsFunctions()
    return (f.isInterrupted)(errno)
}
