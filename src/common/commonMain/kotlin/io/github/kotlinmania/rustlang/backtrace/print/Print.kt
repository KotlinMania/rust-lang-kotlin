// port-lint: source print.rs
package io.github.kotlinmania.rustlang.backtrace.print

import io.github.kotlinmania.rustlang.backtrace.backtrace.Frame
import io.github.kotlinmania.rustlang.backtrace.capture.BacktraceFrame
import io.github.kotlinmania.rustlang.backtrace.capture.BacktraceSymbol
import io.github.kotlinmania.rustlang.backtrace.symbolize.Symbol
import io.github.kotlinmania.rustlang.backtrace.symbolize.SymbolName
import io.github.kotlinmania.rustlang.backtrace.types.BytesOrWideString
import io.github.kotlinmania.rustlang.core.fmt.Error
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen

public val HEX_WIDTH: Int = 2 + 2 * sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)

/**
 * A formatter for backtraces.
 *
 * This type can be used to print a backtrace regardless of where the backtrace
 * itself comes from. If you have a `Backtrace` type then its `Debug`
 * implementation already uses this printing format.
 */
public open class BacktraceFmt internal constructor(
    public var fmt: StringBuilder,
    public var frameIndex: Int,
    public var format: PrintFmt,
    printPathCtorArg: (StringBuilder, BytesOrWideString) -> Result<Unit, Error>,
) {
    public var printPath: (StringBuilder, BytesOrWideString) -> Result<Unit, Error> = printPathCtorArg
        internal set

    /**
     * Prints a preamble for the backtrace about to be printed.
     *
     * This is required on some platforms for backtraces to be fully
     * symbolicated later, and otherwise this should just be the first method
     * you call after creating a `BacktraceFmt`.
     */
    public open fun addContext(): Result<Unit, Error> = Result.Ok<Unit, Error>(Unit)

    /**
     * Adds a frame to the backtrace output.
     *
     * This commit returns an RAII instance of a `BacktraceFrameFmt` which can be used
     * to actually print a frame, and on destruction it will increment the
     * frame counter.
     */
    public open fun frame(): BacktraceFrameFmt = BacktraceFrameFmt(fmt = this, symbolIndex = 0)

    /**
     * Completes the backtrace output.
     *
     * This is currently a no-op but is added for future compatibility with
     * backtrace formats.
     */
    public open fun finish(): Result<Unit, Error> = Result.Ok<Unit, Error>(Unit)

    /**
     * Inserts a message in the backtrace output.
     *
     * This allows information to be inserted between frames,
     * and won't increment the `frameIndex` unlike the `frame`
     * method.
     */
    public open fun message(msg: String): Result<Unit, Error> = run { this.fmt.append(msg); Result.Ok<Unit, Error>(Unit) }

    /**
     * Return the inner formatter.
     *
     * This is used for writing custom information between frames with `write` and `writeln`,
     * and won't increment the `frameIndex` unlike the `frame` method.
     */
    public open fun formatter(): StringBuilder = this.fmt

    public companion object {
        public operator fun invoke(
            fmt: StringBuilder,
            frameIndex: Int,
            format: PrintFmt,
            printPath: (StringBuilder, BytesOrWideString) -> Result<Unit, Error>,
        ): BacktraceFmt = BacktraceFmt(
            fmt = fmt,
            frameIndex = frameIndex,
            format = format,
            printPathCtorArg = printPath,
        )

        /**
         * Create a new `BacktraceFmt` which will write output to the provided
         * `fmt`.
         *
         * The `format` argument will control the style in which the backtrace is
         * printed, and the `printPath` argument will be used to print the
         * `BytesOrWideString` instances of filenames. This type itself doesn't do
         * any printing of filenames, but this callback is required to do so.
         */
        public fun new(fmt: StringBuilder, format: PrintFmt, printPath: (StringBuilder, BytesOrWideString) -> Result<Unit, Error>): BacktraceFmt = BacktraceFmt(fmt = fmt, frameIndex = 0, format = format, printPath = printPath)
    }
}

/**
 * The styles of printing that we can print
 */
public sealed class PrintFmt {
    /**
     * Prints a terser backtrace which ideally only contains relevant information
     */
    public data object Short : PrintFmt()
    /**
     * Prints a backtrace that contains all possible information
     */
    public data object Full : PrintFmt()

    public fun rustEquals(other: PrintFmt): Boolean = this == other
}

/**
 * A formatter for just one frame of a backtrace.
 *
 * This type is created by the `BacktraceFmt.frame` function.
 */
