// port-lint: source new/helenos/inet/tcp.rs
package io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

// HelenOS TCP API
//
// * Header file: <https://github.com/HelenOS/helenos/tree/master/uspace/lib/inet/include/inet/tcp.h>

public fun interface TcpCbTConnectedCallback {
    public operator fun invoke(conn: MutPtr)
}

public fun interface TcpListenCbTNewConnCallback {
    public operator fun invoke(listener: MutPtr, conn: MutPtr)
}

public data class TcpCbT(
    public var connected: TcpCbTConnectedCallback? = null,
    public var connFailed: TcpCbTConnectedCallback? = null,
    public var connReset: TcpCbTConnectedCallback? = null,
    public var dataAvail: TcpCbTConnectedCallback? = null,
    public var urgData: TcpCbTConnectedCallback? = null,
)

public data class TcpListenCbT(
    public var newConn: TcpListenCbTNewConnCallback? = null,
)

/**
 * This is an extern type ("opaque" or "incomplete" type in C).
 *
 * <div class="warning">
 * This type's current representation allows inspecting some properties, such as via
 * <code>sizeOf</code>, and it is technically possible to construct the type within
 * <code>MaybeUninit</code>, However, this <strong>MUST NOT</strong> be relied upon
 * because a future version of <code>libc</code> may switch to a proper
 * <a href="https://rust-lang.github.io/rfcs/1861-extern-types.html">extern type</a>
 * representation when available.
 * </div>
 */
public data class TcpT(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

/**
 * This is an extern type ("opaque" or "incomplete" type in C).
 *
 * <div class="warning">
 * This type's current representation allows inspecting some properties, such as via
 * <code>sizeOf</code>, and it is technically possible to construct the type within
 * <code>MaybeUninit</code>, However, this <strong>MUST NOT</strong> be relied upon
 * because a future version of <code>libc</code> may switch to a proper
 * <a href="https://rust-lang.github.io/rfcs/1861-extern-types.html">extern type</a>
 * representation when available.
 * </div>
 */
public data class TcpConnT(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

/**
 * This is an extern type ("opaque" or "incomplete" type in C).
 *
 * <div class="warning">
 * This type's current representation allows inspecting some properties, such as via
 * <code>sizeOf</code>, and it is technically possible to construct the type within
 * <code>MaybeUninit</code>, However, this <strong>MUST NOT</strong> be relied upon
 * because a future version of <code>libc</code> may switch to a proper
 * <a href="https://rust-lang.github.io/rfcs/1861-extern-types.html">extern type</a>
 * representation when available.
 * </div>
 */
public data class TcpListenerT(
    public var data: Unit = Unit,
    public var marker: Unit = Unit,
)

public expect fun tcpCreate(tcp: MutPtr): ErrnoT

public expect fun tcpDestroy(tcp: MutPtr)

public expect fun tcpConnCreate(tcp: MutPtr, epp: MutPtr, callbacks: MutPtr, arg: MutPtr, conn: MutPtr): ErrnoT

public expect fun tcpConnDestroy(conn: MutPtr)

public expect fun tcpListenerCreate(tcp: MutPtr, ep: MutPtr, listenCallbacks: MutPtr, listenArg: MutPtr, connCallbacks: MutPtr, connArg: MutPtr, listener: MutPtr): ErrnoT

public expect fun tcpListenerDestroy(listener: MutPtr)

public expect fun tcpConnUserptr(conn: MutPtr): MutPtr

public expect fun tcpListenerUserptr(listener: MutPtr): MutPtr

public expect fun tcpConnWaitConnected(conn: MutPtr): ErrnoT

public expect fun tcpConnSend(conn: MutPtr, buf: ConstPtr, len: SizeT): ErrnoT

public expect fun tcpConnSendFin(conn: MutPtr): ErrnoT

public expect fun tcpConnPush(conn: MutPtr): ErrnoT

public expect fun tcpConnReset(conn: MutPtr): ErrnoT

public expect fun tcpConnRecv(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT

public expect fun tcpConnRecvWait(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT
