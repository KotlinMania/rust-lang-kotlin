@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

private val loongarch64NapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun externCrcWBW(a: Int, b: Int): Int =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64ExternCrcWBW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrcWHW(a: Int, b: Int): Int =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64ExternCrcWHW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrcWWW(a: Int, b: Int): Int =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64ExternCrcWWW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrcWDW(a: Long, b: Int): Int =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64ExternCrcWDW(a.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrccWBW(a: Int, b: Int): Int =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64ExternCrccWBW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrccWHW(a: Int, b: Int): Int =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64ExternCrccWHW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrccWWW(a: Int, b: Int): Int =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64ExternCrccWWW(a, b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrccWDW(a: Long, b: Int): Int =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64ExternCrccWDW(a.toString(), b) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cacop(a: Long, b: Long, c: Long) {
    if (loongarch64NapiAddon != null) {
        loongarch64NapiAddon.corearchLoongarch64Cacop(a.toString(), b.toString(), c.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun csrrd(a: Int): Long =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64Csrrd(a) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun csrwr(a: Long, b: Int): Long =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64Csrwr(a.toString(), b) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64Csrxchg(a.toString(), b.toString(), c) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrrdD(a: Int): Long =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64ExternIocsrrdD(a) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrwrD(a: Long, b: Int) {
    if (loongarch64NapiAddon != null) {
        loongarch64NapiAddon.corearchLoongarch64ExternIocsrwrD(a.toString(), b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externAsrtle(a: Long, b: Long) {
    if (loongarch64NapiAddon != null) {
        loongarch64NapiAddon.corearchLoongarch64ExternAsrtle(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externAsrtgt(a: Long, b: Long) {
    if (loongarch64NapiAddon != null) {
        loongarch64NapiAddon.corearchLoongarch64ExternAsrtgt(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun lddir(a: Long, b: Long): Long =
    if (loongarch64NapiAddon != null) (loongarch64NapiAddon.corearchLoongarch64Lddir(a.toString(), b.toString()) as String).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldpte(a: Long, b: Long) {
    if (loongarch64NapiAddon != null) {
        loongarch64NapiAddon.corearchLoongarch64Ldpte(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
