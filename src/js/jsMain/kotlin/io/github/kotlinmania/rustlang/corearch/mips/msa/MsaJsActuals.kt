@file:OptIn(ExperimentalUnsignedTypes::class)

package io.github.kotlinmania.rustlang.corearch.mips.msa

import io.github.kotlinmania.rustlang.core.ptr.MutPtr

private val mipsMsaNapiAddon: dynamic =
    js("(function() { if (typeof globalThis !== 'undefined' && typeof globalThis.__km_napi_kmcore !== 'undefined') return globalThis.__km_napi_kmcore; if (typeof process === 'undefined' || !process.versions || !process.versions.node) return null; var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) return null; try { var pkg = req('@kotlinmania/kmcore-native-bindings'); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = pkg; return pkg; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['kmcore_native.node', 'build/node-ffi/kmcore_native.node', 'build/node-ffi/release/kmcore_native.node', 'build/node-ffi/debug/kmcore_native.node', 'src/js/node-kmcore/build/Release/kmcore_native.node', 'src/js/node-kmcore/build/Debug/kmcore_native.node', 'src/js/node-kmcore/dist/build/Release/kmcore_native.node', 'src/js/node-kmcore/dist/build/Debug/kmcore_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { var loaded = req(c); if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = loaded; return loaded; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } if (typeof globalThis !== 'undefined') globalThis.__km_napi_kmcore = null; return null; })()")

