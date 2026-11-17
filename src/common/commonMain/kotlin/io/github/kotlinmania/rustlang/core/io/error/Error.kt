// port-lint: source io/error.rs
package io.github.kotlinmania.rustlang.core.io.error

import io.github.kotlinmania.rustlang.core.fmt.fmt
import io.github.kotlinmania.rustlang.core.hint.mustUse
import io.github.kotlinmania.rustlang.core.hint.unreachableUnchecked
import io.github.kotlinmania.rustlang.core.io.error.ErrorKind.*
import io.github.kotlinmania.rustlang.core.io.error.osfunctions.decodeErrorKind
import io.github.kotlinmania.rustlang.core.io.error.osfunctions.formatOsError
import io.github.kotlinmania.rustlang.core.io.error.osfunctions.isInterrupted
import io.github.kotlinmania.rustlang.core.io.error.osfunctions.setFunctions
import io.github.kotlinmania.rustlang.core.io.error.repr.Repr
import io.github.kotlinmania.rustlang.core.mem.TransparentTypeLayout
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.UnresolvedTypeLayout
import io.github.kotlinmania.rustlang.core.mem.allocateNativeCell
import io.github.kotlinmania.rustlang.core.mem.forget
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.ptr.nonnull.NonNull
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

public fun interface OsFunctionsFormatOsErrorCallback {
    public operator fun invoke(arg0: RawOsError, arg1: StringBuilder): io.github.kotlinmania.rustlang.core.result.Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>
}

public fun interface OsFunctionsDecodeErrorKindCallback {
    public operator fun invoke(arg0: RawOsError): ErrorKind
}

public fun interface OsFunctionsIsInterruptedCallback {
    public operator fun invoke(arg0: RawOsError): Boolean
}

public fun interface CustomErrorDropCallback {
    public operator fun invoke(arg0: NonNull)
}

public fun interface CustomOuterDropCallback {
    public operator fun invoke(arg0: NonNull)
}

public class DebugStruct(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(name: String, value: Any?): DebugStruct =
        apply {
            fields.add("$name: $value")
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else {
            out.append("$name { ${fields.joinToString(", ")} }")
        }
    }

    public fun finishNonExhaustive() {
        fields.add("..")
        finish()
    }
}

public class DebugTuple(
    private val out: StringBuilder,
    private val name: String,
) {
    private val fields: MutableList<String> = mutableListOf()

    public fun field(value: Any?): DebugTuple =
        apply {
            fields.add(value.toString())
        }

    public fun finish() {
        if (fields.isEmpty()) {
            out.append(name)
        } else if (name.isEmpty()) {
            out.append("(${fields.joinToString(", ")})")
        } else {
            out.append("$name(${fields.joinToString(", ")})")
        }
    }
}

// Note(#74481): Hard-links required to link from `core` to `std`

/**
 * A specialized [Result] type for I/O operations.
 *
 * This type is broadly used across [std.io] for any operation which may
 * produce an error.
 *
 * This type alias is generally used to avoid writing out [io.Error] directly and
 * is otherwise a direct mapping to [Result].
 *
 * While usual Rust style is to import types directly, aliases of [Result]
 * often are not, to make it easier to distinguish between them. [Result] is
 * generally assumed to be [`core.result.Result`][Result], and so users of this alias
 * will generally use `io.Result` instead of shadowing the [prelude]'s import
 * of [`core.result.Result`][Result].
 *
 * [std.io]: ../../std/io/index.html
 * [io.Error]: Error
 *
 * # Examples
 *
 * A convenience function that bubbles an `io.Result` to its caller:
 *
 * ```kotlin
 * import std.io
 *
 * fun getString(): io.Result<String> {
 *     var buffer = ""
 *     io.stdin().readLine(buffer).getOrElse { return Result.failure(it) }
 *     return Result.success(buffer)
 * }
 * ```
 */
public typealias Result<T> = io.github.kotlinmania.rustlang.core.result.Result<T, Error>

// Note(#74481): Hard-links required to link from `core` to `std`

/**
 * The error type for I/O operations of the [`Read`][Read], [`Write`][Write], [`Seek`][Seek], and
 * associated traits.
 *
 * Errors mostly originate from the underlying OS, but custom instances of
 * `Error` can be created with crafted error messages and a particular value of
 * [ErrorKind].
 *
 * [Read]: ../../std/io/trait.Read.html
 */
