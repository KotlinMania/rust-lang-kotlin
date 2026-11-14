// port-lint: source os/xous/services/net.rs
package io.github.kotlinmania.rustlang.std.os.xous.services.net

import io.github.kotlinmania.rustlang.core.sync.atomic.Atomic
import io.github.kotlinmania.rustlang.core.sync.atomic.AtomicU32
import io.github.kotlinmania.rustlang.core.sync.atomic.Ordering
import io.github.kotlinmania.rustlang.std.net.Shutdown
import io.github.kotlinmania.rustlang.std.os.xous.ffi.definitions.Connection
import io.github.kotlinmania.rustlang.std.os.xous.services.connect
import io.github.kotlinmania.rustlang.std.sys.fs.unix.runtimesymbols.read
import io.github.kotlinmania.rustlang.std.sys.fs.unix.runtimesymbols.write

internal sealed class NetBlockingScalar {
    public data class StdGetTtlUdp(public var value: UShort) : NetBlockingScalar()
    // 36
    public data class StdSetTtlUdp(public var field0: UShort, public var field1: UInt) : NetBlockingScalar()
    // 37
    public data class StdGetTtlTcp(public var value: UShort) : NetBlockingScalar()
    // 36
    public data class StdSetTtlTcp(public var field0: UShort, public var field1: UInt) : NetBlockingScalar()
    // 37
    public data class StdGetNodelay(public var value: UShort) : NetBlockingScalar()
    // 38
    public data class StdSetNodelay(public var field0: UShort, public var field1: Boolean) : NetBlockingScalar()
    // 39
    public data class StdTcpClose(public var value: UShort) : NetBlockingScalar()
    // 34
    public data class StdUdpClose(public var value: UShort) : NetBlockingScalar()
    // 41
    public data class StdTcpStreamShutdown(public var field0: UShort, public var field1: Shutdown) : NetBlockingScalar()

    public open fun into(): IntArray =
        when (this) {
            is NetBlockingScalar.StdGetTtlTcp -> {
                val (fd) = this
                intArrayOf(36 or ((fd.toInt()) shl 16), 0, 0, 0, 0)
            }
            is NetBlockingScalar.StdGetTtlUdp -> {
                val (fd1) = this
                intArrayOf(36 or ((fd1.toInt()) shl 16), 0, 0, 0, 1)
            }
            is NetBlockingScalar.StdSetTtlTcp -> {
                val (fd2, ttl) = this
                intArrayOf(37 or ((fd2.toInt()) shl 16), ttl.toInt(), 0, 0, 0)
            }
            is NetBlockingScalar.StdSetTtlUdp -> {
                val (fd3, ttl1) = this
                intArrayOf(37 or ((fd3.toInt()) shl 16), ttl1.toInt(), 0, 0, 1)
            }
            is NetBlockingScalar.StdGetNodelay -> {
                val (fd4) = this
                intArrayOf(38 or ((fd4.toInt()) shl 16), 0, 0, 0, 0)
            }
            is NetBlockingScalar.StdSetNodelay -> {
                val (fd5, enabled) = this
                intArrayOf(39 or ((fd5.toInt()) shl 16), if (enabled) {
                    1
                } else {
                    0
                }, 0, 0, 1)
            }
            is NetBlockingScalar.StdTcpClose -> {
                val (fd6) = this
                intArrayOf(34 or ((fd6.toInt()) shl 16), 0, 0, 0, 0)
            }
            is NetBlockingScalar.StdUdpClose -> {
                val (fd7) = this
                intArrayOf(41 or ((fd7.toInt()) shl 16), 0, 0, 0, 0)
            }
            is NetBlockingScalar.StdTcpStreamShutdown -> {
                val (fd8, how) = this
                intArrayOf(46 or ((fd8.toInt()) shl 16), when (how) {
                    read -> 1
                    write -> 2
                    Shutdown.Both -> 3
                }, 0, 0, 0)
            }
        }
}

internal sealed class NetLendMut {
    public data object StdTcpConnect : NetLendMut()
    // 30
    public data class StdTcpTx(public var value: UShort) : NetLendMut()
    // 31
    public data class StdTcpPeek(public var field0: UShort, public var field1: Boolean) : NetLendMut()
    // 32
    public data class StdTcpRx(public var field0: UShort, public var field1: Boolean) : NetLendMut()
    // 33
    public data class StdGetAddress(public var value: UShort) : NetLendMut()
    // 35
    public data object StdUdpBind : NetLendMut()
    // 40
    public data class StdUdpRx(public var value: UShort) : NetLendMut()
    // 42
    public data class StdUdpTx(public var value: UShort) : NetLendMut()
    // 43
    public data object StdTcpListen : NetLendMut()
    // 44
    public data class StdTcpAccept(public var value: UShort) : NetLendMut()

    public open fun into(): Int =
        when (this) {
            NetLendMut.StdTcpConnect -> 30
            is NetLendMut.StdTcpTx -> {
                val (fd) = this
                31 or ((fd.toInt()) shl 16)
            }
            is NetLendMut.StdTcpPeek -> {
                val (fd1, blocking) = this
                32 or ((fd1.toInt()) shl 16) or
                    if (blocking) {
                        0x8000
                    } else {
                        0
                    }
            }
            is NetLendMut.StdTcpRx -> {
                val (fd2, blocking1) = this
                33 or ((fd2.toInt()) shl 16) or
                    if (blocking1) {
                        0x8000
                    } else {
                        0
                    }
            }
            is NetLendMut.StdGetAddress -> {
                val (fd3) = this
                35 or ((fd3.toInt()) shl 16)
            }
            NetLendMut.StdUdpBind -> 40
            is NetLendMut.StdUdpRx -> {
                val (fd4) = this
                42 or ((fd4.toInt()) shl 16)
            }
            is NetLendMut.StdUdpTx -> {
                val (fd5) = this
                43 or ((fd5.toInt()) shl 16)
            }
            NetLendMut.StdTcpListen -> 44
            is NetLendMut.StdTcpAccept -> {
                val (fd6) = this
                45 or ((fd6.toInt()) shl 16)
            }
        }
}

/**
 * Returns a `Connection` to the Network server. This server provides all
 * OS-level networking functions.
 */
internal fun netServer(): Connection {
    val NET_CONNECTION: Atomic<UInt>  = AtomicU32.new(0)
    val cid = NET_CONNECTION.load(Ordering.Relaxed)
    if (cid != 0) {
        return cid.into()
    }
    val cid1 = connect("_Middleware Network Server_").unwrap()
    NET_CONNECTION.store(cid1.into(), Ordering.Relaxed)
    return cid1
}
