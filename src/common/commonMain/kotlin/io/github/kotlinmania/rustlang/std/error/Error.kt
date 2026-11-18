// port-lint: source error.rs
package io.github.kotlinmania.rustlang.std.error

import io.github.kotlinmania.rustlang.core.cmp.max
import io.github.kotlinmania.rustlang.core.error.Error
import io.github.kotlinmania.rustlang.core.error.requestRef
import io.github.kotlinmania.rustlang.core.fmt.Write
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.alignUpBytes
import io.github.kotlinmania.rustlang.core.ptr.writeBytes
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.orElse
import io.github.kotlinmania.rustlang.std.backtrace.Backtrace

/**
 * An error reporter that prints an error and its sources.
 *
 * Report also exposes configuration options for formatting the error sources, either entirely on a
 * single line, or in multi-line format with each source on a new line.
 *
 * `Report` only requires that the wrapped error implement `Error`. It doesn't require that the
 * wrapped error be `Send`, `Sync`, or `static`.
 *
 * # Examples
 *
 * ```kotlin
 * import std.error.Error
 * import std.error.Report
 * import std.fmt
 *
 * @feature(errorReporter)
 * public fun doctestEntry() {
 *     open class SuperErrorSideKick {
 *         public open fun fmt(f: StringBuilder) {
 *             f.append("SuperErrorSideKick is here!")
 *         }
 *     }
 *     open class SuperError(public var source: SuperErrorSideKick) {
 *         public open fun fmt(f: StringBuilder) {
 *             f.append("SuperError is here!")
 *         }
 *
 *         public open fun source(): Any? {
 *             return this.source
 *         }
 *     }
 *     fun getSuperError(): Result<Unit> {
 *         return Result.failure(SuperError(source = SuperErrorSideKick()))
 *     }
 *     fun main() {
 *         when (val matchVal = getSuperError()) {
 *             is Err -> {
 *                 val (e) = matchVal
 *                 println("Error: ${Report.new(e)}")
 *             }
 *             else -> println("No error")
 *         }
 *     }
 * }
 * ```
 *
 * This example produces the following output:
 *
 * ```console
 * Error: SuperError is here!: SuperErrorSideKick is here!
 * ```
 *
 * ## Output consistency
 *
 * Report prints the same output via `Display` and `Debug`, so it works well with
 * [Result.unwrap]/[Result.expect] which print their `Err` variant via `Debug`:
 *
 * ```kotlin
 * import std.error.Report
 *
 * @feature(errorReporter)
 * public fun doctestEntry() {
 *     getSuperError().mapErr(Report.new).unwrap()
 * }
 * ```
 *
 * This example produces the following output:
 *
 * ```console
 * thread `main` panicked at src/error.rs:34:40:
 * called `Result.unwrap()` on an `Err` value: SuperError is here!: SuperErrorSideKick is here!
 * note: run with `RUST_BACKTRACE=1` environment variable to display a backtrace
 * ```
 *
 * ## Return from `main`
 *
 * `Report` also implements `From` for all types that implement [Error]; this when combined with
 * the `Debug` output means `Report` is an ideal starting place for formatting errors returned
 * from `main`.
 *
 * ```kotlin
 * import std.error.Report
 *
 * @feature(errorReporter)
 * public fun doctestEntry(): Result<Unit> {
 *     fun main(): Result<Unit> {
 *         getSuperError().getOrElse { return Result.failure(it) }
 *         return Result.success(Unit)
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * This example produces the following output:
 *
 * ```console
 * Error: SuperError is here!: SuperErrorSideKick is here!
 * ```
 *
 * **Note**: `Report`s constructed via `?` and `From` will be configured to use the single line
 * output format. If you want to make sure your `Report`s are pretty printed and include backtrace
 * you will need to manually convert and enable those flags.
 *
 * ```kotlin
 * import std.error.Report
 *
 * @feature(errorReporter)
 * public fun doctestEntry(): Result<Unit> {
 *     fun main(): Result<Unit> {
 *         getSuperError().mapErr(Report.from).mapErr({ r -> r.pretty(true).showBacktrace(true) }).getOrElse { return Result.failure(it) }
 *         return Result.success(Unit)
 *     }
 *     return Result.success(Unit)
 * }
 * ```
 *
 * This example produces the following output:
 *
 * ```console
 * Error: SuperError is here!
 *
 * Caused by:
 *       SuperErrorSideKick is here!
 * ```
 */
