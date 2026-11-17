@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLoongarchsharedNapiAddon(): Boolean

private val loongarchsharedNapiAvailable: Boolean = initLoongarchsharedNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternDbar(p0)")
private external fun wasmCorearchLoongarchsharedExternDbar(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternIbar(p0)")
private external fun wasmCorearchLoongarchsharedExternIbar(p0: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternMovgr2fcsr(p0, p1)")
private external fun wasmCorearchLoongarchsharedExternMovgr2fcsr(p0: Int, p1: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternMovfcsr2gr(p0)")
private external fun wasmCorearchLoongarchsharedExternMovfcsr2gr(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternIocsrrdB(p0)")
private external fun wasmCorearchLoongarchsharedExternIocsrrdB(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternIocsrrdH(p0)")
private external fun wasmCorearchLoongarchsharedExternIocsrrdH(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternIocsrrdW(p0)")
private external fun wasmCorearchLoongarchsharedExternIocsrrdW(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternIocsrwrB(p0, p1)")
private external fun wasmCorearchLoongarchsharedExternIocsrwrB(p0: Int, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternIocsrwrH(p0, p1)")
private external fun wasmCorearchLoongarchsharedExternIocsrwrH(p0: Int, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternIocsrwrW(p0, p1)")
private external fun wasmCorearchLoongarchsharedExternIocsrwrW(p0: Int, p1: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedBreakTarget(p0)")
private external fun wasmCorearchLoongarchsharedBreakTarget(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternCpucfg(p0)")
private external fun wasmCorearchLoongarchsharedExternCpucfg(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternSyscall(p0)")
private external fun wasmCorearchLoongarchsharedExternSyscall(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternFrecipeS(p0)")
private external fun wasmCorearchLoongarchsharedExternFrecipeS(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternFrecipeD(p0)")
private external fun wasmCorearchLoongarchsharedExternFrecipeD(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternFrsqrteS(p0)")
private external fun wasmCorearchLoongarchsharedExternFrsqrteS(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedExternFrsqrteD(p0)")
private external fun wasmCorearchLoongarchsharedExternFrsqrteD(p0: Double): Double

public actual fun externDbar(a: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedExternDbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externIbar(a: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedExternIbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externMovgr2fcsr(a: Int, b: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedExternMovgr2fcsr(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externMovfcsr2gr(a: Int): Int =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedExternMovfcsr2gr(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrrdB(a: Int): Int =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedExternIocsrrdB(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrrdH(a: Int): Int =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedExternIocsrrdH(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrrdW(a: Int): Int =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedExternIocsrrdW(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrwrB(a: Int, b: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedExternIocsrwrB(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externIocsrwrH(a: Int, b: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedExternIocsrwrH(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externIocsrwrW(a: Int, b: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedExternIocsrwrW(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun breakTarget(a: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedBreakTarget(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externCpucfg(a: Int): Int =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedExternCpucfg(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externSyscall(a: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedExternSyscall(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externFrecipeS(a: Float): Float =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedExternFrecipeS(a.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externFrecipeD(a: Double): Double =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedExternFrecipeD(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externFrsqrteS(a: Float): Float =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedExternFrsqrteS(a.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externFrsqrteD(a: Double): Double =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedExternFrsqrteD(a) else throw UnsupportedOperationException("Native FFI addon is not available")
