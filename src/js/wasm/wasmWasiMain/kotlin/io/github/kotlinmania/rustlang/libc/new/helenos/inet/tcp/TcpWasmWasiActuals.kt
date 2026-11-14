@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

// The host must provide the C companion wrapper exports in import module kmcore.
public actual fun tcpCreate(tcp: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpCreate")

public actual fun tcpDestroy(tcp: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpDestroy")
}

public actual fun tcpConnCreate(tcp: MutPtr, epp: MutPtr, callbacks: MutPtr, arg: MutPtr, conn: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpConnCreate")

public actual fun tcpConnDestroy(conn: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpConnDestroy")
}

public actual fun tcpListenerCreate(tcp: MutPtr, ep: MutPtr, listenCallbacks: MutPtr, listenArg: MutPtr, connCallbacks: MutPtr, connArg: MutPtr, listener: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpListenerCreate")

public actual fun tcpListenerDestroy(listener: MutPtr) {
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpListenerDestroy")
}

public actual fun tcpConnUserptr(conn: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpConnUserptr")

public actual fun tcpListenerUserptr(listener: MutPtr): MutPtr =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpListenerUserptr")

public actual fun tcpConnWaitConnected(conn: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpConnWaitConnected")

public actual fun tcpConnSend(conn: MutPtr, buf: ConstPtr, len: SizeT): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpConnSend")

public actual fun tcpConnSendFin(conn: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpConnSendFin")

public actual fun tcpConnPush(conn: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpConnPush")

public actual fun tcpConnReset(conn: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpConnReset")

public actual fun tcpConnRecv(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpConnRecv")

public actual fun tcpConnRecvWait(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT =
    throw UnsupportedOperationException("WasmWasi FFI requires a host memory/callback/aggregate or variadic ABI adapter for libcNewHelenosInetTcpTcpConnRecvWait")