public actual fun msaAddAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddsUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddviB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddviH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddviW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAddviD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAndV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAndiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAsubSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAsubSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAsubSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAsubSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAsubUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAsubUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAsubUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAsubUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAveSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAveSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAveSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAveSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAveUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAveUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAveUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAveUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAverSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAverSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAverSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAverSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAverUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAverUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAverUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaAverUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclrB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclrH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclrW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclrD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclriH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclriW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBclriD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinslB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinslH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinslW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinslD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsliH(a: V8u16, b: V8u16, c: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsliW(a: V4u32, b: V4u32, c: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsliD(a: V2u64, b: V2u64, c: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsrB(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsrH(a: V8u16, b: V8u16, c: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsrW(a: V4u32, b: V4u32, c: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsrD(a: V2u64, b: V2u64, c: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsriB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsriH(a: V8u16, b: V8u16, c: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsriW(a: V4u32, b: V4u32, c: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBinsriD(a: V2u64, b: V2u64, c: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBmnzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBmnziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBmzV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBmziB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegiH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegiW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnegiD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnzB(a: V16u8): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnzH(a: V8u16): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnzW(a: V4u32): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnzD(a: V2u64): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBnzV(a: V16u8): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBselV(a: V16u8, b: V16u8, c: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBseliB(a: V16u8, b: V16u8, c: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetiB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetiH(a: V8u16, b: Int): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetiW(a: V4u32, b: Int): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBsetiD(a: V2u64, b: Int): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBzB(a: V16u8): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBzH(a: V8u16): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBzW(a: V4u32): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBzD(a: V2u64): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaBzV(a: V16u8): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCeqB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCeqH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCeqW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCeqD(a: V2i64, b: V2i64): V2i64 =
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
    if (mipsMsaNapiAddon != null) (mipsMsaNapiAddon.corearchMipsMsaMsaCfcmsa(a) as Int) else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleUB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleUH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleUW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCleUD(a: V2u64, b: V2u64): V2i64 =
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

public actual fun msaCltSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltUB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltUH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltUW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaCltUD(a: V2u64, b: V2u64): V2i64 =
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
    if (mipsMsaNapiAddon != null) {
        mipsMsaNapiAddon.corearchMipsMsaMsaCtcmsa(imm5, a)
    } else {
        throw UnsupportedOperationException("Native FFI addon is not available")
    }
}

public actual fun msaDivSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDivSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDivSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDivSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDivUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDivUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDivUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDivUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDotpSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDotpSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDotpSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDotpUH(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDotpUW(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDotpUD(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpaddSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpaddSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpaddSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpaddUH(a: V8u16, b: V16u8, c: V16u8): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpaddUW(a: V4u32, b: V8u16, c: V8u16): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpaddUD(a: V2u64, b: V4u32, c: V4u32): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpsubSH(a: V8i16, b: V16i8, c: V16i8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpsubSW(a: V4i32, b: V8i16, c: V8i16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpsubSD(a: V2i64, b: V4i32, c: V4i32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpsubUH(a: V8i16, b: V16u8, c: V16u8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpsubUW(a: V4i32, b: V8u16, c: V8u16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaDpsubUD(a: V2i64, b: V4u32, c: V4u32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFaddW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFaddD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcafW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFceqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFceqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFclassW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFclassD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcltW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcorW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcueqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFculeW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFculeD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcultW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcunW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcuneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFcuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFdivW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFdivD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFexdoW(a: V2f64, b: V2f64): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFexp2W(a: V4f32, b: V4i32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFexp2D(a: V2f64, b: V2i64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFexuplD(a: V4f32): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFexuprD(a: V4f32): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFfintSW(a: V4i32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFfintSD(a: V2i64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFfintUW(a: V4u32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFfintUD(a: V2u64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFfqlW(a: V8i16): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFfqlD(a: V4i32): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFfqrW(a: V8i16): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFfqrD(a: V4i32): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFillB(a: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFillH(a: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFillW(a: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFillD(a: Long): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFlog2W(a: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFlog2D(a: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFmaddW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFmaddD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFmaxW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFmaxD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFmaxAW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFmaxAD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFminW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFminD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFminAW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFminAD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFmsubW(a: V4f32, b: V4f32, c: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFmsubD(a: V2f64, b: V2f64, c: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFmulW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFmulD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFrintW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFrintD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFrcpW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFrcpD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFrsqrtW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFrsqrtD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsafW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsafD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFseqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFseqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsltW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsltD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsorW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsorD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsqrtW(a: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsqrtD(a: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsubW(a: V4f32, b: V4f32): V4f32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsubD(a: V2f64, b: V2f64): V2f64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsueqW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsueqD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsuleW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsuleD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsultW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsultD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsunW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsunD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsuneW(a: V4f32, b: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFsuneD(a: V2f64, b: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFtintSW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFtintSD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFtintUW(a: V4f32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFtintUD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFtqH(a: V4f32, b: V4f32): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFtqW(a: V2f64, b: V2f64): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFtruncSW(a: V4f32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFtruncSD(a: V2f64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFtruncUW(a: V4f32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaFtruncUD(a: V2f64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHaddSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHaddSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHaddSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHaddUH(a: V16u8, b: V16u8): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHaddUW(a: V8u16, b: V8u16): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHaddUD(a: V4u32, b: V4u32): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHsubSH(a: V16i8, b: V16i8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHsubSW(a: V8i16, b: V8i16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHsubSD(a: V4i32, b: V4i32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHsubUH(a: V16u8, b: V16u8): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHsubUW(a: V8u16, b: V8u16): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaHsubUD(a: V4u32, b: V4u32): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvevB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvevH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvevW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvevD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvlB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvlH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvlW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvlD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaIlvrD(a: V2i64, b: V2i64): V2i64 =
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

public actual fun msaMaddQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaddQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaddrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaddrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaddvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaddvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaddvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaddvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMaxUD(a: V2u64, b: V2u64): V2u64 =
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

public actual fun msaMinAB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinAH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinAW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinAD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMinUD(a: V2u64, b: V2u64): V2u64 =
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

public actual fun msaModSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaModSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaModSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaModSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaModUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaModUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaModUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaModUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMoveV(a: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMsubQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMsubQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMsubrQH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMsubrQW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMsubvB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMsubvH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMsubvW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMsubvD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMulQH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMulQW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMulrQH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMulrQW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMulvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMulvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMulvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaMulvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNlocB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNlocH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNlocW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNlocD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNlzcB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNlzcH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNlzcW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNlzcD(a: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNorV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaNoriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaOrV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaOriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPckevB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPckevH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPckevW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPckevD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPckodB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPckodH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPckodW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPckodD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPcntB(a: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPcntH(a: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPcntW(a: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaPcntD(a: V2i64): V2i64 =
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

public actual fun msaSldB(a: V16i8, b: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldH(a: V8i16, b: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldW(a: V4i32, b: V4i32, c: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldD(a: V2i64, b: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldiB(a: V16i8, b: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldiH(a: V8i16, b: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldiW(a: V4i32, b: V4i32, c: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSldiD(a: V2i64, b: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSllB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSllH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSllW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSllD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSlliB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSlliH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSlliW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSlliD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatB(a: V16i8, c: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatH(a: V8i16, c: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatW(a: V4i32, w: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatD(a: V2i64, c: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSplatiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraiB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraiH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraiW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSraiD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrarB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrarH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrarW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrarD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrariB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrariH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrariW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrariD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrliB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrliH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrliW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrliD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlrB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlrH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlrW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSrlrD(a: V2i64, b: V2i64): V2i64 =
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

public actual fun msaSubsSB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsSH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsSW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsSD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsUB(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsUH(a: V8u16, b: V8u16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsUW(a: V4u32, b: V4u32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsUD(a: V2u64, b: V2u64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsusUB(a: V16u8, b: V16i8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsusUH(a: V8u16, b: V8i16): V8u16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsusUW(a: V4u32, b: V4i32): V4u32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsusUD(a: V2u64, b: V2i64): V2u64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsuuSB(a: V16u8, b: V16u8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsuuSH(a: V8u16, b: V8u16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsuuSW(a: V4u32, b: V4u32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubsuuSD(a: V2u64, b: V2u64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubvB(a: V16i8, b: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubvH(a: V8i16, b: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubvW(a: V4i32, b: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubvD(a: V2i64, b: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubviB(a: V16i8, b: Int): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubviH(a: V8i16, b: Int): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubviW(a: V4i32, b: Int): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaSubviD(a: V2i64, b: Int): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaVshfB(a: V16i8, b: V16i8, c: V16i8): V16i8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaVshfH(a: V8i16, b: V8i16, c: V8i16): V8i16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaVshfW(a: V4i32, b: V4i32, c: V4i32): V4i32 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaVshfD(a: V2i64, b: V2i64, c: V2i64): V2i64 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaXorV(a: V16u8, b: V16u8): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun msaXoriB(a: V16u8, b: Int): V16u8 =
    throw UnsupportedOperationException("Native FFI addon is not available")
