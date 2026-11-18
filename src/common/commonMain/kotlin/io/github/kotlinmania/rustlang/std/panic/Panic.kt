// port-lint: source panic.rs
package io.github.kotlinmania.rustlang.std.panic

import io.github.kotlinmania.rustlang.core.RustAny
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.panic.location.Location
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU8
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.core.sync.atomic.compareExchange
import io.github.kotlinmania.rustlang.core.sync.atomic.load
import io.github.kotlinmania.rustlang.core.sync.atomic.store
import io.github.kotlinmania.rustlang.std.env.varOs
import io.github.kotlinmania.rustlang.std.panicking.beginPanic
import io.github.kotlinmania.rustlang.std.panicking.paniccount.setAlwaysAbort
import io.github.kotlinmania.rustlang.std.sys.backtrace.FULL_BACKTRACE_DEFAULT
import io.github.kotlinmania.rustlang.std.thread.Result
import io.github.kotlinmania.rustlang.core.result.Result as RustResult

// Panic support in the standard library.

/**
 * A struct providing information about a panic.
 *
 * `PanicInfo` has been renamed to [PanicHookInfo] to avoid confusion with
 * [core.panic.PanicInfo].
 */
public typealias PanicInfo = PanicHookInfo

/**
 * A struct providing information about a panic.
 *
 * `PanicHookInfo` structure is passed to a panic hook set by the [setHook] function.
 *
 * # Examples
 *
 * ```kotlin
 * import std.panic
 *
 * panic.setHook({ panicInfo ->
 *     println("panic occurred: $panicInfo")
 * })
 * error("critical system failure")
 * ```
 *
 * [setHook]: ../../std/panic/fn.setHook.html
 */