public open class BacktraceFrameFmt(
    public var fmt: BacktraceFmt,
    public var symbolIndex: Int,
) {
    /**
     * Prints a `BacktraceFrame` with this frame formatter.
     *
     * This will recursively print all `BacktraceSymbol` instances within the
     * `BacktraceFrame`.
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun backtraceFrame(frame: BacktraceFrame): Result<Unit, Error> {
        val symbols = frame.symbols()
        for (symbol in symbols) {
            when (val tryResult = this.backtraceSymbol(frame, symbol)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        }
        if (symbols.isEmpty()) {
            when (val tryResult = this.printRaw(frame.ip(), null, null, null)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    /**
     * Prints a `BacktraceSymbol` within a `BacktraceFrame`.
     *
     * # Required features
     *
     * This function requires the `std` feature of the `backtrace` crate to be
     * enabled, and the `std` feature is enabled by default.
     */
    public open fun backtraceSymbol(frame: BacktraceFrame, symbol: BacktraceSymbol): Result<Unit, Error> {
        when (val tryResult = this.printRawWithColumn(frame.ip(), symbol.name(), symbol.filename().andThen(closure@{ p -> BytesOrWideString.Bytes((when (val tryResult = p.toStr()) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return@closure Result.Err(tryResult.errValue()) }).encodeToByteArray().map { it.toUByte() }) }), symbol.lineno(), symbol.colno())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        return Result.Ok<Unit, Error>(Unit)
    }

    /**
     * Prints a raw traced `Frame` and `Symbol`, typically from within the raw
     * callbacks of this crate.
     */
    public open fun symbol(frame: Frame, symbol: Symbol): Result<Unit, Error> {
        when (val tryResult = this.printRawWithColumn(frame.ip(), symbol.name(), symbol.filenameRaw(), symbol.lineno(), symbol.colno())) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        return Result.Ok<Unit, Error>(Unit)
    }

    /**
     * Adds a raw frame to the backtrace output.
     *
     * This method, unlike the previous, takes the raw arguments in case
     * they're being source from different locations. Note that this may be
     * called multiple times for one frame.
     */
    public open fun printRaw(frameIp: MutPtr, symbolName: SymbolName?, filename: BytesOrWideString?, lineno: UInt?): Result<Unit, Error> = this.printRawWithColumn(frameIp, symbolName, filename, lineno, null)

    /**
     * Adds a raw frame to the backtrace output, including column information.
     *
     * This method, like the previous, takes the raw arguments in case
     * they're being source from different locations. Note that this may be
     * called multiple times for one frame.
     */
    public open fun printRawWithColumn(frameIp: MutPtr, symbolName: SymbolName?, filename: BytesOrWideString?, lineno: UInt?, colno: UInt?): Result<Unit, Error> {
        // Fuchsia is unable to symbolize within a process so it has a special
        // format which can be used to symbolize later. Print that instead of
        // printing addresses in our own format here.
        if (false) {
            when (val tryResult = this.printRawFuchsia(frameIp)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        } else {
            when (val tryResult = this.printRawGeneric(frameIp, symbolName, filename, lineno, colno)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        }
        this.symbolIndex += 1
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun printRawGeneric(frameIp: MutPtr, symbolName: SymbolName?, filename: BytesOrWideString?, lineno: UInt?, colno: UInt?): Result<Unit, Error> {
        // No need to print "null" frames, it basically just means that the
        // system backtrace was a bit eager to trace back super far.
        if (this.fmt.format == PrintFmt.Short) {
            if (((frameIp).rawAddress == 0L)) {
                return Result.Ok<Unit, Error>(Unit)
            }
        }
        // Print the index of the frame as well as the optional instruction
        // pointer of the frame. If we're beyond the first symbol of this frame
        // though we just print appropriate whitespace.
        if (this.symbolIndex == 0) {
            this.fmt.fmt.append("${this.fmt.frameIndex}: ")
            if (this.fmt.format == PrintFmt.Full) {
                this.fmt.fmt.append("$frameIp - ")
            }
        } else {
            this.fmt.fmt.append("      ")
            if (this.fmt.format == PrintFmt.Full) {
                this.fmt.fmt.append(("").toString())
            }
        }
        // Next up write out the symbol name, using the alternate formatting for
        // more information if we're a full backtrace. Here we also handle
        // symbols which don't have a name,
        run {
            val matchVal = Pair(symbolName, this.fmt.format)
            when {
                matchVal.first != null && matchVal.second == PrintFmt.Short -> {
                    val name = matchVal.first
                    this.fmt.fmt.append("$name")
                }
                matchVal.first != null && matchVal.second == PrintFmt.Full -> {
                    val name1 = matchVal.first
                    this.fmt.fmt.append("$name")
                }
                else -> this.fmt.fmt.append("<unknown>")
            }
        }
        this.fmt.fmt.append("\n")
        // And last up, print out the filename/line number if they're available.
        if (Pair(filename, lineno) != null) {
            val file = Pair(filename, lineno).first
            val line = Pair(filename, lineno).second
            when (val tryResult = this.printFileline(file, line, colno)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun printFileline(file: BytesOrWideString, line: UInt, colno: UInt?): Result<Unit, Error> {
        // Filename/line are printed on lines under the symbol name, so print
        // some appropriate whitespace to sort of right-align ourselves.
        if (this.fmt.format == PrintFmt.Full) {
            this.fmt.fmt.append(("").toString())
        }
        this.fmt.fmt.append("             at ")
        // Delegate to our internal callback to print the filename and then
        // print out the line number.
        when (val tryResult = (this.fmt.printPath)(this.fmt.fmt, file)) { is Result.Ok<*, *> -> tryResult.okValue(); is Result.Err<*, *> -> return Result.Err<Unit, Error>(tryResult.errValue()) }
        this.fmt.fmt.append(":$line")
        // Add column number, if available.
        if (colno != null) {
            this.fmt.fmt.append(":$colno")
        }
        this.fmt.fmt.appendLine()
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun printRawFuchsia(frameIp: MutPtr): Result<Unit, Error> {
        // We only care about the first symbol of a frame
        if (this.symbolIndex == 0) {
            this.fmt.fmt.append("{{{bt:")
            this.fmt.fmt.append("${this.fmt.frameIndex}:$frameIp")
            this.fmt.fmt.append("}}}\n")
        }
        return Result.Ok<Unit, Error>(Unit)
    }

    public open fun drop() {
        this.fmt.frameIndex += 1
    }
}
