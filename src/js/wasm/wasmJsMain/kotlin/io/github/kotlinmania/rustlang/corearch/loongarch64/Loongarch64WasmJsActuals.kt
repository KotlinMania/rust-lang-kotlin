@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.loongarch64

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initCorearchLoongarch64NapiAddon(): Boolean

private val corearchLoongarch64NapiAvailable: Boolean = initCorearchLoongarch64NapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64CrcWBW(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64CrcWBW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64CrcWHW(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64CrcWHW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64CrcWWW(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64CrcWWW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64CrcWDW(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64CrcWDW(p0: String, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64CrccWBW(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64CrccWBW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64CrccWHW(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64CrccWHW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64CrccWWW(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64CrccWWW(p0: Int, p1: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64CrccWDW(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64CrccWDW(p0: String, p1: Int): Int

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64Cacop(p0, p1, p2)")
private external fun wasmRustlangCorearchLoongarch64Cacop(p0: String, p1: String, p2: String)

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64Csrrd(p0)")
private external fun wasmRustlangCorearchLoongarch64Csrrd(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64Csrwr(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64Csrwr(p0: String, p1: Int): String

@JsFun("(p0, p1, p2) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64Csrxchg(p0, p1, p2)")
private external fun wasmRustlangCorearchLoongarch64Csrxchg(p0: String, p1: String, p2: Int): String

@JsFun("(p0) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64IocsrrdD(p0)")
private external fun wasmRustlangCorearchLoongarch64IocsrrdD(p0: Int): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64IocsrwrD(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64IocsrwrD(p0: String, p1: Int)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64Asrtle(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64Asrtle(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64Asrtgt(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64Asrtgt(p0: String, p1: String)

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64Lddir(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64Lddir(p0: String, p1: String): String

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.rustlangCorearchLoongarch64Ldpte(p0, p1)")
private external fun wasmRustlangCorearchLoongarch64Ldpte(p0: String, p1: String)

public actual fun crcWBW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64CrcWBW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crcWHW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64CrcWHW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crcWWW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64CrcWWW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crcWDW(a: Long, b: Int): Int =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64CrcWDW(a.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWBW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64CrccWBW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWHW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64CrccWHW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWWW(a: Int, b: Int): Int =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64CrccWWW(a, b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun crccWDW(a: Long, b: Int): Int =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64CrccWDW(a.toString(), b) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun cacop(a: Long, b: Long, c: Long) {
    if (corearchLoongarch64NapiAvailable) {
        wasmRustlangCorearchLoongarch64Cacop(a.toString(), b.toString(), c.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun csrrd(a: Int): Long =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64Csrrd(a).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun csrwr(a: Long, b: Int): Long =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64Csrwr(a.toString(), b).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun csrxchg(a: Long, b: Long, c: Int): Long =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64Csrxchg(a.toString(), b.toString(), c).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrrdD(a: Int): Long =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64IocsrrdD(a).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun iocsrwrD(a: Long, b: Int) {
    if (corearchLoongarch64NapiAvailable) {
        wasmRustlangCorearchLoongarch64IocsrwrD(a.toString(), b)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun asrtle(a: Long, b: Long) {
    if (corearchLoongarch64NapiAvailable) {
        wasmRustlangCorearchLoongarch64Asrtle(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun asrtgt(a: Long, b: Long) {
    if (corearchLoongarch64NapiAvailable) {
        wasmRustlangCorearchLoongarch64Asrtgt(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun lddir(a: Long, b: Long): Long =
    if (corearchLoongarch64NapiAvailable) wasmRustlangCorearchLoongarch64Lddir(a.toString(), b.toString()).toLong() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun ldpte(a: Long, b: Long) {
    if (corearchLoongarch64NapiAvailable) {
        wasmRustlangCorearchLoongarch64Ldpte(a.toString(), b.toString())
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}
