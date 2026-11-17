@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLoongarch64NapiAddon(): Boolean

private val loongarch64NapiAvailable: Boolean = initLoongarch64NapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCrcWBW(p0, p1)")
private external fun wasmCorearchLoongarch64ExternCrcWBW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCrcWHW(p0, p1)")
private external fun wasmCorearchLoongarch64ExternCrcWHW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCrcWWW(p0, p1)")
private external fun wasmCorearchLoongarch64ExternCrcWWW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCrcWDW(p0, p1)")
private external fun wasmCorearchLoongarch64ExternCrcWDW(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCrccWBW(p0, p1)")
private external fun wasmCorearchLoongarch64ExternCrccWBW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCrccWHW(p0, p1)")
private external fun wasmCorearchLoongarch64ExternCrccWHW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCrccWWW(p0, p1)")
private external fun wasmCorearchLoongarch64ExternCrccWWW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCrccWDW(p0, p1)")
private external fun wasmCorearchLoongarch64ExternCrccWDW(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCacop(p0, p1, p2)")
private external fun wasmCorearchLoongarch64ExternCacop(p0: String, p1: String, p2: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCsrrd(p0)")
private external fun wasmCorearchLoongarch64ExternCsrrd(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCsrwr(p0, p1)")
private external fun wasmCorearchLoongarch64ExternCsrwr(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternCsrxchg(p0, p1, p2)")
private external fun wasmCorearchLoongarch64ExternCsrxchg(p0: String, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternIocsrrdD(p0)")
private external fun wasmCorearchLoongarch64ExternIocsrrdD(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternIocsrwrD(p0, p1)")
private external fun wasmCorearchLoongarch64ExternIocsrwrD(p0: String, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternAsrtle(p0, p1)")
private external fun wasmCorearchLoongarch64ExternAsrtle(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternAsrtgt(p0, p1)")
private external fun wasmCorearchLoongarch64ExternAsrtgt(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternLddir(p0, p1)")
private external fun wasmCorearchLoongarch64ExternLddir(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternLdpte(p0, p1)")
private external fun wasmCorearchLoongarch64ExternLdpte(p0: String, p1: String)

public actual fun externCrcWBW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCrcWBW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrcWHW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCrcWHW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrcWWW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCrcWWW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrcWDW(a: Long, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCrcWDW(a.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrccWBW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCrccWBW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrccWHW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCrccWHW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrccWWW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCrccWWW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCrccWDW(a: Long, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCrccWDW(a.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCacop(a: Long, b: Long, c: Long) {
    if (loongarch64NapiAvailable) {
        wasmCorearchLoongarch64ExternCacop(a.toString(), b.toString(), c.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externCsrrd(a: Int): Long =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCsrrd(a).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCsrwr(a: Long, b: Int): Long =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCsrwr(a.toString(), b).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externCsrxchg(a: Long, b: Long, c: Int): Long =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternCsrxchg(a.toString(), b.toString(), c).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrrdD(a: Int): Long =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternIocsrrdD(a).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externIocsrwrD(a: Long, b: Int) {
    if (loongarch64NapiAvailable) {
        wasmCorearchLoongarch64ExternIocsrwrD(a.toString(), b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externAsrtle(a: Long, b: Long) {
    if (loongarch64NapiAvailable) {
        wasmCorearchLoongarch64ExternAsrtle(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externAsrtgt(a: Long, b: Long) {
    if (loongarch64NapiAvailable) {
        wasmCorearchLoongarch64ExternAsrtgt(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externLddir(a: Long, b: Long): Long =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64ExternLddir(a.toString(), b.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externLdpte(a: Long, b: Long) {
    if (loongarch64NapiAvailable) {
        wasmCorearchLoongarch64ExternLdpte(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
