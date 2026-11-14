@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.tcp

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.helenos.SizeT
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNewHelenosInetTcpNapiAddon(): Boolean

private val newHelenosInetTcpNapiAvailable: Boolean = initNewHelenosInetTcpNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpCreate(p0)")
private external fun wasmLibcNewHelenosInetTcpTcpCreate(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpDestroy(p0)")
private external fun wasmLibcNewHelenosInetTcpTcpDestroy(p0: String)

@JsFun("(p0, p1, p2, p3, p4) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpConnCreate(p0, p1, p2, p3, p4)")
private external fun wasmLibcNewHelenosInetTcpTcpConnCreate(p0: String, p1: String, p2: String, p3: String, p4: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpConnDestroy(p0)")
private external fun wasmLibcNewHelenosInetTcpTcpConnDestroy(p0: String)

@JsFun("(p0, p1, p2, p3, p4, p5, p6) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpListenerCreate(p0, p1, p2, p3, p4, p5, p6)")
private external fun wasmLibcNewHelenosInetTcpTcpListenerCreate(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String, p6: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpListenerDestroy(p0)")
private external fun wasmLibcNewHelenosInetTcpTcpListenerDestroy(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpConnUserptr(p0)")
private external fun wasmLibcNewHelenosInetTcpTcpConnUserptr(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpListenerUserptr(p0)")
private external fun wasmLibcNewHelenosInetTcpTcpListenerUserptr(p0: String): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpConnWaitConnected(p0)")
private external fun wasmLibcNewHelenosInetTcpTcpConnWaitConnected(p0: String): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpConnSend(p0, p1, p2)")
private external fun wasmLibcNewHelenosInetTcpTcpConnSend(p0: String, p1: String, p2: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpConnSendFin(p0)")
private external fun wasmLibcNewHelenosInetTcpTcpConnSendFin(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpConnPush(p0)")
private external fun wasmLibcNewHelenosInetTcpTcpConnPush(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpConnReset(p0)")
private external fun wasmLibcNewHelenosInetTcpTcpConnReset(p0: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpConnRecv(p0, p1, p2, p3)")
private external fun wasmLibcNewHelenosInetTcpTcpConnRecv(p0: String, p1: String, p2: String, p3: String): Int

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.libcNewHelenosInetTcpTcpConnRecvWait(p0, p1, p2, p3)")
private external fun wasmLibcNewHelenosInetTcpTcpConnRecvWait(p0: String, p1: String, p2: String, p3: String): Int

public actual fun tcpCreate(tcp: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAvailable) wasmLibcNewHelenosInetTcpTcpCreate(tcp.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpDestroy(tcp: MutPtr) {
    if (newHelenosInetTcpNapiAvailable) {
        wasmLibcNewHelenosInetTcpTcpDestroy(tcp.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tcpConnCreate(tcp: MutPtr, epp: MutPtr, callbacks: MutPtr, arg: MutPtr, conn: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAvailable) wasmLibcNewHelenosInetTcpTcpConnCreate(tcp.rawAddress.toString(), epp.rawAddress.toString(), callbacks.rawAddress.toString(), arg.rawAddress.toString(), conn.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnDestroy(conn: MutPtr) {
    if (newHelenosInetTcpNapiAvailable) {
        wasmLibcNewHelenosInetTcpTcpConnDestroy(conn.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tcpListenerCreate(tcp: MutPtr, ep: MutPtr, listenCallbacks: MutPtr, listenArg: MutPtr, connCallbacks: MutPtr, connArg: MutPtr, listener: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAvailable) wasmLibcNewHelenosInetTcpTcpListenerCreate(tcp.rawAddress.toString(), ep.rawAddress.toString(), listenCallbacks.rawAddress.toString(), listenArg.rawAddress.toString(), connCallbacks.rawAddress.toString(), connArg.rawAddress.toString(), listener.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpListenerDestroy(listener: MutPtr) {
    if (newHelenosInetTcpNapiAvailable) {
        wasmLibcNewHelenosInetTcpTcpListenerDestroy(listener.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun tcpConnUserptr(conn: MutPtr): MutPtr =
    if (newHelenosInetTcpNapiAvailable) MutPtr(wasmLibcNewHelenosInetTcpTcpConnUserptr(conn.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpListenerUserptr(listener: MutPtr): MutPtr =
    if (newHelenosInetTcpNapiAvailable) MutPtr(wasmLibcNewHelenosInetTcpTcpListenerUserptr(listener.rawAddress.toString()).toLong()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnWaitConnected(conn: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAvailable) wasmLibcNewHelenosInetTcpTcpConnWaitConnected(conn.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnSend(conn: MutPtr, buf: ConstPtr, len: SizeT): ErrnoT =
    if (newHelenosInetTcpNapiAvailable) wasmLibcNewHelenosInetTcpTcpConnSend(conn.rawAddress.toString(), buf.rawAddress.toString(), len.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnSendFin(conn: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAvailable) wasmLibcNewHelenosInetTcpTcpConnSendFin(conn.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnPush(conn: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAvailable) wasmLibcNewHelenosInetTcpTcpConnPush(conn.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnReset(conn: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAvailable) wasmLibcNewHelenosInetTcpTcpConnReset(conn.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnRecv(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAvailable) wasmLibcNewHelenosInetTcpTcpConnRecv(conn.rawAddress.toString(), buf.rawAddress.toString(), bufsize.toString(), receivedLen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun tcpConnRecvWait(conn: MutPtr, buf: MutPtr, bufsize: SizeT, receivedLen: MutPtr): ErrnoT =
    if (newHelenosInetTcpNapiAvailable) wasmLibcNewHelenosInetTcpTcpConnRecvWait(conn.rawAddress.toString(), buf.rawAddress.toString(), bufsize.toString(), receivedLen.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")
