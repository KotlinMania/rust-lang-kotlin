@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.libc.new.helenos.inet.dnsr

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.libc.*
import io.github.kotlinmania.rustlang.libc.new.helenos.bits.ErrnoT
import io.github.kotlinmania.rustlang.libc.new.helenos.inet.addr.IpVerT

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initNewHelenosInetDnsrNapiAddon(): Boolean

private val newHelenosInetDnsrNapiAvailable: Boolean = initNewHelenosInetDnsrNapiAddon()

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.libcNewHelenosInetDnsrDnsrName2host(p0, p1, p2)")
private external fun wasmLibcNewHelenosInetDnsrDnsrName2host(p0: String, p1: String, p2: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.libcNewHelenosInetDnsrDnsrHostinfoDestroy(p0)")
private external fun wasmLibcNewHelenosInetDnsrDnsrHostinfoDestroy(p0: String)

public actual fun dnsrName2host(name: ConstPtr, info: MutPtr, ipver: IpVerT): ErrnoT =
    if (newHelenosInetDnsrNapiAvailable) wasmLibcNewHelenosInetDnsrDnsrName2host(name.rawAddress.toString(), info.rawAddress.toString(), ipver.toInt()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun dnsrHostinfoDestroy(info: MutPtr) {
    if (newHelenosInetDnsrNapiAvailable) {
        wasmLibcNewHelenosInetDnsrDnsrHostinfoDestroy(info.rawAddress.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