public open class Error(
    public var repr: Repr,
) {
    public open fun fmt(f: StringBuilder): RustResult<Unit, Unit> = run { f.append((this.repr).toString()); RustResult.Ok<Unit, Unit>(Unit) }

    public open fun intoCustomOwner(): io.github.kotlinmania.rustlang.core.result.Result<CustomOwner, Error> =
        if (when (val matchVal = this.repr.data()) {
            is ErrorData.Custom<*> -> true
            else -> false
        }) {
            val c = this.repr.intoData() ?: // SAFETY: Checked above using `matches`.
unreachableUnchecked()
            RustResult.Ok<CustomOwner, Error>(c)
        } else {
            RustResult.Err<CustomOwner, Error>(this)
        }

    // Note(#74481): Hard-links required to link from `core` to `std` for incoherent method

    /**
     * Returns the OS error that this error represents (if any).
     *
     * If this [Error] was constructed via [`lastOsError`][lastOsError] or
     * [`fromRawOsError`][fromRawOsError], then this function will return [Some], otherwise
     * it will return [None].
     *
     * [lastOsError]: ../../std/io/struct.Error.html#method.lastOsError
     * [fromRawOsError]: ../../std/io/struct.Error.html#method.fromRawOsError
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Error
     * import std.io.ErrorKind
     *
     * fun printOsError(err: Error) {
     *     when (val matchVal = err.rawOsError()) {
     *         null -> {
     *             println("Not an OS error")
     *         }
     *         else -> {
     *             val rawOsErr = matchVal
     *             println("raw OS error: $rawOsErr")
     *         }
     *     }
     * }
     * fun main() {
     *     // Will print "raw OS error: ...".
     *     printOsError(Error.lastOsError())
     *     // Will print "Not an OS error".
     *     printOsError(Error.new(ErrorKind.Other, "oh no!"))
     * }
     * ```
     */
    public open fun rawOsError(): RawOsError? =
        when (val matchVal = this.repr.data()) {
            is ErrorData.Os<*> -> {
                val (i) = matchVal
                i
            }
            is ErrorData.Custom<*> -> null
            is ErrorData.Simple<*> -> null
            is ErrorData.SimpleMessage<*> -> null
        }

    /**
     * Returns a reference to the inner error wrapped by this error (if any).
     *
     * If this [Error] was constructed via [`new`][new] then this function will
     * return [Some], otherwise it will return [None].
     *
     * [new]: ../../alloc/io/struct.Error.html#method.new
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Error
     * import std.io.ErrorKind
     *
     * fun printError(err: Error) {
     *     when (val matchVal = err.getRef()) {
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
    public open fun getRef(): Any? =
        when (val matchVal = this.repr.data()) {
            is ErrorData.Os<*> -> null
            is ErrorData.Simple<*> -> null
            is ErrorData.SimpleMessage<*> -> null
            is ErrorData.Custom<*> -> {
                val (c) = matchVal
                c.errorRef()
            }
        }

    // Note(#74481): Hard-links required to link from `core` to `std`

    /**
     * Returns a mutable reference to the inner error wrapped by this error
     * (if any).
     *
     * If this [Error] was constructed via [`new`][new] then this function will
     * return [Some], otherwise it will return [None].
     *
     * [new]: ../../alloc/io/struct.Error.html#method.new
     *
     * # Examples
     *
     * ```kotlin
     * import std.error
     * import std.fmt
     * import std.io.Error
     * import std.io.ErrorKind
     *
     * open class MyError(public var v: String) {
     *     public open fun new(): MyError {
     *         return MyError(v = "oh no!".toString())
     *     }
     *
     *     public open fun changeMessage(newMessage: String) {
     *         this.v = newMessage.toString()
     *     }
     *
     *     public open fun fmt(f: StringBuilder) {
     *         f.append("MyError: ${this.v}")
     *     }
     * }
     * fun changeError(err: Error): Error {
     *     when (val matchVal = err.getMut()) {
     *         null -> {}
     *         else -> {
     *             val innerErr = matchVal
     *             innerErr.downcastMut<MyError>().unwrap().changeMessage("I've been changed!")
     *         }
     *     }
     *     return err
     * }
     * fun printError(err: Error) {
     *     when (val matchVal = err.getRef()) {
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
     *     printError(changeError(Error.lastOsError()))
     *     // Will print "Inner error: ...".
     *     printError(changeError(Error.new(ErrorKind.Other, MyError.new())))
     * }
     * ```
     */
    public open fun getMut(): Any? =
        when (val matchVal = this.repr.dataMut()) {
            is ErrorData.Os<*> -> null
            is ErrorData.Simple<*> -> null
            is ErrorData.SimpleMessage<*> -> null
            is ErrorData.Custom<*> -> {
                val (c) = matchVal
                c.errorMut()
            }
        }

    // Note(#74481): Hard-links required to link from `core` to `std`

    /**
     * Returns the corresponding [ErrorKind] for this error.
     *
     * This may be a value set by Rust code constructing custom `io.Error`s,
     * or if this `io.Error` was sourced from the operating system,
     * it will be a value inferred from the system's error encoding.
     * See [`lastOsError`][lastOsError] for more details.
     *
     * [lastOsError]: ../../std/io/struct.Error.html#method.lastOsError
     *
     * # Examples
     *
     * ```kotlin
     * import std.io.Error
     * import std.io.ErrorKind
     *
     * fun printError(err: Error) {
     *     println("${err.kind()}")
     * }
     * fun main() {
     *     // As no error has (visibly) occurred, this may print anything!
     *     // It likely prints a Note for unidentified (non-)errors.
     *     printError(Error.lastOsError())
     *     // Will print "AddrInUse".
     *     printError(Error.new(ErrorKind.AddrInUse, "oh no!"))
     * }
     * ```
     */
    public open fun kind(): ErrorKind =
        when (val matchVal = this.repr.data()) {
            is ErrorData.Os<*> -> {
                val (code) = matchVal
                decodeErrorKind(code)
            }
            is ErrorData.Custom<*> -> {
                val (c) = matchVal
                c.kind
            }
            is ErrorData.Simple<*> -> {
                val (kind) = matchVal
                kind
            }
            is ErrorData.SimpleMessage<*> -> {
                val (m) = matchVal
                m.kind
            }
        }

    public open fun isInterrupted(): Boolean =
        when (val matchVal = this.repr.data()) {
            is ErrorData.Os<*> -> {
                val (code) = matchVal
                isInterrupted(code)
            }
            is ErrorData.Custom<*> -> {
                val (c) = matchVal
                c.kind == ErrorKind.Interrupted
            }
            is ErrorData.Simple<*> -> {
                val (kind) = matchVal
                kind == ErrorKind.Interrupted
            }
            is ErrorData.SimpleMessage<*> -> {
                val (m) = matchVal
                m.kind == ErrorKind.Interrupted
            }
        }

    public open fun fmtDisplay(fmt: StringBuilder): RustResult<Unit, Unit> =
        when (val matchVal = this.repr.data()) {
            is ErrorData.Os<*> -> {
                val (code) = matchVal
                val detail = fmt.fromFn({ fmt1 -> formatOsError(code, fmt1) })
                fmt.writeFmt("$detail (os error $code)")
            }
            is ErrorData.Custom<*> -> {
                val (c) = matchVal
                run { fmt.append((c.errorRef()).toString()); RustResult.Ok<Unit, Unit>(Unit) }
            }
            is ErrorData.Simple<*> -> {
                val (kind) = matchVal
                kind.fmt(fmt)
            }
            is ErrorData.SimpleMessage<*> -> {
                val (msg) = matchVal
                msg.message.fmt(fmt)
            }
        }

    public open fun cause(): Any? =
        when (val matchVal = this.repr.data()) {
            is ErrorData.Os<*> -> null
            is ErrorData.Simple<*> -> null
            is ErrorData.SimpleMessage<*> -> null
            is ErrorData.Custom<*> -> {
                val (c) = matchVal
                c.errorRef().cause()
            }
        }

    public open fun source(): Any? =
        when (val matchVal = this.repr.data()) {
            is ErrorData.Os<*> -> null
            is ErrorData.Simple<*> -> null
            is ErrorData.SimpleMessage<*> -> null
            is ErrorData.Custom<*> -> {
                val (c) = matchVal
                c.errorRef().source()
            }
        }

    public companion object {
        /**
         * Common errors constants for use in std
         */
        public val INVALID_UTF8: Error = mustUse(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidData, message = "stream did not contain valid UTF-8")))

        public val READ_EXACT_EOF: Error = mustUse(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.UnexpectedEof, message = "failed to fill whole buffer")))

        public val UNKNOWN_THREAD_COUNT: Error = mustUse(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.NotFound, message = "the number of hardware threads is not known for the target platform")))

        public val UNSUPPORTED_PLATFORM: Error = mustUse(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.Unsupported, message = "operation not supported on this platform")))

        public val WRITE_ALL_EOF: Error = mustUse(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.WriteZero, message = "failed to write whole buffer")))

        public val ZERO_TIMEOUT: Error = mustUse(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "cannot set a 0 duration timeout")))

        public val NO_ADDRESSES: Error = mustUse(Error.fromStaticMessage(SimpleMessage(kind = ErrorKind.InvalidInput, message = "could not resolve to any addresses")))

        /**
         * Intended for use for errors not exposed to the user, where allocating onto
         * the heap (for normal construction via Error.new) is too costly.
         */
        /**
         * Converts an [ErrorKind] into an [Error].
         *
         * This conversion creates a new error with a simple representation of error kind.
         *
         * # Examples
         *
         * ```kotlin
         * import std.io.Error
         * import std.io.ErrorKind
         *
         * val notFound = ErrorKind.NotFound
         * val error = Error.from(notFound)
         * check("entity not found" == "$error")
         * ```
         */
        public fun from(kind: ErrorKind): Error = Error(repr = Repr.newSimple(kind))

        /**
         * # Safety
         *
         * The provided `CustomOwner` must have been constructed from a `Holder` from the `alloc` crate.
         */
        public fun fromCustomOwner(custom: CustomOwner): Error = Error(repr = Repr.newCustom(custom))

        /**
         * Creates a new I/O error from a known kind of error as well as a constant
         * message.
         *
         * This function does not allocate.
         *
         * You should not use this directly, and instead use the `constError`
         * macro: `io.constError(ErrorKind.Something, "someMessage")`.
         *
         * This function should maybe change to `fromStaticMessage<const MSG: &static
         * str>(kind: ErrorKind)` in the future, when const generics allow that.
         */
        public fun fromStaticMessage(msg: SimpleMessage): Error = Error(repr = Repr.newSimpleMessage(msg))

        /**
         * # Safety
         *
         * `functions` must point to data that is entirely constant; it must
         * not be created during runtime.
         */
        public fun fromRawOsErrorWithFunctions(code: RawOsError, functions: OsFunctions): Error {
            // SAFETY: Caller ensures `functions` is a constant not created at runtime.
            run {
                setFunctions(functions)
            }
            return Error(repr = Repr.newOs(code))
        }
    }
}

