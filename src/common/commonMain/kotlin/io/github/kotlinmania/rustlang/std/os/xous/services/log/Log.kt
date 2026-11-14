// port-lint: source os/xous/services/log.rs
@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.std.os.xous.services.log

import io.github.kotlinmania.rustlang.core.mem.PrimitiveLayouts
import io.github.kotlinmania.rustlang.core.mem.sizeOf
import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.os.xous.ffi.connect
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Connection

/**
 * Group a `Int` worth of bytes into a `Int` and return it, beginning from
 * `offset` * sizeof(Int) bytes from the start. For example,
 * `groupOrNull([1, 2, 3, 4, 5, 6, 7, 8], 1)` on a 32-bit system will return a
 * `Int` with 5678 packed into it.
 */
public fun groupOrNull(data: List<UByte>, offset: Int): Int {
    val start = offset * sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)
    val outArray = UByteArray(sizeOf<Int>(typeLayoutT = PrimitiveLayouts.USIZE64)) { 0u.toUByte() }
    if (start < data.size) {
        for ((dest, src) in (outArray.zip(((data).slice(start until data.size))))) {
            dest = src
        }
    }
    return run { val bytesTmp0 = outArray; ((bytesTmp0[3].toInt() shl 24) or (bytesTmp0[2].toInt() shl 16) or (bytesTmp0[1].toInt() shl 8) or bytesTmp0[0].toInt()) }
}

internal sealed class LogScalar {
    /**
     * A panic occurred, and a panic log is forthcoming
     */
    public data object BeginPanic : LogScalar()
    /**
     * Some number of bytes will be appended to the log message
     */
    public data class AppendPanicMessage(public var value: List<UByte>) : LogScalar()

    public open fun into(): IntArray =
        when (this) {
            LogScalar.BeginPanic -> listOf(1000, 0, 0, 0, 0)
            is LogScalar.AppendPanicMessage -> {
                val (c) = this
                intArrayOf(1100 + c.size, groupOrNull(c, 0), groupOrNull(c, 1), groupOrNull(c, 2), groupOrNull(c, 3))
            }
        }
}

internal sealed class LogLend(
    public val value: Int,
) {
    public data object StandardOutput : LogLend(1)
    public data object StandardError : LogLend(2)

    public open fun into(): Int = this.toInt()
}

/**
 * Returns a `Connection` to the log server, which is used for printing messages to
 * the console and reporting panics.
 *
 * If the log server has not yet started, this will block until the server is
 * running. It is safe to call this multiple times, because the address is
 * shared among all threads in a process.
 */
internal fun logServer(): Connection {
    val LOG_SERVER_CONNECTION: Atomic<UInt>  = AtomicU32.new(0)
    val cid = LOG_SERVER_CONNECTION.load(Ordering.Relaxed)
    if (cid != 0) {
        return cid.into()
    }
    val cid1 = connect("xous-log-server ".tryInto().unwrap()).unwrap()
    LOG_SERVER_CONNECTION.store(cid1.into(), Ordering.Relaxed)
    return cid1
}
