@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarchshared

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCorearchLoongarchsharedNapiAddon(): Boolean

private val corearchLoongarchsharedNapiAvailable: Boolean = initCorearchLoongarchsharedNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedDbar(p0)")
private external fun wasmRustlangCorearchLoongarchsharedDbar(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedIbar(p0)")
private external fun wasmRustlangCorearchLoongarchsharedIbar(p0: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedMovgr2fcsr(p0, p1)")
private external fun wasmRustlangCorearchLoongarchsharedMovgr2fcsr(p0: Int, p1: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedMovfcsr2gr(p0)")
private external fun wasmRustlangCorearchLoongarchsharedMovfcsr2gr(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedIocsrrdB(p0)")
private external fun wasmRustlangCorearchLoongarchsharedIocsrrdB(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedIocsrrdH(p0)")
private external fun wasmRustlangCorearchLoongarchsharedIocsrrdH(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedIocsrrdW(p0)")
private external fun wasmRustlangCorearchLoongarchsharedIocsrrdW(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedIocsrwrB(p0, p1)")
private external fun wasmRustlangCorearchLoongarchsharedIocsrwrB(p0: Int, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedIocsrwrH(p0, p1)")
private external fun wasmRustlangCorearchLoongarchsharedIocsrwrH(p0: Int, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedIocsrwrW(p0, p1)")
private external fun wasmRustlangCorearchLoongarchsharedIocsrwrW(p0: Int, p1: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedBreakTarget(p0)")
private external fun wasmRustlangCorearchLoongarchsharedBreakTarget(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedCpucfg(p0)")
private external fun wasmRustlangCorearchLoongarchsharedCpucfg(p0: Int): Int

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedSyscall(p0)")
private external fun wasmRustlangCorearchLoongarchsharedSyscall(p0: Int)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedFrecipeS(p0)")
private external fun wasmRustlangCorearchLoongarchsharedFrecipeS(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedFrecipeD(p0)")
private external fun wasmRustlangCorearchLoongarchsharedFrecipeD(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedFrsqrteS(p0)")
private external fun wasmRustlangCorearchLoongarchsharedFrsqrteS(p0: Double): Double

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarchsharedFrsqrteD(p0)")
private external fun wasmRustlangCorearchLoongarchsharedFrsqrteD(p0: Double): Double

public actual fun dbar(a: Int) {
    if (corearchLoongarchsharedNapiAvailable) {
        wasmRustlangCorearchLoongarchsharedDbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun ibar(a: Int) {
    if (corearchLoongarchsharedNapiAvailable) {
        wasmRustlangCorearchLoongarchsharedIbar(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun movgr2fcsr(a: Int, b: Int) {
    if (corearchLoongarchsharedNapiAvailable) {
        wasmRustlangCorearchLoongarchsharedMovgr2fcsr(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun movfcsr2gr(a: Int): Int =
    if (corearchLoongarchsharedNapiAvailable) wasmRustlangCorearchLoongarchsharedMovfcsr2gr(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdB(a: Int): Int =
    if (corearchLoongarchsharedNapiAvailable) wasmRustlangCorearchLoongarchsharedIocsrrdB(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdH(a: Int): Int =
    if (corearchLoongarchsharedNapiAvailable) wasmRustlangCorearchLoongarchsharedIocsrrdH(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdW(a: Int): Int =
    if (corearchLoongarchsharedNapiAvailable) wasmRustlangCorearchLoongarchsharedIocsrrdW(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrwrB(a: Int, b: Int) {
    if (corearchLoongarchsharedNapiAvailable) {
        wasmRustlangCorearchLoongarchsharedIocsrwrB(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun iocsrwrH(a: Int, b: Int) {
    if (corearchLoongarchsharedNapiAvailable) {
        wasmRustlangCorearchLoongarchsharedIocsrwrH(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun iocsrwrW(a: Int, b: Int) {
    if (corearchLoongarchsharedNapiAvailable) {
        wasmRustlangCorearchLoongarchsharedIocsrwrW(a, b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun breakTarget(a: Int) {
    if (corearchLoongarchsharedNapiAvailable) {
        wasmRustlangCorearchLoongarchsharedBreakTarget(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun cpucfg(a: Int): Int =
    if (corearchLoongarchsharedNapiAvailable) wasmRustlangCorearchLoongarchsharedCpucfg(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun syscall(a: Int) {
    if (corearchLoongarchsharedNapiAvailable) {
        wasmRustlangCorearchLoongarchsharedSyscall(a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun frecipeS(a: Float): Float =
    if (corearchLoongarchsharedNapiAvailable) wasmRustlangCorearchLoongarchsharedFrecipeS(a.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frecipeD(a: Double): Double =
    if (corearchLoongarchsharedNapiAvailable) wasmRustlangCorearchLoongarchsharedFrecipeD(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frsqrteS(a: Float): Float =
    if (corearchLoongarchsharedNapiAvailable) wasmRustlangCorearchLoongarchsharedFrsqrteS(a.toDouble()).toFloat() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun frsqrteD(a: Double): Double =
    if (corearchLoongarchsharedNapiAvailable) wasmRustlangCorearchLoongarchsharedFrsqrteD(a) else throw UnsupportedOperationException("Native FFI addon is not available")
