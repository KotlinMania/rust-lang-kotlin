@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

private val newHelenosInetTcpNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun tcpCreate(tcp: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAddon != null) (newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpCreate(tcp.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpDestroy(tcp: MutPtr) {
    if (newHelenosInetTcpNapiAddon != null) {
        newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpDestroy(tcp.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tcpConnCreate(tcp: MutPtr, epp: MutPtr, callbacks: MutPtr, arg: MutPtr, conn: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAddon != null) (newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpConnCreate(tcp.rawAddress.toString(), epp.rawAddress.toString(), callbacks.rawAddress.toString(), arg.rawAddress.toString(), conn.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnDestroy(conn: MutPtr) {
    if (newHelenosInetTcpNapiAddon != null) {
        newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpConnDestroy(conn.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tcpListenerCreate(tcp: MutPtr, ep: MutPtr, listenCallbacks: MutPtr, listenArg: MutPtr, connCallbacks: MutPtr, connArg: MutPtr, listener: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAddon != null) (newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpListenerCreate(tcp.rawAddress.toString(), ep.rawAddress.toString(), listenCallbacks.rawAddress.toString(), listenArg.rawAddress.toString(), connCallbacks.rawAddress.toString(), connArg.rawAddress.toString(), listener.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpListenerDestroy(listener: MutPtr) {
    if (newHelenosInetTcpNapiAddon != null) {
        newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpListenerDestroy(listener.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tcpConnUserptr(conn: MutPtr): MutPtr =
    if (newHelenosInetTcpNapiAddon != null) MutPtr((newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpConnUserptr(conn.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpListenerUserptr(listener: MutPtr): MutPtr =
    if (newHelenosInetTcpNapiAddon != null) MutPtr((newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpListenerUserptr(listener.rawAddress.toString()) as String).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnWaitConnected(conn: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAddon != null) (newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpConnWaitConnected(conn.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnSend(conn: MutPtr, buf: ConstPtr, len: SizeT): ErrnoT =
    if (newHelenosInetTcpNapiAddon != null) (newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpConnSend(conn.rawAddress.toString(), buf.rawAddress.toString(), len.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnSendFin(conn: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAddon != null) (newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpConnSendFin(conn.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnPush(conn: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAddon != null) (newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpConnPush(conn.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnReset(conn: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAddon != null) (newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpConnReset(conn.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnRecv(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAddon != null) (newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpConnRecv(conn.rawAddress.toString(), buf.rawAddress.toString(), bufsize.toString(), receivedLen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnRecvWait(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAddon != null) (newHelenosInetTcpNapiAddon.libcNewHelenosInetTcpTcpConnRecvWait(conn.rawAddress.toString(), buf.rawAddress.toString(), bufsize.toString(), receivedLen.rawAddress.toString()) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")
