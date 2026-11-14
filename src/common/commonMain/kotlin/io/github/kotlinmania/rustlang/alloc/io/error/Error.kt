// port-lint: source io/error.rs
package io.github.kotlinmania.rustlang.alloc.io.error

import io.github.kotlinmania.rustlang.alloc.boxed.Box
import io.github.kotlinmania.rustlang.alloc.collections.TryReserveError
import io.github.kotlinmania.rustlang.alloc.ffi.cstr.NulError
import io.github.kotlinmania.rustlang.alloc.io.constError
import io.github.kotlinmania.rustlang.core.io.error.Custom
import io.github.kotlinmania.rustlang.core.io.error.CustomOwner
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.slice.asPtr

/**
 * Creates a new I/O error from a known kind of error as well as an
 * arbitrary error payload.
 *
 * This function is used to generically create I/O errors which do not
 * originate from the OS itself. The `error` argument is an arbitrary
 * payload which will be contained in this [Error].
 *
 * Note that this function allocates memory on the heap.
 * If no extra payload is required, use the `From` conversion from
 * `ErrorKind`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.io.Error
 * import std.io.ErrorKind
 *
 * // errors can be created from strings
 * val customError = Error.new(ErrorKind.Other, "oh no!")
 * // errors can also be created from other errors
 * val customError2 = Error.new(ErrorKind.Interrupted, customError)
 * // creating an error without payload (and without memory allocation)
 * val eofError = Error.from(ErrorKind.UnexpectedEof)
 * ```
 */
public fun Error.Companion.new(kind: ErrorKind, error: Any): Error {
    val custom = customOwnerFromBox(kind, error)
    // SAFETY: `customOwner` has been constructed from a `Holder` from the `alloc` crate.
    return Error.fromCustomOwner(custom)
}

// Note(#74481): Hard-links required to link from `alloc` to `std` for incoherent method

/**
 * Creates a new I/O error from an arbitrary error payload.
 *
 * This function is used to generically create I/O errors which do not
 * originate from the OS itself. It is a shortcut for [`Error.new`][new]
 * with [ErrorKind.Other].
 *
 * [new]: struct.Error.html#method.new
 *
 * # Examples
 *
 * ```kotlin
 * import std.io.Error
 *
 * // errors can be created from strings
 * val customError = Error.other("oh no!")
 * // errors can also be created from other errors
 * val customError2 = Error.other(customError)
 * ```
 */
public fun Error.Companion.other(error: Any): Error = Error.new(ErrorKind.Other, error)

// Note(#74481): Hard-links required to link from `alloc` to `std` for incoherent method

/**
 * Consumes the `Error`, returning its inner error (if any).
 *
 * If this [Error] was constructed via [`new`][new] or [`other`][other],
 * then this function will return [Some],
 * otherwise it will return [None].
 *
 * [new]: struct.Error.html#method.new
 * [other]: struct.Error.html#method.other
 *
 * # Examples
 *
 * ```kotlin
 * import std.io.Error
 * import std.io.ErrorKind
 *
 * fun printError(err: Error) {
 *     when (val matchVal = err.intoInner()) {
 *         null -> {
 *             println("No inner error")
 *         }
 *         else -> {
 *             val innerErr = matchVal
 *             println("Inner error: $innerErr")
 *         }
 *     }
 * }
 * fun main() {
 *     // Will print "No inner error".
 *     printError(Error.lastOsError())
 *     // Will print "Inner error: ...".
 *     printError(Error.new(ErrorKind.Other, "oh no!"))
 * }
 * ```
 */
public fun Error.intoInner(): Any? {
    val customOwner = (this.intoCustomOwner().ok() ?: return null)
    val ptr = customOwner.intoRaw().asPtr()
    // SAFETY:
    // `Error` can only contain a `CustomOwner` if it was constructed using `Holder.intoRaw`.
    val custom = Box.fromRaw(ptr)
    val ptr1 = custom.intoRaw().asPtr()
    // SAFETY:
    // Any `CustomOwner` from an `Error` was constructed by the `alloc` crate
    // to contain a `Custom` which itself was constructed with `Holder.intoRaw`.
    return Box.fromRaw(ptr1)
}

