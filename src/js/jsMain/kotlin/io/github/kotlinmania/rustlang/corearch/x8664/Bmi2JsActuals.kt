@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.x8664

private val x8664Bmi2NapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun x86Bmi2Bzhi64(x: ULong, y: ULong): ULong =
    if (x8664Bmi2NapiAddon != null) (x8664Bmi2NapiAddon.corearchX8664Bmi2X86Bmi2Bzhi64(x.toString(), y.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun x86Bmi2Pdep64(x: ULong, y: ULong): ULong =
    if (x8664Bmi2NapiAddon != null) (x8664Bmi2NapiAddon.corearchX8664Bmi2X86Bmi2Pdep64(x.toString(), y.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun x86Bmi2Pext64(x: ULong, y: ULong): ULong =
    if (x8664Bmi2NapiAddon != null) (x8664Bmi2NapiAddon.corearchX8664Bmi2X86Bmi2Pext64(x.toString(), y.toString()) as String).toULong() else throw UnsupportedOperationException("Native FFI addon is not available")