// Only derive debug in tests, to make sure it
// doesn't accidentally get printed.
public sealed class ErrorData<C> {
    public data class Os<C>(public var value: RawOsError) : ErrorData<C>()
    public data class Simple<C>(public var value: ErrorKind) : ErrorData<C>()
    public data class SimpleMessage<C>(public var value: SimpleMessage) : ErrorData<C>()
    public data class Custom<C>(public var value: C) : ErrorData<C>()
}

// `@repr(align(4))` is probably redundant, it should have that value or
// higher already. We include it just because reprBitpacked.rs's encoding
// requires an alignment >= 4 (note that `@repr(align)` will not reduce the
// alignment required by the struct, only increase it).
//
// If we add more variants to ErrorData, this can be increased to 8, but it
// should probably be behind `@cfgAttr(targetPointerWidth = "64", ...)` or
// whatever cfg we're using to enable the `reprBitpacked` code, since only the
// that version needs the alignment, and 8 is higher than the alignment we'll
// have on 32 bit platforms.
//
// (For the sake of being explicit: the alignment requirement here only matters
// if `error / reprBitpacked.rs` is in use — for the unpacked repr it doesn't
// matter at all)
public open class SimpleMessage(
    public var kind: ErrorKind,
    public var message: String,
)

public fun Repr.fmt(fmt: StringBuilder): RustResult<Unit, Unit> =
    when (val matchVal = this.data()) {
        is ErrorData.Os<*> -> {
            val (code) = matchVal
            run {
DebugStruct(fmt, "Os").field("code", code).field("kind", decodeErrorKind(code)).field("message", fmt.fromFn({ fmt1 ->
                fmt1.writeFmt("\"${fmt.fromFn({ fmt2 -> formatOsError(code, fmt2) })}\"")
            })).finish()
RustResult.Ok<Unit, Unit>(Unit)
}
        }
        is ErrorData.Custom<*> -> {
            val (c) = matchVal
            run { fmt.append((c).toString()); RustResult.Ok<Unit, Unit>(Unit) }
        }
        is ErrorData.Simple<*> -> {
            val (kind) = matchVal
            run { DebugTuple(fmt, "Kind").field(kind).finish(); RustResult.Ok<Unit, Unit>(Unit) }
        }
        is ErrorData.SimpleMessage<*> -> {
            val (msg) = matchVal
            run { DebugStruct(fmt, "Error").field("kind", msg.kind).field("message", msg.message).finish(); RustResult.Ok<Unit, Unit>(Unit) }
        }
    }