// Note(#74481): Hard-links required to link from `alloc` to `std` for incoherent method

/**
 * Attempts to downcast the custom boxed error to `E`.
 *
 * If this [Error] contains a custom boxed error,
 * then it would attempt downcasting on the boxed error,
 * otherwise it will return [Err].
 *
 * If the custom boxed error has the same type as `E`, it will return [Ok],
 * otherwise it will also return [Err].
 *
 * This method is meant to be a convenience routine for calling
 * `Holder<dynamic Error + Sync + Send>.downcast` on the custom boxed error, returned by
 * [`Error.intoInner`][intoInner].
 *
 * [intoInner]: struct.Error.html#method.intoInner
 *
 * # Examples
 *
 * ```kotlin
 * import std.error.Error
 *
 * sealed class E {
 *     public data class Io(public var value: io.Error) : E()
 *     public data object SomeOtherVariant : E()
 *
 *     public open fun from(err: io.Error): E {
 *         return err.downcast<E>().unwrapOrElse({ E.Io(it) })
 *     }
 * }
 * fun fromError(err: E): io.Error {
 *     return when (err) {
 *         is E.Io -> {
 *             val (ioError) = err
 *             ioError
 *         }
 *         else -> {
 *             val e = err
 *             io.Error.new(io.ErrorKind.Other, e)
 *         }
 *     }
 * }
 * val e = E.SomeOtherVariant
 * // Convert it to an io.Error
 * val ioError = io.Error.from(e)
 * // Cast it back to the original variant
 * val e1 = E.from(ioError)
 * check(when (e1) {
 *     E.SomeOtherVariant -> true
 *     else -> false
 * })
 * val ioError1 = io.Error.from(io.ErrorKind.AlreadyExists)
 * // Convert it to E
 * val e2 = E.from(ioError1)
 * // Cast it back to the original variant
 * val ioError2 = io.Error.from(e2)
 * check(ioError2.kind() == io.ErrorKind.AlreadyExists)
 * check(ioError2.getRef() == null)
 * check(ioError2.rawOsError() == null)
 * ```
 */
public fun <E> Error.downcast(): Result<E, Error> =
    if (this.getRef() != null && e.isMatch<E>()) {
        if (this.intoInner() != null && b.downcast<E>() is Result.Ok<*>) {
            Result.Ok(err)
        } else {
            // SAFETY: We have just checked that the condition is true
            unreachableUnchecked()
        }
    } else {
        Result.Err(this)
    }

/**
 * Converts a [ffi.NulError] into a [Error].
 */
public fun fromError(arg0: NulError): Error = constError(ErrorKind.InvalidInput, "data provided contains a nul byte")

/**
 * Converts `TryReserveError` to an error with [ErrorKind.OutOfMemory].
 *
 * `TryReserveError` won't be available as the error `source()`,
 * but this may change in the future.
 */
public fun fromError(arg0: TryReserveError): Error {
    // ErrorData.Custom allocates, which isn't great for handling OOM errors.
    return ErrorKind.OutOfMemory.into()
}

public fun customOwnerFromBox(kind: ErrorKind, error: Any): CustomOwner {
    /**
     * # Safety
     *
     * `ptr` must be valid to pass into `Holder.fromRaw`.
     */
    fun dropBoxRaw(ptr: NonNull) {
        // SAFETY: Caller ensures `ptr` is valid to pass into `Holder.fromRaw`.
        drop(Box.fromNonNull(ptr))
    }
    val error1 = Box.intoNonNull(error)
    // SAFETY:
    // * `error` is valid up to a static scope, and owns its pointee.
    // * `dropBoxRaw` is safe to call for the pointer `error` exactly once.
    // * `dropBoxRaw` is safe to call on a pointer to this instance of `Custom`,
    // and will be stored in a `CustomOwner`.
    val custom = Custom.fromRaw(kind, error1, dropBoxRaw<Any>, dropBoxRaw<Custom>)
    val custom1 = Box.intoNonNull(custom)
    // SAFETY: the `outerDrop` provided to `custom` is valid for itself.
    return CustomOwner.fromRaw(custom1)
}
