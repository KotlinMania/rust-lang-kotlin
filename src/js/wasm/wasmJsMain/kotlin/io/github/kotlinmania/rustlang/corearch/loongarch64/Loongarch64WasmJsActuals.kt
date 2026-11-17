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

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchLoongarch64Cacop(p0, p1, p2)")
private external fun wasmCorearchLoongarch64Cacop(p0: String, p1: String, p2: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarch64Csrrd(p0)")
private external fun wasmCorearchLoongarch64Csrrd(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64Csrwr(p0, p1)")
private external fun wasmCorearchLoongarch64Csrwr(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchLoongarch64Csrxchg(p0, p1, p2)")
private external fun wasmCorearchLoongarch64Csrxchg(p0: String, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternIocsrrdD(p0)")
private external fun wasmCorearchLoongarch64ExternIocsrrdD(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternIocsrwrD(p0, p1)")
private external fun wasmCorearchLoongarch64ExternIocsrwrD(p0: String, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternAsrtle(p0, p1)")
private external fun wasmCorearchLoongarch64ExternAsrtle(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64ExternAsrtgt(p0, p1)")
private external fun wasmCorearchLoongarch64ExternAsrtgt(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64Lddir(p0, p1)")
private external fun wasmCorearchLoongarch64Lddir(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64Ldpte(p0, p1)")
private external fun wasmCorearchLoongarch64Ldpte(p0: String, p1: String)

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

public actual fun cacop(a: Long, b: Long, c: Long) {
    if (loongarch64NapiAvailable) {
        wasmCorearchLoongarch64Cacop(a.toString(), b.toString(), c.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun csrrd(a: Int): Long =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64Csrrd(a).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun csrwr(a: Long, b: Int): Long =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64Csrwr(a.toString(), b).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64Csrxchg(a.toString(), b.toString(), c).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

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

public actual fun lddir(a: Long, b: Long): Long =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64Lddir(a.toString(), b.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldpte(a: Long, b: Long) {
    if (loongarch64NapiAvailable) {
        wasmCorearchLoongarch64Ldpte(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
