@file:OptIn(kotlin.ExperimentalUnsignedTypes::class, kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlang.corearch.s390x.vector

import io.github.kotlinmania.rustlang.core.U128
import io.github.kotlinmania.rustlang.core.mem.maybeuninit.MaybeUninit
import io.github.kotlinmania.rustlang.core.ptr.ConstPtr
import io.github.kotlinmania.rustlang.core.ptr.MutPtr
import io.github.kotlinmania.rustlang.corearch.simd.I16x8
import io.github.kotlinmania.rustlang.corearch.simd.I32x4
import io.github.kotlinmania.rustlang.corearch.simd.I8x16

@JsFun("() => { if (typeof globalThis.__km_napi_corearch !== 'undefined') return globalThis.__km_napi_corearch !== null; if (typeof process === 'undefined' || !process.versions || !process.versions.node) { globalThis.__km_napi_corearch = null; return false; } var mod = process.getBuiltinModule ? process.getBuiltinModule('module') : (process.mainModule && process.mainModule.constructor); var req = (mod && mod.createRequire) ? mod.createRequire((process.cwd ? process.cwd() : '.') + '/package.json') : (process.mainModule ? process.mainModule.require : null); if (!req) { globalThis.__km_napi_corearch = null; return false; } try { globalThis.__km_napi_corearch = req('@kotlinmania/corearch-native-bindings'); return true; } catch (_) {} var fs = req('fs'); var path = req('path'); var dir = process.cwd ? process.cwd() : '.'; var rels = ['corearch_native.node', 'build/node-ffi/corearch_native.node', 'build/node-ffi/release/corearch_native.node', 'build/node-ffi/debug/corearch_native.node', 'src/js/node-corearch/build/Release/corearch_native.node', 'src/js/node-corearch/build/Debug/corearch_native.node', 'src/js/node-corearch/dist/build/Release/corearch_native.node', 'src/js/node-corearch/dist/build/Debug/corearch_native.node']; for (var d = 0; d < 8; d++) { for (var i = 0; i < rels.length; i++) { var c = path.resolve(dir, rels[i]); if (fs.existsSync(c)) { globalThis.__km_napi_corearch = req(c); return true; } } var parent = path.dirname(dir); if (parent === dir) break; dir = parent; } globalThis.__km_napi_corearch = null; return false; }")
private external fun initS390xVectorNapiAddon(): Boolean

private val s390xVectorNapiAvailable: Boolean = initS390xVectorNapiAddon()

@JsFun("(p0, p1) => globalThis.__km_napi_corearch.corearchS390xVectorLcbb(p0, p1)")
private external fun wasmCorearchS390xVectorLcbb(p0: String, p1: Int): Int

public actual fun nearbyintV4f32(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun nearbyintV2f64(a: VectorDouble): VectorDouble =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenV4f32(a: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun roundevenV2f64(a: VectorDouble): VectorDouble =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsra(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsrl(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsl(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsrab(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsrlb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vslb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun verimb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar, d: Int): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun verimh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort, d: Int): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun verimf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt, d: Int): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun verimg(a: VectorSignedLongLong, b: VectorSignedLongLong, c: VectorSignedLongLong, d: Int): VectorSignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vperm(a: VectorSignedChar, b: VectorSignedChar, c: VectorUnsignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsumb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsumh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsumgh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsumgf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsumqf(a: VectorUnsignedInt, b: VectorUnsignedInt): U128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsumqg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): U128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vscbiq(a: U128, b: U128): U128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsbiq(a: U128, b: U128, c: U128): U128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vsbcbiq(a: U128, b: U128, c: U128): U128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vacq(a: U128, b: U128, c: U128): U128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vscbib(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vscbih(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vscbif(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vscbig(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaeb(a: VectorSignedChar, b: VectorSignedChar, c: Int): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaeh(a: VectorSignedShort, b: VectorSignedShort, c: Int): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaef(a: VectorSignedInt, b: VectorSignedInt, c: Int): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaezb(a: VectorSignedChar, b: VectorSignedChar, c: Int): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaezh(a: VectorSignedShort, b: VectorSignedShort, c: Int): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaezf(a: VectorSignedInt, b: VectorSignedInt, c: Int): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaebs(a: VectorSignedChar, b: VectorSignedChar, c: Int): PackedTuple<VectorSignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaehs(a: VectorSignedShort, b: VectorSignedShort, c: Int): PackedTuple<VectorSignedShort, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaefs(a: VectorSignedInt, b: VectorSignedInt, c: Int): PackedTuple<VectorSignedInt, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaezbs(a: VectorSignedChar, b: VectorSignedChar, c: Int): PackedTuple<VectorSignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaezhs(a: VectorSignedShort, b: VectorSignedShort, c: Int): PackedTuple<VectorSignedShort, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfaezfs(a: VectorSignedInt, b: VectorSignedInt, c: Int): PackedTuple<VectorSignedInt, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vll(a: UInt, b: ConstPtr): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstl(a: VectorSignedChar, b: UInt, c: MutPtr) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun vlrl(a: UInt, b: ConstPtr): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrl(a: VectorUnsignedChar, b: UInt, c: MutPtr) {
    throw UnsupportedOperationException("Native FFI addon is not available")
}

public actual fun lcbb(a: ConstPtr, b: UInt): UInt =
    if (s390xVectorNapiAvailable) wasmCorearchS390xVectorLcbb(a.rawAddress.toString(), b.toInt()).toUInt() else throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vlbb(a: ConstPtr, b: UInt): MaybeUninit<VectorSignedChar> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpksh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpksf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpksg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpklsh(a: VectorSignedShort, b: VectorSignedShort): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpklsf(a: VectorSignedInt, b: VectorSignedInt): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpklsg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpkshs(a: VectorSignedShort, b: VectorSignedShort): PackedTuple<VectorSignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpksfs(a: VectorSignedInt, b: VectorSignedInt): PackedTuple<VectorSignedShort, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpksgs(a: VectorSignedLongLong, b: VectorSignedLongLong): PackedTuple<VectorSignedInt, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpklshs(a: VectorUnsignedShort, b: VectorUnsignedShort): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpklsfs(a: VectorUnsignedInt, b: VectorUnsignedInt): PackedTuple<VectorUnsignedShort, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vpklsgs(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): PackedTuple<VectorUnsignedInt, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavgb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavgh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavgf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavgg(a: VectorSignedLongLong, b: VectorSignedLongLong): VectorSignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavglb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavglh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavglf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vavglg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): VectorUnsignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcksm(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmhb(a: VectorSignedChar, b: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmhh(a: VectorSignedShort, b: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmhf(a: VectorSignedInt, b: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmlhb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmlhh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmlhf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaeb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaeh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaef(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaleb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaleh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmalef(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaob(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaoh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaof(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedLongLong): VectorSignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmalob(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmaloh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmalof(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmahb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmahh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmahf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmalhb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmalhh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmalhf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmalb(a: VectorSignedChar, b: VectorSignedChar, c: VectorSignedChar): VectorSignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmalh(a: VectorSignedShort, b: VectorSignedShort, c: VectorSignedShort): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmalf(a: VectorSignedInt, b: VectorSignedInt, c: VectorSignedInt): VectorSignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmallb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmallh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmallf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgfmb(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgfmh(a: VectorUnsignedShort, b: VectorUnsignedShort): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgfmf(a: VectorUnsignedInt, b: VectorUnsignedInt): VectorUnsignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgfmg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong): U128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgfmab(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgfmah(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgfmaf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedLongLong): VectorUnsignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vgfmag(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: U128): U128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vbperm(a: VectorUnsignedChar, b: VectorUnsignedChar): VectorUnsignedLongLong =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vftcisb(a: VectorFloat, b: UInt): PackedTuple<VectorBoolInt, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vftcidb(a: VectorDouble, b: UInt): PackedTuple<VectorBoolLongLong, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vtm(a: I8x16, b: I8x16): Int =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrsb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrsh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrsf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrszb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrszh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrszf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vistrb(a: VectorUnsignedChar): VectorUnsignedChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vistrh(a: VectorUnsignedShort): VectorUnsignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vistrf(a: VectorUnsignedInt): VectorUnsignedInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vistrbs(a: VectorUnsignedChar): PackedTuple<VectorUnsignedChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vistrhs(a: VectorUnsignedShort): PackedTuple<VectorUnsignedShort, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vistrfs(a: VectorUnsignedInt): PackedTuple<VectorUnsignedInt, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vmslg(a: VectorUnsignedLongLong, b: VectorUnsignedLongLong, c: U128, d: UInt): U128 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrcb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): VectorBoolChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrch(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): VectorBoolShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrcf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): VectorBoolInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrcbs(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): PackedTuple<VectorBoolChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrchs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): PackedTuple<VectorBoolShort, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrcfs(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): PackedTuple<VectorBoolInt, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrczb(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): VectorBoolChar =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrczh(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): VectorBoolShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrczf(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): VectorBoolInt =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrczbs(a: VectorUnsignedChar, b: VectorUnsignedChar, c: VectorUnsignedChar, d: UInt): PackedTuple<VectorBoolChar, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrczhs(a: VectorUnsignedShort, b: VectorUnsignedShort, c: VectorUnsignedShort, d: UInt): PackedTuple<VectorBoolShort, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vstrczfs(a: VectorUnsignedInt, b: VectorUnsignedInt, c: VectorUnsignedInt, d: UInt): PackedTuple<VectorBoolInt, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeeb(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeeh(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeef(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeezb(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeezh(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeezf(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeebs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeehs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeefs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeezbs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeezhs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeezfs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeneb(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfeneh(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfenef(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfenezb(a: I8x16, b: I8x16): I8x16 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfenezh(a: I16x8, b: I16x8): I16x8 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfenezf(a: I32x4, b: I32x4): I32x4 =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfenebs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfenehs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfenefs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfenezbs(a: I8x16, b: I8x16): PackedTuple<I8x16, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfenezhs(a: I16x8, b: I16x8): PackedTuple<I16x8, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vfenezfs(a: I32x4, b: I32x4): PackedTuple<I32x4, Int> =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vclfnhs(a: VectorSignedShort, immarg: Int): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vclfnls(a: VectorSignedShort, immarg: Int): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcfn(a: VectorSignedShort, immarg: Int): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcnf(a: VectorSignedShort, immarg: Int): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun vcrnfs(a: VectorFloat, b: VectorFloat, immarg: Int): VectorSignedShort =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun minnumV4f32(a: VectorFloat, b: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun minnumV2f64(a: VectorDouble, b: VectorDouble): VectorDouble =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun maxnumV4f32(a: VectorFloat, b: VectorFloat): VectorFloat =
    throw UnsupportedOperationException("Native FFI addon is not available")

public actual fun maxnumV2f64(a: VectorDouble, b: VectorDouble): VectorDouble =
    throw UnsupportedOperationException("Native FFI addon is not available")
