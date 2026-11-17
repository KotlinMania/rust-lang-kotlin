@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

private val loongarchsharedNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun externDbar(a: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedExternDbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externIbar(a: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedExternIbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externMovgr2fcsr(a: Int, b: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedExternMovgr2fcsr(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externMovfcsr2gr(a: Int): Int =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedExternMovfcsr2gr(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrrdB(a: Int): Int =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedExternIocsrrdB(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrrdH(a: Int): Int =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedExternIocsrrdH(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrrdW(a: Int): Int =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedExternIocsrrdW(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrwrB(a: Int, b: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedExternIocsrwrB(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externIocsrwrH(a: Int, b: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedExternIocsrwrH(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externIocsrwrW(a: Int, b: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedExternIocsrwrW(a, b)
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

public actual fun externCpucfg(a: Int): Int =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedExternCpucfg(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externSyscall(a: Int) {
    if (loongarchsharedNapiAddon != null) {
        loongarchsharedNapiAddon.corearchLoongarchsharedExternSyscall(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externFrecipeS(a: Float): Float =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedExternFrecipeS(a.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externFrecipeD(a: Double): Double =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedExternFrecipeD(a) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externFrsqrteS(a: Float): Float =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedExternFrsqrteS(a.toDouble()) as Double).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externFrsqrteD(a: Double): Double =
    if (loongarchsharedNapiAddon != null) (loongarchsharedNapiAddon.corearchLoongarchsharedExternFrsqrteD(a) as Double) else throw UnsupportedOperationException("Native FFI addon is not available")