public fun assertErrorIsSyncSend() {
    fun <T> isSyncSend() {}
    isSyncSend<Error>()
}

public open class OsFunctions(
    public var formatOsError: OsFunctionsFormatOsErrorCallback,
    public var decodeErrorKind: OsFunctionsDecodeErrorKindCallback,
    public var isInterrupted: OsFunctionsIsInterruptedCallback,
) {
    public companion object {
        public val DEFAULT: OsFunctions = OsFunctions(formatOsError = { RustResult.Ok(Unit) }, decodeErrorKind = { ErrorKind.Uncategorized }, isInterrupted = { false })
    }
}

// As with `SimpleMessage`: `@repr(align(4))` here is just because
// reprBitpacked's encoding requires it. In practice it almost certainly be
// already be this high or higher.

/**
 * A user-created allocated error.
 */
public open class Custom(
    public var kind: ErrorKind,
    // INVARIANT: `error` must be a valid pointer and it must be safe to call `errorDrop`
    // with it once.
    /**
     * `Holder<dynamic Error + ...>` without `alloc`.
     */
    public var error: NonNull,
    public var errorDrop: CustomErrorDropCallback,
    // INVARIANT: must be safe to call once with a pointer to `Self` in `CustomOwner`.
    /**
     * Call to drop a pointer to `Custom`.
     */
    public var outerDrop: CustomOuterDropCallback,
) {
    // SAFETY: All members of `Custom` are `Send`

    // SAFETY: All members of `Custom` are `Sync`

    public open fun fmt(f: StringBuilder): RustResult<Unit, Unit> = run { DebugStruct(f, "Custom").field("kind", this.kind).field("error", this.errorRef()).finish(); RustResult.Ok<Unit, Unit>(Unit) }

    public open fun drop() {
        // SAFETY: by `Custom` invariants, this is a drop call on a valid pointer.
        run {
            (this.errorDrop)(this.error)
        }
    }

    public open fun intoRaw(): NonNull {
        val ptr = this.error
        // Avoid `Custom.drop` which would free the error pointer.
        forget(this)
        return ptr
    }

    public open fun errorRef(): Any {
        // SAFETY:
        // `fromRaw` ensures `error` is a valid pointer up to a static scope
        // and is owned by `self`
        return this.error.asRef()
    }

    public open fun errorMut(): Any {
        // SAFETY:
        // `fromRaw` ensures `error` is a valid pointer up to a static scope
        // and is owned by `self`
        return this.error.asMut()
    }

    public companion object {
        /**
         * # Safety
         *
         * * `error` must be valid for up to a static scope, and own its pointee.
         * * `errorDrop` must be safe to call for the pointer `error` exactly once.
         * * `outerDrop` must be safe to call on a pointer to this instance of `Custom`
         *   (the pointer is likely stored within a [CustomOwner]).
         */
        public fun fromRaw(kind: ErrorKind, error: NonNull, errorDrop: CustomErrorDropCallback, outerDrop: CustomOuterDropCallback): Custom {
            // INVARIANT: function preconditions match type invariants.
            return Custom(kind = kind, error = error, errorDrop = errorDrop, outerDrop = outerDrop)
        }
    }
}

