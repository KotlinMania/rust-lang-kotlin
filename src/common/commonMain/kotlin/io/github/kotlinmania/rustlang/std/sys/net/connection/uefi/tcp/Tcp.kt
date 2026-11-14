// port-lint: source sys/net/connection/uefi/tcp.rs
package io.github.kotlinmania.rustlang.std.sys.net.connection.uefi.tcp

import io.github.kotlinmania.rustlang.core.io.ioslice.IoSlice
import io.github.kotlinmania.rustlang.core.io.ioslice.IoSliceMut
import io.github.kotlinmania.rustlang.core.result.Result
import io.github.kotlinmania.rustlang.core.time.Duration
import io.github.kotlinmania.rustlang.std.os.unix.net.SocketAddr
import io.github.kotlinmania.rustlang.std.sys.net.connection.uefi.tcp4.Tcp4
import io.github.kotlinmania.rustlang.std.sys.pal.hermit.unsupported
import io.github.kotlinmania.rustlang.std.sys.pal.uefi.helpers.ipv4FromREfi

internal sealed class Tcp {
    public data class V4(public var value: Tcp4) : Tcp()

    // SAFETY: UEFI has no regular threads, and as per <https://github.com/rust-lang/rust/issues/133604>
    // std does not support being invoked from "irregular threads" such as interrupt handlers or other
    // CPU cores that run outside the scope of UEFI.

    internal open fun accept(): Result<Tcp> =
        when (this) {
            is V4 -> {
                val (client) = this
                client.accept().map(Tcp.V4)
            }
        }

    internal open fun write(buf: List<UByte>, timeout: Duration?): Result<Int> =
        when (this) {
            is V4 -> {
                val (client) = this
                client.write(buf, timeout)
            }
        }

    internal open fun writeVectored(buf: List<IoSlice>, timeout: Duration?): Result<Int> =
        when (this) {
            is V4 -> {
                val (client) = this
                client.writeVectored(buf, timeout)
            }
        }

    internal open fun read(buf: MutableList<UByte>, timeout: Duration?): Result<Int> =
        when (this) {
            is V4 -> {
                val (client) = this
                client.read(buf, timeout)
            }
        }

    internal open fun readVectored(buf: MutableList<IoSliceMut>, timeout: Duration?): Result<Int> =
        when (this) {
            is V4 -> {
                val (client) = this
                client.readVectored(buf, timeout)
            }
        }

    internal open fun ttl(): Result<UInt> =
        when (this) {
            is V4 -> {
                val (client) = this
                client.getModeData().map({ x -> x.timeToLive.into() })
            }
        }

    internal open fun nodelay(): Result<Boolean> {
        return when (this) {
            is V4 -> {
                val (client) = this
                val nagle: Boolean = when (val tryResult = client.getControlOption()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }.enableNagle.into()
                Result.Ok(!nagle)
            }
        }
    }

    public open fun peerAddr(): Result<SocketAddr> =
        when (this) {
            is V4 -> {
                val (client) = this
                client.getModeData().map({ x ->
                    SocketAddr.new(ipv4FromREfi(x.accessPoint.remoteAddress).into(), x.accessPoint.remotePort)
                })
            }
        }

    public open fun socketAddr(): Result<SocketAddr> =
        when (this) {
            is V4 -> {
                val (client) = this
                client.getModeData().map({ x ->
                    SocketAddr.new(ipv4FromREfi(x.accessPoint.stationAddress).into(), x.accessPoint.stationPort)
                })
            }
        }

    public companion object {
        internal fun connect(addr: SocketAddr, timeout: Duration?): Result<Tcp> {
            return when (addr) {
                is SocketAddr.V4 -> {
                    val (x) = addr
                    val temp = when (val tryResult = Tcp4.Tcp4.new()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    when (val tryResult = temp.configure(true, x, null)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    when (val tryResult = temp.connect(timeout)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    Result.Ok(Tcp.v4(temp))
                }
                is SocketAddr.V6 -> unsupported()
            }
        }

        internal fun bind(addr: SocketAddr): Result<Tcp> {
            return when (addr) {
                is SocketAddr.V4 -> {
                    val (x) = addr
                    val temp = when (val tryResult = Tcp4.Tcp4.new()) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    when (val tryResult = temp.configure(false, null, x)) { is Result.Ok<*> -> tryResult.value; is Result.Err<*> -> return Result.Err(tryResult.error) }
                    Result.Ok(Tcp.v4(temp))
                }
                is SocketAddr.V6 -> unsupported()
            }
        }
    }
}
