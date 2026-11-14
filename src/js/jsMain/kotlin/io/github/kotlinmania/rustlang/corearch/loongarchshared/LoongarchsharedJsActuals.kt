@file:OptIn(kotlin.ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

private val loongarchsharedNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/corearch-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_corearch = null; return null; })()")

public actual fun dbar(a: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedDbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun ibar(a: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedIbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun movgr2fcsr(a: Int, b: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedMovgr2fcsr(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun movfcsr2gr(a: Int): Int =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedMovfcsr2gr(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdB(a: Int): Int =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedIocsrrdB(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdH(a: Int): Int =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedIocsrrdH(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdW(a: Int): Int =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedIocsrrdW(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrwrB(a: Int, b: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedIocsrwrB(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun iocsrwrH(a: Int, b: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedIocsrwrH(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun iocsrwrW(a: Int, b: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedIocsrwrW(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun breakTarget(a: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedBreakTarget(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun cpucfg(a: Int): Int =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedCpucfg(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(a: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedSyscall(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun frecipeS(a: Float): Float =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedFrecipeS(a.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frecipeD(a: Double): Double =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedFrecipeD(a) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frsqrteS(a: Float): Float =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedFrsqrteS(a.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frsqrteD(a: Double): Double =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedFrsqrteD(a) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")
