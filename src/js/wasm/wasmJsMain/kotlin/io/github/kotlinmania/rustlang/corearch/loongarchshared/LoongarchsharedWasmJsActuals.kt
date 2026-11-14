@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLoongarchsharedNapiAddon(): Boolean

private val loongarchsharedNapiAvailable: Boolean = initLoongarchsharedNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedDbar(p0)")
private external fun wasmCorearchLoongarchsharedDbar(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedIbar(p0)")
private external fun wasmCorearchLoongarchsharedIbar(p0: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarchsharedMovgr2fcsr(p0, p1)")
private external fun wasmCorearchLoongarchsharedMovgr2fcsr(p0: Int, p1: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedMovfcsr2gr(p0)")
private external fun wasmCorearchLoongarchsharedMovfcsr2gr(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedIocsrrdB(p0)")
private external fun wasmCorearchLoongarchsharedIocsrrdB(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedIocsrrdH(p0)")
private external fun wasmCorearchLoongarchsharedIocsrrdH(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedIocsrrdW(p0)")
private external fun wasmCorearchLoongarchsharedIocsrrdW(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarchsharedIocsrwrB(p0, p1)")
private external fun wasmCorearchLoongarchsharedIocsrwrB(p0: Int, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarchsharedIocsrwrH(p0, p1)")
private external fun wasmCorearchLoongarchsharedIocsrwrH(p0: Int, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarchsharedIocsrwrW(p0, p1)")
private external fun wasmCorearchLoongarchsharedIocsrwrW(p0: Int, p1: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedBreakTarget(p0)")
private external fun wasmCorearchLoongarchsharedBreakTarget(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedCpucfg(p0)")
private external fun wasmCorearchLoongarchsharedCpucfg(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedSyscall(p0)")
private external fun wasmCorearchLoongarchsharedSyscall(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedFrecipeS(p0)")
private external fun wasmCorearchLoongarchsharedFrecipeS(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedFrecipeD(p0)")
private external fun wasmCorearchLoongarchsharedFrecipeD(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedFrsqrteS(p0)")
private external fun wasmCorearchLoongarchsharedFrsqrteS(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarchsharedFrsqrteD(p0)")
private external fun wasmCorearchLoongarchsharedFrsqrteD(p0: Double): Double

public actual fun dbar(a: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedDbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun ibar(a: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedIbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun movgr2fcsr(a: Int, b: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedMovgr2fcsr(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun movfcsr2gr(a: Int): Int =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedMovfcsr2gr(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdB(a: Int): Int =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedIocsrrdB(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdH(a: Int): Int =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedIocsrrdH(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdW(a: Int): Int =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedIocsrrdW(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrwrB(a: Int, b: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedIocsrwrB(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun iocsrwrH(a: Int, b: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedIocsrwrH(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun iocsrwrW(a: Int, b: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedIocsrwrW(a, b)
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

public actual fun cpucfg(a: Int): Int =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedCpucfg(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(a: Int) {
    if (loongarchsharedNapiAvailable) {
        wasmCorearchLoongarchsharedSyscall(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun frecipeS(a: Float): Float =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedFrecipeS(a.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frecipeD(a: Double): Double =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedFrecipeD(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frsqrteS(a: Float): Float =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedFrsqrteS(a.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frsqrteD(a: Double): Double =
    if (loongarchsharedNapiAvailable) wasmCorearchLoongarchsharedFrsqrteD(a) else throw UnsupportedOperationException("Native FFI addon is not available")
