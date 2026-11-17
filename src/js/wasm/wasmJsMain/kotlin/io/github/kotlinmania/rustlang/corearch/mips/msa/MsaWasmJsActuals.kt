@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

@JsFun("() => { if (typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_kmcore = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_kmcore = null; return false; } try { globalThis.__km_napi_kmcore = req('@kotlinmania/kmcore-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_kmcore = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_kmcore = null; return false; }")
private external fun initMipsMsaNapiAddon(): Boolean

private val mipsMsaNapiAvailable: Boolean = initMipsMsaNapiAddon()

@JsFun("(p0) => globalThis.__km_napi_kmcore.corearchMipsMsaMsaCfcmsa(p0)")
private external fun wasmCorearchMipsMsaMsaCfcmsa(p0: Int): Int

@JsFun("(p0, p1) => globalThis.__km_napi_kmcore.corearchMipsMsaMsaCtcmsa(p0, p1)")
private external fun wasmCorearchMipsMsaMsaCtcmsa(p0: Int, p1: Int)

public actual fun externMsaAddAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddsUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAddvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddviB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddviH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddviW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddviD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAndV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAndiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAsubSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAsubSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAsubSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAsubSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAsubUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAsubUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAsubUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAsubUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAveSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAveSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAveSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAveSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAveUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAveUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAveUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAveUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAverSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAverSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAverSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAverSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAverUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAverUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAverUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaAverUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBclrB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBclrH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBclrW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBclrD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclriH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclriW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclriD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBinslB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBinslH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBinslW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBinslD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsliH(a: V8u16, b: V8u16, c: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsliW(a: V4u32, b: V4u32, c: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsliD(a: V2u64, b: V2u64, c: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBinsrB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBinsrH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBinsrW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBinsrD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsriB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsriH(a: V8u16, b: V8u16, c: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsriW(a: V4u32, b: V4u32, c: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsriD(a: V2u64, b: V2u64, c: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBmnzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBmnziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBmzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBmziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBnegB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBnegH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBnegW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBnegD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegiH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegiW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegiD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBnzB(a: V16u8): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBnzH(a: V8u16): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBnzW(a: V4u32): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBnzD(a: V2u64): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBnzV(a: V16u8): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBselV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBseliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBsetB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBsetH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBsetW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBsetD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetiH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetiW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetiD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBzB(a: V16u8): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBzH(a: V8u16): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBzW(a: V4u32): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBzD(a: V2u64): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaBzV(a: V16u8): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCeqB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCeqH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCeqW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCeqD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCeqiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCeqiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCeqiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCeqiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCfcmsa(a: Int): Int =
    if (mipsMsaNapiAvailable) wasmCorearchMipsMsaMsaCfcmsa(a) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCleSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCleSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCleSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCleSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCleUB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCleUH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCleUW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCleUD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleiUB(a: V16u8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleiUH(a: V8u16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleiUW(a: V4u32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleiUD(a: V2u64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCltSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCltSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCltSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCltSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCltUB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCltUH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCltUW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaCltUD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltiUB(a: V16u8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltiUH(a: V8u16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltiUW(a: V4u32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltiUD(a: V2u64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCopySB(a: V16i8, b: Int): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCopySH(a: V8i16, b: Int): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCopySW(a: V4i32, b: Int): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCopySD(a: V2i64, b: Int): Long =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCopyUB(a: V16i8, b: Int): UInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCopyUH(a: V8i16, b: Int): UInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCopyUW(a: V4i32, b: Int): UInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCopyUD(a: V2i64, b: Int): ULong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCtcmsa(imm5: Int, a: Int) {
    if (mipsMsaNapiAvailable) {
        wasmCorearchMipsMsaMsaCtcmsa(imm5, a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun externMsaDivSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDivSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDivSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDivSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDivUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDivUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDivUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDivUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDotpSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDotpSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDotpSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDotpUH(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDotpUW(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDotpUD(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpaddSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpaddSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpaddSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpaddUH(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpaddUW(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpaddUD(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpsubSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpsubSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpsubSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpsubUH(a: V8i16, b: V16u8, c: V16u8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpsubUW(a: V4i32, b: V8u16, c: V8u16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaDpsubUD(a: V2i64, b: V4u32, c: V4u32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFaddW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFaddD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcafW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFceqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFceqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFclassW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFclassD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcltW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcorW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcueqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFculeW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFculeD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcultW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcunW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcuneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFcuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFdivW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFdivD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFexdoW(a: V2f64, b: V2f64): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFexp2W(a: V4f32, b: V4i32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFexp2D(a: V2f64, b: V2i64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFexuplD(a: V4f32): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFexuprD(a: V4f32): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFfintSW(a: V4i32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFfintSD(a: V2i64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFfintUW(a: V4u32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFfintUD(a: V2u64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFfqlW(a: V8i16): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFfqlD(a: V4i32): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFfqrW(a: V8i16): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFfqrD(a: V4i32): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFillB(a: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFillH(a: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFillW(a: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFillD(a: Long): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFlog2W(a: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFlog2D(a: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFmaddW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFmaddD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFmaxW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFmaxD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFmaxAW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFmaxAD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFminW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFminD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFminAW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFminAD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFmsubW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFmsubD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFmulW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFmulD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFrintW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFrintD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFrcpW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFrcpD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFrsqrtW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFrsqrtD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsafW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFseqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFseqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsltW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsorW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsqrtW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsqrtD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsubW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsubD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsueqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsuleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsuleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsultW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsunW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsuneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFsuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFtintSW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFtintSD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFtintUW(a: V4f32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFtintUD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFtqH(a: V4f32, b: V4f32): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFtqW(a: V2f64, b: V2f64): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFtruncSW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFtruncSD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFtruncUW(a: V4f32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaFtruncUD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHaddSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHaddSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHaddSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHaddUH(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHaddUW(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHaddUD(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHsubSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHsubSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHsubSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHsubUH(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHsubUW(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaHsubUD(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvevB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvevH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvevW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvevD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvlB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvlH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvlW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvlD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaIlvrD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaInsertB(a: V16i8, b: Int, c: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaInsertH(a: V8i16, b: Int, c: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaInsertW(a: V4i32, b: Int, c: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaInsertD(a: V2i64, b: Int, c: Long): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaInsveB(a: V16i8, b: Int, c: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaInsveH(a: V8i16, b: Int, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaInsveW(a: V4i32, b: Int, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaInsveD(a: V2i64, b: Int, c: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaLdB(memAddr: MutPtr, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaLdH(memAddr: MutPtr, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaLdW(memAddr: MutPtr, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaLdD(memAddr: MutPtr, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaLdiB(a: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaLdiH(a: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaLdiW(a: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaLdiD(a: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaddQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaddQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaddrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaddrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaddvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaddvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaddvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaddvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMaxUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxiSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxiSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxiSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxiSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxiUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxiUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxiUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxiUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMinUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMiniSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMiniSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMiniSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMiniSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMiniUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMiniUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMiniUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMiniUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaModSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaModSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaModSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaModSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaModUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaModUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaModUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaModUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMoveV(a: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMsubQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMsubQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMsubrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMsubrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMsubvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMsubvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMsubvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMsubvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMulQH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMulQW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMulrQH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMulrQW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMulvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMulvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMulvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaMulvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaNlocB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaNlocH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaNlocW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaNlocD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaNlzcB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaNlzcH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaNlzcW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaNlzcD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaNorV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNoriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaOrV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaOriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPckevB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPckevH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPckevW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPckevD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPckodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPckodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPckodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPckodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPcntB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPcntH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPcntW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaPcntD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSatSB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSatSH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSatSW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSatSD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSatUB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSatUH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSatUW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSatUD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaShfB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaShfH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaShfW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSldB(a: V16i8, b: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSldH(a: V8i16, b: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSldW(a: V4i32, b: V4i32, c: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSldD(a: V2i64, b: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldiB(a: V16i8, b: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldiH(a: V8i16, b: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldiW(a: V4i32, b: V4i32, c: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldiD(a: V2i64, b: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSllB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSllH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSllW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSllD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSlliB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSlliH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSlliW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSlliD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSplatB(a: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSplatH(a: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSplatW(a: V4i32, w: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSplatD(a: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSraB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSraH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSraW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSraD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrarB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrarH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrarW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrarD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrariB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrariH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrariW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrariD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrlB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrlH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrlW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrlD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrliB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrliH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrliW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrliD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrlrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrlrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrlrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSrlrD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlriB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlriH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlriW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlriD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaStB(a: V16i8, memAddr: MutPtr, immS10: Int) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun msaStH(a: V8i16, memAddr: MutPtr, immS11: Int) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun msaStW(a: V4i32, memAddr: MutPtr, immS12: Int) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun msaStD(a: V2i64, memAddr: MutPtr, immS13: Int) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun externMsaSubsSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsusUB(a: V16u8, b: V16i8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsusUH(a: V8u16, b: V8i16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsusUW(a: V4u32, b: V4i32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsusUD(a: V2u64, b: V2i64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsuuSB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsuuSH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsuuSW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubsuuSD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaSubvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubviB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubviH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubviW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubviD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaVshfB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaVshfH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaVshfW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaVshfD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun externMsaXorV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaXoriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")
