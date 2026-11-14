@file:OptIn(ExperimentalForeignApi::class, ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpConnCreate
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpConnDestroy
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpConnPush
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpConnRecv
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpConnRecvWait
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpConnReset
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpConnSend
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpConnSendFin
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpConnUserptr
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpConnWaitConnected
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpCreate
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpDestroy
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpListenerCreate
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpListenerDestroy
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp.cinterop.libcNewHelenosInetTcpTcpListenerUserptr
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong

public actual fun tcpCreate(tcp: MutPtr): ErrnoT =
    libcNewHelenosInetTcpTcpCreate(tcp.rawAddress.toCPointer<ByteVar>())

public actual fun tcpDestroy(tcp: MutPtr) {
    libcNewHelenosInetTcpTcpDestroy(tcp.rawAddress.toCPointer<ByteVar>())
}

public actual fun tcpConnCreate(tcp: MutPtr, epp: MutPtr, callbacks: MutPtr, arg: MutPtr, conn: MutPtr): ErrnoT =
    libcNewHelenosInetTcpTcpConnCreate(tcp.rawAddress.toCPointer<ByteVar>(), epp.rawAddress.toCPointer<ByteVar>(), callbacks.rawAddress.toCPointer<ByteVar>(), arg.rawAddress.toCPointer<ByteVar>(), conn.rawAddress.toCPointer<ByteVar>())

public actual fun tcpConnDestroy(conn: MutPtr) {
    libcNewHelenosInetTcpTcpConnDestroy(conn.rawAddress.toCPointer<ByteVar>())
}

public actual fun tcpListenerCreate(tcp: MutPtr, ep: MutPtr, listenCallbacks: MutPtr, listenArg: MutPtr, connCallbacks: MutPtr, connArg: MutPtr, listener: MutPtr): ErrnoT =
    libcNewHelenosInetTcpTcpListenerCreate(tcp.rawAddress.toCPointer<ByteVar>(), ep.rawAddress.toCPointer<ByteVar>(), listenCallbacks.rawAddress.toCPointer<ByteVar>(), listenArg.rawAddress.toCPointer<ByteVar>(), connCallbacks.rawAddress.toCPointer<ByteVar>(), connArg.rawAddress.toCPointer<ByteVar>(), listener.rawAddress.toCPointer<ByteVar>())

public actual fun tcpListenerDestroy(listener: MutPtr) {
    libcNewHelenosInetTcpTcpListenerDestroy(listener.rawAddress.toCPointer<ByteVar>())
}

public actual fun tcpConnUserptr(conn: MutPtr): MutPtr =
    MutPtr(libcNewHelenosInetTcpTcpConnUserptr(conn.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun tcpListenerUserptr(listener: MutPtr): MutPtr =
    MutPtr(libcNewHelenosInetTcpTcpListenerUserptr(listener.rawAddress.toCPointer<ByteVar>())?.toLong() ?: 0L)

public actual fun tcpConnWaitConnected(conn: MutPtr): ErrnoT =
    libcNewHelenosInetTcpTcpConnWaitConnected(conn.rawAddress.toCPointer<ByteVar>())

public actual fun tcpConnSend(conn: MutPtr, buf: ConstPtr, len: SizeT): ErrnoT =
    libcNewHelenosInetTcpTcpConnSend(conn.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), len)

public actual fun tcpConnSendFin(conn: MutPtr): ErrnoT =
    libcNewHelenosInetTcpTcpConnSendFin(conn.rawAddress.toCPointer<ByteVar>())

public actual fun tcpConnPush(conn: MutPtr): ErrnoT =
    libcNewHelenosInetTcpTcpConnPush(conn.rawAddress.toCPointer<ByteVar>())

public actual fun tcpConnReset(conn: MutPtr): ErrnoT =
    libcNewHelenosInetTcpTcpConnReset(conn.rawAddress.toCPointer<ByteVar>())

public actual fun tcpConnRecv(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT =
    libcNewHelenosInetTcpTcpConnRecv(conn.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), bufsize, receivedLen.rawAddress.toCPointer<ByteVar>())

public actual fun tcpConnRecvWait(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT =
    libcNewHelenosInetTcpTcpConnRecvWait(conn.rawAddress.toCPointer<ByteVar>(), buf.rawAddress.toCPointer<ByteVar>(), bufsize, receivedLen.rawAddress.toCPointer<ByteVar>())
