@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.x8664

import io.github.kotlinmania.rustlang.core.ptr.ConstPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initX8664MovrsNapiAddon(): Boolean

private val x8664MovrsNapiAvailable: Boolean = initX8664MovrsNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchX8664MovrsMovrsqi(p0)")
private external fun wasmCorearchX8664MovrsMovrsqi(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchX8664MovrsMovrshi(p0)")
private external fun wasmCorearchX8664MovrsMovrshi(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchX8664MovrsMovrssi(p0)")
private external fun wasmCorearchX8664MovrsMovrssi(p0: String): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchX8664MovrsMovrsdi(p0)")
private external fun wasmCorearchX8664MovrsMovrsdi(p0: String): String

public actual fun movrsqi(src: ConstPtr): Byte =
    if (x8664MovrsNapiAvailable) wasmCorearchX8664MovrsMovrsqi(src.rawAddress.toString()).toByte() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun movrshi(src: ConstPtr): Short =
    if (x8664MovrsNapiAvailable) wasmCorearchX8664MovrsMovrshi(src.rawAddress.toString()).toShort() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun movrssi(src: ConstPtr): Int =
    if (x8664MovrsNapiAvailable) wasmCorearchX8664MovrsMovrssi(src.rawAddress.toString()) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun movrsdi(src: ConstPtr): Long =
    if (x8664MovrsNapiAvailable) wasmCorearchX8664MovrsMovrsdi(src.rawAddress.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")
