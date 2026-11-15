@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x86

import io.github.kotlinmania.rustlang.simd.I8x16
import io.github.kotlinmania.rustlang.simd.U8x16

private val corearchX86Sse42NapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun pcmpestrm128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): U8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpestri128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpestriz128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpestric128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpestris128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpestrio128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpestria128(a: I8x16, la: Int, b: I8x16, lb: Int, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpistrm128(a: I8x16, b: I8x16, imm8: Byte): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpistri128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpistriz128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpistric128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpistris128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpistrio128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun pcmpistria128(a: I8x16, b: I8x16, imm8: Byte): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crc32328(crc: UInt, v: UByte): UInt =
    if (corearchX86Sse42NapiAddon != null) (corearchX86Sse42NapiAddon.rustlangCorearchX86Sse42Crc32328(crc.toInt(), v.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crc323216(crc: UInt, v: UShort): UInt =
    if (corearchX86Sse42NapiAddon != null) (corearchX86Sse42NapiAddon.rustlangCorearchX86Sse42Crc323216(crc.toInt(), v.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crc323232(crc: UInt, v: UInt): UInt =
    if (corearchX86Sse42NapiAddon != null) (corearchX86Sse42NapiAddon.rustlangCorearchX86Sse42Crc323232(crc.toInt(), v.toInt()) as Int).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")