// INVARIANT: `self.0.outerDrop` must be safe to call once with `self.0`.

/**
 * Effectively a `Holder<Custom>` without `alloc`. The `Custom` holds the call to free this pointer.
 */
public open class CustomOwner internal constructor(
    public val fieldStorage: MutPtr,
    public val fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout(),
) {
    public var field0: NonNull
        get() = value
        set(v) {
            value = v
        }

    public var value: NonNull
        get() = fieldLayout.readNative(fieldStorage.rawAddress)
        internal set(value) { fieldLayout.writeNative(fieldStorage.rawAddress, value) }

    internal constructor(value: NonNull, fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout()) : this(allocateNativeCell(value, fieldLayout), fieldLayout)

    public fun pointerLayout(): TypeLayout<CustomOwner> = TransparentTypeLayout(fieldLayout, { ptr, layout -> CustomOwner(ptr, layout) }, { it.value })

    // SAFETY: Custom is `Send`

    // SAFETY: Custom is `Sync`

    public open fun drop() {
        // SAFETY: by `CustomOwner` invariants, this is a drop call on a valid pointer.
        run {
            (this.value.asRef().outerDrop)(this.value)
        }
    }

    public open fun intoRaw(): NonNull {
        val ptr = this.value
        // Avoid `CustomOwner.drop`, which would free the pointer.
        forget(this)
        return ptr
    }

    public open fun customRef(): Custom {
        // SAFETY:
        // `fromRaw` ensures `0` is a valid pointer up to a static scope
        // and is owned by `self`
        return this.value.asRef()
    }

    public open fun customMut(): Custom {
        // SAFETY:
        // `fromRaw` ensures `0` is a valid pointer up to a static scope
        // and is owned by `self`
        return this.value.asMut()
    }

    public companion object {
        public operator fun invoke(value: NonNull, fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout()): CustomOwner = CustomOwner(value, fieldLayout)

        public fun fromFieldStorage(fieldStorage: MutPtr, fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout()): CustomOwner = CustomOwner(fieldStorage, fieldLayout)

        /**
         * # Safety
         *
         * * `custom` must point to valid `Custom`.
         * * The `outerDrop` of the provided `custom` must be the drop function for this pointer.
         */
        public fun fromRaw(custom: NonNull, fieldLayout: TypeLayout<NonNull> = UnresolvedTypeLayout()): CustomOwner {
            // INVARIANT: by the preconditions, `custom.outerDrop` is the drop for `custom`.
            return CustomOwner(custom, fieldLayout = fieldLayout)
        }
    }
}

/**
 * The type of raw OS error codes.
 *
 * This is an [i32] on all currently supported platforms, but platforms
 * added in the future (such as UEFI) may use a different primitive type like
 * [usize] or [i16]. Use `as` or [into] conversions where applicable to
 * ensure maximum portability.
 *
 * [into]: Into.into
 */
public typealias RawOsError = CfgSelect

/**
 * A list specifying general categories of I/O error.
 *
 * This list is intended to grow over time and it is not recommended to
 * exhaustively match against it.
 *
 * It is used with the [`io.Error`][error] type.
 *
 * [error]: Error
 *
 * # Handling errors and matching on `ErrorKind`
 *
 * In application code, use `when` for the `ErrorKind` values you are
 * expecting; use `_` to match "all other errors".
 *
 * In comprehensive and thorough tests that want to verify that a test doesn't
 * return any known incorrect error kind, you may want to cut-and-paste the
 * current full list of errors from here into your test code, and then match
 * `_` as the correct case. This seems counterintuitive, but it will make your
 * tests more robust. In particular, if you want to verify that your code does
 * produce an unrecognized error kind, the robust solution is to check for all
 * the recognized error kinds and fail in those cases.
 */
