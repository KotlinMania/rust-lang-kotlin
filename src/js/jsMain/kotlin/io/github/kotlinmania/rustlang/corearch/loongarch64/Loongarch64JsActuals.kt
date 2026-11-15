@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

private val corearchLoongarch64NapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun crcWBW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64CrcWBW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crcWHW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64CrcWHW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crcWWW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64CrcWWW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crcWDW(a: Long, b: Int): Int =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64CrcWDW(a.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWBW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64CrccWBW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWHW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64CrccWHW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWWW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64CrccWWW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWDW(a: Long, b: Int): Int =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64CrccWDW(a.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cacop(a: Long, b: Long, c: Long) {
    if (corearchLoongarch64NapiAddon != null) {
        corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64Cacop(a.toString(), b.toString(), c.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun csrrd(a: Int): Long =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64Csrrd(a) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun csrwr(a: Long, b: Int): Long =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64Csrwr(a.toString(), b) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64Csrxchg(a.toString(), b.toString(), c) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdD(a: Int): Long =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64IocsrrdD(a) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrwrD(a: Long, b: Int) {
    if (corearchLoongarch64NapiAddon != null) {
        corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64IocsrwrD(a.toString(), b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun asrtle(a: Long, b: Long) {
    if (corearchLoongarch64NapiAddon != null) {
        corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64Asrtle(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun asrtgt(a: Long, b: Long) {
    if (corearchLoongarch64NapiAddon != null) {
        corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64Asrtgt(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun lddir(a: Long, b: Long): Long =
    if (corearchLoongarch64NapiAddon != null) (corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64Lddir(a.toString(), b.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldpte(a: Long, b: Long) {
    if (corearchLoongarch64NapiAddon != null) {
        corearchLoongarch64NapiAddon.rustlangCorearchLoongarch64Ldpte(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