public open class PanicHookInfo(
    public var payload: RustAny,
    public var location: Location,
    public var canUnwind: Boolean,
    public var forceNoBacktrace: Boolean,
) {
    /**
     * Returns the payload associated with the panic.
     *
     * This will commonly, but not always, be a `String` or [String].
     * If you only care about such payloads, use [payloadAsStr] instead.
     *
     * A invocation of the `panic()` macro in Rust 2021 or later will always result in a
     * panic payload of type `String` or `String`.
     *
     * Only an invocation of [panicAny]
     * (or, in Rust 2018 and earlier, `panic(x)` where `x` is something other than a string)
     * can result in a panic payload other than a `String` or `String`.
     *
     * [String]: ../../std/string/struct.String.html
     * [payloadAsStr]: PanicHookInfo.payloadAsStr
     *
     * # Examples
     *
     * ```kotlin
     * import std.panic
     *
     * panic.setHook({ panicInfo ->
     *     when (val matchVal1 = panicInfo.payload().downcastRef<String>()) {
     *         null -> {
     *             when (val matchVal = panicInfo.payload().downcastRef<String>()) {
     *                 null -> {
     *                     println("panic occurred")
     *                 }
     *                 else -> {
     *                     val s = matchVal
     *                     println("panic occurred: $s")
     *                 }
     *             }
     *         }
     *         else -> {
     *             val s1 = matchVal1
     *             println("panic occurred: $s")
     *         }
     *     }
     * })
     * error("Normal panic")
     * ```
     */
    public open fun payload(): RustAny = this.payload

    /**
     * Returns the payload associated with the panic, if it is a string.
     *
     * This returns the payload if it is of type `String` or `String`.
     *
     * A invocation of the `panic()` macro in Rust 2021 or later will always result in a
     * panic payload where `payloadAsStr` returns `Some`.
     *
     * Only an invocation of [panicAny]
     * (or, in Rust 2018 and earlier, `panic(x)` where `x` is something other than a string)
     * can result in a panic payload where `payloadAsStr` returns `None`.
     *
     * # Example
     *
     * ```kotlin
     * setHook({ panicInfo ->
     *     when (val matchVal = panicInfo.payloadAsStr()) {
     *         null -> {
     *             println("panic occurred")
     *         }
     *         else -> {
     *             val s = matchVal
     *             println("panic occurred: $s")
     *         }
     *     }
     * })
     * error("Normal panic")
     * ```
     */
    public open fun payloadAsStr(): String? =
        when (val matchVal1 = this.payload.downcastRef<String>()) {
            null -> {
                when (val matchVal = this.payload.downcastRef<String>()) {
                    null -> {
                        null
                    }
                    else -> {
                        val s = matchVal
                        s
                    }
                }
            }
            else -> {
                val s1 = matchVal1
                s1
            }
        }

    /**
     * Returns information about the location from which the panic originated,
     * if available.
     *
     * This method will currently always return [Some], but this may change
     * in future versions.
     *
     * # Examples
     *
     * ```kotlin
     * import std.panic
     *
     * panic.setHook({ panicInfo ->
     *     when (val matchVal = panicInfo.location()) {
     *         null -> {
     *             println("panicked at unknown location")
     *         }
     *         else -> {
     *             val location = matchVal
     *             println("panic occurred in file '${location.file()}' at line ${location.line()}")
     *         }
     *     }
     * })
     * error("Normal panic")
     * ```
     */
    public open fun location(): Location? {
        // NOTE: If this is changed to sometimes return None,
        // deal with that case in std.panicking.defaultHook and std.panicking.panicWithHook.
        return this.location
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

    public open fun fmt(formatter: StringBuilder): RustResult<Unit, Error> {
        formatter.append("panicked at ")
        when (val tryResult = this.location.fmt(formatter)) { is RustResult.Ok<*, *> -> tryResult.okValue(); is RustResult.Err<*, *> -> return RustResult.Err<Unit, Error>(tryResult.errValue()) }
        when (val matchVal = this.payloadAsStr()) {
            null -> {}
            else -> {
                val payload = matchVal
                formatter.append(":\n")
                formatter.append(payload)
            }
        }
        return RustResult.Ok<Unit, Error>(Unit)
    }

    public companion object {
        public fun new(location: Location, payload: RustAny, canUnwind: Boolean, forceNoBacktrace: Boolean): PanicHookInfo = PanicHookInfo(payload = payload, location = location, canUnwind = canUnwind, forceNoBacktrace = forceNoBacktrace)
    }
}

/**
 * Panics the current thread with the given message as the panic payload.
 *
 * The message can be of any (`Any + Send`) type, not just strings.
 *
 * The message is wrapped in a `Holder<Any + Send>`, which can be
 * accessed later using [PanicHookInfo.payload].
 *
 * See the [panic] macro for more information about panicking.
 */
public fun <M> panicAny(msg: M): Nothing {
    beginPanic(msg)
}

// https://github.com/rust-lang/rust/issues/62301

/**
 * Invokes a closure, capturing the cause of an unwinding panic if one occurs.
 *
 * This function will return `Ok` with the closure's result if the closure does
 * not panic, and will return `Err(cause)` if the closure panics. The `cause`
 * returned is the object with which panic was originally invoked.
 *
 * Rust functions that are expected to be called from foreign code that does
 * not support unwinding (such as C compiled with `- fno - exceptions`) should be
 * defined using `extern "C"`, which ensures that if the Rust code panics, it
 * is automatically caught and the process is aborted. If this is the desired
 * behavior, it is not necessary to use `catchUnwind` explicitly. This
 * function should instead be used when more graceful error-handling is needed.
 *
 * It is **not** recommended to use this function for a general try/catch
 * mechanism. The [Result] type is more appropriate to use for functions that
 * can fail on a regular basis. Additionally, this function is not guaranteed
 * to catch all panics, see the "Notes" section below.
 *
 * The closure provided is required to adhere to the [UnwindSafe] trait to
 * ensure that all captured variables are safe to cross this boundary. The
 * purpose of this bound is to encode the concept of [exception safety][rfc] in
 * the type system. Most usage of this function should not need to worry about
 * this bound as programs are naturally unwind safe without `unchecked` code. If
 * it becomes a problem the [AssertUnwindSafe] wrapper struct can be used to
 * quickly assert that the usage here is indeed unwind safe.
 *
 * [rfc]: https://rust-lang.github.io/rfcs/1236-stabilize-catch-panic.html
 *
 * # Notes
 *
 * This function **might not catch all Rust panics**. A Rust panic is not
 * always implemented via unwinding, but can be implemented by aborting the
 * process as well. This function *only* catches unwinding panics, not those
 * that abort the process.
 *
 * If a custom panic hook has been set, it will be invoked before the panic is
 * caught, before unwinding.
 *
 * Although unwinding into Rust code with a foreign exception (e.g. an
 * exception thrown from C++ code, or a `panic` in Rust code compiled or
 * linked with a different runtime) via an appropriate ABI (e.g. `"C-unwind"`)
 * is permitted, catching such an exception using this function will have one
 * of two behaviors, and it is unspecified which will occur:
 *
 * * The process aborts, after executing all destructors of `f` and the
 *   functions it called.
 * * The function returns a `Result.Err` containing an opaque type.
 *
 * Finally, be **careful in how you drop the result of this function**. If it
 * is `Err`, it contains the panic payload, and dropping that may in turn
 * panic
 *
 * # Examples
 *
 * ```kotlin
 * import std.panic
 *
 * val result = panic.catchUnwind({
 *     println("hello!")
 * })
 * check(result.isOk())
 * val result1 = panic.catchUnwind({
 *     error("oh no!")
 * })
 * check(result1.isErr())
 * ```
 */
public fun <R> catchUnwind(f: () -> R): Result<R> = io.github.kotlinmania.rustlang.std.panicking.catchUnwind(f)

/**
 * Triggers a panic without invoking the panic hook.
 *
 * This is designed to be used in conjunction with [catchUnwind] to, for
 * example, carry a panic across a layer of C code.
 *
 * # Notes
 *
 * Note that panics in Rust are not always implemented via unwinding, but they
 * may be implemented by aborting the process. If this function is called when
 * panics are implemented this way then this function will abort the process,
 * not trigger an unwind.
 *
 * # Examples
 *
 * ```kotlin
 * import std.panic
 *
 * val result = panic.catchUnwind({
 *     if (1 != 2) {
 *         error("oh no!")
 *     }
 * })
 * if (result is Err) {
 *     val (err) = result
 *     panic.resumeUnwind(err)
 * }
 * ```
 */
public fun resumeUnwind(payload: RustAny): Nothing = io.github.kotlinmania.rustlang.std.panicking.resumeUnwind(payload)

/**
 * Makes all future panics abort directly without running the panic hook or unwinding.
 *
 * There is no way to undo this; the effect lasts until the process exits or
 * execs (or the equivalent).
 *
 * # Use after fork
 *
 * This function is particularly useful for calling after `libc.fork`.  After `fork`, in a
 * multithreaded program it is (on many platforms) not safe to call the allocator.  It is also
 * generally highly undesirable for an unwind to unwind past the `fork`, because that results in
 * the unwind propagating to code that was only ever expecting to run in the parent.
 *
 * `panic.alwaysAbort()` helps avoid both of these.  It directly avoids any further unwinding,
 * and if there is a panic, the abort will occur without allocating provided that the arguments to
 * panic can be formatted without allocating.
 *
 * Examples
 *
 * ```kotlin
 * import std.panic
 *
 * @feature(panicAlwaysAbort)
 * public fun doctestEntry() {
 *     panic.alwaysAbort()
 *     panic.catchUnwind({
 *         error("inside the catch")
 *     })
 *     // We will have aborted already, due to the panic.
 *     error("unreachable")
 * }
 * ```
 */
public fun alwaysAbort() {
    setAlwaysAbort()
}

/**
 * The configuration for whether and how the default panic hook will capture
 * and display the backtrace.
 */
public sealed class BacktraceStyle {
    /**
     * Prints a terser backtrace which ideally only contains relevant
     * information.
     */
    public data object Short : BacktraceStyle()
    /**
     * Prints a backtrace with all possible information.
     */
    public data object Full : BacktraceStyle()
    /**
     * Disable collecting and displaying backtraces.
     */
    public data object Off : BacktraceStyle()

    public fun rustEquals(other: BacktraceStyle): Boolean = this == other

    private fun kmDiscriminant(): Int =
        when (this) {
        Short -> 0
        Full -> 1
        Off -> 2
        }

    public fun toByte(): Byte = kmDiscriminant().toByte()

    public fun toUByte(): UByte = kmDiscriminant().toUByte()

    public fun toShort(): Short = kmDiscriminant().toShort()

    public fun toUShort(): UShort = kmDiscriminant().toUShort()

    public fun toInt(): Int = kmDiscriminant()

    public fun toUInt(): UInt = kmDiscriminant().toUInt()

    public fun toLong(): Long = kmDiscriminant().toLong()

    public fun toULong(): ULong = kmDiscriminant().toULong()

    public open fun asU8(): UByte =
        when (this) {
            BacktraceStyle.Short -> 1u.toUByte()
            BacktraceStyle.Full -> 2u.toUByte()
            BacktraceStyle.Off -> 3u.toUByte()
        }

    public companion object {
        public fun full(): BacktraceStyle? =
            if (false) {
                BacktraceStyle.Full
            } else {
                null
            }

        public fun fromU8(s: UByte): BacktraceStyle? =
            when (s) {
                1u.toUByte() -> BacktraceStyle.Short
                2u.toUByte() -> BacktraceStyle.Full
                3u.toUByte() -> BacktraceStyle.Off
                else -> null
            }
    }
}

// Tracks whether we should/can capture a backtrace, and how we should display
// that backtrace.
//
// Internally stores equivalent of an Option<BacktraceStyle>.
public var SHOULD_CAPTURE: AtomicU8 = AtomicU8.new(0u.toUByte())

/**
 * Configures whether the default panic hook will capture and display a
 * backtrace.
 *
 * The default value for this setting may be set by the `RUST_BACKTRACE`
 * environment variable; see the details in [getBacktraceStyle].
 */
public fun setBacktraceStyle(style: BacktraceStyle) {
    if (false) {
        // If the `backtrace` feature of this crate is enabled, set the backtrace style.
        SHOULD_CAPTURE.store(style.asU8(), Ordering.Relaxed)
    }
}

/**
 * Checks whether the standard library's panic hook will capture and print a
 * backtrace.
 *
 * This function will, if a backtrace style has not been set via
 * [setBacktraceStyle], read the environment variable `RUST_BACKTRACE` to
 * determine a default value for the backtrace formatting:
 *
 * The first call to `getBacktraceStyle` may read the `RUST_BACKTRACE`
 * environment variable if `setBacktraceStyle` has not been called to
 * override the default value. After a call to `setBacktraceStyle` or
 * `getBacktraceStyle`, any changes to `RUST_BACKTRACE` will have no effect.
 *
 * `RUST_BACKTRACE` is read according to these rules:
 *
 * * `0` for `BacktraceStyle.Off`
 * * `full` for `BacktraceStyle.Full`
 * * `1` for `BacktraceStyle.Short`
 * * Other values are currently `BacktraceStyle.Short`, but this may change in
 *   the future
 *
 * Returns `None` if backtraces aren't currently supported.
 */
public fun getBacktraceStyle(): BacktraceStyle? {
    if (!false) {
        // If the `backtrace` feature of this crate isn't enabled quickly return
        // `Unsupported` so this can be constant propagated all over the place
        // to optimize away callers.
        return null
    }
    val current = SHOULD_CAPTURE.load(Ordering.Relaxed)
    when (val matchVal = BacktraceStyle.fromU8(current)) {
        null -> {}
        else -> {
            val style = matchVal
            return style
        }
    }
    val format =
        run {
            val matchVal1 = varOs("RUST_BACKTRACE")
            when {
                matchVal1 != null && matchVal1 == "0" -> {
                    val x = matchVal1
                    BacktraceStyle.Off
                }
                matchVal1 != null && matchVal1 == "full" -> {
                    val x1 = matchVal1
                    BacktraceStyle.Full
                }
                matchVal1 != null -> BacktraceStyle.Short
                matchVal1 == null && FULL_BACKTRACE_DEFAULT -> BacktraceStyle.Full
                else -> BacktraceStyle.Off
            }
        }
    return run {
        val matchVal2 = SHOULD_CAPTURE.compareExchange(0u.toUByte(), format.asU8(), Ordering.Relaxed, Ordering.Relaxed)
        when (matchVal2) { is RustResult.Ok<*, *> -> {
            format
        }; is RustResult.Err<*, *> -> {
            val new = matchVal2.errValue()
            BacktraceStyle.fromU8(new)
        } }
    }
}