public sealed class ErrorKind {
    /**
     * An entity was not found, often a file.
     */
    public data object NotFound : ErrorKind()
    /**
     * The operation lacked the necessary privileges to complete.
     */
    public data object PermissionDenied : ErrorKind()
    /**
     * The connection was refused by the remote server.
     */
    public data object ConnectionRefused : ErrorKind()
    /**
     * The connection was reset by the remote server.
     */
    public data object ConnectionReset : ErrorKind()
    /**
     * The remote host is not reachable.
     */
    public data object HostUnreachable : ErrorKind()
    /**
     * The network containing the remote host is not reachable.
     */
    public data object NetworkUnreachable : ErrorKind()
    /**
     * The connection was aborted (terminated) by the remote server.
     */
    public data object ConnectionAborted : ErrorKind()
    /**
     * The network operation failed because it was not connected yet.
     */
    public data object NotConnected : ErrorKind()
    /**
     * A socket address could not be bound because the address is already in
     * use elsewhere.
     */
    public data object AddrInUse : ErrorKind()
    /**
     * A nonexistent interface was requested or the requested address was not
     * local.
     */
    public data object AddrNotAvailable : ErrorKind()
    /**
     * The system's networking is down.
     */
    public data object NetworkDown : ErrorKind()
    /**
     * The operation failed because a pipe was closed.
     */
    public data object BrokenPipe : ErrorKind()
    /**
     * An entity already exists, often a file.
     */
    public data object AlreadyExists : ErrorKind()
    /**
     * The operation needs to block to complete, but the blocking operation was
     * requested to not occur.
     */
    public data object WouldBlock : ErrorKind()
    /**
     * A filesystem object is, unexpectedly, not a directory.
     *
     * For example, a filesystem path was specified where one of the intermediate directory
     * components was, in fact, a plain file.
     */
    public data object NotADirectory : ErrorKind()
    /**
     * The filesystem object is, unexpectedly, a directory.
     *
     * A directory was specified when a non-directory was expected.
     */
    public data object IsADirectory : ErrorKind()
    /**
     * A non-empty directory was specified where an empty directory was expected.
     */
    public data object DirectoryNotEmpty : ErrorKind()
    /**
     * The filesystem or storage medium is read-only, but a write operation was attempted.
     */
    public data object ReadOnlyFilesystem : ErrorKind()
    /**
     * Loop in the filesystem or IO subsystem; often, too many levels of symbolic links.
     *
     * There was a loop (or excessively long chain) resolving a filesystem object
     * or file IO object.
     *
     * On Unix this is usually the result of a symbolic link loop; or, of exceeding the
     * system-specific limit on the depth of symlink traversal.
     */
    public data object FilesystemLoop : ErrorKind()
    /**
     * Stale network file handle.
     *
     * With some network filesystems, notably NFS, an open file (or directory) can be invalidated
     * by problems with the network or server.
     */
    public data object StaleNetworkFileHandle : ErrorKind()
    /**
     * A parameter was incorrect.
     */
    public data object InvalidInput : ErrorKind()
    /**
     * Data not valid for the operation were encountered.
     *
     * Unlike [InvalidInput], this typically means that the operation
     * parameters were valid, however the error was caused by malformed
     * input data.
     *
     * For example, a function that reads a file into a string will error with
     * `InvalidData` if the file's contents are not valid UTF-8.
     *
     * [InvalidInput]: ErrorKind.InvalidInput
     */
    public data object InvalidData : ErrorKind()
    /**
     * The I/O operation's timeout expired, causing it to be canceled.
     */
    public data object TimedOut : ErrorKind()
    /**
     * An error returned when an operation could not be completed because a
     * call to [`write`][write] returned [Ok].
     *
     * This typically means that an operation could only succeed if it wrote a
     * particular number of bytes but only a smaller number of bytes could be
     * written.
     *
     * [Ok]: Ok
     */
    public data object WriteZero : ErrorKind()
    /**
     * The underlying storage (typically, a filesystem) is full.
     *
     * This does not include out of quota errors.
     */
    public data object StorageFull : ErrorKind()
    /**
     * Seek on unseekable file.
     *
     * Seeking was attempted on an open file handle which is not suitable for seeking - for
     * example, on Unix, a named pipe opened with `File.open`.
     */
    public data object NotSeekable : ErrorKind()
    /**
     * Filesystem quota or some other kind of quota was exceeded.
     */
    public data object QuotaExceeded : ErrorKind()
    /**
     * File larger than allowed or supported.
     *
     * This might arise from a hard limit of the underlying filesystem or file access API, or from
     * an administratively imposed resource limitation.  Simple disk full, and out of quota, have
     * their own errors.
     */
    public data object FileTooLarge : ErrorKind()
    /**
     * Resource is busy.
     */
    public data object ResourceBusy : ErrorKind()
    /**
     * Executable file is busy.
     *
     * An attempt was made to write to a file which is also in use as a running program.  (Not all
     * operating systems detect this situation.)
     */
    public data object ExecutableFileBusy : ErrorKind()
    /**
     * Deadlock (avoided).
     *
     * A file locking operation would result in deadlock.  This situation is typically detected, if
     * at all, on a best-effort basis.
     */
    public data object Deadlock : ErrorKind()
    /**
     * Cross-device or cross-filesystem (hard) link or rename.
     */
    public data object CrossesDevices : ErrorKind()
    /**
     * Too many (hard) links to the same filesystem object.
     *
     * The filesystem does not support making so many hardlinks to the same file.
     */
    public data object TooManyLinks : ErrorKind()
    /**
     * A filename was invalid.
     *
     * This error can also occur if a length limit for a name was exceeded.
     */
    public data object InvalidFilename : ErrorKind()
    /**
     * Program argument list too long.
     *
     * When trying to run an external program, a system or process limit on the size of the
     * arguments would have been exceeded.
     */
    public data object ArgumentListTooLong : ErrorKind()
    /**
     * This operation was interrupted.
     *
     * Interrupted operations can typically be retried.
     */
    public data object Interrupted : ErrorKind()
    /**
     * This operation is unsupported on this platform.
     *
     * This means that the operation can never succeed.
     */
    public data object Unsupported : ErrorKind()
    // ErrorKinds which are primarily categorisations for OS error
    // codes should be added above.
    //

