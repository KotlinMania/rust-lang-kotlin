// port-lint: source sys/pal/itron/error.rs
package io.github.kotlinmania.rustlang.std.sys.pal.itron.error

import io.github.kotlinmania.rustlang.core.io.error.ErrorKind
import io.github.kotlinmania.rustlang.core.io.write.Write
import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.std.process.abort
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.ER
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_BOVR
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_CLS
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_COMM
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_CTX
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_DLT
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_ID
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_ILUSE
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_MACV
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_NOEXS
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_NOID
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_NOMEM
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_NORES
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_NOSPT
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_OACV
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_OBJ
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_PAR
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_QOVR
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_RASTER
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_RLWAI
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_RSATR
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_RSFN
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_SYS
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_TMOUT
import io.github.kotlinmania.rustlang.std.sys.pal.itron.abi.E_WBLK
import io.github.kotlinmania.rustlang.std.sys.stdio.unix.panicOutput
import io.github.kotlinmania.rustlang.std.thread.functions.panicking

/**
 * Wraps a μITRON error code.
 */
public open class ItronError(
    public var er: ER,
) {
    public open fun clone(): ItronError = ItronError(er)

    /**
     * Gets the raw error code.
     */
    public open fun asRaw(): ER = this.er

    public open fun fmt(f: StringBuilder) {
        // Allow the platforms to extend `errorName`
        when (val matchVal = errorName(this.er)) {
            null -> {
                f.append("${this.er}")
            }
            else -> {
                val name = matchVal
                f.append("$name (${this.er})")
            }
        }
    }

    public companion object {
        public fun typeLayout(): TypeLayout<ItronError> =
            object : TypeLayout<ItronError> {
                override val alignBytes: Long = 4L
                override val sizeBytes: Long = 4L
                override fun readNative(addr: Long): ItronError =
                    ItronError(
                        (PrimitiveLayouts.I32).readNative(addr),
                    )
                override fun writeNative(addr: Long, value: ItronError) {
                    (PrimitiveLayouts.I32).writeNative(addr, value.er)
                }
                override fun readBytes(buf: ByteArray, offset: Int): ItronError =
                    ItronError(
                        (PrimitiveLayouts.I32).readBytes(buf, offset),
                    )
                override fun writeBytes(buf: ByteArray, offset: Int, value: ItronError) {
                    (PrimitiveLayouts.I32).writeBytes(buf, offset, value.er)
                }
            }

        /**
         * Constructs `ItronError` from the specified error code. Returns `None` if the
         * error code does not represent a failure or warning.
         */
        public fun new(er: ER): ItronError? =
            if (er < 0) {
                ItronError(er = er)
            } else {
                null
            }

        /**
         * Returns `Ok(er)` if `er` represents a success or `Err(_)` otherwise.
         */
        public fun errIfNegative(er: ER): Result<ER, ItronError> =
            when (val matchVal = ItronError.new(er)) {
                null -> {
                    Result.Ok(er)
                }
                else -> {
                    val error = matchVal
                    Result.Err(error)
                }
            }
    }
}

/**
 * Describe the specified μITRON error code. Returns `None` if it's an
 * undefined error code.
 */
public fun errorName(er: ER): String? =
    when {
        // Success
        er >= 0 -> {
            val er1 = er
            null
        }
        // μITRON 4.0
        er == E_SYS -> "system error"
        er == E_NOSPT -> "unsupported function"
        er == E_RSFN -> "reserved function code"
        er == E_RSATR -> "reserved attribute"
        er == E_PAR -> "parameter error"
        er == E_ID -> "invalid ID number"
        er == E_CTX -> "context error"
        er == E_MACV -> "memory access violation"
        er == E_OACV -> "object access violation"
        er == E_ILUSE -> "illegal service call use"
        er == E_NOMEM -> "insufficient memory"
        er == E_NOID -> "no ID number available"
        er == E_OBJ -> "object state error"
        er == E_NOEXS -> "non-existent object"
        er == E_QOVR -> "queue overflow"
        er == E_RLWAI -> "forced release from waiting"
        er == E_TMOUT -> "polling failure or timeout"
        er == E_DLT -> "waiting object deleted"
        er == E_CLS -> "waiting object state changed"
        er == E_WBLK -> "non-blocking code accepted"
        er == E_BOVR -> "buffer overflow"
        // The TOPPERS third generation kernels
        er == E_NORES -> "insufficient system resources"
        er == E_RASTER -> "termination request raised"
        er == E_COMM -> "communication failure"
        else -> null
    }

