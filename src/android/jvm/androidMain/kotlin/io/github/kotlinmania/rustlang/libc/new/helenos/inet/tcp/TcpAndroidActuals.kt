@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

private object NewHelenosInetTcpJniBridge {
    init {
        System.loadLibrary("kmcore_jni")
    }

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpCreate(tcp: Long): Int

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpDestroy(tcp: Long)

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpConnCreate(tcp: Long, epp: Long, callbacks: Long, arg: Long, conn: Long): Int

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpConnDestroy(conn: Long)

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpListenerCreate(tcp: Long, ep: Long, listenCallbacks: Long, listenArg: Long, connCallbacks: Long, connArg: Long, listener: Long): Int

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpListenerDestroy(listener: Long)

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpConnUserptr(conn: Long): Long

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpListenerUserptr(listener: Long): Long

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpConnWaitConnected(conn: Long): Int

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpConnSend(conn: Long, buf: Long, len: Long): Int

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpConnSendFin(conn: Long): Int

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpConnPush(conn: Long): Int

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpConnReset(conn: Long): Int

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpConnRecv(conn: Long, buf: Long, bufsize: Long, receivedLen: Long): Int

    @JvmStatic
    external fun libcNewHelenosInetTcpTcpConnRecvWait(conn: Long, buf: Long, bufsize: Long, receivedLen: Long): Int
}

public actual fun tcpCreate(tcp: MutPtr): ErrnoT =
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpCreate(tcp.rawAddress)

public actual fun tcpDestroy(tcp: MutPtr) {
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpDestroy(tcp.rawAddress)
}

public actual fun tcpConnCreate(tcp: MutPtr, epp: MutPtr, callbacks: MutPtr, arg: MutPtr, conn: MutPtr): ErrnoT =
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpConnCreate(tcp.rawAddress, epp.rawAddress, callbacks.rawAddress, arg.rawAddress, conn.rawAddress)

public actual fun tcpConnDestroy(conn: MutPtr) {
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpConnDestroy(conn.rawAddress)
}

public actual fun tcpListenerCreate(tcp: MutPtr, ep: MutPtr, listenCallbacks: MutPtr, listenArg: MutPtr, connCallbacks: MutPtr, connArg: MutPtr, listener: MutPtr): ErrnoT =
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpListenerCreate(tcp.rawAddress, ep.rawAddress, listenCallbacks.rawAddress, listenArg.rawAddress, connCallbacks.rawAddress, connArg.rawAddress, listener.rawAddress)

public actual fun tcpListenerDestroy(listener: MutPtr) {
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpListenerDestroy(listener.rawAddress)
}

public actual fun tcpConnUserptr(conn: MutPtr): MutPtr =
    MutPtr(NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpConnUserptr(conn.rawAddress))

public actual fun tcpListenerUserptr(listener: MutPtr): MutPtr =
    MutPtr(NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpListenerUserptr(listener.rawAddress))

public actual fun tcpConnWaitConnected(conn: MutPtr): ErrnoT =
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpConnWaitConnected(conn.rawAddress)

public actual fun tcpConnSend(conn: MutPtr, buf: ConstPtr, len: SizeT): ErrnoT =
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpConnSend(conn.rawAddress, buf.rawAddress, len.toLong())

public actual fun tcpConnSendFin(conn: MutPtr): ErrnoT =
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpConnSendFin(conn.rawAddress)

public actual fun tcpConnPush(conn: MutPtr): ErrnoT =
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpConnPush(conn.rawAddress)

public actual fun tcpConnReset(conn: MutPtr): ErrnoT =
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpConnReset(conn.rawAddress)

public actual fun tcpConnRecv(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT =
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpConnRecv(conn.rawAddress, buf.rawAddress, bufsize.toLong(), receivedLen.rawAddress)

public actual fun tcpConnRecvWait(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT =
    NewHelenosInetTcpJniBridge.libcNewHelenosInetTcpTcpConnRecvWait(conn.rawAddress, buf.rawAddress, bufsize.toLong(), receivedLen.rawAddress)