    /**
     * An error returned when an operation could not be completed because an
     * "end of file" was reached prematurely.
     *
     * This typically means that an operation could only succeed if it read a
     * particular number of bytes but only a smaller number of bytes could be
     * read.
     */
    public data object UnexpectedEof : ErrorKind()
    /**
     * An operation could not be completed, because it failed
     * to allocate enough memory.
     */
    public data object OutOfMemory : ErrorKind()
    /**
     * The operation was partially successful and needs to be checked
     * later on due to not blocking.
     */
    public data object InProgress : ErrorKind()
    /**
     * The process or the whole system has reached its limit on the number of
     * open files or sockets.
     */
    public data object TooManyOpenFiles : ErrorKind()
    /**
     * A low-level input/output error.
     *
     * This usually indicates a hardware or device-level failure, such as a bad
     * disk sector or a removed device, but the operating system may also report
     * it for other low-level I/O conditions.
     */
    public data object InputOutputError : ErrorKind()
    // "Unusual" error kinds which do not correspond simply to (sets
    // of) OS error codes, should be added just above this comment.
    // `Other` and `Uncategorized` should remain at the end:
    //

    /**
     * A custom error that does not fall under any other I/O error kind.
     *
     * This can be used to construct your own [`Error`][error]s that do not match any
     * [ErrorKind].
     *
     * This [ErrorKind] is not used by the standard library.
     *
     * Errors from the standard library that do not fall under any of the I/O
     * error kinds cannot be `when`ed on, and will only match a wildcard (`_`) pattern.
     * New [ErrorKind]s might be added in the future for some of those.
     *
     * [error]: Error
     */
    public data object Other : ErrorKind()
    /**
     * Any I/O error from the standard library that's not part of this list.
     *
     * Errors that are `Uncategorized` now may move to a different or a new
     * [ErrorKind] variant in the future. It is not recommended to match
     * an error against `Uncategorized`; use a wildcard match (`_`) instead.
     */
    public data object Uncategorized : ErrorKind()

    public fun rustEquals(other: ErrorKind): Boolean = this == other

    public open fun asStr(): String =
        when (this) {
            // tidy-alphabetical-start
            AddrInUse -> "address in use"
            AddrNotAvailable -> "address not available"
            AlreadyExists -> "entity already exists"
            ArgumentListTooLong -> "argument list too long"
            BrokenPipe -> "broken pipe"
            ConnectionAborted -> "connection aborted"
            ConnectionRefused -> "connection refused"
            ConnectionReset -> "connection reset"
            CrossesDevices -> "cross-device link or rename"
            Deadlock -> "deadlock"
            DirectoryNotEmpty -> "directory not empty"
            ExecutableFileBusy -> "executable file busy"
            FileTooLarge -> "file too large"
            FilesystemLoop -> "filesystem loop or indirection limit (e.g. symlink loop)"
            HostUnreachable -> "host unreachable"
            InProgress -> "in progress"
            InputOutputError -> "input/output error"
            Interrupted -> "operation interrupted"
            InvalidData -> "invalid data"
            InvalidFilename -> "invalid filename"
            InvalidInput -> "invalid input parameter"
            IsADirectory -> "is a directory"
            NetworkDown -> "network down"
            NetworkUnreachable -> "network unreachable"
            NotADirectory -> "not a directory"
            NotConnected -> "not connected"
            NotFound -> "entity not found"
            NotSeekable -> "seek on unseekable file"
            Other -> "other error"
            OutOfMemory -> "out of memory"
            PermissionDenied -> "permission denied"
            QuotaExceeded -> "quota exceeded"
            ReadOnlyFilesystem -> "read-only filesystem or storage medium"
            ResourceBusy -> "resource busy"
            StaleNetworkFileHandle -> "stale network file handle"
            StorageFull -> "no storage space"
            TimedOut -> "timed out"
            TooManyLinks -> "too many links"
            TooManyOpenFiles -> "too many open files"
            Uncategorized -> "uncategorized error"
            UnexpectedEof -> "unexpected end of file"
            Unsupported -> "unsupported"
            WouldBlock -> "operation would block"
            WriteZero -> "write zero"
        }

    /**
     * Shows a human-readable description of the [ErrorKind].
     *
     * This is similar to `implementation Display for Error`, but doesn't require first converting to Error.
     *
     * # Examples
     *
     * ```kotlin
     * import core.io.ErrorKind
     *
     * check("entity not found" == ErrorKind.NotFound.toString())
     * ```
     */
    public open fun fmt(fmt: StringBuilder): RustResult<Unit, Unit> = run { fmt.append(this.asStr()); RustResult.Ok<Unit, Unit>(Unit) }