public fun isInterrupted(er: ER): Boolean = (er == E_RLWAI).toUByte()

public fun decodeErrorKind(er: ER): ErrorKind =
    when {
        // Success
        er >= 0 -> {
            val er1 = er
            ErrorKind.Uncategorized
        }
        // μITRON 4.0
        // abi.ESys
        er == E_NOSPT -> ErrorKind.Unsupported
        // Some("unsupported function"),
        er == E_RSFN -> ErrorKind.InvalidInput
        // Some("reserved function code"),
        er == E_RSATR -> ErrorKind.InvalidInput
        // Some("reserved attribute"),
        er == E_PAR -> ErrorKind.InvalidInput
        // Some("parameter error"),
        er == E_ID -> ErrorKind.NotFound
        // Some("invalid ID number"),
        // abi.ECtx
        er == E_MACV -> ErrorKind.PermissionDenied
        // Some("memory access violation"),
        er == E_OACV -> ErrorKind.PermissionDenied
        // Some("object access violation"),
        // abi.EIluse
        er == E_NOMEM -> ErrorKind.OutOfMemory
        // Some("insufficient memory"),
        er == E_NOID -> ErrorKind.OutOfMemory
        // Some("no ID number available"),
        // abi.EObj
        er == E_NOEXS -> ErrorKind.NotFound
        // Some("non-existent object"),
        // abi.EQovr
        er == E_RLWAI -> ErrorKind.Interrupted
        // Some("forced release from waiting"),
        er == E_TMOUT -> ErrorKind.TimedOut
        // Some("polling failure or timeout"),
        // abi.EDlt
        // abi.ECls
        // abi.EWblk
        // abi.EBovr
        // The TOPPERS third generation kernels
        er == E_NORES -> ErrorKind.OutOfMemory
        // Some("insufficient system resources"),
        // abi.ERaster
        // abi.EComm
        else -> ErrorKind.Uncategorized
    }

/**
 * Similar to `ItronError.errIfNegative(er).expect()` except that, while
 * panicking, it prints the message to `panicOutput` and aborts the program
 * instead. This ensures the error message is not obscured by double
 * panicking.
 *
 * This is useful for diagnosing creation failures of synchronization
 * primitives that are used by `std`s internal mechanisms. Such failures
 * are common when the system is mis-configured to provide a too-small pool for
 * kernel objects.
 */
public fun expectSuccess(er: ER, msg: String): ER =
    run {
        val matchVal = ItronError.errIfNegative(er)
        when (matchVal) { is Result.Ok<*> -> {
            val x = matchVal.value
            x
        }; is Result.Err<*> -> {
            val e = matchVal.error
            fail(e, msg)
        } }
    }

/**
 * Similar to `ItronError.errIfNegative(er).expect()` but aborts instead.
 *
 * Use this where panicking is not allowed or the effect of the failure
 * would be persistent.
 */
public fun expectSuccessAborting(er: ER, msg: String): ER =
    run {
        val matchVal = ItronError.errIfNegative(er)
        when (matchVal) { is Result.Ok<*> -> {
            val x = matchVal.value
            x
        }; is Result.Err<*> -> {
            val e = matchVal.error
            failAborting(e, msg)
        } }
    }

public fun fail(e: Any, msg: String): Nothing =
    if (panicking()) {
        failAborting(e, msg)
    } else {
        error("$msg failed: $e")
    }

public fun failAborting(e: Any, msg: String): Nothing =
    run {
        when (val matchVal = panicOutput()) {
            null -> {}
            else -> {
                val out = matchVal
                Write.writeFmt(out, "fatal runtime error: " + ("$msg failed: $e").toString() + ", aborting\n")
            }
        }
        run {
            "fatal runtime error: " + ("$msg failed: $e").toString() + ", aborting\n"
        }
        abort()
    }
