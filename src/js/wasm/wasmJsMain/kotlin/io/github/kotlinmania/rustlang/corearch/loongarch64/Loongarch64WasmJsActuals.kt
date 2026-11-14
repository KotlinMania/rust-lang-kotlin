@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initLoongarch64NapiAddon(): Boolean

private val loongarch64NapiAvailable: Boolean = initLoongarch64NapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64CrcWBW(p0, p1)")
private external fun wasmCorearchLoongarch64CrcWBW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64CrcWHW(p0, p1)")
private external fun wasmCorearchLoongarch64CrcWHW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64CrcWWW(p0, p1)")
private external fun wasmCorearchLoongarch64CrcWWW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64CrcWDW(p0, p1)")
private external fun wasmCorearchLoongarch64CrcWDW(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64CrccWBW(p0, p1)")
private external fun wasmCorearchLoongarch64CrccWBW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64CrccWHW(p0, p1)")
private external fun wasmCorearchLoongarch64CrccWHW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64CrccWWW(p0, p1)")
private external fun wasmCorearchLoongarch64CrccWWW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64CrccWDW(p0, p1)")
private external fun wasmCorearchLoongarch64CrccWDW(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchLoongarch64Cacop(p0, p1, p2)")
private external fun wasmCorearchLoongarch64Cacop(p0: String, p1: String, p2: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarch64Csrrd(p0)")
private external fun wasmCorearchLoongarch64Csrrd(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64Csrwr(p0, p1)")
private external fun wasmCorearchLoongarch64Csrwr(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.corearchLoongarch64Csrxchg(p0, p1, p2)")
private external fun wasmCorearchLoongarch64Csrxchg(p0: String, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchLoongarch64IocsrrdD(p0)")
private external fun wasmCorearchLoongarch64IocsrrdD(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64IocsrwrD(p0, p1)")
private external fun wasmCorearchLoongarch64IocsrwrD(p0: String, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64Asrtle(p0, p1)")
private external fun wasmCorearchLoongarch64Asrtle(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64Asrtgt(p0, p1)")
private external fun wasmCorearchLoongarch64Asrtgt(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64Lddir(p0, p1)")
private external fun wasmCorearchLoongarch64Lddir(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchLoongarch64Ldpte(p0, p1)")
private external fun wasmCorearchLoongarch64Ldpte(p0: String, p1: String)

public actual fun crcWBW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64CrcWBW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crcWHW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64CrcWHW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crcWWW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64CrcWWW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crcWDW(a: Long, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64CrcWDW(a.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWBW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64CrccWBW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWHW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64CrccWHW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWWW(a: Int, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64CrccWWW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWDW(a: Long, b: Int): Int =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64CrccWDW(a.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

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

public actual fun iocsrrdD(a: Int): Long =
    if (loongarch64NapiAvailable) wasmCorearchLoongarch64IocsrrdD(a).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrwrD(a: Long, b: Int) {
    if (loongarch64NapiAvailable) {
        wasmCorearchLoongarch64IocsrwrD(a.toString(), b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun asrtle(a: Long, b: Long) {
    if (loongarch64NapiAvailable) {
        wasmCorearchLoongarch64Asrtle(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun asrtgt(a: Long, b: Long) {
    if (loongarch64NapiAvailable) {
        wasmCorearchLoongarch64Asrtgt(a.toString(), b.toString())
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