    public companion object {
        // This compiles to the same code as the check+reinterpret, but doesn't require
        // unchecked, or to hard-code max ErrorKind or its size in a way the compiler
        // couldn't verify.
        public fun fromPrim(ek: UInt): ErrorKind? =
            run {
                when {
                    ek == (ErrorKind.NotFound) -> {
                        val v = ek
                        ErrorKind.NotFound
                    }
                    ek == (ErrorKind.PermissionDenied) -> {
                        val v1 = ek
                        ErrorKind.PermissionDenied
                    }
                    ek == (ErrorKind.ConnectionRefused) -> {
                        val v2 = ek
                        ErrorKind.ConnectionRefused
                    }
                    ek == (ErrorKind.ConnectionReset) -> {
                        val v3 = ek
                        ErrorKind.ConnectionReset
                    }
                    ek == (ErrorKind.HostUnreachable) -> {
                        val v4 = ek
                        ErrorKind.HostUnreachable
                    }
                    ek == (ErrorKind.NetworkUnreachable) -> {
                        val v5 = ek
                        ErrorKind.NetworkUnreachable
                    }
                    ek == (ErrorKind.ConnectionAborted) -> {
                        val v6 = ek
                        ErrorKind.ConnectionAborted
                    }
                    ek == (ErrorKind.NotConnected) -> {
                        val v7 = ek
                        ErrorKind.NotConnected
                    }
                    ek == (ErrorKind.AddrInUse) -> {
                        val v8 = ek
                        ErrorKind.AddrInUse
                    }
                    ek == (ErrorKind.AddrNotAvailable) -> {
                        val v9 = ek
                        ErrorKind.AddrNotAvailable
                    }
                    ek == (ErrorKind.NetworkDown) -> {
                        val v10 = ek
                        ErrorKind.NetworkDown
                    }
                    ek == (ErrorKind.BrokenPipe) -> {
                        val v11 = ek
                        ErrorKind.BrokenPipe
                    }
                    ek == (ErrorKind.AlreadyExists) -> {
                        val v12 = ek
                        ErrorKind.AlreadyExists
                    }
                    ek == (ErrorKind.WouldBlock) -> {
                        val v13 = ek
                        ErrorKind.WouldBlock
                    }
                    ek == (ErrorKind.NotADirectory) -> {
                        val v14 = ek
                        ErrorKind.NotADirectory
                    }
                    ek == (ErrorKind.IsADirectory) -> {
                        val v15 = ek
                        ErrorKind.IsADirectory
                    }
                    ek == (ErrorKind.DirectoryNotEmpty) -> {
                        val v16 = ek
                        ErrorKind.DirectoryNotEmpty
                    }
                    ek == (ErrorKind.ReadOnlyFilesystem) -> {
                        val v17 = ek
                        ErrorKind.ReadOnlyFilesystem
                    }
                    ek == (ErrorKind.FilesystemLoop) -> {
                        val v18 = ek
                        ErrorKind.FilesystemLoop
                    }
                    ek == (ErrorKind.StaleNetworkFileHandle) -> {
                        val v19 = ek
                        ErrorKind.StaleNetworkFileHandle
                    }
                    ek == (ErrorKind.InvalidInput) -> {
                        val v20 = ek
                        ErrorKind.InvalidInput
                    }
                    ek == (ErrorKind.InvalidData) -> {
                        val v21 = ek
                        ErrorKind.InvalidData
                    }
                    ek == (ErrorKind.TimedOut) -> {
                        val v22 = ek
                        ErrorKind.TimedOut
                    }
                    ek == (ErrorKind.WriteZero) -> {
                        val v23 = ek
                        ErrorKind.WriteZero
                    }
                    ek == (ErrorKind.StorageFull) -> {
                        val v24 = ek
                        ErrorKind.StorageFull
                    }
                    ek == (ErrorKind.NotSeekable) -> {
                        val v25 = ek
                        ErrorKind.NotSeekable
                    }
                    ek == (ErrorKind.QuotaExceeded) -> {
                        val v26 = ek
                        ErrorKind.QuotaExceeded
                    }
                    ek == (ErrorKind.FileTooLarge) -> {
                        val v27 = ek
                        ErrorKind.FileTooLarge
                    }
                    ek == (ErrorKind.ResourceBusy) -> {
                        val v28 = ek
                        ErrorKind.ResourceBusy
                    }
                    ek == (ErrorKind.ExecutableFileBusy) -> {
                        val v29 = ek
                        ErrorKind.ExecutableFileBusy
                    }
                    ek == (ErrorKind.Deadlock) -> {
                        val v30 = ek
                        ErrorKind.Deadlock
                    }
                    ek == (ErrorKind.CrossesDevices) -> {
                        val v31 = ek
                        ErrorKind.CrossesDevices
                    }
                    ek == (ErrorKind.TooManyLinks) -> {
                        val v32 = ek
                        ErrorKind.TooManyLinks
                    }
                    ek == (ErrorKind.InvalidFilename) -> {
                        val v33 = ek
                        ErrorKind.InvalidFilename
                    }
                    ek == (ErrorKind.ArgumentListTooLong) -> {
                        val v34 = ek
                        ErrorKind.ArgumentListTooLong
                    }
                    ek == (ErrorKind.Interrupted) -> {
                        val v35 = ek
                        ErrorKind.Interrupted
                    }
                    ek == (ErrorKind.Other) -> {
                        val v36 = ek
                        ErrorKind.Other
                    }
                    ek == (ErrorKind.UnexpectedEof) -> {
                        val v37 = ek
                        ErrorKind.UnexpectedEof
                    }
                    ek == (ErrorKind.Unsupported) -> {
                        val v38 = ek
                        ErrorKind.Unsupported
                    }
                    ek == (ErrorKind.OutOfMemory) -> {
                        val v39 = ek
                        ErrorKind.OutOfMemory
                    }
                    ek == (ErrorKind.InProgress) -> {
                        val v40 = ek
                        ErrorKind.InProgress
                    }
                    ek == (ErrorKind.TooManyOpenFiles) -> {
                        val v41 = ek
                        ErrorKind.TooManyOpenFiles
                    }
                    ek == (ErrorKind.InputOutputError) -> {
                        val v42 = ek
                        ErrorKind.InputOutputError
                    }
                    ek == (ErrorKind.Uncategorized) -> {
                        val v43 = ek
                        ErrorKind.Uncategorized
                    }
                    else -> null
                }
            }
    }
}
