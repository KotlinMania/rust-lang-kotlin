@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

private val corearchLoongarchsharedNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun dbar(a: Int) {
    if (corearchLoongarchsharedNapiAddon != null) {
        corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedDbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun ibar(a: Int) {
    if (corearchLoongarchsharedNapiAddon != null) {
        corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedIbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun movgr2fcsr(a: Int, b: Int) {
    if (corearchLoongarchsharedNapiAddon != null) {
        corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedMovgr2fcsr(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun movfcsr2gr(a: Int): Int =
    if (corearchLoongarchsharedNapiAddon != null) (corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedMovfcsr2gr(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdB(a: Int): Int =
    if (corearchLoongarchsharedNapiAddon != null) (corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedIocsrrdB(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdH(a: Int): Int =
    if (corearchLoongarchsharedNapiAddon != null) (corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedIocsrrdH(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdW(a: Int): Int =
    if (corearchLoongarchsharedNapiAddon != null) (corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedIocsrrdW(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrwrB(a: Int, b: Int) {
    if (corearchLoongarchsharedNapiAddon != null) {
        corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedIocsrwrB(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun iocsrwrH(a: Int, b: Int) {
    if (corearchLoongarchsharedNapiAddon != null) {
        corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedIocsrwrH(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun iocsrwrW(a: Int, b: Int) {
    if (corearchLoongarchsharedNapiAddon != null) {
        corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedIocsrwrW(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun breakTarget(a: Int) {
    if (corearchLoongarchsharedNapiAddon != null) {
        corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedBreakTarget(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun cpucfg(a: Int): Int =
    if (corearchLoongarchsharedNapiAddon != null) (corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedCpucfg(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(a: Int) {
    if (corearchLoongarchsharedNapiAddon != null) {
        corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedSyscall(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun frecipeS(a: Float): Float =
    if (corearchLoongarchsharedNapiAddon != null) (corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedFrecipeS(a.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frecipeD(a: Double): Double =
    if (corearchLoongarchsharedNapiAddon != null) (corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedFrecipeD(a) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frsqrteS(a: Float): Float =
    if (corearchLoongarchsharedNapiAddon != null) (corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedFrsqrteS(a.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frsqrteD(a: Double): Double =
    if (corearchLoongarchsharedNapiAddon != null) (corearchLoongarchsharedNapiAddon.rustlangCorearchLoongarchsharedFrsqrteD(a) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")
