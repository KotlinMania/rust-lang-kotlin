// port-lint: source sys/backtrace.rs
package io.github.kotlinmania.rustlang.std.sys.backtrace

import io.github.kotlinmania.rustlang.backtrace.print.BacktraceFmt
import io.github.kotlinmania.rustlang.backtrace.print.PrintFmt
import io.github.kotlinmania.rustlang.backtrace.types.BytesOrWideString
import io.github.kotlinmania.rustlang.core.io.error.Error
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.os.unix.ffi.osstr.fromBytes
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.MutexGuard
import io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.threadinfo.LOCK

// Common code for printing backtraces.

/**
 * Max number of frames to print.
 */
public val MAX_NB_FRAMES: Int = 100

public val FULL_BACKTRACE_DEFAULT: Boolean = false

public open class BacktraceLock internal constructor(
    valueCtorArg: MutexGuard<Unit>,
) {
    public var value: MutexGuard<Unit> = valueCtorArg
        internal set

    public var field0: MutexGuard<Unit>
        get() = value
        set(v) {
            value = v
        }

    /**
     * Prints the current backtrace.
     */
    public open fun print(w: Write, format: PrintFmt): Result<Unit, Error> {
        open class DisplayBacktrace(
            public var format: PrintFmt,
        ) {
            public open fun fmt(fmt: StringBuilder): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
                // SAFETY: the backtrace lock is held
                return printFmt(fmt, this.format)
            }
        }
        // There are issues currently linking libbacktrace into tests, and in
        // general during std's own unit tests we're not testing this path. In
        // test mode immediately return here to optimize away any references to the
        // libbacktrace symbols
        if (false) {
            return Result.Ok<Unit, Error>(Unit)
        }
        return run { w.append("${DisplayBacktrace(format = format)}"); Result.Ok<Unit, Error>(Unit) }
    }

    public companion object {
        public operator fun invoke(
            value: MutexGuard<Unit>,
        ): BacktraceLock = BacktraceLock(
            valueCtorArg = value,
        )
    }
}

public fun lock(): BacktraceLock {
    val LOCK: Mutex<Unit>  = Mutex.new(Unit)
    return BacktraceLock(LOCK.lock().unwrapOrElse({ it.intoInner() }))
}

/**
 * # Safety
 *
 * This function is not Sync. The caller must hold a mutex lock, or there must be only one thread in the program.
 */
public fun printFmt(fmt: StringBuilder, printFmt: PrintFmt): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
    // Always `fail` to get the cwd when running under Miri -
    // this allows Miri to display backtraces in isolation mode
    val cwd =
        if (!false) {
            env.currentDir().ok()
        } else {
            null
        }
    var printPath =
        { fmt1: StringBuilder, bows: BytesOrWideString ->
            outputFilename(fmt1, bows, printFmt, cwd.asRef())
        }
    fmt.appendLine("stack backtrace:")
    val btFmt = BacktraceFmt.new(fmt, printFmt, printPath)
    when (val tryResult = btFmt.addContext()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(tryResult.error) }
    var idx = 0
    var res = Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit)
    var omittedCount: Int = 0
    var firstOmit = true
    // If we're using a short backtrace, ignore all frames until we're told to start printing.
    var print = printFmt != PrintFmt.Short
    setImageBase()
    // SAFETY: we roll our own locking in this town
    traceUnsynchronized(closure@{ frame ->
        if (printFmt == PrintFmt.Short && idx > MAX_NB_FRAMES) {
            return@closure false
        }
        if (false) {
            val frameIp = frame.ip()
            res = btFmt.formatter().appendLine("$idx: $frameIp")
        } else {
            var hit = false
            resolveFrameUnsynchronized(frame, closure1@{ symbol ->
                hit = true
                // `__rust_end_short_backtrace` means we are done hiding symbols
                // for now. Print until we see `__rust_begin_short_backtrace`.
                if (printFmt == PrintFmt.Short) {
                    when (val matchVal = symbol.name().andThen({ s -> s })) {
                        null -> {}
                        else -> {
                            val sym = matchVal
                            if (sym.contains("__rust_end_short_backtrace")) {
                                print = true
                                return@closure1
                            }
                            if (print && sym.contains("__rust_begin_short_backtrace")) {
                                print = false
                                return@closure1
                            }
                            if (!print) {
                                omittedCount += 1
                            }
                        }
                    }
                }
                if (print) {
                    if (omittedCount > 0) {
                        check(printFmt == PrintFmt.Short)
                        // only print the message between the middle of frames
                        if (!firstOmit) {
                            btFmt.formatter().appendLine("      [... omitted " + (omittedCount).toString() + " frame" + (if (omittedCount > 1) {
                                "s"
                            } else {
                                ""
                            }).toString() + " ...]")
                        }
                        firstOmit = false
                        omittedCount = 0
                    }
                    res = btFmt.frame().symbol(frame, symbol)
                }
            })
            if (!hit && print) {
                res = btFmt.frame().printRaw(frame.ip(), null, null, null)
            }
        }
        idx += 1
        res.isOk()
    })
    when (val tryResult = res) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(tryResult.error) }
    when (val tryResult = btFmt.finish()) { is Result.Ok<*, *> -> tryResult.value; is Result.Err<*, *> -> return Result.Err<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(tryResult.error) }
    if (printFmt == PrintFmt.Short) {
        fmt.appendLine("note: Some details are omitted, run with `RUST_BACKTRACE=full` for a verbose backtrace.")
    }
    return Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit)
}

/**
 * Fixed frame used to clean the backtrace with `RUST_BACKTRACE = 1`. Note that
 * this is only inline(never) when backtraces in std are enabled, otherwise
 * it's fine to optimize away.
 */
public fun <T> rustBeginShortBacktrace(f: () -> T): T {
    val result = f()
    // prevent this frame from being tail-call optimised away
    return result
}

/**
 * Fixed frame used to clean the backtrace with `RUST_BACKTRACE = 1`. Note that
 * this is only inline(never) when backtraces in std are enabled, otherwise
 * it's fine to optimize away.
 */
public fun <T> rustEndShortBacktrace(f: () -> T): T {
    val result = f()
    // prevent this frame from being tail-call optimised away
    return result
}

/**
 * Prints the filename of the backtrace frame.
 *
 * See also `output`.
 */
public fun outputFilename(fmt: StringBuilder, bows: BytesOrWideString, printFmt: PrintFmt, cwd: PathBuf?): Result<Unit, io.github.kotlinmania.rustlang.core.fmt.Error> {
    val file: Path =
        when (bows) {
            is BytesOrWideString.Bytes -> {
                val (bytes) = bows
                Path.new(OsStr.fromBytes(bytes)).into()
            }
            is BytesOrWideString.Wide -> {
                val (wide) = bows
                OsString.fromWide(wide).into()
            }
        }
    if (printFmt == PrintFmt.Short && file.isAbsolute()) {
        if (cwd != null) {
            val cwd1 = cwd
            when (val matchVal = file.stripPrefix(cwd1)) {
                is Result.Ok<*, *> -> {
                    val stripped = matchVal.value
                    when (val matchVal1 = stripped.toStr()) {
                        null -> {}
                        else -> {
                            val s = matchVal1
                            return run { fmt.append(".${path.MAIN_SEPARATOR}$s"); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
                        }
                    }
                }
                else -> {}
            }
        }
    }
    return run { fmt.append((file.display()).toString()); Result.Ok<Unit, io.github.kotlinmania.rustlang.core.fmt.Error>(Unit) }
}

public fun setImageBase() {
    // nothing to do for platforms other than SGX
}
