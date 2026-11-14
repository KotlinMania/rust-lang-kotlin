// port-lint: source sys/backtrace.rs
package io.github.kotlinmania.rustlang.std.sys.backtrace

import io.github.kotlinmania.backtracers.resolveFrameUnsynchronized
import io.github.kotlinmania.backtracers.traceUnsynchronized
import io.github.kotlinmania.libc.myThreadExit
import io.github.kotlinmania.rustlang.core.mem.mutPtrFromRustCast
import io.github.kotlinmania.rustlang.core.ptr.withoutProvenanceMut
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.result.andThen
import io.github.kotlinmania.rustlang.core.result.unwrapOrElse
import io.github.kotlinmania.rustlang.std.backtracers.BacktraceFmt
import io.github.kotlinmania.rustlang.std.backtracers.BytesOrWideString
import io.github.kotlinmania.rustlang.std.backtracers.PrintFmt
import io.github.kotlinmania.rustlang.std.backtracers.SymbolName
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsStr
import io.github.kotlinmania.rustlang.std.ffi.osstr.OsString
import io.github.kotlinmania.rustlang.std.fmt
import io.github.kotlinmania.rustlang.std.path.Path
import io.github.kotlinmania.rustlang.std.path.PathBuf
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.Mutex
import io.github.kotlinmania.rustlang.std.sync.nonpoison.mutex.MutexGuard
import io.github.kotlinmania.rustlang.std.sync.poison.PoisonError
import io.github.kotlinmania.rustlang.std.sys.env.unix.env
import io.github.kotlinmania.rustlang.std.sys.pal.sgx.abi.mem.imageBase
import io.github.kotlinmania.rustlang.std.sys.pal.unix.stackoverflow.threadinfo.LOCK

// Common code for printing backtraces.

/**
 * Max number of frames to print.
 */
public val MAX_NB_FRAMES: Int = 100

internal val FULL_BACKTRACE_DEFAULT: Boolean = cfgSelect(targetOs, "fuchsia", true, false)

internal open class BacktraceLock(
    public var value: MutexGuard<Unit>,
) {
    public var field0: MutexGuard<Unit>
        get() = value
        set(v) {
            value = v
        }

    /**
     * Prints the current backtrace.
     */
    internal open fun print(w: Write, format: PrintFmt): Result<Unit> {
        open class DisplayBacktrace(
            public var format: PrintFmt,
        ) {
            public open fun fmt(fmt: StringBuilder) {
                // SAFETY: the backtrace lock is held
                printFmt(fmt, this.format)
            }
        }
        // There are issues currently linking libbacktrace into tests, and in
        // general during std's own unit tests we're not testing this path. In
        // test mode immediately return here to optimize away any references to the
        // libbacktrace symbols
        if (false) {
            return Result.Ok(Unit)
        }
        return run { w.append("${DisplayBacktrace(format = format)}"); Result.Ok(Unit) }
    }
}

internal fun lock(): BacktraceLock {
    val LOCK: Mutex<Unit>  = Mutex.new()
    return BacktraceLock(LOCK.lock().unwrapOrElse(PoisonError::intoInner))
}

/**
 * # Safety
 *
 * This function is not Sync. The caller must hold a mutex lock, or there must be only one thread in the program.
 */
public fun printFmt(fmt: StringBuilder, printFmt: PrintFmt) {
    // Always 'fail' to get the cwd when running under Miri -
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
    btFmt.addContext()
    var idx = 0
    var res = Unit
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
            if (((mutPtrFromRustCast(myThreadExit)).rawAddress.toULong() == (frame.ip()).rawAddress.toULong())) {
                if (!hit && print) {
                    res = btFmt.frame().printRaw(frame.ip(), SymbolName.new("__my_thread_exit".encodeToByteArray().map { it.toUByte() }), null, null)
                }
                return@closure false
            }
            if (!hit && print) {
                res = btFmt.frame().printRaw(frame.ip(), null, null, null)
            }
        }
        idx += 1
        res.isOk()
    })
    res
    btFmt.finish()
    if (printFmt == PrintFmt.Short) {
        fmt.appendLine("note: Some details are omitted, run with `RUST_BACKTRACE=full` for a verbose backtrace.")
    }
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
public fun outputFilename(fmt: StringBuilder, bows: BytesOrWideString, printFmt: PrintFmt, cwd: PathBuf?) {
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
                is Ok -> {
                    val stripped = matchVal.value
                    when (val matchVal1 = stripped.toStr()) {
                        null -> {}
                        else -> {
                            val s = matchVal1
                            return fmt.append(".${path.MAIN_SEPARATOR}$s")
                        }
                    }
                }
                else -> {}
            }
        }
    }
    fmt.append((file.display()).toString())
}

public fun setImageBase() {
    val imageBase = imageBase()
    io.github.kotlinmania.backtracers.setImageBase(withoutProvenanceMut(((imageBase as Any?) as Unit)))
}