public open class Report<E>(
    /**
     * The error being reported.
     */
    public var error: E,
    /**
     * Whether a backtrace should be included as part of the report.
     */
    public var showBacktrace: Boolean,
    /**
     * Whether the report should be pretty-printed.
     */
    public var pretty: Boolean,
) {
    /**
     * Enable pretty-printing the report across multiple lines.
     *
     * # Examples
     *
     * ```kotlin
     * import std.error.Report
     *
     * @feature(errorReporter)
     * public fun doctestEntry() {
     *     val error = SuperError(source = SuperErrorSideKick)
     *     val report = Report.new(error).pretty(true)
     *     println("Error: $report")
     * }
     * ```
     *
     * This example produces the following output:
     *
     * ```console
     * Error: SuperError is here!
     *
     * Caused by:
     *       SuperErrorSideKick is here!
     * ```
     *
     * When there are multiple source errors the causes will be numbered in order of iteration
     * starting from the outermost error.
     *
     * ```kotlin
     * import std.error.Report
     *
     * @feature(errorReporter)
     * public fun doctestEntry() {
     *     val source = SuperErrorSideKickSideKick
     *     val source1 = SuperErrorSideKick(source = source)
     *     val error = SuperError(source = source1)
     *     val report = Report.new(error).pretty(true)
     *     println("Error: $report")
     * }
     * ```
     *
     * This example produces the following output:
     *
     * ```console
     * Error: SuperError is here!
     *
     * Caused by:
     *    0: SuperErrorSideKick is here!
     *    1: SuperErrorSideKickSideKick is here!
     * ```
     */
    public open fun pretty(pretty: Boolean): Report<E> {
        this.pretty = pretty
        return this
    }

    /**
     * Display backtrace if available when using pretty output format.
     *
     * # Examples
     *
     * **Note**: Report will search for the first `Backtrace` it can find starting from the
     * outermost error. In this example it will display the backtrace from the second error in the
     * sources, `SuperErrorSideKick`.
     *
     * ```kotlin
     * import std.backtrace.Backtrace
     * import std.error.Report
     * import std.error.Request
     *
     * @feature(errorReporter)
     * @feature(errorGenericMemberAccess)
     * public fun doctestEntry() {
     *     open class SuperErrorSideKick(public var backtrace: Backtrace) {
     *         public open fun new(): SuperErrorSideKick {
     *             return SuperErrorSideKick(backtrace = Backtrace.forceCapture())
     *         }
     *
     *         public open fun provide(request: Request) {
     *             request.provideRef<Backtrace>(this.backtrace)
     *         }
     *     }
     *     // The rest of the example is unchanged ...
     *     val source = SuperErrorSideKick.new()
     *     val error = SuperError(source = source)
     *     val report = Report.new(error).pretty(true).showBacktrace(true)
     *     println("Error: $report")
     * }
     * ```
     *
     * This example produces something similar to the following output:
     *
     * ```console
     * Error: SuperError is here!
     *
     * Caused by:
     *       SuperErrorSideKick is here!
     *
     * Stack backtrace:
     *    0: rustOut.main.doctestMainSrcErrorRs11580.SuperErrorSideKick.new
     *    1: rustOut.main.doctestMainSrcErrorRs11580
     *    2: rustOut.main
     *    3: core.ops.function.FnOnce.callOnce
     *    4: std.sys.backtrace.rustBeginShortBacktrace
     *    5: std.rt.langStart::{{closure}}
     *    6: std.panicking.tryBlock
     *    7: std.rt.langStartInternal
     *    8: std.rt.langStart
     *    9: main
     *   10: __libc_start_main
     *   11: _start
     * ```
     */
    public open fun showBacktrace(showBacktrace: Boolean): Report<E> {
        this.showBacktrace = showBacktrace
        return this
    }

    public open fun backtrace(): Backtrace? {
        // have to grab the backtrace on the first error directly since that error may not be
        // static
        val backtrace = requestRef(this.error)
        return backtrace.orElse({
            this.error.source().map({ source -> source.sources().findMap({ source1 -> requestRef(source1) }) }).flatten()
        })
    }

    /**
     * Format the report as a single line.
     */
    public open fun fmtSingleline(f: StringBuilder): Result<Unit, Error> {
        f.append("${this.error}")
        val sources = this.error.source().intoIter().flatMap(Any.sources)
        for (cause in sources) {
            f.append(": $cause")
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    /**
     * Format the report as multiple lines, with each error cause on its own line.
     */
    public open fun fmtMultiline(f: StringBuilder): Result<Unit, Error> {
        val error = this.error
        f.append("$error")
        when (val matchVal = error.source()) {
            null -> {}
            else -> {
                val cause = matchVal
                f.append("\n\nCaused by:")
                val multiple = cause.source() != null
                for ((ind, error1) in cause.sources().mapIndexed { index, item -> Pair(index, item) }) {
                    f.appendLine()
                    val indented = Indented(inner = f)
                    if (multiple) {
                        indented.append("$ind: $error")
                    } else {
                        indented.append("      $error")
                    }
                }
            }
        }
        if (this.showBacktrace) {
            when (val matchVal1 = this.backtrace()) {
                null -> {}
                else -> {
                    val backtrace = matchVal1
                    f.append("\n\nStack backtrace:\n${backtrace.toString().trimEnd()}")
                }
            }
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun fmt(f: StringBuilder): Result<Unit, Error> =
        if (this.pretty) {
            this.fmtMultiline(f)
        } else {
            this.fmtSingleline(f)
        }

    // This type intentionally outputs the same format for `Display` and `Debug`for
    // situations where you unwrap a `Report` or return it from main.
    public open fun fmtDebug(f: StringBuilder): Result<Unit, Error> = run { f.append((this).toString()); Result.Ok<Unit, Error>(Unit) }

    public companion object {
        public fun <E> typeLayout(typeLayoutE: TypeLayout<E>): TypeLayout<Report<E>> =
            object : TypeLayout<Report<E>> {
                override val alignBytes: Long = listOf(1L, typeLayoutE.alignBytes).max()
                private val off1: Long = alignUpBytes(typeLayoutE.sizeBytes, 1L)
                private val off2: Long = alignUpBytes((off1 + 1L), 1L)
                override val sizeBytes: Long = alignUpBytes((off2 + 1L), alignBytes)
                override fun readNative(addr: Long): Report<E> =
                    Report(
                        (typeLayoutE).readNative(addr),
                        (PrimitiveLayouts.BOOL).readNative(addr + off1),
                        (PrimitiveLayouts.BOOL).readNative(addr + off2),
                    )
                override fun writeNative(addr: Long, value: Report<E>) {
                    (typeLayoutE).writeNative(addr, value.error)
                    (PrimitiveLayouts.BOOL).writeNative(addr + off1, value.showBacktrace)
                    (PrimitiveLayouts.BOOL).writeNative(addr + off2, value.pretty)
                }
                override fun readBytes(buf: ByteArray, offset: Int): Report<E> =
                    Report(
                        (typeLayoutE).readBytes(buf, offset),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + off1.toInt()),
                        (PrimitiveLayouts.BOOL).readBytes(buf, offset + off2.toInt()),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: Report<E>) {
                    (typeLayoutE).writeBytes(buf, offset, value.error)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + off1.toInt(), value.showBacktrace)
                    (PrimitiveLayouts.BOOL).writeBytes(buf, offset + off2.toInt(), value.pretty)
                }
            }

        /**
         * Creates a new `Report` from an input error.
         */
        public fun <E> new(error: E): Report<E> = Report.from(error)

        public fun <E> from(error: E): Report<E> = Report(error = error, showBacktrace = false, pretty = false)
    }
}

/**
 * Wrapper type for indenting the inner source.
 */
public open class Indented<D>(
    public var inner: D,
)

public fun <T> Indented<T>.writeStr(s: String): Result<Unit, Error> where T : Write<T> {
    for ((i, line) in s.split('\n').mapIndexed { index, item -> Pair(index, item) }) {
        if (i > 0) {
            this.inner.append('\n')
            this.inner.append("      ")
        }
        this.inner.append(line)
    }
    return Result.Ok<Unit, Error>(Unit)
}
