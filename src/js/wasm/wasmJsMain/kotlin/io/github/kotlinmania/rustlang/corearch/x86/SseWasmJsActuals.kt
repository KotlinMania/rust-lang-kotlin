@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initX86SseNapiAddon(): Boolean

private val x86SseNapiAvailable: Boolean = initX86SseNapiAddon()

@JsFun("() => globalThis.__km_napi_kmcore.corearchX86SseSfence()")
private external fun wasmCorearchX86SseSfence()

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchX86SseStmxcsr(p0)")
private external fun wasmCorearchX86SseStmxcsr(p0: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchX86SseLdmxcsr(p0)")
private external fun wasmCorearchX86SseLdmxcsr(p0: String)

@JsFun("(p0, p1, p2, p3) => globalThis.__km_napi_kmcore.corearchX86SsePrefetch(p0, p1, p2, p3)")
private external fun wasmCorearchX86SsePrefetch(p0: String, p1: Int, p2: Int, p3: Int)

public actual fun rcpss(a: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rcpps(a: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rsqrtss(a: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun rsqrtps(a: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun minss(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun minps(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun maxss(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun maxps(a: M128, b: M128): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cmpps(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comieqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comiltSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comileSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comigtSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comigeSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun comineqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomieqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomiltSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomileSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomigtSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomigeSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ucomineqSs(a: M128, b: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvtss2si(a: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cvttss2si(a: M128): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun sfence() {
    if (x86SseNapiAvailable) {
        wasmCorearchX86SseSfence()
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun stmxcsr(p: MutPtr) {
    if (x86SseNapiAvailable) {
        wasmCorearchX86SseStmxcsr(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun ldmxcsr(p: ConstPtr) {
    if (x86SseNapiAvailable) {
        wasmCorearchX86SseLdmxcsr(p.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun prefetch(p: ConstPtr, rw: Int, loc: Int, ty: Int) {
    if (x86SseNapiAvailable) {
        wasmCorearchX86SsePrefetch(p.rawAddress.toString(), rw, loc, ty)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun cmpss(a: M128, b: M128, imm8: Byte): M128 =
    throw UnsupportedOperationException("Native FFI addon is not available")
