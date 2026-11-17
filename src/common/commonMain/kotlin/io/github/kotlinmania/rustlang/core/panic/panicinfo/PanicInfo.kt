// port-lint: source panic/panic_info.rs
package io.github.kotlinmania.rustlang.core.panic.panicinfo

import io.github.kotlinmania.rustlang.core.panic.location.Location
import io.github.kotlinmania.rustlang.core.result.Result

/**
 * A struct providing information about a panic.
 *
 * A `PanicInfo` structure is passed to the panic handler defined by `@panicHandler`.
 *
 * For the type used by the panic hook mechanism in `std`, see [std.panic.PanicHookInfo].
 *
 * [std.panic.PanicHookInfo]: ../../std/panic/struct.PanicHookInfo.html
 */
public open class PanicInfo(
    public var message: fmt.Arguments,
    public var location: Location,
    public var canUnwind: Boolean,
    public var forceNoBacktrace: Boolean,
) {
    /**
     * The message that was given to the `panic` macro.
     *
     * # Example
     *
     * The type returned by this method implements `Display`, so it can
     * be passed directly to [write] and similar macros.
     *
     * ```ignore (no_std)
     * @panicHandler
     * fn panicHandler(panicInfo: &PanicInfo<_>) -> ! {
     *     write(DEBUG_OUTPUT, "panicked: {}", panicInfo.message());
     *     loop {}
     * }
     * ```
     */
    public open fun message(): PanicMessage = PanicMessage(message = this.message)

    /**
     * Returns information about the location from which the panic originated,
     * if available.
     *
     * This method will currently always return [Some], but this may change
     * in future versions.
     *
     * # Example
     *
     * ```ignore (no_std)
     * @panicHandler
     * fn panicHandler(panicInfo: &PanicInfo<_>) -> ! {
     *     if let Some(location) = panicInfo.location() {
     *         write(DEBUG_OUTPUT, "panicked at {}", location);
     *     } else {
     *         write(DEBUG_OUTPUT, "panicked at unknown location");
     *     }
     *     loop {}
     * }
     * ```
     */
    public open fun location(): Location? {
        // NOTE: If this is changed to sometimes return None,
        // deal with that case in std.panicking.panicHandler and core.panicking.panicFmt.
        return this.location
    }

    /**
     * Returns the payload associated with the panic.
     *
     * On this type, `core.panic.PanicInfo`, this method never returns anything useful.
     * It only exists because of compatibility with [std.panic.PanicHookInfo],
     * which used to be the same type.
     *
     * See [std.panic.PanicHookInfo.payload].
     *
     * [std.panic.PanicHookInfo]: ../../std/panic/struct.PanicHookInfo.html
     * [std.panic.PanicHookInfo.payload]: ../../std/panic/struct.PanicHookInfo.html#method.payload
     */
    public open fun payload(): io.github.kotlinmania.rustlang.core.any.Any {
        open class NoPayload
        return NoPayload()
    }

    /**
     * Returns whether the panic handler is allowed to unwind the stack from
     * the point where the panic occurred.
     *
     * This is true for most kinds of panics with the exception of panics
     * caused by trying to unwind out of a `Drop` implementation or a function
     * whose ABI does not support unwinding.
     *
     * It is safe for a panic handler to unwind even when this function returns
     * false, however this will simply cause the panic handler to be called
     * again.
     */
    public open fun canUnwind(): Boolean = this.canUnwind

    public open fun forceNoBacktrace(): Boolean = this.forceNoBacktrace

    public open fun fmt(formatter: StringBuilder): Result<Unit, Unit> {
        formatter.append("panicked at ")
        when (val tryResult = this.location.fmt(formatter)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        formatter.append(":\n")
        when (val tryResult = formatter.writeFmt(this.message)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Unit>(tryResult.errValue()) }
        return Result.Ok<Unit, Unit>(Unit)
    }

    public companion object {
        public fun new(message: fmt.Arguments, location: Location, canUnwind: Boolean, forceNoBacktrace: Boolean): PanicInfo = PanicInfo(location = location, message = message, canUnwind = canUnwind, forceNoBacktrace = forceNoBacktrace)
    }
}

/**
 * A message that was given to the `panic()` macro.
 *
 * The [Display] implementation of this type will format the message with the arguments
 * that were given to the `panic()` macro.
 *
 * See [PanicInfo.message].
 */
public open class PanicMessage(
    public var message: fmt.Arguments,
) {
    /**
     * Gets the formatted message, if it has no arguments to be formatted at runtime.
     *
     * This can be used to avoid allocations in some cases.
     *
     * # Guarantees
     *
     * For `panic("just a literal")`, this function is guaranteed to
     * return `Some("just a literal")`.
     *
     * For most cases with placeholders, this function will return `None`.
     *
     * See [fmt.Arguments.asStr] for details.
     */
    public open fun asStr(): String? = this.message.asStr()

    public open fun fmt(formatter: StringBuilder): Result<Unit, Unit> = formatter.writeFmt(this.message)

    public open fun fmtDebug(formatter: StringBuilder): Result<Unit, Unit> = formatter.writeFmt(this.message)
}
